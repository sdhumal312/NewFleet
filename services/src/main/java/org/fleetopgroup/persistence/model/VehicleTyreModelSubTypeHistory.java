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
@Table(name = "VehicleTyreModelSubTypeHistory")

public class VehicleTyreModelSubTypeHistory implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Vehicle_Tyre_Model_SubType_History_Id")
	private Integer Vehicle_Tyre_Model_SubType_History_Id;

	@Column(name = "TYRE_MST_ID")
	private Integer TYRE_MST_ID;

	@Column(name = "TYRE_MODEL", length = 200)
	private String TYRE_MODEL;
	
	@Column(name = "TYRE_MT_ID")
	private Integer TYRE_MT_ID;

	@Column(name = "TYRE_MODEL_SUBTYPE", nullable = true, length = 200)
	private String TYRE_MODEL_SUBTYPE;

	@Column(name = "TYRE_MODEL_DESCRITION", length = 250)
	private String TYRE_MODEL_DESCRITION;

	@Column(name = "LASTMODIFIEDBYID", nullable = false)
	private Long LASTMODIFIEDBYID;

	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	@Column(name = "LASTMODIFIED_DATE", nullable = false)
	private Date LASTMODIFIED_DATE;
	
	/* this column show that this record is related from which company*/
	@Column(name = "COMPANY_ID", nullable = false)
	private Integer COMPANY_ID;

	public Integer getVehicle_Tyre_Model_SubType_History_Id() {
		return Vehicle_Tyre_Model_SubType_History_Id;
	}

	public void setVehicle_Tyre_Model_SubType_History_Id(Integer vehicle_Tyre_Model_SubType_History_Id) {
		Vehicle_Tyre_Model_SubType_History_Id = vehicle_Tyre_Model_SubType_History_Id;
	}

	public Integer getTYRE_MST_ID() {
		return TYRE_MST_ID;
	}

	public void setTYRE_MST_ID(Integer tYRE_MST_ID) {
		TYRE_MST_ID = tYRE_MST_ID;
	}

	public String getTYRE_MODEL() {
		return TYRE_MODEL;
	}

	public void setTYRE_MODEL(String tYRE_MODEL) {
		TYRE_MODEL = tYRE_MODEL;
	}

	public Integer getTYRE_MT_ID() {
		return TYRE_MT_ID;
	}

	public void setTYRE_MT_ID(Integer tYRE_MT_ID) {
		TYRE_MT_ID = tYRE_MT_ID;
	}

	public String getTYRE_MODEL_SUBTYPE() {
		return TYRE_MODEL_SUBTYPE;
	}

	public void setTYRE_MODEL_SUBTYPE(String tYRE_MODEL_SUBTYPE) {
		TYRE_MODEL_SUBTYPE = tYRE_MODEL_SUBTYPE;
	}

	public String getTYRE_MODEL_DESCRITION() {
		return TYRE_MODEL_DESCRITION;
	}

	public void setTYRE_MODEL_DESCRITION(String tYRE_MODEL_DESCRITION) {
		TYRE_MODEL_DESCRITION = tYRE_MODEL_DESCRITION;
	}

	public Long getLASTMODIFIEDBYID() {
		return LASTMODIFIEDBYID;
	}

	public void setLASTMODIFIEDBYID(Long lASTMODIFIEDBYID) {
		LASTMODIFIEDBYID = lASTMODIFIEDBYID;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Date getLASTMODIFIED_DATE() {
		return LASTMODIFIED_DATE;
	}

	public void setLASTMODIFIED_DATE(Date lASTMODIFIED_DATE) {
		LASTMODIFIED_DATE = lASTMODIFIED_DATE;
	}

	public Integer getCOMPANY_ID() {
		return COMPANY_ID;
	}

	public void setCOMPANY_ID(Integer cOMPANY_ID) {
		COMPANY_ID = cOMPANY_ID;
	}
}