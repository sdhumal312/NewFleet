/**
 * 
 */
package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

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
@Table(name = "DriverSalaryAdvance")
public class DriverSalaryAdvance implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DSAID")
	private Long DSAID;
	
	@Column(name = "DSANUMBER", nullable = false)
	private Long DSANUMBER;

	/** The value for the TRIP DRIVER ID field */
	@Column(name = "DRIVER_ID")
	private int DRIVER_ID;


	/** The value for the ADVANCE DATE field */
	@Basic(optional = false)
	@Column(name = "ADVANCE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ADVANCE_DATE;

	/** The value for the DRIVER_ADVANCE_AMOUNT field  */
	@Column(name = "ADVANCE_AMOUNT")
	private Double ADVANCE_AMOUNT;

	/** The value for the DRIVER_JAMA_BALANCE field */
	@Column(name = "ADVANCE_BALANCE")
	private Double ADVANCE_BALANCE;

	/** The value for the DRIVER_ADVANCE_REMARK field */
	@Column(name = "ADVANCE_REMARK", length = 250)
	private String ADVANCE_REMARK;

	@Column(name = "ADVANCE_STATUS_ID", nullable = false)
	private short ADVANCE_STATUS_ID;
	
	@Column(name = "ADVANCE_NAME_ID", nullable = false)
	private short ADVANCE_NAME_ID;
	
	/** The value for the Current Trip Daily Sheet ID */
	@Column(name = "TRIPDAILYID")
	private Long TRIPDAILYID;


	@Column(name = "ADVANCE_PAID_TYPE_ID", nullable = false)
	private short ADVANCE_PAID_TYPE_ID;

	/** The value for the ADVANCE_TYPE field */
	@Column(name = "ADVANCE_PAID_NUMBER", length = 150)
	private String ADVANCE_PAID_NUMBER;

	/** The value for the ADVANCE_PAID_BY field *//*
	@Column(name = "ADVANCE_PAID_BY", length = 150)
	private String ADVANCE_PAID_BY;*/
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer	COMPANY_ID;

	@Column(name = "CREATED_BY_ID", nullable = false)
	private Long CREATED_BY_ID;
	
	@Column(name = "LASTMODIFIED_BY_ID", nullable = false)
	private Long LASTMODIFIED_BY_ID;

	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@Column(name = "CREATED", updatable = false)
	private Date CREATED;

	@Column(name = "LASTUPDATED")
	private Date LASTUPDATED;

	@Column(name = "DRIVER_ADVANCE_TYPE_ID", nullable = false) 
	private short DRIVER_ADVANCE_TYPE_ID;

	public short getDRIVER_ADVANCE_TYPE_ID() {
		return DRIVER_ADVANCE_TYPE_ID;
	}

	public void setDRIVER_ADVANCE_TYPE_ID(short dRIVER_ADVANCE_TYPE_ID) {
		DRIVER_ADVANCE_TYPE_ID = dRIVER_ADVANCE_TYPE_ID;
	}

	public DriverSalaryAdvance() {
		super();
	}

	public DriverSalaryAdvance(Long dSAID, Long dSANUMBER, int dRIVER_ID,  Date aDVANCE_DATE, Double aDVANCE_AMOUNT,
			Double aDVANCE_BALANCE, String aDVANCE_REMARK, 
			String aDVANCE_PAID_NUMBER, String aDVANCE_PAID_BY, Integer cOMPANY_ID, 
			Date cREATED, Date lASTUPDATED) {
		super();
		DSAID = dSAID;
		DSANUMBER = dSANUMBER;
		DRIVER_ID = dRIVER_ID;
		ADVANCE_DATE = aDVANCE_DATE;
		ADVANCE_AMOUNT = aDVANCE_AMOUNT;
		ADVANCE_BALANCE = aDVANCE_BALANCE;
		ADVANCE_REMARK = aDVANCE_REMARK;
	//	ADVANCE_STATUS = aDVANCE_STATUS;
		//ADVANCE_PAID_TYPE = aDVANCE_PAID_TYPE;
		ADVANCE_PAID_NUMBER = aDVANCE_PAID_NUMBER;
		//ADVANCE_PAID_BY = aDVANCE_PAID_BY;
		COMPANY_ID	= cOMPANY_ID;
		//CREATEDBY = cREATEDBY;
		//LASTMODIFIEDBY = lASTMODIFIEDBY;
		CREATED = cREATED;
		LASTUPDATED = lASTUPDATED;
	}

	public Long getDSAID() {
		return DSAID;
	}

	public void setDSAID(Long dSAID) {
		DSAID = dSAID;
	}

	public Long getDSANUMBER() {
		return DSANUMBER;
	}

	public void setDSANUMBER(Long dSANUMBER) {
		DSANUMBER = dSANUMBER;
	}

	public int getDRIVER_ID() {
		return DRIVER_ID;
	}

	public void setDRIVER_ID(int dRIVER_ID) {
		DRIVER_ID = dRIVER_ID;
	}

	

/*	*//**
	 * @return the aDVANCE_NAME
	 *//*
	public String getADVANCE_NAME() {
		return ADVANCE_NAME;
	}

	*//**
	 * @param aDVANCE_NAME the aDVANCE_NAME to set
	 *//*
	public void setADVANCE_NAME(String aDVANCE_NAME) {
		ADVANCE_NAME = aDVANCE_NAME;
	}
*/
	/**
	 * @return the tRIPDAILYID
	 */
	public Long getTRIPDAILYID() {
		return TRIPDAILYID;
	}

	/**
	 * @param tRIPDAILYID the tRIPDAILYID to set
	 */
	public void setTRIPDAILYID(Long tRIPDAILYID) {
		TRIPDAILYID = tRIPDAILYID;
	}

	public Date getADVANCE_DATE() {
		return ADVANCE_DATE;
	}

	public void setADVANCE_DATE(Date aDVANCE_DATE) {
		ADVANCE_DATE = aDVANCE_DATE;
	}

	public Double getADVANCE_AMOUNT() {
		return ADVANCE_AMOUNT;
	}

	public void setADVANCE_AMOUNT(Double aDVANCE_AMOUNT) {
		ADVANCE_AMOUNT = aDVANCE_AMOUNT;
	}

	public Double getADVANCE_BALANCE() {
		return ADVANCE_BALANCE;
	}

	public void setADVANCE_BALANCE(Double aDVANCE_BALANCE) {
		ADVANCE_BALANCE = aDVANCE_BALANCE;
	}

	public String getADVANCE_REMARK() {
		return ADVANCE_REMARK;
	}

	public void setADVANCE_REMARK(String aDVANCE_REMARK) {
		ADVANCE_REMARK = aDVANCE_REMARK;
	}

/*	public String getADVANCE_STATUS() {
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
	}*/

	public String getADVANCE_PAID_NUMBER() {
		return ADVANCE_PAID_NUMBER;
	}

	public void setADVANCE_PAID_NUMBER(String aDVANCE_PAID_NUMBER) {
		ADVANCE_PAID_NUMBER = aDVANCE_PAID_NUMBER;
	}

	/*public String getADVANCE_PAID_BY() {
		return ADVANCE_PAID_BY;
	}

	public void setADVANCE_PAID_BY(String aDVANCE_PAID_BY) {
		ADVANCE_PAID_BY = aDVANCE_PAID_BY;
	}*/

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/*public String getCREATEDBY() {
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

	*/

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

	public Date getCREATED() {
		return CREATED;
	}

	public void setCREATED(Date cREATED) {
		CREATED = cREATED;
	}

	public Date getLASTUPDATED() {
		return LASTUPDATED;
	}

	public void setLASTUPDATED(Date lASTUPDATED) {
		LASTUPDATED = lASTUPDATED;
	}
	


	public short getADVANCE_STATUS_ID() {
		return ADVANCE_STATUS_ID;
	}

	public void setADVANCE_STATUS_ID(short aDVANCE_STATUS_ID) {
		ADVANCE_STATUS_ID = aDVANCE_STATUS_ID;
	}

	public short getADVANCE_NAME_ID() {
		return ADVANCE_NAME_ID;
	}

	public void setADVANCE_NAME_ID(short aDVANCE_NAME_ID) {
		ADVANCE_NAME_ID = aDVANCE_NAME_ID;
	}

	public short getADVANCE_PAID_TYPE_ID() {
		return ADVANCE_PAID_TYPE_ID;
	}

	public void setADVANCE_PAID_TYPE_ID(short aDVANCE_PAID_TYPE_ID) {
		ADVANCE_PAID_TYPE_ID = aDVANCE_PAID_TYPE_ID;
	}

	public Long getCREATED_BY_ID() {
		return CREATED_BY_ID;
	}

	public void setCREATED_BY_ID(Long cREATED_BY_ID) {
		CREATED_BY_ID = cREATED_BY_ID;
	}

	public Long getLASTMODIFIED_BY_ID() {
		return LASTMODIFIED_BY_ID;
	}

	public void setLASTMODIFIED_BY_ID(Long lASTMODIFIED_BY_ID) {
		LASTMODIFIED_BY_ID = lASTMODIFIED_BY_ID;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverSalaryAdvance [DSAID=");
		builder.append(DSAID);
		builder.append(", DSANUMBER=");
		builder.append(DSANUMBER);
		builder.append(", DRIVER_ID=");
		builder.append(DRIVER_ID);
		builder.append(", ADVANCE_DATE=");
		builder.append(ADVANCE_DATE);
		builder.append(", ADVANCE_AMOUNT=");
		builder.append(ADVANCE_AMOUNT);
		builder.append(", ADVANCE_BALANCE=");
		builder.append(ADVANCE_BALANCE);
		builder.append(", ADVANCE_REMARK=");
		builder.append(ADVANCE_REMARK);
		/*builder.append(", ADVANCE_STATUS=");
		builder.append(ADVANCE_STATUS);
		builder.append(", ADVANCE_NAME=");
		builder.append(ADVANCE_NAME);*/
		builder.append(", TRIPDAILYID=");
		builder.append(TRIPDAILYID);
		/*builder.append(", ADVANCE_PAID_TYPE=");
		builder.append(ADVANCE_PAID_TYPE);*/
		builder.append(", ADVANCE_PAID_NUMBER=");
		builder.append(ADVANCE_PAID_NUMBER);
		/*builder.append(", ADVANCE_PAID_BY=");
		builder.append(ADVANCE_PAID_BY);*/
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		/*builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);*/
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
