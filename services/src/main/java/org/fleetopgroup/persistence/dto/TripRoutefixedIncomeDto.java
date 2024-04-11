package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.persistence.model.TripRoute;
import org.fleetopgroup.web.util.Utility;

public class TripRoutefixedIncomeDto {

	private Integer routefixedID;

	private String incomeName;
	
	private Integer	incomeId;

	private Double incomeAmount;

	private String incomePlace;

	private Integer incomePlaceId;
	
	private String incomeRefence;

	private String incomeFixed;
	
	private short incomeFixedId;

	private TripRoute triproute;
	
	private Integer companyId;
	
	private boolean markForDelete;


	/**
	 * @return the routefixedID
	 */
	public Integer getRoutefixedID() {
		return routefixedID;
	}

	/**
	 * @param routefixedID
	 *            the routefixedID to set
	 */
	public void setRoutefixedID(Integer routefixedID) {
		this.routefixedID = routefixedID;
	}

	/**
	 * @return the incomeName
	 */
	public String getIncomeName() {
		return incomeName;
	}

	/**
	 * @param incomeName
	 *            the incomeName to set
	 */
	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}

	/**
	 * @return the incomeAmount
	 */
	public Double getIncomeAmount() {
		return incomeAmount;
	}

	/**
	 * @param incomeAmount
	 *            the incomeAmount to set
	 */
	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = Utility.round(incomeAmount,1) ;
	}

	/**
	 * @return the incomeRefence
	 */
	public String getIncomeRefence() {
		return incomeRefence;
	}

	/**
	 * @param incomeRefence
	 *            the incomeRefence to set
	 */
	public void setIncomeRefence(String incomeRefence) {
		this.incomeRefence = incomeRefence;
	}

	/**
	 * @return the incomeFixed
	 */
	public String getIncomeFixed() {
		return incomeFixed;
	}

	/**
	 * @param incomeFixed
	 *            the incomeFixed to set
	 */
	public void setIncomeFixed(String incomeFixed) {
		this.incomeFixed = incomeFixed;
	}

	public short getIncomeFixedId() {
		return incomeFixedId;
	}

	public void setIncomeFixedId(short incomeFixedId) {
		this.incomeFixedId = incomeFixedId;
	}

	/**
	 * @return the triproute
	 */
	public TripRoute getTriproute() {
		return triproute;
	}


	/**
	 * @param triproute
	 *            the triproute to set
	 */
	public void setTriproute(TripRoute triproute) {
		this.triproute = triproute;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete
	 *            the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the incomePlace
	 */
	public String getIncomePlace() {
		return incomePlace;
	}

	/**
	 * @param incomePlace
	 *            the incomePlace to set
	 */
	public void setIncomePlace(String incomePlace) {
		this.incomePlace = incomePlace;
	}

	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getIncomeId() {
		return incomeId;
	}

	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
	}

	public Integer getIncomePlaceId() {
		return incomePlaceId;
	}

	public void setIncomePlaceId(Integer incomePlaceId) {
		this.incomePlaceId = incomePlaceId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripRoutefixedIncome [routefixedID=");
		builder.append(routefixedID);
		builder.append(", incomeName=");
		builder.append(incomeName);
		builder.append(", incomeAmount=");
		builder.append(incomeAmount);
		builder.append(", incomeRefence=");
		builder.append(incomeRefence);
		builder.append(", incomeFixed=");
		builder.append(incomeFixed);
		builder.append(", triproute=");
		builder.append(triproute);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}


}
