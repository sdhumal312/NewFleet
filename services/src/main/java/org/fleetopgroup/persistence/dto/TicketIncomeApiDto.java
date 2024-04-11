package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.web.util.Utility;

/**
 * @author fleetop
 *
 */

public class TicketIncomeApiDto{
	
	public static final String END_TIME						= "T23:59:59+05:30";
	
	public static final String FROM_DATE					= "FromDate";
	public static final String TO_DATE						= "ToDate";
	public static final String BUS_NUMBER					= "BusNumber";

	
	private Long		ticketIncomeApiId;
	
	private Long 		tripSheetID;
	
	private Integer 	vid;
	
	private Double		branchAmt;
	
	private Double		guestAmt;
	
	private Double		offlineAgentAmt;
	
	private Double		onlineAgentsAmt;
	
	private Long		branchSeatcount;
	
	private Long		guestSeatcount;
	
	private Long		offAgentSeatcount;
	
	private Long		onlAgentsSeatcount;
	
	private String		routeName;
	
	private Date		chartDate;
	

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
		this.branchAmt = Utility.round( branchAmt, 2);
	}

	public Double getGuestAmt() {
		return guestAmt;
	}

	public void setGuestAmt(Double guestAmt) {
		this.guestAmt = Utility.round(guestAmt, 2);
	}

	public Double getOfflineAgentAmt() {
		return offlineAgentAmt;
	}

	public void setOfflineAgentAmt(Double offlineAgentAmt) {
		this.offlineAgentAmt = Utility.round( offlineAgentAmt,2);
	}

	public Double getOnlineAgentsAmt() {
		return onlineAgentsAmt;
	}

	public void setOnlineAgentsAmt(Double onlineAgentsAmt) {
		this.onlineAgentsAmt =Utility.round(onlineAgentsAmt, 2);
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

	@Override
	public String toString() {
		return "TicketIncomeApiDto [ticketIncomeApiId=" + ticketIncomeApiId + ", tripSheetID=" + tripSheetID + ", vid="
				+ vid + ", branchAmt=" + branchAmt + ", guestAmt=" + guestAmt + ", offlineAgentAmt=" + offlineAgentAmt
				+ ", onlineAgentsAmt=" + onlineAgentsAmt + ", branchSeatcount=" + branchSeatcount + ", guestSeatcount="
				+ guestSeatcount + ", offAgentSeatcount=" + offAgentSeatcount + ", onlAgentsSeatcount="
				+ onlAgentsSeatcount + ", routeName=" + routeName + ", chartDate=" + chartDate + "]";
	}
	
	
}