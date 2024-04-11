package org.fleetopgroup.persistence.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fleetop
 *
 */

public class RenewalSubTypeDto {

	private Integer renewal_Subid;

	@NotNull
	@Size(min = 1)
	private String renewal_Type;

	@NotNull
	@Size(min = 1)
	private String renewal_SubType;

	private String createdBy;

	private String lastModifiedBy;

	private Integer renewal_id;
	
	private boolean isMandatory;
	
	private Integer	companyId;
	
	/**
	 * @return the renewal_Subid
	 */
	public Integer getRenewal_Subid() {
		return renewal_Subid;
	}

	/**
	 * @param renewal_Subid
	 *            the renewal_Subid to set
	 */
	public void setRenewal_Subid(Integer renewal_Subid) {
		this.renewal_Subid = renewal_Subid;
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

	/**
	 * @return the renewal_SubType
	 */
	public String getRenewal_SubType() {
		return renewal_SubType;
	}

	/**
	 * @param renewal_SubType
	 *            the renewal_SubType to set
	 */
	public void setRenewal_SubType(String renewal_SubType) {
		this.renewal_SubType = renewal_SubType;
	}

	public Integer getRenewal_id() {
		return renewal_id;
	}

	public void setRenewal_id(Integer renewal_id) {
		this.renewal_id = renewal_id;
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

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RenewalSubTypeDto [renewal_Type=");
		builder.append(renewal_Type);
		builder.append(", renewal_SubType=");
		builder.append(renewal_SubType);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", isMandatory=");
		builder.append(isMandatory);
		builder.append("]");
		return builder.toString();
	}

}
