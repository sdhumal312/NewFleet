package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.MailboxDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MailboxDocumentRepository extends JpaRepository<MailboxDocument, Long> {

	@Query("FROM MailboxDocument WHERE MAILDOCID=?1")
	public MailboxDocument get_Mailbox_Document(Long MAILDOCID);
	
	@Query("SELECT MAX(MAILDOCID) FROM MailboxDocument")
	public long getMailBoxDocumentMaxId() throws Exception;
	
	@Query("FROM MailboxDocument where MAILDOCID > ?1 AND MAILDOCID <= ?2")
	public List<MailboxDocument> getMailBoxDocumentList(Long startLimit, Long endLimit) throws Exception;
}
