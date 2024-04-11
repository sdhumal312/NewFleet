package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.dao.RenewalReminderAppDocumentRepository;
import org.fleetopgroup.persistence.dao.RenewalReminderDocumentHistoryRepository;
import org.fleetopgroup.persistence.model.RenewalReminderAppDocument;
import org.fleetopgroup.persistence.model.RenewalReminderDocument;
import org.fleetopgroup.persistence.model.RenewalReminderDocumentHistory;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderDocumentService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class RenewalReminderDocumentService implements IRenewalReminderDocumentService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired private MongoTemplate			mongoTemplate;
	@Autowired private ISequenceCounterService	sequenceCounterService;
	@PersistenceContext public EntityManager 	entityManager;
	
	@Autowired private RenewalReminderAppDocumentRepository		renewalReminderAppDocumentRepository;
	@Autowired private RenewalReminderDocumentHistoryRepository	renewalReminderDocumentHistoryRepository;

	
	@Override
	public void transferRenewalReminderDocument(List<RenewalReminderDocument> list) throws Exception {
		org.fleetopgroup.persistence.document.RenewalReminderDocument	     rewalDoc								= null;
		try {
			for(RenewalReminderDocument document : list) {
					rewalDoc = new org.fleetopgroup.persistence.document.RenewalReminderDocument();
					
					rewalDoc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_RENEWAL_DOCUMENT));
					rewalDoc.setRenewal_id(document.getRenewal_id());
					rewalDoc.setRenewal_R_Number(document.getRenewal_R_Number());
					rewalDoc.setRenewal_filename(document.getRenewal_filename());
					rewalDoc.setRenewal_content(document.getRenewal_content());
					rewalDoc.setRenewal_contentType(document.getRenewal_contentType());
					rewalDoc.setRenewalHis_id(document.getRenewalHis_id());
					rewalDoc.setMarkForDelete(false);
					rewalDoc.setCreatedById(document.getCreatedById());
					rewalDoc.setLastModifiedById(document.getLastModifiedById());
					rewalDoc.setCreated(document.getCreated());
					rewalDoc.setLastupdated(document.getLastupdated());
					rewalDoc.setCompanyId(document.getCompanyId());
					
					mongoTemplate.insert(rewalDoc);

			 }
			} catch (Exception e) {
				throw e;
			}
	}
	
	@Override
	public void save(org.fleetopgroup.persistence.document.RenewalReminderDocument document) throws Exception {
		document.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_RENEWAL_DOCUMENT));
		mongoTemplate.save(document);
	}
	
	@Override
	public org.fleetopgroup.persistence.document.RenewalReminderDocument get_RenewalReminder_Document(Long renewalId, Integer companyId) {
		 
		Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(renewalId).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.RenewalReminderDocument.class);
	}
	
	@Override
	public void delete_document(Long id, Integer companyId) {
		Query query = new Query(Criteria.where("renewalHis_id").is(id).and("companyId").is(companyId));
		Update update = new Update();
		update.set("markForDelete", true);
       mongoTemplate.updateFirst(query, update, org.fleetopgroup.persistence.document.RenewalReminderDocument.class);
	}
	
	@Override
	public List<org.fleetopgroup.persistence.document.RenewalReminderDocument> getBigDocList() throws Exception {
		TypedQuery<Object[]>	queryt = entityManager.createQuery(
				"SELECT R.rendoc_id, OCTET_LENGTH(R.renewal_content) from RenewalReminderDocument AS R where OCTET_LENGTH(R.renewal_content) > 16777216",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<org.fleetopgroup.persistence.document.RenewalReminderDocument> dtos = null;
		if (results != null && !results.isEmpty()) {
			dtos = new ArrayList<org.fleetopgroup.persistence.document.RenewalReminderDocument>();
			org.fleetopgroup.persistence.document.RenewalReminderDocument list = null;
			for (Object[] result : results) {
				list = new org.fleetopgroup.persistence.document.RenewalReminderDocument();

				list.set_id((Long) result[0]);
				
				dtos.add(list);
				
			}
		}
		return dtos;
	}
	
	@Override
	public void updateCompanyId() {
		Query query = new Query(Criteria.where("_id").gt(0));
		Update update = new Update();
		update.set("companyId", 6);
		mongoTemplate.updateMulti(query, update, org.fleetopgroup.persistence.document.ServiceEntriesDocument.class);
	}

	@Override
	public void transferRenewalReminderAppDocument(List<RenewalReminderAppDocument> list) throws Exception {
		org.fleetopgroup.persistence.document.RenewalReminderAppDocument			reminderAppDocument		= null;
		List<org.fleetopgroup.persistence.document.RenewalReminderAppDocument> 		reminderAppDocumentList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				reminderAppDocumentList	= new ArrayList<>();
				for(RenewalReminderAppDocument	document : list) {
					reminderAppDocument	= new org.fleetopgroup.persistence.document.RenewalReminderAppDocument();
					
					reminderAppDocument.set_id(document.getRendoc_id());
					reminderAppDocument.setRenewalApproval_id(document.getRenewalApproval_id());
					reminderAppDocument.setRenewal_filename(document.getRenewal_filename());
					reminderAppDocument.setRenewal_content(document.getRenewal_content());
					reminderAppDocument.setRenewal_contentType(document.getRenewal_contentType());
					reminderAppDocument.setCompanyId(document.getCompanyId());
					reminderAppDocument.setCreatedById(document.getCreatedById());
					reminderAppDocument.setLastModifiedById(document.getLastModifiedById());
					reminderAppDocument.setMarkForDelete(document.isMarkForDelete());
					reminderAppDocument.setCreated(document.getCreated());
					reminderAppDocument.setLastupdated(document.getLastupdated());
					
					reminderAppDocumentList.add(reminderAppDocument);
				}
				mongoTemplate.insert(reminderAppDocumentList, org.fleetopgroup.persistence.document.RenewalReminderAppDocument.class);
			}
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

	@Override
	public void transferRenewalReminderDocumentHistory(List<RenewalReminderDocumentHistory> list) throws Exception {
		org.fleetopgroup.persistence.document.RenewalReminderDocumentHistory			reminderDocumentHistory		= null;
		List<org.fleetopgroup.persistence.document.RenewalReminderDocumentHistory> 		reminderDocumentHistoryList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				reminderDocumentHistoryList	= new ArrayList<>();
				for(RenewalReminderDocumentHistory	document : list) {
					reminderDocumentHistory	= new org.fleetopgroup.persistence.document.RenewalReminderDocumentHistory();
					
					reminderDocumentHistory.set_id(document.getRendoc_id_history());
					reminderDocumentHistory.setRendoc_id(document.getRendoc_id());
					reminderDocumentHistory.setRenewal_id(document.getRenewal_id());
					reminderDocumentHistory.setRenewal_R_Number(document.getRenewal_R_Number());
					reminderDocumentHistory.setRenewalHis_id(document.getRenewalHis_id());
					reminderDocumentHistory.setRenewal_filename(document.getRenewal_filename());
					reminderDocumentHistory.setRenewal_content(document.getRenewal_content());
					reminderDocumentHistory.setRenewal_contentType(document.getRenewal_contentType());
					reminderDocumentHistory.setCompanyId(document.getCompanyId());
					reminderDocumentHistory.setCreatedById(document.getCreatedById());
					reminderDocumentHistory.setLastModifiedById(document.getLastModifiedById());
					reminderDocumentHistory.setMarkForDelete(document.isMarkForDelete());
					reminderDocumentHistory.setCreated(document.getCreated());
					reminderDocumentHistory.setLastupdated(document.getLastupdated());
					
					reminderDocumentHistoryList.add(reminderDocumentHistory);
				}
				logger.debug("Saving reminderDocumentHistory ....");
				mongoTemplate.insert(reminderDocumentHistoryList, org.fleetopgroup.persistence.document.RenewalReminderDocumentHistory.class);
				logger.debug("Saved Successfully....");
			}
		} catch (Exception e) {
			logger.debug("Saved Successfully....");
		}
	}
	
	@Override
	public long getRenewalReminderAppDocumentMaxId() throws Exception {
		try {
			return renewalReminderAppDocumentRepository.getRenewalReminderAppDocumentMaxId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public long getRenewalReminderDocumentHistoryMaxId() throws Exception {
		try {
			return renewalReminderDocumentHistoryRepository.getRenewalReminderDocumentHistoryMaxId();
		} catch (Exception e) {
			return 0;
		}
	}
	
	@Override
	public List<org.fleetopgroup.persistence.document.RenewalReminderDocument> getRenewalDocumentsByRenewalId(long renewalId, int companyId) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("renewal_id").is(renewalId).and("companyId").is(companyId).and("markForDelete").is(false));
		
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.RenewalReminderDocument.class);
	}
}
