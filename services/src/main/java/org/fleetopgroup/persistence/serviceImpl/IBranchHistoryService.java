package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.BranchHistory;

public interface IBranchHistoryService {

	public void registerNewBranchHistory(BranchHistory branchHistory) throws Exception;

}
