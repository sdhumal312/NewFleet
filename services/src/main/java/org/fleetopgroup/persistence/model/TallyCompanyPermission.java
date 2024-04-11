package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TallyCompanyPermission")
public class TallyCompanyPermission implements Serializable {

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		result = prime * result + (int) (user_id ^ (user_id >>> 32));
		result = prime * result + (int) (vg_per_id ^ (vg_per_id >>> 32));
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
		TallyCompanyPermission other = (TallyCompanyPermission) obj;
		if (user_id != other.user_id)
			return false;
		if (vg_per_id != other.vg_per_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TallyCompanyPermission [id=" + id + ", user_id=" + user_id + ", vg_per_id=" + vg_per_id + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	
	
}
