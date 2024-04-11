package org.fleetopgroup.persistence.service;

import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.document.PartDocument;
import org.fleetopgroup.persistence.serviceImpl.IPartDocumentService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class PartDocumentService implements IPartDocumentService{

	 
	@Autowired
	private ISequenceCounterService	sequenceCounterService;
	
	@Autowired
	private MongoTemplate	mongoTemplate;
	
	
	
	@Transactional
	public void add_Part_To_PartDocument(org.fleetopgroup.persistence.document.PartDocument partDoc) {
		partDoc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_PART_DOCUMENT));
		mongoTemplate.save(partDoc);
	}
	
	
	@Override
	public PartDocument getPartDocument(Long id, Integer companyId) throws Exception {// for download 
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id).and("companyId").is(companyId));

		return mongoTemplate.findOne(query, PartDocument.class);
	}
	
	@Override
	public PartDocument getPartDocByPartInvoiceId(Long id, Integer companyId) throws Exception {// checking already exists or not
		
		Query query = new Query();
		query.addCriteria(Criteria.where("part_id").is(id).and("companyId").is(companyId).and("markForDelete").is(false));

		return mongoTemplate.findOne(query, PartDocument.class);
	}

}
