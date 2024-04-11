package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripExpenseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripExpenseHistoryRepository extends JpaRepository<TripExpenseHistory, Integer> {

}