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
@Table(name = "VehicleComment")
public class VehicleComment implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VEHICLE_COMMENTID")
	private Long VEHICLE_COMMENTID;

	/** Issues Comment title */
	@Column(name = "VEHICLE_TITLE", length = 250)
	private String VEHICLE_TITLE;

	/** Issues Comment _ Which issues comment id */
	@Column(name = "VEHICLE_ID", updatable = false)
	private Integer VEHICLE_ID;

	/** Issues Comment_string */
	@Column(name = "VEHICLE_COMMENT", length = 1000)
	private String VEHICLE_COMMENT;

	/** Issues Comment_create by *//*
	@Column(name = "CREATEDBY", updatable = false, length = 150)
	private String CREATEDBY;*/

	/** Issues Comment_create by *//*
	@Column(name = "CREATED_EMAIL", updatable = false, length = 150)
	private String CREATED_EMAIL;*/
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer	COMPANY_ID;
	
	@Column(name = "CREATEDBYID", updatable = false)
	private Long CREATEDBYID;

	/** Issues Comment_status */
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the Comment_create DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;

	public VehicleComment() {
		super();
	}

	public VehicleComment(Long vEHICLE_COMMENTID, String vEHICLE_TITLE, Integer vEHICLE_ID, String vEHICLE_COMMENT,
			String cREATEDBY, String cREATED_EMAIL, boolean MarkForDelete, Date cREATED_DATE, Integer companyId) {
		super();
		VEHICLE_COMMENTID = vEHICLE_COMMENTID;
		VEHICLE_TITLE = vEHICLE_TITLE;
		VEHICLE_ID = vEHICLE_ID;
		VEHICLE_COMMENT = vEHICLE_COMMENT;
		//CREATEDBY = cREATEDBY;
		//CREATED_EMAIL = cREATED_EMAIL;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		COMPANY_ID	= companyId;
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
	 *//*
	public String getCREATEDBY() {
		return CREATEDBY;
	}

	*//**
	 * @param cREATEDBY the cREATEDBY to set
	 *//*
	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}*/

	/**
	 * @return the cREATED_EMAIL
	 *//*
	public String getCREATED_EMAIL() {
		return CREATED_EMAIL;
	}

	*//**
	 * @param cREATED_EMAIL the cREATED_EMAIL to set
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((VEHICLE_COMMENTID == null) ? 0 : VEHICLE_COMMENTID.hashCode());
		result = prime * result + ((VEHICLE_ID == null) ? 0 : VEHICLE_ID.hashCode());
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
		VehicleComment other = (VehicleComment) obj;
		if (VEHICLE_COMMENTID == null) {
			if (other.VEHICLE_COMMENTID != null)
				return false;
		} else if (!VEHICLE_COMMENTID.equals(other.VEHICLE_COMMENTID))
			return false;
		if (VEHICLE_ID == null) {
			if (other.VEHICLE_ID != null)
				return false;
		} else if (!VEHICLE_ID.equals(other.VEHICLE_ID))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleComment [VEHICLE_COMMENTID=");
		builder.append(VEHICLE_COMMENTID);
		builder.append(", VEHICLE_TITLE=");
		builder.append(VEHICLE_TITLE);
		builder.append(", VEHICLE_ID=");
		builder.append(VEHICLE_ID);
		builder.append(", VEHICLE_COMMENT=");
		builder.append(VEHICLE_COMMENT);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBYID);
	/*	builder.append(", CREATED_EMAIL=");
		builder.append(CREATED_EMAIL);*/
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append("]");
		return builder.toString();
	}

	

	
}
