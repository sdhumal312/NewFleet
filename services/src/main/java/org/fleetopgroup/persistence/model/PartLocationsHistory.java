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
@Table(name = "PartLocationsHistory")
public class PartLocationsHistory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Part_Locations_History_Id")
	private Integer Part_Locations_History_Id;

	@Column(name = "partlocation_id")
	private Integer partlocation_id;

	@Column(name = "partlocation_name", unique = false, nullable = false, length = 100)
	private String partlocation_name;

	@Column(name = "partlocation_address", length = 50)
	private String partlocation_address;

	@Column(name = "partlocation_streetaddress", length = 50)
	private String partlocation_streetaddress;

	@Column(name = "partlocation_city", length = 50)
	private String partlocation_city;

	@Column(name = "partlocation_state", length = 50)
	private String partlocation_state;

	@Column(name = "partlocation_pincode", length = 6)
	private String partlocation_pincode;

	@Column(name = "partlocation_country", length = 25)
	private String partlocation_country;

	@Column(name = "shippartlocation_address", length = 50)
	private String shippartlocation_address;

	@Column(name = "shippartlocation_streetaddress", length = 50)
	private String shippartlocation_streetaddress;

	@Column(name = "shippartlocation_city", length = 50)
	private String shippartlocation_city;

	@Column(name = "shippartlocation_state", length = 50)
	private String shippartlocation_state;

	@Column(name = "shippartlocation_pincode", length = 6)
	private String shippartlocation_pincode;

	@Column(name = "shippartlocation_country", length = 25)
	private String shippartlocation_country;

	@Column(name = "partlocation_description", length = 200)
	private String partlocation_description;

	@Column(name = "contactFirName", length = 50)
	private String contactFirName;

	@Column(name = "contactFirPhone", length = 15)
	private String contactFirPhone;

	@Column(name = "contactFirdescription", length = 100)
	private String contactFirdescription;

	@Column(name = "contactSecName", length = 50)
	private String contactSecName;

	@Column(name = "contactSecPhone", length = 15)
	private String contactSecPhone;

	@Column(name = "contactSecdescription", length = 100)
	private String contactSecdescription;
	
	@Column(name = "companyId", nullable = false)
	private Integer companyId;

	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	boolean markForDelete;

	public Integer getPart_Locations_History_Id() {
		return Part_Locations_History_Id;
	}

	public void setPart_Locations_History_Id(Integer part_Locations_History_Id) {
		Part_Locations_History_Id = part_Locations_History_Id;
	}

	public Integer getPartlocation_id() {
		return partlocation_id;
	}

	public void setPartlocation_id(Integer partlocation_id) {
		this.partlocation_id = partlocation_id;
	}

	public String getPartlocation_name() {
		return partlocation_name;
	}

	public void setPartlocation_name(String partlocation_name) {
		this.partlocation_name = partlocation_name;
	}

	public String getPartlocation_address() {
		return partlocation_address;
	}

	public void setPartlocation_address(String partlocation_address) {
		this.partlocation_address = partlocation_address;
	}

	public String getPartlocation_streetaddress() {
		return partlocation_streetaddress;
	}

	public void setPartlocation_streetaddress(String partlocation_streetaddress) {
		this.partlocation_streetaddress = partlocation_streetaddress;
	}

	public String getPartlocation_city() {
		return partlocation_city;
	}

	public void setPartlocation_city(String partlocation_city) {
		this.partlocation_city = partlocation_city;
	}

	public String getPartlocation_state() {
		return partlocation_state;
	}

	public void setPartlocation_state(String partlocation_state) {
		this.partlocation_state = partlocation_state;
	}

	public String getPartlocation_pincode() {
		return partlocation_pincode;
	}

	public void setPartlocation_pincode(String partlocation_pincode) {
		this.partlocation_pincode = partlocation_pincode;
	}

	public String getPartlocation_country() {
		return partlocation_country;
	}

	public void setPartlocation_country(String partlocation_country) {
		this.partlocation_country = partlocation_country;
	}

	public String getShippartlocation_address() {
		return shippartlocation_address;
	}

	public void setShippartlocation_address(String shippartlocation_address) {
		this.shippartlocation_address = shippartlocation_address;
	}

	public String getShippartlocation_streetaddress() {
		return shippartlocation_streetaddress;
	}

	public void setShippartlocation_streetaddress(String shippartlocation_streetaddress) {
		this.shippartlocation_streetaddress = shippartlocation_streetaddress;
	}

	public String getShippartlocation_city() {
		return shippartlocation_city;
	}

	public void setShippartlocation_city(String shippartlocation_city) {
		this.shippartlocation_city = shippartlocation_city;
	}

	public String getShippartlocation_state() {
		return shippartlocation_state;
	}

	public void setShippartlocation_state(String shippartlocation_state) {
		this.shippartlocation_state = shippartlocation_state;
	}

	public String getShippartlocation_pincode() {
		return shippartlocation_pincode;
	}

	public void setShippartlocation_pincode(String shippartlocation_pincode) {
		this.shippartlocation_pincode = shippartlocation_pincode;
	}

	public String getShippartlocation_country() {
		return shippartlocation_country;
	}

	public void setShippartlocation_country(String shippartlocation_country) {
		this.shippartlocation_country = shippartlocation_country;
	}

	public String getPartlocation_description() {
		return partlocation_description;
	}

	public void setPartlocation_description(String partlocation_description) {
		this.partlocation_description = partlocation_description;
	}

	public String getContactFirName() {
		return contactFirName;
	}

	public void setContactFirName(String contactFirName) {
		this.contactFirName = contactFirName;
	}

	public String getContactFirPhone() {
		return contactFirPhone;
	}

	public void setContactFirPhone(String contactFirPhone) {
		this.contactFirPhone = contactFirPhone;
	}

	public String getContactFirdescription() {
		return contactFirdescription;
	}

	public void setContactFirdescription(String contactFirdescription) {
		this.contactFirdescription = contactFirdescription;
	}

	public String getContactSecName() {
		return contactSecName;
	}

	public void setContactSecName(String contactSecName) {
		this.contactSecName = contactSecName;
	}

	public String getContactSecPhone() {
		return contactSecPhone;
	}

	public void setContactSecPhone(String contactSecPhone) {
		this.contactSecPhone = contactSecPhone;
	}

	public String getContactSecdescription() {
		return contactSecdescription;
	}

	public void setContactSecdescription(String contactSecdescription) {
		this.contactSecdescription = contactSecdescription;
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