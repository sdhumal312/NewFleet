package org.fleetopgroup.constant;

import java.util.ArrayList;

public class VehicleFuelType {

	public static final short FUEL_TYPE_DIESEL		= 1;
	public static final short FUEL_TYPE_BIO_DIESEL	= 2;
	public static final short FUEL_TYPE_CNG			= 3;
	public static final short FUEL_TYPE_PETROL		= 4;
	public static final short FUEL_TYPE_TASS		= 5;
	
	
	public static final String FUEL_TYPE_DIESEL_NAME 			= "DIESEL";
	public static final String FUEL_TYPE_PETROL_NAME			= "PETROL";
	public static final String FUEL_TYPE_CNG_NAME 				= "CNG";
	public static final String FUEL_TYPE_BIO_DIESEL_NAME		= "BIO DIESEL";
	public static final String FUEL_TYPE_TASS_NAME				= "TASS";
	
	
	public static final short FUEL_RANGE_BELOW			= 1;
	public static final short FUEL_RANGE_WITHIN			= 2;
	public static final short FUEL_RANGE_OUT_OF_RANGE	= 3;
	
	
	
	private short	vehicleFuelTypeId;
	private String	vehicleType;
	
	public VehicleFuelType() {
		super();
	}
	
	
	public static ArrayList<VehicleFuelType> getVehicleFuelTypeList(){
		ArrayList<VehicleFuelType>		list	= new ArrayList<>();
		list.add(new VehicleFuelType(FUEL_TYPE_DIESEL, FUEL_TYPE_DIESEL_NAME));
		list.add(new VehicleFuelType(FUEL_TYPE_PETROL, FUEL_TYPE_PETROL_NAME));
		list.add(new VehicleFuelType(FUEL_TYPE_CNG, FUEL_TYPE_CNG_NAME));
		list.add(new VehicleFuelType(FUEL_TYPE_BIO_DIESEL, FUEL_TYPE_BIO_DIESEL_NAME));
		list.add(new VehicleFuelType(FUEL_TYPE_TASS, FUEL_TYPE_TASS_NAME));
		
		return list;
	}
	
	
	public VehicleFuelType(short vehicleFuelTypeId, String vehicleType) {
		super();
		this.vehicleFuelTypeId = vehicleFuelTypeId;
		this.vehicleType = vehicleType;
	}




	public short getVehicleFuelTypeId() {
		return vehicleFuelTypeId;
	}





	public void setVehicleFuelTypeId(short vehicleFuelTypeId) {
		this.vehicleFuelTypeId = vehicleFuelTypeId;
	}





	public String getVehicleType() {
		return vehicleType;
	}





	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}





	public static String getVehicleFuelTypeName(String paymentType) {
		
		String [] arr = paymentType.split(",");
		String paymentTypeName		=	"";
		
		for(int i =0 ; i< arr.length ; i++) {
			
			switch (Short.parseShort(arr[i])) {
			
			case FUEL_TYPE_DIESEL:
				paymentTypeName	+= FUEL_TYPE_DIESEL_NAME+",";
				break;
			case FUEL_TYPE_PETROL: 
				paymentTypeName	+= FUEL_TYPE_PETROL_NAME+",";
				break;
			case FUEL_TYPE_CNG: 
				paymentTypeName	+= FUEL_TYPE_CNG_NAME+",";
				break;
				
			case FUEL_TYPE_BIO_DIESEL: 
				paymentTypeName	= FUEL_TYPE_BIO_DIESEL_NAME+",";
				break;
				
				
			default:
				paymentTypeName = "--";
				break;
			}
		}
		
		return paymentTypeName;
	
	}
	
	public static String getVehicleFuelName(short status) throws Exception{
		String statusString		=	null;
		switch (status) {
		  case FUEL_TYPE_DIESEL:
			  statusString	= FUEL_TYPE_DIESEL_NAME;
		        break;
		  case FUEL_TYPE_PETROL: 
			  statusString	= FUEL_TYPE_PETROL_NAME;
		        break;
		  case FUEL_TYPE_CNG: 
			  statusString	= FUEL_TYPE_CNG_NAME;
		        break;
		  case FUEL_TYPE_BIO_DIESEL: 
			  statusString	= FUEL_TYPE_BIO_DIESEL_NAME;
		        break;
		 
		  default:
			  statusString = "--";
		        break;
		}
		return statusString;
	}
}
