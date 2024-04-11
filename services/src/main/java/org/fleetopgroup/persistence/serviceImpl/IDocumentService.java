package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;

public interface IDocumentService {

	ValueObject	getDocumentByDocumentId(Long id, Integer companyId, short type) throws Exception;
}
