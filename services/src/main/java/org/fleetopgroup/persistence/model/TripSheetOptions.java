package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "TripSheetOptions")
public class TripSheetOptions implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripsheetoptionsId")
	private Long tripsheetoptionsId;
	
	@Column(name = "tripsheetextraname", length = 200)
	 private String tripsheetextraname;
	
	@Column(name = "tripsheetextradescription", length = 200)
	 private String tripsheetextradescription;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	
	/** The value for the created by email field */
	@Column(name = "createdById", nullable = false, updatable = false)
	private Long createdById;
	
	@Column(name = "CREATEDBY", length = 200)
	private String CREATEDBY;

	/** The value for the lastUpdated By email field */
	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;
	
	
	@Column(name = "LASTMODIFIEDBY", length = 200)
	private String LASTMODIFIEDBY;

	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	/** The value for the created DATE field */
	@Basic(optional = false)
	@Column(name = "created", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date created;

	/** The value for the lastUpdated DATE field */
	@Basic(optional = false)
	@Column(name = "lastupdated")
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdated;
	
	@Column(name = "ipAddress")
	private String ipAddress;
	
	@Column(name = "ownTripsheetextraname", nullable = false)
	private boolean ownTripsheetextraname;
	
	
	
	@ManyToOne
	@JoinColumn(name = "tripSheetID")
	private TripSheet tripsheet;
	
	
	
	public TripSheet getTripsheet() {
		return tripsheet;
	}

	public void setTripsheet(TripSheet tripsheet) {
		this.tripsheet = tripsheet;
	}

	

	public boolean isOwnTripsheetextraname() {
		return ownTripsheetextraname;
	}

	public void setOwnTripsheetextraname(boolean ownTripsheetextraname) {
		this.ownTripsheetextraname = ownTripsheetextraname;
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

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public Long getTripsheetoptionsId() {
		return tripsheetoptionsId;
	}

	public void setTripsheetoptionsId(Long tripsheetoptionsId) {
		this.tripsheetoptionsId = tripsheetoptionsId;
	}

	public TripSheetOptions() {
		super();
	}
	
	public TripSheetOptions(Long tripsheetoptionsId, 
			String tripsheetextraname,  String tripsheetextradescription,
			Date created, Date lastupdated, String CREATEDBY, String LASTMODIFIEDBY, Long createdById, Long lastModifiedById
			) {
		super();
		this.tripsheetoptionsId = tripsheetoptionsId;
		this.tripsheetextraname = tripsheetextraname;
		this.tripsheetextradescription = tripsheetextradescription;
		this.created = created;
		this.lastupdated = lastupdated;
		this.CREATEDBY= CREATEDBY;
		this.LASTMODIFIEDBY = LASTMODIFIEDBY;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripSheetOptions [tripsheetoptionsId=");
		builder.append(tripsheetoptionsId);
		builder.append(", tripsheetextraname=");
		builder.append(tripsheetextraname);
		builder.append(", tripsheetextradescription=");
		builder.append(tripsheetextradescription);
		builder.append(", CREATEDBY=");
		builder.append(CREATEDBY);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", LASTMODIFIEDBY=");
		builder.append(LASTMODIFIEDBY);
		builder.append(", lastModifiedById=");
		builder.append(lastModifiedById);		
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", ipAddress=");
		builder.append(ipAddress);
		builder.append(", ownTripsheetextraname=");
		builder.append(ownTripsheetextraname);
		
		builder.append("]");
		return builder.toString();
	}

	

}
