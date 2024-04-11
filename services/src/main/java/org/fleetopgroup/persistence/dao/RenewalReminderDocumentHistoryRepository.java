package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.RenewalReminderDocumentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RenewalReminderDocumentHistoryRepository extends JpaRepository<RenewalReminderDocumentHistory, Long>  {

	@Query("SELECT MAX(rendoc_id_history) FROM RenewalReminderDocumentHistory")
	public long getRenewalReminderDocumentHistoryMaxId() throws Exception;
	
	@Query("FROM RenewalReminderDocumentHistory where rendoc_id_history > ?1 AND rendoc_id_history <= ?2")
	public List<RenewalReminderDocumentHistory> getRenewalReminderDocumentHistoryList(Long startLimit, Long endLimit) throws Exception;
}
