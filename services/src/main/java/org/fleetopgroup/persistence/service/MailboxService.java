package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.dao.MailboxRepository;
import org.fleetopgroup.persistence.dao.MessagePropertyRepository;
import org.fleetopgroup.persistence.model.Mailbox;
import org.fleetopgroup.persistence.model.MailboxDocument;
import org.fleetopgroup.persistence.model.MessageProperty;
import org.fleetopgroup.persistence.serviceImpl.IMailBoxService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class MailboxService implements IMailBoxService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private MailboxRepository mailboxRepository;

	@Autowired private MongoTemplate	mongoTemplate;
	@Autowired private ISequenceCounterService	sequenceCounterService;

	private static final int PAGE_SIZE = 25;

	@Autowired
	private MessagePropertyRepository messagePropertyRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IMailBoxService#insert_SENT_MailBox(
	 * org.fleetop.persistence.model.mail.Mailbox)
	 */
	@Transactional
	public Mailbox insert_SENT_MailBox(Mailbox mailboc) throws Exception {

		return mailboxRepository.save(mailboc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * insert_SENT_MeassageProperty(org.fleetop.persistence.model.mail.
	 * MeassageProperty)
	 */
	@Transactional
	public MessageProperty insert_SENT_MeassageProperty(MessageProperty message) throws Exception {

		return messagePropertyRepository.save(message);
	}

	/**
	 * This Page get get_Inbox_Mailbox_Page_Mail table to get pagination values
	 */
	@Transactional
	public Page<Mailbox> get_Inbox_Mailbox_Page_Mail(Integer pageNumber, String mailID) {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return mailboxRepository.get_Inbox_Mailbox_Page_Mail(mailID, pageable);
	}

	/** This Page get get_Mailbox_Page_Mail table to get pagination values */
	@Transactional
	public Page<Mailbox> get_Mailbox_Page_Mail(Integer pageNumber, String mailID, String Status) {
		@SuppressWarnings("deprecation")
		Pageable pageable = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return mailboxRepository.get_Mailbox_Page_Mail(mailID, Status, pageable);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IMailBoxService#List_Mailbox(java.
	 * lang.String)
	 */
	@Transactional
	public List<Mailbox> List_Inbox_Mailbox(Integer pageNumber, String mail_id) throws Exception {

		TypedQuery<Mailbox> query = entityManager.createQuery(
				"from Mailbox where MAILBOX_TO_EMAIL ='" + mail_id
						+ "' AND MAIL_MIME_TYPE ='INBOX' OR MAILBOX_TO_EMAIL ='" + mail_id
						+ "' AND MAIL_MIME_TYPE ='SENT' AND MAIL_IS_SEEN=false ORDER BY MAILBOX_ID desc",
				Mailbox.class);
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IMailBoxService#List_Sent_Mailbox(
	 * java.lang.String)
	 */
	@Transactional
	public List<Mailbox> List_Sent_Mailbox(Integer pageNumber, String mail_id) throws Exception {

		TypedQuery<Mailbox> query = entityManager.createQuery("from Mailbox where MAILBOX_TO_EMAIL ='" + mail_id
				+ "' AND MAIL_MIME_TYPE ='SENT' ORDER BY MAILBOX_ID desc", Mailbox.class);
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IMailBoxService#List_Trash_Mailbox(
	 * java.lang.String)
	 */
	@Transactional
	public List<Mailbox> List_Trash_Mailbox(Integer pageNumber, String mail_id) throws Exception {

		TypedQuery<Mailbox> query = entityManager.createQuery("from Mailbox where MAILBOX_TO_EMAIL ='" + mail_id
				+ "' AND MAIL_MIME_TYPE ='TRASH' ORDER BY MAILBOX_ID desc", Mailbox.class);
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * List_Important_Mailbox(java.lang.String)
	 */
	@Transactional
	public List<Mailbox> List_Important_Mailbox(String mail_id) throws Exception {

		TypedQuery<Mailbox> query = entityManager.createQuery(
				"from Mailbox as m where m.MAILBOX_TO_EMAIL ='" + mail_id
						+ "' AND m.MAIL_IS_FLAGGED =true AND m.MAIL_IS_DELETED =false  ORDER BY MAILBOX_ID desc",
				Mailbox.class);
		query.setFirstResult(0);
		query.setMaxResults(50);
		return query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * List_Important_Mailbox(java.lang.String)
	 */
	@Transactional
	public List<Mailbox> List_Search_Mailbox(String mail_id, String SearchMail) throws Exception {
		if(mail_id != null && !mail_id.trim().equalsIgnoreCase("") && mail_id.indexOf('\'') != 0 ) {
		TypedQuery<Mailbox> query = entityManager.createQuery("from Mailbox as m where m.MAILBOX_TO_EMAIL ='" + mail_id
				+ "' AND m.MAIL_IS_DELETED =false AND m.MAILBOX_FROM_EMAIL Like ('" + SearchMail
				+ "%')   ORDER BY MAILBOX_ID desc", Mailbox.class);
		query.setFirstResult(0);
		query.setMaxResults(50);
		return query.getResultList();
		}else {
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * countTotal_Unread_MailBox(java.lang.String)
	 */
	@Transactional
	public Long countTotal_Unread_MailBox(String mail_id) throws Exception {

		return mailboxRepository.countTotal_Unread_MailBox(mail_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * countTotal_Inbox_MailBox(java.lang.String)
	 */
	@Transactional
	public Long countTotal_Inbox_MailBox(String mail_id) throws Exception {

		return mailboxRepository.countTotal_Inbox_MailBox(mail_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * countTotal_Sent_MailBox(java.lang.String)
	 */
	@Transactional
	public Long countTotal_Sent_MailBox(String mail_id) throws Exception {

		return mailboxRepository.countTotal_Sent_MailBox(mail_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * countTotal_Sent_MailBox(java.lang.String)
	 */
	@Transactional
	public Long countTotal_Trash_MailBox(String mail_id) throws Exception {

		return mailboxRepository.countTotal_Trash_MailBox(mail_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * countTotal_Sent_MailBox(java.lang.String)
	 */
	@Transactional
	public Long countTotal_Important_MailBox(String mail_id) throws Exception {

		return mailboxRepository.countTotal_Important_MailBox(mail_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IMailBoxService#find_Read_Mailbox(
	 * java.lang.Long)
	 */
	@Transactional
	public Mailbox find_Read_Mailbox(Long mail_id) throws Exception {

		Mailbox mail = this.entityManager.find(Mailbox.class, mail_id);
		mail.getProperties().size();
		return mail;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * update_replay_mailID_seen_false(java.lang.Long, java.lang.Long)
	 */
	@Transactional
	public void update_replay_mailID_seen_false(Long senter_mail_id, Long mail_id) throws Exception {

		mailboxRepository.update_replay_mailID_seen_false(senter_mail_id, mail_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IMailBoxService#update_seen_True(java
	 * .lang.Long)
	 */
	@Transactional
	public void update_seen_True(Long mail_id) throws Exception {

		mailboxRepository.update_seen_True(mail_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * Delete_update_MailBox_TrashStatus(java.lang.String, java.lang.String)
	 */
	@Transactional
	public void Trash_update_MailBox_TrashStatus(String Multiplemail_id, String username) throws Exception {

		entityManager.createQuery("UPDATE Mailbox as m SET m.MAIL_MIME_TYPE ='TRASH' where m.MAILBOX_ID IN ("
				+ Multiplemail_id + ") AND m.MAILBOX_TO_EMAIL ='" + username + "'").executeUpdate();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * Delete_update_MailBox_TrashStatus(java.lang.String, java.lang.String)
	 */
	@Transactional
	public void Delete_update_MailBox_DeleteStatus(String Multiplemail_id, String username) throws Exception {

		entityManager.createQuery(
				"UPDATE Mailbox as m SET m.MAIL_MIME_TYPE ='DELETE', m.MAIL_IS_DELETED =true where m.MAILBOX_ID IN ("
						+ Multiplemail_id + ") AND m.MAILBOX_TO_EMAIL ='" + username + "'")
				.executeUpdate();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * update_flagged_Importent_True(java.lang.Long)
	 */
	@Transactional
	public void update_flagged_Importent_True(Long mail_id) throws Exception {

		mailboxRepository.update_flagged_Importent_True(mail_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * update_flagged_Importent_False(java.lang.Long)
	 */
	@Transactional
	public void update_flagged_Importent_False(Long mail_id) throws Exception {

		mailboxRepository.update_flagged_Importent_False(mail_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IMailBoxService#Only_Unread_Mailbox(
	 * java.lang.String)
	 */
	@Transactional
	public List<Mailbox> Only_Unread_Mailbox(String mail_id) throws Exception {

		return mailboxRepository.Only_Unread_Mailbox(mail_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * add_insert_SENT_MailBoxDocument(org.fleetop.persistence.model.
	 * MailboxDocument)
	 */
	@Transactional
	public void save(org.fleetopgroup.persistence.document.MailboxDocument mailDoc) {
		// Note: This Save MailBox Document Values
		mailDoc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_MAIL_BOX_DOCUMENT));
		mongoTemplate.save(mailDoc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.lang.Long)
	 */
	@Transactional
	public org.fleetopgroup.persistence.document.MailboxDocument get_Mailbox_Document(Long mAILBOX_ID) {
		// Note: This Get MailId to Document Details
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("MAILBOX_ID").is(mAILBOX_ID).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.MailboxDocument.class);
	}
	
	@Override
	public org.fleetopgroup.persistence.document.MailboxDocument download_Mailbox_Document(Long mAILBOX_ID) {
		// Note: This Get MailId to Document Details
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("_id").is(mAILBOX_ID).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.MailboxDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IMailBoxService#
	 * Update_MailBoxDocuemnt_ID_To_MailBox(java.lang.Long, boolean, long)
	 */
	@Transactional
	public void Update_MailBoxDocuemnt_ID_To_MailBox(Long maildocid, boolean b, long mailbox_ID) {
		// Note: Update Document Id To MailBox Detaisl

		mailboxRepository.Update_MailBoxDocuemnt_ID_To_MailBox(maildocid, b, mailbox_ID);

	}

	@Override
	public void transferMailBoxDocument(List<MailboxDocument> list) throws Exception {
		org.fleetopgroup.persistence.document.MailboxDocument			mailboxDocument		= null;
		List<org.fleetopgroup.persistence.document.MailboxDocument> 		mailboxDocumentList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				mailboxDocumentList	= new ArrayList<>();
				for(MailboxDocument	document : list) {
					mailboxDocument	= new org.fleetopgroup.persistence.document.MailboxDocument();
					
					mailboxDocument.set_id(document.getMAILDOCID());
					mailboxDocument.setMAILBOX_ID(document.getMAILBOX_ID());
					mailboxDocument.setMAIL_CONTENT(document.getMAIL_CONTENT());
					mailboxDocument.setMAIL_CONTENT_TYPE(document.getMAIL_CONTENT_TYPE());
					mailboxDocument.setMAIL_FILENAME(document.getMAIL_FILENAME());
					mailboxDocument.setCREATEDBY(document.getCREATEDBY());
					mailboxDocument.setLASTMODIFIEDBY(document.getLASTMODIFIEDBY());
					mailboxDocument.setMarkForDelete(false);
					mailboxDocument.setCREATED_DATE(document.getCREATED_DATE());
					mailboxDocument.setLASTUPDATED_DATE(document.getLASTUPDATED_DATE());
					
					mailboxDocumentList.add(mailboxDocument);
				}
				System.err.println("Saving MailboxDocument ....");
				mongoTemplate.insert(mailboxDocumentList, org.fleetopgroup.persistence.document.MailboxDocument.class);
				System.err.println("Saved Successfully....");
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}
}