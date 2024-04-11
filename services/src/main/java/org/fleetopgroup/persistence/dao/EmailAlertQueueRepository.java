package org.fleetopgroup.persistence.dao;

import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.EmailAlertQueue;
import org.fleetopgroup.persistence.model.RenewalMailConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EmailAlertQueueRepository extends JpaRepository<EmailAlertQueue, Long> {
	
	
	@Query("FROM EmailAlertQueue E where E.alertScheduleDate= ?1  AND  E.markForDelete = 0")
	public List<EmailAlertQueue> getAllEmailAlertQueueDetails(Date currentDate) throws Exception;

	
	@Modifying
	@Query("UPDATE EmailAlertQueue SET isEmailSent = 1 where alertScheduleDate= ?1")
	public void updateEmailSent(Date currentDate) throws Exception;
	
	
	@Query("FROM EmailAlertQueue E where E.transactionId= ?1  AND  E.markForDelete = 0")
	public List<EmailAlertQueue> getAllEmailAlertQueueDetailsById(long serviceReminderId) throws Exception;
	
	@Modifying
	@Query("DELETE EmailAlertQueue where transactionId = ?1")
	public void deleteEmailAlertQueueById(long serviceReminderId) throws Exception;

	
}