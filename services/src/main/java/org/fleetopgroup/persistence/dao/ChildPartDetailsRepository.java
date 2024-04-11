package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.ChildPartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ChildPartDetailsRepository extends JpaRepository<ChildPartDetails, Long> {

	@Modifying
	@Query("UPDATE FROM ChildPartDetails set markForDelete = 1 where mainPartId = ?1 ")
	public void deleteByMainPartId(Long partId) throws Exception;
	
	@Modifying
	@Query("UPDATE FROM ChildPartDetails set markForDelete = 1 where partId = ?1 ")
	public void deleteByChildPartId(Long partId) throws Exception;
}
