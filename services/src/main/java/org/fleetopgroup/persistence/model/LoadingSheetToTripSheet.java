package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LoadingSheetToTripSheet")
public class LoadingSheetToTripSheet implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="loadingSheetToTripSheetId")
	private Long		loadingSheetToTripSheetId;
	
	@Column(name="dispatchLedgerId")
	private long		dispatchLedgerId;
	
	@Column(name="wayBillId")
	private long		wayBillId;
	
	@Column(name="wayBillNumber")
	private String		wayBillNumber;
	
	@Column(name="bookingTotal")
	private double		bookingTotal;
	
	@Column(name="freight")
	private double		freight;
	
	@Column(name="lsNumber")
	private String		lsNumber;
	
	@Column(name="totalNoOfPackages")
	private	int			totalNoOfPackages;
	
	@Column(name="wayBillCrossingId")
	private long		wayBillCrossingId;
	
	@Column(name="tripDateTime")
	private Timestamp	tripDateTime;
	
	@Column(name="tripSheetId")
	private Long	tripSheetId;
	
	@Column(name="companyId")
	private Integer	companyId;
	
	@Column(name="waybillTypeId", columnDefinition = "integer default 0")
	private short waybillTypeId;
	
	@Column(name="markForDelete")
	private	boolean	markForDelete;
	
	@Column(name="totalActualWeight")
	private Double	totalActualWeight;
	
	@Column(name="dispatchedWeight")
	private Double	dispatchedWeight;
	
	
	@Column(name="lrSourceBranchId")
	private Long	lrSourceBranchId;
	
	
	@Column(name="lrDestinationBranchId")
	private Long	lrDestinationBranchId;
	
	
	
	@Column(name="lsSourceBranchId")
	private Long	lsSourceBranchId;
	
	
	@Column(name="lsDestinationBranchId")
	private Long	lsDestinationBranchId;
	
	
	@Column(name="lrSourceBranch")
	private String	lrSourceBranch;
	
	
	@Column(name="lsSourceBranch")
	private String	lsSourceBranch;
	
	
	@Column(name="lsDestinationBranch")
	private String	lsDestinationBranch;
	
	@Column(name="billSelectionId")
	private Short billSelectionId;
	
	public String getLsSourceBranch() {
		return lsSourceBranch;
	}
	public void setLsSourceBranch(String lsSourceBranch) {
		this.lsSourceBranch = lsSourceBranch;
	}


	public String getLsDestinationBranch() {
		return lsDestinationBranch;
	}


	public void setLsDestinationBranch(String lsDestinationBranch) {
		this.lsDestinationBranch = lsDestinationBranch;
	}


	public String getLrDestinationBranch() {
		return lrDestinationBranch;
	}


	public void setLrDestinationBranch(String lrDestinationBranch) {
		this.lrDestinationBranch = lrDestinationBranch;
	}


	@Column(name="lrDestinationBranch")
	private String	lrDestinationBranch;
	
	
	
	
	public String getLrSourceBranch() {
		return lrSourceBranch;
	}


	public void setLrSourceBranch(String lrSourceBranch) {
		this.lrSourceBranch = lrSourceBranch;
	}


	public LoadingSheetToTripSheet() {
		super();
	}


	public Long getLoadingSheetToTripSheetId() {
		return loadingSheetToTripSheetId;
	}


	public Long getLrSourceBranchId() {
		return lrSourceBranchId;
	}


	public void setLrSourceBranchId(Long lrSourceBranchId) {
		this.lrSourceBranchId = lrSourceBranchId;
	}


	public Long getLrDestinationBranchId() {
		return lrDestinationBranchId;
	}


	public void setLrDestinationBranchId(Long lrDestinationBranchId) {
		this.lrDestinationBranchId = lrDestinationBranchId;
	}


	public Long getLsSourceBranchId() {
		return lsSourceBranchId;
	}


	public void setLsSourceBranchId(Long lsSourceBranchId) {
		this.lsSourceBranchId = lsSourceBranchId;
	}


	public Long getLsDestinationBranchId() {
		return lsDestinationBranchId;
	}


	public void setLsDestinationBranchId(Long lsDestinationBranchId) {
		this.lsDestinationBranchId = lsDestinationBranchId;
	}


	public void setLoadingSheetToTripSheetId(Long loadingSheetToTripSheetId) {
		this.loadingSheetToTripSheetId = loadingSheetToTripSheetId;
	}


	public long getDispatchLedgerId() {
		return dispatchLedgerId;
	}


	public void setDispatchLedgerId(long dispatchLedgerId) {
		this.dispatchLedgerId = dispatchLedgerId;
	}


	public long getWayBillId() {
		return wayBillId;
	}


	public void setWayBillId(long wayBillId) {
		this.wayBillId = wayBillId;
	}


	public String getWayBillNumber() {
		return wayBillNumber;
	}


	public void setWayBillNumber(String wayBillNumber) {
		this.wayBillNumber = wayBillNumber;
	}


	public Double getTotalActualWeight() {
		return totalActualWeight;
	}


	public void setTotalActualWeight(Double totalActualWeight) {
		this.totalActualWeight = totalActualWeight;
	}


	public Double getDispatchedWeight() {
		return dispatchedWeight;
	}


	public void setDispatchedWeight(Double dispatchedWeight) {
		this.dispatchedWeight = dispatchedWeight;
	}


	public double getBookingTotal() {
		return bookingTotal;
	}


	public void setBookingTotal(double bookingTotal) {
		this.bookingTotal = bookingTotal;
	}


	public double getFreight() {
		return freight;
	}


	public void setFreight(double freight) {
		this.freight = freight;
	}


	public String getLsNumber() {
		return lsNumber;
	}


	public void setLsNumber(String lsNumber) {
		this.lsNumber = lsNumber;
	}


	public int getTotalNoOfPackages() {
		return totalNoOfPackages;
	}


	public void setTotalNoOfPackages(int totalNoOfPackages) {
		this.totalNoOfPackages = totalNoOfPackages;
	}


	public long getWayBillCrossingId() {
		return wayBillCrossingId;
	}


	public void setWayBillCrossingId(long wayBillCrossingId) {
		this.wayBillCrossingId = wayBillCrossingId;
	}


	public Timestamp getTripDateTime() {
		return tripDateTime;
	}


	public void setTripDateTime(Timestamp tripDateTime) {
		this.tripDateTime = tripDateTime;
	}


	public Long getTripSheetId() {
		return tripSheetId;
	}


	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
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


	public short getWaybillTypeId() {
		return waybillTypeId;
	}


	public void setWaybillTypeId(short waybillTypeId) {
		this.waybillTypeId = waybillTypeId;
	}


	public Short getBillSelectionId() {
		return billSelectionId;
	}


	public void setBillSelectionId(Short billSelectionId) {
		this.billSelectionId = billSelectionId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (dispatchLedgerId ^ (dispatchLedgerId >>> 32));
		result = prime * result + ((loadingSheetToTripSheetId == null) ? 0 : loadingSheetToTripSheetId.hashCode());
		result = prime * result + ((tripSheetId == null) ? 0 : tripSheetId.hashCode());
		result = prime * result + (int) (wayBillId ^ (wayBillId >>> 32));
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
		LoadingSheetToTripSheet other = (LoadingSheetToTripSheet) obj;
		if (dispatchLedgerId != other.dispatchLedgerId)
			return false;
		if (loadingSheetToTripSheetId == null) {
			if (other.loadingSheetToTripSheetId != null)
				return false;
		} else if (!loadingSheetToTripSheetId.equals(other.loadingSheetToTripSheetId))
			return false;
		if (tripSheetId == null) {
			if (other.tripSheetId != null)
				return false;
		} else if (!tripSheetId.equals(other.tripSheetId))
			return false;
		if (wayBillId != other.wayBillId)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "LoadingSheetToTripSheet [loadingSheetToTripSheetId=" + loadingSheetToTripSheetId + ", dispatchLedgerId="
				+ dispatchLedgerId + ", wayBillId=" + wayBillId + ", wayBillNumber=" + wayBillNumber + ", bookingTotal="
				+ bookingTotal + ", freight=" + freight + ", lsNumber=" + lsNumber + ", totalNoOfPackages="
				+ totalNoOfPackages + ", wayBillCrossingId=" + wayBillCrossingId + ", tripDateTime=" + tripDateTime
				+ ", tripSheetId=" + tripSheetId + ", companyId=" + companyId + ", waybillTypeId=" + waybillTypeId
				+ ", markForDelete=" + markForDelete + ", totalActualWeight=" + totalActualWeight
				+ ", dispatchedWeight=" + dispatchedWeight + ", lrSourceBranchId=" + lrSourceBranchId
				+ ", lsSourceBranchId=" + lsSourceBranchId + ", lrDestinationBranchId=" + lrDestinationBranchId
				+ ", lsDestinationBranchId=" + lsDestinationBranchId + ", lrSourceBranch=" + lrSourceBranch
				+ ", lsSourceBranch=" + lsSourceBranch + ", lsDestinationBranch=" + lsDestinationBranch
				+ ", lrDestinationBranch=" + lrDestinationBranch + ", billSelectionId=" + billSelectionId + "]";
	}


	
}
