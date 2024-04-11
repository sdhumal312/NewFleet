/**
 * 
 */
package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author fleetop
 *
 */
@Entity
@Table(name = "EmployeeDetails")
public class EmployeeDetails implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value for the EMPID Layout Id field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMPID")
	private Long EMPID;

	/** The value for the EMP_FIRSTNAME field */
	@Column(name = "EMP_FIRSTNAME", nullable = false, length = 100)
	private String EMP_FIRSTNAME;

	/** The value for the EMP_FIRSTNAME field */
	@Column(name = "EMP_LASTNAME", nullable = false, length = 100)
	private String EMP_LASTNAME;

	/** The value for the EMP_NUMBER Number field */
	@Column(name = "EMP_NUMBER", nullable = false, unique = true, length = 25)
	private String EMP_NUMBER;

	/** The value for the EMP_ADDRESS Number field */
	@Column(name = "EMP_ADDRESS", length = 250)
	private String EMP_ADDRESS;

	/** The value for the EMP_STREET Number field */
	@Column(name = "EMP_STREET", length = 250)
	private String EMP_STREET;

	/** The value for the EMP_CITY Number field */
	@Column(name = "EMP_CITY", length = 150)
	private String EMP_CITY;

	/** The value for the EMP_CITY Number field */
	@Column(name = "EMP_STATE", length = 150)
	private String EMP_STATE;

	/** The value for the EMP_COUNTRY Number field */
	@Column(name = "EMP_COUNTRY", length = 150)
	private String EMP_COUNTRY;

	/** The value for the EMP_COUNTRY Number field */
	@Column(name = "EMP_PINCODE", length = 6)
	private Integer EMP_PINCODE;

	/** The value for the EMP_COUNTRY Number field */
	@Column(name = "EMP_LANDMARK", length = 250)
	private String EMP_LANDMARK;

	/** The value for the EMP_EMAIL Number field */
	@Column(name = "EMP_EMAIL", length = 150)
	private String EMP_EMAIL;

	/** The value for the EMP_MOBILE Number field */
	@Column(name = "EMP_MOBILE", length = 15)
	private String EMP_MOBILE;

	/** The value for the EMP_ALTERMOBILE Number field */
	@Column(name = "EMP_ALTERMOBILE", length = 15)
	private String EMP_ALTERMOBILE;

	/** The value for the EMP_ALTERMOBILE Number field */
	@Column(name = "EMP_COMPANY", length = 500)
	private String EMP_COMPANY;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	@Column(name = "CREATED", nullable = false, updatable = false)
	private Date CREATED;

	@Column(name = "LASTUPDATED", nullable = false)
	private Date LASTUPDATED;

	public EmployeeDetails() {
		super();
	}

	public EmployeeDetails(Long eMPID, String eMP_FIRSTNAME, String eMP_LASTNAME, String eMP_NUMBER, String eMP_ADDRESS,
			String eMP_STREET, String eMP_CITY, String eMP_STATE, String eMP_COUNTRY, Integer eMP_PINCODE,
			String eMP_LANDMARK, String eMP_EMAIL, String eMP_MOBILE, String eMP_ALTERMOBILE, String eMP_COMPANY,
			boolean MarkForDelete, Date cREATED, Date lASTUPDATED) {
		super();
		EMPID = eMPID;
		EMP_FIRSTNAME = eMP_FIRSTNAME;
		EMP_LASTNAME = eMP_LASTNAME;
		EMP_NUMBER = eMP_NUMBER;
		EMP_ADDRESS = eMP_ADDRESS;
		EMP_STREET = eMP_STREET;
		EMP_CITY = eMP_CITY;
		EMP_STATE = eMP_STATE;
		EMP_COUNTRY = eMP_COUNTRY;
		EMP_PINCODE = eMP_PINCODE;
		EMP_LANDMARK = eMP_LANDMARK;
		EMP_EMAIL = eMP_EMAIL;
		EMP_MOBILE = eMP_MOBILE;
		EMP_ALTERMOBILE = eMP_ALTERMOBILE;
		EMP_COMPANY = eMP_COMPANY;
		markForDelete = MarkForDelete;
		CREATED = cREATED;
		LASTUPDATED = lASTUPDATED;
	}

	/**
	 * @return the eMPID
	 */
	public Long getEMPID() {
		return EMPID;
	}

	/**
	 * @param eMPID
	 *            the eMPID to set
	 */
	public void setEMPID(Long eMPID) {
		EMPID = eMPID;
	}

	/**
	 * @return the eMP_FIRSTNAME
	 */
	public String getEMP_FIRSTNAME() {
		return EMP_FIRSTNAME;
	}

	/**
	 * @param eMP_FIRSTNAME
	 *            the eMP_FIRSTNAME to set
	 */
	public void setEMP_FIRSTNAME(String eMP_FIRSTNAME) {
		EMP_FIRSTNAME = eMP_FIRSTNAME;
	}

	/**
	 * @return the eMP_LASTNAME
	 */
	public String getEMP_LASTNAME() {
		return EMP_LASTNAME;
	}

	/**
	 * @param eMP_LASTNAME
	 *            the eMP_LASTNAME to set
	 */
	public void setEMP_LASTNAME(String eMP_LASTNAME) {
		EMP_LASTNAME = eMP_LASTNAME;
	}

	/**
	 * @return the eMP_NUMBER
	 */
	public String getEMP_NUMBER() {
		return EMP_NUMBER;
	}

	/**
	 * @param eMP_NUMBER
	 *            the eMP_NUMBER to set
	 */
	public void setEMP_NUMBER(String eMP_NUMBER) {
		EMP_NUMBER = eMP_NUMBER;
	}

	/**
	 * @return the eMP_ADDRESS
	 */
	public String getEMP_ADDRESS() {
		return EMP_ADDRESS;
	}

	/**
	 * @param eMP_ADDRESS
	 *            the eMP_ADDRESS to set
	 */
	public void setEMP_ADDRESS(String eMP_ADDRESS) {
		EMP_ADDRESS = eMP_ADDRESS;
	}

	/**
	 * @return the eMP_STREET
	 */
	public String getEMP_STREET() {
		return EMP_STREET;
	}

	/**
	 * @param eMP_STREET
	 *            the eMP_STREET to set
	 */
	public void setEMP_STREET(String eMP_STREET) {
		EMP_STREET = eMP_STREET;
	}

	/**
	 * @return the eMP_CITY
	 */
	public String getEMP_CITY() {
		return EMP_CITY;
	}

	/**
	 * @param eMP_CITY
	 *            the eMP_CITY to set
	 */
	public void setEMP_CITY(String eMP_CITY) {
		EMP_CITY = eMP_CITY;
	}

	/**
	 * @return the eMP_STATE
	 */
	public String getEMP_STATE() {
		return EMP_STATE;
	}

	/**
	 * @param eMP_STATE
	 *            the eMP_STATE to set
	 */
	public void setEMP_STATE(String eMP_STATE) {
		EMP_STATE = eMP_STATE;
	}

	/**
	 * @return the eMP_COUNTRY
	 */
	public String getEMP_COUNTRY() {
		return EMP_COUNTRY;
	}

	/**
	 * @param eMP_COUNTRY
	 *            the eMP_COUNTRY to set
	 */
	public void setEMP_COUNTRY(String eMP_COUNTRY) {
		EMP_COUNTRY = eMP_COUNTRY;
	}

	/**
	 * @return the eMP_PINCODE
	 */
	public Integer getEMP_PINCODE() {
		return EMP_PINCODE;
	}

	/**
	 * @param eMP_PINCODE
	 *            the eMP_PINCODE to set
	 */
	public void setEMP_PINCODE(Integer eMP_PINCODE) {
		EMP_PINCODE = eMP_PINCODE;
	}

	/**
	 * @return the eMP_LANDMARK
	 */
	public String getEMP_LANDMARK() {
		return EMP_LANDMARK;
	}

	/**
	 * @param eMP_LANDMARK
	 *            the eMP_LANDMARK to set
	 */
	public void setEMP_LANDMARK(String eMP_LANDMARK) {
		EMP_LANDMARK = eMP_LANDMARK;
	}

	/**
	 * @return the eMP_EMAIL
	 */
	public String getEMP_EMAIL() {
		return EMP_EMAIL;
	}

	/**
	 * @param eMP_EMAIL
	 *            the eMP_EMAIL to set
	 */
	public void setEMP_EMAIL(String eMP_EMAIL) {
		EMP_EMAIL = eMP_EMAIL;
	}

	/**
	 * @return the eMP_MOBILE
	 */
	public String getEMP_MOBILE() {
		return EMP_MOBILE;
	}

	/**
	 * @param eMP_MOBILE
	 *            the eMP_MOBILE to set
	 */
	public void setEMP_MOBILE(String eMP_MOBILE) {
		EMP_MOBILE = eMP_MOBILE;
	}

	/**
	 * @return the eMP_ALTERMOBILE
	 */
	public String getEMP_ALTERMOBILE() {
		return EMP_ALTERMOBILE;
	}

	/**
	 * @param eMP_ALTERMOBILE
	 *            the eMP_ALTERMOBILE to set
	 */
	public void setEMP_ALTERMOBILE(String eMP_ALTERMOBILE) {
		EMP_ALTERMOBILE = eMP_ALTERMOBILE;
	}

	/**
	 * @return the eMP_COMPANY
	 */
	public String getEMP_COMPANY() {
		return EMP_COMPANY;
	}

	/**
	 * @param eMP_COMPANY
	 *            the eMP_COMPANY to set
	 */
	public void setEMP_COMPANY(String eMP_COMPANY) {
		EMP_COMPANY = eMP_COMPANY;
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((EMPID == null) ? 0 : EMPID.hashCode());
		result = prime * result + ((EMP_EMAIL == null) ? 0 : EMP_EMAIL.hashCode());
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
		EmployeeDetails other = (EmployeeDetails) obj;
		if (EMPID == null) {
			if (other.EMPID != null)
				return false;
		} else if (!EMPID.equals(other.EMPID))
			return false;
		if (EMP_EMAIL == null) {
			if (other.EMP_EMAIL != null)
				return false;
		} else if (!EMP_EMAIL.equals(other.EMP_EMAIL))
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
		builder.append("EmployeeDetails [EMPID=");
		builder.append(EMPID);
		builder.append(", EMP_FIRSTNAME=");
		builder.append(EMP_FIRSTNAME);
		builder.append(", EMP_LASTNAME=");
		builder.append(EMP_LASTNAME);
		builder.append(", EMP_NUMBER=");
		builder.append(EMP_NUMBER);
		builder.append(", EMP_ADDRESS=");
		builder.append(EMP_ADDRESS);
		builder.append(", EMP_STREET=");
		builder.append(EMP_STREET);
		builder.append(", EMP_CITY=");
		builder.append(EMP_CITY);
		builder.append(", EMP_STATE=");
		builder.append(EMP_STATE);
		builder.append(", EMP_COUNTRY=");
		builder.append(EMP_COUNTRY);
		builder.append(", EMP_PINCODE=");
		builder.append(EMP_PINCODE);
		builder.append(", EMP_LANDMARK=");
		builder.append(EMP_LANDMARK);
		builder.append(", EMP_EMAIL=");
		builder.append(EMP_EMAIL);
		builder.append(", EMP_MOBILE=");
		builder.append(EMP_MOBILE);
		builder.append(", EMP_ALTERMOBILE=");
		builder.append(EMP_ALTERMOBILE);
		builder.append(", EMP_COMPANY=");
		builder.append(EMP_COMPANY);
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
