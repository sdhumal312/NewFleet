package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.MasterPartsExtraDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MasterPartsExtraDetailsRepository extends JpaRepository<MasterPartsExtraDetails, Long> {

	@Modifying
	@Query("update MasterPartsExtraDetails set markForDelete = 1 where partid = ?1")
	public void deleteExtraDetailsByPartId(Long partid) throws Exception;
}
