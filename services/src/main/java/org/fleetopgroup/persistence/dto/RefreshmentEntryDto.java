package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

public class RefreshmentEntryDto {

	private Long	refreshmentEntryId;
	private Long	refreshmentEntryNumber;
	private Integer	vid;
	private Long	tripsheetId;
	private Long 	partid;
	private short 	asignmentType;
	private Integer partLocationId;
	private Long 	inventoryId;
	private Integer routeId;
	private Integer driverId;
	private Date  	asignmentDate;
	private Double 	returnQuantity;
	private Double 	quantity;
	private Double 	unitprice;
	private Double 	discount;
	private Double 	tax;
	private Double 	totalAmount;
	private Integer companyId;
	private Long 	createdById;
	private Long 	lastModifiedById;
	private Date 	created;
	private Date 	lastupdated;
	private	boolean	markForDelete;
	private String	comment;
	private String	asignmentDateStr;
	private Long 	inventory_Number;
	private String	partname;
	private String	locationName; 
	private String	driverName;
	private String	routeName;
	private String	vehicle_registration;
	private Long	tripSheetNumber;
	private String	returnDateStr;
	private Double	balanceAmount;
	private Double	returnAmount;
	
	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Long getRefreshmentEntryId() {
		return refreshmentEntryId;
	}

	public void setRefreshmentEntryId(Long refreshmentEntryId) {
		this.refreshmentEntryId = refreshmentEntryId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getTripsheetId() {
		return tripsheetId;
	}

	public void setTripsheetId(Long tripsheetId) {
		this.tripsheetId = tripsheetId;
	}

	public Long getPartid() {
		return partid;
	}

	public void setPartid(Long partid) {
		this.partid = partid;
	}

	public short getAsignmentType() {
		return asignmentType;
	}

	public void setAsignmentType(short asignmentType) {
		this.asignmentType = asignmentType;
	}

	public Integer getPartLocationId() {
		return partLocationId;
	}

	public void setPartLocationId(Integer partLocationId) {
		this.partLocationId = partLocationId;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Date getAsignmentDate() {
		return asignmentDate;
	}

	public void setAsignmentDate(Date asignmentDate) {
		this.asignmentDate = asignmentDate;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = Utility.round(quantity, 2);
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = Utility.round(unitprice, 2);
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount =  Utility.round(discount, 2) ;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax =  Utility.round(tax, 2);
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount =  Utility.round(totalAmount, 2);
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Double getReturnQuantity() {
		return returnQuantity;
	}

	public void setReturnQuantity(Double returnQuantity) {
		this.returnQuantity = Utility.round(returnQuantity,2);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getAsignmentDateStr() {
		return asignmentDateStr;
	}

	public void setAsignmentDateStr(String asignmentDateStr) {
		this.asignmentDateStr = asignmentDateStr;
	}

	public Long getInventory_Number() {
		return inventory_Number;
	}

	public void setInventory_Number(Long inventory_Number) {
		this.inventory_Number = inventory_Number;
	}

	public String getPartname() {
		return partname;
	}

	public void setPartname(String partname) {
		this.partname = partname;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Long getRefreshmentEntryNumber() {
		return refreshmentEntryNumber;
	}

	public void setRefreshmentEntryNumber(Long refreshmentEntryNumber) {
		this.refreshmentEntryNumber = refreshmentEntryNumber;
	}

	public String getVehicle_registration() {
		return vehicle_registration;
	}

	public void setVehicle_registration(String vehicle_registration) {
		this.vehicle_registration = vehicle_registration;
	}

	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}

	public String getReturnDateStr() {
		return returnDateStr;
	}

	public void setReturnDateStr(String returnDateStr) {
		this.returnDateStr = returnDateStr;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2);
	}

	public Double getReturnAmount() {
		return returnAmount;
	}

	public void setReturnAmount(Double returnAmount) {
		this.returnAmount = Utility.round( returnAmount, 2);
	}

	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}
	

}
