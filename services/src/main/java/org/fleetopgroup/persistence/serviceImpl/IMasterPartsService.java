package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.ChildPartDetailsDto;
import org.fleetopgroup.persistence.dto.LowStockInventoryDto;
import org.fleetopgroup.persistence.dto.MasterPartsDto;
import org.fleetopgroup.persistence.dto.MasterPartsExtraDetailsDto;
import org.fleetopgroup.persistence.dto.MasterPartsLocationDto;
import org.fleetopgroup.persistence.dto.MasterPartsToVehicleMakerDto;
import org.fleetopgroup.persistence.dto.MasterPartsToVehicleModalDto;
import org.fleetopgroup.persistence.dto.PartPurchaseVendorDto;
import org.fleetopgroup.persistence.dto.RepairableVendorDetailsDto;
import org.fleetopgroup.persistence.dto.SubstitudePartDetailsDto;
import org.fleetopgroup.persistence.model.LowStockInventory;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.MasterPartsLocation;
import org.fleetopgroup.persistence.model.MasterPartsPhoto;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;


public interface IMasterPartsService {

	public void addMasterParts(MasterParts status) throws Exception;

	public void updateMasterParts(MasterParts status) throws Exception;

	public void addMasterPartsLocation(MasterPartsLocation status) throws Exception;

	public void updateMasterPartsLocation(MasterPartsLocation status) throws Exception;

	/**This List get MasterParts table to get  pagination  last 10 entries values */
	public List<MasterPartsDto> listMasterParts(Integer pageNumber, Integer companyId) throws Exception;

	public List<MasterPartsDto> listMasterPartsByCompanyId(Integer companyId) throws Exception;
	
	/**This Page get Vehicle table to get  pagination values */
	public Page<MasterParts> getDeployment_Page_MasterParts(Integer pageNumber, Integer companyId)throws Exception;


	public List<MasterParts> findAllMasterParts() throws Exception;

	public List<MasterPartsDto> SearchMasterParts(String search, Integer companyId) throws Exception;
	
	public List<MasterPartsDto> ReportMasterParts(String searchReport, Integer companyId) throws Exception;
	
	//drop down Inventory Master Parts
	public List<MasterPartsDto> SearchInventorySelectParts(String search, Integer companyId) throws Exception;
	
	public List<MasterPartsDto> SearchInventorySelectPartsOnType(String search, Integer companyId, short type) throws Exception;

	public MasterParts getMasterParts(Long part_id) throws Exception;
	
	public MasterPartsDto getMasterParts(Long part_id, Integer companyId) throws Exception;

	public List<MasterPartsLocationDto> getMasterPartsLocation(Long partid, Integer companyId) throws Exception;

	public void deleteMasterParts(Long part_id, Integer companyId) throws Exception;

	public void deleteMasterPartsLocation(Long status, Integer companyId) throws Exception;

	public List<MasterParts> listOnlyStatus() throws Exception;

	public Long countpart(Integer companyId) throws Exception;

	// Show Inventory Location Row , bin get the value in MasterMartLocation Dao
	public List<MasterPartsLocationDto> getInventoryMasterPartsLocation(Long sid, Integer Location_ID, Integer companyId) throws Exception;

	// parts photo to get part_id
	public List<org.fleetopgroup.persistence.document.MasterPartsPhoto> listMasterPartsPhoto(Long partid, Integer companyId) throws Exception;

	// save Master Photo
	public void addMasterPartsPhoto(org.fleetopgroup.persistence.document.MasterPartsPhoto masterPartsPhoto) throws Exception;

	// Get Part Photo_id in MasterParts
	public org.fleetopgroup.persistence.document.MasterPartsPhoto getMasterPartsPhoto(Long sid) throws Exception;

	// Delete MasterPart Photo
	public void deleteMasterPartsPhoto(Long Part_photoid, Integer companyId) throws Exception;

	// update Master Part Profile photo_id 
	public void setMasterPartProfilePhoto(Long Part_photoid, Long PartId) throws Exception;
	
	// master part validate
	public List<MasterParts> GetMasterPartValidate(String  Part_No, String Part_name, Integer companyId) throws Exception;

	
	
	public MasterParts validatePartNumber(String partnumber,String partName, Long makerId, Integer companyId) throws Exception;

	public void transferMasterPartsPhoto(List<MasterPartsPhoto> masterPartsPhotoList) throws Exception;
	
	public long getMasterPartsPhotoMaxId() throws Exception;
	
	public void saveLowStockLevel(LowStockInventory	lowStockInventory) throws Exception;

	public MasterPartsLocation validateMasterPartLocation(Long partId, Integer locationId, Integer companyId) throws Exception;

	public void updateLowStockLevel(LowStockInventory lowStockInventory) throws Exception;
	
	public  LowStockInventory validateLowStockInventory(Long partId, Integer companyId, Integer locationId) throws Exception;
	
	public ValueObject	saveNewMasterPartsDetails(ValueObject	valueObject) throws Exception;
	
	public MasterPartsExtraDetailsDto  getMasterPartsExtraDetailsByPartId(Long partId, Integer companyId) throws Exception;
	
	public List<ChildPartDetailsDto>  getChildPartDetailsByPartId(Long partId, Integer companyId) throws Exception;
	
	public List<ChildPartDetailsDto>  getParentPartDetailsByPartId(Long partId, Integer companyId) throws Exception;
	
	public List<RepairableVendorDetailsDto> getRepairableVendorDetailsByPartId(Long partId, Integer companyId) throws Exception;
	
	public List<PartPurchaseVendorDto> getPurchaseVendorDetailsByPartId(Long partId, Integer companyId) throws Exception;
	
	public List<SubstitudePartDetailsDto>	getSubstitudePartDetailsByPartId(Long partId, Integer companyId) throws Exception;
	
	public ValueObject	getMasterPartsDetailsForEdit(ValueObject	valueObject) throws Exception;
	
	public ValueObject	updateNewMasterPartsDetails(ValueObject	valueObject) throws Exception;
	
	public List<LowStockInventoryDto>  getLowStockInventoryByPartId(Long partId, Integer companyId) throws Exception;
	
	public ValueObject	removeLowStockDetails(ValueObject	valueObject) throws Exception;
	
	public Long getPhotoCount(Long partId, Integer companyId) throws Exception;

	public List<MasterPartsDto> searchAllMasterParts(String term, Integer company_id) throws Exception;
	
	public List<MasterPartsToVehicleModalDto>  getMasterPartsToVehicleModalList(Long partId, Integer companyId) throws Exception;

	public List<MasterPartsToVehicleMakerDto>  getMasterPartsToVehicleMakerList(Long partId, Integer companyId) throws Exception;

}