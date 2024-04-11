package org.fleetopgroup.persistence.dao;


import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.IntangleTripDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IntangleTripDetailsRepository extends JpaRepository<IntangleTripDetails, Integer> {
	
	@Query(nativeQuery = true, value = "select * From IntangleTripDetails I where I.vehicleNumber = ?1 AND I.tripDate = ?2 AND I.markForDelete = 0 LIMIT 1 " )
	IntangleTripDetails validateIntangleTripMileageDetails(String vehicleNumber,  Timestamp date);
}
