package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.document.TyreDocument;
import org.springframework.stereotype.Service;

@Service
public interface ITyreDocumentService {
	
	public void add_Tyre_To_TyreDocument(TyreDocument tyreDoc)throws Exception;

	public TyreDocument getTyreDocument(Long tyre_document_id, Integer company_id)throws Exception;

	public TyreDocument getTyreDocByTyreInvoiceId(Long ityre_ID, Integer company_id)throws Exception;

	
	
}
