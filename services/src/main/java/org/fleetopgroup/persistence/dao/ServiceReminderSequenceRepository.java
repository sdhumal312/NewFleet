package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.ServiceReminderSequenceCounter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ServiceReminderSequenceRepository extends JpaRepository<ServiceReminderSequenceCounter, Long>{

	
	@Query("From ServiceReminderSequenceCounter SRSC where SRSC.companyId = ?1 ")
	public ServiceReminderSequenceCounter findNextServiceReminderNumber(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE ServiceReminderSequenceCounter SRSC SET SRSC.nextVal = ?1  where SRSC.companyId = ?2")
	public void updateNextServiceReminderNumber(long nextVal, Integer companyId) throws Exception;
	

}
