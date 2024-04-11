package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.TicketIncomeApi;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public interface ITicketIncomeApiService {
	
	public void saveticketIncomeApi(TicketIncomeApi ticketIncomeApi) throws Exception;
	
	public ValueObject addVehicleTicketIncome(ValueObject valueObject) throws Exception;
	
	public ValueObject getVehicleTicketIncome(ValueObject valueObject) throws Exception;
	
	public List<TicketIncomeApi> setTicketIncomeDetailsDto(JSONArray jsonArray, ValueObject valueObject) throws Exception;
	
	public ValueObject getTicketIncomeApiDeatilsById(ValueObject valueObject) throws Exception;
	
	public ValueObject addITSGatewayBusIncome(ValueObject valueObject) throws Exception;
	
	public ValueObject addBitlaTicketIncome(ValueObject valueObject) throws Exception;
	
	public ValueObject addMantisDispatchIncome(ValueObject valueObject) throws Exception ;
}