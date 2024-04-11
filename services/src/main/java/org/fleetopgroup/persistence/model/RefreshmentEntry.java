package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "RefreshmentEntry")
public class RefreshmentEntry implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "refreshmentEntryId")
	private Long	refreshmentEntryId;
	
	@Column(name = "refreshmentEntryNumber")
	private Long	refreshmentEntryNumber;
	
	@Column(name = "vid")
	private Integer	vid;
	
	@Column(name = "tripsheetId")
	private Long	tripsheetId;
	
	@Column(name = "partid")
	private Long partid;
	
	@Column(name = "asignmentType")
	private short asignmentType;
	
	@Column(name = "partLocationId")
	private Integer partLocationId;
	
	@Column(name = "inventoryId")
	private Long inventoryId;
	
	@Column(name = "routeId")
	private Integer routeId;
	
	@Column(name = "driverId")
	private Integer driverId;
	
	@Column(name = "asignmentDate")
	private Date  asignmentDate;
	
	@Column(name = "returnDate")
	private Date  returnDate;
	
	@Column(name = "returnQuantity")
	private Double returnQuantity;
	
	@Column(name = "quantity", length = 10)
	private Double quantity;

	@Column(name = "unitprice", length = 10)
	private Double unitprice;

	@Column(name = "discount", length = 10)
	private Double discount;

	@Column(name = "tax", length = 10)
	private Double tax;

	@Column(name = "totalAmount", length = 10)
	private Double totalAmount;
	
	@Column(name = "comment")
	private String	comment;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "createdById", updatable = false)
	private Long createdById;
	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "created", updatable = false)
	private Date created;

	@Column(name = "lastupdated")
	private Date lastupdated;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

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
		this.quantity = quantity;
	}

	public Double getUnitprice() {
		return unitprice;
	}

	public void setUnitprice(Double unitprice) {
		this.unitprice = unitprice;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Double getTax() {
		return tax;
	}

	public void setTax(Double tax) {
		this.tax = tax;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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
		this.returnQuantity = returnQuantity;
	}

	public Long getRefreshmentEntryNumber() {
		return refreshmentEntryNumber;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public void setRefreshmentEntryNumber(Long refreshmentEntryNumber) {
		this.refreshmentEntryNumber = refreshmentEntryNumber;
	}
	
}
