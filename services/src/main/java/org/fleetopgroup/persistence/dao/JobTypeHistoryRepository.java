package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.JobTypeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobTypeHistoryRepository extends JpaRepository<JobTypeHistory, Integer> {

}