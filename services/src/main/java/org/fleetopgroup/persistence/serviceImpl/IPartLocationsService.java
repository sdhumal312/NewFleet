package org.fleetopgroup.persistence.serviceImpl;

/**
 * @author fleetop
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.PartLocationsDto;
import org.fleetopgroup.persistence.dto.UserDto;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.model.User;
import org.fleetopgroup.web.util.ValueObject;

public interface IPartLocationsService {

	public PartLocations addPartLocations(PartLocations status) throws Exception;

	public void updatePartLocations(PartLocations status) throws Exception;

	public List<PartLocations> listPartLocations() throws Exception;

	public PartLocations getPartLocations(int sid) throws Exception;

	public void deletePartLocations(Integer partlocID, Integer companyId) throws Exception;

	public List<PartLocations> listOnlyStatus() throws Exception;
	
	public List<PartLocations> SearchOnlyPartLocations(String Search, Integer companyId) throws Exception;
	
	public PartLocations ValidatePartLocations(Integer locationName, Integer companyId) throws Exception;
	
	public PartLocations validatePartLocations(String locationName, Integer companyId) throws Exception;
	
	public List<PartLocations> Search_PartLocations_select(String partname, Integer companyId) throws Exception;

	public List<PartLocations> getAllPartLocations(String partname, Integer companyId) throws Exception;
	
	/**
	 * This interface is Vehicle count Total part name using to check in Part
	 * Inventory, Tyre Inventory, WorkOrder, PurchaseOrder
	 */
	public Object[] vaildate_TotalPartName_Location_PART_TYRE_WO_PO(String partLocation_name, Integer companyId, Integer locationId) throws Exception;
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<PartLocations> listPartLocationsByCompanyId(Integer companyId) throws Exception;
	
	public List<PartLocations> listAllPartLocationsByCompanyId(Integer companyId) throws Exception;

	public List<PartLocations> getPartLocationListByType(short partLocationType)throws Exception;

	public PartLocationsDto getPartLocationsDetails(Integer partlocation_id)throws Exception;

	public List<PartLocations> searchPartLocationListByMainLocatinId(String term, short locationType, Integer mainLocationId,ValueObject valueObject)throws Exception;

	public ValueObject getSubLocationByMainLocationId(ValueObject valueOutObject)throws Exception;

	public PartLocations validatePartLocation(Integer partlocation_id)throws Exception;

	public List<PartLocations> listOfAllTypePartLocation(Integer company_id)throws Exception;

	public Object[] validateSubLocationIsPresentInInventory_WO_SE_PO(String partLocationName, Integer companyId,
			Integer locationId) throws Exception;
	public List<PartLocations> getAllSubLocations(String term, Integer companyId) throws Exception;

	public List<PartLocations> allLocationDropdown() throws Exception;

	public List<UserDto> Search_User(String user, Integer companyId) throws Exception;

}