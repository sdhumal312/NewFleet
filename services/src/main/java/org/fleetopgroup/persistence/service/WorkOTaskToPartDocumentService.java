package org.fleetopgroup.persistence.service;

import java.util.List;

import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.document.WorkOTaskToPartDocument;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOTaskToPartDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class WorkOTaskToPartDocumentService implements IWorkOTaskToPartDocumentService {
	
	
	@Autowired private MongoTemplate mongoTemplate;
	@Autowired private ISequenceCounterService	sequenceCounterService;

	@Override
	public void save(WorkOTaskToPartDocument document) throws Exception {
		document.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_WO_TASK_TO_PART_DOCUMENT));
		mongoTemplate.save(document);

	}
	
	@Override
	public org.fleetopgroup.persistence.document.WorkOTaskToPartDocument get_WorkOTaskToPart_Document(Long workordertaskto_partid, Integer companyId) {
		 
		Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(workordertaskto_partid).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.WorkOTaskToPartDocument.class);
	}
	
	@Override
	public void deleteById(Long docId, Integer companyId) throws Exception {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(docId).and("companyId").is(companyId));
		mongoTemplate.findAndRemove(query, org.fleetopgroup.persistence.document.WorkOTaskToPartDocument.class);
		
	}
	
	
	@Override
	public List<org.fleetopgroup.persistence.document.WorkOTaskToPartDocument> getWorkOTaskToPartById(long workordertaskto_partid, int companyId) throws Exception {
		Query query = new Query();
		query.fields().include("_id").include("documentFilename").include("documentContentType").include("description");
		query.addCriteria(Criteria.where("workordertasktoPartid").is(workordertaskto_partid).and("companyId").is(companyId).and("markForDelete").is(false));
		
		return mongoTemplate.find(query, org.fleetopgroup.persistence.document.WorkOTaskToPartDocument.class);
		
	}

}
