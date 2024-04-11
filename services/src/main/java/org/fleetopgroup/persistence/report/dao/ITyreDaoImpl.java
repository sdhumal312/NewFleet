package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.InventoryTyreHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreRetreadAmountDto;

public interface ITyreDaoImpl {
	
	public List<InventoryTyreRetreadAmountDto> TyreRetreadInvoiceReportList(String Query, Integer companyId) throws Exception;

	public List<InventoryTyreHistoryDto> getVehicleTyreAssignmentHistoryReportList(String query, Integer company_id) throws Exception;
	
}