package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
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
@Table(name = "TripSheetOptionsHistory")
public class TripSheetOptionsHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRIPSHEET_OPTIONS_HISTORY_Id")
	private Integer TRIPSHEET_OPTIONS_HISTORY_Id;
	
	@Column(name = "tripsheetoptionsId")
	private Long tripsheetoptionsId;

	@Column(name = "tripsheetextraname", unique = false, nullable = false, length = 25)
	private String tripsheetextraname;

	@Column(name = "tripsheetextradescription", length = 250)
	private String tripsheetextradescription;

	@Column(name = "LASTMODIFIEDBYID", length = 200)
	private Long LASTMODIFIEDBYID;

	@Column(name = "LASTMODIFIED_DATE", nullable = false)
	private Date LASTMODIFIED_DATE;
	
	@Column(name = "companyId" , nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public Integer getTRIPSHEET_OPTIONS_HISTORY_Id() {
		return TRIPSHEET_OPTIONS_HISTORY_Id;
	}

	public void setTRIPSHEET_OPTIONS_HISTORY_Id(Integer tRIPSHEET_OPTIONS_HISTORY_Id) {
		TRIPSHEET_OPTIONS_HISTORY_Id = tRIPSHEET_OPTIONS_HISTORY_Id;
	}

	public Long getTripsheetoptionsId() {
		return tripsheetoptionsId;
	}

	public void setTripsheetoptionsId(Long tripsheetoptionsId) {
		this.tripsheetoptionsId = tripsheetoptionsId;
	}

	public String getTripsheetextraname() {
		return tripsheetextraname;
	}

	public void setTripsheetextraname(String tripsheetextraname) {
		this.tripsheetextraname = tripsheetextraname;
	}

	public String getTripsheetextradescription() {
		return tripsheetextradescription;
	}

	public void setTripsheetextradescription(String tripsheetextradescription) {
		this.tripsheetextradescription = tripsheetextradescription;
	}

	public Long getLASTMODIFIEDBYID() {
		return LASTMODIFIEDBYID;
	}

	public void setLASTMODIFIEDBYID(Long lASTMODIFIEDBYID) {
		LASTMODIFIEDBYID = lASTMODIFIEDBYID;
	}

	public Date getLASTMODIFIED_DATE() {
		return LASTMODIFIED_DATE;
	}

	public void setLASTMODIFIED_DATE(Date lASTMODIFIED_DATE) {
		LASTMODIFIED_DATE = lASTMODIFIED_DATE;
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