package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.BatteryAmountDto;

public interface IBatteryReportDao {
	
	public List<BatteryAmountDto> Battery_Purchase_Invoice_Report_List(String Query, Integer companyId) throws Exception;
}