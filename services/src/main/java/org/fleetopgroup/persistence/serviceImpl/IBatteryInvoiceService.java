package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.BatteryInvoiceDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface IBatteryInvoiceService {

	List<BatteryInvoiceDto> getBatteryInvoiceDetails(BatteryInvoiceDto	batteryInvoiceDto) throws Exception;
	
	BatteryInvoiceDto getBatteryInvoice(BatteryInvoiceDto	batteryInvoiceDto) throws Exception;
	
	public void delete(Long id, Timestamp lastupdate, Long batteryInvoiceID) throws Exception;
	
	ValueObject	getBatteryInvoiceList(ValueObject valueObject) throws Exception; 
	
	public Page<BatteryInvoice> getDeployment_Page_VendorPayment(Integer pageNumber, Integer companyId) throws Exception;
	
	public List<BatteryInvoiceDto> getBatteryInvoiceList(Integer pageNumber, Integer companyId) throws Exception;
	
	public void updateBatteryInvoice(BatteryInvoiceDto	batteryInvoiceDto) throws Exception;
	
	ValueObject	searchBatteryInvoice(ValueObject valueObject) throws Exception;
	
	public Page<BatteryInvoice> getDeployment_Page_BatteryInvoice(String term , Integer pageNumber, Integer companyId) throws Exception;
	
	public List<BatteryInvoiceDto> getBatteryInvoiceList(String term ,Integer pageNumber, Integer companyId) throws Exception;
	
	public void registerInvoice(BatteryInvoice batteryInvoice) throws Exception;

	/*
	 * get Filtered Vendor Credit Battery Inventory Details
	 */
	List<BatteryInvoiceDto> FilterVendorCreditListInventoryBatteryInvoice(Integer vendorId, String dateRangeFrom, String dateRangeTo, Integer company_id) throws Exception;

	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception ;
	
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception;
	
	/*
	 * Update Vendor Approvals for Multiple Battery Invoice
	 */
	void update_Vendor_ApprovalTO_Status_MULTIP_InventoryBatteryInvoice_ID(String mULT_BI_id, Long approvalId, short vendorPaymodeStatusApproved) throws Exception;

	List<BatteryInvoiceDto> get_Amount_VendorApproval_IN_InventoryBatteryInvoice(Long approvalId, Integer company_id) throws Exception;

	List<BatteryInvoiceDto> getVendorApproval_IN_InventorybatteryInvoice_List(Long approvalId, Integer company_id) throws Exception;

	void update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryBatteryInvoice_ID(String mULT_BI_id, Long approvalId, short vendorPaymodeStatusPaid, Timestamp topaymodeDate, Integer company_id,double paidAmount,double discountAmount) throws Exception;
	
	public long getbatteryCountByStatus(Integer companyId, short status)throws Exception;

	public void updatePaymentApprovedBatteryDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId)throws Exception;// Approval Approved 

	public void updatePaymentPaidBatteryDetails(String ApprovalInvoice_ID, Timestamp vendorPaymentDate, Integer companyId, short vendorPaymentStatus)throws Exception;// Approval Paid
	
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidBatteryInvoiceList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception;

	public List<TripSheetExpenseDto> getBatteryInvoiceListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;

	public BatteryInvoice getBatteryInvoiceByBatteryInvoiceId(Long batteryInvoiceId, Integer companyId)throws Exception;

	public void updateSublocationInBattery(Long batteryInvoiceId, Integer subLocationId, Integer company_id)throws Exception;

	public BatteryInvoice getBatteryInvoiceByInvoiceNumber(String string, Integer company_id)throws Exception;
	

	public List<TripSheetExpenseDto> getBatteryInvoiceListForTallyImportATC(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;
	
	
}
