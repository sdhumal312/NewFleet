package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleTyreSize")
public class VehicleTyreSize implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TS_ID")
	private Integer TS_ID;

	@Column(name = "TYRE_SIZE", unique = false, nullable = false, length = 25)
	private String TYRE_SIZE;

	@Column(name = "TYRE_SIZE_DESCRITION", length = 250)
	private String TYRE_SIZE_DESCRITION;

	@Column(name = "CREATEDBY", length = 200)
	private String CREATEDBY;
	
	@Column(name = "CREATEDBYID", length = 200)
	private Long CREATEDBYID;

	@Column(name = "LASTMODIFIEDBY", length = 200)
	private String LASTMODIFIEDBY;

	@Column(name = "LASTMODIFIEDBYID", length = 200)
	private Long LASTMODIFIEDBYID;


	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	private Date CREATED_DATE;

	@Column(name = "LASTMODIFIED_DATE", nullable = false)
	private Date LASTMODIFIED_DATE;
	
	@Column(name = "companyId" , nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "ownTyreSize", nullable = false)
	private boolean ownTyreSize;

	public VehicleTyreSize() {
		super();
	}

	public VehicleTyreSize(Integer tS_ID, String tYRE_SIZE, String tYRE_SIZE_DESCRITION, String cREATEDBY,
			String lASTMODIFIEDBY, boolean MarkForDelete, Date cREATED_DATE, Date lASTMODIFIED_DATE,Integer companyId) {
		super();
		TS_ID = tS_ID;
		TYRE_SIZE = tYRE_SIZE;
		TYRE_SIZE_DESCRITION = tYRE_SIZE_DESCRITION;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTMODIFIED_DATE = lASTMODIFIED_DATE;
		this.companyId = companyId;
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
		result = prime * result + ((CREATEDBY == null) ? 0 : CREATEDBY.hashCode());
		result = prime * result + ((CREATED_DATE == null) ? 0 : CREATED_DATE.hashCode());
		result = prime * result + ((LASTMODIFIEDBY == null) ? 0 : LASTMODIFIEDBY.hashCode());
		result = prime * result + ((LASTMODIFIED_DATE == null) ? 0 : LASTMODIFIED_DATE.hashCode());
		result = prime * result + ((TS_ID == null) ? 0 : TS_ID.hashCode());
		result = prime * result + ((TYRE_SIZE == null) ? 0 : TYRE_SIZE.hashCode());
		result = prime * result + ((TYRE_SIZE_DESCRITION == null) ? 0 : TYRE_SIZE_DESCRITION.hashCode());
		return result;
	}

	/**
	 * @return the tS_ID
	 */
	public Integer getTS_ID() {
		return TS_ID;
	}

	/**
	 * @param tS_ID
	 *            the tS_ID to set
	 */
	public void setTS_ID(Integer tS_ID) {
		TS_ID = tS_ID;
	}

	/**
	 * @return the tYRE_SIZE
	 */
	public String getTYRE_SIZE() {
		return TYRE_SIZE;
	}

	/**
	 * @param tYRE_SIZE
	 *            the tYRE_SIZE to set
	 */
	public void setTYRE_SIZE(String tYRE_SIZE) {
		TYRE_SIZE = tYRE_SIZE;
	}

	/**
	 * @return the tYRE_SIZE_DESCRITION
	 */
	public String getTYRE_SIZE_DESCRITION() {
		return TYRE_SIZE_DESCRITION;
	}

	/**
	 * @param tYRE_SIZE_DESCRITION
	 *            the tYRE_SIZE_DESCRITION to set
	 */
	public void setTYRE_SIZE_DESCRITION(String tYRE_SIZE_DESCRITION) {
		TYRE_SIZE_DESCRITION = tYRE_SIZE_DESCRITION;
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
	 * @return the lASTMODIFIED_DATE
	 */
	public Date getLASTMODIFIED_DATE() {
		return LASTMODIFIED_DATE;
	}

	/**
	 * @param lASTMODIFIED_DATE
	 *            the lASTMODIFIED_DATE to set
	 */
	public void setLASTMODIFIED_DATE(Date lASTMODIFIED_DATE) {
		LASTMODIFIED_DATE = lASTMODIFIED_DATE;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getCREATEDBYID() {
		return CREATEDBYID;
	}

	public void setCREATEDBYID(Long cREATEDBYID) {
		CREATEDBYID = cREATEDBYID;
	}

	public Long getLASTMODIFIEDBYID() {
		return LASTMODIFIEDBYID;
	}

	public void setLASTMODIFIEDBYID(Long lASTMODIFIEDBYID) {
		LASTMODIFIEDBYID = lASTMODIFIEDBYID;
	}

	public boolean isOwnTyreSize() {
		return ownTyreSize;
	}

	public void setOwnTyreSize(boolean ownTyreSize) {
		this.ownTyreSize = ownTyreSize;
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
		VehicleTyreSize other = (VehicleTyreSize) obj;
		if (CREATEDBY == null) {
			if (other.CREATEDBY != null)
				return false;
		} else if (!CREATEDBY.equals(other.CREATEDBY))
			return false;
		if (CREATED_DATE == null) {
			if (other.CREATED_DATE != null)
				return false;
		} else if (!CREATED_DATE.equals(other.CREATED_DATE))
			return false;
		if (LASTMODIFIEDBY == null) {
			if (other.LASTMODIFIEDBY != null)
				return false;
		} else if (!LASTMODIFIEDBY.equals(other.LASTMODIFIEDBY))
			return false;
		if (LASTMODIFIED_DATE == null) {
			if (other.LASTMODIFIED_DATE != null)
				return false;
		} else if (!LASTMODIFIED_DATE.equals(other.LASTMODIFIED_DATE))
			return false;
		
		if (TS_ID == null) {
			if (other.TS_ID != null)
				return false;
		} else if (!TS_ID.equals(other.TS_ID))
			return false;
		if (TYRE_SIZE == null) {
			if (other.TYRE_SIZE != null)
				return false;
		} else if (!TYRE_SIZE.equals(other.TYRE_SIZE))
			return false;
		if (TYRE_SIZE_DESCRITION == null) {
			if (other.TYRE_SIZE_DESCRITION != null)
				return false;
		} else if (!TYRE_SIZE_DESCRITION.equals(other.TYRE_SIZE_DESCRITION))
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
		builder.append("VehicleTyreSize [TS_ID=");
		builder.append(TS_ID);
		builder.append(", TYRE_SIZE=");
		builder.append(TYRE_SIZE);
		builder.append(", TYRE_SIZE_DESCRITION=");
		builder.append(TYRE_SIZE_DESCRITION);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTMODIFIED_DATE=");
		builder.append(LASTMODIFIED_DATE);
		builder.append(", company_id=");
		builder.append(companyId);
		builder.append("]");
		return builder.toString();
	}

}