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
@Table(name="VendorTypeHistory")
public class VendorTypeHistory implements Serializable{

	private static final long serialVersionUID = -723583058586873479L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="VendorTypeHistoryId")
	private Long VendorTypeHistoryId;

	@Column(name="vendor_Typeid")
	private Long vendor_Typeid;
	
	@Column(name="vendor_TypeName", unique = false, nullable = false)
	private String vendor_TypeName;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public Long getVendorTypeHistoryId() {
		return VendorTypeHistoryId;
	}

	public void setVendorTypeHistoryId(Long vendorTypeHistoryId) {
		VendorTypeHistoryId = vendorTypeHistoryId;
	}

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
