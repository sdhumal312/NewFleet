package org.fleetopgroup.persistence.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.VehicleClothInventoryDetailsRepository;
import org.fleetopgroup.persistence.dto.VehicleClothInventoryDetailsDto;
import org.fleetopgroup.persistence.model.VehicleClothInventoryDetails;
import org.fleetopgroup.persistence.serviceImpl.IVehicleClothInventoryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleClothInventoryDetailsService implements IVehicleClothInventoryDetailsService {
	
	@PersistenceContext	EntityManager							entityManager;
	@Autowired	VehicleClothInventoryDetailsRepository			vehicleClothInventoryDetailsRepository;
	
	
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");

	@Override
	public void saveVehicleClothInventoryDetails(VehicleClothInventoryDetails inventoryDetails) throws Exception {
		
		vehicleClothInventoryDetailsRepository.save(inventoryDetails);
	}

	@Override
	public List<VehicleClothInventoryDetailsDto> getVehicleClothInventoryDetailsList(Integer vid) throws Exception {

		TypedQuery<Object[]> 								typedQuery 				= null;
		List<Object[]> 										resultList 				= null; 
		List<VehicleClothInventoryDetailsDto> 				batteryInvoiceList 		= null;
		VehicleClothInventoryDetailsDto						batteryInvoiceDto		= null;
	

		try {

			typedQuery = entityManager.createQuery("SELECT VCI.vehicleClothInventoryDetailsId, VCI.vid, VCI.clothTypesId, VCI.quantity, CT.clothTypeName, VCI.locationId , PL.partlocation_name"
					+ " FROM VehicleClothInventoryDetails AS VCI"
					+ " INNER JOIN ClothTypes AS CT ON CT.clothTypesId = VCI.clothTypesId "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = VCI.locationId"
					+ " WHERE VCI.vid = "+vid+" AND VCI.quantity > 0 AND VCI.markForDelete = 0 ORDER BY VCI.vehicleClothInventoryDetailsId DESC", Object[].class);


			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				batteryInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					batteryInvoiceDto = new VehicleClothInventoryDetailsDto();
					
					batteryInvoiceDto.setVehicleClothInventoryDetailsId((Long) result[0]);
					batteryInvoiceDto.setVid((Integer) result[1]);
					batteryInvoiceDto.setClothTypesId((Long) result[2]);
					if(result[3] != null)
					batteryInvoiceDto.setQuantity(Double.parseDouble(toFixedTwo.format(result[3])));
					batteryInvoiceDto.setClothTypesName((String) result[4]);
					batteryInvoiceDto.setLocationId((Integer) result[5]);
					batteryInvoiceDto.setLocationName((String) result[6]);
					
					batteryInvoiceList.add(batteryInvoiceDto);
				}
			}
			return batteryInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			batteryInvoiceList 		= null;
			batteryInvoiceDto		= null;
		}
	}
	
	@Override
	public VehicleClothInventoryDetailsDto getVehicleClothInventoryDetails(Long vehicleClothInventoryDetailsId)
			throws Exception {

		Query query = entityManager.createQuery("SELECT VCI.vehicleClothInventoryDetailsId, VCI.vid, VCI.clothTypesId, VCI.quantity, CT.clothTypeName, VCI.locationId , PL.partlocation_name"
				+ " FROM VehicleClothInventoryDetails AS VCI"
				+ " INNER JOIN ClothTypes AS CT ON CT.clothTypesId = VCI.clothTypesId "
				+ " INNER JOIN PartLocations PL ON PL.partlocation_id = VCI.locationId"
				+ " WHERE VCI.vehicleClothInventoryDetailsId = "+vehicleClothInventoryDetailsId+" AND VCI.quantity > 0 AND VCI.markForDelete = 0 ORDER BY VCI.vehicleClothInventoryDetailsId DESC");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		VehicleClothInventoryDetailsDto select;
		if (result != null) {
			select = new VehicleClothInventoryDetailsDto();
			
			select.setVehicleClothInventoryDetailsId((Long) result[0]);
			select.setVid((Integer) result[1]);
			select.setClothTypesId((Long) result[2]);
			select.setQuantity((Double) result[3]);
			select.setClothTypesName((String) result[4]);
			select.setLocationId((Integer) result[5]);
			select.setLocationName((String) result[6]);
		} else {
			return null;
		}

		return select;
	}

	@Override
	@Transactional
	public void removeClothDetailsFromVehicle(VehicleClothInventoryDetailsDto clothInventoryDetailsDto)
			throws Exception {
		
		entityManager.createQuery(
				" UPDATE VehicleClothInventoryDetails AS VC SET VC.quantity = VC.quantity - "+clothInventoryDetailsDto.getQuantity()+", VC.lastModifiedById = "+clothInventoryDetailsDto.getLastModifiedById()+" "
				+ " , VC.lastModifiedOn = '"+clothInventoryDetailsDto.getLastModifiedOn()+"' WHERE vehicleClothInventoryDetailsId = "+clothInventoryDetailsDto.getVehicleClothInventoryDetailsId()+" ")
				.executeUpdate();
		
	}
	
	@Override
	public VehicleClothInventoryDetails validateVehicleClothInventoryDetails(Long clothTypesId, Integer vehicleId, Integer locationId)
			throws Exception {
		
		return vehicleClothInventoryDetailsRepository.validateVehicleClothInventoryDetails(clothTypesId, vehicleId, locationId);
	}
}
