package org.fleetopgroup.persistence.dao;

import java.sql.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.TripCollectionSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripCollectionSheetRepository extends JpaRepository<TripCollectionSheet, Long> {

	@Modifying
	@Query("update TripCollectionSheet Set TOTAL_EXPENSE= ?1 WHERE TRIPCOLLID = ?2 AND COMPANY_ID = ?3")
	public void update_TripCollection_TotalExpense(Double tripTotalExpense, Long TRIPCOLLID, Integer companyId) throws Exception;

	/*@Query("FROM TripCollectionSheet AS T "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = T.VEHICLE_GROUP_ID AND VGP.user_id = ?2"
			+ " WHERE T.TRIPCOLLID = ?1 AND T.COMPANY_ID = ?3 AND T.markForDelete = 0")
	public TripCollectionSheet Get_Showing_TripCollection_Sheet(Long TRIPCOLLID, long userId, Integer companyId) throws Exception;
	*/
	@Query("FROM TripCollectionSheet AS T WHERE T.TRIPCOLLID = ?1 AND T.COMPANY_ID = ?2 AND T.markForDelete = 0")
	public TripCollectionSheet Get_Showing_TripCollection_Sheet(Long TRIPCOLLID, Integer companyId) throws Exception;
	
	/*@Query("FROM TripCollectionSheet AS T "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = T.VEHICLE_GROUP_ID AND VGP.user_id = ?2"
			+ " WHERE T.TRIPCOLLNUMBER = ?1 AND T.COMPANY_ID = ?3 AND T.markForDelete = 0")
	public TripCollectionSheet Get_Showing_TripCollection_Sheetby_Number(Long TRIPCOLLID, long userId, Integer companyId) throws Exception;
*/
	@Query("FROM TripCollectionSheet AS T  WHERE T.TRIPCOLLNUMBER = ?1 AND T.COMPANY_ID = ?2 AND T.markForDelete = 0")
	public TripCollectionSheet Get_Showing_TripCollection_Sheetby_Number(Long TRIPCOLLID, Integer companyId) throws Exception;


	@Modifying
	@Query("update TripCollectionSheet Set TOTAL_INCOME= ?1 WHERE TRIPCOLLID = ?2 AND COMPANY_ID = ?3")
	public void update_TripCollection_TotalIncome(Double tripTotalIncome, Long TRIPCOLLID, Integer companyId) throws Exception;

	@Query("FROM TripCollectionSheet AS T WHERE T.TRIP_CLOSE_STATUS_ID=1 AND T.TRIPCOLLID = ?1 AND T.COMPANY_ID = ?2 AND T.markForDelete = 0")
	public TripCollectionSheet EDIT_TripCollection_Sheet(Long TRIPCOLLID, Integer companyId) throws Exception;

	@Query("FROM TripCollectionSheet AS T WHERE T.TRIP_CLOSE_STATUS_ID=2 AND T.TRIPCOLLID = ?1 AND T.COMPANY_ID = ?2 AND T.markForDelete = 0")
	public TripCollectionSheet ReOpen_TripCollection_Sheet(Long TRIPCOLLID, Integer companyId) throws Exception;

	@Query("FROM TripCollectionSheet as v  "
			+ " INNER JOIN Vehicle VH ON VH.vid = v.VID"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VH.vehicleGroupId AND VGP.user_id = ?2"
			+ " Where v.TRIP_CLOSE_STATUS_ID=?1 AND v.COMPANY_ID = ?3 AND v.markForDelete = 0 ORDER BY v.TRIPCOLLID desc ")
	public Page<TripCollectionSheet> getDeployment_Page_TripCollectionSheet(short status, long id, Integer companyId, Pageable pageable);

	@Query("FROM TripCollectionSheet as v  Where v.TRIP_CLOSE_STATUS_ID=?1 AND v.COMPANY_ID = ?2 AND v.markForDelete = 0 ORDER BY v.TRIPCOLLID desc ")
	public Page<TripCollectionSheet> getDeployment_Page_TripCollectionSheet(short status, Integer companyId, Pageable pageable);

	
	@Modifying
	@Query("update TripCollectionSheet Set TRIP_DRIVER_JAMA= ?1 WHERE TRIPCOLLID = ?2 AND COMPANY_ID = ?3")
	public void update_TripCollection_Driver_JAM(Double driverJAM, Long TRIPCOLLID, Integer companyId) throws Exception;

	@Modifying
	@Query("update TripCollectionSheet Set TRIP_CONDUCTOR_JAMA= ?1 WHERE TRIPCOLLID = ?2 AND COMPANY_ID = ?3")
	public void update_TripCollection_Conductor_JAM(Double ConductorJAM, Long TRIPCOLLID, Integer companyId) throws Exception;

	@Modifying
	@Query("update TripCollectionSheet Set TRIP_CLOSE_KM= ?1, TRIP_USAGE_KM =?2, TRIP_CLOSE_STATUS_ID =?3, TOTAL_BALANCE =?4, LASTMODIFIEDBYID =?5 WHERE TRIPCOLLID = ?6 AND COMPANY_ID = ?7")
	public void update_Close_TripCollection(Integer closingKM, Integer usageKM, short Status, Double balance,
			Long lastmodifiedby, Long tripcollid, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE TripCollectionSheet SET markForDelete = 1 WHERE TRIPCOLLID = ?1 AND COMPANY_ID = ?2")
	public void delete_TripCollectionSheet(Long tripCollectionID, Integer companyId) throws Exception;


	@Modifying
	@Query("UPDATE FROM TripCollectionSheet AS T SET T.DRIVER_ADVANCE_STATUS_ID= ?1 WHERE T.TRIP_DRIVER_ID = ?2 AND T.COMPANY_ID = ?3")
	public void update_Driver_JAMA_Status(short string, int trip_DRIVER_ID, Integer companyId);

	@Modifying
	@Query("UPDATE FROM TripCollectionSheet AS T SET T.CONDUCTOR_ADVANCE_STATUS_ID=?1 WHERE T.TRIP_CONDUCTOR_ID = ?2 AND T.COMPANY_ID = ?3 ")
	public void update_Conductor_JAMA_Status(short string, int trip_DRIVER_ID, Integer companyId);

	@Modifying
	@Query("UPDATE FROM TripCollectionSheet AS T SET T.TRIP_CLOSE_STATUS_ID=?2 WHERE T.TRIPCOLLID = ?1 AND T.COMPANY_ID = ?3")
	public void Update_ReOpen_Status_TripCollectionSheet(Long tripcollid, short trip_CLOSE_STATUS, Integer companyId);

	@Query("FROM TripCollectionSheet AS T WHERE T.TRIP_OPEN_DATE=?1 AND T.VID = ?2 AND T.COMPANY_ID = ?3 AND T.markForDelete = 0")
	public List<TripCollectionSheet> Validate_TripCollectionSheet_Date_VehicleName(Date fromDate, Integer vid, Integer companyId);
	
	@Query("SELECT COUNT(T) FROM TripCollectionSheet AS T "
			+ " INNER JOIN Vehicle VH ON VH.vid = T.VID"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VH.vehicleGroupId AND VGP.user_id = ?1"
			+ " WHERE  T.COMPANY_ID = ?2 AND T.markForDelete = 0")
	public long getCountOfCompanyByVehicleGroupPermision(long userId, Integer companyId)throws Exception;
	
	@Query("SELECT COUNT(T) FROM TripCollectionSheet AS T WHERE  T.COMPANY_ID = ?1 AND T.markForDelete = 0")
	public long getCountOfCompanyByVehicleGroupPermision(Integer companyId)throws Exception;
}
