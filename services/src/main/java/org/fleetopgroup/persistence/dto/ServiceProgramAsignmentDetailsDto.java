package org.fleetopgroup.persistence.dto;

public class ServiceProgramAsignmentDetailsDto {

	private Long 	programAsignmentId;
	private Long	serviceProgramId;
	private Long	vehicleTypeId;
	private	Long	vehicleModalId;
	private Integer	vid;
	private String	vehicleType;
	private String	vehicleModal;
	private String	vehicleNumber;
	private String	createdOnStr;
	private String	createdBy;
	private Integer	branchId;
	private String	branchName;
	
	
	
	public Long getProgramAsignmentId() {
		return programAsignmentId;
	}
	public void setProgramAsignmentId(Long programAsignmentId) {
		this.programAsignmentId = programAsignmentId;
	}
	public Long getServiceProgramId() {
		return serviceProgramId;
	}
	public void setServiceProgramId(Long serviceProgramId) {
		this.serviceProgramId = serviceProgramId;
	}
	public Long getVehicleTypeId() {
		return vehicleTypeId;
	}
	public void setVehicleTypeId(Long vehicleTypeId) {
		this.vehicleTypeId = vehicleTypeId;
	}
	public Long getVehicleModalId() {
		return vehicleModalId;
	}
	public void setVehicleModalId(Long vehicleModalId) {
		this.vehicleModalId = vehicleModalId;
	}
	public Integer getVid() {
		return vid;
	}
	public void setVid(Integer vid) {
		this.vid = vid;
	}
	public String getVehicleType() {
		return vehicleType;
	}
	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}
	public String getVehicleModal() {
		return vehicleModal;
	}
	public void setVehicleModal(String vehicleModal) {
		this.vehicleModal = vehicleModal;
	}
	public String getVehicleNumber() {
		return vehicleNumber;
	}
	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}
	public String getCreatedOnStr() {
		return createdOnStr;
	}
	public void setCreatedOnStr(String createdOnStr) {
		this.createdOnStr = createdOnStr;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getBranchId() {
		return branchId;
	}
	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
	
}
