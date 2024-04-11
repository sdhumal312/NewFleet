package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.RenewalReminderSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RenewalReminderSequenceRepository extends JpaRepository<RenewalReminderSequenceCounter, Long>{
	
	@Query("From RenewalReminderSequenceCounter RRSC where RRSC.companyId = ?1 AND RRSC.markForDelete = 0")
	public RenewalReminderSequenceCounter findNextRenewal_R_Number(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE RenewalReminderSequenceCounter RRSC SET RRSC.nextVal = ?1  where RRSC.companyId = ?2")
	public void updateNextRenewal_R_Number(long nextVal, Integer companyId) throws Exception;
	
}
