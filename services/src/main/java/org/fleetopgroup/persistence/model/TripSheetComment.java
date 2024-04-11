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
@Table(name = "TripSheetComment")
public class TripSheetComment implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRIPCID")
	private Long TRIPCID;

	/** Issues Comment _ Which issues comment id */
	@Column(name = "TRIPSHEETID", updatable = false)
	private Long TRIPSHEETID;

	/** Issues Comment_string */
	@Column(name = "TRIP_COMMENT", length = 1000)
	private String TRIP_COMMENT;

	/** Issues Comment_create by */
	@Column(name = "CREATEDBY", updatable = false, length = 150)
	private String CREATEDBY;

	/** Issues Comment_string */
	@Column(name = "CREATED_PLACE", length = 150)
	private String CREATED_PLACE;

	/** Issues Comment_create by */
	@Column(name = "CREATED_EMAIL", updatable = false, length = 150)
	private String CREATED_EMAIL;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	/** Issues Comment_status */
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the Comment_create DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;

	public TripSheetComment() {
		super();
	}

	public TripSheetComment(Long tRIPCID, Long tRIPSHEETID, String tRIP_COMMENT, String cREATEDBY, String cREATED_PLACE,
			String cREATED_EMAIL, boolean MarkForDelete, Date cREATED_DATE) {
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

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
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
		TripSheetComment other = (TripSheetComment) obj;
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
		builder.append("TripSheetComment [TRIPCID=");
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
		builder.append("]");
		return builder.toString();
	}

}
