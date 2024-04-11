package org.fleetopgroup.persistence.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.model.CashPaymentTxnChecker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CashPaymentTxnCheckerRepository extends JpaRepository<CashPaymentTxnChecker, Long> {
	
	@Transactional
	@Modifying
	@Query("UPDATE CashPaymentTxnChecker SET dataSyncWithIvCargo = 1 WHERE cashPaymentStatementId IN (?1) ")
	public void updateCashPaymentCheckerList(List<Long> list);
	
	
	@Query("SELECT cashPaymentStatementId FROM CashPaymentTxnChecker WHERE dataSyncWithIvCargo=0 AND  markForDelete=0  AND createdOn < ?1 order by cashPaymentStatementId ASC ")
	public List<Long> getNonSyncCashPaymentList(LocalDateTime time);

}
