package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.TripSheetOptionsExtra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripSheetOptionsExtraRepository extends JpaRepository<TripSheetOptionsExtra, Long> {

	
	
	@Query("from TripSheetOptionsExtra where tripsheetoptionsID= ?1 AND tripSheetID= ?2 AND companyId = ?3 AND markForDelete = 0 ")
	public List<TripSheetOptionsExtra> ValidateAllTripSheetExtraOptions(Long tripsheetextraname, Long tripSheetID, Integer companyId) throws Exception;

	@Query("FROM TripSheetOptionsExtra T WHERE T.tripExtraID = ?1 AND T.companyId = ?2")
	public TripSheetOptionsExtra getTripSheetExtra(Long tripExtraID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE TripSheetOptionsExtra T SET T.markForDelete = 1 WHERE T.tripExtraID = ?1 AND T.companyId = ?2")
	public void deleteTripSheetExtra(Long tripExtraID, Integer companyId) throws Exception;
	
	
}