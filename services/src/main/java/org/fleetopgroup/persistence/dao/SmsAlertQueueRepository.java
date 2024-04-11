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
import org.fleetopgroup.persistence.model.SmsAlertQueue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SmsAlertQueueRepository extends JpaRepository<SmsAlertQueue, Long> {
	
	@Query("FROM SmsAlertQueue E where E.alertScheduleDate= ?1  AND  E.markForDelete = 0")
	public List<SmsAlertQueue> getAllSmsAlertQueueService(Date currentDate) throws Exception;
	
	
	@Query("FROM SmsAlertQueue E where E.transactionId= ?1  AND  E.markForDelete = 0")
	public List<SmsAlertQueue> getAllSmsAlertQueueDetailsById(long serviceReminderId) throws Exception;
	
	@Modifying
	@Query("DELETE SmsAlertQueue where transactionId = ?1")
	public void deletesmsAlertQueueById(long serviceReminderId) throws Exception;
}