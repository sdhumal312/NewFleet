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
@Table(name = "RenewalTypeHistory")
public class RenewalTypeHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RenewalTypeHistoryId")
	private Integer RenewalTypeHistoryId;

	@Column(name = "renewal_id")
	private Integer renewal_id;

	@Column(name = "renewal_Type", unique = false, nullable = false, length = 25)
	private String renewal_Type;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "allowToAvoid", nullable = false)
	private boolean allowToAvoid;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public Integer getRenewalTypeHistoryId() {
		return RenewalTypeHistoryId;
	}

	public void setRenewalTypeHistoryId(Integer renewalTypeHistoryId) {
		RenewalTypeHistoryId = renewalTypeHistoryId;
	}

	public Integer getRenewal_id() {
		return renewal_id;
	}

	public void setRenewal_id(Integer renewal_id) {
		this.renewal_id = renewal_id;
	}

	public String getRenewal_Type() {
		return renewal_Type;
	}

	public void setRenewal_Type(String renewal_Type) {
		this.renewal_Type = renewal_Type;
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

	public boolean isAllowToAvoid() {
		return allowToAvoid;
	}

	public void setAllowToAvoid(boolean allowToAvoid) {
		this.allowToAvoid = allowToAvoid;
	}
}