package org.fleetopgroup.constant;

import java.io.Serializable;
import java.util.ArrayList;

public class PartType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final short	PART_TYPE_QUANTITY	= 1;
	public static final short	PART_TYPE_LITER		= 2;
	
	public static final String	PART_TYPE_QUANTITY_NAME	= "QUANTITY";
	public static final String	PART_TYPE_LITER_NAME	= "LITER";
	
	public static final short PART_MANUFACTURER_TYPE_ORIGINAL	= 1;
	public static final short PART_MANUFACTURER_TYPE_LOCAL		= 2;
	
	public static final String PART_MANUFACTURER_TYPE_ORIGINAL_NAME	= "Original Parts";
	public static final String PART_MANUFACTURER_TYPE_LOCAL_NAME	= "Local Parts";
	
	public static final short STANDARD_MAKE		= 1;
	public static final short CUSTOM_MAKE		= 2;
	
	public static final String	STANDARD_MAKE_NAME	= "Standard Make";
	public static final String	CUSTOM_MAKE_NAME	= "Custom Make";
	
	public static final short	PART_TYPE_CATEGORY_STANDARD		= 1;
	public static final short	PART_TYPE_CATEGORY_PARENT		= 2;
	public static final short	PART_TYPE_CATEGORY_CHILD		= 3;
	
	public static final String	PART_TYPE_CATEGORY_STANDARD_NAME 	= "Standard";
	public static final String	PART_TYPE_CATEGORY_PARENT_NAME 		= "Parent";
	public static final String	PART_TYPE_CATEGORY_CHILD_NAME 		= "Child";
	
	
	private short 	partTypeId;
	private String	partTypeName;
	
	
	public short getPartTypeId() {
		return partTypeId;
	}
	public void setPartTypeId(short partTypeId) {
		this.partTypeId = partTypeId;
	}
	public String getPartTypeName() {
		return partTypeName;
	}
	public void setPartTypeName(String partTypeName) {
		this.partTypeName = partTypeName;
	}
	
	
	public PartType() {
		super();
	}
	
	public PartType(short partTypeId, String partTypeName ) {
		this.partTypeId	= partTypeId;
		this.partTypeName = partTypeName;
	}
	
	
	
	public static ArrayList<PartType> getPartTypeList(){
		ArrayList<PartType>		list	= new ArrayList<>();
		list.add(new PartType(PART_TYPE_QUANTITY, PART_TYPE_QUANTITY_NAME));
		list.add(new PartType(PART_TYPE_LITER, PART_TYPE_LITER_NAME));
		
		return list;
	}
	
	public static String getPartTypeName(short partTypeId) {

		String partTypeName		=	null;
		switch (partTypeId) {
		  case PART_TYPE_QUANTITY:
			  partTypeName	= PART_TYPE_QUANTITY_NAME;
		        break;
		  case PART_TYPE_LITER: 
			  partTypeName	= PART_TYPE_LITER_NAME;
		        break;
		
		  default:
			  partTypeName = "--";
		        break;
		}
		return partTypeName;
	
	}
	

	public static String getPartManufacturerTypeName(short partTypeId) {

		String partTypeName		=	null;
		switch (partTypeId) {
		  case PART_MANUFACTURER_TYPE_ORIGINAL:
			  partTypeName	= PART_MANUFACTURER_TYPE_ORIGINAL_NAME;
		        break;
		  case PART_MANUFACTURER_TYPE_LOCAL: 
			  partTypeName	= PART_MANUFACTURER_TYPE_LOCAL_NAME;
		        break;
		
		  default:
			  partTypeName = "--";
		        break;
		}
		return partTypeName;
	
	}
	
	
	public static String getPartItemType(short partTypeId) {

		String partTypeName		=	null;
		switch (partTypeId) {
		  case STANDARD_MAKE:
			  partTypeName	= STANDARD_MAKE_NAME;
		        break;
		  case CUSTOM_MAKE: 
			  partTypeName	= CUSTOM_MAKE_NAME;
		        break;
		
		  default:
			  partTypeName = "--";
		        break;
		}
		return partTypeName;
	
	}
	
	public static String getPartCategoryType(short partTypeId) {

		String partTypeName		=	null;
		switch (partTypeId) {
		  case PART_TYPE_CATEGORY_STANDARD:
			  partTypeName	= PART_TYPE_CATEGORY_STANDARD_NAME;
		        break;
		  case PART_TYPE_CATEGORY_PARENT: 
			  partTypeName	= PART_TYPE_CATEGORY_PARENT_NAME;
		        break;
		  case PART_TYPE_CATEGORY_CHILD: 
			  partTypeName	= PART_TYPE_CATEGORY_CHILD_NAME;
		        break;
		
		  default:
			  partTypeName = "--";
		        break;
		}
		return partTypeName;
	
	}
}
