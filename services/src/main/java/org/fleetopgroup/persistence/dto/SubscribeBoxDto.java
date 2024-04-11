/**
 * 
 */
package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 */
public class SubscribeBoxDto{
	// private static final String TAB = " ";

	/** The value for the mailboxId field */
	private long SUBSCRIBE_ID;

	/** The value for the USER_ID field */
	private long SUBSCRIBE_USER_ID;

	private String SUBSCRIBE_USER_EMAIL;

	/** The value for the USER_ID field */
	private Integer SUBSCRIBE_VEHICLE_ID;

	private String SUBSCRIBE_VEHICLE_NAME;

	/** The value for the name field SUBSCRIBE_TYPE */
	private String SUBSCRIBE_TYPE;

	/** The value for the name field SUBSCRIBE_TYPE */
	private String SUBSCRIBE_SUBTYPE;

	/** The value for the name field SUBSCRIBE_DATE */
	private String SUBSCRIBE_DATE;

	/** The value for the name field SUBSCRIBE_Threshold_date */
	private String SUBSCRIBE_THRESHOLD_DATE;

	/** The value for the name field SUBSCRIBE_LOCATION */
	private String SUBSCRIBE_LOCATION;
	
	private Long SUBSCRIBE_LOCATION_ID;

	public SubscribeBoxDto() {
		super();
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
	public String getSUBSCRIBE_DATE() {
		return SUBSCRIBE_DATE;
	}

	/**
	 * @param sUBSCRIBE_DATE
	 *            the sUBSCRIBE_DATE to set
	 */
	public void setSUBSCRIBE_DATE(String sUBSCRIBE_DATE) {
		SUBSCRIBE_DATE = sUBSCRIBE_DATE;
	}

	/**
	 * @return the sUBSCRIBE_THRESHOLD_DATE
	 */
	public String getSUBSCRIBE_THRESHOLD_DATE() {
		return SUBSCRIBE_THRESHOLD_DATE;
	}

	/**
	 * @param sUBSCRIBE_THRESHOLD_DATE
	 *            the sUBSCRIBE_THRESHOLD_DATE to set
	 */
	public void setSUBSCRIBE_THRESHOLD_DATE(String sUBSCRIBE_THRESHOLD_DATE) {
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
		builder.append("]");
		return builder.toString();
	}

}
