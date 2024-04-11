package org.fleetopgroup.web.controller;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.DriverAdvanceStatus;
import org.fleetopgroup.constant.DriverAdvanceType;
import org.fleetopgroup.constant.DriverStatus;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.DriverPaidAdvanceDto;
import org.fleetopgroup.persistence.dto.DriverSalaryDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.model.Company;
import org.fleetopgroup.persistence.model.CompanyFixedAllowance;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverPaidAdvance;
import org.fleetopgroup.persistence.model.DriverSalary;
import org.fleetopgroup.persistence.model.DriverSalaryAdvance;
import org.fleetopgroup.persistence.model.TripCollectionSheet;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.serviceImpl.ICompanyService;
import org.fleetopgroup.persistence.serviceImpl.IDriverAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IDriverPaidAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DriverSalaryController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDriverSalaryService driverSalaryService;

	@Autowired
	private IDriverAdvanceService driverAdvanceService;

	@Autowired
	private IDriverSalaryAdvanceService DriverSalaryAdvanceService;

	@Autowired
	private IDriverPaidAdvanceService DriverPaidAdvanceService;
	
	@Autowired IDriverDocumentService	driverDocumentService;

	@Autowired
	private IDriverService driverService;
	DriverBL DBL = new DriverBL();

	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private ICompanyService CompanyService;

	@Autowired
	private IVehicleGroupService vehicleGroupService;

	ByteImageConvert ByteImageConvert = new ByteImageConvert();

	SimpleDateFormat dateFormat_SQL = new SimpleDateFormat("yyyy-MM-dd");

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	SimpleDateFormat dateFormat_Name = new SimpleDateFormat("dd-MMM-yyyy");

	DecimalFormat AMOUNTFORMAT = new DecimalFormat("##,##,##0");

	public static final Integer DRIVER_DEFALAT_ZERO = 0;

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	/**
	 * @param esiAmountPer
	 * @param salary
	 * @return
	 */
	private Double PerCentage_Calculation(Double esiAmountPer, Double salary) {
		// Note: Calculation in Total Percentage
		Double percentage = 0.0;

		percentage = ((esiAmountPer * salary) / 100.0);

		return round(percentage, 2);
	}

	@RequestMapping(value = "/UpdateLastSalaryDetails", method = RequestMethod.POST)
	public ModelAndView UpdateLastSalaryDetails(
			@ModelAttribute("command") @RequestParam("DRIVER_GROUP_ID") final Long driver_Group_Id,
			@RequestParam("DRIVER_JOBTITLE") final Integer driver_Jobtitle_Id,
			@RequestParam("TRIP_DATE_RANGE") final String trip_Date_Range, final HttpServletRequest request) {

		Map<String, Object> 				model 							= null;
		CustomUserDetails 					userDetails 					= null;
		ValueObject							dateRange						= null;
		List<Driver> 						driversList	 					= null;
		HashMap<Integer, TripSheetDto>		driverWiseTripSheetHM			= null;
		Company 							esi_workingDays_company 		= null;
		CompanyFixedAllowance 				fixed_company 					= null;
		DriverSalary 						driverSalary					= null;
		TripSheetDto						tripSheet						= null;
		String 								dateRangeFrom 					= "";
		String 								dateRangeTo 					= "";
		String 								driverGroupQuery 				= "";
		Double 								driverPerDaySalary				= 0.0;
		Double 								esiAmount 						= 0.0;
		Double 								pfAmount 						= 0.0;
		Double 								salary 							= 0.0;
		Double 								esiPFTotal 						= 0.0;
		Double 								extra_Salary 					= 0.0;
		Double 								total 							= 0.0;
		Double 								allowanceAndOtherTotal 			= 0.0;
		Double 								total_Extra 					= 0.0;
		Double 								total_Advance 					= 0.0;
		Double 								total_Allowance_Amount 			= 0.0;
		Double 								total_OtherExtra_amount 		= 0.0;
		Integer 							total_OtherExtraDays 			= 0;
		Long 								total_Working_days 				= (long) 0;
		Long 								total_extra_Working_days 		= (long) 0;
		Long 								total_Current_Working_days 		= (long) 0;
		Long 								total_trip_Sheet		 		= (long) 0;
		Long								total_KM_Usage			 		= (long) 0;
		
		try {
			model 			= new HashMap<String, Object>();
			if (trip_Date_Range != "" && driver_Jobtitle_Id != null && driver_Group_Id != null) {
				userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

				dateRange		= DateTimeUtility.getStartAndEndDateStr(trip_Date_Range);
				dateRangeFrom 	= dateRange.getString(DateTimeUtility.FROM_DATE);
				dateRangeTo 	= dateRange.getString(DateTimeUtility.TO_DATE);

				if (driver_Group_Id != null && driver_Jobtitle_Id == 0) {
					driverGroupQuery = "SELECT DA.driver_id FROM Driver DA WHERE DA.vehicleGroupId="
							+ driver_Group_Id + " AND DA.companyId = "+userDetails.getCompany_id()+" AND DA.markForDelete = 0";
				}

				if (driver_Group_Id != null && driver_Jobtitle_Id > 0) {
					driverGroupQuery = "SELECT DA.driver_id FROM Driver DA WHERE DA.vehicleGroupId="
							+ driver_Group_Id + " AND DA.driJobId=" + driver_Jobtitle_Id + " AND DA.companyId = "+userDetails.getCompany_id()+" AND DA.markForDelete = 0 ";
				}

				driversList 				= driverAdvanceService.Report_Group_Driver_Attendance_Lastmonth_salaryDetails(driverGroupQuery, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
				driverWiseTripSheetHM		= driverAdvanceService.Report_Group_Driver_Trip_And_KMUsage_Lastmonth_salaryDetails(driverGroupQuery, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
				esi_workingDays_company 	= CompanyService.GET_USER_ID_TO_COMPANY_DETAILS_ESI_PF_WORKING_DAYS(userDetails.getCompany_id());

				if (esi_workingDays_company != null) {
					fixed_company = CompanyService.GET_COMPANY_ID_TO_COMPANYFIXEDALLOWANCE_EXTRA(esi_workingDays_company.getCompany_id(), driver_Group_Id, driver_Jobtitle_Id);

					if (fixed_company != null) {
						if (driversList != null && !driversList.isEmpty()) {
							for (Driver driver : driversList) {
								driverSalary = new DriverSalary();

								try {
									if (dateRangeFrom != null) {
										java.util.Date date = dateFormat_SQL.parse(dateRangeFrom);
										Date Reported_Date = new Date(date.getTime());
										driverSalary.setSALARY_FROM_DATE(Reported_Date);
									}

									if (dateRangeTo != null) {
										java.util.Date date = dateFormat_SQL.parse(dateRangeTo);
										Date ReportedTo_Date = new Date(date.getTime());
										driverSalary.setSALARY_TO_DATE(ReportedTo_Date);
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								driverSalary.setDRIVER_ID(driver.getDriver_id());
								driverSalary.setDRIVER_GROUP_ID(driver.getVehicleGroupId());

								if (driver.getDriver_esiamount() != null) {
									driverSalary.setESIAMOUNT_NO(driver.getDriver_esiamount());
								} else {
									driverSalary.setESIAMOUNT_NO(0.0);
								}

								if (driver.getDriver_pfamount() != null) {
									driverSalary.setPFAMOUNT_NO(driver.getDriver_pfamount());
								} else {
									driverSalary.setPFAMOUNT_NO(0.0);
								}

								driverSalary.setDRIVER_SALARY_TYPE_ID(driver.getDriverSalaryTypeId());
								
								if(driver.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_TRIP) {
									if(driverWiseTripSheetHM != null) {
										tripSheet	= driverWiseTripSheetHM.get(driver.getDriver_id());
										
										if(tripSheet != null) {
											total_trip_Sheet	= tripSheet.getTripSheetCount();
										
											if (driver.getDriver_perdaySalary() != null) {
												driverPerDaySalary = driver.getDriver_perdaySalary();
											}
											
											salary = total_trip_Sheet * driverPerDaySalary;
											
											if (!salary.equals(0.0)) {

												if (driver.getDriver_esiamount() != null) {
													Double EsiAmountPer = driver.getDriver_esiamount();
													esiAmount = PerCentage_Calculation(EsiAmountPer, salary);
												}

												if (driver.getDriver_pfamount() != null) {
													Double PFAmountPer = driver.getDriver_pfamount();
													pfAmount = PerCentage_Calculation(PFAmountPer, salary);
												}

												esiPFTotal = esiAmount + pfAmount;

												total = (salary - esiPFTotal);
												
												total_Extra = total + extra_Salary;
											}
										}
									}
								} else if(driver.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_KM) {
									if(driverWiseTripSheetHM != null) {
										tripSheet	= driverWiseTripSheetHM.get(driver.getDriver_id());
										
										if(tripSheet != null) {
											total_KM_Usage	= tripSheet.getTotalKMUsage();
											
											if (driver.getDriver_perdaySalary() != null) {
												driverPerDaySalary = driver.getDriver_perdaySalary();
											}
											
											salary = total_KM_Usage * driverPerDaySalary;
											
											if (!salary.equals(0.0)) {

												if (driver.getDriver_esiamount() != null) {
													Double EsiAmountPer = driver.getDriver_esiamount();
													esiAmount = PerCentage_Calculation(EsiAmountPer, salary);
												}

												if (driver.getDriver_pfamount() != null) {
													Double PFAmountPer = driver.getDriver_pfamount();
													pfAmount = PerCentage_Calculation(PFAmountPer, salary);
												}

												esiPFTotal = esiAmount + pfAmount;

												total = (salary - esiPFTotal);
												
												total_Extra = total + extra_Salary;
											}
										}
									}
								} else if(driver.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_DAY) {
									if (driver.getDriver_perdaySalary() != null && driver.getTripSheetID() != null) {
										total_Current_Working_days = driver.getTripSheetID();

										if (driver.getDriver_perdaySalary() != null) {
											driverPerDaySalary = driver.getDriver_perdaySalary();
										}

										if (total_Current_Working_days <= esi_workingDays_company.getCompany_esi_pf_days()) {

											total_Working_days = total_Current_Working_days;
										} else {
											total_Working_days = esi_workingDays_company.getCompany_esi_pf_days();
											total_extra_Working_days = total_Current_Working_days - esi_workingDays_company.getCompany_esi_pf_days();
										}

										if (total_Current_Working_days != null && fixed_company.getFIX_PERDAY_ALLOWANCE_AMOUNT() != null) {
											total_Allowance_Amount = total_Current_Working_days * fixed_company.getFIX_PERDAY_ALLOWANCE_AMOUNT();
										}

										if (fixed_company.getFIX_EXTRA_DAYS() != null) {
											total_OtherExtraDays = fixed_company.getFIX_EXTRA_DAYS();
										}

										if (total_Current_Working_days >= total_OtherExtraDays) {
											total_OtherExtra_amount = fixed_company.getFIX_EXTRA_DAYS_AMOUNT();
										}

										salary = total_Working_days * driverPerDaySalary;

										extra_Salary = total_extra_Working_days * driverPerDaySalary;

										if (!salary.equals(0.0)) {

											if (driver.getDriver_esiamount() != null) {
												Double EsiAmountPer = driver.getDriver_esiamount();
												esiAmount = PerCentage_Calculation(EsiAmountPer, salary);
											}

											if (driver.getDriver_pfamount() != null) {
												Double PFAmountPer = driver.getDriver_pfamount();
												pfAmount = PerCentage_Calculation(PFAmountPer, salary);
											}

											allowanceAndOtherTotal = total_Allowance_Amount + total_OtherExtra_amount;

											esiPFTotal = esiAmount + pfAmount;

											total = ((allowanceAndOtherTotal + salary) - esiPFTotal);

											total_Extra = total + extra_Salary;
										}

									}
								}

								driverSalary.setTRIP_SHEET_COUNT(total_trip_Sheet);
								driverSalary.setTOTAL_KM_USAGE(total_KM_Usage);
								
								// ATTENDANCE_DATE to showing in TRIPSHEETID besed on company esi logic
								driverSalary.setTOTAL_WORKINGDAY(total_Working_days);

								// Attendance extra working day based on company logic
								driverSalary.setTOTAL_EXTRA_WORKINGDAY(total_extra_Working_days);

								driverSalary.setMONTH_PERDAYSALARY(driverPerDaySalary);
								// Here Driver Point IN Driver Per day salary
								driverSalary.setMONTH_SALARY(round(salary, 2));

								// Here Driver salary Extra IN Driver Per day Salary
								driverSalary.setMONTH_EXTRA_SALARY(round(extra_Salary, 2));

								driverSalary.setTOTAL_ESIAMOUNT(round(esiAmount, 2));
								driverSalary.setTOTAL_PFAMOUNT(round(pfAmount, 2));

								driverSalary.setCOMFIXID(fixed_company.getCOMFIXID());

								// Here Driver Salary Extra IN Driver Per day to Extra allowance
								driverSalary.setTOTAL_ALLOWANCE(round(total_Allowance_Amount, 2));

								// Here Driver Salary Extra IN Driver Per day to Extra allowance
								driverSalary.setTOTAL_OTHEREXTRA(round(total_OtherExtra_amount, 2));

								driverSalary.setTOTAL_PENALTY(0.0);
								driverSalary.setTOTAL_ADVANCE(round(total_Advance, 2));

								driverSalary.setTOTAL_ADVANCE_BALANCE(0.0);

								driverSalary.setTOTAL_NETSALARY(round(total, 2));

								driverSalary.setTOTAL_EXTRA_NETSALARY(round(total_Extra, 2));

								/** Task new from to create change status */
								//driverSalary.setSALARY_STATUS(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN_NAME);
								driverSalary.setSALARY_STATUS_ID(DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN);

								driverSalary.setTOTAL_HANDSALARY(0.0);


								/** Set Status in Issues */
								driverSalary.setmarkForDelete(false);
								/** Set Created by email Id */

								//driverSalary.setCREATEDBY(userDetails.getEmail());
								//driverSalary.setLASTMODIFIEDBY(userDetails.getEmail());
								driverSalary.setCREATED_BY_ID(userDetails.getId());
								driverSalary.setLASTMODIFIED_BY_ID(userDetails.getId());

								java.util.Date currentDateUpdate = new java.util.Date();
								Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

								/** Set Created Current Date */
								driverSalary.setCREATED_DATE(toDate);
								driverSalary.setLASTUPDATED_DATE(toDate);

								// System.out.println(driverSalary.getSALARY_FROM_DATE());
								// System.out.println(driverSalary.getSALARY_TO_DATE());

								List<DriverSalary> validate = driverSalaryService.ValidateDriverSalary(
										driverSalary.getSALARY_FROM_DATE(), driverSalary.getSALARY_TO_DATE(), driverSalary.getDRIVER_ID(),
										driverSalary.getDRIVER_GROUP_ID(), userDetails.getCompany_id());

								if (validate != null && !validate.isEmpty()) {
									model.put("already", true);
								} else {

									Object[] advPen = driverAdvanceService.SUM_TOTAL_ADVANCE_PENALTY_AMOUNT(
											driver.getDriver_id(), dateRangeFrom, dateRangeTo,
											userDetails.getCompany_id());
									if (advPen != null) {
										driverSalary.setTOTAL_PREVIOUS_ADVANCE((Double) advPen[0]);
										driverSalary.setTOTAL_ADVANCE_PENALTY((Double) advPen[1]);

									} else {
										driverSalary.setTOTAL_ADVANCE_PENALTY(0.0);
										driverSalary.setTOTAL_PREVIOUS_ADVANCE(0.0);
									}
									driverSalary.setCOMPANY_ID(userDetails.getCompany_id());
									driverSalaryService.Add_Driver_Salary(driverSalary);
									model.put("save", true);
								}
							}
						}

					}
				}

			} else {
				model.put("emptyNotRange", true);
				return new ModelAndView("redirect:/DR.in", model);
			}

		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			userDetails 					= null;    
			dateRange						= null;    
			driversList	 					= null;    
			driverWiseTripSheetHM			= null;    
			esi_workingDays_company 		= null;    
			fixed_company 					= null;    
			driverSalary					= null;    
			tripSheet						= null;    
			dateRangeFrom 					= "";      
			dateRangeTo 					= "";      
			driverGroupQuery 				= "";      
			driverPerDaySalary				= 0.0;     
			esiAmount 						= 0.0;     
			pfAmount 						= 0.0;     
			salary 							= 0.0;     
			esiPFTotal 						= 0.0;     
			extra_Salary 					= 0.0;     
			total 							= 0.0;     
			allowanceAndOtherTotal 			= 0.0;     
			total_Extra 					= 0.0;     
			total_Advance 					= 0.0;     
			total_Allowance_Amount 			= 0.0;     
			total_OtherExtra_amount 		= 0.0;     
			total_OtherExtraDays 			= 0;       
			total_Working_days 				= (long) 0;
			total_extra_Working_days 		= (long) 0;
			total_Current_Working_days 		= (long) 0;
			total_trip_Sheet		 		= (long) 0;
			total_KM_Usage			 		= (long) 0;    
		}
		return new ModelAndView("redirect:/DR.in", model);
	}
	
	@RequestMapping("/DriverMonthEsiPFReport")
	public ModelAndView DriverMonthEsiPFReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("MONTH_WISE_ESI_PF_REPORT", model);
	}

	// Note : This Controller DailyTripReport and Cash Book Details
	@RequestMapping(value = "/DriverMonthEsiPFReport", method = RequestMethod.POST)
	public ModelAndView DriverMonthEsiPFReport(
			@ModelAttribute("command") @RequestParam("vehicleGroupId") final long DRIVER_GROUP_ID,
			@RequestParam("TRIP_DATE_RANGE") final String TRIP_DATERANGE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<DriverSalaryDto> driverEsiPFList=null; //No Record Found Validation  Logic Y
		try {
			if (TRIP_DATERANGE != "" && TRIP_DATERANGE != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;
				try {

					From_TO_DateRange = TRIP_DATERANGE.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("Halt Driver Date Range:", e);
				}
				VehicleGroup vehicleGroup = vehicleGroupService.getVehicleGroupByID(DRIVER_GROUP_ID);
				model.put("Location", vehicleGroup.getvGroup());

				model.put("dateRange", TRIP_DATERANGE);

				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				CustomUserDetails userName = (CustomUserDetails) auth.getPrincipal();

				model.put("company",
						userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userName.getId()));

				try {

					String Vehicle_group = "", TripOpenDate_Close = "";

					if (DRIVER_GROUP_ID > 0) {
						Vehicle_group = " AND D.vehicleGroupId=" + DRIVER_GROUP_ID + " ";
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						TripOpenDate_Close = "  AND R.SALARY_TO_DATE between '" + dateRangeFrom + "' AND '"
								+ dateRangeTo + "' ";
					}

					String query = " " + Vehicle_group + " " + TripOpenDate_Close + " ";

					List<DriverSalaryDto> Salary = driverSalaryService.Driver_MONTH_WISE_ESI_PF_ReportDate(query,
							userName.getCompany_id());
					driverEsiPFList=DBL.Driver_Salary_ESI_PF_Report(Salary); //No Record Found Validation 
					
					//No Record Found Validation  Logic Start Y
					if(driverEsiPFList == null || driverEsiPFList.isEmpty()) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("MONTH_WISE_ESI_PF_REPORT", model);
					}
					//No Record Found Validation  Logic End  Y
					model.put("salary",driverEsiPFList ); 
					//model.put("salary", DBL.Driver_Salary_ESI_PF_Report(Salary));  //Original code

					Double[] totalGroupSheet = DBL.Tota_TripSalary_Sheet_total(Salary);
					if (totalGroupSheet != null) {
						String TotalWorkingDate = AMOUNTFORMAT.format(totalGroupSheet[0]);
						model.put("TotalWorkingDate", TotalWorkingDate);

						String TotalMONTH_SALARY = AMOUNTFORMAT.format(totalGroupSheet[1]);
						model.put("TotalMONTH_SALARY", TotalMONTH_SALARY);

						String TotalPF = AMOUNTFORMAT.format(totalGroupSheet[2]);
						model.put("TotalPF", TotalPF);

						String TotalESI = AMOUNTFORMAT.format(totalGroupSheet[3]);
						model.put("TotalESI", TotalESI);

						String TotalExtraDuty = AMOUNTFORMAT.format(totalGroupSheet[4]);
						model.put("TotalExtraDuty", TotalExtraDuty);

						String TotalExtraMONTH_SALARY = AMOUNTFORMAT.format(totalGroupSheet[5]);
						model.put("TotalExtraMONTH_SALARY", TotalExtraMONTH_SALARY);

						String TotalPreviousAdvance = AMOUNTFORMAT.format(totalGroupSheet[6]);
						model.put("TotalPreviousAdvance", TotalPreviousAdvance);

						String TotalPenalty = AMOUNTFORMAT.format(totalGroupSheet[7]);
						model.put("TotalPenalty", TotalPenalty);

						String TotalAdvanceDeduction = AMOUNTFORMAT.format(totalGroupSheet[8]);
						model.put("TotalAdvanceDeduction", TotalAdvanceDeduction);

						String TotalPenaltyDeduction = AMOUNTFORMAT.format(totalGroupSheet[9]);
						model.put("TotalPenaltyDeduction", TotalPenaltyDeduction);

						String TOTAL_ALLOWANCE = AMOUNTFORMAT.format(totalGroupSheet[10]);
						model.put("TOTAL_ALLOWANCE", TOTAL_ALLOWANCE);

						String TOTAL_OTHEREXTRA = AMOUNTFORMAT.format(totalGroupSheet[11]);
						model.put("TOTAL_OTHEREXTRA", TOTAL_OTHEREXTRA);

						String TotalNetPay = AMOUNTFORMAT.format(totalGroupSheet[12]);
						model.put("TotalNetPay", TotalNetPay);

						String TotalExtraNetPay = AMOUNTFORMAT.format(totalGroupSheet[13]);
						model.put("TotalExtraNetPay", TotalExtraNetPay);

						String TotalAdvanceBalance = AMOUNTFORMAT.format(totalGroupSheet[14]);
						model.put("TotalAdvanceBalance", TotalAdvanceBalance);

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

		return new ModelAndView("MONTH_WISE_ESI_PF_REPORT", model);
	}

	@RequestMapping(value = "/DriverSalary", method = RequestMethod.GET)
	public ModelAndView NewDriverSalary(@RequestParam("ID") final Integer DRIVER_ID, TripCollectionSheet DayCollection,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {

			DriverDto Trip_JAMA = driverService.getDriver_Header_Show_Details(DRIVER_ID);
			if (Trip_JAMA != null) {
				model.put("driver", DBL.Show_Driver_Header_Details(Trip_JAMA));

				Authentication auth = SecurityContextHolder.getContext().getAuthentication();
				CustomUserDetails userName = (CustomUserDetails) auth.getPrincipal();
				model.put("userName", userName.getId());
				List<DriverSalaryDto> Salary = driverSalaryService.List_Total_OF_DriverSalary_details(DRIVER_ID,
						userName.getCompany_id());
				

				model.put("salary", DBL.Driver_Salary_ESI_PF_Report(Salary));

				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(DRIVER_ID);

				model.put("ReminderCount", (Long) count[0]);
				//model.put("DocumentCount", (Long) count[1]);
				model.put("CommentCount", (Long) count[1]);
				model.put("PhotoCount", (Long) count[2]);
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(DRIVER_ID, userName.getCompany_id()));
		
			}

		} catch (Exception e) {
			LOGGER.error("driver Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("New_DriverSalary", model);
	}

	@RequestMapping(value = "/PaySalary", method = RequestMethod.GET)
	public ModelAndView PaySalary(@RequestParam("DID") final Integer DRIVER_ID,
			@RequestParam("ID") final Long SALARY_ADVANCE_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userName = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			DriverDto Trip_JAMA = driverService.getDriver_Header_Show_Details_ESI_PF(DRIVER_ID);
			if (Trip_JAMA != null) {
				model.put("driver", DBL.Show_Driver_Header_Details(Trip_JAMA));

				if (SALARY_ADVANCE_ID != null) {
					DriverSalaryDto Salary = driverSalaryService.Get_DriverSalary_details_SALARY_ID(SALARY_ADVANCE_ID, userName.getCompany_id());
					model.put("SAID", SALARY_ADVANCE_ID);
					model.put("salary", DBL.Driver_Salary_DETAILS_BY_ID(Salary));

					List<DriverSalaryAdvance> DriverAdvanvce = DriverSalaryAdvanceService
							.GET_DriverSalaryAdvance_details_DRIVER_ID(DRIVER_ID, DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN,
									dateFormat_SQL.format(Salary.getSALARY_FROM_DATE_ON()),
									dateFormat_SQL.format(Salary.getSALARY_TO_DATE_ON()));

					model.put("DriverAdvanvce", DBL.prepare_DriverASalaryAdvance(DriverAdvanvce));

					model.put("OnlyAdvanvceBalance", round(DBL.Total_Only_advanceBalance_total(DriverAdvanvce), 0));
					model.put("OnlyPenaltyBalance", round(DBL.Total_Only_PenaltyBalance_total(DriverAdvanvce), 0));

					model.put("DriverAdvanvceBalance", round(DBL.Total_advanceBalance_total(DriverAdvanvce), 0));
				}

				Authentication authALL = SecurityContextHolder.getContext().getAuthentication();
				String nameALL = authALL.getName();

				model.put("company", userProfileService.findUserProfileByUser_email(nameALL));

				model.put("RECEIVED_BY", userName.getFirstName());
				model.put("RECEIVED_BY_ID", userName.getId());
			}

		} catch (Exception e) {
			LOGGER.error("driver Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("Pay_Salary", model);
	}

	@RequestMapping(value = "/savePaySalary", method = RequestMethod.POST)
	public ModelAndView savePaySalary(@RequestParam("DRIVER_ID") final Integer DRIVER_ID,
			@RequestParam("SAID") final Long SALARY_ADVANCE_ID, final DriverPaidAdvanceDto paidAdvance,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (SALARY_ADVANCE_ID != null) {
				DriverSalaryDto Salary = driverSalaryService.Get_DriverSalary_details_SALARY_ID(SALARY_ADVANCE_ID, userDetails.getCompany_id());

				Double Advance_Amount = 0.0, Penalty_Amount = 0.0;
				if (paidAdvance.getADVANCE_PAID_AMOUNT() != null) {
					Advance_Amount = paidAdvance.getADVANCE_PAID_AMOUNT();
				}
				if (paidAdvance.getPENALTY_PAID_AMOUNT() != null) {
					Penalty_Amount = paidAdvance.getPENALTY_PAID_AMOUNT();
				}
				Double PAID_AMOUNT = Advance_Amount + Penalty_Amount;

				if (Salary.getTOTAL_EXTRA_NETSALARY() >= PAID_AMOUNT) {

					Double OnlyAdvanvceBalance = 0.0;
					Double OnlyPenaltyBalance = 0.0;
					Double AdvanceBalanceAfter = 0.0;

					Double PaidAmountAdvacne = Advance_Amount;
					Double PaidAmountPenalty = Penalty_Amount;
					List<DriverSalaryAdvance> DriverAdvanvce = DriverSalaryAdvanceService
							.GET_DriverSalaryAdvance_details_DRIVER_ID(DRIVER_ID,  DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_OPEN,
									dateFormat_SQL.format(Salary.getSALARY_FROM_DATE_ON()),
									dateFormat_SQL.format(Salary.getSALARY_TO_DATE_ON()));
					if (DriverAdvanvce != null && !DriverAdvanvce.isEmpty()) {

						OnlyAdvanvceBalance = round(DBL.Total_Only_advanceBalance_total(DriverAdvanvce), 0);
						OnlyPenaltyBalance = round(DBL.Total_Only_PenaltyBalance_total(DriverAdvanvce), 0);

						for (DriverSalaryAdvance driverSalaryAdvance : DriverAdvanvce) {

							if (driverSalaryAdvance.getADVANCE_NAME_ID() == DriverAdvanceType.DRIVER_ADVANCE_TYPE_ADVANCE
									&& driverSalaryAdvance.getADVANCE_BALANCE() != 0.0) {

								if (driverSalaryAdvance.getADVANCE_BALANCE() != null) {

									if (driverSalaryAdvance.getADVANCE_BALANCE() > PaidAmountAdvacne) {

										DriverPaidAdvance paid = DBL.perpare_DriverPaidAdvanceDto(paidAdvance);
										paid.setADVANCE_BALANCE(driverSalaryAdvance.getADVANCE_BALANCE());
										paid.setPAID_AMOUNT(PaidAmountAdvacne);
										paid.setDSAID(driverSalaryAdvance.getDSAID());
										paid.setCOMPANY_ID(userDetails.getCompany_id());
										paid.setADVANCE_PAID_TYPE_ID(driverSalaryAdvance.getADVANCE_PAID_TYPE_ID());

										DriverPaidAdvanceService.register_New_DriverPaidAdvance(paid);

										Double paidOldAdvance = DriverPaidAdvanceService
												.Get_TotalSum_OF_Paid_Advacne_Amount_Total(
														driverSalaryAdvance.getDSAID(), paid.getCOMPANY_ID());
										Double Balanceaadvance = driverSalaryAdvance.getADVANCE_AMOUNT()
												- round(paidOldAdvance, 2);

										DriverSalaryAdvanceService.update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK(
												round(Balanceaadvance, 2), driverSalaryAdvance.getDSAID());

									} else if (driverSalaryAdvance.getADVANCE_BALANCE() < PaidAmountAdvacne) {

										DriverPaidAdvance paid = DBL.perpare_DriverPaidAdvanceDto(paidAdvance);
										paid.setADVANCE_PAID_TYPE_ID(driverSalaryAdvance.getADVANCE_PAID_TYPE_ID());
										paid.setADVANCE_BALANCE(driverSalaryAdvance.getADVANCE_BALANCE());

										PaidAmountAdvacne -= driverSalaryAdvance.getADVANCE_BALANCE();

										// pay less amount here
										paid.setPAID_AMOUNT(driverSalaryAdvance.getADVANCE_BALANCE());
										paid.setDSAID(driverSalaryAdvance.getDSAID());

										DriverPaidAdvanceService.register_New_DriverPaidAdvance(paid);

										Double paidOldAdvance = DriverPaidAdvanceService
												.Get_TotalSum_OF_Paid_Advacne_Amount_Total(
														driverSalaryAdvance.getDSAID(), paid.getCOMPANY_ID());
										Double Balanceaadvance = driverSalaryAdvance.getADVANCE_AMOUNT()
												- round(paidOldAdvance, 2);

										DriverSalaryAdvanceService.update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK(
												round(Balanceaadvance, 2), driverSalaryAdvance.getDSAID());

									} else if (driverSalaryAdvance.getADVANCE_BALANCE().equals(PaidAmountAdvacne)) {

										DriverPaidAdvance paid = DBL.perpare_DriverPaidAdvanceDto(paidAdvance);
										paid.setADVANCE_BALANCE(driverSalaryAdvance.getADVANCE_BALANCE());
										paid.setADVANCE_PAID_TYPE_ID(driverSalaryAdvance.getADVANCE_PAID_TYPE_ID());

										PaidAmountAdvacne -= driverSalaryAdvance.getADVANCE_BALANCE();

										paid.setPAID_AMOUNT(PaidAmountAdvacne);
										paid.setDSAID(driverSalaryAdvance.getDSAID());

										DriverPaidAdvanceService.register_New_DriverPaidAdvance(paid);

										// here update Query
										Double paidOldAdvance = DriverPaidAdvanceService
												.Get_TotalSum_OF_Paid_Advacne_Amount_Total(
														driverSalaryAdvance.getDSAID(), paid.getCOMPANY_ID());
										Double Balanceaadvance = driverSalaryAdvance.getADVANCE_AMOUNT()
												- round(paidOldAdvance, 2);

										DriverSalaryAdvanceService
												.update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK_UPDATE_STATUS(
														round(Balanceaadvance, 2), DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_PAID,
														driverSalaryAdvance.getDSAID());

									}
								}

							} else if (driverSalaryAdvance.getADVANCE_NAME_ID() == DriverAdvanceType.DRIVER_ADVANCE_TYPE_PENALTY) {

								if (driverSalaryAdvance.getADVANCE_BALANCE() != null
										&& driverSalaryAdvance.getADVANCE_BALANCE() != 0.0) {

									if (driverSalaryAdvance.getADVANCE_BALANCE() > PaidAmountPenalty) {

										DriverPaidAdvance paid = DBL.perpare_DriverPaidAdvanceDto(paidAdvance);
										paid.setADVANCE_BALANCE(driverSalaryAdvance.getADVANCE_BALANCE());
										paid.setPAID_AMOUNT(PaidAmountPenalty);
										paid.setDSAID(driverSalaryAdvance.getDSAID());
										paid.setADVANCE_PAID_TYPE_ID(driverSalaryAdvance.getADVANCE_PAID_TYPE_ID());

										DriverPaidAdvanceService.register_New_DriverPaidAdvance(paid);

										Double paidOldAdvance = DriverPaidAdvanceService
												.Get_TotalSum_OF_Paid_Advacne_Amount_Total(
														driverSalaryAdvance.getDSAID(), paid.getCOMPANY_ID());
										Double Balanceaadvance = driverSalaryAdvance.getADVANCE_AMOUNT()
												- round(paidOldAdvance, 2);

										DriverSalaryAdvanceService.update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK(
												round(Balanceaadvance, 2), driverSalaryAdvance.getDSAID());

									} else if (driverSalaryAdvance.getADVANCE_BALANCE() < PaidAmountPenalty) {

										DriverPaidAdvance paid = DBL.perpare_DriverPaidAdvanceDto(paidAdvance);
										paid.setADVANCE_BALANCE(driverSalaryAdvance.getADVANCE_BALANCE());

										PaidAmountPenalty -= driverSalaryAdvance.getADVANCE_BALANCE();

										// pay less amount here
										paid.setPAID_AMOUNT(driverSalaryAdvance.getADVANCE_BALANCE());
										paid.setDSAID(driverSalaryAdvance.getDSAID());
										paid.setADVANCE_PAID_TYPE_ID(driverSalaryAdvance.getADVANCE_PAID_TYPE_ID());

										DriverPaidAdvanceService.register_New_DriverPaidAdvance(paid);

										Double paidOldAdvance = DriverPaidAdvanceService
												.Get_TotalSum_OF_Paid_Advacne_Amount_Total(
														driverSalaryAdvance.getDSAID(), paid.getCOMPANY_ID());
										Double Balanceaadvance = driverSalaryAdvance.getADVANCE_AMOUNT()
												- round(paidOldAdvance, 2);

										DriverSalaryAdvanceService.update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK(
												round(Balanceaadvance, 2), driverSalaryAdvance.getDSAID());

									} else if (driverSalaryAdvance.getADVANCE_BALANCE().equals(PaidAmountPenalty)) {

										DriverPaidAdvance paid = DBL.perpare_DriverPaidAdvanceDto(paidAdvance);
										paid.setADVANCE_BALANCE(driverSalaryAdvance.getADVANCE_BALANCE());

										PaidAmountPenalty -= driverSalaryAdvance.getADVANCE_BALANCE();

										paid.setPAID_AMOUNT(PaidAmountAdvacne);
										paid.setDSAID(driverSalaryAdvance.getDSAID());
										paid.setADVANCE_PAID_TYPE_ID(driverSalaryAdvance.getADVANCE_PAID_TYPE_ID());

										DriverPaidAdvanceService.register_New_DriverPaidAdvance(paid);

										// here update Query
										Double paidOldAdvance = DriverPaidAdvanceService
												.Get_TotalSum_OF_Paid_Advacne_Amount_Total(
														driverSalaryAdvance.getDSAID(), paid.getCOMPANY_ID());
										Double Balanceaadvance = driverSalaryAdvance.getADVANCE_AMOUNT()
												- round(paidOldAdvance, 2);

										DriverSalaryAdvanceService
												.update_DriverSalaryAdvance_BALANCE_AMOUNT_SUBTRACK_UPDATE_STATUS(
														round(Balanceaadvance, 2), DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_PAID,
														driverSalaryAdvance.getDSAID());

									}
								}
							}

							if (PaidAmountAdvacne == 0.0 && PaidAmountPenalty == 0.0) {

								break;
							}
						} // close for loof

					}

					Double NetPaySalary = Salary.getTOTAL_EXTRA_NETSALARY();
					Double NetPayAdvance = PAID_AMOUNT;
					Double NetInHandSalary = NetPaySalary - NetPayAdvance;

					if (OnlyAdvanvceBalance != 0.0) {
						AdvanceBalanceAfter = OnlyAdvanvceBalance - Advance_Amount;
					}

					driverSalaryService.UPDATE_DriverSalary_AdvacneAmount_HandAmount_Status(NetPayAdvance,
							Advance_Amount, Penalty_Amount, NetInHandSalary, DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_PAID, OnlyAdvanvceBalance,
							OnlyPenaltyBalance, AdvanceBalanceAfter, Salary.getDSID(), userDetails.getCompany_id());

					DriverSalaryAdvanceService.update_DriverSalaryAdvance_OTHER_PENALTY_UPDATE_STATUS_ZERO(0.0, DriverAdvanceStatus.DRIVER_ADVANCE_STATUS_PAID,
							DRIVER_ID, dateFormat_SQL.format(Salary.getSALARY_FROM_DATE_ON()),
							dateFormat_SQL.format(Salary.getSALARY_TO_DATE_ON()), userDetails.getCompany_id());

				}

			}

		} catch (Exception e) {
			LOGGER.error("driver Page:", e);
			LOGGER.error(e.getLocalizedMessage(), e);
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/PaySalaryPrint.in?DID=" + DRIVER_ID + "&ID=" + SALARY_ADVANCE_ID + "",
				model);
	}

	@RequestMapping(value = "/PaySalaryPrint", method = RequestMethod.GET)
	public ModelAndView PaySalaryPrint(@RequestParam("DID") final Integer DRIVER_ID,
			@RequestParam("ID") final Long SALARY_ADVANCE_ID, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			DriverDto Trip_JAMA = driverService.getDriver_Header_Show_Details_ESI_PF(DRIVER_ID);
			model.put("driver", DBL.Show_Driver_Header_Details(Trip_JAMA));

			if (SALARY_ADVANCE_ID != null) {
				DriverSalaryDto Salary = driverSalaryService.Get_DriverSalary_details_SALARY_ID(SALARY_ADVANCE_ID, userDetails.getCompany_id());
				model.put("SAID", SALARY_ADVANCE_ID);
				model.put("salary", DBL.Driver_Salary_DETAILS_BY_ID(Salary));
			}

			Authentication authALL = SecurityContextHolder.getContext().getAuthentication();
			String nameALL = authALL.getName();

			model.put("company", userProfileService.findUserProfileByUser_email(nameALL));

			model.put("Print_BY", userDetails.getFirstName());

		} catch (Exception e) {
			LOGGER.error("driver Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("Pay_SalaryPrint", model);
	}
}