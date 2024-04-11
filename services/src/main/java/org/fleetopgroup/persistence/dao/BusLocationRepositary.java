package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.BusLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BusLocationRepositary extends JpaRepository<BusLocation, Long>{

	
	@Modifying
	@Query("UPDATE BusLocation SET busOutDateTime = ?1 WHERE busLocationId = ?2 AND companyId = ?3")
	public void updateBusLocationOutTime(Timestamp busOutDateTime, Long busLocationId, Long companyId);
}
