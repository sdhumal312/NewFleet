package org.fleetopgroup.constant;

import java.util.ArrayList;

public class MasterDocumentsConstant {
	
	public static final short MASTER_DOCUMENT_VENDOR					= 1;
	public static final short MASTER_DOCUMENT_INVENTORY_TYRE			= 2;
	public static final short MASTER_DOCUMENT_INVENTORY_BATTERY			= 3;
	
	public static final String MASTER_DOCUMENT_VENDOR_NAME			   		= "VENDOR";
	public static final String MASTER_DOCUMENT_INVENTORY_TYRE_NAME			= "INVENTORY TYRE";
	public static final String MASTER_DOCUMENT_INVENTORY_BATTERY_NAME		= "INVENTORY BATTERY";
	
	private short 	masterDocumentId;
	private String 	masterDocumentName;
	
	public short getMasterDocumentId() {
		return masterDocumentId;
	}
	public void setMasterDocumentId(short masterDocumentId) {
		this.masterDocumentId = masterDocumentId;
	}
	public String getMasterDocumentName() {
		return masterDocumentName;
	}
	public void setMasterDocumentName(String masterDocumentName) {
		this.masterDocumentName = masterDocumentName;
	}
	
	public MasterDocumentsConstant(short masterDocumentId, String masterDocumentName) {
		super();
		this.masterDocumentId = masterDocumentId;
		this.masterDocumentName = masterDocumentName;
	}
	
	public static ArrayList<MasterDocumentsConstant> getMaterDocumentList(){
		ArrayList<MasterDocumentsConstant>		list	= new ArrayList<>();
		list.add(new MasterDocumentsConstant(MASTER_DOCUMENT_VENDOR, MASTER_DOCUMENT_VENDOR_NAME));
		list.add(new MasterDocumentsConstant(MASTER_DOCUMENT_INVENTORY_TYRE, MASTER_DOCUMENT_INVENTORY_TYRE_NAME));		
		return list;
	}
}
