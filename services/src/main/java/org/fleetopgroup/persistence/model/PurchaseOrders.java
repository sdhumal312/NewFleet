package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Basic;
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

@Entity
@Table(name = "PurchaseOrders")
public class PurchaseOrders implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "purchaseorder_id")
	private Long purchaseorder_id;
	
	@Column(name = "purchaseorder_Number", nullable = false)
	private Long purchaseorder_Number;

	@Column(name = "purchaseorder_typeId", nullable = false)
	private short purchaseorder_typeId;

	@Column(name = "purchaseorder_created_on")
	private Date purchaseorder_created_on;

	@Column(name = "purchaseorder_requied_on")
	private Date purchaseorder_requied_on;

	@Column(name = "purchaseorder_vendor_id")
	private Integer purchaseorder_vendor_id;

	@Column(name = "paidAmount")
	private Double paidAmount;
	
	@Column(name = "balanceAmount")
	private Double balanceAmount;
	
	@Column(name = "discountAmount")
	private Double discountAmount;
	
	@Column(name = "purchaseorder_buyer_id", nullable = false)
	private Integer purchaseorder_buyer_id;
	
	@Column(name = "expectedPaymentDate")
	private Timestamp	expectedPaymentDate;

	@Column(name = "purchaseorder_termsId")
	private short purchaseorder_termsId;

	
	@Column(name = "purchaseorder_shipviaId", nullable = false)
	private short purchaseorder_shipviaId;

	@Column(name = "purchaseorder_shiplocation_id", nullable = false)
	private Integer purchaseorder_shiplocation_id;


	@Column(name = "purchaseorder_quotenumber", length = 25)
	private String purchaseorder_quotenumber;

	@Column(name = "purchaseorder_workordernumber", length = 25)
	private String purchaseorder_workordernumber;

	@Column(name = "purchaseorder_indentno", length = 25)
	private String purchaseorder_indentno;

	@Column(name = "purchaseorder_notes", length = 500)
	private String purchaseorder_notes;

	
	@Column(name = "purchaseorder_statusId", nullable = false)
	private short purchaseorder_statusId;

	@Column(name = "purchaseorder_subtotal_cost", length = 10)
	private Double purchaseorder_subtotal_cost;

	@Column(name = "purchaseorder_freight", length = 10)
	private Double purchaseorder_freight;

	@Column(name = "purchaseorder_totaltax_cost", length = 10)
	private Double purchaseorder_totaltax_cost;

	@Column(name = "purchaseorder_totalcost", length = 10)
	private Double purchaseorder_totalcost;

	@Column(name = "purchaseorder_total_debitnote_cost", length = 10)
	private Double purchaseorder_total_debitnote_cost;

	@Column(name = "purchaseorder_balancecost", length = 10)
	private Double purchaseorder_balancecost;

	@Column(name = "purchaseorder_orderd_remark", length = 500)
	private String purchaseorder_orderd_remark;

	
	@Column(name = "purchaseorder_orderdbyId")
	private Long purchaseorder_orderdbyId;

	@Column(name = "purchaseorder_advancecost", length = 10)
	private Double purchaseorder_advancecost;

	@Column(name = "purchaseorder_orderddate")
	private Date purchaseorder_orderddate;

	@Column(name = "purchaseorder_received_remark", length = 500)
	private String purchaseorder_received_remark;

	
	@Column(name = "purchaseorder_receivedbyId")
	private Long purchaseorder_receivedbyId;

	@Column(name = "purchaseorder_invoiceno", length = 512)
	private String purchaseorder_invoiceno;

	@Column(name = "purchaseorder_invoice_date")
	private Date purchaseorder_invoice_date;

	@Column(name = "purchaseorder_received_date")
	private Date purchaseorder_received_date;

	@Column(name = "purchaseorder_complete_date")
	private Date purchaseorder_complete_date;

	@Column(name = "purchaseorder_vendor_paymentdate")
	private Date purchaseorder_vendor_paymentdate;

	
	@Column(name = "purchaseorder_vendor_paymodeId")
	private short purchaseorder_vendor_paymodeId;

	@Column(name = "purchaseorder_vendor_approvalID")
	private Long purchaseorder_vendor_approvalID;

	/** The value for the fuel Document available field */
	@Basic(optional = false)
	@Column(name = "purchaseorder_document", nullable = false)
	private boolean purchaseorder_document = false;

	/** The value for the fuel Document_ID available field */
	@Column(name = "purchaseorder_document_id")
	private Long purchaseorder_document_id;

	@OneToMany(mappedBy = "purchaseorders", fetch = FetchType.LAZY)
	private Set<PurchaseOrdersToParts> purchaseorderstopparts;

	
	@Column(name = "createdById")
	private Long createdById;

	
	@Column(name = "lastModifiedById")
	private Long lastModifiedById;


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
	
	/** The value for which company this record relate */
	@Column(name ="companyId" , nullable = false)
	private Integer companyId;
	
	/** The value for Marking that this record is required or not */
	@Column(name="markForDelete", nullable = false)
	boolean	markForDelete;
	
	@Column(name = "tallyCompanyId")
	private Long tallyCompanyId;
	
	@Column(name="isPendingForTally", nullable = false)
	private boolean	isPendingForTally;
	
	@Column(name="subCompanyId")
	private Long	subCompanyId;

	@Column(name="approvalStatus", columnDefinition = "int default 1")
	private Short approvalStatus;
	
	@Column(name="approvedById")
	private Long approvedById;
	
	@Column(name="approvalRemark", length = 1000)
	private String approvalRemark;
	
	public PurchaseOrders() {
		super();
	}

	public PurchaseOrders(Long purchaseorder_id, Long purchaseorder_Number, Date purchaseorder_created_on, Date purchaseorder_requied_on,
			Integer purchaseorder_vendor_id, 
			 Integer purchaseorder_shiplocation_id,
			String purchaseorder_shiplocation, String purchaseorder_shiplocation_address,
			String purchaseorder_shiplocation_contact, String purchaseorder_shiplocation_mobile,
			String purchaseorder_quotenumber, String purchaseorder_workordernumber, String purchaseorder_notes,
			Double purchaseorder_subtotal_cost, Double purchaseorder_freight,
			Double purchaseorder_totaltax_cost, Double purchaseorder_totalcost,
			Double purchaseorder_total_debitnote_cost, Double purchaseorder_balancecost,
			String purchaseorder_orderd_remark, Double purchaseorder_advancecost,
			Date purchaseorder_orderddate, String purchaseorder_received_remark,
			String purchaseorder_invoiceno, Date purchaseorder_invoice_date, Date purchaseorder_received_date,
			Date purchaseorder_complete_date, Set<PurchaseOrdersToParts> purchaseorderstopparts, Integer companyId, Long tallyCompanyId, Long subCompanyId) {
		super();
		this.purchaseorder_id = purchaseorder_id;
		this.purchaseorder_Number = purchaseorder_Number;
		this.purchaseorder_created_on = purchaseorder_created_on;
		this.purchaseorder_requied_on = purchaseorder_requied_on;
		this.purchaseorder_vendor_id = purchaseorder_vendor_id;
		this.purchaseorder_shiplocation_id = purchaseorder_shiplocation_id;
		this.purchaseorder_quotenumber = purchaseorder_quotenumber;
		this.purchaseorder_workordernumber = purchaseorder_workordernumber;
		this.purchaseorder_notes = purchaseorder_notes;
		this.purchaseorder_subtotal_cost = purchaseorder_subtotal_cost;
		this.purchaseorder_freight = purchaseorder_freight;
		this.purchaseorder_totaltax_cost = purchaseorder_totaltax_cost;
		this.purchaseorder_totalcost = purchaseorder_totalcost;
		this.purchaseorder_total_debitnote_cost = purchaseorder_total_debitnote_cost;
		this.purchaseorder_balancecost = purchaseorder_balancecost;
		this.purchaseorder_orderd_remark = purchaseorder_orderd_remark;
		this.purchaseorder_advancecost = purchaseorder_advancecost;
		this.purchaseorder_orderddate = purchaseorder_orderddate;
		this.purchaseorder_received_remark = purchaseorder_received_remark;
		this.purchaseorder_invoiceno = purchaseorder_invoiceno;
		this.purchaseorder_invoice_date = purchaseorder_invoice_date;
		this.purchaseorder_received_date = purchaseorder_received_date;
		this.purchaseorder_complete_date = purchaseorder_complete_date;
		this.purchaseorderstopparts = purchaseorderstopparts;
		this.companyId = companyId;
		this.tallyCompanyId = tallyCompanyId;
		this.subCompanyId = subCompanyId;
	}

	
	public Integer getPurchaseorder_buyer_id() {
		return purchaseorder_buyer_id;
	}

	public void setPurchaseorder_buyer_id(Integer purchaseorder_buyer_id) {
		this.purchaseorder_buyer_id = purchaseorder_buyer_id;
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
	 * @return the purchaseorder_created_on
	 */
	public Date getPurchaseorder_created_on() {
		return purchaseorder_created_on;
	}

	/**
	 * @param purchaseorder_created_on
	 *            the purchaseorder_created_on to set
	 */
	public void setPurchaseorder_created_on(Date purchaseorder_created_on) {
		this.purchaseorder_created_on = purchaseorder_created_on;
	}

	/**
	 * @return the purchaseorder_requied_on
	 */
	public Date getPurchaseorder_requied_on() {
		return purchaseorder_requied_on;
	}

	/**
	 * @param purchaseorder_requied_on
	 *            the purchaseorder_requied_on to set
	 */
	public void setPurchaseorder_requied_on(Date purchaseorder_requied_on) {
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
		this.purchaseorder_subtotal_cost = purchaseorder_subtotal_cost;
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
		this.purchaseorder_freight = purchaseorder_freight;
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
		this.purchaseorder_totaltax_cost = purchaseorder_totaltax_cost;
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
		this.purchaseorder_totalcost = purchaseorder_totalcost;
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
		this.purchaseorder_advancecost = purchaseorder_advancecost;
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
		this.purchaseorder_balancecost = purchaseorder_balancecost;
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

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
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
	public Date getPurchaseorder_invoice_date() {
		return purchaseorder_invoice_date;
	}

	/**
	 * @param purchaseorder_invoice_date
	 *            the purchaseorder_invoice_date to set
	 */
	public void setPurchaseorder_invoice_date(Date purchaseorder_invoice_date) {
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
		this.purchaseorder_total_debitnote_cost = purchaseorder_total_debitnote_cost;
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
	 * @return the status
	 */
	public boolean isMarkForDelete() {
		return markForDelete;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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

	public Timestamp getExpectedPaymentDate() {
		return expectedPaymentDate;
	}

	public void setExpectedPaymentDate(Timestamp expectedPaymentDate) {
		this.expectedPaymentDate = expectedPaymentDate;
	}

	public Double getPaidAmount() {
		return paidAmount;
	}

	public void setPaidAmount(Double paidAmount) {
		this.paidAmount = paidAmount;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	public Long getTallyCompanyId() {
		return tallyCompanyId;
	}

	public void setTallyCompanyId(Long tallyCompanyId) {
		this.tallyCompanyId = tallyCompanyId;
	}


	public boolean isPendingForTally() {
		return isPendingForTally;
	}

	public void setPendingForTally(boolean isPendingForTally) {
		this.isPendingForTally = isPendingForTally;
	}

	
	public Long getSubCompanyId() {
		return subCompanyId;
	}

	public void setSubCompanyId(Long subCompanyId) {
		this.subCompanyId = subCompanyId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		//result = prime * result + ((purchaseorder_buyeraddress == null) ? 0 : purchaseorder_buyeraddress.hashCode());
		result = prime * result + ((purchaseorder_complete_date == null) ? 0 : purchaseorder_complete_date.hashCode());
		result = prime * result + ((purchaseorder_invoice_date == null) ? 0 : purchaseorder_invoice_date.hashCode());
		result = prime * result + ((purchaseorder_invoiceno == null) ? 0 : purchaseorder_invoiceno.hashCode());
		result = prime * result + ((purchaseorder_notes == null) ? 0 : purchaseorder_notes.hashCode());
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
		PurchaseOrders other = (PurchaseOrders) obj;
		
		if (purchaseorder_complete_date == null) {
			if (other.purchaseorder_complete_date != null)
				return false;
		} else if (!purchaseorder_complete_date.equals(other.purchaseorder_complete_date))
			return false;
		if (purchaseorder_invoice_date == null) {
			if (other.purchaseorder_invoice_date != null)
				return false;
		} else if (!purchaseorder_invoice_date.equals(other.purchaseorder_invoice_date))
			return false;
		if (purchaseorder_invoiceno == null) {
			if (other.purchaseorder_invoiceno != null)
				return false;
		} else if (!purchaseorder_invoiceno.equals(other.purchaseorder_invoiceno))
			return false;
		if (purchaseorder_notes == null) {
			if (other.purchaseorder_notes != null)
				return false;
		} else if (!purchaseorder_notes.equals(other.purchaseorder_notes))
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
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("PurchaseOrders [purchaseorder_id=");
		builder.append(purchaseorder_id);
		builder.append(", purchaseorder_Number=");
		builder.append(purchaseorder_Number);
		/*builder.append(", purchaseorder_type=");
		builder.append(purchaseorder_type);*/
		builder.append(", purchaseorder_created_on=");
		builder.append(purchaseorder_created_on);
		builder.append(", purchaseorder_requied_on=");
		builder.append(purchaseorder_requied_on);
		builder.append(", purchaseorder_vendor_id=");
		builder.append(purchaseorder_vendor_id);
		builder.append(", purchaseorder_shiplocation_id=");
		builder.append(purchaseorder_shiplocation_id);
		builder.append(", purchaseorder_quotenumber=");
		builder.append(purchaseorder_quotenumber);
		builder.append(", purchaseorder_workordernumber=");
		builder.append(purchaseorder_workordernumber);
		builder.append(", purchaseorder_indentno=");
		builder.append(purchaseorder_indentno);
		builder.append(", purchaseorder_notes=");
		builder.append(purchaseorder_notes);
		builder.append(", purchaseorder_subtotal_cost=");
		builder.append(purchaseorder_subtotal_cost);
		builder.append(", purchaseorder_freight=");
		builder.append(purchaseorder_freight);
		builder.append(", purchaseorder_totaltax_cost=");
		builder.append(purchaseorder_totaltax_cost);
		builder.append(", purchaseorder_totalcost=");
		builder.append(purchaseorder_totalcost);
		builder.append(", purchaseorder_total_debitnote_cost=");
		builder.append(purchaseorder_total_debitnote_cost);
		builder.append(", purchaseorder_balancecost=");
		builder.append(purchaseorder_balancecost);
		builder.append(", purchaseorder_orderd_remark=");
		builder.append(purchaseorder_orderd_remark);
		builder.append(", purchaseorder_advancecost=");
		builder.append(purchaseorder_advancecost);
		builder.append(", purchaseorder_orderddate=");
		builder.append(purchaseorder_orderddate);
		builder.append(", purchaseorder_received_remark=");
		builder.append(purchaseorder_received_remark);
		builder.append(", purchaseorder_invoiceno=");
		builder.append(purchaseorder_invoiceno);
		builder.append(", purchaseorder_invoice_date=");
		builder.append(purchaseorder_invoice_date);
		builder.append(", purchaseorder_received_date=");
		builder.append(purchaseorder_received_date);
		builder.append(", purchaseorder_complete_date=");
		builder.append(purchaseorder_complete_date);
		builder.append(", purchaseorder_vendor_paymentdate=");
		builder.append(purchaseorder_vendor_paymentdate);
		builder.append(", purchaseorder_vendor_approvalID=");
		builder.append(purchaseorder_vendor_approvalID);
		builder.append(", purchaseorder_document=");
		builder.append(purchaseorder_document);
		builder.append(", purchaseorder_document_id=");
		builder.append(purchaseorder_document_id);
		builder.append(",markForDelete=");
		builder.append(markForDelete);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", tallyCompanyId=");
		builder.append(tallyCompanyId);
		builder.append(", subCompanyId=");
		builder.append(subCompanyId);
		builder.append("]");
		return builder.toString();
	}

}
