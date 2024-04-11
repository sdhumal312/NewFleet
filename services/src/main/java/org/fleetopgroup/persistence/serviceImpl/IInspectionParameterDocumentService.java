package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.document.InspectionParameterDocument;
import org.springframework.stereotype.Service;

@Service
public interface IInspectionParameterDocumentService {
	
	public Integer saveInspectionParameterDocument(InspectionParameterDocument	inspectParamDoc);
	
	// parameter photo to get id
	public List<org.fleetopgroup.persistence.document.InspectionParameterDocument> listInspectionParameterPhoto(long parameterId) throws Exception;
	
	public org.fleetopgroup.persistence.document.InspectionParameterDocument getInspectionParameterPhoto(long parameterId, Integer companyId) throws Exception;

	public void deleteParameterDocument(int documentid) throws Exception;
}