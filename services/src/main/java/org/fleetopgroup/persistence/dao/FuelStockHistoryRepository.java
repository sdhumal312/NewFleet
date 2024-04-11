package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.FuelStockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuelStockHistoryRepository extends JpaRepository<FuelStockHistory, Long> {

}
