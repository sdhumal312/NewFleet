package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.DriverHistory;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DriverHistoryRepository  extends JpaRepository<DriverHistory, Integer> {
	
	
	
}
