package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripSheetHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripSheetHistoryRepository extends JpaRepository<TripSheetHistory, Long> {
	
}
