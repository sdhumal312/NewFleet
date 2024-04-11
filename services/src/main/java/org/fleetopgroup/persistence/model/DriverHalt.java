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
@Table(name = "DriverHalt")
public class DriverHalt implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	/** The value for the DRIVERATTENDANCE ID field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DHID")
	private Long DHID;

	/** The value for the DRIVER ID field */
	@Column(name = "DRIVERID", nullable = false)
	private int DRIVERID;


	/** The value for the VID field */
	@Column(name = "VID")
	private Integer VID;

	/** The value for the REFERENCE_NO field */
	@Column(name = "REFERENCE_NO", length = 150)
	private String REFERENCE_NO;

	/**
	 * The value for the DRIVER to create ATTENDANCE name one user name field
	 */
	@Basic(optional = false)
	@Column(name = "HALT_DATE_FROM")
	@Temporal(TemporalType.TIMESTAMP)
	private Date HALT_DATE_FROM;

	/**
	 * The value for the DRIVER to create ATTENDANCE name one user name field
	 */
	@Basic(optional = false)
	@Column(name = "HALT_DATE_TO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date HALT_DATE_TO;

	/** The value for the DRIVER TRIP ID field */
	@Column(name = "TRIPSHEETID", nullable = false)
	private Long TRIPSHEETID;
	
	@Column(name = "TRIP_ROUTE_ID")
	private Integer TRIP_ROUTE_ID;

	/** The value for the HALT_AMOUNT NAME field */
	@Column(name = "HALT_AMOUNT")
	private Double HALT_AMOUNT;

	/** The value for the HALT_POINT field */
	@Column(name = "HALT_POINT", nullable = false)
	private Double HALT_POINT;

	/** The value for the HALT_REASON NAME field */
	@Column(name = "HALT_REASON", length = 250)
	private String HALT_REASON;

	@Column(name = "HALT_PLACE_ID", nullable = false)
	private Integer HALT_PLACE_ID;
	
	@Column(name = "HALT_PAIDBY_ID", nullable = false)
	private Long HALT_PAIDBY_ID;
	
	@Column(name = "HALT_PLACE_TYPE_ID", nullable = false)
	private short HALT_PLACE_TYPE_ID;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	@Column(name = "CREATED_BY_ID", nullable = false)
	private Long CREATED_BY_ID;
	
	@Column(name = "LASTUPDATED_BY_ID")
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

	public DriverHalt() {
		super();
	}

	public DriverHalt(Long dHID, int dRIVERID, Date hALT_DATE_FROM, Date hALT_DATE_TO,
			Long tRIPSHEETID,  Double hALT_AMOUNT, Double hALT_POINT, String hALT_REASON,
			  boolean MarkForDelete, Date cREATED,
			Date lASTUPDATED, Integer cOMPANY_ID) {
		super();
		DHID = dHID;
		DRIVERID = dRIVERID;
		HALT_DATE_FROM = hALT_DATE_FROM;
		HALT_DATE_TO = hALT_DATE_TO;
		TRIPSHEETID = tRIPSHEETID;
		HALT_AMOUNT = hALT_AMOUNT;
		HALT_POINT = hALT_POINT;
		HALT_REASON = hALT_REASON;
		markForDelete = MarkForDelete;
		CREATED = cREATED;
		LASTUPDATED = lASTUPDATED;
		COMPANY_ID	= cOMPANY_ID;
	}

	/**
	 * @return the dHID
	 */
	public Long getDHID() {
		return DHID;
	}

	/**
	 * @param dHID
	 *            the dHID to set
	 */
	public void setDHID(Long dHID) {
		DHID = dHID;
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
	 * @return the hALT_DATE_FROM
	 */
	public Date getHALT_DATE_FROM() {
		return HALT_DATE_FROM;
	}

	/**
	 * @param hALT_DATE_FROM
	 *            the hALT_DATE_FROM to set
	 */
	public void setHALT_DATE_FROM(Date hALT_DATE_FROM) {
		HALT_DATE_FROM = hALT_DATE_FROM;
	}

	/**
	 * @return the hALT_DATE_TO
	 */
	public Date getHALT_DATE_TO() {
		return HALT_DATE_TO;
	}

	/**
	 * @param hALT_DATE_TO
	 *            the hALT_DATE_TO to set
	 */
	public void setHALT_DATE_TO(Date hALT_DATE_TO) {
		HALT_DATE_TO = hALT_DATE_TO;
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


	/**
	 * @return the hALT_AMOUNT
	 */
	public Double getHALT_AMOUNT() {
		return HALT_AMOUNT;
	}

	/**
	 * @param hALT_AMOUNT
	 *            the hALT_AMOUNT to set
	 */
	public void setHALT_AMOUNT(Double hALT_AMOUNT) {
		HALT_AMOUNT = hALT_AMOUNT;
	}

	/**
	 * @return the hALT_POINT
	 */
	public Double getHALT_POINT() {
		return HALT_POINT;
	}

	/**
	 * @param hALT_POINT
	 *            the hALT_POINT to set
	 */
	public void setHALT_POINT(Double hALT_POINT) {
		HALT_POINT = hALT_POINT;
	}

	/**
	 * @return the hALT_REASON
	 */
	public String getHALT_REASON() {
		return HALT_REASON;
	}

	/**
	 * @param hALT_REASON
	 *            the hALT_REASON to set
	 */
	public void setHALT_REASON(String hALT_REASON) {
		HALT_REASON = hALT_REASON;
	}

	
	public Long getHALT_PAIDBY_ID() {
		return HALT_PAIDBY_ID;
	}

	public void setHALT_PAIDBY_ID(Long hALT_PAIDBY_ID) {
		HALT_PAIDBY_ID = hALT_PAIDBY_ID;
	}

	public short getHALT_PLACE_TYPE_ID() {
		return HALT_PLACE_TYPE_ID;
	}

	public void setHALT_PLACE_TYPE_ID(short hALT_PLACE_TYPE_ID) {
		HALT_PLACE_TYPE_ID = hALT_PLACE_TYPE_ID;
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

	/**
	 * @return the vID
	 */
	public Integer getVID() {
		return VID;
	}

	/**
	 * @param vID
	 *            the vID to set
	 */
	public void setVID(Integer vID) {
		VID = vID;
	}


	public Integer getTRIP_ROUTE_ID() {
		return TRIP_ROUTE_ID;
	}

	public void setTRIP_ROUTE_ID(Integer tRIP_ROUTE_ID) {
		TRIP_ROUTE_ID = tRIP_ROUTE_ID;
	}

	public Integer getHALT_PLACE_ID() {
		return HALT_PLACE_ID;
	}

	public void setHALT_PLACE_ID(Integer hALT_PLACE_ID) {
		HALT_PLACE_ID = hALT_PLACE_ID;
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

	/**
	 * @return the rEFERENCE_NO
	 */
	public String getREFERENCE_NO() {
		return REFERENCE_NO;
	}

	/**
	 * @param rEFERENCE_NO
	 *            the rEFERENCE_NO to set
	 */
	public void setREFERENCE_NO(String rEFERENCE_NO) {
		REFERENCE_NO = rEFERENCE_NO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverHalt [DHID=");
		builder.append(DHID);
		builder.append(", DRIVERID=");
		builder.append(DRIVERID);
		builder.append(", HALT_DATE_FROM=");
		builder.append(HALT_DATE_FROM);
		builder.append(", HALT_DATE_TO=");
		builder.append(HALT_DATE_TO);
		builder.append(", TRIPSHEETID=");
		builder.append(TRIPSHEETID);
		builder.append(", HALT_AMOUNT=");
		builder.append(HALT_AMOUNT);
		builder.append(", HALT_POINT=");
		builder.append(HALT_POINT);
		builder.append(", HALT_REASON=");
		builder.append(HALT_REASON);
		/*builder.append(", HALT_PAIDBY=");
		builder.append(HALT_PAIDBY);*/
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
