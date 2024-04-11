package org.fleetopgroup.persistence.model;

/**
 * @author fleetop
 *
 *
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
@Table(name = "PartManufacturerHistory")
public class PartManufacturerHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Part_Manufacturer_History_Id")
	private Integer Part_Manufacturer_History_Id;

	@Column(name = "pmid")
	private Integer pmid;

	@Column(name = "pmName", unique = false, nullable = false, length = 50)
	private String pmName;

	@Column(name = "pmdescription", length = 150)
	private String pmdescription;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "lastModifiedById", nullable = false)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = false)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public Integer getPart_Manufacturer_History_Id() {
		return Part_Manufacturer_History_Id;
	}

	public void setPart_Manufacturer_History_Id(Integer part_Manufacturer_History_Id) {
		Part_Manufacturer_History_Id = part_Manufacturer_History_Id;
	}

	public Integer getPmid() {
		return pmid;
	}

	public void setPmid(Integer pmid) {
		this.pmid = pmid;
	}

	public String getPmName() {
		return pmName;
	}

	public void setPmName(String pmName) {
		this.pmName = pmName;
	}

	public String getPmdescription() {
		return pmdescription;
	}

	public void setPmdescription(String pmdescription) {
		this.pmdescription = pmdescription;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
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
}