package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "CloseCashBookConfiguration")
public class CloseCashBookConfiguration implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CBCONFIG_Id")
	private Long CBCONFIG_Id;

	@Column(name = "createdOn", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;
	
	@Column(name = "createdById", nullable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	public CloseCashBookConfiguration() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CloseCashBookConfiguration(Long cBCONFIG_Id, Date createdOn, Long createdById, Long lastModifiedById,
			boolean markForDelete, Integer companyId) {
		super();
		CBCONFIG_Id = cBCONFIG_Id;
		this.createdOn = createdOn;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.markForDelete = markForDelete;
		this.companyId = companyId;
	}

	public Long getCBCONFIG_Id() {
		return CBCONFIG_Id;
	}

	public void setCBCONFIG_Id(Long cBCONFIG_Id) {
		CBCONFIG_Id = cBCONFIG_Id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "CloseCashBookConfiguration [CBCONFIG_Id=" + CBCONFIG_Id + ", createdOn=" + createdOn + ", createdById="
				+ createdById + ", lastModifiedById=" + lastModifiedById + ", markForDelete=" + markForDelete
				+ ", companyId=" + companyId + "]";
	}
}
