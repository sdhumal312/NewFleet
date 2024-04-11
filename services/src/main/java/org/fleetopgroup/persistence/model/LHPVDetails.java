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
@Table(name="LHPVDetails")
public class LHPVDetails  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lHPVDetailsId")
	private Long lHPVDetailsId;
	
	@Column(name = "lhpvId")
	private long 		lhpvId;
	
	@Column(name = "accountGroupId")
	private long 		accountGroupId;
	
	@Column(name = "lhpvDateTimeStamp")
	private Timestamp 	lhpvDateTimeStamp;
	
	@Column(name = "totalAmount")
	private double 		totalAmount;
	
	@Column(name = "advanceAmount")
	private double 		advanceAmount;
	
	@Column(name = "balanceAmount")
	private double 		balanceAmount;
	
	@Column(name = "vehicleNumberMasterId")
	private long 		vehicleNumberMasterId;
	
	@Column(name = "vehicleNumber")
	private String 		vehicleNumber;
	
	@Column(name = "lHPVNumber")
	private String 		lHPVNumber;
	
	@Column(name = "remark")
	private String 		remark;
	
	
	@Column(name = "lorryHire")
	private double		lorryHire;
	
	@Column(name = "companyId")
	private Integer		companyId;
	
	@Column(name = "vid", nullable = false)
	private Integer		vid;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean		markForDelete;
	
	@Column(name = "syncDateTime")
	private Timestamp	syncDateTime;
	
	@Column(name = "isTripSheetCreated", nullable = false)
	private boolean isTripSheetCreated;
	
	@Column(name = "tripSheetId")
	private Long	tripSheetId;
	
	@Column(name = "totalActualWeight")
	private Double		totalActualWeight;
	
	@Column(name = "boliWeight")
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
		this.totalAmount = totalAmount;
	}

	public double getAdvanceAmount() {
		return advanceAmount;
	}

	public void setAdvanceAmount(double advanceAmount) {
		this.advanceAmount = advanceAmount;
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = balanceAmount;
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
		this.lorryHire = lorryHire;
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

	public Timestamp getSyncDateTime() {
		return syncDateTime;
	}

	public void setSyncDateTime(Timestamp syncDateTime) {
		this.syncDateTime = syncDateTime;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public boolean isTripSheetCreated() {
		return isTripSheetCreated;
	}

	public void setTripSheetCreated(boolean isTripSheetCreated) {
		this.isTripSheetCreated = isTripSheetCreated;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public Double getTotalActualWeight() {
		return totalActualWeight;
	}

	public void setTotalActualWeight(Double totalActualWeight) {
		this.totalActualWeight = totalActualWeight;
	}

	public Double getBoliWeight() {
		return boliWeight;
	}

	public void setBoliWeight(Double boliWeight) {
		this.boliWeight = boliWeight;
	}

	@Override
	public String toString() {
		return "LHPVDetails [lHPVDetailsId=" + lHPVDetailsId + ", lhpvId=" + lhpvId + ", accountGroupId="
				+ accountGroupId + ", lhpvDateTimeStamp=" + lhpvDateTimeStamp + ", totalAmount=" + totalAmount
				+ ", advanceAmount=" + advanceAmount + ", balanceAmount=" + balanceAmount + ", vehicleNumberMasterId="
				+ vehicleNumberMasterId + ", vehicleNumber=" + vehicleNumber + ", lHPVNumber=" + lHPVNumber
				+ ", remark=" + remark + ", lorryHire=" + lorryHire + ", companyId=" + companyId + ", vid=" + vid
				+ ", markForDelete=" + markForDelete + ", syncDateTime=" + syncDateTime + ", isTripSheetCreated="
				+ isTripSheetCreated + "]";
	}

	
	
}
