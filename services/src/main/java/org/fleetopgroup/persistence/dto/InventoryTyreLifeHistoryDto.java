/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class InventoryTyreLifeHistoryDto {

	/** The value for the ITYRE_ID Layout Id field */
	private Long TYRE_LIFE_ID;

	/** The value for the TYRE_ID field */
	private Long TYRE_ID;

	/** The value for the TYRE_NUMBER Number field */
	private String TYRE_NUMBER;

	/** The value for the LIFE_PERIOD Number field */
	private String LIFE_PERIOD;

	/** The value for the TYRE_LIFE_COST field */
	private Double TYRE_LIFE_COST;

	/** The value for the TYRE_LIFE_USAGE ID field */
	private Integer TYRE_LIFE_USAGE;

	/** The value for the TYRE_KM_COST ID field */
	private Double TYRE_KM_COST;

	/** The value for the TYRE_RECEIVED_DATE field */
	private String TYRE_RECEIVED_DATE;

	public InventoryTyreLifeHistoryDto() {
		super();
	}

	public InventoryTyreLifeHistoryDto(Long tYRE_LIFE_ID, Long tYRE_ID, String tYRE_NUMBER, String lIFE_PERIOD,
			Double tYRE_LIFE_COST, Integer tYRE_LIFE_USAGE, Double tYRE_KM_COST, String tYRE_RECEIVED_DATE) {
		super();
		TYRE_LIFE_ID = tYRE_LIFE_ID;
		TYRE_ID = tYRE_ID;
		TYRE_NUMBER = tYRE_NUMBER;
		LIFE_PERIOD = lIFE_PERIOD;
		TYRE_LIFE_COST = tYRE_LIFE_COST;
		TYRE_LIFE_USAGE = tYRE_LIFE_USAGE;
		TYRE_KM_COST = tYRE_KM_COST;
		TYRE_RECEIVED_DATE = tYRE_RECEIVED_DATE;
	}

	/**
	 * @return the tYRE_LIFE_ID
	 */
	public Long getTYRE_LIFE_ID() {
		return TYRE_LIFE_ID;
	}

	/**
	 * @param tYRE_LIFE_ID
	 *            the tYRE_LIFE_ID to set
	 */
	public void setTYRE_LIFE_ID(Long tYRE_LIFE_ID) {
		TYRE_LIFE_ID = tYRE_LIFE_ID;
	}

	/**
	 * @return the tYRE_ID
	 */
	public Long getTYRE_ID() {
		return TYRE_ID;
	}

	/**
	 * @param tYRE_ID
	 *            the tYRE_ID to set
	 */
	public void setTYRE_ID(Long tYRE_ID) {
		TYRE_ID = tYRE_ID;
	}

	/**
	 * @return the tYRE_NUMBER
	 */
	public String getTYRE_NUMBER() {
		return TYRE_NUMBER;
	}

	/**
	 * @param tYRE_NUMBER
	 *            the tYRE_NUMBER to set
	 */
	public void setTYRE_NUMBER(String tYRE_NUMBER) {
		TYRE_NUMBER = tYRE_NUMBER;
	}

	/**
	 * @return the lIFE_PERIOD
	 */
	public String getLIFE_PERIOD() {
		return LIFE_PERIOD;
	}

	/**
	 * @param lIFE_PERIOD
	 *            the lIFE_PERIOD to set
	 */
	public void setLIFE_PERIOD(String lIFE_PERIOD) {
		LIFE_PERIOD = lIFE_PERIOD;
	}

	/**
	 * @return the tYRE_LIFE_COST
	 */
	public Double getTYRE_LIFE_COST() {
		return TYRE_LIFE_COST;
	}

	/**
	 * @param tYRE_LIFE_COST
	 *            the tYRE_LIFE_COST to set
	 */
	public void setTYRE_LIFE_COST(Double tYRE_LIFE_COST) {
		TYRE_LIFE_COST = Utility.round(tYRE_LIFE_COST,2);
	}

	/**
	 * @return the tYRE_LIFE_USAGE
	 */
	public Integer getTYRE_LIFE_USAGE() {
		return TYRE_LIFE_USAGE;
	}

	/**
	 * @param tYRE_LIFE_USAGE
	 *            the tYRE_LIFE_USAGE to set
	 */
	public void setTYRE_LIFE_USAGE(Integer tYRE_LIFE_USAGE) {
		TYRE_LIFE_USAGE = tYRE_LIFE_USAGE;
	}

	/**
	 * @return the tYRE_KM_COST
	 */
	public Double getTYRE_KM_COST() {
		return TYRE_KM_COST;
	}

	/**
	 * @param tYRE_KM_COST
	 *            the tYRE_KM_COST to set
	 */
	public void setTYRE_KM_COST(Double tYRE_KM_COST) {
		TYRE_KM_COST = Utility.round(tYRE_KM_COST, 2);
	}

	/**
	 * @return the tYRE_RECEIVED_DATE
	 */
	public String getTYRE_RECEIVED_DATE() {
		return TYRE_RECEIVED_DATE;
	}

	/**
	 * @param tYRE_RECEIVED_DATE
	 *            the tYRE_RECEIVED_DATE to set
	 */
	public void setTYRE_RECEIVED_DATE(String tYRE_RECEIVED_DATE) {
		TYRE_RECEIVED_DATE = tYRE_RECEIVED_DATE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((TYRE_ID == null) ? 0 : TYRE_ID.hashCode());
		result = prime * result + ((TYRE_LIFE_ID == null) ? 0 : TYRE_LIFE_ID.hashCode());
		result = prime * result + ((TYRE_LIFE_USAGE == null) ? 0 : TYRE_LIFE_USAGE.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InventoryTyreLifeHistoryDto other = (InventoryTyreLifeHistoryDto) obj;
		if (TYRE_ID == null) {
			if (other.TYRE_ID != null)
				return false;
		} else if (!TYRE_ID.equals(other.TYRE_ID))
			return false;
		if (TYRE_LIFE_ID == null) {
			if (other.TYRE_LIFE_ID != null)
				return false;
		} else if (!TYRE_LIFE_ID.equals(other.TYRE_LIFE_ID))
			return false;
		if (TYRE_LIFE_USAGE == null) {
			if (other.TYRE_LIFE_USAGE != null)
				return false;
		} else if (!TYRE_LIFE_USAGE.equals(other.TYRE_LIFE_USAGE))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryTyreLifeHistory [TYRE_LIFE_ID=");
		builder.append(TYRE_LIFE_ID);
		builder.append(", TYRE_ID=");
		builder.append(TYRE_ID);
		builder.append(", TYRE_NUMBER=");
		builder.append(TYRE_NUMBER);
		builder.append(", LIFE_PERIOD=");
		builder.append(LIFE_PERIOD);
		builder.append(", TYRE_LIFE_COST=");
		builder.append(TYRE_LIFE_COST);
		builder.append(", TYRE_LIFE_USAGE=");
		builder.append(TYRE_LIFE_USAGE);
		builder.append(", TYRE_KM_COST=");
		builder.append(TYRE_KM_COST);
		builder.append(", TYRE_RECEIVED_DATE=");
		builder.append(TYRE_RECEIVED_DATE);
		builder.append("]");
		return builder.toString();
	}

}
