package org.fleetopgroup.persistence.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fleetop
 *
 *
 *
 */

public class DriverDocTypeDto{

	private Long dri_id;
	
	@NotNull
	@Size(min = 1)
	private String dri_DocType;

	/**
	 * @return the dri_id
	 */
	public Long getDri_id() {
		return dri_id;
	}

	/**
	 * @param dri_id the dri_id to set
	 */
	public void setDri_id(Long dri_id) {
		this.dri_id = dri_id;
	}

	/**
	 * @return the dri_DocType
	 */
	public String getDri_DocType() {
		return dri_DocType;
	}

	/**
	 * @param dri_DocType the dri_DocType to set
	 */
	public void setDri_DocType(String dri_DocType) {
		this.dri_DocType = dri_DocType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DriverDocTypeDto [dri_DocType=");
		builder.append(dri_DocType);
		builder.append(", dri_id=");
		builder.append(dri_id);
		builder.append("]");
		return builder.toString();
	}
	
	
}