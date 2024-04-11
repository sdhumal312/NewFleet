package org.fleetopgroup.persistence.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dto.RefreshmentEntryDto;
import org.fleetopgroup.persistence.report.dao.RefreshmentEntryDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshmentEntryDaoImpl implements RefreshmentEntryDao {

	@PersistenceContext EntityManager entityManager;
	
	@Autowired	ICompanyConfigurationService		companyConfigurationService;

	SimpleDateFormat	format  = new SimpleDateFormat("dd-MM-yyyy");
	
	private static final int PAGE_SIZE = 10;
	
	@Override
	public List<RefreshmentEntryDto> getRefreshmentConsumptionData(String	query, Integer companyId, Long userId) throws Exception {

		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]> 							resultList 				= null; 
		List<RefreshmentEntryDto> 				inventoryList 			= null;
		RefreshmentEntryDto 					inventoryDto			= null;
		HashMap<String, Object>                 configuration			= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.get("vehicleGroupWisePermission")) {
				typedQuery = entityManager.createQuery(" SELECT R.refreshmentEntryId, R.vid, R.tripsheetId, R.partid, R.asignmentType,"
						+ " R.partLocationId, R.inventoryId, R.routeId, R.driverId, R.asignmentDate, R.returnQuantity, R.quantity,"
						+ " R.unitprice, R.discount, R.tax, R.totalAmount, R.comment, I.inventory_Number, MP.partname, PL.partlocation_name,"
						+ " R.refreshmentEntryNumber, TR.routeName, V.vehicle_registration, T.tripSheetNumber, R.returnDate "
						+ " FROM RefreshmentEntry R "
						+ " INNER JOIN Vehicle V ON V.vid = R.vid "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+userId+" "
						+ " INNER JOIN Inventory I ON I.inventory_id = R.inventoryId "
						+ " INNER JOIN MasterParts MP ON MP.partid = R.partid"
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.partLocationId "
						+ " INNER JOIN TripSheet T ON T.tripSheetID = R.tripsheetId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = R.routeId "
						+ " where R.companyId = "+companyId+" "+query+" AND  R.markForDelete = 0", Object[].class);
			}else {
				typedQuery = entityManager.createQuery(" SELECT R.refreshmentEntryId, R.vid, R.tripsheetId, R.partid, R.asignmentType,"
						+ " R.partLocationId, R.inventoryId, R.routeId, R.driverId, R.asignmentDate, R.returnQuantity, R.quantity,"
						+ " R.unitprice, R.discount, R.tax, R.totalAmount, R.comment, I.inventory_Number, MP.partname, PL.partlocation_name,"
						+ " R.refreshmentEntryNumber, TR.routeName, V.vehicle_registration, T.tripSheetNumber, R.returnDate "
						+ " FROM RefreshmentEntry R "
						+ " INNER JOIN Vehicle V ON V.vid = R.vid "
						+ " INNER JOIN Inventory I ON I.inventory_id = R.inventoryId "
						+ " INNER JOIN MasterParts MP ON MP.partid = R.partid"
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.partLocationId"
						+ " INNER JOIN TripSheet T ON T.tripSheetID = R.tripsheetId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = R.routeId "
						+ " where R.companyId = "+companyId+" "+query+" AND  R.markForDelete = 0", Object[].class);
			}

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				inventoryList = new ArrayList<>();
				
				for (Object[] result : resultList) {
					inventoryDto = new RefreshmentEntryDto();
					inventoryDto.setRefreshmentEntryId((Long) result[0]);
					inventoryDto.setVid((Integer) result[1]);
					inventoryDto.setTripsheetId((Long) result[2]);
					inventoryDto.setPartid((Long) result[3]);
					inventoryDto.setAsignmentType((short) result[4]);
					inventoryDto.setPartLocationId((Integer) result[5]);
					inventoryDto.setInventoryId((Long) result[6]);
					inventoryDto.setRouteId((Integer) result[7]);
					inventoryDto.setDriverId((Integer) result[8]);
					inventoryDto.setAsignmentDate((Date) result[9]);
					inventoryDto.setReturnQuantity((Double) result[10]);
					inventoryDto.setQuantity((Double) result[11]);
					inventoryDto.setUnitprice((Double) result[12]);
					inventoryDto.setDiscount((Double) result[13]);
					inventoryDto.setTax((Double) result[14]);
					inventoryDto.setTotalAmount((Double) result[15]);
					inventoryDto.setComment((String) result[16]);
					inventoryDto.setInventory_Number((Long) result[17]);
					inventoryDto.setPartname((String) result[18]);
					inventoryDto.setLocationName((String) result[19]);
					inventoryDto.setRefreshmentEntryNumber((Long) result[20]);
					inventoryDto.setRouteName((String) result[21]);
					inventoryDto.setVehicle_registration((String) result[22]);
					inventoryDto.setTripSheetNumber((Long) result[23]);
					if(result[24] != null) {
						inventoryDto.setReturnDateStr(format.format(result[24]));
					}else {
						inventoryDto.setReturnDateStr("--");
					}
					
					if(inventoryDto.getUnitprice() == null) {
						inventoryDto.setUnitprice(0.0);
					}
					
					
					if(inventoryDto.getReturnQuantity() != null && inventoryDto.getReturnQuantity() > 0) {
						inventoryDto.setReturnAmount(inventoryDto.getReturnQuantity() * inventoryDto.getUnitprice());
					}else {
						inventoryDto.setReturnAmount(0.0);
					}
					inventoryDto.setBalanceAmount(inventoryDto.getTotalAmount() - inventoryDto.getReturnAmount());
					
					if(inventoryDto.getAsignmentDate() != null) {
						inventoryDto.setAsignmentDateStr(format.format(inventoryDto.getAsignmentDate()));
					}
					
					inventoryList.add(inventoryDto);
					
				}
			}
			
			return inventoryList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			inventoryList 			= null;
			inventoryDto			= null;
		}
	}
	
	@Override
	public List<RefreshmentEntryDto> getRefreshmentEntryDtoList(Integer pageNumber, Integer companyId, Long userId)
			throws Exception {

		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]> 							resultList 				= null; 
		List<RefreshmentEntryDto> 				inventoryList 			= null;
		RefreshmentEntryDto 					inventoryDto			= null;
		HashMap<String, Object>                 configuration			= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.get("vehicleGroupWisePermission")) {
				typedQuery = entityManager.createQuery(" SELECT R.refreshmentEntryId, R.vid, R.tripsheetId, R.partid, R.asignmentType,"
						+ " R.partLocationId, R.inventoryId, R.routeId, R.driverId, R.asignmentDate, R.returnQuantity, R.quantity,"
						+ " R.unitprice, R.discount, R.tax, R.totalAmount, R.comment, I.inventory_Number, MP.partname, PL.partlocation_name,"
						+ " R.refreshmentEntryNumber, TR.routeName, V.vehicle_registration, T.tripSheetNumber, R.returnDate "
						+ " FROM RefreshmentEntry R "
						+ " INNER JOIN Vehicle V ON V.vid = R.vid "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+userId+" "
						+ " INNER JOIN Inventory I ON I.inventory_id = R.inventoryId "
						+ " INNER JOIN MasterParts MP ON MP.partid = R.partid"
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.partLocationId "
						+ " INNER JOIN TripSheet T ON T.tripSheetID = R.tripsheetId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = R.routeId "
						+ " where R.companyId = "+companyId+"  AND  R.markForDelete = 0 order by R.refreshmentEntryId DESC", Object[].class);
			}else {
				typedQuery = entityManager.createQuery(" SELECT R.refreshmentEntryId, R.vid, R.tripsheetId, R.partid, R.asignmentType,"
						+ " R.partLocationId, R.inventoryId, R.routeId, R.driverId, R.asignmentDate, R.returnQuantity, R.quantity,"
						+ " R.unitprice, R.discount, R.tax, R.totalAmount, R.comment, I.inventory_Number, MP.partname, PL.partlocation_name,"
						+ " R.refreshmentEntryNumber, TR.routeName, V.vehicle_registration, T.tripSheetNumber, R.returnDate "
						+ " FROM RefreshmentEntry R "
						+ " INNER JOIN Vehicle V ON V.vid = R.vid "
						+ " INNER JOIN Inventory I ON I.inventory_id = R.inventoryId "
						+ " INNER JOIN MasterParts MP ON MP.partid = R.partid"
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = R.partLocationId"
						+ " INNER JOIN TripSheet T ON T.tripSheetID = R.tripsheetId"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = R.routeId "
						+ " where R.companyId = "+companyId+" AND  R.markForDelete = 0 order by R.refreshmentEntryId DESC", Object[].class);
			}
			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				inventoryList = new ArrayList<>();
				
				for (Object[] result : resultList) {
					inventoryDto = new RefreshmentEntryDto();
					inventoryDto.setRefreshmentEntryId((Long) result[0]);
					inventoryDto.setVid((Integer) result[1]);
					inventoryDto.setTripsheetId((Long) result[2]);
					inventoryDto.setPartid((Long) result[3]);
					inventoryDto.setAsignmentType((short) result[4]);
					inventoryDto.setPartLocationId((Integer) result[5]);
					inventoryDto.setInventoryId((Long) result[6]);
					inventoryDto.setRouteId((Integer) result[7]);
					inventoryDto.setDriverId((Integer) result[8]);
					inventoryDto.setAsignmentDate((Date) result[9]);
					inventoryDto.setReturnQuantity((Double) result[10]);
					inventoryDto.setQuantity((Double) result[11]);
					inventoryDto.setUnitprice((Double) result[12]);
					inventoryDto.setDiscount((Double) result[13]);
					inventoryDto.setTax((Double) result[14]);
					inventoryDto.setTotalAmount((Double) result[15]);
					inventoryDto.setComment((String) result[16]);
					inventoryDto.setInventory_Number((Long) result[17]);
					inventoryDto.setPartname((String) result[18]);
					inventoryDto.setLocationName((String) result[19]);
					inventoryDto.setRefreshmentEntryNumber((Long) result[20]);
					inventoryDto.setRouteName((String) result[21]);
					inventoryDto.setVehicle_registration((String) result[22]);
					inventoryDto.setTripSheetNumber((Long) result[23]);
					
					if(result[24] != null) {
						inventoryDto.setReturnDateStr(format.format(result[24]));
					}else {
						inventoryDto.setReturnDateStr("--");
					}
					
					if(inventoryDto.getUnitprice() == null) {
						inventoryDto.setUnitprice(0.0);
					}
					
					
					if(inventoryDto.getReturnQuantity() != null && inventoryDto.getReturnQuantity() > 0) {
						inventoryDto.setReturnAmount(inventoryDto.getReturnQuantity() * inventoryDto.getUnitprice());
					}else {
						inventoryDto.setReturnAmount(0.0);
					}
					inventoryDto.setBalanceAmount(inventoryDto.getTotalAmount() - inventoryDto.getReturnAmount());
					
					if(inventoryDto.getAsignmentDate() != null) {
						inventoryDto.setAsignmentDateStr(format.format(inventoryDto.getAsignmentDate()));
					}
					
					inventoryList.add(inventoryDto);
					
				}
			}
			
			return inventoryList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			inventoryList 			= null;
			inventoryDto			= null;
		}
	}
}
