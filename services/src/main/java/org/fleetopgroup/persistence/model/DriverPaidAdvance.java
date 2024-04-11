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
@Table(name = "DriverPaidAdvance")
public class DriverPaidAdvance implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DPAID")
	private Long DPAID;

	@Column(name = "DRIVER_ID")
	private Integer DRIVER_ID;

	/** The value for the ADVANCE DATE field */
	@Basic(optional = false)
	@Column(name = "PAID_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date PAID_DATE;

	/** The value for the DRIVER_ADVANCE_AMOUNT field */
	@Column(name = "PAID_AMOUNT")
	private Double PAID_AMOUNT;

	/** The value for the DRIVER_JAMA_BALANCE field */
	@Column(name = "ADVANCE_BALANCE")
	private Double ADVANCE_BALANCE;

	/** The value for the DRIVER_ADVANCE_REMARK field */
	@Column(name = "PAID_REMARK", length = 250)
	private String PAID_REMARK;
	
	@Column(name = "ADVANCE_RECEIVED_BY_ID")
	private Long ADVANCE_RECEIVED_BY_ID;
	
	@Column(name = "PAID_STATUS_ID")
	private short PAID_STATUS_ID;

	/** The value for the ADVANCE_TYPE field *//*
	@Column(name = "ADVANCE_PAID_TYPE", length = 150)
	private String ADVANCE_PAID_TYPE;*/
	
	@Column(name = "ADVANCE_PAID_TYPE_ID", nullable = false)
	private short ADVANCE_PAID_TYPE_ID;

	/** The value for the ADVANCE_TYPE field */
	@Column(name = "ADVANCE_PAID_NUMBER", length = 150)
	private String ADVANCE_PAID_NUMBER;

	/** The value for the ADVANCE_PAID_BY field *//*
	@Column(name = "ADVANCE_RECEIVED_BY", length = 150)
	private String ADVANCE_RECEIVED_BY;*/
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	/*@Column(name = "CREATEDBY", length = 150)
	private String CREATEDBY;

	@Column(name = "LASTMODIFIEDBY", length = 150)
	private String LASTMODIFIEDBY;
*/
	@Column(name = "CREATEDBYID")
	private Long CREATEDBYID;
	
	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@Column(name = "CREATED", updatable = false)
	private Date CREATED;

	@Column(name = "LASTUPDATED")
	private Date LASTUPDATED;

	@Column(name = "DSAID")
	private Long DSAID;

	public DriverPaidAdvance() {
		super();
	}

	public DriverPaidAdvance(Long dPAID, Integer aDVANCE_ID, Date pAID_DATE, Double pAID_AMOUNT, Double aDVANCE_BALANCE,
			String pAID_REMARK,  String aDVANCE_PAID_TYPE, String aDVANCE_PAID_NUMBER,
		    Date cREATED, Date lASTUPDATED) {
		super();
		DPAID = dPAID;
		DRIVER_ID = aDVANCE_ID;
		PAID_DATE = pAID_DATE;
		PAID_AMOUNT = pAID_AMOUNT;
		ADVANCE_BALANCE = aDVANCE_BALANCE;
		PAID_REMARK = pAID_REMARK;
		//PAID_STATUS = pAID_STATUS;
		//ADVANCE_PAID_TYPE = aDVANCE_PAID_TYPE;
		ADVANCE_PAID_NUMBER = aDVANCE_PAID_NUMBER;
		//ADVANCE_RECEIVED_BY = aDVANCE_RECEIVED_BY;
		//CREATEDBY = cREATEDBY;
		//LASTMODIFIEDBY = lASTMODIFIEDBY;
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

	public Date getPAID_DATE() {
		return PAID_DATE;
	}

	public void setPAID_DATE(Date pAID_DATE) {
		PAID_DATE = pAID_DATE;
	}

	public Double getPAID_AMOUNT() {
		return PAID_AMOUNT;
	}

	public void setPAID_AMOUNT(Double pAID_AMOUNT) {
		PAID_AMOUNT = pAID_AMOUNT;
	}

	public Double getADVANCE_BALANCE() {
		return ADVANCE_BALANCE;
	}

	public void setADVANCE_BALANCE(Double aDVANCE_BALANCE) {
		ADVANCE_BALANCE = aDVANCE_BALANCE;
	}

	public String getPAID_REMARK() {
		return PAID_REMARK;
	}

	public void setPAID_REMARK(String pAID_REMARK) {
		PAID_REMARK = pAID_REMARK;
	}

/*	public String getPAID_STATUS() {
		return PAID_STATUS;
	}

	public void setPAID_STATUS(String pAID_STATUS) {
		PAID_STATUS = pAID_STATUS;
	}*/

/*	public String getADVANCE_PAID_TYPE() {
		return ADVANCE_PAID_TYPE;
	}

	public void setADVANCE_PAID_TYPE(String aDVANCE_PAID_TYPE) {
		ADVANCE_PAID_TYPE = aDVANCE_PAID_TYPE;
	}
*/
	public String getADVANCE_PAID_NUMBER() {
		return ADVANCE_PAID_NUMBER;
	}

	public void setADVANCE_PAID_NUMBER(String aDVANCE_PAID_NUMBER) {
		ADVANCE_PAID_NUMBER = aDVANCE_PAID_NUMBER;
	}

	/*public String getADVANCE_RECEIVED_BY() {
		return ADVANCE_RECEIVED_BY;
	}

	public void setADVANCE_RECEIVED_BY(String aDVANCE_RECEIVED_BY) {
		ADVANCE_RECEIVED_BY = aDVANCE_RECEIVED_BY;
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
	}*/

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

	public Long getDSAID() {
		return DSAID;
	}

	public void setDSAID(Long dSAID) {
		DSAID = dSAID;
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

	/**
	 * @return the pAID_STATUS_ID
	 */
	public short getPAID_STATUS_ID() {
		return PAID_STATUS_ID;
	}

	/**
	 * @param pAID_STATUS_ID the pAID_STATUS_ID to set
	 */
	public void setPAID_STATUS_ID(short pAID_STATUS_ID) {
		PAID_STATUS_ID = pAID_STATUS_ID;
	}

	/**
	 * @return the cREATEDBYID
	 */
	public Long getCREATEDBYID() {
		return CREATEDBYID;
	}

	/**
	 * @param cREATEDBYID the cREATEDBYID to set
	 */
	public void setCREATEDBYID(Long cREATEDBYID) {
		CREATEDBYID = cREATEDBYID;
	}

	/**
	 * @return the lASTMODIFIEDBYID
	 */
	public Long getLASTMODIFIEDBYID() {
		return LASTMODIFIEDBYID;
	}

	/**
	 * @param lASTMODIFIEDBYID the lASTMODIFIEDBYID to set
	 */
	public void setLASTMODIFIEDBYID(Long lASTMODIFIEDBYID) {
		LASTMODIFIEDBYID = lASTMODIFIEDBYID;
	}

	/**
	 * @return the aDVANCE_PAID_TYPE_ID
	 */
	public short getADVANCE_PAID_TYPE_ID() {
		return ADVANCE_PAID_TYPE_ID;
	}

	/**
	 * @param aDVANCE_PAID_TYPE_ID the aDVANCE_PAID_TYPE_ID to set
	 */
	public void setADVANCE_PAID_TYPE_ID(short aDVANCE_PAID_TYPE_ID) {
		ADVANCE_PAID_TYPE_ID = aDVANCE_PAID_TYPE_ID;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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
		DriverPaidAdvance other = (DriverPaidAdvance) obj;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverPaidAdvance [DPAID=");
		builder.append(DPAID);
		builder.append(", DRIVER_ID=");
		builder.append(DRIVER_ID);
		builder.append(", PAID_DATE=");
		builder.append(PAID_DATE);
		builder.append(", PAID_AMOUNT=");
		builder.append(PAID_AMOUNT);
		builder.append(", ADVANCE_BALANCE=");
		builder.append(ADVANCE_BALANCE);
		builder.append(", PAID_REMARK=");
		builder.append(PAID_REMARK);
		/*builder.append(", PAID_STATUS=");
		builder.append(PAID_STATUS);*/
		/*builder.append(", ADVANCE_PAID_TYPE=");
		builder.append(ADVANCE_PAID_TYPE);*/
		builder.append(", ADVANCE_PAID_NUMBER=");
		builder.append(ADVANCE_PAID_NUMBER);
		/*builder.append(", ADVANCE_RECEIVED_BY=");
		builder.append(ADVANCE_RECEIVED_BY);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);*/
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", LASTUPDATED=");
		builder.append(LASTUPDATED);
		builder.append(", DSAID=");
		builder.append(DSAID);
		builder.append("]");
		return builder.toString();
	}

}
