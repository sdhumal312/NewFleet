package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitAndLossDao {

	List<TripSheetDto>   getVehicleUsageTripSheet(Integer vid, String fromDate, String toDate, CustomUserDetails	userDetails) throws Exception;
	
	List<TripSheetDto>   getGroupUsageTripSheet(Long vid, String fromDate, String toDate, CustomUserDetails	userDetails) throws Exception;
	
	List<TripSheetDto>   getVehicleUsageTripDailySheet(Integer vid, String fromDate, String toDate, CustomUserDetails	userDetails) throws Exception;
	
	List<TripSheetDto>   getGroupUsageTripDailySheet(Long vid, String fromDate, String toDate, CustomUserDetails	userDetails) throws Exception;
	
	List<TripSheetDto>   getRouteUsageTripSheet(Integer routeID, String vehicle, String fromDate, String toDate, CustomUserDetails	userDetails) throws Exception;
	
	List<TripSheetDto>   getRouteUsageTripDailySheet(Integer routeID, String vehicle, String fromDate, String toDate, CustomUserDetails	userDetails) throws Exception;

	List<TripSheetDto> getRouteWiseTripsheetExpense(String routeID, String fromDate, String toDate, CustomUserDetails userDetails) throws Exception;

	List<TripSheetDto> getRouteWiseTripsDailyExpense(String routeIDD, String fromDate, String toDate, CustomUserDetails userDetails) throws Exception;

}
