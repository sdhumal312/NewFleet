package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
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
@Table(name = "ReasonForRepairType")
public class ReasonForRepairType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "Reason_Type", unique = false, nullable = false, length = 150)
	private String Reason_Type;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Reason_id")
	private Integer Reason_id;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdBy", nullable = true)
	private String createdBy;

	@Column(name = "createdById", nullable = false)
	private Long createdById;
	
	@Column(name = "createdOn", nullable = false)
	private Timestamp createdOn;
	
	@Column(name = "lastModifiedBy", nullable = true)
	private String lastModifiedBy;

	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public ReasonForRepairType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReasonForRepairType(String reason_Type, Integer reason_id, Integer companyId, String createdBy,
			Long createdById, Timestamp createdOn, String lastModifiedBy, Long lastModifiedById,
			Timestamp lastModifiedOn, boolean markForDelete) {
		super();
		Reason_Type = reason_Type;
		Reason_id = reason_id;
		this.companyId = companyId;
		this.createdBy = createdBy;
		this.createdById = createdById;
		this.createdOn = createdOn;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedById = lastModifiedById;
		this.lastModifiedOn = lastModifiedOn;
		this.markForDelete = markForDelete;
	}

	public String getReason_Type() {
		return Reason_Type;
	}


	public void setReason_Type(String reason_Type) {
		Reason_Type = reason_Type;
	}

	public Integer getReason_id() {
		return Reason_id;
	}

	public void setReason_id(Integer reason_id) {
		Reason_id = reason_id;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ReasonForRepairType [Reason_Type=" + Reason_Type + ", Reason_id=" + Reason_id + ", companyId="
				+ companyId + ", createdBy=" + createdBy + ", createdById=" + createdById + ", createdOn=" + createdOn
				+ ", lastModifiedBy=" + lastModifiedBy + ", lastModifiedById=" + lastModifiedById + ", lastModifiedOn="
				+ lastModifiedOn + ", markForDelete=" + markForDelete + "]";
	}

	
}