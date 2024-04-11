package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleEmiDetailDto;
import org.fleetopgroup.persistence.dto.VehicleEmiPaymentDetailsDto;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleEmiDetailsDao {

	VehicleEmiDetailDto getVehicleEmiDetails(Integer vid, Integer companyId) throws Exception;
	
	public VehicleEmiDetailDto getVehicleEmiDetailsById(long vehicleEmiDetailsId, Integer companyId) throws Exception;
	
	public List<VehicleEmiDetailDto> getVehicleEmiDetailsList(Integer vid, Integer companyId) throws Exception;
	
	public List<VehicleEmiPaymentDetailsDto> getVehicleEmiPaymentPaidList(long vehEmiDetailsId, Integer companyId) throws Exception;

	public List<VehicleEmiDetailDto> getBankWiseVehicleEmiDetailsReport(String query)throws Exception;


}
