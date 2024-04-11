package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.BatteryTransferRepository;
import org.fleetopgroup.persistence.dto.BatteryTransferDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreTransferDto;
import org.fleetopgroup.persistence.model.BatteryTransfer;
import org.fleetopgroup.persistence.serviceImpl.IBatteryTransferService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BatteryTransferService implements IBatteryTransferService {

	@Autowired private BatteryTransferRepository		batteryTransferRepository;
	@PersistenceContext	EntityManager entityManager;

	
	@Override
	public void registerTransfer(BatteryTransfer batteryTransfer) {

		batteryTransferRepository.save(batteryTransfer);
	}
	
	@Override
	public List<BatteryTransferDto> getBatteryTransferListToReceive(CustomUserDetails	userDetails) throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				" SELECT IT.batteryTransferId, IT.batteryId, INVT.batterySerialNumber, PL.partlocation_name, Pm.partlocation_name, IT.transferQuantity, IT.transferDate, U.email,"
				+ " IT.transferViaId, U2.firstName,IT.receiveRemark, IT.transferStausId, IT.receiveDate,"
				+ " IT.transferReason, IT.batteryInvoiceId, IT.createdOn, IT.lastModifiedOn, IT.receiveById"
				+ " FROM BatteryTransfer AS IT "
				+ " INNER JOIN PartLocations PL ON PL.partlocation_id = IT.fromLocationId"
				+ " INNER JOIN PartLocations Pm ON Pm.partlocation_id = IT.toLocationId"
				+ " INNER JOIN User U ON U.id = IT.transferById"
				+ " INNER JOIN User U2 ON U2.id = IT.receiveById"
				+ " INNER JOIN Battery INVT ON INVT.batteryId = IT.batteryId"
				+ " WHERE IT.receiveById=" + userDetails.getId() + " AND IT.companyId = "+userDetails.getCompany_id()+""
				+ " AND IT.transferStausId = "+InventoryTyreTransferDto.TRANSFER_STATUS_TRANSFER+" AND IT.markForDelete = 0  ORDER BY IT.batteryTransferId desc",
				Object[].class);
		query.setFirstResult(0);
		query.setMaxResults(50);
		List<Object[]> results = query.getResultList();

		List<BatteryTransferDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<BatteryTransferDto>();
			BatteryTransferDto list = null;
			for (Object[] result : results) {
				list = new BatteryTransferDto();

				list.setBatteryTransferId((Long) result[0]);
				list.setBatteryId((Long) result[1]);
				list.setBatterySerialNumber((String) result[2]);
				list.setFromLocationName((String) result[3]);
				list.setToLocationName((String) result[4]);
				list.setTransferQuantity((Integer) result[5]);
				list.setTransferDate((Timestamp) result[6]);
				list.setTransferBy((String) result[7]);
				list.setTransferViaId((short) result[8]);
				list.setTransferVia(InventoryTyreTransferDto.getTransferViaName((short) result[8]));
				list.setReceiveBy((String) result[9]);
				list.setReceiveRemark((String) result[10]);
				list.setTransferStausId((short) result[11]);
				list.setTransferStaus(InventoryTyreTransferDto.getStatusName((short) result[11]));
				list.setReceiveDate((Timestamp) result[12]);
				list.setTransferReason((String) result[13]);
				list.setBatteryInvoiceId((Long) result[14]);
				list.setCreatedOn((Timestamp) result[15]);
				list.setLastModifiedOn((Timestamp) result[16]);
				list.setReceiveById((Long) result[17]);
				
				if(list.getTransferDate() != null)
					 list.setTransferDateStr(DateTimeUtility.getDateFromTimeStamp(list.getTransferDate(), DateTimeUtility.DD_MM_YYYY));
				
				if(list.getReceiveDate() != null)
					 list.setReceiveDateStr(DateTimeUtility.getDateFromTimeStamp(list.getReceiveDate(), DateTimeUtility.DD_MM_YYYY));
				
				if(list.getCreatedOn() != null)
					 list.setCratedOnStr(DateTimeUtility.getDateFromTimeStamp(list.getCreatedOn(), DateTimeUtility.DD_MM_YYYY));
				
				if(list.getLastModifiedOn() != null)
					 list.setLastModifiedOnStr(DateTimeUtility.getDateFromTimeStamp(list.getLastModifiedOn(), DateTimeUtility.DD_MM_YYYY));
				
				Dtos.add(list);
			}
		}

		return Dtos;
	}
	
	@Override
	public BatteryTransfer getBatteryTransfer(Long transferId, Integer companyId) throws Exception {
		
		return batteryTransferRepository.getBatteryTransfer(transferId, companyId);
	}
	
	@Override
	@Transactional
	public void Update_BatteryTransfer_ReceivedBy_Details(Timestamp transfer_receiveddate,
			String transfer_receivedReason, Long lASTMODIFIEDBY, Timestamp transfer_receiveddate2, Long ittid,
			Integer companyid) {
		
		batteryTransferRepository.update_BatteryTransfer_ReceivedBy_Details(transfer_receiveddate, transfer_receivedReason, lASTMODIFIEDBY, transfer_receiveddate2, ittid, companyid);
	}

	@Override
	public ValueObject getBatteryTransferReport(ValueObject valueObject) throws Exception {
		
		ArrayList<BatteryTransferDto>		batteryTransferList				= null;
		BatteryTransferDto					batteryTransfer					= null;
		ValueObject							valOutObject					= null;
		CustomUserDetails					userDetails						= null;
		TypedQuery<Object[]> 				typedQuery						= null;
		List<Object[]> 						results 						= null;
		String								fromDate						= null;
		String								toDate							= null;
		ValueObject							dateRangeObj					= null;
		String 								fromLocation 					= "";		
		String 								toLocation 						= "";		
		String 								transferStatus 					= "";		
		long								fromLocationId					= 0;
		long								toLocationId					= 0;
		short								transferStatusId				= 0;
		
		try {
			
			fromLocationId			= valueObject.getLong("fromLocationId");
			toLocationId			= valueObject.getLong("toLocationId");
			transferStatusId		= valueObject.getShort("transferStatusId");
			
			dateRangeObj			= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
			fromDate				= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate					= dateRangeObj.getString(DateTimeUtility.TO_DATE);
			fromDate = DateTimeUtility.getSqlStrDateFromStrDate(fromDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			toDate   = DateTimeUtility.getSqlStrDateFromStrDate(toDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if (fromLocationId > 0) {
				fromLocation = "AND IT.fromLocationId =" + fromLocationId + " ";
			}

			if (toLocationId > 0) {
				toLocation = "AND IT.toLocationId =" + toLocationId + " ";
			}

			if (transferStatusId > 0 ) {
				transferStatus = "AND IT.transferStausId=" + transferStatusId + "";
			}
			
			String query = "" + fromLocation + " " + toLocation + " " + transferStatus + " ";
			
			typedQuery = entityManager.createQuery(
					" SELECT IT.batteryTransferId, IT.batteryId, INVT.batterySerialNumber, PL.partlocation_name, Pm.partlocation_name, IT.transferQuantity, IT.transferDate, U.email,"
					+ " IT.transferViaId, U2.firstName,IT.receiveRemark, IT.transferStausId, IT.receiveDate,"
					+ " IT.transferReason, IT.batteryInvoiceId, IT.createdOn, IT.lastModifiedOn, IT.receiveById"
					+ " FROM BatteryTransfer AS IT "
					+ " INNER JOIN PartLocations PL ON PL.partlocation_id = IT.fromLocationId"
					+ " INNER JOIN PartLocations Pm ON Pm.partlocation_id = IT.toLocationId"
					+ " LEFT JOIN User U ON U.id = IT.transferById"
					+ " LEFT JOIN User U2 ON U2.id = IT.receiveById"
					+ " INNER JOIN Battery INVT ON INVT.batteryId = IT.batteryId"
					+ " WHERE IT.transferDate BETWEEN '"+ fromDate +"' AND '" + toDate + "' " + query
					+ " AND IT.companyId = "+userDetails.getCompany_id()  
					+ " AND IT.markForDelete = 0  ORDER BY IT.batteryTransferId desc",
					Object[].class);
			
			results = typedQuery.getResultList();

			if (results != null && !results.isEmpty()) {
				batteryTransferList = new ArrayList<BatteryTransferDto>();
				for (Object[] result : results) {
					batteryTransfer = new BatteryTransferDto();

					batteryTransfer.setBatteryTransferId((Long) result[0]);
					batteryTransfer.setBatteryId((Long) result[1]);
					batteryTransfer.setBatterySerialNumber((String) result[2]);
					batteryTransfer.setFromLocationName((String) result[3]);
					batteryTransfer.setToLocationName((String) result[4]);
					batteryTransfer.setTransferQuantity((Integer) result[5]);
					batteryTransfer.setTransferDate((Timestamp) result[6]);
					batteryTransfer.setTransferBy((String) result[7]);
					batteryTransfer.setTransferViaId((short) result[8]);
					batteryTransfer.setTransferVia(InventoryTyreTransferDto.getTransferViaName((short) result[8]));
					if(result[9] != null) {
						batteryTransfer.setReceiveBy((String) result[9]);						
					} else {
						batteryTransfer.setReceiveBy("--");												
					}
					batteryTransfer.setReceiveRemark((String) result[10]);
					batteryTransfer.setTransferStausId((short) result[11]);
					batteryTransfer.setTransferStaus(InventoryTyreTransferDto.getStatusName((short) result[11]));
					batteryTransfer.setReceiveDate((Timestamp) result[12]);
					batteryTransfer.setTransferReason((String) result[13]);
					batteryTransfer.setBatteryInvoiceId((Long) result[14]);
					batteryTransfer.setCreatedOn((Timestamp) result[15]);
					batteryTransfer.setLastModifiedOn((Timestamp) result[16]);
					batteryTransfer.setReceiveById((Long) result[17]);
					
					if(batteryTransfer.getTransferDate() != null) 
						batteryTransfer.setTransferDateStr(DateTimeUtility.getDateFromTimeStamp(batteryTransfer.getTransferDate(), DateTimeUtility.DD_MM_YYYY));						
					
					if(batteryTransfer.getReceiveDate() != null) {
						batteryTransfer.setReceiveDateStr(DateTimeUtility.getDateFromTimeStamp(batteryTransfer.getReceiveDate(), DateTimeUtility.DD_MM_YYYY));						
					} else {
						batteryTransfer.setReceiveDateStr("--");												
					}
					
					if(batteryTransfer.getCreatedOn() != null)
						batteryTransfer.setCratedOnStr(DateTimeUtility.getDateFromTimeStamp(batteryTransfer.getCreatedOn(), DateTimeUtility.DD_MM_YYYY));
					
					if(batteryTransfer.getLastModifiedOn() != null)
						batteryTransfer.setLastModifiedOnStr(DateTimeUtility.getDateFromTimeStamp(batteryTransfer.getLastModifiedOn(), DateTimeUtility.DD_MM_YYYY));
					
					batteryTransferList.add(batteryTransfer);
				}
			}
						
			valOutObject		= new ValueObject();
			valOutObject.put("BatteryTransferList", batteryTransferList);
			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			batteryTransferList				= null;
			batteryTransfer					= null;
			valOutObject					= null;
			userDetails						= null;
			typedQuery						= null;
			results 						= null;
			fromDate						= null;
			toDate							= null;
			dateRangeObj					= null;
			fromLocation 					= "";	
			toLocation 						= "";	
			transferStatus 					= "";	
			fromLocationId					= 0;   
			toLocationId					= 0;   
			transferStatusId				= 0;   
		}
	}
	
}
