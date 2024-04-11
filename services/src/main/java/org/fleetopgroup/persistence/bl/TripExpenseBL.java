package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.persistence.model.TripExpenseHistory;



public class TripExpenseBL {
	public TripExpenseBL() {
	}

	// save the TripExpense Model
	public TripExpense prepareModel(TripExpense TripExpenseBean) {
		java.util.Date 	currentDateUpdate 	= new java.util.Date();
		Timestamp 		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
		
		TripExpense status = new TripExpense();
		status.setExpenseID(TripExpenseBean.getExpenseID());
		status.setExpenseName(TripExpenseBean.getExpenseName());
		status.setExpenseRemarks(TripExpenseBean.getExpenseRemarks());
		status.setCreated(toDate);
		status.setLastupdated(toDate);
		status.setIncldriverbalance(TripExpenseBean.getIncldriverbalance());
		
		return status;
	}

	
	// save the TripExpense Model
		public TripExpense prepareUpdate(TripExpense TripExpenseBean) {
			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

			TripExpense status = new TripExpense();
			status.setExpenseID(TripExpenseBean.getExpenseID());
			status.setExpenseName(TripExpenseBean.getExpenseName());
			status.setExpenseRemarks(TripExpenseBean.getExpenseRemarks());
			status.setLastupdated(toDate);
			status.setIncldriverbalance(TripExpenseBean.getIncldriverbalance());
			return status;
		}

	
	// show the List Of Vehicle Status
	public List<TripExpense> prepareListofBean(List<TripExpense> TripExpense) {
		List<TripExpense> beans = null;
		if (TripExpense != null && !TripExpense.isEmpty()) {
			beans = new ArrayList<TripExpense>();
			TripExpense bean = null;
			for (TripExpense Expense : TripExpense) {
				bean = new TripExpense();
				bean.setExpenseID(Expense.getExpenseID());
				bean.setExpenseName(Expense.getExpenseName());
					bean.setExpenseRemarks(Expense.getExpenseRemarks());

				beans.add(bean);
			}
		}
		return beans;
	}

	// edit Show TripExpenseBean
	public TripExpense prepareTripExpenseBean(TripExpense Expense) {
		TripExpense bean = new TripExpense();
		bean.setExpenseID(Expense.getExpenseID());
		bean.setExpenseName(Expense.getExpenseName());
		bean.setExpenseRemarks(Expense.getExpenseRemarks());
		bean.setIncldriverbalance(Expense.getIncldriverbalance());
		return bean;
	}

	public TripExpenseHistory prepareHistoryModel(TripExpense expense) {
		TripExpenseHistory		tripExpenseHistory	= new TripExpenseHistory();		
		
		tripExpenseHistory.setCompanyId(expense.getCompanyId());
		tripExpenseHistory.setExpenseID(expense.getExpenseID());
		tripExpenseHistory.setExpenseName(expense.getExpenseName());
		tripExpenseHistory.setExpenseRemarks(expense.getExpenseRemarks());
		tripExpenseHistory.setLastModifiedById(expense.getLastModifiedById());
		tripExpenseHistory.setLastupdated(expense.getLastupdated());
		tripExpenseHistory.setMarkForDelete(expense.isMarkForDelete());
		
		return tripExpenseHistory;
	}
}
