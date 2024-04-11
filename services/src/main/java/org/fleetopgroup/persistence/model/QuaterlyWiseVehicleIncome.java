package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "QuaterlyWiseVehicleIncome")
public class QuaterlyWiseVehicleIncome implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "quaterlyWiseVehicleIncomeId")
	private Long		quaterlyWiseVehicleIncomeId;
	
	@Column(name = "vid")
	private Integer 	vid;
	
	@Column(name = "incomeAmount")
	private Double		incomeAmount;
	
	@Column(name = "incomeType")
	private short		incomeType;
	
	@Column(name = "incomeId")
	private Integer 	incomeId;
	
	@Column(name = "txnTypeId")
	private Long		txnTypeId;
	
	@Column(name = "incomeQuater")
	private int			incomeQuater;
	
	@Column(name = "incomeYear")
	private int			incomeYear;
	
	@Column(name = "companyId", nullable = false)
	private Integer		companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean		markForDelete;
	
	public QuaterlyWiseVehicleIncome() {
		super();
	}

	public QuaterlyWiseVehicleIncome(Long quaterlyWiseVehicleIncomeId, Integer vid, Double incomeAmount,
			short incomeType, Integer incomeId, Long txnTypeId, int incomeQuater, int incomeYear, Integer companyId,
			boolean markForDelete) {
		super();
		this.quaterlyWiseVehicleIncomeId = quaterlyWiseVehicleIncomeId;
		this.vid = vid;
		this.incomeAmount = incomeAmount;
		this.incomeType = incomeType;
		this.incomeId = incomeId;
		this.txnTypeId = txnTypeId;
		this.incomeQuater = incomeQuater;
		this.incomeYear = incomeYear;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	public Long getQuaterlyWiseVehicleIncomeId() {
		return quaterlyWiseVehicleIncomeId;
	}

	public void setQuaterlyWiseVehicleIncomeId(Long quaterlyWiseVehicleIncomeId) {
		this.quaterlyWiseVehicleIncomeId = quaterlyWiseVehicleIncomeId;
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

	public Long getTxnTypeId() {
		return txnTypeId;
	}

	public void setTxnTypeId(Long txnTypeId) {
		this.txnTypeId = txnTypeId;
	}

	public int getIncomeQuater() {
		return incomeQuater;
	}

	public void setIncomeQuater(int incomeQuater) {
		this.incomeQuater = incomeQuater;
	}

	public int getIncomeYear() {
		return incomeYear;
	}

	public void setIncomeYear(int incomeYear) {
		this.incomeYear = incomeYear;
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
		result = prime * result + ((incomeId == null) ? 0 : incomeId.hashCode());
		result = prime * result + incomeQuater;
		result = prime * result + incomeYear;
		result = prime * result + ((quaterlyWiseVehicleIncomeId == null) ? 0 : quaterlyWiseVehicleIncomeId.hashCode());
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
		QuaterlyWiseVehicleIncome other = (QuaterlyWiseVehicleIncome) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (incomeId == null) {
			if (other.incomeId != null)
				return false;
		} else if (!incomeId.equals(other.incomeId))
			return false;
		if (incomeQuater != other.incomeQuater)
			return false;
		if (incomeYear != other.incomeYear)
			return false;
		if (quaterlyWiseVehicleIncomeId == null) {
			if (other.quaterlyWiseVehicleIncomeId != null)
				return false;
		} else if (!quaterlyWiseVehicleIncomeId.equals(other.quaterlyWiseVehicleIncomeId))
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
		return "QuaterlyWiseVehicleIncome [quaterlyWiseVehicleIncomeId=" + quaterlyWiseVehicleIncomeId + ", vid=" + vid
				+ ", incomeAmount=" + incomeAmount + ", incomeType=" + incomeType + ", incomeId=" + incomeId
				+ ", txnTypeId=" + txnTypeId + ", incomeQuater=" + incomeQuater + ", incomeYear=" + incomeYear
				+ ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	

}
