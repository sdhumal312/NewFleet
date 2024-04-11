package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.document.DriverDocument;
import org.springframework.stereotype.Service;

@Service
public interface IDriverDocumentService {

	public void saveDriverDoc(DriverDocument document);
	
	public List<DriverDocument> listDriverDocument(int diverreminder, Integer companyId) ;
	
	public long getDriverDocumentCount(int driver_id, Integer companyId) throws Exception;
	
	public DriverDocument getDriverDocument(Integer driver_documentid, Integer companyId) throws Exception;
	
	public void deleteDriverDocument(int driver_documentid, Integer companyId) throws Exception;
	
	public void deleteDriverDocumentByReciptId(long driver_documentid, Integer companyId) throws Exception ;

	long getDriverPhotoCount(int driver_id, Integer companyId) throws Exception;
	
}
