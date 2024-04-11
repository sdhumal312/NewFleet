/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.text.SimpleDateFormat;

/**
 * @author fleetop
 *
 */
public class MainActivity {

	// This Time Create Format Value
	SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("dd-MM-yyyy HH:ss:mm");
	SimpleDateFormat DATE_FORMAT_NAME = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat CREATED_DATE_FORMAT_TIME = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	// This Class All MainACtivty Value story

	public static String VEHICLE_MAIN_STATUS = "ACTIVE";
	public static Integer VEHICLE_MAIN_STATUS_PAGE = 1;

	// WorkOrder Default value
	public static final long TRIP_SHEET_DEFALAT_VALUE = 0;
	public static final long WORK_ORDER_DEFALAT_VALUE = 0;
	public static final Integer WORK_ORDER_DEFALAT_ODAMETER = 0;
	public static final Double WORK_ORDER_DEFALAT_AMOUNT = 0.0;

	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

}
