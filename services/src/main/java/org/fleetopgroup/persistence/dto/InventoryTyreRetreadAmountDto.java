package org.fleetopgroup.persistence.dto;

import java.util.Date;

import org.fleetopgroup.persistence.model.InventoryTyreRetread;
import org.fleetopgroup.web.util.Utility;

public class InventoryTyreRetreadAmountDto {
	
	public static final short TRA_STATUS_OPEN			= 1;
	public static final short TRA_STATUS_RECEIVED		= 2;
	public static final short TRA_STATUS_REJECT			= 3;
	
	public static final String TRA_STATUS_OPEN_NAME			= "OPEN";
	public static final String TRA_STATUS_RECEIVED_NAME		= "RECEIVED";
	public static final String TRA_STATUS_REJECT_NAME		= "REJECT";

	private Long TR_AMOUNT_ID;

	private Long TYRE_ID;

	private String TYRE_NUMBER;

	private String TYRE_SIZE;
	
	private Integer TYRE_SIZE_ID;

	private Double RETREAD_AMOUNT;

	private Double RETREAD_DISCOUNT;

	private Double RETREAD_TAX;

	private Double RETREAD_COST;

	private String TRA_STATUS;
	
	private short TRA_STATUS_ID;

	private InventoryTyreRetread inventoryTyreRetread;

	private String UPDATEDBY;
	
	private Long UPDATEDBYID;

	boolean markForDelete;

	private Date UPDATED_DATE;
	
	private Integer COMPANY_ID;
	
	private String PartLocationName;
	
	private Long TRNUMBER;
	
	private String VENDOR_NAME;
	
	private String VENDOR_LOCATION;
	
	private String TR_OPEN_DATE;
	
	private String TR_REQUIRED_DATE;

	private Long TRID;
	
	private String TR_INVOICE_NUMBER;
	
	private Date TR_INVOICE_DATE;
	
	private Double TR_AMOUNT;
	
	private Integer TR_VENDOR_ID;
	
	private String TR_INVOICE_DATE_STR;
	
	private String TYRE_MODEL_SUBTYPE;
	
	private String LOCATION;
	
	public Long getTRID() {
		return TRID;
	}

	public void setTRID(Long tRID) {
		TRID = tRID;
	}

	public String getTR_OPEN_DATE() {
		return TR_OPEN_DATE;
	}

	public void setTR_OPEN_DATE(String tR_OPEN_DATE) {
		TR_OPEN_DATE = tR_OPEN_DATE;
	}

	public String getTR_REQUIRED_DATE() {
		return TR_REQUIRED_DATE;
	}

	public void setTR_REQUIRED_DATE(String tR_REQUIRED_DATE) {
		TR_REQUIRED_DATE = tR_REQUIRED_DATE;
	}

	public String getVENDOR_NAME() {
		return VENDOR_NAME;
	}

	public void setVENDOR_NAME(String vENDOR_NAME) {
		VENDOR_NAME = vENDOR_NAME;
	}

	public String getVENDOR_LOCATION() {
		return VENDOR_LOCATION;
	}

	public void setVENDOR_LOCATION(String vENDOR_LOCATION) {
		VENDOR_LOCATION = vENDOR_LOCATION;
	}

	public Long getTRNUMBER() {
		return TRNUMBER;
	}

	public void setTRNUMBER(Long tRNUMBER) {
		TRNUMBER = tRNUMBER;
	}

	public String getPartLocationName() {
		return PartLocationName;
	}

	public void setPartLocationName(String partLocationName) {
		PartLocationName = partLocationName;
	}

	/**
	 * @return the tR_AMOUNT_ID
	 */
	public Long getTR_AMOUNT_ID() {
		return TR_AMOUNT_ID;
	}

	/**
	 * @param tR_AMOUNT_ID
	 *            the tR_AMOUNT_ID to set
	 */
	public void setTR_AMOUNT_ID(Long tR_AMOUNT_ID) {
		TR_AMOUNT_ID = tR_AMOUNT_ID;
	}

	/**
	 * @return the tYRE_ID
	 */
	public Long getTYRE_ID() {
		return TYRE_ID;
	}

	/**
	 * @param tYRE_ID
	 *            the tYRE_ID to set
	 */
	public void setTYRE_ID(Long tYRE_ID) {
		TYRE_ID = tYRE_ID;
	}

	/**
	 * @return the tYRE_NUMBER
	 */
	public String getTYRE_NUMBER() {
		return TYRE_NUMBER;
	}

	/**
	 * @param tYRE_NUMBER
	 *            the tYRE_NUMBER to set
	 */
	public void setTYRE_NUMBER(String tYRE_NUMBER) {
		TYRE_NUMBER = tYRE_NUMBER;
	}

	/**
	 * @return the rETREAD_AMOUNT
	 */
	public Double getRETREAD_AMOUNT() {
		return RETREAD_AMOUNT;
	}

	/**
	 * @param rETREAD_AMOUNT
	 *            the rETREAD_AMOUNT to set
	 */
	public void setRETREAD_AMOUNT(Double rETREAD_AMOUNT) {
		RETREAD_AMOUNT = Utility.round(rETREAD_AMOUNT,2);
	}

	/**
	 * @return the rETREAD_DISCOUNT
	 */
	public Double getRETREAD_DISCOUNT() {
		return RETREAD_DISCOUNT;
	}

	/**
	 * @param rETREAD_DISCOUNT
	 *            the rETREAD_DISCOUNT to set
	 */
	public void setRETREAD_DISCOUNT(Double rETREAD_DISCOUNT) {
		RETREAD_DISCOUNT = Utility.round(rETREAD_DISCOUNT,2);
	}

	/**
	 * @return the rETREAD_TAX
	 */
	public Double getRETREAD_TAX() {
		return RETREAD_TAX;
	}

	/**
	 * @param rETREAD_TAX
	 *            the rETREAD_TAX to set
	 */
	public void setRETREAD_TAX(Double rETREAD_TAX) {
		RETREAD_TAX = Utility.round(rETREAD_TAX, 2);
	}

	/**
	 * @return the rETREAD_COST
	 */
	public Double getRETREAD_COST() {
		return RETREAD_COST;
	}

	/**
	 * @param rETREAD_COST
	 *            the rETREAD_COST to set
	 */
	public void setRETREAD_COST(Double rETREAD_COST) {
		RETREAD_COST = Utility.round(rETREAD_COST, 2);
	}

	/**
	 * @return the inventoryTyreRetread
	 */
	public InventoryTyreRetread getInventoryTyreRetread() {
		return inventoryTyreRetread;
	}

	/**
	 * @param inventoryTyreRetread
	 *            the inventoryTyreRetread to set
	 */
	public void setInventoryTyreRetread(InventoryTyreRetread inventoryTyreRetread) {
		this.inventoryTyreRetread = inventoryTyreRetread;
	}

	/**
	 * @return the uPDATEDBY
	 */
	public String getUPDATEDBY() {
		return UPDATEDBY;
	}

	/**
	 * @param uPDATEDBY
	 *            the uPDATEDBY to set
	 */
	public void setUPDATEDBY(String uPDATEDBY) {
		UPDATEDBY = uPDATEDBY;
	}

	/**
	 * @return the sTATUS
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param sTATUS
	 *            the sTATUS to set
	 */
	public void setMarkForDelete(boolean MarkForDelete) {
		markForDelete = MarkForDelete;
	}

	/**
	 * @return the uPDATED_DATE
	 */
	public Date getUPDATED_DATE() {
		return UPDATED_DATE;
	}

	/**
	 * @param uPDATED_DATE
	 *            the uPDATED_DATE to set
	 */
	public void setUPDATED_DATE(Date uPDATED_DATE) {
		UPDATED_DATE = uPDATED_DATE;
	}

	/**
	 * @return the tYRE_SIZE
	 */
	public String getTYRE_SIZE() {
		return TYRE_SIZE;
	}

	/**
	 * @param tYRE_SIZE
	 *            the tYRE_SIZE to set
	 */
	public void setTYRE_SIZE(String tYRE_SIZE) {
		TYRE_SIZE = tYRE_SIZE;
	}

	/**
	 * @return the tRA_STATUS
	 */
	public String getTRA_STATUS() {
		return TRA_STATUS;
	}

	/**
	 * @param tRA_STATUS
	 *            the tRA_STATUS to set
	 */
	public void setTRA_STATUS(String tRA_STATUS) {
		TRA_STATUS = tRA_STATUS;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}

	/**
	 * @return the tYRE_SIZE_ID
	 */
	public Integer getTYRE_SIZE_ID() {
		return TYRE_SIZE_ID;
	}

	/**
	 * @param tYRE_SIZE_ID the tYRE_SIZE_ID to set
	 */
	public void setTYRE_SIZE_ID(Integer tYRE_SIZE_ID) {
		TYRE_SIZE_ID = tYRE_SIZE_ID;
	}

	/**
	 * @return the tRA_STATUS_ID
	 */
	public short getTRA_STATUS_ID() {
		return TRA_STATUS_ID;
	}

	/**
	 * @param tRA_STATUS_ID the tRA_STATUS_ID to set
	 */
	public void setTRA_STATUS_ID(short tRA_STATUS_ID) {
		TRA_STATUS_ID = tRA_STATUS_ID;
	}

	/**
	 * @return the uPDATEDBYID
	 */
	public Long getUPDATEDBYID() {
		return UPDATEDBYID;
	}

	/**
	 * @param uPDATEDBYID the uPDATEDBYID to set
	 */
	public void setUPDATEDBYID(Long uPDATEDBYID) {
		UPDATEDBYID = uPDATEDBYID;
	}

	public String getTR_INVOICE_NUMBER() {
		return TR_INVOICE_NUMBER;
	}

	public void setTR_INVOICE_NUMBER(String tR_INVOICE_NUMBER) {
		TR_INVOICE_NUMBER = tR_INVOICE_NUMBER;
	}

	public Date getTR_INVOICE_DATE() {
		return TR_INVOICE_DATE;
	}

	public void setTR_INVOICE_DATE(Date tR_INVOICE_DATE) {
		TR_INVOICE_DATE = tR_INVOICE_DATE;
	}

	public Double getTR_AMOUNT() {
		return TR_AMOUNT;
	}

	public void setTR_AMOUNT(Double tR_AMOUNT) {
		TR_AMOUNT = Utility.round(tR_AMOUNT,2) ;
	}

	public Integer getTR_VENDOR_ID() {
		return TR_VENDOR_ID;
	}

	public void setTR_VENDOR_ID(Integer tR_VENDOR_ID) {
		TR_VENDOR_ID = tR_VENDOR_ID;
	}
	
	public String getTR_INVOICE_DATE_STR() {
		return TR_INVOICE_DATE_STR;
	}

	public void setTR_INVOICE_DATE_STR(String tR_INVOICE_DATE_STR) {
		TR_INVOICE_DATE_STR = tR_INVOICE_DATE_STR;
	}
	
	public String getTYRE_MODEL_SUBTYPE() {
		return TYRE_MODEL_SUBTYPE;
	}

	public void setTYRE_MODEL_SUBTYPE(String tYRE_MODEL_SUBTYPE) {
		TYRE_MODEL_SUBTYPE = tYRE_MODEL_SUBTYPE;
	}

	public String getLOCATION() {
		return LOCATION;
	}

	public void setLOCATION(String lOCATION) {
		LOCATION = lOCATION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InventoryTyreRetreadAmount [TR_AMOUNT_ID=");
		builder.append(TR_AMOUNT_ID);
		builder.append(", TYRE_ID=");
		builder.append(TYRE_ID);
		builder.append(", TYRE_NUMBER=");
		builder.append(TYRE_NUMBER);
		builder.append(", TYRE_SIZE=");
		builder.append(TYRE_SIZE);
		builder.append(", RETREAD_AMOUNT=");
		builder.append(RETREAD_AMOUNT);
		builder.append(", RETREAD_DISCOUNT=");
		builder.append(RETREAD_DISCOUNT);
		builder.append(", RETREAD_TAX=");
		builder.append(RETREAD_TAX);
		builder.append(", RETREAD_COST=");
		builder.append(RETREAD_COST);
		builder.append(", TRA_STATUS=");
		builder.append(TRA_STATUS);
		builder.append(", inventoryTyreRetread=");
		builder.append(inventoryTyreRetread);
		builder.append(", UPDATEDBY=");
		builder.append(UPDATEDBY);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", UPDATED_DATE=");
		builder.append(UPDATED_DATE);
		builder.append(", COMPANY_ID=");
		builder.append(COMPANY_ID);
		builder.append("]");
		return builder.toString();
	}


	public static String getTraStatusName(short status) {

		String statusName		=	null;
		switch (status) {
		  case TRA_STATUS_OPEN:
			  statusName	= TRA_STATUS_OPEN_NAME;
		        break;
		  case TRA_STATUS_RECEIVED: 
			  statusName	= TRA_STATUS_RECEIVED_NAME;
		        break;
		  case TRA_STATUS_REJECT: 
			  statusName	= TRA_STATUS_REJECT_NAME;
		        break;
		
		  default:
			  statusName = "--";
		        break;
		}
		return statusName;
	}
}
