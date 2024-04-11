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
@Table(name = "DriverAttendance")
public class DriverAttendance implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	/** The value for the DRIVERATTENDANCE ID field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DAID")
	private Long DAID;

	/** The value for the DRIVER ID field */
	@Column(name = "DRIVERID", nullable = false)
	private int DRIVERID;

	/**
	 * The value for the DRIVER to create ATTENDANCE name one user name field
	 */
	@Basic(optional = false)
	@Column(name = "ATTENDANCE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ATTENDANCE_DATE;

	/** The value for the DRIVER TRIP ID field */
	@Column(name = "TRIPSHEETID", nullable = false)
	private Long TRIPSHEETID;
	
	/** The value for the DRIVER TRIP ID field */
	@Column(name = "DHID", nullable = false)
	private Long DHID;
	
	@Column(name = "TRIP_ROUTE_ID")
	private Integer TRIP_ROUTE_ID;

	/** The value for the STATUS NAME field */
	@Column(name = "ATTENDANCE_STATUS_ID")
	private short ATTENDANCE_STATUS_ID;

	/** The value for the DRIVER_POINT field */
	@Column(name = "DRIVER_POINT")
	private Double DRIVER_POINT;

	/** The value for the POINT_TYPE eg: TS, Halt field */
	@Column(name = "POINT_TYPE_ID")
	private short POINT_TYPE_ID;

	/** The value for the POINT_STATUS NAME field */
	@Column(name = "POINT_STATUS_ID")
	private short POINT_STATUS_ID;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	
	@Column(name = "CREATED_BY_ID", nullable = false)
	private Long CREATED_BY_ID;
	
	@Column(name = "LASTUPDATED_BY_ID", nullable = false)
	private Long LASTUPDATED_BY_ID;

	/** The value for the STATUS NAME field */
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the CREATED NAME field */
	@Column(name = "CREATED", updatable = false)
	private Date CREATED;

	/** The value for the LASTUPDATED NAME field */
	@Column(name = "LASTUPDATED")
	private Date LASTUPDATED;
	

	public DriverAttendance() {
		super();
	}

	public DriverAttendance(Long dAID, int dRIVERID,  Date aTTENDANCE_DATE, Long tRIPSHEETID,
			Integer tRIP_ROUTE_ID, boolean MarkForDelete,
			Date cREATED, Date lASTUPDATED, Integer companyId) {
		super();
		DAID = dAID;
		DRIVERID = dRIVERID;
		ATTENDANCE_DATE = aTTENDANCE_DATE;
		TRIPSHEETID = tRIPSHEETID;
		TRIP_ROUTE_ID = tRIP_ROUTE_ID;
		markForDelete = MarkForDelete;
		CREATED = cREATED;
		LASTUPDATED = lASTUPDATED;
		COMPANY_ID = companyId;
	}

	
	/**
	 * @return the dAID
	 */
	public Long getDAID() {
		return DAID;
	}

	/**
	 * @param dAID
	 *            the dAID to set
	 */
	public void setDAID(Long dAID) {
		DAID = dAID;
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
	 * @return the aTTENDANCE_DATE
	 */
	public Date getATTENDANCE_DATE() {
		return ATTENDANCE_DATE;
	}

	/**
	 * @param aTTENDANCE_DATE
	 *            the aTTENDANCE_DATE to set
	 */
	public void setATTENDANCE_DATE(Date aTTENDANCE_DATE) {
		ATTENDANCE_DATE = aTTENDANCE_DATE;
	}

	/**
	 * @return the tRIPSHEETID
	 */
	public Long getTRIPSHEETID() {
		return TRIPSHEETID;
	}

	/**
	 * @param tRIPSHEETID
	 *            the tRIPSHEETID to set
	 */
	public void setTRIPSHEETID(Long tRIPSHEETID) {
		TRIPSHEETID = tRIPSHEETID;
	}

	
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
	public void setMarkForDelete(boolean MarkForDelete) {
		this.markForDelete = MarkForDelete;
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

	/**
	 * @return the dRIVER_POINT
	 */
	public Double getDRIVER_POINT() {
		return DRIVER_POINT;
	}

	/**
	 * @param dRIVER_POINT
	 *            the dRIVER_POINT to set
	 */
	public void setDRIVER_POINT(Double dRIVER_POINT) {
		DRIVER_POINT = dRIVER_POINT;
	}

	

	public Integer getTRIP_ROUTE_ID() {
		return TRIP_ROUTE_ID;
	}

	public void setTRIP_ROUTE_ID(Integer tRIP_ROUTE_ID) {
		TRIP_ROUTE_ID = tRIP_ROUTE_ID;
	}

	public short getATTENDANCE_STATUS_ID() {
		return ATTENDANCE_STATUS_ID;
	}

	public void setATTENDANCE_STATUS_ID(short aTTENDANCE_STATUS_ID) {
		ATTENDANCE_STATUS_ID = aTTENDANCE_STATUS_ID;
	}

	public short getPOINT_TYPE_ID() {
		return POINT_TYPE_ID;
	}

	public void setPOINT_TYPE_ID(short pOINT_TYPE_ID) {
		POINT_TYPE_ID = pOINT_TYPE_ID;
	}

	public short getPOINT_STATUS_ID() {
		return POINT_STATUS_ID;
	}

	public void setPOINT_STATUS_ID(short pOINT_STATUS_ID) {
		POINT_STATUS_ID = pOINT_STATUS_ID;
	}

	public Long getCREATED_BY_ID() {
		return CREATED_BY_ID;
	}

	public void setCREATED_BY_ID(Long cREATED_BY_ID) {
		CREATED_BY_ID = cREATED_BY_ID;
	}

	public Long getLASTUPDATED_BY_ID() {
		return LASTUPDATED_BY_ID;
	}

	public void setLASTUPDATED_BY_ID(Long lASTUPDATED_BY_ID) {
		LASTUPDATED_BY_ID = lASTUPDATED_BY_ID;
	}
	

	public Long getDHID() {
		return DHID;
	}

	public void setDHID(Long dHID) {
		DHID = dHID;
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
		result = prime * result + ((DAID == null) ? 0 : DAID.hashCode());
		result = prime * result + DRIVERID;
		result = prime * result + ((TRIPSHEETID == null) ? 0 : TRIPSHEETID.hashCode());
		result = prime * result + ((TRIP_ROUTE_ID == null) ? 0 : TRIP_ROUTE_ID.hashCode());
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
		DriverAttendance other = (DriverAttendance) obj;
		if (DAID == null) {
			if (other.DAID != null)
				return false;
		} else if (!DAID.equals(other.DAID))
			return false;
		if (DRIVERID != other.DRIVERID)
			return false;
		if (TRIPSHEETID == null) {
			if (other.TRIPSHEETID != null)
				return false;
		} else if (!TRIPSHEETID.equals(other.TRIPSHEETID))
			return false;
		if (TRIP_ROUTE_ID == null) {
			if (other.TRIP_ROUTE_ID != null)
				return false;
		} else if (!TRIP_ROUTE_ID.equals(other.TRIP_ROUTE_ID))
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
		builder.append("DriverAttendance [DAID=");
		builder.append(DAID);
		builder.append(", DRIVERID=");
		builder.append(DRIVERID);
		builder.append(", ATTENDANCE_DATE=");
		builder.append(ATTENDANCE_DATE);
		builder.append(", TRIPSHEETID=");
		builder.append(TRIPSHEETID);
		builder.append(", TRIP_ROUTE_ID=");
		builder.append(TRIP_ROUTE_ID);
		builder.append(", DRIVER_POINT=");
		builder.append(DRIVER_POINT);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
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
