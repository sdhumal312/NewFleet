package org.fleetopgroup.persistence.report.dao;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.TripSheetReportDto;
import org.fleetopgroup.web.util.ValueObject;

public interface TripSheetReportDao {

	public List<TripSheetReportDto>   getTripSheetReportDtoList(String filterQuery, Integer companyId, Long userId) throws Exception;
	
	public HashMap<Long, TripSheetReportDto>   getTripSheetFuelData(String filterQuery, Integer companyId, Long userId) throws Exception;
	
	public HashMap<Long, TripSheetReportDto>   getTripExpenseDataHM(String filterQuery, Integer companyId, Long userId) throws Exception;
	
	public HashMap<Long, TripSheetReportDto>   getTripTollDataHM(String filterQuery, Integer companyId, Long userId) throws Exception;
	
	public HashMap<Long, TripSheetReportDto>   getTripDueAmount(String filterQuery, Integer companyId, Long userId) throws Exception;

	public List<TripSheetReportDto> getTripincomeDetailsByIncomeId(ValueObject valueObject)throws Exception;
}
