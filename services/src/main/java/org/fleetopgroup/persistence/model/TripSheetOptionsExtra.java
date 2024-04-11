package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TripSheetOptionsExtra")
public class TripSheetOptionsExtra implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripExtraID")
	private Long tripExtraID;

	/*@Column(name = "expenseName", length = 150)
	@Deprecated
	private String expenseName;*/
	
	@Column(name = "tripsheetoptionsID", nullable = false)
	private Long tripsheetoptionsID;

	@Column(name = "TripSheetExtraQuantity")
	private Double TripSheetExtraQuantity;
	
		
	@Column(name = "TripSheetExtraDescription", length = 250) String TripSheetExtraDescription;


	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;


	
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long createdById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;


	@ManyToOne
	@JoinColumn(name = "tripSheetID")
	private TripSheet tripsheet;
	

	
	public TripSheetOptionsExtra() {
		super();
	}



	

	
	public TripSheetOptionsExtra(Long tripExtraID, Long tripsheetoptionsID, Double TripSheetExtraQuantity, String TripSheetExtraDescription, Integer companyId,
			Long createdById, Date created, boolean markForDelete, TripSheet tripSheetID) {
		super();
		this.tripExtraID = tripExtraID;
		this.tripsheetoptionsID = tripsheetoptionsID;
		this.TripSheetExtraQuantity = TripSheetExtraQuantity;
		this.TripSheetExtraDescription = TripSheetExtraDescription;
		this.companyId = companyId;
		this.createdById = createdById;
		this.created = created;
		this.markForDelete = markForDelete;
		this.tripsheet = tripSheetID;
	}


	public TripSheetOptionsExtra( Double TripSheetExtraQuantity,  String TripSheetExtraDescription, Integer companyId) {
		super();
		this.TripSheetExtraQuantity = TripSheetExtraQuantity;
		this.TripSheetExtraDescription = TripSheetExtraDescription;
		this.companyId = companyId;
	}

	public TripSheetOptionsExtra( Double TripSheetExtraQuantity,  String TripSheetExtraDescription
			) {
		super();
		this.TripSheetExtraQuantity = TripSheetExtraQuantity;
		this.TripSheetExtraDescription = TripSheetExtraDescription;
	}

	

	

	public Long getTripExtraID() {
		return tripExtraID;
	}


	public void setTripExtraID(Long tripExtraID) {
		this.tripExtraID = tripExtraID;
	}


	public Long getTripsheetoptionsID() {
		return tripsheetoptionsID;
	}


	public void setTripsheetoptionsID(Long tripsheetoptionsID) {
		this.tripsheetoptionsID = tripsheetoptionsID;
	}


	public Double getTripSheetExtraQuantity() {
		return TripSheetExtraQuantity;
	}


	public void setTripSheetExtraQuantity(Double tripSheetExtraQuantity) {
		TripSheetExtraQuantity = tripSheetExtraQuantity;
	}


	public String getTripSheetExtraDescription() {
		return TripSheetExtraDescription;
	}


	public void setTripSheetExtraDescription(String tripSheetExtraDescription) {
		TripSheetExtraDescription = tripSheetExtraDescription;
	}


	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public Long getCreatedById() {
		return createdById;
	}


	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}


	public Date getCreated() {
		return created;
	}


	public void setCreated(Date created) {
		this.created = created;
	}


	public boolean isMarkForDelete() {
		return markForDelete;
	}


	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}


	public TripSheet getTripsheet() {
		return tripsheet;
	}


	public void setTripsheet(TripSheet tripsheet) {
		this.tripsheet = tripsheet;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((TripSheetExtraDescription == null) ? 0 : TripSheetExtraDescription.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TripSheetOptionsExtra other = (TripSheetOptionsExtra) obj;
		if (TripSheetExtraDescription == null) {
			if (other.TripSheetExtraDescription != null)
				return false;
		} else if (!TripSheetExtraDescription.equals(other.TripSheetExtraDescription))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripSheetOptionsExtra [tripExtraID=");
		builder.append(tripExtraID);
		builder.append(", TripSheetExtraQuantity=");
		builder.append(TripSheetExtraQuantity);
		builder.append(", tripsheetoptionsID=");
		builder.append(tripsheetoptionsID);
		builder.append(", TripSheetExtraDescription=");
		builder.append(TripSheetExtraDescription);
		builder.append(", created=");
		builder.append(created);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", tripsheet=");
		builder.append(tripsheet);		
		builder.append("]");
		return builder.toString();
	}

}