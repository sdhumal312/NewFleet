/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import javax.persistence.Column;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class DriverSalaryAdvanceDto extends TripDailySheetDto {

	private Long DSAID;

	/** The value for the TRIP DRIVER ID field */
	private int DRIVER_ID;

	/** The value for the TRIP DRIVER NAME field */
	private String driver_firstname;
	
	/** The value for the TRIP DRIVER NAME field */
	private String driver_Lastname;
	
	/** The value for the TRIP DRIVER NAME field */
	private String driverFatherName;
	
	public String getDriverFatherName() {
		return driverFatherName;
	}

	public void setDriverFatherName(String driverFatherName) {
		this.driverFatherName = driverFatherName;
	}

	/** The value for the TRIP DRIVER NAME field */
	private String driver_empnumber;

	/** The value for the ADVANCE DATE field */
	private String ADVANCE_DATE;
	

	/** The value for the DRIVER_ADVANCE_AMOUNT field */
	private Double ADVANCE_AMOUNT;

	/** The value for the DRIVER_JAMA_BALANCE field */
	private Double ADVANCE_BALANCE;

	/** The value for the DRIVER_ADVANCE_REMARK field */
	private String ADVANCE_REMARK;

	/** The value for the ADVANCE_STATUS field */
	private String ADVANCE_STATUS;

	/** The value for the ADVANCE_TYPE field */
	private String ADVANCE_PAID_TYPE;

	/** The value for the ADVANCE_TYPE field */
	private String ADVANCE_PAID_NUMBER;

	/** The value for the ADVANCE_PAID_BY field */
	private String ADVANCE_PAID_BY;
	
	/** The value for the ADVANCE_NAME (eg: penalty, advance) field */
	private String ADVANCE_NAME;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;

	private boolean markForDelete;

	private String CREATED;

	private String LASTUPDATED;
	
	private short ADVANCE_PAID_TYPE_ID;
	
	private Long DSANUMBER;
	private short ADVANCE_NAME_ID;
	private short ADVANCE_STATUS_ID;
	
	private short DRIVER_ADVANCE_TYPE_ID;

	public short getDRIVER_ADVANCE_TYPE_ID() {
		return DRIVER_ADVANCE_TYPE_ID;
	}

	public void setDRIVER_ADVANCE_TYPE_ID(short dRIVER_ADVANCE_TYPE_ID) {
		DRIVER_ADVANCE_TYPE_ID = dRIVER_ADVANCE_TYPE_ID;
	}

	public DriverSalaryAdvanceDto() {
		super();
	}

	public DriverSalaryAdvanceDto(Long dSAID, int dRIVER_ID,  String aDVANCE_DATE,
			Double aDVANCE_AMOUNT, Double aDVANCE_BALANCE, String aDVANCE_REMARK, String aDVANCE_STATUS,
			String aDVANCE_PAID_TYPE, String aDVANCE_PAID_NUMBER, String aDVANCE_PAID_BY, String cREATEDBY,
			String lASTMODIFIEDBY, String cREATED, String lASTUPDATED) {
		super();
		DSAID = dSAID;
		DRIVER_ID = dRIVER_ID;
		ADVANCE_DATE = aDVANCE_DATE;
		ADVANCE_AMOUNT = aDVANCE_AMOUNT;
		ADVANCE_BALANCE = aDVANCE_BALANCE;
		ADVANCE_REMARK = aDVANCE_REMARK;
		ADVANCE_STATUS = aDVANCE_STATUS;
		ADVANCE_PAID_TYPE = aDVANCE_PAID_TYPE;
		ADVANCE_PAID_NUMBER = aDVANCE_PAID_NUMBER;
		ADVANCE_PAID_BY = aDVANCE_PAID_BY;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		CREATED = cREATED;
		LASTUPDATED = lASTUPDATED;
	}
	
	

	/**
	 * @return the driver_firstname
	 */
	public String getDriver_firstname() {
		return driver_firstname;
	}

	/**
	 * @param driver_firstname the driver_firstname to set
	 */
	public void setDriver_firstname(String driver_firstname) {
		this.driver_firstname = driver_firstname;
	}

	/**
	 * @return the driver_Lastname
	 */
	public String getDriver_Lastname() {
		return driver_Lastname;
	}

	/**
	 * @param driver_Lastname the driver_Lastname to set
	 */
	public void setDriver_Lastname(String driver_Lastname) {
		this.driver_Lastname = driver_Lastname;
	}

	/**
	 * @return the driver_empnumber
	 */
	public String getDriver_empnumber() {
		return driver_empnumber;
	}

	/**
	 * @param driver_empnumber the driver_empnumber to set
	 */
	public void setDriver_empnumber(String driver_empnumber) {
		this.driver_empnumber = driver_empnumber;
	}

	public Long getDSAID() {
		return DSAID;
	}

	public void setDSAID(Long dSAID) {
		DSAID = dSAID;
	}

	public int getDRIVER_ID() {
		return DRIVER_ID;
	}

	public void setDRIVER_ID(int dRIVER_ID) {
		DRIVER_ID = dRIVER_ID;
	}


	public String getADVANCE_DATE() {
		return ADVANCE_DATE;
	}

	public void setADVANCE_DATE(String aDVANCE_DATE) {
		ADVANCE_DATE = aDVANCE_DATE;
	}

	public Double getADVANCE_AMOUNT() {
		return ADVANCE_AMOUNT;
	}

	public void setADVANCE_AMOUNT(Double aDVANCE_AMOUNT) {
		ADVANCE_AMOUNT = Utility.round(aDVANCE_AMOUNT, 2);
	}

	public Double getADVANCE_BALANCE() {
		return ADVANCE_BALANCE;
	}

	public void setADVANCE_BALANCE(Double aDVANCE_BALANCE) {
		ADVANCE_BALANCE =Utility.round(aDVANCE_BALANCE, 2);
	}

	public String getADVANCE_REMARK() {
		return ADVANCE_REMARK;
	}

	public void setADVANCE_REMARK(String aDVANCE_REMARK) {
		ADVANCE_REMARK = aDVANCE_REMARK;
	}

	public String getADVANCE_STATUS() {
		return ADVANCE_STATUS;
	}

	public void setADVANCE_STATUS(String aDVANCE_STATUS) {
		ADVANCE_STATUS = aDVANCE_STATUS;
	}

	public String getADVANCE_PAID_TYPE() {
		return ADVANCE_PAID_TYPE;
	}

	public void setADVANCE_PAID_TYPE(String aDVANCE_PAID_TYPE) {
		ADVANCE_PAID_TYPE = aDVANCE_PAID_TYPE;
	}

	public String getADVANCE_PAID_NUMBER() {
		return ADVANCE_PAID_NUMBER;
	}

	public void setADVANCE_PAID_NUMBER(String aDVANCE_PAID_NUMBER) {
		ADVANCE_PAID_NUMBER = aDVANCE_PAID_NUMBER;
	}

	public String getADVANCE_PAID_BY() {
		return ADVANCE_PAID_BY;
	}

	public void setADVANCE_PAID_BY(String aDVANCE_PAID_BY) {
		ADVANCE_PAID_BY = aDVANCE_PAID_BY;
	}

	public String getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean ismarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete the markForDelete to set
	 */
	public void setmarkForDelete(boolean mARKFORDELETE) {
		markForDelete = mARKFORDELETE;
	}

	

	public void setCREATED(String cREATED) {
		CREATED = cREATED;
	}


	public void setLASTUPDATED(String lASTUPDATED) {
		LASTUPDATED = lASTUPDATED;
	}
	
	

	/**
	 * @return the aDVANCE_NAME
	 */
	public String getADVANCE_NAME() {
		return ADVANCE_NAME;
	}

	/**
	 * @param aDVANCE_NAME the aDVANCE_NAME to set
	 */
	public void setADVANCE_NAME(String aDVANCE_NAME) {
		ADVANCE_NAME = aDVANCE_NAME;
	}

		/**
	 * @return the cREATED
	 */
	public String getCREATED() {
		return CREATED;
	}

	/**
	 * @return the lASTUPDATED
	 */
	public String getLASTUPDATED() {
		return LASTUPDATED;
	}

	public short getADVANCE_PAID_TYPE_ID() {
		return ADVANCE_PAID_TYPE_ID;
	}

	public void setADVANCE_PAID_TYPE_ID(short aDVANCE_PAID_TYPE_ID) {
		ADVANCE_PAID_TYPE_ID = aDVANCE_PAID_TYPE_ID;
	}

	public Long getDSANUMBER() {
		return DSANUMBER;
	}

	public void setDSANUMBER(Long dSANUMBER) {
		DSANUMBER = dSANUMBER;
	}

	public short getADVANCE_NAME_ID() {
		return ADVANCE_NAME_ID;
	}

	public void setADVANCE_NAME_ID(short aDVANCE_NAME_ID) {
		ADVANCE_NAME_ID = aDVANCE_NAME_ID;
	}

	public short getADVANCE_STATUS_ID() {
		return ADVANCE_STATUS_ID;
	}

	public void setADVANCE_STATUS_ID(short aDVANCE_STATUS_ID) {
		ADVANCE_STATUS_ID = aDVANCE_STATUS_ID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverSalaryAdvanceDto [DSAID=");
		builder.append(DSAID);
		builder.append(", DRIVER_ID=");
		builder.append(DRIVER_ID);
		builder.append(", driver_firstname=");
		builder.append(driver_firstname);
		builder.append(", driver_Lastname=");
		builder.append(driver_Lastname);
		builder.append(", driver_empnumber=");
		builder.append(driver_empnumber);
		builder.append(", ADVANCE_DATE=");
		builder.append(ADVANCE_DATE);
		builder.append(", ADVANCE_AMOUNT=");
		builder.append(ADVANCE_AMOUNT);
		builder.append(", ADVANCE_BALANCE=");
		builder.append(ADVANCE_BALANCE);
		builder.append(", ADVANCE_REMARK=");
		builder.append(ADVANCE_REMARK);
		builder.append(", ADVANCE_STATUS=");
		builder.append(ADVANCE_STATUS);
		builder.append(", ADVANCE_PAID_TYPE=");
		builder.append(ADVANCE_PAID_TYPE);
		builder.append(", ADVANCE_PAID_NUMBER=");
		builder.append(ADVANCE_PAID_NUMBER);
		builder.append(", ADVANCE_PAID_BY=");
		builder.append(ADVANCE_PAID_BY);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", LASTUPDATED=");
		builder.append(LASTUPDATED);
		builder.append(", DRIVER_ADVANCE_TYPE_ID=");
		builder.append(DRIVER_ADVANCE_TYPE_ID);
		builder.append("]");
		return builder.toString();
	}

}
