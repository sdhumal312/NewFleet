/**
 * 
 */
package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;
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
@Table(name = "InventoryTyreHistory")
public class InventoryTyreHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value for the ITYRE_ID Layout Id field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TYRE_HIS_ID")
	private Long TYRE_HIS_ID;

	/** The value for the TYRE_ID field */
	@Column(name = "TYRE_ID")
	private Long TYRE_ID;

	/** The value for the TYRE_NUMBER Number field */
	@Column(name = "TYRE_NUMBER", nullable = false, length = 25)
	private String TYRE_NUMBER;

	/** The value for the Vehicle ID field */
	@Column(name = "VEHICLE_ID")
	private Integer VEHICLE_ID;
/*
	*//** The value for the Vehicle Registration Number field *//*
	@Column(name = "VEHICLE_REGISTRATION", length = 25)
	private String VEHICLE_REGISTRATION;*/

	/** The value for the AXLE field */
	@Column(name = "AXLE", length = 25)
	private String AXLE;
	

	/** The value for the POSITION field */
	@Column(name = "POSITION", length = 25)
	private String POSITION;
	
/*	
	*//** The value for the TYRE_STATUS field *//*
	@Column(name = "TYRE_STATUS", length = 25)
	private String TYRE_STATUS;*/
	
	@Column(name = "TYRE_STATUS_ID")
	private short TYRE_STATUS_ID;

	/** The value for the OPEN_ODOMETER ID field */
	@Column(name = "OPEN_ODOMETER")
	private Integer OPEN_ODOMETER;


	/** The value for the TYRE_USEAGE ID field */
	@Column(name = "TYRE_USEAGE")
	private Integer TYRE_USEAGE;

	/** The value for the TYRE_COST ID field */
	@Column(name = "TYRE_COST")
	private Double TYRE_COST;

	/** The value for the TYRE_ASSIGN_DATE field */
	@Basic(optional = false)
	@Column(name = "TYRE_ASSIGN_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date TYRE_ASSIGN_DATE;

	/** The value for the TYRE_STATUS field */
	@Column(name = "TYRE_COMMENT", length = 1000)
	private String TYRE_COMMENT;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer	COMPANY_ID;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "PRE_TYRE_ASSIGN_DATE")
	private Timestamp PRE_TYRE_ASSIGN_DATE;
	
	@Column(name = "PRE_TYRE_NUMBER")
	private String PRE_TYRE_NUMBER;
	
	@Column(name = "PRE_OPEN_ODOMETER")
	private Integer PRE_OPEN_ODOMETER;
	
	@Column(name = "createdById")
	private Long		createdById;
	
	@Column(name = "createdOn")
	private Timestamp	createdOn;

	@Column(name = "issueId")
	private long	issueId;
	
	@Column(name = "transactionTypeId")
	private short transactionTypeId;
	
	@Column(name = "transactionId")
	private Long transactionId;
	
	@Column(name = "transactionSubId")
	private Long transactionSubId;
	
	@Column(name = "tyreGauge")
	private Integer tyreGauge;

	public InventoryTyreHistory() {
		super();
	}

	public InventoryTyreHistory(Long tYRE_HIS_ID, Long tYRE_ID, String tYRE_NUMBER, Integer vEHICLE_ID,
			String aXLE, String pOSITION, Integer oPEN_ODOMETER,
			 Integer tYRE_USEAGE, Double tYRE_COST, Date tYRE_ASSIGN_DATE) {
		super();
		TYRE_HIS_ID = tYRE_HIS_ID;
		TYRE_ID = tYRE_ID;
		TYRE_NUMBER = tYRE_NUMBER;
		VEHICLE_ID = vEHICLE_ID;
		AXLE = aXLE;
		POSITION = pOSITION;
		OPEN_ODOMETER = oPEN_ODOMETER;
		TYRE_USEAGE = tYRE_USEAGE;
		TYRE_COST = tYRE_COST;
		TYRE_ASSIGN_DATE = tYRE_ASSIGN_DATE;
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
	 *//*
	public String getVEHICLE_REGISTRATION() {
		return VEHICLE_REGISTRATION;
	}

	*//**
	 * @param vEHICLE_REGISTRATION
	 *            the vEHICLE_REGISTRATION to set
	 *//*
	public void setVEHICLE_REGISTRATION(String vEHICLE_REGISTRATION) {
		VEHICLE_REGISTRATION = vEHICLE_REGISTRATION;
	}*/

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
	 *//*
	public String getTYRE_STATUS() {
		return TYRE_STATUS;
	}

	*//**
	 * @param tYRE_STATUS
	 *            the tYRE_STATUS to set
	 *//*
	public void setTYRE_STATUS(String tYRE_STATUS) {
		TYRE_STATUS = tYRE_STATUS;
	}
*/
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
		TYRE_COST = tYRE_COST;
	}

	/**
	 * @return the tYRE_ASSIGN_DATE
	 */
	public Date getTYRE_ASSIGN_DATE() {
		return TYRE_ASSIGN_DATE;
	}

	/**
	 * @param tYRE_ASSIGN_DATE
	 *            the tYRE_ASSIGN_DATE to set
	 */
	public void setTYRE_ASSIGN_DATE(Date tYRE_ASSIGN_DATE) {
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

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	public Timestamp getPRE_TYRE_ASSIGN_DATE() {
		return PRE_TYRE_ASSIGN_DATE;
	}

	public void setPRE_TYRE_ASSIGN_DATE(Timestamp pRE_TYRE_ASSIGN_DATE) {
		PRE_TYRE_ASSIGN_DATE = pRE_TYRE_ASSIGN_DATE;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
	

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public long getIssueId() {
		return issueId;
	}

	public void setIssueId(long issueId) {
		this.issueId = issueId;
	}
	
	public short getTransactionTypeId() {
		return transactionTypeId;
	}

	public void setTransactionTypeId(short transactionTypeId) {
		this.transactionTypeId = transactionTypeId;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}
	
	public Long getTransactionSubId() {
		return transactionSubId;
	}

	public void setTransactionSubId(Long transactionSubId) {
		this.transactionSubId = transactionSubId;
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
		InventoryTyreHistory other = (InventoryTyreHistory) obj;
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
	
	public Integer getTyreGauge() {
		return tyreGauge;
	}

	public void setTyreGauge(Integer tyreGauge) {
		this.tyreGauge = tyreGauge;
	}

	@Override
	public String toString() {
		return "InventoryTyreHistory [TYRE_HIS_ID=" + TYRE_HIS_ID + ", TYRE_ID=" + TYRE_ID + ", TYRE_NUMBER="
				+ TYRE_NUMBER + ", VEHICLE_ID=" + VEHICLE_ID + ", AXLE=" + AXLE + ", POSITION=" + POSITION
				+ ", TYRE_STATUS_ID=" + TYRE_STATUS_ID + ", OPEN_ODOMETER=" + OPEN_ODOMETER + ", TYRE_USEAGE="
				+ TYRE_USEAGE + ", TYRE_COST=" + TYRE_COST + ", TYRE_ASSIGN_DATE=" + TYRE_ASSIGN_DATE
				+ ", TYRE_COMMENT=" + TYRE_COMMENT + ", COMPANY_ID=" + COMPANY_ID + ", markForDelete=" + markForDelete
				+ ", PRE_TYRE_ASSIGN_DATE=" + PRE_TYRE_ASSIGN_DATE + ", PRE_TYRE_NUMBER=" + PRE_TYRE_NUMBER
				+ ", PRE_OPEN_ODOMETER=" + PRE_OPEN_ODOMETER + ", createdById=" + createdById + ", createdOn="
				+ createdOn + ", issueId=" + issueId + ", transactionTypeId=" + transactionTypeId + ", transactionId="
				+ transactionId + "]";
	}

	

}
