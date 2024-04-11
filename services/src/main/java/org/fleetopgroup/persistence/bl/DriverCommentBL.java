package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.DriverCommentDto;
import org.fleetopgroup.persistence.model.DriverComment;

public class DriverCommentBL {

	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	public DriverCommentBL() {
	}

	// get logic in driver Information
	public DriverComment prepareDriverComment(DriverCommentDto driverCommentBean) {
		DriverComment driverComment = new DriverComment();

		driverComment.setDriver_commentid(driverCommentBean.getDriver_commentid());
		driverComment.setDriver_id(driverCommentBean.getDriver_id());

		driverComment.setDriver_title(driverCommentBean.getDriver_title());
		driverComment.setDriver_comment(driverCommentBean.getDriver_comment());
		
		driverComment.setCreatedBy(driverCommentBean.getCreatedBy());
		//driverComment.setCreatedBy(driverCommentBean.getCreatedBy());
		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		driverComment.setCreated(toDate);

		driverComment.setMarkForDelete(false);

		return driverComment;
	}

	/**
	 * @param collection
	 * @return
	 */
	public List<DriverCommentDto> prepare_RemarksDriverComment_Report(List<DriverCommentDto> collection) {
		// Report Comment Report

		List<DriverCommentDto> Dtos = null;
		if (collection != null && !collection.isEmpty()) {
			Dtos = new ArrayList<DriverCommentDto>();
			DriverCommentDto list = null;
			for (DriverCommentDto result : collection) {
				list = new DriverCommentDto();

				list.setDriver_id(result.getDriver_id());
				list.setDriver_firstname(result.getDriver_firstname());
				list.setDriver_Lastname(result.getDriver_Lastname());
				list.setDriver_empnumber(result.getDriver_empnumber());
				list.setDriver_commentid(result.getDriver_commentid());
				list.setDriver_title(result.getDriver_title());
				list.setDriver_comment(result.getDriver_comment());
				list.setCreatedBy(result.getCreatedBy());
				list.setCreated(result.getCreated());
				if (result.getCreated() != null) {
					list.setDriver_uploaddate(CreatedDateTime.format(result.getCreated()));
				}
				Dtos.add(list);
			}
		}
		return Dtos;
	}

	/*
	 * #########################################################################
	 * ################
	 */
	/* Show DriverRenewal Reminder */

	/*
	 * public List<DriverReminderDto>
	 * prepareListDriverReminderBean(List<DriverReminder> DriverReminder) {
	 * List<DriverReminderDto> beans = null;
	 * 
	 * if (DriverReminder != null && !DriverReminder.isEmpty()) { beans = new
	 * ArrayList<DriverReminderDto>(); DriverReminderDto driverReminder = null;
	 * 
	 * for (DriverReminder driverBean : DriverReminder) { driverReminder = new
	 * DriverReminderDto();
	 * 
	 * driverReminder.setDriver_remid(driverBean.getDriver_remid());
	 * driverReminder.setDriver_id(driverBean.getDriver_id());
	 * 
	 * driverReminder.setDriver_remindertype(driverBean.getDriver_remindertype()
	 * ); driverReminder.setDriver_dlnumber(driverBean.getDriver_dlnumber());
	 * driverReminder.setDriver_dlfrom(driverBean.getDriver_dlfrom()); //
	 * driverReminder.setDriver_dlto(driverBean.getDriver_dlto());
	 * driverReminder.setDriver_timethreshold(driverBean.getDriver_timethreshold
	 * ()); driverReminder.setDriver_periedthreshold(driverBean.
	 * getDriver_periedthreshold());
	 * 
	 * // get duedate from database is 10/12/2015 String duedate =
	 * driverBean.getDriver_dlto();
	 * 
	 * // that date split the date & month & year date[0]=10, //
	 * date[1]=12,date[2]=2015 String due_EndDate[] = duedate.split("/");
	 * 
	 * // convert string to integer in date Integer due_enddate =
	 * Integer.parseInt(due_EndDate[0]);
	 * 
	 * // convert string to integer in month and one more remove 0 is // month
	 * why means calendar format is [0-11] only so i am // subtract to -1 method
	 * Integer due_endmonth =
	 * (Integer.parseInt(due_EndDate[1].replaceFirst("^0+(?!$)", "")) - 1);
	 * 
	 * // convert string to integer in year Integer due_endyear =
	 * Integer.parseInt(due_EndDate[2]);
	 * 
	 * // create new calendar at specific date. Convert to java.util // calendar
	 * type Calendar due_endcalender = new GregorianCalendar(due_endyear,
	 * due_endmonth, due_enddate);
	 * 
	 * // print date for default value
	 * 
	 * System.out.println("Past calendar : " + due_endcalender.getTime());
	 * 
	 * 
	 * // show due_enddate of calendar
	 * driverReminder.setDriver_dueEndDate(due_endcalender.getTime().toString().
	 * replace("00:00:00 IST", " "));
	 * 
	 * // get Renewal date from database is 10/12/2015 String rendate =
	 * driverBean.getDriver_renewaldate();
	 * 
	 * // that date split the date & month & year date[0]=10, //
	 * date[1]=12,date[2]=2015 String ren_EndDate[] = rendate.split("/");
	 * 
	 * // convert string to integer in date Integer ren_enddate =
	 * Integer.parseInt(ren_EndDate[0]);
	 * 
	 * // convert string to integer in month and one more remove 0 is // month
	 * why means calendar format is [0-11] only so i am // subtract to -1 method
	 * Integer ren_endmonth =
	 * (Integer.parseInt(ren_EndDate[1].replaceFirst("^0+(?!$)", "")) - 1);
	 * 
	 * // convert string to integer in year Integer ren_endyear =
	 * Integer.parseInt(ren_EndDate[2]);
	 * 
	 * // create new calendar at specific date. Convert to java.util // calendar
	 * type Calendar due_renewalcalender = new GregorianCalendar(ren_endyear,
	 * ren_endmonth, ren_enddate);
	 * 
	 * // show the renewal calendar
	 * 
	 * driverReminder
	 * .setDriver_dueRemDate(due_renewalcalender.getTime().toString().
	 * replace("00:00:00 IST", " "));
	 * 
	 * // Two days difference between of days
	 * 
	 * long diffInMillisec = 0; long diffInDays = 0; Calendar firstDate = null;
	 * Calendar secondDate = null;
	 * 
	 * // Create two calendars instances firstDate = Calendar.getInstance();
	 * secondDate = Calendar.getInstance();
	 * 
	 * // get current date format Calendar cal = Calendar.getInstance();
	 * 
	 * // Set the dates firstDate.set(ren_endyear, ren_endmonth, ren_enddate);
	 * secondDate.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH),
	 * cal.get(Calendar.DATE));
	 * 
	 * // Get the difference between two dates in milliseconds diffInMillisec =
	 * firstDate.getTimeInMillis() - secondDate.getTimeInMillis();
	 * 
	 * // Get difference between two dates in days diffInDays = diffInMillisec /
	 * (24 * 60 * 60 * 1000);
	 * 
	 * String diffenceDays = null;
	 * 
	 * if (diffInDays == 0) { diffenceDays = ("Today"); } else if (diffInDays ==
	 * -1) { diffenceDays = ("YesterDay"); } else if (diffInDays == 1) {
	 * diffenceDays = ("Tomorrow"); } else if (diffInDays < -1) { diffenceDays =
	 * (-diffInDays + " days ago"); } else if (diffInDays > 1) { diffenceDays =
	 * (diffInDays + " days from now"); }
	 * 
	 * driverReminder.setDriver_dueDifference(diffenceDays);
	 * 
	 * beans.add(driverReminder); } } return beans; }
	 */

}
