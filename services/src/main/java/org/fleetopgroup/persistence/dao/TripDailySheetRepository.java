/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.TripDailySheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TripDailySheetRepository extends JpaRepository<TripDailySheet, Long> {

	@Modifying
	@Query("update TripDailySheet Set TOTAL_EXPENSE= ?1 WHERE markForDelete=0 AND TRIPDAILYID = ?2")
	public void update_TripDailySheet_TotalExpense(Double tripTotalExpense, Long tripdailyid);

	@Query("FROM TripDailySheet  AS T "
			+ " INNER JOIN Vehicle V ON V.vid = T.VEHICLEID"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?2"
			+ " WHERE T.markForDelete=0 AND T.TRIPDAILYID = ?1 AND T.COMPANY_ID = ?3")
	public TripDailySheet Get_Showing_TripDaily_Sheet(Long tripdailyid, long id, Integer companyId);
	
	@Query("FROM TripDailySheet  AS T WHERE T.markForDelete=0 AND T.TRIPDAILYID = ?1 AND T.COMPANY_ID = ?2")
	public TripDailySheet Get_Showing_TripDaily_Sheet(Long tripdailyid, Integer companyId);
	
	
	@Query("FROM TripDailySheet  AS T "
			+ " INNER JOIN Vehicle V ON V.vid = T.VEHICLEID"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?2"
			+ " WHERE T.markForDelete=0 AND T.TRIPDAILYNUMBER = ?1 AND T.COMPANY_ID = ?3")
	public TripDailySheet Get_Showing_TripDaily_SheetByNumber(Long tripdailyid, long id, Integer companyId);

	@Query("FROM TripDailySheet  AS T WHERE T.markForDelete=0 AND T.TRIPDAILYNUMBER = ?1 AND T.COMPANY_ID = ?2")
	public TripDailySheet Get_Showing_TripDaily_SheetByNumber(Long tripdailyid, Integer companyId);


	@Modifying
	@Query("update TripDailySheet Set TOTAL_INCOME= ?1 WHERE markForDelete=0 AND TRIPDAILYID = ?2")
	public void update_TripDaily_TotalIncome(Double tripTotalIncome, Long tripdailyid);

	@Query("FROM TripDailySheet AS T WHERE T.markForDelete=0 AND T.TRIP_OPEN_DATE= ?1 AND T.VEHICLEID = ?2")
	public List<TripDailySheet> Validate_TripCollectionSheet_Date_VehicleName(Date fromDate, Integer vehicleid);

	@Modifying
	@Query("update TripDailySheet Set TRIP_CLOSE_KM= ?1, TRIP_USAGE_KM =?2, TRIP_STATUS_ID =?3, TOTAL_NET_INCOME=?4, TOTAL_BALANCE =?5, LASTUPDATED =?6, LASTMODIFIED_BY_ID = ?9"
			+ " , noOfRoundTrip =?10 WHERE"
			+ " markForDelete=0 AND TRIPDAILYID = ?7 AND COMPANY_ID = ?8")
	public void Update_Close_TripDailySheet(Integer closingKM, Integer usageKM, short string,Double TOTAL_NET_INCOME, Double balance,
			 Timestamp LASTUPDATED, Long tripdailyid, Integer companyId, Long lastUpdatedById, float noOfRoundTrip);

	@Query("FROM TripDailySheet AS T WHERE T.markForDelete=0 AND T.TRIP_STATUS_ID = 2 AND T.TRIPDAILYID = ?1 AND COMPANY_ID = ?2")
	public TripDailySheet ReOpen_TripDaily_Sheet_ClosedOnly(Long tripCollectionID, Integer companyId);

	@Modifying
	@Query("UPDATE FROM TripDailySheet AS T SET T.TRIP_STATUS_ID=?2 WHERE T.markForDelete=0 AND T.TRIPDAILYID = ?1 AND T.COMPANY_ID = ?3")
	public void Update_ReOpen_Status_TripDailySheet(Long tripdailyid, short string, Integer companyId);

	@Query("FROM TripDailySheet as T  "
			+ " INNER JOIN Vehicle V ON V.vid = T.VEHICLEID"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?2"
			+ " Where T.markForDelete=0 AND T.TRIP_STATUS_ID=?1 AND T.COMPANY_ID = ?3 ORDER BY T.TRIPDAILYID desc ")
	public Page<TripDailySheet> getDeployment_Page_TripDailySheet(short status, long id, Integer companyId, Pageable pageable);

	@Query("FROM TripDailySheet as v  Where v.markForDelete=0 AND v.TRIP_STATUS_ID=?1 AND v.COMPANY_ID = ?2 ORDER BY v.TRIPDAILYID desc ")
	public Page<TripDailySheet> getDeployment_Page_TripDailySheet(short status, Integer companyId, Pageable pageable);

	
	@Modifying
	@Query("UPDATE FROM TripDailySheet AS T SET T.markForDelete=1, T.LASTMODIFIED_BY_ID=?1, LASTUPDATED=?2  WHERE T.TRIPDAILYID = ?3 AND COMPANY_ID = ?4")
	public void delete_TripDailySheet_TRIPDAILYID(Long DeletedBy, Timestamp deletedtime, Long tripCollectionID, Integer companyId);
	
	@Query("select sum(TS.TRIP_USAGE_KM) from TripDailySheet TS where TS.VEHICLEID = ?1 AND TS.TRIP_OPEN_DATE between ?2 AND ?3 AND TS.markForDelete = 0 AND TS.TRIP_STATUS_ID = 2")
	public Integer getVehicleRunKMByVid(Integer vid, Timestamp fromDate, Timestamp toDate) throws Exception;


}
