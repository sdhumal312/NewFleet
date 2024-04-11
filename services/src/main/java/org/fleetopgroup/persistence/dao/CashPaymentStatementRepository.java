package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.CashPaymentStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CashPaymentStatementRepository extends JpaRepository<CashPaymentStatement, Long> {
	
	@Query("FROM CashPaymentStatement WHERE cashPaymentStatementId IN (?1) AND markForDelete = 0")
	public List<CashPaymentStatement> getCashPaymentList(List<Long> IdLIst);
	

}
