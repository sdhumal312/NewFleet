package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.PartInvoiceDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.PartInvoice;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IPartInvoiceService {

	public void saveInvoice(PartInvoice partInvoice) throws Exception;

	public List<PartInvoiceDto> list_Of_All_PartInvoice(Integer pageNumber, Integer company_id) throws Exception;

	public PartInvoiceDto details_of_PartInvoice(Long partInvoice_id, Integer company_id) throws Exception;

	public List<InventoryDto> details_of_Inventory(Long partInvoice_id, Integer company_id) throws Exception;

	public List<PartInvoiceDto> FilterVendorCreditListInventoryPartInvoice(Integer vendorId, String dateRangeFrom,
			String dateRangeTo, Integer company_id) throws Exception;

	public List<PartInvoiceDto> getVendorApproval_IN_InventoryPartInvoice_List(Long approvalId, Integer company_id)throws Exception;

	public void update_Vendor_ApprovalTO_Status_MULTIP_InventoryPartInvoice_ID(String mULT_PI_id, Long approvalId,
			short vendorPaymodeStatusApproved)throws Exception;
	
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception;
	
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception;

	public List<PartInvoiceDto> get_Amount_VendorApproval_IN_InventoryPartInvoice(Long approvalId, Integer company_id)throws Exception;

	public PartInvoiceDto getPartInvoice(PartInvoiceDto part) throws Exception;

	public void update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryPartInvoice_ID(String mULT_BI_id,
			Long approvalId, short vendorPaymodeStatusPaid, Timestamp topaymodeDate, Integer company_id, double paidAmount,double discountAmount)throws Exception;
	
	public PartInvoice getPartInvoiceDetails(Long partInvoiceId, Integer companyId) throws Exception; 
	
	public void delete_PartInvoice(Long partInvoiceId, Integer companyId) throws Exception;
	
	public void update_Quantity_Of_PartInvoice(Long partInvoiceId, Integer companyId, Double quantity, String invoiceAmount) throws Exception;
	
	public void update_QuantityAndInvoiceAmount_Of_PartInvoice(Long partInvoiceId, Integer companyId, Double quantity, String invoiceAmount)
	throws Exception;
	
	public Map<String, Object> deletePartInvoiceInventory(ValueObject valueObject) throws Exception;
	
	public Map<String, Object> deletePartInvoice(ValueObject valueObject) throws Exception;
	
	//public Map<String, Object> updatePartInventoryInvoice(ValueObject valueObject) throws Exception;

	public void update_PartInvoiceAmountOf_Inventory(Object partInvoiceId, Object recievedAmount, Object balanceAmt, Object discountAmt, short vendorPaymentStatusId, long approvalId,java.util.Date date) throws Exception;

	public void update_PartInvoiceAmountOf_BatteryInventory(Object object, Object object3,
			Object object4,Object object5, short approvalPaymentTypeId, long approvalId,java.util.Date date)throws Exception;

	public void update_TyreInvoiceAmountOf_TyreInventory(Object object, Object object3, Object object4, Object object5,
			short approvalPaymentTypeId, long approvalId,java.util.Date date)throws Exception;

	public void update_TyreRetreadInvoiceAmountOf_TyreRetreadInventory(Object object, Object object3,
			Object object4,Object object5, short approvalPaymentTypeId, long approvalId,java.util.Date date)throws Exception;

	public void update_ServiceEntriesApprovalAmount(Object object, Object object3, Object object4,Object object5,
			short approvalPaymentTypeId, long approvalId,java.util.Date date)throws Exception;

	public void update_FuelInventoryAmount(Object object, Object object3, Object object4,
			Object object5, short approvalPaymentTypeId,long approvalId,java.util.Date date)throws Exception;

	public Map<String, Object> updatePartInventoryInvoice(ValueObject valueObject, MultipartFile file) throws Exception;

	public void updatePaymentApprovedPartDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId)throws Exception;//Approval Approved Here

	public void updatePaymentPaidDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId, short vendorPaymentStatus)throws Exception;//Approval Paid Here
	
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidPartInvoiceList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception;
	
	public PartInvoice validatePartInvoiceToPO(Long purchaseOrderId, Integer companyId) throws Exception;
	
	public PartInvoiceDto getLimitedPartInvoice(Long partInvoiceId) throws Exception;
}