package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripDailyAllGroupDayDto;
import org.fleetopgroup.persistence.dto.TripDailyExpenseDto;
import org.fleetopgroup.persistence.dto.TripDailyGroupCollectionDto;
import org.fleetopgroup.persistence.dto.TripDailyIncomeDto;
import org.fleetopgroup.persistence.dto.TripDailyRouteSheetDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.dto.TripDailyTimeIncomeDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.model.TripDailyAllGroupDay;
import org.fleetopgroup.persistence.model.TripDailyExpense;
import org.fleetopgroup.persistence.model.TripDailyGroupCollection;
import org.fleetopgroup.persistence.model.TripDailyIncome;
import org.fleetopgroup.persistence.model.TripDailyRouteSheet;
import org.fleetopgroup.persistence.model.TripDailySheet;
import org.fleetopgroup.persistence.model.TripDailyTimeIncome;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface ITripDailySheetService {

	// This Trip Daily Sri Durgamba Collection To save
	public void add_TripDailySheet(TripDailySheet DailySheet) throws Exception;

	// This Date Route Wise Total Amount
	public void add_TripDailyRoute_Collection(TripDailyRouteSheet RouteSheet) throws Exception;

	// This TripDailySheet To many Expense Amount Save Details
	public void add_TripDailyExpense(TripDailyExpense DailyExpense) throws Exception;

	// This TripDailySheet To many Income Amount Save Details
	public void add_TripDailyIncome(TripDailyIncome DailyIncome) throws Exception;

	// This Date TripDailyGroupCollection Wise Total Amount
	public void add_TripDailyGroup_Collection(TripDailyGroupCollection RouteSheet) throws Exception;

	// This Date TripDailyAllGroupDayCollection Wise Total Amount
	public void add_TripDailyAllGroupDay_Collection(TripDailyAllGroupDay DailyAllGroupDay) throws Exception;

	/**
	 * @param format
	 * @return
	 */
	public List<TripDailySheetDto> list_Today_TripDailySheet(String format, CustomUserDetails	userDetails) throws Exception;
	
	public List<TripDailySheet> list_Today_TripDailySheet(String format, String Status, String Group);

	/**
	 * @return
	 */
	public Object count_TripCollectionSheet();

	/**
	 * @param format
	 * @param string
	 * @return
	 */
	public List<TripDailyRouteSheet> List_TripDailySheetService_closeDate(String VEHICLE_GROUP, String trip_OPEN_DATE,
			String TRIP_CLOSE_STATUS);
	
	public List<TripDailyRouteSheet> List_TripDailySheetService_closeDate(long VEHICLE_GROUP_ID, String trip_OPEN_DATE,
			short TRIP_CLOSE_STATUS, Integer companyId);

	/**
	 * @param tripTotalExpense
	 * @param tripdailyid
	 */
	/** This Update Fixed Expense Total Amount in Trip Daily Sheet Amount */
	/*
	 * public void update_TripDailySheet_TotalExpense(Double tripTotalExpense, Long
	 * tripdailyid);
	 */

	/** This Update Fixed Expense Total Amount in Trip Daily Sheet Amount */
	public void update_TripDailySheet_TotalExpense(Long tripdailyid);

	/**
	 * @param tripdailyid
	 * @return
	 */
	public TripDailySheet Get_Showing_TripDaily_Sheet(Long tripdailyid) throws Exception;
	
	public TripDailySheetDto Get_Showing_TripDaily_Sheet(Long tripdailyid, CustomUserDetails	userDetails) throws Exception;
	
	
	public TripDailySheetDto Get_Showing_TripDaily_SheetByNumber(Long tripdailyid, CustomUserDetails userDetails) throws Exception;

	/**
	 * @param tripdailyid
	 * @return
	 */
	//public List<TripDailyExpense> findAll_TripDailySheet_ID_Expense(Long tripdailyid);
	
	
	public List<TripDailyExpenseDto> findAll_TripDailySheet_ID_Expense(Long tripdailyid, Integer companyId) throws Exception;

	/**
	 * @param tripdailyid
	 * @return
	 */
	//public List<TripDailyIncome> findAll_TripDailySheet_ID_Income(Long tripdailyid);
	
	public List<TripDailyIncomeDto> findAll_TripDailySheet_ID_Income(Long tripdailyid, Integer companyId) throws Exception;

	/**
	 * @param string
	 * @param tripdailyid
	 * @return
	 */
	public List<TripDailyExpense> Validate_TripDaily_Expense(Integer string, Long tripdailyid, Integer companyId);

	/**
	 * @param tripdailyid
	 * @return
	 */
	public List<TripDailyExpense> findAll_TripDaily_Expense(Long tripdailyid);

	/**
	 * @param tripExpenseID
	 * @return
	 */
	public TripDailyExpense Get_TripDaily_Expense(Long tripExpenseID, Integer companyId);
	
	public TripDailyExpense Get_TripDaily_Expense(Integer tripExpenseID, Integer companyId);

	/**
	 * @param tripExpenseID
	 */
	public void delete_TripDailyExpense(Long tripExpenseID, Integer companyId);

	/**
	 * @param tripdailyid
	 * @return
	 */
	public List<TripDailyIncome> findAll_TripDaily_Income(Long tripdailyid);

	/**
	 * @param string
	 * @param tripdailyid
	 * @return
	 */
	public List<TripDailyIncome> Validate_TripDaily_Income(Integer string, Long tripdailyid, Integer companyId);

	/**
	 * @param tripTotalIncome
	 * @param tripdailyid
	 */
	/*
	 * public void update_TripDaily_TotalIncome(Double tripTotalIncome, Long
	 * tripdailyid);
	 */

	public void update_TripDaily_TotalIncome(Long tripdailyid);
	
	public void update_TripDaily_TotalIncome(Long tripdailyid, Integer companyId);

	/**
	 * @param tripIncomeID
	 * @return
	 */
	public TripDailyIncome Get_TripDaily_Income(Long tripIncomeID, Integer companyId);

	/**
	 * @param tripincomeID
	 */
	public void delete_TripDaily_Income(Long tripincomeID, Integer companyId);

	/**
	 * @param fromDate
	 * @param vehicleid
	 * @return
	 */
	public List<TripDailySheet> Validate_TripCollectionSheet_Date_VehicleName(Date fromDate, Integer vehicleid);

	/**
	 * @param fuel_liters
	 * @param fuel_kml
	 * @param tRIPSHEET_ID
	 */
	public void Update_TripDailysheet_Deisel_and_KMPL(Long tRIPSHEET_ID, Integer companyId);

	/**
	 * @param closingKM
	 * @param usageKM
	 * @param string
	 * @param balance
	 * @param lASTUPDATEDBY
	 * @param lASTUPDATED
	 * @param tripdailyid
	 */
	public void Update_Close_TripDailySheet(Integer closingKM, Integer usageKM, short string, Double TOTAL_NET_INCOME, Double balance,
			Timestamp lASTUPDATED, Long tripdailyid, Integer companyId, Long lastUpdatedById, float noOfRoundTrip);

	/**
	 * @param format
	 * @param trip_ROUTE_NAME
	 * @param vehicle_GROUP
	 * @return
	 */
	public List<TripDailyRouteSheet> Validate_TripDailyRoute_SheetIs_or_not(String trip_OPEN_DATE,
			Integer trip_ROUTE_ID, long vehicle_GROUP_ID , Integer companyId);

	/**
	 * @param total_INCOME
	 * @param total_EXPENSE
	 * @param balance
	 * @param trip_OVERTIME
	 * @param trip_RFIDPASS
	 * @param trip_TOTALPASSNGER
	 * @param trip_DIESEL
	 * @param trip_USAGE_KM
	 * @param triprouteid
	 */
	/*
	 * public void update_TripDailyRoute_already(Double total_INCOME, Double
	 * total_EXPENSE, Double balance, Double trip_OVERTIME, Integer trip_RFIDPASS,
	 * Integer trip_TOTALPASSNGER, Double trip_DIESEL, Integer trip_USAGE_KM, Long
	 * triprouteid);
	 */

	public void update_TripDailyRoute_already(String trip_OPEN_DATE, Integer trip_ROUTE_ID, long vehicle_GROUP,
			Long triprouteid, Integer companyId);

	/**
	 * @param tripCollectionID
	 * @return
	 */
	public TripDailySheetDto ReOpen_TripDaily_Sheet_ClosedOnly(Long tripCollectionID, Integer companyId);

	/**
	 * @param tripdailyid
	 * @param string
	 */
	public void Update_ReOpen_Status_TripDailySheet(Long tripdailyid, short status, Integer companyId);

	/**
	 * @param total_INCOME
	 * @param total_EXPENSE
	 * @param total_BALANCE
	 * @param trip_OVERTIME
	 * @param trip_RFIDPASS
	 * @param trip_TOTALPASSNGER
	 * @param trip_DIESEL
	 * @param trip_USAGE_KM
	 * @param tripCollectionDate
	 * @param vehicle_GROUP
	 * @param trip_ROUTE_NAME
	 */
	/*
	 * public void Update_ReOpen_To_Subtrack_TripDailyRouteSheet_to_tripDaily(Double
	 * total_INCOME, Double total_EXPENSE, Double total_BALANCE, Double
	 * trip_OVERTIME, Integer trip_RFIDPASS, Integer trip_TOTALPASSNGER, Double
	 * trip_DIESEL, Integer trip_USAGE_KM, String tripCollectionDate, String
	 * vehicle_GROUP, String trip_ROUTE_NAME);
	 */

	public void Update_ReOpen_To_Subtrack_TripDailyRouteSheet_to_tripDaily(String tripCollectionDate,
			long vehicle_GROUP, Integer trip_ROUTE_ID, Integer companyId);

	/**
	 * @param tripCollectionDate
	 * @param vEHICLEGROUP
	 * @param string
	 * @return
	 *//*
	public List<TripDailySheet> List_TripDailySheet_closeDate_with_Group(String tripCollectionDate, long vEHICLEGROUP,
			short string);*/
	
	public List<TripDailySheetDto> List_TripDailySheet_closeDate_with_Group(String tripCollectionDate, long vEHICLEGROUP,
			short string, Integer companyId);
	
	public HashMap<String, TripDailySheetDto>  getFuelAmountOfTripSheet(String tripCollectionDate, long vEHICLEGROUP,
			short string, Integer companyId) throws Exception;
	

	

	public ArrayList<TripDailySheetDto> List_TripDailySheet(String query, Integer companyId);


	/**
	 * @param tripCollectionDate
	 * @param vEHICLEGROUP
	 * @param string
	 * @return
	 */
	public List<TripDailyRouteSheet> List_TripDailyRouteSheet_closeDate_to_get_details(String tripCollectionDate,
			long vEHICLEGROUP_ID, short string);
	
	public List<TripDailyRouteSheetDto> List_TripDailyRouteSheet_closeDate_to_get_details(String tripCollectionDate,
			long vEHICLEGROUP, short string, Integer companyId);

	/**
	 * @param string
	 * @param tripCollectionDate
	 * @param vEHICLEGROUP
	 */
	public void Update_TripDailyRoute_Closed_Status(short string, String tripCollectionDate, long vEHICLEGROUPID, Integer companyId);

	/**
	 * @param fromDate
	 * @param vEHICLEGROUP
	 * @param string
	 * @return
	 */
	/*public TripDailyGroupCollection GET_TripDailyGroupCollection_CloseDate(Date fromDate, long vEHICLEGROUP_ID,
			String string);*/
	
	public TripDailyGroupCollectionDto GET_TripDailyGroupCollection_CloseDate(Date fromDate, long vEHICLEGROUP,
			short string, Integer companyId);
	
	public TripDailyGroupCollection GET_TripDailyGroupCollectionOnCloseDate(Date fromDate, long vEHICLEGROUP,
			short string, Integer companyId);


	/**
	 * @param query
	 * @return
	 */
	public List<TripDailySheet> List_TripDailySheet_Report(String query) throws Exception;
	
	
	public List<TripDailySheetDto> List_TripDailySheet_Report(String query, CustomUserDetails	userDetails) throws Exception;

	/**
	 * @param string
	 * @param pageNumber
	 * @return
	 */
	public Page<TripDailySheet> getDeployment_Page_TripDailySheet(short string, Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	/**
	 * @param string
	 * @param pageNumber
	 * @return
	 */
	public List<TripDailySheetDto> list_TripDailySheet_Page_Status(short string, Integer pageNumber, CustomUserDetails userDetails) throws Exception;

	/**
	 * @param tripCollectionID
	 */
	public void delete_TripDailyExpense_TRIPDAILYID(Long tripCollectionID, Integer companyId);

	/**
	 * @param tripCollectionID
	 */
	public void delete_TripDailyIncome_TRIPDAILYID(Long tripCollectionID, Integer companyId);

	/**
	 * @param tripCollectionID
	 */
	public void delete_TripDailySheet_TRIPDAILYID(Long DeletedBy, Timestamp deletedtime, Long tripCollectionID, Integer companyId);

	/**
	 * @param query
	 * @return
	 */
	public List<TripDailyRouteSheetDto> List_TripDailyRouteSheet_Report(String query, CustomUserDetails	userDetails) throws Exception;

	/**
	 * @param vehicle_GROUP
	 * @param trip_OPEN_DATE
	 * @param string
	 * @return
	 */
	public List<TripDailyAllGroupDay> Validate_All_GroupDepartment_CloseOrNot_list(java.util.Date trip_OPEN_DATE,
			short Status, Integer companyId);

	/**
	 * @param totalGroupSheet
	 */
	public void Update_TripDailyAllGroupDay_Collection_To_GroupCollection(TripDailyGroupCollection totalGroupSheet,
			Long TRIPALLGROUPID);
	
	public void Subtract_TripDailyAllGroupDay_Collection_To_GroupCollection(TripDailyGroupCollection totalGroupSheet,
			Long TRIPALLGROUPID);

	/**
	 * @param string
	 * @param pageNumber
	 * @return
	 */
	public Page<TripDailyAllGroupDay> getDeployment_Page_TripDailyAllGroupDay(short string, Integer pageNumber, CustomUserDetails	userDetails);

	/**
	 * @param string
	 * @param pageNumber
	 * @return
	 */
	public List<TripDailyAllGroupDayDto> list_TripDailyAllGroupDay_Page_StatusAllGroup(short string, Integer pageNumber, Integer companyId);

	/**
	 * @param tripCollectionDate
	 * @param string
	 * @return
	 */
	public List<TripDailyRouteSheetDto> Validate_TripDailyRoute_All_Group_CLOSED_OR_NOT(String tripCollectionDate,
			short string, Integer companyId);

	/**
	 * @param fromDate
	 * @return
	 */
	public TripDailyAllGroupDayDto GET_DETAILS_TO_TRIPSHEET_ALLGROUPDAY_COLLECTION_DETILS(Date fromDate, Integer companyId);

	/**
	 * @param fromDate
	 * @param string
	 * @return
	 */
	public List<TripDailyGroupCollectionDto> List_TripDailyGroupCollection_Get_all_details(Date fromDate, short string, Integer companyId);

	/**
	 * @param dailyAllGroupDayDto
	 */
	public void Update_AllGroup_Day_Collection_Closed_Details(TripDailyAllGroupDay dailyAllGroupDayDto);

	/**
	 * @param tRIPALLGROUPID
	 * @return
	 */
	public TripDailyAllGroupDayDto GET_DETAILS_TO_TRIPSHEET_ALLGROUPDAY_ID(Long tRIPALLGROUPID, Integer companyId);

	/**
	 * @param trip_OPEN_DATE
	 * @param string
	 * @return
	 */
	public List<TripDailyGroupCollectionDto> List_TripDailyGroupCollection_Get_all_details_Date(
			java.util.Date trip_OPEN_DATE, short string, Integer companyId);

	/**
	 * @param query
	 * @return
	 */
	public List<TripDailyGroupCollectionDto> List_TripDailyGroupCollection_Report(String query, CustomUserDetails	userDetails) throws Exception;

	/**
	 * @param query
	 * @return
	 */
	public List<TripDailyAllGroupDay> List_TripDailyAllGroupDay_Report(String query, Integer companyId) throws Exception;

	/**
	 * @param tripStutes
	 * @return
	 */
	public List<TripDailySheetDto> Search_TripDailySheet(String tripStutes, CustomUserDetails	userDetails) throws Exception;

	/**
	 * @param Search
	 * @param tripStutes
	 * @return
	 */
	public List<TripDailySheetDto> Search_STATUS_TripDailySheet(String Search, short tripStutes) throws Exception;

	public void UpDate_TRIP_PENALTY_AMOUNT_IN_WT(Long tripdailyid, short string);

	public void Update_TripDailyIncome_Amount_By_tripincomeID(Double incomeAmount, Long incomeCollectedBy,
			Long tripincomeID, Integer companyId);

	public List<TripDailyTimeIncomeDto> List_OF_TripDailySheet_get_Fixed_TIME_Income_Name(Integer vEHICLE_ID, String dateRangeFrom,
			String dateRangeTo, Integer companyId);

	public List<Object[]> List_OF_Time_WISE_Report(Integer vEHICLE_ID,
			String dateRangeFrom, String dateRangeTo,
			List<TripDailyTimeIncomeDto> getFixedTypeIncomeName, Integer companyId);
	
	public List<TripDailyTimeIncomeDto> List_OF_TripDailySheet_get_Fixed_TIME_Income_ROUTE_NAME(Integer ROUTE_ID, String dateRangeFrom,
			String dateRangeTo, Integer companyId);

	public List<Object[]> List_OF_Time_WISE_Report_TIME_INCOME_ROUTE_NAME(Integer ROUTE_ID,
			String dateRangeFrom, String dateRangeTo,
			List<TripDailyTimeIncomeDto> getFixedTypeIncomeName, Integer companyId);

	public List<TripDailyTimeIncomeDto> findAll_TripDaily_TIME_Income(Long tripdailyid) throws Exception;

	public void add_TripDailyTimeIncome(TripDailyTimeIncome tripIncome);
	
	public void update_TripDaily_TotalTimeIncome_INCOME_COLLECTION(Long tripdailyid);


	public void update_TripDaily_TotalTimeIncome_INCOME_COLLECTION_TRIP_SUBROUTE_ID(Integer Trip_SUBRouteID, Long tripdailyid, Integer companyId);

	public List<TripDailyTimeIncome> Validate_TripDaily_TIME_Income(Integer string, Long tripdailyid, Integer companyId);

	public void Update_TripDailyTimeIncome_Amount_By_TDTIMEID(Double incomeAmount, Long incomeCollectedBy,
			Long tripincomeID);

	public TripDailyTimeIncome Get_TripDaily_TIME_Income(Long tripIncomeID, Integer companyId);

	public void delete_TripDaily_TIME_Income(Long tdtimeid, Integer companyId);

	//public List<TripDailyTimeIncome> findAll_TripDailySheet_ID_TIME_Income(Long tripdailyid);
	
	public List<TripDailyTimeIncomeDto> findAll_TripDailySheet_ID_TIME_Income(Long tripdailyid, Integer companyId) throws Exception;

	public void Delete_Trip_Daily_Expense_Fuel_ID_Value_Amount(Long fuel_id, Integer companyId);

	public List<Object[]> List_OF_Time_WISE_COLUMN_Report(Integer vEHICLE_ID, String dateRangeFrom, String dateRangeTo,
			List<TripDailyTimeIncomeDto> getFixedTypeIncomeName, Integer companyId);

	public List<Object[]> List_OF_Time_WISE_ROUTE_COLUMN_Report(Integer rOUTE_ID, String dateRangeFrom,
			String dateRangeTo, List<TripDailyTimeIncomeDto> getFixedTypeIncomeName, Integer companyId);
	
	public TripDailySheetDto Get_FuelVendor_SearchTo_TripSheetDetailsIN(Long tripSheetNumber, Integer companyId);
	
	public void updatePassengerDetails(TripDailySheetDto	dailySheet)throws Exception;
	
	public void updateNoOfRoundTrip(TripDailySheetDto	dailySheet)throws Exception;
	
	public void deleteTripDailyGroupCollectionById(String opendate, long vehicleGroupId, Integer companyId) throws Exception;
	/*
	 * Get Trip Sheet Wise Hash Map Of Total Trip Income 
	 */
	public HashMap<Long, Double> getTripSheetWiseTotalIncome(String finalQuery, Integer company_id) throws Exception;
	/*
	 * Get Trip Sheet Wise Hash Map Of Total Trip Expense 
	 */
	public HashMap<Long, Double> getTripSheetWiseTotalExpense(String finalQuery, Integer company_id) throws Exception;
	
	public ValueObject	updateFixedExpenses(ValueObject	valueObject) throws Exception;
	
	public Double getFuelExpensesOfTrip(Long tripSheetId, Integer companyId) throws Exception;


	public HashMap<String, TripDailySheetDto> getFuelAmountOfTripSheetReport(Integer ROUTE_ID, String dateRangeFrom,
			String dateRangeTo, Integer companyId) throws Exception;

	

	
	public ValueObject	getExpensesDetailsforModel(ValueObject	valueObject) throws Exception;

	HashMap<String, TripDailySheetDto> getDieselAmountOfTripSheetReport(Integer VEHICLE_ID, String dateRangeFrom,
			String dateRangeTo, Integer companyId) throws Exception;
	
	public TripDailyExpense	getExpenseByTripSheetIdAndExpenseId(Long tripId, Long expenseId) throws Exception ;
	
	public TripDailyIncome	getIncomeByTripSheetIdAndIncomeId(Long tripId, Integer incomeId) throws Exception ;
	
	public List<TripSheetDto>  getTripSheetDetailsInMonthByVehicleGroupId(long	vehicleGroupId, Timestamp fromDate, Timestamp	toDate) throws Exception;
	
	public List<TripSheetDto>  getTripSheetDetailsInMonthByVId(Integer vid, Timestamp fromDate, Timestamp	toDate) throws Exception;
	
	public HashMap<Integer, Long>  getVehicleRunKMByGroupId(Long groupId, Timestamp fromDate, Timestamp	toDate) throws Exception;
	
	public Integer  getVehicleRunKMByVid(Integer	vid, Timestamp fromDate, Timestamp	toDate) throws Exception;
	
	public List<TripSheetIncomeDto> getVehicleIncomeDetailsOfMonthByIncomeId(TripSheetIncomeDto incomeDto) throws Exception;
	
	public List<TripSheetExpenseDto> getVehicleExpenseDetailsOfMonthByExpenseId(TripSheetIncomeDto incomeDto) throws Exception;

	public List<TripDailySheetDto> getFuelAmountOfTripDailySheetReport(String dateRangeFrom, String dateRangeTo,
			String query1, Integer companyId) throws Exception;

	public HashMap<String, TripDailySheetDto> getFuelAmountOfTripSheetReportDateWise(String dateRangeFrom, String dateRangeTo,
			String query1, Integer companyId) throws Exception;

	public HashMap<String, TripDailySheetDto> getDepotWiseFuelAmountOfTripSheet(String dateRangeFrom, String dateRangeTo,
			String query1, CustomUserDetails userDetails) throws Exception;
	
	public HashMap<String, TripDailySheetDto> getDepotWiseChaloDetailsOfTripSheet(String dateRangeFrom, String dateRangeTo,
			String query1,CustomUserDetails userDetails) throws Exception;

	public TripDailySheetDto getvehicleStatusByVid(Integer vehicleID) throws Exception;

	public int update_Chalo_Details(Long tripdailyid, Integer chaloKm, Double chaloAmount) throws Exception;

	public List<TripDailySheetDto> findChaloInformation(Long tripdailyid, Integer companyId) throws Exception;
	
	public void delete_Chalo_Details(Long tripdailyid, Integer companyId) throws Exception;

	public List<TripDailySheetDto> list_Close_TripDailySheet(String dateRange, CustomUserDetails userDetails)throws Exception;

	public List<TripDailyRouteSheet>  validateForDepotTripDayCollectionClosed(String trip_OPEN_DATE,
			long vEHICLEGROUP, short TRIP_CLOSE_STATUS, Integer companyId) throws Exception;
	
	public TripDailySheet Get_Showing_TripDaily_Sheet(Long tripdailyid, Integer companyId) throws Exception;

}