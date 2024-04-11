package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.RequisitionApprovalDetailsDto;
import org.fleetopgroup.persistence.model.ApprovedRequisitionDetails;
import org.fleetopgroup.persistence.model.PartiallyReceivedDetails;
import org.fleetopgroup.web.util.ValueObject;

public interface IRequisitionApprovalService {
	
	public ValueObject approveSubRequisition(ValueObject object) throws Exception;

	public ValueObject getApprovalDetailsById(ValueObject valueObject) ;
	
	public ValueObject addToApproveRequisition(ValueObject object) throws Exception;
	
	public List<RequisitionApprovalDetailsDto> getApprovalListBySubReq(String condition) throws Exception;
	
	public ValueObject deletedRequisitionApprovalById(ValueObject object);
	
	public ValueObject saveTransferQuantity(ValueObject object);
	
	public ValueObject rejectApproval(ValueObject object);
	
	public  ApprovedRequisitionDetails getApprovalById (long approvedRequisitionId,int companyId) ;
	
	public  void updateApprovalStatus(long approvedRequisitionId,int companyId,Short approvalStatus,double returnedQuantity);
	
	public  void updateApprovalStatus (long approvedRequisitionId,int companyId,Long lastModifiedById,Short approvalStatus);
	
	public  void updateApprovalStatusAndRemark (long approvedRequisitionId,int companyId,Long lastModifiedById,Short approvalStatus,String reamrk);
	
	public List<PartiallyReceivedDetails> getPartiallyReceivedDetails(long approvedId,int companyId);
	
	public  List<ApprovedRequisitionDetails> getApprovalListBySubReqIdAndStatus (List<Long> requisitionIdList,short status,int companyId) throws Exception;
	
	public boolean validateApprovalsComplete(Long requisitionId,int companyId,List<Short>statusList);
	
	public List<RequisitionApprovalDetailsDto> getApprovalListWithSubReqDetails(String condition) throws Exception;
	
	
}
