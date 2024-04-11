package org.fleetopgroup.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleGroupWiseCompanyName")
public class VehicleGroupWiseCompanyName {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "groupWiseCompanyNameId")
	private Long	groupWiseCompanyNameId;
	
	@Column(name = "vehicleGroupId")
	private Long	vehicleGroupId;
	
	@Column(name = "companyName")
	private String	companyName;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;

	public Long getGroupWiseCompanyNameId() {
		return groupWiseCompanyNameId;
	}

	public void setGroupWiseCompanyNameId(Long groupWiseCompanyNameId) {
		this.groupWiseCompanyNameId = groupWiseCompanyNameId;
	}

	public Long getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(Long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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
	
	
}
