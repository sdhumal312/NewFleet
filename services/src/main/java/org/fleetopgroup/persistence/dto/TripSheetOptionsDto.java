/**
 * 
 */
package org.fleetopgroup.persistence.dto;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */
public class TripSheetOptionsDto {
	
	private Long tripExtraID;
	
	private Long tripsheetoptionsId;

	private String tripsheetextraname;

	private String tripsheetextradescription;

	private String CREATEDBY;

	private String LASTMODIFIEDBY;

	boolean markForDelete;

	private Integer companyId;
	
	
	private String lastupdated;
	
	private Double TripSheetExtraQuantity;
	
	private Double TripSheetExtraQuantityReceived;
	
	private Long createdById;
	
	private java.util.Date created;
	
	
	
	
	
	public Double getTripSheetExtraQuantityReceived() {
		return TripSheetExtraQuantityReceived;
	}

	public void setTripSheetExtraQuantityReceived(Double tripSheetExtraQuantityReceived) {
		TripSheetExtraQuantityReceived = Utility.round( tripSheetExtraQuantityReceived, 2);
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	

	public Long getTripExtraID() {
		return tripExtraID;
	}

	public void setTripExtraID(Long tripExtraID) {
		this.tripExtraID = tripExtraID;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	

	public Double getTripSheetExtraQuantity() {
		return TripSheetExtraQuantity;
	}

	public void setTripSheetExtraQuantity(Double tripSheetExtraQuantity) {
		TripSheetExtraQuantity = Utility.round(tripSheetExtraQuantity, 2) ;
	}

	

	public TripSheetOptionsDto() {
		super();
	}

	public TripSheetOptionsDto(Long tripExpenseID, String tripsheetextraname, Long tripsheetoptionsId, Double TripSheetExtraQuantity, String tripsheetextradescription,
			Long createdById, java.util.Date created) {
		super();
		this.tripExtraID=tripExpenseID;
		this.tripsheetextraname = tripsheetextraname;
		this.tripsheetoptionsId = tripsheetoptionsId;
		this.TripSheetExtraQuantity=TripSheetExtraQuantity;
		this.tripsheetextradescription = tripsheetextradescription;
		this.createdById=createdById;
		this.created = created;
		
	}

	/**
	 * @return the tS_ID
	 */
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripSheetOptionsDto [tripExtraID=");
		builder.append(tripExtraID);
		builder.append(", tripsheetextraname=");
		builder.append(tripsheetextraname);
		builder.append(", tripsheetoptionsId=");
		builder.append(tripsheetoptionsId);
		builder.append(", TripSheetExtraQuantity=");
		builder.append(TripSheetExtraQuantity);
		builder.append(", tripsheetextradescription=");
		builder.append(tripsheetextradescription);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", created=");
		builder.append(created);
		builder.append("]");
		return builder.toString();
	}

	public Long getTripsheetoptionsId() {
		return tripsheetoptionsId;
	}

	public void setTripsheetoptionsId(Long tripsheetoptionsId) {
		this.tripsheetoptionsId = tripsheetoptionsId;
	}

	public String getTripsheetextraname() {
		return tripsheetextraname;
	}

	public void setTripsheetextraname(String tripsheetextraname) {
		this.tripsheetextraname = tripsheetextraname;
	}

	public String getTripsheetextradescription() {
		return tripsheetextradescription;
	}

	public void setTripsheetextradescription(String tripsheetextradescription) {
		this.tripsheetextradescription = tripsheetextradescription;
	}

	public String getCREATEDBY() {
		return CREATEDBY;
	}

	public void setCREATEDBY(String cREATEDBY) {
		CREATEDBY = cREATEDBY;
	}

	public String getLASTMODIFIEDBY() {
		return LASTMODIFIEDBY;
	}

	public void setLASTMODIFIEDBY(String lASTMODIFIEDBY) {
		LASTMODIFIEDBY = lASTMODIFIEDBY;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	

	public String getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}

	public java.util.Date getCreated() {
		return created;
	}

	public void setCreated(java.util.Date created) {
		this.created = created;
	}


}
