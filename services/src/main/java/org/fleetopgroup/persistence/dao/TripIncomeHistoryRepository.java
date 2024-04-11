package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripIncomeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripIncomeHistoryRepository extends JpaRepository<TripIncomeHistory, Integer> {

}