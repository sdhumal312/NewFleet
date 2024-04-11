/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleMandatory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface VehicleMandatoryRepository extends JpaRepository<VehicleMandatory, Long> {

	@Query("FROM VehicleMandatory AS M WHERE M.VEHICLE_ID=?1 AND COMPANY_ID = ?2 AND M.markForDelete = 0 ")
	public List<VehicleMandatory> List_Vehicle_Mandatory_Details_GET_VEHICLEID(Integer vehicleID, Integer companyId);

	@Modifying
	@Query("UPDATE VehicleMandatory AS M SET M.markForDelete = 1 WHERE M.VEHICLE_ID=?1 AND COMPANY_ID =?2")
	public void Delete_VehicleMandatory_Old_Vehicle_ID(Integer vEHICLE_VID, Integer companyId);
}
