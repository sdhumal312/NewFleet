package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VendorMarketLorryHireDetailsDto;
import org.fleetopgroup.persistence.model.VendorMarketLorryHireDetails;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;

public interface IVendorMarketLorryHireDetailsService {

	public void saveVendorMarketLorryHireDetails(VendorMarketLorryHireDetails		lorryHireDetails) throws Exception;
	
	public void	updateLorryHireBalanceAmount(Double	amDouble, Long lorryHireDetailsId) throws Exception;
	
	Page<VendorMarketLorryHireDetails> getDeployment_Page_VendorLorryHire(Integer pageNumber, Integer companyId);
	
	List<VendorMarketLorryHireDetailsDto>  getPageWiseLorryHireDetails(Integer pageNumber, Integer companyId) throws Exception;
	
	VendorMarketLorryHireDetailsDto		  getVendorMarketLorryHireDetailsById(Long	lorryHireDetailsId, Integer	companyId) throws Exception;
	
	public void deleteVendorMarketLorryHireDetails(Long	lorryHireDetailsId, CustomUserDetails	userDetails) throws Exception;

	void updatePaidAmountAndBalanceOnChargeDelete(Long lorryHireDetailsId, Integer companyId) throws Exception;
	
	public double	getLorryHireBalanceByVendorId(Integer vendorId) throws Exception;
	
	List<VendorMarketLorryHireDetailsDto>  getVendorLorryHirePaymentData(Integer vendorId, Integer companyId) throws Exception;
	
	public void updateLorryHireDetails(ValueObject	valueObject, ValueObject	object) throws Exception;

	public void updatePaymentStatus(long companyId, Long lorryHireDetailsId) throws Exception;

	public VendorMarketLorryHireDetails getVendorMarketLorryHireDetailsByLorryHireIdentity(Integer companyId,Long lorryHireDetailsId) throws Exception;
	
	List<VendorMarketLorryHireDetailsDto>  getVendorLorryHirePaymentData(String query, Integer companyId) throws Exception;

	

	

	
}
