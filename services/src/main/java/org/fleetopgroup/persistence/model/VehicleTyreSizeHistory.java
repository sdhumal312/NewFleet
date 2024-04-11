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
@Table(name = "VehicleTyreSizeHistory")
public class VehicleTyreSizeHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Vehicle_Tyre_Size_History_Id")
	private Integer Vehicle_Tyre_Size_History_Id;
	
	@Column(name = "TS_ID")
	private Integer TS_ID;

	@Column(name = "TYRE_SIZE", unique = false, nullable = false, length = 25)
	private String TYRE_SIZE;

	@Column(name = "TYRE_SIZE_DESCRITION", length = 250)
	private String TYRE_SIZE_DESCRITION;

	@Column(name = "LASTMODIFIEDBYID", length = 200)
	private Long LASTMODIFIEDBYID;

	@Column(name = "LASTMODIFIED_DATE", nullable = false)
	private Date LASTMODIFIED_DATE;
	
	@Column(name = "companyId" , nullable = false)
	private Integer companyId;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public Integer getVehicle_Tyre_Size_History_Id() {
		return Vehicle_Tyre_Size_History_Id;
	}

	public void setVehicle_Tyre_Size_History_Id(Integer vehicle_Tyre_Size_History_Id) {
		Vehicle_Tyre_Size_History_Id = vehicle_Tyre_Size_History_Id;
	}

	public Integer getTS_ID() {
		return TS_ID;
	}

	public void setTS_ID(Integer tS_ID) {
		TS_ID = tS_ID;
	}

	public String getTYRE_SIZE() {
		return TYRE_SIZE;
	}

	public void setTYRE_SIZE(String tYRE_SIZE) {
		TYRE_SIZE = tYRE_SIZE;
	}

	public String getTYRE_SIZE_DESCRITION() {
		return TYRE_SIZE_DESCRITION;
	}

	public void setTYRE_SIZE_DESCRITION(String tYRE_SIZE_DESCRITION) {
		TYRE_SIZE_DESCRITION = tYRE_SIZE_DESCRITION;
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