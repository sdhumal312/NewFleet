/**
 * 
 */
package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author fleetop
 *
 */
@Entity
@Table(name = "DriverAdvance")
public class DriverAdvance implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DRIADVID")
	private Long DRIADVID;

	/** The value for the TRIP DRIVER ID field */
	@Column(name = "TRIP_DRIVER_ID")
	private int TRIP_DRIVER_ID;


	/** The value for the ADVANCE DATE field */
	@Basic(optional = false)
	@Column(name = "ADVANCE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ADVANCE_DATE;

	/** The value for the DRIVER_JAMA_BALANCE field */
	@Column(name = "DRIVER_JAMA_BALANCE")
	private Double DRIVER_JAMA_BALANCE;

	/** The value for the DRIVER_ADVANCE_AMOUNT field */
	@Column(name = "DRIVER_ADVANCE_AMOUNT")
	private Double DRIVER_ADVANCE_AMOUNT;

	/** The value for the DRIVER_ADVANCE_REMARK field */
	@Column(name = "DRIVER_ADVANCE_REMARK", length = 250)
	private String DRIVER_ADVANCE_REMARK;

	@Column(name = "ADVANCE_STATUS_ID", nullable = false)
	private short ADVANCE_STATUS_ID;
	
	@Column(name = "ADVANCE_PAID_TYPE_ID", nullable = false)
	private short ADVANCE_PAID_TYPE_ID;

	/** The value for the ADVANCE_TYPE field */
	@Column(name = "ADVANCE_PAID_NUMBER", length = 150)
	private String ADVANCE_PAID_NUMBER;

	/** The value for the ADVANCE_PAID_BY field *//*
	@Column(name = "ADVANCE_PAID_BY", length = 150)
	private String ADVANCE_PAID_BY;*/
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	@Column(name = "CREATED_BY_ID", nullable = false)
	private Long  CREATED_BY_ID;
	
	@Column(name = "LASTUPDATED_ID")
	private Long  LASTUPDATED_ID;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	@Column(name = "CREATED", updatable = false)
	private Date CREATED;

	@Column(name = "LASTUPDATED")
	private Date LASTUPDATED;

	public DriverAdvance() {
		super();
	}

	public DriverAdvance(Long dRIADVID, int tRIP_DRIVER_ID, Date aDVANCE_DATE,
			Double dRIVER_JAMA_BALANCE, Double dRIVER_ADVANCE_AMOUNT, String dRIVER_ADVANCE_REMARK,
			 String aDVANCE_PAID_NUMBER, String aDVANCE_PAID_BY,
			 boolean MarkForDelete, Date cREATED, Date lASTUPDATED) {
		super();
		DRIADVID = dRIADVID;
		TRIP_DRIVER_ID = tRIP_DRIVER_ID;
		ADVANCE_DATE = aDVANCE_DATE;
		DRIVER_JAMA_BALANCE = dRIVER_JAMA_BALANCE;
		DRIVER_ADVANCE_AMOUNT = dRIVER_ADVANCE_AMOUNT;
		DRIVER_ADVANCE_REMARK = dRIVER_ADVANCE_REMARK;
		ADVANCE_PAID_NUMBER = aDVANCE_PAID_NUMBER;
		//ADVANCE_PAID_BY = aDVANCE_PAID_BY;
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
	 * @return the aDVANCE_DATE
	 */
	public Date getADVANCE_DATE() {
		return ADVANCE_DATE;
	}

	/**
	 * @param aDVANCE_DATE
	 *            the aDVANCE_DATE to set
	 */
	public void setADVANCE_DATE(Date aDVANCE_DATE) {
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
		DRIVER_JAMA_BALANCE = dRIVER_JAMA_BALANCE;
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
		DRIVER_ADVANCE_AMOUNT = dRIVER_ADVANCE_AMOUNT;
	}

	/**
	 * @return the dRIVER_ADVANCE_REMARK
	 */
	public String getDRIVER_ADVANCE_REMARK() {
		return DRIVER_ADVANCE_REMARK;
	}

	/**
	 * @param dRIVER_ADVANCE_REMARK
	 *            the dRIVER_ADVANCE_REMARK to set
	 */
	public void setDRIVER_ADVANCE_REMARK(String dRIVER_ADVANCE_REMARK) {
		DRIVER_ADVANCE_REMARK = dRIVER_ADVANCE_REMARK;
	}


	public short getADVANCE_STATUS_ID() {
		return ADVANCE_STATUS_ID;
	}

	public void setADVANCE_STATUS_ID(short aDVANCE_STATUS_ID) {
		ADVANCE_STATUS_ID = aDVANCE_STATUS_ID;
	}

	public short getADVANCE_PAID_TYPE_ID() {
		return ADVANCE_PAID_TYPE_ID;
	}

	public void setADVANCE_PAID_TYPE_ID(short aDVANCE_PAID_TYPE_ID) {
		ADVANCE_PAID_TYPE_ID = aDVANCE_PAID_TYPE_ID;
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
	 *//*
	public String getADVANCE_PAID_BY() {
		return ADVANCE_PAID_BY;
	}

	*//**
	 * @param aDVANCE_PAID_BY
	 *            the aDVANCE_PAID_BY to set
	 *//*
	public void setADVANCE_PAID_BY(String aDVANCE_PAID_BY) {
		ADVANCE_PAID_BY = aDVANCE_PAID_BY;
	}*/

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
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
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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

	public Long getCREATED_BY_ID() {
		return CREATED_BY_ID;
	}

	public void setCREATED_BY_ID(Long cREATED_BY_ID) {
		CREATED_BY_ID = cREATED_BY_ID;
	}

	public Long getLASTUPDATED_ID() {
		return LASTUPDATED_ID;
	}

	public void setLASTUPDATED_ID(Long lASTUPDATED_ID) {
		LASTUPDATED_ID = lASTUPDATED_ID;
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
		builder.append(", ADVANCE_DATE=");
		builder.append(ADVANCE_DATE);
		builder.append(", DRIVER_JAMA_BALANCE=");
		builder.append(DRIVER_JAMA_BALANCE);
		builder.append(", DRIVER_ADVANCE_AMOUNT=");
		builder.append(DRIVER_ADVANCE_AMOUNT);
		builder.append(", DRIVER_ADVANCE_REMARK=");
		builder.append(DRIVER_ADVANCE_REMARK);
		builder.append(", ADVANCE_PAID_NUMBER=");
		builder.append(ADVANCE_PAID_NUMBER);
		/*builder.append(", ADVANCE_PAID_BY=");
		builder.append(ADVANCE_PAID_BY);*/
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
