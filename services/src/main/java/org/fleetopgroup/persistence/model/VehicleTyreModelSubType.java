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
@Table(name = "VehicleTyreModelSubType")
public class VehicleTyreModelSubType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TYRE_MST_ID")
	private Integer TYRE_MST_ID;

	@Column(name = "TYRE_MODEL", length = 200)
	private String TYRE_MODEL;
	
	@Column(name = "TYRE_MT_ID")
	private Integer TYRE_MT_ID;

	@Column(name = "TYRE_MODEL_SUBTYPE", nullable = true, length = 200)
	private String TYRE_MODEL_SUBTYPE;

	@Column(name = "TYRE_MODEL_DESCRITION", length = 250)
	private String TYRE_MODEL_DESCRITION;

	@Column(name = "CREATEDBY", length = 200)
	private String CREATEDBY;

	@Column(name = "CREATEDBYID", nullable = false)
	private Long CREATEDBYID;

	@Column(name = "LASTMODIFIEDBY", length = 200)
	private String LASTMODIFIEDBY;

	@Column(name = "LASTMODIFIEDBYID", nullable = false)
	private Long LASTMODIFIEDBYID;
	
	@Column(name = "warrantyPeriod")
	private	Integer	warrantyPeriod;
	
	@Column(name = "warrantyTypeId")
	private short	warrantyTypeId;
	
	@Column(name = "warrentyterm")
	private String	warrentyterm;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	@Column(name = "CREATED_DATE", nullable = false, updatable = false)
	private Date CREATED_DATE;

	@Column(name = "LASTMODIFIED_DATE", nullable = false)
	private Date LASTMODIFIED_DATE;
	
	/* this column show that this record is related from which company*/
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;
	
	@Column(name = "isOwnTyreModel", nullable = false)
	private boolean isOwnTyreModel;
	
	@Column(name = "tyreModelTypeId", nullable = false)
	private short tyreModelTypeId;
	
	@Column(name = "gauageMeasurementLine", nullable = false)
	private Integer gauageMeasurementLine;
	
	@Column(name = "tyreGauge", nullable = false)
	private Integer tyreGauge;
	
	@Column(name = "tyreTubeTypeId", nullable = false)
	private short tyreTubeTypeId;
	
	@Column(name = "ply", nullable = false)
	private Integer ply;
	
	@Column(name = "psi", nullable = false)
	private Integer psi;
	
	@Column(name = "tyreModelSizeId", nullable = false)
	private Integer tyreModelSizeId;

	
	public VehicleTyreModelSubType() {
		super();
	}

	public VehicleTyreModelSubType(Integer tYRE_MST_ID, String tYRE_MODEL, String tYRE_MODEL_SUBTYPE,
			String tYRE_MODEL_DESCRITION, String cREATEDBY, String lASTMODIFIEDBY, boolean MarkForDelete, Date cREATED_DATE,
			Date lASTMODIFIED_DATE,Integer COMPANY_ID, boolean MARKFORDELETE) {
		super();
		TYRE_MST_ID = tYRE_MST_ID;
		TYRE_MODEL = tYRE_MODEL;
		TYRE_MODEL_SUBTYPE = tYRE_MODEL_SUBTYPE;
		TYRE_MODEL_DESCRITION = tYRE_MODEL_DESCRITION;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTMODIFIED_DATE = lASTMODIFIED_DATE;
		this.COMPANY_ID = COMPANY_ID;
	}

	/**
	 * @return the tYRE_MST_ID
	 */
	public Integer getTYRE_MST_ID() {
		return TYRE_MST_ID;
	}

	/**
	 * @param tYRE_MST_ID
	 *            the tYRE_MST_ID to set
	 */
	public void setTYRE_MST_ID(Integer tYRE_MST_ID) {
		TYRE_MST_ID = tYRE_MST_ID;
	}

	/**
	 * @return the tYRE_MODEL
	 */
	public String getTYRE_MODEL() {
		return TYRE_MODEL;
	}

	/**
	 * @param tYRE_MODEL
	 *            the tYRE_MODEL to set
	 */
	public void setTYRE_MODEL(String tYRE_MODEL) {
		TYRE_MODEL = tYRE_MODEL;
	}

	/**
	 * @return the tYRE_MODEL_SUBTYPE
	 */
	public String getTYRE_MODEL_SUBTYPE() {
		return TYRE_MODEL_SUBTYPE;
	}

	/**
	 * @param tYRE_MODEL_SUBTYPE
	 *            the tYRE_MODEL_SUBTYPE to set
	 */
	public void setTYRE_MODEL_SUBTYPE(String tYRE_MODEL_SUBTYPE) {
		TYRE_MODEL_SUBTYPE = tYRE_MODEL_SUBTYPE;
	}

	/**
	 * @return the tYRE_MODEL_DESCRITION
	 */
	public String getTYRE_MODEL_DESCRITION() {
		return TYRE_MODEL_DESCRITION;
	}

	/**
	 * @param tYRE_MODEL_DESCRITION
	 *            the tYRE_MODEL_DESCRITION to set
	 */
	public void setTYRE_MODEL_DESCRITION(String tYRE_MODEL_DESCRITION) {
		TYRE_MODEL_DESCRITION = tYRE_MODEL_DESCRITION;
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

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
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
	public Integer getWarrantyPeriod() {
		return warrantyPeriod;
	}

	public void setWarrantyPeriod(Integer warrantyPeriod) {
		this.warrantyPeriod = warrantyPeriod;
	}

	public short getWarrantyTypeId() {
		return warrantyTypeId;
	}

	public void setWarrantyTypeId(short warrantyTypeId) {
		this.warrantyTypeId = warrantyTypeId;
	}

	public String getWarrentyterm() {
		return warrentyterm;
	}

	public void setWarrentyterm(String warrentyterm) {
		this.warrentyterm = warrentyterm;
	}
		
	public short getTyreModelTypeId() {
		return tyreModelTypeId;
	}

	public void setTyreModelTypeId(short tyreModelTypeId) {
		this.tyreModelTypeId = tyreModelTypeId;
	}

	public Integer getTyreGauge() {
		return tyreGauge;
	}

	public void setTyreGauge(Integer tyreGauge) {
		this.tyreGauge = tyreGauge;
	}

	public short getTyreTubeTypeId() {
		return tyreTubeTypeId;
	}

	public void setTyreTubeTypeId(short tyreTubeTypeId) {
		this.tyreTubeTypeId = tyreTubeTypeId;
	}

	public Integer getPly() {
		return ply;
	}

	public void setPly(Integer ply) {
		this.ply = ply;
	}

	public Integer getPsi() {
		return psi;
	}

	public void setPsi(Integer psi) {
		this.psi = psi;
	}
	
	public Integer getGauageMeasurementLine() {
		return gauageMeasurementLine;
	}

	public void setGauageMeasurementLine(Integer gauageMeasurementLine) {
		this.gauageMeasurementLine = gauageMeasurementLine;
	}
	
	public Integer getTyreModelSizeId() {
		return tyreModelSizeId;
	}

	public void setTyreModelSizeId(Integer tyreModelSizeId) {
		this.tyreModelSizeId = tyreModelSizeId;
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
		result = prime * result + ((TYRE_MODEL == null) ? 0 : TYRE_MODEL.hashCode());
		result = prime * result + ((TYRE_MST_ID == null) ? 0 : TYRE_MST_ID.hashCode());
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
		VehicleTyreModelSubType other = (VehicleTyreModelSubType) obj;
		if (TYRE_MODEL == null) {
			if (other.TYRE_MODEL != null)
				return false;
		} else if (!TYRE_MODEL.equals(other.TYRE_MODEL))
			return false;
		if (TYRE_MST_ID == null) {
			if (other.TYRE_MST_ID != null)
				return false;
		} else if (!TYRE_MST_ID.equals(other.TYRE_MST_ID))
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
		builder.append("VehicleTyreModelSubType [TYRE_MST_ID=");
		builder.append(TYRE_MST_ID);
		builder.append(", TYRE_MODEL=");
		builder.append(TYRE_MODEL);
		builder.append(", TYRE_MODEL_SUBTYPE=");
		builder.append(TYRE_MODEL_SUBTYPE);
		builder.append(", TYRE_MODEL_DESCRITION=");
		builder.append(TYRE_MODEL_DESCRITION);
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