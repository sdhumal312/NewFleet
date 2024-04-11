package org.fleetopgroup.constant;

import java.util.ArrayList;

public class VehicleMeterUnit {
	

	public static final short	METER_UNIT_KM		= 1;
	public static final short	METER_UNIT_MILES	= 2;
	public static final short	METER_UNIT_HOURS	= 3;
	
	public static final String METER_UNIT_KM_NAME		= "km";
	public static final String METER_UNIT_MILES_NAME	= "Miles";
	public static final String METER_UNIT_HOURS_NAME	= "Hours";
	
	
	
	private short 	vehicleMeterUnitId;
	private String	vehicleMeterUnit;
	
	public VehicleMeterUnit() {
		super();
	}
	
	
	
	public VehicleMeterUnit(short vehicleMeterUnitId, String vehicleMeterUnit) {
		super();
		this.vehicleMeterUnitId = vehicleMeterUnitId;
		this.vehicleMeterUnit = vehicleMeterUnit;
	}

	
	public static ArrayList<VehicleMeterUnit> getVehicleMeterUnitList(){
		ArrayList<VehicleMeterUnit>		list	= new ArrayList<>();
		list.add(new VehicleMeterUnit(METER_UNIT_KM, METER_UNIT_KM_NAME));
		list.add(new VehicleMeterUnit(METER_UNIT_MILES, METER_UNIT_MILES_NAME));
		list.add(new VehicleMeterUnit(METER_UNIT_HOURS, METER_UNIT_HOURS_NAME));
		
		return list;
	}


	public short getVehicleMeterUnitId() {
		return vehicleMeterUnitId;
	}




	public void setVehicleMeterUnitId(short vehicleMeterUnitId) {
		this.vehicleMeterUnitId = vehicleMeterUnitId;
	}




	public String getVehicleMeterUnit() {
		return vehicleMeterUnit;
	}




	public void setVehicleMeterUnit(String vehicleMeterUnit) {
		this.vehicleMeterUnit = vehicleMeterUnit;
	}




	public static String getVehicleMeterUnitName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case METER_UNIT_KM:
			  paymentTypeName	= METER_UNIT_KM_NAME;
		        break;
		  case METER_UNIT_MILES: 
			  paymentTypeName	= METER_UNIT_MILES_NAME;
		        break;
		  case METER_UNIT_HOURS: 
			  paymentTypeName	= METER_UNIT_HOURS_NAME;
		        break;
		
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
