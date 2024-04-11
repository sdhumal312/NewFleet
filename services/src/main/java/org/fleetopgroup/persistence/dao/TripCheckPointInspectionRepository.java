package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripCheckPointInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TripCheckPointInspectionRepository extends JpaRepository<TripCheckPointInspection, Integer> {

	@Query("From TripCheckPointInspection TCP where tripCheckPointId = ?1 and TCP.tripSheetId = ?2  and TCP.companyId = ?3 and TCP.markForDelete = 0 ")
	public TripCheckPointInspection getTripCheckPointInspection(long tripCheckPointId, long tripsheetId, int companyId);

	@Query(nativeQuery = true, value = "SELECT *  From TripCheckPointInspection TCPI where TCPI.tripCheckPointId =?1 AND TCPI.companyId = ?2 AND TCPI.markForDelete = 0 LIMIT 1")
	public TripCheckPointInspection getAvailabilityOfTripCheckPointInInspection(long tripCheckPointId, Integer companyId);

	@Modifying
	@Query("UPDATE TripCheckPointInspection TCPI SET TCPI.markForDelete = 1  where TCPI.tripCheckPointInspectionId = ?1")
	public void removeInspectedCheckPoint(long tripCheckPointId);


}
