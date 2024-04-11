package org.fleetopgroup.persistence.service;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
/**
 * @author fleetop
 *
 */
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.AccidentStatus;
import org.fleetopgroup.constant.DealerServiceEntriesConstant;
import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.ServiceEntriesType;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.constant.TyreAssignmentConstant;
import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.constant.VehicleOwnerShip;
import org.fleetopgroup.constant.VehicleStatusConstant;
import org.fleetopgroup.constant.WorkOrdersType;
import org.fleetopgroup.persistence.bl.DealerServiceEntriesBL;
import org.fleetopgroup.persistence.bl.IssuesBL;
import org.fleetopgroup.persistence.bl.LabourBL;
import org.fleetopgroup.persistence.bl.MasterPartsBL;
import org.fleetopgroup.persistence.bl.PartWarrantyDetailsBL;
import org.fleetopgroup.persistence.bl.PendingVendorPaymentBL;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleOdometerHistoryBL;
import org.fleetopgroup.persistence.bl.VehicleProfitAndLossTxnCheckerBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.dao.DealerServiceEntriesLabourRepository;
import org.fleetopgroup.persistence.dao.DealerServiceEntriesPartRepository;
import org.fleetopgroup.persistence.dao.DealerServiceEntriesRepository;
import org.fleetopgroup.persistence.dao.DealerServiceExtraIssueRepository;
import org.fleetopgroup.persistence.dao.DealerServiceRemarkRepository;
import org.fleetopgroup.persistence.dao.DealerServiceReminderHistoryRepository;
import org.fleetopgroup.persistence.dao.DealerServiceReminderRepository;
import org.fleetopgroup.persistence.dao.LabourRepository;
import org.fleetopgroup.persistence.dao.MasterPartsRepository;
import org.fleetopgroup.persistence.dao.PartWarrantyDetailsRepository;
import org.fleetopgroup.persistence.dao.ServiceReminderHistoryRepository;
import org.fleetopgroup.persistence.dao.VendorRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.DealerServiceEntriesDto;
import org.fleetopgroup.persistence.dto.DealerServiceEntriesLabourDto;
import org.fleetopgroup.persistence.dto.DealerServiceEntriesPartDto;
import org.fleetopgroup.persistence.dto.DealerServiceExtraIssueDto;
import org.fleetopgroup.persistence.dto.DealerServiceRemarkDto;
import org.fleetopgroup.persistence.dto.IssuesDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.ServiceReminderDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.AccidentQuotationDetails;
import org.fleetopgroup.persistence.model.DealerServiceEntries;
import org.fleetopgroup.persistence.model.DealerServiceEntriesLabour;
import org.fleetopgroup.persistence.model.DealerServiceEntriesPart;
import org.fleetopgroup.persistence.model.DealerServiceEntriesSequenceCounter;
import org.fleetopgroup.persistence.model.DealerServiceExtraIssue;
import org.fleetopgroup.persistence.model.DealerServiceRemark;
import org.fleetopgroup.persistence.model.DealerServiceReminder;
import org.fleetopgroup.persistence.model.DealerServiceReminderHistory;
import org.fleetopgroup.persistence.model.EmailAlertQueue;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.IssuesTasks;
import org.fleetopgroup.persistence.model.Labour;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.PartWarrantyDetails;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.ServiceReminderHistory;
import org.fleetopgroup.persistence.model.SmsAlertQueue;
import org.fleetopgroup.persistence.model.VehicleAccidentDetails;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.model.VehicleExpenseDetails;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.VendorSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDealerServiceEntriesSequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IDealerServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IEmailAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.IIssuesService;
import org.fleetopgroup.persistence.serviceImpl.IMasterPartsService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IServiceReminderService;
import org.fleetopgroup.persistence.serviceImpl.ISmsAlertQueueService;
import org.fleetopgroup.persistence.serviceImpl.ITyreUsageHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAccidentDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOdometerHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreAssignmentService;
import org.fleetopgroup.persistence.serviceImpl.IVendorSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.web.util.AESEncryptDecrypt;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service("DealerServiceEntriesService")
@Transactional
public class DealerServiceEntriesService implements IDealerServiceEntriesService {
	@Autowired private IDealerServiceEntriesSequenceCounterService 			DSE_SequenceCounterService;
	@Autowired private IVehicleService 										vehicleService;
	@Autowired private IVehicleOdometerHistoryService 						vehicleOdometerHistoryService;
	@Autowired private IPendingVendorPaymentService 						vendorPaymentService;
	@Autowired private VehicleProfitAndLossTxnCheckerService				vehicleProfitAndLossTxnCheckerService;
	@Autowired private IVehicleProfitAndLossService							vehicleProfitAndLossService;
	@Autowired private IVehicleAgentTxnCheckerService						vehicleAgentTxnCheckerService;
	@Autowired private IServiceEntriesDocumentService						documentService;
	@Autowired private IServiceReminderService 								serviceReminderService;
	@Autowired private IEmailAlertQueueService 								emailAlertQueueService;
	@Autowired private ISmsAlertQueueService 								smsAlertQueueService;
	@Autowired private IIssuesService 										issuesService;
	@Autowired private IVehicleTyreAssignmentService 						vehicleTyreAssignmentService;
	@Autowired private ITyreUsageHistoryService								tyreUsageHistoryService;
	@Autowired private DealerServiceReminderHistoryRepository				orderHistoryRepository;
	@Autowired private DealerServiceEntriesRepository 						dealerRepository;
	@Autowired private DealerServiceEntriesLabourRepository 				dealerLabourRepository;
	@Autowired private DealerServiceEntriesPartRepository 					dealerPartRepository;
	@Autowired private MasterPartsRepository 								masterPartsDao;
	@Autowired private DealerServiceExtraIssueRepository					dseExtraIssueRepository;
	@Autowired private VendorRepository										vendorRepository;
	@Autowired private IVendorSequenceService 								vendorSequenceService;
	@Autowired private IVehicleAgentIncomeExpenseDetailsService				vehicleAgentIncomeExpenseDetailsService;
	@Autowired private IVehicleExpenseDetailsService						vehicleExpenseDetailsService;
	@Autowired private LabourRepository										labourRepository;
	@Autowired private PartWarrantyDetailsRepository						partWarrantyDetailsRepository;
	@Autowired private DealerServiceRemarkRepository						dealerServiceRemarkRepository;
	@Autowired private DealerServiceReminderRepository						dealerServiceReminderRepository;
	@Autowired private IMasterPartsService									masterPartsService;
	@Autowired private IVehicleAccidentDetailsService						accidentDetailsService;
	@Autowired         ICompanyConfigurationService 						companyConfigurationService;
	@Autowired 		   IVehicleDocumentService								vehicleDocumentService;
	@Autowired			IBankPaymentService									bankPaymentService;
	@Autowired			ICashPaymentService									cashPaymentService;
	@Autowired			IVendorService										vendorService;
	@Autowired private  ServiceReminderHistoryRepository                  serviceReminderHistoryRepository;
	
	DealerServiceEntriesBL				dealerServiceEntriesBL 		= new DealerServiceEntriesBL();
	VehicleOdometerHistoryBL 			VOHBL 						= new VehicleOdometerHistoryBL();
	VehicleProfitAndLossTxnCheckerBL	txnCheckerBL 				= new VehicleProfitAndLossTxnCheckerBL();
	VehicleAgentTxnCheckerBL			agentTxnCheckerBL			= new VehicleAgentTxnCheckerBL();
	IssuesBL							issuesBL					= new IssuesBL();
	VendorBL							vendorBL					= new VendorBL();
	LabourBL							labourBL					= new LabourBL();
	PartWarrantyDetailsBL				partWarrantyBL				= new PartWarrantyDetailsBL();
	VehicleBL 							VBL 						= new VehicleBL();
	SimpleDateFormat dateFormatSQL 		= new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat 		= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat tallyFormat		= new SimpleDateFormat("yyyyMMdd");
	
	private static final int PAGE_SIZE = 20;
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public ValueObject getDealerServiceEntriesList(ValueObject valueObject) throws Exception {
		List<DealerServiceEntriesDto>	dealerServiceEntriesList		= null;
		String							query							= "";
		CustomUserDetails			           userDetails		= null;
		HashMap<String, Object>                configuration  = null;
		
		try {
			Page<DealerServiceEntries> page 	= getDeploymentPageDealerServiceEntries(valueObject.getShort("status",(short)0), valueObject.getInt("pagenumber"), valueObject.getInt("companyId"));
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration =  companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.DSE_CONFIGURATION_CONFIG);
			
			Object[] statusCount 		= getDealerServiceEntriesCount(valueObject);
			
			if (page != null) {
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				valueObject.put("deploymentLog", page);
				valueObject.put("beginIndex", begin);
				valueObject.put("endIndex", end);
				valueObject.put("currentIndex", current);
			}
			if(valueObject.getShort("status",(short)0) != 0) {
				if(valueObject.getShort("invoiceStatus",(short)0) == 1) {
					query = "AND DSE.statusId ="+valueObject.getShort("status")+" AND (DSE.invoiceNumber = '"+""+"' OR DSE.invoiceDate = NULL)";
				}else if(valueObject.getShort("invoiceStatus",(short)0) == 2) {
					query = "AND DSE.statusId ="+valueObject.getShort("status")+" AND (DSE.invoiceNumber <> '"+""+"' AND DSE.invoiceDate IS NOT NULL)";
				}else {
					query = "AND DSE.statusId ="+valueObject.getShort("status")+" ";
				}
			}
			
			valueObject.put("query", query);
			dealerServiceEntriesList = getDealerServiceEntriesDetailList(valueObject);
			
			valueObject.put("allCount", (Long) statusCount[0]);
			valueObject.put("allOpenCount", (Long) statusCount[1]);
			valueObject.put("invoicePendingCount", (Long) statusCount[2]);
			valueObject.put("invoiceReceivedCount", (Long) statusCount[3]);
			valueObject.put("holdCount", (Long) statusCount[4]);
			valueObject.put("paymentPendingCount", (Long) statusCount[5]);
			valueObject.put("paymentReceivedCount", (Long) statusCount[6]);

			valueObject.put("dealerServiceEntriesList", dealerServiceEntriesList);
			valueObject.put("statusId", valueObject.getShort("status"));
			valueObject.put("currentIndex", valueObject.getInt("pagenumber"));
			valueObject.put("configuration", configuration);
			
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public List<DealerServiceEntriesDto> getDealerServiceEntriesDetailList(ValueObject valueObject) throws Exception {
		List<DealerServiceEntriesDto>	dealerServiceEntriesList		= null;
		DealerServiceEntriesDto 	dealerServiceEntriesDto 		= null;
		TypedQuery<Object[]> 		query 							= null;
		try {
			query = entityManager.createQuery(
					  " SELECT DSE.dealerServiceEntriesId, DSE.dealerServiceEntriesNumber, DSE.vid, V.vehicle_registration,"
					+ " DSE.vendorId , VN.vendorName, DSE.invoiceNumber,DSE.jobNumber, DSE.invoiceDate, DSE.totalInvoiceCost, "
					+ " U1.firstName, U2.firstName, DSE.statusId, DSE.paymentTypeId, DSE.vehicleOdometer,DSE.payNumber, DSE.paidDate,"
					+ " DSE.dealerServiceDocumentId, V.vehicle_chasis, V.vehicle_engine, VN.vendorAddress1, VN.vendorAddress2, "
					+ " DSE.serviceReminderIds, DSE.issueId, I.ISSUES_NUMBER, I.ISSUES_SUMMARY, DSE.driverId, D.driver_firstname, "
					+ " D.driver_Lastname, D.driver_fathername, DSE.remark, DSE.isPartApplicable, DSE.assignToId, U3.firstName, "
					+ " U4.firstName, U5.firstName, DSE.creationOn, DSE.lastUpdatedOn,DSE.isLabourApplicable , V.vehicleModalId, DSE.meterNotWorkingId, DSE.issueIds "
					+ " FROM DealerServiceEntries AS DSE "
					+ " INNER JOIN Vehicle V ON V.vid = DSE.vid "
					+ " INNER JOIN User U2 ON U2.id = DSE.lastModifiedById "
					+ " INNER JOIN Vendor VN ON VN.vendorId = DSE.vendorId "
					+ " LEFT JOIN Driver D ON D.driver_id = DSE.driverId "
					+ " LEFT JOIN Issues I ON I.ISSUES_ID = DSE.issueId "
					+ " LEFT JOIN User U1 ON U1.id = DSE.paidById "
					+ " LEFT JOIN User U3 ON U3.id = DSE.assignToId "
					+ " INNER JOIN User U4 ON U4.id = DSE.createdById "
					+ " INNER JOIN User U5 ON U5.id = DSE.lastModifiedById "
					+ " where DSE.companyId = "+valueObject.getInt("companyId")+" "+valueObject.getString("query","")+" AND  DSE.markForDelete = 0 ORDER BY DSE.dealerServiceEntriesId DESC",
					Object[].class);
			
			if(valueObject.getInt("currentIndex",0) > 0) {
				query.setFirstResult((valueObject.getInt("currentIndex",0) - 1) * PAGE_SIZE);
				query.setMaxResults(PAGE_SIZE);
			}
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				dealerServiceEntriesList = new ArrayList<>();
				
				for (Object[] result : results) {
					dealerServiceEntriesDto = new DealerServiceEntriesDto();

					dealerServiceEntriesDto.setDealerServiceEntriesId((Long) result[0]);
					dealerServiceEntriesDto.setDealerServiceEntriesNumberStr("DSE-"+(Long) result[1]);
					dealerServiceEntriesDto.setVid((Integer) result[2]);
					dealerServiceEntriesDto.setVehicleNumber((String) result[3]);
					dealerServiceEntriesDto.setVendorId((Integer) result[4]);
					dealerServiceEntriesDto.setVendorName((String) result[5]);
					if(result[6] != null) {
						dealerServiceEntriesDto.setInvoiceNumber((String) result[6]);
					}
					dealerServiceEntriesDto.setJobNumber((String) result[7]);
					if( result[8] != null) {
						dealerServiceEntriesDto.setInvoiceDate((Timestamp) result[8]);
						dealerServiceEntriesDto.setInvoiceDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[8], DateTimeUtility.DD_MM_YYYY));
					}else {
						dealerServiceEntriesDto.setInvoiceDateStr("");
					}
					if(result[9] != null) {
						dealerServiceEntriesDto.setTotalInvoiceCost((Double) result[9]);
					}else {
						dealerServiceEntriesDto.setTotalInvoiceCost(0.0);
					}
					dealerServiceEntriesDto.setPaidBy((String) result[10]);
					dealerServiceEntriesDto.setLastModifiedBy((String) result[11]);
					dealerServiceEntriesDto.setStatusId((short) result[12]);
					dealerServiceEntriesDto.setStatus(DealerServiceEntriesConstant.getDealerServiceEntriesStatus(dealerServiceEntriesDto.getStatusId()));
					dealerServiceEntriesDto.setPaymentTypeId((short) result[13]);
					dealerServiceEntriesDto.setPaymentType(PaymentTypeConstant.getPaymentTypeName(dealerServiceEntriesDto.getPaymentTypeId()));
					if(result[14] != null) {
						dealerServiceEntriesDto.setVehicleOdometer((Integer) result[14]);
					}
					dealerServiceEntriesDto.setPayNumber((String) result[15]);
					if( result[16] != null) {
						dealerServiceEntriesDto.setPaidDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[16], DateTimeUtility.DD_MM_YYYY));
					}
					if( result[17] != null) {
						dealerServiceEntriesDto.setDealerServiceDocumentId((Long) result[17]);
					}
					if(result[18] != null) {
						dealerServiceEntriesDto.setVehicleChasisNumber((String) result[18]);
					}
					if(result[19] != null) {
						dealerServiceEntriesDto.setVehicleEngineNumber((String) result[19]);
					}
					if(result[20] != null) {
						dealerServiceEntriesDto.setVendorAddress((String) result[20]);
					}
					if(result[20] != null && result[21] != null) {
						dealerServiceEntriesDto.setVendorAddress((String) result[20] +""+ (String) result[21]);
					}
					if(result[22] != null) {
						dealerServiceEntriesDto.setServiceReminderIds((String) result[22]);
					}
					if(result[23] != null) {
						dealerServiceEntriesDto.setIssueId((Long) result[23]);
						dealerServiceEntriesDto.setIssueNumberStr("I-"+(Long) result[24]);
						dealerServiceEntriesDto.setIssueEncryptId(AESEncryptDecrypt.encryptBase64("" + dealerServiceEntriesDto.getIssueId()));
						dealerServiceEntriesDto.setIssueSummary((String) result[25]);
					}
					if(result[26] != null) {
						dealerServiceEntriesDto.setDriverId((Integer) result[26]);
					}
					if(result[27] != null) {
						dealerServiceEntriesDto.setDriverFirstName((String) result[27]);
					}else {
						dealerServiceEntriesDto.setDriverFirstName("");
					}
					if(result[28] != null) {
						dealerServiceEntriesDto.setDriverLastName((String) result[28]);
					}else {
						dealerServiceEntriesDto.setDriverLastName("");
					}
					if(result[29] != null) {
						dealerServiceEntriesDto.setDriverFatherName((String) result[29]);
					}else {
						dealerServiceEntriesDto.setDriverFatherName("");
					}
					dealerServiceEntriesDto.setDriverFullName(dealerServiceEntriesDto.getDriverFirstName()+" "+dealerServiceEntriesDto.getDriverLastName()+" - "+dealerServiceEntriesDto.getDriverFatherName());
					
					if(result[30] != null) {
						dealerServiceEntriesDto.setRemark((String) result[30]);
					}else {
						dealerServiceEntriesDto.setRemark("");
					}
					dealerServiceEntriesDto.setDsePartStatus(DealerServiceEntriesConstant.getPartStatusOfDse((boolean) result[31]));
					if(result[32] != null && (Long) result[32] > 0) {
						dealerServiceEntriesDto.setAssignToId((Long) result[32]);
						dealerServiceEntriesDto.setAssignTo((String) result[33]);
					}else {
						dealerServiceEntriesDto.setAssignTo("");
					}
					dealerServiceEntriesDto.setCreatedBy((String) result[34]);
					dealerServiceEntriesDto.setLastModifiedBy((String) result[35]);
					dealerServiceEntriesDto.setCreationDate(DateTimeUtility.getStringDateFromDate((Timestamp) result[36], DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
					dealerServiceEntriesDto.setLastUpdatedDate(DateTimeUtility.getStringDateFromDate((Timestamp) result[37], DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
					dealerServiceEntriesDto.setLabourApplicable((boolean) result[38]);
					dealerServiceEntriesDto.setLabourStatus(DealerServiceEntriesConstant.getLabourApplicableStatus(dealerServiceEntriesDto.isLabourApplicable()));
					dealerServiceEntriesDto.setVehicleModelId((Long) result[39]);
					if(result[40] != null) {
						dealerServiceEntriesDto.setMeterNotWorkingId((boolean) result[40]);
						if((boolean) result[40]) {
							dealerServiceEntriesDto.setMeterNotWorking("Meter Not Working");
						}else {
							dealerServiceEntriesDto.setMeterNotWorking("Meter Working");
						}
					}else {
						dealerServiceEntriesDto.setMeterNotWorking("Meter Working");
					}
					dealerServiceEntriesDto.setIssueIds((String) result[41]);
					dealerServiceEntriesList.add(dealerServiceEntriesDto);
				}
			}
			
			
			return dealerServiceEntriesList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Transactional
	public Object[] getDealerServiceEntriesCount(ValueObject valueObject) throws Exception {
		try {
			Query queryt =	null;
			queryt = entityManager
					.createQuery("SELECT COUNT(DSE1),"
							+ "(SELECT  COUNT(DSE2) FROM DealerServiceEntries AS DSE2 where DSE2.statusId =1 AND DSE2.companyId = "+valueObject.getInt("companyId")+" AND DSE2.markForDelete = 0 ) ,"
							+ "(SELECT  COUNT(DSE3) FROM DealerServiceEntries AS DSE3 where DSE3.statusId =1 AND (DSE3.invoiceNumber = '"+""+"' OR DSE3.invoiceDate = NULL) AND DSE3.companyId = "+valueObject.getInt("companyId")+" AND DSE3.markForDelete = 0 ) ,"
							+ "(SELECT  COUNT(DSE4) FROM DealerServiceEntries AS DSE4 where DSE4.statusId =1 AND (DSE4.invoiceNumber <> '"+""+"' AND DSE4.invoiceDate IS NOT NULL) AND DSE4.companyId = "+valueObject.getInt("companyId")+" AND DSE4.markForDelete = 0 ) ,"
							+ "(SELECT  COUNT(DSE5) FROM DealerServiceEntries AS DSE5 where DSE5.statusId =2 AND DSE5.companyId = "+valueObject.getInt("companyId")+" AND DSE5.markForDelete = 0 ) ,"
							+ "(SELECT  COUNT(DSE6) FROM DealerServiceEntries AS DSE6 where DSE6.statusId =3 AND DSE6.companyId = "+valueObject.getInt("companyId")+" AND DSE6.markForDelete = 0 ) ,"
							+ "(SELECT  COUNT(DSE7) FROM DealerServiceEntries AS DSE7 where DSE7.statusId =4 AND DSE7.companyId = "+valueObject.getInt("companyId")+" AND DSE7.markForDelete = 0) " 
							+ " FROM DealerServiceEntries As DSE1 where DSE1.companyId = "+valueObject.getInt("companyId")+" AND DSE1.markForDelete = 0 ");
	 
			Object[] count = (Object[]) queryt.getSingleResult();

			return count;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("deprecation")
	@Transactional
	public Page<DealerServiceEntries> getDeploymentPageDealerServiceEntries(short statusId, Integer pageNumber, Integer comapanyId) throws Exception {
		try {
			Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
			if(statusId == 0) {
				return dealerRepository.getDeploymentPageDealerServiceEntries(comapanyId, pageable);
			}else {
				return dealerRepository.getDeploymentPageDealerServiceEntries(statusId, comapanyId, pageable);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public ValueObject saveDealerServiceEntries(ValueObject valueObject,  MultipartFile uploadfile) throws Exception {
		ArrayList<ValueObject>					labourDataArrayObj						= null;
		ArrayList<ValueObject>					partDataArrayObj						= null;
		List<DealerServiceEntries>				validateDealerServiceEntries			= null;
		DealerServiceEntries					dealerServiceEntries					= null;
		DealerServiceEntriesSequenceCounter 	dealerServiceEntriesSequenceCounter 	= null;
		boolean									vehicleStatus							= false;
		VehicleDto 								checkVehicleStatus 						= null; 
		HashMap<String, Object>                 configuration  							= null;
		try {
			labourDataArrayObj 					= new ArrayList<>();
			partDataArrayObj 					= new ArrayList<>();
			validateDealerServiceEntries		= new ArrayList<>();
			configuration =  companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.DSE_CONFIGURATION_CONFIG);
			
			labourDataArrayObj	= (ArrayList<ValueObject>) valueObject.get("labourDetails");
			partDataArrayObj	= (ArrayList<ValueObject>) valueObject.get("partDetails");
			
			checkVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("vid"));
			if (checkVehicleStatus != null && (checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE  || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACCIDENT || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP)) {
				vehicleStatus = true;
			} 
			
			if(vehicleStatus) {
				if(valueObject.getString("invoiceNumber","") != "") { // because this will also validate while completing DSE 
					validateDealerServiceEntries = validateDealerServiceEntries(valueObject.getInt("vid"),valueObject.getInt("vendorId"),valueObject.getString("invoiceNumber"),  valueObject.getInt("companyId"));
				}
				if(validateDealerServiceEntries != null && !validateDealerServiceEntries.isEmpty()) {
					valueObject.put("alreadyExist", true);
					return valueObject;
				}
				valueObject.put("serviceReminderId", Utility.removeLastComma(valueObject.getString("serviceReminderId", "")));
				dealerServiceEntries 				= dealerServiceEntriesBL.prepareDealerServiceEntries(valueObject);
				dealerServiceEntriesSequenceCounter = DSE_SequenceCounterService.findNextDSENumber(valueObject.getInt("companyId"));
				
				if (dealerServiceEntriesSequenceCounter == null) {
					valueObject.put("sequenceCounterNotFound", true);
					return valueObject;
				}else {
					dealerServiceEntries.setDealerServiceEntriesNumber(dealerServiceEntriesSequenceCounter.getNextVal());
				}
				
				dealerServiceEntries = dealerRepository.save(dealerServiceEntries);
				
				if(dealerServiceEntries != null) {
					valueObject.put("dealerServiceEntriesId", dealerServiceEntries.getDealerServiceEntriesId());
					
					uploadDealerServiceEntryDocument(valueObject,uploadfile);
					if(labourDataArrayObj != null && !labourDataArrayObj.isEmpty()) {
						saveDealerServiceEntriesLabour(labourDataArrayObj, valueObject);
					}
					if(partDataArrayObj != null && !partDataArrayObj.isEmpty()) {
						saveDealerServiceEntriesPart(partDataArrayObj, valueObject);
					}	
					
						updateVehicleOdometer(dealerServiceEntries);
					
					
					if(dealerServiceEntries.getIssueIds() != null && !dealerServiceEntries.getIssueIds().isEmpty()) {
						updateIssueDeatils(dealerServiceEntries);
					}
					
					addDealerServiceReminder(dealerServiceEntries);
					
					if(dealerServiceEntries.getAccidentId() != null && dealerServiceEntries.getAccidentId() > 0) {
						if((boolean) configuration.getOrDefault("multipleQuotation", false)){
							accidentDetailsService.saveAccedentServiceDetails(dealerServiceEntries.getDealerServiceEntriesId(),dealerServiceEntries.getDealerServiceEntriesNumber(),dealerServiceEntries.getAccidentId(),AccidentStatus.SERVICE_TYPE_DSE,valueObject.getInt("companyId"));
						}else{
							accidentDetailsService.updateAccidentDetailsServiceDetails(dealerServiceEntries.getDealerServiceEntriesId(), AccidentStatus.SERVICE_TYPE_DSE, dealerServiceEntries.getAccidentId());
						} 
						accidentDetailsService.updateAccidentDetailsStatus(dealerServiceEntries.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_QUOTATION_CREATED);
					}
				}
				updateDealerServiceEntriesInvoiceCost(valueObject);
				
				DealerServiceEntries savedDealerServiceEntries =getDealerServiceEntriesById(valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
				
				if(valueObject.getBoolean("allowBankPaymentDetails",false) && (PaymentTypeConstant.getPaymentTypeIdList().contains(savedDealerServiceEntries.getPaymentTypeId()) || savedDealerServiceEntries.getPaymentTypeId()==PaymentTypeConstant.PAYMENT_TYPE_CASH )){
					ValueObject bankPaymentValueObject=JsonConvertor.toValueObjectFormSimpleJsonString(valueObject.getString("bankPaymentDetails"));
						bankPaymentValueObject.put("bankPaymentTypeId", savedDealerServiceEntries.getPaymentTypeId());
						bankPaymentValueObject.put("userId",valueObject.getInt("userId"));
						bankPaymentValueObject.put("companyId",valueObject.getInt("companyId"));
						bankPaymentValueObject.put("moduleId",savedDealerServiceEntries.getDealerServiceEntriesId());
						bankPaymentValueObject.put("moduleNo", savedDealerServiceEntries.getDealerServiceEntriesNumber());
						bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.DEALER_SERVICE);
						bankPaymentValueObject.put("amount",savedDealerServiceEntries.getTotalInvoiceCost());
						
						Vendor	vendor	= vendorService.getVendor(savedDealerServiceEntries.getVendorId());
						
						bankPaymentValueObject.put("remark", "Dealer Service DSE-"+savedDealerServiceEntries.getDealerServiceEntriesNumber()
													+" Vendor : "+vendor.getVendorName()+" Payment Type : "+PaymentTypeConstant.getPaymentTypeName(savedDealerServiceEntries.getPaymentTypeId()));
						if(PaymentTypeConstant.PAYMENT_TYPE_CASH == savedDealerServiceEntries.getPaymentTypeId())
							cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
						else
							bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
					}
			}else {
				if (checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SOLD) {
					valueObject.put("inSold", true);
				} else if (checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE) {
					valueObject.put("inActive", true);
				} else if (checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SURRENDER) {
					valueObject.put("inSurrender", true);
				}
			}
			
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	@Transactional
	public ValueObject uploadDealerServiceEntryDocument(ValueObject valueObject, MultipartFile file) throws Exception {
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			org.fleetopgroup.persistence.document.ServiceEntriesDocument serviceDocument = new org.fleetopgroup.persistence.document.ServiceEntriesDocument();
			serviceDocument.setServiceEntries_id(valueObject.getLong("dealerServiceEntriesId"));
			
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
			
				documentService.deleteByServiceId(valueObject.getLong("dealerServiceEntriesId"));
				
				documentService.saveServiceEntriesDoc(serviceDocument);
				
				UpdateDealerServiceEntriesDocuemntAvailableValue(
						serviceDocument.getService_documentid(), true, valueObject.getLong("dealerServiceEntriesId"),
						userDetails.getCompany_id());
				
				valueObject.put("UploadSuccess", true);
			}
			
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void UpdateDealerServiceEntriesDocuemntAvailableValue(Long service_documentid, boolean service_document,
			Long dealerServiceEntriesId, Integer companyId) {

		dealerRepository.UpdateDealerServiceEntriesDocuemntAvailableValue(service_documentid, service_document, dealerServiceEntriesId, companyId);
	}
	
	@Transactional
	public void saveDealerServiceEntriesLabour(ArrayList<ValueObject> labourDataArrayObj ,ValueObject valueObject) throws Exception {
		DealerServiceEntriesLabour				dealerServiceEntriesLabourBL			= null;
		List<DealerServiceEntriesLabour>		dealerServiceEntriesLabourList			= null;
		Labour									prepareLabour								= null;
		Labour									savedLabour							= null;
		try {
			dealerServiceEntriesLabourList 				= new ArrayList<DealerServiceEntriesLabour>();
			for (ValueObject object : labourDataArrayObj) {
				if(!object.getString("labourName","").equals("")) {
					prepareLabour = labourBL.prepareNewLabourFromDSE(object,valueObject);
					savedLabour = labourRepository.save(prepareLabour);
				}
				
				if(savedLabour != null) {
					object.put("labourId", savedLabour.getLabourId());
				}
				
				if(object.getLong("labourId",0 ) > 0) {
					dealerServiceEntriesLabourBL = dealerServiceEntriesBL.prepareDealerServiceEntriesLabour(object,valueObject);
					dealerServiceEntriesLabourList.add(dealerServiceEntriesLabourBL);
				}
			}
			dealerLabourRepository.saveAll(dealerServiceEntriesLabourList);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Transactional
	public void saveDealerServiceEntriesPart(ArrayList<ValueObject> partDataArrayObj ,ValueObject valueObject) throws Exception {
		DealerServiceEntriesPart				dealerServiceEntriesPartBL				= null;
		ValueObject								lastOccurredPartObject					= null;
		DealerServiceEntriesPartDto				lastOccurredDealerServiceEntriesPart	= null;
		int										quantity								= 0;
		PartWarrantyDetails						warrantyBL								= null;
		List<PartWarrantyDetails>				partWarrantyDetailsList					= null;
		DealerServiceEntriesPart				saveDealerServiceEntriesPart				= null;
		try {
			partWarrantyDetailsList = new ArrayList<>();
			for (ValueObject object : partDataArrayObj) {
				if(valueObject.getShort("partDiscountTaxTypeId") == DealerServiceEntriesConstant.PERCENTAGE_TYPE_ID) {
					if(object.getDouble("partDiscount", 0) > 100 || object.getDouble("partTax", 0) > 100) {
						continue;
					}
					
				}
				object.put("vid", valueObject.getInt("vid"));
				object.put("companyId", valueObject.getInt("companyId"));
				lastOccurredPartObject 					= getLastOccurredDsePartDetails(object);
				lastOccurredDealerServiceEntriesPart 	= (DealerServiceEntriesPartDto) lastOccurredPartObject.get("lastOccuredPartDetails");
				
				if(lastOccurredDealerServiceEntriesPart != null) {
					object.put("lastOccurredDealerServiceEntriesId", lastOccurredDealerServiceEntriesPart.getDealerServiceEntriesId());
				}
				
				
				dealerServiceEntriesPartBL 		= dealerServiceEntriesBL.prepareDealerServiceEntriesPart(object,valueObject);
				saveDealerServiceEntriesPart = dealerPartRepository.save(dealerServiceEntriesPartBL);
				
				if(saveDealerServiceEntriesPart !=  null) {
					object.put("dealerServiceEntriesPartId", saveDealerServiceEntriesPart.getDealerServiceEntriesPartId());
					if(object.getBoolean("isWarrantyApplicable",false)) {
						quantity = (int) object.getDouble("partQty");
						for(int i= 0 ; i < quantity; i++) {
							warrantyBL = partWarrantyBL.preparePartWarrantyDetails(object, valueObject);
							partWarrantyDetailsList.add(warrantyBL);
						}
						partWarrantyDetailsRepository.saveAll(partWarrantyDetailsList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	@Transactional
	public void updateVehicleOdometer(DealerServiceEntries dealerServiceEntries) throws Exception {
		VehicleOdometerHistory 					vehicleOdometerHistory 				= null;
		Integer 								currentVehicleOdometer 				= null;
		List<ServiceReminderDto> 				serviceReminderList					= null;
		try {
			currentVehicleOdometer = vehicleService.updateCurrentOdoMeterGETVehicleToCurrentOdometer(dealerServiceEntries.getVid());
			
			if(currentVehicleOdometer != null && dealerServiceEntries.getVehicleOdometer() > 0) {
				if (currentVehicleOdometer < dealerServiceEntries.getVehicleOdometer() || currentVehicleOdometer.equals(dealerServiceEntries.getVehicleOdometer()) ) {
					
					serviceReminderService.updateCurrentOdometerToServiceReminder(dealerServiceEntries.getVid(), dealerServiceEntries.getVehicleOdometer(),dealerServiceEntries.getCompanyId());
					serviceReminderList = serviceReminderService.OnlyVehicleServiceReminderList(dealerServiceEntries.getVid(), dealerServiceEntries.getCompanyId(), dealerServiceEntries.getLastModifiedById());
					if(serviceReminderList != null && !serviceReminderList.isEmpty()) {
						for(ServiceReminderDto dto : serviceReminderList) {
							if(dto.getVehicle_currentOdometer() >= dto.getMeter_serviceodometer()) {
								serviceReminderService.setServiceOdometerUpdatedDate(dto.getService_id(), dto.getCompanyId());
							}
						}
					}
					
					vehicleService.updateCurrentOdoMeter(dealerServiceEntries.getVid(), dealerServiceEntries.getVehicleOdometer(), dealerServiceEntries.getCompanyId());
					
					vehicleOdometerHistory = VOHBL.prepareOdometerGetHistoryToDealerServiceEntries(dealerServiceEntries);
					vehicleOdometerHistory.setCompanyId(dealerServiceEntries.getCompanyId());
					vehicleOdometerHistoryService.addVehicleOdometerHistory(vehicleOdometerHistory);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public void updateIssueDeatils (DealerServiceEntries dealerServiceEntries)throws Exception {
	Issues				issue 		= null;
	IssuesTasks			issueTask 	= null;
		try {
		String[] issueIds =	dealerServiceEntries.getIssueIds().split(",");
		
		Date dateTime = DateTimeUtility.getCurrentDateTime();
		
		for(String issueId : issueIds) {
			issuesService.updateCreateDSE_IssueStatus(IssuesTypeConstant.ISSUES_STATUS_DSE_CREATED, dealerServiceEntries.getCreatedById(), dateTime, dealerServiceEntries.getDealerServiceEntriesId(),Long.parseLong(issueId),dealerServiceEntries.getCompanyId());
			issue 		= issuesService.getIssueDetailsByIssueId(Long.parseLong(issueId), dealerServiceEntries.getCompanyId());
			if(issue != null) {
				issueTask 	= issuesBL.prepareIssuesTaskDetailsForDSE(issue,dealerServiceEntries);
			}
			issuesService.registerNew_IssuesTasks(issueTask);
		}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ValueObject getDealerServiceEntriesWithPartAndLabourDetails(ValueObject valueObject) throws Exception {
		String								query 							= "";
		List<IssuesDto>						issueDtoList					= null;
		List<DealerServiceEntriesDto>		dealerServiceEntries			= null;
		List<DealerServiceEntriesLabourDto>	dealerServiceEntriesLabourList	= null;
		List<DealerServiceEntriesPartDto>	dealerServiceEntriesPartList	= null;
		ValueObject 						valueOutObject					= null;
		HashMap<String, Object> 			configuration					= null;
		try {
			
			configuration =companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.DSE_CONFIGURATION_CONFIG);
			valueOutObject = new ValueObject();
			if(valueObject.getLong("dealerServiceEntriesId",0) > 0) {
				query = "AND DSE.dealerServiceEntriesId = "+valueObject.getLong("dealerServiceEntriesId")+"";
			}
			valueObject.put("query", query);
			dealerServiceEntries 			= getDealerServiceEntriesDetailList(valueObject);
			dealerServiceEntriesLabourList 	= getDealerServiceEntriesLabourDetailList(valueObject);
			dealerServiceEntriesPartList 	= getDealerServiceEntriesPartDetailList(valueObject);
			
			
			if(dealerServiceEntries != null && !dealerServiceEntries.isEmpty()) {
				if(dealerServiceEntries.get(0).getIssueIds() != null && !dealerServiceEntries.get(0).getIssueIds().trim().equals("")) {
				issueDtoList 					= issuesService.getIssueDetailsByIssueIds(dealerServiceEntries.get(0).getIssueIds(), valueObject.getInt("companyId"));
				StringBuilder issueNumbers = new StringBuilder();
				StringBuilder issueNumb = new StringBuilder();
				if(issueDtoList != null && !issueDtoList.isEmpty() ) {
					for(IssuesDto dto : issueDtoList) {
						issueNumbers.append("<a href='showIssues.in?Id="+dto.getISSUES_ID_ENCRYPT()+"' > "+dto.getIssuesNumberStr()+"</a>"+",");
						issueNumb.append(dto.getIssuesNumberStr()+",");
					}
				}
				valueOutObject.put("issueNumb", issueNumb);
				valueOutObject.put("issueNumbers", issueNumbers);
				}
				valueOutObject.put("dealerServiceEntries", dealerServiceEntries.get(0));
				if(!dealerServiceEntries.get(0).getServiceReminderIds().equals("")) {
					ObjectMapper objectMapper = new ObjectMapper();
					if((boolean)configuration.getOrDefault("showServiceProgram", false)) {
						valueOutObject.put("serviceProgramList", objectMapper.writeValueAsString(serviceReminderService.getProgramListByreminderIds(dealerServiceEntries.get(0).getServiceReminderIds(), valueObject.getInt("companyId"))));
					}else {
						valueOutObject.put("serviceProgramList", objectMapper.writeValueAsString(serviceReminderService.getSRByreminderIds(dealerServiceEntries.get(0).getServiceReminderIds(), valueObject.getInt("companyId"))));
					}
				}
			}
			
			if(valueObject.getBoolean("tyreAssginFromDSEConfig",false)) {
				valueOutObject.put("vehicleTyreLayoutPositionList", vehicleTyreAssignmentService.getTyreAssignmentByTransactionTypeAndTransactionId(TyreAssignmentConstant.TRANSACTION_TYPE_DSE, valueObject.getLong("dealerServiceEntriesId"),valueObject.getInt("companyId")));
			}
			valueOutObject.put("dealerServiceEntriesLabour", dealerServiceEntriesLabourList);
			valueOutObject.put("dealerServiceEntriesPart", dealerServiceEntriesPartList);
			return valueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public List<DealerServiceEntriesLabourDto> getDealerServiceEntriesLabourDetailList(ValueObject valueObject) throws Exception {
		List<DealerServiceEntriesLabourDto>	dealerServiceEntriesLabourList		= null;
		DealerServiceEntriesLabourDto 		dealerServiceEntriesLabour 		= null;
		TypedQuery<Object[]> 		query 							= null;
		try {
			query = entityManager.createQuery(
					  " SELECT DSE.dealerServiceEntriesLabourId, DSE.labourName, DSE.labourWorkingHours, DSE.labourPerHourCost,"
					+ " DSE.labourDiscount , DSE.labourTax, DSE.totalCost, DSE.labourDiscountCost, DSE.labourTaxCost, DSE.labourId, L.labourName, DSE.labourDiscountTaxTypeId "
					+ " FROM DealerServiceEntriesLabour AS DSE "
					+ " LEFT JOIN Labour AS L ON L.labourId = DSE.labourId "
					+ " where DSE.companyId = "+valueObject.getInt("companyId")+" "+valueObject.getString("query")+" AND  DSE.markForDelete = 0",
					Object[].class);
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				dealerServiceEntriesLabourList = new ArrayList<DealerServiceEntriesLabourDto>();
				
				for (Object[] result : results) {
					dealerServiceEntriesLabour = new DealerServiceEntriesLabourDto();

					dealerServiceEntriesLabour.setDealerServiceEntriesLabourId((Long) result[0]);
					dealerServiceEntriesLabour.setLabourName((String) result[1]);
					dealerServiceEntriesLabour.setLabourWorkingHours((Double) result[2]);
					dealerServiceEntriesLabour.setLabourPerHourCost((Double) result[3]);
					dealerServiceEntriesLabour.setLabourDiscount((Double) result[4]);
					dealerServiceEntriesLabour.setLabourTax((Double) result[5]);
					dealerServiceEntriesLabour.setLabourTotalCost((Double) result[6]);
					if(result[7] != null) {
						dealerServiceEntriesLabour.setLabourDiscountCost((Double) result[7]);
					}else {
						dealerServiceEntriesLabour.setLabourDiscountCost(0.0);
						
					}
					if(result[8] != null) {
						dealerServiceEntriesLabour.setLabourTaxCost((Double) result[8]);
					}else {
						dealerServiceEntriesLabour.setLabourTaxCost(0.0);
						
					}
					if(result[9] != null) {
						dealerServiceEntriesLabour.setLabourId((Integer) result[9]);
						dealerServiceEntriesLabour.setLabourName((String) result[10]);
					}
					dealerServiceEntriesLabour.setLabourDiscountTaxTypeId((short) result[11]);
					dealerServiceEntriesLabourList.add(dealerServiceEntriesLabour);
				}
			}
			
			return dealerServiceEntriesLabourList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	public List<DealerServiceEntriesPartDto> getDealerServiceEntriesPartDetailList(ValueObject valueObject) throws Exception {
		List<DealerServiceEntriesPartDto>	dealerServiceEntriesPartList	= null;
		DealerServiceEntriesPartDto 		dealerServiceEntriesPart 		= null;
		TypedQuery<Object[]> 				query 							= null;
		try {
			query = entityManager.createQuery(  
					" SELECT DSE.DealerServiceEntriesPartId, DSE.partId, DSE.partName, MP.partname, DSE.quantity, DSE.partEchCost, "
					+ " DSE.partDiscount , DSE.partTax, DSE.partTotalCost, DSE.partDiscountCost, DSE.partTaxCost,D.dealerServiceEntriesId, "
					+ " D.dealerServiceEntriesNumber,D.invoiceDate, D.vehicleOdometer, MP.partnumber, DSE.partDiscountTaxTypeId, MP.isWarrantyApplicable, MP.warrantyInMonths "
					+ " FROM DealerServiceEntriesPart AS DSE "
					+ " LEFT JOIN DealerServiceEntries AS D ON D.dealerServiceEntriesId = DSE.lastOccurredDealerServiceEntriesId"
					+ " LEFT JOIN MasterParts AS MP ON MP.partid = DSE.partId "
					+ " where DSE.companyId = "+valueObject.getInt("companyId")+" "+valueObject.getString("query")+" AND  DSE.markForDelete = 0",
					Object[].class);
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				dealerServiceEntriesPartList = new ArrayList<DealerServiceEntriesPartDto>();
				
				for (Object[] result : results) {
					dealerServiceEntriesPart = new DealerServiceEntriesPartDto();

					dealerServiceEntriesPart.setDealerServiceEntriesPartId((Long) result[0]);
					dealerServiceEntriesPart.setPartId((Long) result[1]);
					if(dealerServiceEntriesPart.getPartId() <= 0) {
						dealerServiceEntriesPart.setPartName((String) result[2]);
					}else {
						dealerServiceEntriesPart.setPartName((String) result[3]);
					}
					dealerServiceEntriesPart.setPartQuantity((Double) result[4]);
					dealerServiceEntriesPart.setPartEchCost((Double) result[5]);
					dealerServiceEntriesPart.setPartDiscount((Double) result[6]);
					dealerServiceEntriesPart.setPartTax((Double) result[7]);
					dealerServiceEntriesPart.setPartTotalCost((Double) result[8]);
					if( result[9] != null) {
						dealerServiceEntriesPart.setPartDiscountCost((Double) result[9]);
					}else {
						dealerServiceEntriesPart.setPartDiscountCost(0.0);
					}
					if( result[10] != null) {
						dealerServiceEntriesPart.setPartTaxCost((Double) result[10]);
					}else {
						dealerServiceEntriesPart.setPartTaxCost(0.0);
					}
					if( result[11] != null) {
						dealerServiceEntriesPart.setLastOccurredDseId((Long) result[11]);
						dealerServiceEntriesPart.setLastOccurredDseNumber((Long) result[12]);
					}
					if(result[13] != null) {
						dealerServiceEntriesPart.setInvoiceDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[13], DateTimeUtility.DD_MM_YYYY));
					}else {
						dealerServiceEntriesPart.setInvoiceDateStr("");
					}
					if(result[14] != null) {
						dealerServiceEntriesPart.setVehicleOdometer((Integer) result[14]);
					}
					if(result[15] != null) {
						dealerServiceEntriesPart.setPartNumber((String) result[15]);
					}else {
						dealerServiceEntriesPart.setPartNumber("");
					}
					dealerServiceEntriesPart.setPartDiscountTaxTypeId((short) result[16]);
					if( result[17] != null) {
						dealerServiceEntriesPart.setWarrantyApplicable((boolean) result[17]);
						dealerServiceEntriesPart.setWarrantyInMonths((Double) result[18]);
						
					}
					dealerServiceEntriesPartList.add(dealerServiceEntriesPart);
				}
			}
			
			return dealerServiceEntriesPartList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public ValueObject searchDealerServiceEntriesByNumber(ValueObject valueObject) throws Exception {
		try {
			DealerServiceEntries dealerServiceEntries	= dealerRepository.searchDealerServiceEntriesByNumber(valueObject.getLong("dealerServiceEntriesNumber"), valueObject.getInt("companyId"));
			if(dealerServiceEntries != null) {
				valueObject.put("dealerServiceEntriesId", dealerServiceEntries.getDealerServiceEntriesId());
			}else {
				valueObject.put("noRecordFound", true);
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public ValueObject searchDealerServiceEntriesByFilter(ValueObject valueObject) throws Exception {
		String query 	= "";
		String filter	= "";
		List<DealerServiceEntriesDto> dealerServiceEntriesList = null;
		try {
			
			filter	= valueObject.getString("filter","");
			if(filter != null && !filter.trim().equalsIgnoreCase("") && filter.indexOf('\'') != 0 ) {
				query = " AND (lower(DSE.dealerServiceEntriesNumber) Like ('%" + filter + "%') OR lower(V.vehicle_registration) Like ('%" + filter + "%') OR lower(DSE.invoiceNumber) Like ('%" + filter + "%') )" ;
			}
			valueObject.put("query", query);
			dealerServiceEntriesList = getDealerServiceEntriesDetailList(valueObject);
			 valueObject.put("dealerServiceEntriesList", dealerServiceEntriesList);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Transactional
	@Override
	public ValueObject completeDealerServiceEntries(ValueObject valueObject) throws Exception {
		Issues								issue							= null;
		IssuesTasks							issueTask						= null;
		List<DealerServiceEntriesPart>		dealerServiceEntriesPartList	= null;
		List<DealerServiceEntriesLabour>	dealerServiceEntriesLabourList	= null;
		short								statusId						= 0;
		List<PartWarrantyDetails>			partWarrantyDetailsList			= null;
		DealerServiceRemark					dealerServiceRemark 			= null;
		HashMap<String, Object> 			configuration					= null;
		try {
			configuration =companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.DSE_CONFIGURATION_CONFIG);
			DealerServiceEntries dealerServiceEntries =getDealerServiceEntriesById(valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
			if(!valueObject.getBoolean("partNotApplicableId",false)) {
				dealerServiceEntriesPartList =	dealerPartRepository.getDealerServiceEntriesPartByDealerServiceEntriesId(valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
				if(dealerServiceEntriesPartList == null || dealerServiceEntriesPartList.isEmpty()) {
					valueObject.put("partDeatilsNotFound", true);
					return valueObject;
				}
			}
			partWarrantyDetailsList = partWarrantyDetailsRepository.getPartWarrantyDetailsByServiceIdAndStatus(valueObject.getLong("dealerServiceEntriesId"), PartWarrantyDetailsBL.UNASIGNED , valueObject.getInt("companyId"));
			if(partWarrantyDetailsList != null && !partWarrantyDetailsList.isEmpty()) {
				valueObject.put("unAssigned", true);
				return valueObject;
			}
			
			if(!valueObject.getBoolean("laboutNotApplicableId",false)) {
				dealerServiceEntriesLabourList =	dealerLabourRepository.getDealerServiceEntriesLabourByDealerServiceEntriesId(valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
				if(dealerServiceEntriesLabourList == null || dealerServiceEntriesLabourList.isEmpty()) {
					valueObject.put("labourDeatilsNotFound", true);
					return valueObject;
				}
			}
			rescheduleServiceReminder(valueObject);
			
			if(valueObject.getDouble("invoiceCost",0) > 0 ) {
				saveVendorPaymentDetails(valueObject);
				saveProfitAndLossDetails(valueObject); 
			 }
			
			if(dealerServiceEntries.getIssueIds() != null && !dealerServiceEntries.getIssueIds().isEmpty()) {
				
				String[] issueIds =	dealerServiceEntries.getIssueIds().split(",");
				
				for(String issueId : issueIds) {
					issue 		= issuesService.getIssueDetailsByIssueId(Long.parseLong(issueId), valueObject.getInt("companyId"));
					if(issue != null) {
						issueTask 	= issuesBL.prepareIssuesTaskDetailsForDSEComplete(issue,valueObject);
						issuesService.registerNew_IssuesTasks(issueTask);
						issuesService.updateCreateDSE_IssueStatus(IssuesTypeConstant.ISSUES_STATUS_RESOLVED, valueObject.getLong("userId"), issueTask.getISSUES_CREATED_DATE(), valueObject.getLong("dealerServiceEntriesId"), Long.parseLong(issueId),valueObject.getInt("companyId"));
					}
					
				}
			}
			if(valueObject.getShort("paymentTypeId") == PaymentTypeConstant.PAYMENT_TYPE_CREDIT ) {
				statusId = DealerServiceEntriesConstant.DSE_PAYMENT_PENDING_STATUS_ID;
			}else {
				statusId = DealerServiceEntriesConstant.DSE_ACCOUNT_CLOSE_STATUS_ID;
				
			}
			if(dealerServiceEntries != null) {
			if(dealerServiceEntries.getAccidentId() != null && dealerServiceEntries.getAccidentId() > 0) {
				VehicleAccidentDetails	accidentDetails	= accidentDetailsService.getVehicleAccidentDetailsById(dealerServiceEntries.getAccidentId());
				if(accidentDetails != null) {
					if( accidentDetails.getStatus() >= AccidentStatus.ACCIDENT_STATUS_QUOTATION_APPROVED) {
						dealerRepository.updateDealerServiceEntriesStatus(valueObject.getString("remark"),statusId,valueObject.getLong("userId"),DateTimeUtility.getCurrentTimeStamp(), DateTimeUtility.getCurrentTimeStamp(),valueObject.getBoolean("partNotApplicableId"),valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"),valueObject.getBoolean("laboutNotApplicableId"));
						if((boolean) configuration.getOrDefault("multipleQuotation", false)){
							boolean complete= accidentDetailsService.checkAllServiceComplete(dealerServiceEntries.getAccidentId(), valueObject.getInt("companyId"));
							if(complete)
								accidentDetailsService.updateAccidentDetailsStatus(dealerServiceEntries.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_SERVICE_COMPLETED);
						}else {
							accidentDetailsService.updateAccidentDetailsStatus(dealerServiceEntries.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_SERVICE_COMPLETED);
						}
					}else {
						valueObject.put("quotationNotApproved", true);
						return valueObject;
					}
				}
			}else {
				dealerRepository.updateDealerServiceEntriesStatus(valueObject.getString("remark"),statusId,valueObject.getLong("userId"),DateTimeUtility.getCurrentTimeStamp(), DateTimeUtility.getCurrentTimeStamp(),valueObject.getBoolean("partNotApplicableId"),valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"),valueObject.getBoolean("laboutNotApplicableId"));
			}
			}
			if(dealerServiceEntries != null && dealerServiceEntries.getDriverId() != null && dealerServiceEntries.getDriverId() <= 0 && dealerServiceEntries.getAssignToId() != null && dealerServiceEntries.getAssignToId() <= 0)
			dealerRepository.updateDriverAndAssignTo(valueObject.getInt("driverId"), valueObject.getLong("assignToId"), valueObject.getLong("dealerServiceEntriesId"),valueObject.getInt("companyId"));
			valueObject.put("statusId", DealerServiceEntriesConstant.DSE_REMARK_COMPLETE_STATUS_ID);
			dealerServiceRemark =	dealerServiceEntriesBL.prepareDealerServiceEntriesRemark(valueObject);
			dealerServiceRemarkRepository.save(dealerServiceRemark);
			tyreUsageHistoryService.saveDSETyreUsageHistory(valueObject);
			
			HashMap<String,Object> companyConfiguration =companyConfigurationService.getCompanyConfiguration( valueObject.getInt("companyId"),PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG );
			if((boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails",false)) {
				ValueObject bankPaymentValueObject = JsonConvertor
						.toValueObjectFormSimpleJsonString(valueObject.getString("bankPaymentDetails"));
				bankPaymentValueObject.put("oldPaymentTypeId", dealerServiceEntries.getPaymentTypeId());
				bankPaymentValueObject.put("bankPaymentTypeId", dealerServiceEntries.getPaymentTypeId());
				bankPaymentValueObject.put("currentPaymentTypeId", dealerServiceEntries.getPaymentTypeId());
				bankPaymentValueObject.put("userId", valueObject.getLong("userId"));
				bankPaymentValueObject.put("companyId", valueObject.getInt("companyId"));
				bankPaymentValueObject.put("moduleId", dealerServiceEntries.getDealerServiceEntriesId());
				bankPaymentValueObject.put("moduleNo", dealerServiceEntries.getDealerServiceEntriesNumber());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.DEALER_SERVICE);
				bankPaymentValueObject.put("amount", dealerServiceEntries.getTotalInvoiceCost());
				//bankPaymentValueObject.put("paidDate",dealerServiceEntries.getInvoiceDate());
				Vendor	vendor	=  vendorService.getVendor(dealerServiceEntries.getVendorId());
				bankPaymentValueObject.put("remark", "Dealer Service Entry DSE-"+dealerServiceEntries.getDealerServiceEntriesNumber()+" vendor : "+vendor.getVendorName());
				
				bankPaymentService.updatePaymentDetailsFromValuObject(bankPaymentValueObject);
				}
				
			valueObject.put("success", true);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	
	@Transactional
	public void saveVendorPaymentDetails(ValueObject valueObject) throws Exception {
		try {
			if(valueObject.getShort("paymentTypeId") == PaymentTypeConstant.PAYMENT_TYPE_CREDIT ) {
				PendingVendorPayment	vendorPayment	=	PendingVendorPaymentBL.createPendingVendorPaymentDTOForDSE(valueObject);
				vendorPaymentService.savePendingVendorPayment(vendorPayment);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Transactional
	public void saveProfitAndLossDetails(ValueObject valueObject) throws Exception {
		 VehicleDto 								checkVehicleStatus 			= null; 
		 ValueObject								object						=	null;
		 ValueObject								ownerShipObject				=	null;
		 VehicleProfitAndLossTxnChecker				profitAndLossTxnChecker		= null;
		 VehicleAgentTxnChecker						agentTxnChecker				= null;
		try {
			checkVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("vid"));
			object	= new ValueObject();
			
			object.put(VehicleProfitAndLossDto.COMPANY_ID, valueObject.getInt("companyId"));
			object.put(VehicleProfitAndLossDto.TRANSACTION_ID, valueObject.getLong("dealerServiceEntriesId"));
			object.put(VehicleProfitAndLossDto.TRANSACTION__TYPE_ID, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
			object.put(VehicleProfitAndLossDto.TRANSACTION_SUB_TYPE_ID, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES);
			object.put(VehicleProfitAndLossDto.TRANSACTION_VID, valueObject.getInt("vid"));
			object.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, valueObject.getLong("dealerServiceEntriesId"));
			
			profitAndLossTxnChecker	= txnCheckerBL.getVehicleProfitAndLossTxnChecker(object);
			vehicleProfitAndLossTxnCheckerService.saveVehicleProfitAndLossTxnChecker(profitAndLossTxnChecker);
	 
			if(checkVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED ){
				ownerShipObject	= new ValueObject();
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, valueObject.getLong("dealerServiceEntriesId"));
				ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, valueObject.getInt("vid"));
				ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, valueObject.getDouble("invoiceCost",0));
				ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
				ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_DEALER_SERVICE_ENTRY);
				ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, valueObject.getInt("companyId"));
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, DateTimeUtility.getCurrentTimeStamp());
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, VehicleAgentConstant.TXN_IDENTIFIER_DEALER_SERVICE_ENTRY_NAME);
				ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "DSE Number : "+valueObject.getLong("dealerServiceEntriesNumber"));
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
				ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, valueObject.getLong("userId"));
				ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, -valueObject.getDouble("invoiceCost",0));
				
				agentTxnChecker	= agentTxnCheckerBL.getVehicleAgentTxnChecker(ownerShipObject);
				vehicleAgentTxnCheckerService.save(agentTxnChecker);
				
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER, agentTxnChecker);
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_CHECKER_ID, agentTxnChecker.getVehicleAgentTxnCheckerId());
			}
		
			
			ValueObject		ProfitAndLossValueObject	=  new ValueObject();
			
			ProfitAndLossValueObject.put(""+VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES_NAME+"", valueObject);
			ProfitAndLossValueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
			ProfitAndLossValueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES);
			ProfitAndLossValueObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
			
			new Thread() {
				public void run() {
					try {
						vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(ProfitAndLossValueObject);
						vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(ProfitAndLossValueObject);
						vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(ProfitAndLossValueObject);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}		
			}.start();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Transactional
	public void rescheduleServiceReminder(ValueObject valueObject) throws Exception {
		List<ServiceReminder> 				serviceReminderList 	= null;
		List<EmailAlertQueue>  				emailPendingList		= null;
		List<SmsAlertQueue>  				smsPendingList			= null;
		
		try {
			if(valueObject.getString("serviceReminderIds","") != "") {
				serviceReminderList = serviceReminderService.getServiceReminderListByServiceReminderIds(valueObject.getString("serviceReminderIds"),valueObject.getInt("companyId"));
			}
					
			if (serviceReminderList != null && !serviceReminderList.isEmpty()) {
				
				for (ServiceReminder serviceReminder : serviceReminderList) {
		
					ServiceReminder service = dealerServiceEntriesBL.dealerServiceToServiceReminder(serviceReminder,valueObject);
					service = serviceReminderService.updateServiceReminder(service);
					
					java.util.Date ServiceReminderDueDate = serviceReminder.getTime_servicedate();
					ServiceReminderHistory srHistory = new ServiceReminderHistory();
					
					srHistory.setDealerServiceEntriesId(service.getDealerServiceEntriesId());
					srHistory.setService_reminderId(service.getService_id());
					srHistory.setCompanyId(valueObject.getInt("companyId"));
					srHistory.setCreated(new java.util.Date());	
					srHistory.setServiceType(WorkOrdersType.SERVICE_REMINDER_DSE);
					srHistory.setServiceDueDate(ServiceReminderDueDate);
						
					serviceReminderHistoryRepository.save(srHistory);
				
					emailPendingList	= emailAlertQueueService.getAllEmailAlertQueueDetailsById(serviceReminder.getService_id());
					
					emailAlertQueueService.deleteEmailAlertQueue(serviceReminder.getService_id());
					
					serviceReminderEmailAlert(emailPendingList,service);
					
					
					
					smsPendingList	= smsAlertQueueService.getAllSmsAlertQueueDetailsById(serviceReminder.getService_id());
					
					smsAlertQueueService.deleteSmsAlertQueue(serviceReminder.getService_id());
					
					serviceReminderSmsAlert(smsPendingList,service);
					
						
					
					DealerServiceReminderHistory	orderHistory =	dealerServiceEntriesBL.prepareDealerServiceReminderHistory(serviceReminder, valueObject);
					DealerServiceReminderHistory	oldHistory   =	serviceReminderService.getDealerServiceReminderHistory(valueObject.getLong("dealerServiceEntriesId"));
					if(oldHistory != null) {
						orderHistory.setDealerServiceReminderHistoryId(oldHistory.getDealerServiceReminderHistoryId());
					}
					
					orderHistoryRepository.save(orderHistory);
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	@Transactional
	public void serviceReminderEmailAlert(List<EmailAlertQueue>  pendingList ,ServiceReminder service) throws Exception {
		EmailAlertQueue 					email 					= null;
		try {
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
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Transactional
	public void serviceReminderSmsAlert(List<SmsAlertQueue>  smsPendingList ,ServiceReminder service) throws Exception {
		SmsAlertQueue 						sms 					= null;
		try {
			if(smsPendingList != null && !smsPendingList.isEmpty()) {
				
				for(SmsAlertQueue	smsAlertQueue : smsPendingList) {
					
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
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Transactional
	@Override
	public void deleteDealerServiceEntriesPart(ValueObject valueObject) throws Exception {
		List<PartWarrantyDetails> partWarrantyDetailsList = null;
 		try {
 			partWarrantyDetailsList = new ArrayList<>();
 			partWarrantyDetailsList = partWarrantyDetailsRepository.getPartWarrantyDetailsByServicePartId(valueObject.getLong("dealerServiceEntriesId"),valueObject.getLong("dealerServiceEntriesPartId"), valueObject.getInt("companyId"));
			if(partWarrantyDetailsList != null && !partWarrantyDetailsList.isEmpty()) {
				for(PartWarrantyDetails obj : partWarrantyDetailsList) {
					if(obj.getReplaceInServiceId() > 0) {
						entityManager.createQuery(
								"Update PartWarrantyDetails SET partWarrantyStatusId ="+PartWarrantyDetailsBL.ASSIGNED+" "
										+ "WHERE partWarrantyDetailsId = " + obj.getReplacePartWarrantyDetailsId() + "  AND companyId = " + valueObject.getInt("companyId")+ " ").executeUpdate();
					}
				}
				entityManager.createQuery(
						"Update PartWarrantyDetails SET markForDelete = 1"
								+ "WHERE serviceId = " + valueObject.getLong("dealerServiceEntriesId") + " AND servicePartId ="+valueObject.getLong("dealerServiceEntriesPartId")+" AND companyId = " + valueObject.getInt("companyId")+ " ").executeUpdate();
			}
 			dealerPartRepository.deleteDealerServiceEntriesPart(valueObject.getLong("userId"),DateTimeUtility.getCurrentTimeStamp(), valueObject.getLong("dealerServiceEntriesPartId"), valueObject.getInt("companyId"));
			updateDealerServiceEntriesInvoiceCost(valueObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Transactional
	@Override
	public void deleteDealerServiceEntriesLabour(ValueObject valueObject) throws Exception {
		try {
			dealerLabourRepository.deleteDealerServiceEntriesLabour(valueObject.getLong("userId"),DateTimeUtility.getCurrentTimeStamp(), valueObject.getLong("dealerServiceEntriesLabourId"), valueObject.getInt("companyId"));
			updateDealerServiceEntriesInvoiceCost(valueObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	public void updateDealerServiceEntriesInvoiceCost(ValueObject valueObject) throws Exception {
		List<DealerServiceEntriesPart> 		dealerServiceEntriesPart 		= null;
		List<DealerServiceEntriesLabour> 		dealerServiceEntriesLabour 		= null;
		try {
			dealerServiceEntriesLabour = dealerLabourRepository.getDealerServiceEntriesLabourByDealerServiceEntriesId(valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
			dealerServiceEntriesPart   = dealerPartRepository.getDealerServiceEntriesPartByDealerServiceEntriesId(valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
			
			if(dealerServiceEntriesLabour != null && !dealerServiceEntriesLabour.isEmpty() && dealerServiceEntriesPart != null && !dealerServiceEntriesPart.isEmpty()) {
				entityManager.createQuery(
						"UPDATE DealerServiceEntries AS DSE SET  "
								+ " DSE.totalInvoiceCost = ((SELECT SUM(partTotalCost) FROM DealerServiceEntriesPart WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 )+(SELECT SUM(totalCost) FROM DealerServiceEntriesLabour WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 )),"
								+ " DSE.balanceAmount = ((SELECT SUM(partTotalCost) FROM DealerServiceEntriesPart WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 )+(SELECT SUM(totalCost) FROM DealerServiceEntriesLabour WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 )),"
								+ " DSE.totalServiceCost = ((SELECT SUM(partTotalCost) FROM DealerServiceEntriesPart WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 )+(SELECT SUM(totalCost) FROM DealerServiceEntriesLabour WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 )),"
								+ " DSE.discountAmount = ((SELECT SUM(partDiscountCost) FROM DealerServiceEntriesPart WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 )+(SELECT SUM(labourDiscountCost) FROM DealerServiceEntriesLabour WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 )),"
								+ " DSE.taxableAmount = ((SELECT SUM(partTaxCost) FROM DealerServiceEntriesPart WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 )+(SELECT SUM(labourTaxCost) FROM DealerServiceEntriesLabour WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 ))"
								+ " WHERE DSE.dealerServiceEntriesId = " + valueObject.getLong("dealerServiceEntriesId") + " ")
				.executeUpdate();
				
				
			}else if((dealerServiceEntriesLabour == null || dealerServiceEntriesLabour.isEmpty()) && dealerServiceEntriesPart != null) {
				entityManager.createQuery(
						"UPDATE DealerServiceEntries AS DSE SET "
								+ " DSE.totalInvoiceCost =  (SELECT SUM(partTotalCost) FROM DealerServiceEntriesPart WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 ),"
								+ " DSE.balanceAmount =  (SELECT SUM(partTotalCost) FROM DealerServiceEntriesPart WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 ),"
								+ " DSE.totalServiceCost =  (SELECT SUM(partTotalCost) FROM DealerServiceEntriesPart WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 ),"
								+ " DSE.discountAmount = (SELECT SUM(partDiscountCost) FROM DealerServiceEntriesPart WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 ),"
								+ " DSE.taxableAmount = (SELECT SUM(partTaxCost) FROM DealerServiceEntriesPart WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 )"
								+ " WHERE DSE.dealerServiceEntriesId = " + valueObject.getLong("dealerServiceEntriesId") + " ")
				.executeUpdate();
			}else if(dealerServiceEntriesLabour != null && (dealerServiceEntriesPart == null || dealerServiceEntriesPart.isEmpty())) {
				entityManager.createQuery(
						"UPDATE DealerServiceEntries AS DSE SET  "
								+ " DSE.totalInvoiceCost = (SELECT SUM(totalCost) FROM DealerServiceEntriesLabour WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 ),"
								+ " DSE.balanceAmount = (SELECT SUM(totalCost) FROM DealerServiceEntriesLabour WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 ),"
								+ " DSE.totalServiceCost = (SELECT SUM(totalCost) FROM DealerServiceEntriesLabour WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0 ),"
								+ " DSE.discountAmount = (SELECT SUM(labourDiscountCost) FROM DealerServiceEntriesLabour WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0  ),"
								+ " DSE.taxableAmount = (SELECT SUM(labourTaxCost) FROM DealerServiceEntriesLabour WHERE dealerServiceEntriesId =" + valueObject.getLong("dealerServiceEntriesId") + " AND companyId = " + valueObject.getInt("companyId") + " AND markForDelete = 0  )"
								+ " WHERE DSE.dealerServiceEntriesId = " + valueObject.getLong("dealerServiceEntriesId") + " ")
				.executeUpdate();
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	@Transactional
	public ValueObject deleteDealerServiceEntries(ValueObject valueObject) throws Exception {
		List<DealerServiceEntriesLabourDto>	dealerServiceEntriesLabourList	= null;
		List<DealerServiceEntriesPartDto>	dealerServiceEntriesPartList	= null;
		String								query							= "";
		DealerServiceEntries				dealerServiceEntries			= null;
		Issues								issue							= null;
		IssuesTasks							issueTask						= null;
		HashMap<String,Object> 				configuration					= null;
		try {
			configuration =companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.DSE_CONFIGURATION_CONFIG);
			if(valueObject.getLong("dealerServiceEntriesId",0) > 0) {
				dealerServiceEntries = dealerRepository.getDealerServiceEntriesById(valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
				query = "AND DSE.dealerServiceEntriesId = "+valueObject.getLong("dealerServiceEntriesId")+"";
			}
			valueObject.put("query", query);
			dealerServiceEntriesLabourList 	= getDealerServiceEntriesLabourDetailList(valueObject);
			dealerServiceEntriesPartList 	= getDealerServiceEntriesPartDetailList(valueObject);
			
			if(dealerServiceEntriesLabourList != null && !dealerServiceEntriesLabourList.isEmpty()) {
				valueObject.put("labourDetailsFound", true);
				return valueObject;
			}
			if(dealerServiceEntriesPartList != null && !dealerServiceEntriesPartList.isEmpty()) {
				valueObject.put("partDetailsFound", true);
				return valueObject;
			}
			if(dealerServiceEntries != null && dealerServiceEntries.getIssueIds() != null && !dealerServiceEntries.getIssueIds().isEmpty() ) {
				
				String[] issueIds =	dealerServiceEntries.getIssueIds().split(",");
				
				for(String issueId : issueIds) {
					issue 		= issuesService.getIssueDetailsByIssueId(Long.parseLong(issueId), valueObject.getInt("companyId"));
					if(issue != null) {
						issueTask 	= issuesBL.prepareIssuesTaskDetailsForDSEDelete(issue,valueObject);
						issuesService.registerNew_IssuesTasks(issueTask);
						issuesService.updateCreateDSE_IssueStatus(IssuesTypeConstant.ISSUES_STATUS_OPEN, valueObject.getLong("userId"), issueTask.getISSUES_CREATED_DATE(), valueObject.getLong("dealerServiceEntriesId"), Long.parseLong(issueId),valueObject.getInt("companyId"));
						issuesService.update_DSE_Issue_Details(Long.parseLong(issueId),valueObject.getInt("companyId"));
					}
				}
			} 
			if(dealerServiceEntries != null && dealerServiceEntries.getAccidentId()!= null && dealerServiceEntries.getAccidentId()>0) {
				if((boolean) configuration.getOrDefault("multipleQuotation", false)){
					accidentDetailsService.deleteQuotationDetailsByservice(AccidentStatus.SERVICE_TYPE_DSE, dealerServiceEntries.getDealerServiceEntriesId(),valueObject.getInt("companyId"));
					List<AccidentQuotationDetails> quotationList=accidentDetailsService.getQuotationDetailsList(dealerServiceEntries.getAccidentId(), valueObject.getInt("companyId"));
					if(quotationList== null || quotationList.isEmpty())
						accidentDetailsService.updateAccidentDetailsStatus(dealerServiceEntries.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_SERVEY_COMPLETED);
				}else {
					accidentDetailsService.updateAccidentDetailsStatus(dealerServiceEntries.getAccidentId(), AccidentStatus.ACCIDENT_STATUS_SERVEY_COMPLETED);
					accidentDetailsService.updateAccidentDetailsServiceDetails((long)0, (short)0, dealerServiceEntries.getAccidentId());
				}
			}
			dealerServiceReminderRepository.deleteDealerServiceReminder(valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
			dealerRepository.deleteDealerServiceEntries(valueObject.getLong("userId"),DateTimeUtility.getCurrentTimeStamp(), valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
			bankPaymentService.deleteBankPaymentDetailsIfTransactionDeleted(valueObject.getLong("dealerServiceEntriesId"), ModuleConstant.DEALER_SERVICE, valueObject.getInt("companyId"),valueObject.getLong("userId"),false);
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	// form view page of DSE
	@Transactional
	@Override
	public void addDealerServiceEntriesLabourDetails(ValueObject valueObject) throws Exception {
		DealerServiceEntriesLabour				dealerServiceEntriesLabourBL			= null;
		Labour									prepareLabour								= null;
		Labour									savedLabour							= null;
		List<ValueObject> listofValue = null;
		try {

			listofValue= JsonConvertor.toValueObjectFromJsonString(valueObject.getString("labourDetails"));
			if(listofValue != null && !listofValue.isEmpty()) {
				for(ValueObject object:listofValue) {
					if(!object.getString("labourName","").equals("")) {
						prepareLabour = labourBL.prepareNewLabourFromDSE(object,valueObject);
						savedLabour = labourRepository.save(prepareLabour);
					}else {
						savedLabour=null;
					}
					if(savedLabour != null) {
						object.put("labourId", savedLabour.getLabourId());
					}
					dealerServiceEntriesLabourBL = dealerServiceEntriesBL.prepareDealerServiceEntriesLabour(object,valueObject);
					dealerLabourRepository.save(dealerServiceEntriesLabourBL);
				}
			}
			updateDealerServiceEntriesInvoiceCost(valueObject);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
	// form view page of DSE
	@Transactional
	@Override
	public void addDealerServiceEntriesPartDetails(ValueObject valueObject) throws Exception {
		ArrayList<ValueObject> valueObjectList = null;
		try {
			valueObjectList=	JsonConvertor.toValueObjectFromJsonString(valueObject.getString("partDetails"));
			if(valueObjectList != null && !valueObjectList.isEmpty()) {
				saveDealerServiceEntriesPart(valueObjectList, valueObject);
				updateDealerServiceEntriesInvoiceCost(valueObject);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ValueObject searchDealerServiceEntriesDateWise(ValueObject valueObject) throws Exception {
		String query 	= "";
		String filter	= "";
		long	vehicleId	= 0;
		List<DealerServiceEntriesDto> dealerServiceEntriesList = null;
		CustomUserDetails			           userDetails		= null;
		HashMap<String, Object>                configuration  = null;
		
		
		
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration =  companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.DSE_CONFIGURATION_CONFIG);
			
			filter		= valueObject.getString("invoiceDate","");
			vehicleId	= valueObject.getLong("vehicleId");
			
			if(filter != "") {
				if(vehicleId > 0)
					query = " AND invoiceDate = '"+valueObject.getString("invoiceDate")+"' AND DSE.vid = "+ vehicleId;
				else
					query = " AND invoiceDate = '"+valueObject.getString("invoiceDate")+"'";
			} else {
				query = " AND DSE.vid = " + vehicleId;
			}
			
			valueObject.put("query", query);
			dealerServiceEntriesList = getDealerServiceEntriesDetailList(valueObject);
			 valueObject.put("dealerServiceEntriesList", dealerServiceEntriesList);
			 valueObject.put("configuration", configuration);
				
			 
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public List<DealerServiceEntries> validateDealerServiceEntries(int vid, int vendorId, String invoiceNumber, int companyId) throws Exception {
		try {
			return dealerRepository.validateDealerServiceEntries(vid,vendorId,invoiceNumber,companyId);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	public List<DealerServiceEntries> validateDealerServiceEntriesOnUpdate(long dealerServiceEntriesId , int vid, int vendorId, String invoiceNumber, int companyId) throws Exception {
		try {
			return dealerRepository.validateDealerServiceEntriesOnUpdate(dealerServiceEntriesId,vid,vendorId,invoiceNumber,companyId);
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public ValueObject updateDealerServiceEntries(ValueObject valueObject, MultipartFile uploadfile) throws Exception {
		List<DealerServiceEntries>				validateDealerServiceEntries			= null;
		boolean									vehicleStatus							= false;
		VehicleDto 								checkVehicleStatus 						= null; 
		String 									paidDateQuery							= "";
		String 									invoiceDateQuery						= "";
		DealerServiceEntries					dealerServiceEntries					= null;
		DealerServiceEntries					oldDealerServiceEntries					= null;
		ArrayList<ValueObject>					labourDataArrayObj						= null;
		ArrayList<ValueObject>					partDataArrayObj						= null;
		String									serviceReminderIds						= "";
		HashMap<String, Object> 				configuration							= null;
		
		try {
			labourDataArrayObj 					= new ArrayList<>();
			partDataArrayObj 					= new ArrayList<>();
			validateDealerServiceEntries		= new ArrayList<>();
			configuration =  companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), PropertyFileConstants.DSE_CONFIGURATION_CONFIG);
			checkVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(valueObject.getInt("vid"));
			if (checkVehicleStatus != null && (checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACTIVE  || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_TRIPROUTE || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_ACCIDENT || checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_WORKSHOP)) {
				vehicleStatus = true;
			} 
			
			if(vehicleStatus) {
				if(valueObject.getString("invoiceNumber","") != "") { 
					validateDealerServiceEntries = validateDealerServiceEntriesOnUpdate(valueObject.getLong("dealerServiceEntriesId"),valueObject.getInt("vid"),valueObject.getInt("vendorId"),valueObject.getString("invoiceNumber"),  valueObject.getInt("companyId"));
				}
				if(validateDealerServiceEntries != null && !validateDealerServiceEntries.isEmpty()) {
					valueObject.put("alreadyExist", true);
					return valueObject;
				}
				if(valueObject.getString("invoiceDate","") != "") {
					invoiceDateQuery = " invoiceDate = '"+DateTimeUtility.getTimeStamp(valueObject.getString("invoiceDate"))+"'";
				}else {
					invoiceDateQuery = " invoiceDate = NULL ";
					
				}
					
				if(valueObject.getShort("paymentTypeId")  == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) {
					valueObject.put("vendorPaymentStatusId",  ServiceEntriesType.PAYMENT_MODE_NOT_PAID);
					paidDateQuery = ", paidDate = NULL ";
				}else {
					valueObject.put("vendorPaymentStatusId",  ServiceEntriesType.PAYMENT_MODE_PAID);
					paidDateQuery = ", paidDate = '"+DateTimeUtility.getCurrentTimeStamp()+"' ";
				}
				if(!valueObject.getString("serviceReminderId", "").equals("") || (boolean)configuration.getOrDefault("showServRemindWhileCreating", false)) {
					serviceReminderIds = ", serviceReminderIds ='"+Utility.removeLastComma(valueObject.getString("serviceReminderId"))+"'";
				}
				oldDealerServiceEntries = dealerRepository.getDealerServiceEntriesById(valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
				valueObject.put("paidDateQuery", paidDateQuery);
				
				entityManager.createQuery(
						"UPDATE DealerServiceEntries  SET vid = "+valueObject.getInt("vid")+", vehicleOdometer = "+valueObject.getInt("vehicleOdometer",0)+","
						+ " vendorId = "+valueObject.getInt("vendorId")+", paymentTypeId = "+valueObject.getShort("paymentTypeId")+","
						+ " "+invoiceDateQuery+", jobNumber ='"+valueObject.getString("jobNumber","")+"', "
						+ " payNumber ='"+valueObject.getString("transactionNumber","")+"', lastModifiedById="+valueObject.getLong("userId")+","
						+ " lastUpdatedOn = '"+DateTimeUtility.getCurrentTimeStamp()+"', invoiceNumber = '"+valueObject.getString("invoiceNumber","")+"',"
						+ " vendorPaymentStatusId = "+valueObject.getShort("vendorPaymentStatusId",(short)0)+" , driverId="+valueObject.getInt("driverId",0)+", meterNotWorkingId = "+valueObject.getBoolean("meterNotWorkingId",false)+"  "
						+ " "+valueObject.getString("paidDateQuery","")+", assignToId ="+valueObject.getLong("assignToId",0)+" "+serviceReminderIds+""
						+ " WHERE dealerServiceEntriesId = " + valueObject.getLong("dealerServiceEntriesId") + " AND companyId="+valueObject.getInt("companyId")+"")
				.executeUpdate();
				
				labourDataArrayObj	= (ArrayList<ValueObject>) valueObject.get("labourDetails");
				partDataArrayObj	= (ArrayList<ValueObject>) valueObject.get("partDetails");
				
				if(labourDataArrayObj != null && !labourDataArrayObj.isEmpty()) {
					saveDealerServiceEntriesLabour(labourDataArrayObj, valueObject);
				}
				if(partDataArrayObj != null && !partDataArrayObj.isEmpty()) {
					for (ValueObject object : partDataArrayObj) {
						object.put("vid", valueObject.getInt("vid"));
						object.put("invoiceDate", valueObject.getString("invoiceDate",""));
						object.put("dealerServiceEntriesId", valueObject.getLong("dealerServiceEntriesId"));
						object.put("userId", valueObject.getLong("userId"));
						object.put("companyId", valueObject.getInt("companyId"));
						object.put("partDiscountTaxTypeId", valueObject.getInt("partDiscountTaxTypeId"));
						updateDealerServicePart(object);
					}
				}	
				updateDealerServiceEntriesInvoiceCost(valueObject);
				uploadDealerServiceEntryDocument(valueObject,uploadfile);
				
				dealerServiceEntries = dealerRepository.getDealerServiceEntriesById(valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
				if(valueObject.getBoolean("allowBankPaymentDetails",false)){
				ValueObject bankPaymentValueObject=JsonConvertor.toValueObjectFormSimpleJsonString(valueObject.getString("bankPaymentDetails"));
				bankPaymentValueObject.put("oldPaymentTypeId",oldDealerServiceEntries.getPaymentTypeId());
				bankPaymentValueObject.put("bankPaymentTypeId", valueObject.getShort("paymentTypeId"));
				bankPaymentValueObject.put("currentPaymentTypeId", valueObject.getShort("paymentTypeId"));
				bankPaymentValueObject.put("userId",valueObject.getLong("userId"));
				bankPaymentValueObject.put("companyId", valueObject.getInt("companyId"));
				bankPaymentValueObject.put("moduleId",dealerServiceEntries.getDealerServiceEntriesId());
				bankPaymentValueObject.put("moduleNo", dealerServiceEntries.getDealerServiceEntriesNumber());
				bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.DEALER_SERVICE);
				bankPaymentValueObject.put("amount",dealerServiceEntries.getTotalInvoiceCost());
				
				Vendor	vendor	=  vendorService.getVendor(dealerServiceEntries.getVendorId());
				bankPaymentValueObject.put("remark", "Update Dealer Service Entry DSE-"+dealerServiceEntries.getDealerServiceEntriesNumber()+" vendor : "+vendor.getVendorName());
				
				
				bankPaymentService.updatePaymentDetailsFromValuObject(bankPaymentValueObject);
				}
				
				if(valueObject.getString("serviceReminderId", null) != null) {
					addDealerServiceReminder(dealerServiceEntries);
				}
				
				updateVehicleOdometer(dealerServiceEntries);
			
			}else {
				if (checkVehicleStatus != null && checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SOLD) {
					valueObject.put("inSold", true);
				} else if (checkVehicleStatus != null && checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_INACTIVE) {
					valueObject.put("inActive", true);
				} else if (checkVehicleStatus != null && checkVehicleStatus.getvStatusId() == VehicleStatusConstant.VEHICLE_STATUS_SURRENDER) {
					valueObject.put("inSurrender", true);
				}
			}
			
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Override
	public List<MonthWiseVehicleExpenseDto> getMonthWiseDealerServiceEntriesExpenseDtoByVid(Integer vid, String fromDate,
			String toDate, Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.totalInvoiceCost), MVE.companyId  "
							+ " FROM DealerServiceEntries MVE "
							+ " where MVE.vid = "+vid+" AND MVE.invoiceDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
							+ " AND MVE.markForDelete = 0 AND MVE.totalInvoiceCost > 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<MonthWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					MonthWiseVehicleExpenseDto	mExpenseDto = new MonthWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setCompanyId((Integer) result[1]);
					mExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES);
					mExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES_NAME);
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	

	@Override
	public List<DealerServiceEntriesDto> getDealerServiceEntriesServiceListOfMonth(TripSheetIncomeDto incomeDto) throws Exception {
		List<DealerServiceEntriesDto> DealerServiceEntriesList = null;
		try {

			TypedQuery<Object[]> querySearch = entityManager.createQuery(
					"SELECT SR.dealerServiceEntriesId, SR.dealerServiceEntriesNumber, SR.vid, SR.totalInvoiceCost, SR.completedDate "
							+ " from DealerServiceEntries SR "
							+ " where  SR.vid = "+incomeDto.getVid()+" AND SR.completedDate between '"+incomeDto.getFromDate()+"' AND '"+incomeDto.getToDate()+"' "
							+ " AND SR.companyId = "+incomeDto.getCompanyId()+" AND SR.statusId = "+DealerServiceEntriesConstant.DSE_ACCOUNT_CLOSE_STATUS_ID+" AND SR.markForDelete = 0",
					Object[].class);

			List<Object[]> results = querySearch.getResultList();

			
			if (results != null && !results.isEmpty()) {
				DealerServiceEntriesList = new ArrayList<DealerServiceEntriesDto>();
				DealerServiceEntriesDto dealerServiceEntriesDto = null;
				for (Object[] result : results) {
					dealerServiceEntriesDto = new DealerServiceEntriesDto();

					dealerServiceEntriesDto.setDealerServiceEntriesId((Long) result[0]);
					dealerServiceEntriesDto.setDealerServiceEntriesNumber((Long) result[1]);
					dealerServiceEntriesDto.setVid((Integer) result[2]);
					dealerServiceEntriesDto.setTotalInvoiceCost((Double) result[3]);
					dealerServiceEntriesDto.setCompletedDateStr(dateFormat.format(result[4]));
					
					DealerServiceEntriesList.add(dealerServiceEntriesDto);
				}
			}
			return DealerServiceEntriesList;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseDealerServiceEntriesExpenseDtoByVid(Integer vid, String fromDate,
			String toDate, Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.totalInvoiceCost), MVE.companyId  "
							+ " FROM DealerServiceEntries MVE "
							+ " where MVE.vid = "+vid+" AND MVE.invoiceDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.markForDelete = 0 AND MVE.totalInvoiceCost > 0 ", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setCompanyId((Integer) result[1]);
					mExpenseDto.setExpenseType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES);
					mExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES_NAME);
					mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES_NAME);
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	@Transactional
	public void updateApprovalIdToDealerServiceEntries(String dealerServiceEntriesId, Long approvalId, short status, Integer companyId)
			throws Exception {
		entityManager.createQuery(
				" UPDATE DealerServiceEntries SET vendorApprovalId = "+approvalId+", vendorPaymentStatusId =  "+status+" "
						+ " WHERE dealerServiceEntriesId IN ( "+dealerServiceEntriesId+") AND companyId = "+companyId+" ").executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateVendorPaymentDetails(Long approvalId, short paymentStatus, String paymentDate) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		entityManager.createQuery("  UPDATE DealerServiceEntries SET paidDate ='" + paymentDate+"', statusId = "+DealerServiceEntriesConstant.DSE_ACCOUNT_CLOSE_STATUS_ID+", " 
				+ " vendorPaymentStatusId =  "+paymentStatus+", balanceAmount = 0 , paidAmount = totalInvoiceCost, paidById ="+userDetails.getId()+""
				+ " WHERE vendorApprovalId = "+approvalId+" ").executeUpdate();
		
	}
	
	
	public ValueObject getLastOccurredDsePartDetails(ValueObject valueObject) throws Exception {
		try {
			
			Query query = entityManager
					.createQuery("SELECT DP.DealerServiceEntriesPartId, DP.dealerServiceEntriesId, DSE.dealerServiceEntriesNumber, DSE.invoiceDate, DSE.vehicleOdometer From DealerServiceEntriesPart AS DP "
							+ " INNER JOIN  DealerServiceEntries AS DSE ON DSE.dealerServiceEntriesId = DP.dealerServiceEntriesId AND DSE.markForDelete = 0 "
							+ " Where DP.partId=" + valueObject.getLong("partId")+" AND DP.companyId = "+valueObject.getInt("companyId")+" AND DSE.vid ="+valueObject.getInt("vid",0)+" "
							+ " AND DP.markForDelete = 0 AND DSE.statusId IN ("+DealerServiceEntriesConstant.DSE_PAYMENT_PENDING_STATUS_ID+","+DealerServiceEntriesConstant.DSE_ACCOUNT_CLOSE_STATUS_ID+")ORDER BY  DP.DealerServiceEntriesPartId DESC");
			query.setMaxResults(1);
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException e) {
				
			}
			DealerServiceEntriesPartDto dto = new DealerServiceEntriesPartDto();
			if (result != null) {
				dto.setDealerServiceEntriesPartId((Long) result[0]);
				dto.setDealerServiceEntriesId((Long) result[1]);
				dto.setDealerServiceEntriesNumber((Long) result[2]);
				dto.setInvoiceDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[3], DateTimeUtility.DD_MM_YYYY));
				if(result[4] != null) {
					dto.setVehicleOdometer((Integer) result[4]);
				}
			} 
			valueObject.put("lastOccuredPartDetails", dto);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Transactional
	@Override
	public ValueObject saveInvoiceDetails(ValueObject valueObject) throws Exception {
		List<DealerServiceEntries>	validateDealerServiceEntries = null;
		try {
			if(valueObject.getString("invoiceNumber","") != "") {
				validateDealerServiceEntries = validateDealerServiceEntries(valueObject.getInt("vid"),valueObject.getInt("vendorId"),valueObject.getString("invoiceNumber"),  valueObject.getInt("companyId"));
			}
			if(validateDealerServiceEntries != null && !validateDealerServiceEntries.isEmpty()) {
				valueObject.put("alreadyExist", true);
				return valueObject;
			}
			java.util.Date date = dateFormat.parse(valueObject.getString("invoiceDate"));
			java.sql.Date invoiceDate = new java.sql.Date(date.getTime());
			
			entityManager.createQuery(
					" UPDATE DealerServiceEntries SET invoiceDate = '"+invoiceDate+"', invoiceNumber =  '"+valueObject.getString("invoiceNumber","")+"' "
							+ " WHERE dealerServiceEntriesId = "+valueObject.getLong("dealerServiceEntriesId")+" AND companyId = "+valueObject.getInt("companyId")+" ").executeUpdate();
			valueObject.put("success", true);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ValueObject saveNewDealerServicePartInMasterPart(ValueObject valueObject) throws Exception {
		MasterParts								masterParts							= null;
		MasterParts								savedMasterPart						= null;
		try {
			List<MasterParts> validate=masterPartsService.GetMasterPartValidate(valueObject.getString("partNumber","").trim().toUpperCase(), valueObject.getString("partName","").trim().toUpperCase(), valueObject.getInt("companyId",0));
			if(validate == null || validate.isEmpty()) {
			 masterParts	= MasterPartsBL.getMasterPartsDto(valueObject);
			 masterParts.setOwnMaterParts(true);
			 masterParts.setAutoMasterPart(true);
			 savedMasterPart = masterPartsDao.save(masterParts);
			 valueObject.put("savedMasterPart", savedMasterPart);
			}else {
				 valueObject.put("already", true);
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	public ValueObject addDealerServiceExtraIssue(ValueObject valueObject) throws Exception {
		DealerServiceExtraIssue				dealerServiceExtraIssueBL 		= null;
		ValueObject							dseExtraIssueObject				= null;
		List<DealerServiceExtraIssue>				validateDseExtraIssue 			= null;
		try {
			
			dseExtraIssueObject 	= getDealerServiceExtraIssue(valueObject);
			validateDseExtraIssue	=  (List<DealerServiceExtraIssue>) dseExtraIssueObject.get("dealerServiceExtraIssue");
			if(validateDseExtraIssue != null && !validateDseExtraIssue.isEmpty()) {
				for(DealerServiceExtraIssue obj : validateDseExtraIssue) {
					if(obj.getDescription().equals(valueObject.getString("description"))) {
						valueObject.put("alreadyExist", true);
						return valueObject;
					}
					
				}
			}
			dealerServiceExtraIssueBL = dealerServiceEntriesBL.prepareDealerServiceExtraIssue(valueObject);
			dseExtraIssueRepository.save(dealerServiceExtraIssueBL);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	public List<DealerServiceExtraIssue> getAllDealerServiceExtraIssue(Integer companyId) throws Exception {
		try {
			return	dseExtraIssueRepository.getAllDealerServiceExtraIssue(companyId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	public ValueObject getDealerServiceExtraIssue(ValueObject valueObject) throws Exception {
		List<DealerServiceExtraIssue>	dealerServiceExtraIssue = null;
		try {
			dealerServiceExtraIssue = dseExtraIssueRepository.getDealerServiceExtraIssue(valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
			valueObject.put("dealerServiceExtraIssue", dealerServiceExtraIssue);
			return	valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	public ValueObject saveNewVendorFromDse(ValueObject valueObject) throws Exception {
		Vendor	vendor = null;
		VendorSequenceCounter sequenceCounter = null;
		try {
			vendor = vendorBL.prepareVendorFormDse(valueObject);
			sequenceCounter = vendorSequenceService.findNextVendorNumber(valueObject.getInt("companyId"));
			vendor.setVendorNumber((int) sequenceCounter.getNextVal());
			
			vendor = vendorRepository.save(vendor);
			valueObject.put("vendor", vendor);
			return	valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	public ValueObject changeDseStatus(ValueObject valueObject) throws Exception {
		DealerServiceRemark			dealerServiceRemark = null;
		try {
			dealerRepository.changeDseStatus(valueObject.getString("remark",""), valueObject.getShort("statusId"),valueObject.getLong("userId"),DateTimeUtility.getCurrentTimeStamp(), valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
			
			dealerServiceRemark =	dealerServiceEntriesBL.prepareDealerServiceEntriesRemark(valueObject);
			dealerServiceRemarkRepository.save(dealerServiceRemark);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	public ValueObject reopenDealerService(ValueObject valueObject) throws Exception {
		DealerServiceRemark			dealerServiceRemark = null;
		Issues 						issue				= null;
		IssuesTasks 				issueTask			= null;
		
		try {
			DealerServiceEntries	entries	= 	dealerRepository.getDealerServiceEntriesById(valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"));
			
			if(entries.getPaymentTypeId() ==  PaymentTypeConstant.PAYMENT_TYPE_CREDIT  && entries.getPaidDate() != null) {
				valueObject.put("accountClose", true);
				return valueObject;
			}
			
			if(entries.getAccidentId() != null && entries.getAccidentId() > 0) {
				VehicleAccidentDetails	accidentDetails	= accidentDetailsService.getVehicleAccidentDetailsById(entries.getAccidentId());
				if(accidentDetails.getStatus() == AccidentStatus.ACCIDENT_STATUS_QUOTATION_CREATED || accidentDetails.getStatus() == AccidentStatus.ACCIDENT_STATUS_FINAL_SERVEY_FOR_DAMAGE || accidentDetails.getStatus() == AccidentStatus.ACCIDENT_STATUS_KEEP_OPEN || accidentDetails.getStatus() == AccidentStatus.ACCIDENT_STATUS_BEFORE_FINAL_APPROVAL){
					//need no action if contains these  status 
				}else if(accidentDetails.getStatus() < AccidentStatus.ACCIDENT_STATUS_FINAL_SERVEY_DONE) {
					accidentDetailsService.updateAccidentDetailsStatus(valueObject.getLong("accidentId",0), AccidentStatus.ACCIDENT_STATUS_QUOTATION_APPROVED);
				}else {
					valueObject.put("accidentEntryApproved", "Cannot Re-Open Service Entry As Vehicle Accident Status is "+AccidentStatus.getStatusName(accidentDetails.getStatus()));
					return valueObject;
				}
			}
			
			if(entries != null && entries.getIssueIds() != null && !entries.getIssueIds().isEmpty()) {
				
				String[] issueIds =	entries.getIssueIds().split(",");
				
				for(String issueId : issueIds) {
					issue 		= issuesService.getIssueDetailsByIssueId(Long.parseLong(issueId), valueObject.getInt("companyId"));
					if(issue != null) {
						//prepareIssuesTaskDetailsForDSEReopen
						issueTask 	= issuesBL.prepareIssuesTaskDetailsForDSEReopen(issue,valueObject);
						issuesService.registerNew_IssuesTasks(issueTask);
						issuesService.updateCreateDSE_IssueStatus(IssuesTypeConstant.ISSUES_STATUS_OPEN, valueObject.getLong("userId"), issueTask.getISSUES_CREATED_DATE(), valueObject.getLong("dealerServiceEntriesId"), Long.parseLong(issueId),valueObject.getInt("companyId"));
						issuesService.update_DSE_Issue_Details(Long.parseLong(issueId),valueObject.getInt("companyId"));
					}
				}
			} 
			
			VehicleDto	CheckVehicleStatus = vehicleService.Get_Vehicle_Current_Status_TripSheetID(entries.getVid(),valueObject.getInt("companyId"));
			ValueObject	ownerShipObject	= null;
			if(CheckVehicleStatus.getVehicleOwnerShipId() == VehicleOwnerShip.OWNER_SHIP_TYPE_ATTACHED && entries.getTotalServiceCost() > 0){
				ownerShipObject	= new ValueObject();
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_TYPE, VehicleAgentConstant.TXN_TYPE_EXPENSE);
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ID, entries.getDealerServiceEntriesId());
				ownerShipObject.put(VehicleAgentConstant.VEHICLE_ID, entries.getVid());
				ownerShipObject.put(VehicleAgentConstant.DEBIT_AMOUNT, entries.getTotalServiceCost());
				ownerShipObject.put(VehicleAgentConstant.CREDIT_AMOUNT, 0.0);
				ownerShipObject.put(VehicleAgentConstant.IDENTIFIER, VehicleAgentConstant.TXN_IDENTIFIER_SERVICE_ENTRY);
				ownerShipObject.put(VehicleAgentConstant.COMPANY_ID, valueObject.getInt("companyId"));
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_DATE, dateFormatSQL.parse(dateFormatSQL.format(entries.getCompletedDate())));
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_ACCOUNT, "Service Entry");
				ownerShipObject.put(VehicleAgentConstant.NUMBER_WITH_TYPE, "Service Number : "+entries.getDealerServiceEntriesNumber());
				ownerShipObject.put(VehicleAgentConstant.TRANSACTION_REMARK, "--");
				ownerShipObject.put(VehicleAgentConstant.CREATED_BY_ID, valueObject.getLong("userId"));
				ownerShipObject.put(VehicleAgentConstant.CLOSING_AMOUNT, entries.getTotalServiceCost());
				
				vehicleAgentIncomeExpenseDetailsService.deleteVehicleAgentIncomeExpense(ownerShipObject);
			}
			
			VehicleExpenseDetails	vehicleExpenseDetails	= vehicleExpenseDetailsService.getVehicleExpenseDetails(entries.getDealerServiceEntriesId(), valueObject.getInt("companyId") ,entries.getVid(), VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES);
			
			if(vehicleExpenseDetails != null) {
				ValueObject		valueObjectPL	= new ValueObject();
				valueObjectPL.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES);
				valueObjectPL.put("txnAmount", entries.getTotalServiceCost());
				valueObjectPL.put("transactionDateTime", DateTimeUtility.geTimeStampFromDate(entries.getCompletedDate()));
				valueObjectPL.put("txnTypeId", entries.getDealerServiceEntriesId());
				valueObjectPL.put("vid", entries.getVid());
				valueObjectPL.put("companyId", valueObject.getInt("companyId") );
				
				
				new Thread() {
					public void run() {
						try {
							vehicleProfitAndLossService.runThreadForDeleteVehicleExpenseDetails(valueObjectPL);
							vehicleProfitAndLossService.runThreadForDeleteDateWiseVehicleExpenseDetails(valueObjectPL);
							vehicleProfitAndLossService.runThreadForDeleteMonthWiseVehicleExpenseDetails(valueObjectPL);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}		
				}.start();
			}
			
			if(entries.getPaymentTypeId() ==  PaymentTypeConstant.PAYMENT_TYPE_CREDIT ) {
				vendorPaymentService.deletePendingVendorPayment(valueObject.getLong("dealerServiceEntriesId"), PendingPaymentType.PAYMENT_TYPE_DEALER_SERVICE_ENTRIES);
			}
			
			dealerRepository.updateDealerServiceEntriesStatus(valueObject.getString("remark"),DealerServiceEntriesConstant.DSE_OPEN_STATUS_ID,valueObject.getLong("userId"),DateTimeUtility.getCurrentTimeStamp(), DateTimeUtility.getCurrentTimeStamp(),entries.isPartApplicable(),valueObject.getLong("dealerServiceEntriesId"), valueObject.getInt("companyId"),entries.isLabourApplicable());
			
			
			valueObject.put("statusId", DealerServiceEntriesConstant.DSE_REMARK_REOPEN_STATUS_ID);
			dealerServiceRemark =	dealerServiceEntriesBL.prepareDealerServiceEntriesRemark(valueObject);
			dealerServiceRemarkRepository.save(dealerServiceRemark);
			
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Transactional
	@Override
	public ValueObject updateDealerServicePart(ValueObject valueObject) throws Exception {
		try {
			DealerServiceEntriesPartDto				oldDealerServiceEntriesPart				= null;
			DealerServiceEntriesPart				dealerServiceEntriesPartBL				= null;
			ValueObject								lastOccurredPartObject					= null;
			DealerServiceEntriesPartDto				lastOccurredDealerServiceEntriesPart	= null;
			int										quantity								= 0;
			PartWarrantyDetails						warrantyBL								= null;
			List<PartWarrantyDetails>				partWarrantyDetailsList					= null;
			List<PartWarrantyDetails>				partWarrantyDetailsBLList					= null;
			String									query									= "";
			List<DealerServiceEntriesPartDto>		dealerServiceEntriesPartList				= null;
			
		
			try {
				partWarrantyDetailsList					= new ArrayList<>();
				
				partWarrantyDetailsBLList				= new ArrayList<>();
				lastOccurredPartObject 					= getLastOccurredDsePartDetails(valueObject);
				lastOccurredDealerServiceEntriesPart 	= (DealerServiceEntriesPartDto) lastOccurredPartObject.get("lastOccuredPartDetails");
				
				if(lastOccurredDealerServiceEntriesPart != null) {
					valueObject.put("lastOccurredDealerServiceEntriesId", lastOccurredDealerServiceEntriesPart.getDealerServiceEntriesId());
				}
				
				query = "AND DSE.DealerServiceEntriesPartId = "+valueObject.getLong("dealerServiceEntriesPartId")+"";
				valueObject.put("query", query);
				dealerServiceEntriesPartList = getDealerServiceEntriesPartDetailList(valueObject);
				
				if(dealerServiceEntriesPartList != null && !dealerServiceEntriesPartList.isEmpty()) {
					oldDealerServiceEntriesPart 	= dealerServiceEntriesPartList.get(0);
				}
				
				if(valueObject.getBoolean("isWarrantyApplicable")) {
					if(oldDealerServiceEntriesPart.getPartId().equals(valueObject.getLong("partId")) && (valueObject.getDouble("partQty")) > oldDealerServiceEntriesPart.getPartQuantity()) {
						quantity = (int)(valueObject.getDouble("partQty")-oldDealerServiceEntriesPart.getPartQuantity());
						
					}else {
						partWarrantyDetailsList = partWarrantyDetailsRepository.getPartWarrantyDetailsByServicePartId(valueObject.getLong("dealerServiceEntriesId"),valueObject.getLong("dealerServiceEntriesPartId"), valueObject.getInt("companyId"));
						if(!oldDealerServiceEntriesPart.getPartId().equals(valueObject.getLong("partId"))) { 
							deletePreviousPartWarrantyDetails(valueObject,partWarrantyDetailsList);
							quantity = (int) valueObject.getDouble("partQty");
						}else if(oldDealerServiceEntriesPart.getPartId().equals(valueObject.getLong("partId")) && (valueObject.getDouble("partQty")) < oldDealerServiceEntriesPart.getPartQuantity() ) {
							if(partWarrantyDetailsList != null && !partWarrantyDetailsList.isEmpty()) {
								quantity = updateParWarrantyWhenQtyLess(valueObject,partWarrantyDetailsList).getInt("quantity");
							}
						}
					}
					
					for(int i= 0 ; i < quantity; i++) {
						warrantyBL = partWarrantyBL.preparePartWarrantyDetails(valueObject, valueObject);
						if(warrantyBL != null) {
							partWarrantyDetailsBLList.add(warrantyBL);
							
						}
					}
					partWarrantyDetailsRepository.saveAll(partWarrantyDetailsBLList);
					
					
				}else if(!valueObject.getBoolean("isWarrantyApplicable") && oldDealerServiceEntriesPart.isWarrantyApplicable()) {
					partWarrantyDetailsList = partWarrantyDetailsRepository.getPartWarrantyDetailsByServicePartId(valueObject.getLong("dealerServiceEntriesId"),valueObject.getLong("dealerServiceEntriesPartId"), valueObject.getInt("companyId"));
					
					if(partWarrantyDetailsList != null && !partWarrantyDetailsList.isEmpty()) {
						deletePreviousPartWarrantyDetails(valueObject,partWarrantyDetailsList);
					}
				}
				
				dealerServiceEntriesPartBL 		= dealerServiceEntriesBL.prepareDealerServiceEntriesPart(valueObject,valueObject);
				dealerPartRepository.save(dealerServiceEntriesPartBL);
				
				updateDealerServiceEntriesInvoiceCost(valueObject);
				
				return valueObject;
				
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Transactional
	@Override
	public ValueObject deletePreviousPartWarrantyDetails(ValueObject valueObject,List<PartWarrantyDetails>	partWarrantyDetailsList	) throws Exception {
		try {
			if(partWarrantyDetailsList != null && !partWarrantyDetailsList.isEmpty()) {
				for(PartWarrantyDetails obj : partWarrantyDetailsList) {
					if(obj.getReplaceInServiceId() > 0) {
						entityManager.createQuery(
								"Update PartWarrantyDetails SET partWarrantyStatusId ="+PartWarrantyDetailsBL.ASSIGNED+" "
										+ "WHERE partWarrantyDetailsId = " + obj.getReplacePartWarrantyDetailsId() + "  AND companyId = " + valueObject.getInt("companyId")+ " ").executeUpdate();
					}
				}
				entityManager.createQuery(
						"Update PartWarrantyDetails SET markForDelete = 1"
								+ "WHERE serviceId = " + valueObject.getLong("dealerServiceEntriesId") + " AND servicePartId ="+valueObject.getLong("dealerServiceEntriesPartId")+" AND companyId = " + valueObject.getInt("companyId")+ " ").executeUpdate();
			}
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	@Transactional
	@Override
	public ValueObject updateParWarrantyWhenQtyLess(ValueObject valueObject,List<PartWarrantyDetails> partWarrantyDetailsList	) throws Exception {
		int										assignCount							= 0;
		List<PartWarrantyDetails>				assignPartWarrantyList					= null;
		int										quantity							= 0;							
		try {
			if(partWarrantyDetailsList != null && !partWarrantyDetailsList.isEmpty()) {
				assignPartWarrantyList 	= partWarrantyDetailsRepository.getAssignPart(valueObject.getLong("dealerServiceEntriesId"),valueObject.getLong("dealerServiceEntriesPartId"), valueObject.getInt("companyId"));
				if(assignPartWarrantyList != null && !assignPartWarrantyList.isEmpty()) {
					assignCount 	= assignPartWarrantyList.size();

					if((int) valueObject.getDouble("partQty") < assignCount) {
						deletePreviousPartWarrantyDetails(valueObject, partWarrantyDetailsList);
						quantity = (int) valueObject.getDouble("partQty");	

					}else if((int) valueObject.getDouble("partQty") == assignCount) {
						partWarrantyDetailsRepository.deleteAllUnAssignPart(valueObject.getLong("dealerServiceEntriesPartId"), PartWarrantyDetailsBL.UNASIGNED, valueObject.getInt("companyId"));

					}else if((int) valueObject.getDouble("partQty") > assignCount) {

						int removeQty = partWarrantyDetailsList.size() - (int) valueObject.getDouble("partQty");
						deleteLimitedUnAssignPartWarrnty(valueObject, removeQty);

					}

				}else {
					int removeQty = partWarrantyDetailsList.size() - (int) valueObject.getDouble("partQty");
					deleteLimitedUnAssignPartWarrnty(valueObject, removeQty);

				}

			}
			valueObject.put("quantity", quantity);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Transactional
	@Override
	public void deleteLimitedUnAssignPartWarrnty(ValueObject valueObject,int  removeQty) throws Exception {
		try {
			entityManager.createNativeQuery(
					"Update PartWarrantyDetails SET markForDelete = 1 "
					+ "WHERE serviceId = " + valueObject.getLong("dealerServiceEntriesId") + " AND "
					+ " servicePartId ="+valueObject.getLong("dealerServiceEntriesPartId")+" AND partWarrantyStatusId = "+ PartWarrantyDetailsBL.UNASIGNED+" AND"
					+ " companyId = " + valueObject.getInt("companyId")+ " AND markForDelete = 0 ORDER BY partWarrantyDetailsId DESC LIMIT "+removeQty+" ").executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	
	@Transactional
	@Override
	public ValueObject updateDealerServiceLabour(ValueObject valueObject) throws Exception {
		try {
			addDealerServiceEntriesLabourDetails(valueObject);
			return valueObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject getVendorWisePreviousDsePartRate(ValueObject valueObject) throws Exception {
		try {
			
			Query query = entityManager
					.createQuery("SELECT DP.DealerServiceEntriesPartId, DP.dealerServiceEntriesId, DP.partEchCost, DP.partDiscount, DP.partTax,DP.partDiscountTaxTypeId From DealerServiceEntriesPart AS DP "
							+ " INNER JOIN  DealerServiceEntries AS DSE ON DSE.dealerServiceEntriesId = DP.dealerServiceEntriesId AND DSE.markForDelete = 0 "
							+ " Where DP.partId=" + valueObject.getLong("partId")+" AND DP.companyId = "+valueObject.getInt("companyId")+" AND DSE.vendorId ="+valueObject.getInt("vendorId",0)+" "
							+ " AND DP.markForDelete = 0 AND DSE.statusId IN ("+DealerServiceEntriesConstant.DSE_PAYMENT_PENDING_STATUS_ID+", "+DealerServiceEntriesConstant.DSE_ACCOUNT_CLOSE_STATUS_ID+") ORDER BY  DP.DealerServiceEntriesPartId DESC");
			query.setMaxResults(1);
			Object[] result = null;

			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException e) {
				
			}
			DealerServiceEntriesPartDto dto = new DealerServiceEntriesPartDto();
			if (result != null) {
				dto.setDealerServiceEntriesPartId((Long) result[0]);
				dto.setDealerServiceEntriesId((Long) result[1]);
				dto.setPartEchCost((Double) result[2]);
				dto.setPartDiscount((Double) result[3]);
				dto.setPartTax((Double) result[4]);
				dto.setPartDiscountTaxTypeId((short) result[5]);
				dto.setPartDiscountTaxType(DealerServiceEntriesConstant.getDiscoutTaxType((short) result[5]));
			} 
			
			valueObject.put("lastPartRate", dto);
			return valueObject;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject getDseRemarks(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 				query 							= null;
		List<DealerServiceRemarkDto> 		dealerServiceRemarkList			= null;
		try {
			
			 query = entityManager
					.createQuery("SELECT DR.dealerServiceEntriesRemarkId, DR.remarkTypeId, DR.createdOn, DR.driverId, D.driver_firstname, D.driver_fathername, D.driver_Lastname,"
							+ " DR.assignee, U.firstName, DR.remark From DealerServiceRemark AS DR "
							+ " LEFT JOIN User U ON U.id = DR.assignee "
							+ " LEFT JOIN  Driver AS D ON D.driver_id = DR.driverId "
							+ " Where DR.dealerServiceEntriesId=" + valueObject.getLong("dealerServiceEntriesId")+" AND DR.companyId = "+valueObject.getInt("companyId")+" "
							+ " AND DR.markForDelete = 0 ORDER BY  DR.dealerServiceEntriesRemarkId DESC",Object[].class);
					
			List<Object[]> results = query.getResultList();


			if (results != null && !results.isEmpty()) {
				dealerServiceRemarkList = new ArrayList<DealerServiceRemarkDto>();
				
				for (Object[] result : results) {
					DealerServiceRemarkDto dto = new DealerServiceRemarkDto();
					dto.setDealerServiceEntriesRemarkId((Long)result[0]);
					dto.setRemarkTypeId((short)result[1]);
					dto.setRemarkType(DealerServiceEntriesConstant.getDealerServiceEntriesRemarkStatus(dto.getRemarkTypeId()));
					dto.setCreationDate(DateTimeUtility.getStringDateFromDate((Timestamp) result[2], DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
					dto.setDriverId((Integer)result[3]);
					if(result[4] != null  ) {
						dto.setDriverFirstName((String)result[4]);
					}else {
						dto.setDriverFirstName("");
					}
					if(result[5] != null  ) {
						dto.setDriverMiddleName((String)result[5]);
					}else {
						dto.setDriverMiddleName("");
					}
					if(result[6] != null  ) {
						dto.setDriverLastName((String)result[6]);
					}else {
						dto.setDriverLastName("");
					}
					dto.setDriverName(dto.getDriverFirstName()+" "+dto.getDriverMiddleName()+" "+dto.getDriverLastName() );
					if(result[7] != null  ) {
						dto.setAssignee((Long)result[7]);
						dto.setAssigneeName((String)result[8]);
					}
					dto.setRemark((String)result[9]);
					dealerServiceRemarkList.add(dto);
					
				}
			}
			
			
			
			valueObject.put("remarkList", dealerServiceRemarkList);
			return valueObject;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	@Override
	public ValueObject getDseExtraIssue(ValueObject valueObject) throws Exception {
		TypedQuery<Object[]> 				query 							= null;
		List<DealerServiceExtraIssueDto> 	dealerServiceExtraIssueList			= null;
		try {
			 query = entityManager
					.createQuery("SELECT DEI.dealerServiceExtraIssueId, DEI.description, DEI.creationOn FROM DealerServiceExtraIssue AS DEI"
							+ " Where DEI.dealerServiceEntriesId=" + valueObject.getLong("dealerServiceEntriesId")+" AND DEI.companyId = "+valueObject.getInt("companyId")+" "
							+ " AND DEI.markForDelete = 0 ORDER BY  DEI.dealerServiceExtraIssueId DESC",Object[].class);
					
			List<Object[]> results = query.getResultList();


			if (results != null && !results.isEmpty()) {
				dealerServiceExtraIssueList = new ArrayList<DealerServiceExtraIssueDto>();
				
				for (Object[] result : results) {
					DealerServiceExtraIssueDto dto = new DealerServiceExtraIssueDto();
					dto.setDealerServiceExtraIssueId((Long)result[0]);
					dto.setDescription((String)result[1]);
					dto.setCreationDate(DateTimeUtility.getStringDateFromDate((Timestamp) result[2], DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
					
					dealerServiceExtraIssueList.add(dto);
					
				}
			}
			
			valueObject.put("extraIssueList", dealerServiceExtraIssueList);
			return valueObject;
		
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	@Override
	@Transactional
	public void addDealerServiceReminder(DealerServiceEntries dealerServiceEntries)throws Exception {
		HashMap<Integer, ServiceReminderDto> 		serviceReminderHM 			= null;
		DealerServiceReminder						dealerServiceReminder		= null;
		List<DealerServiceReminder>					dealerServiceReminderList	= null;
		try {
			dealerServiceReminderList = new ArrayList<>();
			if(!dealerServiceEntries.getServiceReminderIds().equals("")) {
				serviceReminderHM = serviceReminderService.getJobtypeAndSubtypeFromServiceReminderId(dealerServiceEntries.getServiceReminderIds(), dealerServiceEntries.getCompanyId());
				ArrayList<String>	addedTask =  getDealerServiceReminder(dealerServiceEntries.getDealerServiceEntriesId(),dealerServiceEntries.getCompanyId());
				if(serviceReminderHM != null && !serviceReminderHM.isEmpty()) {
					for (Integer key : serviceReminderHM.keySet()) {
						if(addedTask == null ) {
							dealerServiceReminder = new DealerServiceReminder();
							dealerServiceReminder.setDealerServiceEntriesId(dealerServiceEntries.getDealerServiceEntriesId());
							dealerServiceReminder.setServiceId(serviceReminderHM.get(key).getService_id());
							dealerServiceReminder.setServiceTypeId(serviceReminderHM.get(key).getServiceTypeId());
							dealerServiceReminder.setServiceSubTypeId(serviceReminderHM.get(key).getServiceSubTypeId());
							dealerServiceReminder.setCreatedById(dealerServiceEntries.getCreatedById());
							dealerServiceReminder.setLastModifiedById(dealerServiceEntries.getLastModifiedById());
							dealerServiceReminder.setCreationOn(dealerServiceEntries.getCreationOn());
							dealerServiceReminder.setLastUpdatedOn(dealerServiceEntries.getLastUpdatedOn());
							dealerServiceReminder.setCompanyId(dealerServiceEntries.getCompanyId());
							dealerServiceReminder.setMarkForDelete(false);
							dealerServiceReminderList.add(dealerServiceReminder);
						}else if(addedTask != null && !addedTask.contains(serviceReminderHM.get(key).getServiceTypeId()+"_"+serviceReminderHM.get(key).getServiceSubTypeId())) {
							dealerServiceReminder = new DealerServiceReminder();
							dealerServiceReminder.setDealerServiceEntriesId(dealerServiceEntries.getDealerServiceEntriesId());
							dealerServiceReminder.setServiceId(serviceReminderHM.get(key).getService_id());
							dealerServiceReminder.setServiceTypeId(serviceReminderHM.get(key).getServiceTypeId());
							dealerServiceReminder.setServiceSubTypeId(serviceReminderHM.get(key).getServiceSubTypeId());
							dealerServiceReminder.setCreatedById(dealerServiceEntries.getCreatedById());
							dealerServiceReminder.setLastModifiedById(dealerServiceEntries.getLastModifiedById());
							dealerServiceReminder.setCreationOn(dealerServiceEntries.getCreationOn());
							dealerServiceReminder.setLastUpdatedOn(dealerServiceEntries.getLastUpdatedOn());
							dealerServiceReminder.setCompanyId(dealerServiceEntries.getCompanyId());
							dealerServiceReminder.setMarkForDelete(false);
							dealerServiceReminderList.add(dealerServiceReminder);
						}
					}
					dealerServiceReminderRepository.saveAll(dealerServiceReminderList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public ArrayList<String> getDealerServiceReminder(Long DealerServiceEntriesId, Integer comapanyId) throws Exception {
		
		ArrayList<String> addedTask	= null;
		try {
			
			TypedQuery <Object[]> queryCreate = entityManager.createQuery("SELECT w.serviceTypeId , w.serviceSubTypeId "
					+ " FROM DealerServiceReminder as w where w.dealerServiceEntriesId = "+DealerServiceEntriesId+" AND markForDelete = 0 AND companyId = "+comapanyId+"",Object[].class);
			
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
	
	@Transactional(readOnly = true)
	public DealerServiceEntries getDealerServiceEntriesById(Long dealerServiceEntriesId, Integer companyId) {
		return dealerRepository.getDealerServiceEntriesById(dealerServiceEntriesId, companyId);
	}
	
	public ValueObject backdateOdometerValidation(ValueObject object) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			String reportedDate = object.getString("invoiceDate") ;
			String mainConditionQ =" I.companyId = "+userDetails.getCompany_id()+" AND I.vid = "+object.getInt("vid",0)+"  ";
			if(object.getBoolean("fromEdit",false) && object.getLong("id", 0) > 0) {
				mainConditionQ += " AND I.voh_updateId <> "+ object.getLong("id")+" " ;
			}
			
			VehicleDto preIssue = getPreviousOdoFromHistory(mainConditionQ+" AND I.voh_date < '"+reportedDate+" "+DateTimeUtility.DAY_START_TIME+"' ", "ORDER BY I.voh_date DESC");
			VehicleDto nextIssue = getPreviousOdoFromHistory(mainConditionQ+" AND I.voh_date > '"+reportedDate+" "+ DateTimeUtility.DAY_END_TIME+"' AND I.vehicle_Odometer > 0 ", "ORDER BY I.voh_date ASC");
			issuesService.getMaxAndMinOdometer(object, nextIssue, preIssue, reportedDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	@Override
	public Integer caluculateDiffrence(VehicleDto nextIssue , VehicleDto preIssue) {
		int diff =0;
		int odo = 0 ;
		try {
	
			diff = (nextIssue.getModuleOdometer()-1) - (preIssue.getModuleOdometer()+1);
			if(diff >= nextIssue.getVehicle_ExpectedOdameter()) {
				if(nextIssue.getVehicle_ExpectedOdameter() != null)
				odo =(preIssue.getModuleOdometer()+1 ) + (nextIssue.getVehicle_ExpectedOdameter() - 1);
			}else{
				odo = nextIssue.getModuleOdometer()-1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return odo;
	}
	
	public VehicleDto getPreviousOdoFromHistory(String conditionQ,String orderQ) throws Exception {
		Object[] vehicle = null;
		try {
			Query query = null;
				query = entityManager.createQuery(
						"SELECT V.vid, V.vehicle_registration, V.vehicle_Odometer,V.vehicle_ExpectedOdameter, V.vehicleGPSId,V.vStatusId,"
						+ " I.voh_date , I.vehicle_Odometer, I.voh_updateId "
								+ " FROM Vehicle AS V " 
								+ " INNER JOIN VehicleOdometerHistory I ON I.vid = V.vid AND I.markForDelete = 0 "
								+ " WHERE "+conditionQ+" "
								+ " "+orderQ+" ");
				
			try {
				query.setMaxResults(1);
				vehicle = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				//ignore
			}

			if (vehicle == null || vehicle.length == 0) {
				return null;
			}
			VehicleDto select = new VehicleDto();
				select.setVid((Integer) vehicle[0]);
				select.setVehicle_registration((String) vehicle[1]);
				select.setVehicle_Odometer((Integer) vehicle[2]);
				select.setVehicle_ExpectedOdameter((Integer) vehicle[3]);
				select.setVehicleGPSId((String) vehicle[4]);
				select.setvStatusId((short) vehicle[5]);
				select.setVehicle_Status(VehicleStatusConstant.getVehicleStatus(select.getvStatusId()));
				if(vehicle[6] != null)
				select.setDate((Timestamp) vehicle[6]);
				select.setModuleOdometer((Integer)vehicle[7]);
				select.setId ((long) vehicle[8]);
			return select;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
	
	public Object[] getUserActivityCount(ValueObject object) throws Exception {
		
		Query query = null;
		String fromDate ="startDate",toDate="endDate";
		 query = entityManager.createQuery("select count(PO) ,(select count(CO)  From DealerServiceEntries as CO where CO.companyId ="+object.getInt("companyId", 0)+" AND CO.creationOn between '"+object.getString(fromDate)+"' AND '"+object.getString(toDate)+"' "+object.getString("userCreatedCond","")+" ),"
				 +"(select count(UO)  From DealerServiceEntries as UO where UO.companyId ="+object.getInt("companyId", 0)+" AND UO.lastUpdatedOn between '"+object.getString(fromDate)+"' AND '"+object.getString(toDate)+"' AND UO.creationOn <> UO.lastUpdatedOn AND UO.markForDelete = 0 "+object.getString("userUpdatedCond","")+"  ) " 
				+" from DealerServiceEntries as PO where PO.companyId = "+object.getInt("companyId", 0)+" AND PO.markForDelete = 1 AND PO.lastUpdatedOn between '"+object.getString(fromDate)+"' AND '"+object.getString(toDate)+"' "+object.getString("userDeletedCond","")+" ");
		 
		return (Object[]) query.getSingleResult();
	}
	
	public List<DealerServiceEntriesDto> getDSEListByUserActivity(String queryT ,String innerQuery, int companyId) throws Exception {
		List<DealerServiceEntriesDto>	dealerServiceEntriesList		= null;
		DealerServiceEntriesDto 	dealerServiceEntriesDto 		= null;
		TypedQuery<Object[]> 		query 							= null;
		try {
			query = entityManager.createQuery(
					  " SELECT DSE.dealerServiceEntriesId, DSE.dealerServiceEntriesNumber, DSE.vid, V.vehicle_registration,"
					+ " DSE.vendorId , VN.vendorName, DSE.invoiceNumber,DSE.jobNumber, DSE.invoiceDate, DSE.totalInvoiceCost, "
					+ " U1.firstName, U2.firstName, DSE.statusId, DSE.paymentTypeId, DSE.vehicleOdometer,DSE.payNumber, DSE.paidDate,"
					+ " DSE.dealerServiceDocumentId, V.vehicle_chasis, V.vehicle_engine, VN.vendorAddress1, VN.vendorAddress2, "
					+ " DSE.serviceReminderIds, DSE.issueId, I.ISSUES_NUMBER, I.ISSUES_SUMMARY, DSE.driverId, D.driver_firstname, "
					+ " D.driver_Lastname, D.driver_fathername, DSE.remark, DSE.isPartApplicable, DSE.assignToId, U3.firstName, "
					+ " U4.firstName, U4.lastName, DSE.creationOn, DSE.lastUpdatedOn,DSE.isLabourApplicable,DSE.markForDelete "
					+ " FROM DealerServiceEntries AS DSE "
					+ " INNER JOIN Vehicle V ON V.vid = DSE.vid "
					+ " INNER JOIN User U2 ON U2.id = DSE.lastModifiedById "
					+ " INNER JOIN Vendor VN ON VN.vendorId = DSE.vendorId "
					+ " LEFT JOIN Driver D ON D.driver_id = DSE.driverId "
					+ " LEFT JOIN Issues I ON I.ISSUES_ID = DSE.issueId "
					+ " LEFT JOIN User U1 ON U1.id = DSE.paidById "
					+ " LEFT JOIN User U3 ON U3.id = DSE.assignToId "
					+" "+innerQuery+" "
					+ " where DSE.companyId = "+companyId+" "+queryT+"  ORDER BY DSE.dealerServiceEntriesId ASC",
					Object[].class);
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				dealerServiceEntriesList = new ArrayList<>();
				for (Object[] result : results) {
					dealerServiceEntriesDto = new DealerServiceEntriesDto();

					dealerServiceEntriesDto.setDealerServiceEntriesId((Long) result[0]);
					
					
					dealerServiceEntriesDto.setVid((Integer) result[2]);
					dealerServiceEntriesDto.setVehicleNumber((String) result[3]);
					dealerServiceEntriesDto.setVendorId((Integer) result[4]);
					dealerServiceEntriesDto.setVendorName((String) result[5]);
					if(result[6] != null) {
						dealerServiceEntriesDto.setInvoiceNumber((String) result[6]);
					}
					dealerServiceEntriesDto.setJobNumber((String) result[7]);
					if( result[8] != null) {
						dealerServiceEntriesDto.setInvoiceDate((Timestamp) result[8]);
						dealerServiceEntriesDto.setInvoiceDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[8], DateTimeUtility.DD_MM_YYYY));
					}else {
						dealerServiceEntriesDto.setInvoiceDateStr("");
					}
					if(result[9] != null) {
						dealerServiceEntriesDto.setTotalInvoiceCost((Double) result[9]);
					}else {
						dealerServiceEntriesDto.setTotalInvoiceCost(0.0);
					}
					dealerServiceEntriesDto.setPaidBy((String) result[10]);
					dealerServiceEntriesDto.setLastModifiedBy((String) result[11]);
					dealerServiceEntriesDto.setStatusId((short) result[12]);
					dealerServiceEntriesDto.setStatus(DealerServiceEntriesConstant.getDealerServiceEntriesStatus(dealerServiceEntriesDto.getStatusId()));
					dealerServiceEntriesDto.setPaymentTypeId((short) result[13]);
					dealerServiceEntriesDto.setPaymentType(PaymentTypeConstant.getPaymentTypeName(dealerServiceEntriesDto.getPaymentTypeId()));
					if(result[14] != null) {
						dealerServiceEntriesDto.setVehicleOdometer((Integer) result[14]);
					}
					dealerServiceEntriesDto.setPayNumber((String) result[15]);
					if( result[16] != null) {
						dealerServiceEntriesDto.setPaidDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[16], DateTimeUtility.DD_MM_YYYY));
					}
					if( result[17] != null) {
						dealerServiceEntriesDto.setDealerServiceDocumentId((Long) result[17]);
					}
					if(result[18] != null) {
						dealerServiceEntriesDto.setVehicleChasisNumber((String) result[18]);
					}
					if(result[19] != null) {
						dealerServiceEntriesDto.setVehicleEngineNumber((String) result[19]);
					}
					if(result[20] != null) {
						dealerServiceEntriesDto.setVendorAddress((String) result[20]);
					}
					if(result[20] != null && result[21] != null) {
						dealerServiceEntriesDto.setVendorAddress((String) result[20] +""+ (String) result[21]);
					}
					if(result[22] != null) {
						dealerServiceEntriesDto.setServiceReminderIds((String) result[22]);
					}
					if(result[23] != null) {
						dealerServiceEntriesDto.setIssueId((Long) result[23]);
						dealerServiceEntriesDto.setIssueNumberStr("I-"+(Long) result[24]);
						dealerServiceEntriesDto.setIssueEncryptId(AESEncryptDecrypt.encryptBase64("" + dealerServiceEntriesDto.getIssueId()));
						dealerServiceEntriesDto.setIssueSummary((String) result[25]);
					}
					if(result[26] != null) {
						dealerServiceEntriesDto.setDriverId((Integer) result[26]);
					}
					if(result[27] != null) {
						dealerServiceEntriesDto.setDriverFirstName((String) result[27]);
					}else {
						dealerServiceEntriesDto.setDriverFirstName("");
					}
					if(result[28] != null) {
						dealerServiceEntriesDto.setDriverLastName((String) result[28]);
					}else {
						dealerServiceEntriesDto.setDriverLastName("");
					}
					if(result[29] != null) {
						dealerServiceEntriesDto.setDriverFatherName((String) result[29]);
					}else {
						dealerServiceEntriesDto.setDriverFatherName("");
					}
					dealerServiceEntriesDto.setDriverFullName(dealerServiceEntriesDto.getDriverFirstName()+" "+dealerServiceEntriesDto.getDriverLastName()+" - "+dealerServiceEntriesDto.getDriverFatherName());
					
					if(result[30] != null) {
						dealerServiceEntriesDto.setRemark((String) result[30]);
					}else {
						dealerServiceEntriesDto.setRemark("");
					}
					dealerServiceEntriesDto.setDsePartStatus(DealerServiceEntriesConstant.getPartStatusOfDse((boolean) result[31]));
					if(result[32] != null && (Long) result[32] > 0) {
						dealerServiceEntriesDto.setAssignToId((Long) result[32]);
						dealerServiceEntriesDto.setAssignTo((String) result[33]);
					}else {
						dealerServiceEntriesDto.setAssignTo("");
					}
					dealerServiceEntriesDto.setCreatedBy( result[34]+" "+result[35]);
					if( result[36] != null)
					dealerServiceEntriesDto.setCreationDate(DateTimeUtility.getStringDateFromDate((Timestamp) result[36], DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
					if(result[37] != null)
					dealerServiceEntriesDto.setLastUpdatedDate(DateTimeUtility.getStringDateFromDate((Timestamp) result[37], DateTimeUtility.DD_MM_YYYY_HH_MM_AA));
					if(dealerServiceEntriesDto.getCreationDate() != null && dealerServiceEntriesDto.getLastUpdatedDate() != null && dealerServiceEntriesDto.getCreationDate().equals(dealerServiceEntriesDto.getLastUpdatedDate())) {
						dealerServiceEntriesDto.setLastUpdatedDate("");
					}
					dealerServiceEntriesDto.setLabourApplicable((boolean) result[38]);
					dealerServiceEntriesDto.setLabourStatus(DealerServiceEntriesConstant.getLabourApplicableStatus(dealerServiceEntriesDto.isLabourApplicable()));
					dealerServiceEntriesDto.setMarkForDelete((boolean) result[39]);
					if(dealerServiceEntriesDto.isMarkForDelete()) {
						dealerServiceEntriesDto.setDealerServiceEntriesNumberStr("<a style=\"color: red; background: #ffc;\" href=\"#\"  > DSE-"+(Long) result[1]+"</a>");
					}else {
						dealerServiceEntriesDto.setDealerServiceEntriesNumberStr("<a style=\"color: blue; background: #ffc;\" href=\"showDealerServiceEntries?dealerServiceEntriesId="+dealerServiceEntriesDto.getDealerServiceEntriesId()+" \" target=\"_blank\" > DSE-"+(Long) result[1]+"</a>");
					}
					
					dealerServiceEntriesList.add(dealerServiceEntriesDto);
				}
			}
			
			
			return dealerServiceEntriesList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
		
	public ValueObject getDSEReport(ValueObject object) {
		 int statusId = object.getInt("status",0),vehicleId=object.getInt("vehicle",0),vendorId=object.getInt("vendorId",0);
		 ExecutorService ex = Executors.newFixedThreadPool(3);
		 ValueObject valueOutObject = new ValueObject();
		try {
			CustomUserDetails userDetails= Utility.getObject(null);
			object.put("companyId",userDetails.getCompany_id());
			StringBuilder query = new StringBuilder(" AND DSE.invoiceDate between '"+object.getString("fromDate","")+""+DateTimeUtility.DAY_START_TIME+"' AND '"+object.getString("toDate","")+""+DateTimeUtility.DAY_END_TIME+"'  AND DSE.markForDelete = 0 ");
			if(statusId >0 )
				query.append(" AND DSE.statusId ="+statusId+" ");
			if(vehicleId > 0)
				query.append(" AND DSE.vid ="+vehicleId+" ");
			if(vendorId > 0)
				query.append(" AND DSE.vendorId ="+vendorId+" ");
			object.put("condition", query.toString());
			ex.execute(()->{
				try {
					List<DealerServiceEntriesDto> list =getDSEReportList(object);
					valueOutObject.put("list",list);
				} catch (Exception e) {
					e.printStackTrace();
	}
			});
			ex.execute(()->{
				try {
					List<DealerServiceEntriesDto> 		partList 			= getAllDSEPartList(object);
					Map<Long, DealerServiceEntriesDto> 	partHash 			= null;
						if(partList != null)	
					partHash=	partList.parallelStream().collect(Collectors.toMap(DealerServiceEntriesDto::getDealerServiceEntriesId,Function.identity(),(e1,e2)->e1)) ;
					
					List<DealerServiceEntriesDto> 		labourList 			= getAllDSELabourList(object);
					Map<Long, DealerServiceEntriesDto> 	labourHash 			= null;
						if(labourList != null)
					labourHash=labourList.parallelStream().collect(Collectors.toMap(DealerServiceEntriesDto::getDealerServiceEntriesId,Function.identity(),(e1,e2)->e1)) ;
					
					object.put("labourHash", labourHash);
					object.put("partHash", partHash);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			ex.execute(()->{
				ValueObject tableConfig				= new ValueObject();
				try {
					tableConfig.put("companyId", userDetails.getCompany_id());
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.DSE_REPORT_DATA_FILE_PATH);
					tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					valueOutObject.put("tableConfig", tableConfig.getHtData());
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		ex.shutdown();
		try {
			ex.awaitTermination(18, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			ex.shutdownNow();
		}
		
		return getFinalDseReportList(object,valueOutObject);
	}
	
	@SuppressWarnings("unchecked")
	public ValueObject getFinalDseReportList(ValueObject object,ValueObject outObject) {
		try {
		List<DealerServiceEntriesDto> list = (ArrayList<DealerServiceEntriesDto>) outObject.get("list");
		if(list != null) {

			Map<Long, DealerServiceEntriesDto> partHash =(HashMap<Long, DealerServiceEntriesDto>) object.get("partHash");
			Map<Long, DealerServiceEntriesDto> labourHash =(HashMap<Long, DealerServiceEntriesDto>) object.get("labourHash");
			
			for(DealerServiceEntriesDto dto :list ) {
				if(partHash != null) {
					DealerServiceEntriesDto tempDto = partHash.get(dto.getDealerServiceEntriesId());
					if(tempDto != null && tempDto.getTotalPartCost() != null) {
						dto.setTotalPartstr(tempDto.getTotalPartstr());
					}else{
						dto.setTotalPartstr("0");
					}
				}else {
					dto.setTotalPartstr("0");
				}
				if(labourHash != null) {
					DealerServiceEntriesDto tempDto = labourHash.get(dto.getDealerServiceEntriesId());
					if(tempDto != null && tempDto.getTotalLabourCost() != null) {
						dto.setTotalLabourstr(tempDto.getTotalLabourstr());
					}else{
						dto.setTotalLabourstr("0");
					}
				}else {
					dto.setTotalLabourstr("0");
				}
			}
		}
		outObject.put("list", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return outObject;
	}
	
	@Override
	public List<DealerServiceEntriesDto> getDSEReportList(ValueObject object) throws Exception {
		List<DealerServiceEntriesDto>	dealerServiceEntriesList		= null;
		DealerServiceEntriesDto 	dealerServiceEntriesDto 		= null;
		TypedQuery<Object[]> 		query 							= null;
		try {
			query =  entityManager.createQuery(
					  " SELECT DSE.dealerServiceEntriesId, DSE.dealerServiceEntriesNumber, DSE.vid, V.vehicle_registration,"
					+ " DSE.vendorId , VN.vendorName, DSE.invoiceNumber,DSE.jobNumber, DSE.invoiceDate, DSE.totalInvoiceCost, "
					+ " DSE.statusId, DSE.paymentTypeId, DSE.vehicleOdometer,DSE.payNumber, DSE.paidDate,DSE.driverId,D.driver_firstname,"
					+ " D.driver_Lastname, D.driver_fathername,DSE.remark"
					+ " FROM DealerServiceEntries AS DSE "
					+ " INNER JOIN Vehicle V ON V.vid = DSE.vid "
					+ " INNER JOIN Vendor VN ON VN.vendorId = DSE.vendorId "
					+ " LEFT JOIN Driver D ON D.driver_id = DSE.driverId "
					+ " where DSE.companyId = "+object.getInt("companyId",0)+" "+object.getString("condition","")+"  ORDER BY DSE.dealerServiceEntriesId ASC",Object[].class);
			
			List<Object[]> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				dealerServiceEntriesList = new ArrayList<>();
				for (Object[] result : results) {
					dealerServiceEntriesDto = new DealerServiceEntriesDto();

					dealerServiceEntriesDto.setDealerServiceEntriesId((Long) result[0]);
					dealerServiceEntriesDto.setDealerServiceEntriesNumberStr("<a style=\"color: blue; background: #ffc;\" href=\"showDealerServiceEntries?dealerServiceEntriesId="+dealerServiceEntriesDto.getDealerServiceEntriesId()+" \" target=\"_blank\" > DSE-"+((Long)result[1])+"</a>");
					dealerServiceEntriesDto.setVid((Integer) result[2]);
					dealerServiceEntriesDto.setVehicleNumber("<a style=\"color: blue; background: #ffc;\" href=\"showVehicle?vid="+dealerServiceEntriesDto.getVid()+" \" target=\"_blank\" >"+((String) result[3])+"</a>");
					dealerServiceEntriesDto.setVendorId((Integer) result[4]);
					dealerServiceEntriesDto.setVendorName("<a style=\"color: blue; background: #ffc;\" href=\"ShowVendor?vendorId="+dealerServiceEntriesDto.getVendorId()+" \" target=\"_blank\" >"+((String) result[5])+"</a>");
					if(result[6] != null) {
						dealerServiceEntriesDto.setInvoiceNumber((String) result[6]);
					}
					dealerServiceEntriesDto.setJobNumber((String) result[7]);
					if( result[8] != null) {
						dealerServiceEntriesDto.setInvoiceDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[8], DateTimeUtility.DD_MM_YYYY));
					}else {
						dealerServiceEntriesDto.setInvoiceDateStr("");
					}
					dealerServiceEntriesDto.setTotalInvoiceCost((Double) result[9]);
					dealerServiceEntriesDto.setStatusId((short) result[10]);
					dealerServiceEntriesDto.setStatus(DealerServiceEntriesConstant.getDealerServiceEntriesStatus(dealerServiceEntriesDto.getStatusId()));
					dealerServiceEntriesDto.setPaymentTypeId((short) result[11]);
					dealerServiceEntriesDto.setPaymentType(PaymentTypeConstant.getPaymentTypeName(dealerServiceEntriesDto.getPaymentTypeId()));
					if(result[12] != null) {
						dealerServiceEntriesDto.setVehicleOdometer((Integer) result[12]);
					}
					dealerServiceEntriesDto.setPayNumber((String) result[13]);
					if( result[14] != null) {
						dealerServiceEntriesDto.setPaidDateStr(DateTimeUtility.getStringDateFromDate((Timestamp) result[14], DateTimeUtility.DD_MM_YYYY));
					}
					if(result[15] != null) {
						dealerServiceEntriesDto.setDriverId((Integer) result[15]);
					}
					if(result[16] != null) {
						dealerServiceEntriesDto.setDriverFirstName((String) result[16]);
					}else {
						dealerServiceEntriesDto.setDriverFirstName("");
					}
					if(result[17] != null) {
						dealerServiceEntriesDto.setDriverLastName((String) result[17]);
					}else {
						dealerServiceEntriesDto.setDriverLastName("");
					}
					if(result[18] != null) {
						dealerServiceEntriesDto.setDriverFatherName((String) result[18]);
					}else {
						dealerServiceEntriesDto.setDriverFatherName("");
					}
					dealerServiceEntriesDto.setDriverFullName(dealerServiceEntriesDto.getDriverFirstName()+" "+dealerServiceEntriesDto.getDriverLastName()+" - "+dealerServiceEntriesDto.getDriverFatherName());
					
					if(result[19] != null) {
						dealerServiceEntriesDto.setRemark((String) result[19]);
					}else {
						dealerServiceEntriesDto.setRemark("");
					}
					dealerServiceEntriesList.add(dealerServiceEntriesDto);
				}
			}
			return dealerServiceEntriesList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public List<DealerServiceEntriesDto> getAllDSEPartList(ValueObject object) throws Exception {
		List<DealerServiceEntriesDto>	dealerServiceEntriesList		= null;
		DealerServiceEntriesDto 	dealerServiceEntriesDto 		= null;
		TypedQuery<Object[]> 		query 							= null;
		try {
			String partQuery =" SELECT DSE.dealerServiceEntriesId,SUM(DP.partTotalCost)  "
							+ " FROM DealerServiceEntries AS DSE "
							+ " INNER JOIN Vehicle V ON V.vid = DSE.vid "
							+ " INNER JOIN Vendor VN ON VN.vendorId = DSE.vendorId "
							+ " INNER JOIN DealerServiceEntriesPart AS DP on DP.dealerServiceEntriesId = DSE.dealerServiceEntriesId AND DP.markForDelete = 0";
			query =  entityManager.createQuery(partQuery+" where DSE.companyId = "+object.getInt("companyId",0)+" "+object.getString("condition","")+"  GROUP BY DSE.dealerServiceEntriesId",Object[].class);

			List<Object[]> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				dealerServiceEntriesList = new ArrayList<>();
				for (Object[] result : results) {
					dealerServiceEntriesDto = new DealerServiceEntriesDto();
					dealerServiceEntriesDto.setDealerServiceEntriesId((Long) result[0]);
						if( result[1] != null && (Double) result[1] > 0) {
							dealerServiceEntriesDto.setTotalPartCost((Double) result[1]);
							dealerServiceEntriesDto.setTotalPartstr("<a style=\"color: blue; background: #ffc;\" onclick=getpartOrLabourDetails("+dealerServiceEntriesDto.getDealerServiceEntriesId()+",1) href=\"javascript:void(0)\" >"+dealerServiceEntriesDto.getTotalPartCost()+"</a>");
						}
					dealerServiceEntriesList.add(dealerServiceEntriesDto);
				}
			}
			return dealerServiceEntriesList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<DealerServiceEntriesDto> getAllDSELabourList(ValueObject object) throws Exception {
		List<DealerServiceEntriesDto>	dealerServiceEntriesList		= null;
		DealerServiceEntriesDto 	dealerServiceEntriesDto 		= null;
		TypedQuery<Object[]> 		query 							= null;
		try {
			String labourQuery =" SELECT DSE.dealerServiceEntriesId,SUM(DL.totalCost)  "
							+ " FROM DealerServiceEntries AS DSE "
							+ " INNER JOIN Vehicle V ON V.vid = DSE.vid "
							+ " INNER JOIN Vendor VN ON VN.vendorId = DSE.vendorId "
							+ " INNER JOIN DealerServiceEntriesLabour AS DL on DL.dealerServiceEntriesId = DSE.dealerServiceEntriesId AND DL.markForDelete = 0 ";
			query =  entityManager.createQuery(labourQuery+" where DSE.companyId = "+object.getInt("companyId",0)+" "+object.getString("condition","")+"  GROUP BY DSE.dealerServiceEntriesId",Object[].class);
			List<Object[]> results = query.getResultList();
			if (results != null && !results.isEmpty()) {
				dealerServiceEntriesList = new ArrayList<>();
				for (Object[] result : results) {
					dealerServiceEntriesDto = new DealerServiceEntriesDto();
					dealerServiceEntriesDto.setDealerServiceEntriesId((Long) result[0]);
						if( result[1] != null && (Double) result[1] > 0) {
							dealerServiceEntriesDto.setTotalLabourCost((Double) result[1]);
							dealerServiceEntriesDto.setTotalLabourstr("<a style=\"color: blue; background: #ffc;\" onclick=getpartOrLabourDetails("+dealerServiceEntriesDto.getDealerServiceEntriesId()+",2) href=\"javascript:void(0)\" >"+dealerServiceEntriesDto.getTotalLabourCost()+"</a>");						
						}
						dealerServiceEntriesList.add(dealerServiceEntriesDto);
					}
				}
			return dealerServiceEntriesList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public ValueObject getpartLabourList(ValueObject valueObject) {

		ValueObject valueOutObject = new ValueObject();
		try {
			CustomUserDetails userDetails = Utility.getObject(null);
			valueObject.put("companyId", userDetails.getCompany_id());
			valueObject.put("query", " AND DSE.dealerServiceEntriesId ="+valueObject.getLong("dealerServiceId",0)+" ");
			List<DealerServiceEntriesPartDto>	dealerServiceEntriesPartList = null;
			List<DealerServiceEntriesLabourDto>	dealerServiceEntriesLabourList = null;
			short type= valueObject.getShort("type");
			if(type == 1) {
				dealerServiceEntriesPartList	= getDealerServiceEntriesPartDetailList(valueObject);
			}else {
				dealerServiceEntriesLabourList	= getDealerServiceEntriesLabourDetailList(valueObject);
			}
			valueOutObject.put("dealerServiceEntriesLabour", dealerServiceEntriesLabourList);
			valueOutObject.put("dealerServiceEntriesPart", dealerServiceEntriesPartList);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valueOutObject;
	}
	
	
	@Override
	public HashMap<String,Object> getVehicleWiseDealerServiceDetails(int id){
		HashMap<String,Object> map = new HashMap<>();
		CustomUserDetails userDetails= Utility.getObject(null);
		VehicleDto vehicle;
		ValueObject valueObject= new ValueObject();
		try {
			vehicle = vehicleService.Get_Vehicle_Header_Details(id,userDetails.getCompany_id());
			valueObject.put("companyId", userDetails.getCompany_id());

			if(vehicle != null) {
						valueObject.put("query", " AND DSE.vid="+id+" ");
						List<DealerServiceEntriesDto> list=getDealerServiceEntriesDetailList(valueObject);
						map.put("dseList", list);
						VehicleDto vehicleDto = VBL.prepare_Vehicle_Header_Show(vehicle);
						map.put("vehicle", vehicleDto);
						map.put("companyId",userDetails.getCompany_id());
						map.put("userId",userDetails.getId());
						map.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(id, userDetails.getCompany_id()));
						Object[] count = vehicleService.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(id);
						map.put("photo_Count", (Long) count[0]);
						map.put("purchase_Count", (Long) count[1]);
						map.put("reminder_Count", (Long) count[2]);
						map.put("fuelEntrie_Count", (Long) count[3]);
						map.put("serviceEntrie_Count", (Long) count[4]);
						map.put("serviceReminder_Count", (Long) count[5]);
						map.put("tripsheet_Count", (Long) count[6]);
						map.put("workOrder_Count", (Long) count[7]);
						map.put("issues_Count", (Long) count[8]);
						map.put("odometerhis_Count", (Long) count[9]);
						map.put("comment_Count", (Long) count[10]);
						map.put("dseCount", (Long) count[12]);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@Override
	public List<TripSheetExpenseDto> getDealerEntriesListForTallyImport(String fromDate, String toDate,
			Integer companyId, String tallyCompany) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.dealerServiceEntriesId, SE.dealerServiceEntriesNumber, SE.invoiceDate, V.vendorName,"
					+ " VH.vehicle_registration, VH.ledgerName, TC.companyName, SE.creationOn, SE.paymentTypeId, SE.vid, "
					+ " SE.totalServiceCost, SE.isPendingForTally, SE.invoiceNumber "
					+ " FROM DealerServiceEntries SE "
					+ " INNER JOIN Vehicle VH ON VH.vid = SE.vid "
					+ " LEFT JOIN Vendor AS V ON V.vendorId = SE.vendor_id"
					+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = SE.tallyCompanyId AND TC.companyName = '"+tallyCompany+"'"
					+ " WHERE SE.invoiceDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND SE.totalServiceCost > 0"
					+ " AND SE.statusId = "+DealerServiceEntriesConstant.DSE_ACCOUNT_CLOSE_STATUS_ID+"");
			
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
						select.setPendingForTally((boolean) vehicle[11]);
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_DEALER_ENTRIES);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						
						select.setExpenseName("Dealer Service Entries");
						
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
						
						 select.setRemark("Dealer Service Entries On Vehicle "+select.getVehicle_registration()+" Invoice Date: "+dateFormat.format((java.util.Date)vehicle[2])+ " Invoice Number : "+(String)vehicle[12]+" "+  " From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						
						select.setTripSheetNumberStr("DSE-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
						
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
	public List<TripSheetExpenseDto> getDealerEntriesListForTallyImport(String fromDate, String toDate, Integer companyId,
			Long tallyCompanyId) throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT SE.dealerServiceEntriesId, SE.dealerServiceEntriesNumber, SE.invoiceDate, V.vendorName,"
					+ " VH.vehicle_registration, VH.ledgerName, TC.companyName, SE.creationOn, SE.paymentTypeId, SE.vid, "
					+ " SE.totalServiceCost, SE.isPendingForTally, SE.invoiceNumber "
					+ " FROM DealerServiceEntries SE "
					+ " INNER JOIN Vehicle VH ON VH.vid = SE.vid "
					+ " LEFT JOIN Vendor AS V ON V.vendorId = SE.vendorId"
					+ " INNER JOIN TallyCompany TC ON TC.companyId = SE.companyId AND TC.tallyCompanyId = "+tallyCompanyId+""
					+ " WHERE SE.invoiceDate between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
					+ " AND SE.companyId = "+companyId+" AND SE.markForDelete = 0 AND SE.totalServiceCost > 0"
					+ " AND SE.statusId = "+DealerServiceEntriesConstant.DSE_ACCOUNT_CLOSE_STATUS_ID+"");
			
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
						//select.setExpenseName((String) vehicle[11]);
						select.setPendingForTally((boolean) vehicle[11]);
						select.setExpenseTypeId(TallyVoucherTypeContant.TRANSACTION_TYPE_DEALER_ENTRIES);
						select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
						
						
						select.setExpenseName("Vehicle Repairs and Maintenance");
						
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
						
						 select.setRemark("Dealer Service Entries On Vehicle "+select.getVehicle_registration()+" Invoice Date: "+dateFormat.format((java.util.Date)vehicle[2])+ " Invoice Number : "+(String)vehicle[12]+" "+  " From: "+select.getVendorName());
						
						if(select.getTallyCompanyName() == null) {
							select.setTallyCompanyName("--");
						}
						if(select.getLedgerName() == null) {
							select.setLedgerName("--");
						}
						
						select.setTripSheetNumberStr("DSE-"+select.getTripSheetNumber()+"_"+select.getTripExpenseID());
						
						Dtos.add(select);
					}
				}
				
			}
			return Dtos;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
}
