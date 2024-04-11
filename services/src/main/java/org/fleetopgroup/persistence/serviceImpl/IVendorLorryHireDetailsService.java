package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.VendorMarketLorryHireDetailsDto;
import org.fleetopgroup.web.util.ValueObject;

public interface IVendorLorryHireDetailsService {

	ValueObject		saveVendorLorryHireDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject		getPageWiseVendorLorryHireDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject		getLorryHireDetailsById(ValueObject	valueObject) throws Exception;
	
	ValueObject		deleteLorryHireChargesDetails(ValueObject	valueObject) throws Exception;
	
	ValueObject		deleteLorryHireInvoice(ValueObject	valueObject) throws Exception;
	
	ValueObject		getVendorLorryHirePaymentData(ValueObject	valueObject) throws Exception;
	
	ValueObject		savLorryHirePaymentDetails(ValueObject	valueObject) throws Exception;

	ValueObject     saveExpenseDetailsInfo(ValueObject object) throws Exception;		
	
	public void updateLorryExpenseDetailsInfoDetails(double amount, long id) throws Exception; 

	public VendorMarketLorryHireDetailsDto getPaymentStatusIdByLorryHireDetailsId(Long lorryHireDetailsId) throws Exception;

	public void updateLorryExpenseDetailsInfoDetailsDelete(double amount, long id) throws Exception;

	List<VendorMarketLorryHireDetailsDto> getLorryHireListByTripSheetId(Long tripSheetId) throws Exception;
	
}
