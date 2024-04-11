package org.fleetopgroup.persistence.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fleetop
 *
 */

public class RenewalTypeDto {

	private Integer renewal_id;

	@NotNull
	@Size(min = 1)
	private String renewal_Type;

	private String createdBy;

	private String lastModifiedBy;
	
	private Integer expenseId;
	
	private String tallyExpenseName;
	
	private Integer	companyId;
	
	private boolean allowToAvoid;
	
	

	/**
	 * @return the renewal_id
	 */
	public Integer getRenewal_id() {
		return renewal_id;
	}

	/**
	 * @param renewal_id
	 *            the renewal_id to set
	 */
	public void setRenewal_id(Integer renewal_id) {
		this.renewal_id = renewal_id;
	}

	/**
	 * @return the renewal_Type
	 */
	public String getRenewal_Type() {
		return renewal_Type;
	}

	/**
	 * @param renewal_Type
	 *            the renewal_Type to set
	 */
	public void setRenewal_Type(String renewal_Type) {
		this.renewal_Type = renewal_Type;
	}

	
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Integer getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Integer expenseId) {
		this.expenseId = expenseId;
	}

	public String getTallyExpenseName() {
		return tallyExpenseName;
	}

	public void setTallyExpenseName(String tallyExpenseName) {
		this.tallyExpenseName = tallyExpenseName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public boolean isAllowToAvoid() {
		return allowToAvoid;
	}

	public void setAllowToAvoid(boolean allowToAvoid) {
		this.allowToAvoid = allowToAvoid;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RenewalTypeDto [renewal_Type=");
		builder.append(renewal_Type);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append("]");
		return builder.toString();
	}

	

}