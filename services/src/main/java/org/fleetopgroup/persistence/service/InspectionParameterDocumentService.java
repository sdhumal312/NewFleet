package org.fleetopgroup.persistence.service;

import java.util.List;

import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.document.InspectionParameterDocument;
import org.fleetopgroup.persistence.serviceImpl.IInspectionParameterDocumentService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InspectionParameterDocumentService implements IInspectionParameterDocumentService {
	
	@Autowired
	private ISequenceCounterService	sequenceCounterService;
	
	@Autowired
	private MongoTemplate	mongoTemplate;
	
	
	@Override
	public Integer saveInspectionParameterDocument(InspectionParameterDocument	inspectParamDoc) {
		
		inspectParamDoc.setId((int) sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_INSPECTION_PARAMETER));
		mongoTemplate.insert(inspectParamDoc);
		return inspectParamDoc.getId();
	}
	
	@Transactional
	public List<org.fleetopgroup.persistence.document.InspectionParameterDocument> listInspectionParameterPhoto(long parameterId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("id").is(parameterId).and("markForDelete").is(false));
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.InspectionParameterDocument.class);

	}
	
	@Transactional
	public org.fleetopgroup.persistence.document.InspectionParameterDocument getInspectionParameterPhoto(long parameterId, Integer companyId) throws Exception {
		org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
		query.addCriteria(Criteria.where("id").is(parameterId).and("markForDelete").is(false).and("companyId").is(companyId));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.InspectionParameterDocument.class);

	}
	
	@Override
	public void deleteParameterDocument(int documentid) throws Exception {
		
	       
	    org.springframework.data.mongodb.core.query.Query query = new org.springframework.data.mongodb.core.query.Query();
	    query.addCriteria(Criteria.where("id").is(documentid));
		mongoTemplate.remove(query, org.fleetopgroup.persistence.document.InspectionParameterDocument.class);
	       
	}
	
}