package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.IssuesSequenceCounter;

public interface IIssuesSequenceService {

	public IssuesSequenceCounter findNextIssuesSequence_Number(Integer companyId) throws Exception;
	
	public void updateNextIssuesSequence_Number(long nextVal, Integer companyId) throws Exception;

}
