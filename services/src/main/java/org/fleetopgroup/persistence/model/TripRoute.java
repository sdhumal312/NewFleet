package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "triproute")
public class TripRoute implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "routeID")
	private Integer routeID;
	
	@Column(name = "routeNumber", nullable = false)
	private Integer routeNumber;

	@Column(name = "routeType")
	private Integer routeType;
	
	@Column(name = "mainRouteId")
	private Integer mainRouteId;

	@Column(name = "routeName", unique = false, nullable = false, length = 200)
	private String routeName;

	@Column(name = "routeNo", length = 25)
	private String routeNo;

	@Column(name = "routeTimeFrom", length = 8)
	private String routeTimeFrom;

	@Column(name = "routeTimeTo", length = 8)
	private String routeTimeTo;

	@Column(name = "routrAttendance")
	private Double routrAttendance;

	@Column(name = "routeTotalHour")
	private Double routeTotalHour;

	@Column(name = "routeTotalLiter")
	private Double routeTotalLiter;

	@Column(name = "routeApproximateKM")
	private Integer routeApproximateKM;

	@Column(name = "routeRemarks", length = 500)
	private String routeRemarks;

	@Column(name = "createdBy", length = 150)
	private String createdBy;

	@Column(name = "createdById", nullable= false)
	private Long createdById;

	@Column(name = "lastModifiedBy", length = 150)
	private String lastModifiedBy;

	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "lastupdated", nullable = true, updatable = true)
	private Date lastupdated;

	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "vehicleGroupId", nullable = false)
	private long vehicleGroupId;

	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@OneToMany(mappedBy = "triproute", fetch = FetchType.EAGER)
	private Set<TripRoutefixedExpense> triproutefixedexpense;

	@OneToMany(mappedBy = "triproute", fetch = FetchType.EAGER)
	private Set<TripRoutefixedIncome> triproutefixedincome;

	public TripRoute() {
		super();
	}

	public TripRoute(Integer routeNumber,String routeName, String routeNo, Integer routeApproximateKM, String routeRemarks,
			String createdBy, String lastModifiedBy, Date created, Date lastupdated,
			Set<TripRoutefixedExpense> triproutefixedexpense) {
		super();
		this.routeNumber = routeNumber;
		this.routeName = routeName;
		this.routeNo = routeNo;
		this.routeApproximateKM = routeApproximateKM;
		this.routeRemarks = routeRemarks;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.created = created;
		this.lastupdated = lastupdated;
		this.triproutefixedexpense = triproutefixedexpense;
	}

	/**
	 * @return the routeType
	 */
	public Integer getRouteType() {
		return routeType;
	}

	/**
	 * @param routeType
	 *            the routeType to set
	 */
	public void setRouteType(Integer routeType) {
		this.routeType = routeType;
	}

	/**
	 * @return the markForDelete
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param markForDelete
	 *            the markForDelete to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/**
	 * @return the triproutefixedincome
	 */
	public Set<TripRoutefixedIncome> getTriproutefixedincome() {
		return triproutefixedincome;
	}

	/**
	 * @param triproutefixedincome
	 *            the triproutefixedincome to set
	 */
	public void setTriproutefixedincome(Set<TripRoutefixedIncome> triproutefixedincome) {
		this.triproutefixedincome = triproutefixedincome;
	}
	/**
	 * @return the routeID
	 */
	public Integer getRouteID() {
		return routeID;
	}

	/**
	 * @param routeID
	 *            the routeID to set
	 */
	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public Integer getRouteNumber() {
		return routeNumber;
	}

	public void setRouteNumber(Integer routeNumber) {
		this.routeNumber = routeNumber;
	}

	/**
	 * @return the routeName
	 */
	public String getRouteName() {
		return routeName;
	}

	/**
	 * @param routeName
	 *            the routeName to set
	 */
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	/**
	 * @return the routeNo
	 */
	public String getRouteNo() {
		return routeNo;
	}

	/**
	 * @param routeNo
	 *            the routeNo to set
	 */
	public void setRouteNo(String routeNo) {
		this.routeNo = routeNo;
	}

	/**
	 * @return the mainRouteId
	 */
	public Integer getMainRouteId() {
		return mainRouteId;
	}

	/**
	 * @param mainRouteId the mainRouteId to set
	 */
	public void setMainRouteId(Integer mainRouteId) {
		this.mainRouteId = mainRouteId;
	}

	/**
	 * @return the routeApproximateKM
	 */
	public Integer getRouteApproximateKM() {
		return routeApproximateKM;
	}

	/**
	 * @param routeApproximateKM
	 *            the routeApproximateKM to set
	 */
	public void setRouteApproximateKM(Integer routeApproximateKM) {
		this.routeApproximateKM = routeApproximateKM;
	}

	/**
	 * @return the routeRemarks
	 */
	public String getRouteRemarks() {
		return routeRemarks;
	}

	/**
	 * @param routeRemarks
	 *            the routeRemarks to set
	 */
	public void setRouteRemarks(String routeRemarks) {
		this.routeRemarks = routeRemarks;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy
	 *            the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy
	 *            the lastModifiedBy to set
	 */
	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the created
	 */
	public Date getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public Date getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the routeTimeFrom
	 */
	public String getRouteTimeFrom() {
		return routeTimeFrom;
	}

	/**
	 * @param routeTimeFrom
	 *            the routeTimeFrom to set
	 */
	public void setRouteTimeFrom(String routeTimeFrom) {
		this.routeTimeFrom = routeTimeFrom;
	}

	/**
	 * @return the routeTimeTo
	 */
	public String getRouteTimeTo() {
		return routeTimeTo;
	}

	/**
	 * @param routeTimeTo
	 *            the routeTimeTo to set
	 */
	public void setRouteTimeTo(String routeTimeTo) {
		this.routeTimeTo = routeTimeTo;
	}

	/**
	 * @return the routrAttendance
	 */
	public Double getRoutrAttendance() {
		return routrAttendance;
	}

	/**
	 * @param routrAttendance
	 *            the routrAttendance to set
	 */
	public void setRoutrAttendance(Double routrAttendance) {
		this.routrAttendance = routrAttendance;
	}

	/**
	 * @return the routeTotalHour
	 */
	public Double getRouteTotalHour() {
		return routeTotalHour;
	}

	/**
	 * @param routeTotalHour
	 *            the routeTotalHour to set
	 */
	public void setRouteTotalHour(Double routeTotalHour) {
		this.routeTotalHour = routeTotalHour;
	}

	/**
	 * @return the triproutefixedexpense
	 */
	public Set<TripRoutefixedExpense> getTriproutefixedexpense() {
		return triproutefixedexpense;
	}

	/**
	 * @param triproutefixedexpense
	 *            the triproutefixedexpense to set
	 */
	public void setTriproutefixedexpense(Set<TripRoutefixedExpense> triproutefixedexpense) {
		this.triproutefixedexpense = triproutefixedexpense;
	}

	/**
	 * @return the routeTotalLiter
	 */
	public Double getRouteTotalLiter() {
		return routeTotalLiter;
	}

	/**
	 * @param routeTotalLiter
	 *            the routeTotalLiter to set
	 */
	public void setRouteTotalLiter(Double routeTotalLiter) {
		this.routeTotalLiter = routeTotalLiter;
	}

	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId
	 *            the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public long getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((routeName == null) ? 0 : routeName.hashCode());
		result = prime * result + ((routeNo == null) ? 0 : routeNo.hashCode());
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
		TripRoute other = (TripRoute) obj;
		if (routeName == null) {
			if (other.routeName != null)
				return false;
		} else if (!routeName.equals(other.routeName))
			return false;
		if (routeNo == null) {
			if (other.routeNo != null)
				return false;
		} else if (!routeNo.equals(other.routeNo))
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripRoute [routeID=");
		builder.append(routeID);
		builder.append(", routeNumber=");
		builder.append(routeNumber);
		builder.append(", routeType=");
		builder.append(routeType);
		builder.append(", routeName=");
		builder.append(routeName);
		builder.append(", routeNo=");
		builder.append(routeNo);
		builder.append(", routeTimeFrom=");
		builder.append(routeTimeFrom);
		builder.append(", routeTimeTo=");
		builder.append(routeTimeTo);
		builder.append(", routrAttendance=");
		builder.append(routrAttendance);
		builder.append(", routeTotalHour=");
		builder.append(routeTotalHour);
		builder.append(", routeTotalLiter=");
		builder.append(routeTotalLiter);
		builder.append(", routeApproximateKM=");
		builder.append(routeApproximateKM);
		builder.append(", routeRemarks=");
		builder.append(routeRemarks);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", vehicleGroupId=");
		builder.append(vehicleGroupId);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", triproutefixedexpense=");
		builder.append(triproutefixedexpense);
		builder.append(", triproutefixedincome=");
		builder.append(triproutefixedincome);
		builder.append("]");
		return builder.toString();
	}

}