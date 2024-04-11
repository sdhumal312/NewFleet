/**
 * 
 */
package org.fleetopgroup.persistence.model;

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

/**
 * @author fleetop
 *
 */
@Entity
@Table(name = "VehicleMandatory")
public class VehicleMandatory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "VMID")
	private Long VMID;

	/** The value for the Vehicle Mandatory Vehicle ID field */
	@Column(name = "VEHICLE_ID")
	private Integer VEHICLE_ID;

	@Column(name = "MANDATORY_RENEWAL_SUBID", nullable = false)
	private Integer MANDATORY_RENEWAL_SUBID;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	/*@Column(name = "CREATEDBY", updatable = false, length = 150)
	private String CREATEDBY;

	@Column(name = "LASTMODIFIEDBY", length = 150)
	private String LASTMODIFIEDBY;*/
	
	@Column(name = "CREATEDBYID", updatable = false)
	private Long CREATEDBYID;
	
	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "LASTUPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date LASTUPDATED_DATE;

	public VehicleMandatory() {
		super();
	}

	public VehicleMandatory(Long vMID, Integer vEHICLE_ID,  Long cREATEDBY,
			Long lASTMODIFIEDBY, boolean MarkForDelete, Date cREATED_DATE, Date lASTUPDATED_DATE, Integer cOMPANY_ID) {
		super();
		VMID = vMID;
		VEHICLE_ID = vEHICLE_ID;
		CREATEDBYID = cREATEDBY;
		LASTMODIFIEDBYID = lASTMODIFIEDBY;
		markForDelete = MarkForDelete;
		CREATED_DATE = cREATED_DATE;
		LASTUPDATED_DATE = lASTUPDATED_DATE;
		COMPANY_ID	= cOMPANY_ID;
	}

	/**
	 * @return the vMID
	 */
	public Long getVMID() {
		return VMID;
	}

	/**
	 * @param vMID
	 *            the vMID to set
	 */
	public void setVMID(Long vMID) {
		VMID = vMID;
	}

	/**
	 * @return the vEHICLE_ID
	 */
	public Integer getVEHICLE_ID() {
		return VEHICLE_ID;
	}

	/**
	 * @param vEHICLE_ID
	 *            the vEHICLE_ID to set
	 */
	public void setVEHICLE_ID(Integer vEHICLE_ID) {
		VEHICLE_ID = vEHICLE_ID;
	}

	
	public Integer getMANDATORY_RENEWAL_SUBID() {
		return MANDATORY_RENEWAL_SUBID;
	}

	public void setMANDATORY_RENEWAL_SUBID(Integer mANDATORY_RENEWAL_SUBID) {
		MANDATORY_RENEWAL_SUBID = mANDATORY_RENEWAL_SUBID;
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

	*//**
	 * @return the lASTMODIFIEDBY
	 *//*
	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	*//**
	 * @param lASTMODIFIEDBY
	 *            the lASTMODIFIEDBY to set
	 *//*
	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}
*/
	/**
	 * @return the sTATUS
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}


	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
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
	 * @return the lASTUPDATED_DATE
	 */
	public Date getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	/**
	 * @param lASTUPDATED_DATE
	 *            the lASTUPDATED_DATE to set
	 */
	public void setLASTUPDATED_DATE(Date lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
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
		result = prime * result + ((VEHICLE_ID == null) ? 0 : VEHICLE_ID.hashCode());
		result = prime * result + ((VMID == null) ? 0 : VMID.hashCode());
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
		VehicleMandatory other = (VehicleMandatory) obj;
		if (VEHICLE_ID == null) {
			if (other.VEHICLE_ID != null)
				return false;
		} else if (!VEHICLE_ID.equals(other.VEHICLE_ID))
			return false;
		if (VMID == null) {
			if (other.VMID != null)
				return false;
		} else if (!VMID.equals(other.VMID))
			return false;
		return true;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleMandatory [VMID=");
		builder.append(VMID);
		builder.append(", VEHICLE_ID=");
		builder.append(VEHICLE_ID);
		builder.append(", MANDATORY_RENEWAL_SUBID=");
		builder.append(MANDATORY_RENEWAL_SUBID);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", CREATEDBYID=");
		builder.append(CREATEDBYID);
		builder.append(", LASTMODIFIEDBYID=");
		builder.append(LASTMODIFIEDBYID);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append("]");
		return builder.toString();
	}

}
