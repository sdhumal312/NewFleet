package org.fleetopgroup.persistence.dto;

import java.sql.Timestamp;

public class PartLocationsDto {
	
	private Integer partlocation_id;
	
	private String partlocation_name;

	private String partlocation_address;

	private String partlocation_streetaddress;

	private String partlocation_city;

	private String partlocation_state;

	private String partlocation_pincode;

	private String partlocation_country;

	private String shippartlocation_address;

	private String shippartlocation_streetaddress;

	private String shippartlocation_city;

	private String shippartlocation_state;

	private String shippartlocation_pincode;

	private String shippartlocation_country;

	private String partlocation_description;

	private String contactFirName;

	private String contactFirPhone;

	private String contactFirdescription;

	private String contactSecName;

	private String contactSecPhone;

	private String contactSecdescription;
	
	private Integer companyId;

	private String createdBy;

	private Long createdById;
	
	private Timestamp createdOn;
	
	private String lastModifiedBy;

	private Long lastModifiedById;
	
	private Timestamp lastModifiedOn;
	
	private boolean markForDelete;

	private boolean isAutoCreated;
	
	private Integer mainPartLocationId;
	
	private short partLocationType;
	
	private String mainPartLocation;
	
	private String partLocationTypeStr;

	public PartLocationsDto() {
		super();
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Long getCreatedById() {
		return createdById;
	}

	public void setCreatedById(Long createdById) {
		this.createdById = createdById;
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

	public boolean isAutoCreated() {
		return isAutoCreated;
	}

	public void setAutoCreated(boolean isAutoCreated) {
		this.isAutoCreated = isAutoCreated;
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

	public String getMainPartLocation() {
		return mainPartLocation;
	}

	public void setMainPartLocation(String mainPartLocation) {
		this.mainPartLocation = mainPartLocation;
	}

	public String getPartLocationTypeStr() {
		return partLocationTypeStr;
	}

	public void setPartLocationTypeStr(String partLocationTypeStr) {
		this.partLocationTypeStr = partLocationTypeStr;
	}
	
	
	
	
	
}