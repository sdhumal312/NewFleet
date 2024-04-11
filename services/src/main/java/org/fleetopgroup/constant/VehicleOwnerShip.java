package org.fleetopgroup.constant;

import java.util.ArrayList;

public class VehicleOwnerShip {
	
	private short	ownerShipId;
	private String  name;
	
	
	public VehicleOwnerShip() {
		super();
	}

	
	public VehicleOwnerShip(short ownerShipId, String name) {
		super();
		this.ownerShipId = ownerShipId;
		this.name = name;
	}

	public static ArrayList<VehicleOwnerShip> getVehicleOwerShipList(){
		ArrayList<VehicleOwnerShip>		list	= new ArrayList<>();
		list.add(new VehicleOwnerShip(OWNER_SHIP_TYPE_OWNED, OWNER_SHIP_TYPE_OWNED_NAME));
		list.add(new VehicleOwnerShip(OWNER_SHIP_TYPE_LEASED, OWNER_SHIP_TYPE_LEASED_NAME));
		list.add(new VehicleOwnerShip(OWNER_SHIP_TYPE_RENTED, OWNER_SHIP_TYPE_RENTED_NAME));
		list.add(new VehicleOwnerShip(OWNER_SHIP_TYPE_ATTACHED, OWNER_SHIP_TYPE_ATTACHED_NAME));
		list.add(new VehicleOwnerShip(OWNER_SHIP_TYPE_CUSTOMER, OWNER_SHIP_TYPE_CUSTOMER_NAME));
		
		return list;
	}

	public short getOwnerShipId() {
		return ownerShipId;
	}


	public void setOwnerShipId(short ownerShipId) {
		this.ownerShipId = ownerShipId;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public static final short OWNER_SHIP_TYPE_OWNED			= 1;
	public static final short OWNER_SHIP_TYPE_LEASED		= 2;
	public static final short OWNER_SHIP_TYPE_RENTED		= 3;
	public static final short OWNER_SHIP_TYPE_ATTACHED		= 4;
	public static final short OWNER_SHIP_TYPE_CUSTOMER		= 5;
	
	public static final String OWNER_SHIP_TYPE_OWNED_NAME			= "Owned";
	public static final String OWNER_SHIP_TYPE_LEASED_NAME			= "Leased";
	public static final String OWNER_SHIP_TYPE_RENTED_NAME			= "Rented";
	public static final String OWNER_SHIP_TYPE_ATTACHED_NAME		= "Attached";
	public static final String OWNER_SHIP_TYPE_CUSTOMER_NAME		= "Customer";
	
	
	public static String getVehicleMeterUnitName(short paymentType) {

		String paymentTypeName		=	null;
		switch (paymentType) {
		
		  case OWNER_SHIP_TYPE_OWNED:
			  paymentTypeName	= OWNER_SHIP_TYPE_OWNED_NAME;
		        break;
		  case OWNER_SHIP_TYPE_LEASED: 
			  paymentTypeName	= OWNER_SHIP_TYPE_LEASED_NAME;
		        break;
		  case OWNER_SHIP_TYPE_RENTED: 
			  paymentTypeName	= OWNER_SHIP_TYPE_RENTED_NAME;
		        break;
		  case OWNER_SHIP_TYPE_ATTACHED: 
			  paymentTypeName	= OWNER_SHIP_TYPE_ATTACHED_NAME;
		        break;
		  case OWNER_SHIP_TYPE_CUSTOMER: 
			  paymentTypeName	= OWNER_SHIP_TYPE_CUSTOMER_NAME;
		        break;
		
		  default:
			  paymentTypeName = "--";
		        break;
		}
		return paymentTypeName;
	
	}
}
