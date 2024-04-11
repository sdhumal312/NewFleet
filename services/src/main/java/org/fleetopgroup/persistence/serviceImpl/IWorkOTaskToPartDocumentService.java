package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface IWorkOTaskToPartDocumentService {
	
	public void save(org.fleetopgroup.persistence.document.WorkOTaskToPartDocument	document) throws Exception;
	
	public org.fleetopgroup.persistence.document.WorkOTaskToPartDocument get_WorkOTaskToPart_Document(Long workordertaskto_partid, Integer companyId);
	
	public List<org.fleetopgroup.persistence.document.WorkOTaskToPartDocument> getWorkOTaskToPartById(long workordertaskto_partid, int companyId) throws Exception;
	
	public void deleteById(Long workordertaskto_partid, Integer companyId) throws Exception;

}
