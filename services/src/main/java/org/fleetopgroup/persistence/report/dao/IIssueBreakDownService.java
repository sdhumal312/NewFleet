package org.fleetopgroup.persistence.report.dao;

import org.fleetopgroup.persistence.model.IssueBreakDownDetails;

public interface IIssueBreakDownService {
	
	public IssueBreakDownDetails getBreakDownDetailsByIssueId(Long issueId ,Integer companyId);

}
