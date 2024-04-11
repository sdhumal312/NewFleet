package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.BatteryDto;
import org.fleetopgroup.persistence.dto.BatteryHistoryDto;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;

public interface IVehicleDao {
	
	public List<BatteryDto> getVehicleWiseBatteryReportList(String Query, Integer companyId) throws Exception;
	
	public BatteryDto getBatteryHistoryList(int vid, short layoutPosition, long batteryId, Integer companyId) throws Exception;
	
	public List<InventoryTyreDto> getVehicleWiseTyreReportList(String Query, Integer companyId) throws Exception;
	
	public InventoryTyreDto getTyreHistoryList(int vid, String layoutPosition, long tyreId, String axle, Integer companyId) throws Exception;
	
}