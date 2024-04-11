package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.DriverRenewalReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DriverRenewalReceiptRepository extends JpaRepository<DriverRenewalReceipt, Long> {
	
	
	@Modifying
	@Query("UPDATE  DriverRenewalReceipt T SET T.documentId= ?1  where T.driverRenewalReceiptId = ?2 and T.companyId = ?3")
	public void updateDriverRenewalReceipt(long documentId,long driverRenewalReceiptId, Integer companyId);
	
	
	@Modifying
	@Query("UPDATE  DriverRenewalReceipt T SET T.markForDelete= 1  where T.driverRenewalReceiptId = ?1 and T.companyId = ?2")
	public void deleteDriverRenewalReceipt(long driverRenewalReceiptId, Integer companyId);
	
	
	@Modifying
	@Query("UPDATE  DriverRenewalReceipt T SET T.newReceiptStatus= 1  where T.driverRenewalReceiptId = ?1 and T.companyId = ?2")
	public void updateDriverRenewalReceiptStatus(long driverRenewalReceiptId, Integer companyId);


}
