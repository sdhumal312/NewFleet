package org.fleetopgroup.constant;

import java.util.ArrayList;

public class VehicleAcType {
	
	private short 	acTypeId;
	private String	acTypeName;
	
	
	public VehicleAcType() {
		super();
	}

	
	
	public VehicleAcType(short acTypeId, String acTypeName) {
		super();
		this.acTypeId = acTypeId;
		this.acTypeName = acTypeName;
	}


	public static ArrayList<VehicleAcType> getVehicleAcTypeList(){
		ArrayList<VehicleAcType>		list	= new ArrayList<>();
		list.add(new VehicleAcType(AC_TYPE_AC, AC_TYPE_AC_NAME));
		list.add(new VehicleAcType(AC_TYPE_NON_AC, AC_TYPE_NON_AC_NAME));
		list.add(new VehicleAcType(AC_TYPE_AC_AND_NON_AC, AC_TYPE_AC_AND_NON_AC_NAME));
		
		return list;
	}
	

	public short getAcTypeId() {
		return acTypeId;
	}


	public void setAcTypeId(short acTypeId) {
		this.acTypeId = acTypeId;
	}


	public String getAcTypeName() {
		return acTypeName;
	}


	public void setAcTypeName(String acTypeName) {
		this.acTypeName = acTypeName;
	}


	public static final short AC_TYPE_AC			= 1;
	public static final short AC_TYPE_NON_AC		= 2;
	public static final short AC_TYPE_AC_AND_NON_AC	= 3;
	
	public static final String AC_TYPE_AC_NAME				= "A/C";
	public static final String AC_TYPE_NON_AC_NAME			= "Non A/C";
	public static final String AC_TYPE_AC_AND_NON_AC_NAME	= "A/C & Non A/C";
	
	
	public static String getVehicleAcTypeName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case AC_TYPE_AC:
			  paymentTypeName	= AC_TYPE_AC_NAME;
		        break;
		  case AC_TYPE_NON_AC: 
			  paymentTypeName	= AC_TYPE_NON_AC_NAME;
		        break;
		  case AC_TYPE_AC_AND_NON_AC: 
			  paymentTypeName	= AC_TYPE_AC_AND_NON_AC_NAME;
		        break;
		
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
