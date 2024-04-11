package org.fleetopgroup.persistence.service;



import java.sql.Timestamp;

import java.util.ArrayList;

import java.util.HashMap;

import java.util.List;



import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;

import javax.persistence.TypedQuery;



import org.fleetopgroup.constant.PropertyFileConstants;

import org.fleetopgroup.persistence.bl.TallyBL;

import org.fleetopgroup.persistence.dao.TallyCompanyPermissionRepository;

import org.fleetopgroup.persistence.dao.TallyCompanyRepository;

import org.fleetopgroup.persistence.dto.CustomUserDetails;

import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;

import org.fleetopgroup.persistence.model.TallyCompany;

import org.fleetopgroup.persistence.model.TallyCompanyPermission;

import org.fleetopgroup.persistence.serviceImpl.IBatteryInvoiceService;

import org.fleetopgroup.persistence.serviceImpl.IClothInventoryService;

import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;

import org.fleetopgroup.persistence.serviceImpl.IDealerServiceEntriesService;

import org.fleetopgroup.persistence.serviceImpl.IFuelService;

import org.fleetopgroup.persistence.serviceImpl.IInventoryService;

import org.fleetopgroup.persistence.serviceImpl.IInventoryTyreService;

import org.fleetopgroup.persistence.serviceImpl.IPurchaseOrdersService;

import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;

import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;

import org.fleetopgroup.persistence.serviceImpl.ITallyIntegrationService;

import org.fleetopgroup.persistence.serviceImpl.ITollExpensesDetailsService;

import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;

import org.fleetopgroup.persistence.serviceImpl.IUreaInventoryService;

import org.fleetopgroup.persistence.serviceImpl.IVendorApprovalService;

import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;

import org.fleetopgroup.web.util.DateTimeUtility;

import org.fleetopgroup.web.util.Utility;

import org.fleetopgroup.web.util.ValueObject;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;



@Service

public class TallyIntegrationService implements ITallyIntegrationService {

	

	@PersistenceContext EntityManager 				entityManager;



	@Autowired			ITripSheetService					tripSheetService;

	@Autowired			TallyCompanyRepository				TallyCompanyRepository;

	@Autowired			IFuelService						fuelService;

	@Autowired			IServiceEntriesService				serviceEntriesService;

	@Autowired			IWorkOrdersService					workOrderService;

	@Autowired			ICompanyConfigurationService		companyConfigurationService;

	@Autowired			TallyCompanyPermissionRepository	permissionRepository;

	@Autowired			IVendorApprovalService				vendorApprovalService;

	@Autowired			IRenewalReminderService				renewalReminderService;

	@Autowired			IInventoryTyreService				inventoryTyreService;

	@Autowired			IInventoryService					inventoryService;

	@Autowired			IBatteryInvoiceService				batteryInvoiceService;

	@Autowired			IClothInventoryService				clothInventoryService;

	@Autowired			IUreaInventoryService				ureaInventoryService;

	@Autowired			ITollExpensesDetailsService			tollExpensesDetailsService;

	@Autowired			IPurchaseOrdersService				purchaseOrdersService;

	@Autowired			IDealerServiceEntriesService		dealerServiceEntriesService;

	@Autowired			TallyCompanyRepository				tallyCompanyDao;

	

	TallyBL     		tallyBL						= new TallyBL();

	

	@Override

	public ValueObject getTallyIntegrationData(ValueObject valueObject) throws Exception {

		List<TripSheetExpenseDto> 		fuelList				= null;

		List<TripSheetExpenseDto> 		finalList				= null;

		List<TripSheetExpenseDto> 		expenseList				= null;

		List<TripSheetExpenseDto> 		serviceEnteriesList		= null;

		List<TripSheetExpenseDto> 		workOrdersList			= null;

		List<TripSheetExpenseDto> 		vendorPaymentList		= null;

		List<TripSheetExpenseDto> 		fuelListOutTrip			= null;

		List<TripSheetExpenseDto> 		renewalList				= null;

		List<TripSheetExpenseDto> 		tyreInvoiceList			= null;

		List<TripSheetExpenseDto> 		partInvoiceList			= null;

		List<TripSheetExpenseDto> 		batteryInvoiceList		= null;

		List<TripSheetExpenseDto> 		clothInvoiceList		= null;

		List<TripSheetExpenseDto> 		ureaInvoiceList			= null;

		List<TripSheetExpenseDto> 		fasttagList				= null;

		List<TripSheetExpenseDto> 		invoiceList				= null;

		List<TripSheetExpenseDto> 		vehicleExpList			= null;

		List<TripSheetExpenseDto> 		purchaseOrder			= null;

		List<TripSheetExpenseDto>       laundryInvoice			= null;

		List<TripSheetExpenseDto>       vehicleLaundryExp		= null;

		List<TripSheetExpenseDto>       driverBalanceList		= null;

		List<TripSheetExpenseDto>       dealerServiceEntryList	= null;

		HashMap<String, Object> 		configuration			= null;

		

		try {

			finalList		= new ArrayList<>();

			invoiceList		= new ArrayList<>();

			vehicleExpList  = new ArrayList<>();

			laundryInvoice	= new ArrayList<TripSheetExpenseDto>();

			

			configuration			= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), 

														PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);

			

			if(!(boolean) configuration.getOrDefault("allowTallyIntegrationWithoutApproval", false)) {

				fuelList				= fuelService.getFuelListforTallyImport(valueObject.getString("fromDate"), valueObject.getString("toDate"), 

						valueObject.getInt("companyId"), valueObject.getString("selectedStr"), (String)configuration.getOrDefault("tallyHeaderForFuelExpense", "Diesel Expense"));

				

				fuelListOutTrip			= fuelService.getFuelListOutTripForTally(valueObject.getString("fromDate"), valueObject.getString("toDate"), 

						valueObject.getInt("companyId"), valueObject.getString("selectedStr"), (String)configuration.getOrDefault("tallyHeaderForFuelExpense", "Diesel Expense"));

			}else {

				fuelListOutTrip			= fuelService.getFuelListWithoutApprovalForTally(valueObject.getString("fromDate"), valueObject.getString("toDate"), 

						valueObject.getInt("companyId"), valueObject.getString("selectedStr"), (String)configuration.getOrDefault("tallyHeaderForFuelExpense", "Diesel Expense"));

			}

				expenseList				= tripSheetService.findAllTripSheetExpense(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			

			serviceEnteriesList		= serviceEntriesService.getServiceEntriesListForTallyImport(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			workOrdersList			= workOrderService.getWorkOrdersListForTallyImport(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			tyreInvoiceList			= inventoryTyreService.getTyreInvoiceListForTallyImport(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), 

										valueObject.getString("selectedStr"), (String)configuration.getOrDefault("tyreExpenseLedgeName", "Motor Part Expense"));

			partInvoiceList			= inventoryService.getPartInvoiceListForTallyImport(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			batteryInvoiceList		= batteryInvoiceService.getBatteryInvoiceListForTallyImport(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			clothInvoiceList		= clothInventoryService.getClothInvoiceListForTallyImport(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			ureaInvoiceList			= ureaInventoryService.getUreaInvoiceListForTallyImport(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			

			if(!(boolean) configuration.getOrDefault("getBankPaymentDetails", false)) {

				vendorPaymentList		= vendorApprovalService.findVendorPaymentList(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			}else {

				vendorPaymentList		= vendorApprovalService.findVendorPaymentListATC(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			}

			

			renewalList				= renewalReminderService.getRenewalListForTallyImport(valueObject.getString("fromDate"), 

										valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"),

										(boolean) configuration.getOrDefault("getBankPaymentDetails", false), (String)configuration.getOrDefault("renewalLedgerName", "TAX and INSURANCE A/C"));

			fasttagList				= tollExpensesDetailsService.getTollExpensesForTally(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			purchaseOrder			= purchaseOrdersService.getPurchaseListForTallyImport(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			laundryInvoice			= clothInventoryService.getLaundryInvoiceListForTallyImport(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			vehicleLaundryExp		= clothInventoryService.getVehicleLaundryExpListForTally(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			

			if((boolean) configuration.getOrDefault("selectDefaultTallyCompany", false)) {

				

				Long defaultTallyCId = Long.parseLong(configuration.getOrDefault("defaultTallyCompanyId", 21)+"");

				

				dealerServiceEntryList	= dealerServiceEntriesService.getDealerEntriesListForTallyImport(valueObject.getString("fromDate"), 

						valueObject.getString("toDate"), valueObject.getInt("companyId"), defaultTallyCId);

			} 

			

			

			if(fuelList != null && !fuelList.isEmpty()) {

				finalList.addAll(fuelList);

			}

			if(expenseList != null && !expenseList.isEmpty()) {

				finalList.addAll(expenseList);

			}

			if(fuelListOutTrip != null && !fuelListOutTrip.isEmpty()) {

				finalList.addAll(fuelListOutTrip);

			}

			

			  if(serviceEnteriesList != null && !serviceEnteriesList.isEmpty()) {

				  	finalList.addAll(serviceEnteriesList); 

			 } 

			  

			  if(workOrdersList != null && !workOrdersList.isEmpty()) { 

				  vehicleExpList.addAll(workOrdersList); 

			  } 

			  if(vehicleLaundryExp != null && !vehicleLaundryExp.isEmpty()) { 

				  vehicleExpList.addAll(vehicleLaundryExp); 

			  } 

			  

				if(renewalList != null && !renewalList.isEmpty()) {

					finalList.addAll(renewalList);

				}

				if(tyreInvoiceList != null && !tyreInvoiceList.isEmpty()) {

					invoiceList.addAll(tyreInvoiceList);

				}

				if(partInvoiceList != null && !partInvoiceList.isEmpty()) {

					invoiceList.addAll(partInvoiceList);

				}

				if(batteryInvoiceList != null && !batteryInvoiceList.isEmpty()) {

					invoiceList.addAll(batteryInvoiceList);

				}

				if(clothInvoiceList != null && !clothInvoiceList.isEmpty()) {

					invoiceList.addAll(clothInvoiceList);

				}

				if(laundryInvoice != null && !laundryInvoice.isEmpty()) {

					invoiceList.addAll(laundryInvoice);

				}

				if(ureaInvoiceList != null && !ureaInvoiceList.isEmpty()) {

					invoiceList.addAll(ureaInvoiceList);

				}

				if(purchaseOrder != null && !purchaseOrder.isEmpty()) {

					invoiceList.addAll(purchaseOrder);

				}

				if(fasttagList != null && !fasttagList.isEmpty()) {

					finalList.addAll(fasttagList);

				}

				if(dealerServiceEntryList != null && !dealerServiceEntryList.isEmpty()) {

					finalList.addAll(dealerServiceEntryList);

				}

			 

			valueObject.put("expenseList", finalList);

			valueObject.put("vendorPaymentList", vendorPaymentList);

			valueObject.put("invoiceList", invoiceList);

			valueObject.put("vehicleExpList", vehicleExpList);

			valueObject.put("driverBalanceList", driverBalanceList);

			return valueObject;

		} catch (Exception e) {

			throw e;

		}finally {

			fuelList	= null;

		}

	}

	

	@Override

	public ValueObject getTallyIntegrationDataAtc(ValueObject valueObject) throws Exception {

		List<TripSheetExpenseDto> 		fuelList				= null;

		List<TripSheetExpenseDto> 		finalList				= null;

		List<TripSheetExpenseDto> 		expenseList				= null;

		List<TripSheetExpenseDto> 		serviceEnteriesList		= null;

		List<TripSheetExpenseDto> 		workOrdersList			= null;

		List<TripSheetExpenseDto> 		vendorPaymentList		= null;

		List<TripSheetExpenseDto> 		fuelListOutTrip			= null;

		List<TripSheetExpenseDto> 		renewalList				= null;

		List<TripSheetExpenseDto> 		tyreInvoiceList			= null;

		List<TripSheetExpenseDto> 		partInvoiceList			= null;

		List<TripSheetExpenseDto> 		batteryInvoiceList		= null;

		List<TripSheetExpenseDto> 		clothInvoiceList		= null;

		List<TripSheetExpenseDto> 		ureaInvoiceList			= null;

		List<TripSheetExpenseDto> 		fasttagList				= null;

		List<TripSheetExpenseDto> 		invoiceList				= null;

		List<TripSheetExpenseDto> 		vehicleExpList			= null;

		List<TripSheetExpenseDto> 		purchaseOrder			= null;

		List<TripSheetExpenseDto>       laundryInvoice			= null;

		List<TripSheetExpenseDto>       vehicleLaundryExp		= null;

		List<TripSheetExpenseDto>       driverBalanceList		= null;

		List<TripSheetExpenseDto>       dealerServiceEntryList	= null;

		HashMap<String, Object> 		configuration			= null;

		List<TripSheetExpenseDto>       tripAdavnceList         = null;
		

		try {

			finalList		= new ArrayList<>();

			invoiceList		= new ArrayList<>();

			vehicleExpList  = new ArrayList<>();

			laundryInvoice	= new ArrayList<TripSheetExpenseDto>();

			

			configuration			= companyConfigurationService.getCompanyConfiguration(valueObject.getInt("companyId"), 

														PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);

			Long defaultTallyCId 	= Long.parseLong(configuration.getOrDefault("defaultTallyCompanyId", 21)+"");

			

			TallyCompany	tallyCompany	= tallyCompanyDao.findById(defaultTallyCId).get();

			

		try {



			fuelListOutTrip			= fuelService.getFuelListWithoutApprovalForTally(valueObject.getString("fromDate"), valueObject.getString("toDate"), 

					valueObject.getInt("companyId"), valueObject.getString("selectedStr"), (String)configuration.getOrDefault("tallyHeaderForFuelExpense", "Diesel Expense"));

		

		} catch (Exception e) {

			System.err.println("error getting fuel.. "+e);

		}

			try {	

				expenseList				= tripSheetService.findAllTripSheetExpenseForTally(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), 

					valueObject.getString("selectedStr"), "Trip Expenses", defaultTallyCId);

				

			} catch (Exception e) {

				System.err.println("error getting expenseList.. "+e);

			}

			try {

				serviceEnteriesList		= serviceEntriesService.getServiceEntriesListForTallyImportATC(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), tallyCompany.getCompanyName());

				

			} catch (Exception e) {

				System.err.println("error getting serviceEnteriesList.. "+e);

			}

			

		//	workOrdersList			= workOrderService.getWorkOrdersListForTallyImport(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), tallyCompany.getCompanyName());

		try {

			tyreInvoiceList			= inventoryTyreService.getTyreInvoiceListForTallyImportATC(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), 

					valueObject.getString("selectedStr"), (String)configuration.getOrDefault("tyreExpenseLedgeName", "Motor Part Expense"));

		} catch (Exception e) {

			System.err.println("error getting tyreInvoiceList.. "+e);

		}

			try {

				partInvoiceList			= inventoryService.getPartInvoiceListForTallyImportATC(valueObject.getString("fromDate"), 

						valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));



			} catch (Exception e) {

				System.err.println("error getting partInvoiceList.. "+e);

			}

			try {

				batteryInvoiceList		= batteryInvoiceService.getBatteryInvoiceListForTallyImportATC(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			} catch (Exception e) {

				System.err.println("error getting batteryInvoiceList.. "+e);

			}

			try {

				clothInvoiceList		= clothInventoryService.getClothInvoiceListForTallyImportATC(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			} catch (Exception e) {

				System.err.println("error getting clothInvoiceList.. "+e);

			}

	

			try {

				ureaInvoiceList			= ureaInventoryService.getUreaInvoiceListForTallyImportATC(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			} catch (Exception e) {

				System.err.println("error getting ureaInvoiceList.. "+e);

			}

	

			try {

				vendorPaymentList		= vendorApprovalService.findVendorPaymentListATC(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			} catch (Exception e) {

				System.err.println("error getting vendorPaymentList.. "+e);

			}

	

			try {

				renewalList				= renewalReminderService.getRenewalListForTallyImportATC(valueObject.getString("fromDate"), 

						valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"),

						(boolean) configuration.getOrDefault("getBankPaymentDetails", false), (String)configuration.getOrDefault("renewalLedgerName", "TAX and INSURANCE A/C"));



			} catch (Exception e) {

				System.err.println("error getting renewalList.. "+e);

			}

	

			try {

				fasttagList				= tollExpensesDetailsService.getTollExpensesForTallyATC(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			} catch (Exception e) {

				System.err.println("error getting fasttagList.. "+e);

			}

	

			try {

				purchaseOrder			= purchaseOrdersService.getPurchaseListForTallyImportATC(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			} catch (Exception e) {

				System.err.println("error getting purchaseOrder.. "+e);

			}

	

			try {

				laundryInvoice			= clothInventoryService.getLaundryInvoiceListForTallyImportATC(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			} catch (Exception e) {

				System.err.println("error getting laundryInvoice.. "+e);

			}

		

			try {

				driverBalanceList		= tripSheetService.getDriverBalanceListForTally(valueObject.getInt("companyId"), valueObject.getString("fromDate"), valueObject.getString("toDate"),

						defaultTallyCId, (String)configuration.getOrDefault("driverBalanceDebitLedger", "Cash"), valueObject.getString("selectedStr"));



			} catch (Exception e) {

				System.err.println("error getting driverBalanceList.. "+e);

			}

		try {

			if((boolean) configuration.getOrDefault("selectDefaultTallyCompany", false)) {

				dealerServiceEntryList	= dealerServiceEntriesService.getDealerEntriesListForTallyImport(valueObject.getString("fromDate"), 

						valueObject.getString("toDate"), valueObject.getInt("companyId"), defaultTallyCId);

			}

		} catch (Exception e) {

			System.err.println("error getting dealerServiceEntryList.. "+e);

		}
		
		try {
			tripAdavnceList		= tripSheetService.findAllCashlessTripAdavnceList(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));
		} catch (Exception e) {
			System.err.println("error getting tripAdavnceList.. "+e);
		}
			

			//vehicleLaundryExp		= clothInventoryService.getVehicleLaundryExpListForTally(valueObject.getString("fromDate"), valueObject.getString("toDate"), valueObject.getInt("companyId"), valueObject.getString("selectedStr"));

			

			

			if(expenseList != null && !expenseList.isEmpty()) {

				finalList.addAll(expenseList);

			}

			if(fuelListOutTrip != null && !fuelListOutTrip.isEmpty()) {

				finalList.addAll(fuelListOutTrip);

			}

			

			  if(serviceEnteriesList != null && !serviceEnteriesList.isEmpty()) {

				  	finalList.addAll(serviceEnteriesList); 

			 } 

			  

				/*

				 * if(workOrdersList != null && !workOrdersList.isEmpty()) {

				 * vehicleExpList.addAll(workOrdersList); } if(vehicleLaundryExp != null &&

				 * !vehicleLaundryExp.isEmpty()) { vehicleExpList.addAll(vehicleLaundryExp); }

				 */

			  

				if(renewalList != null && !renewalList.isEmpty()) {

					finalList.addAll(renewalList);

				}

				if(tyreInvoiceList != null && !tyreInvoiceList.isEmpty()) {

					invoiceList.addAll(tyreInvoiceList);

				}

				if(partInvoiceList != null && !partInvoiceList.isEmpty()) {

					invoiceList.addAll(partInvoiceList);

				}

				if(batteryInvoiceList != null && !batteryInvoiceList.isEmpty()) {

					invoiceList.addAll(batteryInvoiceList);

				}

				if(clothInvoiceList != null && !clothInvoiceList.isEmpty()) {

					invoiceList.addAll(clothInvoiceList);

				}

				if(laundryInvoice != null && !laundryInvoice.isEmpty()) {

					invoiceList.addAll(laundryInvoice);

				}

				if(ureaInvoiceList != null && !ureaInvoiceList.isEmpty()) {

					invoiceList.addAll(ureaInvoiceList);

				}

				if(purchaseOrder != null && !purchaseOrder.isEmpty()) {

					invoiceList.addAll(purchaseOrder);

				}

				if(fasttagList != null && !fasttagList.isEmpty()) {

					finalList.addAll(fasttagList);

				}

				if(dealerServiceEntryList != null && !dealerServiceEntryList.isEmpty()) {

					finalList.addAll(dealerServiceEntryList);

				}
				if(tripAdavnceList != null && !tripAdavnceList.isEmpty()) {
					finalList.addAll(tripAdavnceList);
				}
				
		
			valueObject.put("expenseList", finalList);

			valueObject.put("vendorPaymentList", vendorPaymentList);

			valueObject.put("invoiceList", invoiceList);

			valueObject.put("vehicleExpList", vehicleExpList);

			valueObject.put("driverBalanceList", driverBalanceList);

			return valueObject;

		} catch (Exception e) {

			System.err.println("exception : "+e);

			throw e;

		}finally {

			fuelList	= null;

		}

	}

	

	@Override

	public ValueObject saveTallyCompany(ValueObject valueObject) throws Exception {

		CustomUserDetails			userDetails			= null;

		List<TallyCompany> 			tallyComapnyList	= null;

		List<TallyCompany> 			validate			= null;

		try {

			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			valueObject.put("userDetails", userDetails);

			

			TallyCompany	tally = tallyBL.saveTallyCompanyObject(valueObject);

			

			validate	=	validateTallyCompanyName(tally.getCompanyName(), userDetails.getCompany_id());

			if(validate != null && !validate.isEmpty()) {

				valueObject.put("alreadyExist", true);

				return valueObject;

			}

			

			TallyCompanyRepository.save(tally);

			valueObject.put("saved", true);

			

			int count			= 0;

			tallyComapnyList	= TallyCompanyRepository.getTallyCompanyListByCompanyId(userDetails.getCompany_id());

			if(tallyComapnyList != null && !tallyComapnyList.isEmpty())

				count	= tallyComapnyList.size();

			

			valueObject.put("tallyComapnyList", tallyComapnyList);

			valueObject.put("count", count);

			

			return valueObject;

		} catch (Exception e) {

			throw e;

		}finally {

			userDetails			= null;

			tallyComapnyList	= null;

		}

	}

	

	@Override

	public List<TallyCompany> validateTallyCompanyName(String companyName, Integer companyId) throws Exception {

		

		return TallyCompanyRepository.validateTallyCompanyName(companyName, companyId);

	}

	

	@Override

	public ValueObject getTallyCompanyList(ValueObject valueObject) throws Exception {

		CustomUserDetails			userDetails			= null;

		List<TallyCompany> 			tallyComapnyList	= null;

		try {

			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			int count	= 0;

			

			tallyComapnyList	= TallyCompanyRepository.getTallyCompanyListByCompanyId(userDetails.getCompany_id());

			if(tallyComapnyList != null && !tallyComapnyList.isEmpty())

				count	= tallyComapnyList.size();

			

			valueObject.put("tallyComapnyList", tallyComapnyList);

			valueObject.put("count", count);

			

			return valueObject;

			

		} catch (Exception e) {

			throw e;

		}finally {

			userDetails			= null;

			tallyComapnyList	= null;

		}

	}

	

	@Override

	public ValueObject getTallyCompanyListForSwingApp(ValueObject valueObject) throws Exception {

		List<TallyCompany>  	tallyComapnyList	= null;	

		List<String> 			companyList			= null;

		try {

			if(valueObject.getBoolean("permissionRequired")) {

				tallyComapnyList	= getTallyCompanyListByCompanyId(valueObject.getInt("companyId"), valueObject.getString("tallyCompanyIds"));



			}else {

				tallyComapnyList	= TallyCompanyRepository.getTallyCompanyListByCompanyId(valueObject.getInt("companyId"));

			}

			if(tallyComapnyList != null && !tallyComapnyList.isEmpty()) {

				companyList	= new ArrayList<String>();

				companyList.add("Please Select");

				for (TallyCompany tallyCompany : tallyComapnyList) {

					companyList.add(tallyCompany.getCompanyName());

				}

				valueObject.put("companyList", companyList);

			}

			return valueObject;

		} catch (Exception e) {

			throw e;

		}

	}

	

	@Override

	public List<TallyCompany> getTallyCompanyListForCompany(Integer companyId) throws Exception {

		

		return TallyCompanyRepository.getTallyCompanyListByCompanyId(companyId);

	}

	

	@Override

	public ValueObject getTallyCompanyListById(ValueObject valueObject) throws Exception {

		try {

			TallyCompany	tallyCompany	=	TallyCompanyRepository.findById(valueObject.getLong("tallyCompanyId")).get();

			valueObject.put("TallyCompany", tallyCompany);

			return valueObject;

		} catch (Exception e) {

			throw e;

		}

	}

	

	@Transactional

	@Override

	public ValueObject editTallyCompany(ValueObject valueObject) throws Exception {

		CustomUserDetails			userDetails			= null;

		List<TallyCompany> 			tallyComapnyList	= null;

		List<TallyCompany> 			validate			= null;

		long 						tallyCompanyId		= 0;

		String 						tallyCompanyName	= null;

		Timestamp 					lastUpdatedOn		= null;

		long						lastUpdatedBy		= 0;

		String					    description			= null;

		

		try {

			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			valueObject.put("userDetails", userDetails);

			

			tallyCompanyId		= valueObject.getLong("editTallyCompanyId");

			tallyCompanyName    = valueObject.getString("editTallyCompany").trim();

			lastUpdatedOn		= DateTimeUtility.getCurrentTimeStamp();

			lastUpdatedBy		= userDetails.getId();

			description     	= valueObject.getString("editDescription");

			

			TallyCompany	tallyCompany	=	TallyCompanyRepository.findById(tallyCompanyId).get();

			if(!tallyCompanyName.equalsIgnoreCase(tallyCompany.getCompanyName().trim())) {

				validate	=	validateTallyCompanyName(tallyCompanyName, userDetails.getCompany_id());

				

				if(validate != null && !validate.isEmpty()) {

					valueObject.put("alreadyExist", true);

					return valueObject;

				}

				

			} 

			

			entityManager.createQuery(

					" UPDATE TallyCompany AS TC SET TC.companyName = '"+tallyCompanyName+"', TC.description = '"+ description +"' , "

					+ " TC.lastUpdatedBy = '"+lastUpdatedBy+"', TC.lastUpdatedOn = '"+lastUpdatedOn+"' "

					+ " WHERE TC.tallyCompanyId = " + tallyCompanyId + " AND TC.companyId = "+userDetails.getCompany_id()

					+ " AND TC.markForDelete = 0  ")

					.executeUpdate();

			

			valueObject.put("saved", true);

			

			int count			= 0;

			tallyComapnyList	= TallyCompanyRepository.getTallyCompanyListByCompanyId(userDetails.getCompany_id());

			if(tallyComapnyList != null && !tallyComapnyList.isEmpty())

				count	= tallyComapnyList.size();

			

			valueObject.put("tallyComapnyList", tallyComapnyList);

			valueObject.put("count", count);

			

			return valueObject;

		} catch (Exception e) {

			throw e;

		}finally {

			userDetails			= null;

			tallyComapnyList	= null;

		}

	}

	

	@Override

	@Transactional

	public ValueObject deleteTallyCompany(ValueObject valueObject) throws Exception {

		try {

			TallyCompanyRepository.deleteTallyCompanyById(valueObject.getLong("tallyCompanyId"));

			valueObject.put("deleted", true);

			

			return valueObject;

		} catch (Exception e) {

			throw e;

		}

	}

	

	@Override

	public List<TallyCompany> searchByTallyCompany(String term, Integer companyId, Long userId) throws Exception {

		HashMap<String, Object>		configuration		= null;

		TypedQuery<Object[]> 		queryt 				= null;

		List<TallyCompany> Dtos = null;

		try {

			configuration	= companyConfigurationService.getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);

			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {

			if((boolean) configuration.get("tallyCompanyWisePermission")) {

				queryt = entityManager.createQuery(

						"SELECT TC.tallyCompanyId, TC.companyName FROM TallyCompany AS TC "

						+ " INNER JOIN TallyCompanyPermission TCP ON TCP.vg_per_id = TC.tallyCompanyId AND TCP.user_id = "+userId+" "

						+ " WHERE lower(TC.companyName) Like ('%" + term + "%') "

						+ " AND TC.companyId = "+companyId+" AND TC.markForDelete = 0 ",

						Object[].class);

			}else {

				queryt = entityManager.createQuery(

						"SELECT TC.tallyCompanyId, TC.companyName FROM TallyCompany AS TC "

						+ " WHERE lower(TC.companyName) Like ('%" + term + "%') "

						+ " AND TC.companyId = "+companyId+" AND TC.markForDelete = 0 ",

						Object[].class);

			}

			

			

			List<Object[]> results = queryt.getResultList();



			if (results != null && !results.isEmpty()) {

				Dtos = new ArrayList<TallyCompany>();

				TallyCompany dropdown = null;

				for (Object[] result : results) {

					dropdown = new TallyCompany();

					

					dropdown.setTallyCompanyId((long) result[0]);

					dropdown.setCompanyName((String) result[1]);



					Dtos.add(dropdown);

				}

			} else {

				return null;

			}

			}

			return Dtos;

		} catch (Exception e) {

			throw e;

		}finally {

			configuration		= null;

			queryt 				= null;

		}

		

	}



	@Override

	@Transactional

	public ValueObject saveTallyCompanyPermission(ValueObject valueObject) throws Exception {

		try {

				String[] tallyCompanyArr = valueObject.getString("tallyCompanyId").split(",");

			if (valueObject.getLong("userId",0) > 0 && valueObject.get("tallyCompanyId") != null) {

				permissionRepository.deleteTallyCompanyPermission(valueObject.getLong("userId",0));

				for (int i = 0; i <  tallyCompanyArr.length; i++) {

					TallyCompanyPermission vehicleGroup = new TallyCompanyPermission();

					vehicleGroup.setVg_per_id(Long.parseLong(tallyCompanyArr[i]+""));

					vehicleGroup.setUser_id(valueObject.getLong("userId",0));

					vehicleGroup.setCompanyId(valueObject.getInt("companyId",0));

					permissionRepository.save(vehicleGroup);

				}

			}



			

			return valueObject;

		} catch (Exception e) {

			throw e;

		}

	}

	

	

	@Override

	public List<TallyCompany> getTallyCompanyListByCompanyId(Integer companyId, String ids) throws Exception {

		TypedQuery<Object[]> query = null;



		query = entityManager.createQuery(

				" SELECT FL.tallyCompanyId, FL.companyName"

						+ " from TallyCompany FL " 

						+ " WHERE FL.companyId = "+companyId+" AND FL.tallyCompanyId IN ("+ids+") AND FL.markForDelete = 0 ",

						Object[].class);

		List<Object[]> results = query.getResultList();



		List<TallyCompany> Dtos = null;

		if (results != null && !results.isEmpty()) {

			Dtos = new ArrayList<TallyCompany>();

			TallyCompany select = null;

			for (Object[] vehicle : results) {



				select = new TallyCompany();

				select.setTallyCompanyId((long) vehicle[0]);

				select.setCompanyName((String) vehicle[1]);



				Dtos.add(select);

			}

		}

		return Dtos;



	}

	

	@Override

	public ValueObject getTallyComapnyListForMobile(ValueObject object) throws Exception {

		List<TallyCompany> 	tallyCmpnyList 				= null;

		List<TallyCompany> 	tallyCmpny					= null;

		CustomUserDetails	userDetails					= null;

		String 				term 						= null;

		try {

			tallyCmpnyList 	= new ArrayList<TallyCompany>();

			userDetails		= new CustomUserDetails();

			term 		    = object.getString("term");

			

			userDetails.setCompany_id(object.getInt("companyId"));

			userDetails.setId(object.getLong("userId"));



			tallyCmpny = searchByTallyCompany(term, userDetails.getCompany_id(), userDetails.getId());

			if (tallyCmpny != null && !tallyCmpny.isEmpty()) {

				for (TallyCompany tally : tallyCmpny) {



					TallyCompany Dto = new TallyCompany();

					Dto.setTallyCompanyId(tally.getTallyCompanyId());

					Dto.setCompanyName(tally.getCompanyName());



					tallyCmpnyList.add(Dto);

				}

			}



			object.put("TallyCmpnyList", tallyCmpnyList);



			return object;

			

		}catch(Exception e) {

			throw e;

		} finally {

			tallyCmpny 		 = null;

			tallyCmpnyList 	 = null;

			object  	 	 = null;

		}

	}

	

	@SuppressWarnings("unchecked")

	@Override

	@Transactional

	public ValueObject updateTallyIntegrationStatus(ValueObject valueObject) throws Exception {

		String 			tripExpenseIds 			= null;

		String 			tripSheetIds 			= null;

		String 			fuelIds 				= null;

		String 			serviceEntriesIds		= null;

		String 			renewalIds 				= null;

		String 			tyreInvoiceIds 			= null;

		String 			partInvoiceIds 			= null;

		String 			batteryInvoiceIds		= null;

		String 			ureaInvoiceIds 			= null;

		String 			clothInoviceIds 		= null;

		String 			vendorPaymentIds 		= null; 

		String			vehicleListIds			= null;

		String			dBalanceIds				= null;

		String			dseIds					= null;

		String 			tripSheetAdvIds			= null;
		HashMap<String, String>  insertedHM 	= null;

		try {

			 insertedHM 	= new HashMap<String, String>();

			 if(valueObject.containsKey("insertedHM")) {

				 insertedHM			= (HashMap<String, String>) valueObject.get("insertedHM");

				 

				 tripExpenseIds 	= Utility.removeLastComma(insertedHM.get("tripExpenseIds"));

				 tripSheetIds 		= Utility.removeLastComma(insertedHM.get("tripSheetIds"));

				 fuelIds 			= Utility.removeLastComma(insertedHM.get("fuelIds"));

				 serviceEntriesIds  = Utility.removeLastComma(insertedHM.get("serviceEntriesIds"));

				 renewalIds 		= Utility.removeLastComma(insertedHM.get("renewalIds"));

				 tyreInvoiceIds 	= Utility.removeLastComma(insertedHM.get("tyreInvoiceIds"));

				 partInvoiceIds	 	= Utility.removeLastComma(insertedHM.get("partInvoiceIds"));

				 batteryInvoiceIds 	= Utility.removeLastComma(insertedHM.get("batteryInvoiceIds"));

				 ureaInvoiceIds 	= Utility.removeLastComma(insertedHM.get("ureaInvoiceIds"));

				 clothInoviceIds 	= Utility.removeLastComma(insertedHM.get("clothInoviceIds"));

				 vendorPaymentIds 	= Utility.removeLastComma(insertedHM.get("vendorPaymentIds"));

				 vehicleListIds 	= Utility.removeLastComma(insertedHM.get("vehicleListIds"));

				 dBalanceIds		= Utility.removeLastComma(insertedHM.get("tripIdsForDBal"));

				 dseIds				= Utility.removeLastComma(insertedHM.get("dseIds"));

				 tripSheetAdvIds	= Utility.removeLastComma(insertedHM.get("tripSheetAdvIds"));
				 

				 if(tripExpenseIds != null && !tripExpenseIds.trim().equalsIgnoreCase("")) {

					 updateTripSheetExpenseTallyStatus(tripExpenseIds);

				 }

				 if(tripSheetIds != null && !tripSheetIds.trim().equalsIgnoreCase("")) {

					 updateFastTagTallyStatus(tripSheetIds);

				 }

				 if(fuelIds != null && !fuelIds.trim().equalsIgnoreCase("")) {

					 updateFuelTallyStatus(fuelIds);

				 }

				 if(serviceEntriesIds != null && !serviceEntriesIds.trim().equalsIgnoreCase("")) {

					 updateServiceEntiresTallyStatus(serviceEntriesIds);

				 }

				 if(renewalIds != null && !renewalIds.trim().equalsIgnoreCase("")) {

					 updateRenewalEntiresTallyStatus(renewalIds);

				 }

				 if(tyreInvoiceIds != null && !tyreInvoiceIds.trim().equalsIgnoreCase("")) {

					 updateTyreInvoiceTallyStatus(tyreInvoiceIds);

				 }

				 if(partInvoiceIds != null && !partInvoiceIds.trim().equalsIgnoreCase("")) {

					 updatePartInvoiceTallyStatus(partInvoiceIds);

				 }

				 if(batteryInvoiceIds != null && !batteryInvoiceIds.trim().equalsIgnoreCase("")) {

					 updateBatteryInvoiceTallyStatus(batteryInvoiceIds);

				 }

				 if(ureaInvoiceIds != null && !ureaInvoiceIds.trim().equalsIgnoreCase("")) {

					 updateUreaInvoiceTallyStatus(ureaInvoiceIds);

				 }

				 if(clothInoviceIds != null && !clothInoviceIds.trim().equalsIgnoreCase("")) {

					 updateClothInvoiceTallyStatus(clothInoviceIds);

				 }

				 if(vendorPaymentIds != null && !vendorPaymentIds.trim().equalsIgnoreCase("")) {

					 updateVendorPaymentTallyStatus(vendorPaymentIds);

				 }

				 if(vehicleListIds != null && !vehicleListIds.trim().equalsIgnoreCase("")) {

					 updateWorkOrderPaymentTallyStatus(vehicleListIds);

				 }

				 

				 System.err.println("dBalanceIds : "+dBalanceIds);

				 System.err.println("dseIds : "+dseIds);

				 

				 if(dBalanceIds != null && !dBalanceIds.trim().equalsIgnoreCase("")) {

					 updateTripDriverBalanceStatus(dBalanceIds);

				 }

				 if(dseIds != null && !dseIds.trim().equalsIgnoreCase("")) {

					 updateDealerServiceEntryStatus(dseIds);

				 }
				 if(tripSheetAdvIds != null && !tripSheetAdvIds.trim().equalsIgnoreCase("")) {
					 updateTripSheetAdvanceTallyStatus(tripSheetAdvIds);
				 }
			 }

			 

			return valueObject;

		} catch (Exception e) {

			throw e;

		}

	}

	

	@Transactional

	private void updateTripSheetExpenseTallyStatus(String ids) {

		try {



			entityManager.createQuery(

					" UPDATE TripSheetExpense SET isPendingForTally = 1  "

					+ " WHERE tripExpenseID IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}

	

	@Transactional

	private void updateFastTagTallyStatus(String ids) {

		try {



			entityManager.createQuery(

					" UPDATE TollExpensesDetails SET isPendingForTally = 1  "

					+ " WHERE tripSheetId IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}

	@Transactional

	private void updateFuelTallyStatus(String ids) {

		try {

			entityManager.createQuery(

					" UPDATE Fuel SET isPendingForTally = 1  "

					+ " WHERE fuel_id IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}

	@Transactional

	private void updateServiceEntiresTallyStatus(String ids) {

		try {

			entityManager.createQuery(

					" UPDATE ServiceEntries SET isPendingForTally = 1  "

					+ " WHERE serviceEntries_id IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}

	

	@Transactional

	private void updateRenewalEntiresTallyStatus(String ids) {

		try {

			entityManager.createQuery(

					" UPDATE RenewalReminder SET isPendingForTally = 1  "

					+ " WHERE renewal_id IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}

	

	@Transactional

	private void updateTyreInvoiceTallyStatus(String ids) {

		try {

			entityManager.createQuery(

					" UPDATE InventoryTyreInvoice SET isPendingForTally = 1  "

					+ " WHERE ITYRE_ID IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}

	

	@Transactional

	private void updatePartInvoiceTallyStatus(String ids) {

		try {

			entityManager.createQuery(

					" UPDATE PartInvoice SET isPendingForTally = 1  "

					+ " WHERE partInvoiceId IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}

	

	@Transactional

	private void updateBatteryInvoiceTallyStatus(String ids) {

		try {

			entityManager.createQuery(

					" UPDATE BatteryInvoice SET isPendingForTally = 1  "

					+ " WHERE batteryInvoiceId IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}

	

	@Transactional

	private void updateClothInvoiceTallyStatus(String ids) {

		try {

			entityManager.createQuery(

					" UPDATE ClothInvoice SET isPendingForTally = 1  "

					+ " WHERE clothInvoiceId IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}

	

	@Transactional

	private void updateUreaInvoiceTallyStatus(String ids) {

		try {

			entityManager.createQuery(

					" UPDATE UreaInvoice SET isPendingForTally = 1  "

					+ " WHERE ureaInvoiceId IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}

	

	@Transactional

	private void updateVendorPaymentTallyStatus(String ids) {

		try {

			entityManager.createQuery(

					" UPDATE VendorApproval SET isPendingForTally = 1  "

					+ " WHERE approvalId IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}

	

	@Transactional

	private void updateWorkOrderPaymentTallyStatus(String ids) {

		try {

			entityManager.createQuery(

					" UPDATE WorkOrders SET isPendingForTally = 1  "

					+ " WHERE workorders_id IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}

	

	@Transactional

	private void updateTripDriverBalanceStatus(String ids) {

		try {

			entityManager.createQuery(

					" UPDATE TripSheet SET isPendingForTally = 1  "

					+ " WHERE tripSheetID IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}

	

	@Transactional

	private void updateDealerServiceEntryStatus(String ids) {

		try {

			entityManager.createQuery(

					" UPDATE DealerServiceEntries SET isPendingForTally = 1  "

					+ " WHERE dealerServiceEntriesId IN ("+ids+") ")

					.executeUpdate();

			

		} catch (Exception e) {

			

		}

	}
	@Transactional
	private void updateTripSheetAdvanceTallyStatus(String ids) {
		try {

			entityManager.createQuery(
					" UPDATE TripSheetAdvance SET isPendingForTally = 1  "
					+ " WHERE 	tripAdvanceID IN ("+ids+") ")
					.executeUpdate();
			
		} catch (Exception e) {
			
		}
	}
}

