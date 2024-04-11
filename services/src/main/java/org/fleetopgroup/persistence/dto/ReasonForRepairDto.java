package org.fleetopgroup.persistence.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fleetop
 *
 *
 *
 */
public class ReasonForRepairDto {

	private Integer Reason_id;
	
	@NotNull
	@Size(min = 1)
	private String Reason_Type;
	
	private Integer companyId;

	public ReasonForRepairDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReasonForRepairDto(Integer reason_id, String reason_Type, Integer companyId) {
		super();
		Reason_id = reason_id;
		Reason_Type = reason_Type;
		this.companyId = companyId;
	}

	public Integer getReason_id() {
		return Reason_id;
	}

	public void setReason_id(Integer reason_id) {
		Reason_id = reason_id;
	}

	public String getReason_Type() {
		return Reason_Type;
	}

	public void setReason_Type(String reason_Type) {
		Reason_Type = reason_Type;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "ReasonForRepairDto [Reason_id=" + Reason_id + ", Reason_Type=" + Reason_Type + ", companyId="
				+ companyId + "]";
	}

}
