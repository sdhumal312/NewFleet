/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import java.io.Serializable;
import java.util.Date;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class DriverPaidAdvanceDto implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	private Long DPAID;

	private Integer DRIVER_ID;

	/** The value for the ADVANCE DATE field */

	private String PAID_DATE;
	
	private Date PAID_DATE_ON;

	/** The value for the DRIVER_ADVANCE_AMOUNT field */
	private Double ADVANCE_PAID_AMOUNT;

	/** The value for the DRIVER_ADVANCE_AMOUNT field */
	private Double PENALTY_PAID_AMOUNT;

	/** The value for the DRIVER_JAMA_BALANCE field */
	private Double ADVANCE_BALANCE;

	/** The value for the DRIVER_ADVANCE_REMARK field */
	private String PAID_REMARK;

	/** The value for the ADVANCE_STATUS field */
	private String PAID_STATUS;

	/** The value for the ADVANCE_TYPE field */
	private String ADVANCE_PAID_TYPE;
	
	private Long ADVANCE_RECEIVED_BY_ID;

	/** The value for the ADVANCE_TYPE field */
	private String ADVANCE_PAID_NUMBER;

	/** The value for the ADVANCE_PAID_BY field */
	private String ADVANCE_RECEIVED_BY;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;

	private boolean markForDelete;

	private String CREATED;

	private String LASTUPDATED;

	public DriverPaidAdvanceDto() {
		super();
	}

	public DriverPaidAdvanceDto(Long dPAID, Integer dRIVER_ID, String pAID_DATE, Double aDVANCE_PAID_AMOUNT,
			Double pENALTY_PAID_AMOUNT, Double aDVANCE_BALANCE, String pAID_REMARK, String pAID_STATUS,
			String aDVANCE_PAID_TYPE, String aDVANCE_PAID_NUMBER, String aDVANCE_RECEIVED_BY, String cREATEDBY,
			String lASTMODIFIEDBY, boolean markForDelete, String cREATED, String lASTUPDATED) {
		super();
		DPAID = dPAID;
		DRIVER_ID = dRIVER_ID;
		PAID_DATE = pAID_DATE;
		ADVANCE_PAID_AMOUNT = aDVANCE_PAID_AMOUNT;
		PENALTY_PAID_AMOUNT = pENALTY_PAID_AMOUNT;
		ADVANCE_BALANCE = aDVANCE_BALANCE;
		PAID_REMARK = pAID_REMARK;
		PAID_STATUS = pAID_STATUS;
		ADVANCE_PAID_TYPE = aDVANCE_PAID_TYPE;
		ADVANCE_PAID_NUMBER = aDVANCE_PAID_NUMBER;
		ADVANCE_RECEIVED_BY = aDVANCE_RECEIVED_BY;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		this.markForDelete = markForDelete;
		CREATED = cREATED;
		LASTUPDATED = lASTUPDATED;
	}

	public Long getDPAID() {
		return DPAID;
	}

	public void setDPAID(Long dPAID) {
		DPAID = dPAID;
	}

	public Integer getDRIVER_ID() {
		return DRIVER_ID;
	}

	public void setDRIVER_ID(Integer dRIVER_ID) {
		DRIVER_ID = dRIVER_ID;
	}

	public String getPAID_DATE() {
		return PAID_DATE;
	}

	public void setPAID_DATE(String pAID_DATE) {
		PAID_DATE = pAID_DATE;
	}

	/**
	 * @return the aDVANCE_PAID_AMOUNT
	 */
	public Double getADVANCE_PAID_AMOUNT() {
		return ADVANCE_PAID_AMOUNT;
	}

	/**
	 * @param aDVANCE_PAID_AMOUNT
	 *            the aDVANCE_PAID_AMOUNT to set
	 */
	public void setADVANCE_PAID_AMOUNT(Double aDVANCE_PAID_AMOUNT) {
		ADVANCE_PAID_AMOUNT = Utility.round(aDVANCE_PAID_AMOUNT, 2);
	}

	/**
	 * @return the pENALTY_PAID_AMOUNT
	 */
	public Double getPENALTY_PAID_AMOUNT() {
		return PENALTY_PAID_AMOUNT;
	}

	/**
	 * @param pENALTY_PAID_AMOUNT
	 *            the pENALTY_PAID_AMOUNT to set
	 */
	public void setPENALTY_PAID_AMOUNT(Double pENALTY_PAID_AMOUNT) {
		PENALTY_PAID_AMOUNT = Utility.round(pENALTY_PAID_AMOUNT, 2);
	}

	/**
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete
	 *            the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Double getADVANCE_BALANCE() {
		return ADVANCE_BALANCE;
	}

	public void setADVANCE_BALANCE(Double aDVANCE_BALANCE) {
		ADVANCE_BALANCE = Utility.round(aDVANCE_BALANCE, 2);
	}

	public String getPAID_REMARK() {
		return PAID_REMARK;
	}

	public void setPAID_REMARK(String pAID_REMARK) {
		PAID_REMARK = pAID_REMARK;
	}

	public String getPAID_STATUS() {
		return PAID_STATUS;
	}

	public void setPAID_STATUS(String pAID_STATUS) {
		PAID_STATUS = pAID_STATUS;
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

	/**
	 * @return the pAID_DATE_ON
	 */
	public Date getPAID_DATE_ON() {
		return PAID_DATE_ON;
	}

	/**
	 * @param pAID_DATE_ON the pAID_DATE_ON to set
	 */
	public void setPAID_DATE_ON(Date pAID_DATE_ON) {
		PAID_DATE_ON = pAID_DATE_ON;
	}

	public String getADVANCE_RECEIVED_BY() {
		return ADVANCE_RECEIVED_BY;
	}

	public void setADVANCE_RECEIVED_BY(String aDVANCE_RECEIVED_BY) {
		ADVANCE_RECEIVED_BY = aDVANCE_RECEIVED_BY;
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
	 * @param markForDelete
	 *            the markForDelete to set
	 */
	public void setmarkForDelete(boolean mARKFORDELETE) {
		markForDelete = mARKFORDELETE;
	}

	public String getCREATED() {
		return CREATED;
	}

	public void setCREATED(String cREATED) {
		CREATED = cREATED;
	}

	public String getLASTUPDATED() {
		return LASTUPDATED;
	}

	public void setLASTUPDATED(String lASTUPDATED) {
		LASTUPDATED = lASTUPDATED;
	}

	/**
	 * @return the aDVANCE_RECEIVED_BY_ID
	 */
	public Long getADVANCE_RECEIVED_BY_ID() {
		return ADVANCE_RECEIVED_BY_ID;
	}

	/**
	 * @param aDVANCE_RECEIVED_BY_ID the aDVANCE_RECEIVED_BY_ID to set
	 */
	public void setADVANCE_RECEIVED_BY_ID(Long aDVANCE_RECEIVED_BY_ID) {
		ADVANCE_RECEIVED_BY_ID = aDVANCE_RECEIVED_BY_ID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((DRIVER_ID == null) ? 0 : DRIVER_ID.hashCode());
		result = prime * result + ((DPAID == null) ? 0 : DPAID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DriverPaidAdvanceDto other = (DriverPaidAdvanceDto) obj;
		if (DRIVER_ID == null) {
			if (other.DRIVER_ID != null)
				return false;
		} else if (!DRIVER_ID.equals(other.DRIVER_ID))
			return false;
		if (DPAID == null) {
			if (other.DPAID != null)
				return false;
		} else if (!DPAID.equals(other.DPAID))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverPaidAdvanceDto [DPAID=");
		builder.append(DPAID);
		builder.append(", DRIVER_ID=");
		builder.append(DRIVER_ID);
		builder.append(", PAID_DATE=");
		builder.append(PAID_DATE);
		builder.append(", ADVANCE_PAID_AMOUNT=");
		builder.append(ADVANCE_PAID_AMOUNT);
		builder.append(", PENALTY_PAID_AMOUNT=");
		builder.append(PENALTY_PAID_AMOUNT);
		builder.append(", ADVANCE_BALANCE=");
		builder.append(ADVANCE_BALANCE);
		builder.append(", PAID_REMARK=");
		builder.append(PAID_REMARK);
		builder.append(", PAID_STATUS=");
		builder.append(PAID_STATUS);
		builder.append(", ADVANCE_PAID_TYPE=");
		builder.append(ADVANCE_PAID_TYPE);
		builder.append(", ADVANCE_PAID_NUMBER=");
		builder.append(ADVANCE_PAID_NUMBER);
		builder.append(", ADVANCE_RECEIVED_BY=");
		builder.append(ADVANCE_RECEIVED_BY);
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
		builder.append("]");
		return builder.toString();
	}

}
