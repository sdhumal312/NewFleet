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
@Table(name = "CashBookNameHistory")
public class CashBookNameHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CashBookNameHistoryId")
	private Integer CashBookNameHistoryId;

	@Column(name = "NAMEID")
	private Integer NAMEID;

	/** This Cash Book Name Master Of values */
	@Column(name = "CASHBOOK_NAME", unique = false, nullable = false, length = 250)
	private String CASHBOOK_NAME;

	/** This Cash Book Name Master remarks Of values */
	@Column(name = "CASHBOOK_REMARKS", length = 250)
	private String CASHBOOK_REMARKS;
	
	@Column(name = "CASHBOOK_CODE", length = 50)
	private String CASHBOOK_CODE;

	/** this value inform to which company this record related*/
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;
	
	@Column(name = "LASTMODIFIEDBYID")
	private Long LASTMODIFIEDBYID;

	/** This Cash Book Name LAST Modified Date Of values */
	@Column(name = "LASTMODIFIEDON", nullable = true, updatable = true)
	private Date LASTMODIFIEDON;
	
	@Column(name = "VEHICLE_GROUP_ID", nullable = false)
	private long VEHICLE_GROUP_ID;
	
	
	/** this is for marking this value is required or not */
	@Column(name = "markForDelete" , nullable = false)
	private boolean	markForDelete;


	public Integer getCashBookNameHistoryId() {
		return CashBookNameHistoryId;
	}


	public void setCashBookNameHistoryId(Integer cashBookNameHistoryId) {
		CashBookNameHistoryId = cashBookNameHistoryId;
	}


	public Integer getNAMEID() {
		return NAMEID;
	}


	public void setNAMEID(Integer nAMEID) {
		NAMEID = nAMEID;
	}


	public String getCASHBOOK_NAME() {
		return CASHBOOK_NAME;
	}


	public void setCASHBOOK_NAME(String cASHBOOK_NAME) {
		CASHBOOK_NAME = cASHBOOK_NAME;
	}


	public String getCASHBOOK_REMARKS() {
		return CASHBOOK_REMARKS;
	}


	public void setCASHBOOK_REMARKS(String cASHBOOK_REMARKS) {
		CASHBOOK_REMARKS = cASHBOOK_REMARKS;
	}


	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}


	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}


	public Long getLASTMODIFIEDBYID() {
		return LASTMODIFIEDBYID;
	}


	public void setLASTMODIFIEDBYID(Long lASTMODIFIEDBYID) {
		LASTMODIFIEDBYID = lASTMODIFIEDBYID;
	}


	public Date getLASTMODIFIEDON() {
		return LASTMODIFIEDON;
	}


	public void setLASTMODIFIEDON(Date lASTMODIFIEDON) {
		LASTMODIFIEDON = lASTMODIFIEDON;
	}


	public long getVEHICLE_GROUP_ID() {
		return VEHICLE_GROUP_ID;
	}


	public String getCASHBOOK_CODE() {
		return CASHBOOK_CODE;
	}


	public void setCASHBOOK_CODE(String cASHBOOK_CODE) {
		CASHBOOK_CODE = cASHBOOK_CODE;
	}


	public void setVEHICLE_GROUP_ID(long vEHICLE_GROUP_ID) {
		VEHICLE_GROUP_ID = vEHICLE_GROUP_ID;
	}


	public boolean isMarkForDelete() {
		return markForDelete;
	}


	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}
}