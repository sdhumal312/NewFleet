package org.fleetopgroup.persistence.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.DriverAdvanceDto;
import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverAdvance;

public interface IDriverAdvanceService {

	DriverAdvance register_New_DriverAdvance(DriverAdvance accountDto) throws Exception;

	List<DriverAdvance> List_Total_OF_DriverAdvance_details(Integer driver_ID);

	void update_DriverAdvance_Status(short advance_Status, Integer driver_ID, Integer companyId) throws Exception;

	/**
	 * @param dRIVER_ID
	 * @return
	 */
	List<DriverAdvance> DRIVER_Advance_BALANCE_TripCollectiom(Integer dRIVER_ID, Integer companyId) throws Exception;

	/**
	 * @param query
	 * @return
	 */
	List<DriverAdvanceDto> List_DriverAdvance_TOTAL_Report(String query, Integer companyId) throws Exception;

	/**
	 * @param dRIVER_ID
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	List<DriverAttendanceDto> Report_OF_Driver_Halt_Point(Integer dRIVER_ID, String dateRangeFrom, String dateRangeTo, Integer companyId) throws Exception;

	/**
	 * @param dRIVER_ID
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	List<DriverAttendanceDto> Report_OF_Driver_LocalHalt_Point(Integer dRIVER_ID, String dateRangeFrom,
			String dateRangeTo, Integer companyId) throws Exception;

	/**
	 * @param dRIVER_GROUP
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	List<DriverAttendanceDto> Report_OF_Driver_Group_Attendance_Point(String dRIVER_GROUP, String dateRangeFrom,
			String dateRangeTo, Integer companyId) throws Exception;

	/**
	 * @param dRIVER_ID
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	List<DriverAttendanceDto> Report_OF_Driver_Attendance(Integer dRIVER_ID, String dateRangeFrom, String dateRangeTo, Integer companyId) throws Exception;

	/**
	 * @param dRIVER_GROUP
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	List<Driver> Report_Group_Driver_Attendance_Lastmonth_salaryDetails(String dRIVER_GROUP, String dateRangeFrom,
			String dateRangeTo, Integer companyId);
	
	/**
	 * @param dRIVER_GROUP
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	List<DriverAttendanceDto> Report_Group_Driver_Attendance(String dRIVER_GROUP, String dateRangeFrom,
			String dateRangeTo, Integer companyId) throws Exception;

	public Object[] SUM_TOTAL_ADVANCE_PENALTY_AMOUNT(Integer DRIVER_ID, String dateRangeFrom,
			String dateRangeTo, Integer companyId)
			throws Exception ;

	HashMap<Integer, TripSheetDto> Report_Group_Driver_Trip_And_KMUsage_Lastmonth_salaryDetails(String driverGroupQuery, String dateRangeFrom, String dateRangeTo, Integer company_id) throws Exception;

	List<DriverAttendanceDto> Report_OF_Driver_AttendanceForDriverSalary(int driverid, String dateRangeFrom, String dateRangeTo, Integer company_id) throws Exception;
	
}
