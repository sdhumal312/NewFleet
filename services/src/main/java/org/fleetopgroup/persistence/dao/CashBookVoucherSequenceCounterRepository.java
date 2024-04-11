package org.fleetopgroup.persistence.dao;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.model.CashBookVoucherSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CashBookVoucherSequenceCounterRepository extends JpaRepository<CashBookVoucherSequenceCounter, Long> {

	@Query(nativeQuery = true, value = "SELECT * FROM CashBookVoucherSequenceCounter where cashBookId = ?1 AND companyId = ?2 AND markForDelete = 0 ORDER BY voucherSequenceCounterId ASC LIMIT 1")
	public CashBookVoucherSequenceCounter getNextVoucherNumber(Integer cashBookId, Integer companyId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE CashBookVoucherSequenceCounter SET nextVal = nextVal + 1 where voucherSequenceCounterId = ?1")
	public void updateNextVal(Long sequenceCounterId) throws Exception;
	
	@Modifying
	@Query("UPDATE CashBookVoucherSequenceCounter SET nextVal = 1 ")
	public void resetAllCashVoucherSequnces() throws Exception;
}
