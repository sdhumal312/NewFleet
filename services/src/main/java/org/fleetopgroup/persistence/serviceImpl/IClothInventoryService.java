package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.document.ClothInvoiceDocument;
import org.fleetopgroup.persistence.dto.ClothInvoiceDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.UpholsterySendLaundryInvoiceDto;
import org.fleetopgroup.persistence.dto.VehicleClothInventoryDetailsDto;
import org.fleetopgroup.persistence.dto.VehicleClothInventoryHistoryDto;
import org.fleetopgroup.persistence.dto.VehicleClothMaxAllowedSettingDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.persistence.model.ClothInventoryDetails;
import org.fleetopgroup.persistence.model.ClothInventoryStockTypeDetails;
import org.fleetopgroup.persistence.model.ClothInvoice;
import org.fleetopgroup.persistence.model.UpholsterySendLaundryInvoice;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IClothInventoryService {

	ValueObject saveClothInventoryDetails(ValueObject  valueObject,MultipartFile file) throws Exception;
	
	ValueObject	getClothInvoiceList(ValueObject valueObject) throws Exception;
	
	public List<ClothInvoiceDto> getClothInvoiceList(Integer pageNumber, Integer companyId) throws Exception ;
	
	ValueObject	getlocationClothDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject	get_list_ClothInvoiceDetails(ValueObject valueObject) throws Exception;
	
	public Page<ClothInventoryStockTypeDetails> getDeploymentLog_Location(Integer Location, Integer pageNumber) throws Exception;
	
	public ClothInvoiceDto  getClothInvoiceDetails(Long invoiceId, Integer companyId) throws Exception;
	
	ValueObject	getClothDetailsToEdit(Long invoiceId, Integer companyId) throws Exception;
	
	public void updateClothInvoice(ClothInvoiceDto clothInvoiceDto) throws Exception ;
	
	ValueObject	deleteClothInventoryDetails(ValueObject 	valueObject) throws Exception;
	
	public void removeClothInventoryDetailsFromInvoice(ClothInventoryDetails		inventoryDetailsDto) throws Exception;
	
	public void removeClothStockTypeDetails(ClothInvoice	clothInvoice, Long clothTYpesId) throws Exception ;
	
	public ValueObject	deleteClothInventory(ValueObject 	valueObject) throws Exception;
	
	public ValueObject	searchClothInvoice(ValueObject 	valueObject) throws Exception;
	
	public Page<ClothInvoice> getDeployment_Page_ClothInvoice(String term , Integer pageNumber, Integer companyId) throws Exception;
	
	public List<ClothInvoiceDto> getClothInvoiceList(String term ,Integer pageNumber, Integer companyId) throws Exception;
	
	public ValueObject	searchClothInvoiceByNumber(ValueObject 	valueObject) throws Exception;
	
	public ValueObject	getVehicleCLothAsignmentDetails(ValueObject 	valueObject) throws Exception;
	
	public ValueObject	saveVehicleClothInventoryDetails(ValueObject 	valueObject) throws Exception;
	
	public ValueObject	getVehicleClothInventoryDetails(ValueObject 	valueObject) throws Exception;
	
	public ValueObject	removeClothInventoryDetails(ValueObject 	valueObject) throws Exception;
	
	public ValueObject	saveSentClothToLaundry(ValueObject 	valueObject) throws Exception;
	
	ValueObject	getClothLaundryDetails(ValueObject valueObject) throws Exception;
	
	public Page<UpholsterySendLaundryInvoice> getDeployment_Page_UpholsterySendLaundryInvoice(Integer pageNumber, Integer companyId);
	
	public List<UpholsterySendLaundryInvoiceDto> getUpholsterySendLaundryInvoiceDtoList(Integer pageNumber, Integer companyId) throws Exception;
	
	ValueObject	getLaundryInvoiceDetails(ValueObject valueObject) throws Exception;
	
	ValueObject	receiveClothFromLaundry(ValueObject valueObject) throws Exception;
	
	ValueObject	getReceivedClothDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getUpholsteryPurchaseInvoiceReport(ValueObject valueObject) throws Exception;

	public ValueObject getLaundryUpholsteryReceiveReport(ValueObject object) throws Exception;
	
	public ValueObject saveVehicleClothMaxAllowed(ValueObject valueObject) throws Exception;
	
	public ValueObject getVehicleClothMaxAllowed(ValueObject valueObject) throws Exception;
	
	public List<VehicleClothMaxAllowedSettingDto> getVehicleClothMaxAllowedList(Integer companyId) throws Exception;
	
	public List<VehicleClothMaxAllowedSettingDto> viewVehicleClothMaxAllowedList(Integer pagenumber, Integer companyId) throws Exception; 
	
	public ValueObject getVehicleClothMaxAllowedById(ValueObject valueObject) throws Exception;
	
	public ValueObject updateVehicleClothMaxAllowedById(ValueObject valueObject) throws Exception;
	
	public ValueObject deleteVehicleClothMaxAllowedById(ValueObject valueObject) throws Exception;
	
	public ValueObject getShowClothAssignDetails(ValueObject valueObject) throws Exception;
	
	public List<VehicleClothInventoryHistoryDto> getShowClothAssignDetailsList(Integer pageNumber, Integer vid, Integer companyId) throws Exception;
	
	public ValueObject getInServiceVehicle(ValueObject valueObject) throws Exception;
	
	public List<VehicleClothInventoryDetailsDto> getInServiceVehicleList(Integer pageNumber, long clothTypeId, Integer locationId) throws Exception;

	public ValueObject getUpholsteryStockReport(ValueObject valueObject) throws Exception;
	
	public ValueObject getDamageWashingQtyDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject saveDamageWashingQty(ValueObject valueObject) throws Exception;
	
	public ValueObject saveLostWashingQty(ValueObject valueObject) throws Exception;
	
	public ValueObject saveClothDamageDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject saveClothLostDetails(ValueObject valueObject) throws Exception;

	public ValueObject getInDamageDetails(ValueObject valueObject) throws Exception; 
	
	public ValueObject getInLostDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getInWashingDetails(ValueObject valueObject) throws Exception;

	public ValueObject getUpholsteryAssignmentReport(ValueObject object)throws Exception;

	public ValueObject getUpholsteryStockTransferReport(ValueObject object)throws Exception;
	
	public ValueObject getVehicleClothListBySearch(ValueObject valueObject) throws Exception;
	
	public List<VehicleClothMaxAllowedSettingDto> vehicleClothListSearchByVid(Integer vid, Integer companyId) throws Exception;

	public ValueObject getUpholsterySentToLaundryReport(ValueObject object) throws Exception;    

	public List<ClothInvoiceDto> FilterVendorCreditListInventoryClothInvoice(Integer vendorId, String dateRangeFrom,
			String dateRangeTo, Integer company_id) throws Exception;

	public List<ClothInvoiceDto> getVendorApproval_IN_InventoryClothInvoice_List(Long approvalId, Integer company_id)throws Exception;
	
	public List<UpholsterySendLaundryInvoiceDto> getVendorApprovalLaundryInvoiceList(Long approvalId, Integer company_id)throws Exception;

	public void update_Vendor_ApprovalTO_Status_MULTIP_InventoryClothInvoice_ID(String mULT_PI_id, Long approvalId,
			short vendorPaymodeStatusApproved)throws Exception;
	
	public void updateVendorPaymentDetails(Long approvalId, String paymentDate, short paymentStatus) throws Exception;
	
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception;
	
	public void update_Vendor_ApprovalTO_Status_LaundryInvoice_ID(String mULT_PI_id, Long approvalId,
			short vendorPaymodeStatusApproved, Integer companyId)throws Exception;
	
	public void updateVendorPaymentDetailsLI(Long approvalId, short paymentStatus) throws Exception ;
	
	public void updateVendorPaymentCancelDetailsLI(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus)
			throws Exception;

	public List<ClothInvoiceDto> get_Amount_VendorApproval_IN_InventoryClothInvoice(Long approvalId, Integer companyId)throws Exception;
	
	public List<UpholsterySendLaundryInvoiceDto> getVendorInventoryLaundryInvoice(Long approvalId, Integer companyId)throws Exception;

	
	public void update_Payment_Vendor_ApprovalTO_Status_MULTIP_InventoryClothInvoice_ID(String mULT_CI_id, Long approvalId,
			short vendorPaymodeStatusPaid, Timestamp paymentDate, Integer company_id, double paidAmount,
			double discountAmount) throws Exception;
	
	public void update_Payment_Vendor_ApprovalTO_Status_MULTIP_LaundryInvoice(String mULT_CI_id, Long approvalId,
			short vendorPaymodeStatusPaid, Timestamp paymentDate, Integer company_id, double paidAmount,
			double discountAmount) throws Exception;

	void update_ClothInvoiveApprovalAmount(Object clothInvoiceId, Object recievedAmount, Object balanceAmount, Object discountAmount,
			short approvalStatusId, long approvalId, Date date) throws Exception;//direct payment

	public ValueObject getAllUpholsteryAssigndToVehicleByDate(ValueObject object)throws Exception;

	public Map<Integer, List<VehicleClothInventoryHistoryDto>> getAllUpholsteryDetails(String startDate, String endDate) throws Exception;

	public ValueObject getUpholsteryLossReport(ValueObject object) throws Exception;

	public void updatePaymentApprovedUpholsteryDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId)throws Exception;//Approval Approved Here
	
	public void updatePaymentApprovedLaundryDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId)throws Exception;

	public void updatePaymentPaidUpholsteryDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId, short vendorpaymentStatus)throws Exception;//Approval Paid Here
	
	public void updatePaymentPaidLaundryDetails(String ApprovalInvoice_ID, Timestamp approval_date, Integer companyId, short vendorpaymentStatus)throws Exception;
	
	public List<TripSheetExpenseDto> getClothInvoiceListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;
	
	public List<TripSheetExpenseDto> getClothInvoiceListForTallyImportATC(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;
	
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidClothInvoiceList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception;
	
	public void saveClothDocument(ClothInvoice cloth, MultipartFile file, ValueObject valueObject) throws Exception;

	public org.fleetopgroup.persistence.document.ClothInvoiceDocument getClothInvoiceDocumentDetails(long clothInvoiceId, Integer company_id)throws Exception;

	public void updateClothInvoiceDocumentId(Long get_id, boolean b, Long clothInvoiceId, Integer company_id)throws Exception;

	public void addClothInvoiceDocument(ClothInvoiceDocument clothDoc)throws Exception;

	public ClothInvoiceDocument getClothInvoiceDocumentDetailsByInvoiceId(Long clothInvoiceId, Integer company_id)throws Exception;

	public ClothInvoice getClothInvoiceByClothInvoiceId(Long clothInvoiceId, Integer companyId) throws Exception;
	
	public ValueObject saveVehicleLaundryDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getVehicleLaundryDetailsList(ValueObject valueObject) throws Exception;
	
	public ValueObject removeVehicleLaundry(ValueObject valueObject) throws Exception;
	
	public ValueObject	updateClothToLaundry(ValueObject 	valueObject) throws Exception;
	
	public ValueObject	deleteClothLaundryInvoice(ValueObject 	valueObject) throws Exception;
	
	public List<TripSheetExpenseDto> getLaundryInvoiceListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;
	
	public List<TripSheetExpenseDto> getLaundryInvoiceListForTallyImportATC(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;
	
	public List<UpholsterySendLaundryInvoiceDto> FilterVendorLaundryInvoice(Integer vendorId, String dateRangeFrom,
			String dateRangeTo, Integer companyId) throws Exception;
	
	public List<TripSheetExpenseDto>  getVehicleLaundryExpListForTally(String fromDate, String toDate, Integer companyId,
			String tallyCompany) throws Exception;

	public ClothInvoice getClothInvoiceByInvoiceNumber(String invoiceNumber, Integer companyId) throws Exception;

} 
