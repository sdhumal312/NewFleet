package org.fleetopgroup.persistence.dto;
/**
 * @author fleetop
 *
 *
 *
 */

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VehicleTypeDto {

	private Long tid;

	@NotNull
	@Size(min = 1)
	private String vtype;

	private String createdBy;

	private String lastModifiedBy;

	private Integer	maxAllowedOdometer;
	
	private Long serviceProgramId;
	
	private String programName;
	
	private Integer	companyId;
	
	private Long superProgramId;
	
	
	/**
	 * @return the tid
	 */
	public Long getTid() {
		return tid;
	}

	/**
	 * @param tid
	 *            the tid to set
	 */
	public void setTid(Long tid) {
		this.tid = tid;
	}

	/**
	 * @return the vtype
	 */
	public String getVtype() {
		return vtype;
	}

	/**
	 * @param vtype
	 *            the vtype to set
	 */
	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	public Integer getMaxAllowedOdometer() {
		return maxAllowedOdometer;
	}

	public void setMaxAllowedOdometer(Integer maxAllowedOdometer) {
		this.maxAllowedOdometer = maxAllowedOdometer;
	}

	public Long getServiceProgramId() {
		return serviceProgramId;
	}

	public void setServiceProgramId(Long serviceProgramId) {
		this.serviceProgramId = serviceProgramId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public Long getSuperProgramId() {
		return superProgramId;
	}

	public void setSuperProgramId(Long superProgramId) {
		this.superProgramId = superProgramId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleTypeDto [vtype=");
		builder.append(vtype);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append("]");
		return builder.toString();
	}

}