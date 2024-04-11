package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.DriverSalaryAdvanceDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverSalaryAdvance;

public interface IDriverSalaryAdvanceService {

	DriverSalaryAdvance register_New_DriverSalaryAdvance(DriverSalaryAdvance accountDto) throws Exception;

	List<DriverSalaryAdvance> List_Total_OF_DriverSalaryAdvance_details(Integer driver_ID, Integer companyId);

	//void update_DriverSalaryAdvance_Status(String advance_Status, Integer driver_ID) throws Exception;

	/**
	 * @param dRIVER_ID
	 * @return
	 */
	public Double DRIVER_Advance_BALANCE_TripCollectiom(Integer dRIVER_ID) throws Exception;

	/**
	 * @param query
	 * @return
	 */
	List<DriverSalaryAdvance> List_DriverSalaryAdvance_TOTAL_Report(String query) throws Exception;

	/**
	 * @param dRIVER_ID
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	List<DriverAttendanceDto> Report_OF_Driver_Halt_Point(Integer dRIVER_ID, String dateRangeFrom, String dateRangeTo);

	/**
	 * @param dRIVER_ID
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	List<DriverAttendanceDto> Report_OF_Driver_LocalHalt_Point(Integer dRIVER_ID, String dateRangeFrom,
			String dateRangeTo);

	/**
	 * @param dRIVER_GROUP
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	List<DriverAttendanceDto> Report_OF_Driver_Group_Attendance_Point(String dRIVER_GROUP, String dateRangeFrom,
			String dateRangeTo);

	/**
	 * @param dRIVER_ID
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	List<DriverAttendanceDto> Report_OF_Driver_Attendance(Integer dRIVER_ID, String dateRangeFrom, String dateRangeTo);

	/**
	 * @param dRIVER_GROUP
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	List<Driver> Report_Group_Driver_Attendance_Lastmonth_salaryDetails(String dRIVER_GROUP, String dateRangeFrom,
			String dateRangeTo);
	
	/**
	 * @param dRIVER_GROUP
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	List<DriverAttendanceDto> Report_Group_Driver_Attendance(String dRIVER_GROUP, String dateRangeFrom,
			String dateRangeTo);

	List<DriverSalaryAdvance> GET_DriverSalaryAdvance_details_DRIVER_ID(Integer dRIVER_ID, short ADVANCE_STATUS, String dateRangeFrom, String dateRangeTo);

	public void update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK(Double paidAmountAdvacne, Long dsaid);
	
	public void update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK_UPDATE_STATUS(Double paidAmountAdvacne, short ADVANCE_STATUS, Long dsaid);

	DriverSalaryAdvance GET_DRIVER_SALARY_ADVANCE_ID(Long dRIVER_SALARYID, Integer companyId);

	public void DELETE_DRIVER_SALARY_ADVANCE_ID(Long dRIVER_SALARYID, Integer companyId);

	//This Trip Daily Sheet Penalty is WT Total Amount Details
	public List<DriverSalaryAdvanceDto> List_OF_TRIPDAILYSHEET_ID_PENALTY_DETAILS(Long tripdailyid, short AdvanceName, int companyId);

	public void DELETE_PENALTY_IN_TRIPSHEET_AID(Long advance_ID);

	public void DELETE_PENALTY_IN_TRIPDailySheet_Delete_ID(Long tripCollectionID);

	List<DriverSalaryAdvanceDto> Driver_Advance_Penalty_ReportDate(String query, String dateRangeFrom,
			String dateRangeTo, Integer companyId);

	List<Object[]> List_OF_DEPOT_WISE_PENALTY_DATE_REPORT(long dRIVER_GROUP, List<String> dateSearch, Integer companyId) throws Exception;

	List<Object[]> List_OF_DEPOT_WISE_PENALTY_COLUMN_Report(long dRIVER_GROUP, List<String> dateSearch, Integer companyId);

	List<Object[]> LIST_OF_INDIDUAL_WISE_PENALTY_DATE_REPORT(Integer dRIVER_ID, String dateRangeFrom,
			String dateRangeTo, Integer companyId);

	List<Object[]> List_OF_DEPOT_WISE_ADVANCE_DATE_REPORT(long dRIVER_GROUP, List<String> dateSearch, Integer companyId);

	List<Object[]> List_OF_DEPOT_WISE_ADVANCE_COLUMN_Report(long dRIVER_GROUP, List<String> dateSearch, Integer companyId);

	List<Object[]> LIST_OF_INDIDUAL_WISE_ADVANCE_DATE_REPORT(String query, String dateRangeFrom,
			String dateRangeTo, Integer companyId);

	public void update_DriverSalaryAdvance_OTHER_PENALTY_UPDATE_STATUS_ZERO(double d, short string, Integer dRIVER_ID, String dateRangeFrom, String dateRangeTo,  Integer companyId);
	
	List<DriverSalaryAdvance>  getDriverSalaryAdvanceByTripSheetId(Long tripSheetId, Integer companyId)throws Exception;

}
