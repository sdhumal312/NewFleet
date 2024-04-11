package org.fleetopgroup.persistence.report.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.MonthWiseVehicleBalance;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthWiseVehicleBalanceDao {
	
	 public void updateMonthBalance(ValueObject	valueObject) throws Exception;
	
	 public void subtractAddAmountForPreviousFuelDate(MonthWiseVehicleBalance monthWiseVehicleBalanceDao, Double paramDouble, boolean paramBoolean, Timestamp paramTimestamp) throws Exception;
	 
	 public void updateAllDayBalanceForBackDateEdit(MonthWiseVehicleBalance	monthWiseVehicleBalance, Double amount, Timestamp previousDate) throws Exception;
	 
	 public void updateCurrentNextDateAllDayBalanceAmount(ValueObject paramValueObject) throws Exception;
	 
	 public void updateAllDayBalanceForNextDateEdit(MonthWiseVehicleBalance	dateWiseVehicleBalance, Double amount, Timestamp previousDate, Timestamp fuelDate) throws Exception;
	 
	 public void updateNextDateAllDayBalanceAmount(ValueObject paramValueObject) throws Exception;
	 
	 public void addNextDateAllDayBalanceAmount(ValueObject paramValueObject) throws Exception;
	 
	 public void updateMonthWiseVehicleBalanceUpdated(String txnids) throws Exception;
	 
	 public MonthWiseVehicleBalance validateMonthWiseVehicleBalance(Integer vid, String	firstDate, Integer companyId) throws Exception;
	 
	 public MonthWiseVehicleBalance getLastMonthWiseVehicleBalance(Integer vid, String	firstDate, Integer companyId) throws Exception;
	 
	 
	 
	 
}
