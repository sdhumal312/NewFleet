package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.constant.PartType;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.MasterPartsDto;
import org.fleetopgroup.persistence.dto.MasterPartsLocationDto;
import org.fleetopgroup.persistence.model.LowStockInventory;
import org.fleetopgroup.persistence.model.MasterPartRate;
import org.fleetopgroup.persistence.model.MasterPartRateHistory;
import org.fleetopgroup.persistence.model.MasterParts;
import org.fleetopgroup.persistence.model.MasterPartsExtraDetails;
import org.fleetopgroup.persistence.model.MasterPartsLocation;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.security.core.context.SecurityContextHolder;

public class MasterPartsBL {

	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	public MasterPartsBL() {
	}

	// save the MasterParts Model
	public MasterParts prepareModel(MasterParts MasterPartsBean) {
		MasterParts part = new MasterParts();
		part.setPartid(MasterPartsBean.getPartid());
		part.setPartnumber(MasterPartsBean.getPartnumber());
		part.setPartname(MasterPartsBean.getPartname());
		part.setDescription(MasterPartsBean.getDescription());
		//part.setLowstocklevel(MasterPartsBean.getLowstocklevel());
		//part.setReorderquantity(MasterPartsBean.getReorderquantity());
		part.setUnitTypeId(MasterPartsBean.getUnitTypeId());
		part.setPartTypeId(MasterPartsBean.getPartTypeId());
		part.setCategoryId(MasterPartsBean.getCategoryId());
		part.setMakerId(MasterPartsBean.getMakerId());
		part.setUnitTypeId(MasterPartsBean.getUnitTypeId());
		part.setPartManufacturerType(MasterPartsBean.getPartManufacturerType());
		part.setMarkForDelete(false);
		/** who Create this vehicle get email id to user profile details */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		part.setCompanyId(userDetails.getCompany_id());

		part.setCreatedById(userDetails.getId());
		part.setLastModifiedById(userDetails.getId());

		Date currentDateUpdate = new Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		part.setCreated(toDate);
		part.setLastupdated(toDate);

		part.setPart_photoid(MasterPartsBean.getPart_photoid());
		part.setRefreshment(MasterPartsBean.isRefreshment());

		return part;
	}
	
	public LowStockInventory getLowStockInventory(MasterPartsDto MasterPartsBean) {
		LowStockInventory	lowStockInventory	= null;
		CustomUserDetails	userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			lowStockInventory	= new LowStockInventory();
			
			lowStockInventory.setLowStockInventoryId(MasterPartsBean.getLowStockInventoryId());
			lowStockInventory.setPartid(MasterPartsBean.getPartid());
			lowStockInventory.setLowstocklevel(MasterPartsBean.getLowstocklevel());
			lowStockInventory.setReorderquantity(MasterPartsBean.getReorderquantity());
			lowStockInventory.setCompanyId(userDetails.getCompany_id());
			lowStockInventory.setCreatedById(userDetails.getId());
			lowStockInventory.setLastModifiedById(userDetails.getId());
			lowStockInventory.setCreated(new Timestamp(System.currentTimeMillis()));
			lowStockInventory.setLastupdated(new Timestamp(System.currentTimeMillis()));
			lowStockInventory.setUnitCost(MasterPartsBean.getUnitCost());
			lowStockInventory.setDiscount(MasterPartsBean.getDiscount());
			lowStockInventory.setTax(MasterPartsBean.getTax());
			lowStockInventory.setLocationId(MasterPartsBean.getLocationId());
			
			return lowStockInventory;
		} catch (Exception e) {
			throw e;
		}finally {
			lowStockInventory	= null;
		}
	}
	
	public MasterPartRate getPartPrice(MasterPartsDto MasterPartsBean) {
		MasterPartRate	lowStockInventory	= null;
		CustomUserDetails	userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			lowStockInventory	= new MasterPartRate();
			
			lowStockInventory.setPartId(MasterPartsBean.getPartid());
			lowStockInventory.setUnitCost(MasterPartsBean.getUnitCost());
			lowStockInventory.setDiscount(MasterPartsBean.getDiscount());
			lowStockInventory.setTax(MasterPartsBean.getTax());
			lowStockInventory.setCompanyId(userDetails.getCompany_id());
			lowStockInventory.setCreatedById(userDetails.getId());
			lowStockInventory.setLastModifiedById(userDetails.getId());
			lowStockInventory.setCreated(new Timestamp(System.currentTimeMillis()));
			lowStockInventory.setLastupdated(new Timestamp(System.currentTimeMillis()));
			
			
			return lowStockInventory;
		} catch (Exception e) {
			throw e;
		}finally {
			lowStockInventory	= null;
		}
	}
	
	public MasterPartRate getPartPrice(ValueObject	valueObject, MasterParts	masterParts) {
		MasterPartRate	lowStockInventory	= null;
		try {
			lowStockInventory	= new MasterPartRate();
			
			lowStockInventory.setPartId(masterParts.getPartid());
			lowStockInventory.setUnitCost(valueObject.getDouble("unitPrice",0));
			lowStockInventory.setDiscount(valueObject.getDouble("discount",0));
			lowStockInventory.setTax(valueObject.getDouble("tax",0));
			lowStockInventory.setCompanyId(masterParts.getCompanyId());
			lowStockInventory.setCreatedById(masterParts.getCreatedById());
			lowStockInventory.setLastModifiedById(masterParts.getCreatedById());
			lowStockInventory.setCreated(new Timestamp(System.currentTimeMillis()));
			lowStockInventory.setLastupdated(new Timestamp(System.currentTimeMillis()));
			
			
			return lowStockInventory;
		} catch (Exception e) {
			throw e;
		}finally {
			lowStockInventory	= null;
		}
	}
	
	public MasterPartsLocation getMasterPartsLocation(MasterPartsLocationDto	masterPartsLocationDto) {
		MasterPartsLocation		masterPartsLocation		= null;
		CustomUserDetails		userDetails				= null;
		MasterParts				masterparts				= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			masterPartsLocation	= new MasterPartsLocation();
			masterPartsLocation.setAisle(masterPartsLocationDto.getAisle());
			masterPartsLocation.setRow(masterPartsLocationDto.getRow());
			masterPartsLocation.setBin(masterPartsLocationDto.getBin());
			masterPartsLocation.setLocationId(masterPartsLocationDto.getLocationId());
			masterPartsLocation.setCompanyId(userDetails.getCompany_id());
			
			masterparts	= new MasterParts();
			masterparts.setPartid(masterPartsLocationDto.getPartid());
			
			masterPartsLocation.setMasterparts(masterparts);
			
			return masterPartsLocation;
		} catch (Exception e) {
			throw e;
		}finally {
			masterPartsLocation		= null;
			userDetails				= null;
			masterparts				= null;
		}
	}


	// show the List Of Vehicle part
	public List<MasterPartsDto> prepareListofBean(List<MasterPartsDto> MasterParts) {
		List<MasterPartsDto> beans = null;
		if (MasterParts != null && !MasterParts.isEmpty()) {
			beans = new ArrayList<MasterPartsDto>();
			MasterPartsDto bean = null;
			for (MasterPartsDto Parts : MasterParts) {
				bean = new MasterPartsDto();
				bean.setPartid(Parts.getPartid());

				bean.setPartnumber(Parts.getPartnumber());
				bean.setPartname(Parts.getPartname());
				bean.setParttype(Parts.getParttype());
				bean.setCategory(Parts.getCategory());
				bean.setMake(Parts.getMake());
				bean.setLowstocklevel(Parts.getLowstocklevel());
				bean.setReorderquantity(Parts.getReorderquantity());
				bean.setUnittype(Parts.getUnittype());
				bean.setDescription(Parts.getDescription());
				bean.setCreated(CreatedDateTime.format(Parts.getCreatedOn()));
				bean.setLastupdated(CreatedDateTime.format(Parts.getLastupdatedOn()));
				bean.setOwnMaterParts(Parts.isOwnMaterParts());
				bean.setPartManufacturerType(Parts.getPartManufacturerType());
				
				beans.add(bean);
			}
		}
		return beans;
	}

	// edit Show MasterPartsBean
	public MasterPartsDto prepareMasterPartsBean(MasterPartsDto Parts) {
		MasterPartsDto bean = new MasterPartsDto();

		bean.setPartid(Parts.getPartid());
		bean.setPartnumber(Parts.getPartnumber());
		bean.setPartname(Parts.getPartname());
		bean.setParttype(PartType.getPartTypeName(Parts.getPartTypeId()));
		bean.setCategory(Parts.getCategory());
		bean.setMake(Parts.getMake());
		bean.setLowstocklevel(Parts.getLowstocklevel());
		bean.setReorderquantity(Parts.getReorderquantity());
		bean.setUnittype(Parts.getUnittype());
		bean.setDescription(Parts.getDescription());
		bean.setPart_photoid(Parts.getPart_photoid());
		bean.setPartTypeId(Parts.getPartTypeId());
		bean.setCategoryId(Parts.getCategoryId());
		bean.setMakerId(Parts.getMakerId());
		bean.setUnitTypeId(Parts.getUnitTypeId());
		bean.setPartManufacturerType(Parts.getPartManufacturerType());
		bean.setLowStockInventoryId(Parts.getLowStockInventoryId());
		bean.setUnitCost(Parts.getUnitCost());
		bean.setDiscount(Parts.getDiscount());
		bean.setTax(Parts.getTax());
		bean.setRefreshment(Parts.isRefreshment());
		bean.setLocalPartName(Parts.getLocalPartName());
		bean.setPartNameOnBill(Parts.getPartNameOnBill());
		bean.setWarrantyApplicable(Parts.isWarrantyApplicable());
		bean.setCouponAvailable(Parts.isCouponAvailable());
		bean.setRepairable(Parts.isRepairable());
		bean.setScrapAvilable(Parts.isScrapAvilable());
		bean.setWarrantyInMonths(Parts.getWarrantyInMonths());
		bean.setCouponDetails(Parts.getCouponDetails());
		bean.setPartSubCategoryName(Parts.getPartSubCategoryName());
		bean.setChildPart(Parts.isChildPart());
		bean.setPartTypeCategoryId(Parts.getPartTypeCategoryId());
		bean.setPartTypeCategory(PartType.getPartCategoryType(Parts.getPartTypeCategoryId()));
		
		bean.setAssetIdRequired(Parts.isAssetIdRequired());
		if(Parts.isAssetIdRequired()) {
			bean.setAssetIdRequiredStr("Yes");
		}else {
			bean.setAssetIdRequiredStr("No");
		}
		
		
		
		// Create and Last updated Display
		bean.setCreatedBy(Parts.getCreatedBy());
		if (Parts.getCreatedOn() != null) {
			bean.setCreated(CreatedDateTime.format(Parts.getCreatedOn()));
		}
		bean.setLastModifiedBy(Parts.getLastModifiedBy());
		if (Parts.getLastupdatedOn() != null) {
			bean.setLastupdated(CreatedDateTime.format(Parts.getLastupdatedOn()));
		}
		
		return bean;
	}

	// show the InventoryPartQuentity
	public List<MasterPartsLocation> prepareListPartLocation(List<MasterPartsLocation> Inventory) {
		List<MasterPartsLocation> beans = null;
		if (Inventory != null && !Inventory.isEmpty()) {
			beans = new ArrayList<MasterPartsLocation>();
			MasterPartsLocation bean = null;
			for (MasterPartsLocation Parts : Inventory) {

				bean = new MasterPartsLocation();

				bean.setMasterparts(Parts.getMasterparts());
				bean.setPartlocationid(Parts.getPartlocationid());
				bean.setLocationId(Parts.getLocationId());
				bean.setAisle(Parts.getAisle());
				bean.setRow(Parts.getRow());
				bean.setBin(Parts.getBin());

				beans.add(bean);
			}
		}
		return beans;
	}

	// show the InventoryPartQuentity
	public List<MasterPartsLocationDto> prepareNoTisEmpty(List<MasterPartsLocationDto> Masterparts,
			List<PartLocations> PartLocations) {

		ArrayList<MasterPartsLocationDto> masterpartBean = new ArrayList<MasterPartsLocationDto>();
		MasterPartsLocationDto bean = null;
		if (Masterparts != null && !Masterparts.isEmpty()) {

			for (MasterPartsLocationDto MParts : Masterparts) {
				bean = new MasterPartsLocationDto();

				bean.setPartlocationid(MParts.getPartlocationid());
				bean.setLocationId(MParts.getLocationId());
				bean.setLocation(MParts.getLocation());
				bean.setAisle(MParts.getAisle());
				bean.setRow(MParts.getRow());
				bean.setBin(MParts.getBin());

				masterpartBean.add(bean);
			}
		}
		if (PartLocations != null && !PartLocations.isEmpty()) {

			for (PartLocations Parts : PartLocations) {
				boolean found = true;
				if (Masterparts != null && !Masterparts.isEmpty()) {
					for (MasterPartsLocationDto MParts : Masterparts) {
						if (MParts.getLocationId() == Parts.getPartlocation_id()) {

							found = false;
							break;
						}
					}
				}

				if (found) {
					bean = new MasterPartsLocationDto();
					bean.setPartlocationid(null);
					bean.setLocationId(Parts.getPartlocation_id());
					bean.setLocation(Parts.getPartlocation_name());
					bean.setAisle(" ");
					bean.setRow(" ");
					bean.setBin(" ");
					masterpartBean.add(bean);
				}
			}

		}

		return masterpartBean;
	}

	// MasterParts Photo
	public List<org.fleetopgroup.persistence.document.MasterPartsPhoto> prepareListMasterPartsPhoto(List<org.fleetopgroup.persistence.document.MasterPartsPhoto> MasterPartsPhoto) {
		List<org.fleetopgroup.persistence.document.MasterPartsPhoto> beans = null;
		if (MasterPartsPhoto != null && !MasterPartsPhoto.isEmpty()) {
			beans = new ArrayList<>();
			org.fleetopgroup.persistence.document.MasterPartsPhoto MasterParts = null;

			for (org.fleetopgroup.persistence.document.MasterPartsPhoto MasterPartsBean : MasterPartsPhoto) {
				MasterParts = new org.fleetopgroup.persistence.document.MasterPartsPhoto();

				MasterParts.set_id(MasterPartsBean.get_id());
				MasterParts.setPartid(MasterPartsBean.getPartid());

				MasterParts.setPart_photoname(MasterPartsBean.getPart_photoname());
				MasterParts.setPart_filename(MasterPartsBean.getPart_filename());

				MasterParts.setLastupdated(MasterPartsBean.getLastupdated());

				beans.add(MasterParts);
			}
		}
		return beans;
	}

	public static MasterParts getMasterPartsDto(ValueObject	valueObject) throws Exception{
		MasterParts		masterParts		= new MasterParts();
		masterParts.setPartnumber(valueObject.getString("partNumber","").trim().toUpperCase());
		masterParts.setPartname(valueObject.getString("partName","").trim().toUpperCase());
		masterParts.setLocalPartName(valueObject.getString("partNameLocal","").trim().toUpperCase());
		masterParts.setPartNameOnBill(valueObject.getString("partNameOnBill","").trim().toUpperCase());
		masterParts.setPartTypeId(valueObject.getShort("partType",(short) 0));
		masterParts.setCategoryId(valueObject.getLong("partCategory",0));
		masterParts.setSubCategory(valueObject.getString("partSubCategory","").trim());
		masterParts.setMakerId(valueObject.getLong("manufacturer",0));
		masterParts.setDescription(valueObject.getString(""));
		masterParts.setUnitTypeId(valueObject.getLong("unitTypeId",0));
		masterParts.setWarrantyApplicable(valueObject.getBoolean("warranty",false));
		masterParts.setWarrantyInMonths(valueObject.getDouble("warrantyInMonth",0));
		masterParts.setRepairable(valueObject.getBoolean("repairable",false));
		masterParts.setCouponAvailable(valueObject.getBoolean("couponAvailable",false));
		masterParts.setCouponDetails(valueObject.getString("couponDetails","").trim());
		masterParts.setCompanyId(valueObject.getInt("companyId",0));
		masterParts.setCreated(new Date());
		masterParts.setLastupdated(new Date());
		masterParts.setCreatedById(valueObject.getLong("userId",0));
		masterParts.setLastModifiedById(valueObject.getLong("userId",0));
		masterParts.setScrapAvilable(valueObject.getBoolean("scrapAvailable", false));
		masterParts.setDescription(valueObject.getString("description"));
		masterParts.setRefreshment(valueObject.getBoolean("refreshment", false));
		masterParts.setPartManufacturerType(valueObject.getShort("partManufacturerType",(short) 0));
		masterParts.setAutoMasterPart(valueObject.getBoolean("autoMasterPart",false));
		masterParts.setPartTypeCategoryId(valueObject.getShort("partTypeCatgory"));
		masterParts.setAssetIdRequired(valueObject.getBoolean("assetIdRequired", false));
		
		return masterParts;
	}
	
	public static MasterPartsExtraDetails getMasterPartsExtraDetailsDto(ValueObject	valueObject) throws Exception{
		MasterPartsExtraDetails	extraDetails	= new MasterPartsExtraDetails();
		
		extraDetails.setOriginalBrand(valueObject.getLong("originalBrand",0));
		extraDetails.setMainPacking(valueObject.getString("mainPacking"));
		extraDetails.setUomPacking(valueObject.getString("uomPacking"));
		extraDetails.setLooseItem(valueObject.getString("looseItem"));
		extraDetails.setLooseUom(valueObject.getString("uomLoose"));
		extraDetails.setBarCodeNumber(valueObject.getString("barCodeNumber"));
		extraDetails.setItemType(valueObject.getShort("itemType"));
		extraDetails.setCompanyId(valueObject.getInt("companyId",0));
		extraDetails.setDimention(valueObject.getString("Dimension"));
		
		return extraDetails;
	}
	
	public MasterPartRateHistory getPartPriceHistoryDto(MasterPartRate MasterPartsBean, CustomUserDetails	userDetails) {
		MasterPartRateHistory	lowStockInventory	= null;
		try {
			lowStockInventory	= new MasterPartRateHistory();
			
			
			lowStockInventory.setPartId(MasterPartsBean.getPartId());
			lowStockInventory.setUnitCost(MasterPartsBean.getUnitCost());
			lowStockInventory.setDiscount(MasterPartsBean.getDiscount());
			lowStockInventory.setTax(MasterPartsBean.getTax());
			lowStockInventory.setCompanyId(userDetails.getCompany_id());
			lowStockInventory.setCreatedById(userDetails.getId());
			lowStockInventory.setLastModifiedById(userDetails.getId());
			lowStockInventory.setCreated(new Timestamp(System.currentTimeMillis()));
			lowStockInventory.setLastupdated(new Timestamp(System.currentTimeMillis()));
			
			
			return lowStockInventory;
		} catch (Exception e) {
			throw e;
		}finally {
			lowStockInventory	= null;
		}
	}
	
}
