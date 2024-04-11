package org.fleetopgroup.persistence.dao;

import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.DriverAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DriverAttendanceRepository extends JpaRepository<DriverAttendance, Long> {

	// public void addDriverAttendance(DriverAttendance Attendance);

	@Query("From DriverAttendance p  where p.DRIVERID=?1 AND p.ATTENDANCE_DATE= ?2 AND markForDelete = 0")
	public DriverAttendance ValidateDriverAttendance(int driver_id, Date ATTENDANCE_DATE);

	// public void updateDriverAttendance(DriverAttendance driver);

	@Query("From DriverAttendance p  where p.DRIVERID= ?1 AND p.markForDelete = 0")
	public List<DriverAttendance> listDriverAttendance(int driver_id);

	@Query("From DriverAttendance p  where p.DRIVERID= ?1 AND p.markForDelete = 0")
	public List<DriverAttendance> list_Date_DriverAttendance(int driver_id, String from, String To);

	@Query("From DriverAttendance p  where p.DAID= ?1 AND p.markForDelete = 0")
	public DriverAttendance getDriverAttendance(Long driver_id);

	@Modifying
	@Query("UPDATE  DriverAttendance T SET markForDelete = 1 where T.DAID = ?1")
	public void deleteDriverAttendance(Long driver_id);

	/*@Modifying
	@Query("UPDATE FROM DriverAttendance AS T SET T.ATTENDANCE_STATUS=?1, T.POINT_STATUS=?2, T.POINT_TYPE=?3, T.DRIVER_POINT=?4 where T.DAID = ?5 AND T.COMPANY_ID = ?6")
	public void Update_DriverAttendance_Halt_point(String ATTENDANCE_STATUS, String POINT_STATUS, String POINT_TYPE,
			double DRIVER_POINT, Long daid, Integer companyId);
	*/
	@Modifying
	@Query("UPDATE FROM DriverAttendance AS T SET T.ATTENDANCE_STATUS_ID=?1, T.POINT_STATUS_ID=?2, T.POINT_TYPE_ID=?3, T.DRIVER_POINT=?4 where T.DAID = ?5 AND T.COMPANY_ID = ?6")
	public void Update_DriverAttendance_Halt_point(short ATTENDANCE_STATUS, short POINT_STATUS, short POINT_TYPE,
			double DRIVER_POINT, Long daid, Integer companyId);


	/**
	 * @param tripSheetID
	 */
	@Modifying
	@Query("UPDATE DriverAttendance T SET T.markForDelete = 1 where T.TRIPSHEETID = ?1 AND T.COMPANY_ID = ?2")
	public void DELETE_DRIVERATTENDANCE_TRIPSHEET_ID(Long tripSheetID, Integer companyID);

	@Modifying
	@Query("UPDATE DriverAttendance T SET T.markForDelete = 1 where T.DHID = ?1 AND T.COMPANY_ID = ?2")
	public void DELETE_DRIVERATTENDANCE_LOCAL_HALTBATA_ID(Long haltBataID, Integer company_id);
	
	@Modifying
	@Query("UPDATE DriverAttendance T SET T.markForDelete = 1 where T.TRIPSHEETID = ?1 AND T.DRIVERID = ?2 AND T.COMPANY_ID = ?3 AND T.markForDelete = 0 ")
	public void deleteDriverAttendanceRoutePoint(Long tripSheetID, int driver_id, Integer companyID);
	
	@Modifying
	@Query("UPDATE DriverAttendance T SET T.DRIVER_POINT = ?1, T.TRIP_ROUTE_ID = ?2 where T.TRIPSHEETID = ?3 AND T.DRIVERID = ?4 AND T.COMPANY_ID = ?5 AND T.markForDelete = 0 ")
	public void updateDriverAttendanceRoutePoint(double routePoint, int routeId, Long tripSheetID, int driver_id, Integer companyID);

}
