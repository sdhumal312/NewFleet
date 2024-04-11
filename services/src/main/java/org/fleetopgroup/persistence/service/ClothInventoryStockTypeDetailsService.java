package org.fleetopgroup.persistence.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.ClothInvoiceStockType;
import org.fleetopgroup.persistence.dao.ClothInventoryStockTypeDetailsRepository;
import org.fleetopgroup.persistence.dto.ClothInventoryStockTypeDetailsDto;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryStockTypeDetailsService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClothInventoryStockTypeDetailsService implements IClothInventoryStockTypeDetailsService {

	@PersistenceContext public EntityManager entityManager;
	@Autowired	private ClothInventoryStockTypeDetailsRepository		typeDetailsRepository;
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	
	private static final int PAGE_SIZE = 10;
	
	@Override
	public List<ClothInventoryStockTypeDetailsDto> getLocationClothDetails(Integer locationId, Integer pageNumber) throws Exception {
		try {

			TypedQuery<Object[]> queryt = null;
				queryt = entityManager.createQuery("SELECT CS.clothInventoryStockTypeDetailsId, CS.usedStockQuantity, CS.newStockQuantity, CS.clothTypesId, CS.wareHouseLocationId"
						+ ", PL.partlocation_name, CT.clothTypeName, CS.inServiceQuantity, CS.inWashingQuantity, CS.damagedQuantity, CS.losedQuantity, CS.inTransferQuantity "
						+ " FROM ClothInventoryStockTypeDetails AS CS "
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id  = CS.wareHouseLocationId"
						+ " INNER JOIN ClothTypes CT ON CT.clothTypesId = CS.clothTypesId"
						+ " where CS.wareHouseLocationId = "+locationId+"  and CS.markForDelete = 0 ",
						Object[].class);
				queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
				queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<ClothInventoryStockTypeDetailsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				
				Dtos = new ArrayList<ClothInventoryStockTypeDetailsDto>();
				ClothInventoryStockTypeDetailsDto dropdown = null;
				for (Object[] result : results) {
					dropdown = new ClothInventoryStockTypeDetailsDto();

					dropdown.setClothInventoryStockTypeDetailsId((Long) result[0]);
					dropdown.setUsedStockQuantity(Double.parseDouble(toFixedTwo.format(result[1])));
					dropdown.setNewStockQuantity(Double.parseDouble(toFixedTwo.format(result[2])));
					dropdown.setClothTypesId((Long) result[3]);
					dropdown.setWareHouseLocationId((Integer) result[4]);
					dropdown.setLocationName((String) result[5]);
					dropdown.setClothTypeName((String) result[6]);
					dropdown.setInServiceQuantity(Double.parseDouble(toFixedTwo.format( result[7])));
					dropdown.setInWashingQuantity(Double.parseDouble(toFixedTwo.format( result[8])));
					if(result[9] != null)
					dropdown.setDamagedQuantity(Double.parseDouble(toFixedTwo.format(result[9])));
					if(result[10] != null)
					dropdown.setLosedQuantity(Double.parseDouble(toFixedTwo.format(result[10])));
					if(result[11] != null)
					dropdown.setInTransferQuantity(Double.parseDouble(toFixedTwo.format(result[11])));	
						
					Dtos.add(dropdown);
				}
			}
			return Dtos;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateLocationStockDetails(ValueObject valueObject) throws Exception {
		try {
			if(valueObject.getShort("typeOfCloth") == ClothInvoiceStockType.STOCK_TYPE_NEW) {
				typeDetailsRepository.updateNewStockDetails(valueObject.getInt("locationId"), valueObject.getDouble("quantity"), valueObject.getLong("clothTypes"));
			}else if(valueObject.getShort("typeOfCloth") == ClothInvoiceStockType.STOCK_TYPE_OLD) {
				typeDetailsRepository.updateUsedStockDetails(valueObject.getInt("locationId"), valueObject.getDouble("quantity"), valueObject.getLong("clothTypes"));
			}else {
				typeDetailsRepository.updateNewStockDetails(valueObject.getInt("locationId"), valueObject.getDouble("quantity"), valueObject.getLong("clothTypes"));
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void removeVehicleClothToStockDetails(ValueObject valueObject) throws Exception {
		long 			clothTypeId 						= 0;
		int				oldLocation							= 0;
		double			removeQuantity						= 0.0;
		
		clothTypeId 		= 	valueObject.getLong("clothTypesId");
		oldLocation 		= 	valueObject.getInt("locationId");
		removeQuantity   	= 	valueObject.getDouble("removeQuantity");
		
			entityManager.createQuery(
					" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.usedStockQuantity = VC.usedStockQuantity + "+removeQuantity+", "
							+ " VC.inServiceQuantity = VC.inServiceQuantity - "+removeQuantity+""
							+ " WHERE VC.clothTypesId = "+clothTypeId+" "
							+ " AND VC.wareHouseLocationId = "+oldLocation+" AND VC.markForDelete = 0 ")
					.executeUpdate();
		
	}
	
	@Override
	public ClothInventoryStockTypeDetailsDto getClothLocationQuantity(Long clothesTypeId, Integer locationId, Integer companyId)
			throws Exception {Query query = entityManager.createQuery(
					"SELECT CT.clothTypesId, CT.usedStockQuantity, CT.newStockQuantity, CT.inServiceQuantity, "
					+ " CT.inWashingQuantity, CT.damagedQuantity, CT.losedQuantity "
					+ " FROM ClothInventoryStockTypeDetails AS CT "
					+ " WHERE CT.clothTypesId = "+clothesTypeId+" AND CT.wareHouseLocationId = "+locationId+" AND CT.companyId = "+companyId+" AND CT.markForDelete = 0 ");
			
			Object[] vehicle = null;
			try {
				vehicle = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			ClothInventoryStockTypeDetailsDto select;
			if (vehicle != null) {
				select = new ClothInventoryStockTypeDetailsDto();
				
				select.setClothTypesId((Long) vehicle[0]);
				if(vehicle[1] != null)
				select.setUsedStockQuantity(Double.parseDouble(toFixedTwo.format(vehicle[1])));
				if(vehicle[2] != null)
				select.setNewStockQuantity(Double.parseDouble(toFixedTwo.format(vehicle[2])));
				if(vehicle[3] != null)
				select.setInServiceQuantity(Double.parseDouble(toFixedTwo.format( vehicle[3])));
				if(vehicle[4] != null)
				select.setInWashingQuantity(Double.parseDouble(toFixedTwo.format(vehicle[4])));
				if(vehicle[5] != null)
				select.setDamagedQuantity(Double.parseDouble(toFixedTwo.format(vehicle[5])));
				if(vehicle[6] != null)
				select.setLosedQuantity(Double.parseDouble(toFixedTwo.format(vehicle[6])));
				
			} else {
				return null;
			}

			return select;
		}
	
	@Override
	@Transactional
	public void updateLocationInWashingStockDetails(Integer	locationId, Long clothTypesId, Double quantity) throws Exception {
		
		entityManager.createQuery(
				" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.inWashingQuantity = VC.inWashingQuantity + "+quantity+", "
						+ " VC.usedStockQuantity = VC.usedStockQuantity - "+quantity+""
						+ " WHERE VC.clothTypesId = "+clothTypesId+" "
						+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
				.executeUpdate();
		
	}
	
	@Override
	@Transactional
	public void updateLocationStockDetailsToReceive(ValueObject valueObject) throws Exception {
		
		long  								clothTypesId		= 	0;
		int   								locationId   		= 	0;
		double 								receiveQuantity		= 	0.0;
		
		clothTypesId 		= valueObject.getLong("clothTypesId");
		locationId 			= valueObject.getInt("locationId");
		receiveQuantity 	= valueObject.getDouble("receiveQuantity");
		
		entityManager.createQuery(
				" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.inWashingQuantity = VC.inWashingQuantity - "+receiveQuantity+", "
						+ " VC.newStockQuantity = VC.newStockQuantity + "+receiveQuantity+""
						+ " WHERE VC.clothTypesId = "+clothTypesId+" "
						+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
				.executeUpdate();
		
	}
	
	@Override
	@Transactional
	public void updateLocationStockDetailsToDamage(ValueObject valueObject) throws Exception {
		
		long  								clothTypesId		= 	0;
		int   								locationId   		= 	0;
		double 								damageQuantity		= 	0.0;
		
		clothTypesId 		= valueObject.getLong("damageclothTypesId");
		locationId 			= valueObject.getInt("damagelocationId");
		damageQuantity 		= valueObject.getDouble("damageQuantity");
		
		entityManager.createQuery(
				" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.inWashingQuantity = VC.inWashingQuantity - "+damageQuantity+", "
						+ " VC.damagedQuantity = VC.damagedQuantity + "+damageQuantity+" "
						+ " WHERE VC.clothTypesId = "+clothTypesId+" "
						+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
				.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateLocationStockDetailsToLost(ValueObject valueObject) throws Exception {
		
		long  								clothTypesId		= 	0;
		int   								locationId   		= 	0;
		double 								lostQuantity		= 	0.0;
		
		clothTypesId 		= valueObject.getLong("lostclothTypesId");
		locationId 			= valueObject.getInt("lostlocationId");
		lostQuantity 		= valueObject.getDouble("lostQuantity");
		
		entityManager.createQuery(
				" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.inWashingQuantity = VC.inWashingQuantity - "+lostQuantity+", "
						+ " VC.losedQuantity = VC.losedQuantity + "+lostQuantity+" "
						+ " WHERE VC.clothTypesId = "+clothTypesId+" "
						+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
				.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateClothDamageDetails(ValueObject valueObject) throws Exception {
		
		long  								clothTypesId		= 	0;
		int   								locationId   		= 	0;
		double 								damageQuantity		= 	0.0;
		short								stockTypeId			= 	0;
		
		clothTypesId 		= valueObject.getLong("damageClothTypes");
		locationId 			= valueObject.getInt("damagelocationId");
		damageQuantity 		= valueObject.getDouble("damageQuantity");	
		stockTypeId			= valueObject.getShort("damageTypeOfCloth");
		
		if(stockTypeId == ClothInvoiceStockType.STOCK_TYPE_NEW) {
			entityManager.createQuery(
					" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.newStockQuantity = VC.newStockQuantity - "+damageQuantity+", "
							+ " VC.damagedQuantity = VC.damagedQuantity + "+damageQuantity+" "
							+ " WHERE VC.clothTypesId = "+clothTypesId+" "
							+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
			.executeUpdate();
		} else {
			entityManager.createQuery(
					" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.usedStockQuantity = VC.usedStockQuantity - "+damageQuantity+", "
							+ " VC.damagedQuantity = VC.damagedQuantity + "+damageQuantity+" "
							+ " WHERE VC.clothTypesId = "+clothTypesId+" "
							+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
			.executeUpdate();	
		}
	}
	
	
	@Override
	@Transactional
	public void updateClothLostDetails(ValueObject valueObject) throws Exception {
		
		long  								clothTypesId		= 	0;
		int   								locationId   		= 	0;
		double 								lostQuantity		= 	0.0;
		short								stockTypeId			= 	0;
		
		clothTypesId 		= valueObject.getLong("lostClothTypes");
		locationId 			= valueObject.getInt("lostlocationId");
		lostQuantity 		= valueObject.getDouble("lostQuantity");	
		stockTypeId			= valueObject.getShort("lostTypeOfCloth");
		
		if(stockTypeId == ClothInvoiceStockType.STOCK_TYPE_NEW) {
			entityManager.createQuery(
					" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.newStockQuantity = VC.newStockQuantity - "+lostQuantity+", "
							+ " VC.losedQuantity = VC.losedQuantity + "+lostQuantity+" "
							+ " WHERE VC.clothTypesId = "+clothTypesId+" "
							+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
			.executeUpdate();
		} else {
			entityManager.createQuery(
					" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.usedStockQuantity = VC.usedStockQuantity - "+lostQuantity+", "
							+ " VC.losedQuantity = VC.losedQuantity + "+lostQuantity+" "
							+ " WHERE VC.clothTypesId = "+clothTypesId+" "
							+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
			.executeUpdate();	
		}
	}
	
	@Override
	@Transactional
	public void removeVehicleUpdateStockDetailsOfUsedStckQty(ValueObject valueObject) throws Exception {
		long 			clothTypeId 						= 0;
		int				newLocation							= 0;
		double			removeQuantity						= 0.0;
		
		clothTypeId 		= 	valueObject.getLong("clothTypesId");
		newLocation 		= 	valueObject.getInt("removelocationId");
		removeQuantity   	= 	valueObject.getDouble("removeQuantity");
		
			entityManager.createQuery(
					" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.usedStockQuantity = VC.usedStockQuantity + "+removeQuantity+" "
							+ " WHERE VC.clothTypesId = "+clothTypeId+" "
							+ " AND VC.wareHouseLocationId = "+newLocation+" AND VC.markForDelete = 0 ")
			.executeUpdate();
	}
	
	@Override
	@Transactional
	public void removeVehicleUpdateStockDetailsOfInService(ValueObject valueObject) throws Exception {
		long 			clothTypeId 						= 0;
		int				oldLocation							= 0;
		double			removeQuantity						= 0.0;
		
		clothTypeId 		= 	valueObject.getLong("clothTypesId");
		oldLocation 		= 	valueObject.getInt("locationId");
		removeQuantity   	= 	valueObject.getDouble("removeQuantity");
		
		entityManager.createQuery(
				" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.inServiceQuantity = VC.inServiceQuantity - "+removeQuantity+" "
						+ " WHERE VC.clothTypesId = "+clothTypeId+" "
						+ " AND VC.wareHouseLocationId = "+oldLocation+" AND VC.markForDelete = 0 ")
		.executeUpdate();
		
	}
	
	@Override
	@Transactional
	public void updateUsedStckQty(long clothTypeId, int locationId, double quantity) throws Exception {
		
			entityManager.createQuery(
					" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.usedStockQuantity = VC.usedStockQuantity + "+quantity+" "
							+ " WHERE VC.clothTypesId = "+clothTypeId+" "
							+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
			.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateNewStckQty(long clothTypeId, int locationId, double quantity) throws Exception {
		
		entityManager.createQuery(
				" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.newStockQuantity = VC.newStockQuantity + "+quantity+" "
						+ " WHERE VC.clothTypesId = "+clothTypeId+" "
						+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
		.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateTransferQty(long clothTypeId, int locationId, double quantity) throws Exception {
		
		entityManager.createQuery(
				" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.inTransferQuantity = VC.inTransferQuantity - "+quantity+" "
						+ " WHERE VC.clothTypesId = "+clothTypeId+" "
						+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
		.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateRejectTransferQty(long clothTypeId, int locationId, double quantity, short stockTypeId) throws Exception {
		
		
		if(stockTypeId == ClothInvoiceStockType.STOCK_TYPE_NEW) {
			entityManager.createQuery(
					" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.inTransferQuantity = VC.inTransferQuantity - "+quantity+", "
							+ " VC.newStockQuantity = VC.newStockQuantity + "+quantity+" "
							+ " WHERE VC.clothTypesId = "+clothTypeId+" "
							+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
			.executeUpdate();
		} else {
			entityManager.createQuery(
					" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.inTransferQuantity = VC.inTransferQuantity - "+quantity+", "
							+ " VC.usedStockQuantity = VC.usedStockQuantity + "+quantity+" "
							+ " WHERE VC.clothTypesId = "+clothTypeId+" "
							+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
			.executeUpdate();	
		}
	}
	
	@Override
	public void updateStockDetailsForLaundryDelete(long clothTypeId, int locationId, double quantity) throws Exception {
		try {
			entityManager.createQuery(
					" UPDATE ClothInventoryStockTypeDetails AS VC SET VC.usedStockQuantity = VC.usedStockQuantity + "+quantity+", "
							+ " inWashingQuantity = inWashingQuantity - "+quantity+" "
							+ " WHERE VC.clothTypesId = "+clothTypeId+" "
							+ " AND VC.wareHouseLocationId = "+locationId+" AND VC.markForDelete = 0 ")
			.executeUpdate();
	
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public ValueObject getLocationWiseUpholsteryQuantity(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 						query 					= null;
		List<InventoryDto> 							inventoryDtoList 		= null;
		try {
			query = entityManager.createQuery("select  SUM(B.newStockQuantity) , B.wareHouseLocationId ,PL.partlocation_name from ClothInventoryStockTypeDetails AS B "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = B.wareHouseLocationId "
					+ " where B.clothTypesId ="+valueObject.getLong("clothTypesId")+" AND B.companyId ="+valueObject.getInt("companyId")+" "+valueObject.getString("queryStock", "")+" AND B.markForDelete=0  group by B.wareHouseLocationId ", Object[].class);
			List<Object[]> results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				inventoryDtoList = new ArrayList<>();
				InventoryDto inventoryDto = null;
				for (Object[] result : results) {
					inventoryDto = new InventoryDto();
					inventoryDto.setQuantity((Double)result[0]);
					inventoryDto.setLocationId((Integer)result[1]);
					inventoryDto.setLocation((String)result[2]);
					if(inventoryDto.getQuantity() > 0.0)
					inventoryDtoList.add(inventoryDto);
				}
			}
			valueObject.put("locationWisePartQuantity", inventoryDtoList);
			return 	valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
