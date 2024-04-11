package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="InspectionParameter")
public class InspectionParameter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "inspectionParameterId")
	private Long		inspectionParameterId;
	
	@Column(name = "parameterName", nullable = false)
	private String		parameterName;
	
	@Column(name = "parameterPhotoId")
	private Long		parameterPhotoId;
	
	@Column(name = "createdById")
	private Long		createdById;
	
	@Column(name = "lastModifiedById")
	private Long		lastModifiedById;
	
	@Column(name = "createdOn")
	private Timestamp	createdOn;
	
	@Column(name = "lastModifiedOn")
	private Timestamp	lastModifiedOn;
	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	public InspectionParameter() {
		super();
	}
	
	
	

	public InspectionParameter(Long inspectionParameterId, String parameterName, Long parameterPhotoId,
			Long createdById, Long lastModifiedById, Timestamp createdOn, Timestamp lastModifiedOn,
			boolean markForDelete) {
		super();
		this.inspectionParameterId = inspectionParameterId;
		this.parameterName = parameterName;
		this.parameterPhotoId = parameterPhotoId;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.createdOn = createdOn;
		this.lastModifiedOn = lastModifiedOn;
		this.markForDelete = markForDelete;
	}


	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getInspectionParameterId() {
		return inspectionParameterId;
	}

	public void setInspectionParameterId(Long inspectionParameterId) {
		this.inspectionParameterId = inspectionParameterId;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public Long getParameterPhotoId() {
		return parameterPhotoId;
	}

	public void setParameterPhotoId(Long parameterPhotoId) {
		this.parameterPhotoId = parameterPhotoId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdById == null) ? 0 : createdById.hashCode());
		result = prime * result + ((inspectionParameterId == null) ? 0 : inspectionParameterId.hashCode());
		result = prime * result + ((parameterName == null) ? 0 : parameterName.hashCode());
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
		InspectionParameter other = (InspectionParameter) obj;
		if (createdById == null) {
			if (other.createdById != null)
				return false;
		} else if (!createdById.equals(other.createdById))
			return false;
		if (inspectionParameterId == null) {
			if (other.inspectionParameterId != null)
				return false;
		} else if (!inspectionParameterId.equals(other.inspectionParameterId))
			return false;
		if (parameterName == null) {
			if (other.parameterName != null)
				return false;
		} else if (!parameterName.equals(other.parameterName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InspectionParameter [inspectionParameterId=" + inspectionParameterId + ", parameterName="
				+ parameterName + ", parameterPhotoId=" + parameterPhotoId + ", createdById=" + createdById
				+ ", lastModifiedById=" + lastModifiedById + ", createdOn=" + createdOn + ", lastModifiedOn="
				+ lastModifiedOn + ", markForDelete=" + markForDelete + "]";
	}
	
	
}
