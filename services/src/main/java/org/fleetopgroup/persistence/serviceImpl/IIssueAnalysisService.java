package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.dto.IssueAnalysisDto;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IIssueAnalysisService {

	ValueObject saveIssueAnalysis(ValueObject valueObject)throws Exception;

	ValueObject getIssueAnalysisDetails(ValueObject valueObject)throws Exception;

	IssueAnalysisDto getIssueAnalysisDetailsByIssueId(Long issueId) throws Exception;


}
