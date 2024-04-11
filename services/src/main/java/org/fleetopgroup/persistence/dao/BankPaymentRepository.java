package org.fleetopgroup.persistence.dao;

import java.util.Date;

import org.fleetopgroup.persistence.model.BankPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BankPaymentRepository extends JpaRepository<BankPayment, Long> {
	
	@Modifying
	@Query("UPDATE BankPayment SET markForDelete =1 , lastUpdatedBy =?3 , lastUpdatedOn =?4 WHERE bankPaymentId =?1 AND companyId = ?2  ")
	public void deleteBankPayment(long bankPaymentId,int companyId,long updatedBy,Date lastUpdatedOn);

}
