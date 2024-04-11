package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.JobSubTypeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobSubTypeHistoryRepository extends JpaRepository<JobSubTypeHistory, Integer> {
	
}