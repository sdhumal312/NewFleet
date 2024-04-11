package org.fleetopgroup.persistence.dto;

public class PartMeasurementUnitDto {

	private Integer pmuid;

	private String pmuName;

	private String pmuSymbol;

	private String pmudescription;
	
	private boolean markForDelete;
	
	private boolean needConversion;
	
	private Integer convertTo;
	
	private Double	conversionRate;
	
	private String	needConversionStr;
	
	private String	convertToStr;

	public Integer getPmuid() {
		return pmuid;
	}

	public void setPmuid(Integer pmuid) {
		this.pmuid = pmuid;
	}

	public String getPmuName() {
		return pmuName;
	}

	public void setPmuName(String pmuName) {
		this.pmuName = pmuName;
	}

	public String getPmuSymbol() {
		return pmuSymbol;
	}

	public void setPmuSymbol(String pmuSymbol) {
		this.pmuSymbol = pmuSymbol;
	}

	public String getPmudescription() {
		return pmudescription;
	}

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

	public String getNeedConversionStr() {
		return needConversionStr;
	}

	public void setNeedConversionStr(String conversionRateStr) {
		this.needConversionStr = conversionRateStr;
	}

	public String getConvertToStr() {
		return convertToStr;
	}

	public void setConvertToStr(String convertToStr) {
		this.convertToStr = convertToStr;
	}

	
}
