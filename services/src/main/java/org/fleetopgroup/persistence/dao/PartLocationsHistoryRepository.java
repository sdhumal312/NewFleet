package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.PartLocationsHistory;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PartLocationsHistoryRepository  extends JpaRepository<PartLocationsHistory, Integer> {

}