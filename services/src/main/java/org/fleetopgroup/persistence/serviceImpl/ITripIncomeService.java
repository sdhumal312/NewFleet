package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TripIncome;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;


public interface ITripIncomeService {

	public void addTripIncome(TripIncome status) throws Exception;

	public void updateTripIncome(TripIncome status) throws Exception;

	public List<TripIncome> listTripIncome() throws Exception;

	public TripIncome getTripIncome(int IncomeID, Integer companyId) throws Exception;

	public void deleteTripIncome(Integer status, Integer companyId) throws Exception;

	public List<TripIncome> listOnlyIncomeName(Integer companyId) throws Exception;
	
	public TripIncome  validateTripIncome(String IncomeNAME, Integer companyId) throws Exception;

	public List<TripIncome> SearchTripIncome_DropDown(String IncomeNAME, Integer companyId) throws Exception;
	
	public List<TripIncome> listTripIncome(Integer companyId) throws Exception;

	public List<TripIncome> listOnlyIncomeName_FixedIncome(Integer companyId);
	
	public List<TripIncome> listTrip_ROUTE_Income(Integer companyId) throws Exception;

	public List<TripIncome> listTrip_CASHBOOK_Income(Integer companyId) throws Exception;

	public Page<TripIncome> getDeployment_Page_TripIncome(Integer incomeType, Integer pageNumber, CustomUserDetails userDetails);

	public List<TripIncome> GET_list_Of_TripIncome(Integer incomeType, Integer pageNumber, CustomUserDetails userDetails);
	
	public ValueObject mobileTripIncomeList(ValueObject object) throws Exception;

	public ValueObject saveTripIncomeWithRate(ValueObject valueInObject)throws Exception;

	public ValueObject getTripIncomeById(ValueObject valueInObject)throws Exception;

	public ValueObject updateTripIncomeWithRate(ValueObject valueInObject)throws Exception;
	
	public List<TripIncome> getTripIncomeByName(String vehicle, Integer companyId) throws Exception;
}