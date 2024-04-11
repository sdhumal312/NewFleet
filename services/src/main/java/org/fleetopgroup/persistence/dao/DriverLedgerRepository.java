package org.fleetopgroup.persistence.dao;

import java.util.Date;

import org.fleetopgroup.persistence.model.DriverLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DriverLedgerRepository extends JpaRepository<DriverLedger, Long> {
	
	@Query(nativeQuery = true, value = "Select * FROM DriverLedger where driverId = ?1 AND companyId = ?2 AND txnDateTime <= ?3"
			+ " AND markForDelete = 0 order by txnDateTime desc,driverLedgerId desc limit 1")
	public DriverLedger getDriveLedgerByDriverId(Long driverId, Integer companyId, Date	txnTime) throws Exception;
	

	@Query(nativeQuery = true, value = "Select * FROM DriverLedger where driverId = ?1 AND transactionId = ?2"
			+ " AND subTransactionId = ?3 AND txnTypeId = ?4"
			+ " AND markForDelete = 0 order by driverLedgerId desc limit 1")
	public DriverLedger getDriveLedgerByDriverId(Long driverId, Long transactionId, Long subTransactionId , short Type) throws Exception;

}
