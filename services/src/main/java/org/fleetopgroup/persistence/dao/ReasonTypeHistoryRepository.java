package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.JobTypeHistory;
import org.fleetopgroup.persistence.model.ReasonTypeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReasonTypeHistoryRepository extends JpaRepository<ReasonTypeHistory,Integer> {

}