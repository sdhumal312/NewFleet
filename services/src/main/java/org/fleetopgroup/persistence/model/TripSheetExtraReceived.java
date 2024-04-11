package org.fleetopgroup.persistence.model;

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
@Table(name = "TripSheetExtraReceived")
public class TripSheetExtraReceived implements Serializable {

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

	@Column(name = "TripSheetExtraQuantityReceived")
	private Double TripSheetExtraQuantityReceived;
	
		
	@Column(name = "TripSheetExtraDescription", length = 250)
	private String TripSheetExtraDescription;


	
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
	

	//construct
	public TripSheetExtraReceived() {
		super();
	}

	
	public TripSheetExtraReceived(Long tripExtraID, Long tripsheetoptionsID, Double TripSheetExtraQuantityReceived, String TripSheetExtraDescription, Integer companyId,
			Long createdById, Date created, boolean markForDelete, TripSheet tripSheetID) {
		super();
		this.tripExtraID = tripExtraID;
		this.tripsheetoptionsID = tripsheetoptionsID;
		this.TripSheetExtraQuantityReceived = TripSheetExtraQuantityReceived;
		this.TripSheetExtraDescription = TripSheetExtraDescription;
		this.companyId = companyId;
		this.createdById = createdById;
		this.created = created;
		this.markForDelete = markForDelete;
		this.tripsheet = tripSheetID;
	}


	public TripSheetExtraReceived( Double TripSheetExtraQuantityReceived,  String TripSheetExtraDescription, Integer companyId) {
		super();
		this.TripSheetExtraQuantityReceived = TripSheetExtraQuantityReceived;
		this.TripSheetExtraDescription = TripSheetExtraDescription;
		this.companyId = companyId;
	}

	public TripSheetExtraReceived( Double TripSheetExtraQuantityReceived,  String TripSheetExtraDescription
			) {
		super();
		this.TripSheetExtraQuantityReceived = TripSheetExtraQuantityReceived;
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

	

	public Double getTripSheetExtraQuantityReceived() {
		return TripSheetExtraQuantityReceived;
	}






	public void setTripSheetExtraQuantityReceived(Double tripSheetExtraQuantityReceived) {
		TripSheetExtraQuantityReceived = tripSheetExtraQuantityReceived;
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
		builder.append(", TripSheetExtraQuantityReceived=");
		builder.append(TripSheetExtraQuantityReceived);
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
