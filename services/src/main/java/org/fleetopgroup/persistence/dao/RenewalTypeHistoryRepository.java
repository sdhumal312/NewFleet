package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.RenewalTypeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenewalTypeHistoryRepository extends JpaRepository<RenewalTypeHistory, Integer> {

}
