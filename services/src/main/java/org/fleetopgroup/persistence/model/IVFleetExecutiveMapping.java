package org.fleetopgroup.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IVFleetExecutiveMapping")
public class IVFleetExecutiveMapping {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ivFleetExecutiveMappingId;
	
	private Long fleetExecutiveId;
	
	private Long ivExecutiveId;
	
	private boolean status;
	
	private boolean markForDelete;

	public Long getIvFleetExecutiveMappingId() {
		return ivFleetExecutiveMappingId;
	}

	public void setIvFleetExecutiveMappingId(Long ivFleetExecutiveMappingId) {
		this.ivFleetExecutiveMappingId = ivFleetExecutiveMappingId;
	}

	public Long getFleetExecutiveId() {
		return fleetExecutiveId;
	}

	public void setFleetExecutiveId(Long fleetExecutiveId) {
		this.fleetExecutiveId = fleetExecutiveId;
	}

	public Long getIvExecutiveId() {
		return ivExecutiveId;
	}

	public void setIvExecutiveId(Long ivExecutiveId) {
		this.ivExecutiveId = ivExecutiveId;
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
