/**
 * 
 */
package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.DriverFamilyDto;
import org.fleetopgroup.persistence.model.DriverFamily;

/**
 * @author fleetop
 *
 */
public interface IDriverFamilyService {

	public void Registered_DriverFamily_Details(DriverFamily driverFam);

	public DriverFamily Validate_DriverFamily_Details(String DF_Name);

	public List<DriverFamilyDto> list_Of_DriverID_to_DriverFamily(Integer DriverId, Integer companyId);

	public void Delete_DriverFamily_Details(Long DFID);

	/**
	 * @param string
	 * @param driverID
	 * @return
	 */
	public List<DriverFamily> Validate_Driver_Family_Member_Name(String string, Integer driverID);
}
