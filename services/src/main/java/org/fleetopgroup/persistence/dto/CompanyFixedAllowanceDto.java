package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

public class CompanyFixedAllowanceDto {

	private Long COMFIXID;

	private Integer COMPANY_ID;

	private Long VEHICLEGROUP_ID;
	
	private String VEHICLEGROUP_NAME;

	private Integer DRIVER_JOBTYPE_ID;
	
	private String DRIVER_JOBTYPE_NAME;

	private Integer FIX_PERDAY_ALLOWANCE;

	private Double FIX_PERDAY_ALLOWANCE_AMOUNT;

	private Integer FIX_EXTRA_DAYS;

	private Double FIX_EXTRA_DAYS_AMOUNT;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;

	private boolean markForDelete;

	private String CREATED;

	private String LASTUPDATED;

	public CompanyFixedAllowanceDto() {
		super();
	}

	/**
	 * @return the cOMFIXID
	 */
	public Long getCOMFIXID() {
		return COMFIXID;
	}

	/**
	 * @param cOMFIXID
	 *            the cOMFIXID to set
	 */
	public void setCOMFIXID(Long cOMFIXID) {
		COMFIXID = cOMFIXID;
	}

	/**
	 * @return the cOMPANY_ID
	 */
	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	/**
	 * @param cOMPANY_ID
	 *            the cOMPANY_ID to set
	 */
	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/**
	 * @return the vEHICLEGROUP_ID
	 */
	public Long getVEHICLEGROUP_ID() {
		return VEHICLEGROUP_ID;
	}

	/**
	 * @param vEHICLEGROUP_ID
	 *            the vEHICLEGROUP_ID to set
	 */
	public void setVEHICLEGROUP_ID(Long vEHICLEGROUP_ID) {
		VEHICLEGROUP_ID = vEHICLEGROUP_ID;
	}

	/**
	 * @return the dRIVER_JOBTYPE_ID
	 */
	public Integer getDRIVER_JOBTYPE_ID() {
		return DRIVER_JOBTYPE_ID;
	}

	/**
	 * @param dRIVER_JOBTYPE_ID
	 *            the dRIVER_JOBTYPE_ID to set
	 */
	public void setDRIVER_JOBTYPE_ID(Integer dRIVER_JOBTYPE_ID) {
		DRIVER_JOBTYPE_ID = dRIVER_JOBTYPE_ID;
	}

	/**
	 * @return the fIX_PERDAY_ALLOWANCE
	 */
	public Integer getFIX_PERDAY_ALLOWANCE() {
		return FIX_PERDAY_ALLOWANCE;
	}

	/**
	 * @param fIX_PERDAY_ALLOWANCE
	 *            the fIX_PERDAY_ALLOWANCE to set
	 */
	public void setFIX_PERDAY_ALLOWANCE(Integer fIX_PERDAY_ALLOWANCE) {
		FIX_PERDAY_ALLOWANCE = fIX_PERDAY_ALLOWANCE;
	}

	/**
	 * @return the fIX_PERDAY_ALLOWANCE_AMOUNT
	 */
	public Double getFIX_PERDAY_ALLOWANCE_AMOUNT() {
		return FIX_PERDAY_ALLOWANCE_AMOUNT;
	}

	/**
	 * @param fIX_PERDAY_ALLOWANCE_AMOUNT
	 *            the fIX_PERDAY_ALLOWANCE_AMOUNT to set
	 */
	public void setFIX_PERDAY_ALLOWANCE_AMOUNT(Double fIX_PERDAY_ALLOWANCE_AMOUNT) {
		FIX_PERDAY_ALLOWANCE_AMOUNT = Utility.round(fIX_PERDAY_ALLOWANCE_AMOUNT, 2);
	}

	/**
	 * @return the fIX_EXTRA_DAYS
	 */
	public Integer getFIX_EXTRA_DAYS() {
		return FIX_EXTRA_DAYS;
	}

	/**
	 * @param fIX_EXTRA_DAYS
	 *            the fIX_EXTRA_DAYS to set
	 */
	public void setFIX_EXTRA_DAYS(Integer fIX_EXTRA_DAYS) {
		FIX_EXTRA_DAYS = fIX_EXTRA_DAYS;
	}

	/**
	 * @return the fIX_EXTRA_DAYS_AMOUNT
	 */
	public Double getFIX_EXTRA_DAYS_AMOUNT() {
		return FIX_EXTRA_DAYS_AMOUNT;
	}
	
	

	/**
	 * @return the vEHICLEGROUP_NAME
	 */
	public String getVEHICLEGROUP_NAME() {
		return VEHICLEGROUP_NAME;
	}

	/**
	 * @param vEHICLEGROUP_NAME the vEHICLEGROUP_NAME to set
	 */
	public void setVEHICLEGROUP_NAME(String vEHICLEGROUP_NAME) {
		VEHICLEGROUP_NAME = vEHICLEGROUP_NAME;
	}

	/**
	 * @return the dRIVER_JOBTYPE_NAME
	 */
	public String getDRIVER_JOBTYPE_NAME() {
		return DRIVER_JOBTYPE_NAME;
	}

	/**
	 * @param dRIVER_JOBTYPE_NAME the dRIVER_JOBTYPE_NAME to set
	 */
	public void setDRIVER_JOBTYPE_NAME(String dRIVER_JOBTYPE_NAME) {
		DRIVER_JOBTYPE_NAME = dRIVER_JOBTYPE_NAME;
	}

	/**
	 * @param fIX_EXTRA_DAYS_AMOUNT
	 *            the fIX_EXTRA_DAYS_AMOUNT to set
	 */
	public void setFIX_EXTRA_DAYS_AMOUNT(Double fIX_EXTRA_DAYS_AMOUNT) {
		FIX_EXTRA_DAYS_AMOUNT = Utility.round(fIX_EXTRA_DAYS_AMOUNT, 2);
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
	 * @return the cREATED
	 */
	public String getCREATED() {
		return CREATED;
	}

	/**
	 * @param cREATED
	 *            the cREATED to set
	 */
	public void setCREATED(String cREATED) {
		CREATED = cREATED;
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
		result = prime * result + ((COMPANY_ID == null) ? 0 : COMPANY_ID.hashCode());
		result = prime * result + ((DRIVER_JOBTYPE_ID == null) ? 0 : DRIVER_JOBTYPE_ID.hashCode());
		result = prime * result + ((VEHICLEGROUP_ID == null) ? 0 : VEHICLEGROUP_ID.hashCode());
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
		CompanyFixedAllowanceDto other = (CompanyFixedAllowanceDto) obj;
		if (COMPANY_ID == null) {
			if (other.COMPANY_ID != null)
				return false;
		} else if (!COMPANY_ID.equals(other.COMPANY_ID))
			return false;
		if (DRIVER_JOBTYPE_ID == null) {
			if (other.DRIVER_JOBTYPE_ID != null)
				return false;
		} else if (!DRIVER_JOBTYPE_ID.equals(other.DRIVER_JOBTYPE_ID))
			return false;
		if (VEHICLEGROUP_ID == null) {
			if (other.VEHICLEGROUP_ID != null)
				return false;
		} else if (!VEHICLEGROUP_ID.equals(other.VEHICLEGROUP_ID))
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
		builder.append("CompanyFixedAllowance [COMFIXID=");
		builder.append(COMFIXID);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", VEHICLEGROUP_ID=");
		builder.append(VEHICLEGROUP_ID);
		builder.append(", DRIVER_JOBTYPE_ID=");
		builder.append(DRIVER_JOBTYPE_ID);
		builder.append(", FIX_PERDAY_ALLOWANCE=");
		builder.append(FIX_PERDAY_ALLOWANCE);
		builder.append(", FIX_PERDAY_ALLOWANCE_AMOUNT=");
		builder.append(FIX_PERDAY_ALLOWANCE_AMOUNT);
		builder.append(", FIX_EXTRA_DAYS=");
		builder.append(FIX_EXTRA_DAYS);
		builder.append(", FIX_EXTRA_DAYS_AMOUNT=");
		builder.append(FIX_EXTRA_DAYS_AMOUNT);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED=");
		builder.append(CREATED);
		builder.append(", LASTUPDATED=");
		builder.append(LASTUPDATED);
		builder.append("]");
		return builder.toString();
	}

}
