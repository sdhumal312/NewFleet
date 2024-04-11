package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vehicletype")
public class VehicleType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tid")
	private Long tid;

	@Column(name = "vtype", unique = false, nullable = false, length = 25)
	private String vtype;
	
	@Column(name = "maxAllowedOdometer")
	private Integer	maxAllowedOdometer;

	@Column(name = "createdBy", length = 25)
	private String createdBy;

	@Column(name = "lastModifiedBy", length = 25)
	private String lastModifiedBy;


	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "lastupdated", nullable = false, updatable = false)
	private Date lastupdated;

	@Column(name = "company_Id", nullable = false)
	private Integer	company_Id;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	@Column(name = "serviceProgramId")
	private Long serviceProgramId;
	
	@Column(name = "superProgramId")
	private Long superProgramId;
	
	

	public VehicleType() {
		super();
	}
	/**
	 * @return the tid
	 */
	public Long getTid() {
		return tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(Long tid) {
		this.tid = tid;
	}

	/**
	 * @return the vtype
	 */
	public String getVtype() {
		return vtype;
	}

	/**
	 * @param vtype
	 *            the vtype to set
	 */
	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the status
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Integer getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(Integer company_Id) {
		this.company_Id = company_Id;
	}

	
	public Integer getMaxAllowedOdometer() {
		return maxAllowedOdometer;
	}
	public void setMaxAllowedOdometer(Integer maxAllowedOdometer) {
		this.maxAllowedOdometer = maxAllowedOdometer;
	}
	public Long getServiceProgramId() {
		return serviceProgramId;
	}
	public void setServiceProgramId(Long serviceProgramId) {
		this.serviceProgramId = serviceProgramId;
	}
	public Long getSuperProgramId() {
		return superProgramId;
	}
	public void setSuperProgramId(Long superProgramId) {
		this.superProgramId = superProgramId;
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
		result = prime * result + ((vtype == null) ? 0 : vtype.hashCode());
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
		VehicleType other = (VehicleType) obj;
		if (vtype == null) {
			if (other.vtype != null)
				return false;
		} else if (!vtype.equals(other.vtype))
			return false;
		return true;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleType [tid=");
		builder.append(tid);
		builder.append(", vtype=");
		builder.append(vtype);
		builder.append(", maxAllowedOdometer=");
		builder.append(maxAllowedOdometer);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", company_Id=");
		builder.append(company_Id);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

	
}