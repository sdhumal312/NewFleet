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
@Table(name = "partlocations")
public class PartLocations implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@Column(name = "createdBy", nullable = true)
	private String createdBy;

	@Column(name = "createdById", nullable = false)
	private Long createdById;
	
	@Column(name = "createdOn", nullable = false)
	private Timestamp createdOn;
	
	@Column(name = "lastModifiedBy", nullable = true)
	private String lastModifiedBy;

	@Column(name = "lastModifiedById", nullable = true)
	private Long lastModifiedById;
	
	@Column(name = "lastModifiedOn", nullable = true)
	private Timestamp lastModifiedOn;
	
	@Column(name = "markForDelete", nullable = false)
	private boolean markForDelete;

	@Column(name = "isAutoCreated", nullable = false)
	private boolean isAutoCreated;
	
	@Column(name = "mainPartLocationId")
	private Integer mainPartLocationId;
	
	@Column(name = "partLocationType")
	private short partLocationType;
	
	public PartLocations() {
		super();
	}

	public PartLocations(String partlocation_name, String partlocation_address, String partlocation_streetaddress,
			String partlocation_city, String partlocation_state, String partlocation_pincode,
			String partlocation_country, String shippartlocation_address, String shippartlocation_streetaddress,
			String shippartlocation_city, String shippartlocation_state, String shippartlocation_pincode,
			String shippartlocation_country, String partlocation_description, String contactFirName,
			String contactFirPhone, String contactFirdescription, String contactSecName, String contactSecPhone,
			String contactSecdescription,Integer companyId, String createdBy, Timestamp createdOn, String lastModifiedBy, Timestamp lastModifiedOn) {
		super();
		this.partlocation_name = partlocation_name;
		this.partlocation_address = partlocation_address;
		this.partlocation_streetaddress = partlocation_streetaddress;
		this.partlocation_city = partlocation_city;
		this.partlocation_state = partlocation_state;
		this.partlocation_pincode = partlocation_pincode;
		this.partlocation_country = partlocation_country;
		this.shippartlocation_address = shippartlocation_address;
		this.shippartlocation_streetaddress = shippartlocation_streetaddress;
		this.shippartlocation_city = shippartlocation_city;
		this.shippartlocation_state = shippartlocation_state;
		this.shippartlocation_pincode = shippartlocation_pincode;
		this.shippartlocation_country = shippartlocation_country;
		this.partlocation_description = partlocation_description;
		this.contactFirName = contactFirName;
		this.contactFirPhone = contactFirPhone;
		this.contactFirdescription = contactFirdescription;
		this.contactSecName = contactSecName;
		this.contactSecPhone = contactSecPhone;
		this.contactSecdescription = contactSecdescription;
		this.companyId = companyId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.lastModifiedBy = lastModifiedBy;
		this.lastModifiedOn = lastModifiedOn;
	}

	/**
	 * @return the partlocation_id
	 */
	public Integer getPartlocation_id() {
		return partlocation_id;
	}

	/**
	 * @param partlocation_id
	 *            the partlocation_id to set
	 */
	public void setPartlocation_id(Integer partlocation_id) {
		this.partlocation_id = partlocation_id;
	}

	/**
	 * @return the partlocation_name
	 */
	public String getPartlocation_name() {
		return partlocation_name;
	}

	/**
	 * @param partlocation_name
	 *            the partlocation_name to set
	 */
	public void setPartlocation_name(String partlocation_name) {
		this.partlocation_name = partlocation_name;
	}

	/**
	 * @return the partlocation_address
	 */
	public String getPartlocation_address() {
		return partlocation_address;
	}

	/**
	 * @param partlocation_address
	 *            the partlocation_address to set
	 */
	public void setPartlocation_address(String partlocation_address) {
		this.partlocation_address = partlocation_address;
	}

	/**
	 * @return the partlocation_streetaddress
	 */
	public String getPartlocation_streetaddress() {
		return partlocation_streetaddress;
	}

	/**
	 * @param partlocation_streetaddress
	 *            the partlocation_streetaddress to set
	 */
	public void setPartlocation_streetaddress(String partlocation_streetaddress) {
		this.partlocation_streetaddress = partlocation_streetaddress;
	}

	/**
	 * @return the partlocation_cityaddress
	 */
	public String getPartlocation_city() {
		return partlocation_city;
	}

	/**
	 * @param partlocation_cityaddress
	 *            the partlocation_cityaddress to set
	 */
	public void setPartlocation_city(String partlocation_city) {
		this.partlocation_city = partlocation_city;
	}

	/**
	 * @return the partlocation_state
	 */
	public String getPartlocation_state() {
		return partlocation_state;
	}

	/**
	 * @param partlocation_state
	 *            the partlocation_state to set
	 */
	public void setPartlocation_state(String partlocation_state) {
		this.partlocation_state = partlocation_state;
	}

	/**
	 * @return the partlocation_pincode
	 */
	public String getPartlocation_pincode() {
		return partlocation_pincode;
	}

	/**
	 * @param partlocation_pincode
	 *            the partlocation_pincode to set
	 */
	public void setPartlocation_pincode(String partlocation_pincode) {
		this.partlocation_pincode = partlocation_pincode;
	}

	/**
	 * @return the partlocation_country
	 */
	public String getPartlocation_country() {
		return partlocation_country;
	}

	/**
	 * @param partlocation_country
	 *            the partlocation_country to set
	 */
	public void setPartlocation_country(String partlocation_country) {
		this.partlocation_country = partlocation_country;
	}

	/**
	 * @return the shippartlocation_address
	 */
	public String getShippartlocation_address() {
		return shippartlocation_address;
	}

	/**
	 * @param shippartlocation_address
	 *            the shippartlocation_address to set
	 */
	public void setShippartlocation_address(String shippartlocation_address) {
		this.shippartlocation_address = shippartlocation_address;
	}

	/**
	 * @return the shippartlocation_streetaddress
	 */
	public String getShippartlocation_streetaddress() {
		return shippartlocation_streetaddress;
	}

	/**
	 * @param shippartlocation_streetaddress
	 *            the shippartlocation_streetaddress to set
	 */
	public void setShippartlocation_streetaddress(String shippartlocation_streetaddress) {
		this.shippartlocation_streetaddress = shippartlocation_streetaddress;
	}

	/**
	 * @return the shippartlocation_city
	 */
	public String getShippartlocation_city() {
		return shippartlocation_city;
	}

	/**
	 * @param shippartlocation_city
	 *            the shippartlocation_city to set
	 */
	public void setShippartlocation_city(String shippartlocation_city) {
		this.shippartlocation_city = shippartlocation_city;
	}

	/**
	 * @return the shippartlocation_state
	 */
	public String getShippartlocation_state() {
		return shippartlocation_state;
	}

	/**
	 * @param shippartlocation_state
	 *            the shippartlocation_state to set
	 */
	public void setShippartlocation_state(String shippartlocation_state) {
		this.shippartlocation_state = shippartlocation_state;
	}

	/**
	 * @return the shippartlocation_pincode
	 */
	public String getShippartlocation_pincode() {
		return shippartlocation_pincode;
	}

	/**
	 * @param shippartlocation_pincode
	 *            the shippartlocation_pincode to set
	 */
	public void setShippartlocation_pincode(String shippartlocation_pincode) {
		this.shippartlocation_pincode = shippartlocation_pincode;
	}

	/**
	 * @return the shippartlocation_country
	 */
	public String getShippartlocation_country() {
		return shippartlocation_country;
	}

	/**
	 * @param shippartlocation_country
	 *            the shippartlocation_country to set
	 */
	public void setShippartlocation_country(String shippartlocation_country) {
		this.shippartlocation_country = shippartlocation_country;
	}

	/**
	 * @return the partlocation_description
	 */
	public String getPartlocation_description() {
		return partlocation_description;
	}

	/**
	 * @param partlocation_description
	 *            the partlocation_description to set
	 */
	public void setPartlocation_description(String partlocation_description) {
		this.partlocation_description = partlocation_description;
	}

	/**
	 * @return the contactFirName
	 */
	public String getContactFirName() {
		return contactFirName;
	}

	/**
	 * @param contactFirName
	 *            the contactFirName to set
	 */
	public void setContactFirName(String contactFirName) {
		this.contactFirName = contactFirName;
	}

	/**
	 * @return the contactFirPhone
	 */
	public String getContactFirPhone() {
		return contactFirPhone;
	}

	/**
	 * @param contactFirPhone
	 *            the contactFirPhone to set
	 */
	public void setContactFirPhone(String contactFirPhone) {
		this.contactFirPhone = contactFirPhone;
	}

	/**
	 * @return the contactFirdescription
	 */
	public String getContactFirdescription() {
		return contactFirdescription;
	}

	/**
	 * @param contactFirdescription
	 *            the contactFirdescription to set
	 */
	public void setContactFirdescription(String contactFirdescription) {
		this.contactFirdescription = contactFirdescription;
	}

	/**
	 * @return the contactSecName
	 */
	public String getContactSecName() {
		return contactSecName;
	}

	/**
	 * @param contactSecName
	 *            the contactSecName to set
	 */
	public void setContactSecName(String contactSecName) {
		this.contactSecName = contactSecName;
	}

	/**
	 * @return the contactSecPhone
	 */
	public String getContactSecPhone() {
		return contactSecPhone;
	}

	/**
	 * @param contactSecPhone
	 *            the contactSecPhone to set
	 */
	public void setContactSecPhone(String contactSecPhone) {
		this.contactSecPhone = contactSecPhone;
	}

	/**
	 * @return the contactSecdescription
	 */
	public String getContactSecdescription() {
		return contactSecdescription;
	}

	/**
	 * @param contactSecdescription
	 *            the contactSecdescription to set
	 */
	public void setContactSecdescription(String contactSecdescription) {
		this.contactSecdescription = contactSecdescription;
	}
	
	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
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

	public boolean isAutoCreated() {
		return isAutoCreated;
	}

	public void setAutoCreated(boolean isAutoCreated) {
		this.isAutoCreated = isAutoCreated;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
	}

	public Long getLastModifiedById() {
		return lastModifiedById;
	}

	public void setLastModifiedById(Long lastModifiedById) {
		this.lastModifiedById = lastModifiedById;
	}

	
	
	public Integer getMainPartLocationId() {
		return mainPartLocationId;
	}

	public void setMainPartLocationId(Integer mainPartLocationId) {
		this.mainPartLocationId = mainPartLocationId;
	}

	public short getPartLocationType() {
		return partLocationType;
	}

	public void setPartLocationType(short partLocationType) {
		this.partLocationType = partLocationType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contactFirName == null) ? 0 : contactFirName.hashCode());
		result = prime * result + ((contactFirPhone == null) ? 0 : contactFirPhone.hashCode());
		result = prime * result + ((contactSecName == null) ? 0 : contactSecName.hashCode());
		result = prime * result + ((contactSecPhone == null) ? 0 : contactSecPhone.hashCode());
		result = prime * result + ((partlocation_city == null) ? 0 : partlocation_city.hashCode());
		result = prime * result + ((partlocation_name == null) ? 0 : partlocation_name.hashCode());
		result = prime * result + ((shippartlocation_city == null) ? 0 : shippartlocation_city.hashCode());
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
		PartLocations other = (PartLocations) obj;
		if (contactFirName == null) {
			if (other.contactFirName != null)
				return false;
		} else if (!contactFirName.equals(other.contactFirName))
			return false;
		if (contactFirPhone == null) {
			if (other.contactFirPhone != null)
				return false;
		} else if (!contactFirPhone.equals(other.contactFirPhone))
			return false;
		if (contactSecName == null) {
			if (other.contactSecName != null)
				return false;
		} else if (!contactSecName.equals(other.contactSecName))
			return false;
		if (contactSecPhone == null) {
			if (other.contactSecPhone != null)
				return false;
		} else if (!contactSecPhone.equals(other.contactSecPhone))
			return false;
		if (partlocation_city == null) {
			if (other.partlocation_city != null)
				return false;
		} else if (!partlocation_city.equals(other.partlocation_city))
			return false;
		if (partlocation_name == null) {
			if (other.partlocation_name != null)
				return false;
		} else if (!partlocation_name.equals(other.partlocation_name))
			return false;
		if (shippartlocation_city == null) {
			if (other.shippartlocation_city != null)
				return false;
		} else if (!shippartlocation_city.equals(other.shippartlocation_city))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PartLocations [partlocation_name=");
		builder.append(partlocation_name);
		builder.append(", partlocation_address=");
		builder.append(partlocation_address);
		builder.append(", partlocation_streetaddress=");
		builder.append(partlocation_streetaddress);
		builder.append(", partlocation_city=");
		builder.append(partlocation_city);
		builder.append(", partlocation_state=");
		builder.append(partlocation_state);
		builder.append(", partlocation_pincode=");
		builder.append(partlocation_pincode);
		builder.append(", partlocation_country=");
		builder.append(partlocation_country);
		builder.append(", shippartlocation_address=");
		builder.append(shippartlocation_address);
		builder.append(", shippartlocation_streetaddress=");
		builder.append(shippartlocation_streetaddress);
		builder.append(", shippartlocation_city=");
		builder.append(shippartlocation_city);
		builder.append(", shippartlocation_state=");
		builder.append(shippartlocation_state);
		builder.append(", shippartlocation_pincode=");
		builder.append(shippartlocation_pincode);
		builder.append(", shippartlocation_country=");
		builder.append(shippartlocation_country);
		builder.append(", partlocation_description=");
		builder.append(partlocation_description);
		builder.append(", contactFirName=");
		builder.append(contactFirName);
		builder.append(", contactFirPhone=");
		builder.append(contactFirPhone);
		builder.append(", contactFirdescription=");
		builder.append(contactFirdescription);
		builder.append(", contactSecName=");
		builder.append(contactSecName);
		builder.append(", contactSecPhone=");
		builder.append(contactSecPhone);
		builder.append(", contactSecdescription=");
		builder.append(contactSecdescription);
		builder.append(", company_id=");
		builder.append(companyId);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", lastModifiedOn=");
		builder.append(lastModifiedOn);
		builder.append(", mainPartLocationId=");
		builder.append(mainPartLocationId);
		builder.append(", partLocationType=");
		builder.append(partLocationType);
		builder.append("]");
		return builder.toString();
	}

}