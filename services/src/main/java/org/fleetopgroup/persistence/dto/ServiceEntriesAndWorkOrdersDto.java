/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class ServiceEntriesAndWorkOrdersDto {

	private Long transactionId;

	private String transactionNumber;

	private Integer vid;
	
	private String vehicle_registration;
	
	private Integer vehicleOdometer;
	
	private String InvoiceNumber;
	
	private String InvoiceDateStr;
	
	private Long taskId;
	
	private String jobType;
	
	private String jobSubType;
	
	private Long taskToPartId;
	
	private Long partId;
	
	private String partName;
	
	private String partNumber;
	
	private Double partQuantity;
	
	private Double partEachCost;
	
	private Double partTotalCost;
	
	private Double LaborTotalCost;

	private String lastUpdatedBy;

	private String startDateStr;

	private String endDateStr;
	
	private Double totalRepairCost;
	
	private short statusIs;
	
	private String status;
	

	boolean markForDelete;

	public ServiceEntriesAndWorkOrdersDto() {
		super();
	}

	public ServiceEntriesAndWorkOrdersDto(Long transactionId, String transactionNumber, Integer vid,
			String vehicle_registration, Integer vehicleOdometer, String invoiceNumber, String invoiceDateStr,
			Long taskId, String jobType, String jobSubType, Long taskToPartId, Long partId, String partName,
			String partNumber, Double partQuantity, Double partEachCost, Double partTotalCost, Double laborTotalCost,
			String lastUpdatedBy, String startDateStr, String endDateStr, boolean markForDelete) {
		super();
		this.transactionId = transactionId;
		this.transactionNumber = transactionNumber;
		this.vid = vid;
		this.vehicle_registration = vehicle_registration;
		this.vehicleOdometer = vehicleOdometer;
		InvoiceNumber = invoiceNumber;
		InvoiceDateStr = invoiceDateStr;
		this.taskId = taskId;
		this.jobType = jobType;
		this.jobSubType = jobSubType;
		this.taskToPartId = taskToPartId;
		this.partId = partId;
		this.partName = partName;
		this.partNumber = partNumber;
		this.partQuantity = partQuantity;
		this.partEachCost = partEachCost;
		this.partTotalCost = partTotalCost;
		LaborTotalCost = laborTotalCost;
		this.lastUpdatedBy = lastUpdatedBy;
		this.startDateStr = startDateStr;
		this.endDateStr = endDateStr;
		this.markForDelete = markForDelete;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public Integer getVehicleOdometer() {
		return vehicleOdometer;
	}

	public void setVehicleOdometer(Integer vehicleOdometer) {
		this.vehicleOdometer = vehicleOdometer;
	}

	public String getInvoiceNumber() {
		return InvoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		InvoiceNumber = invoiceNumber;
	}

	public String getInvoiceDateStr() {
		return InvoiceDateStr;
	}

	public void setInvoiceDateStr(String invoiceDateStr) {
		InvoiceDateStr = invoiceDateStr;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getJobType() {
		return jobType;
	}

	public void setJobType(String jobType) {
		this.jobType = jobType;
	}

	public String getJobSubType() {
		return jobSubType;
	}

	public void setJobSubType(String jobSubType) {
		this.jobSubType = jobSubType;
	}

	public Long getTaskToPartId() {
		return taskToPartId;
	}

	public void setTaskToPartId(Long taskToPartId) {
		this.taskToPartId = taskToPartId;
	}

	public Long getPartId() {
		return partId;
	}

	public void setPartId(Long partId) {
		this.partId = partId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartNumber() {
		return partNumber;
	}

	public void setPartNumber(String partNumber) {
		this.partNumber = partNumber;
	}

	public Double getPartQuantity() {
		return partQuantity;
	}

	public void setPartQuantity(Double partQuantity) {
		this.partQuantity = Utility.round(partQuantity, 2);
	}

	public Double getPartEachCost() {
		return partEachCost;
	}

	public void setPartEachCost(Double partEachCost) {
		this.partEachCost = Utility.round(partEachCost, 2);
	}

	public Double getPartTotalCost() {
		return partTotalCost;
	}

	public void setPartTotalCost(Double partTotalCost) {
		this.partTotalCost = Utility.round(partTotalCost,2) ;
	}

	public Double getLaborTotalCost() {
		return LaborTotalCost;
	}

	public void setLaborTotalCost(Double laborTotalCost) {
		LaborTotalCost =Utility.round(laborTotalCost, 2);
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public String getStartDateStr() {
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public String getEndDateStr() {
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public Double getTotalRepairCost() {
		return totalRepairCost;
	}

	public void setTotalRepairCost(Double totalRepairCost) {
		this.totalRepairCost =  Utility.round(totalRepairCost, 2);
	}

	public short getStatusIs() {
		return statusIs;
	}

	public void setStatusIs(short statusIs) {
		this.statusIs = statusIs;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	@Override
	public String toString() {
		return "ServiceEntriesAndWorkOrdersDto [transactionId=" + transactionId + ", transactionNumber="
				+ transactionNumber + ", vid=" + vid + ", vehicle_registration=" + vehicle_registration
				+ ", vehicleOdometer=" + vehicleOdometer + ", InvoiceNumber=" + InvoiceNumber + ", InvoiceDateStr="
				+ InvoiceDateStr + ", taskId=" + taskId + ", jobType=" + jobType + ", jobSubType=" + jobSubType
				+ ", taskToPartId=" + taskToPartId + ", partId=" + partId + ", partName=" + partName + ", partNumber="
				+ partNumber + ", partQuantity=" + partQuantity + ", partEachCost=" + partEachCost + ", partTotalCost="
				+ partTotalCost + ", LaborTotalCost=" + LaborTotalCost + ", lastUpdatedBy=" + lastUpdatedBy
				+ ", startDateStr=" + startDateStr + ", endDateStr=" + endDateStr + ", markForDelete=" + markForDelete
				+ "]";
	}


	
	
}
