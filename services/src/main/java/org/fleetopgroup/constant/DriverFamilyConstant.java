package org.fleetopgroup.constant;

public class DriverFamilyConstant {

	public static final short DRIVER_DF_RELATIONSHIP_FATHER		= 1;
	public static final short DRIVER_DF_RELATIONSHIP_MOTHER		= 2;
	public static final short DRIVER_DF_RELATIONSHIP_SON		= 3;
	public static final short DRIVER_DF_RELATIONSHIP_DAUGHTER	= 4;
	public static final short DRIVER_DF_RELATIONSHIP_BROTHER	= 5;
	public static final short DRIVER_DF_RELATIONSHIP_SISTER		= 6;
	public static final short DRIVER_DF_RELATIONSHIP_HUSBAND	= 7;
	public static final short DRIVER_DF_RELATIONSHIP_WIFE		= 8;
	
	
	public static final String DRIVER_DF_RELATIONSHIP_FATHER_NAME		= "FATHER";
	public static final String DRIVER_DF_RELATIONSHIP_MOTHER_NAME		= "MOTHER";
	public static final String DRIVER_DF_RELATIONSHIP_SON_NAME			= "SON";
	public static final String DRIVER_DF_RELATIONSHIP_DAUGHTER_NAME		= "DAUGHTER";
	public static final String DRIVER_DF_RELATIONSHIP_BROTHER_NAME		= "BROTHER";
	public static final String DRIVER_DF_RELATIONSHIP_SISTER_NAME		= "SISTER";
	public static final String DRIVER_DF_RELATIONSHIP_HUSBAND_NAME		= "HUSBAND";
	public static final String DRIVER_DF_RELATIONSHIP_WIFE_NAME			= "WIFE";
	
	
	public static final short DRIVER_FAMILY_SEX_MALE	= 1;
	public static final short DRIVER_FAMILY_SEX_FEMALE	= 2;
	
	public static final String DRIVER_FAMILY_SEX_MALE_NAME   = "MALE";
	public static final String DRIVER_FAMILY_SEX_FEMALE_NAME = "FEMALE";
	
	public static String getDFRELATIONName(short status) {

		String statusName		=	null;
		switch (status) {
		  case DRIVER_DF_RELATIONSHIP_FATHER:
			  statusName	= DRIVER_DF_RELATIONSHIP_FATHER_NAME;
		        break;
		  case DRIVER_DF_RELATIONSHIP_MOTHER: 
			  statusName	= DRIVER_DF_RELATIONSHIP_MOTHER_NAME;
		        break;
		  case DRIVER_DF_RELATIONSHIP_SON: 
			  statusName	= DRIVER_DF_RELATIONSHIP_SON_NAME;
		        break;
		  case DRIVER_DF_RELATIONSHIP_DAUGHTER: 
			  statusName	= DRIVER_DF_RELATIONSHIP_DAUGHTER_NAME;
		        break;
		  case DRIVER_DF_RELATIONSHIP_BROTHER: 
			  statusName	= DRIVER_DF_RELATIONSHIP_BROTHER_NAME;
		        break;
		  case DRIVER_DF_RELATIONSHIP_SISTER: 
			  statusName	= DRIVER_DF_RELATIONSHIP_SISTER_NAME;
		        break;
		  case DRIVER_DF_RELATIONSHIP_HUSBAND: 
			  statusName	= DRIVER_DF_RELATIONSHIP_HUSBAND_NAME;
		        break;
		  case DRIVER_DF_RELATIONSHIP_WIFE: 
			  statusName	= DRIVER_DF_RELATIONSHIP_WIFE_NAME;
		        break;
		 
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}
	
	public static String getDriverFamilySexName(short status) {

		String statusName		=	null;
		switch (status) {
		  case DRIVER_FAMILY_SEX_MALE:
			  statusName	= DRIVER_FAMILY_SEX_MALE_NAME;
		        break;
		  case DRIVER_FAMILY_SEX_FEMALE: 
			  statusName	= DRIVER_FAMILY_SEX_FEMALE_NAME;
		        break;
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	
	}
}
