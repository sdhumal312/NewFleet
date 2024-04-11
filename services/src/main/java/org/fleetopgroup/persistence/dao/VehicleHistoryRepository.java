package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VehicleHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleHistoryRepository extends JpaRepository<VehicleHistory, Integer> {

}
