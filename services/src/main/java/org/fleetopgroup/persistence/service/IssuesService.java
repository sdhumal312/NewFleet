/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.IssuesConfigurationContant;
import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.TyreAssignmentConstant;
import org.fleetopgroup.constant.UserAlertNOtificationsConstant;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.constant.VendorTypeConfigurationConstants;
import org.fleetopgroup.constant.WorkOrdersConfigurationConstants;
import org.fleetopgroup.mobilenotifications.NotificationBllImpl;
import org.fleetopgroup.mobilenotifications.NotificationConstant;
import org.fleetopgroup.persistence.bl.IssuesBL;
import org.fleetopgroup.persistence.bl.JobSubTypeBL;
import org.fleetopgroup.persistence.bl.PartCategoriesBL;
import org.fleetopgroup.persistence.bl.ServiceEntriesBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.dao.InspectionCompletionToParameterRepository;
import org.fleetopgroup.persistence.dao.InspectionParameterRepository;
import org.fleetopgroup.persistence.dao.IssueBreakDownDetailsRepository;
import org.fleetopgroup.persistence.dao.IssuesCommentRepository;
import org.fleetopgroup.persistence.dao.IssuesDocumentRepository;
import org.fleetopgroup.persistence.dao.IssuesPhotoRepository;
import org.fleetopgroup.persistence.dao.IssuesRepository;
import org.fleetopgroup.persistence.dao.IssuesTaskRepository;
import org.fleetopgroup.persistence.dao.MobileAppUserRegistrationRepository;
import org.fleetopgroup.persistence.dao.MobileNotificationRepository;
import org.fleetopgroup.persistence.dao.UserAlertNotificationsRepository;
import org.fleetopgroup.persistence.dao.VehicleRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.IssueAnalysisDto;
import org.fleetopgroup.persistence.dto.IssueBreakDownDetailsDto;
import org.fleetopgroup.persistence.dto.IssuesCommentDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.IssuesTasksDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleTyreLayoutPositionDto;
import org.fleetopgroup.persistence.model.InspectionCompletionToParameter;
import org.fleetopgroup.persistence.model.InspectionParameter;
import org.fleetopgroup.persistence.model.IssueBreakDownDetails;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.IssuesComment;
import org.fleetopgroup.persistence.model.IssuesDocument;
import org.fleetopgroup.persistence.model.IssuesPhoto;
import org.fleetopgroup.persistence.model.IssuesSequenceCounter;
import org.fleetopgroup.persistence.model.IssuesTasks;
import org.fleetopgroup.persistence.model.JobSubType;
import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.model.MobileNotifications;
import org.fleetopgroup.persistence.model.ServiceEntries;
import org.fleetopgroup.persistence.model.ServiceEntriesSequenceCounter;
import org.fleetopgroup.persistence.model.ServiceEntriesTasks;
import org.fleetopgroup.persistence.model.UserAlertNotifications;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.VendorSequenceCounter;
import org.fleetopgroup.persistence.model.VendorType;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDealerServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IIssueAnalysisService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IJobSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ITyreUsageHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IUserService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreAssignmentService;
import org.fleetopgroup.persistence.serviceImpl.IVendorSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.persistence.serviceImpl.IVendorTypeService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.ByteConvertor;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

/**
 * @author fleetop
 *
 */
@Service
@Transactional
public class IssuesService implements IIssuesService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private IssuesRepository issuesRepository;

	@Autowired
	private IssuesTaskRepository issuesTaskRepository;

	@Autowired
	private IssuesCommentRepository issuesCommentRepository;

	@Autowired private MongoTemplate	mongoTemplate;
	@Autowired private ISequenceCounterService	sequenceCounterService;

	@Autowired
	private ICompanyConfigurationService	companyConfigurationService;
	
	@Autowired private IssuesDocumentRepository	issuesDocumentRepository;
	
	@Autowired private IssuesPhotoRepository	issuesPhotoRepository;

	@Autowired private InspectionParameterRepository  inspectParameterRepository;

	@Autowired IDealerServiceEntriesService dseService;

	private static final int PAGE_SIZE = 10;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass()); //latest
	
	@Autowired IJobTypeService JobTypeService;   
	
	@Autowired IVehicleService vehicleService;	 
	
	@Autowired IServiceEntriesService ServiceEntriesService;		
	
	@Autowired IVendorService vendorService;		
	
	@Autowired IVendorTypeService VendorTypeService;	
	
	@Autowired IDriverService driverService;		
	
	@Autowired IWorkOrdersService WorkOrdersService;	
	
	@Autowired IServiceReminderService ServiceReminderService;	
	
	@Autowired IVehicleOdometerHistoryService vehicleOdometerHistoryService;	
	
	@Autowired IJobSubTypeService JobSubTypeService;	
	
	@Autowired IServiceEntriesSequenceService serviceEntriesSequenceService;
	
	@Autowired InspectionCompletionToParameterRepository inspectionCompletionToParameterRepository;
	
	@Autowired private IVehicleTyreAssignmentService 						vehicleTyreAssignmentService;
	
	@Autowired IUserService userService;
	
	
	@Autowired IVendorSequenceService vendorSequenceService;
	@Autowired 	private IIssuesSequenceService	issuesSequenceService;
	@Autowired private IUserProfileService userProfileService;
	@Autowired private IVehicleGroupService	vehicleGroupService;
	@Autowired private VehicleRepository	VehicleRepository;
	@Autowired MobileAppUserRegistrationRepository					mobileAppUserRegistrationRepository;
	@Autowired MobileNotificationRepository				         	mobileNotificationRepository;
	@Autowired private UserAlertNotificationsRepository		userAlertNotificationsRepository;
	@Autowired private IssueBreakDownDetailsRepository		issueBreakDownDetailsRepository;
	@Autowired IIssueAnalysisService				issueAnalysisService;
	@Autowired private ITyreUsageHistoryService				tyreUsageHistoryService;
	
	IssuesBL 			issuesBL 			= new IssuesBL();		
	ServiceEntriesBL 	serviceEntriesBL 	= new ServiceEntriesBL();		
	VendorBL 			vendorBL 			= new VendorBL();		
	JobSubTypeBL		jobSubTypeBL		= new JobSubTypeBL();
	SimpleDateFormat 	dateFormat 			= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 	format2 			= new SimpleDateFormat("yyyy-MM-dd");
	DateTimeFormatter   timeFormatter       = DateTimeFormatter.ofPattern("HH:mm");
	
	VehicleOdometerHistoryBL VOHBL = new VehicleOdometerHistoryBL();		
	PartCategoriesBL 	partCategoriesBL	= new PartCategoriesBL();
	@Autowired IPartCategoriesService partCategoriesService;
	
	@Autowired
	IIssuesService 								issuesService;			
	
	SimpleDateFormat sqldateTime = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat sqldateTimeParse = new SimpleDateFormat("dd-MM-yyyy HH:mm");
	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormatonlyDateTime = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss ");
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm:ss"); 
	SimpleDateFormat formatSQL 	 	 = new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IIssuesService#registerNew_Issues(org
	 * .fleetop.persistence.model.Issues)
	 */
	@Transactional
	public Issues registerNew_Issues(Issues IssuesDto) throws Exception {

		return issuesRepository.save(IssuesDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#findAll()
	 */
	@Transactional
	public List<Issues> findAll() {
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * find_Issues_Open_Status(java.lang.String)
	 */
	@Transactional
	public List<IssuesDto> find_Issues_Open_Status(Integer pageNumber, Long user_id, CustomUserDetails	userDetails) throws Exception {
		TypedQuery<Object[]> queryt = 	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_NUMBER, R.ISSUE_TYPE_ID, R.ISSUES_VID, V.vehicle_registration, VG.vGroup,"
							+ " R.ISSUES_ODOMETER, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name, R.ISSUES_DEPARTNMENT_ID,"
							+ " DP.depart_name, R.ISSUES_REPORTED_DATE, R.ISSUES_SUMMARY, R.ISSUES_DESCRIPTION, R.ISSUES_LABELS_ID, U.firstName,"
							+ " R.ISSUES_ASSIGN_TO_NAME, R.ISSUES_STATUS_ID, U2.email, U3.email, R.CREATED_DATE, R.LASTUPDATED_DATE, R.CREATEDBYID, R.LASTMODIFIEDBYID, R.ISSUES_ASSIGN_TO"
							+ " ,D.driver_Lastname ,D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName "
							+ " FROM Issues AS R "
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN User U ON U.id = R.ISSUES_REPORTED_BY_ID"
							+ " LEFT JOIN User U2 ON U2.id = R.CREATEDBYID"
							+ " LEFT JOIN User U3 ON U3.id = R.LASTMODIFIEDBYID"
							+ " LEFT JOIN Driver D ON D.driver_id = ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where R.ISSUES_STATUS_ID="+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ORDER BY R.ISSUES_ID desc",
					Object[].class);
		}else {
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_NUMBER, R.ISSUE_TYPE_ID, R.ISSUES_VID, V.vehicle_registration, VG.vGroup,"
					+ " R.ISSUES_ODOMETER, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name, R.ISSUES_DEPARTNMENT_ID,"
					+ " DP.depart_name, R.ISSUES_REPORTED_DATE, R.ISSUES_SUMMARY, R.ISSUES_DESCRIPTION, R.ISSUES_LABELS_ID, U.firstName,"
					+ " R.ISSUES_ASSIGN_TO_NAME, R.ISSUES_STATUS_ID, U2.email, U3.email, R.CREATED_DATE, R.LASTUPDATED_DATE, R.CREATEDBYID, R.LASTMODIFIEDBYID, R.ISSUES_ASSIGN_TO"
					+ " ,D.driver_Lastname ,D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName "
					+ " FROM Issues AS R"
					+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
					+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
					+ " LEFT JOIN User U ON U.id = R.ISSUES_REPORTED_BY_ID"
					+ " LEFT JOIN User U2 ON U2.id = R.CREATEDBYID"
					+ " LEFT JOIN User U3 ON U3.id = R.LASTMODIFIEDBYID"
					+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
					+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
					+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
					+ " LEFT JOIN Driver D ON D.driver_id = ISSUES_DRIVER_ID"
					+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
					+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
					+ " Where R.ISSUES_STATUS_ID="+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ORDER BY R.ISSUES_ID desc",
							Object[].class);
		}
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		
		List<Object[]> results = queryt.getResultList();

		List<IssuesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();
				
				list.setISSUES_ID((long) result[0]);
				list.setISSUES_NUMBER((long) result[1]);
				list.setISSUES_TYPE_ID((short) result[2]);
				list.setISSUES_VID((Integer) result[3]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[4]);
				list.setISSUES_VEHICLE_GROUP((String) result[5]);
				list.setISSUES_ODOMETER((Integer) result[6]);
				list.setISSUES_DRIVER_ID((Integer) result[7]);
				list.setISSUES_DRIVER_NAME((String) result[8]);
				list.setISSUES_BRANCH_ID((Integer) result[9]);
				list.setISSUES_BRANCH_NAME((String) result[10]);
				list.setISSUES_DEPARTNMENT_ID((Integer) result[11]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[12]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[13]);
				list.setISSUES_SUMMARY((String) result[14]);
				list.setISSUES_DESCRIPTION((String) result[15]);
				list.setISSUES_LABELS_ID((short) result[16]);
				list.setISSUES_REPORTED_BY((String) result[17]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[18]);
				list.setISSUES_STATUS_ID((short) result[19]);
				list.setCREATEDBY((String) result[20]);
				list.setLASTMODIFIEDBY((String) result[21]);
				list.setCREATED_DATE_ON((Date) result[22]);
				list.setLASTUPDATED_DATE_ON((Date) result[23]);
				list.setCREATEDBYID((Long) result[24]);
				list.setLASTMODIFIEDBYID((Long) result[25]);
				list.setISSUES_ASSIGN_TO((String) result[26]);
				if(result[27] != null &&  !((String)result[27]).trim().equals(""))
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[27]);
				if(result[28] != null && !((String)result[28]).trim().equals(""))
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[28]);
				
				list.setVehicleType((String) result[29]);
				
				list.setVehicleMaker((String) result[30]);
				Dtos.add(list);
			}
		}
		return Dtos;


	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * find_Issues_Open_Status(java.lang.String)
	 */
	@Transactional
	public List<IssuesDto> find_Issues_Overdue_Status(Integer pageNumber, CustomUserDetails	userDetails, String date) throws Exception {
		
		TypedQuery<Object[]> queryt = null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER ,D.driver_Lastname "
							+ ",D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID" 
							+ " Where R.ISSUES_REPORTED_DATE <= '" + DateTimeUtility.getCurrentTimeStamp()+"' AND R.ISSUES_STATUS_ID="+IssuesTypeConstant.ISSUES_STATUS_OPEN+" "
							+ " AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ORDER BY R.ISSUES_ID desc",
					Object[].class);
		}else {
			
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER ,D.driver_Lastname "
							+ ",D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " Where  R.ISSUES_REPORTED_DATE <= '" + date + "' AND R.ISSUES_STATUS_ID="+IssuesTypeConstant.ISSUES_STATUS_OPEN+" "
							+ " AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ORDER BY R.ISSUES_ID desc",
							Object[].class);
		}

		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		
		List<IssuesDto> Dtos = null;
		Dtos = new ArrayList<IssuesDto>();
		List<Object[]> results = queryt.getResultList();
		
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setCREATED_DATE_ON((Date) result[14]);
				list.setLASTUPDATED_DATE_ON((Date) result[15]);
				list.setISSUES_STATUS_ID((short) result[16]);
				list.setISSUES_NUMBER((Long) result[17]);
				if(result[18] != null && !((String)result[18]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[18]);
					if(result[19] != null && !((String)result[19]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[19]);
					
					list.setVehicleType((String) result[20]);
					
					list.setVehicleMaker((String) result[21]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * find_Issues_Open_Status(java.lang.String)
	 */
	@Transactional
	public List<IssuesDto> find_Issues_Resolved_Status(Integer pageNumber, CustomUserDetails userDetails) throws Exception {
	
		TypedQuery<Object[]> queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,R.ISSUES_WORKORDER_ID,R.ISSUES_SE_ID,D.driver_Lastname "
							+ ",D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where R.ISSUES_STATUS_ID=:status AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0  ORDER BY R.ISSUES_ID desc",
					Object[].class);
		}else {
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,R.ISSUES_WORKORDER_ID,R.ISSUES_SE_ID,D.driver_Lastname "
							+ ",D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where R.ISSUES_STATUS_ID=:status AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0  ORDER BY R.ISSUES_ID desc",
							Object[].class);
		}
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		
		queryt.setParameter("status", IssuesTypeConstant.ISSUES_STATUS_RESOLVED);
		List<Object[]> results = queryt.getResultList();
		List<IssuesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setCREATED_DATE_ON((Date) result[14]);
				list.setLASTUPDATED_DATE_ON((Date) result[15]);
				list.setISSUES_STATUS_ID((short) result[16]);
				list.setISSUES_NUMBER((Long) result[17]);
				if(result[18] != null) {
					list.setISSUES_WORKORDER_ID((Long) result[18]);
				}
				if(result[19] != null) {
					list.setISSUES_SE_ID((Long) result[19]);
				}
				
				if(result[20] != null && !((String)result[20]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[20]);
					if(result[21] != null && !((String)result[21]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[21]);
					
					list.setVehicleType((String) result[22]);
					
					list.setVehicleMaker((String) result[23]);


				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<IssuesDto> find_Issues_WoCreated_Status(Integer pageNumber, CustomUserDetails userDetails) throws Exception {
		
		TypedQuery<Object[]> queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,D.driver_Lastname "
							+ ",D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where R.ISSUES_STATUS_ID=:status AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0  ORDER BY R.ISSUES_ID desc",
					Object[].class);
		}else {
			
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,D.driver_Lastname "
							+ ",D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " LEFT JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " Where R.ISSUES_STATUS_ID=:status AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0  ORDER BY R.ISSUES_ID desc",
							Object[].class);
		}
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		
		queryt.setParameter("status", IssuesTypeConstant.ISSUES_STATUS_WOCREATED);
		List<Object[]> results = queryt.getResultList();
		List<IssuesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setCREATED_DATE_ON((Date) result[14]);
				list.setLASTUPDATED_DATE_ON((Date) result[15]);
				list.setISSUES_STATUS_ID((short) result[16]);
				list.setISSUES_NUMBER((Long) result[17]);
				if(result[18] != null && !((String)result[18]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[18]);
					if(result[19] != null && !((String)result[19]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[19]);
					
				list.setVehicleType((String) result[20]);
					
				list.setVehicleMaker((String) result[21]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * find_Issues_Open_Status(java.lang.String)
	 */
	@Transactional
	public List<IssuesDto> find_Issues_ReJect_Status(Integer pageNumber,CustomUserDetails userDetails) throws Exception {
		
		TypedQuery<Object[]> queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,D.driver_Lastname "
							+ ",D.driver_fathername,VT.vtype ,VM.vehicleManufacturerName  FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where R.ISSUES_STATUS_ID=:status  AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0  ORDER BY R.ISSUES_ID desc",
					Object[].class);
		}else {
			
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,D.driver_Lastname "
							+ ",D.driver_fathername,VT.vtype ,VM.vehicleManufacturerName  FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where R.ISSUES_STATUS_ID=:status  AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0  ORDER BY R.ISSUES_ID desc",
							Object[].class);
		}
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		
		queryt.setParameter("status", IssuesTypeConstant.ISSUES_STATUS_REJECT);
		List<Object[]> results = queryt.getResultList();
		List<IssuesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setCREATED_DATE_ON((Date) result[14]);
				list.setLASTUPDATED_DATE_ON((Date) result[15]);
				list.setISSUES_STATUS_ID((short) result[16]);
				list.setISSUES_NUMBER((Long) result[17]);
				if(result[18] != null && !((String)result[18]).trim().equals(""))
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[18]);
				if(result[19] != null && !((String)result[19]).trim().equals(""))
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[19]);
				list.setVehicleType((String) result[20]);
				list.setVehicleMaker((String) result[21]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * get_Issues_Your_pagination(java.lang.Integer, java.lang.String)
	 */
	@Transactional
	public Page<Issues> get_Issues_Your_pagination(Integer pageNumber, CustomUserDetails userDetails) throws Exception {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return issuesRepository.get_Issues_Your_pagination(userDetails.getEmail(),userDetails.getCompany_id(), pageable);
		}
		return issuesRepository.get_Issues_Your_pagination(userDetails.getEmail(),userDetails.getCompany_id(), userDetails.getId(), pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * find_Issues_Open_Status(java.lang.String)
	 */
	@Transactional
	public List<IssuesDto> find_Issues_Your_Status(Integer pageNumber, CustomUserDetails userDetails) throws Exception {
		TypedQuery<Object[]> queryt =	null;
		
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,D.driver_Lastname "
							+ ",D.driver_fathername , VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where  lower(R.ISSUES_ASSIGN_TO) Like ('%" + userDetails.getEmail()
							+ "%') AND R.ISSUES_STATUS_ID <> "+IssuesTypeConstant.ISSUES_STATUS_CLOSED+" AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ORDER BY R.ISSUES_ID desc",
					Object[].class);
		}else {
			
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,D.driver_Lastname "
							+ ",D.driver_fathername,VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where  lower(R.ISSUES_ASSIGN_TO) Like ('%" + userDetails.getEmail()
							+ "%') AND R.ISSUES_STATUS_ID <> "+IssuesTypeConstant.ISSUES_STATUS_CLOSED+" AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ORDER BY R.ISSUES_ID desc",
							Object[].class);
		}
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<IssuesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();
				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setCREATED_DATE_ON((Date) result[14]);
				list.setLASTUPDATED_DATE_ON((Date) result[15]);
				list.setISSUES_STATUS_ID((short) result[16]);
				list.setISSUES_NUMBER((Long) result[17]);
				
				if(result[18] != null && !((String)result[18]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[18]);
					if(result[19] != null && !((String)result[19]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[19]);
					
					list.setVehicleType((String) result[20]);
					
					list.setVehicleMaker((String) result[21]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * find_Issues_Open_Status(java.lang.String)
	 */
	@Transactional
	public List<IssuesDto> find_Issues_Closed_Status(Integer pageNumber, CustomUserDetails userDetails) throws Exception {
		
		TypedQuery<Object[]> queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,D.driver_Lastname "
							+ ",D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where R.ISSUES_STATUS_ID=:status AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0  ORDER BY R.ISSUES_ID desc",
					Object[].class);
		}else {
			
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,D.driver_Lastname "
							+ ",D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where R.ISSUES_STATUS_ID=:status AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0  ORDER BY R.ISSUES_ID desc",
							Object[].class);
		}
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		
		queryt.setParameter("status", IssuesTypeConstant.ISSUES_STATUS_CLOSED);
		List<Object[]> results = queryt.getResultList();
		List<IssuesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setCREATED_DATE_ON((Date) result[14]);
				list.setLASTUPDATED_DATE_ON((Date) result[15]);
				list.setISSUES_STATUS_ID((short) result[16]);
				list.setISSUES_NUMBER((Long) result[17]);
				if(result[18] != null && !((String)result[18]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[18]);
					if(result[19] != null && !((String)result[19]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[19]);
				list.setVehicleType((String) result[20]);
					
				list.setVehicleMaker((String) result[21]);	
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#countTotalIssues()
	 */
	@Transactional
	public Long countTotalIssues() throws Exception {

		return issuesRepository.countTotalIssues();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IIssuesService#countStatus(java.lang.
	 * String)
	 */
	/*@Transactional
	public Long countStatus(String Status) throws Exception {

		return issuesRepository.countStatus(Status);
	}*/

	/** Issues Task for only vehicle Issues status only */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * registerNew_IssuesTasks(org.fleetop.persistence.model.IssuesTasks)
	 */
	@Transactional
	public IssuesTasks registerNew_IssuesTasks(IssuesTasks IssuestaskDto) throws Exception {

		return issuesTaskRepository.save(IssuestaskDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IIssuesService#get_IssuesDetails(java
	 * .lang.Long)
	 */
	@Transactional
	public IssuesDto get_IssuesDetails(Long Issues_ID, Integer companyId) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_NUMBER, R.ISSUE_TYPE_ID, R.ISSUES_VID, V.vehicle_registration, VG.vGroup,"
							+ " R.ISSUES_ODOMETER, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name, R.ISSUES_DEPARTNMENT_ID,"
							+ " DP.depart_name, R.ISSUES_REPORTED_DATE, R.ISSUES_SUMMARY, R.ISSUES_DESCRIPTION, R.ISSUES_LABELS_ID, U.firstName,"
							+ " R.ISSUES_ASSIGN_TO_NAME, R.ISSUES_STATUS_ID, U2.email, U3.email, R.CREATED_DATE, R.LASTUPDATED_DATE, R.CREATEDBYID, R.LASTMODIFIEDBYID,"
							+ " R.ISSUES_WORKORDER_DATE, R.ISSUES_REPORTED_BY_ID, R.ISSUES_ASSIGN_TO, D.driver_mobnumber, R.ISSUES_WORKORDER_ID, WO.workorders_Number, R.CUSTOMER_NAME,"
							+ " R.GPS_ODOMETER,R.ISSUES_SE_ID, SE.serviceEntries_Number, R.ISSUES_SE_DATE, R.VEHICLE_GROUP_ID, R.vehicleCurrentOdometer, R.categoryId,Pc.pcName,R.routeID,Tr.routeName,R.dealerServiceEntriesId, D.driver_Lastname "
							+ ", D.driver_fathername ,R.location,VT.vtype, VM.vehicleModelName, R.issueLP_ID "
							+ " FROM Issues AS R"
							+ " LEFT JOIN User U ON U.id = R.ISSUES_REPORTED_BY_ID"
							+ " LEFT JOIN User U2 ON U2.id = R.CREATEDBYID"
							+ " LEFT JOIN User U3 ON U3.id = R.LASTMODIFIEDBYID"
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleModel VM ON VM.vehicleModelId = V.vehicleModalId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.ISSUES_WORKORDER_ID"
							+ " LEFT JOIN ServiceEntries SE ON SE.serviceEntries_id = R.ISSUES_SE_ID"
							+ " LEFT JOIN PartCategories Pc ON Pc.pcid = R.categoryId"
							+ " LEFT JOIN TripRoute Tr ON Tr.routeID = R.routeID"
							+ " WHERE R.ISSUES_ID = :ISSUES_ID AND R.COMPANY_ID = :COMPANY_ID AND R.markForDelete = 0");
			  query.setParameter("ISSUES_ID", Issues_ID);
			query.setParameter("COMPANY_ID", companyId);
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			IssuesDto list;
			if (result != null) {
				list = new IssuesDto();
				
				list.setISSUES_ID((long) result[0]);
				list.setISSUES_NUMBER((long) result[1]);
				list.setISSUES_TYPE_ID((short) result[2]);
				list.setISSUES_VID((Integer) result[3]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[4]);
				list.setISSUES_VEHICLE_GROUP((String) result[5]);
				list.setISSUES_ODOMETER((Integer) result[6]);
				list.setISSUES_DRIVER_ID((Integer) result[7]);
				list.setISSUES_DRIVER_NAME((String) result[8]);
				list.setISSUES_BRANCH_ID((Integer) result[9]);
				list.setISSUES_BRANCH_NAME((String) result[10]);
				list.setISSUES_DEPARTNMENT_ID((Integer) result[11]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[12]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[13]);
				list.setISSUES_SUMMARY((String) result[14]);
				list.setISSUES_DESCRIPTION((String) result[15]);
				list.setISSUES_LABELS_ID((short) result[16]);
				list.setISSUES_REPORTED_BY((String) result[17]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[18]);
				list.setISSUES_STATUS_ID((short) result[19]);
				list.setCREATEDBY((String) result[20]);
				list.setLASTMODIFIEDBY((String) result[21]);
				list.setCREATED_DATE_ON((Date) result[22]);
				list.setLASTUPDATED_DATE_ON((Date) result[23]);
				list.setCREATEDBYID((Long) result[24]);
				list.setLASTMODIFIEDBYID((Long) result[25]);
				list.setISSUES_WORKORDER_DATE_ON((Date) result[26]);
				list.setISSUES_REPORTED_BY_ID( (Long) result[27]);
				list.setISSUES_ASSIGN_TO((String) result[28]);
				list.setDriver_mobnumber((String) result[29]);
				list.setISSUES_WORKORDER_ID((Long) result[30]);
				list.setISSUES_WORKORDER_NUMBER((Long) result[31]);
				if(result[32] != null) {
					list.setCUSTOMER_NAME((String) result[32]);
				}
				if(result[33] != null) {
					list.setGPS_ODOMETER((Double) result[33]);
				}
				if(result[34] != null) {
					list.setISSUES_SE_ID((Long) result[34]);
					list.setServiceEntries_Number((Long) result[35]);
					list.setISSUES_SE_DATEON((Date) result[36]);
				}else {
					list.setISSUES_SE_ID((long)0);
				}
				list.setVEHICLE_GROUP_ID((Long) result[37]);
				if(result[38] != null) {
					list.setVehicleCurrentOdometer((Integer) result[38]);
				}else {
					list.setVehicleCurrentOdometer(0);
				}
				if(result[39] != null) {
					list.setCategoryId((Integer) result[39]);
				}else {
					list.setCategoryId(0);
				}
				if(result[40] != null) {
					list.setPartCategoryName((String) result[40]);
				}else {
					list.setPartCategoryName("");				
				}
				if(result[41] != null) {
					list.setRouteID((Integer) result[41]);
				}else {
					list.setRouteID(0);				
				}
				if(result[42] != null) {
					list.setRouteName((String) result[42]);
				}else {
					list.setRouteName("");				
				}
				if(result[43] != null) {
					list.setDealerServiceEntriesId((Long) result[43]);
				}
				if(result[44] != null)
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+result[44]);
				
				if(result[45] != null)
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+result[45]);
				
				if(result[46] != null) {
					list.setLocation((String) result[46]);	
				}else {
					list.setLocation(" ");	
				}
				
				if(result[47] != null) {
					list.setVehicleType((String) result[47]);
				}else {
					list.setVehicleType("");
				}
				if(result[48] != null) {
					list.setVehicleModel((String) result[48]);
				}else {
					list.setVehicleModel("");
				}
				if(result[49] != null) {
					list.setIssueLP_ID((Long) result[49]);
				}else {
					list.setIssueLP_ID((long)0);
				}
				
			} else {
				return null;
			}
			return list;
			
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IIssuesService#get_IssuesDetails(java
	 * .lang.Long)
	 */
	@Transactional
	public Issues get_IssuesDetails_Your(Long Issues_ID) throws Exception {

		Issues parent = this.entityManager.find(Issues.class, Issues_ID);
		parent.getISSUESTASK().size();
		return parent;
		// return issuesRepository.get_IssuesDetails(Issues_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#delete_Issues(java.
	 * lang.Integer)
	 */
	@Transactional
	public void delete_Issues(Long Issues_ID, Integer companyId,Long userId,Timestamp toDate) {
		 issuesRepository.delete_Issues(Issues_ID, companyId,userId,toDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * check_Issues_Assign_Details(java.lang.Long)
	 */
	@Transactional
	public Issues check_Issues_Assign_Details(Long Issues_ID, CustomUserDetails	userDetails) throws Exception {

		Query query = entityManager
				.createQuery("SELECT f.ISSUES_ID, f.ISSUES_ASSIGN_TO, f.ISSUES_LABELS_ID, f.ISSUES_STATUS_ID , f.CREATEDBYID, f.ISSUE_TYPE_ID From Issues AS f Where ( f.ISSUES_ID=" + Issues_ID
						+ " AND lower(f.ISSUES_ASSIGN_TO) Like ('%" + userDetails.getEmail() + "%') OR f.ISSUES_ID=" + Issues_ID
						+ " AND CREATEDBYID =" + userDetails.getId() + " ) AND f.COMPANY_ID = "+userDetails.getCompany_id()+" AND f.markForDelete = 0 ");

		Object[] issues = null;

		try {
			issues = (Object[]) query.getSingleResult();
		} catch (NoResultException e) {
			
		}
		Issues select = new Issues();
		if (issues != null) {
			select.setISSUES_ID((Long) issues[0]);
			select.setISSUES_ASSIGN_TO((String) issues[1]);
			select.setISSUES_LABELS_ID((short) issues[2]);
			select.setISSUES_STATUS_ID((short) issues[3]);
			select.setCREATEDBYID((Long) issues[4]);
			select.setISSUE_TYPE_ID((short) issues[5]);
		} else {
			return null;
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * check_Issues_Assign_Details(java.lang.Long)
	 */
	@Transactional
	public Issues check_Issues_Created_Issues_Details(Long Issues_ID, CustomUserDetails	userDetails) throws Exception {

		Query query = entityManager
				.createQuery("SELECT f.ISSUES_ID, f.ISSUES_ASSIGN_TO From Issues AS f Where  f.ISSUES_ID=" + Issues_ID
						+ " AND CREATEDBYID =" + userDetails.getId() + " AND f.COMPANY_ID = "+userDetails.getCompany_id()+" AND f.markForDelete = 0 ");

		Object[] issues = null;

		try {
			issues = (Object[]) query.getSingleResult();
		} catch (NoResultException e) {
			
		}
		Issues select = new Issues();
		if (issues != null) {
			select.setISSUES_ID((Long) issues[0]);
			select.setISSUES_ASSIGN_TO((String) issues[1]);
		} else {
			return null;
		}
		return select;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * get_Issues_ID_TO_GET_IssuesTask_Details(java.lang.Long)
	 */
	@Transactional
	public IssuesTasks get_Issues_VEHICLEID_TO_GET_IssuesTask_Details(Long Issues_ID) throws Exception {

		return issuesTaskRepository.get_Issues_VEHICLEID_TO_GET_IssuesTask_Details(Issues_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * countTotalIssues_AND_IssuesOpenStatus()
	 */
	@Transactional
	public Object[] countTotalIssues_AND_IssuesOpenStatus(CustomUserDetails userDetails) throws Exception {
		Query queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager
					.createQuery("SELECT COUNT(co),(SELECT  COUNT(P) FROM Issues AS P where  P.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND P.COMPANY_ID = "+userDetails.getCompany_id()+" AND P.markForDelete = 0 ) ,"
							+ "(SELECT  COUNT(Y) FROM Issues AS Y where Y.COMPANY_ID = "+userDetails.getCompany_id()+" AND Y.markForDelete = 0), " 
							+ "(SELECT  COUNT(OD) FROM Issues AS OD where OD.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND OD.COMPANY_ID = "+userDetails.getCompany_id()+" AND OD.markForDelete = 0 AND OD.ISSUES_REPORTED_DATE <= '"+DateTimeUtility.getCurrentTimeStamp()+"' ), " 
							+ "(SELECT  COUNT(WO) FROM Issues AS WO where WO.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+" AND WO.COMPANY_ID = "+userDetails.getCompany_id()+" AND WO.markForDelete = 0 ), " 
							+ "(SELECT  COUNT(SE) FROM Issues AS SE where SE.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_SE_CREATED+" AND SE.COMPANY_ID = "+userDetails.getCompany_id()+" AND SE.markForDelete = 0 ), " 
							+ "(SELECT  COUNT(IRL) FROM Issues AS IRL where IRL.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_RESOLVED+" AND IRL.COMPANY_ID = "+userDetails.getCompany_id()+" AND IRL.markForDelete = 0 ), " 
							+ "(SELECT  COUNT(IRT) FROM Issues AS IRT where IRT.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_REJECT+" AND IRT.COMPANY_ID = "+userDetails.getCompany_id()+" AND IRT.markForDelete = 0 ), " 
							+ "(SELECT  COUNT(IC) FROM Issues AS IC where IC.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_CLOSED+" AND IC.COMPANY_ID = "+userDetails.getCompany_id()+" AND IC.markForDelete = 0 ), " 
							+ "(SELECT  COUNT(DSE) FROM Issues AS DSE where DSE.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_DSE_CREATED+" AND DSE.COMPANY_ID = "+userDetails.getCompany_id()+" AND DSE.markForDelete = 0 ), " 
							+ "(SELECT  COUNT(VBR) FROM Issues AS VBR where VBR.ISSUE_TYPE_ID ="+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN+" AND VBR.COMPANY_ID = "+userDetails.getCompany_id()+" AND VBR.markForDelete = 0 ) " 
							+ " FROM Issues As co  "
							+ " where co.ISSUES_STATUS_ID  <> "+IssuesTypeConstant.ISSUES_STATUS_CLOSED+" AND lower(co.ISSUES_ASSIGN_TO) Like ('%" + userDetails.getEmail()+"%') AND  co.COMPANY_ID = "+userDetails.getCompany_id()+" AND co.markForDelete = 0 ");
		}else {
			queryt = entityManager
					.createQuery("SELECT COUNT(co),(SELECT  COUNT(P) FROM Issues AS P "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = P.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where  P.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND P.COMPANY_ID = "+userDetails.getCompany_id()+" AND P.markForDelete = 0 ) ,"
							+ "(SELECT  COUNT(Y) FROM Issues AS Y"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = Y.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where Y.COMPANY_ID = "+userDetails.getCompany_id()+" AND Y.markForDelete = 0), " 
							+ "(SELECT  COUNT(OD) FROM Issues AS OD "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = OD.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where OD.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND OD.COMPANY_ID = "+userDetails.getCompany_id()+" AND OD.markForDelete = 0 AND OD.ISSUES_REPORTED_DATE <= '"+DateTimeUtility.getCurrentTimeStamp()+"' ), " 
							+ "(SELECT  COUNT(WO) FROM Issues AS WO "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = WO.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where WO.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+" AND WO.COMPANY_ID = "+userDetails.getCompany_id()+" AND WO.markForDelete = 0 ), " 
							+ "(SELECT  COUNT(SE) FROM Issues AS SE "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = SE.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where SE.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_SE_CREATED+" AND SE.COMPANY_ID = "+userDetails.getCompany_id()+" AND SE.markForDelete = 0 ), " 
							+ "(SELECT  COUNT(IRL) FROM Issues AS IRL "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = IRL.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where IRL.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_RESOLVED+" AND IRL.COMPANY_ID = "+userDetails.getCompany_id()+" AND IRL.markForDelete = 0 ), " 
							+ "(SELECT  COUNT(IRT) FROM Issues AS IRT "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = IRT.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where IRT.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_REJECT+" AND IRT.COMPANY_ID = "+userDetails.getCompany_id()+" AND IRT.markForDelete = 0 ), " 
							+ "(SELECT  COUNT(IC) FROM Issues AS IC "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = IC.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where IC.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_CLOSED+" AND IC.COMPANY_ID = "+userDetails.getCompany_id()+" AND IC.markForDelete = 0 ), "
							+ "(SELECT  COUNT(DSE) FROM Issues AS DSE "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = DSE.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where DSE.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_DSE_CREATED+" AND DSE.COMPANY_ID = "+userDetails.getCompany_id()+" AND DSE.markForDelete = 0 ), "
							+	"(SELECT  COUNT(VBR) FROM Issues AS VBR "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VBR.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where VBR.ISSUE_TYPE_ID ="+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN+" AND VBR.COMPANY_ID = "+userDetails.getCompany_id()+" AND VBR.markForDelete = 0 ) "
							+ " FROM Issues As co  "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = co.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where co.ISSUES_STATUS_ID  <> "+IssuesTypeConstant.ISSUES_STATUS_CLOSED+" AND lower(co.ISSUES_ASSIGN_TO) Like ('%" + userDetails.getEmail()+"%') AND  co.COMPANY_ID = "+userDetails.getCompany_id()+" AND co.markForDelete = 0 ");
		}
 
		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * update_Close_Issues_Status(java.lang.String, java.lang.String,
	 * java.lang.String, java.util.Date, java.lang.Long)
	 */
	@Transactional
	public void update_Close_Issues_Status(short Status, Long CloseBy, Date close_date, Long Issues_ID, Integer companyId) {
		issuesRepository.update_Close_Issues_Status(Status, CloseBy, close_date, Issues_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * update_Close_Issues_Status(java.lang.String, java.lang.String,
	 * java.lang.String, java.util.Date, java.lang.Long)
	 */
	@Transactional
	public void update_Reject_Issues_Status(short Status, Long CloseBy, Date close_date, Long Issues_ID, Integer companyId) {
		issuesRepository.update_Reject_Issues_Status(Status, CloseBy, close_date, Issues_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * update_Close_Issues_Status(java.lang.String, java.lang.String,
	 * java.lang.String, java.util.Date, java.lang.Long)
	 */
	@Transactional
	public void update_Reopen_Issues_Status(short Status, Long CloseBy, Date close_date, Long Issues_ID, Integer companyId) {
		issuesRepository.update_Reopen_Issues_Status(Status, CloseBy, close_date, Issues_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * update_Close_Issues_Status(java.lang.String, java.lang.String,
	 * java.lang.String, java.util.Date, java.lang.Long)
	 */
	@Transactional
	public void update_ReSolved_Issues_Status(short Status, Long CloseBy, Date close_date, Long Issues_ID, Integer companyId) {
		issuesRepository.update_ReSolved_Issues_Status(Status, CloseBy, close_date, Issues_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#find_Issues_Search(
	 * java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<IssuesDto> find_Issues_Search(String user_email, String Search) {
		List<IssuesDto> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
						+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
						+ "DP.depart_name, R.ISSUES_STATUS_ID FROM Issues AS R "
						+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
						+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
						+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
						+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
						+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
						+ " Where lower(V.vehicle_registration) Like ('%" + Search + "%') " + " "
						+ " OR  lower(B.branch_name) Like ('%" + Search + "%') "
						+ "OR lower(D.driver_firstname) Like ('%" + Search + "%') OR" + " lower(R.ISSUES_ID) Like ('%"
						+ Search + "%') ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setISSUES_STATUS_ID((short) result[13]);

				Dtos.add(list);
			}
		}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#find_Issues_Search(
	 * java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<IssuesDto> find_Issues_SearchYour(String user_email, String Search) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<IssuesDto> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
						+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUES_TYPE, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
						+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE FROM Issues AS R " 
						+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
						+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
						+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
						+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
						+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
						+ " Where R.CREATEDBYID=" + userDetails.getId()
						+ " AND lower(V.vehicle_registration) Like ('%" + Search + "%') " + " "
						+ " OR R.CREATEDBYID=" + userDetails.getId() + " AND lower(B.branch_name) Like ('%" + Search
						+ "%') " + "OR R.CREATEDBYID=" + userDetails.getId() + " AND lower(D.driver_firstname) Like ('%"
						+ Search + "%')" + "OR R.CREATEDBYID=" + userDetails.getId() + " AND lower(R.ISSUES_ID) Like ('%"
						+ Search + "%') " + "OR lower(R.ISSUES_ASSIGN_TO) Like ('%" + user_email
						+ "%') AND lower(V.vehicle_registration) Like ('%" + Search + "%') " + " "
						+ " OR lower(R.ISSUES_ASSIGN_TO) Like ('%" + user_email
						+ "%') AND lower(B.branch_name) Like ('%" + Search + "%') "
						+ "OR lower(R.ISSUES_ASSIGN_TO) Like ('%" + user_email
						+ "%') AND lower(D.driver_firstname) Like ('%" + Search + "%')"
						+ "OR lower(R.ISSUES_ASSIGN_TO) Like ('%" + user_email + "%') AND lower(R.ISSUES_ID) Like ('%"
						+ Search + "%')",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS((String) result[2]);
				list.setISSUES_REPORTED_DATE(dateFormat_Name.format((Date) result[3]));
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE((String) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setCREATED_DATE_ON((Date) result[14]);
				list.setLASTUPDATED_DATE_ON((Date) result[15]);
				Dtos.add(list);
			}
		}
		}
		return Dtos;
	}

	/**
	 * Issues Comment in details *
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * add_Issues_Comment_Details(org.fleetop.persistence.model.IssuesComment)
	 */
	@Transactional
	public void add_Issues_Comment_Details(IssuesComment issueComment) throws Exception {

		issuesCommentRepository.save(issueComment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * Get_Issues_ID_Comment_Details(java.lang.Long)
	 */
	@Transactional
	public List<IssuesCommentDto> Get_Issues_ID_Comment_Details(Long issue_ID, Integer companyId) throws Exception {

		// return
		// issuesCommentRepository.Get_Issues_ID_Comment_Details(issue_ID);

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT C.ISSUE_COMMENTID, C.ISSUES_ID, C.ISSUE_TITLE, C.ISSUE_COMMENT, U.firstName, U.email, C.CREATED_DATE, C.COMMENT_TYPE_ID, CT.commentTypeName"
				+ ",C.assignee,UA.firstName,D.driver_firstname,D.driver_Lastname,D.driver_fathername"
				+ " From IssuesComment as C "
				+ " LEFT JOIN User U ON U.id = C.CREATEDBYID"
				+ " LEFT JOIN User UA ON UA.id = C.assignee"
				+ " LEFT JOIN Driver D ON D.driver_id = C.driverId"
				+ " LEFT JOIN CommentType CT ON CT.commentTypeId = C.COMMENT_TYPE_ID"
				+ " Where C.ISSUES_ID =:ID AND C.COMPANY_ID = :companyId AND C.markForDelete = 0 ORDER BY C.ISSUES_ID desc", Object[].class);
		query.setParameter("ID", issue_ID);
		query.setParameter("companyId", companyId);

		query.setFirstResult(0);
		query.setMaxResults(100);

		List<Object[]> results = query.getResultList();

		List<IssuesCommentDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesCommentDto>();
			IssuesCommentDto issueCom = null;
			for (Object[] result : results) {
				issueCom = new IssuesCommentDto();

				issueCom.setISSUE_COMMENTID((Long) result[0]);
				issueCom.setISSUES_ID((Long) result[1]);
				issueCom.setISSUE_TITLE((String) result[2]);
				issueCom.setISSUE_COMMENT((String) result[3]);
				issueCom.setCREATEDBY((String) result[4]);
				issueCom.setCREATED_EMAIL((String) result[5]);
				issueCom.setCREATED_DATEON((Date) result[6]);
				issueCom.setCOMMENT_TYPE_ID((Long) result[7]);
				issueCom.setCOMMENT_TYPE_NAME((String) result[8]);
				if(result[9] != null)
				issueCom.setAssignee((Long) result[9]);
				issueCom.setAssigneeName((String) result[10]);
				if(result[11] != null)
				issueCom.setDriverName((String) result[11]);
				if(result[12] != null)
				issueCom.setDriverName(issueCom.getDriverName() +"  "+result[12]);
				if(result[13] != null)
				issueCom.setDriverFatherName((String) result[13]);
				Dtos.add(issueCom);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * Delete_Issues_Comment_Details(java.lang.Long)
	 */
	@Transactional
	public void Delete_Issues_Comment_Details(Long issueComment_ID, Integer companyId) throws Exception {

		issuesCommentRepository.Delete_Issues_Comment_Details(issueComment_ID, companyId);
	}

	/**
	 * Issues Photo in details *
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * add_Issues_Photo_Details(org.fleetop.persistence.model.IssuesPhoto)
	 */
	@Transactional
	public void add_Issues_Photo_Details(org.fleetopgroup.persistence.document.IssuesPhoto issuePhoto) throws Exception {
		issuePhoto.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_ISSUES_PHOTO));
		mongoTemplate.save(issuePhoto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * Get_Issues_ID_Photo_Details(java.lang.Long)
	 */
	@Transactional
	public List<org.fleetopgroup.persistence.document.IssuesPhoto> Get_Issues_ID_Photo_Details(Long issue_ID, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("ISSUE_ID").is(issue_ID).and("COMPANY_ID").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.IssuesPhoto.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * Delete_Issues_Photo_Details(java.lang.Long)
	 */
	@Transactional
	public void Delete_Issues_Photo_Details(Long issueComment_ID, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(issueComment_ID).and("COMPANY_ID").is(companyId).and("markForDelete").is(false));
		mongoTemplate.remove(query, org.fleetopgroup.persistence.document.IssuesPhoto.class);
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IIssuesService#Get_Issues_Photo(java.
	 * lang.Long)
	 */
	@Transactional
	public org.fleetopgroup.persistence.document.IssuesPhoto Get_Issues_Photo(Long issueComment_ID, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(issueComment_ID).and("COMPANY_ID").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.IssuesPhoto.class);
	}

	/**
	 * Issues Document in details *
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * add_Issues_Document_Details(org.fleetop.persistence.model.IssuesDocument)
	 */
	@Transactional
	public void add_Issues_Document_Details(org.fleetopgroup.persistence.document.IssuesDocument issueDocument) throws Exception {
		issueDocument.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_ISSUES_DOCUMENT));
		mongoTemplate.save(issueDocument);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * Update_Issues_Document_Details(org.fleetop.persistence.model. IssuesDocument)
	 */
	@Transactional
	public void Update_Issues_Document_Details(org.fleetopgroup.persistence.document.IssuesDocument issueDocument) throws Exception {
		mongoTemplate.save(issueDocument);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#Get_Issues_Document(
	 * java.lang.Long)
	 */
	@Transactional
	public org.fleetopgroup.persistence.document.IssuesDocument Get_Issues_Document(Long issueComment_ID, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(issueComment_ID).and("COMPANY_ID").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.IssuesDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * Get_Issues_ID_Document_Details(java.lang.Long)
	 */
	@Transactional
	public List<org.fleetopgroup.persistence.document.IssuesDocument> Get_Issues_ID_Document_Details(Long issue_ID, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("ISSUE_ID").is(issue_ID).and("COMPANY_ID").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.IssuesDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * Delete_Issues_Document_Details(java.lang.Long)
	 */
	@Transactional
	public void Delete_Issues_Document_Details(Long issueComment_ID, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(issueComment_ID).and("COMPANY_ID").is(companyId).and("markForDelete").is(false));
		mongoTemplate.remove(query, org.fleetopgroup.persistence.document.IssuesDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IIssuesService#update_Vehicle_Issues(
	 * org.fleetop.persistence.model.Issues)
	 */
	@Transactional
	public void update_Vehicle_Issues(Issues IssuesDto) throws Exception {

		issuesRepository.update_Vehicle_Issues(IssuesDto.getISSUES_ODOMETER(), IssuesDto.getISSUES_DRIVER_ID(),
				IssuesDto.getISSUES_REPORTED_DATE(), IssuesDto.getISSUES_SUMMARY(),
				IssuesDto.getISSUES_DESCRIPTION(), IssuesDto.getISSUES_LABELS_ID(), IssuesDto.getISSUES_ASSIGN_TO(),
				IssuesDto.getISSUES_ASSIGN_TO_NAME(), IssuesDto.getLASTMODIFIEDBYID(), IssuesDto.getLASTUPDATED_DATE(),
				IssuesDto.getISSUES_ID(), IssuesDto.getCOMPANY_ID());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#update_Driver_Issues(
	 * org.fleetop.persistence.model.Issues)
	 */
	@Transactional
	public void update_Driver_Issues(Issues IssuesDto) throws Exception {

		issuesRepository.update_Driver_Issues(IssuesDto.getISSUES_REPORTED_DATE(), IssuesDto.getISSUES_SUMMARY(),
				IssuesDto.getISSUES_DESCRIPTION(), IssuesDto.getISSUES_LABELS_ID(), IssuesDto.getISSUES_ASSIGN_TO(),
				IssuesDto.getISSUES_ASSIGN_TO_NAME(), IssuesDto.getLASTMODIFIEDBYID(), IssuesDto.getLASTUPDATED_DATE(),
				IssuesDto.getISSUES_ID(), IssuesDto.getCOMPANY_ID());

	}
	
	@Transactional
	public void update_Customer_Issues(Issues IssuesDto) throws Exception {

		issuesRepository.update_Customer_Issues(IssuesDto.getISSUES_REPORTED_DATE(), IssuesDto.getISSUES_SUMMARY(),
				IssuesDto.getISSUES_DESCRIPTION(), IssuesDto.getISSUES_LABELS_ID(), IssuesDto.getISSUES_ASSIGN_TO(),
				IssuesDto.getISSUES_ASSIGN_TO_NAME(),IssuesDto.getCUSTOMER_NAME(), IssuesDto.getLASTMODIFIEDBYID(), IssuesDto.getLASTUPDATED_DATE(),
				IssuesDto.getISSUES_ID(), IssuesDto.getCOMPANY_ID());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#update_Other_Issues(
	 * org.fleetop.persistence.model.Issues)
	 */
	@Transactional
	public void update_Other_Issues(Issues IssuesDto) throws Exception {

		issuesRepository.update_Other_Issues(IssuesDto.getISSUES_BRANCH_ID(), 
				IssuesDto.getISSUES_DEPARTNMENT_ID(),
				IssuesDto.getISSUES_REPORTED_DATE(), IssuesDto.getISSUES_SUMMARY(), IssuesDto.getISSUES_DESCRIPTION(),
				IssuesDto.getISSUES_LABELS_ID(), IssuesDto.getISSUES_ASSIGN_TO(), IssuesDto.getISSUES_ASSIGN_TO_NAME(),
				IssuesDto.getLASTMODIFIEDBYID(), IssuesDto.getLASTUPDATED_DATE(), IssuesDto.getISSUES_ID(), IssuesDto.getCOMPANY_ID());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * update_Create_WorkOrder_Issues_Status(java.lang.String, java.lang.String,
	 * java.util.Date, java.lang.Long, java.lang.Long)
	 */
	@Transactional
	public void update_Create_WorkOrder_Issues_Status(short Status, Long CloseBy, Date close_date, Long WorkOrder_ID,
			Long Issues_ID,int companyId) {
		issuesRepository.update_Create_WorkOrder_Issues_Status(Status, CloseBy, close_date, WorkOrder_ID, Issues_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * Show_Header_Issues_Details(java.lang.Long)
	 */
	@Transactional
	public IssuesDto Show_Header_Issues_Details(Long Issues_ID, CustomUserDetails	userDetails) throws Exception {

		Object[] result;
		try {
			Query query = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.ISSUES_NUMBER, R.ISSUES_STATUS_ID FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " WHERE R.ISSUES_ID=:id AND R.COMPANY_ID = :COMPANY_ID AND R.markForDelete = 0");
			query.setParameter("id", Issues_ID);
			query.setParameter("COMPANY_ID", userDetails.getCompany_id());
			result = (Object[]) query.getSingleResult();

			if (result == null || result.length == 0) {
				return null;
			}
			IssuesDto list = new IssuesDto();
			if (result != null) {

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setISSUES_NUMBER((Long) result[14]);
				list.setISSUES_STATUS_ID((short) result[15]);
			}
			return list;
		} catch (NoResultException e) {
			return null;
		}
	}

	@Transactional
	public long countIssues_Comment(Long Issues_ID) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Query queryt = entityManager.createQuery(
				"SELECT COUNT(co) FROM IssuesComment As co where  co.ISSUES_ID =:id AND co.COMPANY_ID = :companyId AND co.markForDelete = 0");
		
		queryt.setParameter("id", Issues_ID);
		queryt.setParameter("companyId", userDetails.getCompany_id());
		long count = (long) queryt.getSingleResult();
		
		return count;
	}

	@Transactional
	public long countIssues_Document(Long Issues_ID) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("ISSUE_ID").is(Issues_ID).and("COMPANY_ID").is(userDetails.getCompany_id()).and("markForDelete").is(false));
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.IssuesDocument.class).size();
	}

	@Transactional
	public long countIssues_Photo(Long Issues_ID) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("ISSUE_ID").is(Issues_ID).and("COMPANY_ID").is(userDetails.getCompany_id()).and("markForDelete").is(false));
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.IssuesPhoto.class).size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * find_Issues_Open_andVehicle_ID_Status(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public List<IssuesDto> find_Issues_Open_andVehicle_ID_Status(String user_email, Integer vehicle_ID, Integer companyId, Long id) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_ODOMETER, R.ISSUES_REPORTED_DATE,  R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, "
						+ " R.ISSUES_STATUS_ID, R.ISSUES_NUMBER, R.CREATEDBYID, R.ISSUES_ASSIGN_TO FROM Issues AS R "
						+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID" 
						+ " Where R.ISSUES_VID=:VID AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0",
				Object[].class);
		queryt.setParameter("VID", vehicle_ID);
		List<Object[]> results = queryt.getResultList();

		List<IssuesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_ODOMETER((Integer) result[3]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[4]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[5]);
				list.setISSUES_TYPE_ID((short) result[6]);
				list.setISSUES_DRIVER_ID((Integer) result[7]);
				list.setISSUES_DRIVER_NAME((String) result[8]);
				list.setISSUES_STATUS_ID((short) result[9]);
				list.setISSUES_NUMBER((Long) result[10]);
				list.setCREATEDBYID((Long) result[11]);
				list.setISSUES_ASSIGN_TO((String) result[12]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * Report_find_Issues_Search(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<IssuesDto> Report_find_Issues_Search(String reportdateFrom, String reportdateTo, String Search_Query) throws Exception {
		CustomUserDetails	userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TypedQuery<Object[]> queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER , R.CREATEDBYID, R.ISSUES_ASSIGN_TO FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where (R.ISSUES_REPORTED_DATE BETWEEN '" + reportdateFrom + "' AND '" + reportdateTo + "' "
							+ Search_Query + " ) AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ",
					Object[].class);
		}else {
			
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER, R.CREATEDBYID, R.ISSUES_ASSIGN_TO FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where (R.ISSUES_REPORTED_DATE BETWEEN '" + reportdateFrom + "' AND '" + reportdateTo + "' "
							+ Search_Query + " ) AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ",
							Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<IssuesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setISSUES_STATUS_ID((short) result[14]);
				list.setISSUES_NUMBER((Long) result[15]);
				list.setCREATEDBYID((Long) result[16]);
				list.setISSUES_ASSIGN_TO((String) result[17]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<IssuesDto> Report_daily_Issues_Search(String ISSUES_DATE_FROM, String ISSUES_DATE_TO,
			String Search_Query, CustomUserDetails	userDetails) throws Exception {
		TypedQuery<Object[]> queryt = null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager
					.createQuery("SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER ,D.driver_Lastname ,D.driver_fathername FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where R.ISSUES_REPORTED_DATE BETWEEN '" + ISSUES_DATE_FROM
							+ "' AND '" + ISSUES_DATE_TO + "'  " + Search_Query + " AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0", Object[].class);
		}else {
			queryt = entityManager
					.createQuery("SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,D.driver_Lastname ,D.driver_fathername FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where R.ISSUES_REPORTED_DATE BETWEEN '" + ISSUES_DATE_FROM
							+ "' AND '" + ISSUES_DATE_TO + "'  " + Search_Query + " AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0", Object[].class);
		}

		List<Object[]> results = queryt.getResultList();

		List<IssuesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setISSUES_STATUS_ID((short) result[14]);
				list.setISSUES_NUMBER((Long) result[15]);
				if(result[16] != null && !((String)result[16]).trim().equals("")) {
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[16]);
				}
				if(result[17] != null && !((String)result[17]).trim().equals("")) {
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[17]);
				}
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<IssuesDto> Report_Issues_Reported_Search(String reportdateFrom, String reportdateTo, String Search_Query, CustomUserDetails	userDetails) throws Exception {

		TypedQuery<Object[]> queryt = null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager
					.createQuery("SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,D.driver_Lastname,D.driver_fathername FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where R.ISSUES_REPORTED_DATE BETWEEN '" + reportdateFrom
							+ "' AND '" + reportdateTo + "' " + Search_Query + " AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0", Object[].class);
		}else {
			queryt = entityManager
					.createQuery("SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,D.driver_Lastname,D.driver_fathername FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where R.ISSUES_REPORTED_DATE BETWEEN '" + reportdateFrom
							+ "' AND '" + reportdateTo + "' " + Search_Query + " AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0", Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<IssuesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setISSUES_STATUS_ID((short) result[14]);
				list.setISSUES_NUMBER((Long) result[15]);
				if(result[16] != null && !((String)result[16]).trim().equals(""))
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[16]);
				if(result[17] != null && !((String)result[17]).trim().equals("")) 
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[17]);
				Dtos.add(list);
			}
		}
		return Dtos;

	}

	@Transactional
	public List<IssuesDto> Report_YourIssues_Status_Search(String userEmail, String reportdateFrom, String reportdateTo,
			String Search_Query, Integer companyId, Long id) {
		List<IssuesDto> Dtos = null;
		if(Search_Query != null && !Search_Query.trim().equalsIgnoreCase("") && Search_Query.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(" SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
				+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
				+ "DP.depart_name, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,D.driver_Lastname ,D.driver_fathername FROM Issues AS R "
				+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
				+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
				+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
				+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
				+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
				+ " Where (R.CREATEDBYID=" + id
				+ " AND R.ISSUES_REPORTED_DATE BETWEEN '" + reportdateFrom + "' AND '" + reportdateTo + "' "
				+ Search_Query + " OR lower(R.ISSUES_ASSIGN_TO) Like ('%" + userEmail
				+ "%') AND R.ISSUES_REPORTED_DATE BETWEEN '" + reportdateFrom + "' AND '" + reportdateTo + "' "
				+ Search_Query + ") AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0", Object[].class);
		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setISSUES_STATUS_ID((short) result[14]);
				list.setISSUES_NUMBER((Long) result[15]);
				if(result[16] != null && !((String)result[16]).trim().equals(""))
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[16]);
				if(result[17] != null && !((String)result[17]).trim().equals("")) 
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[17]);
				Dtos.add(list);
			}
		}
		}
		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * get_Issues_All_pagination(java.lang.Integer)
	 */
	@Transactional
	public Page<Issues> get_Issues_All_pagination(Integer pageNumber, Integer companyId, Long id) throws Exception {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return issuesRepository.get_Issues_All_pagination(companyId, request);
		}
		return issuesRepository.get_Issues_All_pagination(companyId, id, request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IIssuesService#find_Issues_All_Status
	 * (java.lang.String)
	 */
	@Transactional
	public List<IssuesDto> find_Issues_All_Status(Integer pageNumber, CustomUserDetails userDetails) throws Exception {
		TypedQuery<Object[]> queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.ISSUES_STATUS_ID , R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_NUMBER ,R.ISSUES_WORKORDER_ID,R.ISSUES_SE_ID, R.CREATEDBYID, R.ISSUES_ASSIGN_TO,D.driver_Lastname "
							+ ",D.driver_fathername , VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " where R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ORDER BY R.ISSUES_ID desc",
					Object[].class);
		}else {
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.ISSUES_STATUS_ID , R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_NUMBER,R.ISSUES_WORKORDER_ID,R.ISSUES_SE_ID, R.CREATEDBYID, R.ISSUES_ASSIGN_TO,D.driver_Lastname "
							+ ",D.driver_fathername,VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " where R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0 ORDER BY R.ISSUES_ID desc",
							Object[].class);
		}
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);
		List<Object[]> results = queryt.getResultList();

		List<IssuesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setISSUES_STATUS_ID((short) result[14]);
				list.setCREATED_DATE_ON((Date) result[15]);
				list.setLASTUPDATED_DATE_ON((Date) result[16]);
				list.setISSUES_NUMBER((Long) result[17]);
				if(result[18] != null) {
					list.setISSUES_WORKORDER_ID((Long) result[18]);
				}
				if(result[19] != null) {
					list.setISSUES_SE_ID((Long) result[19]);
				}
				list.setCREATEDBYID((Long) result[20]);
				list.setISSUES_ASSIGN_TO((String) result[21]);
				if(result[22] != null && !((String)result[22]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[22]);
				if(result[23] != null && !((String)result[23]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[23]);
				
				list.setVehicleType((String) result[24]);
				
				list.setVehicleMaker((String) result[25]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * update_Vehicle_Group_USING_Vehicle_Id(java.lang.String, java.lang.Integer)
	 
	@Transactional
	public void update_Vehicle_Group_USING_Vehicle_Id(String Vehicle_Group, Integer vehicle_id, long vehicleGroupId) {

		issuesRepository.update_Vehicle_Group_USING_Vehicle_Id(Vehicle_Group, vehicle_id, vehicleGroupId);
	}*/

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IIssuesService#
	 * GET_WORKORDER_ID_TO_ISSUES_DETAILS(java.lang.Long)
	 */
	@Transactional
	public List<IssuesDto> GET_WORKORDER_ID_TO_ISSUES_DETAILS(Long workorders_id, Integer companyId) {

		TypedQuery<Object[]> query = entityManager
				.createQuery("SELECT R.ISSUES_ID, R.ISSUES_STATUS_ID, R.ISSUES_WORKORDER_ID FROM Issues AS R "
						+ "Where R.ISSUES_WORKORDER_ID=" + workorders_id + " AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0",Object[].class);

		List<Object[]> issuess = null;
		List<IssuesDto> finalList = null;
		try {
			issuess = query.getResultList();

		if(issuess != null && ! issuess.isEmpty()) {
			IssuesDto list = null;
			finalList= new ArrayList<>();
			for(Object[] Issues : issuess) {
				list = new IssuesDto();
				list.setISSUES_ID((Long) Issues[0]);
				list.setISSUES_STATUS(IssuesTypeConstant.getIssuesTaskStatusName((short) Issues[1]) );
				list.setISSUES_WORKORDER_ID((Long) Issues[2]);
				finalList.add(list);
			}
		}
		} catch (Exception nre) {
			nre.printStackTrace();
		}
		return finalList;

	}

	@Transactional
	public Object[] count_TOTAL_ISSUES_NOT_CLOSED_FIVEDAYS_ISSUES(String dayOne, String dayTwo, String dayThree,
			String dayFour, String dayFive, CustomUserDetails userDetails) throws Exception {
		dayOne		= "'"+dayOne+" 00:00:00' AND '"+dayOne+" 23:59:59'";
		dayTwo		= "'"+dayTwo+" 00:00:00' AND '"+dayTwo+" 23:59:59'";
		dayThree 	= "'"+dayThree+" 00:00:00' AND '"+dayThree+" 23:59:59'";
		dayFour		= "'"+dayFour+" 00:00:00' AND '"+dayFour+" 23:59:59'";
		dayFive		= " "+dayFive+" 00:00:00";
		Query queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT COUNT(co),(SELECT COUNT(Pho) FROM Issues AS Pho "
							+ " where Pho.COMPANY_ID = "+userDetails.getCompany_id()+" AND Pho.markForDelete = 0 AND Pho.ISSUES_STATUS_ID =:status AND Pho.ISSUES_REPORTED_DATE BETWEEN " + dayOne + " ), "
							+ " (SELECT  COUNT(Pur) FROM Issues AS Pur "
							+ " where Pur.COMPANY_ID = "+userDetails.getCompany_id()+" AND Pur.markForDelete = 0 AND  Pur.ISSUES_STATUS_ID=:status AND Pur.ISSUES_REPORTED_DATE BETWEEN " + dayTwo + " ),"
							+ " (SELECT  COUNT(RR) FROM Issues AS RR "
							+ " where  RR.COMPANY_ID = "+userDetails.getCompany_id()+" AND RR.markForDelete = 0 AND RR.ISSUES_STATUS_ID=:status AND RR.ISSUES_REPORTED_DATE BETWEEN " + dayThree + " ),"
							+ " (SELECT  COUNT(FE) FROM Issues AS FE "
							+ " where FE.COMPANY_ID = "+userDetails.getCompany_id()+" AND FE.markForDelete = 0 AND  FE.ISSUES_STATUS_ID=:status AND FE.ISSUES_REPORTED_DATE BETWEEN" + dayFour + " )"
							+ " FROM Issues As co "
							+ " Where co.COMPANY_ID = "+userDetails.getCompany_id()+" AND co.markForDelete = 0 AND co.ISSUES_STATUS_ID=:status AND co.ISSUES_REPORTED_DATE <='" + dayFive
							+ "' ");
		}else {
			
			queryt = entityManager.createQuery(
					"SELECT COUNT(co),(SELECT COUNT(Pho) FROM Issues AS Pho "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = Pho.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where Pho.COMPANY_ID = "+userDetails.getCompany_id()+" AND Pho.markForDelete = 0 AND Pho.ISSUES_STATUS_ID =:status AND Pho.ISSUES_REPORTED_DATE BETWEEN " + dayOne + " ), "
							+ " (SELECT  COUNT(Pur) FROM Issues AS Pur "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = Pur.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where Pur.COMPANY_ID = "+userDetails.getCompany_id()+" AND Pur.markForDelete = 0 AND  Pur.ISSUES_STATUS_ID=:status AND Pur.ISSUES_REPORTED_DATE BETWEEN " + dayTwo + " ),"
							+ " (SELECT  COUNT(RR) FROM Issues AS RR "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = RR.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where  RR.COMPANY_ID = "+userDetails.getCompany_id()+" AND RR.markForDelete = 0 AND RR.ISSUES_STATUS_ID=:status AND RR.ISSUES_REPORTED_DATE BETWEEN " + dayThree + " ),"
							+ " (SELECT  COUNT(FE) FROM Issues AS FE "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = FE.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where FE.COMPANY_ID = "+userDetails.getCompany_id()+" AND FE.markForDelete = 0 AND  FE.ISSUES_STATUS_ID=:status AND FE.ISSUES_REPORTED_DATE BETWEEN" + dayFour + " )"
							+ " FROM Issues As co "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = co.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " Where co.COMPANY_ID = "+userDetails.getCompany_id()+" AND co.markForDelete = 0 AND co.ISSUES_STATUS_ID=:status AND co.ISSUES_REPORTED_DATE <='" + dayFive
							+ "' ");
		}
		queryt.setParameter("status", IssuesTypeConstant.ISSUES_STATUS_OPEN);
		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}

	@Transactional
	public List<IssuesDto> find_NOTOPENISSUES_Status_SearchDate(short ISSUES_STATUS, String searchdate, Integer companyId) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
						+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
						+ "DP.depart_name, R.ISSUES_STATUS_ID , R.CREATED_DATE, R.LASTUPDATED_DATE,R.ISSUES_NUMBER, R.CREATEDBYID,R.ISSUES_ASSIGN_TO FROM Issues AS R"
						+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
						+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
						+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
						+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
						+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
						+ " WHERE R.ISSUES_STATUS_ID=" + ISSUES_STATUS + " AND R.ISSUES_REPORTED_DATE between'" + searchdate + " 00:00:00 ' AND '"+searchdate+" 23:59:59' AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.ISSUES_ID desc",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<IssuesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setISSUES_STATUS_ID((short) result[14]);
				list.setCREATED_DATE_ON((Date) result[15]);
				list.setLASTUPDATED_DATE_ON((Date) result[16]);
				list.setISSUES_NUMBER((Long) result[17]);
				list.setCREATEDBYID((Long) result[18]);
				list.setISSUES_ASSIGN_TO((String) result[19]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}
	@Override
	public IssuesDto get_IssuesDetailsByNumber(Long Issues_ID, CustomUserDetails userDetails) throws Exception {
		/*if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return issuesRepository.get_IssuesDetailsByNumber(Issues_ID, userDetails.getCompany_id());
		}
		return issuesRepository.get_IssuesDetailsByNumber(Issues_ID, userDetails.getId(), userDetails.getCompany_id());*/

		try {
			Query query =	null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT R.ISSUES_ID, R.ISSUES_NUMBER, R.ISSUE_TYPE_ID, R.ISSUES_VID, V.vehicle_registration, VG.vGroup,"
								+ " R.ISSUES_ODOMETER, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name, R.ISSUES_DEPARTNMENT_ID,"
								+ " DP.depart_name, R.ISSUES_REPORTED_DATE, R.ISSUES_SUMMARY, R.ISSUES_DESCRIPTION, R.ISSUES_LABELS_ID, U.firstName,"
								+ " R.ISSUES_ASSIGN_TO_NAME, R.ISSUES_STATUS_ID, U2.email, U3.email, R.CREATED_DATE, R.LASTUPDATED_DATE, R.CREATEDBYID, R.LASTMODIFIEDBYID,"
								+ " R.ISSUES_WORKORDER_DATE, R.ISSUES_REPORTED_BY_ID, R.CREATEDBYID, R.ISSUES_ASSIGN_TO, R.dealerServiceEntriesId "
								+ " FROM Issues AS R"
								+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
								+ " LEFT JOIN User U ON U.id = R.ISSUES_REPORTED_BY_ID"
								+ " LEFT JOIN User U2 ON U2.id = R.CREATEDBYID"
								+ " LEFT JOIN User U3 ON U3.id = R.LASTMODIFIEDBYID"
								+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
								+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
								+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
								+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
								+ " WHERE R.ISSUES_NUMBER = :ISSUES_ID AND R.COMPANY_ID = :COMPANY_ID AND R.markForDelete = 0");
			}else {
				query = entityManager.createQuery(
						"SELECT R.ISSUES_ID, R.ISSUES_NUMBER, R.ISSUE_TYPE_ID, R.ISSUES_VID, V.vehicle_registration, VG.vGroup,"
								+ " R.ISSUES_ODOMETER, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name, R.ISSUES_DEPARTNMENT_ID,"
								+ " DP.depart_name, R.ISSUES_REPORTED_DATE, R.ISSUES_SUMMARY, R.ISSUES_DESCRIPTION, R.ISSUES_LABELS_ID, U.firstName,"
								+ " R.ISSUES_ASSIGN_TO_NAME, R.ISSUES_STATUS_ID, U2.email, U3.email, R.CREATED_DATE, R.LASTUPDATED_DATE, R.CREATEDBYID, R.LASTMODIFIEDBYID,"
								+ " R.ISSUES_WORKORDER_DATE, R.ISSUES_REPORTED_BY_ID , R.CREATEDBYID, R.ISSUES_ASSIGN_TO, R.dealerServiceEntriesId"
								+ " FROM Issues AS R"
								+ " LEFT JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+""
								+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
								+ " LEFT JOIN User U ON U.id = R.ISSUES_REPORTED_BY_ID"
								+ " LEFT JOIN User U2 ON U2.id = R.CREATEDBYID"
								+ " LEFT JOIN User U3 ON U3.id = R.LASTMODIFIEDBYID"
								+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
								+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
								+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
								+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
								+ " WHERE R.ISSUES_NUMBER = :ISSUES_ID AND R.COMPANY_ID = :COMPANY_ID AND R.markForDelete = 0");
				
			}
			query.setParameter("ISSUES_ID", Issues_ID);
			query.setParameter("COMPANY_ID", userDetails.getCompany_id());
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			IssuesDto list;
			if (result != null) {
				list = new IssuesDto();
				
				list.setISSUES_ID((long) result[0]);
				list.setISSUES_NUMBER((long) result[1]);
				list.setISSUES_TYPE_ID((short) result[2]);
				list.setISSUES_VID((Integer) result[3]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[4]);
				list.setISSUES_VEHICLE_GROUP((String) result[5]);
				list.setISSUES_ODOMETER((Integer) result[6]);
				list.setISSUES_DRIVER_ID((Integer) result[7]);
				list.setISSUES_DRIVER_NAME((String) result[8]);
				list.setISSUES_BRANCH_ID((Integer) result[9]);
				list.setISSUES_BRANCH_NAME((String) result[10]);
				list.setISSUES_DEPARTNMENT_ID((Integer) result[11]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[12]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[13]);
				list.setISSUES_SUMMARY((String) result[14]);
				list.setISSUES_DESCRIPTION((String) result[15]);
				list.setISSUES_LABELS_ID((short) result[16]);
				list.setISSUES_REPORTED_BY((String) result[17]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[18]);
				list.setISSUES_STATUS_ID((short) result[19]);
				list.setCREATEDBY((String) result[20]);
				list.setLASTMODIFIEDBY((String) result[21]);
				list.setCREATED_DATE_ON((Date) result[22]);
				list.setLASTUPDATED_DATE_ON((Date) result[23]);
				list.setCREATEDBYID((Long) result[24]);
				list.setLASTMODIFIEDBYID((Long) result[25]);
				list.setISSUES_WORKORDER_DATE_ON((Date) result[26]);
				list.setISSUES_REPORTED_BY_ID( (Long) result[27]);
				list.setCREATEDBYID((Long) result[28]);
				list.setISSUES_ASSIGN_TO((String) result[29]);
				if(result[30] != null) {
					list.setDealerServiceEntriesId((Long) result[30]);
				}
				
			} else {
				return null;
			}
			return list;
			
		} catch (Exception e) {
			throw e;
		}
	
	}
	
	@Override
	public List<IssuesTasksDto> getIssuesTasksList(Long Issues_ID, Integer companyId) throws Exception {
		//return issuesTaskRepository.getIssuesTasksList(Issues_ID, companyId);

		/*if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return issuesRepository.get_IssuesDetailsByNumber(Issues_ID, userDetails.getCompany_id());
		}
		return issuesRepository.get_IssuesDetailsByNumber(Issues_ID, userDetails.getId(), userDetails.getCompany_id());*/

		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
						" SELECT p.ISSUES_TASK_ID, p.ISSUES_TASK_STATUS_ID, p.ISSUES_CHANGE_STATUS_ID, U.firstName, p.ISSUES_CREATEBY_ID,"
						+ " p.ISSUES_REASON, p.ISSUES_CREATED_DATE "
						+ " from IssuesTasks as p "
						+ " LEFT JOIN User U ON U.id = p.ISSUES_CREATEBY_ID"
						+ " WHERE  p.ISSUES.ISSUES_ID = :ISSUES_ID AND p.COMPANY_ID = :COMPANY_ID AND p.markForDelete = 0 ORDER BY p.ISSUES_TASK_ID DESC", Object[].class);
			
			query.setParameter("ISSUES_ID", Issues_ID);
			query.setParameter("COMPANY_ID", companyId);
			List<Object[]> results = query.getResultList();

			List<IssuesTasksDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<IssuesTasksDto>();
			IssuesTasksDto list = null;
		for (Object[] result : results) {
			if (result != null) {
				list = new IssuesTasksDto();
				
				list.setISSUES_TASK_ID((Long) result[0]);
				list.setISSUES_TASK_STATUS_ID((short) result[1]);
				list.setISSUES_CHANGE_STATUS_ID((short) result[2]);
				list.setISSUES_CREATEBY_NAME((String) result[3]);
				list.setISSUES_CREATEBY_ID((Long) result[4]);
				list.setISSUES_REASON((String) result[5]);
				list.setISSUES_CREATED_DATE_ON((Date) result[6]);
				
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
	public void transferIssuesDocument(List<IssuesDocument> list) throws Exception {
		org.fleetopgroup.persistence.document.IssuesDocument			issuesDocument		= null;
		List<org.fleetopgroup.persistence.document.IssuesDocument> 		issuesDocumentList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				issuesDocumentList	= new ArrayList<>();
				for(IssuesDocument	document : list) {
					issuesDocument	= new org.fleetopgroup.persistence.document.IssuesDocument();
					
					issuesDocument.set_id(document.getISSUE_DOCUMENTID());
					issuesDocument.setISSUE_DOCUMENTNAME(document.getISSUE_DOCUMENTNAME());
					issuesDocument.setISSUE_UPLOADDATE(document.getISSUE_UPLOADDATE());
					issuesDocument.setISSUE_FILENAME(document.getISSUE_FILENAME());
					issuesDocument.setISSUE_ID(document.getISSUE_ID());
					issuesDocument.setISSUE_CONTENT(document.getISSUE_CONTENT());
					issuesDocument.setISSUE_CONTENTTYPE(document.getISSUE_CONTENTTYPE());
					issuesDocument.setCOMPANY_ID(document.getCOMPANY_ID());
					issuesDocument.setCREATEDBYID(document.getCREATEDBYID());
					issuesDocument.setMarkForDelete(document.isMarkForDelete());
					
					issuesDocumentList.add(issuesDocument);
				}
				mongoTemplate.insert(issuesDocumentList, org.fleetopgroup.persistence.document.IssuesDocument.class);
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}

	@Override
	public void transferIssuesPhotos(List<IssuesPhoto> list) throws Exception {
		org.fleetopgroup.persistence.document.IssuesPhoto				issuesPhoto		= null;
		List<org.fleetopgroup.persistence.document.IssuesPhoto> 		issuesPhotoList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				issuesPhotoList	= new ArrayList<>();
				for(IssuesPhoto	document : list) {
					issuesPhoto	= new org.fleetopgroup.persistence.document.IssuesPhoto();
					
					issuesPhoto.set_id(document.getISSUE_PHOTOID());
					issuesPhoto.setISSUE_PHOTONAME(document.getISSUE_PHOTONAME());
					issuesPhoto.setISSUE_UPLOADDATE(document.getISSUE_UPLOADDATE());
					issuesPhoto.setISSUE_FILENAME(document.getISSUE_FILENAME());
					issuesPhoto.setISSUE_ID(document.getISSUE_ID());
					issuesPhoto.setISSUE_CONTENT(document.getISSUE_CONTENT());
					issuesPhoto.setISSUE_CONTENTTYPE(document.getISSUE_CONTENTTYPE());
					issuesPhoto.setCOMPANY_ID(document.getCOMPANY_ID());
					issuesPhoto.setCREATEDBYID(document.getCREATEDBYID());
					issuesPhoto.setMarkForDelete(document.isMarkForDelete());
					
					issuesPhotoList.add(issuesPhoto);
				}
				mongoTemplate.insert(issuesPhotoList, org.fleetopgroup.persistence.document.IssuesPhoto.class);
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}
	
	@Override
	public long getIssuesDocumentMaxId() throws Exception {
		try {
			return issuesDocumentRepository.getIssuesDocumentMaxId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public long getIssuesPhotoMaxId() throws Exception {
		try {
			return issuesPhotoRepository.getIssuesPhotoMaxId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public List<IssuesDto> getIssuesByDriver(Integer did, String dateRangeFrom,
			String dateRangeTo) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_DESCRIPTION, I.ISSUES_REPORTED_DATE, "
							+ " D.driver_firstname, IC.ISSUE_COMMENT,IC.ISSUE_TITLE, IC.CREATED_DATE , D.driver_fathername ,D.driver_Lastname FROM Issues I "
							+ " INNER JOIN Driver AS D ON D.driver_id = I.ISSUES_DRIVER_ID"
							+ " INNER JOIN IssuesComment as IC ON IC.ISSUES_ID = I.ISSUES_ID"
							+ " where I.ISSUES_DRIVER_ID = "+did+" AND I.ISSUES_REPORTED_DATE BETWEEN '" + dateRangeFrom + "' AND  '"
							+ dateRangeTo + "'  ORDER BY I.ISSUES_REPORTED_DATE desc ",	
							Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<IssuesDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					IssuesDto	issue = new IssuesDto();
					
					
					issue.setISSUES_ID((long) result[0]);
					issue.setISSUES_NUMBER((long) result[1]);
					issue.setISSUES_DESCRIPTION((String) result[2]);
					
					issue.setISSUES_REPORTED_DATE_ON((Date) result[3]);
					if(issue.getISSUES_REPORTED_DATE_ON() != null) {
						issue.setCREATED_DATE(issue.getISSUES_REPORTED_DATE_ON()+"");
					 }
					
					issue.setISSUES_DRIVER_NAME((String) result[4]);
					issue.setIssueComment_Comment((String) result[5]);
					issue.setIssueComment_Title((String) result[6]);
					
					issue.setIssueComment_CreatedDate((Date) result[7]);
					if(issue.getIssueComment_CreatedDate() != null) {
						issue.setIssueComment_CreatedDateStr(issue.getIssueComment_CreatedDate()+"");
					 }
					if(result[8] != null && !((String) result[8]).trim().equals("")) {
						issue.setISSUES_FATHER_NAME(" - "+result[8]);
					}else {
						issue.setISSUES_FATHER_NAME(" ");
					}
				
					issue.setIssueDriverLastName((String)result[9]);
				
					list.add(issue);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Long getTotalIssuesCreatedCount(String startDate, String endDate, Integer companyId ,String vGroupQuery) throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				"SELECT COUNT(I.ISSUES_ID) "
						+ " From Issues as I "
						+ " LEFT JOIN Vehicle V ON V.vid = I.ISSUES_VID "
						+ " WHERE I.ISSUES_REPORTED_DATE between '"+startDate+"' AND '"+endDate+"' "
						+ " AND I.markForDelete = 0 AND V.vStatusId <> 4 AND I.COMPANY_ID = "+companyId+" "+vGroupQuery+" ",
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
	public HashMap<Integer, Long> issuesCreatedOnDate(String startDate, String endDate) throws Exception {
		TypedQuery<Object[]> 	typedQuery = null;
		HashMap<Integer, Long>	map		   = null;
		
		typedQuery = entityManager.createQuery(
				"SELECT COUNT(WO.ISSUES_ID), WO.COMPANY_ID "
						+ " From Issues as WO "
						+ " LEFT JOIN Vehicle V ON V.vid = WO.ISSUES_VID "
						+ " WHERE WO.ISSUES_REPORTED_DATE between '"+startDate+"' AND '"+endDate+"' "
						+ " AND WO.markForDelete = 0 AND V.vStatusId <> 4 GROUP BY WO.COMPANY_ID ",
						Object[].class);
		
		List<Object[]> results = typedQuery.getResultList();

		if (results != null && !results.isEmpty()) {
			
			map	= new HashMap<>();
			
			for (Object[] result : results) {
				
				map.put((Integer)result[1], (Long)result[0]);
				
			}
		}
		
		return map;
	}
	
	@Override
	public HashMap<Integer, Long> issuesResolvedOnDate(String startDate, String endDate) throws Exception {
		TypedQuery<Object[]> 	typedQuery = null;
		HashMap<Integer, Long>	map		   = null;
		
		typedQuery = entityManager.createQuery(
				"SELECT COUNT(WO.ISSUES_ID), WO.COMPANY_ID "
						+ " From Issues as WO "
						+ " LEFT JOIN Vehicle V ON V.vid = WO.ISSUES_VID "
						+ " WHERE WO.LASTUPDATED_DATE between '"+startDate+"' AND '"+endDate+"' AND V.vStatusId <> 4 "
						+ " AND WO.ISSUES_STATUS_ID IN ("+IssuesTypeConstant.ISSUES_STATUS_CLOSED+", "+IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED+")  AND WO.markForDelete = 0 GROUP BY WO.COMPANY_ID ",
						Object[].class);
		
		List<Object[]> results = typedQuery.getResultList();

		if (results != null && !results.isEmpty()) {
			
			map	= new HashMap<>();
			
			for (Object[] result : results) {
				
				map.put((Integer)result[1], (Long)result[0]);
				
			}
		}
		
		return map;
	}
	
	@Override
	public Long getIssuesAllopenCount(Integer companyId,String vGroupQuery) throws Exception {
		
		Query queryt = 	null;
		
		queryt = entityManager.createQuery(
				"SELECT COUNT(I.ISSUES_ID) "
						+ " From Issues as I "
						+ " LEFT JOIN Vehicle V ON V.vid = I.ISSUES_VID "
						+ " WHERE  I.ISSUES_STATUS_ID IN ("+IssuesTypeConstant.ISSUES_STATUS_OPEN+", "+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+")  "
						+ " AND I.markForDelete = 0 AND V.vStatusId <> 4  AND I.COMPANY_ID = "+companyId+" "+vGroupQuery+" ",
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
	public HashMap<Integer, Long> issuesAllopenData() throws Exception {
		TypedQuery<Object[]> 	typedQuery = null;
		HashMap<Integer, Long>	map		   = null;
		
		typedQuery = entityManager.createQuery(
				"SELECT COUNT(WO.ISSUES_ID), WO.COMPANY_ID "
						+ " From Issues as WO "
						+ " LEFT JOIN Vehicle V ON V.vid = WO.ISSUES_VID "
						+ " WHERE  WO.ISSUES_STATUS_ID IN ("+IssuesTypeConstant.ISSUES_STATUS_OPEN+", "+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+")  "
						+ " AND WO.markForDelete = 0 AND V.vStatusId <> 4  GROUP BY WO.COMPANY_ID ",
						Object[].class);
		
		List<Object[]> results = typedQuery.getResultList();

		if (results != null && !results.isEmpty()) {
			
			map	= new HashMap<>();
			
			for (Object[] result : results) {
				
				map.put((Integer)result[1], (Long)result[0]);
				
			}
		}
		
		return map;
	}
	
	@Override
	public Long getIssuesOpenCountBetweenDates(String startDate, String endDate, Integer companyId ,String vGroupQuery) throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				"SELECT COUNT(I.ISSUES_ID) "
						+ " From Issues as I "
						+ " LEFT JOIN Vehicle V ON V.vid = I.ISSUES_VID "
						+ " WHERE I.ISSUES_REPORTED_DATE between '"+startDate+"' AND '"+endDate+"' AND "
						+ " I.ISSUES_STATUS_ID IN ("+IssuesTypeConstant.ISSUES_STATUS_OPEN+", "+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+", "+IssuesTypeConstant.ISSUES_STATUS_SE_CREATED+") "
						+ " AND I.markForDelete = 0 AND I.COMPANY_ID = "+companyId+" AND V.vStatusId <> 4 "+vGroupQuery+" ",
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
	public HashMap<Integer, Long> issuesOpenWithinDays(String startDate, String endDate) throws Exception {
		TypedQuery<Object[]> 	typedQuery = null;
		HashMap<Integer, Long>	map		   = null;
		
		typedQuery = entityManager.createQuery(
				"SELECT COUNT(WO.ISSUES_ID), WO.COMPANY_ID "
						+ " From Issues as WO "
						+ " WHERE WO.CREATED_DATE between '"+startDate+"' AND '"+endDate+"' AND WO.ISSUES_STATUS_ID IN ("+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+", "+IssuesTypeConstant.ISSUES_STATUS_OPEN+")  AND WO.markForDelete = 0 GROUP BY WO.COMPANY_ID ",
						Object[].class);
		
		List<Object[]> results = typedQuery.getResultList();

		if (results != null && !results.isEmpty()) {
			
			map	= new HashMap<>();
			
			for (Object[] result : results) {
				
				map.put((Integer)result[1], (Long)result[0]);
				
			}
		}
		
		return map;
	}
	
	@Override
	public Long getIssuesOpenCountByDate(String startDate, Integer companyId ,String vGroupQuery) throws Exception {
		
		Query queryt = 	null;
	
		queryt = entityManager.createQuery(
				"SELECT COUNT(I.ISSUES_ID) "
						+ " From Issues as I "
						+ " LEFT JOIN Vehicle V ON V.vid = I.ISSUES_VID "
						+ " WHERE I.ISSUES_REPORTED_DATE <= '"+startDate+"' AND "
						+ " I.ISSUES_STATUS_ID IN ("+IssuesTypeConstant.ISSUES_STATUS_OPEN+", "+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+", "+IssuesTypeConstant.ISSUES_STATUS_SE_CREATED+") "
						+ " AND V.vStatusId <> 4  AND I.markForDelete = 0 AND I.COMPANY_ID = "+companyId+" "+vGroupQuery+" ",
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
	public HashMap<Integer, Long> issuesOpen15MoreDays(String startDate) throws Exception {
		TypedQuery<Object[]> 	typedQuery = null;
		HashMap<Integer, Long>	map		   = null;
		
		typedQuery = entityManager.createQuery(
				"SELECT COUNT(WO.ISSUES_ID), WO.COMPANY_ID "
						+ " From Issues as WO "
						+ " LEFT JOIN Vehicle V ON V.vid = WO.ISSUES_VID "
						+ " WHERE WO.CREATED_DATE <= '"+startDate+"' AND WO.ISSUES_STATUS_ID IN "
						+ " ("+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+", "+IssuesTypeConstant.ISSUES_STATUS_OPEN+") "
						+ " AND V.vStatusId <> 4  AND WO.markForDelete = 0 GROUP BY WO.COMPANY_ID ",
						Object[].class);
		
		List<Object[]> results = typedQuery.getResultList();

		if (results != null && !results.isEmpty()) {
			
			map	= new HashMap<>();
			
			for (Object[] result : results) {
				
				map.put((Integer)result[1], (Long)result[0]);
				
			}
		}
		
		return map;
	}
	
	public long issuesCountByStatus(String	startDate, String endDate,short status, Integer companyID ,String vGroupQuery ) throws Exception {
		TypedQuery<Object[]> 	typedQuery 	= null;
		long 					count		= 0;
		
		try {
			typedQuery = entityManager.createQuery(
					"SELECT COUNT(I.ISSUES_ID), I.COMPANY_ID "
							+ " From Issues as I "
							+ " LEFT JOIN Vehicle V ON V.vid = I.ISSUES_VID "
							+ " WHERE  I.ISSUES_STATUS_ID ="+status+" AND "
							+ " I.ISSUES_REPORTED_DATE between '"+startDate+"' AND '"+endDate+"' AND I.COMPANY_ID = "+companyID+" AND  "
							+ " I.markForDelete = 0 AND V.vStatusId <> 4 "+vGroupQuery+" ",
							Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();
			
			if (results != null && !results.isEmpty()) {
				for (Object[] result : results) {
					count = (Long)result[0];
				}
			}
			return count;
		}catch(Exception e){
			throw e;
		}
	}
	
	@Override
	public long issuesCountByInProcessStatus(String	startDate, String endDate, Integer companyID,String vGroupQuery ) throws Exception {
		TypedQuery<Object[]> 	typedQuery 	= null;
		long 					count		= 0;
	
		try {
			typedQuery = entityManager.createQuery(
					"SELECT COUNT(I.ISSUES_ID), I.COMPANY_ID "
							+ " From Issues as I "
							+ " LEFT JOIN Vehicle V ON V.vid = I.ISSUES_VID "
							+ " WHERE  I.ISSUES_STATUS_ID IN("+IssuesTypeConstant.ISSUES_CHANGE_STATUS_WOCREATED+", "+IssuesTypeConstant.ISSUES_STATUS_SE_CREATED+")  "
							+ " AND I.ISSUES_REPORTED_DATE between '"+startDate+"' AND '"+endDate+"' AND I.COMPANY_ID = "+companyID+" AND  "
							+ " I.markForDelete = 0 AND V.vStatusId <> 4 "+vGroupQuery+" ",
							Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();
			
			if (results != null && !results.isEmpty()) {
				for (Object[] result : results) {
					count = (Long)result[0];
				}
			}
			return count;
		}catch(Exception e){
			throw e;
		}
	}
	
	public long issuesResolveCount(String	startDate, String endDate,short status, Integer companyId ) throws Exception {
		TypedQuery<Object[]> 	typedQuery 	= null;
		long 					count		= 0;
		try {
			typedQuery = entityManager.createQuery(
					"SELECT COUNT(I.ISSUES_ID), I.COMPANY_ID "
							+ " From Issues as I "
							+ " LEFT JOIN Vehicle V ON V.vid = I.ISSUES_VID "
							+ " WHERE  I.ISSUES_STATUS_ID ="+status+" AND "
							+ " I.LASTUPDATED_DATE between '"+startDate+"' AND '"+endDate+"' AND I.COMPANY_ID = "+companyId+" AND  "
							+ " V.vStatusId <> 4 AND I.markForDelete = 0 ",
							Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();
			
			if (results != null && !results.isEmpty()) {
				for (Object[] result : results) {
					count = (Long)result[0];
				}
			}
			return count;
		}catch(Exception e){
			throw e;
		}
	}
	public long issuesOverDueCount(String todaysDate, Integer companyID,String vGroupQuery) throws Exception {
		TypedQuery<Object[]> 	typedQuery = null;
		long count= 0;
	
		typedQuery = entityManager.createQuery(
				"SELECT COUNT(I.ISSUES_ID),I.COMPANY_ID "
						+ " From Issues as I "
						+ " LEFT JOIN Vehicle V ON V.vid = I.ISSUES_VID "
						+ " WHERE I.ISSUES_REPORTED_DATE <= '"+todaysDate+"' AND "
						+ " I.ISSUES_STATUS_ID IN ("+IssuesTypeConstant.ISSUES_STATUS_OPEN+", "+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+", "+IssuesTypeConstant.ISSUES_STATUS_SE_CREATED+") "
						+ " AND V.vStatusId <> 4 AND I.COMPANY_ID ="+companyID+" AND I.markForDelete = 0 "+vGroupQuery+" ",
						Object[].class);
		
		List<Object[]> results = typedQuery.getResultList();
		
		if (results != null && !results.isEmpty()) {
			
			
			for (Object[] result : results) {
				
				count = (Long)result[0];
				
			}
		}
		
		return count;
	}
	
	public List<IssuesDto> getIssueDetailsBetweenDatesByStatus(Integer companyID, String issueStatusQuery,String vGroupQuery) throws Exception {
		TypedQuery<Object[]> 	typedQuery 					= null;
		IssuesDto 				list 						= null;
		List<IssuesDto> 		Dtos 						= null;
		String 					todaysDate					= null;
		try {
			todaysDate	= format2.format(new Date());
			
			typedQuery = entityManager.createQuery(
					"SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.CREATED_DATE, V.vehicle_registration, VG.vGroup, "
							+ " I.ISSUES_STATUS_ID, D.driver_firstname, I.ISSUES_VID, I.ISSUES_REPORTED_DATE,I.ISSUES_SUMMARY "
							+ " From Issues as I "
							+ " LEFT JOIN Vehicle V ON V.vid = I.ISSUES_VID"
							+ " LEFT JOIN Driver D ON D.driver_id = I.ISSUES_DRIVER_ID "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " WHERE "+issueStatusQuery+" AND "
							+ " I.COMPANY_ID ="+companyID+" AND I.markForDelete = 0 AND V.vStatusId <> 4 "+vGroupQuery+"  "
							,Object[].class);
			
			List<Object[]> results = typedQuery.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<>();
				
				for(Object[] result : results) {
					list = new IssuesDto();
					
					list.setISSUES_ID((Long)result[0]);
					list.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + list.getISSUES_ID()));
					list.setISSUES_NUMBER((Long)result[1]);
					list.setCREATED_DATE(sqldateTime.format((Timestamp)result[2]));
					if(result[3] != null) {
						list.setISSUES_VEHICLE_REGISTRATION((String)result[3]);
					}else {
						list.setISSUES_VEHICLE_REGISTRATION("-");
					}
					list.setISSUES_VEHICLE_GROUP((String)result[4]);
					list.setISSUES_STATUS_ID((short)result[5]);
					list.setISSUES_STATUS(IssuesTypeConstant.getStatusName((short)result[5]));
					if(result[6] != null) {
						list.setISSUES_DRIVER_NAME((String)result[6]);
					}else {
						list.setISSUES_DRIVER_NAME("-");
					}
					if(result[7] != null)
					list.setISSUES_VID((Integer)result[7]);
					list.setISSUES_REPORTED_DATE(sqldateTime.format((Timestamp)result[8]));
					list.setAgeing(DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getTimeStamp(result[8]+"",DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(todaysDate+DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)));
					list.setISSUES_SUMMARY((String) result[9]);
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			System.err.println("ERR"+e);
			throw e;
			
		}
	}
	
	//Latest Code
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject saveIssuesServiceEntries(ValueObject valueObject) throws Exception {
		
		Map<String, Object> 			model 								= new HashMap<String, Object>();
		List<ServiceEntries> 			validateSE							= null;
		CustomUserDetails 				userDetails 						= null;
		HashMap<String, Object> 		configuration						= null;
		boolean 						ServiceEntriesStatus 				= false;
		Integer							companyId							= 0;
		HashMap<Long, ServiceReminderDto> serviceReminderHM 				= null;
		ServiceReminderDto				serviceReminderDto 					= null;
		Integer							workOrderSubTaskId					= 0;
		List<ServiceReminderDto>		serviceList							= null;
		Integer							    vid				    			= 0 ;
		Integer 							vendorId						= 0 ;
		String							invoiceNumber						= null;
		String							newVendorId							= null;
		String							newVendorLocationId					= null;
		ArrayList<ValueObject> 			dataArrayObjColl 					= null;
		JobType 						getJobTypeName 						= null; //zorro
		JobSubType		 				validateSubType						= null;
		String[] 						serviceReminderArr					= null;
		VehicleDto 						CheckVehicleStatus					= null;
		Vendor 							vendor								= null;
		VendorType 						VenType								= null;
		ServiceEntriesSequenceCounter 	counter								= null;
		ServiceEntriesTasks 			serviceEntriesTask					= null;
		ServiceEntries 					serviceEntries						= null;	
		VendorSequenceCounter			vendorSequenceCounter				= null;
		JobSubType 						jobSubType							= null;
		ServiceEntriesTasks 			serviceEntriesTaskForReminder		= null;
		IssuesTasks 					issuesTasks							= null;
		
		try {
			dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("serviceEntriesDetails");
			
			vid								= valueObject.getInt("vid", 0);
			vendorId						= valueObject.getInt("vendorId", 0);
			invoiceNumber					= valueObject.getString("invoiceNumberId");
			newVendorId						= valueObject.getString("newVendorId");
			newVendorLocationId				= valueObject.getString("newVendorLocationId");
			
			serviceReminderArr	= valueObject.getString("serviceReminder").split(",");
			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			
			if((boolean) configuration.getOrDefault(VendorTypeConfigurationConstants.USE_COMMON_VENDOR_TYPE, true)) {
				companyId			= 0;
			} else {
				companyId			= userDetails.getCompany_id();
			}
			String TIDMandatory = "";

			// Check Vehicle Status Current IN ACTIVE OR NOT
			 CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(vid);   //mods  vid in bracket
			if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE) {
				ServiceEntriesStatus = true;
			} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
				ServiceEntriesStatus = true;
			}

			if (ServiceEntriesStatus) {
				
				validateSE = ServiceEntriesService.validate_ServiceEntries(vendorId, invoiceNumber, vid, userDetails.getCompany_id());  //mods
				
				if((boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_ADD_INVOICE_DEATILS, false)) {
					validateSE.clear();
				}
				
				if (validateSE != null && !validateSE.isEmpty()) {
					model.put("already", true);
					//return new ModelAndView("redirect:/addServiceEntries.in", model);		//original commented
					valueObject.put("already", true);
					//return valueObject;	  //jhol 
				} else {
					serviceEntries = serviceEntriesBL.prepareServiceEntriesInfo(valueObject);		//devy
					
					if(serviceEntries.getVendor_id() == 0) {
						vendor 					= vendorBL.prepareNewVendorFromIssueServiceEntries(newVendorId,newVendorLocationId,userDetails.getCompany_id());
						
						vendorSequenceCounter  	= vendorSequenceService.findNextVendorNumber(companyId);
						vendor.setVendorNumber((int) vendorSequenceCounter.getNextVal());
						
						VenType 				= VendorTypeService.getVendorType("SERVICE-VENDOR", companyId);
						if(VenType != null) {
							vendor.setVendorTypeId(VenType.getVendor_Typeid());
						}else {
							vendor.setVendorTypeId(0);
						}
						vendorService.addVendor(vendor);
						serviceEntries.setVendor_id(vendor.getVendorId());
					}
					counter = serviceEntriesSequenceService.findNextServiceEntries_Number(userDetails.getCompany_id());
					serviceEntries.setServiceEntries_Number(counter.getNextVal());
					ServiceEntriesService.addServiceEntries(serviceEntries); // save 
					model.put("saveServiceEntries", true);
					
					if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
						int i = 1;
						for (ValueObject object : dataArrayObjColl) {
							serviceEntriesTask = serviceEntriesBL.prepareServiceEntriesTaskInfo(object,serviceEntries, userDetails.getCompany_id());	
							
							if(object.getInt("serviceSubJobs") > 0) {
								serviceEntriesTask.setService_subtypetask_id(object.getInt("serviceSubJobs"));
							}else {
								getJobTypeName = JobTypeService.getJobType(object.getInt("jobType"), userDetails.getCompany_id());
								validateSubType = JobSubTypeService.getJobSubTypeByJobROTAndJobType("" + object.getString("service_ROT"), "" +  getJobTypeName.getJob_Type(), userDetails.getCompany_id());
								
								if (validateSubType != null) {
									serviceEntriesTask.setService_subtypetask_id(validateSubType.getJob_Subid());
								} else {
									jobSubType = jobSubTypeBL.prepareNewJobSubTypeFromIssueServiceEntries(getJobTypeName.getJob_id(),getJobTypeName.getJob_Type(),object, userDetails );
									JobSubTypeService.addJobSubType(jobSubType);
									serviceEntriesTask.setService_subtypetask_id(jobSubType.getJob_Subid());
								}
								
							}
							
							if(i < serviceReminderArr.length) {
								serviceEntriesTask.setService_id(Long.parseLong(serviceReminderArr[i-1]));
							}else {
								serviceEntriesTask.setService_id(null);
							}
							i++;
						
						ServiceEntriesService.addServiceEntriesTask(serviceEntriesTask);
						}
					}
					
					if(valueObject.getString("serviceSubJobsId").length() > 0 ) {
						if(!valueObject.getString("serviceSubJobsId").isEmpty()) {
							workOrderSubTaskId = Integer.parseInt(valueObject.getString("serviceSubJobsId"));
						}
					}
					
					if (serviceReminderArr != null ) {
						for (int j = 0; j < serviceReminderArr.length; j++) {
						
							if(serviceReminderArr[j] != null && !serviceReminderArr[j].isEmpty()) {
						serviceReminderHM 	= WorkOrdersService.getJobtypeAndSubtypeFromServiceReminderId(serviceReminderArr[j], userDetails.getCompany_id());
						serviceReminderDto	= serviceReminderHM.get(Long.parseLong(serviceReminderArr[j]));
						if(!workOrderSubTaskId.equals(serviceReminderDto.getServiceSubTypeId())) {
							serviceEntriesTaskForReminder = serviceEntriesBL.prepareServiceEntriesTaskForReminder(serviceReminderDto,serviceEntries,userDetails,serviceReminderArr[j]);
							ServiceEntriesService.addServiceEntriesTask(serviceEntriesTaskForReminder);
						}
						}
					}
					}
					
					//Issues Logic By Devy Start latesty
					if (valueObject.getLong("ISSUES_ID") != 0) {
						try {
							Issues Save_Issues = new Issues();
							Save_Issues.setISSUES_ID(valueObject.getLong("ISSUES_ID"));
						
							issuesTasks =	issuesBL.prepareIssueTaskForServiceEnteries(valueObject,Save_Issues, userDetails);
							
							registerNew_IssuesTasks(issuesTasks);
							
							update_Create_SE_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_SE_CREATED, userDetails.getId(), issuesTasks.getISSUES_CREATED_DATE(),
									serviceEntries.getServiceEntries_id(), valueObject.getLong("ISSUES_ID"));
							
							
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
					//Issues Logic By Devy Stop
					
					// update the Current Odometer vehicle Databases tables
					try {
						Integer currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(serviceEntries.getVid());
						
						if(currentODDMETER != null && serviceEntries.getVehicle_Odometer() != null ) {
							if (currentODDMETER < serviceEntries.getVehicle_Odometer()) {
								
								vehicleService.updateCurrentOdoMeter(serviceEntries.getVid(), serviceEntries.getVehicle_Odometer(), userDetails.getCompany_id());
								// update current Odometer update Service Reminder
								ServiceReminderService.updateCurrentOdometerToServiceReminder(serviceEntries.getVid(),
										serviceEntries.getVehicle_Odometer(), userDetails.getCompany_id());
								
								serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(serviceEntries.getVid(), userDetails.getCompany_id(), userDetails.getId());
								if(serviceList != null) {
									for(ServiceReminderDto list : serviceList) {
										
										if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
											
											ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
											//emailAlertQueueService.sendEmailServiceOdometer(list);
											//smsAlertQueueService.sendSmsServiceOdometer(list);
										}
									}
								}
								
								VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToServiceEntries(serviceEntries);
								vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
								vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
							}
						}
					} catch (Exception e) {
						LOGGER.error("Renewal Reminder:", e);
						e.printStackTrace();

					}
				}

			} // checking Vehicle Status
			else {
				String Link = "";
				if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
					Link += "  <a href=\"getTripsheetDetails.in?tripSheetID=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">TS-"
							+ driverService.getCurrentTripSheetNumber(CheckVehicleStatus.getTripSheetID(),
									userDetails.getCompany_id())
							+ "  <i class=\"fa fa-external-link\"></i></a>";

				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					Link += " <a href=\"showWorkOrder?woId=" + CheckVehicleStatus.getTripSheetID()
							+ "\" target=\"_blank\">WO-" + WorkOrdersService
									.getWorkOrders(CheckVehicleStatus.getTripSheetID()).getWorkorders_Number()
							+ "  <i class=\"fa fa-external-link\"></i></a> ";

				}

				TIDMandatory += " <span> This " + CheckVehicleStatus.getVehicle_registration() + " Vehicle in "
						+ CheckVehicleStatus.getVehicle_Status() + " Status you can't create WorkOrder " + " " + Link
						+ "" + " </span> ,";
				
				valueObject.put("VMandatory", TIDMandatory);
				//redir.addFlashAttribute("VMandatory", TIDMandatory);  //Original Code Commented
				//model.put("closeStatus", true);
				//return new ModelAndView("redirect:/addServiceEntries.in", model);   //Original Code Commented
			}

		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
		}
		return valueObject;  //jhol devy
	}
	
	@Transactional
	public void update_Create_SE_Issues_Status(short Status, Long CloseBy, Date close_date, Long SE_ID,
			Long Issues_ID)throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			
			issuesRepository.update_Create_SE_Issues_Status(Status, CloseBy, close_date, SE_ID, Issues_ID, userDetails.getCompany_id());
		}catch(Exception e) {
			System.err.println("err"+e);
		}
	}
	
	@Transactional
	public List<IssuesDto> find_Issues_SE_Created_Status(Integer pageNumber, CustomUserDetails userDetails, short status) throws Exception {
		
		TypedQuery<Object[]> 			queryt 			= null;
		List<IssuesDto> 				Dtos 			= null;
		IssuesDto 						list 			= null;
		
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER ,D.driver_Lastname"
							+ " ,D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " Where R.ISSUES_STATUS_ID= "+status+" AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0  ORDER BY R.ISSUES_ID desc",
					Object[].class);
		}else {
			
			queryt = entityManager.createQuery(
					"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
							+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
							+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER ,D.driver_Lastname"
							+ " ,D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName FROM Issues AS R "
							+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
							+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
							+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
							+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
							+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
							+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
							+ " LEFT JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " Where R.ISSUES_STATUS_ID= "+status+" AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0  ORDER BY R.ISSUES_ID desc",
							Object[].class);
		}
		queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		queryt.setMaxResults(PAGE_SIZE);

		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos 	= new ArrayList<IssuesDto>();
			
			for (Object[] result : results) {
				list = new IssuesDto();
				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_SUMMARY((String) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
				list.setISSUES_VEHICLE_GROUP((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_TYPE_ID((short) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_DRIVER_NAME((String) result[10]);
				list.setISSUES_BRANCH_ID((Integer) result[11]);
				list.setISSUES_BRANCH_NAME((String) result[12]);
				list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
				list.setCREATED_DATE_ON((Date) result[14]);
				list.setLASTUPDATED_DATE_ON((Date) result[15]);
				list.setISSUES_STATUS_ID((short) result[16]);
				list.setISSUES_NUMBER((Long) result[17]);
				if(result[18] != null && !((String)result[18]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[18]);
					if(result[19] != null && !((String)result[19]).trim().equals(""))
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[19]);
					
					list.setVehicleType((String) result[20]);
					
					list.setVehicleMaker((String) result[21]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	@Transactional
	public IssuesDto GET_SE_ID_TO_ISSUES_DETAILS(Long SE_id, Integer companyId) {

		Query query = entityManager
				.createQuery("SELECT R.ISSUES_ID, R.ISSUES_STATUS_ID, R.ISSUES_SE_ID FROM Issues AS R "
						+ "Where R.ISSUES_SE_ID=" + SE_id + " AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0");

		Object[] Issues = null;
		try {
			Issues = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		IssuesDto list;
		if (Issues != null) {
			list = new IssuesDto();
			list.setISSUES_ID((Long) Issues[0]);
			list.setISSUES_STATUS(IssuesTypeConstant.getIssuesTaskStatusName((short) Issues[1]) );
			list.setISSUES_SE_ID((Long) Issues[2]);

		} else {
			return null;
		}
		return list;

	}
	
	@Transactional
	public void changeIssueStatusFromSE_createdToOpen(Long serviceEntriesId,short issueStatus,Long issuesId , CustomUserDetails userDetails) {
		try {
			issuesRepository.changeIssueStatusFromSE_createdToOpen(serviceEntriesId,issueStatus,issuesId,userDetails.getCompany_id());
		}catch(Exception e) {
			System.err.println("err"+e);
		}
	}
	
	@Override
	public ValueObject saveIssuesDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails					= null;
		Issues						issues						= null;
		Issues						savedIssues					= null;
		IssuesSequenceCounter		sequenceCounter 			= null;
		IssuesTasks 				issueTasks 					= null;
		UserProfileDto 				userProfile 				= null;
		Date 						currentDate  				= null;
		Timestamp 					currnetTimeStamp 			= null;	
		Integer 					currentOdometer				= 0;
		List<ServiceReminderDto>	serviceList					= null;
		VehicleOdometerHistory 		vehicleUpdateHistory 		= null;
		int							companyId					= 0;
		HashMap<String, Object>   	configuration			    = null;
		boolean                     allowedMobNotification      = false;
		VehicleDto 					checkVehicleStatus 			= null;
		try {
			companyId               = valueObject.getInt("companyId",0);
			currentDate 			= new Date();                                             
			currnetTimeStamp 		= new java.sql.Timestamp(currentDate.getTime()); 
			issueTasks 				= new IssuesTasks();
			userDetails				= (CustomUserDetails) valueObject.get("userDetails");
			if(valueObject.getString("companyReference","") != "") {
				Vehicle veh = VehicleRepository.getVehicelReg(valueObject.getString("vehicleNumber"));
				
				if(veh != null) {
					valueObject.put("vid", veh.getVid());
				} else {
					valueObject.put("vehicleNotFound", true);
					return valueObject;
				}
				
			}
			if(valueObject.getInt("vid",0) > 0) {
				if(companyId > 0) {
					checkVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("vid"),companyId);
				}else {
					checkVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("vid"));
				}
				if (checkVehicleStatus != null && (checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE  || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SURRENDER || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SOLD )) {
					
					valueObject.put("vehicleNotActive", true);
					valueObject.put("vehicleStatus", VehicleStatusConstant.getVehicleStatus(checkVehicleStatus.getvStatusId()));
					return valueObject;
				} 
			}
			
			if(valueObject.getShort("issueType",(short)0) == IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN) {
				if(valueObject.getInt("replacedVehicle",0) == valueObject.getInt("vid",0)) {
					valueObject.put("replaceVehicleAndIssueVehicleSame", true);
					return valueObject;
				}
				checkVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("replacedVehicle"),userDetails.getCompany_id());
				if (checkVehicleStatus != null && (checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE  || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SURRENDER || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SOLD )) {
					
					valueObject.put("replaceVehicleNotActive", true);
					valueObject.put("replaceVehicleStatus", VehicleStatusConstant.getVehicleStatus(checkVehicleStatus.getvStatusId()));
					return valueObject;
				} 
				
			}
			
			
			userProfile 			= userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getId());
			companyId				= userDetails.getCompany_id();
			configuration	        = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			sequenceCounter 		= issuesSequenceService.findNextIssuesSequence_Number(companyId);
			allowedMobNotification  = (boolean) configuration.getOrDefault(IssuesConfigurationContant.ALLOWED_MOB_NOTIFICATION, false);
			if(sequenceCounter == null) {
				valueObject.put("sequenceNotFound", true);
				return valueObject;
			}
			
			valueObject.put("issuesNumber", sequenceCounter.getNextVal());  
			issues = issuesBL.prepareIssuesDetails(valueObject, userDetails);
			
			if(valueObject.getInt("vid",0) != 0) {
				currentOdometer = vehicleService.updateCurrentOdoMeterGetVehicleCurrentOdometer(valueObject.getInt("vid"), companyId);
				issues.setVehicleCurrentOdometer(currentOdometer);
			}else {
				issues.setVehicleCurrentOdometer(0);
			}
			
			if(issues != null) {
				savedIssues = issuesRepository.save(issues);
				valueObject.put("savedIssues", savedIssues);
				if(allowedMobNotification){
					String msg =savedIssues.getISSUES_SUMMARY();
					sendNotification(valueObject,savedIssues,msg);
				}
				valueObject.put("issueId", AESEncryptDecrypt.encryptBase64("" + savedIssues.getISSUES_ID()));
				valueObject.put("plainIssueId",  savedIssues.getISSUES_ID());
				valueObject.put("save", true);
				valueObject.put("decryptIssueId", savedIssues.getISSUES_ID());
				
				if(issues.getISSUE_TYPE_ID() == IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN) {
					valueObject.put("fromSave", true);
					valueObject.put("userDetails", userDetails);
					issueBreakDownDetailsRepository.save(IssuesBL.getIssueBreakDownDetailsDTO(valueObject, savedIssues));
				}
			}
			
			issueTasks.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_CREATE);
			issueTasks.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN);
			issueTasks.setISSUES_CREATED_DATE(currnetTimeStamp);
			issueTasks.setISSUES(savedIssues);
			issueTasks.setCOMPANY_ID(companyId);
			issueTasks.setISSUES_CREATEBY_ID(userProfile.getUserprofile_id());
			
			issuesService.registerNew_IssuesTasks(issueTasks);
			if (savedIssues.getISSUES_VID() != null && savedIssues.getISSUES_VID() != 0) {
				currentOdometer = vehicleService.updateCurrentOdoMeterGetVehicleCurrentOdometer(savedIssues.getISSUES_VID(), companyId);
			if(savedIssues.getISSUES_VID() != 0 && currentOdometer != null && savedIssues.getISSUES_ODOMETER() != null) {
				if(currentOdometer < savedIssues.getISSUES_ODOMETER()) {
					vehicleService.updateCurrentOdoMeter(savedIssues.getISSUES_VID(), savedIssues.getISSUES_ODOMETER(), companyId);
					ServiceReminderService.updateCurrentOdometerToServiceReminder(savedIssues.getISSUES_VID(), savedIssues.getISSUES_ODOMETER(), companyId);
					
					vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToIssues(savedIssues);
					vehicleUpdateHistory.setCompanyId(companyId);
					vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					
					serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(savedIssues.getISSUES_VID(), companyId, userDetails.getId());
					
					if(serviceList != null) {
						for(ServiceReminderDto list : serviceList) {
							if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
								ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
							}
						}
					}
					tyreUsageHistoryService.saveIssueTyreUsageHistory(valueObject);
					
				}else if(currentOdometer.equals(savedIssues.getISSUES_ODOMETER())) {
					vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToIssues(savedIssues);
					vehicleUpdateHistory.setCompanyId(companyId);
					vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
				}
			}
			
		}
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			e.printStackTrace();
			throw e;
		}finally {
			userDetails					= null;   
			issues						= null;   
			savedIssues					= null;   
			sequenceCounter 			= null;   
			issueTasks 					= null;   
			userProfile 				= null;   
			currentDate  				= null;   
			currnetTimeStamp 			= null;	  
			currentOdometer				= 0;      
			serviceList					= null;   
			vehicleUpdateHistory 		= null;   
		}
	}
	@Override
	@Transactional
	public ValueObject updateIssuesDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails					= null;
		Issues						issues						= null;
		Issues						savedIssues					= null;
		Integer 					currentOdometer				= 0;
		List<ServiceReminderDto>	serviceList					= null;
		VehicleOdometerHistory 		vehicleUpdateHistory 		= null;
		HashMap<String, Object>   	configuration			    = null;
		boolean                     allowedMobNotification      = false;     
		VehicleDto                  checkVehicleStatus      	= null;     
		try {
			userDetails				= (CustomUserDetails) valueObject.get("userDetails");
			issues = issuesBL.prepareIssuesDetails(valueObject, userDetails);
			configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			allowedMobNotification  = (boolean) configuration.getOrDefault(IssuesConfigurationContant.ALLOWED_MOB_NOTIFICATION, false);
			
			if(issues != null) {
				
				if(valueObject.getShort("issueType",(short)0) == IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN) {
					if(valueObject.getInt("replacedVehicle",0) == valueObject.getInt("vid",0)) {
						valueObject.put("replaceVehicleAndIssueVehicleSame", true);
						return valueObject;
					}
					checkVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("replacedVehicle"),userDetails.getCompany_id());
					if (checkVehicleStatus != null && (checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE  || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SURRENDER || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SOLD )) {
						
						valueObject.put("replaceVehicleNotActive", true);
						valueObject.put("replaceVehicleStatus", VehicleStatusConstant.getVehicleStatus(checkVehicleStatus.getvStatusId()));
						return valueObject;
					} 
					
				}
				
				
				savedIssues = issuesRepository.save(issues);
				valueObject.put("issueId", AESEncryptDecrypt.encryptBase64("" + savedIssues.getISSUES_ID()));
				valueObject.put("save", true);
				if(allowedMobNotification){
					String subscriberIds = valueObject.getString("subscriberIdsForMob","");
					valueObject.put("subscriberIds", subscriberIds);
					String msg =savedIssues.getISSUES_SUMMARY();
					sendNotification(valueObject,savedIssues,msg);
				}
				
				if(savedIssues.getISSUE_TYPE_ID() == IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN ) {
					valueObject.put("userDetails", userDetails);
					IssueBreakDownDetails	breakDownDetails	= IssuesBL.getIssueBreakDownDetailsDTO(valueObject, savedIssues);
					breakDownDetails.setIssueBreakDownDetailsId(valueObject.getLong("breakDownDetailsId",0));
					issueBreakDownDetailsRepository.save(breakDownDetails);
				}
				
			}
			
			if (savedIssues.getISSUES_VID() != null && savedIssues.getISSUES_VID() != 0) {
				currentOdometer = vehicleService.updateCurrentOdoMeterGetVehicleCurrentOdometer(savedIssues.getISSUES_VID(), userDetails.getCompany_id());
				
				if(savedIssues.getISSUES_VID() != 0 && currentOdometer != null && savedIssues.getISSUES_ODOMETER() != null) {
					if(currentOdometer < savedIssues.getISSUES_ODOMETER()) {
						vehicleService.updateCurrentOdoMeter(savedIssues.getISSUES_VID(), savedIssues.getISSUES_ODOMETER(), userDetails.getCompany_id());
						ServiceReminderService.updateCurrentOdometerToServiceReminder(savedIssues.getISSUES_VID(), savedIssues.getISSUES_ODOMETER(), userDetails.getCompany_id());
						
						vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToIssues(savedIssues);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(savedIssues.getISSUES_VID(), userDetails.getCompany_id(), userDetails.getId());
						
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
								}
							}
						}
					}
				}
			}
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}finally {
			userDetails					= null;   
			issues						= null;   
			savedIssues					= null;   
			currentOdometer				= 0;      
			serviceList					= null;   
			vehicleUpdateHistory 		= null;   
		}
	}
	
	
	
	@Override
	@Transactional
	public ValueObject getlastOpenIssue(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails					= null;
		Object[] 					result 						= null;
		IssuesDto					issuesDto					= null;
		TypedQuery<Object[]> 		typedQuery 					= null;
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			issuesDto				= new IssuesDto();
			
			switch (valueObject.getShort("issueType")) {
			case IssuesTypeConstant.ISSUE_TYPE_VEHICLE:
				typedQuery = entityManager.createQuery(
						"SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_REPORTED_DATE FROM Issues AS I"
						+ " WHERE I.ISSUES_STATUS_ID="+IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN+" AND I.ISSUES_VID ="+valueObject.getInt("vid")+" "
						+ " AND I.COMPANY_ID = "+userDetails.getCompany_id()+" AND I.markForDelete = 0 ORDER BY I.ISSUES_ID DESC", Object[].class);
				valueObject.put("issueType", "Vehicle");
				break;
			case IssuesTypeConstant.ISSUE_TYPE_DRIVER:
				typedQuery = entityManager.createQuery(
						"SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_REPORTED_DATE FROM Issues AS I"
						+ " WHERE I.ISSUES_STATUS_ID="+IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN+" AND I.ISSUES_DRIVER_ID ="+valueObject.getInt("driverId")+" "
						+ " AND I.COMPANY_ID = "+userDetails.getCompany_id()+" AND I.markForDelete = 0 ORDER BY I.ISSUES_ID DESC", Object[].class);
				valueObject.put("issueType", "Driver");
				break;
			case IssuesTypeConstant.ISSUE_TYPE_REFUND:
				typedQuery = entityManager.createQuery(
						"SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_REPORTED_DATE FROM Issues AS I"
						+ " WHERE I.ISSUES_STATUS_ID="+IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN+" AND I.ISSUES_BRANCH_ID ="+valueObject.getInt("issuesBranch")+" AND I.ISSUES_DEPARTNMENT_ID = "+valueObject.getInt("issuesDepartnment")+" "
						+ " AND I.COMPANY_ID = "+userDetails.getCompany_id()+" AND I.markForDelete = 0 ORDER BY I.ISSUES_ID DESC", Object[].class);
				valueObject.put("issueType", "Branch/Department");
				break;
			case IssuesTypeConstant.ISSUE_TYPE_CUSTOMER:
				typedQuery = entityManager.createQuery(
						"SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_REPORTED_DATE FROM Issues AS I"
						+ " WHERE I.ISSUES_STATUS_ID="+IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN+" AND I.CUSTOMER_NAME ="+valueObject.getString("customerName")+" "
						+ " AND I.COMPANY_ID = "+userDetails.getCompany_id()+" AND I.markForDelete = 0 ORDER BY I.ISSUES_ID DESC", Object[].class);
				valueObject.put("issueType", "Customer");
				
				break;
			case IssuesTypeConstant.ISSUE_TYPE_OTHER:
				typedQuery = entityManager.createQuery(
						"SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_REPORTED_DATE FROM Issues AS I"
						+ " WHERE I.ISSUES_STATUS_ID="+IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN+" AND I.ISSUES_BRANCH_ID ="+valueObject.getInt("issuesBranch")+" AND I.ISSUES_DEPARTNMENT_ID = "+valueObject.getInt("issuesDepartnment")+" "
						+ " AND I.COMPANY_ID = "+userDetails.getCompany_id()+" AND I.markForDelete = 0 ORDER BY I.ISSUES_ID DESC", Object[].class);
				valueObject.put("issueType", "Branch/Department");
				
				break;
			}
			
			try {
				typedQuery.setMaxResults(1);
				result	= typedQuery.getSingleResult();
			} catch (Exception e) {
				valueObject.put("noResult", true);
				return valueObject;
			}
			
			if(result != null) {
				issuesDto.setISSUES_ID((Long) result[0]);
				issuesDto.setISSUES_NUMBER((Long) result[1]);
				issuesDto.setISSUES_REPORTED_DATE(DateTimeUtility.getDateFromTimeStamp((Timestamp)result[2]));
			}else {
				valueObject.put("noResult", true);
				return valueObject;
			}
			
			valueObject.put("issue", issuesDto);
			
			return valueObject;
		}catch(Exception e) {
			LOGGER.error("Err"+e);
			throw e;
		}
	}
	
	public Issues getIssueDetailsByIssueId(Long Issues_ID, Integer companyId) throws Exception {
		Issues 	issue = null;
		try {
			issue = issuesRepository.getIssueDetailsByIssueId(Issues_ID,companyId);
			return issue;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Transactional
	public void update_SE_Issue_Details(Long Issues_ID)throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			
			issuesRepository.update_SE_Issue_Details( Issues_ID, userDetails.getCompany_id());
		}catch(Exception e) {
			System.err.println("err"+e);
		}
	}
	
	@Transactional
	public void update_WO_Issue_Details(Long Issues_ID,int companyId)throws Exception {
		try {
			issuesRepository.update_WO_Issue_Details( Issues_ID, companyId);
		}catch(Exception e) {
			System.err.println("err"+e);
		}
	}
	
	@Override
	public ValueObject createIssueFromMobile(ValueObject object) throws Exception {
		HashMap<String, Object>   	configuration			= null;
		HashMap<String, Object>   	gpsConfiguration		= null;
		CustomUserDetails			userDetails 			= null;
		boolean					 	customerIssue	      	= false;
		int 						companyId 				= 0;
		long						userId					= 0;
		try {
			companyId   	= object.getInt("companyId");
			userId			= object.getLong("userId");
			UserProfileDto		userProfile =  userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
			userDetails		 = new CustomUserDetails(companyId, userId, userProfile.getUser_email());
			configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			gpsConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			object.put("userName", userDetails.getFirstName());
			object.put("userId", userDetails.getId());
			object.put("gpsConfiguration", gpsConfiguration);
			object.put("companyId", userDetails.getCompany_id());
			object.put("vehiclegroup", vehicleGroupService.getVehiclGroupByPermissionForMobile(companyId, userId));
			
			customerIssue	= (boolean) configuration.getOrDefault(IssuesConfigurationContant.SHOW_CUSTOMER_NAME, false);
			if(customerIssue == true) {
				object.put("IssueType", IssuesTypeConstant.getIssuesTypeList());  
			}
			else {
				object.put("IssueType", IssuesTypeConstant.getIssuesTypeListWithoutCustomerIssue());
			}
			
			object.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_ISSUES, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).get(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_ISSUES));
			object.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_ISSUES,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_ISSUES, false));
			object.put(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, (boolean) configuration.getOrDefault(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, false));
			object.put("configuration", configuration);
			object.put("serverDateStr",DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getCurrentTimeStamp()));
			object.put("serverTimeStr",DateTimeUtility.getTimeFromTimeStamp(DateTimeUtility.getCurrentTimeStamp(), DateTimeUtility.HH_MM));
			object.put("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			return object;
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject showIssueFromMobile(ValueObject object) throws Exception {
		HashMap<String, Object>   	configuration			= null;
		CustomUserDetails			userDetails 			= null;
		int 						companyId 				= 0;
		long						userId					= 0;
		String						issueId					= null;
		try {
			companyId   	= object.getInt("companyId");
			userId			= object.getLong("userId");
			userDetails		= new CustomUserDetails(companyId, userId);
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			issueId         = object.getString("issueId");
			
			if (issueId != null && issueId != "") {
				IssuesDto issue = issuesService.get_IssuesDetails(Long.parseLong(AESEncryptDecrypt.decryptBase64(issueId)), userDetails.getCompany_id());
				
				if (issue != null) {
					
					IssuesDto issueDTO = issuesBL.Showing_Issues_Details(issue);
					
					ArrayList<IssuesTasksDto> IssuestaskDtos = null;
					IssuestaskDtos = new ArrayList<IssuesTasksDto>();
					IssuesTasksDto task = null;
					List<IssuesTasksDto>   issuesTasksList	= issuesService.getIssuesTasksList(issueDTO.getISSUES_ID(), userDetails.getCompany_id());
					
					if(issuesTasksList != null && !issuesTasksList.isEmpty()) {
						for (IssuesTasksDto taskDto : issuesTasksList) {
							task = new IssuesTasksDto();
							task.setISSUES_TASK_ID(taskDto.getISSUES_TASK_ID());
							task.setISSUES_CREATEBY_ID(taskDto.getISSUES_CREATEBY_ID());
							
							if (taskDto.getISSUES_CREATED_DATE_ON() != null) {
								task.setISSUES_CREATED_DATE(dateFormatonlyDateTime.format(taskDto.getISSUES_CREATED_DATE_ON()));
							}
							
							task.setISSUES_TASK_STATUS_ID(taskDto.getISSUES_TASK_STATUS_ID());
							task.setISSUES_TASK_STATUS(IssuesTypeConstant.getIssuesTaskStatusName(taskDto.getISSUES_TASK_STATUS_ID()));
							task.setISSUES_CHANGE_STATUS_ID(taskDto.getISSUES_CHANGE_STATUS_ID());
							task.setISSUES_CHANGE_STATUS(IssuesTypeConstant.getIssuesTaskChangeStatusName(taskDto.getISSUES_CHANGE_STATUS_ID()));
							task.setISSUES_REASON(taskDto.getISSUES_REASON());
							
							IssuestaskDtos.add(task);
						}
					}
					
					Collections.sort(IssuestaskDtos, new IssuesTasksDto());
					
					object.put("configuration", configuration);
					object.put("Isstask", IssuestaskDtos);
					object.put("Issues", issueDTO);
					object.put("vehicleId", issueDTO.getISSUES_VID());
					object.put("plainIssueId", Long.parseLong(AESEncryptDecrypt.decryptBase64(issueId)));
					object.put("IssueTypeId",issueDTO.getISSUES_TYPE_ID());
					List<org.fleetopgroup.persistence.document.IssuesDocument> issueDoc = Get_Issues_ID_Document_Details(Long.parseLong(AESEncryptDecrypt.decryptBase64(issueId)), userDetails.getCompany_id());
					
					if(issueDoc != null && !issueDoc.isEmpty()) {
						object.put("issueImage", ByteConvertor.byteArraytoBase64(issueDoc.get(0).getISSUE_CONTENT()));
						object.put("save", true);
					}
					
				} 

			}
			
			return object;
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject editIssueFromMobile(ValueObject object) throws Exception {
		HashMap<String, Object>   	configuration			= null;
		HashMap<String, Object>   	gpsConfiguration		= null;
		CustomUserDetails			userDetails 			= null;
		int 						companyId 				= 0;
		long						userId					= 0;
		String						issuesId				= null;
		IssuesDto 					issuesDto 				= null;
		StringBuffer 				assignTo 				= new StringBuffer();
		try {
			companyId   	 = object.getInt("companyId");
			userId			 = object.getLong("userId");
			issuesId		 = object.getString("issueId");
			UserProfileDto		userProfile =  userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
			userDetails		 = new CustomUserDetails(companyId, userId, userProfile.getUser_email());
			configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			gpsConfiguration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
			
			if (issuesId != null && issuesId != "") {
				
				issuesDto = issuesBL.Edit_Issues_Details(issuesService.get_IssuesDetails(Long.parseLong(AESEncryptDecrypt.decryptBase64(issuesId)), userDetails.getCompany_id()));
				assignTo = 	assignTo.append(issuesDto.getISSUES_ASSIGN_TO_ID());
				object.put("assignTo", assignTo);
				object.put("Issues", issuesDto);
				object.put("userName", userDetails.getFirstName());
				object.put("userId", userDetails.getId());
				object.put("gpsConfiguration", gpsConfiguration);
				object.put("configuration", configuration);
				object.put("companyId", userDetails.getCompany_id());
				object.put("vehiclegroup", vehicleGroupService.getVehiclGroupByPermissionForMobile(userDetails.getCompany_id(), object.getLong("userId")));
				object.put(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, (boolean) configuration.getOrDefault(IssuesConfigurationContant.SHOW_BREAK_DOWN_SELECTION, false));
				
				if(issuesDto.getISSUES_VID() != null) {
					object.put("vehicle", vehicleService.Get_Vehicle_Current_Status_TripSheetID(issuesDto.getISSUES_VID(), userDetails.getCompany_id()));
					object.put(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_ISSUES, (boolean)companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).get(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_ISSUES));
					object.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_ISSUES,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_ISSUES, false));
					object.put("configuration", configuration);
				}
				object.put("PartCategories", partCategoriesBL.prepareListofBean(partCategoriesService.listPartCategoriesByCompayId(userDetails.getCompany_id())));
			} 
			return object;
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject searchIssueFromMobile(ValueObject object) throws Exception {
		CustomUserDetails			userDetails 			= null;
		int 						companyId 				= 0;
		long						userId					= 0;
		try {
			companyId   	 = object.getInt("companyId");
			userId			 = object.getLong("userId");
			userDetails		 = new CustomUserDetails(companyId, userId, object.getString("email"));
			
			IssuesDto issue  = issuesService.get_IssuesDetailsByNumber(object.getLong("issueNumber"), userDetails);
			if(issue != null) {
				object.put("issueFound", true);
				object.put("issueId", AESEncryptDecrypt.encryptBase64("" + issue.getISSUES_ID()));
			} else {
				object.put("issueNotFound", true);
			}
		
			return object;
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject resolveIssueFromMobile(ValueObject object) throws Exception {
		CustomUserDetails			userDetails 			= null;
		int 						companyId 				= 0;
		long						userId					= 0;
		long						issuesId				= 0;
		HashMap<String, Object>   	configuration			= null;
		boolean                     allowedMobNotification  = false;
		try {
			companyId   	 = object.getInt("companyId");
			userId			 = object.getLong("userId");
			UserProfileDto		userProfile =  userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
			userDetails		 = new CustomUserDetails(companyId, userId, userProfile.getUser_email());
			issuesId		 = Long.parseLong(AESEncryptDecrypt.decryptBase64(object.getString("issuesId")));
			configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			allowedMobNotification  = (boolean) configuration.getOrDefault(IssuesConfigurationContant.ALLOWED_MOB_NOTIFICATION, false);
			if (issuesId != 0) {
				IssuesDto issue = issuesService.get_IssuesDetails(Long.parseLong(AESEncryptDecrypt.decryptBase64(object.getString("issuesId"))), userDetails.getCompany_id());
				Issues checkAssign = issuesService.check_Issues_Assign_Details(issuesId, userDetails);
				if (checkAssign != null) {
					try {
						Issues Save_Issues = new Issues();
						Save_Issues.setISSUES_ID(issuesId);
						
						IssuesTasks WRTask = new IssuesTasks();
						WRTask.setISSUES_TASK_STATUS_ID(issue.getISSUES_STATUS_ID());
						WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED);
						
						UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
						WRTask.setISSUES_CREATEBY_ID(Orderingname.getUserprofile_id());
						
						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
						WRTask.setISSUES_CREATED_DATE(toDate);
						WRTask.setISSUES(Save_Issues);
						WRTask.setISSUES_REASON(object.getString("description"));
						WRTask.setCOMPANY_ID(userDetails.getCompany_id());
						
						issuesService.registerNew_IssuesTasks(WRTask);

						issuesService.update_ReSolved_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_RESOLVED, userDetails.getId(), toDate, issuesId, userDetails.getCompany_id());
						object.put("save", true);
						if(issue!=null && allowedMobNotification){
							sendNotificationWhenIssueResolved(issuesId,userId,companyId);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		
			return object;
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject rejectIssueFromMobile(ValueObject object) throws Exception {
		CustomUserDetails			userDetails 			= null;
		int 						companyId 				= 0;
		long						userId					= 0;
		long						issuesId				= 0;
		HashMap<String, Object>   	configuration			    = null;
		boolean                     allowedMobNotification      = false;
		try {
			companyId   	 = object.getInt("companyId");
			userId			 = object.getLong("userId");
			UserProfileDto		userProfile =  userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
			userDetails		 = new CustomUserDetails(companyId, userId, userProfile.getUser_email());
			issuesId		 = Long.parseLong(AESEncryptDecrypt.decryptBase64(object.getString("issuesId")));
			configuration	        = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			allowedMobNotification  = (boolean) configuration.getOrDefault(IssuesConfigurationContant.ALLOWED_MOB_NOTIFICATION, false);
			if (issuesId != 0) {
				IssuesDto issue = issuesService.get_IssuesDetails(Long.parseLong(AESEncryptDecrypt.decryptBase64(object.getString("issuesId"))), userDetails.getCompany_id());
				Issues checkAssign = issuesService.check_Issues_Assign_Details(issuesId, userDetails);
				if (checkAssign != null) {
					try {
						Issues Save_Issues = new Issues();
						Save_Issues.setISSUES_ID(issuesId);
						
						IssuesTasks WRTask = new IssuesTasks();
						WRTask.setISSUES_TASK_STATUS_ID(issue.getISSUES_STATUS_ID());
						WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_REJECT);
						
						UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
						WRTask.setISSUES_CREATEBY_ID(Orderingname.getUserprofile_id());
						
						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
						WRTask.setISSUES_CREATED_DATE(toDate);
						WRTask.setISSUES(Save_Issues);
						WRTask.setISSUES_REASON(object.getString("description"));
						WRTask.setCOMPANY_ID(userDetails.getCompany_id());
						
						issuesService.registerNew_IssuesTasks(WRTask);

						issuesService.update_ReSolved_Issues_Status(IssuesTypeConstant.ISSUES_CHANGE_STATUS_REJECT, userDetails.getId(), toDate, issuesId, userDetails.getCompany_id());
						if(allowedMobNotification){
							sendNotificationWhenIssueIsRejected(issuesId,userId,companyId);
						}
						
						object.put("save", true);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		
			return object;
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject reOpenIssueFromMobile(ValueObject object) throws Exception {
		CustomUserDetails			userDetails 			= null;
		int 						companyId 				= 0;
		long						userId					= 0;
		long						issuesId				= 0;
		HashMap<String, Object>   	configuration	        = null;
		try {
			companyId   	 = object.getInt("companyId");
			userId			 = object.getLong("userId");
			UserProfileDto		userProfile =  userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
			userDetails		 = new CustomUserDetails(companyId, userId, userProfile.getUser_email());
			issuesId		 = Long.parseLong(AESEncryptDecrypt.decryptBase64(object.getString("issuesId")));
			
			if (issuesId != 0) {
				IssuesDto issue = issuesService.get_IssuesDetails(Long.parseLong(AESEncryptDecrypt.decryptBase64(object.getString("issuesId"))), userDetails.getCompany_id());
				Issues checkAssign = issuesService.check_Issues_Assign_Details(issuesId, userDetails);
				if (checkAssign != null) {
					try {
						Issues Save_Issues = new Issues();
						Save_Issues.setISSUES_ID(issuesId);
						
						IssuesTasks WRTask = new IssuesTasks();
						WRTask.setISSUES_TASK_STATUS_ID(issue.getISSUES_STATUS_ID());
						WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN);
						
						UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
						WRTask.setISSUES_CREATEBY_ID(Orderingname.getUserprofile_id());
						
						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
						WRTask.setISSUES_CREATED_DATE(toDate);
						WRTask.setISSUES(Save_Issues);
						WRTask.setISSUES_REASON(object.getString("description"));
						WRTask.setCOMPANY_ID(userDetails.getCompany_id());
						
						issuesService.registerNew_IssuesTasks(WRTask);

						issuesService.update_ReSolved_Issues_Status(IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN, userDetails.getId(), toDate, issuesId, userDetails.getCompany_id());
						configuration	        = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
						object.put("save", true);
						object.put("configuration", configuration);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
		
			return object;
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject closeIssueFromMobile(ValueObject object) throws Exception {
		CustomUserDetails			userDetails 			= null;
		UserProfileDto	            userProfile             = null;  
		int 						companyId 				= 0;
		long						userId					= 0;
		long						issuesId				= 0;
		HashMap<String, Object>   	configuration			= null;
		int                         driverId                = 0;
		long                        assigneeId              = 0;
		String                      closeIssueRemark        = "";
		try {
			closeIssueRemark = object.getString("closeIssueRemark","");
			companyId   	 = object.getInt("companyId");
			userId			 = object.getLong("userId");
			driverId		 = object.getInt("driverId",0);
			assigneeId	     = object.getLong("assigneeId",0);
			userProfile 	 = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
			userDetails		 = new CustomUserDetails(companyId, userId, userProfile.getUser_email());
			configuration	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
			issuesId		 = Long.parseLong(AESEncryptDecrypt.decryptBase64(object.getString("issuesId")));

			if (issuesId != 0) {
				IssuesDto issue = issuesService.get_IssuesDetails(Long.parseLong(AESEncryptDecrypt.decryptBase64(object.getString("issuesId"))), userDetails.getCompany_id());
				Issues checkAssign = issuesService.check_Issues_Assign_Details(issuesId, userDetails);

				if (checkAssign != null) {
					try {
						if((boolean) configuration.getOrDefault("issueAnalysis", false) && checkAssign.getISSUE_TYPE_ID() == IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN) {
							IssueAnalysisDto 	issueAnalysisDto = issueAnalysisService.getIssueAnalysisDetailsByIssueId(issuesId);
							if(issueAnalysisDto == null ) {
								object.put("IssueAnalysisNotFound", true);
								return object;
							}
						}
						Issues Save_Issues = new Issues();
						Save_Issues.setISSUES_ID(issuesId);

						IssuesTasks WRTask = new IssuesTasks();
						WRTask.setISSUES_TASK_STATUS_ID(issue.getISSUES_STATUS_ID());
						WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_STATUS_CLOSED);

						UserProfileDto Orderingname = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
						WRTask.setISSUES_CREATEBY_ID(Orderingname.getUserprofile_id());

						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
						WRTask.setISSUES_CREATED_DATE(toDate);
						WRTask.setISSUES(Save_Issues);
						WRTask.setISSUES_REASON(object.getString("description"));
						WRTask.setCOMPANY_ID(userDetails.getCompany_id());

						issuesService.registerNew_IssuesTasks(WRTask);
						issuesService.update_Close_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_CLOSED, userDetails.getId(), toDate, issuesId, userDetails.getCompany_id());
						if((boolean) configuration.getOrDefault("closureWithRemark",false)) {
							IssuesComment saveComment = new IssuesComment();
							saveComment.setISSUES_ID(issuesId);
							saveComment.setISSUE_COMMENT(object.getString("description",""));
							saveComment.setCREATEDBYID(userDetails.getId());
							saveComment.setAssignee(assigneeId);
							saveComment.setDriverId(driverId);
							saveComment.setMarkForDelete(false);
							saveComment.setCREATED_DATE(toDate);
							saveComment.setCOMPANY_ID(userDetails.getCompany_id());
							saveComment.setISSUE_TITLE(closeIssueRemark);
							issuesService.add_Issues_Comment_Details(saveComment);
						}
						issuesService.update_ReSolved_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_CLOSED, userDetails.getId(), toDate, issuesId, userDetails.getCompany_id());

						object.put("save", true);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}

			}

			return object;

		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getIssueImageForMobile(ValueObject object) throws Exception {
		CustomUserDetails			userDetails 			= null;
		int 						companyId 				= 0;
		long						userId					= 0;
		long						issuesId				= 0;
		try {
			companyId   	 = object.getInt("companyId");
			userId			 = object.getLong("userId");
			UserProfileDto		userProfile =  userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
			userDetails		 = new CustomUserDetails(companyId, userId, userProfile.getUser_email());
			issuesId		 = Long.parseLong(AESEncryptDecrypt.decryptBase64(object.getString("issuesId")));
			
			if (issuesId != 0) {
				
				List<org.fleetopgroup.persistence.document.IssuesDocument> issue = Get_Issues_ID_Document_Details(issuesId, userDetails.getCompany_id());
				
				if(issue != null && !issue.isEmpty()) {
					object.put("issueImage", ByteConvertor.byteArraytoBase64(issue.get(0).getISSUE_CONTENT()));
					object.put("save", true);
				}
				
			}
		
			return object;
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject saveIssueImageFromMobile(ValueObject object) throws Exception {
		CustomUserDetails			userDetails 			= null;
		int 						companyId 				= 0;
		long						userId					= 0;
		long						issuesId				= 0;
		String						base64					= null;
		String						imageName				= null;
		String						imageExt				= null;
		try {
			companyId   	 = object.getInt("companyId");
			userId			 = object.getLong("userId");
			UserProfileDto		userProfile =  userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userId);
			userDetails		 = new CustomUserDetails(companyId, userId, userProfile.getUser_email());
			base64			 = object.getString("base64String",null);
			imageName		 = object.getString("imageName",null);
			imageExt		 = object.getString("imageExt",null);
			issuesId		 = Long.parseLong(AESEncryptDecrypt.decryptBase64(object.getString("issuesId")));
			
			if (issuesId != 0) {
				
				org.fleetopgroup.persistence.document.IssuesDocument IssuesDocument = new org.fleetopgroup.persistence.document.IssuesDocument();

				IssuesDocument.setISSUE_ID(issuesId);
				IssuesDocument.setISSUE_DOCUMENTNAME(object.getString("documentName"));

				byte[] bytes = ByteConvertor.base64ToByte(base64);
				IssuesDocument.setISSUE_FILENAME(imageName);
				IssuesDocument.setISSUE_CONTENT(bytes);
				IssuesDocument.setISSUE_CONTENTTYPE(imageExt);

				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
				IssuesDocument.setISSUE_UPLOADDATE(toDate);
				IssuesDocument.setCOMPANY_ID(userDetails.getCompany_id());
				IssuesDocument.setCREATEDBYID(userDetails.getId());

				issuesService.add_Issues_Document_Details(IssuesDocument);
				
				object.put("save", true);
				
			}
		
			return object;
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

@Transactional
	public ValueObject getIssueMaxOdometerRange(ValueObject object) throws Exception {
	try {
			
		VehicleOdometerHistory odometerHistory = 	vehicleOdometerHistoryService.getVehicleOdometerHistoryByVidExceptCurrentEntry(object.getLong("issueId",0), object.getInt("vid",0), object.getInt("companyId",0));
	
		if(odometerHistory != null && odometerHistory.getVehicle_Odometer() > object.getInt("issueOdometer",0)) {
			object.put("issueMaxOdomter", odometerHistory.getVehicle_Odometer());
		}
	
		object.put("odometerHistory", odometerHistory);
		return object;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
public void sendNotification(ValueObject valueObject,Issues savedIssues,String msg)throws Exception{
	StringBuffer         notificationString           =   null;
	String[]             assignedToIdsArr             =   null;
	MobileNotifications  mobileNotifications          =   null;
	try{
		notificationString  = new StringBuffer();
		if(valueObject!=null){
			assignedToIdsArr   = valueObject.getString("subscriberIds","").split(",");
			if(assignedToIdsArr!=null && assignedToIdsArr.length > 0){
				for(String subId : assignedToIdsArr){
					if(subId!="" && subId.length() > 0){
						String tokenForNotification   = mobileAppUserRegistrationRepository.getTokenForNotification(Long.parseLong(subId),(long)savedIssues.getCOMPANY_ID());
						notificationString.append("   Issue No                  : "+savedIssues.getISSUES_NUMBER());
						notificationString.append("\n Issue Type                 : "+IssuesTypeConstant.getIssueTypeName(savedIssues.getISSUE_TYPE_ID()));
						notificationString.append("\n Creation Date             : "+savedIssues.getCREATED_DATE());
						notificationString.append("\n Summary                    : "+msg);
						if(tokenForNotification!=null && tokenForNotification.length() > 0){
							NotificationBllImpl.MobileNotification(notificationString.toString(),tokenForNotification, NotificationConstant.NOTI_ISSUE_ASSIGNED);
							mobileNotifications = new MobileNotifications();
							mobileNotifications.setCompanyId((long)savedIssues.getCOMPANY_ID());
							mobileNotifications.setUserId(Long.parseLong(subId));
							mobileNotifications.setNotification(notificationString.toString());
							mobileNotifications.setNotificationModuleIdentifier(NotificationConstant.ISSUE_NOTIFICATION_IDENTIFIER);
							mobileNotificationRepository.save(mobileNotifications);
							UserAlertNotifications	notifications = new UserAlertNotifications();
							notifications.setUserId(Long.parseLong(subId));
							notifications.setAlertMsg(notificationString.toString());
							notifications.setCompanyId(savedIssues.getCOMPANY_ID());
							notifications.setTxnTypeId(UserAlertNOtificationsConstant.ALERT_TYPE_ISSUE_NOTIFICATION);
							notifications.setTransactionId(savedIssues.getISSUES_ID());
							notifications.setCreatedById(savedIssues.getCREATEDBYID());
							notifications.setCreatedOn(new Date());
							notifications.setStatus((short) 1);
							userAlertNotificationsRepository.save(notifications);
						}
					}
				}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
}

public void sendNotificationWhenIssueResolved(long issuesId,long userId,int companyId)throws Exception{
	StringBuffer         notificationString           =   null;
	MobileNotifications  mobileNotifications          =   null;
	String               issueResolvedByName          =   "";
	try{
		IssuesDto issue     = issuesService.get_IssuesDetails(issuesId, companyId);
		notificationString  = new StringBuffer();
		if(issue!=null){
			issueResolvedByName = issue.getLASTMODIFIEDBY();
		}
	
		String tokenForNotification   = mobileAppUserRegistrationRepository.getTokenForNotification(issue.getCREATEDBYID(),(long)companyId);
		if(tokenForNotification!=null && tokenForNotification.length() > 0 && issue!=null){
			notificationString.append(" Issue No                                : "+issue.getISSUES_NUMBER());
			notificationString.append("\n Issue Type                             : "+IssuesTypeConstant.getIssueTypeName(issue.getISSUES_TYPE_ID()));
			notificationString.append("\n Issue Resolved Date            : "+DateTimeUtility.getCurrentTimeStamp().toString());
			notificationString.append("\n Issue Status                          : "+IssuesTypeConstant.getIssuesTaskChangeStatusName(IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED));
			notificationString.append("\n Issue Resolved By                : "+issueResolvedByName);
			notificationString.append("\n Summary                                : "+issue.getISSUES_SUMMARY());
			NotificationBllImpl.MobileNotification(notificationString.toString(),tokenForNotification, NotificationConstant.NOTI_ISSUE_RESOLVED);
			mobileNotifications = new MobileNotifications();
			mobileNotifications.setCompanyId((long)companyId);
			mobileNotifications.setUserId(issue.getCREATEDBYID());
			mobileNotifications.setNotification(notificationString.toString());
			mobileNotifications.setNotificationModuleIdentifier(NotificationConstant.ISSUE_NOTIFICATION_IDENTIFIER);
			mobileNotificationRepository.save(mobileNotifications);
			UserAlertNotifications	notifications = new UserAlertNotifications();
			notifications.setUserId(issue.getCREATEDBYID());
			notifications.setAlertMsg(notificationString.toString());
			notifications.setCompanyId(companyId);
			notifications.setTxnTypeId(UserAlertNOtificationsConstant.ALERT_TYPE_ISSUE_NOTIFICATION);
			notifications.setTransactionId(issuesId);
			notifications.setCreatedById(userId);
			notifications.setCreatedOn(new Date());
			notifications.setStatus((short) 1);
			userAlertNotificationsRepository.save(notifications);
		}
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
}

public void sendNotificationWhenIssueIsRejected(long issuesId,long userId,int companyId)throws Exception{
	StringBuilder         notificationString           =   null;
	String                issueRejectedByName          =   "";
	MobileNotifications   mobileNotifications          =   null;
	try{
		notificationString = new StringBuilder();
		IssuesDto issue     = issuesService.get_IssuesDetails(issuesId, companyId);
		if(issue!=null){
			issueRejectedByName = issue.getLASTMODIFIEDBY();
		}
		String tokenForNotification   = mobileAppUserRegistrationRepository.getTokenForNotification(issue.getCREATEDBYID(),(long)companyId);
		if(tokenForNotification!=null && tokenForNotification.length() > 0 && issue!=null){
			notificationString.append("Issue No : " + issue.getISSUES_NUMBER()+  "\t has been rejected by "+issueRejectedByName);
			notificationString.append("\n Please review the Issue and take action accordingly.");
			NotificationBllImpl.MobileNotification(notificationString.toString(),tokenForNotification, NotificationConstant.NOTI_ISSUE_REJECTED);
			mobileNotifications = new MobileNotifications();
			mobileNotifications.setCompanyId((long)companyId);
			mobileNotifications.setUserId(issue.getCREATEDBYID());
			mobileNotifications.setNotification(notificationString.toString());
			mobileNotifications.setNotificationModuleIdentifier(NotificationConstant.ISSUE_NOTIFICATION_IDENTIFIER);
			mobileNotificationRepository.save(mobileNotifications);
			UserAlertNotifications	notifications = new UserAlertNotifications();
			notifications.setUserId(issue.getCREATEDBYID());
			notifications.setAlertMsg(notificationString.toString());
			notifications.setCompanyId(companyId);
			notifications.setTxnTypeId(UserAlertNOtificationsConstant.ALERT_TYPE_ISSUE_NOTIFICATION);
			notifications.setTransactionId(issuesId);
			notifications.setCreatedById(userId);
			notifications.setCreatedOn(new Date());
			notifications.setStatus((short) 1);
			userAlertNotificationsRepository.save(notifications);
		}
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
}
@Transactional
@Override
public List<IssuesDto> getIssuesActivityList(String query , String innerQuery) throws Exception {
	
	TypedQuery<Object[]> queryt =	null;
		queryt = entityManager.createQuery(
				"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
						+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
						+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER,"
						+ " R.markForDelete,R.CREATEDBYID,R.LASTMODIFIEDBYID,U.firstName, U.lastName ,U.id "
						+ " FROM Issues AS R "
						+ " "+innerQuery+" "
						+ "LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
						+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
						+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
						+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
						+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
						+ " Where "+query+" ",
				Object[].class);
		
	List<Object[]> results = queryt.getResultList();

	List<IssuesDto> Dtos = null;
	if (results != null && !results.isEmpty()) {
		Dtos = new ArrayList<IssuesDto>();
		IssuesDto list = null;
		for (Object[] result : results) {
			list = new IssuesDto();

			list.setISSUES_ID((Long) result[0]);
			list.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + list.getISSUES_ID()));
			list.setISSUES_SUMMARY((String) result[1]);
			list.setISSUES_LABELS_ID((short) result[2]);
			list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
			if( result[4] != null)
			list.setISSUES_VID((Integer) result[4]);
			if( result[5] != null) {
				list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
			}else {
				list.setISSUES_VEHICLE_REGISTRATION("-");
			}
			list.setISSUES_VEHICLE_GROUP((String) result[6]);
			list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
			list.setISSUES_TYPE_ID((short) result[8]);
			list.setISSUES_DRIVER_ID((Integer) result[9]);
			list.setISSUES_DRIVER_NAME((String) result[10]);
			list.setISSUES_BRANCH_ID((Integer) result[11]);
			list.setISSUES_BRANCH_NAME((String) result[12]);
			list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
			if(result[14] != null)
			list.setCREATED_DATE(dateFormat.format(result[14]));
			list.setCREATED_DATE_ON((Date) result[14]);
			if(result[15] != null)
			list.setLASTUPDATED_DATE(dateFormat.format(result[15]));
			list.setLASTUPDATED_DATE_ON((Date) result[15]);
			list.setISSUES_STATUS_ID((short) result[16]);
			list.setISSUES_NUMBER((Long) result[17]);
			list.setMarkForDelete((boolean)result[18]);
			if(!list.isMarkForDelete()) {
				list.setIssuesNumberStr("<a target=\"_blank\" style=\"color: blue; background: #ffc;\"  href=\"showIssues?Id="+list.getISSUES_ID_ENCRYPT()+" \"> I-"+list.getISSUES_NUMBER()+" </a>");
			}else {
				list.setIssuesNumberStr("<a  style=\"color: red; background: #ffc;\"  href=\"# \" data-toggle=\"tip\" data-original-title=\"Deleted Data\"> I-"+list.getISSUES_NUMBER()+" </a>");
			}
			list.setCREATEDBYID((Long)result[19]);
			list.setLASTMODIFIEDBYID((Long)result[20]);
			list.setUserName(result[21]+" "+result[22]);
			list.setUserId((Long)result[23]);
			list.setISSUES_STATUS(IssuesTypeConstant.getIssuesTaskStatusName(list.getISSUES_STATUS_ID()));
			list.setISSUES_TYPE(IssuesTypeConstant.getIssueTypeName(list.getISSUES_TYPE_ID()));
			Dtos.add(list);
		}
	}
	return Dtos;
}

@Transactional
public ValueObject check_Issues_Update_Details (Long Issues_ID, String email ,CustomUserDetails userDetails) throws Exception {
	ValueObject 				valueObject			= null;
	Timestamp 					lastUpdatedOn		= null;
	long						lastUpdatedBy		= 0;
	try {
		lastUpdatedOn		= DateTimeUtility.getCurrentTimeStamp();
		lastUpdatedBy		= userDetails.getId();


		entityManager.createQuery(
				" UPDATE Issues AS isu SET isu.ISSUES_ASSIGN_TO = '"+ email +"', isu.ISSUES_ASSIGN_TO_NAME = '"+ email + "', isu.LASTMODIFIEDBYID = "+ lastUpdatedBy+", isu.LASTUPDATED_DATE = '"+lastUpdatedOn+"' "
						+ " WHERE isu.ISSUES_ID = " + Issues_ID + " AND isu.COMPANY_ID = "+userDetails.getCompany_id())
		.executeUpdate();
		
		valueObject = new ValueObject();
		valueObject.put("Updated", true);

		return valueObject;

	} catch (Exception e) {
		throw e;
	}
}

/*
 * public List<IssuesDto> getOpenIssueDetailsByVehicle(Integer vid,
 * CustomUserDetails userDetails) throws Exception { TypedQuery<Object[]>
 * typedQuery = null; IssuesDto list = null; List<IssuesDto> Dtos = null; try {
 * 
 * typedQuery = entityManager.createQuery(
 * "SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_SUMMARY " +
 * " From Issues as I " +
 * " WHERE  I.ISSUES_VID = "+vid+" AND I.ISSUES_STATUS_ID = "+IssuesTypeConstant
 * .ISSUES_STATUS_OPEN+" AND " +
 * " I.COMPANY_ID ="+userDetails.getCompany_id()+" AND I.markForDelete = 0  "
 * ,Object[].class);
 * 
 * List<Object[]> results = typedQuery.getResultList();
 * 
 * if (results != null && !results.isEmpty()) { Dtos = new ArrayList<>();
 * 
 * for(Object[] result : results) { list = new IssuesDto();
 * 
 * list.setISSUES_ID((Long)result[0]);
 * list.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" +
 * list.getISSUES_ID())); list.setISSUES_NUMBER((Long)result[1]);
 * list.setISSUES_SUMMARY((String)result[2]); Dtos.add(list); } } return Dtos; }
 * catch (Exception e) { System.err.println("ERR"+e); throw e;
 * 
 * } }
 */
public List<IssuesDto> getOpenIssueDetailsByVehicle(Integer vid, CustomUserDetails	userDetails) throws Exception {
	TypedQuery<Object[]> 	typedQuery 					= null;
	IssuesDto 				list 						= null;
	List<IssuesDto> 		Dtos 						= null;
	try {
		
		typedQuery = entityManager.createQuery(
				"SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_SUMMARY "
						+ " From Issues as I "
						+ " WHERE  I.ISSUES_VID = "+vid+" AND I.ISSUES_STATUS_ID = "+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND "
						+ " I.COMPANY_ID ="+userDetails.getCompany_id()+" AND I.markForDelete = 0  "
						,Object[].class);
		
		List<Object[]> results = typedQuery.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<>();
			
			for(Object[] result : results) {
				list = new IssuesDto();
				
				list.setISSUES_ID((Long)result[0]);
				list.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + list.getISSUES_ID()));
				list.setISSUES_NUMBER((Long)result[1]);
				list.setISSUES_SUMMARY((String)result[2]);
				Dtos.add(list);
			}
		}
		return Dtos;
	} catch (Exception e) {
		System.err.println("ERR"+e);
		throw e;
		
	}
}

public List<IssuesDto> getOpenIssueDetailsByVehicle(Integer vid, Integer companyId) throws Exception {
	TypedQuery<Object[]> 	typedQuery 					= null;
	IssuesDto 				list 						= null;
	List<IssuesDto> 		Dtos 						= null;
	try {
		
		typedQuery = entityManager.createQuery(
				"SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_SUMMARY "
						+ " From Issues as I "
						+ " WHERE  I.ISSUES_VID = "+vid+" AND I.ISSUES_STATUS_ID = "+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND "
						+ " I.COMPANY_ID ="+companyId+" AND I.markForDelete = 0  "
						,Object[].class);
		
		List<Object[]> results = typedQuery.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<>();
			
			for(Object[] result : results) {
				list = new IssuesDto();
				
				list.setISSUES_ID((Long)result[0]);
				list.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + list.getISSUES_ID()));
				list.setISSUES_NUMBER((Long)result[1]);
				list.setISSUES_SUMMARY((String)result[2]);
				Dtos.add(list);
			}
		}
		return Dtos;
	} catch (Exception e) {
		System.err.println("ERR"+e);
		throw e;
		
	}
}

@Transactional
public IssuesTasks saveIssuestask(IssuesTasks WRTask, CustomUserDetails userDetails) throws Exception {

	IssuesTasks issuesTasks = new IssuesTasks();
	issuesTasks.setISSUES_TASK_STATUS_ID(WRTask.getISSUES_TASK_STATUS_ID());
	issuesTasks.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_OPEN);
	issuesTasks.setISSUES_CREATEBY_ID(WRTask.getISSUES_CREATEBY_ID());
	issuesTasks.setISSUES_CREATED_DATE(WRTask.getISSUES_CREATED_DATE());
	issuesTasks.setISSUES(WRTask.getISSUES());
	issuesTasks.setISSUES_REASON(WRTask.getISSUES_REASON());
	issuesTasks.setCOMPANY_ID(userDetails.getCompany_id());
	return issuesTaskRepository.save(issuesTasks);
}

@Transactional
public List<IssuesDto> findOpenIsueBeforeFiveDays(short ISSUES_STATUS, String searchdate, Integer companyId) {

	TypedQuery<Object[]> queryt = entityManager.createQuery(
			"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
					+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
					+ "DP.depart_name, R.ISSUES_STATUS_ID , R.CREATED_DATE, R.LASTUPDATED_DATE ,R.ISSUES_NUMBER, R.CREATEDBYID,R.ISSUES_ASSIGN_TO FROM Issues AS R"
					+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
					+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
					+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
					+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
					+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
					+ " WHERE R.ISSUES_STATUS_ID=" + ISSUES_STATUS + " AND R.ISSUES_REPORTED_DATE <='" + searchdate + " 00:00:00 '  AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0 ORDER BY R.ISSUES_ID desc",
			Object[].class);
	List<Object[]> results = queryt.getResultList();

	List<IssuesDto> Dtos = null;
	if (results != null && !results.isEmpty()) {
		Dtos = new ArrayList<IssuesDto>();
		IssuesDto list = null;
		for (Object[] result : results) {
			list = new IssuesDto();

			list.setISSUES_ID((Long) result[0]);
			list.setISSUES_SUMMARY((String) result[1]);
			list.setISSUES_LABELS_ID((short) result[2]);
			list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
			list.setISSUES_VID((Integer) result[4]);
			list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
			list.setISSUES_VEHICLE_GROUP((String) result[6]);
			list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
			list.setISSUES_TYPE_ID((short) result[8]);
			list.setISSUES_DRIVER_ID((Integer) result[9]);
			list.setISSUES_DRIVER_NAME((String) result[10]);
			list.setISSUES_BRANCH_ID((Integer) result[11]);
			list.setISSUES_BRANCH_NAME((String) result[12]);
			list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
			list.setISSUES_STATUS_ID((short) result[14]);
			list.setCREATED_DATE_ON((Date) result[15]);
			list.setLASTUPDATED_DATE_ON((Date) result[16]);
			list.setISSUES_NUMBER((Long) result[17]);
			list.setCREATEDBYID((Long) result[18]);
			list.setISSUES_ASSIGN_TO((String) result[19]);
			Dtos.add(list);
		}
	}
	return Dtos;
}

@SuppressWarnings("unchecked")
@Override
public List<IssuesDto> getOpenIssueDetailsByVehicle(int vid, CustomUserDetails userDetails, long issueId, String conditions,String invoiceDate) throws Exception {
	Query 					query 						= null;
	IssuesDto 				list 						= null;
	List<IssuesDto> 		Dtos 						= null;
	String					issueQuery					= "";
	String					invoiceDateQuery			= "";
	try {
		if(issueId > 0) {
			issueQuery = " AND I.ISSUES_ID = "+issueId+" ";
		}
		if(!invoiceDate.equalsIgnoreCase("")) {
			invoiceDateQuery = " AND I.ISSUES_REPORTED_DATE <= '"+invoiceDate+""+DateTimeUtility.DAY_END_TIME+"'"; 
		}
		query = entityManager.createNativeQuery(
				"SELECT I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_SUMMARY, SUBSTRING_INDEX(I.ISSUES_ASSIGN_TO,',',1), U.id , I.ISSUES_LABELS_ID,"
						+ " I.ISSUES_DRIVER_ID, D.driver_firstname, D.driver_Lastname, D.driver_fathername ,I.ISSUES_ASSIGN_TO "
						+ " From Issues as I  "
						+ " INNER JOIN User AS U ON U.email = SUBSTRING_INDEX(I.ISSUES_ASSIGN_TO,',',1)  AND U.company_id = "+userDetails.getCompany_id()+" "
						+ " LEFT JOIN Driver D ON D.driver_id = I.ISSUES_DRIVER_ID "
						+ " WHERE  I.ISSUES_VID = "+vid+" "+invoiceDateQuery+"  AND "
						+ " I.COMPANY_ID ="+userDetails.getCompany_id()+" "+issueQuery+" AND I.markForDelete = 0 "+conditions+"  " );
		
		List<Object[]> results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<>();
			for(Object[] result : results) {
				list = new IssuesDto();
				
				list.setISSUES_ID(Long.parseLong(result[0]+""));
				list.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + list.getISSUES_ID()));
				list.setISSUES_NUMBER(Long.parseLong(result[1]+""));
				list.setISSUES_SUMMARY((String)result[2]);
				list.setISSUES_ASSIGN_TO((String)result[3]);
				list.setIssueAssignToFirstId(Long.parseLong(result[4]+""));
				list.setISSUES_LABELS_ID((short) result[5]);
				list.setISSUES_LABELS(IssuesTypeConstant.getIssuesLabelName(list.getISSUES_LABELS_ID()));
				list.setISSUES_DRIVER_ID((Integer) result[6]);
				list.setISSUES_DRIVER_NAME((String)result[7]+"_"+(String)result[8]+"_"+(String)result[9]);
				if(list.getISSUES_DRIVER_NAME().contains("_null")) {
					list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME().replace("_null", ""));
				}
				list.setISSUES_ASSIGN_TO_NAME((String)result[10]);
				
				Dtos.add(list);
			}
		}
		return Dtos;
	} catch (Exception e) {
		System.err.println("ERR"+e);
		throw e;
		
	}
}
@Override
public ValueObject getNewIssueReportData(ValueObject valueObject) throws Exception {
	Integer						issuesType				= 0;
	String						dateRange				= "";
	String						subscribeAssign			= "";
	CustomUserDetails			userDetails				= null;
	ValueObject					tableConfig				= null;
	List<IssuesDto> 			issuelist			   	= null;	
	Integer 					issueStatusId			= 0 ;
	Integer 					subscribeReport			= 0 ;				
	Integer 					labelId					= 0 ;				
	String 						whereclause				= "";
	String 						dateRangeFrom			= "" ;
	String 						dateRangeTo 			= "";
	String[] 					From_TO_DateRange		= null;	
	HashMap<String, IssuesDto>	issueDtohm 				= null;
	String 						issuesTypeQuery			= "";
	String 						labelIdQuery			= "";
	String 						issueStatusIdQuery		= "";
	String 						subscribeReportQuery	= "";
	String 						subscribeAssignQuery	= "";

	try {

		userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		issueDtohm						= new HashMap<String, IssuesDto>();
		issuesType						= valueObject.getInt("IssuesType", 0);
		labelId							= valueObject.getInt("labelId");
		issueStatusId					= valueObject.getInt("issueStatusId", 0);
		subscribeReport					= valueObject.getInt("subscribeReport", 0);
		subscribeAssign					= valueObject.getString("subscribeAssign", null);
		dateRange						= valueObject.getString("dateRange");


		if(dateRange != null) {

			From_TO_DateRange = dateRange.split(" to ");

			dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0].trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1].trim()+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
		}	

		if(issuesType > 0) {
			issuesTypeQuery = "AND R.ISSUE_TYPE_ID = "+ issuesType +"";
		}
		if(labelId > 0) {
			labelIdQuery = "AND R.ISSUES_LABELS_ID = "+ labelId +"";
		}
		if(issueStatusId > 0) {
			issueStatusIdQuery = "AND R.ISSUES_STATUS_ID = "+ issueStatusId +"";
		}
		if(subscribeReport > 0) {
			subscribeReportQuery = "AND R.CREATEDBYID = " + subscribeReport + "";
		}
		if(subscribeAssign != null && !subscribeAssign.trim().equalsIgnoreCase("") && subscribeAssign.indexOf('\'') != 0 ) {	
			subscribeAssignQuery = "AND lower(R.ISSUES_ASSIGN_TO) Like ('%" + subscribeAssign + "%')";
		}

		whereclause = ""+issuesTypeQuery+" "+labelIdQuery +" "+issueStatusIdQuery +" "+subscribeReportQuery+" "+subscribeAssignQuery 
				+"AND R.COMPANY_ID = " + userDetails.getCompany_id()+
				" AND R.ISSUES_REPORTED_DATE BETWEEN '" + dateRangeFrom + "' AND '" +dateRangeTo + "'";

		tableConfig				= new ValueObject();
		tableConfig.put("companyId", userDetails.getCompany_id());
		tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.NEW_ISSUE_REPORT_DATA_FILE_PATH);
		tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
		tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

		issuelist		= getNewIssueData(whereclause);

		if(issuelist != null && !issuelist.isEmpty()) {
			for(IssuesDto model : issuelist) {
				if(!issueDtohm.containsKey(model.getISSUES_ID()+"_"+model.getISSUES_STATUS_ID())) {
					issueDtohm.put(model.getISSUES_ID()+"_"+model.getISSUE_TYPE_ID(), model);
				}
			}
		}
		if(issueDtohm != null && !issueDtohm.isEmpty()) {
			issuelist = new ArrayList<IssuesDto>(); 
			issuelist.addAll(issueDtohm.values()); 
		}

		valueObject.put("issuelist", issuelist);
		valueObject.put("dateRange", dateRange);

		valueObject.put("tableConfig", tableConfig.getHtData());
		valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

		return valueObject;
	} catch (Exception e) {
		throw e;
	}finally {
		userDetails			= null;			
	}
}

public List<IssuesDto> getNewIssueData(String whereclause) throws Exception {
	List<IssuesDto> Dtos 	= null;
	try {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.ISSUES_ID, R.ISSUES_STATUS_ID, R.ISSUES_LABELS_ID, R.ISSUE_TYPE_ID, R.ISSUES_VID, R.ISSUES_ODOMETER, US.firstName, R.ISSUES_ASSIGN_TO, V.vehicle_registration, "
						+ " R.ISSUES_DRIVER_ID, R.ISSUES_SUMMARY, D.driver_firstname, "
						+ " R.CREATED_DATE, R.LASTUPDATED_DATE ,R.ISSUES_NUMBER, R.CREATEDBYID,R.ISSUES_ASSIGN_TO, PC.pcName, RT.routeName, IST.ISSUES_CREATED_DATE, R.ISSUES_REPORTED_DATE, IST.ISSUES_TASK_ID FROM Issues AS R "
						+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID "
						+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID "
						+ " LEFT JOIN PartCategories PC ON PC.pcid = R.categoryId "
						+ " LEFT JOIN TripRoute RT ON RT.routeID = R.routeID "
						+ " INNER JOIN User US ON US.id = R.CREATEDBYID "
						+ " LEFT JOIN IssuesTasks IST ON IST.ISSUES.ISSUES_ID = R.ISSUES_ID AND IST.ISSUES_CHANGE_STATUS_ID = "+ IssuesTypeConstant.ISSUES_STATUS_RESOLVED +" "
						+ " WHERE R.markForDelete = 0 "+ whereclause +"ORDER BY R.ISSUES_ID desc",
						Object[].class);

		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<IssuesDto>();
			IssuesDto list = null;
			for (Object[] result : results) {
				list = new IssuesDto();

				list.setISSUES_ID((Long) result[0]);
				list.setISSUES_STATUS_ID((short) result[1]);
				list.setISSUES_LABELS_ID((short) result[2]);
				list.setISSUES_TYPE_ID((short) result[3]);
				list.setISSUES_VID((Integer) result[4]);
				list.setISSUES_ODOMETER((Integer) result[5]);
				list.setCREATEDBY((String) result[6]);
				list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
				list.setISSUES_VEHICLE_REGISTRATION((String) result[8]);
				list.setISSUES_DRIVER_ID((Integer) result[9]);
				list.setISSUES_SUMMARY((String) result[10]);
				list.setISSUES_DRIVER_NAME((String) result[11]);
				list.setCREATED_DATE_ON((Date) result[12]);
				list.setLASTUPDATED_DATE_ON((Date) result[13]);
				list.setISSUES_NUMBER((Long) result[14]);
				list.setCREATEDBYID((Long) result[15]);
				list.setISSUES_ASSIGN_TO((String) result[16]);
				list.setPartCategoryName((String) result[17]);
				list.setRouteName((String) result[18]);
				if(result[19] != null)
					list.setResolvingDateTime((Date) result[19]);
				else
					list.setResolvingDate("--");
				if(result[20] != null)
					list.setISSUES_REPORTED_DATE_ON((Date) result[20]);
				else
					list.setIssuesReportedDateStr("--");
				if(result[21] != null)
					list.setIssueTaskId((Long) result[21]);
				else
					list.setIssueTaskId((long) 0);

				list.setIssueTypeName(IssuesTypeConstant.getIssueTypeName(list.getISSUES_TYPE_ID()));
				list.setIssueLabelName(IssuesTypeConstant.getIssuesLabelName(list.getISSUES_LABELS_ID()));
				list.setIssueStatusName(IssuesTypeConstant.getStatusName(list.getISSUES_STATUS_ID()));

				if(list.getResolvingDateTime() != null) {
					list.setResolvingDate(format.format(list.getResolvingDateTime()));
				}
				if(list.getISSUES_REPORTED_DATE_ON() != null) {
					list.setIssuesReportedDateStr(format.format(list.getISSUES_REPORTED_DATE_ON()));
				}
				if(list.getIssuesReportedDateStr() != "--" && list.getIssuesReportedDateStr() != null && 
						list.getResolvingDate() != "--" && list.getResolvingDate() != null) {
					list.setTotalTime(DateTimeUtility.getDifferenceHours(list.getIssuesReportedDateStr(), list.getResolvingDate()));
				}

				Dtos.add(list);
			}
		}

	}catch (Exception e) {
		e.printStackTrace();
	}
	return Dtos;
}

	
	@Override
	public IssuesDto getIssueBasicDetails(Long issueId, Integer companyId) throws Exception {
	
		Query query = entityManager
				.createQuery("SELECT I.ISSUES_ID, I.ISSUES_VID, I.ISSUES_NUMBER, I.ISSUES_SUMMARY, V.vehicle_registration From Issues AS I "
						+ " INNER JOIN Vehicle AS V ON V.vid = I.ISSUES_VID "
						+ " Where I.ISSUES_ID= "+ issueId +" AND I.COMPANY_ID = "+companyId+" AND I.markForDelete = 0 ");
	
		Object[] issues = null;
	
		try {
			issues = (Object[]) query.getSingleResult();
		} catch (NoResultException e) {
			
		}
		IssuesDto select = new IssuesDto();
		if (issues != null) {
			select.setISSUES_ID((Long) issues[0]);
			select.setISSUES_VID((Integer) issues[1]);
			select.setIssuesNumberStr("I-"+(Long) issues[2]);
			select.setISSUES_SUMMARY((String) issues[3]);
			select.setISSUES_VEHICLE_REGISTRATION((String) issues[4]);
		} else {
			return null;
		}
		return select;
	}
	
	@Transactional
	@Override
	public void updateCreateDSE_IssueStatus(short Status, Long CloseBy, Date close_date, Long dealerServiceEntriesId, Long Issues_ID,Integer companyId)throws Exception  {
		try {
			issuesRepository.updateCreateDSE_IssueStatus(Status, CloseBy, close_date, dealerServiceEntriesId, Issues_ID, companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Transactional
	@Override
	public void update_DSE_Issue_Details(Long Issues_ID,Integer companyId)throws Exception {
		try {
			issuesRepository.update_DSE_Issue_Details( Issues_ID, companyId);
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public Object[] countOverdueIssues(CustomUserDetails userDetails) throws Exception {
		Query queryt =	null;
		if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			
			queryt = entityManager
					.createQuery("SELECT COUNT(co),(SELECT  COUNT(P) FROM Issues AS P where  P.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND P.COMPANY_ID = "+userDetails.getCompany_id()+" AND P.markForDelete = 0 "
							+ " AND P.ISSUES_LABELS_ID IN ( "+IssuesTypeConstant.ISSUES_LABELS_NORMAL+", "+IssuesTypeConstant.ISSUES_LABELS_LOW+", "+IssuesTypeConstant.ISSUES_LABELS_HIGH+" ) AND P.ISSUES_REPORTED_DATE <= '"+DateTimeUtility.getBackDateStrWithTime(3)+"') ,"
							+ "(SELECT  COUNT(DO) FROM Issues AS DO where DO.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND DO.COMPANY_ID = "+userDetails.getCompany_id()+" AND DO.markForDelete = 0 "
							+ " AND DO.ISSUES_LABELS_ID = "+IssuesTypeConstant.ISSUES_LABELS_URGENT+"  AND DO.ISSUES_REPORTED_DATE <= '"+DateTimeUtility.getBackDateStrWithTime(2)+"' ) "
							+ " FROM Issues As co  "
							+ " where co.ISSUES_STATUS_ID  = "+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND co.COMPANY_ID = "+userDetails.getCompany_id()+" AND co.markForDelete = 0 "
							+ " AND co.ISSUES_LABELS_ID IN ( "+IssuesTypeConstant.ISSUES_LABELS_VERY_URGENT+", "+IssuesTypeConstant.ISSUES_LABELS_BREAKDOWN+" ) AND co.ISSUES_REPORTED_DATE <= '"+DateTimeUtility.getBackDateStrWithTime(1)+"') ");
		}else {
			
			queryt = entityManager
					.createQuery("SELECT COUNT(co),(SELECT  COUNT(P) FROM Issues AS P "
							+ " INNER JOIN VehicleGroupPermision VGP1 ON VGP1.vg_per_id = P.VEHICLE_GROUP_ID AND VGP1.user_id = "+userDetails.getId()+" "
							+ " where  P.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND P.COMPANY_ID = "+userDetails.getCompany_id()+" AND P.markForDelete = 0 "
							+ " AND P.ISSUES_LABELS_ID IN ( "+IssuesTypeConstant.ISSUES_LABELS_NORMAL+", "+IssuesTypeConstant.ISSUES_LABELS_LOW+", "+IssuesTypeConstant.ISSUES_LABELS_HIGH+" ) AND P.ISSUES_REPORTED_DATE <= '"+DateTimeUtility.getBackDateStrWithTime(3)+"') ,"
							+ "(SELECT  COUNT(DO) FROM Issues AS DO "
							+ " INNER JOIN VehicleGroupPermision VGP3 ON VGP3.vg_per_id = DO.VEHICLE_GROUP_ID AND VGP3.user_id = "+userDetails.getId()+" "
							+ " where DO.ISSUES_STATUS_ID ="+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND DO.COMPANY_ID = "+userDetails.getCompany_id()+" AND DO.markForDelete = 0 "
							+ " AND DO.ISSUES_LABELS_ID = "+IssuesTypeConstant.ISSUES_LABELS_URGENT+"  AND DO.ISSUES_REPORTED_DATE <= '"+DateTimeUtility.getBackDateStrWithTime(2)+"' ) "
							+ " FROM Issues As co  "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = co.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
							+ " where co.ISSUES_STATUS_ID  = "+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND co.COMPANY_ID = "+userDetails.getCompany_id()+" AND co.markForDelete = 0 "
							+ " AND co.ISSUES_LABELS_ID IN ( "+IssuesTypeConstant.ISSUES_LABELS_VERY_URGENT+", "+IssuesTypeConstant.ISSUES_LABELS_BREAKDOWN+" ) AND co.ISSUES_REPORTED_DATE <= '"+DateTimeUtility.getBackDateStrWithTime(1)+"') ");
		}
 
		Object[] count = (Object[]) queryt.getSingleResult();

		return count;
	}
@Override
public void deleteExistingIssueIfUpdatedAsSuccess(Long completionToParameterId,int vid) throws Exception{
	InspectionCompletionToParameter issueIdDetails = null;
	CustomUserDetails		userDetails	= null;
	Date 						currentDate  				= null;
	Timestamp 					currnetTimeStamp 			= null;
	try {
		currentDate 			= new Date();                                             
		currnetTimeStamp 		= new java.sql.Timestamp(currentDate.getTime()); 
		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		issueIdDetails =	inspectionCompletionToParameterRepository.getFailedParameterIsuuesId(completionToParameterId, vid);
		
		if(issueIdDetails != null && issueIdDetails.getIssueId() != null && issueIdDetails.getIsInspectionSuccess() == 2) {
			issuesRepository.delete_Issues(issueIdDetails.getIssueId(), userDetails.getCompany_id(),userDetails.getId(), currnetTimeStamp);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
}

@Override
public ValueObject prepareValueObject(Long parameterId, int vid , String remark,Long inspectionSheetId,Long completionToParameterId) throws Exception {


	
	Date 					currentDate  		= null;
	ValueObject           	valueObject           = null;
	InspectionParameter  	inspectParameter      = null;  
	CustomUserDetails		userDetails	= null;
	InspectionCompletionToParameter issueIdDetails = null;
	
	try {
		userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object> 		configuration			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.INSPECTION_CONFIG);
		inspectParameter = inspectParameterRepository.getParameterById(parameterId,userDetails.getCompany_id());
		LocalDateTime now = LocalDateTime.now();  
		currentDate 		= new Date();                                             
		valueObject			= new ValueObject();
		valueObject.put("userDetails", userDetails);
		if(completionToParameterId!= null) {
			issueIdDetails =	inspectionCompletionToParameterRepository.getFailedParameterIsuuesId(completionToParameterId, vid);
		}
		if(issueIdDetails != null && issueIdDetails.getIssueId() != null ) {
			valueObject.put("issuesId",issueIdDetails.getIssueId());
		}
		
		valueObject.put("issueType",(short)1);
		valueObject.put("vid",vid);
		valueObject.put("reportdDate",dateFormat.format(currentDate));
		valueObject.put("issueStartTime",timeFormatter.format(now));
		valueObject.put("issuesSummary",inspectParameter.getParameterName());
		valueObject.put("issueLabel",(short)1);
		valueObject.put("reportedById",userDetails.getId());
		valueObject.put("subscribe",configuration.getOrDefault("issueAsignId",""));
		valueObject.put("description",remark);
		return valueObject;
		
	} catch (Exception e) {
		LOGGER.error("err"+e);
		throw e;
		
	}
	
}

@Override
public ValueObject getIssueBreakDownDetails(ValueObject valueObject) throws Exception {
	try {
		valueObject.put("breakDownDetails", getIssueBreakDownDetailsByIssueId(valueObject.getLong("issuesId",0)));
		return valueObject;
	} catch (Exception e) {
		throw e;
	}
}

@Override
public IssueBreakDownDetailsDto getIssueBreakDownDetailsByIssueId(Long issueId) throws Exception {
	try {
		Query query = entityManager.createQuery(
				"SELECT R.issueBreakDownDetailsId, R.tripNumber, R.breakDownLocation, R.isVehicleReplaced, V.vehicle_registration,"
						+ " R.vehicleReplacedLocation, R.isTripCancelled, R.cancelledKM, R.replacedWithVid"
						+ " FROM IssueBreakDownDetails AS R "
						+ " LEFT JOIN Vehicle V ON V.vid = R.replacedWithVid"
						+ " WHERE R.issueId = "+issueId+" AND R.markForDelete = 0 order by R.issueBreakDownDetailsId desc");
		
		Object[] result = null;
		try {
			
			query.setMaxResults(1);
			
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		IssueBreakDownDetailsDto list;
		if (result != null) {
			list = new IssueBreakDownDetailsDto();
			
			list.setIssueBreakDownDetailsId((Long) result[0]);
			list.setTripNumber((String) result[1]);
			list.setBreakDownLocation((String) result[2]);
			list.setIsVehicleReplaced((Boolean) result[3]);
			list.setVehicleNumber((String) result[4]);
			list.setVehicleReplacedLocation((String) result[5]);
			list.setIsTripCancelled((Boolean) result[6]);
			list.setCancelledKM((Double) result[7]);
			list.setReplacedWithVid((Integer) result[8]);
			
			if(list.getIsVehicleReplaced()) {
				list.setVehicleReplacedStr("Yes");
			}else {
				list.setVehicleReplacedStr("No");
			}
			
			if(list.getIsTripCancelled()) {
				list.setTripCancelledStr("Yes");
			}else {
				list.setTripCancelledStr("No");
			}
			
			
		} else {
			return null;
		}
		return list;
	}catch (Exception e) {
		throw e;
	}
}

@Transactional
public ValueObject updateIssueType(ValueObject valueObject) throws Exception {
	Issues 					issues 			= null;
	String 					msg 			= "";
	StringBuilder 			strBuilder 		= null;
	String[] 				strArr 			= null;
	VehicleDto 				checkVehicleStatus = null;
	CustomUserDetails 		userDetails 	= null;
	try {
		userDetails    = Utility.getObject(valueObject);
		strBuilder = new StringBuilder();
		issues	=	issuesRepository.getIssueDetailsByIssueId(valueObject.getLong("issueId",0), valueObject.getInt("companyId"));
		if(issues != null && issues.getISSUE_TYPE_ID() != IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN) {
			
			
			if(valueObject.getInt("replacedVehicle",0) == valueObject.getInt("vid",0)) {
				valueObject.put("replaceVehicleAndIssueVehicleSame", true);
				return valueObject;
			}
			checkVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("replacedVehicle",0),valueObject.getInt("companyId"));
			if (checkVehicleStatus != null && (checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE  || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SURRENDER || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SOLD )) {
				
				valueObject.put("replaceVehicleNotActive", true);
				valueObject.put("replaceVehicleStatus", VehicleStatusConstant.getVehicleStatus(checkVehicleStatus.getvStatusId()));
				return valueObject;
			}
			updateIssueTypeToBreakDown(valueObject);
			valueObject.put("changedToBreakDown", true);
			valueObject.put("userDetails", userDetails);
			issueBreakDownDetailsRepository.save(IssuesBL.getIssueBreakDownDetailsDTO(valueObject,issues));
			
			if(issues.getISSUES_ASSIGN_TO() != null && !issues.getISSUES_ASSIGN_TO().trim().equals("")) {
				strArr = issues.getISSUES_ASSIGN_TO().split(",");
				for(String name : strArr) {
					if(!name.trim().equals("")) {
						UserProfileDto user=userProfileService.get_UserProfile_Issues_Details(name,valueObject.getInt("companyId"));
						if(user!= null) {
							strBuilder.append(user.getUser_id()+",");	
						}
					}
				}
			}
			issuesService.registerNew_IssuesTasks(issuesBL.prepareTaskDetailsForIssueTypeChange(issues, userDetails));
			valueObject.put("subscriberIds",strBuilder.toString());
			msg="Issue Type changed from "+IssuesTypeConstant.getIssueTypeName(issues.getISSUE_TYPE_ID())+" to "+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN_NAME;
			sendNotification(valueObject,issues,msg);
			valueObject.put("success", true);
		}else {
			valueObject.put("already", true);
		}

		
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

@Transactional
public void updateIssueTypeToBreakDown(ValueObject valueObject) throws Exception {
	
	try {
		entityManager.createQuery(" UPDATE Issues SET ISSUE_TYPE_ID = "+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN+", "
				+ " remark = '"+valueObject.getString("remark","")+"' WHERE ISSUES_ID ="+valueObject.getLong("issueId",0)+" AND COMPANY_ID="+valueObject.getInt("companyId")+"  ").executeUpdate();
		
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
			
	
}
@Transactional
public List<IssuesDto> findIssueListByIssueType(CustomUserDetails userDetails, short issueType) throws Exception {
	
	TypedQuery<Object[]> 			queryt 			= null;
	List<IssuesDto> 				Dtos 			= null;
	IssuesDto 						list 			= null;
	
	if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
		queryt = entityManager.createQuery(
				"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
						+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
						+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER ,D.driver_Lastname"
						+ " ,D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName, IA.issueAnalysisId FROM Issues AS R "
						+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
						+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
						+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
						+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
						+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
						+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
						+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
						+ " LEFT JOIN IssueAnalysis IA ON IA.issueId = R.ISSUES_ID"
						+ " Where R.ISSUE_TYPE_ID= "+issueType+" AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0  ORDER BY R.ISSUES_ID desc, R.ISSUES_STATUS_ID asc",
				Object[].class);
	}else {
		
		queryt = entityManager.createQuery(
				"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
						+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
						+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER ,D.driver_Lastname"
						+ " ,D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName, IA.issueAnalysisId FROM Issues AS R "
						+ " LEFT JOIN VehicleGroup VG ON VG.gid = R.VEHICLE_GROUP_ID"
						+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
						+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
						+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
						+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
						+ " LEFT JOIN Branch B ON B.branch_id = R.ISSUES_BRANCH_ID"
						+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
						+ " LEFT JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = R.VEHICLE_GROUP_ID AND VGP.user_id = "+userDetails.getId()+" "
						+ " LEFT JOIN IssueAnalysis IA ON IA.issueId = R.ISSUES_ID"
						+ " Where R.ISSUE_TYPE_ID= "+issueType+" AND R.COMPANY_ID = "+userDetails.getCompany_id()+" AND R.markForDelete = 0  ORDER BY R.ISSUES_ID desc, R.ISSUES_STATUS_ID asc",
						Object[].class);
	}
	List<Object[]> results = queryt.getResultList();

	if (results != null && !results.isEmpty()) {
		Dtos 	= new ArrayList<IssuesDto>();
		
		for (Object[] result : results) {
			list = new IssuesDto();
			list.setISSUES_ID((Long) result[0]);
			list.setISSUES_SUMMARY((String) result[1]);
			list.setISSUES_LABELS_ID((short) result[2]);
			list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
			list.setISSUES_VID((Integer) result[4]);
			list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
			list.setISSUES_VEHICLE_GROUP((String) result[6]);
			list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
			list.setISSUES_TYPE_ID((short) result[8]);
			list.setISSUES_DRIVER_ID((Integer) result[9]);
			list.setISSUES_DRIVER_NAME((String) result[10]);
			list.setISSUES_BRANCH_ID((Integer) result[11]);
			list.setISSUES_BRANCH_NAME((String) result[12]);
			list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
			list.setCREATED_DATE_ON((Date) result[14]);
			list.setLASTUPDATED_DATE_ON((Date) result[15]);
			list.setISSUES_STATUS_ID((short) result[16]);
			list.setISSUES_NUMBER((Long) result[17]);
			if(result[18] != null && !((String)result[18]).trim().equals(""))
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[18]);
				if(result[19] != null && !((String)result[19]).trim().equals(""))
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[19]);
				
				list.setVehicleType((String) result[20]);
				
				list.setVehicleMaker((String) result[21]);
				if(result[22] != null) {
					list.setIssueAnalysisId((Long) result[22]);
					list.setIssueAnalysis(true);
				}else {
					list.setIssueAnalysis(false);
				}
			
			Dtos.add(list);
		}
	}
	return Dtos;
}

@SuppressWarnings("unchecked")
public ValueObject saveMultiIssues(ValueObject valueInObject) throws Exception {
	List<ValueObject>			multiIssues						= null;
	boolean                     isFromMob                       = false;
	try {
		isFromMob                = valueInObject.getBoolean("fromMob",false);
		ValueObject valueOutObject = new ValueObject();
		multiIssues =(List<ValueObject>) valueInObject.get("multiIssueDetails");
		if(!isFromMob){
			valueOutObject		= issuesService.saveIssuesDetails(valueInObject);
		}
		if(multiIssues!=null && !multiIssues.isEmpty()){
			for(ValueObject object : multiIssues) {
				valueInObject.put("issuesId",0);
				valueInObject.put("issuesSummary", object.getString("issuesSummary"," "));
				valueInObject.put("issueLabel", object.getShort("issueLabel_ID",(short) 0));
				valueInObject.put("partCategory", object.getInt("categoryId",0));
				valueOutObject =issuesService.saveIssuesDetails(valueInObject);
			}
		}
		return valueOutObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}	
}
public ValueObject getBreakDownAnalysisReport(ValueObject object) throws Exception {

	CustomUserDetails userDetails = null;
	ValueObject tableConfig = null;
	try {
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		int depot =  object.getInt("depot", 0);
		long vehicleType = object.getLong("VehicleTypeSelect",0);
		int vehicle =  object.getInt("vehicle", 0);
		String assignedTo = object.getString("subscribe1","");
		int route = object.getInt("routeList",0);
		short issueLabel = object.getShort("issueLabel",(short) 0);
		short issuestatus = object.getShort("status",(short) 0);
		short avoidable = object.getShort("avoidable",(short) 0);
		int partCategory = object.getInt("partCategory",0);

		String depotQ ="",vehicleTypeQ="",vehicleQ="",routeQ="",issueLabelQ="",issuestatusQ="",avoidableQ="",partCategoryQ="",assignedToQ="";
		
		StringBuilder finalQuery= new StringBuilder();
		if(depot > 0) 
			depotQ=" AND V.branchId = "+depot+" ";

		if(vehicleType > 0) 
			vehicleTypeQ= " AND V.vehicleTypeId = "+vehicleType+" ";

		if(vehicle >0) 
			vehicleTypeQ=" AND R.ISSUES_VID ="+vehicle+" ";	

		if(route > 0) 
			routeQ=" AND R.routeID = "+route+" ";

		if(issueLabel > 0) 
			issueLabelQ=" AND R.ISSUES_LABELS_ID = "+issueLabel+" ";

		if(issuestatus > 0)
			issuestatusQ =" AND R.ISSUES_STATUS_ID ="+issuestatus+" ";

		if(avoidable == 1) {
			avoidableQ =" AND IA.isAvoidable = 1 ";
		}else if (avoidable == 2) {
			avoidableQ =" AND IA.isAvoidable = 0 ";
		}
		if(partCategory > 0) 
			partCategoryQ =" AND R.categoryId = "+partCategory+" ";
			if(assignedTo != null && !assignedTo.trim().equalsIgnoreCase("") && assignedTo.indexOf('\'') != 0 ) {
			assignedToQ =" AND R.ISSUES_ASSIGN_TO_NAME LIKE ('%"+assignedTo+"%') ";
			}

		finalQuery.append(depotQ).append(vehicleQ).append(vehicleTypeQ).append(routeQ).append(issueLabelQ).append(issuestatusQ).append(avoidableQ).append(partCategoryQ).append(assignedToQ);

		object.put("finalQuery", finalQuery.toString());
			object.put("companyId",userDetails.getCompany_id());
			
			
			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.BREAK_DOWN_ANAIYSIS_REPORT);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
			
			
			List<IssuesDto>analysisList=  getBreakDownAnalysisReportList(object);
			
			if(analysisList != null && !analysisList.isEmpty()) {
				if(depot != 0) {
					object.put("depotKey", analysisList.get(0).getISSUES_BRANCH_NAME());	
				}else {
					object.put("depotKey", "ALL");	
				}
				if(vehicleType != 0) {
					object.put("vehicleTypetKey", analysisList.get(0).getVehicleType());	
				}else {
					object.put("vehicleTypetKey", "ALL");	
				}
				if(vehicle != 0) {
					object.put("vehicleKey", analysisList.get(0).getISSUES_VEHICLE_REGISTRATION());	
				}else {
					object.put("vehicleKey", "ALL");	
				}
				if(!assignedTo.trim().equals("")) {
					object.put("assignedToKey", assignedTo);	
				}else {
					object.put("assignedToKey", "ALL");	
				}
				if(route != 0) {
					object.put("routeKey", analysisList.get(0).getRouteName());	
				}else {
					object.put("routeKey", "ALL");	
				}
				if(issueLabel != 0) {
					object.put("issueLabelKey", IssuesTypeConstant.getIssuesLabelName(analysisList.get(0).getISSUES_LABELS_ID()));	
				}else {
					object.put("issueLabelKey", "ALL");	
				}
				if(issuestatus != 0) {
					object.put("statusKey", IssuesTypeConstant.getIssuesTaskStatusName(analysisList.get(0).getISSUES_STATUS_ID()));	
				}else {
					object.put("statusKey", "ALL");	
				}
				if(avoidable == 1) {
					object.put("avoidableKey","YES");
				}else if (avoidable == 2) {
					object.put("avoidableKey","NO");
				}else {
					object.put("avoidableKey","ALL");
				}
				if(partCategory != 0) {
					object.put("partCategoryKey",analysisList.get(0).getPartCategoryName());
				}else {
					object.put("partCategoryKey","ALL");
				}
			}
			
			object.put("tableConfig", tableConfig.getHtData());
			object.put("fuelEntryDetails", issuesBL.prepareIssueListSubscriberWise(analysisList));


		return object;
	} catch (Exception e) {
		throw e;
	}
}


@Transactional
public List<IssuesDto> getBreakDownAnalysisReportList(ValueObject object) throws Exception {
	
	TypedQuery<Object[]> 			queryt 			= null;
	List<IssuesDto> 				Dtos 			= null;
	IssuesDto 						list 			= null;
	
		queryt = entityManager.createQuery(
				"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
						+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
						+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER ,D.driver_Lastname"
						+ " ,D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName, IA.issueAnalysisId,TR.routeName ,IBR.tripNumber,R.location,PC.pcName,"
						+ "IA.reason,IA.isAvoidable,IA.tempSolution,IA.fixSolution,IA.futurePlan,R.ISSUES_ODOMETER,U.firstName,U.lastName,D.driver_badgenumber"
						+ ",D.driver_mobnumber,R.ISSUES_NUMBER FROM IssueAnalysis AS IA "
						+ " INNER JOIN Issues R ON R.ISSUES_ID = IA.issueId "
						+ " INNER JOIN Vehicle V ON V.vid = R.ISSUES_VID"
						+ " INNER JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
						+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
						+ " INNER JOIN User U ON U.id = R.CREATEDBYID"
						+ " LEFT JOIN Branch B ON B.branch_id = V.branchId"
						+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = R.routeID"
						+ " LEFT JOIN PartCategories PC ON PC.pcid = R.categoryId"
						+ " LEFT JOIN IssueBreakDownDetails IBR ON IBR.issueId = R.ISSUES_ID "
						+ " Where R.ISSUE_TYPE_ID= "+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN+" AND (R.ISSUES_REPORTED_DATE BETWEEN '"+object.getString("fromDate")+"' AND '"+object.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"') AND R.COMPANY_ID = "+object.getInt("companyId")+" "+object.getString("finalQuery"," ")+"  AND R.markForDelete = 0  ",
				Object[].class);
		
	List<Object[]> results = queryt.getResultList();
	if (results != null && !results.isEmpty()) {
		Dtos 	= new ArrayList<>();
		
		for (Object[] result : results) {
			list = new IssuesDto();
			list.setISSUES_ID((Long) result[0]);
			
			list.setISSUES_SUMMARY((String) result[1]);
			list.setISSUES_LABELS_ID((short) result[2]);
			list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
			list.setISSUES_VID((Integer) result[4]);
			list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
			list.setISSUES_VEHICLE_GROUP((String) result[6]);
			list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
			list.setISSUES_TYPE_ID((short) result[8]);
			list.setISSUES_DRIVER_ID((Integer) result[9]);
			if(result[10] != null) 
				list.setISSUES_DRIVER_NAME(result[35]+" "+result[10]);
			list.setISSUES_BRANCH_ID((Integer) result[11]);
			list.setISSUES_BRANCH_NAME((String) result[12]);
			list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
			list.setCREATED_DATE_ON((Date) result[14]);
			list.setLASTUPDATED_DATE_ON((Date) result[15]);
			list.setISSUES_STATUS_ID((short) result[16]);
			list.setISSUES_NUMBER((Long) result[17]);
			if(result[18] != null && !((String)result[18]).trim().equals(""))
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[18]);
				if(result[19] != null && !((String)result[19]).trim().equals(""))
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[19]);
				
				list.setVehicleType((String) result[20]);
				
				list.setVehicleMaker((String) result[21]);
				if(result[22] != null) {
					list.setIssueAnalysisId((Long) result[22]);
					list.setIssueAnalysis(true);
				}else {
					list.setIssueAnalysis(false);
				}
				list.setRouteName((String) result[23]);
				list.setTripNumber((String) result[24]);
				list.setLocation((String) result[25]);
				list.setPartCategoryName((String) result[26]);
				list.setReason((String) result[27]);
				list.setAvoidable((Boolean) result[28]);
				list.setTempSolution((String) result[29]);
				list.setFixSolution((String) result[30]);
				list.setFuturePlan((String) result[31]);
				list.setISSUES_ODOMETER( (Integer) result[32]);
				list.setAssignByName(result[33]+" "+result[34]);
				list.setDriver_mobnumber((String) result[36]);
				list.setIssuesNumberStr("<a target=_blank href=\"showIssues.in?Id="+AESEncryptDecrypt.encryptBase64(""+list.getISSUES_ID() )+"\">I-"+result[37]+" </a>");
			Dtos.add(list);
		}
	}
	return Dtos;
}

@SuppressWarnings("unchecked")
public Object[] getBreakDownIssuesCount(CustomUserDetails userDetails) {
	TypedQuery<Object[]> query = null;
	Object[] result = null;
	try {
		query = (TypedQuery<Object[]>) entityManager.createQuery("SELECT COUNT(I),"
				+ "(SELECT COUNT(A) FROM Issues AS A WHERE A.ISSUE_TYPE_ID ="+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN+" AND A.ISSUES_STATUS_ID = "+IssuesTypeConstant.ISSUES_STATUS_CLOSED+" AND A.COMPANY_ID = "+userDetails.getCompany_id()+" AND A.markForDelete = 0 ),"
				+ "(SELECT COUNT(B) FROM Issues AS B WHERE B.ISSUE_TYPE_ID ="+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN+" AND B.ISSUES_STATUS_ID = "+IssuesTypeConstant.ISSUES_STATUS_WOCREATED+" AND B.COMPANY_ID = "+userDetails.getCompany_id()+" AND B.markForDelete = 0),"
				+ "(SELECT COUNT(C) FROM Issues AS C WHERE C.ISSUE_TYPE_ID ="+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN+" AND C.ISSUES_STATUS_ID = "+IssuesTypeConstant.ISSUES_STATUS_RESOLVED+" AND C.COMPANY_ID = "+userDetails.getCompany_id()+" AND C.markForDelete = 0),"
				+ "(SELECT COUNT(D) FROM Issues AS D WHERE D.ISSUE_TYPE_ID ="+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN+" AND D.ISSUES_STATUS_ID = "+IssuesTypeConstant.ISSUES_STATUS_REJECT+" AND D.COMPANY_ID = "+userDetails.getCompany_id()+" AND D.markForDelete = 0),"
				+ "(SELECT COUNT(E) FROM Issues AS E WHERE E.ISSUE_TYPE_ID ="+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN+" AND E.ISSUES_STATUS_ID = "+IssuesTypeConstant.ISSUES_STATUS_DSE_CREATED+" AND E.COMPANY_ID = "+userDetails.getCompany_id()+" AND E.markForDelete = 0)"
				+ " FROM Issues AS I WHERE I.ISSUE_TYPE_ID ="+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN+" AND I.ISSUES_STATUS_ID = "+IssuesTypeConstant.ISSUES_STATUS_OPEN+" AND I.COMPANY_ID = "+userDetails.getCompany_id()+" AND I.markForDelete = 0")
				;
		result=query.getSingleResult();
	} catch (Exception e) {
		e.printStackTrace();
	}
	return result;
}
public List<UserProfileDto> getAssigneeListByRespectiveIssueId(List<IssuesDto> issuesDto) throws Exception {
	StringBuilder strbuilder = new StringBuilder();
	String subscribers = "";
	String [] strArray = null;
	HashMap<String, UserProfileDto> userList = new HashMap<>();
	Collection<UserProfileDto> userListValues =null;
	ArrayList<UserProfileDto> finalUserList = null;
	try {
		for(IssuesDto dto:issuesDto) {
			if(dto.getISSUES_ASSIGN_TO_NAME()!= null && !dto.getISSUES_ASSIGN_TO_NAME().trim().equals("")) {
				strbuilder.append(","+dto.getISSUES_ASSIGN_TO_NAME());
			}
		}
		subscribers =strbuilder.toString();
		strArray = subscribers.split(",");
		for(String sub : strArray) {
			if(!sub.trim().equals("") && userList.get(sub) == null) {
				UserProfileDto user=userProfileService.get_UserProfile_Issues_Details(sub);
				if(user != null) {
					userList.put(sub, user);
				}
			}
		}
		userListValues = userList.values();
		finalUserList = new ArrayList<>(userListValues);
		return finalUserList;

	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
}

@Override
public List<IssuesDto> getWOIssueList(Long workordersId,String issueIds, Integer companyId) throws Exception {
	List<IssuesDto> issuesList = null;
	Map<Long, IssuesDto> issueHash = null;
	try {
		TypedQuery<Object[]> query = entityManager
				.createQuery("SELECT I.ISSUES_ID, I.ISSUES_VID, I.ISSUES_NUMBER, I.ISSUES_SUMMARY, V.vehicle_registration,"
						+ " I.ISSUES_DRIVER_ID, D.driver_firstname, D.driver_Lastname, D.driver_fathername,D.driver_mobnumber,I.ISSUES_REPORTED_DATE,W.remark,WT.workordertaskid,"
						+ " U.id,U.firstName ,I.ISSUES_ASSIGN_TO_NAME  "
						+ "From Issues AS I "
						+ " INNER JOIN Vehicle AS V ON V.vid = I.ISSUES_VID "
						+ " INNER JOIN User AS U ON U.email = SUBSTRING_INDEX(I.ISSUES_ASSIGN_TO,',',1)  AND U.company_id = "+companyId+" "
						+ " LEFT JOIN Driver D ON D.driver_id = ISSUES_DRIVER_ID"
						+ " LEFT JOIN WorkOrderRemark W ON W.issueId = I.ISSUES_ID AND W.workOrderId = "+workordersId+" "
						+ " LEFT JOIN WorkOrdersTasks WT ON WT.issueIds = I.ISSUES_ID AND WT.workorders.workorders_id = "+workordersId+" AND WT.markForDelete=0 "
						+ " Where I.ISSUES_ID IN("+ issueIds +")  AND I.COMPANY_ID = "+companyId+" AND I.markForDelete = 0 ",Object[].class);
		List<Object[]> results = null;

		results = query.getResultList();
		if(results != null && !results.isEmpty()) {
			issueHash = new HashMap<>();
			IssuesDto select = null;
			for(Object[] result : results) {
				if(issueHash.get(result[0]) == null) {
					select = new IssuesDto();
					select.setISSUES_ID((Long) result[0]);
					select.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64(""+select.getISSUES_ID()));
					select.setISSUES_VID((Integer) result[1]);
					select.setISSUES_NUMBER((Long) result[2]);
					select.setIssuesNumberStr("I-"+(Long) result[2]);
					select.setISSUES_SUMMARY((String) result[3]);
					select.setISSUES_VEHICLE_REGISTRATION((String) result[4]);
					if(result[6] != null) {
						select.setISSUES_DRIVER_NAME((String)result[6]+"_"+(String)result[7]+"_"+(String)result[8]);
						if(select.getISSUES_DRIVER_NAME().contains("_null")) {
							select.setISSUES_DRIVER_NAME(select.getISSUES_DRIVER_NAME().replace("_null", ""));
						}
					}
					select.setDriver_mobnumber((String) result[9]);
					if(result[10] != null)
					select.setISSUES_REPORTED_DATE(dateFormat_Name.format(result[10]));
					select.setWoRemark((String) result[11]);
					select.setWorkOrderTaskId((Long) result[12]);
					select.setUserId((Long) result[13]);
					select.setUserName((String) result[14]);
					select.setISSUES_ASSIGN_TO_NAME((String) result[15]);
					issueHash.put(select.getISSUES_ID(), select);
				}
			}
			issuesList = new ArrayList<>(issueHash.values());
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return issuesList;
}

public ValueObject getIssueTypeChangedReport(ValueObject object) throws Exception {

	CustomUserDetails userDetails = null;
	ValueObject tableConfig = null;
	try {
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		int depot =  object.getInt("depot", 0); 
		int vehicle =  object.getInt("vehicle", 0);
		int route = object.getInt("routeList",0);
		int partCategory = object.getInt("partCategory",0);
		int vehicleModel = object.getInt("vehicleModel",0);
		int driverId = object.getInt("driverId",0);
		long vehicleType = object.getLong("VehicleTypeSelect",0);
		String assignedTo = object.getString("subscribe1","");
		short issueLabel = object.getShort("issueLabel",(short) 0);
		short issuestatus = object.getShort("status",(short) 0);
		
		StringBuilder finalQuery= new StringBuilder();
		if(depot > 0) 
			finalQuery.append(" AND V.branchId = "+depot+" ");

		if(vehicleType > 0) 
			finalQuery.append(" AND V.vehicleTypeId = "+vehicleType+" ");
		
		if(vehicleModel > 0)
			finalQuery.append(" AND V.vehicleModalId = "+vehicleModel+" ");
		
		if(vehicle >0) 
			finalQuery.append(" AND R.ISSUES_VID ="+vehicle+" ");	

		if(route > 0) 
			finalQuery.append(" AND R.routeID = "+route+" ");

		if(issueLabel > 0) 
			finalQuery.append(" AND R.ISSUES_LABELS_ID = "+issueLabel+" ");

		if(issuestatus > 0)
			finalQuery.append(" AND R.ISSUES_STATUS_ID ="+issuestatus+" ");

		if(partCategory > 0) 
			finalQuery.append(" AND R.categoryId = "+partCategory+" ");
		if(assignedTo != null && !assignedTo.trim().equalsIgnoreCase("") && assignedTo.indexOf('\'') != 0 ) 
			finalQuery.append(" AND R.ISSUES_ASSIGN_TO_NAME LIKE ('%"+assignedTo+"%') ");
		if(driverId > 0)
			finalQuery.append(" AND R.ISSUES_DRIVER_ID = "+driverId +" ");



		object.put("finalQuery", finalQuery.toString());
		object.put("companyId",userDetails.getCompany_id());
			
			
			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.ISSUE_TYPE_CHANGED_REPORT);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
			
			
			List<IssuesDto> analysisList =  getissueChangedReportList(object);
			
			if(analysisList != null && !analysisList.isEmpty()) {
			if(depot != 0) {
				object.put("depotKey", analysisList.get(0).getISSUES_BRANCH_NAME());	
			}else {
				object.put("depotKey", "ALL");	
			}
			if(vehicleType != 0) {
				object.put("vehicleTypetKey", analysisList.get(0).getVehicleType());	
			}else {
				object.put("vehicleTypetKey", "ALL");	
			}
			if(vehicle != 0) {
				object.put("vehicleKey", analysisList.get(0).getISSUES_VEHICLE_REGISTRATION());	
			}else {
				object.put("vehicleKey", "ALL");	
			}
			if(assignedTo != null && !assignedTo.trim().equals("")) {
				object.put("assignedToKey", assignedTo);	
			}else {
				object.put("assignedToKey", "ALL");	
			}
			if(route != 0) {
				object.put("routeKey", analysisList.get(0).getRouteName());	
			}else {
				object.put("routeKey", "ALL");	
			}
			if(issueLabel != 0) {
				object.put("issueLabelKey", IssuesTypeConstant.getIssuesLabelName(analysisList.get(0).getISSUES_LABELS_ID()));	
			}else {
				object.put("issueLabelKey", "ALL");	
			}
			if(issuestatus != 0) {
				object.put("statusKey", IssuesTypeConstant.getIssuesTaskStatusName(analysisList.get(0).getISSUES_STATUS_ID()));	
			}else {
				object.put("statusKey", "ALL");	
			}
			if(partCategory != 0) {
				object.put("partCategoryKey",analysisList.get(0).getPartCategoryName());
			}else {
				object.put("partCategoryKey","ALL");
			}
			if(driverId > 0) {
				object.put("driverKey",analysisList.get(0).getISSUES_DRIVER_NAME());
			}else {
				object.put("driverKey","ALL");
			}
			if(vehicleModel > 0) {
				object.put("vehicleModelKey",analysisList.get(0).getVehicleModel());
			}else {
				object.put("vehicleModelKey","ALL");
			}
			}
			
			object.put("tableConfig", tableConfig.getHtData());
			object.put("fuelEntryDetails", issuesBL.prepareIssueListSubscriberWise(analysisList));


		return object;
	} catch (Exception e) {
		throw e;
	}
}


@Transactional
public List<IssuesDto> getissueChangedReportList(ValueObject object) throws Exception {
	
	TypedQuery<Object[]> 			queryt 			= null;
	List<IssuesDto> 				Dtos 			= null;
	IssuesDto 						list 			= null;
	String 							link          	= "";
	
		queryt = entityManager.createQuery(
				"SELECT R.ISSUES_ID, R.ISSUES_SUMMARY, R.ISSUES_LABELS_ID, R.ISSUES_REPORTED_DATE, R.ISSUES_VID, V.vehicle_registration, "
						+ "VG.vGroup, R.ISSUES_ASSIGN_TO_NAME, R.ISSUE_TYPE_ID, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
						+ "DP.depart_name, R.CREATED_DATE, R.LASTUPDATED_DATE, R.ISSUES_STATUS_ID, R.ISSUES_NUMBER ,D.driver_Lastname"
						+ " ,D.driver_fathername, VT.vtype ,VM.vehicleManufacturerName,TR.routeName ,IBR.tripNumber,R.location,PC.pcName,"
						+ "R.ISSUES_ODOMETER,U.firstName,U.lastName,D.driver_badgenumber,D.driver_mobnumber,R.ISSUES_NUMBER,"
						+ "IBR.isVehicleReplaced,VV.vehicle_registration,IBR.vehicleReplacedLocation,IBR.isTripCancelled,IBR.cancelledKM,"
						+ "VMM.vehicleModelName,R.remark,IT.ISSUES_CREATED_DATE,R.WORKORDERS_ID,WO.workorders_Number,R.dealerServiceEntriesId,DSE.dealerServiceEntriesNumber FROM Issues AS R  "
						+ " INNER JOIN IssueBreakDownDetails IBR ON IBR.issueId = R.ISSUES_ID "
						+ " INNER JOIN Vehicle V ON V.vid = R.ISSUES_VID"
						+ " LEFT JOIN Vehicle VV ON VV.vid = IBR.replacedWithVid"
						+ " INNER JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
						+ " LEFT JOIN VehicleModel VMM ON VMM.vehicleModelId = V.vehicleModalId"
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " INNER JOIN User U ON U.id = R.CREATEDBYID"
						+ " LEFT JOIN VehicleManufacturer VM ON VM.vehicleManufacturerId = V.vehicleMakerId"
						+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.WORKORDERS_ID"
						+ " LEFT JOIN DealerServiceEntries DSE ON DSE.dealerServiceEntriesId = R.dealerServiceEntriesId"
						+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
						+ " LEFT JOIN IssuesTasks IT ON IT.ISSUES.ISSUES_ID = R.ISSUES_ID AND IT.ISSUES_CHANGE_STATUS_ID = "+IssuesTypeConstant.ISSUES_TASK_STATUS_TYPE_CHANGED+""
						+ " LEFT JOIN Branch B ON B.branch_id = V.branchId"
						+ " LEFT JOIN Department DP ON DP.depart_id = R.ISSUES_DEPARTNMENT_ID"
						+ " LEFT JOIN TripRoute TR ON TR.routeID = R.routeID"
						+ " LEFT JOIN PartCategories PC ON PC.pcid = R.categoryId"
						+ " Where IBR.changedToBreakDown = 1 AND (R.ISSUES_REPORTED_DATE BETWEEN '"+object.getString("fromDate")+"' AND '"+object.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"') AND R.COMPANY_ID = "+object.getInt("companyId")+" "+object.getString("finalQuery"," ")+"  AND R.markForDelete = 0  ",
				Object[].class);
	List<Object[]> results = queryt.getResultList();
	if (results != null && !results.isEmpty()) {
		Dtos 	= new ArrayList<>();
		
		for (Object[] result : results) {
			list = new IssuesDto();
			list.setISSUES_ID((Long) result[0]);
			
			list.setISSUES_SUMMARY((String) result[1]);
			list.setISSUES_LABELS_ID((short) result[2]);
			list.setISSUES_REPORTED_DATE_ON((Date) result[3]);
			list.setISSUES_VID((Integer) result[4]);
			list.setISSUES_VEHICLE_REGISTRATION((String) result[5]);
			list.setISSUES_VEHICLE_GROUP((String) result[6]);
			list.setISSUES_ASSIGN_TO_NAME((String) result[7]);
			list.setISSUES_TYPE_ID((short) result[8]);
			list.setISSUES_DRIVER_ID((Integer) result[9]);
			if(result[10] != null) 
				list.setISSUES_DRIVER_NAME(result[29]+" "+result[10]);
			list.setISSUES_BRANCH_ID((Integer) result[11]);
			list.setISSUES_BRANCH_NAME((String) result[12]);
			list.setISSUES_DEPARTNMENT_NAME((String) result[13]);
			list.setCREATED_DATE_ON((Date) result[14]);
			list.setLASTUPDATED_DATE_ON((Date) result[15]);
			list.setISSUES_STATUS_ID((short) result[16]);
			list.setISSUES_NUMBER((Long) result[17]);
			if(result[18] != null && !((String)result[18]).trim().equals(""))
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+ result[18]);
				if(result[19] != null && !((String)result[19]).trim().equals(""))
				list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+ result[19]);
				list.setVehicleType((String) result[20]);
				list.setVehicleMaker((String) result[21]);
				list.setRouteName((String) result[22]);
				list.setTripNumber((String) result[23]);
				list.setLocation((String) result[24]);
				list.setPartCategoryName((String) result[25]);
				list.setISSUES_ODOMETER( (Integer) result[26]);
				list.setAssignByName(result[27]+" "+result[28]);
				list.setDriver_mobnumber((String) result[30]);
				list.setIssuesNumberStr("<a target=_blank href=\"showIssues.in?Id="+AESEncryptDecrypt.encryptBase64(""+list.getISSUES_ID() )+"\">I-"+result[31]+" </a>");
				list.setIsVehicleReplaced((Boolean)result[32]);
				if(result[33] != null)
				list.setReplacedVehicle((String) result[33]);
				if(result[34] != null)
				list.setVehicleReplacedLocation((String) result[34]);
				list.setIsTripCancelled((Boolean) result[35]);
				if(result[36] != null)
				list.setCancelledKM((Double) result[36]);
				list.setVehicleModel((String) result[37]);
				if(result[38] != null)
				list.setRemark((String) result[38]);
				if(result[39] != null)
				list.setIssueChangedOn((Date) result[39]);
				if(result[40] != null )
				list.setISSUES_WORKORDER_ID((Long) result[40]);
				if(list.getISSUES_WORKORDER_ID() != null && list.getISSUES_WORKORDER_ID() > 0)
					link = "<a href=\"showWorkOrder?woId="+list.getISSUES_WORKORDER_ID()+"\" target=\"_blank\">WO-"+result[41]+"</a> ";
				if(result[42] != null )
					list.setDealerServiceEntriesId((Long) result[42]);
				if(list.getDealerServiceEntriesId() != null && list.getDealerServiceEntriesId() >0)
					link +=" <a href=\"showDealerServiceEntries?dealerServiceEntriesId="+list.getDealerServiceEntriesId()+"\" target=\"_blank\">DSE-"+result[43]+"</a>";
				list.setWoOrDseLink(link);
				Dtos.add(list);
		}
	}
	return Dtos;
}
public ValueObject getVehicleWiseIssueDetails(ValueObject valueObjectIn) throws Exception {
	CustomUserDetails				userDetails 			            = null;
	ValueObject 				    valueOutObject 					    = null;
	List<IssuesDto> 			    issuesList						    = null;
	List<UserProfileDto> 			subsciberList						= null;
	try{
		userDetails	        = Utility.getObject(valueObjectIn);
		valueOutObject		= new ValueObject();
		if(valueObjectIn.getInt("vid",0) > 0) {
			String	conditions = "";
			if(valueObjectIn.getLong("issueId",0) > 0) {
				conditions = " AND I.ISSUES_ID = "+valueObjectIn.getLong("issueId",0)+" ";
			}else {
				String issueIds =valueObjectIn.getString("issueId","");
				if(!issueIds.trim().equals("") && !issueIds.equals("0")) {
					conditions = " AND I.ISSUES_ID IN("+issueIds+") ";
				}
			}
			if(valueObjectIn.getLong("isEdit",0)==0) {
				conditions +=" AND I.ISSUES_STATUS_ID = "+IssuesTypeConstant.ISSUES_STATUS_OPEN+" ";
			}
			issuesList		= issuesService.getOpenIssueDetailsByVehicle(valueObjectIn.getInt("vid"),userDetails,valueObjectIn.getLong("issueId",0), conditions,valueObjectIn.getString("invoiceDate",""));
		}
		if(issuesList != null && !issuesList.isEmpty()) {
			subsciberList =getAssigneeListByRespectiveIssueId(issuesList,userDetails.getCompany_id());
			valueOutObject.put("issueDetails", issuesList);
			valueOutObject.put("subsciberList", subsciberList);
		}
		return valueOutObject;
	}catch(Exception e){
		e.printStackTrace();
		throw e;
	}
	finally{
		userDetails 			            = null;         
		valueOutObject 					    = null;         
		issuesList						    = null;         
		subsciberList						= null;         
	}
}

@Transactional
@Override
public void updateTyreAssignmentIssueStatus(short Status, Long CloseBy, Date close_date, Long lpid, Long Issues_ID,Integer companyId)throws Exception  {
	try {
		issuesRepository.updateTyreAssignmentIssueStatus(Status, CloseBy, close_date, lpid, Issues_ID, companyId);
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
}
@Override
public ValueObject getIssueAssignTyre(ValueObject valueObject)throws Exception  {
	List<VehicleTyreLayoutPositionDto>	vehicleTyreLayoutPositionList	= null;
	try {
		vehicleTyreLayoutPositionList = new ArrayList<>();
		vehicleTyreLayoutPositionList = vehicleTyreAssignmentService.getTyreAssignmentByTransactionTypeAndTransactionId(TyreAssignmentConstant.TRANSACTION_TYPE_ISSUE, valueObject.getLong("issueId"),valueObject.getInt("companyId"));
		valueObject.put("vehicleTyreLayoutPositionList", vehicleTyreLayoutPositionList);
		return valueObject;
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
}

@Transactional
@Override
public void updateTyreAssignmentIssueStatusByIssueId(short status, long issueId, int companyId)throws Exception  {
	try {
		issuesRepository.updateTyreAssignmentIssueStatus(status,issueId,companyId);
	} catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
	
}


@Override
public ValueObject getVehicleAndBreakDownReportDetails(ValueObject object) throws Exception {

	CustomUserDetails userDetails = null;
	ValueObject tableConfig = null;
	try {
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		int depot =  object.getInt("depot", 0);
		long vehicleType = object.getLong("VehicleTypeSelect",0);
		int vehicle =  object.getInt("vehicle", 0);
		String assignedTo = object.getString("subscribe1","");
		int route = object.getInt("routeList",0);
		short issueLabel = object.getShort("issueLabel",(short) 0);
		short issuestatus = object.getShort("status",(short) 0);
		int partCategory = object.getInt("partCategory",0);

		String depotQ ="",vehicleTypeQ="",vehicleQ="",routeQ="",issueLabelQ="",issuestatusQ="",partCategoryQ="",assignedToQ="";
		
		StringBuilder finalQuery= new StringBuilder();
		if(depot > 0) 
			depotQ=" AND V.branchId = "+depot+" ";

		if(vehicleType > 0) 
			vehicleTypeQ= " AND V.vehicleTypeId = "+vehicleType+" ";

		if(vehicle >0) 
			vehicleTypeQ=" AND R.ISSUES_VID ="+vehicle+" ";	

		if(route > 0) 
			routeQ=" AND R.routeID = "+route+" ";

		if(issueLabel > 0) 
			issueLabelQ=" AND R.ISSUES_LABELS_ID = "+issueLabel+" ";

		if(issuestatus > 0)
			issuestatusQ =" AND R.ISSUES_STATUS_ID ="+issuestatus+" ";

		
		if(partCategory > 0) 
			partCategoryQ =" AND R.categoryId = "+partCategory+" ";
		if(assignedTo != null && !assignedTo.trim().equalsIgnoreCase("") && assignedTo.indexOf('\'') != 0 ) 
			assignedToQ =" AND R.ISSUES_ASSIGN_TO_NAME LIKE ('%"+assignedTo+"%') ";


		finalQuery.append(depotQ).append(vehicleQ).append(vehicleTypeQ).append(routeQ).append(issueLabelQ).append(issuestatusQ).append(partCategoryQ).append(assignedToQ);

		if(object.getShort("issueTypeId",(short) 0) > 0) {
			finalQuery.append(" AND R.ISSUE_TYPE_ID = "+object.getShort("issueTypeId")+" ");
		}else {
			finalQuery.append(" AND R.ISSUE_TYPE_ID IN ("+IssuesTypeConstant.ISSUE_TYPE_VEHICLE+", "+IssuesTypeConstant.ISSUE_TYPE_BREAKDOWN+") ");
		}
		
		if(object.getLong("vehicleModel",0) > 0) {
			finalQuery.append(" AND V.vehicleModalId = "+object.getLong("vehicleModel",0)+" ");
		}
		
		if(object.getInt("driver",0) > 0) {
			finalQuery.append(" AND D.driver_id = "+object.getInt("driver",0)+" ");
		}
		
		object.put("finalQuery", finalQuery.toString());
		object.put("companyId",userDetails.getCompany_id());
			
			
			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VEHICLE_AND_BREAK_DOWN_REPORT);
			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
			
			
			List<IssuesDto>analysisList=  getVehicleAndBreakDownReportList(object);
			
			if(analysisList != null && !analysisList.isEmpty()) {
				if(depot != 0) {
					object.put("depotKey", analysisList.get(0).getISSUES_BRANCH_NAME());	
				}else {
					object.put("depotKey", "ALL");	
				}
				if(vehicleType != 0) {
					object.put("vehicleTypetKey", analysisList.get(0).getVehicleType());	
				}else {
					object.put("vehicleTypetKey", "ALL");	
				}
				if(vehicle != 0) {
					object.put("vehicleKey", analysisList.get(0).getISSUES_VEHICLE_REGISTRATION());	
				}else {
					object.put("vehicleKey", "ALL");	
				}
				if(!assignedTo.trim().equals("")) {
					object.put("assignedToKey", assignedTo);	
				}else {
					object.put("assignedToKey", "ALL");	
				}
				if(route != 0) {
					object.put("routeKey", analysisList.get(0).getRouteName());	
				}else {
					object.put("routeKey", "ALL");	
				}
				if(issueLabel != 0) {
					object.put("issueLabelKey", IssuesTypeConstant.getIssuesLabelName(analysisList.get(0).getISSUES_LABELS_ID()));	
				}else {
					object.put("issueLabelKey", "ALL");	
				}
				if(issuestatus != 0) {
					object.put("statusKey", IssuesTypeConstant.getIssuesTaskStatusName(analysisList.get(0).getISSUES_STATUS_ID()));	
				}else {
					object.put("statusKey", "ALL");	
				}
			
				if(partCategory != 0) {
					object.put("partCategoryKey",analysisList.get(0).getPartCategoryName());
				}else {
					object.put("partCategoryKey","ALL");
				}
			}
			
			object.put("tableConfig", tableConfig.getHtData());
			object.put("fuelEntryDetails", issuesBL.prepareIssueListSubscriberWise(analysisList));


		return object;
	} catch (Exception e) {
		throw e;
	}
}

@Override
public List<IssuesDto> getVehicleAndBreakDownReportList(ValueObject object) throws Exception {
	
	TypedQuery<Object[]> 			queryt 			= null;
	List<IssuesDto> 				Dtos 			= null;
	IssuesDto 						list 			= null;
	
		queryt = entityManager.createQuery(
				"SELECT R.ISSUES_ID, R.ISSUES_NUMBER, R.ISSUE_TYPE_ID, R.ISSUES_VID, V.vehicle_registration,"
						+ " R.ISSUES_ODOMETER, R.ISSUES_DRIVER_ID, D.driver_firstname, R.ISSUES_BRANCH_ID, B.branch_name,"
						+ " R.ISSUES_REPORTED_DATE, R.ISSUES_SUMMARY, R.ISSUES_DESCRIPTION, R.ISSUES_LABELS_ID, U.firstName,"
						+ " R.ISSUES_ASSIGN_TO_NAME, R.ISSUES_STATUS_ID, U2.email, R.CREATED_DATE, R.LASTUPDATED_DATE, R.CREATEDBYID, R.LASTMODIFIEDBYID,"
						+ " R.ISSUES_WORKORDER_DATE, R.ISSUES_REPORTED_BY_ID, R.ISSUES_ASSIGN_TO, D.driver_mobnumber, R.ISSUES_WORKORDER_ID, R.CUSTOMER_NAME,"
						+ " R.GPS_ODOMETER,R.ISSUES_SE_ID, R.ISSUES_SE_DATE, R.VEHICLE_GROUP_ID, R.vehicleCurrentOdometer, R.categoryId,Pc.pcName,R.routeID,Tr.routeName,R.dealerServiceEntriesId, D.driver_Lastname "
						+ ", D.driver_fathername ,R.location,VT.vtype, VM.vehicleModelName, IBD.tripNumber, IBD.breakDownLocation, IBD.isVehicleReplaced, IBD.replacedWithVid,"
						+ " IBD.vehicleReplacedLocation, IBD.isTripCancelled, IBD.cancelledKM, V2.vehicle_registration,"
						+ " WO.workorders_Number, DSE.dealerServiceEntriesNumber "
						+ " FROM Issues AS R"
						+ " LEFT JOIN User U ON U.id = R.ISSUES_REPORTED_BY_ID"
						+ " LEFT JOIN User U2 ON U2.id = R.CREATEDBYID"
						+ " LEFT JOIN Vehicle V ON V.vid = R.ISSUES_VID"
						+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
						+ " LEFT JOIN VehicleModel VM ON VM.vehicleModelId = V.vehicleModalId"
						+ " LEFT JOIN Driver D ON D.driver_id = R.ISSUES_DRIVER_ID"
						+ " LEFT JOIN Branch B ON B.branch_id = V.branchId"
						+ " LEFT JOIN PartCategories Pc ON Pc.pcid = R.categoryId"
						+ " LEFT JOIN TripRoute Tr ON Tr.routeID = R.routeID "
						+ " LEFT JOIN IssueBreakDownDetails IBD ON IBD.issueId = R.ISSUES_ID "
						+ " LEFT JOIN Vehicle V2 ON V2.vid = IBD.replacedWithVid "
						+ " LEFT JOIN WorkOrders WO ON WO.workorders_id = R.ISSUES_WORKORDER_ID"
						+ " LEFT JOIN DealerServiceEntries DSE ON DSE.dealerServiceEntriesId  = R.dealerServiceEntriesId"
						+ " WHERE (R.ISSUES_REPORTED_DATE BETWEEN '"+object.getString("fromDate")+"' AND "
						+ " '"+object.getString("toDate")+""+DateTimeUtility.DAY_END_TIME+"') "
						+ " AND R.COMPANY_ID = "+object.getInt("companyId")+" "+object.getString("finalQuery"," ")+"  "
						+ " AND R.markForDelete = 0 ", Object[].class);
		
	List<Object[]> results = queryt.getResultList();
	if (results != null && !results.isEmpty()) {
		Dtos 	= new ArrayList<>();
		
		for (Object[] result : results) {
			list = new IssuesDto();
			
			list.setISSUES_ID((long) result[0]);
			list.setISSUES_NUMBER((long) result[1]);
			list.setISSUES_TYPE_ID((short) result[2]);
			list.setISSUES_VID((Integer) result[3]);
			list.setISSUES_VEHICLE_REGISTRATION((String) result[4]);
			list.setISSUES_ODOMETER((Integer) result[5]);
			list.setISSUES_DRIVER_ID((Integer) result[6]);
			list.setISSUES_DRIVER_NAME((String) result[7]);
			list.setISSUES_BRANCH_ID((Integer) result[8]);
			list.setISSUES_BRANCH_NAME((String) result[9]);
			list.setISSUES_REPORTED_DATE_ON((Date) result[10]);
			list.setISSUES_SUMMARY((String) result[11]);
			list.setISSUES_DESCRIPTION((String) result[12]);
			list.setISSUES_LABELS_ID((short) result[13]);
			list.setISSUES_REPORTED_BY((String) result[14]);
			list.setISSUES_ASSIGN_TO_NAME((String) result[15]);
			list.setISSUES_STATUS_ID((short) result[16]);
			list.setCREATEDBY((String) result[17]);
			list.setCREATED_DATE_ON((Date) result[18]);
			list.setLASTUPDATED_DATE_ON((Date) result[19]);
			list.setCREATEDBYID((Long) result[20]);
			list.setLASTMODIFIEDBYID((Long) result[21]);
			list.setISSUES_WORKORDER_DATE_ON((Date) result[22]);
			list.setISSUES_REPORTED_BY_ID( (Long) result[23]);
			list.setISSUES_ASSIGN_TO((String) result[24]);
			list.setDriver_mobnumber((String) result[25]);
			list.setISSUES_WORKORDER_ID((Long) result[26]);
			if(result[27] != null) {
				list.setCUSTOMER_NAME((String) result[27]);
			}
			if(result[28] != null) {
				list.setGPS_ODOMETER((Double) result[28]);
			}
			if(result[29] != null) {
				list.setISSUES_SE_ID((Long) result[29]);
				list.setISSUES_SE_DATEON((Date) result[30]);
			}else {
				list.setISSUES_SE_ID((long)0);
			}
			list.setVEHICLE_GROUP_ID((Long) result[31]);
			if(result[32] != null) {
				list.setVehicleCurrentOdometer((Integer) result[32]);
			}else {
				list.setVehicleCurrentOdometer(0);
			}
			if(result[33] != null) {
				list.setCategoryId((Integer) result[33]);
			}else {
				list.setCategoryId(0);
			}
			if(result[34] != null) {
				list.setPartCategoryName((String) result[34]);
			}else {
				list.setPartCategoryName("");				
			}
			if(result[41] != null) {
				list.setRouteID((Integer) result[35]);
			}else {
				list.setRouteID(0);				
			}
			if(result[36] != null) {
				list.setRouteName((String) result[36]);
			}else {
				list.setRouteName("");				
			}
			if(result[37] != null) {
				list.setDealerServiceEntriesId((Long) result[37]);
			}
			if(result[38] != null)
			list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" "+result[38]);
			
			if(result[39] != null)
			list.setISSUES_DRIVER_NAME(list.getISSUES_DRIVER_NAME()+" - "+result[39]);
			
			if(result[40] != null) {
				list.setLocation((String) result[40]);	
			}else {
				list.setLocation(" ");	
			}
			
			if(result[41] != null) {
				list.setVehicleType((String) result[41]);
			}else {
				list.setVehicleType("");
			}
			if(result[42] != null) {
				list.setVehicleModel((String) result[42]);
			}else {
				list.setVehicleModel("");
			}
			list.setTripNumber((String) result[43]);
			list.setBreakDownLocation((String) result[44]);
			list.setIsVehicleReplaced((Boolean) result[45]);
			list.setReplacedWithVid((Integer) result[46]);
			list.setVehicleReplacedLocation((String) result[47]);
			list.setIsTripCancelled((Boolean) result[48]);
			list.setCancelledKM((Double) result[49]);
			list.setReplacedWithVehicle((String) result[50]);
			if( result[51] != null) {
				list.setTransactionNumber("<a target=_blank href=\"showWorkOrder?woId="+list.getISSUES_WORKORDER_ID() +"\">WO-"+(Long)result[51]+" </a>");			
			}else if( result[52] != null) {
				list.setTransactionNumber("<a target=_blank href=\"showDealerServiceEntries?dealerServiceEntriesId="+list.getDealerServiceEntriesId() +"\">DSE-"+(Long)result[51]+" </a>");
			}else {
				list.setTransactionNumber("--");
			}
			
			if(list.getIsVehicleReplaced() != null && list.getIsVehicleReplaced()) {
				list.setVehicleReplacedStr("Yes");
			}else {
				list.setVehicleReplacedStr("NO");
			}
			
			if(list.getIsTripCancelled() != null && list.getIsTripCancelled()) {
				list.setTripCancelledStr("Yes");
			}else {
				list.setTripCancelledStr("No");
			}
			
			list.setIssuesNumberStr("<a target=_blank href=\"showIssues.in?Id="+AESEncryptDecrypt.encryptBase64(""+list.getISSUES_ID() )+"\">I-"+list.getISSUES_NUMBER()+" </a>");
			
			Dtos.add(list);
		}
	}
	return Dtos;
}

public ValueObject backdateOdometerValidation(ValueObject object) {
	CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	try {
		object.put("companyId", userDetails.getCompany_id());
		String startTime = "00:00";
		if(object.getString("issueStartTime") != null && !object.getString("issueStartTime","").equals("")) {
			startTime	= object.getString("issueStartTime");
		}
		String reportedDate = formatSQL.format(DateTimeUtility.getDateTimeFromDateTimeString(object.getString("reportdDate"), startTime));
		String mainConditionQ =" I.companyId = "+userDetails.getCompany_id()+" AND I.vid = "+object.getInt("vid",0)+"  ";
		if(object.getBoolean("fromEdit",false) && object.getLong("id", 0) > 0) {
			mainConditionQ += " AND I.voh_updateId <> "+ object.getLong("id")+" " ;
		}
		VehicleDto preIssue = dseService.getPreviousOdoFromHistory(mainConditionQ+" AND I.voh_date < '"+reportedDate+"' ", "ORDER BY I.voh_date DESC");
		VehicleDto nextIssue = dseService.getPreviousOdoFromHistory(mainConditionQ+" AND I.voh_date > '"+reportedDate+"' AND I.vehicle_Odometer > 0 ", "ORDER BY I.voh_date ASC");
		getMaxAndMinOdometer(object,nextIssue,preIssue,reportedDate);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return object;

}
public ValueObject getMaxAndMinOdometer(ValueObject object,VehicleDto nextIssue, VehicleDto preIssue,String reportedDate) throws Exception {
	int maxOdo =0;
	String minStr ="minOdometer";
	String maxStr ="maxOdometer";
	String mainConditionQ =" I.companyId = "+object.getLong("companyId",0)+" AND I.vid = "+object.getInt("vid",0)+"  ";
	VehicleDto preHistory = null;
	if(nextIssue != null && preIssue != null) {
		if((preIssue.getModuleOdometer()).equals(nextIssue.getModuleOdometer())) {
			object.put(minStr, preIssue.getModuleOdometer());
			object.put(maxStr, nextIssue.getModuleOdometer());
		}else {
			maxOdo = dseService.caluculateDiffrence(nextIssue, preIssue);
			object.put(minStr, preIssue.getModuleOdometer()+1);
			object.put(maxStr, maxOdo);
		}
	}else if(nextIssue != null) {
		preHistory= new VehicleDto();
		preHistory.setModuleOdometer(1);
		maxOdo = dseService.caluculateDiffrence(nextIssue, preHistory);
		object.put(minStr,1);
		object.put(maxStr, maxOdo);
	}else if (preIssue != null && object.getBoolean("fromEdit",false)){
		object.put(minStr, preIssue.getModuleOdometer()+1);
		object.put(maxStr,(preIssue.getModuleOdometer())+preIssue.getVehicle_ExpectedOdameter());
	}else{
		if(preIssue == null && object.getBoolean("fromEdit",false)) {
			VehicleDto expectedOdo = dseService.getPreviousOdoFromHistory(mainConditionQ, "ORDER BY I.voh_date ASC");
			object.put(minStr, 1);
			object.put(maxStr, 1+expectedOdo.getVehicle_ExpectedOdameter());
		}else{
			object.put(minStr, 0);
			object.put(maxStr, 0);
		}
	}
	return object;
}
public List<UserProfileDto> getAssigneeListByRespectiveIssueId(List<IssuesDto> issuesDto,Integer companyId) throws Exception {
	StringBuilder strbuilder = new StringBuilder();
	String subscribers = "";
	String [] strArray = null;
	HashMap<String, UserProfileDto> userList = new HashMap<>();
	Collection<UserProfileDto> userListValues =null;
	ArrayList<UserProfileDto> finalUserList = null;
	try {
		for(IssuesDto dto:issuesDto) {
			if(dto.getISSUES_ASSIGN_TO_NAME()!= null && !dto.getISSUES_ASSIGN_TO_NAME().trim().equals("")) {
				strbuilder.append(","+dto.getISSUES_ASSIGN_TO_NAME());
			}
		}
		subscribers =strbuilder.toString();
		strArray = subscribers.split(",");
		for(String sub : strArray) {
			if(!sub.trim().equals("") && userList.get(sub) == null) {
				UserProfileDto user=userProfileService.get_UserProfile_Issues_Details(sub,companyId);
				if(user != null) {
					userList.put(sub, user);
				}
			}
		}
		userListValues = userList.values();
		finalUserList = new ArrayList<>(userListValues);
	}catch (Exception e) {
		e.printStackTrace();
	}
	return finalUserList;
}

public List<IssuesDto> getIssueDetailsByIssueIds(String issueIds, int companyId) throws Exception {
	List<IssuesDto> issuesList = null;
	Map<Long, IssuesDto> issueHash = null;
	try {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT I.ISSUES_ID, I.ISSUES_VID, I.ISSUES_NUMBER, I.ISSUES_SUMMARY,I.categoryId,I.ISSUES_LABELS_ID,I.ISSUE_TYPE_ID,Pc.pcName,V.vehicle_registration,VT.vtype,V.vehicle_Odometer,D.driver_firstname, D.driver_Lastname, D.driver_fathername,D.driver_mobnumber,I.ISSUES_ASSIGN_TO_NAME,B.branch_name "
						+ " From Issues AS I "
						+ " LEFT JOIN PartCategories Pc ON Pc.pcid = I.categoryId "
						+ " LEFT JOIN Vehicle V ON V.vid = I.ISSUES_VID"
						+ " LEFT JOIN Driver D ON D.driver_id = I.ISSUES_DRIVER_ID"
						+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId"
						+ " LEFT JOIN Branch B ON B.branch_id = V.branchId"
						+ " Where I.ISSUES_ID IN(" + issueIds + ")  AND I.COMPANY_ID = " + companyId
						+ " AND I.markForDelete = 0 ",
				Object[].class);
		List<Object[]> results = null;
		issuesList = new ArrayList<>();
		results = query.getResultList();
		if (results != null && !results.isEmpty()) {
			issueHash = new HashMap<>();
			IssuesDto select = null;
			for (Object[] result : results) {
				if (issueHash.get(result[0]) == null) {
					select = new IssuesDto();
					select.setISSUES_ID((Long) result[0]);
					select.setISSUES_ID_ENCRYPT(AESEncryptDecrypt.encryptBase64("" + select.getISSUES_ID()));
					select.setISSUES_VID((Integer) result[1]);
					select.setISSUES_NUMBER((Long) result[2]);
					select.setIssuesNumberStr("I-" +result[2]);
					select.setISSUES_SUMMARY((String) result[3]);
					select.setCategoryId((Integer) result[4]);
					select.setISSUES_LABELS_ID((short) result[5]);
					select.setISSUE_TYPE_ID((short) result[6]);
					select.setPartCategoryName((String) result[7]);
					select.setISSUES_TYPE(IssuesTypeConstant.getIssueTypeName(select.getISSUE_TYPE_ID()));
					select.setISSUES_LABELS(IssuesTypeConstant.getIssuesLabelName(select.getISSUES_LABELS_ID()));
					select.setVehicleNumber((String) result[8]);
					select.setVehicleType((String) result[9]);
					select.setVehicleCurrentOdometer((Integer) result[10]);
					select.setISSUES_DRIVER_NAME(result[11] + " " +  result[12] + "_" + result[13]);
					if (select.getISSUES_DRIVER_NAME().contains("null")) {
						select.setISSUES_DRIVER_NAME(select.getISSUES_DRIVER_NAME().replace("null", ""));
					}
					select.setDriver_mobnumber((String)result[14]);
					select.setISSUES_ASSIGN_TO_NAME((String) result[15]);
					select.setISSUES_BRANCH_NAME((String) result[16]);
					issuesList.add(select);
				}
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return issuesList;
}

@Override
public List<Issues> getDriverWiseIssueList(ValueObject object) {

	TypedQuery<Issues> query =null;
	List<Issues> issuesList=null;
	try {
	query = entityManager.createQuery(" FROM Issues AS i where i.ISSUES_DRIVER_ID =:id"
			+ " AND i.COMPANY_ID = " + object.getInt("companyId",0)
			+ " AND i.markForDelete = 0 "+object.getString("query", " ")+" ", Issues.class);

	query.setParameter("id", object.getInt("driverId",0));
		issuesList=query.getResultList();
	} catch (Exception e) {
		// TODO: handle exception
	}
	return issuesList;
}
@Override
public Map<String, Object> getPrintDataForIssue(Long issueId) {
	Map<String, Object> map = new HashMap<>();
	try {
		CustomUserDetails userDetails = Utility.getObject(null);
		IssuesDto issue = get_IssuesDetails(issueId, userDetails.getCompany_id());
		issue = issuesBL.Showing_Issues_Details(issue);
		map.put("issue", issue);
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
public Map<String, Object> getMultiPrintDataForIssue(String issueIds) {
	Map<String, Object> map = new HashMap<>();
	try {
		CustomUserDetails userDetails = Utility.getObject(null);
		List<IssuesDto> issueList = getIssueDetailsByIssueIds(issueIds, userDetails.getCompany_id());
		if (issueList != null) {
			map.put("vehicleNumber", issueList.get(0).getVehicleNumber());
			map.put("vehicleType", issueList.get(0).getVehicleType());
			map.put("currentOdometer", issueList.get(0).getVehicleCurrentOdometer());
			map.put("branch", issueList.get(0).getISSUES_BRANCH_NAME());
		}
		map.put("issueList", issueList);
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
	public ValueObject getIssueDetails(ValueObject object) {
		// TODO Auto-generated method stub
		CustomUserDetails userDetails = null;
		
		TypedQuery<Object[]> query = null;
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		try {
			query =  entityManager.createQuery("SELECT  I.ISSUES_ID, I.ISSUES_NUMBER, PC.pcid,PC.pcName  FROM Issues I"
							+ " LEFT JOIN PartCategories  PC ON PC.pcid = I.categoryId "
							+ " WHERE I.ISSUES_ID = "+ object.getInt("issueId") +  " AND I.COMPANY_ID=" + userDetails.getCompany_id()  +" AND I.markForDelete=0" ,Object[].class);
		
		IssuesDto list = null;
		List<Object[]> results = query.getResultList();
		
		if (results != null && !results.isEmpty()) {
			for (Object[] result : results) {
				list = new IssuesDto();
				list.setISSUES_ID((Long)result[0]);
				list.setISSUES_NUMBER((Long) result[1]);
				list.setCategoryId((Integer) result[2]);
				list.setPcName((String) result[3]);
			}
		}
		object.put("IssueCategory", list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return object;
	}

	@Transactional
	public Page<Issues> get_OpenIssues_All_pagination(Integer pageNumber, Integer companyId, Long id, short issueStatus) throws Exception {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return issuesRepository.get_OpenIssues_All_pagination(companyId,issueStatus, request);
		}
		return issuesRepository.get_OpenIssues_All_pagination(companyId, id,issueStatus, request);
	}

	@Transactional
	public Page<Issues> get_OverdueIssues_All_pagination(Integer pageNumber, Integer companyId, Long id, short issueStaus, Timestamp date) throws Exception {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return issuesRepository.get_OverdueIssues_All_pagination(companyId, issueStaus, date, request);
		}
		return issuesRepository.get_OverdueIssues_All_pagination(companyId, id, issueStaus, date, request);
	}

	@Transactional
	public Page<Issues> get_Resolved_All_pagination(Integer pageNumber, Integer companyId, Long id,short issueStatus) throws Exception {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return issuesRepository.get_Resolved_All_pagination(companyId,issueStatus, request);
		}
		return issuesRepository.get_Resolved_All_pagination(companyId, id,issueStatus, request);
	}

	@Transactional
	public Page<Issues> get_SECreatedIssues_All_pagination(Integer pageNumber, Integer companyId, Long id, short issueStatus) throws Exception {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return issuesRepository.get_SECreatedIssues_All_pagination(companyId,issueStatus, request);
		}
		return issuesRepository.get_SECreatedIssues_All_pagination(companyId, id,issueStatus, request);
	}
		
	@Transactional
	public Page<Issues> get_DSE_Created_Issues_All_pagination(Integer pageNumber, Integer companyId, Long id, short issueStatus) throws Exception {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return issuesRepository.get_DSE_Created_Issues_All_pagination(companyId,issueStatus, request);
		}
		return issuesRepository.get_DSE_Created_Issues_All_pagination(companyId, id,issueStatus, request);
	}
			
	@Transactional
	public Page<Issues> get_VehicleBreakDownIssues_All_pagination(Integer pageNumber, Integer companyId, Long id, short issueStatus) throws Exception {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return issuesRepository.get_VehicleBreakDownIssues_All_pagination(companyId,issueStatus, request);
		}
		return issuesRepository.get_VehicleBreakDownIssues_All_pagination(companyId, id,issueStatus, request);
	}

	@Transactional
	public Page<Issues> get_CloseIssues_All_pagination(Integer pageNumber, Integer companyId, Long id, short issueStatus) throws Exception {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return issuesRepository.get_CloseIssues_All_pagination(companyId,issueStatus, request);
		}
		return issuesRepository.get_CloseIssues_All_pagination(companyId, id,issueStatus, request);
	}

	@Transactional
	public Page<Issues> get_RejectIssues_All_pagination(Integer pageNumber, Integer companyId, Long id,short issueStatus) throws Exception {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return issuesRepository.get_RejectIssues_All_pagination(companyId,issueStatus, request);
		}
		return issuesRepository.get_RejectIssues_All_pagination(companyId, id,issueStatus, request);
	}

	@Transactional
	public Page<Issues> get_WOCreatedIssues_All_pagination(Integer pageNumber, Integer companyId, Long id, short issueStatus) throws Exception {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return issuesRepository.get_WOCreatedIssues_All_pagination(companyId,issueStatus, request);
		}
		return issuesRepository.get_WOCreatedIssues_All_pagination(companyId, id,issueStatus, request);
	}

	
}	
