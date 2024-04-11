package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VendorPaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorPaymentHistoryRepository extends JpaRepository<VendorPaymentHistory, Long> {
	
}
