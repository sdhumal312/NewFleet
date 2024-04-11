/**
 * 
 */
package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreAmountDto;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreInvoiceDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadAmountDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadDto;
import org.fleetopgroup.persistence.dto.InventoryTyreTransferDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TyreSoldInvoiceDetailsDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.InventoryTyre;
import org.fleetopgroup.persistence.model.InventoryTyreAmount;
import org.fleetopgroup.persistence.model.InventoryTyreHistory;
import org.fleetopgroup.persistence.model.InventoryTyreInvoice;
import org.fleetopgroup.persistence.model.InventoryTyreLifeHistory;
import org.fleetopgroup.persistence.model.InventoryTyreRetread;
import org.fleetopgroup.persistence.model.InventoryTyreRetreadAmount;
import org.fleetopgroup.persistence.model.InventoryTyreRetreadDocument;
import org.fleetopgroup.persistence.model.InventoryTyreTransfer;
import org.fleetopgroup.persistence.model.TyreExpenseDetails;
import org.fleetopgroup.persistence.model.TyreSoldInvoiceDetails;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

/**
 * @author fleetop
 *
 */
public interface IInventoryTyreService {

	// This interface save tyre inventory Invoice values
	public void add_Tyre_Inventory_Invoice(InventoryTyreInvoice TyreInvoice) throws Exception;

	// This interface save tyre inventory Amount values
	public void add_Tyre_Inventory_Amount(InventoryTyreAmount TyreAmount) throws Exception;

	// This interface save Tyre service number and values
	public void add_Inventory_TYRE(InventoryTyre Tyre) throws Exception;
	
	public InventoryTyre save_Inventory_TYRE(InventoryTyre Tyre) throws Exception;

	public void save_Multiple_Inventory_TYRE(ArrayList<InventoryTyre>	inventoryTyreList) throws Exception;

	// This interface save Tyre History service number and values
	public void add_Inventory_TYRE_History(InventoryTyreHistory TyreHistory) throws Exception;

	// This interface update tyre inventory Invoice values
	public void update_Tyre_Inventory_Invoice(InventoryTyreInvoice TyreInvoice) throws Exception;

	// This interface update tyre inventory Amount values
	public void update_Tyre_Inventory_Amount(InventoryTyreAmount TyreAmount) throws Exception;

	// This interface update Tyre service number and values
	public void update_Inventory_TYRE(InventoryTyre Tyre) throws Exception;

	// This interface update Tyre History service number and values
	public void update_Inventory_TYRE_History(InventoryTyreHistory TyreHistory) throws Exception;

	// This Find last 10 Entries in InventoryTyreInvoice details
	public List<InventoryTyreInvoiceDto> find_list_InventoryTyreInvoice(Integer pageNumber, Integer companyId) throws Exception;

	public Page<InventoryTyreInvoice> getDeployment_Page_TyreInvoice(Integer pageNumber, Integer companyId)throws Exception;

	// This Find last 5 Entries in InventoryTyre details
	//public List<InventoryTyre> find_List_InventoryTyre() throws Exception;

	public Page<InventoryTyre> getDeploymentLog(Integer pageNumber, Integer	companyId) throws Exception;

	public List<InventoryTyreDto> find_List_InventoryTyre(Integer pageNumber, Integer	companyId) throws Exception;

	public Page<InventoryTyre> getDeploymentLog_Location(Integer Location, Integer pageNumber, Integer companyId) throws Exception;

	public List<InventoryTyreDto> find_List_InventoryTyre_Location(Integer Location, Integer pageNumber, Integer companyId) throws Exception;

	// This Find last 5 Entries in InventoryTyreHistory details
	public List<InventoryTyreHistoryDto> find_List_InventoryTyreHistory(Long ITYRE_ID, Integer companyId) throws Exception;

	// This Find ITYRE_ID in InventoryTyreInvoice details
	public InventoryTyreInvoice Get_list_InventoryTyreInvoice(Long ITYRE_ID, Integer companyId) throws Exception;
	
	public InventoryTyreInvoiceDto Get_list_InventoryTyreInvoiceDetails(Long ITYRE_ID, Integer companyId) throws Exception;

	// This Find ITYRE_ID Entries in InventoryTyreAmount details
	public List<InventoryTyreAmountDto> Get_List_InventoryTyreAmount(Long ITYRE_ID, Integer companyId) throws Exception;

	// This Find in InventoryTyre details
	public List<InventoryTyreDto> Get__List_InventoryTyre(Long ITYRE_ID, Integer companyId) throws Exception;

	// This Find ITYRE_ID Entries in InventoryTyreAmount details
	public InventoryTyreAmountDto Get_AMount_ID_InventoryTyreAmount(Long ITYRE_Aomunt_ID, Integer companyId) throws Exception;

	// This Find validate create or not InventoryTyre Tyre_SerivalNo details
	public InventoryTyre Validate_InventoryTyre(String Tyre_SerivalNo, Integer companyId) throws Exception;

	// This Update Save Tyre Inventory_Amount_Complete_Tyre_Number details
	public void Update_Inventory_Amount_Complete_Tyre_Number(Integer done_TyreSerialNo, Long Amount_ID, Integer companyId)
			throws Exception;

	/**
	 * This interface is Tyre Inventory count Total and Location Total inventory
	 */
	public Object[] countTotal_TyreInventrory_AND_Qty() throws Exception;

	/** Search Page Report */
	//public Page<InventoryTyre> Search_getDeploymentLog(Integer pageNumber, String Search);

	/** Search Page Report */
	//public List<InventoryTyre> Search_List_InventoryTyre(Integer pageNumber, String Search) throws Exception;

	/** Search InventoryTyre Page Report */
	public List<InventoryTyreDto> Search_List_InventoryTyre(String Search, Integer companyId) throws Exception;

	/** Search InventoryTyre Page Report */
	public List<InventoryTyreDto> Show_PurchaseOrderTyre_to_List_InventoryTyre(Integer Size, Integer companyId) throws Exception;

	/** Search InventoryTyreInvoice Page Report */
	public List<InventoryTyreInvoiceDto> Search_List_InventoryTyreInvoice(String Search, Integer companyId) throws Exception;

	// This Find ITYRE_ID Entries in InventoryTyre details
	public InventoryTyre Get_TYRE_ID_InventoryTyre(Long TYRE_ID) throws Exception;
	
	public InventoryTyreDto Get_TYRE_ID_InventoryTyre(Long TYRE_ID, Integer companyId) throws Exception;

	/** Delete Tyre Inventory Details */
	public void delete_Tyre_Inventory(Long Tyre_id, Integer companyId) throws Exception;

	/** Update Tyre Inventory Details */
	public void update_Tyre_Inventory(String TyreSerialNO, Long LASTUPDATEBY, Date LASTUPDATEDATE, Long Tyre_id, Integer companyid)
			throws Exception;

	/**
	 * Validate of inventory Amount table entries is there are not checking
	 * delete
	 */
	public List<InventoryTyre> Validate_Amount_InventoryTyre(Long ITYRE_Amount_ID, Integer companyId) throws Exception;

	/** Delete Tyre InventoryAmount Details */
	public void delete_Tyre_Inventory_Amount(Long ITYRE_Amount_ID, Integer companyId) throws Exception;

	/** Subtrack InventoryAmount to InventoryInvoce Amount */
	public void update_Subtrack_InventoryAmount_to_InvoiceAmount(Double Amount, Long Tyre_INVOICE_ID, Integer companyId) throws Exception;

	/** Delete Tyre InventoryInvoice Details */
	public void delete_Tyre_Inventory_Invoice(Long ITYRE_Invoice_ID, Integer companyId) throws Exception;

	/** Sum InventoryAmount to InventoryInvoce Amount */
	public void update_Add_InventoryAmount_to_tyreRemove(Integer Remove_TyreSerialNo, Long Tyre_Amount_ID, Integer companyId)
			throws Exception;

	/** Report Search InventoryTyre Page Report */
	public List<InventoryTyreDto> Report_List_InventoryTyre(String Query, Integer companyId) throws Exception;

	/** Report Search InventoryTyre Mount Tyre SIZE Page Report */
	public List<InventoryTyre> Tyre_Mount_InVehicle(String search, Integer size, Integer companyId) throws Exception;
	
	
	public List<InventoryTyre> Tyre_Mount_InVehicleAllSize(String search, Integer companyId) throws Exception;

	/**
	 * Update Assign this Tyre to Vehicle ID, Vehicle regi, OpenODometer to
	 * InventoryTyre
	 */
	public void update_Assign_TYRE_To_Vehicle_Mount(Integer VEHICLE_ID, 
			Integer OPEN_ODOMETER, short TYRE_ASSIGN_STATUS, Date TYRE_ASSIGN_DATE, Long TYRE_ID, Integer companyId,short dismountStatusId) throws Exception;

	/**
	 * Update Assign this Tyre to Vehicle ID, Vehicle regi, OpenODometer to
	 * InventoryTyre DisMount
	 */
	public void update_Assign_TYRE_To_Vehicle_DisMount(Integer VEHICLE_ID,
			Integer CLOSE_ODOMETER, Integer TYRE_USEAGE, short TYRE_ASSIGN_STATUS, Long TYRE_ID) throws Exception;

	/**
	 * Update Assign this Tyre to Vehicle ID, Vehicle regi, OpenODometer to
	 * InventoryTyre Rotation
	 */
	public void update_Assign_TYRE_To_Vehicle_Rotation(Integer TYRE_USEAGE, Long TYRE_ID) throws Exception;

	/** Filter in Retread Tyre InventoryTyre Page Report */
	public List<InventoryTyreDto> Filter_Retread_Tyre_Inventory(String Query, Integer companyid) throws Exception;

	/** Search InventoryTyre Page Report */
	public List<InventoryTyre> Search_Only_InventoryTyre_ID(String Search, Integer companyid) throws Exception;
	
	public List<InventoryTyre> Search_Only_AvailableTyre_ID(String Search, Integer companyid) throws Exception;
	
	public List<InventoryTyre> Search_Only_InventoryTyre_ID(String Search, Integer location, Integer companyid) throws Exception;

	// get inventory Validate in Database
	public List<InventoryTyreDto> Purchase_Order_Validate_InventoryTyre(Integer TYRE_SIZE_ID) throws Exception;

	// This interface Retread tyre inventory and values
	public void add_Inventory_Retread_Tyre(InventoryTyreRetread TyreRetread) throws Exception;

	// This interface Retread tyre inventory and values
	public void add_Inventory_Retread_Tyre_Amount(InventoryTyreRetreadAmount TyreRetreadAmount) throws Exception;

	// This Find ITYRE_ID Get_Only_TYRE_ID_And_TyreSize in InventoryTyre details
	public InventoryTyreDto Get_Only_TYRE_ID_And_TyreSize(Long TYRE_ID, Integer companyId) throws Exception;

	// This Update Save Tyre Retread Status in Inventory_Tyre multiple id
	// details
	public void Update_Inventory_Tyre_RetreadingStatus(Long TyreRetread_id, short Status, String Multiple_Tyre_ID, Integer companyId)
			throws Exception;

	/** This interface show InventoryTyreReread Page Report */
	public Page<InventoryTyreRetread> getDeploymentInventoryTyreRetreadLog(Integer pageNumber, Integer companyId) throws Exception;

	/** Search InventoryTyreReread Page Report */
	public List<InventoryTyreRetreadDto> find_List_InventoryTyreRetread(Integer pageNumber, Integer companyId) throws Exception;

	// This Find IR_ID Entries in InventoryTyreRetread details
	public InventoryTyreRetreadDto Get_InventoryTyreRetread(Long TRID, Integer companyId) throws Exception;

	// This Find IR_ID Entries in InventoryTyreRetread details
	public InventoryTyreRetread Get_ApprovalID_InventoryTyreRetread(Long TRID) throws Exception;

	// This Find IR_ID Entries in InventoryTyreRetread details
	public List<InventoryTyreRetreadAmountDto> Get_InventoryTyreRetread_Amount(Long TRID, Integer companyId) throws Exception;

	// This interface Retread tyre Amount inventory and values
	public void update_Inventory_Retread_Amount_Tyre_cost(InventoryTyreRetreadAmount RetreadAmount) throws Exception;

	// This interface Retread tyre Amount inventory and values
	public void update_Inventory_Retread_Tyre_cost(Double Cost, Integer TR_RECEIVE_TYRE, Long TRID, Integer companyId) throws Exception;

	// This interface Retread tyre Amount inventory and values
	public void update_Edit_Inventory_Retread_Tyre_cost_ADD(Double Cost, Long TRID, Integer companyId) throws Exception;

	// This interface Retread tyre Amount inventory and values
	public void update_Edit_Inventory_Retread_Tyre_cost_Subtract(Double Cost, Long TRID, Integer companyid) throws Exception;

	// This Find IR_Amount_ID Entries in InventoryTyre details
	public InventoryTyreRetreadAmount Get_TYRE_ID_AND_TOTAL_AMOUNT_InventoryTyreRetreadAmount(Long TYRE_AMOUNT_ID, Integer companyid)
			throws Exception;

	/** This interface Retread tyre Amount inventory and values */
	public void SUBTRACK_Inventory_Retread_Amount_Tyre_cost_DELETE(Double TotalCost, Integer TR_RECEIVE_TYRE, Long TRID, Integer companyid)
			throws Exception;

	/** Delete Tyre InventoryRetreadAmount Details */
	public void delete_Inventory_Retread_Amount_Tyre_cost(Long IR_Amount_ID, Integer companyId) throws Exception;

	/** Delete Tyre InventoryRetread Details */
	public void delete_Tyre_InventoryRetread(Long IRID, Integer companyId) throws Exception;

	/** Delete Inventory Retread to Remove Tyre Amount Update AVALABLE_Status */
	public void Update_Inventory_Tyre_AVALABLE_Status(short Status, Long Tyre_ID, Integer companyId) throws Exception;

	/** This Inventory Retread to Open To Sent-Retread Stutes Change Code */
	public void Update_Inventory_ReTread_Status_and_Description(short Status, String Description, Long LastupdateBy,
			Date Lastupdated, Long TyreRetread_id, Integer companyId)
			throws Exception;

	/**
	 * This Inventory Completed_ Retread to Open To Sent-Retread Stutes Change
	 * Code
	 */
	public void Update_Completed_Inventory_ReTread_Status_and_Description(short Status, String invoiceNumber,
			Date invoiceDate, String Description, Long LastupdateBy, Date Lastupdated, Long TyreRetread_id, Integer companyid)
			throws Exception;

	/**
	 * This Inventory Completed_ Retread to Open To Sent-Retread Stutes Change
	 * Code
	 */
	public void Update_Completed_Inventory_ReTread_Status_and_Description(short Status, String Description,
			Long TyreRetread_id, Integer companyId) throws Exception;

	/** This Inventory Retread to Open To Update_RECEVIED_Status Code */
	public void Update_RECEVIED_RetreadAmount_Status(short Status, Long TyreRetread_id, Integer companyId) throws Exception;

	/** This InventoryTYRE To Select Usage and amount and tyre retread_period */
	public InventoryTyre GET_RECEVIED_Retread_TYRE_Amount(Long Tyre_id, Integer companyId) throws Exception;

	// This interface save tyre inventory TyreLifeHistory values
	public void add_Tyre_Inventory_TyreLifeHistory(InventoryTyreLifeHistory TyreLifeHistory) throws Exception;

	/** Get Tyre Life Cycle Total Cost and usage and milage */
	public List<InventoryTyreLifeHistory> find_list_InventoryTyreLifeHistory(Long Tyre_ID, Integer companyId) throws Exception;

	/**
	 * This Update Retread Usage and amount and tyre retread_period To
	 * InventoryTYRE Id
	 */
	public void Update_REtread_Amount_Usage_RetreadPeriod_Status_InventoryTYRE(Integer RetreadUsage,
			Double RetreadAmount, Integer RetreadCount, short Status, Long Tyre_id, Integer companyId) throws Exception;

	// This Update Save Tyre Scrape Status in Inventory_Tyre multiple id//
	// details
	public void Update_Inventory_Tyre_ScropStatus(Long scropedby, Date scropedDate, short Status,
			String Multiple_Tyre_ID, Integer companyId) throws Exception;

	// This InventoryTyreRetreadDocument validate the Inventory Document
	public org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument Get_Validate_TYRE_Document(Long ITYRE_ID, Integer companyId) throws Exception;

	// This InventoryTyreRetreadDocument save tyre inventory Invoice values
	public void add_Tyre_InventoryTyreRetreadDocument(org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument TyreRetreadDocument)
			throws Exception;

	// This InventoryTyreRetreadDocument update tyre inventory Invoice values
	public void update_Tyre_InventoryTyreRetreadDocument(org.fleetopgroup.persistence.document.InventoryTyreRetreadDocument TyreRetreadDocument)
			throws Exception;

	/** Filter Vendor Credit Entries InventoryTyreInvoice Page Report */
	public List<InventoryTyreInvoiceDto> Filter_Vendor_creditList_InventoryTyreInvoice(Integer vendor_ID,
			String dateRangeFrom, String dateRangeTo, Integer companyId) throws Exception;

	/** Filter InventoryTyreRetread Report */
	public List<InventoryTyreRetreadDto> Filter_Vendor_creditList_InventoryTyreRetread(Integer vendor_ID,
			String dateRangeFrom, String dateRangeTo, Integer companyId) throws Exception;

	// Vendor Approval to Update the status and Approval id also
	public void update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreInvoice_ID(String ApprovalService_ID,
			Long Approval_ID, short approval_Status) throws Exception;
	
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception ;
	
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception ;

	// Vendor Approval to Update the status and Approval id also
	public void update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreInvoice_ID(String ApprovalService_ID,
			Long Approval_ID, short approval_Status, Date approval_date, Integer companyId,double paidAmount,double discountAmount) throws Exception;

	// Vendor Approval ID to Show InventoryTyreInvoice
	public List<InventoryTyreInvoiceDto> getVendorApproval_IN_InventoryTyreInvoice_List(Long VendorApproval_Id, Integer companyId)
			throws Exception;

	// Vendor Approval ID to Show InventoryTyreInvoice
	public List<InventoryTyreInvoiceDto> get_Amount_VendorApproval_IN_InventoryTyreInvoice(Long VendorApproval_Id, Integer companyId)
			throws Exception;

	// Vendor Approval to Update the status and Approval id also
	// InventoryTyreRetread
	public void update_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreRetread_ID(String ApprovalService_ID,
			Long Approval_ID, short approval_Status) throws Exception;
	
	public void updateVendorPaymentDetailsForRt(Long approvalId, String paymentDate, short paymentStatus) throws Exception;
	public void updateVendorPaymentCancelDetailsForRT(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception;

	// Vendor Approval to Update the status and Approval id also
	// InventoryTyreRetread
	public void update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryTyreRetread_ID(String ApprovalService_ID,
			Long Approval_ID, short approval_Status, Date approval_date, Integer companyId,double paidAmount,double discountAmount) throws Exception;

	// Vendor Approval ID to Show InventoryTyreRetread
	public List<InventoryTyreRetreadDto> getVendorApproval_IN_InventoryTyreRetread_List(Long VendorApproval_Id, Integer companyId)
			throws Exception;

	// Vendor Approval ID to Show InventoryTyreRetread
	public List<InventoryTyreRetreadDto> get_Amount_VendorApproval_IN_InventoryTyreRetread(Long VendorApproval_Id, Integer companyId)
			throws Exception;

	/** Tyre Report Purchase Report show in invoice Date */
	public List<InventoryTyreInvoiceDto> Tyre_Purchase_Report(String dateRangeFrom, String DateRangeTo, String Query)
			throws Exception;

	/** Tyre Report InventoryTyreAmount Report show in invoice Date */
	public List<InventoryTyreAmountDto> Tyre_Purchase_Report_TyreAmount(String multiple_ID, Integer companyId) throws Exception;

	/** Tyre Report InventoryTyre Report show in invoice Date */
	public List<InventoryTyreDto> Tyre_Purchase_Report_InventoryTyre(String multiple_ID, Integer companyId) throws Exception;

	/** Tyre Report InventoryTyre Report show in invoice Date */
	public List<Double> Tyre_Purchase_Report_InventoryTyre_TotalAmount(String multiple_ID, Integer companyId);

	/** Tyre Report Tyre_Stock_Report */
	public List<InventoryTyreDto> Tyre_Stock_Report(String Query) throws Exception;

	/**
	 * @param createTransferIn
	 */
	public void add_Inventory_Tyre_Transfer(InventoryTyreTransfer createTransferIn);

	/**
	 * @param inventory_transfer_id
	 * @return
	 */
	public InventoryTyreTransfer Find_InventoryTyreTranfer_Details_ID(Long inventory_transfer_id, Integer companyId);

	/**
	 * @param transfer_receiveddate
	 * @param transfer_receivedReason
	 * @param lASTMODIFIEDBY
	 * @param transfer_receiveddate2
	 * @param ittid
	 */
	public void Update_InventoryTyreTransfer_ReceivedBy_Details(Timestamp transfer_receiveddate,
			String transfer_receivedReason, Long lASTMODIFIEDBY, Timestamp transfer_receiveddate2, Long ittid, Integer companyid);

	/**
	 * @param tyre_id
	 * @return
	 */
	public List<InventoryTyreTransferDto> Get_Tyre_id_To_InventoryTyreTransfer(Long tyre_id, Integer companyId);

	/**
	 * @param tra_TO_LOCATION
	 * @param tyre_ID
	 */
	public void Update_Transfer_Location_InventoryTyre(Integer tra_TO_LOCATION, Long tyre_ID, Integer companyId);
	
	/**
	 * 
	 * @param TyreInvoice
	 * @throws Exception
	 */
	public void save_Tyre_Inventory_Invoice(InventoryTyreInvoice TyreInvoice) throws Exception;

	/**
	 * Transfer all the document details from mysql to mongodb 
	 * @param list
	 * @throws Exception
	 */
	public void transferInventoryTyreRetreadDocument(List<InventoryTyreRetreadDocument> inventoryTyreRetreadDocumentList) throws Exception;
	
	public long getInventoryTyreRetreadDocumentMaxId() throws Exception;
	
	public List<InventoryTyreTransferDto> Get_Tyre_id_To_InventoryTyreTransfer(CustomUserDetails	userDetails);

	/**
	 * Method For Get Details of Transfered Tyre
	 * @param valueInObject
	 * @return
	 * @throws Exception
	 */
	public ValueObject getInventoryTyreTransferedReport(ValueObject object) throws Exception;
	
	public List<InventoryTyreAmountDto>  getInventoryAmountDetailsToEdit(Long invoiceId , Integer companyId) throws Exception;
	
	public void updateTyreInvoice(Long	inventoryInvoice, boolean asigned) throws Exception;

	public ValueObject getTyreSentForRetreadingReport(ValueObject object) throws Exception;
	
	public ValueObject getTyreRetreadInvoiceReport(ValueObject valueObject) throws Exception;

	public ValueObject updateTyreRetreadInvoice(ValueObject object) throws Exception;

	public List<InventoryTyreRetreadDto> getAllRetreadTyreList(String term)throws Exception;

	void updateInventoryTyreRetread(double amount, long id) throws Exception;

	public ValueObject saveMultipleRetreadTyre(ValueObject object)throws Exception;

	void updateTyreRetreadStatus(long id) throws Exception;

	long getInventoryTyreCountByStatus(CustomUserDetails userDetails, short tyreStatusId) throws Exception;

	long getLocationWiseInventoryTyreCount(CustomUserDetails userDetails, short tyreStatusId, Integer locationId) throws Exception;

	public Map<Integer, List<InventoryTyreHistoryDto>> getAllTyreAssignmentDetails(String startDate, String endDate)throws Exception;

	public InventoryTyreHistoryDto getPreTyreMountDismountHistory(long tyre_ID, InventoryTyreHistory tyreHistory,short statusID)throws Exception;

	public InventoryTyreHistoryDto getPreTyreRotationHistory(long rO_FROM_TYRE_ID, InventoryTyreHistory tyreHistory,short statusID)throws Exception;

	public void Update_TyreDocument_ID_to_Tyre(Long get_id, boolean b, Long ityre_ID, Integer company_id)throws Exception;

	public ValueObject getTyreCountListDetails(ValueObject object)throws Exception;

	public List<InventoryTyreDto> getTyreCountList(String query, Integer pageNumber) throws Exception;
	
	/*public Page<InventoryTyre> getDeployment_Page_ShowInLocationWiseInventoryTyreList(Integer pageNumber , short status,Integer locationID, Integer companyId) throws Exception;
	
	public Page<InventoryTyre> getDeployment_Page_ShowInInventoryTyreList(Integer pageNumber , short status, Integer companyId) throws Exception;*/

	public ValueObject getVehicleTyreAssignmentHistoryReport(ValueObject object) throws Exception;

	
	public void updatePaymentApprovedTyreDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId)throws Exception;// Tyre Approval Approved
	
	public void updatePaymentPaidTyreDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId, short VENDOR_PAYMODE_STATUS_ID)throws Exception;// Tyre Approval Paid

	public void updatePaymentApprovedTyreReteadDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId)throws Exception;// Retread Tyre Approval Approved

	public void updatePaymentPaidTyreRetreadDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId, short TR_VENDOR_PAYMODE_STATUS_ID)throws Exception;// Retread Tyre Approval Paid

	public void saveTyreExpenseDetails(TyreExpenseDetails tyreExpenseDetails)throws Exception;

	public void deleteTyreExpensedetails(Long tyre_id, Integer company_id)throws Exception;

	public ValueObject getAllTyreListByStatus(String term,String status)throws Exception;

	public List<InventoryTyreDto> getTyreSoldDetails(String term,short status)throws Exception;

	public ValueObject saveTyreSoldInvoice(ValueObject valueInObject)throws Exception;

	public void updateTyreStatusByTyreId(String string, short tyreAssignStatusSold)throws Exception;

	public ValueObject getTyreSoldDetailsByTyreSoldInvoiceId(ValueObject valueInObject)throws Exception;

	public void saveSoldTyreCost(ValueObject valueInObject)throws Exception;

	public void updateTyreSoldInvoiceDetils(ValueObject valueInObject)throws Exception;

	public ValueObject getPageWiseTyreSoldInvoiceDetails(ValueObject object)throws Exception;
	
	public Page<TyreSoldInvoiceDetails> getDeployment_Page_TyreSoldInvoiceDetails(Integer pageNumber, Integer companyId) throws Exception;

	public List<TyreSoldInvoiceDetailsDto> getTyreSoldInvoiceList(Integer pageNumber, Integer companyId) throws Exception;

	public ValueObject deleteTyreSoldInvoiceDetails(ValueObject valueInObject)throws Exception;

	public InventoryTyreRetreadAmount getRetreadTyreAmountDetailsByTR_AMOUNT_ID(Long tr_AMOUNT_ID)throws Exception;
	
	public List<TripSheetExpenseDto> getTyreInvoiceListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany, String ledgerame)throws Exception ;
	
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidTyreInvoiceList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception;
	
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidTyreRethreadList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception;

	public InventoryTyreHistory getTyreMountCreatedBetweenDates(String startDate, String endDate, Integer companyId) throws Exception;

	public void updateSublocationInInventoryTyre(Long ityre_ID, Integer subLocationId, Integer companyId) throws Exception;

	public InventoryTyreInvoice getInventoryTyreInvoiceByInvoiceNumber(String invoice_NUMBER, Integer company_id)throws Exception;

	public List<InventoryTyreHistoryDto> getTyreConsumptionList(ValueObject object)throws Exception;

	public ValueObject getLocationWiseTyreQuantity(ValueObject object)throws Exception;
	
	public ValueObject getlocationWiseTyreCount(ValueObject valueobject) throws Exception;

	public List<InventoryTyre> getTyreByTyreModel(String term, Long tyreModelId,  short tyreStatusId, Integer company_id,Integer locationId)throws Exception;

	public void updateTyreStatusToAvailable(Integer VEHICLE_ID, Integer CLOSE_ODOMETER, Integer TYRE_USEAGE,
			short TYRE_ASSIGN_STATUS, short tyreStatusId, Long TYRE_ID) throws Exception;

	public ValueObject getAvailabeTyreForAssignment(ValueObject object)throws Exception;

	public ValueObject getTyreGuageByTyreId(ValueObject valueObject) throws Exception;
	
	public ValueObject getlocationTyreCount(ValueObject valueobject) throws Exception ;
	
	public List<InventoryTyre> getlocationTyreList(ValueObject valueobject) throws Exception;
	
	public void updateMultipleTyreStatus(short status ,String tyreIds ,int companyId);
	
	public void updateMultipleTyreStatusAndLocation(short status ,String tyreIds ,int companyId,long location);
	
	public List<InventoryTyre> tyreListDropDown(String term, Integer location, Integer companyid,long model)
			throws Exception;
	
	public List<InventoryTyre> getTyreListByIds(List<Long> ids ,ValueObject valueobject) throws Exception;

	public Long validateTyreByTyreSize(Integer tyreSizeId, Integer companyId) throws Exception;

	public Long  validateTyreByTyreModel(Integer tyreModelId, Integer companyId) throws Exception;

	public List<InventoryTyreDto> getMaxRunTyre(ValueObject valueObject) throws Exception;

	public List<InventoryTyreDto> getMinRunScrapTyre(ValueObject valueObject)throws Exception;

	public void updateOpenOdometer(Long tyre_ID, int RotationOdometer, Integer companyId)throws Exception;
	public List<InventoryTyre> getMoveToRepairTyre(Integer company_id, Integer locationId)throws Exception;

	void updateInventoryTyreRejectStatus(Long scropedby, Date scropedDate, short Status, short dismountStatus,
			String Multiple_Tyre_ID, Integer companyId) throws Exception;

	List<InventoryTyre> getLocationWiseTyreList(Integer company_id, Integer locationId) throws Exception;
	
	public List<InventoryTyreDto> getTyreHistoryDetails(String query) throws Exception;
	

	public List<TripSheetExpenseDto> getTyreInvoiceListForTallyImportATC(String fromDate, String toDate, Integer companyId,
			String tallyCompany, String ledgerame)throws Exception ;


}
