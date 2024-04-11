package org.fleetopgroup.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="BranchMapper")
public class BranchMapper {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long branchMapperId;
	
	private Integer fleetBranchId;
	
	private Long ivBranchId;
	
	private boolean status;
	
	private boolean markForDelete;

	public Long getBranchMapperId() {
		return branchMapperId;
	}

	public void setBranchMapperId(Long branchMapperId) {
		this.branchMapperId = branchMapperId;
	}

	public Integer getFleetBranchId() {
		return fleetBranchId;
	}

	public void setFleetBranchId(Integer fleetBranchId) {
		this.fleetBranchId = fleetBranchId;
	}

	public Long getIvBranchId() {
		return ivBranchId;
	}

	public void setIvBranchId(Long ivBranchId) {
		this.ivBranchId = ivBranchId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

}
