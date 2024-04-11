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
@Table(name = "VehicleEmiPaymentDetails")
public class VehicleEmiPaymentDetails implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicleEmiPaymentDetailsId")
	private Long 		vehicleEmiPaymentDetailsId;

	@Column(name = "vehicleEmiDetailsId", nullable = false)
	private Long         vehicleEmiDetailsId ;

	@Column(name = "monthlyEmiAmount", nullable = false)
	private Double 		monthlyEmiAmount;

	@Column(name = "vid", nullable = false)
	private Integer 	vid;
	
	@Column(name = "emiLoanDate")
	private Date 		emiLoanDate;

	@Column(name = "emiPaidDate")
	private Date 		emiPaidDate;

	@Column(name = "emiPaidAmount")
	private Double 		emiPaidAmount;

	@Column(name = "emiPaidRemark")
	private String 		emiPaidRemark;

	@Column(name = "paymentTypeId")
	private short		paymentTypeId;
	
	@Column(name = "paymentStatusId")
	private short		paymentStatusId;

	@Column(name = "createdById", nullable = false)
	private Long 		createdById;

	@Column(name = "lastModifiedById", nullable = false)
	private Long 		lastModifiedById;

	@Column(name = "createdOn", nullable = false)
	private Date 		createdOn;

	@Column(name = "lastModifiedOn", nullable = false)
	private Date 		lastModifiedOn;

	@Column(name = "companyId", nullable = false)
	private Integer 	companyId;

	@Column(name = "markForDelete", nullable = false)
	private boolean 	markForDelete;
	
	

	public VehicleEmiPaymentDetails() {
		super();
	}


	public VehicleEmiPaymentDetails(Long vehicleEmiPaymentDetailsId, Long vehicleEmiDetailsId, Double monthlyEmiAmount,
			Integer vid, Date emiPaidDate, Double emiPaidAmount, String emiPaidRemark, short paymentTypeId,
			short paymentStatusId, Long createdById, Long lastModifiedById, Date createdOn, Date lastModifiedOn,
			Integer companyId, boolean markForDelete, Date emiLoanDate) {
		super();
		this.vehicleEmiPaymentDetailsId = vehicleEmiPaymentDetailsId;
		this.vehicleEmiDetailsId = vehicleEmiDetailsId;
		this.monthlyEmiAmount = monthlyEmiAmount;
		this.vid = vid;
		this.emiPaidDate = emiPaidDate;
		this.emiPaidAmount = emiPaidAmount;
		this.emiPaidRemark = emiPaidRemark;
		this.paymentTypeId = paymentTypeId;
		this.paymentStatusId = paymentStatusId;
		this.createdById = createdById;
		this.lastModifiedById = lastModifiedById;
		this.createdOn = createdOn;
		this.lastModifiedOn = lastModifiedOn;
		this.companyId = companyId;
		this.markForDelete = markForDelete;
		this.emiLoanDate = emiLoanDate;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vehicleEmiDetailsId == null) ? 0 : vehicleEmiDetailsId.hashCode());
		result = prime * result + ((vehicleEmiPaymentDetailsId == null) ? 0 : vehicleEmiPaymentDetailsId.hashCode());
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
		VehicleEmiPaymentDetails other = (VehicleEmiPaymentDetails) obj;
		if (vehicleEmiDetailsId == null) {
			if (other.vehicleEmiDetailsId != null)
				return false;
		} else if (!vehicleEmiDetailsId.equals(other.vehicleEmiDetailsId))
			return false;
		if (vehicleEmiPaymentDetailsId == null) {
			if (other.vehicleEmiPaymentDetailsId != null)
				return false;
		} else if (!vehicleEmiPaymentDetailsId.equals(other.vehicleEmiPaymentDetailsId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}


	public Long getVehicleEmiPaymentDetailsId() {
		return vehicleEmiPaymentDetailsId;
	}


	public void setVehicleEmiPaymentDetailsId(Long vehicleEmiPaymentDetailsId) {
		this.vehicleEmiPaymentDetailsId = vehicleEmiPaymentDetailsId;
	}


	public Long getVehicleEmiDetailsId() {
		return vehicleEmiDetailsId;
	}


	public void setVehicleEmiDetailsId(Long vehicleEmiDetailsId) {
		this.vehicleEmiDetailsId = vehicleEmiDetailsId;
	}


	public Double getMonthlyEmiAmount() {
		return monthlyEmiAmount;
	}


	public void setMonthlyEmiAmount(Double monthlyEmiAmount) {
		this.monthlyEmiAmount = monthlyEmiAmount;
	}


	public Integer getVid() {
		return vid;
	}


	public void setVid(Integer vid) {
		this.vid = vid;
	}


	public Date getEmiPaidDate() {
		return emiPaidDate;
	}


	public void setEmiPaidDate(Date emiPaidDate) {
		this.emiPaidDate = emiPaidDate;
	}


	public Double getEmiPaidAmount() {
		return emiPaidAmount;
	}


	public void setEmiPaidAmount(Double emiPaidAmount) {
		this.emiPaidAmount = emiPaidAmount;
	}


	public String getEmiPaidRemark() {
		return emiPaidRemark;
	}


	public void setEmiPaidRemark(String emiPaidRemark) {
		this.emiPaidRemark = emiPaidRemark;
	}


	public short getPaymentTypeId() {
		return paymentTypeId;
	}


	public void setPaymentTypeId(short paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}


	public short getPaymentStatusId() {
		return paymentStatusId;
	}


	public void setPaymentStatusId(short paymentStatusId) {
		this.paymentStatusId = paymentStatusId;
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


	public Date getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}


	public Date getLastModifiedOn() {
		return lastModifiedOn;
	}


	public void setLastModifiedOn(Date lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
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
	
	public Date getEmiLoanDate() {
		return emiLoanDate;
	}


	public void setEmiLoanDate(Date emiLoanDate) {
		this.emiLoanDate = emiLoanDate;
	}
	

	@Override
	public String toString() {
		return "VehicleEmiPaymentDetails [vehicleEmiPaymentDetailsId=" + vehicleEmiPaymentDetailsId
				+ ", vehicleEmiDetailsId=" + vehicleEmiDetailsId + ", monthlyEmiAmount=" + monthlyEmiAmount + ", vid="
				+ vid + ", emiLoanDate=" + emiLoanDate + ", emiPaidDate=" + emiPaidDate + ", emiPaidAmount="
				+ emiPaidAmount + ", emiPaidRemark=" + emiPaidRemark + ", paymentTypeId=" + paymentTypeId
				+ ", paymentStatusId=" + paymentStatusId + ", createdById=" + createdById + ", lastModifiedById="
				+ lastModifiedById + ", createdOn=" + createdOn + ", lastModifiedOn=" + lastModifiedOn + ", companyId="
				+ companyId + ", markForDelete=" + markForDelete + "]";
	}
	
	
}