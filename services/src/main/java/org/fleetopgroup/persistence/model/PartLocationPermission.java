package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PartLocationPermission")
public class PartLocationPermission implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long	 id;
	
	@Column(name = "partLocationId", nullable = false)
	private long 	partLocationId;
	
	@Column(name = "user_Id", nullable = false)
	private long		user_Id;
	
	@Column(name = "companyId", nullable = false)
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;
	
	
	public PartLocationPermission() {
		super();
	}

	public PartLocationPermission(long partLocationId, long user_Id, Integer companyId) {
		super();
		this.partLocationId = partLocationId;
		this.user_Id = user_Id;
		this.companyId = companyId;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getPartLocationId() {
		return partLocationId;
	}

	public void setPartLocationId(long partLocationId) {
		this.partLocationId = partLocationId;
	}

	public long getUser_Id() {
		return user_Id;
	}

	public void setUser_Id(long user_Id) {
		this.user_Id = user_Id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (markForDelete ? 1231 : 1237);
		result = prime * result + (int) (partLocationId ^ (partLocationId >>> 32));
		result = prime * result + (int) (user_Id ^ (user_Id >>> 32));
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
		PartLocationPermission other = (PartLocationPermission) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (markForDelete != other.markForDelete)
			return false;
		if (partLocationId != other.partLocationId)
			return false;
		if (user_Id != other.user_Id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PartLocationPermission [id=");
		builder.append(id);
		builder.append(", partLocationId=");
		builder.append(partLocationId);
		builder.append(", user_Id=");
		builder.append(user_Id);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

	
}
