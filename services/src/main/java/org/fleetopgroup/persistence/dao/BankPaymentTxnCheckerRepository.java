package org.fleetopgroup.persistence.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.model.BankPaymentTxnChecker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BankPaymentTxnCheckerRepository extends JpaRepository<BankPaymentTxnChecker, Long> {
	
	@Transactional
	@Modifying
	@Query("UPDATE BankPaymentTxnChecker SET dataSyncWithIvCargo = 1 WHERE bankPaymetDetailsToIvId IN (?1) ")
	public void updateBankPaymentTxnCheckerList(List<Long> list);
	
	
	@Query("SELECT bankPaymetDetailsToIvId From BankPaymentTxnChecker where dataSyncWithIvCargo = 0 AND markForDelete = 0 AND createdOn < ?1 ORDER BY bankPaymentTxnCheckerId ASC")
	public List<Long> getNonSyncTxnList(LocalDateTime time);

}
