package org.fleetopgroup.persistence.model;
/**
 * @author fleetop
 *
 */
import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="vehiclepurchaseinfotypehistory")
public class VehiclePurchaseInfoTypeHistory implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="vehicle_purchase_info_type_history_id")
	private Long vehicle_purchase_info_type_history_id;

	@Column(name="ptid")
	private Long ptid;

	@Column(name="vPurchaseInfoType", unique = false, nullable = false, length=25)
	private String vPurchaseInfoType;
	
	@Column(name = "company_Id", nullable = false)
	private Integer company_Id;

	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public VehiclePurchaseInfoTypeHistory() {
		super();
	}

	public VehiclePurchaseInfoTypeHistory(String vPurchaseInfoType) {
		super();
		this.vPurchaseInfoType = vPurchaseInfoType;
	}

	
	public Long getVehicle_purchase_info_type_history_id() {
		return vehicle_purchase_info_type_history_id;
	}

	public void setVehicle_purchase_info_type_history_id(Long vehicle_purchase_info_type_history_id) {
		this.vehicle_purchase_info_type_history_id = vehicle_purchase_info_type_history_id;
	}

	/**
	 * @return the ptid
	 */
	public Long getPtid() {
		return ptid;
	}

	/**
	 * @param ptid the ptid to set
	 */
	public void setPtid(Long ptid) {
		this.ptid = ptid;
	}

	/**
	 * @return the vPurchaseInfoType
	 */
	public String getvPurchaseInfoType() {
		return vPurchaseInfoType;
	}

	/**
	 * @param vPurchaseInfoType the vPurchaseInfoType to set
	 */
	public void setvPurchaseInfoType(String vPurchaseInfoType) {
		this.vPurchaseInfoType = vPurchaseInfoType;
	}

	public Integer getCompany_Id() {
		return company_Id;
	}

	public void setCompany_Id(Integer company_Id) {
		this.company_Id = company_Id;
	}

	public Timestamp getLastModifiedOn() {
		return lastModifiedOn;
	}

	public void setLastModifiedOn(Timestamp lastModifiedOn) {
		this.lastModifiedOn = lastModifiedOn;
	}

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}
}