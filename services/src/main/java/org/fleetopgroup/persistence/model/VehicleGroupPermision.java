package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleGroupPermision")
public class VehicleGroupPermision implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "user_id", nullable = false)
	private long user_id;

	@Column(name = "vg_per_id", nullable = false)
	private long vg_per_id;
	
	@Column(name = "companyId", nullable = false)
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;

	public VehicleGroupPermision() {
		super();
	}

	public VehicleGroupPermision(long user_id, long vg_per_id) {
		super();
		this.user_id = user_id;
		this.vg_per_id = vg_per_id;
	}

	public long getUser_id() {
		return user_id;
	}

	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}

	public long getVg_per_id() {
		return vg_per_id;
	}

	public void setVg_per_id(long vg_per_id) {
		this.vg_per_id = vg_per_id;
	}
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (int) (user_id ^ (user_id >>> 32));
		result = prime * result + (int) (vg_per_id ^ (vg_per_id >>> 32));
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
		VehicleGroupPermision other = (VehicleGroupPermision) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (user_id != other.user_id)
			return false;
		if (vg_per_id != other.vg_per_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleGroupPermision [id=");
		builder.append(id);
		builder.append(", user_id=");
		builder.append(user_id);
		builder.append(", vg_per_id=");
		builder.append(vg_per_id);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

}