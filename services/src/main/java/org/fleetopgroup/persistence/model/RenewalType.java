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
@Table(name = "renewaltype")
public class RenewalType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "renewal_Type", unique = false, nullable = false, length = 25)
	private String renewal_Type;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "renewal_id")
	private Integer renewal_id;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "expenseId")
	private Integer expenseId;
	
	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;
	
	@Column(name = "createdOn", nullable = true, updatable = false)
	private Timestamp createdOn;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "allowToAvoid", nullable = false)
	private boolean allowToAvoid;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	
	public RenewalType(String renewal_Type) {
		super();
		this.renewal_Type = renewal_Type;
	}

	public RenewalType() {
		super();
	}

	public String getRenewal_Type() {
		return renewal_Type;
	}

	public void setRenewal_Type(String renewal_Type) {
		this.renewal_Type = renewal_Type;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/*public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}*/

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	/*public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}
*/
	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public boolean isAllowToAvoid() {
		return allowToAvoid;
	}

	public void setAllowToAvoid(boolean allowToAvoid) {
		this.allowToAvoid = allowToAvoid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((renewal_Type == null) ? 0 : renewal_Type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RenewalType other = (RenewalType) obj;
		if (renewal_Type == null) {
			if (other.renewal_Type != null)
				return false;
		} else if (!renewal_Type.equals(other.renewal_Type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RenewalType [renewal_Type=");
		builder.append(renewal_Type);
		builder.append(", company_id=");
		builder.append(companyId);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append("]");
		return builder.toString();
	}
	
	
}