package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.VendorFixedLaundryRateDto;
import org.fleetopgroup.persistence.model.VendorFixedLaundryRate;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface IVendorFixedLaundryRateService {

	ValueObject	getPageWiseVendorLaundryRate(ValueObject	valueObject) throws Exception;
	
	public Page<VendorFixedLaundryRate> Get_Deployment_Page_VendorFixedLaundryPrice(Integer vendorId, Integer pageNumber, Integer companyId);
	
	public List<VendorFixedLaundryRateDto> list_VendorFixedLaundryPrice(Integer vehicleID, Integer pageNumber, Integer companyId);
	
	ValueObject	saveVendorLaundryRate(ValueObject	valueObject) throws Exception;
	
	ValueObject	updateVendorLaundryRate(ValueObject	valueObject) throws Exception;
	
	ValueObject	getLaundryRateById(ValueObject	valueObject) throws Exception;
	
	ValueObject	deleteLaundryRate(ValueObject	valueObject) throws Exception;
	
	VendorFixedLaundryRateDto	getVendorRateAndLocationQuantity(Integer locationId, Long clothTypesId, Integer vendorId) throws Exception;

}
