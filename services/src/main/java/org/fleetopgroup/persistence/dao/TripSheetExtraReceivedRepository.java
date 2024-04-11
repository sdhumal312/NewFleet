package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.TripSheetExtraReceived;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TripSheetExtraReceivedRepository extends JpaRepository<TripSheetExtraReceived, Long> {

	
	
	@Query("from TripSheetExtraReceived where tripsheetoptionsID= ?1 AND tripSheetID= ?2 AND companyId = ?3 AND markForDelete = 0 ")
	public List<TripSheetExtraReceived> ValidateAllTripSheetExtraOptionsReceived(Long tripsheetextraname, Long tripSheetID, Integer companyId) throws Exception;
	
	
	
}