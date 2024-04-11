package org.fleetopgroup.persistence.dto;


public class CompanyDto{

	private Integer company_id;
	
	private String company_id_encode;

	private String company_name;
	
	private String companyCode;

	private String company_address;

	private String company_city;

	private String company_state;

	private String company_country;

	private String company_pincode;

	private String company_website;

	private String company_email;

	private String company_mobilenumber;

	private String company_type;

	private String company_pan_no;

	private String company_tan_no;

	private String company_tax_no;

	private String company_tin_no;

	private String company_cin_no;

	private String company_abount;

	private String company_parentName;

	private Integer company_parent_id;
	
	private Long company_esi_pf_days;

	private Integer company_esi_pf_disable;

	private String company_status;
	
	private String createdBy;

	private String lastModifiedBy;
	
	private long tripSheetCount;
	
	private long serviceEntriesCount;
	
	private long workOrderCount;
	
	private long serviceReminderCount;
	
	private long renewalReminderCount;
	
	private long fuelEntriesCount;
	
	private long totalTripSheetCount;
	
	private long totalServiceEntriesCount;
	
	private long totalWorkOrderCount;
	
	private long  totalServiceReminderCount;
	
	private long totalRenewalReminderCount;
	
	private long totalFuelEntriesCount;
	
	private long totalActiveVehicleCount;
	
	private long totalInActiveVehicleCount;
	
	private long totalSoldVehicleCount;
	
	

	public CompanyDto() {
		super();
	}

	public CompanyDto(String company_name, String company_address, String company_city, String company_state,
			String company_country, String company_pincode, String company_website, String company_email,
			String company_mobilenumber, String company_type, String company_pan_no, String company_tan_no,
			String company_tax_no, String company_tin_no, String company_cin_no, String company_abount,
			String company_status) {
		super();
		this.company_name = company_name;
		this.company_address = company_address;
		this.company_city = company_city;
		this.company_state = company_state;
		this.company_country = company_country;
		this.company_pincode = company_pincode;
		this.company_website = company_website;
		this.company_email = company_email;
		this.company_mobilenumber = company_mobilenumber;
		this.company_type = company_type;
		this.company_pan_no = company_pan_no;
		this.company_tan_no = company_tan_no;
		this.company_tax_no = company_tax_no;
		this.company_tin_no = company_tin_no;
		this.company_cin_no = company_cin_no;
		this.company_abount = company_abount;
		this.company_status = company_status;
	}

	
	
	public Integer getCompany_id() {
		return company_id;
	}

	public void setCompany_id(Integer company_id) {
		this.company_id = company_id;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getCompany_address() {
		return company_address;
	}

	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}

	public String getCompany_city() {
		return company_city;
	}

	public void setCompany_city(String company_city) {
		this.company_city = company_city;
	}

	public String getCompany_state() {
		return company_state;
	}

	public void setCompany_state(String company_state) {
		this.company_state = company_state;
	}

	public String getCompany_country() {
		return company_country;
	}

	public void setCompany_country(String company_country) {
		this.company_country = company_country;
	}

	public String getCompany_pincode() {
		return company_pincode;
	}

	public void setCompany_pincode(String company_pincode) {
		this.company_pincode = company_pincode;
	}

	public String getCompany_website() {
		return company_website;
	}

	public void setCompany_website(String company_website) {
		this.company_website = company_website;
	}

	public String getCompany_email() {
		return company_email;
	}

	public void setCompany_email(String company_email) {
		this.company_email = company_email;
	}

	public String getCompany_mobilenumber() {
		return company_mobilenumber;
	}

	public void setCompany_mobilenumber(String company_mobilenumber) {
		this.company_mobilenumber = company_mobilenumber;
	}

	public String getCompany_type() {
		return company_type;
	}

	public void setCompany_type(String company_type) {
		this.company_type = company_type;
	}

	public String getCompany_pan_no() {
		return company_pan_no;
	}

	public void setCompany_pan_no(String company_pan_no) {
		this.company_pan_no = company_pan_no;
	}

	public String getCompany_tan_no() {
		return company_tan_no;
	}

	public void setCompany_tan_no(String company_tan_no) {
		this.company_tan_no = company_tan_no;
	}

	public String getCompany_tax_no() {
		return company_tax_no;
	}

	public void setCompany_tax_no(String company_tax_no) {
		this.company_tax_no = company_tax_no;
	}

	public String getCompany_tin_no() {
		return company_tin_no;
	}

	public void setCompany_tin_no(String company_tin_no) {
		this.company_tin_no = company_tin_no;
	}

	public String getCompany_cin_no() {
		return company_cin_no;
	}

	public void setCompany_cin_no(String company_cin_no) {
		this.company_cin_no = company_cin_no;
	}

	public String getCompany_abount() {
		return company_abount;
	}

	public void setCompany_abount(String company_abount) {
		this.company_abount = company_abount;
	}

	public String getCompany_parentName() {
		return company_parentName;
	}

	public void setCompany_parentName(String company_parentName) {
		this.company_parentName = company_parentName;
	}

	public Integer getCompany_parent_id() {
		return company_parent_id;
	}

	public void setCompany_parent_id(Integer company_parent_id) {
		this.company_parent_id = company_parent_id;
	}

	public String getCompany_status() {
		return company_status;
	}

	public void setCompany_status(String company_status) {
		this.company_status = company_status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLastModifiedBy() {
		return lastModifiedBy;
	}

	public void setLastModifiedBy(String lastModifiedBy) {
		this.lastModifiedBy = lastModifiedBy;
	}

	
	public String getCompany_id_encode() {
		return company_id_encode;
	}

	public void setCompany_id_encode(String company_id_encode) {
		this.company_id_encode = company_id_encode;
	}
	
	
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the company_esi_pf_days
	 */
	public Long getCompany_esi_pf_days() {
		return company_esi_pf_days;
	}

	/**
	 * @param company_esi_pf_days the company_esi_pf_days to set
	 */
	public void setCompany_esi_pf_days(Long company_esi_pf_days) {
		this.company_esi_pf_days = company_esi_pf_days;
	}

	/**
	 * @return the company_esi_pf_disable
	 */
	public Integer getCompany_esi_pf_disable() {
		return company_esi_pf_disable;
	}

	/**
	 * @param company_esi_pf_disable the company_esi_pf_disable to set
	 */
	public void setCompany_esi_pf_disable(Integer company_esi_pf_disable) {
		this.company_esi_pf_disable = company_esi_pf_disable;
	}
	

	public long getTripSheetCount() {
		return tripSheetCount;
	}

	public void setTripSheetCount(long tripSheetCount) {
		this.tripSheetCount = tripSheetCount;
	}

	public long getServiceEntriesCount() {
		return serviceEntriesCount;
	}

	public void setServiceEntriesCount(long serviceEntriesCount) {
		this.serviceEntriesCount = serviceEntriesCount;
	}

	public long getWorkOrderCount() {
		return workOrderCount;
	}

	public void setWorkOrderCount(long workOrderCount) {
		this.workOrderCount = workOrderCount;
	}

	public long getServiceReminderCount() {
		return serviceReminderCount;
	}

	public void setServiceReminderCount(long serviceReminderCount) {
		this.serviceReminderCount = serviceReminderCount;
	}

	public long getRenewalReminderCount() {
		return renewalReminderCount;
	}

	public void setRenewalReminderCount(long renewalReminderCount) {
		this.renewalReminderCount = renewalReminderCount;
	}

	public long getFuelEntriesCount() {
		return fuelEntriesCount;
	}

	public void setFuelEntriesCount(long fuelEntriesCount) {
		this.fuelEntriesCount = fuelEntriesCount;
	}

	public long getTotalTripSheetCount() {
		return totalTripSheetCount;
	}

	public void setTotalTripSheetCount(long totalTripSheetCount) {
		this.totalTripSheetCount = totalTripSheetCount;
	}

	public long getTotalServiceEntriesCount() {
		return totalServiceEntriesCount;
	}

	public void setTotalServiceEntriesCount(long totalServiceEntriesCount) {
		this.totalServiceEntriesCount = totalServiceEntriesCount;
	}

	public long getTotalWorkOrderCount() {
		return totalWorkOrderCount;
	}

	public void setTotalWorkOrderCount(long totalWorkOrderCount) {
		this.totalWorkOrderCount = totalWorkOrderCount;
	}

	public long getTotalServiceReminderCount() {
		return totalServiceReminderCount;
	}

	public void setTotalServiceReminderCount(long totalServiceReminderCount) {
		this.totalServiceReminderCount = totalServiceReminderCount;
	}

	public long getTotalRenewalReminderCount() {
		return totalRenewalReminderCount;
	}

	public void setTotalRenewalReminderCount(long totalRenewalReminderCount) {
		this.totalRenewalReminderCount = totalRenewalReminderCount;
	}

	public long getTotalFuelEntriesCount() {
		return totalFuelEntriesCount;
	}

	public void setTotalFuelEntriesCount(long totalFuelEntriesCount) {
		this.totalFuelEntriesCount = totalFuelEntriesCount;
	}

	public long getTotalActiveVehicleCount() {
		return totalActiveVehicleCount;
	}

	public void setTotalActiveVehicleCount(long totalActiveVehicleCount) {
		this.totalActiveVehicleCount = totalActiveVehicleCount;
	}

	public long getTotalInActiveVehicleCount() {
		return totalInActiveVehicleCount;
	}

	public void setTotalInActiveVehicleCount(long totalInActiveVehicleCount) {
		this.totalInActiveVehicleCount = totalInActiveVehicleCount;
	}

	public long getTotalSoldVehicleCount() {
		return totalSoldVehicleCount;
	}

	public void setTotalSoldVehicleCount(long totalSoldVehicleCount) {
		this.totalSoldVehicleCount = totalSoldVehicleCount;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CompanyDto [company_id=");
		builder.append(company_id);
		builder.append(", company_id_encode=");
		builder.append(company_id_encode);
		builder.append(", company_name=");
		builder.append(company_name);
		builder.append(", companyCode=");
		builder.append(companyCode);
		builder.append(", company_address=");
		builder.append(company_address);
		builder.append(", company_city=");
		builder.append(company_city);
		builder.append(", company_state=");
		builder.append(company_state);
		builder.append(", company_country=");
		builder.append(company_country);
		builder.append(", company_pincode=");
		builder.append(company_pincode);
		builder.append(", company_website=");
		builder.append(company_website);
		builder.append(", company_email=");
		builder.append(company_email);
		builder.append(", company_mobilenumber=");
		builder.append(company_mobilenumber);
		builder.append(", company_type=");
		builder.append(company_type);
		builder.append(", company_pan_no=");
		builder.append(company_pan_no);
		builder.append(", company_tan_no=");
		builder.append(company_tan_no);
		builder.append(", company_tax_no=");
		builder.append(company_tax_no);
		builder.append(", company_tin_no=");
		builder.append(company_tin_no);
		builder.append(", company_cin_no=");
		builder.append(company_cin_no);
		builder.append(", company_abount=");
		builder.append(company_abount);
		builder.append(", company_parentName=");
		builder.append(company_parentName);
		builder.append(", company_parent_id=");
		builder.append(company_parent_id);
		builder.append(", company_esi_pf_days=");
		builder.append(company_esi_pf_days);
		builder.append(", company_esi_pf_disable=");
		builder.append(company_esi_pf_disable);
		builder.append(", company_status=");
		builder.append(company_status);
		builder.append(", createdBy=");
		builder.append(createdBy);
		builder.append(", lastModifiedBy=");
		builder.append(lastModifiedBy);
		builder.append(", tripSheetCount=");
		builder.append(tripSheetCount);
		builder.append(", serviceEntriesCount=");
		builder.append(serviceEntriesCount);
		builder.append(", workOrderCount=");
		builder.append(workOrderCount);
		builder.append(", serviceReminderCount=");
		builder.append(serviceReminderCount);
		builder.append(", renewalReminderCount=");
		builder.append(renewalReminderCount);
		builder.append(", fuelEntriesCount=");
		builder.append(fuelEntriesCount);
		builder.append(", totalTripSheetCount=");
		builder.append(totalTripSheetCount);
		builder.append(", totalServiceEntriesCount=");
		builder.append(totalServiceEntriesCount);
		builder.append(", totalWorkOrderCount=");
		builder.append(totalWorkOrderCount);
		builder.append(", totalServiceReminderCount=");
		builder.append(totalServiceReminderCount);
		builder.append(", totalRenewalReminderCount=");
		builder.append(totalRenewalReminderCount);
		builder.append(", totalFuelEntriesCount=");
		builder.append(totalFuelEntriesCount);
		builder.append(", totalActiveVehicleCount=");
		builder.append(totalActiveVehicleCount);
		builder.append(", totalInActiveVehicleCount=");
		builder.append(totalInActiveVehicleCount);
		builder.append(", totalSoldVehicleCount=");
		builder.append(totalSoldVehicleCount);
		builder.append("]");
		return builder.toString();
	}

	

}
