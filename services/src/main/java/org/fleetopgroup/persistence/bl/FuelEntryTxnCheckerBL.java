package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Calendar;

import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.DateWiseVehicleBalance;
import org.fleetopgroup.persistence.model.DateWiseVehicleExpense;
import org.fleetopgroup.persistence.model.MonthWiseVehicleBalance;
import org.fleetopgroup.persistence.model.MonthWiseVehicleExpense;
import org.fleetopgroup.persistence.model.MonthWiseVehicleIncome;
import org.fleetopgroup.persistence.model.MothWiseVehicleIncomeBalance;
import org.fleetopgroup.persistence.model.VehicleExpenseDetails;
import org.fleetopgroup.persistence.model.VehicleIncomeDetails;
import org.fleetopgroup.web.util.DateTimeUtility;

public class FuelEntryTxnCheckerBL {
	

/*public FuelEntryTxnChecker	getFuelEntryTxnCheckerDto(Fuel	fuel) throws Exception{
		
		FuelEntryTxnChecker			fuelEntryTxnChecker		= null;
		try {
			fuelEntryTxnChecker	= new FuelEntryTxnChecker();
			
			fuelEntryTxnChecker.setCompanyId(fuel.getCompanyId());
			fuelEntryTxnChecker.setVid(fuel.getVid());
			fuelEntryTxnChecker.setTxnInsertionDateTime(DateTimeUtility.getCurrentTimeStamp());
			fuelEntryTxnChecker.setFuelId(fuel.getFuel_id());
			
			return fuelEntryTxnChecker;
		} catch (Exception e) {
			throw e;
		}finally {
			fuelEntryTxnChecker		= null;
		}
	}*/

	public VehicleExpenseDetails createDataForVehicleExpenseDetails(VehicleProfitAndLossDto	vehicleProfitAndLossDto) throws Exception{
		VehicleExpenseDetails		vehicleExpenseDetails	= null;
		try {
			vehicleExpenseDetails	= new VehicleExpenseDetails();
			
			vehicleExpenseDetails.setVid(vehicleProfitAndLossDto.getVid());
			vehicleExpenseDetails.setExpenseAmount(vehicleProfitAndLossDto.getTxnAmount());
			vehicleExpenseDetails.setExpenseType(vehicleProfitAndLossDto.getType());
			vehicleExpenseDetails.setExpenseDate(vehicleProfitAndLossDto.getTransactionDateTime());
			vehicleExpenseDetails.setTxnTypeId(vehicleProfitAndLossDto.getTxnTypeId());
			vehicleExpenseDetails.setExpenseId(Integer.parseInt(vehicleProfitAndLossDto.getExpenseId()+""));
			vehicleExpenseDetails.setCompanyId(vehicleProfitAndLossDto.getCompanyId());
			vehicleExpenseDetails.setCreatedById(vehicleProfitAndLossDto.getCreatedById());
			vehicleExpenseDetails.setLastUpdatedById(vehicleProfitAndLossDto.getLastUpdatedById());
			vehicleExpenseDetails.setCratedOn(vehicleProfitAndLossDto.getCratedOn());
			vehicleExpenseDetails.setLastUpdatedOn(vehicleProfitAndLossDto.getLastUpdatedOn());
			vehicleExpenseDetails.setTripExpenseId(vehicleProfitAndLossDto.getTripExpenseId());
			
			return vehicleExpenseDetails;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleExpenseDetails	= null;
		}
	}
	
	public DateWiseVehicleExpense createDateWiseVehicleExpense(VehicleProfitAndLossDto	vehicleProfitAndLossDto,  DateWiseVehicleExpense	preDetails) throws Exception{
		DateWiseVehicleExpense		dateWiseVehicleExpense	= null;
		try {
			dateWiseVehicleExpense	= new DateWiseVehicleExpense();
			
			dateWiseVehicleExpense.setVid(vehicleProfitAndLossDto.getVid());
			if(preDetails != null && preDetails.getExpenseAmount() != 0.0 && vehicleProfitAndLossDto.getPreviousDate() != null){
				
				
				if(vehicleProfitAndLossDto.isEdit() && vehicleProfitAndLossDto.getPreviousDate().equals(vehicleProfitAndLossDto.getTransactionDateTime())) {
					dateWiseVehicleExpense.setExpenseAmount(vehicleProfitAndLossDto.getAmountToBeAdded() + preDetails.getExpenseAmount());
				}else if(vehicleProfitAndLossDto.isEdit()){
					vehicleProfitAndLossDto.setAmountToBeAdded(vehicleProfitAndLossDto.getTxnAmount()-vehicleProfitAndLossDto.getPreviousDownPaymentAmount());
					dateWiseVehicleExpense.setExpenseAmount(vehicleProfitAndLossDto.getAmountToBeAdded() + preDetails.getExpenseAmount());
				}else {
					dateWiseVehicleExpense.setExpenseAmount(vehicleProfitAndLossDto.getTxnAmount() + preDetails.getExpenseAmount());
					
				}
			}else {
				dateWiseVehicleExpense.setExpenseAmount(vehicleProfitAndLossDto.getTxnAmount());
			}
			
			dateWiseVehicleExpense.setExpenseType(vehicleProfitAndLossDto.getType());
			dateWiseVehicleExpense.setExpenseId(Integer.parseInt(vehicleProfitAndLossDto.getExpenseId()+""));
			dateWiseVehicleExpense.setExpenseDate(vehicleProfitAndLossDto.getTransactionDateTime());
			dateWiseVehicleExpense.setTxnTypeId(vehicleProfitAndLossDto.getTxnTypeId());
			dateWiseVehicleExpense.setCompanyId(vehicleProfitAndLossDto.getCompanyId());
			
			return dateWiseVehicleExpense;
		} catch (Exception e) {
			System.err.println("Exception in createDateWiseVehicleExpense !");
			throw e;
		}finally {
			dateWiseVehicleExpense	= null;
		}
	}
	
	public MonthWiseVehicleExpense createMonthWiseVehicleExpense(VehicleProfitAndLossDto	profitAndLossDto, Calendar	calendar, MonthWiseVehicleExpense validate) throws Exception{
		MonthWiseVehicleExpense		monthWiseVehicleExpense	= null;
		try {
			monthWiseVehicleExpense	= new MonthWiseVehicleExpense();
			
			monthWiseVehicleExpense.setVid(profitAndLossDto.getVid());
			if(validate != null) {
				
				
				if(profitAndLossDto.getPreviousDate() != null && profitAndLossDto.isEdit() && DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getPreviousDate()).equals(DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getTransactionDateTime()))) {
					monthWiseVehicleExpense.setExpenseAmount(profitAndLossDto.getAmountToBeAdded() + validate.getExpenseAmount());
				}else if(profitAndLossDto.isEdit()){
					
					profitAndLossDto.setAmountToBeAdded(profitAndLossDto.getTxnAmount()-profitAndLossDto.getPreviousDownPaymentAmount());
					
					monthWiseVehicleExpense.setExpenseAmount(profitAndLossDto.getAmountToBeAdded() + validate.getExpenseAmount());
					
				} else {
							monthWiseVehicleExpense.setExpenseAmount(profitAndLossDto.getTxnAmount() + validate.getExpenseAmount());
					
				}
				
			}else {
				monthWiseVehicleExpense.setExpenseAmount(profitAndLossDto.getTxnAmount());
			}
			
			monthWiseVehicleExpense.setExpenseType(profitAndLossDto.getType());
			monthWiseVehicleExpense.setTxnTypeId(profitAndLossDto.getTxnTypeId());
			monthWiseVehicleExpense.setExpenseId(Integer.parseInt(profitAndLossDto.getExpenseId()+""));
			monthWiseVehicleExpense.setCompanyId(profitAndLossDto.getCompanyId());
			/**
			 * we are adding 1 because calendar month start from 0 to 11
			 */
			//monthWiseVehicleExpense.setExpenseMonth(calendar.get(Calendar.MONTH) + 1);
			//monthWiseVehicleExpense.setExpenseYear(calendar.get(Calendar.YEAR));
			monthWiseVehicleExpense.setStartDateOfMonth(DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getTransactionDateTime()));
			
			return monthWiseVehicleExpense;
		} catch (Exception e) {
			throw e;
		}finally {
			monthWiseVehicleExpense	= null;
		}
	}
	
	public DateWiseVehicleBalance createDateWiseVehicleBalance(VehicleProfitAndLossDto	profitAndLossDto, DateWiseVehicleBalance	currentDay, DateWiseVehicleBalance lastDate) throws Exception{
		DateWiseVehicleBalance		dateWiseVehicleBalance	= null;
		try {
			dateWiseVehicleBalance	= new DateWiseVehicleBalance();
			
			if(!profitAndLossDto.isEdit()) {
				if(currentDay != null && lastDate != null) {
					dateWiseVehicleBalance.setTotalExpenseAmount(currentDay.getTotalExpenseAmount() + profitAndLossDto.getTxnAmount());
					dateWiseVehicleBalance.setTotalIncomeAmount(currentDay.getTotalIncomeAmount());
					dateWiseVehicleBalance.setDayBalanceAmount(dateWiseVehicleBalance.getTotalIncomeAmount() - dateWiseVehicleBalance.getTotalExpenseAmount());
					dateWiseVehicleBalance.setAllDayBalanceAmount(lastDate.getAllDayBalanceAmount() + dateWiseVehicleBalance.getDayBalanceAmount());
				}
				if(currentDay != null && lastDate == null) {
					dateWiseVehicleBalance.setTotalExpenseAmount(currentDay.getTotalExpenseAmount() + profitAndLossDto.getTxnAmount());
					dateWiseVehicleBalance.setTotalIncomeAmount(currentDay.getTotalIncomeAmount());
					dateWiseVehicleBalance.setDayBalanceAmount(dateWiseVehicleBalance.getTotalIncomeAmount() - dateWiseVehicleBalance.getTotalExpenseAmount());
					dateWiseVehicleBalance.setAllDayBalanceAmount(dateWiseVehicleBalance.getDayBalanceAmount());
				}
				if(currentDay == null && lastDate != null) {
					dateWiseVehicleBalance.setTotalExpenseAmount(profitAndLossDto.getTxnAmount());
					dateWiseVehicleBalance.setTotalIncomeAmount((double) 0);
					dateWiseVehicleBalance.setDayBalanceAmount(dateWiseVehicleBalance.getTotalIncomeAmount() - dateWiseVehicleBalance.getTotalExpenseAmount());
					dateWiseVehicleBalance.setAllDayBalanceAmount(lastDate.getAllDayBalanceAmount() + dateWiseVehicleBalance.getDayBalanceAmount());
				}
				if(currentDay == null && lastDate == null) {
					dateWiseVehicleBalance.setTotalExpenseAmount(profitAndLossDto.getTxnAmount());
					dateWiseVehicleBalance.setTotalIncomeAmount((double) 0);
					dateWiseVehicleBalance.setDayBalanceAmount(dateWiseVehicleBalance.getTotalIncomeAmount() - dateWiseVehicleBalance.getTotalExpenseAmount());
					dateWiseVehicleBalance.setAllDayBalanceAmount(dateWiseVehicleBalance.getDayBalanceAmount());
				}
			}else {
				if(profitAndLossDto.getPreviousDate().equals(profitAndLossDto.getTransactionDateTime())) {
					if(currentDay != null && lastDate != null) {
						dateWiseVehicleBalance.setTotalExpenseAmount(currentDay.getTotalExpenseAmount() + profitAndLossDto.getAmountToBeAdded());
						dateWiseVehicleBalance.setTotalIncomeAmount(currentDay.getTotalIncomeAmount());
						dateWiseVehicleBalance.setDayBalanceAmount(dateWiseVehicleBalance.getTotalIncomeAmount() - dateWiseVehicleBalance.getTotalExpenseAmount());
						dateWiseVehicleBalance.setAllDayBalanceAmount(lastDate.getAllDayBalanceAmount() + dateWiseVehicleBalance.getDayBalanceAmount());
					}
					if(currentDay != null && lastDate == null) {
						dateWiseVehicleBalance.setTotalExpenseAmount(currentDay.getTotalExpenseAmount() + profitAndLossDto.getAmountToBeAdded());
						dateWiseVehicleBalance.setTotalIncomeAmount(currentDay.getTotalIncomeAmount());
						dateWiseVehicleBalance.setDayBalanceAmount(dateWiseVehicleBalance.getTotalIncomeAmount() - dateWiseVehicleBalance.getTotalExpenseAmount());
						dateWiseVehicleBalance.setAllDayBalanceAmount(dateWiseVehicleBalance.getDayBalanceAmount());
					}
					if(currentDay == null && lastDate != null) {
						dateWiseVehicleBalance.setTotalExpenseAmount(profitAndLossDto.getAmountToBeAdded());
						dateWiseVehicleBalance.setTotalIncomeAmount((double) 0);
						dateWiseVehicleBalance.setDayBalanceAmount(dateWiseVehicleBalance.getTotalIncomeAmount() - dateWiseVehicleBalance.getTotalExpenseAmount());
						dateWiseVehicleBalance.setAllDayBalanceAmount(lastDate.getAllDayBalanceAmount() + dateWiseVehicleBalance.getDayBalanceAmount());
					}
					if(currentDay == null && lastDate == null) {
						dateWiseVehicleBalance.setTotalExpenseAmount(profitAndLossDto.getAmountToBeAdded());
						dateWiseVehicleBalance.setTotalIncomeAmount((double) 0);
						dateWiseVehicleBalance.setDayBalanceAmount(dateWiseVehicleBalance.getTotalIncomeAmount() - dateWiseVehicleBalance.getTotalExpenseAmount());
						dateWiseVehicleBalance.setAllDayBalanceAmount(dateWiseVehicleBalance.getDayBalanceAmount());
					}
				}else {

					if(currentDay != null && lastDate != null) {
						dateWiseVehicleBalance.setTotalExpenseAmount(currentDay.getTotalExpenseAmount() + profitAndLossDto.getTxnAmount());
						dateWiseVehicleBalance.setTotalIncomeAmount(currentDay.getTotalIncomeAmount());
						dateWiseVehicleBalance.setDayBalanceAmount(dateWiseVehicleBalance.getTotalIncomeAmount() - dateWiseVehicleBalance.getTotalExpenseAmount());
						dateWiseVehicleBalance.setAllDayBalanceAmount(lastDate.getAllDayBalanceAmount() + dateWiseVehicleBalance.getDayBalanceAmount());
					}
					if(currentDay != null && lastDate == null) {
						dateWiseVehicleBalance.setTotalExpenseAmount(currentDay.getTotalExpenseAmount() + profitAndLossDto.getTxnAmount());
						dateWiseVehicleBalance.setTotalIncomeAmount(currentDay.getTotalIncomeAmount());
						dateWiseVehicleBalance.setDayBalanceAmount(dateWiseVehicleBalance.getTotalIncomeAmount() - dateWiseVehicleBalance.getTotalExpenseAmount());
						dateWiseVehicleBalance.setAllDayBalanceAmount(dateWiseVehicleBalance.getDayBalanceAmount());
					}
					if(currentDay == null && lastDate != null) {
						dateWiseVehicleBalance.setTotalExpenseAmount(profitAndLossDto.getTxnAmount());
						dateWiseVehicleBalance.setTotalIncomeAmount((double) 0);
						dateWiseVehicleBalance.setDayBalanceAmount(dateWiseVehicleBalance.getTotalIncomeAmount() - dateWiseVehicleBalance.getTotalExpenseAmount());
						dateWiseVehicleBalance.setAllDayBalanceAmount(lastDate.getAllDayBalanceAmount() + dateWiseVehicleBalance.getDayBalanceAmount());
					}
					if(currentDay == null && lastDate == null) {
						dateWiseVehicleBalance.setTotalExpenseAmount(profitAndLossDto.getTxnAmount());
						dateWiseVehicleBalance.setTotalIncomeAmount((double) 0);
						dateWiseVehicleBalance.setDayBalanceAmount(dateWiseVehicleBalance.getTotalIncomeAmount() - dateWiseVehicleBalance.getTotalExpenseAmount());
						dateWiseVehicleBalance.setAllDayBalanceAmount(dateWiseVehicleBalance.getDayBalanceAmount());
					}
				
				}
				
			
			}
			
			
			
			dateWiseVehicleBalance.setDayBalanceAmount(dateWiseVehicleBalance.getTotalIncomeAmount() - dateWiseVehicleBalance.getTotalExpenseAmount());
			dateWiseVehicleBalance.setVid(profitAndLossDto.getVid());
			
			dateWiseVehicleBalance.setTransactionDate(profitAndLossDto.getTransactionDateTime());
			dateWiseVehicleBalance.setSystemDateAndTime(profitAndLossDto.getSystemDateTime());
			dateWiseVehicleBalance.setCompanyId(profitAndLossDto.getCompanyId());
			return dateWiseVehicleBalance;
		} catch (Exception e) {
			throw e;
		}finally {
			dateWiseVehicleBalance	= null;
		}
	}
	
	
	public MonthWiseVehicleBalance createMonthWiseVehicleBalance(VehicleProfitAndLossDto	profitAndLossDto, MonthWiseVehicleBalance	currentMonth, MonthWiseVehicleBalance lastMonth, Calendar	calendar, Timestamp previousDateMonthFirstDate) throws Exception{
		MonthWiseVehicleBalance		monthWiseVehicleBalance	= null;
		try {
			monthWiseVehicleBalance	= new MonthWiseVehicleBalance();
			
			monthWiseVehicleBalance.setVid(profitAndLossDto.getVid());
			//monthWiseVehicleBalance.setExpenseMonth(calendar.get(Calendar.MONTH) + 1);
			//monthWiseVehicleBalance.setExpenseYear(calendar.get(Calendar.YEAR));
			monthWiseVehicleBalance.setCompanyId(profitAndLossDto.getCompanyId());
			monthWiseVehicleBalance.setMonthStartDate(DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getTransactionDateTime()));
			
			if(!profitAndLossDto.isEdit()) {
				if(currentMonth != null && lastMonth != null) {
					monthWiseVehicleBalance.setTotalExpenseAmount(currentMonth.getTotalExpenseAmount() + profitAndLossDto.getTxnAmount());
					monthWiseVehicleBalance.setTotalIncomeAmount(currentMonth.getTotalIncomeAmount());
					monthWiseVehicleBalance.setBalanceAmount(monthWiseVehicleBalance.getTotalIncomeAmount() - monthWiseVehicleBalance.getTotalExpenseAmount());
					monthWiseVehicleBalance.setMonthBalance(monthWiseVehicleBalance.getBalanceAmount() + lastMonth.getMonthBalance());
					
					monthWiseVehicleBalance.setTotalIncomeAmount(currentMonth.getTotalIncomeAmount());
					monthWiseVehicleBalance.setToatlDiesel(currentMonth.getToatlDiesel());
					monthWiseVehicleBalance.setToatlDieselAmount(currentMonth.getToatlDieselAmount());
					monthWiseVehicleBalance.setTotalNoOfKMPL(currentMonth.getTotalNoOfKMPL());
					monthWiseVehicleBalance.setTotalRfid(currentMonth.getTotalRfid());
					monthWiseVehicleBalance.setTotalRfidAmount(currentMonth.getTotalRfidAmount());
					monthWiseVehicleBalance.setTotalCollection(currentMonth.getTotalCollection());
				}
				if(currentMonth != null && lastMonth == null) {
					monthWiseVehicleBalance.setTotalExpenseAmount(currentMonth.getTotalExpenseAmount() + profitAndLossDto.getTxnAmount());
					monthWiseVehicleBalance.setTotalIncomeAmount(currentMonth.getTotalIncomeAmount());
					monthWiseVehicleBalance.setBalanceAmount(monthWiseVehicleBalance.getTotalIncomeAmount() - monthWiseVehicleBalance.getTotalExpenseAmount());
					monthWiseVehicleBalance.setMonthBalance(monthWiseVehicleBalance.getBalanceAmount());
					
					monthWiseVehicleBalance.setTotalIncomeAmount(currentMonth.getTotalIncomeAmount());
					monthWiseVehicleBalance.setToatlDiesel(currentMonth.getToatlDiesel());
					monthWiseVehicleBalance.setToatlDieselAmount(currentMonth.getToatlDieselAmount());
					monthWiseVehicleBalance.setTotalNoOfKMPL(currentMonth.getTotalNoOfKMPL());
					monthWiseVehicleBalance.setTotalRfid(currentMonth.getTotalRfid());
					monthWiseVehicleBalance.setTotalRfidAmount(currentMonth.getTotalRfidAmount());
					monthWiseVehicleBalance.setTotalCollection(currentMonth.getTotalCollection());
				}if(currentMonth == null && lastMonth != null) {
					monthWiseVehicleBalance.setTotalExpenseAmount(profitAndLossDto.getTxnAmount());
					monthWiseVehicleBalance.setTotalIncomeAmount((double) 0);
					monthWiseVehicleBalance.setBalanceAmount(monthWiseVehicleBalance.getTotalIncomeAmount() - monthWiseVehicleBalance.getTotalExpenseAmount());
					monthWiseVehicleBalance.setMonthBalance(monthWiseVehicleBalance.getBalanceAmount() + lastMonth.getMonthBalance());
				}
				if(currentMonth == null && lastMonth == null) {
					monthWiseVehicleBalance.setTotalExpenseAmount(profitAndLossDto.getTxnAmount());
					monthWiseVehicleBalance.setTotalIncomeAmount((double) 0);
					monthWiseVehicleBalance.setBalanceAmount(monthWiseVehicleBalance.getTotalIncomeAmount() - monthWiseVehicleBalance.getTotalExpenseAmount());
					monthWiseVehicleBalance.setMonthBalance(monthWiseVehicleBalance.getBalanceAmount());
				}
			}else {
				if(previousDateMonthFirstDate.equals(monthWiseVehicleBalance.getMonthStartDate())) {

					if(currentMonth != null && lastMonth != null) {
						monthWiseVehicleBalance.setTotalExpenseAmount(currentMonth.getTotalExpenseAmount() + profitAndLossDto.getAmountToBeAdded());
						monthWiseVehicleBalance.setTotalIncomeAmount(currentMonth.getTotalIncomeAmount());
						monthWiseVehicleBalance.setBalanceAmount(monthWiseVehicleBalance.getTotalIncomeAmount() - monthWiseVehicleBalance.getTotalExpenseAmount());
						monthWiseVehicleBalance.setMonthBalance(monthWiseVehicleBalance.getBalanceAmount() + lastMonth.getMonthBalance());
						
						monthWiseVehicleBalance.setTotalIncomeAmount(currentMonth.getTotalIncomeAmount());
						monthWiseVehicleBalance.setToatlDiesel(currentMonth.getToatlDiesel());
						monthWiseVehicleBalance.setToatlDieselAmount(currentMonth.getToatlDieselAmount());
						monthWiseVehicleBalance.setTotalNoOfKMPL(currentMonth.getTotalNoOfKMPL());
						monthWiseVehicleBalance.setTotalRfid(currentMonth.getTotalRfid());
						monthWiseVehicleBalance.setTotalRfidAmount(currentMonth.getTotalRfidAmount());
						monthWiseVehicleBalance.setTotalCollection(currentMonth.getTotalCollection());
						
					}
					if(currentMonth != null && lastMonth == null) {
						monthWiseVehicleBalance.setTotalExpenseAmount(currentMonth.getTotalExpenseAmount() + profitAndLossDto.getAmountToBeAdded());
						monthWiseVehicleBalance.setTotalIncomeAmount(currentMonth.getTotalIncomeAmount());
						monthWiseVehicleBalance.setBalanceAmount(monthWiseVehicleBalance.getTotalIncomeAmount() - monthWiseVehicleBalance.getTotalExpenseAmount());
						monthWiseVehicleBalance.setMonthBalance(monthWiseVehicleBalance.getBalanceAmount());
						
						monthWiseVehicleBalance.setTotalIncomeAmount(currentMonth.getTotalIncomeAmount());
						monthWiseVehicleBalance.setToatlDiesel(currentMonth.getToatlDiesel());
						monthWiseVehicleBalance.setToatlDieselAmount(currentMonth.getToatlDieselAmount());
						monthWiseVehicleBalance.setTotalNoOfKMPL(currentMonth.getTotalNoOfKMPL());
						monthWiseVehicleBalance.setTotalRfid(currentMonth.getTotalRfid());
						monthWiseVehicleBalance.setTotalRfidAmount(currentMonth.getTotalRfidAmount());
						monthWiseVehicleBalance.setTotalCollection(currentMonth.getTotalCollection());
						
					}if(currentMonth == null && lastMonth != null) {
						monthWiseVehicleBalance.setTotalExpenseAmount(profitAndLossDto.getAmountToBeAdded());
						monthWiseVehicleBalance.setTotalIncomeAmount((double) 0);
						monthWiseVehicleBalance.setBalanceAmount(monthWiseVehicleBalance.getTotalIncomeAmount() - monthWiseVehicleBalance.getTotalExpenseAmount());
						monthWiseVehicleBalance.setMonthBalance(monthWiseVehicleBalance.getBalanceAmount() + lastMonth.getMonthBalance());
					}
					if(currentMonth == null && lastMonth == null) {
						monthWiseVehicleBalance.setTotalExpenseAmount(profitAndLossDto.getAmountToBeAdded());
						monthWiseVehicleBalance.setTotalIncomeAmount((double) 0);
						monthWiseVehicleBalance.setBalanceAmount(monthWiseVehicleBalance.getTotalIncomeAmount() - monthWiseVehicleBalance.getTotalExpenseAmount());
						monthWiseVehicleBalance.setMonthBalance(monthWiseVehicleBalance.getBalanceAmount());
					}
				
				}else {

					if(currentMonth != null && lastMonth != null) {
						monthWiseVehicleBalance.setTotalExpenseAmount(currentMonth.getTotalExpenseAmount() + profitAndLossDto.getAmountToBeAdded());
						monthWiseVehicleBalance.setTotalIncomeAmount(currentMonth.getTotalIncomeAmount());
						monthWiseVehicleBalance.setBalanceAmount(monthWiseVehicleBalance.getTotalIncomeAmount() - monthWiseVehicleBalance.getTotalExpenseAmount());
						monthWiseVehicleBalance.setMonthBalance(monthWiseVehicleBalance.getBalanceAmount() + lastMonth.getMonthBalance());
						
						monthWiseVehicleBalance.setTotalIncomeAmount(currentMonth.getTotalIncomeAmount());
						monthWiseVehicleBalance.setToatlDiesel(currentMonth.getToatlDiesel());
						monthWiseVehicleBalance.setToatlDieselAmount(currentMonth.getToatlDieselAmount());
						monthWiseVehicleBalance.setTotalNoOfKMPL(currentMonth.getTotalNoOfKMPL());
						monthWiseVehicleBalance.setTotalRfid(currentMonth.getTotalRfid());
						monthWiseVehicleBalance.setTotalRfidAmount(currentMonth.getTotalRfidAmount());
						monthWiseVehicleBalance.setTotalCollection(currentMonth.getTotalCollection());
						
					}
					if(currentMonth != null && lastMonth == null) {
						monthWiseVehicleBalance.setTotalExpenseAmount(currentMonth.getTotalExpenseAmount() + profitAndLossDto.getAmountToBeAdded());
						monthWiseVehicleBalance.setTotalIncomeAmount(currentMonth.getTotalIncomeAmount());
						monthWiseVehicleBalance.setBalanceAmount(monthWiseVehicleBalance.getTotalIncomeAmount() - monthWiseVehicleBalance.getTotalExpenseAmount());
						monthWiseVehicleBalance.setMonthBalance(monthWiseVehicleBalance.getBalanceAmount());
						
						monthWiseVehicleBalance.setTotalIncomeAmount(currentMonth.getTotalIncomeAmount());
						monthWiseVehicleBalance.setToatlDiesel(currentMonth.getToatlDiesel());
						monthWiseVehicleBalance.setToatlDieselAmount(currentMonth.getToatlDieselAmount());
						monthWiseVehicleBalance.setTotalNoOfKMPL(currentMonth.getTotalNoOfKMPL());
						monthWiseVehicleBalance.setTotalRfid(currentMonth.getTotalRfid());
						monthWiseVehicleBalance.setTotalRfidAmount(currentMonth.getTotalRfidAmount());
						monthWiseVehicleBalance.setTotalCollection(currentMonth.getTotalCollection());
						
					}if(currentMonth == null && lastMonth != null) {
						monthWiseVehicleBalance.setTotalExpenseAmount(profitAndLossDto.getTxnAmount());
						monthWiseVehicleBalance.setTotalIncomeAmount((double) 0);
						monthWiseVehicleBalance.setBalanceAmount(monthWiseVehicleBalance.getTotalIncomeAmount() - monthWiseVehicleBalance.getTotalExpenseAmount());
						monthWiseVehicleBalance.setMonthBalance(monthWiseVehicleBalance.getBalanceAmount() + lastMonth.getMonthBalance());
					}
					if(currentMonth == null && lastMonth == null) {
						monthWiseVehicleBalance.setTotalExpenseAmount(profitAndLossDto.getTxnAmount());
						monthWiseVehicleBalance.setTotalIncomeAmount((double) 0);
						monthWiseVehicleBalance.setBalanceAmount(monthWiseVehicleBalance.getTotalIncomeAmount() - monthWiseVehicleBalance.getTotalExpenseAmount());
						monthWiseVehicleBalance.setMonthBalance(monthWiseVehicleBalance.getBalanceAmount());
					}
				
				}
			}
			
			/*if(currentMonth != null){
				if(isFuelEdit) {
					monthWiseVehicleBalance.setTotalExpenseAmount(fuelAmountToBeAdded + currentMonth.getTotalExpenseAmount());
				}else {
					monthWiseVehicleBalance.setTotalExpenseAmount(fuel.getFuel_amount() + currentMonth.getTotalExpenseAmount());	
				}
				
				monthWiseVehicleBalance.setTotalIncomeAmount(currentMonth.getTotalIncomeAmount());
				monthWiseVehicleBalance.setToatlDiesel(currentMonth.getToatlDiesel());
				monthWiseVehicleBalance.setToatlDieselAmount(currentMonth.getToatlDieselAmount());
				monthWiseVehicleBalance.setTotalNoOfKMPL(currentMonth.getTotalNoOfKMPL());
				monthWiseVehicleBalance.setTotalRfid(currentMonth.getTotalRfid());
				monthWiseVehicleBalance.setTotalRfidAmount(currentMonth.getTotalRfidAmount());
				monthWiseVehicleBalance.setTotalCollection(currentMonth.getTotalCollection());
				
			}else {
				monthWiseVehicleBalance.setTotalExpenseAmount(fuel.getFuel_amount());
				monthWiseVehicleBalance.setTotalIncomeAmount((double) 0);
				monthWiseVehicleBalance.setToatlDiesel((double) 0);
				monthWiseVehicleBalance.setToatlDieselAmount((double) 0);
				monthWiseVehicleBalance.setTotalNoOfKMPL(0);
				monthWiseVehicleBalance.setTotalRfid(0);
				monthWiseVehicleBalance.setTotalRfidAmount((double) 0);
				monthWiseVehicleBalance.setTotalCollection((double) 0);
				
			}
			if(lastMonth != null) {
				lastDayBalance	= lastMonth.getMonthBalance();
			}*/
			//monthWiseVehicleBalance.setBalanceAmount(monthWiseVehicleBalance.getTotalIncomeAmount() - monthWiseVehicleBalance.getTotalExpenseAmount());
			//monthWiseVehicleBalance.setMonthBalance(lastDayBalance + monthWiseVehicleBalance.getBalanceAmount());
			
			
			return monthWiseVehicleBalance;
		} catch (Exception e) {
			throw e;
		}finally {
			monthWiseVehicleBalance	= null;
		}
	}
	
	public VehicleIncomeDetails createDataForVehicleIncomeDetails(VehicleProfitAndLossDto	vehicleProfitAndLossDto) throws Exception{
		VehicleIncomeDetails		vehicleIncomeDetails	= null;
		try {
			vehicleIncomeDetails	= new VehicleIncomeDetails();
			
			vehicleIncomeDetails.setVid(vehicleProfitAndLossDto.getVid());
			vehicleIncomeDetails.setIncomeAmount(vehicleProfitAndLossDto.getTxnAmount());
			vehicleIncomeDetails.setIncomeType(vehicleProfitAndLossDto.getType());
			vehicleIncomeDetails.setIncomeDate(vehicleProfitAndLossDto.getTransactionDateTime());
			vehicleIncomeDetails.setTxnTypeId(vehicleProfitAndLossDto.getTxnTypeId());
			vehicleIncomeDetails.setIncomeId(Integer.parseInt(vehicleProfitAndLossDto.getIncomeId()+""));
			vehicleIncomeDetails.setCompanyId(vehicleProfitAndLossDto.getCompanyId());
			vehicleIncomeDetails.setCreatedById(vehicleProfitAndLossDto.getCreatedById());
			vehicleIncomeDetails.setLastUpdatedById(vehicleProfitAndLossDto.getLastUpdatedById());
			vehicleIncomeDetails.setCratedOn(vehicleProfitAndLossDto.getCratedOn());
			vehicleIncomeDetails.setLastUpdatedOn(vehicleProfitAndLossDto.getLastUpdatedOn());
			vehicleIncomeDetails.setTripincomeId(vehicleProfitAndLossDto.getTripIncomeId());
			
			return vehicleIncomeDetails;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleIncomeDetails	= null;
		}
	}
	
	public MonthWiseVehicleIncome createMonthWiseVehicleIncome(VehicleProfitAndLossDto	profitAndLossDto, Calendar	calendar, MonthWiseVehicleIncome validate) throws Exception{
		MonthWiseVehicleIncome		monthWiseVehicleIncome	= null;
		try {
			monthWiseVehicleIncome	= new MonthWiseVehicleIncome();
			
			monthWiseVehicleIncome.setVid(profitAndLossDto.getVid());
			if(validate != null) {
				if(profitAndLossDto.isEdit() && DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getPreviousDate()).equals(DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getTransactionDateTime()))) {
					monthWiseVehicleIncome.setIncomeAmount(profitAndLossDto.getAmountToBeAdded() + validate.getIncomeAmount());
				}else {
					monthWiseVehicleIncome.setIncomeAmount(profitAndLossDto.getTxnAmount() + validate.getIncomeAmount());
				}
				
			}else {
				monthWiseVehicleIncome.setIncomeAmount(profitAndLossDto.getTxnAmount());
			}
			
			monthWiseVehicleIncome.setIncomeType(profitAndLossDto.getType());
			monthWiseVehicleIncome.setTxnTypeId(profitAndLossDto.getTxnTypeId());
			monthWiseVehicleIncome.setIncomeId(Integer.parseInt(profitAndLossDto.getIncomeId()+""));
			monthWiseVehicleIncome.setCompanyId(profitAndLossDto.getCompanyId());
			/**
			 * we are adding 1 because calendar month start from 0 to 11
			 */
			//monthWiseVehicleIncome.setIncomeMonth(calendar.get(Calendar.MONTH) + 1);
			//monthWiseVehicleIncome.setIncomeYear(calendar.get(Calendar.YEAR));
			monthWiseVehicleIncome.setStartDateOfMonth(DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getTransactionDateTime()));
			
			return monthWiseVehicleIncome;
		} catch (Exception e) {
			throw e;
		}finally {
			monthWiseVehicleIncome	= null;
		}
	}
	
	public MothWiseVehicleIncomeBalance creatMothWiseVehicleIncomeBalance(VehicleProfitAndLossDto	profitAndLossDto, MothWiseVehicleIncomeBalance previous, MothWiseVehicleIncomeBalance current) throws Exception{
		MothWiseVehicleIncomeBalance		mothWiseVehicleIncomeBalance	= null;
		try {
			mothWiseVehicleIncomeBalance	= new MothWiseVehicleIncomeBalance();
			
			mothWiseVehicleIncomeBalance.setVid(profitAndLossDto.getVid());
			mothWiseVehicleIncomeBalance.setStartDateOfMonth(DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getTransactionDateTime()));
			mothWiseVehicleIncomeBalance.setCompanyId(profitAndLossDto.getCompanyId());
			
			if(previous != null && current != null) {
				mothWiseVehicleIncomeBalance.setTotalIncome(current.getTotalIncome() + profitAndLossDto.getTxnAmount());
				mothWiseVehicleIncomeBalance.setTotalBalanceIncome(previous.getTotalBalanceIncome() + mothWiseVehicleIncomeBalance.getTotalIncome());
			}
			if(previous == null && current != null) {
				mothWiseVehicleIncomeBalance.setTotalIncome(current.getTotalIncome() + profitAndLossDto.getTxnAmount());
				mothWiseVehicleIncomeBalance.setTotalBalanceIncome(mothWiseVehicleIncomeBalance.getTotalIncome());
			}
			if(previous != null && current == null) {
				mothWiseVehicleIncomeBalance.setTotalIncome(profitAndLossDto.getTxnAmount());
				mothWiseVehicleIncomeBalance.setTotalBalanceIncome(previous.getTotalBalanceIncome() + mothWiseVehicleIncomeBalance.getTotalIncome());
			}
			if(previous == null && current == null) {
				mothWiseVehicleIncomeBalance.setTotalIncome(profitAndLossDto.getTxnAmount());
				mothWiseVehicleIncomeBalance.setTotalBalanceIncome(mothWiseVehicleIncomeBalance.getTotalIncome());
			}
			
			return mothWiseVehicleIncomeBalance;
		} catch (Exception e) {
			throw e;
		}finally {
			mothWiseVehicleIncomeBalance	= null;
		}
	}
}
