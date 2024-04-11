package org.fleetopgroup.persistence.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fleetop
 *
 */

public class VehicleDocTypeDto {
	
	private Long dtid;
	
	@NotNull
	@Size(min = 1)
	private String vDocType;

	/**
	 * @return the dtid
	 */
	public Long getDtid() {
		return dtid;
	}

	/**
	 * @param dtid the dtid to set
	 */
	public void setDtid(Long dtid) {
		this.dtid = dtid;
	}

	/**
	 * @return the vDocType
	 */
	public String getvDocType() {
		return vDocType;
	}

	/**
	 * @param vDocType the vDocType to set
	 */
	public void setvDocType(String vDocType) {
		this.vDocType = vDocType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleDocTypeDto [vDocType=");
		builder.append(vDocType);
		builder.append(", dtid=");
		builder.append(dtid);
		builder.append("]");
		return builder.toString();
	}
	
}