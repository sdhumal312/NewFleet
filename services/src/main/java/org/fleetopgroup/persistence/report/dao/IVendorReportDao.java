package org.fleetopgroup.persistence.report.dao;

import java.util.List;
import org.fleetopgroup.persistence.dto.VendorApprovalDto;
import org.fleetopgroup.persistence.dto.VendorMarketLorryHireDetailsDto;


public interface IVendorReportDao {

	public List<VendorApprovalDto> getVendorPaymentHistoryReportList(String query, Integer company_id) throws Exception;

	public List<VendorMarketLorryHireDetailsDto> getVendorLorryHireDetailsReport(String query, Integer company_id) throws Exception;
	
	public List<VendorApprovalDto> vendorWiseApprovedStatusReport(int vendorId, String dateFrom, String dateTo, Integer companyId) throws Exception;
	
	public List<VendorApprovalDto> vendorWisePaidStatusReport(int vendorId, String dateFrom, String dateTo, Integer companyId) throws Exception;

}
