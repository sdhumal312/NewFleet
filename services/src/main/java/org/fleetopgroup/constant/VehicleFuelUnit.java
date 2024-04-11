package org.fleetopgroup.constant;

import java.util.ArrayList;

public class VehicleFuelUnit {
	
	public static final short FUEL_UNIT_LITERS		= 1;
	public static final short FUEL_UNIT_GALLONS		= 2;
	
	public static final String FUEL_UNIT_LITERS_NAME		= "liters";
	public static final String FUEL_UNIT_GALLONS_NAME		= "gallons";
	
	
	
	private short  vehicleFuelUnitId;
	private String vehicleFuelUnit;

	
	public VehicleFuelUnit() {
		super();
	}
	
	
	
	public VehicleFuelUnit(short vehicleFuelUnitId, String vehicleFuelUnit) {
		super();
		this.vehicleFuelUnitId = vehicleFuelUnitId;
		this.vehicleFuelUnit = vehicleFuelUnit;
	}


	
	public static ArrayList<VehicleFuelUnit> getVehicleFuelUnitList(){
		ArrayList<VehicleFuelUnit>		list	= new ArrayList<>();
		list.add(new VehicleFuelUnit(FUEL_UNIT_LITERS, FUEL_UNIT_LITERS_NAME));
		list.add(new VehicleFuelUnit(FUEL_UNIT_GALLONS, FUEL_UNIT_GALLONS_NAME));
		
		return list;
	}
	

	public short getVehicleFuelUnitId() {
		return vehicleFuelUnitId;
	}



	public void setVehicleFuelUnitId(short vehicleFuelUnitId) {
		this.vehicleFuelUnitId = vehicleFuelUnitId;
	}



	public String getVehicleFuelUnit() {
		return vehicleFuelUnit;
	}



	public void setVehicleFuelUnit(String vehicleFuelUnit) {
		this.vehicleFuelUnit = vehicleFuelUnit;
	}



	public static String getVehicleFuelUnitName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case FUEL_UNIT_LITERS:
			  paymentTypeName	= FUEL_UNIT_LITERS_NAME;
		        break;
		  case FUEL_UNIT_GALLONS: 
			  paymentTypeName	= FUEL_UNIT_GALLONS_NAME;
		        break;
		
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
