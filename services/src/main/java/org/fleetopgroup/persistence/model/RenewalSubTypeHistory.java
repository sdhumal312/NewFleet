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
@Table(name = "RenewalSubTypeHistory")
public class RenewalSubTypeHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RenewalSubTypeHistoryId")
	private Integer RenewalSubTypeHistoryId;

	@Column(name = "renewal_Subid")
	private Integer renewal_Subid;
	
	@Column(name = "renewal_SubType", unique = false, nullable = true, length = 25)
	private String renewal_SubType;

	@Column(name = "renewal_id", nullable = false)
	private Integer renewal_id;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	@Column(name = "isMandatory")
	boolean isMandatory;

	public Integer getRenewalSubTypeHistoryId() {
		return RenewalSubTypeHistoryId;
	}

	public void setRenewalSubTypeHistoryId(Integer renewalSubTypeHistoryId) {
		RenewalSubTypeHistoryId = renewalSubTypeHistoryId;
	}

	public Integer getRenewal_Subid() {
		return renewal_Subid;
	}

	public void setRenewal_Subid(Integer renewal_Subid) {
		this.renewal_Subid = renewal_Subid;
	}

	public String getRenewal_SubType() {
		return renewal_SubType;
	}

	public void setRenewal_SubType(String renewal_SubType) {
		this.renewal_SubType = renewal_SubType;
	}

	public Integer getRenewal_id() {
		return renewal_id;
	}

	public void setRenewal_id(Integer renewal_id) {
		this.renewal_id = renewal_id;
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

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	
	
}