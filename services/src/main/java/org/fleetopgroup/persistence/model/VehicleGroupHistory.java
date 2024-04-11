package org.fleetopgroup.persistence.model;

/**
 * @author Ashish Tiwari
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
@Table(name = "vehiclegrouphistory")
public class VehicleGroupHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicle_group_history_id")
	private Long vehicle_group_history_id;
	
	@Column(name = "gid")
	private Long gid;

	@Column(name = "vGroup", unique = false, nullable = false, length = 25)
	private String vGroup;

	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "lastupdated", nullable = false, updatable = false)
	private Date lastupdated;

	@Column(name = "company_Id", nullable = false)
	Integer company_Id;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public VehicleGroupHistory() {
		super();
	}

	public Long getVehicle_group_history_id() {
		return vehicle_group_history_id;
	}

	public void setVehicle_group_history_id(Long vehicle_group_history_id) {
		this.vehicle_group_history_id = vehicle_group_history_id;
	}

	public Long getGid() {
		return gid;
	}

	public void setGid(Long gid) {
		this.gid = gid;
	}

	public String getvGroup() {
		return vGroup;
	}

	public void setvGroup(String vGroup) {
		this.vGroup = vGroup;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Integer getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(Integer company_Id) {
		this.company_Id = company_Id;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}