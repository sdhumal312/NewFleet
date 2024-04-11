package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.CompanyDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.dto.VendorPaymentNotPaidDto;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.FuelDocument;
import org.fleetopgroup.persistence.model.FuelInvoice;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface IFuelService {

	public void addFuel(Fuel fuel);

	public void updateFuel(Fuel fuel);

	public Fuel getFuelList(Integer fuel);

	/** This Page get Fuel table to get pagination values */
	public Page<Fuel> getDeployment_Page_Fuel(Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	/** This List get Fuel table to get pagination last 10 entries values */
	public List<Fuel> listFuel(Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	/** This List get Fuel table to get pagination last 10 entries values */
	public List<FuelDto> listFuelEntries(Integer pageNumber, CustomUserDetails userDetails) throws Exception;
	
	public List<FuelDto> listFuelEntriesByTripSheetId(Long tripSheetId) throws Exception;
	
	/** This List get Fuel table to get pagination last 10 entries values */
	public List<FuelDto> listFuelEntriesBySP(Integer pageNumber, CustomUserDetails userDetails) throws Exception;


	
	public List<Fuel> listUPYesterdayFuel(Date yesterday) throws Exception;

	// Fuel validate of the vehicle Fuel
	public List<Fuel> listFuelValidate(Integer Vid, Integer odameter, Double liter, Double price, Integer vendor_id,
			String vendor_name, String refernceNumber) throws Exception;

	// Without vendor Id Fuel validate of the vehicle Fuel
	public List<Fuel> listFuelValidate(Integer Vid, Integer odameter, Double liter, Double price, String vendor_name,
			String refernceNumber, Integer companyId) throws Exception;

	public List<Fuel> listFuelValidate(String refernceNumber);

	public List<Fuel> listOfGetvehicelpartialFuel(Integer fuel_vehicleid, Integer fuel_tank);

	/**
	 * the value Fuel calculation using Odometer Ascending Order value in search
	 * v_id, current_odometer & fuel_date also
	 */
	public List<Fuel> findMissingOddmeterSecondASC_Vaule_Liter_Amount(Integer fuel_vehicleid, Integer fuel_tank,
			Date fuel_date);

	/**
	 * the value Fuel calculation using Odometer Ascending Order Delete value in
	 * search v_id, current_odometer & fuel_date also
	 */
	public List<Fuel> findMissingOddmeterSecondASC_Vaule_Liter_Amount_DELETE(Integer fuel_vehicleid, Integer fuel_tank,
			Date fuel_date);

	public void update_Missing_OddMeter_KM_cost(Integer OldFullTank_OpringKM, Integer OldFullTank_OpringKM_Usage,
			Long FuellFullTank_ID, Double km, Double cost);

	public void update_Missing_OddMeter_Usage_Partial(Integer OldFullTank_OpringKM, Integer OldFullTank_OpringKM_Usage,
			Long FuellFullTank_ID, Integer companyId);

	public void update_Missing_FuelNextFull_KM_cost(Long FuellFullTank_ID, Double km, Double cost, Integer companyId);

	// missing oddmeter value
	/** missing oddmeter value */
	/**
	 * the value Fuel calculation using Odometer descending Order value in
	 * search v_id, current_odometer & fuel_date also
	 */
	public List<Fuel> findMissingOddmeterFirstDESC_Vaule_Liter_Amount(Integer fuel_vehicleid, Integer fuel_Odd_Meter,
			Date fuel_date);

	/**
	 * the value Fuel calculation using Odometer descending Order Delete value
	 * in search v_id, current_odometer & fuel_date also
	 */
	public List<Fuel> findMissingOddmeterFirstDESC_Vaule_Liter_Amount_DELETE(Integer fuel_vehicleid,
			Integer fuel_Odd_Meter, Date fuel_date);

	/** The Value of Fuel Entries Get Fuel ID To details */
	public Fuel getFuel(Long fuel_id, Integer companyId) throws Exception;
	
	public FuelDto getFuelDetails(Long fuel_id, Integer companyId) throws Exception;

	public void deleteFuel(Long fuel, Timestamp toDate, long userId, Integer companyId);

	/* Update the partialFuel */
	public void updatepartialFuel(Integer fuel_vehicleid, Integer fuel_tank, Integer companyId);

	// Fuel vehicle Report
	public List<Fuel> listVehicleFuelReport(Integer vid);

	// Fuel Report
	public List<FuelDto> listFuelReport(String query,ValueObject object) throws Exception;

	public List<Double> ReportFuelTotalCount(String query) throws Exception;

	// Fuel Search
	public List<FuelDto> SearchFuel(String query, CustomUserDetails userDetails) throws Exception;

	public Long countFuel(Integer companyId) throws Exception;

	public Long countFuelTodayEntries(Date toDate, Integer companyId) throws Exception;

	// vendor CountTotal_ value
	public List<Double> listOFCountTotal_Cost_NotPaid(Integer vendorId, Integer companyId) throws Exception;

	//public List<Double> listOFCountTotal_Cost_Paid(String vendor_name) throws Exception;

	public List<Fuel> listFuel_vendor_History(String vendor_name, String fuel_payment) throws Exception;

	// payment vender to approval List
//	public List<Fuel> listFuel_vendor_APPROVALLIST(String vendor_name) throws Exception;

	public List<FuelDto> listFuel_vendor_APPROVALLISTFilter(Integer vendor_id, String ApprovalQuery, Integer companyId) throws Exception;

	public List<FuelDto> listFuel_vendor_Credit_To_NotPaid(Integer vendor_name, Integer companyId) throws Exception;

	public List<FuelDto> listFuel_vendor_Cash_To_Paid(Integer vendor_name, Integer companyId) throws Exception;

	//public List<Fuel> listFuel_vendor_To_Approval(String vendor_name) throws Exception;

	// Vendor Approval to Update the status and Approval id also
	public void update_Vendor_ApprovalTO_Status(String ApprovalFuel_ID, Long Approval_ID, String approval_Status)
			throws Exception;

	// Vendor Approval to Update the status and Approval id also
	public void update_Vendor_ApprovalTO_Status_MULTIP_FUEL_ID(String ApprovalFuel_ID, Long Approval_ID,
			short approval_Status, Integer companyId) throws Exception;

	public void update_Vendor_ApprovalTO_Status(Long ApprovalFuel_ID, Long Approval_ID, short approval_Status, Integer companyId)
			throws Exception;

	public void update_Vendor_ApprovalTO_Status_MUTIP_FUEL_ID(String ApprovalFuel_ID, Long Approval_ID,
			String approval_Status) throws Exception;

	/*public void update_Vendor_ApprovalTO_Status_PayDate(Long ApprovalFuel_ID, Date paymentDate, Long Approval_ID,
			String approval_Status) throws Exception;*/

	// update Approval Paid mode in Multiple Fuel IF
	public void update_Vendor_ApprovalTO_Status_PayDate_Multiple_fuel_ID(String ApprovalFuel_ID, Date paymentDate,
			Long Approval_ID, short approval_Status, Integer companyId, double paidAmount, double discountAmount) throws Exception;

	public List<Fuel> getVendorApproval_IN_FuelTable_List(Long VendorApproval_Id) throws Exception;
	public List<FuelDto> getVendorApproval_IN_FuelTable_List(Long VendorApproval_Id, CustomUserDetails userDetails) throws Exception;

	public List<FuelDto> Get_TripSheet_IN_FuelTable_List(Long Tripsheet_Id, int compId, long userId) throws Exception;

	// Fuel validate of the APPROVAL Status Entries to enter the values
	public List<Fuel> getFuelValidate_APPROVAL_Status(Long Fuel_ID, int companyId);

	// Fuel vehicle Graph show details
	public List<Fuel> GraphVehicleFuelReport(Integer vid);

	// Fuel vehicle wise Fuel Mileage Report
	public List<FuelDto> Vehicle_wise_Fuel_Mileage_Report(String DateRangeFrom, String DateRangeTo, String Multi_vid, Integer companyId) throws Exception;
	
	public ValueObject Group_wise_Fuel_Mileage_Report(String DateRangeFrom, String DateRangeTo, Long groupId, Integer companyId) throws Exception;
	
	// Fuel Vendor wise Fuel Mileage Report
	public List<FuelDto> Vendor_wise_Fuel_Mileage_Report(String Query, String Multi_vendor_id, Integer companyId) throws Exception;
	
	// Fuel Range with vehicle wise only full tank Report
	public List<FuelDto> Fuel_Range_with_Vehicle_wise__Report(String DateRangeFrom, String DateRangeTo, String Multi_vid, Integer companyId) throws Exception;
	
	public List<FuelDto> Fuel_Range_with_Vehicle_wise__ReportWithPartial(String DateRangeFrom, String DateRangeTo, String Multi_vid, Integer companyId) throws Exception;

	// Fuel vehicle wise Group_FuelRange Report
	public List<FuelDto> Vehicle_wise_Group_FuelRange_Report(String DateRangeFrom, String DateRangeTo,
			long VehicleGroupId, Integer companyId) throws Exception;
	
	public List<FuelDto> Vehicle_wise_Group_FuelRange_ReportWithPartial(String DateRangeFrom, String DateRangeTo,
			long VehicleGroupId, Integer companyId) throws Exception;

	/**
	 * This Interface Vendor Value is check vendor Id in Fuel Entries ARE not
	 * Checking
	 */
	public List<Fuel> Vendor_delete_ValidateIn_Fuel(Integer vendor_Id, Integer companyId) throws Exception;

	/** This Page get Fuel Vehicle List table to get pagination values */
	public Page<Fuel> getlistVehicleFuelReport_Page_Fuel(Integer vid, Integer pageNumber, Integer companyId);

	/**
	 * This List get Fuel Vehicle List table to get pagination last 10 entries
	 * values
	 */
	public List<FuelDto> listVehicleFuelReport(Integer vid, Integer pageNumber, Integer companyId) throws Exception;

	/**
	 * Update Vehicle Edit to Vehicle Group And Vehicle Ownership using vehicle
	 * ID
	 **//*
	public void update_Vehicle_Group_AND_Vehicle_Ownership(String Vehicle_Group, short Vehicle_Ownership,
			Integer vehicle_id, long vehicleGroupId);
*/
	/**
	 * @param dateFrom
	 * @param dateTo
	 * @param vehicle_id
	 * @return
	 */
	public List<Fuel> Vehicel_Fuel_Chart_InDate(String dateFrom, String dateTo, Integer vehicle_id);

	/** This Page get FuelVendor table to get pagination values */
	public Page<Fuel> getDeployment_Page_FuelVendor_Page(Integer pageNumber, Integer VendorId);

	/**
	 * This List get FuelVendor table to get pagination last 10 entries values
	 */
	public List<FuelDto> list_FuelVendor_ListOf_FuelEntries(Integer pageNumber, Integer VendorId) throws Exception;

	/**
	 * @param fuelDoc
	 */
	public void add_Fuel_To_FuelDocument(org.fleetopgroup.persistence.document.FuelDocument fuelDoc);

	/**
	 * @param fuel_id
	 * @return
	 */
	public org.fleetopgroup.persistence.document.FuelDocument get_Fuel_Document_Details(Long fuel_id, Integer companyId);

	/**
	 * @param fueldocid
	 * @param b
	 * @param fuel_id
	 */
	public void Update_FuelDocument_ID_to_Fuel(Long fueldocid, boolean b, Long fuel_id, Integer companyId);
	
	public FuelDto getFuelByNumber(Long fuel_Number, CustomUserDetails userDetails) throws Exception;
	
	
	public Long getTripSheetIdByNumber(long tripSheetId) throws Exception;

	/**
	 * Transfer all the document details from mysql to mongodb 
	 * @param list
	 * @throws Exception
	 */
	public void transferFuelDocument(List<FuelDocument> fuelDocumentList) throws Exception;
	
	public long getFuelDocumentMaxId() throws Exception;

	public List<FuelDto> getFuelConsumed(Integer driverId, String dateRangeFrom, String dateRangeTo) throws Exception;

	ValueObject getMonthlyVehicleWiseFuelReport(ValueObject valueInObject) throws Exception;
	
	List<FuelDto>  getFuelListByVidAndDateRange(TripSheetIncomeDto	incomeDto) throws Exception;

	public ValueObject getFuelRangeCashCreditWiseReport(ValueObject object) throws Exception;
	
	public ValueObject getFuelEfficiencyDataReport(ValueObject valueInObject) throws Exception;
	
	public HashMap<String, TripDailySheetDto> getNumberOfTripsByDate(List<TripDailySheetDto> Dtos) throws Exception;

	public Long getALLEmailFuelEntriesDailyWorkStatus(String startDate, String endDate, Integer companyId) throws Exception;

	public List<CompanyDto> getALLEmailFuelEntriesDailyWorkForManagers(String startDate, String endDate) throws Exception;

	public ValueObject getdateWiseFuelEntryDetailsReport(ValueObject object) throws Exception;

	public ValueObject getUserWiseFuelEntryDetailsReport(ValueObject object) throws Exception;
	
	public ValueObject saveFuelEntryDetails(ValueObject valueObject, MultipartFile uploadfile) throws Exception;
	
	public ValueObject saveVehicleProfitAndLossTxnChecker(ValueObject valueObject) throws Exception;
	
	public void saveFuelDocument(Fuel fuel, MultipartFile file) throws Exception;
	
	public Integer saveFuelEntriesToNewVendorCreateAuto(String FuelVendor, Integer companyId , Long userId) throws Exception;
	
	public Fuel saveFuel(ValueObject valueObject) throws Exception;
	
	public Fuel fuelMeterGreaterThanFuelMeterOld(ValueObject valueObject, Fuel fuel) throws Exception;
	
	public Fuel fuelMeterLessThanFuelMeterOld(ValueObject valueObject, Fuel fuel) throws Exception;
	
	public ValueObject getFuelEntriesResetProperties(Integer companyId) throws Exception;
	
	public ValueObject creationDateWiseFuelEntryReport(ValueObject object) throws Exception;
	
	public void deletePreviousVehicleFuelEntries(Fuel fuel) throws Exception;
	
	public void updateVidOfFuelEntries(long tripSheetId, int vid, int companyId) throws Exception;
	
	public HashMap<Integer, Long> getFuelEntriesCountHM(String startDate, String endDate) throws Exception;

	public List<FuelDto> getTripsheetfuelAmount(int int1, String fromDate, String toDate,
			CustomUserDetails userDetails, int tripFlavor) throws Exception;
	
	public List<FuelDto> getTripsheetfuelAmountByGroupId(Long int1, String fromDate, String toDate,
			CustomUserDetails userDetails, int tripFlavor) throws Exception;

	public List<FuelDto> getRouteWiseTripsheetfuelAmount(int routeID, String fromDate, String toDate,
			CustomUserDetails userDetails, int tripFlavor)throws Exception;
	
	public ValueObject getTripSheetWiseFuelEntryDetailsReport(ValueObject valueObject) throws Exception;
	
	public long todaysFuelEntriesCount (String startDate, String endDate, int companyId) throws Exception;
	
	public double todaysTotalFuelCost (String startDate, String endDate, int companyId) throws Exception;
	
	public double todaysTotalFuelLiter (String startDate, String endDate, int companyId) throws Exception;
	
	public double todaysAverageFuelPrice (String startDate, String endDate, int companyId) throws Exception;
	
	public List<FuelDto> getTotalFuelCreatedDetailsBetweenDates(String startDate, String endDate, Integer companyId) throws Exception; 
	
	public List<FuelDto> getTotalTripSheetFuelEntriesCreatedDetailsBetweenDates(String startDate, String endDate, Integer companyId)throws Exception;

	public ValueObject getFuelVehicleOdoMerete(ValueObject object)throws Exception;

	public ValueObject getFuelVendorList(ValueObject object)throws Exception;

	public ValueObject initializeFuelEntry(ValueObject object)throws Exception;

	public ValueObject getFuelDetailsForMobile(ValueObject object)throws Exception;

	public List<FuelDto> getRouteWiseTripsheetfuelExpense(String routeIDF, String fromDate, String toDate, CustomUserDetails userDetails, int tripFlavor)throws Exception;

	public ValueObject getDriverWiseFuelEntryReport(ValueObject object) throws Exception;

	public void updatePaymentApprovedFuelDetails(Long ApprovalInvoice_ID,
			Long Approval_ID, short approval_Status, Timestamp expectedPaymentDate, Integer companyId)throws Exception;//Approval Approved Here

	public void updatePaymentPaidFuelDetails(String serviceEntries_id, Timestamp paymentDate, Integer company_id, short fuel_vendor_paymodeId) throws Exception;//Approval Paid Here
	
	public Fuel	getLastFuelEntrieDetails(Integer vid, Timestamp fromDate) throws Exception;
	
	public List<TripSheetExpenseDto> getFuelListforTallyImport(String fromDate, String toDate, Integer companyId, String tallyCompany, String ledgerName) throws Exception;
	
	public Long	checkVehicleFirstFuelEntry(Integer vid) throws Exception;
	
	public ValueObject getActiveTripsheetDataAtTime(ValueObject object) throws Exception;
	
	public TripSheetDto getTripsheetDataAtTime (String tripActiveDateAndTime, int vid, Integer companyId) throws Exception;
	
	public VehicleDto getPreviousFuelEntryDetails(String query) throws Exception;
	
	public VehicleDto getNextFuelEntryDetailsInCaseOfSelectedBackDate(String query) throws Exception;
	
	public FuelDto getVehicleLastFuelDetailsByVidDate(String fuelDate, Integer vid)throws Exception;
	
	public List<FuelDto> validateFuelReferenceInFinancailYear(String fromDate, String toDate, String reference, Integer vendorId)throws Exception;
	
	public List<FuelDto> validateFuelReferenceInFinancailYear(String fromDate, String toDate, String reference, Integer vendorId, Long fuelId)throws Exception;
	
	public ValueObject getFuelDetailsById(ValueObject object) throws Exception;
	
	public ValueObject updateFuelEntries(ValueObject object) throws Exception;
	
	public Fuel getLastFullEntryDetail(Integer vid ,  String dateTime) throws  Exception;
	
	public List<Fuel> listOfGetvehicelpartialFuelBetween(Integer fuel_vehicleid,  String fromDate, String toDate, Long fuelId);
	
	public Fuel getnextFuelEntryOfType(Integer vid, String dateTime, Integer fuel_tank, Long fuelId) throws Exception;
	
	public void updateNextFuelCostKmpl(Long fuel_id, Double cost , Double kmpl , Integer usageKM, Long id, Timestamp dateTime,  Integer oldMeter)throws Exception;
	
	public void updateFuelUsageKm(Long fuel_id, Integer usageKM, Long  id, Timestamp updattime)throws Exception;
	
	public Fuel findMissingOddmeterFirstDESC_Vaule_Liter_AmountFuel(Integer fuel_vehicleid,
			Integer fuel_Odd_Meter, String fuel_date)  throws Exception;
	
	public List<DateWiseVehicleExpenseDto>	getDateWiseFuelExpenseDtoByVid(Integer vid, String fromDate, String toDate, Integer companyId) throws Exception;
	
	public List<MonthWiseVehicleExpenseDto>	getMonthWiseFuelExpenseDtoByVid(Integer vid, String fromDate, String toDate, Integer companyId) throws Exception;
	
	public List<FuelDto> getListOfNumberOfFECreatedOnVehicles(Integer companyID) throws Exception;
	
	
	public List<DateWiseVehicleExpenseDto>	getDateWiseFuelExpenseDtoByRouteId(Integer vid, String fromDate, String toDate, Integer companyId, Integer routeId) throws Exception;

	public List<DateWiseVehicleExpenseDto>	getDateWiseFuelExpenseDtoByVehicleTypeId(Integer vid, String fromDate, String toDate, Integer companyId, Long vehicleTypeId) throws Exception;

	public List<DateWiseVehicleExpenseDto>	getDateWiseFuelExpenseDtoByVTRouteId(Integer vid, String fromDate, String toDate, Integer companyId, Long vehicleTypeId, Integer routeId) throws Exception;
	
	public void deleteFuelByTripSheetId(Long tripSheetId) throws Exception;
	
	public List<TripSheetExpenseDto> getFuelListOutTripForTally(String fromDate, String toDate, Integer companyId, String tallyCompany, String ledgerName) throws Exception;
	
	public List<VendorPaymentNotPaidDto> vendorPaymentNotPaidFuelList(Integer vendor_id, String dateFrom, String dateTo, Integer companyId) throws Exception;

	
	public List<FuelDto> vehicleWiseFuelEntriesList(Integer vid, Integer companyId) throws Exception;
	
	public ValueObject searchFuelEntriesByVehicle(ValueObject object) throws Exception;

	public Fuel getFuelCreatedBetweenDates(String startDate, String endDate, Integer companyId) throws Exception;
	
	public HashMap<Integer, Long> getFuelEntriesCountHMOnCreated(String startDate, String endDate) throws Exception;
	
	public ValueObject updateFuelGPSUsage(ValueObject object) throws Exception;

	public ValueObject getMissingFuelEntryAlertByCompanyId(Integer company_id)throws Exception;
	
	public FuelDto	getTripsheetFuelMileage(Long	tripSheetId, Integer companyId) throws Exception;
	
	public ValueObject searchFuelEntriesByVehicleNumberAndDateRange(ValueObject object) throws Exception;

	public Fuel getfueldetailsbyfuelInvoiceId(Long fuelInvoiceId, Integer companyId)throws Exception;
	
	public ValueObject getallVehiclesMileageReport(ValueObject valueObject) throws Exception;
	
	public void updateVendorPaymentDetails(Long approvalId, String	paymentDate, short paymentStatus) throws Exception;
	
	public void updateVendorPaymentCancelDetails(Long invoiceId, Long approvalId, Date	paymentDate, short paymentStatus) throws Exception;
	
	public List<FuelDto> getUserWiseFEActivityList( String queryStr ,String innerQuery) throws Exception;

	public void saveIntangleFuelPullAPI() throws Exception;
	
	public ValueObject prepareInvoiceWiseFuelEntry(ValueObject valueObject,MultipartFile uploadfile,CustomUserDetails userDetails,HashMap<String, Object> config) throws Exception ;
	
	public double eachPriceForFuleInvoice(FuelInvoice fuelInvoice) throws Exception;
	
	public List<Fuel> getDriverWiseFuelList(int driverId,String queryStr,int companyId);
	
	public List<TripSheetExpenseDto> getFuelListWithoutApprovalForTally(String fromDate, String toDate, Integer companyId, String tallyCompany, String ledgerName) throws Exception;

	public ValueObject saveTripsheetFuelDetailsFromClosTripApi(ArrayList<ValueObject> fulearray, ValueObject object, Vehicle vehicle)throws Exception;

	public Fuel 	getFuelMeterByTripsheetIdByDateTime(Long Tripsheet_Id, Integer companyId) throws Exception;

	public Fuel getFuelMeterByDateTime(Integer vid, Integer companyId) throws Exception;
	
}
