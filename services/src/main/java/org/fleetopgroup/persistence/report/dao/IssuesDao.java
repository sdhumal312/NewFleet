package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.IssuesDto;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuesDao {

	List<IssuesDto> getIssuesList(IssuesDto issuesDto) throws Exception;

	List<IssuesDto> getIssuesAllList(IssuesDto issuesDto) throws Exception;
	
}
