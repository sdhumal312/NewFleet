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
@Table(name = "LHPVSettlementCharges")
public class LHPVSettlementCharges implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "lHPVSettlementChargesId")
	private Long	lHPVSettlementChargesId;
	
	@Column(name = "lHPVDetailsId")
	private Long	lHPVDetailsId;
	
	@Column(name = "lhpvId")
	private long	lhpvId;
	
	@Column(name = "lhpvDateTimeStamp")
	private Timestamp 	lhpvDateTimeStamp;

	@Column(name = "lhpvChargeMasterId")
	private long		lhpvChargeMasterId;
	
	@Column(name = "chargeAmount")
	private Double	chargeAmount;
	
	@Column(name = "chargeName")
	private String	chargeName;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;
	
	
	public LHPVSettlementCharges() {
		super();
	}


	public LHPVSettlementCharges(Long lHPVSettlementChargesId, Long lHPVDetailsId, long lhpvId,
			Timestamp lhpvDateTimeStamp, long lhpvChargeMasterId, Double chargeAmount, String chargeName,
			Integer companyId, boolean markForDelete) {
		super();
		this.lHPVSettlementChargesId = lHPVSettlementChargesId;
		this.lHPVDetailsId = lHPVDetailsId;
		this.lhpvId = lhpvId;
		this.lhpvDateTimeStamp = lhpvDateTimeStamp;
		this.lhpvChargeMasterId = lhpvChargeMasterId;
		this.chargeAmount = chargeAmount;
		this.chargeName = chargeName;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}


	public Long getlHPVSettlementChargesId() {
		return lHPVSettlementChargesId;
	}


	public void setlHPVSettlementChargesId(Long lHPVSettlementChargesId) {
		this.lHPVSettlementChargesId = lHPVSettlementChargesId;
	}


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


	public Timestamp getLhpvDateTimeStamp() {
		return lhpvDateTimeStamp;
	}


	public void setLhpvDateTimeStamp(Timestamp lhpvDateTimeStamp) {
		this.lhpvDateTimeStamp = lhpvDateTimeStamp;
	}


	public long getLhpvChargeMasterId() {
		return lhpvChargeMasterId;
	}


	public void setLhpvChargeMasterId(long lhpvChargeMasterId) {
		this.lhpvChargeMasterId = lhpvChargeMasterId;
	}


	public Double getChargeAmount() {
		return chargeAmount;
	}


	public void setChargeAmount(Double chargeAmount) {
		this.chargeAmount = chargeAmount;
	}


	public String getChargeName() {
		return chargeName;
	}


	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
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


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lHPVDetailsId == null) ? 0 : lHPVDetailsId.hashCode());
		result = prime * result + ((lHPVSettlementChargesId == null) ? 0 : lHPVSettlementChargesId.hashCode());
		result = prime * result + (int) (lhpvChargeMasterId ^ (lhpvChargeMasterId >>> 32));
		result = prime * result + (int) (lhpvId ^ (lhpvId >>> 32));
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
		LHPVSettlementCharges other = (LHPVSettlementCharges) obj;
		if (lHPVDetailsId == null) {
			if (other.lHPVDetailsId != null)
				return false;
		} else if (!lHPVDetailsId.equals(other.lHPVDetailsId))
			return false;
		if (lHPVSettlementChargesId == null) {
			if (other.lHPVSettlementChargesId != null)
				return false;
		} else if (!lHPVSettlementChargesId.equals(other.lHPVSettlementChargesId))
			return false;
		if (lhpvChargeMasterId != other.lhpvChargeMasterId)
			return false;
		if (lhpvId != other.lhpvId)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "LHPVSettlementCharges [lHPVSettlementChargesId=" + lHPVSettlementChargesId + ", lHPVDetailsId="
				+ lHPVDetailsId + ", lhpvId=" + lhpvId + ", lhpvDateTimeStamp=" + lhpvDateTimeStamp
				+ ", lhpvChargeMasterId=" + lhpvChargeMasterId + ", chargeAmount=" + chargeAmount + ", chargeName="
				+ chargeName + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	
}
