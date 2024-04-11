package org.fleetopgroup.persistence.dto;

public class TripSheetCommentDto {

	private Long TRIPCID;

	/** Issues Comment _ Which issues comment id */
	private Long TRIPSHEETID;

	/** Issues Comment_string */
	private String TRIP_COMMENT;

	/** Issues Comment_create by */
	private String CREATEDBY;

	/** Issues Comment_string */
	private String CREATED_PLACE;

	/** Issues Comment_create by */
	private String CREATED_EMAIL;

	/** Issues Comment_status */
	boolean markForDelete;

	/** The value for the Comment_create DATE field */
	private String CREATED_DATE;

	/** The value for the Comment_create DATE Different field */
	private String CREATED_DATE_DIFFERENT;

	public TripSheetCommentDto() {
		super();
	}

	public TripSheetCommentDto(Long tRIPCID, Long tRIPSHEETID, String tRIP_COMMENT, String cREATEDBY,
			String cREATED_PLACE, String cREATED_EMAIL, boolean MarkForDelete, String cREATED_DATE) {
		super();
		TRIPCID = tRIPCID;
		TRIPSHEETID = tRIPSHEETID;
		TRIP_COMMENT = tRIP_COMMENT;
		CREATEDBY = cREATEDBY;
		CREATED_PLACE = cREATED_PLACE;
		CREATED_EMAIL = cREATED_EMAIL;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the tRIPCID
	 */
	public Long getTRIPCID() {
		return TRIPCID;
	}

	/**
	 * @param tRIPCID
	 *            the tRIPCID to set
	 */
	public void setTRIPCID(Long tRIPCID) {
		TRIPCID = tRIPCID;
	}

	/**
	 * @return the tRIPSHEETID
	 */
	public Long getTRIPSHEETID() {
		return TRIPSHEETID;
	}

	/**
	 * @param tRIPSHEETID
	 *            the tRIPSHEETID to set
	 */
	public void setTRIPSHEETID(Long tRIPSHEETID) {
		TRIPSHEETID = tRIPSHEETID;
	}

	/**
	 * @return the tRIP_COMMENT
	 */
	public String getTRIP_COMMENT() {
		return TRIP_COMMENT;
	}

	/**
	 * @param tRIP_COMMENT
	 *            the tRIP_COMMENT to set
	 */
	public void setTRIP_COMMENT(String tRIP_COMMENT) {
		TRIP_COMMENT = tRIP_COMMENT;
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
	 * @return the cREATED_PLACE
	 */
	public String getCREATED_PLACE() {
		return CREATED_PLACE;
	}

	/**
	 * @param cREATED_PLACE
	 *            the cREATED_PLACE to set
	 */
	public void setCREATED_PLACE(String cREATED_PLACE) {
		CREATED_PLACE = cREATED_PLACE;
	}

	/**
	 * @return the cREATED_EMAIL
	 */
	public String getCREATED_EMAIL() {
		return CREATED_EMAIL;
	}

	/**
	 * @param cREATED_EMAIL
	 *            the cREATED_EMAIL to set
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
	 * @return the cREATED_DATE_DIFFERENT
	 */
	public String getCREATED_DATE_DIFFERENT() {
		return CREATED_DATE_DIFFERENT;
	}

	/**
	 * @param cREATED_DATE_DIFFERENT
	 *            the cREATED_DATE_DIFFERENT to set
	 */
	public void setCREATED_DATE_DIFFERENT(String cREATED_DATE_DIFFERENT) {
		CREATED_DATE_DIFFERENT = cREATED_DATE_DIFFERENT;
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
		result = prime * result + ((TRIPCID == null) ? 0 : TRIPCID.hashCode());
		result = prime * result + ((TRIPSHEETID == null) ? 0 : TRIPSHEETID.hashCode());
		result = prime * result + ((TRIP_COMMENT == null) ? 0 : TRIP_COMMENT.hashCode());
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
		TripSheetCommentDto other = (TripSheetCommentDto) obj;
		if (TRIPCID == null) {
			if (other.TRIPCID != null)
				return false;
		} else if (!TRIPCID.equals(other.TRIPCID))
			return false;
		if (TRIPSHEETID == null) {
			if (other.TRIPSHEETID != null)
				return false;
		} else if (!TRIPSHEETID.equals(other.TRIPSHEETID))
			return false;
		if (TRIP_COMMENT == null) {
			if (other.TRIP_COMMENT != null)
				return false;
		} else if (!TRIP_COMMENT.equals(other.TRIP_COMMENT))
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
		builder.append("TripSheetCommentDto [TRIPCID=");
		builder.append(TRIPCID);
		builder.append(", TRIPSHEETID=");
		builder.append(TRIPSHEETID);
		builder.append(", TRIP_COMMENT=");
		builder.append(TRIP_COMMENT);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", CREATED_PLACE=");
		builder.append(CREATED_PLACE);
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
