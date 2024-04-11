package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.ServiceReminder;
import org.fleetopgroup.persistence.model.ServiceReminderHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ServiceReminderHistoryRepository extends JpaRepository<ServiceReminderHistory, Long> {
	
	@Query("From ServiceReminderHistory SRH where SRH.service_reminderId= ?1 AND SRH.companyId = ?2 AND SRH.markForDelete = 0")
	public List<ServiceReminderHistory> getlistServiceReminderHistory(Long service_reminderId, Integer companyId);

	
}
