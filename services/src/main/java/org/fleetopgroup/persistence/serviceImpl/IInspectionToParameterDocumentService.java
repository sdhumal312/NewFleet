package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.document.InspectionToParameterDocument;

public interface IInspectionToParameterDocumentService {

	public void save(InspectionToParameterDocument document) throws Exception;
	
	public InspectionToParameterDocument getVehicleParameterDocument(Long documentId) throws Exception;
	
	public List<InspectionToParameterDocument> getVehicleParameterDocumentList(long completionToParameterId, int companyId) throws Exception;
}
