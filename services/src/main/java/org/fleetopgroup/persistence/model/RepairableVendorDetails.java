package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RepairableVendorDetails")
public class RepairableVendorDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "repairableVendorDetailsId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	repairableVendorDetailsId;
	
	@Column(name = "vendorId")
	private Integer vendorId;
	
	@Column(name = "partId")
	private Long	partId;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public Long getRepairableVendorDetailsId() {
		return repairableVendorDetailsId;
	}

	public void setRepairableVendorDetailsId(Long repairableVendorDetailsId) {
		this.repairableVendorDetailsId = repairableVendorDetailsId;
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
