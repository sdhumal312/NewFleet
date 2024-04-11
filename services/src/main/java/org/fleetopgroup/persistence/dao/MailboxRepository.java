package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.Mailbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MailboxRepository extends JpaRepository<Mailbox, Long> {

	@Query("FROM Mailbox DDT where DDT.MAILBOX_ID = ?1")
	public Mailbox getMilBoxByID(Long MAILBOX_ID);

	/** The Value Of count Unread mail in user */
	@Query("Select count(m.MAILBOX_ID)  From Mailbox as m where m.MAILBOX_TO_EMAIL = ?1 AND m.MAIL_MIME_TYPE ='INBOX' AND m.MAIL_IS_SEEN=False OR m.MAILBOX_TO_EMAIL = ?1 AND m.MAIL_MIME_TYPE ='SENT' AND m.MAIL_IS_SEEN=False")
	public Long countTotal_Unread_MailBox(String mail_id) throws Exception;

	@Query("From Mailbox as m where m.MAILBOX_TO_EMAIL = ?1 AND m.MAIL_MIME_TYPE ='INBOX' AND m.MAIL_IS_SEEN=False OR m.MAILBOX_TO_EMAIL = ?1 AND m.MAIL_MIME_TYPE ='SENT' AND m.MAIL_IS_SEEN=False")
	public List<Mailbox> Only_Unread_Mailbox(String mail_id) throws Exception;

	/** The Value Of count Inbox mail in user */
	@Query("Select count(m.MAILBOX_ID)  From Mailbox as m where m.MAILBOX_TO_EMAIL = ?1 AND m.MAIL_MIME_TYPE ='INBOX'")
	public Long countTotal_Inbox_MailBox(String mail_id) throws Exception;

	/** The Value Of count Sent mail in user */
	@Query("Select count(m.MAILBOX_ID)  From Mailbox as m where m.MAILBOX_TO_EMAIL = ?1 AND m.MAIL_MIME_TYPE ='SENT'")
	public Long countTotal_Sent_MailBox(String mail_id) throws Exception;

	/** The Value Of count Trash mail in user */
	@Query("Select count(m.MAILBOX_ID)  From Mailbox as m where m.MAILBOX_TO_EMAIL = ?1 AND m.MAIL_MIME_TYPE ='TRASH'")
	public Long countTotal_Trash_MailBox(String mail_id) throws Exception;

	/** The Value Of count Important mail in user */
	@Query("Select count(m.MAILBOX_ID) From Mailbox as m where m.MAILBOX_TO_EMAIL = ?1 AND m.MAIL_IS_FLAGGED =true AND m.MAIL_IS_DELETED =false ")
	public Long countTotal_Important_MailBox(String mail_id) throws Exception;

	/** The Value Of find read mail box */
	@Query("From Mailbox as m where m.MAILBOX_ID = ?1 ")
	public Mailbox find_Read_Mailbox(Long mail_id) throws Exception;

	/**
	 * The Value Of update mail replay mail id user mail_id to again reply
	 * purpass
	 */
	@Modifying
	@Query("UPDATE Mailbox as m SET m.MAIL_IS_SEEN=false, m.MAIL_TEXTUAL_COUNT=m.MAIL_TEXTUAL_COUNT+1, m.MAILBOX_SENTER_MAILBOX_ID= ?1 where m.MAILBOX_ID = ?2 ")
	public void update_replay_mailID_seen_false(Long senter_mail_id, Long mail_id) throws Exception;

	/** The Value Of update mail seen true */
	@Modifying
	@Query("UPDATE Mailbox as m SET m.MAIL_IS_SEEN=true where m.MAILBOX_ID = ?1 ")
	public void update_seen_True(Long mail_id) throws Exception;

	/** The Value Of update mail flagged important true */
	@Modifying
	@Query("UPDATE Mailbox as m SET m.MAIL_IS_FLAGGED=true where m.MAILBOX_ID = ?1 ")
	public void update_flagged_Importent_True(Long mail_id) throws Exception;

	/** The Value Of update mail flagged important true */
	@Modifying
	@Query("UPDATE Mailbox as m SET m.MAIL_IS_FLAGGED=false where m.MAILBOX_ID = ?1 ")
	public void update_flagged_Importent_False(Long mail_id) throws Exception;

	@Query("from Mailbox as m where m.MAILBOX_TO_EMAIL =?1 AND m.MAIL_MIME_TYPE ='INBOX' OR m.MAILBOX_TO_EMAIL =?1 AND m.MAIL_MIME_TYPE ='SENT' AND m.MAIL_IS_SEEN=false")
	public Page<Mailbox> get_Inbox_Mailbox_Page_Mail(String mail_id, Pageable pageable);

	@Query("from Mailbox as m where m.MAILBOX_TO_EMAIL =?1 AND m.MAIL_MIME_TYPE =?2")
	public Page<Mailbox> get_Mailbox_Page_Mail(String mail_id, String status, Pageable pageable);

	@Modifying
	@Query("UPDATE Mailbox as m SET m.MAIL_DOCUMENT_ID=?1, m.MAIL_DOCUMENT =?2 where m.MAILBOX_ID = ?3 ")
	public void Update_MailBoxDocuemnt_ID_To_MailBox(Long maildocid, boolean b, long mailbox_ID);

}
