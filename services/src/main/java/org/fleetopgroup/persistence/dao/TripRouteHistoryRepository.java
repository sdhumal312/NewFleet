package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripRouteHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRouteHistoryRepository extends JpaRepository<TripRouteHistory, Integer> {

}