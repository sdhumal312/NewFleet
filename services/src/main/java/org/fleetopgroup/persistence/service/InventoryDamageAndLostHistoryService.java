package org.fleetopgroup.persistence.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.InventoryDamageAndLostHistoryRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDamageAndLostHistoryDto;
import org.fleetopgroup.persistence.model.InventoryDamageAndLostHistory;
import org.fleetopgroup.persistence.serviceImpl.IInventoryDamageAndLostHistoryService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InventoryDamageAndLostHistoryService implements IInventoryDamageAndLostHistoryService{
	
	@PersistenceContext EntityManager entityManager;
	
	@Autowired private InventoryDamageAndLostHistoryRepository InventoryDamageAndLostHistoryRepository;
	
	SimpleDateFormat	format = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat	format1 = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY_HH_MM_AA);
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	
	private static final int PAGE_SIZE_DAMAGE = 50;
	private static final int PAGE_SIZE_LOST = 50;
	
	@Override
	public void saveInventoryDamageAndLostHistory(InventoryDamageAndLostHistory inventoryDamageHistory) throws Exception {
		
		InventoryDamageAndLostHistoryRepository.save(inventoryDamageHistory);
	}
	
	
	@Override
	public List<InventoryDamageAndLostHistoryDto> getInDamageDetailsList(Integer pageNumber, long clothTypeId, Integer locationId) throws Exception {

		TypedQuery<Object[]> 							typedQuery 				= null;
		List<Object[]> 									resultList 				= null; 
		List<InventoryDamageAndLostHistoryDto> 			inDamageDetailsList 	= null;
		InventoryDamageAndLostHistoryDto 				inDamageDetails			= null;
		CustomUserDetails								userDetails				= null;

		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			typedQuery = entityManager.createQuery(
				"SELECT IDLH.InventoryDamageAndLostHistoryId, IDLH.wareHouseLocation, IDLH.clothTypesId, IDLH.damagedQuantity, "
					+ " PL.partlocation_name, C.clothTypeName, U.firstName, IDLH.createdDate "
					+ " FROM InventoryDamageAndLostHistory as IDLH "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = IDLH.wareHouseLocation "
					+ " INNER JOIN ClothTypes C ON C.clothTypesId = IDLH.clothTypesId "
					+ " INNER JOIN User U ON U.id = IDLH.receiveById "
					+ " WHERE IDLH.clothTypesId = "+clothTypeId+" AND IDLH.wareHouseLocation = "+locationId+" AND IDLH.damagedQuantity != 0 "
					+ " AND IDLH.companyId = "+userDetails.getCompany_id()+" AND IDLH.markForDelete = 0 ORDER BY IDLH.InventoryDamageAndLostHistoryId DESC ",
					Object[].class);
			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE_DAMAGE);
			typedQuery.setMaxResults(PAGE_SIZE_DAMAGE);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				inDamageDetailsList = new ArrayList<>();

				for (Object[] result : resultList) {
					inDamageDetails = new InventoryDamageAndLostHistoryDto();
					
					inDamageDetails.setInventoryDamageAndLostHistoryId((long) result[0]);
					inDamageDetails.setWareHouseLocation((int) result[1]);
					inDamageDetails.setClothTypesId((long) result[2]);
					inDamageDetails.setDamagedQuantity(Double.parseDouble(toFixedTwo.format(result[3])));
					inDamageDetails.setLocationName((String) result[4]);
					inDamageDetails.setClothName((String) result[5]);
					inDamageDetails.setReceiveByStr((String) result[6]);
					inDamageDetails.setCreatedDateStr(format1.format((Date) result[7]));
					
					inDamageDetailsList.add(inDamageDetails);
				}
			}
			return inDamageDetailsList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			inDamageDetailsList 	= null;
			inDamageDetails			= null;
		}
	}
	
	@Override
	public List<InventoryDamageAndLostHistoryDto> getInLostDetailsList(Integer pageNumber, long clothTypeId, Integer locationId) throws Exception {

		TypedQuery<Object[]> 							typedQuery 				= null;
		List<Object[]> 									resultList 				= null; 
		List<InventoryDamageAndLostHistoryDto> 			inLostDetailsList 		= null;
		InventoryDamageAndLostHistoryDto 				inLostDetails			= null;
		CustomUserDetails								userDetails				= null;

		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			typedQuery = entityManager.createQuery(
				"SELECT IDLH.InventoryDamageAndLostHistoryId, IDLH.wareHouseLocation, IDLH.clothTypesId, IDLH.losedQuantity, "
					+ " PL.partlocation_name, C.clothTypeName, U.firstName, IDLH.createdDate "
					+ " FROM InventoryDamageAndLostHistory as IDLH "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = IDLH.wareHouseLocation "
					+ " INNER JOIN ClothTypes C ON C.clothTypesId = IDLH.clothTypesId "
					+ " INNER JOIN User U ON U.id = IDLH.receiveById "
					+ " WHERE IDLH.clothTypesId = "+clothTypeId+" AND IDLH.wareHouseLocation = "+locationId+" AND IDLH.losedQuantity != 0 "
					+ " AND IDLH.companyId = "+userDetails.getCompany_id()+" AND IDLH.markForDelete = 0 ORDER BY IDLH.InventoryDamageAndLostHistoryId DESC ",
					Object[].class);
			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE_LOST);
			typedQuery.setMaxResults(PAGE_SIZE_LOST);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				inLostDetailsList = new ArrayList<>();

				for (Object[] result : resultList) {
					inLostDetails = new InventoryDamageAndLostHistoryDto();
					
					inLostDetails.setInventoryDamageAndLostHistoryId((long) result[0]);
					inLostDetails.setWareHouseLocation((int) result[1]);
					inLostDetails.setClothTypesId((long) result[2]);
					inLostDetails.setLosedQuantity(Double.parseDouble(toFixedTwo.format(result[3])));
					inLostDetails.setLocationName((String) result[4]);
					inLostDetails.setClothName((String) result[5]);
					inLostDetails.setReceiveByStr((String) result[6]);
					inLostDetails.setCreatedDateStr(format1.format((Date) result[7]));
					
					inLostDetailsList.add(inLostDetails);
				}
			}
			return inLostDetailsList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			inLostDetailsList 		= null;
			inLostDetails			= null;
		}
	}
	
	@Override
	@Transactional
	public void updateDamageQty(Long laundryInvoiceId, Long	clothTypesId, double damagedQuantity) throws Exception {
		CustomUserDetails						userDetails					= null;
		Date									receivedDate				;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			receivedDate		 = new Date();
			java.sql.Date sqlDate = new java.sql.Date(receivedDate.getTime());
			
			entityManager.createQuery(
					" UPDATE InventoryDamageAndLostHistory AS VC SET VC.damagedQuantity = VC.damagedQuantity + "+damagedQuantity+", "
							+ " VC.lastModifiedOn = '"+sqlDate+"', VC.lastModifiedById = "+userDetails.getId()+" "
							+ " WHERE VC.laundryInvoiceId = "+laundryInvoiceId+" "
							+ " AND VC.clothTypesId = "+clothTypesId+" AND VC.markForDelete = 0 ")
			.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails					= null;
		}
		
		
	}
	
	@Override
	@Transactional
	public void updateLostQty(Long laundryInvoiceId, Long	clothTypesId, double losedQuantity) throws Exception {
		
		CustomUserDetails						userDetails					= null;
		Date									receivedDate				;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			receivedDate		 = new Date();
			java.sql.Date sqlDate = new java.sql.Date(receivedDate.getTime());
			
			entityManager.createQuery(
					" UPDATE InventoryDamageAndLostHistory AS VC SET VC.losedQuantity = VC.losedQuantity + "+losedQuantity+", "
							+ " VC.lastModifiedOn = '"+sqlDate+"', VC.lastModifiedById = "+userDetails.getId()+" "
							+ " WHERE VC.laundryInvoiceId = "+laundryInvoiceId+" "
							+ " AND VC.clothTypesId = "+clothTypesId+" AND VC.markForDelete = 0 ")
			.executeUpdate();
			
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails					= null;
		}
		
	}
}