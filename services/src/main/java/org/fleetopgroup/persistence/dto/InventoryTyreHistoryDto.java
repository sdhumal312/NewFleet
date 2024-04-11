/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class InventoryTyreHistoryDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	public static final short INVENTORY_TYRE_HISTORY_STATUS_AVAILABLE		= 1;
	public static final short INVENTORY_TYRE_HISTORY_STATUS_IN_SERVICE		= 2;
	public static final short INVENTORY_TYRE_HISTORY_STATUS_SCRAPED			= 3;
	public static final short INVENTORY_TYRE_HISTORY_STATUS_SENT_RETREAD	= 4;
	public static final short INVENTORY_TYRE_HISTORY_STATUS_RECEIVE			= 5;
	public static final short INVENTORY_TYRE_HISTORY_STATUS_MOUNT			= 6;
	public static final short INVENTORY_TYRE_HISTORY_STATUS_DISMOUNT		= 7;
	public static final short INVENTORY_TYRE_HISTORY_STATUS_REJECT			= 8;
	public static final short INVENTORY_TYRE_HISTORY_STATUS_ROTATION		= 9;
	public static final short INVENTORY_TYRE_HISTORY_STATUS_FLIP			= 10;
	
	public static final String INVENTORY_TYRE_HISTORY_STATUS_AVAILABLE_NAME			= "AVAILABLE";
	public static final String INVENTORY_TYRE_HISTORY_STATUS_IN_SERVICE_NAME		= "IN-SERVICE";
	public static final String INVENTORY_TYRE_HISTORY_STATUS_SCRAPED_NAME			= "SCRAPED";
	public static final String INVENTORY_TYRE_HISTORY_STATUS_SENT_RETREAD_NAME		= "SENT-RETREAD";
	public static final String INVENTORY_TYRE_HISTORY_STATUS_RECEIVE_NAME			= "RECEIVE";
	public static final String INVENTORY_TYRE_HISTORY_STATUS_MOUNT_NAME				= "MOUNT";
	public static final String INVENTORY_TYRE_HISTORY_STATUS_DISMOUNT_NAME			= "DISMOUNT";
	public static final String INVENTORY_TYRE_HISTORY_STATUS_REJECT_NAME			= "REJECT";
	public static final String INVENTORY_TYRE_HISTORY_STATUS_ROTATION_NAME			= "ROTATION";
	public static final String INVENTORY_TYRE_HISTORY_STATUS_FLIP_NAME				= "FLIP";
	
	
	public static final short INVENTORY_TYRE_HISTORY_POSITION_LO			= 1;
	public static final short INVENTORY_TYRE_HISTORY_POSITION_LI			= 2;
	public static final short INVENTORY_TYRE_HISTORY_POSITION_RI			= 3;
	public static final short INVENTORY_TYRE_HISTORY_POSITION_RO			= 4;
	public static final short INVENTORY_TYRE_HISTORY_POSITION_ST			= 5;
	
	
	public static final String INVENTORY_TYRE_HISTORY_POSITION_LO_NAME		= "LO";
	public static final String INVENTORY_TYRE_HISTORY_POSITION_LI_NAME		= "LI";
	public static final String INVENTORY_TYRE_HISTORY_POSITION_RI_NAME		= "RI";
	public static final String INVENTORY_TYRE_HISTORY_POSITION_RO_NAME		= "RO";
	public static final String INVENTORY_TYRE_HISTORY_POSITION_ST_NAME		= "ST";

	
	

	/** The value for the ITYRE_ID Layout Id field */
	private Long TYRE_HIS_ID;

	/** The value for the TYRE_ID field */
	private Long TYRE_ID;

	/** The value for the TYRE_NUMBER Number field */
	private String TYRE_NUMBER;

	/** The value for the Vehicle ID field */
	private Integer VEHICLE_ID;

	/** The value for the Vehicle Registration Number field */
	private String VEHICLE_REGISTRATION;

	/** The value for the AXLE field */
	private String AXLE;

	/** The value for the POSITION field */
	private String POSITION;
	

	/** The value for the TYRE_STATUS field */
	private String TYRE_STATUS;
	
	private short TYRE_STATUS_ID;

	/** The value for the OPEN_ODOMETER ID field */
	private Integer OPEN_ODOMETER;

	/** The value for the CLOSE_ODOMETER ID field */
	private Integer CLOSE_ODOMETER;

	/** The value for the TYRE_USEAGE ID field */
	private Integer TYRE_USEAGE;

	/** The value for the TYRE_COST ID field */
	private Double TYRE_COST;

	/** The value for the TYRE_ASSIGN_DATE field */
	private String TYRE_ASSIGN_DATE;
	
	private Timestamp TYRE_ASSIGN_DATE_TIMESTAMP;

	/** The value for the TYRE_COMMENT field */
	private String TYRE_COMMENT;
	
	private String tyreModel;
	
	private Integer companyId;

	private Timestamp PRE_TYRE_ASSIGN_DATE;

	private String PRE_TYRE_ASSIGN_DATEStr;
	
	private String  PRE_TYRE_NUMBER;
	
	private Integer PRE_OPEN_ODOMETER;
	
	private String TYRE_MOUNT_DATE;
	
	private String TYRE_DIS_MOUNT_DATE;
	
	private  Double COST_PER_KM;
	
	private short TYRE_ASSIGN_STATUS_ID;
	
	private Integer VEHICLE_ODOMETER;
	
	private String WAREHOUSE_LOCATION;

	public String getWAREHOUSE_LOCATION() {
		return WAREHOUSE_LOCATION;
	}

	public void setWAREHOUSE_LOCATION(String wAREHOUSE_LOCATION) {
		WAREHOUSE_LOCATION = wAREHOUSE_LOCATION;
	}

	public InventoryTyreHistoryDto() {
		super();
	}

	public InventoryTyreHistoryDto(Long tYRE_HIS_ID, Long tYRE_ID, String tYRE_NUMBER, Integer vEHICLE_ID,
			String vEHICLE_REGISTRATION, String aXLE, String pOSITION, String tYRE_STATUS, Integer oPEN_ODOMETER,
			Integer cLOSE_ODOMETER, Integer tYRE_USEAGE, Double tYRE_COST, String tYRE_ASSIGN_DATE, 
			String tYRE_MOUNT_DATE,String tYRE_DIS_MOUNT_DATE,Double cOST_PER_KM,
			Short tYRE_ASSIGN_STATUS_ID,Integer vEHICLE_ODOMETER,String wAREHOUSE_LOCATION ) {
		super();
		TYRE_HIS_ID = tYRE_HIS_ID;
		TYRE_ID = tYRE_ID;
		TYRE_NUMBER = tYRE_NUMBER;
		VEHICLE_ID = vEHICLE_ID;
		VEHICLE_REGISTRATION = vEHICLE_REGISTRATION;
		AXLE = aXLE;
		POSITION = pOSITION;
		TYRE_STATUS = tYRE_STATUS;
		OPEN_ODOMETER = oPEN_ODOMETER;
		CLOSE_ODOMETER = cLOSE_ODOMETER;
		TYRE_USEAGE = tYRE_USEAGE;
		TYRE_COST = tYRE_COST;
		TYRE_ASSIGN_DATE = tYRE_ASSIGN_DATE;
		TYRE_MOUNT_DATE = tYRE_MOUNT_DATE;
		TYRE_DIS_MOUNT_DATE = tYRE_DIS_MOUNT_DATE;
		COST_PER_KM = cOST_PER_KM;
		TYRE_ASSIGN_STATUS_ID = tYRE_ASSIGN_STATUS_ID;
		VEHICLE_ODOMETER = vEHICLE_ODOMETER;
		WAREHOUSE_LOCATION = wAREHOUSE_LOCATION;
	}

	/**
	 * @return the tYRE_HIS_ID
	 */
	public Long getTYRE_HIS_ID() {
		return TYRE_HIS_ID;
	}

	/**
	 * @param tYRE_HIS_ID
	 *            the tYRE_HIS_ID to set
	 */
	public void setTYRE_HIS_ID(Long tYRE_HIS_ID) {
		TYRE_HIS_ID = tYRE_HIS_ID;
	}

	/**
	 * @return the tYRE_ID
	 */
	public Long getTYRE_ID() {
		return TYRE_ID;
	}

	/**
	 * @param tYRE_ID
	 *            the tYRE_ID to set
	 */
	public void setTYRE_ID(Long tYRE_ID) {
		TYRE_ID = tYRE_ID;
	}

	/**
	 * @return the tYRE_NUMBER
	 */
	public String getTYRE_NUMBER() {
		return TYRE_NUMBER;
	}

	/**
	 * @param tYRE_NUMBER
	 *            the tYRE_NUMBER to set
	 */
	public void setTYRE_NUMBER(String tYRE_NUMBER) {
		TYRE_NUMBER = tYRE_NUMBER;
	}

	/**
	 * @return the vEHICLE_ID
	 */
	public Integer getVEHICLE_ID() {
		return VEHICLE_ID;
	}

	/**
	 * @param vEHICLE_ID
	 *            the vEHICLE_ID to set
	 */
	public void setVEHICLE_ID(Integer vEHICLE_ID) {
		VEHICLE_ID = vEHICLE_ID;
	}

	/**
	 * @return the vEHICLE_REGISTRATION
	 */
	public String getVEHICLE_REGISTRATION() {
		return VEHICLE_REGISTRATION;
	}

	/**
	 * @param vEHICLE_REGISTRATION
	 *            the vEHICLE_REGISTRATION to set
	 */
	public void setVEHICLE_REGISTRATION(String vEHICLE_REGISTRATION) {
		VEHICLE_REGISTRATION = vEHICLE_REGISTRATION;
	}

	/**
	 * @return the aXLE
	 */
	public String getAXLE() {
		return AXLE;
	}

	/**
	 * @param aXLE
	 *            the aXLE to set
	 */
	public void setAXLE(String aXLE) {
		AXLE = aXLE;
	}

	/**
	 * @return the pOSITION
	 */
	public String getPOSITION() {
		return POSITION;
	}

	/**
	 * @param pOSITION
	 *            the pOSITION to set
	 */
	public void setPOSITION(String pOSITION) {
		POSITION = pOSITION;
	}

	/**
	 * @return the tYRE_STATUS
	 */
	public String getTYRE_STATUS() {
		return TYRE_STATUS;
	}

	/**
	 * @param tYRE_STATUS
	 *            the tYRE_STATUS to set
	 */
	public void setTYRE_STATUS(String tYRE_STATUS) {
		TYRE_STATUS = tYRE_STATUS;
	}

	/**
	 * @return the oPEN_ODOMETER
	 */
	public Integer getOPEN_ODOMETER() {
		return OPEN_ODOMETER;
	}

	/**
	 * @param oPEN_ODOMETER
	 *            the oPEN_ODOMETER to set
	 */
	public void setOPEN_ODOMETER(Integer oPEN_ODOMETER) {
		OPEN_ODOMETER = oPEN_ODOMETER;
	}

	/**
	 * @return the cLOSE_ODOMETER
	 */
	public Integer getCLOSE_ODOMETER() {
		return CLOSE_ODOMETER;
	}

	/**
	 * @param cLOSE_ODOMETER
	 *            the cLOSE_ODOMETER to set
	 */
	public void setCLOSE_ODOMETER(Integer cLOSE_ODOMETER) {
		CLOSE_ODOMETER = cLOSE_ODOMETER;
	}

	/**
	 * @return the tYRE_USEAGE
	 */
	public Integer getTYRE_USEAGE() {
		return TYRE_USEAGE;
	}

	/**
	 * @param tYRE_USEAGE
	 *            the tYRE_USEAGE to set
	 */
	public void setTYRE_USEAGE(Integer tYRE_USEAGE) {
		TYRE_USEAGE = tYRE_USEAGE;
	}

	/**
	 * @return the tYRE_COST
	 */
	public Double getTYRE_COST() {
		return TYRE_COST;
	}

	/**
	 * @param tYRE_COST
	 *            the tYRE_COST to set
	 */
	public void setTYRE_COST(Double tYRE_COST) {
		TYRE_COST = Utility.round(tYRE_COST, 2);
	}

	/**
	 * @return the tYRE_ASSIGN_DATE
	 */
	public String getTYRE_ASSIGN_DATE() {
		return TYRE_ASSIGN_DATE;
	}

	/**
	 * @param tYRE_ASSIGN_DATE
	 *            the tYRE_ASSIGN_DATE to set
	 */
	public void setTYRE_ASSIGN_DATE(String tYRE_ASSIGN_DATE) {
		TYRE_ASSIGN_DATE = tYRE_ASSIGN_DATE;
	}

	/**
	 * @return the tYRE_COMMENT
	 */
	public String getTYRE_COMMENT() {
		return TYRE_COMMENT;
	}

	/**
	 * @param tYRE_COMMENT
	 *            the tYRE_COMMENT to set
	 */
	public void setTYRE_COMMENT(String tYRE_COMMENT) {
		TYRE_COMMENT = tYRE_COMMENT;
	}

	
	/**
	 * @return the tYRE_STATUS_ID
	 */
	public short getTYRE_STATUS_ID() {
		return TYRE_STATUS_ID;
	}

	/**
	 * @param tYRE_STATUS_ID the tYRE_STATUS_ID to set
	 */
	public void setTYRE_STATUS_ID(short tYRE_STATUS_ID) {
		TYRE_STATUS_ID = tYRE_STATUS_ID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((TYRE_ID == null) ? 0 : TYRE_ID.hashCode());
		result = prime * result + ((VEHICLE_ID == null) ? 0 : VEHICLE_ID.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryTyreHistoryDto other = (InventoryTyreHistoryDto) obj;
		if (TYRE_ID == null) {
			if (other.TYRE_ID != null)
				return false;
		} else if (!TYRE_ID.equals(other.TYRE_ID))
			return false;
		if (VEHICLE_ID == null) {
			if (other.VEHICLE_ID != null)
				return false;
		} else if (!VEHICLE_ID.equals(other.VEHICLE_ID))
			return false;
		return true;
	}

	/*@Override
	public String toString() {
		return "InventoryTyreHistoryDto [TYRE_HIS_ID=" + TYRE_HIS_ID + ", TYRE_ID=" + TYRE_ID + ", TYRE_NUMBER="
				+ TYRE_NUMBER + ", VEHICLE_ID=" + VEHICLE_ID + ", VEHICLE_REGISTRATION=" + VEHICLE_REGISTRATION
				+ ", AXLE=" + AXLE + ", POSITION=" + POSITION + ", TYRE_STATUS=" + TYRE_STATUS + ", TYRE_STATUS_ID="
				+ TYRE_STATUS_ID + ", OPEN_ODOMETER=" + OPEN_ODOMETER + ", CLOSE_ODOMETER=" + CLOSE_ODOMETER
				+ ", TYRE_USEAGE=" + TYRE_USEAGE + ", TYRE_COST=" + TYRE_COST + ", TYRE_ASSIGN_DATE=" + TYRE_ASSIGN_DATE
				+ ", TYRE_ASSIGN_DATE_TIMESTAMP=" + TYRE_ASSIGN_DATE_TIMESTAMP + ", TYRE_COMMENT=" + TYRE_COMMENT
				+ ", tyreModel=" + tyreModel + ", companyId=" + companyId + ", PRE_TYRE_ASSIGN_DATE="
				+ PRE_TYRE_ASSIGN_DATE + ", PRE_TYRE_ASSIGN_DATEStr=" + PRE_TYRE_ASSIGN_DATEStr + ", PRE_TYRE_NUMBER="
				+ PRE_TYRE_NUMBER + ", PRE_OPEN_ODOMETER=" + PRE_OPEN_ODOMETER + "]";
	}*/
	
	
	
	public static String getHistoryStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case INVENTORY_TYRE_HISTORY_POSITION_LO:
			  statusName	= INVENTORY_TYRE_HISTORY_POSITION_LO_NAME;
		        break;
		  case INVENTORY_TYRE_HISTORY_POSITION_LI: 
			  statusName	= INVENTORY_TYRE_HISTORY_POSITION_LI_NAME;
		        break;
		  case INVENTORY_TYRE_HISTORY_POSITION_RI: 
			  statusName	= INVENTORY_TYRE_HISTORY_POSITION_RI_NAME;
		        break;
		  case INVENTORY_TYRE_HISTORY_POSITION_RO: 
			  statusName	= INVENTORY_TYRE_HISTORY_POSITION_RO_NAME;
		        break;
		  case INVENTORY_TYRE_HISTORY_POSITION_ST: 
			  statusName	= INVENTORY_TYRE_HISTORY_POSITION_ST_NAME;
		        break;
		 
		
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryTyreHistoryDto [TYRE_HIS_ID=");
		builder.append(TYRE_HIS_ID);
		builder.append(", TYRE_ID=");
		builder.append(TYRE_ID);
		builder.append(", TYRE_NUMBER=");
		builder.append(TYRE_NUMBER);
		builder.append(", VEHICLE_ID=");
		builder.append(VEHICLE_ID);
		builder.append(", VEHICLE_REGISTRATION=");
		builder.append(VEHICLE_REGISTRATION);
		builder.append(", AXLE=");
		builder.append(AXLE);
		builder.append(", POSITION=");
		builder.append(POSITION);
		builder.append(", TYRE_STATUS=");
		builder.append(TYRE_STATUS);
		builder.append(", TYRE_STATUS_ID=");
		builder.append(TYRE_STATUS_ID);
		builder.append(", OPEN_ODOMETER=");
		builder.append(OPEN_ODOMETER);
		builder.append(", CLOSE_ODOMETER=");
		builder.append(CLOSE_ODOMETER);
		builder.append(", TYRE_USEAGE=");
		builder.append(TYRE_USEAGE);
		builder.append(", TYRE_COST=");
		builder.append(TYRE_COST);
		builder.append(", TYRE_ASSIGN_DATE=");
		builder.append(TYRE_ASSIGN_DATE);
		builder.append(", TYRE_ASSIGN_DATE_TIMESTAMP=");
		builder.append(TYRE_ASSIGN_DATE_TIMESTAMP);
		builder.append(", TYRE_COMMENT=");
		builder.append(TYRE_COMMENT);
		builder.append(", tyreModel=");
		builder.append(tyreModel);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", PRE_TYRE_ASSIGN_DATE=");
		builder.append(PRE_TYRE_ASSIGN_DATE);
		builder.append(", PRE_TYRE_ASSIGN_DATEStr=");
		builder.append(PRE_TYRE_ASSIGN_DATEStr);
		builder.append(", PRE_TYRE_NUMBER=");
		builder.append(PRE_TYRE_NUMBER);
		builder.append(", PRE_OPEN_ODOMETER=");
		builder.append(PRE_OPEN_ODOMETER);
		builder.append(", TYRE_MOUNT_DATE=");
		builder.append(TYRE_MOUNT_DATE);
		builder.append(", TYRE_DIS_MOUNT_DATE=");
		builder.append(TYRE_DIS_MOUNT_DATE);
		builder.append(", COST_PER_KM=");
		builder.append(COST_PER_KM);
		builder.append(", VEHICLE_ODOMETER=");
		builder.append(VEHICLE_ODOMETER);
		builder.append(", WAREHOUSE_LOCATION=");
		builder.append(WAREHOUSE_LOCATION);
		builder.append("]");
		return builder.toString();
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getTyreModel() {
		return tyreModel;
	}

	public void setTyreModel(String tyreModel) {
		this.tyreModel = tyreModel;
	}

	public static String getStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case INVENTORY_TYRE_HISTORY_STATUS_AVAILABLE:
			  statusName	= INVENTORY_TYRE_HISTORY_STATUS_AVAILABLE_NAME;
		        break;
		  case INVENTORY_TYRE_HISTORY_STATUS_IN_SERVICE: 
			  statusName	= INVENTORY_TYRE_HISTORY_STATUS_IN_SERVICE_NAME;
		        break;
		  case INVENTORY_TYRE_HISTORY_STATUS_SCRAPED: 
			  statusName	= INVENTORY_TYRE_HISTORY_STATUS_SCRAPED_NAME;
		        break;
		  case INVENTORY_TYRE_HISTORY_STATUS_SENT_RETREAD: 
			  statusName	= INVENTORY_TYRE_HISTORY_STATUS_SENT_RETREAD_NAME;
		        break;
		  case INVENTORY_TYRE_HISTORY_STATUS_RECEIVE: 
			  statusName	= INVENTORY_TYRE_HISTORY_STATUS_RECEIVE_NAME;
		        break;
		  case INVENTORY_TYRE_HISTORY_STATUS_MOUNT: 
			  statusName	= INVENTORY_TYRE_HISTORY_STATUS_MOUNT_NAME;
		        break;
		  case INVENTORY_TYRE_HISTORY_STATUS_DISMOUNT: 
			  statusName	= INVENTORY_TYRE_HISTORY_STATUS_DISMOUNT_NAME;
		        break;
		  case INVENTORY_TYRE_HISTORY_STATUS_REJECT: 
			  statusName	= INVENTORY_TYRE_HISTORY_STATUS_REJECT_NAME;
		        break;
		  case INVENTORY_TYRE_HISTORY_STATUS_ROTATION: 
			  statusName	= INVENTORY_TYRE_HISTORY_STATUS_ROTATION_NAME;
		        break;
		
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}

	public Timestamp getTYRE_ASSIGN_DATE_TIMESTAMP() {
		return TYRE_ASSIGN_DATE_TIMESTAMP;
	}

	public void setTYRE_ASSIGN_DATE_TIMESTAMP(Timestamp tYRE_ASSIGN_DATE_TIMESTAMP) {
		TYRE_ASSIGN_DATE_TIMESTAMP = tYRE_ASSIGN_DATE_TIMESTAMP;
	}

	public Timestamp getPRE_TYRE_ASSIGN_DATE() {
		return PRE_TYRE_ASSIGN_DATE;
	}

	public void setPRE_TYRE_ASSIGN_DATE(Timestamp pRE_TYRE_ASSIGN_DATE) {
		PRE_TYRE_ASSIGN_DATE = pRE_TYRE_ASSIGN_DATE;
	}

	public String getPRE_TYRE_ASSIGN_DATEStr() {
		return PRE_TYRE_ASSIGN_DATEStr;
	}

	public void setPRE_TYRE_ASSIGN_DATEStr(String pRE_TYRE_ASSIGN_DATEStr) {
		PRE_TYRE_ASSIGN_DATEStr = pRE_TYRE_ASSIGN_DATEStr;
	}

	public String getPRE_TYRE_NUMBER() {
		return PRE_TYRE_NUMBER;
	}

	public void setPRE_TYRE_NUMBER(String pRE_TYRE_NUMBER) {
		PRE_TYRE_NUMBER = pRE_TYRE_NUMBER;
	}

	public Integer getPRE_OPEN_ODOMETER() {
		return PRE_OPEN_ODOMETER;
	}

	public void setPRE_OPEN_ODOMETER(Integer pRE_OPEN_ODOMETER) {
		PRE_OPEN_ODOMETER = pRE_OPEN_ODOMETER;
	}

	public String getTYRE_MOUNT_DATE() {
		return TYRE_MOUNT_DATE;
	}

	public void setTYRE_MOUNT_DATE(String tYRE_MOUNT_DATE) {
		TYRE_MOUNT_DATE = tYRE_MOUNT_DATE;
	}

	public String getTYRE_DIS_MOUNT_DATE() {
		return TYRE_DIS_MOUNT_DATE;
	}

	public void setTYRE_DIS_MOUNT_DATE(String tYRE_DIS_MOUNT_DATE) {
		TYRE_DIS_MOUNT_DATE = tYRE_DIS_MOUNT_DATE;
	}

	public Double getCOST_PER_KM() {
		return COST_PER_KM;
	}

	public void setCOST_PER_KM(Double cOST_PER_KM) {
		COST_PER_KM = Utility.round(cOST_PER_KM, 2);
	}

	public short getTYRE_ASSIGN_STATUS_ID() {
		return TYRE_ASSIGN_STATUS_ID;
	}

	public void setTYRE_ASSIGN_STATUS_ID(short tYRE_ASSIGN_STATUS_ID) {
		TYRE_ASSIGN_STATUS_ID = tYRE_ASSIGN_STATUS_ID;
	}

	public Integer getVEHICLE_ODOMETER() {
		return VEHICLE_ODOMETER;
	}

	public void setVEHICLE_ODOMETER(Integer vEHICLE_ODOMETER) {
		VEHICLE_ODOMETER = vEHICLE_ODOMETER;
	}

	


}
