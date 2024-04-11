package org.fleetopgroup.persistence.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "VehicleLaundryDetails")
public class VehicleLaundryDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "vehicleLaundryDetailsId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long	vehicleLaundryDetailsId;
	
	@Column(name = "vid")
	private Integer	vid;
	
	@Column(name = "clothTypesId")
	private Long	clothTypesId;
	
	@Column(name = "laundryInvoiceId")
	private Long	laundryInvoiceId;
	
	@Column(name="laundryClothDetailsId")
	private Long	laundryClothDetailsId;
	
	@Column(name = "quantity")
	private Double quantity;
	
	@Column(name = "companyId")
	private Integer	companyId;
	
	@Column(name = "markForDelete")
	private boolean	markForDelete;

	public Long getVehicleLaundryDetailsId() {
		return vehicleLaundryDetailsId;
	}

	public void setVehicleLaundryDetailsId(Long vehicleLaundryDetailsId) {
		this.vehicleLaundryDetailsId = vehicleLaundryDetailsId;
	}

	public Integer getVid() {
		return vid;
	}

	public void setVid(Integer vid) {
		this.vid = vid;
	}

	public Long getClothTypesId() {
		return clothTypesId;
	}

	public void setClothTypesId(Long clothTypesId) {
		this.clothTypesId = clothTypesId;
	}

	public Long getLaundryInvoiceId() {
		return laundryInvoiceId;
	}

	public void setLaundryInvoiceId(Long laundryInvoiceId) {
		this.laundryInvoiceId = laundryInvoiceId;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
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

	public Long getLaundryClothDetailsId() {
		return laundryClothDetailsId;
	}

	public void setLaundryClothDetailsId(Long laundryClothDetailsId) {
		this.laundryClothDetailsId = laundryClothDetailsId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clothTypesId == null) ? 0 : clothTypesId.hashCode());
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((laundryInvoiceId == null) ? 0 : laundryInvoiceId.hashCode());
		result = prime * result + ((quantity == null) ? 0 : quantity.hashCode());
		result = prime * result + ((vehicleLaundryDetailsId == null) ? 0 : vehicleLaundryDetailsId.hashCode());
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
		VehicleLaundryDetails other = (VehicleLaundryDetails) obj;
		if (clothTypesId == null) {
			if (other.clothTypesId != null)
				return false;
		} else if (!clothTypesId.equals(other.clothTypesId))
			return false;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (laundryInvoiceId == null) {
			if (other.laundryInvoiceId != null)
				return false;
		} else if (!laundryInvoiceId.equals(other.laundryInvoiceId))
			return false;
		if (quantity == null) {
			if (other.quantity != null)
				return false;
		} else if (!quantity.equals(other.quantity))
			return false;
		if (vehicleLaundryDetailsId == null) {
			if (other.vehicleLaundryDetailsId != null)
				return false;
		} else if (!vehicleLaundryDetailsId.equals(other.vehicleLaundryDetailsId))
			return false;
		if (vid == null) {
			if (other.vid != null)
				return false;
		} else if (!vid.equals(other.vid))
			return false;
		return true;
	}
	
	
	
	
}
