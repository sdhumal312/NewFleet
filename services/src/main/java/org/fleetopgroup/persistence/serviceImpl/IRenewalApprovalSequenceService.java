package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.RenewalApprovalSequenceCounter;

public interface IRenewalApprovalSequenceService {
	
	public RenewalApprovalSequenceCounter findNextRenewalApproval_Number(Integer companyId) throws Exception;
	
	public void updateNextRenewalApproval_Number(long nextVal, Integer companyId) throws Exception;
}
