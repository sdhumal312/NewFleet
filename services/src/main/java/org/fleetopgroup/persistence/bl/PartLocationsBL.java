package org.fleetopgroup.persistence.bl;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.persistence.dto.PartLocationPermissionDto;
import org.fleetopgroup.persistence.dto.PartLocationsDto;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.model.PartLocationsHistory;



public class PartLocationsBL {
	public PartLocationsBL() {
	}

	// save the PartLocations Model
	public PartLocations prepareModel(PartLocations PartLocationsBean) {
		PartLocations location = new PartLocations();
		location.setPartlocation_id(PartLocationsBean.getPartlocation_id());
		location.setPartlocation_name(PartLocationsBean.getPartlocation_name().replace(" ", "-"));
		location.setPartlocation_description(PartLocationsBean.getPartlocation_description());
		location.setPartlocation_address(PartLocationsBean.getPartlocation_address());
		location.setPartlocation_streetaddress(PartLocationsBean.getPartlocation_streetaddress());
		location.setPartlocation_city(PartLocationsBean.getPartlocation_city());
		location.setPartlocation_state(PartLocationsBean.getPartlocation_state());
		location.setPartlocation_country(PartLocationsBean.getPartlocation_country());
		location.setPartlocation_pincode(PartLocationsBean.getPartlocation_pincode());
		location.setShippartlocation_address(PartLocationsBean.getShippartlocation_address());
		location.setShippartlocation_streetaddress(PartLocationsBean.getShippartlocation_streetaddress());
		location.setShippartlocation_city(PartLocationsBean.getShippartlocation_city());
		location.setShippartlocation_state(PartLocationsBean.getShippartlocation_state());
		location.setShippartlocation_country(PartLocationsBean.getShippartlocation_country());
		location.setShippartlocation_pincode(PartLocationsBean.getShippartlocation_pincode());
		location.setContactFirName(PartLocationsBean.getContactFirName());
		location.setContactFirPhone(PartLocationsBean.getContactFirPhone());
		location.setContactFirdescription(PartLocationsBean.getContactFirdescription());
		location.setContactSecName(PartLocationsBean.getContactSecName());
		location.setContactSecPhone(PartLocationsBean.getContactSecPhone());
		location.setContactSecdescription(PartLocationsBean.getContactSecdescription());
		location.setMainPartLocationId(PartLocationsBean.getMainPartLocationId());
		if(PartLocationsBean.getPartLocationType() > 0) {
			location.setPartLocationType(PartLocationsBean.getPartLocationType());
		}else {
			location.setPartLocationType((short) 1);
		}
		
		if(PartLocationsBean.getPartLocationType() == 1) {
			location.setMainPartLocationId(0);
		}else {
			location.setMainPartLocationId(PartLocationsBean.getMainPartLocationId());
		}
		return location;
	}

	// show the List Of Vehicle Status
	public List<PartLocations> prepareListofBean(List<PartLocations> PartLocations) {
		List<PartLocations> beans = null;
		if (PartLocations != null && !PartLocations.isEmpty()) {
			beans = new ArrayList<PartLocations>();
			PartLocations bean = null;
			for (PartLocations PartCate : PartLocations) {
				bean = new PartLocations();
				bean.setPartlocation_id(PartCate.getPartlocation_id());
				bean.setPartlocation_name(PartCate.getPartlocation_name());
				bean.setPartlocation_description(PartCate.getPartlocation_description());
				bean.setPartLocationType(PartCate.getPartLocationType());
				beans.add(bean);
			}
		}
		return beans;
	}
	
	

	// edit Show PartLocationsBean
	public PartLocations preparePartLocationsBean(PartLocations PartLocationsBean) {
		PartLocations bean = new PartLocations();
		bean.setPartlocation_id(PartLocationsBean.getPartlocation_id());
		bean.setPartlocation_id(PartLocationsBean.getPartlocation_id());
		bean.setPartlocation_name(PartLocationsBean.getPartlocation_name());
		bean.setPartlocation_description(PartLocationsBean.getPartlocation_description());
		bean.setPartlocation_address(PartLocationsBean.getPartlocation_address());
		bean.setPartlocation_streetaddress(PartLocationsBean.getPartlocation_streetaddress());
		bean.setPartlocation_city(PartLocationsBean.getPartlocation_city());
		bean.setPartlocation_state(PartLocationsBean.getPartlocation_state());
		bean.setPartlocation_country(PartLocationsBean.getPartlocation_country());
		bean.setPartlocation_pincode(PartLocationsBean.getPartlocation_pincode());
		bean.setShippartlocation_address(PartLocationsBean.getShippartlocation_address());
		bean.setShippartlocation_streetaddress(PartLocationsBean.getShippartlocation_streetaddress());
		bean.setShippartlocation_city(PartLocationsBean.getShippartlocation_city());
		bean.setShippartlocation_state(PartLocationsBean.getShippartlocation_state());
		bean.setShippartlocation_country(PartLocationsBean.getShippartlocation_country());
		bean.setShippartlocation_pincode(PartLocationsBean.getShippartlocation_pincode());
		bean.setContactFirName(PartLocationsBean.getContactFirName());
		bean.setContactFirPhone(PartLocationsBean.getContactFirPhone());
		bean.setContactFirdescription(PartLocationsBean.getContactFirdescription());
		bean.setContactSecName(PartLocationsBean.getContactSecName());
		bean.setContactSecPhone(PartLocationsBean.getContactSecPhone());
		bean.setContactSecdescription(PartLocationsBean.getContactSecdescription());
		
		return bean;
	}
	
	public List<PartLocationPermissionDto> prepareListofPartLocation(List<PartLocations> PartLocations) {
		List<PartLocationPermissionDto> beans = null;
		if (PartLocations != null && !PartLocations.isEmpty()) {
			beans = new ArrayList<PartLocationPermissionDto>();
			PartLocationPermissionDto bean = null;
			for (PartLocations PartCate : PartLocations) {
				bean = new PartLocationPermissionDto();
				bean.setPartLocationId(PartCate.getPartlocation_id());
				bean.setPartLocationName(PartCate.getPartlocation_name());
				beans.add(bean);
			}
		}
		return beans;
	}

	public PartLocationsHistory prepareHistoryModel(PartLocations locations) {
		PartLocationsHistory	partLocationsHistory	= new PartLocationsHistory();
		
		partLocationsHistory.setCompanyId(locations.getCompanyId());
		partLocationsHistory.setContactFirdescription(locations.getContactFirdescription());
		partLocationsHistory.setContactFirName(locations.getContactFirName());
		partLocationsHistory.setContactFirPhone(locations.getContactFirPhone());
		partLocationsHistory.setContactSecdescription(locations.getContactSecdescription());
		partLocationsHistory.setContactSecName(locations.getContactSecName());
		partLocationsHistory.setContactSecPhone(locations.getContactSecPhone());
		partLocationsHistory.setLastModifiedById(locations.getLastModifiedById());
		partLocationsHistory.setLastModifiedOn(locations.getLastModifiedOn());
		partLocationsHistory.setMarkForDelete(locations.isMarkForDelete());
		partLocationsHistory.setPartlocation_address(locations.getPartlocation_address());
		partLocationsHistory.setPartlocation_city(locations.getPartlocation_city());
		partLocationsHistory.setPartlocation_country(locations.getPartlocation_country());
		partLocationsHistory.setPartlocation_description(locations.getPartlocation_description());
		partLocationsHistory.setPartlocation_id(locations.getPartlocation_id());
		partLocationsHistory.setPartlocation_name(locations.getPartlocation_name());
		partLocationsHistory.setPartlocation_pincode(locations.getPartlocation_pincode());
		partLocationsHistory.setPartlocation_state(locations.getPartlocation_state());
		partLocationsHistory.setPartlocation_streetaddress(locations.getPartlocation_streetaddress());
		partLocationsHistory.setShippartlocation_address(locations.getShippartlocation_address());
		partLocationsHistory.setShippartlocation_city(locations.getShippartlocation_city());
		partLocationsHistory.setShippartlocation_country(locations.getShippartlocation_country());
		partLocationsHistory.setShippartlocation_pincode(locations.getShippartlocation_pincode());
		partLocationsHistory.setShippartlocation_state(locations.getShippartlocation_state());
		partLocationsHistory.setShippartlocation_streetaddress(locations.getShippartlocation_streetaddress());
		
		return partLocationsHistory;
	}
	
	//Edit
	public PartLocationsDto preparePartLocationsBeanDetails(PartLocationsDto PartLocationsBean) {
		PartLocationsDto bean = new PartLocationsDto();
		bean.setPartlocation_id(PartLocationsBean.getPartlocation_id());
		bean.setPartlocation_id(PartLocationsBean.getPartlocation_id());
		bean.setPartlocation_name(PartLocationsBean.getPartlocation_name());
		bean.setPartlocation_description(PartLocationsBean.getPartlocation_description());
		bean.setPartlocation_address(PartLocationsBean.getPartlocation_address());
		bean.setPartlocation_streetaddress(PartLocationsBean.getPartlocation_streetaddress());
		bean.setPartlocation_city(PartLocationsBean.getPartlocation_city());
		bean.setPartlocation_state(PartLocationsBean.getPartlocation_state());
		bean.setPartlocation_country(PartLocationsBean.getPartlocation_country());
		bean.setPartlocation_pincode(PartLocationsBean.getPartlocation_pincode());
		bean.setShippartlocation_address(PartLocationsBean.getShippartlocation_address());
		bean.setShippartlocation_streetaddress(PartLocationsBean.getShippartlocation_streetaddress());
		bean.setShippartlocation_city(PartLocationsBean.getShippartlocation_city());
		bean.setShippartlocation_state(PartLocationsBean.getShippartlocation_state());
		bean.setShippartlocation_country(PartLocationsBean.getShippartlocation_country());
		bean.setShippartlocation_pincode(PartLocationsBean.getShippartlocation_pincode());
		bean.setContactFirName(PartLocationsBean.getContactFirName());
		bean.setContactFirPhone(PartLocationsBean.getContactFirPhone());
		bean.setContactFirdescription(PartLocationsBean.getContactFirdescription());
		bean.setContactSecName(PartLocationsBean.getContactSecName());
		bean.setContactSecPhone(PartLocationsBean.getContactSecPhone());
		bean.setContactSecdescription(PartLocationsBean.getContactSecdescription());
		bean.setMainPartLocationId(PartLocationsBean.getMainPartLocationId());
		bean.setMainPartLocation(PartLocationsBean.getMainPartLocation());
		bean.setPartLocationType(PartLocationsBean.getPartLocationType());
		bean.setPartLocationTypeStr(PartLocationsBean.getPartLocationTypeStr());
		return bean;
	}
	
	
}
