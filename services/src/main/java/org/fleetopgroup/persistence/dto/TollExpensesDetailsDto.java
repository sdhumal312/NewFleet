package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

import org.fleetopgroup.web.util.Utility;

public class TollExpensesDetailsDto {
	
	public static final String END_TIME						= "T23:59:59+05:30";
	
	public static final String CUSTOMER_ID					= "CustomerId";
	public static final String START_TRANSACTION_DATE		= "StartTransactionDate";
	public static final String END_TRANSACTION_DATE			= "EndTransactionDate";
	public static final String VEHICLE_NUMBER				= "VehicleNumber";
	public static final String WALLET_ID					= "walletId";
	public static final String FROM_DATE					= "fromDate";
	public static final String TO_DATE						= "toDate";
	
	public static final String GET_TRANSACTION_URL							= "https://fastagapi.icicibank.com/ISRCUSTAPI/Customer/GetTransactionDetails";
	public static final String GET_KVB_BANK_TOKEN_URL						= "https://www.fastag.kvb.co.in/TRANSPORTAPI/oauth/token";
	public static final String GET_KVB_BANK_TOLL_TRANSACTION_URL			= "https://www.fastag.kvb.co.in/TRANSPORTAPI/TransportTransactionAPI/GetTransactionHistory";
	

	private Long		tollExpensesDetailsId;
	
	
	
	
	private Timestamp	processingDateTime;
	
	private Timestamp	transactionDateTime;
	
	private Double		transactionAmount;
	
	private String		transactionId;
	
	private Integer		vid;
	
	private Long		tripSheetId;
	
	private String		laneCode;
	
	private String		plazaCode;
	
	private String		transactionStatus;
	
	private String		transactionReferenceNumber;
	
	private String		plazaName;
	
	private Integer		companyId;
	
	private boolean		markForDelete;
	
	private Timestamp	createdOn;
	
	private Long		createdById;
	
	private String		processingDateTimeStr;
	
	private short		fastTagBankId;
	
	private String		fastTagBankName;
	
	private String		transactionDateTimeStr;
	
	private Integer		routeID;


	public String getProcessingDateTimeStr() {
		return processingDateTimeStr;
	}



	public void setProcessingDateTimeStr(String processingDateTimeStr) {
		this.processingDateTimeStr = processingDateTimeStr;
	}



	public Timestamp getCreatedOn() {
		return createdOn;
	}



	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}



	public Long getCreatedById() {
		return createdById;
	}



	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}



	public Long getTollExpensesDetailsId() {
		return tollExpensesDetailsId;
	}



	public void setTollExpensesDetailsId(Long tollExpensesDetailsId) {
		this.tollExpensesDetailsId = tollExpensesDetailsId;
	}



	public Timestamp getProcessingDateTime() {
		return processingDateTime;
	}



	public void setProcessingDateTime(Timestamp processingDateTime) {
		this.processingDateTime = processingDateTime;
	}



	public Timestamp getTransactionDateTime() {
		return transactionDateTime;
	}



	public void setTransactionDateTime(Timestamp transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}



	public Double getTransactionAmount() {
		return transactionAmount;
	}



	public void setTransactionAmount(Double transactionAmount) {
		this.transactionAmount =Utility.round(transactionAmount, 2);
	}



	public String getTransactionId() {
		return transactionId;
	}



	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}



	public Integer getVid() {
		return vid;
	}



	public void setVid(Integer vid) {
		this.vid = vid;
	}



	public Long getTripSheetId() {
		return tripSheetId;
	}



	public void setTripSheetId(Long tripSheetId) {
		this.tripSheetId = tripSheetId;
	}



	public String getLaneCode() {
		return laneCode;
	}



	public void setLaneCode(String laneCode) {
		this.laneCode = laneCode;
	}



	public String getPlazaCode() {
		return plazaCode;
	}



	public void setPlazaCode(String plazaCode) {
		this.plazaCode = plazaCode;
	}



	public String getTransactionStatus() {
		return transactionStatus;
	}



	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}



	public String getTransactionReferenceNumber() {
		return transactionReferenceNumber;
	}



	public void setTransactionReferenceNumber(String transactionReferenceNumber) {
		this.transactionReferenceNumber = transactionReferenceNumber;
	}



	public String getPlazaName() {
		return plazaName;
	}



	public void setPlazaName(String plazaName) {
		this.plazaName = plazaName;
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



	public short getFastTagBankId() {
		return fastTagBankId;
	}



	public String getFastTagBankName() {
		return fastTagBankName;
	}



	public void setFastTagBankId(short fastTagBankId) {
		this.fastTagBankId = fastTagBankId;
	}



	public void setFastTagBankName(String fastTagBankName) {
		this.fastTagBankName = fastTagBankName;
	}



	public String getTransactionDateTimeStr() {
		return transactionDateTimeStr;
	}



	public void setTransactionDateTimeStr(String transactionDateTimeStr) {
		this.transactionDateTimeStr = transactionDateTimeStr;
	}



	public Integer getRouteID() {
		return routeID;
	}



	public void setRouteID(Integer routeID) {
		this.routeID = routeID;
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TollExpensesDetailsDto [tollExpensesDetailsId=");
		builder.append(tollExpensesDetailsId);
		builder.append(", processingDateTime=");
		builder.append(processingDateTime);
		builder.append(", transactionDateTime=");
		builder.append(transactionDateTime);
		builder.append(", transactionAmount=");
		builder.append(transactionAmount);
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", vid=");
		builder.append(vid);
		builder.append(", tripSheetId=");
		builder.append(tripSheetId);
		builder.append(", laneCode=");
		builder.append(laneCode);
		builder.append(", plazaCode=");
		builder.append(plazaCode);
		builder.append(", transactionStatus=");
		builder.append(transactionStatus);
		builder.append(", transactionReferenceNumber=");
		builder.append(transactionReferenceNumber);
		builder.append(", plazaName=");
		builder.append(plazaName);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", createdById=");
		builder.append(createdById);
		builder.append(", processingDateTimeStr=");
		builder.append(processingDateTimeStr);
		builder.append(", fastTagBankId=");
		builder.append(fastTagBankId);
		builder.append(", fastTagBankName=");
		builder.append(fastTagBankName);
		builder.append(", transactionDateTimeStr=");
		builder.append(transactionDateTimeStr);
		builder.append("]");
		return builder.toString();
	}



}
