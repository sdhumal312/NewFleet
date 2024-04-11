package org.fleetopgroup.constant;

import java.util.ArrayList;
import java.util.List;

public class TripRouteFixedType {

	public static final short  TRIP_ROUTE_FIXED_TYPE_FIXED		= 1;
	public static final short  TRIP_ROUTE_FIXED_TYPE_NEW		= 2;
	public static final short  TRIP_ROUTE_FIXED_TYPE_LHPV		= 3;
	public static final short  TRIP_ROUTE_FIXED_TYPE_FUEL		= 4;
	public static final short  TRIP_ROUTE_FIXED_TYPE_SE			= 5;
	public static final short  TRIP_ROUTE_FIXED_TYPE_RENEWAL	= 6;
	
	public static final String TRIP_ROUTE_FIXED_TYPE_FIXED_NAME	 	= "FIXED";
	public static final String TRIP_ROUTE_FIXED_TYPE_NEW_NAME 		= "NEW";
	public static final String TRIP_ROUTE_FIXED_TYPE_LHPV_NAME 		= "LHPV";
	public static final String TRIP_ROUTE_FIXED_TYPE_FUEL_NAME 		= "Fuel";
	public static final String TRIP_ROUTE_FIXED_TYPE_SE_NAME 		= "Service Entry";
	public static final String TRIP_ROUTE_FIXED_TYPE_RENEWAL_NAME 	= "Renewal";
	
	
	public static String getFixedTypeName(short status) throws Exception{
		String statusString		=	null;
		switch (status) {
		  case TRIP_ROUTE_FIXED_TYPE_FIXED:
			  statusString	= TRIP_ROUTE_FIXED_TYPE_FIXED_NAME;
		        break;
		  case TRIP_ROUTE_FIXED_TYPE_NEW: 
			  statusString	= TRIP_ROUTE_FIXED_TYPE_NEW_NAME;
		        break;
		  case TRIP_ROUTE_FIXED_TYPE_LHPV: 
			  statusString	= TRIP_ROUTE_FIXED_TYPE_LHPV_NAME;
		        break;
		  case TRIP_ROUTE_FIXED_TYPE_FUEL: 
			  statusString	= TRIP_ROUTE_FIXED_TYPE_FUEL_NAME;
		        break;
		  case TRIP_ROUTE_FIXED_TYPE_SE: 
			  statusString	= TRIP_ROUTE_FIXED_TYPE_SE_NAME;
		        break;
		  case TRIP_ROUTE_FIXED_TYPE_RENEWAL: 
			  statusString	= TRIP_ROUTE_FIXED_TYPE_RENEWAL_NAME;
		        break;
		  
		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
	
	public static boolean isListToAvoidDelete(short id) throws Exception{
		try {
			List<Short>    avoidList	= new ArrayList<Short>();
				avoidList.add(TRIP_ROUTE_FIXED_TYPE_FUEL);
			if(avoidList.contains(id)) {
				return true;
			}
			
			return false;
		} catch (Exception e) {
			throw e;
		}
	}
}
