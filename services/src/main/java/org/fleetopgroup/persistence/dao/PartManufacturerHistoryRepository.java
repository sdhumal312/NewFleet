package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.PartManufacturerHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartManufacturerHistoryRepository   extends JpaRepository<PartManufacturerHistory, Integer> {
	
}