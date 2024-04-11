package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.HashMap;
/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.ServiceProgramSchedulesDto;
import org.fleetopgroup.persistence.dto.SubCompanyDto;
import org.fleetopgroup.persistence.dto.VehicleCommentDto;
import org.fleetopgroup.persistence.dto.VehicleDocumentDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VehiclePurchaseDto;
import org.fleetopgroup.persistence.model.ServiceProgramSchedules;
import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.UploadFile;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.persistence.model.VehicleComment;
import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.model.VehiclePhoto;
import org.fleetopgroup.persistence.model.VehiclePurchase;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.springframework.data.domain.Page;

//import com.fleetop.dto.UploadFile;

public interface IVehicleService {

	public Vehicle addVehicle(Vehicle vehicle) throws Exception;

	public void updateVehicle(Vehicle vehicle) throws Exception;

	public void addFileVehicle(UploadFile upload) throws Exception;

	public List<Vehicle> listVehicleVaildateOLD(String vehicle_registration, String vehicle_chasis,
			String vehicle_engine) throws Exception;

	/**This List get Vehicle table to get  pagination  last 10 entries values */
	public List<VehicleDto> listVehicel(short Status, Integer pageNumber, CustomUserDetails userDetails) throws Exception;
	
	/**This Page get Vehicle table to get  pagination values */
	public Page<Vehicle> getDeployment_Page_Vehicle(short Status, Integer pageNumber) throws Exception;


	public List<Vehicle> findAllVehiclelist() throws Exception;

	public Vehicle getVehicel(int vid, Integer companyId) throws Exception;
	
	public ValueObject getVehicleIdFromVehicle(ValueObject object) throws Exception;

	public Vehicle getVehicel1(Integer integer) throws Exception;
	
	/** Select getVehicle_Select_TripSheet_Details */
	public VehicleDto getVehicle_Select_TripSheet_Details(Integer integer) throws Exception;

	/** Select Vehicle Details Fuel Entries */
	public VehicleDto getVehicel_Details_Fuel_entries(Integer integer) throws Exception;
	
	/** Select Vehicle Details Fuel Import Entries */
	public VehicleDto getVehicel_Details_Fuel_Import(String  vehicleName, Integer companyId) throws Exception;
	
	
	/** Select Vehicle Drop Down in Fuel Add Entries */
	public VehicleDto getVehicel_DropDown_Fuel_ADD_entries(Integer integer, Integer companyId) throws Exception;
	
	
	public VehicleDto getVehicel_DropDown_Last_Fuel_ADD_entries(Integer integer, Integer companyId) throws Exception;
	
	
	public VehicleDto getVehicel_DropDown_Urea_ADD_entries(Integer integer, Integer companyId) throws Exception;
	
	
	/** Select Vehicle Header Show Details */
	public VehicleDto Get_Vehicle_Header_Details(Integer integer) throws Exception;
	
	/** Select Vehicle Header Show Fuel_ Entries Details */
	public VehicleDto Get_Vehicle_Header_Fuel_Entries_Details(Integer integer, int companyId) throws Exception;
	
	/** Select Vehicle Header Show Fuel__ADD_Entries get Fuel Type Details */
	public VehicleDto Get_Vehicle_Header_Fuel_ADD_Entries_Details(Integer integer, Integer companyId) throws Exception;
	
	
	/** Select Vehicle Details Renewal Reminder Entries */
	public Vehicle getVehicle_Details_Renewal_Reminder_Entries(Integer integer) throws Exception;
	
	/** Select Vehicle Details Service Entries */
	public VehicleDto getVehicle_Details_Service_Entries(Integer integer) throws Exception;
	
	/** Select Vehicle Details Service _Reminder Entries */
	public VehicleDto getVehicle_Details_Service_Reminder_Entries(Integer integer, Integer companyId) throws Exception;
	
	/** Select Vehicle Details Trip Sheet Entries */
	public VehicleDto getVehicle_Details_TripSheet_Entries(Integer integer) throws Exception;
	
	/** Select Vehicle Details WorkOrder Entries */
	public Vehicle getVehicle_Details_WorkOrder_Entries(Integer integer) throws Exception;
	
	/** Select Vehicle Drop Down in Vehicle Add Entries */
	public Vehicle getVehicle_DropDown_Issues_ADD_entries(Integer integer) throws Exception;
	
	/** Select Vehicle Details Vehicle Entries */
	public VehicleDto getVehicle_Details_Issues_entries(Integer integer) throws Exception;
	
	
	public Vehicle getVehicelReg(String vehicle_register) throws Exception;

	public void deleteVehicle(Integer vehicle_ID,Long userId,Timestamp toDate, Integer companyId) throws Exception;

	/* Vehicle Document */
	public void addVehicleDocument(VehicleDocument vehicleDocument) throws Exception;

	public void updateVehicleDocument(VehicleDocument vehicleDocument) throws Exception;

	public List<VehicleDocumentDto> listVehicleDocument(Integer vehicleDocument, Integer companyId) throws Exception;

	public List<VehicleDocumentDto> listofSortVehicleDocument(Integer vhid, Long name, Integer companyId) throws Exception;

	public VehicleDocumentDto getVehicleDocument(int vehicle_id, Integer companyId) throws Exception;

	public VehicleDocumentDto getDownload(int vehicle_id, Integer companyId) throws Exception;

	public void deleteVehicleDocument(int vehicle_id, Integer companyId) throws Exception;

	/* Vehicle Photo */
	public void addVehiclePhoto(org.fleetopgroup.persistence.document.VehiclePhoto vehiclePhoto) throws Exception;

	public void updateVehiclePhoto(VehicleDocument vehiclePhoto) throws Exception;

	public List<org.fleetopgroup.persistence.document.VehiclePhoto> listVehiclePhoto(Integer vid, Integer companyId) throws Exception;

	public List<org.fleetopgroup.persistence.document.VehiclePhoto> listofSortVehiclePhoto(String vehiclePhoto, String name) throws Exception;

	public org.fleetopgroup.persistence.document.VehiclePhoto getVehiclePhoto(int Photo_id, Integer companyId) throws Exception;

	public org.fleetopgroup.persistence.document.VehiclePhoto getDownloadPhoto(int Photo_id) throws Exception;

	public void deleteVehiclePhoto(int Photo_id, Integer companyId) throws Exception;

	/* Set profilePhoto */
	public void setprofilePhoto(int vehiclephoto_id, Integer vehicle_id, Integer companyId) throws Exception;

	/* Vehicle Purchase */
	public void addVehiclePurchase(VehiclePurchase vehiclePurchase) throws Exception;

	public void updateVehiclePurchase(VehiclePurchase vehiclePhoto) throws Exception;

	public List<VehiclePurchaseDto> listVehiclePurchase(Integer vehiclePurchase, Integer companyId) throws Exception;

	public List<VehiclePurchase> listofSortVehiclePurchase(String vehiclePurchase, String name) throws Exception;

	public VehiclePurchaseDto getVehiclePurchase(int doc_id, Integer companyId) throws Exception;

	public VehiclePurchase getDownloadPurchase(int doc_id, Integer companyId) throws Exception;

	public void deleteVehiclePurchase(int doc_id, Integer companyId) throws Exception;

	/* Update the current Odometer */
	public void updateCurrentOdoMeter(Integer vehicle_id, int oddmeter_id, Integer companyId) throws Exception;

	// Report
	public List<Vehicle> listVehicleReport(Vehicle vehicle) throws Exception;

	public List<VehicleDto> listVehicleReport(String vehicle) throws Exception;

	public List<Vehicle> GETImportVehicel(String vehicle_re) throws Exception;

	public List<Vehicle> listVehiclePatternReport(String vehicle) throws Exception;

	public List<Vehicle> listVehicleImport(String vehicle) throws Exception;

	public Long countTotalVehicle() throws Exception;

//	public Long countActive() throws Exception;

	//public Long countOwnership(String Ownership) throws Exception;

	public List<VehicleDto> SearchVehicle(String vehicle) throws Exception;
	
	public List<VehicleDto> SearchVehicle_Registration_Chassis(String vehicle) throws Exception;

	public List<Vehicle> findDropdownList(String vehicle) throws Exception;

	public List<Vehicle> SearchOnlyVehicleNAME(String vehicle) throws Exception;

	public List<Vehicle> SearchOnlyVehicleNAME_ALL_STATUS(String vehicle) throws Exception;
	
	public List<Vehicle> SearchOnlyVehicleNAME_ALL_STATUS(String vehicle, Long vehicleGroupId) throws Exception;

	// getVehicle_ID Current odometer values
	public Integer updateCurrentOdoMeterGETVehicleToCurrentOdometer(int vid) throws Exception;
	
	public Integer updateCurrentOdoMeterGetVehicleCurrentOdometer(int vid, int companyId) throws Exception;

	// Report WorkOrder Vehicle Wise Repair Report select Multiple vehicle
	public List<Vehicle> vehicle_wise_select_multiple_vehicle(String Multiplevehicle, Integer companyId) throws Exception;

	// Report Fuel Vehicle Wise Fuel_Mileage Report select Multiple vehicle get
	// expert mileage
	public List<Vehicle> vehicle_wise_Fuel_mileage_multiple_vehicle(String Multiplevehicle) throws Exception;

	// Report Fuel  vehicle_wise_GroupFuelRange_Group Report select vehicle Group List
	public List<Vehicle> vehicle_wise_GroupFuelRange_Group(long vehicleGroupId, Integer companyId) throws Exception;

	
	/** This interface is Vehicle count Total Vehicle and Owned Vehicle */
	public Object[] countTotalVehicle_AND_Ownership() throws Exception;
	
	/** This interface is Vehicle count Total Vehicle Document , photo, purchase, renewal reminder, fuel, sericeEntries, 
	 * serviceReminder, trip, workorders,  Vehicle, odometer, vehicleComment */
	public Object[] countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(Integer Vehicle_ID) throws Exception;

	
	
	/**
	 * Vehicle Comment in details *
	 */

	/** This Interface is save Vehicle Comment Details */
	public void add_Vehicle_Comment_Details(VehicleComment vehicleComment) throws Exception;

	/** This Interface is List of one Vehicle Comment Details */
	public List<VehicleCommentDto> Get_Vehicle_ID_Comment_Details(Integer vehicle_ID, Integer companyId) throws Exception;

	/** This Interface is Delete Vehicle Comment using comment id Details */
	public void Delete_Vehicle_Comment_Details(Long vehicleComment_ID, Integer companyId) throws Exception;

	/** This Interface is List of one Vehicle Comment Get Recent Comment Details to show */
	public List<VehicleCommentDto> Get_Recent_Comment_Details( Integer companyId) throws Exception;
	
	
	/** This interface is DashBoard count Total Issues-open-Overdue, Service_Reminder-Overdue-DueSoon, 
	 * Renewal_Reminder-Today-DLRENEWAL, Issues Open and OVerdue, Purchase Recommend and Purchase_Ordered   */
	public Object[] countTotal_Dashboard_IOpen_IOverdue_SROverdue_SRDuesoon_RRToday_DLRToday_PUReq_PUOrdered(String CurrentDate, Integer companyId) throws Exception;


	/** This interface is DashBoard count Total date total cost expense deatils  */
	public Object[] countTotal_Grap_Cost_Fuel_RenewalRe_SE_TS_PO_WO_Amount(String FromDate, String ToDate, Integer companyId) throws Exception;

	/**
	 * @param string
	 * @param vehicle_vid
	 *            // Note : When Vehicle Created Work Order That time Vehicle
	 *            Status update to 'WORKSHOP'
	 */
	//public void Update_Vehicle_Status(String vehicleStatus, Integer vehicle_vid);

	/**
	 * @param term
	 * @return
	 */
	public List<Vehicle> Search_Service_entries_Vehicle_Name(String vehicle) throws Exception;
	
	/**
	 * @param term
	 * @return
	 */
	public List<VehicleDto> Search_DealerService_Vehicle_Name(String vehicle) throws Exception;

	
	/**
	 * @param term
	 * @return
	 */
	public List<Vehicle> searchVehicleListCompare(String vehicle, Integer vid) throws Exception;

	
	/**
	 * @param string
	 * @param tripSheetID
	 * @param vid
	 */
	public void Update_Vehicle_Status_TripSheetID(Long tripSheetID, Integer vid, Integer compId, short vStatusId);

	/**
	 * @param vid
	 * @return
	 */
	public VehicleDto Get_Vehicle_Current_Status_TripSheetID(Integer vid) throws Exception;
	
	public VehicleDto Get_Vehicle_Current_Status_TripSheetID(Integer vid, Integer companyId) throws Exception;

	/**
	 * @param vEHICLE_VID
	 * @param format
	 * @param format2
	 * @return
	 */
	/**
	 * This interface is Vehicle count Total RenewalReminder Fuel Entries,
	 * ServiceEntries, TripSheet, WorkOrder
	 */
	public Object[] countTotal_LAST_MONTH_RR_FE_SE_TS_or_TC_or_TD_WO_REPORT(Integer vEHICLE_VID, String fromDate,
			String toDate);
	
	public List<VehicleDto> listVehicel(short Status, Integer companyId) throws Exception;
	
	public List<Vehicle> listVehicleReportByVGPermision(String vehicle) throws Exception ;
	
	public VehicleDto getVehicelDetails(Integer vid, CustomUserDetails	userDetails) throws Exception;
	
	public void updateVehicleStatus(short status, Integer vid, Integer companyId)throws Exception;
	
	public ValueObject getVehicleCommentList(ValueObject	valueInObject) throws Exception;
	
	public List<Vehicle> getVehicleListByVehicleGroupId(long vehicleGroupId, Integer companyId) throws Exception;
	
	public List<Vehicle> getVehicleListByCompanyId(Integer companyId) throws Exception;

	public void transferVehiclePhoto(List<VehiclePhoto> vehiclePhotoList) throws Exception;
	
	public long getVehiclePhotoMaxId() throws Exception;
	
	public void UpdateOdameterUpdateDate(Integer vid, Timestamp	date) throws Exception;
	
	public  VehicleDto getVehicleByNumber(String vehicleNumber, Integer companyId) throws Exception;
	
	public void updateLastLhpvSyncDateTime(Integer	vid, Timestamp	lastSyncDateTime) throws Exception;

	public ValueObject getVehicleCreationReport(ValueObject object)throws Exception;

	public List<VehicleDto> getVehicleCreationReportDetails(long cId, long vStatusId, String month) throws Exception;
	
	public ValueObject getVehicleWiseBatteryReport(ValueObject valueObject) throws Exception;
	
	public HashMap<String ,BatteryDto> getVehicleWiseBatteryMap(List<BatteryDto> Dtos) throws Exception;
	
	public ValueObject getVehicleWiseTyreReport(ValueObject valueObject) throws Exception;
	
	public HashMap<String ,InventoryTyreDto> getVehicleWiseTyreMap(List<InventoryTyreDto> Dtos) throws Exception;

	public ValueObject saveDriverMonthlySalary(ValueObject valueOutObject) throws Exception;
	
	public HashMap<Integer, Long>  getActiveVehicleCountMap() throws Exception;
	
	public long getActiveVehicleCount(int companyId) throws Exception;
	
	public List<VehicleDto> getActiveVehicleList(int companyId) throws Exception;

	public ValueObject getBankWiseVehicleEmiDetailsReport(ValueObject object)throws Exception;

	public ValueObject getVehicleWiseEmiDetailsReport(ValueObject object)throws Exception;

	public List<Vehicle> searchOnlyVehicleNameOnMobile(String vehicle,int companyId , long userId) throws Exception;

	public ValueObject getVehicleList(ValueObject object)throws Exception;
	
	public ValueObject saveVehicleKmplDetails(ValueObject object) throws Exception;

	public ValueObject getBusDetailsForFalconITSApi(ValueObject valueObject) throws Exception;

	public ValueObject saveBusDetailsForFalconITSApi(ValueObject valueObject) throws Exception;

	public ValueObject saveVehicleDetails(ValueObject valueInObject)throws Exception;

	public ValueObject updateVehicleDetails(ValueObject valueInObject)throws Exception;

	public ValueObject saveVehicleTollDetails(ValueObject valueInObject)throws Exception;
	
	public HashMap<Long, Long>	getVehicleAsinedTollCount(Integer companyId) throws Exception;
	
	public HashMap<Long, Long>	getVehicleAsinedGPSCount(Integer companyId) throws Exception;
	
	public ValueObject saveVehicleGPSAccount(ValueObject valueInObject)throws Exception;
	
	public ValueObject getVehicleDetailsOnTime(ValueObject valueObject) throws Exception;

	public ValueObject getVehicleRepairAndPartConsumptionDetails(ValueObject object)throws Exception;

	public ValueObject getVehicleDetailsFromVehicleGroup(ValueObject valueObject) throws Exception;

	public ValueObject getVehicleAutoCompleteByVehicleGroup(String search, String vGroup) throws Exception;
	
	public List<VehicleDto> getListOfNumberOfSRNotCreatedOnVehicles(String vids, Integer companyID) throws Exception;
	
	public List<VehicleDto> getListOfNumberOfRRNotCreatedOnVehicles(String vids, Integer companyID) throws Exception;
	
	public List<VehicleDto> getListOfNumberOfFENotCreatedOnVehicles(String vids, Integer companyID) throws Exception;
	
	public List<VehicleDto> listOfVehiclesWithoutMileage(Integer companyID) throws Exception;
	
	public List<VehicleDto> getActiveVehicleInDateRange(String fromDate, String toDate, Integer companyId, Long userId) throws Exception;
	
	public ValueObject getActiveVehicleListInRange(ValueObject valueObject) throws Exception;
	
	public List<VehicleDto> getActiveVehicleInDateRange(String query, Integer companyId, Long userId) throws Exception ;

	public List<VehicleDto> Get_Vehicle_Header_DetailsByGid(long int1)throws Exception;

	public ValueObject getActiveVehicleDetailsOfTheMonth(ValueObject valueObject) throws Exception;
	
	public Vehicle getLimitedVehicleDetails(Integer vid) throws Exception;
	public Integer updateCurrentOdoMeterGETVehicleToCurrentOdometer(int vid,int companyId) throws Exception;
	public VehicleDto Get_Vehicle_Header_Details(Integer vehicle_ID,Integer companyId) throws Exception;
	
	public List<Vehicle> getVehicleListByGpsVenordId(Integer gpsVendorId) throws Exception;

	public ValueObject getVehicleRouteChangeReport(ValueObject valueObject) throws Exception;

	public JSONArray getIntangleVehicleList(HashMap<String, Object> 	configuration)throws Exception;
	
	public JSONArray getIntangleByVehicleNumber(HashMap<String, Object> configuration, ValueObject valueObject)throws Exception;
	
	public List<Vehicle>  getVehicleByTypeAndModal(Long vehicleType, Long vehicleModelId, Integer companyId) throws Exception;

	public  void getServiceReminderListToSave(ServiceProgramSchedulesDto schedulesDto, Vehicle vehicle) throws Exception;
	
	public  void getServiceReminderListToSave(ServiceProgramSchedules schedulesDto, Vehicle vehicle) throws Exception;
	
	public void updateServiceProgramId(Long serviceProgramId, Integer vid) throws Exception;
	
	public void updateVehicleByServiceProgramId(Long oldServiceProgramId, Long newServiceProgramId, Integer companyId) throws Exception;
	
	public void updateVehicleServiceProgramStatus(Long vehicleTypeId, Long modal, Long serviceProgramId, Integer companyId) throws Exception;
	
	public List<Object[]> getVehicleIdByVehicleType(long typeId,Integer companyId,Integer branchId) throws Exception;
	
	public void prepareForInspectionSheetAssignForNewVid(int vid,Long vehicleTypeId,Integer branchId) throws Exception;
	
	public Long totalCountVehicles(int companyId) throws Exception ;

	public List<Vehicle> getVehicleByTypeModalAndBranch(Long vehicleType, Long vehicleModelId, Integer branchId,
			Integer companyId) throws Exception;

	public void updateBranchWiseVehicleServiceProgramStatus(Long vehicleTypeId, Long modal,Integer branchId,  Long serviceProgramId, Integer companyId) throws Exception;

	public ValueObject getVehicleListForCreateServiceProgram(ValueObject object)throws Exception;

	public List<Vehicle> getVehicleListForSR(String vids, Integer companyID) throws Exception;

	public ValueObject checkVehicleStatusByVid(ValueObject object)throws Exception;
	
	public ValueObject getIntangleByVehicleNumberFromMob(ValueObject valueObjectIn) throws Exception;
	
	public List<Vehicle> searchActiveVehicle(String term)throws Exception;

	public List<Vehicle> searchActiveVehicleByVehicleModel(String term, Long vehicleModelId)throws Exception;

	public Long checkVehicleByVehicleModel(Long vModelId, Integer companyId) throws Exception;
	
	public ValueObject getVehicleWiseTyreAssignReport(ValueObject valueObject) throws Exception;
	
	public ValueObject getVehicleExpenseDetails(ValueObject object);
	
	public ValueObject getVehicleIncidentReport(ValueObject object);
	
	public ValueObject getVehicleWiseIncidentDetails(ValueObject object) ;

	public List<SubCompanyDto> getSubCompany(Integer company_id) throws Exception;

	public void updateServiceReminder(ServiceProgramSchedulesDto schedulesDto, Vehicle vehicle, ServiceReminder sr);
	
	public Vehicle GetVehicleByRegNo(String registration) throws Exception;

	public ValueObject saveDriverMonthlyBhatta(ValueObject object) throws Exception;

}