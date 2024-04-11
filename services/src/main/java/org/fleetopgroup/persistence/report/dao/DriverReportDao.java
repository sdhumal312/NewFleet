package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverReportDao {
	
	public List<TripSheetDto> getDriverTripsheetAdvanceDetails(String query, int companyId) throws Exception;
	
}