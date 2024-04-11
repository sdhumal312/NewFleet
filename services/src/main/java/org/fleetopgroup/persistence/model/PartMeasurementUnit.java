package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PartMeasurementUnit")
public class PartMeasurementUnit implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pmuid")
	private Integer pmuid;

	@Column(name = "pmuName", unique = true, nullable = false, length = 50)
	private String pmuName;

	@Column(name = "pmuSymbol", nullable = false, length = 25)
	private String pmuSymbol;

	@Column(name = "pmudescription", length = 150)
	private String pmudescription;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "needConversion", nullable = false)
	private boolean needConversion;
	
	@Column(name = "convertTo")
	private Integer convertTo;
	
	@Column(name = "conversionRate")
	private Double	conversionRate;

	public PartMeasurementUnit() {
		super();
	}

	public PartMeasurementUnit(String pmuName, String pmuSymbol, String pmudescription) {
		super();
		this.pmuName = pmuName;
		this.pmuSymbol = pmuSymbol;
		this.pmudescription = pmudescription;
	}

	/**
	 * @return the pmuid
	 */
	public Integer getPmuid() {
		return pmuid;
	}

	/**
	 * @param pmuid
	 *            the pmuid to set
	 */
	public void setPmuid(Integer pmuid) {
		this.pmuid = pmuid;
	}

	/**
	 * @return the pmuName
	 */
	public String getPmuName() {
		return pmuName;
	}

	/**
	 * @param pmuName
	 *            the pmuName to set
	 */
	public void setPmuName(String pmuName) {
		this.pmuName = pmuName;
	}

	/**
	 * @return the pmuSymbol
	 */
	public String getPmuSymbol() {
		return pmuSymbol;
	}

	/**
	 * @param pmuSymbol
	 *            the pmuSymbol to set
	 */
	public void setPmuSymbol(String pmuSymbol) {
		this.pmuSymbol = pmuSymbol;
	}

	/**
	 * @return the pmudescription
	 */
	public String getPmudescription() {
		return pmudescription;
	}

	/**
	 * @param pmudescription
	 *            the pmudescription to set
	 */
	public void setPmudescription(String pmudescription) {
		this.pmudescription = pmudescription;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public boolean isNeedConversion() {
		return needConversion;
	}

	public void setNeedConversion(boolean needConversion) {
		this.needConversion = needConversion;
	}

	public Integer getConvertTo() {
		return convertTo;
	}

	public void setConvertTo(Integer convertTo) {
		this.convertTo = convertTo;
	}

	public Double getConversionRate() {
		return conversionRate;
	}

	public void setConversionRate(Double conversionRate) {
		this.conversionRate = conversionRate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pmuName == null) ? 0 : pmuName.hashCode());
		result = prime * result + ((pmuSymbol == null) ? 0 : pmuSymbol.hashCode());
		result = prime * result + ((pmudescription == null) ? 0 : pmudescription.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PartMeasurementUnit other = (PartMeasurementUnit) obj;
		if (pmuName == null) {
			if (other.pmuName != null)
				return false;
		} else if (!pmuName.equals(other.pmuName))
			return false;
		if (pmuSymbol == null) {
			if (other.pmuSymbol != null)
				return false;
		} else if (!pmuSymbol.equals(other.pmuSymbol))
			return false;
		if (pmudescription == null) {
			if (other.pmudescription != null)
				return false;
		} else if (!pmudescription.equals(other.pmudescription))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PartMeasurementUnit [pmuid=" + pmuid + ", pmuName=" + pmuName + ", pmuSymbol=" + pmuSymbol
				+ ", pmudescription=" + pmudescription + ", markForDelete=" + markForDelete + ", needConversion="
				+ needConversion + ", convertTo=" + convertTo + ", conversionRate=" + conversionRate + "]";
	}

	

}