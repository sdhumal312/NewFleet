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
@Table(name = "ServeyorDetails")
public class ServeyorDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "serveyorDetailsId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	serveyorDetailsId;
	
	@Column(name = "accidentId")
	private Long	accidentId;
	
	@Column(name = "serveyorName")
	private String	serveyorName;
	
	@Column(name = "serveyorMobile")
	private String	serveyorMobile;
	
	@Column(name = "serveyorCompany")
	private String	serveyorCompany;
	
	@Column(name = "remark", length = 512)
	private String remark;
	
	@Column(name = "completionDate")
	private Date	completionDate;
	
	@Column(name = "completionRemark")
	private String	completionRemark;
	
	@Column(name = "finalDamageServeyDate")
	private Date	finalDamageServeyDate;
	
	@Column(name = "finalDamageServeyRemark", length = 1000)
	private String	finalDamageServeyRemark;
	
	@Column(name = "quotationApprovalDate")
	private Date	quotationApprovalDate;
	
	@Column(name = "quotationApprovalRemark", length = 1000)
	private String	quotationApprovalRemark;
	
	@Column(name = "finalInspectionDate")
	private Date	finalInspectionDate;
	
	@Column(name = "finalInspectionRemark", length = 1000)
	private String	finalInspectionRemark;
	
	@Column(name = "created", nullable = false, updatable = false)
	private Date	created;
	
	@Column(name = "lastUpdated")
	private Date	lastUpdated;
	
	@Column(name = "createdById" , nullable = false, updatable = false)
	private Long	createdById;
	
	@Column(name = "lastUpdatedById")
	private Long	lastUpdatedById;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;
	
	@Column(name = "finalServeyorName")
	private String	finalServeyorName;
	
	@Column(name = "finalServeyorMobile")
	private String	finalServeyorMobile;
	
	@Column(name = "finalServeyorEmail")
	private String	finalServeyorEmail;
	
	@Column(name = "finalServeyorDept")
	private String	finalServeyorDept;
	
	@Column(name = "finalServeyorClaimNum")
	private String	finalServeyorClaimNum;
	
	@Column(name = "keepOpenDate")
	private Date	keepOpenDate;
	
	@Column(name = "keepOpenRemark")
	private String	keepOpenRemark;
	
	@Column(name = "insuranceSubmitDate")
	private Date	insuranceSubmitDate;
	
	@Column(name = "callFinalSurveyorDate")
	private Date	callFinalSurveyorDate;
	
	@Column(name = "salvageAmount")
	private Double	salvageAmount;
	
	@Column(name = "serveyInformDate")
	private Date	serveyInformDate;
	

	public Long getServeyorDetailsId() {
		return serveyorDetailsId;
	}

	public void setServeyorDetailsId(Long serveyorDetailsId) {
		this.serveyorDetailsId = serveyorDetailsId;
	}

	public Long getAccidentId() {
		return accidentId;
	}

	public void setAccidentId(Long accidentId) {
		this.accidentId = accidentId;
	}

	public String getServeyorName() {
		return serveyorName;
	}

	public void setServeyorName(String serveyorName) {
		this.serveyorName = serveyorName;
	}

	public String getServeyorMobile() {
		return serveyorMobile;
	}

	public void setServeyorMobile(String serveyorMobile) {
		this.serveyorMobile = serveyorMobile;
	}

	public String getServeyorCompany() {
		return serveyorCompany;
	}

	public void setServeyorCompany(String serveyorCompany) {
		this.serveyorCompany = serveyorCompany;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}

	public String getCompletionRemark() {
		return completionRemark;
	}

	public void setCompletionRemark(String completionRemark) {
		this.completionRemark = completionRemark;
	}

	public Date getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastUpdatedById() {
		return lastUpdatedById;
	}

	public void setLastUpdatedById(Long lastUpdatedById) {
		this.lastUpdatedById = lastUpdatedById;
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

	public Date getFinalDamageServeyDate() {
		return finalDamageServeyDate;
	}

	public void setFinalDamageServeyDate(Date finalDamageServeyDate) {
		this.finalDamageServeyDate = finalDamageServeyDate;
	}

	public String getFinalDamageServeyRemark() {
		return finalDamageServeyRemark;
	}

	public void setFinalDamageServeyRemark(String finalDamageServeyRemark) {
		this.finalDamageServeyRemark = finalDamageServeyRemark;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getQuotationApprovalDate() {
		return quotationApprovalDate;
	}

	public void setQuotationApprovalDate(Date quotationApprovalDate) {
		this.quotationApprovalDate = quotationApprovalDate;
	}

	public String getQuotationApprovalRemark() {
		return quotationApprovalRemark;
	}

	public void setQuotationApprovalRemark(String quotationApprovalRemark) {
		this.quotationApprovalRemark = quotationApprovalRemark;
	}

	public Date getFinalInspectionDate() {
		return finalInspectionDate;
	}

	public void setFinalInspectionDate(Date finalInspectionDate) {
		this.finalInspectionDate = finalInspectionDate;
	}

	public String getFinalInspectionRemark() {
		return finalInspectionRemark;
	}

	public void setFinalInspectionRemark(String finalInspectionRemark) {
		this.finalInspectionRemark = finalInspectionRemark;
	}

	public String getFinalServeyorName() {
		return finalServeyorName;
	}

	public void setFinalServeyorName(String finalServeyorName) {
		this.finalServeyorName = finalServeyorName;
	}

	public String getFinalServeyorMobile() {
		return finalServeyorMobile;
	}

	public void setFinalServeyorMobile(String finalServeyorMobile) {
		this.finalServeyorMobile = finalServeyorMobile;
	}

	public String getFinalServeyorDept() {
		return finalServeyorDept;
	}

	public void setFinalServeyorDept(String finalServeyorDept) {
		this.finalServeyorDept = finalServeyorDept;
	}

	public String getFinalServeyorClaimNum() {
		return finalServeyorClaimNum;
	}

	public void setFinalServeyorClaimNum(String finalServeyorClaimNum) {
		this.finalServeyorClaimNum = finalServeyorClaimNum;
	}

	public String getFinalServeyorEmail() {
		return finalServeyorEmail;
	}

	public void setFinalServeyorEmail(String finalServeyorEmail) {
		this.finalServeyorEmail = finalServeyorEmail;
	}

	public Date getKeepOpenDate() {
		return keepOpenDate;
	}

	public void setKeepOpenDate(Date keepOpenDate) {
		this.keepOpenDate = keepOpenDate;
	}

	public String getKeepOpenRemark() {
		return keepOpenRemark;
	}

	public void setKeepOpenRemark(String keepOpenRemark) {
		this.keepOpenRemark = keepOpenRemark;
	}

	public Date getInsuranceSubmitDate() {
		return insuranceSubmitDate;
	}

	public void setInsuranceSubmitDate(Date insuranceSubmitDate) {
		this.insuranceSubmitDate = insuranceSubmitDate;
	}

	public Date getCallFinalSurveyorDate() {
		return callFinalSurveyorDate;
	}

	public void setCallFinalSurveyorDate(Date callFinalSurveyorDate) {
		this.callFinalSurveyorDate = callFinalSurveyorDate;
	}

	public Double getSalvageAmount() {
		return salvageAmount;
	}

	public void setSalvageAmount(Double salvageAmount) {
		this.salvageAmount = salvageAmount;
	}

	public Date getServeyInformDate() {
		return serveyInformDate;
	}

	public void setServeyInformDate(Date serveyInformDate) {
		this.serveyInformDate = serveyInformDate;
	}
	
	
	
	
	
}
