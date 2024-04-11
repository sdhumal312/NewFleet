package org.fleetopgroup.persistence.service;

import java.util.List;

import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.document.InspectionToParameterDocument;
import org.fleetopgroup.persistence.serviceImpl.IInspectionToParameterDocumentService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class InspectionToParameterDocumentService implements IInspectionToParameterDocumentService {
	
	@Autowired private MongoTemplate	mongoTemplate;
	@Autowired private ISequenceCounterService	sequenceCounterService;

	@Override
	public void save(InspectionToParameterDocument document) throws Exception {
		
		document.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.INSPECTION_PARAMETER_DOCUMENTS));
		
		mongoTemplate.save(document);
	}
	
	@Override
	public InspectionToParameterDocument getVehicleParameterDocument(Long documentId) throws Exception {
		try {
			
			Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(documentId).and("markForDelete").is(false));
			return mongoTemplate.findOne(query, InspectionToParameterDocument.class);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public List<InspectionToParameterDocument> getVehicleParameterDocumentList(long completionToParameterId, int companyId) throws Exception {
		Query query = new Query();
		query.fields().include("_id").include("name");
		query.addCriteria(Criteria.where("completionToParameterId").is(completionToParameterId).and("companyId").is(companyId).and("markForDelete").is(false));
		
		return mongoTemplate.find(query,InspectionToParameterDocument.class);
		
	}
}
