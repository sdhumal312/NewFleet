package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.UreaEntriesDto;

public interface IUreaReportDao {

	public List<UreaEntriesDto> getUreaEntryDetails_Report(String query, Integer company_id)throws Exception;
	
	
	
}