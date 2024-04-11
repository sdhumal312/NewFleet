package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.VehicleOdometerConstant;
import org.fleetopgroup.persistence.dao.DealerServiceEntriesRepository;
import org.fleetopgroup.persistence.dao.FuelRepository;
import org.fleetopgroup.persistence.dao.InventoryTyreRepository;
import org.fleetopgroup.persistence.dao.IssuesRepository;
import org.fleetopgroup.persistence.dao.ServiceEntriesRepository;
import org.fleetopgroup.persistence.dao.ServiceReminderRepository;
import org.fleetopgroup.persistence.dao.TripSheetRepository;
import org.fleetopgroup.persistence.dao.VehicleOdometerHistoryRepository;
import org.fleetopgroup.persistence.dao.WorkOrdersRepository;
import org.fleetopgroup.persistence.dto.VehicleOdometerHistoryDto;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service("VehicleOdometerHistoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VehicleOdometerHistoryService implements IVehicleOdometerHistoryService {
	
	
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private VehicleOdometerHistoryRepository vehicleOdometerHistoryDao;
	
	@Autowired	IVehicleService		vehicleService;
	
	@Autowired private ServiceReminderRepository ServiceReminderDao;
	
	//@Autowired private org.fleetopgroup.persistence.dao.WorkOrdersRepository WorkOrdersRepository;
	@Autowired private WorkOrdersRepository workOrdersRepository;
	@Autowired private FuelRepository fuelRepository;
	@Autowired private IssuesRepository issuesRepository;
	@Autowired private InventoryTyreRepository inventoryTyreRepository;
	@Autowired private DealerServiceEntriesRepository dealerServiceEntriesRepository;
	@Autowired private ServiceEntriesRepository ServiceEntriesRepository;
	@Autowired private TripSheetRepository tripSheetRepository;
	
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addVehicleOdometerHistory(VehicleOdometerHistory status) throws Exception {
		
		vehicleOdometerHistoryDao.save(status);
		
		updateOdometerThreshHoldToServiceReminder(status);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateOdometerThreshHoldToServiceReminder(VehicleOdometerHistory status) throws Exception {
		    		
					ServiceReminderDao.updateOdometerThreshHoldToServiceReminder(status.getVehicle_Odometer(), status.getVid(), new Date());
		
	}
	
	@Override
	public VehicleOdometerHistory getMonthStartOdoMeterOfVehicle(Integer vid, Timestamp date) throws Exception {
		
		return vehicleOdometerHistoryDao.getMonthStartOdoMeterOfVehicle(vid, date);
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateVehicleOdometerHistory(VehicleOdometerHistory status) throws Exception {
		vehicleOdometerHistoryDao.save(status);
	}

	@Transactional
	public List<VehicleOdometerHistory> listVehicleOdometerHistory() throws Exception {
		
		return vehicleOdometerHistoryDao.listVehicleOdometerHistory();
	}

	
	@Transactional
	public List<VehicleOdometerHistoryDto> listVehicleOdometerHistory(Integer vid, Integer companyId) throws Exception {
		
		TypedQuery<Object[]> query = entityManager
				.createQuery("SELECT VH.voh_id, VH.vid, VH.voh_date, VH.vehicle_Odometer, VH.voh_updatelocation, VH.voh_updateId, V.vehicle_registration"
						+ " from  VehicleOdometerHistory VH"
						+ " INNER JOIN Vehicle V ON V.vid = VH.vid "
						+ " where VH.vid=" + vid + " AND VH.companyId = "+companyId+" AND VH.markForDelete = 0 ORDER BY VH.voh_id desc", Object[].class);
		query.setFirstResult(0);
		query.setMaxResults(100);
		List<Object[]> results = query.getResultList();

		List<VehicleOdometerHistoryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleOdometerHistoryDto>();
			VehicleOdometerHistoryDto list = null;
			for (Object[] result : results) {
				list = new VehicleOdometerHistoryDto();
				
				list.setVoh_id((Long) result[0]);
				list.setVid((Integer) result[1]);
				list.setVoh_date_On((Date) result[2]);
				list.setVehicle_Odometer((Integer) result[3]);
				list.setVoh_updatelocation((String) result[4]);
				list.setVoh_updateId((Long) result[5]);
				list.setVoh_updateNumber(getVehicleOdometerHistoryNumber((Long)result[5],(String)result[4], companyId));
				list.setVehicle_registration((String) result[6]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	@Transactional
	public String getVehicleOdometerHistoryNumber(Long voh_updateId, String voh_updatelocation, Integer companyId) throws Exception{
	    
	    switch (voh_updatelocation) {

	        case VehicleOdometerConstant.WORKORDER_ENTRY_NAME:
	            return ""+ workOrdersRepository.getWorkOrders_Number(companyId, voh_updateId);
	        case VehicleOdometerConstant.FUEL_ENTRY_NAME:
	        	return "" + fuelRepository.getFuel_Number(companyId, voh_updateId);
	        case VehicleOdometerConstant.ISSUES_ENTRY_NAME:
	        	return "" + issuesRepository.getIssues_Number(companyId, voh_updateId);
	        case VehicleOdometerConstant.TYRE_ENTRY_NAME:
	        	return inventoryTyreRepository.getTyre_Number(companyId, voh_updateId);
	        case VehicleOdometerConstant.DEALER_SERVICE_ENTRY_NAME:
	        	return "" + dealerServiceEntriesRepository.getDSE_Number(companyId, voh_updateId);
	        case VehicleOdometerConstant.SERVICE_ENTRY_NAME:
	        	return "" + ServiceEntriesRepository.getSE_Number(companyId, voh_updateId);	
	        	
	        default:
	        	return "" + tripSheetRepository.getTripSheet_Number(companyId,voh_updateId);
	    }
	    
	}
	
	
	@Transactional
	public List<VehicleOdometerHistory> listVehicleOdometerGraph(Integer vid) throws Exception {
		
		//return vehicleOdometerHistoryDao.listVehicleOdometerGraph(vid);
		
		TypedQuery<VehicleOdometerHistory> query = entityManager
				.createQuery("from VehicleOdometerHistory where vid=" + vid + " ORDER BY voh_id desc", VehicleOdometerHistory.class);
		query.setFirstResult(0);
		query.setMaxResults(4);
		return query.getResultList();
	}

	@Transactional
	public void updateVehicleOdometerHistory(Integer odometer, Long voh_updateId) throws Exception {
		
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		
		vehicleOdometerHistoryDao.updateVehicleOdometerHistory(odometer,toDate, voh_updateId);
	}

	@Override
	public VehicleOdometerHistory getOdoMeterAtSpecifiedTime(Integer vid, Timestamp date) throws Exception {

		TypedQuery<VehicleOdometerHistory> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM VehicleOdometerHistory AS T"
						+ " where T.vid = '"+vid+"' AND T.voh_date <= '"+date+"' ORDER BY T.voh_id DESC ", VehicleOdometerHistory.class);

		VehicleOdometerHistory	dateWiseVehicleExpense = null;
		try {
			query.setMaxResults(1);
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			
			try {
				TypedQuery<VehicleOdometerHistory> query2 = entityManager.createQuery(
						"SELECT T "
						+ " FROM VehicleOdometerHistory AS T"
								+ " where T.vid = '"+vid+"' AND T.voh_date >= '"+date+"' ORDER BY T.voh_id ASC", VehicleOdometerHistory.class);
				query2.setMaxResults(1);
				dateWiseVehicleExpense = query2.getSingleResult();
				
			} catch (NoResultException nre2) {
				
			}
		}

		return dateWiseVehicleExpense;
	}
	
	@Override
	public VehicleOdometerHistory getLastOdometerHistory(Integer vid, Integer odometer) throws Exception {

		TypedQuery<VehicleOdometerHistory> query = entityManager.createQuery(
				"SELECT T "
				+ " FROM VehicleOdometerHistory AS T"
						+ " where T.vid = '"+vid+"' AND T.vehicle_Odometer < '"+odometer+"' AND markForDelete = 0 ORDER BY T.voh_id DESC ", VehicleOdometerHistory.class);

		VehicleOdometerHistory	dateWiseVehicleExpense = null;
		try {
			query.setMaxResults(1);
			dateWiseVehicleExpense = query.getSingleResult();
			
		} catch (NoResultException nre) {
			
		}

		return dateWiseVehicleExpense;
	}
	@Transactional
	@Override
	public VehicleOdometerHistory getVehicleOdometerHistoryByVidExceptCurrentEntry(Long currentEntryId ,Integer vid, Integer companyId) throws Exception {// currentEntryId == voh_UpdatedId means fuel,SE,Urea.... primaryId
		VehicleOdometerHistory	vehicleOdometerHistory = null;
		try {
			TypedQuery<VehicleOdometerHistory> query = entityManager
	    				.createQuery("FROM VehicleOdometerHistory  where voh_updateId <> "+currentEntryId+"  AND vid = "+vid+" AND companyId = "+companyId+" AND markForDelete = 0 ORDER BY voh_id DESC " , VehicleOdometerHistory.class);
			query.setMaxResults(1);	
			vehicleOdometerHistory = query.getSingleResult();
		    	
			} catch (NoResultException nre) {
				
			}

			return vehicleOdometerHistory;
		
	}
	@Transactional
	@Override
	public void deleteVehicleOdometerHistory(Long voh_updateId, Integer companyId) throws Exception {// if delete some entry then this will update vohUpdateLocationStatusId 
		try {
				vehicleOdometerHistoryDao.deleteVehicleOdometerHistory(voh_updateId,  companyId);
			} catch (Exception e) {
				e.printStackTrace();
			throw e;
			}		
		
	}
	
	@Override
	public void updateVohUpdateLocationStatusIdByVoh_id(short vohUpdateLocationStatusId, Long voh_Id, Integer companyId) throws Exception {// if delete some entry then this will update vohUpdateLocationStatusId 
		try {
				vehicleOdometerHistoryDao.updateVohUpdateLocationStatusIdByVoh_id(vohUpdateLocationStatusId ,voh_Id,  companyId);
			} catch (Exception e) {
				e.printStackTrace();
			throw e;
			}		
		
	}
}
