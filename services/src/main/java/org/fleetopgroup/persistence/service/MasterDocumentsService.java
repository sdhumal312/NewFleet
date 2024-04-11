package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.document.MasterDocuments;
import org.fleetopgroup.persistence.serviceImpl.IMasterDocumentService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MasterDocumentsService implements IMasterDocumentService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired MongoTemplate			mongoTemplate;
	@Autowired ISequenceCounterService	sequenceCounterService;

	@Transactional
	public void saveMasterDocuments(MasterDocuments masterDocuments) {
		masterDocuments.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_MASTER_DOCUMENTS));
		mongoTemplate.save(masterDocuments);
	}

	@Transactional
	public void saveOrUpdateMasterDocuments(MasterDocuments masterDocuments) {
		mongoTemplate.save(masterDocuments);
	}
	
	@Transactional
	public MasterDocuments getMasterDocuemntByDocumentTypeId(Short documentTypeId) {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("documentTypeId").is(documentTypeId));
		return mongoTemplate.findOne(query, MasterDocuments.class);
	
	}
}