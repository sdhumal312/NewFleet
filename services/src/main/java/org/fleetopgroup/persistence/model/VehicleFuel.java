package org.fleetopgroup.persistence.model;
/**
 * @author fleetop
 *
 */
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="vehiclefuel")
public class VehicleFuel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="fid")
	private Long fid;

	@Column(name="vFuel", unique = true, nullable = false, length=25)
	private String vFuel;
	
	@Column(name="markForDelete", nullable = false)
	private boolean markForDelete;
	
	public VehicleFuel() {
		super();
	}

	public VehicleFuel(String vFuel) {
		super();
		this.vFuel = vFuel;
	}

	/**
	 * @return the fid
	 */
	public Long getFid() {
		return fid;
	}

	/**
	 * @param fid the fid to set
	 */
	public void setFid(Long fid) {
		this.fid = fid;
	}

	/**
	 * @return the vFuel
	 */
	public String getvFuel() {
		return vFuel;
	}

	/**
	 * @param vFuel the vFuel to set
	 */
	public void setvFuel(String vFuel) {
		this.vFuel = vFuel;
	}
	

	

	public boolean isMarkForDelete() {
		return markForDelete;
	}

	public void setMarkForDelete(boolean markForDelete) {
		this.markForDelete = markForDelete;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((vFuel == null) ? 0 : vFuel.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VehicleFuel other = (VehicleFuel) obj;
		if (vFuel == null) {
			if (other.vFuel != null)
				return false;
		} else if (!vFuel.equals(other.vFuel))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("VehicleFuel [vFuel=");
		builder.append(vFuel);
		builder.append("]");
		return builder.toString();
	}

	
	
}