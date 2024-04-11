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
@Table(name = "Subscribe")
public class SubscribeBox implements Serializable {
	private static final long serialVersionUID = 1L;

	// private static final String TAB = " ";

	/** The value for the mailboxId field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "SUBSCRIBE_ID")
	private long SUBSCRIBE_ID;

	/** The value for the USER_ID field */
	@Basic(optional = false)
	@Column(name = "SUBSCRIBE_USER_ID", nullable = false)
	private long SUBSCRIBE_USER_ID;

	@Basic(optional = false)
	@Column(name = "SUBSCRIBE_USER_EMAIL", nullable = false, length = 200)
	private String SUBSCRIBE_USER_EMAIL;

	/** The value for the USER_ID field */
	@Basic(optional = false)
	@Column(name = "SUBSCRIBE_VEHICLE_ID", nullable = false)
	private Integer SUBSCRIBE_VEHICLE_ID;

	@Basic(optional = false)
	@Column(name = "SUBSCRIBE_VEHICLE_NAME", nullable = false, length = 200)
	private String SUBSCRIBE_VEHICLE_NAME;

	/** The value for the name field SUBSCRIBE_TYPE */
	@Basic(optional = true)
	@Column(name = "SUBSCRIBE_TYPE", length = 200)
	private String SUBSCRIBE_TYPE;
	
	@Column(name = "SUBSCRIBE_TYPE_ID", nullable = false)
	private Integer SUBSCRIBE_TYPE_ID;

	/** The value for the name field SUBSCRIBE_TYPE */
	@Basic(optional = true)
	@Column(name = "SUBSCRIBE_SUBTYPE", length = 200)
	private String SUBSCRIBE_SUBTYPE;
	
	
	@Column(name = "SUBSCRIBE_SUBTYPE_ID", nullable = false)
	private Integer SUBSCRIBE_SUBTYPE_ID;

	/** The value for the name field SUBSCRIBE_DATE */
	@Basic(optional = true)
	@Column(name = "SUBSCRIBE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date SUBSCRIBE_DATE;

	/** The value for the name field SUBSCRIBE_Threshold_date */
	@Basic(optional = true)
	@Column(name = "SUBSCRIBE_THRESHOLD_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date SUBSCRIBE_THRESHOLD_DATE;

	/** The value for the name field SUBSCRIBE_LOCATION */
	@Basic(optional = false)
	@Column(name = "SUBSCRIBE_LOCATION", nullable = false, length = 200)
	private String SUBSCRIBE_LOCATION;
	
	@Basic(optional = false)
	@Column(name = "SUBSCRIBE_LOCATION_ID", nullable = false)
	private Long SUBSCRIBE_LOCATION_ID;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private	Integer COMPANY_ID;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public SubscribeBox() {
		super();
	}

	public SubscribeBox(long sUBSCRIBE_ID, long sUBSCRIBE_USER_ID, String sUBSCRIBE_USER_EMAIL,
			Integer sUBSCRIBE_VEHICLE_ID, String sUBSCRIBE_VEHICLE_NAME, String sUBSCRIBE_TYPE,
			String sUBSCRIBE_SUBTYPE, Date sUBSCRIBE_DATE, Date sUBSCRIBE_THRESHOLD_DATE, String sUBSCRIBE_LOCATION
			, Integer COMPANY_ID) {
		super();
		SUBSCRIBE_ID = sUBSCRIBE_ID;
		SUBSCRIBE_USER_ID = sUBSCRIBE_USER_ID;
		SUBSCRIBE_USER_EMAIL = sUBSCRIBE_USER_EMAIL;
		SUBSCRIBE_VEHICLE_ID = sUBSCRIBE_VEHICLE_ID;
		SUBSCRIBE_VEHICLE_NAME = sUBSCRIBE_VEHICLE_NAME;
		SUBSCRIBE_TYPE = sUBSCRIBE_TYPE;
		SUBSCRIBE_SUBTYPE = sUBSCRIBE_SUBTYPE;
		SUBSCRIBE_DATE = sUBSCRIBE_DATE;
		SUBSCRIBE_THRESHOLD_DATE = sUBSCRIBE_THRESHOLD_DATE;
		SUBSCRIBE_LOCATION = sUBSCRIBE_LOCATION;
		this.COMPANY_ID = COMPANY_ID;
	}

	/**
	 * @return the sUBSCRIBE_ID
	 */
	public long getSUBSCRIBE_ID() {
		return SUBSCRIBE_ID;
	}

	/**
	 * @param sUBSCRIBE_ID
	 *            the sUBSCRIBE_ID to set
	 */
	public void setSUBSCRIBE_ID(long sUBSCRIBE_ID) {
		SUBSCRIBE_ID = sUBSCRIBE_ID;
	}

	/**
	 * @return the sUBSCRIBE_USER_ID
	 */
	public long getSUBSCRIBE_USER_ID() {
		return SUBSCRIBE_USER_ID;
	}

	/**
	 * @param sUBSCRIBE_USER_ID
	 *            the sUBSCRIBE_USER_ID to set
	 */
	public void setSUBSCRIBE_USER_ID(long sUBSCRIBE_USER_ID) {
		SUBSCRIBE_USER_ID = sUBSCRIBE_USER_ID;
	}

	/**
	 * @return the sUBSCRIBE_USER_EMAIL
	 */
	public String getSUBSCRIBE_USER_EMAIL() {
		return SUBSCRIBE_USER_EMAIL;
	}

	/**
	 * @param sUBSCRIBE_USER_EMAIL
	 *            the sUBSCRIBE_USER_EMAIL to set
	 */
	public void setSUBSCRIBE_USER_EMAIL(String sUBSCRIBE_USER_EMAIL) {
		SUBSCRIBE_USER_EMAIL = sUBSCRIBE_USER_EMAIL;
	}

	/**
	 * @return the sUBSCRIBE_VEHICLE_ID
	 */
	public Integer getSUBSCRIBE_VEHICLE_ID() {
		return SUBSCRIBE_VEHICLE_ID;
	}

	/**
	 * @param sUBSCRIBE_VEHICLE_ID
	 *            the sUBSCRIBE_VEHICLE_ID to set
	 */
	public void setSUBSCRIBE_VEHICLE_ID(Integer sUBSCRIBE_VEHICLE_ID) {
		SUBSCRIBE_VEHICLE_ID = sUBSCRIBE_VEHICLE_ID;
	}

	/**
	 * @return the sUBSCRIBE_VEHICLE_NAME
	 */
	public String getSUBSCRIBE_VEHICLE_NAME() {
		return SUBSCRIBE_VEHICLE_NAME;
	}

	/**
	 * @param sUBSCRIBE_VEHICLE_NAME
	 *            the sUBSCRIBE_VEHICLE_NAME to set
	 */
	public void setSUBSCRIBE_VEHICLE_NAME(String sUBSCRIBE_VEHICLE_NAME) {
		SUBSCRIBE_VEHICLE_NAME = sUBSCRIBE_VEHICLE_NAME;
	}

	/**
	 * @return the sUBSCRIBE_TYPE
	 */
	public String getSUBSCRIBE_TYPE() {
		return SUBSCRIBE_TYPE;
	}

	/**
	 * @param sUBSCRIBE_TYPE
	 *            the sUBSCRIBE_TYPE to set
	 */
	public void setSUBSCRIBE_TYPE(String sUBSCRIBE_TYPE) {
		SUBSCRIBE_TYPE = sUBSCRIBE_TYPE;
	}

	/**
	 * @return the sUBSCRIBE_SUBTYPE
	 */
	public String getSUBSCRIBE_SUBTYPE() {
		return SUBSCRIBE_SUBTYPE;
	}

	/**
	 * @param sUBSCRIBE_SUBTYPE
	 *            the sUBSCRIBE_SUBTYPE to set
	 */
	public void setSUBSCRIBE_SUBTYPE(String sUBSCRIBE_SUBTYPE) {
		SUBSCRIBE_SUBTYPE = sUBSCRIBE_SUBTYPE;
	}

	/**
	 * @return the sUBSCRIBE_DATE
	 */
	public Date getSUBSCRIBE_DATE() {
		return SUBSCRIBE_DATE;
	}

	/**
	 * @param sUBSCRIBE_DATE
	 *            the sUBSCRIBE_DATE to set
	 */
	public void setSUBSCRIBE_DATE(Date sUBSCRIBE_DATE) {
		SUBSCRIBE_DATE = sUBSCRIBE_DATE;
	}

	/**
	 * @return the sUBSCRIBE_THRESHOLD_DATE
	 */
	public Date getSUBSCRIBE_THRESHOLD_DATE() {
		return SUBSCRIBE_THRESHOLD_DATE;
	}

	/**
	 * @param sUBSCRIBE_THRESHOLD_DATE
	 *            the sUBSCRIBE_THRESHOLD_DATE to set
	 */
	public void setSUBSCRIBE_THRESHOLD_DATE(Date sUBSCRIBE_THRESHOLD_DATE) {
		SUBSCRIBE_THRESHOLD_DATE = sUBSCRIBE_THRESHOLD_DATE;
	}

	/**
	 * @return the sUBSCRIBE_LOCATION
	 */
	public String getSUBSCRIBE_LOCATION() {
		return SUBSCRIBE_LOCATION;
	}

	/**
	 * @param sUBSCRIBE_LOCATION
	 *            the sUBSCRIBE_LOCATION to set
	 */
	public void setSUBSCRIBE_LOCATION(String sUBSCRIBE_LOCATION) {
		SUBSCRIBE_LOCATION = sUBSCRIBE_LOCATION;
	}
	
	

	/**
	 * @return the sUBSCRIBE_LOCATION_ID
	 */
	public Long getSUBSCRIBE_LOCATION_ID() {
		return SUBSCRIBE_LOCATION_ID;
	}

	/**
	 * @param sUBSCRIBE_LOCATION_ID the sUBSCRIBE_LOCATION_ID to set
	 */
	public void setSUBSCRIBE_LOCATION_ID(Long sUBSCRIBE_LOCATION_ID) {
		SUBSCRIBE_LOCATION_ID = sUBSCRIBE_LOCATION_ID;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/**
	 * @return the sUBSCRIBE_TYPE_ID
	 */
	public Integer getSUBSCRIBE_TYPE_ID() {
		return SUBSCRIBE_TYPE_ID;
	}

	/**
	 * @param sUBSCRIBE_TYPE_ID the sUBSCRIBE_TYPE_ID to set
	 */
	public void setSUBSCRIBE_TYPE_ID(Integer sUBSCRIBE_TYPE_ID) {
		SUBSCRIBE_TYPE_ID = sUBSCRIBE_TYPE_ID;
	}

	/**
	 * @return the sUBSCRIBE_SUBTYPE_ID
	 */
	public Integer getSUBSCRIBE_SUBTYPE_ID() {
		return SUBSCRIBE_SUBTYPE_ID;
	}

	/**
	 * @param sUBSCRIBE_SUBTYPE_ID the sUBSCRIBE_SUBTYPE_ID to set
	 */
	public void setSUBSCRIBE_SUBTYPE_ID(Integer sUBSCRIBE_SUBTYPE_ID) {
		SUBSCRIBE_SUBTYPE_ID = sUBSCRIBE_SUBTYPE_ID;
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

	public boolean ismarkForDelete() {
		return markForDelete;
	}

	public void setmarkForDelete(boolean MarkForDelete) {
		markForDelete = MarkForDelete;
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
		result = prime * result + (int) (SUBSCRIBE_ID ^ (SUBSCRIBE_ID >>> 32));
		result = prime * result + (int) (SUBSCRIBE_USER_ID ^ (SUBSCRIBE_USER_ID >>> 32));
		result = prime * result + ((SUBSCRIBE_VEHICLE_ID == null) ? 0 : SUBSCRIBE_VEHICLE_ID.hashCode());
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
		SubscribeBox other = (SubscribeBox) obj;
		if (SUBSCRIBE_ID != other.SUBSCRIBE_ID)
			return false;
		if (SUBSCRIBE_USER_ID != other.SUBSCRIBE_USER_ID)
			return false;
		if (SUBSCRIBE_VEHICLE_ID == null) {
			if (other.SUBSCRIBE_VEHICLE_ID != null)
				return false;
		} else if (!SUBSCRIBE_VEHICLE_ID.equals(other.SUBSCRIBE_VEHICLE_ID))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SubscribeBox [SUBSCRIBE_ID=");
		builder.append(SUBSCRIBE_ID);
		builder.append(", SUBSCRIBE_USER_ID=");
		builder.append(SUBSCRIBE_USER_ID);
		builder.append(", SUBSCRIBE_USER_EMAIL=");
		builder.append(SUBSCRIBE_USER_EMAIL);
		builder.append(", SUBSCRIBE_VEHICLE_ID=");
		builder.append(SUBSCRIBE_VEHICLE_ID);
		builder.append(", SUBSCRIBE_VEHICLE_NAME=");
		builder.append(SUBSCRIBE_VEHICLE_NAME);
		builder.append(", SUBSCRIBE_TYPE=");
		builder.append(SUBSCRIBE_TYPE);
		builder.append(", SUBSCRIBE_SUBTYPE=");
		builder.append(SUBSCRIBE_SUBTYPE);
		builder.append(", SUBSCRIBE_DATE=");
		builder.append(SUBSCRIBE_DATE);
		builder.append(", SUBSCRIBE_THRESHOLD_DATE=");
		builder.append(SUBSCRIBE_THRESHOLD_DATE);
		builder.append(", SUBSCRIBE_LOCATION=");
		builder.append(SUBSCRIBE_LOCATION);
		builder.append(", SUBSCRIBE_LOCATION_ID=");
		builder.append(SUBSCRIBE_LOCATION_ID);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

}
