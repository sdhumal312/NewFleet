package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class LHPVDetailsDto {
	
	private Long 		lHPVDetailsId;
	
	private long 		lhpvId;
	
	private long 		accountGroupId;
	
	private Timestamp 	lhpvDateTimeStamp;
	
	private double 		totalAmount;
	
	private double 		advanceAmount;
	
	private double 		balanceAmount;
	
	private long 		vehicleNumberMasterId;
	
	private String 		vehicleNumber;
	
	private String 		lHPVNumber;
	
	private String 		remark;
	
	private double		lorryHire;
	
	private Integer		companyId;
	
	private Integer		vid;
	
	private boolean		markForDelete;
	
	private Timestamp	syncDateTime;
	
	private Long		lhpvSettlementChargesId;
	
	private long		lhpvChargeTypeMasterId;
	
	private String		chargeName;
	
	private double		chargeAmount;
	
	private String		lhpvDateTime;
	
	private Double		totalActualWeight;
	
	private Double		boliWeight;
	
	public Long getlHPVDetailsId() {
		return lHPVDetailsId;
	}

	public void setlHPVDetailsId(Long lHPVDetailsId) {
		this.lHPVDetailsId = lHPVDetailsId;
	}

	public long getLhpvId() {
		return lhpvId;
	}

	public void setLhpvId(long lhpvId) {
		this.lhpvId = lhpvId;
	}

	public long getAccountGroupId() {
		return accountGroupId;
	}

	public void setAccountGroupId(long accountGroupId) {
		this.accountGroupId = accountGroupId;
	}

	public Timestamp getLhpvDateTimeStamp() {
		return lhpvDateTimeStamp;
	}

	public void setLhpvDateTimeStamp(Timestamp lhpvDateTimeStamp) {
		this.lhpvDateTimeStamp = lhpvDateTimeStamp;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = Utility.round(totalAmount, 2);
	}

	public double getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(double advanceAmount) {
		this.advanceAmount = Utility.round(advanceAmount, 2);
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2);
	}

	public long getVehicleNumberMasterId() {
		return vehicleNumberMasterId;
	}

	public void setVehicleNumberMasterId(long vehicleNumberMasterId) {
		this.vehicleNumberMasterId = vehicleNumberMasterId;
	}

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getlHPVNumber() {
		return lHPVNumber;
	}

	public void setlHPVNumber(String lHPVNumber) {
		this.lHPVNumber = lHPVNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public double getLorryHire() {
		return lorryHire;
	}

	public void setLorryHire(double lorryHire) {
		this.lorryHire = Utility.round(lorryHire,2);
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public String getChargeName() {
		return chargeName;
	}

	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}

	public long getLhpvChargeTypeMasterId() {
		return lhpvChargeTypeMasterId;
	}

	public void setLhpvChargeTypeMasterId(long lhpvChargeTypeMasterId) {
		this.lhpvChargeTypeMasterId = lhpvChargeTypeMasterId;
	}

	public Long getLhpvSettlementChargesId() {
		return lhpvSettlementChargesId;
	}

	public void setLhpvSettlementChargesId(Long lhpvSettlementChargesId) {
		this.lhpvSettlementChargesId = lhpvSettlementChargesId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Timestamp getSyncDateTime() {
		return syncDateTime;
	}

	public void setSyncDateTime(Timestamp syncDateTime) {
		this.syncDateTime = syncDateTime;
	}

	public double getChargeAmount() {
		return chargeAmount;
	}

	public void setChargeAmount(double chargeAmount) {
		this.chargeAmount = Utility.round(chargeAmount, 2);
	}

	public String getLhpvDateTime() {
		return lhpvDateTime;
	}

	public void setLhpvDateTime(String lhpvDateTime) {
		this.lhpvDateTime = lhpvDateTime;
	}

	public Double getTotalActualWeight() {
		return totalActualWeight;
	}

	public void setTotalActualWeight(Double totalActualWeight) {
		this.totalActualWeight = Utility.round(totalActualWeight, 2);
	}

	public Double getBoliWeight() {
		return boliWeight;
	}

	public void setBoliWeight(Double boliWeight) {
		this.boliWeight = Utility.round(boliWeight, 2);
	}

	@Override
	public String toString() {
		return "LHPVDetailsDto [lHPVDetailsId=" + lHPVDetailsId + ", lhpvId=" + lhpvId + ", accountGroupId="
				+ accountGroupId + ", lhpvDateTimeStamp=" + lhpvDateTimeStamp + ", totalAmount=" + totalAmount
				+ ", advanceAmount=" + advanceAmount + ", balanceAmount=" + balanceAmount + ", vehicleNumberMasterId="
				+ vehicleNumberMasterId + ", vehicleNumber=" + vehicleNumber + ", lHPVNumber=" + lHPVNumber
				+ ", remark=" + remark + ", lorryHire=" + lorryHire + ", companyId=" + companyId + ", vid=" + vid
				+ ", markForDelete=" + markForDelete + ", syncDateTime=" + syncDateTime + "]";
	}

	
	
	
}
