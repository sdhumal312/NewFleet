package org.fleetopgroup.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BankPaymentHistoryRepository extends JpaRepository<org.fleetopgroup.persistence.model.BankPaymentHistory, Long> {

}
