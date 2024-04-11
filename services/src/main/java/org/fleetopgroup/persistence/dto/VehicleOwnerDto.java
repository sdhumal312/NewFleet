package org.fleetopgroup.persistence.dto;

public class VehicleOwnerDto {
	/*** Vehicle Owner Details ID **/
	private Integer VOID;

	/*** Vehicle Owner Details ID **/
	private Integer VEHID;

	/*** Vehicle Owner name Details **/
	private String VEH_OWNER_NAME;

	/*** Vehicle Owner Serial No Details **/
	private String VEH_OWNER_SERIAL;

	/*** Vehicle Owner Aadhar No Details **/
	private String VEH_OWNER_AADHARNO;

	/*** VehicleOwner Pan No Details **/
	private String VEH_OWNER_PANNO;

	/*** VehicleOwner Pan No Details **/
	private String VEH_OWNER_ADDRESS;

	/*** VehicleOwner City Details **/
	private String VEH_OWNER_CITY;

	/*** VehicleOwner State Details **/
	private String VEH_OWNER_STATE;

	/*** VehicleOwner Country Details **/
	private String VEH_OWNER_COUNTRY;

	/*** VehicleOwner Country Code Details **/
	private String VEH_OWNER_PINCODE;

	/*** VehicleOwner Phone Details **/
	private String VEH_OWNER_PHONE;

	/*** VehicleOwner Driver Name Details **/
	private String VEH_DRIVER_NAME;

	/*** VehicleOwner Driver Details **/
	private String VEH_DRIVER_PHONE;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;

	boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	private String CREATED_DATE;

	/** The value for the ISSUES_Due DATE field */
	private String LASTUPDATED_DATE;

	public VehicleOwnerDto() {
		super();
	}

	public VehicleOwnerDto(Integer vOID, Integer vEHID, String vEH_OWNER_NAME, String vEH_OWNER_SERIAL,
			String vEH_OWNER_AADHARNO, String vEH_OWNER_PANNO, String vEH_OWNER_ADDRESS, String vEH_OWNER_CITY,
			String vEH_OWNER_STATE, String vEH_OWNER_COUNTRY, String vEH_OWNER_PINCODE, String vEH_OWNER_PHONE,
			String vEH_DRIVER_NAME, String vEH_DRIVER_PHONE, String cREATEDBY, String lASTMODIFIEDBY, boolean MarkForDelete,
			String cREATED_DATE, String lASTUPDATED_DATE) {
		super();
		VOID = vOID;
		VEHID = vEHID;
		VEH_OWNER_NAME = vEH_OWNER_NAME;
		VEH_OWNER_SERIAL = vEH_OWNER_SERIAL;
		VEH_OWNER_AADHARNO = vEH_OWNER_AADHARNO;
		VEH_OWNER_PANNO = vEH_OWNER_PANNO;
		VEH_OWNER_ADDRESS = vEH_OWNER_ADDRESS;
		VEH_OWNER_CITY = vEH_OWNER_CITY;
		VEH_OWNER_STATE = vEH_OWNER_STATE;
		VEH_OWNER_COUNTRY = vEH_OWNER_COUNTRY;
		VEH_OWNER_PINCODE = vEH_OWNER_PINCODE;
		VEH_OWNER_PHONE = vEH_OWNER_PHONE;
		VEH_DRIVER_NAME = vEH_DRIVER_NAME;
		VEH_DRIVER_PHONE = vEH_DRIVER_PHONE;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	/**
	 * @return the vOID
	 */
	public Integer getVOID() {
		return VOID;
	}

	/**
	 * @param vOID
	 *            the vOID to set
	 */
	public void setVOID(Integer vOID) {
		VOID = vOID;
	}

	/**
	 * @return the vEHID
	 */
	public Integer getVEHID() {
		return VEHID;
	}

	/**
	 * @param vEHID
	 *            the vEHID to set
	 */
	public void setVEHID(Integer vEHID) {
		VEHID = vEHID;
	}

	/**
	 * @return the vEH_OWNER_NAME
	 */
	public String getVEH_OWNER_NAME() {
		return VEH_OWNER_NAME;
	}

	/**
	 * @param vEH_OWNER_NAME
	 *            the vEH_OWNER_NAME to set
	 */
	public void setVEH_OWNER_NAME(String vEH_OWNER_NAME) {
		VEH_OWNER_NAME = vEH_OWNER_NAME;
	}

	/**
	 * @return the vEH_OWNER_SERIAL
	 */
	public String getVEH_OWNER_SERIAL() {
		return VEH_OWNER_SERIAL;
	}

	/**
	 * @param vEH_OWNER_SERIAL
	 *            the vEH_OWNER_SERIAL to set
	 */
	public void setVEH_OWNER_SERIAL(String vEH_OWNER_SERIAL) {
		VEH_OWNER_SERIAL = vEH_OWNER_SERIAL;
	}

	/**
	 * @return the vEH_OWNER_AADHARNO
	 */
	public String getVEH_OWNER_AADHARNO() {
		return VEH_OWNER_AADHARNO;
	}

	/**
	 * @param vEH_OWNER_AADHARNO
	 *            the vEH_OWNER_AADHARNO to set
	 */
	public void setVEH_OWNER_AADHARNO(String vEH_OWNER_AADHARNO) {
		VEH_OWNER_AADHARNO = vEH_OWNER_AADHARNO;
	}

	/**
	 * @return the vEH_OWNER_PANNO
	 */
	public String getVEH_OWNER_PANNO() {
		return VEH_OWNER_PANNO;
	}

	/**
	 * @param vEH_OWNER_PANNO
	 *            the vEH_OWNER_PANNO to set
	 */
	public void setVEH_OWNER_PANNO(String vEH_OWNER_PANNO) {
		VEH_OWNER_PANNO = vEH_OWNER_PANNO;
	}

	/**
	 * @return the vEH_OWNER_ADDRESS
	 */
	public String getVEH_OWNER_ADDRESS() {
		return VEH_OWNER_ADDRESS;
	}

	/**
	 * @param vEH_OWNER_ADDRESS
	 *            the vEH_OWNER_ADDRESS to set
	 */
	public void setVEH_OWNER_ADDRESS(String vEH_OWNER_ADDRESS) {
		VEH_OWNER_ADDRESS = vEH_OWNER_ADDRESS;
	}

	/**
	 * @return the vEH_OWNER_CITY
	 */
	public String getVEH_OWNER_CITY() {
		return VEH_OWNER_CITY;
	}

	/**
	 * @param vEH_OWNER_CITY
	 *            the vEH_OWNER_CITY to set
	 */
	public void setVEH_OWNER_CITY(String vEH_OWNER_CITY) {
		VEH_OWNER_CITY = vEH_OWNER_CITY;
	}

	/**
	 * @return the vEH_OWNER_STATE
	 */
	public String getVEH_OWNER_STATE() {
		return VEH_OWNER_STATE;
	}

	/**
	 * @param vEH_OWNER_STATE
	 *            the vEH_OWNER_STATE to set
	 */
	public void setVEH_OWNER_STATE(String vEH_OWNER_STATE) {
		VEH_OWNER_STATE = vEH_OWNER_STATE;
	}

	/**
	 * @return the vEH_OWNER_COUNTRY
	 */
	public String getVEH_OWNER_COUNTRY() {
		return VEH_OWNER_COUNTRY;
	}

	/**
	 * @param vEH_OWNER_COUNTRY
	 *            the vEH_OWNER_COUNTRY to set
	 */
	public void setVEH_OWNER_COUNTRY(String vEH_OWNER_COUNTRY) {
		VEH_OWNER_COUNTRY = vEH_OWNER_COUNTRY;
	}

	/**
	 * @return the vEH_OWNER_PINCODE
	 */
	public String getVEH_OWNER_PINCODE() {
		return VEH_OWNER_PINCODE;
	}

	/**
	 * @param vEH_OWNER_PINCODE
	 *            the vEH_OWNER_PINCODE to set
	 */
	public void setVEH_OWNER_PINCODE(String vEH_OWNER_PINCODE) {
		VEH_OWNER_PINCODE = vEH_OWNER_PINCODE;
	}

	/**
	 * @return the vEH_OWNER_PHONE
	 */
	public String getVEH_OWNER_PHONE() {
		return VEH_OWNER_PHONE;
	}

	/**
	 * @param vEH_OWNER_PHONE
	 *            the vEH_OWNER_PHONE to set
	 */
	public void setVEH_OWNER_PHONE(String vEH_OWNER_PHONE) {
		VEH_OWNER_PHONE = vEH_OWNER_PHONE;
	}

	/**
	 * @return the vEH_DRIVER_NAME
	 */
	public String getVEH_DRIVER_NAME() {
		return VEH_DRIVER_NAME;
	}

	/**
	 * @param vEH_DRIVER_NAME
	 *            the vEH_DRIVER_NAME to set
	 */
	public void setVEH_DRIVER_NAME(String vEH_DRIVER_NAME) {
		VEH_DRIVER_NAME = vEH_DRIVER_NAME;
	}

	/**
	 * @return the vEH_DRIVER_PHONE
	 */
	public String getVEH_DRIVER_PHONE() {
		return VEH_DRIVER_PHONE;
	}

	/**
	 * @param vEH_DRIVER_PHONE
	 *            the vEH_DRIVER_PHONE to set
	 */
	public void setVEH_DRIVER_PHONE(String vEH_DRIVER_PHONE) {
		VEH_DRIVER_PHONE = vEH_DRIVER_PHONE;
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
	 * @return the cREATED_DATE
	 */
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	/**
	 * @param cREATED_DATE
	 *            the cREATED_DATE to set
	 */
	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the lASTUPDATED_DATE
	 */
	public String getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	/**
	 * @param lASTUPDATED_DATE
	 *            the lASTUPDATED_DATE to set
	 */
	public void setLASTUPDATED_DATE(String lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
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
		result = prime * result + ((VEHID == null) ? 0 : VEHID.hashCode());
		result = prime * result + ((VOID == null) ? 0 : VOID.hashCode());
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
		VehicleOwnerDto other = (VehicleOwnerDto) obj;
		if (VEHID == null) {
			if (other.VEHID != null)
				return false;
		} else if (!VEHID.equals(other.VEHID))
			return false;
		if (VOID == null) {
			if (other.VOID != null)
				return false;
		} else if (!VOID.equals(other.VOID))
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
		builder.append("VehicleOwner [VOID=");
		builder.append(VOID);
		builder.append(", VEHID=");
		builder.append(VEHID);
		builder.append(", VEH_OWNER_NAME=");
		builder.append(VEH_OWNER_NAME);
		builder.append(", VEH_OWNER_SERIAL=");
		builder.append(VEH_OWNER_SERIAL);
		builder.append(", VEH_OWNER_AADHARNO=");
		builder.append(VEH_OWNER_AADHARNO);
		builder.append(", VEH_OWNER_PANNO=");
		builder.append(VEH_OWNER_PANNO);
		builder.append(", VEH_OWNER_ADDRESS=");
		builder.append(VEH_OWNER_ADDRESS);
		builder.append(", VEH_OWNER_CITY=");
		builder.append(VEH_OWNER_CITY);
		builder.append(", VEH_OWNER_STATE=");
		builder.append(VEH_OWNER_STATE);
		builder.append(", VEH_OWNER_COUNTRY=");
		builder.append(VEH_OWNER_COUNTRY);
		builder.append(", VEH_OWNER_PINCODE=");
		builder.append(VEH_OWNER_PINCODE);
		builder.append(", VEH_OWNER_PHONE=");
		builder.append(VEH_OWNER_PHONE);
		builder.append(", VEH_DRIVER_NAME=");
		builder.append(VEH_DRIVER_NAME);
		builder.append(", VEH_DRIVER_PHONE=");
		builder.append(VEH_DRIVER_PHONE);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append("]");
		return builder.toString();
	}

}