package org.fleetopgroup.persistence.report.dao;

import java.sql.Timestamp;
import org.fleetopgroup.persistence.model.DateWiseVehicleBalance;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Repository;

@Repository
public abstract interface DateWiseVehicleBalanceDao
{
  public abstract void updateNextDateAllDayBalanceAmount(ValueObject paramValueObject)
    throws Exception;
  
  public abstract void subtractAddAmountForPreviousFuelDate(DateWiseVehicleBalance paramDateWiseVehicleBalance, Double paramDouble, boolean paramBoolean, Timestamp paramTimestamp)
    throws Exception;
  
  public abstract void addNextDayAmounts(DateWiseVehicleBalance paramDateWiseVehicleBalance, Double amount)
		    throws Exception;
  
  public void updateCurrentDateAllDayBalance(DateWiseVehicleBalance	dateWiseVehicleBalance, Double amount) throws Exception;
  
  public void updateAllDayBalanceForBackDateEdit(DateWiseVehicleBalance	dateWiseVehicleBalance, Double amount, Timestamp previousDate) throws Exception;
  
  public void updateAllDayBalanceForNextDateEdit(DateWiseVehicleBalance	dateWiseVehicleBalance, Double amount, Timestamp previousDate, Timestamp fuelDate) throws Exception;
  
  
  public abstract void updateCurrentNextDateAllDayBalanceAmount(ValueObject paramValueObject)
		    throws Exception;
  
  public void deleteDateWiseVehicleBalance(ValueObject	valueObject) throws Exception;
  
  public void addAmountToAllDayAmountForNextDays(DateWiseVehicleBalance paramDateWiseVehicleBalance, Double paramDouble, Timestamp paramTimestamp) throws Exception;
  
  public void updateDateWiseVehicleBalanceMultiple(String txnIds) throws Exception;
  
  public DateWiseVehicleBalance validateDateWiseVehicleBalance(Integer vid, Integer companyId, String	expenseDate) throws Exception;
  
  public DateWiseVehicleBalance getLastDateVehicleBalance(Integer vid, Integer companyId, String	expenseDate) throws Exception;
		  
}