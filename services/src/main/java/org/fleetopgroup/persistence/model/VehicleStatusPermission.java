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
@Table(name = "VehicleStatusPermission")
public class VehicleStatusPermission implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vSPermissionId")
	private Long 	vSPermissionId;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "sid")
	private  Long 	sid;
	
	@Column(name = "vStatus")
	private String	vStatus;
	
	
	@Column(name = "createdBy", length = 50, nullable = false)
	private String createdBy;

	@Column(name = "lastModifiedBy", length = 25)
	private String lastModifiedBy;
	

	@Column(name = "lastupdated")
	private Timestamp lastupdated;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;


	
	public VehicleStatusPermission() {
		super();
	}
	
	


	public VehicleStatusPermission(Long vSPermissionId, Integer companyId, Long sid, String vStatus, String createdBy,
			String lastModifiedBy, Timestamp created, Timestamp lastupdated) {
		super();
		this.vSPermissionId = vSPermissionId;
		this.companyId = companyId;
		this.sid = sid;
		this.vStatus = vStatus;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.created = created;
		this.lastupdated = lastupdated;
	}




	public Long getvSPermissionId() {
		return vSPermissionId;
	}

	public void setvSPermissionId(Long vSPermissionId) {
		this.vSPermissionId = vSPermissionId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public String getvStatus() {
		return vStatus;
	}

	public void setvStatus(String vStatus) {
		this.vStatus = vStatus;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public Timestamp getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Timestamp lastupdated) {
		this.lastupdated = lastupdated;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Column(name = "created", nullable = false, updatable = false)
	private Timestamp created;
	

	@Override
	public String toString() {
		return "VehicleStatusPermission [vSPermissionId=" + vSPermissionId + ", companyId=" + companyId + ", sid=" + sid
				+ ", vStatus=" + vStatus + ", createdBy=" + createdBy + ", lastModifiedBy=" + lastModifiedBy
				+ ", created=" + created + ", lastupdated=" + lastupdated + ", markForDelete=" + markForDelete + "]";
	}
	
	
	
}
