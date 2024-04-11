package org.fleetopgroup.constant;

public class VehicleGPSVendorConstant {

	public static final Integer	GPS_VENDOR_OMNI_TALK			= 1;
	public static final Integer	GPS_VENDOR_INTANGLES			= 2;
	public static final Integer	GPS_VENDOR_IDEAGAMI				= 3;
	public static final Integer	GPS_VENDOR_SMTC					= 4;
	public static final Integer	GPS_VENDOR_ITS_GATEWAY			= 5;
	public static final Integer	GPS_VENDOR_TRACK_NOW			= 6;
	public static final Integer	GPS_VENDOR_FLEETEX				= 7;
	public static final Integer	GPS_VENDOR_TATA					= 8;
	public static final Integer GPS_VENDOR_EICHER				= 9;
	
	public static final String	GPS_VENDOR_OMNI_TALK_NAME 		= "OMNI TALK";
	public static final String	GPS_VENDOR_INTANGLES_NAME 		= "Intangles";
	public static final String	GPS_VENDOR_IDEAGAMI_NAME 		= "Ideagami";
	public static final String	GPS_VENDOR_SMTC_NAME 			= "SMTC";
	public static final String	GPS_VENDOR_ITS_GATEWAY_NAME 	= "ITSGateway";
	public static final String	GPS_VENDOR_TRACK_NOW_NAME 		= "Track Now";
	public static final String	GPS_VENDOR_FLEETEX_NAME 		= "Fleetx";
	public static final String	GPS_VENDOR_TATA_NAME 			= "TATA";
	public static final String  GPS_VENDOR_EICHER_NAME			= "Eicher";
	
	public static String getGPSVendorName(Integer gpsVendorId) {

		String paymentTypeName		=	"--";
		if(gpsVendorId == GPS_VENDOR_OMNI_TALK) {
			paymentTypeName = GPS_VENDOR_OMNI_TALK_NAME;
			
		}else if(gpsVendorId == GPS_VENDOR_INTANGLES) {
			paymentTypeName = GPS_VENDOR_INTANGLES_NAME;
			
		}else if(gpsVendorId == GPS_VENDOR_IDEAGAMI) {
			paymentTypeName = GPS_VENDOR_IDEAGAMI_NAME;
			
		}else if(gpsVendorId == GPS_VENDOR_SMTC) {
			paymentTypeName = GPS_VENDOR_SMTC_NAME;
			
		}else if(gpsVendorId == GPS_VENDOR_ITS_GATEWAY) {
			paymentTypeName = GPS_VENDOR_ITS_GATEWAY_NAME;
			
		}else if(gpsVendorId == GPS_VENDOR_TRACK_NOW) {
			paymentTypeName = GPS_VENDOR_TRACK_NOW_NAME;
		}else if(gpsVendorId == GPS_VENDOR_FLEETEX) {
			paymentTypeName = GPS_VENDOR_FLEETEX_NAME;
		}else if(gpsVendorId == GPS_VENDOR_TATA) {
			paymentTypeName = GPS_VENDOR_TATA_NAME;
		}else if(gpsVendorId == GPS_VENDOR_EICHER) {
			paymentTypeName = GPS_VENDOR_EICHER_NAME;
		}
		else {
			paymentTypeName = "--";
		}
		return paymentTypeName;
	}
	
}
