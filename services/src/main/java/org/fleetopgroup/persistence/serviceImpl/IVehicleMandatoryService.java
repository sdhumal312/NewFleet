/**
 * 
 */
package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleMandatoryDto;
import org.fleetopgroup.persistence.model.VehicleMandatory;

/**
 * @author fleetop
 *
 */
public interface IVehicleMandatoryService {

	public VehicleMandatory register_New_VehicleMandatory(VehicleMandatory mandatory) throws Exception;

	/**
	 * @param vehicleID
	 * @return
	 */
	public List<VehicleMandatoryDto> List_Vehicle_Mandatory_Details_GET_VEHICLEID(Integer vehicleID, Integer companyId);

	/**
	 * @param vEHICLE_VID
	 */
	public void Delete_VehicleMandatory_Old_Vehicle_ID(Integer vEHICLE_VID, Integer companyId);

	/**
	 * @param vid
	 * @param tripfromDate
	 * @return
	 */
	public List<VehicleMandatoryDto> List_Vehicle_Mandatory_Details_GET_VEHICLEID_NOT_EXISTS(Integer vid,
			Date tripfromDate, Date triptoDate, Integer companyId);

	/**
	 * @param tripfromDate
	 * @return
	 */
	public List<VehicleMandatoryDto> List_Vehicle_Mandatory_Details_GET_RENEWALREMINDERVALUES(String tripfromDate,
			String triptoDate) throws Exception;

	public Long Count_Vehicle_Mandatory_Details_GET_VEHICLEID_NOT_EXISTS_Count(String tripfromDate) throws Exception;

}
