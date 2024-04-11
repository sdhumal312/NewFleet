package org.fleetopgroup.persistence.model;
/**
 * @author fleetop
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
@Table(name="vehiclephotypehistory")
public class VehiclePhoTypeHistory implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vehicle_pho_type_history_id")
	private Long vehicle_pho_type_history_id;

	@Column(name="ptid")
	private Long ptid;

	@Column(name="vPhoType", unique = false, nullable = false, length=25)
	private String vPhoType;

	@Column(name = "company_Id ", nullable = false)
	private Integer	company_Id;
	
	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	public VehiclePhoTypeHistory() {
		super();
	}

	public Long getVehicle_pho_type_history_id() {
		return vehicle_pho_type_history_id;
	}

	public void setVehicle_pho_type_history_id(Long vehicle_pho_type_history_id) {
		this.vehicle_pho_type_history_id = vehicle_pho_type_history_id;
	}

	public Long getPtid() {
		return ptid;
	}

	public void setPtid(Long ptid) {
		this.ptid = ptid;
	}

	public String getvPhoType() {
		return vPhoType;
	}

	public void setvPhoType(String vPhoType) {
		this.vPhoType = vPhoType;
	}

	public Integer getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(Integer company_Id) {
		this.company_Id = company_Id;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
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
}