package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripCollectionExpenseDto;
import org.fleetopgroup.persistence.dto.TripCollectionIncomeDto;
import org.fleetopgroup.persistence.dto.TripCollectionSheetDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.dto.TripDailyTimeIncomeDto;
import org.fleetopgroup.persistence.dto.TripGroupCollectionDto;
import org.fleetopgroup.persistence.model.TripCollectionExpense;
import org.fleetopgroup.persistence.model.TripCollectionIncome;
import org.fleetopgroup.persistence.model.TripCollectionSheet;
import org.fleetopgroup.persistence.model.TripDayCollection;
import org.fleetopgroup.persistence.model.TripGroupCollection;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface ITripCollectionService {

	public void add_TripCollectionSheet(TripCollectionSheet CollectionSheet) throws Exception;

	public void add_TripDayCollection(TripDayCollection DayCollection) throws Exception;

	public void add_TripGroupCollection(TripGroupCollection GroupCollection) throws Exception;

	public void add_TripCollectionExpense(TripCollectionExpense CollectionExpense) throws Exception;

	public void add_TripCollectionIncome(TripCollectionIncome CollectionIncome) throws Exception;

	// search Trip TripCollectionSheet
	public List<TripCollectionSheetDto> Search_TripCollectionSheet(String Search, CustomUserDetails userDetails) throws Exception;

	public List<TripCollectionSheetDto> Search_TripCollectionSheet_Statues(short Statues, String Search) throws Exception;

	public List<TripCollectionSheetDto> List_TripCollectionSheet_Report(String Search) throws Exception;

	public List<TripCollectionSheetDto> List_DriverJAMA_TripCollectionSheet_Report(String Search, Integer companyId) throws Exception;

	public List<TripGroupCollectionDto> List_TripGroupCollection_Report(String Search, Integer companyId) throws Exception;

	public List<TripDayCollection> List_TripDayCollection_Report(String Search, CustomUserDetails	userDetails) throws Exception;

	public Long count_TripCollectionSheet(CustomUserDetails	userDetails) throws Exception;

	public Long count_TripCollectionSheet_Today(String Today) throws Exception;

	public List<TripCollectionSheetDto> list_Today_TripCollectionSheet(String date, CustomUserDetails	userDetails) throws Exception;

	/** This Update Fixed Expense Total Amount in Trip Collection Amount */
	public void update_TripCollection_TotalExpense(Double tripTotalExpense, Long TRIPCOLLID, Integer companyId) throws Exception;

	// This get Long Collection ID to in collection details
	//public TripCollectionSheet Get_Showing_TripCollection_Sheet(Long TRIPCOLLID) throws Exception;
	
	public TripCollectionSheetDto Get_Showing_TripCollection_Sheet(Long TRIPCOLLID, CustomUserDetails userDetails) throws Exception;

	// This get Long Collection ID to in collection details
	public TripCollectionSheetDto Get_Showing_TripCollection_Sheetby_Number(Long TRIPCOLLID, CustomUserDetails userDetails) throws Exception;

	
	// This get Long Collection ID to in collection details
	public List<TripCollectionExpense> findAll_TripCollection_Expense(Long TRIPCOLLID) throws Exception;
	
	public List<TripCollectionExpenseDto> findAll_TripCollection_Expense(Long TRIPCOLLID, Integer companyId) throws Exception;

	// This get Long Collection ID to in collection details
	public List<TripCollectionExpense> Validate_TripCollection_Expense(Integer expenceName, Long TRIPCOLLID, Integer companyId)
			throws Exception;

	// This get Long Collection ID to in collection details
	public List<TripCollectionIncome> findAll_TripCollection_Income(Long TRIPCOLLID) throws Exception;
	
	public List<TripCollectionIncomeDto> findAll_TripCollection_Income(Long TRIPCOLLID, Integer companyId) throws Exception;

	// This get trip Collection expense ID to in collection details
	public TripCollectionExpense Get_TripCollection_Expense(Long TripSheet_Expenseid, Integer companyId) throws Exception;

	// delete Trip Collection Expense Details
	public void delete_TripCollectionExpense(Long TripExpenseid, Integer companyId) throws Exception;

	/** This Update Fixed Income Total Amount in Trip Collection Amount */
	public void update_TripCollection_TotalIncome(Double tripTotalIncome, Long TRIPCOLLID, Integer companyId) throws Exception;

	// This get Long Collection ID to in collection details
	public List<TripCollectionIncome> Validate_TripCollection_Income(Integer IncomeName, Long TRIPCOLLID, Integer companyId)
			throws Exception;

	// This get trip Collection Income ID to in collection details
	public TripCollectionIncome Get_TripCollection_Income(Long TripSheet_Incomeid, Integer companyId) throws Exception;

	// delete Trip Collection Income Details
	public void delete_TripCollectionIncome(Long TripIncomeid, Integer companyId) throws Exception;

	// This get Long Collection ID to in collection details
	public TripCollectionSheetDto EDIT_TripCollection_Sheet(Long TRIPCOLLID, Integer companyId) throws Exception;

	// This get Long Collection ID to in collection details
	public TripCollectionSheetDto ReOpen_TripCollection_Sheet(Long TRIPCOLLID, Integer companyId) throws Exception;

	/** This Page get TripCollectionSheet table to get pagination values */
	public Page<TripCollectionSheet> getDeployment_Page_TripCollectionSheet(short status, Integer pageNumber, CustomUserDetails	userDetails) throws Exception;

	/** This Page get TripCollectionSheet table to get pagination values */
	public List<TripCollectionSheetDto> list_TripCollectionSheet_Page_Status(short status, Integer pageNumber, CustomUserDetails	userDetails) throws Exception;

	/** This Update driver JAM Details Amount */
	public void update_TripCollection_Driver_JAM(Double driverJAM, Long TRIPCOLLID, Integer companyId) throws Exception;

	/** This Update Conductor JAM Details Amount */
	public void update_TripCollection_Conductor_JAM(Double ConductorJAM, Long TRIPCOLLID, Integer companyId) throws Exception;

	/**
	 * @param closingKM
	 * @param usageKM
	 * @param Status
	 * @param balance
	 * @param lastmodifiedby
	 * @param tripcollid
	 */
	public void update_Close_TripCollection(Integer closingKM, Integer usageKM, short Status, Double balance,
			Long lastmodifiedby, Long tripcollid, Integer companyId) throws Exception;

	/**
	 * @param trip_OPEN_DATE
	 * @param vehicle_GROUP
	 * @return
	 */
	public List<TripGroupCollection> Validate_TripGroupCollection(String trip_OPEN_DATE, Long vehicle_GROUP, Integer companyId)
			throws Exception;

	/**
	 * @param trip_OPEN_DATE
	 * @return
	 */
	public List<TripGroupCollectionDto> List_TripGroupCollection_closeDate(String trip_OPEN_DATE, short TRIP_CLOSE_STATUS, Integer companyId)
			throws Exception;
	
	public List<TripGroupCollection> List_TripGroupCollection_closeDate(String trip_OPEN_DATE, String TRIP_CLOSE_STATUS, CustomUserDetails userDetails)
			throws Exception;


	/**
	 * @param trip_OPEN_DATE
	 * @return
	 */
	public List<TripCollectionSheet> List_TripCollectionSheet_closeDate(String trip_OPEN_DATE, String TRIP_CLOSE_STATUS)
			throws Exception;
	
	public List<TripCollectionSheetDto> List_TripCollectionSheet_closeDate(String trip_OPEN_DATE, short TRIP_CLOSE_STATUS, CustomUserDetails userDetails)
			throws Exception;


	/**
	 * @param total_INCOME
	 * @param total_EXPENSE
	 * @param total_BALANCE
	 * @param trip_DIESEL_LITER
	 * @param trip_DIESEL_LITER2
	 * @param trip_SINGL
	 * @param tripcollid
	 */
	public void update_TripGroupCollection(Double total_INCOME, Double total_EXPENSE, Double total_BALANCE,
			Integer total_BUS, Integer total_DIESEL, Integer total_SINGL, Long TRIPGROUPID, Integer companyId) throws Exception;

	/**
	 * @param per_SINGL_COLLECTION
	 * @param tripCollectionDate
	 */
	public void update_TripGroup_Ac_collectionTotal_Amount(Double per_SINGL_COLLECTION, String tripCollectionDate, Integer companyId)
			throws Exception;

	/**
	 * @param tripCollectionDate
	 * @param string
	 */
	public TripDayCollection List_TripDayCollection_CloseDate(String tripCollectionDate, short CloseSatus)
			throws Exception;
	
	
	/**
	 * @param tripCollectionID
	 */
	public void delete_TripCollectionSheet(Long tripCollectionID, Integer companyId) throws Exception;

	/**
	 * @param tripCollectionID
	 */
	public void delete_TripCollectionExpense_collectionID(Long tripCollectionID, Integer companyId) throws Exception;

	/**
	 * @param tripCollectionID
	 */
	public void delete_TripCollectionIncome_collectionId(Long tripCollectionID, Integer companyId) throws Exception;

	/**
	 * @param dRIVER_ID
	 * @return
	 */
	public List<TripCollectionSheet> DRIVER_JAMA_BALANCE_TripCollectio(Integer dRIVER_ID) throws Exception;

	/**
	 * @param dRIVER_ID
	 * @return
	 */
	public List<TripCollectionSheet> CONDUCTOR_JAMA_BALANCE_TripCollectio(Integer dRIVER_ID) throws Exception;

	/**
	 * @param string
	 * @param trip_DRIVER_ID
	 */
	public void update_Driver_JAMA_Status(short string, int trip_DRIVER_ID, Integer companyId);

	/**
	 * @param string
	 * @param trip_DRIVER_ID
	 */
	public void update_Conductor_JAMA_Status(short string, int trip_DRIVER_ID, Integer companyId);

	/**
	 * @param tripcollid
	 * @param trip_CLOSE_STATUS
	 */
	public void Update_ReOpen_Status_TripCollectionSheet(Long tripcollid, short trip_CLOSE_STATUS, Integer companyId);

	/**
	 * @param total_INCOME
	 * @param total_EXPENSE
	 * @param total_BALANCE
	 * @param trip_DIESEL_LITER
	 * @param trip_SINGL
	 * @param trip_ROUTE_ID
	 * @param trip_OPEN_DATE
	 * @param vehicle_GROUP
	 */
	public void Update_ReOpen_To_Subtrack_TripGroupCollection(Double total_INCOME, Double total_EXPENSE,
			Double total_BALANCE, Integer trip_DIESEL_LITER, Integer trip_SINGL, Integer BUS_ROUTE_ID,
			String trip_OPEN_DATE, long vehicle_GROUP, Integer companyId);

	/**
	 * @param fromDate
	 * @param vid
	 * @return
	 */
	public List<TripCollectionSheet> Validate_TripCollectionSheet_Date_VehicleName(Date fromDate, Integer vid, Integer companyId);
	
	public TripCollectionSheetDto Get_FuelVendor_SearchTo_TripSheetDetailsIN(Long tripSheetNumber, Integer companyId);
	
	public Object[] getDriverAdvanceAndBataExpenseId(Integer companyId) throws Exception;

	/*
	 * Method For get Trip Collection Report Based on Daily, Monthly & Yearly
	 */
	public ValueObject getDailyMonthlyYearlyTripCollectionReport(ValueObject valueObject) throws Exception;

	/*
	 * Method For get Trip Collection Report Based on Daily
	 */
	public ValueObject getDailyTripDailyCashBookReport(ValueObject valueObject) throws Exception;
	/*
	 * Method For get Depot Wise trip Sheet Status Report
	 */
	public ValueObject getDepotWiseTripSheetStatusReport(ValueObject valueObject) throws Exception;
	/*
	 * Method For get Expense Name using date range report
	 */
	public ValueObject getTripCollectionExpenseName(ValueObject object) throws Exception;

	public List<TripDailySheetDto> getTreepCollectionInfo(Integer ROUTE_ID, String dateRangeFrom,
			String dateRangeTo, List<TripDailyTimeIncomeDto> getFixedTypeIncomeName, Integer companyId)throws Exception;
	
	public ValueObject getTreepCollectionExpense(ValueObject object)throws Exception;

}