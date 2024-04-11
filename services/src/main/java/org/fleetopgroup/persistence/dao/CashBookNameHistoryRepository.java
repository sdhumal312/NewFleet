package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.CashBookNameHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashBookNameHistoryRepository extends JpaRepository<CashBookNameHistory, Integer> {
	
}
