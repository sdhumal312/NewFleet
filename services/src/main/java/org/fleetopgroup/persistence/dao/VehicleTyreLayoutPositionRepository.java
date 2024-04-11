/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleTyreLayoutPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface VehicleTyreLayoutPositionRepository  extends JpaRepository<VehicleTyreLayoutPosition, Long>{

	/*public VehicleTyreLayoutPosition registerNewTyreLayoutPosition(VehicleTyreLayoutPosition VehicleTyreLayoutPostion)
			throws Exception;*/
	
	@Query("From VehicleTyreLayoutPosition WHERE VEHICLE_ID =?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public List<VehicleTyreLayoutPosition> Get_Vehicle_Type_Details(Integer Vehicle_id, Integer companyId)  throws Exception;
	
	@Modifying
	@Query("UPDATE From VehicleTyreLayoutPosition SET TYRE_ID=?1, TYRE_SERIAL_NO=?2, TYRE_ASSIGNED=?3, remark =?6 WHERE LP_ID =?4 AND COMPANY_ID = ?5 ")
	public void update_Position_Assign_TYRE_To_Vehicle(Long TYRE_ID ,String TYRE_SERIAL_NO, boolean TYRE_ASSIGNED, Long LP_ID, Integer companyId, String emark) throws Exception;
	
	@Query("From VehicleTyreLayoutPosition WHERE VEHICLE_ID =?1 AND TYRE_ASSIGNED =?2 AND COMPANY_ID = ?3  AND markForDelete = 0")
	public List<VehicleTyreLayoutPosition> Validate_Vehicle_Tyre_Position_AssignOrNot(Integer Vehicle_id, boolean TYRE_ASSIGNED, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE VehicleTyreLayoutPosition SET markForDelete = 1 WHERE VEHICLE_ID =?1  AND COMPANY_ID = ?2 ")
	public void Delete_VehicleTyreLayoutPosition(Integer Vehicle_id, Integer companyId) throws Exception;
	
	@Query("From VehicleTyreLayoutPosition WHERE VEHICLE_ID =?1 AND POSITION=?2 AND COMPANY_ID = ?3 AND markForDelete = 0")
	public VehicleTyreLayoutPosition Get_Position_name_to_GetDetails(Integer Vehicle_id, String position, Integer companyId)
			throws Exception;
	
	@Query("From VehicleTyreLayoutPosition WHERE LP_ID =?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public VehicleTyreLayoutPosition Get_Position_ID_to_GetDetails(Long Position_ID, Integer companyId)
			throws Exception;

	
}
