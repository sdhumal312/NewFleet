package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 */
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryAllDto;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.InventoryLocationDto;
import org.fleetopgroup.persistence.dto.InventoryTransferDto;
import org.fleetopgroup.persistence.dto.PartInvoiceDto;
import org.fleetopgroup.persistence.dto.PurchaseOrderReportDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.model.Inventory;
import org.fleetopgroup.persistence.model.InventoryAll;
import org.fleetopgroup.persistence.model.InventoryLocation;
import org.fleetopgroup.persistence.model.InventoryTransfer;
import org.fleetopgroup.persistence.model.PartInvoice;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface IInventoryService {

	// save Inventory All total Quantity
	public void add_InventoryAll(InventoryAll InventoryAll) throws Exception;

	public Inventory save_Inventory(Inventory inventory) throws Exception;
	
	// save Inventory All location total Quantity
	public InventoryLocation add_InventoryLocation(InventoryLocation inventory_Location) throws Exception;

	// save Inventory location All Details and Quantity
	public void add_Inventory(Inventory Quantity) throws Exception;

	// update Inventory All total Quantity
	public void update_InventoryAll_PARTID_PARTNAME_GENRAL(Long PARTID) throws Exception;

	// update Inventory All location total Quantity
	public void update_InventoryLocation_PARTID_PARTNAME_LOCATION_GENRAL(Long PARTID, Integer PartLocation_ID)
			throws Exception;

	// update Inventory location All Details and Quantity
	public void updateInventory(Inventory Inventory) throws Exception;

	public List<Inventory> listInventory() throws Exception;

	public List<Inventory> findAllListInventory(Integer companyId) throws Exception;

	public List<Inventory> SearchlistInventory(String Search) throws Exception;

	// search Inventory SearchlistInventoryINVoice
	public List<InventoryDto> SearchlistInventoryINVoice(String Search, Integer companyId) throws Exception;

	// search all Inventory
	public List<InventoryAllDto> SearchlistInventoryAll(String Search, Integer companyId) throws Exception;

	// search location Quantity
	public List<InventoryLocationDto> SearchlistInventoryLocation(String Search, Integer companyId) throws Exception;
	
	public List<InventoryLocationDto> SearchlistInventoryLocationOnLocation(String Search, Integer companyId, Integer locationId) throws Exception;

	// get inventory Validate in Database
	public List<InventoryAll> Validate_InventoryAll(Long part_id, Integer companyId) throws Exception;

	// get inventory Validate in Database
	public InventoryAllDto Purchase_Order_Validate_InventoryAll(Long part_id) throws Exception;

	// get inventory Validate in Database
	public List<InventoryLocation> Validate_Inventory_Location(Long part_id, Integer locationId) throws Exception;

	// get InventoryAll Details
	public InventoryAllDto getInventoryAll(Long sid, Integer companyId) throws Exception;
	
	public List<InventoryAllDto> getInventoryAllGPONName(String partName, Integer companyId) throws Exception;
	
	public InventoryAllDto getInventoryAllOnName(String partname, Integer companyId) throws Exception;

	// get InventoryAll Details Part_id
	public InventoryAllDto Get_InventoryAll_USEING_PART_ID(Long sid, Integer companyId) throws Exception;
		
	// get InventoryLocation Details
	public InventoryLocationDto getInventoryLocationByLocationId(Long Location_id, Integer companyId) throws Exception;
	
	public InventoryLocation getInventoryLocation(Long Location_id) throws Exception;

	// get InventoryAll ID To InventoryLocation Part Location Quantiy
	public List<InventoryLocationDto> Get_InventoryAll_id_To_Inventory_Location(Long inventory_all_id, Integer companyId) throws Exception;
	
	public List<InventoryLocationDto> Get_InventoryAll_id_To_Inventory_LocationOnName(String inventory_all_id, Integer companyId) throws Exception;

	// get InventoryAll ID To Inventory Part Location Quantiy
	public List<InventoryDto> Get_InventoryAll_id_To_Inventory(Long inventory_all_id, Integer companyId) throws Exception;

	// get InventoryAll ID To Inventory Part Location Quantiy
	public List<InventoryDto> Show_AvailableQYT_InventoryAll_id_To_Inventory(Long inventory_all_id, Integer companyId) throws Exception;
	
	public List<InventoryDto> Show_AvailableQYT_InventoryAll_id_To_InventoryOnName(String inventory_all_id, Integer companyId) throws Exception;

	// get WorkOrder InventoryAll ID To Inventory Part Location Quantiy
	public List<InventoryDto> Get_WorkOrder_InventoryLocation_id_To_Inventory(Long inventory_location_id, Integer companyId, String subLocationQuery) throws Exception;

	public InventoryDto getInventory(Long sid) throws Exception;

	public void updateInventoryQuantity(Double Quantiy, Long Inventoryid) throws Exception;

	// Subtract in Inventory Quantity From WorkOrder to Assign
	// workOrdersQuantity
	public void Subtract_update_Inventory_from_Workorder(Double Quantiy, Long Inventoryid) throws Exception;

	// Subtract in InventoryLocation Quantity From WorkOrder to Assign
	// workOrdersQuantity
	public void Subtract_update_InventoryLocation_from_Workorder(Long Part_Id, Integer Inventory_location_id, Integer companyId)
			throws Exception;

	// Subtract in InventoryLocation Quantity From WorkOrder to Assign
	// workOrdersQuantity
	public void Subtract_update_InventoryAll_from_Workorder(Long Part_Id, Integer companyId) throws Exception;

	// Add in Inventory Quantity From WorkOrder to Assign
	// workOrdersQuantity
	public void Add_update_Inventory_from_Workorder(Double Quantiy, Long Inventoryid, Integer companyId) throws Exception;

	// Add in InventoryLocation Quantity From WorkOrder to Assign
	// workOrdersQuantity
	public void Add_update_InventoryLocation_from_Workorder(Double Quantiy, Long Inventory_location_id, Integer companyId)
			throws Exception;

	// Add in InventoryLocation Quantity From WorkOrder to Assign
	// workOrdersQuantity
	public void Add_update_InventoryAll_from_Workorder(Double Quantiy, Long Inventory_All_id, Integer companyId) throws Exception;

	// delete Inventory save quantity
	public void deleteInventory(Long Inventory_id, Integer companyId) throws Exception;

	// delete InventoryLocation quantity
	public void deleteLocationInventory(Long Inventory_Location_id, Integer companyId) throws Exception;

	// delete InventoryAll All quantity
	public void deleteInventoryAll(Long Inventory_All_id, Integer companyId) throws Exception;

	// get Inventory_Location ID To Inventory Part Location Quantiy
	public List<Inventory> validate_Inventory_Location_id_To_Inventory(Long inventory_location_id) throws Exception;

	public List<Inventory> listOnlyStatus() throws Exception;

	// Inventory PartQuentity and location
	public List<Inventory> getInventoryPartQuantity_Location(String location) throws Exception;

	public Long countInventory() throws Exception;

	public List<Long> countLocationTotalQty() throws Exception;

	public List<Long> countLocationTotalQty(String location) throws Exception;

	/** This Page get InventoryAll table to get pagination values */
	public Page<InventoryAll> getDeployment_Page_InventoryAll(Integer pageNumber, Integer companyId, HashMap<String, Object> configuration)throws Exception ;
	
	public Page<PartInvoice> getDeployment_Page_PartInvoice(Integer pageNumber, Integer companyId)throws Exception ;
	
	public Page<InventoryLocation> getDeployment_Page_LowStockInventoryLocation(Integer pageNumber, CustomUserDetails userDetails, Integer partlocation_id)throws Exception ;

	/**
	 * This List get InventoryAll table to get pagination last 10 entries values
	 */
	public List<InventoryAllDto> list_Of_All_Inventory_Quantity(Integer pageNumber, Integer companyId, HashMap<String, Object>  configuration) throws Exception;

	/**
	 * This Page get Inventory table to get pagination values GET PAGE TO GROUP
	 * BY PART_ID AND PART NUMBER AND QUANTITY
	 */
	public Page<Inventory> getDeployment_Page_Inventory_GROUPBY_PARTID_PARTNAME(Integer pageNumber);

	/**
	 * This List get InventoryAll table to get pagination last 10 entries values
	 * GET PAGE TO GROUP BY PART_ID AND PART NUMBER AND QUANTITY
	 */
	public List<InventoryDto> list_Of_All_Inventory_GROUPBY_PARTID_PARTNAME(Integer pageNumber) throws Exception;

	/** This Page get InventoryAll table to get pagination values */
	public Page<InventoryLocation> getDeployment_Page_InventoryLocation(Integer pageNumber, Integer locationId, 
									Integer companyId, HashMap<String, Object>  configuration);

	/**
	 * This List get InventoryLocation table to get pagination last 10 entries
	 * values
	 */
	public List<InventoryLocationDto> list_Of_Location_Inventory_Quantity(Integer pageNumber, Integer locationId, 
			 							Integer companyId, HashMap<String, Object> configuration)
			throws Exception;

	// save InventoryTransfer All Details
	public void add_InventoryTransfer(InventoryTransfer inventoryTransfer) throws Exception;

	// get InventoryAll ID To InventoryTransfer Part Location Quantity
	public List<InventoryTransferDto> Get_InventoryAll_id_To_InventoryTransfer(Long inventory_all_id, Integer companyId) throws Exception;

	// Inventory Report
	public List<InventoryDto> Report_listInventory(InventoryDto inventoryReport, Integer companyId) throws Exception;

	/** This interface is Inventory count Total and Location Total inventory */
	public Object[] countTotal_Inventrory_AND_Location_Qty() throws Exception;

	/** This interface is Inventory Qty Total and Location Total inventory */
	public Object[] countTotal_Inventory_Qty_AND_Location_Qty(Integer locationId, Integer companyId) throws Exception;

	/** This interface is Inventory Qty Total and Location Total inventory */
	public Object[] countTotal_Inventory_Qty_Liter(Integer companyId) throws Exception;

	/** This Report_list_Of_Location_Inventory */
	public List<InventoryLocationDto> Report_list_Of_Location_Inventory(String Query, Integer companyId) throws Exception;

	/** This Report_list_Of_Location_Inventory */
	public List<InventoryDto> Report_Purchase_Inventory(String Query, Integer companyId) throws Exception;

	/** This Report_list_Of_Location_Inventory */
	public List<InventoryDto> Report_Part_Inventory_Stock_Invoice_DateRange_Report(String Query , Integer companyId) throws Exception;

	/**
	 * @param inventoryquery
	 * @return
	 */
	public double Report_list_Of_Location_Inventory_Total_Inventory_Amount(String inventoryquery, Integer companyId);

	/**
	 * @param inventoryquery
	 * @return
	 */
	public double Report_Part_Inventory_Stock_Invoice_DateRange_Report_Amount(String inventoryquery);

	/**
	 * @param purchaseorder_id
	 * @return
	 */
	public List<Inventory> Get_Validate_InventoryPurchaseOrderId_Details(String purchaseorder_id, Integer companyid) throws Exception;

	/**
	 * @param inventory_transfer_id
	 * @return
	 */
	public InventoryTransfer Find_InventoryTranfer_Details_ID(Long inventory_transfer_id, Integer companyId);

	/**
	 * @param transfer_receiveddate
	 * @param transfer_receivedReason
	 * @param lastmodifiedby
	 * @param lastupdated_DATE
	 * @param inventory_transfer_id
	 */
	public void Update_InventoryTransfer_ReceivedBy_Details(Date transfer_receiveddate, String transfer_receivedReason,
			String lastmodifiedby, Date lastupdated_DATE, short TRANSFER_STATUS_ID, Long inventory_transfer_id, Integer companyId);

	/** This Page get InventoryAll table to get pagination values */
	public Page<InventoryTransfer> getDeployment_InventoryAll_id_To_InventoryTransfer_transfer_byEmail_Status(
			Long transfer_by_ID, Integer pageNumber, Integer companyId);

	// get InventoryAll ID To InventoryTransfer Part Location Quantity User Name
	// List
	public List<InventoryTransferDto> Get_InventoryAll_id_To_InventoryTransfer_transfer_byEmail_Status(Long UserID,
			Integer pageNumber, Integer companyId) throws Exception;

	/** This Page get InventoryAll table to get pagination values */
	public Page<InventoryTransfer> getDeployment_InventoryAll_id_To_InventoryTransfer_transfer_receivedbyEmail(
			Long transfer_receivedby_ID, Integer pageNumber, Integer companyId);

	// get InventoryAll ID To InventoryTransfer Part Location Quantity User Name
	// List
	public List<InventoryTransferDto> Get_InventoryAll_id_To_InventoryTransfer_transfer_receivedbyEmail(Long receivedby_ID,
			Integer pageNumber, Integer companyId) throws Exception;

	/**
	 * @param inventory_transfer_id
	 */
	public void Delete_InventoryTransfer_History_Delete_By_ID(Long inventory_transfer_id, Integer companyId);
	
	// get InventoryAll ID To InventoryLocation Part Location Quantiy
	public List<InventoryLocation> getInventoryLocationById(Long inventory_all_id, Integer companyId) throws Exception;

	public List<InventoryTransferDto> Get_Transfer_Inventory_Report_Wise_location(String dateRangeFrom, String dateRangeTo,
			String query, Integer companyId);

	public List<InventoryLocationDto> GetLowStockInventorLocationDetails(Integer pageNumber, CustomUserDetails	userDetails, Integer locationId) throws Exception;

	public List<InventoryDto> getPartInvoiceAmountDetailsToEdit(Long invoiceId , Integer companyId) throws Exception;
	
	public PartInvoiceDto Get_list_PartInvoiceDetails(Long partInvoiceId, Integer companyId) throws Exception;

	public void update_Part_Inventory_Invoice (PartInvoice PartInvoice) throws Exception;
	
	public void update_Part_Inventory (Inventory inventory) throws Exception;
	
	public Inventory getInventoryDetails(Long inventory_id, Integer companyId) throws Exception;
	
	public void update_anyPartNumberAsign_InPartInvoice(Long partinvoiceId, Integer companyId) throws Exception;
	
	public void update_InventoryAll_Quantity_From_PartInvoice(Long inventory_all_id, Integer companyId, Double all_quantity) throws Exception;
	
	public void update_InventoryLocation_Quantity_From_PartInvoice(Long inventory_location_id, Integer companyId, Double location_quantity) throws Exception;
	
	public List<Inventory> Get_InventoryDetails_From_PartInvoiceId(long partInvoiceId, Integer companyId) throws Exception;
	
	public void update_InvoiceAmountOf_Inventory(Long partInvoiceId, Integer companyId, String invoiceAmount) throws Exception;
	
	public Map<String, Object> updateAddMorePartInventoryInvoice(ValueObject valueObject) throws Exception;

	public Map<String, Object> editPartInventory(ValueObject valueObject) throws Exception;
	
	public List<InventoryDto> Part_Purchase_Invoice_Report(String Query, Integer companyId) throws Exception;

	public void Update_PartDocument_ID_to_Part(Long get_id, boolean b, Long partInvoiceId, Integer company_id)throws Exception;

	public List<InventoryTransferDto> getAllRejectedRequisition(Long id, Integer pageNumber, Integer company_id, String status)throws Exception;

	public List<InventoryLocationDto> getPartForRefreshment(String Search, Integer locationId, Integer companyId) throws Exception;
	
	public List<InventoryLocationDto> getPartForRefreshment(String Search, Integer companyId) throws Exception;
	
	public List<InventoryDto> getInventoryLocation_id_To_Inventory(Long partid, Integer locationId, Integer companyId) throws Exception;
	
	public List<TripSheetExpenseDto> getPartInvoiceListForTallyImport(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;
	
	
	public List<TripSheetExpenseDto> getPartInvoiceListForTallyImportATC(String fromDate, String toDate, Integer companyId,
			String tallyCompany)throws Exception ;

	public List<InventoryDto> 	 getsubLocationPartDetails(ValueObject valueOutObject)throws Exception ;

	public List<InventoryLocationDto> searchlistInventorySubLocation(String term, Integer subLocationId, Integer company_id)throws Exception ;

	public void subtractInvenotryFromSubLocation(Double workOrdersQuantity, Long inventory_id, Integer object)throws Exception ;

	public List<InventoryDto> getInvoiceWisePartList(String term, String locationStr, Integer companyId)throws Exception ;

	public List<InventoryDto> getWorkorderInventoryPartDetails(Integer company_id, String query)throws Exception ;

	public ValueObject getLocationWisePartQuantity(ValueObject object)throws Exception ;
	
	public void Subtract_update_Inventory_from_Workorder(Double Quantiy, Long Inventoryid,int companyId) throws Exception;
	public void subtractInvenotryFromSubLocation(Double Quantiy, Long Inventoryid, Integer subLocationId,int companyId) throws Exception;
	public InventoryLocation getInventoryLocation(Long Location_id,int companyId) throws Exception;
	public InventoryDto getInventory(Long sid,int companyId) throws Exception;
	
	public void updateSerialNoAddedForParts(Long inventoryId, Integer companyId, Integer	count) throws Exception;
	
	public Double getLocationWisePartSum(long partId, int locationId ,int companyId);
	
	public Double getOtherWisePartSum( long partId,int locationId,int companyId);
	
	public InventoryLocationDto getInventoryLocationDtoByLocation(Long locationId, Integer companyId) throws Exception;
	
	public List<InventoryLocationDto> preparePartIdsString(List<InventoryLocationDto> partList,int companyId);
	
	public List<InventoryLocationDto> getSubstitudePartList(String partIds ,int companyId) throws Exception ;
	
	public List<InventoryLocationDto> allpartList(String Search, Integer companyId) throws Exception;

	public List<InventoryLocationDto> searchRepairablePartByLocation(String term, Integer company_id)throws Exception;

	public ValueObject transferInventoryToRepairLocation(ValueObject valueObject)throws Exception;

	public Inventory getInventoryByRepairStockId(long partId,long long1)throws Exception;

	public void deleteTransferdInventoryIdFromInventory(long inventoryId)throws Exception;
	
	public void upadateInTransitQntyInInventory(Double quantiy, Long inventoryid) throws Exception;
	
	public void upadateInTransitQntyInLoncationInv(Long partId, int locationId,int companyId) throws Exception ;
	
	public InventoryLocation addInventoryLocation(InventoryLocation inventory_Location) throws Exception;

	public List<InventoryDto> getWarrantyPartList(String term, Integer company_id) throws Exception;
	
	public InventoryAll   getInventoryAllById(Long inventory_all_id, Integer companyId) throws Exception;
	
	public Inventory getInventoryDetailsByPartId(Long poToPartId, Integer companyId) throws Exception;
	
	public List<PurchaseOrderReportDto> Purchase_Order_StatusWise_Report(String Query, Integer companyId) throws Exception;
	public List<PurchaseOrderReportDto> Purchase_Order_StatusWise_Report_urea(String Query, Integer companyId) throws Exception;
	
}