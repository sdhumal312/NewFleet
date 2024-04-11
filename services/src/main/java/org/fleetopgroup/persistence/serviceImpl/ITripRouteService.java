package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripRouteDto;
import org.fleetopgroup.persistence.dto.TripRoutefixedExpenseDto;
import org.fleetopgroup.persistence.dto.TripRoutefixedIncomeDto;
import org.fleetopgroup.persistence.model.TripRoute;
import org.fleetopgroup.persistence.model.TripRoutefixedExpense;
import org.fleetopgroup.persistence.model.TripRoutefixedIncome;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;


public interface ITripRouteService {

	public TripRoute addTripRoute(TripRoute status) throws Exception;
	
	public void addTripRouteFixedExpene(TripRoutefixedExpense fixedExpense) throws Exception;

	public void updateTripRoute(TripRoute status) throws Exception;

	public TripRoute getTripRoute(int RouteID) throws Exception;
	
	public TripRouteDto getTripRouteDetails(int RouteID, Integer companyId) throws Exception;

	public void deleteTripRoute(TripRoute status) throws Exception;

	public List<TripRoute> SearchOnlyRouteNAME(String Search, CustomUserDetails	userDetails) throws Exception;
	
	public List<TripRoutefixedExpense> listTripRouteFixedExpenes(Integer routeID, Integer companyId) throws Exception;
	
	public List<TripRoutefixedExpenseDto> listTripRouteFixedExpene(Integer routeID, Integer companyId) throws Exception;
	
	public List<TripRoutefixedExpenseDto> listTripRouteFixedExpeneList(Integer routeID, Long tripSheetId, Integer companyId) throws Exception;
	
	public List<TripRoutefixedExpense> ValidateTripRouteFixedExpene(Integer expenseId, Integer routeID, Integer companyId) throws Exception;
	
	public TripRoutefixedExpense getTripRoutefixedExpense(Integer RoutefixedID, Integer companyId) throws Exception;
	
	public void deleteTripRoutefixedExpense(Integer fixedExpense, Integer companyId) throws Exception;
	
	public List<TripRoute> listTripRoute(CustomUserDetails	userDetails) throws Exception;

	/**
	 * @param pageNumber
	 * @return
	 */
	public Page<TripRoute> getDeployment_Page_TripRoute(Integer pageNumber, CustomUserDetails	userDetails) throws Exception;

	/**
	 * @param pageNumber
	 * @return
	 */
	public List<TripRoute> GET_list_Of_TripRoute(Integer pageNumber, CustomUserDetails	userDetails) throws Exception;

	/**
	
	/**
	 * 
	 * @param name
	 * @param companyId
	 * @return
	 * @throws Exception
	 */
	public TripRoute validateTripRoute(String name, Integer companyId)throws Exception;
	
	public List<TripRoute> SearchOnlyRoute_MAIN_ROUTE_NAME(String Search, CustomUserDetails	userDetails) throws Exception;
	
	public List<TripRoute> SearchOnlyRoute_MAIN_ROUTE_BY_DEPOTID(Long Search, CustomUserDetails	userDetails) throws Exception;
	public List<TripRoute> SearchOnlyRoute_SUB_ROUTE_BY_RouteId(Integer Search, CustomUserDetails	userDetails) throws Exception;
	
	
	public List<TripRoutefixedIncome> ValidateTripRouteFixedIncome(Integer string, Integer routeID , Integer Company_id);

	public void addTripRouteFixedIncome(TripRoutefixedIncome tripIncome);

	public List<TripRoutefixedIncome> listTripRouteFixedIncome(Integer routeID);
	
	public List<TripRoutefixedIncomeDto> listTripRouteFixedIncome(Integer routeID, Integer companyId) throws Exception;

	public TripRoutefixedIncome getTripRoutefixedIncome(Integer routefixedID, Integer companyId) throws Exception;

	public void deleteTripRoutefixedIncome(Integer routefixedID, Integer companyId) throws Exception;

	public List<TripRoute> Validate_TRIP_ROUTE_NAME(String routeName, Integer routeType);

	public List<TripRoute> SearchOnlyRoute_SUB_ROUTE_NAME(String term, CustomUserDetails	userDetails) throws Exception;
	
	public List<TripRoute> getTripRouteSerachList(String term, CustomUserDetails	userDetails) throws Exception;
	
	public List<TripRoute> getTripRouteListByType(Integer routeType, CustomUserDetails	userDetails) throws Exception;
	
	
	
	public Integer	getTripRouteApproxKm(Integer routeId) throws Exception;

	public List<TripRoute> getRouteListByVehicleGroupId(Long vehicleGroupId, Integer company_id)throws Exception;

	public List<TripRoutefixedIncome> listTripRouteFixedIncomeList(Integer routeID, int companyId)throws Exception;
	
	public TripRoute getTriproutePoint(Integer routeID, int companyId) throws Exception;
	
	public ValueObject getTripRouteNameWiseList(ValueObject object) throws Exception;

	public ValueObject getTripRouteFixedExpeneByExpenseId(ValueObject valueInObject)throws Exception;

}