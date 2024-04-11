package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.dto.RequisitionDto;
import org.fleetopgroup.persistence.dto.SubRequisitionDto;
import org.fleetopgroup.persistence.model.Requisition;
import org.fleetopgroup.persistence.model.SubRequisition;
import org.fleetopgroup.web.util.ValueObject;

public interface IRequisitionService {

	public ValueObject prepareRequisitionToSave(ValueObject valueObject) throws Exception;
	
	public List<RequisitionDto> getRequisitionById(ValueObject valueObject,String queryStr,int companyId);
	
	public ValueObject getSubRequisitionDetails(ValueObject valueObject) throws Exception ;
	
	public ValueObject updateSubRequisitionById(ValueObject object);
	
	public ValueObject rejectSubRequisitionById(ValueObject object);
	
//	public ValueObject getTransactionStock(ValueObject object);
	
	public ValueObject getLocationWiseTransactionStock(ValueObject object) ;
	
	public void updateApprovalQunatity(Long subReqId ,short subStatusId,long updatedById,Double quantity,String remark);
	
	public SubRequisition getSubRequisitionById(Long subReqId,int companyId);
	
	public ValueObject getStockWiseBranchList(ValueObject object);
	
	public void updateApprovalQunatity(Long subReqId ,long updatedById,Double quantity);
	
	public List<SubRequisitionDto> getSubRequisitionDetailsById(String queryStr,int companyId);
	
	public void updateSubRequisitionStatus(Long subRequisitionId,short subRequisitionStatus,long lastModifiedById,int companyId);
	
	public ValueObject finalApproval(ValueObject object);
	
	public ValueObject getSubRequisitionBySubRequisitionId(ValueObject valueObject) throws Exception;
	
	public Requisition getRequisitionByRequisitionId(long requisitionId,int companyId);
	
	public ValueObject getRequisitionList(ValueObject valueObject);
	
	public ValueObject deleteRequisitionById(ValueObject object);
	
	public List<SubRequisition> getSubRequisitionListByStatus(long requisitionId,short status,int companyId);
	
	public void updateRequisitionStatus(Long requisitionId ,short requisitionStatus,long updatedById,int companyId);
	
	public ValueObject searchRequisitionByNumber(ValueObject object);
	
	public Map<String,Object> getPartRequsitionPrintData(long requisitionId);

	List<RequisitionDto> getPartRequisitionStatusWiseReport(String queryStr, int companyId);
}
