package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "IssuesComment")
public class IssuesComment implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ISSUE_COMMENTID")
	private Long ISSUE_COMMENTID;

	/** Issues Comment title */
	@Column(name = "ISSUE_TITLE", length = 250)
	private String ISSUE_TITLE;

	/** Issues Comment _ Which issues comment id */
	@Column(name = "ISSUES_ID", updatable = false)
	private Long ISSUES_ID;

	/** Issues Comment_string */
	@Column(name = "ISSUE_COMMENT", length = 1000)
	private String ISSUE_COMMENT;

	/** Issues Comment_create by *//*
	@Column(name = "CREATEDBY", updatable = false, length = 150)
	private String CREATEDBY;*/
	
	@Column(name = "CREATEDBYID")
	private Long CREATEDBYID;

	/** Issues Comment_create by *//*
	@Column(name = "CREATED_EMAIL", updatable = false, length = 150)
	private String CREATED_EMAIL;*/
	
	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	/** Issues Comment_status */
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	@Column(name = "COMMENT_TYPE_ID")
	private Long COMMENT_TYPE_ID;

	/** The value for the Comment_create DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;
	
	@Column(name = "driverId")
	private Integer driverId;
	
	@Column(name = "assignee")
	private Long	assignee;

	public IssuesComment() {
		super();
	}

	public IssuesComment(Long iSSUE_COMMENTID, String iSSUE_TITLE, Long iSSUES_ID, String iSSUE_COMMENT,
			Long cREATEDBY, boolean MarkForDelete, Date cREATED_DATE, Integer cOMPANY_ID) {
		super();
		ISSUE_COMMENTID = iSSUE_COMMENTID;
		ISSUE_TITLE = iSSUE_TITLE;
		ISSUES_ID = iSSUES_ID;
		ISSUE_COMMENT = iSSUE_COMMENT;
		CREATEDBYID = cREATEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		COMPANY_ID	= cOMPANY_ID;
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
	 *//*
	public String getCREATEDBY() {
		return CREATEDBY;
	}

	*//**
	 * @param cREATEDBY
	 *            the cREATEDBY to set
	 *//*
	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}
*/
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
	 * @return the cREATED_EMAIL
	 *//*
	public String getCREATED_EMAIL() {
		return CREATED_EMAIL;
	}

	*//**
	 * @param cREATED_EMAIL
	 *            the cREATED_EMAIL to set
	 *//*
	public void setCREATED_EMAIL(String cREATED_EMAIL) {
		CREATED_EMAIL = cREATED_EMAIL;
	}*/

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/**
	 * @return the cREATEDBYID
	 */
	public Long getCREATEDBYID() {
		return CREATEDBYID;
	}

	/**
	 * @param cREATEDBYID the cREATEDBYID to set
	 */
	public void setCREATEDBYID(Long cREATEDBYID) {
		CREATEDBYID = cREATEDBYID;
	}

	/**
	 * @return the lASTMODIFIEDBYID
	 */
	public Long getLASTMODIFIEDBYID() {
		return LASTMODIFIEDBYID;
	}

	/**
	 * @param lASTMODIFIEDBYID the lASTMODIFIEDBYID to set
	 */
	public void setLASTMODIFIEDBYID(Long lASTMODIFIEDBYID) {
		LASTMODIFIEDBYID = lASTMODIFIEDBYID;
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

	public Long getCOMMENT_TYPE_ID() {
		return COMMENT_TYPE_ID;
	}

	public void setCOMMENT_TYPE_ID(Long cOMMENT_TYPE_ID) {
		COMMENT_TYPE_ID = cOMMENT_TYPE_ID;
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
		IssuesComment other = (IssuesComment) obj;
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
		builder.append("IssuesComment [ISSUE_COMMENTID=");
		builder.append(ISSUE_COMMENTID);
		builder.append(", ISSUE_TITLE=");
		builder.append(ISSUE_TITLE);
		builder.append(", ISSUES_ID=");
		builder.append(ISSUES_ID);
		builder.append(", ISSUE_COMMENT=");
		builder.append(ISSUE_COMMENT);
		builder.append(", CREATEDBYID=");
		builder.append(CREATEDBYID);
		/*builder.append(", CREATED_EMAIL=");
		builder.append(CREATED_EMAIL);*/
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append("]");
		return builder.toString();
	}

}
