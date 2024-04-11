package org.fleetopgroup.persistence.bl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverReminderDto;
import org.fleetopgroup.persistence.dto.DriverReminderHistoryDto;
import org.fleetopgroup.persistence.model.Driver;
import org.fleetopgroup.persistence.model.DriverReminder;
import org.fleetopgroup.persistence.model.DriverReminderHistory;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.web.multipart.MultipartFile;


public class DriverReminderBL {

	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat_name = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat sqlFormatTime = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	
	public DriverReminderBL() {
	}

	// get logic in driver Information
	public DriverReminder prepareDriverReminder(DriverReminderDto driverReminderBean) {
		DriverReminder driverReminder = new DriverReminder();

		String[] From_ToDate = null;

		driverReminder.setDriver_id(driverReminderBean.getDriver_id());

		driverReminder.setDriver_remid(driverReminderBean.getDriver_remid());
		//driverReminder.setDriver_remindertype(driverReminderBean.getDriver_remindertype());
		driverReminder.setDriverRenewalTypeId(driverReminderBean.getDriverRenewalTypeId());
		driverReminder.setDriver_dlnumber(driverReminderBean.getDriver_dlnumber());

		try {
			if(!driverReminderBean.getDriver_dlfrom_show().isEmpty()) {
				From_ToDate = driverReminderBean.getDriver_dlfrom_show().split("  to  ");
				java.util.Date date = dateFormat.parse(From_ToDate[0]);
				java.sql.Date FromDate = new Date(date.getTime());
				driverReminder.setDriver_dlfrom(FromDate);
				java.util.Date dateTo = dateFormat.parse(From_ToDate[1]);
				java.sql.Date ToDate = new Date(dateTo.getTime());
				driverReminder.setDriver_dlto(ToDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		driverReminder.setDriver_timethreshold(driverReminderBean.getDriver_timethreshold());
		driverReminder.setDriver_periedthreshold(driverReminderBean.getDriver_periedthreshold());

		/* due end date */
		// get the date from database is 10/12/2015
		String duedate = From_ToDate[1];

		// that date split the date & month & year date[0]=10,
		// date[1]=12,date[2]=2015
		String due_EndDate[] = duedate.split("-");

		/* due timethreshold and peried of reminder */
		Integer duetimeandperied;

		// calculation of the reminder before date of reminder
		Integer timeandperied = (driverReminderBean.getDriver_timethreshold())
				* (driverReminderBean.getDriver_periedthreshold());

		// timeandperied=3*0 days; or timeandperied=3*7 weeks or
		// timeandperied=3*28 month

		if (timeandperied == 0) {
			duetimeandperied = driverReminderBean.getDriver_timethreshold(); // 3
																				// days
		} else {
			duetimeandperied = timeandperied; // 21 days
		}

		// convert string to integer in date
		Integer due_enddate = Integer.parseInt(due_EndDate[0]);

		// convert string to integer in month and one more remove 0 is month why
		// means calendar format is [0-11] only so i am subtract to -1 method
		Integer due_endmonth = (Integer.parseInt(due_EndDate[1].replaceFirst("^0+(?!$)", "")) - 1);

		// convert string to integer in year
		Integer due_endyear = Integer.parseInt(due_EndDate[2]);

		// create new calendar at specific date. Convert to java.util calendar
		// type
		Calendar due_endcalender = new GregorianCalendar(due_endyear, due_endmonth, due_enddate);

		// print date for default value
	//	System.out.println("Past calendar : " + due_endcalender.getTime());

		// subtract 2 days from the calendar and the due_time and peried of
		// throsold integer
		due_endcalender.add(Calendar.DATE, -duetimeandperied);
		//System.out.println(duetimeandperied + "days ago: " + due_endcalender.getTime());

		// this format this the [0-11] but real time month format this [1-12]
		Integer month = due_endcalender.get(Calendar.MONTH) + 1;
		// i am change the format to story the database in reminder date
		String reminder_dateof = "" + due_endcalender.get(Calendar.DATE) + "-" + month + "-"
				+ due_endcalender.get(Calendar.YEAR);

		// save renewal date
		driverReminder.setDriver_renewaldate(reminder_dateof);

		// System.out.println("change reminder date: "+reminder_dateof);

		// System.out.println("*******************************************\n");

		return driverReminder;
	}

	// get logic in driver Information
	public DriverReminderHistory prepareDriverReminderHistroy(DriverReminderDto driverReminderBean) {

		DriverReminderHistory driverReminder = new DriverReminderHistory();

		driverReminder.setDriver_id(driverReminderBean.getDriver_id());

		driverReminder.setDriver_rhtypeId(driverReminderBean.getDriverRenewalTypeId());
		driverReminder.setDriver_rhnumber(driverReminderBean.getDriver_dlnumber());
		driverReminder.setDriver_rhfromDate(driverReminderBean.getDriver_dlfrom());
		driverReminder.setDriver_rhtoDate(driverReminderBean.getDriver_dlto());
		driverReminder.setDriver_timethreshold(driverReminderBean.getDriver_timethreshold());
		driverReminder.setDriver_periedthreshold(driverReminderBean.getDriver_periedthreshold());
		driverReminder.setDriver_renewaldate(driverReminderBean.getDriver_renewaldate());

		driverReminder.setDriver_content(driverReminderBean.getDriver_content());
		driverReminder.setDriver_contentType(driverReminderBean.getDriver_contentType());
		driverReminder.setDriver_filename(driverReminderBean.getDriver_filename());
		driverReminder.setCreatedById(driverReminderBean.getCreatedById());
		driverReminder.setCreated(driverReminderBean.getCreated());

		return driverReminder;
	}

	/*
	 * #########################################################################
	 * ################
	 */
	/* Show DriverRenewal Reminder */

	public List<DriverReminderDto> prepareListDriverReminderBean(List<DriverReminderDto> DriverReminder) {
		List<DriverReminderDto> beans = null;

		if (DriverReminder != null && !DriverReminder.isEmpty()) {
			beans = new ArrayList<DriverReminderDto>();
			DriverReminderDto driverReminder = null;

			for (DriverReminderDto driverBean : DriverReminder) {
				driverReminder = new DriverReminderDto();

				driverReminder.setDriver_remid(driverBean.getDriver_remid());
				driverReminder.setDriver_id(driverBean.getDriver_id());
				driverReminder.setDriver_firstname(driverBean.getDriver_firstname());
				driverReminder.setDriver_Lastname(driverBean.getDriver_Lastname());
				driverReminder.setDriver_empnumber(driverBean.getDriver_empnumber());

				driverReminder.setDriver_remindertype(driverBean.getDriver_remindertype());
				driverReminder.setDriver_dlnumber(driverBean.getDriver_dlnumber());
				if (driverBean.getDriver_dlfrom() != null) {
					driverReminder.setDriver_dlfrom_show(dateFormat_name.format(driverBean.getDriver_dlfrom()));
				}
				if (driverBean.getDriver_dlto() != null) {
					driverReminder.setDriver_dlto_show(dateFormat_name.format(driverBean.getDriver_dlto()));
				}
				driverReminder.setDriver_timethreshold(driverBean.getDriver_timethreshold());
				driverReminder.setDriver_periedthreshold(driverBean.getDriver_periedthreshold());

				driverReminder.setDriver_filename(driverBean.getDriver_filename());

				driverReminder.setDriver_renewaldate(driverBean.getDriver_renewaldate());
				driverReminder.setDriverFatherName(driverBean.getDriverFatherName());
			
				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (driverBean.getDriver_dlto() != null) {

					int diffInDays = (int) ((driverBean.getDriver_dlto().getTime() - toDate.getTime())
							/ (1000 * 60 * 60 * 24));

					// System.out.println(diffInDays);
					String diffenceDays = null;

					switch (diffInDays) {
					case 0:
						diffenceDays = ("Today");
						break;
					case -1:
						diffenceDays = ("YesterDay");
						break;
					case 1:
						diffenceDays = ("Tomorrow");
						break;
					default:
						if (diffInDays < -1) {
							long days = -diffInDays;
							if (days >= 365) {
								diffenceDays = (days / 365 + " years & " + (days % 365) + " months ago");
							} else if (days > 30) {
								diffenceDays = (days / 30 + " months & " + days % 30 + " days ago");
							} else
								diffenceDays = (-diffInDays + " days ago");
						} else if (diffInDays > 1) {
							if (diffInDays >= 365) {
								diffenceDays = (diffInDays / 365 + " years & " + ((diffInDays % 365) / 31)
										+ " months  from now");
							} else if (diffInDays > 30) {
								diffenceDays = (diffInDays / 30 + " months & " + diffInDays % 30 + " days from now");
							} else
								diffenceDays = (diffInDays + " days from now");
						}
						break;
					}

					// System.out.println( diffenceDays);
					driverReminder.setDriver_dueDifference(diffenceDays);
				}

				

				beans.add(driverReminder);
			}
		}
		return beans;
	}

	
	public List<DriverReminderDto> getDriverReminder_Showing_Details(List<DriverReminderDto> DriverReminder) {
		List<DriverReminderDto> beans = null;

		if (DriverReminder != null && !DriverReminder.isEmpty()) {
			beans = new ArrayList<DriverReminderDto>();
			DriverReminderDto driverReminder = null;

			for (DriverReminderDto driverBean : DriverReminder) {
				driverReminder = new DriverReminderDto();

				driverReminder.setDriver_remid(driverBean.getDriver_remid());
				driverReminder.setDriver_id(driverBean.getDriver_id());

				driverReminder.setDriver_remindertype(driverBean.getDriver_remindertype());
				driverReminder.setDriver_dlnumber(driverBean.getDriver_dlnumber());
				if (driverBean.getDriver_dlfrom() != null) {
					driverReminder.setDriver_dlfrom_show(dateFormat.format(driverBean.getDriver_dlfrom()));
				}
				if (driverBean.getDriver_dlto() != null) {
					driverReminder.setDriver_dlto_show(dateFormat.format(driverBean.getDriver_dlto()));
				}
				driverReminder.setDriver_timethreshold(driverBean.getDriver_timethreshold());
				driverReminder.setDriver_periedthreshold(driverBean.getDriver_periedthreshold());
				
				if(driverReminder.getDriver_periedthreshold() == 0) {
					driverReminder.setDriver_periedthresholdStr("Day(s)");
				} else if (driverReminder.getDriver_periedthreshold() == 7) {
					driverReminder.setDriver_periedthresholdStr("Week(s)");
				} else {
					driverReminder.setDriver_periedthresholdStr("Month(s)");
				}

				driverReminder.setDriver_filename(driverBean.getDriver_filename());

				driverReminder.setDriver_renewaldate(driverBean.getDriver_renewaldate());
				
				driverReminder.setDriver_content(driverBean.getDriver_content());
				driverReminder.setDriver_contentType(driverBean.getDriver_contentType());
				// get Current days
				java.util.Date currentDate = new java.util.Date();
				Timestamp toDate = new java.sql.Timestamp(currentDate.getTime());

				if (driverBean.getDriver_dlto() != null) {

					int diffInDays = (int) ((driverBean.getDriver_dlto().getTime() - toDate.getTime())
							/ (1000 * 60 * 60 * 24));

					// System.out.println(diffInDays);
					String diffenceDays = null;

					switch (diffInDays) {
					case 0:
						diffenceDays = ("Today");
						break;
					case -1:
						diffenceDays = ("YesterDay");
						break;
					case 1:
						diffenceDays = ("Tomorrow");
						break;
					default:
						if (diffInDays < -1) {
							long days = -diffInDays;
							if (days >= 365) {
								diffenceDays = (days / 365 + " years & " + (days % 365) + " months ago");
							} else if (days > 30) {
								diffenceDays = (days / 30 + " months & " + days % 30 + " days ago");
							} else
								diffenceDays = (-diffInDays + " days ago");
						} else if (diffInDays > 1) {
							if (diffInDays >= 365) {
								diffenceDays = (diffInDays / 365 + " years & " + ((diffInDays % 365) / 31)
										+ " months  from now");
							} else if (diffInDays > 30) {
								diffenceDays = (diffInDays / 30 + " months & " + diffInDays % 30 + " days from now");
							} else
								diffenceDays = (diffInDays + " days from now");
						}
						break;
					}

					// System.out.println( diffenceDays);
					driverReminder.setDriver_dueDifference(diffenceDays);
				}

				beans.add(driverReminder);
			}
		}
		return beans;
	}

	
	
	/*
	 * #########################################################################
	 * ################
	 */

	/*public DriverReminderDto GetDriverReminder(DriverReminder driverReminder2) {

		DriverReminderDto driverReminder = new DriverReminderDto();

		driverReminder.setDriver_id(driverReminder2.getDriver_id());

		driverReminder.setDriver_remid(driverReminder2.getDriver_remid());
		driverReminder.setDriver_remindertype(driverReminder2.getDriver_remindertype());
		driverReminder.setDriver_dlnumber(driverReminder2.getDriver_dlnumber());
		if (driverReminder2.getDriver_dlfrom() != null) {
			driverReminder.setDriver_dlfrom_show(dateFormat.format(driverReminder2.getDriver_dlfrom()));
		}
		if (driverReminder2.getDriver_dlto() != null) {
			driverReminder.setDriver_dlto_show(dateFormat.format(driverReminder2.getDriver_dlto()));
		}
		driverReminder.setDriver_timethreshold(driverReminder2.getDriver_timethreshold());
		driverReminder.setDriver_periedthreshold(driverReminder2.getDriver_periedthreshold());

		driverReminder.setDriver_renewaldate(driverReminder2.getDriver_renewaldate());

		driverReminder.setDriver_content(driverReminder2.getDriver_content());
		driverReminder.setDriver_contentType(driverReminder2.getDriver_contentType());
		driverReminder.setDriver_filename(driverReminder2.getDriver_filename());

		return driverReminder;
	}
*/
	public DriverReminderDto GetDriverReminder(DriverReminderDto driverReminder2) {

		DriverReminderDto driverReminder = new DriverReminderDto();

		driverReminder.setDriver_id(driverReminder2.getDriver_id());

		driverReminder.setDriver_remid(driverReminder2.getDriver_remid());
		driverReminder.setDriver_remindertype(driverReminder2.getDriver_remindertype());
		driverReminder.setDriver_dlnumber(driverReminder2.getDriver_dlnumber());
		if (driverReminder2.getDriver_dlfrom() != null) {
			driverReminder.setDriver_dlfrom_show(dateFormat.format(driverReminder2.getDriver_dlfrom()));
		}
		if (driverReminder2.getDriver_dlto() != null) {
			driverReminder.setDriver_dlto_show(dateFormat.format(driverReminder2.getDriver_dlto()));
		}
		driverReminder.setDriver_dlfrom(driverReminder2.getDriver_dlfrom());
		driverReminder.setDriver_dlto(driverReminder2.getDriver_dlto());
		driverReminder.setDriver_timethreshold(driverReminder2.getDriver_timethreshold());
		driverReminder.setDriver_periedthreshold(driverReminder2.getDriver_periedthreshold());

		driverReminder.setDriver_renewaldate(driverReminder2.getDriver_renewaldate());

		driverReminder.setDriver_content(driverReminder2.getDriver_content());
		driverReminder.setDriver_contentType(driverReminder2.getDriver_contentType());
		driverReminder.setDriver_filename(driverReminder2.getDriver_filename());
		driverReminder.setDriverRenewalTypeId(driverReminder2.getDriverRenewalTypeId());
		driverReminder.setCreatedById(driverReminder2.getCreatedById());
		driverReminder.setCreated(driverReminder2.getCreated());
		driverReminder.setNewDriverReminder(driverReminder2.isNewDriverReminder());
		driverReminder.setRenewalRecieptId(driverReminder2.getRenewalRecieptId());

		return driverReminder;
	}

	
	/* get the Driver Reminder History */
	/*public DriverReminderHistoryDto GetDriverReminderHistory(DriverReminderHistory driverReminder2) {

		DriverReminderHistoryDto driverReminder = new DriverReminderHistoryDto();

		driverReminder.setDriver_id(driverReminder2.getDriver_id());

		driverReminder.setDriver_rhid(driverReminder2.getDriver_rhid());
		driverReminder.setDriver_rhtype(driverReminder2.getDriver_rhtype());
		driverReminder.setDriver_rhnumber(driverReminder2.getDriver_rhnumber());
		driverReminder.setDriver_rhfrom(driverReminder2.getDriver_rhfrom());
		driverReminder.setDriver_rhto(driverReminder2.getDriver_rhto());
		driverReminder.setDriver_timethreshold(driverReminder2.getDriver_timethreshold());
		driverReminder.setDriver_periedthreshold(driverReminder2.getDriver_periedthreshold());

		driverReminder.setDriver_renewaldate(driverReminder2.getDriver_renewaldate());

		driverReminder.setDriver_content(driverReminder2.getDriver_content());
		driverReminder.setDriver_contentType(driverReminder2.getDriver_contentType());
		driverReminder.setDriver_filename(driverReminder2.getDriver_filename());

		return driverReminder;
	}*/

	/*public DriverReminder GetDriverReminderRenewal(DriverReminder driverReminder2) {

		DriverReminder driverReminder = new DriverReminder();

		driverReminder.setDriver_id(driverReminder2.getDriver_id());

		driverReminder.setDriver_remid(driverReminder2.getDriver_remid());
		driverReminder.setDriver_remindertype(driverReminder2.getDriver_remindertype());
		driverReminder.setDriver_dlnumber(driverReminder2.getDriver_dlnumber());
		driverReminder.setDriver_dlfrom(driverReminder2.getDriver_dlfrom());
		driverReminder.setDriver_dlto(driverReminder2.getDriver_dlto());
		driverReminder.setDriver_timethreshold(driverReminder2.getDriver_timethreshold());
		driverReminder.setDriver_periedthreshold(driverReminder2.getDriver_periedthreshold());

		return driverReminder;
	}
*/
	/*public DriverReminder deleteDriverReminder(DriverReminderDto driverReminderBean) {
		DriverReminder driverReminder = new DriverReminder();

		driverReminder.setDriver_id(driverReminderBean.getDriver_id());

		driverReminder.setDriver_remid(driverReminderBean.getDriver_remid());
		driverReminder.setDriver_remindertype(driverReminderBean.getDriver_remindertype());
		driverReminder.setDriver_dlnumber(driverReminderBean.getDriver_dlnumber());
		driverReminder.setDriver_dlfrom(dateFormat.format(driverReminderBean.getDriver_dlfrom()));
		driverReminder.setDriver_dlto(dateFormat.format(driverReminderBean.getDriver_dlto()));
		driverReminder.setDriver_timethreshold(driverReminderBean.getDriver_timethreshold());
		driverReminder.setDriver_periedthreshold(driverReminderBean.getDriver_periedthreshold());

		return driverReminder;
	}*/

	/* delete the Reminder History 
	public DriverReminderHistory deleteDriverReminderHis(DriverReminderHistoryDto driverReminderBean) {
		DriverReminderHistory driverReminder = new DriverReminderHistory();

		driverReminder.setDriver_id(driverReminderBean.getDriver_id());

		driverReminder.setDriver_rhid(driverReminderBean.getDriver_rhid());
		driverReminder.setDriver_rhtype(driverReminderBean.getDriver_rhtype());
		driverReminder.setDriver_rhnumber(driverReminderBean.getDriver_rhnumber());
		driverReminder.setDriver_rhfrom(driverReminderBean.getDriver_rhfrom());
		driverReminder.setDriver_rhto(driverReminderBean.getDriver_rhto());
		driverReminder.setDriver_timethreshold(driverReminderBean.getDriver_timethreshold());
		driverReminder.setDriver_periedthreshold(driverReminderBean.getDriver_periedthreshold());

		driverReminder.setDriver_renewaldate(driverReminderBean.getDriver_renewaldate());

		driverReminder.setDriver_content(driverReminderBean.getDriver_content());
		driverReminder.setDriver_contentType(driverReminderBean.getDriver_contentType());
		driverReminder.setDriver_filename(driverReminderBean.getDriver_filename());

		return driverReminder;
	}
*/
	/* Show the Driver Renewal History */
	/* Show DriverRenewal Reminder */

	public List<DriverReminderHistoryDto> prepareListDriverReminderHisBean(
			List<DriverReminderHistoryDto> driverReminderHistory) {
		List<DriverReminderHistoryDto> beans = null;

		if (driverReminderHistory != null && !driverReminderHistory.isEmpty()) {
			beans = new ArrayList<DriverReminderHistoryDto>();
			DriverReminderHistoryDto driverReminder = null;

			for (DriverReminderHistoryDto driverBean : driverReminderHistory) {
				driverReminder = new DriverReminderHistoryDto();

				driverReminder.setDriver_rhid(driverBean.getDriver_rhid());
				driverReminder.setDriver_id(driverBean.getDriver_id());

				driverReminder.setDriver_rhtypeId(driverBean.getDriver_rhtypeId());
				driverReminder.setDriver_rhnumber(driverBean.getDriver_rhnumber());
				driverReminder.setDriver_rhfrom(driverBean.getDriver_rhfrom());
				driverReminder.setDriver_rhto(driverBean.getDriver_rhto());
				driverReminder.setDriver_rhtype(driverBean.getDriver_rhtype());

				driverReminder.setDriver_filename(driverBean.getDriver_filename());

				beans.add(driverReminder);
			}
		}
		return beans;

	}
	
	public DriverReminder prepareDriverReminderDetails(ValueObject valueObject,Driver driver, CustomUserDetails userDetails, MultipartFile file) throws Exception {
		
		ValueObject					dateRange						= null;	
		String 						dateRangeFrom 					= "";
		String 						dateRangeTo 					= "";
		DriverReminder 				driverReminder 					= null;

		try {
			driverReminder = new DriverReminder();
			
			dateRange		= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("driverReminderdate"));
			dateRangeFrom 	= dateRange.getString(DateTimeUtility.FROM_DATE);
			dateRangeTo 	= dateRange.getString(DateTimeUtility.TO_DATE);

			driverReminder.setDriver_id(driver.getDriver_id());
			driverReminder.setDriver_remid(valueObject.getInt("driverReminderId",0));
			driverReminder.setDriverRenewalTypeId(valueObject.getLong("driverRenewalTypeId",0));
			driverReminder.setDriver_dlnumber(valueObject.getString("dlNumber"));
			driverReminder.setDriver_dlfrom(DateTimeUtility.getDateFromString(dateRangeFrom,DateTimeUtility.DD_MM_YYYY));
			driverReminder.setDriver_dlto(DateTimeUtility.getDateFromString(dateRangeTo,DateTimeUtility.DD_MM_YYYY));
			driverReminder.setDriver_timethreshold(valueObject.getInt("driverDueThreshold"));
			driverReminder.setDriver_periedthreshold(valueObject.getInt("driverDueThresholdPeriod"));
			
			if (!file.isEmpty()) {
				byte[] bytes = file.getBytes();
				driverReminder.setDriver_filename(file.getOriginalFilename());
				driverReminder.setDriver_content(bytes);
				driverReminder.setDriver_contentType(file.getContentType());
			}
			
			driverReminder.setDriver_renewaldate(dateRangeTo);
			driverReminder.setCompanyId(userDetails.getCompany_id());
			driverReminder.setCreatedById(userDetails.getId());
			driverReminder.setCreated(DateTimeUtility.getCurrentDate());

			return driverReminder;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
	}
	
	public DriverReminder saveDriverReminderDetailsFromMobile(ValueObject object) throws Exception {
		
		DriverReminder      driverReminder = new DriverReminder();
		CustomUserDetails 	userDetails    = (CustomUserDetails) object.get("userDetails");
		
		driverReminder.setDriver_id(object.getInt("driverId"));
		driverReminder.setDriverRenewalTypeId(object.getLong("driverRenewalType"));
		driverReminder.setDriver_dlnumber(object.getString("dlNumber"));
		
		java.util.Date date = dateFormat.parse(object.getString("validityFrom"));
		java.sql.Date fromDate = new Date(date.getTime());
		driverReminder.setDriver_dlfrom(fromDate);
		driverReminder.setDriver_dlto(sqlFormatTime.parse(object.getString("validityTo") + DateTimeUtility.DAY_END_TIME));
		
		driverReminder.setDriver_timethreshold(object.getInt("timeThreshold",1));
		driverReminder.setDriver_periedthreshold(object.getInt("renewalPeriodThreshold",0));
		
		Integer duetimeandperied;
		String  duedate       = object.getString("validityTo");
		Integer timeandperied = (driverReminder.getDriver_timethreshold()	* driverReminder.getDriver_periedthreshold());

		// timeandperied=3*0 days; or timeandperied=3*7 weeks or timeandperied=3*28 month

		if (timeandperied == 0) {
			duetimeandperied = driverReminder.getDriver_timethreshold(); 
		} else {
			duetimeandperied = timeandperied; 
		}

		String reminder_dateof = Change_CurrentDate_To_RenewalDate_SubTrackDate(duedate, duetimeandperied);
		driverReminder.setDriver_renewaldate(reminder_dateof);
		
		
		driverReminder.setCreatedById(userDetails.getId());
		driverReminder.setLastModifiedById(userDetails.getId());
		driverReminder.setCompanyId(userDetails.getCompany_id());
		driverReminder.setMarkForDelete(false);

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		driverReminder.setCreated(toDate);
		driverReminder.setLastModified(toDate);
		
		return driverReminder;
	}
	
	public String Change_CurrentDate_To_RenewalDate_SubTrackDate(String duedate, Integer duetimeandperied) {

		// get the date from database is 10-12-2015
		// that date split the date & month & year date[0]=10,
		// date[1]=12,date[2]=2015
		String due_EndDate[] = duedate.split("-");

		// convert string to integer in date
		Integer due_enddate = Integer.parseInt(due_EndDate[0]);

		// convert string to integer in month and one more remove 0 is month why
		// means calendar format is [0-11] only so i am subtract to -1 method
		Integer due_endmonth = (Integer.parseInt(due_EndDate[1].replaceFirst("^0+(?!$)", "")) - 1);

		// convert string to integer in year
		Integer due_endyear = Integer.parseInt(due_EndDate[2]);

		// create new calendar at specific date. Convert to java.util calendar
		// type
		Calendar due_endcalender = new GregorianCalendar(due_endyear, due_endmonth, due_enddate);

		// print date for default value
		// System.out.println("Past calendar : " + due_endcalender.getTime());

		// subtract 2 days from the calendar and the due_time and peried of
		// throsold integer
		due_endcalender.add(Calendar.DATE, -duetimeandperied);
		// System.out.println(duetimeandperied+"days ago: " +
		// due_endcalender.getTime());

		// this format this the [0-11] but real time month format this [1-12]
		Integer month = due_endcalender.get(Calendar.MONTH) + 1;
		// i am change the format to story the database in reminder date
		String reminder_dateof = "" + due_endcalender.get(Calendar.DATE) + "-" + month + "-"
				+ due_endcalender.get(Calendar.YEAR);

		return reminder_dateof;
	}

}
