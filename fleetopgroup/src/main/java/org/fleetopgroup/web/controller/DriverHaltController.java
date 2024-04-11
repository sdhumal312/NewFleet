/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.DriverAttandancePointStatus;
import org.fleetopgroup.constant.DriverAttandancePointType;
import org.fleetopgroup.constant.DriverStatus;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.bl.TripSheetBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverDto;
import org.fleetopgroup.persistence.dto.DriverHaltDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverAttendance;
import org.fleetopgroup.persistence.model.DriverHalt;
import org.fleetopgroup.persistence.serviceImpl.IDriverAttendanceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IDriverHaltService;
import org.fleetopgroup.persistence.serviceImpl.IDriverService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class DriverHaltController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IUserProfileService userProfileService;

	@Autowired
	private IDriverHaltService driverHaltService;

	@Autowired
	private IDriverAttendanceService DriverAttendanceService;

	@Autowired
	private IDriverService driverService;
	
	@Autowired IDriverDocumentService	driverDocumentService;
	
//	@Autowired private ICompanyConfigurationService	companyConfigurationService;
//	@Autowired private IVehicleService	vehicleService;
	
	
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");

	DriverBL DBL = new DriverBL();

	// @Autowired
	// private ITripSheetService TripSheetService;

	TripSheetBL VehStBL = new TripSheetBL();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat driverAttendanceFormat = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormatTime = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");

	// Show Add Expense Page
	@RequestMapping(value = "/DriverHaltNew", method = RequestMethod.GET)
	public ModelAndView addDriverHalt(@RequestParam("driverId") final Integer driverId, final HttpServletRequest result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(driverId != null && driverId > 0) {
			Driver driver = 	driverService.getDriver(driverId, userDetails);
			model.put("driverId", driverId);
			model.put("driverName", driver.getDriver_firstname());
			}
			UserProfileDto Orderingname = userProfileService
					.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(userDetails.getEmail());

			model.put("haltBy", Orderingname.getFirstName() + "_" + Orderingname.getLastName());
			model.put("place", Orderingname.getBranch_name());
			model.put("placeId", Orderingname.getBranch_id());

		} catch (Exception e) {
			LOGGER.error("trip Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("add_IN_DriverHalt", model);
	}

	// update Expense TripSheet and also Amount
	@RequestMapping(value = "/saveDriverHalt", method = RequestMethod.POST)
	public ModelAndView updateHaltAmountTripsheet(@ModelAttribute("command") DriverHalt DriverHalt,
			@RequestParam("DRIVERID") final Integer Driver_ID, @RequestParam("HALT_DATE") final String haltDateRange,
			final HttpServletRequest request) throws Exception {
		
		Map<String, Object> 				model 					= null;
		CustomUserDetails 					userDetails 			= null;
		ValueObject							dateRangeValObj			= null;
		DriverHalt 							driverHalt				= null; 
		String 								dateRangeFrom 			= "";
		String 								dateRangeTo 			= "";
		Double 								TotalPoint 				= 0.0;
		java.util.Date 						fromdate 				= null;
		java.sql.Date 						fromDate 				= null;
		java.util.Date 						todate 					= null;
		java.sql.Date 						toDate 					= null;
		java.util.Date 						currentDateUpdate 		= null; 
		
		try {
			
			model 					= new HashMap<String, Object>();                                                            
			userDetails 			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			if (haltDateRange != "" && Driver_ID != null) {

				dateRangeValObj		= DateTimeUtility.getStartAndEndDateStr(haltDateRange);

				dateRangeFrom 		= dateRangeValObj.getString(DateTimeUtility.FROM_DATE);
				dateRangeTo 		= dateRangeValObj.getString(DateTimeUtility.TO_DATE);

				driverHalt 			= VehStBL.prepare_Driver_Halt_Details(DriverHalt);

				if (Driver_ID != null) {
					driverHalt.setDRIVERID(Driver_ID);

					if (DriverHalt.getVID() != null) {
						driverHalt.setVID(DriverHalt.getVID());
					}else {
						driverHalt.setVID(0);
					}
					if (DriverHalt.getVID() != null) {
						driverHalt.setVID(DriverHalt.getVID());
					} else {
						driverHalt.setVID(0);
					}

					if (dateRangeFrom != "" && dateRangeTo != "") {
						fromdate 	= dateFormat.parse(dateRangeFrom);
						fromDate 	= new Date(fromdate.getTime());
						
						todate 		= dateFormat.parse(dateRangeTo);
						toDate 		= new Date(todate.getTime());

						driverHalt.setHALT_DATE_FROM(fromDate);
						driverHalt.setHALT_DATE_TO(toDate);

						Double Point = VehStBL.Driver_Halt_Point(fromDate, toDate);
						
						TotalPoint += Point;
						driverHalt.setHALT_POINT(Point);

						driverHalt.setHALT_AMOUNT(Point * DriverHalt.getHALT_AMOUNT());

					}

					driverHalt.setTRIPSHEETID((long) 0);

					List<DriverHalt> DriverHaltVali = driverHaltService.Validate_list_Date_DriverHalt(Driver_ID,
							driverAttendanceFormat.format(driverHalt.getHALT_DATE_FROM()),
							driverAttendanceFormat.format(driverHalt.getHALT_DATE_TO()), driverHalt.getCOMPANY_ID());
					if (DriverHaltVali != null && !DriverHaltVali.isEmpty()) {
						model.put("AlreadyHalt", true);
						return new ModelAndView("redirect:/DriverHaltNew.in?driverId="+Driver_ID+"", model);
						
					} else {

						driverHaltService.register_New_DriverHalt(driverHalt);
						
						currentDateUpdate = dateFormat.parse(dateFormat.format(new java.util.Date()));
						
						if(todate.getTime() >= currentDateUpdate.getTime()) {
							driverService.Update_Driver_Status(DriverStatus.DRIVER_STATUS_LOCAL_HALT, Driver_ID);							
						}
						
						LocalDate dHaltstartCle = LocalDate.parse(driverAttendanceFormat.format(driverHalt.getHALT_DATE_FROM())),
								dHaltendCle = LocalDate.parse(driverAttendanceFormat.format(driverHalt.getHALT_DATE_TO()));

						LocalDate nextCle = dHaltstartCle.minusDays(1);
						while ((nextCle = nextCle.plusDays(1)).isBefore(dHaltendCle.plusDays(1))) {
							// System.out.println(nextCle);
							try {
								if (nextCle != null) {
									DriverAttendance attendanceCle = DBL.Driver_HALT_Attendance_to_DriverHaltTable(Driver_ID, driverHalt.getDHID());

									java.util.Date attendanceDate = driverAttendanceFormat.parse("" + nextCle);
									java.sql.Date Reported_DateCle = new Date(attendanceDate.getTime());
									attendanceCle.setATTENDANCE_DATE(Reported_DateCle);

									attendanceCle.setPOINT_STATUS_ID(
											DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT);
									// attendanceCle.setPOINT_STATUS(
									// DriverAttandancePointStatus.DRIVER_ATTANDANCE_POINT_STATUS_POINT_NAME);
									attendanceCle.setPOINT_TYPE_ID(
											DriverAttandancePointType.ATTANDANCE_POINT_TYPE_LOCALHALT);
									// attendanceCle
									// .setPOINT_TYPE(DriverAttandancePointType.ATTANDANCE_POINT_TYPE_HALT_NAME);
									attendanceCle.setDRIVER_POINT(1.0);
									attendanceCle.setCOMPANY_ID(driverHalt.getCOMPANY_ID());
									attendanceCle.setCREATED_BY_ID(userDetails.getId());

									// validate
									DriverAttendance validateHalt = DriverAttendanceService
											.validate_DriverAttendance_Halt_details(Driver_ID,
													driverAttendanceFormat.format(attendanceCle.getATTENDANCE_DATE()),
													attendanceCle.getPOINT_TYPE_ID(), userDetails.getCompany_id());
									if (validateHalt != null) {
										DriverAttendanceService.Update_DriverAttendance_Halt_point(
												attendanceCle.getATTENDANCE_STATUS_ID(),
												attendanceCle.getPOINT_STATUS_ID(), attendanceCle.getPOINT_TYPE_ID(),
												1.0, validateHalt.getDAID(), userDetails.getCompany_id());
										model.put("AlreadyHalt", true);
									} else {
										// save
										DriverAttendanceService.addDriverAttendance(attendanceCle);
										model.put("Haltsuccess", true);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
								model.put("danger", true);
								return new ModelAndView("redirect:/DriverHaltNew.in?driverId="+Driver_ID+"", model);
							}
						} // close while loop
					}
				} // driver id condition
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("danger", true);
			return new ModelAndView("redirect:/showDriverBata.in?driver_id="+Driver_ID+"", model);
		}finally {
			//configuration = null;
		}
		return new ModelAndView("redirect:/showDriverBata.in?driver_id="+Driver_ID+"", model);
	}

	// Note: This /showDriverBata Controller show Driver Bata Details and
	// HaltBata Details
	@RequestMapping(value = "/showDriverBata", method = RequestMethod.GET)
	public ModelAndView showDriverFamily(@RequestParam("driver_id") final Integer Driver_id,
			final HttpServletRequest request, final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		DriverDto driverDto = null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driverDto = driverService.getDriver_Header_Show_Details(Driver_id);
			if (driverDto != null) {
				model.put("driver", DBL.Show_Driver_Header_Details(driverDto));
				/* list of driver reminder */

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
				// get date ending date
				c.add(Calendar.DAY_OF_MONTH, numOfDaysInMonth - 1);
				String driverEnd = dateFormatSQL.format(c.getTime());
				model.put("startEnd", "" + driverEnd + "");

				// List<TripSheetDto> tripSheet =
				// TripSheetService.list_Of_DriverID_to_Driver_BATA_Details(Driver_id,
				// driverStart, driverEnd, userDetails.getCompany_id());

				List<DriverHaltDto> DriverHaltVali = driverHaltService.list_Of_DriverID_to_DriverHalt_BATA_Details(
						Driver_id, driverStart, driverEnd, userDetails.getCompany_id());
				// model.put("DBata", DBL.Show_Driver_BataDetails_ToTripsheet(tripSheet,
				// DriverHaltVali));
				model.put("DBata", DriverHaltVali);

				// model.put("TotalBataAmount", DBL.Total_BataDetails_ToTripsheet(tripSheet,
				// DriverHaltVali));

				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(Driver_id);

				model.put("ReminderCount", (Long) count[0]);
				//model.put("DocumentCount", (Long) count[1]);
				model.put("CommentCount", (Long) count[1]);
				model.put("PhotoCount", (Long) count[2]);
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(Driver_id, userDetails.getCompany_id()));
		
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowDriverBata", model);
	}

	// Note: This /showDriverBata Controller show Driver Bata Details and
	// HaltBata Details
	@RequestMapping(value = "/editDriverHalt", method = RequestMethod.GET)
	public ModelAndView editDriverHalt(@RequestParam("DID") final Integer Driver_id,
			@RequestParam("DHID") final Long DHID, final HttpServletRequest request,
			final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		DriverDto driverDto = null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			DriverHaltDto DriverHaltVali = driverHaltService.GET_DriverHalt_Edit_BATA_Details(DHID,
					userDetails.getCompany_id());
			if (DriverHaltVali != null) {
				driverDto = driverService.getDriver_Header_Show_Details(Driver_id);
				if (driverDto != null) {
					model.put("driver", DBL.Show_Driver_Header_Details(driverDto));
					/* list of driver reminder */
				}
				model.put("DriverHalt", DriverHaltVali);
			} else {
				return new ModelAndView("redirect:/showDriverBata.in?driver_id=" + Driver_id + "", model);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("EditDriverBata", model);
	}

	@RequestMapping(value = "/updateLocalDriverHalt", method = RequestMethod.POST)
	public ModelAndView updateLocalDriverHalt(@ModelAttribute("command") DriverHalt DriverHalt,
			@RequestParam("DRIVERID") final Integer Driver_ID, @RequestParam("DHID") final Long DHID,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			if (Driver_ID != null) {

				DriverHaltDto DriverHaltVali = driverHaltService.GET_DriverHalt_Edit_BATA_Details(DHID,
						userDetails.getCompany_id());
				if (DriverHaltVali != null) {

					DriverHalt tHalt = VehStBL.Update_prepare_Driver_Halt_Details(DriverHalt);

					try {

						if (DriverHalt.getHALT_AMOUNT() < DriverHaltVali.getHALT_AMOUNT()) {
							tHalt.setHALT_AMOUNT(DriverHaltVali.getHALT_POINT() * DriverHalt.getHALT_AMOUNT());
						} else if (DriverHalt.getHALT_AMOUNT() > DriverHaltVali.getHALT_AMOUNT()) {
							tHalt.setHALT_AMOUNT(DriverHaltVali.getHALT_POINT() * DriverHalt.getHALT_AMOUNT());
						} else {
							tHalt.setHALT_AMOUNT(DriverHaltVali.getHALT_AMOUNT());
						}
						driverHaltService.update_DriverHalt(tHalt);
						model.put("update", true);

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					return new ModelAndView("redirect:/showDriverBata.in?driver_id=" + Driver_ID + "", model);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("danger", true);
			return new ModelAndView("redirect:/DriverHaltNew.in?driverId="+Driver_ID+"", model);
		}
		return new ModelAndView("redirect:/showDriverBata.in?driver_id=" + Driver_ID + "", model);
	}

	@RequestMapping(value = "/deleteLocalDriverHalt", method = RequestMethod.GET)
	public ModelAndView deleteLocalDriverHalt(
			@RequestParam("DID") final Integer Driver_ID, @RequestParam("DHID") final Long DHID,
			final HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			if (Driver_ID != null) {

				DriverHaltDto DriverHaltVali = driverHaltService.GET_DriverHalt_Edit_BATA_Details(DHID,
						userDetails.getCompany_id());
				if (DriverHaltVali != null) {
					try {

						driverHaltService.delete_DriverHalt(DHID, userDetails.getCompany_id());

						DriverAttendanceService.DELETE_DRIVERATTENDANCE_LOCAL_HALTBATA_ID(DHID);

						driverService.Update_Driver_Status(DriverStatus.DRIVER_STATUS_ACTIVE, Driver_ID);

						model.put("delete", true);
					} catch (Exception e) {
						e.printStackTrace();
					}

				} else {
					return new ModelAndView("redirect:/showDriverBata.in?driver_id=" + Driver_ID + "", model);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			model.put("danger", true);
			return new ModelAndView("redirect:/DriverHaltNew.in?driverId="+Driver_ID+"", model);
		}
		return new ModelAndView("redirect:/showDriverBata.in?driver_id=" + Driver_ID + "", model);
	}

	// Note: This /searchDriverBata Controller show Driver Bata Details and
	// HaltBata Details
	@RequestMapping(value = "/searchDriverBata", method = RequestMethod.POST)
	public ModelAndView searchDriverBata(@RequestParam("DRIVERID") final Integer Driver_id,
			@RequestParam("DRIVER_BATA_DATE") final String DRIVER_BATA_DATE, final HttpServletRequest request,
			final HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		DriverDto driver = null;
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			driver = driverService.getDriver_Header_Show_Details(Driver_id);
			if (driver != null) {

				model.put("driver", DBL.Show_Driver_Header_Details(driver));
				/* list of driver reminder */

				String dateRangeFrom = "", dateRangeTo = "";

				String[] Issues_TO_DateRange = null;
				try {

					Issues_TO_DateRange = DRIVER_BATA_DATE.split(" to ");

					dateRangeFrom = Issues_TO_DateRange[0];
					dateRangeTo = Issues_TO_DateRange[1];

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				// List<TripSheetDto> tripSheet =
				// TripSheetService.list_Of_DriverID_to_Driver_BATA_Details(Driver_id,
				// dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
				List<DriverHaltDto> DriverHaltVali = driverHaltService.list_Of_DriverID_to_DriverHalt_BATA_Details(
						Driver_id, dateRangeFrom, dateRangeTo, userDetails.getCompany_id());
				// model.put("DBata", DBL.Show_Driver_BataDetails_ToTripsheet(tripSheet,
				// DriverHaltVali));
				model.put("DBata", DriverHaltVali);

				// model.put("TotalBataAmount", DBL.Total_BataDetails_ToTripsheet(tripSheet,
				// DriverHaltVali));

				Object[] count = driverService.countTotal_REMINDER_DOC_COMMENT__AND_PHOTO(Driver_id);

				model.put("ReminderCount", (Long) count[0]);
				//model.put("DocumentCount", (Long) count[1]);
				model.put("CommentCount", (Long) count[1]);
				model.put("PhotoCount", (Long) count[2]);
				model.put("DocumentCount", driverDocumentService.getDriverDocumentCount(Driver_id, userDetails.getCompany_id()));
		
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("ShowDriverBata", model);
	}

	@RequestMapping(value = "/getALLDriverHalt", method = RequestMethod.POST)
	public void getALLDriverHalt(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<DriverDto> Driver = new ArrayList<DriverDto>();
		// Note: This search all driver active list
		List<Driver> driverName = driverService.Search_ALL_Driver_LIST_AJAX(term);
		if (driverName != null && !driverName.isEmpty()) {
			for (Driver add : driverName) {
				DriverDto wadd = new DriverDto();
				wadd.setDriver_id(add.getDriver_id());
				wadd.setDriver_empnumber(add.getDriver_empnumber());
				wadd.setDriver_firstname(add.getDriver_firstname());
				wadd.setDriver_Lastname(add.getDriver_Lastname());
				wadd.setDriver_fathername(add.getDriver_fathername());
				Driver.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(Driver));
	}

	@Scheduled(cron = "0 10 0,12 * * *")
	public void doScheduledWork() {

		try {

			java.util.Date currentDateUpdate = new java.util.Date();
			Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
			String UpdateDate = dateFormatSQL.format(toDate);
			driverService.Update_DriverHalt_Status_To_Active(UpdateDate);
			LOGGER.warn("Auto Update Halt to Active");
		} catch (Exception e) {
			LOGGER.error("Auto Update Halt to Active", e);
			e.printStackTrace();
		}
	}
	

}
