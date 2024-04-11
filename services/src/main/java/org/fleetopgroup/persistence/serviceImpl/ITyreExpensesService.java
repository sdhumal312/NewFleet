package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.TyreExpenses;
import org.fleetopgroup.web.util.ValueObject;

//import com.fleetop.dto.UploadFile;

public interface ITyreExpensesService {

	public ValueObject saveTyreExpense(ValueObject valueObject) throws Exception;
	
	public TyreExpenses validateTyreExpense(String tyreExpenseName, Integer companyId) throws Exception;

	public ValueObject getAllTyreExpenses()throws Exception;

	public ValueObject getTyreExpenseByTyreExpenseId(ValueObject valueInObject)throws Exception;

	public ValueObject updateTyreExpense(ValueObject valueInObject)throws Exception;

	public ValueObject deleteTyreExpense(ValueObject valueInObject)throws Exception;

	public ValueObject getAllTyreExpenses2(String term)throws Exception;

	
}