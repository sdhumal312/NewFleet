package org.fleetopgroup.persistence.model;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AccidentQuotationDetails")
public class AccidentQuotationDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accidentQuotationDetailsId")
	private Long	accidentQuotationDetailsId;
	
	
	@Column(name = "accidentDetailsId")
	private Long	accidentDetailsId;
	
	@Column(name = "serviceType")
	private short	serviceType;
	
	@Column(name = "serviceId")
	private Long	serviceId;
	
	@Column(name = "serviceNum")
	private Long	serviceNum;
	
	@Column(name = "companyId")
	private Integer companyId;
	

	
	@Column(name = "status")
	private short	status;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean	markForDelete;

	public Long getaccidentQuotationDetailsId() {
		return accidentQuotationDetailsId;
	}

	public void setaccidentQuotationDetailsId(Long accidentQuotationDetailsId) {
		this.accidentQuotationDetailsId = accidentQuotationDetailsId;
	}

	public Long getAccidentDetailsId() {
		return accidentDetailsId;
	}

	public void setAccidentDetailsId(Long accidentDetailsId) {
		this.accidentDetailsId = accidentDetailsId;
	}


	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public short getServiceType() {
		return serviceType;
	}

	public void setServiceType(short serviceType) {
		this.serviceType = serviceType;
	}

	public short getStatus() {
		return status;
	}

	public void setStatus(short status) {
		this.status = status;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public Long getServiceNum() {
		return serviceNum;
	}

	public void setServiceNum(Long serviceNum) {
		this.serviceNum = serviceNum;
	}

	@Override
	public String toString() {
		return "AccidentQuotationDetails [accidentQuotationDetailsId=" + accidentQuotationDetailsId
				+ ", accidentDetailsId=" + accidentDetailsId + ", serviceType=" + serviceType + ", serviceId="
				+ serviceId + ", serviceNum=" + serviceNum + ", companyId=" + companyId + ", status=" + status
				+ ", markForDelete=" + markForDelete + "]";
	}	

}
