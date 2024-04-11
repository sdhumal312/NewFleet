package org.fleetopgroup.persistence.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.RenealReminderStatus;
import org.fleetopgroup.constant.RenewalReminderConfiguration;
import org.fleetopgroup.constant.RenewalReminderEmailConfiguration;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.constant.TripRouteFixedType;
import org.fleetopgroup.constant.TripSheetStatus;
import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.dao.RenewalMailConfigurationRepository;
import org.fleetopgroup.persistence.dao.RenewalReminderApprovalRepository;
import org.fleetopgroup.persistence.dao.RenewalReminderDocumentRepository;
import org.fleetopgroup.persistence.dao.RenewalReminderHistoryRepository;
import org.fleetopgroup.persistence.dao.RenewalReminderRepository;
import org.fleetopgroup.persistence.dao.UserRepository;
import org.fleetopgroup.persistence.dao.VehicleRepository;
import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderApprovalDto;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.RenewalReminderHistoryDto;
import org.fleetopgroup.persistence.dto.RenewalSubTypeDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.RenewalMailConfiguration;
import org.fleetopgroup.persistence.model.RenewalReminder;
import org.fleetopgroup.persistence.model.RenewalReminderApproval;
import org.fleetopgroup.persistence.model.RenewalReminderDocument;
import org.fleetopgroup.persistence.model.RenewalReminderHistory;
import org.fleetopgroup.persistence.model.RenewalReminderSequenceCounter;
import org.fleetopgroup.persistence.model.RenewalType;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalSubTypeService;
import org.fleetopgroup.persistence.serviceImpl.IRenewalTypeService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.ITallyIntegrationService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.ByteConvertor;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import au.com.bytecode.opencsv.CSVReader;
import java.math.BigInteger;

@Service("RenewalReminderService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class RenewalReminderService implements IRenewalReminderService {
	Logger logger = Logger.getLogger(RenewalReminderService.class);
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired private RenewalReminderRepository 			renewalReminderDao;
	@Autowired private RenewalMailConfigurationRepository 	renewalReminderMail;
	@Autowired private RenewalReminderDocumentRepository 	renewalReminderDocumentDao;
	@Autowired private RenewalReminderHistoryRepository 	renewalReminderHistoryDao;
	@Autowired private RenewalReminderApprovalRepository 	renewalReminderApprovalDao;
	@Autowired private ICompanyConfigurationService 		companyConfigurationService;
	@Autowired private ITallyIntegrationService 			tallyIntegrationService;
	@Autowired private MongoTemplate 						mongoTemplate;
	@Autowired private ISequenceCounterService 				sequenceCounterService;
	@Autowired private IUserProfileService 					 userProfileService;
	@Autowired private ITripSheetService 					 tripSheetService;
	@Autowired private VehicleProfitAndLossTxnCheckerService vehicleProfitAndLossTxnCheckerService;
	@Autowired private IVehicleProfitAndLossService 		 vehicleProfitAndLossService;
	@Autowired private ITripExpenseService 					tripExpenseService;
	@Autowired private IVehicleService 						vehicleService;
	@Autowired private IRenewalReminderSequenceService 		renewalReminderSequenceService;
	@Autowired private IRenewalReminderDocumentService		 documentService;
	@Autowired private IVehicleAgentTxnCheckerService 		vehicleAgentTxnCheckerService;
	@Autowired private IVehicleAgentIncomeExpenseDetailsService vehicleAgentIncomeExpenseDetailsService;
	@Autowired private IRenewalTypeService					RenewalTypeService;
	@Autowired private IRenewalSubTypeService				RenewalSubTypeService;
	@Autowired private VehicleRepository					VehicleRepository;
	@Autowired private UserRepository						userService;
	@Autowired private HttpServletRequest					request;
	@Autowired private IBankPaymentService					bankPaymentService;
	@Autowired private ICashPaymentService					cashPaymentService;

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sqlFormatTime = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	SimpleDateFormat tallyFormat		= new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat SQL_DATE_FORMAT2 = new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
	DecimalFormat toFixedTwo 		= new DecimalFormat("#.##");
	VehicleProfitAndLossTxnCheckerBL txnCheckerBL = new VehicleProfitAndLossTxnCheckerBL();
	RenewalReminderBL RRBL = new RenewalReminderBL();
	VehicleAgentTxnCheckerBL agentTxnCheckerBL = new VehicleAgentTxnCheckerBL();
	VehicleBL VBL = new VehicleBL();

	private static final int PAGE_SIZE = 10;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addRenewalReminder(RenewalReminder renewalReminder) {
		try {
			renewalReminderDao.save(renewalReminder);
		} catch (Exception e) {
			logger.error("Exception : ", e);
		}

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateRenewalReminder(RenewalReminder renewalReminder) {

		renewalReminderDao.save(renewalReminder);
	}

	@Transactional
	public void updateApproedBy(Long renewal_id, short renewal_status, Long renewal_approvedby,
			String renewal_approvedComment, Date renewal_approveddate, Integer companyId) {
		renewalReminderDao.updateApproedBy(renewal_id, renewal_status, renewal_approvedby, renewal_approvedComment,
				renewal_approveddate, companyId);
	}

	
	@SuppressWarnings("deprecation")
	/** This Page get Vehicle table to get pagination values */
	@Transactional
	public Page<RenewalReminder> getDeployment_Page_RenewalReminder(short status, Integer pageNumber,
			CustomUserDetails userDetails) throws Exception {

		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(status > 0) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
	
				return renewalReminderDao.getDeployment_Page_RenewalReminder(status, userDetails.getCompany_id(), request);
			} else {
	
				return renewalReminderDao.getDeployment_Page_RenewalReminder(status, userDetails.getCompany_id(),
						userDetails.getId(), request);
			}
		}else {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
	
				return renewalReminderDao.getDeploymentPageRenewalReminderForAll(userDetails.getCompany_id(), request);
			} else {
	
				return renewalReminderDao.getDeploymentPageRenewalReminderForAll(userDetails.getCompany_id(),
						userDetails.getId(), request);
			}
		}
	}

	@Transactional
	public List<RenewalReminderDto> Find_listRenewalReminder(short status, Integer pageNumber,
			CustomUserDetails userDetails) throws Exception {
		String renewalStatus = "";
		try {
			if(status > 0) {
				renewalStatus = " AND R.renewal_staus_id =" + status + " "	;
			}
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, VG.vGroup, R.newRRCreated, VS.vStatus, R.renewal_receipt, R.ignored,RT.allowToAvoid "
								+ " FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid "
								+ " INNER JOIN VehicleStatus VS ON VS.sid = V.vStatusId "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " WHERE R.companyId = " + userDetails.getCompany_id() + " "+renewalStatus+" AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			} else {
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, VG.vGroup, R.newRRCreated, VS.vStatus, R.renewal_receipt, R.ignored,RT.allowToAvoid "
								+ " FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid "
								+ " INNER JOIN VehicleStatus VS ON VS.sid = V.vStatusId "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " WHERE  R.companyId = " + userDetails.getCompany_id() + " "+renewalStatus+" AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			}
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();
					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_from_Date((Date) result[5]);
					renewal.setRenewal_To_Date((Date) result[6]);
					if (result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_staus_id((short) result[9]);
					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
					renewal.setRenewalAproval_Number((Long) result[14]);
					renewal.setVehicleGroup((String) result[15]);
					renewal.setNewRRCreated((boolean) result[16]);
					renewal.setVehicleStatus((String) result[17]);
					renewal.setRenewal_receipt((String) result[18]);
					renewal.setIgnored((boolean) result[19]);
					renewal.setAllowToIgnored((boolean) result[20]);
					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public RenewalReminderDto getRenewalReminder(Long renewal_id) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, R.renewal_receipt, R.paymentTypeId, R.renewal_PayNumber,"
								+ " R.renewal_authorization, R.renewal_number, R.renewal_dateofpayment, U.firstName, R.renewal_timethreshold,"
								+ " R.renewal_periedthreshold, U2.firstName, R.renewal_approveddate, R.renewal_approvedComment,"
								+ " U3.email, R.created, U4.email, R.lastupdated FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " LEFT JOIN User U ON U.id   = R.renewal_paidbyId"
								+ " LEFT JOIN User U2 ON U2.id = R.renewal_approvedbyId"
								+ " INNER JOIN User U3 ON U3.id = R.createdById"
								+ " INNER JOIN User U4 ON U4.id = R.lastModifiedById" + " WHERE R.renewal_R_Number ="
								+ renewal_id + " AND R.companyId = " + userDetails.getCompany_id()
								+ " AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			} else {

				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, R.renewal_receipt, R.paymentTypeId, R.renewal_PayNumber,"
								+ " R.renewal_authorization, R.renewal_number, R.renewal_dateofpayment, U.firstName, R.renewal_timethreshold,"
								+ " R.renewal_periedthreshold, U2.firstName, R.renewal_approveddate, R.renewal_approvedComment,"
								+ " U3.email, R.created, U4.email, R.lastupdated FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid "
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " LEFT JOIN User U ON U.id   = R.renewal_paidbyId"
								+ " LEFT JOIN User U2 ON U2.id = R.renewal_approvedbyId"
								+ " INNER JOIN User U3 ON U3.id = R.createdById"
								+ " INNER JOIN User U4 ON U4.id = R.lastModifiedById" + " WHERE R.renewal_R_Number = "
								+ renewal_id + " AND R.companyId = " + userDetails.getCompany_id()
								+ " AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();

					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_from_Date((Date) result[5]);
					renewal.setRenewal_To_Date((Date) result[6]);
					if (result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_staus_id((short) result[9]);
					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
					renewal.setRenewalAproval_Number((Long) result[14]);
					renewal.setRenewal_receipt((String) result[15]);
					renewal.setPaymentTypeId((short) result[16]);
					renewal.setRenewal_PayNumber((String) result[17]);
					renewal.setRenewal_authorization((String) result[18]);
					renewal.setRenewal_number((String) result[19]);
					renewal.setRenewal_payment_Date((Date) result[20]);
					renewal.setRenewal_paidby((String) result[21]);
					renewal.setRenewal_timethreshold((Integer) result[22]);
					renewal.setRenewal_periedthreshold((Integer) result[23]);
					renewal.setRenewal_approvedby((String) result[24]);
					renewal.setRenewal_approveddate((Date) result[25]);
					renewal.setRenewal_approvedComment((String) result[26]);
					renewal.setCreatedBy((String) result[27]);
					renewal.setCreatedOn((Date) result[28]);
					renewal.setLastModifiedBy((String) result[29]);
					renewal.setLastupdatedOn((Date) result[30]);

					Dtos.add(renewal);
				}
			} else {
				return null;
			}

			return Dtos.get(0);

		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Override
	@Transactional
	public RenewalReminder getRenewalReminderById(Long renewal_id) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return renewalReminderDao.getRenewalReminderById(renewal_id, userDetails.getCompany_id());
			}
			return renewalReminderDao.getRenewalReminderById(renewal_id, userDetails.getCompany_id(),
					userDetails.getId());

		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
		}
	}

	@Override
	public RenewalReminderDto getRenewalReminderById(Long renewal_id, CustomUserDetails userDetails) throws Exception {

		try {
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, R.renewal_receipt, R.paymentTypeId, R.renewal_PayNumber,"
								+ " R.renewal_authorization, R.renewal_number, R.renewal_dateofpayment, U.firstName, R.renewal_timethreshold,"
								+ " R.renewal_periedthreshold, U2.firstName, R.renewal_approveddate, R.renewal_approvedComment,"
								+ " U3.email, R.created, U4.email, R.lastupdated, R.renewalTypeId, R.renewal_Subid, R.renewal_paidbyId, "
								+ " R.renewal_approvedbyId, R.vendorId, VN.vendorName, VN.vendorLocation, TC.companyName, R.tallyCompanyId ,R.ignored,R.igRemark  "
								+ " FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid"
								+ " LEFT  JOIN Vendor VN ON VN.vendorId = R.vendorId "
								+ " LEFT JOIN User U ON U.id   = R.renewal_paidbyId"
								+ " LEFT JOIN User U2 ON U2.id = R.renewal_approvedbyId "
								+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = R.tallyCompanyId"
								+ " INNER JOIN User U3 ON U3.id = R.createdById"
								+ " INNER JOIN User U4 ON U4.id = R.lastModifiedById" + " WHERE R.renewal_id ="
								+ renewal_id + " AND R.companyId = " + userDetails.getCompany_id()
								+ " AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			} else {

				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, R.renewal_receipt, R.paymentTypeId, R.renewal_PayNumber,"
								+ " R.renewal_authorization, R.renewal_number, R.renewal_dateofpayment, U.firstName, R.renewal_timethreshold,"
								+ " R.renewal_periedthreshold, U2.firstName, R.renewal_approveddate, R.renewal_approvedComment,"
								+ " U3.email, R.created, U4.email, R.lastupdated, R.renewalTypeId, R.renewal_Subid, R.renewal_paidbyId, "
								+ " R.renewal_approvedbyId, R.vendorId, VN.vendorName, VN.vendorLocation, TC.companyName, R.tallyCompanyId,R.ignored,R.igRemark "
								+ " FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid "
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " LEFT JOIN User U ON U.id   = R.renewal_paidbyId"
								+ " LEFT JOIN User U2 ON U2.id = R.renewal_approvedbyId"
								+ " LEFT JOIN Vendor VN ON VN.vendorId = R.vendorId "
								+ " LEFT JOIN TallyCompany TC ON TC.tallyCompanyId = R.tallyCompanyId"
								+ " INNER JOIN User U3 ON U3.id = R.createdById"
								+ " INNER JOIN User U4 ON U4.id = R.lastModifiedById" + " WHERE R.renewal_id = "
								+ renewal_id + " AND R.companyId = " + userDetails.getCompany_id()
								+ " AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();

					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_from_Date((Date) result[5]);
					renewal.setRenewal_To_Date((Date) result[6]);
					if (result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_staus_id((short) result[9]);
					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
					renewal.setRenewalAproval_Number((Long) result[14]);
					renewal.setRenewal_receipt((String) result[15]);
					renewal.setPaymentTypeId((short) result[16]);
					renewal.setRenewal_PayNumber((String) result[17]);
					renewal.setRenewal_authorization((String) result[18]);
					renewal.setRenewal_number((String) result[19]);
					if (result[20] != null) {
						renewal.setRenewal_payment_Date((Date) result[20]);
					}
					renewal.setRenewal_paidby((String) result[21]);
					renewal.setRenewal_timethreshold((Integer) result[22]);
					renewal.setRenewal_periedthreshold((Integer) result[23]);
					renewal.setRenewal_approvedby((String) result[24]);
					renewal.setRenewal_approveddate((Date) result[25]);
					renewal.setRenewal_approvedComment((String) result[26]);
					renewal.setCreatedBy((String) result[27]);
					renewal.setCreatedOn((Date) result[28]);
					renewal.setLastModifiedBy((String) result[29]);
					renewal.setLastupdatedOn((Date) result[30]);
					renewal.setRenewalTypeId((Integer) result[31]);
					renewal.setRenewal_Subid((Integer) result[32]);
					renewal.setRenewal_paidbyId((Long) result[33]);
					renewal.setRenewal_approvedbyId((Long) result[34]);
					if (result[35] != null) {
						renewal.setVendorId((Integer) result[35]);
						renewal.setVendorName((String) result[36]);
						if (result[37] != null) {
							renewal.setVendorName((String) result[36] + " : " + (String) result[37]);
						}
					}
					if (result[38] != null) {
						renewal.setTallyCompanyName((String) result[38]);
					}
					if (result[39] != null) {
						renewal.setTallyCompanyId((Long) result[39]);
					}
					renewal.setIgnored((boolean) result[40]);
					if(result[41] != null)
					renewal.setIgnoredRemark((String) result[41]);
					
					Dtos.add(renewal);
				}
			} else {
				return null;
			}

			return Dtos.get(0);

		} catch (Exception e) {
			throw e;
		}

	}

	@Transactional
	public void deleteRenewalReminder(Long renewalReminder, Integer companyId ,Long userId, Timestamp toDate) {

		renewalReminderDao.deleteRenewalReminder(renewalReminder, companyId ,userId ,toDate);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addRenewalReminderHistory(RenewalReminderHistory renewalReminderHistory) {

		renewalReminderHistoryDao.save(renewalReminderHistory);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateRenewalReminderHistory(RenewalReminderHistory renewalReminderHistory) {

		renewalReminderHistoryDao.save(renewalReminderHistory);
	}

	@Transactional
	public void getRenewalReminderHistoryList(Integer driver_id) {

		renewalReminderHistoryDao.getRenewalReminderHistoryList(driver_id);
	}

	@Transactional
	public List<RenewalReminderHistoryDto> getRenewalReminderHistory(Integer companyId) throws Exception {

		String sql = "SELECT R.renewalhis_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, R.renewalhis_from, "
				+ "R.renewalhis_to, R.renewalhis_receipt, R.renewalhis_Amount, R.renewal_document,R.renewal_document_id "
				+ " FROM RenewalReminderHistory AS R" + " INNER JOIN Vehicle V ON V.vid = R.vid "
				+ " LEFT JOIN RenewalType RT ON RT.renewal_id = R.renewalhis_typeId"
				+ " LEFT JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewalhis_subtypeId"
				+ " where R.companyId = " + companyId + " AND R.markForDelete = 0";
		TypedQuery<Object[]> queryt = entityManager.createQuery(sql, Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderHistoryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderHistoryDto>();
			RenewalReminderHistoryDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderHistoryDto();

				renewal.setRenewalhis_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewalhis_type((String) result[3]);
				renewal.setRenewalhis_subtype((String) result[4]);
				renewal.setRenewalhis_fromDate((Date) result[5]);
				renewal.setRenewalhis_toDate((Date) result[6]);
				renewal.setRenewalhis_receipt((String) result[7]);
				renewal.setRenewalhis_Amount((Double) result[8]);
				renewal.setRenewal_document((boolean) result[9]);
				renewal.setRenewal_document_id((Long) result[10]);

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<RenewalReminderHistoryDto> listRenewalReminderHistory(Integer Vehicle_id) throws Exception {
		CustomUserDetails userDetails = null;
		userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String sql = "SELECT R.renewalhis_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, R.renewalhis_from, "
				+ "R.renewalhis_to, R.renewalhis_receipt, R.renewalhis_Amount, R.renewal_document,R.renewal_document_id, R.renewalhis_number "
				+ " FROM RenewalReminderHistory AS R" + " INNER JOIN Vehicle V ON V.vid = R.vid "
				+ " LEFT JOIN RenewalType RT ON RT.renewal_id = R.renewalhis_typeId"
				+ " LEFT JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewalhis_subtypeId" + " where R.vid='"
				+ Vehicle_id + "' AND R.companyId = " + userDetails.getCompany_id() + " AND R.markForDelete = 0";
		TypedQuery<Object[]> queryt = entityManager.createQuery(sql, Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderHistoryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderHistoryDto>();
			RenewalReminderHistoryDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderHistoryDto();

				renewal.setRenewalhis_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewalhis_type((String) result[3]);
				renewal.setRenewalhis_subtype((String) result[4]);
				renewal.setRenewalhis_fromDate((Date) result[5]);
				renewal.setRenewalhis_toDate((Date) result[6]);
				renewal.setRenewalhis_receipt((String) result[7]);
				renewal.setRenewalhis_Amount((Double) result[8]);
				renewal.setRenewal_document((boolean) result[9]);
				renewal.setRenewal_document_id((Long) result[10]);
				renewal.setRenewalhis_number((String) result[11]);

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	@Transactional
	public RenewalReminderHistory getRenewalReminderHistory(Long renewalhis_id) {

		return renewalReminderHistoryDao.getRenewalReminderHistory(renewalhis_id);
	}

	@Transactional
	public void deleteRenewalReminderHistory(Long renewalReminderHistory, Integer companyId) {

		renewalReminderHistoryDao.deleteRenewalReminderHistory(renewalReminderHistory, companyId);
	}

	@Transactional
	public List<RenewalReminderDto> listVehicleRenewalReminder(String renewal_vehiclename) {

		TypedQuery<Object[]> queryt = null;
		queryt = entityManager.createQuery(
				"SELECT R.renewal_id, R.vid, V.vehicle_registration, RST.renewal_Type, RST.renewal_SubType, "
						+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
						+ ", R.renewal_R_Number, RRA.renewalApproval_Number, R.renewal_receipt, R.paymentTypeId, R.renewal_PayNumber,"
						+ " R.renewal_authorization, R.renewal_number, R.renewal_dateofpayment, R.renewal_paidby, R.renewal_timethreshold,"
						+ " R.renewal_periedthreshold, R.renewal_approvedby, R.renewal_approveddate, R.renewal_approvedComment,"
						+ " R.createdBy, R.created, R.lastModifiedBy, R.lastupdated, R.renewalTypeId, R.renewal_Subid FROM RenewalReminder AS R"
						+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
						+ " INNER JOIN Vehicle V ON V.vid = R.vid"
						+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
						+ " WHERE V.vehicle_registration ='" + renewal_vehiclename
						+ "' AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewal_type((String) result[3]);
				renewal.setRenewal_subtype((String) result[4]);
				renewal.setRenewal_from_Date((Date) result[5]);
				renewal.setRenewal_To_Date((Date) result[6]);
				if (result[7] != null)
					renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
				renewal.setRenewal_Amount((Double) result[8]);
				renewal.setRenewal_staus_id((short) result[9]);
				renewal.setRenewal_document((boolean) result[10]);
				renewal.setRenewal_document_id((Long) result[11]);
				renewal.setRenewal_approvedID((Long) result[12]);
				renewal.setRenewal_R_Number((Long) result[13]);
				renewal.setRenewalAproval_Number((Long) result[14]);
				renewal.setRenewal_receipt((String) result[15]);
				renewal.setPaymentTypeId((short) result[16]);
				renewal.setRenewal_PayNumber((String) result[17]);
				renewal.setRenewal_authorization((String) result[18]);
				renewal.setRenewal_number((String) result[19]);
				renewal.setRenewal_payment_Date((Date) result[20]);
				renewal.setRenewal_paidby((String) result[21]);
				renewal.setRenewal_timethreshold((Integer) result[22]);
				renewal.setRenewal_periedthreshold((Integer) result[23]);
				renewal.setRenewal_approvedby((String) result[24]);
				renewal.setRenewal_approveddate((Date) result[25]);
				renewal.setRenewal_approvedComment((String) result[26]);
				renewal.setCreatedBy((String) result[27]);
				renewal.setCreatedOn((Date) result[28]);
				renewal.setLastModifiedBy((String) result[29]);
				renewal.setLastupdatedOn((Date) result[30]);
				renewal.setRenewalTypeId((Integer) result[31]);
				renewal.setRenewal_Subid((Integer) result[32]);

				Dtos.add(renewal);
			}
		} else {
			return null;
		}

		return Dtos;

	}

	@Override
	public List<RenewalReminderDto> listVehicleRenewalReminder(Integer vid, Integer companyId) {
		TypedQuery<Object[]> queryt = null;
		queryt = entityManager.createQuery(
				"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
						+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
						+ ", R.renewal_R_Number, RRA.renewalApproval_Number, R.renewal_receipt, R.paymentTypeId, R.renewal_PayNumber,"
						+ " R.renewal_authorization, R.renewal_number, R.renewal_dateofpayment, U.firstName, R.renewal_timethreshold,"
						+ " R.renewal_periedthreshold, U2.firstName, R.renewal_approveddate, R.renewal_approvedComment,"
						+ " U3.email, R.created, U4.email, R.lastupdated, R.renewalTypeId, R.renewal_Subid ,R.ignored, R.companyId FROM RenewalReminder AS R"
						+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
						+ " INNER JOIN Vehicle V ON V.vid = R.vid"
						+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
						+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
						+ " LEFT JOIN User U  ON U.id  = R.renewal_paidbyId"
						+ " LEFT JOIN User U2 ON U2.id = R.renewal_approvedbyId"
						+ " LEFT JOIN User U3 ON U3.id = R.createdById"
						+ " LEFT JOIN User U4 ON U4.id = R.lastModifiedById" + " WHERE R.vid =" + vid
						+ " AND R.companyId = " + companyId + " AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewal_type((String) result[3]);
				renewal.setRenewal_subtype((String) result[4]);
				renewal.setRenewal_from_Date((Date) result[5]);
				renewal.setRenewal_from(dateFormat.format(renewal.getRenewal_from_Date()));
				renewal.setRenewal_To_Date((Date) result[6]);
				renewal.setRenewal_to(dateFormat.format(renewal.getRenewal_To_Date()));
				if (result[7] != null)
					renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
				renewal.setRenewal_Amount((Double) result[8]);
				renewal.setRenewal_staus_id((short) result[9]);
				renewal.setRenewal_document((boolean) result[10]);
				renewal.setRenewal_document_id((Long) result[11]);
				renewal.setRenewal_approvedID((Long) result[12]);
				renewal.setRenewal_R_Number((Long) result[13]);
				renewal.setRenewalAproval_Number((Long) result[14]);
				renewal.setRenewal_receipt((String) result[15]);
				renewal.setPaymentTypeId((short) result[16]);
				renewal.setRenewal_PayNumber((String) result[17]);
				renewal.setRenewal_authorization((String) result[18]);
				renewal.setRenewal_number((String) result[19]);
				renewal.setRenewal_payment_Date((Date) result[20]);
				renewal.setRenewal_paidby((String) result[21]);
				renewal.setRenewal_timethreshold((Integer) result[22]);
				renewal.setRenewal_periedthreshold((Integer) result[23]);
				renewal.setRenewal_approvedby((String) result[24]);
				renewal.setRenewal_approveddate((Date) result[25]);
				renewal.setRenewal_approvedComment((String) result[26]);
				renewal.setCreatedBy((String) result[27]);
				renewal.setCreatedOn((Date) result[28]);
				renewal.setLastModifiedBy((String) result[29]);
				renewal.setLastupdatedOn((Date) result[30]);
				renewal.setRenewalTypeId((Integer) result[31]);
				renewal.setRenewal_Subid((Integer) result[32]);
				renewal.setIgnored((boolean) result[33]);
				renewal.setCompanyId((Integer) result[34]);
				Dtos.add(renewal);
			}
		} else {
			return null;
		}

		return Dtos;

	}

	@Transactional
	public List<RenewalReminderDto> listVehicleRenewalReminder(Integer renewal_vehiclename, String Todate,
			Integer companyId) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_R_Number FROM RenewalReminder AS R "
						+ " INNER JOIN Vehicle V ON V.vid = R.vid"
						+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
						+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid " + " where R.vid="
						+ renewal_vehiclename + " AND R.renewal_to >= '" + Todate + "' AND R.companyId = " + companyId
						+ " AND R.markForDelete = 0 ORDER BY R.renewal_id DESC",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewal_type((String) result[3]);
				renewal.setRenewal_subtype((String) result[4]);
				renewal.setRenewal_D_from((Date) result[5]);
				renewal.setRenewal_D_to((Date) result[6]);
				if (result[7] != null)
					renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
				renewal.setRenewal_Amount((Double) result[8]);

				renewal.setRenewal_staus_id((short) result[9]);
				renewal.setRenewal_document((boolean) result[10]);
				renewal.setRenewal_document_id((Long) result[11]);
				renewal.setRenewal_R_Number((Long) result[12]);

				Dtos.add(renewal);
			}
		}
		return Dtos;

	}

	@Transactional
	public List<RenewalReminder> listRenewalReminderReport(RenewalReminderDto renewalReminder) {

		TypedQuery<RenewalReminder> query = entityManager.createQuery(
				"from RenewalReminder RR where RR.vehicle_registration='" + renewalReminder.getVehicle_registration()
						+ "' OR RR.renewal_type ='" + renewalReminder.getRenewal_type() + "' OR RR.renewal_subtype ='"
						+ renewalReminder.getRenewal_subtype() + "'  OR RR.renewal_to between '"
						+ renewalReminder.getRenewal_from() + "' AND '" + renewalReminder.getRenewal_to() + "' ",
				RenewalReminder.class);
		return query.getResultList();
	}

	@Transactional
	public List<RenewalReminder> listRenewalReminderReportVehicle(RenewalReminderDto renewalReminder) {

		TypedQuery<RenewalReminder> query = entityManager.createQuery(
				"from RenewalReminder RR where RR.vehicle_registration='" + renewalReminder.getVehicle_registration()
						+ "' AND  RR.renewal_to between '" + renewalReminder.getRenewal_from() + "' AND '"
						+ renewalReminder.getRenewal_to() + "' ",
				RenewalReminder.class);
		return query.getResultList();
	}

	@Transactional
	public List<RenewalReminder> listRenewalReminderReportType(RenewalReminderDto renewalReminder) {

		TypedQuery<RenewalReminder> query = entityManager.createQuery("from RenewalReminder RR where RR.renewal_type='"
				+ renewalReminder.getRenewal_type() + "' AND  RR.renewal_to between '"
				+ renewalReminder.getRenewal_from() + "' AND '" + renewalReminder.getRenewal_to() + "' ",
				RenewalReminder.class);
		return query.getResultList();
	}

	@Transactional
	public List<RenewalReminder> listRenewalReminderReportSubType(RenewalReminderDto renewalReminder) {

		TypedQuery<RenewalReminder> query = entityManager
				.createQuery("from RenewalReminder RR where RR.renewal_subtype='" + renewalReminder.getRenewal_subtype()
						+ "' AND  RR.renewal_to between '" + renewalReminder.getRenewal_from() + "' AND '"
						+ renewalReminder.getRenewal_to() + "' ", RenewalReminder.class);
		return query.getResultList();
	}

	@Transactional
	public List<RenewalReminderDto> listRenewalReminder(String qurey,ValueObject object) throws Exception {
		CustomUserDetails userDetails = null;
		userDetails = Utility.getObject(object);
		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT RR.renewal_id, RR.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
							+ "RR.renewal_from, RR.renewal_to, RR.dateofRenewal, RR.renewal_Amount, RR.renewal_staus_id, RR.renewal_dateofpayment, RR.renewal_receipt, U.firstName, U2.firstName, RR.renewal_PayNumber "
							+ ", RR.renewal_R_Number, RR.newRRCreated FROM RenewalReminder AS RR "
							+ " INNER JOIN Vehicle V ON V.vid = RR.vid "
							+ " LEFT JOIN User U ON U.id = RR.renewal_paidbyId"
							+ " LEFT JOIN User U2 ON U2.id = RR.renewal_approvedbyId"
							+ " INNER JOIN RenewalType RT ON RT.renewal_id = RR.renewalTypeId"
							+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = RR.renewal_Subid "
							+ " where RR.markForDelete = 0 AND RR.companyId = " + userDetails.getCompany_id() + " "
							+ qurey + " ",
					Object[].class);
		} else {
			queryt = entityManager.createQuery(
					"SELECT RR.renewal_id, RR.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
							+ "RR.renewal_from, RR.renewal_to, RR.dateofRenewal, RR.renewal_Amount, RR.renewal_staus_id, RR.renewal_dateofpayment, RR.renewal_receipt, U.firstName, U2.firstName, RR.renewal_PayNumber "
							+ ", RR.renewal_R_Number, RR.newRRCreated FROM RenewalReminder AS RR "
							+ " INNER JOIN Vehicle V ON V.vid = RR.vid "
							+ " LEFT JOIN User U ON U.id = RR.renewal_paidbyId"
							+ " LEFT JOIN User U2 ON U2.id = RR.renewal_approvedbyId"
							+ " INNER JOIN RenewalType RT ON RT.renewal_id = RR.renewalTypeId"
							+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = RR.renewal_Subid "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + " " + " where RR.markForDelete = 0 AND RR.companyId = "
							+ userDetails.getCompany_id() + "  " + qurey + " ",
					Object[].class);
		}
		queryt.setFirstResult(0);
		queryt.setMaxResults(250);
		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewal_type((String) result[3]);
				renewal.setRenewal_subtype((String) result[4]);
				renewal.setRenewal_D_from((Date) result[5]);
				renewal.setRenewal_D_to((Date) result[6]);
				if (result[7] != null)
					renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
				if(result[8] != null) {
					renewal.setRenewal_Amount((Double) result[8]);
				}else {
					renewal.setRenewal_Amount(0.0);
				}
				renewal.setRenewal_staus_id((short) result[9]);
				renewal.setRenewal_payment_Date((Date) result[10]);
				renewal.setRenewal_receipt((String) result[11]);
				renewal.setRenewal_paidby((String) result[12]);
				renewal.setRenewal_approvedby((String) result[13]);
				renewal.setRenewal_PayNumber((String) result[14]);
				renewal.setRenewal_R_Number((long) result[15]);
				renewal.setNewRRCreated((boolean) result[16]);
				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<RenewalReminderDto> Validate_RenewalReminder(String qurey) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT RR.renewal_id, RR.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
						+ "RR.renewal_from, RR.renewal_to, RR.dateofRenewal, RR.renewal_receipt "
						+ " ,RR.renewal_R_Number, RR.newRRCreated FROM RenewalReminder AS RR "
						+ " INNER JOIN Vehicle V ON V.vid = RR.vid"
						+ " INNER JOIN RenewalType RT ON RT.renewal_id = RR.renewalTypeId"
						+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = RR.renewal_Subid " + " where  " + qurey
						+ " ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewal_type((String) result[3]);
				renewal.setRenewal_subtype((String) result[4]);
				renewal.setRenewal_D_from((Date) result[5]);
				renewal.setRenewal_D_to((Date) result[6]);
				if (result[7] != null)
					renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
				renewal.setRenewal_receipt((String) result[8]);
				renewal.setRenewal_R_Number((Long) result[9]);
				renewal.setNewRRCreated((boolean) result[10]);

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<RenewalReminderDto> Validate_RenewalReminder_VehicleMandatoryCompliances(String qurey) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT RR.renewal_id, RR.vid, RR.vehicle_registration, RR.renewal_type, RR.renewal_subtype, "
						+ "RR.renewal_from, RR.renewal_to, RR.dateofRenewal " + "FROM RenewalReminder AS RR where "
						+ qurey + " ",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewal_type((String) result[3]);
				renewal.setRenewal_subtype((String) result[4]);
				renewal.setRenewal_D_from((Date) result[5]);
				renewal.setRenewal_D_to((Date) result[6]);
				if (result[7] != null)
					renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	@Transactional
	public Long countRenewalReminder() throws Exception {

		return renewalReminderDao.countRenewalReminder();
	}

	@Transactional
	public Long countTodayDueRenewalReminder(String toDate) throws Exception {

		// return renewalReminderDao.countTodayDueRenewalReminder(toDate);
		String hql = "select count(*) from RenewalReminder RR where RR.renewal_to between '" + toDate + "' AND '"
				+ toDate + "'";
		Query query = entityManager.createQuery(hql);
		return (Long) (query.getSingleResult());
	}

	@Transactional
	public List<RenewalReminderDto> SearchRenewalReminder(String Search) throws Exception {
		CustomUserDetails userDetails = null;
		List<RenewalReminderDto> Dtos = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			String sql = null;
			if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				sql = "SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, R.renewal_from, "
						+ "R.renewal_to, R.renewal_receipt, R.renewal_Amount, R.renewal_dateofpayment, U.firstName, R.renewal_R_Number, R.renewal_staus_id, R.newRRCreated"
						+ " FROM RenewalReminder AS R " + " INNER JOIN Vehicle V ON V.vid = R.vid "
						+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
						+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
						+ " LEFT JOIN User U ON U.id = R.renewal_paidbyId" + " where R.companyId = "
						+ userDetails.getCompany_id()
						+ " AND R.markForDelete = 0 AND ( lower(R.renewal_R_Number) Like ('%" + Search
						+ "%') OR R.companyId = " + userDetails.getCompany_id()
						+ " AND R.markForDelete = 0 AND lower(V.vehicle_registration) Like ('%" + Search + "%') "
						+ " OR R.companyId = " + userDetails.getCompany_id()
						+ " AND R.markForDelete = 0 AND lower(R.renewal_receipt) Like ('%" + Search + "%')  )";
			} else {

				sql = "SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, R.renewal_from, "
						+ "R.renewal_to, R.renewal_receipt, R.renewal_Amount, R.renewal_dateofpayment,  U.firstName, R.renewal_R_Number, R.renewal_staus_id, R.newRRCreated"
						+ " FROM RenewalReminder AS R "
						+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
						+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
						+ " INNER JOIN Vehicle V ON V.vid = R.vid "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
						+ userDetails.getId() + "" + " LEFT JOIN User U ON U.id = R.renewal_paidbyId"
						+ " where R.companyId = " + userDetails.getCompany_id()
						+ " AND R.markForDelete = 0 AND ( lower(R.renewal_R_Number) Like ('%" + Search
						+ "%') OR R.companyId = " + userDetails.getCompany_id()
						+ " AND R.markForDelete = 0 AND  lower(V.vehicle_registration) Like ('%" + Search + "%') "
						+ " OR R.companyId = " + userDetails.getCompany_id()
						+ " AND R.markForDelete = 0 AND  lower(R.renewal_receipt) Like ('%" + Search + "%')  )";
			}
			TypedQuery<Object[]> queryt = entityManager.createQuery(sql, Object[].class);
			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();

					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_D_from((Date) result[5]);
					renewal.setRenewal_D_to((Date) result[6]);
					renewal.setRenewal_receipt((String) result[7]);
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_payment_Date((Date) result[9]);
					renewal.setRenewal_paidby((String) result[10]);
					renewal.setRenewal_R_Number((long) result[11]);
					renewal.setRenewal_staus_id((short) result[12]);
					renewal.setNewRRCreated((boolean) result[13]);
					Dtos.add(renewal);
				}
			}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public List<RenewalReminderDto> TodayRenewalReminderList(String toDate) throws Exception {
		CustomUserDetails userDetails = null;
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, VG.vGroup, R.newRRCreated FROM RenewalReminder AS R "
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId "
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " where R.renewal_to between '" + toDate + "' AND '" + toDate + "' AND R.companyId = "
								+ userDetails.getCompany_id() + " AND R.markForDelete = 0",
						Object[].class);
			} else {

				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, VG.vGroup, R.newRRCreated FROM RenewalReminder AS R "
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId "
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " where R.renewal_to between '" + toDate + "' AND '"
								+ toDate + "' AND R.companyId = " + userDetails.getCompany_id()
								+ " AND R.markForDelete = 0",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();

					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_from_Date((Date) result[5]);
					renewal.setRenewal_To_Date((Date) result[6]);
					if (result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_staus_id((short) result[9]);

					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
					renewal.setRenewalAproval_Number((Long) result[14]);
					renewal.setVehicleGroup((String) result[15]);
					renewal.setNewRRCreated((boolean) result[16]);

					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public String getRenewalReminderMonthlyReport(Date sql) {

		String hql = "select sum(job.renewal_Amount) from RenewalReminder job"
				+ " where year(job.renewal_from) = year('" + sql + "')" + " and month(job.renewal_from) = month('" + sql
				+ "')";
		Query query = entityManager.createQuery(hql);
		return ((String) query.getSingleResult());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * add_RenewalReminder_Document(org.fleetop.persistence.model.
	 * RenewalReminderDocument)
	 */
	@Transactional
	public void add_RenewalReminder_Document(RenewalReminderDocument rewalDoc) {

		renewalReminderDocumentDao.save(rewalDoc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * get_RenewalReminder_Document(java.lang.Integer)
	 */
	@Transactional
	public RenewalReminderDocument get_RenewalReminder_Document(Long renewal_id) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return renewalReminderDocumentDao.get_RenewalReminder_Document(renewal_id, userDetails.getCompany_id());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * get_RenewalReminderHistory_Document(java.lang.Long)
	 */
	@Transactional
	public RenewalReminderDocument get_RenewalReminderHistory_Document(Long renewalhis_id) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return renewalReminderDocumentDao.get_RenewalReminderHistory_Document(renewalhis_id,
				userDetails.getCompany_id());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * delete_RenewalReminderDocument_ID_History(java.lang.Long)
	 */
	@Transactional
	public void delete_RenewalReminderDocument_ID_History(Long renewalhis_id, Integer companyId) {
		// NOTE: Delete Renewal Document using History ID

		renewalReminderDocumentDao.delete_RenewalReminderDocument_ID_History(renewalhis_id, companyId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Update_RenewalReminderDocument_ID_to_RenewalReminder(java.lang.Long, boolean,
	 * java.lang.Long)
	 */
	@Transactional
	public void Update_RenewalReminderDocument_ID_to_RenewalReminder(Long rendoc_id, boolean b, Long renewal_id,
			Integer companyId) {

		renewalReminderDao.Update_RenewalReminderDocument_ID_to_RenewalReminder(rendoc_id, b, renewal_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * add_RenewalReminderApproval(org.fleetop.persistence.model.
	 * RenewalReminderApproval)
	 */
	@Transactional
	public void add_RenewalReminderApproval(RenewalReminderApproval approval) {
		// Note: Save To Database in RenewalReminderApproval Entries
		renewalReminderApprovalDao.save(approval);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Update_ApproavalPayment_Amount_Get_Apprival_RR(java.lang.Long)
	 */
	@Transactional
	public void Update_ApproavalPayment_Amount_Get_Apprival_RR(Long renewalApproval_id, Integer companyId) {
		// Note: Update Approval Amount Value to get Select Renewal Reminder

		/*
		 * renewalReminderApprovalDao. Update_ApproavalPayment_Amount_Get_Apprival_RR(
		 * approvalPayment_Amount, renewalApproval_id);
		 */

		entityManager.createQuery(
				"Update FROM RenewalReminderApproval AS a SET a.approvalPayment_Amount = (SELECT SUM(R.renewal_Amount) FROM RenewalReminder AS R WHERE R.renewal_approvedID="
						+ renewalApproval_id + " AND R.companyId = " + companyId
						+ " AND R.markForDelete = 0) WHERE  a.renewalApproval_id=" + renewalApproval_id
						+ " AND companyId = " + companyId)
				.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Get_RenewalReminderApproval(java.lang.Long)
	 */
	@Transactional
	public RenewalReminderApproval Get_RenewalReminderApproval(Long approvalID, Integer companyId) {

		try {
			return renewalReminderApprovalDao.Get_RenewalReminderApproval(approvalID, companyId);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public RenewalReminderApprovalDto Get_RenewalReminderApprovalDetails(Long approvalID, Integer companyId)
			throws Exception {

		Query query = entityManager.createQuery(
				"SELECT R.renewalApproval_id, R.renewalApproval_Number, U.firstName, R.approvalCreated_ById,"
						+ " R.approvalCreated_Date, R.paymentTypeId, R.approvalStatusId, R.approvalPay_Number, U2.firstName,"
						+ " R.approvalPayment_ById, R.approvalPayment_Date, R.approvalPayment_Amount, R.approvalApproved_Amount,"
						+ " R.approvalPending_Amount, R.approvalCancel_Amount, R.approval_document, R.approval_document_id, U3.email,"
						+ " U4.email, R.createdById, R.lastModifiedById, R.created, R.lastupdated "
						+ " From RenewalReminderApproval AS R " + " LEFT JOIN User U ON U.id = R.approvalCreated_ById"
						+ " LEFT JOIN User U2 ON U2.id = R.approvalPayment_ById"
						+ " LEFT JOIN User U3 ON U3.id = R.createdById"
						+ " LEFT JOIN User U4 ON U4.id = R.lastModifiedById" + " WHERE R.renewalApproval_id= "
						+ approvalID + " and R.companyId = " + companyId + " and R.markForDelete = 0");

		Object[] result = null;
		try {
			result = (Object[]) query.getSingleResult();
		} catch (NoResultException nre) {
			// Ignore this because as per your logic this is ok!
		}

		RenewalReminderApprovalDto select;
		if (result != null) {
			select = new RenewalReminderApprovalDto();

			select.setRenewalApproval_id((Long) result[0]);
			select.setRenewalApproval_Number((Long) result[1]);
			select.setApprovalCreated_By((String) result[2]);
			select.setApprovalCreated_ById((Long) result[3]);
			select.setApprovalCreated_DateOn((Date) result[4]);
			select.setPaymentTypeId((short) result[5]);
			select.setApprovalStatusId((short) result[6]);
			select.setApprovalPay_Number((String) result[7]);
			select.setApprovalPayment_By((String) result[8]);
			select.setApprovalPayment_ById((Long) result[9]);
			select.setApprovalPayment_DateOn((Date) result[10]);
			select.setApprovalPayment_Amount((Double) result[11]);
			select.setApprovalApproved_Amount((Double) result[12]);
			select.setApprovalPending_Amount((Double) result[13]);
			select.setApprovalCancel_Amount((Double) result[14]);
			select.setApproval_document((boolean) result[15]);
			select.setApproval_document_id((Long) result[16]);
			select.setCreatedBy((String) result[17]);
			select.setLastModifiedBy((String) result[18]);
			select.setCreatedById((Long) result[19]);
			select.setLastModifiedById((Long) result[20]);
			select.setCreatedOn((Date) result[21]);
			select.setLastupdatedOn((Date) result[22]);
		} else {
			return null;
		}

		return select;

	}

	/*
	 * (non-Javadoc) =
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Find_Approval_RenewalReminder(java.lang.Long)
	 */
	@Transactional
	public List<RenewalReminder> Find_Approval_RenewalReminder(Long approvalID, Integer companyId) {
		// Note: Renewal to get Approval List
		return renewalReminderDao.Find_Approval_RenewalReminder(approvalID, companyId);
	}

	@Override
	public List<RenewalReminderDto> Find_Approval_RenewalReminderList(Long approvalID, CustomUserDetails userDetails)
			throws Exception {
		TypedQuery<Object[]> queryt = null;
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			queryt = entityManager.createQuery(
					"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
							+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
							+ ", R.renewal_R_Number, RRA.renewalApproval_Number, R.renewal_receipt, R.paymentTypeId, R.renewal_PayNumber,"
							+ " R.renewal_authorization, R.renewal_number, R.renewal_dateofpayment, U.firstName, R.renewal_timethreshold,"
							+ " R.renewal_periedthreshold, U2.firstName, R.renewal_approveddate, R.renewal_approvedComment,"
							+ " U3.email, R.created, U4.email, R.lastupdated, R.renewalTypeId, R.renewal_Subid, R.newRRCreated FROM RenewalReminder AS R"
							+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
							+ " INNER JOIN Vehicle V ON V.vid = R.vid"
							+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
							+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
							+ " LEFT JOIN User U ON U.id = R.renewal_paidbyId"
							+ " LEFT JOIN User U2 ON U2.id = R.renewal_approvedbyId"
							+ " LEFT JOIN User U3 ON U3.id = R.createdById"
							+ " LEFT JOIN User U4 ON U4.id = R.lastModifiedById" + " WHERE R.renewal_approvedID ="
							+ approvalID + " AND R.companyId = " + userDetails.getCompany_id()
							+ " AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
					Object[].class);
		} else {

			queryt = entityManager.createQuery(
					"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
							+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
							+ ", R.renewal_R_Number, RRA.renewalApproval_Number, R.renewal_receipt, R.paymentTypeId, R.renewal_PayNumber,"
							+ " R.renewal_authorization, R.renewal_number, R.renewal_dateofpayment, U.firstName, R.renewal_timethreshold,"
							+ " R.renewal_periedthreshold, U2.firstName, R.renewal_approveddate, R.renewal_approvedComment,"
							+ " U3.email, R.created, U4.email, R.lastupdated, R.renewalTypeId, R.renewal_Subid, R.newRRCreated FROM RenewalReminder AS R"
							+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
							+ " INNER JOIN Vehicle V ON V.vid = R.vid "
							+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
							+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
							+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
							+ userDetails.getId() + "" + " LEFT JOIN User U ON U.id = R.renewal_paidbyId"
							+ " LEFT JOIN User U2 ON U2.id = R.renewal_approvedbyId"
							+ " LEFT JOIN User U3 ON U3.id = R.createdById"
							+ " LEFT JOIN User U4 ON U4.id = R.lastModifiedById" + " WHERE R.renewal_approvedID = "
							+ approvalID + " AND R.companyId = " + userDetails.getCompany_id()
							+ " AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
					Object[].class);
		}
		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewal_type((String) result[3]);
				renewal.setRenewal_subtype((String) result[4]);
				renewal.setRenewal_from_Date((Date) result[5]);
				renewal.setRenewal_To_Date((Date) result[6]);
				if (result[7] != null)
					renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
				renewal.setRenewal_Amount((Double) result[8]);
				renewal.setRenewal_staus_id((short) result[9]);
				renewal.setRenewal_document((boolean) result[10]);
				renewal.setRenewal_document_id((Long) result[11]);
				renewal.setRenewal_approvedID((Long) result[12]);
				renewal.setRenewal_R_Number((Long) result[13]);
				renewal.setRenewalAproval_Number((Long) result[14]);
				renewal.setRenewal_receipt((String) result[15]);
				renewal.setPaymentTypeId((short) result[16]);
				renewal.setRenewal_PayNumber((String) result[17]);
				renewal.setRenewal_authorization((String) result[18]);
				renewal.setRenewal_number((String) result[19]);
				renewal.setRenewal_payment_Date((Date) result[20]);
				renewal.setRenewal_paidby((String) result[21]);
				renewal.setRenewal_timethreshold((Integer) result[22]);
				renewal.setRenewal_periedthreshold((Integer) result[23]);
				renewal.setRenewal_approvedby((String) result[24]);
				renewal.setRenewal_approveddate((Date) result[25]);
				renewal.setRenewal_approvedComment((String) result[26]);
				renewal.setCreatedBy((String) result[27]);
				renewal.setCreatedOn((Date) result[28]);
				renewal.setLastModifiedBy((String) result[29]);
				renewal.setLastupdatedOn((Date) result[30]);
				renewal.setRenewalTypeId((Integer) result[31]);
				renewal.setRenewal_Subid((Integer) result[32]);
				renewal.setNewRRCreated((boolean) result[33]);
				Dtos.add(renewal);
			}
		} else {
			return null;
		}

		return Dtos;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * getDeployment_Page_RenewalReminderApproval(java.lang.Integer)
	 */
	@SuppressWarnings("deprecation")
	@Transactional
	public Page<RenewalReminderApproval> getDeployment_Page_RenewalReminderApproval(short Status, Integer pageNumber,
			CustomUserDetails userDetails) throws Exception {
		// Note: RenealReminder Approval Entries pages
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if (!companyConfigurationService.getVehicleGroupWisePerInRenewal(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

			return renewalReminderApprovalDao.getDeployment_Page_RenewalReminderApproval(Status,
					userDetails.getCompany_id(), request);
		} else {
			return renewalReminderApprovalDao.getDeployment_Page_RenewalReminderApproval(Status,
					userDetails.getCompany_id(), userDetails.getId(), request);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Find_listRenewalReminderApproval(java.lang.Integer)
	 */
	@Transactional
	public List<RenewalReminderApprovalDto> Find_listRenewalReminderApproval(short Status, Integer pageNumber,
			CustomUserDetails userDetails) throws Exception {
		Set<RenewalReminderApprovalDto> reminderApprovalSet = null;
		try {
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePerInRenewal(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.renewalApproval_id, R.approval_document_id, U.firstName, R.approvalCreated_Date, R.approvalPayment_Amount, "
								+ "R.approval_document, R.renewalApproval_Number, R.approvalStatusId, R.paymentTypeId "
								+ " FROM RenewalReminderApproval AS R "
								+ " LEFT JOIN User U ON U.id = R.approvalCreated_ById" + " WHERE R.companyId = "
								+ userDetails.getCompany_id() + " AND R.markForDelete = 0 AND  R.approvalStatusId = "
								+ Status + " ORDER BY R.renewalApproval_id desc",
						Object[].class);
			} else {
				queryt = entityManager.createQuery(
						"SELECT R.renewalApproval_id, R.approval_document_id, U.firstName, R.approvalCreated_Date, R.approvalPayment_Amount, "
								+ "R.approval_document, R.renewalApproval_Number, R.approvalStatusId, R.paymentTypeId "
								+ " FROM RenewalReminderApproval AS R "
								+ " LEFT JOIN RenewalReminder RR ON RR.renewal_approvedID = R.renewalApproval_id"
								+ " INNER JOIN Vehicle V ON V.vid = RR.vid"
								+ " LEFT JOIN User U ON U.id = R.approvalCreated_ById"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " WHERE R.companyId = " + userDetails.getCompany_id()
								+ " AND R.markForDelete = 0 AND  R.approvalStatusId = " + Status
								+ " ORDER BY R.renewalApproval_id desc",
						Object[].class);
			}

			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderApprovalDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderApprovalDto>();
				RenewalReminderApprovalDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderApprovalDto();

					renewal.setRenewalApproval_id((Long) result[0]);
					renewal.setApproval_document_id((Long) result[1]);
					renewal.setApprovalCreated_By((String) result[2]);
					renewal.setApprovalCreated_DateOn((Date) result[3]);
					renewal.setApprovalPayment_Amount((Double) result[4]);
					renewal.setApproval_document((boolean) result[5]);
					renewal.setRenewalApproval_Number((Long) result[6]);
					renewal.setApprovalStatusId((short) result[7]);
					renewal.setPaymentTypeId((short) result[8]);
					Dtos.add(renewal);
				}
			}
			if (Dtos != null && Dtos.size() > 0) {
				reminderApprovalSet = new HashSet<>();
				reminderApprovalSet.addAll(Dtos);
				Dtos.clear();
				Dtos.addAll(reminderApprovalSet);
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		} finally {
			reminderApprovalSet = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Get_ApprovalID_to_RenewalReminder_Amount(java.lang.Long)
	 */
	@Transactional
	public Double Get_ApprovalID_to_RenewalReminder_Amount(Long renewalApproval_id) {
		String hql = "select sum(job.renewal_Amount) from RenewalReminder job" + " where job.renewal_approvedID="
				+ renewalApproval_id + " ";
		Query query = entityManager.createQuery(hql);
		return ((Double) query.getSingleResult());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Update_ADD_ApproavalPayment_Amount(java.lang.Double, java.lang.Long)
	 */
	@Transactional
	public void Update_ADD_ApproavalPayment_Amount(Double renewal_Amount, Long renewalApproval_id) {
		// Update Approval Payment add

		entityManager
				.createQuery("UPDATE RenewalReminderApproval  SET approvalPayment_Amount = approvalPayment_Amount + "
						+ renewal_Amount + " where renewalApproval_id=" + renewalApproval_id)
				.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * delete_RenewalReminder_To_Approval_ID(java.lang.Long)
	 */
	@Transactional
	public void delete_RenewalReminder_To_Approval_ID(Long renewalApproval_id, Integer companyId) {
		// Note : Delete All Approval Entries to Renewal reminder

		renewalReminderDao.delete_RenewalReminder_To_Approval_ID(renewalApproval_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * delete_RenewalReminderApproval_ID(java.lang.Long)
	 */
	@Transactional
	public void delete_RenewalReminderApproval_ID(Long renewalApproval_id, Integer companyId) {
		// Note : delete Approval Entries using Approval Id

		renewalReminderApprovalDao.delete_RenewalReminderApproval_ID(renewalApproval_id, companyId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * update_RenewalReminderApproval_ApprovedBY_andDate(java.lang.String,
	 * java.sql.Timestamp, java.lang.String, java.lang.Long)
	 */
	@Transactional
	public void update_RenewalReminderApproval_ApprovedBY_andDate(Long approvalCreated_By, Timestamp toDate,
			Long renewalApproval_id, Integer companyId, short statusid) {
		// Note : Update Approval Time
		renewalReminderApprovalDao.update_RenewalReminderApproval_ApprovedBY_andDate(approvalCreated_By, toDate,
				renewalApproval_id, companyId, statusid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Update_RenewalReminder_ApprovedPayment_Details(java.lang.String,
	 * java.lang.String, java.sql.Timestamp, java.lang.String, java.lang.String,
	 * java.lang.String, java.sql.Timestamp, java.lang.Long)
	 */
	@Transactional
	public void Update_RenewalReminder_ApprovedPayment_Details(Double approvalPending_Amount, String approvalPay_Number,
			Timestamp toDate, Long approvalPayment_By, Long lastUpdatedBy, Timestamp toDate2, Long renewalApproval_id,
			Integer companyId, short staus, short paymentTypeId) {
		// Note: Approval Update Details

		renewalReminderApprovalDao.Update_RenewalReminder_ApprovedPayment_Details(approvalPending_Amount,
				approvalPay_Number, toDate, approvalPayment_By, lastUpdatedBy, toDate2, renewalApproval_id, companyId,
				staus, paymentTypeId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Update_RenewalReminderApproval_ID_To_Payment_Status_Change(java.lang. String,
	 * java.lang.String, java.lang.String, java.sql.Timestamp, java.lang.String,
	 * java.lang.Long)
	 */
	@Transactional
	public void Update_RenewalReminderApproval_ID_To_Payment_Status_Change(String approvalPay_Number,
			Long approvalPayment_By, Timestamp toDate, Long renewalApproval_id, Integer companyId, short status,
			short paymentTypeId) {
		// Note : This Approval Payment to Update all Approval Renewal Reminder
		// OPEN Status to IN PROGRESS Status Change

		renewalReminderDao.Update_RenewalReminderApproval_ID_To_Payment_Status_Change(approvalPay_Number,
				approvalPayment_By, toDate, renewalApproval_id, companyId, status, paymentTypeId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Update_RenewalReminder_Upload_File(java.lang.String, boolean,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	@Transactional
	public void Update_RenewalReminder_Upload_File(String renewal_receipt, boolean renewal_document,
			String renewal_authorization, String renewal_number, short renewal_status, Long renewal_id,
			Integer companyId) {
		// Note : Receipt number also Change Status IN PROGREES TO NOT APPROVAED

		renewalReminderDao.Update_RenewalReminder_Upload_File(renewal_receipt, renewal_document, renewal_authorization,
				renewal_number, renewal_status, renewal_id, companyId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Search_RenewalReminderApproval(java.lang.String)
	 */
	@Transactional
	public List<RenewalReminderApprovalDto> Search_RenewalReminderApproval(String searchDate) {
		List<RenewalReminderApprovalDto> Dtos = null;

		/* this only Select column */
		if(searchDate != null && !searchDate.trim().equalsIgnoreCase("") && searchDate.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.renewalApproval_id, R.approval_document_id, U.firstName, R.approvalCreated_Date, R.approvalPayment_Amount, "
						+ "R.approval_document, R.approvalStatusId " + "FROM RenewalReminderApproval AS R "
						+ " LEFT JOIN User U ON U.id = R.approvalCreated_ById" + " WHERE R.renewalApproval_id="
						+ searchDate + " OR lower(R.renewalApproval_id) Like ('%" + searchDate
						+ "%') ORDER BY R.renewalApproval_id desc",
				Object[].class);

		List<Object[]> results = queryt.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderApprovalDto>();
			RenewalReminderApprovalDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderApprovalDto();

				renewal.setRenewalApproval_id((Long) result[0]);
				renewal.setApproval_document_id((Long) result[1]);
				renewal.setApprovalCreated_By((String) result[2]);
				renewal.setApprovalCreated_DateOn((Date) result[3]);
				renewal.setApprovalPayment_Amount((Double) result[4]);
				renewal.setApproval_document((boolean) result[5]);

				renewal.setApprovalStatusId((short) result[6]);
				Dtos.add(renewal);
			}
		}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Subtrack_PendingAmount_Add_Approval_APPROVED_Amount_Value(java.lang. Double,
	 * java.lang.Long)
	 */
	@Transactional
	public void Subtract_PendingAmount_Add_Approval_APPROVED_Amount_Value(Double renewal_Amount,
			Long renewal_approvedID, Integer companyId) {
		// Note: Pending Amount Subtract and Approval Amount Add

		entityManager
				.createQuery("UPDATE RenewalReminderApproval  SET approvalApproved_Amount = approvalApproved_Amount + "
						+ renewal_Amount + ", approvalPending_Amount = approvalPending_Amount - " + renewal_Amount
						+ " where renewalApproval_id=" + renewal_approvedID + " AND companyId = " + companyId + "")
				.executeUpdate();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * add_RenewalReminderApproval_Document(org.fleetop.persistence.model.
	 * RenewalReminderAppDocument)
	 */
	@Transactional
	public void add_RenewalReminderApproval_Document(
			org.fleetopgroup.persistence.document.RenewalReminderAppDocument rewalDoc) {
		// Note : Save Renewal Approval Document
		rewalDoc.set_id(sequenceCounterService
				.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_RENEWAL_REMINDER_APP_DOCUMENT));
		mongoTemplate.save(rewalDoc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Update_RenewalReminderApprovalDocument_ID_to_RenewalReminderApproval(java
	 * .lang.Long, boolean, java.lang.Long)
	 */
	@Transactional
	public void Update_RenewalReminderApprovalDocument_ID_to_RenewalReminderApproval(Long rendoc_id, boolean b,
			Long renewalApproval_id, Integer companyId) {
		// Note: Update the Value Approval Document true and Document_id

		renewalReminderApprovalDao.Update_RenewalReminderApprovalDocument_ID_to_RenewalReminderApproval(rendoc_id, b,
				renewalApproval_id, companyId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Get_RenewalReminderApproval_Document(java.lang.Long)
	 */
	@Transactional
	public org.fleetopgroup.persistence.document.RenewalReminderAppDocument Get_RenewalReminderApproval_Document(
			Long renewal_id) {
		// Note: Renewal Reminder Document
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(renewal_id).and("companyId").is(userDetails.getCompany_id()));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.RenewalReminderAppDocument.class);
	}
	
	@Transactional
	public org.fleetopgroup.persistence.document.RenewalReminderAppDocument Get_RenewalReminderApproval_DocumentForMobile(
			Long renewal_id, int companyId) {

		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(renewal_id).and("companyId").is(companyId));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.RenewalReminderAppDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Update_RenewalReminder_Approval_Amount(java.lang.Double, java.lang.Long)
	 */
	@Transactional
	public void Update_RenewalReminder_Approval_Amount(Double renewalAmount, Long renewalID, Integer companyId) {
		// Note : Update RenewalReminder Approval Amount Details
		renewalReminderDao.Update_RenewalReminder_Approval_Amount(renewalAmount, renewalID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * update_RenewalReminder_DD_Number(java.lang.String, java.lang.String,
	 * java.lang.String, java.util.Date, java.lang.Long)
	 */
	@Transactional
	public void update_RenewalReminder_DD_Number(String renewal_PayNumber, Long renewal_paidby,
			Date renewal_dateofpayment, Long renewal_id, Integer companyId, short paymentTypeId) {
		// Update Payment DD number

		renewalReminderDao.update_RenewalReminder_DD_Number(renewal_PayNumber, renewal_paidby, renewal_dateofpayment,
				renewal_id, companyId, paymentTypeId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * update_Cancel_ApprovalID_to_Status_changeDate_From_To(java.lang.String,
	 * java.lang.String, java.lang.String, java.util.Date, java.util.Date,
	 * java.lang.Long)
	 */
	@Transactional
	public void update_Cancel_ApprovalID_to_Status_changeDate_From_To(short renewal_status, Long firstName,
			String renewal_approvedComment, Date From, Date To, Long renewal_id, Integer companyId) {
		// Update Approval ID to Change Cancel Status
		renewalReminderDao.update_Cancel_ApprovalID_to_Status_changeDate_From_To(renewal_status, firstName,
				renewal_approvedComment, From, To, renewal_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * Add_Approval_Cancel_Amount_Value(java.lang.Double, java.lang.Long)
	 */
	@Transactional
	public void Add_Approval_Cancel_Amount_Value(Double renewal_Amount, Long renewal_approvedID, Integer companyId) {

		// Update Cancel Amount Renewal Reminder

		entityManager.createQuery("UPDATE RenewalReminderApproval  SET approvalCancel_Amount = approvalCancel_Amount + "
				+ renewal_Amount + " where renewalApproval_id=" + renewal_approvedID + " AND companyId = " + companyId)
				.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IRenewalReminderService#
	 * RENEWAL_REMINDER_GROUP_WISE_REPORT(java.lang.String, java.lang.String)
	 */
	@Transactional
	public ArrayList<RenewalReminderDto> RENEWAL_REMINDER_GROUP_WISE_REPORT(String vEHICLEGROUP, String Todate) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.renewal_id, R.vid, R.vehicle_registration, R.renewal_type, R.renewal_subtype, R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id FROM RenewalReminder AS R where R.renewal_to >= '"
						+ Todate + "' AND R.vid IN (SELECT V.vid FROM Vehicle AS V WHERE V.vehicle_Group='"
						+ vEHICLEGROUP + "')",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		ArrayList<RenewalReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewal_type((String) result[3]);
				renewal.setRenewal_subtype((String) result[4]);
				renewal.setRenewal_D_from((Date) result[5]);
				renewal.setRenewal_D_to((Date) result[6]);
				if (result[7] != null)
					renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
				renewal.setRenewal_Amount((Double) result[8]);

				renewal.setRenewal_staus_id((short) result[9]);

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	@Transactional
	public Object[] count_TOTAL_RENEWAL_REMINDER_FIVEDAYS_ISSUES(String dayOne, String dayTwo, String dayThree,
			String dayFour, String dayFive) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Query queryt = null;

			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery("SELECT COUNT(co),(SELECT COUNT(Pho) FROM RenewalReminder AS Pho "
						+ "where  Pho.renewal_to between '" + dayOne + "' AND '" + dayOne + "' AND Pho.companyId = "
						+ userDetails.getCompany_id() + " and Pho.markForDelete = 0 ), "
						+ " (SELECT  COUNT(Pur) FROM RenewalReminder AS Pur " + " where  Pur.renewal_to between '"
						+ dayTwo + "' AND '" + dayTwo + "' AND Pur.companyId = " + userDetails.getCompany_id()
						+ " and Pur.markForDelete = 0 )," + " (SELECT  COUNT(RR) FROM RenewalReminder AS RR "
						+ " where  RR.renewal_to between '" + dayThree + "' AND '" + dayThree + "' AND RR.companyId = "
						+ userDetails.getCompany_id() + " and RR.markForDelete = 0 ),"
						+ " (SELECT  COUNT(FE) FROM RenewalReminder AS FE " + " where  FE.renewal_to between '"
						+ dayFour + "' AND '" + dayFour + "' AND FE.companyId = " + userDetails.getCompany_id()
						+ " and FE.markForDelete = 0), " + " (SELECT  COUNT(RD) FROM RenewalReminder AS RD "
						+ "	INNER JOIN Vehicle V ON V.vid = RD.vid AND V.vStatusId <> 4" + " where  RD.renewal_to < '"
						+ dayOne + "'  AND RD.companyId = " + userDetails.getCompany_id()
						+ " and RD.markForDelete = 0 AND RD.newRRCreated = 0), "
						+ " (SELECT  COUNT(RS) FROM RenewalReminder AS RS "
						+ "	INNER JOIN Vehicle V ON V.vid = RS.vid AND V.vStatusId <> 4"
						+ " where  RS.dateofRenewal <= '" + dayOne + "' AND RS.renewal_to > '" + dayOne
						+ "'  AND RS.companyId = " + userDetails.getCompany_id()
						+ " and RS.markForDelete = 0 AND RS.newRRCreated = 0) " + " FROM RenewalReminder As co "
						+ " Where co.renewal_to between '" + dayFive + "' AND   '" + dayFive + "' AND co.companyId = "
						+ userDetails.getCompany_id() + " and co.markForDelete = 0");
			} else {

				queryt = entityManager.createQuery("SELECT COUNT(co),(SELECT COUNT(Pho) FROM RenewalReminder AS Pho "
						+ "	INNER JOIN Vehicle V ON V.vid = Pho.vid "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
						+ userDetails.getId() + "" + " where  Pho.renewal_to between '" + dayOne + "' AND '" + dayOne
						+ "' AND Pho.companyId = " + userDetails.getCompany_id() + " and Pho.markForDelete = 0 ), "
						+ " (SELECT  COUNT(Pur) FROM RenewalReminder AS Pur "
						+ "	INNER JOIN Vehicle V ON V.vid = Pur.vid "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
						+ userDetails.getId() + "" + " where  Pur.renewal_to between '" + dayTwo + "' AND '" + dayTwo
						+ "' AND Pur.companyId = " + userDetails.getCompany_id() + " and Pur.markForDelete = 0 ),"
						+ " (SELECT  COUNT(RR) FROM RenewalReminder AS RR " + "	INNER JOIN Vehicle V ON V.vid = RR.vid "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
						+ userDetails.getId() + "" + " where  RR.renewal_to between '" + dayThree + "' AND '" + dayThree
						+ "' AND RR.companyId = " + userDetails.getCompany_id() + " and RR.markForDelete = 0 ),"
						+ " (SELECT  COUNT(FE) FROM RenewalReminder AS FE " + "	INNER JOIN Vehicle V ON V.vid = FE.vid "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
						+ userDetails.getId() + "" + " where  FE.renewal_to between '" + dayFour + "' AND '" + dayFour
						+ "' AND FE.companyId = " + userDetails.getCompany_id() + " and FE.markForDelete = 0), "
						+ " (SELECT  COUNT(RD) FROM RenewalReminder AS RD "
						+ "	INNER JOIN Vehicle V ON V.vid = RD.vid AND V.vStatusId <> 4"
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
						+ userDetails.getId() + "" + " where  RD.renewal_to < '" + dayOne + "'  AND RD.companyId = "
						+ userDetails.getCompany_id() + " and RD.markForDelete = 0 AND RD.newRRCreated = 0), "
						+ " (SELECT  COUNT(RS) FROM RenewalReminder AS RS "
						+ "	INNER JOIN Vehicle V ON V.vid = RS.vid AND V.vStatusId <> 4"
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
						+ userDetails.getId() + "" + " where  RS.dateofRenewal <= '" + dayOne
						+ "' AND RS.renewal_to > '" + dayOne + "' AND RS.companyId = " + userDetails.getCompany_id()
						+ " and RS.markForDelete = 0 AND RS.newRRCreated = 0) " + " FROM RenewalReminder As co "
						+ "	INNER JOIN Vehicle V ON V.vid = co.vid "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
						+ userDetails.getId() + "" + " Where co.renewal_to between '" + dayFive + "' AND   '" + dayFive
						+ "' AND co.companyId = " + userDetails.getCompany_id() + " and co.markForDelete = 0");
			}
			Object[] count = (Object[]) queryt.getSingleResult();
			return count;

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public List<RenewalReminderDto> Find_listRenewalReminder(String status) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, R.vehicle_registration, R.renewal_type, R.renewal_subtype, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_status, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, R.newRRCreated FROM RenewalReminder AS R "
								+ " WHERE R.renewal_status ='" + status + "' AND R.companyId = "
								+ userDetails.getCompany_id() + " AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			} else {

				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, R.vehicle_registration, R.renewal_type, R.renewal_subtype, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_status, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, R.newRRCreated FROM RenewalReminder AS R "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " WHERE R.renewal_status ='" + status
								+ "' AND R.companyId = " + userDetails.getCompany_id()
								+ " AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();

					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_D_from((Date) result[5]);
					renewal.setRenewal_D_to((Date) result[6]);
					if (result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_status((String) result[9]);
					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
					renewal.setNewRRCreated((boolean) result[14]);
					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	@Override
	public RenewalReminderApprovalDto Get_RenewalReminderApprovalByApprovalNumber(Long approval_Number,
			Integer companyId, Long userId) throws Exception {
		try {
			Query query = null;
			if (!companyConfigurationService.getVehicleGroupWisePerInRenewal(companyId,
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT R.renewalApproval_id, R.renewalApproval_Number, U.firstName, R.approvalCreated_ById,"
								+ " R.approvalCreated_Date, R.paymentTypeId, R.approvalStatusId, R.approvalPay_Number, U2.firstName,"
								+ " R.approvalPayment_ById, R.approvalPayment_Date, R.approvalPayment_Amount, R.approvalApproved_Amount,"
								+ " R.approvalPending_Amount, R.approvalCancel_Amount, R.approval_document, R.approval_document_id, U3.email,"
								+ " U4.email, R.createdById, R.lastModifiedById, R.created, R.lastupdated "
								+ " From RenewalReminderApproval AS R "
								+ " LEFT JOIN User U ON U.id = R.approvalCreated_ById"
								+ " LEFT JOIN User U2 ON U2.id = R.approvalPayment_ById"
								+ " LEFT JOIN User U3 ON U3.id = R.createdById"
								+ " LEFT JOIN User U4 ON U4.id = R.lastModifiedById"
								+ " WHERE R.renewalApproval_Number= " + approval_Number + " and R.companyId = "
								+ companyId + " and R.markForDelete = 0");
			} else {
				query = entityManager.createQuery(
						"SELECT R.renewalApproval_id, R.renewalApproval_Number, U.firstName, R.approvalCreated_ById,"
								+ " R.approvalCreated_Date, R.paymentTypeId, R.approvalStatusId, R.approvalPay_Number, U2.firstName,"
								+ " R.approvalPayment_ById, R.approvalPayment_Date, R.approvalPayment_Amount, R.approvalApproved_Amount,"
								+ " R.approvalPending_Amount, R.approvalCancel_Amount, R.approval_document, R.approval_document_id, U3.email,"
								+ " U4.email, R.createdById, R.lastModifiedById, R.created, R.lastupdated "
								+ " From RenewalReminderApproval AS R "
								+ " LEFT JOIN User U ON U.id = R.approvalCreated_ById"
								+ " LEFT JOIN User U2 ON U2.id = R.approvalPayment_ById"
								+ " LEFT JOIN User U3 ON U3.id = R.createdById"
								+ " LEFT JOIN User U4 ON U4.id = R.lastModifiedById"
								+ " WHERE R.renewalApproval_Number= " + approval_Number + " and R.companyId = "
								+ companyId + " and R.markForDelete = 0");
			}

			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			RenewalReminderApprovalDto select;
			if (result != null) {
				select = new RenewalReminderApprovalDto();

				select.setRenewalApproval_id((Long) result[0]);
				select.setRenewalApproval_Number((Long) result[1]);
				select.setApprovalCreated_By((String) result[2]);
				select.setApprovalCreated_ById((Long) result[3]);
				select.setApprovalCreated_DateOn((Date) result[4]);
				select.setPaymentTypeId((short) result[5]);
				select.setApprovalStatusId((short) result[6]);
				select.setApprovalPay_Number((String) result[7]);
				select.setApprovalPayment_By((String) result[8]);
				select.setApprovalPayment_ById((Long) result[9]);
				select.setApprovalPayment_DateOn((Date) result[10]);
				select.setApprovalPayment_Amount((Double) result[11]);
				select.setApprovalApproved_Amount((Double) result[12]);
				select.setApprovalPending_Amount((Double) result[13]);
				select.setApprovalCancel_Amount((Double) result[14]);
				select.setApproval_document((boolean) result[15]);
				select.setApproval_document_id((Long) result[16]);
				select.setCreatedBy((String) result[17]);
				select.setLastModifiedBy((String) result[18]);
				select.setCreatedById((Long) result[19]);
				select.setLastModifiedById((Long) result[20]);
				select.setCreatedOn((Date) result[21]);
				select.setLastupdatedOn((Date) result[22]);
			} else {
				return null;
			}

			return select;

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public void saveRenewalDocHistory(
			org.fleetopgroup.persistence.document.RenewalReminderDocumentHistory documentHistory) throws Exception {
		documentHistory.set_id(sequenceCounterService
				.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_RENEWAL_REMINDER_DOCUMENT_HISTORY));
		mongoTemplate.save(documentHistory);
	}

	@Override
	@Transactional
	public void updateRenewalPeriod(RenewalReminder renewalReminder) throws Exception {
		try {
			renewalReminderDao.updateRenewalPeriod(renewalReminder.getRenewal_from(), renewalReminder.getRenewal_to(),
					renewalReminder.getRenewal_timethreshold(), renewalReminder.getDateofRenewal(),
					renewalReminder.getRenewal_id(), renewalReminder.getCompanyId(), renewalReminder.getLastupdated(),
					renewalReminder.getLastModifiedById(), renewalReminder.getRenewal_periedthreshold());
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<RenewalReminderDto> getVehicleRenewalExpenses(Integer vid, Timestamp startDateOfMonth,
			Integer companyId, String endDateOfMonth) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.renewal_id, R.vid, V.vehicle_registration, R.renewal_from, R.renewal_to, R.renewal_Amount, RT.renewal_Type, RST.renewal_SubType, R.newRRCreated "
						+ " FROM RenewalReminder AS R " + " INNER JOIN Vehicle V ON V.vid = R.vid "
						+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
						+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid " + " where R.vid = "
						+ vid + " AND ('" + startDateOfMonth + "' between renewal_from AND R.renewal_to " + " OR '"
						+ endDateOfMonth + "' between renewal_from AND R.renewal_to  )" + " AND R.companyId = "
						+ companyId + " AND R.markForDelete = 0 AND R.renewal_Amount > 0",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewal_from_Date((Date) result[3]);
				renewal.setRenewal_To_Date((Date) result[4]);
				renewal.setRenewal_Amount((Double) result[5]);
				renewal.setRenewal_type((String) result[6]);
				renewal.setRenewal_subtype((String) result[7]);
				renewal.setNewRRCreated((boolean) result[8]);
				Dtos.add(renewal);
			}
		}
		return Dtos;

	}

	@Override
	public List<RenewalReminderDto> getGroupRenewalExpenses(long vehicleGroupId, Timestamp startDateOfMonth,
			Integer companyId,String endDateOfMonth) throws Exception {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.renewal_id, R.vid, V.vehicle_registration, R.renewal_from, R.renewal_to, R.renewal_Amount, R.newRRCreated "
						+ " FROM RenewalReminder AS R " + " INNER JOIN Vehicle V ON V.vid = R.vid "
						+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId" + " where VG.gid = "
						+ vehicleGroupId + " AND  ('" + startDateOfMonth + "' between renewal_from AND R.renewal_to " + " OR '"+ endDateOfMonth + "' between renewal_from AND R.renewal_to  )" 
						+ " AND R.companyId = " + companyId + " AND R.markForDelete = 0",
				Object[].class);

		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewal_from_Date((Date) result[3]);
				renewal.setRenewal_To_Date((Date) result[4]);
				renewal.setRenewal_Amount((Double) result[5]);
				renewal.setNewRRCreated((boolean) result[6]);

				Dtos.add(renewal);
			}
		}
		return Dtos;

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ValueObject saveEmailRenewalReminder(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		Timestamp createdDateTime = null;
		RenewalMailConfiguration RenewalMailConfiguration = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			createdDateTime = new java.sql.Timestamp(new Date().getTime());
			RenewalMailConfiguration = new RenewalMailConfiguration();

			RenewalMailConfiguration.setEmailIds(valueObject.getString("emailId"));
			RenewalMailConfiguration.setCompanyId(userDetails.getCompany_id());
			RenewalMailConfiguration.setCreatedOn(createdDateTime);
			RenewalMailConfiguration.setLastModifiedOn(createdDateTime);
			RenewalMailConfiguration.setLastModifiedById(userDetails.getId());
			RenewalMailConfiguration.setCreatedById(userDetails.getId());
			RenewalMailConfiguration.setMarkForDelete(false);
			renewalReminderMail.save(RenewalMailConfiguration);

			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails = null;
			createdDateTime = null;
		}
	}

	/* Getting EmailIds Renewal Reminder */
	@Override
	public List<RenewalMailConfiguration> getAllEmailRenewalReminder() throws Exception {

		return renewalReminderMail.getAllReminderEmailAlertQueueDetails();
	}

	/* Getting List Of The Renewal Reminder Month Wise */

	@Transactional
	public List<RenewalReminderDto> get_listRenewalReminderMonthWise(String startDate, String endDate,
			Integer companyId) throws Exception {

		try {
			TypedQuery<Object[]> queryt = null;

			queryt = entityManager.createQuery(
					"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
							+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
							+ ", R.renewal_R_Number, RRA.renewalApproval_Number, VG.vGroup, R.newRRCreated, R.companyId, VS.vStatus FROM RenewalReminder AS R"
							+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
							+ " INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> "
							+ VehicleStatusConstant.VEHICLE_STATUS_SOLD + " "
							+ " INNER JOIN VehicleStatus VS ON VS.sid = V.vStatusId " 
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
							+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
							+ " WHERE  R.companyId =" + companyId + " AND R.renewal_to between '" + startDate
							+ "' AND '" + endDate
							+ "' AND R.markForDelete = 0 AND R.newRRCreated = 0 ORDER BY R.renewal_to ASC ",
					Object[].class);

			// queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();

					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_from_Date((Date) result[5]);
					renewal.setRenewal_To_Date((Date) result[6]);
					if (result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_staus_id((short) result[9]);
					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
					renewal.setRenewalAproval_Number((Long) result[14]);
					renewal.setVehicleGroup((String) result[15]);
					renewal.setNewRRCreated((boolean) result[16]);
					renewal.setCompanyId((Integer) result[17]);

					if (renewal.getRenewal_Amount() == null) {
						renewal.setRenewal_Amount(0.0);
					}
					renewal.setVehicleStatus((String) result[18]);
					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<RenewalMailConfiguration> getAllEmail_ById(Integer companyId) throws Exception {
		return renewalReminderMail.getAllReminderEmail_Ids(companyId);
	}

	@Override
	@Transactional
	public void update_Email_ById(String email, long configurationId, Long userId, Integer companyId) throws Exception {
		renewalReminderMail.updateReminderEmail_Ids(email, userId, configurationId, companyId);
	}

	@Override
	public List<RenewalMailConfiguration> getAllEmailTripSheetDailyWorkStatus() throws Exception {
		return renewalReminderMail.getAllEmailTripSheetDailyWorkStatusDetails(
				RenewalReminderEmailConfiguration.ADMIN_DAILY_WORK_STATUS_EMAIL);
	}

	@Override
	public Long getRenewalReminderCount(String startDate, String endDate, Integer companyId) throws Exception {

		Query queryt = null;
		queryt = entityManager.createQuery("SELECT COUNT(R.renewal_id) " + " From RenewalReminder as R "
				+ " WHERE R.created between '" + startDate + "' AND '" + endDate + "' AND R.companyId = " + companyId
				+ "  " + " AND R.markForDelete = 0 AND R.newRRCreated = 0 ", Object[].class);

		Long count = (long) 0;
		try {

			if ((Long) queryt.getSingleResult() != null) {
				count = (Long) queryt.getSingleResult();
			}

		} catch (NoResultException nre) {
		}

		return count;
	}

	@Override
	public HashMap<Integer, Long> getALLEmailRenewalRemindersDailyWorkStatus(String startDate, String endDate)
			throws Exception {
		try {
			TypedQuery<Object[]> typedQuery = null;
			HashMap<Integer, Long> map = null;

			typedQuery = entityManager.createQuery("SELECT COUNT(R.renewal_id), R.companyId "
					+ " From RenewalReminder as R " + " WHERE  R.dateofRenewal between '" + startDate + "' AND '" + endDate
					+ "' AND R.markForDelete = 0 AND R.newRRCreated = 0 GROUP BY R.companyId ", Object[].class);

			List<Object[]> results = typedQuery.getResultList();

			if (results != null && !results.isEmpty()) {

				map = new HashMap<>();

				for (Object[] result : results) {

					map.put((Integer) result[1], (Long) result[0]);

				}
			}

			return map;
		} catch (Exception e) {
			throw e;
		}
	}

	// Renewal Reminder Overdue Count Logic Start

	@Override
	public List<RenewalReminderDto> getRenewalReminderOverDue(Long userId, Integer companyId) throws Exception {
		TypedQuery<Object[]> queryt = null;

		try {

			queryt = entityManager.createQuery(
					"SELECT R.renewal_id, R.vid, R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, "
							+ "  R.renewal_document, R.renewal_document_id, R.renewal_approvedID, R.renewal_R_Number, R.newRRCreated "
							+ " FROM RenewalReminder AS R" + " WHERE R.renewal_staus_id ="
							+ RenealReminderStatus.NOT_APPROVED + " AND R.companyId = " + companyId
							+ " AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
					Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();
					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					if (result[2] != null)
						renewal.setRenewal_from_Date((Date) result[2]);
					if (result[3] != null)
						renewal.setRenewal_To_Date((Date) result[3]);
					if (result[4] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[4]));
					if (result[5] != null)
						renewal.setRenewal_Amount((Double) result[5]);
					renewal.setRenewal_staus_id((short) result[6]);
					if (result[7] != null)
						renewal.setRenewal_document((boolean) result[7]);
					renewal.setRenewal_document_id((Long) result[8]);
					renewal.setRenewal_approvedID((Long) result[9]);
					if (result[10] != null)
						renewal.setRenewal_R_Number((Long) result[10]);
					renewal.setNewRRCreated((boolean) result[11]);

					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}
	// Renewal Reminder Overdue Count Logic End

	@SuppressWarnings("unchecked")
	@Override
	public ValueObject configureDailyWorkStatusEmailBody(ValueObject valueInObject) throws Exception {

		ValueObject valueOutObject = null;
		TripSheetDto highyestTripDetail = null;
		try {

			RenewalMailConfiguration renewalMailConfiguration = (RenewalMailConfiguration) valueInObject
					.get("renewalMailConfiguration");

			Long fuelEntriesCounts = valueInObject.getLong("fuelEntriesCounts");
			Long serviceReminderCounts = valueInObject.getLong("serviceReminderCounts");
			String emailDate = valueInObject.getString("emailDate");

			TripSheetDto tripSheetClosed = (TripSheetDto) valueInObject.get("tripSheetCountsClosed");
			Long tripSheetCountsClosed = (long) 0;
			Long usesKM = (long) 0;

			if (tripSheetClosed != null) {
				tripSheetCountsClosed = tripSheetClosed.getTripSheetID();
				if (tripSheetClosed.getTripSheetNumber() != null)
					usesKM = tripSheetClosed.getTripSheetNumber();
			}

			Long tripSheetCountsMissedClosing = valueInObject.getLong("tripSheetCountsMissedClosing", 0);

			String tripSheetNumber = "";
			String dayDiff = "";
			if (valueInObject.getLong("tripSheetNumber", 0) > 0) {
				tripSheetNumber = "TS-" + valueInObject.getLong("tripSheetNumber", 0) + "";
				dayDiff = valueInObject.getLong("dayDiff", 0) + " Days";
			}
			Long renewalReminderOverDueCounts = valueInObject.getLong("renewalReminderOverDueCounts", 0);
			Long tripSheetCountsInOpenState = valueInObject.getLong("tripSheetCountsInOpenState", 0);

			HashMap<Integer, Long> seCompleteCount = (HashMap<Integer, Long>) valueInObject.get("seCompleteCount");
			HashMap<Integer, Long> serviceEntriesCounts = (HashMap<Integer, Long>) valueInObject
					.get("serviceEntriesCounts");
			HashMap<Integer, Long> sevenDaysSEData = (HashMap<Integer, Long>) valueInObject.get("sevenDaysSEData");
			HashMap<Integer, Long> fifteenDaysSEData = (HashMap<Integer, Long>) valueInObject.get("fifteenDaysSEData");
			HashMap<Integer, Long> monthSEData = (HashMap<Integer, Long>) valueInObject.get("monthSEData");
			HashMap<Integer, Long> allOPenSEData = (HashMap<Integer, Long>) valueInObject.get("allOPenSEData");

			HashMap<Integer, Long> serviceReminder = (HashMap<Integer, Long>) valueInObject.get("serviceReminder");
			HashMap<Integer, Long> serviceReminderOD = (HashMap<Integer, Long>) valueInObject.get("serviceReminderOD");
			HashMap<Integer, Long> serviceReminder7OD = (HashMap<Integer, Long>) valueInObject
					.get("serviceReminder7OD");
			HashMap<Integer, Long> serviceReminder15OD = (HashMap<Integer, Long>) valueInObject
					.get("serviceReminder15OD");
			HashMap<Integer, Long> serviceReminder30OD = (HashMap<Integer, Long>) valueInObject
					.get("serviceReminder30OD");

			HashMap<Integer, Long> workOrderCounts = (HashMap<Integer, Long>) valueInObject.get("workOrderCounts");
			HashMap<Integer, Long> AllOpenworkOrder = (HashMap<Integer, Long>) valueInObject.get("AllOpenworkOrder");
			HashMap<Integer, Long> ONhOLDWO = (HashMap<Integer, Long>) valueInObject.get("ONhOLDWO");
			HashMap<Integer, Long> completedWO = (HashMap<Integer, Long>) valueInObject.get("completedWO");
			HashMap<Integer, Long> woOpenFrom7Days = (HashMap<Integer, Long>) valueInObject.get("woOpenFrom7Days");
			HashMap<Integer, Long> woOpenFrom15Days = (HashMap<Integer, Long>) valueInObject.get("woOpenFrom15Days");
			HashMap<Integer, Long> woOpenFrom30Days = (HashMap<Integer, Long>) valueInObject.get("woOpenFrom30Days");

			HashMap<Integer, Long> issuesCreatedHM = (HashMap<Integer, Long>) valueInObject.get("issuesCreatedHM");
			HashMap<Integer, Long> issuesResolvedHM = (HashMap<Integer, Long>) valueInObject.get("issuesResolvedHM");
			HashMap<Integer, Long> issuesAllOpenHM = (HashMap<Integer, Long>) valueInObject.get("issuesAllOpenHM");
			HashMap<Integer, Long> issuesOpen7DaysHM = (HashMap<Integer, Long>) valueInObject.get("issuesOpen7DaysHM");
			HashMap<Integer, Long> issuesOpen15DaysHM = (HashMap<Integer, Long>) valueInObject
					.get("issuesOpen15DaysHM");
			HashMap<Integer, Long> issuesOpen30DaysHM = (HashMap<Integer, Long>) valueInObject
					.get("issuesOpen30DaysHM");

			HashMap<Integer, Long> vehicleCountHM = (HashMap<Integer, Long>) valueInObject.get("vehicleCountHM");
			HashMap<Integer, Long> tripCountHM = (HashMap<Integer, Long>) valueInObject.get("tripCountHM");
			HashMap<Integer, Long> fuelCountHM = (HashMap<Integer, Long>) valueInObject.get("fuelCountHM");
			HashMap<Integer, Long> ureaCountHM = (HashMap<Integer, Long>) valueInObject.get("ureaCountHM");

			HashMap<Integer, Long> renewalCounts = (HashMap<Integer, Long>) valueInObject.get("renewalCounts");
			HashMap<Integer, Long> renewalOdCounts = (HashMap<Integer, Long>) valueInObject.get("renewalOdCounts");
			HashMap<Integer, Long> renewalOd7Counts = (HashMap<Integer, Long>) valueInObject.get("renewalOd7Counts");
			HashMap<Integer, Long> renewalOd15Counts = (HashMap<Integer, Long>) valueInObject.get("renewalOd15Counts");
			HashMap<Integer, Long> renewalOd15PlusCounts = (HashMap<Integer, Long>) valueInObject
					.get("renewalOd15PlusCounts");

			highyestTripDetail = (TripSheetDto) valueInObject.get("highyestTripDetail");

			Long completese = (long) 0;
			Long createdSe = (long) 0;
			Long allopenSe = (long) 0;
			Long sevenDaysSe = (long) 0;
			Long fifteenDaysSe = (long) 0;
			Long monthSe = (long) 0;

			Long createdSr = (long) 0;
			Long allOverDueSR = (long) 0;
			Long allOverDue7SR = (long) 0;
			Long allOverDue15SR = (long) 0;
			Long allOverDue30SR = (long) 0;

			Long woCount = (long) 0;
			Long woOpen = (long) 0;
			Long wocompleted = (long) 0;
			Long woOnHold = (long) 0;
			Long woOpen7Days = (long) 0;
			Long woOpen15Days = (long) 0;
			Long woOpen30Days = (long) 0;

			long issuesCreated = 0;
			long issuesResolved = 0;
			long issuesOpen = 0;
			long issues7Open = 0;
			long issues15Open = 0;
			long issues30Open = 0;

			long activeVehicles = 0;
			long tripsheetCount = 0;
			long fuelEntriesCount = 0;
			long ureaEntriesCount = 0;

			long noOfRenewal = 0;
			long noOfOdRenewal = 0;
			long noOfOd7Renewal = 0;
			long noOfOd15Renewal = 0;
			long noOf15PlusRenewal = 0;

			String highTripNumber = "";
			String highVehicle = "";
			if (highyestTripDetail != null) {
				highTripNumber = "TS-" + highyestTripDetail.getTripSheetNumber() + "";
				if (highyestTripDetail.getVehicle_registration() != null)
					highVehicle = highyestTripDetail.getVehicle_registration();
			} else {
				highTripNumber = "";
				highVehicle = "";
			}

			if (seCompleteCount != null && seCompleteCount.get(renewalMailConfiguration.getCompanyId()) != null)
				completese = seCompleteCount.get(renewalMailConfiguration.getCompanyId());

			if (serviceEntriesCounts != null
					&& serviceEntriesCounts.get(renewalMailConfiguration.getCompanyId()) != null)
				createdSe = serviceEntriesCounts.get(renewalMailConfiguration.getCompanyId());

			if (allOPenSEData != null && allOPenSEData.get(renewalMailConfiguration.getCompanyId()) != null)
				allopenSe = allOPenSEData.get(renewalMailConfiguration.getCompanyId());

			if (sevenDaysSEData != null && sevenDaysSEData.get(renewalMailConfiguration.getCompanyId()) != null)
				sevenDaysSe = sevenDaysSEData.get(renewalMailConfiguration.getCompanyId());

			if (fifteenDaysSEData != null && fifteenDaysSEData.get(renewalMailConfiguration.getCompanyId()) != null)
				fifteenDaysSe = fifteenDaysSEData.get(renewalMailConfiguration.getCompanyId());

			if (monthSEData != null && monthSEData.get(renewalMailConfiguration.getCompanyId()) != null)
				monthSe = monthSEData.get(renewalMailConfiguration.getCompanyId());

			if (serviceReminder != null && serviceReminder.get(renewalMailConfiguration.getCompanyId()) != null)
				createdSr = serviceReminder.get(renewalMailConfiguration.getCompanyId());

			if (serviceReminderOD != null && serviceReminderOD.get(renewalMailConfiguration.getCompanyId()) != null)
				allOverDueSR = serviceReminderOD.get(renewalMailConfiguration.getCompanyId());

			if (serviceReminder7OD != null && serviceReminder7OD.get(renewalMailConfiguration.getCompanyId()) != null)
				allOverDue7SR = serviceReminder7OD.get(renewalMailConfiguration.getCompanyId());

			if (serviceReminder15OD != null && serviceReminder15OD.get(renewalMailConfiguration.getCompanyId()) != null)
				allOverDue15SR = serviceReminder15OD.get(renewalMailConfiguration.getCompanyId());

			if (serviceReminder30OD != null && serviceReminder30OD.get(renewalMailConfiguration.getCompanyId()) != null)
				allOverDue30SR = serviceReminder30OD.get(renewalMailConfiguration.getCompanyId());

			if (renewalCounts != null && renewalCounts.get(renewalMailConfiguration.getCompanyId()) != null)
				noOfRenewal = renewalCounts.get(renewalMailConfiguration.getCompanyId());

			if (renewalOdCounts != null && renewalOdCounts.get(renewalMailConfiguration.getCompanyId()) != null)
				noOfOdRenewal = renewalOdCounts.get(renewalMailConfiguration.getCompanyId());

			if (renewalOd7Counts != null && renewalOd7Counts.get(renewalMailConfiguration.getCompanyId()) != null)
				noOfOd7Renewal = renewalOd7Counts.get(renewalMailConfiguration.getCompanyId());

			if (renewalOd15Counts != null && renewalOd15Counts.get(renewalMailConfiguration.getCompanyId()) != null)
				noOfOd15Renewal = renewalOd15Counts.get(renewalMailConfiguration.getCompanyId());

			if (renewalOd15PlusCounts != null
					&& renewalOd15PlusCounts.get(renewalMailConfiguration.getCompanyId()) != null)
				noOf15PlusRenewal = renewalOd15PlusCounts.get(renewalMailConfiguration.getCompanyId());

			if (workOrderCounts != null && workOrderCounts.get(renewalMailConfiguration.getCompanyId()) != null)
				woCount = workOrderCounts.get(renewalMailConfiguration.getCompanyId());

			if (AllOpenworkOrder != null && AllOpenworkOrder.get(renewalMailConfiguration.getCompanyId()) != null)
				woOpen = AllOpenworkOrder.get(renewalMailConfiguration.getCompanyId());

			if (ONhOLDWO != null && ONhOLDWO.get(renewalMailConfiguration.getCompanyId()) != null)
				woOnHold = ONhOLDWO.get(renewalMailConfiguration.getCompanyId());

			if (completedWO != null && completedWO.get(renewalMailConfiguration.getCompanyId()) != null)
				wocompleted = completedWO.get(renewalMailConfiguration.getCompanyId());

			if (woOpenFrom7Days != null && woOpenFrom7Days.get(renewalMailConfiguration.getCompanyId()) != null)
				woOpen7Days = woOpenFrom7Days.get(renewalMailConfiguration.getCompanyId());

			if (woOpenFrom15Days != null && woOpenFrom15Days.get(renewalMailConfiguration.getCompanyId()) != null)
				woOpen15Days = woOpenFrom15Days.get(renewalMailConfiguration.getCompanyId());

			if (woOpenFrom30Days != null && woOpenFrom30Days.get(renewalMailConfiguration.getCompanyId()) != null)
				woOpen30Days = woOpenFrom30Days.get(renewalMailConfiguration.getCompanyId());

			if (issuesCreatedHM != null && issuesCreatedHM.get(renewalMailConfiguration.getCompanyId()) != null)
				issuesCreated = issuesCreatedHM.get(renewalMailConfiguration.getCompanyId());

			if (issuesResolvedHM != null && issuesResolvedHM.get(renewalMailConfiguration.getCompanyId()) != null)
				issuesResolved = issuesResolvedHM.get(renewalMailConfiguration.getCompanyId());

			if (issuesAllOpenHM != null && issuesAllOpenHM.get(renewalMailConfiguration.getCompanyId()) != null)
				issuesOpen = issuesAllOpenHM.get(renewalMailConfiguration.getCompanyId());

			if (issuesOpen7DaysHM != null && issuesOpen7DaysHM.get(renewalMailConfiguration.getCompanyId()) != null)
				issues7Open = issuesOpen7DaysHM.get(renewalMailConfiguration.getCompanyId());

			if (issuesOpen15DaysHM != null && issuesOpen15DaysHM.get(renewalMailConfiguration.getCompanyId()) != null)
				issues15Open = issuesOpen15DaysHM.get(renewalMailConfiguration.getCompanyId());

			if (issuesOpen30DaysHM != null && issuesOpen30DaysHM.get(renewalMailConfiguration.getCompanyId()) != null)
				issues30Open = issuesOpen30DaysHM.get(renewalMailConfiguration.getCompanyId());

			if (renewalCounts != null && renewalCounts.get(renewalMailConfiguration.getCompanyId()) != null)
				noOfRenewal = renewalCounts.get(renewalMailConfiguration.getCompanyId());

			if (vehicleCountHM != null)
				activeVehicles = vehicleCountHM.get(renewalMailConfiguration.getCompanyId());

			if (tripCountHM != null && tripCountHM.get(renewalMailConfiguration.getCompanyId()) != null)
				tripsheetCount = tripCountHM.get(renewalMailConfiguration.getCompanyId());

			if (fuelCountHM != null && fuelCountHM.get(renewalMailConfiguration.getCompanyId()) != null)
				fuelEntriesCount = fuelCountHM.get(renewalMailConfiguration.getCompanyId());
			if (ureaCountHM != null && ureaCountHM.get(renewalMailConfiguration.getCompanyId()) != null)
				ureaEntriesCount = ureaCountHM.get(renewalMailConfiguration.getCompanyId());

			Query queryt = entityManager.createQuery("SELECT c.company_name, c.company_id" + " FROM Company as c"
					+ " WHERE c.company_id=" + renewalMailConfiguration.getCompanyId() + " ");
			Object[] result2 = null;
			try {
				result2 = (Object[]) queryt.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			CompanyDto list2 = null;
			if (result2 != null) {
				list2 = new CompanyDto();
				list2.setCompany_name((String) result2[0]);
				list2.setCompany_id((Integer) result2[1]);
			}

			String fleetop = new String("https://www.fleetop.in/login.html");
			String srs = new String("https://srstravels.xyz/login.html");

			String finalcompanygroup = "";

			Integer store = (renewalMailConfiguration.getCompanyId());
			if (store == 6) {
				finalcompanygroup = srs;
			} else {
				finalcompanygroup = fleetop;
			}

			final String flavourOneMessage = "<html>" + "<head>" + "<style>"
					+ " .m_341222959575819737m_7686097641867126553content {" + " background-color: #e8e8e8;"
					+ " color: #333;" + " font-family: Arial,\" Helvetica Neue\",Helvetica,sans-serif;"
					+ " font-size: 14px;" + " width: 100%;" + " height: 100%;" + " max-width: 900px;"
					+ "  display: block;" + "}" + " </style>" + "</head>" + "<body>"
					+ "<table bgcolor=\"#e8e8e8\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0 auto;padding:0;background-color:#e8e8e8;color:#333;font-family:Arial,&quot; Helvetica Neue&quot;,Helvetica,sans-serif;font-size:14px;width:100%;height:100%;max-width:900px;display:block;\"> "
					+ "<tbody>" + "	<tr style=\"padding:0;margin:0\">\n"
					+ "            <td style=\"padding:10px;background-color:#e8e8e8;display:block!important;max-width:900px!important;margin:0 auto!important;clear:both!important;\">\n"
					+ "               <p style=\"text-align: center;font-size: 20px;font-weight: bold;\">Fleetop<img src=\"https://fleetop.in/resources/QhyvOb0m3EjE7A4/images/plus.png\" alt =\"Plus\"> Daily Updates<p>  \n"
					+ "            </td>\n" + "        </tr>" + "<tr style=\"padding:0;margin:0\">"
					+ " <td style=\"padding:10px;background-color:#f8f8f8;width:900px;display:block!important;max-width:900px!important;margin:0 auto!important;clear:both!important\">"
					+ "  <div  style=\"padding:0;max-width:600px;margin:0 auto;display:block\">"
					+ " <p>Following is your fleetop daily work statistics for " + emailDate + ". </p>" + "</tr>"
					+ "<tr>" + "<td>" + "</td>" + "</tr><br/><br/>" + "<tr>" + "<td align=\"center\">"
					+ "<table bgcolor=\"#ed9ae5\"  width=\"700px\" align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "	<thead>\n  <tr><td style=\"text-align: center;\" colspan=\"6\"><b>Fuel/Urea Entries Details</b></td></tr> "
					+ "    	        <tr>\n" + "      			 <th>Active Vehicles</th>\n"
					+ "      			 <th>TripSheet Count</th>\n"
					+ "                   <th>Fuel Entries Count</th>\n <th>Urea Entry Count</th>\n" + "    	        </tr>\n" + "  	</thead>\n"
					+ "    \n" + "	<tbody>\n" + "   			<tr>\n"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ activeVehicles + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ tripsheetCount + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ fuelEntriesCount + "</a></div></td> " 
					+" 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ ureaEntriesCount + "</a></div></td> " 
					+ "            	</tr>\n" + "  	</tbody>\n" + "\n" + "\n"
					+ "</table>\n" + "</td>" + "</tr><br/>" + "<tr>" + "<td align=\"center\">"
					+ "<table bgcolor=\"#7af593\" width=\"700px\" align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "	<thead>\n  <tr><td style=\"text-align: center;\" colspan=\"7\"><b>WorkOrder Details</b></td></tr> "
					+ "    	        <tr>\n" + "      				<th>Created</th>\n"
					+ "               		<th>Completed</th>\n" + "               		<th>On Hold</th>\n"
					+ "      				<th>All Open</th>\n" + "    	        </tr>\n" + "  	</thead>\n"
					+ "    \n" + "	<tbody>\n" + "   			<tr>\n"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">" + woCount
					+ "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ wocompleted + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">" + woOnHold
					+ "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">" + woOpen
					+ "</a></div>"
					+ "					<table bgcolor=\"cyan\"  width=\" 100%;\" align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "					<tr>" + "                   <th>0 to 7 Days</th>\n"
					+ "                   <th>8 to 15 Days</th>\n" + "                   <th>15+ Days</th>\n"
					+ "					</tr>" + "					<tr>"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#FF9900\" rel=\"noreferrer\">"
					+ woOpen7Days + "</a></div></td>"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#CC66FF\" rel=\"noreferrer\">"
					+ woOpen15Days + "</a></div></td>"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#ff3300\" rel=\"noreferrer\">"
					+ woOpen30Days + "</a></div></td> " + "					</tr>" + "					</table>"
					+ "					</td> " + "            	</tr>\n" + "  	</tbody>\n" + "\n" + "\n" + "</table>\n"
					+ "</td>" + "</tr><br/>" + "<tr>" + "<td align=\"center\">"
					+ "<table bgcolor=\"cyan\"  width=\"700px\" align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "	<thead>\n  <tr><td style=\"text-align: center;\" colspan=\"6\"><b>Service Entries Details</b></td></tr> "
					+ "    	        <tr>\n" + "      				<th>Created</th>\n"
					+ "               		<th>Completed</th>\n" + "      				<th>All Open</th>\n"
					+ "    	        </tr>\n" + "  	</thead>\n" + "    \n" + "	<tbody>\n" + "   			<tr>\n"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ createdSe + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ completese + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ allopenSe + "</a></div>"
					+ "						<table bgcolor=\"cyan\"  width=\" 100%;\" align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "						<tr>" + "                   	<th>0 to 7 Days</th>\n"
					+ "                   	<th>8 to 15 Days</th>\n" + "                   	<th>15+ Days</th>\n"
					+ "						</tr>" + "						<tr>"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#FF9900\" rel=\"noreferrer\">"
					+ sevenDaysSe + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#CC66FF\" rel=\"noreferrer\">"
					+ fifteenDaysSe + "</a></div></td>"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#ff3300\" rel=\"noreferrer\">" + monthSe
					+ "</a></div></td>" + "						</tr>" + "						</table>"
					+ "    					</td> " + "            	</tr>\n" + "  	</tbody>\n" + "\n" + "\n"
					+ "</table>\n" + "</td>" + "</tr><br/>" + "<tr>" + "<td align=\"center\" >"
					+ "<table bgcolor=\"#b996e3\"  width=\"700px\" align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "	<thead>\n" + "    	        <tr>\n" + "      				<th>TripSheet Created</th>\n"
					+ "      				<th>TripSheet Closed</th>\n"
					+ "               		<th>Open TripSheet</th>\n"
					+ "                   <th>Missed Closing TripSheet</th>\n"
					+ "                   <th>Oldest Open TripSheet</th>\n" + "    	        </tr>\n" + "  	</thead>\n"
					+ "    \n" + "	<tbody>\n" + "   			<tr>\n"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ tripsheetCount + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ tripSheetCountsClosed + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ tripSheetCountsInOpenState + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ tripSheetCountsMissedClosing + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ tripSheetNumber + "<br>" + dayDiff + "</a></div></td>" + "            	</tr>\n"
					+ "  	</tbody>\n" + "\n" + "\n" + "</table>\n" + "</td>" + "</tr><br/>" + "<tr>"
					+ "<td align=\"center\" k >"
					+ "<table bgcolor=\"#cee0d2\" width=\"700px\" align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "	<thead>\n  <tr><td style=\"text-align: center;\" colspan=\"6\"><b>Issues Details</b></td></tr> "
					+ "    	        <tr>\n" + "      				<th>Created</th>\n"
					+ "      				<th>Resolved</th>\n" + "      				<th>Open</th>\n"
					+ "    	        </tr>\n" + "  	</thead>\n" + "    \n" + "	<tbody>\n" + "   			<tr>\n"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ issuesCreated + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ issuesResolved + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ issuesOpen + "</a></div>"
					+ "					<table bgcolor=\"cyan\"  width=\" 100%;\" align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "					<tr>" + "                   <th>0 to 7 Days</th>\n"
					+ "                   <th>8 to 15 Days</th>\n" + "                   <th>15+ Days</th>\n"
					+ "					</tr>" + "					<tr>"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#FF9900\" rel=\"noreferrer\">"
					+ issues7Open + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#CC66FF\" rel=\"noreferrer\">"
					+ issues15Open + "</a></div></td>"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#ff3300\" rel=\"noreferrer\">"
					+ issues30Open + "</a></div></td> " + "					</tr>" + "					</table>"
					+ "					</td> " + "            	</tr>\n" + "  	</tbody>\n" + "\n" + "\n" + "</table>\n"
					+ "</td>" + "</tr><br/>" + "<tr>" + "<td align=\"center\">"
					+ "<table bgcolor=\"#bdeb34\" width=\"700px\" align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "	<thead>\n  <tr><td style=\"text-align: center;\" colspan=\"6\"><b>Renewal Reminder Details</b></td></tr> "
					+ "    	        <tr>\n" + "      			 <th>Created</th>\n"
					+ "      			 <th >OverDue</th>\n" + "    	        </tr>\n" + "  	</thead>\n" + "    \n"
					+ "	<tbody>\n" + "   			<tr>\n"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ noOfRenewal + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ noOfOdRenewal + "</a></div> "
					+ "						<table bgcolor=\"cyan\"  width=\" 100%;\" align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "					<tr>" + "                   <th>0 to 7 Days</th>\n"
					+ "                   <th>8 to 15 Days</th>\n" + "                   <th>15+ Days</th>\n"
					+ "					</tr>" + "					<tr>"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#FF9900\" rel=\"noreferrer\">"
					+ noOfOd7Renewal + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#CC66FF\" rel=\"noreferrer\">"
					+ noOfOd15Renewal + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#ff3300\" rel=\"noreferrer\">"
					+ noOf15PlusRenewal + "</a></div></td>" + "					</tr>" + "					</table>"
					+ " 					</td> " + "            	</tr>\n" + "  	</tbody>\n" + "\n" + "\n"
					+ "</table>\n" + "</td>" + "</tr><br/>" + "<tr>" + "<td align=\"center\" >"
					+ "<table bgcolor=\"pink\" width=\"700px\" align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "	<thead>\n  <tr><td style=\"text-align: center;\" colspan=\"6\"><b>Service Reminder Details</b></td></tr> "
					+ "    	        <tr>\n" + "      				<th>Created</th>\n"
					+ "      				<th>OverDue</th>\n" + "    	        </tr>\n" + "  	</thead>\n" + "    \n"
					+ "	<tbody>\n" + "   			<tr>\n"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ createdSr + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ allOverDueSR + "</a></div>"
					+ "						<table bgcolor=\"cyan\"  width=\" 100%;\" align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "						<tr>" + "                   	<th>0 to 7 Days</th>\n"
					+ "                   	<th>8 to 15 Days</th>\n" + "                   	<th>15+ Days</th>\n"
					+ "						</tr>" + "						<tr>"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#FF9900\" rel=\"noreferrer\">"
					+ allOverDue7SR + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#CC66FF\" rel=\"noreferrer\">"
					+ allOverDue15SR + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#ff3300\" rel=\"noreferrer\">"
					+ allOverDue30SR + "</a></div></td>" + "						</tr>"
					+ "						</table>" + "						</td> " + "            	</tr>\n"
					+ "  	</tbody>\n" + "\n" + "\n" + "</table>\n" + "</td>" + "</tr><br/>" + "<tr>"
					+ "<td align=\"center\" >"
					+ "<table bgcolor=\"#f5ed5f\" width=\"700px\" align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "	<thead>\n  <tr><td style=\"text-align: center;\" colspan=\"6\"><b>Miscellaneous</b></td></tr> "
					+ "    	        <tr>\n" + "      				<th>Total Run Count(IN KM)</th>\n"
					+ "      				<th>Highest Run Vehicle / TripSheet No </th>\n" + "    	        </tr>\n"
					+ "  	</thead>\n" + "    \n" + "	<tbody>\n" + "   			<tr>\n"
					+ " 					<td align='center'><div style=\"font-size:35px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">" + usesKM
					+ "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:35px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#000000\" rel=\"noreferrer\">"
					+ highVehicle + " <br>" + highTripNumber + "</a></div></td> " + "            	</tr>\n"
					+ "  	</tbody>\n" + "\n" + "\n" + "</table>\n" + "</td>" + "</tr><br/>" + "<br/>"
					+ "</tbody></table>" + "<p>For more updates, login to <a href=" + finalcompanygroup
					+ " style=\\\"text-decoration:none;color:#ed5949\\\" rel=\\\"noreferrer\\\" target=\\\"_blank\\\" data-saferedirecturl=\\\"https://www.google.com/url?q=http://"
					+ finalcompanygroup
					+ "/?OVC%3DGGEI&amp;source=gmail&amp;ust=1557550993110000&amp;usg=AFQjCNH9NaK8MRR4b77DXYYbdjvwvANpYA\\\">Fleetop</a>.</p>"
					+ "</body>" + "</html>";

			final String withoutFlavorMessage = "<html>" + "<head>" + "<style>"
					+ " .m_341222959575819737m_7686097641867126553content {" + " background-color: #e8e8e8;"
					+ " color: #333;" + " font-family: Arial,\" Helvetica Neue\",Helvetica,sans-serif;"
					+ " font-size: 14px;" + " width: 100%;" + " height: 100%;" + " max-width: 900px;"
					+ "  display: block;" + "}" + " </style>" + "</head>" + "<body>"
					+ "<table bgcolor=\"#e8e8e8\" cellpadding=\"0\" cellspacing=\"0\" style=\"margin:0 auto;padding:0;background-color:#e8e8e8;color:#333;font-family:Arial,&quot; Helvetica Neue&quot;,Helvetica,sans-serif;font-size:14px;width:100%;height:100%;max-width:900px;display:block;\"> "
					+ "<tbody>" + "	<tr style=\"padding:0;margin:0\">\n"
					+ "            <td style=\"padding:10px;background-color:#e8e8e8;display:block!important;max-width:900px!important;margin:0 auto!important;clear:both!important;\">\n"
					+ "               <p style=\"text-align: center;font-size: 20px;font-weight: bold;\">Fleetop<img src=\"https://fleetop.in/resources/QhyvOb0m3EjE7A4/images/plus.png\" alt =\"Plus\"> Daily Updates<p>  \n"
					+ "            </td>\n" + "        </tr>" + "<tr style=\"padding:0;margin:0\">"
					+ " <td style=\"padding:10px;background-color:#f8f8f8;width:600px;display:block!important;max-width:900px!important;margin:0 auto!important;clear:both!important\">"
					+ "  <div  style=\"padding:0;max-width:900px;margin:0 auto;display:block\">"
					+ " <p>Following is your fleetop daily work statistics for " + emailDate + ". </p>"

					+ " <div  style=\"height:100px;background-color:#fff;text-align:center;margin-bottom:10px;color:#425a69\">"
					+ "   <div  style=\"padding:10px 0\">Service Entries</div> "
					+ " <div  style=\"font-size:42px\"><a href=" + finalcompanygroup
					+ " style=\"text-decoration:none;color:#ed5949\" rel=\"noreferrer\"> " + serviceEntriesCounts
					+ "</a></div>" + " </div>"
					+ "<div  style=\"height:100px;background-color:#fff;text-align:center;margin-bottom:10px;color:#425a69\">"
					+ "<div  style=\"padding:10px 0\">Work Orders</div>" + "<div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#ed5949\" rel=\"noreferrer\"> "
					+ workOrderCounts + "</a></div>" + " </div>"
					+ " <div  style=\"height:100px;background-color:#fff;text-align:center;margin-bottom:10px;color:#425a69\">"
					+ " <div  style=\"padding:10px 0\">Fuel Entries</div>" + "  <div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#ed5949\" rel=\"noreferrer\"> "
					+ fuelEntriesCounts + "</a></div>" + " </div>"
					+ " <div style=\"height:100px;background-color:#fff;text-align:center;margin-bottom:10px;color:#425a69\">"
					+ "   <div  style=\"padding:10px 0\">Renewal</div>" + "   <div  style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#ed5949\" rel=\"noreferrer\"> "
					+ renewalCounts + "</a></div>" + " </div>"
					+ " <div  style=\"height:100px;background-color:#fff;text-align:center;margin-bottom:10px;color:#425a69\">"
					+ "  <div  style=\"padding:10px 0\">Renewal Overdue</div>"
					+ "    <div  style=\"font-size:42px\"><a href=" + finalcompanygroup
					+ " style=\"text-decoration:none;color:#ed5949\" rel=\"noreferrer\"> "
					+ renewalReminderOverDueCounts + "</a></div>" + "  </div>"
					+ " <div  style=\"height:100px;background-color:#fff;text-align:center;margin-bottom:10px;color:#425a69\">"
					+ "  <div  style=\"padding:10px 0\">Service Reminder</div>"
					+ "    <div  style=\"font-size:42px\"><a href=" + finalcompanygroup
					+ " style=\"text-decoration:none;color:#ed5949\" rel=\"noreferrer\"> " + serviceReminderCounts
					+ "</a></div>" + "  </div>" + " </div>" + "</td>" + "</tr>" + "<tr>" + "<td>" + "</td>" + "</tr>"
					+ "<tr>" + "<td>" + "<table align='center' border=1 style=\"border-collapse: collapse;\" >\n"
					+ "	<thead>\n" + "    	        <tr>\n" + "      				<th>TripSheet Created</th>\n"
					+ "      				<th>TripSheet Closed</th>\n" + "    	        </tr>\n" + "  	</thead>\n"
					+ "    \n" + "	<tbody>\n" + "   			<tr>\n"
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#ed5949\" rel=\"noreferrer\">"
					+ tripsheetCount + "</a></div></td> "
					+ " 					<td align='center'><div style=\"font-size:42px\"><a href="
					+ finalcompanygroup + " style=\"text-decoration:none;color:#ed5949\" rel=\"noreferrer\">"
					+ tripSheetCountsClosed + "</a></div></td> " + "            	</tr>\n" + "  	</tbody>\n" + "\n"
					+ "\n" + "</table>\n" + "</td>" + "</tr>" + "</tbody></table>"
					+ "<p>For more updates, login to <a href=" + finalcompanygroup
					+ " style=\\\"text-decoration:none;color:#ed5949\\\" rel=\\\"noreferrer\\\" target=\\\"_blank\\\" data-saferedirecturl=\\\"https://www.google.com/url?q=http://"
					+ finalcompanygroup
					+ "/?OVC%3DGGEI&amp;source=gmail&amp;ust=1557550993110000&amp;usg=AFQjCNH9NaK8MRR4b77DXYYbdjvwvANpYA\\\">Fleetop</a>.</p>"
					+ "</body>" + "</html>";

			valueOutObject = new ValueObject();
			valueOutObject.put("flavourOneMessage", flavourOneMessage);
			valueOutObject.put("withoutFlavorMessage", withoutFlavorMessage);

			return valueOutObject;

		} catch (Exception e) {
			return null;
			// throw e;
		} finally {
			highyestTripDetail = null;
			valueOutObject = null;
		}

	}

	// Email System for Manager Start Renewal Reminder
	@Override
	public List<CompanyDto> getALLEmailRenewalRemindersDailyWorkForManagers(String startDate, String endDate)
			throws Exception {

		try {
			TypedQuery<Object[]> typedQuery = null;

			typedQuery = entityManager.createQuery("SELECT COUNT(R.renewal_id), C.company_id, C.company_name "
					+ " From RenewalReminder as R " + " INNER JOIN Company AS C on C.company_id = R.companyId  "
					+ " WHERE R.created between '" + startDate + "' AND '" + endDate + "'  AND R.markForDelete = 0 "
					+ " Group by R.companyId ", Object[].class);

			List<Object[]> results = typedQuery.getResultList();

			List<CompanyDto> renewalReminder = null;
			if (results != null && !results.isEmpty()) {

				renewalReminder = new ArrayList<CompanyDto>();
				CompanyDto list = null;
				for (Object[] result : results) {
					list = new CompanyDto();
					if (result != null) {

						list.setTripSheetCount((long) 0);
						list.setServiceEntriesCount((long) 0);
						list.setServiceReminderCount((long) 0);
						list.setFuelEntriesCount((long) 0);
						list.setWorkOrderCount((long) 0);

						if (result[0] != null)
							list.setRenewalReminderCount((long) result[0]);
						list.setCompany_id((int) result[1]);
						list.setCompany_name((String) result[2]);
					}
					renewalReminder.add(list);
				}
			}
			return renewalReminder;
		} catch (Exception e) {
			throw e;
		}
	}
	// Email System for Manager Stop Renewal Reminder

	@Override
	public ValueObject getRenewalExpiryDataReport(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		String renewalSubType = null;
		String toDate = null;
		ValueObject tableConfig = null;
		String toSqlDate = null;

		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			renewalSubType = valueObject.getString("renewalSubType");
			toDate = valueObject.getString("renewalToDate");

			if (toDate != null) {
				java.util.Date date = dateFormat.parse(toDate);
				toSqlDate = new java.sql.Date(date.getTime()) + DateTimeUtility.DAY_END_TIME;

				tableConfig = new ValueObject();

				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH,
						ModuleFilePathConstant.RENEWAL_REMINDER_EXPIRY_REPORT);

				tableConfig = JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig = JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

			}

			valueObject.put("renewalList",
					getRenewalReminderDataOnToDate(toSqlDate, userDetails.getCompany_id(), renewalSubType));
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",
					userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			tableConfig = null;
			userDetails = null;
			toDate = null;
		}
	}

	private List<RenewalReminderDto> getRenewalReminderDataOnToDate(String toSqlDate, Integer companyId,
			String renewalTypes) throws Exception {
		List<RenewalReminderDto> reminderDtos = null;
		try {
			TypedQuery<Object[]> queryt = null;

			queryt = entityManager.createQuery(
					"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
							+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
							+ ", R.renewal_R_Number, VG.vGroup FROM RenewalReminder AS R"
							+ " INNER JOIN Vehicle V ON V.vid = R.vid "
							+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
							+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
							+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
							+ " WHERE R.companyId =" + companyId + " AND RST.renewal_Subid IN (" + renewalTypes
							+ ") AND renewal_to = '" + toSqlDate
							+ "' AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
					Object[].class);
			
			List<Object[]> results = null;
			try {
				results = queryt.getResultList();
				
			} catch (NoResultException nre) {
				
			}
			

			if (results != null && !results.isEmpty()) {
				reminderDtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();

					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_from_Date((Date) result[5]);
					renewal.setRenewal_To_Date((Date) result[6]);
					if (result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_staus_id((short) result[9]);
					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
					renewal.setVehicleGroup((String) result[14]);

					if (renewal.getRenewal_from_Date() != null)
						renewal.setRenewal_from(dateFormat.format(renewal.getRenewal_from_Date()));
					if (renewal.getRenewal_To_Date() != null)
						renewal.setRenewal_to(dateFormat.format(renewal.getRenewal_To_Date()));

					reminderDtos.add(renewal);
				}
			}
			return reminderDtos;

		} catch (Exception e) {
			throw e;
		} finally {
			reminderDtos = null;
		}
	}

	private List<RenewalReminderDto> getRenewalReminderOverDueList(String query, CustomUserDetails userDetails)
			throws Exception {
		List<RenewalReminderDto> reminderDtos = null;
		try {
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, VG.vGroup FROM RenewalReminder AS R"
								+ "	INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> "
								+ VehicleStatusConstant.VEHICLE_STATUS_SOLD + ""
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " WHERE R.renewal_to < '" + DateTimeUtility.getCurrentDate() + "' AND R.companyId ="
								+ userDetails.getCompany_id() + " " + query
								+ " AND R.markForDelete = 0 AND R.ignored = 0 AND R.newRRCreated = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			} else {

				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, VG.vGroup FROM RenewalReminder AS R"
								+ "	INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> "
								+ VehicleStatusConstant.VEHICLE_STATUS_SOLD + ""
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " WHERE R.renewal_to < '" + DateTimeUtility.getCurrentDate() + "' AND R.companyId ="
								+ userDetails.getCompany_id() + " " + query
								+ " AND R.markForDelete = 0 AND R.ignored = 0 AND R.newRRCreated = 0 ORDER BY R.renewal_id desc",
						Object[].class);

			}
			queryt.setMaxResults(1000);
			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				reminderDtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();

					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_from_Date((Date) result[5]);
					renewal.setRenewal_To_Date((Date) result[6]);
					if (result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_staus_id((short) result[9]);
					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
					renewal.setVehicleGroup((String) result[14]);

					if (renewal.getRenewal_from_Date() != null)
						renewal.setRenewal_from(dateFormat.format(renewal.getRenewal_from_Date()));
					if (renewal.getRenewal_To_Date() != null)
						renewal.setRenewal_to(dateFormat.format(renewal.getRenewal_To_Date()));

					reminderDtos.add(renewal);
				}
			}
			return reminderDtos;

		} catch (Exception e) {
			throw e;
		} finally {
			reminderDtos = null;
		}
	}

	private List<RenewalReminderDto> getRenewalReminderDueSoonList(String query, CustomUserDetails userDetails)
			throws Exception {
		List<RenewalReminderDto> reminderDtos = null;
		try {
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, VG.vGroup FROM RenewalReminder AS R"
								+ "	INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> "
								+ VehicleStatusConstant.VEHICLE_STATUS_SOLD + ""
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " WHERE R.dateofRenewal <= '" + DateTimeUtility.getCurrentDate()
								+ "' AND R.renewal_to > '" + DateTimeUtility.getCurrentDate() + "' AND R.companyId ="
								+ userDetails.getCompany_id() + " " + query
								+ " AND R.markForDelete = 0 AND R.newRRCreated = 0 AND R.ignored = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			} else {
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, VG.vGroup FROM RenewalReminder AS R"
								+ "	INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> "
								+ VehicleStatusConstant.VEHICLE_STATUS_SOLD + ""
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + ""
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " WHERE R.dateofRenewal <= '" + DateTimeUtility.getCurrentDate()
								+ "' AND R.renewal_to > '" + DateTimeUtility.getCurrentDate() + "' AND R.companyId ="
								+ userDetails.getCompany_id() + " " + query
								+ " AND R.markForDelete = 0 AND R.newRRCreated = 0 AND R.ignored = 0 ORDER BY R.renewal_id desc",
						Object[].class);

			}
			queryt.setMaxResults(1000);
			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				reminderDtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();

					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_from_Date((Date) result[5]);
					renewal.setRenewal_To_Date((Date) result[6]);
					if (result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_staus_id((short) result[9]);
					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
					renewal.setVehicleGroup((String) result[14]);

					if (renewal.getRenewal_from_Date() != null)
						renewal.setRenewal_from(dateFormat.format(renewal.getRenewal_from_Date()));
					if (renewal.getRenewal_To_Date() != null)
						renewal.setRenewal_to(dateFormat.format(renewal.getRenewal_To_Date()));

					reminderDtos.add(renewal);
				}
			}
			return reminderDtos;

		} catch (Exception e) {
			throw e;
		} finally {
			reminderDtos = null;
		}
	}

	@Override
	@Transactional
	public void updateNewRRCreated(Integer vid, Integer renewalType, Integer renewalSubType) throws Exception {

		renewalReminderDao.updateNewRRCreated(vid, renewalType, renewalSubType);
	}

	@Override
	public ValueObject getTotalOverDueRenewalCount(String date, Integer companyId) throws Exception {
		ValueObject 			valueObject 	= null;
		HashMap<Short, Long> 	overDueCountHM	= null;	
	//	Query queryt = null;
		TypedQuery<Object[]> queryt = null;
		try {
			overDueCountHM	= new HashMap<>();
			valueObject 	= new ValueObject();
			queryt = entityManager.createQuery(" SELECT COUNT(RR.renewal_id), V.vStatusId  " + " From RenewalReminder as RR "
					+ " INNER JOIN Vehicle V ON V.vid = RR.vid AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
					+ " WHERE  RR.renewal_to <= '" + date +"' AND RR.companyId = " + companyId
					+ " AND RR.markForDelete = 0 AND RR.newRRCreated = 0 AND RR.ignored =0  GROUP BY V.vStatusId ",Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();
					if(result[0] != null) {
						renewal.setRenewalCount((Long) result[0]);
					}else {
						renewal.setRenewalCount(Long.parseLong("0"));
					}
					renewal.setVehicleStatusId((short) result[1]);
					
					overDueCountHM.put(renewal.getVehicleStatusId(), renewal.getRenewalCount());
					Dtos.add(renewal);
				}
			}
			valueObject.put("renewalCountGroupByVStatus", Dtos);
			valueObject.put("overDueCountHM", overDueCountHM);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public HashMap<Integer, Long> getAllOverDueRenewalReminders(String startDate) throws Exception {
		try {
			TypedQuery<Object[]> typedQuery = null;
			HashMap<Integer, Long> map = null;

			typedQuery = entityManager.createQuery(
					"SELECT COUNT(R.renewal_id), R.companyId " + " From RenewalReminder as R "
							+ " INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> 4" + " WHERE  R.renewal_to < '"
							+ startDate + "'  AND R.markForDelete = 0 AND R.newRRCreated = 0 GROUP BY R.companyId ",
					Object[].class);

			List<Object[]> results = typedQuery.getResultList();

			if (results != null && !results.isEmpty()) {

				map = new HashMap<>();

				for (Object[] result : results) {

					map.put((Integer) result[1], (Long) result[0]);

				}
			}

			return map;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Long getTotalOverDueRenewalCountBetweenDates(String startDate, String endDate, Integer companyId)
			throws Exception {

		Query queryt = null;
		queryt = entityManager.createQuery("SELECT COUNT(R.renewal_id) " + " From RenewalReminder as R "
				+ " INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> 4 " + " WHERE  R.renewal_to between '"
				+ startDate + "' AND '" + endDate + "' AND R.companyId = " + companyId + "  "
				+ " AND R.markForDelete = 0 AND R.newRRCreated = 0 ", Object[].class);

		Long count = (long) 0;
		try {

			if ((Long) queryt.getSingleResult() != null) {
				count = (Long) queryt.getSingleResult();
			}

		} catch (NoResultException nre) {
		}

		return count;
	}

	@Override
	public HashMap<Integer, Long> getAllOverDueRenewalReminders(String startDate, String endDate) throws Exception {
		try {
			TypedQuery<Object[]> typedQuery = null;
			HashMap<Integer, Long> map = null;

			typedQuery = entityManager.createQuery(
					"SELECT COUNT(R.renewal_id), R.companyId " + " From RenewalReminder as R "
							+ " INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> 4"
							+ " WHERE  R.renewal_to between '" + startDate + "' AND '" + endDate
							+ "'  AND R.markForDelete = 0 AND R.newRRCreated = 0 GROUP BY R.companyId ",
					Object[].class);

			List<Object[]> results = typedQuery.getResultList();

			if (results != null && !results.isEmpty()) {

				map = new HashMap<>();

				for (Object[] result : results) {

					map.put((Integer) result[1], (Long) result[0]);

				}
			}

			return map;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<RenewalReminderDto> getVehicleRenewalExpensesDateRange(Integer vid, String fromDate, String toDate)
			throws Exception {
		toDate =	DateTimeUtility.getEndOfDayDateStr(toDate, "yyyy-MM-dd HH:mm:ss");
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.renewal_id, R.vid, V.vehicle_registration, R.renewal_from, R.renewal_to, R.renewal_Amount, RT.renewal_Type,"
				+ " RST.renewal_SubType, R.newRRCreated, R.renewalTypeId "
						+ " FROM RenewalReminder AS R " + " INNER JOIN Vehicle V ON V.vid = R.vid "
						+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
						+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid " 
						+ " where R.vid = "+vid+" AND ( '" + fromDate + "' >= R.renewal_from AND '"+fromDate+"' <= R.renewal_to  OR R.vid=" + vid + " "
						+ " AND '" + toDate + "' >= R.renewal_from  AND  '" + toDate + "' <= R.renewal_to OR "
						+ " R.renewal_from >= '"+fromDate+"' AND R.renewal_from <= '"+toDate+"' OR "
						+ " R.renewal_to >= '"+fromDate+"' AND R.renewal_to <= '"+toDate+"' ) AND R.markForDelete = 0",
				Object[].class);

		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewal_from_Date((Date) result[3]);
				renewal.setRenewal_To_Date((Date) result[4]);
				renewal.setRenewal_Amount((Double) result[5]);
				renewal.setRenewal_type((String) result[6]);
				renewal.setRenewal_subtype((String) result[7]);
				renewal.setNewRRCreated((boolean) result[8]);
				renewal.setRenewalTypeId((Integer) result[9]);
				Dtos.add(renewal);
			}
		}
		return Dtos;

	}

	@Override
	public List<RenewalReminderDto> getAllOverDueRenewalList(Date startDate, ValueObject valueObject,
			CustomUserDetails userDetails) throws Exception {
		Integer pageNumber = valueObject.getInt("pageNumber");
		try {
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, VG.vGroup, R.newRRCreated, R.renewal_receipt,RT.allowToAvoid "
								+ " FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> 4 "+valueObject.getString("vehicleStatusQuery", "")+""
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " WHERE R.renewal_to < '" + startDate + "' AND R.companyId = "
								+ userDetails.getCompany_id()
								+ "  AND R.markForDelete = 0 AND R.ignored = 0 AND R.newRRCreated = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			} else {

				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, VG.vGroup, R.newRRCreated, R.renewal_receipt,RT.allowToAvoid "
								+ " FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> 4 "+valueObject.getString("vehicleStatusQuery", "")+""
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " WHERE R.renewal_to < '" + startDate
								+ "'  AND R.markForDelete = 0 AND R.ignored = 0 AND R.newRRCreated = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			}
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();
					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_from_Date((Date) result[5]);
					renewal.setRenewal_To_Date((Date) result[6]);
					if(result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_staus_id((short) result[9]);
					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
					renewal.setRenewalAproval_Number((Long) result[14]);
					renewal.setVehicleGroup((String) result[15]);
					renewal.setNewRRCreated((boolean) result[16]);
					renewal.setRenewal_receipt((String) result[17]);
					renewal.setAllowToIgnored((boolean) result[18]);
					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Page<RenewalReminder> getDeployment_Page_RenewalOverDue(Date startDate, ValueObject valueObject,
			CustomUserDetails userDetails) throws Exception {
		Integer pageNumber =	valueObject.getInt("pageNumber");
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		if(valueObject.getShort("vehicleStatus") == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return renewalReminderDao.getDeploymentPageInactiveVehicleRenewalOverDue(startDate, userDetails.getCompany_id(),
						request);
			} else {
				return renewalReminderDao.getDeploymentPageInactiveVehicleRenewalOverDue(startDate, userDetails.getCompany_id(),
						userDetails.getId(), request);
			}
			
		}else {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				if(valueObject.getBoolean("vehicleExcludingSurrenderAndInactive", false)) {
					return renewalReminderDao.getDeploymentPageRenewalOverDueExcludingSurrenderAndInactive(startDate, userDetails.getCompany_id(),
							request);
				}else {
					return renewalReminderDao.getDeployment_Page_RenewalOverDue(startDate, userDetails.getCompany_id(),
							request);
				}
			} else {
				if(valueObject.getBoolean("vehicleExcludingSurrenderAndInactive", false)) {
					return renewalReminderDao.getDeployment_Page_RenewalOverDue(startDate, userDetails.getCompany_id(),
							userDetails.getId(), request);
				}else {
					return renewalReminderDao.getDeploymentPageRenewalOverDueExcludingSurrenderAndInactive(startDate, userDetails.getCompany_id(),
							userDetails.getId(), request);
				}
			}
		}
		
	}

	@Override
	public Long getAllOverDueRenewalRemindersCount(Date startDate, CustomUserDetails userDetails) throws Exception {
		if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

			return renewalReminderDao.getRenewalOverDueCount(startDate, userDetails.getCompany_id());
		} else {

			return renewalReminderDao.getRenewalOverDueCount(startDate, userDetails.getCompany_id(),
					userDetails.getId());
		}
	}

	@Override
	public List<RenewalReminderDto> getAllDueSoonRenewalList(Date startDate, ValueObject valueObject,
			CustomUserDetails userDetails) throws Exception {
		Integer pageNumber=	valueObject.getInt("pageNumber");
		try {
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, VG.vGroup, R.newRRCreated, R.renewal_receipt,RT.allowToAvoid "
								+ " FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> 4 "+valueObject.getString("vehicleStatusQuery")+""
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " WHERE R.dateofRenewal <= '" + startDate + "' AND R.renewal_to > '" + startDate
								+ "' AND R.companyId = " + userDetails.getCompany_id()
								+ "  AND R.markForDelete = 0 AND R.ignored = 0 AND R.newRRCreated = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			} else {

				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, VG.vGroup, R.newRRCreated, R.renewal_receipt,RT.allowToAvoid "
								+ " FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> 4 "+valueObject.getString("vehicleStatusQuery")+""
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " WHERE R.dateofRenewal <= '" + startDate
								+ "' AND R.renewal_to > '" + startDate
								+ "'  AND R.markForDelete = 0 AND R.ignored = 0 AND R.newRRCreated = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			}
			queryt.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();
					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_from_Date((Date) result[5]);
					renewal.setRenewal_To_Date((Date) result[6]);
					if (result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_staus_id((short) result[9]);
					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
					renewal.setRenewalAproval_Number((Long) result[14]);
					renewal.setVehicleGroup((String) result[15]);
					renewal.setNewRRCreated((boolean) result[16]);
					renewal.setRenewal_receipt((String) result[17]);
					renewal.setAllowToIgnored((boolean) result[18]);
					
					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Page<RenewalReminder> getDeployment_Page_RenewalDueSoon(Date startDate, ValueObject valueObject,
			CustomUserDetails userDetails) throws Exception {
		Integer pageNumber = valueObject.getInt("pageNumber");
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		
		if(valueObject.getShort("vehicleStatus") == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE) {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {

				return renewalReminderDao.getDeploymentPageInactiveVehicleRenewalDueSoon(startDate, userDetails.getCompany_id(),
						request);
			} else {

				return renewalReminderDao.getDeploymentPageInactiveVehicleRenewalDueSoon(startDate, userDetails.getCompany_id(),
						userDetails.getId(), request);
			}
		}else {
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				if(valueObject.getBoolean("vehicleExcludingSurrenderAndInactive", false)) {
					return renewalReminderDao.getDeploymentPageRenewalDueSoonExcludingSurrenderAndInactive(startDate, userDetails.getCompany_id(),
							request);
				}else {
					return renewalReminderDao.getDeployment_Page_RenewalDueSoon(startDate, userDetails.getCompany_id(),
						request);
				}
			} else {

				if(valueObject.getBoolean("vehicleExcludingSurrenderAndInactive", false)) {
					return renewalReminderDao.getDeploymentPageRenewalDueSoonExcludingSurrenderAndInactive(startDate, userDetails.getCompany_id(),
							userDetails.getId(), request);
				}else {
					return renewalReminderDao.getDeployment_Page_RenewalDueSoon(startDate, userDetails.getCompany_id(),
							userDetails.getId(), request);
				}
				
				
			}
		}
	}

	@Override
	public long todaysTotalRenewalCount(String startDate, String endDate, int companyId) throws Exception {
		Query queryt = null;
		queryt = entityManager.createQuery(" SELECT COUNT(RR.renewal_id) " + " From RenewalReminder as RR "
				+ " INNER JOIN Vehicle AS V ON V.vid = RR.vid  AND V.vStatusId <> "
				+ VehicleStatusConstant.VEHICLE_STATUS_SOLD + " " + " WHERE RR.renewal_to between '" + startDate
				+ "' AND '" + endDate + "' AND RR.companyId = " + companyId
				+ " AND RR.markForDelete = 0 AND RR.newRRCreated = 0 ");

		Long count = (long) 0;
		try {

			if ((Long) queryt.getSingleResult() != null) {
				count = (Long) queryt.getSingleResult();
			}

		} catch (NoResultException nre) {
		}

		return count;
	}

	@Override
	public ValueObject totalDueSoonCount(String date, int companyId) throws Exception {
		ValueObject 			valueObject 	= null;
		HashMap<Short, Long> 	dueSoonCountHM	= null;	
	//	Query queryt = null;
		TypedQuery<Object[]> queryt = null;
		try {
			dueSoonCountHM	= new HashMap<>();
			valueObject 	= new ValueObject();
			queryt = entityManager.createQuery(" SELECT COUNT(RR.renewal_id), V.vStatusId  " + " From RenewalReminder as RR "
					+ " INNER JOIN Vehicle V ON V.vid = RR.vid AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD+" "
					+ " WHERE RR.dateofRenewal <= '" + date
					+ "' AND RR.renewal_to > '" + date + "' AND RR.companyId = " + companyId
					+ " AND RR.markForDelete = 0 AND RR.ignored =0 AND RR.newRRCreated = 0  GROUP BY V.vStatusId ",Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();
					if(result[0] != null) {
						renewal.setRenewalCount((Long) result[0]);
					}else {
						renewal.setRenewalCount(Long.parseLong("0"));
					}
					renewal.setVehicleStatusId((short) result[1]);
					
					dueSoonCountHM.put(renewal.getVehicleStatusId(), renewal.getRenewalCount());
					Dtos.add(renewal);
				}
			}
			valueObject.put("renewalCountGroupByVStatus", Dtos);
			valueObject.put("dueSoonCountHM", dueSoonCountHM);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getMonthlyRenwalCountAndAmount(String startDate, String endDate, int companyId) throws Exception {
		try {
			TypedQuery<Object[]> typedQuery = null;
			ValueObject 		 map 	    = null;

			typedQuery = entityManager.createQuery(
					"SELECT count(RR.renewal_id), sum(RR.renewal_Amount) "
						+ " From RenewalReminder as RR "
						+ " INNER JOIN Vehicle AS V ON V.vid = RR.vid  AND V.vStatusId <> "
						+   VehicleStatusConstant.VEHICLE_STATUS_SOLD + " "		
						+ " WHERE RR.renewal_to between '" + startDate + "' AND '" + endDate + "' "
						+ " AND RR.markForDelete = 0 AND RR.newRRCreated = 0 AND RR.ignored = 0 AND RR.companyId = "+companyId+" ", Object[].class);

			List<Object[]> results = typedQuery.getResultList();

			if (results != null && !results.isEmpty()) {

				map = new ValueObject();

				for (Object[] result : results) {

					map.put("renewalCount", (Long) result[0]);
					
					if(result[1] != null) {
						map.put("renewalAmount", (double) result[1]);
					} else {
						map.put("renewalAmount", (double) 0);
					}

				}
			}

			return map;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public long totalDueSoonCountBetweenDates(String startDate, String endDate, int companyId) throws Exception {
		Query queryt = null;
		queryt = entityManager.createQuery(" SELECT COUNT(RR.renewal_id) " + " From RenewalReminder as RR "
				+ "	INNER JOIN Vehicle V ON V.vid = RR.vid AND V.vStatusId <> "
				+ VehicleStatusConstant.VEHICLE_STATUS_SOLD + "" + " WHERE RR.dateofRenewal BETWEEN '" + startDate
				+ "' AND '" + endDate + "' " + " AND RR.renewal_to > '" + endDate + "' AND RR.companyId = " + companyId
				+ " " + " AND RR.markForDelete = 0 AND RR.newRRCreated = 0 ");

		Long count = (long) 0;
		try {

			if ((Long) queryt.getSingleResult() != null) {
				count = (Long) queryt.getSingleResult();
			}

		} catch (NoResultException nre) {
		}

		return count;
	}

	@Override
	public List<RenewalReminderDto> getRenewalReminderTableListBetweenDates(String query, Integer companyID)
			throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
			queryt = entityManager
					.createQuery(" SELECT R.renewal_id, R.renewal_R_Number, R.created, R.renewal_from, R.renewal_to, "
							+ " R.vid, V.vehicle_registration, R.renewal_staus_id, RT.renewal_Type, R.dateofRenewal, "
							+ " R.renewal_Amount,R.renewal_receipt,R.renewal_document_id,B.branch_name,VT.vtype"
							+ " ,RST.renewal_SubType From RenewalReminder AS R "
							+ " INNER JOIN Vehicle AS V ON V.vid = R.vid  AND V.vStatusId <> "+ VehicleStatusConstant.VEHICLE_STATUS_SOLD + " "
							+ " LEFT JOIN Branch B ON B.branch_id = V.branchId "
							+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId "
							+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
							+ " LEFT JOIN VehicleType VT ON VT.tid = V.vehicleTypeId "
							+ " WHERE " + query + " "
							+ "  R.companyId = " + companyID + " AND R.markForDelete = 0 AND R.newRRCreated = 0 "
							+ " ORDER BY R.renewal_id DESC ", Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();

					renewal.setRenewal_id((Long) result[0]);

					if (result[1] != null) {
						renewal.setRenewal_R_Number((Long) result[1]);
						renewal.setRenewal_number("<a href=\"showRenewalReminderDetails?renewalId="+renewal.getRenewal_id()+"\" target=\"_blank\" style=\"color:blue\">RR-"+renewal.getRenewal_R_Number() +" <a>");
					}

					if (result[2] != null)
						renewal.setCreatedOn((Date) result[2]);

					if (result[3] != null)
						renewal.setRenewal_from(dateFormat.format((Date) result[3]));
					renewal.setRenewal_D_from((Date) result[3]);

					if (result[4] != null)
						renewal.setRenewal_to(dateFormat.format((Date) result[4]));
					renewal.setRenewal_D_to((Date) result[4]);

					renewal.setVid((Integer) result[5]);

					if (result[6] != null)
						renewal.setVehicle_registration((String) result[6]);

					if (result[7] != null) {
						renewal.setRenewal_staus_id((short) result[7]);
						renewal.setRenewal_status(
								RenealReminderStatus.getRenewalStatusName(renewal.getRenewal_staus_id()));
					}

					if (result[8] != null)
						renewal.setRenewal_type((String) result[8]);

					if (result[9] != null) {
						renewal.setDateofRenewal((Date) result[9]);
						renewal.setRenewal_dateofRenewal(dateFormat.format(renewal.getDateofRenewal()));						
					}

						renewal.setRenewal_Amount((Double) result[10]);
						renewal.setRenewal_receipt((String) result[11]);
						renewal.setRenewal_document_id( (Long) result[12]);
						renewal.setBranchName((String) result[13]);
						renewal.setVehicleType((String) result[14]);
						renewal.setRenewal_subtype((String) result[15]);
						renewal.setRenewalBase64Document("<a href=\"download/RenewalReminder/"+renewal.getRenewal_document_id()+".in\" target=\"_blank\"><span class=\"fa fa-download\"> </span></a>");

					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}

	}
	
	@Override
	public List<RenewalReminderDto> getRenewalReminderGroupByRenewalTypeBetweenDates(String query, Integer companyID) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;

			queryt = entityManager
					.createQuery(" SELECT RT.renewal_Type, COUNT(R.renewal_id) "
							+ " FROM RenewalReminder AS R "
							+ " INNER JOIN Vehicle AS V ON V.vid = R.vid  AND V.vStatusId <> "
							+ VehicleStatusConstant.VEHICLE_STATUS_SOLD + " "
							+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId " + " WHERE " + query + " "
							+ "  R.companyId = " + companyID + " AND R.markForDelete = 0 AND R.newRRCreated = 0 "
							+ " GROUP BY R.renewalTypeId ", Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();
					
					if (result[0] != null)
						renewal.setRenewal_type((String) result[0]);

					renewal.setRenewal_id((Long) result[1]);

					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public double totalRenewalExpense(String currentDate, int companyId) throws Exception {
		Query queryt = null;
		queryt = entityManager.createQuery(
			" SELECT SUM(RR.renewal_Amount) " 
				+ " From RenewalReminder as RR "
				+ "	INNER JOIN Vehicle V ON V.vid = RR.vid AND V.vStatusId <> "+ VehicleStatusConstant.VEHICLE_STATUS_SOLD + "  "
				+ " WHERE RR.renewal_from < '" + currentDate + "'   AND RR.companyId = " + companyId +""
				+ " AND RR.markForDelete = 0 AND RR.newRRCreated = 0 ");

		Double count = (double) 0;
		try {

			if ((Double) queryt.getSingleResult() != null) {
				count = (Double) queryt.getSingleResult();
			}

		} catch (NoResultException nre) {
		}

		return count;
	}

	@Override
	public ValueObject getRenewalOverDueReport(ValueObject valueObject) throws Exception {
		CustomUserDetails userDetails = null;
		Long renewalTypeId = null;
		Long renewalSubTypeId = null;
		ValueObject tableConfig = null;
		Short statusId = 0;
		short vStatusId = 0;
		String query = "";
		try {

			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			renewalTypeId = valueObject.getLong("renewalTypeId", 0);
			renewalSubTypeId = valueObject.getLong("renewalSubTypeId", 0);
			statusId = valueObject.getShort("statusId");
			vStatusId = valueObject.getShort("vStatusId", (short)0);
			
			if (statusId > 0) {

				if (renewalTypeId > 0) {
					query += " AND R.renewalTypeId = " + renewalTypeId + " ";
				}
				if (renewalSubTypeId > 0) {
					query += " AND R.renewal_Subid = " + renewalSubTypeId + " ";
				}
				
				if(vStatusId > 0) {
					query +=" AND V.vStatusId ="+vStatusId+" ";
				}

				tableConfig = new ValueObject();

				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH,
						ModuleFilePathConstant.RENEWAL_REMINDER_STATUS_REPORT);

				tableConfig = JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig = JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);

				valueObject.clear();
				if (statusId == 1) {
					valueObject.put("renewalList", getRenewalReminderOverDueList(query, userDetails));
				}
				if (statusId == 2) {
					valueObject.put("renewalList", getRenewalReminderDueSoonList(query, userDetails));
				}

				valueObject.put("tableConfig", tableConfig.getHtData());
				valueObject.put("company",
						userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));

			}
			return valueObject;

		} catch (Exception e) {
			throw e;
		} finally {

		}
	}

	@Override
	public Long getRenewalTallyPendingCount(Integer companyId, Integer vid) throws Exception {

		Query queryt = null;
		queryt = entityManager.createQuery("SELECT Count(V.renewal_id) " + " From RenewalReminder  AS V "
				+ " where V.vid = " + vid + " AND V.isPendingForTally = 0 AND V.markForDelete = 0 "
				+ " AND V.companyId = " + companyId + " AND V.renewal_Amount > 0 ", Object[].class);

		Long count = (long) 0;
		try {
			if ((Long) queryt.getSingleResult() != null) {
				count = (Long) queryt.getSingleResult();
			}

		} catch (NoResultException nre) {

		}

		return count;
	}

	@Override
	public ValueObject getRenewalReminderPendingList(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.renewal_id, R.vid, V.vehicle_registration, R.renewal_from, R.renewal_to, R.renewal_Amount, RT.renewal_Type,"
						+ " RST.renewal_SubType, R.renewal_R_Number, R.vendorId, R.paymentTypeId, VN.vendorName "
						+ " FROM RenewalReminder AS R " + " INNER JOIN Vehicle V ON V.vid = R.vid "
						+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
						+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid"
						+ " LEFT JOIN Vendor VN ON VN.vendorId = R.vendorId " + " where R.vid = "
						+ valueObject.getInt("vid") + " AND R.companyId = " + valueObject.getInt("companyId") + " "
						+ " AND R.isPendingForTally = 0 AND R.markForDelete = 0 AND R.renewal_Amount > 0",
				Object[].class);

		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewal_from_Date((Date) result[3]);
				renewal.setRenewal_To_Date((Date) result[4]);
				renewal.setRenewal_Amount((Double) result[5]);
				renewal.setRenewal_type((String) result[6]);
				renewal.setRenewal_subtype((String) result[7]);
				renewal.setRenewal_R_Number((Long) result[8]);
				if (result[9] != null)
					renewal.setVendorId((Integer) result[9]);
				renewal.setPaymentTypeId((short) result[10]);
				renewal.setRenewal_paymentType(PaymentTypeConstant.getPaymentTypeName((short) result[10]));
				if (result[11] != null) {
					renewal.setVendorName((String) result[11]);
				} else {
					renewal.setVendorName("--");
				}

				Dtos.add(renewal);
			}
		}
		valueObject.put("renewalList", Dtos);

		valueObject.put("expenseList", tripExpenseService.listTripExpense(valueObject.getInt("companyId")));
		valueObject.put("companyList",
				tallyIntegrationService.getTallyCompanyListForCompany(valueObject.getInt("companyId")));

		return valueObject;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false)
	public ValueObject saveRenewalPendingDetails(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject> dataArrayObjColl = null;
		TripSheet tripSheet = null;
		TripSheetExpense tripSheetExpense = null;
		HashMap<String, Object> configuration = null;
		VehicleProfitAndLossTxnChecker profitAndLossExpenseTxnChecker = null;
		CustomUserDetails userDetails = null;
		HashMap<Long, VehicleProfitAndLossTxnChecker> expenseTxnCheckerHashMap = null;
		HashMap<Long, TripSheetExpense> tripSheetExpenseHM = null;
		try {

			tripSheet = tripSheetService.getTripSheet(valueObject.getLong("tripSheetId"),
					valueObject.getInt("companyId"));
			if (tripSheet != null) {

				userDetails = new CustomUserDetails(tripSheet.getCompanyId(), valueObject.getLong("userId"));
				configuration = companyConfigurationService.getCompanyConfiguration(tripSheet.getCompanyId(),
						PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
				UserProfileDto Orderingname = userProfileService
						.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(valueObject.getLong("userId"));
				dataArrayObjColl = (ArrayList<ValueObject>) valueObject.get("seDetails");
				if (dataArrayObjColl != null && !dataArrayObjColl.isEmpty()) {
					for (ValueObject object : dataArrayObjColl) {
						tripSheetExpense = new TripSheetExpense();
						tripSheetExpense.setExpenseId(object.getInt("expenseId", 0));
						tripSheetExpense.setExpenseAmount(object.getDouble("renewalAmt", 0));
						tripSheetExpense.setExpensePlaceId(Orderingname.getBranch_id());
						tripSheetExpense.setExpenseFixedId(TripRouteFixedType.TRIP_ROUTE_FIXED_TYPE_RENEWAL);
						tripSheetExpense.setCreatedById(valueObject.getLong("userId"));
						tripSheetExpense.setTripsheet(tripSheet);
						tripSheetExpense.setCreated(new java.util.Date());
						tripSheetExpense.setCompanyId(tripSheet.getCompanyId());
						tripSheetExpense.setTallyCompanyId(object.getLong("tallyCompanyId"));
						tripSheetExpense.setTransactionId(object.getLong("renewalId"));
						tripSheetExpense.setVendorId(object.getInt("vendorId", 0));
						if (object.getShort("paymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
							tripSheetExpense.setCredit(true);
						}
						tripSheetExpense.setBalanceAmount(tripSheetExpense.getExpenseAmount());
						tripSheetService.addTripSheetExpense(tripSheetExpense);

						updatePendingStatus(object.getLong("renewalId"), (short) 1);

						if (tripSheet.getTripStutesId() == TripSheetStatus.TRIP_STATUS_CLOSED) {
							ValueObject exeObject = new ValueObject();
							expenseTxnCheckerHashMap = new HashMap<>();
							tripSheetExpenseHM = new HashMap<>();

							exeObject.put(VehicleProfitAndLossDto.COMPANY_ID, tripSheet.getCompanyId());
							exeObject.put(VehicleProfitAndLossDto.TRANSACTION_ID, tripSheet.getTripSheetID());
							exeObject.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID,
									VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
							exeObject.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID,
									VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
							exeObject.put(VehicleProfitAndLossDto.TRANSACTION_VID, tripSheet.getVid());
							exeObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, (int) configuration.get("expenseId"));
							exeObject.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, tripSheetExpense.getTripExpenseID());

							profitAndLossExpenseTxnChecker = txnCheckerBL.getVehicleProfitAndLossTxnChecker(exeObject);

							vehicleProfitAndLossTxnCheckerService
									.saveVehicleProfitAndLossTxnChecker(profitAndLossExpenseTxnChecker);
							expenseTxnCheckerHashMap.put(
									profitAndLossExpenseTxnChecker.getVehicleProfitAndLossTxnCheckerId(),
									profitAndLossExpenseTxnChecker);
							tripSheetExpenseHM.put(tripSheetExpense.getTripExpenseID(), tripSheetExpense);
						}

					}

					tripSheetService.UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(tripSheet.getTripSheetID(),
							tripSheet.getCompanyId());

					if (tripSheetExpenseHM != null && !tripSheetExpenseHM.isEmpty()) {

						ValueObject valueObjectExe = new ValueObject();
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

	@Override
	@Transactional
	public void updatePendingStatus(Long renewal_id, short status) throws Exception {
		entityManager.createQuery(" UPDATE RenewalReminder AS ST SET ST.isPendingForTally = " + status
				+ "  where ST.renewal_id = " + renewal_id + "").executeUpdate();

	}
	
	@Override
	public ValueObject getRenewalReminderInitialConfigDataForMobile(ValueObject object) throws Exception {
		List<RenewalType>   				renewalTypeList 		= null;
		int 								companyId 				= 0;
		long 								userId 					= 0;
		CustomUserDetails 					userDetails 			= null;
		HashMap<String, Object>		 		configuration			= null;
		ArrayList<RenewalReminderDto>		periodThreshold			= null;
		try {
			companyId 					= object.getInt("companyId");
			userId 						= object.getLong("userId");
			
			userDetails = new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			renewalTypeList = RenewalTypeService.mobileRenewalTypeList(object);
			object.put("renewalTypeList", renewalTypeList);
			
			periodThreshold = new ArrayList<RenewalReminderDto>();
			periodThreshold.add( new RenewalReminderDto(0, "Days"));
			periodThreshold.add( new RenewalReminderDto(7, "Weeks"));
			periodThreshold.add( new RenewalReminderDto(28, "Months"));
			
			object.put("periodThreshold", periodThreshold);
			
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			
			object.put("userName", userDetails.getFirstName());
			object.put("userId", userDetails.getId());
			object.put("PaymentType", PaymentTypeConstant.getPaymentTypeList());
			object.put(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE, (boolean)configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE));
			object.put("configuration", configuration);
			object.put("SelectPage", 1);

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalTypeList 		= null;
			userDetails 			= null;
		}
	}

	@SuppressWarnings("unused")
	@Override
	@Transactional(readOnly = false)
	public ValueObject saveRenewalReminderFromMobile(ValueObject valueObject, MultipartFile[] file) throws Exception {
		RenewalReminderSequenceCounter 				counter 					= null;
		HashMap<String, Object> 					configuration 				= null;
		CustomUserDetails 							userDetails 				= null;
		VehicleDto 									CheckVehicleStatus 			= null;
		RenewalReminder 							renewalReminder 			= null;
		List<RenewalReminderDto> 					renewal 					= null;
		String 										TIDMandatory 				= "";
		boolean 									VEHICLESTATUS 				= false;
		int 										companyId 					= 0;
		long 										userId 						= 0;
		String										base64						= null;
		String										imageName					= null;
		String										imageExt					= null;
		try {
			companyId 					= valueObject.getInt("companyId");
			userId 						= valueObject.getLong("userId");
			base64						= valueObject.getString("base64String",null);
			imageName					= valueObject.getString("imageName",null);
			imageExt					= valueObject.getString("imageExt",null);

			userDetails = new CustomUserDetails(companyId, userId);
			valueObject.put("userDetails", userDetails);

			configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),
					PropertyFileConstants.RENEWAL_REMINDER_CONFIG);

			// Check Vehicle Status Current IN ACTIVE OR NOT
			CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("vehicleId"), companyId);

			switch (CheckVehicleStatus.getvStatusId()) {
			case VehicleStatusConstant.VEHICLE_STATUS_ACTIVE:

				VEHICLESTATUS = true;
				break;
			case VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP:

				VEHICLESTATUS = true;
				break;
			case VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE:

				VEHICLESTATUS = true;
				break;

			default:
				VEHICLESTATUS = false;
				break;
			}

			Collection<GrantedAuthority> permission = userDetails.getGrantedAuthoritiesList();

			if (permission.contains(new SimpleGrantedAuthority("ALLOW_ALL_STATUS_RENEWAL"))) {
				VEHICLESTATUS = true;
			}

			if (VEHICLESTATUS) {

				counter = renewalReminderSequenceService.findNextRenewal_R_Number(userDetails.getCompany_id());
				if (counter == null) {
					valueObject.put("sequenceNotFound", true);
					valueObject.put("accessToken", Utility.getUniqueToken(request));
					return valueObject;
				}

				renewalReminder = RRBL.saveRRDetailsFromMobile(valueObject);
				
				renewalReminder.setRenewal_R_Number(counter.getNextVal());
				if(valueObject.getBoolean("createApproval", false) == true) {
					renewalReminder.setRenewal_staus_id(RenealReminderStatus.NOT_APPROVED);
				}else {
					renewalReminder.setRenewal_staus_id(RenealReminderStatus.APPROVED);
				}
				renewalReminder.setRenewal_approvedbyId(userDetails.getId());
				renewalReminder.setRenewal_approveddate(new java.util.Date());

				if ((boolean) configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE) || file != null || base64 != null) {
					
					if(file != null) {
						if(file.length > 0) {
							renewalReminder.setRenewal_document(true);
						}else {
							renewalReminder.setRenewal_document(false);
						}
					} else {
						if(base64 != null) {
							renewalReminder.setRenewal_document(true);
						}else {
							renewalReminder.setRenewal_document(false);
						}
					}

					// Here Checking the Renewal Reminder Details Date From and To Value Details

					String Query = " ( RR.vid =" + renewalReminder.getVid() + " " + " AND RR.renewalTypeId ="
							+ renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
							+ renewalReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_from()
							+ "' between RR.renewal_from AND RR.renewal_to  OR RR.vid=" + renewalReminder.getVid() + " "
							+ " AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
							+ renewalReminder.getRenewal_Subid() + "  AND '" + SQL_DATE_FORMAT2.format(renewalReminder.getRenewal_to())
							+ "' between RR.renewal_from AND RR.renewal_to ) AND RR.companyId = "
							+ userDetails.getCompany_id() + " AND RR.markForDelete = 0 AND RR.renewalTypeId NOT IN(67, 116) ";//=fitness

					// show Vehicle Name Renewal Type and Renewal SubType List
					if((boolean) configuration.getOrDefault("dateValidation", true))
					renewal = RRBL.prepareListofRenewalDto(Validate_RenewalReminder(Query));

					if (renewal != null && !renewal.isEmpty()) {
						// Checking the Value Of Mandatory Compliance
						for (RenewalReminderDto add : renewal) {
							TIDMandatory += "<span>" + add.getVehicle_registration()
									+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
									+ add.getRenewal_id() + " \" target=\"_blank\" >" + add.getRenewal_type() + " "
									+ add.getRenewal_subtype()
									+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
									+ add.getRenewal_from() + " To " + add.getRenewal_to() + " </span>, <br>";
						}
						valueObject.put("already", TIDMandatory);
						valueObject.put("renewalRemindeAlready", true);
						valueObject.put("accessToken", Utility.getUniqueToken(request));

						return valueObject;

					} else {

						List<RenewalReminderDto> renewalReceipt = null;

						// Here Checking the Renewal Reminder Details Receipt Details
						String QueryReceipt = "RR.renewal_receipt='" + renewalReminder.getRenewal_receipt()
								+ "' AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId()
								+ " AND RR.renewal_Subid =" + renewalReminder.getRenewal_Subid()
								+ " AND RR.companyId = " + userDetails.getCompany_id() + " AND RR.markForDelete = 0";

						// Renewal Reminder Receipt
						if ((boolean) configuration.get("receiptnumbershow")
								&& (boolean) configuration.get("validateDuplicateRefnumber")) {
							renewalReceipt = RRBL.prepareListofRenewalDto(Validate_RenewalReminder(QueryReceipt));
						}

						if (renewalReceipt != null && !renewalReceipt.isEmpty()) {

							// Checking the Value Of Mandatory Compliance
							for (RenewalReminderDto add : renewalReceipt) {
								TIDMandatory += "<span>" + add.getVehicle_registration()
										+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
										+ add.getRenewal_id() + "\" target=\"_blank\">" + add.getRenewal_type() + " "
										+ add.getRenewal_subtype()
										+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
										+ add.getRenewal_from() + " To " + add.getRenewal_to()
										+ " Receipt | Challan  Number " + add.getRenewal_receipt() + " </span>, <br>";
							}
							valueObject.put("ReceiptAlready", TIDMandatory);
							valueObject.put("renewalReceiptAlready", true);
							valueObject.put("accessToken", Utility.getUniqueToken(request));

							return valueObject;

						} else {

							/* save Renewal Reminder */

							updateNewRRCreated(renewalReminder.getVid(), renewalReminder.getRenewalTypeId(),
									renewalReminder.getRenewal_Subid());
							
							System.err.println("before renewalReminder "+renewalReminder);
							addRenewalReminder(renewalReminder);
							System.err.println("after save renewalReminder "+renewalReminder);

							ValueObject ownerShipObject = null;
							if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED ){
								ownerShipObject	= new ValueObject();
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, renewalReminder.getRenewal_id());
								ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, renewalReminder.getVid());
								ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, renewalReminder.getRenewal_Amount());
								ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
								ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_RENEWAL);
								ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, SQL_DATE_FORMAT.parse(SQL_DATE_FORMAT.format(renewalReminder.getRenewal_dateofpayment())));
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Renewal Entry");
								ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Renewal Number : "+renewalReminder.getRenewal_R_Number());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
								ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
								ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -renewalReminder.getRenewal_Amount());
								
								VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
								vehicleAgentTxnCheckerService.save(agentTxnChecker);
								
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
								
							}

							org.fleetopgroup.persistence.document.RenewalReminderDocument rewalDoc = new org.fleetopgroup.persistence.document.RenewalReminderDocument();
							
							if(file != null && file.length > 0) {
								
								for(int i=0; i<file.length; i++) {
									rewalDoc.setRenewal_id(renewalReminder.getRenewal_id());
									rewalDoc.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
									
									byte[] bytes = file[i].getBytes();
									
									rewalDoc.setRenewal_filename(file[i].getOriginalFilename());
									rewalDoc.setRenewal_content(bytes);
									rewalDoc.setRenewal_contentType(file[i].getContentType());

									// when Add Renewal Reminder to file Renewal Reminder History id is null
									rewalDoc.setRenewalHis_id((long) 0);
									rewalDoc.setMarkForDelete(false);
									rewalDoc.setCreatedById(userDetails.getId());
									rewalDoc.setLastModifiedById(userDetails.getId());

									java.util.Date currentDateUpdate = new java.util.Date();
									Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

									/** Set Created Current Date */
									rewalDoc.setCreated(toDate);
									rewalDoc.setLastupdated(toDate);
									rewalDoc.setCompanyId(userDetails.getCompany_id());
									

									//RenewalReminderService.add_RenewalReminder_Document(rewalDoc);
									documentService.save(rewalDoc);

									// Note: This Update is Document ID Update Renewal Reminder Details
									Update_RenewalReminderDocument_ID_to_RenewalReminder(
											rewalDoc.get_id(), true, renewalReminder.getRenewal_id(),
											userDetails.getCompany_id());
								}
							}
							
							if(base64 != null) {
								rewalDoc.setRenewal_id(renewalReminder.getRenewal_id());
								rewalDoc.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
								
								byte[] bytes = ByteConvertor.base64ToByte(base64);
								
								rewalDoc.setRenewal_filename(imageName);
								rewalDoc.setRenewal_content(bytes);
								rewalDoc.setRenewal_contentType(imageExt);

								// when Add Renewal Reminder to file Renewal Reminder History id is null
								rewalDoc.setRenewalHis_id((long) 0);

								rewalDoc.setMarkForDelete(false);
								rewalDoc.setCreatedById(userDetails.getId());
								rewalDoc.setLastModifiedById(userDetails.getId());

								java.util.Date currentDateUpdate = new java.util.Date();
								Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

								rewalDoc.setCreated(toDate);
								rewalDoc.setLastupdated(toDate);
								rewalDoc.setCompanyId(userDetails.getCompany_id());

								//RenewalReminderService.add_RenewalReminder_Document(rewalDoc);
								documentService.save(rewalDoc);

								// Note: This Update is Document ID Update Renewal Reminder Details
								Update_RenewalReminderDocument_ID_to_RenewalReminder(
										rewalDoc.get_id(), true, renewalReminder.getRenewal_id(),
										userDetails.getCompany_id());
							}

							valueObject.put("saveRenewalReminder", true);
							valueObject.put("renewalId", renewalReminder.getRenewal_id());
							
							if(valueObject.getBoolean("allowBankPaymentDetails",false) && (PaymentTypeConstant.getPaymentTypeIdList().contains(renewalReminder.getPaymentTypeId()) || renewalReminder.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)) {
								ValueObject bankPaymentValueObject=JsonConvertor.toValueObjectFormSimpleJsonString(valueObject.getString("bankPaymentDetails"));
									bankPaymentValueObject.put("bankPaymentTypeId", renewalReminder.getPaymentTypeId());
									bankPaymentValueObject.put("userId",userDetails.getId());
									bankPaymentValueObject.put("companyId",userDetails.getCompany_id());
									bankPaymentValueObject.put("moduleId",renewalReminder.getRenewal_id());
									bankPaymentValueObject.put("moduleNo", renewalReminder.getRenewal_R_Number());
									bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.RENEWAL_REMINDER);
									bankPaymentValueObject.put("amount",renewalReminder.getRenewal_Amount());
									bankPaymentValueObject.put("paidDate", renewalReminder.getRenewal_dateofpayment());
									bankPaymentValueObject.put("remark", "Renewal Created For Vehicle "+CheckVehicleStatus.getVehicle_registration());
									bankPaymentValueObject.put("stopIvCargoCashBookEntries", valueObject.getBoolean("stopIvCargoCashBookEntries",false));
									if(renewalReminder.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)
										cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);	
									else
										bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
								}

							if (ownerShipObject != null) {
								vehicleAgentIncomeExpenseDetailsService
										.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
							}
						}

					}

				} else {
					valueObject.put("documentIsCompulsory", true);
					valueObject.put("accessToken", Utility.getUniqueToken(request));
				}

			}else {
				valueObject.put("vehicleStatus", CheckVehicleStatus.getVehicle_Status());
				valueObject.put("accessToken", Utility.getUniqueToken(request));
			}
			return valueObject;

		} catch (Exception e) {
			throw e;
		}

	}
	
	@Override
	public ValueObject getRenewalReminderShowDataForMobile(ValueObject object) throws Exception {
		int 								companyId 				= 0;
		long 								userId 					= 0;
		CustomUserDetails 					userDetails 			= null;
		RenewalReminderDto 					renewalReminder 		= null;
		HashMap<String, Object>		 		configuration			= null;
		try {
			companyId 					= object.getInt("companyId");
			userId 						= object.getLong("userId");
			
			userDetails = new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			renewalReminder = getRenewalReminderById(object.getLong("renewalId"), userDetails);
			object.put("renewalReminder", RRBL.GetRenewalReminder(renewalReminder));
			
			org.fleetopgroup.persistence.document.RenewalReminderDocument rewalDoc = documentService.get_RenewalReminder_Document(renewalReminder.getRenewal_document_id(), userDetails.getCompany_id());

			if(rewalDoc != null) {
				renewalReminder.setRenewalBase64Document(ByteConvertor.byteArraytoBase64(rewalDoc.getRenewal_content()));
				renewalReminder.setFileExtension(rewalDoc.getRenewal_contentType());
			}
			object.put("renewalImage", renewalReminder.getRenewalBase64Document());
			object.put("SelectStatus", RenealReminderStatus.NOT_APPROVED);
			object.put("configuration", configuration);
			object.put("SelectStatusString",RenealReminderStatus.getRenewalStatusName(RenealReminderStatus.NOT_APPROVED));
			object.put("imageExt", renewalReminder.getFileExtension());
			if(renewalReminder.getRenewal_approvedID() != null)
				object.put("RenewalReminderApproval", RRBL.getApprovalDetails(Get_RenewalReminderApprovalDetails(renewalReminder.getRenewal_approvedID(), userDetails.getCompany_id())));
				

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalReminder 		= null;
			userDetails 			= null;
		}
	}
	
	@SuppressWarnings("unused")
	@Override
	public ValueObject searchRenewalByNumber(ValueObject object) throws Exception {
		int 								companyId 				= 0;
		long 								userId 					= 0;
		CustomUserDetails 					userDetails 			= null;
		RenewalReminder 					renewalReminder 		= null;
		RenewalReminderDocument				document				= null;
		RenewalReminderDto					renewalDto				= null;
		try {
			companyId 					= object.getInt("companyId");
			userId 						= object.getLong("userId");
			renewalReminder				= new RenewalReminder();
			renewalDto					= new RenewalReminderDto();
			
			userDetails = new CustomUserDetails(companyId, userId);
			object.put("userDetails", userDetails);
			
			renewalReminder = renewalReminderDao.getRenewalReminder(object.getLong("renewalNumber"), companyId);
			
			if(renewalReminder != null) {
				object.put("renewalFound", true);
				object.put("renewalId", renewalReminder.getRenewal_id());
				
			} else {
				object.put("renewalNotFound", true);
			}

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalReminder 		= null;
			userDetails 			= null;
		}
	}
	@Override
	public List<RenewalReminderDto> getListOfNumberOfRRCreatedOnVehicles(Integer companyID) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
			queryt = entityManager.createQuery(
					" SELECT COUNT(RR.vid), RR.vid, V.vehicle_registration "
							+ " FROM RenewalReminder AS RR "
							+ " INNER JOIN Vehicle AS V ON V.vid = RR.vid AND V.vStatusId <> 4  "
							+ " WHERE RR.companyId = "+companyID+" and RR.markForDelete = 0 "
							+ " GROUP BY RR.vid ",
							Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto service = null;
				
					for (Object[] result : results) {
						service = new RenewalReminderDto();
						
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
	public List<RenewalReminderDto> getListOfAllRRVehicleWise(Integer companyID) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
			queryt = entityManager.createQuery(
					" SELECT COUNT(RR.vid), RR.vid, V.vehicle_registration "
							+ " FROM Vehicle AS V "
							+ " LEFT JOIN RenewalReminder AS RR ON RR.vid = V.vid AND RR.companyId = "+companyID+" and RR.markForDelete = 0  "
							+ " WHERE V.company_Id = "+companyID+" and V.markForDelete = 0 AND V.vStatusId <> 4  "
							+ " GROUP BY V.vid ",
							Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto service = null;
				
					for (Object[] result : results) {
						service = new RenewalReminderDto();
						
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
	public List<RenewalReminderDto> getRenewalReminderListByVehicle(int vehicleId, CustomUserDetails userDetails) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, R.renewal_receipt, R.paymentTypeId, R.renewal_PayNumber,"
								+ " R.renewal_authorization, R.renewal_number, R.renewal_dateofpayment, U.firstName, R.renewal_timethreshold,"
								+ " R.renewal_periedthreshold, U2.firstName, R.renewal_approveddate, R.renewal_approvedComment,"
								+ " U3.email, R.created, U4.email, R.lastupdated, R.renewalTypeId, R.renewal_Subid, R.renewal_paidbyId, "
								+ " R.renewal_approvedbyId, R.vendorId, VN.vendorName, VN.vendorLocation  "
								+ " FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid"
								+ " LEFT  JOIN Vendor VN ON VN.vendorId = R.vendorId "
								+ " LEFT JOIN User U ON U.id   = R.renewal_paidbyId"
								+ " LEFT JOIN User U2 ON U2.id = R.renewal_approvedbyId"
								+ " INNER JOIN User U3 ON U3.id = R.createdById"
								+ " INNER JOIN User U4 ON U4.id = R.lastModifiedById " 
								+ " WHERE R.vid = "+vehicleId+" AND R.companyId = "+userDetails.getCompany_id()+" "
								+ " AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			} else {			
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, R.renewal_receipt, R.paymentTypeId, R.renewal_PayNumber,"
								+ " R.renewal_authorization, R.renewal_number, R.renewal_dateofpayment, U.firstName, R.renewal_timethreshold,"
								+ " R.renewal_periedthreshold, U2.firstName, R.renewal_approveddate, R.renewal_approvedComment,"
								+ " U3.email, R.created, U4.email, R.lastupdated, R.renewalTypeId, R.renewal_Subid, R.renewal_paidbyId, "
								+ " R.renewal_approvedbyId, R.vendorId, VN.vendorName, VN.vendorLocation"
								+ " FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid "
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ userDetails.getId() + "" + " LEFT JOIN User U ON U.id   = R.renewal_paidbyId"
								+ " LEFT JOIN User U2 ON U2.id = R.renewal_approvedbyId"
								+ " LEFT JOIN Vendor VN ON VN.vendorId = R.vendorId "
								+ " INNER JOIN User U3 ON U3.id = R.createdById"
								+ " INNER JOIN User U4 ON U4.id = R.lastModifiedById " 
								+ " WHERE R.vid = "+vehicleId+" AND R.companyId = "+userDetails.getCompany_id()+" "
								+ " AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			}
			queryt.setMaxResults(25);
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();

					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_from_Date((Date) result[5]);
					renewal.setRenewal_To_Date((Date) result[6]);
					renewal.setRenewal_from(dateFormat.format(renewal.getRenewal_from_Date()));
					renewal.setRenewal_to(dateFormat.format(renewal.getRenewal_To_Date()));
					if (result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_staus_id((short) result[9]);
					renewal.setRenewal_status(RenealReminderStatus.getRenewalStatusName(renewal.getRenewal_staus_id()));
					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
					renewal.setRenewalAproval_Number((Long) result[14]);
					renewal.setRenewal_receipt((String) result[15]);
					renewal.setPaymentTypeId((short) result[16]);
					renewal.setRenewal_paymentType(PaymentTypeConstant.getPaymentTypeName(renewal.getPaymentTypeId()));
					renewal.setRenewal_PayNumber((String) result[17]);
					renewal.setRenewal_authorization((String) result[18]);
					renewal.setRenewal_number((String) result[19]);
					if (result[20] != null) {
						renewal.setRenewal_payment_Date((Date) result[20]);
					}
					if(renewal.getRenewal_payment_Date()!=null) {
						renewal.setRenewal_dateofpayment(dateFormat.format(renewal.getRenewal_payment_Date()));
					}
					renewal.setRenewal_paidby((String) result[21]);
					renewal.setRenewal_timethreshold((Integer) result[22]);
					renewal.setRenewal_periedthreshold((Integer) result[23]);
					renewal.setRenewal_approvedby((String) result[24]);
					renewal.setRenewal_approveddate((Date) result[25]);
					renewal.setRenewal_approvedComment((String) result[26]);
					renewal.setCreatedBy((String) result[27]);
					renewal.setCreatedOn((Date) result[28]);
					renewal.setLastModifiedBy((String) result[29]);
					renewal.setLastupdatedOn((Date) result[30]);
					renewal.setRenewalTypeId((Integer) result[31]);
					renewal.setRenewal_Subid((Integer) result[32]);
					renewal.setRenewal_paidbyId((Long) result[33]);
					renewal.setRenewal_approvedbyId((Long) result[34]);
					if (result[35] != null) {
						renewal.setVendorId((Integer) result[35]);
						renewal.setVendorName((String) result[36]);
						if (result[37] != null) {
							renewal.setVendorName((String) result[36] + " : " + (String) result[37]);
						}
					}
					
					org.fleetopgroup.persistence.document.RenewalReminderDocument rewalDoc = documentService.get_RenewalReminder_Document(renewal.getRenewal_document_id(), userDetails.getCompany_id());

					if(rewalDoc != null) {
						renewal.setRenewalBase64Document(ByteConvertor.byteArraytoBase64(rewalDoc.getRenewal_content()));
					}

					Dtos.add(renewal);
				}
			} else {
				return null;
			}

			return Dtos;

		} catch (Exception e) {
			throw e;
		}

	}
	
	@Override
	public ValueObject searchRenewalByVehicle(ValueObject object) throws Exception {
		int 								companyId 				= 0;
		long 								userId 					= 0;
		CustomUserDetails 					userDetails 			= null;
		List<RenewalReminderDto> 			renewalReminder 		= null;
		HashMap<String, Object>		 		configuration			= null;
		try {
			companyId 					= object.getInt("companyId");
			userId 						= object.getLong("userId");
			
			userDetails 	= new CustomUserDetails(companyId, userId);
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			
			object.put("userDetails", userDetails);
			object.put("configuration", configuration);
			
			renewalReminder = getRenewalReminderListByVehicle(object.getInt("vid"), userDetails);
			
			if(renewalReminder != null) {
				object.put("renewalFound", true);
				object.put("renewalReminder", renewalReminder);
			} else {
				object.put("renewalNotFound", true);
			}

			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalReminder 		= null;
			userDetails 			= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> getRenewalListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany, boolean setFixedRenewalLedgerName, String renewalLedgerName) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.renewal_id, SE.renewal_R_Number, SE.renewal_dateofpayment, V.vendorName,"
					+ " VH.vehicle_registration, VH.ledgerName, TC.companyName, SE.created, SE.paymentTypeId, SE.vid, "
					+ " SE.renewal_Amount, E.expenseName, SE.isPendingForTally  "
					+ " FROM RenewalReminder SE "
					+ " INNER JOIN Vehicle VH ON VH.vid = SE.vid "
					+ " LEFT JOIN Vendor AS V ON V.vendorId = SE.vendorId"
					+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = SE.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
					+ " INNER JOIN RenewalType TE ON TE.renewal_id = SE.renewalTypeId "
					+ " INNER JOIN TripExpense E ON E.expenseID = TE.expenseId"
					+ " WHERE SE.renewal_dateofpayment between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND SE.renewal_Amount > 0");
			
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
						if(!setFixedRenewalLedgerName)
							select.setExpenseName((String) vehicle[11]);
						else
							select.setExpenseName(renewalLedgerName);
						
						select.setPendingForTally((boolean) vehicle[12]);
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_RENEWAL);
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
						
						 select.setRemark("Renewal Reminder On Vehicle "+select.getVehicle_registration()+" Date: "+dateFormat.format((java.util.Date)vehicle[2])+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						
						select.setTripSheetNumberStr("RR-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
						
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
	public List<TripSheetExpenseDto> getRenewalListForTallyImportATC(String fromDate, String toDate, Integer companyId,
			String tallyCompany, boolean setFixedRenewalLedgerName, String renewalLedgerName) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.renewal_id, SE.renewal_R_Number, SE.renewal_dateofpayment, V.vendorName,"
					+ " VH.vehicle_registration, VH.ledgerName, SE.created, SE.paymentTypeId, SE.vid, "
					+ " SE.renewal_Amount, E.expenseName, SE.isPendingForTally  "
					+ " FROM RenewalReminder SE "
					+ " INNER JOIN Vehicle VH ON VH.vid = SE.vid "
					+ " LEFT JOIN Vendor AS V ON V.vendorId = SE.vendorId"
					+ " INNER JOIN RenewalType TE ON TE.renewal_id = SE.renewalTypeId "
					+ " INNER JOIN TripExpense E ON E.expenseID = TE.expenseId"
					+ " WHERE SE.renewal_dateofpayment between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND SE.renewal_Amount > 0");
			
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
						select.setTallyCompanyName(tallyCompany);
						select.setCreatedOn((Timestamp) vehicle[6]);
						select.setExpenseFixedId((short) vehicle[7]);
						select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
						select.setVid((Integer) vehicle[8]);
						select.setExpenseAmount((Double) vehicle[9]);
						if(!setFixedRenewalLedgerName)
							select.setExpenseName((String) vehicle[10]);
						else
							select.setExpenseName(renewalLedgerName);
						
						select.setPendingForTally((boolean) vehicle[11]);
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_RENEWAL);
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
						
						 select.setRemark("Renewal Reminder On Vehicle "+select.getVehicle_registration()+" Date: "+dateFormat.format((java.util.Date)vehicle[2])+" From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						
						select.setTripSheetNumberStr("RR-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
						
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
	public ValueObject getRenewalMandatoryList(ValueObject object) throws Exception {
		List<RenewalReminderDto>			mandatoryList			= null;
		String 								query					= "";
		try {
			if(object.getInt("vid",0) > 0 ) {
				query = "AND RR.vid ="+object.getInt("vid",0)+" ";
			
			}
			mandatoryList	= getRenewalMandatoryListByVehicle(query, object.getInt("companyId"));
			
			if(mandatoryList != null) {
				object.put("mandatoryList", mandatoryList);
			}
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			mandatoryList   = null;
		}
	}
	
	@Override
	public List<RenewalReminderDto> getRenewalMandatoryListByVehicle(String query, int companyId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
			queryt = entityManager
					.createQuery(" SELECT RT.renewal_id, RT.renewal_Type, RST.renewal_Subid, RST.renewal_SubType, RR.renewal_id "
							+ " FROM RenewalSubType AS RST "
							+ " INNER JOIN RenewalType AS RT ON RT.renewal_id = RST.renewal_id "
							+ " LEFT JOIN RenewalReminder AS RR ON RR.renewal_Subid = RST.renewal_Subid " 
							+ " "+query+" AND RR.companyId = "+companyId+" AND RR.markForDelete = 0 "
							+ " WHERE RST.companyId IN ("+companyId+", 0) AND RST.markForDelete = 0 AND RST.isMandatory = 1 ",
							 	Object[].class);

			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();
					
					if (result[4] == null) {
						renewal.setRenewalTypeId((int) result[0]);
						renewal.setRenewal_type((String) result[1]);
						renewal.setRenewal_Subid((int) result[2]);
						renewal.setRenewal_subtype((String) result[3]);
						renewal.setRenewal_id((Long) result[4]);

						Dtos.add(renewal);
					}
						
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}

	}
	
	@Override
	public ValueObject getVehicleAndRenewalTypeDetails(ValueObject object) throws Exception {
		CustomUserDetails 					userDetails 			= null;
		RenewalSubTypeDto					typeAndSubType			= null;
		Vehicle								vehicle					= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vehicle = VehicleRepository.getVehicel(object.getInt("vid"), userDetails.getCompany_id());
			object.put("vehicle", vehicle);
			
			typeAndSubType	= RenewalSubTypeService.getRenewalSubTypeByID(object.getInt("mandatoryRenewalSubTypeId"), userDetails.getCompany_id());
			if(typeAndSubType != null) {
				object.put("typeAndSubType", typeAndSubType);
			}
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			userDetails  	= null;
			typeAndSubType   = null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject updateRenewalReminderDetails(ValueObject valueObject, MultipartFile[] file) throws Exception {
		HashMap<String, Object> 					configuration 				= null;
		CustomUserDetails 							userDetails 				= null;
		RenewalReminder 							renewalReminder 			= null;
		List<RenewalReminderDto> 					validateRenewalDB 			= null;
		String 										TIDMandatory 				= "";
		boolean 									UpdateRenewalReminder 		= false;
		String 										recieiptNumber 				= null;
		List<RenewalReminderDto> 					renewal 					= null;
		List<RenewalReminderDto> 					renewalReceipt 				= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.RENEWAL_REMINDER_CONFIG);

			valueObject.put("userDetails", userDetails);

			renewalReminder = RRBL.updateRenewalReminderDetails(valueObject);

				// Here Checking the Renewal Reminder Details Date From and To Value Details

				String Query = " ( RR.vid =" + renewalReminder.getVid() + " " + " AND RR.renewalTypeId ="
						+ renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
						+ renewalReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_from()
						+ "' between RR.renewal_from AND RR.renewal_to  OR RR.vid=" + renewalReminder.getVid() + " "
						+ " AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
						+ renewalReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_to()
						+ "' between RR.renewal_from AND RR.renewal_to ) AND RR.companyId = "
						+ userDetails.getCompany_id() + " AND RR.markForDelete = 0 "
						+ " AND RR.renewal_id <> "+renewalReminder.getRenewal_id()+" AND RR.renewalTypeId NOT IN (67, 116) ";
				
				

				// show Vehicle Name Renewal Type and Renewal SubType List
				validateRenewalDB = RRBL.prepareListofRenewalDto(Validate_RenewalReminder(Query));
				
				
				if (validateRenewalDB != null && !validateRenewalDB.isEmpty() ) {
					for (RenewalReminderDto renewalReminderDto2 : validateRenewalDB) {
						recieiptNumber = renewalReminderDto2.getRenewal_receipt();
						break;
					}
				} else {
					UpdateRenewalReminder = true;
				}
				if (validateRenewalDB != null && !validateRenewalDB.isEmpty() ) {				
					for(RenewalReminderDto renewalDB :validateRenewalDB ){	
						
						if (renewalDB != null  && !renewalDB.getRenewal_id().equals(renewalReminder.getRenewal_id())) {
							try {
								renewal = RRBL.prepareListofRenewalDto(validateRenewalDB);
								for (RenewalReminderDto add : renewal) {
									TIDMandatory += "<span>" + add.getVehicle_registration()
											+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
											+ add.getRenewal_id() + "\" target=\"_blank\">" + add.getRenewal_type() + " "
											+ add.getRenewal_subtype()
											+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
											+ add.getRenewal_from() + " To " + add.getRenewal_to() + " </span>, <br>";
								}
							} catch (Exception e) {
								logger.error("Renewal Reminder:", e);
							}
							valueObject.put("already", TIDMandatory);
							valueObject.put("renewalRemindeAlready", true);

							return valueObject;

						} else {
							if (recieiptNumber == null) {
								recieiptNumber = "";
							}

							if (!(recieiptNumber).equalsIgnoreCase(renewalReminder.getRenewal_receipt())) {

								try {
									String QueryReceipt = "RR.renewal_receipt='" + renewalReminder.getRenewal_receipt()
											+ "' AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId()
											+ " AND RR.renewal_Subid =" + renewalReminder.getRenewal_Subid()
											+ " AND RR.companyId = " + userDetails.getCompany_id()
											+ " AND RR.markForDelete = 0 ";
									// Renewal Reminder Receipt
									
									if((boolean)configuration.get("receiptnumbershow") && (boolean)configuration.get("validateDuplicateRefnumber")) {
										renewalReceipt = RRBL.prepareListofRenewalDto(Validate_RenewalReminder(QueryReceipt));
										
										if (renewalReceipt != null && !renewalReceipt.isEmpty() && !renewalReceipt.get(0).getRenewal_id().equals(renewalReminder.getRenewal_id())) {
									
											// Checking the Value Of Mandatory Compliance
											for (RenewalReminderDto add : renewalReceipt) {
												TIDMandatory += "<span>" + add.getVehicle_registration()
														+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
														+ add.getRenewal_id() + "\" target=\"_blank\">" + add.getRenewal_type()
														+ " " + add.getRenewal_subtype()
														+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
														+ add.getRenewal_from() + " To " + add.getRenewal_to()
														+ " Receipt | Challan  Number " + add.getRenewal_receipt()
														+ " </span>, <br>";
											}
											
											valueObject.put("ReceiptAlready", TIDMandatory);
											valueObject.put("renewalReceiptAlready", true);
	
											return valueObject;
										} 
									} else {
										UpdateRenewalReminder = true;
									}

								} catch (Exception e) {
									logger.error("Renewal Reminder:", e);
								}

							} else {
								UpdateRenewalReminder = true;
							}
						}
					}
				}
				if (UpdateRenewalReminder) {
					java.util.Date  previousDate	= null;
					Double			previousAmount	= 0.0;
					short 			oldPaymentTypeId = 0;
					try {
						// get the reminder id to get one row to
						RenewalReminderDto renewalReminderOld = (getRenewalReminderById(renewalReminder.getRenewal_id(), userDetails));
						
						previousDate	= SQL_DATE_FORMAT.parse(SQL_DATE_FORMAT.format(renewalReminderOld.getRenewal_payment_Date()));
						previousAmount	= renewalReminderOld.getRenewal_Amount();
						oldPaymentTypeId = renewalReminderOld.getPaymentTypeId();
						
						renewalReminder.setRenewal_R_Number(renewalReminderOld.getRenewal_R_Number());
						if(file == null || file.length <= 0) {
							renewalReminder.setRenewal_document(renewalReminderOld.getRenewal_document());
							renewalReminder.setRenewal_document_id(renewalReminderOld.getRenewal_document_id());
						}
						
						// get reminder Dto data change to DriverReminderHistory
						RenewalReminderHistory renewalReminderHistory = RRBL.prepareRenewalRemiderHistory(renewalReminderOld);
						renewalReminderHistory.setCompanyId(userDetails.getCompany_id());
						renewalReminderHistory.setCreatedById(userDetails.getId());
						renewalReminderHistory.setLastModifiedById(userDetails.getId());
						addRenewalReminderHistory(renewalReminderHistory);
						
						valueObject.put("saveRenewalReminderHis", true);
						
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
					//	renewalReminder.setRenewal_staus_id(RenealReminderStatus.APPROVED);
						// renewalReminder.setRenewal_status(RenealReminderStatus.NOT_APPROVED_NAME);
						/* update Renewal Reminder */
						updateRenewalReminder(renewalReminder);
						
						VehicleDto	CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(renewalReminder.getVid(), userDetails.getCompany_id());
						ValueObject	ownerShipObject	= null;	
						if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED){
							if((renewalReminder.getRenewal_Amount() - previousAmount) != 0 || !(previousDate.equals(new Timestamp(renewalReminder.getRenewal_dateofpayment().getTime())))) {
								
								ownerShipObject	= new ValueObject();
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, renewalReminder.getRenewal_id());
								ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, renewalReminder.getVid());
								ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, renewalReminder.getRenewal_Amount());
								ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
								ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_RENEWAL);
								ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, renewalReminder.getCompanyId());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, SQL_DATE_FORMAT.parse(SQL_DATE_FORMAT.format(renewalReminder.getRenewal_dateofpayment())));
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Urea Entry");
								ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Urea Number : "+renewalReminder.getRenewal_R_Number());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
								ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, renewalReminder.getCreatedById());
								if(previousDate.equals(new Timestamp(renewalReminder.getRenewal_dateofpayment().getTime()))) {
									ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, - (renewalReminder.getRenewal_Amount() - previousAmount));
									ownerShipObject.put("isDateChanged", false);
								}else {
									ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, - renewalReminder.getRenewal_Amount());
									ownerShipObject.put("isDateChanged", true);
									
								}
								ownerShipObject.put("previousAmount", -previousAmount);
								ownerShipObject.put("previousDate", previousDate);
								
								
								VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
								vehicleAgentTxnCheckerService.save(agentTxnChecker);
								
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
							}
								
						}
						

						org.fleetopgroup.persistence.document.RenewalReminderDocument rewalDoc = new org.fleetopgroup.persistence.document.RenewalReminderDocument();
						
						if(file != null && file.length > 0) {
							
							for(int i=0; i<file.length; i++) {
								rewalDoc.setRenewal_id(renewalReminder.getRenewal_id());
								rewalDoc.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
								
								byte[] bytes = file[i].getBytes();
								
								rewalDoc.setRenewal_filename(file[i].getOriginalFilename());
								rewalDoc.setRenewal_content(bytes);
								rewalDoc.setRenewal_contentType(file[i].getContentType());

								// when Add Renewal Reminder to file Renewal Reminder History id is null
								rewalDoc.setRenewalHis_id((long) 0);
								rewalDoc.setMarkForDelete(false);
								rewalDoc.setCreatedById(userDetails.getId());
								rewalDoc.setLastModifiedById(userDetails.getId());

								java.util.Date currentDateUpdate = new java.util.Date();
								Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

								/** Set Created Current Date */
								rewalDoc.setCreated(toDate);
								rewalDoc.setLastupdated(toDate);
								rewalDoc.setCompanyId(userDetails.getCompany_id());

								//RenewalReminderService.add_RenewalReminder_Document(rewalDoc);
								documentService.save(rewalDoc);

								// Note: This Update is Document ID Update Renewal Reminder Details
								Update_RenewalReminderDocument_ID_to_RenewalReminder(
										rewalDoc.get_id(), true, renewalReminder.getRenewal_id(),
										userDetails.getCompany_id());
							}
						}
						
						if(ownerShipObject != null) {
							vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
						}
						
						if (valueObject.getBoolean("allowBankPaymentDetails",false)) {
						ValueObject bankPaymentValueObject=JsonConvertor.toValueObjectFormSimpleJsonString(valueObject.getString("bankPaymentDetails"));
						bankPaymentValueObject.put("oldPaymentTypeId",oldPaymentTypeId);
						bankPaymentValueObject.put("bankPaymentTypeId", renewalReminder.getPaymentTypeId());
						bankPaymentValueObject.put("currentPaymentTypeId", renewalReminder.getPaymentTypeId());
						bankPaymentValueObject.put("userId",userDetails.getId());
						bankPaymentValueObject.put("companyId", userDetails.getCompany_id());
						bankPaymentValueObject.put("moduleId",renewalReminder.getRenewal_id());
						bankPaymentValueObject.put("moduleNo", renewalReminder.getRenewal_R_Number());
						bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.RENEWAL_REMINDER);
						bankPaymentValueObject.put("amount",renewalReminder.getRenewal_Amount());
						
						//Vendor	vendor	=  vendorService.getVendor(renewalReminder.getVendorId());
						bankPaymentValueObject.put("remark", "Update Renewal Reminder RR-"+renewalReminder.getRenewal_R_Number());
					
						
						bankPaymentService.updatePaymentDetailsFromValuObject(bankPaymentValueObject);
						}
						
						valueObject.put("updateRenewalReminder", true);
						
						return valueObject;
						
					} catch (Exception e) {
						e.printStackTrace();
						return valueObject;
					}
				}

			return valueObject;

		} catch (Exception e) {
			throw e;
		}

	}
	
	@Override
	public ValueObject getRenewalReminderListByVehicle(ValueObject object) throws Exception {
		CustomUserDetails 					userDetails 		= null;
		List<RenewalReminderDto>			renewalList			= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(object.getBoolean("renewalHistory") ) {
				renewalList	= listVehicleRenewalReminder(object.getInt("vid"), userDetails.getCompany_id());
			}else {
				renewalList	= listVehicleActiveRenewalReminder(object.getInt("vid"), userDetails.getCompany_id());
			}
			if(renewalList != null) {
				object.put("renewalList", renewalList);
			}
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			userDetails   = null;
			renewalList   = null;
		}
	}
	
	@Override
	public ValueObject getRenewalDetailsByRenewalId(ValueObject object) throws Exception {
		CustomUserDetails 			userDetails 		= null;
		RenewalReminderDto			renewalDetails		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			renewalDetails = RRBL.GetRenewalReminder(getRenewalReminderById(object.getLong("renewal_id"), userDetails));
			if(renewalDetails != null) {
				object.put("renewalDetails", renewalDetails);
			}
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			userDetails   	 = null;
			renewalDetails   = null;
		}
	}
	
	@Transactional
	@Override
	public ValueObject deleteRenewalReminderById(ValueObject object) throws Exception {
		CustomUserDetails 					userDetails 			= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			RenewalReminderDto renewalReminder = getRenewalReminderById(object.getLong("renewal_id"), userDetails);
			
			RenewalReminderHistory renewalReminderHistory = RRBL.prepareRenewalRemiderHistory(renewalReminder);
			renewalReminderHistory.setCompanyId(userDetails.getCompany_id());
			renewalReminderHistory.setCreatedById(userDetails.getId());
			renewalReminderHistory.setLastModifiedById(userDetails.getId());
			// save the RenewalHistory
			addRenewalReminderHistory(renewalReminderHistory);
			
			object.put("saveRenewalReminderHis", true);
			
			VehicleDto	CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(renewalReminder.getVid(), userDetails.getCompany_id());
			ValueObject	ownerShipObject	= null;
			if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED && renewalReminder.getRenewal_Amount() > 0){
				ownerShipObject	= new ValueObject();
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, renewalReminder.getRenewal_id());
				ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, renewalReminder.getVid());
				ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, renewalReminder.getRenewal_Amount());
				ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
				ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_RENEWAL);
				ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, SQL_DATE_FORMAT.parse(SQL_DATE_FORMAT.format(renewalReminder.getRenewal_payment_Date())));
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Renewal Entry");
				ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Renewal Number : "+renewalReminder.getRenewal_R_Number());
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
				ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, renewalReminder.getCreatedById());
				ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, renewalReminder.getRenewal_Amount());
				
				vehicleAgentIncomeExpenseDetailsService.deleteVehicleAgentIncomeExpense(ownerShipObject);
			}
			java.util.Date currentDate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());
			deleteRenewalReminder(object.getLong("renewal_id"), userDetails.getCompany_id(),userDetails.getId(),toDate );
			
			bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(object.getLong("renewal_id"), ModuleConstant.RENEWAL_REMINDER, userDetails.getCompany_id(),userDetails.getId(),false);
			object.put("deleteRenewalReminder", true);
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			userDetails  	 = null;
		}
	}
	
	@Override
	@Transactional
	public ValueObject getRenewalReminderDetailsOpenList(ValueObject valueObject) throws Exception {
		CustomUserDetails 					userDetails 					= null;
		long 								RenewalReminderCount 			= 0;
		HashMap<String, Object> 			configuration					= null;
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
			Page<RenewalReminder> page = getDeployment_Page_RenewalReminder(valueObject.getShort("status"), valueObject.getInt("pagenumber"), userDetails);
			
			if (page != null) {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
				valueObject.put("configuration", configuration);
				RenewalReminderCount = page.getTotalElements();
			}
			
			List<RenewalReminderDto> pageList = RRBL.Only_Show_ListofRenewal(Find_listRenewalReminder(valueObject.getShort("status"), valueObject.getInt("pagenumber"), userDetails));

			valueObject.put("RenewalReminder", pageList);
			valueObject.put("SelectStatus", valueObject.getShort("status"));
			valueObject.put("SelectPage", valueObject.getInt("pagenumber"));
			valueObject.put("RenewalReminderCount", RenewalReminderCount);
			
			return valueObject;
			
		} catch (Exception e) {
			System.err.println("ERR"+e);
			throw e;
		}finally {
			
		}
	}
	
	@Override
	@Transactional
	public ValueObject getRenewalReminderDetailsOverdueList(ValueObject valueObject) throws Exception {
		CustomUserDetails 					userDetails 					= null;
		long 								RenewalReminderCount 			= 0;
		HashMap<String, Object> 			configuration					= null;
		String								vehicleStatusQuery					= "";
		try {
			java.util.Date	todaysStrDate		= DateTimeUtility.getCurrentDate();
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
			Page<RenewalReminder> page = getDeployment_Page_RenewalOverDue(todaysStrDate, valueObject, userDetails);
			
			if (page != null) {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
				valueObject.put("configuration", configuration);
				RenewalReminderCount = page.getTotalElements();
			}
			
			if(valueObject.getBoolean("vehicleExcludingSurrenderAndInactive",false) && valueObject.getShort("vehicleStatus") != VehicleStatusConstant.VEHICLE_STATUS_INACTIVE) {
				vehicleStatusQuery = "AND V.vStatusId <> 2 AND V.vStatusId <> 3 ";
			}else if(valueObject.getShort("vehicleStatus") == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE){
				vehicleStatusQuery = "AND  V.vStatusId = 2 ";
			}
			valueObject.put("vehicleStatusQuery", vehicleStatusQuery);
			List<RenewalReminderDto> pageList = RRBL.Only_Show_ListofRenewal(getAllOverDueRenewalList(todaysStrDate, valueObject, userDetails));

			valueObject.put("RenewalReminder", pageList);
			valueObject.put("SelectStatus", valueObject.getShort("status"));
			valueObject.put("SelectPage", valueObject.getInt("pagenumber"));
			valueObject.put("RenewalReminderCount", RenewalReminderCount);
			
			return valueObject;
			
		} catch (Exception e) {
			System.err.println("ERR"+e);
			throw e;
		}finally {
			
		}
	}
	
	@Override
	@Transactional
	public ValueObject getRenewalReminderDetailsDueSoonList(ValueObject valueObject) throws Exception {
		CustomUserDetails 					userDetails 					= null;
		long 								RenewalReminderCount 			= 0;
		HashMap<String, Object> 			configuration					= null;
		String								vehicleStatusQuery					= "";
		try {
			java.util.Date	todaysStrDate		= DateTimeUtility.getCurrentDate();
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
			Page<RenewalReminder> page = getDeployment_Page_RenewalDueSoon(todaysStrDate, valueObject, userDetails);
			
			if (page != null) {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
				valueObject.put("configuration", configuration);
				RenewalReminderCount = page.getTotalElements();
			}
			if(valueObject.getBoolean("vehicleExcludingSurrenderAndInactive",false) && valueObject.getShort("vehicleStatus") != VehicleStatusConstant.VEHICLE_STATUS_INACTIVE) {
				vehicleStatusQuery = "AND V.vStatusId <> 2 AND V.vStatusId <> 3 ";
			}else if(valueObject.getShort("vehicleStatus") == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE){
				vehicleStatusQuery = "AND  V.vStatusId = 2 ";
			}
			valueObject.put("vehicleStatusQuery", vehicleStatusQuery);
			
			List<RenewalReminderDto> pageList = RRBL.Only_Show_ListofRenewal(getAllDueSoonRenewalList(todaysStrDate, valueObject, userDetails));

			valueObject.put("RenewalReminder", pageList);
			valueObject.put("SelectStatus", valueObject.getShort("status"));
			valueObject.put("SelectPage", valueObject.getInt("pagenumber"));
			valueObject.put("RenewalReminderCount", RenewalReminderCount);
			
			return valueObject;
			
		} catch (Exception e) {
			System.err.println("ERR"+e);
			throw e;
		}finally {
			
		}
	}
	
	@Override
	public ValueObject serachRenewalReminderByNumber(ValueObject object) throws Exception {
		RenewalReminderDto		renewalDetails		= null;
		try {
			renewalDetails = RRBL.GetRenewalReminder(getRenewalReminder(object.getLong("renewalNumber")));
			if(renewalDetails != null) {
				object.put("RenewalReminder", renewalDetails);
			}
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalDetails   = null;
		}
	}
	
	@Override
	public ValueObject searchRRByDifferentFilter(ValueObject object) throws Exception {
		List<RenewalReminderDto>			renewalDetails		= null;
		try {
			renewalDetails = RRBL.prepareList_Only_Search(SearchRenewalReminder(object.getString("filter")));
			if(renewalDetails != null) {
				object.put("RenewalReminder", renewalDetails);
			}
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalDetails   = null;
		}
	}
	
	@Override
	public ValueObject searchRRDateWise(ValueObject object) throws Exception {
		List<RenewalReminderDto>			renewalDetails		= null;
		String								SearchDate			= null;
		try {
			SearchDate = object.getString("dateRange") + DateTimeUtility.DAY_END_TIME;
			
			renewalDetails = RRBL.Only_Show_ListofRenewal(TodayRenewalReminderList(SearchDate));
			if(renewalDetails != null) {
				object.put("RenewalReminder", renewalDetails);
			}
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalDetails   = null;
		}
	}
	
	@Override
	public ValueObject searchRenRemndReport(ValueObject object) throws Exception {
		int 								vid            			= 0 ;
		int									renewalTypeId			= 0;
		int									renewalSubTypeId		= 0;
		String 								dateRangeFrom 			= "";
		String 								dateRangeTo 			= "";
		ValueObject							dateRange				= null;	
		List<RenewalReminderDto>			renewalDetails			= null;
		short								renewalStatusId			= 0;
		String 								renewalStatus 			= "";
		try {
			vid					= object.getInt("vid", 0);
			renewalTypeId		= object.getInt("typeId",0);
			renewalSubTypeId	= object.getInt("subTypeId",0);
			dateRange			= DateTimeUtility.getStartAndEndDateStr(object.getString("dateRange"),DateTimeUtility.DD_MM_YY,DateTimeUtility.YYYY_MM_DD);
			dateRangeFrom 		= dateRange.getString(DateTimeUtility.FROM_DATE);
			dateRangeTo 		= dateRange.getString(DateTimeUtility.TO_DATE);
			renewalStatusId		= object.getShort("renewalStatusId");
			String Vehicle_registration = "", Renewal_type = "", Renewal_subtype = "", Renewal_to = "", Renewal_from = "";
			
			if (vid > 0) {
				Vehicle_registration = " AND RR.vid='"+vid+"'";
			}
			if (renewalTypeId > 0) {
				Renewal_type = " AND RR.renewalTypeId="+renewalTypeId+"";
			}
			if (renewalSubTypeId > 0) {
				Renewal_subtype = " AND RR.renewal_Subid="+renewalSubTypeId+"";
			}
			if (dateRangeFrom != "" && dateRangeTo != "") {
				Renewal_to = " AND RR.renewal_to between '"+dateRangeFrom+"' AND '"+dateRangeTo+"' ";
			}
			if(renewalStatusId > 0) {
				renewalStatus = " AND RR.renewal_staus_id = "+renewalStatusId+" ";
			}

			String query = " "+Vehicle_registration+" "+Renewal_type+" "+Renewal_subtype+" "+Renewal_to+" "+Renewal_from+" "+renewalStatus+" ";
			
			renewalDetails = RRBL.prepareListofRenewalDto(listRenewalReminder(query,object));
			
			if(renewalDetails != null) {
				object.put("RenewalReminder", renewalDetails);
			}		
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalDetails   = null;
		}
	}
	
	@Override
	@Transactional(readOnly = false)
	public ValueObject reviseRenewalReminderDetails(ValueObject valueObject, MultipartFile[] file) throws Exception {
		RenewalReminderSequenceCounter 				counter 					= null;
		HashMap<String, Object> 					configuration 				= null;
		CustomUserDetails 							userDetails 				= null;
		VehicleDto 									CheckVehicleStatus 			= null;
		RenewalReminder 							renewalReminder 			= null;
		List<RenewalReminderDto> 					renewal 					= null;
		String 										TIDMandatory 				= "";
		boolean 									VEHICLESTATUS 				= false;
		int 										companyId 					= 0;
		long 										userId 						= 0;
		try {
			companyId 					= valueObject.getInt("companyId");
			userId 						= valueObject.getLong("userId");

			userDetails = new CustomUserDetails(companyId, userId);
			valueObject.put("userDetails", userDetails);

			configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.RENEWAL_REMINDER_CONFIG);

			// Check Vehicle Status Current IN ACTIVE OR NOT
			CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("vehicleId"), companyId);

			switch (CheckVehicleStatus.getvStatusId()) {
			case VehicleStatusConstant.VEHICLE_STATUS_ACTIVE:

				VEHICLESTATUS = true;
				break;
			case VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP:

				VEHICLESTATUS = true;
				break;
			case VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE:

				VEHICLESTATUS = true;
				break;

			default:
				VEHICLESTATUS = false;
				break;
			}

			Collection<GrantedAuthority> permission = userDetails.getGrantedAuthoritiesList();

			if (permission.contains(new SimpleGrantedAuthority("ALLOW_ALL_STATUS_RENEWAL"))) {
				VEHICLESTATUS = true;
			}

			if (VEHICLESTATUS) {

				renewalReminder = RRBL.saveRRDetailsFromMobile(valueObject);

				if ((boolean) configuration.get("alwaysApprovedRenewal")) {
					if(valueObject.getBoolean("createApproval", false) == true) {
						renewalReminder.setRenewal_staus_id(RenealReminderStatus.NOT_APPROVED);
					}else {
						renewalReminder.setRenewal_staus_id(RenealReminderStatus.APPROVED);
					}
					renewalReminder.setRenewal_approvedbyId(userDetails.getId());
					renewalReminder.setRenewal_approveddate(new java.util.Date());
				}

				if ((boolean) configuration.get(RenewalReminderConfiguration.SAVE_RENEWAL_WITHOUT_FILE) || file != null) {
					
					if(file != null) {
						if(file.length > 0) {
							renewalReminder.setRenewal_document(true);
						}else {
							renewalReminder.setRenewal_document(false);
						}
					} 

					// Here Checking the Renewal Reminder Details Date From and To Value Details

					String Query = " ( RR.vid =" + renewalReminder.getVid() + " " + " AND RR.renewalTypeId ="
							+ renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
							+ renewalReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_from()
							+ "' between RR.renewal_from AND RR.renewal_to  OR RR.vid=" + renewalReminder.getVid() + " "
							+ " AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
							+ renewalReminder.getRenewal_Subid() + "  AND '" + renewalReminder.getRenewal_to()
							+ "' between RR.renewal_from AND RR.renewal_to ) AND RR.companyId = "
							+ userDetails.getCompany_id() + " AND RR.markForDelete = 0 AND RR.renewalTypeId NOT IN(67, 116) ";

					// show Vehicle Name Renewal Type and Renewal SubType List
					
					if((boolean) configuration.getOrDefault("dateValidation", true))
					renewal = RRBL.prepareListofRenewalDto(Validate_RenewalReminder(Query));

					if (renewal != null && !renewal.isEmpty()) {
						// Checking the Value Of Mandatory Compliance
						for (RenewalReminderDto add : renewal) {
							TIDMandatory += "<span>" + add.getVehicle_registration()
									+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
									+ add.getRenewal_id() + " \" target=\"_blank\" >" + add.getRenewal_type() + " "
									+ add.getRenewal_subtype()
									+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
									+ add.getRenewal_from() + " To " + add.getRenewal_to() + " </span>, <br>";
						}
						valueObject.put("already", TIDMandatory);
						valueObject.put("renewalRemindeAlready", true);

						return valueObject;

					} else {

						List<RenewalReminderDto> renewalReceipt = null;

						// Here Checking the Renewal Reminder Details Receipt Details
						String QueryReceipt = "RR.renewal_receipt='" + renewalReminder.getRenewal_receipt()
								+ "' AND RR.renewalTypeId =" + renewalReminder.getRenewalTypeId()
								+ " AND RR.renewal_Subid =" + renewalReminder.getRenewal_Subid()
								+ " AND RR.companyId = " + userDetails.getCompany_id() + " AND RR.markForDelete = 0";

						// Renewal Reminder Receipt
						if ((boolean) configuration.get("receiptnumbershow")
								&& (boolean) configuration.get("validateDuplicateRefnumber")) {
							renewalReceipt = RRBL.prepareListofRenewalDto(Validate_RenewalReminder(QueryReceipt));
						}

						if (renewalReceipt != null && !renewalReceipt.isEmpty()) {

							// Checking the Value Of Mandatory Compliance
							for (RenewalReminderDto add : renewalReceipt) {
								TIDMandatory += "<span>" + add.getVehicle_registration()
										+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
										+ add.getRenewal_id() + "\" target=\"_blank\">" + add.getRenewal_type() + " "
										+ add.getRenewal_subtype()
										+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
										+ add.getRenewal_from() + " To " + add.getRenewal_to()
										+ " Receipt | Challan  Number " + add.getRenewal_receipt() + " </span>, <br>";
							}
							valueObject.put("ReceiptAlready", TIDMandatory);
							valueObject.put("renewalReceiptAlready", true);

							return valueObject;

						} else {

							/* save Renewal Reminder */

							updateNewRRCreated(renewalReminder.getVid(), renewalReminder.getRenewalTypeId(), renewalReminder.getRenewal_Subid());
							
							counter = renewalReminderSequenceService.findNextRenewal_R_Number(userDetails.getCompany_id());
							if (counter == null) {
								valueObject.put("sequenceNotFound", true);
								return valueObject;
							}
							renewalReminder.setRenewal_R_Number(counter.getNextVal());
							
							addRenewalReminder(renewalReminder);

							ValueObject ownerShipObject = null;
							if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED ){
								ownerShipObject	= new ValueObject();
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, renewalReminder.getRenewal_id());
								ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, renewalReminder.getVid());
								ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, renewalReminder.getRenewal_Amount());
								ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
								ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_RENEWAL);
								ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, userDetails.getCompany_id());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, SQL_DATE_FORMAT.parse(SQL_DATE_FORMAT.format(renewalReminder.getRenewal_dateofpayment())));
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Renewal Entry");
								ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Renewal Number : "+renewalReminder.getRenewal_R_Number());
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
								ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, userDetails.getId());
								ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -renewalReminder.getRenewal_Amount());
								
								VehicleAgentTxnChecker	agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
								vehicleAgentTxnCheckerService.save(agentTxnChecker);
								
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
								ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
								
							}

							org.fleetopgroup.persistence.document.RenewalReminderDocument rewalDoc = new org.fleetopgroup.persistence.document.RenewalReminderDocument();
							
							if(file != null && file.length > 0) {
								
								for(int i=0; i<file.length; i++) {
									rewalDoc.setRenewal_id(renewalReminder.getRenewal_id());
									rewalDoc.setRenewal_R_Number(renewalReminder.getRenewal_R_Number());
									
									byte[] bytes = file[i].getBytes();
									
									rewalDoc.setRenewal_filename(file[i].getOriginalFilename());
									rewalDoc.setRenewal_content(bytes);
									rewalDoc.setRenewal_contentType(file[i].getContentType());

									// when Add Renewal Reminder to file Renewal Reminder History id is null
									rewalDoc.setRenewalHis_id((long) 0);
									rewalDoc.setMarkForDelete(false);
									rewalDoc.setCreatedById(userDetails.getId());
									rewalDoc.setLastModifiedById(userDetails.getId());

									java.util.Date currentDateUpdate = new java.util.Date();
									Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

									/** Set Created Current Date */
									rewalDoc.setCreated(toDate);
									rewalDoc.setLastupdated(toDate);
									rewalDoc.setCompanyId(userDetails.getCompany_id());

									//RenewalReminderService.add_RenewalReminder_Document(rewalDoc);
									documentService.save(rewalDoc);

									// Note: This Update is Document ID Update Renewal Reminder Details
									Update_RenewalReminderDocument_ID_to_RenewalReminder(
											rewalDoc.get_id(), true, renewalReminder.getRenewal_id(),
											userDetails.getCompany_id());
								}
							}

							valueObject.put("saveRenewalReminder", true);
							
							if(valueObject.getBoolean("allowBankPaymentDetails",false) && (PaymentTypeConstant.getPaymentTypeIdList().contains(renewalReminder.getPaymentTypeId()) || renewalReminder.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)){
								ValueObject bankPaymentValueObject=JsonConvertor.toValueObjectFormSimpleJsonString(valueObject.getString("bankPaymentDetails"));
									bankPaymentValueObject.put("bankPaymentTypeId", renewalReminder.getPaymentTypeId());
									bankPaymentValueObject.put("userId",userDetails.getId());
									bankPaymentValueObject.put("companyId",userDetails.getCompany_id());
									bankPaymentValueObject.put("moduleId",renewalReminder.getRenewal_id());
									bankPaymentValueObject.put("moduleNo", renewalReminder.getRenewal_R_Number());
									bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.RENEWAL_REMINDER);
									bankPaymentValueObject.put("amount",renewalReminder.getRenewal_Amount());
									bankPaymentValueObject.put("paidDate", renewalReminder.getRenewal_dateofpayment());
									bankPaymentValueObject.put("remark", "Renewal Revised For Vehicle "+CheckVehicleStatus.getVehicle_registration());
									if(renewalReminder.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)
										cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);	
									else
										bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
								}
							
							valueObject.put("renewalId", renewalReminder.getRenewal_id());

							if (ownerShipObject != null) {
								vehicleAgentIncomeExpenseDetailsService.startThreadForVehicleAgentIncomeExpense(ownerShipObject);
							}

							if (!(boolean) configuration.get("alwaysApprovedRenewal")) {
								/* return new ModelAndView("redirect:/RenewalReminder/1/1.in", model); */
								return valueObject;
							} else {
								/* return new ModelAndView("redirect:/RenewalReminder/2/1.in", model); */
								return valueObject;
							}

						}

					}

				} else {
					valueObject.put("documentIsCompulsory", true);
				}

			}

			return valueObject;

		} catch (Exception e) {
			throw e;
		}

	}
	
	@Transactional
	@Override
	public ValueObject updateRenewalPeriodDetails(ValueObject object) throws Exception {
		RenewalReminder 				renewal 			= null;
		CustomUserDetails				userDetails 		= null;
		String 							TIDMandatory 		= "";
		String[] 						From_TO_Array 		= null;
		try {
			renewal     = new RenewalReminder();
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			String dateRange = object.getString("dateRange");
			From_TO_Array = dateRange.split("  to  ");
			object.put("validityFrom", From_TO_Array[0]);
			object.put("validityTo", From_TO_Array[1]);
			
			renewal.setRenewal_id(object.getLong("renewal_id"));
			java.util.Date date = dateFormat.parse(object.getString("validityFrom"));
			java.sql.Date fromDate = new java.sql.Date(date.getTime());
			renewal.setRenewal_from(fromDate);
			renewal.setRenewal_to(sqlFormatTime.parse(object.getString("validityTo") + DateTimeUtility.DAY_END_TIME));
			
			renewal.setRenewal_timethreshold(object.getInt("timeThreshold",1));
			renewal.setRenewal_periedthreshold(object.getInt("renewalPeriodThreshold",0));
			
			Integer duetimeandperied;
			String  duedate       = object.getString("validityTo");
			Integer timeandperied = (renewal.getRenewal_timethreshold()	* renewal.getRenewal_periedthreshold());

			// timeandperied=3*0 days; or timeandperied=3*7 weeks or timeandperied=3*28 month

			if (timeandperied == 0) {
				duetimeandperied = renewal.getRenewal_timethreshold(); 
			} else {
				duetimeandperied = timeandperied; 
			}

			String reminder_dateof = Change_CurrentDate_To_RenewalDate_SubTrackDate(duedate, duetimeandperied);
			renewal.setDateofRenewal(dateFormat.parse(reminder_dateof));
			
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			renewal.setLastModifiedById(userDetails.getId());
			renewal.setLastupdated(toDate);
			renewal.setCompanyId(userDetails.getCompany_id());
			
			RenewalReminder preReminder	= getRenewalReminderById(renewal.getRenewal_id());
			
			String Query = " ( RR.vid =" + preReminder.getVid() + " " + " AND RR.renewalTypeId ="
					+ preReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
					+ preReminder.getRenewal_Subid() + "  AND '" + renewal.getRenewal_from()
					+ "' between RR.renewal_from AND RR.renewal_to  OR RR.vid=" + preReminder.getVid() + " "
					+ " AND RR.renewalTypeId =" + preReminder.getRenewalTypeId() + " AND RR.renewal_Subid ="
					+ preReminder.getRenewal_Subid() + "  AND '" + renewal.getRenewal_to()
					+ "' between RR.renewal_from AND RR.renewal_to ) AND RR.companyId = "
					+ userDetails.getCompany_id() + " AND RR.markForDelete = 0 ";
			
			List<RenewalReminderDto> renewalreminder = RRBL.prepareListofRenewalDto(Validate_RenewalReminder(Query));
			
			if (renewalreminder != null && !renewalreminder.isEmpty() && !renewalreminder.get(0).getRenewal_id().equals(renewal.getRenewal_id())) {
				
				// Checking the Value Of Mandatory Compliance
				for (RenewalReminderDto add : renewalreminder) {
					TIDMandatory += "<span>" + add.getVehicle_registration()
					+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
					+ add.getRenewal_id() + " \" target=\"_blank\" >" + add.getRenewal_type() + " "
					+ add.getRenewal_subtype()
					+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
					+ add.getRenewal_from() + " To " + add.getRenewal_to() + " </span>, <br>";
				}

				object.put("TIDMandatory", TIDMandatory);
				object.put("renewalRemindeAlready", true);
				return object;
			}
			
			updateRenewalPeriod(renewal);
			object.put("renewalPeriodUpdated", true);
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewal   = null;
		}
	}
	
	public String Change_CurrentDate_To_RenewalDate_SubTrackDate(String duedate, Integer duetimeandperied) {

		// get the date from database is 10-12-2015
		// that date split the date & month & year date[0]=10,
		// date[1]=12,date[2]=2015
		String due_EndDate[] = duedate.split("-");

		// convert string to integer in date
		Integer due_enddate = Integer.parseInt(due_EndDate[0]);

		// convert string to integer in month and one more remove 0 is month why
		// means calendar format is [0-11] only so i am subtract to -1 method
		Integer due_endmonth = (Integer.parseInt(due_EndDate[1].replaceFirst("^0+(?!$)", "")) - 1);

		// convert string to integer in year
		Integer due_endyear = Integer.parseInt(due_EndDate[2]);

		// create new calendar at specific date. Convert to java.util calendar
		// type
		Calendar due_endcalender = new GregorianCalendar(due_endyear, due_endmonth, due_enddate);

		// print date for default value
		// System.out.println("Past calendar : " + due_endcalender.getTime());

		// subtract 2 days from the calendar and the due_time and peried of
		// throsold integer
		due_endcalender.add(Calendar.DATE, -duetimeandperied);
		// System.out.println(duetimeandperied+"days ago: " +
		// due_endcalender.getTime());

		// this format this the [0-11] but real time month format this [1-12]
		Integer month = due_endcalender.get(Calendar.MONTH) + 1;
		// i am change the format to story the database in reminder date
		String reminder_dateof = "" + due_endcalender.get(Calendar.DATE) + "-" + month + "-"
				+ due_endcalender.get(Calendar.YEAR);

		return reminder_dateof;
	}
	
	@Transactional
	@Override
	public ValueObject updateRenewalDocumentsDetails(ValueObject object, MultipartFile[] file) throws Exception {
		CustomUserDetails				userDetails 		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			org.fleetopgroup.persistence.document.RenewalReminderDocument rewalDoc = new org.fleetopgroup.persistence.document.RenewalReminderDocument();
			
			if(file != null && file.length > 0) {
				
				for(int i=0; i<file.length; i++) {
					rewalDoc.setRenewal_id(object.getLong("renewal_id"));
					rewalDoc.setRenewal_R_Number(object.getLong("renewal_Number"));
					
					byte[] bytes = file[i].getBytes();
					rewalDoc.setRenewal_filename(file[i].getOriginalFilename());
					rewalDoc.setRenewal_content(bytes);
					rewalDoc.setRenewal_contentType(file[i].getContentType());

					// when Add Renewal Reminder to file Renewal Reminder History id is null
					rewalDoc.setRenewalHis_id((long) 0);
					rewalDoc.setMarkForDelete(false);
					rewalDoc.setCreatedById(userDetails.getId());
					rewalDoc.setLastModifiedById(userDetails.getId());

					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

					/** Set Created Current Date */
					rewalDoc.setCreated(toDate);
					rewalDoc.setLastupdated(toDate);
					rewalDoc.setCompanyId(userDetails.getCompany_id());

					//RenewalReminderService.add_RenewalReminder_Document(rewalDoc);
					documentService.save(rewalDoc);

					// Note: This Update is Document ID Update Renewal Reminder Details
					Update_RenewalReminderDocument_ID_to_RenewalReminder(
							rewalDoc.get_id(), true, rewalDoc.getRenewal_id(),
							userDetails.getCompany_id());
				}
				
				object.put("renewalDocumentUpdated", true);
			}
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			userDetails   = null;
		}
	}
	
	@Transactional
	@Override
	public ValueObject uploadRenewalCSVFile(ValueObject object, MultipartFile file, HttpServletRequest request) throws Exception {
		CustomUserDetails				userDetails 		= null;
		String 							rootPath			= "";
		File 							dir					= null;
		File 							serverFile			= null;
		List<RenewalType> 				renewalTypes		= null;
		List<RenewalSubTypeDto> 		renewalSubTypes		= null;
		List<PaymentTypeConstant> 		paymentList			= null;
		List<User> 						userHm				= null;
		RenewalReminder 				renewal				= null;
		RenewalReminderSequenceCounter 	counter 			= null;
		List<Vehicle> 					vehicleList			= null;
		String 							importdateFrom 	    = null;
		java.util.Date 					date 			    = null;
		java.sql.Date 					fromDate 		    = null;
		String 							importdateTo		= null;
		java.util.Date 					dateTo 		        = null;
		java.sql.Date 					toDate 		        = null;
		List<RenewalReminderDto> 		renewalReceipt		= null;
		HashMap<String, Object>  		configuration		= null;
		int 							CountSuccess 		= 0;
		int 							CountDuplicate 		= 0;
		String 							TIDMandatory 		= "";
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			rootPath 		= request.getSession().getServletContext().getRealPath("/");
			dir 			= new File(rootPath + File.separator + "uploadedfile");
			
			if (!dir.exists()) {
				dir.mkdirs();
			}
			serverFile 		= new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
			
			try {
				try (InputStream is = file.getInputStream();
					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
					int i;
					// write file to server
						while ((i = is.read()) != -1) {
							stream.write(i);
						}
						stream.flush();
					}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String[] nextLine;
			try {
				// read file CSVReader(fileReader, ';', '\'', 1) means using separator ; and using single quote ' . Skip first line when read
				
				renewalTypes 		= RenewalTypeService.findAllByCompanyId(userDetails.getCompany_id());
				renewalSubTypes 	= RenewalSubTypeService.findAllByCompanyId(userDetails.getCompany_id());
				paymentList 		= PaymentTypeConstant.getPaymentTypeList();
				userHm 				= userService.getUserList(userDetails.getCompany_id());
				
				try (FileReader fileReader = new FileReader(serverFile);
						CSVReader reader = new CSVReader(fileReader, ';', '\'', 1);) {
						while ((nextLine = reader.readNext()) != null) {
							
							renewal = new RenewalReminder();
							for (int i = 0; i < nextLine.length; i++) {
								try {
									String[] importRenewal = nextLine[i].split(",");
									try {
										String Query = "AND V.vehicle_registration ='" + importRenewal[0]
												+ "' AND V.company_Id = " + userDetails.getCompany_id() + " ";
										
										vehicleList = VBL.prepareListofDto(vehicleService.listVehicleReportByVGPermision(Query));
										
											if (vehicleList != null && !vehicleList.isEmpty()) {
												for (Vehicle vehicleDto : vehicleList) {
													renewal.setVid(vehicleDto.getVid());
													break;
												}
												if (renewalTypes != null && !renewalTypes.isEmpty()) {
													for (RenewalType renewalType : renewalTypes) {
														if (renewalType.getRenewal_Type().trim().equalsIgnoreCase(importRenewal[1].trim())) {
															renewal.setRenewalTypeId(renewalType.getRenewal_id());
															break;
														}
													}
												}
												if (renewalSubTypes != null && !renewalSubTypes.isEmpty()) {
													for (RenewalSubTypeDto renewalSubType : renewalSubTypes) {
														if (renewalSubType.getRenewal_SubType().trim().equalsIgnoreCase(importRenewal[2].trim())) {
															renewal.setRenewal_Subid(renewalSubType.getRenewal_Subid());
															break;
														}
													}
												}
												importdateFrom 		= new String(importRenewal[3]);
												date 				= dateFormat.parse(importdateFrom.replace('/', '-'));
												fromDate 			= new java.sql.Date(date.getTime());
												renewal.setRenewal_from(fromDate);
			
												importdateTo 	= new String(importRenewal[4]);
												dateTo 			= dateFormat.parse(importdateTo.replace('/', '-'));
												toDate 			= new java.sql.Date(dateTo.getTime());
												renewal.setRenewal_to(toDate);
			
												String importdatePay = new String(importRenewal[5]);
												
												if(importdatePay != null && !importdatePay.trim().equalsIgnoreCase("")) {
													
													java.util.Date 	datePay 	= dateFormat.parse(importdatePay.replace('/', '-'));
													java.sql.Date 	DateofPay 	= new java.sql.Date(datePay.getTime());
													renewal.setRenewal_dateofpayment(DateofPay);
												}
												
												renewal.setRenewal_receipt(importRenewal[6]);
												renewal.setRenewal_Amount(Double.parseDouble(importRenewal[7]));
												
												if(importRenewal.length > 8) {
													if (paymentList != null && !paymentList.isEmpty()) {
														for (PaymentTypeConstant constant : paymentList) {
															if (constant.getPaymentTypeName().equalsIgnoreCase(importRenewal[8])) {
																renewal.setPaymentTypeId(constant.getPaymentTypeId());
																break;
															}
														}
													}
													
												}
												
												if(importRenewal.length > 9)
													renewal.setRenewal_PayNumber(importRenewal[9]);
												if(importRenewal.length > 10)
													renewal.setRenewal_authorization(importRenewal[10]);
												if(importRenewal.length > 11)
													renewal.setRenewal_number(importRenewal[11]);
												if(importRenewal.length > 12) {
													
													for(User user : userHm) {
														if(importRenewal[12].equalsIgnoreCase(user.getFirstName()))
															renewal.setRenewal_paidbyId(user.getId());
														break;
													}
													
												}
												if(importRenewal.length > 13)
												renewal.setRenewal_timethreshold(Integer.parseInt(importRenewal[13]));
												renewal.setRenewal_periedthreshold(0);
												counter = renewalReminderSequenceService.findNextRenewal_R_Number(userDetails.getCompany_id());
												
												if (counter == null) {
													object.put("sequenceNotFound", true);
													return object;
												}
												
												long renewal_R_Number = counter.getNextVal();
												renewal.setRenewal_R_Number(renewal_R_Number);
												renewal.setCompanyId(userDetails.getCompany_id());
												String 	duedate 		= importRenewal[4];
												Integer duetimeandperied;
												Integer timeandperied 	= 0;
												if(importRenewal.length > 13)
													timeandperied = Integer.parseInt(importRenewal[13]);
			
												if (timeandperied == 0) {
													duetimeandperied = 3; // 3 days
												} else {
													duetimeandperied = timeandperied; // 21
												}
			
												String reminder_dateof = Change_CurrentDate_To_RenewalDate_SubTrackDate(duedate,duetimeandperied);
			
												renewal.setRenewal_staus_id(RenealReminderStatus.NOT_APPROVED);
												renewal.setDateofRenewal(dateFormat.parse(reminder_dateof));	
												renewal.setRenewal_document(false);
												renewal.setRenewal_document_id((long) 0);
												renewal.setMarkForDelete(false);
												renewal.setCreatedById(userDetails.getId());
												renewal.setLastModifiedById(userDetails.getId());
												renewal.setCompanyId(userDetails.getCompany_id());
			
												java.util.Date currentDateUpdate = new java.util.Date();
												Timestamp toDatet = new java.sql.Timestamp(currentDateUpdate.getTime());
												renewal.setCreated(toDatet);
												renewal.setLastupdated(toDatet);
			
												String Query1 = "( RR.vid =" + renewal.getVid() + " " + " AND RR.renewalTypeId ="
														+ renewal.getRenewalTypeId() + " AND RR.renewal_Subid ="
														+ renewal.getRenewal_Subid() + " AND '" + renewal.getRenewal_from()
														+ "' between RR.renewal_from AND RR.renewal_to  OR " + " RR.vid ="
														+ renewal.getVid() + " " + " AND RR.renewalTypeId ="
														+ renewal.getRenewalTypeId() + " AND RR.renewal_Subid ="
														+ renewal.getRenewal_Subid() + "  AND '" + renewal.getRenewal_to()
														+ "' between RR.renewal_from AND RR.renewal_to ) AND RR.companyId = "
														+ userDetails.getCompany_id() + " AND RR.markForDelete = 0 ";
												List<RenewalReminderDto> renewalList = RRBL.prepareListofRenewalDto(
														Validate_RenewalReminder(Query1));
												
												
												if (renewalList != null && !renewalList.isEmpty()) {
													try {
														for (RenewalReminderDto add : renewalList) {
															TIDMandatory += "<span>" + add.getVehicle_registration()
																	+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
																	+ add.getRenewal_id() + "\" target=\"_blank\">"
																	+ add.getRenewal_type() + " " + add.getRenewal_subtype()
																	+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
																	+ add.getRenewal_from() + " To " + add.getRenewal_to()
																	+ " </span>, <br>";
														}
													} catch (Exception e) {
														logger.error("Renewal Reminder Already:", e);
													}
													object.put("already", TIDMandatory);
													object.put("renewalRemindeAlready", true);
													
													return object;
			
												} else {
													if(renewal.getRenewal_receipt() != null && !renewal.getRenewal_receipt().trim().equalsIgnoreCase("")) {
														String QueryReceipt = "RR.renewal_receipt='" + renewal.getRenewal_receipt()
														+ "' AND RR.renewalTypeId =" + renewal.getRenewalTypeId()
														+ " AND RR.renewal_Subid = " + renewal.getRenewal_Subid()
														+ " AND RR.companyId = " + userDetails.getCompany_id()
														+ " AND RR.markForDelete = 0 ";
														
														if((boolean)configuration.get("receiptnumbershow") && (boolean)configuration.get("validateDuplicateRefnumber")) {
															renewalReceipt 		= Validate_RenewalReminder(QueryReceipt);
														}
			
												if (renewalReceipt != null && !renewalReceipt.isEmpty()) {
													try {
														try {
															for (RenewalReminderDto add : renewalReceipt) {
																TIDMandatory += "<span>" + add.getVehicle_registration()
																		+ " Compliance <a href=\"../../showRenewalReminder.in?renewal_id="
																		+ add.getRenewal_id() + "\" target=\"_blank\">"
																		+ add.getRenewal_type() + " " + add.getRenewal_subtype()
																		+ "  <i class=\"fa fa-external-link\"></i></a> is Available On "
																		+ add.getRenewal_from() + " To " + add.getRenewal_to()
																		+ " Receipt | Challan  Number "
																		+ add.getRenewal_receipt() + " </span>, <br>";
															}
														} catch (Exception e) {
															logger.error("Renewal Receipt Already:", e);
														}
														
													} catch (Exception e) {
														logger.error("Renewal Receipt Already:", e);
													}
													object.put("ReceiptAlready", TIDMandatory);
													object.put("renewalReceiptAlready", true);
													return object;
			
												}else {
													try {
														updateNewRRCreated(renewal.getVid(), renewal.getRenewalTypeId(), renewal.getRenewal_Subid());
														addRenewalReminder(renewal);
														CountSuccess++;
		
													} catch (final Exception e) {
														System.err.println("Duplicate "+e);
														++CountDuplicate;
														String Duplicate = "Vehicle =" + importRenewal[0];
														object.put("CountDuplicate", CountDuplicate);
														object.put("Duplicate", Duplicate);
														object.put("importSaveAlreadyError", true);
														
														return object;
													}
												}
												} else {
			
														try {
															updateNewRRCreated(renewal.getVid(), renewal.getRenewalTypeId(), renewal.getRenewal_Subid());
															addRenewalReminder(renewal);
															CountSuccess++;
			
														} catch (final Exception e) {
															System.err.println("Duplicate Exception "+e);
															++CountDuplicate;
															String Duplicate = "Vehicle =" + importRenewal[0];
															object.put("CountDuplicate", CountDuplicate);
															object.put("Duplicate", Duplicate);
															object.put("importSaveAlreadyError", true);
															
															return object;
														}
			
													}
			
												}
			
											} // close if
										else {
											++CountDuplicate;
											String Duplicate = "Vehicle = " + importRenewal[0];
											object.put("CountDuplicate", CountDuplicate);
											object.put("Duplicate", Duplicate);
											object.put("importSaveAlreadyError", true);
											
											return object;
										}
		
									} catch (Exception e) {
										logger.error("Renewal Reminder:", e);
									}
		
								} catch (Exception e) { 
									e.printStackTrace();
									System.err.println("Exception dsad "+e);
									object.put("importSaveError", true);
		
									return object;
								}
		
							} 
		
						}
				}
			} catch (Exception e) {
				System.out.println("error while reading csv and put to db : " + e.getMessage());
				object.put("importSaveError", true);
	
				return object;
			}
			
			object.put("CountSuccess", CountSuccess);
			object.put("importSave", true);
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			userDetails   = null;
		}
	}
	
	@Override
	public ValueObject getVehicleWiseRenewalReminder(ValueObject object) throws Exception {
		CustomUserDetails 					userDetails 		= null;
		List<RenewalReminderDto>			renewalList			= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
			
			renewalList	= RRBL.Only_Show_ListofRenewalDto(listVehicleRenewalReminder(object.getInt("vid",0), ft.format(currentDate)+DateTimeUtility.DAY_END_TIME, userDetails.getCompany_id()));
			if(renewalList != null) {
				object.put("renewalList", renewalList);
			}
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			userDetails   = null;
			renewalList   = null;
		}
	}
	
	@Override
	public ValueObject getRenewalReminderHistoryByVehicle(ValueObject object) throws Exception {
		List<RenewalReminderHistoryDto>			renewalList			= null;
		try {
			renewalList	= RRBL.prepareListofRenewalHistoryDto(listRenewalReminderHistory(object.getInt("vid")));
			if(renewalList != null) {
				object.put("renewalList", renewalList);
			}
			
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalList   = null;
		}
	}
	
	@Transactional
	public ValueObject getRRListByDate(ValueObject valueObject) throws Exception {
		CustomUserDetails 					userDetails 					= null;
		long 								RenewalReminderCount 			= 0;
		HashMap<String, Object> 			configuration					= null;
	
		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG);
			Page<RenewalReminder> page = getDeploymentPageRenewalReminderByDate(valueObject);
			
			
			if (page != null) {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
				valueObject.put("configuration", configuration);
				RenewalReminderCount = page.getTotalElements();
			}
			
			List<RenewalReminderDto> pageList = RRBL.Only_Show_ListofRenewal(getRRListDetailsByDate(valueObject));
			valueObject.put("RenewalReminder", pageList);
			valueObject.put("date", valueObject.getString("date"));
			valueObject.put("dateStatus", valueObject.getString("dateStatus"));
			valueObject.put("count", valueObject.getLong("count"));
			valueObject.put("SelectStatus", valueObject.getShort("status"));
			valueObject.put("SelectPage", valueObject.getInt("pagenumber"));
			valueObject.put("RenewalReminderCount", RenewalReminderCount);
			
			return valueObject;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
			
			
	}
	
	
	
	
	@Transactional
	public List<RenewalReminderDto> getRRListDetailsByDate(ValueObject object) throws Exception {
		try {
			TypedQuery<Object[]> queryt = null;
			if (!companyConfigurationService.getVehicleGroupWisePermission(object.getInt("companyId"),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, VG.vGroup, R.newRRCreated,VS.vStatus"
								+ ",R.ignored,RT.allowToAvoid FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD + " "
								+ " INNER JOIN VehicleStatus VS ON VS.sid = V.vStatusId"
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " WHERE R.companyId = " + object.getInt("companyId") + " AND R.ignored = 0 AND R.newRRCreated = 0 AND R.renewal_to between '" + object.getString("startDate")+DateTimeUtility.DAY_START_TIME + "' AND '" + object.getString("endDate")+DateTimeUtility.DAY_END_TIME  + "'  AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			} else {

				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, RRA.renewalApproval_Number, VG.vGroup, R.newRRCreated,VS.vStatus"
								+ ",R.ignored,RT.allowToAvoid FROM RenewalReminder AS R"
								+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid AND V.vStatusId <> "+VehicleStatusConstant.VEHICLE_STATUS_SOLD + " "
								+ " INNER JOIN VehicleStatus VS ON VS.sid = V.vStatusId"
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "
								+ object.getLong("userId") + "" + " WHERE  R.companyId = " + object.getInt("companyId") + " AND R.ignored = 0 AND R.newRRCreated = 0 "
								+ " AND R.renewal_to between '" + object.getString("startDate")+DateTimeUtility.DAY_START_TIME + "' AND '" + object.getString("endDate")+DateTimeUtility.DAY_END_TIME + "' AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
						Object[].class);
			}
			queryt.setFirstResult((object.getInt("pagenumber") - 1) * PAGE_SIZE);
			queryt.setMaxResults(PAGE_SIZE);
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();
					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_from_Date((Date) result[5]);
					renewal.setRenewal_To_Date((Date) result[6]);
					if (result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					renewal.setRenewal_Amount((Double) result[8]);
					renewal.setRenewal_staus_id((short) result[9]);
					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
					renewal.setRenewalAproval_Number((Long) result[14]);
					renewal.setVehicleGroup((String) result[15]);
					renewal.setNewRRCreated((boolean) result[16]);
					renewal.setVehicleStatus((String) result[17]);
					renewal.setIgnored((boolean)result[18]);
					renewal.setAllowToIgnored((boolean) result[19]);
					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	/** This Page get Vehicle table to get pagination values */
	@Transactional
	public Page<RenewalReminder> getDeploymentPageRenewalReminderByDate(ValueObject object) throws Exception {
		PageRequest request = new PageRequest(object.getInt("pagenumber") - 1, PAGE_SIZE);
		Timestamp startDate = DateTimeUtility.getTimeStamp(object.getString("startDate")+DateTimeUtility.DAY_START_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
		Timestamp endDate = DateTimeUtility.getTimeStamp(object.getString("endDate")+DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
		sqlFormatTime.format(startDate);
		
		if (!companyConfigurationService.getVehicleGroupWisePermission(object.getInt("companyId"),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				return renewalReminderDao.getDeploymentPageRenewalReminderByDate(startDate , endDate , object.getInt("companyId"), request);
			} else {
				return renewalReminderDao.getDeploymentPageRenewalReminderByDate(startDate ,endDate, object.getInt("companyId"),
						object.getInt("userId"), request);
			}
	
	}
	
	@Override
	public List<RenewalReminderDto> listVehicleActiveRenewalReminder(Integer vid, Integer companyId) {
		TypedQuery<Object[]> queryt = null;
		queryt = entityManager.createQuery(
				"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
						+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
						+ ", R.renewal_R_Number, RRA.renewalApproval_Number, R.renewal_receipt, R.paymentTypeId, R.renewal_PayNumber,"
						+ " R.renewal_authorization, R.renewal_number, R.renewal_dateofpayment, U.firstName, R.renewal_timethreshold,"
						+ " R.renewal_periedthreshold, U2.firstName, R.renewal_approveddate, R.renewal_approvedComment,"
						+ " U3.email, R.created, U4.email, R.lastupdated, R.renewalTypeId, R.renewal_Subid,R.ignored,R.companyId FROM RenewalReminder AS R"
						+ " LEFT JOIN RenewalReminderApproval RRA ON RRA.renewalApproval_id = R.renewal_approvedID "
						+ " INNER JOIN Vehicle V ON V.vid = R.vid"
						+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
						+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
						+ " LEFT JOIN User U  ON U.id  = R.renewal_paidbyId"
						+ " LEFT JOIN User U2 ON U2.id = R.renewal_approvedbyId"
						+ " LEFT JOIN User U3 ON U3.id = R.createdById"
						+ " LEFT JOIN User U4 ON U4.id = R.lastModifiedById" + " WHERE R.vid =" + vid
						+ " AND R.companyId = " + companyId + " AND R.renewal_to >= '"+DateTimeUtility.getCurrentTimeStamp()+"' AND R.markForDelete = 0 ORDER BY R.renewal_id desc",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<RenewalReminderDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<RenewalReminderDto>();
			RenewalReminderDto renewal = null;
			for (Object[] result : results) {
				renewal = new RenewalReminderDto();

				renewal.setRenewal_id((Long) result[0]);
				renewal.setVid((Integer) result[1]);
				renewal.setVehicle_registration((String) result[2]);
				renewal.setRenewal_type((String) result[3]);
				renewal.setRenewal_subtype((String) result[4]);
				renewal.setRenewal_from_Date((Date) result[5]);
				renewal.setRenewal_from(dateFormat.format(renewal.getRenewal_from_Date()));
				renewal.setRenewal_To_Date((Date) result[6]);
				renewal.setRenewal_to(dateFormat.format(renewal.getRenewal_To_Date()));
				if (result[7] != null)
					renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
				renewal.setRenewal_Amount((Double) result[8]);
				renewal.setRenewal_staus_id((short) result[9]);
				renewal.setRenewal_document((boolean) result[10]);
				renewal.setRenewal_document_id((Long) result[11]);
				renewal.setRenewal_approvedID((Long) result[12]);
				renewal.setRenewal_R_Number((Long) result[13]);
				renewal.setRenewalAproval_Number((Long) result[14]);
				renewal.setRenewal_receipt((String) result[15]);
				renewal.setPaymentTypeId((short) result[16]);
				renewal.setRenewal_PayNumber((String) result[17]);
				renewal.setRenewal_authorization((String) result[18]);
				renewal.setRenewal_number((String) result[19]);
				renewal.setRenewal_payment_Date((Date) result[20]);
				renewal.setRenewal_paidby((String) result[21]);
				renewal.setRenewal_timethreshold((Integer) result[22]);
				renewal.setRenewal_periedthreshold((Integer) result[23]);
				renewal.setRenewal_approvedby((String) result[24]);
				renewal.setRenewal_approveddate((Date) result[25]);
				renewal.setRenewal_approvedComment((String) result[26]);
				renewal.setCreatedBy((String) result[27]);
				renewal.setCreatedOn((Date) result[28]);
				renewal.setLastModifiedBy((String) result[29]);
				renewal.setLastupdatedOn((Date) result[30]);
				renewal.setRenewalTypeId((Integer) result[31]);
				renewal.setRenewal_Subid((Integer) result[32]);
				renewal.setIgnored((boolean) result[33]);
				renewal.setCompanyId((Integer) result[34]);

				Dtos.add(renewal);
			}
		}
			else {
			return null;
		}
		
		return Dtos;
		
	}


	@SuppressWarnings("unchecked")
	@Override
		public ValueObject getMandatoryRenewalPendingReport(ValueObject valueObject) throws Exception {		
			ValueObject					valueOutObject;
			ValueObject					tableConfig					= null;
			List<RenewalReminder>		mandotoryRenewalPendingList = null;
			CustomUserDetails 			userDetails					= null;
		/* Map<Integer, Vehicle> vehicleHM = null; */
			try {
					userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
					tableConfig						= new ValueObject();
					mandotoryRenewalPendingList		= new ArrayList<RenewalReminder>();
					
					tableConfig.put("companyId", userDetails.getCompany_id());
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.MANDATORY_RENEWAL_PENDING_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);	
					
					valueOutObject = new ValueObject();
					valueOutObject.put("tableConfig", tableConfig.getHtData());
					valueOutObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
					valueObject.put("companyId", userDetails.getCompany_id());
				
					valueObject = getRenewalMandatoryList(valueObject);
					

					
					
					mandotoryRenewalPendingList = (List<RenewalReminder>) valueObject.get("mandatoryList");
					
					valueOutObject.put("mandotoryRenewalPendingList", mandotoryRenewalPendingList);
					
				
					
				return valueOutObject;
			} catch (Exception e) {
				throw e;
			}finally {
				tableConfig			= null;
			}
		

}
	public ValueObject searchRenRemndReportForMob(ValueObject object) throws Exception{
		int 								vid            			= 0 ;
		int									renewalTypeId			= 0;
		int									renewalSubTypeId		= 0;
		String 								dateRangeFrom 			= "";
		String 								dateRangeTo 			= "";
		ValueObject							dateRange				= null;	
		List<RenewalReminderDto>			renewalDetails			= null;
		short								renewalStatusId			= 0;
		String 								renewalStatus 			= "";
		String                              query                   = "";
		try {
			vid					= object.getInt("vid", 0);
			renewalTypeId		= object.getInt("typeId",0);
			renewalSubTypeId	= object.getInt("subTypeId",0);
			renewalStatusId		= object.getShort("renewalStatusId");
			dateRange			= DateTimeUtility.getStartAndEndDateStr(object.getString("dateRange"));
			if(dateRange!=null){
				dateRangeFrom 		= dateRange.getString(DateTimeUtility.FROM_DATE,"");
				dateRangeTo 		= dateRange.getString(DateTimeUtility.TO_DATE,"");
			}
			
			String Vehicle_registration = "", Renewal_type = "", Renewal_subtype = "", Renewal_to = "", Renewal_from = "";
			
			if (vid > 0) {
				Vehicle_registration = " AND RR.vid='"+vid+"'";
			}
			if (renewalTypeId > 0) {
				Renewal_type = " AND RR.renewalTypeId="+renewalTypeId+"";
			}
			if (renewalSubTypeId > 0) {
				Renewal_subtype = " AND RR.renewal_Subid="+renewalSubTypeId+"";
			}
			if (dateRangeFrom != "" && dateRangeTo != "") {
				Renewal_to = " AND RR.renewal_to between '"+dateRangeFrom+"' AND '"+dateRangeTo+"' ";
			}
			if(renewalStatusId > 0) {
				renewalStatus = " AND RR.renewal_staus_id = "+renewalStatusId+" ";
			}

			if(dateRangeFrom != "" && dateRangeTo != ""){
				query = " "+Vehicle_registration+" "+Renewal_type+" "+Renewal_subtype+" "+Renewal_to+" "+Renewal_from+" "+renewalStatus+" ";
			}else{
				query = " "+Vehicle_registration+" "+Renewal_type+" "+Renewal_subtype+" "+renewalStatus+" ";
			}
		    
			renewalDetails = RRBL.prepareListofRenewalDto(listRenewalReminder(query,object));
			
			if(renewalDetails != null) {
				object.put("RenewalReminder", renewalDetails);
			}		
				
			return object;
			
		}catch(Exception e) {
			throw e;
		} finally {
			renewalDetails   = null;
		}
	}
	
	@Override
	public List<RenewalReminderDto> getUserWiseActivityRRData(String queryStr,String innerQuery) throws Exception {
		try {
			
			TypedQuery<Object[]> queryt = null;
			
				queryt = entityManager.createQuery(
						"SELECT R.renewal_id, R.vid, V.vehicle_registration, RT.renewal_Type, RST.renewal_SubType, "
								+ "R.renewal_from, R.renewal_to, R.dateofRenewal, R.renewal_Amount, R.renewal_staus_id, R.renewal_document, R.renewal_document_id, R.renewal_approvedID "
								+ ", R.renewal_R_Number, VG.vGroup, R.newRRCreated, VS.vStatus, R.created ,R.lastupdated,"
								+ "R.markForDelete,R.createdById,R.lastModifiedById,U.firstName, U.lastName ,U.id"
								+ " FROM RenewalReminder AS R"
								+ " "+innerQuery+" "
								+ " INNER JOIN Vehicle V ON V.vid = R.vid "
								+ " INNER JOIN VehicleStatus VS ON VS.sid = V.vStatusId "
								+ " INNER JOIN VehicleGroup VG ON VG.gid = V.vehicleGroupId"
								+ " INNER JOIN RenewalType RT ON RT.renewal_id = R.renewalTypeId"
								+ " INNER JOIN RenewalSubType RST ON RST.renewal_Subid = R.renewal_Subid "
								+ " WHERE "+queryStr+" ",
						Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<RenewalReminderDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<RenewalReminderDto>();
				RenewalReminderDto renewal = null;
				for (Object[] result : results) {
					renewal = new RenewalReminderDto();
					renewal.setRenewal_id((Long) result[0]);
					renewal.setVid((Integer) result[1]);
					renewal.setVehicle_registration((String) result[2]);
					renewal.setRenewal_type((String) result[3]);
					renewal.setRenewal_subtype((String) result[4]);
					renewal.setRenewal_from_Date((Date) result[5]);
					renewal.setRenewal_To_Date((Date) result[6]);
					if (result[7] != null)
						renewal.setRenewal_dateofRenewal(dateFormat.format(result[7]));
					if (result[8] != null) {
						renewal.setRenewal_Amount(Double.parseDouble(toFixedTwo.format(result[8])));
					}else {
						renewal.setRenewal_Amount(0.0);
					}
					renewal.setRenewal_staus_id((short) result[9]);
					renewal.setRenewal_document((boolean) result[10]);
					renewal.setRenewal_document_id((Long) result[11]);
					renewal.setRenewal_approvedID((Long) result[12]);
					renewal.setRenewal_R_Number((Long) result[13]);
				
					renewal.setVehicleGroup((String) result[14]);
					renewal.setNewRRCreated((boolean) result[15]);
					renewal.setVehicleStatus((String) result[16]);
					if(result[17] != null)
					renewal.setCreated(dateFormat.format(result[17]));
					if(result[18] != null)
					renewal.setLastupdated(dateFormat.format(result[18]));
					renewal.setMarkForDelete((boolean) result[19]);
					if(!renewal.isMarkForDelete()) {
						renewal.setRenewal_number("<a target=\"_blank\" style=\"color: blue; background: #ffc;\"  href=\"showRenewalReminderDetails?renewalId="+renewal.getRenewal_id()+" \"> RR-"+renewal.getRenewal_R_Number()+" </a>");
					}else {
						renewal.setRenewal_number("<a  style=\"color: red; background: #ffc;\"  href=\"#\" data-toggle=\"tip\" data-original-title=\"Deleted Data\"> RR-"+renewal.getRenewal_R_Number()+" </a>");
					}
					renewal.setCreatedById((Long)result[20]);
					renewal.setLastModifiedById((Long)result[21]);
					renewal.setUserName(result[22]+" "+result[23]);
					renewal.setUserId((Long) result[24]);
					Dtos.add(renewal);
				}
			}
			return Dtos;

		} catch (Exception e) {
			System.err.println(e);
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject removeRenewalDocument(ValueObject object) throws Exception {
		List<org.fleetopgroup.persistence.document.RenewalReminderDocument>  documents = null;
		Long	docId	= (long) 0;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
			query.addCriteria(Criteria.where("_id").is(object.getLong("documentId",0))
					.and("companyId").is(userDetails.getCompany_id()));
			mongoTemplate.remove(query, org.fleetopgroup.persistence.document.RenewalReminderDocument.class);
			
			documents = documentService.getRenewalDocumentsByRenewalId(object.getLong("renewal_id",0), userDetails.getCompany_id());
			
			if(documents != null && !documents.isEmpty()) {
				docId = documents.get(0).get_id();
			}else {
				docId	= null;
			}
			Update_RenewalReminderDocument_ID_to_RenewalReminder(
					docId, true, object.getLong("renewal_id",0),
					userDetails.getCompany_id());
			
			object.put("deleted", true);
		} catch (Exception e) {
			throw e;
		}
		return object;
	}
	@Transactional
	public ValueObject ignoreRenewalReminder(ValueObject object) {
		CustomUserDetails userDetails = null;
		try {
			long renewalId = object.getLong("renewalId", 0);
			userDetails= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(renewalId > 0) {
				updateIgnoreRRstatus(renewalId, userDetails.getCompany_id(), userDetails.getId(), DateTimeUtility.getCurrentTimeStamp(),object.getString("igRemark", ""));
				object.put("ignored",true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}

@Transactional
public void updateIgnoreRRstatus (Long renewalReminder,Integer companyId , Long userId , Timestamp todate,String remark) {
		renewalReminderDao.ignoreRenewalReminder(renewalReminder, companyId, userId, todate, remark);
	}

@Override
public ValueObject getRenewalReminderReport(ValueObject object) {
	ExecutorService ex=Executors.newFixedThreadPool(2);

	ex.execute(()->getRenewalReminderList(object));
	
	ex.execute(()->{
			try {
				ValueObject tableConfig				= new ValueObject();
				tableConfig.put("companyId",object.getInt("companyId",0));
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.RENEWAL_REMINDER_REPORT);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				object.put("tableConfig", tableConfig.getHtData());
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		try {
	
		ex.shutdown();
		ex.awaitTermination(17, TimeUnit.SECONDS);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}finally {
		ex.shutdownNow();
	}
	
	return object;
}
public void getRenewalReminderList(ValueObject object) {
	int 		companyId 		= object.getInt("companyId",0);
	String 		fromDate 		= object.getString("fromDate",null);
	String		toDate 			= object.getString("toDate", null);
	long 		vType 			= object.getLong("vTypeId",0);
	long 		vLocation		= object.getLong("vLocation",0);
	long 		vid				= object.getLong("vid",0);
	int 		renewalTypeId	= object.getInt("renewalTypeId",0);
	int 		renewalsubTypeId= object.getInt("renewalsubTypeId",0);
	StringBuilder query= new StringBuilder("");
		if(vid > 0)
			query.append(" V.vid = "+vid+" AND ");
		if(vType > 0)
			query.append(" V.vehicleTypeId = "+vType+" AND ");
		if(vLocation > 0)
			query.append(" V.branchId ="+vLocation+" AND ");
		if(fromDate != null && toDate != null)
			query.append(" R.renewal_to BETWEEN '"+fromDate+DateTimeUtility.DAY_START_TIME+"' AND '"+toDate+DateTimeUtility.DAY_END_TIME+"'  AND ");
		if(renewalTypeId >0)
			query.append(" R.renewalTypeId ="+renewalTypeId+" AND ");
		if(renewalsubTypeId >0)
			query.append(" R.renewal_Subid ="+renewalsubTypeId+" AND ");
		try {
			List<RenewalReminderDto> list=	getRenewalReminderTableListBetweenDates(query.toString(), companyId);
			list=RRBL.Only_Show_ListofRenewalDto(list);
			object.put("list", list);
		}catch(Exception e) {
			e.printStackTrace();
		}
}
	@SuppressWarnings("null")
	@Override
	@Transactional
    public long getRenewalReminderListBeforeFiveDaysDue(Integer companyId, Integer threshold) throws Exception {
		Long 								count							= 0L;
		try
		{
				TypedQuery<Long> query = null; 
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_MONTH, threshold);
				Date targetDate = calendar.getTime();
				query = entityManager.createQuery("SELECT COUNT(renewal_id) "
					    + "FROM RenewalReminder "
					    + "WHERE DATE(renewal_to) = :targetDate "
					    + "AND companyId = :companyId  "
					    + "AND markForDelete = 0", Long.class);
				query.setParameter("targetDate", targetDate);
				query.setParameter("companyId", companyId);
			    count = query.getSingleResult();
		}
		catch(Exception e){	
		}
    	return count;
    }
}
