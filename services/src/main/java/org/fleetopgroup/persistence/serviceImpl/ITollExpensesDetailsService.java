package org.fleetopgroup.persistence.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.TollExpensesDetailsDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.model.TollExpenseData;
import org.fleetopgroup.persistence.model.TollExpensesDetails;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface ITollExpensesDetailsService {

	ValueObject	addVehicleTripTollExpenses(ValueObject	valueObject) throws Exception;
	
	public void saveTollExpenses(TollExpensesDetails	tollExpensesDetails) throws Exception;
	
	public List<TollExpensesDetails>	validateTollExpensesDetails(String transactionId, Integer companyId) throws Exception;
	
	public ValueObject	getTollExpensesDetailsList(Long tripSheetId, Integer companyId) throws Exception;

	public List<TollExpensesDetailsDto> getTripsheetTollAmount(int vid, String fromDate, String toDate,
			CustomUserDetails userDetails, int tripFlavor)throws Exception;
	
	public List<TollExpensesDetailsDto> getTripsheetTollAmountByGid(Long vid, String fromDate, String toDate,
			CustomUserDetails userDetails, int tripFlavor)throws Exception;

	List<TollExpensesDetailsDto> getRouteWiseTripsheetTollAmount(String fromDate, String toDate,
			CustomUserDetails userDetails, int tripFlavor)throws Exception;
	
	public TollExpensesDetailsDto getTripTotalTollExpenseAmount(String startDate, String endDate,Integer companyId) throws Exception;

	List<TollExpensesDetailsDto> getRouteWiseTripsheetTollExpenseAmount(String routeIDT, String fromDate, String toDate, CustomUserDetails userDetails, int tripFlavor)throws Exception;
	
	public Double getTripSheetTollAmount(Long tripSheetId) throws Exception;
	
	public List<DateWiseVehicleExpenseDto> getDateWiseFastTagTollExpenseDtoByVid(Integer vid, String fromDate, String toDate) throws Exception ;
	
	public List<MonthWiseVehicleExpenseDto> getMonthWiseFastTagTollExpenseDtoByVid(Integer vid, String fromDate, String toDate) throws Exception ;
	
	public List<DateWiseVehicleExpenseDto> getDateWiseFastTagTollExpenseDtoByRouteId(Integer vid, String fromDate, String toDate, Integer routeId) throws Exception ;

	public List<TripSheetExpenseDto>  getTollExpensesForTally(String fromDate, String toDate, Integer companyId,
			String tallyCompany) throws Exception;

	public List<TripSheetExpenseDto>  getTollExpensesForTallyATC(String fromDate, String toDate, Integer companyId,
			String tallyCompany) throws Exception;
	
	public ValueObject	karinsFasttagExpenses(List<TollExpenseData>  tollExpenseList) throws Exception;
	
	public HashMap<Object, Object>	karinsFasttagExpenses(HashMap<Object, Object> allRequestParams) throws Exception;
	
}
