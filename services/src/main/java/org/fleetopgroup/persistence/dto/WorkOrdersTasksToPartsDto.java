package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class WorkOrdersTasksToPartsDto {

	private Long workordertaskto_partid;

	private Long partid;

	private String partname;

	private String partnumber;

	private String parttype;

	private String location;

	private Integer locationId;

	private Double quantity;
	
	private Double stockQuantity;

	private Double parteachcost;

	private Double totalcost;

	private String oldpart;

	private Long inventory_id;

	private Long workorders_id;
	
	private Long workOrderNumber;

	private Long workordertaskid;

	private Integer vehicle_vid;

	private Date last_occurred_date;

	private Long last_occurred_woId;

	private Integer companyId;
	
	private String companyName;

	private boolean markForDelete;
	
	private String fromDate;
	
	private String toDate;
	
	private long	vehicleGroupId;
	
	private String  vehicle_registration;
	
	private String partlocation_name;
	
	private long partCount;
	
	private double totalValuePartConsumed;
	
	private String vehicleGroup;
	
	private String	workOrderNumberStr;
	
	private String	complitionDate;

	private short	serviceTypeId;
	
	private Date 	completed_date;
	
	private long createdById;
	
	private String firstName;

	private String lastName;
	
	private String createdDate;
	
	private Integer vehicle_Odometer;
	
	private String last_occurred_dateStr;
	
	private Integer last_occurred_odometer;
	
	private String partNameOnHover;
	
	private String driverName;
	
	private boolean woPart_document;
	
	private long woPart_documentId;
	
	private boolean	isWarrantyApplicable;
	
	private boolean isRepairable;

	private short	assignedNoPart;
	
	private boolean assetIdRequired;

	public boolean isWoPart_document() {
		return woPart_document;
	}

	public void setWoPart_document(boolean woPart_document) {
		this.woPart_document = woPart_document;
	}

	public long getWoPart_documentId() {
		return woPart_documentId;
	}

	public void setWoPart_documentId(long woPart_documentId) {
		this.woPart_documentId = woPart_documentId;
	}

	public String getPartlocation_name() {
		return partlocation_name;
	}

	public void setPartlocation_name(String partlocation_name) {
		this.partlocation_name = partlocation_name;
	}

	/**
	 * @return the workordertaskto_partid
	 */
	public Long getWorkordertaskto_partid() {
		return workordertaskto_partid;
	}

	/**
	 * @param workordertaskto_partid
	 *            the workordertaskto_partid to set
	 */
	public void setWorkordertaskto_partid(Long workordertaskto_partid) {
		this.workordertaskto_partid = workordertaskto_partid;
	}

	/**
	 * @return the partid
	 */
	public Long getPartid() {
		return partid;
	}

	/**
	 * @param partid
	 *            the partid to set
	 */
	public void setPartid(Long partid) {
		this.partid = partid;
	}

	/**
	 * @return the partname
	 */
	public String getPartname() {
		return partname;
	}

	/**
	 * @param partname
	 *            the partname to set
	 */
	public void setPartname(String partname) {
		this.partname = partname;
	}

	/**
	 * @return the partnumber
	 */
	public String getPartnumber() {
		return partnumber;
	}

	/**
	 * @param partnumber
	 *            the partnumber to set
	 */
	public void setPartnumber(String partnumber) {
		this.partnumber = partnumber;
	}

	public String getParttype() {
		return parttype;
	}

	public void setParttype(String parttype) {
		this.parttype = parttype;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}


	/**
	 * @return the locationId
	 */
	public Integer getLocationId() {
		return locationId;
	}

	/**
	 * @param locationId the locationId to set
	 */
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the quantity
	 */
	public Double getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
	 */
	public void setQuantity(Double quantity) {
		this.quantity = Utility.round(quantity, 2);
	}

	public Long getWorkOrderNumber() {
		return workOrderNumber;
	}

	public void setWorkOrderNumber(Long workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}

	/**
	 * @return the parteachcost
	 */
	public Double getParteachcost() {
		return parteachcost;
	}

	/**
	 * @param parteachcost
	 *            the parteachcost to set
	 */
	public void setParteachcost(Double parteachcost) {
		this.parteachcost = Utility.round(parteachcost, 2);
	}

	/**
	 * @return the totalcost
	 */
	public Double getTotalcost() {
		return totalcost;
	}

	/**
	 * @param totalcost
	 *            the totalcost to set
	 */
	public void setTotalcost(Double totalcost) {
		this.totalcost = Utility.round(totalcost, 2);
	}

	/**
	 * @return the workorders_id
	 */
	public Long getWorkorders_id() {
		return workorders_id;
	}

	/**
	 * @param workorders_id
	 *            the workorders_id to set
	 */
	public void setWorkorders_id(Long workorders_id) {
		this.workorders_id = workorders_id;
	}

	/**
	 * @return the workordertaskid
	 */
	public Long getWorkordertaskid() {
		return workordertaskid;
	}

	/**
	 * @param workordertaskid
	 *            the workordertaskid to set
	 */
	public void setWorkordertaskid(Long workordertaskid) {
		this.workordertaskid = workordertaskid;
	}

	public String getVehicleGroup() {
		return vehicleGroup;
	}

	public void setVehicleGroup(String vehicleGroup) {
		this.vehicleGroup = vehicleGroup;
	}

	/**
	 * @return the oldpart
	 */
	public String getOldpart() {
		return oldpart;
	}

	/**
	 * @param oldpart
	 *            the oldpart to set
	 */
	public void setOldpart(String oldpart) {
		this.oldpart = oldpart;
	}

	/**
	 * @return the inventory_id
	 */
	public Long getInventory_id() {
		return inventory_id;
	}

	/**
	 * @param inventory_id
	 *            the inventory_id to set
	 */
	public void setInventory_id(Long inventory_id) {
		this.inventory_id = inventory_id;
	}

	/**
	 * @return the vehicle_vid
	 */
	public Integer getVehicle_vid() {
		return vehicle_vid;
	}

	/**
	 * @param vehicle_vid
	 *            the vehicle_vid to set
	 */
	public void setVehicle_vid(Integer vehicle_vid) {
		this.vehicle_vid = vehicle_vid;
	}

	/**
	 * @return the last_occurred_date
	 */
	public Date getLast_occurred_date() {
		return last_occurred_date;
	}

	/**
	 * @param last_occurred_date
	 *            the last_occurred_date to set
	 */
	public void setLast_occurred_date(Date last_occurred_date) {
		this.last_occurred_date = last_occurred_date;
	}

	/**
	 * @return the last_occurred_woId
	 */
	public Long getLast_occurred_woId() {
		return last_occurred_woId;
	}

	/**
	 * @param last_occurred_woId
	 *            the last_occurred_woId to set
	 */
	public void setLast_occurred_woId(Long last_occurred_woId) {
		this.last_occurred_woId = last_occurred_woId;
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



	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public long getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}
	
	public long getPartCount() {
		return partCount;
	}

	public void setPartCount(long partCount) {
		this.partCount = partCount;
	}

	public double getTotalValuePartConsumed() {
		return totalValuePartConsumed;
	}

	public void setTotalValuePartConsumed(double totalValuePartConsumed) {
		this.totalValuePartConsumed = Utility.round( totalValuePartConsumed, 2);
	}

	public String getWorkOrderNumberStr() {
		return workOrderNumberStr;
	}

	public void setWorkOrderNumberStr(String workOrderNumberStr) {
		this.workOrderNumberStr = workOrderNumberStr;
	}

	public String getComplitionDate() {
		return complitionDate;
	}

	public void setComplitionDate(String complitionDate) {
		this.complitionDate = complitionDate;
	}

	public short getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(short serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	
	public Date getCompleted_date() {
		return completed_date;
	}

	public void setCompleted_date(Date completed_date) {
		this.completed_date = completed_date;
	}	

	
	public long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(long createdById) {
		this.createdById = createdById;
	}

	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getVehicle_Odometer() {
		return vehicle_Odometer;
	}

	public void setVehicle_Odometer(Integer vehicle_Odometer) {
		this.vehicle_Odometer = vehicle_Odometer;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLast_occurred_dateStr() {
		return last_occurred_dateStr;
	}

	public void setLast_occurred_dateStr(String last_occurred_dateStr) {
		this.last_occurred_dateStr = last_occurred_dateStr;
	}

	public Integer getLast_occurred_odometer() {
		return last_occurred_odometer;
	}

	public void setLast_occurred_odometer(Integer last_occurred_odometer) {
		this.last_occurred_odometer = last_occurred_odometer;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Double getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Double stockQuantity) {
		this.stockQuantity = Utility.round(stockQuantity, 2);
	}

	public String getPartNameOnHover() {
		return partNameOnHover;
	}

	public void setPartNameOnHover(String partNameOnHover) {
		this.partNameOnHover = partNameOnHover;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public boolean isWarrantyApplicable() {
		return isWarrantyApplicable;
	}

	public void setWarrantyApplicable(boolean isWarrantyApplicable) {
		this.isWarrantyApplicable = isWarrantyApplicable;
	}

	public boolean isRepairable() {
		return isRepairable;
	}

	public void setRepairable(boolean isRepairable) {
		this.isRepairable = isRepairable;
	}

	public short getAssignedNoPart() {
		return assignedNoPart;
	}

	public void setAssignedNoPart(short assignedNoPart) {
		this.assignedNoPart = assignedNoPart;
	}

	

	public boolean isAssetIdRequired() {
		return assetIdRequired;
	}

	public void setAssetIdRequired(boolean assetIdRequired) {
		this.assetIdRequired = assetIdRequired;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("WorkOrdersTasksToPartsDto [workordertaskto_partid=");
		builder.append(workordertaskto_partid);
		builder.append(", partid=");
		builder.append(partid);
		builder.append(", partname=");
		builder.append(partname);
		builder.append(", partnumber=");
		builder.append(partnumber);
		builder.append(", parttype=");
		builder.append(parttype);
		builder.append(", location=");
		builder.append(location);
		builder.append(", locationId=");
		builder.append(locationId);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append(", parteachcost=");
		builder.append(parteachcost);
		builder.append(", totalcost=");
		builder.append(totalcost);
		builder.append(", oldpart=");
		builder.append(oldpart);
		builder.append(", inventory_id=");
		builder.append(inventory_id);
		builder.append(", workorders_id=");
		builder.append(workorders_id);
		builder.append(", workOrderNumber=");
		builder.append(workOrderNumber);
		builder.append(", workordertaskid=");
		builder.append(workordertaskid);
		builder.append(", vehicle_vid=");
		builder.append(vehicle_vid);
		builder.append(", last_occurred_date=");
		builder.append(last_occurred_date);
		builder.append(", last_occurred_woId=");
		builder.append(last_occurred_woId);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", fromDate=");
		builder.append(fromDate);
		builder.append(", toDate=");
		builder.append(toDate);
		builder.append(", vehicleGroupId=");
		builder.append(vehicleGroupId);
		builder.append(", vehicle_registration=");
		builder.append(vehicle_registration);
		builder.append(", partlocation_name=");
		builder.append(partlocation_name);
		builder.append(", partCount=");
		builder.append(partCount);
		builder.append(", totalValuePartConsumed=");
		builder.append(totalValuePartConsumed);
		builder.append(", vehicleGroup=");
		builder.append(vehicleGroup);
		builder.append(", workOrderNumberStr=");
		builder.append(workOrderNumberStr);
		builder.append(", complitionDate=");
		builder.append(complitionDate);
		builder.append(", serviceTypeId=");
		builder.append(serviceTypeId);
		builder.append(", completed_date=");
		builder.append(completed_date);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append("]");
		return builder.toString();
	}

}
