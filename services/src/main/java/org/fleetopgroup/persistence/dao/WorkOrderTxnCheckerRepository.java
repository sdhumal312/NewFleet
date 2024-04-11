package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.WorkOrderTxnChecker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkOrderTxnCheckerRepository extends JpaRepository<WorkOrderTxnChecker, Long>{

}
