package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RouteWiseTripSheetWeightDto;
import org.fleetopgroup.persistence.dto.TripSheetAdvanceDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.TripSheetOptionsDto;
import org.fleetopgroup.persistence.dto.TripsheetDueAmountDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.RouteWiseTripSheetWeight;
import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetAdvance;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.TripSheetExtraReceived;
import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.fleetopgroup.persistence.model.TripSheetOptionsExtra;
import org.fleetopgroup.persistence.model.Vehicle;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ITripSheetService {

	public TripSheet  addTripSheet(TripSheet TripSheet) throws Exception;

	public void addTripSheetAdvance(TripSheetAdvance TripSheetAdvance) throws Exception;

	public TripSheetExpense addTripSheetExpense(TripSheetExpense TripSheetExpense) throws Exception;
	
	public void addAllTripSheetExpense(List<TripSheetExpense> TripSheetExpense) throws Exception;

	public void addTripSheetIncome(TripSheetIncome TripSheetIncome) throws Exception;

	public List<TripSheetExpense> getTripSheetExpenseList(Long tripSheetID,int companyId) throws Exception;
	
	public List<TripSheetAdvance> getTripSheetAdvanceList(Long tripSheetID,int companyId) throws Exception ;
	
	public List<TripSheetAdvanceDto> findAllTripSheetAdvance(Long tripSheetID, Integer companyId) throws Exception;

	public List<TripSheetExpenseDto> findAllTripSheetExpense(Long tripSheetID, Integer companyId) throws Exception;
	
	public List<TripSheetExpenseDto> findAllTripSheetExpense(String fromDate, String toDate, Integer companyId, String tallyCompany) throws Exception;
	
	public List<TripSheetExpenseDto> findAllTripSheetExpenseForTally(String fromDate, String toDate, Integer companyId, 
										String tallyCompany, String ledgerName, Long tallyId) throws Exception;

	public List<TripSheetIncome> findAllTripSheetIncomeList(Long tripSheetID,int companyId) throws Exception;
	
	public List<TripSheetIncomeDto> findAllTripSheetIncome(Long tripSheetID, Integer companyId) throws Exception;
	
	public List<TripSheetIncomeDto> findAllTripSheetIncomeForPL(Long tripSheetID, Integer companyId) throws Exception;

	public List<TripSheetExpense> ValidateAllTripSheetExpense(Integer ExpenseName, Long tripSheetID, Integer companyId) throws Exception;

	public List<TripSheetIncome> ValidateAllTripSheetIncome(Integer IncomeName, Long tripSheetID, Integer companyId) throws Exception;

	public void updateTripSheet(TripSheet TripSheet) throws Exception;

	public void updateCloseTripSheet(Long TripSheetID, Integer CloseingKm, Integer UsageKM, short CloseingKmStatus,
			short paidto, String CloseRefenceNo, Double amount, Long paidby, Date Closed_data, Long LASTUPDATED,
			java.util.Date LASTUPDATED_DATE, Long ClosedBy, Integer ClosedLocation, java.util.Date ClosedByTime,
			Integer companyId, Double closingGPSKM, String gpsClosingLocation, Double gpsUsageKM, Double driverBalance)
			throws Exception;

	public void updateTripSheetAdvance(TripSheetAdvance TripSheetAdvance) throws Exception;

	public void UPDATE_TOTALADVANCE_IN_TRIPSHEET_TRIPSHEETADVANCE_ID(Long tripSheetID, Integer companyId) throws Exception;

	public void UPDATE_TOTALEXPENSE_IN_TRIPSHEET_TRIPSHEETEXPENSE_ID(Long tripSheetID, Integer companyId) throws Exception;

	public void UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(Long tripSheetID, Integer companyId) throws Exception;
	
//	public void UPDATE_TOTALINCOME_IN_TRIPSHEET_TRIPSHEETINCOME_ID(Long tripSheetID, Integer companyId) throws Exception;

	public void updateCloseAccountTrip(Long closeAccountBY, short closeAccountStatus, Double amount,
			String closeReference, Timestamp toDate, Long LASTUPDATED, Timestamp LASTUPDATED_DATE, Long tripSheetID, Integer companyId, Integer acclocation)
			throws Exception;

	public TripSheet getTripSheet(Long TripSheet_id) throws Exception;
	
	public TripSheet getTripSheetData(Long TripSheet_id, int compId) throws Exception;
	
	public TripSheet getTripSheetDetails(Long TripSheet_id) throws Exception;
	
	public TripSheetDto getTripSheetDetails(Long TripSheet_id, CustomUserDetails companyId) throws Exception;
	
	public TripSheet getTripSheet_ID_FUEL_SHOW_NUMBER(Long TripSheet_id) throws Exception;
	
	public TripSheetDto getTripSheetByNumber(Long TripSheet_id, CustomUserDetails	userDetails) throws Exception;

	public TripSheetDto Get_Vehicle_Current_TripSheet_Id(Integer vehicle_ID, CustomUserDetails	userDetails) throws Exception;
	
	public List<TripSheetDto> Get_Vehicle_Current_TripSheet_Id(Integer vehicle_ID, Integer companyId) throws Exception;
	
	public List<TripSheetDto> getTripSheetDetailsOfSelectedDate(String Date, Integer companyId) throws Exception;
	
	public ValueObject getTripSheetDataOnDateRange(ValueObject valueObjectIn) throws Exception;
	
	public ValueObject closeAccountOfSelectedTripSheet(ValueObject valueObjectIn) throws Exception;
	

	
	public TripSheetAdvance getTripSheetAdvance(Long TripSheet_Advanceid, Integer companyId) throws Exception;

	public TripSheetExpense getTripSheetExpense(Long TripSheet_Expenseid, Integer companyId) throws Exception;
	
	public TripSheetExpense getTripSheetExpenseByFuelId(Long fuelId) throws Exception;
	
	public TripSheetOptionsExtra getTripSheetExtra(Long tripExtraID, Integer companyId) throws Exception;

	public TripSheetIncome getTripSheetIncome(Long TripSheet_Incomeid, Integer companyId) throws Exception;

	public List<TripSheet> findAllTripSheetList() throws Exception;

	public List<TripSheet> listTripSheet(CustomUserDetails	userDetails) throws Exception;

	public List<TripSheetDto> listTripSheet(String ReportQuery) throws Exception;
	

	public List<TripSheetDto> list_Report_TripSheet_TripAdvance(String ReportQuery, CustomUserDetails	userDetails) throws Exception;

	public List<TripSheetDto> list_Report_Daily_TripSheet_Report(String ReportQuery, Integer companyId) throws Exception;

	public List<TripSheetDto> list_Report_TripSheet_DATE(String TRIP_DATE_RANGE, String TripsheetGroup, Integer companyId) throws Exception;

	public List<TripSheetDto> list_Report_TripSheet_HALT_AMOUNT(String ReportQuery, CustomUserDetails	userDetails) throws Exception;

	public List<Double> ReportTripAdvanceTotal(String ReportQuery) throws Exception;

	public List<Double> ReportTripExpenseTotal(String ReportQuery) throws Exception;

	public List<Double> ReportTripIncomeTotal(String ReportQuery) throws Exception;

	public List<Double> ReportTripAccBalanceTotal(String ReportQuery) throws Exception;

	public List<TripSheetDto> listTodayTripSheet(String date, CustomUserDetails userDetails) throws Exception;

	public List<TripSheetDto> listTripSheetDispatch(CustomUserDetails userDetails) throws Exception;

	public List<TripSheetDto> listTripSheetManage(CustomUserDetails userDetails) throws Exception;

	/** This Page get TripSheet table to get pagination values */
	public Page<TripSheet> getDeployment_Page_TripSheet(short status, Integer pageNumber, CustomUserDetails	userDetails) throws Exception;

	/** This Page get TripSheet table to get pagination values */
	public List<TripSheetDto> listTrip_Page_Status(short status, Integer pageNumber, CustomUserDetails	userDetails) throws Exception;

	public List<TripSheetDto> listTripSheetClose(CustomUserDetails userDetails) throws Exception;

	public List<TripSheet> listTripSheetCloseAccount() throws Exception;

	public void deleteTripSheet(Long Approval_ID, Integer companyId , Long userId,Timestamp date) throws Exception;

	public void deleteTripSheetAdvance(Long TripSheet_Advanceid, Integer companyId) throws Exception;

	public void deleteTripSheetExpense(Long TripSheet_Expenseid, Integer companyId) throws Exception;
	
	public void deleteTripSheetExtra(Long tripExtraID, Integer companyId) throws Exception;

	public void deleteTripSheetIncome(Long TripSheet_Incomeid, Integer companyId) throws Exception;
	
	public void deleteTripSheetIncomeById(Long TripSheet_Incomeid, Integer companyId) throws Exception;

	public Long countTripSheet(CustomUserDetails userDetails) throws Exception;

	public Long countTripSheetToday(String Today) throws Exception;

	// search Trip Sheet
	public List<TripSheetDto> SearchTripSheet(String Search) throws Exception;

	public List<TripSheetDto> Search_TripSheet_Status(String vid, short status) throws Exception;

	// inside vehicle List
	public List<TripSheetDto> VehicleTOlistTripSheet(Integer vid, Integer pageNumber) throws Exception;

	/**
	 * Update Vehicle Edit to Vehicle Group using vehicle Id
	 **/
	//public void update_Vehicle_Group_USING_Vehicle_Id(String Vehicle_Group, Integer vehicle_id, long vehicleGroupId);

	/**
	 * @param tripSheetID
	 */
	public void Delete_TSID_to_TripSheetAdvance(Long tripSheetID, Integer companyId);

	/**
	 * @param tripSheetID
	 */
	public void Delete_TSID_to_TripSheetExpence(Long tripSheetID, Integer companyId);

	/**
	 * @param tripSheetID
	 */
	public void Delete_TSTD_to_TripSheetIncome(Long tripSheetID, Integer companyId);

	/**
	 * @param vehicleID
	 * @return
	 */
	public List<TripSheetDto> Get_Validate_TripSheet_Vehicle_DriverAcount(Integer vehicleID, Integer companyId);
	
	public List<TripSheetDto> Get_Validate_TripSheet_Vehicle(String vehicleID, Integer companyId);

	/**
	 * @param tripSheet_ID
	 */
	public void update_Total_TripSheet_comment_Add(Long tripSheet_ID, Integer companyId);

	/**
	 * @param tripSheet_ID
	 */
	public void update_Total_TripSheet_comment_SubTrack(Long tripSheet_ID, Integer companyId);

	/**
	 * @param parseDouble
	 *            FIRST DRIVER BATA DETAILS TO UPDATE TRIP AMOUNT
	 */
	public void Update_TripSheet_FIRST_Driver_BATA(double parseDouble, Long tripSheetID, Integer companyId);

	/**
	 * @param parseDouble
	 * @param tripSheetID
	 *            SECOND DRIVER BATA DETAILS TO UPDATE TRIP AMOUNT
	 */
	public void Update_TripSheet_SECOND_Driver_BATA(double parseDouble, Long tripSheetID, Integer companyId);

	/**
	 * @param parseDouble
	 * @param tripSheetID
	 *            CLEANER BATA DETAILS TO UPDATE TRIP AMOUNT
	 */
	public void Update_TripSheet_CLEANER_BATA(double parseDouble, Long tripSheetID, Integer companyId);

	/**
	 * @param driver_id
	 * @param driverStart
	 * @param driverEnd
	 * @return
	 */
	public List<TripSheetDto> list_Of_DriverID_to_Driver_BATA_Details(Integer driver_id, String driverStart,
			String driverEnd, Integer companytId);

	/**
	 * @param tripSheetID
	 * @return
	 */
	public TripSheetDto Get_FuelVendor_SearchTo_TripSheetDetailsIN_Select(Long tripSheetID, Integer companyId);
	

	/**
	 * @param tRIP_DATE_RANGE
	 * @return
	 */
	public List<TripSheetDto> list_Report_TripSheet_Collection_DATE_Report(String tRIP_DATE_FROM, String tRIP_DATE_TO,
			String TripsheetGroup, Integer companyId) throws Exception;

	/**
	 * @param dhid
	 */
	public void DELETE_TRIPSHEETEXPENSE_DRIVERHALT_ID(Long dhid, Integer companyId);

	/**
	 * @param fuel_id
	 */
	public void DELETE_TRIPSHEETEXPENSE_FUEL_ID(Long fuel_id);

	public Object[] count_TOTAL_TRIPSHEET_FIVEDAYS(String dayOne, String dayTwo, String dayThree, String dayFour,
			String dayFive, CustomUserDetails userDetails) throws Exception;

	public List<TripSheetDto> list_Report_TripSheet_Difference_Km_Liter_List(String query, String dateRangeFrom,
			String dateRangeTo, CustomUserDetails	userDetails) throws Exception;
	
	public Object[] getDriverAdvanceAndBataExpenseId(Integer companyId) throws Exception;
	
	public Object[] getExpenseIdWithName(Integer companyId) throws Exception;
	
	public int countTripSheetByNumber(Long tripSheetNumber , Integer companyId) throws Exception;
	
	public List<TripSheetDto> Get_TripSheetListByNumber(Long vehicle_ID, Integer companyId) throws Exception;

	/**
	 * Method For Get Trip Sheet Report By Route Wise
	 * @param valueInObject
	 * @return
	 * @throws Exception
	 */
	public ValueObject getRouteWiseTripSheetReport(ValueObject valueInObject) throws Exception;

	public List<TripSheetOptionsExtra> ValidateAllTripSheetExtraOptions(Long tripsheetextraname, Long tripSheetID, Integer companyId)
			throws Exception;

	public void addTripSheetExtraOption(TripSheetOptionsExtra tripExtraOptions) throws Exception;

	public List<TripSheetOptionsDto> findAllTripSheetExtraOptions(Long tripSheetID, Integer company_id) throws Exception;

	public List<TripSheetExtraReceived> ValidateAllTripSheetExtraOptionsReceived(Long tripsheetextraname, Long tripSheetID,
			Integer companyId) throws Exception;
	
	public void addTripSheetExtraOptionReceived(TripSheetExtraReceived tripSheetExtraReceived) throws Exception;

	public List<TripSheetOptionsDto> findAllTripSheetExtraOptionsReceived(Long tripSheetID, Integer companyId)
			throws Exception;
	
	public TripSheet getTripSheet(Long TripSheet_id, Integer companyId) throws Exception ;
	
	public TripSheetExpense getTripSheetExpensebyExpenseId(Long tripExpenseId, Integer companyId, Long tripSheetID) throws Exception;

	public TripSheetIncome	getTripSheetIncomeByTripIdAndIncomeId(Long tripSheetID, Integer expenseid) throws Exception;
	
	public List<TripSheetDto>  getTripSheetDetailsInMonthByVid(Integer	vid, Timestamp fromDate, Timestamp	toDate) throws Exception;
	
	public List<TripSheetDto>  getTripSheetDetailsInMonthByRouteId(Integer	vid, Timestamp fromDate, Timestamp	toDate, Integer routeId) throws Exception;
	
	public List<TripSheetDto>  getTripSheetDetailsInMonthByVehicleGroupId(long	vehicleGroupId, Timestamp fromDate, Timestamp	toDate) throws Exception;
	
	public Integer  getVehicleRunKMByVid(Integer	vid, Timestamp fromDate, Timestamp	toDate) throws Exception;
	
	public HashMap<Integer, Long>  getVehicleRunKMByGroupId(Long groupId, Timestamp fromDate, Timestamp	toDate) throws Exception;

	public List<TripSheetDto> getDriverPerformanceOnTrip(Integer driverId, String dateRangeFrom, String dateRangeTo) throws Exception;
	
	public List<TripSheetIncomeDto> getVehicleIncomeDetailsOfMonthByIncomeId(TripSheetIncomeDto incomeDto) throws Exception;
	
	public List<TripSheetExpenseDto> getVehicleExpenseDetailsOfMonthByExpenseId(TripSheetIncomeDto incomeDto) throws Exception;
	
	public List<TripSheetExpenseDto> getVehicleFastTagDetailsOfMonthByExpenseId(TripSheetIncomeDto incomeDto) throws Exception;
	
	public Double	getGPSUsageKM(HashMap<String,  Object> configuration, ValueObject	valueObject) throws Exception;

	public Long getALLEmailTripDailyWorkStatus(String startDate, String endDate,Integer companyId) throws Exception;
	
	public HashMap<Integer, Long> getALLEmailTripDailyWorkStatusHM(String startDate, String endDate,Integer companyId) throws Exception;
	
	public ValueObject updateTripSheetClosingKM(ValueObject	valueObject) throws Exception;
	
	public HashMap<Integer,TripSheetExpenseDto> findAllTripSheetExpenseCombineMap (List<TripSheetExpenseDto> expenseList) throws Exception;
	
	public ValueObject findAllTripSheetExpenseCombineList(ValueObject valueInObject) throws Exception;
	
	public void saveTripAdvanceList(List<TripSheetAdvance>   advances) throws Exception;
	
	public void saveTripIncomeList(List<TripSheetIncome> incomes) throws Exception; 

	public TripSheetDto getTripClosed(String startDate, String endDate, Integer companyId) throws Exception; 
	
	public Long getTripAccountClosedCount(String startDate, String endDate,Integer companyId) throws Exception;
	
	public Long getAllTripSheetCount(String startDate, Integer companyId) throws Exception;
	
	public Long getTripMissedClosing(String startDate, String endDate,Integer companyId) throws Exception;
	
	public Long getTodaysTripOpenStatusCount(String startDate, String endDate, Integer companyId) throws Exception; 

	public Long getTripSheetDispatchedCount(String endDate, Integer companyId) throws Exception;
	
	public Long getTripSheetSavedCount(String endDate, Integer companyId) throws Exception;

	public TripSheetDto getTripOldestOpenFlavorOne(Integer companyId, String startDate) throws Exception; 

	public Long getTripInOpenState(String startDate, String endDate, Integer companyId) throws Exception;
	
	public TripSheetDto getTripIncomeAndExpense (String startDate, String endDate,Integer companyId) throws Exception;
	
	public long getTripTotalRunKM(String startDate, String endDate,Integer companyId) throws Exception;
	
	public ValueObject getCreateDayWiseExpenseReport(ValueObject object) throws Exception; 
	
	public ValueObject getVoucherDateWiseExpenseReport(ValueObject object) throws Exception; 
	
	public void updateBookRefToLhpvIntripSheet(String bookRef, Long tripSheetId) throws Exception;
	//public ValueObject saveLoadTypes(ValueObject object) throws Exception;   //newy
	
	public void updatePODDetails(Long tripSheetID, int noOfPOD, int companyId) throws Exception;
	
	public void updateReceivedPODDetails(Long tripSheetID, int receivedPOD) throws Exception;
	
	public void updateFirstDriverRoutePoint(Long tripSheetID, double routePoint, int companyId) throws Exception;
	
	public void updateSecDriverRoutePoint(Long tripSheetID, double routePoint, int companyId) throws Exception;
	
	public void updateCleanerRoutePoint(Long tripSheetID, double routePoint, int companyId) throws Exception;
	
	ValueObject	getIVLoadingSheetDataForTrip(ValueObject	valueObject) throws Exception;
	
	public void addTripSheetIncomeList(List<TripSheetIncome> TripSheetIncome,ValueObject valueObject) throws Exception;
	
	public List<Long> findAllTripSheetIncomeForLS(Long tripSheetID, Integer	companyId) throws Exception;
	
	ValueObject	getLoadingSheetIncomeDetails(ValueObject	valueObject) throws Exception;
	
	List<TripSheetDto>  getRecentTripListByVid(Integer	vid, Date uptoDate) throws Exception;
	
	public void deletePreviousVehicleTripSheetIncome(TripSheetIncome tripIncome, Integer oldVid) throws Exception;
	
	public void saveVehicleProfitAndLossTxnCheckerForTripSheetIncome(ValueObject valueObject) throws Exception;
	
	public void deletePreviousVehicleTripSheetExpense(TripSheetExpense tripSheetExpense, Integer oldVid) throws Exception;
	
	public void saveVehicleProfitAndLossTxnCheckerForTripSheetExpense(ValueObject valueObject) throws Exception;
	
	public HashMap<Integer, Long> getTripSheetCountHM(String startDate, String endDate) throws Exception;
	
	TripSheetDto	getHighyestKMRunDetails(String startDate, String endDate,Integer companyId) throws Exception;
	
	public List<TripSheetDto> getTotalTripSheetCreatedDetailsBetweenDates(String startDate, String endDate, Integer companyId) throws Exception;
	
	public List<TripSheetDto> getTotalTripSheetOpenDetailsBetweenDates(String startDate, String endDate, Integer companyId) throws Exception;
	
	public List<TripSheetDto> getTotalTripSheetClosedDetailsBetweenDates(String startDate, String endDate, Integer companyId) throws Exception;

	public List<TripSheetDto> getTotalTripSheetAcntClosedDetailsBetweenDates(String startDate, String endDate, Integer companyId) throws Exception;
	
	public List<TripSheetDto> getTotalRunKmList(String startDate, String endDate, Integer companyId) throws Exception;
	
	public Long getTripSheetCreatedCount(String startDate, String endDate,Integer companyId) throws Exception;
	
	public ValueObject getToDaysTripSheetList(ValueObject	valueObject) throws Exception;
	
	public ValueObject getDispatchTripSheetList(ValueObject	valueObject) throws Exception;
	
	public ValueObject getManageTripSheetList(ValueObject	valueObject) throws Exception;
	
	public ValueObject getAdvCloseTripSheetList(ValueObject	valueObject) throws Exception;
	
	public ValueObject getTripSheetAccountList(ValueObject	valueObject) throws Exception;
	
	public ValueObject getTripSheetAccountCloseList(ValueObject	valueObject) throws Exception;
	
	public List<TripSheetDto> getTodaysTripOpenStatusList(String startDate, String endDate, Integer companyId) throws Exception;
	
	public List<TripSheetDto> getTripSheetDispatchedOverdueList(String endDate, Integer companyId) throws Exception;
	
	public List<TripSheetDto> getTripSheetSavedOverdueList(String endDate, Integer companyId) throws Exception;

	public ValueObject getRecentTripListByVehicleId(ValueObject object)throws Exception;

	public void updateTotalNetIncomeInTripsheet(Long tripSheetID, Integer company_id)throws Exception;

	public ValueObject getTripsheetByTripsheetId(ValueObject object)throws Exception;

	public ValueObject saveDriverPenalty(ValueObject object)throws Exception;

	public ValueObject getDriverPenaltyByTripsheetId(ValueObject object)throws Exception;

	public ValueObject deleteDriverPenalty(ValueObject object)throws Exception;
	
	public TripSheetDto getLastTripSheetDetails(Integer vid , String date) throws Exception;
	
	public void removeTripSheetFuel(Long fuel_id)throws Exception;
	
	public void updateBalanceFuel(Long tripSheetId, Double balanceFuel)throws Exception;
	
	public void saveVoucherDate(ValueObject object) throws Exception; 
	
	public ValueObject getDriverDetailsFromItsGatewayApi(ValueObject valueObject) throws Exception;
	
	public ValueObject getVehicleTripSheetDetails(ValueObject	valueObject) throws Exception;
	
	public ValueObject saveDueAmount(ValueObject valueObject) throws Exception;
	
	public ValueObject getDueAmountList(ValueObject valueObject) throws Exception;
	
	public List<TripsheetDueAmountDto> getDueAmountListByTripsheetId(Long tripsheetId, int companyId);
	
	public double getTotalDueAmountByTripsheetId (Long tripsheetId, int companyId) throws Exception;
	
	public ValueObject removeDueAmount(ValueObject valueObject) throws Exception;
	
	public ValueObject getDueAmountReport(ValueObject valueObject) throws Exception;
	
	public ValueObject getDueAmountPaymentById (ValueObject valueObject) throws Exception;
	
	public ValueObject saveDueAmountPaymentDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject saveDueAmountPaymentByAmount(ValueObject valueObject) throws Exception;
	
	public ValueObject saveDueAmountPaymentByExpense(ValueObject object) throws Exception;
	
	public ValueObject getDueAmountPaymentReport(ValueObject valueObject) throws Exception;
	
	public ValueObject updateExpenseRemark(ValueObject	valueObject) throws Exception;
	
	List<TripSheetDto>  getTripSheetForDate(Integer vid, String fromDate, String toDate) throws Exception;
	
	ValueObject  getTripSheetDataForRefreshmentAdd(ValueObject	valueObject) throws Exception;
	
	TripSheetDto getLastClosedTripSheetByDateTime(Integer vid, String fromDate)throws Exception;
	
	public TripSheetDto getTripSheetLimitedDetails(Long TripSheet_id) throws Exception;
	
	public ValueObject	getLastNextTripSheetDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getLastNextTripSheetDetailsForEdit(ValueObject valueObject) throws Exception;
	
	public TripSheetDto getNextClosedTripSheetByDateTime(Integer vid, String fromDate) throws Exception;
	
	public Long getTripSheetIdByNumber(Long tripsheetNumber, Integer companyId, Long userId) throws Exception;

	public ValueObject updateTripSheetOpeningKM(ValueObject valueObject) throws Exception;
	
	public Long getAllRouteCount(String startDate, String toDate, Integer vid) throws Exception;
	
	public void deleteDueAmountByTripSheetId(Long tripsheetId) throws Exception;
	
	public void superUserdeleteTripSheet(Long tripSheetID, Long userId, String remark) throws Exception;
	
	List<TripSheetDto>  searchTripSheetNumberList(Integer	companyId, String term) throws Exception;

	public void deleteFuelExpenseByTripId(Long tripsheetId) throws Exception;
	
	ValueObject updateTallyCompanyToTripSheet(ValueObject valueObject) throws Exception;

	public TripSheetDto getInBetweentimeTripSheetByDateTime(Integer vid, String query) throws Exception;

	public TripSheetDto getLastClosedTripSheetByDateTimeById(Integer vid, String fromDate, Long tripSheetId)throws Exception;
	
	public TripSheetDto getNextClosedTripSheetByDateTime(Integer vid, String fromDate, Long tripSheetId)throws Exception;

	public TripSheet getTripSheetCreatedBetweenDates(String startDate, String endDate, Integer companyId) throws Exception;

	public ValueObject updateTripSheetUsageKM(ValueObject object)throws Exception;

	public void getVehicleGpsDetailAndUpdateTripSheetUsageKM()throws Exception;

	public List<TripSheetDto> getTotalGpsTripSheetClosedDetailsBetweenDates(String startDate, String endDate,
			Integer companyId) throws Exception;

	public void saveTripExpenseList(List<TripSheetExpense> expense) throws Exception;
	
	public TripSheetDto getLastTripSheetLimitedData(Integer vid, String fromDate, Long tripSheetId) throws Exception;
	
	public ValueObject updateTripRoutePoint(ValueObject object)throws Exception;
	
	public ValueObject getPreviousDueAmountData(ValueObject valueObject) throws Exception;
	
	public ValueObject getPreviousDueAmountDataList(ValueObject valueObject) throws Exception;
	
	public TripSheetDto getSavedTripSheetByVid(Integer vid) throws Exception;
	
	public TripSheetDto getInBetweenTripSheetDetails(Integer vid, String dispatchByTime) throws Exception;

	public void updateCloseTripAmount(long tripsheetId, double tripcloseAmount, Integer companyId)throws Exception;
	
	public List<TripSheetDto>  getNextTripSheetsByVidDate(Integer vid, String dispatchByTime) throws Exception;
	
	public void updateOpeningClosingKM(Long tripSheetId, Integer openingKM, Integer closingKM) throws Exception;
	
	public TripSheetDto getLimitedTripSheetDetails(Long tripSheetId) throws Exception;
	
	public XSSFSheet getHssfSheet(DataValidationHelper validationHelper, XSSFSheet hssfSheet)throws Exception;

	public ValueObject addTripsheetExcel(Row myrow)throws Exception;

	public void saveTripsheetExcel(XSSFWorkbook mySheet)throws Exception;

	public ValueObject addTripsheetExpenseExcel(TripSheet tripsheet, Row myrow) throws Exception;
	public ValueObject addTripsheetIncomeExcel(TripSheet tripsheet, Row myrow) throws Exception;

	public List<TripSheetDto> getCreatedTripsheetDetails(Integer companyId) throws Exception;
	
	ValueObject getTripSheetDetailsByNumber(String vehicleNumber) throws Exception;
	
	public TripSheetDto  getActiveTripSheetByVid(Integer vid) throws Exception;

	public ValueObject getTripSheetDetailsAtTime(ValueObject valueObject) throws Exception;
	
	public ValueObject	getLastTripsheetFuelMileage(ValueObject valueObject) throws Exception;
	
	public Long  getLastTripSheetIdOfDriver(Integer	driverId) throws Exception;
	
	public List<TripSheetDto> getUserWiseTSActivityList(String queryStr ,String innerQuery) throws Exception;

	public void saveIntangleTripPullAPI() throws Exception;
	
	public ValueObject getAllGroupTripsheetDateReport(ValueObject valueObject) throws Exception ;
	
	public ValueObject getTripsheetDetailsithoutLoadingSheet(ValueObject valueObject) throws Exception ;

	public Long getLastTripSheetFromGivenDate(java.util.Date date,int companyId,int driverId);

	public ValueObject showLSDetails(ValueObject valueObject) throws Exception ;
	
	public void updateDriverInTripSheetAdvance(int driverId,long tripSheetId,int companyId) throws Exception;
	
	public void updateDriverInTripSheetExpense(int driverId,long tripSheetId,int companyId) throws Exception;
	
	
	public void updateTripSheetStatusToClose(Short closeAccountBY, short closeAccountStatus, Double amount, 
			String closeReference, Timestamp toDate, Long LASTUPDATED, Timestamp LASTUPDATED_DATE, Long tripSheetID, 
			Integer companyId, Integer acclocation) throws Exception;
	
	public List<TripSheetExpenseDto>  getDriverBalanceListForTally(Integer companyId, String fromDate, String toDate, 
																		Long tallyCompany, String debitLedger, String tallyName) throws Exception;

	public ValueObject getTripSheetDetailsByVehicleNoAndTripOpenCloseDate(String vehicleNumber , String date) throws Exception;

	public void addTripSheetRouteWiseWeight(RouteWiseTripSheetWeight tripWeight)throws Exception;

	List<RouteWiseTripSheetWeightDto> findAllTripSheetRouteWiseDetails(Long tripSheetID, Integer companyId) throws Exception;

	public void deleteTripSheetRouteWiseWeight(Long routeWiseWeightId, Integer companyId) throws Exception;

	public List<Driver> getTripDriverList(Integer company_id, Long parseLong)throws Exception;

	public Double getDriverBalance(long tripsheetId, int companyId)throws Exception;

	public void updateDriverBalance(long tripsheetId, Double driverBalance, int companyId)throws Exception;

	public ValueObject getLastTripSheetDetailsForDriver(ValueObject valueObject) throws Exception;

	public TripSheetDto getInBetweentimeTripSheetByDateTimeForDriver(Integer driverId, String query) throws Exception;
	
	public List<TripSheetDto> getTotalTripSheetClosedTodayDetailsBetweenDates(String startDate, String endDate, Integer companyId) throws Exception;
	
	public Long getTripSheetClosedTodayCount(String startDate, String endDate,Integer companyId) throws Exception;

	public List<TripSheetExpenseDto> findAllCashlessTripAdavnceList(String fromDate, String toDate, int companyId,
			String tallyCompany) throws Exception;

	public List<TripSheetDto> FinalAllTodayTripSheet(Integer companyId, String todaysDate);

	public String getTripSheetCreatedMailBody(List<TripSheetDto> triplist, String todaysDate);
	
	public TripSheet getTripsheetByLhpvId(Integer lhpvId) throws Exception; 
	
	
	/**This Page get Vehicle table to get  pagination values */
	public Page<TripSheet> getPaginatedTripSheetsByVehicleId(Integer Status, Integer pageNumber) throws Exception;
	
	public Long countTotalTripSheet(Integer vid,  Integer companyId) throws Exception;
}
