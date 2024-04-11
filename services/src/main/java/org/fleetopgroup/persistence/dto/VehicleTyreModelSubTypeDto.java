/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class VehicleTyreModelSubTypeDto {
	
	public static final short	WARRANTY_TYPE_MONTH	= 1;
	public static final short	WARRANTY_TYPE_YEAR	= 2;
	
	public static final String	WARRANTY_TYPE_MONTH_NAME	= "MONTH";
	public static final String	WARRANTY_TYPE_YEAR_NAME		= "YEAR";
	

	private Integer TYRE_MST_ID;
	
	private Integer TYRE_MT_ID;

	private String TYRE_MODEL;

	private String TYRE_MODEL_SUBTYPE;

	private String TYRE_MODEL_DESCRITION;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;

	boolean markForDelete;

	private String CREATED_DATE;

	private String LASTMODIFIED_DATE;
	
	private	Integer	warrantyPeriod;
	
	private short	warrantyTypeId;

	private String warrantyType;
	
	private String	warrentyterm;
	
	private Double	costPerKM;
	
	private Long 		vehicleCostFixingId;
	
	private short tyreModelTypeId;
	
	private String tyreModelType;
	
	private Integer tyreGauge;
	
	private Integer gauageMeasurementLine;
	
	private short tyreTubeTypeId;
	
	private String tyreTubeType;
	
	private Integer ply;
	
	private Integer psi;
	
	private Integer tyreModelSizeId;
	
	private String tyreModelSize;
	
	public static  String  getWarrantyTypeName(short type) throws Exception {

		String statusString = null;
		switch (type) {
		case WARRANTY_TYPE_MONTH:
			statusString = WARRANTY_TYPE_MONTH_NAME;
			break;
		case WARRANTY_TYPE_YEAR:
			statusString = WARRANTY_TYPE_YEAR_NAME;
			break;
		
		default:
			statusString = "--";
			break;
		}
		return statusString;
	
	} 


	public VehicleTyreModelSubTypeDto() {
		super();
	}

	public VehicleTyreModelSubTypeDto(Integer tYRE_MST_ID, String tYRE_MODEL, String tYRE_MODEL_SUBTYPE,
			String tYRE_MODEL_DESCRITION, String cREATEDBY, String lASTMODIFIEDBY, boolean MarkForDelete, String cREATED_DATE,
			String lASTMODIFIED_DATE ) {
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
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	/**
	 * @param cREATED_DATE
	 *            the cREATED_DATE to set
	 */
	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the lASTMODIFIED_DATE
	 */
	public String getLASTMODIFIED_DATE() {
		return LASTMODIFIED_DATE;
	}

	/**
	 * @param lASTMODIFIED_DATE
	 *            the lASTMODIFIED_DATE to set
	 */
	public void setLASTMODIFIED_DATE(String lASTMODIFIED_DATE) {
		LASTMODIFIED_DATE = lASTMODIFIED_DATE;
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

	public String getWarrantyType() {
		return warrantyType;
	}

	public void setWarrantyType(String warrantyType) {
		this.warrantyType = warrantyType;
	}

	public String getWarrentyterm() {
		return warrentyterm;
	}

	public void setWarrentyterm(String warrentyterm) {
		this.warrentyterm = warrentyterm;
	}


	public Double getCostPerKM() {
		return costPerKM;
	}


	public void setCostPerKM(Double costPerKM) {
		this.costPerKM = Utility.round(costPerKM, 2);
	}


	public Long getVehicleCostFixingId() {
		return vehicleCostFixingId;
	}


	public void setVehicleCostFixingId(Long vehicleCostFixingId) {
		this.vehicleCostFixingId = vehicleCostFixingId;
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
		result = prime * result + ((TYRE_MODEL_SUBTYPE == null) ? 0 : TYRE_MODEL_SUBTYPE.hashCode());
		result = prime * result + ((TYRE_MST_ID == null) ? 0 : TYRE_MST_ID.hashCode());
		return result;
	}

	public short getTyreModelTypeId() {
		return tyreModelTypeId;
	}


	public void setTyreModelTypeId(short tyreModelTypeId) {
		this.tyreModelTypeId = tyreModelTypeId;
	}


	public String getTyreModelType() {
		return tyreModelType;
	}


	public void setTyreModelType(String tyreModelType) {
		this.tyreModelType = tyreModelType;
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


	public String getTyreTubeType() {
		return tyreTubeType;
	}


	public void setTyreTubeType(String tyreTubeType) {
		this.tyreTubeType = tyreTubeType;
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


	public String getTyreModelSize() {
		return tyreModelSize;
	}


	public void setTyreModelSize(String tyreModelSize) {
		this.tyreModelSize = tyreModelSize;
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
		VehicleTyreModelSubTypeDto other = (VehicleTyreModelSubTypeDto) obj;
		if (TYRE_MODEL == null) {
			if (other.TYRE_MODEL != null)
				return false;
		} else if (!TYRE_MODEL.equals(other.TYRE_MODEL))
			return false;
		if (TYRE_MODEL_SUBTYPE == null) {
			if (other.TYRE_MODEL_SUBTYPE != null)
				return false;
		} else if (!TYRE_MODEL_SUBTYPE.equals(other.TYRE_MODEL_SUBTYPE))
			return false;
		if (TYRE_MST_ID == null) {
			if (other.TYRE_MST_ID != null)
				return false;
		} else if (!TYRE_MST_ID.equals(other.TYRE_MST_ID))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "VehicleTyreModelSubTypeDto [TYRE_MST_ID=" + TYRE_MST_ID + ", TYRE_MT_ID=" + TYRE_MT_ID + ", TYRE_MODEL="
				+ TYRE_MODEL + ", TYRE_MODEL_SUBTYPE=" + TYRE_MODEL_SUBTYPE + ", TYRE_MODEL_DESCRITION="
				+ TYRE_MODEL_DESCRITION + ", CREATEDBY=" + CREATEDBY + ", LASTMODIFIEDBY=" + LASTMODIFIEDBY
				+ ", markForDelete=" + markForDelete + ", CREATED_DATE=" + CREATED_DATE + ", LASTMODIFIED_DATE="
				+ LASTMODIFIED_DATE + ", warrantyPeriod=" + warrantyPeriod + ", warrantyTypeId=" + warrantyTypeId
				+ ", warrantyType=" + warrantyType + ", warrentyterm=" + warrentyterm + ", costPerKM=" + costPerKM
				+ ", vehicleCostFixingId=" + vehicleCostFixingId + ", tyreModelTypeId=" + tyreModelTypeId
				+ ", tyreModelType=" + tyreModelType + ", tyreGauge=" + tyreGauge + ", gauageMeasurementLine="
				+ gauageMeasurementLine + ", tyreTubeTypeId=" + tyreTubeTypeId + ", tyreTubeType=" + tyreTubeType
				+ ", ply=" + ply + ", psi=" + psi + ", tyreModelSizeId=" + tyreModelSizeId + ", tyreModelSize="
				+ tyreModelSize + "]";
	}




	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */

}
