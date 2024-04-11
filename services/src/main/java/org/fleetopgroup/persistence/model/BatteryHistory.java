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
@Table(name = "BatteryHistory")
public class BatteryHistory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "batteryHistoryId")
	private Long batteryHistoryId;
	
	@Column(name = "batteryId", nullable = false)
	private Long batteryId;
	
	@Column(name = "batterySerialNumber")
	private String batterySerialNumber;
	
	@Column(name = "vid")
	private Integer	vid;
	
	@Column(name = "layoutPosition")
	private short layoutPosition;
	
	@Column(name = "batteryStatusId")
	private short batteryStatusId;
	
	@Column(name = "openOdometer")
	private Integer openOdometer;
	
	@Column(name = "batteryUseage")
	private Integer batteryUseage;
	
	@Column(name = "usesNoOfDay")
	private Long  usesNoOfDay;
	
	@Column(name = "batteryCost")
	private Double batteryCost;
	
	@Column(name = "batteryAsignDate")
	private Timestamp batteryAsignDate;
	
	@Column(name = "batteryScrapRemark")
	private String batteryScrapRemark;
	
	@Column(name = "batteryComment")
	private String batteryComment;
	
	@Column(name = "ScrapReason")
	private String scrapreason;
	
	@Column(name = "preBatteryAsignDate")
	private Timestamp preBatteryAsignDate;
	
	@Column(name = "preBatterySerialNumber")
	private String preBatterySerialNumber;
	
	@Column(name = "createdById")
	private Long		createdById;
	
	@Column(name = "createdOn")
	private Timestamp	createdOn;
	
	public String getScrapreason() {
		return scrapreason;
	}

	public void setScrapreason(String scrapreason) {
		this.scrapreason = scrapreason;
	}

	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	public BatteryHistory() {
		super();
	}

	public BatteryHistory(Long batteryHistoryId, Long batteryId, String batterySerialNumber, Integer vid,
			short layoutPosition, short batteryStatusId, Integer openOdometer, Integer batteryUseage,
			Double batteryCost, Timestamp batteryAsignDate, String batteryComment, Integer companyId,
			boolean markForDelete) {
		super();
		this.batteryHistoryId = batteryHistoryId;
		this.batteryId = batteryId;
		this.batterySerialNumber = batterySerialNumber;
		this.vid = vid;
		this.layoutPosition = layoutPosition;
		this.batteryStatusId = batteryStatusId;
		this.openOdometer = openOdometer;
		this.batteryUseage = batteryUseage;
		this.batteryCost = batteryCost;
		this.batteryAsignDate = batteryAsignDate;
		this.batteryComment = batteryComment;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	public Long getBatteryHistoryId() {
		return batteryHistoryId;
	}

	public Long getBatteryId() {
		return batteryId;
	}

	public String getBatterySerialNumber() {
		return batterySerialNumber;
	}

	public Integer getVid() {
		return vid;
	}

	public short getLayoutPosition() {
		return layoutPosition;
	}

	public short getBatteryStatusId() {
		return batteryStatusId;
	}

	public Integer getOpenOdometer() {
		return openOdometer;
	}

	public Integer getBatteryUseage() {
		return batteryUseage;
	}

	public Double getBatteryCost() {
		return batteryCost;
	}

	public Timestamp getBatteryAsignDate() {
		return batteryAsignDate;
	}

	public String getBatteryComment() {
		return batteryComment;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setBatteryHistoryId(Long batteryHistoryId) {
		this.batteryHistoryId = batteryHistoryId;
	}

	public void setBatteryId(Long batteryId) {
		this.batteryId = batteryId;
	}

	public void setBatterySerialNumber(String batterySerialNumber) {
		this.batterySerialNumber = batterySerialNumber;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public void setLayoutPosition(short layoutPosition) {
		this.layoutPosition = layoutPosition;
	}

	public void setBatteryStatusId(short batteryStatusId) {
		this.batteryStatusId = batteryStatusId;
	}

	public void setOpenOdometer(Integer openOdometer) {
		this.openOdometer = openOdometer;
	}

	public void setBatteryUseage(Integer batteryUseage) {
		this.batteryUseage = batteryUseage;
	}

	public void setBatteryCost(Double batteryCost) {
		this.batteryCost = batteryCost;
	}

	public void setBatteryAsignDate(Timestamp batteryAsignDate) {
		this.batteryAsignDate = batteryAsignDate;
	}

	public void setBatteryComment(String batteryComment) {
		this.batteryComment = batteryComment;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

/*	public Timestamp getBatteryDismountDate() {
		return batteryDismountDate;
	}

	public void setBatteryDismountDate(Timestamp batteryDismountDate) {
		this.batteryDismountDate = batteryDismountDate;
	}
*/
	public Long getUsesNoOfDay() {
		return usesNoOfDay;
	}

	public void setUsesNoOfDay(Long usesNoOfDay) {
		this.usesNoOfDay = usesNoOfDay;
	}

	public String getBatteryScrapRemark() {
		return batteryScrapRemark;
	}

	public void setBatteryScrapRemark(String batteryScrapRemark) {
		this.batteryScrapRemark = batteryScrapRemark;
	}

	public Timestamp getPreBatteryAsignDate() {
		return preBatteryAsignDate;
	}

	public void setPreBatteryAsignDate(Timestamp preBatteryAsignDate) {
		this.preBatteryAsignDate = preBatteryAsignDate;
	}

	public String getPreBatterySerialNumber() {
		return preBatterySerialNumber;
	}

	public void setPreBatterySerialNumber(String preBatterySerialNumber) {
		this.preBatterySerialNumber = preBatterySerialNumber;
	}
	

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((batteryAsignDate == null) ? 0 : batteryAsignDate.hashCode());
		result = prime * result + ((batteryCost == null) ? 0 : batteryCost.hashCode());
		result = prime * result + ((batteryId == null) ? 0 : batteryId.hashCode());
		result = prime * result + ((batterySerialNumber == null) ? 0 : batterySerialNumber.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
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
		BatteryHistory other = (BatteryHistory) obj;
		if (batteryAsignDate == null) {
			if (other.batteryAsignDate != null)
				return false;
		} else if (!batteryAsignDate.equals(other.batteryAsignDate))
			return false;
		if (batteryCost == null) {
			if (other.batteryCost != null)
				return false;
		} else if (!batteryCost.equals(other.batteryCost))
			return false;
		if (batteryId == null) {
			if (other.batteryId != null)
				return false;
		} else if (!batteryId.equals(other.batteryId))
			return false;
		if (batterySerialNumber == null) {
			if (other.batterySerialNumber != null)
				return false;
		} else if (!batterySerialNumber.equals(other.batterySerialNumber))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BatteryHistory [batteryHistoryId=" + batteryHistoryId + ", batteryId=" + batteryId
				+ ", batterySerialNumber=" + batterySerialNumber + ", vid=" + vid + ", layoutPosition=" + layoutPosition
				+ ", batteryStatusId=" + batteryStatusId + ", openOdometer=" + openOdometer + ", batteryUseage="
				+ batteryUseage + ", usesNoOfDay=" + usesNoOfDay + ", batteryCost=" + batteryCost
				+ ", batteryAsignDate=" + batteryAsignDate + ", batteryScrapRemark=" + batteryScrapRemark
				+ ", batteryComment=" + batteryComment + ", scrapreason=" + scrapreason + ", preBatteryAsignDate="
				+ preBatteryAsignDate + ", preBatterySerialNumber=" + preBatterySerialNumber + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	
	
}
