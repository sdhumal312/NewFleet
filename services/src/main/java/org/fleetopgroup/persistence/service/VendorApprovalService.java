package org.fleetopgroup.persistence.service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.fleetopgroup.constant.ApprovalType;
import org.fleetopgroup.constant.FuelVendorPaymentMode;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PendingPaymentType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TallyVoucherTypeContant;
import org.fleetopgroup.persistence.bl.VendorApprovalBL;
import org.fleetopgroup.persistence.dao.VendorApprovalRepository;
import org.fleetopgroup.persistence.dao.VendorSubApprovalDetailsRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.VendorApprovalDto;
import org.fleetopgroup.persistence.dto.VendorSubApprovalDetailsDto;
import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.VendorApproval;
import org.fleetopgroup.persistence.model.VendorApprovalSequenceCounter;
import org.fleetopgroup.persistence.model.VendorSubApprovalDetails;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IBatteryInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IClothInventoryService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDealerServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IFuelInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;
import org.fleetopgroup.persistence.serviceImpl.IPartInvoiceService;
import org.fleetopgroup.persistence.serviceImpl.IPendingVendorPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUreaInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IVendorApprovalSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IVendorApprovalService;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.fleetopgroup.persistence.serviceImpl.IVendorSubApprovalDetailsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConvertor;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("VendorApprovalService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class VendorApprovalService implements IVendorApprovalService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired private VendorApprovalRepository 				vendorApprovalDao;
	@Autowired private IVendorService							vendorService;
	@Autowired private IVendorApprovalSequenceService			approvalSequenceService;
	@Autowired private IPendingVendorPaymentService				pendingVendorPaymentService;
	@Autowired private IVendorSubApprovalDetailsService 		vendorSubApprovalService;
	@Autowired private IFuelService								fuelService;
	@Autowired private IServiceEntriesService					serviceEntriesService;
	@Autowired private IPurchaseOrdersService					purchaseOrdersService;
	@Autowired private IInventoryTyreService 					inventoryTyreService;
	@Autowired private IBatteryInvoiceService					batteryInvoiceService;
	@Autowired private IPartInvoiceService						partInvoiceService;
	@Autowired private IClothInventoryService					clothInventoryService;
	@Autowired private IUreaInventoryService					ureaInventoryService;
	@Autowired private IFuelInvoiceService						fuelInvoiceService;
	@Autowired private ICompanyConfigurationService				companyConfigurationService;
	@Autowired private VendorSubApprovalDetailsRepository		vendorSubApprovalDetailsRepository;
	@Autowired private IDealerServiceEntriesService				dealerServiceEntriesService;
	@Autowired private IBankPaymentService						bankPaymentService;
	@Autowired private ICashPaymentService						cashPaymentService;
	
	VendorApprovalBL approvalBL = new VendorApprovalBL();
	
	SimpleDateFormat					tallyFormat					= new SimpleDateFormat("yyyyMMdd");
	SimpleDateFormat					dateFormat					= new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat					viewFormat					= new SimpleDateFormat("dd-MM-yyyy HH:mm:ss ");
	SimpleDateFormat                  dateFormatSQL					= new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat 						toFixedTwo 					= new DecimalFormat("#.##");

	private static final int PAGE_SIZE = 10;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addVendorApproval(VendorApproval VendorApproval) throws Exception {

		vendorApprovalDao.save(VendorApproval);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void updateVendorApproval(VendorApproval VendorApproval) throws Exception {

		vendorApprovalDao.save(VendorApproval);
	}

	@Transactional
	public VendorApproval getVendorApproval(Long approvalId, Integer companyId) throws Exception {
		return vendorApprovalDao.getVendorApproval(approvalId, companyId);
	}
	
	@Override
	public VendorApprovalDto getVendorApprovalDetails(Long VendorApproval_id, Integer companyId) throws Exception {
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT VA.approvalId, VA.approvalNumber, VA.approvalvendorID, V.vendorName, VT.vendor_TypeName, V.vendorLocation, VA.approvalStatusId,"
					+ " VA.approvalPlaceId, VA.approvalTotal, VA.approvalCreateDate, U.email, VA.created, VA.lastupdated, VA.approvalPaymentTypeId, VA.approvalPayNumber,"
					+ " U.firstName , VA.approvalDateofpayment, U2.email, U3.email, VA.approvalCreateById, VA.approvalPaidTotal, V.vendorTDSPercent, VA.approvalTDSAmount"
					+ " FROM VendorApproval VA "
					+ " LEFT JOIN Vendor V ON V.vendorId = VA.approvalvendorID"
					+ " LEFT JOIN  VendorType VT ON VT.vendor_Typeid = V.vendorTypeId"
					+ " LEFT JOIN User U ON U.id = VA.approvalCreateById"
					+ " LEFT JOIN User U2 ON U2.id = VA.lastModifiedById"
					+ " LEFT JOIN User U3 ON U3.id = VA.createdById"
					+ " LEFT JOIN User U4 ON U4.id = VA.approvalpaidbyId"
					+ " where VA.approvalId =" + VendorApproval_id + " AND VA.companyId = "+companyId+" AND VA.markForDelete = 0 ORDER BY VA.approvalId desc",
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
					vendorApproval.setApprovalStatus(FuelVendorPaymentMode.getPaymentMode((short) result[6]));
					vendorApproval.setApprovalPlaceId((short) result[7]);
					vendorApproval.setApprovalPlace(ApprovalType.getApprovalType((short) result[7]));
					vendorApproval.setApprovalTotal((Double) result[8]);
					vendorApproval.setApprovalCreateDate((Date) result[9]);
					if(result[9] != null)
					vendorApproval.setApprovalCreateDateStr(viewFormat.format((Date) result[9]));
					vendorApproval.setApprovalCreateBy((String) result[10]);
					vendorApproval.setCreatedOn((Date) result[11]);
					if(result[11] != null)
					vendorApproval.setCreated(viewFormat.format((Date) result[11]));
					vendorApproval.setLastupdatedOn((Date) result[12]);
					vendorApproval.setApprovalPaymentTypeId((short) result[13]);
					vendorApproval.setApprovalPaymentType(PaymentTypeConstant.getPaymentTypeName((short) result[13]));
					vendorApproval.setApprovalPayNumber((String) result[14]);
					vendorApproval.setApprovalpaidby((String) result[15]);
					vendorApproval.setApprovalDateofpaymentOn((Date) result[16]);
					vendorApproval.setLastModifiedBy((String) result[17]);
					vendorApproval.setCreatedBy((String) result[18]);
					vendorApproval.setApprovalCreateById((Long) result[19]);
					if(result[20] != null) {
						vendorApproval.setApprovalPaidTotal((Double) result[20]);
					}
					if(result[21] !=null)
						vendorApproval.setVendorTDSPercent((Double) result[21]);
					if(result[22] != null)
						vendorApproval.setTDSAmount((Double) result[22]);
					Dtos.add(vendorApproval);
				}
			}
			if(Dtos != null) {
				return Dtos.get(0);
			}else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Transactional
	public void updateVendorApproval_Aoumt(Long approvalID, Double approvalAmount) throws Exception {

		vendorApprovalDao.updateVendorApprovalAmount(approvalID, approvalAmount);
	}

	@Transactional
	public void update_Remove_VendorApproval_Aoumt(Long VendorApproval, Double ApprovalAmount) throws Exception {

		entityManager.createQuery("UPDATE FROM VendorApproval SET approvalTotal = approvalTotal - "
				+ ApprovalAmount + "  where approvalId=" + VendorApproval + "").executeUpdate();
	}

	@Override
	@Transactional
	public void updateApprovedPayment_Details(Long approvalId, short paymentType, String paymentNumber,
			Date approvalCreateDate, Long paymentPaidBy, short approvedStutes, Long lastUpdateBy, 
			Date lastUpdatedDate,double paidAmount)
			throws Exception {
		vendorApprovalDao.updateApprovedPaymentDetails(approvalId, paymentType, paymentNumber, approvalCreateDate,
				paymentPaidBy, approvedStutes,lastUpdateBy, lastUpdatedDate, paidAmount);
	}
	
	@Transactional
	public List<VendorApproval> findAllVendorApprovalList(Integer companyId) throws Exception {

		return vendorApprovalDao.findAllVendorApprovalList(companyId);
	}

	/** This Page get Vendor table to get pagination values */
	@Transactional
	public Page<VendorApproval> getDeployment_Page_VendorApproval(short approvalStatus, Integer pageNumber, Integer companyId) {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return vendorApprovalDao.getDeploymentPageVendorApproval(approvalStatus, companyId, pageable);
	}

	@Transactional
	public List<VendorApprovalDto> findAll_VendorApproval(short approvalStatus, Integer pageNumber, Integer companyId) throws Exception {
		
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT VA.approvalId, VA.approvalNumber, VA.approvalvendorID, V.vendorName, VT.vendor_TypeName, V.vendorLocation, VA.approvalStatusId,"
				+ " VA.approvalPlaceId, VA.approvalTotal, VA.approvalCreateDate, U.email, VA.created, VA.lastupdated "
				+ " FROM VendorApproval VA "
				+ " LEFT JOIN Vendor V ON V.vendorId = VA.approvalvendorID"
				+ " LEFT JOIN  VendorType VT ON VT.vendor_Typeid = V.vendorTypeId"
				+ " LEFT JOIN User U ON U.id = VA.approvalCreateById"
				+ " where VA.approvalStatusId =" + approvalStatus + " AND VA.companyId = "+companyId+" AND VA.markForDelete = 0 ORDER BY VA.approvalId desc",
				Object[].class);
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		
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
				vendorApproval.setApprovalPlaceId((short) result[7]);
				vendorApproval.setApprovalTotal((Double) result[8]);
				vendorApproval.setApprovalCreateDate((Date) result[9]);
				vendorApproval.setApprovalCreateBy((String) result[10]);
				vendorApproval.setCreatedOn((Date) result[11]);
				if(result[11] != null)
				vendorApproval.setCreated(viewFormat.format((Date)result[11]));
				vendorApproval.setLastupdatedOn((Date) result[12]);

				Dtos.add(vendorApproval);
			}
		}
		return Dtos;
	}

	@Transactional
	public List<VendorApproval> findAllVendorApproval(String approvalStatus) throws Exception {

		// return vendorApprovalDao.findAllVendorApproval(approvalStatus);

		TypedQuery<VendorApproval> query = entityManager.createQuery(
				"FROM VendorApproval VT where VT.approvalStatus ='" + approvalStatus + "'  ORDER BY VT.approvalId desc",
				VendorApproval.class);
		return query.getResultList();
	}

	@Transactional
	public List<VendorApproval> listVendorApproval() throws Exception {

		TypedQuery<VendorApproval> query = entityManager
				.createQuery("FROM VendorApproval as v ORDER BY v.approvalId desc", VendorApproval.class);
		query.setFirstResult(0);
		query.setMaxResults(10);
		return query.getResultList();
	}

	@Transactional
	public void updateVendorApprovedBY_andDate(Long approvalId, Long approvedBy, Date approvalCreateDate,
			short approvalStatus) throws Exception { 

		vendorApprovalDao.updateVendorApprovedByAndDate(approvalId, approvedBy, approvalCreateDate,
				approvalStatus);
	}

	@Transactional
	public void deleteVendorApproval(Long VendorApproval) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		vendorApprovalDao.deleteVendorApproval(VendorApproval, userDetails.getCompany_id());
	}

	@Transactional
	public Long countVendorApproval() throws Exception {

		return vendorApprovalDao.countVendorApproval();
	}

	@Transactional
	public Long countVendorApprovedToday(Date Today) throws Exception {

		return vendorApprovalDao.countVendorApprovedToday(Today);
	}

	/*@Transactional
	public Long countVendorApproved(String approvalStatus) throws Exception {

		return vendorApprovalDao.countVendorApproved(approvalStatus);
	}*/

	/*@Transactional
	public List<VendorApproval> findAllApproval_PaymentList(String Approval_Stutes) throws Exception {

		return vendorApprovalDao.findAllApproval_PaymentList(Approval_Stutes);
	}*/

	@Transactional
	public List<VendorApproval> listVendorApproval(String qurey) throws Exception {

		TypedQuery<VendorApproval> query = entityManager.createQuery("from VendorApproval where " + qurey + " ",
				VendorApproval.class);
		return query.getResultList();
	}

	@Transactional
	public void deleteVendorApproval(VendorApproval VendorApproval) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		vendorApprovalDao.deleteVendorApproval(VendorApproval.getApprovalId(), userDetails.getCompany_id());
	}

	@Transactional
	public List<VendorApprovalDto> SearchVendorApproval(String Search, Integer companyId) throws Exception {
		List<VendorApprovalDto> Dtos = null;
		if(Search != null && !Search.trim().equalsIgnoreCase("") && Search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT VA.approvalId, VA.approvalNumber, VA.approvalvendorID, V.vendorName, VT.vendor_TypeName, V.vendorLocation, VA.approvalStatusId,"
				+ " VA.approvalPlaceId, VA.approvalTotal, VA.approvalCreateDate, U.email, VA.created, VA.lastupdated "
				+ " FROM VendorApproval VA "
				+ " LEFT JOIN Vendor V ON V.vendorId = VA.approvalvendorID"
				+ " LEFT JOIN  VendorType VT ON VT.vendor_Typeid = V.vendorTypeId"
				+ " LEFT JOIN User U ON U.id = VA.approvalCreateById"
				+ " where ( lower(VA.approvalNumber) Like ('%" + Search 
				+ "%') OR lower(V.vendorName) Like ('%" + Search + "%') OR lower(VA.approvalTotal) Like ('%"
				+ Search + "%') OR lower(VT.vendor_TypeName) Like ('%" + Search + "%')) AND VA.companyId = "+companyId+" AND VA.markForDelete = 0 ",
				Object[].class);
		List<Object[]> results = query.getResultList();
		
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
				vendorApproval.setApprovalPlaceId((short) result[7]);
				vendorApproval.setApprovalTotal((Double) result[8]);
				vendorApproval.setApprovalCreateDate((Date) result[9]);
				vendorApproval.setApprovalCreateBy((String) result[10]);
				vendorApproval.setCreatedOn((Date) result[11]);
				vendorApproval.setLastupdatedOn((Date) result[12]);

				Dtos.add(vendorApproval);
			}
		}
		}
		return Dtos;
	
	}
	
	
	@Transactional
	public List<VendorApprovalDto> findAll_VendorPaidApproval(short paidStatus,short partiallyPaidStatus,short negotiablePaidStatus, Integer pageNumber, Integer companyId) throws Exception {
		
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT VA.approvalId, VA.approvalNumber, VA.approvalvendorID, V.vendorName, VT.vendor_TypeName, V.vendorLocation, VA.approvalStatusId,"
				+ " VA.approvalPlaceId, VA.approvalTotal, VA.approvalCreateDate, U.email, VA.created, VA.lastupdated, VA.approvalPaidTotal  "
				+ " FROM VendorApproval VA "
				+ " LEFT JOIN Vendor V ON V.vendorId = VA.approvalvendorID"
				+ " LEFT JOIN  VendorType VT ON VT.vendor_Typeid = V.vendorTypeId"
				+ " LEFT JOIN User U ON U.id = VA.approvalCreateById"
				+ " where (VA.approvalStatusId =" + paidStatus + " OR VA.approvalStatusId =" + partiallyPaidStatus + " OR VA.approvalStatusId =" + negotiablePaidStatus + " ) AND VA.companyId = "+companyId+" AND VA.markForDelete = 0 ORDER BY VA.approvalId desc",
				Object[].class);
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		
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
				vendorApproval.setApprovalPlaceId((short) result[7]);
				vendorApproval.setApprovalTotal((Double) result[8]);
				vendorApproval.setApprovalCreateDate((Date) result[9]);
				vendorApproval.setApprovalCreateBy((String) result[10]);
				vendorApproval.setCreatedOn((Date) result[11]);
				if(result[11] != null)
				vendorApproval.setCreated(viewFormat.format((Date) result[11]));
				vendorApproval.setLastupdatedOn((Date) result[12]);
				if(result[13] != null)
					vendorApproval.setApprovalPaidTotal((Double) result[13]);

				Dtos.add(vendorApproval);
			}
		}
		return Dtos;
	}
	
	
	
	
/*	@Transactional
	public List<VendorApprovalDto> findAll_VendorApprovalPaymentEntries(short approvalStatus, Integer pageNumber, Integer companyId,short typeOfPaymentId,short approvalStatusPaid) throws Exception {
		
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT VA.approvalId, VA.approvalNumber, VA.approvalvendorID, V.vendorName, VT.vendor_TypeName, V.vendorLocation, VA.approvalStatusId,"
				+ " VA.approvalPlaceId, VA.approvalTotal, VA.approvalCreateDate, U.email, VA.created, VA.lastupdated, VA.typeOfPaymentId, VA.paidAmount "
				+ " FROM VendorApproval VA "
				+ " LEFT JOIN Vendor V ON V.vendorId = VA.approvalvendorID"
				+ " LEFT JOIN  VendorType VT ON VT.vendor_Typeid = V.vendorTypeId"
				+ " LEFT JOIN User U ON U.id = VA.approvalCreateById"
				+ " where ((VA.approvalStatusId = " + approvalStatus + " AND VA.typeOfPaymentId = 0 ) OR (VA.approvalStatusId = " + approvalStatusPaid + " AND VA.typeOfPaymentId = " + typeOfPaymentId + ")) "
				+ " AND VA.companyId = "+companyId+" AND VA.markForDelete = 0 ORDER BY VA.approvalId desc",
				Object[].class);
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		
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
				vendorApproval.setApprovalPlaceId((short) result[7]);
				
				vendorApproval.setApprovalTotal((Double) result[8]);
				vendorApproval.setApprovalCreateDate((Date) result[9]);
				vendorApproval.setApprovalCreateBy((String) result[10]);
				vendorApproval.setCreatedOn((Date) result[11]);
				vendorApproval.setLastupdatedOn((Date) result[12]);
				
				vendorApproval.setTypeOfPaymentId((Short)result[13]);
				
				vendorApproval.setPaidAmount((Long)result[14]);

				Dtos.add(vendorApproval);
			}
		}
		return Dtos;
	}*/
	
	/*@Override
	@Transactional
	public void updateApprovedPayment_DetailsInApprovedList(Long VendorApproval, short PaymentType, String PaymentNumber,
			Date approvalCreateDate, Long PaymentPaidBy, short ApprovedStutes, Long lastUpdateBy, 
			Date lastUpdatedDate, String TypeOfPaymentId, String PaidAmount, String BalanceAmount)
			throws Exception {
		vendorApprovalDao.updateApprovedPayment_DetailsInApprovedList(VendorApproval, PaymentType, PaymentNumber, approvalCreateDate,
				PaymentPaidBy, ApprovedStutes, lastUpdateBy, lastUpdatedDate, TypeOfPaymentId, PaidAmount, BalanceAmount);
	}*/
	
	@Override
	@Transactional
	public void updateTallyCompany(Long tallyCompanyId, Long approvalId) throws Exception {
		
		vendorApprovalDao.updateTallyCompany(tallyCompanyId, approvalId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> findVendorPaymentList(String fromDate, String toDate, Integer companyId,
			String tallyCompany) throws Exception {
		Query query = entityManager.createQuery(
				"SELECT VA.approvalId, VA.approvalNumber, VA.approvalvendorID, VA.approvalDateofpayment, VA.approvalPaidTotal, VA.tallyCompanyId,"
						+ " TC.companyName, V.vendorName, VA.approvalPaymentTypeId, VA.isPendingForTally "
						+ " FROM VendorApproval VA "
						+ " INNER JOIN TallyCompany TC ON TC.tallyCompanyId = VA.tallyCompanyId AND  TC.companyName = '"+tallyCompany+"'"
						+ " INNER JOIN Vendor AS V ON V.vendorId = VA.approvalvendorID "
						+ " WHERE VA.approvalDateofpayment between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
						+ " AND VA.companyId = "+companyId+" AND VA.markForDelete = 0");

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
					select.setVendorId((Integer) vehicle[2]);
					select.setVoucherDate(tallyFormat.format(vehicle[3]));
					select.setExpenseAmount((Double) vehicle[4]);
					select.setTallyCompanyId((Long) vehicle[5]);
					select.setTallyCompanyName((String) vehicle[6]);
					select.setVendorName((String) vehicle[7]);
					select.setExpenseFixedId((short) vehicle[8]);
					select.setPendingForTally((boolean) vehicle[9]);
					select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
					select.setLedgerName("Hdfc Bank");
					select.setRemark("Vendor Payment done for vendor approval number VA-"+select.getTripSheetNumber());
					select.setExpenseTypeId(TallyVoucherTypeContant.VENDOR_APPROVAL_PAYMENT);
					select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
					select.setCreatedStr(dateFormat.format(vehicle[3]));
					
					if(select.getVendorName() == null ) {
						select.setVendorName("--");
					}
					
					if(select.getTallyCompanyName() == null) {
						select.setTallyCompanyName("--");
					}
					select.setTripSheetNumberStr("VA-"+select.getTripSheetNumber());
					
					Dtos.add(select);
				}
			}
			
		}
		return Dtos;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TripSheetExpenseDto> findVendorPaymentListATC(String fromDate, String toDate, Integer companyId,
			String tallyCompany) throws Exception {
		Query query = entityManager.createQuery(
				"SELECT VA.approvalId, VA.approvalNumber, VA.approvalvendorID, VA.approvalDateofpayment, VA.approvalPaidTotal, VA.tallyCompanyId,"
						+ " V.vendorName, VA.approvalPaymentTypeId, VA.isPendingForTally, BM.bankName, BA.accountNumber "
						+ " FROM VendorApproval VA "
						+ " INNER JOIN Vendor AS V ON V.vendorId = VA.approvalvendorID "
						+ " LEFT JOIN BankPayment BP ON BP.moduleId = VA.approvalId AND BP.moduleIdentifier = "+ModuleConstant.APPROVAL_ENTRIES+" "
										+ "	AND BP.markForDelete = 0 AND VA.approvalPaymentTypeId <>  "+PaymentTypeConstant.PAYMENT_TYPE_CASH+" "
						+ " LEFT JOIN BankAccount BA ON BA.bankAccountId = BP.bankAccountId "
						+ " LEFT JOIN BankMaster BM ON BM.bankId = BA.bankId "
						+ " WHERE VA.approvalDateofpayment between '"+fromDate+"' AND '"+DateTimeUtility.getEndOfDayDateStr(toDate, DateTimeUtility.YYYY_MM_DD)+"' "
						+ " AND VA.companyId = "+companyId+" AND VA.markForDelete = 0");

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
					select.setVendorId((Integer) vehicle[2]);
					select.setVoucherDate(tallyFormat.format(vehicle[3]));
					select.setExpenseAmount((Double) vehicle[4]);
					select.setTallyCompanyId((Long) vehicle[5]);
					select.setTallyCompanyName(tallyCompany);
					select.setVendorName((String) vehicle[6]);
					select.setExpenseFixedId((short) vehicle[7]);
					select.setPendingForTally((boolean) vehicle[8]);
					
					select.setExpenseFixed(PaymentTypeConstant.getPaymentTypeName(select.getExpenseFixedId()));
					if(vehicle[9] != null)
						select.setLedgerName(vehicle[9]+"  A/C NO-"+vehicle[10]);
					else
						select.setLedgerName("Cash");
					
					select.setRemark("Vendor Payment done for vendor approval number VA-"+select.getTripSheetNumber());
					select.setExpenseTypeId(TallyVoucherTypeContant.VENDOR_APPROVAL_PAYMENT);
					select.setExpenseType(TallyVoucherTypeContant.getExpenseTypeName(select.getExpenseTypeId()));
					select.setCreatedStr(dateFormat.format(vehicle[3]));
					
					if(select.getVendorName() == null ) {
						select.setVendorName("--");
					}
					
					if(select.getTallyCompanyName() == null) {
						select.setTallyCompanyName("--");
					}
					select.setTripSheetNumberStr("VA-"+select.getTripSheetNumber());
					
					Dtos.add(select);
				}
			}
			
		}
		return Dtos;
	}

	@Override
	@Transactional
	public void updateVendorApprovalPaidAmount(Long approvalId, Double amount) throws Exception {
		entityManager.createQuery("UPDATE  VendorApproval SET approvalPaidTotal = "+amount+"  "
			                    + " where approvalId=" + approvalId + "").executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public ValueObject createVendorApproval(ValueObject valueObject) throws Exception {
		VendorApproval 						approval					= null;
		CustomUserDetails					userDetails					= null;
		VendorApprovalSequenceCounter		sequenceCounter				= null;
		ArrayList<ValueObject> 				dataArrayObjColl 			= null;
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Vendor	vendor	= vendorService.getVendor(valueObject.getInt("vendorId"));
			if(vendor != null) {
				approval		= VendorApprovalBL.getVendorApprovalDTO(vendor, userDetails);
				sequenceCounter	= approvalSequenceService.findNextSequenceNumber(userDetails.getCompany_id());
				approval.setApprovalNumber(sequenceCounter.getNextVal());
				approval.setInvoiceId((long) 0);
				if(valueObject.getShort("approvalType") == 2) {
					approval.setApprovalStatusId(FuelVendorPaymentMode.PAYMENT_MODE_APPROVED);
				}
				
				addVendorApproval(approval);
				
				valueObject.put("approval", approval);
				
				dataArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("selectedInvoice");
				proccessSubApprovaldetails(valueObject, dataArrayObjColl, userDetails);
			}
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
		
	
	private ValueObject  proccessSubApprovaldetails(ValueObject	valueObject, ArrayList<ValueObject>  dataArrayObjColl, CustomUserDetails	userDetails) throws Exception {
		VendorSubApprovalDetails 				subApproval				= null;
		PendingVendorPayment					pendingVendorPayment	= null;
		VendorApproval							approval				= null;
		Double									totalAmount				= 0.0;
		HashMap<String, Object>			        configuration 			= null;
		
		try {
			approval	= (VendorApproval) valueObject.get("approval");
			if(dataArrayObjColl != null && !dataArrayObjColl.isEmpty() && approval != null) {
				for (ValueObject object : dataArrayObjColl) {
					pendingVendorPayment	=	pendingVendorPaymentService.getPendingVendorPaymentById(object.getLong("transactionId",0), object.getShort("transactionTypeId"));
					totalAmount += pendingVendorPayment.getBalanceAmount();
					subApproval	=	approvalBL.prepareVendorSubApprovalDetails(approval, pendingVendorPayment);
					if(valueObject.getShort("approvalType") == 2) {
						subApproval.setApprovedPaymentStatusId(FuelVendorPaymentMode.PAYMENT_MODE_APPROVED);
						subApproval.setSubApprovalpaidAmount(subApproval.getSubApprovalTotal());
					}
					vendorSubApprovalService.saveSubApproval(subApproval);
					updateTransactionStatus(pendingVendorPayment.getTransactionId()+"", pendingVendorPayment.getTxnTypeId(), approval.getApprovalId(), userDetails.getCompany_id());
					pendingVendorPaymentService.updateVendorPaymentStatus(pendingVendorPayment.getTransactionId(), pendingVendorPayment.getTxnTypeId(), PendingPaymentType.PAYMENT_STATUS_APPROVAL_CREATED);
				}
				
					updateVendorApproval_Aoumt(approval.getApprovalId(), Double.parseDouble(toFixedTwo.format(totalAmount)));
				
					valueObject.put("totalAmount", totalAmount);
			}
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void updateTransactionStatus(String transactionIds, short type, Long approvalId, Integer companyId) throws Exception {
		
		if(type == PendingPaymentType.PAYMENT_TYPE_FUEL_ENTRIES) {
			fuelService.update_Vendor_ApprovalTO_Status_MULTIP_FUEL_ID(transactionIds, approvalId, FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL, companyId);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_SERVICE_ENTRIES) {
			serviceEntriesService.update_Vendor_ApprovalTO_Status_MULTIP_SERVICE_ID(transactionIds,approvalId, FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_PURCHASE_ORDER) {
			purchaseOrdersService.update_Vendor_ApprovalTO_Status_MULTIP_PurchaseOrders(transactionIds,approvalId, FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_TYRE_INVOICE) {
			inventoryTyreService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreInvoice_ID(transactionIds, approvalId, FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_TYRE_RETREAD_INVOICE) {
			inventoryTyreService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreRetread_ID(transactionIds, approvalId, FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_BATTERY_INVOICE) {
			batteryInvoiceService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryBatteryInvoice_ID(transactionIds, approvalId, FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_PART_INVOICE) {
			partInvoiceService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryPartInvoice_ID(transactionIds, approvalId, FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_CLOTH_INVOICE) {
			clothInventoryService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryClothInvoice_ID(transactionIds, approvalId, FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_UREA_INVOICE) {
			ureaInventoryService.update_Vendor_ApprovalTO_StatusUreaInvoice_ID(transactionIds, approvalId, FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL, companyId);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_LAUNDRY_INVOICE) {
			clothInventoryService.update_Vendor_ApprovalTO_Status_LaundryInvoice_ID(transactionIds, approvalId, FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL, companyId);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_FUEL_INVOICE) {
			fuelInvoiceService.updateApprovalIdToInvoice(transactionIds, approvalId, FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL, companyId);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_DEALER_SERVICE_ENTRIES) {
			dealerServiceEntriesService.updateApprovalIdToDealerServiceEntries(transactionIds, approvalId, FuelVendorPaymentMode.PAYMENT_MODE_CREATE_APPROVAL, companyId);
		}
	
	}
	
	@Transactional
	public void updateTransactionStatus(String transactionIds, short type, Long approvalId, Integer companyId, short status) throws Exception {
		
		if(type == PendingPaymentType.PAYMENT_TYPE_FUEL_ENTRIES) {
			fuelService.update_Vendor_ApprovalTO_Status_MULTIP_FUEL_ID(transactionIds, approvalId, status, companyId);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_SERVICE_ENTRIES) {
			serviceEntriesService.update_Vendor_ApprovalTO_Status_MULTIP_SERVICE_ID(transactionIds,approvalId, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_PURCHASE_ORDER) {
			purchaseOrdersService.update_Vendor_ApprovalTO_Status_MULTIP_PurchaseOrders(transactionIds,approvalId, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_TYRE_INVOICE) {
			inventoryTyreService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreInvoice_ID(transactionIds, approvalId, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_TYRE_RETREAD_INVOICE) {
			inventoryTyreService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreRetread_ID(transactionIds, approvalId, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_BATTERY_INVOICE) {
			batteryInvoiceService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryBatteryInvoice_ID(transactionIds, approvalId, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_PART_INVOICE) {
			partInvoiceService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryPartInvoice_ID(transactionIds, approvalId, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_CLOTH_INVOICE) {
			clothInventoryService.update_Vendor_ApprovalTO_Status_MULTIP_InventoryClothInvoice_ID(transactionIds, approvalId, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_UREA_INVOICE) {
			ureaInventoryService.update_Vendor_ApprovalTO_StatusUreaInvoice_ID(transactionIds, approvalId, status, companyId);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_LAUNDRY_INVOICE) {
			clothInventoryService.update_Vendor_ApprovalTO_Status_LaundryInvoice_ID(transactionIds, approvalId, status, companyId);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_FUEL_INVOICE) {
			fuelInvoiceService.updateApprovalIdToInvoice(transactionIds, approvalId, status, companyId);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_DEALER_SERVICE_ENTRIES) {
			dealerServiceEntriesService.updateApprovalIdToDealerServiceEntries(transactionIds, approvalId, status, companyId);
		}
	
	}
	
	@Override
	@Transactional
	public void updateApprovalPaymentDetailsToInvoice(short type, Long approvalId, short status, String paymentDate)
			throws Exception {
		
		if(type == PendingPaymentType.PAYMENT_TYPE_FUEL_ENTRIES) {
			fuelService.updateVendorPaymentDetails(approvalId, paymentDate, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_SERVICE_ENTRIES) {
			serviceEntriesService.updateVendorPaymentDetails(approvalId, paymentDate, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_PURCHASE_ORDER) {
			purchaseOrdersService.updateVendorPaymentDetails(approvalId, paymentDate, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_TYRE_INVOICE) {
			inventoryTyreService.updateVendorPaymentDetails(approvalId, paymentDate, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_TYRE_RETREAD_INVOICE) {
			inventoryTyreService.updateVendorPaymentDetailsForRt(approvalId, paymentDate, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_BATTERY_INVOICE) {
			batteryInvoiceService.updateVendorPaymentDetails(approvalId, paymentDate, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_PART_INVOICE) {
			partInvoiceService.updateVendorPaymentDetails(approvalId, paymentDate, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_CLOTH_INVOICE) {
			clothInventoryService.updateVendorPaymentDetails(approvalId, paymentDate, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_UREA_INVOICE) {
			ureaInventoryService.updateVendorPaymentDetails(approvalId, paymentDate, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_LAUNDRY_INVOICE) {
			clothInventoryService.updateVendorPaymentDetailsLI(approvalId, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_FUEL_INVOICE) {
			fuelInvoiceService.updateVendorPaymentDetails(approvalId, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_DEALER_SERVICE_ENTRIES) {
			dealerServiceEntriesService.updateVendorPaymentDetails(approvalId, status, paymentDate);
		}
	}
	
	@Override
	@Transactional
	public void cancelVendorApprovalInvoices(Long invoiceId, short type, Long approvalId, short status)
			throws Exception {
		
		if(type == PendingPaymentType.PAYMENT_TYPE_FUEL_ENTRIES) {
			fuelService.updateVendorPaymentCancelDetails(invoiceId, approvalId, null, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_SERVICE_ENTRIES) {
			serviceEntriesService.updateVendorPaymentCancelDetails(invoiceId, approvalId, null, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_PURCHASE_ORDER) {
			purchaseOrdersService.updateVendorPaymentCancelDetails(invoiceId, approvalId, null, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_TYRE_INVOICE) {
			inventoryTyreService.updateVendorPaymentCancelDetails(invoiceId, approvalId, null, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_TYRE_RETREAD_INVOICE) {
			inventoryTyreService.updateVendorPaymentCancelDetailsForRT(invoiceId, approvalId, null, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_BATTERY_INVOICE) {
			batteryInvoiceService.updateVendorPaymentCancelDetails(invoiceId, approvalId, null, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_PART_INVOICE) {
			partInvoiceService.updateVendorPaymentCancelDetails(invoiceId, approvalId, null, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_CLOTH_INVOICE) {
			clothInventoryService.updateVendorPaymentCancelDetails(invoiceId, approvalId, null, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_UREA_INVOICE) {
			ureaInventoryService.updateVendorPaymentCancelDetails(invoiceId, approvalId, null, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_LAUNDRY_INVOICE) {
			clothInventoryService.updateVendorPaymentCancelDetailsLI(invoiceId, approvalId, null, status);
		}else if(type == PendingPaymentType.PAYMENT_TYPE_FUEL_INVOICE) {
			fuelInvoiceService.updateVendorPaymentCancelDetails(invoiceId, approvalId, null, status);
		}
	}
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	@Override
	public ValueObject getApprovalListByStatus(ValueObject valueObject) throws Exception {
		CustomUserDetails				userDetails				= null;
		Page<VendorApproval> 			page					= null;
		List<VendorApprovalDto> 		pageList				= null;
		VendorApprovalDto 				pageListBL				= null;
		List<VendorApprovalDto> 		finalList				= null;
		HashMap<String, Object>			configuration 			= null;
		List<VendorSubApprovalDetails>  vendorSubApprovalDetails = null;
		try {

			finalList		= new ArrayList<>();
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG);
			page 			= getDeployment_Page_VendorApproval(valueObject.getShort("status"), valueObject.getInt("pageNumber"), userDetails.getCompany_id());
			
			if(page != null) {
				
				int current 	= page.getNumber() + 1;
				int begin 		= Math.max(1, current - 5);
				int end 		= Math.min(begin + 10, page.getTotalPages());
				pageList 		= findAll_VendorApproval(valueObject.getShort("status"), valueObject.getInt("pageNumber"), userDetails.getCompany_id());
				
				if(pageList != null && !pageList.isEmpty()) {
					
					for(VendorApprovalDto dto : pageList) {
						vendorSubApprovalDetails 	= vendorSubApprovalDetailsRepository.getVendorSubApprovalDetails(dto.getApprovalId(),userDetails.getCompany_id());
						pageListBL 					= approvalBL.prepareVendorApprovalList(dto, vendorSubApprovalDetails);
						finalList.add(pageListBL);
					}
					
					valueObject.put("configuration", configuration);
					valueObject.put("deploymentLog", page);
					valueObject.put("beginIndex", begin);
					valueObject.put("endIndex", end);
					valueObject.put("currentIndex", current);
					valueObject.put("approvalCount", page.getTotalElements());
					valueObject.put("approval", finalList);
				}
				
			}

			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
		@SuppressWarnings("unchecked")
		@Override
		public List<VendorSubApprovalDetailsDto> getVendorSubApprovalDetailsList(Long approvalId) throws Exception {
			Query query = entityManager.createQuery(
					"SELECT VA.subApprovalId, VA.approvalId, VA.subApprovalTotal, VA.subApprovalpaidAmount, VA.invoiceId, VA.approvalPlaceId,"
							+ " VA.transactionNumber, VA.expectedPaymentDate, VA.approvedPaymentStatusId, VA.invoiceNumber, VA.invoiceDate,"
							+ " VA.approvalPaymentTypeId, VA.vid, V.vehicle_registration "
							+ " FROM VendorSubApprovalDetails VA "
							+ " LEFT JOIN Vehicle V ON V.vid = VA.vid"
							+ " WHERE VA.approvalId = "+approvalId+" AND VA.markForDelete = 0");
	
			List<Object[]> results = null;
			try {
				results = query.getResultList();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			List<VendorSubApprovalDetailsDto> dtos = null;
			if (results != null && !results.isEmpty()) {
				dtos = new ArrayList<VendorSubApprovalDetailsDto>();
				VendorSubApprovalDetailsDto select;
				for (Object[] vehicle : results) {
					if (vehicle != null) {
						select = new VendorSubApprovalDetailsDto();
						
						select.setSubApprovalId((Long) vehicle[0]);
						select.setApprovalId((Long) vehicle[1]);
						select.setSubApprovalTotal((Double) vehicle[2]);
						select.setSubApprovalPaidAmount((Double) vehicle[3]);
						select.setInvoiceId((Long) vehicle[4]);
						select.setApprovalPlaceId((short) vehicle[5]);
						select.setTransactionNumber((String) vehicle[6]);
						if(vehicle[7] != null)
						select.setExpectedPaymentDateStr(dateFormat.format((Date)vehicle[7]));
						select.setApprovedPaymentStatusId((short) vehicle[8]);
						select.setInvoiceNumber((String) vehicle[9]);
						if(vehicle[10] != null)
							select.setInvoiceDateStr(dateFormat.format((Date)vehicle[10]));
						select.setApprovalPaymentTypeId((short) vehicle[11]);
						select.setVid((Integer) vehicle[12]);
						select.setVehicleNumber((String) vehicle[13]);
						select.setTransactionUrl(PendingPaymentType.getTransactionUrl(select.getInvoiceId(), select.getApprovalPlaceId()));
						select.setApprovedPaymentStatus(InventoryTyreInvoiceDto.getPaymentModeName(select.getApprovedPaymentStatusId()));
						
						dtos.add(select);
					}
				}
				
			}
			return dtos;
		}
		
		@Override
		@Transactional
		public ValueObject removeInvoiceFromApproval(ValueObject valueObject) throws Exception {
			CustomUserDetails		userDetails		= null;
			try {
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				
				VendorSubApprovalDetails	approvalDetails	=	vendorSubApprovalService.getVendorSubApprovalDetailsById(valueObject.getLong("subApprovalId",0));
				
				if(approvalDetails != null) {
					updateTransactionStatus(valueObject.getLong("invoiceId")+"", valueObject.getShort("txnType") , null , userDetails.getCompany_id(), FuelVendorPaymentMode.PAYMENT_MODE_NOT_PAID);
					pendingVendorPaymentService.updateVendorPaymentStatus(valueObject.getLong("invoiceId"), valueObject.getShort("txnType"), PendingPaymentType.PAYMENT_STATUS_PENDING);
					update_Remove_VendorApproval_Aoumt(valueObject.getLong("approvalId"), round(valueObject.getDouble("amount",0), 2));
					
					vendorSubApprovalService.deleteSubVendorApprovalById(valueObject.getLong("subApprovalId",0), userDetails.getCompany_id());
					
					valueObject.put("deleteSuccess", true);
				}else {
					valueObject.put("deleteFailed", false);
				}
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@SuppressWarnings("unchecked")
		@Override
		@Transactional
		public ValueObject approveVendorApprovalEntry(ValueObject valueObject) throws Exception {
			VendorApproval						approval				= null;
			CustomUserDetails					userDetails				= null;
			ArrayList<ValueObject> 				validateArrayObjColl 	= null;
			try {
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				approval	= getVendorApproval(valueObject.getLong("approvalId",0), userDetails.getCompany_id());
				if(approval != null) {
					updateVendorApprovedBY_andDate(approval.getApprovalId(), userDetails.getId(), new Date(), FuelVendorPaymentMode.PAYMENT_MODE_APPROVED);
					
					validateArrayObjColl	= (ArrayList<ValueObject>) valueObject.get("invoiceDetails");
					if(validateArrayObjColl != null && !validateArrayObjColl.isEmpty()) {
						for (ValueObject invoiceObj : validateArrayObjColl) {
							updateTransactionStatus(invoiceObj.getLong("invoiceId")+"", invoiceObj.getShort("txnType") , approval.getApprovalId() , userDetails.getCompany_id(), FuelVendorPaymentMode.PAYMENT_MODE_APPROVED);
							vendorSubApprovalService.updateSubApproved_Details(invoiceObj.getLong("invoiceId"), invoiceObj.getShort("paymentStatus"),approval.getApprovalId(),DateTimeUtility.getTimeStamp(dateFormatSQL.format(dateFormat.parse(invoiceObj.getString("expectedPaymentDate","0000-00-00"))),DateTimeUtility.YYYY_MM_DD),invoiceObj.getDouble("receivedAmount",0), userDetails.getCompany_id());
							if(invoiceObj.getShort("paymentStatus") == FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID)
								pendingVendorPaymentService.updateVendorPaymentStatusAndAmt(invoiceObj.getLong("invoiceId"), invoiceObj.getShort("txnType"),
										PendingPaymentType.PAYMENT_STATUS_PARTIAL, invoiceObj.getDouble("receivedAmount") );
							if(invoiceObj.getShort("paymentStatus") == FuelVendorPaymentMode.PAYMENT_MODE_PAID)
								pendingVendorPaymentService.updateVendorPaymentStatusAndAmt(invoiceObj.getLong("invoiceId"), invoiceObj.getShort("txnType"), 
										PendingPaymentType.PAYMENT_STATUS_PAID, invoiceObj.getDouble("receivedAmount") );
							if(invoiceObj.getShort("paymentStatus") == FuelVendorPaymentMode.PAYMENT_MODE_NEGOTIABLE_PAID)
								pendingVendorPaymentService.updateVendorPaymentStatusAndAmt(invoiceObj.getLong("invoiceId"), invoiceObj.getShort("txnType"),
										PendingPaymentType.PAYMENT_STATUS_NEGOTIATED, invoiceObj.getDouble("receivedAmount") );
						}
					}
					
					 if(valueObject.getDouble("totalApprovedAmount",0)  > 0) {
						 updateVendorApprovalPaidAmount(approval.getApprovalId(), valueObject.getDouble("totalApprovedAmount",0));
					 }
					 
					 valueObject.put("approveSuccess", true);
				}
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		@Transactional
		public ValueObject makeVendorApprovalPayment(ValueObject valueObject) throws Exception {
			VendorApproval					vendorApproval		= null;
			List<VendorSubApprovalDetails>	subApprovalList		= null;
			CustomUserDetails				userDetails			= null;
			Vendor							vendor				= null;
			try {
				
				userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				vendorApproval	= getVendorApproval(valueObject.getLong("approvalId",0), userDetails.getCompany_id());
				subApprovalList	= vendorSubApprovalService.getSubApprovalListByApprovalId(valueObject.getLong("approvalId",0), userDetails.getCompany_id());
				vendorApproval	= approvalBL.getVendorApprovalDTOForPayment(valueObject, vendorApproval);
				
				
				addVendorApproval(vendorApproval);
				
				if(subApprovalList != null && !subApprovalList.isEmpty()) {
					for (VendorSubApprovalDetails vendorSubApprovalDetails : subApprovalList) {
						
						updateTransactionStatus(vendorSubApprovalDetails.getInvoiceId()+"", vendorSubApprovalDetails.getApprovalPlaceId(), vendorApproval.getApprovalId(), userDetails.getCompany_id(), FuelVendorPaymentMode.PAYMENT_MODE_PAID);
						
						vendorSubApprovalService.updateSubApprovalPaymentStatus(vendorSubApprovalDetails.getSubApprovalId(),
								 vendorSubApprovalDetails.getApprovedPaymentStatusId(), vendorApproval.getApprovalStatusId());
						
						updateApprovalPaymentDetailsToInvoice(vendorSubApprovalDetails.getApprovalPlaceId(), 
								vendorApproval.getApprovalId(), FuelVendorPaymentMode.PAYMENT_MODE_PAID, 
								DateTimeUtility.getStringDateFromDate(vendorApproval.getApprovalDateofpayment(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
					}
				}
				if(valueObject.getBoolean("allowBankPaymentDetails",false) && (PaymentTypeConstant.getPaymentTypeIdList().contains(vendorApproval.getApprovalPaymentTypeId()) || vendorApproval.getApprovalPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH )){
					ValueObject bankPaymentValueObject=JsonConvertor.toValueObjectFormSimpleJsonString(valueObject.getString("bankPaymentDetails"));
						bankPaymentValueObject.put("bankPaymentTypeId",vendorApproval.getApprovalPaymentTypeId());
						bankPaymentValueObject.put("userId",userDetails.getId());
						bankPaymentValueObject.put("companyId",userDetails.getCompany_id());
						bankPaymentValueObject.put("moduleId",valueObject.getLong("approvalId",0));
						bankPaymentValueObject.put("moduleNo", vendorApproval.getApprovalNumber());
						bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.APPROVAL_ENTRIES);
						bankPaymentValueObject.put("amount",vendorApproval.getApprovalPaidTotal() );
						bankPaymentValueObject.put("paidDate", vendorApproval.getApprovalDateofpayment());
						
						vendor	= vendorService.getVendor(vendorApproval.getApprovalvendorID());
						
						bankPaymentValueObject.put("remark", "Vendor Approval Payment Done VA-"+vendorApproval.getApprovalNumber() + 
													" Vendor : "+vendor.getVendorName()+" Payment Type : "+PaymentTypeConstant.getPaymentTypeName(vendorApproval.getApprovalPaymentTypeId()));
						if(vendorApproval.getApprovalPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)
							cashPaymentService.saveCashPaymentSatement(bankPaymentValueObject);
						else
							bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
					}
				
				
				return valueObject;
			} catch (Exception e) {
				throw e;
			}
		}
		
		@Override
		public List<VendorApprovalDto> findAllVendorApprovalsByStatusId(short status, String queryStr) throws Exception {
			HashMap<Long, VendorApprovalDto>  tempHM	= null;
			VendorApprovalDto				  tempDto	= null;
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT VA.approvalId, VA.approvalNumber, VA.approvalvendorID, V.vendorName, VT.vendor_TypeName, V.vendorLocation, VA.approvalStatusId,"
							+ " VA.approvalPlaceId, VA.approvalTotal, VA.approvalCreateDate, U.email, VA.created, VA.lastupdated,"
							+ " VSD.subApprovalpaidAmount "
							+ " FROM VendorApproval VA "
							+ " INNER JOIN VendorSubApprovalDetails VSD ON VSD.approvalId = VA.approvalId"
							+ " LEFT JOIN Vendor V ON V.vendorId = VA.approvalvendorID"
							+ " LEFT JOIN  VendorType VT ON VT.vendor_Typeid = V.vendorTypeId"
							+ " LEFT JOIN User U ON U.id = VA.approvalCreateById"
					+ " where (VA.approvalStatusId IN ("+ status +","+FuelVendorPaymentMode.PAYMENT_MODE_PARTIALLY_PAID+") ) "+queryStr+" AND VA.markForDelete = 0 "
					+ " ORDER BY VA.approvalId desc",
					Object[].class);
			
			List<Object[]> results = query.getResultList();
			
			List<VendorApprovalDto> dtos = null;
			if (results != null && !results.isEmpty()) {
				dtos = new ArrayList<VendorApprovalDto>();
				tempHM	= new HashMap<Long, VendorApprovalDto>();
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
					vendorApproval.setApprovalPlaceId((short) result[7]);
					vendorApproval.setApprovalTotal((Double) result[8]);
					vendorApproval.setApprovalCreateDate((Date) result[9]);
					vendorApproval.setApprovalCreateBy((String) result[10]);
					vendorApproval.setCreatedOn((Date) result[11]);
					vendorApproval.setLastupdatedOn((Date) result[12]);
					vendorApproval.setSubApprovalpaidAmount((Double) result[13]);
					
					vendorApproval.setApprovalCreateDateStr(viewFormat.format(vendorApproval.getApprovalCreateDate()));
					vendorApproval.setApprovalStatus(FuelVendorPaymentMode.getPaymentMode(vendorApproval.getApprovalStatusId()));
					
					tempDto = tempHM.get(vendorApproval.getApprovalId());
					if(tempDto != null) {
						vendorApproval.setSubApprovalpaidAmount(vendorApproval.getSubApprovalpaidAmount() + tempDto.getSubApprovalpaidAmount());
					}
					
					tempHM.put(vendorApproval.getApprovalId(), vendorApproval);
					
				}
				dtos = new ArrayList<VendorApprovalDto>(tempHM.values());
			}
			
			
			return dtos;
		}
}
