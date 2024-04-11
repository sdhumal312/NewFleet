package org.fleetopgroup.persistence.dao;


/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.TripSheetAdvance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;



public interface TripSheetAdvanceRepository  extends JpaRepository<TripSheetAdvance, Long>{

	//public void addTripSheetAdvance(TripSheetAdvance TripSheetAdvance) throws Exception;

	//public void updateTripSheetAdvance(TripSheetAdvance TripSheetAdvance) throws Exception;

	@Query("FROM TripSheetAdvance T WHERE T.tripAdvanceID = ?1 AND T.companyId =?2 AND T.markForDelete = 0")
	public TripSheetAdvance getTripSheetAdvance(Long TripSheet_Advanceid, Integer companyId) throws Exception;

	//public List<Double> ReportTripAdvanceTotal(String ReportQuery);

	@Modifying
	@Query("UPDATE TripSheetAdvance SET markForDelete = 1 WHERE tripAdvanceID = ?1 AND companyId = ?2")
	public void deleteTripSheetAdvance(Long TripSheet_Advanceid, Integer companyId) throws Exception;

	@Query("from TripSheetAdvance where tripSheetID= ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<TripSheetAdvance> findAllTripSheetAdvance(Long tripSheetID, Integer companyId) throws Exception;

	@Modifying
	@Query("update TripSheet Set tripTotalAdvance= ?1 WHERE tripSheetID = ?2")
	public void updateTotalAdvance(Double tripTotalAdvance, Long tripSheetID) throws Exception;

	
	/**
	 * @param tripSheetID
	 */
	@Modifying
	@Query("UPDATE TripSheetAdvance SET markForDelete = 1 WHERE tripSheetID = ?1 AND companyId = ?2")
	public void Delete_TSID_to_TripSheetAdvance(Long tripSheetID, Integer companyId);
	
	@Modifying
	@Query("UPDATE TripSheetAdvance SET driverId = ?1 WHERE tripSheetID =?2 AND companyId = ?3 AND markForDelete = 0")
	public void updateDriverInTripSheetAdvance(int newDriverId, Long tripSheetId,Integer companyId);
	
	
}
