package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.SubstitudePartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubstitudePartDetailsRepository extends JpaRepository<SubstitudePartDetails, Long> {

	@Modifying
	@Query("UPDATE FROM SubstitudePartDetails set markForDelete = 1 where mainPartId = ?1 ")
	public void deleteByMainPartId(Long partId) throws Exception;
}
