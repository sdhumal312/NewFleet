package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.VendorApprovalDto;
import org.fleetopgroup.persistence.dto.VendorSubApprovalDetailsDto;
import org.fleetopgroup.persistence.model.VendorApproval;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;


public interface IVendorApprovalService {

	public void addVendorApproval(VendorApproval VendorApproval) throws Exception;

	public void updateVendorApproval(VendorApproval VendorApproval) throws Exception;

	public void updateVendorApproval_Aoumt(Long VendorApproval, Double ApprovalAmount) throws Exception;

	public void update_Remove_VendorApproval_Aoumt(Long VendorApproval, Double ApprovalAmount) throws Exception;

	public void updateVendorApprovedBY_andDate(Long VendorApproval, Long ApprovedBY, Date approvalCreateDate,
			short ApprovalStatus) throws Exception;

	/*public void updateApprovedPayment_Details(Long VendorApproval, String PaymentType, String PaymentNumber,
			Date approvalCreateDate, String PaymentPaidBy, String ApprovedStutes, String lastUpdateBy,
			Date lastUpdatedDate) throws Exception;
	*/
	public void updateApprovedPayment_Details(Long VendorApproval, short PaymentType, String PaymentNumber,
			Date approvalCreateDate, Long PaymentPaidBy, short ApprovedStutes,Long lastUpdateBy,
			Date lastUpdatedDate, double totalApprovalAmount) throws Exception;

	public VendorApproval getVendorApproval(Long approvalId, Integer companyId) throws Exception;
	
	public VendorApprovalDto getVendorApprovalDetails(Long VendorApproval_id, Integer companyId) throws Exception;

	public List<VendorApproval> findAllVendorApprovalList(Integer companyId) throws Exception;
	
	/** This Page get VendorApproval table to get pagination values */
	public Page<VendorApproval> getDeployment_Page_VendorApproval(short status, Integer pageNumber, Integer companyId)throws Exception;

	/** This Page get VendorApproval table to get pagination values */
	public List<VendorApprovalDto> findAll_VendorApproval(short status, Integer pageNumber, Integer companyId) throws Exception;

	//public List<VendorApprovalDto> findAll_VendorApprovalPaymentEntries(short status, Integer pageNumber, Integer companyId, short approvalStatusPaid,short typeOfPaymentId ) throws Exception;
	
	/** This Page get VendorApproval table to get pagination values */
	public List<VendorApproval> findAllVendorApproval(String status) throws Exception;

	public List<VendorApproval> listVendorApproval() throws Exception;

	public void deleteVendorApproval(Long Approval_ID) throws Exception;

	public Long countVendorApproval() throws Exception;

	public Long countVendorApprovedToday(Date Today) throws Exception;

	//public Long countVendorApproved(String approvalStatus) throws Exception;

	public List<VendorApproval> listVendorApproval(String qurey) throws Exception;

	// Approval payment
	//public List<VendorApproval> findAllApproval_PaymentList(String Approval_Stutes) throws Exception;

	public void deleteVendorApproval(VendorApproval VendorApproval) throws Exception;

	//search Approval
	public List<VendorApprovalDto> SearchVendorApproval(String Search, Integer companyId) throws Exception;

	public List<VendorApprovalDto> findAll_VendorPaidApproval(short paymentModePaid, short paymentModePartiallyPaid,
			short paymentModeNegotiablePaid, Integer pageNumber, Integer company_id)throws Exception;
	
	/*public void updateApprovedPayment_DetailsInApprovedList(Long VendorApproval, short PaymentType, String PaymentNumber,
			Date approvalCreateDate, Long PaymentPaidBy, short ApprovedStutes, Long lastUpdateBy, Date lastUpdatedDate,
			short TypeOfPaymentId, Long PaidAmount, Long BalanceAmount) throws Exception;*/
	public void updateTallyCompany(Long tallyCompanyId, Long approvalId) throws Exception;
	
	public List<TripSheetExpenseDto> findVendorPaymentList(String fromDate, String toDate, Integer companyId, String tallyCompany) throws Exception;
	
	public List<TripSheetExpenseDto> findVendorPaymentListATC(String fromDate, String toDate, Integer companyId, String tallyCompany) throws Exception;
	
	public void updateVendorApprovalPaidAmount(Long approvalId, Double amount) throws Exception;

	public ValueObject createVendorApproval(ValueObject		valueObject) throws Exception;
	
	public ValueObject getApprovalListByStatus(ValueObject		valueObject) throws Exception;
	
	public List<VendorSubApprovalDetailsDto>  getVendorSubApprovalDetailsList(Long approvalId) throws Exception;
	
	public ValueObject makeVendorApprovalPayment(ValueObject	valueObject) throws Exception;
	
	public ValueObject removeInvoiceFromApproval(ValueObject		valueObject) throws Exception;
	
	public ValueObject approveVendorApprovalEntry(ValueObject		valueObject) throws Exception;
	
	public void updateTransactionStatus(String transactionIds, short type, Long approvalId, Integer companyId, short status) throws Exception ;
	
	public void updateApprovalPaymentDetailsToInvoice(short type, Long approvalId , short status, String	paymentDate) throws Exception ;
	
	public void updateTransactionStatus(String transactionIds, short type, Long approvalId, Integer companyId) throws Exception;
	
	public void cancelVendorApprovalInvoices(Long invoiceId, short type, Long approvalId, short status) throws Exception;
	
	public List<VendorApprovalDto>  findAllVendorApprovalsByStatusId(short status, String query) throws Exception;
}
