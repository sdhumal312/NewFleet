package org.fleetopgroup.web.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


public class DateTimeUtility {
	
	public static final String	TRACE_ID	= DateTimeUtility.class.getName();

	public static final String	DD_MM_YYYY				= "dd-MM-yyyy";

	public static final String	DDMMYYYY				= "dd/MM/yyyy";
	
	public static final String	YYYY_MM_DD				= "yyyy-MM-dd";

	public static final String	HH_MM_AA				= "hh:mm aa";
	
	public static final String	HH_MM					= "hh:mm";

	public static final String	HHH_MM					= "HH:mm";
	
	public static final String	KK_MM					= "kk:mm";

	public static final String	H_MM_AA					= "h:mm aa";

	public static final String DD_MM_YY					= "dd-MM-yy";

	public static final String DD_MM_YY_HH_MM_AA		= "dd-MM-yy hh:mm aa";

	public static final String DD_MM_YYYY_HH_MM_AA		= "dd-MM-yyyy hh:mm aa";
	
	public static final String MM_DD_YYYY_HH_MM_AA_SLASH	= "MM/dd/yyyy hh:mm aa";

	public static final String DD_MM_YYYY_HH_MM_AA_WITHOUT_CHARACTERS		= "dd MM yyyy hh mm aa";

	public static final String DD_MM_YYYY_HH_MM_SS_AA	= "dd-MM-yyyy hh:mm:ss aa";

	public static final String DD_MM_YY_HH_MM_SS		= "dd-MM-yyyy HH:mm:ss";
	
	public static final String HH_MM_SS					= "HH:mm:ss";
	
	public static final String DD_MM_YYYY_HH_MM_SS		= "dd-MM-yyyy HH:mm:ss";
	
	public static final String YYYY_MM_DD_HH_MM_SS_AA		= "yyyy-MM-dd HH:mm:ss aa";
	
	public static final String YYYY_MM_DD_HH_MM_SS		= "yyyy-MM-dd HH:mm:ss";
	
	public static final String YYYYMMDD		= "yyyyMMdd";
	
	public static final String YYYYMMDDHHMMSS		= "yyyyMMddHHmmss";

	public static final String YYYY						= "yyyy";

	public static final String MM						= "MM";

	public static final String MMM						= "MMM";

	public static final String START_DATE_OF_MONTH		= "startDateOfMonth";
	
	public static final String END_DATE_OF_MONTH		= "endDateOfMonth";
	
	public static final String START_OF_MONTH			= "startOfMonth";

	public static final String END_OF_MONTH				= "endOfMonth";

	public static final String START_OF_WEEK			= "startOfWeek";

	public static final String END_OF_WEEK				= "endOfWeek";

	public static final String FROM_DATE				= "FromDate";
	
	public static final String TO_DATE					= "ToDate";
	
	public static final String DAY_START_TIME 			= " 00:00:00";
	
	public static final String DAY_END_TIME 			= " 23:59:59";
	
	public static final int	  FISCAL_START_MONTH		= 4;
	
	public static final int	  FISCAL_END_MONTH			= 3;
	
	public static final String DAY_START_TIME1 			= " 00:00:00.0";
	
	public static String[] fieldsName = { "YEAR", "MONTH", "HOUR", "MINUTE", "SECOND", "DAY_OF_WEEK", "DAY_OF_MONTH", "DAY_OF_YEAR",
             "WEEK_OF_MONTH", "DAY_OF_WEEK_IN_MONTH", "WEEK_OF_YEAR", "TIMEZONE" };
	
	 private static final Map<String, String> DATE_FORMAT_REGEXPS = new HashMap<String, String>() {/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

	{
	        put("^\\d{8}$", "yyyyMMdd");
	        put("^\\d{1,2}-\\d{1,2}-\\d{4}$", "dd-MM-yyyy");
	        put("^\\d{4}-\\d{1,2}-\\d{1,2}$", "yyyy-MM-dd");
	        put("^\\d{1,2}/\\d{1,2}/\\d{4}$", "MM/dd/yyyy");
	        put("^\\d{4}/\\d{1,2}/\\d{1,2}$", "yyyy/MM/dd");
	        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}$", "dd MMM yyyy");
	        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}$", "dd MMMM yyyy");
	        put("^\\d{12}$", "yyyyMMddHHmm");
	        put("^\\d{8}\\s\\d{4}$", "yyyyMMdd HHmm");
	        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}$", "dd-MM-yyyy HH:mm");
	        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy-MM-dd HH:mm");
	        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}$", "MM/dd/yyyy HH:mm");
	        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}$", "yyyy/MM/dd HH:mm");
	        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMM yyyy HH:mm");
	        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}$", "dd MMMM yyyy HH:mm");
	        put("^\\d{14}$", "yyyyMMddHHmmss");
	        put("^\\d{8}\\s\\d{6}$", "yyyyMMdd HHmmss");
	        put("^\\d{1,2}-\\d{1,2}-\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd-MM-yyyy HH:mm:ss");
	        put("^\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");
	        put("^\\d{1,2}/\\d{1,2}/\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "MM/dd/yyyy HH:mm:ss");
	        put("^\\d{4}/\\d{1,2}/\\d{1,2}\\s\\d{1,2}:\\d{2}:\\d{2}$", "yyyy/MM/dd HH:mm:ss");
	        put("^\\d{1,2}\\s[a-z]{3}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMM yyyy HH:mm:ss");
	        put("^\\d{1,2}\\s[a-z]{4,}\\s\\d{4}\\s\\d{1,2}:\\d{2}:\\d{2}$", "dd MMMM yyyy HH:mm:ss");
	    }};

	/**
	 * Current date of system
	 *
	 * @return - system current date
	 */
	public static Date getCurrentDate() {
		return Date.valueOf(LocalDate.now());
	}
	
	/**
	 * Current date of system
	 *
	 * @return - system current date
	 */
	public static Date getCurrentDateTime() {
		return new Date(System.currentTimeMillis());
	}
	

	/**
	 * Current timestamp of system
	 *
	 * @return - system current timestamp
	 */
	public static Timestamp getCurrentTimeStamp() {
		return Timestamp.from(Instant.now());
	}

	/**
	 * TimeStamp form string data
	 *
	 * @param date - date in string format
	 * @return - return timestamp of given date
	 * @throws Exception
	 */
	public static Timestamp getTimeStamp(String date) throws Exception {

		SimpleDateFormat	dateFormat		= null;
		java.util.Date		parsedTimeStamp	= null;
		Timestamp			timestamp		= null;

		try {
			dateFormat		=	new SimpleDateFormat(DD_MM_YYYY);
			parsedTimeStamp	=	dateFormat.parse(date);
			timestamp		=	new Timestamp(parsedTimeStamp.getTime());
			return timestamp;
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat		= null;
			parsedTimeStamp	= null;
			timestamp		= null;
		}
	}
	
	public static Timestamp getTimeStamp(String date, String format) throws Exception {

		SimpleDateFormat	dateFormat		= null;
		java.util.Date		parsedTimeStamp	= null;
		Timestamp			timestamp		= null;

		try {
			dateFormat		=	new SimpleDateFormat(format);
			parsedTimeStamp	=	dateFormat.parse(date);
			timestamp		=	new Timestamp(parsedTimeStamp.getTime());
			return timestamp;
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat		= null;
			parsedTimeStamp	= null;
			timestamp		= null;
		}
	}
	
	public static java.util.Date getDateFromString(String date, String format) throws Exception {

		SimpleDateFormat	dateFormat		= null;

		try {
			dateFormat		=	new SimpleDateFormat(format);
			return dateFormat.parse(date);
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat		= null;
		}
	}
	
	public static String getStrFromString(String date, String format) throws Exception {

		SimpleDateFormat	dateFormat		= null;

		try {
			dateFormat		=	new SimpleDateFormat(format);
			java.util.Date dateFormated = dateFormat.parse(date);
			
			return dateFormat.format(dateFormated);
			
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat		= null;
		}
	}
	
	
	
	public static Timestamp getTimeStampFromStringWithAMPM(String date) throws Exception {

		SimpleDateFormat	dateFormat		= null;
		java.util.Date		parsedTimeStamp	= null;
		Timestamp			timestamp		= null;

		try {
			dateFormat		=	new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS_AA);
			parsedTimeStamp	=	dateFormat.parse(date);
			timestamp		=	new Timestamp(parsedTimeStamp.getTime());
			return timestamp;
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat		= null;
			parsedTimeStamp	= null;
			timestamp		= null;
		}
	}

	public static Timestamp getTimeStampFromString(String date) throws Exception {

		SimpleDateFormat	dateFormat		= null;
		java.util.Date		parsedTimeStamp	= null;
		Timestamp			timestamp		= null;

		try {
			dateFormat		=	new SimpleDateFormat(DDMMYYYY);
			parsedTimeStamp	=	dateFormat.parse(date);
			timestamp		=	new Timestamp(parsedTimeStamp.getTime());
			return timestamp;
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat		= null;
			parsedTimeStamp	= null;
			timestamp		= null;
		}
	}

	/**
	 * Create custom date form year, month, day
	 *
	 * @param year - to be set
	 * @param month - to be set
	 * @param day - to be set
	 * @return - generated custom date
	 */
	public static Date getCustomDate(int year, int month, int day) {
		return Date.valueOf(LocalDate.of(year, month, day));
	}

	/**
	 * Timestamp to date in DD-MM-YYYY format
	 *
	 * @param timeStamp - to format into date
	 * @return - formated date of given timestamp
	 */
	public static String getDateFromTimeStamp(Timestamp timeStamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY);
		return dateFormat.format(timeStamp);
	}
	
	public static String getDateFromTimeStampWithAMPM(Timestamp timeStamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DD_MM_YYYY_HH_MM_SS_AA);
		return dateFormat.format(timeStamp);
	}

	/**
	 * Timestamp to time in HH-MM-AA format
	 *
	 * @param timeStamp - to format into date
	 * @return - formated time of given timestamp
	 */
	public static String getTimeFromTimeStamp(Timestamp timeStamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(H_MM_AA);
		return dateFormat.format(timeStamp);
	}
	public static String getTimeFromTimeStampIn24HourFormat(Timestamp timeStamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm:ss");
		return dateFormat.format(timeStamp);
	}
	public static String getTimeFromTimeStamp(Timestamp timeStamp, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String date = dateFormat.format(timeStamp);
		if(date.contains("24:")) {
			date = date.replace("24:", "00:");
		}
		return date;
	}
	

	/**
	 * Timestamp to date in DD-MM-YYYY HH:MM AA format
	 *
	 * @param timeStamp - to format into date
	 * @return - formated date of given timestamp
	 */
	public static String getDateTimeFromTimeStamp(Timestamp timeStamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DD_MM_YY_HH_MM_AA);
		return dateFormat.format(timeStamp);
	}

	/**
	 * Timestamp to date in given format
	 *
	 * @param timeStamp - to format into date
	 * @param dateFormat - custom date format
	 * @return - formated date of given timestamp
	 */
	public static String getDateFromTimeStamp(Timestamp timeStamp, String dateFormat) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
		return simpleDateFormat.format(timeStamp);
	}

	public static Timestamp getStartOfDayTimeStamp(String date) throws Exception {

		try {
			return getCalendarTimeFromTimestamp(getTimeStamp(date), 0, 0, 0, 0);
		} catch (Exception e) {
			throw e;
		} 
	}

	public static Timestamp getEndOfDayTimeStamp(String date) throws Exception {
		try {
			return getCalendarTimeFromTimestamp(getTimeStamp(date), 23, 59, 59, 998);
		} catch (Exception e) {
			throw e;
		} 
	}

	public static Timestamp getEndOfDayTimeStamp(String date, String format) throws Exception {
		try {
			return getCalendarTimeFromTimestamp(getTimeStamp(date, format), 23, 59, 59, 998);
		} catch (Exception e) {
			throw e;
		} 
	}
	
	public static java.util.Date getEndOfDayDate(String date, String format) throws Exception {
		try {
			return getCalendarTimeFromDate(getDateFromString(date, format), 23, 59, 59);
		} catch (Exception e) {
			throw e;
		} 
	}
	
	public static String getEndOfDayDateStr(String date, String format) throws Exception {
		try {
			SimpleDateFormat	sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			java.util.Date  endDate = getCalendarTimeFromDate(getDateFromString(date, format), 23, 59, 59);
			return sdf.format(endDate);
		} catch (Exception e) {
			throw e;
		} 
	}
	
	public static Timestamp appendTimeToDate(String date) throws Exception {
		Calendar 			currentDateTime 	= null;
		SimpleDateFormat	sdf					= null;
		try {
			currentDateTime 	= Calendar.getInstance();
			sdf    				= new SimpleDateFormat(DD_MM_YY_HH_MM_SS);
			return new Timestamp((sdf.parse(date + " " + currentDateTime.get(Calendar.HOUR_OF_DAY) + ":" + currentDateTime.get(Calendar.MINUTE) + ":" + currentDateTime.get(Calendar.SECOND))).getTime());
		} catch (Exception e) {
			throw e;
		}
	}

	public static Timestamp getCalendarTimeFromTimestamp(Timestamp timestamp,int hour,int minute,int second,int millisecond) throws Exception {
		Calendar 	calendar 	= null ;
		Timestamp 	timestamp2 	= null;

		try {
			calendar = Calendar.getInstance();
			calendar.setTime(timestamp);

			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, second);
			calendar.set(Calendar.MILLISECOND, millisecond);	

			timestamp2 = new Timestamp(calendar.getTimeInMillis());

			return timestamp2;	

		} catch (Exception e) {
			throw e;
		} finally {
			calendar 	= null ;
			timestamp2 	= null;
		}	
	}
	
	public static java.util.Date getCalendarTimeFromDate(java.util.Date timestamp,int hour,int minute,int second) throws Exception {
		Calendar 	calendar 	= null ;

		try {
			calendar = Calendar.getInstance();
			calendar.setTime(timestamp);

			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, minute);
			calendar.set(Calendar.SECOND, second);


			return calendar.getTime();	

		} catch (Exception e) {
			throw e;
		} finally {
			calendar 	= null ;
		}	
	}

	public static Timestamp getFirstDayTimestampOfMonth() throws Exception {
		Calendar 	calendar 	= null ;
		Timestamp 	timestamp2 	= null;

		try {
			calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_MONTH,1);

			timestamp2 = new Timestamp(calendar.getTimeInMillis());

			return timestamp2;	

		} catch (Exception e) {
			throw e;
		} finally {
			calendar 	= null ;
			timestamp2 	= null;
		}	
	}

	public static boolean checkDateExistsBetweenDates(Timestamp fromDate, Timestamp toDate, Timestamp transDate) throws Exception {
		boolean			isDuplicate	 = false;

		try {

			if((transDate.after(fromDate) && transDate.before(toDate))){
				isDuplicate = true;
			}
			return isDuplicate;

		} catch (Exception e) {
			throw e;
		} finally {
		}
	}


	public static boolean isEqualFiscalYear(LocalDateTime startDate, LocalDateTime endDate) {
		try {
			if (getFiscalYear(startDate) == getFiscalYear(endDate)) return true;
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return false;
	}

	public static boolean isEqualMonth(LocalDateTime startDate, LocalDateTime endDate) {
		try {
			if (getFiscalYear(startDate) == getFiscalYear(endDate) && startDate.getMonth().compareTo(endDate.getMonth()) == 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();;
		}
		return false;
	}

	public static long getFiscalYear(LocalDateTime date) {

		long	fiscalYear	= 0;

		try {

			if (date.getMonth().compareTo(Month.APRIL) < 0) {
				fiscalYear	= Long.parseLong((date.getYear() - 1) + "" + date.getYear());
			} else {
				fiscalYear	= Long.parseLong(date.getYear() + "" + (date.getYear() + 1));
			}

		} catch (Exception e) {
			e.printStackTrace();;
		}

		return fiscalYear;
	}

	public static long getDayDifference(LocalDateTime startDate, LocalDateTime endDate) {
		return ChronoUnit.DAYS.between(startDate, endDate.plusDays(1));
	}

	public static long getMonthDifference(LocalDateTime startDate, LocalDateTime endDate) {
		return ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), endDate.withDayOfMonth(endDate.toLocalDate().lengthOfMonth()).plusDays(1));
	}
	
	public static long getMonthDifference(java.util.Date startDate, java.util.Date endDate) {
		
		LocalDateTime start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime end	= endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		return ChronoUnit.MONTHS.between(start.withDayOfMonth(1), end.withDayOfMonth(end.toLocalDate().lengthOfMonth()).plusDays(1));
	}
	
public static int getMonthDifference2(java.util.Date startDate, java.util.Date endDate) {
		
	Calendar startCalendar = new GregorianCalendar();
	startCalendar.setTime(startDate);
	Calendar endCalendar = new GregorianCalendar();
	endCalendar.setTime(endDate);

	int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
	int diffMonth = diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);
	return diffMonth;
	}

	public static long getFiscalYearDifference(LocalDateTime startDate, LocalDateTime endDate) throws Exception {

		LocalDateTime		yearStartDate	= null;
		LocalDateTime		yearEndDate		= null;

		Period			period			= null;

		long			yearDiff		= 0;

		try {

			if (startDate.getMonth().compareTo(Month.APRIL) < 0) {
				yearStartDate	= LocalDateTime.of(startDate.getYear() - 1, Month.APRIL, 1, 0, 0, 0, 0);
			} else {
				yearStartDate	= LocalDateTime.of(startDate.getYear(), Month.APRIL, 1, 0, 0, 0, 0);
			}

			if (endDate.getMonth().compareTo(Month.APRIL) < 0) {
				yearEndDate	= LocalDateTime.of(endDate.getYear(), Month.MARCH, 31, 0, 0, 0, 0);
			} else {
				yearEndDate	= LocalDateTime.of(endDate.getYear() + 1, Month.MARCH, 31, 0, 0, 0, 0);
			}

			period		= Period.between(yearStartDate.toLocalDate(), yearEndDate.toLocalDate().plusDays(1)); // Added 1 Day because it does not count last date

			yearDiff	= period.getYears();

			return yearDiff;
		} catch (Exception e) {
			throw e;
		} finally {
			yearStartDate	= null;
			yearEndDate		= null;
			period			= null;
		}
	}

	public static Timestamp getStartDateofFiscalYear(LocalDate date) throws Exception {
		Date			utilDate	= null;
		Calendar 		cal			= null;
		
		try {
			if (date.getMonth().compareTo(Month.APRIL) < 0) {
				utilDate = getCustomDate(date.getYear()- 1, Month.APRIL.getValue(), 1);
			} else {
				utilDate = getCustomDate(date.getYear(), Month.APRIL.getValue(), 1);
			}

			cal = Calendar.getInstance();
			cal.setTime(utilDate);
			cal.set(Calendar.MILLISECOND, 0);

			return new java.sql.Timestamp(utilDate.getTime());

		} catch (Exception e) {
			throw e;
		} finally {
			utilDate		= null;
			cal				= null;
		}
	}

	public static long getMonthTableId(LocalDateTime date) {
		return Long.parseLong(date.getYear() + "" + date.getMonthValue());
	}

	public static List<Long> getMonthTableIdList(LocalDateTime startDate, LocalDateTime endDate) {
		List<Long>		monthList		= null;
		long			monthDiff		= 0;

		try {

			monthList	= new ArrayList<Long>();

			monthDiff	= getMonthDifference(startDate, endDate);

			for (int i = 0; i < monthDiff; i++) {
				monthList.add(getMonthTableId(startDate.plusMonths(i)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return monthList;
	}

	public static long getYearTableId(LocalDateTime date) {
		return getFiscalYear(date);
	}

	public static List<Long> getFiscalYearTableIdList(LocalDateTime startDate, LocalDateTime endDate) {
		List<Long>		yearList		= null;
		long			yearDiff		= 0;

		try {

			yearList	= new ArrayList<Long>();

			yearDiff	= getFiscalYearDifference(startDate, endDate);

			for (int i = 0; i < yearDiff; i++) {
				yearList.add(getYearTableId(startDate.plusYears(i)));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return yearList;
	}

	public static boolean isFiscalYearFirstDate(LocalDateTime startDate) {
		LocalDateTime		yearStartDate	= null;

		try {

			if (startDate.getMonth().compareTo(Month.APRIL) < 0) {
				yearStartDate	= LocalDateTime.of(startDate.getYear() - 1, Month.APRIL, 1, 0, 0, 0, 0);
			} else {
				yearStartDate	= LocalDateTime.of(startDate.getYear(), Month.APRIL, 1, 0, 0, 0, 0);
			}

			if (startDate.equals(yearStartDate)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			yearStartDate	= null;
		}

		return false;
	}

	public static boolean isFiscalYearLastDate(LocalDateTime endDate) {
		LocalDateTime		yearEndDate	= null;

		try {

			if (endDate.getMonth().compareTo(Month.APRIL) < 0) {
				yearEndDate	= LocalDateTime.of(endDate.getYear(), Month.MARCH, 31, 0, 0, 0, 0);
			} else {
				yearEndDate	= LocalDateTime.of(endDate.getYear() + 1, Month.MARCH, 31, 0, 0, 0, 0);
			}

			if (endDate.equals(yearEndDate)) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			yearEndDate	= null;
		}

		return false;
	}

	public static String getDateBeforeNoOfDays(int noOfDays) {
		String previousDate		= null;

		try {
			Calendar now = Calendar.getInstance();

			now.add(Calendar.DATE, -noOfDays);

			previousDate	= now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR);

			return previousDate;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			previousDate		= null;
		}
	}
	public static String getDateAfterNoOfDays(int noOfDays) {
		String previousDate		= null;
		
		try {
			Calendar now = Calendar.getInstance();
			
			now.add(Calendar.DATE, noOfDays);
			
			previousDate	= now.get(Calendar.DATE) + "-" + (now.get(Calendar.MONTH) + 1) + "-" + now.get(Calendar.YEAR);
			
			return previousDate;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			previousDate		= null;
		}
	}
	
	public static long getExactDayDiffBetweenTwoDates (Timestamp fromDate, Timestamp tillDate) throws Exception {

		Calendar calTillDate	= null;
		Calendar calFromDate	= null;
		long 	 dayDiff		= 0;	

		try {
			calFromDate = Calendar.getInstance();
			calTillDate = Calendar.getInstance();

			calFromDate.setTime(fromDate);
			calFromDate.set(Calendar.HOUR_OF_DAY, 0);
			calFromDate.set(Calendar.MINUTE, 0);
			calFromDate.set(Calendar.SECOND, 0);
			calFromDate.set(Calendar.MILLISECOND, 0);

			calTillDate.setTime(tillDate);
			calTillDate.set(Calendar.HOUR_OF_DAY, 0);
			calTillDate.set(Calendar.MINUTE, 0);
			calTillDate.set(Calendar.SECOND, 0);
			calTillDate.set(Calendar.MILLISECOND, 0);

			dayDiff = ((calTillDate.getTimeInMillis() - calFromDate.getTimeInMillis())/(1000*60*60*24));

			return	dayDiff;
		} catch (Exception e) {
			throw e;
		} finally{
			calTillDate	= null;
			calFromDate	= null;
		}
	}

	public static long getDayDiffBetweenTwoDates (Timestamp fromDate, Timestamp tillDate) throws Exception {
		Calendar calTillDate	= null;
		Calendar calFromDate	= null;
		long 	 dayDiff		= 0;	

		try {
			calFromDate = Calendar.getInstance();
			calTillDate = Calendar.getInstance();

			calFromDate.setTime(fromDate);
			calFromDate.set(Calendar.HOUR_OF_DAY, 0);
			calFromDate.set(Calendar.MINUTE, 0);
			calFromDate.set(Calendar.SECOND, 0);
			calFromDate.set(Calendar.MILLISECOND, 0);

			calTillDate.setTime(tillDate);
			calTillDate.set(Calendar.HOUR_OF_DAY, 0);
			calTillDate.set(Calendar.MINUTE, 0);
			calTillDate.set(Calendar.SECOND, 0);
			calTillDate.set(Calendar.MILLISECOND, 0);

			dayDiff = ((calTillDate.getTimeInMillis() - calFromDate.getTimeInMillis())/(1000*60*60*24));

			return	dayDiff + 1;
		} catch (Exception e) {
			throw e;
		} finally{
			calTillDate	= null;
			calFromDate	= null;
		}
	}
	
	public static long getDayDiffBetweenTwoDatesWithABS (Timestamp fromDate, Timestamp tillDate) throws Exception {

		
		Calendar calTillDate	= null;
		Calendar calFromDate	= null;
		long 	 dayDiff		= 0;	

		try {
			calFromDate = Calendar.getInstance();
			calTillDate = Calendar.getInstance();

			calFromDate.setTime(fromDate);
			calFromDate.set(Calendar.HOUR_OF_DAY, 0);
			calFromDate.set(Calendar.MINUTE, 0);
			calFromDate.set(Calendar.SECOND, 0);
			calFromDate.set(Calendar.MILLISECOND, 0);

			calTillDate.setTime(tillDate);
			calTillDate.set(Calendar.HOUR_OF_DAY, 0);
			calTillDate.set(Calendar.MINUTE, 0);
			calTillDate.set(Calendar.SECOND, 0);
			calTillDate.set(Calendar.MILLISECOND, 0);

			dayDiff = ((calTillDate.getTimeInMillis() - calFromDate.getTimeInMillis())/(1000*60*60*24));

			return	Math.abs(dayDiff + 1);
		} catch (Exception e) {
			throw e;
		} finally{
			calTillDate	= null;
			calFromDate	= null;
		}
	}



	public static String getYesterdayDate() throws Exception {
		SimpleDateFormat	dateFormat	= null;
		Calendar 			calender	= null;
		
		try {
			dateFormat = new SimpleDateFormat(DD_MM_YYYY);
			calender = Calendar.getInstance();
			calender.add(Calendar.DATE, -1);
			
			return dateFormat.format(calender.getTime());			
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat	= null;
			calender	= null;
		}
	}

	public static ValueObject getPreviousMonthStartAndEndDate() throws Exception {
		Calendar			aCalendar					= null;
		java.util.Date 		firstDateOfPreviousMonth	= null;
		java.util.Date 		lastDateOfPreviousMonth		= null;
		String				startOfMonth				= null;
		String				endOfMonth					= null;
		ValueObject			valueObject					= null;
		SimpleDateFormat 	sdf							= null;
		
		try {
			aCalendar = Calendar.getInstance();
			aCalendar.add(Calendar.MONTH, -1);
			aCalendar.set(Calendar.DATE, 1);
			firstDateOfPreviousMonth = aCalendar.getTime();
			aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			lastDateOfPreviousMonth = aCalendar.getTime();
			sdf 			= new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
			startOfMonth	= sdf.format(firstDateOfPreviousMonth);
			endOfMonth		= sdf.format(lastDateOfPreviousMonth);
			valueObject		= new ValueObject();
			
			valueObject.put(DateTimeUtility.START_OF_MONTH, startOfMonth);
			valueObject.put(DateTimeUtility.END_OF_MONTH, endOfMonth);
			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			aCalendar					= null;
			firstDateOfPreviousMonth	= null;
			lastDateOfPreviousMonth		= null;
			startOfMonth				= null;
			endOfMonth					= null;
			valueObject					= null;
			sdf							= null;
		}
	}
	
	public static ValueObject getNextMonthStartAndEndDate() throws Exception {
		Calendar			aCalendar					= null;
		java.util.Date 		firstDateOfPreviousMonth	= null;
		java.util.Date 		lastDateOfPreviousMonth		= null;
		String				startOfMonth				= null;
		String				endOfMonth					= null;
		ValueObject			valueObject					= null;
		SimpleDateFormat 	sdf							= null;
		
		try {
			aCalendar = Calendar.getInstance();
			aCalendar.add(Calendar.MONTH, +1);
			aCalendar.set(Calendar.DATE, 1);
			firstDateOfPreviousMonth = aCalendar.getTime();
			aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			lastDateOfPreviousMonth = aCalendar.getTime();
			sdf 			= new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
			startOfMonth	= sdf.format(firstDateOfPreviousMonth);
			endOfMonth		= sdf.format(lastDateOfPreviousMonth);
			valueObject		= new ValueObject();
			
			valueObject.put(DateTimeUtility.START_OF_MONTH, startOfMonth);
			valueObject.put(DateTimeUtility.END_OF_MONTH, endOfMonth);
			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			aCalendar					= null;
			firstDateOfPreviousMonth	= null;
			lastDateOfPreviousMonth		= null;
			startOfMonth				= null;
			endOfMonth					= null;
			valueObject					= null;
			sdf							= null;
		}
	}

	public static ValueObject getPreviousWeekStartAndEndDate() throws Exception {
		java.util.Date		date			= null;
		Calendar 			cal				= null;
		java.util.Date		start			= null;
		java.util.Date		end				= null;
		SimpleDateFormat	sdf				= null;
		String				startOfWeek		= null;
		String				endOfweek		= null;
		ValueObject			valueObject		= null;
		
		try {
			date = new java.util.Date();
			cal = Calendar.getInstance();
			cal.setTime(date);
			int i = cal.get(Calendar.DAY_OF_WEEK) - cal.getFirstDayOfWeek();
			cal.add(Calendar.DATE, -i - 6);
			start = cal.getTime();
			cal.add(Calendar.DATE, 6);
			end = cal.getTime();
			sdf = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
			startOfWeek		= sdf.format(start);
			endOfweek		= sdf.format(end);
			valueObject		= new ValueObject();
			valueObject.put(DateTimeUtility.START_OF_WEEK, startOfWeek);
			valueObject.put(DateTimeUtility.END_OF_WEEK, endOfweek);
			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			date			= null;
			cal				= null;
			start			= null;
			end				= null;
			sdf				= null;
			startOfWeek		= null;
			endOfweek		= null;
			valueObject		= null;
		}
	}

	public static Timestamp getPreviousEndOfDateFromSelectedDate(String date) throws Exception {

		Calendar 	calendar 	= null;
		Timestamp 	timestamp2 	= null;

		try {

			calendar = Calendar.getInstance();
			calendar.setTime(getTimeStamp(date));

			calendar.add(Calendar.DATE, -1);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 998);	

			timestamp2 = new Timestamp(calendar.getTimeInMillis());

			return timestamp2;	
		} catch (Exception e) {
			throw e;
		} finally {
			calendar 	= null ;
			timestamp2 	= null;
		}
	}
	
	public static Timestamp getNextEndOfDateFromSelectedDate(String date) throws Exception {

		Calendar 	calendar 	= null;
		Timestamp 	timestamp2 	= null;

		try {

			calendar = Calendar.getInstance();
			calendar.setTime(getTimeStamp(date));

			calendar.add(Calendar.DATE, 1);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 998);	

			timestamp2 = new Timestamp(calendar.getTimeInMillis());

			return timestamp2;	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			calendar 	= null ;
			timestamp2 	= null;
		}
	}
	
	public static String getMonthStringByDate(Timestamp	dateFormat) throws Exception {
		Calendar 			calender	= null;

		try {

			calender = Calendar.getInstance();
			calender.setTime(dateFormat);
			calender.get(Calendar.MONTH);
			
			return new SimpleDateFormat("MMMMM").format(dateFormat);			
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat	= null;
			calender	= null;
		}
	}
	
	public static String getMonthStringAbbrByDate(Timestamp	dateFormat) throws Exception {
		Calendar 			calender	= null;

		try {

			calender = Calendar.getInstance();
			calender.setTime(dateFormat);
			calender.get(Calendar.MONTH);
			
			return new SimpleDateFormat("MMM").format(dateFormat);			
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat	= null;
			calender	= null;
		}
	}
	
	public static int getDayOfMonthByDate(Timestamp dateFormat) throws Exception {
		Calendar 			calender	= null;

		try {

			calender = Calendar.getInstance();
			calender.setTime(dateFormat);
			calender.get(Calendar.MONTH);
			
			return Integer.parseInt(new SimpleDateFormat("MM").format(dateFormat));
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat	= null;
			calender	= null;
		}
	}
	
	public static int getYearFromDate(Timestamp dateFormat) throws Exception {
		Calendar 			calender	= null;

		try {

			calender = Calendar.getInstance();
			calender.setTime(dateFormat);
			
			return calender.get(Calendar.YEAR);
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat	= null;
			calender	= null;
		}
	}
	
	public static int getMonthFromDate(Timestamp dateFormat) throws Exception {
		Calendar 			calender	= null;

		try {

			calender = Calendar.getInstance();
			calender.setTime(dateFormat);
			
			return calender.get(Calendar.MONTH) + 1;
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat	= null;
			calender	= null;
		}
	}
	
	public static int getMonthFromDate(Timestamp dateFormat, int month) throws Exception {
		Calendar 			calender	= null;

		try {

			calender = Calendar.getInstance();
			calender.setTime(dateFormat);
			
			return calender.get(Calendar.MONTH) + month;
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat	= null;
			calender	= null;
		}
	}
	
	public static ValueObject getCurrentDateTimeAsRange() throws Exception {

		ValueObject 		valueOutObject 	= null;
		SimpleDateFormat 	sdf 			= null;
		String 				currDate 		= null;
		Timestamp        	fromDate		= null;
		Timestamp        	toDate			= null;

		try {

			sdf 		= new SimpleDateFormat("dd-MM-yyyy");
			
			currDate 	= sdf.format(getCurrentTimeStamp());

			sdf               = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
			fromDate          = new Timestamp(sdf.parse(currDate + " 00:00:00").getTime());
			toDate            = new Timestamp(sdf.parse(currDate + " 23:59:59").getTime());

			valueOutObject = new ValueObject();

			valueOutObject.put("fromDate", fromDate);
			valueOutObject.put("toDate", toDate);

			return valueOutObject;

		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
			sdf 			= null;
			currDate 		= null;
			fromDate		= null;
			toDate			= null;
		}
	}
	
	public static Timestamp getNextMonthEndOfDateOnDays(int date, int month, int year) throws Exception {

		Calendar 	calendar 	= null;

		try {

			/* Date		- Monday date
			 * month	- Previous Month
			 * year		- Current Year
			 */
			calendar = Calendar.getInstance();
			calendar.set(year, month, date);

			calendar.add(Calendar.DATE, 0);
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 998);	

			return new Timestamp(calendar.getTimeInMillis());	
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			calendar 	= null;
		}
	}
	
	public static Timestamp getTimeStampFromDateTimeString(String dateTimeString) {
		return Timestamp.valueOf(dateTimeString);
	}
	
	public static Timestamp getDateTimeFromStr(String date, String format) throws Exception {

		SimpleDateFormat sdf = null;

		try {
			sdf = new SimpleDateFormat(format);
			return new Timestamp((sdf.parse(date)).getTime());
		} catch (Exception e) {
			return getCurrentTimeStamp();
		} finally {
			sdf = null;
		}
	}
	
    public static int getPartOfDate(String partName, Date date) {

        if (partName == null || date == null) {
            return 0;
        }
        
        int ret = 0;
        
        java.util.List<String> filedsList = java.util.Arrays.asList(fieldsName);
        
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        switch (filedsList.indexOf(partName)) {
        case 0:
            ret = c.get(Calendar.YEAR);
            break;
        case 1:
            ret = c.get(Calendar.MONTH);
            break;
        case 2:
            ret = c.get(Calendar.HOUR);
            break;
        case 3:
            ret = c.get(Calendar.MINUTE);
            break;
        case 4:
            ret = c.get(Calendar.SECOND);
            break;
        case 5:
            ret = c.get(Calendar.DAY_OF_WEEK);
            break;
        case 6:
            ret = c.get(Calendar.DAY_OF_MONTH);
            break;
        case 7:
            ret = c.get(Calendar.DAY_OF_YEAR);
            break;
        case 8:
            // the ordinal number of current week in a month (it means a 'week' may be not contain 7 days)
            ret = c.get(Calendar.WEEK_OF_MONTH);
            break;
        case 9:
            // 1-7 correspond to 1, 8-14 correspond to 2,...
            ret = c.get(Calendar.DAY_OF_WEEK_IN_MONTH);
            break;
        case 10:
            ret = c.get(Calendar.WEEK_OF_YEAR);
            break;
        case 11:
            ret = (c.get(Calendar.ZONE_OFFSET)) / (1000 * 60 * 60);
            break;
        default:
            break;

        }
        return ret;
    }

    /**
     * return an ISO formatted random date
     *
     *
     * {talendTypes} Date
     *
     * {Category} TalendDate
     *
     * {param} string("2007-01-01") min : minimum date
     *
     * {param} string("2008-12-31") max : maximum date (superior to min)
     *
     * {example} getRandomDate("1981-01-18", "2005-07-24") {example} getRandomDate("1980-12-08", "2007-02-26")
     */
    public static Date getRandomDate(String minDate, String maxDate) {
        if (minDate == null) {
            minDate = "1970-01-01";
        }

        if (maxDate == null) {
            maxDate = "2099-12-31";
        }

        if (!minDate.matches("\\d{4}-\\d{2}-\\d{2}") || !minDate.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new IllegalArgumentException("The parameter should be \"yyy-MM-dd\"");
        }

        int minYear 	= Integer.parseInt(minDate.substring(0, 4));
        int minMonth 	= Integer.parseInt(minDate.substring(5, 7));
        int minDay 		= Integer.parseInt(minDate.substring(8, 10));

        int maxYear 	= Integer.parseInt(maxDate.substring(0, 4));
        int maxMonth 	= Integer.parseInt(maxDate.substring(5, 7));
        int maxDay 		= Integer.parseInt(maxDate.substring(8, 10));

        Calendar minCal = Calendar.getInstance();
        minCal.set(Calendar.YEAR, minYear);
        minCal.set(Calendar.MONTH, minMonth - 1);
        minCal.set(Calendar.DAY_OF_MONTH, minDay);

        Calendar maxCal = Calendar.getInstance();
        maxCal.set(Calendar.YEAR, maxYear);
        maxCal.set(Calendar.MONTH, maxMonth - 1);
        maxCal.set(Calendar.DAY_OF_MONTH, maxDay);

        long random 	= minCal.getTimeInMillis()
                + (long) ((maxCal.getTimeInMillis() - minCal.getTimeInMillis() + 1) * Math.random());
        return new Date(random);
    }
    
    /**
     * get last day of the month
     *
     * @param date (a date value)
     * @return a new date (the date has been changed to the last day)
     *
     * {talendTypes} Date
     *
     * {Category} TalendDate
     *
     * {param} date(mydate) date : the date to get last date of current month
     *
     * {example} getFirstDayMonth(2008/02/24 12:15:25) return 2008/02/28 12:15:25
     */
    public static Date getLastDayOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DATE, lastDay);
        return (Date) c.getTime();
    }
    
    /**
     * get first day of the month
     *
     * @param date (a date value)
     * @return a new date (the date has been changed to the first day)
     *
     * {talendTypes} Date
     *
     * {Category} TalendDate
     *
     * {param} date(mydate) date : the date to get first date of current month
     *
     * {example} getFirstDayMonth(2008/02/24 12:15:25) return 2008/02/01 12:15:25 #
     */
    public static Date getFirstDayOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DATE, 1);
        return (Date) c.getTime();
    }
    
    /**
     * return difference between two dates
     *
     * @param Date1 ( first date )
     * @param Date1 ( second date )
     * @return a number of years, months, days ... date1 - date2
     *
     * {talendTypes} Long
     *
     * {Category} TalendDate
     *
     * {param} date(myDate) date1 : the first date to compare
     *
     * {param} date(myDate) date2 : the second date to compare
     *
     * {examples} diffDate(2008/11/24 12:15:25, 2008/10/14 16:10:35) : return 41 #
     */

    public static long diffDate(Date date1, Date date2) {
        return diffDate(date1, date2, "dd", true);
    }
    
    /**
     * return difference between two dates
     *
     * @param Date1 ( first date )
     * @param Date1 ( second date )
     * @param dateType value=("yyyy","MM","dd","HH","mm","ss","SSS") for type of return
     * @param ignoreDST
     * @return a number of years, months, days ... date1 - date2
     *
     * {talendTypes} Long
     *
     * {Category} TalendDate
     *
     * {param} date(myDate) date1 : the first date to compare
     *
     * {param} date(myDate2) date2 : the second date to compare
     *
     * {param} String("MM") dateType : the difference on the specified part
     *
     * {param} boolean(true) ignoreDST : ignore daylight saving time or not.
     *
     * {examples}
     *
     * ->> diffDate(2012/03/26 00:00:00, 2012/03/24 00:00:00, "dd", true) : return 2 not 1 in GMT+1#
     *
     * ->> diffDate(2012/03/26 00:00:00, 2012/03/24 00:00:00, "dd", false) : return 1 not 2 in GMT+1#
     */
    
    public static long diffDate(Date date1, Date date2, String dateType, boolean ignoreDST) {

        if (date1 == null) {
            date1 = new Date(0);
        }
        if (date2 == null) {
            date2 = new Date(0);
        }

        if (dateType == null) {
            dateType = "SSS";
        }

        // ignore DST
        int addDSTSavings = 0;
        if (ignoreDST) {
            boolean d1In = TimeZone.getDefault().inDaylightTime(date1);
            boolean d2In = TimeZone.getDefault().inDaylightTime(date2);
            if (d1In != d2In) {
                if (d1In) {
                    addDSTSavings = TimeZone.getDefault().getDSTSavings();
                } else if (d2In) {
                    addDSTSavings = -TimeZone.getDefault().getDSTSavings();
                }
            }
        }

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);

        if (dateType.equalsIgnoreCase("yyyy")) { //$NON-NLS-1$
            return c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR);
        } else if (dateType.equals("MM")) { //$NON-NLS-1$
            return (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) * 12 + (c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH));
        } else {
            long diffTime = date1.getTime() - date2.getTime() + addDSTSavings;

            if (dateType.equalsIgnoreCase("HH")) { //$NON-NLS-1$
                return diffTime / (1000 * 60 * 60);
            } else if (dateType.equals("mm")) { //$NON-NLS-1$
                return diffTime / (1000 * 60);
            } else if (dateType.equalsIgnoreCase("ss")) { //$NON-NLS-1$
                return diffTime / 1000;
            } else if (dateType.equalsIgnoreCase("SSS")) { //$NON-NLS-1$
                return diffTime;
            } else if (dateType.equalsIgnoreCase("dd")) {
                return diffTime / (1000 * 60 * 60 * 24);
            } else {
                throw new RuntimeException("Can't support the dateType: " + dateType);
            }
        }
    }
    
    /**
     * return difference between two dates ignore DST
     *
     * @param Date1 ( first date )
     * @param Date1 ( second date )
     * @param dateType value=("yyyy","MM","dd","HH","mm","ss","SSS") for type of return
     * @return a number of years, months, days ... date1 - date2
     *
     * {talendTypes} Long
     *
     * {Category} TalendDate
     *
     * {param} date(myDate) date1 : the first date to compare
     *
     * {param} date(myDate2) date2 : the second date to compare
     *
     * {param} String("MM") dateType : the difference on the specified part
     *
     * {examples}
     *
     * ->> diffDate(2012/03/26 00:00:00, 2012/03/24 00:00:00, "dd") : return 2 not 1 in GMT+1#
     */
    public static long diffDateIgnoreDST(Date date1, Date date2, String dateType) {
        return diffDate(date1, date2, dateType, true);
    }
    
    /**
     * return difference between two dates by floor
     *
     * @param Date1 ( first date )
     * @param Date1 ( second date )
     * @param dateType value=("yyyy","MM") for type of return
     * @return a number of years, months (date1 - date2)
     *
     * {talendTypes} Integer
     *
     * {Category} TalendDate
     *
     * {param} date(myDate) date1 : the first date to compare
     *
     * {param} date(myDate2) date2 : the second date to compare
     *
     * {param} String("MM") dateType : the difference on the specified part
     *
     * {examples}
     *
     * ->> diffDate(2009/05/10, 2008/10/15, "yyyy") : return 0
     *
     * ->> diffDate(2009/05/10, 2008/10/15, "MM") : return 6
     */
    public static int diffDateFloor(Date date1, Date date2, String dateType) {
        if (date1 == null) {
            date1 = new Date(0);
        }
        if (date2 == null) {
            date2 = new Date(0);
        }

        if (dateType == null) {
            dateType = "yyyy";
        }

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);

        int result = 0;
        Calendar tmp = null;
        boolean flag = false;
        if (c1.compareTo(c2) < 0) {
            flag = true;
            tmp = c1;
            c1 = c2;
            c2 = tmp;
        }
        result = (c1.get(Calendar.YEAR) - c2.get(Calendar.YEAR)) * 12 + (c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH));
        c2.add(Calendar.MONTH, result);
        result += c2.after(c1) ? -1 : 0;
        if (flag) {
            result = -result;
        }

        if (dateType.equalsIgnoreCase("yyyy")) {
            return result / 12;
        } else if (dateType.equals("MM")) {
            return result;
        } else {
            throw new RuntimeException("Can't support the dateType: " + dateType + " ,please try \"yyyy\" or \"MM\"");
        }
    }
    
    /**
     * add number of day, month ... to a date (with Date given in String with a pattern)
     *
     * @param date (a Date given in string)
     * @param pattern (the pattern for the related date)
     * @param nb (the value to add)
     * @param dateType (date pattern = ("yyyy","MM","dd","HH","mm","ss","SSS" ))
     * @return a new date
     *
     * {talendTypes} String
     *
     * {Category} TalendDate
     *
     * {param} String("") string : date represent in string
     *
     * {param} String("yyyy-MM-dd") pattern : date pattern
     *
     * {param} int(addValue) nb : the added value
     *
     * {param} date("MM") dateType : the part to add
     *
     * {examples}
     *
     * ->> addDate("2008/11/24 12:15:25", "yyyy-MM-dd HH:mm:ss", 5,"dd") return "2008/11/29 12:15:25"
     *
     * ->> addDate("2008/11/24 12:15:25", "yyyy/MM/DD HH:MM:SS", 5,"ss") return "2008/11/24 12:15:30" #
     *
     */
    public static String addDate(String string, String pattern, int nb, String dateType) {
        if (string == null || dateType == null) {
            return null;
        }
        java.util.Date date = null;

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(pattern);
        try {
            date = sdf.parse(string);
        } catch (ParseException e) {
            throw new RuntimeException(pattern + " can't support the date!"); //$NON-NLS-1$
        }
        String dateString = sdf.format(addDate((Date) date, nb, dateType));

        return dateString;
    }
    
    /**
     * add number of day, month ... to a date (with Java date type !)
     *
     * @param date (a <code>Date</code> type value)
     * @param nb (the value to add)
     * @param dateType (date pattern = ("yyyy","MM","dd","HH","mm","ss","SSS" ))
     * @return a new date
     *
     * {talendTypes} Date
     *
     * {Category} TalendDate
     *
     * {param} date(myDate) date : the date to update
     *
     * {param} int(addValue) nb : the added value
     *
     * {param} date("MM") dateType : the part to add
     *
     * {examples}
     *
     * ->> addDate(dateVariable), 5,"dd") return a date with 2008/11/29 12:15:25 (with dateVariable is a date with
     * 2008/11/24 12:15:25) #
     *
     * ->> addDate(2008/11/24 12:15:25, 5,"ss") return 2008/11/24 12:15:30 #
     *
     */
    public static Date addDate(Date date, int nb, String dateType) {
        if (date == null || dateType == null) {
            return null;
        }

        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);

        if (dateType.equalsIgnoreCase("yyyy")) { //$NON-NLS-1$
            c1.add(Calendar.YEAR, nb);
        } else if (dateType.equals("MM")) { //$NON-NLS-1$
            c1.add(Calendar.MONTH, nb);
        } else if (dateType.equalsIgnoreCase("dd")) { //$NON-NLS-1$
            c1.add(Calendar.DAY_OF_MONTH, nb);
        } else if (dateType.equals("HH")) { //$NON-NLS-1$
            c1.add(Calendar.HOUR, nb);
        } else if (dateType.equals("mm")) { //$NON-NLS-1$
            c1.add(Calendar.MINUTE, nb);
        } else if (dateType.equalsIgnoreCase("ss")) { //$NON-NLS-1$
            c1.add(Calendar.SECOND, nb);
        } else if (dateType.equalsIgnoreCase("SSS")) { //$NON-NLS-1$
            c1.add(Calendar.MILLISECOND, nb);
        } else {
            throw new RuntimeException("Can't support the dateType: " + dateType);
        }

        return (Date) c1.getTime();
    }
    
    /*
     * Method For Getting List of Dates Between Two Dates
     */
	public static List<java.util.Date> getDaysBetweenDates(java.util.Date startdate, java.util.Date enddate, List<java.util.Date> 	dateList) throws Exception	{
		Calendar 			endCalendar 		= null;
		Calendar 			calendar 			= null;
		java.util.Date 		result 				= null;
		try {
			
			endCalendar = new GregorianCalendar();
			calendar 	= new GregorianCalendar();
			
			endCalendar.setTime(enddate);
			endCalendar.add(Calendar.DATE, 1);
			
		    calendar.setTime(startdate);

		    while (calendar.getTime().before(endCalendar.getTime())) {
		        
		    	result = calendar.getTime();
		        if(!dateList.contains(result)) {
		        	dateList.add(result);
		        }
		        calendar.add(Calendar.DATE, 1);
		    }
		    return dateList;
		} catch (Exception e) {
			throw e;
		} finally {
			endCalendar 	= null;
			calendar 		= null;
			result 			= null;
		}
	}
	
	public static java.util.Date getDateTimeFromDateTimeString(String date, String time) throws Exception {
		SimpleDateFormat			simpleDateFormat	= null;
		LocalDateTime 				localDateTime 		= null;
		LocalDate 					datePart 			= null;
		LocalTime 					timePart 			= null;
		String 						dateStr 			= null; 
		try {

			simpleDateFormat	= new SimpleDateFormat(DD_MM_YYYY);
			dateStr 			= new SimpleDateFormat("yyyy-MM-dd").format(simpleDateFormat.parse(date)); // format date string in yyyy-MM-dd format
			 
			datePart 			= LocalDate.parse(dateStr);
			if(time != null) {
				if(time.length() == 4) {
					time = "0"+time;
				}
				
				timePart 			= LocalTime.parse(time);
			}
		    localDateTime	 	= LocalDateTime.of(datePart, timePart);
		    
		    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		} catch (Exception e) {
			throw e;
		} finally {
			simpleDateFormat	= null;
			localDateTime 		= null;
			datePart 			= null;
			timePart 			= null;
			dateStr 			= null;
		}
	}
	
	public static ValueObject getStartAndEndDateStr(String dateRange) throws Exception {
		
		String[] 			from_TO_DateRange 				= null;
		String				fromDateStr						= null;							
		String				toDateStr						= null;	
		ValueObject			valOutObject					= null;
		
		try {
		
			if(dateRange.contains("to")) {
				from_TO_DateRange 	= dateRange.split(" to ");
				
				fromDateStr			= from_TO_DateRange[0].trim() + DAY_START_TIME;
				toDateStr			= from_TO_DateRange[1].trim() + DAY_END_TIME;
				
				valOutObject		= new ValueObject();
				valOutObject.put(FROM_DATE, fromDateStr);
				valOutObject.put(TO_DATE, toDateStr);
			}
			
			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			from_TO_DateRange 				= null;
			fromDateStr						= null;
			toDateStr						= null;
			valOutObject					= null;
		}
	}

 public static ValueObject getStartAndEndDateStr(String dateRange, String initialFormat, String resultFormat) throws Exception {
		
		String[] 			from_TO_DateRange 				= null;
		String				fromDateStr						= null;							
		String				toDateStr						= null;	
		ValueObject			valOutObject					= null;
		
		try {
			if(dateRange.contains("to")) {
				from_TO_DateRange 	= dateRange.split(" to ");
				
				fromDateStr			= from_TO_DateRange[0].trim()+ DAY_START_TIME;
				toDateStr			= from_TO_DateRange[1].trim()+ DAY_END_TIME;
				
				valOutObject		= new ValueObject();
				valOutObject.put(FROM_DATE, formatDate(fromDateStr, initialFormat, resultFormat));
				valOutObject.put(TO_DATE, formatDate(toDateStr, initialFormat, resultFormat));
			}
			
			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			from_TO_DateRange 				= null;
			fromDateStr						= null;
			toDateStr						= null;
			valOutObject					= null;
		}
	}
 
 	public static String formatDate (String date, String initDateFormat, String endDateFormat) throws ParseException {

	    java.util.Date initDate = new SimpleDateFormat(initDateFormat).parse(date);
	    SimpleDateFormat formatter = new SimpleDateFormat(endDateFormat);
	    String parsedDate = formatter.format(initDate);

	    return parsedDate;
	}


	public static Timestamp geTimeStampFromDate(java.util.Date	date) throws Exception {

		try {
			return getCalendarTimeFromTimestamp(new Timestamp(date.getTime()), 0, 0, 0, 0);
		} catch (Exception e) {
			throw e;
		} 
	}

	public static Timestamp geTimeStampFromDateWithTime(java.util.Date	date) throws Exception {

		try {
			if(date != null) {
				return new Timestamp(date.getTime());
			}else {
				return null;
			}
			
		} catch (Exception e) {
			throw e;
		} 
	}

	
	
	public static Timestamp  getFirstDayOfMonth(Timestamp	timestamp) throws Exception{
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		
		LocalDate	localDate	=	LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)).with( TemporalAdjusters.firstDayOfMonth());
		
		return Timestamp.valueOf(localDate.atStartOfDay());
	}
	
	public static Timestamp  getLastDayOfMonth(Timestamp	timestamp) throws Exception{
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(timestamp);
		
		LocalDate	localDate	=	LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH)).with( TemporalAdjusters.lastDayOfMonth());
		
		return Timestamp.valueOf(localDate.atStartOfDay());
	}
	
	public static Timestamp  getFirstDayOfPreviousMonth(Timestamp	timestamp) throws Exception{
		
	Calendar aCalendar = Calendar.getInstance();
	
	aCalendar.setTime(timestamp);
	// add -1 month to current month
	aCalendar.add(Calendar.MONTH, -1);
	// set DATE to 1, so first date of previous month
	aCalendar.set(Calendar.DATE, 1);

		java.util.Date firstDateOfPreviousMonth = aCalendar.getTime();
		
		return new Timestamp(firstDateOfPreviousMonth.getTime());

	}

public static Timestamp  getFirstDayOfNextMonth(Timestamp	timestamp) throws Exception{
	
			Calendar aCalendar = Calendar.getInstance();
			
			aCalendar.setTime(timestamp);
			// add -1 month to current month
			aCalendar.add(Calendar.MONTH, +1);
			// set DATE to 1, so first date of previous month
			aCalendar.set(Calendar.DATE, 1);
			
				java.util.Date firstDateOfPreviousMonth = aCalendar.getTime();
				
				return new Timestamp(firstDateOfPreviousMonth.getTime());

}
	
	public static java.util.Date getDateWithoutTimeUsingFormat(String format) 
			  throws ParseException {
			    SimpleDateFormat formatter = new SimpleDateFormat(format);
			    return formatter.parse(formatter.format(new java.util.Date()));
	}
	
	public static int getNoOfDaysInMonth(Timestamp	date) throws Exception{
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	public static ValueObject getCurrentMonthStartAndEndDate() throws Exception {
		Calendar			aCalendar					= null;
		java.util.Date 		firstDateOfPreviousMonth	= null;
		java.util.Date 		lastDateOfPreviousMonth		= null;
		String				startDateOfMonth			= null;
		String				endDateOfMonth				= null;
		ValueObject			valueObject					= null;
		SimpleDateFormat 	sdf							= null;
		
		try {
			aCalendar = Calendar.getInstance();
			aCalendar.add(Calendar.MONTH, 0);
			aCalendar.set(Calendar.DATE, 1);
			firstDateOfPreviousMonth = aCalendar.getTime();
			aCalendar.set(Calendar.DATE, aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			lastDateOfPreviousMonth = aCalendar.getTime();
			sdf 			= new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD);
			startDateOfMonth	= sdf.format(firstDateOfPreviousMonth);
			endDateOfMonth		= sdf.format(lastDateOfPreviousMonth);
			
			valueObject		= new ValueObject();
			
			valueObject.put(DateTimeUtility.START_DATE_OF_MONTH, startDateOfMonth);
			valueObject.put(DateTimeUtility.END_DATE_OF_MONTH, endDateOfMonth);
			return valueObject;
		} catch (Exception e) {
			throw e;
		} finally {
			aCalendar					= null;
			firstDateOfPreviousMonth	= null;
			lastDateOfPreviousMonth		= null;
			startDateOfMonth			= null;
			endDateOfMonth				= null;
			valueObject					= null;
			sdf							= null;
		}
	}
	
	public static List<java.util.Date> getDaysBetweenDates(java.util.Date startdate, java.util.Date enddate)
	{
	    List<java.util.Date> dates = new ArrayList<java.util.Date>();
	    Calendar calendar = new GregorianCalendar();
	    calendar.setTime(startdate);

	    while (calendar.getTime().before(enddate))
	    {
	    	java.util.Date result = calendar.getTime();
	        dates.add(result);
	        calendar.add(Calendar.DATE, 1);
	    }
	    return dates;
	}

	public static Timestamp atEndOfDay(Timestamp date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(date);
	    calendar.set(Calendar.HOUR_OF_DAY, 23);
	    calendar.set(Calendar.MINUTE, 59);
	    calendar.set(Calendar.SECOND, 59);
	    calendar.set(Calendar.MILLISECOND, 999);
	    return new Timestamp(calendar.getTime().getTime());
	}
	
	public static int getNoOfMonthBetweenTwoDates(Timestamp fromDate, Timestamp tillDate) throws Exception{
		Calendar startCalendar = new GregorianCalendar();
    	startCalendar.setTime(fromDate);
    	
    	Calendar endCalendar = new GregorianCalendar();
    	endCalendar.setTime(tillDate);

    	int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
    	return (diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH) + 1);
	}
	
	public static String getBackDateBasedOnGivenDate(String date, int noOfDays) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		try{
		   c.setTime(sdf.parse(date));    //Setting the date to the given date
		}catch(ParseException e){
			e.printStackTrace();
		 }
		   
		c.add(Calendar.DAY_OF_MONTH, -noOfDays);  
		String newDate = sdf.format(c.getTime());  
		
		return newDate;
	}
	
	public static String getBackDateBasedOnGivenDate(java.util.Date date, int noOfDays) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(date);    //Setting the date to the given date
		   
		c.add(Calendar.MONTH, -noOfDays);
		c.add(Calendar.DAY_OF_MONTH, 1);
		String newDate = sdf.format(c.getTime());  
		
		return newDate;
	}
	
	public static String getNextDateByMonth(Timestamp timeStamp, int month) throws Exception {
		SimpleDateFormat dateFormat = null;
			try {
				
				if (timeStamp == null) {
				return null;
				}
				dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(timeStamp);
				int nextMonth   = DateTimeUtility.getMonthFromDate(timeStamp,month);
				calendar.set(Calendar.MONTH, nextMonth);
	
				java.util.Date utilDate = calendar.getTime();
				java.sql.Date sDate = new java.sql.Date(utilDate.getTime());
				
				return dateFormat.format(sDate);
		
			} catch (Exception e) {
				throw e;
			} 
		}
	
	public static Timestamp getTimeStampFromStringInYY_MM_DD_HH_MM_SS_Format(String date) throws Exception {

		SimpleDateFormat	dateFormat		= null;
		java.util.Date		parsedTimeStamp	= null;
		Timestamp			timestamp		= null;

		try {
			dateFormat		=	new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			parsedTimeStamp	=	dateFormat.parse(date);
			timestamp		=	new Timestamp(parsedTimeStamp.getTime());
			return timestamp;
		} catch (Exception e) {
			throw e;
		} finally {
			dateFormat		= null;
			parsedTimeStamp	= null;
			timestamp		= null;
		}
	}
	
	public static Timestamp getTimeStampFromDate(java.util.Date	date) throws Exception{
		
		return new Timestamp(date.getTime());
	}
	
	public static java.util.Date substractDayFromDate(java.util.Date date , int days)throws Exception{
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -days);
		return cal.getTime();
	}
	
	public static String getCurrentDateInString() {
		
		SimpleDateFormat	dateFormat = new SimpleDateFormat(YYYY_MM_DD);
		
		return dateFormat.format(new java.util.Date());
	}
	
	public static String getCurrentDateInStringViewFormat() {
		
		SimpleDateFormat	dateFormat = new SimpleDateFormat(DD_MM_YYYY);
		
		return dateFormat.format(new java.util.Date());
	}
	
	public static java.util.Date getNextDate(String  curDate, Integer nextDateNum) throws ParseException {
		 java.util.Date 		date	= null;
		try {
			final SimpleDateFormat 	format  	= new SimpleDateFormat(YYYY_MM_DD);
			final Calendar 			calendar 	= Calendar.getInstance();
									date 		= format.parse(curDate);
			
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, nextDateNum);
			return calendar.getTime(); 
		} catch (Exception e) {
			throw e;
		}
	}
	public static String getNextDateStr(String  curDate, Integer nextDateNum) throws ParseException {
		java.util.Date 		date	= null;
		try {
			final SimpleDateFormat 	format  	= new SimpleDateFormat(YYYY_MM_DD);
			final Calendar 			calendar 	= Calendar.getInstance();
			date 		= format.parse(curDate);
			
			calendar.setTime(date);
			calendar.add(Calendar.DAY_OF_YEAR, nextDateNum);
			return format.format(calendar.getTime()); 
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static String getFirstDayOfFiscalYear(java.util.Date date, String format) {
		Date			utilDate	= null;
		Calendar 		cal			= null;
		
		try {
			cal = Calendar.getInstance();
			cal.setTime(date);
			
			if (cal.get(Calendar.MONTH) < FISCAL_START_MONTH) {
				utilDate = getCustomDate(cal.get(Calendar.YEAR)- 1, Month.APRIL.getValue(), 1);
			} else {
				utilDate = getCustomDate(cal.get(Calendar.YEAR), Month.APRIL.getValue(), 1);
			}
			
			cal.setTime(utilDate);
		
			cal.set(Calendar.MILLISECOND, 0);

			 SimpleDateFormat sdf = new SimpleDateFormat(format);
			    
			 return sdf.format(cal.getTime());

		} catch (Exception e) {
			throw e;
		} finally {
			utilDate		= null;
			cal				= null;
		}
	}

	public static String getLastDayOfFiscalYear(java.util.Date date, String format) {
		Date			utilDate	= null;
		Calendar 		cal			= null;
		
		try {
			cal = Calendar.getInstance();
			cal.setTime(date);
			
			if (cal.get(Calendar.MONTH) < FISCAL_START_MONTH) {
				utilDate = getCustomDate(cal.get(Calendar.YEAR), Month.MARCH.getValue(), 31);
			} else {
				utilDate = getCustomDate(cal.get(Calendar.YEAR) + 1, Month.MARCH.getValue(), 31);
			}
			
			cal.setTime(utilDate);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 0);

			 SimpleDateFormat sdf = new SimpleDateFormat(format);
			    
			 return sdf.format(cal.getTime());

		} catch (Exception e) {
			throw e;
		} finally {
			utilDate		= null;
			cal				= null;
		}
	}
	
	public static String getCurrentDateStr(String format) throws Exception{
		SimpleDateFormat	dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(new java.util.Date());
	}
	
	public static String getStringDateFromDate(java.util.Date date, String format) throws Exception{
		SimpleDateFormat	dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static String getBackDateStr(int noOfDays) throws Exception {
		return getStringDateFromDate(substractDayFromDate(new java.util.Date(), noOfDays), YYYY_MM_DD);
	}
	
	public static String getBackDateStrWithTime(int noOfDays) throws Exception {
		return getStringDateFromDate(substractDayFromDate(new java.util.Date(), noOfDays), YYYY_MM_DD_HH_MM_SS);
	}
	
	public static boolean isSameDate(String fromDate, String toDate, String format) {
		 SimpleDateFormat  dateFormat	= new SimpleDateFormat(format);
	        try {
	        	java.util.Date d1 = dateFormat.parse(fromDate); 
				java.util.Date d2 = dateFormat.parse(toDate);
				
				if (d1.compareTo(d2) == 0) { 
					return true;
		        } 
			} catch (ParseException e) {
				e.printStackTrace();
				
			} 
	        
	        return false;
	}
	
	public static boolean isSameDate(java.util.Date fromDate, java.util.Date toDate) {
				
				if (fromDate.equals(toDate)) { 
					return true;
		        } 
	        
	        return false;
	}
	
	public static String substractTimeFromDate(String date, String format, int noOfHour) throws Exception {
		
		try {
			SimpleDateFormat	dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
			Timestamp	currDateTime	=	getTimeStamp(date, format);
			
			Calendar cal = Calendar.getInstance();
			// remove next line if you're always using the current time.
			cal.setTime(currDateTime);
			cal.add(Calendar.HOUR, -noOfHour);
			
			return dateFormat.format(cal.getTime());
		} catch (Exception e) {
			throw e;
		}
	}
	
	public static List<String> getNextDaysList(int noOfdays) throws Exception{
		try {
			List<String>   daysList = new ArrayList<String>();
			
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			Calendar date = Calendar.getInstance();
			String[] calendar = new String[noOfdays];
 			for(int i = 0; i < noOfdays;i++){
			    calendar[i] = format.format(date.getTime());
			    date.add(Calendar.DATE  , 1);
			    int day = date.get(Calendar.DAY_OF_WEEK);
			    daysList.add(calendar[i]+"_"+getDaysName(day));
			}
 			
 			return daysList;
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	private static String getDaysName(int day ) {
		String days = "";
		switch (day) {
        case 1:
        	days = "Sunday";
            break;
        case 2:
        	days = "Monday";
            break;
        case 3:
        	days = "Tuesday";
            break;
        case 4:
        	days = "Wednesday";
            break;
        case 5:
            days = "Thursday";
            break;
        case 6:
            days = "Friday";
            break;
        case 7:
            days = "Saturday";
	  }
		return days;
	}
	
	public static String getStrDateFromStrDate(String date, String format) throws Exception{
		SimpleDateFormat	dateFormat = new SimpleDateFormat(format);
		SimpleDateFormat	sqlFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		return sqlFormat.format(dateFormat.parse(date));
	}
	
	
	/**if Date = 10-01-2021 and number of back date is 1 it will give (09-01-2021 23:59:59 in InMilliseconds like >> 1623263300000)**/
	public static long getUnixEpochEndTimeTimestampInMilliseconds(int noOfBackDate)throws Exception{

		SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		cal.add(Calendar.DATE, -noOfBackDate);
		java.util.Date today = cal.getTime();

		String currentTime = dateFormat.format(today);
		java.util.Date date = dateFormat.parse(currentTime);

		return date.getTime();
	}
	
	/**if Date = 10-01-2021 and number of back date is 1 it will give (09-01-2021 00:00:00 in InMilliseconds like >> 1623263400000)**/
	public static long getUnixEpochStartTimeTimestampInMilliseconds(int noOfBackDate)throws Exception{

		SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		cal.set(Calendar.MILLISECOND, 999);
		cal.add(Calendar.DATE, -noOfBackDate);
		java.util.Date today = cal.getTime();

		String currentTime = dateFormat.format(today);
		java.util.Date date = dateFormat.parse(currentTime);

		return date.getTime();
	}
	
	
	public static String getStrDateFromStrDate(String date, String format, String requiredFormate) throws Exception{
		SimpleDateFormat	dateFormat = new SimpleDateFormat(format);
		SimpleDateFormat	sqlFormat = new SimpleDateFormat(requiredFormate);
		return sqlFormat.format(dateFormat.parse(date));
	}
	
	public static String getSqlStrDateFromStrDate(String date, String requiredFormate) throws Exception{
		if(date != null)
			date = date.trim();
		SimpleDateFormat	dateFormat = new SimpleDateFormat(determineDateFormat(date));
		SimpleDateFormat	sqlFormat = new SimpleDateFormat(requiredFormate);
		return sqlFormat.format(dateFormat.parse(date));
	}
	
	 // Converters ---------------------------------------------------------------------------------

	    /**
	     * Convert the given date to a Calendar object. The TimeZone will be derived from the local
	     * operating system's timezone.
	     * @param date The date to be converted to Calendar.
	     * @return The Calendar object set to the given date and using the local timezone.
	     */
	    public static Calendar toCalendar(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.clear();
	        calendar.setTime(date);
	        return calendar;
	    }
	    
	    public static Calendar toCalendar(java.util.Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.clear();
	        calendar.setTime(date);
	        return calendar;
	    }

	    /**
	     * Convert the given date to a Calendar object with the given timezone.
	     * @param date The date to be converted to Calendar.
	     * @param timeZone The timezone to be set in the Calendar.
	     * @return The Calendar object set to the given date and timezone.
	     */
	    public static Calendar toCalendar(Date date, TimeZone timeZone) {
	        Calendar calendar = toCalendar(date);
	        calendar.setTimeZone(timeZone);
	        return calendar;
	    }

	    /**
	     * Parse the given date string to date object and return a date instance based on the given
	     * date string. This makes use of the {@link DateUtil#determineDateFormat(String)} to determine
	     * the SimpleDateFormat pattern to be used for parsing.
	     * @param dateString The date string to be parsed to date object.
	     * @return The parsed date object.
	     * @throws ParseException If the date format pattern of the given date string is unknown, or if
	     * the given date string or its actual date is invalid based on the date format pattern.
	     */
	    public static java.util.Date parse(String dateString) throws ParseException {
	        String dateFormat = determineDateFormat(dateString);
	        if (dateFormat == null) {
	            throw new ParseException("Unknown date format.", 0);
	        }
	        return parse(dateString, dateFormat);
	    }

	    /**
	     * Validate the actual date of the given date string based on the given date format pattern and
	     * return a date instance based on the given date string.
	     * @param dateString The date string.
	     * @param dateFormat The date format pattern which should respect the SimpleDateFormat rules.
	     * @return The parsed date object.
	     * @throws ParseException If the given date string or its actual date is invalid based on the
	     * given date format pattern.
	     * @see SimpleDateFormat
	     */
	    public static java.util.Date parse(String dateString, String dateFormat) throws ParseException {
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
	        simpleDateFormat.setLenient(false); // Don't automatically convert invalid date.
	        return simpleDateFormat.parse(dateString);
	    }

	    
	    
	    
	    // Validators ---------------------------------------------------------------------------------

	    /**
	     * Checks whether the actual date of the given date string is valid. This makes use of the
	     * {@link DateUtil#determineDateFormat(String)} to determine the SimpleDateFormat pattern to be
	     * used for parsing.
	     * @param dateString The date string.
	     * @return True if the actual date of the given date string is valid.
	     */
	    public static boolean isValidDate(String dateString) {
	        try {
	            parse(dateString);
	            return true;
	        } catch (ParseException e) {
	            return false;
	        }
	    }

	    /**
	     * Checks whether the actual date of the given date string is valid based on the given date
	     * format pattern.
	     * @param dateString The date string.
	     * @param dateFormat The date format pattern which should respect the SimpleDateFormat rules.
	     * @return True if the actual date of the given date string is valid based on the given date
	     * format pattern.
	     * @see SimpleDateFormat
	     */
	    public static boolean isValidDate(String dateString, String dateFormat) {
	        try {
	            parse(dateString, dateFormat);
	            return true;
	        } catch (ParseException e) {
	            return false;
	        }
	    }

	    // Checkers -----------------------------------------------------------------------------------

	    /**
	     * Determine SimpleDateFormat pattern matching with the given date string. Returns null if
	     * format is unknown. You can simply extend DateUtil with more formats if needed.
	     * @param dateString The date string to determine the SimpleDateFormat pattern for.
	     * @return The matching SimpleDateFormat pattern, or null if format is unknown.
	     * @see SimpleDateFormat
	     */
	    public static String determineDateFormat(String dateString) {
	        for (String regexp : DATE_FORMAT_REGEXPS.keySet()) {
	            if (dateString.toLowerCase().matches(regexp)) {
	                return DATE_FORMAT_REGEXPS.get(regexp);
	            }
	        }
	        return null; // Unknown format.
	    }

	    // Changers -----------------------------------------------------------------------------------

	    /**
	     * Add the given amount of years to the given date. It actually converts the date to Calendar
	     * and calls {@link CalendarUtil#addYears(Calendar, int)} and then converts back to date.
	     * @param date The date to add the given amount of years to.
	     * @param years The amount of years to be added to the given date. Negative values are also
	     * allowed, it will just go back in time.
	     */
	    public static java.util.Date addYears(Date date, int years) {
	        Calendar calendar = toCalendar(date);
	        CalendarUtil.addYears(calendar, years);
	        return calendar.getTime();
	    }

	    /**
	     * Add the given amount of months to the given date. It actually converts the date to Calendar
	     * and calls {@link CalendarUtil#addMonths(Calendar, int)} and then converts back to date.
	     * @param date The date to add the given amount of months to.
	     * @param months The amount of months to be added to the given date. Negative values are also
	     * allowed, it will just go back in time.
	     */
	    public static java.util.Date addMonths(Date date, int months) {
	        Calendar calendar = toCalendar(date);
	        CalendarUtil.addMonths(calendar, months);
	        return calendar.getTime();
	    }
	    
	    public static java.util.Date addMonths(java.util.Date date, int months) {
	        Calendar calendar = toCalendar(date);
	        CalendarUtil.addMonths(calendar, months);
	        return calendar.getTime();
	    }

	    /**
	     * Add the given amount of days to the given date. It actually converts the date to Calendar and
	     * calls {@link CalendarUtil#addDays(Calendar, int)} and then converts back to date.
	     * @param date The date to add the given amount of days to.
	     * @param days The amount of days to be added to the given date. Negative values are also
	     * allowed, it will just go back in time.
	     */
	    public static java.util.Date addDays(Date date, int days) {
	        Calendar calendar = toCalendar(date);
	        CalendarUtil.addDays(calendar, days);
	        return calendar.getTime();
	    }

	    /**
	     * Add the given amount of hours to the given date. It actually converts the date to Calendar
	     * and calls {@link CalendarUtil#addHours(Calendar, int)} and then converts back to date.
	     * @param date The date to add the given amount of hours to.
	     * @param hours The amount of hours to be added to the given date. Negative values are also
	     * allowed, it will just go back in time.
	     */
	    public static java.util.Date addHours(Date date, int hours) {
	        Calendar calendar = toCalendar(date);
	        CalendarUtil.addHours(calendar, hours);
	        return calendar.getTime();
	    }

	    /**
	     * Add the given amount of minutes to the given date. It actually converts the date to Calendar
	     * and calls {@link CalendarUtil#addMinutes(Calendar, int)} and then converts back to date.
	     * @param date The date to add the given amount of minutes to.
	     * @param minutes The amount of minutes to be added to the given date. Negative values are also
	     * allowed, it will just go back in time.
	     */
	    public static java.util.Date addMinutes(Date date, int minutes) {
	        Calendar calendar = toCalendar(date);
	        CalendarUtil.addMinutes(calendar, minutes);
	        return calendar.getTime();
	    }

	    /**
	     * Add the given amount of seconds to the given date. It actually converts the date to Calendar
	     * and calls {@link CalendarUtil#addSeconds(Calendar, int)} and then converts back to date.
	     * @param date The date to add the given amount of seconds to.
	     * @param seconds The amount of seconds to be added to the given date. Negative values are also
	     * allowed, it will just go back in time.
	     */
	    public static java.util.Date addSeconds(Date date, int seconds) {
	        Calendar calendar = toCalendar(date);
	        CalendarUtil.addSeconds(calendar, seconds);
	        return calendar.getTime();
	    }

	    /**
	     * Add the given amount of millis to the given date. It actually converts the date to Calendar
	     * and calls {@link CalendarUtil#addMillis(Calendar, int)} and then converts back to date.
	     * @param date The date to add the given amount of millis to.
	     * @param millis The amount of millis to be added to the given date. Negative values are also
	     * allowed, it will just go back in time.
	     */
	    public static java.util.Date addMillis(Date date, int millis) {
	        Calendar calendar = toCalendar(date);
	        CalendarUtil.addMillis(calendar, millis);
	        return calendar.getTime();
	    }

	    // Comparators --------------------------------------------------------------------------------

	    /**
	     * Returns <tt>true</tt> if the two given dates are dated on the same year. It actually
	     * converts the both dates to Calendar and calls
	     * {@link CalendarUtil#sameYear(Calendar, Calendar)}.
	     * @param one The one date.
	     * @param two The other date.
	     * @return True if the two given dates are dated on the same year.
	     * @see CalendarUtil#sameYear(Calendar, Calendar)
	     */
	    public static boolean sameYear(Date one, Date two) {
	        return CalendarUtil.sameYear(toCalendar(one), toCalendar(two));
	    }

	    /**
	     * Returns <tt>true</tt> if the two given dates are dated on the same year and month. It
	     * actually converts the both dates to Calendar and calls
	     * {@link CalendarUtil#sameMonth(Calendar, Calendar)}.
	     * @param one The one date.
	     * @param two The other date.
	     * @return True if the two given dates are dated on the same year and month.
	     * @see CalendarUtil#sameMonth(Calendar, Calendar)
	     */
	    public static boolean sameMonth(Date one, Date two) {
	        return CalendarUtil.sameMonth(toCalendar(one), toCalendar(two));
	    }

	    /**
	     * Returns <tt>true</tt> if the two given dates are dated on the same year, month and day. It
	     * actually converts the both dates to Calendar and calls
	     * {@link CalendarUtil#sameDay(Calendar, Calendar)}.
	     * @param one The one date.
	     * @param two The other date.
	     * @return True if the two given dates are dated on the same year, month and day.
	     * @see CalendarUtil#sameDay(Calendar, Calendar)
	     */
	    public static boolean sameDay(Date one, Date two) {
	        return CalendarUtil.sameDay(toCalendar(one), toCalendar(two));
	    }

	    /**
	     * Returns <tt>true</tt> if the two given dates are dated on the same year, month, day and
	     * hour. It actually converts the both dates to Calendar and calls
	     * {@link CalendarUtil#sameHour(Calendar, Calendar)}.
	     * @param one The one date.
	     * @param two The other date.
	     * @return True if the two given dates are dated on the same year, month, day and hour.
	     * @see CalendarUtil#sameHour(Calendar, Calendar)
	     */
	    public static boolean sameHour(Date one, Date two) {
	        return CalendarUtil.sameHour(toCalendar(one), toCalendar(two));
	    }

	    /**
	     * Returns <tt>true</tt> if the two given dates are dated on the same year, month, day, hour
	     * and minute. It actually converts the both dates to Calendar and calls
	     * {@link CalendarUtil#sameMinute(Calendar, Calendar)}.
	     * @param one The one date.
	     * @param two The other date.
	     * @return True if the two given dates are dated on the same year, month, day, hour and minute.
	     * @see CalendarUtil#sameMinute(Calendar, Calendar)
	     */
	    public static boolean sameMinute(Date one, Date two) {
	        return CalendarUtil.sameMinute(toCalendar(one), toCalendar(two));
	    }

	    /**
	     * Returns <tt>true</tt> if the two given dates are dated on the same year, month, day, hour,
	     * minute and second. It actually converts the both dates to Calendar and calls
	     * {@link CalendarUtil#sameSecond(Calendar, Calendar)}.
	     * @param one The one date.
	     * @param two The other date.
	     * @return True if the two given dates are dated on the same year, month, day, hour, minute and
	     * second.
	     * @see CalendarUtil#sameSecond(Calendar, Calendar)
	     */
	    public static boolean sameSecond(Date one, Date two) {
	        return CalendarUtil.sameSecond(toCalendar(one), toCalendar(two));
	    }

	    // Calculators --------------------------------------------------------------------------------

	    /**
	     * Retrieve the amount of elapsed years between the two given dates. It actually converts the
	     * both dates to Calendar and calls {@link CalendarUtil#elapsedYears(Calendar, Calendar)}.
	     * @param before The first date with expected date before the second date.
	     * @param after The second date with expected date after the first date.
	     * @return The amount of elapsed years between the two given dates
	     * @throws IllegalArgumentException If the first date is dated after the second date.
	     * @see CalendarUtil#elapsedYears(Calendar, Calendar)
	     */
	    public static int elapsedYears(Date before, Date after) {
	        return CalendarUtil.elapsedYears(toCalendar(before), toCalendar(after));
	    }

	    /**
	     * Retrieve the amount of elapsed months between the two given dates. It actually converts the
	     * both dates to Calendar and calls {@link CalendarUtil#elapsedMonths(Calendar, Calendar)}.
	     * @param before The first date with expected date before the second date.
	     * @param after The second date with expected date after the first date.
	     * @return The amount of elapsed months between the two given dates.
	     * @throws IllegalArgumentException If the first date is dated after the second date.
	     * @see CalendarUtil#elapsedMonths(Calendar, Calendar)
	     */
	    public static int elapsedMonths(Date before, Date after) {
	        return CalendarUtil.elapsedMonths(toCalendar(before), toCalendar(after));
	    }

	    /**
	     * Retrieve the amount of elapsed days between the two given dates. It actually converts the
	     * both dates to Calendar and calls {@link CalendarUtil#elapsedDays(Calendar, Calendar)}.
	     * @param before The first date with expected date before the second date.
	     * @param after The second date with expected date after the first date.
	     * @return The amount of elapsed days between the two given dates.
	     * @throws IllegalArgumentException If the first date is dated after the second date.
	     * @see CalendarUtil#elapsedDays(Calendar, Calendar)
	     */
	    public static int elapsedDays(Date before, Date after) {
	        return CalendarUtil.elapsedDays(toCalendar(before), toCalendar(after));
	    }

	    /**
	     * Retrieve the amount of elapsed hours between the two given dates. It actually converts the
	     * both dates to Calendar and calls {@link CalendarUtil#elapsedHours(Calendar, Calendar)}.
	     * @param before The first date with expected date before the second date.
	     * @param after The second date with expected date after the first date.
	     * @return The amount of elapsed hours between the two given dates.
	     * @throws IllegalArgumentException If the first date is dated after the second date.
	     * @see CalendarUtil#elapsedHours(Calendar, Calendar)
	     */
	    public static int elapsedHours(Date before, Date after) {
	        return CalendarUtil.elapsedHours(toCalendar(before), toCalendar(after));
	    }

	    /**
	     * Retrieve the amount of elapsed minutes between the two given dates. It actually converts the
	     * both dates to Calendar and calls {@link CalendarUtil#elapsedMinutes(Calendar, Calendar)}.
	     * @param before The first date with expected date before the second date.
	     * @param after The second date with expected date after the first date.
	     * @return The amount of elapsed minutes between the two given dates.
	     * @throws IllegalArgumentException If the first date is dated after the second date.
	     * @see CalendarUtil#elapsedMinutes(Calendar, Calendar)
	     */
	    public static int elapsedMinutes(Date before, Date after) {
	        return CalendarUtil.elapsedMinutes(toCalendar(before), toCalendar(after));
	    }

	    /**
	     * Retrieve the amount of elapsed seconds between the two given dates. It actually converts the
	     * both dates to Calendar and calls {@link CalendarUtil#elapsedSeconds(Calendar, Calendar)}.
	     * @param before The first date with expected date before the second date.
	     * @param after The second date with expected date after the first date.
	     * @return The amount of elapsed seconds between the two given dates.
	     * @throws IllegalArgumentException If the first date is dated after the second date.
	     * @see CalendarUtil#elapsedSeconds(Calendar, Calendar)
	     */
	    public static int elapsedSeconds(Date before, Date after) {
	        return CalendarUtil.elapsedSeconds(toCalendar(before), toCalendar(after));
	    }

	    /**
	     * Retrieve the amount of elapsed milliseconds between the two given dates. It actually converts
	     * the both dates to Calendar and calls {@link CalendarUtil#elapsedMillis(Calendar, Calendar)}.
	     * @param before The first date with expected date before the second date.
	     * @param after The second date with expected date after the first date.
	     * @return The amount of elapsed milliseconds between the two given dates.
	     * @throws IllegalArgumentException If the first date is dated after the second date.
	     * @see CalendarUtil#elapsedMillis(Calendar, Calendar)
	     */
	    public static long elapsedMillis(Date before, Date after) {
	        return CalendarUtil.elapsedMillis(toCalendar(before), toCalendar(after));
	    }

	    /**
	     * Calculate the total of elapsed time from years up to seconds between the two given dates. It
	     * Returns an int array with the elapsed years, months, days, hours, minutes and seconds
	     * respectively. It actually converts the both dates to Calendar and calls
	     * {@link CalendarUtil#elapsedTime(Calendar, Calendar)}.
	     * @param before The first date with expected date before the second date.
	     * @param after The second date with expected date after the first date.
	     * @return The elapsed time between the two given dates in years, months, days, hours, minutes
	     * and seconds.
	     * @throws IllegalArgumentException If the first date is dated after the second date.
	     * @see CalendarUtil#elapsedTime(Calendar, Calendar)
	     */
	    public static int[] elapsedTime(Date before, Date after) {
	        return CalendarUtil.elapsedTime(toCalendar(before), toCalendar(after));
	    }
	    public static String getDifferenceHours(String date1, String date2) {

	    	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:mm:ss");  

	    	java.util.Date d1 = null;
	    	java.util.Date d2 = null;
	    	try {
	    		d1 =  format.parse(date1);
	    		d2 = format.parse(date2);
	    	} catch (ParseException e) {
	    		e.printStackTrace();
	    	}    

	    	long diff = d2.getTime() - d1.getTime();
	    	long diffHours = diff / (60 * 60 * 1000);                      
	    	long diffMinutes = diff / (60 * 1000)% 60;;         

	    	return diffHours +" HH "+ diffMinutes +" MM " ; 
	    }
		
		public static java.util.Date getDateAfterNumberOfMonths(java.util.Date date, int noOfMonths) throws Exception {
			
			Calendar c = Calendar.getInstance();
			c.setTime(date);   
			c.add(Calendar.MONTH, +noOfMonths);
			
			return  c.getTime();
		}
		
		public static boolean checkDateAfterDate(Timestamp comareDate, Timestamp compareWithDate) throws Exception {
			boolean			isDuplicate	 = false;

			try {

				if((comareDate.after(compareWithDate))){
					isDuplicate = true;
				}
				return isDuplicate;

			} catch (Exception e) {
				throw e;
			} finally {
			}
		}
		
		public static LocalDate convertToLocalDate(java.util.Date date) {
			LocalDate localDate= null;
			try {
				localDate =date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return localDate;
		}
		
		
		public static Timestamp convertTimestamp(Timestamp timestamp, String sourceTimezone, String targetTimezone) {
		    LocalDateTime localDateTime = timestamp.toLocalDateTime();

		    // Get the source timezone
		    ZoneId sourceZone = ZoneId.of(sourceTimezone);

		    // Convert the LocalDateTime to the source timezone
		    LocalDateTime sourceDateTime = localDateTime.atZone(sourceZone).toLocalDateTime();

		    // Get the target timezone
		    ZoneId targetZone = ZoneId.of(targetTimezone);

		    // Convert the LocalDateTime to the target timezone
		    LocalDateTime targetDateTime = sourceDateTime.atZone(sourceZone).withZoneSameInstant(targetZone).toLocalDateTime();

		    // Convert the LocalDateTime to a Timestamp and return it
		    return Timestamp.valueOf(targetDateTime);
		}
 
}