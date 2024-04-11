package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.BatteryAmountDto;
import org.fleetopgroup.persistence.dto.BatteryInvoiceDto;
import org.fleetopgroup.persistence.model.BatteryAmount;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

@Service
public interface IBatteryAmountService {

	List<BatteryAmountDto> getBatteryAmountList(BatteryInvoiceDto	batteryInvoiceDto) throws Exception;
	
	void updateBatteryAsignNumber(Integer asigned, Long amountId, Integer companyId) throws Exception;
	
	void AddBatteryAsignNumber(Integer asigned, Long amountId, Integer companyId) throws Exception;
	
	public ValueObject	deleteBatteryAmountDetails(ValueObject valueObject) throws Exception; 
	
	public void updateInvoiceAmountToBatteryInvoice(Double amount, Long invoiceId, Integer companyId) throws Exception;
	
	public void registerBatteryAmount(BatteryAmount  batteryAmount) throws Exception;
	
	public void updateTotalAmountToBatteryAmount(Double amount, Long batteryAmountId, Integer companyId) throws Exception;
}
