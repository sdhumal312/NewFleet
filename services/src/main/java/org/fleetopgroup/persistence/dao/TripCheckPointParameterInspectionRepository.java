package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripCheckPointParameterInspection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TripCheckPointParameterInspectionRepository extends JpaRepository<TripCheckPointParameterInspection, Integer> {

	@Query(nativeQuery = true, value = "SELECT *  From TripCheckPointParameterInspection TCPI where TCPI.tripCheckPointParameterId =?1 AND TCPI.companyId = ?2 AND TCPI.markForDelete = 0 LIMIT 1")
	public TripCheckPointParameterInspection getAvailabilityOfTripCheckPointParamterInInspection(long tripCheckPointParameterId, Integer companyId);

	@Modifying
	@Query("UPDATE TripCheckPointParameterInspection TCPI SET TCPI.markForDelete = 1  where TCPI.tripCheckPointParameterInspectionId = ?1")
	public void removeInspectedParameter(long tripCheckPointParameterInspectionId);

	@Query(nativeQuery = true, value = "SELECT *  From TripCheckPointParameterInspection TCPI where TCPI.TripCheckPointInspectionId =?1 AND TCPI.markForDelete = 0 LIMIT 1")
	public TripCheckPointParameterInspection getAvailabilityOfParamterByTripCheckPointInspectionId(long tripCheckPointInspection);
	
	@Query(nativeQuery = true, value = "SELECT *  From TripCheckPointParameterInspection TCPI where TCPI.tripCheckPointParameterId =?1 AND TCPI.TripCheckPointInspectionId =?2 AND TCPI.companyId = ?3 AND TCPI.markForDelete = 0 LIMIT 1")
	public TripCheckPointParameterInspection validateDuplicateParameterByTripCheckPointInspectionId(long tripCheckPointParameterId, long tripCheckPointInspection, Integer companyId);

}

