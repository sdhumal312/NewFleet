
package org.fleetopgroup.web.controller;

import java.util.List;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.model.TripDailyExpense;
import org.fleetopgroup.persistence.model.TripDailyIncome;
import org.fleetopgroup.persistence.model.TripDailySheet;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossIncomeTxnChecker;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ICustomUserDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IFuelService;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossIncomeTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossTxnCheckerService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

@Controller
public class VehicleProfitAndLossDataEntryPOJO {
	
	@Autowired	private IVehicleProfitAndLossTxnCheckerService			txnCheckerService;
	@Autowired  private IVehicleProfitAndLossService					vehicleProfitAndLossService;
	@Autowired	private IFuelService									fuelService;
	@Autowired	private ICustomUserDetailsService						userDetailsService;
	@Autowired	private IServiceEntriesService							serviceEntriesService;
	@Autowired	private IWorkOrdersService								workOrdersService;
	@Autowired	private ITripSheetService								tripSheetService;
	@Autowired	private ICompanyConfigurationService					companyConfigurationService;
	@Autowired	private ITripDailySheetService							tripDailySheetService;
	@Autowired	private IVehicleProfitAndLossIncomeTxnCheckerService	incomeTxnCheckerService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Scheduled(cron = "0 25 0 * * *")
	public void doScheduledWork() {
		try {
			LOGGER.warn("Profit & Loss Missing entry started..");
			
				addVehicleExpenseDetailsMissingEntries();
				addDateWiseVehicleExpenseDetailsMissingEntries();
				addMonthWiseVehicleExpenseDetailsMissingEntries();
				//addDateWiseVehicleExpenseBalanceMissingEntries();
				//addMonthWiseVehicleBalanceMissingEntries();
			
			LOGGER.warn("Profit & Loss Missing entry completed..");
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 46 0,13 * * *")
	public void deleteVehicleExpenseTxnChecker() {
		try {
			LOGGER.warn("TxnChecker Deletion started..");
			
			vehicleProfitAndLossService.updateIsFinallyEntered();
			vehicleProfitAndLossService.deleteVehicleExpenseTxnChecker();
			
			LOGGER.warn("TxnChecker Deletion  completed..");
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	}
	
	@Scheduled(cron = "0 04 0,13 * * *")
	public void deleteVehicleExpenseTxnIncomeChecker() {
		try {
			LOGGER.warn("TxnChecker Deletion started..");
			
			vehicleProfitAndLossService.updateIsFinallyEnteredForIncome();
			vehicleProfitAndLossService.deleteVehicleExpenseIncomeTxnChecker();
			
			LOGGER.warn("TxnChecker Deletion  completed..");
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	}
	
	//@Scheduled(cron = "0 30 0 * * *")
	public void doScheduledWorkForMissingIncome() {
		
		try {
			LOGGER.warn("Profit & Loss Missing entry started..");
			
			addVehicleIncomeDetailsMissingEntries();
			addMonthWiseVehicleIncomeDetailsMissingEntries();
			addMonthWiseVehicleIncomeBalanceDetailsMissingEntries();
			
			LOGGER.warn("Profit & Loss Missing entry completed..");
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	}
	
	public void addVehicleExpenseDetailsMissingEntries() {

		List<VehicleProfitAndLossTxnChecker>  	pendingList		= null;
		
		try {
			
			
			/**
			 * For VehicleExpenseDetails Table
			 */
			pendingList	= txnCheckerService.getAllPendingTxnCheckerListForVehicleExpenseAdded();
			
			if(pendingList != null && !pendingList.isEmpty()) {
				
				for(VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker : pendingList) {
					ValueObject		valueObject	= new ValueObject();
					valueObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueObject.put("userDetails", userDetailsService.getCustomUserDetails(profitAndLossTxnChecker.getCompanyId()));
					
					if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL) {
						valueObject.put("fuel", fuelService.getFuel(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						valueObject.put("serviceEntries", serviceEntriesService.getServiceEntriesDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						valueObject.put("serviceEntries", serviceEntriesService.getServiceEntriesDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER) {
						valueObject.put("workOrders", workOrdersService.getWorkOrdersDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						int flavor	= companyConfigurationService.getTripSheetFlavor(profitAndLossTxnChecker.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
						if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
							TripSheet				tripSheet	 = tripSheetService.getTripSheet(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId());
							TripSheetExpense		expense		 = tripSheetService.getTripSheetExpensebyExpenseId(profitAndLossTxnChecker.getTripExpenseId(), profitAndLossTxnChecker.getCompanyId(), profitAndLossTxnChecker.getTransactionId());
							valueObject.put("tripSheet", tripSheet);
							valueObject.put("tripSheetAmount", expense.getExpenseAmount());
						}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
							TripDailySheetDto TripCol = tripDailySheetService.Get_Showing_TripDaily_Sheet(profitAndLossTxnChecker.getTransactionId(), userDetailsService.getCustomUserDetails(profitAndLossTxnChecker.getCompanyId()));
							TripDailyExpense	dailyExpense	= tripDailySheetService.getExpenseByTripSheetIdAndExpenseId(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getTripExpenseId());
							
							TripSheet	tripSheet = new TripSheet();
							tripSheet.setVid(TripCol.getVEHICLEID());
							tripSheet.setTripSheetID(TripCol.getTRIPDAILYID());
							tripSheet.setClosetripDate(TripCol.getTRIP_OPEN_DATE_D());
							valueObject.put("tripSheetAmount", dailyExpense.getExpenseAmount());
							valueObject.put("tripSheet", tripSheet);
						}
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						valueObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, profitAndLossTxnChecker.getExpenseId());
					}

					vehicleProfitAndLossService.runThreadForVehicleExpenseDetails(valueObject);
					
				}
				
			}
			
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	
	}
	
	public void addDateWiseVehicleExpenseDetailsMissingEntries() {

		List<VehicleProfitAndLossTxnChecker>  	pendingList		= null;
		
		try {
			
			
			/**
			 * For VehicleExpenseDetails Table
			 */
			pendingList	= txnCheckerService.getAllPendingTxnCheckerListForDateWiseVehicleExpenseAdded();
			
			if(pendingList != null && !pendingList.isEmpty()) {
				
				for(VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker : pendingList) {
					ValueObject		valueObject	= new ValueObject();
					valueObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueObject.put("userDetails", userDetailsService.getCustomUserDetails(profitAndLossTxnChecker.getCompanyId()));
					
					if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL) {
						valueObject.put("fuel", fuelService.getFuel(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						valueObject.put("serviceEntries", serviceEntriesService.getServiceEntriesDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						valueObject.put("serviceEntries", serviceEntriesService.getServiceEntriesDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER) {
						valueObject.put("workOrders", workOrdersService.getWorkOrdersDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						int flavor	= companyConfigurationService.getTripSheetFlavor(profitAndLossTxnChecker.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
						if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
							TripSheet				tripSheet	 = tripSheetService.getTripSheet(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId());
							TripSheetExpense		expense		 = tripSheetService.getTripSheetExpensebyExpenseId(profitAndLossTxnChecker.getTripExpenseId(), profitAndLossTxnChecker.getCompanyId(), profitAndLossTxnChecker.getTransactionId());
							valueObject.put("tripSheet", tripSheet);
							valueObject.put("tripSheetAmount", expense.getExpenseAmount());
						}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
							TripDailySheetDto TripCol = tripDailySheetService.Get_Showing_TripDaily_Sheet(profitAndLossTxnChecker.getTransactionId(), userDetailsService.getCustomUserDetails(profitAndLossTxnChecker.getCompanyId()));
							TripDailyExpense	dailyExpense	= tripDailySheetService.getExpenseByTripSheetIdAndExpenseId(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getTripExpenseId());
							
							TripSheet	tripSheet = new TripSheet();
							tripSheet.setVid(TripCol.getVEHICLEID());
							tripSheet.setTripSheetID(TripCol.getTRIPDAILYID());
							tripSheet.setClosetripDate(TripCol.getTRIP_OPEN_DATE_D());
							valueObject.put("tripSheetAmount", dailyExpense.getExpenseAmount());
							valueObject.put("tripSheet", tripSheet);
						}
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						valueObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, profitAndLossTxnChecker.getExpenseId());
					}

					vehicleProfitAndLossService.runThreadForDateWiseVehicleExpenseDetails(valueObject);
				}
				
			}
			
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	
	}
	
	public void addMonthWiseVehicleExpenseDetailsMissingEntries() {

		List<VehicleProfitAndLossTxnChecker>  	pendingList		= null;
		
		try {
			
			
			/**
			 * For VehicleExpenseDetails Table
			 */
			pendingList	= txnCheckerService.getAllPendingTxnCheckerListForMonthWiseVehicleExpenseAdded();
			
			if(pendingList != null && !pendingList.isEmpty()) {
				
				for(VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker : pendingList) {
					ValueObject		valueObject	= new ValueObject();
					valueObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueObject.put("userDetails", userDetailsService.getCustomUserDetails(profitAndLossTxnChecker.getCompanyId()));
					
					if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL) {
						valueObject.put("fuel", fuelService.getFuel(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						valueObject.put("serviceEntries", serviceEntriesService.getServiceEntriesDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						valueObject.put("serviceEntries", serviceEntriesService.getServiceEntriesDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER) {
						valueObject.put("workOrders", workOrdersService.getWorkOrdersDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						int flavor	= companyConfigurationService.getTripSheetFlavor(profitAndLossTxnChecker.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
						if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
							TripSheet				tripSheet	 = tripSheetService.getTripSheet(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId());
							TripSheetExpense		expense		 = tripSheetService.getTripSheetExpensebyExpenseId(profitAndLossTxnChecker.getTripExpenseId(), profitAndLossTxnChecker.getCompanyId(), profitAndLossTxnChecker.getTransactionId());
							valueObject.put("tripSheet", tripSheet);
							valueObject.put("tripSheetAmount", expense.getExpenseAmount());
						}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
							TripDailySheetDto TripCol = tripDailySheetService.Get_Showing_TripDaily_Sheet(profitAndLossTxnChecker.getTransactionId(), userDetailsService.getCustomUserDetails(profitAndLossTxnChecker.getCompanyId()));
							TripDailyExpense	dailyExpense	= tripDailySheetService.getExpenseByTripSheetIdAndExpenseId(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getTripExpenseId());
							
							TripSheet	tripSheet = new TripSheet();
							tripSheet.setVid(TripCol.getVEHICLEID());
							tripSheet.setTripSheetID(TripCol.getTRIPDAILYID());
							tripSheet.setClosetripDate(TripCol.getTRIP_OPEN_DATE_D());
							valueObject.put("tripSheetAmount", dailyExpense.getExpenseAmount());
							valueObject.put("tripSheet", tripSheet);
						}
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						valueObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, profitAndLossTxnChecker.getExpenseId());
					}

					vehicleProfitAndLossService.runThreadForMonthWiseVehicleExpenseDetails(valueObject);
				}
				
			}
			
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	
	}
	
	public void addDateWiseVehicleExpenseBalanceMissingEntries() {

		List<VehicleProfitAndLossTxnChecker>  	pendingList		= null;
		
		try {
			
			
			/**
			 * For VehicleExpenseDetails Table
			 */
			pendingList	= txnCheckerService.getAllPendingTxnCheckerListForDateWiseVehicleBalanceAdded();
			
			if(pendingList != null && !pendingList.isEmpty()) {
				
				for(VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker : pendingList) {
					ValueObject		valueObject	= new ValueObject();
					valueObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueObject.put("userDetails", userDetailsService.getCustomUserDetails(profitAndLossTxnChecker.getCompanyId()));
					
					if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL) {
						valueObject.put("fuel", fuelService.getFuel(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						valueObject.put("serviceEntries", serviceEntriesService.getServiceEntriesDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						valueObject.put("serviceEntries", serviceEntriesService.getServiceEntriesDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER) {
						valueObject.put("workOrders", workOrdersService.getWorkOrdersDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						int flavor	= companyConfigurationService.getTripSheetFlavor(profitAndLossTxnChecker.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
						if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
							TripSheet				tripSheet	 = tripSheetService.getTripSheet(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId());
							TripSheetExpense		expense		 = tripSheetService.getTripSheetExpensebyExpenseId(profitAndLossTxnChecker.getTripExpenseId(), profitAndLossTxnChecker.getCompanyId(), profitAndLossTxnChecker.getTransactionId());
							valueObject.put("tripSheet", tripSheet);
							valueObject.put("tripSheetAmount", expense.getExpenseAmount());
						}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
							TripDailySheetDto TripCol = tripDailySheetService.Get_Showing_TripDaily_Sheet(profitAndLossTxnChecker.getTransactionId(), userDetailsService.getCustomUserDetails(profitAndLossTxnChecker.getCompanyId()));
							TripDailyExpense	dailyExpense	= tripDailySheetService.getExpenseByTripSheetIdAndExpenseId(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getTripExpenseId());
							
							TripSheet	tripSheet = new TripSheet();
							tripSheet.setVid(TripCol.getVEHICLEID());
							tripSheet.setTripSheetID(TripCol.getTRIPDAILYID());
							tripSheet.setClosetripDate(TripCol.getTRIP_OPEN_DATE_D());
							valueObject.put("tripSheetAmount", dailyExpense.getExpenseAmount());
							valueObject.put("tripSheet", tripSheet);
						}
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						valueObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, profitAndLossTxnChecker.getExpenseId());
						valueObject.put("txnIds", profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId()+"");
					}

					//vehicleProfitAndLossService.runThreadForDateWiseVehicleBalanceDetails(valueObject);
					
				}
				
			}
			
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	
	}
	
	public void addMonthWiseVehicleBalanceMissingEntries() {

		List<VehicleProfitAndLossTxnChecker>  	pendingList		= null;
		
		try {
			
			
			/**
			 * For VehicleExpenseDetails Table
			 */
			pendingList	= txnCheckerService.getAllPendingTxnCheckerListForMonthWiseVehicleBalanceAdded();
			
			if(pendingList != null && !pendingList.isEmpty()) {
				
				for(VehicleProfitAndLossTxnChecker	profitAndLossTxnChecker : pendingList) {
					ValueObject		valueObject	= new ValueObject();
					valueObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
					valueObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueObject.put("userDetails", userDetailsService.getCustomUserDetails(profitAndLossTxnChecker.getCompanyId()));
					
					if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL) {
						valueObject.put("fuel", fuelService.getFuel(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						valueObject.put("serviceEntries", serviceEntriesService.getServiceEntriesDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						valueObject.put("serviceEntries", serviceEntriesService.getServiceEntriesDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER) {
						valueObject.put("workOrders", workOrdersService.getWorkOrdersDetails(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER);
					}else if(profitAndLossTxnChecker.getTransactionSubTypeId() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						int flavor	= companyConfigurationService.getTripSheetFlavor(profitAndLossTxnChecker.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
						if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
							TripSheet				tripSheet	 = tripSheetService.getTripSheet(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId());
							TripSheetExpense		expense		 = tripSheetService.getTripSheetExpensebyExpenseId(profitAndLossTxnChecker.getTripExpenseId(), profitAndLossTxnChecker.getCompanyId(), profitAndLossTxnChecker.getTransactionId());
							valueObject.put("tripSheet", tripSheet);
							valueObject.put("tripSheetAmount", expense.getExpenseAmount());
						}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
							TripDailySheetDto TripCol = tripDailySheetService.Get_Showing_TripDaily_Sheet(profitAndLossTxnChecker.getTransactionId(), userDetailsService.getCustomUserDetails(profitAndLossTxnChecker.getCompanyId()));
							TripDailyExpense	dailyExpense	= tripDailySheetService.getExpenseByTripSheetIdAndExpenseId(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getTripExpenseId());
							
							TripSheet	tripSheet = new TripSheet();
							tripSheet.setVid(TripCol.getVEHICLEID());
							tripSheet.setTripSheetID(TripCol.getTRIPDAILYID());
							tripSheet.setClosetripDate(TripCol.getTRIP_OPEN_DATE_D());
							valueObject.put("tripSheetAmount", dailyExpense.getExpenseAmount());
							valueObject.put("tripSheet", tripSheet);
						}
						valueObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						valueObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, profitAndLossTxnChecker.getExpenseId());
						valueObject.put("txnIds", profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId()+"");
					}

					vehicleProfitAndLossService.runThreadForMonthWiseVehicleBalanceDetails(valueObject);
				}
				
			}
			
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	
	}
	
	public void addVehicleIncomeDetailsMissingEntries() {

		List<VehicleProfitAndLossIncomeTxnChecker>				incomeTxnCheckerList			= null;
		try {
			
			/**
			 * For VehicleIncomeDetails Table
			 */
			incomeTxnCheckerList	=	incomeTxnCheckerService.getAllPendingTxnCheckerListForVehicleExpenseAdded();
			
			if(incomeTxnCheckerList != null && !incomeTxnCheckerList.isEmpty()) {
				TripSheetIncome	incomeDto	=	null;
				
				for(VehicleProfitAndLossIncomeTxnChecker	profitAndLossTxnChecker : incomeTxnCheckerList) {
					
					Double incomAmount	= 0.0;
					Integer incomeId	= 0;
					
					int flavor	= companyConfigurationService.getTripSheetFlavor(profitAndLossTxnChecker.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
					
					if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
						incomeDto	= tripSheetService.getTripSheetIncomeByTripIdAndIncomeId(profitAndLossTxnChecker.getTransactionId(), Integer.parseInt(profitAndLossTxnChecker.getIncomeId()+""));
						incomAmount	= incomeDto.getIncomeAmount();
						incomeId	= incomeDto.getIncomeId();
						
					}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
						TripDailyIncome		dailyIncome = tripDailySheetService.getIncomeByTripSheetIdAndIncomeId(profitAndLossTxnChecker.getTransactionId(), Integer.parseInt(profitAndLossTxnChecker.getIncomeId()+""));
						incomAmount	= dailyIncome.getIncomeAmount();
						incomeId	= dailyIncome.getIncomeId();
						
					}
					ValueObject	valueInObject				= new ValueObject();
					valueInObject.put("tripSheet", tripSheetService.getTripSheet(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
					valueInObject.put("userDetails", userDetailsService.getCustomUserDetails(profitAndLossTxnChecker.getCompanyId()));
					valueInObject.put(VehicleExpenseTypeConstant.INCOME_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
					valueInObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
					valueInObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueInObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, incomeId);
					valueInObject.put("tripSheetAmount", incomAmount);
					valueInObject.put("txnIds", profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId()+"");
						
					vehicleProfitAndLossService.runThreadForVehicleIncomeDetails(valueInObject);
					//vehicleProfitAndLossService.runThreadForMonthWiseIncomeDetails(valueInObject);
					//vehicleProfitAndLossService.runThreadForMonthWiseIncomeBalance(valueInObject);
				}
				
			}
			
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	
	}
	
	public void addMonthWiseVehicleIncomeDetailsMissingEntries() {

		List<VehicleProfitAndLossIncomeTxnChecker>				incomeTxnCheckerList			= null;
		try {
			
			/**
			 * For VehicleIncomeDetails Table
			 */
			incomeTxnCheckerList	=	incomeTxnCheckerService.getAllPendingTxnCheckerListForMonthWiseVehicleIncomeAdded();
			
			if(incomeTxnCheckerList != null && !incomeTxnCheckerList.isEmpty()) {
				TripSheetIncome	incomeDto	=	null;
				
				for(VehicleProfitAndLossIncomeTxnChecker	profitAndLossTxnChecker : incomeTxnCheckerList) {
					
					Double incomAmount	= 0.0;
					Integer incomeId	= 0;
					
					int flavor	= companyConfigurationService.getTripSheetFlavor(profitAndLossTxnChecker.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
					ValueObject	valueInObject				= new ValueObject();
					if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
						incomeDto	= tripSheetService.getTripSheetIncomeByTripIdAndIncomeId(profitAndLossTxnChecker.getTransactionId(), Integer.parseInt(profitAndLossTxnChecker.getIncomeId()+""));
						incomAmount	= incomeDto.getIncomeAmount();
						incomeId	= incomeDto.getIncomeId();
						valueInObject.put("tripSheet", tripSheetService.getTripSheet(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						
					}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
						TripDailyIncome		dailyIncome = tripDailySheetService.getIncomeByTripSheetIdAndIncomeId(profitAndLossTxnChecker.getTransactionId(), Integer.parseInt(profitAndLossTxnChecker.getIncomeId()+""));
						incomAmount	= dailyIncome.getIncomeAmount();
						incomeId	= dailyIncome.getIncomeId();
						TripDailySheet	dailySheet = tripDailySheetService.Get_Showing_TripDaily_Sheet(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId());
						TripSheet	tripSheet = null;
						if(dailySheet != null) {
							tripSheet	= new TripSheet();
							tripSheet.setVid(dailySheet.getVEHICLEID());
							tripSheet.setTripSheetID(dailySheet.getTRIPDAILYID());
							tripSheet.setClosetripDate(dailySheet.getTRIP_OPEN_DATE());
							valueInObject.put("tripSheet", tripSheet);
						}
					}
					
					valueInObject.put("userDetails", userDetailsService.getCustomUserDetails(profitAndLossTxnChecker.getCompanyId()));
					valueInObject.put(VehicleExpenseTypeConstant.INCOME_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
					valueInObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
					valueInObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueInObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, incomeId);
					valueInObject.put("tripSheetAmount", incomAmount);
					valueInObject.put("txnIds", profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId()+"");
					
					vehicleProfitAndLossService.runThreadForMonthWiseIncomeDetails(valueInObject);
				}
				
			}
			
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	
	}
	public void addMonthWiseVehicleIncomeBalanceDetailsMissingEntries() {

		List<VehicleProfitAndLossIncomeTxnChecker>				incomeTxnCheckerList			= null;
		try {
			
			/**
			 * For VehicleIncomeDetails Table
			 */
			incomeTxnCheckerList	=	incomeTxnCheckerService.getAllPendingTxnCheckerListForMonthWiseVehicleBalanceAdded();
			
			if(incomeTxnCheckerList != null && !incomeTxnCheckerList.isEmpty()) {
				TripSheetIncome	incomeDto	=	null;
				
				for(VehicleProfitAndLossIncomeTxnChecker	profitAndLossTxnChecker : incomeTxnCheckerList) {
					
					Double incomAmount	= 0.0;
					Integer incomeId	= 0;
					ValueObject	valueInObject				= new ValueObject();
					
					int flavor	= companyConfigurationService.getTripSheetFlavor(profitAndLossTxnChecker.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
					
					if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
						incomeDto	= tripSheetService.getTripSheetIncomeByTripIdAndIncomeId(profitAndLossTxnChecker.getTransactionId(), Integer.parseInt(profitAndLossTxnChecker.getIncomeId()+""));
						incomAmount	= incomeDto.getIncomeAmount();
						incomeId	= incomeDto.getIncomeId();
						valueInObject.put("tripSheet", tripSheetService.getTripSheet(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId()));
						
					}else if(flavor == TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
						TripDailyIncome		dailyIncome = tripDailySheetService.getIncomeByTripSheetIdAndIncomeId(profitAndLossTxnChecker.getTransactionId(), Integer.parseInt(profitAndLossTxnChecker.getIncomeId()+""));
						incomAmount	= dailyIncome.getIncomeAmount();
						incomeId	= dailyIncome.getIncomeId();
						
						TripDailySheet	dailySheet = tripDailySheetService.Get_Showing_TripDaily_Sheet(profitAndLossTxnChecker.getTransactionId(), profitAndLossTxnChecker.getCompanyId());
						TripSheet	tripSheet = null;
						if(dailySheet != null) {
							tripSheet	= new TripSheet();
							tripSheet.setVid(dailySheet.getVEHICLEID());
							tripSheet.setTripSheetID(dailySheet.getTRIPDAILYID());
							tripSheet.setClosetripDate(dailySheet.getTRIP_OPEN_DATE());
							valueInObject.put("tripSheet", tripSheet);
						}
						
					}
					
					
					valueInObject.put("userDetails", userDetailsService.getCustomUserDetails(profitAndLossTxnChecker.getCompanyId()));
					valueInObject.put(VehicleExpenseTypeConstant.INCOME_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
					valueInObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
					valueInObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
					valueInObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, incomeId);
					valueInObject.put("tripSheetAmount", incomAmount);
					valueInObject.put("txnIds", profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId()+"");
						
					vehicleProfitAndLossService.runThreadForMonthWiseIncomeBalance(valueInObject);
				}
				
			}
			
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	
	}
	
}
