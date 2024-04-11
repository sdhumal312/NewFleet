package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripExpenseDto;
import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;


public interface ITripExpenseService {

	public void addTripExpense(TripExpense status) throws Exception;

	public void updateTripExpense(TripExpense status) throws Exception;

	public List<TripExpense> listTripExpense() throws Exception;

	public TripExpense getTripExpense(int ExpenseID, Integer companyId) throws Exception;

	public void deleteTripExpense(Integer status) throws Exception;

	public List<TripExpense> listOnlyExpenseName() throws Exception;
	
	public List<TripExpense> ValidateTripExpense(String ExpenseNAME, Integer companyId) throws Exception;

	public List<TripExpense> SearchTripExpense_DropDown(String ExpenseNAME, Integer companyId) throws Exception;
	
	public List<TripExpense> listTripExpense(Integer companyId) throws Exception;
	
	public List<TripExpenseDto> listTripExpenseForCompare(Integer companyId) throws Exception;

	public Page<TripExpense> getDeployment_Page_TripExpense(Integer pageNumber, CustomUserDetails userDetails);

	public List<TripExpense> GET_list_Of_TripExpense(Integer pageNumber, CustomUserDetails userDetails);
	
	public TripExpense getTripExpenseByName(String name, Integer compannyId) throws Exception;
	//TOLL CHARGES

	public List<TripExpense> Search_ExpenseName_select(String term, Integer company_id) throws Exception;
	
	public ValueObject getTripExpenseList(ValueObject object) throws Exception;

	public TripExpense saveTripExpenses(TripExpense expense)throws Exception;
}