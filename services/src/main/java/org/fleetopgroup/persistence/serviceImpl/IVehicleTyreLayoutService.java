/**
 * 
 */
package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleTyreLayoutDto;
import org.fleetopgroup.persistence.dto.VehicleTyreLayoutPositionDto;
import org.fleetopgroup.persistence.model.VehicleTyreLayout;
import org.fleetopgroup.persistence.model.VehicleTyreLayoutPosition;

/**
 * @author fleetop
 *
 */
public interface IVehicleTyreLayoutService {

	public VehicleTyreLayout registerNewTyreLayout(VehicleTyreLayout VehicleTyreLayout) throws Exception;

	public VehicleTyreLayout UpdateTyreLayout(VehicleTyreLayout VehicleTyreLayout) throws Exception;

	/** This interface get vehicle id to tyre layout details */
	public List<VehicleTyreLayoutDto> Get_Vehicle_Type_Details(Integer Vehicle_id, Integer companyId) throws Exception;

	/** This interface save Tyre layout position save and tyre is */
	public VehicleTyreLayoutPosition registerNewTyreLayoutPosition(VehicleTyreLayoutPosition VehicleTyreLayoutPostion)
			throws Exception;
	
	/** This interface get vehicle id to tyre layout details */
	public List<VehicleTyreLayoutPositionDto> Get_Vehicle_TyreLayout_Position_Details(Integer Vehicle_id, Integer companyId) throws Exception;


	/** This interface update get vehicle id to tyre layout details */
	public void update_Position_Assign_TYRE_To_Vehicle(Long TYRE_ID ,String TYRE_SERIAL_NO, boolean TYRE_ASSIGNED, Long LP_ID, String remark) throws Exception;
	
	/** This interface get vehicle id to Validate_Vehicle_Tyre_Position_AssignOrNot details */
	public List<VehicleTyreLayoutPosition> Validate_Vehicle_Tyre_Position_AssignOrNot(Integer Vehicle_id, boolean TYRE_ASSIGNED, Integer companyId) throws Exception;

	/** This interface Delete_VehicleTyreLayoutPosition details */
	public void Delete_VehicleTyreLayoutPosition(Integer Vehicle_id, Integer companyId) throws Exception;
	
	/** This interface Delete_VehicleTyreLayout details*/
	public void Delete_VehicleTyreLayout(Integer Vehicle_id, Integer companyId) throws Exception;
	
	/** This interface save Tyre layout Vehicle_id, position  save and tyre is */
	public VehicleTyreLayoutPosition Get_Position_name_to_GetDetails(Integer Vehicle_id, String position, Integer companyId)
			throws Exception;
	
	/** This interface save Tyre layout Vehicle_id, position  save and tyre is */
	public VehicleTyreLayoutPosition Get_Position_ID_to_GetDetails(Long Position_ID, Integer companyId)
			throws Exception;
	
	public List<VehicleTyreLayoutPositionDto> getVehicleTyreExpenseDetailsList(Integer vid, Integer companyId) throws Exception;
	
	public List<VehicleTyreLayoutPositionDto> getGroupTyreExpenseDetailsList(long vehicleGroupId, Integer companyId) throws Exception;
	
		
}
