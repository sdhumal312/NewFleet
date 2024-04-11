package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;


import org.fleetopgroup.persistence.dto.VendorMarketHireToChargesDto;
import org.fleetopgroup.persistence.model.VendorMarketHireToCharges;

public interface IVendorMarketHireToChargesService {

	List<VendorMarketHireToChargesDto>  getVendorMarketHireToChargesList(Long	lorryHireDetailsId, Integer	companyId) throws Exception;
	
	public void deleteMarketHireCharges(Long hireToChargesId, Integer companyId) throws Exception;
	
	public void deleteMarketHireChargesById(Long hireToChargesId, Integer companyId) throws Exception;

	public VendorMarketHireToCharges getAmountFromVendorMarketHireToCharges(long hireToChargesId, Integer company_id) throws Exception;//new

	
}
