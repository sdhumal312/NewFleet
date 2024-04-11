package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripsheetDueAmountDto;
import org.springframework.stereotype.Repository;

@Repository
public interface TripSheetDao {
	
	public List<TripSheetDto>  getTripSheetList(TripSheetDto tripSheetDto)throws Exception;

	public List<TripSheetDto> getCreateDayWiseExpenseReportList(String query,Integer companyId) throws Exception;
	
	public List<TripSheetDto> getCreateDayWiseFasttagExpenseReportList(String query,Integer companyId) throws Exception;
	
	public List<TripSheetDto> getVoucherDateWiseExpenseReportList(String query,Integer companyId) throws Exception;
	
	public List<TripsheetDueAmountDto> getDueAmountReportList(String query, Integer companyId);
	
	public TripsheetDueAmountDto getDueAmountPaymentById(long tripsheetDueAmountId, Integer companyId);
	
	public List<TripsheetDueAmountDto> getDueAmountPaymentReportList(String query, Integer companyId) throws Exception;
}
