/**
 * 
 */
package org.fleetopgroup.persistence.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author fleetop
 *
 */
@Entity
@Table(name = "Issues")
public class Issues implements Serializable {
	private static final long serialVersionUID = 1L;

	/** The value for the ISSUES_Id field */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ISSUES_ID")
	private long ISSUES_ID;
	
	@Column(name = "ISSUES_NUMBER")
	private long ISSUES_NUMBER;

	/** The value for the VEHICLE ID field */
	@Column(name = "ISSUES_VID", nullable = true)
	private Integer ISSUES_VID;
	
	/** The value for the Vehicle Odometer and editable field */
	@Column(name = "ISSUES_ODOMETER", nullable = true)
	private Integer ISSUES_ODOMETER;

	/** The value for the DRIVER_ID field */
	@Column(name = "ISSUES_DRIVER_ID", nullable = true)
	private Integer ISSUES_DRIVER_ID;

	/** The value for the BRANCH_ID field */
	@Column(name = "ISSUES_BRANCH_ID", nullable = true)
	private Integer ISSUES_BRANCH_ID;

	/** The value for the DEPARTNMENT_ID field */
	@Column(name = "ISSUES_DEPARTNMENT_ID", nullable = true)
	private Integer ISSUES_DEPARTNMENT_ID;

	/** The value for the ISSUES_REPORTED_ON DATE field */
	@Basic(optional = false)
	@Column(name = "ISSUES_REPORTED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ISSUES_REPORTED_DATE;

	/** The value for the ISSUES Summary name field */
	@Column(name = "ISSUES_SUMMARY", nullable = false, length = 150)
	private String ISSUES_SUMMARY;

	/** The value for the ISSUES Description name field */
	@Column(name = "ISSUES_DESCRIPTION", nullable = true, length = 1500)
	private String ISSUES_DESCRIPTION;
	
	@Column(name = "ISSUES_LABELS_ID", nullable = true)
	private short ISSUES_LABELS_ID;
	
	@Column(name = "ISSUES_REPORTED_BY_ID")
	private Long ISSUES_REPORTED_BY_ID;

	/** The value for the ISSUES Assign to one user name field */
	@Column(name = "ISSUES_ASSIGN_TO", nullable = true, length = 250)
	private String ISSUES_ASSIGN_TO;
	
	/** The value for the ISSUES Assign to one user name field */
	@Column(name = "ISSUES_ASSIGN_TO_NAME", nullable = true, length = 250)
	private String ISSUES_ASSIGN_TO_NAME;
	
	@Column(name = "CUSTOMER_NAME", nullable = true, length = 250)
	private String CUSTOMER_NAME;


	/** The value for the ISSUES Multiple Task value field */
	
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, mappedBy = "ISSUES")
	private Set<IssuesTasks> ISSUESTASK;

	/** The value for the ISSUES to create workOrder name one user name field */
	@Column(name = "ISSUES_WORKORDER_ID")
	private Long ISSUES_WORKORDER_ID;

	/** The value for the ISSUES to create workOrder name one user name field */
	@Basic(optional = false)
	@Column(name = "ISSUES_WORKORDER_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ISSUES_WORKORDER_DATE;

	@Column(name = "ISSUES_STATUS_ID", nullable = false)
	private short ISSUES_STATUS_ID;
	
	@Column(name = "ISSUE_TYPE_ID")
	private short ISSUE_TYPE_ID;
	
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer	COMPANY_ID;
	
	@Column(name = "VEHICLE_GROUP_ID", nullable = false)
	private long VEHICLE_GROUP_ID;
	
	@Column(name = "WORKORDERS_ID")
	private Long WORKORDERS_ID;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;
	
	@Column(name = "CREATEDBYID", updatable = false)
	private Long CREATEDBYID;

	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;
	
	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "CREATED_DATE", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date CREATED_DATE;

	/** The value for the ISSUES_Due DATE field */
	@Basic(optional = false)
	@Column(name = "LASTUPDATED_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date LASTUPDATED_DATE;
	
	@Column(name = "GPS_ODOMETER")
	private Double GPS_ODOMETER;
	
	@Column(name = "SE_ID")
	private Long SE_ID;
	
	@Column(name = "ISSUES_SE_ID")
	private Long ISSUES_SE_ID;

	/** The value for the ISSUES to create workOrder name one user name field */
	@Basic(optional = true)
	@Column(name = "ISSUES_SE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ISSUES_SE_DATE;
	
	@Column(name = "vehicleCurrentOdometer", length = 10)
	private Integer vehicleCurrentOdometer;
	
	@Column(name = "companyReference", length = 250)
	private String companyReference;
	
	@Column(name = "categoryId")
	private Integer categoryId;
	
	@Column(name = "routeID")
	private Integer routeID;
	
	@Column(name = "dealerServiceEntriesId")
	private Long dealerServiceEntriesId;
	
	@Column(name = "dealerServiceEntriesCreatedOn")
	private Date dealerServiceEntriesCreatedOn;
	
	
	@Column(name = "remark", nullable = true, length = 1500)
	private String remark;
	
	@Column(name = "location", nullable = true)
	private String location;
	
	@Column(name = "issueLP_ID", nullable = true)
	private Long issueLP_ID;


	public Integer getRouteID() {
		return routeID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public Issues() {
		super();
	}

	public Issues(long iSSUES_ID, long iSSUES_NUMBER, Integer iSSUES_VID,
			String iSSUES_VEHICLE_GROUP, Integer iSSUES_ODOMETER, Integer iSSUES_DRIVER_ID, String iSSUES_DRIVER_NAME,
			Integer iSSUES_BRANCH_ID, String iSSUES_BRANCH_NAME, Integer iSSUES_DEPARTNMENT_ID,
			String iSSUES_DEPARTNMENT_NAME, Date iSSUES_REPORTED_DATE, String iSSUES_SUMMARY, String iSSUES_DESCRIPTION,String CUSTOMER_NAME,
			String iSSUES_REPORTED_BY, String iSSUES_ASSIGN_TO, String iSSUES_ASSIGN_TO_NAME,
			Set<IssuesTasks> iSSUESTASK, String iSSUES_STATUS,
			Date cREATED_DATE, Date lASTUPDATED_DATE, Integer	COMPANY_ID, long VEHICLE_GROUP_ID, Integer vehicleCurrentOdometer) {
		super();
		this.ISSUES_ID = iSSUES_ID;
		this.ISSUES_NUMBER = iSSUES_NUMBER;
		this.ISSUES_VID = iSSUES_VID;
		this.ISSUES_ODOMETER = iSSUES_ODOMETER;
		this.ISSUES_DRIVER_ID = iSSUES_DRIVER_ID;
		this.ISSUES_BRANCH_ID = iSSUES_BRANCH_ID;
		this.ISSUES_DEPARTNMENT_ID = iSSUES_DEPARTNMENT_ID;
		this.ISSUES_REPORTED_DATE = iSSUES_REPORTED_DATE;
		this.ISSUES_SUMMARY = iSSUES_SUMMARY;
		this.ISSUES_DESCRIPTION = iSSUES_DESCRIPTION;
		this.ISSUES_ASSIGN_TO = iSSUES_ASSIGN_TO;
		this.ISSUES_ASSIGN_TO_NAME = iSSUES_ASSIGN_TO_NAME;
		this.CUSTOMER_NAME = CUSTOMER_NAME;
		this.ISSUESTASK = iSSUESTASK;
		this.COMPANY_ID	= COMPANY_ID;
		this.VEHICLE_GROUP_ID = VEHICLE_GROUP_ID;
		this.CREATED_DATE = cREATED_DATE;
		this.LASTUPDATED_DATE = lASTUPDATED_DATE;
		this.vehicleCurrentOdometer = vehicleCurrentOdometer;
	}

	/**
	 * @return the iSSUES_ID
	 */
	public long getISSUES_ID() {
		return ISSUES_ID;
	}

	/**
	 * @param iSSUES_ID
	 *            the iSSUES_ID to set
	 */
	public void setISSUES_ID(long iSSUES_ID) {
		ISSUES_ID = iSSUES_ID;
	}

	public long getISSUES_NUMBER() {
		return ISSUES_NUMBER;
	}

	public void setISSUES_NUMBER(long iSSUES_NUMBER) {
		ISSUES_NUMBER = iSSUES_NUMBER;
	}

	/**
	 * @return the iSSUES_VID
	 */
	public Integer getISSUES_VID() {
		return ISSUES_VID;
	}

	/**
	 * @param iSSUES_VID
	 *            the iSSUES_VID to set
	 */
	public void setISSUES_VID(Integer iSSUES_VID) {
		ISSUES_VID = iSSUES_VID;
	}

	/**
	 * @return the iSSUES_ODOMETER
	 */
	public Integer getISSUES_ODOMETER() {
		return ISSUES_ODOMETER;
	}

	/**
	 * @param iSSUES_ODOMETER
	 *            the iSSUES_ODOMETER to set
	 */
	public void setISSUES_ODOMETER(Integer iSSUES_ODOMETER) {
		ISSUES_ODOMETER = iSSUES_ODOMETER;
	}

	/**
	 * @return the iSSUES_DRIVER_ID
	 */
	public Integer getISSUES_DRIVER_ID() {
		return ISSUES_DRIVER_ID;
	}

	/**
	 * @param iSSUES_DRIVER_ID
	 *            the iSSUES_DRIVER_ID to set
	 */
	public void setISSUES_DRIVER_ID(Integer iSSUES_DRIVER_ID) {
		ISSUES_DRIVER_ID = iSSUES_DRIVER_ID;
	}

	

	/**
	 * @return the iSSUES_BRANCH_ID
	 */
	public Integer getISSUES_BRANCH_ID() {
		return ISSUES_BRANCH_ID;
	}

	/**
	 * @param iSSUES_BRANCH_ID
	 *            the iSSUES_BRANCH_ID to set
	 */
	public void setISSUES_BRANCH_ID(Integer iSSUES_BRANCH_ID) {
		ISSUES_BRANCH_ID = iSSUES_BRANCH_ID;
	}

	
	/**
	 * @return the iSSUES_REPORTED_DATE
	 */
	public Date getISSUES_REPORTED_DATE() {
		return ISSUES_REPORTED_DATE;
	}

	/**
	 * @param iSSUES_REPORTED_DATE
	 *            the iSSUES_REPORTED_DATE to set
	 */
	public void setISSUES_REPORTED_DATE(Date iSSUES_REPORTED_DATE) {
		ISSUES_REPORTED_DATE = iSSUES_REPORTED_DATE;
	}

	/**
	 * @return the iSSUES_SUMMARY
	 */
	public String getISSUES_SUMMARY() {
		return ISSUES_SUMMARY;
	}

	/**
	 * @param iSSUES_SUMMARY
	 *            the iSSUES_SUMMARY to set
	 */
	public void setISSUES_SUMMARY(String iSSUES_SUMMARY) {
		ISSUES_SUMMARY = iSSUES_SUMMARY;
	}

	/**
	 * @return the iSSUES_DESCRIPTION
	 */
	public String getISSUES_DESCRIPTION() {
		return ISSUES_DESCRIPTION;
	}

	/**
	 * @param iSSUES_DESCRIPTION
	 *            the iSSUES_DESCRIPTION to set
	 */
	public void setISSUES_DESCRIPTION(String iSSUES_DESCRIPTION) {
		ISSUES_DESCRIPTION = iSSUES_DESCRIPTION;
	}
	
	/**
	 * @return the iSSUES_ASSIGN_TO_EMAIL
	 */
	public String getISSUES_ASSIGN_TO() {
		return ISSUES_ASSIGN_TO;
	}

	/**
	 * @param iSSUES_ASSIGN_TO_EMAIL
	 *            the iSSUES_ASSIGN_TO_EMAIL to set
	 */
	public void setISSUES_ASSIGN_TO(String iSSUES_ASSIGN_TO_EMAIL) {
		ISSUES_ASSIGN_TO = iSSUES_ASSIGN_TO_EMAIL;
	}

	/**
	 * @return the iSSUES_ASSIGN_TO_NAME
	 */
	public String getISSUES_ASSIGN_TO_NAME() {
		return ISSUES_ASSIGN_TO_NAME;
	}

	/**
	 * @param iSSUES_ASSIGN_TO_NAME
	 *            the iSSUES_ASSIGN_TO_NAME to set
	 */
	public void setISSUES_ASSIGN_TO_NAME(String iSSUES_ASSIGN_TO_NAME) {
		ISSUES_ASSIGN_TO_NAME = iSSUES_ASSIGN_TO_NAME;
	}
	
	/**
	 * @return the CUSTOMER_NAME
	 */
	public String getCUSTOMER_NAME() {
		return CUSTOMER_NAME;
	}
	/**
	 * @param CUSTOMER_NAME
	 *            the CUSTOMER_NAME to set
	 */
	public void setCUSTOMER_NAME(String cUSTOMER_NAME) {
		CUSTOMER_NAME = cUSTOMER_NAME;
	}

	/**
	 * @return the iSSUESTASK
	 */
	public Set<IssuesTasks> getISSUESTASK() {
		return ISSUESTASK;
	}

	/**
	 * @param iSSUESTASK
	 *            the iSSUESTASK to set
	 */
	public void setISSUESTASK(Set<IssuesTasks> iSSUESTASK) {
		ISSUESTASK = iSSUESTASK;
	}

	/**
	 * @return the cREATED_DATE
	 */
	public Date getCREATED_DATE() {
		return CREATED_DATE;
	}

	/**
	 * @param cREATED_DATE
	 *            the cREATED_DATE to set
	 */
	public void setCREATED_DATE(Date cREATED_DATE) {
		CREATED_DATE = cREATED_DATE;
	}

	/**
	 * @return the lASTUPDATED_DATE
	 */
	public Date getLASTUPDATED_DATE() {
		return LASTUPDATED_DATE;
	}

	/**
	 * @param lASTUPDATED_DATE
	 *            the lASTUPDATED_DATE to set
	 */
	public void setLASTUPDATED_DATE(Date lASTUPDATED_DATE) {
		LASTUPDATED_DATE = lASTUPDATED_DATE;
	}

	

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	public long getVEHICLE_GROUP_ID() {
		return VEHICLE_GROUP_ID;
	}

	public void setVEHICLE_GROUP_ID(long vEHICLE_GROUP_ID) {
		VEHICLE_GROUP_ID = vEHICLE_GROUP_ID;
	}

	

	/**
	 * @return the iSSUES_DEPARTNMENT_ID
	 */
	public Integer getISSUES_DEPARTNMENT_ID() {
		return ISSUES_DEPARTNMENT_ID;
	}

	/**
	 * @param iSSUES_DEPARTNMENT_ID
	 *            the iSSUES_DEPARTNMENT_ID to set
	 */
	public void setISSUES_DEPARTNMENT_ID(Integer iSSUES_DEPARTNMENT_ID) {
		ISSUES_DEPARTNMENT_ID = iSSUES_DEPARTNMENT_ID;
	}

	

	/**
	 * @return the iSSUES_WORKORDER_ID
	 */
	public Long getISSUES_WORKORDER_ID() {
		return ISSUES_WORKORDER_ID;
	}

	/**
	 * @param iSSUES_WORKORDER_ID
	 *            the iSSUES_WORKORDER_ID to set
	 */
	public void setISSUES_WORKORDER_ID(Long iSSUES_WORKORDER_ID) {
		ISSUES_WORKORDER_ID = iSSUES_WORKORDER_ID;
	}

	/**
	 * @return the iSSUES_WORKORDER_DATE
	 */
	public Date getISSUES_WORKORDER_DATE() {
		return ISSUES_WORKORDER_DATE;
	}

	/**
	 * @param iSSUES_WORKORDER_DATE
	 *            the iSSUES_WORKORDER_DATE to set
	 */
	public void setISSUES_WORKORDER_DATE(Date iSSUES_WORKORDER_DATE) {
		ISSUES_WORKORDER_DATE = iSSUES_WORKORDER_DATE;
	}

	public short getISSUE_TYPE_ID() {
		return ISSUE_TYPE_ID;
	}

	public void setISSUE_TYPE_ID(short iSSUE_TYPE_ID) {
		ISSUE_TYPE_ID = iSSUE_TYPE_ID;
	}

	/**
	 * @return the iSSUES_LABELS_ID
	 */
	public short getISSUES_LABELS_ID() {
		return ISSUES_LABELS_ID;
	}

	/**
	 * @param iSSUES_LABELS_ID the iSSUES_LABELS_ID to set
	 */
	public void setISSUES_LABELS_ID(short iSSUES_LABELS_ID) {
		ISSUES_LABELS_ID = iSSUES_LABELS_ID;
	}

	/**
	 * @return the iSSUES_REPORTED_BY_ID
	 */
	public Long getISSUES_REPORTED_BY_ID() {
		return ISSUES_REPORTED_BY_ID;
	}

	/**
	 * @param iSSUES_REPORTED_BY_ID the iSSUES_REPORTED_BY_ID to set
	 */
	public void setISSUES_REPORTED_BY_ID(Long iSSUES_REPORTED_BY_ID) {
		ISSUES_REPORTED_BY_ID = iSSUES_REPORTED_BY_ID;
	}


	/**
	 * @return the iSSUES_STATUS_ID
	 */
	public short getISSUES_STATUS_ID() {
		return ISSUES_STATUS_ID;
	}

	/**
	 * @param iSSUES_STATUS_ID the iSSUES_STATUS_ID to set
	 */
	public void setISSUES_STATUS_ID(short iSSUES_STATUS_ID) {
		ISSUES_STATUS_ID = iSSUES_STATUS_ID;
	}

	/**
	 * @return the cREATEDBYID
	 */
	public Long getCREATEDBYID() {
		return CREATEDBYID;
	}

	/**
	 * @param cREATEDBYID the cREATEDBYID to set
	 */
	public void setCREATEDBYID(Long cREATEDBYID) {
		CREATEDBYID = cREATEDBYID;
	}

	/**
	 * @return the lASTMODIFIEDBYID
	 */
	public Long getLASTMODIFIEDBYID() {
		return LASTMODIFIEDBYID;
	}

	/**
	 * @param lASTMODIFIEDBYID the lASTMODIFIEDBYID to set
	 */
	public void setLASTMODIFIEDBYID(Long lASTMODIFIEDBYID) {
		LASTMODIFIEDBYID = lASTMODIFIEDBYID;
	}

	/**
	 * @return the wORKORDERS_ID
	 */
	public Long getWORKORDERS_ID() {
		return WORKORDERS_ID;
	}

	/**
	 * @param wORKORDERS_ID the wORKORDERS_ID to set
	 */
	public void setWORKORDERS_ID(Long wORKORDERS_ID) {
		WORKORDERS_ID = wORKORDERS_ID;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Double getGPS_ODOMETER() {
		return GPS_ODOMETER;
	}

	public void setGPS_ODOMETER(Double gPS_ODOMETER) {
		GPS_ODOMETER = gPS_ODOMETER;
	}

	public Long getSE_ID() {
		return SE_ID;
	}

	public void setSE_ID(Long sE_ID) {
		SE_ID = sE_ID;
	}

	public Long getISSUES_SE_ID() {
		return ISSUES_SE_ID;
	}

	public void setISSUES_SE_ID(Long iSSUES_SE_ID) {
		ISSUES_SE_ID = iSSUES_SE_ID;
	}

	public Date getISSUES_SE_DATE() {
		return ISSUES_SE_DATE;
	}

	public void setISSUES_SE_DATE(Date iSSUES_SE_DATE) {
		ISSUES_SE_DATE = iSSUES_SE_DATE;
	}
	
	public Integer getVehicleCurrentOdometer() {
		return vehicleCurrentOdometer;
	}

	public void setVehicleCurrentOdometer(Integer vehicleCurrentOdometer) {
		this.vehicleCurrentOdometer = vehicleCurrentOdometer;
	}

	public String getCompanyReference() {
		return companyReference;
	}

	public void setCompanyReference(String companyReference) {
		this.companyReference = companyReference;
	}
	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	
	public Long getIssueLP_ID() {
		return issueLP_ID;
	}

	public void setIssueLP_ID(Long issueLP_ID) {
		this.issueLP_ID = issueLP_ID;
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
		result = prime * result + ((ISSUES_DRIVER_ID == null) ? 0 : ISSUES_DRIVER_ID.hashCode());
		result = prime * result + (int) (ISSUES_ID ^ (ISSUES_ID >>> 32));
		result = prime * result + ((ISSUES_VID == null) ? 0 : ISSUES_VID.hashCode());
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
		Issues other = (Issues) obj;
		if (ISSUES_DRIVER_ID == null) {
			if (other.ISSUES_DRIVER_ID != null)
				return false;
		} else if (!ISSUES_DRIVER_ID.equals(other.ISSUES_DRIVER_ID))
			return false;
		if (ISSUES_ID != other.ISSUES_ID)
			return false;
		if (ISSUES_VID == null) {
			if (other.ISSUES_VID != null)
				return false;
		} else if (!ISSUES_VID.equals(other.ISSUES_VID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Issues [ISSUES_ID=");
		builder.append(ISSUES_ID);
		builder.append(", ISSUES_NUMBER=");
		builder.append(ISSUES_NUMBER);
		builder.append(", ISSUES_VID=");
		builder.append(ISSUES_VID);
		builder.append(", ISSUES_ODOMETER=");
		builder.append(ISSUES_ODOMETER);
		builder.append(", ISSUES_DRIVER_ID=");
		builder.append(ISSUES_DRIVER_ID);
		builder.append(", ISSUES_BRANCH_ID=");
		builder.append(ISSUES_BRANCH_ID);
		builder.append(", ISSUES_DEPARTNMENT_ID=");
		builder.append(ISSUES_DEPARTNMENT_ID);
		builder.append(", ISSUES_REPORTED_DATE=");
		builder.append(ISSUES_REPORTED_DATE);
		builder.append(", ISSUES_SUMMARY=");
		builder.append(ISSUES_SUMMARY);
		builder.append(", ISSUES_DESCRIPTION=");
		builder.append(ISSUES_DESCRIPTION);
		builder.append(", ISSUES_LABELS_ID=");
		builder.append(ISSUES_LABELS_ID);
		builder.append(", ISSUES_REPORTED_BY_ID=");
		builder.append(ISSUES_REPORTED_BY_ID);
		builder.append(", ISSUES_ASSIGN_TO=");
		builder.append(ISSUES_ASSIGN_TO);
		builder.append(", ISSUES_ASSIGN_TO_NAME=");
		builder.append(ISSUES_ASSIGN_TO_NAME);
		builder.append(", CUSTOMER_NAME=");
		builder.append(CUSTOMER_NAME);
		builder.append(", ISSUESTASK=");
		builder.append(ISSUESTASK);
		builder.append(", ISSUES_WORKORDER_ID=");
		builder.append(ISSUES_WORKORDER_ID);
		builder.append(", ISSUES_WORKORDER_DATE=");
		builder.append(ISSUES_WORKORDER_DATE);
		builder.append(", ISSUES_STATUS_ID=");
		builder.append(ISSUES_STATUS_ID);
		builder.append(", ISSUE_TYPE_ID=");
		builder.append(ISSUE_TYPE_ID);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append(", VEHICLE_GROUP_ID=");
		builder.append(VEHICLE_GROUP_ID);
		builder.append(", WORKORDERS_ID=");
		builder.append(WORKORDERS_ID);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", CREATEDBYID=");
		builder.append(CREATEDBYID);
		builder.append(", LASTMODIFIEDBYID=");
		builder.append(LASTMODIFIEDBYID);
		builder.append(", CREATED_DATE=");
		builder.append(CREATED_DATE);
		builder.append(", LASTUPDATED_DATE=");
		builder.append(LASTUPDATED_DATE);
		builder.append(", GPS_ODOMETER=");
		builder.append(GPS_ODOMETER);
		builder.append(", SE_ID=");
		builder.append(SE_ID);
		builder.append(", ISSUES_SE_ID=");
		builder.append(ISSUES_SE_ID);
		builder.append(", ISSUES_SE_DATE=");
		builder.append(ISSUES_SE_DATE);
		builder.append(", vehicleCurrentOdometer=");
		builder.append(vehicleCurrentOdometer);
		builder.append(", companyReference=");
		builder.append(companyReference);
		builder.append("]");
		return builder.toString();
	}

	

}
