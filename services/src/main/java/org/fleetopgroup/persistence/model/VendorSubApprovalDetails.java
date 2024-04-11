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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VendorSubApprovalDetails")
public class VendorSubApprovalDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subApprovalId")
	private Long subApprovalId;
	
	@Column(name = "approvalId")
	private Long approvalId;
	
	@Column(name = "subApprovalTotal")//invoiceTotalAmount
	private Double subApprovalTotal;

	@Column(name = "subApprovalpaidAmount")//TotalApprovedAmount
	private Double subApprovalpaidAmount;
	
	@Column(name = "invoiceId", nullable = false)
	private Long invoiceId;
	
	@Column(name = "invoiceNumber")
	private String	invoiceNumber;
	
	@Column(name = "invoiceDate")
	private Date	invoiceDate;
	
	@Column(name = "approvalPlaceId")
	private short	approvalPlaceId;
	
	@Column(name = "approvalPaymentTypeId")
	private short	approvalPaymentTypeId;
	
	@Column(name = "transactionNumber")
	private String	transactionNumber;

	@Column(name = "expectedPaymentDate")
	private Timestamp	expectedPaymentDate;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "vid")
	private Integer vid;
	
	@Column(name = "approvedPaymentStatusId")
	private short	approvedPaymentStatusId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;

	
	public VendorSubApprovalDetails() {
		super();
	}


	

	public Long getSubApprovalId() {
		return subApprovalId;
	}


	public void setSubApprovalId(Long subApprovalId) {
		this.subApprovalId = subApprovalId;
	}


	public Long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}

	
	public Double getSubApprovalTotal() {
		return subApprovalTotal;
	}


	public void setSubApprovalTotal(Double subApprovalTotal) {
		this.subApprovalTotal = subApprovalTotal;
	}


	public Double getSubApprovalpaidAmount() {
		return subApprovalpaidAmount;
	}


	public void setSubApprovalpaidAmount(Double subApprovalpaidAmount) {
		this.subApprovalpaidAmount = subApprovalpaidAmount;
	}


	public Long getInvoiceId() {
		return invoiceId;
	}


	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Timestamp getExpectedPaymentDate() {
		return expectedPaymentDate;
	}


	public void setExpectedPaymentDate(Timestamp expectedPaymentDate) {
		this.expectedPaymentDate = expectedPaymentDate;
	}

	public Integer getCompanyId() {
		return companyId;
	}


	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public String getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}




	public short getApprovedPaymentStatusId() {
		return approvedPaymentStatusId;
	}




	public void setApprovedPaymentStatusId(short approvedPaymentStatusId) {
		this.approvedPaymentStatusId = approvedPaymentStatusId;
	}




	public short getApprovalPlaceId() {
		return approvalPlaceId;
	}




	public String getInvoiceNumber() {
		return invoiceNumber;
	}




	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}




	public Date getInvoiceDate() {
		return invoiceDate;
	}




	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}




	public void setApprovalPlaceId(short approvalPlaceId) {
		this.approvalPlaceId = approvalPlaceId;
	}




	public short getApprovalPaymentTypeId() {
		return approvalPaymentTypeId;
	}

	public void setApprovalPaymentTypeId(short approvalPaymentTypeId) {
		this.approvalPaymentTypeId = approvalPaymentTypeId;
	}




	public Integer getVid() {
		return vid;
	}




	public void setVid(Integer vid) {
		this.vid = vid;
	}



}
