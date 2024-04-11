package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.document.PartDocument;
import org.springframework.stereotype.Service;

@Service
public interface IPartDocumentService {
	
	public void add_Part_To_PartDocument(PartDocument partDoc)throws Exception;

	
	public PartDocument getPartDocument(Long part_document_id, Integer company_id)throws Exception;


	public PartDocument getPartDocByPartInvoiceId(Long partInvoiceId, Integer company_id)throws Exception;
}
