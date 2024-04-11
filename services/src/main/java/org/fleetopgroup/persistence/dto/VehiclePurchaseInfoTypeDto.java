package org.fleetopgroup.persistence.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author fleetop
 *
 */

public class VehiclePurchaseInfoTypeDto {
	
	private Long ptid;
	
	@NotNull
	@Size(min = 1)
	private String vPurchaseInfoType;

	/**
	 * @return the ptid
	 */
	public Long getPtid() {
		return ptid;
	}

	/**
	 * @param ptid the ptid to set
	 */
	public void setPtid(Long ptid) {
		this.ptid = ptid;
	}

	/**
	 * @return the vPurchaseInfoType
	 */
	public String getvPurchaseInfoType() {
		return vPurchaseInfoType;
	}

	/**
	 * @param vPurchaseInfoType the vPurchaseInfoType to set
	 */
	public void setvPurchaseInfoType(String vPurchaseInfoType) {
		this.vPurchaseInfoType = vPurchaseInfoType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehiclePurchaseInfoTypeDto [vPurchaseInfoType=");
		builder.append(vPurchaseInfoType);
		builder.append(", ptid=");
		builder.append(ptid);
		builder.append("]");
		return builder.toString();
	}
	
	
}