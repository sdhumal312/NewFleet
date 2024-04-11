package org.fleetopgroup.persistence.dto;

/**
 * @author fleetop
 *
 *
 *
 */

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class TripSheetAdvanceDto {

	private Long tripAdvanceID;

	private Double AdvanceAmount;

	private String advancePlace;

	private String advancePaidby;
	
	private Integer driverId;

	private String advanceRefence;

	private String advanceRemarks;

	private String createdBy;

	private Date created;
	
	private String createdStr;

	private TripSheetDto tripsheet;
	
	private Integer	advancePlaceId;
	private Long advancePaidbyId;
	private Long createdById;
	
	private short paymentTypeId;
	
	private String paymentTypeName;
	
	private String driverName;

	public TripSheetAdvanceDto() {

	}

	public TripSheetAdvanceDto(Double advanceAmount, String advancePlace, String advancePaidby, String advanceRefence,
			String advanceRemarks, String createdBy, Date created, TripSheetDto tripsheet, String driverName) {
		super();
		AdvanceAmount = advanceAmount;
		this.advancePlace = advancePlace;
		this.advancePaidby = advancePaidby;
		this.advanceRefence = advanceRefence;
		this.advanceRemarks = advanceRemarks;
		this.createdBy = createdBy;
		this.created = created;
		this.tripsheet = tripsheet;
		this.driverName=driverName;
	}

	/**
	 * @return the tripAdvanceID
	 */
	public Long getTripAdvanceID() {
		return tripAdvanceID;
	}

	/**
	 * @param tripAdvanceID
	 *            the tripAdvanceID to set
	 */
	public void setTripAdvanceID(Long tripAdvanceID) {
		this.tripAdvanceID = tripAdvanceID;
	}

	/**
	 * @return the advanceAmount
	 */
	public Double getAdvanceAmount() {
		return AdvanceAmount;
	}

	/**
	 * @param advanceAmount
	 *            the advanceAmount to set
	 */
	public void setAdvanceAmount(Double advanceAmount) {
		AdvanceAmount = Utility.round(advanceAmount, 2);
	}

	/**
	 * @return the advancePlace
	 */
	public String getAdvancePlace() {
		return advancePlace;
	}

	/**
	 * @param advancePlace
	 *            the advancePlace to set
	 */
	public void setAdvancePlace(String advancePlace) {
		this.advancePlace = advancePlace;
	}

	/**
	 * @return the advancePaidby
	 */
	public String getAdvancePaidby() {
		return advancePaidby;
	}

	/**
	 * @param advancePaidby
	 *            the advancePaidby to set
	 */
	public void setAdvancePaidby(String advancePaidby) {
		this.advancePaidby = advancePaidby;
	}

	/**
	 * @return the advanceRefence
	 */
	public String getAdvanceRefence() {
		return advanceRefence;
	}

	/**
	 * @param advanceRefence
	 *            the advanceRefence to set
	 */
	public void setAdvanceRefence(String advanceRefence) {
		this.advanceRefence = advanceRefence;
	}

	/**
	 * @return the advanceRemarks
	 */
	public String getAdvanceRemarks() {
		return advanceRemarks;
	}

	/**
	 * @param advanceRemarks
	 *            the advanceRemarks to set
	 */
	public void setAdvanceRemarks(String advanceRemarks) {
		this.advanceRemarks = advanceRemarks;
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
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the tripsheet
	 */
	public TripSheetDto getTripsheet() {
		return tripsheet;
	}

	/**
	 * @param tripsheet
	 *            the tripsheet to set
	 */
	public void setTripsheet(TripSheetDto tripsheet) {
		this.tripsheet = tripsheet;
	}

	/**
	 * @return the advancePlaceId
	 */
	public Integer getAdvancePlaceId() {
		return advancePlaceId;
	}

	/**
	 * @param advancePlaceId the advancePlaceId to set
	 */
	public void setAdvancePlaceId(Integer advancePlaceId) {
		this.advancePlaceId = advancePlaceId;
	}

	/**
	 * @return the advancePaidbyId
	 */
	public Long getAdvancePaidbyId() {
		return advancePaidbyId;
	}

	/**
	 * @param advancePaidbyId the advancePaidbyId to set
	 */
	public void setAdvancePaidbyId(Long advancePaidbyId) {
		this.advancePaidbyId = advancePaidbyId;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripSheetAdvanceDto [AdvanceAmount=");
		builder.append(AdvanceAmount);
		builder.append(", advancePlace=");
		builder.append(advancePlace);
		builder.append(", advancePaidby=");
		builder.append(advancePaidby);
		builder.append(", advanceRefence=");
		builder.append(advanceRefence);
		builder.append(", advanceRemarks=");
		builder.append(advanceRemarks);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", created=");
		builder.append(created);
		builder.append(", tripsheet=");
		builder.append(tripsheet);
		builder.append(", driverName=");
		builder.append(driverName);
		builder.append("]");
		return builder.toString();
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getCreatedStr() {
		return createdStr;
	}

	public void setCreatedStr(String createdStr) {
		this.createdStr = createdStr;
	}

	public short getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public String getPaymentTypeName() {
		return paymentTypeName;
	}

	public void setPaymentTypeName(String paymentTypeName) {
		this.paymentTypeName = paymentTypeName;
	}

}