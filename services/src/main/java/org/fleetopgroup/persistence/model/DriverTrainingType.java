package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "driverTrainingtype")
public class DriverTrainingType implements Serializable {

	private static final long serialVersionUID = -723583058586873479L;

	@Column(name = "dri_TrainingType", unique = false, nullable = false)
	private String dri_TrainingType;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dri_id")
	private Integer dri_id;
		
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	/*@Column(name = "createdBy", nullable = true)
	private String createdBy;*/
	
	@Column(name = "createdById")
	private Long createdById;
	
	@Column(name = "createdOn", nullable = true)
	private Timestamp createdOn;
	
	/*@Column(name = "lastModifiedBy", nullable = true)
	private String lastModifiedBy;
	*/
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/*public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}*/

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	/*public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}*/

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public DriverTrainingType() {
		super();
		
	}
	
	public DriverTrainingType(String dri_TrainingType) {
		super();
		this.dri_TrainingType = dri_TrainingType;
	}

	public String getDri_TrainingType() {
		return dri_TrainingType;
	}

	public void setDri_TrainingType(String dri_TrainingType) {
		this.dri_TrainingType = dri_TrainingType;
	}

	public Integer getDri_id() {
		return dri_id;
	}

	public void setDri_id(Integer dri_id) {
		this.dri_id = dri_id;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dri_TrainingType == null) ? 0 : dri_TrainingType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DriverTrainingType other = (DriverTrainingType) obj;
		if (dri_TrainingType == null) {
			if (other.dri_TrainingType != null)
				return false;
		} else if (!dri_TrainingType.equals(other.dri_TrainingType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverTrainingType [dri_TrainingType=");
		builder.append(dri_TrainingType);
		builder.append(", dri_id=");
		builder.append(dri_id);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append("]");
		return builder.toString();
	}

	
	
}