package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.SpotSurveyorDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpotSurveyorDetailsRepository extends JpaRepository<SpotSurveyorDetails, Long>{

	
	@Query("FROM SpotSurveyorDetails where accidentId = ?1 AND companyId =?2 AND markForDelete = 0")
	public SpotSurveyorDetails getSpotSurveyorDetails(long accidnetId, int companyId);

}
