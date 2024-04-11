package org.fleetopgroup.persistence.dao;

import java.util.Date;

import org.fleetopgroup.persistence.model.CashPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CashPaymentRepository extends JpaRepository<CashPayment, Long> {
	
	@Query("FROM CashPayment WHERE moduleId =?1 AND moduleIdentifier =?2 AND companyId =?3 AND markForDelete = 0 ")
	public CashPayment getCashPayment(long moduleId ,short moduleIdentifier,int companyId);
	
	@Modifying
	@Query("UPDATE CashPayment SET markForDelete = 1 WHERE  moduleId =?1 AND moduleIdentifier =?2 AND companyId =?3 ")
	public void deleteCashPayment(long moduleId ,short moduleIdentifier,int companyId);
	
	@Modifying
	@Query("UPDATE CashPayment SET amount = ?1,lastUpdatedOn = ?2,lastUpdatedBy = ?3  WHERE  cashPaymentId = ?4 AND companyId = ?5 ")
	public void updateCashPaymentAmount(double amount ,Date lastUpdatedOn,long lastUpdatedBy,long cashPaymentId,int companyId);
	
	@Modifying
	@Query("UPDATE CashPayment SET amount = ?1,transactionDate = ?6,lastUpdatedOn = ?2,lastUpdatedBy = ?3  WHERE  cashPaymentId = ?4 AND companyId = ?5 ")
	public void updateCashPaymentAmountAndTransactionDate(double amount ,Date lastUpdatedOn,long lastUpdatedBy,long cashPaymentId,int companyId,Date transactionDate);
	
	@Modifying
	@Query("UPDATE CashPayment SET markForDelete = 1 WHERE  cashPaymentId =?1 ")
	public void deleteCashPaymentByPaymentId(long cashPaymentId);

	//updateCashPaymentBranchId
	@Modifying
	@Query("UPDATE CashPayment SET branchId = ?1, lastUpdatedBy = ?2  WHERE  cashPaymentId = ?3 AND companyId = ?4 ")
	public void updateCashPaymentBranchId(int branchId, long lastUpdatedBy,long cashPaymentId,int companyId);
}
