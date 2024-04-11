package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class VendorMarketHireToChargesDto {

	private Long	hireToChargesId;
	
	private Integer chargeMasterId;
	
	private Long	lorryHireDetailsId;
	
	private Double	amount;
	
	private short	identifier;
	
	private Integer	companyId;
	
	private Long		createdById;
	
	private Long		lastModifiedById;
	
	private Timestamp	createdOn;
	
	private Timestamp	lastModifiedOn;

	private boolean	markForDelete;
	
	private String	chargeName;

	public Long getHireToChargesId() {
		return hireToChargesId;
	}

	public void setHireToChargesId(Long hireToChargesId) {
		this.hireToChargesId = hireToChargesId;
	}

	public Integer getChargeMasterId() {
		return chargeMasterId;
	}

	public void setChargeMasterId(Integer chargeMasterId) {
		this.chargeMasterId = chargeMasterId;
	}

	public Long getLorryHireDetailsId() {
		return lorryHireDetailsId;
	}

	public void setLorryHireDetailsId(Long lorryHireDetailsId) {
		this.lorryHireDetailsId = lorryHireDetailsId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = Utility.round( amount, 2);
	}

	public short getIdentifier() {
		return identifier;
	}

	public void setIdentifier(short identifier) {
		this.identifier = identifier;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
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

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VendorMarketHireToChargesDto [hireToChargesId=");
		builder.append(hireToChargesId);
		builder.append(", chargeMasterId=");
		builder.append(chargeMasterId);
		builder.append(", lorryHireDetailsId=");
		builder.append(lorryHireDetailsId);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", identifier=");
		builder.append(identifier);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", chargeName=");
		builder.append(chargeName);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
