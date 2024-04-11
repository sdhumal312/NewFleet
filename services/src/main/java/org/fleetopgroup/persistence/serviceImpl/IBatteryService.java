package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.document.BatteryInvoiceDocument;
import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.BatteryInvoiceDto;
import org.fleetopgroup.persistence.model.Battery;
import org.fleetopgroup.persistence.model.BatteryInvoice;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IBatteryService {

	public ValueObject	saveBatteryDetails(ValueObject	valueObject, MultipartFile file) throws Exception;
	
	public ValueObject	get_list_BatteryInvoiceDetails(ValueObject	valueObject) throws Exception;
	
	public List<BatteryDto> getBatteryList(BatteryInvoiceDto	batteryInvoiceDto) throws Exception;
	
	public ValueObject	saveBatterySerialNumber(ValueObject	valueObject) throws Exception;
	
	public ValueObject	deleteBatteryDetails(ValueObject valueObject) throws Exception; 
	
	public ValueObject	deleteBatteryInvoice(ValueObject valueObject) throws Exception; 
	
	public Page<Battery> getDeploymentLog_Location(Integer Location, Integer pageNumber, Integer companyId) throws Exception;
	
	public List<BatteryDto> find_List_InventoryBattery_Location(Integer Location, Integer pageNumber, Integer companyId) throws Exception;
	
	public List<BatteryDto> find_List_InventoryBattery_Search(String Location, Integer pageNumber, Integer companyId) throws Exception;
	
	ValueObject	getLocationBatteryList(ValueObject	valueObject) throws Exception;
	
	ValueObject	getBatteryInfo(ValueObject valueObject) throws Exception;
	
	public BatteryDto getBatteryInfo(Long batteryId, Integer companyId) throws Exception;
	
	ValueObject	updateBatterySerialNumber(ValueObject valueObject) throws Exception;
	
	ValueObject	getSearchBatteryList(ValueObject	valueObject) throws Exception;
	
	public Page<Battery> getDeploymentLog_Search_Battery(String Location, Integer pageNumber, Integer companyId) throws Exception;
	
	List<BatteryDto>  getBatteryListForDropdown(String term , Integer companyId, Long id) throws Exception;
	
	List<BatteryDto>  getBatteryListForDropdown(String term , Integer companyId, Long capacityId, Long id) throws Exception;
	
	List<BatteryDto>  getBatteryListForDropdown(String term , Integer companyId, Integer fromLocation) throws Exception;
	
	BatteryDto getBatteryCapacity(Long batteryId) throws Exception;
	
	public void update_Assign_Battry_To_Vehicle_Mount(Integer VEHICLE_ID, 
			Integer OPEN_ODOMETER, short TYRE_ASSIGN_STATUS, Date TYRE_ASSIGN_DATE, Long TYRE_ID, Integer companyId) throws Exception;

	public void update_Assign_Battry_To_Vehicle_Mount(Integer VEHICLE_ID, 
			Integer OPEN_ODOMETER, short TYRE_ASSIGN_STATUS, Date TYRE_ASSIGN_DATE, Long TYRE_ID, Integer companyId, Timestamp batteryFirstAsignedDate) throws Exception;

	
	public void update_Assign_Battry_To_Vehicle_DisMount(Integer VEHICLE_ID,
			Integer CLOSE_ODOMETER, Integer TYRE_USEAGE, short TYRE_ASSIGN_STATUS, Long TYRE_ID, Long usesNoOfTime) throws Exception;
	
	BatteryDto  getVehicleBatteryDetails(Long layoutId, Long batteryId) throws Exception;
	
	ValueObject	viewBatteryHistory(ValueObject valueObject) throws Exception;
	
	List<BatteryDto>  getBatteryListToScrap(String query, Integer companyId) throws Exception;
	
	public void Update_Inventory_Battery_ScropStatus(Long scropedby, Date scropedDate, short Status,
			String Multiple_Tyre_ID, Integer companyId) throws Exception;
	
	public void updateScrapRemark(Long batteryId, String reason) throws Exception;
	
	Battery getBattery(Long batteryId) throws Exception;
	
	public void Update_Transfer_Location_InventoryBattery(Integer tra_TO_LOCATION, Long tyre_ID, Integer companyId);
	
	Long getBatteryCount(Long capacityId, Integer companyId) throws Exception;
	
	public List<Battery>  validateBatterySerialNumber(String number, Integer companyId) throws Exception;
	
	public void saveBattery(Battery  battery) throws Exception;
	
	public ValueObject getBatteryScrapReport(ValueObject valueObject) throws Exception;
	
	List<BatteryDto>  getVehicleBatteryListForCost(Integer vid) throws Exception;
	
	List<BatteryDto>  getGroupVehicleBatteryListForCost(long vehicleGroupId) throws Exception;

	ValueObject getBatteryStockReport(ValueObject valueObject) throws Exception;
	
	public ValueObject getBatteryPurchaseInvoiceReport(ValueObject valueObject) throws Exception;

	long getlocationWisebatteryCountByStatus(Integer companyId, short status, Integer location) throws Exception;

	public ValueObject getBatteryCountListDetails(ValueObject object)throws Exception;
	
	public List<BatteryDto> getBatteryCountList(String query, Integer pageNo) throws Exception;

	public void saveBatteryDocument(BatteryInvoice battery, MultipartFile file, ValueObject valueObject) throws Exception;

	public org.fleetopgroup.persistence.document.BatteryInvoiceDocument getBatteryInvoiceDocumentDetails(long batteryInvoiceId, Integer company_id)throws Exception;

	public void updateBatteryInvoiceDocumentId(Long get_id, boolean b, Long batteryInvoiceId, Integer company_id)throws Exception;

	public void addBatteryInvoiceDocument(BatteryInvoiceDocument batteryDoc)throws Exception;

	public BatteryInvoiceDocument getBatteryInvoiceDocumentDetailsByInvoiceId(Long id, Integer companyId) throws Exception;

	public ValueObject getLocationWiseBatteryQuantity(ValueObject object)throws Exception;
	
	public ValueObject getlocationWiseBatteryCount(ValueObject valueobject) throws Exception;
	
	public ValueObject getlocationBatteryCount(ValueObject valueobject) throws Exception;
	
	public void updateMultipleBatteryStatus(String batteryIds,short status,int companyId);
	
	public  List<Battery> getlocationWisebatteryList(Integer companyId, short status, Integer location ,long batteryManufacturerId ,long batteryModel,long batteryCapacity) throws Exception;
	
	public void updateMultipleBatteryStatusAndLocation(String batteryIds,short status,int companyId,long wareHouseLocationId);
	
	public List<BatteryDto> getBatteryListDropdown(String term, Integer companyId, Integer fromLocation,long capacity)
			throws Exception;
	
	public  List<Battery> getbatteryListByIds(Integer companyId, short status, Integer location ,List<Long> ids) throws Exception;

	public void updateDismountStatusId(Integer vid, Integer openOdometer, Integer useage,
			short batteryAsignedStatusAvailable, short short1, Long batteryId, Long usesNoOfTime)throws Exception;

	public List<BatteryDto> getMoveToRepairBattery(Integer company_id,Integer fromLocationId)throws Exception;

	void updateInventoryBatteryRejectStatus(Long scropedby, Date scropedDate, short Status, short dismountStatus,
			String Multiple_Tyre_ID, Integer companyId) throws Exception;

	List<BatteryDto> getLocationWiseBatteryList(Integer companyId, Integer fromLocationId) throws Exception;

	public List<Battery> Search_Battery_History_Report(String battery) throws Exception;
	
}
