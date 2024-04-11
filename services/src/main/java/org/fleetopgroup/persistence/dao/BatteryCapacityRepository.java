package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.BatteryCapacity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BatteryCapacityRepository extends JpaRepository<BatteryCapacity, Long>{

	@Query("from BatteryCapacity where markForDelete = 0")
	List<BatteryCapacity> findAll();
	
	@Query("from BatteryCapacity where batteryCapacity = ?1 AND markForDelete = 0")
	public List<BatteryCapacity> validateBatteryCapacity(String capacity) throws Exception; 
}
