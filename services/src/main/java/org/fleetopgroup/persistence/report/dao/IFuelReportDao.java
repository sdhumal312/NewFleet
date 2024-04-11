package org.fleetopgroup.persistence.report.dao;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.FuelDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.web.util.ValueObject;

public interface IFuelReportDao {
	
	public List<TripDailySheetDto> getFuelTripDailyList(String Query, Integer companyId) throws Exception;
	
	public List<TripDailySheetDto> getFuelEfficiencyDtosReport(String Query, Integer companyId) throws Exception;
	
	public void getFinalMapOfFuelEfficiencyDataReport(List<TripDailySheetDto> finalListOfFuel,
			HashMap<Long, TripDailySheetDto> finalFuelMap) throws Exception;

	public List<FuelDto> getFuelEntryDetails_Report(String query,Integer companyId)throws Exception;

	public List<FuelDto> getUserWiseFuelEntryDetails_Report(String query, Integer company_id, String vehicleWiseGrpData)throws Exception;

	public List<FuelDto> getFuelConsumptionList(ValueObject object)throws Exception;
	
	
}