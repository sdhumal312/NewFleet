package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DriverJobTypeHistory")
public class DriverJobTypeHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "driverJobTypeHistoryId")
	private Integer driverJobTypeHistoryId;

	@Column(name = "driJobId")
	private Integer driJobId;

	@Column(name = "driJobType", unique = false, nullable = false)
	private String driJobType;

	@Column(name = "driJobRemarks", length = 200)
	private String driJobRemarks;

	/** The value for the driJob_delete field */
	@Basic(optional = true)
	@Column(name = "driJob_deleteable", nullable = true)
	private boolean driJob_deleteable = true;

	/** The value for the driJob_delete field */
	@Basic(optional = true)
	@Column(name = "driJob_editable", nullable = true)
	private boolean driJob_editable = true;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public Integer getDriverJobTypeHistoryId() {
		return driverJobTypeHistoryId;
	}

	public void setDriverJobTypeHistoryId(Integer driverJobTypeHistoryId) {
		this.driverJobTypeHistoryId = driverJobTypeHistoryId;
	}

	public Integer getDriJobId() {
		return driJobId;
	}

	public void setDriJobId(Integer driJobId) {
		this.driJobId = driJobId;
	}

	public String getDriJobType() {
		return driJobType;
	}

	public void setDriJobType(String driJobType) {
		this.driJobType = driJobType;
	}

	public String getDriJobRemarks() {
		return driJobRemarks;
	}

	public void setDriJobRemarks(String driJobRemarks) {
		this.driJobRemarks = driJobRemarks;
	}

	public boolean isDriJob_deleteable() {
		return driJob_deleteable;
	}

	public void setDriJob_deleteable(boolean driJob_deleteable) {
		this.driJob_deleteable = driJob_deleteable;
	}

	public boolean isDriJob_editable() {
		return driJob_editable;
	}

	public void setDriJob_editable(boolean driJob_editable) {
		this.driJob_editable = driJob_editable;
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