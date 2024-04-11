package org.fleetopgroup.persistence.service;

import java.util.List;

import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.document.DriverDocument;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocumentService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class DriverDocumentService implements IDriverDocumentService {
	
	@Autowired
	private ISequenceCounterService	sequenceCounterService;
	
	@Autowired
	private MongoTemplate	mongoTemplate;
	
	@Override
	public void saveDriverDoc(DriverDocument document) {
		
		document.setDriver_documentid((int) sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_DRIVER_DOCUMENT));	
		mongoTemplate.insert(document);
	}
	
	@Override
	public List<DriverDocument> listDriverDocument(int diverreminder, Integer companyId) {
		
		
		Query query = new Query();
		query.addCriteria(Criteria.where("driver_id").is(diverreminder).and("companyId").is(companyId).and("markForDelete").is(false));

		return mongoTemplate.find(query, DriverDocument.class);
	}
	
	@Override
	public long getDriverDocumentCount(int driver_id, Integer companyId) throws Exception {
		 Query query = new Query();
		query.addCriteria(Criteria.where("driver_id").is(driver_id).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.count(query, DriverDocument.class);
	}
	
	@Override
	public DriverDocument getDriverDocument(Integer driver_documentid, Integer companyId) throws Exception {
		 Query query = new Query();
			query.addCriteria(Criteria.where("_id").is(driver_documentid).and("companyId").is(companyId).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, DriverDocument.class);
	}
	
	@Override
	public void deleteDriverDocument(int driver_documentid, Integer companyId) throws Exception {
		
			Query query = new Query(Criteria.where("_id").is(driver_documentid).and("companyId").is(companyId));
			Update update = new Update();
			update.set("markForDelete", true);
	       mongoTemplate.updateFirst(query, update, DriverDocument.class);
	}
	@Override
	public void deleteDriverDocumentByReciptId(long driverRenewalReceiptId, Integer companyId) throws Exception {
		
			Query query = new Query(Criteria.where("driverRenewalReceiptId").is(driverRenewalReceiptId).and("companyId").is(companyId));
			Update update = new Update();
			update.set("markForDelete", true);
	       mongoTemplate.updateFirst(query, update, DriverDocument.class);
	}
	
	@Override
	public long getDriverPhotoCount(int driver_id, Integer companyId) throws Exception {
		 Query query = new Query();
			query.addCriteria(Criteria.where("driver_id").is(driver_id).and("companyId").is(companyId).and("markForDelete").is(false));
			return mongoTemplate.count(query, org.fleetopgroup.persistence.document.DriverPhoto.class);
	}
}
