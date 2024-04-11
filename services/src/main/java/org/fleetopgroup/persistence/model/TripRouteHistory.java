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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "TripRouteHistory")
public class TripRouteHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Trip_Route_History_Id")
	private Integer Trip_Route_History_Id;

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

	@Column(name = "routeRemarks", length = 200)
	private String routeRemarks;

	@Column(name = "lastModifiedById")
	private Long lastModifiedById;

	@Column(name = "lastupdated", nullable = true, updatable = true)
	private Date lastupdated;

	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "vehicleGroupId", nullable = false)
	private long vehicleGroupId;

	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@OneToMany(mappedBy = "triproute")
	private Set<TripRoutefixedExpense> triproutefixedexpense;

	@OneToMany(mappedBy = "triproute")
	private Set<TripRoutefixedIncome> triproutefixedincome;

	public Integer getTrip_Route_History_Id() {
		return Trip_Route_History_Id;
	}

	public void setTrip_Route_History_Id(Integer trip_Route_History_Id) {
		Trip_Route_History_Id = trip_Route_History_Id;
	}

	public Integer getRouteID() {
		return routeID;
	}

	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}

	public Integer getRouteNumber() {
		return routeNumber;
	}

	public void setRouteNumber(Integer routeNumber) {
		this.routeNumber = routeNumber;
	}

	public Integer getRouteType() {
		return routeType;
	}

	public void setRouteType(Integer routeType) {
		this.routeType = routeType;
	}

	public Integer getMainRouteId() {
		return mainRouteId;
	}

	public void setMainRouteId(Integer mainRouteId) {
		this.mainRouteId = mainRouteId;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getRouteNo() {
		return routeNo;
	}

	public void setRouteNo(String routeNo) {
		this.routeNo = routeNo;
	}

	public String getRouteTimeFrom() {
		return routeTimeFrom;
	}

	public void setRouteTimeFrom(String routeTimeFrom) {
		this.routeTimeFrom = routeTimeFrom;
	}

	public String getRouteTimeTo() {
		return routeTimeTo;
	}

	public void setRouteTimeTo(String routeTimeTo) {
		this.routeTimeTo = routeTimeTo;
	}

	public Double getRoutrAttendance() {
		return routrAttendance;
	}

	public void setRoutrAttendance(Double routrAttendance) {
		this.routrAttendance = routrAttendance;
	}

	public Double getRouteTotalHour() {
		return routeTotalHour;
	}

	public void setRouteTotalHour(Double routeTotalHour) {
		this.routeTotalHour = routeTotalHour;
	}

	public Double getRouteTotalLiter() {
		return routeTotalLiter;
	}

	public void setRouteTotalLiter(Double routeTotalLiter) {
		this.routeTotalLiter = routeTotalLiter;
	}

	public Integer getRouteApproximateKM() {
		return routeApproximateKM;
	}

	public void setRouteApproximateKM(Integer routeApproximateKM) {
		this.routeApproximateKM = routeApproximateKM;
	}

	public String getRouteRemarks() {
		return routeRemarks;
	}

	public void setRouteRemarks(String routeRemarks) {
		this.routeRemarks = routeRemarks;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

	public void setLastupdated(Date lastupdated) {
		this.lastupdated = lastupdated;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public long getVehicleGroupId() {
		return vehicleGroupId;
	}

	public void setVehicleGroupId(long vehicleGroupId) {
		this.vehicleGroupId = vehicleGroupId;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Set<TripRoutefixedExpense> getTriproutefixedexpense() {
		return triproutefixedexpense;
	}

	public void setTriproutefixedexpense(Set<TripRoutefixedExpense> triproutefixedexpense) {
		this.triproutefixedexpense = triproutefixedexpense;
	}

	public Set<TripRoutefixedIncome> getTriproutefixedincome() {
		return triproutefixedincome;
	}

	public void setTriproutefixedincome(Set<TripRoutefixedIncome> triproutefixedincome) {
		this.triproutefixedincome = triproutefixedincome;
	}
}