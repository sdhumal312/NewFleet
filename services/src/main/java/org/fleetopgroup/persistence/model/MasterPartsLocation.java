package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
 *
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MasterPartsLocation")
public class MasterPartsLocation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "partlocationid")
	private Integer partlocationid;

	
	@Column(name = "locationId")
	private Integer locationId;

	@Column(name = "Aisle", length = 50)
	private String Aisle;

	@Column(name = "[row]", length = 50)
	private String row;

	@Column(name = "bin", length = 50)
	private String bin;

	@ManyToOne
	@JoinColumn(name = "partid")
	private MasterParts masterparts;
	
	/** The value for which company this record relate */
	@Column(name ="companyId" , nullable = false)
	private Integer companyId;
	
	/** The value for Marking that this record is required or not */
	@Column(name="markForDelete", nullable = false)
	private boolean	markForDelete;

	public MasterPartsLocation() {

	}

	public MasterPartsLocation(Integer location_ID, String aisle, String row, String bin) {
		super();
		this.locationId = location_ID;
		this.Aisle = aisle;
		this.row = row;
		this.bin = bin;

	}

	public MasterPartsLocation(Integer partlocationid, Integer location_ID, String aisle, String row, String bin) {
		super();
		this.partlocationid = partlocationid;
		this.locationId = location_ID;
		Aisle = aisle;
		this.row = row;
		this.bin = bin;

	}

	/**
	 * @return the partlocationid
	 */
	public Integer getPartlocationid() {
		return partlocationid;
	}

	/**
	 * @param partlocationid
	 *            the partlocationid to set
	 */
	public void setPartlocationid(Integer partlocationid) {
		this.partlocationid = partlocationid;
	}

	
	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	/**
	 * @return the aisle
	 */
	public String getAisle() {
		return Aisle;
	}

	/**
	 * @param aisle
	 *            the aisle to set
	 */
	public void setAisle(String aisle) {
		Aisle = aisle;
	}

	/**
	 * @return the row
	 */
	public String getRow() {
		return row;
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public void setRow(String row) {
		this.row = row;
	}

	/**
	 * @return the bin
	 */
	public String getBin() {
		return bin;
	}

	/**
	 * @param bin
	 *            the bin to set
	 */
	public void setBin(String bin) {
		this.bin = bin;
	}

	/**
	 * @return the masterparts
	 */
	public MasterParts getMasterparts() {
		return masterparts;
	}

	/**
	 * @param masterparts
	 *            the masterparts to set
	 */
	public void setMasterparts(MasterParts masterparts) {
		this.masterparts = masterparts;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((masterparts == null) ? 0 : masterparts.hashCode());
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
		MasterPartsLocation other = (MasterPartsLocation) obj;
		if (masterparts == null) {
			if (other.masterparts != null)
				return false;
		} else if (!masterparts.equals(other.masterparts))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MasterPartsLocation [partlocationid=");
		builder.append(partlocationid);
		builder.append(", locationId=");
		builder.append(locationId);
		builder.append(", Aisle=");
		builder.append(Aisle);
		builder.append(", row=");
		builder.append(row);
		builder.append(", bin=");
		builder.append(bin);
		builder.append(", masterparts=");
		builder.append(masterparts);
		builder.append(", companyId=");
		builder.append(companyId);
		builder.append(", markForDelete=");
		builder.append(markForDelete);
		builder.append("]");
		return builder.toString();
	}

	

}