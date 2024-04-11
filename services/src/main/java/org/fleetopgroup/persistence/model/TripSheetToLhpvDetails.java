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
@Table(name="TripSheetToLhpvDetails")
public class TripSheetToLhpvDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="tripSheetToLhpvDetailsId")
	private Long	tripSheetToLhpvDetailsId;
	
	@Column(name="tripSheetId")
	private Long	tripSheetId;
	
	@Column(name="lhpvId")
	private Long	lhpvId;
	
	@Column(name="lhpvNumber")
	private String	lhpvNumber;
	
	@Column(name="lhpvDate")
	private Date	lhpvDate;
	
	@Column(name="lhpvAdvance")
	private	Double	lhpvAdvance;
	
	@Column(name="lhpvLorryHire")
	private Double	lhpvLorryHire;
	
	@Column(name="companyId")
	private Integer	companyId;
	
	@Column(name="markForDelete")
	private boolean	markForDelete;

	public Long getTripSheetToLhpvDetailsId() {
		return tripSheetToLhpvDetailsId;
	}

	public void setTripSheetToLhpvDetailsId(Long tripSheetToLhpvDetailsId) {
		this.tripSheetToLhpvDetailsId = tripSheetToLhpvDetailsId;
	}

	public Long getTripSheetId() {
		return tripSheetId;
	}

	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}

	public Long getLhpvId() {
		return lhpvId;
	}

	public void setLhpvId(Long lhpvId) {
		this.lhpvId = lhpvId;
	}

	public String getLhpvNumber() {
		return lhpvNumber;
	}

	public void setLhpvNumber(String lhpvNumber) {
		this.lhpvNumber = lhpvNumber;
	}

	public Date getLhpvDate() {
		return lhpvDate;
	}

	public void setLhpvDate(Date lhpvDate) {
		this.lhpvDate = lhpvDate;
	}


	public Double getLhpvAdvance() {
		return lhpvAdvance;
	}

	public void setLhpvAdvance(Double lhpvAdvance) {
		this.lhpvAdvance = lhpvAdvance;
	}

	public Double getLhpvLorryHire() {
		return lhpvLorryHire;
	}

	public void setLhpvLorryHire(Double lhpvLorryHire) {
		this.lhpvLorryHire = lhpvLorryHire;
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
		result = prime * result + ((lhpvId == null) ? 0 : lhpvId.hashCode());
		result = prime * result + ((tripSheetId == null) ? 0 : tripSheetId.hashCode());
		result = prime * result + ((tripSheetToLhpvDetailsId == null) ? 0 : tripSheetToLhpvDetailsId.hashCode());
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
		TripSheetToLhpvDetails other = (TripSheetToLhpvDetails) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (lhpvId == null) {
			if (other.lhpvId != null)
				return false;
		} else if (!lhpvId.equals(other.lhpvId))
			return false;
		if (tripSheetId == null) {
			if (other.tripSheetId != null)
				return false;
		} else if (!tripSheetId.equals(other.tripSheetId))
			return false;
		if (tripSheetToLhpvDetailsId == null) {
			if (other.tripSheetToLhpvDetailsId != null)
				return false;
		} else if (!tripSheetToLhpvDetailsId.equals(other.tripSheetToLhpvDetailsId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripSheetToLhpvDetails [tripSheetToLhpvDetailsId=");
		builder.append(tripSheetToLhpvDetailsId);
		builder.append(", tripSheetId=");
		builder.append(tripSheetId);
		builder.append(", lhpvId=");
		builder.append(lhpvId);
		builder.append(", lhpvNumber=");
		builder.append(lhpvNumber);
		builder.append(", lhpvDate=");
		builder.append(lhpvDate);
		builder.append(", lhpvAdvance=");
		builder.append(lhpvAdvance);
		builder.append(", lhpvLorryHire=");
		builder.append(lhpvLorryHire);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
