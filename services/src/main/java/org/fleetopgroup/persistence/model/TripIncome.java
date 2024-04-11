package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tripincome")
public class TripIncome implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "incomeID")
	private Integer incomeID;

	@Column(name = "incomeName", unique = false, nullable = false, length = 200)
	private String incomeName;
	
	@Column(name = "incomeType",  nullable = false, length = 25)
	private Integer incomeType;

	@Column(name = "incomeRemarks", length = 200)
	private String incomeRemarks;

	@Column(name = "createdBy", length = 150,updatable = false , nullable = false)
	private String createdBy;

	@Column(name = "createdById",updatable = false , nullable = false)
	private Long createdById;

	@Column(name = "lastModifiedBy", length = 150)
	private String lastModifiedBy;

	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "created", nullable = false, updatable = false)
	private Date created;

	@Column(name = "lastupdated", nullable = true, updatable = true)
	private Date lastupdated;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;
	
	@Column(name = "commission")
	private Double commission;
	
	@Column(name = "gst")
	private Double gst;

	public TripIncome() {
		super();
	}

	public TripIncome(String incomeName, String incomeRemarks, String createdBy, String lastModifiedBy, Date created,
			Date lastupdated, Integer companyId, Double commission, Double gst) {
		super();
		this.incomeName = incomeName;
		this.incomeRemarks = incomeRemarks;
		this.createdBy = createdBy;
		this.lastModifiedBy = lastModifiedBy;
		this.created = created;
		this.lastupdated = lastupdated;
		this.companyId = companyId;
		this.commission = commission;
		this.gst = gst;
	}

	/**
	 * @return the incomeID
	 */
	public Integer getIncomeID() {
		return incomeID;
	}

	/**
	 * @param incomeID
	 *            the incomeID to set
	 */
	public void setIncomeID(Integer incomeID) {
		this.incomeID = incomeID;
	}

	/**
	 * @return the incomeName
	 */
	public String getIncomeName() {
		return incomeName;
	}

	/**
	 * @param incomeName
	 *            the incomeName to set
	 */
	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}

	/**
	 * @return the incomeRemarks
	 */
	public String getIncomeRemarks() {
		return incomeRemarks;
	}

	/**
	 * @param incomeRemarks
	 *            the incomeRemarks to set
	 */
	public void setIncomeRemarks(String incomeRemarks) {
		this.incomeRemarks = incomeRemarks;
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

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
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

	/**
	 * @return the incomeType
	 */
	public Integer getIncomeType() {
		return incomeType;
	}

	/**
	 * @param incomeType the incomeType to set
	 */
	public void setIncomeType(Integer incomeType) {
		this.incomeType = incomeType;
	}
	

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public Double getGst() {
		return gst;
	}

	public void setGst(Double gst) {
		this.gst = gst;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((incomeName == null) ? 0 : incomeName.hashCode());
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
		TripIncome other = (TripIncome) obj;
		if (incomeName == null) {
			if (other.incomeName != null)
				return false;
		} else if (!incomeName.equals(other.incomeName))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TripIncome [incomeID=");
		builder.append(incomeID);
		builder.append(", incomeName=");
		builder.append(incomeName);
		builder.append(", incomeType=");
		builder.append(incomeType);
		builder.append(", incomeRemarks=");
		builder.append(incomeRemarks);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", created=");
		builder.append(created);
		builder.append(", lastupdated=");
		builder.append(lastupdated);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append(", commission=");
		builder.append(commission);
		builder.append(", gst=");
		builder.append(gst);
		builder.append("]");
		return builder.toString();
	}
}