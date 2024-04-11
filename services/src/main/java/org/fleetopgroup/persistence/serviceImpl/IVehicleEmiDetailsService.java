package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.dto.VehicleEmiDetailDto;
import org.fleetopgroup.persistence.dto.VehicleEmiPaymentDetailsDto;
import org.fleetopgroup.persistence.model.VehicleEmiDetails;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IVehicleEmiDetailsService {

	ValueObject getVehicleEmiDetails(ValueObject object) throws Exception;
	
	ValueObject saveVehicleEmiDetails(ValueObject object) throws Exception;
	
	public ValueObject getVehicleEmiDetailsById(ValueObject object) throws Exception;
	
	ValueObject updateVehicleEmiDetails(ValueObject object) throws Exception;
	
	public ValueObject deleteVehicleEmiDetailsById(ValueObject valueObject) throws Exception;
	
	public List<VehicleEmiDetailDto> getVehicleEMIForMonth(Timestamp fromDate, Timestamp toDate, Integer vid) throws Exception;
	
	public List<VehicleEmiPaymentDetailsDto> getVehicleEMIPaymentDetailsForMonth(Timestamp fromDate, Timestamp toDate, Integer vid) throws Exception;
	
	List<VehicleEmiPaymentDetailsDto>  getGroupVehicleEMIForMonth(Timestamp	startDate, Timestamp toDate,long vehicleGroupId) throws Exception;
	
	public List<VehicleEmiPaymentDetailsDto> getGroupVehicleEMIPaymentDetailsForMonth(Timestamp fromDate, Timestamp toDate, long vehicleGroupId) throws Exception;
	
	List<VehicleEmiPaymentDetailsDto>  getVehicleEMIForDateRange(String	startDate, String toDate, Integer vid) throws Exception;
	
	public ValueObject getVehicleWiseEmiPaymentPendingList(ValueObject object) throws Exception;
	
	public ValueObject saveVehicleEmiPaymentDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getVehicleWiseEmiPaidList(ValueObject object) throws Exception;
	
	public ValueObject getPreEmiSettlementDetail(ValueObject object) throws Exception;
	
	public ValueObject savePreEmiSettlementDetails(ValueObject object) throws Exception;
	
	public List<VehicleEmiDetails> getVehicleEMIForMonth(Timestamp startDate, Integer vid) throws Exception;

	
}
