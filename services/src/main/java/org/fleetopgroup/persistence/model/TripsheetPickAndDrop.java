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
@Table(name = "TripsheetPickAndDrop")
public class TripsheetPickAndDrop implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripsheetPickAndDropId")
	private Long tripsheetPickAndDropId;
	
	@Column(name = "tripSheetNumber")
	private Long tripSheetNumber;

	@Column(name = "vid")
	private Integer vid;
	
	@Column(name = "journeyDate")
	private Date journeyDate;
	
	@Column(name = "tripFristDriverID")
	private int tripFristDriverID;
	
	@Column(name = "vendorId")
	private Integer vendorId;
	
	@Column(name = "newVendorName")
	private String newVendorName;
	
	@Column(name = "pickOrDropStatus")
	private short pickOrDropStatus;
	
	@Column(name = "pickOrDropId")
	private Long pickOrDropId;
	
	@Column(name = "newPickOrDropLocationName")
	private String newPickOrDropLocationName;
	
	@Column(name = "tripUsageKM", length = 10)
	private Integer tripUsageKM;
	
	@Column(name = "rate")
	private Double rate;
	
	@Column(name = "amount")
	private Double amount;
	
	@Column(name = "tripTotalAdvance")
	private Double tripTotalAdvance;
	
	@Column(name = "remark", length = 500)
	private String remark;
	
	@Column(name = "invoiceStatus")
	private short invoiceStatus;
	
	@Column(name = "creationDate")
	private Date	creationDate;
	
	@Column(name = "lastUpdatedOn")
	private Date	lastUpdatedOn;
	
	@Column(name = "createdById")
	private Long	createdById;
	
	@Column(name = "lastUpdatedBy")
	private Long	lastUpdatedBy;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	

	public TripsheetPickAndDrop() {
		super();
	}
	
	public TripsheetPickAndDrop(Long tripsheetPickAndDropId, Long tripSheetNumber, Integer vid, Date journeyDate,
			Date journeyTime, int tripFristDriverID, Integer vendorId, short pickOrDropStatus, Long pickOrDropId,
			Integer tripUsageKM, Double rate, Double amount, Double tripTotalAdvance, String remark, Date creationDate,
			Date lastUpdatedOn, Long createdById, Long lastUpdatedBy, Integer companyId, boolean markForDelete) {
		super();
		this.tripsheetPickAndDropId = tripsheetPickAndDropId;
		this.tripSheetNumber = tripSheetNumber;
		this.vid = vid;
		this.journeyDate = journeyDate;
		this.tripFristDriverID = tripFristDriverID;
		this.vendorId = vendorId;
		this.pickOrDropStatus = pickOrDropStatus;
		this.pickOrDropId = pickOrDropId;
		this.tripUsageKM = tripUsageKM;
		this.rate = rate;
		this.amount = amount;
		this.tripTotalAdvance = tripTotalAdvance;
		this.remark = remark;
		this.creationDate = creationDate;
		this.lastUpdatedOn = lastUpdatedOn;
		this.createdById = createdById;
		this.lastUpdatedBy = lastUpdatedBy;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pickOrDropId == null) ? 0 : pickOrDropId.hashCode());
		result = prime * result + pickOrDropStatus;
		result = prime * result + tripFristDriverID;
		result = prime * result + ((tripsheetPickAndDropId == null) ? 0 : tripsheetPickAndDropId.hashCode());
		result = prime * result + ((vendorId == null) ? 0 : vendorId.hashCode());
		result = prime * result + ((vid == null) ? 0 : vid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TripsheetPickAndDrop other = (TripsheetPickAndDrop) obj;
		if (pickOrDropId == null) {
			if (other.pickOrDropId != null)
				return false;
		} else if (!pickOrDropId.equals(other.pickOrDropId))
			return false;
		if (pickOrDropStatus != other.pickOrDropStatus)
			return false;
		if (tripFristDriverID != other.tripFristDriverID)
			return false;
		if (tripsheetPickAndDropId == null) {
			if (other.tripsheetPickAndDropId != null)
				return false;
		} else if (!tripsheetPickAndDropId.equals(other.tripsheetPickAndDropId))
			return false;
		if (vendorId == null) {
			if (other.vendorId != null)
				return false;
		} else if (!vendorId.equals(other.vendorId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}


	public Long getTripsheetPickAndDropId() {
		return tripsheetPickAndDropId;
	}


	public void setTripsheetPickAndDropId(Long tripsheetPickAndDropId) {
		this.tripsheetPickAndDropId = tripsheetPickAndDropId;
	}


	public Long getTripSheetNumber() {
		return tripSheetNumber;
	}


	public void setTripSheetNumber(Long tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}


	public Integer getVid() {
		return vid;
	}


	public void setVid(Integer vid) {
		this.vid = vid;
	}


	public Date getJourneyDate() {
		return journeyDate;
	}


	public void setJourneyDate(Date journeyDate) {
		this.journeyDate = journeyDate;
	}

	public int getTripFristDriverID() {
		return tripFristDriverID;
	}


	public void setTripFristDriverID(int tripFristDriverID) {
		this.tripFristDriverID = tripFristDriverID;
	}


	public Integer getVendorId() {
		return vendorId;
	}


	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}


	public short getPickOrDropStatus() {
		return pickOrDropStatus;
	}


	public void setPickOrDropStatus(short pickOrDropStatus) {
		this.pickOrDropStatus = pickOrDropStatus;
	}


	public Long getPickOrDropId() {
		return pickOrDropId;
	}


	public void setPickOrDropId(Long pickOrDropId) {
		this.pickOrDropId = pickOrDropId;
	}


	public Integer getTripUsageKM() {
		return tripUsageKM;
	}


	public void setTripUsageKM(Integer tripUsageKM) {
		this.tripUsageKM = tripUsageKM;
	}


	public Double getTripTotalAdvance() {
		return tripTotalAdvance;
	}


	public void setTripTotalAdvance(Double tripTotalAdvance) {
		this.tripTotalAdvance = tripTotalAdvance;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Date getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}


	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}


	public Long getCreatedById() {
		return createdById;
	}


	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}


	public Long getLastUpdatedBy() {
		return lastUpdatedBy;
	}


	public void setLastUpdatedBy(Long lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public short getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(short invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public String getNewVendorName() {
		return newVendorName;
	}

	public void setNewVendorName(String newVendorName) {
		this.newVendorName = newVendorName;
	}

	public String getNewPickOrDropLocationName() {
		return newPickOrDropLocationName;
	}

	public void setNewPickOrDropLocationName(String newPickOrDropLocationName) {
		this.newPickOrDropLocationName = newPickOrDropLocationName;
	}

	@Override
	public String toString() {
		return "TripsheetPickAndDrop [tripsheetPickAndDropId=" + tripsheetPickAndDropId + ", tripSheetNumber="
				+ tripSheetNumber + ", vid=" + vid + ", journeyDate=" + journeyDate + ", tripFristDriverID="
				+ tripFristDriverID + ", vendorId=" + vendorId + ", newVendorName=" + newVendorName
				+ ", pickOrDropStatus=" + pickOrDropStatus + ", pickOrDropId=" + pickOrDropId
				+ ", newPickOrDropLocationName=" + newPickOrDropLocationName + ", tripUsageKM=" + tripUsageKM
				+ ", rate=" + rate + ", amount=" + amount + ", tripTotalAdvance=" + tripTotalAdvance + ", remark="
				+ remark + ", invoiceStatus=" + invoiceStatus + ", creationDate=" + creationDate + ", lastUpdatedOn="
				+ lastUpdatedOn + ", createdById=" + createdById + ", lastUpdatedBy=" + lastUpdatedBy + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + "]";
	}
	
}