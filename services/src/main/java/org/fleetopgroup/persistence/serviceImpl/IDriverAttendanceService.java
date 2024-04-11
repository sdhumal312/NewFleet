package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.HashMap;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.model.DriverAttendance;

public interface IDriverAttendanceService {

	public void addDriverAttendance(DriverAttendance Attendance);

	public DriverAttendance ValidateDriverAttendance(int driver_id, Date driver_Date);

	public void updateDriverAttendance(DriverAttendance driver);

	public List<DriverAttendance> listDriverAttendance(int driver_id);

	public List<DriverAttendanceDto> list_Date_DriverAttendance(int driver_id, String from, String To, Integer companyId) throws Exception ;

	public List<DriverAttendanceDto> list_OF_Point_Date_DriverAttendance(int driver_id, String from, String To,Integer companyId) throws Exception  ;

	public DriverAttendance getDriverAttendance(Long driver_id);

	public void deleteDriverAttendance(Long driver_id);

	public Long Driver_Worked_Over_Monthly(int driver_id, String from, String To);

	public Double Driver_Point_Over_Monthly(int driver_id, String from, String To, Integer companyId);

	public DriverAttendance validate_DriverAttendance_Halt_details(int driver_id, String driver_Date, short openType, int companyId);

	/*public void Update_DriverAttendance_Halt_point(String ATTENDANCE_STATUS, String POINT_STATUS, String POINT_TYPE,
			double DRIVER_POINT, Long daid);
	*/
	public void Update_DriverAttendance_Halt_point(short ATTENDANCE_STATUS, short POINT_STATUS, short POINT_TYPE,
			double DRIVER_POINT, Long daid, int companyId);

	/**
	 * @param tripSheetID
	 */
	public void DELETE_DRIVERATTENDANCE_TRIPSHEET_ID(Long tripSheetID, int companyId);

	/**
	 * @param haltBataID
	 */
	public void DELETE_DRIVERATTENDANCE_LOCAL_HALTBATA_ID(Long haltBataID);
	
	public HashMap<String, Object> getAttandanceReport(HashMap<String, Object>  inObject) throws Exception;
	
	public List<DriverAttendanceDto> Driver_MONTH_WISE_ATTENDANCE_ReportDate(long dRIVER_GROUP, Integer jobTitle, String dateRangeFrom,
			String dateRangeTo, Integer companyId);

	public TripSheetDto Driver_Trip_Details_Over_Monthly(Integer driver_id, String fromDate, String endDate, HashMap<String, Object> configuration) throws Exception;
	
	public List<DriverAttendanceDto>  getDriverAttandanceListOfTripSheet(Long tripSheetId, Integer companyId) throws Exception;
	
	public List<DriverAttendanceDto> getDriverAttandanceOfMonth(TripSheetIncomeDto incomeDto) throws Exception;

	public Double getTripSheetByDriver(Integer driver_id, String driverStart, String driverEnd,
			HashMap<String, Object> configuration, Integer companyId)throws Exception;

}
