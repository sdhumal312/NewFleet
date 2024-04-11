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
@Table(name = "TripIncomeHistory")
public class TripIncomeHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TripIncomeHistoryid")
	private Integer TripIncomeHistoryid;

	@Column(name = "incomeID")
	private Integer incomeID;

	@Column(name = "incomeName", unique = false, nullable = false, length = 200)
	private String incomeName;
	
	@Column(name = "incomeType",  nullable = false, length = 25)
	private Integer incomeType;

	@Column(name = "incomeRemarks", length = 200)
	private String incomeRemarks;

	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;

	@Column(name = "lastupdated", nullable = true, updatable = true)
	private Date lastupdated;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	public Integer getTripIncomeHistoryid() {
		return TripIncomeHistoryid;
	}

	public void setTripIncomeHistoryid(Integer tripIncomeHistoryid) {
		TripIncomeHistoryid = tripIncomeHistoryid;
	}

	public Integer getIncomeID() {
		return incomeID;
	}

	public void setIncomeID(Integer incomeID) {
		this.incomeID = incomeID;
	}

	public String getIncomeName() {
		return incomeName;
	}

	public void setIncomeName(String incomeName) {
		this.incomeName = incomeName;
	}

	public Integer getIncomeType() {
		return incomeType;
	}

	public void setIncomeType(Integer incomeType) {
		this.incomeType = incomeType;
	}

	public String getIncomeRemarks() {
		return incomeRemarks;
	}

	public void setIncomeRemarks(String incomeRemarks) {
		this.incomeRemarks = incomeRemarks;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	public Date getLastupdated() {
		return lastupdated;
	}

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
}