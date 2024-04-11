/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class DriverAdvanceDto {

	private Long DRIADVID;

	/** The value for the TRIP DRIVER ID field */
	private int TRIP_DRIVER_ID;

	/** The value for the TRIP DRIVER NAME field */
	private String TRIP_DRIVER_NAME;

	/** The value for the ADVANCE DATE field */
	private String ADVANCE_DATE;
	
	private Date ADVANCE_DATE_ON;

	/** The value for the DRIVER_JAMA_BALANCE field */
	private Double DRIVER_JAMA_BALANCE;

	/** The value for the DRIVER_ADVANCE_AMOUNT field */
	private Double DRIVER_ADVANCE_AMOUNT;

	/** The value for the DRIVER_ADVANCE_REMARK field */
	private String DRIVER_ADVANCE_REMARK;

	/** The value for the ADVANCE_STATUS field */
	private String ADVANCE_STATUS;
	
	private short ADVANCE_STATUS_ID;

	/** The value for the ADVANCE_TYPE field */
	private String ADVANCE_PAID_TYPE;
	
	private short ADVANCE_PAID_TYPE_ID;

	/** The value for the ADVANCE_TYPE field */
	private String ADVANCE_PAID_NUMBER;

	/** The value for the ADVANCE_PAID_BY field */
	private String ADVANCE_PAID_BY;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;

	boolean markForDelete;

	private Date CREATED;

	private Date LASTUPDATED;

	public DriverAdvanceDto() {
		super();
	}

	public DriverAdvanceDto(Long dRIADVID, int tRIP_DRIVER_ID, String tRIP_DRIVER_NAME, String aDVANCE_DATE,
			Double dRIVER_JAMA_BALANCE, Double dRIVER_ADVANCE_AMOUNT, String dRIVER_ADVANCE_REMARK,
			String aDVANCE_GIVEN_TYPE, String aDVANCE_PAID_TYPE, String aDVANCE_PAID_NUMBER, String aDVANCE_PAID_BY,
			String cREATEDBY, String lASTMODIFIEDBY, boolean MarkForDelete, Date cREATED, Date lASTUPDATED) {
		super();
		DRIADVID = dRIADVID;
		TRIP_DRIVER_ID = tRIP_DRIVER_ID;
		TRIP_DRIVER_NAME = tRIP_DRIVER_NAME;
		ADVANCE_DATE = aDVANCE_DATE;
		DRIVER_JAMA_BALANCE = dRIVER_JAMA_BALANCE;
		DRIVER_ADVANCE_AMOUNT = dRIVER_ADVANCE_AMOUNT;
		DRIVER_ADVANCE_REMARK = dRIVER_ADVANCE_REMARK;
		ADVANCE_STATUS = aDVANCE_GIVEN_TYPE;
		ADVANCE_PAID_TYPE = aDVANCE_PAID_TYPE;
		ADVANCE_PAID_NUMBER = aDVANCE_PAID_NUMBER;
		ADVANCE_PAID_BY = aDVANCE_PAID_BY;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED = cREATED;
		LASTUPDATED = lASTUPDATED;
	}

	/**
	 * @return the dRIADVID
	 */
	public Long getDRIADVID() {
		return DRIADVID;
	}

	/**
	 * @param dRIADVID
	 *            the dRIADVID to set
	 */
	public void setDRIADVID(Long dRIADVID) {
		DRIADVID = dRIADVID;
	}

	/**
	 * @return the tRIP_DRIVER_ID
	 */
	public int getTRIP_DRIVER_ID() {
		return TRIP_DRIVER_ID;
	}

	/**
	 * @param tRIP_DRIVER_ID
	 *            the tRIP_DRIVER_ID to set
	 */
	public void setTRIP_DRIVER_ID(int tRIP_DRIVER_ID) {
		TRIP_DRIVER_ID = tRIP_DRIVER_ID;
	}

	/**
	 * @return the tRIP_DRIVER_NAME
	 */
	public String getTRIP_DRIVER_NAME() {
		return TRIP_DRIVER_NAME;
	}

	/**
	 * @param tRIP_DRIVER_NAME
	 *            the tRIP_DRIVER_NAME to set
	 */
	public void setTRIP_DRIVER_NAME(String tRIP_DRIVER_NAME) {
		TRIP_DRIVER_NAME = tRIP_DRIVER_NAME;
	}

	/**
	 * @return the aDVANCE_DATE
	 */
	public String getADVANCE_DATE() {
		return ADVANCE_DATE;
	}

	/**
	 * @param aDVANCE_DATE
	 *            the aDVANCE_DATE to set
	 */
	public void setADVANCE_DATE(String aDVANCE_DATE) {
		ADVANCE_DATE = aDVANCE_DATE;
	}

	/**
	 * @return the dRIVER_JAMA_BALANCE
	 */
	public Double getDRIVER_JAMA_BALANCE() {
		return DRIVER_JAMA_BALANCE;
	}

	/**
	 * @param dRIVER_JAMA_BALANCE
	 *            the dRIVER_JAMA_BALANCE to set
	 */
	public void setDRIVER_JAMA_BALANCE(Double dRIVER_JAMA_BALANCE) {
		DRIVER_JAMA_BALANCE = Utility.round(dRIVER_JAMA_BALANCE,2);
	}

	/**
	 * @return the dRIVER_ADVANCE_AMOUNT
	 */
	public Double getDRIVER_ADVANCE_AMOUNT() {
		return DRIVER_ADVANCE_AMOUNT;
	}

	/**
	 * @param dRIVER_ADVANCE_AMOUNT
	 *            the dRIVER_ADVANCE_AMOUNT to set
	 */
	public void setDRIVER_ADVANCE_AMOUNT(Double dRIVER_ADVANCE_AMOUNT) {
		DRIVER_ADVANCE_AMOUNT = Utility.round(dRIVER_ADVANCE_AMOUNT,2);
	}

	/**
	 * @return the dRIVER_ADVANCE_REMARK
	 */
	public String getDRIVER_ADVANCE_REMARK() {
		return DRIVER_ADVANCE_REMARK;
	}

	/**
	 * @return the aDVANCE_DATE_ON
	 */
	public Date getADVANCE_DATE_ON() {
		return ADVANCE_DATE_ON;
	}

	/**
	 * @param aDVANCE_DATE_ON the aDVANCE_DATE_ON to set
	 */
	public void setADVANCE_DATE_ON(Date aDVANCE_DATE_ON) {
		ADVANCE_DATE_ON = aDVANCE_DATE_ON;
	}

	/**
	 * @param dRIVER_ADVANCE_REMARK
	 *            the dRIVER_ADVANCE_REMARK to set
	 */
	public void setDRIVER_ADVANCE_REMARK(String dRIVER_ADVANCE_REMARK) {
		DRIVER_ADVANCE_REMARK = dRIVER_ADVANCE_REMARK;
	}

	/**
	 * @return the aDVANCE_GIVEN_TYPE
	 */
	public String getADVANCE_STATUS() {
		return ADVANCE_STATUS;
	}

	/**
	 * @param aDVANCE_GIVEN_TYPE
	 *            the aDVANCE_GIVEN_TYPE to set
	 */
	public void setADVANCE_STATUS(String aDVANCE_GIVEN_TYPE) {
		ADVANCE_STATUS = aDVANCE_GIVEN_TYPE;
	}

	public short getADVANCE_STATUS_ID() {
		return ADVANCE_STATUS_ID;
	}

	public void setADVANCE_STATUS_ID(short aDVANCE_STATUS_ID) {
		ADVANCE_STATUS_ID = aDVANCE_STATUS_ID;
	}

	/**
	 * @return the aDVANCE_PAID_TYPE
	 */
	public String getADVANCE_PAID_TYPE() {
		return ADVANCE_PAID_TYPE;
	}

	/**
	 * @param aDVANCE_PAID_TYPE
	 *            the aDVANCE_PAID_TYPE to set
	 */
	public void setADVANCE_PAID_TYPE(String aDVANCE_PAID_TYPE) {
		ADVANCE_PAID_TYPE = aDVANCE_PAID_TYPE;
	}

	/**
	 * @return the aDVANCE_PAID_NUMBER
	 */
	public String getADVANCE_PAID_NUMBER() {
		return ADVANCE_PAID_NUMBER;
	}

	/**
	 * @param aDVANCE_PAID_NUMBER
	 *            the aDVANCE_PAID_NUMBER to set
	 */
	public void setADVANCE_PAID_NUMBER(String aDVANCE_PAID_NUMBER) {
		ADVANCE_PAID_NUMBER = aDVANCE_PAID_NUMBER;
	}

	/**
	 * @return the aDVANCE_PAID_BY
	 */
	public String getADVANCE_PAID_BY() {
		return ADVANCE_PAID_BY;
	}

	/**
	 * @param aDVANCE_PAID_BY
	 *            the aDVANCE_PAID_BY to set
	 */
	public void setADVANCE_PAID_BY(String aDVANCE_PAID_BY) {
		ADVANCE_PAID_BY = aDVANCE_PAID_BY;
	}

	public short getADVANCE_PAID_TYPE_ID() {
		return ADVANCE_PAID_TYPE_ID;
	}

	public void setADVANCE_PAID_TYPE_ID(short aDVANCE_PAID_TYPE_ID) {
		ADVANCE_PAID_TYPE_ID = aDVANCE_PAID_TYPE_ID;
	}

	/**
	 * @return the cREATEDBY
	 */
	public String getCREATEDBY() {
		return CREATEDBY;
	}

	/**
	 * @param cREATEDBY
	 *            the cREATEDBY to set
	 */
	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	/**
	 * @return the lASTMODIFIEDBY
	 */
	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	/**
	 * @param lASTMODIFIEDBY
	 *            the lASTMODIFIEDBY to set
	 */
	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}

	/**
	 * @return the sTATUS
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param sTATUS
	 *            the sTATUS to set
	 */
	public void setMarkForDelete(boolean MarkForDelete) {
		markForDelete = MarkForDelete;
	}

	/**
	 * @return the cREATED
	 */
	public Date getCREATED() {
		return CREATED;
	}

	/**
	 * @param cREATED
	 *            the cREATED to set
	 */
	public void setCREATED(Date cREATED) {
		CREATED = cREATED;
	}

	/**
	 * @return the lASTUPDATED
	 */
	public Date getLASTUPDATED() {
		return LASTUPDATED;
	}

	/**
	 * @param lASTUPDATED
	 *            the lASTUPDATED to set
	 */
	public void setLASTUPDATED(Date lASTUPDATED) {
		LASTUPDATED = lASTUPDATED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverAdvance [DRIADVID=");
		builder.append(DRIADVID);
		builder.append(", TRIP_DRIVER_ID=");
		builder.append(TRIP_DRIVER_ID);
		builder.append(", TRIP_DRIVER_NAME=");
		builder.append(TRIP_DRIVER_NAME);
		builder.append(", ADVANCE_DATE=");
		builder.append(ADVANCE_DATE);
		builder.append(", DRIVER_JAMA_BALANCE=");
		builder.append(DRIVER_JAMA_BALANCE);
		builder.append(", DRIVER_ADVANCE_AMOUNT=");
		builder.append(DRIVER_ADVANCE_AMOUNT);
		builder.append(", DRIVER_ADVANCE_REMARK=");
		builder.append(DRIVER_ADVANCE_REMARK);
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
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", LASTUPDATED=");
		builder.append(LASTUPDATED);
		builder.append("]");
		return builder.toString();
	}

}
