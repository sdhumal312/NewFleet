package org.fleetopgroup.persistence.service;

import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.document.TyreDocument;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.ITyreDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class TyreDocumentService implements ITyreDocumentService{

	 
	@Autowired
	private ISequenceCounterService	sequenceCounterService;
	
	@Autowired
	private MongoTemplate	mongoTemplate;
	
	
	@Transactional
	public void add_Tyre_To_TyreDocument(org.fleetopgroup.persistence.document.TyreDocument tyreDoc) {
		tyreDoc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_INVENTORY_TYRE_DOCUMENT));
		mongoTemplate.save(tyreDoc);
	
	}
	
	@Override
	public TyreDocument getTyreDocument(Long id, Integer companyId) throws Exception {// for download 
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id).and("companyId").is(companyId));

		return mongoTemplate.findOne(query, TyreDocument.class);
	}
	

	@Override
	public TyreDocument getTyreDocByTyreInvoiceId(Long id, Integer companyId) throws Exception {// checking already exists or not
		
		Query query = new Query();
		query.addCriteria(Criteria.where("tyre_id").is(id).and("companyId").is(companyId).and("markForDelete").is(false));

		return mongoTemplate.findOne(query, TyreDocument.class);
	}
}
