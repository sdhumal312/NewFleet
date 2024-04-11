package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.ExecutiveMapperTxnChecker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ExecutiveMapperTxnCheckerRepository extends JpaRepository<ExecutiveMapperTxnChecker, Long> {
	
	@Modifying
	@Query("update ExecutiveMapperTxnChecker set isIVCargoStatusUpdated = ?2 where executiveMapperTxnCheckerId = ?1  ")
	public void updateIVCargoTxnChecherStatus(Long txnCheckerId, boolean status) throws Exception;

}
