package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class VendorFixedFuelPriceDto {

	/** The value for the VFFID by email field */
	private Long VFFID;

	/** The value for the VENDORID by email field */
	private Integer VENDORID;

	/** The value for the FUELCOST by email field */
	private Long FID;

	/** The value for the FUELCOST by email field */
	private String FUEL_NAME;

	/** The value for the FUELCOST by email field */
	private Double FUEL_PERDAY_COST;

	/** The value for the FUEL_FIXED_DATE DATE field */
	private String FUEL_FIXED_DATE;

	/** The value for the created by email field */
	private String CREATEDBY;

	/** The value for the lastUpdated By email field */
	private String LASTMODIFIEDBY;

	private boolean markForDelete;

	/** The value for the created DATE field */
	private String CREATEDDATE;

	/** The value for the lastUpdated DATE field */
	private String LASTUPDATED;

	public VendorFixedFuelPriceDto() {
		super();

	}

	public VendorFixedFuelPriceDto(Long vFFID, Integer vENDORID, Long fID, Double fUEL_PERDAY_COST,
			String fUEL_FIXED_DATE, String cREATEDBY, String lASTMODIFIEDBY, boolean markForDelete, String cREATEDDATE,
			String lASTUPDATED) {
		super();
		VFFID = vFFID;
		VENDORID = vENDORID;
		FID = fID;
		FUEL_PERDAY_COST = fUEL_PERDAY_COST;
		FUEL_FIXED_DATE = fUEL_FIXED_DATE;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
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
	 * @return the fUEL_NAME
	 */
	public String getFUEL_NAME() {
		return FUEL_NAME;
	}

	/**
	 * @param fUEL_NAME
	 *            the fUEL_NAME to set
	 */
	public void setFUEL_NAME(String fUEL_NAME) {
		FUEL_NAME = fUEL_NAME;
	}

	/**
	 * @param fUEL_PERDAY_COST
	 *            the fUEL_PERDAY_COST to set
	 */
	public void setFUEL_PERDAY_COST(Double fUEL_PERDAY_COST) {
		FUEL_PERDAY_COST = Utility.round(fUEL_PERDAY_COST, 2);
	}

	/**
	 * @return the fUEL_FIXED_DATE
	 */
	public String getFUEL_FIXED_DATE() {
		return FUEL_FIXED_DATE;
	}

	/**
	 * @param fUEL_FIXED_DATE
	 *            the fUEL_FIXED_DATE to set
	 */
	public void setFUEL_FIXED_DATE(String fUEL_FIXED_DATE) {
		FUEL_FIXED_DATE = fUEL_FIXED_DATE;
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
	public String getCREATEDDATE() {
		return CREATEDDATE;
	}

	/**
	 * @param cREATEDDATE
	 *            the cREATEDDATE to set
	 */
	public void setCREATEDDATE(String cREATEDDATE) {
		CREATEDDATE = cREATEDDATE;
	}

	/**
	 * @return the lASTUPDATED
	 */
	public String getLASTUPDATED() {
		return LASTUPDATED;
	}

	/**
	 * @param lASTUPDATED
	 *            the lASTUPDATED to set
	 */
	public void setLASTUPDATED(String lASTUPDATED) {
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
		VendorFixedFuelPriceDto other = (VendorFixedFuelPriceDto) obj;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
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
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
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
