package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.ApprovalType;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.persistence.bl.InventoryPartInvoiceBL;
import org.fleetopgroup.persistence.bl.VendorApprovalBL;
import org.fleetopgroup.persistence.bl.VendorBL;
import org.fleetopgroup.persistence.dao.VendorPaymentHistoryRepository;
import org.fleetopgroup.persistence.dao.VendorPaymentRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VendorApprovalDto;
import org.fleetopgroup.persistence.dto.VendorDto;
import org.fleetopgroup.persistence.dto.VendorPaymentDTO;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.VendorApproval;
import org.fleetopgroup.persistence.model.VendorApprovalSequenceCounter;
import org.fleetopgroup.persistence.model.VendorPayment;
import org.fleetopgroup.persistence.model.VendorPaymentHistory;
import org.fleetopgroup.persistence.model.VendorPaymentSequenceCounter;
import org.fleetopgroup.persistence.model.VendorSubApprovalDetails;
import org.fleetopgroup.persistence.report.dao.IVendorReportDao;
import org.fleetopgroup.persistence.serviceImpl.IBatteryInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IPartInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVendorApprovalSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IVendorApprovalService;
import org.fleetopgroup.persistence.serviceImpl.IVendorPaymentSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.persistence.serviceImpl.IVendorSubApprovalDetailsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VendorPaymentService implements IVendorPaymentService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	IUserProfileService	userProfileService;
	
	@Autowired 	IVendorReportDao	vendorReportDao;
	
	@PersistenceContext
	EntityManager entityManager;

	@Autowired 
	VendorPaymentRepository vendorPaymentRepository;

	@Autowired 
	VendorPaymentHistoryRepository vendorPaymentHistoryRepository;

	@Autowired 
	IVendorPaymentSequenceService vendorPaymentSequenceService;
	
	@Autowired 
	IPurchaseOrdersService  purchaseOrdersService;
	
	@Autowired
	private IVendorApprovalService vendorApprovalService;
	
	@Autowired
	private IVendorSubApprovalDetailsService vendorSubApprovalService;
	
	@Autowired
	private IVendorApprovalSequenceService	approvalSequenceService;
	
	@Autowired
	private IServiceEntriesService	ServiceEntriesService;
	
	@Autowired
	private IInventoryTyreService	iventoryTyreService;
	
	@Autowired
	IPartInvoiceService			partInvoiceService;
	
	@Autowired
	IClothInventoryService		clothInvoiceService;
	
	@Autowired
	IBatteryInvoiceService		batteryInvoiceService;
	
	@Autowired private IPendingVendorPaymentService		pendingVendorPaymentService;
	
	@Autowired
	IFuelService		fuelService;
	
	InventoryPartInvoiceBL		PartBL				= new InventoryPartInvoiceBL(); 
	
	@Autowired private IVendorService vendorService;
	@Autowired IVendorPaymentService   	vendorPaymentService;
	VendorApprovalBL approval = new VendorApprovalBL();
	VendorBL VenBL = new VendorBL();
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	DecimalFormat 	AMOUNT_FORMAT 		= new DecimalFormat("##,##,##0");

	private static final int PAGE_SIZE = 10;
	
	public static boolean arrayContains(String[] arr, String targetValue) {
		for (String s : arr) {
			if (s.matches(targetValue))
				return true;
		}
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ValueObject addVendorPayment(ValueObject	valueObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		VendorPayment					vendorPayment					= null;
		ValueObject						valOutObject					= null;
		Timestamp 						createdDateTime 				= null;
		VendorPaymentSequenceCounter	sequenceCounter					= null;

		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			createdDateTime 	= new java.sql.Timestamp(new Date().getTime());

			sequenceCounter		= vendorPaymentSequenceService.findNextVendorPaymentNumber(userDetails.getCompany_id());

			if(sequenceCounter != null) {
				vendorPayment		= prepareVendorPaymentDTO(valueObject);
				if(vendorPayment != null) {
					vendorPayment.setVendorPaymentNumber(sequenceCounter.getNextVal());
					vendorPayment.setCreatedById(userDetails.getId());
					vendorPayment.setCompanyId(userDetails.getCompany_id());
					vendorPayment.setCreatedDate(createdDateTime);

					vendorPaymentRepository.save(vendorPayment);				
				}				
			}

			if(vendorPayment != null) {
				valOutObject	= new ValueObject();
				valOutObject.put("VendorPaymentId", vendorPayment.getVendorPaymentId());
			}

			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails						= null;
			vendorPayment					= null;
			valOutObject					= null;
			createdDateTime 				= null;
		}
	}

	private VendorPayment prepareVendorPaymentDTO(ValueObject valueObject) throws Exception {

		VendorPayment		vendorPayment		= null;
		java.util.Date 		date 				= null;
		java.sql.Date 		invoiceDate 		= null;
		java.sql.Date 		paymentDate 		= null;
		java.sql.Date 		chequeDate 			= null;

		try {
			vendorPayment		= new VendorPayment();
			if(valueObject.getLong("VehicleGroup") != 0 ) {
			vendorPayment.setVehicleGroup(valueObject.getLong("VehicleGroup")); //This line added for Group Info in Vendor Payment Report by Dev Y
			}
			vendorPayment.setVendorId(valueObject.getLong("vendorId"));
			vendorPayment.setTransactionTypeId(valueObject.getShort("transactionTypeId"));
			vendorPayment.setTransactionAmount(valueObject.getDouble("transactionAmount"));
			vendorPayment.setGstAmount(valueObject.getDouble("gstAmount",0));

			if(valueObject.getShort("transactionTypeId") == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
				vendorPayment.setInvoiceNumber(valueObject.getString("invoiceNumber"));

				date 				= dateFormat.parse(valueObject.getString("invoiceDate"));
				invoiceDate 		= new java.sql.Date(date.getTime());

				vendorPayment.setInvoiceDate(invoiceDate);
			} else {
				vendorPayment.setPaymentTypeId(valueObject.getShort("paymentTypeId"));

				if(valueObject.getShort("paymentTypeId") == PaymentTypeConstant.PAYMENT_TYPE_CHEQUE) {
					if(valueObject.getString("chequeNumber") != null && valueObject.getString("chequeNumber") != "") {
						vendorPayment.setChequeNumber(valueObject.getString("chequeNumber"));					
					}

					if(valueObject.getString("chequeDate") != null && valueObject.getString("chequeDate") != "") {
						date 				= dateFormat.parse(valueObject.getString("chequeDate"));
						chequeDate 			= new java.sql.Date(date.getTime());

						vendorPayment.setChequeDate(chequeDate);
						vendorPayment.setPaymentDate(chequeDate);
					}

				} else {
					vendorPayment.setCashVoucherNumber(valueObject.getString("cashVoucherNumber"));				

					date 				= dateFormat.parse(valueObject.getString("paymentDate"));
					paymentDate 		= new java.sql.Date(date.getTime());
					
					vendorPayment.setPaymentDate(paymentDate);
				}
			}

			vendorPayment.setMarkForDelete(false);

			return vendorPayment;
		} catch (Exception e) {
			throw e;
		} finally {
			vendorPayment		= null;
			date 				= null;
			invoiceDate 		= null;
			chequeDate 			= null;
		}
	}

	private VendorPayment prepareUpdateVendorPaymentDTO(ValueObject valueObject, VendorPayment	vendorPayment) throws Exception {
		java.util.Date 		date 				= null;
		java.sql.Date 		invoiceDate 		= null;
		java.sql.Date 		chequeDate 			= null;
		
		try {
			
			vendorPayment.setTransactionAmount(valueObject.getDouble("transactionAmount"));
			vendorPayment.setGstAmount(valueObject.getDouble("gstAmount",0));
			if(vendorPayment.getTransactionTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
				vendorPayment.setInvoiceNumber(valueObject.getString("invoiceNumber"));
				
				date 				= dateFormat.parse(valueObject.getString("invoiceDate"));
				invoiceDate 		= new java.sql.Date(date.getTime());
				
				vendorPayment.setInvoiceDate(invoiceDate);
			} else {				
				vendorPayment.setPaymentTypeId(valueObject.getShort("paymentTypeId"));
				
				if(valueObject.getShort("paymentTypeId") == PaymentTypeConstant.PAYMENT_TYPE_CHEQUE) {
					if(valueObject.getString("chequeNumber") != null && valueObject.getString("chequeNumber") != "") {
						vendorPayment.setChequeNumber(valueObject.getString("chequeNumber"));					
					}
					
					if(valueObject.getString("chequeDate") != null && valueObject.getString("chequeDate") != "") {
						date 				= dateFormat.parse(valueObject.getString("chequeDate"));
						chequeDate 			= new java.sql.Date(date.getTime());
						
						vendorPayment.setChequeDate(chequeDate);					
						vendorPayment.setPaymentDate(chequeDate);					
					}
					
				} else {
					vendorPayment.setCashVoucherNumber(valueObject.getString("cashVoucherNumber"));				
				}
			}
			
			
			vendorPayment.setMarkForDelete(false);
			
			return vendorPayment;
		} catch (Exception e) {
			throw e;
		} finally {
			vendorPayment		= null;
			date 				= null;
			invoiceDate 		= null;
			chequeDate 			= null;
		}
	}

	@Override
	public ValueObject getVendorOpeningBalance(ValueObject valueObject) throws Exception {

		CustomUserDetails					userDetails					= null;
		VendorPaymentDTO					vendorPayment 				= null;
		TypedQuery<Object[]> 				query 						= null;
		ValueObject							valueOutObject				= null;
		long								vendorId					= 0;
		double								amount						= 0;

		try {
			
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vendorId			= valueObject.getLong("vendorId",0);
			
			query = entityManager.createQuery("SELECT VP.transactionTypeId, VP.transactionAmount"
					+ " FROM VendorPayment AS VP"
					+ " WHERE VP.vendorId = "+vendorId
					+ " AND VP.companyId = "+userDetails.getCompany_id()
					+ " AND VP.markForDelete = 0"
					+ " ORDER BY VP.vendorPaymentId",Object[].class);
			
			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				for (Object[] result : results) {
					if((short) result[0] == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
						amount += (double) result[1];
					} else {
						amount -= (double) result[1];						
					}
				}
			}

			vendorPayment	= new VendorPaymentDTO();
			vendorPayment.setOpeningAmount(amount);
			
			valueOutObject	= new ValueObject();
			valueOutObject.put("vendorPayment", vendorPayment);
			
			return valueOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			vendorPayment 		= null;
			query 				= null;
		}
	}

	public VendorPaymentDTO getVendorOpeningBalanceWithDateRange(Long vendorId, Integer companyId, String date) throws Exception {
		
		VendorPaymentDTO					vendorPayment 				= null;
		TypedQuery<Object[]> 				query 						= null;
		double								amount						= 0;
		
		try {
			
			query = entityManager.createQuery("SELECT VP.transactionTypeId, VP.transactionAmount"
					+ " FROM VendorPayment AS VP"
					+ " WHERE VP.createdDate < '" + date + "' AND VP.vendorId = "+vendorId
					+ " AND VP.companyId = "+companyId
					+ " AND VP.markForDelete = 0"
					+ " ORDER BY VP.vendorPaymentId",Object[].class);
			
			List<Object[]> results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				for (Object[] result : results) {
					if((short) result[0] == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
						amount += (double) result[1];
					} else {
						amount -= (double) result[1];						
					}
				}
			}
			
			vendorPayment	= new VendorPaymentDTO();
			vendorPayment.setOpeningAmount(amount);
			
			return vendorPayment;
		} catch (Exception e) {
			throw e;
		} finally {
			vendorPayment 		= null;
			query 				= null;
		}
	}

	@Override
	public ValueObject getVendorPaymentReport(ValueObject	valueObject) throws Exception {
		CustomUserDetails				userDetails						= null;
		ValueObject						valOutObject					= null;
		VendorPaymentDTO				vendorPayment					= null;
		ArrayList<VendorPaymentDTO> 	vendorPaymentList				= null;
		long							vendorId						= 0;
		String[]						From_TO_DateRange				= null;

		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			vendorId			= valueObject.getLong("vendorId");
			From_TO_DateRange	= valueObject.getString("dateRange").split(" to ");
			if(From_TO_DateRange != null) {
				vendorPayment		= new VendorPaymentDTO();
				vendorPayment.setVendorId(vendorId);
				vendorPayment.setFromDate(DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
				vendorPayment.setToDate(DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
				vendorPayment.setCompanyId(userDetails.getCompany_id());

				vendorPaymentList	= finalizeVendorPaymentList(getVendorPaymentList(vendorPayment));

				valOutObject		= new ValueObject();

				valOutObject.put("vendorPaymentList", vendorPaymentList);
				valOutObject.put("vendorPayment", getVendorOpeningBalanceWithDateRange(vendorId,userDetails.getCompany_id(), DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS)));
			}

			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails						= null;
			valOutObject					= null;
		}
	}

	private ArrayList<VendorPaymentDTO> getVendorPaymentList(VendorPaymentDTO vendorPayment) throws Exception {

		ArrayList<VendorPaymentDTO>				vendorPaymentList			= null;

		try {

			TypedQuery<Object[]> query = null;

			query = entityManager.createQuery("SELECT VP.vendorPaymentId, VP.vendorId, VP.transactionTypeId, VP.transactionAmount,VP.invoiceNumber, VP.invoiceDate,"
					+ " VP.paymentTypeId, VP.chequeNumber, VP.chequeDate, VP.createdDate, V.vendorName, VP.cashVoucherNumber, VP.paymentDate, VP.gstAmount "
					+ " FROM VendorPayment AS VP"
					+ " INNER JOIN Vendor AS V ON VP.vendorId = V.vendorId"
					+ " where VP.createdDate >= '" + vendorPayment.getFromDate() + "' AND VP.createdDate <= '" + vendorPayment.getToDate()+"'"
					+ " AND VP.vendorId = " + vendorPayment.getVendorId() + " AND VP.companyId = " + vendorPayment.getCompanyId()
					+ " AND VP.markForDelete = 0 ORDER BY VP.vendorPaymentId",
					Object[].class);

			List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				vendorPaymentList = new ArrayList<VendorPaymentDTO>();

				VendorPaymentDTO paymentDTO = null;
				for (Object[] result : results) {
					paymentDTO = new VendorPaymentDTO();

					paymentDTO.setVendorPaymentId((Long) result[0]);
					paymentDTO.setVendorId((Long) result[1]);
					paymentDTO.setTransactionTypeId((Short) result[2]);
					paymentDTO.setTransactionAmount((Double) result[3]);
					paymentDTO.setInvoiceNumber((String) result[4]);
					paymentDTO.setInvoiceDate((Date) result[5]);
					paymentDTO.setPaymentTypeId((Short) result[6]);
					paymentDTO.setChequeNumber((String) result[7]);
					paymentDTO.setChequeDate((Date) result[8]);
					paymentDTO.setCreatedDate((Date) result[9]);
					paymentDTO.setVendorName((String) result[10]);
					paymentDTO.setCashVoucherNumber((String) result[11]);
					if(result[12] != null)
					 paymentDTO.setPaymentDate((Date) result[12]);
					if(result[13] != null)
						paymentDTO.setGstAmount((Double) result[13]);

					vendorPaymentList.add(paymentDTO);
				}
			}

			return vendorPaymentList;
		} catch (Exception e) {
			throw e;
		} finally {
			vendorPaymentList		= null;
		}
	}


	public ArrayList<VendorPaymentDTO> finalizeVendorPaymentList(ArrayList<VendorPaymentDTO> vendorPaymentList) throws Exception {
		try {
			if(vendorPaymentList != null) {
				for (VendorPaymentDTO vendorPayment : vendorPaymentList) {
					
					if(vendorPayment.getChequeDate() != null) {
						vendorPayment.setChequeDateStr(dateFormat.format(vendorPayment.getChequeDate()));
					} else {
						vendorPayment.setChequeDateStr("--");
					}

					if(vendorPayment.getChequeNumber() == null || vendorPayment.getChequeNumber() == "") {
						vendorPayment.setChequeNumber("--");
					}

					if(vendorPayment.getCashVoucherNumber() == null || vendorPayment.getCashVoucherNumber() == "") {
						vendorPayment.setCashVoucherNumber("--");
					}
					
					if(vendorPayment.getPaymentDate() != null) {
						vendorPayment.setPaymentDateStr(dateFormat.format(vendorPayment.getPaymentDate())); 																	
					} else {
						vendorPayment.setPaymentDateStr("--"); 																	
					}

					if(vendorPayment.getInvoiceDate() != null) {
						vendorPayment.setInvoiceDateStr(dateFormat.format(vendorPayment.getInvoiceDate()));						
					} else {
						vendorPayment.setInvoiceDateStr("--");												
					}

					if(vendorPayment.getInvoiceNumber() == null || vendorPayment.getInvoiceNumber() == "") {
						vendorPayment.setInvoiceNumber("--");
					}

					if(vendorPayment.getPaymentTypeId() > 0) {
						vendorPayment.setPaymentTypeStr(PaymentTypeConstant.getPaymentTypeName(vendorPayment.getPaymentTypeId()));						
					} else {
						vendorPayment.setPaymentTypeStr("--");												
					}
				}				
			}

			return vendorPaymentList;
		} catch (Exception e) {
			throw e;
		}
	}

	public Page<VendorPayment> getDeployment_Page_VendorPayment(Integer pageNumber, Integer companyId) {		@SuppressWarnings("deprecation")
		PageRequest pageable = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "vendorPaymentId");
		return vendorPaymentRepository.getDeployment_Page_VendorPayment(companyId, pageable);
	}

	public ArrayList<VendorPaymentDTO> listVendorPayment(Integer pageNumber, Integer companyId) throws Exception {

		TypedQuery<Object[]> 			typedQuery 				= null;
		List<Object[]> 					resultList 				= null; 
		ArrayList<VendorPaymentDTO> 	vendorPaymentList 		= null;
		VendorPaymentDTO 				vendorPayment			= null;
		try {
			typedQuery = entityManager.createQuery("SELECT VP.vendorPaymentId, VP.vendorPaymentNumber, VP.vendorId, V.vendorTypeId, V.vendorName, VP.invoiceNumber, VP.invoiceDate,"
					+ " VP.paymentTypeId, VP.chequeNumber, VP.chequeDate, VP.cashVoucherNumber, VP.paymentDate, VP.transactionTypeId, VP.transactionAmount, VP.gstAmount, VG.vGroup"
					+ " FROM VendorPayment AS VP"
					+ " INNER JOIN Vendor AS V ON VP.vendorId = V.vendorId"
					+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = VP.VehicleGroup "
					+ " WHERE VP.companyId ="+companyId+" AND VP.markForDelete = 0 ORDER BY VP.vendorPaymentId DESC", Object[].class);
			 
			typedQuery.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			typedQuery.setMaxResults(PAGE_SIZE);

			resultList = typedQuery.getResultList();
			

			if (resultList != null && !resultList.isEmpty()) {
				vendorPaymentList = new ArrayList<>();

				for (Object[] result : resultList) {
					vendorPayment = new VendorPaymentDTO();
					if(vendorPayment != null) {
					vendorPayment.setVendorPaymentId((Long) result[0]);
					if(result[1] != null)
					vendorPayment.setVendorPaymentNo((Long) result[1]);
					vendorPayment.setVendorId((Long) result[2]);
					vendorPayment.setVendorTypeId((Long) result[3]);
					vendorPayment.setVendorName((String) result[4]);
					vendorPayment.setInvoiceNumber((String) result[5]);
					
					if(result[6] != null) //Dev Y
					vendorPayment.setInvoiceDate((Date) result[6]);
					
					vendorPayment.setPaymentTypeId((Short) result[7]);
					vendorPayment.setChequeNumber((String) result[8]);
					vendorPayment.setChequeDate((Date) result[9]);
					vendorPayment.setCashVoucherNumber((String) result[10]);
					vendorPayment.setPaymentDate((Date) result[11]);
					vendorPayment.setTransactionTypeId((Short) result[12]);
					vendorPayment.setTransactionAmount((Double) result[13]);
					if(result[14] != null)
					{
							vendorPayment.setGstAmount((Double) result[14]);
					}
					if(result[15] != null)//Dev Y
					{
						vendorPayment.setVehicleGroup((String) result[15]);//Dev Y 
					}
					}
					
					vendorPaymentList.add(vendorPayment);
				}
			}

			return vendorPaymentList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			vendorPaymentList 		= null;
			vendorPayment			= null;
		}
	}

	@Override
	public ValueObject getPageWiseVendorPaymentDetails(ValueObject valueObject) throws Exception {

		CustomUserDetails				userDetails 				= null;
		Integer							pageNumber					= null;
		ValueObject						valOutObject				= null;
		try {
			
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			valOutObject		= new ValueObject();
			pageNumber			= valueObject.getInt("pageNumber",0);
			Page<VendorPayment> page = getDeployment_Page_VendorPayment(pageNumber, userDetails.getCompany_id());
			
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			valOutObject.put("deploymentLog", page);
			valOutObject.put("beginIndex", begin);
			valOutObject.put("endIndex", end);
			valOutObject.put("currentIndex", current);

			valOutObject.put("VendorPaymentCount", page.getTotalElements());
			
			ArrayList<VendorPaymentDTO> pageList = finalizeVendorPaymentList(listVendorPayment(pageNumber, userDetails.getCompany_id()));
			
			valOutObject.put("vendorPayment", pageList);
			valOutObject.put("SelectPage", pageNumber);
			
			return valOutObject;
		} catch (NullPointerException e) {
			throw e;
		} 
	
	}

	@Transactional
	public ValueObject deleteVendorPayment(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails 				= null;
		ValueObject					valOutObject				= null;
		VendorPayment				vendorPaymentFromDB			= null;
		VendorPaymentHistory		vendorPaymentHistory		= null;
		Long						vendorPaymentId				= null;

		try {
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vendorPaymentId			= valueObject.getLong("vendorPaymentId",0);
			vendorPaymentFromDB		= vendorPaymentRepository.findById(valueObject.getLong("vendorPaymentId"));
			vendorPaymentHistory	= prepareVendorPaymentHistoryDTO(vendorPaymentFromDB);
			
			vendorPaymentRepository.deleteVendorPayment(vendorPaymentId, userDetails.getCompany_id());
			vendorPaymentHistoryRepository.save(vendorPaymentHistory);
			
			valOutObject		= new ValueObject();
			valOutObject.put("vendorPaymentId", vendorPaymentId);
			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails 				= null;
			valOutObject				= null;
			vendorPaymentId				= null;
		}
	}

	@Override
	public ValueObject updateVendorPayment(ValueObject valueObject) throws Exception {
		CustomUserDetails			userDetails 				= null;
		ValueObject					valOutObject				= null;
		VendorPayment				vendorPayment				= null;
		VendorPayment				vendorPaymentFromDB			= null;
		VendorPaymentHistory		vendorPaymentHistory		= null;
		Timestamp					modifiedDate				= null;

		try {
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			modifiedDate 			= new java.sql.Timestamp(new Date().getTime());
			
			vendorPaymentFromDB		= vendorPaymentRepository.findById(valueObject.getLong("vendorPaymentId"));
			vendorPaymentHistory	= prepareVendorPaymentHistoryDTO(vendorPaymentFromDB);
			vendorPayment			= prepareUpdateVendorPaymentDTO(valueObject, vendorPaymentRepository.findById(valueObject.getLong("vendorPaymentId")));
			System.out.println("vendorPayment"+vendorPayment);
			if(vendorPayment != null) {
				vendorPayment.setModifiedById(userDetails.getId());
				vendorPayment.setModifiedDate(modifiedDate);
				
				vendorPaymentRepository.save(vendorPayment);
				vendorPaymentHistoryRepository.save(vendorPaymentHistory);
			}
			
			valOutObject		= new ValueObject();
			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails 				= null;
			valOutObject				= null;
			vendorPayment				= null;
			modifiedDate				= null;
		}
	}

	public VendorPaymentHistory prepareVendorPaymentHistoryDTO(VendorPayment vendorPaymentFromDB) throws Exception {
		
		VendorPaymentHistory			vendorPaymentHistory			= null;
		
		try {
			
			vendorPaymentHistory		= new VendorPaymentHistory();
			
			vendorPaymentHistory.setVendorPaymentId(vendorPaymentFromDB.getVendorPaymentId());
			vendorPaymentHistory.setModifiedById(vendorPaymentFromDB.getModifiedById());
			vendorPaymentHistory.setModifiedDate(vendorPaymentFromDB.getModifiedDate());
			vendorPaymentHistory.setTransactionAmount(vendorPaymentFromDB.getTransactionAmount());
			vendorPaymentHistory.setGstAmount(vendorPaymentFromDB.getGstAmount());
			vendorPaymentHistory.setInvoiceNumber(vendorPaymentFromDB.getInvoiceNumber());
			vendorPaymentHistory.setInvoiceDate(vendorPaymentFromDB.getInvoiceDate());
			vendorPaymentHistory.setPaymentTypeId(vendorPaymentFromDB.getPaymentTypeId());
			vendorPaymentHistory.setCashVoucherNumber(vendorPaymentFromDB.getCashVoucherNumber());
			vendorPaymentHistory.setChequeNumber(vendorPaymentFromDB.getChequeNumber());
			vendorPaymentHistory.setChequeDate(vendorPaymentFromDB.getChequeDate());
			vendorPaymentHistory.setMarkForDelete(vendorPaymentFromDB.isMarkForDelete());
			
		
			
			return vendorPaymentHistory;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public ValueObject getVendorPaymentDetailsById(ValueObject valueObject) throws Exception {
		ValueObject					valOutObject				= null;
		VendorPaymentDTO			vendorPayment				= null;
		ArrayList<VendorPaymentDTO>	vendorPaymentList			= null;
		TypedQuery<Object[]>		typedQuery					= null;
		Object[]					result						= null;
		long						vendorPaymentId				= 0;

		try {
			vendorPaymentId		= valueObject.getLong("vendorPaymentId",0);
			
			typedQuery = entityManager.createQuery("SELECT VP.vendorPaymentId, VP.vendorPaymentNumber, VP.vendorId, V.vendorTypeId, V.vendorName, VP.invoiceNumber, VP.invoiceDate,"
					+ " VP.paymentTypeId, VP.chequeNumber, VP.chequeDate, VP.cashVoucherNumber, VP.paymentDate, VP.transactionTypeId, VP.transactionAmount, VP.gstAmount,VG.vGroup "
					+ " FROM VendorPayment AS VP" 
					+ " INNER JOIN Vendor AS V ON VP.vendorId = V.vendorId"
					+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = VP.VehicleGroup"
					+ " WHERE VP.vendorPaymentId ="+vendorPaymentId+" AND VP.markForDelete = 0 ORDER BY VP.vendorPaymentId DESC", Object[].class);
			
			result = (Object[]) typedQuery.getSingleResult();
			
			if (result != null) {
				vendorPayment 		= new VendorPaymentDTO();
				vendorPaymentList	= new ArrayList<>();

				vendorPayment.setVendorPaymentId((Long) result[0]);
				vendorPayment.setVendorPaymentNo((Long) result[1]);
				vendorPayment.setVendorId((Long) result[2]);
				vendorPayment.setVendorTypeId((Long) result[3]);
				vendorPayment.setVendorName((String) result[4]);
				vendorPayment.setInvoiceNumber((String) result[5]);
				vendorPayment.setInvoiceDate((Date) result[6]);
				vendorPayment.setPaymentTypeId((Short) result[7]);
				vendorPayment.setChequeNumber((String) result[8]);
				vendorPayment.setChequeDate((Date) result[9]);
				vendorPayment.setCashVoucherNumber((String) result[10]);
				vendorPayment.setPaymentDate((Date) result[11]);
				vendorPayment.setTransactionTypeId((Short) result[12]);
				vendorPayment.setTransactionAmount((Double) result[13]);
				vendorPayment.setGstAmount((Double) result[14]);
				if(result[15] != null) {// This line added by DEV Y
				vendorPayment.setVehicleGroup((String)result[15]); // This line added by DEV Y
				}// This line added by DEV Y
				vendorPaymentList.add(vendorPayment);
			}
			if(vendorPaymentList != null) {
				vendorPaymentList	= finalizeVendorPaymentList(vendorPaymentList);
				
				valOutObject		= new ValueObject();
				valOutObject.put("vendorPayment", vendorPaymentList.get(0));				
			}
			
			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			valOutObject				= null;
			vendorPayment				= null;
			vendorPaymentList			= null;
			typedQuery					= null;
			result						= null;
			vendorPaymentId				= 0;   
		}
	}
	
	
	
	
	//EXP Vendor GST Report  1 START By Dev Y
	@Override
	public ValueObject getVendorGstReport(ValueObject valueInObject) throws Exception {
	
		String							dateRange						= null;
		Integer							VehicleGroup					= null; 
		CustomUserDetails				userDetails						= null;
		ValueObject						valueOutObject					= null;
		ArrayList<VendorPaymentDTO> 	vendorPaymentList				= null;
		double totalAmount=0;
		
		try {

			 userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 dateRange	= 		valueInObject.getString("dateRange"); 			
			 VehicleGroup	    = valueInObject.getInt("VehicleGroup",0);			
			 
			/* if (VehicleGroup > 0) {
					queryString  += "AND VP.VehicleGroup =" + VehicleGroup + " ";
				}
			 */
			 String dateRangeFrom = "", dateRangeTo = "";
			 String[] From_TO_DateRange = null;			 
			 From_TO_DateRange = dateRange.split(" to ");
			 dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			 dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			 
			 TypedQuery<Object[]> query = null;			
				query = entityManager.createQuery(
						" SELECT V.vendorName, V.vendorGSTNO, VP.invoiceNumber,VP.invoiceDate,VP.transactionAmount,VP.gstAmount "
								+ " FROM VendorPayment AS VP"
								+ " INNER JOIN Vendor AS V ON V.vendorId = VP.vendorId  "
								+ " LEFT JOIN VehicleGroup AS VG ON VG.gid = VP.VehicleGroup"
								+ " Where VP.markForDelete=0 AND VP.VehicleGroup="+VehicleGroup+" "
								+ " AND VP.companyId = " + userDetails.getCompany_id() + " AND VP.createdDate BETWEEN '" + dateRangeFrom + "' AND  '"
								+ dateRangeTo + "'  ORDER BY VP.createdDate desc ",		
								Object[].class);
				
			List<Object[]> results = query.getResultList();
			
			 if (results != null && !results.isEmpty()) {
					vendorPaymentList = new ArrayList<VendorPaymentDTO>();				
					VendorPaymentDTO list = null;
					for (Object[] result : results) {
						list = new VendorPaymentDTO();					
						list.setVendorName((String) result[0]);
						list.setGstNumber((String) result[1]);
						list.setInvoiceNumber((String) result[2]);							
						list.setInvoiceDate((Date) result[3]);
						if(list.getInvoiceDate()!=null)
						{
							list.setInvoiceDateStr(list.getInvoiceDate()+"");
						}						
						list.setTransactionAmount((Double)result[4]);
						list.setGstAmount((Double)result[5]);				
						
						totalAmount=list.getTransactionAmount() + list.getGstAmount();  
						
						list.setTotalAmount(totalAmount);					
											
						vendorPaymentList.add(list);
					}
				}
			 valueOutObject	= new ValueObject();
			 valueOutObject.put("VendorGstReport", vendorPaymentList); 				
			 return	valueOutObject;
				
		} catch (Exception e) {
			throw e;
		} finally {
			userDetails						= null;
			valueOutObject					= null;
		}
	}			
	//EXP Vendor GST Report 1 END By Dev Y
	

	@SuppressWarnings("unchecked")
	@Override
	public ValueObject VendorPaymentApproval(ValueObject valueObject) throws Exception {
		ValueObject							outObject							= null;
		VendorApprovalSequenceCounter		sequenceCounter						= null;
		CustomUserDetails					userDetails							= null;
		VendorDto 							vendor 								= null;
		VendorApproval 						Approval							= null;
		VendorSubApprovalDetails 			subApproval							= null;
		ArrayList<ValueObject> 				dataArrayObj 						= null;
		String 								Other 								= "";
		String 								selectedval							= "";
		String								SE 									= "SE";
		String								PO                                  = "PO";
		String								TI                                  = "TI";
		String								TR                                  = "TR";
		String								BI                                  = "BI";
		String								PI                                  = "PI";
		String								CI                                  = "CI";
		String 								paymentDate							= "";
		double								totalApprovalAmount					= 0;
		long								invoiceId							= 0;
		short								paymentType							= 0;//cash,Neft,Rtgs
		String								payNumber							= "";
		short								placeId								= 0;
		try {
			outObject 		= new ValueObject();
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vendor = VenBL.getVendorDetails(vendorService.getVendorDetails(valueObject.getInt("vendorID"), userDetails.getCompany_id()));
			
			sequenceCounter	= approvalSequenceService.findNextSequenceNumber(userDetails.getCompany_id());
			if(sequenceCounter == null) {
				outObject.put("saveMessage", "Sequence not found please contact to system Administrator !");
				return outObject;
			}
			
				dataArrayObj	= (ArrayList<ValueObject>) valueObject.get("partInvoicePayment");
				
				if(dataArrayObj != null && !dataArrayObj.isEmpty()) {
					for (ValueObject object : dataArrayObj) {
						totalApprovalAmount	+= object.getDouble("approvalAmount");
						invoiceId			 = object.getLong("invoiceId") ;
						paymentType 		 = object.getShort("PaymentMode");
						payNumber 			 = object.getString("paymentModeNum");
					}
				}
				Approval 		= approval.prepareVendorApproval(vendor);
				Approval.setApprovalNumber(sequenceCounter.getNextVal());
				Approval.setApprovalCreateById(userDetails.getId());
				Approval.setApprovalpaidbyId(userDetails.getId());
				Approval.setInvoiceId(invoiceId);// No Use 
				Approval.setApprovalStatusId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
				Approval.setApprovalTotal(totalApprovalAmount);
				Approval.setApprovalDateofpayment(DateTimeUtility.getCurrentTimeStamp());// currentDate
				Approval.setApprovalPlaceId(ApprovalType.APPROVAL_TYPE_SERVICE_ENTRIES);// No Use 
				
				Approval.setApprovalPaymentTypeId(paymentType);// No Use 
				Approval.setApprovalPayNumber(payNumber);// No Use 
				
				vendorApprovalService.addVendorApproval(Approval);
				
				
				
				
				if(dataArrayObj != null && !dataArrayObj.isEmpty()) {
					for (ValueObject object : dataArrayObj) {
						 
						selectedval = object.getString("selectedVal");
						
						if (selectedval.equals(SE)) {
							placeId = ApprovalType.APPROVAL_TYPE_SERVICE_ENTRIES;
						}
						if (selectedval.equals(PO)) {
							placeId = ApprovalType.APPROVAL_TYPE_PURCHASE_ORDER;
						}
						if (selectedval.equals(TI)) {
							placeId = ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_INVOICE;
						}
						if (selectedval.equals(TR)) {
							placeId = ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_RETREAD;
						}
						if (selectedval.equals(BI)) {
							placeId = ApprovalType.APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE;
						}
						if (selectedval.equals(PI)) {
							placeId = ApprovalType.APPROVAL_TYPE_INVENTORY_PART_INVOICE;
						}
						if (selectedval.equals(CI)) {
							placeId = ApprovalType.APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE;
						}
						PendingVendorPayment pendingVendorPayment	=	pendingVendorPaymentService.getPendingVendorPaymentById(object.getLong("invoiceId",0), placeId);
						subApproval 		= approval.prepareVendorSubApprovalDetails(Approval, pendingVendorPayment);
						
						paymentDate			= object.getString("paymentDate");
						Date paidDate       = DateTimeUtility.getDateTimeFromStr(paymentDate, DateTimeUtility.YYYY_MM_DD);
						subApproval.setSubApprovalpaidAmount(object.getDouble("approvalAmount"));
						if(object.getShort("paymentType") == 2) {
							subApproval.setApprovedPaymentStatusId(FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID);
						}
						else if(object.getShort("paymentType") == 3) {
						subApproval.setApprovedPaymentStatusId(FuelVendorPaymentMode.PAYMENT_MODE_NEGOTIABLE_PAID);
						}else {
						subApproval.setApprovedPaymentStatusId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
						}
						
						vendorSubApprovalService.saveSubApproval(subApproval);
						
						
						partInvoiceService.update_PartInvoiceAmountOf_Inventory(object.get("invoiceId"),
								object.get("recievedAmount"),object.get("balanceAmt"),object.get("discountAmt"),subApproval.getApprovedPaymentStatusId(),Approval.getApprovalId(),paidDate);

						partInvoiceService.update_PartInvoiceAmountOf_BatteryInventory(object.get("invoiceId"),
								object.get("recievedAmount"),object.get("balanceAmt"),object.get("discountAmt"),subApproval.getApprovedPaymentStatusId(),Approval.getApprovalId(),paidDate);
						
						partInvoiceService.update_TyreInvoiceAmountOf_TyreInventory(object.get("invoiceId"),
								object.get("recievedAmount"),object.get("balanceAmt"),object.get("discountAmt"),subApproval.getApprovedPaymentStatusId(),Approval.getApprovalId(),paidDate);
						
						partInvoiceService.update_TyreRetreadInvoiceAmountOf_TyreRetreadInventory(object.get("invoiceId"),
								object.get("recievedAmount"),object.get("balanceAmt"),object.get("discountAmt"),subApproval.getApprovedPaymentStatusId(),Approval.getApprovalId(),paidDate);
						
						purchaseOrdersService.update_PurchaseOrderApprovalAmount(object.get("invoiceId"),
								object.get("recievedAmount"),object.get("balanceAmt"),object.get("discountAmt"),subApproval.getApprovedPaymentStatusId(),Approval.getApprovalId(),paidDate);
						
						partInvoiceService.update_ServiceEntriesApprovalAmount(object.get("invoiceId"),
								object.get("recievedAmount"),object.get("balanceAmt"),object.get("discountAmt"),subApproval.getApprovedPaymentStatusId(),Approval.getApprovalId(),paidDate);
						
						clothInvoiceService.update_ClothInvoiveApprovalAmount(object.get("invoiceId"),
								object.get("recievedAmount"),object.get("balanceAmt"),object.get("discountAmt"),subApproval.getApprovedPaymentStatusId(),Approval.getApprovalId(),paidDate);
					}
				}
			
			return valueObject;
		} 
		catch (Exception e) {
			LOGGER.error("Error", e);
			throw e;
		}finally {
			outObject			= null;
			userDetails			= null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ValueObject VendorFuelPaymentApproval(ValueObject valueObject) throws Exception {
		ValueObject							outObject							= null;
		VendorApprovalSequenceCounter		sequenceCounter						= null;
		CustomUserDetails					userDetails							= null;
		VendorDto 							vendor 								= null;
		VendorApproval 						Approval							= null;
		VendorSubApprovalDetails 			subApproval							= null;
		ArrayList<ValueObject> 				dataArrayObj 						= null;
		String 								paymentDate							= "";
		double								totalApprovalAmount					= 0;
		long								invoiceId							= 0;
		short								paymentType							= 0;//cash,Neft,Rtgs
		String								payNumber							= "";
		short								placeId								= 0;
		try {
			outObject 			= new ValueObject();
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			vendor = VenBL.getVendorDetails(vendorService.getVendorDetails(valueObject.getInt("vendorID"), userDetails.getCompany_id()));
			
			sequenceCounter	= approvalSequenceService.findNextSequenceNumber(userDetails.getCompany_id());
			if(sequenceCounter == null) {
				outObject.put("saveMessage", "Sequence not found please contact to system Administrator !");
				return outObject;
			}
			
			dataArrayObj	= (ArrayList<ValueObject>) valueObject.get("partInvoicePayment");
			
			if(dataArrayObj != null && !dataArrayObj.isEmpty()) {
				for (ValueObject object : dataArrayObj) {
					totalApprovalAmount	+= object.getDouble("approvalAmount");
					invoiceId			 = object.getLong("invoiceId") ;
					paymentType 		 = object.getShort("PaymentMode");
					payNumber 			 = object.getString("paymentModeNum");
				}
			}
			Approval 		= approval.prepareVendorApproval(vendor);
			Approval.setApprovalNumber(sequenceCounter.getNextVal());
			Approval.setApprovalCreateById(userDetails.getId());
			Approval.setApprovalpaidbyId(userDetails.getId());
			Approval.setInvoiceId(invoiceId);// No Use 
			Approval.setApprovalStatusId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
			Approval.setApprovalTotal(totalApprovalAmount);
			Approval.setApprovalDateofpayment(DateTimeUtility.getCurrentTimeStamp());// currentDate
			Approval.setApprovalPlaceId(ApprovalType.APPROVAL_TYPE_SERVICE_ENTRIES);// No Use 
			
			Approval.setApprovalPaymentTypeId(paymentType);// No Use 
			Approval.setApprovalPayNumber(payNumber);// No Use 
			
			vendorApprovalService.addVendorApproval(Approval);
			
			
			
			
			if(dataArrayObj != null && !dataArrayObj.isEmpty()) {
				PendingVendorPayment pendingVendorPayment	=	null;
				for (ValueObject object : dataArrayObj) {
					pendingVendorPayment	=	pendingVendorPaymentService.getPendingVendorPaymentById(object.getLong("invoiceId",0), placeId);
					subApproval 		= approval.prepareVendorSubApprovalDetails(Approval, pendingVendorPayment);
					
					paymentDate			= object.getString("paymentDate");
					Date paidDate       = DateTimeUtility.getDateTimeFromStr(paymentDate, DateTimeUtility.YYYY_MM_DD);
					subApproval.setSubApprovalpaidAmount(object.getDouble("approvalAmount"));
					
					if(object.getShort("paymentType") == 2) {
						subApproval.setApprovedPaymentStatusId(FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID);
					}
					else if(object.getShort("paymentType") == 3) {
					subApproval.setApprovedPaymentStatusId(FuelVendorPaymentMode.PAYMENT_MODE_NEGOTIABLE_PAID);
					}else {
						subApproval.setApprovedPaymentStatusId(FuelVendorPaymentMode.PAYMENT_MODE_PAID);
					}
					
					vendorSubApprovalService.saveSubApproval(subApproval);
					
						
						partInvoiceService.update_FuelInventoryAmount(object.get("invoiceId"),
								object.get("recievedAmount"),object.get("balanceAmt"),object.get("discountAmt"),subApproval.getApprovedPaymentStatusId(),Approval.getApprovalId(),paidDate);

					}
				}
			
			return valueObject;
		} 
		catch (Exception e) {
			LOGGER.error("Error", e);
			throw e;
		}finally {
			outObject			= null;
			userDetails			= null;
		}
	}
	
	@Override
	public ValueObject getPartiallyPaidApprovalDetails(ValueObject valueObject) throws Exception {
		short 				padiId 				= FuelVendorPaymentMode.PAYMENT_MODE_PAID;
		short 				partiallyPadiId 	= FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID;
		short 				negotiablePadiId 	= FuelVendorPaymentMode.PAYMENT_MODE_NEGOTIABLE_PAID;
		CustomUserDetails	userDetails			= null;
		try {
			
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT VA.approvalId, VA.approvalNumber, VA.approvalvendorID, V.vendorName, VT.vendor_TypeName, V.vendorLocation, VA.approvalStatusId,"
							+ " VA.approvalPlaceId, VA.approvalTotal, VA.approvalCreateDate, U.email, VA.created, VA.lastupdated "
							+ " FROM VendorApproval VA "
							+ " LEFT JOIN Vendor V ON V.vendorId = VA.approvalvendorID"
							+ " LEFT JOIN  VendorType VT ON VT.vendor_Typeid = V.vendorTypeId"
							+ " LEFT JOIN User U ON U.id = VA.approvalCreateById"
							+ " where VA.approvalStatusId IN(" + padiId + " ," + partiallyPadiId + " ," + negotiablePadiId + " ) AND invoiceId ="+valueObject.getLong("invoiceId")+" AND VA.companyId = "+userDetails.getCompany_id()+" AND VA.markForDelete = 0 ORDER BY VA.approvalId desc",
							Object[].class);
			List<Object[]> results = query.getResultList();
			
			List<VendorApprovalDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VendorApprovalDto>();
				VendorApprovalDto vendorApproval = null;
				for (Object[] result : results) {
					vendorApproval = new VendorApprovalDto();
					vendorApproval.setApprovalId((Long) result[0]);
					vendorApproval.setApprovalNumber((Long) result[1]);
					vendorApproval.setApprovalvendorID((Integer) result[2]);
					vendorApproval.setApprovalvendorName((String) result[3]);
					vendorApproval.setApprovalvendorType((String) result[4]);
					vendorApproval.setApprovalvendorLocation((String) result[5]);
					vendorApproval.setApprovalStatusId((short) result[6]);
					vendorApproval.setApprovalStatus(FuelVendorPaymentMode.getPaymentMode(vendorApproval.getApprovalStatusId()));
					vendorApproval.setApprovalPlaceId((short) result[7]);
					vendorApproval.setApprovalPlace(ApprovalType.getApprovalType(vendorApproval.getApprovalPlaceId()));
					vendorApproval.setApprovalTotal((Double) result[8]);
					vendorApproval.setApprovalCreateDate((Date) result[9]);
					vendorApproval.setApprovalCreateBy((String) result[10]);
					vendorApproval.setCreatedOn((Date) result[11]);
					vendorApproval.setCreated(vendorApproval.getCreatedOn().toString());
					vendorApproval.setLastupdatedOn((Date) result[12]);
					
					Dtos.add(vendorApproval);
					valueObject.put("approvalList", Dtos);
				}
			}
		}catch(Exception e){
			LOGGER.error("Error", e);
			throw e;
		}
		 return valueObject;
	}
	
	
	//Vendor Payment History Report Service Code Start
	
	@Override
	public ValueObject getVendorPaymentHistoryReport(ValueObject valueObject) throws Exception {
		String						dateRange				= null;		
		CustomUserDetails			userDetails				= null;
		List<VendorApprovalDto>		vendorList				= null;		
		ValueObject					tableConfig			    = null;
		int 						vendorId                = 0 ;		
			 
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRange	 	= valueObject.getString("dateRange");
			vendorId	    = valueObject.getInt("selectVendor", 0);		
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = From_TO_DateRange[0];
				dateRangeTo = From_TO_DateRange[1]+" "+DateTimeUtility.DAY_END_TIME;			
				
				String Vendor_Name = "", Date = "";				
				
				if(vendorId > 0 )
				{
					Vendor_Name = " AND V.vendorId = "+ vendorId +" ";
				}				
				
				Date			   =  " AND VA.approvalCreateDate between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;											
				
				String query       = " " + Vendor_Name + " "+ Date + " ";
				
				tableConfig		= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VENDOR_PAYMENT_HISTORY_TABLE_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);				
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);				
				vendorList	    = vendorReportDao.getVendorPaymentHistoryReportList(query, userDetails.getCompany_id());
				
			}
			
			
			valueObject.put("vendor", vendorList);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",
			userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			vendorList			= null;			
			userDetails			= null;
			dateRange			= null;
		}
	}
	
	@Override
	public ValueObject getVendorWisePaymentStatusReport(ValueObject valueObject) throws Exception {
		int							vendorType				= 0;
		int 						vendorId                = 0 ;
		int							status					= 0;
		final int					NOT_PAID				= 1;
		final int					Approved				= 2;
		final int					PAID					= 3;
		Object[] 					vendorCreditAmount 		= null;
		double 						vendorCredit 			= 0;
		double 						serviceEntries 			= 0;
		double 						purchaseOrder 			= 0;
		double 						fuelCredit 				= 0;
		double 						batteryCredit 			= 0;
		double 						partCredit 				= 0;
		double						tyreInventoryCredit		= 0;
		double						upholsteryCredit		= 0;
		try {
			vendorType	    = valueObject.getInt("vendorType", 0);		
			vendorId	    = valueObject.getInt("vendorId", 0);		
			status	    	= valueObject.getInt("status", 0);		
			
			vendorCreditAmount = vendorService.GET_VENDOR_CREDIT_AMOUNT_CountTotal_Cost_NotPaid(vendorId);
			
			if (vendorCreditAmount != null) {
				if (vendorCreditAmount[0] != null) {
					serviceEntries = (Double) vendorCreditAmount[0];
				}

				if (vendorCreditAmount[1] != null) {
					purchaseOrder = (Double) vendorCreditAmount[1];
				}

				if (vendorCreditAmount[2] != null) {
					fuelCredit = (Double) vendorCreditAmount[2];
				}

				if (vendorCreditAmount[3] != null) {
					batteryCredit = (Double) vendorCreditAmount[3];
				}
				if (vendorCreditAmount[4] != null) {
					tyreInventoryCredit = (Double) vendorCreditAmount[4];
				}
				if (vendorCreditAmount[5] != null) {
					partCredit = Double.parseDouble(vendorCreditAmount[5]+"") ;
				}
				if (vendorCreditAmount[6] != null) {
					upholsteryCredit = Double.parseDouble(vendorCreditAmount[6]+"") ;
				}

				vendorCredit = vendorCredit + serviceEntries + purchaseOrder + fuelCredit + batteryCredit+ tyreInventoryCredit+ partCredit+upholsteryCredit;
				String TotalShow = AMOUNT_FORMAT.format(vendorCredit);
				valueObject.put("TotalShow", TotalShow);
			}	
			
			if(status == NOT_PAID) {
				getVendorWiseNotPaidStatusReport(valueObject);
			} else if(status == Approved) {
				getVendorWiseApprovedStatusReport(valueObject);
			} else if(status == PAID) {
				getVendorWisePaidStatusReport(valueObject);
			}
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	public ValueObject getVendorWiseNotPaidStatusReport(ValueObject valueObject) throws Exception {
		int								vendorType				= 0;
		int 							vendorId                = 0;
		String							dateRange				= null;			
		CustomUserDetails				userDetails				= null;
		List<VendorPaymentNotPaidDto>	notPaidList				= null;		
		ValueObject						tableConfig			    = null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vendorType	    = valueObject.getInt("vendorType", 0);		
			vendorId	    = valueObject.getInt("vendorId", 0);		
			dateRange	 	= valueObject.getString("dateRange");		
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				switch (vendorType) {
				case ApprovalType.APPROVAL_TYPE_FUEL_ENTRIES:
					
					notPaidList	= fuelService.vendorPaymentNotPaidFuelList(vendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					valueObject.put("notPaidList", notPaidList);
					
					break;
					
				case ApprovalType.APPROVAL_TYPE_SERVICE_ENTRIES:
					
					notPaidList	= ServiceEntriesService.vendorPaymentNotPaidSEList(vendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					valueObject.put("notPaidList", notPaidList);
					
					break;
					
				case ApprovalType.APPROVAL_TYPE_PURCHASE_ORDER:
					
					notPaidList = purchaseOrdersService.vendorPaymentNotPaidPOList(vendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					valueObject.put("notPaidList", notPaidList);

					break;
					
				case ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_INVOICE:
					
					notPaidList = iventoryTyreService.vendorPaymentNotPaidTyreInvoiceList(vendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					valueObject.put("notPaidList", notPaidList);
					
					break;
				
				case ApprovalType.APPROVAL_TYPE_INVENTORY_TYRE_RETREAD:

					notPaidList = iventoryTyreService.vendorPaymentNotPaidTyreRethreadList(vendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					valueObject.put("notPaidList", notPaidList);
					
					break;
				
				case ApprovalType.APPROVAL_TYPE_INVENTORY_BATTERY_INVOICE:
					
					notPaidList = batteryInvoiceService.vendorPaymentNotPaidBatteryInvoiceList(vendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					valueObject.put("notPaidList", notPaidList);
					
					break;	
				
				case ApprovalType.APPROVAL_TYPE_INVENTORY_PART_INVOICE:
					
					notPaidList = partInvoiceService.vendorPaymentNotPaidPartInvoiceList(vendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					valueObject.put("notPaidList", notPaidList);
					
					break;
				
				case ApprovalType.APPROVAL_TYPE_INVENTORY_CLOTH_INVOICE:
					
					notPaidList = clothInvoiceService.vendorPaymentNotPaidClothInvoiceList(vendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
					valueObject.put("notPaidList", notPaidList);
					
					break;	
					
				}
				
				tableConfig		= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VENDOR_PAYMENT_NOT_PAID_TABLE_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);				
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				
				valueObject.put("tableConfig", tableConfig.getHtData());
				valueObject.put("company",
				userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
				
			}
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			notPaidList			= null;			
			userDetails			= null;
			dateRange			= null;
		}
	}
	
	public ValueObject getVendorWiseApprovedStatusReport(ValueObject valueObject) throws Exception {
		int 							vendorId                = 0;
		String							dateRange				= null;			
		CustomUserDetails				userDetails				= null;
		ValueObject						tableConfig			    = null;
		List<VendorApprovalDto>			vendorList				= null;	
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vendorId	    = valueObject.getInt("vendorId", 0);		
			dateRange	 	= valueObject.getString("dateRange");		
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				tableConfig		= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VENDOR_PAYMENT_APPROVED_TABLE_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);				
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				vendorList	    = vendorReportDao.vendorWiseApprovedStatusReport(vendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
				
				valueObject.put("vendor", vendorList);
				valueObject.put("tableConfig", tableConfig.getHtData());
				valueObject.put("company",
				userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
				
			}
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			userDetails			= null;
			dateRange			= null;
		}
	}
	
	public ValueObject getVendorWisePaidStatusReport(ValueObject valueObject) throws Exception {
		int 							vendorId                = 0;
		String							dateRange				= null;			
		CustomUserDetails				userDetails				= null;
		ValueObject						tableConfig			    = null;
		List<VendorApprovalDto>			vendorList				= null;	
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vendorId	    = valueObject.getInt("vendorId", 0);		
			dateRange	 	= valueObject.getString("dateRange");		
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				tableConfig		= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.VENDOR_PAYMENT_PAID_TABLE_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);				
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				vendorList	    = vendorReportDao.vendorWisePaidStatusReport(vendorId, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
				
				valueObject.put("vendor", vendorList);
				valueObject.put("tableConfig", tableConfig.getHtData());
				valueObject.put("company",
				userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
				
			}
			
			return valueObject;
			
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			userDetails			= null;
			dateRange			= null;
		}
	}
	
	@Override
	public ValueObject getPendingVendorPaymentList(ValueObject valueObject) throws Exception {
		
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String query = "";
		
		if(valueObject.getInt("vendorId",0) > 0) {
			query += "BH.vendorId = "+valueObject.getInt("vendorId",0)+"";
		}
		if(valueObject.containsKey("dateRange")) {
			ValueObject	dateObject	= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"), DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			query += "AND BH.transactionDate between  '"+dateObject.getString(DateTimeUtility.FROM_DATE)+"' AND '"+dateObject.getString(DateTimeUtility.TO_DATE)+"' ";
		}
		valueObject.put("pendingPaymentList", pendingVendorPaymentService.getPendingVendorPayment(query, userDetails.getCompany_id()));
		
		return valueObject;
	}
	
	@Override
	public ValueObject getVendorApprovalForPayment(ValueObject valueObject) throws Exception {
		String	query	= "";
		try {
			ValueObject		dateObject	= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"), DateTimeUtility.DD_MM_YY_HH_MM_SS, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			query += "AND VA.approvalvendorID = "+valueObject.getInt("vendorId",0)+" AND VA.approvalCreateDate between '"+dateObject.getString("FromDate")+"' "
					+ " AND '"+dateObject.getString("ToDate")+"' ";
			
			valueObject.put("approvalList", vendorApprovalService.findAllVendorApprovalsByStatusId(FuelVendorPaymentMode.PAYMENT_MODE_APPROVED, query)); 
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
}