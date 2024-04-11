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
@Table(name = "DriverFamily")
public class DriverFamily implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	/** The value for the DFID field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DFID")
	private long DFID;

	/** The value for the DRIVERID field */
	@Column(name = "DRIVERID")
	private int DRIVERID;

	/** The value for the Driver family member name field */
	@Column(name = "DF_NAME", nullable = false, length = 150)
	private String DF_NAME;
/*
	*//** The value for the Driver family Relation ship field *//*
	@Column(name = "DF_RELATIONSHIP", length = 25)
	private String DF_RELATIONSHIP;*/
	
	@Column(name = "DF_RELATIONSHIP_ID")
	private short DF_RELATIONSHIP_ID;

	/** The value for the Driver family gender field *//*
	@Column(name = "DF_SEX", length = 25)
	private String DF_SEX;*/
	
	@Column(name = "DF_SEX_ID")
	private short DF_SEX_ID;

	/** The value for the Driver family age field */
	@Column(name = "DF_AGE")
	private Integer DF_AGE;
	
	@Column(name = "COMPANY_ID", nullable = false)
	Integer COMPANY_ID;

	@Column(name = "CREATEDBYID", updatable = false, length = 150)
	private Long CREATEDBYID;

	@Column(name = "LASTMODIFIEDBYID", length = 150)
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

	public DriverFamily() {
		super();
	}

	public DriverFamily(long dFID, int dRIVERID, String dF_NAME, short dF_RELATIONSHIP, short dF_SEX, Integer dF_AGE,
			Long cREATEDBY, Long lASTMODIFIEDBY, boolean MarkForDelete, Date cREATED_DATE, Date lASTUPDATED_DATE) {
		super();
		DFID = dFID;
		DRIVERID = dRIVERID;
		DF_NAME = dF_NAME;
		DF_RELATIONSHIP_ID = dF_RELATIONSHIP;
		DF_SEX_ID = dF_SEX;
		DF_AGE = dF_AGE;
		CREATEDBYID = cREATEDBY;
		LASTMODIFIEDBYID = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	public DriverFamily(int dRIVERID, String dF_NAME, short dF_RELATIONSHIP, short dF_SEX, Integer dF_AGE) {
		super();
		DRIVERID = dRIVERID;
		DF_NAME = dF_NAME;
		DF_RELATIONSHIP_ID = dF_RELATIONSHIP;
		DF_SEX_ID = dF_SEX;
		DF_AGE = dF_AGE;
	}

	/**
	 * @return the dFID
	 */
	public long getDFID() {
		return DFID;
	}

	/**
	 * @param dFID
	 *            the dFID to set
	 */
	public void setDFID(long dFID) {
		DFID = dFID;
	}

	/**
	 * @return the dRIVERID
	 */
	public int getDRIVERID() {
		return DRIVERID;
	}

	/**
	 * @param dRIVERID
	 *            the dRIVERID to set
	 */
	public void setDRIVERID(int dRIVERID) {
		DRIVERID = dRIVERID;
	}

	/**
	 * @return the dF_NAME
	 */
	public String getDF_NAME() {
		return DF_NAME;
	}

	/**
	 * @param dF_NAME
	 *            the dF_NAME to set
	 */
	public void setDF_NAME(String dF_NAME) {
		DF_NAME = dF_NAME;
	}

	/**
	 * @return the dF_RELATIONSHIP
	 *//*
	public String getDF_RELATIONSHIP() {
		return DF_RELATIONSHIP;
	}

	*//**
	 * @param dF_RELATIONSHIP
	 *            the dF_RELATIONSHIP to set
	 *//*
	public void setDF_RELATIONSHIP(String dF_RELATIONSHIP) {
		DF_RELATIONSHIP = dF_RELATIONSHIP;
	}

	*//**
	 * @return the dF_SEX
	 *//*
	public String getDF_SEX() {
		return DF_SEX;
	}

	*//**
	 * @param dF_SEX
	 *            the dF_SEX to set
	 *//*
	public void setDF_SEX(String dF_SEX) {
		DF_SEX = dF_SEX;
	}
*/
	/**
	 * @return the dF_AGE
	 */
	public Integer getDF_AGE() {
		return DF_AGE;
	}

	/**
	 * @param dF_AGE
	 *            the dF_AGE to set
	 */
	public void setDF_AGE(Integer dF_AGE) {
		DF_AGE = dF_AGE;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
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
	 * @return the dF_RELATIONSHIP_ID
	 */
	public short getDF_RELATIONSHIP_ID() {
		return DF_RELATIONSHIP_ID;
	}

	/**
	 * @param dF_RELATIONSHIP_ID the dF_RELATIONSHIP_ID to set
	 */
	public void setDF_RELATIONSHIP_ID(short dF_RELATIONSHIP_ID) {
		DF_RELATIONSHIP_ID = dF_RELATIONSHIP_ID;
	}

	/**
	 * @return the dF_SEX_ID
	 */
	public short getDF_SEX_ID() {
		return DF_SEX_ID;
	}

	/**
	 * @param dF_SEX_ID the dF_SEX_ID to set
	 */
	public void setDF_SEX_ID(short dF_SEX_ID) {
		DF_SEX_ID = dF_SEX_ID;
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
		result = prime * result + (int) (DFID ^ (DFID >>> 32));
		result = prime * result + ((DF_NAME == null) ? 0 : DF_NAME.hashCode());
		result = prime * result + DRIVERID;
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
		DriverFamily other = (DriverFamily) obj;
		if (DFID != other.DFID)
			return false;
		if (DF_NAME == null) {
			if (other.DF_NAME != null)
				return false;
		} else if (!DF_NAME.equals(other.DF_NAME))
			return false;
		if (DRIVERID != other.DRIVERID)
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
		builder.append("DriverFamily [DFID=");
		builder.append(DFID);
		builder.append(", DRIVERID=");
		builder.append(DRIVERID);
		builder.append(", DF_NAME=");
		builder.append(DF_NAME);
		builder.append(", DF_RELATIONSHIP_ID=");
		builder.append(DF_RELATIONSHIP_ID);
		builder.append(", DF_SEX_ID=");
		builder.append(DF_SEX_ID);
		builder.append(", DF_AGE=");
		builder.append(DF_AGE);
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
		builder.append("]");
		return builder.toString();
	}

}
