package org.fleetopgroup.persistence.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fleetop
 *
 */

public class VehicleFuelDto {

	private Long fid;

	@NotNull
	@Size(min = 1)
	private String vFuel;

	private String createdBy;

	private String lastModifiedBy;

	/**
	 * @return the fid
	 */
	public Long getFid() {
		return fid;
	}

	/**
	 * @param fid
	 *            the fid to set
	 */
	public void setFid(Long fid) {
		this.fid = fid;
	}

	/**
	 * @return the vFuel
	 */
	public String getvFuel() {
		return vFuel;
	}

	/**
	 * @param vFuel
	 *            the vFuel to set
	 */
	public void setvFuel(String vFuel) {
		this.vFuel = vFuel;
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
		builder.append("VehicleFuelDto [vFuel=");
		builder.append(vFuel);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append("]");
		return builder.toString();
	}

}