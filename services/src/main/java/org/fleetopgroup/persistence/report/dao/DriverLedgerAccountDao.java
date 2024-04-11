package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.DriverLedgerDto;
import org.springframework.stereotype.Repository;
@Repository
public interface DriverLedgerAccountDao {
	
	public List<DriverLedgerDto> getDriverLedgerAccountReportList(String Query, Integer companyId) throws Exception;

}
