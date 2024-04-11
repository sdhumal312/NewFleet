package org.fleetopgroup.persistence.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fleetop
 *
 *
 *
 */
public class JobTypeDto {

	private Integer Job_id;
	
	@NotNull
	@Size(min = 1)
	private String Job_Type;
	
	private Integer companyId;
	
	

	public Integer getJob_id() {
		return Job_id;
	}

	public void setJob_id(Integer Job_id) {
		this.Job_id = Job_id;
	}

	public String getJob_Type() {
		return Job_Type;
	}

	public void setJob_Type(String Job_Type) {
		this.Job_Type = Job_Type;
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
		builder.append("JobTypeDto [Job_id=");
		builder.append(Job_id);
		builder.append(", Job_Type=");
		builder.append(Job_Type);
		builder.append("]");
		return builder.toString();
	}
	
	

}
