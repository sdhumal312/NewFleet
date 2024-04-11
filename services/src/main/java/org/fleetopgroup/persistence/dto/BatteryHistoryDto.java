package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class BatteryHistoryDto {


	private Long batteryHistoryId;
	
	private Long batteryId;
	
	private String batterySerialNumber;
	
	private Integer	vid;
	
	private short layoutPosition;
	
	private short batteryStatusId;
	
	private Integer openOdometer;
	
	private Integer batteryUseage;
	
	private Double batteryCost;
	
	private Timestamp batteryAsignDate;
	
	private String batteryComment;
	
	private Integer companyId;
	
	private boolean markForDelete;
	
	private String vehicle_registration;
	
	private String batteryAsignDateStr;
	
	/*private String batteryDismountDateStr;*/
	
	private String batteryStatus;
	
	private Long  usesNoOfDay;
	
	private Long  ScrapReason;
	
	private String batteryManufacturerName;
	
	private String preBatterySerialNumber;
	
	private Timestamp preBatteryAsignDate;
	
	private String preBatteryAsignDateStr;

	public Long getScrapReason() {
		return ScrapReason;
	}

	public void setScrapReason(Long scrapReason) {
		ScrapReason = scrapReason;
	}

	public Long getBatteryHistoryId() {
		return batteryHistoryId;
	}

	public Long getBatteryId() {
		return batteryId;
	}

	public String getBatterySerialNumber() {
		return batterySerialNumber;
	}

	public Integer getVid() {
		return vid;
	}

	public short getLayoutPosition() {
		return layoutPosition;
	}

	public short getBatteryStatusId() {
		return batteryStatusId;
	}

	public Integer getOpenOdometer() {
		return openOdometer;
	}

	public Integer getBatteryUseage() {
		return batteryUseage;
	}

	public Double getBatteryCost() {
		return batteryCost;
	}

	public Timestamp getBatteryAsignDate() {
		return batteryAsignDate;
	}

	public String getBatteryComment() {
		return batteryComment;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setBatteryHistoryId(Long batteryHistoryId) {
		this.batteryHistoryId = batteryHistoryId;
	}

	public void setBatteryId(Long batteryId) {
		this.batteryId = batteryId;
	}

	public void setBatterySerialNumber(String batterySerialNumber) {
		this.batterySerialNumber = batterySerialNumber;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public void setLayoutPosition(short layoutPosition) {
		this.layoutPosition = layoutPosition;
	}

	public void setBatteryStatusId(short batteryStatusId) {
		this.batteryStatusId = batteryStatusId;
	}

	public void setOpenOdometer(Integer openOdometer) {
		this.openOdometer = openOdometer;
	}

	public void setBatteryUseage(Integer batteryUseage) {
		this.batteryUseage = batteryUseage;
	}

	public void setBatteryCost(Double batteryCost) {
		this.batteryCost = Utility.round(batteryCost, 2);
	}

	public void setBatteryAsignDate(Timestamp batteryAsignDate) {
		this.batteryAsignDate = batteryAsignDate;
	}

	public void setBatteryComment(String batteryComment) {
		this.batteryComment = batteryComment;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/*public Timestamp getBatteryDismountDate() {
		return batteryDismountDate;
	}

	public void setBatteryDismountDate(Timestamp batteryDismountDate) {
		this.batteryDismountDate = batteryDismountDate;
	}*/

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public String getBatteryAsignDateStr() {
		return batteryAsignDateStr;
	}

	public void setBatteryAsignDateStr(String batteryAsignDateStr) {
		this.batteryAsignDateStr = batteryAsignDateStr;
	}

/*	public String getBatteryDismountDateStr() {
		return batteryDismountDateStr;
	}

	public void setBatteryDismountDateStr(String batteryDismountDateStr) {
		this.batteryDismountDateStr = batteryDismountDateStr;
	}*/

	public String getBatteryStatus() {
		return batteryStatus;
	}

	public void setBatteryStatus(String batteryStatus) {
		this.batteryStatus = batteryStatus;
	}

	public Long getUsesNoOfDay() {
		return usesNoOfDay;
	}

	public void setUsesNoOfDay(Long usesNoOfDay) {
		this.usesNoOfDay = usesNoOfDay;
	}
	
	public String getBatteryManufacturerName() {
		return batteryManufacturerName;
	}

	public void setBatteryManufacturerName(String batteryManufacturerName) {
		this.batteryManufacturerName = batteryManufacturerName;
	}

	public String getPreBatterySerialNumber() {
		return preBatterySerialNumber;
	}

	public void setPreBatterySerialNumber(String preBatterySerialNumber) {
		this.preBatterySerialNumber = preBatterySerialNumber;
	}

	public Timestamp getPreBatteryAsignDate() {
		return preBatteryAsignDate;
	}

	public void setPreBatteryAsignDate(Timestamp preBatteryAsignDate) {
		this.preBatteryAsignDate = preBatteryAsignDate;
	}

	public String getPreBatteryAsignDateStr() {
		return preBatteryAsignDateStr;
	}

	public void setPreBatteryAsignDateStr(String preBatteryAsignDateStr) {
		this.preBatteryAsignDateStr = preBatteryAsignDateStr;
	}

	@Override
	public String toString() {
		return "BatteryHistoryDto [batteryHistoryId=" + batteryHistoryId + ", batteryId=" + batteryId
				+ ", batterySerialNumber=" + batterySerialNumber + ", vid=" + vid + ", layoutPosition=" + layoutPosition
				+ ", batteryStatusId=" + batteryStatusId + ", openOdometer=" + openOdometer + ", batteryUseage="
				+ batteryUseage + ", batteryCost=" + batteryCost + ", batteryAsignDate=" + batteryAsignDate
				+ ", batteryComment=" + batteryComment + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ ", vehicle_registration=" + vehicle_registration + ", batteryAsignDateStr=" + batteryAsignDateStr
				+ ", batteryStatus=" + batteryStatus + ", usesNoOfDay=" + usesNoOfDay + ", ScrapReason=" + ScrapReason
				+ ", batteryManufacturerName=" + batteryManufacturerName + "]";
	}


}
