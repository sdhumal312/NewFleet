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
@Table(name="VendorMarketHireToCharges")
public class VendorMarketHireToCharges implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="hireToChargesId")
	private Long	hireToChargesId;
	
	@Column(name="chargeMasterId")
	private Integer chargeMasterId;
	
	@Column(name="lorryHireDetailsId")
	private Long	lorryHireDetailsId;
	
	@Column(name="amount")
	private Double	amount;
	
	@Column(name="identifier")
	private short	identifier;
	
	@Column(name="companyId")
	private Integer	companyId;
	
	@Column(name = "createdById")
	private Long		createdById;
	
	@Column(name = "lastModifiedById")
	private Long		lastModifiedById;
	
	@Column(name = "createdOn")
	private Timestamp	createdOn;
	
	@Column(name = "lastModifiedOn")
	private Timestamp	lastModifiedOn;

	@Column(name="markForDelete")
	private boolean	markForDelete;
	
	
	

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
		this.amount = amount;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chargeMasterId == null) ? 0 : chargeMasterId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((hireToChargesId == null) ? 0 : hireToChargesId.hashCode());
		result = prime * result + ((lorryHireDetailsId == null) ? 0 : lorryHireDetailsId.hashCode());
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
		VendorMarketHireToCharges other = (VendorMarketHireToCharges) obj;
		if (chargeMasterId == null) {
			if (other.chargeMasterId != null)
				return false;
		} else if (!chargeMasterId.equals(other.chargeMasterId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (hireToChargesId == null) {
			if (other.hireToChargesId != null)
				return false;
		} else if (!hireToChargesId.equals(other.hireToChargesId))
			return false;
		if (lorryHireDetailsId == null) {
			if (other.lorryHireDetailsId != null)
				return false;
		} else if (!lorryHireDetailsId.equals(other.lorryHireDetailsId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VendorMarketHireToCharges [hireToChargesId=");
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
		builder.append("]");
		return builder.toString();
	}
	
	
}
