package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.PendingVendorPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PendingVendorPaymentRepository  extends JpaRepository<PendingVendorPayment, Long> {

	@Modifying
	@Query("UPDATE PendingVendorPayment set markForDelete = 1 where transactionId = ?1 AND txnTypeId = ?2")
	public void deletePendingVendorPayment(Long transactionId, short type) throws Exception;
	
	@Query("FROM PendingVendorPayment where transactionId = ?1 AND txnTypeId = ?2 AND markForDelete = 0")
	public PendingVendorPayment getPendingVendorPaymentById(Long transactionId, short type) throws Exception;
	
	@Query("FROM PendingVendorPayment where transactionId = ?2 AND vid = ?1 AND companyId= ?3 AND markForDelete = 0")
	public PendingVendorPayment getPendingVendorPaymentByTransactionId(Integer vid, Long invoiceId, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE PendingVendorPayment set  balanceAmount= ?2 where pendingPaymentId=?1")
	public void updatependingVendorBalanceAmunt(Long pendingPaymentId,  Double transactionAmount);
	
}
