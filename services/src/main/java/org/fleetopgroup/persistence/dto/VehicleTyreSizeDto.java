/**
 * 
 */
package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 */
public class VehicleTyreSizeDto {

	private Integer TS_ID;

	private String TYRE_SIZE;

	private String TYRE_SIZE_DESCRITION;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;

	boolean markForDelete;

	private String CREATED_DATE;

	private String LASTMODIFIED_DATE;

	public VehicleTyreSizeDto() {
		super();
	}

	public VehicleTyreSizeDto(Integer tS_ID, String tYRE_SIZE, String tYRE_SIZE_DESCRITION, String cREATEDBY,
			String lASTMODIFIEDBY, boolean MarkForDelete, String cREATED_DATE, String lASTMODIFIED_DATE) {
		super();
		TS_ID = tS_ID;
		TYRE_SIZE = tYRE_SIZE;
		TYRE_SIZE_DESCRITION = tYRE_SIZE_DESCRITION;
		CREATEDBY = cREATEDBY;
		LASTMODIFIEDBY = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTMODIFIED_DATE = lASTMODIFIED_DATE;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleTyreSizeDto [TS_ID=");
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
		builder.append("]");
		return builder.toString();
	}

}
