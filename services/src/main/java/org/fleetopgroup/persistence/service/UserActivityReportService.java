package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.IDealerServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserActivityReportService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("UserActivityReportService")
@Transactional
public class UserActivityReportService implements IUserActivityReportService {
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired IWorkOrdersService workOrdersService;
	
	@Autowired IServiceEntriesService serviceEntriesService;
	
	@Autowired ITripSheetService tripSheetService;
	
	@Autowired IFuelService fuelService;
	
	@Autowired IRenewalReminderService renewalReminderService;
	
	@Autowired IIssuesService issuesService;
	
	@Autowired IPurchaseOrdersService poService;
	
	@Autowired IDealerServiceEntriesService dseService;
	
	
	@Override
	public ValueObject getUSerWiseWOActivityCount(ValueObject object) throws Exception{
		CustomUserDetails			userDetails			= null;
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(object.getLong("userId") == 0) {
				
				Query queryCreate = entityManager.createQuery("SELECT COUNT(*) FROM WorkOrders as w "
						+ "WHERE w.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND w.companyId="+userDetails.getCompany_id() +"");
				
					Long workOrderCount =  (Long) queryCreate.getSingleResult();
					
					object.put("workOrderCreateCount", workOrderCount);		
				
					queryCreate=entityManager.createQuery("SELECT COUNT(*) FROM WorkOrders as w "
							+ "WHERE  w.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND  w.markForDelete=0  AND w.companyId="+userDetails.getCompany_id() +"");
						
					 Long workOrderCount1 =  (Long) queryCreate.getSingleResult();
					object.put("workOrderUpdateCount", workOrderCount1);
					
					
					queryCreate=entityManager.createQuery("SELECT COUNT(*) FROM WorkOrders as w "
							+ "WHERE   w.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND  w.markForDelete=1  AND w.companyId="+userDetails.getCompany_id() +"");
					
					Long workOrderCount2 =  (Long) queryCreate.getSingleResult();
					object.put("workOrderDeleteCount", workOrderCount2);
				
			} else {
				
				Query queryCreate = entityManager.createQuery("SELECT COUNT(*) FROM WorkOrders as w "
						+ "WHERE w.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND w.createdById = "+object.getLong("userId")+"   AND w.companyId="+userDetails.getCompany_id() +"");
				
					Long workOrderCount =  (Long) queryCreate.getSingleResult();
					
					object.put("workOrderCreateCount", workOrderCount);		
				
					queryCreate=entityManager.createQuery("SELECT COUNT(*) FROM WorkOrders as w "
							+ "WHERE  w.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND w.lastModifiedById = "+object.getLong("userId")+"  AND w.markForDelete=0  AND w.companyId="+userDetails.getCompany_id() +"");
						
					 Long workOrderCount1 =  (Long) queryCreate.getSingleResult();
					object.put("workOrderUpdateCount", workOrderCount1);
					
					
					queryCreate=entityManager.createQuery("SELECT COUNT(*) FROM WorkOrders as w "
							+ "WHERE   w.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND w.lastModifiedById = "+object.getLong("userId")+"  AND w.markForDelete=1  AND w.companyId="+userDetails.getCompany_id() +"");
					
					Long workOrderCount2 =  (Long) queryCreate.getSingleResult();
					object.put("workOrderDeleteCount", workOrderCount2);
				
			}
			
			
				
				return object;
			
		} catch (Exception e) {
			throw e;
		}
		
		
	}
	
	@Override
	public ValueObject getUserWiseServiceEntryCount(ValueObject object) throws Exception{
		CustomUserDetails			userDetails			= null;
		try {

			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(object.getLong("userId") == 0) {
				
				Query query = entityManager.createQuery("SELECT COUNT(*) FROM ServiceEntries as s "
						+ "WHERE s.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'    AND s.companyId="+userDetails.getCompany_id() +"");
				
				Long result =  (Long) query.getSingleResult();
				object.put("serviceEntryCreatedCount", result);
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM ServiceEntries as s "
						+ "WHERE s.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND s.markForDelete=0  AND s.companyId="+userDetails.getCompany_id() +"");
				result =  (Long) query.getSingleResult();
				object.put("serviceEntryUpdatedCount", result);
				
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM ServiceEntries as s "
						+ "WHERE s.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND s.markForDelete=1  AND s.companyId="+userDetails.getCompany_id() +"");
				result =  (Long) query.getSingleResult();
				object.put("serviceEntryDeletedCount", result);

				
			}else {
				
				Query query = entityManager.createQuery("SELECT COUNT(*) FROM ServiceEntries as s "
						+ "WHERE s.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND s.createdById = "+object.getLong("userId")+"   AND s.companyId="+userDetails.getCompany_id() +"");
				
				Long result =  (Long) query.getSingleResult();
				object.put("serviceEntryCreatedCount", result);
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM ServiceEntries as s "
						+ "WHERE s.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND s.lastModifiedById = "+object.getLong("userId")+"  AND s.markForDelete=0  AND s.companyId="+userDetails.getCompany_id() +"");
				result =  (Long) query.getSingleResult();
				object.put("serviceEntryUpdatedCount", result);
				
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM ServiceEntries as s "
						+ "WHERE s.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND s.lastModifiedById = "+object.getLong("userId")+"  AND s.markForDelete=1  AND s.companyId="+userDetails.getCompany_id() +"");
				result =  (Long) query.getSingleResult();
				object.put("serviceEntryDeletedCount", result);
			}
			
				
				return object;
			
		} catch (Exception e) {
			throw e;
		}
		
		
	}

	@Override
	public ValueObject getUserWiseTripSheetActivityCount(ValueObject object) throws Exception {
		CustomUserDetails			userDetails			= null;
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(object.getLong("userId") == 0) {
				
				Query query = entityManager.createQuery("SELECT COUNT(*) FROM TripSheet as t "
						+ "WHERE t.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND t.companyId="+userDetails.getCompany_id() +"");
				Long result =  (Long) query.getSingleResult();
				object.put("tripSheetCreateCount", result);	
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM TripSheet as t "
						+ "WHERE t.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'  AND t.markForDelete=0 AND t.companyId="+userDetails.getCompany_id() +"");
				
				result =  (Long) query.getSingleResult();
				object.put("tripSheetUpdatedCount", result);	
				
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM TripSheet as t "
						+ "WHERE t.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'  AND t.markForDelete=1 AND t.companyId="+userDetails.getCompany_id() +"");
				
				result =  (Long) query.getSingleResult();
				object.put("tripSheetDeletedCount", result);
				
			}else {
				
				
				Query query = entityManager.createQuery("SELECT COUNT(*) FROM TripSheet as t "
						+ "WHERE t.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND t.createdById = "+object.getLong("userId")+"  AND t.companyId="+userDetails.getCompany_id() +"");
				Long result =  (Long) query.getSingleResult();
				object.put("tripSheetCreateCount", result);	
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM TripSheet as t "
						+ "WHERE t.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND t.lastModifiedById = "+object.getLong("userId")+"  AND t.markForDelete=0 AND t.companyId="+userDetails.getCompany_id() +"");
				
				result =  (Long) query.getSingleResult();
				object.put("tripSheetUpdatedCount", result);	
				
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM TripSheet as t "
						+ "WHERE t.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND t.lastModifiedById = "+object.getLong("userId")+"  AND t.markForDelete=1 AND t.companyId="+userDetails.getCompany_id() +"");
				
				result =  (Long) query.getSingleResult();
				object.put("tripSheetDeletedCount", result);
				
			}
				
				
				return object;
			
		} catch (Exception e) {
			throw e;
		}
		
		
	}
	
	@Override
	public ValueObject getUserWiseFuelEntryActivityCount(ValueObject object) throws Exception {
		CustomUserDetails			userDetails			= null;
		try {

			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			if(object.getLong("userId") == 0) {
				
				
				Query query = entityManager.createQuery("SELECT COUNT(*) FROM Fuel as f "
						+ "WHERE f.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND f.companyId="+userDetails.getCompany_id() +"");
				
				Long result =  (Long) query.getSingleResult();
				object.put("fuelEntryCreatedCount", result);	
				
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM Fuel as f "
						+ "WHERE f.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'  AND f.markForDelete=0 AND f.companyId="+userDetails.getCompany_id() +"");
				
				result =  (Long) query.getSingleResult();
				object.put("fuelEntryUpdatedCount", result);
				
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM Fuel as f "
						+ "WHERE f.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'  AND f.markForDelete=1 AND f.companyId="+userDetails.getCompany_id() +"");
				
				result =  (Long) query.getSingleResult();
				object.put("fuelEntryDeletedCount", result);

				
			}else {
				
				Query query = entityManager.createQuery("SELECT COUNT(*) FROM Fuel as f "
						+ "WHERE f.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND f.createdById = "+object.getLong("userId")+"   AND f.companyId="+userDetails.getCompany_id() +"");
				
				Long result =  (Long) query.getSingleResult();
				object.put("fuelEntryCreatedCount", result);	
				
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM Fuel as f "
						+ "WHERE f.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND f.lastModifiedById = "+object.getLong("userId")+"  AND f.markForDelete=0 AND f.companyId="+userDetails.getCompany_id() +"");
				
				result =  (Long) query.getSingleResult();
				object.put("fuelEntryUpdatedCount", result);
				
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM Fuel as f "
						+ "WHERE f.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND f.lastModifiedById = "+object.getLong("userId")+"  AND f.markForDelete=1 AND f.companyId="+userDetails.getCompany_id() +"");
				
				result =  (Long) query.getSingleResult();
				object.put("fuelEntryDeletedCount", result);
			}

			return object;

		} catch (Exception e) {
			throw e;
		}
		
		
	}
	
	@Override
	public ValueObject	getUserWiseRRActivityCount(ValueObject object) throws Exception {
	
	CustomUserDetails			userDetails			= null;
	try {

		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(object.getLong("userId") == 0) {
			
			Query query = entityManager.createQuery("SELECT COUNT(*) FROM RenewalReminder as r "
					+ "WHERE r.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND r.companyId="+userDetails.getCompany_id() +"");
			
			Long result =  (Long) query.getSingleResult();
			object.put("rrCreatedCount", result);	
			
			
			query = entityManager.createQuery("SELECT COUNT(*) FROM RenewalReminder as r "
					+ "WHERE r.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND r.markForDelete=0 AND r.companyId="+userDetails.getCompany_id() +"");
			
			result =  (Long) query.getSingleResult();
			object.put("rrUpdatedCount", result);
			
			
			query = entityManager.createQuery("SELECT COUNT(*) FROM RenewalReminder as r "
					+ "WHERE r.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND r.markForDelete=1 AND r.companyId="+userDetails.getCompany_id() +"");
			
			result =  (Long) query.getSingleResult();
			object.put("rrDeletedCount", result);
			
		}else {
			
			Query query = entityManager.createQuery("SELECT COUNT(*) FROM RenewalReminder as r "
					+ "WHERE r.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND r.createdById = "+object.getLong("userId")+"   AND r.companyId="+userDetails.getCompany_id() +"");
			
			Long result =  (Long) query.getSingleResult();
			object.put("rrCreatedCount", result);	
			
			
			query = entityManager.createQuery("SELECT COUNT(*) FROM RenewalReminder as r "
					+ "WHERE r.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND r.lastModifiedById = "+object.getLong("userId")+"  AND r.markForDelete=0 AND r.companyId="+userDetails.getCompany_id() +"");
			
			result =  (Long) query.getSingleResult();
			object.put("rrUpdatedCount", result);
			
			
			query = entityManager.createQuery("SELECT COUNT(*) FROM RenewalReminder as r "
					+ "WHERE r.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND r.lastModifiedById = "+object.getLong("userId")+"  AND r.markForDelete=1 AND r.companyId="+userDetails.getCompany_id() +"");
			
			result =  (Long) query.getSingleResult();
			object.put("rrDeletedCount", result);
			
		}
		
		return object;

	} catch (Exception e) {
		throw e;
	}
	
}
	@Override
	public ValueObject getUserWiseIssuesCount(ValueObject object) throws Exception {
		CustomUserDetails			userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(object.getLong("userId") == 0) {
				
				
				Query query = entityManager.createQuery("SELECT COUNT(*) FROM Issues as I "
						+ "WHERE I.CREATED_DATE between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND I.COMPANY_ID="+userDetails.getCompany_id() +"");
				
				Long result =  (Long) query.getSingleResult();
				object.put("issuesCreatedCount", result);	
				
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM Issues as I "
						+ "WHERE I.LASTUPDATED_DATE between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND I.markForDelete=0 AND I.COMPANY_ID="+userDetails.getCompany_id() +"");
				
				result =  (Long) query.getSingleResult();
				object.put("issuesUpdatedCount", result);
				
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM Issues as I "
						+ "WHERE I.LASTUPDATED_DATE between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND I.markForDelete=1 AND I.COMPANY_ID="+userDetails.getCompany_id() +"");
				
				result =  (Long) query.getSingleResult();
				object.put("issuesDeletedCount", result);

				
			}else {
				
				
				Query query = entityManager.createQuery("SELECT COUNT(*) FROM Issues as I "
						+ "WHERE I.CREATED_DATE between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND I.CREATEDBYID = "+object.getLong("userId")+"  AND I.COMPANY_ID="+userDetails.getCompany_id() +"");
				
				Long result =  (Long) query.getSingleResult();
				object.put("issuesCreatedCount", result);	
				
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM Issues as I "
						+ "WHERE I.LASTUPDATED_DATE between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND I.LASTMODIFIEDBYID = "+object.getLong("userId")+"  AND I.markForDelete=0 AND I.COMPANY_ID="+userDetails.getCompany_id() +"");
				
				result =  (Long) query.getSingleResult();
				object.put("issuesUpdatedCount", result);
				
				
				query = entityManager.createQuery("SELECT COUNT(*) FROM Issues as I "
						+ "WHERE I.LASTUPDATED_DATE between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND I.LASTMODIFIEDBYID = "+object.getLong("userId")+"  AND I.markForDelete=1 AND I.COMPANY_ID="+userDetails.getCompany_id() +"");
				
				result =  (Long) query.getSingleResult();
				object.put("issuesDeletedCount", result);
			}
			return object;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject	getActivityWOData(ValueObject object) throws Exception {
		String query = "";
		String innerQuery =" ";
		CustomUserDetails			userDetails			= null;
		ValueObject 				tableConfig 		= null;
		try {
			
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(object.getInt("activityType") == 1) {
				if(object.getLong("userId") == 0) {
					query="WHERE w.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND w.companyId="+userDetails.getCompany_id() +"";	
					innerQuery = " INNER JOIN User AS U ON U.id = w.createdById ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_WO_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					
					object.put("tableConfig", tableConfig.getHtData());
				
				}else {
					query="WHERE w.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND w.createdById = "+object.getLong("userId")+"   AND w.companyId="+userDetails.getCompany_id() +"";	
					innerQuery = " INNER JOIN User AS U ON U.id = w.createdById ";
				}
				object.put("woCreatedData", workOrdersService.getUserWiseWOActivityList(query, innerQuery));
			}else if (object.getInt("activityType") == 2) {
				if(object.getLong("userId") == 0) {
					query="WHERE w.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'  AND w.markForDelete=0  AND w.companyId="+userDetails.getCompany_id() +"";
					innerQuery = " INNER JOIN User AS U ON U.id = w.lastModifiedById ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_WO_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					
					object.put("tableConfig", tableConfig.getHtData());
				}else {
					innerQuery = " INNER JOIN User AS U ON U.id = w.lastModifiedById ";
					query="WHERE w.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND w.lastModifiedById = "+object.getLong("userId")+"  AND w.markForDelete=0  AND w.companyId="+userDetails.getCompany_id() +"";
				}
				object.put("woUpdatedData", workOrdersService.getUserWiseWOActivityList(query, innerQuery));
			}else if (object.getInt("activityType") == 3) {
				if(object.getLong("userId") == 0) {
					query ="WHERE w.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND w.markForDelete = 1  AND w.companyId="+userDetails.getCompany_id() +"";
					innerQuery = " INNER JOIN User AS U ON U.id = w.lastModifiedById ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_WO_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					
					object.put("tableConfig", tableConfig.getHtData());
				}else {
					innerQuery = " INNER JOIN User AS U ON U.id = w.lastModifiedById ";
					query ="WHERE w.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND w.lastModifiedById = "+object.getLong("userId")+"  AND w.markForDelete = 1  AND w.companyId="+userDetails.getCompany_id() +"";
				}
				object.put("woDeletedData", workOrdersService.getUserWiseWOActivityList(query, innerQuery));
			}
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public ValueObject	getActivitySEData(ValueObject object) throws Exception {
		String query = "";
		String innerQuery =" ";
		CustomUserDetails			userDetails			= null;
		ValueObject 				tableConfig 		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(object.getInt("activityType") == 1) {
				
				if(object.getLong("userId") == 0) {
					query="WHERE s.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND s.companyId="+userDetails.getCompany_id() +"";	
					innerQuery = " INNER JOIN User AS U ON U.id = s.createdById  ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_SE_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());
					object.put("seCreatedData", serviceEntriesService.getUserWiseSEActivityList(query,innerQuery));
				}else {
					query="WHERE s.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND s.createdById = "+object.getLong("userId")+"   AND s.companyId="+userDetails.getCompany_id() +"";
					innerQuery = " INNER JOIN User AS U ON U.id = s.createdById  ";
					object.put("seCreatedData", serviceEntriesService.getUserWiseSEActivityList(query,innerQuery));
				}
				
				
			
			}else if (object.getInt("activityType") == 2) {
				if(object.getLong("userId") == 0) {
						query ="WHERE s.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND s.markForDelete=0  AND s.companyId="+userDetails.getCompany_id() +"";	
						innerQuery = " INNER JOIN User AS U ON U.id = s.lastModifiedById  ";
						tableConfig				= new ValueObject();
						tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_SE_ACTIVITY_REPORT_DATA_FILE_PATH);
						tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
						tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
						object.put("tableConfig", tableConfig.getHtData());
						object.put("seUpdatedData", serviceEntriesService.getUserWiseSEActivityList(query,innerQuery));
				}else {
						innerQuery = " INNER JOIN User AS U ON U.id = s.lastModifiedById  ";
						query ="WHERE s.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND s.lastModifiedById = "+object.getLong("userId")+"  AND s.markForDelete=0  AND s.companyId="+userDetails.getCompany_id() +"";
						object.put("seUpdatedData", serviceEntriesService.getUserWiseSEActivityList(query,innerQuery));
				}
			}else if (object.getInt("activityType") == 3) {
				
				if(object.getLong("userId") == 0) {
					query ="WHERE s.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'  AND s.markForDelete=1  AND s.companyId="+userDetails.getCompany_id() +"";	
					innerQuery = " INNER JOIN User AS U ON U.id = s.lastModifiedById  ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_SE_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());
					object.put("seDeletedData", serviceEntriesService.getUserWiseSEActivityList(query,innerQuery));
			}else {
				innerQuery = " INNER JOIN User AS U ON U.id = s.lastModifiedById  ";
				query ="WHERE s.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND s.lastModifiedById = "+object.getLong("userId")+"  AND s.markForDelete=1  AND s.companyId="+userDetails.getCompany_id() +"";
				object.put("seDeletedData", serviceEntriesService.getUserWiseSEActivityList(query,innerQuery));
				}
			}
			return object;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	@Override
	public ValueObject	getActivityTSData(ValueObject object) throws Exception {
		
		String query = "";
		String innerQuery =" ";
		CustomUserDetails			userDetails			= null;
		ValueObject 				tableConfig 		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(object.getInt("activityType") == 1) {
				if(object.getLong("userId") == 0) {
					query="WHERE TS.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'  AND TS.companyId="+userDetails.getCompany_id() +" ";	
					innerQuery = " INNER JOIN User AS U ON U.id = TS.createdById  ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_TS_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());
					object.put("tsCreatedData", tripSheetService.getUserWiseTSActivityList(query,innerQuery));
				}else {
					query="WHERE TS.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND TS.createdById = "+object.getLong("userId")+"  AND TS.companyId="+userDetails.getCompany_id() +" ";
					innerQuery = " INNER JOIN User AS U ON U.id = TS.createdById  ";
					object.put("tsCreatedData", tripSheetService.getUserWiseTSActivityList(query,innerQuery));

				}
			}else if (object.getInt("activityType") == 2) {
				
				if(object.getLong("userId") == 0) {
					query = " WHERE TS.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'  AND TS.markForDelete=0 AND TS.companyId="+userDetails.getCompany_id() +" ";	
					innerQuery = " INNER JOIN User AS U ON U.id = TS.lastModifiedById  ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_TS_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());
					object.put("tsUpdatedData", tripSheetService.getUserWiseTSActivityList(query,innerQuery));
				}else {

					query = " WHERE TS.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND TS.lastModifiedById = "+object.getLong("userId")+"  AND TS.markForDelete=0 AND TS.companyId="+userDetails.getCompany_id() +" ";
					innerQuery = " INNER JOIN User AS U ON U.id = TS.lastModifiedById  ";
					object.put("tsUpdatedData", tripSheetService.getUserWiseTSActivityList(query,innerQuery));
				}
				}else if (object.getInt("activityType") == 3) {
					if(object.getLong("userId") == 0) {
						query =" WHERE TS.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND TS.markForDelete=1 AND TS.companyId="+userDetails.getCompany_id() +" ";	
						innerQuery = " INNER JOIN User AS U ON U.id = TS.lastModifiedById  ";
						tableConfig				= new ValueObject();
						tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_TS_ACTIVITY_REPORT_DATA_FILE_PATH);
						tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
						tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
						object.put("tableConfig", tableConfig.getHtData());
						object.put("tsDeletedData", tripSheetService.getUserWiseTSActivityList(query,innerQuery));
					}else {	
						query =" WHERE TS.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND TS.lastModifiedById = "+object.getLong("userId")+"  AND TS.markForDelete=1 AND TS.companyId="+userDetails.getCompany_id() +"";
						innerQuery = " INNER JOIN User AS U ON U.id = TS.lastModifiedById  ";
						object.put("tsDeletedData", tripSheetService.getUserWiseTSActivityList(query,innerQuery));
					}
					}
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public ValueObject	getActivityFEData(ValueObject object) throws Exception {
		String query = "";
		String innerQuery =" ";
		CustomUserDetails			userDetails			= null;
		ValueObject 				tableConfig 		= null;
		try {
			userDetails =(CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(object.getInt("activityType") == 1) {
				
				if(object.getLong("userId") == 0) {
					query=" F.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' "
							+ "  AND F.companyId="+userDetails.getCompany_id() +" ";	
					innerQuery = " INNER JOIN User AS U ON U.id = F.createdById  ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_FE_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());
					object.put("feCreatedData", fuelService.getUserWiseFEActivityList(query,innerQuery));
				}else {
				
				query=" F.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' "
						+ "AND F.createdById = "+object.getLong("userId")+"   AND F.companyId="+userDetails.getCompany_id() +" ";
				innerQuery = " INNER JOIN User AS U ON U.id = F.createdById  ";
				object.put("feCreatedData", fuelService.getUserWiseFEActivityList(query,innerQuery));
				}
			}else if (object.getInt("activityType") == 2) {
				
				if(object.getLong("userId") == 0) {
					query=" F.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' "
							+ " AND F.markForDelete=0 AND F.companyId="+userDetails.getCompany_id() +" ";
					innerQuery = " INNER JOIN User AS U ON U.id = F.lastModifiedById  ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_FE_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());
					object.put("feUpdatedData", fuelService.getUserWiseFEActivityList(query,innerQuery));
				}else {
				
				query=" F.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' "
						+ "AND F.lastModifiedById = "+object.getLong("userId")+"  AND F.markForDelete=0 AND F.companyId="+userDetails.getCompany_id() +" ";
				innerQuery = " INNER JOIN User AS U ON U.id = F.lastModifiedById  ";
				object.put("feUpdatedData", fuelService.getUserWiseFEActivityList(query,innerQuery));
				}
			}else if (object.getInt("activityType") == 3) {
				
				if(object.getLong("userId") == 0) {
					query=" F.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' "
							+ " AND F.markForDelete=1 AND F.companyId="+userDetails.getCompany_id() +" ";
					innerQuery = " INNER JOIN User AS U ON U.id = F.lastModifiedById  ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_FE_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());
					object.put("feDeletedData", fuelService.getUserWiseFEActivityList(query,innerQuery));
				}else {
				
				query=" F.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' "
						+ "AND F.lastModifiedById = "+object.getLong("userId")+"  AND F.markForDelete=1 AND F.companyId="+userDetails.getCompany_id() +" ";
				innerQuery = " INNER JOIN User AS U ON U.id = F.lastModifiedById  ";
				object.put("feDeletedData", fuelService.getUserWiseFEActivityList(query,innerQuery));
				}
			}
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject	getActivityRRData(ValueObject object) throws Exception {
		String query = "";
		String innerQuery =" ";
		CustomUserDetails			userDetails			= null;
		ValueObject 				tableConfig 		= null;
		try {
			userDetails =(CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(object.getInt("activityType") == 1) {
				
				if(object.getLong("userId") == 0) {
					query="  R.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'  AND R.companyId="+userDetails.getCompany_id() +" ";
					innerQuery = " INNER JOIN User AS U ON U.id = R.createdById  ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_RR_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());
					object.put("rrCreatedData", renewalReminderService.getUserWiseActivityRRData(query,innerQuery));
				}else {
					innerQuery = " INNER JOIN User AS U ON U.id = R.createdById  ";
				query="  R.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND R.createdById = "+object.getLong("userId")+"   AND R.companyId="+userDetails.getCompany_id() +" ";
				object.put("rrCreatedData", renewalReminderService.getUserWiseActivityRRData(query,innerQuery));
				}
			}else if (object.getInt("activityType") == 2) {
				
				if(object.getLong("userId") == 0) {
					query= " R.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND R.markForDelete=0 AND R.companyId="+userDetails.getCompany_id() +"";
					innerQuery = " INNER JOIN User AS U ON U.id = R.lastModifiedById  ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_RR_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());
					object.put("rrUpdatedData", renewalReminderService.getUserWiseActivityRRData(query,innerQuery));
				}else {
					innerQuery = " INNER JOIN User AS U ON U.id = R.lastModifiedById  ";
					query= " R.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND R.lastModifiedById = "+object.getLong("userId")+"  AND R.markForDelete=0 AND R.companyId="+userDetails.getCompany_id() +"";
					object.put("rrUpdatedData", renewalReminderService.getUserWiseActivityRRData(query,innerQuery));
					}
			}else if (object.getInt("activityType") == 3) {
				
				
				if(object.getLong("userId") == 0) {
					query= " R.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'   AND R.markForDelete=1 AND R.companyId="+userDetails.getCompany_id() +"";
					innerQuery = " INNER JOIN User AS U ON U.id = R.lastModifiedById  ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_RR_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());
					object.put("rrDeletedData", renewalReminderService.getUserWiseActivityRRData(query,innerQuery));
				}else {
					innerQuery = " INNER JOIN User AS U ON U.id = R.lastModifiedById  ";
					query= " R.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND R.lastModifiedById = "+object.getLong("userId")+"  AND R.markForDelete=1 AND R.companyId="+userDetails.getCompany_id() +"";
					object.put("rrDeletedData", renewalReminderService.getUserWiseActivityRRData(query,innerQuery));
				}
			}
			return object;
			
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public ValueObject getActivityIssuesData(ValueObject object) throws Exception {

		String query = "";
		String innerQuery =" ";
		CustomUserDetails			userDetails			= null;
		ValueObject 				tableConfig 		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if(object.getInt("activityType") == 1) {
				
				if(object.getLong("userId") == 0) {
					query = 	" R.CREATED_DATE between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'  AND R.COMPANY_ID="+userDetails.getCompany_id() +"";
					innerQuery = " INNER JOIN User AS U ON U.id = R.CREATEDBYID  ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_ISSUES_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());
					object.put("issuesCreatedData", issuesService.getIssuesActivityList(query,innerQuery));
				}else {
					innerQuery = " INNER JOIN User AS U ON U.id = R.CREATEDBYID  ";
				
					query = 	" R.CREATED_DATE between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND R.CREATEDBYID = "+object.getLong("userId")+" AND R.COMPANY_ID="+userDetails.getCompany_id() +"";
					object.put("issuesCreatedData", issuesService.getIssuesActivityList(query,innerQuery));
				}
			}else if (object.getInt("activityType") == 2) {
				
				if(object.getLong("userId") == 0) {
					query = " R.LASTUPDATED_DATE between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'  AND R.markForDelete=0 AND R.COMPANY_ID="+userDetails.getCompany_id() +"";
					innerQuery = " INNER JOIN User AS U ON U.id = R.LASTMODIFIEDBYID  ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_ISSUES_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());
					object.put("issuesUpdatedData", issuesService.getIssuesActivityList(query,innerQuery));
				}else {
					innerQuery = " INNER JOIN User AS U ON U.id = R.LASTMODIFIEDBYID  ";

					query = " R.LASTUPDATED_DATE between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND R.LASTMODIFIEDBYID = "+object.getLong("userId")+"  AND R.markForDelete=0 AND R.COMPANY_ID="+userDetails.getCompany_id() +"";
					object.put("issuesUpdatedData", issuesService.getIssuesActivityList(query,innerQuery));
				}
			}else if (object.getInt("activityType") == 3) {
				
				if(object.getLong("userId") == 0) {
					query = " R.LASTUPDATED_DATE between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"'  AND R.markForDelete=1 AND R.COMPANY_ID="+userDetails.getCompany_id() +"";
					innerQuery = " INNER JOIN User AS U ON U.id = R.LASTMODIFIEDBYID  ";
					tableConfig				= new ValueObject();
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ALL_USER_ISSUES_ACTIVITY_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					object.put("tableConfig", tableConfig.getHtData());
					object.put("issuesDeletedData", issuesService.getIssuesActivityList(query,innerQuery));
				}else {
					innerQuery = " INNER JOIN User AS U ON U.id = R.LASTMODIFIEDBYID  ";
				
			query = " R.LASTUPDATED_DATE between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME +"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND R.LASTMODIFIEDBYID = "+object.getLong("userId")+"  AND R.markForDelete=1 AND R.COMPANY_ID="+userDetails.getCompany_id() +"";
			object.put("issuesDeletedData", issuesService.getIssuesActivityList(query,innerQuery));
				}
			}
			return object;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	 public ValueObject getUserWiseCount(ValueObject object) {
		 
		 CustomUserDetails userDetails = null;
		 Object[] count = null;
		 try {
			 userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 object.put("companyId", userDetails.getCompany_id());
			 object.put("startDate", object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME);
			 object.put("endDate", object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME);
				if(object.getLong("userId",0) > 0) {
					object.put("userCreatedCond", " AND CO.createdById = "+object.getLong("userId",0)+" ");
					object.put("userUpdatedCond", " AND UO.lastModifiedById = "+object.getLong("userId",0)+" ");
					object.put("userDeletedCond", " AND PO.lastModifiedById = "+object.getLong("userId",0)+" ");
				}
				if(object.getBoolean("DSE",false)) {
					  count = dseService.getUserActivityCount(object);
				}else {
					count = poService.getUserActivityCount(object);
				}
			
			object.put("DeletedPoCount", count[0]);
			object.put("CreatePoCount", count[1]);
			object.put("UpdatedPoCount", count[2]);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		 return object;
		 
	 }
	  
	 public ValueObject getUserWisePoData(ValueObject object) {
	
		 ValueObject tableConfig = null;
		 
		 
		 CustomUserDetails userDetails = null;
			try {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String innnerJoin = "",query="";
				String updateInner = " INNER JOIN User AS U ON U.id = p.lastModifiedById  ";
				String updateQuery = " AND p.lastupdated between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
				if(object.getInt("activityType") == 1) {
					if(object.getLong("userId",0) > 0) {
						innnerJoin = " INNER JOIN User AS U ON U.id = p.createdById  ";
						query = " AND p.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND p.createdById ="+object.getLong("userId",0)+" ";
					}else {
						innnerJoin = " INNER JOIN User AS U ON U.id = p.createdById  ";
						query = " AND p.created between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
					}
					
				}
				else if(object.getInt("activityType") == 2) {
					if(object.getLong("userId",0) > 0) {
						innnerJoin = updateInner;
						query = updateQuery +=" AND p.lastModifiedById ="+object.getLong("userId",0)+" AND p.markForDelete =0 ";
					}else {
						innnerJoin = updateInner;
						query = updateQuery+=" AND p.markForDelete =0 ";
					}
					
				}else if(object.getInt("activityType") == 3) {
					if(object.getLong("userId",0) > 0) {
						innnerJoin = updateInner;
						query = updateQuery+=" AND p.lastModifiedById ="+object.getLong("userId",0)+" AND p.markForDelete = 1 ";
					}else {
						innnerJoin = updateInner;
						query = updateQuery+=" AND p.markForDelete = 1 ";
					}
					
				}
				
				tableConfig				= new ValueObject();
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.USER_PO_ACTIVITY_REPORT_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				object.put("tableConfig", tableConfig.getHtData());
				object.put("tableData", poService.getUserActivityData(query,innnerJoin,userDetails.getCompany_id()));
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		 
		 return object;


	 }
	 
	 public ValueObject getUserWiseDSEData(ValueObject object) {
			
		 ValueObject tableConfig = null;
		 
		 
		 CustomUserDetails userDetails = null;
			try {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				String innnerJoin = "",query="";
				String updateInner = " INNER JOIN User U4 ON U4.id = DSE.lastModifiedById  ";
				String updateQuery = " AND DSE.lastUpdatedOn between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND DSE.creationOn <> DSE.lastUpdatedOn ";
				if(object.getInt("activityType") == 1) {
					if(object.getLong("userId",0) > 0) {
						innnerJoin = " INNER JOIN User U4 ON U4.id = DSE.createdById  ";
						query = " AND DSE.creationOn between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' AND DSE.createdById ="+object.getLong("userId",0)+" ";
					}else {
						innnerJoin = " INNER JOIN User U4 ON U4.id = DSE.createdById  ";
						query = " AND DSE.creationOn between '"+object.getString("startDate") +" "+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("endDate")+" "+DateTimeUtility.DAY_END_TIME+"' ";
					}
					
				}
				else if(object.getInt("activityType") == 2) {
					if(object.getLong("userId",0) > 0) {
						innnerJoin = updateInner;
						query = updateQuery +=" AND DSE.lastModifiedById ="+object.getLong("userId",0)+" AND DSE.markForDelete =0 ";
					}else {
						innnerJoin = updateInner;
						query = updateQuery+=" AND DSE.markForDelete =0 ";
					}
					
				}else if(object.getInt("activityType") == 3) {
					if(object.getLong("userId",0) > 0) {
						innnerJoin = updateInner;
						query = updateQuery+=" AND DSE.lastModifiedById ="+object.getLong("userId",0)+" AND DSE.markForDelete = 1 ";
					}else {
						innnerJoin = updateInner;
						query = updateQuery+=" AND DSE.markForDelete = 1 ";
					}
					
				}
				
				tableConfig				= new ValueObject();
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.USER_DSE_ACTIVITY_REPORT_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				object.put("tableConfig", tableConfig.getHtData());
				object.put("tableData", dseService.getDSEListByUserActivity(query,innnerJoin,userDetails.getCompany_id()));
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		 
		 return object;


	 }
	
}
