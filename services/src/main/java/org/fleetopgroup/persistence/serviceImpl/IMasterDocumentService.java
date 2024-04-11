package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.document.MasterDocuments;

public interface IMasterDocumentService {
	/**
	 * @param Save Master Documents
	 */
	public void saveMasterDocuments(MasterDocuments masterDocuments);

	/**
	 * @param Save OR Update Master Documents
	 */
	public void saveOrUpdateMasterDocuments(MasterDocuments masterDocuments);
	
	/**
	 * @param document_type_id
	 * @return
	 */
	public MasterDocuments getMasterDocuemntByDocumentTypeId(Short documentTypeId);
}
