package org.fleetopgroup.persistence.serviceImpl;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.DriverHaltDto;
import org.fleetopgroup.persistence.model.DriverHalt;

public interface IDriverHaltService {

	DriverHalt register_New_DriverHalt(DriverHalt accountDto) throws Exception;
	
	List<DriverHalt> List_Total_OF_DriverHalt_details(Integer driver_ID);

	public DriverHalt Validate_DriverHalt(int driver_id, Date driver_Date);

	public void update_DriverHalt(DriverHalt driver);

	public List<DriverHalt> list_DriverHalt(int driver_id);

	public List<DriverHaltDto> Find_list_TripSheet_DriverHalt(Long TripSheetID, int companyId);

	public List<DriverHalt> list_Date_DriverHalt(int driver_id, String from, String To);

	public List<DriverHalt> Validate_list_Date_DriverHalt(int driver_id, String from, String To, Integer companyId);

	public DriverHalt get_DriverHalt(Long driver_id);

	public void delete_DriverHalt(Long driver_id, Integer companyId);

	/**
	 * @param query
	 * @return
	 */
	List<DriverHaltDto> Report_OF_Driver_Local_Halt(String query);

	/**
	 * @param driver_id
	 * @param driverStart
	 * @param driverEnd
	 * @return
	 */
	List<DriverHaltDto> list_Of_DriverID_to_DriverHalt_BATA_Details(Integer driver_id, String driverStart,
			String driverEnd, Integer companyId);

	public DriverHaltDto GET_DriverHalt_Edit_BATA_Details(Long dHID, Integer company_id);

	public List<DriverHaltDto> getBataDetails(Integer driverId, String dateRangeFrom, String dateRangeTo) throws Exception;
	
	public void deleteDriverHaltByTripSheetId(Long tripSheeetId) throws Exception;

	

}
