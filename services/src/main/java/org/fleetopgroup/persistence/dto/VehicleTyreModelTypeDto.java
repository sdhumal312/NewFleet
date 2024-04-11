/**
 * 
 */
package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 */
public class VehicleTyreModelTypeDto {

	private Integer TYRE_MT_ID;

	private String TYRE_MODEL;

	private String TYRE_MODEL_DESCRITION;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;

	boolean markForDelete;

	private String CREATED_DATE;

	private String LASTMODIFIED_DATE;
	
	private short		warrantyTypeId;
	
	
	private Integer		warrantyType;
	
	private String		warrentyterm;


	public VehicleTyreModelTypeDto() {
		super();
	}

	public VehicleTyreModelTypeDto(Integer tYRE_MT_ID, String tYRE_MODEL, String tYRE_MODEL_DESCRITION,
			String cREATEDBY, String lASTMODIFIEDBY, boolean MarkForDelete, String cREATED_DATE, String lASTMODIFIED_DATE) {
		super();
		TYRE_MT_ID = tYRE_MT_ID;
		TYRE_MODEL = tYRE_MODEL;
		TYRE_MODEL_DESCRITION = tYRE_MODEL_DESCRITION;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTMODIFIED_DATE = lASTMODIFIED_DATE;
	}

	/**
	 * @return the tYRE_MT_ID
	 */
	public Integer getTYRE_MT_ID() {
		return TYRE_MT_ID;
	}

	/**
	 * @param tYRE_MT_ID
	 *            the tYRE_MT_ID to set
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
	
	public short getWarrantyTypeId() {
		return warrantyTypeId;
	}

	public void setWarrantyTypeId(short warrantyTypeId) {
		this.warrantyTypeId = warrantyTypeId;
	}

	public Integer getWarrantyType() {
		return warrantyType;
	}

	public void setWarrantyType(Integer warrantyType) {
		this.warrantyType = warrantyType;
	}

	public String getWarrentyterm() {
		return warrentyterm;
	}

	public void setWarrentyterm(String warrentyterm) {
		this.warrentyterm = warrentyterm;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleTyreModelTypeDto [TYRE_MT_ID=");
		builder.append(TYRE_MT_ID);
		builder.append(", TYRE_MODEL=");
		builder.append(TYRE_MODEL);
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
