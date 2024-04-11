package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.VendorDto;
import org.fleetopgroup.persistence.dto.VendorFixedFuelPriceDto;
import org.fleetopgroup.persistence.dto.VendorFixedPartPriceDto;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.model.VendorDocument;
import org.fleetopgroup.persistence.model.VendorFixedFuelPrice;
import org.fleetopgroup.persistence.model.VendorFixedPartPrice;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;


public interface IVendorService {

	public void addVendor(Vendor vendor);

	public void updateVendor(Vendor vendor);
	
	/**This Page get Vendor table to get  pagination values */
	public Page<Vendor> getDeployment_Page_Vendor(Long vendorType_Id, Integer pageNumber, Integer companyId);

	/**This List get Vendor table to get  pagination  last 10 entries values */
	public List<VendorDto> listVendor(Long vendorType_Id, Integer pageNumber, Integer companyId);

	public List<VendorDto> findAllVendorList(Integer companyid) throws Exception;

	public List<VendorDto> listVendor(String qurey, Integer companyId) throws Exception;

	public Vendor getVendor(int vendor_id);
	
	public VendorDto getVendorDetails(int vendor_id, Integer companyId) throws Exception;
	
	/** Select Vendor Details Fuel Entries */
	public Vendor getVendor_Details_Fuel_entries(int vendor_id);

	public List<Vendor> getVendorNametoall(String vendorname, Integer companyId);

	public void deleteVendor(Integer vendor, Integer companyId);

	public Long countVendor(Integer companyId) throws Exception;

	public List<VendorDto> SearchVendor(String Search, Integer companyId) throws Exception;
	
	public List<Vendor> SearchOnlyFuelVendorName(String term, Integer companyId) throws Exception;
	
	public List<Vendor> SearchOnly_PART_VendorName(String term, Integer companyId) throws Exception;
	
	public List<Vendor> SearchOnly_Other_VendorName(String term, Integer companyId) throws Exception;
	
	public List<Vendor> SearchOnly_TYRE_VendorName(String term, Integer companyId) throws Exception;
	
	public List<Vendor> SearchOnly_Battery_VendorName(String term, Integer companyId) throws Exception;
	
	public List<Vendor> SearchOnly_All_VendorName(String term, Integer companyId) throws Exception;
	
	public List<Vendor> SearchOnly_Cloth_VendorName(String term, Integer companyId) throws Exception;
	
	public List<Vendor> SearchOnly_Laundry_VendorName(String term, Integer companyId) throws Exception;
	
	public List<Vendor> SearchOnly_Urea_VendorName(String term, Integer companyId) throws Exception;

	public List<Vendor> SearchAll_TYRE_Vendor() throws Exception;
	
	/** Select Vendor Details Fuel Import Entries */
	public Vendor GetAllVendorListToFuelImportVendorID(String importVendorName, String Location, Integer companyId) throws Exception;

	/**
	 * @param userNameEmail
	 * @return
	 */
	public Vendor Get_UserEmailId_To_vendor_Details(Long id);

	/**
	 * @param validate
	 */
	public void ADD_VendorFixedPartPrice_IN_DB(VendorFixedPartPrice validate);


	/**
	 * @param parseLong
	 * @param vENDOR_ID
	 * @return
	 */
	public VendorFixedPartPrice Validate_Vendor_Fixed_Part_value(long parseLong, Integer vENDOR_ID, Integer companyId);

	/**
	 * @param vehicleID
	 * @param pageNumber
	 * @return
	 */
	public Page<VendorFixedPartPrice> Get_Deployment_Page_VendorFixedPartPrice(Integer vehicleID, Integer pageNumber, Integer companyId);

	/**
	 * @param vehicleID
	 * @param pageNumber
	 * @return
	 */
	public List<VendorFixedPartPriceDto> list_VendorFixedPartPrice_VehicleID(Integer vehicleID, Integer pageNumber, Integer companyId);

	/**
	 * @param vPID
	 */
	public void Delete_VendorFixedPartPrice_ID(Long vPID, Integer companyId);

	/**
	 * @param pART_ID
	 * @param vENDOTID
	 * @return
	 */
	public VendorFixedPartPrice Get_VendorFixedPrice_Validate_VendorID_PartId(Long pART_ID, Integer vENDOTID, Integer companyId);

	/**
	 * @param vENDOR_ID
	 * @return
	 */
	public List<org.fleetopgroup.persistence.document.VendorDocument> list_VendorDocument_IN_VendorId(Integer vENDOR_ID, Integer companyId);

	/**
	 * @param vendorDoc
	 */
	public void saveVendorDocument(org.fleetopgroup.persistence.document.VendorDocument vendorDoc);

	/**
	 * @param driver_documentid
	 * @return
	 */
	public org.fleetopgroup.persistence.document.VendorDocument getVendorDocuemnt(Long vDID, Integer companyId);

	/**
	 * @param vDID
	 */
	public void deleteVendorDocument(Long vDID, Integer companyId);

	/**
	 * @param vendorName
	 * @return
	 */
	public Object[] GET_VENDOR_CREDIT_AMOUNT_CountTotal_Cost_NotPaid(Integer vendor_id);

	
	/**
	 * @param vendorName
	 * @return
	 */
	public Page<VendorFixedFuelPrice> Get_Deployment_Page_VendorFixedFuelPrice(Integer vehicleID, Integer pageNumber, Integer companyId);

	public List<VendorFixedFuelPriceDto> list_VendorFixedFuelPrice_VehicleID(Integer vehicleID, Integer pageNumber, Integer companyId);

	public VendorFixedFuelPrice Validate_Vendor_Fixed_Fuel_value(Long fuel_ID, Date fuel_FIXED_DATE,
			Integer vendorid, Integer companyId);

	public void ADD_VendorFixed_FuelPrice_IN_DB(VendorFixedFuelPrice validateFuel);

	public void Delete_VendorFixed_FuelPrice_ID(Long vPID);

	public VendorFixedFuelPrice GET_VENDOR_DROPDOWN_FUEL_ADD_FIXED_DETAILS(Integer vendor_id, Long fuel_ID,
			java.sql.Date fuelDate, Integer companyId);

	public void transferVendorDocument(List<VendorDocument> vendorDocumentList) throws Exception;
	
	public long getVendorDocumentMaxId() throws Exception;
	
	Vendor	getLorryHireVendor(Integer vendorId) throws Exception;

	public List<Vendor> SearchOnly_All_VendorNameOnTripExpense(String term, Integer companyId) throws Exception;
	
	public Vendor getVendorIdFromNew(String vendorName, Integer companyId, String vendorType, String module) throws Exception;
	
	public ValueObject getOtherVendorSearchListForMobile(ValueObject object) throws Exception;

	public List<Vendor> searchVendorByNameAndType(String term, String vendorTypeId, Integer company_id)throws Exception;
	
	public List<Vendor> getOwnPetrolPumpList(Integer	companyId) throws Exception;
	
	public List<Vendor> SearchOnlyPetrolPumpVendorName(String term, Integer companyId) throws Exception;

	public List<Vendor> getVendorPanNo(String vendorPanNO, Integer company_id);
	
	public Vendor getVendorByName(String string, Integer company_Id) throws Exception;
	
}
