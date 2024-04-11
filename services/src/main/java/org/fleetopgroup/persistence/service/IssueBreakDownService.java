package org.fleetopgroup.persistence.service;

import org.fleetopgroup.persistence.dao.IssueBreakDownDetailsRepository;
import org.fleetopgroup.persistence.model.IssueBreakDownDetails;
import org.fleetopgroup.persistence.report.dao.IIssueBreakDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IssueBreakDownService implements IIssueBreakDownService  {
	
	
	@Autowired IssueBreakDownDetailsRepository  breakDownDetailsRepo;
	
	@Override
	@Transactional
	public IssueBreakDownDetails getBreakDownDetailsByIssueId(Long issueId ,Integer companyId) {
		return breakDownDetailsRepo.getIssueBreakDownDetails(issueId, companyId);
	}

}
