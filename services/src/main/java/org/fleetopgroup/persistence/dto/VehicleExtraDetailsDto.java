package org.fleetopgroup.persistence.dto;


public class VehicleExtraDetailsDto {
	
	private Long	vehicleExtraDetailsId;
	
	private Integer vid;
	
	private long	busId;
	
	private long	deviceId;
	
	private Integer	companyId;
	
	private boolean markForDelete;
	
	private String	customerId;
	
	private Long	vehicleTollDetailsId;
	
	private Long	vehicleGPSCredentialId;
	
	private String	userName;
	
	private String	password;
	
	private String	walletId;

	public Long getVehicleExtraDetailsId() {
		return vehicleExtraDetailsId;
	}

	public void setVehicleExtraDetailsId(Long vehicleExtraDetailsId) {
		this.vehicleExtraDetailsId = vehicleExtraDetailsId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public long getBusId() {
		return busId;
	}

	public void setBusId(long busId) {
		this.busId = busId;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Long getVehicleTollDetailsId() {
		return vehicleTollDetailsId;
	}

	public void setVehicleTollDetailsId(Long vehicleTollDetailsId) {
		this.vehicleTollDetailsId = vehicleTollDetailsId;
	}

	public Long getVehicleGPSCredentialId() {
		return vehicleGPSCredentialId;
	}

	public void setVehicleGPSCredentialId(Long vehicleGPSCredentialId) {
		this.vehicleGPSCredentialId = vehicleGPSCredentialId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	@Override
	public String toString() {
		return "VehicleExtraDetailsDto [vehicleExtraDetailsId=" + vehicleExtraDetailsId + ", vid=" + vid + ", busId="
				+ busId + ", deviceId=" + deviceId + ", companyId=" + companyId + ", markForDelete=" + markForDelete
				+ "]";
	}
	
	
}