package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.RenewalReminderDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RenewalReminderDocumentRepository extends JpaRepository<RenewalReminderDocument, Long> {

	@Query("FROM RenewalReminderDocument AS R WHERE R.rendoc_id=?1 AND R.companyId = ?2 AND R.markForDelete = 0")
	public RenewalReminderDocument get_RenewalReminder_Document(Long renewal_id, Integer companyId);
	
	@Query("FROM RenewalReminderDocument AS R WHERE R.rendoc_id=?1 AND R.companyId = ?2 AND R.markForDelete = 0")
	public RenewalReminderDocument get_RenewalReminderHistory_Document(Long renewalhis_id, Integer companyId);

	@Modifying
	@Query("UPDATE RenewalReminderDocument AS R SET R.markForDelete = 1 WHERE R.renewalHis_id=?1 AND R.companyId = ?2")
	public void delete_RenewalReminderDocument_ID_History(Long renewalhis_id, Integer companyId);
	
	@Query("FROM RenewalReminderDocument where rendoc_id > ?1 AND rendoc_id <= ?2")
	public List<RenewalReminderDocument> getRenewalDocumentList(long startLimit, long endLimit) throws Exception;
	
	@Query("SELECT MAX(rendoc_id) FROM RenewalReminderDocument")
	public long getBigRenewalDocumentList() throws Exception;
	

}
