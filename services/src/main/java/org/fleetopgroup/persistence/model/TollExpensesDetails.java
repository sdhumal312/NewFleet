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
@Table(name="TollExpensesDetails")
public class TollExpensesDetails  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tollExpensesDetailsId")
	private Long		tollExpensesDetailsId;
	
	@Column(name = "processingDateTime")
	private Timestamp	processingDateTime;
	
	@Column(name = "transactionDateTime")
	private Timestamp	transactionDateTime;
	
	@Column(name = "transactionAmount")
	private Double		transactionAmount;
	
	@Column(name = "transactionId")
	private String		transactionId;
	
	@Column(name = "vid")
	private Integer		vid;
	
	@Column(name = "tripSheetId")
	private Long	tripSheetId;
	
	@Column(name = "laneCode")
	private String		laneCode;
	
	@Column(name = "plazaCode")
	private String		plazaCode;
	
	@Column(name = "transactionStatus")
	private String		transactionStatus;
	
	@Column(name = "transactionReferenceNumber")
	private String		transactionReferenceNumber;
	
	@Column(name = "plazaName")
	private String		plazaName;
	
	@Column(name = "companyId")
	private Integer		companyId;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	@Column(name = "createdOn")
	private Timestamp	createdOn;
	
	@Column(name = "createdById")
	private Long		createdById;
	
	@Column(name = "fastTagBankId")
	private short		fastTagBankId;
	
	@Column(name = "isPendingForTally", nullable = false)
	private boolean isPendingForTally;
	
	
	
	public TollExpensesDetails() {
		super();
	}

	public TollExpensesDetails(Long tollExpensesDetailsId, Timestamp processingDateTime, Timestamp transactionDateTime,
			Double transactionAmount, String transactionId, Integer vid, Long tripSheetId, String laneCode,
			String plazaCode, String transactionStatus, String transactionReferenceNumber, String plazaName,
			Integer companyId, boolean markForDelete) {
		super();
		this.tollExpensesDetailsId = tollExpensesDetailsId;
		this.processingDateTime = processingDateTime;
		this.transactionDateTime = transactionDateTime;
		this.transactionAmount = transactionAmount;
		this.transactionId = transactionId;
		this.vid = vid;
		this.tripSheetId = tripSheetId;
		this.laneCode = laneCode;
		this.plazaCode = plazaCode;
		this.transactionStatus = transactionStatus;
		this.transactionReferenceNumber = transactionReferenceNumber;
		this.plazaName = plazaName;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((tollExpensesDetailsId == null) ? 0 : tollExpensesDetailsId.hashCode());
		result = prime * result + ((transactionAmount == null) ? 0 : transactionAmount.hashCode());
		result = prime * result + ((transactionId == null) ? 0 : transactionId.hashCode());
		result = prime * result + ((tripSheetId == null) ? 0 : tripSheetId.hashCode());
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
		TollExpensesDetails other = (TollExpensesDetails) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (tollExpensesDetailsId == null) {
			if (other.tollExpensesDetailsId != null)
				return false;
		} else if (!tollExpensesDetailsId.equals(other.tollExpensesDetailsId))
			return false;
		if (transactionAmount == null) {
			if (other.transactionAmount != null)
				return false;
		} else if (!transactionAmount.equals(other.transactionAmount))
			return false;
		if (transactionId == null) {
			if (other.transactionId != null)
				return false;
		} else if (!transactionId.equals(other.transactionId))
			return false;
		if (tripSheetId == null) {
			if (other.tripSheetId != null)
				return false;
		} else if (!tripSheetId.equals(other.tripSheetId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}
	
	
	

	public Long getTollExpensesDetailsId() {
		return tollExpensesDetailsId;
	}

	public void setTollExpensesDetailsId(Long tollExpensesDetailsId) {
		this.tollExpensesDetailsId = tollExpensesDetailsId;
	}

	public Timestamp getProcessingDateTime() {
		return processingDateTime;
	}

	public void setProcessingDateTime(Timestamp processingDateTime) {
		this.processingDateTime = processingDateTime;
	}

	public Timestamp getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(Timestamp transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public Double getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount = transactionAmount;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public String getLaneCode() {
		return laneCode;
	}

	public void setLaneCode(String laneCode) {
		this.laneCode = laneCode;
	}

	public String getPlazaCode() {
		return plazaCode;
	}

	public void setPlazaCode(String plazaCode) {
		this.plazaCode = plazaCode;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionReferenceNumber() {
		return transactionReferenceNumber;
	}

	public void setTransactionReferenceNumber(String transactionReferenceNumber) {
		this.transactionReferenceNumber = transactionReferenceNumber;
	}

	public String getPlazaName() {
		return plazaName;
	}

	public void setPlazaName(String plazaName) {
		this.plazaName = plazaName;
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

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public short getFastTagBankId() {
		return fastTagBankId;
	}

	public void setFastTagBankId(short fastTagBankId) {
		this.fastTagBankId = fastTagBankId;
	}

	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	@Override
	public String toString() {
		return "TollExpensesDetails [tollExpensesDetailsId=" + tollExpensesDetailsId + ", processingDateTime="
				+ processingDateTime + ", transactionDateTime=" + transactionDateTime + ", transactionAmount="
				+ transactionAmount + ", transactionId=" + transactionId + ", vid=" + vid + ", tripSheetId="
				+ tripSheetId + ", laneCode=" + laneCode + ", plazaCode=" + plazaCode + ", transactionStatus="
				+ transactionStatus + ", transactionReferenceNumber=" + transactionReferenceNumber + ", plazaName="
				+ plazaName + ", companyId=" + companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	
	
}
