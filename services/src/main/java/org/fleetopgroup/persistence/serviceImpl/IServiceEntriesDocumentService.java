package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.document.ServiceEntriesDocument;
import org.fleetopgroup.persistence.model.DriverDocument;
import org.springframework.stereotype.Service;

@Service
public interface IServiceEntriesDocumentService {

	public void saveServiceEntriesDoc(ServiceEntriesDocument	document);
	
	public ServiceEntriesDocument	getServiceEntriesDocument(Long id , Integer companyId) throws Exception;
	
	public ServiceEntriesDocument	getServiceEntriesDocByServiceId(Long id , Integer companyId) throws Exception;
	
	public void transferDataFromMySqlToMongoDb(List<org.fleetopgroup.persistence.model.ServiceEntriesDocument> list) throws Exception;
	
	public void transferDriverDocument(List<DriverDocument> list) throws Exception;
	
	public void deleteByServiceId(Long seId) throws Exception;
}
