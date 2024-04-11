package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.persistence.document.DriverDocument;
import org.fleetopgroup.persistence.document.ServiceEntriesDocument;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class ServiceEntriesDocumentService implements IServiceEntriesDocumentService{

	 
	@Autowired
	private ISequenceCounterService	sequenceCounterService;
	
	@Autowired
	private MongoTemplate	mongoTemplate;
	
	@Override
	public void saveServiceEntriesDoc(ServiceEntriesDocument document) {
		
		try {
			
			document.setService_documentid(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_SERVICE_ENTRIES_DOCUMENT));	
			
			mongoTemplate.insert(document);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ServiceEntriesDocument getServiceEntriesDocument(Long id, Integer companyId) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id).and("companyId").is(companyId));

		return mongoTemplate.findOne(query, ServiceEntriesDocument.class);
	}
	
	@Override
	public ServiceEntriesDocument getServiceEntriesDocByServiceId(Long id, Integer companyId) throws Exception {
		
		Query query = new Query();
		query.addCriteria(Criteria.where("serviceEntries_id").is(id).and("companyId").is(companyId).and("markForDelete").is(false));

		return mongoTemplate.findOne(query, ServiceEntriesDocument.class);
	}
	
	@Override
	public void deleteByServiceId(Long seId) throws Exception {
		Query query = new Query();
		query.addCriteria(Criteria.where("serviceEntries_id").is(seId));

		mongoTemplate.remove(query, ServiceEntriesDocument.class);
	}
	
	@Override
	public void transferDataFromMySqlToMongoDb(List<org.fleetopgroup.persistence.model.ServiceEntriesDocument> list)
			throws Exception {
		List<ServiceEntriesDocument> entriesDocuments = null; 
		ServiceEntriesDocument		serviceDocument	= null;
		try {
			entriesDocuments	= new ArrayList<>();
			
			
			for (org.fleetopgroup.persistence.model.ServiceEntriesDocument serviceEntriesDocument : list) {
				
				serviceDocument	= new ServiceEntriesDocument();
				
				serviceDocument.setService_documentid(serviceEntriesDocument.getService_documentid());
				serviceDocument.setServiceEntries_id(serviceEntriesDocument.getServiceEntries_id());
				serviceDocument.setService_filename(serviceEntriesDocument.getService_filename());
				serviceDocument.setService_content(serviceEntriesDocument.getService_content());
				serviceDocument.setService_contentType(serviceEntriesDocument.getService_contentType());

				serviceDocument.setCreated(serviceEntriesDocument.getCreated());
				serviceDocument.setLastupdated(serviceEntriesDocument.getLastupdated());
				serviceDocument.setCompanyId(serviceEntriesDocument.getCompanyId());
				serviceDocument.setCreatedById(serviceEntriesDocument.getCreatedById());
				serviceDocument.setLastModifiedById(serviceEntriesDocument.getLastModifiedById());
				entriesDocuments.add(serviceDocument);
			}
			System.err.println("about to Save....");
			mongoTemplate.insert(entriesDocuments, ServiceEntriesDocument.class);
			System.err.println("Saved Successfully....");
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
		
		
	}

	@Override
	public void transferDriverDocument(List<org.fleetopgroup.persistence.model.DriverDocument> list) throws Exception {
		DriverDocument				 driverDocument		 = null;
		List<DriverDocument> 		 driverDocumentList	 = null; 
		try {
			driverDocumentList	= new ArrayList<>();
			for (org.fleetopgroup.persistence.model.DriverDocument document : list) {
				driverDocument	= new DriverDocument();
				driverDocument.setDriver_documentid(document.getDriver_documentid());
				driverDocument.setDriver_filename(document.getDriver_filename());
				driverDocument.setDriver_content(document.getDriver_content());
				driverDocument.setDriver_contentType(document.getDriver_contentType());
				driverDocument.setCompanyId(document.getCompanyId());
				driverDocument.setCreatedById(document.getCreatedById());
				driverDocument.setUploaddate(new Date());
				driverDocument.setDriver_documentname(document.getDriver_documentname());
				driverDocument.setDriver_id(document.getDriver_id());
				
				driverDocumentList.add(driverDocument);
			}
			System.err.println("Saving driverDocument ....");
			mongoTemplate.insert(driverDocumentList, DriverDocument.class);
			System.err.println("Saved Successfully....");
		} catch (Exception e) {
			
		}
	}
}
