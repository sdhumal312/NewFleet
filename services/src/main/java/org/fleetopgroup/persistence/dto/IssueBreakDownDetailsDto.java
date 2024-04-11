package org.fleetopgroup.persistence.dto;

public class IssueBreakDownDetailsDto {

	private Long	issueBreakDownDetailsId;
	
	private Long	issueId;
	
	private String tripNumber;
	
	private String breakDownLocation;
	
	private Boolean isVehicleReplaced;
	
	private Integer	replacedWithVid;
	
	private String vehicleReplacedLocation;
	
	private Boolean isTripCancelled;
	
	private Double	cancelledKM; 
	
	private String	vehicleNumber;
	
	private String	vehicleReplacedStr;
	
	private String	tripCancelledStr;

	public Long getIssueBreakDownDetailsId() {
		return issueBreakDownDetailsId;
	}

	public void setIssueBreakDownDetailsId(Long issueBreakDownDetailsId) {
		this.issueBreakDownDetailsId = issueBreakDownDetailsId;
	}

	public Long getIssueId() {
		return issueId;
	}

	public void setIssueId(Long issueId) {
		this.issueId = issueId;
	}

	public String getTripNumber() {
		return tripNumber;
	}

	public void setTripNumber(String tripNumber) {
		this.tripNumber = tripNumber;
	}

	public String getBreakDownLocation() {
		return breakDownLocation;
	}

	public void setBreakDownLocation(String breakDownLocation) {
		this.breakDownLocation = breakDownLocation;
	}

	public Boolean getIsVehicleReplaced() {
		return isVehicleReplaced;
	}

	public void setIsVehicleReplaced(Boolean isVehicleReplaced) {
		this.isVehicleReplaced = isVehicleReplaced;
	}

	public Integer getReplacedWithVid() {
		return replacedWithVid;
	}

	public void setReplacedWithVid(Integer replacedWithVid) {
		this.replacedWithVid = replacedWithVid;
	}

	public String getVehicleReplacedLocation() {
		return vehicleReplacedLocation;
	}

	public void setVehicleReplacedLocation(String vehicleReplacedLocation) {
		this.vehicleReplacedLocation = vehicleReplacedLocation;
	}

	public Boolean getIsTripCancelled() {
		return isTripCancelled;
	}

	public void setIsTripCancelled(Boolean isTripCancelled) {
		this.isTripCancelled = isTripCancelled;
	}

	public Double getCancelledKM() {
		return cancelledKM;
	}

	public void setCancelledKM(Double cancelledKM) {
		this.cancelledKM = cancelledKM;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getVehicleReplacedStr() {
		return vehicleReplacedStr;
	}

	public void setVehicleReplacedStr(String vehicleReplacedStr) {
		this.vehicleReplacedStr = vehicleReplacedStr;
	}

	public String getTripCancelledStr() {
		return tripCancelledStr;
	}

	public void setTripCancelledStr(String tripCancelledStr) {
		this.tripCancelledStr = tripCancelledStr;
	}
}
