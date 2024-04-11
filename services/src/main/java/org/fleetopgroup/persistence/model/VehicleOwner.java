package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
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

@Entity
@Table(name = "VehicleOwner")
public class VehicleOwner implements Serializable {
	private static final long serialVersionUID = 1L;
	/*** Vehicle Owner Details ID **/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VOID")
	private Integer VOID;

	/*** Vehicle Owner Details ID **/
	@Column(name = "VEHID")
	private Integer VEHID;

	/*** Vehicle Owner name Details **/
	@Column(name = "VEH_OWNER_NAME", length = 150)
	private String VEH_OWNER_NAME;

	/*** Vehicle Owner Serial No Details **/
	@Column(name = "VEH_OWNER_SERIAL", length = 25)
	private String VEH_OWNER_SERIAL;

	/*** Vehicle Owner Aadhar No Details **/
	@Column(name = "VEH_OWNER_AADHARNO", length = 25)
	private String VEH_OWNER_AADHARNO;

	/*** VehicleOwner Pan No Details **/
	@Column(name = "VEH_OWNER_PANNO", length = 25)
	private String VEH_OWNER_PANNO;

	/*** VehicleOwner Pan No Details **/
	@Column(name = "VEH_OWNER_ADDRESS", length = 200)
	private String VEH_OWNER_ADDRESS;

	/*** VehicleOwner City Details **/
	@Column(name = "VEH_OWNER_CITY", length = 100)
	private String VEH_OWNER_CITY;

	/*** VehicleOwner State Details **/
	@Column(name = "VEH_OWNER_STATE", length = 100)
	private String VEH_OWNER_STATE;

	/*** VehicleOwner Country Details **/
	@Column(name = "VEH_OWNER_COUNTRY", length = 25)
	private String VEH_OWNER_COUNTRY;

	/*** VehicleOwner Country Code Details **/
	@Column(name = "VEH_OWNER_PINCODE", length = 6)
	private String VEH_OWNER_PINCODE;

	/*** VehicleOwner Phone Details **/
	@Column(name = "VEH_OWNER_PHONE", length = 15)
	private String VEH_OWNER_PHONE;

	/*** VehicleOwner Driver Name Details **/
	@Column(name = "VEH_DRIVER_NAME", length = 100)
	private String VEH_DRIVER_NAME;

	/*** VehicleOwner Driver Details **/
	@Column(name = "VEH_DRIVER_PHONE", length = 15)
	private String VEH_DRIVER_PHONE;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	/*@Column(name = "CREATEDBY", updatable = false, length = 150)
	private String CREATEDBY;

	@Column(name = "LASTMODIFIEDBY", length = 150)
	private String LASTMODIFIEDBY;*/
	
	@Column(name = "CREATEDBYID", updatable = false)
	private Long CREATEDBYID;
	
	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "LASTUPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date LASTUPDATED_DATE;

	public VehicleOwner() {
		super();
	}

	public VehicleOwner(Integer vOID, Integer vEHID, String vEH_OWNER_NAME, String vEH_OWNER_SERIAL,
			String vEH_OWNER_AADHARNO, String vEH_OWNER_PANNO, String vEH_OWNER_ADDRESS, String vEH_OWNER_CITY,
			String vEH_OWNER_STATE, String vEH_OWNER_COUNTRY, String vEH_OWNER_PINCODE, String vEH_OWNER_PHONE,
			String vEH_DRIVER_NAME, String vEH_DRIVER_PHONE, String cREATEDBY, String lASTMODIFIEDBY, boolean MarkForDelete,
			Date cREATED_DATE, Date lASTUPDATED_DATE, Integer cOMPANY_ID) {
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
		//CREATEDBY = cREATEDBY;
		//LASTMODIFIEDBY = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
		COMPANY_ID	= cOMPANY_ID;
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

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/**
	 * @return the cREATEDBY
	 *//*
	public String getCREATEDBY() {
		return CREATEDBY;
	}

	*//**
	 * @param cREATEDBY
	 *            the cREATEDBY to set
	 *//*
	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	*//**
	 * @return the lASTMODIFIEDBY
	 *//*
	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	*//**
	 * @param lASTMODIFIEDBY
	 *            the lASTMODIFIEDBY to set
	 *//*
	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}
*/
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
	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}

	/**
	 * @param cREATED_DATE
	 *            the cREATED_DATE to set
	 */
	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the lASTUPDATED_DATE
	 */
	public Date getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	/**
	 * @param lASTUPDATED_DATE
	 *            the lASTUPDATED_DATE to set
	 */
	public void setLASTUPDATED_DATE(Date lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
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
		VehicleOwner other = (VehicleOwner) obj;
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
		builder.append(", CREATEDBYID=");
		builder.append(CREATEDBYID);
		builder.append(", LASTMODIFIEDBYID=");
		builder.append(LASTMODIFIEDBYID);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append("]");
		return builder.toString();
	}

}