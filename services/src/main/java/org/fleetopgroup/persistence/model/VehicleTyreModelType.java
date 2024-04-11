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
@Table(name = "VehicleTyreModelType")
public class VehicleTyreModelType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TYRE_MT_ID")
	private Integer TYRE_MT_ID;
	
	@Column(name = "TYRE_MODEL", unique = false, nullable = true, length = 200)
	private String TYRE_MODEL;
	
	@Column(name = "TYRE_MODEL_DESCRITION", length = 250)
	private String TYRE_MODEL_DESCRITION;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	@Column(name = "CREATEDBY", length = 200)
	private String CREATEDBY;

	@Column(name = "LASTMODIFIEDBY", length = 200)
	private String LASTMODIFIEDBY;

	@Column(name = "CREATEDBYID", nullable = false)
	private Long CREATEDBYID;
	
	@Column(name = "LASTMODIFIEDBYID", nullable = false)
	private Long LASTMODIFIEDBYID;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	private Date CREATED_DATE;

	@Column(name = "LASTMODIFIED_DATE", nullable = false)
	private Date LASTMODIFIED_DATE;
	
	@Column(name = "isOwnTyreModel", nullable = false)
	private boolean isOwnTyreModel;
	
	
	public VehicleTyreModelType() {
		super();
	}

	public VehicleTyreModelType(Integer tYRE_MT_ID, String tYRE_MODEL, String tYRE_MODEL_DESCRITION, String cREATEDBY,
			String lASTMODIFIEDBY, boolean MarkForDelete, Date cREATED_DATE, Date lASTMODIFIED_DATE, Integer companyId) {
		super();
		TYRE_MT_ID = tYRE_MT_ID;
		TYRE_MODEL = tYRE_MODEL;
		TYRE_MODEL_DESCRITION = tYRE_MODEL_DESCRITION;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTMODIFIED_DATE = lASTMODIFIED_DATE;
		COMPANY_ID = companyId;
	}

	/**
	 * @return the tYRE_MT_ID
	 */
	public Integer getTYRE_MT_ID() {
		return TYRE_MT_ID;
	}

	/**
	 * @param tYRE_MT_ID the tYRE_MT_ID to set
	 */
	public void setTYRE_MT_ID(Integer tYRE_MT_ID) {
		TYRE_MT_ID = tYRE_MT_ID;
	}

	/**
	 * @return the tYRE_MODEL
	 */
	public String getTYRE_MODEL() {
		return TYRE_MODEL;
	}

	/**
	 * @param tYRE_MODEL the tYRE_MODEL to set
	 */
	public void setTYRE_MODEL(String tYRE_MODEL) {
		TYRE_MODEL = tYRE_MODEL;
	}

	/**
	 * @return the tYRE_MODEL_DESCRITION
	 */
	public String getTYRE_MODEL_DESCRITION() {
		return TYRE_MODEL_DESCRITION;
	}

	/**
	 * @param tYRE_MODEL_DESCRITION the tYRE_MODEL_DESCRITION to set
	 */
	public void setTYRE_MODEL_DESCRITION(String tYRE_MODEL_DESCRITION) {
		TYRE_MODEL_DESCRITION = tYRE_MODEL_DESCRITION;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/**
	 * @return the cREATEDBY
	 */
	public String getCREATEDBY() {
		return CREATEDBY;
	}

	/**
	 * @param cREATEDBY the cREATEDBY to set
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
	 * @param lASTMODIFIEDBY the lASTMODIFIEDBY to set
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
	 * @param sTATUS the sTATUS to set
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
	 * @param cREATED_DATE the cREATED_DATE to set
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
	 * @param lASTMODIFIED_DATE the lASTMODIFIED_DATE to set
	 */
	public void setLASTMODIFIED_DATE(Date lASTMODIFIED_DATE) {
		LASTMODIFIED_DATE = lASTMODIFIED_DATE;
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

	public boolean isOwnTyreModel() {
		return isOwnTyreModel;
	}

	public void setOwnTyreModel(boolean isOwnTyreModel) {
		this.isOwnTyreModel = isOwnTyreModel;
	}
	

	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((TYRE_MODEL == null) ? 0 : TYRE_MODEL.hashCode());
		result = prime * result + ((TYRE_MT_ID == null) ? 0 : TYRE_MT_ID.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		VehicleTyreModelType other = (VehicleTyreModelType) obj;
		if (TYRE_MODEL == null) {
			if (other.TYRE_MODEL != null)
				return false;
		} else if (!TYRE_MODEL.equals(other.TYRE_MODEL))
			return false;
		if (TYRE_MT_ID == null) {
			if (other.TYRE_MT_ID != null)
				return false;
		} else if (!TYRE_MT_ID.equals(other.TYRE_MT_ID))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleTyreModelType [TYRE_MT_ID=");
		builder.append(TYRE_MT_ID);
		builder.append(", TYRE_MODEL=");
		builder.append(TYRE_MODEL);
		builder.append(", TYRE_MODEL_DESCRITION=");
		builder.append(TYRE_MODEL_DESCRITION);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
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
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}