package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.RequisitionSequenceCounter;

public interface IRequisitionsequenceService {
	public RequisitionSequenceCounter findNextNumber(Integer companyId) throws Exception;
}
