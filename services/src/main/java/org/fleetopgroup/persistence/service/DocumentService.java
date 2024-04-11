package org.fleetopgroup.persistence.service;

import org.fleetopgroup.constant.DocumentTypeConstent;
import org.fleetopgroup.persistence.document.VehicleAccidentDocument;
import org.fleetopgroup.persistence.document.VehicleExpenseDocument;
import org.fleetopgroup.persistence.serviceImpl.IDocumentService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class DocumentService implements IDocumentService {
 
	@Autowired private  MongoTemplate							mongoTemplate;
	
	@Override
	public ValueObject getDocumentByDocumentId(Long id, Integer companyId, short type) throws Exception {
		try {
			Query query = new Query();
			 query.addCriteria(Criteria.where("_id").is(id).and("companyId").is(companyId));
			 
			return findDocument(query, type);
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	private ValueObject findDocument(Query query, short type) throws Exception{
		try {
			ValueObject	valueObject	= new ValueObject(); 
				switch (type) {
				  case DocumentTypeConstent.VEHICLE_ACCIDENT_DOCUMENT:
					VehicleAccidentDocument	document	=  mongoTemplate.findOne(query, VehicleAccidentDocument.class);
					
					valueObject.put("file", document);
					valueObject.put("fileName", document.getFileName());
					valueObject.put("content", document.getContent());
					valueObject.put("contentType", document.getContentType());
					break;
				  case DocumentTypeConstent.VEHICLE_EXPENSE_DOCUMENT:
					  VehicleExpenseDocument	expenseDocument	=  mongoTemplate.findOne(query, VehicleExpenseDocument.class);
						valueObject.put("file", expenseDocument);
						valueObject.put("fileName", expenseDocument.getFilename());
						valueObject.put("content", expenseDocument.getContent());
						valueObject.put("contentType", expenseDocument.getContentType());
						break;
				  default:
				        break;
				}
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
		
	}
}
