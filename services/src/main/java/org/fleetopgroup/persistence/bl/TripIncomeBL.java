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

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.TripIncome;
import org.fleetopgroup.persistence.model.TripIncomeHistory;
import org.fleetopgroup.web.util.ValueObject;



public class TripIncomeBL {
	public TripIncomeBL() {
	}

	// save the TripIncome Model
	public TripIncome prepareModel(TripIncome TripIncomeBean) {
		java.util.Date 	currentDateUpdate 	= new java.util.Date();
		Timestamp 		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
		
		TripIncome status = new TripIncome();
		status.setIncomeID(TripIncomeBean.getIncomeID());
		status.setIncomeName(TripIncomeBean.getIncomeName());
		status.setIncomeRemarks(TripIncomeBean.getIncomeRemarks());
		status.setIncomeType(TripIncomeBean.getIncomeType());
		status.setCreated(toDate);
		status.setLastupdated(toDate);
		
		return status;
	}

	
	// save the TripIncome Model
		public TripIncome prepareUpdate(TripIncome TripIncomeBean) {
			java.util.Date 	currentDateUpdate 	= new java.util.Date();
			Timestamp 		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());

			TripIncome status = new TripIncome();
			status.setIncomeID(TripIncomeBean.getIncomeID());
			status.setIncomeName(TripIncomeBean.getIncomeName());
			status.setIncomeRemarks(TripIncomeBean.getIncomeRemarks());
			status.setLastupdated(toDate);
			
			return status;
		}

	
	// show the List Of Vehicle Status
	public List<TripIncome> prepareListofBean(List<TripIncome> TripIncome) {
		List<TripIncome> beans = null;
		if (TripIncome != null && !TripIncome.isEmpty()) {
			beans = new ArrayList<TripIncome>();
			TripIncome bean = null;
			for (TripIncome Income : TripIncome) {
				bean = new TripIncome();
				bean.setIncomeID(Income.getIncomeID());
				bean.setIncomeName(Income.getIncomeName());
					bean.setIncomeRemarks(Income.getIncomeRemarks());

				beans.add(bean);
			}
		}
		return beans;
	}

	// edit Show TripIncomeBean
	public TripIncome prepareTripIncomeBean(TripIncome Income) {
		TripIncome bean = new TripIncome();
		bean.setIncomeID(Income.getIncomeID());
		bean.setIncomeName(Income.getIncomeName());
		bean.setIncomeRemarks(Income.getIncomeRemarks());

		return bean;
	}

	public TripIncomeHistory prepareHistoryModel(TripIncome tripIncome) {
		TripIncomeHistory		tripIncomeHistory	= new TripIncomeHistory();
		
		tripIncomeHistory.setCompanyId(tripIncome.getCompanyId());
		tripIncomeHistory.setIncomeID(tripIncome.getIncomeID());
		tripIncomeHistory.setIncomeName(tripIncome.getIncomeName());
		tripIncomeHistory.setIncomeRemarks(tripIncome.getIncomeRemarks());
		tripIncomeHistory.setIncomeType(tripIncome.getIncomeType());
		tripIncomeHistory.setLastModifiedById(tripIncome.getLastModifiedById());
		tripIncomeHistory.setLastupdated(tripIncome.getLastupdated());
		tripIncomeHistory.setMarkForDelete(tripIncome.isMarkForDelete());
		
		return tripIncomeHistory;
	}

	public TripIncome prepareTripIncomeWithRate(ValueObject valueObject, CustomUserDetails userDetails) throws Exception {
	try {
		java.util.Date 	currentDateUpdate 	= new java.util.Date();
		Timestamp 		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
		
		TripIncome tripIncome = new TripIncome();
		tripIncome.setIncomeType(valueObject.getInt("incomeType"));
		tripIncome.setIncomeName(valueObject.getString("incomeName"));
		tripIncome.setIncomeRemarks(valueObject.getString("discription"));
		tripIncome.setCreated(toDate);
		tripIncome.setLastupdated(toDate);
		tripIncome.setCreatedById(userDetails.getId());
		tripIncome.setLastModifiedById(userDetails.getId());
		tripIncome.setCommission(valueObject.getDouble("commision"));
		tripIncome.setGst(valueObject.getDouble("gst"));
		tripIncome.setCompanyId(userDetails.getCompany_id());
		
		
		return tripIncome;
	} catch (Exception e) {
		throw e;
	}
		
	}
}
