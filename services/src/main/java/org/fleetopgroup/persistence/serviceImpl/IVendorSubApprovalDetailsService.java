package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.dto.VendorSubApprovalDetailsDto;
import org.fleetopgroup.persistence.model.VendorSubApprovalDetails;

public interface IVendorSubApprovalDetailsService {

	public void saveSubApproval(VendorSubApprovalDetails subApproval)throws Exception;

	public void updateSubApproved_Details(Long invoiceId,short approvedPaymentStatus, Long approvalId, Timestamp expectedPaymentDate, double subApprovalpaidAmount, Integer company_id)throws Exception;
	
	public void updateSubApprovedPayment_Details(short approvalStatus,short paymentType,String payNumber,Timestamp paymentDate,long paidById, String serviceEntries_id,  Long approvalId, Integer companyID)throws Exception;

	public void deleteSubVendorApproval(Long approvalId, Integer companyId)throws Exception;

	public List<VendorSubApprovalDetailsDto>  getVendorSubApprovalDetails(Long VendorApproval_id, Integer companyId) throws Exception;
	
	public void deleteSubVendorApprovalById(Long subApprovalId, Integer companyId)throws Exception;
	
	VendorSubApprovalDetails getVendorSubApprovalDetailsById(Long subApprovalId) throws Exception;
	
	List<VendorSubApprovalDetails>  getSubApprovalListByApprovalId(Long		approvalId, Integer companyId) throws Exception;
	
	public void updateSubApprovalPaymentStatus(Long subApprovalId, short paymentStatus, short paymentType) throws Exception;


}
