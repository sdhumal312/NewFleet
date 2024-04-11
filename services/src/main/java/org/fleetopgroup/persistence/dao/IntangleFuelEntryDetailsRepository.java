package org.fleetopgroup.persistence.dao;


import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.IntangleFuelEntryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IntangleFuelEntryDetailsRepository extends JpaRepository<IntangleFuelEntryDetails, Integer> {
	

	@Query(nativeQuery = true, value = "select * From IntangleFuelEntryDetails I where I.IntangleFuelApiId = ?1 AND I.vid = ?2 AND I.FuelDate = ?3 AND I.odometer = ?4 AND I.amount = ?5 AND I.companyId =?6 AND I.markForDelete = 0 LIMIT 1 " )
	IntangleFuelEntryDetails validateIntangleFuelEntryDetails(String id, Integer vehicelId, Timestamp fuelDate, long odometer, double amount,Integer companyId);
}
