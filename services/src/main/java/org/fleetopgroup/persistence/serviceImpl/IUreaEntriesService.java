package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.UreaEntriesDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.UreaEntries;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface IUreaEntriesService {

	ValueObject	saveUreaEntriesDetails(ValueObject	valueObject)throws Exception;
	
	ValueObject	getPageWiseUreaEntriesDetails(ValueObject	valueObject)throws Exception;
	
	public Page<UreaEntries> getDeployment_Page_UreaInvoice(Integer pageNumber, Integer companyId);
	
	public List<UreaEntriesDto> getUreaEntriesDtoList(Integer pageNumber, Integer companyId) throws Exception;
	
	ValueObject	getShowUreaEntryDetails(ValueObject	valueObject)throws Exception;
	
	public UreaEntriesDto getUreaEntriesDtoDetails(Long ureaEntriesId, Integer companyId) throws Exception;
	
	ValueObject	searchUreaEntriesByNumber(ValueObject valueObject)throws Exception;
	
	public UreaEntriesDto getUreaEntriesDetailsByInvcId(Long ureaEntriesId, Integer companyId) throws Exception;
	
	public ValueObject updateUreaEntriesDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject deleteUreaEntryById(ValueObject valueObject) throws Exception;

	public ValueObject getdateWiseUreaEntryDetailsReport(ValueObject object)throws Exception;//date wise Urea Report
	
	public void deletePreviousVehicleUreaEntries(UreaEntries ureaEntries) throws Exception;
	
	public void updateVidOfUreaEntries(long tripSheetId, int vid, int companyId) throws Exception;
	
	public void saveVehicleProfitAndLossTxnCheckerForUrea(ValueObject valueObject) throws Exception;
	
	public List<UreaEntriesDto> getUreaEntriesDetailsByTripSheetId(Long tripSheetId, Integer companyId) throws Exception;
	
	public List<UreaEntriesDto> getUreaEntriesDetailsByDateRange(Integer vid, String fromDate, String toDate) throws Exception;

	public Map<Integer, List<UreaEntriesDto>> getAllUreaEntriesDetails(String startDate, String endDate)throws Exception;
	
	public List<DateWiseVehicleExpenseDto> getDateWiseUreaExpenseDtoByVid(Integer vid, String fromDate, String toDate,
			Integer companyId) throws Exception;
	
	public List<MonthWiseVehicleExpenseDto> getMonthWiseUreaExpenseDtoByVid(Integer vid, String fromDate, String toDate,
			Integer companyId) throws Exception;
	
	
	public List<DateWiseVehicleExpenseDto> getDateWiseUreaExpenseDtoByRouteId(Integer vid, String fromDate, String toDate,
			Integer companyId, Integer routeId) throws Exception;
	
	public List<DateWiseVehicleExpenseDto> getDateWiseUreaExpenseDtoByVTId(Integer vid, String fromDate, String toDate,
			Integer companyId, Long routeId) throws Exception;
	
	public List<DateWiseVehicleExpenseDto> getDateWiseUreaExpenseDtoByVTRouteId(Integer vid, String fromDate, String toDate,
			Integer companyId, Long vehicleTypeId, Integer routeId) throws Exception;

	public ValueObject getPreNextUreaEntires(ValueObject object)throws Exception;

	public VehicleDto getPreviousNextUreaEntryDetails(String query) throws Exception;

	public void updateNextOldOdometer(Long ureaEntriesId, Double oldOdometer, Integer companyId) throws Exception;

	public ValueObject searchUreaEntries(ValueObject object) throws Exception;

	public Page<UreaEntries> getDeployment_Page_SerachUreaEntries(Integer pageNumber, Timestamp startDate, Timestamp endDate, Integer companyId);

	public HashMap<Integer, Long> getUreaEntriesCountHMOnCreated(String startDate, String endDate) throws Exception;
	
	public ValueObject getMissingUreaEntryAlertByCompanyId(Integer company_id)throws Exception;

	public List<UreaEntriesDto> getUreaConsumptionList(ValueObject object)throws Exception;

}
