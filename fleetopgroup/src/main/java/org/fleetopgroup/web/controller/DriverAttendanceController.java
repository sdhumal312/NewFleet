/**
 * 
 */
package org.fleetopgroup.web.controller;

/**
 * 
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.DriverAttandancePointStatus;
import org.fleetopgroup.constant.DriverAttandancePointType;
import org.fleetopgroup.constant.DriverAttandanceStatus;
import org.fleetopgroup.constant.DriverStatus;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverAttendanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

/**
 * @author fleetop
 *
 */
@Controller
public class DriverAttendanceController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IDriverService driverService;
	@Autowired
	private IDriverDocumentService	driverDocumentService;

	
	DriverBL DBL = new DriverBL();

	@Autowired
	private IDriverAttendanceService driverAttendanceService;
	
	@Autowired private ICompanyConfigurationService	companyConfigurationService;

	SimpleDateFormat dateFormatAtt = new SimpleDateFormat("yyyy, MM, dd");
	SimpleDateFormat AttendanceYear = new SimpleDateFormat("yyyy");
	SimpleDateFormat AttendanceMonth = new SimpleDateFormat("MM");
	SimpleDateFormat AttendanceDay = new SimpleDateFormat("dd");

	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping("/ShowDriverAd")
	public ModelAndView ShowDriverAd(@RequestParam("Id") final Integer driver_id, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= null;
		HashMap<String, Object> 	        configuration				    = null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration				= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			
			DriverDto driverAD = driverService.getDriver_Header_Show_Attandance_perday_esi_Details(driver_id, userDetails.getCompany_id());
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			
			if(driverAD != null) {
				model.put("driver", DBL.Show_Driver_Header_Details(driverAD));
				
				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(driver_id);
				
				model.put("ReminderCount", (Long) count[0]);
				//model.put("DocumentCount", (Long) count[1]);
				model.put("CommentCount", (Long) count[1]);
				model.put("PhotoCount", (Long) count[2]);
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(driver_id, userDetails.getCompany_id()));
		
				
				Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = 1;
				c.set(year, month, day);
				int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				// System.out.println("numOfDaysInMonth : " + numOfDaysInMonth);
				String driverStart = dateFormatSQL.format(c.getTime());
				// get date starting date
				model.put("startDate", "" + driverStart + "");
				model.put("startDateNew", "" + dateFormat.format(dateFormatSQL.parse(driverStart)) + "");
				// get date ending date
				c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
				String driverEnd = dateFormatSQL.format(c.getTime());
				model.put("startEnd", "" + driverEnd + "");
				model.put("startEndNew", "" + dateFormat.format(dateFormatSQL.parse(driverEnd)) + "");
				
				Double DriverPerDay = 0.0;
				Double EsiAmount = 0.0;
				Double PFAmount = 0.0;
				Double Salary = 0.0;
				Double total = 0.0;
				Long   TripCount	= (long) 0;
				Long   TotalKM	= (long) 0;
				Double totalDriverBhatta = 0.0;
				Long worked = driverAttendanceService.Driver_Worked_Over_Monthly(driver_id, driverStart, driverEnd);
				TripSheetDto	tripSheet	= driverAttendanceService.Driver_Trip_Details_Over_Monthly(driver_id, driverStart, driverEnd, configuration);
				
				double  tripExpenseAmount = 0.0;
				if((boolean) configuration.get("IncTripExpenseInDriverSalary")){
					 tripExpenseAmount = driverAttendanceService.getTripSheetByDriver(driver_id, driverStart, driverEnd, configuration, userDetails.getCompany_id());
					 if(tripExpenseAmount>0){
						 tripSheet.setExpenseAmount(tripExpenseAmount);
					 }
				}
				
				if (worked != null) {
					
					// System.out.println(worked);
					if (driverAD.getDriver_perdaySalary() != null) {
						DriverPerDay = driverAD.getDriver_perdaySalary();
					}
					
					if(driverAD.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_DAY) {
						Salary = worked * DriverPerDay;						
					} else if(driverAD.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_TRIP) {
						if(tripSheet != null) {							
							TripCount	= tripSheet.getTripSheetCount();
							Salary = TripCount * DriverPerDay;		
						}
					} else if(driverAD.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_KM) {
						if(tripSheet != null) {							
							TotalKM	= tripSheet.getTotalKMUsage();
							Salary = TotalKM * DriverPerDay;																		
						}
					}
					
					if(tripSheet != null && tripSheet.getExpenseAmount() != null && tripSheet.getExpenseAmount() > 0) {
						totalDriverBhatta	= tripSheet.getExpenseAmount();
					}
					
					
					if (Salary != 0) {
						
						if (driverAD.getDriver_esiamount() != null) {
							Double EsiAmountPer = driverAD.getDriver_esiamount();
							
							EsiAmount = PerCentage_Calculation(EsiAmountPer, (Salary+totalDriverBhatta));
						}
						
						if (driverAD.getDriver_pfamount() != null) {
							Double PFAmountPer = driverAD.getDriver_pfamount();
							
							PFAmount = PerCentage_Calculation(PFAmountPer, (Salary+totalDriverBhatta));
						}
						
						total = Salary - EsiAmount - PFAmount;
					}
				}

				model.put("DriverSalaryType", driverAD.getDriverSalaryTypeId());
				model.put("Worked", worked);
				model.put("TripCount", TripCount);
				model.put("TotalKM", TotalKM);
				model.put("PerDay", DriverPerDay);
				model.put("ESIAmount", round(EsiAmount, 2));
				model.put("PFAmount", round(PFAmount, 2));
				model.put("Salary", round(Salary, 2));
				model.put("Total", round(total, 2));
				model.put("totalDriverBhatta", round(totalDriverBhatta, 2));
				
				model.put("SelectJob", 1);
				model.put("SelectPage", 1);
			}

		} catch (Exception e) {
			LOGGER.error("Driver Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("ShowDriverAttendance", model);
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

	@RequestMapping("/ShowDriverAdNext")
	public ModelAndView ShowDriverAdNext(@RequestParam("Id") final Integer driver_id,
			@RequestParam("end") final String endDate, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			DriverDto driverAD = driverService.getDriver_Header_Show_Attandance_perday_esi_Details(driver_id, userDetails.getCompany_id());
			HashMap<String, Object>		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			if(driverAD != null) {
				
				model.put("driver", DBL.Show_Driver_Header_Details(driverAD));
				
				@SuppressWarnings("unused")
				Date date = dateFormatSQL.parse(endDate);// all done
				Calendar c = dateFormatSQL.getCalendar();
				c.add(Calendar.DATE, 1);
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = 1;
				c.set(year, month, day);
				int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				// System.out.println("numOfDaysInMonth : " + numOfDaysInMonth);
				// driver Starting date
				String driverStart = dateFormatSQL.format(c.getTime());
				model.put("startDate", "" + driverStart + "");
				c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
				// driver Ending Date
				String driverEnd = dateFormatSQL.format(c.getTime());
				model.put("startEnd", "" + driverEnd + "");
				model.put("startDateNew", "" + dateFormat.format(dateFormatSQL.parse(driverStart)) + "");
				model.put("startEndNew", "" + dateFormat.format(dateFormatSQL.parse(driverEnd)) + "");
				
				Double DriverPerDay = 0.0;
				Double EsiAmount = 0.0;
				Double PFAmount = 0.0;
				Double Salary = 0.0;
				Double total = 0.0;
				Long   TripCount	= (long) 0;
				Long   TotalKM	= (long) 0;
				Double totalDriverBhatta = 0.0;
				Long worked = driverAttendanceService.Driver_Worked_Over_Monthly(driver_id, driverStart, driverEnd);
				TripSheetDto	tripSheet	= driverAttendanceService.Driver_Trip_Details_Over_Monthly(driver_id, driverStart, driverEnd, configuration);
				
				double  tripExpenseAmount = 0.0;
				if((boolean) configuration.get("IncTripExpenseInDriverSalary")){
					 tripExpenseAmount = driverAttendanceService.getTripSheetByDriver(driver_id, driverStart, driverEnd, configuration, userDetails.getCompany_id());
					 if(tripExpenseAmount>0){
						 tripSheet.setExpenseAmount(tripExpenseAmount);
					 }
				}
				
				if (worked != null) {
					
					// System.out.println(worked);
					if (driverAD.getDriver_perdaySalary() != null) {
						DriverPerDay = driverAD.getDriver_perdaySalary();
					}
					
					if(driverAD.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_DAY) {
						Salary = worked * DriverPerDay;						
					} else if(driverAD.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_TRIP) {
						if(tripSheet != null) {							
							TripCount	= tripSheet.getTripSheetCount();
							Salary = TripCount * DriverPerDay;		
						}
					} else if(driverAD.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_KM) {
						if(tripSheet != null) {							
							TotalKM	= tripSheet.getTotalKMUsage();
							Salary = TotalKM * DriverPerDay;																		
						}
					}
					
					if(tripSheet != null && tripSheet.getExpenseAmount() != null && tripSheet.getExpenseAmount() > 0) {
						totalDriverBhatta	= tripSheet.getExpenseAmount();
					}
					
					if (Salary != 0) {
						
						if (driverAD.getDriver_esiamount() != null) {
							Double EsiAmountPer = driverAD.getDriver_esiamount();
							
							EsiAmount = PerCentage_Calculation(EsiAmountPer, (Salary+totalDriverBhatta));
						}
						
						if (driverAD.getDriver_pfamount() != null) {
							Double PFAmountPer = driverAD.getDriver_pfamount();
							
							PFAmount = PerCentage_Calculation(PFAmountPer, (Salary+totalDriverBhatta));
						}
						
						total = Salary - EsiAmount - PFAmount;
					}
				}
				model.put("DriverSalaryType", driverAD.getDriverSalaryTypeId());
				model.put("Worked", worked);
				model.put("TripCount", TripCount);
				model.put("TotalKM", TotalKM);
				model.put("PerDay", DriverPerDay);
				model.put("ESIAmount", round(EsiAmount, 2));
				model.put("PFAmount", round(PFAmount, 2));
				model.put("Salary", round(Salary, 2));
				model.put("Total", round(total, 2));
				model.put("totalDriverBhatta", round(totalDriverBhatta, 2));
				
				model.put("SelectJob", 1);
				model.put("SelectPage", 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowDriverAttendance", model);
	}

	@RequestMapping("/ShowDriverAdPrev")
	public ModelAndView ShowDriverAdPrev(@RequestParam("Id") final Integer driver_id,
			@RequestParam("start") final String startDate, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			DriverDto driverAD = driverService.getDriver_Header_Show_Attandance_perday_esi_Details(driver_id, userDetails.getCompany_id());
			HashMap<String, Object>		configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG);
			if(driverAD != null) {
				
				model.put("driver", DBL.Show_Driver_Header_Details(driverAD));
				
				@SuppressWarnings("unused")
				Date date = dateFormatSQL.parse(startDate);// all done
				Calendar c = dateFormatSQL.getCalendar();
				c.add(Calendar.DATE, -1);
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = 1;
				c.set(year, month, day);
				int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				// System.out.println("numOfDaysInMonth : " + numOfDaysInMonth);
				// driver Starting Day
				String driverStart = dateFormatSQL.format(c.getTime());
				model.put("startDate", "" + driverStart + "");
				c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
				// driver Ending Date
				String driverEnd = dateFormatSQL.format(c.getTime());
				model.put("startEnd", "" + driverEnd + "");
				model.put("startDateNew", "" + dateFormat.format(dateFormatSQL.parse(driverStart)) + "");
				model.put("startEndNew", "" + dateFormat.format(dateFormatSQL.parse(driverEnd)) + "");
				
				Double DriverPerDay = 0.0;
				Double EsiAmount = 0.0;
				Double PFAmount = 0.0;
				Double Salary = 0.0;
				Double total = 0.0;
				Long   TripCount	= (long) 0;
				Long   TotalKM	= (long) 0;
				Double	totalDriverBhatta = 0.0;
				Long worked = driverAttendanceService.Driver_Worked_Over_Monthly(driver_id, driverStart, driverEnd);
				TripSheetDto	tripSheet	= driverAttendanceService.Driver_Trip_Details_Over_Monthly(driver_id, driverStart, driverEnd, configuration);
				
				double  tripExpenseAmount = 0.0;
				if((boolean) configuration.get("IncTripExpenseInDriverSalary")){
					 tripExpenseAmount = driverAttendanceService.getTripSheetByDriver(driver_id, driverStart, driverEnd, configuration, userDetails.getCompany_id());
					 if(tripExpenseAmount>0){
						 tripSheet.setExpenseAmount(tripExpenseAmount);
					 }
				}
				
				if (worked != null) {
					
					// System.out.println(worked);
					if (driverAD.getDriver_perdaySalary() != null) {
						DriverPerDay = driverAD.getDriver_perdaySalary();
					}
					
					
					if(driverAD.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_DAY) {
						Salary = worked * DriverPerDay;						
					} else if(driverAD.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_TRIP) {
						if(tripSheet != null) {							
							TripCount	= tripSheet.getTripSheetCount();
							Salary = TripCount * DriverPerDay;		
						}
					} else if(driverAD.getDriverSalaryTypeId() == DriverStatus.DRIVER_SALARY_TYPE_PER_KM) {
						if(tripSheet != null) {							
							TotalKM	= tripSheet.getTotalKMUsage();
							Salary = TotalKM * DriverPerDay;																		
						}
					}
					
					if(tripSheet != null && tripSheet.getExpenseAmount() != null && tripSheet.getExpenseAmount() > 0) {
						totalDriverBhatta	= tripSheet.getExpenseAmount();
					}
					
					
					if (Salary != 0) {
						
						if (driverAD.getDriver_esiamount() != null) {
							Double EsiAmountPer = driverAD.getDriver_esiamount();
							
							EsiAmount = PerCentage_Calculation(EsiAmountPer, (Salary+totalDriverBhatta));
						}
						
						if (driverAD.getDriver_pfamount() != null) {
							Double PFAmountPer = driverAD.getDriver_pfamount();
							
							PFAmount = PerCentage_Calculation(PFAmountPer, (Salary+totalDriverBhatta));
						}
						
						total = Salary - EsiAmount - PFAmount;
					}
				}
				model.put("DriverSalaryType", driverAD.getDriverSalaryTypeId());
				model.put("Worked", worked);
				model.put("TripCount", TripCount);
				model.put("TotalKM", TotalKM);
				model.put("PerDay", DriverPerDay);
				model.put("ESIAmount", round(EsiAmount, 2));
				model.put("PFAmount", round(PFAmount, 2));
				model.put("Salary", round(Salary, 2));
				model.put("Total", round(total, 2));
				model.put("totalDriverBhatta", round(totalDriverBhatta, 2));
				
				model.put("SelectJob", 1);
				model.put("SelectPage", 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowDriverAttendance", model);
	}

	@RequestMapping(value = "/scheduleDriverAttendance", method = RequestMethod.GET)
	public void scheduleDriverAttendance( @RequestParam("Id") final Integer DriverId,
			@RequestParam("start") final String startDate, 
			@RequestParam("end") final String endDate, HttpServletRequest request, 
			HttpServletResponse response) throws Exception {
		
		CustomUserDetails			userDetails 	= null;
		List<DriverAttendanceDto> 	group  			= null;
		List<DriverAttendanceDto> 	DriverAtten 	= null;
		DriverAttendanceDto 		wadd 			= null;
		
		
		
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			group 		= new ArrayList<DriverAttendanceDto>();
			DriverAtten = driverAttendanceService.list_Date_DriverAttendance(DriverId, startDate, endDate, userDetails.getCompany_id());
			
			
			if (DriverAtten != null && !DriverAtten.isEmpty()) {
				
				for (DriverAttendanceDto add : DriverAtten) {
					wadd = new DriverAttendanceDto();
					
					wadd.setTRIP_ROUTE_NAME(add.getTRIP_ROUTE_NAME());
					wadd.setTRIPSHEETID(add.getTRIPSHEETID());			
					wadd.setTRIPSHEETNUMBER(add.getDAID());
					wadd.setATTENDANCE_STATUS_ID(add.getATTENDANCE_STATUS_ID());
					wadd.setATTENDANCE_STATUS(DriverAttandanceStatus.getStausName(add.getATTENDANCE_STATUS_ID()));
					wadd.setTRIP_ROUTE_ID(add.getTRIP_ROUTE_ID());
					
					if (add.getD_ATTENDANCE_DATE() != null) {
						wadd.setATTENDANCE_DATE(dateFormatAtt.format(add.getD_ATTENDANCE_DATE()));
						wadd.setDADATE(AttendanceDay.format(add.getD_ATTENDANCE_DATE()));
						wadd.setDAMONTH("" + (Integer.parseInt(AttendanceMonth.format(add.getD_ATTENDANCE_DATE())) - 1));
						
						wadd.setDAYEAR(AttendanceYear.format(add.getD_ATTENDANCE_DATE()));
					}
					//wadd.setDAID(add.getDAID()); 
					group.add(wadd);
				}
			}
			
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(group));
		}catch(Exception e) {
			LOGGER.error("Err"+e);
		}

	}

	@RequestMapping("/ShowDriverAdPOINT")
	public ModelAndView ShowDriverAdPOINT(@RequestParam("Id") final Integer driver_id,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		DriverDto		driver		= null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driver	= driverService.getDriver_Header_Show_Details(driver_id);
			if(driver != null) {
				
				model.put("driver", DBL.Show_Driver_Header_Details(driver));
				
				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(driver_id);
				
				model.put("ReminderCount", (Long) count[0]);
				//model.put("DocumentCount", (Long) count[1]);
				model.put("CommentCount", (Long) count[1]);
				model.put("PhotoCount", (Long) count[2]);
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(driver_id, userDetails.getCompany_id()));
		
				
				Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = 1;
				c.set(year, month, day);
				int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				String driverStart = dateFormatSQL.format(c.getTime());
				// get date starting date
				model.put("startDate", "" + driverStart + "");
				// get date ending date
				c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
				String driverEnd = dateFormatSQL.format(c.getTime());
				model.put("startEnd", "" + driverEnd + "");
				model.put("startEndNew", "" + dateFormat.format(dateFormatSQL.parse(driverEnd)) + "");
				model.put("startDateNew", "" + dateFormat.format(dateFormatSQL.parse(driverStart)) + "");
				
				Double totalPoint = driverAttendanceService.Driver_Point_Over_Monthly(driver_id, driverStart, driverEnd, userDetails.getCompany_id());
				if (totalPoint != null) {
					model.put("WorkedPoint", totalPoint);
					
				}
				
			}
			model.put("SelectJob", 1);
			model.put("SelectPage", 1);


		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowDriverAtPoint", model);
	}

	@RequestMapping("/ShowDAdPointNext")
	public ModelAndView ShowDriverAdPointNext(@RequestParam("Id") final Integer driver_id,
			@RequestParam("end") final String endDate, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		DriverDto		driver		= null;
		try {
			driver	= driverService.getDriver_Header_Show_Details(driver_id);
			if(driver != null) {
				CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				model.put("driver", DBL.Show_Driver_Header_Details(driver));
				@SuppressWarnings("unused")
				Date date = dateFormatSQL.parse(endDate);// all done
				Calendar c = dateFormatSQL.getCalendar();
				c.add(Calendar.DATE, 1);
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = 1;
				c.set(year, month, day);
				int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				System.out.println("numOfDaysInMonth : " + numOfDaysInMonth);
				// driver Starting date
				String driverStart = dateFormatSQL.format(c.getTime());
				model.put("startDate", "" + driverStart + "");
				c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
				// driver Ending Date
				String driverEnd = dateFormatSQL.format(c.getTime());
				model.put("startEnd", "" + driverEnd + "");
				model.put("startEndNew", "" + dateFormat.format(dateFormatSQL.parse(driverEnd)) + "");
				model.put("startDateNew", "" + dateFormat.format(dateFormatSQL.parse(driverStart)) + "");

				Double totalPoint = driverAttendanceService.Driver_Point_Over_Monthly(driver_id, driverStart, driverEnd, userDetails.getCompany_id());
				if (totalPoint != null) {
					model.put("WorkedPoint", totalPoint);
				}
			}
			model.put("SelectJob", 1);
			model.put("SelectPage", 1);


		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowDriverAtPoint", model);
	}

	@RequestMapping("/ShowDAdPointPrev")
	public ModelAndView ShowDriverAdPointPrev(@RequestParam("Id") final Integer driver_id,
			@RequestParam("start") final String startDate, final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		DriverDto		driver		= null;
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driver = driverService.getDriver_Header_Show_Details(driver_id);
			if(driver != null) {
				model.put("driver", DBL.Show_Driver_Header_Details(driver));
				@SuppressWarnings("unused")
				Date date = dateFormatSQL.parse(startDate);// all done
				Calendar c = dateFormatSQL.getCalendar();
				c.add(Calendar.DATE, -1);
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = 1;
				c.set(year, month, day);
				int numOfDaysInMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
				System.out.println("numOfDaysInMonth : " + numOfDaysInMonth);
				// driver Starting Day
				String driverStart = dateFormatSQL.format(c.getTime());
				model.put("startDate", "" + driverStart + "");
				c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
				// driver Ending Date
				String driverEnd = dateFormatSQL.format(c.getTime());
				model.put("startEnd", "" + driverEnd + "");
				model.put("startEndNew", "" + dateFormat.format(dateFormatSQL.parse(driverEnd)) + "");
				model.put("startDateNew", "" + dateFormat.format(dateFormatSQL.parse(driverStart)) + "");

				Double totalPoint = driverAttendanceService.Driver_Point_Over_Monthly(driver_id, driverStart, driverEnd, userDetails.getCompany_id());
				if (totalPoint != null) {
					model.put("WorkedPoint", totalPoint);
				}

			}
			model.put("SelectJob", 1);
			model.put("SelectPage", 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowDriverAtPoint", model);
	}

	@RequestMapping(value = "/scheduleDriverAdPoint", method = RequestMethod.GET)
	public void scheduleDriverAttendancePoint(@RequestParam("Id") final Integer DriverId,
			@RequestParam("start") final String startDate, @RequestParam("end") final String endDate,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<DriverAttendanceDto> group = new ArrayList<DriverAttendanceDto>();
		List<DriverAttendanceDto> DriverAttPoint = driverAttendanceService.list_OF_Point_Date_DriverAttendance(DriverId,
				startDate, endDate, userDetails.getCompany_id());
		//List<TripSheet>		tripSheetLIst	= tripSheetService.list_Of_DriverID_to_Driver_BATA_Details(DriverId, startDate, endDate, userDetails.getCompany_id());
		
		//HashMap<String, TripSheet>	driverTripSheetMap	=  DBL.getTripSheetDriverMap(tripSheetLIst, DriverId);
		
		if (DriverAttPoint != null && !DriverAttPoint.isEmpty()) {
			
			DriverAttendanceDto wadd = null;
			for (DriverAttendanceDto add : DriverAttPoint) {
				wadd = new DriverAttendanceDto();

				wadd.setTRIP_ROUTE_NAME(add.getTRIP_ROUTE_NAME());
				wadd.setTRIP_ROUTE_ID(add.getTRIP_ROUTE_ID());
				wadd.setTRIPSHEETID(add.getTRIPSHEETID());
				wadd.setTRIPSHEETNUMBER(add.getDAID());
				wadd.setATTENDANCE_STATUS_ID(add.getATTENDANCE_STATUS_ID());
				wadd.setATTENDANCE_STATUS(DriverAttandanceStatus.getStausName(add.getATTENDANCE_STATUS_ID()));
				wadd.setPOINT_STATUS_ID(add.getPOINT_STATUS_ID());
				wadd.setPOINT_STATUS(DriverAttandancePointStatus.getPointStausName(add.getPOINT_STATUS_ID()));
				wadd.setPOINT_TYPE_ID(add.getPOINT_TYPE_ID());
				wadd.setPOINT_TYPE(DriverAttandancePointType.getPointTypeName(add.getPOINT_TYPE_ID()));
				wadd.setDRIVER_POINT(add.getDRIVER_POINT());
				if (add.getD_ATTENDANCE_DATE() != null) {
					wadd.setATTENDANCE_DATE(dateFormatAtt.format(add.getD_ATTENDANCE_DATE()));
					wadd.setDADATE(AttendanceDay.format(add.getD_ATTENDANCE_DATE()));
					wadd.setDAMONTH("" + (Integer.parseInt(AttendanceMonth.format(add.getD_ATTENDANCE_DATE())) - 1));

					wadd.setDAYEAR(AttendanceYear.format(add.getD_ATTENDANCE_DATE()));
				}

				
				//wadd.setDAID(add.getDAID()); 
				group.add(wadd);
			}
		}

		
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(group));
	}

}

