package org.fleetopgroup.persistence.service;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.AccidentStatus;
import org.fleetopgroup.constant.ApprovalType;
import org.fleetopgroup.constant.IssuesConfigurationContant;
import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PartType;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.ServiceEntriesConfigurationConstants;
import org.fleetopgroup.constant.ServiceEntriesType;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.constant.TripRouteFixedType;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.constant.VendorGSTRegistered;
import org.fleetopgroup.constant.WorkOrdersConfigurationConstants;
import org.fleetopgroup.constant.WorkOrdersType;
import org.fleetopgroup.persistence.bl.IssuesBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.bl.ServiceEntriesBL;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.bl.WorkOrdersBL;
import org.fleetopgroup.persistence.dao.ServiceEntriesDocumentRepository;
import org.fleetopgroup.persistence.dao.ServiceEntriesRepository;
import org.fleetopgroup.persistence.dao.ServiceEntriesTasksRepository;
import org.fleetopgroup.persistence.dao.ServiceEntriesTasksToLaborRepository;
import org.fleetopgroup.persistence.dao.ServiceEntriesTasksToPartsRepository;
import org.fleetopgroup.persistence.dao.ServiceEntryRemarkRepository;
import org.fleetopgroup.persistence.dao.ServiceReminderHistoryRepository;
import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesAndWorkOrdersDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesRemarkDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesTasksDto;
import org.fleetopgroup.persistence.dto.ServiceEntriesTasksToPartsDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.dto.WorkOrderRemarkDto;
import org.fleetopgroup.persistence.model.EmailAlertQueue;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.IssuesTasks;
import org.fleetopgroup.persistence.model.JobSubType;
import org.fleetopgroup.persistence.model.JobType;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.ServiceEntries;
import org.fleetopgroup.persistence.model.ServiceEntriesDocument;
import org.fleetopgroup.persistence.model.ServiceEntriesRemark;
import org.fleetopgroup.persistence.model.ServiceEntriesSequenceCounter;
import org.fleetopgroup.persistence.model.ServiceEntriesTasks;
import org.fleetopgroup.persistence.model.ServiceEntriesTasksToLabor;
import org.fleetopgroup.persistence.model.ServiceEntriesTasksToParts;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.ServiceReminderHistory;
import org.fleetopgroup.persistence.model.SmsAlertQueue;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.VehicleAccidentDetails;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.model.VehicleExpenseDetails;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.VendorSequenceCounter;
import org.fleetopgroup.persistence.model.VendorType;
import org.fleetopgroup.persistence.model.WorkOrderRemark;
import org.fleetopgroup.persistence.report.dao.IServiceEntriesDao;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDealerServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IEmailAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IJobSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IJobTypeService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISmsAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.ITallyIntegrationService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAccidentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVendorSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.persistence.serviceImpl.IVendorTypeService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("ServiceEntriesService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ServiceEntriesService implements IServiceEntriesService {	
	
	private final  Logger LOGGER = LoggerFactory.getLogger(getClass());
	

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private ServiceEntriesRepository ServiceEntriesDao;

	@Autowired
	private ServiceEntriesTasksRepository ServiceEntriesTasksRepository;

	@Autowired
	private ServiceEntriesTasksToPartsRepository ServiceEntriesTasksToPartsRepository;

	@Autowired
	private ServiceEntriesTasksToLaborRepository ServiceEntriesTasksToLaborRepository;

	@Autowired
	private ServiceEntriesDocumentRepository ServiceEntriesDocumentRepository;
	
	@Autowired private HttpServletRequest		request;

	
	@Autowired private  ICompanyConfigurationService 							companyConfigurationService;
	@Autowired private  IServiceEntriesDao										iServiceEntriesDao;
	@Autowired private	ITallyIntegrationService								tallyIntegrationService;
	@Autowired private	ITripSheetService										tripSheetService;
	@Autowired private	IUserProfileService										userProfileService;
	@Autowired private  VehicleProfitAndLossTxnCheckerService					vehicleProfitAndLossTxnCheckerService;
	@Autowired private	IVehicleProfitAndLossService							vehicleProfitAndLossService;
	@Autowired private	ITripExpenseService										tripExpenseService;
	@Autowired private  IJobTypeService                                         JobTypeService;
	@Autowired private  IVehicleService 										vehicleService;
	@Autowired private  IVendorSequenceService 									vendorSequenceService;
	@Autowired private  IVendorTypeService 										VendorTypeService;
	@Autowired private  IVendorService 											vendorService;
	@Autowired private  IServiceEntriesSequenceService 							serviceEntriesSequenceService;
	@Autowired private  IJobSubTypeService 										JobSubTypeService;
	@Autowired private  IServiceReminderService 								ServiceReminderService;
	@Autowired private  IVehicleOdometerHistoryService 							vehicleOdometerHistoryService;
	@Autowired private	IVehicleExpenseDetailsService							vehicleExpenseDetailsService;
	@Autowired private	IVehicleAgentIncomeExpenseDetailsService				vehicleAgentIncomeExpenseDetailsService;
	@Autowired private	IVehicleAgentTxnCheckerService							vehicleAgentTxnCheckerService;
	@Autowired private  IEmailAlertQueueService 								emailAlertQueueService;
	@Autowired private  ISmsAlertQueueService 									smsAlertQueueService;
	@Autowired private	IIssuesService 											issuesService;
	@Autowired private  IServiceEntriesDocumentService							documentService;
	@Autowired private  MongoOperations											mongoOperations;
	@Autowired private 	IWorkOrdersService 										workOrdersService;
	@Autowired private	IPendingVendorPaymentService							vendorPaymentService;
	@Autowired private	IVehicleAccidentDetailsService							accidentDetailsService;
	@Autowired private	IDealerServiceEntriesService							dealerServiceEntriesService;
	@Autowired private  ServiceEntriesBL										serviceEntriesBL;
	@Autowired private  ServiceEntryRemarkRepository						    SERemarkRepository;
	@Autowired private  ServiceReminderHistoryRepository                        serviceReminderHistoryRepository;
	@Autowired private ICashPaymentService									    cashPaymentService;
	@Autowired private IBankPaymentService										bankPaymentService;
	
	ServiceEntriesBL 						WOBL 						= new ServiceEntriesBL();
	VehicleProfitAndLossTxnCheckerBL		txnCheckerBL 				= new VehicleProfitAndLossTxnCheckerBL();
	VehicleOdometerHistoryBL 				VOHBL 						= new VehicleOdometerHistoryBL();
	VehicleAgentTxnCheckerBL				agentTxnCheckerBL			= new VehicleAgentTxnCheckerBL();
	IssuesBL								issuesBL						= new IssuesBL();
	
	SimpleDateFormat dateFormatAtt 		= new SimpleDateFormat("yyyy, MM, dd");
	SimpleDateFormat AttendanceYear 	= new SimpleDateFormat("yyyy");
	SimpleDateFormat AttendanceMonth 	= new SimpleDateFormat("MM");
	SimpleDateFormat AttendanceDay 		= new SimpleDateFormat("dd");
	SimpleDateFormat dateFormatSQL 		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat 		= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat tallyFormat		= new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat dateFormatTime 	= new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	DecimalFormat    toFixedTwo			= new DecimalFormat("##.##");
	SimpleDateFormat sqldateWithTime    = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	
	private static final int PAGE_SIZE = 10;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ServiceEntries addServiceEntries(ServiceEntries ServiceEntries) throws Exception {

		ServiceEntries = ServiceEntriesDao.save(ServiceEntries);
		return ServiceEntries;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addServiceEntriesTask(ServiceEntriesTasks ServiceEntriesTask) throws Exception {

		ServiceEntriesTasksRepository.save(ServiceEntriesTask);
	}

	@Transactional
	public ServiceEntries getServiceEntries(long ServiceEntries) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return ServiceEntriesDao.getServiceEntries(ServiceEntries, userDetails.getCompany_id());
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Override
	public ServiceEntriesDto getServiceEntriesDetails(long ServiceEntries, Integer companyId) throws Exception {

		Query query = entityManager.createQuery(
				"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, SE.vid, V.vehicle_registration, VG.vGroup, SE.vehicle_Odometer,"
						+ " SE.driver_id, D.driver_firstname,  SE.vendor_id, VE.vendorName, VE.vendorLocation,  SE.invoiceNumber, SE.invoiceDate, SE.jobNumber,"
						+ " SE.service_paymentTypeId, SE.service_PayNumber, U.firstName, SE.service_paidbyId, SE.service_paiddate, SE.totalservice_cost,"
						+ " SE.totalserviceROUND_cost, SE.serviceEntries_statusId, SE.completed_date, SE.service_document, SE.service_document_id, SE.service_vendor_paymentdate,"
						+ " SE.service_vendor_paymodeId, SE.service_vendor_approvalID, V.vehicleGroupId, U2.firstName, U3.firstName, SE.created,"
						+ " SE.lastupdated, SE.createdById, SE.lastModifiedById, SE.gpsOdometer, SE.workshopInvoiceAmount,SE.tallyCompanyId, TC.companyName,"
						+ " SE.tripSheetId , TS.expenseName, TS.expenseID ,SE.ISSUES_ID, SE.accidentId,I.ISSUES_NUMBER, I.ISSUES_SUMMARY ,D.driver_fathername ,D.driver_Lastname " 
						+ " FROM ServiceEntries SE "
						+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Driver D ON D.driver_id = SE.driver_id"
						+ " LEFT JOIN Vendor VE ON VE.vendorId = SE.vendor_id"
						+ " LEFT JOIN User U ON U.id = SE.service_paidbyId"
						+ " LEFT JOIN User U2 ON U2.id = SE.createdById"
						+ " LEFT JOIN User U3 ON U3.id = SE.lastModifiedById"
						+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = SE.tallyCompanyId"
						+ " LEFT JOIN TripExpense TS ON TS.expenseID = SE.tallyExpenseId"
						+ " LEFT JOIN Issues I ON I.ISSUES_ID = SE.ISSUES_ID"
						+ " WHERE SE.serviceEntries_id =:id AND SE.companyId =:companyId AND SE.markForDelete = 0");
		query.setParameter("id", ServiceEntries);
		query.setParameter("companyId", companyId);
		Object[] vehicle = null;
		try {
			vehicle = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			LOGGER.error("Err"+nre);
			// Ignore this because as per your logic this is ok!
		}catch (Exception e) {
			LOGGER.error("Err"+e);
			// Ignore this because as per your logic this is ok!
		}
		

		ServiceEntriesDto select;
		if (vehicle != null) {
			select = new ServiceEntriesDto();
			select.setServiceEntries_id((Long) vehicle[0]);
			select.setServiceEntries_Number((Long) vehicle[1]);
			select.setVid((Integer) vehicle[2]);
			select.setVehicle_registration((String) vehicle[3]);
			select.setVehicle_Group((String) vehicle[4]);
			select.setVehicle_Odometer((Integer) vehicle[5]);
			select.setDriver_id((Integer) vehicle[6]);
			select.setDriver_name((String) vehicle[7]);
			select.setVendor_id((Integer) vehicle[8]);
			select.setVendor_name((String) vehicle[9]);
			select.setVendor_location((String) vehicle[10]);
			select.setInvoiceNumber((String) vehicle[11]);
			select.setInvoiceDateOn((java.util.Date) vehicle[12]);
			select.setJobNumber((String) vehicle[13]);
			select.setService_paymentTypeId((short) vehicle[14]);
			select.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) vehicle[14]));
			select.setService_PayNumber((String) vehicle[15]);
			select.setService_paidby((String) vehicle[16]);
			select.setService_paidbyId((Long) vehicle[17]);
			select.setService_paiddateOn((java.util.Date) vehicle[18]);
			select.setTotalservice_cost(Double.parseDouble(toFixedTwo.format(vehicle[19])));
			select.setTotalserviceROUND_cost((Double) vehicle[20]);
			select.setServiceEntries_statusId((short) vehicle[21]);
			select.setServiceEntries_status(ServiceEntriesType.getStatusName((short) vehicle[21]));
			select.setCompleted_dateOn((java.util.Date) vehicle[22]);
			select.setService_document((boolean) vehicle[23]);
			select.setService_document_id((Long) vehicle[24]);
			select.setService_vendor_paymentdate((java.util.Date) vehicle[25]);
			select.setService_vendor_paymodeId((short) vehicle[26]);
			select.setService_vendor_paymode(ServiceEntriesType.getPaymentModeName((short) vehicle[26]));
			select.setService_vendor_approvalID((Long) vehicle[27]);
			select.setVehicleGroupId((long) vehicle[28]);
			select.setCreatedBy((String) vehicle[29]);
			select.setLastModifiedBy((String) vehicle[30]);
			select.setCreatedOn((java.util.Date) vehicle[31]);
			select.setLastupdatedOn((java.util.Date) vehicle[32]);
			select.setCreatedById((Long) vehicle[33]);
			select.setLastModifiedById((Long) vehicle[34]);
			if(vehicle[35] != null)
				select.setGpsOdometer((Double) vehicle[35]);
			if(vehicle[36] != null)
				select.setWorkshopInvoiceAmount((Double) vehicle[36]);
			if(vehicle[37] != null) {
				select.setTallyCompanyId((Long) vehicle[37]);
				select.setTallyCompanyName((String) vehicle[38]);
			}
			if(vehicle[39] != null)
				select.setTripSheetId((Long) vehicle[39]);
			if(vehicle[40] != null)
				select.setTallyExpenseName((String) vehicle[40]);
			if(vehicle[41] != null)
				select.setTallyExpenseId((Integer) vehicle[41]);
			if(vehicle[42] != null) {
				select.setISSUES_ID((Long) vehicle[42]);
			}else {
				select.setISSUES_ID((long) 0);
			}
			if(vehicle[43] != null) {
				select.setAccidentId((Long) vehicle[43]);
			}else {
				select.setAccidentId((long) 0);
			}
			if(vehicle[44] != null) {
				select.setIssueNumber((Long) vehicle[44]);
			}
			if(vehicle[45] != null) {
				select.setIssueSummary((String) vehicle[45]);
			}
			
			if(vehicle[46] != null)
			select.setDriver_name(select.getDriver_name()+" "+vehicle[46]);
			if(vehicle[47] != null)
			select.setDriver_name(select.getDriver_name()+" - "+vehicle[47]);

		} else {
			return null;
		}

		return select;
	}

	@Transactional
	public List<ServiceEntriesTasksDto> getServiceEntriesTasks(long ServiceEntries_id, Integer companyId)
			throws Exception {
		// return
		// ServiceEntriesTasksRepository.getServiceEntriesTasks(ServiceEntries_id,
		// companyId);
		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT SE.servicetaskid, JT.Job_Type, SE.service_typetaskId, SE.service_subtypetask_id, JST.Job_ROT, JST.Job_ROT_number,"
						+ " SE.mark_complete, SE.totalpart_cost, SE.totallaber_cost, SE.totaltask_cost, SE.vid, SE.service_id, SR.service_Number,"
						+ " SE.taskRemark "
						+ " FROM ServiceEntriesTasks  AS SE "
						+ " LEFT JOIN JobType JT ON JT.Job_id = SE.service_typetaskId "
						+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = SE.service_subtypetask_id"
						+ "	LEFT JOIN ServiceReminder SR on SR.service_id = SE.service_id "
						+ " where SE.ServiceEntries.serviceEntries_id = " + ServiceEntries_id + " AND SE.companyId = "
						+ companyId + " AND SE.markForDelete = 0 ",
				Object[].class);
		List<Object[]> results = query.getResultList();

		List<ServiceEntriesTasksDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesTasksDto>();
			ServiceEntriesTasksDto list = null;
			for (Object[] result : results) {
				list = new ServiceEntriesTasksDto();

				list.setServicetaskid((Long) result[0]);
				list.setService_typetask((String) result[1]);
				list.setService_typetaskId((Integer) result[2]);
				list.setService_subtypetask_id((Integer) result[3]);
				if(result[5] != null) {
					list.setService_subtypetask((String) result[4] + " - " + (String) result[5]);
				}else {
					list.setService_subtypetask((String) result[4]);
				}
				
				list.setMark_complete((Integer) result[6]);
				list.setTotalpart_cost(Double.parseDouble(toFixedTwo.format(result[7])));
				list.setTotallaber_cost(Double.parseDouble(toFixedTwo.format(result[8])));
				list.setTotaltask_cost(Double.parseDouble(toFixedTwo.format(result[9])));
				list.setVid((Integer) result[10]);
				list.setServiceEntries_id(ServiceEntries_id);
				if(result[11] != null) {
				list.setService_id((long) result[11]);
				}
				if(result[12] != null) {
				list.setService_Number((long) result[12]);
				}
				if(result[13] != null)
				 list.setTaskRemark((String) result[13]);
				
				Dtos.add(list);
			}
		}
		return Dtos;

	}

	@Transactional
	public List<ServiceEntriesTasksToPartsDto> getServiceEntriesTasksToParts(long ServiceEntries_id) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		Double taxInamount = 0.0;
		
		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT SEP.serviceEntriesTaskto_partid, SEP.partid, MP.partnumber, MP.partname, MP.partTypeId, SEP.quantity,"
						+ " SEP.parteachcost, SEP.partdisc, SEP.parttaxtype, SEP.totalcost, SEP.serviceEntries_id, SEP.servicetaskid, SEP.parttax,"
						+ " SEP.partIGST, SEP.partCGST, SEP.partSGST "
						+ " FROM ServiceEntriesTasksToParts  AS SEP "
						+ " INNER JOIN MasterParts MP ON MP.partid = SEP.partid " + " where SEP.serviceEntries_id = "
						+ ServiceEntries_id + " AND SEP.companyId = " + userDetails.getCompany_id()
						+ " AND SEP.markForDelete = 0 ",
				Object[].class);
		List<Object[]> results = query.getResultList();

		List<ServiceEntriesTasksToPartsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesTasksToPartsDto>();
			ServiceEntriesTasksToPartsDto list = null;
			for (Object[] result : results) {
				list = new ServiceEntriesTasksToPartsDto();

				list.setServiceEntriesTaskto_partid((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPartnumber((String) result[2]);
				list.setPartname((String) result[3]);
				list.setParttype(PartType.getPartTypeName((short) result[4]));
				if(result[5] != null)
				list.setQuantity(Double.parseDouble(toFixedTwo.format(result[5])));
				if(result[6] != null)
				list.setParteachcost(Double.parseDouble(toFixedTwo.format(result[6])));
				if(result[7] != null)
				list.setPartdisc(Double.parseDouble(toFixedTwo.format(result[7])));
				list.setParttaxtype((String) result[8]);
				if(result[9] != null)
				list.setTotalcost(Double.parseDouble(toFixedTwo.format(result[9])));
				list.setServiceEntries_id((Long) result[10]);
				list.setServicetaskid((Long) result[11]);
				if(result[12] != null)
					
				list.setParttax(Double.parseDouble(toFixedTwo.format(result[12])));
				list.setPartIGST(Double.parseDouble(toFixedTwo.format(result[13])));
				list.setPartCGST(Double.parseDouble(toFixedTwo.format(result[14])));
				list.setPartSGST(Double.parseDouble(toFixedTwo.format(result[15])));
				
				taxInamount =   (Double.parseDouble(toFixedTwo.format(result[9]))) * Double.parseDouble(toFixedTwo.format(result[12]))/100;
				
				list.setTaxInAmount(taxInamount);
				
				Dtos.add(list);
			}
		}

		return Dtos;
	}

	@Transactional
	public List<ServiceEntriesTasksToParts> getServiceEntriesTasksToParts_ID(long ServiceEntriesTASK_id,
			Integer companyId) {

		return ServiceEntriesTasksToPartsRepository.getServiceEntriesTasksToParts_ID(ServiceEntriesTASK_id, companyId);
	}

	@Transactional
	public void updateServiceEntries(ServiceEntries ServiceEntries) {

		ServiceEntriesDao.updateServiceEntries(ServiceEntries.getDriver_id(), ServiceEntries.getVehicle_Odometer(),
				ServiceEntries.getVendor_id(), ServiceEntries.getInvoiceNumber(), ServiceEntries.getInvoiceDate(),
				ServiceEntries.getJobNumber(), ServiceEntries.getService_paymentTypeId(),ServiceEntries.getService_vendor_paymodeId(),
				ServiceEntries.getService_PayNumber(), ServiceEntries.getService_paidbyId(),
				ServiceEntries.getLastModifiedById(), ServiceEntries.getLastupdated(), ServiceEntries.getService_paiddate(),
				ServiceEntries.getServiceEntries_id(), ServiceEntries.getCompanyId(),ServiceEntries.getWorkshopInvoiceAmount()
				,ServiceEntries.getTallyCompanyId(), ServiceEntries.getTallyExpenseId(), ServiceEntries.getVid());
	}

	@Transactional
	public List<ServiceEntries> listServiceEntries() {

		return ServiceEntriesDao.findAll();
	}

	@Transactional
	public List<ServiceEntries> listVehicleServiceEntries(String ServiceEntries_vehiclename) {

		return ServiceEntriesDao.listVehicleServiceEntries(ServiceEntries_vehiclename);
	}

	@Transactional
	public void deleteServiceEntries(Long ServiceEntries_id,Timestamp toDate, Long userId,  Integer companyid) {

		ServiceEntriesDao.deleteServiceEntries(ServiceEntries_id,toDate,userId, companyid);
	}

	/** This Page get Vehicle table to get pagination values */
	@SuppressWarnings("deprecation")
	@Transactional
	public Page<ServiceEntries> getDeployment_Page_ServiceEntries(short Status_ID, Integer pageNumber,
			CustomUserDetails userDetails) throws Exception {

		try {
			Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return ServiceEntriesDao.getDeployment_Page_ServiceEntries(Status_ID, userDetails.getCompany_id(),
						pageable);
			}
			return ServiceEntriesDao.getDeployment_Page_ServiceEntries(Status_ID, userDetails.getId(),
					userDetails.getCompany_id(), pageable);

		} catch (Exception e) {
			throw e;
		}
	}

	/** This List get Vehicle table to get pagination last 10 entries values */
	@Transactional
	public List<ServiceEntriesDto> listOpenServiceEntries(short Status_ID, Integer pageNumber) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, SE.vid, V.vehicle_registration, VG.vGroup "
								+ ", SE.vehicle_Odometer, SE.vendor_id, VE.vendorName, VE.vendorLocation, SE.invoiceNumber, SE.jobNumber, SE.invoiceDate"
								+ ", SE.serviceEntries_statusId, U.firstName, SE.totalservice_cost, SE.totalserviceROUND_cost, SE.service_paymentTypeId"
								+ ",SE.service_document, SE.service_document_id, V.vehicleGroupId, SE.lastModifiedById, U.firstName, SE.service_paiddate"
								+ " FROM ServiceEntries  AS SE "
								+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid"
								+ " INNER JOIN VehicleGroup AS VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Vendor AS VE ON VE.vendorId = SE.vendor_id "
								+ " LEFT JOIN User AS U ON U.id = SE.service_paidbyId"
								+ " LEFT JOIN User U ON U.id = SE.lastModifiedById"
								+ " where SE.serviceEntries_statusId = " + Status_ID + " AND  SE.companyId = "
								+ userDetails.getCompany_id()
								+ " AND SE.markForDelete = 0 ORDER BY SE.serviceEntries_id DESC ",
						Object[].class);
			} else {

				query = entityManager.createQuery(
						"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, SE.vid, V.vehicle_registration, VG.vGroup "
								+ ", SE.vehicle_Odometer, SE.vendor_id, VE.vendorName, VE.vendorLocation, SE.invoiceNumber, SE.jobNumber, SE.invoiceDate"
								+ ", SE.serviceEntries_statusId, U.firstName, SE.totalservice_cost, SE.totalserviceROUND_cost, SE.service_paymentTypeId"
								+ ",SE.service_document, SE.service_document_id, V.vehicleGroupId, SE.lastModifiedById, U.firstName, SE.service_paiddate"
								+ " FROM ServiceEntries  AS SE "
								+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid"
								+ " INNER JOIN VehicleGroup AS VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Vendor AS VE ON VE.vendorId = SE.vendor_id "
								+ " LEFT JOIN User AS U ON U.id = SE.service_paidbyId"
								+ " LEFT JOIN User U ON U.id = SE.lastModifiedById"
								+ "	INNER JOIN VehicleGroupPermision AS VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + " " + " where SE.serviceEntries_statusId = " + Status_ID
								+ " AND SE.companyId = " + userDetails.getCompany_id()
								+ " AND SE.markForDelete = 0 ORDER BY SE.serviceEntries_id DESC ",
						Object[].class);
			}
			query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			query.setMaxResults(PAGE_SIZE);
			List<Object[]> results = query.getResultList();

			List<ServiceEntriesDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceEntriesDto>();
				ServiceEntriesDto list = null;
				for (Object[] result : results) {
					list = new ServiceEntriesDto();

					list.setServiceEntries_id((Long) result[0]);
					list.setServiceEntries_Number((Long) result[1]);
					list.setVid((Integer) result[2]);
					list.setVehicle_registration((String) result[3]);
					list.setVehicle_Group((String) result[4]);
					list.setVehicle_Odometer((Integer) result[5]);
					list.setVendor_id((Integer) result[6]);
					list.setVendor_name((String) result[7]);
					list.setVendor_location((String) result[8]);

					list.setInvoiceNumber((String) result[9]);
					list.setJobNumber((String) result[10]);
					list.setInvoiceDateOn((java.util.Date) result[11]);
					list.setServiceEntries_statusId((short) result[12]);
					list.setServiceEntries_status(ServiceEntriesType.getStatusName((short) result[12]));
					list.setService_paidby((String) result[13]);
					list.setTotalservice_cost((Double) result[14]);
					list.setTotalserviceROUND_cost((Double) result[15]);
					list.setService_paymentTypeId((short) result[16]);
					list.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[16]));
					list.setService_document((boolean) result[17]);
					list.setService_document_id((Long) result[18]);
					list.setVehicleGroupId((long) result[19]);
					list.setLastModifiedById((long) result[20]);
					list.setLastModifiedBy((String) result[21]);
					list.setService_paiddateOn((java.util.Date) result[22]);

					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public void addServiceEntriesTaskToParts(ServiceEntriesTasksToParts ServiceEntriesTask) throws Exception {

		ServiceEntriesTasksToPartsRepository.save(ServiceEntriesTask);
	}

	@Transactional
	public List<ServiceEntriesTasks> getServiceEntriesTasksIDToTotalCost(long ServiceEntries_id) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return ServiceEntriesTasksRepository.getServiceEntriesTasksIDToTotalCost(ServiceEntries_id,
				userDetails.getCompany_id());
	}

	@Transactional
	public void updateServiceEntriesTask_TotalPartCost(Long TaskID) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		entityManager.createQuery(
				" UPDATE ServiceEntriesTasks AS ST SET ST.totalpart_cost=(SELECT COALESCE(ROUND(SUM(STP.totalcost),2),0) FROM ServiceEntriesTasksToParts AS STP WHERE STP.servicetaskid="
						+ TaskID + " AND STP.companyId=" + userDetails.getCompany_id() + " AND STP.markForDelete=0 ),  "
						+ " ST.totaltask_cost = COALESCE(ROUND(ST.totalpart_cost + ST.totallaber_cost,2),0) "
						+ " where ST.servicetaskid=" + TaskID + " AND ST.companyId = " + userDetails.getCompany_id()
						+ " AND ST.markForDelete = 0 ")
				.executeUpdate();
	}

	@Transactional
	public void updateServiceEntriesMainTotalCost(Long ServiceEntries_ID) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		entityManager.createQuery(
				" UPDATE ServiceEntries AS SE SET SE.totalservice_cost=(SELECT COALESCE(ROUND(SUM(ST.totaltask_cost),2),0) FROM ServiceEntriesTasks AS ST WHERE ST.ServiceEntries.serviceEntries_id="
						+ ServiceEntries_ID + " AND ST.companyId=" + userDetails.getCompany_id()
						+ " AND ST.markForDelete=0) , "
						+ " SE.balanceAmount = (SELECT COALESCE(ROUND(SUM(ST.totaltask_cost),2),0) FROM ServiceEntriesTasks AS ST WHERE ST.ServiceEntries.serviceEntries_id="+ ServiceEntries_ID + " "  
						+ " AND ST.companyId=" + userDetails.getCompany_id() + " AND ST.markForDelete=0) , "
						+ " SE.totalserviceROUND_cost = COALESCE(ROUND(SE.totalservice_cost,0),0) where SE.serviceEntries_id="
						+ ServiceEntries_ID + " AND SE.companyId=" + userDetails.getCompany_id()
						+ " AND SE.markForDelete=0  ")
				.executeUpdate();
	}

	@Transactional
	public void updateServiceEntriesProcess(short Process, Long ServiceEntries_ID, Integer companyId, Date lastupdated, Long lastmodifiedby) throws Exception {
		ServiceEntriesDao.updateServiceEntriesProcess(Process, ServiceEntries_ID, companyId, lastupdated, lastmodifiedby);
	}

	@Transactional
	public void addServiceEntriesTaskToLabor(ServiceEntriesTasksToLabor ServiceEntriesTaskLabor) throws Exception {

		ServiceEntriesTasksToLaborRepository.save(ServiceEntriesTaskLabor);
	}

	@Transactional
	public void updateServiceEntriesTask_TotalLaborCost(Long TaskID) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		entityManager.createQuery(
				" UPDATE ServiceEntriesTasks AS ST SET ST.totallaber_cost=(SELECT COALESCE(ROUND(SUM(STL.totalcost),2),0) FROM ServiceEntriesTasksToLabor AS STL WHERE STL.servicetaskid="
						+ TaskID + " AND STL.companyId=" + userDetails.getCompany_id() + " AND STL.markForDelete=0 ),  "
						+ " ST.totaltask_cost = COALESCE(ROUND(ST.totalpart_cost + ST.totallaber_cost,2),0) "
						+ " where ST.servicetaskid=" + TaskID + " AND ST.companyId = " + userDetails.getCompany_id()
						+ " AND ST.markForDelete = 0 ")
				.executeUpdate();

	}

	@Transactional
	public List<ServiceEntriesTasksToLabor> getServiceEntriesTasksToLabor(long ServiceEntries_id) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return ServiceEntriesTasksToLaborRepository.getServiceEntriesTasksToLabor(ServiceEntries_id,
				userDetails.getCompany_id());
	}

	@Transactional
	public List<ServiceEntriesTasksToLabor> getServiceEntriesTasksToLabor_ID(long ServiceEntriesTask_id,
			Integer companyId) {

		return ServiceEntriesTasksToLaborRepository.getServiceEntriesTasksToLabor_ID(ServiceEntriesTask_id, companyId);
	}

	@Transactional
	public ServiceEntriesTasks getServiceEntriesCompletion(Long ServiceEntriesTasksID, Integer companyId)
			throws Exception {

		return ServiceEntriesTasksRepository.getServiceEntriesCompletion(ServiceEntriesTasksID, companyId);
	}

	@Transactional
	public void updateServiceEntriesTask_Completion(Integer completionvalue, Long ServiceEntriesTasksID,
			Integer companyId) throws Exception {

		ServiceEntriesDao.updateServiceEntriesTask_Completion(completionvalue, ServiceEntriesTasksID, companyId);
	}

	@Transactional
	public void updateServiceEntriesCOMPLETE_date(short Process, Date COMPLETE_date, Long ServiceEntries_ID,
			Integer companyId, Long lastmodifiedby) throws Exception {

		ServiceEntriesDao.updateServiceEntriesCOMPLETE_date(Process, COMPLETE_date, ServiceEntries_ID, companyId, lastmodifiedby);
	}

	@Transactional
	public Long countServiceEntries(Integer companyId) throws Exception {

		return ServiceEntriesDao.countServiceEntries(companyId);
	}

	@Transactional
	public Long countServiceEntriestatues(String Statues, Integer companyId) throws Exception {

		return ServiceEntriesDao.countServiceEntriestatues(Statues, companyId);
	}

	@Transactional
	public ServiceEntriesTasks getServiceEntriesTask(long ServiceEntriestask_ID) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if(userDetails != null && ServiceEntriestask_ID > 0) {
			return ServiceEntriesTasksRepository.getServiceEntriesTask(ServiceEntriestask_ID, userDetails.getCompany_id());
		}else {
			return null;
		}
	}

	@Transactional
	public void deleteServiceEntriesTask(Long ServiceEntriesTask_id, Integer companyId) {

		ServiceEntriesTasksRepository.deleteServiceEntriesTask(ServiceEntriesTask_id, companyId);
	}

	@Transactional
	public ServiceEntriesTasksToParts getServiceEntriesTaskToParts_ONLY_ID(long ServiceEntriesTaskToPart_ID,
			Integer companyId) {

		return ServiceEntriesTasksToPartsRepository.getServiceEntriesTaskToParts_ONLY_ID(ServiceEntriesTaskToPart_ID,
				companyId);
	}

	@Transactional
	public void deleteServiceEntriesTaskTOParts(Long ServiceEntriesTask_id, Integer companyId) {

		ServiceEntriesTasksToPartsRepository.deleteServiceEntriesTaskTOParts(ServiceEntriesTask_id, companyId);
	}

	@Transactional
	public void addServiceEntriesTask(String jobSub_name, Long ServiceEntriesID) throws Exception {

		entityManager.createQuery("INSERT INTO ServiceEntriesTasks (job_subtypetask, ServiceEntries_id) VALUES ('"
				+ jobSub_name + "'," + ServiceEntriesID + ")").executeUpdate();
	}

	@Transactional
	public ServiceEntriesTasksToLabor getServiceEntriesTaskToLabor_ONLY_ID(long ServiceEntriesTaskToLabor_ID,
			Integer companyId) {

		return ServiceEntriesTasksToLaborRepository.getServiceEntriesTaskToLabor_ONLY_ID(ServiceEntriesTaskToLabor_ID,
				companyId);
	}

	@Transactional
	public void deleteServiceEntriesTaskTOLabor(Long ServiceEntriesTask_Laborid, Integer companyId) {

		ServiceEntriesTasksToLaborRepository.deleteServiceEntriesTaskTOLabor(ServiceEntriesTask_Laborid, companyId);
	}

	@Transactional
	public void updateServiceEntriesMainTotalCost_TAX(Double TotalServiceEntriesTAX, Double TotalServiceEntriescost,
			Long ServiceEntries_ID) throws Exception {

		ServiceEntriesDao.updateServiceEntriesMainTotalCost_TAX(TotalServiceEntriesTAX, TotalServiceEntriescost,
				ServiceEntries_ID);
	}

	@Transactional
	public List<ServiceEntriesDto> SearchServiceEntries(String ServiceEntries_Search, Integer companyId, Long id)
			throws Exception {
		List<ServiceEntriesDto> Dtos = null;

		// return ServiceEntriesDao.SearchServiceEntries(ServiceEntries_Search);
		TypedQuery<Object[]> query = null;
		ServiceEntriesDto list = null;
		if(ServiceEntries_Search != null && !ServiceEntries_Search.trim().equalsIgnoreCase("") && ServiceEntries_Search.indexOf('\'') != 0 ) {
		if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, SE.vid, V.vehicle_registration, VG.vGroup "
							+ ", SE.vehicle_Odometer, VE.vendorName, VE.vendorLocation, SE.invoiceNumber, SE.jobNumber, SE.invoiceDate"
							+ ", SE.serviceEntries_statusId, U.firstName, SE.totalservice_cost, SE.totalserviceROUND_cost, SE.service_paymentTypeId"
							+ ",SE.service_document, SE.service_document_id, V.vehicleGroupId FROM ServiceEntries  AS SE "
							+ " INNER JOIN Vehicle V ON V.vid = SE.vid"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Vendor AS VE ON VE.vendorId = SE.vendor_id "
							+ " LEFT JOIN User U ON U.id = SE.service_paidbyId"
							+ " where (lower(SE.serviceEntries_Number) Like ('%" + ServiceEntries_Search
							+ "%') OR lower(V.vehicle_registration) Like ('%" + ServiceEntries_Search
							+ "%') OR lower(SE.invoiceNumber) Like ('%" + ServiceEntries_Search
							+ "%')) AND SE.companyId = " + companyId + " AND SE.markForDelete = 0 ORDER BY SE.serviceEntries_id DESC ",
					Object[].class);
		} else {

			query = entityManager.createQuery(
					"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, SE.vid, V.vehicle_registration, VG.vGroup "
							+ ", SE.vehicle_Odometer, VE.vendorName, VE.vendorLocation, SE.invoiceNumber, SE.jobNumber, SE.invoiceDate"
							+ ", SE.serviceEntries_statusId, U.firstName, SE.totalservice_cost, SE.totalserviceROUND_cost, SE.service_paymentTypeId"
							+ ",SE.service_document, SE.service_document_id, V.vehicleGroupId from ServiceEntries AS SE "
							+ " INNER JOIN Vehicle V ON V.vid = SE.vid"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Vendor AS VE ON VE.vendorId = SE.vendor_id "
							+ " LEFT JOIN User U ON U.id = SE.service_paidbyId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
							+ id + "" + " where (lower(SE.serviceEntries_Number) Like ('%" + ServiceEntries_Search
							+ "%') OR lower(V.vehicle_registration) Like ('%" + ServiceEntries_Search
							+ "%') OR lower(SE.invoiceNumber) Like ('%" + ServiceEntries_Search
							+ "%')) AND SE.companyId = " + companyId + " AND SE.markForDelete = 0 ORDER BY SE.serviceEntries_id DESC ",
					Object[].class);
		}
		query.setFirstResult(0);
		query.setMaxResults(100);
		List<Object[]> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesDto>();
			for (Object[] result : results) {
				list = new ServiceEntriesDto();

				list.setServiceEntries_id((Long) result[0]);
				list.setServiceEntries_Number((Long) result[1]);
				list.setVid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Group((String) result[4]);
				list.setVehicle_Odometer((Integer) result[5]);
				list.setVendor_name((String) result[6]);
				list.setVendor_location((String) result[7]);
				list.setInvoiceNumber((String) result[8]);
				list.setJobNumber((String) result[9]);
				list.setInvoiceDateOn((java.util.Date) result[10]);
				list.setServiceEntries_statusId((short) result[11]);
				list.setServiceEntries_status(ServiceEntriesType.getStatusName((short) result[11]));
				list.setService_paidby((String) result[12]);
				list.setTotalservice_cost((Double) result[13]);
				list.setTotalserviceROUND_cost((Double) result[14]);
				list.setService_paymentTypeId((short) result[15]);
				list.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[15]));
				list.setService_document((boolean) result[16]);
				list.setService_document_id((Long) result[17]);
				list.setVehicleGroupId((long) result[18]);

				Dtos.add(list);
			}
		}
		}
		return Dtos;

	}

	@Override
	public List<ServiceEntriesDto> SearchServiceEntriesByNumber(Long ServiceEntries_Search, Integer companyId,
			Long userId) throws Exception {

		// return ServiceEntriesDao.SearchServiceEntries(ServiceEntries_Search);
		TypedQuery<Object[]> query = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			query = entityManager.createQuery(
					"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, SE.vid, V.vehicle_registration, VG.vGroup "
							+ ", SE.vehicle_Odometer, VE.vendorName, VE.vendorLocation, SE.invoiceNumber, SE.jobNumber, SE.invoiceDate"
							+ ", SE.serviceEntries_statusId, U.firstName, SE.totalservice_cost, SE.totalserviceROUND_cost, SE.service_paymentTypeId"
							+ ",SE.service_document, SE.service_document_id, V.vehicleGroupId FROM ServiceEntries  AS SE "
							+ " INNER JOIN Vehicle V ON V.vid = SE.vid"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Vendor AS VE ON VE.vendorId = SE.vendor_id "
							+ " LEFT JOIN User U ON U.id = SE.service_paidbyId"
							+ " where SE.serviceEntries_Number = "+ServiceEntries_Search+" AND SE.companyId = " + companyId + " AND SE.markForDelete = 0",
					Object[].class);
		} else {

			query = entityManager.createQuery(
					"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, SE.vid, V.vehicle_registration, VG.vGroup "
							+ ", SE.vehicle_Odometer, VE.vendorName, VE.vendorLocation, SE.invoiceNumber, SE.jobNumber, SE.invoiceDate"
							+ ", SE.serviceEntries_statusId, U.firstName, SE.totalservice_cost, SE.totalserviceROUND_cost, SE.service_paymentTypeId"
							+ ",SE.service_document, SE.service_document_id, V.vehicleGroupId from ServiceEntries AS SE "
							+ " INNER JOIN Vehicle V ON V.vid = SE.vid"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Vendor AS VE ON VE.vendorId = SE.vendor_id "
							+ " LEFT JOIN User U ON U.id = SE.service_paidbyId"
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
							+ userId + "" + " where SE.serviceEntries_Number = "+ServiceEntries_Search+" AND SE.companyId = " + companyId + " AND SE.markForDelete = 0",
					Object[].class);
		}
		query.setFirstResult(0);
		query.setMaxResults(20);
		List<Object[]> results = query.getResultList();

		List<ServiceEntriesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesDto>();
			ServiceEntriesDto list = null;
			for (Object[] result : results) {
				list = new ServiceEntriesDto();

				list.setServiceEntries_id((Long) result[0]);
				list.setServiceEntries_Number((Long) result[1]);
				list.setVid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Group((String) result[4]);
				list.setVehicle_Odometer((Integer) result[5]);
				list.setVendor_name((String) result[6]);
				list.setVendor_location((String) result[7]);
				list.setInvoiceNumber((String) result[8]);
				list.setJobNumber((String) result[9]);
				list.setInvoiceDateOn((java.util.Date) result[10]);
				list.setServiceEntries_statusId((short) result[11]);
				list.setServiceEntries_status(ServiceEntriesType.getStatusName((short) result[11]));
				list.setService_paidby((String) result[12]);
				list.setTotalservice_cost((Double) result[13]);
				list.setTotalserviceROUND_cost((Double) result[14]);
				list.setService_paymentTypeId((short) result[15]);
				list.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[15]));
				list.setService_document((boolean) result[16]);
				list.setService_document_id((Long) result[17]);
				list.setVehicleGroupId((long) result[18]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}
	
	@Transactional
	public List<ServiceEntriesDto> ReportServiceEntries(String qurey, CustomUserDetails userDetails) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, V.vehicle_registration, VG.vGroup "
								+ ", SR.vehicle_Odometer, VE.vendorName, VE.vendorLocation, SR.invoiceNumber, SR.jobNumber, SR.invoiceDate"
								+ ", SR.serviceEntries_statusId, U.firstName, SR.totalservice_cost, SR.totalserviceROUND_cost, SR.service_paymentTypeId"
								+ ", SR.service_document, SR.service_document_id, V.vehicleGroupId, U2.firstName from ServiceEntries SR "
								+ " INNER JOIN Vehicle V ON V.vid = SR.vid"
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Vendor AS VE ON VE.vendorId = SR.vendor_id "
								+ " LEFT JOIN User U ON U.id = SR.service_paidbyId" + ""
								+ " LEFT JOIN User U2 ON U2.id = SR.lastModifiedById" + ""
								+ " where  SR.markForDelete  = 0 "
								+ qurey + " AND SR.companyId = " + userDetails.getCompany_id() + " ORDER BY SR.serviceEntries_id DESC ",
						Object[].class);
			} else {
				query = entityManager.createQuery(
						"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, V.vehicle_registration, VG.vGroup  "
								+ ", SR.vehicle_Odometer, VE.vendorName, VE.vendorLocation, SR.invoiceNumber, SR.jobNumber, SR.invoiceDate"
								+ ", SR.serviceEntries_statusId, U.firstName, SR.totalservice_cost, SR.totalserviceROUND_cost, SR.service_paymentTypeId"
								+ ", SR.service_document, SR.service_document_id, V.vehicleGroupId, U2.firstName from ServiceEntries SR "
								+ " INNER JOIN Vehicle V ON V.vid = SR.vid"
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Vendor AS VE ON VE.vendorId = SR.vendor_id "
								+ " LEFT JOIN User U ON U.id = SR.service_paidbyId"
								+ " LEFT JOIN User U2 ON U2.id = SR.lastModifiedById" + ""
								+ "	INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + " " + " where  SR.markForDelete = 0 " + qurey
								+ " AND SR.companyId = " + userDetails.getCompany_id() + " ORDER BY SR.serviceEntries_id DESC ",
						Object[].class);
			}
			List<Object[]> results = query.getResultList();

			List<ServiceEntriesDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceEntriesDto>();
				ServiceEntriesDto list = null;
				for (Object[] result : results) {
					list = new ServiceEntriesDto();

					list.setServiceEntries_id((Long) result[0]);
					list.setServiceEntries_Number((Long) result[1]);
					list.setVid((Integer) result[2]);
					list.setVehicle_registration((String) result[3]);
					list.setVehicle_Group((String) result[4]);
					list.setVehicle_Odometer((Integer) result[5]);
					list.setVendor_name((String) result[6]);
					list.setVendor_location((String) result[7]);
					list.setInvoiceNumber((String) result[8]);
					list.setJobNumber((String) result[9]);
					list.setInvoiceDateOn((java.util.Date) result[10]);
					list.setServiceEntries_statusId((short) result[11]);
					list.setServiceEntries_status(ServiceEntriesType.getStatusName((short) result[11]));
					list.setService_paidby((String) result[12]);
					list.setTotalservice_cost((Double) result[13]);
					list.setTotalserviceROUND_cost((Double) result[14]);
					list.setService_paymentTypeId((short) result[15]);
					list.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[15]));
					list.setService_document((boolean) result[16]);
					list.setService_document_id((Long) result[17]);
					list.setVehicleGroupId((long) result[18]);
					if(result[19] != null)
						list.setLastModifiedBy((String) result[19]);

					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}

	}

	@Transactional
	public List<ServiceEntriesDto> VehicleToServiceEntriesList(Integer Vehicle_id, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, V.vehicle_registration, VG.vGroup "
							+ ", SR.vehicle_Odometer, VE.vendorName, VE.vendorLocation, SR.invoiceNumber, SR.jobNumber, SR.invoiceDate"
							+ ", SR.serviceEntries_statusId, U.firstName, SR.totalservice_cost, SR.totalserviceROUND_cost, SR.service_paymentTypeId"
							+ ", SR.service_document, SR.service_document_id, V.vehicleGroupId from ServiceEntries SR "
							+ " INNER JOIN Vehicle V ON V.vid = SR.vid"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Vendor AS VE ON VE.vendorId = SR.vendor_id "
							+ " LEFT JOIN User U ON U.id = SR.service_paidbyId"
							+ " where  SR.markForDelete  = 0 AND SR.vid = " + Vehicle_id + " AND SR.companyId = "
							+ companyId + " AND V.vStatusId IN("+VehicleStatusConstant.VEHICLE_STATUS_ACTIVE+","+VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE+","+VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP+","+VehicleStatusConstant.VEHICLE_STATUS_ACCIDENT+") order by SR.serviceEntries_Number DESC ",
					Object[].class);
			List<Object[]> results = query.getResultList();

			List<ServiceEntriesDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceEntriesDto>();
				ServiceEntriesDto list = null;
				for (Object[] result : results) {
					list = new ServiceEntriesDto();

					list.setServiceEntries_id((Long) result[0]);
					list.setServiceEntries_Number((Long) result[1]);
					list.setVid((Integer) result[2]);
					list.setVehicle_registration((String) result[3]);
					list.setVehicle_Group((String) result[4]);
					list.setVehicle_Odometer((Integer) result[5]);
					list.setVendor_name((String) result[6]);
					list.setVendor_location((String) result[7]);
					list.setInvoiceNumber((String) result[8]);
					list.setJobNumber((String) result[9]);
					list.setInvoiceDateOn((java.util.Date) result[10]);
					list.setServiceEntries_statusId((short) result[11]);
					list.setServiceEntries_status(ServiceEntriesType.getStatusName((short) result[11]));
					list.setService_paidby((String) result[12]);
					list.setTotalservice_cost((Double) result[13]);
					list.setTotalserviceROUND_cost((Double) result[14]);
					list.setService_paymentTypeId((short) result[15]);
					list.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[15]));
					list.setService_document((boolean) result[16]);
					list.setService_document_id((Long) result[17]);
					list.setVehicleGroupId((long) result[18]);

					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}

	}

	@Transactional
	public void uploadServiceEntriesDocument(ServiceEntriesDocument ServiceEntriesdocument) throws Exception {

		ServiceEntriesDocumentRepository.save(ServiceEntriesdocument);
	}

	@Transactional
	public ServiceEntriesDocument getServiceEntriesDocument(Long ServiceEntries_id) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return ServiceEntriesDocumentRepository.getServiceEntriesDocument(ServiceEntries_id,
				userDetails.getCompany_id());
	}

	@Transactional
	public void updateOldServiceEntriesDocument(ServiceEntriesDocument ServiceEntriesDocument) throws Exception {

		ServiceEntriesDocumentRepository.save(ServiceEntriesDocument);
	}

	@Transactional
	public List<ServiceEntriesTasks> getLast_ServiceEntriesTasks(Integer jobType, Integer jobsubtype,
			Integer vehicle_id, Integer companyId) {

		TypedQuery<ServiceEntriesTasks> query = entityManager.createQuery(
				"FROM ServiceEntriesTasks where service_typetaskId=" + jobType + " AND service_subtypetask_id ="
						+ jobsubtype + " AND  vid =" + vehicle_id + " AND companyId = " + companyId
						+ " AND markForDelete = 0 ORDER BY servicetaskid DESC",
				ServiceEntriesTasks.class);
		query.setFirstResult(0);
		query.setMaxResults(1);
		return query.getResultList();
	}

	@Transactional
	public List<ServiceEntriesTasks> getLast_ServiceEntriesTasks(Integer jobType, Integer jobsubtype,
			Integer vehicle_id, Long serviceEntries_id) {

		TypedQuery<ServiceEntriesTasks> query = entityManager
				.createQuery(
						"FROM ServiceEntriesTasks where service_typetaskId=" + jobType + " AND service_subtypetask_id ="
								+ jobsubtype + " AND  vid =" + vehicle_id + " AND serviceEntries_id <>'"
								+ serviceEntries_id + "' AND markForDelete = 0 ORDER BY servicetaskid DESC",
						ServiceEntriesTasks.class);
		query.setFirstResult(0);
		query.setMaxResults(1);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceEntriesService#
	 * validate_ServiceEntries(java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<ServiceEntries> validate_ServiceEntries(Integer vendor_id, String invoice_no, Integer vehicle_Id,
			Integer companyId) {

		return ServiceEntriesDao.validate_ServiceEntries(vendor_id, invoice_no, vehicle_Id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceEntriesService#
	 * vehicle_WiseRepair_Report(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<ServiceEntriesDto> vehicle_WiseRepair_Report(String dateRangeFrom, String dateRangeTo,
			String multipleVehicle, Integer companyId) throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, V.vehicle_registration, VG.vGroup "
						+ ", SR.vehicle_Odometer, VN.vendorName, VN.vendorLocation, SR.invoiceNumber, SR.jobNumber, SR.invoiceDate"
						+ ", SR.serviceEntries_statusId, U.firstName, SR.totalservice_cost, SR.totalserviceROUND_cost, SR.service_paymentTypeId"
						+ ", SR.service_document, SR.service_document_id, V.vehicleGroupId from ServiceEntries SR "
						+ " INNER JOIN Vehicle V ON V.vid = SR.vid"
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = SR.vendor_id"
						+ " LEFT JOIN User U ON U.id = SR.service_paidbyId" + " where SR.invoiceDate between '"
						+ dateRangeFrom + "' AND '" + dateRangeTo + "' AND  SR.vid IN (" + multipleVehicle
						+ ") AND SR.companyId = " + companyId
						+ "  AND SR.markForDelete = 0 ORDER BY SR.serviceEntries_id DESC",
				Object[].class);

		List<Object[]> results = query.getResultList();

		List<ServiceEntriesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesDto>();
			ServiceEntriesDto list = null;
			for (Object[] result : results) {
				list = new ServiceEntriesDto();

				list.setServiceEntries_id((Long) result[0]);
				list.setServiceEntries_Number((Long) result[1]);
				list.setVid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Group((String) result[4]);
				list.setVehicle_Odometer((Integer) result[5]);
				list.setVendor_name((String) result[6]);
				list.setVendor_location((String) result[7]);
				list.setInvoiceNumber((String) result[8]);
				list.setJobNumber((String) result[9]);
				list.setInvoiceDateOn((java.util.Date) result[10]);
				list.setServiceEntries_statusId((short) result[11]);
				list.setServiceEntries_status(ServiceEntriesType.getStatusName((short) result[11]));
				list.setService_paidby((String) result[12]);
				if(result[13] != null)
				list.setTotalservice_cost(Double.parseDouble(toFixedTwo.format(result[13])) );
				if(result[14] != null)
				list.setTotalserviceROUND_cost(Double.parseDouble(toFixedTwo.format(result[14])));
				list.setService_paymentTypeId((short) result[15]);
				list.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[15]));
				list.setService_document((boolean) result[16]);
				list.setService_document_id((Long) result[17]);
				list.setVehicleGroupId((long) result[18]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Override
	public List<ServiceEntriesDto> vehicle_WiseRepair_Report(String dateRangeFrom, String dateRangeTo, Long createdById,
			String vids, Integer companyId) throws Exception {

		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, V.vehicle_registration, VG.vGroup "
						+ ", SR.vehicle_Odometer, VN.vendorName, VN.vendorLocation, SR.invoiceNumber, SR.jobNumber, SR.invoiceDate"
						+ ", SR.serviceEntries_statusId, U.firstName, SR.totalservice_cost, SR.totalserviceROUND_cost, SR.service_paymentTypeId"
						+ ", SR.service_document, SR.service_document_id, V.vehicleGroupId from ServiceEntries SR "
						+ " INNER JOIN Vehicle V ON V.vid = SR.vid"
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = SR.vendor_id"
						+ " LEFT JOIN User U ON U.id = SR.service_paidbyId" + " where SR.invoiceDate between '"
						+ dateRangeFrom + "' AND '" + dateRangeTo + "' AND  SR.vid IN (" + vids
						+ ") AND SR.createdById = "+createdById+" AND SR.companyId = " + companyId
						+ "  AND SR.markForDelete = 0 ORDER BY SR.serviceEntries_id DESC",
				Object[].class);

		List<Object[]> results = query.getResultList();

		List<ServiceEntriesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesDto>();
			ServiceEntriesDto list = null;
			for (Object[] result : results) {
				list = new ServiceEntriesDto();

				list.setServiceEntries_id((Long) result[0]);
				list.setServiceEntries_Number((Long) result[1]);
				list.setVid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Group((String) result[4]);
				list.setVehicle_Odometer((Integer) result[5]);
				list.setVendor_name((String) result[6]);
				list.setVendor_location((String) result[7]);
				list.setInvoiceNumber((String) result[8]);
				list.setJobNumber((String) result[9]);
				list.setInvoiceDateOn((java.util.Date) result[10]);
				list.setServiceEntries_statusId((short) result[11]);
				list.setServiceEntries_status(ServiceEntriesType.getStatusName((short) result[11]));
				list.setService_paidby((String) result[12]);
				list.setTotalservice_cost((Double) result[13]);
				list.setTotalserviceROUND_cost((Double) result[14]);
				list.setService_paymentTypeId((short) result[15]);
				list.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[15]));
				list.setService_document((boolean) result[16]);
				list.setService_document_id((Long) result[17]);
				list.setVehicleGroupId((long) result[18]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	
	@Override
	public List<ServiceEntriesDto> vehicle_WiseRepair_Report(String dateRangeFrom, String dateRangeTo, Long createdById,
			Integer companyId) throws Exception {
		TypedQuery<Object[]> query = null;
		if(createdById != null && createdById > 0) {
			query = entityManager.createQuery(
					"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, V.vehicle_registration, VG.vGroup "
							+ ", SR.vehicle_Odometer, VN.vendorName, VN.vendorLocation, SR.invoiceNumber, SR.jobNumber, SR.invoiceDate"
							+ ", SR.serviceEntries_statusId, U.firstName, SR.totalservice_cost, SR.totalserviceROUND_cost, SR.service_paymentTypeId"
							+ ", SR.service_document, SR.service_document_id, V.vehicleGroupId from ServiceEntries SR "
							+ " INNER JOIN Vehicle V ON V.vid = SR.vid"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Vendor VN ON VN.vendorId = SR.vendor_id"
							+ " LEFT JOIN User U ON U.id = SR.service_paidbyId" + " where SR.invoiceDate between '"
							+ dateRangeFrom + "' AND '" + dateRangeTo + "' AND  SR.createdById = "+createdById+" AND SR.companyId = " + companyId
							+ "  AND SR.markForDelete = 0 ORDER BY SR.serviceEntries_id DESC",
					Object[].class);
		}else {
			query = entityManager.createQuery(
					"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, V.vehicle_registration, VG.vGroup "
							+ ", SR.vehicle_Odometer, VN.vendorName, VN.vendorLocation, SR.invoiceNumber, SR.jobNumber, SR.invoiceDate"
							+ ", SR.serviceEntries_statusId, U.firstName, SR.totalservice_cost, SR.totalserviceROUND_cost, SR.service_paymentTypeId"
							+ ", SR.service_document, SR.service_document_id, V.vehicleGroupId from ServiceEntries SR "
							+ " INNER JOIN Vehicle V ON V.vid = SR.vid"
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " LEFT JOIN Vendor VN ON VN.vendorId = SR.vendor_id"
							+ " LEFT JOIN User U ON U.id = SR.service_paidbyId" + " where SR.invoiceDate between '"
							+ dateRangeFrom + "' AND '" + dateRangeTo + "' AND SR.companyId = " + companyId
							+ "  AND SR.markForDelete = 0 ORDER BY SR.serviceEntries_id DESC",
					Object[].class);
		}
		
		

		List<Object[]> results = query.getResultList();

		List<ServiceEntriesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesDto>();
			ServiceEntriesDto list = null;
			for (Object[] result : results) {
				list = new ServiceEntriesDto();

				list.setServiceEntries_id((Long) result[0]);
				list.setServiceEntries_Number((Long) result[1]);
				list.setVid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Group((String) result[4]);
				list.setVehicle_Odometer((Integer) result[5]);
				list.setVendor_name((String) result[6]);
				list.setVendor_location((String) result[7]);
				list.setInvoiceNumber((String) result[8]);
				list.setJobNumber((String) result[9]);
				list.setInvoiceDateOn((java.util.Date) result[10]);
				list.setServiceEntries_statusId((short) result[11]);
				list.setServiceEntries_status(ServiceEntriesType.getStatusName((short) result[11]));
				list.setService_paidby((String) result[12]);
				if(result[13] != null)
				list.setTotalservice_cost(Double.parseDouble(toFixedTwo.format(result[13])));
				if(result[14] != null)
				list.setTotalserviceROUND_cost(Double.parseDouble(toFixedTwo.format(result[14])));
				
				list.setService_paymentTypeId((short) result[15]);
				list.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[15]));
				list.setService_document((boolean) result[16]);
				list.setService_document_id((Long) result[17]);
				list.setVehicleGroupId((long) result[18]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceEntriesService#
	 * getServiceEntriesTasks_vehicle_WiseRepair_Report(java.lang.String)
	 */
	@Transactional
	public List<ServiceEntriesTasksDto> getServiceEntriesTasks_vehicle_WiseRepair_Report(
			String multipleServiceEntries_id, Integer companyId) {

		TypedQuery<Object[]> queryt = null;
		try {
			/* this only Select column */
			queryt = entityManager.createQuery(
					"SELECT R.servicetaskid, JT.Job_Type, R.service_subtypetask_id, JST.Job_ROT, JST.Job_ROT_number, R.mark_complete, R.totalpart_cost, "
							+ "R.totallaber_cost, R.totaltask_cost, R.vid, R.ServiceEntries.serviceEntries_id "
							+ " FROM ServiceEntriesTasks  AS R"
							+ " LEFT JOIN JobType JT ON JT.Job_id = R.service_typetaskId "
							+ " LEFT JOIN JobSubType JST ON JST.Job_Subid = R.service_subtypetask_id"
							+ " where R.ServiceEntries.serviceEntries_id IN (" + multipleServiceEntries_id
							+ ") AND R.companyId = " + companyId + " AND R.markForDelete = 0",
					Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<ServiceEntriesTasksDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceEntriesTasksDto>();
				ServiceEntriesTasksDto list = null;
				for (Object[] result : results) {
					list = new ServiceEntriesTasksDto();

					list.setServicetaskid((Long) result[0]);
					list.setService_typetask((String) result[1]);
					list.setService_subtypetask_id((Integer) result[2]);
					list.setService_subtypetask((String) result[3] + " - " + (String) result[4]);
					list.setMark_complete((Integer) result[5]);
					if(result[6] != null)
					list.setTotalpart_cost(Double.parseDouble(toFixedTwo.format(result[6])));
					if(result[7] != null)
					list.setTotallaber_cost(Double.parseDouble(toFixedTwo.format(result[7])));
					if(result[8] != null)
					list.setTotaltask_cost(Double.parseDouble(toFixedTwo.format(result[8])));
					list.setVid((Integer) result[9]);
					list.setServiceEntries_id((Long) result[10]);
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		} finally {
			queryt = null;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceEntriesService#
	 * getServiceEntriesTasksToParts_vehicle_WiseRepair_Report(java.lang.String)
	 */
	@Transactional
	public List<ServiceEntriesTasksToPartsDto> getServiceEntriesTasksToParts_vehicle_WiseRepair_Report(
			String multipleServiceEntries_id, Integer companyId) {

		TypedQuery<Object[]> query = null;
		query = entityManager.createQuery(
				"SELECT SEP.serviceEntriesTaskto_partid, SEP.partid, MP.partnumber, MP.partname, MP.partTypeId, SEP.quantity,"
						+ " SEP.parteachcost, SEP.partdisc, SEP.parttaxtype, SEP.totalcost, SEP.serviceEntries_id, SEP.servicetaskid, SEP.parttax "
						+ " FROM ServiceEntriesTasksToParts  AS SEP "
						+ " INNER JOIN MasterParts MP ON MP.partid = SEP.partid " + " where SEP.serviceEntries_id IN ("
						+ multipleServiceEntries_id + ") AND SEP.companyId = " + companyId
						+ " AND SEP.markForDelete = 0 ",
				Object[].class);
		List<Object[]> results = query.getResultList();

		List<ServiceEntriesTasksToPartsDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesTasksToPartsDto>();
			ServiceEntriesTasksToPartsDto list = null;
			for (Object[] result : results) {
				list = new ServiceEntriesTasksToPartsDto();

				list.setServiceEntriesTaskto_partid((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPartnumber((String) result[2]);
				list.setPartname((String) result[3]);
				list.setParttype(PartType.getPartTypeName((short) result[4]));
				if(result[5] != null)
				list.setQuantity(Double.parseDouble(toFixedTwo.format(result[5])));
				if(result[6] != null)
				list.setParteachcost(Double.parseDouble(toFixedTwo.format(result[6])));
				if(result[7] != null)
				list.setPartdisc(Double.parseDouble(toFixedTwo.format(result[7])));
				list.setParttaxtype((String) result[8]);
				if(result[9] != null)
				list.setTotalcost(Double.parseDouble(toFixedTwo.format(result[9])));
				list.setServiceEntries_id((Long) result[10]);
				list.setServicetaskid((Long) result[11]);
				if(result[12] != null)
				list.setParttax(Double.parseDouble(toFixedTwo.format(result[12])));

				Dtos.add(list);
			}
		}
        
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceEntriesService#
	 * ServiceEntriesTasks_vehicle_WiseRepair_TotalAmount(java.lang.String)
	 */
	@Transactional
	public List<Double> ServiceEntriesTasks_vehicle_WiseRepair_SumAmount(String multipleServiceEntries_id,
			Integer companyId) {
		TypedQuery<Double> query = entityManager.createQuery(
				"SELECT SVET.totaltask_cost * SETP.quantity AS total" 
		        + " FROM ServiceEntriesTasks AS SVET" 
				+ " LEFT JOIN ServiceEntriesTasksToParts SETP ON SETP.serviceEntries_id = SVET.ServiceEntries"
				+ " where SVET.ServiceEntries IN (" + multipleServiceEntries_id
				+ ") AND SVET.companyId = " + companyId + " AND SVET.markForDelete = 0",
            Double.class);
		return query.getResultList();
	}
	
	@Transactional
	public List<Double> ServiceEntriesTasks_vehicle_WiseRepair_TotalAmount(String multipleServiceEntries_id,
			Integer companyId) {

		TypedQuery<Double> query = entityManager.createQuery(
				"select sum(totaltask_cost) from ServiceEntriesTasks where serviceEntries_id IN ("
						+ multipleServiceEntries_id + ") AND companyId = " + companyId + " AND markForDelete = 0 ",
						Double.class);

		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceEntriesService#
	 * listServiceEntries_vendor_APPROVALLISTFilter(java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<ServiceEntriesDto> listServiceEntries_vendor_APPROVALLISTFilter(Integer vendor_id, String ApprovalQuery,
			Integer companyId) throws Exception {

		TypedQuery<Object[]> querySearch = entityManager.createQuery(
				"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, V.vehicle_registration, VG.vGroup "
						+ ", SR.vehicle_Odometer, VN.vendorName, VN.vendorLocation, SR.invoiceNumber, SR.jobNumber, SR.invoiceDate"
						+ ", SR.serviceEntries_statusId, U.firstName, SR.totalservice_cost, SR.totalserviceROUND_cost, SR.service_paymentTypeId"
						+ ", SR.service_document, SR.service_document_id, VG.gid, SR.service_vendor_paymodeId, SR.paidAmount, SR.balanceAmount "  
						+ " from ServiceEntries SR "
						+ " INNER JOIN Vehicle V ON V.vid = SR.vid"
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = SR.vendor_id"
						+ " LEFT JOIN User U ON U.id = SR.service_paidbyId" + " where  ( SR.vendor_id=" + vendor_id
						+ " and SR.service_paymentTypeId=" + PaymentTypeConstant.PAYMENT_TYPE_CREDIT
						+ " and SR.service_vendor_paymodeId =" + ServiceEntriesType.PAYMENT_MODE_NOT_PAID
						+ " OR SR.vendor_id=" + vendor_id + " and SR.service_paymentTypeId="
						+ PaymentTypeConstant.PAYMENT_TYPE_CREDIT + " and SR.service_vendor_paymodeId ="
						+ ServiceEntriesType.PAYMENT_MODE_APPROVED + " "
						+ " OR SR.vendor_id=" + vendor_id + " AND SR.service_paymentTypeId ="+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" AND SR.service_vendor_paymodeId =" + ServiceEntriesType.PAYMENT_MODE_PARTIALLY_PAID+ "  AND SR.balanceAmount > 0) "
						+ " AND " + ApprovalQuery + " ORDER BY SR.invoiceDate DESC",
				Object[].class);

		List<Object[]> results = querySearch.getResultList();

		List<ServiceEntriesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesDto>();
			ServiceEntriesDto list = null;
			for (Object[] result : results) {
				list = new ServiceEntriesDto();

				list.setServiceEntries_id((Long) result[0]);
				list.setServiceEntries_Number((Long) result[1]);
				list.setVid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Group((String) result[4]);
				list.setVehicle_Odometer((Integer) result[5]);
				list.setVendor_name((String) result[6]);
				list.setVendor_location((String) result[7]);
				list.setInvoiceNumber((String) result[8]);
				list.setJobNumber((String) result[9]);
				list.setInvoiceDateOn((java.util.Date) result[10]);
				list.setServiceEntries_statusId((short) result[11]);
				list.setServiceEntries_status(ServiceEntriesType.getStatusName((short) result[11]));
				list.setService_paidby((String) result[12]);
				if(result[13] != null)
				list.setTotalservice_cost(Double.parseDouble(toFixedTwo.format(result[13])) );
				if(result[14] != null)
				list.setTotalserviceROUND_cost(Double.parseDouble(toFixedTwo.format(result[14])));
				
				list.setService_paymentTypeId((short) result[15]);
				list.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[15]));
				list.setService_document((boolean) result[16]);
				list.setService_document_id((Long) result[17]);
				list.setVehicleGroupId((long) result[18]);
				list.setService_vendor_paymodeId((short) result[19]);
				list.setService_vendor_paymode(ServiceEntriesType.getPaymentModeName((short) result[19]));
			
				if(result[20]!= null) {
					list.setPaidAmount(Double.parseDouble(toFixedTwo.format(result[20])));
				}
				if(result[21]!= null) {
					list.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[21])));
				}
				

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceEntriesService#
	 * update_Vendor_ApprovalTO_Status_MULTIP_SERVICE_ID(java.lang.String,
	 * java.lang.Long, java.lang.String)
	 */
	@Transactional
	public void update_Vendor_ApprovalTO_Status_MULTIP_SERVICE_ID(String ApprovalService_ID, Long Approval_ID,
			short approval_Status) throws Exception {

		entityManager.createQuery(
				"update ServiceEntries set service_vendor_approvalID=" + Approval_ID + ", service_vendor_paymodeId="
						+ approval_Status + " where serviceEntries_id IN (" + ApprovalService_ID + ") ")
				.executeUpdate();

	}
	
	@Override
	@Transactional
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception {
		entityManager.createQuery("  UPDATE ServiceEntries SET service_vendor_paymentdate ='" + paymentDate+"' , "
				+ " service_vendor_paymodeId="+paymentStatus+""
				+ " WHERE service_vendor_approvalID = "+approvalId+" ").executeUpdate();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceEntriesService#
	 * getVendorApproval_IN_SERVICE_Entries_List(java.lang.Long)
	 */
	@Transactional
	public List<ServiceEntriesDto> getVendorApproval_IN_SERVICE_Entries_List(Long VendorApproval_Id) throws Exception {
		// return
		// ServiceEntriesDao.getVendorApproval_IN_SERVICE_Entries_List(VendorApproval_Id);

		TypedQuery<Object[]> querySearch = entityManager.createQuery(
				"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, V.vehicle_registration, VG.vGroup "
						+ ", SR.vehicle_Odometer, VN.vendorName, VN.vendorLocation, SR.invoiceNumber, SR.jobNumber, SR.invoiceDate"
						+ ", SR.serviceEntries_statusId, U.firstName, SR.totalservice_cost, SR.totalserviceROUND_cost, SR.service_paymentTypeId"
						+ ", SR.service_document, SR.service_document_id, V.vehicleGroupId, SR.service_vendor_paymodeId,SR.paidAmount "
						+ " from ServiceEntries SR "
						+ " INNER JOIN Vehicle V ON V.vid = SR.vid"
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = SR.vendor_id"
						+ " LEFT JOIN User U ON U.id = SR.service_paidbyId" + " where  SR.service_vendor_approvalID = "
						+ VendorApproval_Id + " AND SR.markForDelete = 0 ",
				Object[].class);

		List<Object[]> results = querySearch.getResultList();

		List<ServiceEntriesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesDto>();
			ServiceEntriesDto list = null;
			for (Object[] result : results) {
				list = new ServiceEntriesDto();

				list.setServiceEntries_id((Long) result[0]);
				list.setServiceEntries_Number((Long) result[1]);
				list.setVid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Group((String) result[4]);
				list.setVehicle_Odometer((Integer) result[5]);
				list.setVendor_name((String) result[6]);
				list.setVendor_location((String) result[7]);
				list.setInvoiceNumber((String) result[8]);
				list.setJobNumber((String) result[9]);
				list.setInvoiceDateOn((java.util.Date) result[10]);
				list.setServiceEntries_statusId((short) result[11]);
				list.setServiceEntries_status(ServiceEntriesType.getStatusName((short) result[11]));
				list.setService_paidby((String) result[12]);
				list.setTotalservice_cost((Double) result[13]);
				list.setTotalserviceROUND_cost((Double) result[14]);
				list.setService_paymentTypeId((short) result[15]);
				list.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[15]));
				list.setService_document((boolean) result[16]);
				list.setService_document_id((Long) result[17]);
				list.setVehicleGroupId((long) result[18]);
				list.setService_vendor_paymodeId((short) result[19]);
				list.setService_vendor_paymode(ServiceEntriesType.getPaymentModeName((short) result[19]));
				if((Double) result[20] != null)
				list.setPaidAmount((Double) result[20]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}
	
	@Override
	public List<ServiceEntriesDto> getServiceEntiresInVendorApproval(Long VendorApproval_Id) throws Exception {
		// return
		// ServiceEntriesDao.getVendorApproval_IN_SERVICE_Entries_List(VendorApproval_Id);

		TypedQuery<Object[]> querySearch = entityManager.createQuery(
				"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, V.vehicle_registration, VG.vGroup "
						+ ", SR.vehicle_Odometer, VN.vendorName, VN.vendorLocation, SR.invoiceNumber, SR.jobNumber, SR.invoiceDate"
						+ ", SR.serviceEntries_statusId, U.firstName, SR.totalservice_cost, SR.totalserviceROUND_cost, SR.service_paymentTypeId"
						+ ", SR.service_document, SR.service_document_id, V.vehicleGroupId, SR.service_vendor_paymodeId,SR.paidAmount "
						+ " from ServiceEntries SR "
						+ " INNER JOIN Vehicle V ON V.vid = SR.vid"
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = SR.vendor_id"
						+ " LEFT JOIN User U ON U.id = SR.service_paidbyId" + " where  SR.service_vendor_approvalID = "
						+ VendorApproval_Id + " AND SR.markForDelete = 0 ",
				Object[].class);

		List<Object[]> results = querySearch.getResultList();

		List<ServiceEntriesDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesDto>();
			ServiceEntriesDto list = null;
			for (Object[] result : results) {
				list = new ServiceEntriesDto();

				list.setServiceEntries_id((Long) result[0]);
				list.setServiceEntries_Number((Long) result[1]);
				list.setVid((Integer) result[2]);
				list.setVehicle_registration((String) result[3]);
				list.setVehicle_Group((String) result[4]);
				list.setVehicle_Odometer((Integer) result[5]);
				list.setVendor_name((String) result[6]);
				list.setVendor_location((String) result[7]);
				list.setInvoiceNumber((String) result[8]);
				list.setJobNumber((String) result[9]);
				list.setInvoiceDateOn((java.util.Date) result[10]);
				list.setServiceEntries_statusId((short) result[11]);
				list.setServiceEntries_status(ServiceEntriesType.getStatusName((short) result[11]));
				list.setService_paidby((String) result[12]);
				list.setTotalservice_cost((Double) result[13]);
				list.setTotalserviceROUND_cost((Double) result[14]);
				list.setService_paymentTypeId((short) result[15]);
				list.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[15]));
				list.setService_document((boolean) result[16]);
				list.setService_document_id((Long) result[17]);
				list.setVehicleGroupId((long) result[18]);
				list.setService_vendor_paymodeId((short) result[19]);
				list.setService_vendor_paymode(ServiceEntriesType.getPaymentModeName((short) result[19]));
				if((Double) result[20] != null)
				list.setPaidAmount((Double) result[20]);

				Dtos.add(list);
			}
		}
		return Dtos;

	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceEntriesService#
	 * getVendorApproval_IN_SERVICE_Entries_List(java.lang.Long)
	 */
	@Transactional
	public List<ServiceEntries> get_Amount_VendorApproval_IN_SERVICE_Entries(Long VendorApproval_Id) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.serviceEntries_id, R.totalservice_cost From ServiceEntries As R where R.service_vendor_approvalID =:approval AND R.markForDelete = 0",
				Object[].class);
		queryt.setParameter("approval", VendorApproval_Id);

		List<Object[]> results = queryt.getResultList();

		List<ServiceEntries> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceEntries>();
			ServiceEntries list = null;
			for (Object[] result : results) {
				list = new ServiceEntries();

				list.setServiceEntries_id((Long) result[0]);
				list.setTotalservice_cost((Double) result[1]);

				Dtos.add(list);
			}
		}

		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceEntriesService#
	 * update_Vendor_ApprovalTO_Status_PayDate_Multiple_Service_ID(java.lang.
	 * String, java.sql.Date, java.lang.Long, java.lang.String)
	 */
	@Transactional
	public void update_Vendor_ApprovalTO_Status_PayDate_Multiple_Service_ID(String ApprovalService_ID,
			java.util.Date paymentDate, Long Approval_ID, short approval_Status, Integer companyId,double paidAmount, double discountAmount) throws Exception {
		double balanceAmount = 0;
		entityManager.createQuery("update ServiceEntries set service_vendor_paymentdate='" + paymentDate
				+ "' , service_vendor_approvalID=" + Approval_ID + ", service_vendor_paymodeId=' " + approval_Status
				+ "', paidAmount = '"+paidAmount+"', discountAmount = '"+discountAmount+"' , balanceAmount = '"+balanceAmount+"' "
				+ " where serviceEntries_id IN (" + ApprovalService_ID + ") AND companyId = " + companyId + "")
				.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceEntriesService#
	 * updateServiceEntries_ROUNT_OF_COST_COMPLETE_date(java.lang.Double,
	 * java.lang.String, java.sql.Date, java.lang.Long)
	 */
	@Transactional
	public void updateServiceEntries_ROUNT_OF_COST_COMPLETE_date(Double serviceRountCost, String Process,
			Date COMPLETE_date, Long ServiceEntries_ID) throws Exception {

		ServiceEntriesDao.updateServiceEntries_ROUNT_OF_COST_COMPLETE_date(serviceRountCost, Process, COMPLETE_date,
				ServiceEntries_ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceEntriesService#
	 * update_Vehicle_Group_USING_Vehicle_Id(java.lang.String, java.lang.Integer)
	 */
	// @Transactional
	// public void update_Vehicle_Group_USING_Vehicle_Id(String Vehicle_Group,
	// Integer vehicle_id, long vehicleGroupId,
	// Integer companyId) {
	//
	// ServiceEntriesDao.update_Vehicle_Group_USING_Vehicle_Id(Vehicle_Group,
	// vehicle_id, vehicleGroupId, companyId);
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IServiceEntriesService#
	 * Update_ServiceEntries_Docuemnt_AvailableValue(boolean, java.lang.Long)
	 */
	@Transactional
	public void Update_ServiceEntries_Docuemnt_AvailableValue(Long service_documentid, boolean service_document,
			Long serviceEntries_id, Integer companyId) {

		ServiceEntriesDao.Update_ServiceEntries_Docuemnt_AvailableValue(service_documentid, service_document,
				serviceEntries_id, companyId);
	}
	
	@Override
	@Transactional
	public void Update_ServiceEntries_Docuemnt_AvailableValue(String service_documentid, boolean service_document,
			Long serviceEntries_id, Integer companyId) {
		
		ServiceEntriesDao.Update_ServiceEntries_Docuemnt_AvailableValue(service_documentid, service_document,
				serviceEntries_id, companyId);
	}

	@Override
	public List<ServiceEntriesDto> listOpenServiceEntries(CustomUserDetails userDetails) throws Exception {
		try {
			TypedQuery<Object[]> query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, SE.vid, V.vehicle_registration, VG.vGroup "
								+ ", SE.vehicle_Odometer, VE.vendorName, VE.vendorLocation, SE.invoiceNumber, SE.jobNumber, SE.invoiceDate"
								+ ", SE.serviceEntries_statusId, U.firstName, SE.totalservice_cost, SE.totalserviceROUND_cost, SE.service_paymentTypeId"
								+ ",SE.service_document, SE.service_document_id, V.vehicleGroupId FROM ServiceEntries  AS SE "
								+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid "
								+ " INNER JOIN VehicleGroup AS VG ON VG.gid = V.vehicleGroupId "
								+ " LEFT JOIN User U ON U.id = SE.service_paidbyId"
								+ " LEFT JOIN Vendor VN ON VN.vendorId = SE.vendor_id" + " where SE.companyId = "
								+ userDetails.getCompany_id() + " AND SE.markForDelete = 0",
						Object[].class);

			} else {

				query = entityManager.createQuery(
						"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, SE.vid, V.vehicle_registration, VG.vGroup "
								+ ", SE.vehicle_Odometer, VE.vendorName, VE.vendorLocation, SE.invoiceNumber, SE.jobNumber, SE.invoiceDate"
								+ ", SE.serviceEntries_statusId, U.firstName, SE.totalservice_cost, SE.totalserviceROUND_cost, SE.service_paymentTypeId"
								+ ",SE.service_document, SE.service_document_id, V.vehicleGroupId FROM ServiceEntries  AS SE "
								+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid "
								+ " INNER JOIN VehicleGroup AS VG ON VG.gid = V.vehicleGroupId "
								+ " LEFT JOIN Vendor VE ON VE.vendorId = SE.vendor_id"
								+ " LEFT JOIN User U ON U.id = SE.service_paidbyId"
								+ "	INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + " " + " where SE.companyId = " + userDetails.getCompany_id()
								+ " AND SE.markForDelete = 0",
						Object[].class);

			}
			List<Object[]> results = query.getResultList();

			List<ServiceEntriesDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceEntriesDto>();
				ServiceEntriesDto list = null;
				for (Object[] result : results) {
					list = new ServiceEntriesDto();

					list.setServiceEntries_id((Long) result[0]);
					list.setServiceEntries_Number((Long) result[1]);
					list.setVid((Integer) result[2]);
					list.setVehicle_registration((String) result[3]);
					list.setVehicle_Group((String) result[4]);
					list.setVehicle_Odometer((Integer) result[5]);
					list.setVendor_name((String) result[6]);
					list.setVendor_location((String) result[7]);
					list.setInvoiceNumber((String) result[8]);
					list.setJobNumber((String) result[9]);
					list.setInvoiceDate_On((java.util.Date) result[10]);
					list.setServiceEntries_status(ServiceEntriesType.getStatusName((short) result[11]));
					list.setService_paidby((String) result[12]);
					list.setTotalservice_cost((Double) result[13]);
					list.setTotalserviceROUND_cost((Double) result[14]);
					list.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[15]));
					list.setService_document((boolean) result[16]);
					list.setService_document_id((Long) result[17]);
					list.setVehicleGroupId((long) result[18]);

					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ServiceEntriesDto getServiceEntriesByNumber(long ServiceEntries, Integer companyId, Long id)
			throws Exception {
		try {
			Query query = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(companyId,
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, SE.vid, V.vehicle_registration, VG.vGroup, SE.vehicle_Odometer,"
								+ " SE.driver_id, D.driver_firstname, SE.vendor_id, VE.vendorName, VE.vendorLocation, SE.invoiceNumber, SE.invoiceDate, SE.jobNumber,"
								+ " SE.service_paymentTypeId, SE.service_PayNumber, U.firstName, SE.service_paidbyId, SE.service_paiddate, SE.totalservice_cost,"
								+ " SE.totalserviceROUND_cost, SE.serviceEntries_statusId, SE.completed_date, SE.service_document, SE.service_document_id, SE.service_vendor_paymentdate,"
								+ " SE.service_vendor_paymodeId, SE.service_vendor_approvalID, V.vehicleGroupId, U2.firstName, U3.firstName, SE.created,"
								+ " SE.lastupdated, SE.lastModifiedById, SE.lastModifiedById "
								+ " FROM ServiceEntries SE " 
								+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Vendor VE ON VE.vendorId = SE.vendor_id"
								+ " LEFT JOIN Driver D ON D.driver_id = SE.driver_id"
								+ " LEFT JOIN User U ON U.id = SE.service_paidbyId"
								+ " LEFT JOIN User U2 ON U2.id = SE.createdById"
								+ " LEFT JOIN User U3 ON U3.id = SE.lastModifiedById"
								+ " WHERE SE.serviceEntries_Number =:sEntries_Number AND SE.companyId =:comId AND SE.markForDelete = 0");
			} else {

				query = entityManager.createQuery(
						"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, SE.vid, V.vehicle_registration, VG.vGroup, SE.vehicle_Odometer,"
								+ " SE.driver_id, D.driver_firstname, SE.vendor_id, VE.vendorName, VE.vendorLocation, SE.invoiceNumber, SE.invoiceDate, SE.jobNumber,"
								+ " SE.service_paymentTypeId, SE.service_PayNumber, U.firstName, SE.service_paidbyId, SE.service_paiddate, SE.totalservice_cost,"
								+ " SE.totalserviceROUND_cost, SE.serviceEntries_statusId, SE.completed_date, SE.service_document, SE.service_document_id, SE.service_vendor_paymentdate,"
								+ " SE.service_vendor_paymodeId, SE.service_vendor_approvalID, V.vehicleGroupId, U2.firstName, U3.firstName, SE.created,"
								+ " SE.lastupdated, SE.lastModifiedById, SE.lastModifiedById "
								+ " FROM ServiceEntries SE " 
								+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " LEFT JOIN Vendor VE ON VE.vendorId = SE.vendor_id"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ id + "" + " LEFT JOIN User U ON U.id = SE.service_paidbyId"
								+ " LEFT JOIN Driver D ON D.driver_id = SE.driver_id"
								+ " LEFT JOIN User U2 ON U2.id = SE.createdById"
								+ " LEFT JOIN User U3 ON U3.id = SE.lastModifiedById"
								+ " WHERE SE.serviceEntries_Number =:sEntries_Number AND SE.companyId =:comId AND SE.markForDelete = 0");
			}
			query.setParameter("sEntries_Number", ServiceEntries);
			query.setParameter("comId", companyId);
			Object[] vehicle = null;
			try {
				vehicle = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				return null;
				// Ignore this because as per your logic this is ok!
				//nre.printStackTrace();
			}
			ServiceEntriesDto select = null;
			if (vehicle != null) {
				select = new ServiceEntriesDto();
				select.setServiceEntries_id((Long) vehicle[0]);
				select.setServiceEntries_Number((Long) vehicle[1]);
				select.setVid((Integer) vehicle[2]);
				select.setVehicle_registration((String) vehicle[3]);
				select.setVehicle_Group((String) vehicle[4]);
				select.setVehicle_Odometer((Integer) vehicle[5]);
				select.setDriver_id((Integer) vehicle[6]);
				select.setDriver_name((String) vehicle[7]);
				select.setVendor_id((Integer) vehicle[8]);
				select.setVendor_name((String) vehicle[9]);
				select.setVendor_location((String) vehicle[10]);
				select.setInvoiceNumber((String) vehicle[11]);
				select.setInvoiceDateOn((java.util.Date) vehicle[12]);
				select.setJobNumber((String) vehicle[13]);
				select.setService_paymentTypeId((short) vehicle[14]);
				select.setService_paymentType(PaymentTypeConstant.getPaymentTypeName((short) vehicle[14]));
				select.setService_PayNumber((String) vehicle[15]);
				select.setService_paidby((String) vehicle[16]);
				select.setService_paidbyId((Long) vehicle[17]);
				select.setService_paiddateOn((java.util.Date) vehicle[18]);
				select.setTotalservice_cost((Double) vehicle[19]);
				select.setTotalserviceROUND_cost((Double) vehicle[20]);
				select.setServiceEntries_statusId((short) vehicle[21]);
				select.setServiceEntries_status(ServiceEntriesType.getStatusName((short) vehicle[21]));
				select.setCompleted_dateOn((java.util.Date) vehicle[22]);
				select.setService_document((boolean) vehicle[23]);
				select.setService_document_id((Long) vehicle[24]);
				select.setService_vendor_paymentdate((java.util.Date) vehicle[25]);
				select.setService_vendor_paymodeId((short) vehicle[26]);
				select.setService_vendor_paymode(ServiceEntriesType.getPaymentModeName((short) vehicle[26]));
				select.setService_vendor_approvalID((Long) vehicle[27]);
				select.setVehicleGroupId((long) vehicle[28]);
				select.setCreatedBy((String) vehicle[29]);
				select.setLastModifiedBy((String) vehicle[30]);
				select.setCreatedOn((java.util.Date) vehicle[31]);
				select.setLastupdatedOn((java.util.Date) vehicle[32]);
				select.setCreatedById((Long) vehicle[33]);
				select.setLastModifiedById((Long) vehicle[34]);
			}
			return select;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public int getServiceEntriesCountByNumber(Long serviceNumber, Integer companyId) throws Exception {
		return ServiceEntriesDao.getServiceEntriesCountByNumber(serviceNumber, companyId);
	}
	
	@Override
	public List<ServiceEntriesDocument> getServiceEntriesDocumentList(long startLimit, long endLimit) throws Exception {
		return ServiceEntriesDocumentRepository.getServiceEntriesDocumentList(startLimit, endLimit);
	}
	
	@Override
	public long getServiceEntriesDocumentCount() throws Exception {
		return ServiceEntriesDocumentRepository.getServiceEntriesDocumentCount();
	}
	
	@Override
	public ValueObject getVehicleBreakDownDetails(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails				= null;
		ServiceEntriesDto			serviceEntriesDto		= null;
		List<ServiceEntriesDto> 	serviceEntriesList		= null;
		ValueObject					valueOutObject			= null;
		try {
			valueOutObject	= new ValueObject();
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			serviceEntriesDto	= new ServiceEntriesDto();
			serviceEntriesDto.setFromDate(valueObject.getString("startDate"));
			serviceEntriesDto.setToDate(valueObject.getString("startEnd"));
			serviceEntriesDto.setVid(valueObject.getInt("vehicleId"));
			serviceEntriesDto.setCompanyId(userDetails.getCompany_id());
			
			serviceEntriesList	=	iServiceEntriesDao.getServiceEntriesList(serviceEntriesDto);
			if(serviceEntriesList !=null) {
				
				for(ServiceEntriesDto entriesDto : serviceEntriesList) {
					entriesDto.setInvoiceDate(dateFormatAtt.format(entriesDto.getInvoiceDateOn()));
					entriesDto.setInvoiceDateStr(dateFormat.format(entriesDto.getInvoiceDateOn()));
					entriesDto.setServiceDate(AttendanceDay.format(entriesDto.getInvoiceDateOn()));
					entriesDto.setServiceMonth("" + (Integer.parseInt(AttendanceMonth.format(entriesDto.getInvoiceDateOn())) - 1));
					entriesDto.setServiceYear(AttendanceYear.format(entriesDto.getInvoiceDateOn()));
				}
			}
			valueOutObject.put("serviceEntriesList", serviceEntriesList);
			
			valueOutObject.put("vehicleId", valueObject.getInt("vehicleId"));
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Transactional
	public List<ServiceEntriesTasksToPartsDto> getServiceEntriesTasksTo_Most_Parts_Count_Report(Long GroupID,String dateRangeFrom, String dateRangeTo, Integer company_id, Integer location) throws Exception {
		List<ServiceEntriesTasksToPartsDto> Dtos = null;
		try {
		TypedQuery<Object[]> query = null;
		if(location > 0) {
			query = entityManager.createQuery(
					"select count(w.partid),w.partid,mp.partname,mp.partnumber,(w.quantity * w.parteachcost), w.quantity "
							+ "from ServiceEntriesTasksToParts w "
							+ "INNER JOIN MasterParts mp on mp.partid = w.partid "
							+ "INNER JOIN ServiceEntries wo on wo.serviceEntries_id = w.serviceEntries_id "
							+ "INNER JOIN Vehicle v on v.vid = wo.vid "
							+ "INNER JOIN VehicleGroup vg on vg.gid = v.vehicleGroupId "
							+ "where w.companyId =  '"+company_id+"'and v.vehicleGroupId = '"+GroupID+"' and wo.completed_date between '"+dateRangeFrom+"' "
							+ " and '"+dateRangeTo+"' group by w.partid ",
							Object[].class);
		}else {

			query = entityManager.createQuery(
					"select count(w.partid),w.partid,mp.partname,mp.partnumber,(w.quantity * w.parteachcost), w.quantity "
							+ "from ServiceEntriesTasksToParts w "
							+ "INNER JOIN MasterParts mp on mp.partid = w.partid "
							+ "INNER JOIN ServiceEntries wo on wo.serviceEntries_id = w.serviceEntries_id "
							+ "INNER JOIN Vehicle v on v.vid = wo.vid "
							+ "INNER JOIN VehicleGroup vg on vg.gid = v.vehicleGroupId "
							+ "where w.companyId =  '"+company_id+"'and v.vehicleGroupId = '"+GroupID+"' and wo.completed_date between '"+dateRangeFrom+"' "
							+ " and '"+dateRangeTo+"' group by w.partid ",
							Object[].class);
		
		}
		List<Object[]> results = query.getResultList();
		
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<ServiceEntriesTasksToPartsDto>();
			ServiceEntriesTasksToPartsDto list = null;
			for (Object[] result : results) {
				list = new ServiceEntriesTasksToPartsDto();

				list.setPartCount((Long) result[0]);
				list.setPartid((Long) result[1]);
				list.setPartname((String)result[2]);
				list.setPartnumber((String) result[3]);
				list.setTotalValuePartConsumed((double)result[4]);
				list.setQuantity((Double) result[5]);
			
				Dtos.add(list);
			}
		}
        
		return Dtos;
	}
	catch(Exception e) {
	}
		finally {
			
			Dtos 		= null;
			
		}
		return Dtos;
	}
	
	
	@Override
	public List<ServiceEntriesDto> getServiceEntriesServiceListOfMonth(TripSheetIncomeDto incomeDto) throws Exception {
		try {

			TypedQuery<Object[]> querySearch = entityManager.createQuery(
					"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, SR.totalservice_cost, SR.completed_date "
							+ " from ServiceEntries SR "
							+ " where  SR.vid = "+incomeDto.getVid()+" AND SR.completed_date between '"+incomeDto.getFromDate()+"' AND '"+incomeDto.getToDate()+"' "
							+ " AND SR.companyId = "+incomeDto.getCompanyId()+" AND SR.serviceEntries_statusId = "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE+" AND SR.markForDelete = 0",
					Object[].class);

			List<Object[]> results = querySearch.getResultList();

			List<ServiceEntriesDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceEntriesDto>();
				ServiceEntriesDto list = null;
				for (Object[] result : results) {
					list = new ServiceEntriesDto();

					list.setServiceEntries_id((Long) result[0]);
					list.setServiceEntries_Number((Long) result[1]);
					list.setVid((Integer) result[2]);
					list.setTotalservice_cost((Double) result[3]);
					list.setCompleted_date(dateFormat.format(result[4]));
					
					Dtos.add(list);
				}
			}
			return Dtos;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Long getTotalServiceEntriesCount(String startDate, String endDate,Integer companyId) throws Exception {
		
		Query queryt = 	null;
		queryt = entityManager.createQuery(
				"SELECT COUNT(SE.serviceEntries_id) "
						+ " From ServiceEntries as SE "
						+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
						+ " WHERE SE.invoiceDate between '"+startDate+"' AND '"+endDate+"' "
						+ " AND SE.markForDelete = 0 AND V.vStatusId <> 4 AND SE.companyId = "+companyId+" ",
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
		public HashMap<Integer, Long> getALLEmailServiceEntriesDailyWorkStatus(String startDate, String endDate)
				throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(SE.serviceEntries_id), SE.companyId "
								+ " From ServiceEntries as SE "
								+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
								+ " WHERE SE.invoiceDate between '"+startDate+"' AND '"+endDate+"' "
								+ " AND SE.markForDelete = 0 AND V.vStatusId <> 4 GROUP BY SE.companyId ",
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
		
		//Email System for Manager Start Service Entries
		@Override
		public List<CompanyDto> getALLEmailServiceEntriesDailyWorkForManagers(String startDate, String endDate)
				throws Exception {
						
			try{
				TypedQuery<Object[]> typedQuery = null;

									
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(SE.serviceEntries_id), C.company_id, C.company_name "
								+ "From ServiceEntries as SE "
								+ " INNER JOIN Company AS C on C.company_id = SE.companyId  "
								+ " WHERE SE.created between '"+startDate+"' AND '"+endDate+"'  AND SE.markForDelete = 0 "
								+ " Group by SE.companyId ",
								Object[].class);
				
				
				List<Object[]> results = typedQuery.getResultList();

				List<CompanyDto> serviceEntries = null;
				if (results != null && !results.isEmpty()) {
					
					serviceEntries = new ArrayList<CompanyDto>();
					CompanyDto list = null;
					for (Object[] result : results) {
						list = new CompanyDto();
					if(result !=null) {
						
						list.setTripSheetCount((long) 0);
						list.setRenewalReminderCount((long) 0);
						list.setServiceReminderCount((long) 0);
						list.setFuelEntriesCount((long) 0);						
						list.setWorkOrderCount((long) 0);
						
						if(result[0] != null) 
						list.setServiceEntriesCount((long) result[0]);						
						list.setCompany_id((int) result[1]);
						list.setCompany_name((String) result[2]);
						}
					serviceEntries.add(list);
					}
				}
				return serviceEntries;
			}
			catch(Exception e){
				throw e;						
			}			
		}	
		//Email System for Manager Stop Service Entries
		
		@Override
		public List<VehicleDto> getVehicleWiseServiceEntries(String query, int companyId) throws Exception {
			try{
				TypedQuery<Object[]> typedQuery = null;
									
				typedQuery = entityManager.createQuery(
						"SELECT SE.serviceEntries_id, SE.totalserviceROUND_cost, SE.vid, V.vehicle_registration, "
								+ " SE.serviceEntries_Number, SE.invoiceDate "
								+ " From ServiceEntries as SE "
								+ " INNER JOIN Vehicle V on V.vid = SE.vid "
								+ " WHERE SE.companyId = "+companyId+" AND SE.markForDelete = 0 "+query+" ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();

				List<VehicleDto> serviceEntries = null;
				if (results != null && !results.isEmpty()) {
					
					serviceEntries = new ArrayList<VehicleDto>();
					VehicleDto list = null;
					for (Object[] result : results) {
						list = new VehicleDto();
						list.setServiceEntries_id((long) result[0]);
						list.setTotalserviceROUND_cost((double) result[1]);
						list.setVid((int) result[2]);
						list.setVehicle_registration((String) result[3]);
						list.setVehicleServiceEntriesCount((long) 1);
						list.setWorkorders_id((long) 0);
						list.setTotalworkorder_cost((double) 0.0);
						
						list.setServiceEntriesNumber((long) result[4]);
						list.setSeInvoiceDate(dateFormat.format((Timestamp) result[5]));
						
						serviceEntries.add(list);
					}
				}
				return serviceEntries;
			}
			catch(Exception e){
				throw e;						
			}			
		}
		
		@Override
		@Transactional
		public ValueObject saveServEntInvDetails(ValueObject valueObject) throws Exception {
			CustomUserDetails						userDetails				= null;
			String									invoiceDate				= null;
			String									invoiceNumber			= null;
			String									paidDate				= null;
			long									serviceEntries_id		= 0;
			try {
				userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				serviceEntries_id 				= valueObject.getLong("seId");	
				invoiceNumber 					= valueObject.getString("invoiceNumber",null);
				
				java.util.Date lastModDate 		= new java.util.Date();
				Timestamp lastMod 				= new java.sql.Timestamp(lastModDate.getTime());
				
				invoiceDate 					= valueObject.getString("invoicestartDate",null);
				java.util.Date date 		 	= dateFormat.parse(invoiceDate);
				Timestamp sqlInvoiceDate 		= new java.sql.Timestamp(date.getTime());
				
				paidDate 						= valueObject.getString("servicepaiddate",null);
				java.util.Date date1 		 	= dateFormat.parse(paidDate);
				Timestamp sqlpaidDate 			= new java.sql.Timestamp(date1.getTime());
				
				entityManager.createQuery(
						" UPDATE ServiceEntries AS SE SET SE.invoiceDate = '"+sqlInvoiceDate+"', SE.invoiceNumber = '"+ invoiceNumber +"' , "
								+ " SE.service_paiddate = '"+sqlpaidDate+"', SE.lastupdated = '"+lastMod+"', SE.lastModifiedById = "+userDetails.getId()+"  "
								+ " WHERE SE.serviceEntries_id = " + serviceEntries_id + " "
								+ " AND SE.companyId = " + userDetails.getCompany_id() + " "
								+ " AND SE.markForDelete=0  ")
						.executeUpdate();
				
				valueObject.put("InvoiceUpdated", true);
					
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public ValueObject getSEInvoiceDeatils(ValueObject valueObject) throws Exception {
			CustomUserDetails						userDetails				= null;
			long									serviceEntries_id		= 0;
			ServiceEntriesDto						ServiceEntriesDto		= null;
			ServiceEntriesDto						work					= null;
			try {
				userDetails			 = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				serviceEntries_id 	 = valueObject.getLong("seId");
				
				ServiceEntriesDto = getServiceEntriesDetails(serviceEntries_id, userDetails.getCompany_id());
				work 			  = WOBL.getServEntInvoiceDetail(ServiceEntriesDto);
				
				valueObject.put("ServiceEntriesDto", work);
					
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public HashMap<Integer, Long> getALLCompletedServiceEntriesByDate(String startDate, String endDate)
				throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(SE.serviceEntries_id), SE.companyId "
								+ " From ServiceEntries as SE "
								+ " WHERE SE.completed_date between '"+startDate+"' AND '"+endDate+"' AND SE.markForDelete = 0 GROUP BY SE.companyId ",
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
		public Long getAllOpenServiceEntriesByDateCount(int companyId) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
				"SELECT COUNT(SE.serviceEntries_id) "
						+ " From ServiceEntries as SE "
						+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
						+ " WHERE SE.serviceEntries_statusId IN ("+ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN+", "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS+") "
						+ " AND SE.markForDelete = 0 AND V.vStatusId <> 4  AND SE.companyId = "+companyId+" AND SE.invoiceDate <> null ",
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
		public ValueObject getServiceEntriesDuePaymentCountAndAmount(int companyId) throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				ValueObject				map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(SE.serviceEntries_id), SUM(SE.totalserviceROUND_cost) "
								+ " From ServiceEntries as SE "
								+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
								+ " WHERE SE.service_vendor_paymentdate is null AND service_paymentTypeId = "+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" "
								+ " AND SE.markForDelete = 0 AND V.vStatusId <> 4  AND SE.companyId = "+companyId+"  ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();

				if (results != null && !results.isEmpty()) {
					
					map	= new ValueObject();
					
					for (Object[] result : results) {
						
						map.put("duePaymentCount", (Long)result[0]);
						
						if(result[1] != null) {
							map.put("duePaymentAmount", (double)result[1]);
						} else {
							map.put("duePaymentAmount", (double)0);
						}
						
					}
				}
				
				return map;
			} catch (Exception e) {
				throw e;
			}
		}
		
		
		@Override
		public HashMap<Integer, Long> getALLOpenServiceEntriesByDate() throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(SE.serviceEntries_id), SE.companyId "
								+ " From ServiceEntries as SE "
								+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
								+ " WHERE SE.serviceEntries_statusId IN ("+ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN+", "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS+") "
								+ " AND SE.markForDelete = 0 AND V.vStatusId <> 4  GROUP BY SE.companyId ",
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
		public Long getALLOpenServiceEntriesBetweenDates(String startDate, String endDate,Integer companyId) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
					"SELECT COUNT(SE.serviceEntries_id) "
							+ " From ServiceEntries as SE "
							+ " WHERE SE.serviceEntries_statusId IN ("+ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN+", "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS+")"
							+ " AND SE.invoiceDate between '"+startDate+"' AND '"+endDate+"' "
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
		public HashMap<Integer, Long> getALLOpenSevenDaysServiceEntriesByDate(String startDate, String endDate)
				throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(SE.serviceEntries_id), SE.companyId "
								+ " From ServiceEntries as SE "
								+ " WHERE SE.serviceEntries_statusId IN ("+ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN+", "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS+")"
										+ " AND SE.created between '"+startDate+"' AND '"+endDate+"' AND SE.markForDelete = 0 GROUP BY SE.companyId ",
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
		public Long getALLOpenServiceEntriesByDate(String startDate, Integer companyId) throws Exception {
			
			Query queryt = 	null;
			queryt = entityManager.createQuery(
				"SELECT COUNT(SE.serviceEntries_id) "
						+ " From ServiceEntries as SE "
						+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
						+ " WHERE SE.serviceEntries_statusId IN ("+ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN+", "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS+")"
						+ " AND SE.invoiceDate < '"+startDate+"' AND SE.markForDelete = 0 AND V.vStatusId <> 4 AND SE.companyId = "+companyId+" ",
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
		public HashMap<Integer, Long> getALLOpen15MoreDaysServiceEntriesByDate(String startDate) throws Exception {
			try {
				TypedQuery<Object[]> 	typedQuery = null;
				HashMap<Integer, Long>	map		   = null;
				
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(SE.serviceEntries_id), SE.companyId "
								+ " From ServiceEntries as SE "
								+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
								+ " WHERE SE.serviceEntries_statusId IN ("+ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN+", "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS+")"
								+ " AND SE.created < '"+startDate+"' AND SE.markForDelete = 0 AND V.vStatusId <> 4 GROUP BY SE.companyId ",
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
	
		public long getSECountByStatusID(String startDate, String endDate,Integer companyId , short status) throws Exception {
			TypedQuery<Object[]> 	typedQuery 	= null;
			long  					count		= 0;
			try {
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(SE.serviceEntries_id), SE.companyId "
								+ " From ServiceEntries as SE "
								+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
								+ " WHERE SE.invoiceDate between '"+startDate+"' AND '"+endDate+"' "
								+ " AND SE.serviceEntries_statusId="+status+" AND SE.companyId ="+companyId+""
								+ " AND SE.markForDelete = 0  AND V.vStatusId <> 4 ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();
				if (results != null && !results.isEmpty()) {
					for (Object[] result : results) {
						 count = (Long)result[0];
					}
				}
				
				return count;
			} catch (Exception e) {
				throw e;
			}
		}
		public long getSECompletedCount(String startDate, String endDate,Integer companyId , short status) throws Exception {
			TypedQuery<Object[]> 	typedQuery 	= null;
			long  					count		= 0;
			try {
				typedQuery = entityManager.createQuery(
						"SELECT COUNT(SE.serviceEntries_id), SE.companyId "
								+ " From ServiceEntries as SE "
								+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
								+ " WHERE SE.completed_date between '"+startDate+"' AND '"+endDate+"' "
								+ " AND SE.serviceEntries_statusId="+status+" AND SE.companyId ="+companyId+""
								+ " AND SE.markForDelete = 0  AND V.vStatusId <> 4 ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();
				if (results != null && !results.isEmpty()) {
					for (Object[] result : results) {
						count = (Long)result[0];
					}
				}
				
				return count;
			} catch (Exception e) {
				throw e;
			}
		}
		
		public List<ServiceEntriesDto> getSEDetailsBetweenDatesByStatus(Integer companyID, String SEStatusQuery) throws Exception {
			TypedQuery<Object[]> 		typedQuery 	= null;
			ServiceEntriesDto 			list 		= null;
			List<ServiceEntriesDto> 	Dtos 		= null;
			
			try {
				typedQuery = entityManager.createQuery(
						"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, V.vehicle_registration, VD.vendorName, SE.invoiceNumber, "
								+ " SE.created, SE.serviceEntries_statusId, SE.vid, SE.invoiceDate, SE.totalserviceROUND_cost  "
								+ " From ServiceEntries as SE "
								+ " INNER JOIN Vehicle AS V ON V.vid = SE.vid AND V.vStatusId <> 4 "
								+ " INNER JOIN Vendor AS VD ON VD.vendorId = SE.vendor_id "
								+ " WHERE "+SEStatusQuery+" "
								+ " AND SE.companyId ="+companyID+" AND SE.markForDelete = 0  ",
								Object[].class);
				
				List<Object[]> results = typedQuery.getResultList();
				
				
				if (results != null && !results.isEmpty()) {
					Dtos = new ArrayList<>();
					
					for(Object[] result : results) {
						list = new ServiceEntriesDto();
						
						list.setServiceEntries_id((Long)result[0]);
						list.setServiceEntries_Number((Long)result[1]);
						list.setVehicle_registration((String)result[2]);
						list.setVendor_name((String)result[3]);
						list.setInvoiceNumber((String)result[4]);
						list.setCreated(dateFormat.format((Timestamp)result[5]));
						list.setServiceEntries_statusId((short)result[6]);
						list.setServiceEntries_status(ServiceEntriesType.getStatusName((short)result[6]));
						list.setVid((Integer)result[7]);
						
						if(result[8] != null)
						list.setInvoiceDate(dateFormat.format((Timestamp)result[8]));
						if(result[9] != null)
						list.setTotalserviceROUND_cost(Double.parseDouble(toFixedTwo.format(result[9])));
						
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
		public void updatePaymentApprovedSEDetails(Long ApprovalInvoice_ID,
				Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId) throws Exception {
			entityManager.createQuery("  UPDATE ServiceEntries SET service_vendor_approvalID=" + Approval_ID
					+ ",expectedPaymentDate='" + expectedPaymentDate + "', service_vendor_paymodeId='" + approval_Status + "' "
					+ " WHERE serviceEntries_id IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
			
		}
		@Transactional
		public void updatePaymentPaidSEDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId, short service_vendor_paymodeId) throws Exception {
			
			entityManager.createQuery("UPDATE ServiceEntries SET service_vendor_paymentdate='" + approval_date+"' , service_vendor_paymodeId="+service_vendor_paymodeId+" WHERE serviceEntries_id IN (" + ApprovalInvoice_ID + ") AND companyId = "+companyId+" ").executeUpdate();
		}
		
		@SuppressWarnings("unchecked")
		@Override
		public List<TripSheetExpenseDto> getServiceEntriesListForTallyImport(String fromDate, String toDate, Integer companyId,
				String tallyCompany) throws Exception {
			try {
				Query query = entityManager.createQuery(
						"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, VA.approvalCreateDate, V.vendorName,"
						+ " VH.vehicle_registration, VH.ledgerName, TC.companyName, SE.created, SE.service_paymentTypeId, SE.vid, "
						+ " VSD.subApprovalpaidAmount, TE.expenseName, SE.isPendingForTally, SE.invoiceDate, SE.invoiceNumber "
						+ " FROM ServiceEntries SE "
						+ " INNER JOIN Vehicle VH ON VH.vid = SE.vid "
						+ " LEFT JOIN Vendor AS V ON V.vendorId = SE.vendor_id"
						+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = SE.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
						+ " INNER JOIN TripExpense TE ON TE.expenseID = SE.tallyExpenseId "
						+ " INNER JOIN VendorApproval VA ON VA.approvalId = SE.service_vendor_approvalID AND VA.markForDelete = 0"
						+ " INNER JOIN VendorSubApprovalDetails VSD ON VSD.approvalId = VA.approvalId AND VSD.invoiceId = SE.serviceEntries_id AND VSD.approvalPlaceId = "+ApprovalType.APPROVAL_TYPE_SERVICE_ENTRIES+""
						+ " WHERE VA.approvalCreateDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
						+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND VSD.subApprovalpaidAmount > 0"
						+ " AND SE.serviceEntries_statusId = "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE+"");
				
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
							select.setVendorName((String) vehicle[3]);
							select.setVehicle_registration((String) vehicle[4]);
							select.setLedgerName((String) vehicle[5]);
							select.setTallyCompanyName((String) vehicle[6]);
							select.setCreatedOn((Timestamp) vehicle[7]);
							select.setExpenseFixedId((short) vehicle[8]);
							select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
							select.setVid((Integer) vehicle[9]);
							select.setExpenseAmount((Double) vehicle[10]);
							select.setExpenseName((String) vehicle[11]);
							select.setPendingForTally((boolean) vehicle[12]);
							select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_SERVICE_ENTRIES);
							select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
							
							
							select.setTripSheetId(select.getTripExpenseID());
							if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
								select.setCredit(true);
							}else {
								select.setCredit(false);
							}
							
							if(select.getVendorName() == null ) {
								select.setVendorName("--");
							}
							
							if(vehicle[2] != null ) {
								select.setCreatedStr(dateFormat.format((java.util.Date)vehicle[2]));
							}
							
							 select.setRemark("Service Entries On Vehicle "+select.getVehicle_registration()+" Invoice Date: "+dateFormat.format((java.util.Date)vehicle[13])+ " Invoice Number : "+(String)vehicle[14]+" "+  " From: "+select.getVendorName());
							
							if(select.getTallyCompanyName() == null) {
								select.setTallyCompanyName("--");
							}
							if(select.getLedgerName() == null) {
								select.setLedgerName("--");
							}
							
							select.setTripSheetNumberStr("SE-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
							
							Dtos.add(select);
						}
					}
					
				}
				return Dtos;
				
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public Long getServiceEntriesTallyPendingCount(Integer companyId, Integer vid) throws Exception {
		
			Query queryt = 	null;
			queryt = entityManager.createQuery(
					"SELECT Count(V.serviceEntries_id) "
							+ " From ServiceEntries  AS V "
							+ " where V.vid = "+vid+" AND V.isPendingForTally = 0 AND V.markForDelete = 0  AND V.totalserviceROUND_cost > 0"
							+ " AND V.companyId = "+companyId+" AND V.serviceEntries_statusId ="+ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE+"",
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
		public ValueObject getServiceEntiresPendingList(ValueObject valueObject) throws Exception {
			try {
				
				valueObject.put("companyList", tallyIntegrationService.getTallyCompanyListForCompany(valueObject.getInt("companyId")));
				valueObject.put("serviceList", iServiceEntriesDao.getServiceEntriesPendingList(valueObject.getInt("companyId"), valueObject.getInt("vid")));
				valueObject.put("expenseList", tripExpenseService.listTripExpense(valueObject.getInt("companyId")));
				return valueObject;
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		@Transactional(readOnly = false)
		public ValueObject saveServiceExpenseToTrip(ValueObject valueObject) throws Exception {
			ArrayList<ValueObject> 								dataArrayObjColl 				= null;
			TripSheet											tripSheet						= null;
			TripSheetExpense									tripSheetExpense				= null;
			HashMap<String, Object> 							configuration					= null;
			VehicleProfitAndLossTxnChecker						profitAndLossExpenseTxnChecker	= null;	
			CustomUserDetails									userDetails						= null;
			HashMap<Long, VehicleProfitAndLossTxnChecker>		expenseTxnCheckerHashMap		= null;
			HashMap<Long, TripSheetExpense> 					tripSheetExpenseHM				= null;
			try {
				
				tripSheet	= tripSheetService.getTripSheet(valueObject.getLong("tripSheetId"), valueObject.getInt("companyId"));
				if(tripSheet != null) {
					
					userDetails	= new CustomUserDetails(tripSheet.getCompanyId(), valueObject.getLong("userId"));
					configuration	= companyConfigurationService.getCompanyConfiguration(tripSheet.getCompanyId(), PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
					UserProfileDto Orderingname =	userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(valueObject.getLong("userId"));
					dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("seDetails");
					if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
						for (ValueObject object : dataArrayObjColl) {
							tripSheetExpense	= new TripSheetExpense();
							tripSheetExpense.setExpenseId(object.getInt("expenseId",0));
							 tripSheetExpense.setExpenseAmount(object.getDouble("serviceCost", 0));
							 tripSheetExpense.setExpensePlaceId(Orderingname.getBranch_id());
							 tripSheetExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_SE);
							 tripSheetExpense.setCreatedById(valueObject.getLong("userId"));
							 tripSheetExpense.setTripsheet(tripSheet); 
							 tripSheetExpense.setCreated(new java.util.Date());
							 tripSheetExpense.setCompanyId(tripSheet.getCompanyId());
							 tripSheetExpense.setTallyCompanyId(object.getLong("tallyCompanyId", 0));
							 tripSheetExpense.setTransactionId(object.getLong("serviceEntriesId", 0));
							 tripSheetExpense.setVendorId(object.getInt("vendorId", 0));
							 if(object.getShort("paymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
								 tripSheetExpense.setCredit(true);
							 }
							 tripSheetExpense.setBalanceAmount(tripSheetExpense.getExpenseAmount());
							  tripSheetService.addTripSheetExpense(tripSheetExpense);
							  iServiceEntriesDao.updatePendingStatus(object.getLong("serviceEntriesId"), (short) 1);
							  
							  if(tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
								  ValueObject exeObject	= new ValueObject();
									expenseTxnCheckerHashMap	= new HashMap<>();
									tripSheetExpenseHM			= new HashMap<>();
									
								  exeObject.put(VehicleProfitAndLossDto.COMPANY_ID, tripSheet.getCompanyId());
								  exeObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, tripSheet.getTripSheetID());
								  exeObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
								  exeObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
								  exeObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, tripSheet.getVid());
								  exeObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, (int)configuration.get("expenseId"));
								  exeObject.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, tripSheetExpense.getTripExpenseID());

									profitAndLossExpenseTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(exeObject);

									vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossExpenseTxnChecker);
									expenseTxnCheckerHashMap.put(profitAndLossExpenseTxnChecker.getVehicleProfitAndLossTxnCheckerId(), profitAndLossExpenseTxnChecker);
									tripSheetExpenseHM.put(tripSheetExpense.getTripExpenseID(), tripSheetExpense);
							  }
							  
							 
						}
						
						tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(tripSheet
								  .getTripSheetID(), tripSheet.getCompanyId());
						
						
						
						if(tripSheetExpenseHM != null && !tripSheetExpenseHM.isEmpty()) {
							
							ValueObject	valueObjectExe	= new ValueObject();
							valueObjectExe.put("tripSheet", tripSheet);
							valueObjectExe.put("userDetails", userDetails);
							valueObjectExe.put("tripSheetExpenseHM", tripSheetExpenseHM);
							valueObjectExe.put("expenseTxnCheckerHashMap", expenseTxnCheckerHashMap);
							valueObjectExe.put("isTripSheetClosed", true);
							
							vehicleProfitAndLossService.runThreadForTripSheetExpenses(valueObjectExe);
						}
					}
				}
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
			
		}
		public ServiceEntries getDeletedSEById(Long ServiceEntries_id, Integer companyid) throws Exception {
			ServiceEntries  serviceEntries = null;
			try {
				serviceEntries = ServiceEntriesDao.deleteServiceEntries(ServiceEntries_id, companyid);
				return serviceEntries;
			} catch (Exception e) {
				LOGGER.error("Err"+e);
				throw e;
			}
			
		}

		public ServiceEntries checkDuplicateEntriesDetails(ValueObject valueObject, Integer companyId)throws Exception{
			try {
				Long serviceEntries_id = valueObject.getLong("serviceEntryId",0);
				int vid =valueObject.getInt("vid",0);
				int vendorId = valueObject.getInt("vendorId",0);
				String invNo = valueObject.getString("invoiceNumber","");
				
			  	java.util.Date date = dateFormat.parse(valueObject.getString("invoiceDate"));
				java.sql.Date InDate = new Date(date.getTime());
					
				Query query = entityManager.createQuery(
					    "SELECT SE.serviceEntries_id, SE.vid, SE.vendor_id, SE.invoiceNumber, SE.invoiceDate " +
					    "FROM ServiceEntries SE WHERE " +
					    "SE.serviceEntries_id != :serviceEntries_id AND SE.vid = :vid AND SE.vendor_id = :vendorId AND SE.invoiceNumber = :invNo " +
					    "AND SE.invoiceDate = :InDate AND SE.companyId = :companyId AND SE.markForDelete = 0"
					);
					query.setParameter("serviceEntries_id", serviceEntries_id);
				  	query.setParameter("vid", vid);
			       		query.setParameter("vendorId", vendorId);
			        	query.setParameter("invNo", invNo);
			        	query.setParameter("InDate", InDate);
			        	query.setParameter("companyId", companyId);
				
				Object[] result = null;
				try {
					query.setMaxResults(1);
					result = (Object[]) query.getSingleResult();
				} catch (NoResultException nre) {
				}

				ServiceEntries	entries = null;
				if (result != null) {
					entries = new ServiceEntries();
					entries.setServiceEntries_id((Long) result[0]);
				}
				return entries;
			  }catch (Exception e){
				e.printStackTrace();
				throw e;
			}
			
		}	
		
		@Override
		@Transactional(readOnly = false)
		public ValueObject saveServiceEntriesDetails(ValueObject valueObject) throws Exception {
			CustomUserDetails									userDetails					= null;
			List<ServiceEntries> 								validateSE					= null;
			HashMap<String, Object> 							config						= null;
			HashMap<String, Object> 							SEconfig					= null;
			boolean 											ServiceEntriesStatus 		= false;
			HashMap<Long, ServiceReminderDto> 					dtosMap1 					= null;
			ServiceReminderDto									serviceReminderDto 			= null;
			Integer												workOrderSubTaskId			= 0;
			List<ServiceReminderDto>							serviceList					= null;
			Issues												issue						= null;
			IssuesTasks											issueTask					= null;
			ServiceEntries										serviceEntries				= null;
			ServiceEntries										validateServiceEntries		= null;
			ServiceEntries										validateDuplicate 		= null;
			
			try {
				
				
				userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				config 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				SEconfig 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
				
				if((boolean) SEconfig.getOrDefault("validateInvoiceNumber", false)) {
					validateServiceEntries = getServiceEntriesByInvoiceNumber(valueObject.getString("invoiceNumber"), userDetails.getCompany_id());
					
					if(validateServiceEntries != null) {
						valueObject.put("duplicateInvoiceNumber", true);
						return valueObject;
					}
				}
				
				
				validateDuplicate = checkDuplicateEntriesDetails(valueObject,userDetails.getCompany_id());
				if(validateDuplicate != null) {
					valueObject.put("validateDuplicateSE", true);
					return valueObject;
				}
				
				valueObject.put("userDetails", userDetails);
				
				VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("vid"));
				if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE 
						|| CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE
						|| CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACCIDENT
						|| CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP) {
					ServiceEntriesStatus = true;
				} 
				
				if (ServiceEntriesStatus) {
					
					validateSE = validate_ServiceEntries(valueObject.getInt("vendorId"),valueObject.getString("invoiceNumber"),
									  valueObject.getInt("vid"), userDetails.getCompany_id());
					
					if((boolean) config.getOrDefault(WorkOrdersConfigurationConstants.SHOW_ADD_INVOICE_DEATILS, false)) {
						validateSE.clear();
					}
					
					if (validateSE != null && !validateSE.isEmpty()) {
						valueObject.put("already", true);
						return valueObject;
					} else {
						
						ServiceEntries service = WOBL.createServiceEntries(valueObject);
						
						if(service.getVendor_id() == 0) {
							int newVendorId = saveNewVendorDetails(valueObject);
							service.setVendor_id(newVendorId);
						}
						
						ServiceEntriesSequenceCounter counter = serviceEntriesSequenceService.findNextServiceEntries_Number(userDetails.getCompany_id());
						
						if(counter == null) {
							valueObject.put("sequenceNotFound", true);
							return valueObject;
						}
						
						if(valueObject.getLong("accidentId",0) > 0) {
							service.setAccidentId(valueObject.getLong("accidentId",0));
						}
						
						service.setServiceEntries_Number(counter.getNextVal());
						serviceEntries = addServiceEntries(service);
						
						valueObject.put("serviceEntryCreated", true);
						valueObject.put("serviceEntry", service);
						valueObject.put("serviceEntryId", service.getServiceEntries_id());
						
						// Save Task Details
						saveServiceEntryTasks(valueObject);
						
						String service_id[]	= valueObject.getString("serviceReminderId").split(",");
						
						workOrderSubTaskId = valueObject.getInt("workOrderSubTaskId",0);
						
						if (service_id != null && !valueObject.getString("serviceReminderId").isEmpty()) {
							dtosMap1 = workOrdersService.getJobtypeAndSubtypeFromServiceReminderId(valueObject.getString("serviceReminderId"), userDetails.getCompany_id());
							ServiceReminderService.updateServiceIdInServiceReminder(valueObject);
							for (int j = 0; j < service_id.length; j++) {
								
								serviceReminderDto	= dtosMap1.get(Long.parseLong(service_id[j]));
								
								if(!workOrderSubTaskId.equals(serviceReminderDto.getServiceSubTypeId())) {
								
									ServiceEntriesTasks WRTaskServReminder = new ServiceEntriesTasks();
									
									if(serviceReminderDto.getServiceTypeId() != null) {
									WRTaskServReminder.setService_typetaskId(serviceReminderDto.getServiceTypeId());	
									}
									if(serviceReminderDto.getServiceSubTypeId() != null) {
									WRTaskServReminder.setService_subtypetask_id(serviceReminderDto.getServiceSubTypeId());	
									}
									
									WRTaskServReminder.setServiceEntries(service);
									WRTaskServReminder.setVid(service.getVid());
									WRTaskServReminder.setTotalpart_cost(0.0);
									WRTaskServReminder.setTotallaber_cost(0.0);
									WRTaskServReminder.setTotaltask_cost(0.0);
									WRTaskServReminder.setCompanyId(userDetails.getCompany_id());
									WRTaskServReminder.setService_id(Long.parseLong(service_id[j]+""));
									
									addServiceEntriesTask(WRTaskServReminder);
								}
								
							}
						}
						
						Integer currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(service.getVid());
						if(currentODDMETER != null && service.getVehicle_Odometer() != null) {
							if (currentODDMETER < service.getVehicle_Odometer()) {
								
								vehicleService.updateCurrentOdoMeter(service.getVid(), service.getVehicle_Odometer(), userDetails.getCompany_id());
								// update current Odometer update Service Reminder
								ServiceReminderService.updateCurrentOdometerToServiceReminder(service.getVid(),
										service.getVehicle_Odometer(), userDetails.getCompany_id());
								
								serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(service.getVid(), userDetails.getCompany_id(), userDetails.getId());
								if(serviceList != null) {
									for(ServiceReminderDto list : serviceList) {
										
										if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
											
											ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
										}
									}
								}
								
								VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToServiceEntries(service);
								vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
								vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
							}else if(currentODDMETER.equals(service.getVehicle_Odometer())) {
								VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToServiceEntries(service);
								vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
								vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
							}
						}
						
						Date dateTime = DateTimeUtility.getCurrentDateTime();

						if(valueObject.getLong("issueId",0) > 0) {
							issuesService.update_Create_SE_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_SE_CREATED, userDetails.getId(), dateTime, serviceEntries.getServiceEntries_id(), valueObject.getLong("issueId"));
							
							issue 		= issuesService.getIssueDetailsByIssueId(valueObject.getLong("issueId"), userDetails.getCompany_id());
							if(issue != null) {
								issueTask 	= issuesBL.prepareIssuesTaskDetails(issue,userDetails);
								issuesService.registerNew_IssuesTasks(issueTask);
								
							}
						}
						
						if(valueObject.getLong("accidentId",0) > 0) {
							if((boolean) config.getOrDefault("multipleQuotation", false)) {
								accidentDetailsService.saveAccedentServiceDetails(serviceEntries.getServiceEntries_id(),serviceEntries.getServiceEntries_Number() ,valueObject.getLong("accidentId",0),AccidentStatus.SERVICE_TYPE_SE,userDetails.getCompany_id());
							}else{
								accidentDetailsService.updateAccidentDetailsServiceDetails(serviceEntries.getServiceEntries_id(), AccidentStatus.SERVICE_TYPE_SE, valueObject.getLong("accidentId",0));
							} 
							accidentDetailsService.updateAccidentDetailsStatus(valueObject.getLong("accidentId",0), AccidentStatus.ACCIDENT_STATUS_QUOTATION_CREATED);
						}
					}
				} else {
					if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SOLD) {
						valueObject.put("inSold", true);
					} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE) {
						valueObject.put("inActive", true);
					} else if (CheckVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SURRENDER) {
						valueObject.put("inSurrender", true);
					}
				}
				
				return valueObject;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			
		}
		
		public int saveNewVendorDetails(ValueObject object) throws Exception {
			CustomUserDetails 				 userDetails 				= null;
			java.util.Date 					 currnetDate				= null;
			try {
				userDetails 		= (CustomUserDetails) object.get("userDetails");
				currnetDate 		= new java.util.Date();
				
				Vendor 					vendor 			= new Vendor();
				VendorSequenceCounter	sequenceCounter = vendorSequenceService.findNextVendorNumber(userDetails.getCompany_id());
				
				vendor.setVendorTermId(PaymentTypeConstant.PAYMENT_TYPE_CASH);
				vendor.setVendorNumber((int) sequenceCounter.getNextVal());
				vendor.setVendorGSTRegisteredId(VendorGSTRegistered.VENDOR_GST_NOT_REGISTERED);
				vendor.setCompanyId(userDetails.getCompany_id());
				vendor.setCreated(currnetDate);
				vendor.setLastupdated(currnetDate);
				vendor.setVendorRemarks("This Create Service Entries To Vendor");
				vendor.setVendorName(object.getString("enterVendorName"));
				vendor.setVendorLocation(object.getString("enterVendorLocation"));
				vendor.setAutoVendor(true);
				
				VendorType VenType = VendorTypeService.getVendorType("SERVICE-VENDOR", userDetails.getCompany_id());
				if(VenType != null) {
					vendor.setVendorTypeId(VenType.getVendor_Typeid());
				}else {
					vendor.setVendorTypeId(0);
				}
				vendorService.addVendor(vendor);
				
				return vendor.getVendorId();
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@SuppressWarnings("unchecked")
		public void saveServiceEntryTasks(ValueObject object) throws Exception {
			CustomUserDetails 				 userDetails 				= null;
			ArrayList<ValueObject> 			 dataArrayObjColl 			= null;
			JobSubType 						 JobSubType 				= null;
			ServiceEntries 					 serviceEntry				= null;
			Integer							 workOrderSubTaskId			= 0;
			try {
				userDetails 		= (CustomUserDetails) object.get("userDetails");
				serviceEntry		= (ServiceEntries) object.get("serviceEntry");
				
				List<JobType> jobTypeList = JobTypeService.listJobTypeByCompanyId(userDetails.getCompany_id());
				
				dataArrayObjColl	= (ArrayList<ValueObject>) object.get("serviceEntriesTaskDetails");
				
				if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
					
					//workOrderSubTaskId = dataArrayObjColl.get(0).getInt("serviceSubJobs");
					//object.put("workOrderSubTaskId", workOrderSubTaskId);
					
					for (ValueObject service : dataArrayObjColl) {
						if(service != null) {
							if(service.getString("serviceSubJobs") != null && service.getString("serviceSubJobs") != "") {
								String [] arr =service.getString("serviceSubJobs").split(",");
								for(String w :arr) {
									if(Integer.parseInt(w)>0) {
										ServiceEntriesTasks  WRTask = new ServiceEntriesTasks();
										
										WRTask.setService_typetaskId(service.getInt("jobType"));
										
										if(service.getString("jobType") != null && service.getString("serviceSubJobs") != null) {
											if(service.getString("serviceSubJobs") != "") {
												JobSubType = JobSubTypeService.getJobSubType(Integer.parseInt(w));
											}
										}
										
										if (JobSubType != null) {
											WRTask.setService_subtypetask_id(Integer.parseInt(w));
										}
										
										if(service.getString("remark") != null) {
											WRTask.setTaskRemark(service.getString("remark"));							
										}
										
										WRTask.setVid(serviceEntry.getVid());
										WRTask.setServiceEntries(serviceEntry);
										WRTask.setTotalpart_cost(0.0);
										WRTask.setTotallaber_cost(0.0);
										WRTask.setTotaltask_cost(0.0);
										WRTask.setCompanyId(userDetails.getCompany_id());
										
										addServiceEntriesTask(WRTask);
									}
									else {
										
										if(service.getString("service_ROT") != null && !service.getString("service_ROT","").trim().equalsIgnoreCase("0") ) {
											
											ServiceEntriesTasks  WRTask = new ServiceEntriesTasks();
											JobSubType DocType = new JobSubType();
											WRTask.setService_typetaskId(service.getInt("jobType"));
											DocType.setJob_Type(service.getString("jobType"));
											for (JobType jobType : jobTypeList) {
												if (jobType.getJob_Type().equalsIgnoreCase(service.getString("jobType"))) {
													DocType.setJob_TypeId(jobType.getJob_id());
													DocType.setJob_Type(jobType.getJob_Type());
													break;
												}
											}
											DocType.setJob_ROT(service.getString("service_ROT"));
											if(!service.getString("service_ROT_number").trim().equalsIgnoreCase("0"))
												DocType.setJob_ROT_number(service.getString("service_ROT_number"));
											DocType.setJob_ROT_hour("" + 0);
											DocType.setJob_ROT_amount("" + 0);
											DocType.setCompanyId(userDetails.getCompany_id());
											DocType.setCreatedBy(userDetails.getEmail());
											DocType.setCreatedOn(new Timestamp(System.currentTimeMillis()));
											DocType.setCreatedById(userDetails.getId());
											DocType.setJob_ROT_Service_Reminder(false);
											
											List<JobSubType> validate = JobSubTypeService.ValidateJobSubType(service.getString("service_ROT"),
													service.getString("jobType"), userDetails.getCompany_id());
											
											if (validate != null && !validate.isEmpty()) {
												
												for (JobSubType jobSubTypeVD : validate) {
													WRTask.setService_subtypetask_id(jobSubTypeVD.getJob_Subid());
													break;
												}
												
											} else {
												JobSubTypeService.addJobSubType(DocType);
												WRTask.setService_subtypetask_id(DocType.getJob_Subid());
											}
											
											
											
											if(service.getString("remark") != null) {
												WRTask.setTaskRemark(service.getString("remark"));							
											}
											
											WRTask.setVid(serviceEntry.getVid());
											WRTask.setServiceEntries(serviceEntry);
											WRTask.setTotalpart_cost(0.0);
											WRTask.setTotallaber_cost(0.0);
											WRTask.setTotaltask_cost(0.0);
											WRTask.setCompanyId(userDetails.getCompany_id());
											
											addServiceEntriesTask(WRTask);
											
										}
									}
								}
							} 
						}
					}
				}	
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Override
		public ValueObject getServiceEntryDetails(ValueObject valueObject) throws Exception {
			CustomUserDetails 						userDetails								= null;
			ServiceEntriesDto 						serviceEntries							= null;
			try {
				userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				serviceEntries 			= getServiceEntriesDetails(valueObject.getLong("serviceEntryId"), userDetails.getCompany_id());
				
				ServiceEntriesDto work = WOBL.Show_ServiceEntries(serviceEntries);
				valueObject.put("ServiceEntries", work);
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject saveServiceEntryPartDetails(ValueObject valueObject) throws Exception {
			CustomUserDetails 						userDetails								= null;
			ServiceEntriesTasksToParts 				workTaskToParts							= null;
			HashMap<String, Object>     			config						= null;

			try {
				userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				config	 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
				valueObject.put("allowGSTbifurcation", config.get("allowGSTbifurcation"));
				
				workTaskToParts 		= WOBL.saveServiceEntryPartDetails(valueObject);
				
				workTaskToParts.setCompanyId(userDetails.getCompany_id());
				addServiceEntriesTaskToParts(workTaskToParts);
				
				updateServiceEntriesTask_TotalPartCost(workTaskToParts.getServicetaskid());
				updateServiceEntriesMainTotalCost(valueObject.getLong("serviceEntryId"));
				
				java.sql.Date updateDate = new java.sql.Date( new java.util.Date().getTime());
				
				updateServiceEntriesProcess(ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS,
						valueObject.getLong("serviceEntryId"), userDetails.getCompany_id(), updateDate, userDetails.getId());
				
				valueObject.put("partAdded", true);
				valueObject.put("accessToken", Utility.getUniqueToken(request));
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject saveServiceEntryLabourDetails(ValueObject valueObject) throws Exception {
			CustomUserDetails 						userDetails								= null;
			ServiceEntriesTasksToLabor 				workTaskToLabor							= null;
			HashMap<String, Object>     			config						= null;
			try {
				userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				config	 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
				
				valueObject.put("allowGSTbifurcation", config.get("allowGSTbifurcation"));
				workTaskToLabor 		= WOBL.prepareServiceEntriesTaskToLabor(valueObject);
				
				workTaskToLabor.setCompanyId(userDetails.getCompany_id());
				addServiceEntriesTaskToLabor(workTaskToLabor);
				
				updateServiceEntriesTask_TotalLaborCost(workTaskToLabor.getServicetaskid());
				updateServiceEntriesMainTotalCost(valueObject.getLong("serviceEntryId"));
				
				java.sql.Date updateDate = new java.sql.Date( new java.util.Date().getTime());
				
				updateServiceEntriesProcess(ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS,
						valueObject.getLong("serviceEntryId"), userDetails.getCompany_id(), updateDate, userDetails.getId());
				
				valueObject.put("labourAdded", true);
				valueObject.put("accessToken", Utility.getUniqueToken(request));
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject saveServiceEntryNewTaskDetails(ValueObject valueObject) throws Exception {
			CustomUserDetails 						userDetails					= null;
			ServiceEntries 							WorkOrd						= null;
			ServiceEntriesTasks 					WorkTOTask					= null;
			try {
				userDetails = Utility.getObject(valueObject);
				
				WorkOrd = new ServiceEntries();
				WorkOrd.setServiceEntries_id(valueObject.getLong("serviceEntryId"));
				
				WorkTOTask = new ServiceEntriesTasks();
				WorkTOTask.setService_typetaskId(valueObject.getInt("jobtypeId"));

				JobSubType JobSubType = JobSubTypeService.getJobSubType(valueObject.getInt("jobsubtypeId"));
				if (JobSubType != null) {
					WorkTOTask.setService_subtypetask_id(valueObject.getInt("jobsubtypeId"));
				}

				WorkTOTask.setVid(valueObject.getInt("vehicle"));
				WorkTOTask.setTotalpart_cost(0.0);
				WorkTOTask.setTotallaber_cost(0.0);
				WorkTOTask.setTotaltask_cost(0.0);
				WorkTOTask.setServiceEntries(WorkOrd);
				WorkTOTask.setCompanyId(userDetails.getCompany_id());
				WorkTOTask.setTaskRemark(valueObject.getString("taskRemark"));
				
				addServiceEntriesTask(WorkTOTask);
				
				valueObject.put("taskAdded", true);
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject deletePartDetails(ValueObject valueObject) throws Exception {
			CustomUserDetails 						userDetails					= null;
			ServiceEntriesTasksToParts 				WORKTASKTOPARTS				= null;
			try {
				userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				WORKTASKTOPARTS = getServiceEntriesTaskToParts_ONLY_ID(valueObject.getLong("serviceEntriesTaskto_partid"), userDetails.getCompany_id());
				
				if(WORKTASKTOPARTS.getServiceEntries_id()!= null && WORKTASKTOPARTS.getServiceEntries_id() > 0) {
					ServiceEntriesDto	entriesDto =	getLimitedSEDetails(WORKTASKTOPARTS.getServiceEntries_id(), userDetails.getCompany_id());
					if(entriesDto.getAccidentId() != null && entriesDto.getAccidentId() > 0) {
						VehicleAccidentDetails	accidentDetails	= accidentDetailsService.getVehicleAccidentDetailsById(entriesDto.getAccidentId());
						if(accidentDetails.getStatus() <= AccidentStatus.ACCIDENT_STATUS_QUOTATION_APPROVED) {
							accidentDetailsService.updateAccidentDetailsStatus(valueObject.getLong("accidentId",0), AccidentStatus.ACCIDENT_STATUS_SERVEY_COMPLETED);
						}else {
							valueObject.put("accidentEntryApproved", true);
							return valueObject;
						
						}
					}
				}
				
				deleteServiceEntriesTaskTOParts(WORKTASKTOPARTS.getServiceEntriesTaskto_partid(), userDetails.getCompany_id());
				updateServiceEntriesTask_TotalPartCost(WORKTASKTOPARTS.getServicetaskid());
				updateServiceEntriesMainTotalCost(WORKTASKTOPARTS.getServiceEntries_id());
				
				valueObject.put("partDeleted", true);
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject deleteLabourDetails(ValueObject valueObject) throws Exception {
			CustomUserDetails 						userDetails					= null;
			ServiceEntriesTasksToLabor 				WORKTASKTOLabor				= null;
			try {
				userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				WORKTASKTOLabor = getServiceEntriesTaskToLabor_ONLY_ID(valueObject.getLong("serviceEntriesto_laberid"), userDetails.getCompany_id());
				if(WORKTASKTOLabor.getServiceEntries_id()!= null && WORKTASKTOLabor.getServiceEntries_id() > 0) {
					ServiceEntriesDto	entriesDto =	getLimitedSEDetails(WORKTASKTOLabor.getServiceEntries_id(), userDetails.getCompany_id());
					
					if(entriesDto.getAccidentId() != null && entriesDto.getAccidentId() > 0) {
						VehicleAccidentDetails	accidentDetails	= accidentDetailsService.getVehicleAccidentDetailsById(entriesDto.getAccidentId());
						if(accidentDetails.getStatus() <= AccidentStatus.ACCIDENT_STATUS_QUOTATION_APPROVED) {
							accidentDetailsService.updateAccidentDetailsStatus(valueObject.getLong("accidentId",0), AccidentStatus.ACCIDENT_STATUS_SERVEY_COMPLETED);
						}else {
							valueObject.put("accidentEntryApproved", true);
							return valueObject;
						
						}
					}
				}
				deleteServiceEntriesTaskTOLabor(WORKTASKTOLabor.getServiceEntriesto_laberid(), userDetails.getCompany_id());
				updateServiceEntriesTask_TotalLaborCost(WORKTASKTOLabor.getServicetaskid());
				updateServiceEntriesMainTotalCost(WORKTASKTOLabor.getServiceEntries_id());
				
				valueObject.put("LabourDetailsDeleted", true);
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject deleteTaskDetails(ValueObject valueObject) throws Exception {
			ServiceEntriesTasks						WORKTASK						= null;
			List<ServiceEntriesTasksToParts> 		ServiceEntriestasktoParts		= null;
			List<ServiceEntriesTasksToLabor> 		ServiceEntriestasktoLabor		= null;
			try {
				CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				WORKTASK 	= getServiceEntriesTask(valueObject.getLong("servicetaskid",0));
				
				if(WORKTASK != null && WORKTASK.getServiceEntries().getServiceEntries_id() != null && WORKTASK.getServiceEntries().getServiceEntries_id() > 0) {
					ServiceEntriesDto	entriesDto =	getLimitedSEDetails(WORKTASK.getServiceEntries().getServiceEntries_id(), userDetails.getCompany_id());
					if(entriesDto.getAccidentId() != null && entriesDto.getAccidentId() > 0) {
						VehicleAccidentDetails	accidentDetails	= accidentDetailsService.getVehicleAccidentDetailsById(entriesDto.getAccidentId());
						if(accidentDetails.getStatus() <= AccidentStatus.ACCIDENT_STATUS_QUOTATION_APPROVED) {
							accidentDetailsService.updateAccidentDetailsStatus(valueObject.getLong("accidentId",0), AccidentStatus.ACCIDENT_STATUS_SERVEY_COMPLETED);
						}else {
							valueObject.put("accidentEntryApproved", true);
							return valueObject;
						
						}
					}
				}
				
				ServiceEntriestasktoParts = getServiceEntriesTasksToParts_ID(valueObject.getLong("servicetaskid"), WORKTASK.getCompanyId());
				if (ServiceEntriestasktoParts != null && !ServiceEntriestasktoParts.isEmpty()) {
					valueObject.put("deletePartFirst", true);
					return valueObject;
				}
				
				ServiceEntriestasktoLabor = getServiceEntriesTasksToLabor_ID(valueObject.getLong("servicetaskid"),	WORKTASK.getCompanyId());
				if (ServiceEntriestasktoLabor != null && !ServiceEntriestasktoLabor.isEmpty()) {
					valueObject.put("deleteLobourFirst", true);
					return valueObject;
				}
				
				deleteServiceEntriesTask(WORKTASK.getServicetaskid(), WORKTASK.getCompanyId());
				updateServiceEntriesMainTotalCost(WORKTASK.getServiceEntries().getServiceEntries_id());
				valueObject.put("taskDetailsDeleted", true);
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject changeStatusToInProgress(ValueObject object) throws Exception {
			CustomUserDetails 						userDetails					= null;
			ServiceEntries							entries						= null;
			VehicleExpenseDetails					vehicleExpenseDetails		= null;
			VehicleDto								CheckVehicleStatus			= null;
			ValueObject								ownerShipObject				= null;
			Issues									Save_Issues					= null;
			IssuesTasks								issueTask					= null;
			IssuesDto								issuesDto					= null;
			HashMap<String, Object>     			config						= null;
			HashMap<String, Object> 	      companyConfiguration     			= null;			
			
			try {
				userDetails  = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				entries		 = 	getServiceEntries(object.getLong("serviceEntries_id"));
				companyConfiguration 	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
				config	 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
				
				if(entries.getAccidentId() != null && entries.getAccidentId() > 0) {
					VehicleAccidentDetails	accidentDetails	= accidentDetailsService.getVehicleAccidentDetailsById(entries.getAccidentId());
					if(accidentDetails.getStatus() < AccidentStatus.ACCIDENT_STATUS_FINAL_SERVEY_DONE) {
						accidentDetailsService.updateAccidentDetailsStatus(object.getLong("accidentId",0), AccidentStatus.ACCIDENT_STATUS_QUOTATION_APPROVED);
					}else {
						object.put("accidentEntryApproved", "Cannot Re-Open Service Entry As Vehicle Accident Status is "+AccidentStatus.getStatusName(accidentDetails.getStatus()));
						return object;
					
					}
				}
				
				if(entries.getService_paymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && entries.getService_vendor_paymodeId() != ServiceEntriesType.PAYMENT_MODE_NOT_PAID) {
					object.put("paymentDone", true);
					return object;
				}
					
					CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(entries.getVid(), userDetails.getCompany_id());
					
					if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED && entries.getTotalservice_cost() > 0){
						ownerShipObject	= new ValueObject();
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, entries.getServiceEntries_id());
						ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, entries.getVid());
						ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, entries.getTotalservice_cost());
						ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
						ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_SERVICE_ENTRY);
						ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, dateFormatSQL.parse(dateFormatSQL.format(entries.getCompleted_date())));
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Service Entry");
						ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Service Number : "+entries.getServiceEntries_Number());
						ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
						ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
						ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, entries.getTotalservice_cost());
						
						vehicleAgentIncomeExpenseDetailsService.deleteVehicleAgentIncomeExpense(ownerShipObject);
					}
					
					java.sql.Date updateDate = new java.sql.Date( new java.util.Date().getTime());
					
					if (object.getLong("serviceEntries_id") > 0 && entries.getServiceEntries_statusId() == ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE) {
						
						issuesDto = issuesService.GET_SE_ID_TO_ISSUES_DETAILS(object.getLong("serviceEntries_id"), userDetails.getCompany_id());
						if (issuesDto != null) {
							try {
								Save_Issues = new Issues();
								issueTask 	= new IssuesTasks();
								
								Save_Issues.setISSUES_ID(issuesDto.getISSUES_ID());
								
								issueTask.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED);
								issueTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_SE_CREATED);
								issueTask.setISSUES_CREATEBY_ID(userDetails.getId());
								issueTask.setISSUES_CREATED_DATE(updateDate);
								issueTask.setISSUES(Save_Issues);
								issueTask.setCOMPANY_ID(userDetails.getCompany_id());
								issueTask.setISSUES_REASON("ReOpen SE");
								
								issuesService.registerNew_IssuesTasks(issueTask);
								issuesService.update_Create_SE_Issues_Status( IssuesTypeConstant.ISSUES_STATUS_SE_CREATED,userDetails.getId(), updateDate, object.getLong("serviceEntries_id"), issuesDto.getISSUES_ID());
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
					}
					
					updateServiceEntriesProcess(ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS,
							object.getLong("serviceEntries_id"), userDetails.getCompany_id(), updateDate, userDetails.getId());
					
					if(entries.getAccidentId() != null && entries.getAccidentId() > 0) {
						accidentDetailsService.updateAccidentDetailsStatus(entries.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_QUOTATION_CREATED);
					}
					
					object.put("statusUpdated", true);
					
					if(entries.getService_paymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && entries.getTotalservice_cost() > 0) {
						vendorPaymentService.deletePendingVendorPayment(entries.getServiceEntries_id(), PendingPaymentType.PAYMENT_TYPE_SERVICE_ENTRIES);
					}
					
					vehicleExpenseDetails	= vehicleExpenseDetailsService.getVehicleExpenseDetails(entries.getServiceEntries_id(), 
							userDetails.getCompany_id() ,entries.getVid(), VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
					
					if(vehicleExpenseDetails != null) {
						ValueObject		valueObject	= new ValueObject();
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
						valueObject.put("txnAmount", entries.getTotalservice_cost());
						valueObject.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(entries.getCompleted_date()));
						valueObject.put("txnTypeId", entries.getServiceEntries_id());
						valueObject.put("vid", entries.getVid());
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
				
					if((boolean) config.get("sendServiceEntriesToCargo") && (boolean) companyConfiguration.get("allowBankPaymentDetails") 
							&& (entries.getService_paymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)) {
								removeSECashPaymentDetailsFromCargo(object);
					}
				
				return object;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject completeServiceEntry(ValueObject serviceEntryObject) throws Exception {
			HashMap<String, Object>     				configuration							= null;
			ServiceEntriesDto 							entries 								= null;
			List<EmailAlertQueue>  						pendingList								= null;
			List<SmsAlertQueue>  						pendingList1							= null;
			EmailAlertQueue 							email 									= null;
			SmsAlertQueue 								sms 									= null;
			ValueObject									ownerShipObject							= null;
			java.util.Date 								currentDate 							= null;
			DateFormat 									simpleDateFormat 						= null;
			java.util.Date 								toDate 									= null;
			java.sql.Date 								COMPLETE_date 							= null;
			CustomUserDetails 							userDetails								= null;
			VehicleProfitAndLossTxnChecker				profitAndLossTxnChecker					= null;
			VehicleDto 									CheckVehicleStatus						= null;
			VehicleAgentTxnChecker						agentTxnChecker							= null;
			ServiceEntriesDto 							serviceEntriesDto						= null;
			List<ServiceEntriesTasksDto> 				ServiceEntriesTasks						= null;
			List<ServiceReminder> 						serviceReminder							= null;
			ServiceReminder 							service									= null;
			Calendar 									calendar1								= null;
			java.util.Date 								alertDate1								= null; 
			java.util.Date 								alertBeforeDate							= null;
			java.util.Date 								alertAfterDate 							= null;
			IssuesDto 									issuesDto								= null;
			Issues 										Save_Issues								= null;
			IssuesTasks 								issueTask 								= null;
			java.util.Date 								currentDateUpdateIssues 				= null;
			Timestamp 									currentIsuueDate						= null;
			int											noOfBackDaysToReOpenSE					= 0;
			boolean										reOpenSE								= true;
			long										differenceBetTwoDate					= 0;
			HashMap<String, Object>     				config									= null;
			HashMap<String, Object>   	                configurationOfIssue		            = null;
			boolean                                     allowedMobNotification                  = false;
			HashMap<String, Object> 	                companyConfiguration     				= null;
			try {
				currentDate 			= new java.util.Date();
				simpleDateFormat 		= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
				toDate 					= dateFormatTime.parse(simpleDateFormat.format(currentDate));
				COMPLETE_date 			= new java.sql.Date(toDate.getTime());
				userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration 			= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
				config	 				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
				currentDateUpdateIssues = new java.util.Date();
				currentIsuueDate 		= new java.sql.Timestamp(currentDateUpdateIssues.getTime());
				configurationOfIssue    = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG);
				allowedMobNotification  = (boolean) configurationOfIssue.getOrDefault(IssuesConfigurationContant.ALLOWED_MOB_NOTIFICATION, false);
				entries 				= getServiceEntriesDetails(serviceEntryObject.getLong("serviceEntries_id"), userDetails.getCompany_id());
				noOfBackDaysToReOpenSE	= (Integer) config.getOrDefault(ServiceEntriesConfigurationConstants.NO_OF_BACK_DAYS_TO_REOPEN_SE, 0);
				companyConfiguration 	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);;
				
				final SimpleDateFormat format1 	= new SimpleDateFormat("yyyy-MM-dd");
				
				if (entries != null) {
					if(entries.getAccidentId() != null && entries.getAccidentId() > 0) {
						VehicleAccidentDetails	accidentDetails	= accidentDetailsService.getVehicleAccidentDetailsById(entries.getAccidentId());
						if(accidentDetails.getStatus() == AccidentStatus.ACCIDENT_STATUS_QUOTATION_APPROVED) {
							accidentDetailsService.updateAccidentDetailsStatus(entries.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_SERVICE_COMPLETED);
						}else {
							serviceEntryObject.put("quotationNotApproved", true);
							serviceEntryObject.put("completeSEToken", Utility.getUniqueToken(request));
							return serviceEntryObject;
						}
					}
					entries.setCompanyId(userDetails.getCompany_id());

					if(entries.getCompleted_dateOn() != null) {
						differenceBetTwoDate = DateTimeUtility.getExactDayDiffBetweenTwoDates(DateTimeUtility.getTimeStampFromDate(entries.getCompleted_dateOn()),DateTimeUtility.getCurrentTimeStamp());
						if(differenceBetTwoDate > noOfBackDaysToReOpenSE) {
							reOpenSE	= false;
						}
					}
					
					if(entries.isPendingForTally()) {
						reOpenSE	= false;
					}
					
					if(entries.getTotalservice_cost() > 0) {
						ValueObject		object	= new ValueObject();
						
						object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						object.put(VehicleProfitAndLossDto.TRANSACTION_ID, entries.getServiceEntries_id());
						object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
						object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
						object.put(VehicleProfitAndLossDto.COMPANY_ID, userDetails.getCompany_id());
						object.put(VehicleProfitAndLossDto.TRANSACTION_VID, entries.getVid());
						object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, entries.getServiceEntries_id());
						
						profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
						
						vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
						
						CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(entries.getVid(), userDetails.getCompany_id()); 
						
						if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED ){
							ownerShipObject	= new ValueObject();
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, entries.getServiceEntries_id());
							ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, entries.getVid());
							ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, entries.getTotalservice_cost());
							ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
							ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_SERVICE_ENTRY);
							ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, dateFormatSQL.parse(dateFormatSQL.format(currentDate)));
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Service Entry");
							ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Service Number : "+entries.getServiceEntries_Number());
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
							ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
							ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -entries.getTotalservice_cost());
							
							agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
							vehicleAgentTxnCheckerService.save(agentTxnChecker);
							
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
							ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
						}
						
					}

					serviceEntriesDto = WOBL.Show_ServiceEntries(entries);
					serviceEntriesDto.setCompanyId(userDetails.getCompany_id());
					
					updateServiceEntriesCOMPLETE_date( ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE, COMPLETE_date, serviceEntryObject.getLong("serviceEntries_id"), userDetails.getCompany_id(), userDetails.getId());
					
					
					if(entries.getService_paymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && entries.getTotalservice_cost() > 0) {
						PendingVendorPayment	vendorPayment	=	PendingVendorPaymentBL.createPendingVendorPaymentDTOForSE(entries);
						vendorPaymentService.savePendingVendorPayment(vendorPayment);
					}
					
					serviceEntryObject.put("companyId", userDetails.getCompany_id());
					serviceEntryObject.put("userId", userDetails.getId());
					
					if((boolean) config.get("addSECOmpletionRemark") && serviceEntryObject.getString("SEremark", null) != null) {
						ServiceEntriesRemark	SERemark	= serviceEntriesBL.ServiceEntryRemarkDto(entries, serviceEntryObject);	
						SERemark.setRemarkTypeId((short) 1); //1= completion 2 = Reopen 3 =Hold 4 =In-Process 5 = issue Remark
						SERemarkRepository.save(SERemark);
					}
					
					ServiceEntriesTasks 				= getServiceEntriesTasks(serviceEntryObject.getLong("serviceEntries_id"), userDetails.getCompany_id());
					
					// update Service Reminder into WorkOders
					if (ServiceEntriesTasks != null && !ServiceEntriesTasks.isEmpty()) {
						// many WorkOrdersTask and Update to that complete Time to
						// service Reminder
						for (ServiceEntriesTasksDto workOrdersTasksToServiceRC : ServiceEntriesTasks) {

							serviceReminder = ServiceReminderService.listSearchWorkorderToServiceReminder(serviceEntriesDto.getVid(),workOrdersTasksToServiceRC.getService_typetaskId(), workOrdersTasksToServiceRC.getService_subtypetask_id(),userDetails.getCompany_id());
							
							if (serviceReminder != null && !serviceReminder.isEmpty()) {
								for (ServiceReminder serviceReminderFindSameWorkorder : serviceReminder) {
									service 	= WOBL.ServiceEntriesTOServiceReminder(serviceReminderFindSameWorkorder, serviceEntriesDto);
									pendingList	= emailAlertQueueService.getAllEmailAlertQueueDetailsById(serviceReminderFindSameWorkorder.getService_id());
									service.setCompanyId(userDetails.getCompany_id());
									
									java.util.Date ServiceReminderDueDate = serviceReminderFindSameWorkorder.getTime_servicedate();
									
									ServiceReminderService.updateServiceReminder(service);
									
									if(workOrdersTasksToServiceRC.getService_id() >0 && workOrdersTasksToServiceRC.getService_id() == service.getService_id() ) 
									{
									ServiceReminderHistory srHistory = new ServiceReminderHistory();
									
									srHistory.setServiceEntries_id(service.getServiceEntries_id());
									srHistory.setService_reminderId(service.getService_id());
									srHistory.setCompanyId(userDetails.getCompany_id());
									srHistory.setCreated(new java.util.Date());	
									srHistory.setServiceType(WorkOrdersType.SERVICE_REMINDER_SE);
									srHistory.setServiceDueDate(ServiceReminderDueDate);
	 								
									serviceReminderHistoryRepository.save(srHistory);
									}
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
												
												calendar1 		= Calendar.getInstance();
												calendar1.setTime(service.getTime_servicedate());
												calendar1.add(Calendar.DAY_OF_YEAR, -emailAlertQueue.getAlertBeforeValues());
												
												alertDate1 		= format1.parse(format1.format(calendar1.getTime()));
												alertBeforeDate =  new Date(alertDate1.getTime());
												
												email.setAlertBeforeDate(alertBeforeDate+"");
												email.setAlertScheduleDate(alertBeforeDate);
											
												emailAlertQueueService.updateEmailAlertQueue(email);
											} else {
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
												email.setAlertAfterValues(emailAlertQueue.getAlertAfterValues());
												email.setServiceDate(service.getTime_servicedate());
													
												calendar1	= Calendar.getInstance();
												calendar1.setTime(service.getTime_servicedate());
												calendar1.add(Calendar.DAY_OF_YEAR, emailAlertQueue.getAlertAfterValues());
												
												alertDate1 		= format1.parse(format1.format(calendar1.getTime()));
												alertAfterDate 	=  new Date(alertDate1.getTime());
												
												email.setAlertAfterDate(alertAfterDate+"");
												email.setAlertScheduleDate(alertAfterDate);
											
												emailAlertQueueService.updateEmailAlertQueue(email);
											  
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
													
												calendar1 = Calendar.getInstance();
												calendar1.setTime(service.getTime_servicedate());
												calendar1.add(Calendar.DAY_OF_YEAR, -smsAlertQueue.getAlertBeforeValues());
												
												alertDate1 		= format1.parse(format1.format(calendar1.getTime()));
												alertBeforeDate =  new Date(alertDate1.getTime());
												
												sms.setAlertBeforeDate(alertBeforeDate+"");
												sms.setAlertScheduleDate(alertBeforeDate);
											
												smsAlertQueueService.updateSmsAlertQueue(sms);
										  } else {
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
												sms.setAlertAfterValues(smsAlertQueue.getAlertAfterValues());
												sms.setServiceDate(service.getTime_servicedate());
													
												calendar1 		= Calendar.getInstance();
												calendar1.setTime(service.getTime_servicedate());
												calendar1.add(Calendar.DAY_OF_YEAR, smsAlertQueue.getAlertAfterValues());
												
												alertDate1 		= format1.parse(format1.format(calendar1.getTime()));
												alertAfterDate	=  new Date(alertDate1.getTime());
												
												sms.setAlertAfterDate(alertAfterDate+"");
												sms.setAlertScheduleDate(alertAfterDate);
											
												smsAlertQueueService.updateSmsAlertQueue(sms);
											  
										       }
											}	
										}	
									}
								}
							}
						}
					
					if (serviceEntryObject.getString("serviceEntries_id") != null && serviceEntryObject.getLong("serviceEntries_id") != 0) {
						
						issuesDto = issuesService.GET_SE_ID_TO_ISSUES_DETAILS(serviceEntryObject.getLong("serviceEntries_id"), userDetails.getCompany_id());
						if (issuesDto != null) {
							try {
								Save_Issues = new Issues();
								issueTask 	= new IssuesTasks();
								
								Save_Issues.setISSUES_ID(issuesDto.getISSUES_ID());
								
								issueTask.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_SE_CREATED);
								issueTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_CHANGE_STATUS_RESOLVED);
								issueTask.setISSUES_CREATEBY_ID(userDetails.getId());
								issueTask.setISSUES_CREATED_DATE(currentIsuueDate);
								issueTask.setISSUES(Save_Issues);
								issueTask.setCOMPANY_ID(userDetails.getCompany_id());
								issueTask.setISSUES_REASON("ServiceEntries To Issues Resovled");
								
								issuesService.registerNew_IssuesTasks(issueTask);
								issuesService.update_Create_SE_Issues_Status( IssuesTypeConstant.ISSUES_STATUS_RESOLVED,userDetails.getId(), toDate, serviceEntryObject.getLong("serviceEntries_id"), issuesDto.getISSUES_ID());
								if(allowedMobNotification){
									issuesService.sendNotificationWhenIssueResolved(issuesDto.getISSUES_ID(), userDetails.getId(), userDetails.getCompany_id());
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

					}
					
					if(entries.getTotalservice_cost() > 0) {	
						ValueObject		valueObject	=  new ValueObject();
						
						valueObject.put("serviceEntries", entries);
						valueObject.put("userDetails", userDetails);
						valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
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
					
					if(ownerShipObject != null){
						vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
					}
					
					serviceEntryObject.put("reOpenSE", reOpenSE);
					serviceEntryObject.put("configuration", configuration);
					serviceEntryObject.put("completeStatus", true);
					
				  }
				if((boolean) config.get("sendServiceEntriesToCargo") && (boolean) companyConfiguration.get("allowBankPaymentDetails") 
						&&  (serviceEntriesDto.getService_paymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)) {
					
					sendSECashPaymentDetailsToCargo(serviceEntryObject, serviceEntriesDto);
				}
				
				return serviceEntryObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				 serviceEntryObject.put("completeSEToken", Utility.getUniqueToken(request));
				 return serviceEntryObject;
			}
		}
		
		@Override
		@Transactional
		public ValueObject saveRoundOfDetails(ValueObject valueObject) throws Exception {
			try {
				
				if (valueObject.getString("totalRoundCost") != null && valueObject.getDouble("totalRoundCost") != 0.0) {
					
					java.util.Date currentDate = new java.util.Date();
					DateFormat ft = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
					java.util.Date toDate = dateFormatTime.parse(ft.format(currentDate));
					java.sql.Date COMPLETE_date = new java.sql.Date(toDate.getTime());
					
					updateServiceEntries_ROUNT_OF_COST_COMPLETE_date(valueObject.getDouble("totalRoundCost"), "COMPLETE", 
							COMPLETE_date, valueObject.getLong("serviceEntries_id"));
					
					valueObject.put("roundOfCompleted", true);
				}
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}
		}
		
		@Transactional
		@Override
		public ValueObject uploadServiceEntryDocument(ValueObject valueObject, MultipartFile file) throws Exception {
			try {
				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				org.fleetopgroup.persistence.document.ServiceEntriesDocument serviceDocument = new org.fleetopgroup.persistence.document.ServiceEntriesDocument();
				serviceDocument.setServiceEntries_id(valueObject.getLong("serviceEntries_id"));
				
				if (!file.isEmpty()) {
					
					try {

						byte[] bytes = file.getBytes();
						serviceDocument.setService_filename(file.getOriginalFilename());
						serviceDocument.setService_content(bytes);
						serviceDocument.setService_contentType(file.getContentType());

						java.util.Date currentDateUpdate = new java.util.Date();
						Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

						serviceDocument.setCreated(toDate);
						serviceDocument.setLastupdated(toDate);
						serviceDocument.setCompanyId(userDetails.getCompany_id());
						serviceDocument.setCreatedById(userDetails.getId());
						serviceDocument.setLastModifiedById(userDetails.getId());
					} catch (IOException e) {

					}
				}
					documentService.deleteByServiceId(valueObject.getLong("serviceEntries_id"));
					
					documentService.saveServiceEntriesDoc(serviceDocument);
					
					Update_ServiceEntries_Docuemnt_AvailableValue(
							serviceDocument.getService_documentid(), true, valueObject.getLong("serviceEntries_id"),
							userDetails.getCompany_id());
				
				valueObject.put("UploadSuccess", true);
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject updateServiceEntryDetails(ValueObject valueObject) throws Exception {
			CustomUserDetails 				userDetails 				= null;
			ServiceEntries 					SE							= null;
			Integer 						currentODDMETER				= null;
			List<ServiceReminderDto>		serviceList					= null;
			HashMap<String, Object> 		SEconfig					= null;
			ServiceEntries					validateServiceEntries		= null;
			ServiceEntries                                  validateDuplicateEditAction     = null;
			try {
				userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				SEconfig 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
				SE 			= WOBL.updateServiceEntry(valueObject);
				
				ServiceEntriesDto	serviceEntriesDto	=		getLimitedSEDetails(SE.getServiceEntries_id(), userDetails.getCompany_id());
				
				if(!serviceEntriesDto.getVid().equals(SE.getVid()) || !serviceEntriesDto.getDriver_id().equals(SE.getDriver_id())) {
					valueObject.put("changeAccidentDetails", true);
					return valueObject;
				}
				
				
				if((boolean) SEconfig.getOrDefault("validateInvoiceNumber", false)) {
					if(!serviceEntriesDto.getInvoiceNumber().equalsIgnoreCase(valueObject.getString("invoiceNumber"))) {
						validateServiceEntries = getServiceEntriesByInvoiceNumber(valueObject.getString("invoiceNumber"), userDetails.getCompany_id());
						if(validateServiceEntries != null) {
							valueObject.put("duplicateInvoiceNumber", true);
							return valueObject;
						}
					}
				}
			
				validateDuplicateEditAction = checkDuplicateEntriesDetails(valueObject,userDetails.getCompany_id());
				if(validateDuplicateEditAction != null) {
					valueObject.put("validateDuplicateEditActionSE", true);
					return valueObject;
				}
				
				if(serviceEntriesDto.getServiceEntries_statusId() != ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE && (serviceEntriesDto.getService_vendor_paymodeId() == ServiceEntriesType.PAYMENT_MODE_NOT_PAID || serviceEntriesDto.getService_paymentTypeId() != PaymentTypeConstant.PAYMENT_TYPE_CREDIT)) {
					
					updateServiceEntries(SE);
					
					currentODDMETER = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(SE.getVid());
					if (currentODDMETER < SE.getVehicle_Odometer()) {
						
						vehicleService.updateCurrentOdoMeter(SE.getVid(), SE.getVehicle_Odometer(), userDetails.getCompany_id());
						// update current Odometer update Service Reminder
						ServiceReminderService.updateCurrentOdometerToServiceReminder(SE.getVid(),SE.getVehicle_Odometer(), userDetails.getCompany_id());
						
						serviceList = ServiceReminderService.OnlyVehicleServiceReminderList(SE.getVid(), userDetails.getCompany_id(), userDetails.getId());
						
						if(serviceList != null) {
							for(ServiceReminderDto list : serviceList) {
								
								if(list.getVehicle_currentOdometer() >= list.getMeter_serviceodometer()) {
									
									ServiceReminderService.setServiceOdometerUpdatedDate(list.getService_id(), list.getCompanyId());
								}
							}
						}
						
						VehicleOdometerHistory vehicleUpdateHistory = VOHBL.prepareOdometerGetHistoryToServiceEntries(SE);
						vehicleUpdateHistory.setCompanyId(userDetails.getCompany_id());
						vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleUpdateHistory);
						
						valueObject.put("saveServiceEntries", true);
					}
					
					valueObject.put("serviceEntryUpdated", true);
				}else {
					valueObject.put("completed", true);
				}
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+ e);
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject deleteServiceEntryDetails(ValueObject valueObject) throws Exception {
			CustomUserDetails 					userDetails 				= null;
			List<ServiceEntriesTasksDto> 		ServiceEntriestask			= null;
			Issues								issue						= null;
			IssuesTasks							issueTask					= null;
			try {
				userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				ServiceEntriestask  = getServiceEntriesTasks(valueObject.getLong("serviceEntries_id"), userDetails.getCompany_id());
				ServiceEntries serviceEntries = getDeletedSEById(valueObject.getLong("serviceEntries_id"), userDetails.getCompany_id());
				if(serviceEntries != null ) {
					if (ServiceEntriestask != null && !ServiceEntriestask.isEmpty()) {
						valueObject.put("serviceEntryCannotBeDeleted", true);
						return valueObject;
					}
					
					if(serviceEntries.getAccidentId() != null && serviceEntries.getAccidentId() > 0) {
						accidentDetailsService.updateAccidentDetailsStatus(serviceEntries.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_SERVEY_COMPLETED);
						accidentDetailsService.updateAccidentDetailsServiceDetails((long)0, (short)0, serviceEntries.getAccidentId());
					}
					
				java.util.Date currentDateUpdate  = new java.util.Date();
				Timestamp 	   toDate 			  = new java.sql.Timestamp(currentDateUpdate.getTime());
				
				deleteServiceEntries(valueObject.getLong("serviceEntries_id"), toDate, userDetails.getId(), userDetails.getCompany_id());
				
					VehicleDto CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(serviceEntries.getVid());
					if(CheckVehicleStatus.getVehicle_Odometer().equals(serviceEntries.getVehicle_Odometer())) { 
						VehicleOdometerHistory odometerHistoryLsit	= vehicleOdometerHistoryService.getVehicleOdometerHistoryByVidExceptCurrentEntry(serviceEntries.getServiceEntries_id(),serviceEntries.getVid(), userDetails.getCompany_id());
						if(odometerHistoryLsit != null ) {
							if(odometerHistoryLsit.getVehicle_Odometer() < serviceEntries.getVehicle_Odometer()) { //  found either pre entry or post entry
								vehicleService.updateCurrentOdoMeter(serviceEntries.getVid(), odometerHistoryLsit.getVehicle_Odometer(), userDetails.getCompany_id());
								vehicleOdometerHistoryService.deleteVehicleOdometerHistory(serviceEntries.getServiceEntries_id(), userDetails.getCompany_id());
							}
						
						}
						
					}
			
				if(serviceEntries.getISSUES_ID() != null && serviceEntries.getISSUES_ID() > 0  ) {
					issue 		= issuesService.getIssueDetailsByIssueId(serviceEntries.getISSUES_ID(), userDetails.getCompany_id());
				
					if(issue != null) {
						issueTask 	= issuesBL.prepareIssuesTaskDetails(issue,userDetails);
						issueTask.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_STATUS_SE_CREATED);
						issueTask.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_OPEN);
						issueTask.setISSUES_REASON("SE Deleted "); 
						issuesService.registerNew_IssuesTasks(issueTask);
						issuesService.update_Create_SE_Issues_Status(IssuesTypeConstant.ISSUES_STATUS_OPEN, userDetails.getId(), issueTask.getISSUES_CREATED_DATE(), serviceEntries.getServiceEntries_id(), serviceEntries.getISSUES_ID());
						issuesService.update_SE_Issue_Details(serviceEntries.getISSUES_ID());
					}
				}
				
				valueObject.put("serviceEntryDeleted", true);
			 }
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject getServiceEntriesList(ValueObject valueObject) throws Exception {
			CustomUserDetails 					userDetails 					= null;
			long 								totalserviceentriescount 		= 0;
			HashMap<String, Object> 			configuration					= null;
			try {
				userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
				Page<ServiceEntries> page = getDeployment_Page_ServiceEntries(valueObject.getShort("status"), valueObject.getInt("pagenumber"), userDetails);
				
				if (page != null) {
					int current = page.getNumber() + 1;
					int begin = Math.max(1, current - 5);
					int end = Math.min(begin + 10, page.getTotalPages());

					valueObject.put("deploymentLog", page);
					valueObject.put("beginIndex", begin);
					valueObject.put("endIndex", end);
					valueObject.put("currentIndex", current);
					valueObject.put("configuration", configuration);		
					totalserviceentriescount = page.getTotalElements();
				}
				valueObject.put("companyId", userDetails.getCompany_id());

				List<ServiceEntriesDto> pageList = WOBL.prepareListServiceEntries(listOpenServiceEntries(valueObject.getShort("status"), valueObject.getInt("pagenumber")));
				Object[] statusCount 		= getServiceEntriesCount(valueObject);
				
				valueObject.put("allCount", (Long) statusCount[0]);
				valueObject.put("inProcessCount", (Long) statusCount[1]);
				valueObject.put("completeCount", (Long) statusCount[2]);
				
				valueObject.put("ServiceEntries", pageList);
				valueObject.put("SelectStatus", valueObject.getShort("status"));
				valueObject.put("SelectPage", valueObject.getInt("pagenumber"));
				valueObject.put("totalserviceentriescount", totalserviceentriescount);
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}finally {
				
			}
		}
		
		@Override
		@Transactional
		public ValueObject searchServiceEntriesByNumber (ValueObject valueObject) throws Exception {
			CustomUserDetails 			userDetails 					= null;
			ServiceEntriesDto 			serviceEntries 					= null;
			try {
				userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				serviceEntries 		= getServiceEntriesByNumber(valueObject.getLong("serviceNumber"), userDetails.getCompany_id(), userDetails.getId());
				
				if(serviceEntries != null) {
					valueObject.put("serviceEntriesFound", true);
					valueObject.put("serviceEntries", serviceEntries);
					valueObject.put("serviceEntryId", serviceEntries.getServiceEntries_id());
				} else {
					valueObject.put("serviceEntriesNotFound", true);
				}
				
				return valueObject;
				
			} catch (Exception e) {
				System.err.println("ERR"+e);
				throw e;
			}
		}
		
		@Override
		public ValueObject searchServiceEntries(ValueObject valueObject) throws Exception {
			ValueObject							dateRange				= null;		
			CustomUserDetails					userDetails				= null;
			List<ServiceEntriesDto>				service					= null;		
			int 								vid            			= 0 ;
			int									driverId				= 0;
			int									vendorId				= 0;
			String								invoiceDate				= null;
			short								paymentType				= 0;
			String 								dateRangeFrom 			= "";
			String 								dateRangeTo 			= "";
			try {
				userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				vid				= valueObject.getInt("vehicle_vid", 0);
				driverId		= valueObject.getInt("SelectDriverName",0);
				vendorId		= valueObject.getInt("selectVendor",0);
				invoiceDate		= valueObject.getString("invoiceDate","");
				paymentType		= valueObject.getShort("renPT_option",(short)0);
				dateRange		= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"),DateTimeUtility.DD_MM_YYYY,DateTimeUtility.YYYY_MM_DD);
				dateRangeFrom 	= dateRange.getString(DateTimeUtility.FROM_DATE);
				dateRangeTo 	= dateRange.getString(DateTimeUtility.TO_DATE);
				
				if(dateRange != null) {
					
					String vehicleName = "" , driverName ="",  vendorName ="", invoiceDateName ="", paymentTypeName ="", Date = "";
					
					if (vid > 0) {
						vehicleName = " AND  SR.vid=" + vid + " ";
					}

					if (driverId > 0) {
						driverName = " AND SR.driver_id=" + driverId + " ";
					}

					if (vendorId > 0) {
						vendorName = " AND SR.vendor_id=" + vendorId + " ";
					}
					
					if (invoiceDate != "") {
						java.util.Date date = dateFormat.parse(invoiceDate);
						java.sql.Date InDate = new Date(date.getTime());

						invoiceDateName = " AND SR.invoiceDate='" + InDate + "' ";
					}

					if (paymentType > 0) {
						paymentTypeName = " AND SR.service_paymentTypeId=" + paymentType + " ";
					}
					
					Date = " AND SR.service_paiddate between '" + dateRangeFrom + "'  AND '" + dateRangeTo	+ "' ";
										
					String query  = " "+vehicleName+" "+driverName+" "+vendorName+" "+invoiceDateName+" "+paymentTypeName+" "+Date+" ";
								
					service	        = WOBL.prepareListServiceEntries(ReportServiceEntries(query, userDetails));
				}
				
				valueObject.put("ServiceEntries", service);
				
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}finally {
				service				= null;			
				userDetails			= null;
				dateRange			= null;
			}
		}
		
		@Override
		public ValueObject searchServiceEntriesDateWise(ValueObject valueObject) throws Exception {
			ValueObject							dateRange				= null;		
			CustomUserDetails					userDetails				= null;
			List<ServiceEntriesDto>				service					= null;		
			String 								dateRangeFrom 			= "";
			String 								dateRangeTo 			= "";
			String  							Date 					= "";
			try {
				userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				dateRange		= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"),DateTimeUtility.DD_MM_YYYY,DateTimeUtility.YYYY_MM_DD);
				dateRangeFrom 	= dateRange.getString(DateTimeUtility.FROM_DATE);
				dateRangeTo 	= dateRange.getString(DateTimeUtility.TO_DATE);
				
				if(dateRange != null) {
					Date 		= " AND SR.created between '" + dateRangeFrom + "'  AND '" + dateRangeTo + "' ";
					service	    = WOBL.prepareListServiceEntries(ReportServiceEntries(Date, userDetails));
				}
				
				valueObject.put("ServiceEntries", service);
				
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			}finally {
				service				= null;			
				userDetails			= null;
				dateRange			= null;
			}
		}
		
		@Override
		public ValueObject searchServiceEntriesByDifferentFilters(ValueObject valueObject) throws Exception {
			CustomUserDetails					userDetails				= null;
			List<ServiceEntriesDto>				service					= null;		
			try {
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				service	    = WOBL.prepareListServiceEntries(SearchServiceEntries(valueObject.getString("filter"), userDetails.getCompany_id(), userDetails.getId()));
				
				valueObject.put("ServiceEntries", service);
				
				return valueObject;
				
			} catch (Exception e) {
				throw e;
			}finally {
				service				= null;			
				userDetails			= null;
			}
		}
		
		@Override
		public List<DateWiseVehicleExpenseDto> getDateWiseServiceEntriesExpenseDtoByVid(Integer vid, String fromDate,
				String toDate, Integer companyId) throws Exception {
			
			try {
				TypedQuery<Object[]> queryt = entityManager
						.createQuery("SELECT  SUM(MVE.totalservice_cost), MVE.companyId  "
								+ " FROM ServiceEntries MVE "
								+ " where MVE.vid = "+vid+" AND MVE.invoiceDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
										+ " AND MVE.markForDelete = 0 AND MVE.totalservice_cost > 0 ", Object[].class);
				List<Object[]>	results = queryt.getResultList();
				
				List<DateWiseVehicleExpenseDto>	list	= null;
				
				if(results != null && !results.isEmpty()) {
					list	=	new ArrayList<>();
					for (Object[] result : results) {
						DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
						
						mExpenseDto.setExpenseAmount((Double) result[0]);
						mExpenseDto.setExpenseType((short)2);
						mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
						
						list.add(mExpenseDto);
					}
				}
				
				return list;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<MonthWiseVehicleExpenseDto> getMonthWiseServiceEntriesExpenseDtoByVid(Integer vid, String fromDate,
				String toDate, Integer companyId) throws Exception {
			
			try {
				TypedQuery<Object[]> queryt = entityManager
						.createQuery("SELECT  SUM(MVE.totalservice_cost), MVE.companyId  "
								+ " FROM ServiceEntries MVE "
								+ " where MVE.vid = "+vid+" AND MVE.invoiceDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
										+ " AND MVE.markForDelete = 0 AND MVE.totalservice_cost > 0 ", Object[].class);
				List<Object[]>	results = queryt.getResultList();
				
				List<MonthWiseVehicleExpenseDto>	list	= null;
				
				if(results != null && !results.isEmpty()) {
					list	=	new ArrayList<>();
					for (Object[] result : results) {
						MonthWiseVehicleExpenseDto	mExpenseDto = new MonthWiseVehicleExpenseDto();
						
						mExpenseDto.setExpenseAmount((Double) result[0]);
						mExpenseDto.setExpenseType((short)2);
						mExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
						
						list.add(mExpenseDto);
					}
				}
				
				return list;
			
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<ServiceEntriesAndWorkOrdersDto> getVehicleRepairAndPartConsumptionDetailsInSE(ValueObject valueObject) throws Exception {
			CustomUserDetails						userDetails						= null;
			List<ServiceEntriesAndWorkOrdersDto>	serviceEntriesList				= null;
			ServiceEntriesAndWorkOrdersDto			serviceEntriesDto				= null;
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
				serviceEntriesList			= new ArrayList<ServiceEntriesAndWorkOrdersDto>();
				vehicleId					= valueObject.getString("vehicleId","");
				vehicleGroup				= valueObject.getLong("vehicleGroup",0);
				startDate					= valueObject.getString("startDate","");
				endDate						= valueObject.getString("endDate","")+DateTimeUtility.DAY_END_TIME;
				
				startDate = DateTimeUtility.getStrDateFromStrDate(startDate, DateTimeUtility.DD_MM_YYYY);
				endDate	= DateTimeUtility.getStrDateFromStrDate(endDate, DateTimeUtility.DD_MM_YY_HH_MM_SS);
				
				
				if(startDate != "" && endDate != "") {
					dateRange 		= " SE.invoiceDate between '" +startDate+ "' AND '" +endDate + "' ";
				}
				
				if(vehicleGroup > 0) {
					vehicleGroupStr = " AND V.vehicleGroupId IN("+vehicleGroup+")";
				}
				if(vehicleId != "") {
					vehicleIdStr 	= " AND SE.vid IN ("+vehicleId+")";
				}
				
				queryStr		= " "+dateRange+"  "+vehicleGroupStr+" "+vehicleIdStr+" ";
				
				
				query = entityManager.createQuery(
						"SELECT SE.serviceEntries_id, SE.vid, V.vehicle_registration, SE.vehicle_Odometer, SE.serviceEntries_Number, "
						+ " SETK.servicetaskid, JT.Job_Type, JST.Job_ROT, SETP.serviceEntriesTaskto_partid, SETP.partid, "
						+ " MP.partname, MP.partnumber, SETP.quantity, SETP.parteachcost, SETP.totalcost, SETK.totallaber_cost, SE.created, SE.completed_date, SE.invoiceNumber, SE.invoiceDate, SE.serviceEntries_statusId  "
						+ " FROM ServiceEntries AS SE " 
						+ " INNER JOIN Vehicle V ON V.vid = SE.vid "
						+ " INNER JOIN ServiceEntriesTasks SETK ON SETK.ServiceEntries.serviceEntries_id = SE.serviceEntries_id AND SETK.markForDelete = 0 "
						+ " INNER JOIN JobType JT ON JT.Job_id = SETK.service_typetaskId "
						+ " INNER JOIN JobSubType JST ON JST.Job_Subid = SETK.service_subtypetask_id "
						+ " LEFT  JOIN ServiceEntriesTasksToParts SETP ON SETP.servicetaskid = SETK.servicetaskid AND SETP.markForDelete = 0 "
						+ " LEFT JOIN MasterParts MP ON MP.partid = SETP.partid" 
						+ " where "+queryStr+" AND SE.companyId = " + userDetails.getCompany_id()
						+ " AND SE.markForDelete = 0", Object[].class);
				

				results = query.getResultList();

				if (results != null && !results.isEmpty()) {
					laborHM			  = new HashMap<Long, Double>();
					for (Object[] result : results) {
						serviceEntriesDto = new ServiceEntriesAndWorkOrdersDto();
						serviceEntriesDto.setTransactionId((Long) result[0]);
						serviceEntriesDto.setVid((Integer) result[1]);
						serviceEntriesDto.setVehicle_registration("<a href='showVehicle.in?vid="+serviceEntriesDto.getVid()+"' target='_blank'>"+(String) result[2]+"</a>");
						serviceEntriesDto.setVehicleOdometer((Integer) result[3]);
						serviceEntriesDto.setTransactionNumber("<a href='ServiceEntriesParts.in?SEID="+serviceEntriesDto.getTransactionId()+"' target='_blank'>SE-"+(Long) result[4]+"</a>");
						serviceEntriesDto.setTaskId((Long) result[5]);
						serviceEntriesDto.setJobType((String) result[6]);
						serviceEntriesDto.setJobSubType((String) result[6]+"-"+(String) result[7]);
						serviceEntriesDto.setTaskToPartId((Long) result[8]);
						serviceEntriesDto.setPartId((Long) result[9]);
						serviceEntriesDto.setPartName((String) result[10]);
						serviceEntriesDto.setPartNumber((String) result[11]);
						serviceEntriesDto.setPartQuantity((Double) result[12]);
						serviceEntriesDto.setPartEachCost((Double) result[13]);
						if((Double) result[14] != null) {
							serviceEntriesDto.setPartTotalCost((Double) result[14]);
						}else {
							serviceEntriesDto.setPartTotalCost(0.0);
						}
						
						if(laborHM.containsKey(serviceEntriesDto.getTaskId())) {
							serviceEntriesDto.setLaborTotalCost(0.0);
						}else {
							laborHM.put(serviceEntriesDto.getTaskId(), (Double) result[15]);
							if((Double) result[15] != null) {
								serviceEntriesDto.setLaborTotalCost((Double) result[15]);
							}else {
								serviceEntriesDto.setLaborTotalCost(0.0);
							}
							
						}
						serviceEntriesDto.setStartDateStr(dateFormat.format((Timestamp) result[16]));
						if((Timestamp) result[17] != null) {
							serviceEntriesDto.setEndDateStr(dateFormat.format((Timestamp) result[17]));
						}
						if((String) result[18] != null) {
							serviceEntriesDto.setInvoiceNumber((String) result[18]);
						}
						if((Timestamp) result[19] != null) {
							serviceEntriesDto.setInvoiceDateStr(dateFormat.format((Timestamp) result[19]));
						}
						
						serviceEntriesDto.setTotalRepairCost(serviceEntriesDto.getPartTotalCost()+serviceEntriesDto.getLaborTotalCost());
						serviceEntriesDto.setStatus(ServiceEntriesType.getStatusName((short) result[20]));
						serviceEntriesList.add(serviceEntriesDto);
					}
				}
				
				return serviceEntriesList;
			} catch (Exception e) {
				LOGGER.error("Err"+e);
				throw e;
			}finally {
				userDetails						= null;     
				serviceEntriesDto				= null; 
				serviceEntriesList				= null;
			}
		}
		
		@Override
		public List<DateWiseVehicleExpenseDto> getDateWiseServiceEntriesExpenseDtoByRouteId(Integer vid, String fromDate,
				String toDate, Integer companyId, Integer routeId) throws Exception {
			try {
				TypedQuery<Object[]> queryt = entityManager
						.createQuery("SELECT  SUM(MVE.totalservice_cost), MVE.companyId  "
								+ " FROM ServiceEntries MVE "
								+ " INNER JOIN TripSheet TS ON TS.tripSheetID = MVE.tripSheetId"
								+ " where MVE.vid = "+vid+" AND MVE.invoiceDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
										+ " AND TS.routeID = "+routeId+" AND MVE.markForDelete = 0 AND MVE.totalservice_cost > 0 ", Object[].class);
				List<Object[]>	results = queryt.getResultList();
				
				List<DateWiseVehicleExpenseDto>	list	= null;
				
				if(results != null && !results.isEmpty()) {
					list	=	new ArrayList<>();
					for (Object[] result : results) {
						DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
						
						mExpenseDto.setExpenseAmount((Double) result[0]);
						mExpenseDto.setExpenseType((short)2);
						mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
						
						list.add(mExpenseDto);
					}
				}
				return list;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<DateWiseVehicleExpenseDto> getDateWiseServiceEntriesExpenseDtoByVTId(Integer vid, String fromDate,
				String toDate, Integer companyId, Long vehicleTypeId) throws Exception {
			try {
				TypedQuery<Object[]> queryt = entityManager
						.createQuery("SELECT  SUM(MVE.totalservice_cost), MVE.companyId  "
								+ " FROM ServiceEntries MVE "
								+ " INNER JOIN Vehicle V ON V.vid = MVE.vid AND V.vehicleTypeId = "+vehicleTypeId+""
								+ " where MVE.vid = "+vid+" AND MVE.invoiceDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
										+ " AND MVE.markForDelete = 0 AND MVE.totalservice_cost > 0 ", Object[].class);
				List<Object[]>	results = queryt.getResultList();
				
				List<DateWiseVehicleExpenseDto>	list	= null;
				
				if(results != null && !results.isEmpty()) {
					list	=	new ArrayList<>();
					for (Object[] result : results) {
						DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
						
						mExpenseDto.setExpenseAmount((Double) result[0]);
						mExpenseDto.setExpenseType((short)2);
						mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
						
						list.add(mExpenseDto);
					}
				}
				return list;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<DateWiseVehicleExpenseDto> getDateWiseServiceEntriesExpenseDtoByVTRouteId(Integer vid, String fromDate,
				String toDate, Integer companyId, Long vehicleTypeId, Integer routeId) throws Exception {
			try {
				TypedQuery<Object[]> queryt = entityManager
						.createQuery("SELECT  SUM(MVE.totalservice_cost), MVE.companyId  "
								+ " FROM ServiceEntries MVE "
								+ " INNER JOIN TripSheet TS ON TS.tripSheetID = MVE.tripSheetId AND TS.routeID = "+routeId+""
								+ " INNER JOIN Vehicle V ON V.vid = MVE.vid AND V.vehicleTypeId = "+vehicleTypeId+""
								+ " where MVE.vid = "+vid+" AND MVE.invoiceDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
										+ " AND MVE.markForDelete = 0 AND MVE.totalservice_cost > 0 ", Object[].class);
				List<Object[]>	results = queryt.getResultList();
				
				List<DateWiseVehicleExpenseDto>	list	= null;
				
				if(results != null && !results.isEmpty()) {
					list	=	new ArrayList<>();
					for (Object[] result : results) {
						DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
						
						mExpenseDto.setExpenseAmount((Double) result[0]);
						mExpenseDto.setExpenseType((short)2);
						mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
						
						list.add(mExpenseDto);
					}
				}
				return list;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidSEList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception {

			TypedQuery<Object[]> querySearch = entityManager.createQuery(
					"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, V.vehicle_registration, "
							+ " VN.vendorName, SR.invoiceNumber, SR.invoiceDate, SR.service_paymentTypeId,  "
						+ " SR.totalserviceROUND_cost, SR.balanceAmount " 	
							+ " from ServiceEntries SR "
							+ " INNER JOIN Vehicle V ON V.vid = SR.vid"
							+ " LEFT JOIN Vendor VN ON VN.vendorId = SR.vendor_id"
							+ " where  ( SR.vendor_id=" + vendor_id
							+ " and SR.service_paymentTypeId=" + PaymentTypeConstant.PAYMENT_TYPE_CREDIT
							+ " and SR.service_vendor_paymodeId =" + ServiceEntriesType.PAYMENT_MODE_NOT_PAID
							+ " OR SR.vendor_id=" + vendor_id + " and SR.service_paymentTypeId="
							+ PaymentTypeConstant.PAYMENT_TYPE_CREDIT + " and SR.service_vendor_paymodeId ="
							+ ServiceEntriesType.PAYMENT_MODE_APPROVED + " "
							+ " OR SR.vendor_id=" + vendor_id + " AND SR.service_paymentTypeId ="+PaymentTypeConstant.PAYMENT_TYPE_CREDIT+" AND SR.service_vendor_paymodeId =" + ServiceEntriesType.PAYMENT_MODE_PARTIALLY_PAID+ "  AND SR.balanceAmount > 0) "
							+ " AND SR.invoiceDate between '"+dateFrom+"' AND '"+dateTo+"' "
							+ " AND SR.companyId = "+companyId+" AND SR.markForDelete = 0 ",
					Object[].class);

			List<Object[]> results = querySearch.getResultList();

			List<VendorPaymentNotPaidDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VendorPaymentNotPaidDto>();
				VendorPaymentNotPaidDto list = null;
				for (Object[] result : results) {
					list = new VendorPaymentNotPaidDto();
					
					list.setSerialNumberId((Long) result[0]);
					list.setSerialNumber((Long) result[1]);
					list.setSerialNumberStr("SE-"+list.getSerialNumber());
					list.setVid((Integer) result[2]);
					list.setVehicleRegistration((String) result[3]);
					list.setVendorName((String) result[4]);
					
					if(result[5] != null) {
						list.setInvoiceNumber((String) result[5]);
					} else {
						list.setInvoiceNumber("-");
					}
					
					list.setInvoiceDateStr(dateFormat.format((java.util.Date) result[6]));
					list.setPaymentType(PaymentTypeConstant.getPaymentTypeName((short) result[7]));
					
					if(result[8] != null) {
						list.setInvoiceAmount((Double) result[8]);
					} else {
						list.setInvoiceAmount(0.0);
					}
					
					if(result[9] != null) {
						list.setBalanceAmount((Double) result[9]);
					} else {
						list.setBalanceAmount(0.0);
					}

					Dtos.add(list);
				}
			}
			return Dtos;
		}
		
		@Override
		public ServiceEntries getServiceEntriesCreatedBetweenDates(String startDate, String endDate, Integer companyId) throws Exception {
						
			TypedQuery<ServiceEntries> query = entityManager.createQuery(
						"SELECT SE "
								+ "From ServiceEntries as SE "
								+ " WHERE SE.created between '"+startDate+"' AND '"+endDate+"' AND SE.companyId = "+companyId+"  AND SE.markForDelete = 0 "
								+ " Group by SE.companyId ",ServiceEntries.class);
				
				
			ServiceEntries	serviceEntries = null;
			try {
					query.setMaxResults(1);
					serviceEntries = query.getSingleResult();
				
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			return serviceEntries;
		}
		
		@Override
		public ServiceEntriesDto getLimitedSEDetails(Long serviceId, Integer companyId) throws Exception {
			try {

				TypedQuery<Object[]>	query = entityManager.createQuery(
					"SELECT SR.serviceEntries_id, SR.serviceEntries_Number, SR.vid, SR.serviceEntries_statusId, "
					+ " SR.service_paymentTypeId, SR.service_vendor_paymodeId, SR.accidentId, SR.driver_id, SR.invoiceNumber "
						+ "	FROM ServiceEntries SR "
						+ " WHERE SR.serviceEntries_id = "+serviceId+" AND SR.companyId ="+companyId+" AND  SR.markForDelete = 0", Object[].class);
				
				Object[] result = null;
				try {
					result = (Object[]) query.getSingleResult();
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}

				ServiceEntriesDto	entriesDto = null;
				if (result != null) {
					entriesDto = new ServiceEntriesDto();
					entriesDto.setServiceEntries_id((Long) result[0]);
					entriesDto.setServiceEntries_Number((Long) result[1]);
					entriesDto.setVid((Integer) result[2]);
					entriesDto.setServiceEntries_statusId((short) result[3]);
					entriesDto.setService_paymentTypeId( (short) result[4]);
					entriesDto.setService_vendor_paymodeId((short) result[5]);
					entriesDto.setAccidentId((Long) result[6]);
					entriesDto.setDriver_id((Integer) result[7]);
					entriesDto.setInvoiceNumber((String) result[8]);
				}
				
				return entriesDto;
				
			
			} catch (Exception e) {
				throw e;
			}
		}
		@Override
		public ServiceEntries getServiceEntriesByInvoiceNumber(String invoiceNumber, Integer companyId)throws Exception{
			try {
				
				Query query = entityManager.createQuery(
						"SELECT SE.serviceEntries_id, SE.invoiceNumber FROM ServiceEntries  as SE WHERE "
						+ " SE.invoiceNumber = :invoiceNumber  AND SE.companyId ="+companyId+" AND  SE.markForDelete = 0");
			
				
				Object[] result = null;
				try {
					query.setParameter("invoiceNumber", invoiceNumber);
					query.setMaxResults(1);
					result = (Object[]) query.getSingleResult();
				} catch (NoResultException nre) {
					// Ignore this because as per your logic this is ok!
				}

				ServiceEntries	entries = null;
				if (result != null) {
					entries = new ServiceEntries();
					entries.setServiceEntries_id((Long) result[0]);
				}
				return entries;
			} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
			
	}
		
		@Override
		@Transactional
		public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
				throws Exception {
			
			entityManager.createQuery("  UPDATE ServiceEntries SET service_vendor_paymentdate=" + paymentDate+" , "
					+ " service_vendor_paymodeId="+paymentStatus+", service_vendor_approvalID = "+approvalId+" "
					+ " WHERE serviceEntries_id = "+invoiceId+" ").executeUpdate();
			
		}
		
		@Override
		public List<ServiceEntriesDto> getUserWiseSEActivityList(String query ,String innerQuery) throws Exception{
			
			List<ServiceEntriesDto>        serviceEntriesDto			= null;
			try {
				serviceEntriesDto = new ArrayList<ServiceEntriesDto>();
				TypedQuery <Object[]> queryCreate = entityManager.createQuery("SELECT s.serviceEntries_id,s.serviceEntries_Number,s.vid,v.vehicle_registration,VN.vendorName, s.invoiceNumber, s.invoiceDate, "
						+ "  s.totalserviceROUND_cost, s.balanceAmount, s.created ,s.lastupdated ,s.markForDelete "
						+ ",s.createdById ,s.lastModifiedById ,U.firstName, U.lastName ,U.id FROM ServiceEntries as s "
						+ " INNER JOIN Vehicle v ON v.vid = s.vid "
						+ "  LEFT JOIN Vendor VN ON VN.vendorId = s.vendor_id "
						+ " "+innerQuery+" "
						+ " "+query+" ",Object[].class);
				
				List<Object[]>	results = queryCreate.getResultList();
				
				
				if(results != null && !results.isEmpty()) {
				
					
					for(Object[] result : results ) {
						ServiceEntriesDto	entriesDto  = new ServiceEntriesDto();
						entriesDto.setServiceEntries_id((Long) result[0]);
						entriesDto.setServiceEntries_Number((Long) result[1]);
						entriesDto.setVid((Integer) result[2]);
						entriesDto.setVehicle_registration((String) result[3]);
						
						entriesDto.setVendor_name((String) result[4]);
						
						if(result[5] != null) {
							entriesDto.setInvoiceNumber((String) result[5]);
						} else {
							entriesDto.setInvoiceNumber("-");
						}
						if(result[6] != null)
						entriesDto.setInvoiceDateStr(dateFormat.format((java.util.Date) result[6]));
						
						if(result[7] != null) {
							entriesDto.setTotalserviceROUND_cost(Double.parseDouble(toFixedTwo.format(result[7]))); 
						} else {
							entriesDto.setTotalserviceROUND_cost(0.0);
						}
						
						if(result[8] != null) {
							entriesDto.setBalanceAmount(Double.parseDouble(toFixedTwo.format(result[8])));
						} else {
							entriesDto.setBalanceAmount(0.0);
						}
						if(result[9] != null)
						entriesDto.setCreated(dateFormat.format(result[9]));
						if(result[10] != null)
						entriesDto.setLastupdated(dateFormat.format(result[10]));
						entriesDto.setMarkForDelete((boolean) result[11]);
						if(!entriesDto.isMarkForDelete()) {
							entriesDto.setServiceEntriesNumberstr("<a target=\"_blank\" style=\"color: blue; background: #ffc;\"  href=\"showServiceEntryDetails?serviceEntryId="+entriesDto.getServiceEntries_id()+" \"> SE-"+entriesDto.getServiceEntries_Number()+" </a>");
						}else {
							entriesDto.setServiceEntriesNumberstr("<a  style=\"color: red; background: #ffc;\"  href=\"# \" data-toggle=\"tip\" data-original-title=\"Deleted Data\"> SE-"+entriesDto.getServiceEntries_Number()+" </a>");
						}
						entriesDto.setCreatedById((Long)result[12]);
						entriesDto.setLastModifiedById((Long)result[13]);
						entriesDto.setUserName(result[14]+" "+result[15]);
						entriesDto.setUserID((Long)result[16]);
						serviceEntriesDto.add(entriesDto);
					}
				}
					return serviceEntriesDto;
				
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
						.createQuery("SELECT COUNT(co.workorders_id),(SELECT  COUNT(P.workorders_id) FROM ServiceEntries AS P where  P.workorders_statusId ="+WorkOrdersType.WORKORDERS_STATUS_OPEN+" AND P.companyId = "+companyId+" AND P.markForDelete = 0 ) ,"
								+ "(SELECT  COUNT(OP.workorders_id) FROM ServiceEntries AS OP where OP.workorders_statusId ="+WorkOrdersType.WORKORDERS_STATUS_ONHOLD+" AND OP.companyId = "+companyId+" AND OP.markForDelete = 0 ) "
								+ " FROM ServiceEntries As co  "
								+ " where co.workorders_statusId  = "+WorkOrdersType.WORKORDERS_STATUS_COMPLETE+" AND co.companyId = "+companyId+" AND co.markForDelete = 0 ");
			}else {
				queryt = entityManager
						.createQuery("SELECT COUNT(co.workorders_id),(SELECT  COUNT(P.workorders_id) FROM ServiceEntries AS P "
								+ " INNER JOIN Vehicle V ON V.vid = P.vehicle_vid"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+userId+" "
								+ " where  P.workorders_statusId ="+WorkOrdersType.WORKORDERS_STATUS_OPEN+" AND P.companyId = "+companyId+" AND P.markForDelete = 0 ) ,"
								+ "(SELECT  COUNT(OD.workorders_id) FROM ServiceEntries AS OD "
								+ " INNER JOIN Vehicle V2 ON V2.vid = OD.vehicle_vid"
								+ " INNER JOIN VehicleGroupPermision VGP2 ON VGP2.vg_per_id = V2.vehicleGroupId AND VGP2.user_id = "+userId+" "
								+ " where OD.workorders_statusId ="+WorkOrdersType.WORKORDERS_STATUS_INPROCESS+" AND OD.companyId = "+companyId+" AND OD.markForDelete = 0 ), "
								+ "(SELECT  COUNT(OP.workorders_id) FROM ServiceEntries AS OP "
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
		
		@Transactional
		public Object[] getServiceEntriesCount(ValueObject valueObject) throws Exception {
			try {
				Query queryt =	null;
				queryt = entityManager
						.createQuery("SELECT COUNT(SE1),"
								+ "(SELECT  COUNT(SE2) FROM ServiceEntries AS SE2 where SE2.serviceEntries_statusId = "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_INPROCESS+" AND SE2.companyId = "+valueObject.getInt("companyId")+" AND SE2.markForDelete = 0 ) ,"
								+ "(SELECT  COUNT(SE3) FROM ServiceEntries AS SE3 where SE3.serviceEntries_statusId = "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE+" AND SE3.companyId = "+valueObject.getInt("companyId")+" AND SE3.markForDelete = 0 ) "
								+ " FROM ServiceEntries As SE1 where SE1.companyId = "+valueObject.getInt("companyId")+" AND  SE1.serviceEntries_statusId = "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_OPEN+" AND SE1.markForDelete = 0 ");
		 
				Object[] count = (Object[]) queryt.getSingleResult();

				return count;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@Override
		public List<TripSheetExpenseDto> getServiceEntriesListForTallyImportATC(String fromDate, String toDate, Integer companyId, String tallyCompany) throws Exception {
			try {
				Query query = entityManager.createQuery(
						"SELECT SE.serviceEntries_id, SE.serviceEntries_Number, SE.invoiceDate, V.vendorName,"
						+ " VH.vehicle_registration, VH.ledgerName, TC.companyName, SE.created, SE.service_paymentTypeId, SE.vid, "
						+ " SE.totalservice_cost, TE.expenseName, SE.isPendingForTally, SE.invoiceDate, SE.invoiceNumber "
						+ " FROM ServiceEntries SE "
						+ " INNER JOIN Vehicle VH ON VH.vid = SE.vid "
						+ " LEFT JOIN Vendor AS V ON V.vendorId = SE.vendor_id"
						+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = SE.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
						+ " INNER JOIN TripExpense TE ON TE.expenseID = SE.tallyExpenseId "
						+ " WHERE SE.invoiceDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
						+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND SE.totalservice_cost > 0"
						+ " AND SE.serviceEntries_statusId = "+ServiceEntriesType.SERVICE_ENTRIES_STATUS_COMPLETE+"");
				
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
							select.setVendorName((String) vehicle[3]);
							select.setVehicle_registration((String) vehicle[4]);
							select.setLedgerName((String) vehicle[5]);
							select.setTallyCompanyName((String) vehicle[6]);
							select.setCreatedOn((Timestamp) vehicle[7]);
							select.setExpenseFixedId((short) vehicle[8]);
							select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
							select.setVid((Integer) vehicle[9]);
							select.setExpenseAmount((Double) vehicle[10]);
							select.setExpenseName((String) vehicle[11]);
							select.setPendingForTally((boolean) vehicle[12]);
							select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_SERVICE_ENTRIES);
							select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
							
							
							select.setTripSheetId(select.getTripExpenseID());
							if(select.getExpenseFixedId() == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
								select.setCredit(true);
							}else {
								select.setCredit(false);
							}
							
							if(select.getVendorName() == null ) {
								select.setVendorName("--");
							}
							
							if(vehicle[2] != null ) {
								select.setCreatedStr(dateFormat.format((java.util.Date)vehicle[2]));
							}
							
							 select.setRemark("Service Entries On Vehicle "+select.getVehicle_registration()+" Invoice Date: "+dateFormat.format((java.util.Date)vehicle[13])+ " Invoice Number : "+(String)vehicle[14]+" "+  " From: "+select.getVendorName());
							
							if(select.getTallyCompanyName() == null) {
								select.setTallyCompanyName("--");
							}
							if(select.getLedgerName() == null) {
								select.setLedgerName("--");
							}
							
							select.setTripSheetNumberStr("SE-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
							select.setTallyCompanyName(tallyCompany);
							
							Dtos.add(select);
						}
					}
					
				}
				return Dtos;
				
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<ServiceEntriesRemarkDto> getServiceEntryRemarkDtoList(Integer SEId) throws Exception {
			List<ServiceEntriesRemarkDto>        SERemarkList			= null;
			try {
				SERemarkList = new ArrayList<ServiceEntriesRemarkDto>();
				TypedQuery <Object[]> queryCreate = entityManager.createQuery("SELECT  SE.serviceEntryRemarkId, SE.remark, SE.remarkTypeId, "
						+ "  SE.driverId, SE.createdOn, D.driver_firstname, D.driver_Lastname, D.driver_fathername "
						+ " FROM  ServiceEntriesRemark as SE "
						+ " LEFT JOIN Driver D ON D.driver_id = SE.driverId "
						+ " WHERE SE.serviceEntryId = " + SEId + " and SE.markForDelete = 0 "
						+ " ",Object[].class);
				List<Object[]>	results = queryCreate.getResultList();
				if(results != null && !results.isEmpty()) {
					for(Object[] result : results ) {
						ServiceEntriesRemarkDto  SEDto = new ServiceEntriesRemarkDto();
						SEDto.setServiceEntryId((Long) result[0]);
						SEDto.setRemark((String) result[1]);
						SEDto.setRemarkTypeId((short)result[2]);
						SEDto.setDriverId((Integer) result[3]);
						SEDto.setCreatedOn(DateTimeUtility.getDateFromTimeStampWithAMPM((Timestamp) result[4]));
						SEDto.setDriverName((String) result[5] + "_"+ (String) result[6]+ "_"+ (String) result[7]);
						SERemarkList.add(SEDto);
					}
				}
				return SERemarkList;
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	
	public void sendSECashPaymentDetailsToCargo(ValueObject object, ServiceEntriesDto serviceEntriesDto) {
		
		int							companyId							= 0;
		long						userId								= 0;
		long						serviceEntries_id					= 0;
		CustomUserDetails			userDetails							= null;
		HashMap<String, Object> 	configuration						= null;
		HashMap<String, Object> 	companyConfiguration				= null;
		
		try {
        	companyId    			= object.getInt("companyId");
			userId					= object.getLong("userId");
			serviceEntries_id 		= object.getLong("serviceEntries_id");
						
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object.put("userDetails", userDetails);
			configuration	 		 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			companyConfiguration 	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);;
			
			ValueObject expensValueObject = new ValueObject();
			
				expensValueObject.put("bankPaymentTypeId",PaymentTypeConstant.PAYMENT_TYPE_CASH);
				expensValueObject.put("userId", object.getLong("userId"));
				expensValueObject.put("companyId", object.getInt("companyId"));
				expensValueObject.put("moduleId", serviceEntriesDto.getServiceEntries_id());
				expensValueObject.put("moduleNo", serviceEntriesDto.getServiceEntries_Number());
				expensValueObject.put("moduleIdentifier", ModuleConstant.SERVICE_ENTRIES);
				expensValueObject.put("amount", serviceEntriesDto.getTotalservice_cost());
				expensValueObject.put("remark", " SE - "+serviceEntriesDto.getServiceEntries_Number()+", Driver Name "+serviceEntriesDto.getDriver_name()+", Vehicle Number : "+serviceEntriesDto.getVehicle_registration()+" ");

			    cashPaymentService.saveCashPaymentSatement(expensValueObject);
				
				
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void removeSECashPaymentDetailsFromCargo(ValueObject object) {
			
			int							companyId							= 0;
			long						userId								= 0;
			long						serviceEntries_id					= 0;
			CustomUserDetails			userDetails							= null;
			HashMap<String, Object> 	configuration						= null;
			HashMap<String, Object> 	companyConfiguration				= null;
			ServiceEntriesDto		    serviceEntriesDto				    = null;
			
			try {
	        	companyId    			= object.getInt("companyId");
				userId					= object.getLong("userId");
				serviceEntries_id 		= object.getLong("serviceEntries_id");
							
				userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				object.put("userDetails", userDetails);
				configuration	 		 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
				companyConfiguration 	 = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);;
				
				serviceEntriesDto 				= getServiceEntriesDetails(object.getLong("serviceEntries_id"), userDetails.getCompany_id());
				
				bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(serviceEntries_id,ModuleConstant.SERVICE_ENTRIES, userDetails.getCompany_id() , object.getLong("userId",0),false);
			
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
}
