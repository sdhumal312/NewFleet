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
@Table(name ="TripsheetDueAmount")
public class TripsheetDueAmount implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripsheetDueAmountId")
	private Long	tripsheetDueAmountId;
	
	@Column(name = "tripSheetID")
	private Long tripSheetID;
	
	@Column(name = "driver_id")
	private int driver_id;
	
	@Column(name = "dueAmount")
	private double	dueAmount;
	
	@Column(name = "balanceAmount")
	private double	balanceAmount;
	
	@Column(name = "approximateDate")
	private Date	approximateDate;
	
	@Column(name = "dueDate")
	private Date	dueDate;
	
	@Column(name = "remark")
	private String	remark;
	
	@Column(name = "dueStatus")
	private short	dueStatus;
	
	@Column(name = "createdById")
	private Long	createdById;
	
	@Column(name = "creationDate")
	private Date	creationDate;
	
	@Column(name = "lastUpdatedBy")
	private Long	lastUpdatedBy;
	
	@Column(name = "lastUpdatedOn")
	private Date	lastUpdatedOn;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "vid")
	private Integer vid;
	
	@Column(name="billSelectionId")
    private Short billSelectionId;
	
	
	public TripsheetDueAmount() {
		super();
	}

	public TripsheetDueAmount(Long tripsheetDueAmountId, Long tripSheetID, int driver_id, double dueAmount,
			Date approximateDate, Date dueDate, String remark, Long createdById, Date creationDate, Long lastUpdatedBy,
			Date lastUpdatedOn, Integer companyId, boolean markForDelete) {
		super();
		this.tripsheetDueAmountId = tripsheetDueAmountId;
		this.tripSheetID = tripSheetID;
		this.driver_id = driver_id;
		this.dueAmount = dueAmount;
		this.approximateDate = approximateDate;
		this.dueDate = dueDate;
		this.remark = remark;
		this.createdById = createdById;
		this.creationDate = creationDate;
		this.lastUpdatedBy = lastUpdatedBy;
		this.lastUpdatedOn = lastUpdatedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	public Long getTripsheetDueAmountId() {
		return tripsheetDueAmountId;
	}

	public void setTripsheetDueAmountId(Long tripsheetDueAmountId) {
		this.tripsheetDueAmountId = tripsheetDueAmountId;
	}

	public Long getTripSheetID() {
		return tripSheetID;
	}

	public Short getBillSelectionId() {
		return billSelectionId;
	}

	public void setBillSelectionId(Short billSelectionId) {
		this.billSelectionId = billSelectionId;
	}

	public void setTripSheetID(Long tripSheetID) {
		this.tripSheetID = tripSheetID;
	}

	public int getDriver_id() {
		return driver_id;
	}

	public void setDriver_id(int driver_id) {
		this.driver_id = driver_id;
	}

	public double getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(double dueAmount) {
		this.dueAmount = dueAmount;
	}

	public Date getApproximateDate() {
		return approximateDate;
	}

	public void setApproximateDate(Date approximateDate) {
		this.approximateDate = approximateDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
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
	
	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public short getDueStatus() {
		return dueStatus;
	}

	public void setDueStatus(short dueStatus) {
		this.dueStatus = dueStatus;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	@Override
	public String toString() {
		return "TripsheetDueAmount [tripsheetDueAmountId=" + tripsheetDueAmountId + ", tripSheetID=" + tripSheetID
				+ ", driver_id=" + driver_id + ", dueAmount=" + dueAmount + ", balanceAmount=" + balanceAmount
				+ ", approximateDate=" + approximateDate + ", dueDate=" + dueDate + ", remark=" + remark
				+ ", dueStatus=" + dueStatus + ", createdById=" + createdById + ", creationDate=" + creationDate
				+ ", lastUpdatedBy=" + lastUpdatedBy + ", lastUpdatedOn=" + lastUpdatedOn + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + ", vid=" + vid + ", billSelectionId=" + billSelectionId + "]";
	}
	
}