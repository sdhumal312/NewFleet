package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.RenewalReminderConfiguration;
import org.fleetopgroup.constant.UserAlertNOtificationsConstant;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.dao.UserAlertNotificationsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.UserAlertNotificationsDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.RenewalReminder;
import org.fleetopgroup.persistence.model.UserAlertNotifications;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IUserAlertNotificationsService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAlertNotificationsService implements IUserAlertNotificationsService {

	@PersistenceContext public EntityManager 	entityManager;
	
	@Autowired	private UserAlertNotificationsRepository	userAlertNotificationsRepository;
	@Autowired	private IUserProfileService					userProfileService;
	@Autowired	private IRenewalReminderService				RRService;
	@Autowired private ICompanyConfigurationService 		companyConfigurationService;
	RenewalReminderBL RRBL = new RenewalReminderBL();
	
	SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY_HH_MM_AA);
	
	private static final int PAGE_SIZE = 10;
	
	@Override
	public ValueObject getUserNotificationList(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			valueObject.put("notificationList", getUserAlertNotificationsList(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Long getNotificationCountForUser(Long userId) throws Exception {
		
		return userAlertNotificationsRepository.getNotificationCountForUser(userId);
	}
	
	@Override
	public List<UserAlertNotificationsDto> getUserAlertNotificationsList(Long userId) throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT c.userAlertNotificationsId, c.userId, c.transactionId, c.txnTypeId, c.status, c.alertMsg, c.companyId,"
					+ " c.createdById, c.createdOn, IR.INVRID_NUMBER "
					+ " FROM UserAlertNotifications AS c "
					+ " LEFT JOIN InventoryRequisition IR ON IR.INVRID = c.transactionId"
					+ " where c.userId="+ userId + " AND c.status = 1 AND c.markForDelete = 0",
					Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<UserAlertNotificationsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<UserAlertNotificationsDto>();
				UserAlertNotificationsDto notificationsDto = null;
				for (Object[] result : results) {
					notificationsDto = new UserAlertNotificationsDto();
					
					notificationsDto.setUserAlertNotificationsId((Long) result[0]);
					notificationsDto.setUserId((Long) result[1]);
					notificationsDto.setTransactionId((Long) result[2]);
					notificationsDto.setTxnTypeId((short) result[3]);
					notificationsDto.setStatus((short) result[4]);
					notificationsDto.setAlertMsg((String) result[5]);
					notificationsDto.setCompanyId((Integer) result[6]);
					notificationsDto.setCreatedById((Long) result[7]);
					notificationsDto.setCreatedOn((Date) result[8]);
					notificationsDto.setPartRequisitionNumber((Long) result[9]);

					Dtos.add(notificationsDto);
				}
			}
			return Dtos;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<UserAlertNotificationsDto> getUnReadAlertNotificationsList(Long userId, Integer pageNumber)
			throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT c.userAlertNotificationsId, c.userId, c.transactionId, c.txnTypeId, c.status, c.alertMsg, c.companyId,"
					+ " c.createdById, c.createdOn, IR.INVRID_NUMBER, U.firstName, U.lastName,R.requisitionNum "
					+ " FROM UserAlertNotifications AS c "
					+ " INNER JOIN User U ON U.id = c.createdById"
					+ " LEFT JOIN InventoryRequisition IR ON IR.INVRID = c.transactionId"
					+ " LEFT JOIN Requisition R ON R.requisitionId = c.transactionId"
					+ " where c.userId="+ userId + " AND c.status = 1 AND c.markForDelete = 0 order by c.userAlertNotificationsId desc ",
					Object[].class);
			
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);

			List<Object[]> results = queryt.getResultList();

			List<UserAlertNotificationsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<UserAlertNotificationsDto>();
				UserAlertNotificationsDto notificationsDto = null;
				for (Object[] result : results) {
					notificationsDto = new UserAlertNotificationsDto();
					
					notificationsDto.setUserAlertNotificationsId((Long) result[0]);
					notificationsDto.setUserId((Long) result[1]);
					notificationsDto.setTransactionId((Long) result[2]);
					notificationsDto.setTxnTypeId((short) result[3]);
					notificationsDto.setStatus((short) result[4]);
					notificationsDto.setAlertMsg((String) result[5]);
					notificationsDto.setCompanyId((Integer) result[6]);
					notificationsDto.setCreatedById((Long) result[7]);
					notificationsDto.setCreatedOn((Date) result[8]);
					notificationsDto.setPartRequisitionNumber((Long) result[9]);
					notificationsDto.setSendBy((String) result[10]+"_"+(String) result[11]);
					notificationsDto.setRequisitionNumber((Long) result[12]);
					notificationsDto.setTxnTypeName(UserAlertNOtificationsConstant.getAlertTypeName(notificationsDto.getTxnTypeId()));

					Dtos.add(notificationsDto);
				}
			}
			return Dtos;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getUnreadNotificationList(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		try {

			userDetails 			= Utility.getObject(valueObject);
			pageNumber				= valueObject.getInt("pageNumber",1);
			Page<UserAlertNotifications> page  = getUnReadPageObject(pageNumber, userDetails.getId());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());

			List<UserAlertNotificationsDto> pageList = getUnReadAlertNotificationsList(userDetails.getId(), pageNumber);

			valueObject.put("notificationList", pageList);
			valueObject.put("SelectPage", pageNumber);
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
			 pageNumber					= null;
		}
	}
	
	@Override
	public Page<UserAlertNotifications> getUnReadPageObject(Integer pageNumber, Long userId) {	
		
		System.out.println("pageNumber "+pageNumber);
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "userAlertNotificationsId");
		return userAlertNotificationsRepository.getUnReadPageObject(userId, pageable);
	}
	
	@Override
	@Transactional
	public ValueObject markNotificationAsRead(ValueObject valueObject) throws Exception {
		userAlertNotificationsRepository.markNotificationAsRead(valueObject.getLong("userAlertNotificationsId"));
		return valueObject;
	}
	
	@Override
	public ValueObject getReadNotificationList(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		try {

			userDetails 			= Utility.getObject(valueObject);
			pageNumber				= valueObject.getInt("pageNumber",0);
			Page<UserAlertNotifications> page  = getReadPageObject(pageNumber, userDetails.getId());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());

			List<UserAlertNotificationsDto> pageList = getReadAlertNotificationsList(userDetails.getId(), pageNumber);

			valueObject.put("notificationList", pageList);
			valueObject.put("SelectPage", pageNumber);
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
			 pageNumber					= null;
		}
	}
	
	@Override
	public Page<UserAlertNotifications> getReadPageObject(Integer pageNumber, Long userId) throws Exception {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "userAlertNotificationsId");
		return userAlertNotificationsRepository.getReadPageObject(userId, pageable);
	}
	
	@Override
	public List<UserAlertNotificationsDto> getReadAlertNotificationsList(Long userId, Integer pageNumber)
			throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT c.userAlertNotificationsId, c.userId, c.transactionId, c.txnTypeId, c.status, c.alertMsg, c.companyId,"
					+ " c.createdById, c.createdOn, IR.INVRID_NUMBER, U.firstName, U.lastName,R.requisitionNum "
					+ " FROM UserAlertNotifications AS c "
					+ " INNER JOIN User U ON U.id = c.createdById"
					+ " LEFT JOIN InventoryRequisition IR ON IR.INVRID = c.transactionId"
					+ " LEFT JOIN Requisition R ON R.requisitionId = c.transactionId"
					+ " where c.userId="+ userId + " AND c.status = 2 AND c.markForDelete = 0 order by c.userAlertNotificationsId desc",
					Object[].class);
			
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);

			List<Object[]> results = queryt.getResultList();

			List<UserAlertNotificationsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<UserAlertNotificationsDto>();
				UserAlertNotificationsDto notificationsDto = null;
				for (Object[] result : results) {
					notificationsDto = new UserAlertNotificationsDto();
					
					notificationsDto.setUserAlertNotificationsId((Long) result[0]);
					notificationsDto.setUserId((Long) result[1]);
					notificationsDto.setTransactionId((Long) result[2]);
					notificationsDto.setTxnTypeId((short) result[3]);
					notificationsDto.setStatus((short) result[4]);
					notificationsDto.setAlertMsg((String) result[5]);
					notificationsDto.setCompanyId((Integer) result[6]);
					notificationsDto.setCreatedById((Long) result[7]);
					notificationsDto.setCreatedOn((Date) result[8]);
					notificationsDto.setPartRequisitionNumber((Long) result[9]);
					notificationsDto.setSendBy((String) result[10]+"_"+(String) result[11]);
					notificationsDto.setRequisitionNumber((Long) result[12]);
					
					notificationsDto.setTxnTypeName(UserAlertNOtificationsConstant.getAlertTypeName(notificationsDto.getTxnTypeId()));

					Dtos.add(notificationsDto);
				}
			}
			return Dtos;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getSentNotificationList(ValueObject valueObject) throws Exception {
		CustomUserDetails	   userDetails				= null;
		Integer				   pageNumber				= null;
		try {

			userDetails 			= Utility.getObject(valueObject);
			pageNumber				= valueObject.getInt("pageNumber",0);
			Page<UserAlertNotifications> page  = getSentPageObject(pageNumber, userDetails.getId());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valueObject.put("deploymentLog", page);
			valueObject.put("beginIndex", begin);
			valueObject.put("endIndex", end);
			valueObject.put("currentIndex", current);

			valueObject.put("invoiceCount", page.getTotalElements());

			List<UserAlertNotificationsDto> pageList = getSentAlertNotificationsList(userDetails.getId(), pageNumber);

			valueObject.put("notificationList", pageList);
			valueObject.put("SelectPage", pageNumber);
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
			 pageNumber					= null;
		}
	}
	
	@Override
	public Page<UserAlertNotifications> getSentPageObject(Integer pageNumber, Long userId) throws Exception {		
		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "userAlertNotificationsId");
		return userAlertNotificationsRepository.getSentPageObject(userId, pageable);
	}
	
	@Override
	public List<UserAlertNotificationsDto> getSentAlertNotificationsList(Long userId, Integer pageNumber)
			throws Exception {
		try {

			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT c.userAlertNotificationsId, c.userId, c.transactionId, c.txnTypeId, c.status, c.alertMsg, c.companyId,"
					+ " c.createdById, c.createdOn, IR.INVRID_NUMBER, U.firstName, U.lastName,R.requisitionNum  "
					+ " FROM UserAlertNotifications AS c "
					+ " INNER JOIN User U ON U.id = c.userId"
					+ " LEFT JOIN InventoryRequisition IR ON IR.INVRID = c.transactionId"
					+ " LEFT JOIN Requisition R ON R.requisitionId = c.transactionId"
					+ " where c.createdById="+ userId + " AND c.markForDelete = 0 order by c.userAlertNotificationsId desc ",
					Object[].class);
			
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);

			List<Object[]> results = queryt.getResultList();

			List<UserAlertNotificationsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<UserAlertNotificationsDto>();
				UserAlertNotificationsDto notificationsDto = null;
				for (Object[] result : results) {
					notificationsDto = new UserAlertNotificationsDto();
					
					notificationsDto.setUserAlertNotificationsId((Long) result[0]);
					notificationsDto.setUserId((Long) result[1]);
					notificationsDto.setTransactionId((Long) result[2]);
					notificationsDto.setTxnTypeId((short) result[3]);
					notificationsDto.setStatus((short) result[4]);
					notificationsDto.setAlertMsg((String) result[5]);
					notificationsDto.setCompanyId((Integer) result[6]);
					notificationsDto.setCreatedById((Long) result[7]);
					notificationsDto.setCreatedOn((Date) result[8]);
					notificationsDto.setPartRequisitionNumber((Long) result[9]);
					notificationsDto.setSendBy((String) result[10]+"_"+(String) result[11]);
					notificationsDto.setRequisitionNumber((Long) result[12]);
					
					notificationsDto.setTxnTypeName(UserAlertNOtificationsConstant.getAlertTypeName(notificationsDto.getTxnTypeId()));
					notificationsDto.setStatusStr(UserAlertNOtificationsConstant.getAlertStatusName(notificationsDto.getStatus()));
					Dtos.add(notificationsDto);
				}
			}
			return Dtos;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject getRRNotificationCount() throws Exception {
		long 							overDueCount				= 0;
		long							dueSoonCount				= 0;
		ValueObject						valueObject					= null;
		String							todaysDate					= null;
		CustomUserDetails	   			userDetails					= null;
		UserProfileDto					useProfileDto				= null;
		String[]						subArr						= null;
		List<String> 					list 						= null;
		HashMap<String, Object> 		configuration	   		 	= null;
		HashMap<String, Object> 		companyConfi	   		 	= null;
		boolean							rrNotification				= false;
		ValueObject						dueSoonCountByVStatus						= null;
		ValueObject						overDueCountByVStatus						= null;
		HashMap<Short, Long> 			dueSoonCountHM								= null;	
		HashMap<Short, Long> 			overDueCountHM								= null;	
		try {
			valueObject 	= new ValueObject();
			todaysDate		= DateTimeUtility.getCurrentDateInString();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			companyConfi	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			rrNotification	= (boolean) configuration.getOrDefault(RenewalReminderConfiguration.SHOW_RR_NOTIFICATION, false);
			useProfileDto 	= userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId());
			
			if(useProfileDto.getSubscribe() != null) {
				subArr					= useProfileDto.getSubscribe().split(",");
				list 					= Arrays.asList(subArr);
			}
			if(list != null ) {
				if(list.contains("RENEWAL_REMINDER")) {
					
					dueSoonCountByVStatus		= RRService.totalDueSoonCount(todaysDate, userDetails.getCompany_id());
					overDueCountByVStatus		= RRService.getTotalOverDueRenewalCount(todaysDate+DateTimeUtility.DAY_START_TIME, userDetails.getCompany_id());
					
					dueSoonCountHM				= (HashMap<Short, Long>) dueSoonCountByVStatus.get("dueSoonCountHM");
					overDueCountHM				= (HashMap<Short, Long>) overDueCountByVStatus.get("overDueCountHM");
					
					if(dueSoonCountHM != null ) {
						for(Entry<Short, Long> status : dueSoonCountHM.entrySet()){
							dueSoonCount += status.getValue() ;
							}
					}
					if(overDueCountHM != null ) {
						for(Entry<Short, Long> status : overDueCountHM.entrySet()){
							overDueCount += status.getValue() ;
						}
					}
					
			//		overDueCount	= RRService.getTotalOverDueRenewalCount(todaysDate, userDetails.getCompany_id(),vehicleJoinQuery);
			//		dueSoonCount	= RRService.totalDueSoonCount(todaysDate, userDetails.getCompany_id(),vehicleJoinQuery);
				}
			}	
			
			valueObject.put("overDueCount", overDueCount);
			valueObject.put("dueSoonCount", dueSoonCount);
			valueObject.put("totalRRCount", (overDueCount+dueSoonCount));
			valueObject.put("rrNotification", rrNotification);
			valueObject.put("showIvCargoLogoWithLink",companyConfi.getOrDefault("showIvCargoLogoWithLink", false));
			
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Override
	public ValueObject getAllRenewalRemiderListByStatus(ValueObject valueObject) throws Exception {
		CustomUserDetails	   		userDetails				= null;
		Integer				   		pageNumber				= null;
		java.util.Date				todaysStrDate			= null;
		UserProfileDto				useProfileDto			= null;
		Page<RenewalReminder> 		page 					= null;
		List<RenewalReminderDto> 	pageList				= null;
		int							status					= 0;
		String[]					subArr					= null;
		List<String> 				list 					= null;
		try {
			todaysStrDate			= DateTimeUtility.getCurrentDate();
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			useProfileDto 			= userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId());
			status 					= valueObject.getInt("status",0);
			pageNumber				= valueObject.getInt("pageNumber",0);

			if(useProfileDto.getSubscribe() != null) {
				subArr					= useProfileDto.getSubscribe().split(",");
				list 					= Arrays.asList(subArr);
			}
			
			if(list != null && status > 0 ) {
				if(list.contains("RENEWAL_REMINDER")) {
					if(status ==  7) {
						page 		= RRService.getDeployment_Page_RenewalDueSoon(todaysStrDate, valueObject, userDetails);
						pageList 	= RRBL.Only_Show_ListofRenewal(RRService.getAllDueSoonRenewalList(todaysStrDate , valueObject, userDetails));
						
					}else if(status == 8) {
						page 		= RRService.getDeployment_Page_RenewalOverDue(todaysStrDate, valueObject, userDetails);
						pageList 	= RRBL.Only_Show_ListofRenewal(RRService.getAllOverDueRenewalList(todaysStrDate , valueObject, userDetails));
					}
				
					int current = page.getNumber() + 1;
					int begin = Math.max(1, current - 5);
					int end = Math.min(begin + 10, page.getTotalPages());

					valueObject.put("deploymentLog", page);
					valueObject.put("beginIndex", begin);
					valueObject.put("endIndex", end);
					valueObject.put("currentIndex", current);

					
					valueObject.put("rrList", pageList);
					
					valueObject.put("SelectPage", pageNumber);
				
				}
			
			}
		
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			 userDetails				= null;
			 pageNumber					= null;
		}
	}
	
}
