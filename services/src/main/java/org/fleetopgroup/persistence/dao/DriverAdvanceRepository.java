package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.DriverAdvance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DriverAdvanceRepository extends JpaRepository<DriverAdvance, Long> {

	@Modifying
	@Query("update DriverAdvance DDT set DDT.ADVANCE_STATUS_ID = ?1 where DDT.TRIP_DRIVER_ID = ?2 AND DDT.COMPANY_ID = ?3")
	void update_DriverAdvance_Status(short advance_Status, Integer driver_ID, Integer companyId) throws Exception;

}
