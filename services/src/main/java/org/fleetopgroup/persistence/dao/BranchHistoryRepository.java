package org.fleetopgroup.persistence.dao;


import org.fleetopgroup.persistence.model.BranchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchHistoryRepository extends JpaRepository<BranchHistory, Integer> {

}
