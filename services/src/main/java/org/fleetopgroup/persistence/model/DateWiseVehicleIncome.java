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
@Table(name = "DateWiseVehicleIncome")
public class DateWiseVehicleIncome implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dateWiseVehicleIncomeId")
	private Long	dateWiseVehicleIncomeId;
	
	@Column(name = "vid")
	private Integer 	vid;
	
	@Column(name = "incomeAmount")
	private Double		incomeAmount;
	
	@Column(name = "incomeType")
	private short		incomeType;
	
	@Column(name = "incomeId")
	private Integer 	incomeId;
	
	@Column(name = "incomeDate", nullable = false)
	private Timestamp	incomeDate;
	
	
	@Column(name = "txnTypeId")
	private Long		txnTypeId;
	
	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean		markForDelete;
	
	
	public DateWiseVehicleIncome() {
		super();
	}


	public DateWiseVehicleIncome(Long dateWiseVehicleIncomeId, Integer vid, Double incomeAmount, short incomeType,
			Integer incomeId, Timestamp incomeDate, Long txnTypeId, Integer companyId, boolean markForDelete) {
		super();
		this.dateWiseVehicleIncomeId = dateWiseVehicleIncomeId;
		this.vid = vid;
		this.incomeAmount = incomeAmount;
		this.incomeType = incomeType;
		this.incomeId = incomeId;
		this.incomeDate = incomeDate;
		this.txnTypeId = txnTypeId;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}


	public Long getDateWiseVehicleIncomeId() {
		return dateWiseVehicleIncomeId;
	}


	public void setDateWiseVehicleIncomeId(Long dateWiseVehicleIncomeId) {
		this.dateWiseVehicleIncomeId = dateWiseVehicleIncomeId;
	}


	public Integer getVid() {
		return vid;
	}


	public void setVid(Integer vid) {
		this.vid = vid;
	}


	public Double getIncomeAmount() {
		return incomeAmount;
	}


	public void setIncomeAmount(Double incomeAmount) {
		this.incomeAmount = incomeAmount;
	}


	public short getIncomeType() {
		return incomeType;
	}


	public void setIncomeType(short incomeType) {
		this.incomeType = incomeType;
	}


	public Integer getIncomeId() {
		return incomeId;
	}


	public void setIncomeId(Integer incomeId) {
		this.incomeId = incomeId;
	}


	public Timestamp getIncomeDate() {
		return incomeDate;
	}


	public void setIncomeDate(Timestamp incomeDate) {
		this.incomeDate = incomeDate;
	}


	public Long getTxnTypeId() {
		return txnTypeId;
	}


	public void setTxnTypeId(Long txnTypeId) {
		this.txnTypeId = txnTypeId;
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
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((dateWiseVehicleIncomeId == null) ? 0 : dateWiseVehicleIncomeId.hashCode());
		result = prime * result + ((incomeId == null) ? 0 : incomeId.hashCode());
		result = prime * result + ((txnTypeId == null) ? 0 : txnTypeId.hashCode());
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
		DateWiseVehicleIncome other = (DateWiseVehicleIncome) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (dateWiseVehicleIncomeId == null) {
			if (other.dateWiseVehicleIncomeId != null)
				return false;
		} else if (!dateWiseVehicleIncomeId.equals(other.dateWiseVehicleIncomeId))
			return false;
		if (incomeId == null) {
			if (other.incomeId != null)
				return false;
		} else if (!incomeId.equals(other.incomeId))
			return false;
		if (txnTypeId == null) {
			if (other.txnTypeId != null)
				return false;
		} else if (!txnTypeId.equals(other.txnTypeId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "DateWiseVehicleIncome [dateWiseVehicleIncomeId=" + dateWiseVehicleIncomeId + ", vid=" + vid
				+ ", incomeAmount=" + incomeAmount + ", incomeType=" + incomeType + ", incomeId=" + incomeId
				+ ", incomeDate=" + incomeDate + ", txnTypeId=" + txnTypeId + ", companyId=" + companyId
				+ ", markForDelete=" + markForDelete + "]";
	}
	
	

}
