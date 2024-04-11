package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleOdometerHistoryDto;
import org.fleetopgroup.persistence.model.VehicleOdometerHistory;



public interface IVehicleOdometerHistoryService {

	public void addVehicleOdometerHistory(VehicleOdometerHistory status) throws Exception;

	public void updateVehicleOdometerHistory(VehicleOdometerHistory status) throws Exception;
	
	public void updateVehicleOdometerHistory(Integer odometer, Long voh_updateId) throws Exception;

	public List<VehicleOdometerHistory> listVehicleOdometerHistory() throws Exception;

	public List<VehicleOdometerHistoryDto> listVehicleOdometerHistory(Integer vid, Integer companyId) throws Exception;
	
	//show graph 
	public List<VehicleOdometerHistory> listVehicleOdometerGraph(Integer vid) throws Exception;
	
	public void updateOdometerThreshHoldToServiceReminder(VehicleOdometerHistory status) throws Exception;
	
	public VehicleOdometerHistory getMonthStartOdoMeterOfVehicle(Integer vid, Timestamp date) throws Exception;
	
	public VehicleOdometerHistory	getOdoMeterAtSpecifiedTime(Integer vid, Timestamp date)  throws Exception;
	
	public VehicleOdometerHistory  getLastOdometerHistory(Integer vid, Integer odometer)throws Exception;

	public VehicleOdometerHistory getVehicleOdometerHistoryByVidExceptCurrentEntry(Long voh_updateId,Integer vid, Integer companyId) throws Exception;

	public void deleteVehicleOdometerHistory(Long voh_updateId, Integer companyId)throws Exception;

	public void updateVohUpdateLocationStatusIdByVoh_id(short vohUpdateLocationStatusId, Long voh_Id, Integer companyId)throws Exception;

	public String getVehicleOdometerHistoryNumber(Long voh_id, String voh_updatelocation, Integer companyId) throws Exception;
	
}
