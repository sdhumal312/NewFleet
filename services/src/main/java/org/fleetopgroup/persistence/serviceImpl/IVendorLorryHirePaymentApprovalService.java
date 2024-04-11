package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.LorryHirePaymentApprovalToHireDetailsDto;
import org.fleetopgroup.persistence.dto.VendorMarketLorryHireDetailsDto;
import org.fleetopgroup.persistence.model.LorryHirePaymentApprovalToHireDetails;
import org.fleetopgroup.persistence.model.VendorLorryHirePaymentApproval;
import org.fleetopgroup.web.util.ValueObject;

public interface IVendorLorryHirePaymentApprovalService {

	public void saveVendorLorryHirePaymentApproval(VendorLorryHirePaymentApproval hirePaymentApproval) throws Exception;
	
	public void saveVendorApprovalToHireDetails(LorryHirePaymentApprovalToHireDetails approvalToHireDetails) throws Exception;
	
	ValueObject	getVendorLorryPaymentDetailsReport(ValueObject	valueObject) throws Exception;
	
	List<LorryHirePaymentApprovalToHireDetailsDto>  getLorryHirePaymentList(String queryStr, Integer companyId) throws Exception;

	public ValueObject getVendorLorryHireDetailsReport(ValueObject object) throws Exception;

	public ValueObject getVendorPaymentInformation(ValueObject object) throws Exception;

	List<VendorMarketLorryHireDetailsDto> getVendorPaymentInformationDetails(Long lorryHireDetailsId,Integer companyId) throws Exception;//latest
	
	ValueObject	getVendorLorryPaymentOutStandingReport(ValueObject	valueObject) throws Exception;


}
