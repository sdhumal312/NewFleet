package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.ServiceReminderType;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.constant.WorkOrdersType;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.ServiceReminderBL;
import org.fleetopgroup.persistence.dao.DealerServiceEntriesRepository;
import org.fleetopgroup.persistence.dao.DealerServiceReminderHistoryRepository;
import org.fleetopgroup.persistence.dao.ServiceEntriesRepository;
import org.fleetopgroup.persistence.dao.ServiceReminderHistoryRepository;
import org.fleetopgroup.persistence.dao.ServiceReminderRepository;
import org.fleetopgroup.persistence.dao.ServiceReminderWorkOrderHistoryRepository;
import org.fleetopgroup.persistence.dao.WorkOrdersRepository;
import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverReminderDto;
import org.fleetopgroup.persistence.dto.InventoryTransferDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.ServiceReminderHistoryDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.model.CalenderEvent;
import org.fleetopgroup.persistence.model.DealerServiceEntries;
import org.fleetopgroup.persistence.model.DealerServiceReminderHistory;
import org.fleetopgroup.persistence.model.EmailAlertQueue;
import org.fleetopgroup.persistence.model.ServiceEntries;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.ServiceReminderHistory;
import org.fleetopgroup.persistence.model.ServiceReminderSequenceCounter;
import org.fleetopgroup.persistence.model.ServiceReminderWorkOrderHistory;
import org.fleetopgroup.persistence.model.SmsAlertQueue;
import org.fleetopgroup.persistence.model.SubscribeBox;
import org.fleetopgroup.persistence.model.VehicleServiceProgram;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.persistence.report.dao.ServiceReminderDao;
import org.fleetopgroup.persistence.report.dao.impl.ServiceReminderDaoImpl;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDealerServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IEmailAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.IServiceProgramService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISmsAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.ISubscribeBoxService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
//
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service("ServiceReminderService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ServiceReminderService implements IServiceReminderService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired private ServiceReminderRepository 					serviceReminderDao;
	@Autowired private ICompanyConfigurationService					companyConfigurationService;
	@Autowired private ServiceReminderDao							ServiceReminderDaoImpl;
	@Autowired private ServiceReminderWorkOrderHistoryRepository	historyRepository;
	@Autowired private IServiceReminderSequenceService 				serviceReminderSequenceService;
	@Autowired private IUserProfileService							userProfileService;
	@Autowired private IVehicleService								vehicleService;
	@Autowired private ISubscribeBoxService 						subscribeBoxService;
	@Autowired private IEmailAlertQueueService 						emailAlertQueueService;
	@Autowired private ISmsAlertQueueService 						smsAlertQueueService;
	@Autowired private HttpServletRequest							request;
	@Autowired private DealerServiceReminderHistoryRepository		DSE_HistoryRepository;
	@Autowired private IServiceProgramService						serviceProgramService;
	@Autowired private IDealerServiceEntriesService 				dealearService;
	@Autowired private ServiceReminderHistoryRepository             srHistoryRepository;
	@Autowired private WorkOrdersRepository							workOrdersRepository;
	@Autowired private ServiceEntriesRepository                     serviceEntriesRepository;
	@Autowired private DealerServiceEntriesRepository               dealerServiceEntriesRepository;
	
	SimpleDateFormat 						dateFormatAtt 		= new SimpleDateFormat("yyyy, MM, dd");
	SimpleDateFormat 						dateFormat 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 						AttendanceYear 		= new SimpleDateFormat("yyyy");
	SimpleDateFormat 						AttendanceMonth 	= new SimpleDateFormat("MM");
	SimpleDateFormat 						AttendanceDay 		= new SimpleDateFormat("dd");
	SimpleDateFormat 						sqlDateFormat 		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat 						sqlDateFormat2 		= new SimpleDateFormat("MM-dd-yyyy");
	
	RenewalReminderBL 	RRBL 			= new RenewalReminderBL();
	ServiceReminderBL 	SRBL					= new ServiceReminderBL();
	
	private static final int PAGE_SIZE 			= 10;
	private static final int PAGE_SIZE_15	 	= 15;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addServiceReminder(ServiceReminder serviceReminder) {

		serviceReminderDao.save(serviceReminder);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addMultipleServiceReminder(List<ServiceReminder> reminderList) throws Exception {
		
		serviceReminderDao.saveAll(reminderList);
		
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ServiceReminder updateServiceReminder(ServiceReminder ServiceReminder) {

		return serviceReminderDao.save(ServiceReminder);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateWORKOrderToServiceReminder(Long Service_id, short Status, Integer companyId, Long workOrders_id) {

		serviceReminderDao.updateWORKOrderToServiceReminder(Service_id, Status, companyId, workOrders_id);
	}

	/** This Page get ServiceReminder table to get pagination values */
	@SuppressWarnings("deprecation")
	@Transactional
	public Page<ServiceReminder> getDeployment_Page_ServiceReminder(Integer pageNumber, CustomUserDetails userDetails) throws Exception{
		
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		try {
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				
				return serviceReminderDao.getDeployment_Page_ServiceReminder(userDetails.getCompany_id(), pageable);
			}else {
				return serviceReminderDao.getDeployment_Page_ServiceReminder(userDetails.getId(),userDetails.getCompany_id(), pageable);
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Page<ServiceReminder> getDeployment_Page_ServiceReminderByVid(Integer pageNumber,
			CustomUserDetails userDetails) throws Exception {
		
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE_15);
		try {
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				
				return serviceReminderDao.getDeployment_Page_ServiceReminderByVehicle(userDetails.getCompany_id(), pageable);
			}else {
				return serviceReminderDao.getDeployment_Page_ServiceReminderByVid(userDetails.getId(),userDetails.getCompany_id(), pageable);
			}
			
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * This List get ServiceReminder table to get pagination last 10 entries
	 * values
	 */
	@Transactional
	public List<ServiceReminderDto> listServiceReminder(Integer pageNumber, CustomUserDetails userDetails) throws Exception {

		TypedQuery<Object[]> query =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager
					.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT"
							+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
							+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
							+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
							+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
							+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number FROM ServiceReminder v "
							+ " INNER JOIN Vehicle T ON T.vid = v.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
							+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
							+ " LEFT JOIN User U ON U.id = v.createdById"
							+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
							+ " WHERE v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
							+ " AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") ORDER BY v.service_id desc ", Object[].class);
		}else {
			
			query = entityManager
					.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT "
							+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
							+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
							+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
							+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
							+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number FROM ServiceReminder v "
							+ " INNER JOIN Vehicle T ON T.vid = v.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
							+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
							+ " LEFT JOIN User U ON U.id = v.createdById"
							+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
							+ " WHERE v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
							+ " AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") ORDER BY v.service_id desc", Object[].class);
		}
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		
		List<Object[]> results = query.getResultList();

		List<ServiceReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceReminderDto>();
			ServiceReminderDto list = null;
			for (Object[] result : results) {
				list = new ServiceReminderDto();
				
				list.setService_id((Long) result[0]);
				list.setVid((Integer) result[1]);
				list.setVehicle_registration((String) result[2]);
				list.setVehicle_Group((String) result[3]);
				list.setService_type((String) result[4]);
				if(result[5] != null) {
					list.setService_subtype((String) result[5]+" - "+(String) result[6]);
				}else {
					list.setService_subtype((String) result[6]);
				}
				list.setMeter_interval((Integer) result[7]);
				list.setVehicle_currentOdometer((Integer) result[8]);
				list.setMeter_serviceodometer((Integer) result[9]);
				list.setMeter_threshold((Integer) result[10]);
				list.setMeter_servicethreshold_odometer((Integer) result[11]);
				list.setTime_interval((Integer) result[12]);
				list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
				list.setTime_intervalperiodId((short) result[13]);
				list.setTime_interval_currentdate( (Date) result[14]);
				list.setTime_servicedate((Date) result[15]);
				list.setTime_threshold((Integer) result[16]);
				list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
				list.setTime_thresholdperiodId((short) result[17]);
				list.setTime_servicethreshold_date((Date) result[18]);
				list.setService_emailnotification((String) result[19]);
				list.setService_subscribeduser((String) result[20]);
				list.setService_subscribeduser_name((String) result[21]);
				list.setLast_servicedate((Date) result[22]);
				list.setLast_servicecompleldby((String) result[23]);
				list.setLast_servicecompleldid((Long) result[24]);
				list.setService_remider_howtimes((Integer) result[25]);
				list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
				list.setServiceStatusId((short) result[26]);
				list.setVehicleGroupId((long) result[27]);
				list.setCreatedBy((String) result[28]);
				list.setService_Number((Long) result[29]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	@Override
	public List<ServiceReminderDto> listServiceReminderGroupByVid(Integer pageNumber, CustomUserDetails userDetails)
			throws Exception {

		TypedQuery<Object[]> query =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager
					.createQuery("SELECT v.vid, T.vehicle_registration, COUNT(v.service_id) , COUNT(distinct v.serviceProgramId) "
							+ " FROM ServiceReminder v "
							+ " INNER JOIN Vehicle T ON T.vid = v.vid "
							+ " WHERE v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
							+ " AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") group by v.vid ORDER BY v.service_id desc ", Object[].class);
		}else {
			query = entityManager
					.createQuery("SELECT v.vid, T.vehicle_registration, COUNT(v.service_id), COUNT( distinct v.serviceProgramId) "
							+ " FROM ServiceReminder v "
							+ " INNER JOIN Vehicle T ON T.vid = v.vid "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = T.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
							+ " WHERE v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
							+ " AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") group by v.vid ORDER BY v.service_id desc ", Object[].class);
		}
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE_15);
		query.setMaxResults(PAGE_SIZE_15);
		
		List<Object[]> results = query.getResultList();

		List<ServiceReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceReminderDto>();
			ServiceReminderDto list = null;
			for (Object[] result : results) {
				list	= new ServiceReminderDto();
				list.setVid((Integer) result[0]);
				list.setVehicle_registration((String) result[1]);
				list.setService_id((Long) result[2]);
				list.setServiceProgramId((Long) result[3]);
				
				Dtos.add(list);
				
			}
		}
		return Dtos;
	}
	public List<ServiceReminder> AllServiceReminderList() throws Exception {

		return serviceReminderDao.findAll();
	}

	@Transactional
	public List<ServiceReminderDto> OverDueServiceRemnder(Date toDate, CustomUserDetails userDetails, Integer pageNumber) throws Exception {
		try {
			TypedQuery<Object[]> query =	null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT  " 
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
								+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " LEFT JOIN User U ON U.id = v.createdById"
								+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
								+ " where v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
								+ " AND ( (v.time_servicedate <= '"+toDate+"' AND v.time_interval > 0)  OR ( v.meter_serviceodometer <= v.vehicle_currentOdometer AND v.meter_interval > 0 ) AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") )", Object[].class);
			
			}else {
				
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT  " 
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
								+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
								+ " LEFT JOIN User U ON U.id = v.createdById"
								+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
								+ " where v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
								+ " AND ( ( v.time_servicedate <= '"+toDate+"' AND v.time_interval > 0) OR ( v.meter_serviceodometer <= v.vehicle_currentOdometer AND v.meter_interval > 0)"
								+ "AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") )", Object[].class);
				
			}
			query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			query.setMaxResults(PAGE_SIZE);
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setVid((Integer) result[1]);
					list.setVehicle_registration((String) result[2]);
					list.setVehicle_Group((String) result[3]);
					list.setService_type((String) result[4]);
					if(result[5] != null) {
						list.setService_subtype((String) result[5]+" - "+(String) result[6]);
					}else {
						list.setService_subtype((String) result[6]);
					}
					list.setMeter_interval((Integer) result[7]);
					list.setVehicle_currentOdometer((Integer) result[8]);
					list.setMeter_serviceodometer((Integer) result[9]);
					list.setMeter_threshold((Integer) result[10]);
					list.setMeter_servicethreshold_odometer((Integer) result[11]);
					list.setTime_interval((Integer) result[12]);
					list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
					list.setTime_intervalperiodId((short) result[13]);
					list.setTime_interval_currentdate( (Date) result[14]);
					list.setTime_servicedate((Date) result[15]);
					list.setTime_threshold((Integer) result[16]);
					list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
					list.setTime_thresholdperiodId((short) result[17]);
					list.setTime_servicethreshold_date((Date) result[18]);
					list.setService_emailnotification((String) result[19]);
					list.setService_subScribedUserId((String) result[20]);
					list.setService_subscribeduser_name((String) result[21]);
					list.setLast_servicedate((Date) result[22]);
					list.setLast_servicecompleldby((String) result[23]);
					list.setLast_servicecompleldid((Long) result[24]);
					list.setService_remider_howtimes((Integer) result[25]);
					list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
					list.setServiceStatusId((short) result[26]);
					list.setVehicleGroupId((long) result[27]);
					list.setCreatedBy((String) result[28]);
					list.setService_Number((Long) result[29]);
					
					Dtos.add(list);
				}
			}
				return Dtos;
		
		} catch (Exception e) {
			throw e;
		}
		//return serviceReminderDao.OverDueServiceRemnder(toDate, userDetails.getId(), userDetails.getCompany_id());
	}
	@Transactional
	public List<ServiceReminderDto> TodaysOverDueServiceRemnder(Date toDate, CustomUserDetails userDetails, Integer pageNumber) throws Exception {
		try {
			TypedQuery<Object[]> query =	null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT  " 
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
								+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " LEFT JOIN User U ON U.id = v.createdById"
								+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
								+ " where v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
								+ " AND ( (v.time_servicedate = '"+toDate+"' AND v.time_interval > 0)  AND ( v.meter_interval > 0 ) AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") )", Object[].class);
			
			}else {
				
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT  " 
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
								+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
								+ " LEFT JOIN User U ON U.id = v.createdById"
								+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
								+ " where v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
								+ " AND ( ( v.time_servicedate = '"+toDate+"' AND v.time_interval > 0) AND ( v.meter_interval > 0)"
								+ "AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") )", Object[].class);
				
			}
			query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			query.setMaxResults(PAGE_SIZE);
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setVid((Integer) result[1]);
					list.setVehicle_registration((String) result[2]);
					list.setVehicle_Group((String) result[3]);
					list.setService_type((String) result[4]);
					if(result[5] != null) {
						list.setService_subtype((String) result[5]+" - "+(String) result[6]);
					}else {
						list.setService_subtype((String) result[6]);
					}
					list.setMeter_interval((Integer) result[7]);
					list.setVehicle_currentOdometer((Integer) result[8]);
					list.setMeter_serviceodometer((Integer) result[9]);
					list.setMeter_threshold((Integer) result[10]);
					list.setMeter_servicethreshold_odometer((Integer) result[11]);
					list.setTime_interval((Integer) result[12]);
					list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
					list.setTime_intervalperiodId((short) result[13]);
					list.setTime_interval_currentdate( (Date) result[14]);
					list.setTime_servicedate((Date) result[15]);
					list.setTime_threshold((Integer) result[16]);
					list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
					list.setTime_thresholdperiodId((short) result[17]);
					list.setTime_servicethreshold_date((Date) result[18]);
					list.setService_emailnotification((String) result[19]);
					list.setService_subScribedUserId((String) result[20]);
					list.setService_subscribeduser_name((String) result[21]);
					list.setLast_servicedate((Date) result[22]);
					list.setLast_servicecompleldby((String) result[23]);
					list.setLast_servicecompleldid((Long) result[24]);
					list.setService_remider_howtimes((Integer) result[25]);
					list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
					list.setServiceStatusId((short) result[26]);
					list.setVehicleGroupId((long) result[27]);
					list.setCreatedBy((String) result[28]);
					list.setService_Number((Long) result[29]);
					
					Dtos.add(list);
				}
			}
				return Dtos;
		
		} catch (Exception e) {
			throw e;
		}
		//return serviceReminderDao.OverDueServiceRemnder(toDate, userDetails.getId(), userDetails.getCompany_id());
	}

	@Transactional
	public List<ServiceReminderDto> DueSoonServiceRemnder(Date toDate, CustomUserDetails userDetails) throws Exception {
		try {
			TypedQuery<Object[]> query =	null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid,T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT   " 
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
								+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number, v.serviceProgramId, v.serviceScheduleId"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " LEFT JOIN User U ON U.id = v.createdById"
								+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
								+ " where  ( ( v.time_servicethreshold_date <= '"+toDate+"' AND  v.time_servicedate >= '"+toDate+"' AND v.time_interval > 0 )"
								+ " OR ( v.meter_servicethreshold_odometer <= v.vehicle_currentOdometer AND "
								+ " v.meter_serviceodometer >= v.vehicle_currentOdometer AND v.meter_interval > 0  )) AND "
								+ " v.companyId = "+userDetails.getCompany_id()+" "
								+ " AND v.time_servicedate > '"+toDate+"' and v.meter_serviceodometer > T.vehicle_Odometer"
								+ " AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") AND v.markForDelete = 0"
								+ " ", Object[].class);
			}else {
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid,T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT   " 
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
								+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number, v.serviceProgramId, v.serviceScheduleId"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " LEFT JOIN User U ON U.id = v.createdById"
								+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
								+ " where ( (v.time_servicethreshold_date <= '"+toDate+"' AND "
								+ " v.time_servicedate >= '"+toDate+"' AND v.time_interval > 0) OR ( v.meter_servicethreshold_odometer <= v.vehicle_currentOdometer "
								+ " AND v.meter_serviceodometer >= v.vehicle_currentOdometer AND v.meter_interval > 0 )) AND v.companyId = "+userDetails.getCompany_id()+" AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") "
								+ " AND v.time_servicedate > '"+toDate+"' and v.meter_serviceodometer > T.vehicle_Odometer"
								+ " AND v.markForDelete = 0 ", Object[].class);
				
			}
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setVid((Integer) result[1]);
					list.setVehicle_registration((String) result[2]);
					list.setVehicle_Group((String) result[3]);
					list.setService_type((String) result[4]);
					if(result[5] != null) {
						list.setService_subtype((String) result[5]+" - "+(String) result[6]);
					}else {
						list.setService_subtype((String) result[6]);
					}
					list.setMeter_interval((Integer) result[7]);
					list.setVehicle_currentOdometer((Integer) result[8]);
					list.setMeter_serviceodometer((Integer) result[9]);
					list.setMeter_threshold((Integer) result[10]);
					list.setMeter_servicethreshold_odometer((Integer) result[11]);
					list.setTime_interval((Integer) result[12]);
					list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
					list.setTime_intervalperiodId((short) result[13]);
					list.setTime_interval_currentdate( (Date) result[14]);
					list.setTime_servicedate((Date) result[15]);
					list.setTime_threshold((Integer) result[16]);
					list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
					list.setTime_thresholdperiodId((short) result[17]);
					list.setTime_servicethreshold_date((Date) result[18]);
					list.setService_emailnotification((String) result[19]);
					list.setService_subScribedUserId((String) result[20]);
					list.setService_subscribeduser_name((String) result[21]);
					list.setLast_servicedate((Date) result[22]);
					list.setLast_servicecompleldby((String) result[23]);
					list.setLast_servicecompleldid((Long) result[24]);
					list.setService_remider_howtimes((Integer) result[25]);
					list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
					list.setServiceStatusId((short) result[26]);
					list.setVehicleGroupId((long) result[27]);
					list.setCreatedBy((String) result[28]);
					list.setService_Number((Long) result[29]);
					list.setServiceProgramId((Long) result[30]);
					list.setServiceScheduleId((Long) result[31]);
					
						Dtos.add(list);
					
				}
			}
				return Dtos;
		
		
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public List<ServiceReminder> listVehicleServiceReminder(String Service_vehiclename) {

		// return serviceReminderDao.listServiceReminder(Service_vehiclename);

		TypedQuery<ServiceReminder> query = entityManager
				.createQuery("from ServiceReminder RR where " + Service_vehiclename + "", ServiceReminder.class);
		return query.getResultList();
	}

	@Transactional
	public ValueObject getServiceReminderForDueAndOverDue(ValueObject object) throws Exception{
		
		ServiceReminderDto serviceReminderDto = null;
		
		serviceReminderDto = SRBL.prepare_ServiceReminderDetail(getServiceReminder(object.getLong("serviceId"),object.getInt("companyId")));
		
		if(!serviceReminderDto.getDue() || serviceReminderDto.getDue() == false) {
			object.put("warningMsg", true);
		}
		System.err.println("serviceReminderDto=" +serviceReminderDto);
			
		
		return object;
	}

	@Transactional
	public ServiceReminderDto getServiceReminder(Long Service_id, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT" 
						+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
						+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
						+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
						+ ", v.service_subscribeduser_name, v.last_servicedate, U.email, v.last_servicecompleldid, v.service_remider_howtimes"
						+ " , v.serviceStatusId, v.vehicleGroupId, U2.email, v.service_Number, v.created, v.lastupdated, U3.email"
						+ ", v.serviceTypeId, v.serviceSubTypeId, v.serviceType, v.workorders_id, WO.workorders_Number, WO.vehicle_Odometer,"
						+ " v.isFirstService, v.firstMeterInterval, v.firstTimeInterval, v.firstTimeIntervalType, v.serviceEntries_id , SE.serviceEntries_Number,"
						+ " DSE.dealerServiceEntriesId, DSE.dealerServiceEntriesNumber, v.serviceProgramId, SP.programName, v.serviceScheduleId,"
						+ " DSE.vehicleOdometer,v.isSkip,v.skipRemark "
						+ " FROM ServiceReminder v "
						+ " INNER JOIN Vehicle T ON T.vid = v.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
						+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
						+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
						+ " LEFT JOIN User U ON U.id = v.last_servicecompleldbyId"
						+ " LEFT JOIN User U2 ON U2.id = v.createdById"
						+ " LEFT JOIN User U3 ON U3.id = v.lastModifiedById"
						+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = v.workorders_id"
						+ " LEFT JOIN ServiceEntries SE ON SE.serviceEntries_id = v.serviceEntries_id"
						+ " LEFT JOIN DealerServiceEntries DSE ON DSE.dealerServiceEntriesId = v.dealerServiceEntriesId"
						+ " LEFT JOIN VehicleServiceProgram SP ON SP.vehicleServiceProgramId = v.serviceProgramId "
						+ " where v.service_id = "+Service_id+" AND  v.companyId = "+companyId+" AND v.markForDelete = 0");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		ServiceReminderDto list;
		if (result != null) {
			list = new ServiceReminderDto();
			list.setService_id((Long) result[0]);
			list.setVid((Integer) result[1]);
			list.setVehicle_registration((String) result[2]);
			list.setVehicle_Group((String) result[3]);
			list.setService_type((String) result[4]);
			if(result[5] != null) {
				list.setService_subtype((String) result[5] + " - "+ (String) result[6]);
			}else {
				list.setService_subtype((String) result[6]);
			}
			
			list.setMeter_interval((Integer) result[7]);
			list.setVehicle_currentOdometer((Integer) result[8]);
			list.setMeter_serviceodometer((Integer) result[9]);
			list.setMeter_threshold((Integer) result[10]);
			list.setMeter_servicethreshold_odometer((Integer) result[11]);
			list.setTime_interval((Integer) result[12]);
			list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
			list.setTime_intervalperiodId((short) result[13]);
			list.setTime_interval_currentdate( (Date) result[14]);
			list.setTime_servicedate((Date) result[15]);
			list.setTime_threshold((Integer) result[16]);
			list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
			list.setTime_thresholdperiodId((short) result[17]);
			list.setTime_servicethreshold_date((Date) result[18]);
			list.setService_emailnotification((String) result[19]);
			list.setService_subScribedUserId((String) result[20]);
			list.setService_subscribeduser_name((String) result[21]);
			list.setLast_servicedate((Date) result[22]);
			list.setLast_servicecompleldby((String) result[23]);
			list.setLast_servicecompleldid((Long) result[24]);
			list.setService_remider_howtimes((Integer) result[25]);
			list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
			list.setServiceStatusId((short) result[26]);
			list.setVehicleGroupId((long) result[27]);
			list.setCreatedBy((String) result[28]);
			list.setService_Number((Long) result[29]);
			list.setCreatedOn((Date) result[30]);
			list.setLastupdatedOn((Date) result[31]);
			list.setLastModifiedBy((String) result[32]);
			list.setServiceTypeId((Integer) result[33]);
			list.setServiceSubTypeId((Integer) result[34]);
			list.setServiceType((short) result[35]);
			list.setServiceReminderType(ServiceReminderType.getServiceReminderType((short) result[35]));
			if(result[36] != null) {
				list.setWorkOrderId((Long) result[36]);
				list.setWorkOrderNumber((Long) result[37]);
			}
			list.setServiceOdometer((Integer) result[38]);
			list.setFirstService((boolean) result[39]);
			list.setFirstMeterInterval((Integer) result[40]);
			list.setFirstTimeInterval((Integer) result[41]);
			if(result[42] != null)
				list.setFirstTimeIntervalType((short) result[42]);
			list.setFirstTimeIntervalTypeStr(ServiceReminderDto.getTimeInterValName(list.getFirstTimeIntervalType()));
			if(result[43] != null) {
				list.setServiceEntriesId((Long) result[43]);
				list.setServiceEntriesNumber((Long) result[44]);
			}
			if(result[45] != null) {
				list.setDealerServiceEntriesId((Long) result[45]);
				list.setDealerServiceEntriesNumber((Long) result[46]);
				list.setServiceOdometer((Integer) result[50]);
			}
			list.setServiceProgramId((Long) result[47]);
			list.setService_subtype(list.getService_subtype().replace("null -", ""));
			if((String) result[48] != null)
				list.setServiceProgram((String) result[48]);
			else
				list.setServiceProgram("");
			list.setServiceScheduleId((Long) result[49]);
			list.setSkip((boolean) result[51]);
			if(result[52] != null)
			list.setIsSkipRemark((String) result[52]);
			
		} else {
			return null;
		}

		return list;
	}

	@Transactional
	public void deleteServiceReminder(Long ServiceReminder,Timestamp toDate, long userId, Integer companyId) {

		serviceReminderDao.deleteServiceReminder(ServiceReminder,toDate,userId, companyId);
	}

	@Transactional
	public List<ServiceReminder> listServiceReminder(String Service_vehiclename) {

		// return serviceReminderDao.listServiceReminder(query);

		TypedQuery<ServiceReminder> query = entityManager
				.createQuery("from ServiceReminder RR where " + Service_vehiclename + "", ServiceReminder.class);
		return query.getResultList();
	}

	@Transactional
	public Long countServiceReminder() throws Exception {

		return serviceReminderDao.countServiceReminder();
	}

	@Transactional
	public Long countTodayDueServiceReminder(Date toDate, Integer companyId, Long id) throws Exception {
		
		HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.SERVICE_REMINDER_CONFIG);
		if(!(boolean) configuration.get("groupListByVehicleNumber")) {
			if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return serviceReminderDao.countTodayDueServiceReminder(toDate, companyId);
			}
			return serviceReminderDao.countTodayDueServiceReminder(toDate, companyId, id);
		}else {
			if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return serviceReminderDao.countTodayDueServiceReminderGByVid(toDate, companyId);
			}
			return serviceReminderDao.countTodayDueServiceReminderGByVid(toDate, companyId, id);
			
		}
	}
	@Transactional
	public Long countTodaysDueServiceReminder(Date toDate, Integer companyId, Long id) throws Exception {
		
		HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.SERVICE_REMINDER_CONFIG);
		if(!(boolean) configuration.get("groupListByVehicleNumber")) {
			if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return serviceReminderDao.countTodaysOverDueServiceReminder(toDate, companyId);
			}
			return serviceReminderDao.countTodaysOverDueServiceReminder(toDate, companyId, id);
				}else {
			if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return serviceReminderDao.countTodaysOverDueServiceReminderGByVid(toDate, companyId);
			}
			return serviceReminderDao.countTodaysOverDueServiceReminderGByVid(toDate, companyId, id);	
		}
	//	return serviceReminderDao.countTodaysOverDueServiceReminder(toDate, companyId, id);
	}
		@Transactional
		public Long countUpcomingDueServiceReminder(Date fromDate, Date toDate, Integer companyId, Long id) throws Exception {

			HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.SERVICE_REMINDER_CONFIG);
			if(!(boolean) configuration.get("groupListByVehicleNumber")) {
				if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
					return serviceReminderDao.countUpcomingDueServiceReminder(fromDate, toDate, companyId);
				}
				return serviceReminderDao.countUpcomingDueServiceReminder(fromDate, toDate, companyId, id);
					}else {
				if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
					return serviceReminderDao.countUpcomingDueServiceReminderGByVid(fromDate, toDate, companyId);
				}
				return serviceReminderDao.countUpcomingDueServiceReminderGByVid(fromDate, toDate, companyId, id);
			}
			//return serviceReminderDao.countUpcomingDueServiceReminder(fromDate, toDate, companyId, id);	
		}


	@Transactional
	public Long countTodayOverDueServiceReminder(Date toDate, Integer companyId, Long id) throws Exception {
		
		HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.SERVICE_REMINDER_CONFIG);
		
		if(!(boolean) configuration.get("groupListByVehicleNumber")) {
			if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return serviceReminderDao.countTodayOverDueServiceReminder(toDate, companyId);
			}
			return serviceReminderDao.countTodayOverDueServiceReminder(toDate, companyId, id);
		}else {
			if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return serviceReminderDao.countTodayOverDueServiceReminderGByVid(toDate, companyId);
			}
			return serviceReminderDao.countTodayOverDueServiceReminderGByVid(toDate, companyId, id);
		}
		
		
	}

	@Transactional
	public List<ServiceReminder> SearchServiceReminder(String Search) throws Exception {
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<ServiceReminder> query = entityManager
				.createQuery("from ServiceReminder where lower(service_id) Like ('%" + Search
						+ "%') OR lower(vehicle_registration) Like ('%" + Search + "%') OR lower(service_type) Like ('%"
						+ Search + "%') OR lower(service_subtype) Like ('%" + Search + "%') ", ServiceReminder.class);
		return query.getResultList();
		}else {
			return null;
		}
	}

	@Transactional
	public List<ServiceReminderDto> OnlyVehicleServiceReminderList(Integer vehicle_ID, Integer companyId, Long id) throws Exception {
		TypedQuery<Object[]> query =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager
					.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT "
							+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
							+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
							+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
							+ ",v.service_subscribeduser_name, v.last_servicedate, U.email, v.last_servicecompleldid, v.service_remider_howtimes"
							+ " , v.serviceStatusId, v.vehicleGroupId, U2.email, v.service_Number, v.companyId FROM ServiceReminder v "
							+ " INNER JOIN Vehicle T ON T.vid = v.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
							+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
							+ " LEFT JOIN User U ON U.id = v.last_servicecompleldbyId"
							+ " LEFT JOIN User U2 ON U2.id = v.createdById"
							+ " WHERE v.vid = "+vehicle_ID+" AND v.companyId = "+companyId+" AND v.markForDelete = 0 ORDER BY v.service_id desc", Object[].class);
		}else {
			
			query = entityManager
					.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT "
							+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
							+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
							+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
							+ ",v.service_subscribeduser_name, v.last_servicedate, U.email, v.last_servicecompleldid, v.service_remider_howtimes"
							+ " , v.serviceStatusId, v.vehicleGroupId, U2.email, v.service_Number, v.companyId FROM ServiceReminder v "
							+ " INNER JOIN Vehicle T ON T.vid = v.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
							+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
							+ " LEFT JOIN User U ON U.id = v.last_servicecompleldbyId"
							+ " LEFT JOIN User U2 ON U2.id = v.createdById"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+id+" "
							+ " WHERE v.vid = "+vehicle_ID+" AND  v.companyId = "+companyId+" AND v.markForDelete = 0 ORDER BY v.service_id desc", Object[].class);
		}
		query.setFirstResult(0);
		query.setMaxResults(100);
		List<Object[]> results = query.getResultList();

		List<ServiceReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceReminderDto>();
			ServiceReminderDto list = null;
			for (Object[] result : results) {
				list = new ServiceReminderDto();
				
				list.setService_id((Long) result[0]);
				list.setVid((Integer) result[1]);
				list.setVehicle_registration((String) result[2]);
				list.setVehicle_Group((String) result[3]);
				list.setService_type((String) result[4]);
				list.setService_subtype((String) result[5] + " - "+ (String) result[6]);
				list.setMeter_interval((Integer) result[7]);
				list.setVehicle_currentOdometer((Integer) result[8]);
				list.setMeter_serviceodometer((Integer) result[9]);
				list.setMeter_threshold((Integer) result[10]);
				list.setMeter_servicethreshold_odometer((Integer) result[11]);
				list.setTime_interval((Integer) result[12]);
				list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
				list.setTime_intervalperiodId((short) result[13]);
				list.setTime_interval_currentdate( (Date) result[14]);
				list.setTime_servicedate((Date) result[15]);
				list.setTime_threshold((Integer) result[16]);
				list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
				list.setTime_thresholdperiodId((short) result[17]);
				list.setTime_servicethreshold_date((Date) result[18]);
				list.setService_emailnotification((String) result[19]);
				list.setService_subscribeduser((String) result[20]);
				list.setService_subscribeduser_name((String) result[21]);
				list.setLast_servicedate((Date) result[22]);
				list.setLast_servicecompleldby((String) result[23]);
				list.setLast_servicecompleldid((Long) result[24]);
				list.setService_remider_howtimes((Integer) result[25]);
				list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
				list.setServiceStatusId((short) result[26]);
				list.setVehicleGroupId((long) result[27]);
				list.setCreatedBy((String) result[28]);
				list.setService_Number((Long) result[29]);
				list.setCompanyId((int) result[30]);
				
				list.setService_subtype(list.getService_subtype().replace("null -", ""));
				
				Dtos.add(list);
			}
		}
		return Dtos;
	
	}

	@Transactional
	public void updateCurrentOdometerToServiceReminder(Integer vehicle_id, Integer oddmeter_Current, Integer companyId) throws Exception {
		serviceReminderDao.updateCurrentOdometerToServiceReminder(vehicle_id, oddmeter_Current, companyId);
	}

	@Transactional
	public List<ServiceReminder> listSearchWorkorderToServiceReminder(Integer vid, Integer JobTask, Integer SubJobTask, Integer companyId)
			throws Exception {
		//return serviceReminderDao.listSearchWorkorderToServiceReminder(vid, JobTask, SubJobTask, companyId);
		TypedQuery<ServiceReminder> query =	null;
		query = entityManager
				.createQuery("SELECT v FROM ServiceReminder v "
						+ " where v.vid = "+vid+" AND v.serviceTypeId = "+JobTask+" AND v.serviceSubTypeId = "+SubJobTask+" AND v.companyId = "+companyId+" AND v.markForDelete = 0", ServiceReminder.class);
		
		return  query.getResultList();

	}

	@Transactional
	public List<ServiceReminderDto> ReportServiceReminder(String querySelect, Integer companyId) throws Exception {

		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TypedQuery<Object[]> query =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager
					.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT"
							+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
							+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
							+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
							+ ",v.service_subscribeduser_name, v.last_servicedate, U.email, v.last_servicecompleldid, v.service_remider_howtimes"
							+ " , v.serviceStatusId, v.vehicleGroupId, U2.email, v.service_Number FROM ServiceReminder v "
							+ " INNER JOIN Vehicle T ON T.vid = v.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
							+ " LEFT JOIN User U ON U.id = v.last_servicecompleldbyId"
							+ " LEFT JOIN User U2 ON U2.id = v.createdById"
							+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
							+ " where v.markForDelete = 0 " + querySelect + " ", Object[].class);
		}else {
			
			query = entityManager
					.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT "
							+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
							+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
							+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
							+ ",v.service_subscribeduser_name, v.last_servicedate, U.email, v.last_servicecompleldid, v.service_remider_howtimes"
							+ " , v.serviceStatusId, v.vehicleGroupId, U2.email, v.service_Number FROM ServiceReminder v "
							+ " INNER JOIN Vehicle T ON T.vid = v.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
							+ " LEFT JOIN User U ON U.id = v.last_servicecompleldbyId"
							+ " LEFT JOIN User U2 ON U2.id = v.createdById"
							+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
							+ " where v.markForDelete = 0 " + querySelect + " ", Object[].class);
		}
		List<Object[]> results = query.getResultList();

		List<ServiceReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceReminderDto>();
			ServiceReminderDto list = null;
			for (Object[] result : results) {
				list = new ServiceReminderDto();
				
				list.setService_id((Long) result[0]);
				list.setVid((Integer) result[1]);
				list.setVehicle_registration((String) result[2]);
				list.setVehicle_Group((String) result[3]);
				list.setService_type((String) result[4]);
				list.setService_subtype((String) result[5] + " - "+ (String) result[6]);
				list.setMeter_interval((Integer) result[7]);
				list.setVehicle_currentOdometer((Integer) result[8]);
				list.setMeter_serviceodometer((Integer) result[9]);
				list.setMeter_threshold((Integer) result[10]);
				list.setMeter_servicethreshold_odometer((Integer) result[11]);
				list.setTime_interval((Integer) result[12]);
				list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
				list.setTime_intervalperiodId((short) result[13]);
				list.setTime_interval_currentdate( (Date) result[14]);
				list.setTime_servicedate((Date) result[15]);
				list.setTime_threshold((Integer) result[16]);
				list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
				list.setTime_thresholdperiodId((short) result[17]);
				list.setTime_servicethreshold_date((Date) result[18]);
				list.setService_emailnotification((String) result[19]);
				list.setService_subScribedUserId((String) result[20]);
				list.setService_subscribeduser_name((String) result[21]);
				list.setLast_servicedate((Date) result[22]);
				list.setLast_servicecompleldby((String) result[23]);
				list.setLast_servicecompleldid((Long) result[24]);
				list.setService_remider_howtimes((Integer) result[25]);
				list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
				list.setServiceStatusId((short) result[26]);
				list.setVehicleGroupId((long) result[27]);
				list.setCreatedBy((String) result[28]);
				list.setService_Number((Long) result[29]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	@Override
	public List<ServiceReminderDto> ReportOverDueServiceReminder(String querySelect, String Currentdate, Integer companyId) throws Exception {

		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TypedQuery<Object[]> query =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager
					.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT"
							+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
							+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
							+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
							+ ",v.service_subscribeduser_name, v.last_servicedate, U.email, v.last_servicecompleldid, v.service_remider_howtimes"
							+ " , v.serviceStatusId, v.vehicleGroupId, U2.email, v.service_Number FROM ServiceReminder v "
							+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
							+ " INNER JOIN Vehicle T ON T.vid = v.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
							+ " LEFT JOIN User U ON U.id = v.last_servicecompleldbyId"
							+ " LEFT JOIN User U2 ON U2.id = v.createdById"
							+ " where ( v.markForDelete  = 0 " + querySelect + " ) AND v.time_servicedate <= '"
							+ Currentdate + "'  OR  v.meter_serviceodometer <= v.vehicle_currentOdometer  " + querySelect
							+ "  ORDER BY v.time_servicedate ASC  ", Object[].class);
		}else {
			
			query = entityManager
					.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT "
							+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
							+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
							+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
							+ ",v.service_subscribeduser_name, v.last_servicedate, U.email, v.last_servicecompleldid, v.service_remider_howtimes"
							+ " , v.serviceStatusId, v.vehicleGroupId, U2.email, v.service_Number FROM ServiceReminder v "
							+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
							+ " INNER JOIN Vehicle T ON T.vid = v.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
							+ " LEFT JOIN User U ON U.id = v.last_servicecompleldbyId"
							+ " LEFT JOIN User U2 ON U2.id = v.createdById"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
							+ "where ( v.markForDelete  = 0 " + querySelect + " ) AND v.time_servicedate <= '"
							+ Currentdate + "'  OR  v.meter_serviceodometer <= v.vehicle_currentOdometer  " + querySelect
							+ "  ORDER BY v.time_servicedate ASC ", Object[].class);
		}
		List<Object[]> results = query.getResultList();

		List<ServiceReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceReminderDto>();
			ServiceReminderDto list = null;
			for (Object[] result : results) {
				list = new ServiceReminderDto();
				
				list.setService_id((Long) result[0]);
				list.setVid((Integer) result[1]);
				list.setVehicle_registration((String) result[2]);
				list.setVehicle_Group((String) result[3]);
				list.setService_type((String) result[4]);
				list.setService_subtype((String) result[5] + " - "+ (String) result[6]);
				list.setMeter_interval((Integer) result[7]);
				list.setVehicle_currentOdometer((Integer) result[8]);
				list.setMeter_serviceodometer((Integer) result[9]);
				list.setMeter_threshold((Integer) result[10]);
				list.setMeter_servicethreshold_odometer((Integer) result[11]);
				list.setTime_interval((Integer) result[12]);
				list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
				list.setTime_intervalperiodId((short) result[13]);
				list.setTime_interval_currentdate( (Date) result[14]);
				list.setTime_servicedate((Date) result[15]);
				list.setTime_threshold((Integer) result[16]);
				list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
				list.setTime_thresholdperiodId((short) result[17]);
				list.setTime_servicethreshold_date((Date) result[18]);
				list.setService_emailnotification((String) result[19]);
				list.setService_subScribedUserId((String) result[20]);
				list.setService_subscribeduser_name((String) result[21]);
				list.setLast_servicedate((Date) result[22]);
				list.setLast_servicecompleldby((String) result[23]);
				list.setLast_servicecompleldid((Long) result[24]);
				list.setService_remider_howtimes((Integer) result[25]);
				list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
				list.setServiceStatusId((short) result[26]);
				list.setVehicleGroupId((long) result[27]);
				list.setCreatedBy((String) result[28]);
				list.setService_Number((Long) result[29]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<ServiceReminderDto> ReportDueSoonServiceReminder(String querySelect, String Currentdate, Integer companyId) throws Exception {

		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TypedQuery<Object[]> query =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager
					.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT"
							+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
							+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
							+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
							+ ",v.service_subscribeduser_name, v.last_servicedate, U.email, v.last_servicecompleldid, v.service_remider_howtimes"
							+ " , v.serviceStatusId, v.vehicleGroupId, U2.email, v.service_Number FROM ServiceReminder v "
							+ " INNER JOIN Vehicle T ON T.vid = v.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
							+ " LEFT JOIN User U ON U.id = v.last_servicecompleldbyId"
							+ " LEFT JOIN User U2 ON U2.id = v.createdById"
							+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
							+ " where ( v.markForDelete = 0 " + querySelect + " ) AND v.time_servicethreshold_date <= '"
							+ Currentdate + "' AND  v.time_servicedate >= '" + Currentdate + "'  OR "
							+ "v.meter_servicethreshold_odometer <= v.vehicle_currentOdometer AND v.meter_serviceodometer >= v.vehicle_currentOdometer  "
							+ querySelect + " ORDER BY v.time_servicedate ASC ", Object[].class);
		}else {
			
			query = entityManager
					.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT "
							+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
							+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
							+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
							+ ",v.service_subscribeduser_name, v.last_servicedate, U.email, v.last_servicecompleldid, v.service_remider_howtimes"
							+ " , v.serviceStatusId, v.vehicleGroupId, U2.email, v.service_Number FROM ServiceReminder v "
							+ " INNER JOIN Vehicle T ON T.vid = v.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
							+ " LEFT JOIN User U ON U.id = v.last_servicecompleldbyId"
							+ " LEFT JOIN User U2 ON U2.id = v.createdById"
							+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
							+ " where ( v.markForDelete = 0 " + querySelect + " ) AND v.time_servicethreshold_date <= '"
							+ Currentdate + "' AND  v.time_servicedate >= '" + Currentdate + "'  OR "
							+ "v.meter_servicethreshold_odometer <= v.vehicle_currentOdometer AND v.meter_serviceodometer >= v.vehicle_currentOdometer  "
							+ querySelect + " ORDER BY v.time_servicedate ASC ", Object[].class);
		}
		List<Object[]> results = query.getResultList();

		List<ServiceReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceReminderDto>();
			ServiceReminderDto list = null;
			for (Object[] result : results) {
				list = new ServiceReminderDto();
				
				list.setService_id((Long) result[0]);
				list.setVid((Integer) result[1]);
				list.setVehicle_registration((String) result[2]);
				list.setVehicle_Group((String) result[3]);
				list.setService_type((String) result[4]);
				list.setService_subtype((String) result[5] + " - "+ (String) result[6]);
				list.setMeter_interval((Integer) result[7]);
				list.setVehicle_currentOdometer((Integer) result[8]);
				list.setMeter_serviceodometer((Integer) result[9]);
				list.setMeter_threshold((Integer) result[10]);
				list.setMeter_servicethreshold_odometer((Integer) result[11]);
				list.setTime_interval((Integer) result[12]);
				list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
				list.setTime_intervalperiodId((short) result[13]);
				list.setTime_interval_currentdate( (Date) result[14]);
				list.setTime_servicedate((Date) result[15]);
				list.setTime_threshold((Integer) result[16]);
				list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
				list.setTime_thresholdperiodId((short) result[17]);
				list.setTime_servicethreshold_date((Date) result[18]);
				list.setService_emailnotification((String) result[19]);
				list.setService_subScribedUserId((String) result[20]);
				list.setService_subscribeduser_name((String) result[21]);
				list.setLast_servicedate((Date) result[22]);
				list.setLast_servicecompleldby((String) result[23]);
				list.setLast_servicecompleldid((Long) result[24]);
				list.setService_remider_howtimes((Integer) result[25]);
				list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
				list.setServiceStatusId((short) result[26]);
				list.setVehicleGroupId((long) result[27]);
				list.setCreatedBy((String) result[28]);
				list.setService_Number((Long) result[29]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceReminderService#
	 * OverDue_DueSoon_Subcribe_ServiceReminder(java.util.Date)
	 */
	@Transactional
	public List<ServiceReminderDto> OverDue_DueSoon_Subcribe_ServiceReminder(Date toDate) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.service_id, R.vid, V.vehicle_registration, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT, R.time_servicedate, "
						+ "R.meter_serviceodometer, R.vehicle_currentOdometer, R.serviceStatusId, R.service_Number FROM ServiceReminder AS R "
						+ " LEFT JOIN JobType JT ON JT.Job_id = R.serviceTypeId"
						+ " INNER JOIN Vehicle V ON V.vid = R.vid"
						+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = R.serviceSubTypeId"
						+ " where ( R.time_servicedate <= '"
						+ toDate + "'  " + " OR R.meter_serviceodometer <= R.vehicle_currentOdometer"
						+ " OR R.time_servicethreshold_date <= '" + toDate + "' AND  R.time_servicedate >= '" + toDate
						+ "'  "
						+ " OR R.meter_servicethreshold_odometer <= R.vehicle_currentOdometer AND R.meter_serviceodometer >= R.vehicle_currentOdometer) AND R.companyId = "+userDetails.getCompany_id()+" AND R.markForDelete = 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<ServiceReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceReminderDto>();
			ServiceReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new ServiceReminderDto();

				renewal.setService_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setService_type((String) result[3]);
				renewal.setService_subtype((String) result[4] +" - "+ (String) result[5]);
				renewal.setTime_servicedate((Date) result[6]);
				renewal.setMeter_serviceodometer((Integer) result[7]);
				renewal.setVehicle_currentOdometer((Integer) result[8]);
				renewal.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[9]));
				renewal.setService_Number((Long) result[10]);

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceReminderService#
	 * update_Vehicle_Group_USING_Vehicle_Id(java.lang.String,
	 * java.lang.Integer)
	 */
	@Transactional
	public void update_Vehicle_Group_USING_Vehicle_Id(String Vehicle_Group, Integer vehicle_id, long vehicleGroupId, Integer companyId) {

		serviceReminderDao.update_Vehicle_Group_USING_Vehicle_Id(Vehicle_Group, vehicle_id, vehicleGroupId, companyId);
	}
	
	/*@Override
	public List<ServiceReminder> listServiceReminder(CustomUserDetails userDetails) throws Exception {
		try {
			TypedQuery<Object[]> query =	null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, v.vehicle_registration, v.vehicle_Group, v.service_type, v.service_subtype "
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
								+ " , v.time_interval, v.time_intervalperiod, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiod, v.time_servicethreshold_date, v.service_emailnotification, v.service_subscribeduser"
								+ ",v.service_subscribeduser_name, v.last_servicedate, v.last_servicecompleldby, v.last_servicecompleldid, v.service_remider_howtimes" 
								+ " , v.servicestatus, v.vehicleGroupId, v.createdBy, v.service_Number FROM ServiceReminder v"
								+ " WHERE v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0", Object[].class);
				
			}else {
				
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, v.vehicle_registration, v.vehicle_Group, v.service_type, v.service_subtype "
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
								+ " , v.time_interval, v.time_intervalperiod, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiod, v.time_servicethreshold_date, v.service_emailnotification, v.service_subscribeduser"
								+ ",v.service_subscribeduser_name, v.last_servicedate, v.last_servicecompleldby, v.last_servicecompleldid, v.service_remider_howtimes" 
								+ " , v.servicestatus, v.vehicleGroupId, v.createdBy, v.service_Number FROM ServiceReminder v"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
								+ " WHERE v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0", Object[].class);
				
			}
		List<Object[]> results = query.getResultList();

		List<ServiceReminder> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceReminder>();
			ServiceReminder list = null;
			for (Object[] result : results) {
				list = new ServiceReminder();
				
				list.setService_id((Long) result[0]);
				list.setVid((Integer) result[1]);
				list.setVehicle_registration((String) result[2]);
				list.setVehicle_Group((String) result[3]);
				list.setService_type((String) result[4]);
				list.setService_subtype((String) result[5]);
				list.setMeter_interval((Integer) result[6]);
				list.setVehicle_currentOdometer((Integer) result[7]);
				list.setMeter_serviceodometer((Integer) result[8]);
				list.setMeter_threshold((Integer) result[9]);
				list.setMeter_servicethreshold_odometer((Integer) result[10]);
				list.setTime_interval((Integer) result[11]);
				list.setTime_intervalperiod((String) result[12]);
				list.setTime_interval_currentdate( (Date) result[13]);
				list.setTime_servicedate((Date) result[14]);
				list.setTime_threshold((Integer) result[15]);
				list.setTime_thresholdperiod((String) result[16]);
				list.setTime_servicethreshold_date((Date) result[17]);
				list.setService_emailnotification((String) result[18]);
				list.setService_subscribeduser((String) result[19]);
				list.setService_subscribeduser_name((String) result[20]);
				list.setLast_servicedate((Date) result[21]);
				list.setLast_servicecompleldby((String) result[22]);
				list.setLast_servicecompleldid((Long) result[23]);
				list.setService_remider_howtimes((Integer) result[24]);
				list.setServicestatus((String) result[25]);
				list.setVehicleGroupId((long) result[26]);
				list.setCreatedBy((String) result[27]);
				list.setService_Number((Long) result[28]);
				
				Dtos.add(list);
			}
		}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}*/
	
	@Override
	public List<ServiceReminderDto> SearchServiceReminderByNumber(String Search, CustomUserDetails	userDetails) throws Exception {
		List<ServiceReminderDto> Dtos = null;
		try {
			TypedQuery<Object[]> query =	null;
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT " 
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
								+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " LEFT JOIN User U ON U.id = v.createdById"
								+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
								+ " where ( lower(v.service_Number) Like ('%" + Search
								+ "%') OR lower(T.vehicle_registration) Like ('%" + Search + "%') OR lower(JT.Job_Type) Like ('%"
								+ Search + "%') OR lower(JST.Job_ROT_number) Like ('%" + Search + "%') ) AND v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 ", Object[].class);
			
			}else {
				
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT " 
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
								+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " LEFT JOIN User U ON U.id = v.createdById"
								+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
								+ " where ( lower(v.service_Number) Like ('%" + Search
								+ "%') OR lower(T.vehicle_registration) Like ('%" + Search + "%') OR lower(JT.Job_Type) Like ('%"
								+ Search + "%') OR lower(JST.Job_ROT_number) Like ('%" + Search + "%') ) AND v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 ", Object[].class);
				
			}
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setVid((Integer) result[1]);
					list.setVehicle_registration((String) result[2]);
					list.setVehicle_Group((String) result[3]);
					list.setService_type((String) result[4]);
					if(result[5] != null) {
						list.setService_subtype((String) result[5]+" - "+(String) result[6]);
					}else {
						list.setService_subtype((String) result[6]);
					}
					list.setMeter_interval((Integer) result[7]);
					list.setVehicle_currentOdometer((Integer) result[8]);
					list.setMeter_serviceodometer((Integer) result[9]);
					list.setMeter_threshold((Integer) result[10]);
					list.setMeter_servicethreshold_odometer((Integer) result[11]);
					list.setTime_interval((Integer) result[12]);
					list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
					list.setTime_intervalperiodId((short) result[13]);
					list.setTime_interval_currentdate( (Date) result[14]);
					list.setTime_servicedate((Date) result[15]);
					list.setTime_threshold((Integer) result[16]);
					list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
					list.setTime_thresholdperiodId((short) result[17]);
					list.setTime_servicethreshold_date((Date) result[18]);
					list.setService_emailnotification((String) result[19]);
					list.setService_subScribedUserId((String) result[20]);
					list.setService_subscribeduser_name((String) result[21]);
					list.setLast_servicedate((Date) result[22]);
					list.setLast_servicecompleldby((String) result[23]);
					list.setLast_servicecompleldid((Long) result[24]);
					list.setService_remider_howtimes((Integer) result[25]);
					list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
					list.setServiceStatusId((short) result[26]);
					list.setVehicleGroupId((long) result[27]);
					list.setCreatedBy((String) result[28]);
					list.setService_Number((Long) result[29]);
					
					Dtos.add(list);
				}
			}
			}
				return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ServiceReminderDto getServiceReminderByNumber(Long Service_id, CustomUserDetails userDetails) throws Exception {
		try {
			Query query	= null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				 query = entityManager.createQuery(
						"SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT" 
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
								+ ", v.service_subscribeduser_name, v.last_servicedate, U.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U2.email, v.service_Number, v.created, v.lastupdated, U3.email"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " LEFT JOIN User U ON U.id = v.last_servicecompleldbyId"
								+ " LEFT JOIN User U2 ON U2.id = v.createdById"
								+ " LEFT JOIN User U3 ON U3.id = v.lastModifiedById"
								+ " where v.service_Number = "+Service_id+" AND  v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0");
			}else {

				 query = entityManager.createQuery(
						"SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT" 
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
								+ ", v.service_subscribeduser_name, v.last_servicedate, U.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U2.email, v.service_Number, v.created, v.lastupdated, U3.email"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+""
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " LEFT JOIN User U ON U.id = v.last_servicecompleldbyId"
								+ " LEFT JOIN User U2 ON U2.id = v.createdById"
								+ " LEFT JOIN User U3 ON U3.id = v.lastModifiedById"
								+ " where v.service_Number = "+Service_id+" AND  v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0");
			
			}
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			ServiceReminderDto list;
			if (result != null) {
				list = new ServiceReminderDto();
				list.setService_id((Long) result[0]);
				list.setVid((Integer) result[1]);
				list.setVehicle_registration((String) result[2]);
				list.setVehicle_Group((String) result[3]);
				list.setService_type((String) result[4]);
				list.setService_subtype((String) result[5] + " - "+ (String) result[6]);
				list.setMeter_interval((Integer) result[7]);
				list.setVehicle_currentOdometer((Integer) result[8]);
				list.setMeter_serviceodometer((Integer) result[9]);
				list.setMeter_threshold((Integer) result[10]);
				list.setMeter_servicethreshold_odometer((Integer) result[11]);
				list.setTime_interval((Integer) result[12]);
				list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
				list.setTime_intervalperiodId((short) result[13]);
				list.setTime_interval_currentdate( (Date) result[14]);
				list.setTime_servicedate((Date) result[15]);
				list.setTime_threshold((Integer) result[16]);
				list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
				list.setTime_thresholdperiodId((short) result[17]);
				list.setTime_servicethreshold_date((Date) result[18]);
				list.setService_emailnotification((String) result[19]);
				list.setService_subScribedUserId((String) result[20]);
				list.setService_subscribeduser_name((String) result[21]);
				list.setLast_servicedate((Date) result[22]);
				list.setLast_servicecompleldby((String) result[23]);
				list.setLast_servicecompleldid((Long) result[24]);
				list.setService_remider_howtimes((Integer) result[25]);
				list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
				list.setServiceStatusId((short) result[26]);
				list.setVehicleGroupId((long) result[27]);
				list.setCreatedBy((String) result[28]);
				list.setService_Number((Long) result[29]);
				list.setCreatedOn((Date) result[30]);
				list.setLastupdatedOn((Date) result[31]);
				list.setLastModifiedBy((String) result[32]);
				
			} else {
				return null;
			}

			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getServiceReminderCalendarDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails						userDetails				= null;
		ServiceReminderDto						serviceReminderDto		= null;
		List<ServiceReminderDto> 				serviceReminderList		= null;
		List<ServiceReminderWorkOrderHistory> 	serviceWorkOrderList	= null;
		List<ServiceReminderDto> 				serviceReminderOdoList	= null;
		try {
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				serviceReminderDto	= new ServiceReminderDto();
				
				serviceReminderDto.setCompanyId(userDetails.getCompany_id());
				serviceReminderDto.setFromDate(valueObject.getString("fromDate"));
				serviceReminderDto.setToDate(valueObject.getString("toDate"));
				serviceReminderDto.setUserId(userDetails.getId());
				
				serviceReminderList		= ServiceReminderDaoImpl.getServiceReminderList(serviceReminderDto);
				serviceReminderOdoList	= ServiceReminderDaoImpl.getServiceReminderListByOdometer(serviceReminderDto);
				serviceWorkOrderList	= ServiceReminderDaoImpl.getServiceWorkOrderList(serviceReminderDto);
				
				valueObject.clear();
				valueObject.put("serviceReminder", getFinalServiceReminderList(serviceReminderList, serviceWorkOrderList, serviceReminderOdoList));
				
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private ArrayList<CalenderEvent> getFinalServiceReminderList(List<ServiceReminderDto> list, List<ServiceReminderWorkOrderHistory> 	serviceWorkOrderList, List<ServiceReminderDto> odoList) throws Exception {
		CalenderEvent				calenderEvent		= null;
		ArrayList<CalenderEvent>    calenderEventList	= null;
		try {
			calenderEventList	= new ArrayList<>();
			
			if(list != null && list.size() > 0) {
				for (ServiceReminderDto reminderDto : list) {
					calenderEvent	= new CalenderEvent();

					calenderEvent.setEventDay(AttendanceDay.format(reminderDto.getTime_servicethreshold_date()));
					calenderEvent.setEventMonth("" + (Integer.parseInt(AttendanceMonth.format(reminderDto.getTime_servicethreshold_date())) - 1));
					calenderEvent.setEventYear(AttendanceYear.format(reminderDto.getTime_servicethreshold_date()));
					calenderEvent.setEventTitle("SR-"+reminderDto.getService_Number());
					calenderEvent.setEventUrl("ShowService.in?service_id="+reminderDto.getService_id());
					calenderEvent.setEventColor("Black");
					calenderEvent.setEventDate(sqlDateFormat.format(reminderDto.getTime_servicethreshold_date()));
					
					calenderEventList.add(calenderEvent);
				}
			}
			
			if(odoList != null && odoList.size() > 0) {
				for (ServiceReminderDto reminderDto : odoList) {
					calenderEvent	= new CalenderEvent();

					calenderEvent.setEventDay(AttendanceDay.format(reminderDto.getTime_servicethreshold_date()));
					calenderEvent.setEventMonth("" + (Integer.parseInt(AttendanceMonth.format(reminderDto.getTime_servicethreshold_date())) - 1));
					calenderEvent.setEventYear(AttendanceYear.format(reminderDto.getTime_servicethreshold_date()));
					calenderEvent.setEventTitle("SR-"+reminderDto.getService_Number());
					calenderEvent.setEventUrl("ShowService.in?service_id="+reminderDto.getService_id());
					calenderEvent.setEventColor("Black");
					calenderEvent.setEventDate(sqlDateFormat.format(reminderDto.getTime_servicethreshold_date()));
					
					calenderEventList.add(calenderEvent);
				}
			}
			
			if(serviceWorkOrderList != null && serviceWorkOrderList.size() > 0) {
				for (ServiceReminderWorkOrderHistory reminderDto : serviceWorkOrderList) {
					calenderEvent	= new CalenderEvent();

					calenderEvent.setEventDay(AttendanceDay.format(reminderDto.getServiceThresholdDate()));
					calenderEvent.setEventMonth("" + (Integer.parseInt(AttendanceMonth.format(reminderDto.getServiceThresholdDate())) - 1));
					calenderEvent.setEventYear(AttendanceYear.format(reminderDto.getServiceThresholdDate()));
					calenderEvent.setEventTitle("SR-"+reminderDto.getService_Number());
					calenderEvent.setEventUrl("ShowService.in?service_id="+reminderDto.getService_id());
					calenderEvent.setEventColor("cyan");
					calenderEvent.setEventDate(sqlDateFormat.format(reminderDto.getServiceThresholdDate()));
					calenderEvent.setEventHistory(true);
					
					calenderEventList.add(calenderEvent);
				}
			}
			
			return calenderEventList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ServiceReminderWorkOrderHistory getServiceWorkHistory(Long workOrderId) throws Exception {
		try {
			return historyRepository.getServiceWorkOrder(workOrderId);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getServiceReminderListOftheDay(ValueObject valueObject) throws Exception {
		String 									selectedDate			= null; 
		Timestamp								selectedTimeStamp		= null;
		ServiceReminderDto						serviceReminderDto		= null;
		CustomUserDetails						userDetails				= null;
		List<ServiceReminderDto> 				serviceReminderList		= null;
		List<ServiceReminderDto> 				serviceWorkOrderList	= null;
		List<ServiceReminderDto> 				serviceReminderOdoList	= null;
		try {
			selectedDate		= valueObject.getString("Date");
			selectedTimeStamp	= DateTimeUtility.getCalendarTimeFromTimestamp(DateTimeUtility.getDateTimeFromStr(selectedDate, DateTimeUtility.YYYY_MM_DD),0,0,0,0);
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			serviceReminderDto	= new ServiceReminderDto();
			
			serviceReminderDto.setFromDate(DateTimeUtility.getDateFromTimeStamp(selectedTimeStamp, DateTimeUtility.YYYY_MM_DD));
			serviceReminderDto.setToDate(DateTimeUtility.getDateFromTimeStamp(selectedTimeStamp, DateTimeUtility.YYYY_MM_DD)+InventoryTransferDto.DAY_END_TIME);
			serviceReminderDto.setCompanyId(userDetails.getCompany_id());
			serviceReminderDto.setUserId(userDetails.getId());
			
			serviceReminderList		= ServiceReminderDaoImpl.getServiceReminderListOfDay(serviceReminderDto);
			serviceReminderOdoList	= ServiceReminderDaoImpl.getServiceReminderListByOdometerOfDay(serviceReminderDto);
			serviceWorkOrderList	= ServiceReminderDaoImpl.getServiceWorkOrderListOfDay(serviceReminderDto);
			
			
			valueObject.clear();
			valueObject.put("serviceReminderfinalList", getFinalServiceDetailsOnDay(serviceReminderList, serviceReminderOdoList, serviceWorkOrderList));
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	private List<ServiceReminderDto>  getFinalServiceDetailsOnDay(List<ServiceReminderDto> list, List<ServiceReminderDto> 	serviceWorkOrderList, List<ServiceReminderDto> odoList){
		
		 List<ServiceReminderDto>		serviceReminderfinalList		= null;
		 ServiceReminderDto				serviceReminderDto				= null;
		try {
			
			serviceReminderfinalList	= new ArrayList<>();
			
			if(list != null && list.size() > 0) {
				for (ServiceReminderDto reminderDto : list) {
					serviceReminderDto = new ServiceReminderDto();
					serviceReminderDto.setService_id(reminderDto.getService_id());
					serviceReminderDto.setService_Number(reminderDto.getService_Number());
					serviceReminderDto.setVid(reminderDto.getVid());
					serviceReminderDto.setVehicle_registration(reminderDto.getVehicle_registration());
					if(reminderDto.getTime_servicethreshold_date() != null)
						serviceReminderDto.setThreshHoldDate(dateFormat.format(reminderDto.getTime_servicethreshold_date()));
					if(reminderDto.getTime_servicedate() != null)
						serviceReminderDto.setServceDate(dateFormat.format(reminderDto.getTime_servicedate()));
					serviceReminderDto.setService_type(reminderDto.getService_type());
					serviceReminderDto.setWorkOrderId(reminderDto.getWorkOrderId());
					if(reminderDto.getWorkOrderNumber() != null) {
						serviceReminderDto.setWorkOrderNumberStr(reminderDto.getWorkOrderNumber()+"");
					}else {
						serviceReminderDto.setWorkOrderNumberStr("--");
					}
						
					serviceReminderDto.setServiceOdometer(reminderDto.getServiceOdometer());
					if(reminderDto.getServicedOn() != null) {
						serviceReminderDto.setWorkOrderCompletedOn(dateFormat.format(reminderDto.getServicedOn()));
					}else {
						serviceReminderDto.setWorkOrderCompletedOn("--");
					}
					
					
					serviceReminderfinalList.add(serviceReminderDto);
				}
			}
			
			if(odoList != null && odoList.size() > 0) {
				for (ServiceReminderDto reminderDto : odoList) {
					serviceReminderDto = new ServiceReminderDto();
					serviceReminderDto.setService_id(reminderDto.getService_id());
					serviceReminderDto.setService_Number(reminderDto.getService_Number());
					serviceReminderDto.setVid(reminderDto.getVid());
					serviceReminderDto.setVehicle_registration(reminderDto.getVehicle_registration());
					if(reminderDto.getTime_servicethreshold_date() != null)
						serviceReminderDto.setThreshHoldDate(dateFormat.format(reminderDto.getTime_servicethreshold_date()));
					if(reminderDto.getTime_servicedate() != null)
						serviceReminderDto.setServceDate(dateFormat.format(reminderDto.getTime_servicedate()));
					serviceReminderDto.setService_type(reminderDto.getService_type());
					serviceReminderDto.setWorkOrderId(reminderDto.getWorkOrderId());
					if(reminderDto.getWorkOrderNumber() != null) {
						serviceReminderDto.setWorkOrderNumberStr(reminderDto.getWorkOrderNumber()+"");
					}else {
						serviceReminderDto.setWorkOrderNumberStr("--");
					}
						
					serviceReminderDto.setServiceOdometer(reminderDto.getServiceOdometer());
					if(reminderDto.getServicedOn() != null) {
						serviceReminderDto.setWorkOrderCompletedOn(dateFormat.format(reminderDto.getServicedOn()));
					}else {
						serviceReminderDto.setWorkOrderCompletedOn("--");
					}
					
					
					serviceReminderfinalList.add(serviceReminderDto);
				}
			}
			
			if(serviceWorkOrderList != null && serviceWorkOrderList.size() > 0) {
				for (ServiceReminderDto reminderDto : serviceWorkOrderList) {
					serviceReminderDto = new ServiceReminderDto();
					serviceReminderDto.setService_id(reminderDto.getService_id());
					serviceReminderDto.setService_Number(reminderDto.getService_Number());
					serviceReminderDto.setVid(reminderDto.getVid());
					serviceReminderDto.setVehicle_registration(reminderDto.getVehicle_registration());
					if(reminderDto.getTime_servicethreshold_date() != null)
						serviceReminderDto.setThreshHoldDate(dateFormat.format(reminderDto.getTime_servicethreshold_date()));
					if(reminderDto.getTime_servicedate() != null)
						serviceReminderDto.setServceDate(dateFormat.format(reminderDto.getTime_servicedate()));
					serviceReminderDto.setService_type(reminderDto.getService_type());
					serviceReminderDto.setWorkOrderId(reminderDto.getWorkOrderId());
					if(reminderDto.getWorkOrderNumber() != null) {
						serviceReminderDto.setWorkOrderNumberStr(reminderDto.getWorkOrderNumber()+"");
					}else {
						serviceReminderDto.setWorkOrderNumberStr("--");
					}
						
					serviceReminderDto.setServiceOdometer(reminderDto.getServiceOdometer());
					if(reminderDto.getServicedOn() != null) {
						serviceReminderDto.setWorkOrderCompletedOn(dateFormat.format(reminderDto.getServicedOn()));
					}else {
						serviceReminderDto.setWorkOrderCompletedOn("--");
					}
					
					
					serviceReminderfinalList.add(serviceReminderDto);
				}
			}
			
			return serviceReminderfinalList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getReminderStatusForOverdue(Integer vid) throws Exception {
		ValueObject						valueObject				= null;
		String 							currentDate				= null;
		List<ServiceReminderDto>		serviceReminderList		= null;
		String							overDueReminder			= "";
		try {
			
			currentDate	= DateTimeUtility.getDateFromTimeStamp(new Timestamp(System.currentTimeMillis()), DateTimeUtility.YYYY_MM_DD);	
			
			serviceReminderList	= ServiceReminderDaoImpl.getReminderStatusForOverdue(vid, currentDate, currentDate+InventoryTransferDto.DAY_END_TIME);
			
			if(serviceReminderList != null && serviceReminderList.size() > 0) {
				valueObject	= new ValueObject();
				for(ServiceReminderDto reminderDto : serviceReminderList) {
					overDueReminder	+= "SR-"+reminderDto.getService_Number() +",";
				}
				valueObject.put("overDueReminder", overDueReminder);
			}
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Page<ServiceReminder> geServiceReminderOverDuePage(Timestamp toDate, Integer pageNumber, CustomUserDetails userDetails)
			throws Exception {
		
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		try {
			
			boolean	vehicleGWisePermission	= companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
				if(!vehicleGWisePermission) {
					
					return serviceReminderDao.getServiceReminderOverduePage(toDate , userDetails.getCompany_id(), pageable);
				}
				
				return serviceReminderDao.getServiceReminderOverduePage(toDate, userDetails.getId(),userDetails.getCompany_id(), pageable);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Page<ServiceReminder> geServiceReminderOverDuePageGByVid(Timestamp toDate, Integer pageNumber,
			CustomUserDetails userDetails) throws Exception {
		
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE_15);
		try {
				boolean	vehicleGWisePermission	= companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
				if(!vehicleGWisePermission) {
					return serviceReminderDao.getServiceReminderOverduePageGByVid(toDate , userDetails.getCompany_id(), pageable);
				}
				
				return serviceReminderDao.getServiceReminderOverduePageGByVid(toDate, userDetails.getId(),userDetails.getCompany_id(), pageable);
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public Page<ServiceReminder> geServiceReminderTodaysOverDuePage(Timestamp toDate, Integer pageNumber, CustomUserDetails userDetails)
			throws Exception {
		
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		try {
			
			boolean	vehicleGWisePermission	= companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
				if(!vehicleGWisePermission) {
					
					return serviceReminderDao.getServiceReminderTodaysOverduePage(toDate , userDetails.getCompany_id(), pageable);
				}
				
				return serviceReminderDao.getServiceReminderTodaysOverduePage(toDate, userDetails.getId(),userDetails.getCompany_id(), pageable);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Page<ServiceReminder> geServiceReminderTodaysOverDuePageGByVid(Timestamp toDate, Integer pageNumber,
			CustomUserDetails userDetails) throws Exception {
		
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE_15);
		try {
				boolean	vehicleGWisePermission	= companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
				if(!vehicleGWisePermission) {
					return serviceReminderDao.getServiceReminderTodaysOverduePageGByVid(toDate , userDetails.getCompany_id(), pageable);
				}
				
				return serviceReminderDao.getServiceReminderTodaysOverduePageGByVid(toDate, userDetails.getId(),userDetails.getCompany_id(), pageable);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public List<ServiceReminderDto> OnlyVehicleServiceReminderListByDate(Integer vehicle_ID, String currentDate, String dueSoon,Integer companyId) throws Exception {
		
		// return ServiceReminderDao.listServiceReminder();
		TypedQuery<Object[]> query =	null;
			query = entityManager
					.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration,  JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT "
							+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
							+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold "
							+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.last_servicedate"
							+ " , v.service_Number, v.serviceTypeId, v.serviceSubTypeId, SP.programName"
							+ " FROM ServiceReminder v "
							+ " INNER JOIN Vehicle T ON T.vid = v.vid "
							+ " INNER JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
							+ " INNER JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
							+ " LEFT JOIN VehicleServiceProgram SP ON SP.vehicleServiceProgramId = v.serviceProgramId"
							+ " WHERE v.vid = "+vehicle_ID+" "
							+ " AND (v.time_servicedate BETWEEN '"+currentDate+"' AND '"+dueSoon+"' OR (v.time_servicedate <= '"+currentDate+"' OR v.meter_serviceodometer <= v.vehicle_currentOdometer)) "
							+ "AND v.companyId = "+companyId+" AND v.markForDelete = 0 ORDER BY v.service_id desc", Object[].class);
		
		query.setFirstResult(0);
		query.setMaxResults(100);
		List<Object[]> results = query.getResultList();

		List<ServiceReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceReminderDto>();
			ServiceReminderDto list = null;
			for (Object[] result : results) {
				list = new ServiceReminderDto();
				
				list.setService_id((Long) result[0]);
				list.setVid((Integer) result[1]);
				list.setVehicle_registration((String) result[2]);
				list.setService_type((String) result[3]);
				list.setService_subtype((String) result[4] + " - "+ (String) result[5]);
				list.setMeter_interval((Integer) result[6]);
				list.setVehicle_currentOdometer((Integer) result[7]);
				list.setMeter_serviceodometer((Integer) result[8]);
				list.setMeter_threshold((Integer) result[9]);
				list.setMeter_servicethreshold_odometer((Integer) result[10]);
				list.setTime_interval((Integer) result[11]);
				list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[12]));
				list.setTime_intervalperiodId((short) result[12]);
				list.setTime_interval_currentdate( (Date) result[13]);
				list.setTime_servicedate((Date) result[14]);
				list.setTime_threshold((Integer) result[15]);
				list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[16]));
				list.setTime_thresholdperiodId((short) result[16]);
				list.setTime_servicethreshold_date((Date) result[17]);
				list.setService_emailnotification((String) result[18]);
				list.setLast_servicedate((Date) result[19]);
				list.setService_Number((Long) result[20]);
				list.setServiceTypeId((Integer) result[21]);
				list.setServiceSubTypeId((Integer) result[22]);
				
				list.setWorkOrderNumberStr((String) result[23]);
				
				if((String) result[23] != null)
					list.setServiceProgram((String) result[23]);
				else
					list.setServiceProgram("");
					
				list.setService_subtype(list.getService_subtype().replace("null -", ""));
				
				Dtos.add(list);
			}
		}
		return Dtos;
	
	}

	@Override
	public List<ServiceReminderDto> getServiceReminderByThresholdDate(String currentDate, String companyId)
			throws Exception {
		
		//return ServiceReminderDao.getServiceReminderByThresholdDate(currentDate,companyId);
		
		TypedQuery<Object[]> query =	null;
			query = entityManager
					.createQuery("SELECT v.service_id, v.time_servicedate, v.time_servicethreshold_date, v.vid, v.companyId, "
							+ " v.serviceTypeId, v.serviceSubTypeId, v.service_Number, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_servicethreshold_odometer, "
							+ " v.service_subScribedUserId, vh.vehicle_registration, c.company_name, jt.Job_Type, jst.Job_ROT "
							+ "	FROM ServiceReminder v "
							+ " INNER JOIN Vehicle vh ON vh.vid = v.vid "
							+ " INNER JOIN Company c  ON c.company_id = v.companyId "
							+ " INNER JOIN JobType jt ON jt.Job_id = v.serviceTypeId "
							+ " INNER JOIN JobSubType jst ON jst.Job_Subid = v.serviceSubTypeId "
							+ " WHERE v.companyId IN ("+companyId+") AND v.time_servicethreshold_date= '"+currentDate+"' "
							+ " OR (v.vehicle_currentOdometer >= v.meter_servicethreshold_odometer AND v.vehicle_currentOdometer <= v.meter_serviceodometer AND v.companyId IN ("+companyId+") ) "
							+ "	AND  v.markForDelete = 0", Object[].class);
		
		
		List<Object[]> results = query.getResultList();

		List<ServiceReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceReminderDto>();
			ServiceReminderDto list = null;
			for (Object[] result : results) {
				list = new ServiceReminderDto();
				
				list.setService_id((Long) result[0]);
				list.setTime_servicedate((Date) result[1]);
				list.setTime_servicethreshold_date((Date) result[2]);
				list.setVid((Integer) result[3]);
				list.setCompanyId((Integer) result[4]);
				if(result[5] != null) {
				list.setServiceTypeId((Integer) result[5]);
				}
				if(result[6] != null) {
				list.setServiceSubTypeId((Integer) result[6]);
				}
				list.setService_Number((long) result[7]);
				list.setVehicle_currentOdometer((Integer) result[8]);
				list.setMeter_serviceodometer((Integer) result[9]);
				list.setMeter_servicethreshold_odometer((Integer) result[10]);
				list.setService_subScribedUserId((String) result[11]);
				list.setVehicle_registration((String) result[12]);
				list.setCompanyName((String) result[13]);
				list.setService_type((String) result[14]);
				list.setService_subtype((String) result[15]);
				
				
				Dtos.add(list);
			}
		}
		return Dtos;
		
	}
	
	@Override
	public Long getTotalServiceReminderCount(String startDate, String endDate,Integer companyId) throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				" SELECT COUNT(SE.service_id) "
						+ " From ServiceReminder as SE "
						+"  INNER JOIN Vehicle AS V ON V.vid = SE.vid  AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
						+ " WHERE SE.created between '"+startDate+"' AND '"+endDate+"' AND SE.markForDelete = 0 "
						+ " AND SE.companyId = "+companyId+" ",
						Object[].class);
		
		Long count = (long) 0;
		try {
			
			if((Long) queryt.getSingleResult() != null) {
			count = (Long) queryt.getSingleResult();
			}
			
		} catch (NoResultException nre) {
			}

		return count;
	}
	
		@Override
		public HashMap<Integer, Long> getALLEmailServiceReminderDailyWorkStatus(String startDate, String endDate)
				throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(SE.service_id), SE.companyId "
								+ " From ServiceReminder as SE "
								+"  INNER JOIN Vehicle AS V ON V.vid = SE.vid  AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
								+ " WHERE SE.created between '"+startDate+"' AND '"+endDate+"' AND SE.markForDelete = 0 GROUP BY SE.companyId ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();

				if (results != null && !results.isEmpty()) {
					
					map	= new HashMap<>();
					
					for (Object[] result : results) {
						
						map.put((Integer)result[1], (Long)result[0]);
						
					}
				}
				
				return map;
			} catch (Exception e) {
				throw e;
			}
		}	
		
		//Email System for Manager Start Service Reminder
				@Override
				public List<CompanyDto> getALLEmailServiceReminderDailyWorkForManagers(String startDate, String endDate)
						throws Exception {
								
					try{
						TypedQuery<Object[]> typedQuery = null;

											
						typedQuery = entityManager.createQuery(
								" SELECT COUNT(SR.service_id), C.company_id, C.company_name "
										+ "From ServiceReminder as SR "
										+ " INNER JOIN Company AS C on C.company_id = SR.companyId  "
										+ " WHERE SR.created between '"+startDate+"' AND '"+endDate+"'  AND SR.markForDelete = 0 "
										+ " Group by SR.companyId ",
										Object[].class);
						
						
						List<Object[]> results = typedQuery.getResultList();

						List<CompanyDto> serviceReminder = null;
						if (results != null && !results.isEmpty()) {
							
							serviceReminder = new ArrayList<CompanyDto>();
							CompanyDto list = null;
							for (Object[] result : results) {
								list = new CompanyDto();
							if(result !=null) {
								
								list.setTripSheetCount((long) 0);
								list.setRenewalReminderCount((long) 0);								
								list.setFuelEntriesCount((long) 0);						
								list.setWorkOrderCount((long) 0);							
								list.setServiceEntriesCount((long) 0);
								
								if(result[0] != null) 
								list.setServiceReminderCount((long) result[0]);								
								list.setCompany_id((int) result[1]);
								list.setCompany_name((String) result[2]);
								}
							serviceReminder.add(list);
							}
						}
						return serviceReminder;
					}
					catch(Exception e){
						throw e;						
					}			
				}	
				//Email System for Manager Stop  Service Reminder
		
		@Override
		public Long getTotalServiceReminderOverDueCount(String date, Integer companyId) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
					"SELECT COUNT(SE.service_id) "
							+ " From ServiceReminder as SE "
							+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid and V.vStatusId <> 4 "
							+ " WHERE ( SE.time_servicedate <= '"+date+"'  OR "
							+ " ( SE.serviceOdometerUpdatedDate <= '"+date+"') ) "
							+ " AND SE.markForDelete = 0 AND SE.companyId = "+companyId+" ",
							Object[].class);
			
			Long count = (long) 0;
			try {
				
				if((Long) queryt.getSingleResult() != null) {
				count = (Long) queryt.getSingleResult();
				}
				
			} catch (NoResultException nre) {
				}

			return count;
		}
		
		@Override
		public Long getDistinctTotalServiceReminderOverDueCount(String date, String serviceIds, Integer companyId) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
					"SELECT COUNT(SE.service_id) "
							+ " From ServiceReminder as SE "
							+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid and V.vStatusId <> 4 "
							+ " WHERE ( SE.time_servicedate <= '"+date+"'  OR "
							+ " ( SE.serviceOdometerUpdatedDate <= '"+date+"') ) "
							+ " AND SE.service_id NOT IN ("+serviceIds+")"
							+ " AND SE.markForDelete = 0 AND SE.companyId = "+companyId+" ",
							Object[].class);
			
			Long count = (long) 0;
			try {
				
				if((Long) queryt.getSingleResult() != null) {
				count = (Long) queryt.getSingleResult();
				}
				
			} catch (NoResultException nre) {
				}

			return count;
		}

		@Override
		public HashMap<Integer, Long> getALLServiceReminderOverDueCOunt(String startDate) throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(SE.service_id), SE.companyId "
								+ " From ServiceReminder as SE "
								+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid  AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
								+ " WHERE  ( SE.time_servicedate <= '"+startDate+"'  OR "
								+ " ( SE.serviceOdometerUpdatedDate <= '"+startDate+"' AND SE.time_servicedate <= '"+startDate+"') ) AND SE.markForDelete = 0 GROUP BY SE.companyId ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();

				if (results != null && !results.isEmpty()) {
					
					map	= new HashMap<>();
					
					for (Object[] result : results) {
						
						map.put((Integer)result[1], (Long)result[0]);
						
					}
				}
				
				return map;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public Long getTotalServiceReminderOverDueCountBetweenDates(String startDate, String endDate,Integer companyId) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
					"SELECT COUNT(SE.service_id) "
							+ " From ServiceReminder as SE "
							+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid  AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
							+ " WHERE  ( (SE.time_servicedate between '"+startDate+"' AND '"+endDate+"' ) OR (SE.serviceOdometerUpdatedDate between '"+startDate+"' AND '"+endDate+"' AND SE.time_servicedate NOT between '"+startDate+"' AND '"+endDate+"' )) "
							+ " AND SE.markForDelete = 0 AND SE.companyId = "+companyId+" ",
							Object[].class);
			
			Long count = (long) 0;
			try {
				
				if((Long) queryt.getSingleResult() != null) {
				count = (Long) queryt.getSingleResult();
				}
				
			} catch (NoResultException nre) {
				}

			return count;
		}
		
		@Override
		public List<ServiceReminder> getTotalServiceReminderOverDueListBetweenDates(String startDate, String endDate, Integer companyId) throws Exception {
			
			TypedQuery<Object[]> typedQuery = null;
			List<ServiceReminder> map		= null;
			
			 typedQuery = entityManager.createQuery(
					"SELECT SE.service_id, SE.companyId "
							+ " From ServiceReminder as SE "
							+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid  AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
							+ " WHERE  ( (SE.time_servicedate between '"+startDate+"' AND '"+endDate+"' ) OR (SE.serviceOdometerUpdatedDate between '"+startDate+"' AND '"+endDate+"' AND SE.time_servicedate NOT between '"+startDate+"' AND '"+endDate+"' )) "
							+ " AND SE.markForDelete = 0 AND SE.companyId = "+companyId+" ", Object[].class);
			
			 List<Object[]> results = typedQuery.getResultList();
			 map	= new ArrayList<ServiceReminder>();
			 ServiceReminder list = null;

				if (results != null && !results.isEmpty()) {
					
					for (Object[] result : results) {
						list = new ServiceReminder();
						
						list.setService_id((long)result[0]);
						list.setCompanyId((int)result[1]);
						
						map.add(list);
					}
					
				} else {
					list = new ServiceReminder();
					list.setService_id((long)0);
					list.setCompanyId(companyId);
					map.add(list);
				}
				
				return map;
		}
		
		@Override
		public Long getDistinctTotalServiceReminderOverDueCountBetweenDates(String startDate, String endDate, String serviceId, Integer companyId) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
					"SELECT COUNT(SE.service_id) "
							+ " From ServiceReminder as SE "
							+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid  AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
							+ " WHERE  ( SE.time_servicedate between '"+startDate+"' AND '"+endDate+"' OR SE.serviceOdometerUpdatedDate between '"+startDate+"' AND '"+endDate+"' ) "
							+ " AND SE.service_id NOT IN ("+serviceId+")"
							+ " AND SE.markForDelete = 0 AND SE.companyId = "+companyId+" ", 
							Object[].class);
			
			Long count = (long) 0;
			try {
				
				if((Long) queryt.getSingleResult() != null) {
				count = (Long) queryt.getSingleResult();
				}
				
			} catch (NoResultException nre) {
				}

			return count;
		}
		
		@Override
		public List<ServiceReminder> getDistinctTotalServiceReminderOverDueListBetweenDates(String startDate, String endDate, String serviceId, Integer companyId) throws Exception {
			
			TypedQuery<Object[]> typedQuery = null;
			List<ServiceReminder> map		= null;
			
			typedQuery = entityManager.createQuery(
					"SELECT SE.service_id, SE.companyId "
							+ " From ServiceReminder as SE "
							+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid  AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
							+ " WHERE  ( SE.time_servicedate between '"+startDate+"' AND '"+endDate+"' OR SE.serviceOdometerUpdatedDate between '"+startDate+"' AND '"+endDate+"' ) "
							+ " AND SE.service_id NOT IN ("+serviceId+")"
							+ " AND SE.markForDelete = 0 AND SE.companyId = "+companyId+" ",  Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();
			 map	= new ArrayList<ServiceReminder>();
			 ServiceReminder list = null;

				if (results != null && !results.isEmpty()) {
					
					for (Object[] result : results) {
						list = new ServiceReminder();
						
						list.setService_id((long)result[0]);
						list.setCompanyId((int)result[1]);
						
						map.add(list);
					}
					
				} else {
					list = new ServiceReminder();
					list.setService_id((long)0);
					list.setCompanyId(companyId);
					map.add(list);
				}
			
			return map;
		}
		
		@Override
		public HashMap<Integer, Long> getALLServiceReminderOverDueCOunt(String startDate, String endDate) throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(SE.service_id), SE.companyId "
								+ " From ServiceReminder as SE "
								+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid  AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
								+ " WHERE  ( (SE.time_servicedate between '"+startDate+"' AND '"+endDate+"' ) OR (SE.serviceOdometerUpdatedDate between '"+startDate+"' AND '"+endDate+"' AND SE.time_servicedate NOT between '"+startDate+"' AND '"+endDate+"' )) AND SE.markForDelete = 0 GROUP BY SE.companyId ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();

				if (results != null && !results.isEmpty()) {
					
					map	= new HashMap<>();
					
					for (Object[] result : results) {
						
						map.put((Integer)result[1], (Long)result[0]);
						
					}
				}
				
				return map;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public HashMap<Integer, Long> getALLServiceReminderOverDueMore15COunt(String startDate) throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(SE.service_id), SE.companyId "
								+ " From ServiceReminder as SE "
								+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid  AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
								+ " WHERE (SE.time_servicedate <= '"+startDate+"' OR "
								+ " (SE.serviceOdometerUpdatedDate <= '"+startDate+"' AND SE.time_servicedate <= '"+startDate+"' ) ) "
								+ " AND SE.markForDelete = 0 GROUP BY SE.companyId ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();

				if (results != null && !results.isEmpty()) {
					
					map	= new HashMap<>();
					
					for (Object[] result : results) {
						
						map.put((Integer)result[1], (Long)result[0]);
						
					}
				}
				
				return map;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		@Transactional 
		public void setServiceOdometerUpdatedDate(long service_id, int companyId) throws Exception {
			Date					 updatedDate					;
			try {
				updatedDate					 = new Date();
				java.sql.Timestamp sqlDate   = new java.sql.Timestamp(updatedDate.getTime());
				entityManager.createQuery(
					"Update ServiceReminder SET serviceOdometerUpdatedDate = '"+sqlDate+"' "
						+ " Where service_id = "+service_id+" "
						+ " AND companyId = "+companyId+" ")
				.executeUpdate();
				
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public long totalDueSoonCount (String date, int companyId) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
				" SELECT COUNT(service_id) from ServiceReminder SR "
					+" INNER JOIN Vehicle AS V ON V.vid = SR.vid  AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
					+" where (SR.time_servicethreshold_date <= '"+date+"' AND  SR.time_servicedate >= '"+date+"'  OR " 
					+" SR.meter_servicethreshold_odometer <= vehicle_currentOdometer AND SR.meter_serviceodometer >= SR.vehicle_currentOdometer) "  
				    +" AND SR.companyId = "+companyId+" AND SR.markForDelete = 0 ");
			
			Long count = (long) 0;
			try {
				
				if((Long) queryt.getSingleResult() != null) {
					count = (Long) queryt.getSingleResult();
				}
				
			} catch (NoResultException nre) {
			}
			
			return count;
		}
		
		@Override
		public long todaysServiceCount (String startDate, String endDate, int companyId) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
					" SELECT COUNT(SR.service_id) from ServiceReminder SR "
							+" INNER JOIN Vehicle AS V ON V.vid = SR.vid  AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
							+" where (SR.time_servicedate between '"+startDate+"' AND '"+endDate +"') OR "
							+" (SR.serviceOdometerUpdatedDate between '"+startDate+"' AND '"+endDate +"') " 
							+" AND SR.companyId = "+companyId+" AND SR.markForDelete = 0 ");
			
			Long count = (long) 0;
			try {
				
				if((Long) queryt.getSingleResult() != null) {
					count = (Long) queryt.getSingleResult();
				}
				
			} catch (NoResultException nre) {
				
			}
			
			return count;
		}
		
		
		@Transactional
		public List<ServiceReminderDto> getListServiceReminderMonthWiseDueSoon(String startDateOfMonth , String endDateOfMonth, Integer companyId) throws Exception{
			
			TypedQuery<Object[]> query =	null;
			
				query = entityManager
						.createQuery(" SELECT S.service_id, S.service_Number, S.vid, V.vehicle_registration, "
								+ " JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT, "
								+ " S.time_interval, S.time_thresholdperiodId,"
								+ " S.time_servicethreshold_date, S.serviceStatusId, S.time_servicedate, S.companyId "
								+ " FROM ServiceReminder AS S "
								+ " INNER JOIN VehicleGroup AS VG ON VG.gid = S.vehicleGroupId "
								+ " LEFT JOIN JobType AS JT ON JT.Job_id = S.serviceTypeId "
								+ " LEFT JOIN JobSubType AS JST ON JST.Job_Subid = S.serviceSubTypeId "
								+ " INNER JOIN Vehicle AS V ON V.vid = S.vid  "
								+ " WHERE S.serviceStatusId = 1 AND S.companyId = "+companyId
								+ " AND S.time_servicedate BETWEEN   '"+startDateOfMonth+"' AND '"+endDateOfMonth+"'"
								+ " AND S.markForDelete = 0 "
								+ " ORDER BY S.time_servicedate ASC "
								, Object[].class);
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					
					if(result[1]!= null)
					list.setService_Number((Long) result[1]);
					
					list.setVid((Integer) result[2]);
					
					if(result[3]!= null)
					list.setVehicle_registration((String) result[3]);
					
					if(result[4]!= null)
					list.setService_type((String) result[4]);
					
					if(result[5]!= null && result[6]!= null)
					list.setService_subtype((String) result[5] + " - "+ (String) result[6]);
					
					if(result[7]!= null)
					list.setTime_interval((Integer) result[7]);
					
					if(result[8]!= null)
					list.setTime_thresholdperiodId((short) result[8]);
					
					if(result[9]!= null)
					list.setTime_servicethreshold_date((Date) result[9]);
					
					list.setServiceStatusId((short) result[10]);
					
					if(result[11] != null)
					list.setTime_servicedate((Date) result[11]);
					
					list.setCompanyId((Integer) result[12]);
					
					Dtos.add(list);
				}
			}
			return Dtos;
		}

		@SuppressWarnings("unchecked")
		@Override
		public ValueObject configureReminderOnStartDayOfMonthEmailBody(ValueObject valueInObject) throws Exception {
			
			ValueObject					        valueOutObject 				= null;
			
			List<ServiceReminderDto>			serviceReminderDto 			= null;
			List<RenewalReminderDto>			renewalReminderDto			= null;
			List<DriverReminderDto>			     driverReminderDto			= null;
			
			String 							renewalReminderHeader			= "";
			String 							renewalReminderBody				= "";
			String 							serviceReminderHeader			= "";
			String 							serviceReminderBody				= "";
			String 							driverReminderHeader			= "";
			String 							driverReminderBody			    = "";
			SimpleDateFormat                 sdf							= new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
			String							serviceReminderDueDate			= "";		 
			String							renewalReminderDate				= "";
			String							startDateOfMonth				= "";
			String							endDateOfMonth					= "";
			
			String							renewalReminderHeaderWhenNoDataFound			= "";
			String							serviceReminderHeaderWhenNoDataFound			= "";
			String							driverReminderHeaderWhenNoDataFound				= "";
			
			CompanyDto						companyDto						= null;
			
			try {
				
				serviceReminderDto = (List<ServiceReminderDto>) valueInObject.get("serviceReminderDto");
				
				renewalReminderDto = (List<RenewalReminderDto>) valueInObject.get("renewalReminderDto");
				
				driverReminderDto = (List<DriverReminderDto>) valueInObject.get("driverReminderDto");

				startDateOfMonth = (String) valueInObject.get("startDateOfMonth");
				endDateOfMonth	 = (String) valueInObject.get("endDateOfMonth");
				
				String 			startDateArray[] = startDateOfMonth.split("-"); 
				String 			endDateArray[]	 = endDateOfMonth.split("-");
				String 			startYear	 	 = startDateArray[0];
				String 			startMonth	 	 = startDateArray[1];
				String 			startDay	 	 = startDateArray[2];
				String 			endYear	 	 	 = endDateArray[0];
				String 			endMonth 	 	 = endDateArray[1];
				String 			endDay	 		 = endDateArray[2];
				String 			finalStartDate	 = startDay+"-"+startMonth+"-"+startYear;
				String 			finalEndDate	 = endDay+"-"+endMonth+"-"+endYear;		
				
				companyDto		= (CompanyDto) valueInObject.get("companyInfo");
				
				if(serviceReminderDto != null && !serviceReminderDto.isEmpty()) {
					serviceReminderHeader	+= "<tr width=\"100%\" bgcolor=\"DodgerBlue\" >"
							+"<td align='center' colspan=\"8\"><div style=\"font-size:20px\"> Service Reminder of Month  "  
							+"</td>"
							+"</tr>";
					
					serviceReminderHeader  +="<tr bgcolor=\"#00FFFF\">"
							+"<th align='center' ><div style=\"font-size:17px\"> Vehicle </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Service Number  </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Service Job Type </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Service Job Sub Type </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Due Date </th>"
							+"</tr>";
					
					for(ServiceReminderDto dto : serviceReminderDto) {
							
						serviceReminderDueDate = sdf.format(dto.getTime_servicedate());
							
							serviceReminderBody  +="<tr>"
									+"<td align='center'><div style=\"font-size:18px\">"+ dto.getVehicle_registration() +"</td>"
									+"<td align='center'><div style=\"font-size:18px\">"+ dto.getService_Number()+"</td>"
									+"<td align='center'><div style=\"font-size:18px\">"+ dto.getService_type()+"</td>"
									+"<td align='center'><div style=\"font-size:18px\">"+ dto.getService_subtype() +"</td>"
									+"<td align='center'><div style=\"font-size:18px\">"+ serviceReminderDueDate +"</td>"
									+"</tr>";
							
						}
					
				}
				else {
					serviceReminderHeaderWhenNoDataFound	+= "<tr width=\"100%\" bgcolor=\"#05354B\" >"
							+"<br><td align='center' colspan=\"8\"><div style=\"font-size:20px; color:#ffffff\"><b>No Service Reminder This Month</b>"  
							+"</td>"
							+"</tr>";
				}
				
				if(renewalReminderDto != null && !renewalReminderDto.isEmpty()) {
					
					renewalReminderHeader	+= "<tr width=\"100%\" bgcolor=\"DodgerBlue\" >"
							+"<td align='center' colspan=\"7\"><div style=\"font-size:20px\"> Renewal Reminder of Month  "  
							+"</td>"
							+"</tr>";	
					
					renewalReminderHeader   +="<tr bgcolor=\"#00FFFF\">"
							+"<th align='center' ><div style=\"font-size:17px\"> Vehicle </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Vehicle Status</th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Renewal Reminder Number </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Renewal Type </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Renewal Subtype </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Renewal Date </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Renewal Amount </th>"
							+"</tr>";
					
					for(RenewalReminderDto dto : renewalReminderDto) {
						
						renewalReminderDate = sdf.format(dto.getRenewal_To_Date());
						
						renewalReminderBody  +="<tr>"
								
								+"<td align='center'><div style=\"font-size:18px\">"+ dto.getVehicle_registration()+"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto.getVehicleStatus()+"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto.getRenewal_R_Number()+"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto.getRenewal_type()+"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto.getRenewal_subtype()+"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ renewalReminderDate +"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto.getRenewal_Amount() +"</td>"
								+"</tr>";
					}
					
				}
				else {
					renewalReminderHeaderWhenNoDataFound	+= "<tr width=\"100%\" bgcolor=\"#05354B\" >"
							+"<br><td align='center' colspan=\"8\"><div style=\"font-size:20px; color:#ffffff\"><b>No Renewal Reminder This Month</b>"  
							+"</td>"
							+"</tr>";
				}
				
				if(driverReminderDto != null && !driverReminderDto.isEmpty()) {
					
					driverReminderHeader	+= "<tr width=\"100%\" bgcolor=\"DodgerBlue\" >"
							+"<td align='center' colspan=\"7\"><div style=\"font-size:20px\"> Driver Reminder of Month  "  
							+"</td>"
							+"</tr>";	
					
					driverReminderHeader    +="<tr bgcolor=\"#00FFFF\">"
							+"<th align='center' ><div style=\"font-size:17px\"> Driver </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Renewal Type </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> License Number </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Renewal Date </th>"
							+"</tr>";
					
					for(DriverReminderDto dto : driverReminderDto) {
						
						driverReminderBody  +="<tr>"
								
								+"<td align='center'><div style=\"font-size:18px\">"+ dto.getDriver_firstname()+"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto.getDriver_remindertype()+"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto.getDriver_dlnumber() +"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto.getDriver_renewaldate() +"</td>"
								+"</tr>";
						
						
					}
				}
				else {
					driverReminderHeaderWhenNoDataFound	+= "<tr width=\"100%\" bgcolor=\"#05354B\" >"
							+" <br> <td align='center' colspan=\"8\"><div style=\"font-size:20px; color:#ffffff \"><b>No Driver Reminder This Month</b>"  
							+"</td>"
							+"</tr>";	
				}
						
				
		
				final String ReminderOnStartDayOfEveryMonth = "<html>\r\n" + 
						"<head>\r\n" + 
						"</head>\r\n" + 
						"\r\n" + 
						"<body>\r\n" +
						"<table bgcolor=\"#05354B\" width=\"100%\">\r\n" + 
						"<tr>\r\n" + 
						"<td>\r\n" + 
						"<h2><font color=\"#ffffff\" ><b><center>Fleetop <img src=\"https://fleetop.in/resources/QhyvOb0m3EjE7A4/images/plus.png\" alt =\"Plus\"> Monthly Reminder </center></b></font></h2>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n" + 
						"</table>\r\n" + 
						"<br>\r\n <font color='blue'> <h2> "+companyDto.getCompany_name()+"</h2> <h3> Monthly Reminder Details  &nbsp; &nbsp; <span style='color:#500050;'>"+finalStartDate+" : "+finalEndDate+" </span> </h3>  </font>" +	 	
						"<br>\r\n" + 
						"<table style=\"width:100%;border-collapse: collapse;\" border=1>\r\n" +
						"	<thead>"+renewalReminderHeader+
						"	</thead>"+
						"	<tbody>" + renewalReminderBody+
						"	</tbody>" + 								
						"</table>\r\n" + 
						"<br>\r\n" + 
						"\r\n" + 
						"</b><br>\r\n" + 
						"<table style=\"width:100%;border-collapse: collapse;\"  border=1>\r\n" +
						"	<thead>"+serviceReminderHeader+
						"	</thead>"+
						"	<tbody>" + serviceReminderBody+
						"	</tbody>" + 								
						"</table>\r\n" + 
						"<br>\r\n" + 
						"\r\n" + 
						"</b><br>\r\n" + 
						"<table style=\"width:100%;border-collapse: collapse;\" border=1>\r\n" +
						"	<thead>"+driverReminderHeader+
						"	</thead>"+
						"	<tbody>" + driverReminderBody+
						"	</tbody>" + 								
						"</table>\r\n" + 
						"<br>\r\n" + 
						"\r\n" + 
						
						"<table style=\"width:100%;border-collapse: collapse;\" border=1>\r\n" +
						"	<thead>"+renewalReminderHeaderWhenNoDataFound+
						"	</thead>"+
						"</table>\r\n" + 
						"<br>"+
					
						"<table style=\"width:100%;border-collapse: collapse;\" border=1>\r\n" +
						"	<thead>"+serviceReminderHeaderWhenNoDataFound+
						"	</thead>"+
						"</table>\r\n" + 
						"<br>"+
						
						"<table style=\"width:100%;border-collapse: collapse;\" border=1>\r\n" +
						"	<thead>"+driverReminderHeaderWhenNoDataFound+
						"	</thead>"+
						"</table>\r\n" +
						"<br>"+
						
						"<table bgcolor=\"#05354B\" width=\"100%\">\r\n" + 
						"<tr>\r\n" + 
						"<td>\r\n" + 
						"<h2><font color=\"#ffffff\" ><b> </b></font></h2>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n" + 
						"</table>\r\n" + 
						"\r\n" + 
						"\r\n" + 
						
						"</body>\r\n" + 
						"</html>";
				
					
				valueOutObject	= new ValueObject();
				valueOutObject.put("ReminderOnStartDayOfEveryMonth", ReminderOnStartDayOfEveryMonth);
					
				return valueOutObject;
			}
			catch(Exception e) {
				throw e;
			}
			
		}
		
		
		@Override
		public List<ServiceReminderDto> getServiceReminderTableListBetweenDates(String query,Integer companyID) throws Exception {
			try {
				TypedQuery<Object[]> queryt = null;

				queryt = entityManager.createQuery(
						" SELECT SR.service_id, SR.companyId ,SR.created,  SR.time_servicedate ,SR.serviceOdometerUpdatedDate ,"
								+ " SR.time_servicethreshold_date ,SR.meter_servicethreshold_odometer ,SR.vehicle_currentOdometer,"
								+ " SR.meter_serviceodometer, SR.service_Number, V.vehicle_registration "
								+ " FROM ServiceReminder AS SR "
								+ " INNER JOIN Vehicle AS V ON V.vid = SR.vid AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
								+ " WHERE "+query
								+ " AND SR.companyId = " +companyID
								+ " AND SR.markForDelete = 0 "
								+ " ORDER BY SR.service_id DESC ",
								Object[].class);

				List<Object[]> results = queryt.getResultList();

				List<ServiceReminderDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<ServiceReminderDto>();
					ServiceReminderDto service = null;
					
						for (Object[] result : results) {
							service = new ServiceReminderDto();
					
							service.setService_id((Long) result[0]);
					
							service.setCompanyId((Integer)  result[1]);
					
							if(result[2] != null)
							service.setCreatedOn((Date) result[2]);
					
							if(result[3] != null) 
							service.setTimeServiceDate(dateFormat.format((Date) result[3]));
					
							if(result[4] != null)
							service.setServiceOdometerUpdatedDate((Date) result[4]);
					
							if(result[5] != null)
							service.setTimeServiceThresholdDateStr(dateFormat.format((Date) result[5]));
					
							if(result[6] != null)
							service.setMeter_servicethreshold_odometer((Integer) result[6]);
					
							if(result[7] != null)
							service.setVehicle_currentOdometer((Integer) result[7]);
					
							if(result[8] != null)
							service.setMeter_serviceodometer((Integer) result[8]);
							
							service.setService_Number((long) result[9]);
							service.setVehicle_registration((String) result[10]);
					
							Dtos.add(service);
						}
				}
					return Dtos;

			} catch (Exception e) {
			throw e;
			}

		}
		
		@Override
		public List<ServiceReminderDto> getServiceReminderOverDueListServiceDateWise(String date, int compId) throws Exception {
			try {
				
				TypedQuery<Object[]> queryt = null;
					queryt = entityManager.createQuery(
							"SELECT SR.service_id, SR.companyId, SR.service_Number, V.vehicle_registration, SR.vehicle_currentOdometer,"
								+ " SR.meter_serviceodometer, SR.time_servicethreshold_date, SR.time_servicedate "
								+ " From ServiceReminder as SR "
								+ " INNER JOIN Vehicle AS V ON V.vid = SR.vid  AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
								+ " WHERE SR.time_servicedate <= '"+date+"' "
								+ " AND SR.markForDelete = 0 AND SR.companyId = "+compId+" ",
								Object[].class);
				
					List<Object[]> results = queryt.getResultList();

					List<ServiceReminderDto> Dtos = null;
					if (results != null && !results.isEmpty()) {
						Dtos = new ArrayList<ServiceReminderDto>();
						ServiceReminderDto service = null;
						
							for (Object[] result : results) {
								service = new ServiceReminderDto();
						
								service.setService_id((Long) result[0]);
								service.setCompanyId((Integer)  result[1]);
								service.setService_Number((long) result[2]);
								service.setVehicle_registration((String) result[3]);
								
								if(result[4] != null)
									service.setVehicle_currentOdometer((Integer) result[4]);
							
								if(result[5] != null)
									service.setMeter_serviceodometer((Integer) result[5]);
								
								if(result[6] != null)
									service.setTimeServiceThresholdDateStr(dateFormat.format((Date) result[6]));
								
								if(result[7] != null) 
									service.setTimeServiceDate(dateFormat.format((Date) result[7]));
						
								Dtos.add(service);
							}
					}
						return Dtos;
				
			}catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<ServiceReminderDto> getServiceReminderOverDueListOdometerWise(String date, int compId) throws Exception {
			try {
				
				TypedQuery<Object[]> queryt = null;
					queryt = entityManager.createQuery(
							"SELECT SR.service_id, SR.companyId, SR.service_Number, V.vehicle_registration, SR.vehicle_currentOdometer, "
								+ " SR.meter_serviceodometer, SR.time_servicethreshold_date, SR.time_servicedate "
								+ " From ServiceReminder as SR "
								+ " INNER JOIN Vehicle AS V ON V.vid = SR.vid  AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
								+ " WHERE SR.serviceOdometerUpdatedDate <= '"+date+"' "
								+ " AND SR.markForDelete = 0 AND SR.companyId = "+compId+" ",
								Object[].class);
				
					List<Object[]> results = queryt.getResultList();

					List<ServiceReminderDto> Dtos = null;
					if (results != null && !results.isEmpty()) {
						Dtos = new ArrayList<ServiceReminderDto>();
						ServiceReminderDto service = null;
						
							for (Object[] result : results) {
								service = new ServiceReminderDto();
						
								service.setService_id((Long) result[0]);
								service.setCompanyId((Integer)  result[1]);
								service.setService_Number((long) result[2]);
								service.setVehicle_registration((String) result[3]);
								
								if(result[4] != null)
									service.setVehicle_currentOdometer((Integer) result[4]);
							
								if(result[5] != null)
									service.setMeter_serviceodometer((Integer) result[5]);
								
								if(result[6] != null)
									service.setTimeServiceThresholdDateStr(dateFormat.format((Date) result[6]));
								
								if(result[7] != null) 
									service.setTimeServiceDate(dateFormat.format((Date) result[7]));
						
								Dtos.add(service);
							}
					}
						return Dtos;
				
			}catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public HashMap<Long ,ServiceReminderDto> getSROverDue15PlusCount(List<ServiceReminderDto> Dtos) throws Exception {
			try {

				HashMap<Long ,ServiceReminderDto> dtosMap = null;
				if (Dtos != null && !Dtos.isEmpty()) {
					dtosMap = new HashMap<Long ,ServiceReminderDto>();
					
					for (ServiceReminderDto serviceDto : Dtos) {
						
						if(dtosMap.containsKey(serviceDto.getService_id())) {
							
						} else {
							dtosMap.put(serviceDto.getService_id(),serviceDto);
						}
					}
				}
				return dtosMap;

			}catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<ServiceReminderDto> getListOfNumberOfSRCreatedOnVehicles(Integer companyID) throws Exception {
			try {
				TypedQuery<Object[]> queryt = null;

				queryt = entityManager.createQuery(
						" SELECT COUNT(SR.vid), SR.vid, V.vehicle_registration "
								+ " FROM ServiceReminder AS SR "
								+ " INNER JOIN Vehicle AS V ON V.vid = SR.vid AND V.vStatusId <> 4 "
								+ " WHERE SR.companyId = "+companyID+" and SR.markForDelete = 0 "
								+ " GROUP BY SR.vid ",
								Object[].class);

				List<Object[]> results = queryt.getResultList();

				List<ServiceReminderDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<ServiceReminderDto>();
					ServiceReminderDto service = null;
					
						for (Object[] result : results) {
							service = new ServiceReminderDto();
							
							service.setCountOfSROnEachVehicle((Long) result[0]);
							service.setVid((Integer)  result[1]);
							service.setVehicle_registration((String) result[2]);
					
							Dtos.add(service);
						}
				}
					return Dtos;

			} catch (Exception e) {
			throw e;
			}

		}
		
		@Override
		public ValueObject getServiceReminderList(ValueObject valueObject) throws Exception {
			CustomUserDetails 					userDetails 					= null;
			long 								totalCount				 		= 0;
			HashMap<String, Object> 			configuration					= null;
			List<ServiceReminderDto>			 pageList						= null; 
			Page<ServiceReminder> 				page 							= null;
			try {
				userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_REMINDER_CONFIG);
				if(!(boolean) configuration.get("groupListByVehicleNumber")) {
					page = getDeployment_Page_ServiceReminder(valueObject.getInt("pageNumber"), userDetails);
				}else {
					page = getDeployment_Page_ServiceReminderByVid(valueObject.getInt("pageNumber"), userDetails);
				}
				
				if (page != null) {
					int current = page.getNumber() + 1;
					int begin = Math.max(1, current - 5);
					int end = Math.min(begin + 10, page.getTotalPages());

					valueObject.put("deploymentLog", page);
					valueObject.put("beginIndex", begin);
					valueObject.put("endIndex", end);
					valueObject.put("currentIndex", current);
					valueObject.put("configuration", configuration);
					totalCount = page.getTotalElements();
					
					if(!(boolean) configuration.get("groupListByVehicleNumber")) {
						pageList = SRBL.prepareListofServiceReminderAjax(
								listServiceReminder(valueObject.getInt("pageNumber"), userDetails));
					}else {
						pageList	= listServiceReminderGroupByVid(valueObject.getInt("pageNumber"), userDetails);
					}
				}
				valueObject.put("serviceReminderList", pageList);
				valueObject.put("totalCount", totalCount);
				
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(toDate);
				calendar.add(Calendar.DAY_OF_MONTH, 30);
				Date toDatePlus30Days = calendar.getTime();
				
				valueObject.put("TodayOverDueServiceRemindercount",
										countTodayOverDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));
				valueObject.put("TodayDueServiceRemindercount",
										countTodayDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));
				valueObject.put("TodaysDueServiceRemindercount",
										countTodaysDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));
				valueObject.put("UpcomingDueServiceRemindercount",
						countUpcomingDueServiceReminder(toDate, toDatePlus30Days, userDetails.getCompany_id(), userDetails.getId()));

				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}finally {
				userDetails 					= null;
				totalCount				 		= 0;
				configuration					= null;
			}
		}
		@Override
		public ValueObject getTodaysOverDueServiceList(ValueObject valueObject) throws Exception {
			CustomUserDetails 					userDetails 					= null;
			long 								totalCount				 		= 0;
			HashMap<String, Object> 			configuration					= null;
			List<ServiceReminderDto>			 pageList						= null; 
			try {
				userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
				Page<ServiceReminder> page = null;
				configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_REMINDER_CONFIG);
				if(!(boolean) configuration.get("groupListByVehicleNumber")) {
					page = geServiceReminderTodaysOverDuePage(toDate , valueObject.getInt("pageNumber"), userDetails);
				}else {
					page = geServiceReminderTodaysOverDuePageGByVid(toDate , valueObject.getInt("pageNumber"), userDetails);
				}
				
				if (page != null) {
					int current = page.getNumber() + 1;
					int begin = Math.max(1, current - 5);
					int end = Math.min(begin + 10, page.getTotalPages());

					valueObject.put("deploymentLog", page);
					valueObject.put("beginIndex", begin);
					valueObject.put("endIndex", end);
					valueObject.put("currentIndex", current);
					valueObject.put("configuration", configuration);
					totalCount = page.getTotalElements();
					
					if(!(boolean) configuration.get("groupListByVehicleNumber")) {
						pageList = SRBL.prepareListofServiceReminderAjax(
								TodaysOverDueServiceRemnder(toDate, userDetails, valueObject.getInt("pageNumber")));
						
					}else {
						pageList = TodaysOverDueServiceRemnderGByVid(toDate, userDetails, valueObject.getInt("pageNumber"));
					}
					
				}
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(toDate);
				calendar.add(Calendar.DAY_OF_MONTH, 30);
				Date toDatePlus30Days = calendar.getTime();
				
				valueObject.put("serviceReminderList", pageList);
				valueObject.put("totalCount", totalCount);
				
				valueObject.put("TodayOverDueServiceRemindercount",
										countTodayOverDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));

				valueObject.put("TodayDueServiceRemindercount",
										countTodayDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));
				valueObject.put("TodaysDueServiceRemindercount",
						countTodaysDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));
				
				valueObject.put("UpcomingDueServiceRemindercount",
						countUpcomingDueServiceReminder(toDate, toDatePlus30Days, userDetails.getCompany_id(), userDetails.getId()));

				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}finally {
				userDetails 					= null;
				totalCount				 		= 0;
				configuration					= null;
			}
		}
	
				@Override
				public ValueObject getUpcomingOverDueServiceList(ValueObject valueObject) throws Exception {
					CustomUserDetails 					userDetails 					= null;
					long 								totalCount				 		= 0;
					HashMap<String, Object> 			configuration					= null;
					List<ServiceReminderDto>			 pageList						= null; 
					try {
						
						userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
						java.util.Date currentDate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
						
						Date currtDate = new Date();
				       		Calendar calendar = Calendar.getInstance();
				       		calendar.setTime(currtDate);
				       		calendar.add(Calendar.DAY_OF_MONTH, 30);
						Date updatedDate = calendar.getTime();
					
					    
					    Timestamp toDate2 = new java.sql.Timestamp(updatedDate.getTime());
				
						Page<ServiceReminder> page = null;
				
						configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_REMINDER_CONFIG);
						if(!(boolean) configuration.get("groupListByVehicleNumber")) {
							page = geServiceReminderUpcomingOverDuePage(toDate ,toDate2, valueObject.getInt("pageNumber"), userDetails);
						}else {
							page = geServiceReminderUpcomingOverDuePageGByVid(toDate ,toDate2, valueObject.getInt("pageNumber"), userDetails);
						}
						
						if (page != null) {
							int current = page.getNumber() + 1;
							int begin = Math.max(1, current - 5);
							int end = Math.min(begin + 10, page.getTotalPages());

							valueObject.put("deploymentLog", page);
							valueObject.put("beginIndex", begin);
							valueObject.put("endIndex", end);
							valueObject.put("currentIndex", current);
							valueObject.put("configuration", configuration);
							totalCount = page.getTotalElements();
							
							if(!(boolean) configuration.get("groupListByVehicleNumber")) {
								pageList = SRBL.prepareListofServiceReminderAjax(
										UpcomingOverDueServiceRemnder(toDate,toDate2, userDetails, valueObject.getInt("pageNumber")));
								System.err.println("pageList1    -"+pageList);
							}else {
								pageList = UpcomingOverDueServiceRemnderGByVid(toDate,toDate2, userDetails, valueObject.getInt("pageNumber"));
								System.err.println("pageList2    -"+pageList);
							}
							
						}
						
						valueObject.put("serviceReminderList", pageList);
						valueObject.put("totalCount", totalCount);
						
						valueObject.put("TodayOverDueServiceRemindercount",
												countTodayOverDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));

						valueObject.put("TodayDueServiceRemindercount",
												countTodayDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));
						valueObject.put("TodaysDueServiceRemindercount",
								countTodaysDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));
						
						valueObject.put("UpcomingDueServiceRemindercount",
								countUpcomingDueServiceReminder(toDate, toDate2, userDetails.getCompany_id(), userDetails.getId()));
	
						return valueObject;
						
					} catch (Exception e) {
						System.err.println("ERR"+e);
						throw e;
					}finally {
						userDetails 					= null;
						totalCount				 		= 0;
						configuration					= null;
					}
				}
		
		@Override
		public ValueObject getOverDueServiceList(ValueObject valueObject) throws Exception {
			CustomUserDetails 					userDetails 					= null;
			long 								totalCount				 		= 0;
			HashMap<String, Object> 			configuration					= null;
			List<ServiceReminderDto>			 pageList						= null; 
			try {
				userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
				Page<ServiceReminder> page = null;
				configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_REMINDER_CONFIG);
				if(!(boolean) configuration.get("groupListByVehicleNumber")) {
					page = geServiceReminderOverDuePage(toDate , valueObject.getInt("pageNumber"), userDetails);
				}else {
					page = geServiceReminderOverDuePageGByVid(toDate , valueObject.getInt("pageNumber"), userDetails);
				}
				
				if (page != null) {
					int current = page.getNumber() + 1;
					int begin = Math.max(1, current - 5);
					int end = Math.min(begin + 10, page.getTotalPages());

					valueObject.put("deploymentLog", page);
					valueObject.put("beginIndex", begin);
					valueObject.put("endIndex", end);
					valueObject.put("currentIndex", current);
					valueObject.put("configuration", configuration);
					totalCount = page.getTotalElements();
					
					
					if(!(boolean) configuration.get("groupListByVehicleNumber")) {
						pageList = SRBL.prepareListofServiceReminderAjax(
								OverDueServiceRemnder(toDate, userDetails, valueObject.getInt("pageNumber")));
					}else {
						pageList = OverDueServiceRemnderGByVid(toDate, userDetails, valueObject.getInt("pageNumber"));
					}
					
				}
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(toDate);
				calendar.add(Calendar.DAY_OF_MONTH, 30);
				Date toDatePlus30Days = calendar.getTime();
				
				valueObject.put("serviceReminderList", pageList);
				valueObject.put("totalCount", totalCount);
				
				valueObject.put("TodayOverDueServiceRemindercount",
										countTodayOverDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));

				valueObject.put("TodayDueServiceRemindercount",
										countTodayDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));
				
				valueObject.put("TodaysDueServiceRemindercount",
						countTodaysDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));
				
				valueObject.put("UpcomingDueServiceRemindercount",
						countUpcomingDueServiceReminder(toDate, toDatePlus30Days, userDetails.getCompany_id(), userDetails.getId()));

				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}finally {
				userDetails 					= null;
				totalCount				 		= 0;
				configuration					= null;
			}
		}
		
		@Override
		public ValueObject getDueSoonServiceList(ValueObject valueObject) throws Exception {
			CustomUserDetails 					userDetails 					= null;
			long 								totalCount				 		= 0;
			List<ServiceReminderDto>			 pageList						= null; 
			try {
				userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
				
				HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_REMINDER_CONFIG);
				if(!(boolean)configuration.get("groupListByVehicleNumber")) {
					pageList = SRBL.prepareListofServiceReminderAjax(DueSoonServiceRemnder(toDate, userDetails));
				}else {
					pageList = DueSoonServiceRemnderGByVid(toDate, userDetails);
				}	
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(toDate);
				calendar.add(Calendar.DAY_OF_MONTH, 30);
				Date toDatePlus30Days = calendar.getTime();
				
				valueObject.put("serviceReminderList", pageList);
				valueObject.put("totalCount", totalCount);
				
				valueObject.put("TodayOverDueServiceRemindercount",
										countTodayOverDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));

				valueObject.put("TodayDueServiceRemindercount",
										countTodayDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));
				
				valueObject.put("TodaysDueServiceRemindercount",
						countTodaysDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));

				valueObject.put("UpcomingDueServiceRemindercount",
						countUpcomingDueServiceReminder(toDate, toDatePlus30Days, userDetails.getCompany_id(), userDetails.getId()));

				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}finally {
				userDetails 					= null;
				totalCount				 		= 0;
			}
		}
		
		@Override
		@Transactional
		public ValueObject saveServiceReminderDetails(ValueObject valueObject) throws Exception {
			HashMap<Long ,UserProfileDto>	userMap				= null;
			String 							vidArr[]			= null;
			ServiceReminder					serviceReminder		= null;
			SubscribeBox					seSubscribeBox		= null;
			VehicleDto						vehicle				= null;
			ServiceReminderSequenceCounter 	counter 			= null;
			List<ServiceReminder>           validate			= null;
			int								duplicateCount		= 0;
			String							duplicateSr			= "";
			try {
				if(valueObject.getString("subscribe", null) != null) {
					userMap	= userProfileService.getUserEmailIdFromUserId(valueObject.getString("subscribe"));
					setAsignedToNames(userMap, valueObject);
				}
				
				vidArr = valueObject.getString("vids").split(",");
				
				for (int i = 0; i < vidArr.length; i++) {
					vehicle	= vehicleService.getVehicle_Details_Service_Reminder_Entries(Integer.parseInt(vidArr[i]), valueObject.getInt("companyId"));
					serviceReminder = getServiceReminderDto(valueObject, vehicle);
					
					validate	=	validateServiceReminder(serviceReminder.getVid(), serviceReminder.getServiceTypeId(), serviceReminder.getServiceSubTypeId());
					if(validate != null && !validate.isEmpty()) {
							duplicateCount ++;
							duplicateSr 	+= validate.get(0).getService_Number()+",";
						valueObject.put("duplicateSr", duplicateSr);
						valueObject.put("duplicateCount", duplicateCount);
						valueObject.put("vehicleLength", vidArr.length);
						continue;
					}
					counter = serviceReminderSequenceService.findNextServiceReminderNumber(valueObject.getInt("companyId"));
					if(counter != null) {
						serviceReminder.setService_Number(counter.getNextVal());
						addServiceReminder(serviceReminder);
						if (serviceReminder.getService_subScribedUserId() != null) {
							seSubscribeBox	=	getSubscribeBoxDto(serviceReminder, valueObject);
							subscribeBoxService.insert_Subscribe_Box(seSubscribeBox);
						}
					}
				}
				if(duplicateCount > 0 && (duplicateCount == vidArr.length)) {
					valueObject.put("accessToken",Utility.getUniqueToken(request));
				}
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
		}
		
		private void setAsignedToNames(HashMap<Long ,UserProfileDto>	userMap, ValueObject	valueObject) throws Exception{
			try {
				int count	= 0;
				if (valueObject.getString("subscribe", null) != null) {
					String 							assigntoname 		= "";
					String 							assignEmail 		= "";
					String username[] = valueObject.getString("subscribe", null).split(",");
					for (String userAssignto : username) {
						UserProfileDto Orderingname = userMap.get(Long.parseLong(userAssignto));
						assigntoname += "" + Orderingname.getFirstName() + " , ";
						if(count < username.length) {
							assignEmail  += ""+ Orderingname.getUser_email()+ " ,";
						}else {
							assignEmail  += ""+ Orderingname.getUser_email();
						}
						count++;
					}
					valueObject.put("asignedToName", assigntoname);
					valueObject.put("assignEmail", assignEmail);
				}
				
			} catch (Exception e) {
				
			}
		}
		
		
		
		public ServiceReminder getServiceReminderDto(ValueObject	valueObject , VehicleDto vehiclename) throws Exception{
			ServiceReminder			service		= null;
			try {
					service	= new ServiceReminder();
					
					service.setVid(vehiclename.getVid());
					service.setVehicle_currentOdometer(vehiclename.getVehicle_Odometer());
					service.setVehicleGroupId(vehiclename.getVehicleGroupId());
					// get UI to Service Reminder Details
					service.setServiceTypeId(valueObject.getInt("serviceTypeId",0));
					service.setServiceSubTypeId(valueObject.getInt("serviceSubTypeId",0));
					
					if(valueObject.getBoolean("firstService",false)) {
						service.setFirstService(valueObject.getBoolean("firstService",false));
						service.setFirstMeterInterval(valueObject.getInt("first_meter_interval",0));
						service.setFirstTimeInterval(valueObject.getInt("first_time_interval",0));
						service.setFirstTimeIntervalType(valueObject.getShort("first_time_intervalperiod"));
					}
					
					service.setTime_interval(valueObject.getInt("time_interval",0));
					if(valueObject.getInt("time_interval",0) > 0) {
						service.setTime_intervalperiodId(valueObject.getShort("timeIntervalType",(short) 0));
						service.setTime_threshold(valueObject.getInt("time_threshold",0));
						service.setTime_thresholdperiodId(valueObject.getShort("time_thresholdType",(short) 0));
					}

					service.setMeter_interval(valueObject.getInt("meter_interval",0));
					if(valueObject.getInt("meter_interval",0) > 0) {
						service.setMeter_threshold(valueObject.getInt("dueMeterThreshold",0));
					}

					
					// Find Subscribe user first name below
					service.setService_subscribeduser_name(valueObject.getString("asignedToName"));
					service.setService_subScribedUserId(valueObject.getString("subscribe"));

					Integer CurrentOdometer = vehiclename.getVehicle_Odometer();
					Integer Meter_interval = service.getMeter_interval();

					if (CurrentOdometer == null) {
						CurrentOdometer = 0;
					}
					if (Meter_interval == null) {
						Meter_interval = 0;
					}
					Integer ServiceOdometer = CurrentOdometer + Meter_interval;
					// save Service meter_Odometer reading
					if(service.isFirstService()) {
						ServiceOdometer = CurrentOdometer + service.getFirstMeterInterval();
					}
					service.setMeter_serviceodometer(ServiceOdometer);

					if (service.getMeter_threshold() != null) {

						Integer meter_threshold = service.getMeter_threshold();
						if (ServiceOdometer == 0) {
							meter_threshold = 0;
						}

						Integer ServiceOdometer_threshold = ServiceOdometer - meter_threshold;
						// save advance Meter Threshold time and period
						service.setMeter_servicethreshold_odometer(ServiceOdometer_threshold);
					}

					// get Current days
					java.util.Date currentDate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
					// save current days
					service.setTime_interval_currentdate(toDate);

					// time interval not equeal to null only enter the value
					if (service.getTime_interval() != null) {

						// get time interval calculation to service to get
						// service time interval days
						Integer time_Intervalperiod = 0;
						short IntervalType  = 0;
						if (service.getTime_intervalperiodId() > 0) {
							time_Intervalperiod = service.getTime_interval();
						}
						if(service.isFirstService()) {
							time_Intervalperiod = service.getFirstTimeInterval();
							IntervalType  = service.getFirstTimeIntervalType();
						}
						else {
							IntervalType = service.getTime_intervalperiodId();
						}				
						Integer timeserviceDaysPeriod = 0;
						switch (IntervalType) {
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
							timeserviceDaysPeriod = time_Intervalperiod;
							break;
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
							timeserviceDaysPeriod = time_Intervalperiod * 7;
							break;
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
							timeserviceDaysPeriod = time_Intervalperiod * 30;
							break;
						case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
							timeserviceDaysPeriod = time_Intervalperiod * 365;
							break;

						default:
							timeserviceDaysPeriod = time_Intervalperiod;
							break;
						}

						final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

						final Calendar calendar = Calendar.getInstance();
						calendar.setTime(toDate);
						calendar.add(Calendar.DAY_OF_YEAR, timeserviceDaysPeriod);

						// fuel date converted String to date Format
						java.util.Date servicedate = format.parse(format.format(calendar.getTime()));
						java.sql.Date Time_servicedate = new java.sql.Date(servicedate.getTime());

						// save Service Time_interval Reminder
						service.setTime_servicedate(Time_servicedate);

						if (service.getTime_threshold() != null) {

							Integer Time_threshold = 0;
							switch (service.getTime_thresholdperiodId()) {
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
								Time_threshold = service.getTime_threshold();
								break;
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
								Time_threshold = service.getTime_threshold() * 7;
								break;
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
								Time_threshold = service.getTime_threshold() * 30;
								break;
							case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
								Time_threshold = service.getTime_threshold() * 365;
								break;

							default:
								Time_threshold = service.getTime_threshold();
								break;
							}
							final Calendar calendar_advanceThreshold = Calendar.getInstance();
							// System.out.println("Service =" +
							// Time_servicedate);
							// this Time_servicedate is service time Day so that
							// day to minus advance
							// time threshold time
							calendar_advanceThreshold.setTime(Time_servicedate);
							calendar_advanceThreshold.add(Calendar.DAY_OF_YEAR, -Time_threshold);
							/*
							 * System.out.println( "Service time_advancedate ="
							 * +
							 * format.format(calendar_advanceThreshold.getTime()
							 * ));
							 */
							// fuel date converted String to date Format
							java.util.Date serviceThreshold = format
									.parse(format.format(calendar_advanceThreshold.getTime()));
							java.sql.Date Time_serviceAdvacedate = new java.sql.Date(serviceThreshold.getTime());

							// save Service Time_interval Advance Threshold
							// dateReminder
							service.setTime_servicethreshold_date(Time_serviceAdvacedate);

						}
					}

					// service Status in Add ACTIVE
					//service.setServicestatus("ACTIVE");
					service.setServiceStatusId(ServiceReminderDto.SERVICE_REMINDER_STATUS_ACTIVE);
					// service time set FIRST times
					service.setService_remider_howtimes(1);

					/**
					 * who Create this vehicle get email id to user profile
					 * details
					 */
					//service.setCreatedBy(userDetails.getEmail());
					service.setCreatedById(valueObject.getLong("userId",0));
				//	service.setLastModifiedBy(userDetails.getEmail());
					service.setLastModifiedById(valueObject.getLong("userId",0));
					service.setMarkForDelete(false);
					service.setCreated(toDate);
					service.setLastupdated(toDate);
					service.setCompanyId(valueObject.getInt("companyId"));
					service.setServiceType(valueObject.getShort("serviceType", (short) 1));

				
				return service;
			} catch (Exception e) {
				throw e;
			}
		}
		
		private SubscribeBox getSubscribeBoxDto(ServiceReminder service, ValueObject valueObject) throws Exception{
			SubscribeBox		subBox = null;
			try {

				String Subscribe[] = valueObject.getString("assignEmail").split(",");
				String SubscribeId[] = service.getService_subScribedUserId().split(",");
				for (int i = 0 ; i< Subscribe.length ; i++) {
					subBox = new SubscribeBox();

					subBox.setSUBSCRIBE_VEHICLE_ID(service.getVid());
					subBox.setSUBSCRIBE_TYPE_ID(service.getServiceTypeId());
					subBox.setSUBSCRIBE_SUBTYPE_ID(service.getServiceSubTypeId());
					subBox.setSUBSCRIBE_LOCATION_ID(service.getService_id());
					subBox.setSUBSCRIBE_LOCATION("SERVICE_REMINDER");
					subBox.setSUBSCRIBE_USER_EMAIL(Subscribe[i]);
					subBox.setSUBSCRIBE_USER_ID(Long.parseLong(SubscribeId[i]+""));
					subBox.setSUBSCRIBE_DATE(service.getTime_servicedate());
					subBox.setSUBSCRIBE_THRESHOLD_DATE(service.getTime_servicethreshold_date());
					subBox.setCOMPANY_ID(valueObject.getInt("companyId"));

				} 
			
				return subBox;
			} catch (Exception e) {
				throw e;
			}
		}

		@Override
		public List<ServiceReminder> validateServiceReminder(Integer vid, Integer serviceType, Integer serviceSubType)
				throws Exception {
			
			return serviceReminderDao.validateServiceReminder(vid, serviceType, serviceSubType);
		}
		
		@Override
		public List<ServiceReminder> validateServiceReminderEdit(Integer vid, Integer serviceType, Integer serviceSubType,
				Long serviceId) throws Exception {
			return serviceReminderDao.validateServiceReminderEdit(vid, serviceType, serviceSubType, serviceId);
		}
		
		@Override
		public ValueObject getServiceReminderData(ValueObject valueObject) throws Exception {
			try {
				valueObject.put("serviceReminder", getServiceReminder(valueObject.getLong("service_id"), valueObject.getInt("companyId")));
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject updateServiceReminderDetails(ValueObject valueObject) throws Exception {
			HashMap<Long ,UserProfileDto>	userMap				= null;
			ServiceReminder					serviceReminder		= null;
			VehicleDto						vehicle				= null;
			List<ServiceReminder>           validate			= null;
			int								duplicateCount		= 0;
			String							duplicateSr			= "";
			try {
					if(valueObject.getString("subscribe", null) != null) {
						userMap	= userProfileService.getUserEmailIdFromUserId(valueObject.getString("subscribe"));
						setAsignedToNames(userMap, valueObject);
					}
				
				
					vehicle	= vehicleService.getVehicle_Details_Service_Reminder_Entries(valueObject.getInt("vid"), valueObject.getInt("companyId"));
					if(vehicle != null) {
						
						ServiceReminder oldReminder	= serviceReminderDao.getServiceReminderById(valueObject.getLong("service_id",0));
						
						serviceReminder = getServiceReminderDto(valueObject, vehicle);
						serviceReminder.setService_id(valueObject.getLong("service_id",0));
						serviceReminder.setService_Number(valueObject.getLong("service_Number",0));
						serviceReminder.setDealerServiceEntriesId(oldReminder.getDealerServiceEntriesId());
						serviceReminder.setServiceProgramId(oldReminder.getServiceProgramId());
						serviceReminder.setServiceScheduleId(oldReminder.getServiceScheduleId());
						
						validate	=	validateServiceReminderEdit(serviceReminder.getVid(), serviceReminder.getServiceTypeId(), serviceReminder.getServiceSubTypeId(), serviceReminder.getService_id());
						if(validate != null && !validate.isEmpty()) {
							duplicateCount ++;
							duplicateSr 	+= validate.get(0).getService_Number()+",";
							valueObject.put("duplicateSr", duplicateSr);
							valueObject.put("duplicateCount", duplicateCount);
							valueObject.put("vehicleLength", 1);
						}
						
						updateServiceReminder(serviceReminder);
						
						processEmailAlert(serviceReminder);
						processSMSAlert(serviceReminder);
						
					}
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Transactional
		private void processEmailAlert(ServiceReminder	serviceReminder) {
			EmailAlertQueue 				email 				= null;
			List<EmailAlertQueue>  			pendingList			= null;
			try {
				pendingList	= emailAlertQueueService.getAllEmailAlertQueueDetailsById(serviceReminder.getService_id());
				
				emailAlertQueueService.deleteEmailAlertQueue(serviceReminder.getService_id());
				
				if(pendingList != null && !pendingList.isEmpty()) {
					
					for(EmailAlertQueue	emailAlertQueue : pendingList) {
						
						if(emailAlertQueue.getAlertBeforeDate() != null) {
						email = new EmailAlertQueue();
						email.setVid(emailAlertQueue.getVid());
						email.setContent(emailAlertQueue.getContent());
						email.setAlertType(emailAlertQueue.getAlertType());
						email.setEmailId(emailAlertQueue.getEmailId());
						email.setCompanyId(emailAlertQueue.getCompanyId());
						email.setTransactionId(emailAlertQueue.getTransactionId());
						email.setTransactionNumber(emailAlertQueue.getTransactionNumber());
						email.setOverDueAlert(false);
						email.setEmailSent(false);
						email.setAlertBeforeValues(emailAlertQueue.getAlertBeforeValues());
						email.setServiceDate(serviceReminder.getTime_servicedate());
						
							
						final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
						Calendar calendar1 = Calendar.getInstance();
						calendar1.setTime(serviceReminder.getTime_servicedate());
						
						calendar1.add(Calendar.DAY_OF_YEAR, -emailAlertQueue.getAlertBeforeValues());
						java.util.Date alertDate1 = format1.parse(format1.format(calendar1.getTime()));
						java.util.Date alertBeforeDate =  new Date(alertDate1.getTime());
						email.setAlertBeforeDate(alertBeforeDate+"");
						email.setAlertScheduleDate(alertBeforeDate);
					
						emailAlertQueueService.updateEmailAlertQueue(email);
					  } else {
						   
						  EmailAlertQueue email1 = new EmailAlertQueue();
							email1.setVid(emailAlertQueue.getVid());
							email1.setContent(emailAlertQueue.getContent());
							email1.setAlertType(emailAlertQueue.getAlertType());
							email1.setEmailId(emailAlertQueue.getEmailId());
							email1.setCompanyId(emailAlertQueue.getCompanyId());
							email1.setTransactionId(emailAlertQueue.getTransactionId());
							email1.setTransactionNumber(emailAlertQueue.getTransactionNumber());
							email1.setOverDueAlert(false);
							email1.setEmailSent(false);
							email1.setAlertAfterValues(emailAlertQueue.getAlertAfterValues());
							email1.setServiceDate(serviceReminder.getTime_servicedate());
								
							final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
							Calendar calendar2 = Calendar.getInstance();
							calendar2.setTime(serviceReminder.getTime_servicedate());
							
							calendar2.add(Calendar.DAY_OF_YEAR, emailAlertQueue.getAlertAfterValues());
							java.util.Date alertDate1 = format2.parse(format2.format(calendar2.getTime()));
							java.util.Date alertAfterDate =  new Date(alertDate1.getTime());
							email1.setAlertAfterDate(alertAfterDate+"");
							email1.setAlertScheduleDate(alertAfterDate);
						
							emailAlertQueueService.updateEmailAlertQueue(email1);
						  
					       }
					}	
				}	
			} catch (Exception e) {
				System.err.println("Excepection inside processEmailAlert");
			}finally {
				email 				= null;
				pendingList			= null;
			}
		}
		
		@Transactional
		private void processSMSAlert(ServiceReminder	serviceReminder) throws Exception{
			SmsAlertQueue 					sms 				= null;
			List<SmsAlertQueue>  			pendingList1		= null;
			try {
				pendingList1	= smsAlertQueueService.getAllSmsAlertQueueDetailsById(serviceReminder.getService_id());
				
				smsAlertQueueService.deleteSmsAlertQueue(serviceReminder.getService_id());
				
					if(pendingList1 != null && !pendingList1.isEmpty()) {
					
					for(SmsAlertQueue	smsAlertQueue : pendingList1) {
						
						if(smsAlertQueue.getAlertBeforeDate() != null) {
						sms = new SmsAlertQueue();
						sms.setVid(smsAlertQueue.getVid());
						sms.setContent(smsAlertQueue.getContent());
						sms.setAlertType(smsAlertQueue.getAlertType());
						sms.setMobileNumber(smsAlertQueue.getMobileNumber());
						sms.setCompanyId(smsAlertQueue.getCompanyId());
						sms.setTransactionId(smsAlertQueue.getTransactionId());
						sms.setTransactionNumber(smsAlertQueue.getTransactionNumber());
						sms.setOverDueAlert(false);
						sms.setSmsSent(false);
						sms.setAlertBeforeValues(smsAlertQueue.getAlertBeforeValues());
						sms.setServiceDate(serviceReminder.getTime_servicedate());
						
							
						final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
						Calendar calendar1 = Calendar.getInstance();
						calendar1.setTime(serviceReminder.getTime_servicedate());
						
						calendar1.add(Calendar.DAY_OF_YEAR, -smsAlertQueue.getAlertBeforeValues());
						java.util.Date alertDate1 = format1.parse(format1.format(calendar1.getTime()));
						java.util.Date alertBeforeDate =  new Date(alertDate1.getTime());
						sms.setAlertBeforeDate(alertBeforeDate+"");
						sms.setAlertScheduleDate(alertBeforeDate);
					
						smsAlertQueueService.updateSmsAlertQueue(sms);
					  } else {
						   
						  SmsAlertQueue sms1 = new SmsAlertQueue();
							sms1.setVid(smsAlertQueue.getVid());
							sms1.setContent(smsAlertQueue.getContent());
							sms1.setAlertType(smsAlertQueue.getAlertType());
							sms1.setMobileNumber(smsAlertQueue.getMobileNumber());
							sms1.setCompanyId(smsAlertQueue.getCompanyId());
							sms1.setTransactionId(smsAlertQueue.getTransactionId());
							sms1.setTransactionNumber(smsAlertQueue.getTransactionNumber());
							sms1.setOverDueAlert(false);
							sms1.setSmsSent(false);
							sms1.setAlertAfterValues(smsAlertQueue.getAlertAfterValues());
							sms1.setServiceDate(serviceReminder.getTime_servicedate());
								
							final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
							Calendar calendar2 = Calendar.getInstance();
							calendar2.setTime(serviceReminder.getTime_servicedate());
							
							calendar2.add(Calendar.DAY_OF_YEAR, smsAlertQueue.getAlertAfterValues());
							java.util.Date alertDate1 = format2.parse(format2.format(calendar2.getTime()));
							java.util.Date alertAfterDate =  new Date(alertDate1.getTime());
							sms1.setAlertAfterDate(alertAfterDate+"");
							sms1.setAlertScheduleDate(alertAfterDate);
						
							smsAlertQueueService.updateSmsAlertQueue(sms1);
						  
					       }
					}	
				}	
			} catch (Exception e) {
				System.err.println("Excepection inside processSMSAlert");
			}finally {
				sms 				= null;
				pendingList1		= null;
			}
		}
		
		@Override
		public ValueObject searchServiceReminderByNumber(ValueObject valueObject) throws Exception {
			CustomUserDetails		userDetails		= null;
			try {
					userDetails	= new CustomUserDetails(valueObject.getInt("companyId"), valueObject.getLong("userId"));
					valueObject.put("service_id", getServiceIdByNumber(valueObject.getLong("serviceNumber"), userDetails));
				
					return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
				userDetails		= null;
			}
		}
		
		@Override
		public Long getServiceIdByNumber(Long number, CustomUserDetails userDetails) throws Exception {
			try {
				if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
					return serviceReminderDao.getServiceIdByNumber(number, userDetails.getCompany_id());
				}else {
					return serviceReminderDao.getServiceIdByNumber(number, userDetails.getCompany_id(), userDetails.getId());
				}
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public ValueObject searchServiceReminderByMultiple(ValueObject valueObject) throws Exception {
			CustomUserDetails					 userDetails		= null;
			List<ServiceReminderDto>			 pageList			= null;
			try {
				userDetails	= new CustomUserDetails(valueObject.getInt("companyId",0), valueObject.getLong("userId",0));
				
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
				
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(toDate);
				calendar.add(Calendar.DAY_OF_MONTH, 30);
				Date toDatePlus30Days = calendar.getTime();

				pageList = SRBL.prepareListofServiceReminderAjax(
						SearchServiceReminderByNumber(valueObject.getString("serviceNumber"), userDetails));
				
				valueObject.put("serviceReminderList", pageList);
				
				valueObject.put("TodayOverDueServiceRemindercount",
										countTodayOverDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));

				valueObject.put("TodayDueServiceRemindercount",
										countTodayDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));

				valueObject.put("TodaysDueServiceRemindercount",
						countTodaysDueServiceReminder(toDate, userDetails.getCompany_id(), userDetails.getId()));
				
				valueObject.put("UpcomingDueServiceRemindercount",
						countUpcomingDueServiceReminder(toDate, toDatePlus30Days, userDetails.getCompany_id(), userDetails.getId()));
				
			}catch (Exception e) {
				System.err.println("Exception : "+e);
			}
			return valueObject;
		}
		
		@Override
		@Transactional
		public ValueObject deleteServiceReminderById(ValueObject valueObject) throws Exception {
			CustomUserDetails	userDetails		= null;
			try {
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
				userDetails	= new CustomUserDetails(valueObject.getInt("companyId"), valueObject.getLong("userId"));
				deleteServiceReminder(valueObject.getLong("service_id"),toDate,userDetails.getId(), userDetails.getCompany_id());
				valueObject.put("delete", true);
			} catch (Exception e) {
				valueObject.put("delete", false);
			}
			return valueObject;
		}
		
		
		
		@Override
		public ValueObject getVehicleWiseActiveSR(ValueObject valueObject) throws Exception {		
			ValueObject								valueOutObject							= null;
			CustomUserDetails 						userDetails								= null;
			ValueObject						   	 	tableConfig								= null;
			List<ServiceReminderDto>				serviceReminderList						= null;
			String									vehicle									= "";
			
			
			try {
				valueOutObject 							= new ValueObject();
				tableConfig								= new ValueObject();
				serviceReminderList						= new ArrayList<ServiceReminderDto>();
				userDetails								= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
				if(valueObject.getInt("vehicleId",0) > 0) {
					vehicle = " AND SR.vid ="+valueObject.getInt("vehicleId")+"";
				}
				valueObject.put("vehicle", vehicle);
				valueObject.put("companyId", userDetails.getCompany_id());
				valueObject.put("userId", userDetails.getId());
				
				serviceReminderList = getVehicleWiseActiveSRList(valueObject); // List
				valueOutObject.put("serviceReminderList", serviceReminderList);
				
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VEHICLE_WISE_ACTIVE_SR_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);	
				
				valueOutObject.put("tableConfig", tableConfig.getHtData());
				valueOutObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
				
				return valueOutObject;
			} catch (Exception e) {
				throw e;
			}finally {
				valueOutObject					= null;
				userDetails						= null;
				tableConfig						= null;
			}
		}
		
		
		@Transactional
		public List<ServiceReminderDto> getVehicleWiseActiveSRList(ValueObject valueObject) throws Exception {
			TypedQuery<Object[]> query = null;
			try {
				if(!companyConfigurationService.getVehicleGroupWisePermission(valueObject.getInt("companyId"), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
					
					query = entityManager
							.createQuery("SELECT SR.service_id, SR.service_Number, SR.vid, V.vehicle_registration, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT ,"
									+ " SR.created, SR.vehicle_currentOdometer ,SR.meter_servicethreshold_odometer, SR.time_servicethreshold_date ,SR.meter_serviceodometer , SR.time_servicedate FROM ServiceReminder AS SR"
									+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
									+ " LEFT JOIN JobType JT ON JT.Job_id = SR.serviceTypeId"
									+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = SR.serviceSubTypeId"
									+ " where SR.vehicleGroupId = "+valueObject.getInt("vehicleGroup")+" AND SR.markForDelete = 0 AND SR.companyId = "+valueObject.getInt("companyId")+" "
									+ valueObject.getString("vehicle") + " "
									+ " ORDER BY SR.time_servicedate ASC ", Object[].class);
				}else {
					
					query = entityManager
							.createQuery("SELECT SR.service_id, SR.service_Number, SR.vid, V.vehicle_registration, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT ,"
									+ " SR.created, SR.vehicle_currentOdometer ,SR.meter_servicethreshold_odometer, SR.time_servicethreshold_date ,SR.meter_serviceodometer , SR.time_servicedate FROM ServiceReminder AS SR"
									+ " INNER JOIN Vehicle V ON V.vid = SR.vid "
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SR.vehicleGroupId AND VGP.user_id = "+valueObject.getLong("userId")+" "
									+ " LEFT JOIN JobType JT ON JT.Job_id = SR.serviceTypeId"
									+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = SR.serviceSubTypeId"
									+ " where SR.vehicleGroupId = "+valueObject.getInt("vehicleGroup")+" AND SR.markForDelete = 0 AND SR.companyId = "+valueObject.getInt("companyId")+""
									+ valueObject.getString("vehicle") + ""
									+ " ORDER BY SR.time_servicedate ASC ", Object[].class);
				
				}
				List<Object[]> results = query.getResultList();
	
				List<ServiceReminderDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<ServiceReminderDto>();
					ServiceReminderDto list = null;
					for (Object[] result : results) {
						list = new ServiceReminderDto();
						
						list.setService_id((Long) result[0]);
						list.setSrNumber("<a href=\"ShowService.in?service_id="+list.getService_id()+"\" target=\"_blank\">SR- " + (Long) result[1] + "</a>");
						list.setVid((Integer) result[2]);
						list.setVehicle_registration("<a href=\"showVehicle.in?vid="+list.getVid()+"\" target=\"_blank\">SR- " + (String) result[3] + "</a>");
						list.setService_type((String) result[4]);
						list.setService_subtype((String) result[5] + " - "+ (String) result[6]);
						list.setCreatedOn((Date) result[7]);
						list.setCreated(dateFormat.format(list.getCreatedOn()));
						list.setVehicle_currentOdometer((Integer) result[8]);
						list.setMeter_servicethreshold_odometer((Integer) result[9]);
						list.setTime_servicethreshold_date((Date) result[10]);
						list.setThreshHoldDate(dateFormat.format(list.getTime_servicethreshold_date()));
						list.setMeter_serviceodometer((Integer) result[11]);
						list.setTime_servicedate((Date) result[12]);
						list.setServceDate(dateFormat.format(list.getTime_servicedate()));
						
						Dtos.add(list);
					}
				}
				return Dtos;
			
			}catch(Exception e) {
				e.printStackTrace();
				throw e;
			}
		
		}		
		
		@Override
		public ServiceReminderDto getServiceReminderByVidAndServiceId(Integer vid, long serviceId, Integer companyId)throws Exception {
			TypedQuery<Object[]> 	query 				= null;
			ServiceReminderDto 		serviceReminderDto	= null;
			
			try {
				query = entityManager.createQuery(
					"SELECT SR.service_id, SR.serviceTypeId, SR.serviceSubTypeId, JT.Job_Type, JST.Job_ROT "
						+ "	FROM ServiceReminder SR "
						+ " INNER JOIN JobType JT ON JT.Job_id = SR.serviceTypeId "
						+ " INNER JOIN JobSubType JST ON JST.Job_Subid = SR.serviceSubTypeId "
						+ " WHERE SR.service_id = "+serviceId+" AND SR.vid = "+vid+" AND SR.companyId ="+companyId+" AND  SR.markForDelete = 0", Object[].class);
				
				Object[] result = null;
				try {
					result = (Object[]) query.getSingleResult();
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}

				
				if (result != null) {
					serviceReminderDto = new ServiceReminderDto();
					serviceReminderDto.setService_id((Long) result[0]);
					serviceReminderDto.setServiceTypeId((Integer) result[1]);
					serviceReminderDto.setServiceSubTypeId((Integer) result[2]);
					serviceReminderDto.setServiceReminderType((String) result[3]);
					serviceReminderDto.setService_subtype((String) result[4]);
				}
				
				return serviceReminderDto;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		
		
		@Override
		@Transactional 
		public void updateServiceIdInServiceReminder(ValueObject object) throws Exception {
			CustomUserDetails	userDetails		= null;
			try {
				userDetails = (CustomUserDetails) object.get("userDetails");
				entityManager.createQuery(
					"Update ServiceReminder SET serviceEntries_id = "+object.getLong("serviceEntryId")+", last_servicecompleldbyId ="+userDetails.getId()+" "
					+ " Where service_id IN ("+object.getString("serviceReminderId")+") "
					+ " AND companyId = "+userDetails.getCompany_id()+" ").executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Override
		public Map<String, ServiceReminder> getMapOfServiceReminderByCompanyId(Long vehicleType, Long vehiclModal, Integer companyId,String branchQuery) throws Exception {
			Map<String, ServiceReminder>			srMap				= null;	
			List<ServiceReminder>					serviceReminderList	= null;			
			try {
					TypedQuery<ServiceReminder> query = entityManager
							.createQuery("SELECT SR from ServiceReminder SR "
									+ " INNER JOIN Vehicle VGP ON VGP.vid = SR.vid AND VGP.markForDelete = 0 "
									+ " where VGP.vehicleTypeId = "+vehicleType+" AND VGP.vehicleModalId = "+vehiclModal+" "+branchQuery+" "
									+ " AND  SR.companyId = "+companyId+" AND SR.markForDelete = 0 ", ServiceReminder.class);
					serviceReminderList	= query.getResultList();
					
					if(serviceReminderList != null && !serviceReminderList.isEmpty()) {
						srMap	= new HashMap<String, ServiceReminder>();
						for (ServiceReminder serviceReminder : serviceReminderList) {
							srMap.put(serviceReminder.getVid()+"_"+serviceReminder.getServiceTypeId()+"_"+serviceReminder.getServiceSubTypeId(), 
										serviceReminder);
						}
					}
					
					return srMap;
				} catch (Exception e) {
					throw e;
				}
		}
		
		@Override
		@Transactional
		public void deleteServiceReminderForSchedule(Long serviceScheduleId, Integer companyId, Long userId, Long serviceProgramId) throws Exception {
			entityManager.createNativeQuery(" UPDATE servicereminder sr"
					+ " SET sr.markForDelete  = 1, sr.lastModifiedById = "+userId+", "
					+ " sr.lastupdated = '"+DateTimeUtility.getCurrentDateStr(DateTimeUtility.YYYY_MM_DD_HH_MM_SS)+"' "
					+ " where sr.serviceScheduleId = "+serviceScheduleId+" AND  sr.companyId = "+companyId+" "
					+ " and sr.markForDelete = 0 " ).executeUpdate();
					
		}
		
		@Override
		@Transactional
		public void deleteReminderServiceProgramAssignment(Long vehicleTypeId, Long modalId, Long serviceProgramId,
				Integer companyId) throws Exception {
			
			entityManager.createNativeQuery(" UPDATE servicereminder sr "
					+ " INNER JOIN Vehicle V ON V.vid = sr.vid "
					+ " SET sr.markForDelete  = 1 "
					+ " WHERE V.vehicleTypeId = "+vehicleTypeId+" AND V.vehicleModalId = "+modalId+" "
					+ " AND sr.serviceProgramId = "+serviceProgramId+" AND  sr.companyId = "+companyId+" "
					+ "  " ).executeUpdate();
					
		}
		
		@Override
		public List<ServiceReminder> getServiceReminderListByServiceReminderIds(String serviceReminderIds , Integer companyId ) throws Exception {
			try {
				
				TypedQuery<ServiceReminder> query = entityManager
						.createQuery("FROM ServiceReminder SR where SR.service_id IN (" + serviceReminderIds + ") AND SR.companyId = "+companyId+" "
								+ " AND SR.markForDelete = 0 ", ServiceReminder.class);
				return query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		@Override
		public DealerServiceReminderHistory getDealerServiceReminderHistory(Long dealerServiceEntriesId) throws Exception {
			try {
				return DSE_HistoryRepository.getDealerServiceReminderHistory(dealerServiceEntriesId);
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Override
		@Transactional
		public List<ServiceReminderDto> listServiceReminderByServiceProgram(Long serviceProgram,Long sheduledId, CustomUserDetails userDetails) throws Exception {
			TypedQuery<Object[]> query =	null;
			try {
				
			
			if(sheduledId > 0) {
				
				
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration,JT.Job_Type, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT "
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
								+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number"
								+ "  FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " LEFT JOIN User U ON U.id = v.createdById"
								+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
								+ " WHERE v.serviceProgramId="+serviceProgram+" AND v.serviceScheduleId="+sheduledId+"  AND v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
								+ "AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") ORDER BY v.time_servicedate asc", Object[].class);
				
			}else {
				
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, JT.Job_Type, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT"
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
								+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " LEFT JOIN User U ON U.id = v.createdById"
								+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
								+ " WHERE v.serviceProgramId="+serviceProgram+" AND v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
								+ " AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") ORDER BY v.time_servicedate asc", Object[].class);

			}
			
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setVid((Integer) result[1]);
					list.setVehicle_registration((String) result[2]);
					list.setVehicle_Group((String) result[3]);
					list.setService_type((String) result[4]);
					if(result[5] != null) {
						list.setService_subtype((String) result[5]+" - "+(String) result[6]);
					}else {
						list.setService_subtype((String) result[6]);
					}
					list.setMeter_interval((Integer) result[7]);
					list.setVehicle_currentOdometer((Integer) result[8]);
					list.setMeter_serviceodometer((Integer) result[9]);
					list.setMeter_threshold((Integer) result[10]);
					list.setMeter_servicethreshold_odometer((Integer) result[11]);
					list.setTime_interval((Integer) result[12]);
					list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
					list.setTime_intervalperiodId((short) result[13]);
					list.setTime_interval_currentdate( (Date) result[14]);
					list.setTime_servicedate((Date) result[15]);
					list.setTime_threshold((Integer) result[16]);
					list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
					list.setTime_thresholdperiodId((short) result[17]);
					list.setTime_servicethreshold_date((Date) result[18]);
					list.setService_emailnotification((String) result[19]);
					list.setService_subscribeduser((String) result[20]);
					list.setService_subscribeduser_name((String) result[21]);
					list.setLast_servicedate((Date) result[22]);
					list.setLast_servicecompleldby((String) result[23]);
					list.setLast_servicecompleldid((Long) result[24]);
					list.setService_remider_howtimes((Integer) result[25]);
					list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
					list.setServiceStatusId((short) result[26]);
					list.setVehicleGroupId((long) result[27]);
					list.setCreatedBy((String) result[28]);
					list.setService_Number((Long) result[29]);
					
					Dtos.add(list);
				}
			}
			return Dtos;
			} catch (Exception e) {
				System.err.println("#######In catch");
				e.printStackTrace();
				throw e;
			}
		}

		
		@Override
		public ValueObject getDueSoonServiceListGroupBySProgram(ValueObject valueObject) throws Exception {
			CustomUserDetails 					userDetails 					= null;
			List<ServiceReminderDto>			 pageList						= null; 
			ValueObject						   	 tableConfig					= null;
			try {
				userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
				tableConfig		 = new ValueObject();
				HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_REMINDER_CONFIG);
				
				if(!(boolean) configuration.get("groupListByVehicleNumber")) {
					pageList = SRBL.prepareListofServiceReminderAjax(DueSoonServiceRemnder(toDate, userDetails));
				}else {
					pageList = SRBL.prepareListofServiceReminderAjax(DueSoonServiceRemnderGByVid(toDate, userDetails));
				}
				
				
				
				if(pageList != null && !pageList.isEmpty()) {
					Map<Long, VehicleServiceProgram> 		spHM		= serviceProgramService.getServiceProgramHM(userDetails.getCompany_id());
					
					for (ServiceReminderDto serviceReminderDto : pageList) {
						if(spHM.get(serviceReminderDto.getServiceProgramId()) != null)
						serviceReminderDto.setServiceProgram(spHM.get(serviceReminderDto.getServiceProgramId()).getProgramName());
					}
					
					valueObject.put("serviceReminderList", pageList);

					tableConfig.put("companyId", userDetails.getCompany_id());
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.DUESOON_OVERDUE_POPUP_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);	
					
					valueObject.put("tableConfig", tableConfig.getHtData());
					
				}
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}finally {
				userDetails 					= null;
			}
		}
		
		@Override
		public ValueObject getOverDueServiceListGroupBySProgram(ValueObject valueObject) throws Exception {
			CustomUserDetails 					userDetails 					= null;
			List<ServiceReminderDto>			 pageList						= null; 
			ValueObject						   	 tableConfig					= null;
			try {
				userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
				tableConfig		 = new ValueObject();
					
				pageList = SRBL.prepareListofServiceReminderAjax(OverDueServiceRemnder(toDate, userDetails));
				
				
				if(pageList != null && !pageList.isEmpty()) {
					Map<Long, VehicleServiceProgram> 		spHM		= serviceProgramService.getServiceProgramHM(userDetails.getCompany_id());
					
					for (ServiceReminderDto serviceReminderDto : pageList) {
						if(spHM.get(serviceReminderDto.getServiceProgramId()) != null)
						serviceReminderDto.setServiceProgram(spHM.get(serviceReminderDto.getServiceProgramId()).getProgramName());
					}
					
					valueObject.put("serviceReminderList", pageList);

					tableConfig.put("companyId", userDetails.getCompany_id());
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.DUESOON_OVERDUE_POPUP_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);	
					
					valueObject.put("tableConfig", tableConfig.getHtData());
					
				}
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}finally {
				userDetails 					= null;
			}
		}
		
		@Override
		public List<ServiceReminderDto> OverDueServiceRemnder(Date toDate, CustomUserDetails userDetails) throws Exception {

			try {
				TypedQuery<Object[]> query =	null;
				if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
					query = entityManager
							.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT  " 
									+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
									+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
									+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
									+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
									+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number, v.serviceProgramId, v.serviceScheduleId"
									+ " FROM ServiceReminder v "
									+ " INNER JOIN Vehicle T ON T.vid = v.vid "
									+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
									+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
									+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
									+ " LEFT JOIN User U ON U.id = v.createdById"
									+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
									+ " where v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
									+ " AND ( (v.time_servicedate <= '"+toDate+"' AND v.time_interval > 0)  OR ( v.meter_serviceodometer <= v.vehicle_currentOdometer AND v.meter_interval > 0 ) )", Object[].class);
				
				}else {
					
					query = entityManager
							.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT  " 
									+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
									+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
									+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
									+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
									+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number, v.serviceProgramId, v.serviceScheduleId"
									+ " FROM ServiceReminder v "
									+ " INNER JOIN Vehicle T ON T.vid = v.vid "
									+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
									+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
									+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
									+ " LEFT JOIN User U ON U.id = v.createdById"
									+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
									+ " where v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
									+ " AND ( ( v.time_servicedate <= '"+toDate+"' AND v.time_interval > 0) OR ( v.meter_serviceodometer <= v.vehicle_currentOdometer AND v.meter_interval > 0) )", Object[].class);
					
				}
				
				List<Object[]> results = query.getResultList();

				List<ServiceReminderDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<ServiceReminderDto>();
					ServiceReminderDto list = null;
					for (Object[] result : results) {
						list = new ServiceReminderDto();
						
						list.setService_id((Long) result[0]);
						list.setVid((Integer) result[1]);
						list.setVehicle_registration((String) result[2]);
						list.setVehicle_Group((String) result[3]);
						list.setService_type((String) result[4]);
						if(result[5] != null) {
							list.setService_subtype((String) result[5]+" - "+(String) result[6]);
						}else {
							list.setService_subtype((String) result[6]);
						}
						list.setMeter_interval((Integer) result[7]);
						list.setVehicle_currentOdometer((Integer) result[8]);
						list.setMeter_serviceodometer((Integer) result[9]);
						list.setMeter_threshold((Integer) result[10]);
						list.setMeter_servicethreshold_odometer((Integer) result[11]);
						list.setTime_interval((Integer) result[12]);
						list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
						list.setTime_intervalperiodId((short) result[13]);
						list.setTime_interval_currentdate( (Date) result[14]);
						list.setTime_servicedate((Date) result[15]);
						list.setTime_threshold((Integer) result[16]);
						list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
						list.setTime_thresholdperiodId((short) result[17]);
						list.setTime_servicethreshold_date((Date) result[18]);
						list.setService_emailnotification((String) result[19]);
						list.setService_subScribedUserId((String) result[20]);
						list.setService_subscribeduser_name((String) result[21]);
						list.setLast_servicedate((Date) result[22]);
						list.setLast_servicecompleldby((String) result[23]);
						list.setLast_servicecompleldid((Long) result[24]);
						list.setService_remider_howtimes((Integer) result[25]);
						list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
						list.setServiceStatusId((short) result[26]);
						list.setVehicleGroupId((long) result[27]);
						list.setCreatedBy((String) result[28]);
						list.setService_Number((Long) result[29]);
						list.setServiceProgramId((Long) result[30]);
						list.setServiceScheduleId((Long) result[31]);
						
						Dtos.add(list);
					}
				}
					return Dtos;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<ServiceReminderDto> OverDueServiceRemnderGByVid(Date toDate, CustomUserDetails userDetails,
				Integer pageNumber) throws Exception {

			try {
				TypedQuery<Object[]> query =	null;
				if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
					query = entityManager
							.createQuery("SELECT v.vid, T.vehicle_registration, COUNT(v.service_id) , COUNT(distinct v.serviceProgramId) "
									+ " FROM ServiceReminder v "
									+ " INNER JOIN Vehicle T ON T.vid = v.vid "
									+ " where v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
									+ " AND ( (v.time_servicedate <= '"+toDate+"' AND v.time_interval > 0)  OR "
									+ " ( v.meter_serviceodometer <= v.vehicle_currentOdometer AND v.meter_interval > 0 ) "
									+ " ) group by v.vid", Object[].class);
				
				}else {
					
					query = entityManager
							.createQuery("SELECT v.vid, T.vehicle_registration, COUNT(v.service_id) , COUNT(distinct v.serviceProgramId) "
									+ " FROM ServiceReminder v "
									+ " INNER JOIN Vehicle T ON T.vid = v.vid "
									+ " where v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
									+ " AND ( ( v.time_servicedate <= '"+toDate+"' AND v.time_interval > 0) OR "
									+ " ( v.meter_serviceodometer <= v.vehicle_currentOdometer AND v.meter_interval > 0) "
									+ " ) group by v.vid", Object[].class);
					
				}
				query.setFirstResult((pageNumber - 1) * PAGE_SIZE_15);
				query.setMaxResults(PAGE_SIZE_15);
				
				List<Object[]> results = query.getResultList();

				List<ServiceReminderDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<ServiceReminderDto>();
					ServiceReminderDto list = null;
					for (Object[] result : results) {
						list	= new ServiceReminderDto();
						list.setVid((Integer) result[0]);
						list.setVehicle_registration((String) result[1]);
						list.setService_id((Long) result[2]);
						list.setServiceProgramId((Long) result[3]);
						
						Dtos.add(list);
						
					}
				}
					return Dtos;
			
			} catch (Exception e) {
				throw e;
			}
		}
		@Override
		public List<ServiceReminderDto> TodaysOverDueServiceRemnderGByVid(Date toDate, CustomUserDetails userDetails,
				Integer pageNumber) throws Exception {

			try {
				TypedQuery<Object[]> query =	null;
				if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
					query = entityManager
							.createQuery("SELECT v.vid, T.vehicle_registration, COUNT(v.service_id) , COUNT(distinct v.serviceProgramId) "
									+ " FROM ServiceReminder v "
									+ " INNER JOIN Vehicle T ON T.vid = v.vid "
									+ " where v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
									+ " AND ( (v.time_servicedate = '"+toDate+"' AND v.time_interval > 0)  OR "
									+ " ( v.meter_serviceodometer <= v.vehicle_currentOdometer AND v.meter_interval > 0 ) "
									+ " ) group by v.vid", Object[].class);
				
				}else {
					
					query = entityManager
							.createQuery("SELECT v.vid, T.vehicle_registration, COUNT(v.service_id) , COUNT(distinct v.serviceProgramId) "
									+ " FROM ServiceReminder v "
									+ " INNER JOIN Vehicle T ON T.vid = v.vid "
									+ " where v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
									+ " AND ( ( v.time_servicedate = '"+toDate+"' AND v.time_interval > 0) OR "
									+ " ( v.meter_serviceodometer <= v.vehicle_currentOdometer AND v.meter_interval > 0) "
									+ " ) group by v.vid", Object[].class);
					
				}
				query.setFirstResult((pageNumber - 1) * PAGE_SIZE_15);
				query.setMaxResults(PAGE_SIZE_15);
				
				List<Object[]> results = query.getResultList();

				List<ServiceReminderDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<ServiceReminderDto>();
					ServiceReminderDto list = null;
					for (Object[] result : results) {
						list	= new ServiceReminderDto();
						list.setVid((Integer) result[0]);
						list.setVehicle_registration((String) result[1]);
						list.setService_id((Long) result[2]);
						list.setServiceProgramId((Long) result[3]);
						
						Dtos.add(list);
						
					}
				}
					return Dtos;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<ServiceReminderDto> getServiceProgramListByVid(Integer vehicle_ID, String currentDate, String dueSoon,Integer companyId) throws Exception {
			
			TypedQuery<Object[]> query =	null;
				query = entityManager
						.createQuery("SELECT DISTINCT SP.vehicleServiceProgramId, SP.programName"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN VehicleServiceProgram SP ON SP.vehicleServiceProgramId = v.serviceProgramId"
								+ " WHERE v.vid = "+vehicle_ID+" "
								+ " AND v.companyId = "+companyId+" AND v.markForDelete = 0 ", Object[].class);
			
			query.setFirstResult(0);
			query.setMaxResults(100);
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setServiceProgramId((Long) result[0]);
					list.setServiceProgram((String) result[1]);
					Dtos.add(list);
				}
			}
			return Dtos;
		
		}
		
		@Override
		public List<ServiceReminderDto> getServiceProgramListByVidProgramId(Integer vid, String serviceProgramId,
				Integer companyId) throws Exception {

			TypedQuery<Object[]> query =	null;
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT"
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
								+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number, VP.programName, VT.vtype, B.branch_name, v.serviceTypeId, v.serviceSubTypeId "
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId "
								+ " INNER JOIN VehicleServiceProgram VP ON VP.vehicleServiceProgramId = v.serviceProgramId"
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " LEFT JOIN User U ON U.id = v.createdById"
								+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId "
								+ " LEFT JOIN VehicleType VT ON VT.tid = T.vehicleTypeId "
								+ " LEFT JOIN Branch B ON B.branch_id = T.branchId "
								+ " WHERE v.companyId = "+companyId+" AND v.serviceProgramId IN ( "+serviceProgramId+" ) "
								+ " AND v.vid = "+vid+" AND v.markForDelete = 0 ", Object[].class);
			
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setVid((Integer) result[1]);
					list.setVehicle_registration((String) result[2]);
					list.setVehicle_Group((String) result[3]);
					list.setService_type((String) result[4]);
					if(result[5] != null) {
						list.setService_subtype((String) result[5]+" - "+(String) result[6]);
					}else {
						list.setService_subtype((String) result[6]);
					}
					list.setMeter_interval((Integer) result[7]);
					list.setVehicle_currentOdometer((Integer) result[8]);
					list.setMeter_serviceodometer((Integer) result[9]);
					list.setMeter_threshold((Integer) result[10]);
					list.setMeter_servicethreshold_odometer((Integer) result[11]);
					list.setTime_interval((Integer) result[12]);
					list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
					list.setTime_intervalperiodId((short) result[13]);
					list.setTime_interval_currentdate( (Date) result[14]);
					list.setTime_servicedate((Date) result[15]);
					list.setTime_threshold((Integer) result[16]);
					list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
					list.setTime_thresholdperiodId((short) result[17]);
					list.setTime_servicethreshold_date((Date) result[18]);
					list.setService_emailnotification((String) result[19]);
					list.setService_subscribeduser((String) result[20]);
					list.setService_subscribeduser_name((String) result[21]);
					list.setLast_servicedate((Date) result[22]);
					list.setLast_servicecompleldby((String) result[23]);
					list.setLast_servicecompleldid((Long) result[24]);
					list.setService_remider_howtimes((Integer) result[25]);
					list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
					list.setServiceStatusId((short) result[26]);
					list.setVehicleGroupId((long) result[27]);
					list.setCreatedBy((String) result[28]);
					list.setService_Number((Long) result[29]);
					list.setServiceProgram((String) result[30]);
					list.setVehicleType((String)result[31]);
					list.setSRBranchName((String)result[32]);
					list.setServiceTypeId((Integer)result[33]);
					list.setServiceSubTypeId((Integer)result[34]);
					Dtos.add(list);
				}
			}
			return Dtos;
		}
		
		@Override
		public ValueObject getServiceReminderByProgramIdVid(ValueObject object) throws Exception {
			List<ServiceReminderDto> 	scheduleList	= null;
			try {
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if(!object.getString("serviceProgramId","").trim().equals(""))
				scheduleList	= SRBL.prepareListofServiceReminderAjax(getServiceProgramListByVidProgramId(object.getInt("vid",0), object.getString("serviceProgramId"),
										userDetails.getCompany_id()));
				
				if(scheduleList != null && !scheduleList.isEmpty()) {
				  Map<String, List<ServiceReminderDto>>	scheduleHashMap	=	scheduleList.stream().collect(Collectors.groupingBy(ServiceReminderDto :: getServiceProgram));
				  
				  object.put("serviceSchedules", scheduleList);
				  object.put("scheduleHashMap", scheduleHashMap);
				}
				
				
			} catch (Exception e) {
				  System.err.println("inside exception  "+e);
			}
			return object;
		}
		
		@Override
		public List<ServiceReminderDto> getProgramListByreminderIds(String serviceIds, Integer companyId) throws Exception {

			TypedQuery<Object[]> query =	null;
				query = entityManager
						.createQuery("SELECT DISTINCT VP.vehicleServiceProgramId, VP.programName"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN VehicleServiceProgram VP ON VP.vehicleServiceProgramId = v.serviceProgramId"
								+ " WHERE v.companyId = "+companyId+" AND v.service_id IN ( "+serviceIds+" ) "
								+ " AND v.markForDelete = 0 ", Object[].class);
			
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setServiceProgramId((Long) result[0]);
					list.setServiceProgram((String) result[1]);
					
					Dtos.add(list);
				}
			}
			return Dtos;
		
		}
		
		
		public List<ServiceReminderDto> getSRByreminderIds(String serviceIds, Integer companyId) throws Exception {

			TypedQuery<Object[]> query =	null;
				query = entityManager
						.createQuery("SELECT v.service_id,JT.Job_Type,JST.Job_ROT"
								+ " FROM ServiceReminder v "
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " WHERE v.companyId = "+companyId+" AND v.service_id IN ( "+serviceIds+" ) "
								+ " AND v.markForDelete = 0 ", Object[].class);
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> dtos = null;
			if (results != null && !results.isEmpty()) {
				dtos = new ArrayList<>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					list.setServiceProgramId((Long) result[0]);
					list.setServiceProgram(result[1] + " - "+result[2]);
					dtos.add(list);
				}
			}
			return dtos;
		
		}
		
		
		@Override
		public HashMap<Integer ,ServiceReminderDto> getJobtypeAndSubtypeFromServiceReminderId (String serviceIds, Integer company_id) throws Exception {
			try {
				TypedQuery<Object[]> querySearch = entityManager.createQuery(
					"SELECT  SR.service_id, JT.Job_Type, JST.Job_ROT, SR.serviceTypeId, SR.serviceSubTypeId " 
				       + " From ServiceReminder SR " 
				       + " LEFT JOIN JobType JT ON JT.Job_id = SR.serviceTypeId " 
					   + " LEFT JOIN JobSubType JST ON JST.Job_Subid = SR.serviceSubTypeId " 
					   + " where SR.service_id IN ( "+serviceIds+" ) AND SR.companyId = "+company_id+" " 
					   + " AND SR.markForDelete = 0 ",
						Object[].class);
				
				List<Object[]> results = querySearch.getResultList();

				HashMap<Integer, ServiceReminderDto> dtosMap = null;
				if (results != null && !results.isEmpty()) {
					
					dtosMap = new HashMap<Integer ,ServiceReminderDto>();
					ServiceReminderDto list = null;
					for (Object[] result : results) {
						list = new ServiceReminderDto();
						
						list.setService_id((long) result[0]);
						list.setService_type((String) result[1]);
						list.setService_subtype((String) result[2]);
						list.setServiceTypeId((Integer) result[3]);
						list.setServiceSubTypeId((Integer) result[4]);
						
						dtosMap.put(list.getServiceSubTypeId(),list);
					}
				}

				return	dtosMap;
			
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			
		}
		
		
		@Override
		public ValueObject getServiceReminderByServiceId(ValueObject object) throws Exception {
			List<ServiceReminderDto> 	scheduleList	= null;
			try {
				if(!object.getString("serviceReminderIds","").trim().equals(""))
				scheduleList	= SRBL.prepareListofServiceReminderAjax(getServiceReminderByServiceId(object.getString("serviceReminderIds"), object.getInt("companyId")));
				if(scheduleList != null && !scheduleList.isEmpty()) {
				  Map<String, List<ServiceReminderDto>>	scheduleHashMap	=	scheduleList.stream().collect(Collectors.groupingBy(ServiceReminderDto :: getServiceProgram));
				  
				  object.put("serviceSchedules", scheduleList);
				  object.put("scheduleHashMap", scheduleHashMap);
				}
				
				
			} catch (Exception e) {
				  System.err.println("inside exception  "+e);
			}
			return object;
		}
		
		
		@Override
		public List<ServiceReminderDto> getServiceReminderByServiceId(String serviceIds, Integer companyId) throws Exception {

			TypedQuery<Object[]> query =	null;
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT"
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
								+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number, VP.programName"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId "
								+ " LEFT JOIN VehicleServiceProgram VP ON VP.vehicleServiceProgramId = v.serviceProgramId"
								+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " LEFT JOIN User U ON U.id = v.createdById"
								+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
								+ " WHERE v.companyId = "+companyId+" AND v.service_id IN ( "+serviceIds+" ) "
								+ " AND v.markForDelete = 0 ", Object[].class);
			
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setVid((Integer) result[1]);
					list.setVehicle_registration((String) result[2]);
					list.setVehicle_Group((String) result[3]);
					list.setService_type((String) result[4]);
					if(result[5] != null) {
						list.setService_subtype((String) result[5]+" - "+(String) result[6]);
					}else {
						list.setService_subtype((String) result[6]);
					}
					list.setMeter_interval((Integer) result[7]);
					list.setVehicle_currentOdometer((Integer) result[8]);
					list.setMeter_serviceodometer((Integer) result[9]);
					list.setMeter_threshold((Integer) result[10]);
					list.setMeter_servicethreshold_odometer((Integer) result[11]);
					list.setTime_interval((Integer) result[12]);
					list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
					list.setTime_intervalperiodId((short) result[13]);
					list.setTime_interval_currentdate( (Date) result[14]);
					list.setTime_servicedate((Date) result[15]);
					list.setTime_threshold((Integer) result[16]);
					list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
					list.setTime_thresholdperiodId((short) result[17]);
					list.setTime_servicethreshold_date((Date) result[18]);
					list.setService_emailnotification((String) result[19]);
					list.setService_subscribeduser((String) result[20]);
					list.setService_subscribeduser_name((String) result[21]);
					list.setLast_servicedate((Date) result[22]);
					list.setLast_servicecompleldby((String) result[23]);
					list.setLast_servicecompleldid((Long) result[24]);
					list.setService_remider_howtimes((Integer) result[25]);
					list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
					list.setServiceStatusId((short) result[26]);
					list.setVehicleGroupId((long) result[27]);
					list.setCreatedBy((String) result[28]);
					list.setService_Number((Long) result[29]);
					list.setServiceProgram((String) result[30]);
					
					Dtos.add(list);
				}
			}
			return Dtos;
		}
		
		
		@Override
		public List<ServiceReminderDto> getSRByVehicleTypeAndModal(Long vehicleTypeId, Long modalId, Long serviceProgramId,
				Integer companyId) throws Exception {

			TypedQuery<Object[]> query =	null;
				query = entityManager
						.createQuery("SELECT DISTINCT SR.service_id,SR.vid "
								+ " FROM ServiceReminder SR "
								+ " INNER JOIN Vehicle V ON V.vid = SR.vid"
								+ " WHERE SR.companyId = "+companyId+" AND SR.markForDelete = 0 "
								+ " AND  V.vehicleTypeId = "+vehicleTypeId+" AND V.vehicleModalId = "+modalId+" ", Object[].class);
			
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					list.setService_id((Long) result[0]);
						
					Dtos.add(list);
				}
			}
			return Dtos;
		
		}
		
		@Override
		public List<ServiceReminderDto> getSRByVehicleTypeModalAndBranach(Long vehicleTypeId, Long modalId, Integer branchId, Long serviceProgramId,
				Integer companyId) throws Exception {

			TypedQuery<Object[]> query =	null;
				query = entityManager
						.createQuery("SELECT DISTINCT SR.service_id,SR.vid "
								+ " FROM ServiceReminder SR "
								+ " INNER JOIN Vehicle V ON V.vid = SR.vid"
								+ " WHERE SR.companyId = "+companyId+" AND SR.markForDelete = 0 "
								+ " AND  V.vehicleTypeId = "+vehicleTypeId+" AND V.vehicleModalId = "+modalId+" AND V.branchId = "+branchId+" AND SR.serviceProgramId = "+serviceProgramId+" ", Object[].class);
			
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					list.setService_id((Long) result[0]);
						
					Dtos.add(list);
				}
			}
			return Dtos;
		
		}
		
		@Override
		@Transactional
		public void deleteReminderByServiceIds(String serviceIds) throws Exception {
			try {
				entityManager.createQuery(
						"Update ServiceReminder SET markForDelete = 1"
								+ " Where service_id IN("+serviceIds+")")
				.executeUpdate();
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Override
		@Transactional
		public void deleteServiceReminderByVid(Integer vid, Integer companyId, Long serviceProgramId) throws Exception {
			serviceReminderDao.deleteServiceReminderByVid(vid, companyId, serviceProgramId);
		}
		
		@Override
		public ValueObject getServiceReminderOfVehicle(ValueObject object) throws Exception {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			List<ServiceReminderDto>	list = SRBL.prepareListofServiceReminderAjax(listServiceReminderByVid(object.getInt("vid"), userDetails));
			if(list != null && !list.isEmpty()) {
				  Map<String, List<ServiceReminderDto>>	scheduleHashMap	=	list.stream().collect(Collectors.groupingBy(ServiceReminderDto :: getServiceProgram));
				  object.put("reminderHM", scheduleHashMap);
			}
			object.put("serviceReminderList", list);
			
			return object;
		}
		
		@Override
		public ValueObject getOverDueReminderOfVehicle(ValueObject object) throws Exception {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			List<ServiceReminderDto>	list = SRBL.prepareListofServiceReminderAjax(listOverDueReminderByVid(object.getInt("vid"), userDetails));
			
			if(list != null && !list.isEmpty()) {
				  Map<String, List<ServiceReminderDto>>	scheduleHashMap	=	list.stream().collect(Collectors.groupingBy(ServiceReminderDto :: getServiceProgram));
				  object.put("reminderHM", scheduleHashMap);
			}
			
			object.put("serviceReminderList", list);
			
			return object;
		}
		
		@Override
		public List<ServiceReminderDto> listOverDueReminderByVid(Integer vid, CustomUserDetails userDetails)
				throws Exception {
			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
			TypedQuery<Object[]> query =	null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT"
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
								+ ",v.service_subscribeduser_name, v.last_servicedate,  v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, v.service_Number, VP.programName, T.vehicle_Odometer,v.isSkip "
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " INNER JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " INNER JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId "
								+ " LEFT JOIN VehicleServiceProgram VP ON VP.vehicleServiceProgramId = v.serviceProgramId"
								+ " where v.vid = "+vid+" and v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
								+ " AND ( (v.time_servicedate <= '"+toDate+"' AND v.time_interval > 0)  OR "
								+ " ( v.meter_serviceodometer <= v.vehicle_currentOdometer AND v.meter_interval > 0 ) "
								+ " ) AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") ORDER BY v.service_id desc ", Object[].class);
			}else {
				
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT "
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
								+ ",v.service_subscribeduser_name, v.last_servicedate, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, v.service_Number, VP.programName, T.vehicle_Odometer,v.isSkip"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " INNER JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " INNER JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
								+ " LEFT JOIN VehicleServiceProgram VP ON VP.vehicleServiceProgramId = v.serviceProgramId"
								+ " where v.vid = "+vid+" and v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
								+ " AND ( (v.time_servicedate <= '"+toDate+"' AND v.time_interval > 0)  OR "
								+ " ( v.meter_serviceodometer <= v.vehicle_currentOdometer AND v.meter_interval > 0 ) "
								+ " ) AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") ORDER BY v.service_id desc ", Object[].class);
			}
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setVid((Integer) result[1]);
					list.setVehicle_registration((String) result[2]);
					list.setVehicle_Group((String) result[3]);
					list.setService_type((String) result[4]);
					if(result[5] != null) {
						list.setService_subtype((String) result[5]+" - "+(String) result[6]);
					}else {
						list.setService_subtype((String) result[6]);
					}
					list.setMeter_interval((Integer) result[7]);
					list.setVehicle_currentOdometer((Integer) result[8]);
					list.setMeter_serviceodometer((Integer) result[9]);
					list.setMeter_threshold((Integer) result[10]);
					list.setMeter_servicethreshold_odometer((Integer) result[11]);
					list.setTime_interval((Integer) result[12]);
					list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
					list.setTime_intervalperiodId((short) result[13]);
					list.setTime_interval_currentdate( (Date) result[14]);
					list.setTime_servicedate((Date) result[15]);
					list.setTime_threshold((Integer) result[16]);
					list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
					list.setTime_thresholdperiodId((short) result[17]);
					list.setTime_servicethreshold_date((Date) result[18]);
					list.setService_emailnotification((String) result[19]);
					list.setService_subscribeduser((String) result[20]);
					list.setService_subscribeduser_name((String) result[21]);
					list.setLast_servicedate((Date) result[22]);
					list.setLast_servicecompleldid((Long) result[23]);
					list.setService_remider_howtimes((Integer) result[24]);
					list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[25]));
					list.setServiceStatusId((short) result[25]);
					list.setVehicleGroupId((long) result[26]);
					list.setService_Number((Long) result[27]);
					list.setServiceProgram((String) result[28]);
					list.setVehicle_currentOdometer((Integer) result[29]);
					list.setSkip((boolean) result[30]);
					
					Dtos.add(list);
				}
			}
			return Dtos;
		}
		
		@Override
		public List<ServiceReminderDto> listServiceReminderByVid(Integer vid, CustomUserDetails userDetails)
				throws Exception {

			TypedQuery<Object[]> query =	null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT"
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
								+ ",v.service_subscribeduser_name, v.last_servicedate,  v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, v.service_Number, VP.programName, T.vehicle_Odometer,v.isSkip "
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " INNER JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " INNER JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId "
								+ " LEFT JOIN VehicleServiceProgram VP ON VP.vehicleServiceProgramId = v.serviceProgramId"
								+ " WHERE v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 and v.vid = "+vid+" "
								+ " AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+")ORDER BY v.service_id desc ", Object[].class);
			}else {
				
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT "
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
								+ ",v.service_subscribeduser_name, v.last_servicedate, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, v.service_Number, VP.programName, T.vehicle_Odometer,v.isSkip"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " INNER JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " INNER JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
								+ " LEFT JOIN VehicleServiceProgram VP ON VP.vehicleServiceProgramId = v.serviceProgramId"
								+ " WHERE v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 and v.vid = "+vid+" "
								+ " AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") ORDER BY v.service_id desc", Object[].class);
			}
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setVid((Integer) result[1]);
					list.setVehicle_registration((String) result[2]);
					list.setVehicle_Group((String) result[3]);
					list.setService_type((String) result[4]);
					if(result[5] != null) {
						list.setService_subtype((String) result[5]+" - "+(String) result[6]);
					}else {
						list.setService_subtype((String) result[6]);
					}
					list.setMeter_interval((Integer) result[7]);
					list.setVehicle_currentOdometer((Integer) result[8]);
					list.setMeter_serviceodometer((Integer) result[9]);
					list.setMeter_threshold((Integer) result[10]);
					list.setMeter_servicethreshold_odometer((Integer) result[11]);
					list.setTime_interval((Integer) result[12]);
					list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
					list.setTime_intervalperiodId((short) result[13]);
					list.setTime_interval_currentdate( (Date) result[14]);
					list.setTime_servicedate((Date) result[15]);
					list.setTime_threshold((Integer) result[16]);
					list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
					list.setTime_thresholdperiodId((short) result[17]);
					list.setTime_servicethreshold_date((Date) result[18]);
					list.setService_emailnotification((String) result[19]);
					list.setService_subscribeduser((String) result[20]);
					list.setService_subscribeduser_name((String) result[21]);
					list.setLast_servicedate((Date) result[22]);
					list.setLast_servicecompleldid((Long) result[23]);
					list.setService_remider_howtimes((Integer) result[24]);
					list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[25]));
					list.setServiceStatusId((short) result[25]);
					list.setVehicleGroupId((long) result[26]);
					list.setService_Number((Long) result[27]);
					list.setServiceProgram((String) result[28]);
					list.setVehicle_currentOdometer((Integer) result[29]);
					list.setSkip((boolean) result[30]);
					
					Dtos.add(list);
				}
			}
			return Dtos;
		}
		
		@Override
		public List<ServiceReminderDto> DueSoonServiceRemnderGByVid(Date toDate, CustomUserDetails userDetails)
				throws Exception {

			try {
				TypedQuery<Object[]> query =	null;
				if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
					query = entityManager
							.createQuery("SELECT v.vid, T.vehicle_registration, COUNT(v.service_id) , COUNT(distinct v.serviceProgramId) "
									+ " FROM ServiceReminder v "
									+ " INNER JOIN Vehicle T ON T.vid = v.vid "
									+ " where  ( ( v.time_servicethreshold_date <= '"+toDate+"' AND  v.time_servicedate >= '"+toDate+"' AND v.time_interval > 0 )"
									+ " OR ( v.meter_servicethreshold_odometer <= v.vehicle_currentOdometer AND "
									+ " v.meter_serviceodometer >= v.vehicle_currentOdometer AND v.meter_interval > 0  )) AND "
									+ " v.companyId = "+userDetails.getCompany_id()+" AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") AND v.markForDelete = 0 "
									+ " AND v.time_servicedate > '"+toDate+"' and v.meter_serviceodometer > T.vehicle_Odometer"
									+ " group by v.vid", Object[].class);
				
				}else {
					
					query = entityManager
							.createQuery("SELECT v.vid, T.vehicle_registration, COUNT(v.service_id) , COUNT(distinct v.serviceProgramId) "
									+ " FROM ServiceReminder v "
									+ " INNER JOIN Vehicle T ON T.vid = v.vid "
									+ " where  ( ( v.time_servicethreshold_date <= '"+toDate+"' AND  v.time_servicedate >= '"+toDate+"' AND v.time_interval > 0 )"
									+ " OR ( v.meter_servicethreshold_odometer <= v.vehicle_currentOdometer AND "
									+ " v.meter_serviceodometer >= v.vehicle_currentOdometer AND v.meter_interval > 0  )) AND "
									+ " v.companyId = "+userDetails.getCompany_id()+" AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") AND v.markForDelete = 0"
									+ " AND v.time_servicedate > '"+toDate+"' and v.meter_serviceodometer > T.vehicle_Odometer group by v.vid", Object[].class);
					
				}
				
				List<Object[]> results = query.getResultList();

				List<ServiceReminderDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<ServiceReminderDto>();
					ServiceReminderDto list = null;
					for (Object[] result : results) {
						list	= new ServiceReminderDto();
						list.setVid((Integer) result[0]);
						list.setVehicle_registration((String) result[1]);
						list.setService_id((Long) result[2]);
						list.setServiceProgramId((Long) result[3]);
						
						Dtos.add(list);
						
					}
				}
					return Dtos;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public ValueObject getDueSoonReminderOfVehicle(ValueObject object) throws Exception {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			List<ServiceReminderDto>	list = SRBL.prepareListofServiceReminderAjax(listDueSoonReminderByVid(object.getInt("vid"), userDetails));
			
			if(list != null && !list.isEmpty()) {
				  Map<String, List<ServiceReminderDto>>	scheduleHashMap	=	list.stream().collect(Collectors.groupingBy(ServiceReminderDto :: getServiceProgram));
				  object.put("reminderHM", scheduleHashMap);
			}
			
			object.put("serviceReminderList", list);
			
			return object;
		}
		
		@Override
		public List<ServiceReminderDto> listDueSoonReminderByVid(Integer vid, CustomUserDetails userDetails)
				throws Exception {
			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
			TypedQuery<Object[]> query =	null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT"
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
								+ ",v.service_subscribeduser_name, v.last_servicedate,  v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, v.service_Number, VP.programName, T.vehicle_Odometer , v.isSkip "
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " INNER JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " INNER JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId "
								+ " LEFT JOIN VehicleServiceProgram VP ON VP.vehicleServiceProgramId = v.serviceProgramId"
								+ " where v.vid ="+vid+" and ( ( v.time_servicethreshold_date <= '"+toDate+"' AND  v.time_servicedate >= '"+toDate+"' AND v.time_interval > 0 )"
								+ " OR ( v.meter_servicethreshold_odometer <= v.vehicle_currentOdometer AND "
								+ " v.meter_serviceodometer >= v.vehicle_currentOdometer AND v.meter_interval > 0  )) AND "
								+ " v.companyId = "+userDetails.getCompany_id()+" AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") AND v.markForDelete = 0 "
								+ " AND v.time_servicedate > '"+toDate+"' and v.meter_serviceodometer > T.vehicle_Odometer ", Object[].class);
			}else {
				
				query = entityManager
						.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT "
								+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer"
								+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
								+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId"
								+ ",v.service_subscribeduser_name, v.last_servicedate, v.last_servicecompleldid, v.service_remider_howtimes"
								+ " , v.serviceStatusId, v.vehicleGroupId, v.service_Number, VP.programName, T.vehicle_Odometer, v.isSkip"
								+ " FROM ServiceReminder v "
								+ " INNER JOIN Vehicle T ON T.vid = v.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
								+ " INNER JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
								+ " INNER JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
								+ " LEFT JOIN VehicleServiceProgram VP ON VP.vehicleServiceProgramId = v.serviceProgramId"
								+  " where  v.vid ="+vid+" and ( ( v.time_servicethreshold_date <= '"+toDate+"' AND  v.time_servicedate >= '"+toDate+"' AND v.time_interval > 0 )"
								+ " OR ( v.meter_servicethreshold_odometer <= v.vehicle_currentOdometer AND "
								+ " v.meter_serviceodometer >= v.vehicle_currentOdometer AND v.meter_interval > 0  )) AND "
								+ " v.companyId = "+userDetails.getCompany_id()+" AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") AND v.markForDelete = 0 "
								+ " AND v.time_servicedate > '"+toDate+"' and v.meter_serviceodometer > T.vehicle_Odometer ", Object[].class);
			}
			
			List<Object[]> results = query.getResultList();

			List<ServiceReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((Long) result[0]);
					list.setVid((Integer) result[1]);
					list.setVehicle_registration((String) result[2]);
					list.setVehicle_Group((String) result[3]);
					list.setService_type((String) result[4]);
					if(result[5] != null) {
						list.setService_subtype((String) result[5]+" - "+(String) result[6]);
					}else {
						list.setService_subtype((String) result[6]);
					}
					list.setMeter_interval((Integer) result[7]);
					list.setVehicle_currentOdometer((Integer) result[8]);
					list.setMeter_serviceodometer((Integer) result[9]);
					list.setMeter_threshold((Integer) result[10]);
					list.setMeter_servicethreshold_odometer((Integer) result[11]);
					list.setTime_interval((Integer) result[12]);
					list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
					list.setTime_intervalperiodId((short) result[13]);
					list.setTime_interval_currentdate( (Date) result[14]);
					list.setTime_servicedate((Date) result[15]);
					list.setTime_threshold((Integer) result[16]);
					list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
					list.setTime_thresholdperiodId((short) result[17]);
					list.setTime_servicethreshold_date((Date) result[18]);
					list.setService_emailnotification((String) result[19]);
					list.setService_subscribeduser((String) result[20]);
					list.setService_subscribeduser_name((String) result[21]);
					list.setLast_servicedate((Date) result[22]);
					list.setLast_servicecompleldid((Long) result[23]);
					list.setService_remider_howtimes((Integer) result[24]);
					list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[25]));
					list.setServiceStatusId((short) result[25]);
					list.setVehicleGroupId((long) result[26]);
					list.setService_Number((Long) result[27]);
					list.setServiceProgram((String) result[28]);
					list.setVehicle_currentOdometer((Integer) result[29]);
					list.setSkip((boolean) result[30]);				
					Dtos.add(list);
				}
			}
			return Dtos;
		}
		
		public ValueObject skipSrWithRemark(ValueObject object) throws Exception {

			CustomUserDetails userDetails = null;
			ServiceReminder service = null;
			List<EmailAlertQueue>  				emailPendingList		= null;
			List<SmsAlertQueue>  				smsPendingList			= null;
			try {
				userDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				long serviceId = object.getLong("skipSrId",0);
				if(serviceId > 0) {
					service=getServiceReminderbyId(serviceId, userDetails.getCompany_id());
					service.setSkipRemark(object.getString("skipRemark",""));
					srReschedule(service, userDetails);
					
					emailPendingList	= emailAlertQueueService.getAllEmailAlertQueueDetailsById(serviceId);
					emailAlertQueueService.deleteEmailAlertQueue(serviceId);
					dealearService.serviceReminderEmailAlert(emailPendingList,service);
					smsPendingList	= smsAlertQueueService.getAllSmsAlertQueueDetailsById(serviceId);
					smsAlertQueueService.deleteSmsAlertQueue(serviceId);
					dealearService.serviceReminderSmsAlert(smsPendingList,service);
					
					object.put("skip", true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return object;
		}
		
		@Transactional
		public void srReschedule(ServiceReminder serviceToSave,
				CustomUserDetails userDetails) throws Exception {

			ServiceReminder service = new ServiceReminder();
			service.setService_id(serviceToSave.getService_id());
			service.setService_Number(serviceToSave.getService_Number());
			service.setCompanyId(userDetails.getCompany_id());
			service.setServiceTypeId(serviceToSave.getServiceTypeId());
			service.setServiceSubTypeId(serviceToSave.getServiceSubTypeId());
			service.setVid(serviceToSave.getVid());
			service.setVehicleGroupId(serviceToSave.getVehicleGroupId());

			service.setMeter_interval(serviceToSave.getMeter_interval());
			service.setTime_interval(serviceToSave.getTime_interval());
			service.setTime_intervalperiodId(serviceToSave.getTime_intervalperiodId());

			service.setMeter_threshold(serviceToSave.getMeter_threshold());
			service.setServiceType(serviceToSave.getServiceType());

			service.setTime_threshold(serviceToSave.getTime_threshold());
			service.setTime_thresholdperiodId(serviceToSave.getTime_thresholdperiodId());

			service.setService_subscribeduser_name(serviceToSave.getService_subscribeduser_name());

			service.setCreated(serviceToSave.getCreated());

			Integer currentOdometer = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(serviceToSave.getVid(),userDetails.getCompany_id());
			service.setVehicle_currentOdometer(currentOdometer);
			Integer meterInterval = serviceToSave.getMeter_interval();

			if (currentOdometer == null) {
				currentOdometer = 0;
			}
			if (meterInterval == null) {
				meterInterval = 0;
			}
			Integer serviceOdometer = currentOdometer + meterInterval;
			service.setMeter_serviceodometer(serviceOdometer);

			if (serviceToSave.getMeter_threshold() != null) {

				Integer meterThreshold = serviceToSave.getMeter_threshold();
				if (serviceOdometer == 0) {
					meterThreshold = 0;
				}

				Integer serviceOdometerThreshold = serviceOdometer - meterThreshold;
				service.setMeter_servicethreshold_odometer(serviceOdometerThreshold);
			}

			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
			service.setTime_interval_currentdate(toDate);
			if (serviceToSave.getTime_interval() != null) {
				Integer timeIntervalperiod = 0;
				if (serviceToSave.getTime_intervalperiodId() >= 0) {
					timeIntervalperiod = serviceToSave.getTime_interval();
				}
				Integer timeserviceDaysPeriod = 0;
				switch (serviceToSave.getTime_intervalperiodId()) {
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
					timeserviceDaysPeriod = timeIntervalperiod;
					break;
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
					timeserviceDaysPeriod = timeIntervalperiod * 7;
					break;
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
					timeserviceDaysPeriod = timeIntervalperiod * 30;
					break;
				case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
					timeserviceDaysPeriod = timeIntervalperiod * 365;
					break;

				default:
					timeserviceDaysPeriod = timeIntervalperiod;
					break;
				}

				final Calendar calendar = Calendar.getInstance();
				calendar.setTime(toDate);
				calendar.add(Calendar.DAY_OF_YEAR, timeserviceDaysPeriod);
				calendar.set(Calendar.HOUR_OF_DAY, 0);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				service.setTime_servicedate(calendar.getTime());

				if (serviceToSave.getTime_threshold() != null) {

					Integer timeThreshold = 0;
					switch (serviceToSave.getTime_thresholdperiodId()) {
					case ServiceReminderDto.TIME_INTERVAL_PERIOD_DAYS:
						timeThreshold = serviceToSave.getTime_threshold();
						break;
					case ServiceReminderDto.TIME_INTERVAL_PERIOD_WEEKS:
						timeThreshold = serviceToSave.getTime_threshold() * 7;
						break;
					case ServiceReminderDto.TIME_INTERVAL_PERIOD_MONTHS:
						timeThreshold = serviceToSave.getTime_threshold() * 30;
						break;
					case ServiceReminderDto.TIME_INTERVAL_PERIOD_YEARS:
						timeThreshold = serviceToSave.getTime_threshold() * 365;
						break;

					default:
						timeThreshold = serviceToSave.getTime_threshold();
						break;
					}
					final Calendar calendarAdvanceThreshold = Calendar.getInstance();
					calendarAdvanceThreshold.setTime(calendar.getTime());
					calendarAdvanceThreshold.add(Calendar.DAY_OF_YEAR, -timeThreshold);
					
					service.setTime_servicethreshold_date(calendarAdvanceThreshold.getTime());

				}
			}
			Integer serviceHowtimes = serviceToSave.getService_remider_howtimes() + 1;
			service.setService_remider_howtimes(serviceHowtimes);
			service.setLastModifiedById(userDetails.getId());
			service.setLastupdated(toDate);
			service.setLast_servicecompleldid(serviceToSave.getWorkorders_id());
			service.setLast_servicedate(toDate);
			service.setWorkorders_id(serviceToSave.getWorkorders_id());
			service.setServiceStatusId(serviceToSave.getServiceStatusId());
			service.setDealerServiceEntriesId(serviceToSave.getDealerServiceEntriesId());
			service.setServiceProgramId(serviceToSave.getServiceProgramId());
			service.setServiceScheduleId(serviceToSave.getServiceScheduleId());
			service.setServiceType(serviceToSave.getServiceType());
			service.setSkipRemark(serviceToSave.getSkipRemark());
			service.setSkip(true);
			
			updateServiceReminder(service);
			
			
		}
		
		@Transactional(readOnly=true)
		public ServiceReminder getServiceReminderbyId(Long serviceId ,Integer companyId) {
			
			return serviceReminderDao.getServiceReminder(serviceId, companyId);
		}
		
		@Override
		public Map<String, Object> getMultiPrintDataForServiceReminder(Integer vehicleId, String serviceProgramIds,Integer companyId) {
			
			Map<String, Object> map = new HashMap<>();
			try {
				CustomUserDetails userDetails = Utility.getObject(null);
				List<ServiceReminderDto> ServiceProgramList = getServiceProgramListByVidProgramId(vehicleId,serviceProgramIds, userDetails.getCompany_id());
				if (ServiceProgramList != null) {
					map.put("vehicleNumber", ServiceProgramList.get(0).getVehicle_registration());
					map.put("vehicleType", ServiceProgramList.get(0).getVehicleType());
					map.put("currentOdometer", ServiceProgramList.get(0).getVehicle_currentOdometer());
					map.put("branch", ServiceProgramList.get(0).getSRBranchName());
				}
				map.put("ServiceProgramList", ServiceProgramList);
				map.put("companyId", userDetails.getCompany_id());
				UserProfileDto company = userProfileService.findUserProfileByUser_email(userDetails.getEmail());
				map.put("company", company);
				Timestamp date = DateTimeUtility.getCurrentTimeStamp();
				map.put("date", DateTimeUtility.getDateFromTimeStampWithAMPM(date));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			return map;
		}
		
		@Override
		public ValueObject checkServiceReminderForJobTypeAndSubType(ValueObject object) {
			
			StringBuilder warning = new StringBuilder();
			Boolean  Due = null;
			ServiceReminderDto dto = null;
			List<ServiceReminderDto> serviceReminderDtoList = new ArrayList<ServiceReminderDto>();
			try {
				TypedQuery<Object[]> query =  entityManager.createQuery("SELECT SR.service_id, SR.service_Number,"
						+ " SR.serviceTypeId, SR.serviceSubTypeId, SR.vid,"
						+ " SR.time_servicedate, SR.time_interval, SR.vehicle_currentOdometer, "
						+ " SR.meter_serviceodometer, SR.meter_servicethreshold_odometer, SR.time_servicethreshold_date, "
						+ " SR.meter_interval, SR.meter_threshold , J.Job_Type, JS.Job_ROT , SR.serviceTypeId, "
						+ " SR.serviceSubTypeId"
						+ " FROM ServiceReminder  AS SR "
						+ " INNER JOIN JobType AS J ON J.Job_id = SR.serviceTypeId "
						+ " INNER JOIN JobSubType AS JS ON JS.Job_Subid = SR.serviceSubTypeId "
						+ " WHERE SR.vid = "+ object.getInt("vid") + " AND SR.companyId = "+ object.getInt("companyId")
						+ " AND SR.serviceTypeId = "+ object.getString("jobType") + " AND SR.serviceSubTypeId IN ("+ object.getString("jobsubType") +")"
						+ " AND SR.markForDelete = 0",
						Object[].class);
				
				List<Object[]> results = query.getResultList();
				if (results != null && !results.isEmpty()) {
					for (Object[] result : results) {
						dto = new ServiceReminderDto();
					    Due = false;
						dto.setService_id((Long) result[0]);
						dto.setService_Number((Long) result[1]);
						dto.setTime_servicedate((Date)result[5]);
						dto.setTime_interval((Integer) result[6]);
						dto.setVehicle_currentOdometer((Integer) result[7]);
						dto.setMeter_serviceodometer((Integer) result[8]);
						dto.setMeter_servicethreshold_odometer((Integer)result[9]);
						dto.setTime_servicethreshold_date((Date)result[10]);
						dto.setMeter_interval((Integer) result[11]);
						dto.setMeter_threshold((Integer) result[12]);
						dto.setServiceIdsWithSubIds(result[16]+"_"+result[0]);
						dto.setServiceSubTypeId((Integer)result[16]);
						
					   java.util.Date currentDate = new java.util.Date();
					   Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
					
						if(dto.getTime_servicedate() != null) {
							if(dto.getTime_interval() >0 ) {
								dto.setDiffrent_time_days(RRBL.time_DateDifferentAjax(dto.getTime_servicedate(),toDate));
							}
							else {
								dto.setDiffrent_time_days("");
							}
						}
						Integer vehicleCurrentOdometer = 0;
						if(dto.getVehicle_currentOdometer() != null) {
							vehicleCurrentOdometer = dto.getVehicle_currentOdometer();
						}
						Integer Current_vehicleOdmeter = vehicleCurrentOdometer;
						if (dto.getMeter_servicethreshold_odometer() != null && dto.getMeter_interval() > 0) {
							Integer currentThresholdDiff = Current_vehicleOdmeter
									- dto.getMeter_servicethreshold_odometer();
							Integer meter_Threshold = 0;
							if (dto.getMeter_threshold() != null) {
								meter_Threshold = dto.getMeter_threshold();
							}
							switch (currentThresholdDiff) {
								case 1:
									Due = true;
									break;
	
								default:
									if (currentThresholdDiff > 1) {
										if (currentThresholdDiff < meter_Threshold) {
											Due  = true;
										} else if (currentThresholdDiff == meter_Threshold) {
											Due = true;
										} else {
											Due = true;
										}
	
									}
									break;
							}
						}
						if (dto.getTime_servicethreshold_date() != null) {
							int diffInDays_threshold = (int) ((toDate.getTime()
									- dto.getTime_servicethreshold_date().getTime())
									/ (1000 * 60 * 60 * 24));
							switch (diffInDays_threshold) {
								case 1:
									Due = true;
									break;
								default:
									if (diffInDays_threshold > 1) {
	
										int diffInTimeServicethreshold = (int) ((dto.getTime_servicedate()
												.getTime() - dto.getTime_servicethreshold_date().getTime())
												/ (1000 * 60 * 60 * 24));
	
										if (diffInDays_threshold < diffInTimeServicethreshold) {
											Due = true;
										} else if (diffInDays_threshold == diffInTimeServicethreshold) {
											Due = true;
										} else {
											Due = true;
										}
									}
									break;
							}
						}
						
						if(!Due) { 
							warning.append(result[14] + ", ");
						}
						
						serviceReminderDtoList.add(dto);
					}
					object.put("serviceReminderDtoList", serviceReminderDtoList);
				}
			}catch(Exception e) {
				
			}
			if(!warning.toString().isEmpty()) {
				try {
					object.put("Warning",  warning.append(" Not In Due Status. Do you want to continue ??"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return object;
		}

		@Override
		public ValueObject getServiceReminderHistory(ValueObject object) throws Exception {
			List<ServiceReminderHistoryDto> srHistoryList = null;
			
			try {
				CustomUserDetails userDetails = Utility.getObject(null);
				WorkOrders    work = null;
				ServiceReminderHistoryDto HistoryDto = null;
				ServiceEntries serviceEntries  = null;
				DealerServiceEntries dealerServiceEntries = null;
				
				Long serviceId = object.getLong("serviceReminderId");
				
				ServiceReminder sr = serviceReminderDao.getServiceReminder(serviceId, userDetails.getCompany_id());
				
				List<ServiceReminderHistory> resultList =  srHistoryRepository.getlistServiceReminderHistory(serviceId, userDetails.getCompany_id());
				
				srHistoryList = new ArrayList<ServiceReminderHistoryDto>();
				
				for(ServiceReminderHistory history : resultList) {
					
					HistoryDto = new ServiceReminderHistoryDto();
					if(history.getServiceType()==WorkOrdersType.SERVICE_REMINDER_WO) {
						
						work = workOrdersRepository.getWorkOrders(history.getWorkorders_id(),userDetails.getCompany_id());
						HistoryDto.setCompleted_Date(sqlDateFormat2.format(work.getCompleted_date()));
					}else if(history.getServiceType()==WorkOrdersType.SERVICE_REMINDER_SE) {
						
						serviceEntries = serviceEntriesRepository.getServiceEntries(history.getServiceEntries_id(), userDetails.getCompany_id());
						HistoryDto.setCompleted_Date(sqlDateFormat2.format(serviceEntries.getCompleted_date()));
					}else if(history.getServiceType()==WorkOrdersType.SERVICE_REMINDER_DSE) {
						
						dealerServiceEntries = dealerServiceEntriesRepository.getDealerServiceEntriesById(history.getDealerServiceEntriesId() , userDetails.getCompany_id());
						HistoryDto.setCompleted_Date(sqlDateFormat2.format(dealerServiceEntries.getCompletedDate()));
					}
					
					HistoryDto.setDue_Date(sqlDateFormat2.format(history.getServiceDueDate()));
					
					HistoryDto.setCompleted_odometer(sr.getVehicle_currentOdometer());
					HistoryDto.setDue_odometer(sr.getMeter_serviceodometer());
					
					
					String serviceType = null;
					
					if(history.getServiceType() == WorkOrdersType.SERVICE_REMINDER_WO) {
						serviceType  = "<a href='showWorkOrder?woId="+work.getWorkorders_id()+"'> WORK ORDER-" + work.getWorkorders_Number() + "</a>";
						HistoryDto.setService_Type(serviceType);
					}else if(history.getServiceType() == WorkOrdersType.SERVICE_REMINDER_SE) {
						serviceType  = "<a href='showServiceEntryDetails?serviceEntryId="+ serviceEntries.getServiceEntries_id()+"'> SE-" + serviceEntries.getServiceEntries_Number() + "</a>";
						HistoryDto.setService_Type(serviceType);
					}else if(history.getServiceType() == WorkOrdersType.SERVICE_REMINDER_DSE) {
						serviceType  = "<a href='showDealerServiceEntries?dealerServiceEntriesId="+ dealerServiceEntries.getDealerServiceEntriesId()+"'> DSE-" + dealerServiceEntries.getDealerServiceEntriesNumber() + "</a>";
						HistoryDto.setService_Type(serviceType);
					}
					
					if(history.getServiceType() == WorkOrdersType.SERVICE_REMINDER_WO) {
					
						if(work.getCompleted_date().before(history.getServiceDueDate())) {
							long diffdaysmills  = history.getServiceDueDate().getTime() -work.getCompleted_date().getTime();
							long  diffdays = diffdaysmills/(1000*60*60*24);
							HistoryDto.setCompliance("ON TIME");
							HistoryDto.setDate_compliances( diffdays+ " days behind");
						}else {
							long diffdaysmills  = history.getServiceDueDate().getTime() - work.getCompleted_date().getTime();
							long  diffdays = diffdaysmills/(1000*60*60*24);
							HistoryDto.setDate_compliances( diffdays+ " days Ahead");
							HistoryDto.setCompliance("LATE");
						}
					}else if(history.getServiceType() == WorkOrdersType.SERVICE_REMINDER_SE) {
						
						if(serviceEntries.getCompleted_date().before(history.getServiceDueDate())) {
							long diffdaysmills  = history.getServiceDueDate().getTime() -serviceEntries.getCompleted_date().getTime();
							long  diffdays = diffdaysmills/(1000*60*60*24);
							HistoryDto.setCompliance("ON TIME");
							HistoryDto.setDate_compliances( diffdays+ " days behind");
						}else {
							long diffdaysmills  = history.getServiceDueDate().getTime() - serviceEntries.getCompleted_date().getTime();
							long  diffdays = diffdaysmills/(1000*60*60*24);
							HistoryDto.setDate_compliances( diffdays+ " days Ahead");
							HistoryDto.setCompliance("LATE");
						}
					}else if(history.getServiceType() == WorkOrdersType.SERVICE_REMINDER_DSE) {
						
						if(dealerServiceEntries.getCompletedDate().before(history.getServiceDueDate())) {
							System.err.println("inside if ");
							long diffdaysmills  = history.getServiceDueDate().getTime() -dealerServiceEntries.getCompletedDate().getTime();
							long  diffdays = diffdaysmills/(1000*60*60*24);
							HistoryDto.setCompliance("ON TIME");
							HistoryDto.setDate_compliances( diffdays+ " days behind");
						}else {
							long diffdaysmills  = history.getServiceDueDate().getTime() - dealerServiceEntries.getCompletedDate().getTime();
							long  diffdays = diffdaysmills/(1000*60*60*24);
							HistoryDto.setDate_compliances( diffdays+ " days Ahead");
							HistoryDto.setCompliance("LATE");
						}
					}
					
					// odometer
					Integer odometer_diff = sr.getMeter_serviceodometer() - sr.getVehicle_currentOdometer();
					if(odometer_diff < 0) {
						HistoryDto.setOdometer_compliances(odometer_diff + " Km Ahead");
					}else {
						HistoryDto.setOdometer_compliances(odometer_diff + " Km behind");
					}
					
					srHistoryList.add(HistoryDto);
				}
				
			}catch(Exception e) {
				
			}
			object.put("srHistoryList", srHistoryList);
			return object;
		}
		
		@Override
		public Page<ServiceReminder> geServiceReminderUpcomingOverDuePage(Timestamp fromDate, Timestamp toDate, Integer pageNumber, CustomUserDetails userDetails)throws Exception {
			
			@SuppressWarnings("deprecation")
			Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
			try {
				boolean	vehicleGWisePermission	= companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				
					if(!vehicleGWisePermission) {
						return serviceReminderDao.getServiceReminderUpcomingOverduePage(fromDate , toDate, userDetails.getCompany_id(), pageable);
					}
					
					return serviceReminderDao.getServiceReminderUpcomingOverduePage(fromDate,toDate, userDetails.getId(),userDetails.getCompany_id(), pageable);
				
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public Page<ServiceReminder> geServiceReminderUpcomingOverDuePageGByVid(Timestamp fromDate,Timestamp toDate, Integer pageNumber,
				CustomUserDetails userDetails) throws Exception {
			
			@SuppressWarnings("deprecation")
			Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE_15);
			try {

					boolean	vehicleGWisePermission	= companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				
					if(!vehicleGWisePermission) {
						return serviceReminderDao.getServiceReminderUpcomingOverduePageGByVid(fromDate ,toDate, userDetails.getCompany_id(), pageable);
					}
					
					return serviceReminderDao.getServiceReminderUpcomingOverduePageGByVid(fromDate,toDate, userDetails.getId(),userDetails.getCompany_id(), pageable);
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Transactional
		public List<ServiceReminderDto> UpcomingOverDueServiceRemnder(Date fromDate, Date toDate, CustomUserDetails userDetails, Integer pageNumber) throws Exception {
			try {
				TypedQuery<Object[]> query =	null;
				if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			
					query = entityManager.
							createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT  " 
						    + " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
						    + " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
						    + ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
						    + ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
						    + " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number"
						    + " FROM ServiceReminder v "
						    + " INNER JOIN Vehicle T ON T.vid = v.vid "
						    + " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
						    + " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
						    + " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
						    + " LEFT JOIN User U ON U.id = v.createdById"
						    + " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
						    + " WHERE v.companyId = " + userDetails.getCompany_id()
						    + " AND v.markForDelete = 0 "
						    + " AND ((v.time_servicedate BETWEEN '" + fromDate + "' AND '" + toDate + "') AND v.time_interval > 0"
						    + " AND v.meter_interval > 0"
						    + " AND T.vStatusId IN (" + VehicleStatusConstant.VEHICLE_STATUS_ACTIVE + ","
						    + VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE + "," + VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP + "))", Object[].class);
				}else {
					
					query = entityManager
							.createQuery("SELECT v.service_id, v.vid, T.vehicle_registration, VG.vGroup, JT.Job_Type, JST.Job_ROT_number, JST.Job_ROT  " 
									+ " , v.meter_interval, v.vehicle_currentOdometer, v.meter_serviceodometer, v.meter_threshold, v.meter_servicethreshold_odometer" 
									+ " , v.time_interval, v.time_intervalperiodId, v.time_interval_currentdate, v.time_servicedate, v.time_threshold"
									+ ", v.time_thresholdperiodId, v.time_servicethreshold_date, v.service_emailnotification, v.service_subScribedUserId" 
									+ ",v.service_subscribeduser_name, v.last_servicedate, U2.email, v.last_servicecompleldid, v.service_remider_howtimes"
									+ " , v.serviceStatusId, v.vehicleGroupId, U.email, v.service_Number"
									+ " FROM ServiceReminder v "
									+ " INNER JOIN Vehicle T ON T.vid = v.vid "
									+ " INNER JOIN VehicleGroup VG ON VG.gid = v.vehicleGroupId"
									+ " LEFT JOIN JobType JT ON JT.Job_id = v.serviceTypeId"
									+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = v.serviceSubTypeId"
									+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+" "
									+ " LEFT JOIN User U ON U.id = v.createdById"
									+ " LEFT JOIN User U2 ON U2.id = v.last_servicecompleldbyId"
									+ " where v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
									+ " AND ( ( (v.time_servicedate BETWEEN '" + fromDate + "' AND '" + toDate + "') AND v.time_interval > 0) OR ( v.meter_serviceodometer <= v.vehicle_currentOdometer AND v.meter_interval > 0)"
									+ "AND T.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+") )", Object[].class);
					
				}
				query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
				query.setMaxResults(PAGE_SIZE);
				
				List<Object[]> results = query.getResultList();

				List<ServiceReminderDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<ServiceReminderDto>();
					ServiceReminderDto list = null;
					for (Object[] result : results) {
						list = new ServiceReminderDto();
						
						list.setService_id((Long) result[0]);
						list.setVid((Integer) result[1]);
						list.setVehicle_registration((String) result[2]);
						list.setVehicle_Group((String) result[3]);
						list.setService_type((String) result[4]);
						if(result[5] != null) {
							list.setService_subtype((String) result[5]+" - "+(String) result[6]);
						}else {
							list.setService_subtype((String) result[6]);
						}
						list.setMeter_interval((Integer) result[7]);
						list.setVehicle_currentOdometer((Integer) result[8]);
						list.setMeter_serviceodometer((Integer) result[9]);
						list.setMeter_threshold((Integer) result[10]);
						list.setMeter_servicethreshold_odometer((Integer) result[11]);
						list.setTime_interval((Integer) result[12]);
						list.setTime_intervalperiod(ServiceReminderDto.getTimeInterValName((short) result[13]));
						list.setTime_intervalperiodId((short) result[13]);
						list.setTime_interval_currentdate( (Date) result[14]);
						list.setTime_servicedate((Date) result[15]);
						list.setTime_threshold((Integer) result[16]);
						list.setTime_thresholdperiod(ServiceReminderDto.getTimeInterValName((short) result[17]));
						list.setTime_thresholdperiodId((short) result[17]);
						list.setTime_servicethreshold_date((Date) result[18]);
						list.setService_emailnotification((String) result[19]);
						list.setService_subScribedUserId((String) result[20]);
						list.setService_subscribeduser_name((String) result[21]);
						list.setLast_servicedate((Date) result[22]);
						list.setLast_servicecompleldby((String) result[23]);
						list.setLast_servicecompleldid((Long) result[24]);
						list.setService_remider_howtimes((Integer) result[25]);
						list.setServicestatus(ServiceReminderDto.getServiceReminderStatus((short) result[26]));
						list.setServiceStatusId((short) result[26]);
						list.setVehicleGroupId((long) result[27]);
						list.setCreatedBy((String) result[28]);
						list.setService_Number((Long) result[29]);
						
						Dtos.add(list);
					}
				}
					return Dtos;
			
			} catch (Exception e) {
				throw e;
			}
		}
	
		@Override
		public List<ServiceReminderDto> UpcomingOverDueServiceRemnderGByVid(Date fromDate, Date toDate, CustomUserDetails userDetails,Integer pageNumber) throws Exception {
			try {
				TypedQuery<Object[]> query =	null;
				if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
					query = entityManager
							.createQuery("SELECT v.vid, T.vehicle_registration, COUNT(v.service_id) , COUNT(distinct v.serviceProgramId) "
									+ " FROM ServiceReminder v "
									+ " INNER JOIN Vehicle T ON T.vid = v.vid "
									+ " where v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
									+ " AND ( ((v.time_servicedate BETWEEN '" + fromDate + "' AND '" + toDate + "') AND v.time_interval > 0)  OR "
									+ " ( v.meter_serviceodometer <= v.vehicle_currentOdometer AND v.meter_interval > 0 ) "
									+ " ) group by v.vid", Object[].class);
				}else {
					query = entityManager
							.createQuery("SELECT v.vid, T.vehicle_registration, COUNT(v.service_id) , COUNT(distinct v.serviceProgramId) "
									+ " FROM ServiceReminder v "
									+ " INNER JOIN Vehicle T ON T.vid = v.vid "
									+ " where v.companyId = "+userDetails.getCompany_id()+" AND v.markForDelete = 0 "
									+ " AND ( ( (v.time_servicedate BETWEEN '" + fromDate + "' AND '" + toDate + "') AND v.time_interval > 0) OR "
									+ " ( v.meter_serviceodometer <= v.vehicle_currentOdometer AND v.meter_interval > 0) "
									+ " ) group by v.vid", Object[].class);
					
				}
				query.setFirstResult((pageNumber - 1) * PAGE_SIZE_15);
				query.setMaxResults(PAGE_SIZE_15);
				
				List<Object[]> results = query.getResultList();

				List<ServiceReminderDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<ServiceReminderDto>();
					ServiceReminderDto list = null;
					for (Object[] result : results) {
						list	= new ServiceReminderDto();
						list.setVid((Integer) result[0]);
						list.setVehicle_registration((String) result[1]);
						list.setService_id((Long) result[2]);
						list.setServiceProgramId((Long) result[3]);
						
						Dtos.add(list);
						
					}
				}
					return Dtos;
			
			} catch (Exception e) {
				throw e;
			}
		}
}
