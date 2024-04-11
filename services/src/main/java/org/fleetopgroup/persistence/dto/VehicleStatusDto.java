package org.fleetopgroup.persistence.dto;
/**
 * @author fleetop
 *
 *
 *
 */

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class VehicleStatusDto {

	private Long sid;

	@NotNull
	@Size(min = 1)
	private String vStatus;

	private String createdBy;

	private String lastModifiedBy;

	/**
	 * @return the sid
	 */
	public Long getSid() {
		return sid;
	}

	/**
	 * @param sid
	 *            the sid to set
	 */
	public void setSid(Long sid) {
		this.sid = sid;
	}

	/**
	 * @return the vStatus
	 */
	public String getvStatus() {
		return vStatus;
	}

	/**
	 * @param vStatus
	 *            the vStatus to set
	 */
	public void setvStatus(String vStatus) {
		this.vStatus = vStatus;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleStatusDto [vStatus=");
		builder.append(vStatus);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append("]");
		return builder.toString();
	}

}