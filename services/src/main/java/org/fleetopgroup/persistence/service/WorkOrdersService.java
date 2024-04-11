package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.AccidentStatus;
import org.fleetopgroup.constant.GPSConstant;
import org.fleetopgroup.constant.IssuesConfigurationContant;
import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.PartType;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.constant.VehicleConfigurationContant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleGPSConfiguratinConstant;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.constant.WorkOrdersConfigurationConstants;
import org.fleetopgroup.constant.WorkOrdersType;
import org.fleetopgroup.persistence.bl.InventoryBL;
import org.fleetopgroup.persistence.bl.IssuesBL;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.bl.PartWarrantyBL;
import org.fleetopgroup.persistence.bl.PartWarrantyDetailsBL;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.bl.WorkOrdersBL;
import org.fleetopgroup.persistence.dao.DriverRepository;
import org.fleetopgroup.persistence.dao.PartInvoiceRepository;
import org.fleetopgroup.persistence.dao.PartWarrantyDetailsHistoryRepository;
import org.fleetopgroup.persistence.dao.PartWarrantyDetailsRepository;
import org.fleetopgroup.persistence.dao.ServiceReminderHistoryRepository;
import org.fleetopgroup.persistence.dao.ServiceReminderWorkOrderHistoryRepository;
import org.fleetopgroup.persistence.dao.WorkOrderRemarkRepository;
import org.fleetopgroup.persistence.dao.WorkOrdersDocumentRepository;
import org.fleetopgroup.persistence.dao.WorkOrdersRepository;
import org.fleetopgroup.persistence.dao.WorkOrdersTasksRepository;
import org.fleetopgroup.persistence.dao.WorkOrdersTasksToLaborRepository;
import org.fleetopgroup.persistence.dao.WorkOrdersTasksToPartsRepository;
import org.fleetopgroup.persistence.dao.WorkOrdersTasksToReceivedRepository;
import org.fleetopgroup.persistence.dto.BatteryHistoryDto;
import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.JobSubTypeDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.PartLocationPermissionDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesAndWorkOrdersDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.UreaEntriesDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleClothInventoryHistoryDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.dto.WorkOrderRemarkDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToLaborDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.persistence.model.AccidentQuotationDetails;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.EmailAlertQueue;
import org.fleetopgroup.persistence.model.InventoryLocation;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.IssuesTasks;
import org.fleetopgroup.persistence.model.JobSubType;
import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.model.PartWarrantyDetails;
import org.fleetopgroup.persistence.model.PartWarrantyDetailsHistory;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.ServiceReminderHistory;
import org.fleetopgroup.persistence.model.ServiceReminderWorkOrderHistory;
import org.fleetopgroup.persistence.model.SmsAlertQueue;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleAccidentDetails;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.model.VehicleExpenseDetails;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.model.WorkOrderRemark;
import org.fleetopgroup.persistence.model.WorkOrderSequenceCounter;
import org.fleetopgroup.persistence.model.WorkOrderTxnChecker;
import org.fleetopgroup.persistence.model.WorkOrders;
import org.fleetopgroup.persistence.model.WorkOrdersDocument;
import org.fleetopgroup.persistence.model.WorkOrdersTasks;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToLabor;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToParts;
import org.fleetopgroup.persistence.model.WorkOrdersTasksToReceived;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IEmailAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IJobSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationsService;
import org.fleetopgroup.persistence.serviceImpl.IPartWarrantyDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISmsAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.ITyreUsageHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAccidentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGPSDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOTaskToPartDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrderSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrderTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.ByteConvertor;
import org.fleetopgroup.web.util.DateTimeUtility;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.antkorwin.xsync.XMutexFactoryImpl;
import com.antkorwin.xsync.XSync;


@Service("WorkOrdersService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class WorkOrdersService implements IWorkOrdersService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private WorkOrdersRepository WorkOrdersDao;

	@Autowired
	private WorkOrdersTasksRepository WorkOrdersTasksRepository;

	@Autowired
	private WorkOrdersTasksToPartsRepository WorkOrdersTasksToPartsRepository;

	@Autowired
	private WorkOrdersTasksToLaborRepository WorkOrdersTasksToLaborRepository;

	@Autowired
	private WorkOrdersTasksToReceivedRepository WorkOrdersTasksToReceived;

	@Autowired
	private ICompanyConfigurationService companyConfigurationService;
	
	
	@Autowired
	private IWorkOTaskToPartDocumentService workOTaskToPartDocumentService;

	@Autowired private MongoTemplate											mongoTemplate;
	@Autowired private SequenceCounterService									sequenceCounterService;
	@Autowired private WorkOrdersDocumentRepository								documentRepository;
	@Autowired private org.fleetopgroup.persistence.report.dao.WorkOrdersDao	workOrdersDao;
	@Autowired private IUserProfileService										userProfileService;
	@Autowired private IJobTypeService 											jobTypeService;
	@Autowired private IWorkOrderSequenceService 								workOrderSequenceService;
	@Autowired private IVehicleService 											vehicleService;
	@Autowired private IPartLocationsService 									partLocationsService;
	@Autowired private IJobSubTypeService 										jobSubTypeService;
	@Autowired private IServiceReminderService 									ServiceReminderService;
	@Autowired private IVehicleOdometerHistoryService 							vehicleOdometerHistoryService;
	@Autowired private IPartLocationPermissionService 							partLocationPermissionService;
	@Autowired private IInventoryService 										inventoryService;
	@Autowired private IIssuesService 											issuesService;
	@Autowired private IVehicleProfitAndLossService								vehicleProfitAndLossService;
	@Autowired private IVehicleExpenseDetailsService							vehicleExpenseDetailsService;
	@Autowired private IVehicleAgentIncomeExpenseDetailsService					vehicleAgentIncomeExpenseDetailsService;
	@Autowired private VehicleProfitAndLossTxnCheckerService					vehicleProfitAndLossTxnCheckerService;
	@Autowired private IVehicleAgentTxnCheckerService							vehicleAgentTxnCheckerService;
	@Autowired private IWorkOrderTxnCheckerService								workOrderTxnCheckerService;
	@Autowired private IEmailAlertQueueService 									emailAlertQueueService;
	@Autowired private ISmsAlertQueueService 									smsAlertQueueService;
	@Autowired private ServiceReminderWorkOrderHistoryRepository				orderHistoryRepository;
	@Autowired private PartInvoiceRepository									partInvoiceRepository;
	@Autowired private HttpServletRequest										request;
	@Autowired private IVehicleAccidentDetailsService							accidentDetailsService;
	@Autowired private	IVehicleGPSDetailsService					            vehicleGPSDetailsService;
	@Autowired private IDriverService 								            driverService;
	@Autowired private XSync<String> 											xSync;
	@Autowired private WorkOrderRemarkRepository								orderRemarkRepository;
	@Autowired private IMasterPartsService										masterPartsService;
	@Autowired private IPartWarrantyDetailsService								warrantyDetailsService;		
	@Autowired private	PartWarrantyDetailsHistoryRepository					historyRepository;
	@Autowired private 	PartWarrantyDetailsRepository			partWarrantyDetailsDao;
	@Autowired private ITyreUsageHistoryService								tyreUsageHistoryService;
	@Autowired private  ServiceReminderHistoryRepository                    serviceReminderHistoryRepository;
	@Autowired DriverRepository                                             driverRepository;

	
	
	
	WorkOrdersBL 								WOBL 							= new WorkOrdersBL();
	VehicleOdometerHistoryBL 					vehicleOdometerHistoryBL 		= new VehicleOdometerHistoryBL();
	PartLocationsBL 							partLocationsBL 				= new PartLocationsBL();
	InventoryBL 								inventoryBL 					= new InventoryBL();
	VehicleProfitAndLossTxnCheckerBL			txnCheckerBL 					= new VehicleProfitAndLossTxnCheckerBL();
	VehicleAgentTxnCheckerBL					agentTxnCheckerBL				= new VehicleAgentTxnCheckerBL();
	IssuesBL									issuesBL						= new IssuesBL();
	VehicleBL 									vehicleBL 						= new VehicleBL();
	
	XMutexFactoryImpl<String> xMutexFactory = new XMutexFactoryImpl<String>();

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	SimpleDateFormat 		sqldateTime 	= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat 		tallyFormat		= new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat 		format2 		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat 		sqlFormat 		= new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat			toFixedTwo		= new DecimalFormat("##.##");
	SimpleDateFormat 		sqldateWithTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	SimpleDateFormat 		dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");
	
	private static final int 			PAGE_SIZE 							= 10;
	public  static final Double 		WORK_ORDER_DEFALAT_AMOUNT 			= 0.0;
	public static final  long 			WORK_ORDER_DEFALAT_VALUE			= 0;
	public static final  Integer 		WORK_ORDER_DEFALAT_ODAMETER 		= 0;
	public static final  long 			TRIP_SHEET_DEFALAT_VALUE 			= 0;
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@Transactional
	public WorkOrders addWorkOrders(WorkOrders workOrders) throws Exception {

		workOrders = 	WorkOrdersDao.save(workOrders);
		return workOrders;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addWorkOrdersTask(WorkOrdersTasks WorkOrdersTask) throws Exception {

		WorkOrdersTasksRepository.save(WorkOrdersTask);
	}
	
	@Transactional
	public void saveMultipleTask(List<WorkOrdersTasks> workOrdersTasks) {
		WorkOrdersTasksRepository.saveAll(workOrdersTasks);
	}

	@Transactional(readOnly = true)
	public WorkOrders getWorkOrders(long WorkOrders) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return WorkOrdersDao.getWorkOrders(WorkOrders, userDetails.getCompany_id());
	}

	@Override
	public WorkOrdersDto getWorkOrdersDetails(long WorkOrders, Integer companyId) {

		Query query = entityManager.createQuery(
				"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
						+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
						+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id,"
						+ " WO.workorders_location_ID, WO.indentno, WO.totalsubworktask_cost, WO.totalworktax_cost, WO.initial_note, WO.ISSUES_ID,"
						+ " WO.created, WO.lastupdated, U2.email, U3.email, WO.assigneeId, WO.vehicle_Odometer_old, WO.workorders_driver_id,"
						+ " WO.workorders_driver_number, WO.out_work_station, WO.workorders_route, WO.vehicle_Odometer, WO.workorders_diesel, WO.gpsWorkLocation,"
						+ " WO.gpsOdometer, WO.tallyCompanyId, TC.companyName, WO.vehicleReOpenStatusId, WO.subLocationId, "
						+ " PSL.partlocation_name, WO.approvalStatusId, WO.accidentId,I.ISSUES_NUMBER, I.ISSUES_SUMMARY ,D.driver_Lastname , D.driver_fathername, WO.workLocationId"
						+ ", PWL.partlocation_name,WO.issueIds "
						+ " from WorkOrders WO " 
						+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
						+ " LEFT JOIN User U ON U.id = WO.assigneeId"
						+ " LEFT JOIN User U2 ON U2.id = WO.createdById"
						+ " LEFT JOIN User U3 ON U3.id = WO.lastModifiedById"
						+ " INNER JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
						+ " LEFT JOIN PartLocations AS PSL ON PSL.partlocation_id = WO.subLocationId"
						+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id" 
						+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = WO.tallyCompanyId" 
						+ " LEFT JOIN Issues I ON I.ISSUES_ID = WO.ISSUES_ID"
						+ " LEFT JOIN PartLocations AS PWL ON PWL.partlocation_id = WO.workLocationId"
						+ " where WO.workorders_id="
						+ WorkOrders + " AND WO.companyId = " + companyId + " AND WO.markForDelete = 0");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		WorkOrdersDto list;
		if (result != null) {
			list = new WorkOrdersDto();

			list.setWorkorders_id((Long) result[0]);
			list.setWorkorders_Number((Long) result[1]);
			list.setVehicle_vid((Integer) result[2]);
			list.setVehicle_registration((String) result[3]);
			list.setVehicle_Odometer((Integer) result[4]);
			list.setStart_dateOn((Date) result[5]);
			list.setDue_dateOn((Date) result[6]);
			list.setWorkorders_drivername((String) result[7]);
			list.setAssignee((String) result[8]);
			list.setWorkorders_location((String) result[9]);
			list.setPriorityId((short) result[10]);
			list.setPriority(WorkOrdersType.getPriorityName((short) result[10]));
			list.setCompleted_dateOn((Date) result[11]);
			list.setTotalworkorder_cost(Double.parseDouble(toFixedTwo.format(result[12])) );
			list.setWorkorders_statusId((short) result[13]);
			list.setWorkorders_status(WorkOrdersType.getStatusName((short) result[13]));
			list.setWorkorders_document((boolean) result[14]);
			list.setWorkorders_document_id((Long) result[15]);
			list.setWorkorders_location_ID((Integer) result[16]);
			list.setIndentno((String) result[17]);
			list.setTotalsubworktask_cost(Double.parseDouble(toFixedTwo.format(result[18])));
			
			list.setTotalworktax_cost(Double.parseDouble(toFixedTwo.format(result[19])));
			list.setInitial_note((String) result[20]);
			list.setISSUES_ID((long) result[21]);
			list.setCreatedOn((Date) result[22]);
			list.setLastupdatedOn((Date) result[23]);
			list.setCreatedBy((String) result[24]);
			list.setLastModifiedBy((String) result[25]);
			list.setAssigneeId((Long) result[26]);
			list.setVehicle_Odometer_old((Integer) result[27]);
			list.setWorkorders_driver_id((Integer) result[28]);
			list.setWorkorders_driver_number((String) result[29]);
			list.setOut_work_station((String) result[30]);
			list.setWorkorders_route((String) result[31]);
			list.setVehicle_Odometer((Integer) result[32]);
			list.setWorkorders_diesel((Long) result[33]);
			if(result[34] != null)
				list.setGpsWorkLocation((String) result[34]);
			if(result[35] != null)
				list.setGpsOdometer((Double) result[35]);
			if(result[36] != null) {
				list.setTallyCompanyId((Long) result[36]);
				list.setTallyCompanyName((String) result[37]);
			}
			if(result[38] != null)
				list.setVehicleReOpenStatusId((short) result[38]);
			if(result[39] != null) {
				list.setSubLocationId((Integer) result[39]);
				list.setSubLocation((String) result[40]);
			}
			if(result[41] != null) 
				list.setApprovalStatusId((short) result[41]);
			if(result[42] != null) 
				list.setAccidentId((Long) result[42]);
			if(result[43] != null) {
				list.setIssueNumber((Long) result[43]);
			}
			if(result[44] != null) {
				list.setIssueSummary((String) result[44]);
			}
			if(result[45] != null)
				list.setWorkorders_drivername(list.getWorkorders_drivername()+" "+result[45]);
			if(result[46] != null)
				list.setWorkorders_drivername(list.getWorkorders_drivername()+" - "+result[46]);
			if(result[47] != null) {
				list.setWorkLocationId((Integer) result[47]);
				list.setWorkLocation((String) result[48]);
				
				list.setIssueIds((String) result[49]);
			}
			
		} else {
			return null;
		}

		return list;
	}

	@Transactional
	public List<WorkOrdersTasksDto> getWorkOrdersTasks(long WorkOrders_id, Integer companyId) {
		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT WO.workordertaskid, JT.Job_Type, WO.job_typetaskId, WO.job_subtypetask_id, JST.Job_ROT, JST.Job_ROT_number,"
						+ " WO.mark_complete, WO.totalpart_cost, WO.totallaber_cost, WO.totaltask_cost, WO.vehicle_vid, WO.last_occurred_date,"
						+ " WO.last_occurred_woId, WO.last_occurred_odameter, WO.jobTypeRemark, WO.service_id, SR.service_Number, SP.programName,WO.issueIds,I.ISSUES_NUMBER,I.ISSUES_SUMMARY, "
						+ " PC.pcName  From WorkOrdersTasks WO "
						+ " INNER JOIN JobType JT ON JT.Job_id = WO.job_typetaskId"
						+ " INNER JOIN JobSubType JST ON JST.Job_Subid = WO.job_subtypetask_id"
						+ "	LEFT JOIN ServiceReminder SR on SR.service_id = WO.service_id AND SR.markForDelete = 0"
						+ "	LEFT JOIN Issues I on I.ISSUES_ID = WO.issueIds AND I.markForDelete = 0"
						+ " LEFT JOIN PartCategories  PC ON PC.pcid = WO.categoryId AND PC.markForDelete = 0 "
						+ " LEFT JOIN VehicleServiceProgram SP ON SP.vehicleServiceProgramId = SR.serviceProgramId AND SP.markForDelete = 0"
						+ " where WO.workorders.workorders_id = " + WorkOrders_id + " AND WO.companyId = " + companyId
						+ " AND WO.markForDelete = 0 ",
						Object[].class);
		List<Object[]> results = query.getResultList();

		List<WorkOrdersTasksDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersTasksDto>();
			WorkOrdersTasksDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersTasksDto();

				list.setWorkordertaskid((Long) result[0]);
				list.setJob_typetask((String) result[1]);
				list.setJob_typetaskId((Integer) result[2]);
				list.setJob_subtypetask_id((Integer) result[3]);
				if(result[5] != null) {
					list.setJob_subtypetask((String) result[4] + " - " + (String) result[5]);
				}else {
					list.setJob_subtypetask((String) result[4]);
				}
				list.setMark_complete((Integer) result[6]);
				list.setTotalpart_cost((Double) result[7]);
				list.setTotallaber_cost((Double) result[8]);
				list.setTotaltask_cost((Double) result[9]);
				list.setVehicle_vid((Integer) result[10]);
				list.setLast_occurred_dateOn((Date) result[11]);
				list.setLast_occurred_woId((Long) result[12]);
				list.setLast_occurred_odameter((Integer) result[13]);
				list.setJobTypeRemark((String) result[14]);
				list.setWorkorders_id(WorkOrders_id);
				if(result[15] != null) {
				list.setService_id((long) result[15]);
				}
				if(result[16] != null) {
					list.setService_Number("S-"+(long) result[16]);
					list.setService_NumberStr("S-"+(long) result[16]);
				}
				if(result[17] != null) {
					list.setVehicle_registration((String) result[17]);
				}
				
				if(list.getVehicle_registration() != null) {
					list.setService_Number(list.getVehicle_registration() +" : "+list.getService_Number());
				}
				list.setIssueIds((Integer) result[18]);
				list.setIssueNumber(""+result[19]);
				list.setIssueSummary((String) result[20]);
				list.setPcName((String) result[21]);
				Dtos.add(list);
			}
		}
		return Dtos;

	}

	@Transactional
	public List<WorkOrdersTasksToPartsDto> getWorkOrdersTasksToParts(long WorkOrders_id, Integer companyId) {
		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT WO.workordertaskto_partid, WO.partid, MP.partnumber, MP.partname, MP.partTypeId, PL.partlocation_name, WO.quantity,"
						+ " WO.parteachcost, WO.totalcost, WO.oldpart, WO.inventory_id, WO.workordertaskid, WO.vehicle_vid, WO.last_occurred_date, "
						+ " WO.last_occurred_woId, WO.createdById, U.firstName, U.lastName, WO.woPart_document,"
						+ " MP.isWarrantyApplicable, MP.isRepairable, WO.locationId, WO.assignedNoPart, MP.assetIdRequired "
						+ " From WorkOrdersTasksToParts WO "
						+ " INNER JOIN MasterParts MP ON MP.partid = WO.partid "
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = WO.locationId "
						+ " LEFT JOIN User AS U ON U.id = WO.createdById "
						+ " where WO.workorders_id = " + WorkOrders_id + " AND WO.companyId = " + companyId
						+ " AND WO.markForDelete = 0 ",
						Object[].class);
		List<Object[]> results = query.getResultList();

		List<WorkOrdersTasksToPartsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersTasksToPartsDto>();
			WorkOrdersTasksToPartsDto list = null;
			for (Object[] result : results) {

				list = new WorkOrdersTasksToPartsDto();

				list.setWorkordertaskto_partid((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPartname((String) result[2]);
				list.setPartnumber((String) result[3]);
				list.setParttype(PartType.getPartTypeName((short) result[4]));
				list.setLocation((String) result[5]);
				if(result[6] != null)
				list.setQuantity(Double.parseDouble((toFixedTwo.format(result[6]))));
				if(result[7] != null)
				list.setParteachcost(Double.parseDouble(toFixedTwo.format(result[7])));
				if(result[8] != null)
				list.setTotalcost(Double.parseDouble(toFixedTwo.format(result[8])));
				list.setOldpart((String) result[9]);
				list.setInventory_id((Long) result[10]);
				list.setWorkordertaskid((Long) result[11]);
				list.setVehicle_vid((Integer) result[12]);
				list.setLast_occurred_date((Date) result[13]);
				list.setLast_occurred_woId((Long) result[14]);
				if((Long) result[15]!=null) {
				list.setCreatedById((Long) result[15]);
				}
				if((String) result[16]!=null) {
				list.setFirstName((String) result[16]);
				}
				if((String) result[17]!=null) {
				list.setLastName((String) result[17]);
				}
				list.setWoPart_document((boolean) result[18]);
				list.setWarrantyApplicable((boolean) result[19]);
				list.setRepairable((boolean) result[20]);
				list.setLocationId((Integer) result[21]);
				list.setAssignedNoPart((short) result[22]);
				list.setAssetIdRequired((boolean) result[23]);
				
				if(list.isAssetIdRequired()) {
					list.setWarrantyApplicable(list.isAssetIdRequired());
				}
				
				list.setWorkorders_id(WorkOrders_id);	
				
				Dtos.add(list);
			}
		}
		return Dtos;

	}

	@Transactional
	public List<WorkOrdersTasksToParts> getWorkOrdersTasksToParts_ID(long WorkOrdersTASK_id, Integer companyId) {

		return WorkOrdersTasksToPartsRepository.getWorkOrdersTasksToParts_ID(WorkOrdersTASK_id, companyId);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateWorkOrders(WorkOrders WorkOrders) {

		WorkOrdersDao.save(WorkOrders);
	}

	@Transactional
	public List<WorkOrders> listWorkOrders() {

		return WorkOrdersDao.findAll();
	}

	@Transactional
	public List<WorkOrders> listVehicleWorkOrders(String WorkOrders_vehiclename) {

		return WorkOrdersDao.listVehicleWorkOrders(WorkOrders_vehiclename);
	}

	@Transactional
	public void deleteWorkOrders(Long workorders_id, long usersId, Timestamp toDate, Integer companyId) {

		WorkOrdersDao.deleteWorkOrders(workorders_id,usersId,toDate, companyId);
	}

	@Transactional
	public List<WorkOrders> listInprocessWorkOrders(String WorkOrders_Inproces) {

		return WorkOrdersDao.listInprocessWorkOrders(WorkOrders_Inproces);
	}

	@Transactional
	public List<WorkOrders> listResolvedWorkOrders(String WorkOrders_Resolved) {

		return WorkOrdersDao.listResolvedWorkOrders(WorkOrders_Resolved);
	}

	@Transactional
	public List<WorkOrders> listClosedWorkOrders(String WorkOrders_Closed) {

		return WorkOrdersDao.listClosedWorkOrders(WorkOrders_Closed);
	}

	@Transactional
	public WorkOrdersTasksToParts addWorkOrdersTaskToParts(WorkOrdersTasksToParts workOrdersTask) throws Exception {

	return	WorkOrdersTasksToPartsRepository.save(workOrdersTask);
	}

	@Transactional
	public List<WorkOrdersTasks> getWorkOrdersTasksIDToTotalCost(long WorkOrders_id, Integer companyId) {

		return WorkOrdersTasksRepository.getWorkOrdersTasksIDToTotalCost(WorkOrders_id, companyId);
	}

	@Transactional
	public void updateWorkOrdersTask_TotalPartCost(Long TaskID, Integer companyId) throws Exception {

		entityManager.createQuery(
				" UPDATE WorkOrdersTasks AS WO SET WO.totalpart_cost=(SELECT COALESCE(ROUND(SUM(WTP.totalcost),2),0) FROM WorkOrdersTasksToParts AS WTP WHERE WTP.workordertaskid="
						+ TaskID + " AND WTP.companyId=" + companyId + " AND WTP.markForDelete=0 ),  "
						+ " WO.totaltask_cost = COALESCE(ROUND(WO.totalpart_cost + WO.totallaber_cost,2),0) " + " where WO.workordertaskid="
						+ TaskID + " AND WO.companyId = " + companyId + " AND WO.markForDelete = 0 ")
		.executeUpdate();
	}

	@Transactional
	public void updateWorkOrderMainTotalCost(Long WorkOrder_ID) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		entityManager.createQuery(
				" UPDATE WorkOrders AS WO SET WO.totalsubworktask_cost=(SELECT COALESCE(ROUND(SUM(WOT.totaltask_cost),2),0) FROM WorkOrdersTasks AS WOT WHERE WOT.workorders.workorders_id="
						+ WorkOrder_ID + " AND WOT.companyId=" + userDetails.getCompany_id()
						+ " AND WOT.markForDelete=0) ,"
						+ " WO.totalworkorder_cost = COALESCE(ROUND(WO.totalsubworktask_cost + WO.totalworktax_cost,2),0)  where WO.workorders_id="
						+ WorkOrder_ID + " AND WO.companyId=" + userDetails.getCompany_id()
						+ " AND WO.markForDelete=0  ")
		.executeUpdate();

	}

	@Transactional
	public void updateWorkOrderProcess(short Process, Long lastModifiedBy, Timestamp lastupdated, Long WorkOrder_ID,
			Integer companyId) throws Exception {
		WorkOrdersDao.updateWorkOrderProcess(Process, lastModifiedBy, lastupdated, WorkOrder_ID, companyId);
	}

	@Override
	@Transactional
	public void updateWorkOrderProcess(short Process, Long lastModifiedBy, Timestamp lastupdated, Long WorkOrder_ID,
			Integer companyId, short vStatus) throws Exception {
		WorkOrdersDao.updateWorkOrderProcess(Process, lastModifiedBy, lastupdated, WorkOrder_ID, companyId, vStatus);
	}
	
	@Transactional
	public void addWorkOrdersTaskToLabor(WorkOrdersTasksToLabor workOrdersTaskLabor) throws Exception {

		WorkOrdersTasksToLaborRepository.save(workOrdersTaskLabor);
	}

	@Transactional
	public void updateWorkOrdersTask_TotalLaborCost(Long TaskID,
			Integer companyId) throws Exception {

		//WorkOrdersTasksRepository.updateWorkOrdersTask_TotalLaborCost(TaskLaborCost, TotalCost, TaskID, companyId);

		entityManager.createQuery(
				" UPDATE WorkOrdersTasks AS WO SET WO.totallaber_cost=(SELECT COALESCE(ROUND(SUM(WTL.totalcost),2),0) FROM WorkOrdersTasksToLabor AS WTL WHERE WTL.workordertaskid="
						+ TaskID + " AND WTL.companyId=" + companyId + " AND WTL.markForDelete=0 ),  "
						+ " WO.totaltask_cost = COALESCE(ROUND(WO.totalpart_cost + WO.totallaber_cost,2),0) " + " where WO.workordertaskid="
						+ TaskID + " AND WO.companyId = " + companyId + " AND WO.markForDelete = 0 ")
		.executeUpdate();
	}

	@Transactional
	public List<WorkOrdersTasksToLaborDto> getWorkOrdersTasksToLabor(long WorkOrders_id, Integer companyId) {


		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT WO.workordertaskto_laberid, WO.laberid, D.driver_firstname, D.driver_Lastname, WO.laberhourscost, WO.eachlabercost, WO.laberdiscount,"
						+ " WO.labertax, WO.totalcost, WO.workordertaskid , WO.labername " + " From WorkOrdersTasksToLabor WO"
						+ " LEFT JOIN Driver D ON D.driver_id = WO.laberid" + " where WO.workorders_id = "
						+ WorkOrders_id + " AND WO.companyId = " + companyId + " AND WO.markForDelete = 0 ",
						Object[].class);
		List<Object[]> results = query.getResultList();
		List<WorkOrdersTasksToLaborDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersTasksToLaborDto>();
			WorkOrdersTasksToLaborDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersTasksToLaborDto();
				list.setWorkordertaskto_laberid((Long) result[0]);
				list.setLaberid((Integer) result[1]);
				if(result[2] != null && result[3] != null) {
					list.setLabername((String) result[2] + " - " + (String) result[3]);
				}
				
				list.setLaberhourscost(Double.parseDouble(toFixedTwo.format(result[4])));
				
				list.setEachlabercost(Double.parseDouble(toFixedTwo.format(result[5])));
				list.setLaberdiscount(Double.parseDouble(toFixedTwo.format(result[6])) );
				list.setLabertax(Double.parseDouble(toFixedTwo.format(result[7])));
				list.setTotalcost(Double.parseDouble(toFixedTwo.format(result[8])));
				list.setWorkordertaskid((Long) result[9]);
				
				if(list.getLabername() == null || list.getLabername().trim().equals("")) {
					list.setLabername((String)result[10] );
				}
				
				list.setWorkorders_id(WorkOrders_id);

				Dtos.add(list);
			}
		}
		return Dtos;

	}

	@Transactional
	public List<WorkOrdersTasksToLabor> getWorkOrdersTasksToLabor_ID(long WorkOrdersTask_id, Integer companyId) {

		return WorkOrdersTasksToLaborRepository.getWorkOrdersTasksToLabor_ID(WorkOrdersTask_id, companyId);
	}

	@Transactional
	public WorkOrdersTasks getWorkOrdersCompletion(Long workOrdersTasksID, Integer companyid) throws Exception {

		return WorkOrdersTasksRepository.getWorkOrdersCompletion(workOrdersTasksID, companyid);
	}

	@Transactional
	public void updateWorkOrdersTask_Completion(Integer completionvalue, Long workOrdersTasksID, Integer companyId)
			throws Exception {

		WorkOrdersTasksRepository.updateWorkOrdersTask_Completion(completionvalue, workOrdersTasksID, companyId);
	}

	@Transactional
	public void updateWorkOrderCOMPLETE_date(short Process, Timestamp COMPLETE_date, String lastModifiedBy,
			Timestamp lastupdated, Long WorkOrder_ID, Integer companyId) throws Exception {

		WorkOrdersDao.updateWorkOrderCOMPLETE_date(Process, COMPLETE_date, lastModifiedBy, lastupdated, WorkOrder_ID,
				companyId);
	}

	@Transactional
	public Long countWorkOrder() throws Exception {

		return WorkOrdersDao.countWorkOrder();
	}

	@Transactional
	public Long countWorkOrderStatues(String Statues) throws Exception {

		return WorkOrdersDao.countWorkOrderStatues(Statues);
	}

	@Transactional
	public WorkOrdersTasks getWorkOrdersTask(long WorkOrderstask_ID, Integer companyId) {

		return WorkOrdersTasksRepository.getWorkOrdersTask(WorkOrderstask_ID, companyId);
	}

	@Transactional
	public void deleteWorkOrdersTask(Long WorkOrdersTask_id, Integer companyId) {

		WorkOrdersTasksRepository.deleteWorkOrdersTask(WorkOrdersTask_id, companyId);
	}

	@Transactional
	public WorkOrdersTasksToParts getWorkOrdersTaskToParts_ONLY_ID(long WorkOrdersTaskToPart_ID, Integer companyId) {
		
		return WorkOrdersTasksToPartsRepository.getWorkOrdersTaskToParts_ONLY_ID(WorkOrdersTaskToPart_ID, companyId);
	}

	@Transactional
	public void deleteWorkOrdersTaskTOParts(Long WorkOrdersTask_id, Integer companyId) {

		WorkOrdersTasksToPartsRepository.deleteWorkOrdersTaskTOParts(WorkOrdersTask_id, companyId);
	}

	@Transactional
	public void addWorkOrdersTask(String jobSub_name, Long workOrderID) throws Exception {

		// WorkOrdersTasksRepository.addWorkOrdersTask(jobSub_name,
		// workOrderID);

		entityManager.createNamedQuery("INSERT INTO WorkOrdersTasks (job_subtypetask, workorders_id) VALUES ('"
				+ jobSub_name + "'," + workOrderID + ");").executeUpdate();

	}

	@Transactional
	public WorkOrdersTasksToLabor getWorkOrdersTaskToLabor_ONLY_ID(long WorkOrdersTaskToLabor_ID, Integer companyId) {

		return WorkOrdersTasksToLaborRepository.getWorkOrdersTaskToLabor_ONLY_ID(WorkOrdersTaskToLabor_ID, companyId);
	}

	@Transactional
	public void deleteWorkOrdersTaskTOLabor(Long WorkOrdersTask_Laborid, Integer companyId) {

		WorkOrdersTasksToLaborRepository.deleteWorkOrdersTaskTOLabor(WorkOrdersTask_Laborid, companyId);
	}

	@Transactional
	public void updateWorkOrderMainTotalCost_TAX(Double TotalWorkOrderTAX, Double TotalWorkOrdercost, Long WorkOrder_ID,
			Integer companyId) throws Exception {

		WorkOrdersDao.updateWorkOrderMainTotalCost_TAX(TotalWorkOrderTAX, TotalWorkOrdercost, WorkOrder_ID, companyId);
	}

	@Transactional
	public List<WorkOrdersDto> SearchWorkOrders(String WorkOrders_Search, Integer companyId, long id) throws Exception {
		List<WorkOrdersDto> Dtos = null;
		TypedQuery<Object[]> query = null;
		if(WorkOrders_Search != null && !WorkOrders_Search.trim().equalsIgnoreCase("") && WorkOrders_Search.indexOf('\'') != 0 ) {
		if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
							+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
							+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
							+ " from WorkOrders WO " + " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
							+ " LEFT JOIN User U ON U.id = WO.assigneeId"
							+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
							+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
							+ " where (lower(WO.workorders_Number) Like ('%" + WorkOrders_Search
							+ "%') OR lower(V.vehicle_registration) Like ('%" + WorkOrders_Search
							+ "%')) AND WO.companyId = " + companyId + " AND WO.markForDelete = 0 ",
							Object[].class);
		} else {
			query = entityManager.createQuery(
					"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
							+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
							+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
							+ " from WorkOrders WO " + " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
							+ " LEFT JOIN User U ON U.id = WO.assigneeId"
							+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
							+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
							+ id + "" + " where (lower(WO.workorders_Number) Like ('%" + WorkOrders_Search
							+ "%') OR lower(V.vehicle_registration) Like ('%" + WorkOrders_Search
							+ "%')) AND WO.companyId = " + companyId + " AND WO.markForDelete = 0 ",
							Object[].class);
		}
		List<Object[]> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersDto();
				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				list.setVehicle_vid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Odometer((Integer) result[4]);
				list.setStart_dateOn((Date) result[5]);
				list.setDue_dateOn((Date) result[6]);
				list.setWorkorders_drivername((String) result[7]);
				list.setAssignee((String) result[8]);
				list.setWorkorders_location((String) result[9]);
				list.setPriorityId((short) result[10]);
				list.setPriority(WorkOrdersType.getPriorityName((short) result[10]));
				list.setCompleted_dateOn((Date) result[11]);
				list.setTotalworkorder_cost((Double) result[12]);
				list.setWorkorders_statusId((short) result[13]);
				list.setWorkorders_status(WorkOrdersType.getStatusName((short) result[13]));
				list.setWorkorders_document((boolean) result[14]);
				list.setWorkorders_document_id((Long) result[15]);

				Dtos.add(list);
			}
		}
		}
		return Dtos;
	}

	@Override
	public List<WorkOrdersDto> SearchWorkOrdersList(Long WorkOrders_Search, Integer companyId, long id)
			throws Exception {
		TypedQuery<Object[]> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
							+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
							+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
							+ " from WorkOrders WO " + " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
							+ " LEFT JOIN User U ON U.id = WO.assigneeId"
							+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
							+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
							+ " where WO.workorders_Number = "+WorkOrders_Search+"  AND WO.companyId = " + companyId + " AND WO.markForDelete = 0 ",
							Object[].class);
		} else {
			query = entityManager.createQuery(
					"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
							+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
							+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
							+ " from WorkOrders WO " + " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
							+ " LEFT JOIN User U ON U.id = WO.assigneeId"
							+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
							+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
							+ id + "" + " where WO.workorders_Number = "+WorkOrders_Search+" AND WO.companyId = " + companyId + " AND WO.markForDelete = 0 ",
							Object[].class);
		}
		List<Object[]> results = query.getResultList();

		List<WorkOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersDto();
				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				list.setVehicle_vid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Odometer((Integer) result[4]);
				list.setStart_dateOn((Date) result[5]);
				list.setDue_dateOn((Date) result[6]);
				list.setWorkorders_drivername((String) result[7]);
				list.setAssignee((String) result[8]);
				list.setWorkorders_location((String) result[9]);
				list.setPriorityId((short) result[10]);
				list.setPriority(WorkOrdersType.getPriorityName((short) result[10]));
				list.setCompleted_dateOn((Date) result[11]);
				list.setTotalworkorder_cost((Double) result[12]);
				list.setWorkorders_statusId((short) result[13]);
				list.setWorkorders_status(WorkOrdersType.getStatusName((short) result[13]));
				list.setWorkorders_document((boolean) result[14]);
				list.setWorkorders_document_id((Long) result[15]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<WorkOrdersDto> VehicleToWorkOrdersList(Integer Vehicle_id, Integer companyId) throws Exception {
		try{
		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
							+ " WO.start_date, WO.due_date, U.email, B.partlocation_name, WO.priorityId,"
						+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
						+ " from WorkOrders WO " + " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
						+ " LEFT JOIN User U ON U.id = WO.assigneeId"
						+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
						+ " where WO.vehicle_vid=" + Vehicle_id + " AND WO.companyId = " + companyId
						+ " AND  WO.markForDelete = 0 ORDER BY WO.workorders_id desc ",
						Object[].class);

		List<Object[]> results = query.getResultList();
		List<WorkOrdersDto> Dtos = null;
			try{
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersDto();
				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				list.setVehicle_vid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Odometer((Integer) result[4]);
				list.setStart_dateOn((Date) result[5]);
				list.setDue_dateOn((Date) result[6]);
					list.setAssignee((String) result[7]);
					list.setWorkorders_location((String) result[8]);
					list.setPriorityId((short) result[9]);
					list.setCompleted_dateOn((Date) result[10]);
					list.setTotalworkorder_cost((Double) result[11]);
					list.setWorkorders_statusId((short) result[12]);
					list.setWorkorders_status(WorkOrdersType.getStatusName((short) result[12]));
					list.setWorkorders_document((boolean) result[13]);
					list.setWorkorders_document_id((Long) result[14]);
				Dtos.add(list);
			}
			}}catch(Exception e){
				throw e;
		}
		return Dtos;
		}catch(Exception e){
			throw e;
	}
	}

	@Transactional
	public void uploadWorkOrdersDocument(org.fleetopgroup.persistence.document.WorkOrdersDocument WorkOrdersdocument) throws Exception {

		WorkOrdersdocument.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_WORK_ORDER_DOCUMENT));
		mongoTemplate.save(WorkOrdersdocument);
	}

	@Transactional
	public org.fleetopgroup.persistence.document.WorkOrdersDocument getWorkOrdersDocument(Long WorkOrders_id, Integer companyId) throws Exception {

		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("workorder_id").is(WorkOrders_id).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.WorkOrdersDocument.class);
	}

	@Transactional
	public void updateOldWorkOrdersDocument(org.fleetopgroup.persistence.document.WorkOrdersDocument WorkOrders_id) throws Exception {
		mongoTemplate.save(WorkOrders_id);
	}

	@Transactional
	public void addWorkOrdersToReceived(WorkOrdersTasksToReceived workOrders) throws Exception {

		WorkOrdersTasksToReceived.save(workOrders);
	}

	@Transactional
	public List<WorkOrdersDto> ReportWorkOrders(String WorkOrderQuery, String RangeFrom, String RangeTo,
			CustomUserDetails userDetails) throws Exception {
		TypedQuery<Object[]> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
							+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
							+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
							+ ", WO.assigneeId, WO.approvalStatusId from WorkOrders WO " + " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
							+ " LEFT JOIN User U ON U.id = WO.assigneeId"
							+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
							+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
							+ " where ((WO.start_date BETWEEN '" + RangeFrom + "' AND  '" + RangeTo + "') "
							+ WorkOrderQuery + ") AND WO.companyId = " + userDetails.getCompany_id()
							+ " AND WO.markForDelete = 0",
							Object[].class);
		} else {
			query = entityManager.createQuery(
					"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
							+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
							+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
							+ " , WO.assigneeId, WO.approvalStatusId from WorkOrders WO " + " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
							+ " LEFT JOIN User U ON U.id = WO.assigneeId"
							+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
							+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + "" + " where ((WO.start_date BETWEEN '" + RangeFrom + "' AND  '"
							+ RangeTo + "') " + WorkOrderQuery + ") AND WO.companyId = " + userDetails.getCompany_id()
							+ " AND WO.markForDelete = 0",
							Object[].class);
		}
		List<Object[]> results = query.getResultList();

		List<WorkOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersDto();
				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				list.setVehicle_vid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Odometer((Integer) result[4]);
				list.setStart_dateOn((Date) result[5]);
				list.setDue_dateOn((Date) result[6]);
				list.setWorkorders_drivername((String) result[7]);
				list.setAssignee((String) result[8]);
				list.setWorkorders_location((String) result[9]);
				list.setPriorityId((short) result[10]);
				list.setPriority(WorkOrdersType.getPriorityName((short) result[10]));
				list.setCompleted_dateOn((Date) result[11]);
				list.setTotalworkorder_cost((Double) result[12]);
				list.setWorkorders_statusId((short) result[13]);
				list.setWorkorders_status(WorkOrdersType.getStatusName((short) result[13]));
				list.setWorkorders_document((boolean) result[14]);
				list.setWorkorders_document_id((Long) result[15]);
				list.setAssigneeId((Long) result[16]);
				list.setApprovalStatusId((short) result[17]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<WorkOrdersTasksDto> getLast_WorkOrdersTasks(Integer jobType, Integer jobsubtype, Integer vehicle_id) {

		// return WorkOrdersTasksRepository.getLast_WorkOrdersTasks(jobType,
		// jobsubtype, vehicle_id);
		TypedQuery<Object[]> query = entityManager.createQuery(
				" SELECT WO.workordertaskid, JT.Job_Type, WO.job_typetaskId, WO.job_subtypetask_id, JST.Job_ROT, JST.Job_ROT_number,"
						+ " WO.mark_complete, WO.totalpart_cost, WO.totallaber_cost, WO.totaltask_cost, WO.vehicle_vid, WO.last_occurred_date,"
						+ " WO.last_occurred_woId, WO.last_occurred_odameter, WO.workorders.workorders_id " + " FROM WorkOrdersTasks AS WO "
						+ " LEFT JOIN JobType JT ON JT.Job_id = WO.job_typetaskId "
						+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = WO.job_subtypetask_id "
						+ " where WO.job_typetaskId=" + jobType + " AND WO.job_subtypetask_id =" + jobsubtype
						+ " AND  WO.vehicle_vid =" + vehicle_id + " ORDER BY WO.workordertaskid DESC ",
						Object[].class);
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<Object[]> results = query.getResultList();

		List<WorkOrdersTasksDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersTasksDto>();
			WorkOrdersTasksDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersTasksDto();

				list.setWorkordertaskid((Long) result[0]);
				list.setJob_typetask((String) result[1]);
				list.setJob_typetaskId((Integer) result[2]);
				list.setJob_subtypetask_id((Integer) result[3]);
				list.setJob_subtypetask((String) result[4] + " - " + (String) result[5]);
				list.setMark_complete((Integer) result[6]);
				list.setTotalpart_cost((Double) result[7]);
				list.setTotallaber_cost((Double) result[8]);
				list.setTotaltask_cost((Double) result[9]);
				list.setVehicle_vid((Integer) result[10]);
				list.setLast_occurred_dateOn((Date) result[11]);
				list.setLast_occurred_woId((Long) result[12]);
				list.setLast_occurred_odameter((Integer) result[13]);
				list.setWorkorders_id((Long) result[14]);
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<WorkOrders> getLast_WorkOrdersTask_To_WorkOrders_details(Integer jobType, Integer jobsubtype,
			Integer vehicle_id, Integer companyId) {

		// return WorkOrdersTasksRepository.getLast_WorkOrdersTasks(jobType,
		// jobsubtype, vehicle_id);
		TypedQuery<WorkOrders> query = entityManager.createQuery(
				"FROM WorkOrders AS W where W.workorders_id IN(select T.workorders.workorders_id FROM WorkOrdersTasks AS T where T.job_typetaskId="
						+ jobType + " AND T.job_subtypetask_id =" + jobsubtype + " AND  T.vehicle_vid =" + vehicle_id
						+ " AND W.companyId = " + companyId
						+ " AND W.markForDelete = 0 AND T.markForDelete = 0 ORDER BY T.workordertaskid DESC ) AND W.workorders_statusId = "+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+" ORDER BY W.workorders_id DESC ",
						WorkOrders.class);
		query.setFirstResult(0);
		query.setMaxResults(1);
		return query.getResultList();
	}
	
	@Transactional
	public WorkOrders getLastWorkOrdersTaskToWorkOrdersdetails(Integer jobType, Integer jobsubtype,
			Integer vehicle_id, Integer companyId ,Long Workorders_id ,String completeQuery)throws Exception {
		
		TypedQuery<WorkOrders> query = entityManager.createQuery(
				" select wo From WorkOrders wo "
				+ "INNER JOIN WorkOrdersTasks WT ON WT.workorders.workorders_id = wo.workorders_id"
				+ " where WT.job_typetaskId = "+jobType+" AND WT.job_subtypetask_id = "+jobsubtype+" "
						+ " AND wo.vehicle_vid = "+vehicle_id+" AND wo.companyId = "+companyId+" AND WT.markForDelete = 0"
						+ " and wo.markForDelete = 0 and wo.workorders_statusId = "+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+" "
						+ " "+completeQuery+" and wo.workorders_id  <> "+Workorders_id+" order by wo.completed_date desc ",
						WorkOrders.class);
		
		WorkOrders	workOrders = null;
		try {
				query.setMaxResults(1);
				workOrders = query.getSingleResult();
			
		} catch (NoResultException nre) {
		}
		return workOrders;
	}
		
		
	

	@Override
	public List<WorkOrders> getLast_WorkOrdersTask_To_WorkOrders_details(String jobType, String jobsubtype,
			Integer vehicle_id, Integer companyId) {

		TypedQuery<WorkOrders> query = entityManager.createQuery(
				"FROM WorkOrders AS W where W.workorders_id IN(select T.workorders.workorders_id FROM WorkOrdersTasks AS T where T.job_typetask='"
						+ jobType + "' AND T.job_subtypetask ='" + jobsubtype + "' AND  T.vehicle_vid =" + vehicle_id
						+ " AND W.companyId = " + companyId
						+ " AND W.markForDelete = 0 ORDER BY T.workordertaskid DESC ) ORDER BY W.workorders_id DESC ",
						WorkOrders.class);
		query.setFirstResult(0);
		query.setMaxResults(1);
		return query.getResultList();
	}

	@Transactional
	public WorkOrders getLast_WorkOrdersTaskID_To_WorkOrders_details(Long workOrderTaskId, Integer companyId) {
		WorkOrders	workOrders	= null;
		Query query = entityManager.createQuery(
				"FROM WorkOrders AS W where W.workorders_id IN ( select T.workorders.workorders_id FROM WorkOrdersTasks AS T where T.workordertaskid="
						+ workOrderTaskId + " AND T.companyId = " + companyId
						+ " AND T.markForDelete = 0 ) AND  W.companyId = " + companyId
						+ " AND W.markForDelete = 0 ORDER BY W.workorders_id DESC ");
		try {
			workOrders = (WorkOrders) query.getSingleResult();
		} catch (NoResultException nre) {
			
		}
		
		return workOrders;
	}

	@Transactional
	public WorkOrders getLast_WorkOrdersTasktoPARTID_To_WorkOrders_details(Long workOrderTasktoPartID,
			Integer companyId) {
		WorkOrders	workOrders	= null;
		Query query = entityManager.createQuery(
				"FROM WorkOrders AS W where W.workorders_id IN(select T.workorders_id FROM WorkOrdersTasksToParts AS T where T.workordertaskto_partid="
						+ workOrderTasktoPartID + " AND T.markForDelete = 0 ) AND W.companyId = " + companyId
						+ " AND W.markForDelete = 0 ORDER BY W.workorders_id DESC ");
		
		try {
			workOrders = (WorkOrders) query.getSingleResult();
		} catch (NoResultException nre) {
			
		}
		return workOrders;
	}

	@Transactional
	public List<WorkOrdersTasksDto> getLast_WorkOrdersTasks(Integer jobType, Integer jobsubtype, Integer vehicle_id,
			Long workorders_id, Integer companyId) {

		try {
			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					"SELECT WO.workordertaskid, JT.Job_Type, WO.job_typetaskId, WO.job_subtypetask_id, JST.Job_ROT, JST.Job_ROT_number,"
							+ " WO.mark_complete, WO.totalpart_cost, WO.totallaber_cost, WO.totaltask_cost, WO.vehicle_vid, WO.last_occurred_date,"
							+ " WO.last_occurred_woId, WO.last_occurred_odameter, WO.workorders"
							+ " from WorkOrdersTasks WO " + " LEFT JOIN JobType JT ON JT.Job_id = WO.job_typetaskId"
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = WO.job_subtypetask_id"
							+ " where WO.job_typetaskId=" + jobType + " AND WO.job_subtypetask_id =" + jobsubtype
							+ " AND  WO.vehicle_vid =" + vehicle_id + " AND WO.workorders.workorders_id <> '"
							+ workorders_id + "' AND WO.companyId = " + companyId
							+ " AND WO.markForDelete = 0 ORDER BY WO.workordertaskid DESC",
							Object[].class);
			List<Object[]> results = query.getResultList();

			List<WorkOrdersTasksDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<WorkOrdersTasksDto>();
				WorkOrdersTasksDto list = null;
				for (Object[] result : results) {
					list = new WorkOrdersTasksDto();

					list.setWorkordertaskid((Long) result[0]);
					list.setJob_typetask((String) result[1]);
					list.setJob_typetaskId((Integer) result[2]);
					list.setJob_subtypetask_id((Integer) result[3]);
					list.setJob_subtypetask((String) result[4] + " - " + (String) result[5]);
					list.setMark_complete((Integer) result[6]);
					list.setTotalpart_cost((Double) result[7]);
					list.setTotallaber_cost((Double) result[8]);
					list.setTotaltask_cost((Double) result[9]);
					list.setVehicle_vid((Integer) result[10]);
					list.setLast_occurred_dateOn((Date) result[11]);
					list.setLast_occurred_woId((Long) result[12]);
					list.setLast_occurred_odameter((Integer) result[13]);
					if (result[14] != null) {
						WorkOrders workOrder = (WorkOrders) result[14];
						list.setWorkorders_id(workOrder.getWorkorders_id());
					}
					Dtos.add(list);
				}
			}
			return Dtos;

		} catch (Exception e) {
			System.err.println("Exception : " + e);
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IWorkOrdersService#
	 * vehicle_WiseRepair_Report(java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<WorkOrdersDto> vehicle_WiseRepair_Report(String dateRangeFrom, String dateRangeTo,
			String multipleVehicle, Integer companyId) throws Exception {
		// return WorkOrdersDao.VehicleToWorkOrdersList(Vehicle_id, companyId);

		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
						+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
						+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
						+ " from WorkOrders WO " 
						+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
						+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
						+ " LEFT JOIN User U ON U.id = WO.assigneeId"
						+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
						+ " where WO.due_date between '" + dateRangeFrom + "' AND '" + dateRangeTo
						+ "' AND  WO.vehicle_vid IN (" + multipleVehicle + ") AND WO.companyId = " + companyId
						+ " AND WO.markForDelete = 0",
						Object[].class);

		List<Object[]> results = query.getResultList();

		List<WorkOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersDto();
				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				list.setVehicle_vid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Odometer((Integer) result[4]);
				list.setStart_dateOn((Date) result[5]);
				list.setDue_dateOn((Date) result[6]);
				list.setWorkorders_drivername((String) result[7]);
				list.setAssignee((String) result[8]);
				list.setWorkorders_location((String) result[9]);
				list.setPriorityId((short) result[10]);
				list.setPriority(WorkOrdersType.getPriorityName((short) result[10]));
				list.setCompleted_dateOn((Date) result[11]);
				if(result[12] != null)
				list.setTotalworkorder_cost(Double.parseDouble(toFixedTwo.format(result[12])));
				list.setWorkorders_statusId((short) result[13]);
				list.setWorkorders_status(WorkOrdersType.getStatusName((short) result[13]));
				list.setWorkorders_document((boolean) result[14]);
				list.setWorkorders_document_id((Long) result[15]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}
	
	@Override
	public List<WorkOrdersDto> vehicle_WiseRepair_Report(String dateRangeFrom, String dateRangeTo, Long createdById,
			Integer companyId) throws Exception {
		// return WorkOrdersDao.VehicleToWorkOrdersList(Vehicle_id, companyId);

		TypedQuery<Object[]> query = null;
		if(createdById != null && createdById > 0) {
			query = entityManager.createQuery(
					"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
							+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
							+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
							+ " from WorkOrders WO " 
							+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
							+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
							+ " LEFT JOIN User U ON U.id = WO.assigneeId"
							+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
							+ " where WO.due_date between '" + dateRangeFrom + "' AND '" + dateRangeTo
							+ "' AND  WO.createdById = "+createdById+" AND WO.companyId = " + companyId
							+ " AND WO.markForDelete = 0",
							Object[].class);
		}else {
			query = entityManager.createQuery(
					"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
							+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
							+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
							+ " from WorkOrders WO " 
							+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
							+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
							+ " LEFT JOIN User U ON U.id = WO.assigneeId"
							+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
							+ " where WO.due_date between '" + dateRangeFrom + "' AND '" + dateRangeTo
							+ "' AND WO.companyId = " + companyId
							+ " AND WO.markForDelete = 0",
							Object[].class);
		}
		

		List<Object[]> results = query.getResultList();

		List<WorkOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersDto();
				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				list.setVehicle_vid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Odometer((Integer) result[4]);
				list.setStart_dateOn((Date) result[5]);
				list.setDue_dateOn((Date) result[6]);
				list.setWorkorders_drivername((String) result[7]);
				list.setAssignee((String) result[8]);
				list.setWorkorders_location((String) result[9]);
				list.setPriorityId((short) result[10]);
				list.setPriority(WorkOrdersType.getPriorityName((short) result[10]));
				list.setCompleted_dateOn((Date) result[11]);
				if(result[12] != null)
				list.setTotalworkorder_cost(Double.parseDouble(toFixedTwo.format(result[12])));
				list.setWorkorders_statusId((short) result[13]);
				list.setWorkorders_status(WorkOrdersType.getStatusName((short) result[13]));
				list.setWorkorders_document((boolean) result[14]);
				list.setWorkorders_document_id((Long) result[15]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}

	@Override
	public List<WorkOrdersDto> vehicle_WiseRepair_Report(String dateRangeFrom, String dateRangeTo, Long userId,
			String vids, Integer companyId) throws Exception {
		// return WorkOrdersDao.VehicleToWorkOrdersList(Vehicle_id, companyId);

		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
						+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
						+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
						+ " from WorkOrders WO " 
						+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
						+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
						+ " LEFT JOIN User U ON U.id = WO.assigneeId"
						+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
						+ " where WO.due_date between '" + dateRangeFrom + "' AND '" + dateRangeTo
						+ "' AND  WO.vehicle_vid IN (" + vids + ") AND WO.createdById = "+userId+" AND WO.companyId = " + companyId
						+ " AND WO.markForDelete = 0",
						Object[].class);

		List<Object[]> results = query.getResultList();

		List<WorkOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersDto();
				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				list.setVehicle_vid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Odometer((Integer) result[4]);
				list.setStart_dateOn((Date) result[5]);
				list.setDue_dateOn((Date) result[6]);
				list.setWorkorders_drivername((String) result[7]);
				list.setAssignee((String) result[8]);
				list.setWorkorders_location((String) result[9]);
				list.setPriorityId((short) result[10]);
				list.setPriority(WorkOrdersType.getPriorityName((short) result[10]));
				list.setCompleted_dateOn((Date) result[11]);
				if(result[12] != null)
				list.setTotalworkorder_cost(Double.parseDouble(toFixedTwo.format(result[12])));
				list.setWorkorders_statusId((short) result[13]);
				list.setWorkorders_status(WorkOrdersType.getStatusName((short) result[13]));
				list.setWorkorders_document((boolean) result[14]);
				list.setWorkorders_document_id((Long) result[15]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}

	/*in the case of warehouse location*/
	@Transactional
	public List<WorkOrdersDto> depot_wise_Report(String dateRangeFrom, String dateRangeTo,
			String multipleVehicle, String locationId, Integer companyId) throws Exception {
		// return WorkOrdersDao.VehicleToWorkOrdersList(Vehicle_id, companyId);

		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
						+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
						+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
						+ " from WorkOrders WO " 
						+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
						+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
						+ " LEFT JOIN User U ON U.id = WO.assigneeId"
						+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
						+ " where WO.due_date between '" + dateRangeFrom + "' AND '" + dateRangeTo
						+ "' AND  WO.vehicle_vid IN (" + multipleVehicle + ") AND WO.companyId = " + companyId
						+ " AND WO.workorders_location_ID = " + locationId + " AND WO.markForDelete = 0",
						Object[].class);

		List<Object[]> results = query.getResultList();

		List<WorkOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersDto();
				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				list.setVehicle_vid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Odometer((Integer) result[4]);
				list.setStart_dateOn((Date) result[5]);
				list.setDue_dateOn((Date) result[6]);
				list.setWorkorders_drivername((String) result[7]);
				list.setAssignee((String) result[8]);
				list.setWorkorders_location((String) result[9]);
				list.setPriorityId((short) result[10]);
				list.setPriority(WorkOrdersType.getPriorityName((short) result[10]));
				list.setCompleted_dateOn((Date) result[11]);
				list.setTotalworkorder_cost((Double) result[12]);
				list.setWorkorders_statusId((short) result[13]);
				list.setWorkorders_status(WorkOrdersType.getStatusName((short) result[13]));
				list.setWorkorders_document((boolean) result[14]);
				list.setWorkorders_document_id((Long) result[15]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}



	@Transactional
	public List<WorkOrdersDto> vehicle_WiseRepair_Report_location(String dateRangeFrom, String dateRangeTo,
			String multipleVehicle, String locationId, Integer companyId) throws Exception {
		// return WorkOrdersDao.VehicleToWorkOrdersList(Vehicle_id, companyId);

		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
						+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
						+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
						+ " from WorkOrders WO " 
						+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
						+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
						+ " LEFT JOIN User U ON U.id = WO.assigneeId"
						+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
						+ " where WO.due_date between '" + dateRangeFrom + "' AND '" + dateRangeTo
						+ "' AND  WO.vehicle_vid IN (" + multipleVehicle + ") AND WO.companyId = " + companyId
						+ " AND WO.workorders_location_ID = " + locationId + " AND WO.markForDelete = 0",
						Object[].class);

		List<Object[]> results = query.getResultList();

		List<WorkOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersDto();
				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				list.setVehicle_vid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Odometer((Integer) result[4]);
				list.setStart_dateOn((Date) result[5]);
				list.setDue_dateOn((Date) result[6]);
				list.setWorkorders_drivername((String) result[7]);
				list.setAssignee((String) result[8]);
				list.setWorkorders_location((String) result[9]);
				list.setPriorityId((short) result[10]);
				list.setPriority(WorkOrdersType.getPriorityName((short) result[10]));
				list.setCompleted_dateOn((Date) result[11]);
				list.setTotalworkorder_cost((Double) result[12]);
				list.setWorkorders_statusId((short) result[13]);
				list.setWorkorders_status(WorkOrdersType.getStatusName((short) result[13]));
				list.setWorkorders_document((boolean) result[14]);
				list.setWorkorders_document_id((Long) result[15]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}






	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IWorkOrdersService#
	 * getWorkOrdersTasks_vehicle_WiseRepair_Report(java.lang.String)
	 */
	@Transactional
	public List<WorkOrdersTasksDto> getWorkOrdersTasks_vehicle_WiseRepair_Report(String multipleWorkOrders_id,
			Integer companyId) {
		SimpleDateFormat sqldateTime = new SimpleDateFormat("dd-MM-yyyy");
		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT WO.workordertaskid, JT.Job_Type, WO.job_typetaskId, WO.job_subtypetask_id, JST.Job_ROT, JST.Job_ROT_number,"
						+ " WO.mark_complete, WO.totalpart_cost, WO.totallaber_cost, WO.totaltask_cost, WO.vehicle_vid, WO.last_occurred_date,"
						+ " WO.last_occurred_woId, WO.last_occurred_odameter, WO.workorders.workorders_id, WO.jobTypeRemark"
						+ " FROM WorkOrdersTasks AS WO " 
						+ " LEFT JOIN JobType JT ON JT.Job_id = WO.job_typetaskId"
						+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = WO.job_subtypetask_id"
						+ " where WO.workorders.workorders_id IN (" + multipleWorkOrders_id + ") AND WO.companyId = "
						+ companyId + " AND WO.markForDelete = 0",
						Object[].class);
		List<Object[]> results = query.getResultList();

		List<WorkOrdersTasksDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersTasksDto>();
			WorkOrdersTasksDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersTasksDto();

				list.setWorkordertaskid((Long) result[0]);
				list.setJob_typetask((String) result[1]);
				list.setJob_typetaskId((Integer) result[2]);
				list.setJob_subtypetask_id((Integer) result[3]);
				list.setJob_subtypetask((String) result[4] + " - " + (String) result[5]);
				list.setMark_complete((Integer) result[6]);
				if(result[7] != null)
				list.setTotalpart_cost(Double.parseDouble(toFixedTwo.format(result[7])));
				if(result[8] != null)
				list.setTotallaber_cost(Double.parseDouble(toFixedTwo.format(result[8])));
				if(result[9] != null)
				list.setTotaltask_cost(Double.parseDouble(toFixedTwo.format(result[9])));
				list.setVehicle_vid((Integer) result[10]);
				list.setLast_occurred_dateOn((Date) result[11]);				
				list.setLast_occurred_woId((Long) result[12]);
				list.setLast_occurred_odameter((Integer) result[13]);
				list.setWorkorders_id((Long) result[14]);
				list.setJobTypeRemark((String) result[15]);

				if(list.getLast_occurred_dateOn() != null) {
					list.setLast_occurred_date(sqldateTime.format(list.getLast_occurred_dateOn()));
				}

				Dtos.add(list);
			}
		}

		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IWorkOrdersService#
	 * getWorkOrdersTasksToParts_vehicle_WiseRepair_Report(java.lang.String)
	 */
	@Transactional
	public List<WorkOrdersTasksToPartsDto> getWorkOrdersTasksToParts_vehicle_WiseRepair_Report(
			String multipleWorkOrders_id, Integer companyId) {

		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT WO.workordertaskto_partid, WO.partid, MP.partname, MP.partnumber, MP.partTypeId, PL.partlocation_name, WO.quantity,"
						+ " WO.parteachcost, WO.totalcost, WO.oldpart, WO.inventory_id, WO.workordertaskid, WO.vehicle_vid, WO.last_occurred_date, "
						+ " WO.last_occurred_woId, WO.workorders_id, PL.partlocation_name From WorkOrdersTasksToParts WO "
						+ " INNER JOIN MasterParts MP ON MP.partid = WO.partid "
						+ " LEFT JOIN PartLocations PL ON PL.partlocation_id = WO.locationId "
						+ " where  WO.workorders_id IN (" + multipleWorkOrders_id + ") AND WO.companyId = " + companyId
						+ " AND WO.markForDelete = 0",
						Object[].class);

		List<Object[]> results = query.getResultList();

		List<WorkOrdersTasksToPartsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersTasksToPartsDto>();
			WorkOrdersTasksToPartsDto list = null;
			for (Object[] result : results) {

				list = new WorkOrdersTasksToPartsDto();

				list.setWorkordertaskto_partid((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPartname((String) result[2]);
				list.setPartnumber((String) result[3]);
				list.setParttype(PartType.getPartTypeName((short) result[4]));
				list.setLocation((String) result[5]);
				if(result[6] != null)
				list.setQuantity(Double.parseDouble(toFixedTwo.format(result[6])) );
				if(result[7] != null)
				list.setParteachcost(Double.parseDouble(toFixedTwo.format(result[7])));
				if(result[8] != null)
				list.setTotalcost(Double.parseDouble(toFixedTwo.format(result[8])));
				list.setOldpart((String) result[9]);
				list.setInventory_id((Long) result[10]);
				list.setWorkordertaskid((Long) result[11]);
				list.setVehicle_vid((Integer) result[12]);
				list.setLast_occurred_date((Date) result[13]);
				list.setLast_occurred_woId((Long) result[14]);
				list.setWorkorders_id((Long) result[15]);
				list.setPartlocation_name((String) result[16] );
				Dtos.add(list);
			}
		}

		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IWorkOrdersService#
	 * WorkOrdersTasks_vehicle_WiseRepair_TotalAmount(java.lang.String)
	 */
	@Transactional
	public List<Double> WorkOrdersTasks_vehicle_WiseRepair_TotalAmount(String multipleWorkOrders_id,
			Integer companyid) {

		TypedQuery<Double> query = entityManager
				.createQuery(
						"select sum(totaltask_cost) from WorkOrdersTasks where workorders_id IN ("
								+ multipleWorkOrders_id + ") AND companyId = " + companyid + " AND markForDelete = 0",
								Double.class);

		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IWorkOrdersService#
	 * getDeployment_Page_WorkOrders(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public Page<WorkOrders> getDeployment_Page_WorkOrders(short status, Integer pageNumber,
			CustomUserDetails userDetails) throws Exception {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			return WorkOrdersDao.getDeployment_Page_WorkOrders(status, userDetails.getCompany_id(), pageable);
		}
		return WorkOrdersDao.getDeployment_Page_WorkOrders(status, userDetails.getId(), userDetails.getCompany_id(),
				pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IWorkOrdersService#listOpenWorkOrders
	 * (java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public List<WorkOrdersDto> listOpenWorkOrders(short WorkOrders_open, Integer pageNumber,
			CustomUserDetails userDetails) throws Exception {

		TypedQuery<Object[]> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
							+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
							+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id, WO.subLocationId,"
							+ " PSL.partlocation_name, I.ISSUES_SUMMARY "
							+ " from WorkOrders WO " + " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
							+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
							+ " LEFT JOIN User U ON U.id = WO.assigneeId"
							+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
							+ " LEFT JOIN PartLocations AS PSL ON PSL.partlocation_id = WO.subLocationId "
							+ " LEFT JOIN Issues AS I ON I.ISSUES_ID = WO.ISSUES_ID "
							+ " where WO.workorders_statusId=" + WorkOrders_open + " AND WO.companyId = "
							+ userDetails.getCompany_id() + " AND WO.markForDelete = 0 ORDER BY WO.workorders_id desc ",
							Object[].class);
		} else {
			query = entityManager.createQuery(
					" SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
							+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
							+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id, WO.subLocationId,"
							+ " PSL.partlocation_name, I.ISSUES_SUMMARY "
							+ " from WorkOrders WO " + " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
							+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
							+ " LEFT JOIN User U ON U.id = WO.assigneeId"
							+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
							+ " LEFT JOIN PartLocations AS PSL ON PSL.partlocation_id = WO.subLocationId "
							+ " LEFT JOIN Issues AS I ON I.ISSUES_ID = WO.ISSUES_ID "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + "" + " where WO.workorders_statusId=" + WorkOrders_open
							+ "	 AND WO.companyId = " + userDetails.getCompany_id()
							+ " AND WO.markForDelete = 0 ORDER BY WO.workorders_id desc ",
							Object[].class);
		}
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		List<Object[]> results = query.getResultList();

		List<WorkOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersDto();
				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				list.setVehicle_vid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Odometer((Integer) result[4]);
				list.setStart_dateOn((Date) result[5]);
				list.setDue_dateOn((Date) result[6]);
				list.setWorkorders_drivername((String) result[7]);
				list.setAssignee((String) result[8]);
				list.setWorkorders_location((String) result[9]);
				list.setPriorityId((short) result[10]);
				list.setPriority(WorkOrdersType.getPriorityName((short) result[10]));
				list.setCompleted_dateOn((Date) result[11]);
				list.setTotalworkorder_cost((Double) result[12]);
				list.setWorkorders_statusId((short) result[13]);
				list.setWorkorders_status(WorkOrdersType.getStatusName((short) result[13]));
				list.setWorkorders_document((boolean) result[14]);
				list.setWorkorders_document_id((Long) result[15]);
				if(result[16] != null) {
					list.setSubLocationId((Integer) result[16]);
					list.setSubLocation((String) result[17]);
					if(result[18] != null) {
						list.setIssueSummary((String) result[18]);	
					}else{
						list.setIssueSummary(" ");	
					}
				}

				Dtos.add(list);
			}
		}
		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IWorkOrdersService#
	 * getDeployment_Page_WorkOrders(java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public Page<WorkOrders> getDeployment_Page_WorkOrders_VehicleId(Integer Vehicle_id, Integer pageNumber,
			Integer companyId) {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return WorkOrdersDao.getDeployment_Page_WorkOrders_VehicleId(Vehicle_id, companyId, pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IWorkOrdersService#listOpenWorkOrders
	 * (java.lang.String, java.lang.Integer)
	 */
	@Transactional
	public List<WorkOrdersDto> listOpenWorkOrders_VehicleId(Integer Vehicle_id, Integer pageNumber, Integer companyId) {

		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
						+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
						+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id"
						+ " from WorkOrders WO " + " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
						+ " LEFT JOIN User U ON U.id = WO.assigneeId"
						+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
						+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
						+ " where WO.vehicle_vid=" + Vehicle_id + " AND WO.companyId = " + companyId
						+ " AND  WO.markForDelete = 0 ORDER BY WO.workorders_id desc ",
						Object[].class);

		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		List<Object[]> results = query.getResultList();

		List<WorkOrdersDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersDto>();
			WorkOrdersDto list = null;
			for (Object[] result : results) {
				list = new WorkOrdersDto();
				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				list.setVehicle_vid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Odometer((Integer) result[4]);
				list.setStart_dateOn((Date) result[5]);
				list.setDue_dateOn((Date) result[6]);
				list.setWorkorders_drivername((String) result[7]);
				list.setAssignee((String) result[8]);
				list.setWorkorders_location((String) result[9]);
				list.setPriorityId((short) result[10]);
				list.setPriority(WorkOrdersType.getPriorityName((short) result[10]));
				list.setCompleted_dateOn((Date) result[11]);
				list.setTotalworkorder_cost((Double) result[12]);
				list.setWorkorders_statusId((short) result[13]);
				list.setWorkorders_status(WorkOrdersType.getStatusName((short) result[13]));
				list.setWorkorders_document((boolean) result[14]);
				list.setWorkorders_document_id((Long) result[15]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IWorkOrdersService#
	 * Update_WorkOrdre_Document_Available_TRUE(boolean, java.lang.Long)
	 */
	@Transactional
	public void Update_WorkOrdre_Document_Available_TRUE(Long workorders_document_id, boolean workOrder_document,
			Long workorder_id, Integer companyId) {

		WorkOrdersDao.Update_WorkOrdre_Document_Available_TRUE(workorders_document_id, workOrder_document, workorder_id,
				companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IWorkOrdersService#
	 * WorkOrders_Part_Consuming_WorkOrdersTasksToParts(java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<WorkOrdersTasksToPartsDto> WorkOrders_Part_Consuming_WorkOrdersTasksToParts(String workOrderQuery,
			String dateRangeFrom, String dateRangeTo) {
		// Note : this part Consuming WorkOrders parts

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT  v.partid, MP.partname, MP.partnumber, COUNT(v.partid) as inventory_id  FROM WorkOrdersTasksToParts AS v"
						+ " INNER JOIN MasterParts MP ON MP.partid = v.partid "
						+ " WHERE v.workorders_id IN (SELECT p.workorders_id FROM WorkOrders AS p WHERE (p.completed_date BETWEEN '"
						+ dateRangeFrom + "' AND  '" + dateRangeTo + "') " + workOrderQuery
						+ ") GROUP BY v.partid ORDER BY MP.partname ASC",
						Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<WorkOrdersTasksToPartsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<WorkOrdersTasksToPartsDto>();
			WorkOrdersTasksToPartsDto dropdown = null;
			for (Object[] result : results) {
				dropdown = new WorkOrdersTasksToPartsDto();

				dropdown.setPartid((Long) result[0]);
				dropdown.setPartname((String) result[1]);
				dropdown.setPartnumber((String) result[2]);
				dropdown.setInventory_id((Long) result[3]);

				Dtos.add(dropdown);
			}
		}
		return Dtos;
	}

	@Override
	public WorkOrdersDto getWorkOrdersByNumber(long WorkOrders, long id, Integer companyId) throws Exception {
		try {
			Query query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
								+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
								+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id,"
								+ " WO.workorders_location_ID, WO.indentno, WO.totalsubworktask_cost, WO.totalworktax_cost, WO.initial_note, WO.ISSUES_ID,"
								+ " WO.created, WO.lastupdated, U2.email, U3.email " + " from WorkOrders WO "
								+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
								+ " LEFT JOIN User U ON U.id = WO.assigneeId"
								+ " LEFT JOIN User U2 ON U2.id = WO.createdById"
								+ " LEFT JOIN User U3 ON U3.id = WO.lastModifiedById"
								+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
								+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
								+ " where WO.workorders_Number=" + WorkOrders + " AND WO.companyId = " + companyId
								+ " AND WO.markForDelete = 0");

			} else {
				query = entityManager.createQuery(
						"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer,"
								+ " WO.start_date, WO.due_date, D.driver_firstname, U.email, B.partlocation_name, WO.priorityId,"
								+ " WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId, WO.workorders_document, WO.workorders_document_id,"
								+ " WO.workorders_location_ID, WO.indentno, WO.totalsubworktask_cost, WO.totalworktax_cost, WO.initial_note, WO.ISSUES_ID,"
								+ " WO.created, WO.lastupdated, U2.email, U3.email " + " from WorkOrders WO "
								+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ id + "" + " LEFT JOIN User U ON U.id = WO.assigneeId"
								+ " LEFT JOIN User U2 ON U2.id = WO.createdById"
								+ " LEFT JOIN User U3 ON U3.id = WO.lastModifiedById"
								+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id"
								+ " LEFT JOIN PartLocations AS B ON B.partlocation_id = WO.workorders_location_ID"
								+ " where WO.workorders_Number=" + WorkOrders + " AND WO.companyId = " + companyId
								+ " AND WO.markForDelete = 0");

			}

			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			WorkOrdersDto list;
			if (result != null) {
				list = new WorkOrdersDto();

				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				list.setVehicle_vid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Odometer((Integer) result[4]);
				list.setStart_dateOn((Date) result[5]);
				list.setDue_dateOn((Date) result[6]);
				list.setWorkorders_drivername((String) result[7]);
				list.setAssignee((String) result[8]);
				list.setWorkorders_location((String) result[9]);
				list.setPriorityId((short) result[10]);
				list.setPriority(WorkOrdersType.getPriorityName((short) result[10]));
				list.setCompleted_dateOn((Date) result[11]);
				list.setTotalworkorder_cost((Double) result[12]);
				list.setWorkorders_statusId((short) result[13]);
				list.setWorkorders_status(WorkOrdersType.getStatusName((short) result[13]));
				list.setWorkorders_document((boolean) result[14]);
				list.setWorkorders_document_id((Long) result[15]);
				list.setWorkorders_location_ID((Integer) result[16]);
				list.setIndentno((String) result[17]);
				list.setTotalsubworktask_cost((Double) result[18]);
				list.setTotalworktax_cost((Double) result[19]);
				list.setInitial_note((String) result[20]);
				list.setISSUES_ID((long) result[21]);
				list.setCreatedOn((Date) result[22]);
				list.setLastupdatedOn((Date) result[23]);
				list.setCreatedBy((String) result[24]);
				list.setLastModifiedBy((String) result[25]);

			} else {
				return null;
			}

			return list;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public int getWorkOrderCountByNumber(long WorkOrders, Integer companyId) throws Exception {

		return WorkOrdersDao.getWorkOrderCountByNumber(WorkOrders, companyId);
	}

	@Override
	public void transferWorkOrderDocument(List<WorkOrdersDocument> list) throws Exception {
		org.fleetopgroup.persistence.document.WorkOrdersDocument			workOrdersDocument		= null;
		List<org.fleetopgroup.persistence.document.WorkOrdersDocument> 		workOrdersDocumentList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				workOrdersDocumentList	= new ArrayList<>();
				for(WorkOrdersDocument	document : list) {
					workOrdersDocument	= new org.fleetopgroup.persistence.document.WorkOrdersDocument();
					workOrdersDocument.set_id(document.getWorkorder_documentid());
					workOrdersDocument.setWorkorder_id(document.getWorkorder_id());
					workOrdersDocument.setWorkorder_content(document.getWorkorder_content());
					workOrdersDocument.setWorkorder_contentType(document.getWorkorder_contentType());
					workOrdersDocument.setWorkorder_filename(document.getWorkorder_filename());
					workOrdersDocument.setCompanyId(document.getCompanyId());
					workOrdersDocument.setCreated(document.getCreated());
					workOrdersDocument.setLastupdated(document.getLastupdated());
					workOrdersDocument.setMarkForDelete(document.isMarkForDelete());

					workOrdersDocumentList.add(workOrdersDocument);
				}
				System.err.println("Saving WorkOrdersDocument ....");
				mongoTemplate.insert(workOrdersDocumentList, org.fleetopgroup.persistence.document.WorkOrdersDocument.class);
				System.err.println("Saved Successfully....");
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}

	@Override
	public long getWorkOrderDocumentMaxId() throws Exception {
		try {
			return documentRepository.getWorkOrderDocumentMaxId();
		} catch (Exception e) {
			return 0;
		}

	}

	@Override
	public ValueObject getOldPartReceived(ValueObject valueObject) throws Exception {
		WorkOrdersTasksToPartsDto		workOrdersTasksToPartsDto		= null;
		String							dateRange						= null;
		CustomUserDetails				userDetails						= null;
		String							query							= "";
		List<WorkOrdersTasksToPartsDto> workOrderPartList				= null;
		try {

			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			workOrdersTasksToPartsDto	= new WorkOrdersTasksToPartsDto();
			dateRange	= valueObject.getString("dateRange");
			String[] From_TO_DateRange = null;

			From_TO_DateRange = dateRange.split(" to ");

			workOrdersTasksToPartsDto.setFromDate(DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			workOrdersTasksToPartsDto.setToDate(DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			workOrdersTasksToPartsDto.setCompanyId(userDetails.getCompany_id());
			workOrdersTasksToPartsDto.setLocationId(valueObject.getInt("workOrderLocation", 0));
			workOrdersTasksToPartsDto.setVehicleGroupId(valueObject.getLong("vehicleGroupId", 0));

			if (workOrdersTasksToPartsDto.getVehicleGroupId() > 0) {
				query += " AND V.vehicleGroupId = "+workOrdersTasksToPartsDto.getVehicleGroupId()+" ";
			}
			if(workOrdersTasksToPartsDto.getLocationId() > 0) {
				query += " AND WTP.locationId = "+workOrdersTasksToPartsDto.getLocationId()+" ";
			}

			workOrderPartList	= workOrdersDao.getOldPartReceivedByVehicleGroup(workOrdersTasksToPartsDto, query);

			valueObject.clear();
			valueObject.put("SearchDate", dateRange.replace(" ", "_"));
			valueObject.put("workOrderPartList", workOrderPartList);
			valueObject.put("company",
					userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			workOrdersTasksToPartsDto		= null;
			dateRange						= null;
			userDetails						= null;
			query							= "";
			workOrderPartList				= null;
		}
	}

	@Override
	public ValueObject getOldPartNotReceived(ValueObject valueObject) throws Exception {
		WorkOrdersTasksToPartsDto		workOrdersTasksToPartsDto		= null;
		String							dateRange						= null;
		CustomUserDetails				userDetails						= null;
		String							query							= "";
		List<WorkOrdersTasksToPartsDto> workOrderPartList				= null;
		try {

			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			workOrdersTasksToPartsDto	= new WorkOrdersTasksToPartsDto();
			dateRange	= valueObject.getString("dateRange");
			String[] From_TO_DateRange = null;

			From_TO_DateRange = dateRange.split(" to ");

			workOrdersTasksToPartsDto.setFromDate(DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			workOrdersTasksToPartsDto.setToDate(DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
			workOrdersTasksToPartsDto.setCompanyId(userDetails.getCompany_id());
			workOrdersTasksToPartsDto.setLocationId(valueObject.getInt("workOrderLocation", 0));
			workOrdersTasksToPartsDto.setVehicleGroupId(valueObject.getLong("vehicleGroupId", 0));

			if (workOrdersTasksToPartsDto.getVehicleGroupId() > 0) {
				query += " AND V.vehicleGroupId = "+workOrdersTasksToPartsDto.getVehicleGroupId()+" ";
			}
			if(workOrdersTasksToPartsDto.getLocationId() > 0) {
				query += " AND WTP.locationId = "+workOrdersTasksToPartsDto.getLocationId()+" ";
			}

			workOrderPartList	= workOrdersDao.getOldPartNotReceived(workOrdersTasksToPartsDto, query);

			valueObject.clear();
			valueObject.put("SearchDate", dateRange.replace(" ", "_"));
			valueObject.put("workOrderPartList", workOrderPartList);
			valueObject.put("company",
					userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			workOrdersTasksToPartsDto		= null;
			dateRange						= null;
			userDetails						= null;
			query							= "";
			workOrderPartList				= null;
		}
	}

	@Override
	public WorkOrdersDto getWorkOrdersWithVehicleDetails(long workorders_id, Integer company_id) throws Exception {

		Query 			query 			= null;
		Object[] 		result 			= null;
		WorkOrdersDto 	list 			= null;

		try {
			query = entityManager.createQuery(
					"SELECT WO.workorders_id, WO.workorders_Number, WO.start_date, WO.completed_date, WO.totalworkorder_cost, WO.workorders_statusId,"
							+ " WO.vehicle_vid, V.vehicle_registration, V.vehicle_chasis, V.vehicle_engine, WO.vehicle_Odometer,"
							+ " WO.workorders_driver_id, D.driver_firstname, WO.workorders_driver_number, WO.workorders_route, WO.due_date, WO.workorders_diesel,"
							+ " AU.firstName, AU.lastName, CU.firstName, CU.lastName, WO.initial_note"
							+ " from WorkOrders WO " 
							+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid"
							+ " LEFT JOIN Driver D ON D.driver_id = WO.workorders_driver_id" 
							+ " INNER JOIN User AS AU ON AU.id = WO.assigneeId"
							+ " INNER JOIN User AS CU ON CU.id = WO.createdById"
							+ " where WO.workorders_id=" + workorders_id + " AND WO.companyId = " + company_id + " AND WO.markForDelete = 0");

			result = (Object[]) query.getSingleResult();

			if (result != null) {
				list = new WorkOrdersDto();

				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				list.setStart_dateOn((Date) result[2]);
				list.setCompleted_dateOn((Date) result[3]);
				list.setTotalworkorder_cost((Double) result[4]);
				list.setWorkorders_statusId((short) result[5]);
				list.setVehicle_vid((Integer) result[6]);
				list.setVehicle_registration((String) result[7]);
				list.setVehicle_chasis((String) result[8]);
				list.setVehicle_engine((String) result[9]);
				list.setVehicle_Odometer((Integer) result[10]);
				list.setWorkorders_driver_id((Integer) result[11]);
				list.setWorkorders_drivername((String) result[12]);
				list.setWorkorders_driver_number((String) result[13]);
				list.setWorkorders_route((String) result[14]);
				list.setDue_dateOn((Date) result[15]);
				list.setWorkorders_diesel((Long) result[16]);
				if(result[18] != null && result[18] != "") {
					list.setAssignee((String) result[17] + " "+ (String) result[18]);										
				} else {
					list.setAssignee((String) result[17]);					
				}

				if(result[20] != null && result[20] != "") {
					list.setCreatedBy((String) result[19] + " "+ (String) result[20]);										
				} else {
					list.setCreatedBy((String) result[19]);					
				}
				list.setInitial_note((String) result[21]);
			} 

			return list;	
		} catch (Exception e) {
			throw e;
		} finally {
			query 			= null;
			result 			= null;
			list 			= null;
		}
	}

	@Transactional
	public List<WorkOrdersTasksDto> getWorkOrderTasksWithMechanic(long workorders_id, Integer company_id) throws Exception {

		TypedQuery<Object[]> 		query	 	= null;
		List<WorkOrdersTasksDto> 	Dtos 		= null;
		List<Object[]> 				results 	= null;
		WorkOrdersTasksDto 			list 		= null;

		try {
			query = entityManager.createQuery(
					"SELECT WO.workordertaskid, JT.Job_Type, WO.job_typetaskId, WO.job_subtypetask_id, JST.Job_ROT, JST.Job_ROT_number,"
							+ " WO.totalpart_cost, WO.totallaber_cost, WO.totaltask_cost,D.driver_firstname, D.driver_Lastname, WO.jobTypeRemark "
							+ " from WorkOrdersTasks WO "
							+ " LEFT JOIN JobType JT ON JT.Job_id = WO.job_typetaskId "
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = WO.job_subtypetask_id "
							+ " LEFT JOIN WorkOrdersTasksToLabor WOTL ON WOTL.workordertaskid = WO.workordertaskid "
							+ " LEFT JOIN Driver D ON D.driver_id = WOTL.laberid "
							+ " where WO.workorders = " + workorders_id + " AND WO.companyId = " + company_id
							+ " AND WO.markForDelete = 0 ",
							Object[].class);

			results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<WorkOrdersTasksDto>();
				for (Object[] result : results) {
					list = new WorkOrdersTasksDto();

					list.setWorkordertaskid((Long) result[0]);
					list.setJob_typetask((String) result[1]);
					list.setJob_typetaskId((Integer) result[2]);
					list.setJob_subtypetask_id((Integer) result[3]);
					list.setJob_subtypetask((String) result[4]);
					list.setTotalpart_cost((Double) result[6]);
					list.setTotallaber_cost((Double) result[7]);
					list.setTotaltask_cost((Double) result[8]);
					if((String) result[9] != null && (String) result[10] != null) {
						list.setMechanic((String) result[9] + " " + (String) result[10]);
					} else if ((String) result[9] != null) {
						list.setMechanic((String) result[9]);						
					}
					list.setJobTypeRemark((String) result[11]);
					list.setWorkorders_id(workorders_id);
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		} finally {
			query	 	= null;
			Dtos 		= null;
			results 	= null;
			list 		= null;
		}
	}

	@Transactional
	public ValueObject updateTaskRemarkById(ValueObject valueObject) throws Exception {
		ValueObject					valOutObject				= null;
		Long						workordertaskid				= null;
		String						taskRemark					= null;
		CustomUserDetails			userDetails					= null;
		try {
			userDetails         = Utility.getObject(valueObject);
			workordertaskid		= valueObject.getLong("workordertaskid",0);
			taskRemark			= valueObject.getString("remark");

			if(workordertaskid != 0 && taskRemark != null) {
				WorkOrdersTasksRepository.updateTaskRemarkById(taskRemark, workordertaskid, userDetails.getCompany_id());
				valOutObject	= new ValueObject();
				valOutObject.put("taskRemark", taskRemark);
			}
			return valOutObject;
		} catch (Exception e) {
			throw e;
		}
	}


	@Transactional
	public List<WorkOrdersTasksToPartsDto> getWorkOrdersTasksTo_Most_Parts_Count_Report	(Long GroupID,String dateRangeFrom, String dateRangeTo, Integer company_id, Integer LocationId) throws Exception {

		TypedQuery<Object[]> 		query	 	= null;
		ArrayList<WorkOrdersTasksToPartsDto> 	Dtos 		= null;
		List<Object[]> 				results 	= null;
		WorkOrdersTasksToPartsDto 			list 		= null;

		try {
			if(LocationId > 0) {

				query = entityManager.createQuery(
						"select w.partid,w.partid,mp.partname,mp.partnumber,w.parteachcost,v.vehicleGroupId, vg.vGroup, w.locationId, w.quantity "
								+ "from WorkOrdersTasksToParts w "
								+ "INNER JOIN WorkOrders wo on wo.workorders_id = w.workorders_id "
								+ "INNER JOIN MasterParts mp on mp.partid = w.partid "
								+ "INNER JOIN Vehicle v on v.vid = wo.vehicle_vid "
								+ "left JOIN VehicleGroup vg on vg.gid = v.vehicleGroupId "
								+ "where w.companyId = '"+company_id+"' and w.locationId ='"+LocationId +"' and wo.completed_date between '"+dateRangeFrom+"' and '"+dateRangeTo+"' and v.vehicleGroupId = '"+GroupID+"' group by w.partid ",
								Object[].class);
			} else {
				query = entityManager.createQuery(
						"select w.partid,w.partid,mp.partname,mp.partnumber,w.parteachcost,v.vehicleGroupId, vg.vGroup, w.locationId, w.quantity "
								+ "from WorkOrdersTasksToParts w "
								+ "INNER JOIN WorkOrders wo on wo.workorders_id = w.workorders_id "
								+ "INNER JOIN MasterParts mp on mp.partid = w.partid "
								+ "INNER JOIN Vehicle v on v.vid = wo.vehicle_vid "
								+ "left JOIN VehicleGroup vg on vg.gid = v.vehicleGroupId "
								+ "where w.companyId = '"+company_id+"' and wo.completed_date between '"+dateRangeFrom+"' and '"+dateRangeTo+"' and v.vehicleGroupId = '"+GroupID+"' group by w.partid ",
								Object[].class);

			}

			results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<WorkOrdersTasksToPartsDto>();

				for (Object[] result : results) {
					list = new WorkOrdersTasksToPartsDto();

					list.setPartCount((Long) result[0]);
					list.setPartid((Long) result[1]);
					list.setPartname((String)result[2]);
					list.setPartnumber((String) result[3]);
					list.setTotalValuePartConsumed((double)result[4]);
					list.setVehicleGroupId((long)result[5]);
					list.setVehicleGroup((String) result[6]);
					list.setLocationId((Integer)result[7]);
					if(result[8] != null)
					list.setQuantity(Double.parseDouble(toFixedTwo.format(result[8])));
					if(list.getTotalValuePartConsumed() > 0 && list.getQuantity() != null) {
						list.setTotalValuePartConsumed(list.getTotalValuePartConsumed() * list.getQuantity());
					}
					
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		} finally {
			query	 	= null;
			Dtos 		= null;
			results 	= null;
			list 		= null;
		}
	}
	
	@Override
	public List<WorkOrdersDto> getWorkOrdereListOfMonthByVid(TripSheetIncomeDto incomeDto) throws Exception {
		try {

			TypedQuery<Object[]> querySearch = entityManager.createQuery(
					"SELECT SR.workorders_id, SR.workorders_Number, SR.vehicle_vid, SR.totalworkorder_cost, SR.completed_date "
							+ " from WorkOrders SR "
							+ " where  SR.vehicle_vid = "+incomeDto.getVid()+" AND SR.completed_date between '"+incomeDto.getFromDate()+"' AND '"+incomeDto.getToDate()+"' "
							+ " AND SR.companyId = "+incomeDto.getCompanyId()+" AND SR.workorders_statusId = "+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+" AND SR.markForDelete = 0",
					Object[].class);

			List<Object[]> results = querySearch.getResultList();

			List<WorkOrdersDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<WorkOrdersDto>();
				WorkOrdersDto list = null;
				for (Object[] result : results) {
					list = new WorkOrdersDto();

					list.setWorkorders_id((Long) result[0]);
					list.setWorkorders_Number((Long) result[1]);
					list.setVehicle_vid((Integer) result[2]);
					list.setTotalworkorder_cost((Double) result[3]);
					list.setCompleted_date(sqldateTime.format(result[4]));
					
					Dtos.add(list);
				}
			}
			return Dtos;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	public HashMap<Long ,ServiceReminderDto> getJobtypeAndSubtypeFromServiceReminderId (String serviceIds, Integer company_id) throws Exception {
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

			HashMap<Long, ServiceReminderDto> dtosMap = null;
			if (results != null && !results.isEmpty()) {
				
				dtosMap = new HashMap<Long ,ServiceReminderDto>();
				ServiceReminderDto list = null;
				for (Object[] result : results) {
					list = new ServiceReminderDto();
					
					list.setService_id((long) result[0]);
					list.setService_type((String) result[1]);
					list.setService_subtype((String) result[2]);
					list.setServiceTypeId((Integer) result[3]);
					list.setServiceSubTypeId((Integer) result[4]);
					
					dtosMap.put(list.getService_id(),list);
				}
			}

			return	dtosMap;
		
		} catch (Exception e) {
			LOGGER.error("Eror"+e);
			throw e;
		}
		
	}
	
	@Override
	public HashMap<Integer ,ServiceReminderDto> getJobtypeAndSubtypeFromServiceId (String serviceIds, Integer company_id) throws Exception {
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
			LOGGER.error("Eror"+e);
			throw e;
		}
		
	}


	
		@Override
		public HashMap<Integer, Long> getALLEmailWorkOrderDailyWorkStatus(String startDate, String endDate)
				throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(WO.workorders_id), WO.companyId "
								+ " From WorkOrders as WO "
								+ " WHERE WO.start_date between '"+startDate+"' AND '"+endDate+"' AND WO.markForDelete = 0 GROUP BY WO.companyId ",
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
		
		//Email System for Manager Start Work Order
				@Override
				public List<CompanyDto> getALLEmailWorkOrderDailyWorkForManagers(String startDate, String endDate)
						throws Exception {
								
					try{
						TypedQuery<Object[]> typedQuery = null;

											
						typedQuery = entityManager.createQuery(
								"SELECT COUNT(W.workorders_id), C.company_id, C.company_name "
										+ " From WorkOrders as W "
										+ " INNER JOIN Company AS C on C.company_id = W.companyId  "
										+ " WHERE W.created between '"+startDate+"' AND '"+endDate+"'  AND W.markForDelete = 0 "
										+ " Group by W.companyId ",
										Object[].class);
						
						
						List<Object[]> results = typedQuery.getResultList();

						List<CompanyDto> workOrders = null;
						if (results != null && !results.isEmpty()) {
							
							workOrders = new ArrayList<CompanyDto>();
							CompanyDto list = null;
							for (Object[] result : results) {
								list = new CompanyDto();
							if(result !=null) {
								
								list.setTripSheetCount((long) 0);
								list.setRenewalReminderCount((long) 0);
								list.setServiceReminderCount((long) 0);
								list.setFuelEntriesCount((long) 0);								
								list.setServiceEntriesCount((long) 0);
								
								if(result[0] !=null) 
								list.setWorkOrderCount((long) result[0]);								
								list.setCompany_id((int) result[1]);
								list.setCompany_name((String) result[2]);
								}
							workOrders.add(list);
							}
						}
						return workOrders;
					}
					catch(Exception e){
						throw e;						
					}			
				}	
				//Email System for Manager Stop Work Order
				
		@Override
		public List<VehicleDto> getVehicleWiseWorkOrders(String query, int companyId) throws Exception {
						
			try{
				TypedQuery<Object[]> typedQuery = null;
									
				typedQuery = entityManager.createQuery(
						"SELECT WO.workorders_id, WO.totalworkorder_cost, WO.vehicle_vid, V.vehicle_registration, PL.partlocation_id, "
								+ " PL.partlocation_name, WO.workorders_Number, WO.start_date, WO.due_date, WO.completed_date "
								+ " From WorkOrders WO "
								+ " INNER JOIN Vehicle V on V.vid = WO.vehicle_vid "
								+ " INNER JOIN PartLocations PL on PL.partlocation_id = WO.workorders_location_ID "
								+ " WHERE WO.workorders_statusId = "+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+" AND "
								+ " WO.companyId = "+companyId+" AND WO.markForDelete = 0 "+query+" ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();

				List<VehicleDto> workOrders = null;
				if (results != null && !results.isEmpty()) {
					
					workOrders = new ArrayList<VehicleDto>();
					VehicleDto list = null;
					for (Object[] result : results) {
						list = new VehicleDto();
						
						list.setWorkorders_id((long) result[0]);
						list.setTotalworkorder_cost((double) result[1]);
						list.setVid((int) result[2]);
						list.setVehicle_registration((String) result[3]);
						list.setPartlocation_id((int) result[4]);
						list.setPartlocation_name((String) result[5]);
						//list.setVehicleWorkOrdersCount((int) 1);
						list.setServiceEntries_id((long) 0);
						list.setTotalserviceROUND_cost((double) 0.0);
						
						list.setWorkOrder_Number((long) result[6]);
						list.setWoStartDate(sqldateTime.format((Date) result[7]));
						list.setWoDueDate(sqldateTime.format((Date) result[8]));
						list.setWocompletedDate(sqldateTime.format((Date) result[9]));
						
						workOrders.add(list);
					}
				}
				return workOrders;
			}
			catch(Exception e){
				throw e;						
			}			
		}		
		
		@Override
		public Long getALLOpenWorkOrderCount(Integer companyId) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
					"SELECT COUNT(WO.workorders_id) "
							+ " From WorkOrders as WO "
							+ " INNER JOIN Vehicle AS V ON V.vid = WO.vehicle_vid "
							+ " WHERE WO.workorders_statusId NOT IN("+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+") "
							+ " AND WO.markForDelete = 0 AND V.vStatusId <> 4  AND WO.companyId = "+companyId+" ",
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
		public HashMap<Integer, Long> getALLOpenWorkOrder() throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(WO.workorders_id), WO.companyId "
								+ " From WorkOrders as WO "
								+ " INNER JOIN Vehicle AS V ON V.vid = WO.vehicle_vid "
								+ " WHERE WO.workorders_statusId NOT IN("+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+") "
								+ " AND WO.markForDelete = 0 AND V.vStatusId <> 4  GROUP BY WO.companyId ",
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
		public Long getALLOpenWorkOrderBetweenDates(String startDate, String endDate, Integer companyId) throws Exception {
			
			Query queryt = 	null;
			
			queryt = entityManager.createQuery(
					"SELECT COUNT(WO.workorders_id) "
							+ " From WorkOrders as WO "
							+ " WHERE WO.start_date between '"+startDate+"' AND '"+endDate+"' AND "
							+ " WO.workorders_statusId NOT IN("+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+") "
							+ " AND WO.markForDelete = 0 AND WO.companyId = "+companyId+" ",
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
		public HashMap<Integer, Long> getALLOpenWorkOrderFromDateRange(String startDate, String endDate) throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(WO.workorders_id), WO.companyId "
								+ " From WorkOrders as WO "
								+ " WHERE WO.created between '"+startDate+"' AND '"+endDate+"' AND "
								+ " WO.workorders_statusId NOT IN("+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+") AND WO.markForDelete = 0 GROUP BY WO.companyId ",
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
		public HashMap<Integer, Long> getALLOnHoldWorkOrder() throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(WO.workorders_id), WO.companyId "
								+ " From WorkOrders as WO "
								+ " WHERE WO.workorders_statusId = "+WorkOrdersType.WORKORDERS_STATUS_ONHOLD+" AND WO.markForDelete = 0 GROUP BY WO.companyId ",
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
		public Long getTotalWorkOrderCompleteCount(String startDate, String endDate, Integer companyId) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
					"SELECT COUNT(WO.workorders_id) "
							+ " From WorkOrders as WO "
							+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid "
							+ " WHERE WO.completed_date between '"+startDate+"' AND '"+endDate+"' "
							+ " AND WO.markForDelete = 0 AND V.vStatusId <> 4 AND WO.companyId = "+companyId+" ",
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
		public HashMap<Integer, Long> getALLCOMPLETEDWorkOrder(String startDate, String endDate) throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(WO.workorders_id), WO.companyId "
								+ " From WorkOrders as WO "
								+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid "
								+ " WHERE WO.completed_date between '"+startDate+"' AND '"+endDate+"' "
								+ " AND WO.markForDelete = 0 AND V.vStatusId <> 4 GROUP BY WO.companyId ",
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
		public Long getALLOpenWorkOrderByDate(String startDate, Integer companyId) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
					"SELECT COUNT(WO.workorders_id) "
							+ " From WorkOrders as WO "
							+ " WHERE WO.start_date <= '"+startDate+"' AND "
							+ " WO.workorders_statusId NOT IN("+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+") "
							+ " AND WO.markForDelete = 0 AND WO.companyId = "+companyId+" ",
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
		public HashMap<Integer, Long> getALLOpenWorkOrderFrom15Days(String startDate) throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(WO.workorders_id), WO.companyId "
								+ " From WorkOrders as WO "
								+ " WHERE WO.created <= '"+startDate+"' AND "
								+ " WO.workorders_statusId NOT IN("+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+") AND WO.markForDelete = 0 GROUP BY WO.companyId ",
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
		
		public Map<Integer, List<WorkOrdersTasksToPartsDto>> getAllPartsInWorkorders(String startDate, String endDate) throws Exception {
			TypedQuery<Object[]> 									typedQuery  	= null;
			Map<Integer, List<WorkOrdersTasksToPartsDto>>			companyHM		= null;
			List<WorkOrdersTasksToPartsDto>							companyLIst 	= null;
			try {
				typedQuery = entityManager.createQuery(
						"SELECT WT.workorders_id, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer, WO.created, WT.partid, M.partname , "
								+ " WT.companyId, WT.last_occurred_date, WT.last_occ_odometer,C.company_name,WO.workorders_Number,WT.quantity,IV.all_quantity"
								+ " From WorkOrdersTasksToParts as WT "
								+ " INNER JOIN WorkOrders as WO on WO.workorders_id = WT.workorders_id "
								+ " INNER JOIN MasterParts as M	on M.partid = WT.partid "
								+ " INNER JOIN Company as C	on C.company_id = WT.companyId "
								+ " INNER JOIN Vehicle as V on V.vid = WO.vehicle_vid " 
								+ " INNER JOIN InventoryAll as IV on IV.partid = WT.partid AND IV.companyId = WT.companyId AND IV.markForDelete = 0 "
								+ " WHERE WO.created BETWEEN '"+startDate+"' AND '"+endDate+"' "
								+ " AND WT.markForDelete = 0 ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();
				if (results != null && !results.isEmpty()) {
					
					companyHM	= new HashMap<Integer, List<WorkOrdersTasksToPartsDto>>();
					
					WorkOrdersTasksToPartsDto	ordersTasksDto = null;
					for (Object[] result : results) {
						ordersTasksDto	= new WorkOrdersTasksToPartsDto();
						ordersTasksDto.setWorkorders_id((Long) result[0]);
						ordersTasksDto.setVehicle_vid((Integer) result[1]);
						ordersTasksDto.setVehicle_registration((String) result[2]);
						ordersTasksDto.setVehicle_Odometer((Integer) result[3]);
						ordersTasksDto.setCreatedDate(sqldateTime.format((Timestamp)result[4]));
						ordersTasksDto.setPartid((Long) result[5]);
						if(result[6].toString().length() >= 20) {
							String partName= result[6].toString().substring(0,18);
							ordersTasksDto.setPartname(partName.concat(".."));
							ordersTasksDto.setPartNameOnHover((String) result[6]);
						}else {
							ordersTasksDto.setPartname((String) result[6]);
						}
						ordersTasksDto.setCompanyId((Integer) result[7]);
						if (result[8] != null) {
						ordersTasksDto.setLast_occurred_dateStr(sqldateTime.format((Timestamp)result[8]));
						}else {
							ordersTasksDto.setLast_occurred_dateStr("");
						}if(result[9] != null) {
							ordersTasksDto.setLast_occurred_odometer((Integer) result[9]);
						}else {
							ordersTasksDto.setLast_occurred_odometer(0);
						}
						ordersTasksDto.setCompanyName((String) result[10]);
						
						
						ordersTasksDto.setWorkOrderNumber((Long) result[11]);
						if(result[12] != null) {
						ordersTasksDto.setQuantity((Double) result[12]);
						}
						if(result[13] != null) {
							ordersTasksDto.setStockQuantity((Double) result[13]);
						}
						companyLIst	= companyHM.get(ordersTasksDto.getCompanyId());
						if(companyLIst == null) {
							companyLIst	= new ArrayList<>();
						}
						companyLIst.add(ordersTasksDto);
						
						companyHM.put(ordersTasksDto.getCompanyId(), companyLIst);
					
					}
					
				}
				return companyHM;
			} catch (Exception e) {
				throw e;
			}
		}
		
		

		@SuppressWarnings("unchecked")
		@Override
		public ValueObject configureDailyPartConsumedEmailBody(ValueObject valueInObject) throws Exception {
		//	Map<Long,List<WorkOrdersTasksToPartsDto>>					finalMapOfPartConsumption				= null;
			List<WorkOrdersTasksToPartsDto>								partConsumptionList 					= null;
			List<InventoryTyreHistoryDto>								tyreAssignmentList						= null;
			List<BatteryHistoryDto>										batteryAssignmentList					= null;
			List<UreaEntriesDto>										ureaConsumptionList						= null;
			Map<Integer,Map<Long,VehicleClothInventoryHistoryDto>>		companyWiseMapOfUpholsteryAssignment	= null;
			Map<Long,VehicleClothInventoryHistoryDto>					finalMapOfupholsteryAssignment			= null;
			VehicleClothInventoryHistoryDto								vehicleClothInventoryHistoryDto			= null;
			
			ValueObject					       	 						valueOutObject 							= null;
			String 														partConsumptionHeader 					= "";
			String 														partConsumptionBody 					= "";
			String 														tyreAssignmentHeader 					= "";
			String 														tyreAssignmentBody 						= "";
			String 														batteryAssignmentHeader 				= "";
			String 														batteryAssignmentBody 					= "";
			String 														ureaConsumptionHeader 					= "";
			String 														ureaConsumptionBody 					= "";
			String 														upholsteryAssignmentHeader				= "";
			String 														upholsteryAssignmentBody 				= "";
			String														emailDate								= "";
			String 														companyName								= "";
			try {
		//		finalMapOfPartConsumption  				= (Map<Long, List<WorkOrdersTasksToPartsDto>>) valueInObject.get("partWiseParConsumedHM");
				partConsumptionList  				= (List<WorkOrdersTasksToPartsDto>) valueInObject.get("companywiseParConsumedList");
				tyreAssignmentList  					= (List<InventoryTyreHistoryDto>) valueInObject.get("companywiseTyreAssignmentList");
				batteryAssignmentList				 	= (List<BatteryHistoryDto>) valueInObject.get("companywiseBatteryAssignmentList");
				ureaConsumptionList 					= (List<UreaEntriesDto>) valueInObject.get("companywiseUreaConsumptionList");
				companyWiseMapOfUpholsteryAssignment  	= (Map<Integer, Map<Long,VehicleClothInventoryHistoryDto>>) valueInObject.get("vehiclewiseUpholsteryAssignmentHM");
				emailDate								= valueInObject.getString("emailDate");
				companyName								= valueInObject.getString("companyName");
				/*if(finalMapOfPartConsumption != null && !finalMapOfPartConsumption.isEmpty()) {
					partConsumptionHeader	+= "<tr width=\"100%\" bgcolor=\"DodgerBlue\" >"
							+"<td align='center' colspan=\"7\"><div style=\"font-size:20px\"> PART CONSUMPTION"  
							+"</td>"
							+"</tr>";
					
					partConsumptionHeader +="<tr bgcolor=\"#00FFFF\">"
							+"<th align='center' ><div style=\"font-size:17px\"> VEHICLE </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> WO NO </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> QUANTITY </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> ODOMETER </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> HISTORY"
							+"<table width=100% >"
							+"<tr>"
							+"<th align='left' ><div style=\"font-size:17px\"> LAST OCC ODO </th>"
							+"<th align='right' ><div style=\"font-size:17px\"> LAST OCC ON </th>"
							+"</tr>"
							+"</table>"
							+"</th>"
							+"</tr>";
					
					for(Long key : finalMapOfPartConsumption.keySet()) {
						long workorderid = 0;
						partConsumptionList = finalMapOfPartConsumption.get(key);
						
						partConsumptionBody 	+= "<tr width=\"100%\" >"
								+"<td align='center'  colspan=\"7\"><div style=\"font-size:20px\" ><span style='color:#5B5BC8;' >"+ partConsumptionList.get(0).getPartname() +"</span> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; <span style='color:#5B5BC8;' > STOCK "+partConsumptionList.get(0).getStockQuantity()+" UNIT </span>"+ "</td>"
								+"</tr>";
						for(WorkOrdersTasksToPartsDto dto1 : partConsumptionList) {
							if(workorderid != dto1.getWorkorders_id()) {
								workorderid = dto1.getWorkorders_id();
								partConsumptionBody  +="<tr>"
										+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getVehicle_registration() +"</td>"
										+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getWorkOrderNumber() +"</td>"
										+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getQuantity() +"</td>"
										+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getVehicle_Odometer() +" Km</td>"
										+"<td align='center'><div style=\"font-size:18px\">"
										+"<table width=100% >"
										+"<tr>"
										+"<td align='left'><div style=\"font-size:18px\">"+ dto1.getLast_occurred_odometer() +" Km</td>"
										+"<td align='right'><div style=\"font-size:18px\">"+ dto1.getLast_occurred_dateStr() +"</td>"
										+"</tr>"
										+"</table>"
										+"</td>"
										+"</tr>";
								}
							}
						}
					}else {
						partConsumptionHeader+="";
						partConsumptionBody += "<tr width=\"100%\" bgcolor=\"#05354B\" >"
								+" <br> <td align='center' colspan=\"8\"><div style=\"font-size:20px; color:#ffffff \"><b>No PART CONSUMPTION On This Day</b>"  
								+"</td>"
								+"</tr>";
								}*/
				
				
				if(partConsumptionList != null && !partConsumptionList.isEmpty()) {
					
					partConsumptionHeader	+= "<tr width=\"100%\"  bgcolor=\"DodgerBlue\">"
							+"<td align='center' colspan=\"9\"><div style=\"font-size:20px\">  PART CONSUMPTION "  
							+"</td>"
							+"</tr>";
					
					partConsumptionHeader +="<tr width=\"100%\" bgcolor=\"#00FFFF\">"
							+"<th width=\"20%\" align='center' ><div style=\"font-size:17px\"> VEHICLE </th>"
							+"<th width=\"10%\" align='center' ><div style=\"font-size:17px\"> WO NO </th>"
							+"<th width=\"20%\" align='center' ><div style=\"font-size:17px\"> PART </th>"
							+"<th width=\"10%\" align='center' ><div style=\"font-size:17px\"> QUANTITY </th>"
							+"<th width=\"15%\" align='center' ><div style=\"font-size:17px\"> ODOMETER </th>"
							+"<th width=\"25%\" align='center' ><div style=\"font-size:17px\"> HISTORY"
							+"<table width=100% >"
							+"<tr>"
							+"<th align='left' ><div style=\"font-size:17px\"> LAST OCC ODO </th>"
							+"<th align='right' ><div style=\"font-size:17px\"> LAST OCC ON </th>"
							+"</tr>"
							+"</table>"
							+"</th>"
							+"</tr>";
					
					for(WorkOrdersTasksToPartsDto dto1 : partConsumptionList) {
						String hover="";
						
						if(dto1.getPartNameOnHover() != null) {
							 hover="<td width=\"20%\" align='center'><div  style=\"font-size:20px\"class=\"showMain\"><div class=\"ok\">"+ dto1.getPartname() 
							+"</div> <div class=\"showMe\"> "+dto1.getPartNameOnHover()+"</div>  </div> "
							+"</td>";
							}else {
							hover="<td width=\"20%\" align='center'><div style=\"font-size:20px\"  >"+ dto1.getPartname()+"</td>";
							}
						
						partConsumptionBody  +="<tr width=\"100%\">"
								+"<td width=\"20%\" align='center'><div style=\"font-size:18px\">"+ dto1.getVehicle_registration() +"</td>"
								+"<td width=\"10%\" align='center'><div style=\"font-size:18px\">"+ dto1.getWorkOrderNumber() +"</td>"
								/*if(dto1.getPartNameOnHover() != null) {
								+"<td width=\"20%\" align='center'><div style=\"font-size:20px\" class=\"m_-4059356677258949735tooltip\" >"+ dto1.getPartname() 
								+"<span class=\"m_-4059356677258949735tooltiptext\"> "+dto1.getPartNameOnHover()+"</span> </div> "
								+"</td>"
								}*/
								+" "+hover+" "
								+"<td width=\"10%\" align='center'><div style=\"font-size:18px\">"+ dto1.getQuantity() +"</td>"
								+"<td width=\"15%\" align='center'><div style=\"font-size:18px\">"+ dto1.getVehicle_Odometer() +" Km</td>"
								+"<td width=\"25%\" align='center'><div style=\"font-size:18px\">"
								+"<table width=100% >"
								+"<tr>"
								+"<td align='left'><div style=\"font-size:18px\">"+ dto1.getLast_occurred_odometer() +" Km</td>"
								+"<td align='right'><div style=\"font-size:18px\">"+ dto1.getLast_occurred_dateStr() +"</td>"
								+"</tr>"
								+"</table>"
								+"</td>"
								+"</tr>";
					
					}
				}else {
					partConsumptionHeader+="";
					partConsumptionBody += "<tr width=\"100%\" bgcolor=\"#05354B\" >"
							+" <br> <td align='center' colspan=\"8\"><div style=\"font-size:20px; color:#ffffff \"><b>No PART CONSUMPTION On This Day</b>"  
							+"</td>"
							+"</tr>";
				}
				
				
				if(tyreAssignmentList != null && !tyreAssignmentList.isEmpty()) {
					
					tyreAssignmentHeader	+= "<tr width=\"100%\"  bgcolor=\"DodgerBlue\">"
							+"<td align='center' colspan=\"9\"><div style=\"font-size:20px\"> TYRE MOUNT/DISMOUNT"  
							+"</td>"
							+"</tr>";
					
					tyreAssignmentHeader +="<tr bgcolor=\"#00FFFF\">"
							+"<th align='center' ><div style=\"font-size:17px\"> VEHICLE </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> TYRE NO </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> ODOMETER </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> MODEL </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> POSITION </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Status </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> PRE-ASSIGN ON </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> PRE-ODO </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> PRE-TYRE NO </th>"
							+"</tr>";
					
					for(InventoryTyreHistoryDto dto1 : tyreAssignmentList) {
						tyreAssignmentBody  +="<tr>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getVEHICLE_REGISTRATION() +"</td>"
									+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getTYRE_NUMBER() +"</td>"
									+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getOPEN_ODOMETER() +"</td>"
									+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getTyreModel() +"</td>"
									+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getPOSITION() +"-"+dto1.getAXLE()+"</td>"
									+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getTYRE_STATUS()+"</td>"
									+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getPRE_TYRE_ASSIGN_DATEStr()+"</td>"
									+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getPRE_OPEN_ODOMETER()+"</td>"
									+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getPRE_TYRE_NUMBER()+"</td>"
									+"</tr>";
							}
				}else {
					tyreAssignmentHeader+="";
					tyreAssignmentBody += "<tr width=\"100%\" bgcolor=\"#05354B\" >"
							+" <br> <td align='center' colspan=\"8\"><div style=\"font-size:20px; color:#ffffff \"><b>No TYRE MOUNT/DISMOUNT On This Day</b>"  
							+"</td>"
							+"</tr>";
							}
				
				if(batteryAssignmentList != null && !batteryAssignmentList.isEmpty()) {
					
					batteryAssignmentHeader	+= "<tr width=\"100%\"  bgcolor=\"DodgerBlue\">"
							+"<td align='center' colspan=\"8\"><div style=\"font-size:20px\"> BATTERY MOUNT/DISMOUNT"  
							+"</td>"
							+"</tr>";
					
					batteryAssignmentHeader +="<tr bgcolor=\"#00FFFF\">"
							+"<th align='center' ><div style=\"font-size:17px\"> VEHICLE </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> BATTERY NO </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> MODEL </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> POSITION </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> Status </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> USED DAYS </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> PRE ASSIGN DATE </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> PRE BATTER NO </th>"
							+"</tr>";
					
					for(BatteryHistoryDto dto1 : batteryAssignmentList) {
						
						batteryAssignmentBody  +="<tr>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getVehicle_registration() +"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getBatterySerialNumber() +"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getBatteryManufacturerName() +"</td>"
								+"<td align='center'><div style=\"font-size:18px\">BATTERY-"+ dto1.getLayoutPosition() +"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getBatteryStatus()+"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getUsesNoOfDay()+"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getPreBatteryAsignDateStr()+"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getPreBatterySerialNumber()+"</td>"
								+"</tr>";
						
					}
				}else {
					batteryAssignmentHeader+="";
					batteryAssignmentBody += "<tr width=\"100%\" bgcolor=\"#05354B\" >"
							+" <br> <td align='center' colspan=\"8\"><div style=\"font-size:20px; color:#ffffff \"><b>No BATTERY MOUNT/DISMOUNT On This Day</b>"  
							+"</td>"
							+"</tr>";
							}
				
				
				if(ureaConsumptionList != null && !ureaConsumptionList.isEmpty()) {
					
					ureaConsumptionHeader	+= "<tr width=\"100%\"  bgcolor=\"DodgerBlue\">"
							+"<td align='center' colspan=\"7\"><div style=\"font-size:20px\"> UREA CONSUMPTION"  
							+"</td>"
							+"</tr>";
					
					ureaConsumptionHeader +="<tr bgcolor=\"#00FFFF\">"
							+"<th align='center' ><div style=\"font-size:17px\"> VEHICLE </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> UREA NO </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> QUANTITY IN LTRS</th>"
							+"<th align='center' ><div style=\"font-size:17px\"> ODOMETER </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> OLD ODOMETER </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> ODOMETER DIFFERENCE</th>"
							+"</tr>";
					
					for(UreaEntriesDto dto1 : ureaConsumptionList) {
						ureaConsumptionBody  +="<tr>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getVehicle_registration() +"</td>"
								+"<td align='center'><div style=\"font-size:18px\">UE-"+ dto1.getUreaEntriesNumber() +"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getUreaLiters() +"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getUreaOdometer() +"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ dto1.getUreaOdometerOld()+"</td>"
								+"<td align='center'><div style=\"font-size:18px\">"+ (dto1.getUreaOdometer() - dto1.getUreaOdometerOld())+"</td>"
								+"</tr>";
						
						}
				}else {
					ureaConsumptionHeader+="";
					ureaConsumptionBody += "<tr width=\"100%\" bgcolor=\"#05354B\" >"
							+" <br> <td align='center' colspan=\"8\"><div style=\"font-size:20px; color:#ffffff \"><b>No UREA CONSUMPTION On This Day</b>"  
							+"</td>"
							+"</tr>";
							}
				
				
				
				if(companyWiseMapOfUpholsteryAssignment != null && !companyWiseMapOfUpholsteryAssignment.isEmpty()) {
					upholsteryAssignmentHeader	+= "<tr width=\"100%\" bgcolor=\"DodgerBlue\" >"
							+"<td align='center' colspan=\"7\"><div style=\"font-size:20px\"> UPHOLSTERY ASSIGN/REMOVE"  
							+"</td>"
							+"</tr>";
					
					upholsteryAssignmentHeader +="<tr bgcolor=\"#00FFFF\">"
							+"<th align='center' ><div style=\"font-size:17px\"> UPHOLSTERY NAME </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> ASSIGN QUANTITY </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> REMOVED QUANTITY </th>"
							+"<th align='center' ><div style=\"font-size:17px\"> ALL ASSIGNED </th>"
							+"</tr>";
					
					for(Integer key : companyWiseMapOfUpholsteryAssignment.keySet()) {
						finalMapOfupholsteryAssignment = companyWiseMapOfUpholsteryAssignment.get(key);
						
						int count = 0 ;
						
						for(Long upholsteryId : finalMapOfupholsteryAssignment.keySet()) {
							vehicleClothInventoryHistoryDto = finalMapOfupholsteryAssignment.get(upholsteryId);
							if(count == 0) {
								upholsteryAssignmentBody 	+= "<tr width=\"100%\" >"
										+"<td align='center'  colspan=\"7\"><div style=\"font-size:20px\" >"+ vehicleClothInventoryHistoryDto.getVehicleRegistration() + "</td>"
										+"</tr>";
							}
							count++;
							upholsteryAssignmentBody  +="<tr>"
										+"<td align='center'><div style=\"font-size:18px\">"+ vehicleClothInventoryHistoryDto.getClothTypeName()+"</td>"
										+"<td align='center'><div style=\"font-size:18px\">"+ vehicleClothInventoryHistoryDto.getAsignedQuantity() +"</td>"
										+"<td align='center'><div style=\"font-size:18px\">"+ vehicleClothInventoryHistoryDto.getRemovedQuantity() +"</td>"
										+"<td align='center'><div style=\"font-size:18px\">"+ vehicleClothInventoryHistoryDto.getTotalAssignQuantity() +"</td>"
										+"</tr>";
								
							}
						}
					}else {
						upholsteryAssignmentHeader+="";
						upholsteryAssignmentBody += "<tr width=\"100%\" bgcolor=\"#05354B\" >"
								+" <br> <td align='center' colspan=\"8\"><div style=\"font-size:20px; color:#ffffff \"><b>No UPHOLSTERY ASSIGN/REMOVE On This Day</b>"  
								+"</td>"
								+"</tr>";
								}
				
				final String DailyPartCousumed_EmailBody = "<html>\r\n" + 
						"<head>\r\n" + 
						"<style> "
						+ ".showMe{ \r\n" + 
						"   display: none;\r\n" + 
						" }\r\n" + 
						" .showMain:hover .showMe{\r\n" + 
						"   display : block;\r\n" + 
						" }\r\n" + 
						" .showMain:hover .ok{\r\n" + 
						"   display : none;\r\n" + 
						" }"+
						"</style>  "+
						"</head>\r\n" +
						"\r\n" + 
						"<body>\r\n" +
						"<table bgcolor=\"black\" width=\"100%\">\r\n" + 
						"<tr>\r\n" + 
						"<td>\r\n" + 
						"<h2><font color=\"white\" ><b><center>Fleetop<img  src = \"cid:pluslogo\" alt=\"Plus\"> Daily Consumption </center></b></font></h2>\r\n" + 
						"</td>\r\n" + 
						"</tr>\r\n" + 
						"</table>\r\n" + 
						"<br>\r\n <font color='black'><h2>"+companyName+"</h2><h3>Daily Consumption Details for &nbsp; &nbsp; <span style='color:#500050;'>"+emailDate+" </span> </h3>  </font>" +	 	
						"<br>\r\n" + 
						"<table style=\"width:100%;border-collapse: collapse;\" border=1>\r\n" +
						"	<thead>"+partConsumptionHeader+
						"	</thead>"+
						"	<tbody>" + partConsumptionBody+
						"	</tbody>" + 								
						"</table>\r\n" + 
						"<br>\r\n" + 
						"\r\n" + 
						"</b><br>\r\n" + 
						"<table style=\"width:100%;border-collapse: collapse;\"  border=1>\r\n" +
						"	<thead>"+tyreAssignmentHeader+
						"	</thead>"+
						"	<tbody>" + tyreAssignmentBody+
						"	</tbody>" + 								
						"</table>\r\n" + 
						"<br>\r\n" + 
						"\r\n" + 
						"</b><br>\r\n" + 
						"<table style=\"width:100%;border-collapse: collapse;\" border=1>\r\n" +
						"	<thead>"+batteryAssignmentHeader+
						"	</thead>"+
						"	<tbody>" + batteryAssignmentBody+
						"	</tbody>" + 								
						"</table>\r\n" + 
						"<br>\r\n" + 
						"\r\n" + 
						"</b><br>\r\n" + 
						"<table style=\"width:100%;border-collapse: collapse;\"  border=1>\r\n" +
						"	<thead>"+ureaConsumptionHeader+
						"	</thead>"+
						"	<tbody>" + ureaConsumptionBody+
						"	</tbody>" + 								
						"</table>\r\n" + 
						"<br>\r\n" + 
						"\r\n" + 
						"</b><br>\r\n" + 
						"<table style=\"width:100%;border-collapse: collapse;\"   border=1>\r\n" +
						"	<thead>"+upholsteryAssignmentHeader+
						"	</thead>"+
						"	<tbody>" + upholsteryAssignmentBody+
						"	</tbody>" + 								
						"</table>\r\n" + 
						"<br>\r\n" + 
						"\r\n" + 
						"<table bgcolor=\"blue\" width=\"100%\">\r\n" + 
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
				valueOutObject.put("DailyPartCousumed_EmailBody", DailyPartCousumed_EmailBody);
				
				return valueOutObject;
				
			}catch (Exception e) {
				throw e;
			}finally {
				partConsumptionList 		= null;
			//	finalMapOfPartConsumption			= null;
				tyreAssignmentList			= null;
				batteryAssignmentList			= null;
				ureaConsumptionList			= null;
				valueOutObject 						= null;
				
			}
			
		}	
		//DashBoard
		@Override
		public long getWorkOrderCreatedCount(String startDate, String endDate, Integer companyId) throws Exception {
			long 					openCount	= 0;
			TypedQuery<Object[]> 	typedQuery 	= null;
			try {
				typedQuery = entityManager.createQuery(
							"SELECT COUNT(WO.workorders_id), WO.companyId "
								+ " From WorkOrders as WO "
								+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid "
								+ " WHERE WO.start_date between '"+startDate+"' AND '"+endDate+"'  "
								+ " AND WO.companyId ='"+companyId+"' AND WO.markForDelete = 0 AND V.vStatusId <> 4 ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();
				if (results != null && !results.isEmpty()) {
					for (Object[] result : results) {
						openCount = (Long)result[0];
					}
				}
				
				return openCount;
			
			} catch (Exception e) {
				LOGGER.error("getWorkOrderCreatedCount Expection "+e);
				throw e;
			}
		}
		
		@Override
		public long getWorkOrderCountByStatusID(String startDate, String endDate, Integer companyId,short status) throws Exception {
			TypedQuery<Object[]> 	typedQuery 	= null;
			long 					openCount	= 0;
			try {
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(WO.workorders_id), WO.companyId "
								+ " From WorkOrders as WO "
								+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid "
								+ " WHERE WO.start_date between '"+startDate+"' AND '"+endDate+"' "
								+ " AND WO.workorders_statusId="+status+" AND WO.companyId ='"+companyId+"' "
								+ " AND WO.markForDelete = 0 AND V.vStatusId <> 4 ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();
				
				if (results != null && !results.isEmpty()) {
					for (Object[] result : results) {
						openCount = (Long)result[0];
					}
				}
				return openCount;
				
			} catch (Exception e) {
				LOGGER.error("getWorkOrderCountByStatusID Expection "+e);
				throw e;
			}
		}
	
		public List<WorkOrdersDto> getLocationWiseWorkOrderCreatedCount(String startDate, String endDate, Integer companyID) throws Exception {
			TypedQuery<Object[]> 	typedQuery 	= null;
			WorkOrdersDto 			dto 		= null;
			try {
				typedQuery = entityManager.createQuery(
						"SELECT count(WO.workorders_id), WO.workorders_location_ID, PL.partlocation_name , WO.workorders_statusId "
								+ " From WorkOrders as WO "
								+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid "
								+ " INNER JOIN PartLocations AS PL ON PL.partlocation_id = WO.workorders_location_ID "
								+ " WHERE WO.start_date between '"+startDate+"' AND '"+endDate+"' "
								+ " AND WO.companyId = "+companyID+"AND WO.markForDelete = 0 AND V.vStatusId <> 4 "
								+ " AND WO.workorders_statusId NOT IN ("+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+") "
								+ " GROUP BY WO.workorders_location_ID ",
								Object[].class);
				
				List<Object[]> 			results 		= typedQuery.getResultList();
				List<WorkOrdersDto> 	DtosCloseList 		= null;
			
				if (results != null && !results.isEmpty()) {
					DtosCloseList 			= new ArrayList<WorkOrdersDto>();
					
					for (Object[] result : results) {
						dto = new WorkOrdersDto();
						dto.setWorkOrderOpenCount((Long) result[0]);
						dto.setWorkorders_id((Long)result[0]);
						dto.setWorkorders_location_ID((Integer)result[1]);
						dto.setWorkorders_location((String)result[2]);
						dto.setWorkorders_statusId(WorkOrdersType.WORKORDERS_STATUS_OPEN);//because create has no status (only to differentiate from close)
						
						DtosCloseList.add(dto);
					}
				}
				return DtosCloseList;
			} catch (Exception e) {
				LOGGER.error("Location Close Exception"+e);
				throw e;
			}
		}
		public List<WorkOrdersDto> getLocationWiseWorkOrderCloseCount(String startDate, String endDate, Integer companyID) throws Exception {
			TypedQuery<Object[]> 	typedQuery 	= null;
			WorkOrdersDto 			dto 		= null;
			try {
				typedQuery = entityManager.createQuery(
						"SELECT count(WO.workorders_id), WO.workorders_location_ID, PL.partlocation_name , WO.workorders_statusId "
								+ " From WorkOrders as WO "
								+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid "
								+ " INNER JOIN PartLocations AS PL ON PL.partlocation_id = WO.workorders_location_ID "
								+ " WHERE WO.start_date between '"+startDate+"' AND '"+endDate+"' "
								+ " AND WO.companyId = "+companyID+"AND WO.markForDelete = 0 AND V.vStatusId <> 4 "
								+ " AND WO.workorders_statusId = "+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+" "
								+ " GROUP BY WO.workorders_location_ID ",
								Object[].class);
				
				List<Object[]> 			results 		= typedQuery.getResultList();
				List<WorkOrdersDto> 	DtosCloseList 		= null;
				
				if (results != null && !results.isEmpty()) {
					DtosCloseList 			= new ArrayList<WorkOrdersDto>();
					
					for (Object[] result : results) {
						dto = new WorkOrdersDto();
						dto.setWorkOrderCloseCount((Long) result[0]);
						dto.setWorkorders_location_ID((Integer)result[1]);
						dto.setWorkorders_location((String)result[2]);
						dto.setWorkorders_statusId((short)result[3]);
						
						DtosCloseList.add(dto);
					}
				}
				return DtosCloseList;
			} catch (Exception e) {
				LOGGER.error("Location Open Exception"+e);
				throw e;
			}
		}
		
		public List<WorkOrdersDto> getWorkorderDetailsBetweenDatesByStatus(Integer companyID, String WOStatusQuery) throws Exception {
			TypedQuery<Object[]> 	typedQuery 					= null;
			WorkOrdersDto 			list 						= null;
			List<WorkOrdersDto> 	Dtos 						= null;
			String 					todaysDate					= null;
			try {
				todaysDate	= format2.format(new Date());
				
				typedQuery = entityManager.createQuery(
						"SELECT WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, WO.workorders_location_ID, "
								+ " PL.partlocation_name , WO.workorders_statusId, WO.created, WO.completed_date, WO.start_date, "
								+ " WO.totalworkorder_cost "
								+ " From WorkOrders as WO "
								+ " INNER JOIN PartLocations AS PL ON PL.partlocation_id = WO.workorders_location_ID "
								+ " INNER JOIN Vehicle AS V ON V.vid = WO.vehicle_vid "
								+ " WHERE "+WOStatusQuery+" AND WO.companyId = "+companyID+"AND WO.markForDelete = 0 AND V.vStatusId <> 4 ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();
				
				
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<>();
					
					for(Object[] result : results) {
						list = new WorkOrdersDto();
						
						list.setWorkorders_id((Long)result[0]);
						list.setWorkorders_Number((Long)result[1]);
						list.setVehicle_vid((Integer)result[2]);
						list.setVehicle_registration((String)result[3]);
						list.setWorkorders_location_ID((Integer)result[4]);
						list.setWorkorders_location((String)result[5]);
						list.setWorkorders_statusId((short)result[6]);
						list.setWorkorders_status(WorkOrdersType.getStatusName((short)result[6]));
						list.setCreated(sqldateTime.format((Timestamp)result[7]));
						if((Date)result[8] != null) {
							list.setCompleted_date(sqldateTime.format((Date)result[8]));
						}else {
							list.setCompleted_date("-");
						}
						list.setStart_date(sqldateTime.format((Timestamp)result[9]));
						
						if(result[10] != null)
							list.setTotalworkorder_cost(Double.parseDouble(toFixedTwo.format(result[10])));
						
						list.setAgeing(DateTimeUtility.getDayDiffBetweenTwoDates(DateTimeUtility.getTimeStamp(result[9]+"",DateTimeUtility.YYYY_MM_DD_HH_MM_SS), DateTimeUtility.getTimeStamp(todaysDate+DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS)));
						
						Dtos.add(list);
					}
				}
				
				return Dtos;
			
			} catch (Exception e) {
				LOGGER.error("getAllLocationWiseWorkOrderCountDetails Expection "+e);
				throw e;
			}
			
		}
		
		@Transactional
		public WorkOrdersTasksToParts getLastOccorredOnPartDetailsByVehicleId(Integer vid, Long partid, Integer companyID ) throws Exception {
			try {
				Query queryt = entityManager.createQuery(
						" SELECT TH.workordertaskto_partid ,TH.workorders_id FROM WorkOrdersTasksToParts AS TH "
								+ " WHERE  TH.vehicle_vid = "+vid+" AND TH.partid ="+partid+" "
								+ " AND TH.companyId = "+ companyID +" AND TH.markForDelete = 0 ORDER BY TH.workordertaskto_partid desc ");
								queryt.setMaxResults(1);
				Object[] result = null;
				WorkOrdersTasksToParts select  = new WorkOrdersTasksToParts();
				try {
					result = (Object[]) queryt.getSingleResult();
					
				} catch (NoResultException nre) {
					
					return null;
				}
				
				if (result != null) {
					
					select.setWorkordertaskto_partid((Long)result[0]);
					select.setWorkorders_id((Long)result[1]);
					
				}
				return select;
				} catch (Exception e) {
					LOGGER.error("Excep", e);
					throw e;
				}
			}
		
		@Transactional
		public WorkOrdersDto getWorkOrderLastUpdatedDetails(Long workorders_id, Integer companyID) throws Exception {
			WorkOrdersDto 	dto 			= null;
			Query 			query 			= null;
			Object[] 		result 			= null;
			try {
			query = entityManager.createQuery(
						" SELECT WO.workorders_id ,WO.lastupdated, WO.vehicle_Odometer FROM WorkOrders AS WO "
								+ " WHERE  WO.workorders_id = "+workorders_id+" "
								+ " AND WO.companyId = "+ companyID +" AND WO.markForDelete = 0 ");

			result = (Object[]) query.getSingleResult();

			if (result != null) {
				dto = new WorkOrdersDto();

				dto.setWorkorders_id((Long) result[0]);
				dto.setLastupdated(sqldateTime.format((Timestamp) result[1]));
				dto.setVehicle_Odometer((Integer) result[2]);
			} 

			return dto;	
		}catch(Exception e) {
			LOGGER.error("err"+e);
			throw e;
		}
	}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<TripSheetExpenseDto> getWorkOrdersListForTallyImport(String fromDate, String toDate, Integer companyId,
				String tallyCompany) throws Exception {
			try {
				Query query = entityManager.createQuery(
						"SELECT WO.workorders_id, WO.workorders_Number, WO.start_date, "
						+ " VH.vehicle_registration, VH.ledgerName, TC.companyName, WO.created, WO.vehicle_vid, "
						+ " WO.totalworkorder_cost, WO.isPendingForTally "
						+ " FROM WorkOrders WO "
						+ " INNER JOIN Vehicle VH ON VH.vid = WO.vehicle_vid "
						+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = WO.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
						+ " WHERE WO.completed_date between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
						+ " AND WO.companyId = "+companyId+" AND WO.markForDelete = 0 AND WO.totalworkorder_cost > 0 AND WO.approvalStatusId = 1"
						+ "AND WO.workorders_statusId = "+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+"" );
					
				
				List<Object[]> results = null;
				try {
					results = query.getResultList();
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}
				List<TripSheetExpenseDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<TripSheetExpenseDto>();
					TripSheetExpenseDto select;
					for (Object[] vehicle : results) {
						if (vehicle != null) {
							select = new TripSheetExpenseDto();
							select.setTripExpenseID((Long) vehicle[0]);
							select.setTripSheetNumber((Long) vehicle[1]);
							select.setVoucherDate(tallyFormat.format((java.util.Date)vehicle[2]));
							select.setVehicle_registration((String) vehicle[3]);
							select.setLedgerName((String) vehicle[4]);
							select.setTallyCompanyName((String) vehicle[5]);
							select.setCreated((Date) vehicle[6]);
							select.setExpenseFixedId(PaymentTypeConstant.PAYMENT_TYPE_CREDIT);
							select.setVid((Integer) vehicle[7]);
							select.setExpenseAmount((Double) vehicle[8]);
							select.setPendingForTally((boolean) vehicle[9]);
							select.setExpenseTypeId(VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
							select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
							select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
							
							select.setTripSheetNumberStr("WO-"+select.getTripSheetNumber());
							select.setTripSheetId(select.getTripExpenseID());
							select.setExpenseName("Motor Part Expense");
							select.setVendorName("Motor Part Expense");
							
							if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
								select.setCredit(true);
							}else {
								select.setCredit(false);
							}
							
							if(select.getVendorName() == null ) {
								select.setVendorName("--");
							}
							if(select.getCreated() != null ) {
								select.setCreatedStr(sqldateTime.format(select.getCreated()));
							}
							
							
							
							 select.setRemark("WorKOrder On Vehicle "+select.getVehicle_registration()+" Date: "+sqldateTime.format((java.util.Date)vehicle[2]));
							
							if(select.getTallyCompanyName() == null) {
								select.setTallyCompanyName("--");
							}
							if(select.getLedgerName() == null) {
								select.setLedgerName("--");
							}
							
							Dtos.add(select);
						}
					}
					
				}
				return Dtos;
				
			} catch (Exception e) {
				throw e;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Transactional
		@Override
		public ValueObject saveWorkOrder(ValueObject valueObject) throws Exception {
			WorkOrderSequenceCounter 				sequenceCounter 						= null;
			CustomUserDetails 						userDetails 							= null;
			HashMap<String, Object> 				configuration							= null;
			VehicleDto 								CheckVehicleStatus 						= null;
			PartLocations							partLocations							= null;
			int										locationId								= 0;
			List<ServiceReminderDto>				serviceList								= null;
			boolean									allowToCreateWOInTripRouteStatus		= false;
			Issues									issue									= null;
			IssuesTasks								issueTask								= null;
			WorkOrders								workOrders								= null;
			boolean                                 isFromMob                               = false;
			Integer                                 currentODDMETER                         = 0;
			ArrayList<ValueObject>					dataArrayObjColl						= null;
			try {
				
				dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("workOrderDetails");
				
				
				if((dataArrayObjColl == null || dataArrayObjColl.isEmpty()) && (valueObject.getString("serviceReminderId", null) == null || valueObject.getString("serviceReminderId", null).trim().equals("")) && (valueObject.getString("issueId" , null) == null || valueObject.getString("issueId" ,"").trim().equals(""))) {
					valueObject.put("emptyTask", true);
					valueObject.put("accessToken", Utility.getUniqueToken(request));
					return valueObject;
				}
				
				valueObject.put("serviceReminderId", Utility.removeLastComma(valueObject.getString("serviceReminderId", "")));
				
				isFromMob       = valueObject.getBoolean("isFromMob",false);
				userDetails     = Utility.getObject(valueObject);
				configuration   = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				sequenceCounter = workOrderSequenceService.findNextWorkOrderNumber(userDetails.getCompany_id());
				valueObject.put("multiIssueInWO", configuration.getOrDefault("multiIssueInWO", false));
				if (sequenceCounter == null) {
					valueObject.put("sequenceNotFound", true);
					return valueObject;
				}
				valueObject.put("userDetails", userDetails);
				if(isFromMob){
					CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("vid"),valueObject.getInt("companyId"));
				}else{
				CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("vid"));
				}
				
				if(CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE && (boolean) configuration.get("allowToCreateWOInTripRouteStatus")) {
					allowToCreateWOInTripRouteStatus = true;
				}
				if(CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACCIDENT && valueObject.getLong("accidentId",0) > 0) {
					allowToCreateWOInTripRouteStatus = true;
				}
				if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE 
						|| allowToCreateWOInTripRouteStatus) {
					
					  if(valueObject.getString("location") == null || valueObject.getString("location") == "") { 
						  partLocations = new  PartLocations();
						  partLocations.setPartlocation_name(valueObject.getString("new_workorders_location"));
						  partLocations.setMarkForDelete(false); partLocations.setAutoCreated(true);
						  partLocations.setCompanyId(userDetails.getCompany_id());
						  partLocations.setCreatedById(userDetails.getId());
						  partLocations.setCreatedOn(new Timestamp(System.currentTimeMillis()));
						  partLocationsService.addPartLocations(partLocations);
						  if(partLocations != null && partLocations.getPartlocation_id() > 0) {
							  locationId = partLocations.getPartlocation_id();
						  } 
					  } else {
						  locationId = valueObject.getInt("location");
					  }
					  
					  valueObject.put("locationId", locationId);
					if (valueObject.getString("woStartDate") != "" && valueObject.getString("woStartDate") != null && locationId > 0) {
						WorkOrders WorkOrd = WOBL.saveWorkOrderDetails(valueObject);
						WorkOrd.setWorkorders_statusId(WorkOrdersType.WORKORDERS_STATUS_OPEN);
						WorkOrd.setTotalsubworktask_cost(WORK_ORDER_DEFALAT_AMOUNT);
						WorkOrd.setTotalworktax_cost(WORK_ORDER_DEFALAT_AMOUNT);
						WorkOrd.setTotalworkorder_cost(WORK_ORDER_DEFALAT_AMOUNT);
						WorkOrd.setCompanyId(userDetails.getCompany_id());
						WorkOrd.setWorkorders_Number(sequenceCounter.getNextVal());
						workOrders = 	addWorkOrders(WorkOrd);
						if(valueObject.getLong("accidentId",0) <= 0) {
							vehicleService.Update_Vehicle_Status_TripSheetID(WorkOrd.getWorkorders_id(), WorkOrd.getVehicle_vid(), 
									userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP);
						}
						if(workOrders.getAccidentId() != null && workOrders.getAccidentId() > 0) {
							if((boolean) configuration.getOrDefault("multipleQuotation", false)) {
								accidentDetailsService.saveAccedentServiceDetails(workOrders.getWorkorders_id(),workOrders.getWorkorders_Number(),workOrders.getAccidentId(),AccidentStatus.SERVICE_TYPE_WO,userDetails.getCompany_id());
							}else {
								accidentDetailsService.updateAccidentDetailsServiceDetails(workOrders.getWorkorders_id(), AccidentStatus.SERVICE_TYPE_WO, workOrders.getAccidentId());
							}
							accidentDetailsService.updateAccidentDetailsStatus(workOrders.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_QUOTATION_CREATED);
						}
						valueObject.put("saveWorkOrder", true);
						valueObject.put("workOrder", WorkOrd);
						valueObject.put("workOrderId", WorkOrd.getWorkorders_id());
						
						// Save task Details
						saveWorkOrderTasks(valueObject);
						if(isFromMob){
							 currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(WorkOrd.getVehicle_vid(),userDetails.getCompany_id());
						}else{
							 currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(WorkOrd.getVehicle_vid());
						}
						
						if (currentODDMETER < WorkOrd.getVehicle_Odometer()) {
							
							vehicleService.updateCurrentOdoMeter(WorkOrd.getVehicle_vid(), WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
							ServiceReminderService.updateCurrentOdometerToServiceReminder(WorkOrd.getVehicle_vid(),
														WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
							serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(WorkOrd.getVehicle_vid(), userDetails.getCompany_id(), userDetails.getId());
							if(serviceList != null) {
								for(ServiceReminderDto list : serviceList) {
									
									if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
										ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
									}
								}
							}
							VehicleOdometerHistory vehicleUpdateHistory = vehicleOdometerHistoryBL.prepareOdometerGetHistoryToWorkOrder(WorkOrd);
							vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
							vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						}else if (currentODDMETER == WorkOrd.getVehicle_Odometer()) {
							VehicleOdometerHistory vehicleUpdateHistory = vehicleOdometerHistoryBL.prepareOdometerGetHistoryToWorkOrder(WorkOrd);
							vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
							vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						}
						
					}
					
					if(valueObject.getLong("issueId",0) > 0 ) {
						Date dateTime = DateTimeUtility.getCurrentDateTime();
						issuesService.update_Create_WorkOrder_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_WOCREATED, userDetails.getId(), dateTime, workOrders.getWorkorders_id(), valueObject.getLong("issueId"),userDetails.getCompany_id());
						issue 		= issuesService.getIssueDetailsByIssueId(valueObject.getLong("issueId"), userDetails.getCompany_id());
						if(issue != null) {
							issueTask 	= issuesBL.prepareIssuesTaskDetails(issue,userDetails);
							issuesService.registerNew_IssuesTasks(issueTask);
						}
					}else {
						String issueIds =valueObject.getString("issueId","");
						if(!issueIds.trim().equals("")&& !issueIds.equals("0")) {
							String[] strArr =issueIds.split(",");
							for(String issueId : strArr) {
								if(!issueId.trim().equals("")) {
									issuesService.update_Create_WorkOrder_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_WOCREATED, userDetails.getId(), DateTimeUtility.getCurrentDate(), workOrders.getWorkorders_id(), Long.parseLong(issueId),userDetails.getCompany_id());
									issue 		= issuesService.getIssueDetailsByIssueId(Long.parseLong(issueId), userDetails.getCompany_id());
									if(issue != null) {
										issueTask 	= issuesBL.prepareIssuesTaskDetails(issue,userDetails);
										issuesService.registerNew_IssuesTasks(issueTask);
									}
								}
							}
						}
					}
					
				} else {
					
					if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
						valueObject.put("inTripRoute", true);
					} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
						valueObject.put("inWorkShop", true);
					} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SOLD) {
						valueObject.put("inSold", true);
					} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE) {
						valueObject.put("inActive", true);
					} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SURRENDER) {
						valueObject.put("inSurrender", true);
					} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACCIDENT) {
						valueObject.put("inAccident", true);
					}
				}
				valueObject.put("accessToken", Utility.getUniqueToken(request));
				valueObject.put("vehicleStatusString", VehicleStatusConstant.getVehicleStatus(CheckVehicleStatus.getvStatusId()));
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			}
		}
		
		@SuppressWarnings({ "unchecked", "unused" })
		@Transactional
		public void saveWorkOrderTasks(ValueObject object) throws Exception {
			CustomUserDetails 				 userDetails 				= null;
			ArrayList<ValueObject> 			 dataArrayObjColl 			= null;
			WorkOrders 					 	 WorkOrd					= null;
			List<JobType> 					 jobTypeList 				= null;
			HashMap<Integer, ServiceReminderDto> 		dtosMap1 		= null;
			ArrayList<String> 				addedTask					= null;
			try {
				userDetails 		= (CustomUserDetails) object.get("userDetails");
				WorkOrd				= (WorkOrders) object.get("workOrder");
				jobTypeList 		= jobTypeService.listJobTypeByCompanyId(userDetails.getCompany_id());
				
				dataArrayObjColl	= (ArrayList<ValueObject>) object.get("workOrderDetails");
				addedTask	= new ArrayList<String>();
		
				if(object.getString("serviceReminderId", null) !=  null && !object.getString("serviceReminderId").trim().equals("")) {
					String service_id[]	= object.getString("serviceReminderId").split(",");
					
					if(service_id != null && service_id.length > 0) {
						dtosMap1 = getJobtypeAndSubtypeFromServiceId(object.getString("serviceReminderId"), userDetails.getCompany_id());
					}
				}
				if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
					for (ValueObject wo : dataArrayObjColl) {
						if (wo.getString("jobSubType") != null || wo.getString("jobType") != null) {

							if(wo.getString("jobType") != null && !wo.getString("jobType").trim().equalsIgnoreCase("")) {
								if(wo.getString("jobSubType") != null ) {
								String [] arr=wo.getString("jobSubType").split(",");
								
								for(String w: arr) {
									if(w !=null || w!= "") {
									
								String [] arr2=w.split("_");
								
								//if(arr2.length > 1)
								
								WorkOrdersTasks WRTask = new WorkOrdersTasks();
								
								WRTask.setJob_typetaskId(wo.getInt("jobType"));
								WRTask.setJob_subtypetask_id(Integer.parseInt(arr2[0]));
								WRTask.setCategoryId(wo.getInt("part_category"));
								WRTask.setReasonRepairTypeId(wo.getInt("reason_category"));
								
								if(arr2.length > 1)
									WRTask.setService_id(Long.parseLong(arr2[1]));
											
								
								if(dtosMap1 != null) {
									ServiceReminderDto reminderDto = dtosMap1.get(Integer.parseInt(w));
									if(reminderDto != null) {
										WRTask.setService_id(reminderDto.getService_id());
									}
								}
								
								if(wo.getString("remark") != null) {
									WRTask.setJobTypeRemark(wo.getString("remark"));								
								}
								
								WRTask.setWorkorders(WorkOrd);
								WRTask.setVehicle_vid(WorkOrd.getVehicle_vid());
								WRTask.setTotalpart_cost(WORK_ORDER_DEFALAT_AMOUNT);
								WRTask.setTotallaber_cost(WORK_ORDER_DEFALAT_AMOUNT);
								WRTask.setTotaltask_cost(WORK_ORDER_DEFALAT_AMOUNT);
								WRTask.setCompanyId(userDetails.getCompany_id());
								
								List<WorkOrders> LastOcc = getLast_WorkOrdersTask_To_WorkOrders_details(WRTask.getJob_typetaskId(), WRTask.getJob_subtypetask_id(), WorkOrd.getVehicle_vid(), userDetails.getCompany_id());
								
								if (LastOcc != null && !LastOcc.isEmpty()) {
									for (WorkOrders lastOccWO : LastOcc) {
										
										WRTask.setLast_occurred_woId(lastOccWO.getWorkorders_id());
										WRTask.setLast_occurred_date(lastOccWO.getStart_date());
										WRTask.setLast_occurred_odameter(lastOccWO.getVehicle_Odometer());
										break;
									}
								} else {
									WRTask.setLast_occurred_woId(WORK_ORDER_DEFALAT_VALUE);
									WRTask.setLast_occurred_date(null);
									WRTask.setLast_occurred_odameter(WORK_ORDER_DEFALAT_ODAMETER);
								}
								
								addWorkOrdersTask(WRTask);
								
								addedTask.add(WRTask.getJob_typetaskId()+"_"+WRTask.getJob_subtypetask_id());
								
								}else  {
									if(wo.getString("wo_ROT") != null && !wo.getString("wo_ROT").trim().equalsIgnoreCase("0")) {
										WorkOrdersTasks WRTask = new WorkOrdersTasks();
										WRTask.setJob_typetaskId(wo.getInt("jobType"));
										// add New ROT Code in WorkOrder to JOB
										JobSubType DocType = new JobSubType();
										
										for (JobType jobType : jobTypeList) {
											if (jobType.getJob_id() == wo.getInt("jobType")) {
												DocType.setJob_TypeId(jobType.getJob_id());
												DocType.setJob_Type(jobType.getJob_Type());
												break;
											}
										}
										
										DocType.setJob_ROT(wo.getString("wo_ROT"));
										if(!wo.getString("wo_ROT_number").trim().equalsIgnoreCase("0"))
										DocType.setJob_ROT_number(wo.getString("wo_ROT_number"));
										DocType.setJob_ROT_hour("" + 0);
										DocType.setJob_ROT_amount("" + 0);
										DocType.setCompanyId(userDetails.getCompany_id());
										DocType.setCreatedBy(userDetails.getEmail());
										DocType.setCreatedById(userDetails.getId());
										DocType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
										DocType.setJob_ROT_Service_Reminder(false);
										
										List<JobSubType> validate = jobSubTypeService.ValidateJobRotnumber(wo.getString("wo_ROT"),
																			userDetails.getCompany_id());
										
										if (validate != null && !validate.isEmpty()) {
											for (JobSubType jobSubTypeVD : validate) {
												WRTask.setJob_subtypetask_id(jobSubTypeVD.getJob_Subid());
												break;
											}
										} else {
											jobSubTypeService.addJobSubType(DocType);
											WRTask.setJob_subtypetask_id(DocType.getJob_Subid());
										}
									WRTask.setWorkorders(WorkOrd);
									WRTask.setVehicle_vid(WorkOrd.getVehicle_vid());
									WRTask.setTotalpart_cost(WORK_ORDER_DEFALAT_AMOUNT);
									WRTask.setTotallaber_cost(WORK_ORDER_DEFALAT_AMOUNT);
									WRTask.setTotaltask_cost(WORK_ORDER_DEFALAT_AMOUNT);
									WRTask.setCompanyId(userDetails.getCompany_id());
									
									List<WorkOrders> LastOcc = getLast_WorkOrdersTask_To_WorkOrders_details(WRTask.getJob_typetaskId(), WRTask.getJob_subtypetask_id(), WorkOrd.getVehicle_vid(), userDetails.getCompany_id());
									
									if (LastOcc != null && !LastOcc.isEmpty()) {
										for (WorkOrders lastOccWO : LastOcc) {
											
											WRTask.setLast_occurred_woId(lastOccWO.getWorkorders_id());
											WRTask.setLast_occurred_date(lastOccWO.getStart_date());
											WRTask.setLast_occurred_odameter(lastOccWO.getVehicle_Odometer());
											break;
										}
									} else {
										WRTask.setLast_occurred_woId(WORK_ORDER_DEFALAT_VALUE);
										WRTask.setLast_occurred_date(null);
										WRTask.setLast_occurred_odameter(WORK_ORDER_DEFALAT_ODAMETER);
									}
									
									addWorkOrdersTask(WRTask);
									addedTask.add(WRTask.getJob_typetaskId()+"_"+WRTask.getJob_subtypetask_id());
									}
								}
								}
							}
						}
					}
				}
				}
				saveWorkOrderTaskFromSProgram(dtosMap1, WorkOrd, userDetails, addedTask);
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		private void saveWorkOrderTaskFromSProgram(HashMap<Integer, ServiceReminderDto> dtosMap1, WorkOrders WorkOrd, 
				CustomUserDetails	userDetails, ArrayList<String> 	addedTask) throws Exception {
			
			if(dtosMap1 != null && !dtosMap1.isEmpty()) {
				for (Integer key : dtosMap1.keySet()) {
					if(!addedTask.contains(dtosMap1.get(key).getServiceTypeId()+"_"+dtosMap1.get(key).getServiceSubTypeId())) {
						
						WorkOrdersTasks WRTask = new WorkOrdersTasks();
						WRTask.setJob_typetaskId(dtosMap1.get(key).getServiceTypeId());
						WRTask.setJob_subtypetask_id(dtosMap1.get(key).getServiceSubTypeId());
						WRTask.setService_id(dtosMap1.get(key).getService_id());
						WRTask.setWorkorders(WorkOrd);
						WRTask.setVehicle_vid(WorkOrd.getVehicle_vid());
						WRTask.setTotalpart_cost(WORK_ORDER_DEFALAT_AMOUNT);
						WRTask.setTotallaber_cost(WORK_ORDER_DEFALAT_AMOUNT);
						WRTask.setTotaltask_cost(WORK_ORDER_DEFALAT_AMOUNT);
						WRTask.setCompanyId(userDetails.getCompany_id());
						
						List<WorkOrders> LastOcc = getLast_WorkOrdersTask_To_WorkOrders_details(WRTask.getJob_typetaskId(), WRTask.getJob_subtypetask_id(), WorkOrd.getVehicle_vid(), userDetails.getCompany_id());
						
						if (LastOcc != null && !LastOcc.isEmpty()) {
							for (WorkOrders lastOccWO : LastOcc) {
								
								WRTask.setLast_occurred_woId(lastOccWO.getWorkorders_id());
								WRTask.setLast_occurred_date(lastOccWO.getStart_date());
								WRTask.setLast_occurred_odameter(lastOccWO.getVehicle_Odometer());
								break;
							}
						} else {
							WRTask.setLast_occurred_woId(WORK_ORDER_DEFALAT_VALUE);
							WRTask.setLast_occurred_date(null);
							WRTask.setLast_occurred_odameter(WORK_ORDER_DEFALAT_ODAMETER);
						}
						
						addWorkOrdersTask(WRTask);
					}
					
				}
			}
		}
		
		@Override
		@Transactional
		public ValueObject savePartForWorkOderTask(ValueObject object) throws Exception {
			
			try {
					addPartToWorkOrder(object);
					
					return object;
			
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional(propagation = Propagation.REQUIRES_NEW)
		private void addPartToWorkOrder(ValueObject		object) {
			try {
				xSync.execute(object.getLong("masterPartId")+"_"+object.getInt("companyId",0), () -> {
					
						List<InventoryDto> 					get_InventoryList					= null;
						Double 								EachpartInventoryUnitprice			= WORK_ORDER_DEFALAT_AMOUNT;
						Double 								Totalcost 							= WORK_ORDER_DEFALAT_AMOUNT;
						WorkOrdersTasksToParts				preWorkOrdersTasksToParts			= null;
						WorkOrdersDto						preWorkOrderLastUpdatedDetails		= null;
						WorkOrdersTasksToParts 				workTaskToParts						= null;
						Double 								workOrdersQuantity					= null;
						InventoryLocation 					inventoryLocation					= null;
						WorkOrdersTasksToReceived 			TasksToReceived						= null;
						String 								subLocationQuery 					= "";
						String 								query 								= "";
						String								invoiceWisePartListQuery			= "";
						WorkOrdersDto 						workOrdersDto						= null;
						WorkOrders							workOrders							= null;
						boolean 							isFilled_GET_Permission				= false;
						CustomUserDetails 				 	userDetails 						= null;
						List<PartLocationPermissionDto> 	PartLocPermissionList				= null;
						List<PartLocations>					partlocationList					= null;
						Date 								currentDateUpdate					= null;
						Timestamp							LastUpdate							= null;
						
						boolean                             isFromMob                           = false;
						try {
							Thread.sleep(2500);

							isFromMob               = object.getBoolean("isFromMob",false);
							currentDateUpdate 		= new Date();
							LastUpdate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
							userDetails             = Utility.getObject(object);
								
								partlocationList		= partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id());
								PartLocPermissionList 	= partLocationsBL.prepareListofPartLocation(partlocationList);
								if(isFromMob){
									workOrders          = WorkOrdersDao.getWorkOrders(object.getLong("workOrderId"), userDetails.getCompany_id());
								}else{
									workOrders          = getWorkOrders(object.getLong("workOrderId"));
								}
								
								workOrdersDto 			= WOBL.getWorkOrders(workOrders);
							isFilled_GET_Permission	= inventoryBL.isFilled_GET_Permission(workOrders.getWorkorders_location_ID(), PartLocPermissionList);
							
							if (isFilled_GET_Permission) {
								
								workOrdersQuantity 	= object.getDouble("quantity", 0);
							if(workOrdersQuantity > 0) {
								
								if(object.getBoolean("showSubLocation") == true && object.getInt("subLocationId",0) > 0) {
									subLocationQuery 	= " AND I.subLocationId="+object.getInt("subLocationId")+" ";
								}
								if(object.getBoolean("invoiceWisePartListConfig")) {
									invoiceWisePartListQuery = "AND I.inventory_id = "+object.getLong("inventoryId")+" ";
								}
								
								query = " "+subLocationQuery+" "+invoiceWisePartListQuery+"";
								
								
								if(object.getBoolean("invoiceWisePartListConfig")) {
									get_InventoryList 	= inventoryService.getWorkorderInventoryPartDetails(userDetails.getCompany_id(), query);
								}else {
									get_InventoryList 	= inventoryService.Get_WorkOrder_InventoryLocation_id_To_Inventory(object.getLong("partId"),userDetails.getCompany_id(),subLocationQuery);
								}
								
								if (get_InventoryList != null && !get_InventoryList.isEmpty()) {
									for (InventoryDto inventoryDto : get_InventoryList) {
										if (inventoryDto.getQuantity() > 0) {
											if (workOrdersQuantity <= inventoryDto.getQuantity()) {
												
												EachpartInventoryUnitprice 		= round((inventoryDto.getTotal() / inventoryDto.getHistory_quantity()), 2);
												Totalcost 						= workOrdersQuantity * EachpartInventoryUnitprice;
												preWorkOrdersTasksToParts		= getLastOccorredOnPartDetailsByVehicleId(workOrdersDto.getVehicle_vid(),inventoryDto.getPartid(),object.getInt("companyId",0));
												
												if(preWorkOrdersTasksToParts != null) {
													preWorkOrderLastUpdatedDetails	= getWorkOrderLastUpdatedDetails(preWorkOrdersTasksToParts.getWorkorders_id(),userDetails.getCompany_id());
												}
												workTaskToParts = WOBL.prepareWorkOrdersTaskToPartInAjax(object, inventoryDto, workOrdersQuantity, userDetails, EachpartInventoryUnitprice, Totalcost, workOrdersDto, preWorkOrderLastUpdatedDetails);
												addWorkOrdersTaskToParts(workTaskToParts);
												
												if(object.getBoolean("showSubLocation") == true && object.getInt("subLocationId",0) > 0) {
													if(isFromMob){
														inventoryService.subtractInvenotryFromSubLocation(workOrdersQuantity,inventoryDto.getInventory_id(),object.getInt("subLocationId"),object.getInt("companyId",0));
													}else{
														inventoryService.subtractInvenotryFromSubLocation(workOrdersQuantity,inventoryDto.getInventory_id(),object.getInt("subLocationId"));
													}
												}else {
													if(isFromMob){
														inventoryService.Subtract_update_Inventory_from_Workorder(workOrdersQuantity,inventoryDto.getInventory_id(),object.getInt("companyId",0));
													}else{
														inventoryService.Subtract_update_Inventory_from_Workorder(workOrdersQuantity,inventoryDto.getInventory_id());
													}
												}
												
												inventoryService.Subtract_update_InventoryLocation_from_Workorder(inventoryDto.getPartid(),inventoryDto.getLocationId(),userDetails.getCompany_id());
												
												inventoryService.Subtract_update_InventoryAll_from_Workorder(inventoryDto.getPartid(),userDetails.getCompany_id());
												
												updateWorkOrdersTask_TotalPartCost(workTaskToParts.getWorkordertaskid(),userDetails.getCompany_id());
												if(isFromMob){
													updateWorkOrderMainTotalCost(object.getLong("workOrderId"),object.getInt("companyId",0));
												}else{
													updateWorkOrderMainTotalCost(object.getLong("workOrderId"));
												}
												
												
												updateWorkOrderProcess(WorkOrdersType.WORKORDERS_STATUS_INPROCESS, userDetails.getId(), LastUpdate, object.getLong("workOrderId"),userDetails.getCompany_id());
												
												partInvoiceRepository.update_anyPartNumberAsign_InPartInvoice(inventoryDto.getPartInvoiceId(),userDetails.getCompany_id());
												break;
												
											} else {
												
												EachpartInventoryUnitprice 	= round(inventoryDto.getTotal() / inventoryDto.getHistory_quantity(), 2);
												Totalcost 					= inventoryDto.getQuantity() * EachpartInventoryUnitprice;
												workTaskToParts = WOBL.prepareWorkOrdersTaskToPartInAjax(object, inventoryDto, inventoryDto.getQuantity(), userDetails, EachpartInventoryUnitprice, Totalcost, workOrdersDto, preWorkOrderLastUpdatedDetails);
												
												addWorkOrdersTaskToParts(workTaskToParts);
										if(object.getBoolean("showSubLocation") && object.getInt("subLocationId",0) > 0) {
													if(isFromMob){
														inventoryService.subtractInvenotryFromSubLocation(inventoryDto.getQuantity(),inventoryDto.getInventory_id(),object.getInt("subLocationId"),object.getInt("companyId",0));
													}else{
														inventoryService.subtractInvenotryFromSubLocation(inventoryDto.getQuantity(),inventoryDto.getInventory_id(),object.getInt("subLocationId"));
													}
												}else {
													if(isFromMob){
														inventoryService.Subtract_update_Inventory_from_Workorder(inventoryDto.getQuantity(),inventoryDto.getInventory_id(),object.getInt("companyId",0));
													}else{
														inventoryService.Subtract_update_Inventory_from_Workorder(inventoryDto.getQuantity(),inventoryDto.getInventory_id());
													}
												}
												inventoryService.Subtract_update_InventoryLocation_from_Workorder(inventoryDto.getPartid(),inventoryDto.getLocationId(),userDetails.getCompany_id());
												
												inventoryService.Subtract_update_InventoryAll_from_Workorder(inventoryDto.getPartid(),object.getInt("companyId",0));
												
												workOrdersQuantity = (workOrdersQuantity - inventoryDto.getQuantity());
												
												updateWorkOrdersTask_TotalPartCost(workTaskToParts.getWorkordertaskid(),userDetails.getCompany_id());
												if(isFromMob){
													updateWorkOrderMainTotalCost(object.getLong("workOrderId"),object.getInt("companyId",0));
												}else{
													updateWorkOrderMainTotalCost(object.getLong("workOrderId"));
												}
												updateWorkOrderProcess(WorkOrdersType.WORKORDERS_STATUS_INPROCESS, userDetails.getId(), LastUpdate, object.getLong("workOrderId"),userDetails.getCompany_id());
												
												partInvoiceRepository.update_anyPartNumberAsign_InPartInvoice(inventoryDto.getPartInvoiceId(),userDetails.getCompany_id());
												
											}
										}
									}
									
									
									if (object.getString("oldpart") != null) {
										if(isFromMob){
											inventoryLocation 	= inventoryService.getInventoryLocation(object.getLong("partId"),userDetails.getCompany_id());
										}else{
											inventoryLocation 	= inventoryService.getInventoryLocation(object.getLong("partId"));
										}
										TasksToReceived 	= WOBL.prepareWorkOrdersTaskToReceicedInAjax(object, inventoryLocation);
										TasksToReceived.setCompanyId(object.getInt("companyId",0));
										addWorkOrdersToReceived(TasksToReceived);
									}
									
									object.put("partAdded", true);
									
								} else {
									object.put("NoInventory", true);
								}
							}else {
								object.put("quantityZero", true);
							}
							
							} else {
								object.put("NoAuthen", true);
							}
							object.put("accessToken", Utility.getUniqueToken(request));
						} catch (Exception e) {
							logger.error("exception inside xSync ", e);
							
						}
						
					});
			} catch (Exception e) {
				
			}
			
		}
		
		@Transactional
		@Override
		public ValueObject workOrderTaskToPartDocumentSave (MultipartFile[] fileUpload,ValueObject object) throws Exception {
			
			CustomUserDetails userDetails = null;
			org.fleetopgroup.persistence.document.WorkOTaskToPartDocument woPartDoc = null; 
			ValueObject valueOutObject= new ValueObject();
			try {
				
			if(fileUpload != null && fileUpload.length >0 ) {
				woPartDoc=new org.fleetopgroup.persistence.document.WorkOTaskToPartDocument();
				userDetails= (CustomUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				for(int i=0;i<fileUpload.length;i++) {
					

					woPartDoc.setWorkordertasktoPartid(object.getLong("workordertaskto_partid"));
					byte [] bytes =fileUpload[i].getBytes();
					woPartDoc.setDocumentContent(bytes);
					woPartDoc.setDocumentFilename(fileUpload[i].getOriginalFilename());
					woPartDoc.setDocumentContentType(fileUpload[i].getContentType());

					woPartDoc.setCompanyId(userDetails.getCompany_id());
					woPartDoc.setCreatedById(userDetails.getId());
					woPartDoc.setLastModifiedById(userDetails.getId());
					woPartDoc.setMarkForDelete(false);
					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

					woPartDoc.setCreated(toDate);
					woPartDoc.setLastupdated(toDate);
					woPartDoc.setDescription(object.getString("description"));
					workOTaskToPartDocumentService.save(woPartDoc);
				}
					
					WorkOrdersTasksToPartsRepository.updateDocumentWorkOrdersTaskTOParts(true,object.getLong("workordertaskto_partid"), userDetails.getCompany_id());
				
				valueOutObject.put("FileUploaded", true);
			
			}
			return valueOutObject;
					
					} catch (Exception e) {
						
					throw e;
					}
		
		}
		
		@Transactional
		@Override
		public ValueObject saveLabourForWorkOderTask(ValueObject object) throws Exception {
			CustomUserDetails 				 	userDetails 						= null;
			List<PartLocationPermissionDto> 	PartLocPermission					= null;
			WorkOrdersDto                       work                                = null;
			boolean                             isFromFlag                          = false;
			HashMap<String, Object>             configuration   			= null;
			try {
				isFromFlag              = object.getBoolean("isFromMob",false);
				userDetails             = Utility.getObject(object);
				PartLocPermission 		= partLocationsBL.prepareListofPartLocation(partLocationPermissionService
						.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));
				
				configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);

				if(isFromFlag){
					 work = WOBL.getWorkOrders(getWorkOrders(object.getLong("workOrderId"),userDetails.getCompany_id()));
				}else{
					 work = WOBL.getWorkOrders(getWorkOrders(object.getLong("workOrderId")));
				}
				
				if (inventoryBL.isFilled_GET_Permission(work.getWorkorders_location_ID(), PartLocPermission)) {
					
					WorkOrdersTasksToLabor workTaskToLabor = WOBL.saveLabourForWorkOderTask(object);
					
					if((boolean) configuration.get("showLaborDataInWO")) {
						
						Double perHourDriverSalary = calculatePerHourLaborCost(workTaskToLabor.getLaberid() ,userDetails.getCompany_id());
						Double hourWork =  workTaskToLabor.getLaberhourscost() ;
						Double totalCost = hourWork * perHourDriverSalary ;
						
						workTaskToLabor.setTotalcost(totalCost);
						
					}
					workTaskToLabor.setCompanyId(userDetails.getCompany_id());
					addWorkOrdersTaskToLabor(workTaskToLabor);

					updateWorkOrdersTask_TotalLaborCost(workTaskToLabor.getWorkordertaskid(), userDetails.getCompany_id());
              
					if(isFromFlag){
						updateWorkOrderMainTotalCost(object.getLong("workOrderId"),userDetails.getCompany_id());
					}else{
					updateWorkOrderMainTotalCost(object.getLong("workOrderId"));
					}
					
					Date currentDateUpdate = new Date();
					Timestamp LastUpdate = new java.sql.Timestamp(currentDateUpdate.getTime());

					updateWorkOrderProcess(WorkOrdersType.WORKORDERS_STATUS_INPROCESS, userDetails.getId(), LastUpdate, object.getLong("workOrderId"), userDetails.getCompany_id());
					
					object.put("labourAdded", true);

				} else {
					object.put("NoAuthen", true);
				}
				
				object.put("accessToken", Utility.getUniqueToken(request));
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional
		@Override
		public ValueObject saveMarkAsComplete(ValueObject object) throws Exception {
			CustomUserDetails 				 	userDetails 					= null;
			WorkOrdersTasks 					workTask						= null;
			try {
				userDetails         = Utility.getObject(object);
				workTask 	= getWorkOrdersCompletion(object.getLong("woTaskId"), userDetails.getCompany_id());

				if (workTask.getMark_complete() == null || workTask.getMark_complete() == 0) {
					updateWorkOrdersTask_Completion(1, object.getLong("woTaskId"), userDetails.getCompany_id());
				} else {
					updateWorkOrdersTask_Completion(0, object.getLong("woTaskId"), userDetails.getCompany_id());
				}
				
				object.put("markAsCompleted", true);
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional
		@Override
		public ValueObject saveWorkOderTasks(ValueObject object) throws Exception {
			CustomUserDetails 			userDetails 			= null;
			JobSubType                  JobSubType              = null;
			boolean                     isFromFlag              = false;
			List<WorkOrdersTasks> workOrdList = null;
			try {
				isFromFlag              = object.getBoolean("isFromMob",false);
				userDetails         = Utility.getObject(object);
				
				String jonSubType =  object.getString("jobsubtypeId","");
				if(!jonSubType.trim().equals("")) {
					workOrdList = new ArrayList<>();
					String[] strArr = jonSubType.split(",");
					for (String subType:strArr) {
						if(!subType.trim().equals("")) {
							WorkOrders WorkOrd = new WorkOrders();
							WorkOrd.setWorkorders_id(object.getLong("workOrderId",0));
							WorkOrdersTasks WorkTOTask = new WorkOrdersTasks();
							WorkTOTask.setVehicle_vid(object.getInt("vid"));
							WorkTOTask.setJob_typetaskId(object.getInt("jobtypeId"));
							if(isFromFlag){
								JobSubType = jobSubTypeService.getJobSubTypeForMob(Integer.parseInt(subType),userDetails.getCompany_id());
							}else{
								JobSubType = jobSubTypeService.getJobSubType(Integer.parseInt(subType));
							}
							if (JobSubType != null) {
								WorkTOTask.setJob_subtypetask_id(Integer.parseInt(subType));
							}
							
							if(object.getString("taskRemark") != null && object.getString("taskRemark") != "") {
								WorkTOTask.setJobTypeRemark(object.getString("taskRemark"));								
							}

							WorkTOTask.setTotalpart_cost(WORK_ORDER_DEFALAT_AMOUNT);
							WorkTOTask.setTotallaber_cost(WORK_ORDER_DEFALAT_AMOUNT);
							WorkTOTask.setTotaltask_cost(WORK_ORDER_DEFALAT_AMOUNT);
							WorkTOTask.setWorkorders(WorkOrd);
							WorkTOTask.setCompanyId(userDetails.getCompany_id());
							WorkTOTask.setIssueIds(object.getInt("issueId",0));
							WorkTOTask.setCategoryId(object.getInt("categoryId"));
							workOrdList.add(WorkTOTask);
//							List<WorkOrders> LastOcc = getLast_WorkOrdersTask_To_WorkOrders_details(WorkTOTask.getJob_typetaskId(), WorkTOTask.getJob_subtypetask_id(), WorkTOTask.getVehicle_vid(), userDetails.getCompany_id());
//
//							if (LastOcc != null && !LastOcc.isEmpty()) {
//								for (WorkOrders lastOccWO : LastOcc) {
//									WorkTOTask.setLast_occurred_woId(lastOccWO.getWorkorders_id());
//									WorkTOTask.setLast_occurred_date(lastOccWO.getStart_date());
//									WorkTOTask.setLast_occurred_odameter(lastOccWO.getVehicle_Odometer());
//									break;
//								}
//							} else {
//								WorkTOTask.setLast_occurred_woId(WORK_ORDER_DEFALAT_VALUE);
//								WorkTOTask.setLast_occurred_date(null);
//								WorkTOTask.setLast_occurred_odameter(0);
//							}

						//	addWorkOrdersTask(WorkTOTask);
							
						}
					}
					saveMultipleTask(workOrdList);
					object.put("taskAdded", true);
				}
				object.put("accessToken", Utility.getUniqueToken(request));
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional
		@Override
		public ValueObject deletePartOfWorkOrdertask(ValueObject object) throws Exception {
			CustomUserDetails 				 	userDetails 						= null;
			List<PartLocationPermissionDto> 	PartLocPermission					= null;
			WorkOrders 							work								= null;
			Object								partUsedQuantity					= null;
			InventoryDto                        inventoryParts                      = null;
			boolean                             isFromMob                           = false;
			try {
				isFromMob           = object.getBoolean("isFromMob",false);
				userDetails         = Utility.getObject(object);
				PartLocPermission   = partLocationsBL.prepareListofPartLocation(partLocationPermissionService
									.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

				work = getLast_WorkOrdersTasktoPARTID_To_WorkOrders_details(object.getLong("workordertaskto_partid"), userDetails.getCompany_id());
				
				if (inventoryBL.isFilled_GET_Permission(work.getWorkorders_location_ID(), PartLocPermission)) {

					WorkOrdersTasksToParts WORKTASKTOPARTS = getWorkOrdersTaskToParts_ONLY_ID(object.getLong("workordertaskto_partid"), userDetails.getCompany_id());
					
					if(work.getAccidentId() != null && work.getAccidentId() > 0) {
						VehicleAccidentDetails	accidentDetails	= accidentDetailsService.getVehicleAccidentDetailsById(work.getAccidentId());
						if(accidentDetails.getStatus() <= AccidentStatus.ACCIDENT_STATUS_QUOTATION_APPROVED) {
							accidentDetailsService.updateAccidentDetailsStatus(object.getLong("accidentId",0), AccidentStatus.ACCIDENT_STATUS_SERVEY_COMPLETED);
						}else {
							object.put("accidentEntryApproved", true);
							return object;
						
						}
					}
					if(isFromMob){
						inventoryParts = inventoryService.getInventory(WORKTASKTOPARTS.getInventory_id(),userDetails.getCompany_id());
					}else{
						inventoryParts = inventoryService.getInventory(WORKTASKTOPARTS.getInventory_id());
					}
					
					
					if (WORKTASKTOPARTS.getQuantity() != null && inventoryParts != null) {

						Double workOrdersQuantity = WORKTASKTOPARTS.getQuantity();
						
						inventoryService.Add_update_Inventory_from_Workorder(workOrdersQuantity,
								inventoryParts.getInventory_id(), userDetails.getCompany_id());

						inventoryService.Add_update_InventoryLocation_from_Workorder(workOrdersQuantity,
								inventoryParts.getInventory_location_id(), userDetails.getCompany_id());
						
						inventoryService.Add_update_InventoryAll_from_Workorder(workOrdersQuantity,
								inventoryParts.getInventory_all_id(), userDetails.getCompany_id());
					}
					
					MasterParts	masterParts	= masterPartsService.getMasterParts(WORKTASKTOPARTS.getPartid());

					deleteWorkOrdersTaskTOParts(WORKTASKTOPARTS.getWorkordertaskto_partid(), userDetails.getCompany_id());

					updateWorkOrdersTask_TotalPartCost(WORKTASKTOPARTS.getWorkordertaskid(), userDetails.getCompany_id());
 
					if(isFromMob){
						updateWorkOrderMainTotalCost(WORKTASKTOPARTS.getWorkorders_id(),userDetails.getCompany_id());
					}else{
					updateWorkOrderMainTotalCost(WORKTASKTOPARTS.getWorkorders_id());
					}
					
					
					if(masterParts.isWarrantyApplicable() || masterParts.isAssetIdRequired()) {
						PartWarrantyDetailsHistory		detailsHistory				= null;		
						List<PartWarrantyDetails>	warrantyList	= warrantyDetailsService.getPartWarrantyDetailsByServiceId(WORKTASKTOPARTS.getWorkorders_id(), (short)2);
						if(warrantyList != null && !warrantyList.isEmpty()) {
							for (PartWarrantyDetails partWarrantyDetails : warrantyList) {
								
								detailsHistory	= PartWarrantyBL.getPartWarrantyHistoryDto(userDetails.getId(), partWarrantyDetails);
								detailsHistory.setHistoryTypeId(PartWarrantyBL.PART_WARRANTY_HISTORY_TYPE_DELETE);
								
								historyRepository.save(detailsHistory);
								
								if(partWarrantyDetails.getReplacePartWarrantyDetailsId() > 0) {
									PartWarrantyDetails	preWarrantyDetails	=			partWarrantyDetailsDao.getPartWarrantyDetailsById(partWarrantyDetails.getReplacePartWarrantyDetailsId(), userDetails.getCompany_id());
									detailsHistory	= PartWarrantyBL.getPartWarrantyHistoryDto(userDetails.getId(), preWarrantyDetails);
									detailsHistory.setHistoryTypeId(PartWarrantyBL.PART_WARRANTY_HISTORY_TYPE_DELETE);
									
									historyRepository.save(detailsHistory);
								}
							}
						}
						
						entityManager.createQuery(
								
								"Update PartWarrantyDetails SET partWarrantyStatusId = "+PartWarrantyDetailsBL.UNASIGNED+", "
										+ " status = "+PartWarrantyDetailsBL.UNASIGNED+", vid = 0 ,"
										+ "serviceId = 0 , assignedFrom = 0 , partAsignDate = NULL "
										+ " , servicePartId = 0, replacePartWarrantyDetailsId = 0 WHERE servicePartId = " + WORKTASKTOPARTS.getWorkordertaskto_partid() + "  "
										+ " AND companyId = " + userDetails.getCompany_id()+ " ").executeUpdate();
						
							updateAssignedNoOfPart(WORKTASKTOPARTS.getWorkordertaskto_partid() , - WORKTASKTOPARTS.getAssignedNoPart());
							
							entityManager.createQuery(
									"Update PartWarrantyDetails SET partWarrantyStatusId = "+PartWarrantyDetailsBL.PART_WARRANTY_STATUS_ASIGNED+" "
											+ ", replaceInServiceId = 0 , status = "+PartWarrantyDetailsBL.UNASIGNED+""
											+ " WHERE replaceInServiceId = " + WORKTASKTOPARTS.getWorkorders_id() + "  "
											+ " AND companyId = " + userDetails.getCompany_id()+ " ").executeUpdate();
					}
					
					object.put("partDeleted", true);
					
					partUsedQuantity =  WorkOrdersTasksToPartsRepository.countNumberOfPartsUsedFromThatInventory(WORKTASKTOPARTS.getInventory_id(), userDetails.getCompany_id());
					
					if(partUsedQuantity == null) {
					  partInvoiceRepository.update_anyPartNumberNotAsign_InPartInvoice(inventoryParts.getPartInvoiceId(), userDetails.getCompany_id()); 
					}
					 

				} else {
					object.put("NoAuthen", true);
				}
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		
		@Transactional
		@Override
		public ValueObject deletePartOfWorkOrdertaskDocument(ValueObject object) throws Exception {
				ValueObject objectOut =null;
				CustomUserDetails 				 	userDetails 						= null;
				List<org.fleetopgroup.persistence.document.WorkOTaskToPartDocument> docList = null;
				
			try {
				
				objectOut = new ValueObject();
					
				userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				workOTaskToPartDocumentService.deleteById(object.getLong("docId"), userDetails.getCompany_id());
				
				docList=workOTaskToPartDocumentService.getWorkOTaskToPartById(object.getLong("workordertaskto_partid"), userDetails.getCompany_id());
				objectOut.put("partDeleted", true);
				if(docList != null && !docList.isEmpty()) {
					
				}else{
					
					WorkOrdersTasksToPartsRepository.updateDocumentWorkOrdersTaskTOParts(false,object.getLong("workordertaskto_partid"), userDetails.getCompany_id());
				}
				
				return objectOut;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			
		}
		
		@Transactional
		@Override
		public ValueObject deleteLabourOfWorkOrdertask(ValueObject object) throws Exception {
			CustomUserDetails 				 	userDetails 						= null;
			WorkOrdersTasksToLabor 				WORKTASKTOLabor						= null;
			boolean                             isFromMob                           = false;
			WorkOrders                          work                                = null;
			try {
				isFromMob       = object.getBoolean("isFromMob",false);
				userDetails     = Utility.getObject(object);
				WORKTASKTOLabor = getWorkOrdersTaskToLabor_ONLY_ID(object.getLong("workordertaskto_laberid"), userDetails.getCompany_id());
				if(WORKTASKTOLabor != null) {
					if(isFromMob){
						 work = getWorkOrders(WORKTASKTOLabor.getWorkorders_id(),userDetails.getCompany_id());
					}else{
						 work = getWorkOrders(WORKTASKTOLabor.getWorkorders_id());
					}		
					
					if(work.getAccidentId() != null && work.getAccidentId() > 0) {
						VehicleAccidentDetails	accidentDetails	= accidentDetailsService.getVehicleAccidentDetailsById(work.getAccidentId());
						if(accidentDetails.getStatus() <= AccidentStatus.ACCIDENT_STATUS_QUOTATION_APPROVED) {
							accidentDetailsService.updateAccidentDetailsStatus(object.getLong("accidentId",0), AccidentStatus.ACCIDENT_STATUS_SERVEY_COMPLETED);
						}else {
							object.put("accidentEntryApproved", true);
							return object;
						}
					}
					deleteWorkOrdersTaskTOLabor(WORKTASKTOLabor.getWorkordertaskto_laberid(), userDetails.getCompany_id());
					
					updateWorkOrdersTask_TotalLaborCost(WORKTASKTOLabor.getWorkordertaskid(), userDetails.getCompany_id());
					
					if(isFromMob){
						updateWorkOrderMainTotalCost(WORKTASKTOLabor.getWorkorders_id(),userDetails.getCompany_id());
					}else{
					updateWorkOrderMainTotalCost(WORKTASKTOLabor.getWorkorders_id());
					}					
					object.put("LabourDetailsDeleted", true);
				}
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional
		@Override
		public ValueObject deleteWorkOrdertask(ValueObject object) throws Exception {
			CustomUserDetails 				 	userDetails 						= null;
			List<PartLocationPermissionDto> 	PartLocPermission					= null;
			WorkOrders 							work								= null;
			boolean                             isFromMob                           = false;
			try {
				isFromMob           = object.getBoolean("isFromMob",false);
				userDetails         = Utility.getObject(object);
				PartLocPermission   = partLocationsBL.prepareListofPartLocation(partLocationPermissionService
									.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

				work = getLast_WorkOrdersTaskID_To_WorkOrders_details(object.getLong("workordertaskid"), userDetails.getCompany_id());
				
				if(work.getAccidentId() != null && work.getAccidentId() > 0) {
					VehicleAccidentDetails	accidentDetails	= accidentDetailsService.getVehicleAccidentDetailsById(work.getAccidentId());
					if(accidentDetails.getStatus() <= AccidentStatus.ACCIDENT_STATUS_QUOTATION_APPROVED) {
						accidentDetailsService.updateAccidentDetailsStatus(object.getLong("accidentId",0), AccidentStatus.ACCIDENT_STATUS_SERVEY_COMPLETED);
					}else {
						object.put("accidentEntryApproved", true);
						return object;
					}
				}
				
				
				if (inventoryBL.isFilled_GET_Permission(work.getWorkorders_location_ID(), PartLocPermission)) {
					
					WorkOrdersTasks WORKTASK = getWorkOrdersTask(object.getLong("workordertaskid"), userDetails.getCompany_id());
					
					List<WorkOrdersTasksToParts> WorkOrdertasktoParts = getWorkOrdersTasksToParts_ID(object.getLong("workordertaskid"), userDetails.getCompany_id());
					
					if (WorkOrdertasktoParts != null && !WorkOrdertasktoParts.isEmpty()) {
						
						object.put("deletePartFirst", true);
						return object;
						
					} else {
						
						List<WorkOrdersTasksToLabor> WorkOrdertasktoLabor = getWorkOrdersTasksToLabor_ID(object.getLong("workordertaskid"), userDetails.getCompany_id());
						
						if (WorkOrdertasktoLabor != null && !WorkOrdertasktoLabor.isEmpty()) {
							
							object.put("deleteLobourFirst", true);
							return object;
							
						} else {
							
							deleteWorkOrdersTask(WORKTASK.getWorkordertaskid(), userDetails.getCompany_id());
							if(isFromMob){
								updateWorkOrderMainTotalCost(WORKTASK.getWorkorders().getWorkorders_id(),userDetails.getCompany_id());
							}else{
							updateWorkOrderMainTotalCost(WORKTASK.getWorkorders().getWorkorders_id());
							}
							
							
							object.put("taskDetailsDeleted", true);
						}
					}
					
				} else {
					object.put("NoAuthen", true);
				}
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional
		@Override
		public ValueObject updateWoGstCost(ValueObject object) throws Exception {
			CustomUserDetails 			userDetails 						= null;
			WorkOrders 					workTask							= null;
			Double 						TotalWorkOrdercost 					= WORK_ORDER_DEFALAT_AMOUNT;
			try {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				workTask    = getWorkOrders(object.getLong("workOrderId"));

				if (workTask != null) {
					
					if (workTask.getTotalworktax_cost() == null || workTask.getTotalworktax_cost() == 0) {

						Double TotalWorkOrdercost_DB = workTask.getTotalworkorder_cost();
						if (TotalWorkOrdercost_DB == null) {
							TotalWorkOrdercost_DB = WORK_ORDER_DEFALAT_AMOUNT;
						}
						TotalWorkOrdercost = TotalWorkOrdercost_DB + object.getDouble("gstCost");

					} else {
						Double TotalWorkOrdercost_DB = workTask.getTotalworkorder_cost();
						Double TotalWorkOrderTax_DB = workTask.getTotalworktax_cost();
						if (TotalWorkOrdercost_DB == null) {
							TotalWorkOrdercost_DB = WORK_ORDER_DEFALAT_AMOUNT;
						}
						Double TotalWorkOrderNowSubtrackAfter = WORK_ORDER_DEFALAT_AMOUNT;

						TotalWorkOrderNowSubtrackAfter = (TotalWorkOrdercost_DB - TotalWorkOrderTax_DB);
						TotalWorkOrdercost = TotalWorkOrderNowSubtrackAfter + object.getDouble("gstCost");
					}

					updateWorkOrderMainTotalCost_TAX(object.getDouble("gstCost"), round(TotalWorkOrdercost, 2), object.getLong("workOrderId"), userDetails.getCompany_id());
					
					object.put("gstCostUpdated", true);
				}
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional
		@Override
		public ValueObject changeWorkorderStatusToHold(ValueObject object) throws Exception {
			CustomUserDetails 			userDetails 			= null;
			WorkOrdersDto 				workOrders 				= null;
			HashMap<String, Object>  	configuration 			= null;
			try {
				userDetails         = Utility.getObject(object);
				workOrders  = getWorkOrdersDetails(object.getLong("workOrderId"), userDetails.getCompany_id());
				configuration =companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				object.put("userId",userDetails.getId());
				object.put("companyId",userDetails.getCompany_id());;
				if (workOrders != null) {
					WorkOrdersDto work = WOBL.Show_WorkOrders(workOrders);

					List<PartLocationPermissionDto> PartLocPermission = partLocationsBL.prepareListofPartLocation(partLocationPermissionService
							.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

					if (inventoryBL.isFilled_GET_Permission(work.getWorkorders_location_ID(), PartLocPermission)) {
						
						VehicleDto	vehicleStatus	=	vehicleService.Get_Vehicle_Current_Status_TripSheetID(workOrders.getVehicle_vid(), userDetails.getCompany_id());
						Date currentDateUpdate = new Date();
						Timestamp LastUpdate = new java.sql.Timestamp(currentDateUpdate.getTime());
						
						updateWorkOrderProcess(WorkOrdersType.WORKORDERS_STATUS_ONHOLD, userDetails.getId(), LastUpdate, object.getLong("workOrderId"), userDetails.getCompany_id());
						
						if((boolean) configuration.getOrDefault("addHoldRemark", false) && object.getString("woRemark", null) != null) {
							WorkOrderRemark orderRemark=	WorkOrdersBL.getWorkOrderRemarkDto(workOrders, object);
							orderRemark.setRemarkTypeId((short) 3);  // 1= completion 2 = Reopen 3 =Hold 4 =In-Process 5 = issue Remark
							orderRemarkRepository.save(orderRemark);
						}
						
						if(workOrders.getVehicleReOpenStatusId() > 0 && vehicleStatus.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE && vehicleStatus.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
							vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, work.getVehicle_vid(), 
									userDetails.getCompany_id(), workOrders.getVehicleReOpenStatusId());
						} else {
							vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, work.getVehicle_vid(), 
									userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);
						}

						object.put("statusChangedToHold", true);

					} else {
						object.put("NoAuthen", true);
					}
				}
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional
		@Override
		public ValueObject changeWorkorderStatusToInProgress(ValueObject object) throws Exception {
			CustomUserDetails 			userDetails 			= null;
			WorkOrdersDto 				workOrders 				= null;
			try {
				userDetails         = Utility.getObject(object);
				workOrders  = getWorkOrdersDetails(object.getLong("workOrderId"), userDetails.getCompany_id());
				HashMap<String, Object> configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				
				if (workOrders != null) {
					
					object.put("userId", userDetails.getId());
					object.put("companyId", userDetails.getCompany_id());
					VehicleDto	vehicleStatus	=	vehicleService.Get_Vehicle_Current_Status_TripSheetID(workOrders.getVehicle_vid(), userDetails.getCompany_id());
					WorkOrdersDto work = WOBL.Show_WorkOrders(workOrders);
					
					if(workOrders.getAccidentId() != null && workOrders.getAccidentId() > 0) {

						VehicleAccidentDetails	accidentDetails	= accidentDetailsService.getVehicleAccidentDetailsById(workOrders.getAccidentId());
						if(accidentDetails.getStatus() == AccidentStatus.ACCIDENT_STATUS_QUOTATION_CREATED || accidentDetails.getStatus() == AccidentStatus.ACCIDENT_STATUS_FINAL_SERVEY_FOR_DAMAGE || accidentDetails.getStatus() == AccidentStatus.ACCIDENT_STATUS_KEEP_OPEN || accidentDetails.getStatus() == AccidentStatus.ACCIDENT_STATUS_BEFORE_FINAL_APPROVAL){
							//need no action if contains these  status 
						}else if(accidentDetails.getStatus() < AccidentStatus.ACCIDENT_STATUS_FINAL_SERVEY_DONE) {
							accidentDetailsService.updateAccidentDetailsStatus(workOrders.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_QUOTATION_APPROVED);
						}else {
							object.put("accidentEntryApproved", "Cannot Re-Open Service Entry As Vehicle Accident Status is "+AccidentStatus.getStatusName(accidentDetails.getStatus()));
							return object;
						}
					}
					List<PartLocationPermissionDto> PartLocPermission = partLocationsBL.prepareListofPartLocation(partLocationPermissionService
							.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

					if (inventoryBL.isFilled_GET_Permission(work.getWorkorders_location_ID(), PartLocPermission)) {
						
						Date currentDateUpdate = new Date();
						Timestamp LastUpdate = new java.sql.Timestamp(currentDateUpdate.getTime());
						String issueIds="";
						if((boolean) configuration.getOrDefault("multiIssueInWO", false)) {
							issueIds =work.getIssueIds();
						}else {
							issueIds = Long.toString(work.getISSUES_ID());
						}
						if (work.getWorkorders_statusId() == WorkOrdersType.WORKORDERS_STATUS_COMPLETE) {
							if(issueIds != null &&  !issueIds.trim().equals("") && !issueIds.equals("0")) {
								String [] issueIdArr = issueIds.split(",");
								for(String issueId : issueIdArr) {
									if(!issueId.trim().equals("")) {
										Issues Save_Issues = new Issues();
										Save_Issues.setISSUES_ID(Long.parseLong(issueId));
										IssuesTasks WRTask = new IssuesTasks();

										WRTask.setISSUES_TASK_STATUS_ID(work.getWorkorders_statusId());
										WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_WOCREATED);
										WRTask.setISSUES_CREATEBY_ID(userDetails.getId());
										WRTask.setISSUES_CREATED_DATE(LastUpdate);
										WRTask.setISSUES(Save_Issues);
										WRTask.setCOMPANY_ID(userDetails.getCompany_id());
										WRTask.setISSUES_REASON("WorkOrder RE-OPEN Status ");

										issuesService.registerNew_IssuesTasks(WRTask);

										issuesService.update_Create_WorkOrder_Issues_Status(
												IssuesTypeConstant.ISSUES_STATUS_WOCREATED, userDetails.getId(), LastUpdate,
												work.getWorkorders_id(), Long.parseLong(issueId),userDetails.getCompany_id());
									}
								}
							}
						}
						
						
						ValueObject	ownerShipObject	= null;
						if(vehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED){
							ownerShipObject	= new ValueObject();
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, workOrders.getWorkorders_id());
							ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, workOrders.getVehicle_vid());
							ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, workOrders.getTotalworkorder_cost());
							ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
							ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_WORKORDER);
							ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, sqlFormat.parse(sqlFormat.format(currentDateUpdate)));
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "WorkOrder Entry");
							ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "WorkOrder Number : "+workOrders.getWorkorders_Number());
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
							ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
							ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, workOrders.getTotalworkorder_cost());
							
							vehicleAgentIncomeExpenseDetailsService.deleteVehicleAgentIncomeExpense(ownerShipObject);
						}
						
						if(vehicleStatus.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE && vehicleStatus.getvStatusId() != VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
							updateWorkOrderProcess(WorkOrdersType.WORKORDERS_STATUS_INPROCESS, userDetails.getId(), LastUpdate, object.getLong("workOrderId"), userDetails.getCompany_id(), vehicleStatus.getvStatusId());
						}else {
							updateWorkOrderProcess(WorkOrdersType.WORKORDERS_STATUS_INPROCESS, userDetails.getId(), LastUpdate, object.getLong("workOrderId"), userDetails.getCompany_id());
						}
						
						if(((boolean) configuration.get("addWOCompletionRemark") || (boolean) configuration.getOrDefault("addInprocessRemark", false))  && object.getString("woRemark", null) != null) {
							WorkOrderRemark	orderRemark	= WorkOrdersBL.getWorkOrderRemarkDto(workOrders, object);
							if(object.getBoolean("inPorocess", false)) {
								orderRemark.setRemarkTypeId((short) 4);  // 1= completion 2 = Reopen 3 =Hold 4 =In-Process 5 = issue Remark
							}else {
								orderRemark.setRemarkTypeId((short) 2);  // 1= completion 2 = Reopen 3 =Hold 4 =In-Process 5 = issue Remark
							}
							orderRemarkRepository.save(orderRemark);
						}
						
//						if(workOrders.getAccidentId() != null && workOrders.getAccidentId() > 0) {
//							accidentDetailsService.updateAccidentDetailsStatus(workOrders.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_QUOTATION_CREATED);
//						}
						
						vehicleService.Update_Vehicle_Status_TripSheetID(object.getLong("workOrderId"), work.getVehicle_vid(), 
								userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP);

						object.put("statusChangedToInProgress", true);
						VehicleExpenseDetails	vehicleExpenseDetails	= vehicleExpenseDetailsService.getVehicleExpenseDetails(workOrders.getWorkorders_id(), userDetails.getCompany_id() ,workOrders.getVehicle_vid(), VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
						
						if(vehicleExpenseDetails != null) {
							ValueObject		valueObject	= new ValueObject();
							valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
							valueObject.put("txnAmount", workOrders.getTotalworkorder_cost());
							valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(workOrders.getCompleted_dateOn()));
							valueObject.put("txnTypeId", workOrders.getWorkorders_id());
							valueObject.put("vid", workOrders.getVehicle_vid());
							valueObject.put("companyId", userDetails.getCompany_id());
							
							new Thread() {
								public void run() {
									try {
										vehicleProfitAndLossService.runThreadForDeleteVehicleExpenseDetails(valueObject);
										vehicleProfitAndLossService.runThreadForDeleteDateWiseVehicleExpenseDetails(valueObject);
										vehicleProfitAndLossService.runThreadForDeleteMonthWiseVehicleExpenseDetails(valueObject);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}		
							}.start();
						}
						
						
					} else {
						
						object.put("NoAuthen", true);
					}
					
				}
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional
		@Override
		public ValueObject changeWorkorderStatusToComplete(ValueObject woObject) throws Exception {
			CustomUserDetails 					userDetails 			= null;
			WorkOrdersDto 						workOrders 				= null;
			List<PartLocationPermissionDto> 	PartLocPermission 		= null;
			WorkOrdersDto 						work 					= null;
			ValueObject							ownerShipObject			= null;
			VehicleProfitAndLossTxnChecker		profitAndLossTxnChecker	= null;
			HashMap<String, Object>       		configuration			= null;
			HashMap<String, Object>   	        configurationOfIssue    = null;
			boolean                             allowedMobNotification  = false;
			try {
				userDetails         = Utility.getObject(woObject);
				configurationOfIssue	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
				allowedMobNotification  = (boolean) configurationOfIssue.getOrDefault(IssuesConfigurationContant.ALLOWED_MOB_NOTIFICATION, false);
				workOrders  = getWorkOrdersDetails(woObject.getLong("workOrderId"), userDetails.getCompany_id());
				configuration =companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				if (workOrders != null) {
					work 				= WOBL.Show_WorkOrders(workOrders);
					workOrders.setCompanyId(userDetails.getCompany_id());
					woObject.put("work", work);
					
					woObject.put("userId", userDetails.getId());
					woObject.put("companyId", userDetails.getCompany_id());
					
					PartLocPermission 	= partLocationsBL.prepareListofPartLocation(partLocationPermissionService.getPartLocationForPermissionCheck(userDetails.getId(), userDetails.getCompany_id()));

					if (inventoryBL.isFilled_GET_Permission(work.getWorkorders_location_ID(), PartLocPermission)) {
						Date currentDateUpdate = new Date();
						Timestamp COMPLETE_date = new java.sql.Timestamp(currentDateUpdate.getTime());
						if((boolean) configuration.get("workOrderTaskToPartDocument")) {
							if(findocumentAvailabilityByPartId(woObject.getLong("workOrderId"),(long)userDetails.getCompany_id())){
								woObject.put("DocMissing", true);
								return woObject;
							}
						}
						
						if(workOrders.getAccidentId() != null && workOrders.getAccidentId() > 0) {
							VehicleAccidentDetails	accidentDetails	= accidentDetailsService.getVehicleAccidentDetailsById(workOrders.getAccidentId());
							if(accidentDetails.getStatus() >= AccidentStatus.ACCIDENT_STATUS_QUOTATION_APPROVED) {
								updateWorkOrderCOMPLETE_date(WorkOrdersType.WORKORDERS_STATUS_COMPLETE, COMPLETE_date, userDetails.getEmail(), COMPLETE_date, woObject.getLong("workOrderId"), userDetails.getCompany_id());
								if((boolean) configuration.getOrDefault("multipleQuotation", false)){
									boolean complete= accidentDetailsService.checkAllServiceComplete(workOrders.getAccidentId(), userDetails.getCompany_id());
									if(complete)
										accidentDetailsService.updateAccidentDetailsStatus(workOrders.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_SERVICE_COMPLETED);
								}else {
									accidentDetailsService.updateAccidentDetailsStatus(workOrders.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_SERVICE_COMPLETED);
								}
							}else {
								woObject.put("quotationNotApproved", true);
								return woObject;
							}
						}else {
							updateWorkOrderCOMPLETE_date(WorkOrdersType.WORKORDERS_STATUS_COMPLETE, COMPLETE_date, userDetails.getEmail(), COMPLETE_date, woObject.getLong("workOrderId"), userDetails.getCompany_id());
						}
						if((boolean) configuration.get("addWOCompletionRemark") && woObject.getString("woRemark", null) != null) {
							WorkOrderRemark	orderRemark	= WorkOrdersBL.getWorkOrderRemarkDto(workOrders, woObject);	
							orderRemark.setRemarkTypeId((short) 1); //1= completion 2 = Reopen 3 =Hold 4 =In-Process 5 = issue Remark
							orderRemarkRepository.save(orderRemark);
						}
						
						if(workOrders.getTotalworkorder_cost() > 0) {
							
							ValueObject		object	= new ValueObject();
							
							object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
							object.put(VehicleProfitAndLossDto.TRANSACTION_ID, workOrders.getWorkorders_id());
							object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
							object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
							object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
							object.put(VehicleProfitAndLossDto.TRANSACTION_VID, workOrders.getVehicle_vid());
							object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, workOrders.getWorkorders_id());
							
							profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
							vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
							
							VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(workOrders.getVehicle_vid(), userDetails.getCompany_id()); 
							if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED ){
								ownerShipObject	= new ValueObject();
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, workOrders.getWorkorders_id());
								ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, workOrders.getVehicle_vid());
								ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, workOrders.getTotalworkorder_cost());
								ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
								ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_WORKORDER);
								ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, sqlFormat.parse(sqlFormat.format(currentDateUpdate)));
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "WorkOrder Entry");
								ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "WorkOrder Number : "+workOrders.getWorkorders_Number());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
								ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
								ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -workOrders.getTotalworkorder_cost());
								
								VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
								vehicleAgentTxnCheckerService.save(agentTxnChecker);
								
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
							}
							
						}
						if(workOrders.getAccidentId() == null || workOrders.getAccidentId() == 0) {
							if(workOrders.getVehicleReOpenStatusId() > 0) {
								vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, work.getVehicle_vid(), 
										userDetails.getCompany_id(), workOrders.getVehicleReOpenStatusId());
							}else {
								vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, work.getVehicle_vid(), 
										userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);
							}
						}
						
						WorkOrderTxnChecker	workOrderTxnChecker	= WOBL.getWorkOrderTxnChecker(workOrders);
						workOrderTxnCheckerService.saveWorkOrderTxnChecker(workOrderTxnChecker);

						// update Service Reminder into WorkOders
						sendEmailAndSMS(woObject);
						
						
						
						// Note : via Issues to Create WorkOrder To Resolves The values
						if (work.getWorkorders_id() != null && work.getWorkorders_id() != 0) {

							List<IssuesDto> WOISSUESS = issuesService.GET_WORKORDER_ID_TO_ISSUES_DETAILS(work.getWorkorders_id(), userDetails.getCompany_id());
									
							if (WOISSUESS != null && !WOISSUESS.isEmpty() ) {
								for(IssuesDto WOISSUES : WOISSUESS) {
								Issues Save_Issues = new Issues();
								Save_Issues.setISSUES_ID(WOISSUES.getISSUES_ID());
								IssuesTasks WRTask = new IssuesTasks();
								WRTask.setISSUES_TASK_STATUS_ID(WOISSUES.getISSUES_STATUS_ID());
								WRTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED);
								WRTask.setISSUES_CREATEBY_ID(userDetails.getId());
								
								java.util.Date currentDateUpdateIssues = new java.util.Date();
								Timestamp toDate = new java.sql.Timestamp(currentDateUpdateIssues.getTime());
								
								WRTask.setISSUES_CREATED_DATE(toDate);
								WRTask.setISSUES(Save_Issues);
								WRTask.setCOMPANY_ID(userDetails.getCompany_id());
								WRTask.setISSUES_REASON("WorkOrder To Issues Resovled");
								
								issuesService.registerNew_IssuesTasks(WRTask);

								issuesService.update_Create_WorkOrder_Issues_Status(
										IssuesTypeConstant.ISSUES_STATUS_RESOLVED, userDetails.getId(), toDate,
										work.getWorkorders_id(), WOISSUES.getISSUES_ID(),userDetails.getCompany_id());
								if(allowedMobNotification){
									issuesService.sendNotificationWhenIssueResolved(WOISSUES.getISSUES_ID(), userDetails.getId(), userDetails.getCompany_id());
								}
							}
						}
						}
						
						// Below code is commented because now this logic is written when the part is added in wo. No need to wait till WO is in Complete Status
						
						// To Update  anyPartNumberAsign column in PartInvoice Table hence Part wouldn't be edited in partInvoice.
//						List<WorkOrdersTasksToPartsDto> workOrdersTasksToPart = getWorkOrdersTasksToParts(woObject.getLong("workOrderId"), userDetails.getCompany_id());
//						if (workOrdersTasksToPart != null && !workOrdersTasksToPart.isEmpty()) {
//							
//							for (WorkOrdersTasksToPartsDto workOrdersTasksToPartInvoice : workOrdersTasksToPart) {
//								
//								Inventory inventory = inventoryService.getInventoryDetails(workOrdersTasksToPartInvoice.getInventory_id(), userDetails.getCompany_id());
//								if(inventory != null && inventory.getPartInvoiceId() != null) {
//									
//									Inventory inv = new Inventory();
//									
//										inv.setInventory_id(inventory.getInventory_id());
//										inv.setPartInvoiceId(inventory.getPartInvoiceId());
//										inv.setCompanyId(inventory.getCompanyId());
//										
//										inventoryService.update_anyPartNumberAsign_InPartInvoice(inv.getPartInvoiceId(), inv.getCompanyId());
//								}
//							}
//						}
						
						if(profitAndLossTxnChecker != null) {
							
							ValueObject	valueObject	= new ValueObject();
							valueObject.put("workOrders", workOrders);
							valueObject.put("userDetails", userDetails);
							valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
							valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
							valueObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
							
							new Thread() {
								public void run() {
									try {
										vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(valueObject);
										vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(valueObject);
										vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(valueObject);
									} catch (Exception e) {
										e.printStackTrace();
									}
								}		
							}.start();
						}
						
						if(ownerShipObject != null) {
							vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
						}
						woObject.put("vid", workOrders.getVehicle_vid());
						woObject.put("woOdometer", workOrders.getVehicle_Odometer());
						tyreUsageHistoryService.saveWOTyreUsageHistory(woObject);
						woObject.put("statusChangedToComplete", true);

					} else {
						woObject.put("NoAuthen", true);
					}
				}
				
				return woObject;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		public ValueObject sendEmailAndSMS(ValueObject woObject) throws Exception {
			CustomUserDetails 					userDetails 			= null;
			WorkOrdersDto 						work 					= null;
			List<EmailAlertQueue>  				pendingList				= null;
			List<SmsAlertQueue>  				pendingList1			= null;
			EmailAlertQueue 					email 					= null;
			SmsAlertQueue 						sms 					= null;
			List<WorkOrdersTasksDto> 			workOrdersTasks			= null;
			try {
				userDetails     = Utility.getObject(woObject);
				work  			= (WorkOrdersDto) woObject.get("work");
				workOrdersTasks = getWorkOrdersTasks(woObject.getLong("workOrderId"), userDetails.getCompany_id());
				
				if (workOrdersTasks != null && !workOrdersTasks.isEmpty()) {
					// many WorkOrdersTask and Update to that complete Time to service Reminder
					for (WorkOrdersTasksDto workOrdersTasksToServiceRC : workOrdersTasks) {

						List<ServiceReminder> serviceReminder = ServiceReminderService.listSearchWorkorderToServiceReminder(work.getVehicle_vid(),
										workOrdersTasksToServiceRC.getJob_typetaskId(), workOrdersTasksToServiceRC.getJob_subtypetask_id(), userDetails.getCompany_id());
										
						if (serviceReminder != null && !serviceReminder.isEmpty()) {
							
							for (ServiceReminder serviceReminderFindSameWorkorder : serviceReminder) {

								Date  serviceReminderDueDate = serviceReminderFindSameWorkorder.getTime_servicedate();
								
								ServiceReminder service = WOBL.WorkOrdersTOServiceReminder(serviceReminderFindSameWorkorder, work,userDetails);
								service.setCompanyId(userDetails.getCompany_id());
								ServiceReminderService.updateServiceReminder(service);
								
								ServiceReminderHistory srHistory = new ServiceReminderHistory();
								
								srHistory.setWorkorders_id(work.getWorkorders_id());
								srHistory.setService_reminderId(service.getService_id());
								srHistory.setCompanyId(userDetails.getCompany_id());
								srHistory.setCreated(new Date());	
								srHistory.setServiceType(WorkOrdersType.SERVICE_REMINDER_WO);
								srHistory.setServiceDueDate(serviceReminderDueDate);
								
								serviceReminderHistoryRepository.save(srHistory);
								
								pendingList	= emailAlertQueueService.getAllEmailAlertQueueDetailsById(serviceReminderFindSameWorkorder.getService_id());
								
								emailAlertQueueService.deleteEmailAlertQueue(serviceReminderFindSameWorkorder.getService_id());
								
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
										email.setServiceDate(service.getTime_servicedate());
										
											
										final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
										Calendar calendar1 = Calendar.getInstance();
										calendar1.setTime(service.getTime_servicedate());
										
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
											email1.setServiceDate(service.getTime_servicedate());
												
											final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
											Calendar calendar2 = Calendar.getInstance();
											calendar2.setTime(service.getTime_servicedate());
											if(emailAlertQueue.getAlertAfterValues() != null) {
												calendar2.add(Calendar.DAY_OF_YEAR, emailAlertQueue.getAlertAfterValues());
												java.util.Date alertDate1 = format2.parse(format2.format(calendar2.getTime()));
												java.util.Date alertAfterDate =  new Date(alertDate1.getTime());
												email1.setAlertAfterDate(alertAfterDate+"");
												email1.setAlertScheduleDate(alertAfterDate);
											}
										
											emailAlertQueueService.updateEmailAlertQueue(email1);
										  
									       }
									}	
								}	
								
								pendingList1	= smsAlertQueueService.getAllSmsAlertQueueDetailsById(serviceReminderFindSameWorkorder.getService_id());
								
								smsAlertQueueService.deleteSmsAlertQueue(serviceReminderFindSameWorkorder.getService_id());
								
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
										sms.setServiceDate(service.getTime_servicedate());
										
											
										final SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
										Calendar calendar1 = Calendar.getInstance();
										calendar1.setTime(service.getTime_servicedate());
										
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
											sms1.setServiceDate(service.getTime_servicedate());
												
											final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
											Calendar calendar2 = Calendar.getInstance();
											calendar2.setTime(service.getTime_servicedate());
											
											calendar2.add(Calendar.DAY_OF_YEAR, smsAlertQueue.getAlertAfterValues());
											java.util.Date alertDate1 = format2.parse(format2.format(calendar2.getTime()));
											java.util.Date alertAfterDate =  new Date(alertDate1.getTime());
											sms1.setAlertAfterDate(alertAfterDate+"");
											sms1.setAlertScheduleDate(alertAfterDate);
										
											smsAlertQueueService.updateSmsAlertQueue(sms1);
										  
									       }
									}	
								}	
								
								ServiceReminderWorkOrderHistory	orderHistory =	WOBL.getServiceWorkOrderDto(serviceReminderFindSameWorkorder, work, userDetails);
								ServiceReminderWorkOrderHistory	oldHistory   =	ServiceReminderService.getServiceWorkHistory(woObject.getLong("workOrderId"));
								if(oldHistory != null)
									orderHistory.setServiceReminderWorkOrderHistoryId(oldHistory.getServiceReminderWorkOrderHistoryId());
								
								orderHistoryRepository.save(orderHistory);
								
								ServiceReminderService.updateServiceReminder(service);
							}
						}

					}
				}
				
				return woObject;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional
		@Override
		public ValueObject uploadWorkOrderDocument(ValueObject object, MultipartFile file) throws Exception {
			CustomUserDetails 				 	userDetails 						= null;
			boolean                             isFromMob                           = false;
			String							    base64					        	= null;
			String							    imageName				        	= null;
			String							    imageExt					        = null;
			
			try {
				isFromMob           = object.getBoolean("isFromMob",false);
				base64				= object.getString("base64String",null);
				imageName			= object.getString("imageName",null);
				imageExt			= object.getString("imageExt",null);
				userDetails         = Utility.getObject(object);
				org.fleetopgroup.persistence.document.WorkOrdersDocument workDocument = new org.fleetopgroup.persistence.document.WorkOrdersDocument();
				
				workDocument.setWorkorder_id(object.getLong("workOrderId"));
				if(isFromMob){
					if (base64 != null) {
						byte[]   bytes = ByteConvertor.base64ToByte(base64);
						workDocument.setWorkorder_filename(imageName);
						workDocument.setWorkorder_content(bytes);
						workDocument.setWorkorder_contentType(imageExt);
					}
				}else{
				byte[] bytes = file.getBytes();
				workDocument.setWorkorder_filename(file.getOriginalFilename());
				workDocument.setWorkorder_content(bytes);
				workDocument.setWorkorder_contentType(file.getContentType());
				}
				
				workDocument.setCompanyId(userDetails.getCompany_id());

				java.util.Date currentDateUpdate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

				workDocument.setCreated(toDate);
				workDocument.setLastupdated(toDate);

				org.fleetopgroup.persistence.document.WorkOrdersDocument doc = getWorkOrdersDocument(object.getLong("workOrderId"),
						userDetails.getCompany_id());
				
				if (doc != null) {
					workDocument.set_id(doc.get_id());
					
					updateOldWorkOrdersDocument(workDocument);

					Update_WorkOrdre_Document_Available_TRUE(workDocument.get_id(), true, object.getLong("workOrderId"), userDetails.getCompany_id());
							
				} else {
					uploadWorkOrdersDocument(workDocument);

					Update_WorkOrdre_Document_Available_TRUE(workDocument.get_id(), true, object.getLong("workOrderId"), userDetails.getCompany_id());
				}

				object.put("UploadSuccess", true);
			
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Override
		public WorkOrdersDto getWorkOrdersLimitedDetails(long WorkOrders, Integer companyId) throws Exception {

			Query query = entityManager.createQuery(
					"SELECT WO.workorders_id, WO.workorders_Number"
							+ " from WorkOrders WO " 
							+ " where WO.workorders_id="
							+ WorkOrders + " AND WO.companyId = " + companyId + " AND WO.markForDelete = 0");

			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			WorkOrdersDto list;
			if (result != null) {
				list = new WorkOrdersDto();

				list.setWorkorders_id((Long) result[0]);
				list.setWorkorders_Number((Long) result[1]);
				
			} else {
				return null;
			}

			return list;
		}
		
		@Transactional
		@Override
		public ValueObject updateWorkOrderDetails(ValueObject object) throws Exception {
			CustomUserDetails 				userDetails 						= null;
			WorkOrdersDto 					Oldwork								= null;
			WorkOrders 						WorkOrd								= null;
			List<ServiceReminderDto>		serviceList							= null;
			VehicleDto 						CheckVehicleStatus 					= null;
			Integer                         currentODDMETER                     = null;
			boolean                         isFromMob                           = false;
			try {
				isFromMob   = object.getBoolean("isFromMob",false);
				userDetails = Utility.getObject(object);
				object.put("userDetails", userDetails);
				if(isFromMob){
					CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(object.getInt("vid"),userDetails.getCompany_id());
				}else{
				CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(object.getInt("vid"));
				}
				
				if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE) {
					object.put("inTripRoute", true);
					return object;
				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP && object.getShort("workOrdersStatusId") != WorkOrdersType.WORKORDERS_STATUS_OPEN) {
					object.put("inWorkShop", true);
					return object;
				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SOLD) {
					object.put("inSold", true);
					return object;
				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE) {
					object.put("inActive", true);
					return object;
				} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SURRENDER) {
					object.put("inSurrender", true);
					return object;
				}
				
				if (object.getString("woStartDate") != "" && object.getString("woStartDate") != null
						&& object.getString("location") != null && object.getInt("location") > 0) {

					Oldwork = WOBL.Show_WorkOrders(getWorkOrdersDetails(object.getLong("workOrderId"), userDetails.getCompany_id()));
					
					if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACCIDENT && !Oldwork.getVehicle_vid().equals(object.getInt("vid"))) {
						object.put("inAccident", true);
						return object;
					}
					
					WorkOrd = WOBL.updateWorkOrderDetails(object);
					
					if(Oldwork.getAccidentId() != null && Oldwork.getAccidentId() > 0 && (!Oldwork.getVehicle_vid().equals(WorkOrd.getVehicle_vid()) || Oldwork.getDriver_id() != object.getInt("driverId",0))) {
						object.put("changeAccidentDetails", true);
						return object;
					}
					
					WorkOrd.setWorkorders_statusId(Oldwork.getWorkorders_statusId());
					WorkOrd.setTotalsubworktask_cost(Oldwork.getTotalsubworktask_cost());
					WorkOrd.setTotalworktax_cost(Oldwork.getTotalworktax_cost());
					WorkOrd.setTotalworkorder_cost(Oldwork.getTotalworkorder_cost());
					WorkOrd.setWorkorders_document(Oldwork.isWorkorders_document());
					WorkOrd.setCreated(Oldwork.getCreatedOn());
					WorkOrd.setCreatedById(Oldwork.getCreatedById());
					if(object.getBoolean("multiIssueInWO",false)) {
						WorkOrd.setIssueIds(object.getString("issue",""));
					}else {
						WorkOrd.setISSUES_ID(object.getLong("issue",0));
					}
					addWorkOrders(WorkOrd);
					
					object.put("workOrder", WorkOrd);
					
					saveWorkOrderTasks(object);
					if(object.getString("serviceReminderId", null) != null) {
						HashMap<Integer ,ServiceReminderDto> dtosMap1 = getJobtypeAndSubtypeFromServiceId(Utility.removeLastComma(object.getString("serviceReminderId")), userDetails.getCompany_id());
						if(dtosMap1 != null && !dtosMap1.isEmpty()) {
							ArrayList<String>	addedTask =  getWorkOrderTaskLimitredDetails(WorkOrd.getWorkorders_id());
							saveWorkOrderTaskFromSProgram(dtosMap1, WorkOrd, userDetails, addedTask);
						}
					}

					object.put("workOrderUpdated", true);
					
					if(!Oldwork.getVehicle_vid().equals(WorkOrd.getVehicle_vid())) {
						vehicleService.Update_Vehicle_Status_TripSheetID(WorkOrd.getWorkorders_id(), WorkOrd.getVehicle_vid(), 
								userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP);
						
						vehicleService.Update_Vehicle_Status_TripSheetID((long)0, Oldwork.getVehicle_vid(), 
								userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);
					}
					if(isFromMob){
						currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(WorkOrd.getVehicle_vid(),userDetails.getCompany_id());
					}else{
						currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(WorkOrd.getVehicle_vid());
					}
					

					if (currentODDMETER!=null && currentODDMETER < WorkOrd.getVehicle_Odometer()) {
						
						vehicleService.updateCurrentOdoMeter(WorkOrd.getVehicle_vid(), WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
						ServiceReminderService.updateCurrentOdometerToServiceReminder(WorkOrd.getVehicle_vid(), WorkOrd.getVehicle_Odometer(), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(WorkOrd.getVehicle_vid(), userDetails.getCompany_id(), userDetails.getId());
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
								}
							}
						}

						VehicleOdometerHistory vehicleUpdateHistory = vehicleOdometerHistoryBL.prepareOdometerGetHistoryToWorkOrder(WorkOrd);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
					}

				} else {
					object.put("emptyWO", true);
				}
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional
		@Override
		public ValueObject deleteWorkOrderDetails(ValueObject object) throws Exception {
			CustomUserDetails 				 	userDetails 				= null;
			List<WorkOrdersTasksDto> 			WorkOrdertask				= null;
			Issues								issue						= null;
			IssuesTasks							issueTask					= null;
			boolean                             isFromMob                   = false;
			VehicleDto                          vehicle                     = null;
			HashMap<String,Object>				configuration				= null;
			try { 
				
				userDetails   = Utility.getObject(object);
				configuration =companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				isFromMob     = object.getBoolean("isFromMob",false);
				WorkOrdertask = getWorkOrdersTasks(object.getLong("workOrderId"), userDetails.getCompany_id());
				
				if (WorkOrdertask != null && !WorkOrdertask.isEmpty()) {
					object.put("woCannotBeDeleted", true);
					return object;
					
				} else {
					
					WorkOrders workorders = getWorkOrdersByCompanyId(object.getLong("workOrderId"),userDetails.getCompany_id());
				if(workorders != null) {
					if(workorders.getAccidentId() != null && workorders.getAccidentId() > 0) {
						//deleteQuotationDetails
						
						if((boolean) configuration.getOrDefault("multipleQuotation", false)){
							accidentDetailsService.deleteQuotationDetailsByservice(AccidentStatus.SERVICE_TYPE_WO, object.getLong("workOrderId"),userDetails.getCompany_id());
							List<AccidentQuotationDetails> quotationList=accidentDetailsService.getQuotationDetailsList(workorders.getAccidentId(), userDetails.getCompany_id());
							if(quotationList== null || quotationList.isEmpty())
								accidentDetailsService.updateAccidentDetailsStatus(workorders.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_SERVEY_COMPLETED);
						}else {
							accidentDetailsService.updateAccidentDetailsStatus(workorders.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_SERVEY_COMPLETED);
							accidentDetailsService.updateAccidentDetailsServiceDetails((long)0, (short)0, workorders.getAccidentId());
						}
					}else{
						vehicleService.Update_Vehicle_Status_TripSheetID(TRIP_SHEET_DEFALAT_VALUE, object.getInt("vid"), 
								userDetails.getCompany_id(), VehicleStatusConstant.VEHICLE_STATUS_ACTIVE);
					}
					
						java.util.Date currentDate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
						deleteWorkOrders(object.getLong("workOrderId"), userDetails.getId(), toDate, userDetails.getCompany_id());
						if(isFromMob){
							vehicle = vehicleService.Get_Vehicle_Current_Status_TripSheetID(workorders.getVehicle_vid(),userDetails.getCompany_id());
						}else{
							vehicle = vehicleService.Get_Vehicle_Current_Status_TripSheetID(workorders.getVehicle_vid());
						}
						if(vehicle.getVehicle_Odometer().equals(workorders.getVehicle_Odometer())) { 
							VehicleOdometerHistory odometerHistoryLsit	= vehicleOdometerHistoryService.getVehicleOdometerHistoryByVidExceptCurrentEntry(workorders.getWorkorders_id(),workorders.getVehicle_vid(), userDetails.getCompany_id());
							if(odometerHistoryLsit != null ) {
								if(odometerHistoryLsit.getVehicle_Odometer() < workorders.getVehicle_Odometer()) { //  found either pre entry or post entry
									vehicleService.updateCurrentOdoMeter(workorders.getVehicle_vid(), odometerHistoryLsit.getVehicle_Odometer(), userDetails.getCompany_id());
									vehicleOdometerHistoryService.deleteVehicleOdometerHistory(workorders.getWorkorders_id(), userDetails.getCompany_id());
								}
								
							}
							
						}
						
						if(workorders.getISSUES_ID() > 0 ) {
							issue 		= issuesService.getIssueDetailsByIssueId(workorders.getISSUES_ID(), userDetails.getCompany_id());
							if(issue != null) {
								issueTask 	= issuesBL.prepareIssuesTaskDetails(issue,userDetails);
								issueTask.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_STATUS_WOCREATED);
								issueTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_OPEN);
								issueTask.setISSUES_REASON("WO Deleted "); 
								issuesService.registerNew_IssuesTasks(issueTask);
								issuesService.update_Create_WorkOrder_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_OPEN, userDetails.getId(), issueTask.getISSUES_CREATED_DATE(), workorders.getWorkorders_id(), workorders.getISSUES_ID(),userDetails.getCompany_id());
								issuesService.update_WO_Issue_Details(workorders.getISSUES_ID(),userDetails.getCompany_id());
							}
						}
						
						if(workorders.getIssueIds() != null && !workorders.getIssueIds().trim().equals("") ){
							String[] array	=	workorders.getIssueIds().split(",");
							for (int i = 0; i < array.length; i++) {
								issue 		= issuesService.getIssueDetailsByIssueId(Long.parseLong(array[i]+""), userDetails.getCompany_id());
								if(issue != null) {
									issueTask 	= issuesBL.prepareIssuesTaskDetails(issue,userDetails);
									issueTask.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_STATUS_WOCREATED);
									issueTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_OPEN);
									issueTask.setISSUES_REASON("WO Deleted "); 
									issuesService.registerNew_IssuesTasks(issueTask);
									issuesService.update_Create_WorkOrder_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_OPEN, userDetails.getId(), issueTask.getISSUES_CREATED_DATE(), workorders.getWorkorders_id(), Long.parseLong(array[i]+""),userDetails.getCompany_id());
									issuesService.update_WO_Issue_Details(Long.parseLong(array[i]+""),userDetails.getCompany_id());
								}
							
							}
						}
						
						object.put("woDeleted", true);
					}else {
						object.put("woNotFound", true);
					}
				}
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional
		@Override
		public ValueObject getWorkOrderList(ValueObject object) throws Exception {
			CustomUserDetails 				 	userDetails 				= null;
			HashMap<String, Object>  			configuration				= null;
			List<WorkOrdersDto> 				pageList 					= null; 
			try {
				userDetails = Utility.getObject(object);
				configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				
				Page<WorkOrders> page = getDeployment_Page_WorkOrders(object.getShort("status"), object.getInt("pagenumber"), userDetails);
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());
				

				object.put("deploymentLog", page);
				object.put("beginIndex", begin);
				object.put("endIndex", end);
				object.put("currentIndex", current);
				object.put("WorkOrderCount", page.getTotalElements());
				
				if((boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_START_AND_DUE_TIME_FIELD, false)) {
					pageList = WOBL.prepareWorkOrdersList(listOpenWorkOrders(object.getShort("status"), object.getInt("pagenumber"), userDetails));				
				} else {
					pageList = WOBL.prepareListWorkOrders(listOpenWorkOrders(object.getShort("status"), object.getInt("pagenumber"), userDetails));
				}
				
				Object[] statusCount =	getWorkOrderCountByStatus(userDetails.getCompany_id(), userDetails.getId());

				object.put("WorkOrder", pageList);
				object.put("openWOCount", (Long) statusCount[1]);
				object.put("inProressWOCount", (Long) statusCount[2]);
				object.put("onHoldWOCount", (Long) statusCount[3]);
				object.put("completedWOCount", (Long) statusCount[0]);
				object.put("SelectStatus", object.getShort("status"));
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Override
		public ValueObject searchWoByNumber(ValueObject object) throws Exception {
			CustomUserDetails 				 	userDetails 				= null;
			WorkOrdersDto 						workOrder 					= null;
			try {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 				workOrder   = getWorkOrdersByNumber(object.getLong("woNumber",0), userDetails.getId(), userDetails.getCompany_id());
 				
				if(workOrder != null) {
					object.put("WorkOrderFound", true);
					object.put("WorkOrder", WOBL.Show_WorkOrders(workOrder));
					object.put("workOrderId", workOrder.getWorkorders_id());
				} else {
					object.put("WorkOrderNotFound", true);
				}
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Override
		public ValueObject searchWoByDifferentParameters(ValueObject object) throws Exception {
			CustomUserDetails 				 	userDetails 				        = null;
			List<WorkOrdersDto> 				workOrder 					        = null;
			String    					        issueIds				         	= null;
			List<IssuesDto>				        issueList					        = null;
			HashMap<String, Object>             configuration				        = null;
			WorkOrdersDto 				        workOrdersDto 				        = null;
			boolean                             allTaskCOmpleted					= false;
			try {
				userDetails     = Utility.getObject(object);
				workOrder       = WOBL.prepareListWorkOrders(SearchWorkOrders(object.getString("searchTerm"), userDetails.getCompany_id(), userDetails.getId()));
				configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			
				if(workOrder != null && !workOrder.isEmpty()) {
					
					workOrdersDto 	= getWorkOrdersDetails(workOrder.get(0).getWorkorders_id(), userDetails.getCompany_id());
					issueIds = workOrdersDto.getIssueIds();
					Boolean issueRemarkStatus = false;
					Boolean issueTaskStatus = false;
					if((boolean)configuration.getOrDefault("showIssueInWO", false) && issueIds != null && !issueIds.trim().equals("")) {
						issueList=issuesService.getWOIssueList(workOrder.get(0).getWorkorders_id(),issueIds,userDetails.getCompany_id());
						if(issueList != null && !issueList.isEmpty()) {
							for(IssuesDto dto:issueList) {
								if(dto.getWoRemark() == null || (dto.getWoRemark() != null && dto.getWoRemark().trim().equals(""))) 
									issueRemarkStatus = true;
								if(dto.getWorkOrderTaskId() == null || (dto.getWorkOrderTaskId() != null && dto.getWorkOrderTaskId() <= 0)) 
									issueTaskStatus = true;
								if(issueRemarkStatus && issueTaskStatus) 
									break;
							}
						}
					}
					List<WorkOrdersTasksDto> WorkOrderTask =WOBL.View_WorkOrder_Task_Details(getWorkOrdersTasks(workOrder.get(0).getWorkorders_id(), userDetails.getCompany_id()));					
					if(WorkOrderTask!= null && !WorkOrderTask.isEmpty()) {
						String completeDate = sqlFormat.format(workOrdersDto.getStart_dateOn())+" "+DateTimeUtility.DAY_END_TIME;
						String	query = " AND wo.completed_date < '"+completeDate+"' " ;
						
						for(WorkOrdersTasksDto wo : WorkOrderTask) {
							WorkOrders LastOcc =getLastWorkOrdersTaskToWorkOrdersdetails(wo.getJob_typetaskId(), wo.getJob_subtypetask_id(), wo.getVehicle_vid(), userDetails.getCompany_id(),workOrder.get(0).getWorkorders_id(),query);
							if (LastOcc != null) {
								wo.setLast_occurred_woId(LastOcc.getWorkorders_id());
								if (LastOcc.getStart_date() != null) {
									wo.setLast_occurred_date(dateFormat_Name.format(LastOcc.getStart_date()));
								}
								wo.setLast_occurred_odameter(LastOcc.getVehicle_Odometer());
							}else {
								wo.setLast_occurred_woId((long) 0);
							}
							if(wo.getMark_complete() == null || wo.getMark_complete() == 0) {
								allTaskCOmpleted	= false;
							}
						}
					}
					object.put("issueTaskStatus",issueTaskStatus);
					object.put("allTaskCOmpleted",allTaskCOmpleted);
					object.put("issueRemarkStatus", issueRemarkStatus);
					object.put("issueList", issueList);
					object.put("WorkOrderListFound", true);
					object.put("WorkOrder", workOrder);
				} else {
					object.put("WorkOrderListNotFound", true);
				}
				
				return object;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Override
		public ValueObject searchWorkOrderByData(ValueObject valueObject) throws Exception {
			ValueObject							dateRange				= null;		
			CustomUserDetails					userDetails				= null;
			List<WorkOrdersDto> 				workOrder 				= null;	
			int 								vid            			= 0 ;
			long								assigneeId				= 0;
			long								locationId				= 0;
			short								priority				= 0;
			String 								dateRangeFrom 			= "";
			String 								dateRangeTo 			= "";
			try {
				userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				vid				= valueObject.getInt("vid", 0);
				assigneeId		= valueObject.getLong("assign",0);
				locationId		= valueObject.getLong("location",0);
				priority		= valueObject.getShort("priority",(short)0);
				dateRange		= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"),DateTimeUtility.DD_MM_YYYY,DateTimeUtility.YYYY_MM_DD);
				dateRangeFrom 	= dateRange.getString(DateTimeUtility.FROM_DATE);
				dateRangeTo 	= dateRange.getString(DateTimeUtility.TO_DATE);
				
				if(dateRange != null) {
					
					String vehicleName = "" , assigned = "", location = "", priorityName = "";
					
					if (vid > 0) {
						vehicleName = " AND  WO.vehicle_vid = " + vid + " ";
					}

					if (assigneeId > 0) {
						assigned = " AND WO.assigneeId = " + assigneeId + " ";
					}

					if (locationId > 0) {
						location = " AND WO.workorders_location_ID = " + locationId + " ";
					}
					
					if (priority > 0) {
						priorityName = " AND WO.priorityId = " + priority + " ";
					}
										
					String query  = " "+vehicleName+" "+assigned+" "+location+" "+priorityName+" ";
								
					workOrder = WOBL.prepareListWorkOrders(ReportWorkOrders(query, dateRangeFrom, dateRangeTo, userDetails));
					
					valueObject.put("WorkOrder", workOrder);
				}
				
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			}finally {
				workOrder			= null;			
				userDetails			= null;
				dateRange			= null;
			}
		}
		
		@Override
		public List<DateWiseVehicleExpenseDto> getDateWiseWorkOrderExpenseDtoByVid(Integer vid, String fromDate,
				String toDate, Integer companyId) throws Exception {
			
			try {
				TypedQuery<Object[]> queryt = entityManager
						.createQuery("SELECT  SUM(MVE.totalworkorder_cost), MVE.companyId  "
								+ " FROM WorkOrders MVE "
								+ " where MVE.vehicle_vid = "+vid+" AND MVE.completed_date between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
										+ " AND MVE.markForDelete = 0 AND MVE.totalworkorder_cost > 0 ", Object[].class);
				List<Object[]>	results = queryt.getResultList();
				
				List<DateWiseVehicleExpenseDto>	list	= null;
				
				if(results != null && !results.isEmpty()) {
					list	=	new ArrayList<>();
					for (Object[] result : results) {
						DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
						
						mExpenseDto.setExpenseAmount((Double) result[0]);
						mExpenseDto.setExpenseType((short)3);
						mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
						
						if(result[0] != null)
							list.add(mExpenseDto);
					}
				}
				
				return list;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<MonthWiseVehicleExpenseDto> getMonthWiseWorkOrderExpenseDtoByVid(Integer vid, String fromDate,
				String toDate, Integer companyId) throws Exception {
			
			try {
				TypedQuery<Object[]> queryt = entityManager
						.createQuery("SELECT  SUM(MVE.totalworkorder_cost), MVE.companyId  "
								+ " FROM WorkOrders MVE "
								+ " where MVE.vehicle_vid = "+vid+" AND MVE.completed_date between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
										+ " AND MVE.markForDelete = 0 AND MVE.totalworkorder_cost > 0 ", Object[].class);
				List<Object[]>	results = queryt.getResultList();
				
				List<MonthWiseVehicleExpenseDto>	list	= null;
				
				if(results != null && !results.isEmpty()) {
					list	=	new ArrayList<>();
					for (Object[] result : results) {
						MonthWiseVehicleExpenseDto	mExpenseDto = new MonthWiseVehicleExpenseDto();
						
						mExpenseDto.setExpenseAmount((Double) result[0]);
						mExpenseDto.setExpenseType((short)3);
						mExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
						
						if(result[0] != null)
							list.add(mExpenseDto);
					}
				}
				
				return list;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<ServiceEntriesAndWorkOrdersDto> getVehicleRepairAndPartConsumptionDetailsInWO(ValueObject valueObject) throws Exception {
			CustomUserDetails						userDetails						= null;
			List<ServiceEntriesAndWorkOrdersDto>	workOrdersList					= null;
			ServiceEntriesAndWorkOrdersDto			workOrdersDto					= null;
			TypedQuery<Object[]> 					query 							= null;
			List<Object[]> 							results							= null;
			String									queryStr						= "";
			String									vehicleId						= "";
			String									vehicleIdStr					= "";
			long									vehicleGroup					= 0;
			String									vehicleGroupStr					= "";
			String									startDate						= "";
			String									endDate							= "";
			String									dateRange						= "";
			HashMap<Long, Double> 					laborHM							= null;
			try {
				userDetails					= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				workOrdersList				= new ArrayList<ServiceEntriesAndWorkOrdersDto>();
				laborHM						= new HashMap<Long, Double>();
				vehicleId					= valueObject.getString("vehicleId","");
				vehicleGroup				= valueObject.getLong("vehicleGroup",0);
				startDate					= valueObject.getString("startDate","");
				endDate						= valueObject.getString("endDate","")+DateTimeUtility.DAY_END_TIME;
				startDate = DateTimeUtility.getStrDateFromStrDate(startDate, DateTimeUtility.DD_MM_YYYY);
				endDate	= DateTimeUtility.getStrDateFromStrDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS);
				
				if(startDate != "" && endDate != "") {
					dateRange 		= " WO.due_date between '" +startDate+ "' AND '" +endDate+ "' ";
				}
				
				if(vehicleGroup > 0) {
					vehicleGroupStr = " AND V.vehicleGroupId IN("+vehicleGroup+")";
				}
				if(vehicleId != "") {
					vehicleIdStr 	= " AND WO.vehicle_vid IN ("+vehicleId+")";
				}
				
				
				queryStr		= " "+dateRange+" "+vehicleGroupStr+" "+vehicleIdStr+" ";
				
				
				query = entityManager.createQuery(
						"SELECT WO.workorders_id, WO.vehicle_vid, V.vehicle_registration, WO.vehicle_Odometer, WO.workorders_Number, "
						+ " WT.workordertaskid, JT.Job_Type, JST.Job_ROT, WTP.workordertaskto_partid, WTP.partid, "
						+ " MP.partname, MP.partnumber, WTP.quantity, WTP.parteachcost, WTP.totalcost, WT.totallaber_cost, WO.start_date, WO.due_date, WO.workorders_statusId  "
						+ " FROM WorkOrders AS WO " 
						+ " INNER JOIN Vehicle V ON V.vid = WO.vehicle_vid "
						+ " INNER JOIN WorkOrdersTasks WT ON WT.workorders.workorders_id = WO.workorders_id AND WT.markForDelete = 0 "
						+ " INNER JOIN JobType JT ON JT.Job_id = WT.job_typetaskId "
						+ " INNER JOIN JobSubType JST ON JST.Job_Subid = WT.job_subtypetask_id "
						+ " LEFT JOIN WorkOrdersTasksToParts WTP ON WTP.workordertaskid = WT.workordertaskid AND WTP.markForDelete = 0 "
						+ " LEFT JOIN MasterParts MP ON MP.partid = WTP.partid" 
						+ " where "+queryStr+" AND WO.companyId = " + userDetails.getCompany_id()
						+ " AND WO.markForDelete = 0", Object[].class);

				results = query.getResultList();

				if (results != null && !results.isEmpty()) {
					for (Object[] result : results) {
						workOrdersDto = new ServiceEntriesAndWorkOrdersDto();
						workOrdersDto.setTransactionId((Long) result[0]);
						workOrdersDto.setVid((Integer) result[1]);
						workOrdersDto.setVehicle_registration("<a href='showVehicle.in?vid="+workOrdersDto.getVid()+"' target='_blank'>"+(String) result[2]+"</a>");
						workOrdersDto.setVehicleOdometer((Integer) result[3]);
						workOrdersDto.setTransactionNumber("<a href='showWorkOrder?woId="+workOrdersDto.getTransactionId()+"' target='_blank'>WO-"+(Long) result[4]+"</a>");
						workOrdersDto.setTaskId((Long) result[5]);
						workOrdersDto.setJobType((String) result[6]);
						workOrdersDto.setJobSubType((String) result[7]);
						workOrdersDto.setTaskToPartId((Long) result[8]);
						workOrdersDto.setPartId((Long) result[9]);
						workOrdersDto.setPartName((String) result[10]);
						workOrdersDto.setPartNumber((String) result[11]);
						workOrdersDto.setPartQuantity((Double) result[12]);
						workOrdersDto.setPartEachCost((Double) result[13]);
						if((Double) result[14] != null) {
							workOrdersDto.setPartTotalCost((Double) result[14]);
						}else {
							workOrdersDto.setPartTotalCost(0.0);
						}
						if(laborHM.containsKey(workOrdersDto.getTaskId())) {
							workOrdersDto.setLaborTotalCost(0.0);
						}else {
							laborHM.put(workOrdersDto.getTaskId(), (Double) result[15]);
							if((Double) result[15] != null) {
								workOrdersDto.setLaborTotalCost((Double) result[15]);
							}else {
								workOrdersDto.setLaborTotalCost(0.0);
							}
						}
						workOrdersDto.setStartDateStr(sqldateTime.format((Timestamp) result[16]));
						workOrdersDto.setEndDateStr(sqldateTime.format((Timestamp) result[17]));
						
						workOrdersDto.setTotalRepairCost(workOrdersDto.getPartTotalCost()+workOrdersDto.getLaborTotalCost());
						workOrdersDto.setStatus(WorkOrdersType.getStatusName((short) result[18]));
						
						workOrdersList.add(workOrdersDto);
					}
				}
				
				
				
				
				return workOrdersList;
			} catch (Exception e) {
				LOGGER.error("Err"+e);
				throw e;
			}finally {
				userDetails						= null;     
				workOrdersDto					= null; 
				workOrdersList					= null;
			}
		}
		
		@Override
		public List<DateWiseVehicleExpenseDto> getDateWiseWorkOrderExpenseDtoVehicleTypeId(Integer vid, String fromDate,
				String toDate, Integer companyId, Long vehicleTypeId) throws Exception {
			return null;
		}
		
		@Override // you can change return type 
		public WorkOrders getWorkordersCreatedBetweenDates(String startDate, String endDate, Integer companyId) throws Exception {
						
			TypedQuery<WorkOrders> query = entityManager.createQuery(
						"SELECT W "
								+ " From WorkOrders as W "
								+ " WHERE W.created between '"+startDate+"' AND '"+endDate+"' AND W.companyId = "+companyId+" AND W.markForDelete = 0 "
								+ " Group by W.companyId ",WorkOrders.class);
			WorkOrders	workOrders = null;
			try {
					query.setMaxResults(1);
					workOrders = query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			return workOrders;
		}
		
		@Override
		@Transactional
		public ValueObject approveWorkOrderDetails(ValueObject object) throws Exception {
			
			try {
				if(object.getString("workorderId","") == "" ) {
					return object;
				}
				entityManager.createQuery("UPDATE  WorkOrders SET approvalStatusId = "+object.getShort("approvalStatusId", (short)0)+" "
						+ " where workorders_id IN("+object.getString("workorderId","") + ")").executeUpdate();
				
				object.put("success", true);

				return object;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

		public ValueObject getWorkOrderInitialDetails(ValueObject valueObject) throws Exception {
			ValueObject  		        model 				= new ValueObject();
			CustomUserDetails 			userDetails 		= null;
			HashMap<String, Object>     configuration		= null;
			HashMap<String, Object>     gpsConfiguration	= null;
			boolean						tallyConfig			= false;
			int							noOfBackDays		= 0;
			String 						minBackDate			= null; 
			Vehicle						vehicle					= null;
			int 						vid						= 0;
			long						issueId					= 0;
			String 						issue					= "0,0";
			boolean						showSubLocation			= false;
			String 						mainLocationIds			= null; 
			String 						serverDate			= null; 
			try {
				 userDetails        = Utility.getObject(valueObject);
				configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
				tallyConfig			= (boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.TALLY_COMPANY_MASTER_IN_WO, false);
				noOfBackDays		= (Integer) configuration.getOrDefault(WorkOrdersConfigurationConstants.NO_OF_BACK_DAYS, 0);
				minBackDate 		= DateTimeUtility.getDateBeforeNoOfDays(noOfBackDays);
				serverDate 		    = DateTimeUtility.getDateBeforeNoOfDays(0);
				showSubLocation	 	= (boolean)configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_SUB_LOCATION, false);
				mainLocationIds 	=  configuration.getOrDefault(WorkOrdersConfigurationConstants.MAIN_LOCATION_IDS, "")+"";
				vid 					= Integer.parseInt(issue.split(",")[0]);
				issueId 				= Integer.parseInt(issue.split(",")[1]);
				vehicle					= vehicleService.getVehicel(vid, userDetails.getCompany_id());
				model.put("issueId",issueId);
				model.put("vid",vid);
				if(vehicle != null) {
					model.put("vehicleReg",vehicle.getVehicle_registration());
				}
				String uniqueID = UUID.randomUUID().toString();
				model.put("accessToken", uniqueID);
				model.put("tallyConfig", tallyConfig);
				model.put("minBackDate",minBackDate);
				model.put("serverDate",serverDate);
				model.put("partLocationPermission", partLocationPermissionService.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id()));
				model.put("configuration", configuration);
				model.put("validateOdometerInWorkOrder", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_WORKORDER, false));
				model.put(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_WORKORDER,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_MIN_ODOMETER_IN_WORKORDER, false));
				model.put("gpsConfiguration", gpsConfiguration);
				model.put("companyId", userDetails.getCompany_id());
				model.put("showSubLocation", showSubLocation);
				model.put("mainLocationIds", mainLocationIds);
				return model;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				userDetails 		= null;
				configuration		= null;
				gpsConfiguration	= null;
			}
		}

		@Override
		public ValueObject getUserEmailId_Subscrible(ValueObject valueObject) throws Exception {
			List<UserProfileDto>      userNameList      = null;
			CustomUserDetails		  userDetails       = null;
			try {
				 userDetails  = Utility.getObject(valueObject);
				 userNameList = userProfileService.SearchUserEmail_id_and_Name(valueObject.getString("term",""), userDetails.getCompany_id());
				if (userNameList != null && !userNameList.isEmpty()) {
					valueObject.put("userNameList", userNameList);
				}
				return valueObject;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}finally{
				userNameList      = null;   
				userDetails       = null;   
			}
		}
		public ValueObject getVehicleOdoMeter(Integer companyId,Integer vehicleID) throws Exception {
			ValueObject  valueObject           = null;
			VehicleDto   wadd                  = null;
			try{
				valueObject     = new ValueObject();
				wadd            = new VehicleDto();
				if(vehicleID != null) {
					Vehicle oddmeter = vehicleService.getVehicel(vehicleID, companyId);
					if (oddmeter != null) {
						HashMap<String, Object> 	gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.VEHICLE_GPS_CONFIG);
						if((boolean) gpsConfiguration.get(VehicleGPSConfiguratinConstant.ALLOW_GPS_INTEGRATION)) {
							valueObject.put("companyId", companyId);
							valueObject.put("vehicleId", vehicleID);
							ValueObject	gpsObject	= vehicleGPSDetailsService.getVehicleGPSData(valueObject);
							if(gpsObject != null) {
								if(gpsObject.containsKey(GPSConstant.VEHICLE_TOTAL_KM_NAME)) {
									wadd.setGpsOdameter(gpsObject.getDouble(GPSConstant.VEHICLE_TOTAL_KM_NAME, 0));
									wadd.setGpsLocation(gpsObject.getString(GPSConstant.GPS_LOCATION_NAME));
								}
							}
						}
						wadd.setVehicle_Odometer(oddmeter.getVehicle_Odometer());
						wadd.setVehicle_ExpectedOdameter(oddmeter.getVehicle_ExpectedOdameter());
					}
				}
				valueObject.put("wadd",wadd);
				return valueObject;
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}finally{
				valueObject     = null;  
				wadd            = null;  
			}
		}

		public ValueObject getJobTypeWorkOrder(Integer companyId, String term) throws Exception {
			ValueObject    valueObjectOut      = null;
			List<JobType>  jobTypeList         = null;
			try{
				valueObjectOut  = new ValueObject();
				jobTypeList     = jobTypeService.SearchOnlyJobType(term, companyId);
				if (jobTypeList != null && !jobTypeList.isEmpty()) {
					valueObjectOut.put("jobTypeList", jobTypeList);
				}
				return valueObjectOut;
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}

		public ValueObject getJobSubTypeWorkOrder(Integer companyId, String term) throws Exception {
			ValueObject       valueObjectOut         = null;
			List<JobSubType>  subjobTypeList         = null;
			try{
				valueObjectOut  = new ValueObject();
				subjobTypeList   = jobSubTypeService.SearchJobSubType_ROT(term, companyId);
				if (subjobTypeList != null && !subjobTypeList.isEmpty()) {
					valueObjectOut.put("subjobTypeList", subjobTypeList);
				}
				return valueObjectOut;
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		public ValueObject getJobDetailsFromSubJobId(Integer JobSub_ID,Integer companyId) throws Exception {
			ValueObject       valueObjectOut         = null;
			JobSubTypeDto     obj                    = null;
			try{
				valueObjectOut  = new ValueObject();
				if(JobSub_ID > 0) {
					obj = jobSubTypeService.getJobSub_ID_Only_TypeName(JobSub_ID, companyId);
					if (obj != null) {
						valueObjectOut.put("jobTypeDetails", obj);
					}
				}
				return valueObjectOut;
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		
		public ValueObject showWorkOrderDetailsFromMob(ValueObject valueObjectIn) throws Exception {
			ValueObject                 valueObjectOut              = null;
			CustomUserDetails 			userDetails 				= null;
			WorkOrdersDto 				workOrdersDto 				= null;
			WorkOrdersDto 				workOrders 					= null;
			HashMap<String, Object>     configuration				= null;
			boolean						showCompanyWisePrint		= false;
			int							noOfBackDaysToReOpenWO		= 0;
			long						differenceBetTwoDate		= 0;
			boolean						reOpenWO					= false;
			boolean						showSubLocation				= false;
			long                        workOrderId                 = 0;
			List<IssuesDto>				issueList			        = null;
			String                      issueIds                    = "";
			boolean						allTaskCOmpleted  			= true;
			
			try {
				valueObjectOut  = new ValueObject();
				userDetails     = Utility.getObject(valueObjectIn);
				workOrderId     = valueObjectIn.getLong("workOrderId",0);
				configuration 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				showSubLocation	= (boolean)configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_SUB_LOCATION, false);
				workOrdersDto 	= getWorkOrdersDetails(workOrderId, userDetails.getCompany_id());
				if (workOrdersDto != null) {
					Boolean issueRemarkStatus = false;
					Boolean issueTaskStatus = false;
					issueIds        = workOrdersDto.getIssueIds();
					if((boolean)configuration.getOrDefault("showIssueInWO", false) && issueIds != null && !issueIds.trim().equals("")) {
						issueList=issuesService.getWOIssueList(workOrderId,issueIds,userDetails.getCompany_id());
						if(issueList != null && !issueList.isEmpty()) {
							for(IssuesDto dto:issueList) {
								if(dto.getWoRemark() == null || (dto.getWoRemark() != null && dto.getWoRemark().trim().equals(""))) 
									issueRemarkStatus = true;
								if(dto.getWorkOrderTaskId() == null || (dto.getWorkOrderTaskId() != null && dto.getWorkOrderTaskId() <= 0)) 
									issueTaskStatus = true;
								if(issueRemarkStatus && issueTaskStatus) 
									break;
							}
						}
					}
					List<WorkOrdersTasksDto> WorkOrderTask =WOBL.View_WorkOrder_Task_Details(getWorkOrdersTasks(workOrderId, userDetails.getCompany_id()));					
					if(WorkOrderTask!= null && !WorkOrderTask.isEmpty()) {
						String completeDate = sqlFormat.format(workOrdersDto.getStart_dateOn())+" "+DateTimeUtility.DAY_END_TIME;
						String	query = " AND wo.completed_date < '"+completeDate+"' " ;
						
						for(WorkOrdersTasksDto wo : WorkOrderTask) {
							WorkOrders LastOcc =getLastWorkOrdersTaskToWorkOrdersdetails(wo.getJob_typetaskId(), wo.getJob_subtypetask_id(), wo.getVehicle_vid(), userDetails.getCompany_id(),workOrderId,query);
							if (LastOcc != null) {
								wo.setLast_occurred_woId(LastOcc.getWorkorders_id());
								if (LastOcc.getStart_date() != null) {
									wo.setLast_occurred_date(dateFormat_Name.format(LastOcc.getStart_date()));
								}
								wo.setLast_occurred_odameter(LastOcc.getVehicle_Odometer());
							}else {
								wo.setLast_occurred_woId((long) 0);
							}
							if(wo.getMark_complete() == null || wo.getMark_complete() == 0) {
								allTaskCOmpleted	= false;
							}
						}
					}
					valueObjectOut.put("issueTaskStatus",issueTaskStatus);
					valueObjectOut.put("allTaskCOmpleted",allTaskCOmpleted);
					workOrders 		= WOBL.Show_WorkOrders(workOrdersDto);

					if((boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_START_AND_DUE_TIME_FIELD, false)) {
						DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm");
						if (workOrdersDto.getStart_dateOn() != null) {
							workOrders.setStart_date(ft.format(workOrdersDto.getStart_dateOn()));
						}
						if (workOrdersDto.getDue_dateOn() != null) {
							workOrders.setDue_date(ft.format(workOrdersDto.getDue_dateOn()));
						}
					}
					workOrders.setCompanyId(userDetails.getCompany_id());				
					valueObjectOut.put("WorkOrder", workOrders);
					valueObjectOut.put("configuration", configuration);
					valueObjectOut.put("showSubLocation", showSubLocation);

					if((boolean) configuration.get("showCompanyWisePrint")) {
						showCompanyWisePrint = true;
					}

					if((boolean) configuration.get("companyWisePrintOnClosedStatus")) {
						if(workOrders.getWorkorders_statusId() == WorkOrdersType.WORKORDERS_STATUS_COMPLETE){
							showCompanyWisePrint = false;
						}else {
							showCompanyWisePrint = true;
						}
					}
					valueObjectOut.put("companyWisePrint", showCompanyWisePrint);
					valueObjectOut.put("showJobTypeRemarkCol", configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_JOB_TYPE_REMARK_COL, false));
					valueObjectOut.put("showEditJobTypeRemark", configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_EDIT_JOB_TYPE_REMARK, false));
					valueObjectOut.put("WorkOrderTask", WOBL.View_WorkOrder_Task_Details(getWorkOrdersTasks(workOrderId, userDetails.getCompany_id())));
					valueObjectOut.put("WorkOrderTaskPart", getWorkOrdersTasksToParts(workOrderId, userDetails.getCompany_id()));
					valueObjectOut.put("WorkOrderTaskLabor",getWorkOrdersTasksToLabor(workOrderId, userDetails.getCompany_id()));
					valueObjectOut.put("accessPartToken", Utility.getUniqueToken(request));
					valueObjectOut.put("accessLabourToken", Utility.getUniqueToken(request));

					if (workOrders.getWorkorders_statusId() == WorkOrdersType.WORKORDERS_STATUS_ONHOLD) {
					}
					if (workOrders.getWorkorders_statusId() == WorkOrdersType.WORKORDERS_STATUS_COMPLETE) {
						if(workOrders.getCompleted_dateOn() != null) {
							noOfBackDaysToReOpenWO				= (Integer) configuration.getOrDefault(WorkOrdersConfigurationConstants.NO_OF_BACK_DAYS_TO_REOPEN_WO, 0);
							differenceBetTwoDate 				= DateTimeUtility.getExactDayDiffBetweenTwoDates(DateTimeUtility.getTimeStampFromDate(workOrders.getCompleted_dateOn()),DateTimeUtility.getCurrentTimeStamp());
						}
						if(differenceBetTwoDate < noOfBackDaysToReOpenWO) {
							reOpenWO	= true;
						}
						valueObjectOut.put("reOpenWO", reOpenWO);
					}
					valueObjectOut.put(WorkOrdersType.WORK_ORDER_STATUS, workOrders.getWorkorders_statusId());
					valueObjectOut.put("issueRemarkStatus", issueRemarkStatus);
					valueObjectOut.put("issueTaskStatus", issueTaskStatus);
					valueObjectOut.put("issueList", issueList);
				}
				return valueObjectOut;
			} catch (Exception e) {
				throw e;
			} finally {
				valueObjectOut              = null; 
				userDetails 				= null; 
				workOrdersDto 				= null; 
				workOrders 					= null; 
				configuration				= null; 
				showCompanyWisePrint		= false;
				noOfBackDaysToReOpenWO		= 0;    
				differenceBetTwoDate		= 0;    
				reOpenWO					= false;
				showSubLocation				= false;
				workOrderId                 = 0;    
			}
		}
		
		public ValueObject vehicleWiseWorkorderServiceDetails(ValueObject valueObjectIn)throws Exception {
			ValueObject                 valueObjectOut              = null;
			CustomUserDetails 			userDetails 				= null;
			int                         vehicleId                   = 0;
			try {
				
				vehicleId       = valueObjectIn.getInt("vehicleId",0);
				valueObjectOut  = new ValueObject();
				userDetails     = Utility.getObject(valueObjectIn);
				VehicleDto vehicle = vehicleBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(vehicleId,userDetails.getCompany_id()));
				valueObjectOut.put("vehicle", vehicle);
				valueObjectOut.put("WorkOrder",  WOBL.prepareListWorkOrders(
				VehicleToWorkOrdersList(vehicleId, userDetails.getCompany_id())));
				return valueObjectOut;
			} catch (Exception e) {
				throw e;
			}finally{
				 valueObjectOut              = null;    
				 userDetails 				 = null;    
				 vehicleId                   = 0;       
			}
		}
		public ValueObject getInvoiceWisePartList(ValueObject valIn) throws Exception {
			ValueObject                         valOut                                          = null;
			CustomUserDetails 					userDetails 									= null;
			List<InventoryDto> 					location 										= null;
			HashMap<String, Object> 			configuration 									= null;
			boolean 							showSubLocation	 								= false;
			boolean								showSublocationPartDetailForGivenMainLocation  	= false;
			String								mainLocationIds 								= "";
			String[] 							mainLocationIdsArr 								= null;
			String 								locationStr										= "";
			int                                 mainLocationId                                  = 0;
			int                                 subLocationId                                   = 0;
			String                              term                                            = "";
			try {
				mainLocationId      = valIn.getInt("mainLocationId",0);
				subLocationId       = valIn.getInt("subLocationId",0);
				term                = valIn.getString("term","");
				valOut              = new ValueObject();
				userDetails         = Utility.getObject(valIn);
				List<InventoryDto> 	finalList = new ArrayList<>() ;
				configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				showSubLocation	 	= (boolean)configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_SUB_LOCATION, false);
				mainLocationIds 	= configuration.getOrDefault(WorkOrdersConfigurationConstants.MAIN_LOCATION_IDS, "")+"";
				mainLocationIdsArr 	= mainLocationIds.split(",");

				if(showSubLocation && mainLocationIdsArr.length > 0) {
					for(int i=0; i<mainLocationIdsArr.length; i++) {
						if(mainLocationId == Integer.parseInt(mainLocationIdsArr[i])) {
							showSublocationPartDetailForGivenMainLocation = true;
							break;
						}
					}
				}
				if(showSublocationPartDetailForGivenMainLocation && subLocationId > 0) {
					locationStr = "AND R.locationId = "+mainLocationId+" AND R.subLocationId = "+subLocationId+"";
				}else {
					locationStr = "AND R.locationId = "+mainLocationId+" ";
				}
				location = inventoryService.getInvoiceWisePartList(term, locationStr, userDetails.getCompany_id());
				if (location != null && !location.isEmpty()) {
					for (InventoryDto add : location) {
						InventoryDto wadd = new InventoryDto();
						wadd.setQuantity(add.getQuantity());
						wadd.setInventory_id(add.getInventory_id());
						wadd.setPartInvoiceId(add.getPartInvoiceId());
						wadd.setLocation(add.getLocation());
						wadd.setLocationId(add.getLocationId());
						wadd.setPartname(add.getPartname());
						wadd.setPartnumber(add.getPartnumber());
						wadd.setMake(add.getMake());
						wadd.setInventory_location_id(add.getInventory_location_id());
						finalList.add(wadd);
					}
				}
				valOut.put("finalList", finalList);
				return valOut;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		public ValueObject getTechinicianWorkOrder(String term ,int companyId) throws Exception {
			ValueObject                         valOut                               = null;
			List<Driver>                        drivertypeList                       = null;
			try{
				valOut                 = new ValueObject();
				drivertypeList = driverService.SearchOnly_Techinicion_NAME(term, companyId);
				if (drivertypeList != null && !drivertypeList.isEmpty()) {
					valOut.put("technicianList", drivertypeList);
				}
				return valOut;
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		@Transactional(readOnly = true)
		public WorkOrders getWorkOrders(long WorkOrders,int companyId)throws Exception {
			try{
				return WorkOrdersDao.getWorkOrders(WorkOrders, companyId);
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		@Transactional
		public void updateWorkOrderMainTotalCost(Long WorkOrder_ID,int companyId) throws Exception {
			try{
				entityManager.createQuery(
						" UPDATE WorkOrders AS WO SET WO.totalsubworktask_cost=(SELECT COALESCE(ROUND(SUM(WOT.totaltask_cost),2),0) FROM WorkOrdersTasks AS WOT WHERE WOT.workorders.workorders_id="
								+ WorkOrder_ID + " AND WOT.companyId=" + companyId
								+ " AND WOT.markForDelete=0) ,"
								+ " WO.totalworkorder_cost = COALESCE(ROUND(WO.totalsubworktask_cost + WO.totalworktax_cost,2),0)  where WO.workorders_id="
								+ WorkOrder_ID + " AND WO.companyId=" + companyId
								+ " AND WO.markForDelete=0  ")
				.executeUpdate();
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		
		@Transactional(readOnly = true)
		public WorkOrders getWorkOrdersByCompanyId(long WorkOrders,int companyId)throws Exception {
			try{
				return WorkOrdersDao.getWorkOrders(WorkOrders, companyId);
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		public ValueObject getworkOrderEditInitialDetails(ValueObject valueObject)	throws Exception {
			HashMap<String, Object>     configuration		= null;
			HashMap<String, Object>     gpsConfiguration	= null;
			ValueObject 		        model 				= null;
			CustomUserDetails		 	userDetails 		= null;
			WorkOrdersDto 				workOrdersDto 		= null;
			WorkOrdersDto 				workOrdersDtoBL		= null;
			boolean						tallyConfig			= false;
			int							noOfBackDays		= 0;
			String 						minBackDate			= null; 
			boolean						showSubLocation		= false;
			String 						mainLocationIds		= null; 
			long                        Workorders_id       = 0;
			try {
				Workorders_id       = valueObject.getLong("woId",0);
				model 				= new ValueObject();
				userDetails         = Utility.getObject(valueObject);
				configuration 		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				gpsConfiguration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_GPS_CONFIG);
				tallyConfig			= (boolean) configuration.getOrDefault(WorkOrdersConfigurationConstants.TALLY_COMPANY_MASTER_IN_WO, false);
				noOfBackDays		= (Integer) configuration.getOrDefault(WorkOrdersConfigurationConstants.NO_OF_BACK_DAYS, 0);
				minBackDate 		= DateTimeUtility.getDateBeforeNoOfDays(noOfBackDays);
				showSubLocation	 	= (boolean)configuration.getOrDefault(WorkOrdersConfigurationConstants.SHOW_SUB_LOCATION, false);
				mainLocationIds 	=  configuration.getOrDefault(WorkOrdersConfigurationConstants.MAIN_LOCATION_IDS, "")+"";
				workOrdersDto 		= getWorkOrdersDetails(Workorders_id, userDetails.getCompany_id());
				workOrdersDtoBL 	= WOBL.Show_WorkOrders(workOrdersDto);

				model.put("minBackDate",minBackDate);
				model.put("WorkOrder", workOrdersDtoBL);
				if(workOrdersDtoBL.getISSUES_ID() > 0) {
					model.put("issueId", workOrdersDtoBL.getISSUES_ID());
				}
				model.put("tallyConfig", tallyConfig);
				model.put("configuration", configuration);
				model.put("gpsConfiguration", gpsConfiguration);
				model.put("companyId", userDetails.getCompany_id());
				model.put("vehicle", vehicleService.Get_Vehicle_Current_Status_TripSheetID(workOrdersDtoBL.getVehicle_vid(),userDetails.getCompany_id()));
				model.put("validateOdometerInWorkOrder", companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG).getOrDefault(VehicleConfigurationContant.VALIDATE_ODOMETER_IN_WORKORDER, false));
				model.put("showSubLocation", showSubLocation);
				model.put("mainLocationIds", mainLocationIds);
				model.put("serverDate", DateTimeUtility.getDateBeforeNoOfDays(0));
				return model;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		// get service reminder list drop down for particular vehicle
		public ValueObject getVehicleServiceReminderList(ValueObject valueObject) throws Exception {
			ValueObject                 valueObjectOut            =  null;
			CustomUserDetails		 	userDetails 		      =  null;
			int                         vehicleID                 =  0;
			try{
				vehicleID      = valueObject.getInt("vehicleID",0);
				valueObjectOut = new ValueObject();
				SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtility.DD_MM_YY);
				SimpleDateFormat sdf1 = new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD);
				
				userDetails         = Utility.getObject(valueObject);
				
				int noOfDay = companyConfigurationService.getServiceReminderListByDate(userDetails.getCompany_id(),
						PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				
				java.util.Date currentDate = new java.util.Date();
				String currDate = sdf1.format(currentDate);
				String dueSoon = DateTimeUtility.getDateAfterNoOfDays(noOfDay);
				Date parsedDate1 = sdf.parse(dueSoon);
				String newdueSoon = sdf1.format(parsedDate1);
				
				List<ServiceReminderDto> servReminder = new ArrayList<ServiceReminderDto>();
				List<ServiceReminderDto> servReminderList = ServiceReminderService.OnlyVehicleServiceReminderListByDate(vehicleID, currDate, newdueSoon,userDetails.getCompany_id());
				if (servReminderList != null && !servReminderList.isEmpty()) {
					for (ServiceReminderDto add : servReminderList) {
						ServiceReminderDto wadd = new ServiceReminderDto();
						wadd.setService_id(add.getService_id());
						wadd.setService_type(add.getService_type());
						wadd.setService_subtype(add.getService_subtype());
						wadd.setTime_servicedate(add.getTime_servicedate());
						wadd.setServceDate(sqldateTime.format(add.getTime_servicedate()));
						wadd.setServiceTypeId(add.getServiceTypeId());
						wadd.setServiceSubTypeId(add.getServiceSubTypeId());
						wadd.setService_Number(add.getService_Number());
						
						if(add.getWorkOrderNumberStr() != null) {
							wadd.setService_NumberStr(add.getWorkOrderNumberStr()+" : SR-"+add.getService_Number());
						}else {
							wadd.setService_NumberStr("SR-"+add.getService_Number());
						}
						
						
						servReminder.add(wadd);
						
						
					}
				}
				valueObjectOut.put("servReminderList",servReminder);
				return valueObjectOut;
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
		}
		@Override
		public Boolean findocumentAvailabilityByPartId(Long workOrderId,Long companyId) throws Exception {
			 HashMap<String, WorkOrdersTasksToParts> 	docHM	= null;
			 Boolean	photoPendingForAnyPart	= false;
			 List<WorkOrdersTasksToParts>  partIdList		= null;
			try {
				TypedQuery<Object[]> queryt = entityManager
						.createQuery("SELECT  MVE.partid , MVE.woPart_document , MVE.workordertaskid "
								+ " FROM WorkOrdersTasksToParts MVE "
								+ " where MVE.workorders_id = "+workOrderId+" AND companyId = "+ companyId +" AND MVE.markForDelete = 0 ", Object[].class);
				List<Object[]>	results = queryt.getResultList();
				
				WorkOrdersTasksToParts	workOrdersTasksToParts 	= null;
				WorkOrdersTasksToParts	temp 					= null;
				
				if(results != null && !results.isEmpty()) {
					docHM	= new HashMap<String, WorkOrdersTasksToParts>();
					partIdList	= new ArrayList<WorkOrdersTasksToParts>();
					for (Object[] result : results) {
						workOrdersTasksToParts	= new WorkOrdersTasksToParts();
						workOrdersTasksToParts.setPartid((Long) result[0]);
						workOrdersTasksToParts.setWoPart_document((boolean) result[1]);
						workOrdersTasksToParts.setWorkordertaskid((Long) result[2]);
						
						partIdList.add(workOrdersTasksToParts);
						
						temp	= docHM.get(workOrdersTasksToParts.getPartid()+"_"+workOrdersTasksToParts.getWorkordertaskid());
						if(temp == null ) {
							if(workOrdersTasksToParts.isWoPart_document()) {
								docHM.put(workOrdersTasksToParts.getPartid()+"_"+workOrdersTasksToParts.getWorkordertaskid(), workOrdersTasksToParts);
							}
						}
					}
				}
				if(partIdList == null) {
					return false;
				}
				for (WorkOrdersTasksToParts objects : partIdList) {
					if(docHM.get(objects.getPartid()+"_"+objects.getWorkordertaskid()) == null) {
						photoPendingForAnyPart = true;
						break;
					}
				}
				
				return photoPendingForAnyPart;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<WorkOrdersDto> getUserWiseWOActivityList(String query ,String innerQuery) throws Exception{
			
			
			List<WorkOrdersDto>        workOrderList			= null;
			try {
				workOrderList = new ArrayList<WorkOrdersDto>();
				
				TypedQuery <Object[]> queryCreate = entityManager.createQuery("SELECT w.workorders_id,w.workorders_Number,w.vehicle_vid,v.vehicle_registration ,w.start_date,w.due_date,w.created,w.lastupdated,w.createdById , "
						+ " w.lastModifiedById, U.firstName, U.lastName ,U.id ,w.markForDelete,w.totalworkorder_cost FROM WorkOrders as w "
						+ " INNER JOIN Vehicle v ON v.vid = w.vehicle_vid "
						+ " "+innerQuery +" "
						+ " "+query+" ",Object[].class);
				
				
				
				List<Object[]>	results = queryCreate.getResultList();
				
				
				if(results != null && !results.isEmpty()) {
				
					for(Object[] result : results ) {
					WorkOrdersDto workOrdersDto = new WorkOrdersDto();
						workOrdersDto.setWorkorders_id((Long) result[0]);
						workOrdersDto.setWorkorders_Number((Long) result[1]); 
						
						workOrdersDto.setVehicle_vid((Integer) result[2]);
						workOrdersDto.setVehicle_registration((String) result[3]);
						if(result[4] != null)
						workOrdersDto.setStart_date(sqldateTime.format(result[4]));
						if(result[5] != null)
						workOrdersDto.setDue_date(sqldateTime.format(result[5]));
						if(result[6] != null)
						workOrdersDto.setCreated(sqldateTime.format(result[6]));
						if(result[7] != null)
						workOrdersDto.setLastupdated(sqldateTime.format(result[7]));
						workOrdersDto.setCreatedById((Long) result[8]);
						workOrdersDto.setLastModifiedById((Long) result[9]);
						workOrdersDto.setUserName(result[10] +" "+result[11]);
						workOrdersDto.setUserId((Long)result[12]);
						workOrdersDto.setMarkForDelete((boolean) result[13]);
						if(!workOrdersDto.isMarkForDelete()) {
							workOrdersDto.setWorkorders_Numbers("<a target=\"_blank\" style=\"color: blue; background: #ffc;\"  href=\"showWorkOrder?woId="+workOrdersDto.getWorkorders_id()+" \"> WO-"+workOrdersDto.getWorkorders_Number()+" </a>");
						}else {
							workOrdersDto.setWorkorders_Numbers("<a  style=\"color: red; background: #ffc;\"  href=\"#\" data-toggle=\"tip\" data-original-title=\"Deleted Data\"> WO-"+workOrdersDto.getWorkorders_Number()+" </a>");
						}
						if(result[14]!= null) {
							workOrdersDto.setTotalworkorder_cost(Double.parseDouble(toFixedTwo.format(result[14])));
						}else {
							workOrdersDto.setTotalworkorder_cost(0.0);
						}
						workOrderList.add(workOrdersDto);
					}
					
				}
					return workOrderList;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			
		}
		
		@Override
		public Object[] getWorkOrderCountByStatus(Integer companyId, Long userId) throws Exception {
			Query queryt =	null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager
						.createQuery("SELECT COUNT(co.workorders_id),(SELECT  COUNT(P.workorders_id) FROM WorkOrders AS P where  P.workorders_statusId ="+WorkOrdersType.WORKORDERS_STATUS_OPEN+" AND P.companyId = "+companyId+" AND P.markForDelete = 0 ) ,"
								+ "(SELECT  COUNT(OD.workorders_id) FROM WorkOrders AS OD where OD.workorders_statusId ="+WorkOrdersType.WORKORDERS_STATUS_INPROCESS+" AND OD.companyId = "+companyId+" AND OD.markForDelete = 0 ), "
								+ "(SELECT  COUNT(OP.workorders_id) FROM WorkOrders AS OP where OP.workorders_statusId ="+WorkOrdersType.WORKORDERS_STATUS_ONHOLD+" AND OP.companyId = "+companyId+" AND OP.markForDelete = 0 ) "
								+ " FROM WorkOrders As co  "
								+ " where co.workorders_statusId  = "+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+" AND co.companyId = "+companyId+" AND co.markForDelete = 0 ");
			}else {
				queryt = entityManager
						.createQuery("SELECT COUNT(co.workorders_id),(SELECT  COUNT(P.workorders_id) FROM WorkOrders AS P "
								+ " INNER JOIN Vehicle V ON V.vid = P.vehicle_vid"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+userId+" "
								+ " where  P.workorders_statusId ="+WorkOrdersType.WORKORDERS_STATUS_OPEN+" AND P.companyId = "+companyId+" AND P.markForDelete = 0 ) ,"
								+ "(SELECT  COUNT(OD.workorders_id) FROM WorkOrders AS OD "
								+ " INNER JOIN Vehicle V2 ON V2.vid = OD.vehicle_vid"
								+ " INNER JOIN VehicleGroupPermision VGP2 ON VGP2.vg_per_id = V2.vehicleGroupId AND VGP2.user_id = "+userId+" "
								+ " where OD.workorders_statusId ="+WorkOrdersType.WORKORDERS_STATUS_INPROCESS+" AND OD.companyId = "+companyId+" AND OD.markForDelete = 0 ), "
								+ "(SELECT  COUNT(OP.workorders_id) FROM WorkOrders AS OP "
								+ " INNER JOIN Vehicle V3 ON V3.vid = OP.vehicle_vid"
								+ " INNER JOIN VehicleGroupPermision VGP3 ON VGP3.vg_per_id = V3.vehicleGroupId AND VGP3.user_id = "+userId+" "
								+ " where OP.workorders_statusId ="+WorkOrdersType.WORKORDERS_STATUS_ONHOLD+" AND OP.companyId = "+companyId+" AND OP.markForDelete = 0 ) "
								+ " FROM WorkOrders As co  "
								+ " INNER JOIN Vehicle V4 ON V4.vid = co.vehicle_vid"
								+ " INNER JOIN VehicleGroupPermision VGP4 ON VGP4.vg_per_id = V4.vehicleGroupId AND VGP4.user_id = "+userId+" "
								+ " where co.workorders_statusId  = "+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+" AND co.companyId = "+companyId+" AND co.markForDelete = 0 ");
			}
	 
			Object[] count = (Object[]) queryt.getSingleResult();

			return count;
		}
		
		@Override
		public ArrayList<String> getWorkOrderTaskLimitredDetails(Long workOrderId) throws Exception {
			
			ArrayList<String> addedTask	= null;
			try {
				
				TypedQuery <Object[]> queryCreate = entityManager.createQuery("SELECT w.job_typetaskId , w.job_subtypetask_id "
						+ " FROM WorkOrdersTasks as w where w.workorders.workorders_id = "+workOrderId+"",Object[].class);
				
				List<Object[]>	results = queryCreate.getResultList();
				
				
				if(results != null && !results.isEmpty()) {
					addedTask	= new ArrayList<String>();
					
					for(Object[] result : results ) {
						
						addedTask.add(result[0]+"_"+result[1]);
					}
					
				}
					return addedTask;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			
		}
		
		@Override
		public List<WorkOrderRemarkDto> getWorkOrderRemarkDtoList(Long orkOrderId) throws Exception {


			List<WorkOrderRemarkDto>        workOrderList			= null;
			try {
				workOrderList = new ArrayList<WorkOrderRemarkDto>();

				TypedQuery <Object[]> queryCreate = entityManager.createQuery("SELECT w.workOrderRemarkId ,w.remark ,w.remarkTypeId , w.driverId , w.assignee,"
						+ "  D.driver_firstname, D.driver_Lastname, D.driver_fathername, U.firstName, U.lastName,"
						+ " w.createdOn,I.ISSUES_NUMBER "
						+ " FROM WorkOrderRemark as w "
						+ " LEFT JOIN Driver D ON D.driver_id = w.driverId "
						+ " LEFT JOIN User U ON U.id = w.assignee"
						+ " LEFT JOIN Issues I ON I.ISSUES_ID = w.issueId"
						+ "  where w.workOrderId = "+orkOrderId+" and w.markForDelete = 0 "
						+ " ",Object[].class);
				List<Object[]>	results = queryCreate.getResultList();
				if(results != null && !results.isEmpty()) {

					for(Object[] result : results ) {
						WorkOrderRemarkDto workOrdersDto = new WorkOrderRemarkDto();
						workOrdersDto.setWorkOrderId(orkOrderId);
						workOrdersDto.setWorkOrderRemarkId((Long) result[0]);
						workOrdersDto.setRemark((String) result[1]);
						workOrdersDto.setRemarkTypeId((short) result[2]);
						workOrdersDto.setDriverId((Integer) result[3]);
						workOrdersDto.setAssigneeId((Long) result[4]);
						workOrdersDto.setDriverName((String) result[5]+"_"+(String) result[6]+"_"+(String) result[7]);
						workOrdersDto.setAssigneeName((String) result[8]+"_"+(String) result[9]);
						workOrdersDto.setCreatedOn(sqldateWithTime.format((Date)result[10]));
						workOrdersDto.setIssueNumber((Long) result[11]);
						workOrderList.add(workOrdersDto);
					}
				}
				return workOrderList;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Override
		public List<WorkOrderRemarkDto> getWorkOrderRemarkDtoOnHoldList(Long orkOrderId) throws Exception {


			List<WorkOrderRemarkDto>        workOrderList			= null;
			try {
				workOrderList = new ArrayList<WorkOrderRemarkDto>();

				TypedQuery <Object[]> queryCreate = entityManager.createQuery("SELECT w.workOrderRemarkId ,w.remark ,w.remarkTypeId , w.driverId , w.assignee,"
						+ "  D.driver_firstname, D.driver_Lastname, D.driver_fathername, U.firstName, U.lastName,"
						+ " w.createdOn,I.ISSUES_NUMBER, w.remarkTypeId "
						+ " FROM WorkOrderRemark as w "
						+ " LEFT JOIN Driver D ON D.driver_id = w.driverId "
						+ " LEFT JOIN User U ON U.id = w.assignee"
						+ " LEFT JOIN Issues I ON I.ISSUES_ID = w.issueId"
						+ "  where w.workOrderId = "+orkOrderId+" and w.markForDelete = 0 AND w.remarkTypeId = " +WorkOrdersType.WORKORDERS_STATUS_ONHOLD+ "  "
						+ " ",Object[].class);
				List<Object[]>	results = queryCreate.getResultList();
				if(results != null && !results.isEmpty()) {

					for(Object[] result : results ) {
						WorkOrderRemarkDto workOrdersDto = new WorkOrderRemarkDto();
						workOrdersDto.setWorkOrderId(orkOrderId);
						workOrdersDto.setWorkOrderRemarkId((Long) result[0]);
						workOrdersDto.setRemark((String) result[1]);
						workOrdersDto.setRemarkTypeId((short) result[2]);
						workOrdersDto.setDriverId((Integer) result[3]);
						workOrdersDto.setAssigneeId((Long) result[4]);
						workOrdersDto.setDriverName((String) result[5]+"_"+(String) result[6]+"_"+(String) result[7]);
						workOrdersDto.setAssigneeName((String) result[8]+"_"+(String) result[9]);
						workOrdersDto.setCreatedOn(sqldateWithTime.format((Date)result[10]));
						workOrdersDto.setIssueNumber((Long) result[11]);
						workOrderList.add(workOrdersDto);
					}
				}
				return workOrderList;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject markAllTaskCompleted(ValueObject object) throws Exception {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			WorkOrdersTasksRepository.markAllTaskCompleted(object.getLong("workOrderId",0), userDetails.getCompany_id());
			
			object.put("saved", true);
			
			return object;
		}
		
//		@Override
//		@Transactional
//		public ValueObject saveIssueRemark(ValueObject object) throws Exception {
//			
//			CustomUserDetails userDetails = null;
//			
//			try {
//				userDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//				if(object.getString("woIssueRemark",null) != null) {
//					
//				WorkOrderRemark	orderRemark = new  WorkOrderRemark();
//				orderRemark.setRemark(object.getString("woIssueRemark",""));
//				orderRemark.setWorkOrderId(object.getLong("workOrderId",0));
//				orderRemark.setCreatedOn(new Date());
//				orderRemark.setCreatedById(userDetails.getId());
//				orderRemark.setCompanyId(userDetails.getCompany_id());
//				orderRemark.setDriverId(object.getInt("driverId",0));
//				orderRemark.setAssignee(object.getLong("assignee",0));
//				orderRemark.setMarkForDelete(false);
//				orderRemark.setRemarkTypeId((short) 6);
//				orderRemark.setIssueId(object.getLong("issueIdRemark",0));
//				orderRemarkRepository.save(orderRemark);
//				object.put("saved", true);
//				}else {
//					object.put("notSaved", true);
//				}
//				
//				return object;
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//				throw e;
//			}
//		}
		
		@Override
		@Transactional
		public void updateAssignedNoOfPart(Long woTaskId, int noOfPart) throws Exception {
			entityManager.createQuery(
					"Update WorkOrdersTasksToParts SET assignedNoPart = assignedNoPart + "+noOfPart+" "
							+ " WHERE workordertaskto_partid = " + woTaskId +  " ").executeUpdate();
		}
		
		@Override
		public WorkOrdersTasksToParts validateAssignedNoOfParts(Long woTaskId) throws Exception {
		
			return WorkOrdersTasksToPartsRepository.validateAssignedNoOfParts(woTaskId);
		}
		
		@Override
		@Transactional
		public WorkOrdersDto getWorkOrdersTasksByTaskId(long WorkOrderTaskId, Integer companyId) {
			TypedQuery<Object[]> query = null;
			WorkOrdersDto dto = null;
			query = entityManager.createQuery(
					"SELECT WT.workordertaskid, WO.workorders_id, WO.workorders_Number, WO.vehicle_vid, V.vehicle_registration, V.vehicleModalId "
							+ " From WorkOrdersTasks WT "
							+ " INNER JOIN WorkOrders AS WO ON WO.workorders_id = WT.workorders.workorders_id "
							+ " INNER JOIN Vehicle AS V ON V.vid = WO.vehicle_vid "
							+ " where WT.workordertaskid = " + WorkOrderTaskId + " AND WT.companyId = " + companyId
							+ " AND WT.markForDelete = 0 ",
							Object[].class);
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			if (result != null) {
				dto = new WorkOrdersDto();

				dto.setWorkordertaskid((Long) result[0]);
				dto.setWorkorders_id((Long) result[1]);
				dto.setWorkorders_Number((Long) result[2]);
				dto.setVehicle_vid((Integer) result[3]);
				dto.setVehicle_registration((String) result[4]);
				dto.setVehicleModelId((Long) result[5]);
			}
			return dto;

		}
		
		@Transactional
		public ValueObject markAllTaskCompletedFromMob(ValueObject object) throws Exception {
			CustomUserDetails             userDetails    = null;
			try {
				userDetails     = Utility.getObject(object);
				WorkOrdersTasksRepository.markAllTaskCompleted(object.getLong("workOrderId",0), userDetails.getCompany_id());
				object.put("saved", true);
				return object;
			}catch(Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@SuppressWarnings("null")
		@Override
		public ValueObject getWorkOrderAndDseDetails(ValueObject valueObject) throws Exception {
			
			CustomUserDetails				 userDetails		   = null;
			List<WorkOrdersDto> 			 IssueWO		       = null;
			List<WorkOrdersDto> 			 IssueDSE			   = null;
			List<WorkOrdersDto> 			 ServiceReminderWO	   = null;
			List<WorkOrdersDto> 	 		 ServiceReminderDSE	   = null;
			
			List<WorkOrdersDto> 			FinalList 			   = null;
			
			
			try
			{
				userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				String dateRange       = valueObject.getString("dateRange");
				int workOrderTypeId  = valueObject.getInt("workOrderTypeId",0);
				
				if(dateRange != null) {
					
					String dateRangeFrom = "", dateRangeTo = "";
					String[] From_TO_DateRange = null;

					From_TO_DateRange = dateRange.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					
					if(workOrderTypeId >0 && workOrderTypeId==WorkOrdersType.ISSSUES_REGARDING_WO )
						IssueWO 	= IssueRegardingWo(valueObject, userDetails.getCompany_id(),dateRangeFrom, dateRangeTo );
					if(workOrderTypeId > 0 && workOrderTypeId==WorkOrdersType.ISSSUES_REGARDING_DSE)
						IssueDSE = IssueRegardingDSEReport(valueObject, userDetails.getCompany_id(),dateRangeFrom, dateRangeTo);
					if(workOrderTypeId > 0 && workOrderTypeId==WorkOrdersType.SERVICE_REMINDER_WO)
						ServiceReminderWO = ServiceReminedrRegardingWO(valueObject, userDetails.getCompany_id(),dateRangeFrom, dateRangeTo);
					if(workOrderTypeId > 0 && workOrderTypeId==WorkOrdersType.SERVICE_REMINDER_DSE)
						ServiceReminderDSE = ServiceReminedrRegardingDSE(valueObject, userDetails.getCompany_id(),dateRangeFrom, dateRangeTo);
					
					if(workOrderTypeId == 0)
					{
						IssueWO = IssueRegardingWo(valueObject, userDetails.getCompany_id(), dateRangeFrom, dateRangeTo);
						IssueDSE = IssueRegardingDSEReport(valueObject, userDetails.getCompany_id(),dateRangeFrom, dateRangeTo);
						ServiceReminderWO = ServiceReminedrRegardingWO(valueObject, userDetails.getCompany_id(),dateRangeFrom, dateRangeTo);
					    ServiceReminderDSE = ServiceReminedrRegardingDSE(valueObject, userDetails.getCompany_id(),dateRangeFrom, dateRangeTo);
					}
				}
				
				if(workOrderTypeId >0)
				{
					if(IssueWO != null && !IssueWO.isEmpty()){
						valueObject.put("IssuesWo", IssueWO);
						valueObject.put("IsIssuesWoList", "true");
					}	
					if(IssueDSE!=null && !IssueDSE.isEmpty()){
						valueObject.put("IssueDse" , IssueDSE);
					    valueObject.put("IsIssuesDseList", "true");
					}
					if(ServiceReminderWO!=null && !ServiceReminderWO.isEmpty()){
						valueObject.put("ServiceReminderWo", ServiceReminderWO);
					    valueObject.put("IsSRWoList", "true");
					}
					
					if(ServiceReminderDSE != null && !ServiceReminderDSE.isEmpty()){
					   valueObject.put("ServiceReminderDse", ServiceReminderDSE);
					   valueObject.put("IsSRDseList", "true");
					}
				}
				else
				{
					
					FinalList = new ArrayList<>();
					
					if(IssueWO != null && !IssueWO.isEmpty() || IssueDSE != null && !IssueDSE.isEmpty() ||
					   ServiceReminderWO != null && !ServiceReminderWO.isEmpty() || ServiceReminderDSE != null && !ServiceReminderDSE.isEmpty())
					{
						if(IssueWO != null && !IssueWO.isEmpty()){
							FinalList.addAll(IssueWO);
						}
						if(IssueDSE != null && !IssueDSE.isEmpty()){
							FinalList.addAll(IssueDSE);
						}
						if(ServiceReminderWO != null && !ServiceReminderWO.isEmpty()){
							FinalList.addAll(ServiceReminderWO);
						}
						if(ServiceReminderDSE != null && !ServiceReminderDSE.isEmpty()){
							FinalList.addAll(ServiceReminderDSE);
						}
					}
					
					if(FinalList != null && !FinalList.isEmpty())
					{
						valueObject.put("FinalList", FinalList);
						valueObject.put("IsFinalList", "true");
					}
				}
			}
			catch (Exception e) {
				LOGGER.error("Exception", e);
				throw e;
			}finally {
				IssueWO		= null;
				userDetails			= null;
			}
			
			return valueObject;
		}	
		
		public List<WorkOrdersDto> IssueRegardingWo(ValueObject valueObject, Integer companyId, String dateRangeFrom, String dateRangeTo)
		{
			TypedQuery<Object[]> queryt = null;
			
			StringBuffer query = new StringBuffer();
			
			if(valueObject.getString("vid","0") != "0")
			{
				if(valueObject.getInt("workOrderTypeId",0) > 0 && valueObject.getInt("workOrderTypeId",0)==WorkOrdersType.ISSSUES_REGARDING_WO)
				    query.append(" AND w.vehicle_vid in ("+valueObject.getString("vid","0")+")  ");
			}
			if(valueObject.getInt("VehicleGroupId",0) > 0)
				query.append("AND v.vehicleGroupId = "+valueObject.getInt("VehicleGroupId",0)+" ");
			if(valueObject.getInt("VehicleTypeId",0) > 0)
				query.append("AND v.vehicleTypeId = "+valueObject.getInt("VehicleTypeId",0)+" ");
			if(valueObject.getInt("vLocationId",0) > 0)
				query.append("AND w.workorders_location_ID = "+valueObject.getInt("vLocationId",0)+" ");
			
			query.append("AND w.start_date between '" + dateRangeFrom + "' AND '" + dateRangeTo +"'");
			
			
			try {
				queryt = entityManager.createQuery(
						"SELECT  w.workorders_id, w.workorders_Number, w.start_date, w.vehicle_vid, w.vehicle_Odometer, v.vehicle_registration, "
						+ " w.start_date, jt.Job_Type, Jst.Job_ROT, mp.partnumber, mp.partname, wtp.quantity ,"
						+ " d.driver_firstname, d.driver_Lastname, I.CREATED_DATE, pc.pcName, I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_SUMMARY, PL.partlocation_name "
						+ " FROM Issues AS I "
						+ " INNER JOIN WorkOrders AS w on w.workorders_id = I.ISSUES_WORKORDER_ID " 
						+ " INNER JOIN Vehicle AS v ON v.vid = w.vehicle_vid "
						+ " LEFT JOIN WorkOrdersTasks AS wt on  wt.workorders.workorders_id = w.workorders_id AND wt.markForDelete = 0 "
						+ " LEFT JOIN JobType AS jt on jt.Job_id = wt.job_typetaskId "
						+ " LEFT JOIN JobSubType AS Jst on Jst.Job_Subid = wt.job_subtypetask_id "
						+ " LEFT JOIN WorkOrdersTasksToParts AS wtp on wtp.workordertaskid = wt.workordertaskid AND wtp.markForDelete = 0"
						+ " LEFT JOIN MasterParts AS mp on mp.partid = wtp.partid "
						+ " INNER JOIN PartLocations  AS PL on PL.partlocation_id = w.workorders_location_ID"
						+ " LEFT JOIN Inventory R ON R.inventory_id = wtp.inventory_id "
						+ " LEFT JOIN WorkOrdersTasksToLabor AS wtl on wtl.workordertaskid = wt.workordertaskid AND wtl.markForDelete = 0"
						+ " LEFT JOIN Driver AS d on d.driver_id = wtl.laberid "
						+ " LEFT JOIN PartCategories as pc ON pc.pcid = I.categoryId "
						+ " where w.companyId = " + companyId +" "+ query + "AND w.markForDelete = 0 AND I.markForDelete=0" ,
						Object[].class);
				
				List<Object[]> results = queryt.getResultList();
				int temp=0;
				List<WorkOrdersDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					
					Dtos = new ArrayList<WorkOrdersDto>();
					
					WorkOrdersDto Dto = null;
					
					for (Object[] result : results) {
						Dto = new WorkOrdersDto();
						temp++;
						
						Dto.setWoType(1);
						if(result[14]!=null)
						  Dto.setIssueCreated(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[14]));
						
						Dto.setWorkorders_id((Long) result[0]);
						Dto.setWorkorders_Number((Long) result[1]);
						
						if(result[2]!=null)
							Dto.setCreated(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[2]));
						Dto.setVehicle_vid((int) result[3]);
						Dto.setVehicle_Odometer((int)result[4]);
						Dto.setVehicle_registration((String) result[5]);
						Dto.setIssueSummary((String) result[18]);
						Dto.setWorkLocation((String) result[19]);
						
						if(result[7]!=null)
							Dto.setJob_Type((String) result[7]);
						else
							Dto.setJob_Type("-");
						if(result[8]!=null)
							Dto.setSubType((String) result[8]);
						else
							Dto.setSubType("-");
						if(result[9]!=null)
							Dto.setPartNo((String)result[9]);
						if(result[10]!=null)
							Dto.setPartName((String)result[10]);
						if(result[11]!=null)
							Dto.setPartQt((Double)result[11]);
						
						if(result[12]!=null && result[13]!=null)
							Dto.setLabername(result[12] +" " + result[13]);
						
						Dto.setIssueCategory((String) result[15]);
						
						Dtos.add(Dto);
					}
				}
				return Dtos;
			} catch (Exception e) {
				throw e;
			} finally {
				queryt = null;
			}
			
		}
		
		public List<WorkOrdersDto> IssueRegardingDSEReport(ValueObject valueObject, Integer companyId, String dateRangeFrom, String dateRangeTo)
		{
			TypedQuery<Object[]> queryt = null;
			
			StringBuffer query = new StringBuffer();
			
			if(valueObject.getString("vid","0") != "0")
			{
				if(valueObject.getInt("workOrderTypeId",0) > 0 && valueObject.getInt("workOrderTypeId",0)==WorkOrdersType.ISSSUES_REGARDING_DSE)
					query.append(" AND D.vid in ("+valueObject.getString("vid","0")+")  ");
			}
			
			if(valueObject.getInt("VehicleGroupId",0) > 0)
				query.append("AND v.vehicleGroupId = "+valueObject.getInt("VehicleGroupId",0)+" ");
			if(valueObject.getInt("VehicleTypeId",0) > 0)
				query.append("AND v.vehicleTypeId = "+valueObject.getInt("VehicleTypeId",0)+" ");
			
			query.append("AND D.invoiceDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"'");
			
			try {
				queryt = entityManager.createQuery(
						"SELECT I.CREATED_DATE, D.dealerServiceEntriesId, D.dealerServiceEntriesNumber,v.vid, v.vehicle_registration,"
						+ " D.vehicleOdometer, D.invoiceDate, D.invoiceNumber, mp.partnumber, mp.partname, DP.quantity, L.labourName, "
						+ " pc.pcName, I.ISSUES_ID, I.ISSUES_NUMBER, I.ISSUES_SUMMARY, vn.vendorName, D.creationOn "
						+ " FROM Issues AS I "
						+ " INNER JOIN DealerServiceEntries AS D on D.dealerServiceEntriesId = I.dealerServiceEntriesId"
						+ " INNER JOIN Vehicle AS v ON v.vid = D.vid "
						+ " INNER JOIN DealerServiceEntriesLabour AS DL on DL.dealerServiceEntriesId = D.dealerServiceEntriesId AND DL.markForDelete = 0 "
						+ " INNER JOIN DealerServiceEntriesPart AS DP ON DP.dealerServiceEntriesId = D.dealerServiceEntriesId AND DP.markForDelete = 0 "
						+ " INNER JOIN Vendor AS vn ON vn.vendorId = D.vendorId "
						+ " LEFT JOIN Labour AS L ON L.labourId = DL.labourId "
						+ " LEFT JOIN MasterParts AS mp on mp.partid = DP.partId "
						+ " LEFT JOIN PartCategories as pc ON pc.pcid = I.categoryId "
						+ " where D.companyId = " + companyId +" "+ query + "AND D.markForDelete = 0 AND I.markForDelete=0" ,
						Object[].class);
				
				
				List<Object[]> results = queryt.getResultList();
				int temp=0;
				List<WorkOrdersDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					
					Dtos = new ArrayList<WorkOrdersDto>();
					
					WorkOrdersDto Dto = null;
					
					for (Object[] result : results) {
						Dto = new WorkOrdersDto();
						temp++;
						
						Dto.setWoType(2);
						if(result[0]!=null)
							Dto.setIssueCreated(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[0]));
						Dto.setVehicle_vid((Integer) result[3]);
						Dto.setVehicle_registration((String) result[4]);
						Dto.setVehicle_Odometer((Integer) result[5]);
						Dto.setIssueCategory((String) result[12]) ;
						Dto.setIssueSummary((String) result[15]);
						if(result[17]!=null)
							Dto.setCreated(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[17]));
						Dto.setDseNo((Long) result[2]);
						Dto.setDseId((Long) result[1]);
						Dto.setInvNo((String) result[7]);
						
						if(result[8]!=null)
							Dto.setPartNo((String)result[8]);
						if(result[9]!=null)
							Dto.setPartName((String)result[9]);
						if(result[10]!=null)
							Dto.setPartQt((Double)result[10]);
						if(result[11]!=null)
							Dto.setLabername((String)result[11]);
						if(result[16]!=null)
							Dto.setVendorName((String) result[16]);

						Dtos.add(Dto);
					}
				}
				return Dtos;
			} catch (Exception e) {
				throw e;
			} finally {
				queryt = null;
			}
		}
		
		public List<WorkOrdersDto> ServiceReminedrRegardingWO(ValueObject valueObject , Integer companyId,String dateRangeFrom,String dateRangeTo )
		{
			StringBuffer query = new StringBuffer();
			
			if(valueObject.getString("vid","0") != "0")
			{
				if(valueObject.getInt("workOrderTypeId",0) > 0 && valueObject.getInt("workOrderTypeId",0)==WorkOrdersType.ISSSUES_REGARDING_WO)
				    query.append(" AND w.vehicle_vid in ("+valueObject.getString("vid","0")+")  ");
			}
			
			if(valueObject.getInt("VehicleGroupId",0) > 0)
				query.append("AND v.vehicleGroupId = "+valueObject.getInt("VehicleGroupId",0)+" ");
			if(valueObject.getInt("VehicleTypeId",0) > 0)
				query.append("AND v.vehicleTypeId = "+valueObject.getInt("VehicleTypeId",0)+" ");
			if(valueObject.getInt("vLocationId",0) > 0)
				query.append("AND w.workorders_location_ID = "+valueObject.getInt("vLocationId",0)+" ");
			
			query.append("AND w.start_date between '" + dateRangeFrom + "' AND '" + dateRangeTo +"'");
			
			
			TypedQuery<Object[]> queryt = null;
			try {
				queryt = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.created, SR.vid, v.vehicle_Odometer, v.vehicle_registration, "
						+ " VSP.programName, SJT.Job_Type, SJST.Job_ROT,  "
						+ " w.start_date, w.workorders_Number, jt.Job_Type, Jst.Job_ROT, mp.partnumber, mp.partname, wtp.quantity ,"
						+ " d.driver_firstname, d.driver_Lastname, PL.partlocation_name, w.workorders_id, v.id "
						+ " FROM ServiceReminderWorkOrderHistory AS SRW  "
						+ " INNER JOIN WorkOrders As w on w.workorders_id = SRW.workOrderId "
						+ " INNER JOIN ServiceReminder As SR on SR.workorders_id = w.workorders_id "
						+ " LEFT JOIN VehicleServiceProgram VSP on VSP.vehicleServiceProgramId = SR.serviceProgramId "
						+ " LEFT JOIN JobType SJT ON SJT.Job_id = SR.serviceTypeId"
						+ " LEFT JOIN JobSubType SJST ON SJST.Job_Subid = SR.serviceSubTypeId"
						+ " LEFT JOIN Vehicle AS v ON v.vid = w.vehicle_vid "
						+ " LEFT JOIN WorkOrdersTasks AS wt on  wt.workorders.workorders_id = w.workorders_id AND wt.markForDelete = 0 "
						+ " LEFT JOIN JobType AS jt on jt.Job_id = wt.job_typetaskId "
						+ " LEFT JOIN JobSubType AS Jst on Jst.Job_Subid = wt.job_subtypetask_id "
						+ " LEFT JOIN WorkOrdersTasksToParts AS wtp on wtp.workordertaskid = wt.workordertaskid AND wtp.markForDelete = 0"
						+ " LEFT JOIN MasterParts AS mp on mp.partid = wtp.partid "
						+ " INNER JOIN PartLocations  AS PL on PL.partlocation_id = w.workorders_location_ID"
						+ " LEFT JOIN Inventory R ON R.inventory_id = wtp.inventory_id "
						+ " LEFT JOIN WorkOrdersTasksToLabor AS wtl on wtl.workordertaskid = wt.workordertaskid AND wtl.markForDelete = 0"
						+ " LEFT JOIN Driver AS d on d.driver_id = wtl.laberid "
						+ " where w.companyId = " + companyId +" "+ query + "AND w.markForDelete = 0 AND SR.markForDelete=0" ,
						Object[].class);
				
				
				
				List<Object[]> results = queryt.getResultList();
				int temp=0;
				List<WorkOrdersDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<WorkOrdersDto>();
					
					WorkOrdersDto Dto = null;
					
					for (Object[] result : results) {
						Dto = new WorkOrdersDto();
						temp++;
						
						Dto.setWoType(3);
						if(result[2]!=null)
						{
							Dto.setSRCreated(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[2]));
						}
						Dto.setVehicle_vid((Integer) result[20]);
						Dto.setVehicle_Odometer((Integer)result[4]);
						Dto.setVehicle_registration((String) result[5]);
						Dto.setSRprogram((String) result[6]);	
						Dto.setSRDetails(result[7] + " " + result[8]);
						
						if(result[9]!=null)
						{
							Dto.setCreated(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[9]));
						}
						Dto.setWorkorders_Number((Long)result[10]);
						Dto.setWorkorders_id((Long) result[19]);
						
						if(result[11]!=null)
							Dto.setJob_Type((String) result[11]);
						else
							Dto.setJob_Type("-");
						if(result[12]!=null)
							Dto.setSubType((String) result[12]);
						else
							Dto.setSubType("-");
						if(result[13]!=null)
							Dto.setPartNo((String)result[13]);
						if(result[14]!=null)
							Dto.setPartName((String)result[14]);
						if(result[15]!=null)
							Dto.setPartQt((Double)result[15]);
						if(result[16]!=null && result[17]!=null)
							Dto.setLabername(result[16] +" "+ result[17]);
						if(result[18]!=null)
							Dto.setWorkLocation((String) result[18]);                 
						
						
						Dtos.add(Dto);
					}
				}
				return Dtos;
			} catch (Exception e) {
				throw e;
			} finally {
				queryt = null;
			}
		}
		
		
		public List<WorkOrdersDto> ServiceReminedrRegardingDSE(ValueObject valueObject , Integer companyId,String dateRangeFrom,String dateRangeTo)
		{
			TypedQuery<Object[]> queryt = null;
			
			StringBuffer query = new StringBuffer();
			
			if(valueObject.getString("vid","0") != "0")
			{
				if(valueObject.getInt("workOrderTypeId",0) > 0 && valueObject.getInt("workOrderTypeId",0)==WorkOrdersType.ISSSUES_REGARDING_DSE)
					query.append(" AND D.vid in ("+valueObject.getString("vid","0")+")  ");
			}
			
			if(valueObject.getInt("VehicleGroupId",0) > 0)
				query.append("AND v.vehicleGroupId = "+valueObject.getInt("VehicleGroupId",0)+" ");
			if(valueObject.getInt("VehicleTypeId",0) > 0)
				query.append("AND v.vehicleTypeId = "+valueObject.getInt("VehicleTypeId",0)+" ");
			
			query.append("AND D.invoiceDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"'");
			
			try {
				queryt = entityManager.createQuery(
						"SELECT SR.service_id, SR.service_Number, SR.created, v.vid, v.vehicle_Odometer, v.vehicle_registration, "
								+ " VSP.programName, SJT.Job_Type, SJST.Job_ROT,  "
								+ " D.invoiceDate, D.invoiceNumber,D.dealerServiceEntriesNumber, mp.partnumber, mp.partname, DP.quantity ,"
								+ " L.labourName , D.creationOn ,vn.vendorName, D.dealerServiceEntriesId"
								+ " FROM ServiceReminder AS SR "
								+ " INNER JOIN DealerServiceEntries AS D on D.dealerServiceEntriesId = SR.dealerServiceEntriesId "
								+ " INNER JOIN VehicleServiceProgram VSP on VSP.vehicleServiceProgramId = SR.serviceProgramId"
								+ " LEFT JOIN JobType SJT ON SJT.Job_id = SR.serviceTypeId"
								+ " LEFT JOIN JobSubType SJST ON SJST.Job_Subid = SR.serviceSubTypeId"
								+ " LEFT JOIN Vehicle AS v ON v.vid = D.vid "
								+ " INNER JOIN DealerServiceEntriesLabour AS DL on DL.dealerServiceEntriesId = D.dealerServiceEntriesId AND DL.markForDelete=0"
								+ " INNER JOIN DealerServiceEntriesPart AS DP ON DP.dealerServiceEntriesId = D.dealerServiceEntriesId AND DP.markForDelete=0"
								+ " LEFT JOIN MasterParts AS mp on mp.partid = DP.partId "
								+ " INNER JOIN Vendor AS vn ON vn.vendorId = D.vendorId "
								+ " LEFT JOIN Labour AS L ON L.labourId = DL.labourId "
								+ " where SR.companyId = " + companyId +" "+ query + "AND SR.markForDelete = 0 AND SR.markForDelete=0" ,
						Object[].class);
				
				
				List<Object[]> results = queryt.getResultList();
				int temp=0;
				List<WorkOrdersDto> Dtos = null;
				if (results != null && !results.isEmpty()) {
					
					Dtos = new ArrayList<WorkOrdersDto>();
					
					WorkOrdersDto Dto = null;
					
					for (Object[] result : results) {
						Dto = new WorkOrdersDto();
						temp++;
						
						Dto.setWoType(4);
						if(result[2]!=null)
							Dto.setSRCreated(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[2]));
						Dto.setVehicle_vid((Integer) result[3]);
						Dto.setVehicle_Odometer((Integer)result[4]);
						Dto.setVehicle_registration((String) result[5]);
						Dto.setSRprogram((String) result[6]);	
						Dto.setSRDetails(result[7] + " " + result[8]);
						
						if(result[16]!=null)
							Dto.setCreated(DateTimeUtility.getDateFromTimeStamp((Timestamp) result[16]));
						
						Dto.setDseNo((Long) result[11]);
						Dto.setDseId((Long) result[18]);
						Dto.setInvNo((String) result[10]);
						
						
						if(result[12]!=null)
							Dto.setPartNo((String)result[12]);
						if(result[13]!=null)
							Dto.setPartName((String)result[13]);
						if(result[14]!=null)
							Dto.setPartQt((Double)result[14]);
						if(result[15]!=null)
							Dto.setLabername((String) result[15]);
						if(result[17]!=null)
							Dto.setVendorName((String) result[17]);
						
						
						Dtos.add(Dto);
					}
				}
				return Dtos;
			} catch (Exception e) {
				throw e;
			} finally {
				queryt = null;
			}
		}
		
	@Override
	public double calculatePerHourLaborCost(Integer driverId, Integer companyId) throws Exception {
        Driver driver = null;
        CustomUserDetails userDetails = null;
        HashMap<String, Object> configuration = null;
        Double perHourDriverSalary = null;

        try {
            userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);

	            driver = driverRepository.getDriver(driverId, userDetails.getCompany_id());
	            Double perDaySalary = driver.getDriver_perdaySalary();
	            perHourDriverSalary = perDaySalary / (int) configuration.get("getPerDayWorkingHourForDriver");
	            
	          return perHourDriverSalary;
            
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

		
}

