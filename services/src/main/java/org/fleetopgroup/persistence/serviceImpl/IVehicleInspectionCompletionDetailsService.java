package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.VehicleInspctionSheetAsingmentDto;
import org.fleetopgroup.persistence.dto.VehicleInspectionCompletionDetailsDto;
import org.fleetopgroup.persistence.model.VehicleInspectionCompletionDetails;

public interface IVehicleInspectionCompletionDetailsService {

	public void saveVehicleInspectionCompletionDetails(VehicleInspectionCompletionDetails	details) throws Exception;
	
	public long getTodaysVehicleInspectedCount(Integer companyId, Timestamp	inspectionStartDate) throws Exception;
	
	public List<VehicleInspectionCompletionDetailsDto> getTodaysInspectedList(Integer companyId, Timestamp	inspectionStartDate) throws Exception;
	
	public List<VehicleInspctionSheetAsingmentDto> getTodaysInspectionPendingList(Integer companyId, Timestamp	inspectionStartDate) throws Exception;
	
	public List<VehicleInspectionCompletionDetailsDto> getTodaysSavedInspectedList(Integer companyId, Timestamp	inspectionStartDate) throws Exception;
	
	public List<MonthWiseVehicleExpenseDto> getMonthWiseInspectionPenaltyByVid(Integer vid, String fromDate,
			String toDate, Integer companyId) throws Exception ;
	
	public List<DateWiseVehicleExpenseDto> getDateWiseInspectionPenaltyByVid(Integer vid, String fromDate,
			String toDate, Integer companyId) throws Exception;
	
	public Map<Integer,Double> getMonthWiseInspectionPenaltyByVGroup(Long vehicleGroupId ,Timestamp fromdate ,Timestamp toDate ,int companyId) throws Exception;

	public List<VehicleInspectionCompletionDetailsDto> getDateWiseInspectedList(Integer companyId ,Integer vid,Timestamp fromdate ,Timestamp toDate) throws Exception;
	
	public List<VehicleInspectionCompletionDetailsDto> getInspectionComplitionDetails(String query) throws Exception;
}
