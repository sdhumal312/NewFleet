package org.fleetopgroup.persistence.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "IssuesComment")
public class IssuesCommentDto {

	private Long ISSUE_COMMENTID;

	/** The value for the ISSUE_COMMENTID_ENCRYPT field */
	private String ISSUE_COMMENTID_ENCRYPT;

	/** Issues Comment title */
	private String ISSUE_TITLE;

	/** Issues Comment _ Which issues comment id */
	private Long ISSUES_ID;

	/** The value for the ISSUES_ID_ENCRYPT field */
	private String ISSUES_ID_ENCRYPT;

	/** Issues Comment_string */
	private String ISSUE_COMMENT;

	/** Issues Comment_create by */
	private String CREATEDBY;

	/** Issues Comment_create by */
	private String CREATED_EMAIL;

	/** Issues Comment_status */
	boolean markForDelete;

	/** The value for the Comment_create DATE field */
	private String CREATED_DATE;
	
	private Date CREATED_DATEON;

	/** The value for the Comment_create DATE Different field */
	private String CREATED_DATE_DIFFERENT;
	
	private Long 	COMMENT_TYPE_ID;
	
	private String  COMMENT_TYPE_NAME;
	
	private String  driverName;
	
	private String  driverFatherName;
	
	private String  assigneeName;
	
	private Integer driverId;
	
	private Long	assignee;

	public Long getCOMMENT_TYPE_ID() {
		return COMMENT_TYPE_ID;
	}

	public void setCOMMENT_TYPE_ID(Long cOMMENT_TYPE_ID) {
		COMMENT_TYPE_ID = cOMMENT_TYPE_ID;
	}

	

	public IssuesCommentDto() {
		super();
	}

	public IssuesCommentDto(Long iSSUE_COMMENTID, String iSSUE_TITLE, Long iSSUES_ID, String iSSUE_COMMENT,
			String cREATEDBY, boolean MarkForDelete, String cREATED_DATE) {
		super();
		ISSUE_COMMENTID = iSSUE_COMMENTID;
		ISSUE_TITLE = iSSUE_TITLE;
		ISSUES_ID = iSSUES_ID;
		ISSUE_COMMENT = iSSUE_COMMENT;
		CREATEDBY = cREATEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the iSSUE_COMMENTID_ENCRYPT
	 */
	public String getISSUE_COMMENTID_ENCRYPT() {
		return ISSUE_COMMENTID_ENCRYPT;
	}

	/**
	 * @param iSSUE_COMMENTID_ENCRYPT
	 *            the iSSUE_COMMENTID_ENCRYPT to set
	 */
	public void setISSUE_COMMENTID_ENCRYPT(String iSSUE_COMMENTID_ENCRYPT) {
		ISSUE_COMMENTID_ENCRYPT = iSSUE_COMMENTID_ENCRYPT;
	}

	/**
	 * @return the iSSUES_ID_ENCRYPT
	 */
	public String getISSUES_ID_ENCRYPT() {
		return ISSUES_ID_ENCRYPT;
	}

	/**
	 * @param iSSUES_ID_ENCRYPT
	 *            the iSSUES_ID_ENCRYPT to set
	 */
	public void setISSUES_ID_ENCRYPT(String iSSUES_ID_ENCRYPT) {
		ISSUES_ID_ENCRYPT = iSSUES_ID_ENCRYPT;
	}

	/**
	 * @return the iSSUE_COMMENTID
	 */
	public Long getISSUE_COMMENTID() {
		return ISSUE_COMMENTID;
	}

	/**
	 * @param iSSUE_COMMENTID
	 *            the iSSUE_COMMENTID to set
	 */
	public void setISSUE_COMMENTID(Long iSSUE_COMMENTID) {
		ISSUE_COMMENTID = iSSUE_COMMENTID;
	}

	/**
	 * @return the iSSUE_TITLE
	 */
	public String getISSUE_TITLE() {
		return ISSUE_TITLE;
	}

	/**
	 * @param iSSUE_TITLE
	 *            the iSSUE_TITLE to set
	 */
	public void setISSUE_TITLE(String iSSUE_TITLE) {
		ISSUE_TITLE = iSSUE_TITLE;
	}

	/**
	 * @return the iSSUES_ID
	 */
	public Long getISSUES_ID() {
		return ISSUES_ID;
	}

	/**
	 * @param iSSUES_ID
	 *            the iSSUES_ID to set
	 */
	public void setISSUES_ID(Long iSSUES_ID) {
		ISSUES_ID = iSSUES_ID;
	}

	/**
	 * @return the iSSUE_COMMENT
	 */
	public String getISSUE_COMMENT() {
		return ISSUE_COMMENT;
	}

	/**
	 * @param iSSUE_COMMENT
	 *            the iSSUE_COMMENT to set
	 */
	public void setISSUE_COMMENT(String iSSUE_COMMENT) {
		ISSUE_COMMENT = iSSUE_COMMENT;
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

	/**
	 * @return the cREATED_DATEON
	 */
	public Date getCREATED_DATEON() {
		return CREATED_DATEON;
	}

	/**
	 * @param cREATED_DATEON the cREATED_DATEON to set
	 */
	public void setCREATED_DATEON(Date cREATED_DATEON) {
		CREATED_DATEON = cREATED_DATEON;
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
		result = prime * result + ((ISSUES_ID == null) ? 0 : ISSUES_ID.hashCode());
		result = prime * result + ((ISSUE_COMMENTID == null) ? 0 : ISSUE_COMMENTID.hashCode());
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
		IssuesCommentDto other = (IssuesCommentDto) obj;
		if (ISSUES_ID == null) {
			if (other.ISSUES_ID != null)
				return false;
		} else if (!ISSUES_ID.equals(other.ISSUES_ID))
			return false;
		if (ISSUE_COMMENTID == null) {
			if (other.ISSUE_COMMENTID != null)
				return false;
		} else if (!ISSUE_COMMENTID.equals(other.ISSUE_COMMENTID))
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
		builder.append("IssuesCommentDto [ISSUE_COMMENTID=");
		builder.append(ISSUE_COMMENTID);
		builder.append(", ISSUE_COMMENTID_ENCRYPT=");
		builder.append(ISSUE_COMMENTID_ENCRYPT);
		builder.append(", ISSUE_TITLE=");
		builder.append(ISSUE_TITLE);
		builder.append(", ISSUES_ID=");
		builder.append(ISSUES_ID);
		builder.append(", ISSUES_ID_ENCRYPT=");
		builder.append(ISSUES_ID_ENCRYPT);
		builder.append(", ISSUE_COMMENT=");
		builder.append(ISSUE_COMMENT);
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

	public String getCOMMENT_TYPE_NAME() {
		return COMMENT_TYPE_NAME;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Long getAssignee() {
		return assignee;
	}

	public void setAssignee(Long assignee) {
		this.assignee = assignee;
	}

	public void setCOMMENT_TYPE_NAME(String cOMMENT_TYPE_NAME) {
		COMMENT_TYPE_NAME = cOMMENT_TYPE_NAME;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverFatherName() {
		return driverFatherName;
	}

	public String getAssigneeName() {
		return assigneeName;
	}

	public void setAssigneeName(String assigneeName) {
		this.assigneeName = assigneeName;
	}

	public void setDriverFatherName(String driverFatherName) {
		this.driverFatherName = driverFatherName;
	}

}
