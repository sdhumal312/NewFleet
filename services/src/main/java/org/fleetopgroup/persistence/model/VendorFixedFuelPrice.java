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
@Table(name = "VendorFixedFuelPrice")
public class VendorFixedFuelPrice implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The value for the VFFID by email field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VFFID")
	private Long VFFID;

	/** The value for the VENDORID by email field */
	@Column(name = "VENDORID")
	private Integer VENDORID;

	/** The value for the FUELCOST by email field */
	@Column(name = "FID")
	private Long FID;

	/** The value for the FUELCOST by email field */
	@Column(name = "FUEL_PERDAY_COST", nullable=false)
	private Double FUEL_PERDAY_COST;

	/** The value for the FUEL_FIXED_DATE DATE field */
	@Basic(optional = false)
	@Column(name = "FUEL_FIXED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date FUEL_FIXED_DATE;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer	COMPANY_ID;

	/** The value for the created by email field *//*
	@Column(name = "CREATEDBY", updatable = false, length = 150)
	private String CREATEDBY;

	*//** The value for the lastUpdated By email field *//*
	@Column(name = "LASTMODIFIEDBY", length = 150)
	private String LASTMODIFIEDBY;
	*/
	@Column(name = "CREATEDBYID", updatable = false)
	private Long CREATEDBYID;
	
	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;

	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "CREATEDDATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATEDDATE;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "LASTUPDATED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date LASTUPDATED;

	public VendorFixedFuelPrice() {
		super();

	}

	public VendorFixedFuelPrice(Long vFFID, Integer vENDORID, Long fID, Double fUEL_PERDAY_COST, Date fUEL_FIXED_DATE,
			Long cREATEDBY, Long lASTMODIFIEDBY, boolean markForDelete, Date cREATEDDATE, Date lASTUPDATED) {
		super();
		VFFID = vFFID;
		VENDORID = vENDORID;
		FID = fID;
		FUEL_PERDAY_COST = fUEL_PERDAY_COST;
		FUEL_FIXED_DATE = fUEL_FIXED_DATE;
		CREATEDBYID = cREATEDBY;
		LASTMODIFIEDBYID = lASTMODIFIEDBY;
		this.markForDelete = markForDelete;
		CREATEDDATE = cREATEDDATE;
		LASTUPDATED = lASTUPDATED;
	}

	/**
	 * @return the vFFID
	 */
	public Long getVFFID() {
		return VFFID;
	}

	/**
	 * @param vFFID
	 *            the vFFID to set
	 */
	public void setVFFID(Long vFFID) {
		VFFID = vFFID;
	}

	/**
	 * @return the vENDORID
	 */
	public Integer getVENDORID() {
		return VENDORID;
	}

	/**
	 * @param vENDORID
	 *            the vENDORID to set
	 */
	public void setVENDORID(Integer vENDORID) {
		VENDORID = vENDORID;
	}

	/**
	 * @return the fID
	 */
	public Long getFID() {
		return FID;
	}

	/**
	 * @param fID
	 *            the fID to set
	 */
	public void setFID(Long fID) {
		FID = fID;
	}

	/**
	 * @return the fUEL_PERDAY_COST
	 */
	public Double getFUEL_PERDAY_COST() {
		return FUEL_PERDAY_COST;
	}

	
	

	/**
	 * @param fUEL_PERDAY_COST
	 *            the fUEL_PERDAY_COST to set
	 */
	public void setFUEL_PERDAY_COST(Double fUEL_PERDAY_COST) {
		FUEL_PERDAY_COST = fUEL_PERDAY_COST;
	}

	/**
	 * @return the fUEL_FIXED_DATE
	 */
	public Date getFUEL_FIXED_DATE() {
		return FUEL_FIXED_DATE;
	}

	/**
	 * @param fUEL_FIXED_DATE
	 *            the fUEL_FIXED_DATE to set
	 */
	public void setFUEL_FIXED_DATE(Date fUEL_FIXED_DATE) {
		FUEL_FIXED_DATE = fUEL_FIXED_DATE;
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
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete
	 *            the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the cREATEDDATE
	 */
	public Date getCREATEDDATE() {
		return CREATEDDATE;
	}

	/**
	 * @param cREATEDDATE
	 *            the cREATEDDATE to set
	 */
	public void setCREATEDDATE(Date cREATEDDATE) {
		CREATEDDATE = cREATEDDATE;
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

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
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
		result = prime * result + ((FID == null) ? 0 : FID.hashCode());
		result = prime * result + ((FUEL_FIXED_DATE == null) ? 0 : FUEL_FIXED_DATE.hashCode());
		result = prime * result + ((FUEL_PERDAY_COST == null) ? 0 : FUEL_PERDAY_COST.hashCode());
		result = prime * result + ((VENDORID == null) ? 0 : VENDORID.hashCode());
		result = prime * result + ((VFFID == null) ? 0 : VFFID.hashCode());
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
		VendorFixedFuelPrice other = (VendorFixedFuelPrice) obj;
		if (FID == null) {
			if (other.FID != null)
				return false;
		} else if (!FID.equals(other.FID))
			return false;
		if (FUEL_FIXED_DATE == null) {
			if (other.FUEL_FIXED_DATE != null)
				return false;
		} else if (!FUEL_FIXED_DATE.equals(other.FUEL_FIXED_DATE))
			return false;
		if (FUEL_PERDAY_COST == null) {
			if (other.FUEL_PERDAY_COST != null)
				return false;
		} else if (!FUEL_PERDAY_COST.equals(other.FUEL_PERDAY_COST))
			return false;
		if (VENDORID == null) {
			if (other.VENDORID != null)
				return false;
		} else if (!VENDORID.equals(other.VENDORID))
			return false;
		if (VFFID == null) {
			if (other.VFFID != null)
				return false;
		} else if (!VFFID.equals(other.VFFID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VendorFixedFuelPrice [VFFID=");
		builder.append(VFFID);
		builder.append(", VENDORID=");
		builder.append(VENDORID);
		builder.append(", FID=");
		builder.append(FID);
		builder.append(", FUEL_PERDAY_COST=");
		builder.append(FUEL_PERDAY_COST);
		builder.append(", FUEL_FIXED_DATE=");
		builder.append(FUEL_FIXED_DATE);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", CREATEDBYID=");
		builder.append(CREATEDBYID);
		builder.append(", LASTMODIFIEDBYID=");
		builder.append(LASTMODIFIEDBYID);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATEDDATE=");
		builder.append(CREATEDDATE);
		builder.append(", LASTUPDATED=");
		builder.append(LASTUPDATED);
		builder.append("]");
		return builder.toString();
	}

}
