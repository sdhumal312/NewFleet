package org.fleetopgroup.persistence.dto;

import java.util.Date;

public class VehicleCommentDto{
	private Long VEHICLE_COMMENTID;

	/** Issues Comment title */
	private String VEHICLE_TITLE;

	/** Issues Comment _ Which issues comment id */
	private Integer VEHICLE_ID;

	/** Issues Comment_string */
	private String VEHICLE_COMMENT;

	/** Issues Comment_create by */
	private String CREATEDBY;

	/** Issues Comment_create by */
	private String CREATED_EMAIL;

	/** Issues Comment_status */
	boolean markForDelete;

	/** The value for the Comment_create DATE field */
	private String CREATED_DATE;
	
	private Date CREATED_DATE_ON;
	
	/** The value for the Comment_create DATE_ DIFFERENT field */
	private String CREATED_DATE_DIFFERENT;
	
	private Long CREATEDBYID;
	
	private Integer COMPANY_ID;
	
	private long VEHICLE_GROUP_ID;
	
	private String fromDate;
	
	private String toDate;
	
	private String VEHICLE_REGISTRATION;

	public VehicleCommentDto() {
		super();
	}

	public VehicleCommentDto(Long vEHICLE_COMMENTID, String vEHICLE_TITLE, Integer vEHICLE_ID, String vEHICLE_COMMENT,
			String cREATEDBY, String cREATED_EMAIL, boolean MarkForDelete, String cREATED_DATE) {
		super();
		VEHICLE_COMMENTID = vEHICLE_COMMENTID;
		VEHICLE_TITLE = vEHICLE_TITLE;
		VEHICLE_ID = vEHICLE_ID;
		VEHICLE_COMMENT = vEHICLE_COMMENT;
		CREATEDBY = cREATEDBY;
		CREATED_EMAIL = cREATED_EMAIL;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the vEHICLE_COMMENTID
	 */
	public Long getVEHICLE_COMMENTID() {
		return VEHICLE_COMMENTID;
	}

	/**
	 * @param vEHICLE_COMMENTID the vEHICLE_COMMENTID to set
	 */
	public void setVEHICLE_COMMENTID(Long vEHICLE_COMMENTID) {
		VEHICLE_COMMENTID = vEHICLE_COMMENTID;
	}

	/**
	 * @return the vEHICLE_TITLE
	 */
	public String getVEHICLE_TITLE() {
		return VEHICLE_TITLE;
	}

	/**
	 * @param vEHICLE_TITLE the vEHICLE_TITLE to set
	 */
	public void setVEHICLE_TITLE(String vEHICLE_TITLE) {
		VEHICLE_TITLE = vEHICLE_TITLE;
	}

	/**
	 * @return the vEHICLE_ID
	 */
	public Integer getVEHICLE_ID() {
		return VEHICLE_ID;
	}

	/**
	 * @param vEHICLE_ID the vEHICLE_ID to set
	 */
	public void setVEHICLE_ID(Integer vEHICLE_ID) {
		VEHICLE_ID = vEHICLE_ID;
	}

	/**
	 * @return the vEHICLE_COMMENT
	 */
	public String getVEHICLE_COMMENT() {
		return VEHICLE_COMMENT;
	}

	/**
	 * @param vEHICLE_COMMENT the vEHICLE_COMMENT to set
	 */
	public void setVEHICLE_COMMENT(String vEHICLE_COMMENT) {
		VEHICLE_COMMENT = vEHICLE_COMMENT;
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
	 * @return the cREATED_EMAIL
	 */
	public String getCREATED_EMAIL() {
		return CREATED_EMAIL;
	}

	/**
	 * @param cREATED_EMAIL the cREATED_EMAIL to set
	 */
	public void setCREATED_EMAIL(String cREATED_EMAIL) {
		CREATED_EMAIL = cREATED_EMAIL;
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
	 * @return the cREATED_DATE_DIFFERENT
	 */
	public String getCREATED_DATE_DIFFERENT() {
		return CREATED_DATE_DIFFERENT;
	}

	/**
	 * @param cREATED_DATE_DIFFERENT the cREATED_DATE_DIFFERENT to set
	 */
	public void setCREATED_DATE_DIFFERENT(String cREATED_DATE_DIFFERENT) {
		CREATED_DATE_DIFFERENT = cREATED_DATE_DIFFERENT;
	}

	/**
	 * @param cREATED_DATE the cREATED_DATE to set
	 */
	public void setCREATED_DATE(String cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	

	/**
	 * @return the cREATED_DATE
	 */
	public String getCREATED_DATE() {
		return CREATED_DATE;
	}

	/**
	 * @return the cREATEDBYID
	 */
	public Long getCREATEDBYID() {
		return CREATEDBYID;
	}

	/**
	 * @return the cREATED_DATE_ON
	 */
	public Date getCREATED_DATE_ON() {
		return CREATED_DATE_ON;
	}

	/**
	 * @param cREATED_DATE_ON the cREATED_DATE_ON to set
	 */
	public void setCREATED_DATE_ON(Date cREATED_DATE_ON) {
		CREATED_DATE_ON = cREATED_DATE_ON;
	}

	/**
	 * @param cREATEDBYID the cREATEDBYID to set
	 */
	public void setCREATEDBYID(Long cREATEDBYID) {
		CREATEDBYID = cREATEDBYID;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	public long getVEHICLE_GROUP_ID() {
		return VEHICLE_GROUP_ID;
	}

	public void setVEHICLE_GROUP_ID(long vEHICLE_GROUP_ID) {
		VEHICLE_GROUP_ID = vEHICLE_GROUP_ID;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getVEHICLE_REGISTRATION() {
		return VEHICLE_REGISTRATION;
	}

	public void setVEHICLE_REGISTRATION(String vEHICLE_REGISTRATION) {
		VEHICLE_REGISTRATION = vEHICLE_REGISTRATION;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleCommentDto [VEHICLE_COMMENTID=");
		builder.append(VEHICLE_COMMENTID);
		builder.append(", VEHICLE_TITLE=");
		builder.append(VEHICLE_TITLE);
		builder.append(", VEHICLE_ID=");
		builder.append(VEHICLE_ID);
		builder.append(", VEHICLE_COMMENT=");
		builder.append(VEHICLE_COMMENT);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", CREATED_EMAIL=");
		builder.append(CREATED_EMAIL);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", CREATED_DATE_DIFFERENT=");
		builder.append(CREATED_DATE_DIFFERENT);
		builder.append("]");
		return builder.toString();
	}

	

	
}
