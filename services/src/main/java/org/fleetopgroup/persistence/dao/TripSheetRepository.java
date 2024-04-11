package org.fleetopgroup.persistence.dao;

import java.sql.Date;
import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripSheetRepository extends JpaRepository<TripSheet, Long> {


	@Query("FROM TripSheet T WHERE T.tripSheetID = ?1 AND T.companyId = ?2 AND T.markForDelete = 0")
	public TripSheet getTripSheet(Long TripSheet_id, Integer companyId) throws Exception;
	
	@Query("FROM TripSheet T WHERE T.tripSheetID = ?1 AND T.markForDelete = 0")
	public TripSheet getTripSheetDetails(Long TripSheet_id) throws Exception;


//	@Query("FROM TripSheet as v "
//			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = ?1"
//			+ " Where v.tripStutesId= 1 AND v.companyId = ?2 AND v.markForDelete = 0 ORDER BY v.tripSheetID desc")
//	public List<TripSheet> listTripSheetDispatch(long user_Id, Integer companyId) throws Exception;
//	
//	@Query("FROM TripSheet as v Where v.tripStutesId=1 AND v.companyId = ?1 AND v.markForDelete = 0 ORDER BY v.tripSheetID desc")
//	public List<TripSheet> listTripSheetDispatch(Integer companyId) throws Exception;


//	@Query("FROM TripSheet as v  Where v.tripStutesId=2 ORDER BY v.tripSheetID desc ")
//	public List<TripSheet> listTripSheetManage() throws Exception;

//	@Query("FROM TripSheet as v  Where v.tripStutesId=3 ORDER BY v.tripSheetID desc ")
//	public List<TripSheet> listTripSheetClose() throws Exception;

	@Query("select v.tripSheetID FROM TripSheet as v "
			+ " INNER JOIN Vehicle VH ON VH.vid = v.vid"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VH.vehicleGroupId AND VGP.user_id = ?2"
			+ " Where v.tripStutesId=?1 AND v.companyId = ?3 AND v.markForDelete = 0 ORDER BY v.tripSheetID DESC ")
	public Page<TripSheet> getDeployment_Page_TripSheet(short status,long userId,Integer companyId, Pageable pageable);
	
	@Query("select v.tripSheetID FROM TripSheet as v Where v.tripStutesId=?1 AND v.companyId = ?2 AND v.markForDelete = 0 ORDER BY v.tripSheetID DESC ")
	public Page<TripSheet> getDeployment_Page_TripSheet(short status, Integer companyId, Pageable pageable);


	@Query("FROM TripSheet as v  Where v.tripStutesId=4 ORDER BY v.tripSheetID desc ")
	public List<TripSheet> listTripSheetCloseAccount() throws Exception;

	@Modifying
	@Query("UPDATE TripSheet SET markForDelete = 1 , lastModifiedById = ?3 ,lastupdated = ?4 WHERE tripSheetID = ?1 AND companyId = ?2")
	public void deleteTripSheet(Long Approval_ID, Integer companyId ,Long userId ,Timestamp date) throws Exception;

	@Query("select count(TS) from TripSheet TS "
			+ " INNER JOIN Vehicle V ON V.vid = TS.vid"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?1"
			+ " where TS.companyId = ?2 AND TS.markForDelete = 0")
	public Long countTripSheet(long id, Integer companyId) throws Exception;
	
	@Query("select count(TS) from TripSheet TS where TS.companyId = ?1 AND TS.markForDelete = 0")
	public Long countTripSheet(Integer companyId) throws Exception;


	@Modifying
	@Query("update TripSheet Set closeACCTripNameById= ?1 , tripStutesId= ?2, closeACCTripAmount=  ?3, closeACCTripReference= ?4, "
			+ "closeACCtripDate= ?5 , lastModifiedById=?6 , lastupdated=?7, acclosedLocationId = ?10 WHERE tripSheetID = ?8 AND companyId =?9")
	public void updateCloseAccountTrip(Long closeAccountBY, short closeAccountStatus, Double amount,
			String closeReference, Timestamp toDate, Long LASTUPDATED, Timestamp LASTUPDATED_DATE, Long tripSheetID, Integer companyId, Integer acclocation) throws Exception;

	@Modifying
	@Query("update TripSheet Set tripClosingKM= ?2, tripUsageKM= ?3, tripClosingKMStatusId= ?4, closeTripStatusId= ?5,closeTripReference= ?6, "
			+ "closeTripAmount= ?7, closeTripNameById= ?8, closetripDate= ?9 , tripStutesId= 3, lastModifiedById=?10 ,"
			+ "lastupdated=?11, closedById=?12, closedLocationId=?13, closedByTime=?14, tripGpsClosingKM = ?16, gpsCloseLocation =?17, "
			+ "gpsTripUsageKM = ?18, driverBalance = ?19 WHERE tripSheetID = ?1 AND companyId = ?15")
	public void updateCloseTripSheet(Long TripSheetID, Integer CloseingKm, Integer UsageKM, short CloseingKmStatus,
			short paidto, String CloseRefenceNo, Double amount, Long paidby, Date Closed_data, Long LASTUPDATED,
			java.util.Date LASTUPDATED_DATE, Long ClosedBy, Integer ClosedLocation, java.util.Date ClosedByTime,
			Integer companyId, Double closingGPSKM, String gpsClosingLocation, Double gpsUsageKM, Double driverBalance)
			throws Exception;

	@Query("FROM TripSheet as v  Where v.vid= ?1 AND v.companyId = ?2 AND v.markForDelete = 0 ORDER BY v.tripSheetID desc ")
	public List<TripSheet> VehicleTOlistTripSheet(Integer vid, Integer companyId) throws Exception;

	public List<TripSheet> findAll();

	/**
	 * Update Vehicle Edit to Vehicle Group using vehicle Id
	 **//*
	@Modifying
	@Query("update TripSheet Set vehicle_Group= ?1, vehicleGroupId = ?3 WHERE vid = ?2 ")
	public void update_Vehicle_Group_USING_Vehicle_Id(String Vehicle_Group, Integer vehicle_id, long vehicleGroupId);
*/
	@Query("FROM TripSheet as v  Where v.vid= ?1 AND v.tripStutesId=2 AND v.companyId = ?2 AND v.markForDelete = 0 ORDER BY v.tripSheetID desc ")
	public List<TripSheet> Get_Validate_TripSheet_Vehicle_DriverAcount(Integer vehicleID, Integer companyId);
	
	/*@Query("FROM TripSheet as v  Where v.vehicle_registration= ?1 AND v.tripStutesId=2 AND v.companyId = ?2 AND v.markForDelete = 0 ORDER BY v.tripSheetID desc ")
	public List<TripSheet> Get_Validate_TripSheet_Vehicle(String vehicleID, Integer companyId);
*/

	@Modifying
	@Query("update TripSheet Set tripFristDriverBata= ?1 WHERE tripSheetID = ?2 AND companyId = ?3")
	public void Update_TripSheet_FIRST_Driver_BATA(double parseDouble, Long tripSheetID, Integer companyId);

	/**
	 * @param parseDouble
	 * @param tripSheetID
	 *            SECOND DRIVER BATA DETAILS TO UPDATE TRIP AMOUNT
	 */
	@Modifying
	@Query("update TripSheet Set tripSecDriverBata= ?1 WHERE tripSheetID = ?2 AND companyId = ?3")
	public void Update_TripSheet_SECOND_Driver_BATA(double parseDouble, Long tripSheetID, Integer companyId);

	/**
	 * @param parseDouble
	 * @param tripSheetID
	 *            CLEANER BATA DETAILS TO UPDATE TRIP AMOUNT
	 */
	@Modifying
	@Query("update TripSheet Set tripCleanerBata= ?1 WHERE tripSheetID = ?2 AND companyId = ?3")
	public void Update_TripSheet_CLEANER_BATA(double parseDouble, Long tripSheetID, Integer companyId);
	

	@Query("select T FROM TripSheet T "
			+ " INNER JOIN Vehicle V ON V.vid = T.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?3"
			+ " WHERE T.tripSheetNumber = ?1 AND T.companyId = ?2 AND T.markForDelete = 0")
	public TripSheet getTripSheetByNumber(Long TripSheet_id, Integer companyId, long id) throws Exception;

	@Query("select T FROM TripSheet T WHERE T.tripSheetNumber = ?1 AND T.companyId = ?2 AND T.markForDelete = 0")
	public TripSheet getTripSheetByNumber(Long TripSheet_id, Integer companyId) throws Exception;

	@Query("select count(T) FROM TripSheet T WHERE T.tripSheetNumber = ?1 AND T.companyId = ?2 AND T.markForDelete = 0")
	public int countTripSheetByNumber(Long tripSheetNumber , Integer companyId);


	@Query("select sum(TS.tripUsageKM) from TripSheet TS where TS.vid = ?1 AND TS.closetripDate between ?2 AND ?3 AND TS.markForDelete = 0 AND TS.tripStutesId IN (3,4)")
	public Integer getVehicleRunKMByVid(Integer vid, Timestamp fromDate, Timestamp toDate) throws Exception;

	@Modifying
	@Query("UPDATE TripSheet SET markForDelete = 1, deleteRemark = ?3, lastModifiedById = ?2 WHERE tripSheetID = ?1")
	public void superUserdeleteTripSheet(Long tripSheetID, Long userId, String remark) throws Exception;

	@Modifying
	@Query("Update TripSheet set tripSheetDocumentId= ?1, tripSheetDocument= ?2 where tripSheetID= ?3 AND companyId = ?4")
	public void updateTripSheetDocumentId(Long tripSheetDocId, boolean tripSheetDocument, Long tripSheetId, Integer companyId);
	
	@Modifying
	@Query("update TripSheet Set closeACCTripNameById= ?1 , tripStutesId= ?2, closeACCTripAmount=  ?3, closeACCTripReference= ?4, "
			+ "closeACCtripDate= ?5 , lastModifiedById=?6 , lastupdated=?7, acclosedLocationId = ?10 WHERE tripSheetID = ?8 AND companyId =?9")
	public void updateTripSheetStatusToClose(Short closeAccountBY, short trisheetStatusId, Double amount,
			String closeReference, Timestamp toDate, Long LASTUPDATED, Timestamp LASTUPDATED_DATE, 
			Long tripSheetID, Integer companyId, Integer acclocation) throws Exception;
	
	@Query("SELECT T.driverBalance FROM TripSheet AS T WHERE T.tripSheetID =?1  AND T.companyId= ?2")
	public Double getDriverBalance(long tripsheetId, int companyId) throws Exception;
	
	@Query("select T FROM TripSheet T WHERE T.lhpvId  = ?1 AND T.markForDelete = 0")
	public TripSheet getTripSheetByLHPV(Integer lhpvId) throws Exception;
	
	@Query("SELECT T.tripSheetNumber FROM TripSheet AS T WHERE T.companyId = ?1 AND T.tripSheetID = ?2 AND T.markForDelete = 0")
	public String getTripSheet_Number(Integer companyId, Long tripSheetID ) throws Exception;
	
	@Query("Select v.vid from TripSheet v where v.vid= ?1 and v.companyId = ?2 and v.markForDelete = 0")
	public Page<TripSheet> getDeployment_Page_Vehicle_Status(Integer vid,Integer companyId, Pageable pageable);
	
	@Query("select count(TS) from TripSheet TS where TS.vid = ?2 AND TS.companyId = ?1 AND TS.markForDelete = 0")
	public Long countTripSheetonVehicle(Integer vid, Integer companyId) throws Exception;
	
}
