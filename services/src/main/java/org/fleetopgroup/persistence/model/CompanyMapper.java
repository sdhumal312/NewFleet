package org.fleetopgroup.persistence.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CompanyMapper")
public class CompanyMapper {
	
	@Id
	private Integer companyMapperId;
	
	private Integer fleetCompanyId;
	
	private Integer ivGroupId;
	
	private short ivServerIdentifier;
	
	private boolean status;
	
	private boolean markForDelete;

	public Integer getCompanyMapperId() {
		return companyMapperId;
	}

	public void setCompanyMapperId(Integer companyMapperId) {
		this.companyMapperId = companyMapperId;
	}

	public Integer getFleetCompanyId() {
		return fleetCompanyId;
	}

	public void setFleetCompanyId(Integer fleetCompanyId) {
		this.fleetCompanyId = fleetCompanyId;
	}

	public Integer getIvGroupId() {
		return ivGroupId;
	}

	public void setIvGroupId(Integer ivGroupId) {
		this.ivGroupId = ivGroupId;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public short getIvServerIdentifier() {
		return ivServerIdentifier;
	}

	public void setIvServerIdentifier(short ivServerIdentifier) {
		this.ivServerIdentifier = ivServerIdentifier;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}
