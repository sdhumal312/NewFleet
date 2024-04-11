package org.fleetopgroup.persistence.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fleetop
 *
 */

public class VehicleGroupDto {

	private Long gid;

	@NotNull
	@Size(min = 1)
	private String vGroup;

	private String createdBy;

	private String lastModifiedBy;

	/**
	 * @return the gid
	 */
	public Long getGid() {
		return gid;
	}

	/**
	 * @param gid
	 *            the gid to set
	 */
	public void setGid(Long gid) {
		this.gid = gid;
	}

	/**
	 * @return the vGroup
	 */
	public String getvGroup() {
		return vGroup;
	}

	/**
	 * @param vGroup
	 *            the vGroup to set
	 */
	public void setvGroup(String vGroup) {
		this.vGroup = vGroup;
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
		builder.append("VehicleGroupDto [vGroup=");
		builder.append(vGroup);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append("]");
		return builder.toString();
	}

}