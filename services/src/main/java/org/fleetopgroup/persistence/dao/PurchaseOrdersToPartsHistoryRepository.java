package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.PurchaseOrdersToPartsHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrdersToPartsHistoryRepository extends JpaRepository<PurchaseOrdersToPartsHistory, Long> {

}
