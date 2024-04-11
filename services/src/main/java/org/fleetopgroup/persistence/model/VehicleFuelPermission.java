package org.fleetopgroup.persistence.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleFuelPermission")
public class VehicleFuelPermission {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vFPId")
	private Long vFPId;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "fid", nullable = false)
	private Long fid;
	
	@Column(name = "vFuel", nullable = false)
	private String vFuel;
	
	
	@Column(name = "createdBy", length = 50, nullable = false)
	private String createdBy;
	
	
	@Column(name = "createdOn")
	private Timestamp createdOn;

	@Column(name = "lastModifiedBy", length = 25)
	private String lastModifiedBy;
	

	@Column(name = "lastupdated")
	private Timestamp lastupdated;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	
	
	public VehicleFuelPermission() {
		super();
	}
	
	
	
	

	public VehicleFuelPermission(Long vFPId, Integer companyId, Long fid, String vFuel, String createdBy,
			Timestamp createdOn, String lastModifiedBy, Timestamp lastupdated) {
		super();
		this.vFPId = vFPId;
		this.companyId = companyId;
		this.fid = fid;
		this.vFuel = vFuel;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.lastModifiedBy = lastModifiedBy;
		this.lastupdated = lastupdated;
	}





	public Long getvFPId() {
		return vFPId;
	}

	public void setvFPId(Long vFPId) {
		this.vFPId = vFPId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getFid() {
		return fid;
	}

	public void setFid(Long fid) {
		this.fid = fid;
	}

	public String getvFuel() {
		return vFuel;
	}

	public void setvFuel(String vFuel) {
		this.vFuel = vFuel;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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





	@Override
	public String toString() {
		return "VehicleFuelPermission [vFPId=" + vFPId + ", companyId=" + companyId + ", fid=" + fid + ", vFuel="
				+ vFuel + ", createdBy=" + createdBy + ", createdOn=" + createdOn + ", lastModifiedBy=" + lastModifiedBy
				+ ", lastupdated=" + lastupdated + ", markForDelete=" + markForDelete + "]";
	}
	
	
}
