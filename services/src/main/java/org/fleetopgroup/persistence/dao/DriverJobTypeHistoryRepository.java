package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.DriverJobTypeHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DriverJobTypeHistoryRepository  extends JpaRepository<DriverJobTypeHistory, Integer>{
	
}