package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.RenewalReminderAppDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RenewalReminderAppDocumentRepository extends JpaRepository<RenewalReminderAppDocument, Long> {

	@Query("FROM RenewalReminderAppDocument AS R WHERE R.rendoc_id=?1 AND R.companyId = ?2")
	public RenewalReminderAppDocument get_RenewalReminderAppDocument_Document(Long renewal_id, Integer companyId);
	
	@Modifying
	@Query("DELETE FROM RenewalReminderAppDocument AS R WHERE R.rendoc_id=?1 ")
	public void delete_RenewalReminderAppDocument_ID_History(Long renewalhis_id);

	@Query("SELECT MAX(rendoc_id) FROM RenewalReminderAppDocument")
	public long getRenewalReminderAppDocumentMaxId() throws Exception;
	
	@Query("FROM RenewalReminderAppDocument where rendoc_id > ?1 AND rendoc_id <= ?2")
	public List<RenewalReminderAppDocument> getRenewalReminderAppDocumentList(Long startLimit, Long endLimit) throws Exception;
}
