package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.Mailbox;
import org.fleetopgroup.persistence.model.MessageProperty;
import org.springframework.data.domain.Page;

public interface IMailBoxService {

	/** The value is create sent mail own user id insert */
	public Mailbox insert_SENT_MailBox(Mailbox mailboc) throws Exception;

	/**
	 * The value is create sent mail MeassageProperty own user id insert
	 * MeassageProperty
	 */
	public MessageProperty insert_SENT_MeassageProperty(MessageProperty message) throws Exception;

	/** This Page get _Inbox_Mailbox table to get pagination values */
	public Page<Mailbox> get_Inbox_Mailbox_Page_Mail(Integer pageNumber, String mail_id);

	/** This Page get _Inbox_Mailbox table to get pagination values */
	public Page<Mailbox> get_Mailbox_Page_Mail(Integer pageNumber, String mail_id, String status);

	/** The Value Of Searching current 50 mail descending Order */
	public List<Mailbox> List_Inbox_Mailbox(Integer pageNumber, String mail_id) throws Exception;

	/** The Value Of Searching current 50 mail descending Order */
	public List<Mailbox> List_Sent_Mailbox(Integer pageNumber, String mail_id) throws Exception;

	/** The Value Of Searching current 50 mail descending Order */
	public List<Mailbox> List_Important_Mailbox(String mail_id) throws Exception;

	/** The Value Of Searching current 50 mail descending Order */
	public List<Mailbox> List_Search_Mailbox(String mail_id, String SearchMail) throws Exception;

	/** The Value Of Searching current 50 mail descending Order */
	public List<Mailbox> List_Trash_Mailbox(Integer pageNumber, String mail_id) throws Exception;

	/** The Value Of count Unread mail in user */
	public Long countTotal_Unread_MailBox(String mail_id) throws Exception;

	/** The Value Of Searching 4 descending Order */
	public List<Mailbox> Only_Unread_Mailbox(String mail_id) throws Exception;

	/** The Value Of count All inbox mail in user */
	public Long countTotal_Inbox_MailBox(String mail_id) throws Exception;

	/** The Value Of count All Sent mail in user */
	public Long countTotal_Sent_MailBox(String mail_id) throws Exception;

	/** The Value Of count All Trash mail in user */
	public Long countTotal_Trash_MailBox(String mail_id) throws Exception;

	/** The Value Of count All Important mail in user */
	public Long countTotal_Important_MailBox(String mail_id) throws Exception;

	/** The Value Of find read mail box */
	public Mailbox find_Read_Mailbox(Long mail_id) throws Exception;

	/**
	 * The Value Of update mail replay mail id user mail_id to again reply
	 * purpass
	 */
	public void update_replay_mailID_seen_false(Long senter_mail_id, Long mail_id) throws Exception;

	/** The Value Of update mail seen true */
	public void update_seen_True(Long mail_id) throws Exception;

	/** The Value Of update mail Trash value Multiple Mails true */
	public void Trash_update_MailBox_TrashStatus(String Multiplemail_id, String username) throws Exception;

	/** The Value Of update mail Delete value Multiple Mails true */
	public void Delete_update_MailBox_DeleteStatus(String Multiplemail_id, String username) throws Exception;

	/** The Value Of update mail flagged important true */
	public void update_flagged_Importent_True(Long mail_id) throws Exception;

	/** The Value Of update mail flagged important False */
	public void update_flagged_Importent_False(Long mail_id) throws Exception;

	/**
	 * @param mailDoc
	 */
	public void save(org.fleetopgroup.persistence.document.MailboxDocument mailboxDocument) throws Exception;

	/**
	 * @param mAILBOX_ID
	 * @return
	 */
	public org.fleetopgroup.persistence.document.MailboxDocument get_Mailbox_Document(Long mAILBOX_ID);
	
	public org.fleetopgroup.persistence.document.MailboxDocument download_Mailbox_Document(Long mAILBOX_ID);

	/**
	 * @param maildocid
	 * @param b
	 * @param mailbox_ID
	 */
	public void Update_MailBoxDocuemnt_ID_To_MailBox(Long maildocid, boolean b, long mailbox_ID);
	
	/**
	 * Transfer all the document details from mysql to mongodb 
	 * @param list
	 * @throws Exception
	 */
	public void transferMailBoxDocument(List<org.fleetopgroup.persistence.model.MailboxDocument> list) throws Exception;

}
