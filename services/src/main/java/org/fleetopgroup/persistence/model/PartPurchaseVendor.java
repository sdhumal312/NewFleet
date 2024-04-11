package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PartPurchaseVendor")
public class PartPurchaseVendor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "partPurchaseVendorId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	partPurchaseVendorId;
	
	@Column(name = "vendorId")
	private Integer vendorId;
	
	@Column(name = "partId")
	private Long	partId;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;

	public Long getPartPurchaseVendorId() {
		return partPurchaseVendorId;
	}

	public void setPartPurchaseVendorId(Long partPurchaseVendorId) {
		this.partPurchaseVendorId = partPurchaseVendorId;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
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
