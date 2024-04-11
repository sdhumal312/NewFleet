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
@Table(name="TicketIncomeApi")
public class TicketIncomeApi  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ticketIncomeApiId")
	private Long		ticketIncomeApiId;
	
	@Column(name = "tripSheetID")
	private Long tripSheetID;
	
	@Column(name = "vid")
	private Integer vid;
	
	@Column(name = "branchAmt")
	private Double		branchAmt;
	
	@Column(name = "guestAmt")
	private Double		guestAmt;
	
	@Column(name = "offlineAgentAmt")
	private Double		offlineAgentAmt;
	
	@Column(name = "onlineAgentsAmt")
	private Double		onlineAgentsAmt;
	
	@Column(name = "branchSeatcount")
	private Long		branchSeatcount;
	
	@Column(name = "guestSeatcount")
	private Long		guestSeatcount;
	
	@Column(name = "offAgentSeatcount")
	private Long		offAgentSeatcount;
	
	@Column(name = "onlAgentsSeatcount")
	private Long		onlAgentsSeatcount;
	
	@Column(name = "routeName")
	private String		routeName;
	
	@Column(name = "chartDate")
	private Date		chartDate;
	
	@Column(name = "companyId")
	private Integer		companyId;
	
	@Column(name = "markForDelete")
	private boolean		markForDelete;
	
	@Column(name = "createdOn")
	private Date	createdOn;
	 
	@Column(name = "createdById")
	private Long		createdById;
	
	@Column(name = "noOfTrip")
	private Integer		noOfTrip;
	
	@Column(name = "netAmount")
	private Double	netAmount;
	
	@Column(name = "lessCommission")
	private Double	lessCommission;
	
	@Column(name = "actualweight")
	private Double	actualweight;
	
	@Column(name = "chargedWeight")
	private Double	chargedWeight;
	
	@Column(name = "quantity")
	private Double	quantity;
	
	
	public TicketIncomeApi() {
		super();
		
	}
	
	public TicketIncomeApi(Long ticketIncomeApiId, Long tripSheetID, Integer vid, Double branchAmt, Double guestAmt,
			Double offlineAgentAmt, Double onlineAgentsAmt, Long branchSeatcount, Long guestSeatcount,
			Long offAgentSeatcount, Long onlAgentsSeatcount, String routeName, Date chartDate, 
			Integer companyId, boolean markForDelete, Date createdOn, Long createdById) {
		super();
		this.ticketIncomeApiId = ticketIncomeApiId;
		this.tripSheetID = tripSheetID;
		this.vid = vid;
		this.branchAmt = branchAmt;
		this.guestAmt = guestAmt;
		this.offlineAgentAmt = offlineAgentAmt;
		this.onlineAgentsAmt = onlineAgentsAmt;
		this.branchSeatcount = branchSeatcount;
		this.guestSeatcount = guestSeatcount;
		this.offAgentSeatcount = offAgentSeatcount;
		this.onlAgentsSeatcount = onlAgentsSeatcount;
		this.routeName = routeName;
		this.chartDate = chartDate;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
		this.createdOn = createdOn;
		this.createdById = createdById;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ticketIncomeApiId == null) ? 0 : ticketIncomeApiId.hashCode());
		result = prime * result + ((tripSheetID == null) ? 0 : tripSheetID.hashCode());
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
		TicketIncomeApi other = (TicketIncomeApi) obj;
		if (ticketIncomeApiId == null) {
			if (other.ticketIncomeApiId != null)
				return false;
		} else if (!ticketIncomeApiId.equals(other.ticketIncomeApiId))
			return false;
		if (tripSheetID == null) {
			if (other.tripSheetID != null)
				return false;
		} else if (!tripSheetID.equals(other.tripSheetID))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}
	
	
	public Long getTicketIncomeApiId() {
		return ticketIncomeApiId;
	}

	public void setTicketIncomeApiId(Long ticketIncomeApiId) {
		this.ticketIncomeApiId = ticketIncomeApiId;
	}

	public Long getTripSheetID() {
		return tripSheetID;
	}

	public void setTripSheetID(Long tripSheetID) {
		this.tripSheetID = tripSheetID;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Double getBranchAmt() {
		return branchAmt;
	}

	public void setBranchAmt(Double branchAmt) {
		this.branchAmt = branchAmt;
	}

	public Double getGuestAmt() {
		return guestAmt;
	}

	public void setGuestAmt(Double guestAmt) {
		this.guestAmt = guestAmt;
	}

	public Double getOfflineAgentAmt() {
		return offlineAgentAmt;
	}

	public void setOfflineAgentAmt(Double offlineAgentAmt) {
		this.offlineAgentAmt = offlineAgentAmt;
	}

	public Double getOnlineAgentsAmt() {
		return onlineAgentsAmt;
	}

	public void setOnlineAgentsAmt(Double onlineAgentsAmt) {
		this.onlineAgentsAmt = onlineAgentsAmt;
	}

	public Long getBranchSeatcount() {
		return branchSeatcount;
	}

	public void setBranchSeatcount(Long branchSeatcount) {
		this.branchSeatcount = branchSeatcount;
	}

	public Long getGuestSeatcount() {
		return guestSeatcount;
	}

	public void setGuestSeatcount(Long guestSeatcount) {
		this.guestSeatcount = guestSeatcount;
	}

	public Long getOffAgentSeatcount() {
		return offAgentSeatcount;
	}

	public void setOffAgentSeatcount(Long offAgentSeatcount) {
		this.offAgentSeatcount = offAgentSeatcount;
	}

	public Long getOnlAgentsSeatcount() {
		return onlAgentsSeatcount;
	}

	public void setOnlAgentsSeatcount(Long onlAgentsSeatcount) {
		this.onlAgentsSeatcount = onlAgentsSeatcount;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Date getChartDate() {
		return chartDate;
	}

	public void setChartDate(Date chartDate) {
		this.chartDate = chartDate;
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

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Integer getNoOfTrip() {
		return noOfTrip;
	}

	public void setNoOfTrip(Integer noOfTrip) {
		this.noOfTrip = noOfTrip;
	}

	public Double getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}

	public Double getLessCommission() {
		return lessCommission;
	}

	public void setLessCommission(Double lessCommission) {
		this.lessCommission = lessCommission;
	}

	public Double getActualweight() {
		return actualweight;
	}

	public void setActualweight(Double actualweight) {
		this.actualweight = actualweight;
	}

	public Double getChargedWeight() {
		return chargedWeight;
	}

	public void setChargedWeight(Double chargedWeight) {
		this.chargedWeight = chargedWeight;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "TicketIncomeApi [ticketIncomeApiId=" + ticketIncomeApiId + ", tripSheetID=" + tripSheetID + ", vid="
				+ vid + ", branchAmt=" + branchAmt + ", guestAmt=" + guestAmt + ", offlineAgentAmt=" + offlineAgentAmt
				+ ", onlineAgentsAmt=" + onlineAgentsAmt + ", branchSeatcount=" + branchSeatcount + ", guestSeatcount="
				+ guestSeatcount + ", offAgentSeatcount=" + offAgentSeatcount + ", onlAgentsSeatcount="
				+ onlAgentsSeatcount + ", routeName=" + routeName + ", chartDate=" + chartDate + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + ", createdOn=" + createdOn + ", createdById="
				+ createdById + "]";
	}
	
}	