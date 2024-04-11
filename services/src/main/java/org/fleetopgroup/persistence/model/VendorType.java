package org.fleetopgroup.persistence.model;
/**
 * @author fleetop
 *
 *
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
@Table(name="vendortype")
public class VendorType implements Serializable{

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vendor_Typeid")
	private Long vendor_Typeid;
	
	@Column(name="vendor_TypeName", unique = false, nullable = false)
	private String vendor_TypeName;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", updatable = true)
	private Long createdById;
	
	@Column(name = "createdOn", nullable = true)
	private Timestamp createdOn;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@Column(name = "isCommonMaster", nullable = false)
	private boolean isCommonMaster;

	public Long getVendor_Typeid() {
		return vendor_Typeid;
	}

	public void setVendor_Typeid(Long vendor_Typeid) {
		this.vendor_Typeid = vendor_Typeid;
	}

	public String getVendor_TypeName() {
		return vendor_TypeName;
	}

	public void setVendor_TypeName(String vendor_TypeName) {
		this.vendor_TypeName = vendor_TypeName;
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

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
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

	public boolean isCommonMaster() {
		return isCommonMaster;
	}

	public void setCommonMaster(boolean isCommonMaster) {
		this.isCommonMaster = isCommonMaster;
	}

	@Override
	public String toString() {
		return "VendorType [vendor_Typeid=" + vendor_Typeid + ", vendor_TypeName=" + vendor_TypeName + ", companyId="
				+ companyId + ", createdById=" + createdById + ", createdOn=" + createdOn + ", lastModifiedById="
				+ lastModifiedById + ", lastModifiedOn=" + lastModifiedOn + ", markForDelete=" + markForDelete
				+ ", isCommonMaster=" + isCommonMaster + "]";
	}
}
