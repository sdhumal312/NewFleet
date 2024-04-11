package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleAccidentAdvance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleAccidentAdvanceRepository extends JpaRepository<VehicleAccidentAdvance, Long> {

	@Query("from VehicleAccidentAdvance where accidentId = ?1 and markForDelete = 0")
	public List<VehicleAccidentAdvance>  findByAccidentId(Long accidentId) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleAccidentAdvance SET markForDelete = 1 where vehicleAccidentAdvanceId = ?1")
	public void removeAdvance(Long advanceId);
	
	@Query("select COUNT(vehicleAccidentAdvanceId) from  VehicleAccidentAdvance where accidentId = ?1 AND markForDelete = 0")
	public long getAdvanceCountByAccidentId(Long accidentId) throws Exception;
}
