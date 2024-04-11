package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.CashBookStatus;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripCollectionConfigurationConstant;
import org.fleetopgroup.constant.TripDailySheetCollectionConfiguration;
import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.persistence.bl.CashBookBL;
import org.fleetopgroup.persistence.bl.TripDailyBL;
import org.fleetopgroup.persistence.dto.CashBookDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.dto.TripDailyGroupCollectionDto;
import org.fleetopgroup.persistence.dto.TripDailyRouteSheetDto;
import org.fleetopgroup.persistence.dto.TripDailySheetDto;
import org.fleetopgroup.persistence.dto.TripDailyTimeIncomeDto;
import org.fleetopgroup.persistence.model.CashBookBalance;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.model.TripDailyAllGroupDay;
import org.fleetopgroup.persistence.model.TripRoute;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.service.CompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripRouteService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TripDailyCollectionReportController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ITripDailySheetService tripDailySheetService;
	@Autowired
	private IUserProfileService userProfileService;
	@Autowired
	private ICashBookService CashBookService;

	@Autowired
	private ICashBookNameService cashBookNameService;

	@Autowired
	private IVehicleGroupService vehicleGroupService;

	@Autowired
	private ITripRouteService tripRouteService;

	@Autowired
	CompanyConfigurationService companyConfigurationService;

	CashBookBL CBBL = new CashBookBL();

	TripDailyBL TDBL = new TripDailyBL();

	SimpleDateFormat dateFormatonlyDate = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
	DecimalFormat AMOUNTFORMAT = new DecimalFormat("##,##,##0");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping("/TDR")
	public ModelAndView TripDailyReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("TDR", model);
	}

	// Note : This Controller DailyTripReport and Cash Book Details
	@RequestMapping(value = "/DailyTripDailyCashbookReport", method = RequestMethod.POST)
	public ModelAndView DailyTripDailyCashbookReport(
			@ModelAttribute("command") @RequestParam("TRIP_DATE") final String TRIP_DATERANGE,
			@RequestParam("VEHICLEGROUP") final long VEHICLEGROUP_ID, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		VehicleGroup group = null;
		try {
			if (TRIP_DATERANGE != "" && TRIP_DATERANGE != null && VEHICLEGROUP_ID != 0) {

				java.util.Date date = dateFormatSQL.parse(TRIP_DATERANGE);
				java.sql.Date fromDate = new Date(date.getTime());
				try {
					List<TripDailyRouteSheetDto> GroupSheet = tripDailySheetService
							.List_TripDailyRouteSheet_closeDate_to_get_details(TRIP_DATERANGE, VEHICLEGROUP_ID,
									TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
					if (GroupSheet != null && !GroupSheet.isEmpty()) {

						List<TripDailySheetDto> CollectionSheet = tripDailySheetService
								.List_TripDailySheet_closeDate_with_Group(TRIP_DATERANGE, VEHICLEGROUP_ID,
										TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());

						model.put("TDRoute", TDBL.CloseDaily_tripDailyRoute_Sheet(GroupSheet, CollectionSheet));
						
						List<TripDailySheetDto> TripCollection = tripDailySheetService.list_Close_TripDailySheet(TRIP_DATERANGE, userDetails);
						try {
							model.put("TDGroupCol", TDBL.CLOSED_DAILY_GROUP_SHEET(tripDailySheetService
									.GET_TripDailyGroupCollection_CloseDate(fromDate, VEHICLEGROUP_ID,
											TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id()),TripCollection));

						} catch (Exception e) {
							e.printStackTrace();
						}

					}
					model.put("GROUP_NAME_ID", VEHICLEGROUP_ID);
					group = vehicleGroupService.getVehicleGroupByID(VEHICLEGROUP_ID);
					model.put("SEARCHDATE", TRIP_DATERANGE);
					model.put("GROUP_NAME", group.getvGroup());

					model.put("TRIPCOL_DATE", dateFormatonlyDate.format(date));

					Date yesterday = new Date(date.getTime() - 1 * 24 * 3600 * 1000);
					model.put("YESTERDAY", dateFormatSQL.format(yesterday));
					Date tomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
					model.put("TOMORROW", dateFormatSQL.format(tomorrow));

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

				try {
					// fuel date converted String to date Format
					CashBookName cashBookName = null;
					if (TRIP_DATERANGE != null) {
						java.util.Date date3 = dateFormatSQL.parse(TRIP_DATERANGE);
						java.sql.Date cashbookdate = new Date(date3.getTime());

						Double OpeningBalanceTotal = 0.0, CloseingBalanceTotal = 0.0;
						int n = 1;
						/*
						 * if (CASH_BOOK_NO.equalsIgnoreCase("MAIN-CASH-BOOK")) {
						 */
						while (n < 3) {
							// this check Before Begin Opening Balances
							Date openingBalanceDate = new Date(date.getTime() - n * 24 * 3600 * 1000);
							// System.out.println("datBefore = " + n + " " +
							// openingBalanceDate);
							cashBookName = cashBookNameService.getCashBookByVehicleGroupId(group.getGid(),
									userDetails.getCompany_id());
							if (cashBookName != null) {
								CashBookBalance OpeningBalance = CashBookService.Validate_Last_DayCashBook_CloseOrNot(
										openingBalanceDate, cashBookName.getNAMEID(),
										CashBookStatus.CASH_BOOK_STATUS_CLOSED, userDetails.getCompany_id());
								if (OpeningBalance != null) {
									// Assign Opening Balance Total
									OpeningBalanceTotal = OpeningBalance.getCASH_ALL_BALANCE();

									String TotalOpenBalance = AMOUNTFORMAT.format(round(OpeningBalanceTotal, 2));
									model.put("OpenBalance", TotalOpenBalance);

									// System.out.println(TotalOpenBalance);
									break;
								}
							}

							n++;
						}
						/* } */
						// When Cash Book Approval the Date only show the
						// Details
						List<CashBookDto> Credit_Debit = CashBookService.Report_CashBook_All(cashBookName.getNAMEID(),
								cashbookdate, userDetails.getCompany_id());

						model.put("CASHBOOK_NAME", group.getvGroup());
						model.put("CASHBOOK_DATE", dateFormatonlyDate.format(date));

						model.put("cashbook", CBBL.prepare_CashBookDto_ListDto(Credit_Debit));

						CashBookBalance CloseBalance = CashBookService.Validate_CashBookBalance_value(cashbookdate,
								cashBookName.getNAMEID(), userDetails.getCompany_id());
						if (CloseBalance != null) {
							if (CloseBalance.getCASH_STATUS_ID() == CashBookStatus.CASH_BOOK_STATUS_CLOSED) {

								// Assign Opening Balance Total
								CloseingBalanceTotal = CloseBalance.getCASH_ALL_BALANCE();

								String TotalCloseBalance = AMOUNTFORMAT.format(round(CloseingBalanceTotal, 2));
								model.put("Balance", TotalCloseBalance);

								model.put("CashBookStatus",
										CashBookStatus.getCashBookStatusName(CloseBalance.getCASH_STATUS_ID()));

								ConvertIntegerToWord amount = new ConvertIntegerToWord();
								Double AmountWorld = round(CloseingBalanceTotal, 2);
								int value = Integer.valueOf(AmountWorld.intValue());

								String AmountWORLD = amount.convertNumberToWords(value);
								model.put("BalanceWorld", AmountWORLD);
							} else {

								// Assign Opening Balance Total
								CloseingBalanceTotal = OpeningBalanceTotal + CloseBalance.getCASH_DAY_BALANCE();

								String TotalCloseBalance = AMOUNTFORMAT.format(round(CloseingBalanceTotal, 2));
								model.put("Balance", TotalCloseBalance);

								model.put("CashBookStatus",
										CashBookStatus.getCashBookStatusName(CloseBalance.getCASH_STATUS_ID()));

								ConvertIntegerToWord amount = new ConvertIntegerToWord();
								Double AmountWorld = round(CloseingBalanceTotal, 2);
								int value = Integer.valueOf(AmountWorld.intValue());

								String AmountWORLD = amount.convertNumberToWords(value);
								model.put("BalanceWorld", AmountWORLD);
							}

							String TotalShowDebit = AMOUNTFORMAT.format(round(CloseBalance.getCASH_DEBIT(), 2));
							model.put("DebitTotal", TotalShowDebit);

							Double CASH_CREDIT = OpeningBalanceTotal + CloseBalance.getCASH_CREDIT();
							String TotalShowCredit = AMOUNTFORMAT.format(round(CASH_CREDIT, 2));
							model.put("CreditTotal", TotalShowCredit);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Daily_TripDaily_CashBook_Report", model);
	}

	@RequestMapping("/DailyTripDailyReport")
	public ModelAndView DailyTripDailyReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Daily_Trip_Daily_Report", model);
	}

	@RequestMapping(value = "/DailyTripDailyReport", method = RequestMethod.POST)
	public ModelAndView DailyTripDailyReport(
			@ModelAttribute("command") @RequestParam("TRIP_DATE") final String TRIP_DATERANGE,
			@RequestParam("VEHICLEGROUP") final long VEHICLEGROUP_ID, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<TripDailyRouteSheetDto> depotWiseDailyTripCollectionReport=null; //No Record Found Validation  Logic Y
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {
			if (TRIP_DATERANGE != "" && TRIP_DATERANGE != null && VEHICLEGROUP_ID != 0) {

				java.util.Date date = dateFormatSQL.parse(TRIP_DATERANGE);
				java.sql.Date fromDate = new Date(date.getTime());
				try {
					List<TripDailyRouteSheetDto> GroupSheet = tripDailySheetService
							.List_TripDailyRouteSheet_closeDate_to_get_details(TRIP_DATERANGE, VEHICLEGROUP_ID,
									TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());
					
					if (GroupSheet != null && !GroupSheet.isEmpty()) {
						List<TripDailySheetDto> CollectionSheet = tripDailySheetService
								.List_TripDailySheet_closeDate_with_Group(TRIP_DATERANGE, VEHICLEGROUP_ID,
										TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id());

						depotWiseDailyTripCollectionReport= TDBL.CloseDaily_tripDailyRoute_Sheet(GroupSheet, CollectionSheet);
						
						
						model.put("TDRoute", depotWiseDailyTripCollectionReport);
						//model.put("TDRoute", TDBL.CloseDaily_tripDailyRoute_Sheet(GroupSheet, CollectionSheet)); //Original Code
						List<TripDailySheetDto> TripCollection = tripDailySheetService.list_Close_TripDailySheet(TRIP_DATERANGE, userDetails);
						try {
							model.put("TDGroupCol", TDBL.CLOSED_DAILY_GROUP_SHEET(tripDailySheetService
									.GET_TripDailyGroupCollection_CloseDate(fromDate, VEHICLEGROUP_ID,
											TripDailySheetStatus.TRIP_STATUS_CLOSED, userDetails.getCompany_id()),TripCollection));

						} catch (Exception e) {
							e.printStackTrace();
						}

					}									
					model.put("GROUP_NAME_ID", VEHICLEGROUP_ID);
					model.put("SEARCHDATE", TRIP_DATERANGE);
					model.put("GROUP_NAME", vehicleGroupService.getVehicleGroupByID(VEHICLEGROUP_ID).getvGroup());

					model.put("TRIPCOL_DATE", dateFormatonlyDate.format(date));

					Date yesterday = new Date(date.getTime() - 1 * 24 * 3600 * 1000);
					model.put("YESTERDAY", dateFormatSQL.format(yesterday));
					Date tomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
					model.put("TOMORROW", dateFormatSQL.format(tomorrow));
					
					if(depotWiseDailyTripCollectionReport == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Daily_Trip_Daily_Report", model);
					}	

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Daily_Trip_Daily_Report", model);
	}

	@RequestMapping("/DailyTripDailyTimeReport")
	public ModelAndView DailyTripDailyTimeReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		
		return new ModelAndView("Daily_Trip_Daily_TIME_Report", model);
	}

	@RequestMapping(value = "/DailyTripDailyTimeReport", method = RequestMethod.POST)
	public ModelAndView DailyTripDailyTimeReport(
			@ModelAttribute("command") @RequestParam("VEHICLE_ID") final Integer VEHICLE_ID,
			@RequestParam("VEHICLE_TC_DATERAGE") final String TRIP_DATERANGE, final HttpServletRequest request) {

			Map<String, Object> 					model 					= new HashMap<String, Object>();
			HashMap<String, Object> 				configuration 			= null;
			CustomUserDetails 						userDetails 			= null;
			String[] 								From_TO_DateRange 		= null;
			String 									dateRangeFrom 			= "";
			String 									dateRangeTo 			= "";
			HashMap<String, TripDailySheetDto> 		fuelAmountHm 			= null;
			List<TripDailyTimeIncomeDto> 			GetFixedTypeIncomeName	= null;
			String 									TDINCOME_NAME 			= "";
			String 									TDINCOME_COLUMN 		= "";
			List<Object[]> 							FixedTypeSheet			= null;
			List<TripDailySheetDto> 				Dtos 					= null;
			String 									VEHICLE_NAME 			= "";
			TripDailySheetDto 						list 					= null;
			List<Object[]> 							FixedTypeSheetColumTOTAL= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIP_COLLECTION_CONFIG);
			model.put("configuration", configuration);
			if (TRIP_DATERANGE != "" && TRIP_DATERANGE != null && VEHICLE_ID != null) {
				try {

					From_TO_DateRange 	= TRIP_DATERANGE.split(" to ");
					dateRangeFrom 		= From_TO_DateRange[0];
					dateRangeTo 		= From_TO_DateRange[1];

					model.put("SEARCHDATE", TRIP_DATERANGE);
					} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}
				try {
					configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.TRIP_COLLECTION_CONFIG);
					GetFixedTypeIncomeName = tripDailySheetService.List_OF_TripDailySheet_get_Fixed_TIME_Income_Name(VEHICLE_ID, dateRangeFrom, dateRangeTo,userDetails.getCompany_id());
					if (GetFixedTypeIncomeName != null && !GetFixedTypeIncomeName.isEmpty()) {
						for (TripDailyTimeIncomeDto tripDailyIncome : GetFixedTypeIncomeName) {
								TDINCOME_NAME += "<th>" + tripDailyIncome.getIncomeName() + "</th>";
						}
						model.put("TDINCOME_NAME", TDINCOME_NAME);
						FixedTypeSheet = tripDailySheetService.List_OF_Time_WISE_Report(VEHICLE_ID,
								dateRangeFrom, dateRangeTo, GetFixedTypeIncomeName, userDetails.getCompany_id());

						if ((boolean) configuration.get(TripDailySheetCollectionConfiguration.SHOW_DIESEL_AMOUNT)) {
							fuelAmountHm = tripDailySheetService.getDieselAmountOfTripSheetReport(VEHICLE_ID,
									dateRangeFrom, dateRangeTo, userDetails.getCompany_id());

							if (fuelAmountHm != null && fuelAmountHm.size() > 0) {

								Double totalDieselAmount = 0.0;
								for (Map.Entry<String, TripDailySheetDto> entry : fuelAmountHm.entrySet()) {
									totalDieselAmount += entry.getValue().getTRIP_DIESEL_AMOUNT();
								}
								model.put("totalDieselAmount", round(totalDieselAmount, 2));
							}
						}
						
						if (FixedTypeSheet != null && !FixedTypeSheet.isEmpty()) {
							Dtos = new ArrayList<TripDailySheetDto>();
							for (Object[] result : FixedTypeSheet) {
								list = new TripDailySheetDto();
								list.setTRIPDAILYID((Long) result[0]);
								list.setVEHICLE_REGISTRATION((String) result[1]);
								VEHICLE_NAME = ((String) result[1]);
								if (result[2] != null) {
									list.setTRIP_OPEN_DATE(dateFormatonlyDate.format((java.util.Date) result[2]));
								}

								list.setTRIP_USAGE_KM((Integer) result[3]);
								list.setTRIP_DIESEL((Double) result[4]);
								// list.setTRIP_DIESEL_AMOUNT((Double) result[5]);
								list.setTRIP_DIESELKMPL((Double) result[5]);
								list.setTOTAL_INCOME((Double) result[6]);
								list.setTOTAL_EXPENSE((Double) result[7]);
								list.setTOTAL_BALANCE((Double) result[8]);
								list.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[9]));
								// list.setTRIPDAILYNUMBER((Long) result[10]);
								if ((boolean) configuration.get(TripDailySheetCollectionConfiguration.SHOW_DIESEL_AMOUNT)) {
									if (fuelAmountHm.get(list.getTRIPDAILYID() + "") != null) {
										list.setTRIP_DIESEL_AMOUNT(
												(fuelAmountHm.get(list.getTRIPDAILYID() + "")).getTRIP_DIESEL_AMOUNT());
									} else {
										list.setTRIP_DIESEL_AMOUNT(0.0);
									}
								}

								String COLUMN_TIP_VALUE = "";
								short ColumnResult = 10;
								for (@SuppressWarnings("unused")
								TripDailyTimeIncomeDto tripDailyIncome : GetFixedTypeIncomeName) {
									COLUMN_TIP_VALUE += "<td>" + (Double) result[ColumnResult] + "</td>";
									ColumnResult++;
								}
								list.setCREATEDBY(COLUMN_TIP_VALUE);
								ColumnResult = 10;
								Dtos.add(list);				
							}
						}
						if(Dtos == null || Dtos.isEmpty()){
							model.put("NotFound", true); 
							//return new ModelAndView("Daily_Trip_Daily_TIME_Report", model);
						}else {
						}
						model.put("TDRoute", Dtos); //Original Code Before No Record Found
						model.put("VEHICLE_NAME", VEHICLE_NAME);

						Double[] totalGroupSheet = TDBL.Total_TripDaily_collection_Time_Report(Dtos);
						if (totalGroupSheet != null) {
							String TRIP_USAGE_KM = AMOUNTFORMAT.format(totalGroupSheet[0]);
							model.put("TRIP_USAGE_KM", TRIP_USAGE_KM);

							String TRIP_DIESEL = AMOUNTFORMAT.format(totalGroupSheet[1]);
							model.put("TRIP_DIESEL", TRIP_DIESEL);

							String TRIP_DIESELKMPL = AMOUNTFORMAT.format(totalGroupSheet[2]);
							model.put("TRIP_DIESELKMPL", TRIP_DIESELKMPL);

							String TOTAL_BALANCE = AMOUNTFORMAT.format(totalGroupSheet[5]);
							model.put("TOTAL_BALANCE", TOTAL_BALANCE);
							
							String TOTAL_INCOME = AMOUNTFORMAT.format(totalGroupSheet[6]);
							model.put("TOTAL_INCOME", TOTAL_INCOME);

							String TOTAL_EXPENSE = AMOUNTFORMAT.format(totalGroupSheet[7]);
							model.put("TOTAL_EXPENSE", TOTAL_EXPENSE);

							
							String TRIP_DIESEL_AMOUNT = AMOUNTFORMAT.format(totalGroupSheet[10]);
							model.put("TRIP_DIESEL_AMOUNT", TRIP_DIESEL_AMOUNT);

						}

						FixedTypeSheetColumTOTAL = tripDailySheetService.List_OF_Time_WISE_COLUMN_Report(
								VEHICLE_ID, dateRangeFrom, dateRangeTo, GetFixedTypeIncomeName,
								userDetails.getCompany_id());

						if (FixedTypeSheetColumTOTAL != null && !FixedTypeSheetColumTOTAL.isEmpty()) {

							for (Object[] result_column : FixedTypeSheetColumTOTAL) {
								short ColumnResult = 1;
								for (@SuppressWarnings("unused")
								TripDailyTimeIncomeDto tripDailyIncome : GetFixedTypeIncomeName) {
									TDINCOME_COLUMN += "<td>" + (Double) result_column[ColumnResult] + "</td>";
									ColumnResult++;
								}

							}
						}
						model.put("TDINCOME_COLUMN", TDINCOME_COLUMN);

					}else {
						model.put("NotFound", true);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {
			LOGGER.error("Error ",e);
			e.printStackTrace();
		}

		return new ModelAndView("Daily_Trip_Daily_TIME_Report", model);
	}

	@RequestMapping("/TripDailyRouteTimeReport")
	public ModelAndView TripDailyRouteTimeReport() {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		HashMap<String, Object> configuration = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),
					PropertyFileConstants.TRIP_COLLECTION_CONFIG);

			model.put("configuration", configuration);
		} catch (Exception e) {
			LOGGER.error("TripSheet Page:", e);
			e.printStackTrace();
		} finally {

		}
		return new ModelAndView("Daily_Trip_Daily_ROUTETIME_Report", model);
	}

	@RequestMapping("/DailyMonthlyYearlyTripCollectionReport")
	public ModelAndView DailyMonthlyYearlyTripCollectionReport() {
		Map<String, Object> 		model 					= new HashMap<String, Object>();
		HashMap<String, Object>   	configuration			= null;
		CustomUserDetails			userDetails 			= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG);
			model.put(TripCollectionConfigurationConstant.HIDE_WT,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG).getOrDefault(TripCollectionConfigurationConstant.HIDE_WT, false));
			model.put(TripCollectionConfigurationConstant.HIDE_EPK,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG).getOrDefault(TripCollectionConfigurationConstant.HIDE_EPK, false));
			model.put(TripCollectionConfigurationConstant.HIDE_OT,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG).getOrDefault(TripCollectionConfigurationConstant.HIDE_OT, false));
			model.put(TripCollectionConfigurationConstant.NET_COLLECTION,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG).getOrDefault(TripCollectionConfigurationConstant.NET_COLLECTION, false));
			model.put(TripCollectionConfigurationConstant.TOTAL_BALANCE,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG).getOrDefault(TripCollectionConfigurationConstant.TOTAL_BALANCE, false));
			model.put(TripDailySheetCollectionConfiguration.SHOW_DIESEL_AMOUNT,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG).getOrDefault(TripDailySheetCollectionConfiguration.SHOW_DIESEL_AMOUNT, false));
			model.put(TripDailySheetCollectionConfiguration.TOTAL_NET_COLLECTION_FORMULA,companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG).getOrDefault(TripDailySheetCollectionConfiguration.TOTAL_NET_COLLECTION_FORMULA, false));
			
			model.put("configuration", configuration.get(TripDailySheetCollectionConfiguration.SHOW_CHALO_DETAILS));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return new ModelAndView("DailyMonthlyYearlyTripCollectionReport", model);
	}

	@RequestMapping("/DailyTripCollectionDailyCashbookReport")
	public ModelAndView DailyTripCollectionDailyCashbookReport() {
		Map<String, Object> 		model 					= new HashMap<String, Object>();
		HashMap<String, Object>   	configuration			= null;
		CustomUserDetails			userDetails 			= null;
		
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG);
			
			model.put("configuration", configuration.get(TripDailySheetCollectionConfiguration.SHOW_CHALO_DETAILS));
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}

		return new ModelAndView("DailyTripCollectionDailyCashbookReport", model);
	}

	@RequestMapping("/DepotWiseDailyTripCollectionStatusReport")
	public ModelAndView DepotWiseDailyTripCollectionStatusReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("DepotWiseDailyTripCollectionStatusReport", model);
	}

	@RequestMapping(value = "/DailyTripDailyRouteTimeReport", method = RequestMethod.GET)
	public ModelAndView DriverReport(@RequestParam("ROUTE_ID") final Integer ROUTE_ID,
			@RequestParam("VEHICLEGROUP") final long VEHICLEGROUP,
			@RequestParam("SUB_ROUTE_ID") final Integer SUB_ROUTE_ID,
			@RequestParam("FROM_DATERANGE") final String TRIP_DATERANGE,
			@RequestParam("TO_DATERANGE") final String TO_DATERANGE, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		/* HashMap<String, Object> configuration = null; */
		try {

			/*
			 * userDetails = (CustomUserDetails)
			 * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 * configuration =
			 * companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id
			 * (), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			 * model.put("configuration", configuration);
			 */
			model = getDailyTripDailyRouteTimeReportDetails(SUB_ROUTE_ID, TRIP_DATERANGE, TO_DATERANGE, ROUTE_ID,
					VEHICLEGROUP);
			if (model.containsKey("emptyNotRange")) {
				return new ModelAndView("redirect:/Report", model);
			}
		} catch (Exception e) {
		}
		return new ModelAndView("Daily_Trip_Daily_ROUTETIME_Report", model);
	}

	@RequestMapping(value = "/DailyTripDailyRouteTimeReport2", method = RequestMethod.POST)
	public ModelAndView DailyTripDailyRouteTimeReport2(@RequestParam("SUB_ROUTE_ID1") final Integer SUB_ROUTE_ID1,
			@RequestParam("FROM_DATERANGE") final String TRIP_DATERANGE,
			@RequestParam("TO_DATERANGE") final String TO_DATERANGE, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		/*
		 * CustomUserDetails userDetails = null; HashMap<String, Object> configuration =
		 * null;
		 */
		try {

			/*
			 * userDetails = (CustomUserDetails)
			 * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 * configuration =
			 * companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id
			 * (), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			 * model.put("configuration", configuration);
			 */
			TripRoute tripRoute = tripRouteService.getTripRoute(SUB_ROUTE_ID1);

			model = getDailyTripDailyRouteTimeReportDetails(SUB_ROUTE_ID1, TRIP_DATERANGE, TO_DATERANGE,
					tripRoute.getMainRouteId(), tripRoute.getVehicleGroupId());
			if (model.containsKey("emptyNotRange")) {
				return new ModelAndView("redirect:/Report", model);
			}
		} catch (Exception e) {
			
		}
		return new ModelAndView("Daily_Trip_Daily_ROUTETIME_Report", model);
	}

	@RequestMapping(value = "/DailyTripDailyRouteTimeReport", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView DailyTripDailyRouteTimeReport(@RequestParam("ROUTE_ID") final Integer ROUTE_ID,
			@RequestParam("VEHICLEGROUP") final long VEHICLEGROUP,
			@RequestParam("SUB_ROUTE_ID") final Integer SUB_ROUTE_ID,
			@RequestParam("FROM_DATERANGE") final String TRIP_DATERANGE,
			@RequestParam("TO_DATERANGE") final String TO_DATERANGE, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		/*
		 * CustomUserDetails userDetails = null; HashMap<String, Object> configuration =
		 * null;
		 */
		try {

			/*
			 * userDetails = (CustomUserDetails)
			 * SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			 * configuration =
			 * companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id
			 * (), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			 * model.put("configuration", configuration);
			 */
			model = getDailyTripDailyRouteTimeReportDetails(SUB_ROUTE_ID, TRIP_DATERANGE, TO_DATERANGE, ROUTE_ID,
					VEHICLEGROUP);
			if (model.containsKey("emptyNotRange")) {
				return new ModelAndView("redirect:/Report", model);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Daily_Trip_Daily_ROUTETIME_Report", model);
	}

	public Map<String, Object> getDailyTripDailyRouteTimeReportDetails(Integer SUB_ROUTE_ID, String TRIP_DATERANGE,
			String TO_DATERANGE, Integer ROUTE_ID, long VEHICLEGROUP) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> configuration = null;
		CustomUserDetails userDetails = null;

		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration = companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),
					PropertyFileConstants.TRIP_COLLECTION_CONFIG);
			model.put("configuration", configuration);
			if (TRIP_DATERANGE != "" && TRIP_DATERANGE != null && TO_DATERANGE != "" && TO_DATERANGE != null
					&& SUB_ROUTE_ID != null) {

				String dateRangeFrom = "", dateRangeTo = "", tempdateRangeTo = "";

				String[] From_DateRange = null;
				String[] TO_DateRange = null;
				try {

					From_DateRange = TRIP_DATERANGE.split("/");
					TO_DateRange = TO_DATERANGE.split("/");

					dateRangeFrom = From_DateRange[1] + "-" + From_DateRange[0] + "-01";
					tempdateRangeTo = TO_DateRange[1] + "-" + TO_DateRange[0] + "-01";

					java.util.Date convertedDate = dateFormatSQL.parse(tempdateRangeTo);

					Calendar c = Calendar.getInstance();
					c.setTime(convertedDate);
					c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

					dateRangeTo = TO_DateRange[1] + "-" + TO_DateRange[0] + "-"
							+ c.getActualMaximum(Calendar.DAY_OF_MONTH);

					model.put("SEARCHDATE", dateFormat.format(dateFormatSQL.parse(dateRangeFrom)) + " to "
							+ dateFormat.format(dateFormatSQL.parse(dateRangeTo)));
					// SearchOnlyRoute_SUB_ROUTE_BY_RouteId

					model.put("subRouteList",
							tripRouteService.SearchOnlyRoute_SUB_ROUTE_BY_RouteId(ROUTE_ID, userDetails));
					model.put("SUB_ROUTE_ID", SUB_ROUTE_ID);
					model.put("TRIP_DATERANGE", TRIP_DATERANGE);
					model.put("TO_DATERANGE", TO_DATERANGE);
					model.put("ROUTE_ID", ROUTE_ID);
					model.put("VEHICLEGROUP", VEHICLEGROUP);
					model.put("configuration", configuration);
				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					HashMap<String, TripDailySheetDto> fuelAmountHm = null;

					List<TripDailyTimeIncomeDto> GetFixedTypeIncomeName = tripDailySheetService
							.List_OF_TripDailySheet_get_Fixed_TIME_Income_ROUTE_NAME(SUB_ROUTE_ID, dateRangeFrom,
									dateRangeTo, userDetails.getCompany_id());
					if (GetFixedTypeIncomeName != null && !GetFixedTypeIncomeName.isEmpty()) {

						String TDINCOME_NAME = "";
						String TDINCOME_COLUMN = "";
						for (TripDailyTimeIncomeDto tripDailyIncome : GetFixedTypeIncomeName) {

							TDINCOME_NAME += "<th>" + tripDailyIncome.getIncomeName() + "</th>";
						}

						model.put("TDINCOME_NAME", TDINCOME_NAME);

						List<Object[]> FixedTypeSheet = tripDailySheetService
								.List_OF_Time_WISE_Report_TIME_INCOME_ROUTE_NAME(SUB_ROUTE_ID, dateRangeFrom,
										dateRangeTo, GetFixedTypeIncomeName, userDetails.getCompany_id());

						if ((boolean) configuration.get(TripDailySheetCollectionConfiguration.SHOW_DIESEL_AMOUNT)) {
							fuelAmountHm = tripDailySheetService.getFuelAmountOfTripSheetReport(SUB_ROUTE_ID,
									dateRangeFrom, dateRangeTo, userDetails.getCompany_id());

							if (fuelAmountHm != null && fuelAmountHm.size() > 0) {
								Double totalDieselAmount = 0.0;
								for (Map.Entry<String, TripDailySheetDto> entry : fuelAmountHm.entrySet()) {
									totalDieselAmount += entry.getValue().getTRIP_DIESEL_AMOUNT();
								}
								model.put("totalDieselAmount", round(totalDieselAmount, 2));
							}
						}

						List<TripDailySheetDto> Dtos = null;

						String TRIP_ROUTE_NAME = "";
						if (FixedTypeSheet != null && !FixedTypeSheet.isEmpty()) {
							Dtos = new ArrayList<TripDailySheetDto>();
							TripDailySheetDto list = null;

							for (Object[] result : FixedTypeSheet) {
								list = new TripDailySheetDto();

								list.setTRIPDAILYID((Long) result[0]);
								list.setVEHICLE_REGISTRATION((String) result[1]);

								list.setTRIP_ROUTE_NAME((String) result[2]);
								TRIP_ROUTE_NAME = ((String) result[2]);

								if (result[3] != null) {
									list.setTRIP_OPEN_DATE(dateFormatonlyDate.format((java.util.Date) result[3]));
								}

								list.setTRIP_USAGE_KM((Integer) result[4]);
								list.setTRIP_DIESEL((Double) result[5]);
								/* list.setFuel_amount((Double) result[6]); */
								list.setTRIP_DIESELKMPL((Double) result[6]);
								list.setTRIP_RFIDPASS((Integer) result[7]);
								list.setTRIP_RFID_AMOUNT((Double) result[8]);
								list.setTOTAL_INCOME((Double) result[9]);
								list.setTOTAL_EXPENSE((Double) result[10]);
								list.setTOTAL_BALANCE((Double) result[11]);
								list.setTRIP_CLOSE_STATUS(TripDailySheetStatus.getTripSheetStatusName((short) result[12]));
								
								if(list.getTRIP_RFID_AMOUNT() == null) {
									list.setTRIP_RFID_AMOUNT(0.0);
								}
								if(list.getTOTAL_INCOME() == null) {
									list.setTOTAL_INCOME(0.0);
								}
								if(list.getTOTAL_EXPENSE() == null) {
									list.setTOTAL_EXPENSE(0.0);
								}
								
								if(list.getTRIP_DIESEL() == null) {
									list.setTRIP_DIESEL(0.0);
								}
								if(list.getTRIP_DIESELKMPL() == null) {
									list.setTRIP_DIESELKMPL(0.0);
								}
								if(list.getTOTAL_BALANCE() == null) {
									list.setTOTAL_BALANCE(0.0);
								}
								
								list.setTOTAL_INCOME_COLLECTION((list.getTRIP_RFID_AMOUNT() + list.getTOTAL_INCOME()));
								list.setTOTAL_NET_BALANCE((list.getTOTAL_INCOME_COLLECTION() - list.getTOTAL_EXPENSE()));

								if ((boolean) configuration
										.get(TripDailySheetCollectionConfiguration.SHOW_DIESEL_AMOUNT)) {
									if (fuelAmountHm.get(list.getTRIPDAILYID() + "") != null) {
										list.setTRIP_DIESEL_AMOUNT((fuelAmountHm.get(list.getTRIPDAILYID() + "")).getTRIP_DIESEL_AMOUNT());
									} else {
										list.setTRIP_DIESEL_AMOUNT(0.0);
									}
								}

								String COLUMN_TIP_VALUE = "";
								short ColumnResult = 13;
								for (@SuppressWarnings("unused")
								TripDailyTimeIncomeDto tripDailyIncome : GetFixedTypeIncomeName) {

									COLUMN_TIP_VALUE += "<td>" + (Double) result[ColumnResult] + "</td>";
									ColumnResult++;
								}

								list.setCREATEDBY(COLUMN_TIP_VALUE);

								ColumnResult = 13;

								Dtos.add(list);
							}

						}

						model.put("TDRoute", Dtos);

						model.put("VEHICLE_NAME", TRIP_ROUTE_NAME);

						Double[] totalGroupSheet = TDBL.Total_TripDaily_collection_Time_Report(Dtos);

						if (totalGroupSheet != null) {
							String TRIP_USAGE_KM = AMOUNTFORMAT.format(totalGroupSheet[0]);
							model.put("TRIP_USAGE_KM", TRIP_USAGE_KM);

							String TRIP_DIESEL = AMOUNTFORMAT.format(totalGroupSheet[1]);
							model.put("TRIP_DIESEL", TRIP_DIESEL);

							/*
							 * String Fuel_amount = AMOUNTFORMAT.format(totalGroupSheet[2]);
							 * model.put("Fuel_amount", Fuel_amount);
							 */

							String TRIP_DIESELKMPL = AMOUNTFORMAT.format(totalGroupSheet[2]);
							model.put("TRIP_DIESELKMPL", TRIP_DIESELKMPL);

							String TRIP_RFIDPASS = AMOUNTFORMAT.format(totalGroupSheet[3]);
							model.put("TRIP_RFIDPASS", TRIP_RFIDPASS);

							String TRIP_RFID_AMOUNT = AMOUNTFORMAT.format(totalGroupSheet[4]);
							model.put("TRIP_RFID_AMOUNT", TRIP_RFID_AMOUNT);

							String TOTAL_INCOME = AMOUNTFORMAT.format(totalGroupSheet[5]);
							model.put("TOTAL_INCOME", TOTAL_INCOME);

							String TOTAL_EXPENSE = AMOUNTFORMAT.format(totalGroupSheet[6]);
							model.put("TOTAL_EXPENSE", TOTAL_EXPENSE);

							String TOTAL_BALANCE = AMOUNTFORMAT.format(totalGroupSheet[7]);
							model.put("TOTAL_BALANCE", TOTAL_BALANCE);

							String TOTAL_INCOME_COLLECTION = AMOUNTFORMAT.format(totalGroupSheet[8]);
							model.put("TOTAL_INCOME_COLLECTION", TOTAL_INCOME_COLLECTION);

							String TOTAL_NET_BALANCE = AMOUNTFORMAT.format(totalGroupSheet[9]);
							model.put("TOTAL_NET_BALANCE", TOTAL_NET_BALANCE);

						}

						List<Object[]> FixedTypeSheetColumTOTAL = tripDailySheetService
								.List_OF_Time_WISE_ROUTE_COLUMN_Report(SUB_ROUTE_ID, dateRangeFrom, dateRangeTo,
										GetFixedTypeIncomeName, userDetails.getCompany_id());

						if (FixedTypeSheetColumTOTAL != null && !FixedTypeSheetColumTOTAL.isEmpty()) {

							for (Object[] result_column : FixedTypeSheetColumTOTAL) {
								short ColumnResult = 1;
								for (@SuppressWarnings("unused")
								TripDailyTimeIncomeDto tripDailyIncome : GetFixedTypeIncomeName) {
									TDINCOME_COLUMN += "<td>" + (Double) result_column[ColumnResult] + "</td>";
									ColumnResult++;
								}

							}
						}
						model.put("TDINCOME_COLUMN", TDINCOME_COLUMN);

					}

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
			}
			return model;
		} catch (Exception e) {
			throw e;
		}
	}

	@RequestMapping(value = "/DepotwiseDateRangeReport", method = RequestMethod.POST)
	public ModelAndView DepotwiseDateRangeReport(
			@ModelAttribute("command") @RequestParam("TRIP_DATERANGE") final String TRIP_DATERANGE,
			@RequestParam("VEHICLEGROUP") final long VEHICLEGROUP, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {
			if (TRIP_DATERANGE != "" && TRIP_DATERANGE != null && VEHICLEGROUP != 0) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATERANGE.split(" to ");

					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {

					try {
						String Trip_depotName = "", TripOpenDate_Close = "";

						if (VEHICLEGROUP != 0) {
							Trip_depotName = "  AND R.VEHICLE_GROUP_ID = " + VEHICLEGROUP + " ";
						}

						if (dateRangeFrom != "" && dateRangeTo != "") {
							TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
									+ dateRangeTo + "' ";
						}

						String query = " " + Trip_depotName + " " + TripOpenDate_Close + " ";

						List<TripDailyGroupCollectionDto> TDGroupCol = tripDailySheetService
								.List_TripDailyGroupCollection_Report(query, userDetails);

						model.put("TDGroupCol", TDBL.prepare_LIST_TripDailyGroupCollectionDto(TDGroupCol));

						model.put("CurrentMonth_FromDATE", dateRangeFrom);
						model.put("CurrentMonth_ToDATE", dateRangeTo);

						Double[] totalGroupSheet = TDBL.Tota_TripDailyGroupCollection_Sheet_total(TDGroupCol);
						if (totalGroupSheet != null) {
							String TotalUsageKM = AMOUNTFORMAT.format(totalGroupSheet[0]);
							model.put("TotalUsageKM", TotalUsageKM);
							String TotalDiesel = AMOUNTFORMAT.format(totalGroupSheet[1]);
							model.put("TotalDiesel", TotalDiesel);
							String TotalDieselKMPL = AMOUNTFORMAT.format(totalGroupSheet[2]);
							model.put("TotalDieselKMPL", TotalDieselKMPL);
							String TotalPassenger = AMOUNTFORMAT.format(totalGroupSheet[3]);
							model.put("TotalPassenger", TotalPassenger);
							String TotalRFID = AMOUNTFORMAT.format(totalGroupSheet[4]);
							model.put("TotalRFID", TotalRFID);

							String TotalRFIDAmount = AMOUNTFORMAT.format(totalGroupSheet[5]);
							model.put("TotalRFIDAmount", TotalRFIDAmount);

							String TotalCollection = AMOUNTFORMAT.format(totalGroupSheet[6]);
							model.put("TotalCollection", TotalCollection);

							String TotalWT = AMOUNTFORMAT.format(totalGroupSheet[7]);
							model.put("TotalWT", TotalWT);

							String TotalNetCollection = AMOUNTFORMAT.format(totalGroupSheet[8]);
							model.put("TotalNetCollection", TotalNetCollection);

							String TotalEPK = AMOUNTFORMAT.format(totalGroupSheet[9]);
							model.put("TotalEPK", TotalEPK);

							String TotalExpense = AMOUNTFORMAT.format(totalGroupSheet[10]);
							model.put("TotalExpense", TotalExpense);
							String TotalOT = AMOUNTFORMAT.format(totalGroupSheet[11]);
							model.put("TotalOT", TotalOT);
							String TotalBalance = AMOUNTFORMAT.format(totalGroupSheet[12]);
							model.put("TotalBalance", TotalBalance);
						}
						VehicleGroup group = vehicleGroupService.getVehicleGroupByID(VEHICLEGROUP);
						CashBookName cashBookName = cashBookNameService.getCashBookByVehicleGroupId(group.getGid(),
								userDetails.getCompany_id());
						// When Cash Book Approval the Date only show the
						// Details

						if (dateRangeFrom != null && dateRangeTo != null) {
							java.sql.Date dateRangeFrom_CB = null, dateRangeTo_CB = null;

							try {

								java.util.Date date = dateFormatSQL.parse(dateRangeFrom);
								dateRangeFrom_CB = new Date(date.getTime());

								java.util.Date dateTo = dateFormatSQL.parse(dateRangeTo);
								dateRangeTo_CB = new Date(dateTo.getTime());
							} catch (Exception e) {
								LOGGER.error("Fuel Page:", e);

							}
							List<String> dateSearch = new ArrayList<String>();
							Calendar calender = new GregorianCalendar();
							calender.setTime(dateRangeFrom_CB);

							while (calender.getTime().getTime() <= dateRangeTo_CB.getTime()) {

								java.util.Date result = calender.getTime();

								dateSearch.add(dateFormatonlyDate.format(result));

								calender.add(Calendar.DATE, 1);

							}

							model.put("DATESEARCH", dateSearch);
						}

						List<CashBookDto> Credit_Debit = CashBookService.Report_CashBook_Month_wise_Expense_Group(
								cashBookName.getNAMEID(), dateRangeFrom, dateRangeTo, userDetails.getCompany_id());

						model.put("CASHBOOK_NAME", group.getvGroup());
						model.put("CASH_BOOK_ID", cashBookName.getNAMEID());
						model.put("GROUP_NAME", group.getvGroup());
						// model.put("CASHBOOK_DATE",
						// dateFormatonlyDate.format(date));
						HashMap<String, Object> hashMap = CBBL.getCashPaymentNatureWiseHashMap(Credit_Debit);
						// model.put("cashbook", CBBL.prepare_CashBookDto_ListofDto(Credit_Debit));
						model.put("cashbookMap", hashMap.get("cashBookMap"));
						model.put("paymentTypeWise", hashMap.get("paymentTypeWise"));

						// Assign Opening Balance Total

						Double[] DebitTotal = TDBL.Total_DebitTotal_CreditTotal_CashBook(Credit_Debit);

						String TotalShowDebit = AMOUNTFORMAT.format(round(DebitTotal[0], 2));
						model.put("DebitTotal", TotalShowDebit);

						String TotalShowCredit = AMOUNTFORMAT.format(round(DebitTotal[1], 2));
						model.put("CreditTotal", TotalShowCredit);

						String TotalCloseBalance = AMOUNTFORMAT.format(round(DebitTotal[2], 2));
						model.put("Balance", TotalCloseBalance);

						ConvertIntegerToWord amount = new ConvertIntegerToWord();
						Double AmountWorld = round(DebitTotal[2], 2);
						int value = Integer.valueOf(AmountWorld.intValue());
						String AmountWORLD = amount.convertNumberToWords(value);
						model.put("TotalBalanceWorld", AmountWORLD);

					} catch (Exception e) {
						e.printStackTrace();
					}

					model.put("SEARCHDATE", TRIP_DATERANGE);

					model.put("VEHICLEGROUPID", VEHICLEGROUP);

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Depot_wise_DateRange_Report", model);
	}

	@RequestMapping("/VehicleWiseTripDailyReport")
	public ModelAndView VehicleWiseTripDailyReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Vehicle_Wise_TripDaily_Report", model);
	}

	@RequestMapping(value = "/VehicleWiseTripDailyReport", method = RequestMethod.POST)
	public ModelAndView VehicleWiseTripDailyReport(
			@ModelAttribute("command") @RequestParam("VEHICLE_ID") final Integer VEHICLE_ID,
			@RequestParam("VEHICLE_TC_DATERAGE") final String VEHICLE_TC_DATERAGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>   	configuration			= null;
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {
			
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG);
			model.put("configuration", configuration);
			String Fuel_amt ="0";
			model.put("Fuel_amt", Fuel_amt);
			if (VEHICLE_TC_DATERAGE != "" && VEHICLE_ID != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = VEHICLE_TC_DATERAGE.split(" to ");

					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String Vid = "", TripOpenDate_Close = "";

					if (VEHICLE_ID != null && VEHICLE_ID != 0) {
						Vid = " AND R.VEHICLEID=" + VEHICLE_ID + " ";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
								+ dateRangeTo + "' ";
					}

					String query = " " + Vid + " " + " " + TripOpenDate_Close + " ";
					String query1 = " AND R.vid=" + VEHICLE_ID + " ";
					List<TripDailySheetDto> Collection = tripDailySheetService.List_TripDailySheet_Report(query,
							userDetails);
					
					/* start Get TotalDeisel Amount*/
					List< TripDailySheetDto>	fuelamt	= tripDailySheetService.getFuelAmountOfTripDailySheetReport(dateRangeFrom, dateRangeTo, query1, userDetails.getCompany_id());
					
					Double TotalDieselAmt =0.0;
					Double[] totalCollection = new Double[1];
					try {
					if(fuelamt != null && !fuelamt.isEmpty()) {
						for(TripDailySheetDto dto:fuelamt) {
							if(dto.getFuel_amount()!=null) {
								TotalDieselAmt += dto.getFuel_amount();
							}
						}
					}	
					totalCollection[0] = TotalDieselAmt;
					String TotalDieselAmount = AMOUNTFORMAT.format(totalCollection[0]);
					model.put("DieselTotalAmount", TotalDieselAmount);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
					/* End Get TotalDeisel Amount*/
					if(Collection == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Vehicle_Wise_TripDaily_Report", model);
					}				
					
					model.put("TripDaily", TDBL.prepare_TripDailySheetDto_ReportList(Collection));
					/*Geting The Fuel Amount */
					model.put("FuelAmt", fuelamt);
					
					
					
					Double[] totalGroupSheet = TDBL.Total_TripDaily_Sheet_total_Details(Collection);
					if (totalGroupSheet != null) {
						String TotalUsageKM = AMOUNTFORMAT.format(totalGroupSheet[0]);
						model.put("TotalUsageKM", TotalUsageKM);
						String TotalDiesel = AMOUNTFORMAT.format(totalGroupSheet[1]);
						model.put("TotalDiesel", TotalDiesel);

						model.put("TotalKMPL", totalGroupSheet[2]);

						String TotalPassenger = AMOUNTFORMAT.format(totalGroupSheet[3]);
						model.put("TotalPassenger", TotalPassenger);

						String TotalRFID = AMOUNTFORMAT.format(totalGroupSheet[4]);
						model.put("TotalRFID", TotalRFID);

						String TotalRFIDAmount = AMOUNTFORMAT.format(totalGroupSheet[5]);
						model.put("TotalRFIDAmount", TotalRFIDAmount);

						String TotalCollection = AMOUNTFORMAT.format(totalGroupSheet[6]);
						model.put("TotalCollection", TotalCollection);
						
						String TotalWT = AMOUNTFORMAT.format(totalGroupSheet[7]);
						model.put("TotalWT", TotalWT);

						 /*Start Total Net Collection Using Following Formula (RFID+TOTAL)*/
						String NetTotalCollection = AMOUNTFORMAT.format(totalGroupSheet[5]+totalGroupSheet[6]);
						model.put("NetTotalCollection", NetTotalCollection);
						 /*End Total Net Collection */
						
						String TotalNetCollection = AMOUNTFORMAT.format(totalGroupSheet[8]);
						model.put("TotalNetCollection", TotalNetCollection);

						String TotalEPK = AMOUNTFORMAT.format(totalGroupSheet[9]);
						model.put("TotalEPK", TotalEPK);

						String TotalExpense = AMOUNTFORMAT.format(totalGroupSheet[10]);
						model.put("TotalExpense", TotalExpense);

						String TotalOT = AMOUNTFORMAT.format(totalGroupSheet[11]);
						model.put("TotalOT", TotalOT);
						
						/*Start Total Balance Using Following Formula (NetCollection+TOTALExpense)*/
						String BalanceTotal = AMOUNTFORMAT.format((totalGroupSheet[5]+totalGroupSheet[6])-totalGroupSheet[10]);
						model.put("BalanceTotal", BalanceTotal);
						/*End Total Balance */
						
						String TotalBalance = AMOUNTFORMAT.format(totalGroupSheet[12]);
						model.put("TotalBalance", TotalBalance);
					}

					model.put("SEARCHDATE", VEHICLE_TC_DATERAGE);

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Vehicle_Wise_TripDaily_Report", model);
	}

	@RequestMapping("/VehicleWiseFuelMileageReport")
	public ModelAndView VehicleWiseFuelMileageReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Vehicle_Wise_FuelMileage_Report", model);
	}

	@RequestMapping(value = "/VehicleWiseFuelMileageReport", method = RequestMethod.POST)
	public ModelAndView Vehicle_Wise_Fuel_MileageReport(
			@ModelAttribute("command") @RequestParam("VEHICLE_ID") final Integer VEHICLE_ID,
			@RequestParam("VEHICLE_TC_DATERAGE") final String VEHICLE_TC_DATERAGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {

			if (VEHICLE_TC_DATERAGE != "" && VEHICLE_ID != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = VEHICLE_TC_DATERAGE.split(" to ");

					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String Vid = "", TripOpenDate_Close = "";

					if (VEHICLE_ID != null && VEHICLE_ID != 0) {
						Vid = " AND R.VEHICLEID=" + VEHICLE_ID + " ";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
								+ dateRangeTo + "' ";
					}

					String query = " " + Vid + " " + " " + TripOpenDate_Close + " ";
					List<TripDailySheetDto> Collection = tripDailySheetService.List_TripDailySheet_Report(query,
							userDetails);
					
					if(Collection == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Vehicle_Wise_FuelMileage_Report", model);
					}
					
					model.put("TripDaily", TDBL.prepare_TripDaily_FuelMileage_ReportList(Collection));

					Double[] totalGroupSheet = TDBL.FuelMilage_TripDaily_Sheet_total(Collection);
					if (totalGroupSheet != null) {
						String TotalUsageKM = AMOUNTFORMAT.format(totalGroupSheet[0]);
						model.put("TotalUsageKM", TotalUsageKM);
						String TotalDiesel = AMOUNTFORMAT.format(totalGroupSheet[1]);
						model.put("TotalDiesel", TotalDiesel);

						model.put("TotalKmpl", totalGroupSheet[2]);

					}

					model.put("SEARCHDATE", VEHICLE_TC_DATERAGE);
				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Vehicle_Wise_FuelMileage_Report", model);
	}

	@RequestMapping("/DriverWiseFuelMileageReport")
	public ModelAndView DriverWiseFuelMileageReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Driver_Wise_FuelMileage_Report", model);
	}

	@RequestMapping(value = "/DriverWiseFuelMileageReport", method = RequestMethod.POST)
	public ModelAndView Driver_Wise_Fuel_MileageReport(
			@ModelAttribute("command") @RequestParam("TRIP_DRIVER_ID") final Integer DRIVER_ID,
			@RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			if (TRIP_DATE_RANGE != "" && DRIVER_ID != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String DriverId = "", TripOpenDate_Close = "";

					if (DRIVER_ID != null && DRIVER_ID != 0) {
						DriverId = " AND R.TRIP_DRIVER_ID=" + DRIVER_ID + " ";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
								+ dateRangeTo + "' ";
					}

					String query = " " + DriverId + " " + " " + TripOpenDate_Close + " ";

					List<TripDailySheetDto> Collection = tripDailySheetService.List_TripDailySheet_Report(query,
							userDetails);
					
					
					if(Collection == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Driver_Wise_FuelMileage_Report", model);
					}
					
					model.put("TripDaily", TDBL.prepare_TripDaily_FuelMileage_ReportList(Collection));

					Double[] totalGroupSheet = TDBL.FuelMilage_TripDaily_Sheet_total(Collection);
					if (totalGroupSheet != null) {
						String TotalUsageKM = AMOUNTFORMAT.format(totalGroupSheet[0]);
						model.put("TotalUsageKM", TotalUsageKM);
						String TotalDiesel = AMOUNTFORMAT.format(totalGroupSheet[1]);
						model.put("TotalDiesel", TotalDiesel);

						model.put("TotalKmpl", totalGroupSheet[2]);

					}

					model.put("SEARCHDATE", TRIP_DATE_RANGE);
				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Driver_Wise_FuelMileage_Report", model);
	}

	@RequestMapping("/ConductorWiseCollectionReport")
	public ModelAndView ConductorWiseCollectionReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Conductor_WiseCollection_Report", model);
	}

	@RequestMapping(value = "/ConductorWiseCollectionReport", method = RequestMethod.POST)
	public ModelAndView ConductorWiseCollectionReport(
			@ModelAttribute("command") @RequestParam("TRIP_CONDUCTOR_ID") final Integer CONDUCTOR_ID,
			@RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			if (TRIP_DATE_RANGE != "" && CONDUCTOR_ID != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String DriverId = "", TripOpenDate_Close = "";

					if (CONDUCTOR_ID != null && CONDUCTOR_ID != 0) {
						DriverId = " AND R.TRIP_CONDUCTOR_ID=" + CONDUCTOR_ID + " ";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
								+ dateRangeTo + "' ";
					}

					String query = " " + DriverId + " " + " " + TripOpenDate_Close + " ";

					List<TripDailySheetDto> Collection = tripDailySheetService.List_TripDailySheet_Report(query,
							userDetails);
					
					if(Collection == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Conductor_WiseCollection_Report", model);
					}
					
					
					
					model.put("TripDaily", TDBL.prepare_TripDailySheetDto_ReportList(Collection));

					Double[] totalGroupSheet = TDBL.Total_Conducter_TripDaily_Collection_total(Collection);
					if (totalGroupSheet != null) {
						String TotalUsageKM = AMOUNTFORMAT.format(totalGroupSheet[0]);
						model.put("TotalUsageKM", TotalUsageKM);

						String TotalPassenger = AMOUNTFORMAT.format(totalGroupSheet[1]);
						model.put("TotalPassenger", TotalPassenger);

						String TotalRFID = AMOUNTFORMAT.format(totalGroupSheet[2]);
						model.put("TotalRFID", TotalRFID);

						String TotalCollection = AMOUNTFORMAT.format(totalGroupSheet[3]);
						model.put("TotalCollection", TotalCollection);

						String TotalEPK = AMOUNTFORMAT.format(totalGroupSheet[4]);
						model.put("TotalEPK", TotalEPK);

					}

					model.put("SEARCHDATE", TRIP_DATE_RANGE);
				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return new ModelAndView("Conductor_WiseCollection_Report", model);
	}

	@RequestMapping("/RouteWiseTripDailyReport")
	public ModelAndView RouteWiseTripDailyReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Route_Wise_TripDaily_Report", model);
	}

	@RequestMapping(value = "/RouteWiseTripDailyReport", method = RequestMethod.POST)
	public ModelAndView RouteWiseTripDailyReport(
			@ModelAttribute("command") @RequestParam("TRIP_ROUTE") final Integer TRIP_ROUTE,
			@RequestParam("VEHICLE_TC_DATERAGE") final String VEHICLE_TC_DATERAGE, final HttpServletRequest request) {
		Map<String, Object> 				model 			= new HashMap<String, Object>();
		CustomUserDetails 					userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		HashMap<String, Object>   			configuration	= null;
		HashMap<String, TripDailySheetDto>  fuelAmountHm	= null;
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIP_COLLECTION_CONFIG);
			model.put("configuration", configuration);
			if (VEHICLE_TC_DATERAGE != "" && TRIP_ROUTE != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;
				try {
					From_TO_DateRange = VEHICLE_TC_DATERAGE.split(" to ");

					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}
				try {
					String Vehicle_group = "", TripOpenDate_Close = "";
					if (TRIP_ROUTE != 0) {
						Vehicle_group = " AND R.TRIP_ROUTE_ID=" + TRIP_ROUTE + " ";
					}
					if (dateRangeFrom != "" && dateRangeTo != "") {
						TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' ";
					}
					String query = " " + Vehicle_group + " " + TripOpenDate_Close + " ";

					
					List<TripDailyRouteSheetDto> Collection = tripDailySheetService.List_TripDailyRouteSheet_Report(query, userDetails);
/*For Diesel Amount Make New Query1*/
					String routeID = " AND v.TRIP_ROUTE_ID=" + TRIP_ROUTE + " ";
					String Daterange = "  AND v.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '" + dateRangeTo + "' ";

					String query1=" " + routeID + " " + Daterange + " ";

					if(Collection == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Route_Wise_TripDaily_Report", model);
					}
					if((boolean) configuration.get(TripDailySheetCollectionConfiguration.SHOW_DIESEL_AMOUNT)) {
						fuelAmountHm=tripDailySheetService.getFuelAmountOfTripSheetReportDateWise(dateRangeFrom, dateRangeTo, query1, userDetails.getCompany_id());
						if(fuelAmountHm != null && fuelAmountHm.size() > 0) {
							model.put("TDRoute", TDBL.prepare_TripDailyRouteSheet_CollectionDto(Collection,fuelAmountHm)); 
							Double[] totalGroupSheet = TDBL.Route_Wise_Collection_totalDieselAmount(Collection,fuelAmountHm);
							if (totalGroupSheet != null) {
								String totalDieselAmount1 = AMOUNTFORMAT.format(totalGroupSheet[0]);
								model.put("totalDieselAmount", totalDieselAmount1);
						}
					}
				}
					else {
						model.put("TDRoute", TDBL.prepare_TripDailyRouteSheet_CollectionDto(Collection));
					}

					Double[] totalGroupSheet = TDBL.Group_Total_details_to_TripDaily_all_total(Collection);
					if (totalGroupSheet != null) {
						String TotalUsageKM = AMOUNTFORMAT.format(totalGroupSheet[0]);
						model.put("TotalUsageKM", TotalUsageKM);
						String TotalDiesel = AMOUNTFORMAT.format(totalGroupSheet[1]);
						model.put("TotalDiesel", TotalDiesel);

						model.put("TotalKMPL", totalGroupSheet[2]);
						String TotalPassenger = AMOUNTFORMAT.format(totalGroupSheet[3]);
						model.put("TotalPassenger", TotalPassenger);

						String TotalRFID = AMOUNTFORMAT.format(totalGroupSheet[4]);
						model.put("TotalRFID", TotalRFID);

						String TotalRFIDAmount = AMOUNTFORMAT.format(totalGroupSheet[5]);
						model.put("TotalRFIDAmount", TotalRFIDAmount);

						String TotalCollection = AMOUNTFORMAT.format(totalGroupSheet[6]);
						model.put("TotalCollection", TotalCollection);

						String TotalWT = AMOUNTFORMAT.format(totalGroupSheet[7]);
						model.put("TotalWT", TotalWT);

						String TotalNetCollection = AMOUNTFORMAT.format(totalGroupSheet[8]);
						model.put("TotalNetCollection", TotalNetCollection);

						String TotalEPK = AMOUNTFORMAT.format(totalGroupSheet[9]);
						model.put("TotalEPK", TotalEPK);

						String TotalExpense = AMOUNTFORMAT.format(totalGroupSheet[10]);
						model.put("TotalExpense", TotalExpense);

						String TotalOT = AMOUNTFORMAT.format(totalGroupSheet[11]);
						model.put("TotalOT", TotalOT);

						String TotalBalance = AMOUNTFORMAT.format(totalGroupSheet[12]);
						model.put("TotalBalance", TotalBalance);

						/*Start Total Net Collection Using Following Formula (RFID+TOTAL)*/
						String NetTotalCollection = AMOUNTFORMAT.format(totalGroupSheet[5]+totalGroupSheet[6]);
						model.put("NetTotalCollection", NetTotalCollection);
						/*End Total Net Collection */


						/*Start Total Balance Using Following Formula (NetCollection+TOTALExpense)*/
						String BalanceTotal = AMOUNTFORMAT.format((totalGroupSheet[5]+totalGroupSheet[6])-totalGroupSheet[10]);
						model.put("BalanceTotal", BalanceTotal);
						/*End Total Balance */

					}

					model.put("SEARCHDATE", VEHICLE_TC_DATERAGE);

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Route_Wise_TripDaily_Report", model);

	}

	@RequestMapping(value = "/DateWiseTripDailyReport", method = RequestMethod.POST)
	public ModelAndView DateWiseTripDailyReport(
			@ModelAttribute("command") @RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String TripOpenDate_Close = "";

					if (dateRangeFrom != "" && dateRangeTo != "") {
						TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
								+ dateRangeTo + "' ";
					}

					String query = " " + TripOpenDate_Close + " ";

					List<TripDailySheetDto> Collection = tripDailySheetService.List_TripDailySheet_Report(query,
							userDetails);
					model.put("TripDaily", TDBL.prepare_TripDailySheetDto_ReportList(Collection));

					Double[] totalGroupSheet = TDBL.Total_TripDaily_Sheet_total_Details(Collection);
					if (totalGroupSheet != null) {
						String TotalUsageKM = AMOUNTFORMAT.format(totalGroupSheet[0]);
						model.put("TotalUsageKM", TotalUsageKM);
						String TotalDiesel = AMOUNTFORMAT.format(totalGroupSheet[1]);
						model.put("TotalDiesel", TotalDiesel);

						model.put("TotalKMPL", totalGroupSheet[2]);

						String TotalPassenger = AMOUNTFORMAT.format(totalGroupSheet[3]);
						model.put("TotalPassenger", TotalPassenger);

						String TotalRFID = AMOUNTFORMAT.format(totalGroupSheet[4]);
						model.put("TotalRFID", TotalRFID);

						String TotalRFIDAmount = AMOUNTFORMAT.format(totalGroupSheet[5]);
						model.put("TotalRFIDAmount", TotalRFIDAmount);

						String TotalCollection = AMOUNTFORMAT.format(totalGroupSheet[6]);
						model.put("TotalCollection", TotalCollection);

						String TotalWT = AMOUNTFORMAT.format(totalGroupSheet[7]);
						model.put("TotalWT", TotalWT);

						String TotalNetCollection = AMOUNTFORMAT.format(totalGroupSheet[8]);
						model.put("TotalNetCollection", TotalNetCollection);

						String TotalEPK = AMOUNTFORMAT.format(totalGroupSheet[9]);
						model.put("TotalEPK", TotalEPK);

						String TotalExpense = AMOUNTFORMAT.format(totalGroupSheet[10]);
						model.put("TotalExpense", TotalExpense);

						String TotalOT = AMOUNTFORMAT.format(totalGroupSheet[11]);
						model.put("TotalOT", TotalOT);

						String TotalBalance = AMOUNTFORMAT.format(totalGroupSheet[12]);
						model.put("TotalBalance", TotalBalance);
					}

					model.put("SEARCHDATE", TRIP_DATE_RANGE);

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Vehicle_Wise_TripDaily_Report", model);
	}

	@RequestMapping("/DateWiseGroupTDReport")
	public ModelAndView DateWiseGroupTDReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Day_TDGroupCollection_Report", model);
	}

	@RequestMapping(value = "/DateWiseGroupTDReport", method = RequestMethod.POST)
	public ModelAndView DateWiseGroupTripDailyReport(
			@ModelAttribute("command") @RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {

			if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String TripOpenDate_Close = "";

					if (dateRangeFrom != "" && dateRangeTo != "") {
						TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
								+ dateRangeTo + "' ";
					}

					String query = " " + TripOpenDate_Close + " ";

					List<TripDailyGroupCollectionDto> Collection = tripDailySheetService
							.List_TripDailyGroupCollection_Report(query, userDetails);
					
					if(Collection == null) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Day_TDGroupCollection_Report", model);
					}
					
					
					model.put("TripDGroup", TDBL.prepare_LIST_TripDailyGroupCollectionDto(Collection));

					Double[] totalGroupSheet = TDBL.Tota_TripDailyGroupCollection_total(Collection);
					if (totalGroupSheet != null) {
						String TotalUsageKM = AMOUNTFORMAT.format(totalGroupSheet[0]);
						model.put("TotalUsageKM", TotalUsageKM);
						String TotalDiesel = AMOUNTFORMAT.format(totalGroupSheet[1]);
						model.put("TotalDiesel", TotalDiesel);
						String TotalPassenger = AMOUNTFORMAT.format(totalGroupSheet[2]);
						model.put("TotalPassenger", TotalPassenger);
						String TotalRFID = AMOUNTFORMAT.format(totalGroupSheet[3]);
						model.put("TotalRFID", TotalRFID);
						String TotalCollection = AMOUNTFORMAT.format(totalGroupSheet[4]);
						model.put("TotalCollection", TotalCollection);
						String TotalExpense = AMOUNTFORMAT.format(totalGroupSheet[5]);
						model.put("TotalExpense", TotalExpense);
						String TotalOT = AMOUNTFORMAT.format(totalGroupSheet[6]);
						model.put("TotalOT", TotalOT);
						String TotalBalance = AMOUNTFORMAT.format(totalGroupSheet[7]);
						model.put("TotalBalance", TotalBalance);
					}

					model.put("SEARCHDATE", TRIP_DATE_RANGE);

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Day_TDGroupCollection_Report", model);
	}

	@RequestMapping("/DateWiseAllGroupTDReport")
	public ModelAndView DateWiseAllGroupTDReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Day_ALLTDGroupCollection_Report", model);
	}

	@RequestMapping(value = "/DateWiseAllGroupTDReport", method = RequestMethod.POST)
	public ModelAndView DateWiseAllGroupTDReport(
			@ModelAttribute("command") @RequestParam("TRIP_DATE_RANGE") final String TRIP_DATE_RANGE,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		try {

			if (TRIP_DATE_RANGE != "" && TRIP_DATE_RANGE != null) {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATE_RANGE.split(" to ");

					dateRangeFrom = From_TO_DateRange[0];
					dateRangeTo = From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME;

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String TripOpenDate_Close = "";

					if (dateRangeFrom != "" && dateRangeTo != "") {
						TripOpenDate_Close = "  AND R.TRIP_OPEN_DATE between '" + dateRangeFrom + "' AND '"
								+ dateRangeTo + "' ";
					}

					String query = " " + TripOpenDate_Close + " ";

					List<TripDailyAllGroupDay> Collection = tripDailySheetService
							.List_TripDailyAllGroupDay_Report(query, userDetails.getCompany_id());
					
					if((Collection == null) ||(Collection.isEmpty())) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("Day_ALLTDGroupCollection_Report", model);
					}
					
					model.put("TDALLGroup", TDBL.prepare_LIST_TripDailyAllGroupDayDto(Collection));

					Double[] totalGroupSheet = TDBL.Tota_TripDailyAllGroupDay_total(Collection);
					if (totalGroupSheet != null) {
						String TotalUsageKM = AMOUNTFORMAT.format(totalGroupSheet[0]);
						model.put("TotalUsageKM", TotalUsageKM);
						String TotalDiesel = AMOUNTFORMAT.format(totalGroupSheet[1]);
						model.put("TotalDiesel", TotalDiesel);
						String TotalPassenger = AMOUNTFORMAT.format(totalGroupSheet[2]);
						model.put("TotalPassenger", TotalPassenger);
						String TotalRFIDCARD = AMOUNTFORMAT.format(totalGroupSheet[3]);
						model.put("TotalRFIDCARD", TotalRFIDCARD);
						String TotalRFIDRCG = AMOUNTFORMAT.format(totalGroupSheet[4]);
						model.put("TotalRFIDRCG", TotalRFIDRCG);
						String TotalRFIDUSAGE = AMOUNTFORMAT.format(totalGroupSheet[5]);
						model.put("TotalRFIDUSAGE", TotalRFIDUSAGE);
						String TotalCollection = AMOUNTFORMAT.format(totalGroupSheet[6]);
						model.put("TotalCollection", TotalCollection);
						String TotalExpense = AMOUNTFORMAT.format(totalGroupSheet[7]);
						model.put("TotalExpense", TotalExpense);
						String TotalOT = AMOUNTFORMAT.format(totalGroupSheet[8]);
						model.put("TotalOT", TotalOT);
						String TotalBalance = AMOUNTFORMAT.format(totalGroupSheet[9]);
						model.put("TotalBalance", TotalBalance);
					}

					model.put("SEARCHDATE", TRIP_DATE_RANGE);

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/Report", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Day_ALLTDGroupCollection_Report", model);
	}

	@RequestMapping("/TripCollectionExpenseReport")
	public ModelAndView TyreSentForRethreadingReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		HashMap<String, Object>	configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
		model.put("configuration", configuration);
		return new ModelAndView("Trip_Collection_Expense_Report", model);
	}

	@RequestMapping("/TripCollectionExpenseNameReport")
	public ModelAndView TripCollectionExpenseNameReport(
			@ModelAttribute("command") RenewalReminderDto renewalReminderDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Trip_Collection_Expense_Name_Report", model);
	}

}
