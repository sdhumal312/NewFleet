package org.fleetopgroup.persistence.dto;

import java.util.Collection;

/**
 * @author fleetop
 *
 *
 *
 */

import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.Column;

import org.fleetopgroup.persistence.model.PurchaseOrdersToParts;
import org.fleetopgroup.web.util.Utility;

public class PurchaseOrdersDto {
	
	

	private Long purchaseorder_id;

	private Long purchaseorder_Number;
	
	private String purchaseorderNumberStr;
	
	private String purchaseorder_type;
	
	private short purchaseorder_typeId;

	private String purchaseorder_created_on;

	private String purchaseorder_requied_on;
	
	private Date purchaseorder_created;

	private Date purchaseorder_requied;

	private Integer purchaseorder_vendor_id;

	private String purchaseorder_vendor_name;

	private String purchaseorder_vendor_email;

	private String purchaseorder_vendor_location;

	private String purchaseorder_orderby;
	
	private Long purchaseorder_orderbyId;

	private String purchaseorder_buyer;

	private String purchaseorder_buyeraddress;

	private String purchaseorder_terms;
	
	private short purchaseorder_termsId;

	private String purchaseorder_shipvia;
	
	private short purchaseorder_shipviaId;

	private Integer purchaseorder_shiplocation_id;

	private String purchaseorder_shiplocation;

	private String purchaseorder_shiplocation_address;

	private String purchaseorder_shiplocation_contact;

	private String purchaseorder_shiplocation_mobile;

	private String purchaseorder_quotenumber;

	private String purchaseorder_workordernumber;

	private String purchaseorder_indentno;

	private String purchaseorder_notes;

	private String purchaseorder_status;
	
	private short purchaseorder_statusId;

	private Double purchaseorder_subtotal_cost;

	private Double purchaseorder_totaltax_cost;

	private Double purchaseorder_freight;

	private Double purchaseorder_totalcost;

	private Double purchaseorder_total_debitnote_cost;

	private Double purchaseorder_advancecost;

	private Double purchaseorder_balancecost;

	private String purchaseorder_orderd_remark;

	private String purchaseorder_orderdby;
	
	private Long purchaseorder_orderdbyId;

	private Date purchaseorder_orderddate;

	private String purchaseorder_received_remark;

	private String purchaseorder_receivedby;
	
	private Long purchaseorder_receivedbyId;

	private Date purchaseorder_received_date;

	private String purchaseorder_invoiceno;

	private String purchaseorder_invoice_date;
	
	private Date purchaseorder_invoice_date_On;

	private Date purchaseorder_complete_date;

	private Date purchaseorder_vendor_paymentdate;

	private String purchaseorder_vendor_paymode;
	
	private short purchaseorder_vendor_paymodeId;

	private Long purchaseorder_vendor_approvalID;

	private boolean purchaseorder_document;

	private boolean markForDelete;

	private Long purchaseorder_document_id;

	private Set<PurchaseOrdersToParts> purchaseorderstopparts;

	private String createdBy;
	
	private Long createdById;

	private String lastModifiedBy;
	
	private Long lastModifiedById;

	private String created;
	
	private Date createdOn;

	private String lastupdated;
	
	private Date lastupdatedOn;
	
	private short typeOfPaymentId;
	
	private String typeOfPaymentStr;
	
	private double paidAmount;
	
	private double balanceAmount;
	
	private Long tallyCompanyId;
	
	private String tallyCompanyName;
	
	private Long subCompanyId;
	
	private String subCompanyName;

	private String vendorGstNumber;
	
	private String buyerGstNumber;

	private Long vendorApprovalNumber;
	
	private Integer companyId;
	
	private String purchaseorderOrderdate;

	private String userName;
	
	private String PurchaseorderReceivedDate;
	
	private Short approvalStatus;
	
	private Long approvedById;
	
	private String approvalRemark;
	
	private String approvedBy;
	
	private String approvalStatusStr;

	//new set variables
	private String vendorAddress1;
	private String contactPerson;
	private String vendorFirPhone;
	
	//
	
	public String getApprovalStatusStr() {
		return approvalStatusStr;
	}

	public void setApprovalStatusStr(String approvalStatusStr) {
		this.approvalStatusStr = approvalStatusStr;
	}

	
	public String getVendorFirPhone() {
		return vendorFirPhone;
	}

	public void setVendorFirPhone(String vendorFirPhone) {
		this.vendorFirPhone = vendorFirPhone;
	}

	
	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getVendorAddress1() {
		return vendorAddress1;
	}

	public void setVendorAddress1(String vendorAddress1) {
		this.vendorAddress1 = vendorAddress1;
	}
	//
			
			
	
	public short getTypeOfPaymentId() {
		return typeOfPaymentId;
	}

	public void setTypeOfPaymentId(short typeOfPaymentId) {
		this.typeOfPaymentId = typeOfPaymentId;
	}

	public String getTypeOfPaymentStr() {
		return typeOfPaymentStr;
	}

	public void setTypeOfPaymentStr(String typeOfPaymentStr) {
		this.typeOfPaymentStr = typeOfPaymentStr;
	}

	public double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(double paidAmount) {
		this.paidAmount = Utility.round(paidAmount, 2);
	}

	public double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(double balanceAmount) {
		this.balanceAmount = Utility.round(balanceAmount, 2) ;
	}

	public PurchaseOrdersDto() {
		super();
	}

	/**
	 * @return the purchaseorder_id
	 */
	public Long getPurchaseorder_id() {
		return purchaseorder_id;
	}

	/**
	 * @param purchaseorder_id
	 *            the purchaseorder_id to set
	 */
	public void setPurchaseorder_id(Long purchaseorder_id) {
		this.purchaseorder_id = purchaseorder_id;
	}

	public Long getPurchaseorder_Number() {
		return purchaseorder_Number;
	}

	public void setPurchaseorder_Number(Long purchaseorder_Number) {
		this.purchaseorder_Number = purchaseorder_Number;
	}

	/**
	 * @return the purchaseorder_type
	 */
	public String getPurchaseorder_type() {
		return purchaseorder_type;
	}

	/**
	 * @param purchaseorder_type
	 *            the purchaseorder_type to set
	 */
	public void setPurchaseorder_type(String purchaseorder_type) {
		this.purchaseorder_type = purchaseorder_type;
	}

	/**
	 * @return the purchaseorder_created_on
	 */
	public String getPurchaseorder_created_on() {
		return purchaseorder_created_on;
	}

	/**
	 * @param purchaseorder_created_on
	 *            the purchaseorder_created_on to set
	 */
	public void setPurchaseorder_created_on(String purchaseorder_created_on) {
		this.purchaseorder_created_on = purchaseorder_created_on;
	}

	/**
	 * @return the purchaseorder_requied_on
	 */
	public String getPurchaseorder_requied_on() {
		return purchaseorder_requied_on;
	}

	public Short getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(Short approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public Long getApprovedById() {
		return approvedById;
	}

	public void setApprovedById(Long approvedById) {
		this.approvedById = approvedById;
	}

	public String getApprovalRemark() {
		return approvalRemark;
	}

	public void setApprovalRemark(String approvalRemark) {
		this.approvalRemark = approvalRemark;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @param purchaseorder_requied_on
	 *            the purchaseorder_requied_on to set
	 */
	public void setPurchaseorder_requied_on(String purchaseorder_requied_on) {
		this.purchaseorder_requied_on = purchaseorder_requied_on;
	}

	/**
	 * @return the purchaseorder_vendor_id
	 */
	public Integer getPurchaseorder_vendor_id() {
		return purchaseorder_vendor_id;
	}

	/**
	 * @param purchaseorder_vendor_id
	 *            the purchaseorder_vendor_id to set
	 */
	public void setPurchaseorder_vendor_id(Integer purchaseorder_vendor_id) {
		this.purchaseorder_vendor_id = purchaseorder_vendor_id;
	}

	/**
	 * @return the purchaseorder_vendor_name
	 */
	public String getPurchaseorder_vendor_name() {
		return purchaseorder_vendor_name;
	}

	/**
	 * @param purchaseorder_vendor_name
	 *            the purchaseorder_vendor_name to set
	 */
	public void setPurchaseorder_vendor_name(String purchaseorder_vendor_name) {
		this.purchaseorder_vendor_name = purchaseorder_vendor_name;
	}

	/**
	 * @return the purchaseorder_vendor_email
	 */
	public String getPurchaseorder_vendor_email() {
		return purchaseorder_vendor_email;
	}

	/**
	 * @param purchaseorder_vendor_email
	 *            the purchaseorder_vendor_email to set
	 */
	public void setPurchaseorder_vendor_email(String purchaseorder_vendor_email) {
		this.purchaseorder_vendor_email = purchaseorder_vendor_email;
	}

	/**
	 * @return the purchaseorder_vendor_location
	 */
	public String getPurchaseorder_vendor_location() {
		return purchaseorder_vendor_location;
	}

	/**
	 * @param purchaseorder_vendor_location
	 *            the purchaseorder_vendor_location to set
	 */
	public void setPurchaseorder_vendor_location(String purchaseorder_vendor_location) {
		this.purchaseorder_vendor_location = purchaseorder_vendor_location;
	}

	/**
	 * @return the purchaseorder_orderby
	 */
	public String getPurchaseorder_orderby() {
		return purchaseorder_orderby;
	}

	/**
	 * @param purchaseorder_orderby
	 *            the purchaseorder_orderby to set
	 */
	public void setPurchaseorder_orderby(String purchaseorder_orderby) {
		this.purchaseorder_orderby = purchaseorder_orderby;
	}

	/**
	 * @return the purchaseorder_buyer
	 */
	public String getPurchaseorder_buyer() {
		return purchaseorder_buyer;
	}

	/**
	 * @param purchaseorder_buyer
	 *            the purchaseorder_buyer to set
	 */
	public void setPurchaseorder_buyer(String purchaseorder_buyer) {
		this.purchaseorder_buyer = purchaseorder_buyer;
	}

	/**
	 * @return the purchaseorder_buyeraddress
	 */
	public String getPurchaseorder_buyeraddress() {
		return purchaseorder_buyeraddress;
	}

	/**
	 * @param purchaseorder_buyeraddress
	 *            the purchaseorder_buyeraddress to set
	 */
	public void setPurchaseorder_buyeraddress(String purchaseorder_buyeraddress) {
		this.purchaseorder_buyeraddress = purchaseorder_buyeraddress;
	}

	/**
	 * @return the purchaseorder_terms
	 */
	public String getPurchaseorder_terms() {
		return purchaseorder_terms;
	}

	/**
	 * @param purchaseorder_terms
	 *            the purchaseorder_terms to set
	 */
	public void setPurchaseorder_terms(String purchaseorder_terms) {
		this.purchaseorder_terms = purchaseorder_terms;
	}

	/**
	 * @return the purchaseorder_shipvia
	 */
	public String getPurchaseorder_shipvia() {
		return purchaseorder_shipvia;
	}

	/**
	 * @param purchaseorder_shipvia
	 *            the purchaseorder_shipvia to set
	 */
	public void setPurchaseorder_shipvia(String purchaseorder_shipvia) {
		this.purchaseorder_shipvia = purchaseorder_shipvia;
	}

	/**
	 * @return the purchaseorder_shiplocation_id
	 */
	public Integer getPurchaseorder_shiplocation_id() {
		return purchaseorder_shiplocation_id;
	}

	/**
	 * @param purchaseorder_shiplocation_id
	 *            the purchaseorder_shiplocation_id to set
	 */
	public void setPurchaseorder_shiplocation_id(Integer purchaseorder_shiplocation_id) {
		this.purchaseorder_shiplocation_id = purchaseorder_shiplocation_id;
	}

	/**
	 * @return the purchaseorder_shiplocation
	 */
	public String getPurchaseorder_shiplocation() {
		return purchaseorder_shiplocation;
	}

	/**
	 * @param purchaseorder_shiplocation
	 *            the purchaseorder_shiplocation to set
	 */
	public void setPurchaseorder_shiplocation(String purchaseorder_shiplocation) {
		this.purchaseorder_shiplocation = purchaseorder_shiplocation;
	}

	/**
	 * @return the purchaseorder_shiplocation_address
	 */
	public String getPurchaseorder_shiplocation_address() {
		return purchaseorder_shiplocation_address;
	}

	/**
	 * @param purchaseorder_shiplocation_address
	 *            the purchaseorder_shiplocation_address to set
	 */
	public void setPurchaseorder_shiplocation_address(String purchaseorder_shiplocation_address) {
		this.purchaseorder_shiplocation_address = purchaseorder_shiplocation_address;
	}

	/**
	 * @return the purchaseorder_quotenumber
	 */
	public String getPurchaseorder_quotenumber() {
		return purchaseorder_quotenumber;
	}

	/**
	 * @param purchaseorder_quotenumber
	 *            the purchaseorder_quotenumber to set
	 */
	public void setPurchaseorder_quotenumber(String purchaseorder_quotenumber) {
		this.purchaseorder_quotenumber = purchaseorder_quotenumber;
	}

	/**
	 * @return the purchaseorder_workordernumber
	 */
	public String getPurchaseorder_workordernumber() {
		return purchaseorder_workordernumber;
	}

	/**
	 * @param purchaseorder_workordernumber
	 *            the purchaseorder_workordernumber to set
	 */
	public void setPurchaseorder_workordernumber(String purchaseorder_workordernumber) {
		this.purchaseorder_workordernumber = purchaseorder_workordernumber;
	}

	/**
	 * @return the purchaseorder_notes
	 */
	public String getPurchaseorder_notes() {
		return purchaseorder_notes;
	}

	/**
	 * @param purchaseorder_notes
	 *            the purchaseorder_notes to set
	 */
	public void setPurchaseorder_notes(String purchaseorder_notes) {
		this.purchaseorder_notes = purchaseorder_notes;
	}

	/**
	 * @return the purchaseorder_status
	 */
	public String getPurchaseorder_status() {
		return purchaseorder_status;
	}

	/**
	 * @param purchaseorder_status
	 *            the purchaseorder_status to set
	 */
	public void setPurchaseorder_status(String purchaseorder_status) {
		this.purchaseorder_status = purchaseorder_status;
	}

	/**
	 * @return the purchaseorder_subtotal_cost
	 */
	public Double getPurchaseorder_subtotal_cost() {
		return purchaseorder_subtotal_cost;
	}

	/**
	 * @param purchaseorder_subtotal_cost
	 *            the purchaseorder_subtotal_cost to set
	 */
	public void setPurchaseorder_subtotal_cost(Double purchaseorder_subtotal_cost) {
		this.purchaseorder_subtotal_cost = Utility.round(purchaseorder_subtotal_cost, 2);
	}

	/**
	 * @return the purchaseorder_totaltax_cost
	 */
	public Double getPurchaseorder_totaltax_cost() {
		return purchaseorder_totaltax_cost;
	}

	/**
	 * @param purchaseorder_totaltax_cost
	 *            the purchaseorder_totaltax_cost to set
	 */
	public void setPurchaseorder_totaltax_cost(Double purchaseorder_totaltax_cost) {
		this.purchaseorder_totaltax_cost = Utility.round(purchaseorder_totaltax_cost, 2);
	}

	/**
	 * @return the purchaseorder_totalcost
	 */
	public Double getPurchaseorder_totalcost() {
		return purchaseorder_totalcost;
	}

	/**
	 * @param purchaseorder_totalcost
	 *            the purchaseorder_totalcost to set
	 */
	public void setPurchaseorder_totalcost(Double purchaseorder_totalcost) {
		this.purchaseorder_totalcost = Utility.round(purchaseorder_totalcost, 2);
	}

	/**
	 * @return the purchaseorder_advancecost
	 */
	public Double getPurchaseorder_advancecost() {
		return purchaseorder_advancecost;
	}

	/**
	 * @param purchaseorder_advancecost
	 *            the purchaseorder_advancecost to set
	 */
	public void setPurchaseorder_advancecost(Double purchaseorder_advancecost) {
		this.purchaseorder_advancecost = Utility.round(purchaseorder_advancecost, 2);
	}

	/**
	 * @return the purchaseorder_balancecost
	 */
	public Double getPurchaseorder_balancecost() {
		return purchaseorder_balancecost;
	}

	/**
	 * @param purchaseorder_balancecost
	 *            the purchaseorder_balancecost to set
	 */
	public void setPurchaseorder_balancecost(Double purchaseorder_balancecost) {
		this.purchaseorder_balancecost =Utility.round(purchaseorder_balancecost, 2);
	}

	/**
	 * @return the purchaseorderstopparts
	 */
	public Set<PurchaseOrdersToParts> getPurchaseorderstopparts() {
		return purchaseorderstopparts;
	}

	/**
	 * @param purchaseorderstopparts
	 *            the purchaseorderstopparts to set
	 */
	public void setPurchaseorderstopparts(Set<PurchaseOrdersToParts> purchaseorderstopparts) {
		this.purchaseorderstopparts = purchaseorderstopparts;
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
	public String getCreated() {
		return created;
	}

	/**
	 * @param created
	 *            the created to set
	 */
	public void setCreated(String created) {
		this.created = created;
	}

	/**
	 * @return the lastupdated
	 */
	public String getLastupdated() {
		return lastupdated;
	}

	/**
	 * @param lastupdated
	 *            the lastupdated to set
	 */
	public void setLastupdated(String lastupdated) {
		this.lastupdated = lastupdated;
	}

	/**
	 * @return the purchaseorder_shiplocation_contact
	 */
	public String getPurchaseorder_shiplocation_contact() {
		return purchaseorder_shiplocation_contact;
	}

	/**
	 * @param purchaseorder_shiplocation_contact
	 *            the purchaseorder_shiplocation_contact to set
	 */
	public void setPurchaseorder_shiplocation_contact(String purchaseorder_shiplocation_contact) {
		this.purchaseorder_shiplocation_contact = purchaseorder_shiplocation_contact;
	}

	/**
	 * @return the purchaseorder_shiplocation_mobile
	 */
	public String getPurchaseorder_shiplocation_mobile() {
		return purchaseorder_shiplocation_mobile;
	}

	/**
	 * @param purchaseorder_shiplocation_mobile
	 *            the purchaseorder_shiplocation_mobile to set
	 */
	public void setPurchaseorder_shiplocation_mobile(String purchaseorder_shiplocation_mobile) {
		this.purchaseorder_shiplocation_mobile = purchaseorder_shiplocation_mobile;
	}

	/**
	 * @return the purchaseorder_freight
	 */
	public Double getPurchaseorder_freight() {
		return purchaseorder_freight;
	}

	/**
	 * @param purchaseorder_freight
	 *            the purchaseorder_freight to set
	 */
	public void setPurchaseorder_freight(Double purchaseorder_freight) {
		this.purchaseorder_freight = Utility.round(purchaseorder_freight, 2);
	}

	/**
	 * @return the purchaseorder_orderd_remark
	 */
	public String getPurchaseorder_orderd_remark() {
		return purchaseorder_orderd_remark;
	}

	/**
	 * @param purchaseorder_orderd_remark
	 *            the purchaseorder_orderd_remark to set
	 */
	public void setPurchaseorder_orderd_remark(String purchaseorder_orderd_remark) {
		this.purchaseorder_orderd_remark = purchaseorder_orderd_remark;
	}

	/**
	 * @return the purchaseorder_orderdby
	 */
	public String getPurchaseorder_orderdby() {
		return purchaseorder_orderdby;
	}

	/**
	 * @param purchaseorder_orderdby
	 *            the purchaseorder_orderdby to set
	 */
	public void setPurchaseorder_orderdby(String purchaseorder_orderdby) {
		this.purchaseorder_orderdby = purchaseorder_orderdby;
	}

	/**
	 * @return the purchaseorder_orderddate
	 */
	public Date getPurchaseorder_orderddate() {
		return purchaseorder_orderddate;
	}

	/**
	 * @param purchaseorder_orderddate
	 *            the purchaseorder_orderddate to set
	 */
	public void setPurchaseorder_orderddate(Date purchaseorder_orderddate) {
		this.purchaseorder_orderddate = purchaseorder_orderddate;
	}

	/**
	 * @return the purchaseorder_received_remark
	 */
	public String getPurchaseorder_received_remark() {
		return purchaseorder_received_remark;
	}

	/**
	 * @param purchaseorder_received_remark
	 *            the purchaseorder_received_remark to set
	 */
	public void setPurchaseorder_received_remark(String purchaseorder_received_remark) {
		this.purchaseorder_received_remark = purchaseorder_received_remark;
	}

	/**
	 * @return the purchaseorder_receivedby
	 */
	public String getPurchaseorder_receivedby() {
		return purchaseorder_receivedby;
	}

	/**
	 * @param purchaseorder_receivedby
	 *            the purchaseorder_receivedby to set
	 */
	public void setPurchaseorder_receivedby(String purchaseorder_receivedby) {
		this.purchaseorder_receivedby = purchaseorder_receivedby;
	}

	/**
	 * @return the purchaseorder_received_date
	 */
	public Date getPurchaseorder_received_date() {
		return purchaseorder_received_date;
	}

	/**
	 * @param purchaseorder_received_date
	 *            the purchaseorder_received_date to set
	 */
	public void setPurchaseorder_received_date(Date purchaseorder_received_date) {
		this.purchaseorder_received_date = purchaseorder_received_date;
	}

	/**
	 * @return the purchaseorder_invoiceno
	 */
	public String getPurchaseorder_invoiceno() {
		return purchaseorder_invoiceno;
	}

	/**
	 * @param purchaseorder_invoiceno
	 *            the purchaseorder_invoiceno to set
	 */
	public void setPurchaseorder_invoiceno(String purchaseorder_invoiceno) {
		this.purchaseorder_invoiceno = purchaseorder_invoiceno;
	}

	/**
	 * @return the purchaseorder_invoice_date
	 */
	public String getPurchaseorder_invoice_date() {
		return purchaseorder_invoice_date;
	}

	/**
	 * @param purchaseorder_invoice_date
	 *            the purchaseorder_invoice_date to set
	 */
	public void setPurchaseorder_invoice_date(String purchaseorder_invoice_date) {
		this.purchaseorder_invoice_date = purchaseorder_invoice_date;
	}

	/**
	 * @return the purchaseorder_total_debitnote_cost
	 */
	public Double getPurchaseorder_total_debitnote_cost() {
		return purchaseorder_total_debitnote_cost;
	}

	/**
	 * @param purchaseorder_total_debitnote_cost
	 *            the purchaseorder_total_debitnote_cost to set
	 */
	public void setPurchaseorder_total_debitnote_cost(Double purchaseorder_total_debitnote_cost) {
		this.purchaseorder_total_debitnote_cost =  Utility.round(purchaseorder_total_debitnote_cost, 2);
	}

	/**
	 * @return the purchaseorder_complete_date
	 */
	public Date getPurchaseorder_complete_date() {
		return purchaseorder_complete_date;
	}

	/**
	 * @param purchaseorder_complete_date
	 *            the purchaseorder_complete_date to set
	 */
	public void setPurchaseorder_complete_date(Date purchaseorder_complete_date) {
		this.purchaseorder_complete_date = purchaseorder_complete_date;
	}

	public String getPurchaseorder_indentno() {
		return purchaseorder_indentno;
	}

	public void setPurchaseorder_indentno(String purchaseorder_indentno) {
		this.purchaseorder_indentno = purchaseorder_indentno;
	}

	/**
	 * @return the purchaseorder_document
	 */
	public boolean isPurchaseorder_document() {
		return purchaseorder_document;
	}

	/**
	 * @param purchaseorder_document
	 *            the purchaseorder_document to set
	 */
	public void setPurchaseorder_document(boolean purchaseorder_document) {
		this.purchaseorder_document = purchaseorder_document;
	}

	/**
	 * @return the purchaseorder_document_id
	 */
	public Long getPurchaseorder_document_id() {
		return purchaseorder_document_id;
	}

	/**
	 * @param purchaseorder_document_id
	 *            the purchaseorder_document_id to set
	 */
	public void setPurchaseorder_document_id(Long purchaseorder_document_id) {
		this.purchaseorder_document_id = purchaseorder_document_id;
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0) {
				builder.append(", ");
			}
			builder.append(iterator.next());
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * @return the purchaseorder_vendor_paymentdate
	 */
	public Date getPurchaseorder_vendor_paymentdate() {
		return purchaseorder_vendor_paymentdate;
	}

	/**
	 * @param purchaseorder_vendor_paymentdate
	 *            the purchaseorder_vendor_paymentdate to set
	 */
	public void setPurchaseorder_vendor_paymentdate(Date purchaseorder_vendor_paymentdate) {
		this.purchaseorder_vendor_paymentdate = purchaseorder_vendor_paymentdate;
	}

	/**
	 * @return the purchaseorder_vendor_paymode
	 */
	public String getPurchaseorder_vendor_paymode() {
		return purchaseorder_vendor_paymode;
	}

	/**
	 * @param purchaseorder_vendor_paymode
	 *            the purchaseorder_vendor_paymode to set
	 */
	public void setPurchaseorder_vendor_paymode(String purchaseorder_vendor_paymode) {
		this.purchaseorder_vendor_paymode = purchaseorder_vendor_paymode;
	}

	/**
	 * @return the purchaseorder_vendor_approvalID
	 */
	public Long getPurchaseorder_vendor_approvalID() {
		return purchaseorder_vendor_approvalID;
	}

	/**
	 * @param purchaseorder_vendor_approvalID
	 *            the purchaseorder_vendor_approvalID to set
	 */
	public void setPurchaseorder_vendor_approvalID(Long purchaseorder_vendor_approvalID) {
		this.purchaseorder_vendor_approvalID = purchaseorder_vendor_approvalID;
	}

	/**
	 * @return the purchaseorder_typeId
	 */
	public short getPurchaseorder_typeId() {
		return purchaseorder_typeId;
	}

	/**
	 * @param purchaseorder_typeId the purchaseorder_typeId to set
	 */
	public void setPurchaseorder_typeId(short purchaseorder_typeId) {
		this.purchaseorder_typeId = purchaseorder_typeId;
	}

	/**
	 * @return the purchaseorder_orderbyId
	 */
	public Long getPurchaseorder_orderbyId() {
		return purchaseorder_orderbyId;
	}

	/**
	 * @param purchaseorder_orderbyId the purchaseorder_orderbyId to set
	 */
	public void setPurchaseorder_orderbyId(Long purchaseorder_orderbyId) {
		this.purchaseorder_orderbyId = purchaseorder_orderbyId;
	}

	/**
	 * @return the purchaseorder_termsId
	 */
	public short getPurchaseorder_termsId() {
		return purchaseorder_termsId;
	}

	/**
	 * @param purchaseorder_termsId the purchaseorder_termsId to set
	 */
	public void setPurchaseorder_termsId(short purchaseorder_termsId) {
		this.purchaseorder_termsId = purchaseorder_termsId;
	}

	/**
	 * @return the purchaseorder_shipviaId
	 */
	public short getPurchaseorder_shipviaId() {
		return purchaseorder_shipviaId;
	}

	/**
	 * @param purchaseorder_shipviaId the purchaseorder_shipviaId to set
	 */
	public void setPurchaseorder_shipviaId(short purchaseorder_shipviaId) {
		this.purchaseorder_shipviaId = purchaseorder_shipviaId;
	}

	/**
	 * @return the purchaseorder_statusId
	 */
	public short getPurchaseorder_statusId() {
		return purchaseorder_statusId;
	}

	/**
	 * @param purchaseorder_statusId the purchaseorder_statusId to set
	 */
	public void setPurchaseorder_statusId(short purchaseorder_statusId) {
		this.purchaseorder_statusId = purchaseorder_statusId;
	}

	/**
	 * @return the purchaseorder_orderdbyId
	 */
	public Long getPurchaseorder_orderdbyId() {
		return purchaseorder_orderdbyId;
	}

	/**
	 * @param purchaseorder_orderdbyId the purchaseorder_orderdbyId to set
	 */
	public void setPurchaseorder_orderdbyId(Long purchaseorder_orderdbyId) {
		this.purchaseorder_orderdbyId = purchaseorder_orderdbyId;
	}

	/**
	 * @return the purchaseorder_receivedbyId
	 */
	public Long getPurchaseorder_receivedbyId() {
		return purchaseorder_receivedbyId;
	}

	/**
	 * @param purchaseorder_receivedbyId the purchaseorder_receivedbyId to set
	 */
	public void setPurchaseorder_receivedbyId(Long purchaseorder_receivedbyId) {
		this.purchaseorder_receivedbyId = purchaseorder_receivedbyId;
	}

	/**
	 * @return the purchaseorder_vendor_paymodeId
	 */
	public short getPurchaseorder_vendor_paymodeId() {
		return purchaseorder_vendor_paymodeId;
	}

	/**
	 * @param purchaseorder_vendor_paymodeId the purchaseorder_vendor_paymodeId to set
	 */
	public void setPurchaseorder_vendor_paymodeId(short purchaseorder_vendor_paymodeId) {
		this.purchaseorder_vendor_paymodeId = purchaseorder_vendor_paymodeId;
	}

	/**
	 * @return the createdById
	 */
	public Long getCreatedById() {
		return createdById;
	}

	/**
	 * @param createdById the createdById to set
	 */
	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	/**
	 * @return the lastModifiedById
	 */
	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	/**
	 * @param lastModifiedById the lastModifiedById to set
	 */
	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	/**
	 * @return the createdOn
	 */
	public Date getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the lastupdatedOn
	 */
	public Date getLastupdatedOn() {
		return lastupdatedOn;
	}

	/**
	 * @param lastupdatedOn the lastupdatedOn to set
	 */
	public void setLastupdatedOn(Date lastupdatedOn) {
		this.lastupdatedOn = lastupdatedOn;
	}

	/**
	 * @return the purchaseorder_created
	 */
	public Date getPurchaseorder_created() {
		return purchaseorder_created;
	}

	/**
	 * @param purchaseorder_created the purchaseorder_created to set
	 */
	public void setPurchaseorder_created(Date purchaseorder_created) {
		this.purchaseorder_created = purchaseorder_created;
	}

	/**
	 * @return the purchaseorder_requied
	 */
	public Date getPurchaseorder_requied() {
		return purchaseorder_requied;
	}

	/**
	 * @param purchaseorder_requied the purchaseorder_requied to set
	 */
	public void setPurchaseorder_requied(Date purchaseorder_requied) {
		this.purchaseorder_requied = purchaseorder_requied;
	}

	/**
	 * @return the purchaseorder_invoice_date_On
	 */
	public Date getPurchaseorder_invoice_date_On() {
		return purchaseorder_invoice_date_On;
	}

	/**
	 * @param purchaseorder_invoice_date_On the purchaseorder_invoice_date_On to set
	 */
	public void setPurchaseorder_invoice_date_On(Date purchaseorder_invoice_date_On) {
		this.purchaseorder_invoice_date_On = purchaseorder_invoice_date_On;
	}

	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}

	public String getTallyCompanyName() {
		return tallyCompanyName;
	}

	public void setTallyCompanyName(String tallyCompanyName) {
		this.tallyCompanyName = tallyCompanyName;
	}

	public Long getSubCompanyId() {
		return subCompanyId;
	}

	public void setSubCompanyId(Long subCompanyId) {
		this.subCompanyId = subCompanyId;
	}

	public String getSubCompanyName() {
		return subCompanyName;
	}

	public void setSubCompanyName(String subCompanyName) {
		this.subCompanyName = subCompanyName;
	}

	public String getVendorGstNumber() {
		return vendorGstNumber;
	}

	public void setVendorGstNumber(String vendorGstNumber) {
		this.vendorGstNumber = vendorGstNumber;
	}

	public String getBuyerGstNumber() {
		return buyerGstNumber;
	}

	public void setBuyerGstNumber(String buyerGstNumber) {
		this.buyerGstNumber = buyerGstNumber;
	}
	
	public Long getVendorApprovalNumber() {
		return vendorApprovalNumber;
	}

	public void setVendorApprovalNumber(Long vendorApprovalNumber) {
		this.vendorApprovalNumber = vendorApprovalNumber;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getPurchaseorderOrderdate() {
		return purchaseorderOrderdate;
	}

	public void setPurchaseorderOrderdate(String purchaseorderOrderdate) {
		this.purchaseorderOrderdate = purchaseorderOrderdate;
	}

	public String getPurchaseorderReceivedDate() {
		return PurchaseorderReceivedDate;
	}

	public void setPurchaseorderReceivedDate(String purchaseorderReceivedDate) {
		PurchaseorderReceivedDate = purchaseorderReceivedDate;
	}

	public String getPurchaseorderNumberStr() {
		return purchaseorderNumberStr;
	}

	public void setPurchaseorderNumberStr(String purchaseorderNumberStr) {
		this.purchaseorderNumberStr = purchaseorderNumberStr;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("PurchaseOrdersDto [purchaseorder_id=");
		builder.append(purchaseorder_id);
		builder.append(", purchaseorder_Number=");
		builder.append(purchaseorder_Number);
		builder.append(", purchaseorder_typeId=");
		builder.append(purchaseorder_typeId);
		builder.append(", purchaseorder_type=");
		builder.append(purchaseorder_type);
		builder.append(", purchaseorder_created_on=");
		builder.append(purchaseorder_created_on);
		builder.append(", purchaseorder_requied_on=");
		builder.append(purchaseorder_requied_on);
		builder.append(", purchaseorder_vendor_id=");
		builder.append(purchaseorder_vendor_id);
		builder.append(", purchaseorder_vendor_name=");
		builder.append(purchaseorder_vendor_name);
		builder.append(", purchaseorder_vendor_email=");
		builder.append(purchaseorder_vendor_email);
		builder.append(", purchaseorder_vendor_location=");
		builder.append(purchaseorder_vendor_location);
		builder.append(", purchaseorder_orderby=");
		builder.append(purchaseorder_orderby);
		builder.append(", purchaseorder_buyer=");
		builder.append(purchaseorder_buyer);
		builder.append(", purchaseorder_buyeraddress=");
		builder.append(purchaseorder_buyeraddress);
		builder.append(", purchaseorder_terms=");
		builder.append(purchaseorder_terms);
		builder.append(", purchaseorder_shipvia=");
		builder.append(purchaseorder_shipvia);
		builder.append(", purchaseorder_shiplocation_id=");
		builder.append(purchaseorder_shiplocation_id);
		builder.append(", purchaseorder_shiplocation=");
		builder.append(purchaseorder_shiplocation);
		builder.append(", purchaseorder_shiplocation_address=");
		builder.append(purchaseorder_shiplocation_address);
		builder.append(", purchaseorder_shiplocation_contact=");
		builder.append(purchaseorder_shiplocation_contact);
		builder.append(", purchaseorder_shiplocation_mobile=");
		builder.append(purchaseorder_shiplocation_mobile);
		builder.append(", purchaseorder_quotenumber=");
		builder.append(purchaseorder_quotenumber);
		builder.append(", purchaseorder_workordernumber=");
		builder.append(purchaseorder_workordernumber);
		builder.append(", purchaseorder_indentno=");
		builder.append(purchaseorder_indentno);
		builder.append(", purchaseorder_notes=");
		builder.append(purchaseorder_notes);
		builder.append(", purchaseorder_status=");
		builder.append(purchaseorder_status);
		builder.append(", purchaseorder_subtotal_cost=");
		builder.append(purchaseorder_subtotal_cost);
		builder.append(", purchaseorder_totaltax_cost=");
		builder.append(purchaseorder_totaltax_cost);
		builder.append(", purchaseorder_freight=");
		builder.append(purchaseorder_freight);
		builder.append(", purchaseorder_totalcost=");
		builder.append(purchaseorder_totalcost);
		builder.append(", purchaseorder_total_debitnote_cost=");
		builder.append(purchaseorder_total_debitnote_cost);
		builder.append(", purchaseorder_advancecost=");
		builder.append(purchaseorder_advancecost);
		builder.append(", purchaseorder_balancecost=");
		builder.append(purchaseorder_balancecost);
		builder.append(", purchaseorder_orderd_remark=");
		builder.append(purchaseorder_orderd_remark);
		builder.append(", purchaseorder_orderdby=");
		builder.append(purchaseorder_orderdby);
		builder.append(", purchaseorder_orderddate=");
		builder.append(purchaseorder_orderddate);
		builder.append(", purchaseorder_received_remark=");
		builder.append(purchaseorder_received_remark);
		builder.append(", purchaseorder_receivedby=");
		builder.append(purchaseorder_receivedby);
		builder.append(", purchaseorder_received_date=");
		builder.append(purchaseorder_received_date);
		builder.append(", purchaseorder_invoiceno=");
		builder.append(purchaseorder_invoiceno);
		builder.append(", purchaseorder_invoice_date=");
		builder.append(purchaseorder_invoice_date);
		builder.append(", purchaseorder_complete_date=");
		builder.append(purchaseorder_complete_date);
		builder.append(", purchaseorder_vendor_paymentdate=");
		builder.append(purchaseorder_vendor_paymentdate);
		builder.append(", purchaseorder_vendor_paymode=");
		builder.append(purchaseorder_vendor_paymode);
		builder.append(", purchaseorder_vendor_approvalID=");
		builder.append(purchaseorder_vendor_approvalID);
		builder.append(", purchaseorder_document=");
		builder.append(purchaseorder_document);
		builder.append(", purchaseorder_document_id=");
		builder.append(purchaseorder_document_id);
		builder.append(", purchaseorderstopparts=");
		builder.append(purchaseorderstopparts != null ? toString(purchaseorderstopparts, maxLen) : null);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", typeOfPaymentId=");
		builder.append(typeOfPaymentId);		
		builder.append(", paidAmount=");
		builder.append(paidAmount);		
		builder.append(", balanceAmount=");
		builder.append(balanceAmount);
		builder.append(", subCompanyId=");
		builder.append(subCompanyId);
		builder.append(", subCompanyName=");
		builder.append(subCompanyName);
		builder.append(", vendorGstNumber=");
		builder.append(vendorGstNumber);
		builder.append(", buyerGstNumber=");
		builder.append(buyerGstNumber);
		builder.append(", vendorApprovalNumber=");
		builder.append(vendorApprovalNumber);
		builder.append("]");
		return builder.toString();
	}

}
