package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.PurchaseOrdersDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToDebitNoteDto;
import org.fleetopgroup.persistence.dto.PurchaseOrdersToPartsDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.PurchaseOrderToBattery;
import org.fleetopgroup.persistence.model.PurchaseOrders;
import org.fleetopgroup.persistence.model.PurchaseOrdersDocument;
import org.fleetopgroup.persistence.model.PurchaseOrdersToDebitNote;
import org.fleetopgroup.persistence.model.PurchaseOrdersToParts;
import org.fleetopgroup.persistence.model.PurchaseOrdersToTyre;
import org.fleetopgroup.persistence.model.VendorSubApprovalDetails;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface IPurchaseOrdersService {

	public void addPurchaseOrders(PurchaseOrders purchaseOrders) throws Exception;

	public void addPurchaseOrdersTask(PurchaseOrdersToParts purchaseOrdersToParts) throws Exception;

	/** This save Purchase Order Tyre Serial Number Table */
	public void addPurchaseOrders_Tyre_Serial_Number(PurchaseOrdersToTyre Tyre_Serial_Number) throws Exception;
	
	public void addPurchaseOrders_Battery(PurchaseOrderToBattery purchaseOrderToBattery) throws Exception;

	/* purchase order show page to add purchase orders */
	public PurchaseOrders getPurchaseOrders(long purchaseOrders);

	public PurchaseOrdersDto getPurchaseOrders(long purchaseOrders, CustomUserDetails userDetails) throws Exception;

	public PurchaseOrdersDto getPurchaseOrdersByNumber(long purchaseOrders, CustomUserDetails userDetails) throws Exception;

	public PurchaseOrdersToParts getPurchaseOrdersToParts(long purchaseOrderstask_ID);

	public List<PurchaseOrdersToParts> getPurchaseOrdersTasksToParts(long purchaseOrders_id);

	public List<PurchaseOrdersToPartsDto> getPurchaseOrdersTasksToParts(long purchaseOrders_id, Integer companyId)throws Exception;
	
	public List<PurchaseOrdersToPartsDto> getPurchaseOrdersTasksToCloth(long purchaseOrders_id, Integer companyId)throws Exception;
	
	public List<PurchaseOrdersToPartsDto> getPurchaseOrdersToPartsforBattery(long purchaseOrders_id, Integer companyId)
			throws Exception;

	/** This find Purchase Order Tyre to All Purchase Table */
	public List<PurchaseOrdersToTyre> getPurchaseOrdersTasksToTyre(long purchaseOrders_id, Integer companyId);
	
	public List<PurchaseOrderToBattery> getPurchaseOrdersTasksToBattery(long purchaseOrders_id, Integer companyId);

	/** This Page get PurchaseOrders table to get pagination values */
	public Page<PurchaseOrders> getDeployment_Page_PurchaseOrders(short status, Integer pageNumber, Integer companyId);

	/** This Page get PurchaseOrders table to get pagination values */
	// show REQUISITION List in main page
	public List<PurchaseOrdersDto> listOpenPurchaseOrders(short status, Integer pageNumber, Integer companyId);

	// show Report search List
	public List<PurchaseOrdersDto> Report_PurchaseOrders(String Query, String rangeFrom, String RangeTo,
			Integer companyId);

	public List<PurchaseOrders> listPurchaseOrders_InvoiceDate_vendor_APPROVALLISTFilter(String Query, String rangeFrom,
			String RangeTo);

	// Vendor Approval to Update the status and Approval id also
	public void update_Vendor_ApprovalTO_Status_MULTIP_PurchaseOrders(String ApprovalPurchase_ID, Long Approval_ID,
			short approval_Status) throws Exception;
	
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception ;

	// update Approval Paid mode in Multiple Service ID
	public void update_Vendor_ApprovalTO_Status_PayDate_Multiple_PurchaseOrders_ID(String ApprovalService_ID,
			java.util.Date paymentDate, Long Approval_ID, short approval_Status, Integer companyId,double paidAmount, double discountAmount) throws Exception;

	// Get Vendor Approval Amount Details
	public List<PurchaseOrders> get_Amount_VendorApproval_IN_PurchaseOrders(Long VendorApproval_Id) throws Exception;

	// Get Vendor Approval Amount Details
	public List<PurchaseOrdersDto> getVendorApproval_IN_PurchaseOrders(Long VendorApproval_Id, Integer companyId)
			throws Exception;

	/* save purchasestasktoparts */
	public PurchaseOrdersToParts addPurchaseOrdersToParts(PurchaseOrdersToParts purchaseOrdersTask) throws Exception;

	// Update Task TotalCost
	public List<PurchaseOrdersToParts> getPurchaseOrdersTasksIDToPartsList(long purchaseOrders_id);

	public void updatePurchaseOrderMainTotalCost(Long purchaseOrder_ID) throws Exception;

	// Update Orderd By details
	public void updatePurchaseOrderedBYAdavanceCost(PurchaseOrders Update_OrderedBy) throws Exception;

	// Update PurchaseOrderd Parts and Received_Quantity and
	// NOT_Received_Quantity
	public void updatePurchaseOrderPart_ReceivedQuantity(Double TotalPurchaseOrderPart_ReceivedQty,
			String Received_Qty_Remark, Double TotalPurchaseOrderPart_NOT_ReceivedQty, Long TotalPurchaseOrderPart_Id)
			throws Exception;

	// Update Received By details
	public void updatePurchaseOrderedToReceived_Quantity(PurchaseOrders Update_OrderedBy) throws Exception;

	// Update Freight
	public void updatePurchaseOrder_Freight(PurchaseOrders Update_OrderedBy) throws Exception;

	// Update TaxCost
	public void updatePurchaseOrder_TaxCost(PurchaseOrders Update_OrderedBy) throws Exception;

	/* save purchasestasktoparts To PurchaseOrdersToDebitNote */
	public void addPurchaseOrdersToDebitNote(PurchaseOrdersToDebitNote purchaseOrdersToDebitNote) throws Exception;

	// Get List to DebitNote
	public List<PurchaseOrdersToDebitNote> getPurchaseOrdersTasksToDebitNote(long purchaseOrders_id);

	public List<PurchaseOrdersToDebitNoteDto> getPurchaseOrdersTasksToDebitNote(long purchaseOrders_id,
			Integer companyId) throws Exception;
	
	public List<PurchaseOrdersToDebitNoteDto> getPurchaseOrdersTasksToDebitNoteForCloth(long purchaseOrders_id,
			Integer companyId) throws Exception;

	// update debit_note_cost to PurchaseOrder Table
	public void updatePurchaseOrder_Total_DebitCost(Double TotalpurchaseOrder_Debitcost, Long purchaseOrder_ID)
			throws Exception;

	// count purchaseOrder
	public Long countPurchaseOrder(Integer companyId) throws Exception;

	public void deletePurchaseOrdersTOParts(Long purchaseOrdersTask_id, Integer companyId);

	// upload Document save
	public void uploadPurchaseOrdersDocument(org.fleetopgroup.persistence.document.PurchaseOrdersDocument purchaseOrdersdocument) throws Exception;

	// get Document
	public PurchaseOrdersDocument getPurchaseOrdersDocument(Long purchaseOrders_id) throws Exception;

	public org.fleetopgroup.persistence.document.PurchaseOrdersDocument getPurchaseOrdersDocumentDetails(Long purchaseorder_documentid) throws Exception;

	public org.fleetopgroup.persistence.document.PurchaseOrdersDocument ValidatePurchaseOrdersDocument(Long purchaseOrders_id, String purchaseorder_document)
			throws Exception;

	// upload to Old Document Update
	public void updateOldPurchaseOrdersDocument(org.fleetopgroup.persistence.document.PurchaseOrdersDocument purchaseOrders_id) throws Exception;

	// Update Complete
	public void updatePurchaseOrder_Complete(PurchaseOrders Update_Complete) throws Exception;

	// Update ReOpen
	public void updatePurchaseOrder_ReOpen(PurchaseOrders Update_Complete) throws Exception;

	// Search Purchase Orders
	public List<PurchaseOrdersDto> SearchPurchaseOrders(String purchaseOrders_Search, CustomUserDetails userDetails)
			throws Exception;

	// count Statues
	public Long countPurchaseOrderStatues(String Statues) throws Exception;

	// Delete PurchaseOrder
	public void deletePurchaseOrders(Long purchaseOrders_id, Integer companyId ,Long userId);

	/**
	 * @param purchaseorder_invoiceno
	 * @return
	 */
	public List<PurchaseOrders> Validate_PurchaseOrder_Received_Invoice(Integer VendorID,
			String purchaseorder_invoiceno);

	/**
	 * @param purchaseorder_created_on
	 * @param purchaseorder_requied_on
	 * @param purchaseorder_buyeraddress
	 * @param purchaseorder_terms
	 * @param purchaseorder_shipvia
	 * @param purchaseorder_quotenumber
	 * @param purchaseorder_indentno
	 * @param purchaseorder_workordernumber
	 * @param purchaseorder_notes
	 * @param purchaseorder_id
	 * @param subCompanyId 
	 */
	public void UpdateEdit_PurchaseOrders_details(java.util.Date purchaseorder_created_on,
			java.util.Date purchaseorder_requied_on, short purchaseorder_terms,
			short purchaseorder_shipvia, String purchaseorder_quotenumber, String purchaseorder_indentno,
			String purchaseorder_workordernumber, String purchaseorder_notes, Long lastModifiedBy,
			Date Lastupdated_date, Long purchaseorder_id,short venodrPaymodId, Integer companyId, Long subCompanyId, Integer purchaseorder_vendor_id, Integer purchaseorder_shiplocation_id);

	/* purchase order show page to add purchase orders */
	public PurchaseOrders get_ApprovalID_AMOUNT_PurchaseOrders(long purchaseOrders);

	// Get List to DebitNote Id purchaseorderto_debitnoteid
	public PurchaseOrdersToDebitNote Get_PurchaseOrdersTasks_ToDebitNoteID(Long purchaseorderto_debitnoteid,
			Integer companyid);

	// Update Delete Return Parts Add Amount IN Balance And Subtrack Amount in
	// DebitNot
	public void Update_PurchaseOrder_Delete_DebitNote_Amount(PurchaseOrders Update_Complete) throws Exception;

	// Delete PurchaseOrder DebitNote Id purchaseorderto_debitnoteid
	public void Delete_PurchaseOrders_ToDebitNoteID(Long purchaseorderto_debitnoteid, Integer companyId);

	/**
	 * @param b
	 * @param purchaseorder_id
	 */
	public void Update_PurchaseOrders_To_Document_True(Long purchaseorder_documentid, boolean purchaseOrder_document,
			Long purchaseorder_id, Integer companyid);

	/**
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	public List<PurchaseOrdersToPartsDto> Part_consuming_Report(String dateRangeFrom, String dateRangeTo,
			Integer companyId) throws Exception;

	public void updatePurchaseOrder_RE_Enter(PurchaseOrders purchase_complete) throws Exception;

	public HashMap<String, Object> getPurchaseOrderDetailsHM(Long purchaseOrder_id, CustomUserDetails userDetails) throws Exception;

	public HashMap<String, Object> getPurchaseOrderDetailsHMByNumber(Long purchaseOrder_id, CustomUserDetails userDetails)
			throws Exception;

	public List<PurchaseOrdersToPartsDto> ReportPart_PurchaseOrdersPartsToPurchaseOrdersId(short PartType, String Query,
			String dateRangeFrom, String dateRangeTo, Integer companyId);
	
	public List<PurchaseOrdersToPartsDto> ReportTyre_PurchaseOrdersPartsToPurchaseOrdersId(short PartType, String Query,
			String dateRangeFrom, String dateRangeTo, Integer companyId);

	public void transferPurchaseOrderDocument(List<PurchaseOrdersDocument> purchaseOrdersDocumentList) throws Exception;
	
	public long getPurchaseOrdersDocumentMaxId() throws Exception;

	public void update_PurchaseOrderApprovalAmount(Object object, Object object3, Object object4,Object object5,
			short approvalPaymentTypeId, long approvalId,java.util.Date date)throws Exception;
	
	public PurchaseOrders getPurchaseOrderDeatils(Long purchaseOrderNumber, Integer companyId) throws Exception;

	public void updatePaymentApprovedPurchaseOrderDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId) throws Exception;// Approval Approved Here

	public void updatePaymentPaidPurchaseOrderDetails(VendorSubApprovalDetails vendorSubApproval, Timestamp approval_date, Integer companyId, short purchaseorder_vendor_paymodeId)throws Exception;// Approval Paid Here

	public ValueObject getPurchaseOrdersPartDetails(ValueObject valueObjcet)throws Exception;

	public ValueObject savePurchaseOrderPart(ValueObject valueInObject)throws Exception;

	public ValueObject deletePurchaseOrderPart(ValueObject valueInObject)throws Exception;

	public ValueObject sentToPurchaseOrder(ValueObject valueInObject)throws Exception;

	public ValueObject receivedQuantityToPurchaseOrder(ValueObject valueInObject)throws Exception;

	public ValueObject reEnterPurchaseOrder(ValueObject valueInObject)throws Exception;

	public ValueObject receivedPartFromPurchaseOrder(ValueObject valueInObject)throws Exception;

	public ValueObject completePurchaseOrder(ValueObject valueInObject)throws Exception;

	public ValueObject getPurchaseOrdersDebitNoteDetails(ValueObject valueInObject)throws Exception;

	public ValueObject deleteReturnPurchaseOrdersDetails(ValueObject valueInObject)throws Exception;
	
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidPOList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId);

	public ValueObject updateVendorDetailsInPurchaseOrder(ValueObject valueInObject)throws Exception;
	
	public ValueObject updateTallyCompaanyDetailsInPurchaseOrder(ValueObject valueInObject) throws Exception;
	
	public ValueObject updatePartPOStatusFromReceivedToOrdered(ValueObject object) throws Exception;
	
	public ValueObject updateBatteryPOStatusFromReceivedToOrdered(ValueObject object) throws Exception;
	
	public ValueObject updateTyrePOStatusFromReceivedToOrdered(ValueObject object) throws Exception;
	
	public ValueObject updateUpholsteryPOStatusFromReceivedToOrdered(ValueObject object) throws Exception;
	
	public ValueObject updateUreaPOStatusFromReceivedToOrdered(ValueObject object) throws Exception;
	
	public List<TripSheetExpenseDto> getPurchaseListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;

	public List<TripSheetExpenseDto> getPurchaseListForTallyImportATC(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;

	public ValueObject updatePurchaseOrderPartDetails(ValueObject object)throws Exception;

	public void updatePurchaseOrderVariousTotalCost(ValueObject object)throws Exception;

	public ValueObject getTotalAmountOfPurchaseOrders(PurchaseOrdersDto purchase)throws Exception;
	
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception;

	public  ValueObject createPurchaseOrderPartApproval(ValueObject object)throws Exception;

	public List<PurchaseOrdersToParts> getPurchaseOrdersApprovedTasksToParts(Long purchaseorder_id)throws Exception;

	public void deletePurchaseOrdersParts(Long purchaseorder_id, Integer company_id)throws Exception;
	
	public List<PurchaseOrdersToPartsDto> getLeatestPartFromPO(long partid,short partType) throws Exception;
	
	public PurchaseOrdersToPartsDto getLeatestTyreFromPO(long tyreSizeId,long tyreManufacturerId ,long tyreModelId) throws Exception;
	
	public PurchaseOrdersToPartsDto getLeatestBatteryFromPO(long batteryCapacity,long manufacturer ,long batterrymodel) throws Exception;
	
	public PurchaseOrdersToPartsDto getLastUreaDetailsFromPO(long manufacturer) throws Exception;
	
	public Object[] getUserActivityCount(ValueObject object) throws Exception;
	
	public List<PurchaseOrdersDto> getUserActivityData(String Query, String innerJoin,
			Integer companyId);
	
	public PurchaseOrdersToParts preparePurchaseOrdersTaskToPart(String partId,String quantityStr,String eachCostStr,String discountStr,String taxStr);
	
	public void deleteDebitNoteByPOId(Long purchaseorder_id, Integer companyId) ;
	
	public void deleteDebitNoteByPOIdByPartId(Long purchaseorderto_debitnoteid, Integer companyId);
	
	public ValueObject approvalPO(ValueObject	valueObject) throws Exception;
	
	public void updatePOApprovalStatus(short status, Long purchaserOrderId) throws Exception;
	
	ValueObject getPurchaseOrderStatusWiseReport(ValueObject valueObject) throws Exception;

	public List<PurchaseOrdersDto> listOpenPurchaseOrdersByApprovalStatus(short purchaseorderStatusOrdered,
			Integer pageNumber, Integer company_id);

	public Page<PurchaseOrders> getDeployment_Page_PurchaseOrdersApproval(short purchaseStatusApproved,
			Integer pageNumber, Integer company_id);

	ValueObject SavePoRemark(ValueObject valueObject) throws Exception;

	ValueObject getPoRemark(ValueObject valueObject) throws Exception;
	
	public PurchaseOrders getPurchaseOrderId(Long purchaseorder_Number, Integer companyId) throws Exception;
	
	public Object[] countTotalPurchaseOrder(CustomUserDetails userDetails) throws Exception;
	
}
