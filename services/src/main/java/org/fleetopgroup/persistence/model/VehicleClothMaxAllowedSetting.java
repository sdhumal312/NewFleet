package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleClothMaxAllowedSetting")
public class VehicleClothMaxAllowedSetting implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "maxAllowedSettingId")
	private Long maxAllowedSettingId;
	
	@Column(name = "vid")
	private Integer	vid;
	
	@Column(name = "clothTypesId")
	private Long clothTypesId;

	@Column(name = "maxAllowedQuantity")
	private Double maxAllowedQuantity;
	
	@Column(name = "remark")
	private String	remark;
	
	@Column(name = "companyId")
	private Integer companyId;
	
	@Column(name = "createdById")
	private Long	createdById;

	@Column(name = "lastModifiedById")	
	private Long	lastModifiedById;

	@Column(name = "creationDate")
	private Date	creationDate;

	@Column(name = "lastUpdatedDate")
	private Date	lastUpdatedDate;

	@Column(name = "markForDelete")
	private boolean	markForDelete;
	
	public VehicleClothMaxAllowedSetting() {
		super();
	}
	
	public VehicleClothMaxAllowedSetting(Long maxAllowedSettingId, Integer	vid, Long clothTypesId, Double maxAllowedQuantity,
			String	remark, Integer companyId, Long	createdById, Long lastModifiedById, Date creationDate, Date	lastUpdatedDate, 
			boolean	markForDelete ) {
		super();
		this.maxAllowedSettingId = maxAllowedSettingId;
		this.vid = vid;
		this.clothTypesId = clothTypesId;
		this.maxAllowedQuantity = maxAllowedQuantity;
		this.remark = remark;
		this.companyId = companyId;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.creationDate = creationDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.markForDelete = markForDelete;
	}

	public Long getMaxAllowedSettingId() {
		return maxAllowedSettingId;
	}

	public void setMaxAllowedSettingId(Long maxAllowedSettingId) {
		this.maxAllowedSettingId = maxAllowedSettingId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public Double getMaxAllowedQuantity() {
		return maxAllowedQuantity;
	}

	public void setMaxAllowedQuantity(Double maxAllowedQuantity) {
		this.maxAllowedQuantity = maxAllowedQuantity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "VehicleClothMaxAllowedSetting [maxAllowedSettingId=" + maxAllowedSettingId + ", vid=" + vid
				+ ", clothTypesId=" + clothTypesId + ", maxAllowedQuantity=" + maxAllowedQuantity + ", remark=" + remark
				+ ", companyId=" + companyId + ", createdById=" + createdById + ", lastModifiedById=" + lastModifiedById
				+ ", creationDate=" + creationDate + ", lastUpdatedDate=" + lastUpdatedDate + ", markForDelete="
				+ markForDelete + "]";
	}
	
	
}	