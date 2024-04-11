package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.util.Date;

import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.ServiceEntriesDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.model.Fuel;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.UreaEntries;
import org.fleetopgroup.persistence.model.VehicleEmiDetails;
import org.fleetopgroup.persistence.model.VehicleEmiPaymentDetails;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public class DataCreatorForVehicleProfitAndLoss {
	
	public static VehicleProfitAndLossDto prepareVehicleProfitAndLossDtoForFuel(ValueObject	valueObject) throws Exception{
		VehicleProfitAndLossDto		vehicleProfitAndLossDto		= null;
		Fuel						fuel						= null;
		CustomUserDetails			userDetails					= null;
		Long						fuelEntryTxnCheckerId		= null;
		boolean						isFuelEdit					= false;
		Double						fuelAmountToBeAdded			= 0.0;
		boolean						isFuelDateChanged			= false;
		Timestamp					previousFuelDate			= null;
		Double						previousFuelAmount			= null;
		try {
			fuel					= (Fuel) valueObject.get("fuel");
			userDetails				= (CustomUserDetails) valueObject.get("userDetails");
			fuelEntryTxnCheckerId	= valueObject.getLong(VehicleProfitAndLossDto.TXN_CHECKER_ID, 0);
			
			isFuelEdit				= valueObject.getBoolean("isFuelEdit", false);
			fuelAmountToBeAdded		= valueObject.getDouble("fuelAmountToBeAdded", 0);
			isFuelDateChanged		= valueObject.getBoolean("isFuelDateChanged", false);
			previousFuelDate		= (Timestamp) valueObject.get("previousFuelDate");
			previousFuelAmount		= valueObject.getDouble("previousFuelAmount", 0);
			
			
			if(fuel != null) {
				vehicleProfitAndLossDto	= new VehicleProfitAndLossDto();
				
				vehicleProfitAndLossDto.setVid(fuel.getVid());
				vehicleProfitAndLossDto.setCompanyId(userDetails.getCompany_id());
				vehicleProfitAndLossDto.setTxnTypeId(fuel.getFuel_id());
				vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(fuel.getFuel_date()));
				vehicleProfitAndLossDto.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnAmount(fuel.getFuel_amount());
				vehicleProfitAndLossDto.setTxnType(VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				vehicleProfitAndLossDto.setType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
				vehicleProfitAndLossDto.setCreatedById(userDetails.getId());
				vehicleProfitAndLossDto.setLastUpdatedById(userDetails.getId());
				vehicleProfitAndLossDto.setCratedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnCheckerId(fuelEntryTxnCheckerId);
				vehicleProfitAndLossDto.setEdit(isFuelEdit);
				vehicleProfitAndLossDto.setAmountToBeAdded(fuelAmountToBeAdded);
				vehicleProfitAndLossDto.setDateChanged(isFuelDateChanged);
				vehicleProfitAndLossDto.setPreviousDate(previousFuelDate);
				vehicleProfitAndLossDto.setPreviousAmount(previousFuelAmount);
				vehicleProfitAndLossDto.setExpenseId(fuel.getFuel_id());
				
			}
			
			return vehicleProfitAndLossDto;
			
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleProfitAndLossDto		= null;
			fuel						= null;
			userDetails					= null;
			fuelEntryTxnCheckerId		= null;
			isFuelEdit					= false;
			fuelAmountToBeAdded			= 0.0;
			isFuelDateChanged			= false;
			previousFuelDate			= null;
			previousFuelAmount			= null;
		}
		
	}
	
	public static VehicleProfitAndLossDto prepareVehicleProfitAndLossDtoForServiceEntries(ValueObject	valueObject) throws Exception{
		VehicleProfitAndLossDto		vehicleProfitAndLossDto		= null;
		ServiceEntriesDto			serviceEntries				= null;
		CustomUserDetails			userDetails					= null;
		Long						serviceEntriesTxnCheckerId	= null;
		boolean						isFuelEdit					= false;
		Double						fuelAmountToBeAdded			= 0.0;
		boolean						isFuelDateChanged			= false;
		Timestamp					previousFuelDate			= null;
		Double						previousFuelAmount			= null;
		try {
			serviceEntries				= (ServiceEntriesDto) valueObject.get("serviceEntries");
			userDetails					= (CustomUserDetails) valueObject.get("userDetails");
			serviceEntriesTxnCheckerId	= valueObject.getLong(VehicleProfitAndLossDto.TXN_CHECKER_ID, 0);
			
			isFuelEdit				= valueObject.getBoolean("isEdit", false);
			fuelAmountToBeAdded		= valueObject.getDouble("amountToBeAdded", 0);
			isFuelDateChanged		= valueObject.getBoolean("isDateChanged", false);
			previousFuelDate		= (Timestamp) valueObject.get("previousDate");
			previousFuelAmount		= valueObject.getDouble("previousAmount", 0);
			
			
			if(serviceEntries != null) {
				vehicleProfitAndLossDto	= new VehicleProfitAndLossDto();
				
				vehicleProfitAndLossDto.setVid(serviceEntries.getVid());
				vehicleProfitAndLossDto.setCompanyId(userDetails.getCompany_id());
				vehicleProfitAndLossDto.setTxnTypeId(serviceEntries.getServiceEntries_id());
				if(serviceEntries.getCompleted_dateOn() != null) {
					vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(serviceEntries.getCompleted_dateOn()));
				}else {
					vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(new Date()));
				}
				
				vehicleProfitAndLossDto.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnAmount(serviceEntries.getTotalservice_cost());
				vehicleProfitAndLossDto.setTxnType(VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				vehicleProfitAndLossDto.setType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
				vehicleProfitAndLossDto.setCreatedById(userDetails.getId());
				vehicleProfitAndLossDto.setLastUpdatedById(userDetails.getId());
				vehicleProfitAndLossDto.setCratedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnCheckerId(serviceEntriesTxnCheckerId);
				vehicleProfitAndLossDto.setEdit(isFuelEdit);
				vehicleProfitAndLossDto.setAmountToBeAdded(fuelAmountToBeAdded);
				vehicleProfitAndLossDto.setDateChanged(isFuelDateChanged);
				vehicleProfitAndLossDto.setPreviousDate(previousFuelDate);
				vehicleProfitAndLossDto.setPreviousAmount(previousFuelAmount);
				vehicleProfitAndLossDto.setExpenseId(serviceEntries.getServiceEntries_id());
				
			}
			
			return vehicleProfitAndLossDto;
			
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleProfitAndLossDto		= null;
			serviceEntries				= null;
			userDetails					= null;
			serviceEntriesTxnCheckerId	= null;
			isFuelEdit					= false;
			fuelAmountToBeAdded			= 0.0;
			isFuelDateChanged			= false;
			previousFuelDate			= null;
			previousFuelAmount			= null;
		}
		
	}
	
	public static VehicleProfitAndLossDto prepareVehicleProfitAndLossDtoForWorkOrder(ValueObject	valueObject) throws Exception{
		VehicleProfitAndLossDto		vehicleProfitAndLossDto		= null;
		WorkOrdersDto				workOrders					= null;
		CustomUserDetails			userDetails					= null;
		Long						serviceEntriesTxnCheckerId	= null;
		boolean						isFuelEdit					= false;
		Double						fuelAmountToBeAdded			= 0.0;
		boolean						isFuelDateChanged			= false;
		Timestamp					previousFuelDate			= null;
		Double						previousFuelAmount			= null;
		try {
			workOrders					= (WorkOrdersDto) valueObject.get("workOrders");
			userDetails					= (CustomUserDetails) valueObject.get("userDetails");
			serviceEntriesTxnCheckerId	= valueObject.getLong(VehicleProfitAndLossDto.TXN_CHECKER_ID, 0);
			
			isFuelEdit				= valueObject.getBoolean("isEdit", false);
			fuelAmountToBeAdded		= valueObject.getDouble("amountToBeAdded", 0);
			isFuelDateChanged		= valueObject.getBoolean("isDateChanged", false);
			previousFuelDate		= (Timestamp) valueObject.get("previousDate");
			previousFuelAmount		= valueObject.getDouble("previousAmount", 0);
			
			
			if(workOrders != null) {
				vehicleProfitAndLossDto	= new VehicleProfitAndLossDto();
				
				vehicleProfitAndLossDto.setVid(workOrders.getVehicle_vid());
				vehicleProfitAndLossDto.setCompanyId(userDetails.getCompany_id());
				vehicleProfitAndLossDto.setTxnTypeId(workOrders.getWorkorders_id());
				if(workOrders.getCompleted_dateOn() != null) {
					vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(workOrders.getCompleted_dateOn()));
				}else {
					vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(new Date()));
				}
				
				vehicleProfitAndLossDto.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnAmount(workOrders.getTotalworkorder_cost());
				vehicleProfitAndLossDto.setTxnType(VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				vehicleProfitAndLossDto.setType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
				vehicleProfitAndLossDto.setCreatedById(userDetails.getId());
				vehicleProfitAndLossDto.setLastUpdatedById(userDetails.getId());
				vehicleProfitAndLossDto.setCratedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnCheckerId(serviceEntriesTxnCheckerId);
				vehicleProfitAndLossDto.setEdit(isFuelEdit);
				vehicleProfitAndLossDto.setAmountToBeAdded(fuelAmountToBeAdded);
				vehicleProfitAndLossDto.setDateChanged(isFuelDateChanged);
				vehicleProfitAndLossDto.setPreviousDate(previousFuelDate);
				vehicleProfitAndLossDto.setPreviousAmount(previousFuelAmount);
				vehicleProfitAndLossDto.setExpenseId(workOrders.getWorkorders_id());
				
			}
			
			return vehicleProfitAndLossDto;
			
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleProfitAndLossDto		= null;
			workOrders					= null;
			userDetails					= null;
			serviceEntriesTxnCheckerId	= null;
			isFuelEdit					= false;
			fuelAmountToBeAdded			= 0.0;
			isFuelDateChanged			= false;
			previousFuelDate			= null;
			previousFuelAmount			= null;
		}
		
	}
	
	public static VehicleProfitAndLossDto prepareVehicleProfitAndLossDtoForTripSheet(ValueObject	valueObject) throws Exception{
		VehicleProfitAndLossDto		vehicleProfitAndLossDto		= null;
		TripSheet					tripSheet					= null;
		CustomUserDetails			userDetails					= null;
		Long						serviceEntriesTxnCheckerId	= null;
		boolean						isFuelEdit					= false;
		Double						fuelAmountToBeAdded			= 0.0;
		boolean						isFuelDateChanged			= false;
		Timestamp					previousFuelDate			= null;
		Double						previousFuelAmount			= null;
		try {
			tripSheet					= (TripSheet) valueObject.get("tripSheet");
			userDetails					= (CustomUserDetails) valueObject.get("userDetails");
			serviceEntriesTxnCheckerId	= valueObject.getLong(VehicleProfitAndLossDto.TXN_CHECKER_ID, 0);
			
			isFuelEdit				= valueObject.getBoolean("isEdit", false);
			fuelAmountToBeAdded		= valueObject.getDouble("amountToBeAdded", 0);
			isFuelDateChanged		= valueObject.getBoolean("isDateChanged", false);
			previousFuelDate		= (Timestamp) valueObject.get("previousDate");
			previousFuelAmount		= valueObject.getDouble("previousAmount", 0);
			
			
			if(tripSheet != null) {
				vehicleProfitAndLossDto	= new VehicleProfitAndLossDto();
				
				vehicleProfitAndLossDto.setVid(tripSheet.getVid());
				vehicleProfitAndLossDto.setCompanyId(userDetails.getCompany_id());
				vehicleProfitAndLossDto.setTxnTypeId(tripSheet.getTripSheetID());
				if(tripSheet.getClosetripDate() != null) {
					vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(tripSheet.getClosetripDate()));
				}else {
					vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(new Date()));
				}
				
				vehicleProfitAndLossDto.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnAmount(valueObject.getDouble("tripSheetAmount", 0));
				vehicleProfitAndLossDto.setTxnType(VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				vehicleProfitAndLossDto.setType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
				vehicleProfitAndLossDto.setCreatedById(userDetails.getId());
				vehicleProfitAndLossDto.setLastUpdatedById(userDetails.getId());
				vehicleProfitAndLossDto.setCratedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnCheckerId(serviceEntriesTxnCheckerId);
				vehicleProfitAndLossDto.setEdit(isFuelEdit);
				vehicleProfitAndLossDto.setAmountToBeAdded(fuelAmountToBeAdded);
				vehicleProfitAndLossDto.setDateChanged(isFuelDateChanged);
				vehicleProfitAndLossDto.setPreviousDate(previousFuelDate);
				vehicleProfitAndLossDto.setPreviousAmount(previousFuelAmount);
				vehicleProfitAndLossDto.setExpenseId(valueObject.getLong(VehicleProfitAndLossDto.TXN_EXPENSE_ID, 0));
				vehicleProfitAndLossDto.setTripExpenseId(valueObject.getLong(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, 0));
				
			}
			
			return vehicleProfitAndLossDto;
			
		} catch (Exception e) {
			System.err.println("Exception "+e);
			throw e;
		}finally {
			vehicleProfitAndLossDto		= null;
			tripSheet					= null;
			userDetails					= null;
			serviceEntriesTxnCheckerId	= null;
			isFuelEdit					= false;
			fuelAmountToBeAdded			= 0.0;
			isFuelDateChanged			= false;
			previousFuelDate			= null;
			previousFuelAmount			= null;
		}
		
	}
	
	public static VehicleProfitAndLossDto prepareVehicleProfitAndLossDtoForTripSheetIncome(ValueObject	valueObject) throws Exception{
		VehicleProfitAndLossDto		vehicleProfitAndLossDto		= null;
		TripSheet					tripSheet					= null;
		CustomUserDetails			userDetails					= null;
		Long						txnCheckerId				= null;
		try {
			tripSheet					= (TripSheet) valueObject.get("tripSheet");
			userDetails					= (CustomUserDetails) valueObject.get("userDetails");
			txnCheckerId				= valueObject.getLong(VehicleProfitAndLossDto.TXN_CHECKER_ID, 0);
			
			if(tripSheet != null) {
				vehicleProfitAndLossDto	= new VehicleProfitAndLossDto();
				
				vehicleProfitAndLossDto.setVid(tripSheet.getVid());
				vehicleProfitAndLossDto.setCompanyId(userDetails.getCompany_id());
				vehicleProfitAndLossDto.setTxnTypeId(tripSheet.getTripSheetID());
				vehicleProfitAndLossDto.setTripIncomeId(valueObject.getLong(VehicleProfitAndLossDto.TRIP_INCOME_ID, 0));
				if(tripSheet.getClosetripDate() != null) {
					vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(tripSheet.getClosetripDate()));
				}else {
					vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(new Date()));
				}
				
				vehicleProfitAndLossDto.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnAmount(valueObject.getDouble("tripSheetAmount", 0));
				vehicleProfitAndLossDto.setTxnType(VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
				vehicleProfitAndLossDto.setType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
				vehicleProfitAndLossDto.setCreatedById(userDetails.getId());
				vehicleProfitAndLossDto.setLastUpdatedById(userDetails.getId());
				vehicleProfitAndLossDto.setCratedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnCheckerId(txnCheckerId);
				vehicleProfitAndLossDto.setExpenseId(valueObject.getLong(VehicleProfitAndLossDto.TXN_EXPENSE_ID, 0));
				vehicleProfitAndLossDto.setIncomeId(valueObject.getLong(VehicleProfitAndLossDto.TXN_INCOME_ID, 0));
				
			}
			
			return vehicleProfitAndLossDto;
			
		} catch (Exception e) {
			System.err.println("Exception "+e);
			throw e;
		}finally {
			vehicleProfitAndLossDto		= null;
			tripSheet					= null;
			userDetails					= null;
		}
		
	}
	
	public static VehicleProfitAndLossDto prepareVehicleProfitAndLossDtoForDriverSalary(ValueObject	valueObject) throws Exception{
		VehicleProfitAndLossDto		vehicleProfitAndLossDto		= null;
		VehicleProfitAndLossDto		sheetExpense				= null;
		CustomUserDetails			userDetails					= null;
		Long						serviceEntriesTxnCheckerId	= null;
		boolean						isFuelEdit					= false;
		Double						fuelAmountToBeAdded			= 0.0;
		boolean						isFuelDateChanged			= false;
		Timestamp					previousFuelDate			= null;
		Double						previousFuelAmount			= null;
		try {
			sheetExpense					= (VehicleProfitAndLossDto) valueObject.get("driverSalaryExpense");
			userDetails					= (CustomUserDetails) valueObject.get("userDetails");
			serviceEntriesTxnCheckerId	= valueObject.getLong(VehicleProfitAndLossDto.TXN_CHECKER_ID, 0);
			
			isFuelEdit				= valueObject.getBoolean("isEdit", false);
			fuelAmountToBeAdded		= valueObject.getDouble("amountToBeAdded", 0);
			isFuelDateChanged		= valueObject.getBoolean("isDateChanged", false);
			previousFuelDate		= (Timestamp) valueObject.get("previousDate");
			previousFuelAmount		= valueObject.getDouble("previousAmount", 0);
			
			
			if(sheetExpense != null) {
				vehicleProfitAndLossDto	= new VehicleProfitAndLossDto();
				
				vehicleProfitAndLossDto.setVid(sheetExpense.getVid());
				vehicleProfitAndLossDto.setCompanyId(userDetails.getCompany_id());
				vehicleProfitAndLossDto.setTxnTypeId(sheetExpense.getTxnTypeId());
				if(sheetExpense.getTransactionDateTime() != null) {
					vehicleProfitAndLossDto.setTransactionDateTime(sheetExpense.getTransactionDateTime());
				}else {
					vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(new Date()));
				}
				
				vehicleProfitAndLossDto.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnAmount(sheetExpense.getTxnAmount());
				vehicleProfitAndLossDto.setTxnType(VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				vehicleProfitAndLossDto.setType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY);
				vehicleProfitAndLossDto.setCreatedById(userDetails.getId());
				vehicleProfitAndLossDto.setLastUpdatedById(userDetails.getId());
				vehicleProfitAndLossDto.setCratedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnCheckerId(serviceEntriesTxnCheckerId);
				vehicleProfitAndLossDto.setEdit(isFuelEdit);
				vehicleProfitAndLossDto.setAmountToBeAdded(fuelAmountToBeAdded);
				vehicleProfitAndLossDto.setDateChanged(isFuelDateChanged);
				vehicleProfitAndLossDto.setPreviousDate(previousFuelDate);
				vehicleProfitAndLossDto.setPreviousAmount(previousFuelAmount);
				vehicleProfitAndLossDto.setExpenseId(sheetExpense.getExpenseId());
				
			}
			return vehicleProfitAndLossDto;
			
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleProfitAndLossDto		= null;
			sheetExpense				= null;
			userDetails					= null;
			serviceEntriesTxnCheckerId	= null;
			isFuelEdit					= false;
			fuelAmountToBeAdded			= 0.0;
			isFuelDateChanged			= false;
			previousFuelDate			= null;
			previousFuelAmount			= null;
		}
		
	}
	
	public static VehicleProfitAndLossDto prepareVehicleProfitAndLossDtoForUrea(ValueObject	valueObject) throws Exception{
		VehicleProfitAndLossDto		vehicleProfitAndLossDto		= null;
		UreaEntries					ureaEntries					= null;
		CustomUserDetails			userDetails					= null;
		Long						fuelEntryTxnCheckerId		= null;
		boolean						isUreaEdit					= false;
		Double						ureaAmountToBeAdded			= 0.0;
		boolean						isUreaDateChanged			= false;
		Timestamp					previousUreaDate			= null;
		Double						previousUreaAmount			= null;
		try {
			ureaEntries				= (UreaEntries) valueObject.get("ureaEntries");
			userDetails				= (CustomUserDetails) valueObject.get("userDetails");
			fuelEntryTxnCheckerId	= valueObject.getLong(VehicleProfitAndLossDto.TXN_CHECKER_ID, 0);
			
			isUreaEdit				= valueObject.getBoolean("isUreaEdit", false);
			ureaAmountToBeAdded		= valueObject.getDouble("ureaAmountToBeAdded", 0);
			isUreaDateChanged		= valueObject.getBoolean("isUreaDateChanged", false);
			previousUreaDate		= (Timestamp) valueObject.get("previousUreaDate");
			previousUreaAmount		= valueObject.getDouble("previousUreaAmount", 0);
			
			
			if(ureaEntries != null) {
				vehicleProfitAndLossDto	= new VehicleProfitAndLossDto();
				
				vehicleProfitAndLossDto.setVid(ureaEntries.getVid());
				vehicleProfitAndLossDto.setCompanyId(userDetails.getCompany_id());
				vehicleProfitAndLossDto.setTxnTypeId(ureaEntries.getUreaEntriesId());
				vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(ureaEntries.getUreaDate()));
				vehicleProfitAndLossDto.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnAmount(ureaEntries.getUreaAmount());
				vehicleProfitAndLossDto.setTxnType(VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				vehicleProfitAndLossDto.setType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA);
				vehicleProfitAndLossDto.setCreatedById(userDetails.getId());
				vehicleProfitAndLossDto.setLastUpdatedById(userDetails.getId());
				vehicleProfitAndLossDto.setCratedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnCheckerId(fuelEntryTxnCheckerId);
				vehicleProfitAndLossDto.setEdit(isUreaEdit);
				vehicleProfitAndLossDto.setAmountToBeAdded(ureaAmountToBeAdded);
				vehicleProfitAndLossDto.setDateChanged(isUreaDateChanged);
				vehicleProfitAndLossDto.setPreviousDate(previousUreaDate);
				vehicleProfitAndLossDto.setPreviousAmount(previousUreaAmount);
				vehicleProfitAndLossDto.setExpenseId(ureaEntries.getUreaEntriesId());
				
			}
			
			return vehicleProfitAndLossDto;
			
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleProfitAndLossDto		= null;
			ureaEntries					= null;
			userDetails					= null;
			fuelEntryTxnCheckerId		= null;
			isUreaEdit					= false;
			ureaAmountToBeAdded			= 0.0;
			isUreaDateChanged			= false;
			previousUreaDate			= null;
			previousUreaAmount			= null;
		}
		
	}
	
	public static VehicleProfitAndLossDto prepareVehicleProfitAndLossDtoForEMIDetails (ValueObject	valueObject) throws Exception{
		VehicleProfitAndLossDto		vehicleProfitAndLossDto		= null;
		VehicleEmiDetails			vehicleEmiDetails			= null;
		CustomUserDetails			userDetails					= null;
		Long						emiTxnCheckerId				= null;
		boolean						isEMIEdit					= false;
		Double						emiAmountToBeAdded			= 0.0;
		boolean						isEMIDateChanged			= false;
		Timestamp					previousEMIDate				= null;
		Double						previousEMIAmount			= null;
		try {
			vehicleEmiDetails		= (VehicleEmiDetails) valueObject.get("vehicleEmiDetails");
			userDetails				= (CustomUserDetails) valueObject.get("userDetails");
			emiTxnCheckerId			= valueObject.getLong(VehicleProfitAndLossDto.TXN_CHECKER_ID, 0);
			
			isEMIEdit				= valueObject.getBoolean("isEMIEdit", false);
			emiAmountToBeAdded		= valueObject.getDouble("emiAmountToBeAdded", 0);
			isEMIDateChanged		= valueObject.getBoolean("isEMIDateChanged", false);
			previousEMIDate			= (Timestamp) valueObject.get("previousEMIDate");
			previousEMIAmount		= valueObject.getDouble("previousEMIAmount", 0);
			
			
			if(vehicleEmiDetails != null) {
				vehicleProfitAndLossDto	= new VehicleProfitAndLossDto();
				
				vehicleProfitAndLossDto.setVid(vehicleEmiDetails.getVid());
				vehicleProfitAndLossDto.setCompanyId(userDetails.getCompany_id());
				vehicleProfitAndLossDto.setTxnTypeId(vehicleEmiDetails.getVehicleEmiDetailsId());
				if(isEMIEdit) {
					vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.getTimeStamp(valueObject.getString("loanStartDateEdit")));
				}else {
					vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(vehicleEmiDetails.getLoanStartDate()));
				}
				vehicleProfitAndLossDto.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnAmount(vehicleEmiDetails.getDownPaymentAmount());
				vehicleProfitAndLossDto.setTxnType(VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				vehicleProfitAndLossDto.setType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI);
				vehicleProfitAndLossDto.setCreatedById(userDetails.getId());
				vehicleProfitAndLossDto.setLastUpdatedById(userDetails.getId());
				vehicleProfitAndLossDto.setCratedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnCheckerId(emiTxnCheckerId);
				vehicleProfitAndLossDto.setEdit(isEMIEdit);
				vehicleProfitAndLossDto.setAmountToBeAdded(emiAmountToBeAdded);
				vehicleProfitAndLossDto.setDateChanged(isEMIDateChanged);
				vehicleProfitAndLossDto.setPreviousDate(previousEMIDate);
				vehicleProfitAndLossDto.setPreviousAmount(previousEMIAmount);
				vehicleProfitAndLossDto.setExpenseId(vehicleEmiDetails.getVehicleEmiDetailsId());
				vehicleProfitAndLossDto.setPreviousDownPaymentAmount(valueObject.getDouble("preDownpaymentAmount",0));
				
			}
			
			return vehicleProfitAndLossDto;
			
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleProfitAndLossDto		= null;
			vehicleEmiDetails			= null;
			userDetails					= null;
			emiTxnCheckerId				= null;
			isEMIEdit					= false;
			emiAmountToBeAdded			= 0.0;
			isEMIDateChanged			= false;
			previousEMIDate				= null;
			previousEMIAmount			= null;
		}
		
	}
	
	public static VehicleProfitAndLossDto prepareVehicleProfitAndLossDtoForEMIPaymentDetails (ValueObject	valueObject) throws Exception{
		VehicleProfitAndLossDto		vehicleProfitAndLossDto		= null;
		VehicleEmiPaymentDetails	vehicleEmiPaymentDetails	= null;
		CustomUserDetails			userDetails					= null;
		Long						emiTxnCheckerId				= null;
		boolean						isEMIEdit					= false;
		Double						emiAmountToBeAdded			= 0.0;
		boolean						isEMIDateChanged			= false;
		Timestamp					previousEMIDate				= null;
		Double						previousEMIAmount			= null;
		try {
			vehicleEmiPaymentDetails	= (VehicleEmiPaymentDetails) valueObject.get("vehicleEmiPaymentDetails");
			userDetails					= (CustomUserDetails) valueObject.get("userDetails");
			emiTxnCheckerId				= valueObject.getLong(VehicleProfitAndLossDto.TXN_CHECKER_ID, 0);
			
			emiAmountToBeAdded			= valueObject.getDouble("emiAmountToBeAdded", 0);
			isEMIDateChanged			= valueObject.getBoolean("isEMIDateChanged", false);
			previousEMIDate				= (Timestamp) valueObject.get("previousEMIDate");
			previousEMIAmount			= valueObject.getDouble("previousEMIAmount", 0);
			
			
			if(vehicleEmiPaymentDetails != null) {
				vehicleProfitAndLossDto	= new VehicleProfitAndLossDto();
				
				vehicleProfitAndLossDto.setVid(vehicleEmiPaymentDetails.getVid());
				vehicleProfitAndLossDto.setCompanyId(userDetails.getCompany_id());
				vehicleProfitAndLossDto.setTxnTypeId(vehicleEmiPaymentDetails.getVehicleEmiDetailsId());
				vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(vehicleEmiPaymentDetails.getEmiPaidDate()));
				vehicleProfitAndLossDto.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnAmount(vehicleEmiPaymentDetails.getEmiPaidAmount());
				vehicleProfitAndLossDto.setTxnType(VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				vehicleProfitAndLossDto.setType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI);
				vehicleProfitAndLossDto.setCreatedById(userDetails.getId());
				vehicleProfitAndLossDto.setLastUpdatedById(userDetails.getId());
				vehicleProfitAndLossDto.setCratedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnCheckerId(emiTxnCheckerId);
				vehicleProfitAndLossDto.setEdit(isEMIEdit);
				vehicleProfitAndLossDto.setAmountToBeAdded(emiAmountToBeAdded);
				vehicleProfitAndLossDto.setDateChanged(isEMIDateChanged);
				vehicleProfitAndLossDto.setPreviousDate(previousEMIDate);
				vehicleProfitAndLossDto.setPreviousAmount(previousEMIAmount);
				vehicleProfitAndLossDto.setExpenseId(vehicleEmiPaymentDetails.getVehicleEmiPaymentDetailsId());
				
			}
			
			return vehicleProfitAndLossDto;
			
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleProfitAndLossDto		= null;
			vehicleEmiPaymentDetails	= null;
			userDetails					= null;
			emiTxnCheckerId				= null;
			isEMIEdit					= false;
			emiAmountToBeAdded			= 0.0;
			isEMIDateChanged			= false;
			previousEMIDate				= null;
			previousEMIAmount			= null;
		}
		
	}

	public static VehicleProfitAndLossDto prepareVehicleProfitAndLossDtoForDealerServiceEntries(ValueObject	valueObject) throws Exception{
		VehicleProfitAndLossDto		vehicleProfitAndLossDto		= null;
		Long						serviceEntriesTxnCheckerId	= null;
		boolean						isFuelEdit					= false;
		Double						fuelAmountToBeAdded			= 0.0;
		boolean						isFuelDateChanged			= false;
		Timestamp					previousFuelDate			= null;
		Double						previousFuelAmount			= null;
		ValueObject					dealerValueObject			= null;
		try {
			dealerValueObject = (ValueObject) valueObject.get(""+VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES_NAME+"");
			serviceEntriesTxnCheckerId	= valueObject.getLong(VehicleProfitAndLossDto.TXN_CHECKER_ID, 0);
			
			isFuelEdit				= valueObject.getBoolean("isEdit", false);
			fuelAmountToBeAdded		= valueObject.getDouble("amountToBeAdded", 0);
			isFuelDateChanged		= valueObject.getBoolean("isDateChanged", false);
			previousFuelDate		= (Timestamp) valueObject.get("previousDate");
			previousFuelAmount		= valueObject.getDouble("previousAmount", 0);
			
			
			if(dealerValueObject != null) {
				vehicleProfitAndLossDto	= new VehicleProfitAndLossDto();
				
				vehicleProfitAndLossDto.setVid(dealerValueObject.getInt("vid"));
				vehicleProfitAndLossDto.setCompanyId(dealerValueObject.getInt("companyId"));
				vehicleProfitAndLossDto.setTxnTypeId(dealerValueObject.getLong("dealerServiceEntriesId"));
				vehicleProfitAndLossDto.setTransactionDateTime(DateTimeUtility.geTimeStampFromDate(new Date()));
				vehicleProfitAndLossDto.setSystemDateTime(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnAmount(dealerValueObject.getDouble("invoiceCost"));
				vehicleProfitAndLossDto.setTxnType(VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
				vehicleProfitAndLossDto.setType(VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES);
				vehicleProfitAndLossDto.setCreatedById(dealerValueObject.getLong("userId"));
				vehicleProfitAndLossDto.setLastUpdatedById(dealerValueObject.getLong("userId"));
				vehicleProfitAndLossDto.setCratedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setLastUpdatedOn(DateTimeUtility.getCurrentTimeStamp());
				vehicleProfitAndLossDto.setTxnCheckerId(serviceEntriesTxnCheckerId);
				vehicleProfitAndLossDto.setEdit(isFuelEdit);
				vehicleProfitAndLossDto.setAmountToBeAdded(fuelAmountToBeAdded);
				vehicleProfitAndLossDto.setDateChanged(isFuelDateChanged);
				vehicleProfitAndLossDto.setPreviousDate(previousFuelDate);
				vehicleProfitAndLossDto.setPreviousAmount(previousFuelAmount);
				vehicleProfitAndLossDto.setExpenseId(dealerValueObject.getLong("dealerServiceEntriesId"));
				
			}
			
			return vehicleProfitAndLossDto;
			
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleProfitAndLossDto		= null;
			serviceEntriesTxnCheckerId	= null;
			isFuelEdit					= false;
			fuelAmountToBeAdded			= 0.0;
			isFuelDateChanged			= false;
			previousFuelDate			= null;
			previousFuelAmount			= null;
		}
		
	}
	
}
