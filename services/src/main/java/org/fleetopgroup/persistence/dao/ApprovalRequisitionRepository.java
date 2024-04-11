package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.ApprovedRequisitionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ApprovalRequisitionRepository extends JpaRepository<ApprovedRequisitionDetails, Long> {
	
	@Query("FROM ApprovedRequisitionDetails WHERE subRequisitionId =?1 AND companyId =?2 AND markForDelete = 0 ")
	public List<ApprovedRequisitionDetails> getApprovalListBySubRequisition(long subRequisitionId ,int companyId );
	
	@Query("FROM ApprovedRequisitionDetails WHERE approvedRequisitionId =?1 AND companyId =?2 AND markForDelete = 0 ")
	public ApprovedRequisitionDetails getApprovalById(long approvedRequisitionId ,int companyId );
	
	@Query("SELECT approvedRequisitionId FROM ApprovedRequisitionDetails WHERE subRequisitionId IN (?1) AND approvedStatus IN (?2) AND companyId =?3 AND markForDelete = 0 ")
	public List<Long> getApprovalIdsByStatusAndSubReqIds(List<Long> subRequisitionIds,List<Short> statusIds ,int companyId );
	
	@Modifying
	@Query("UPDATE ApprovedRequisitionDetails SET markForDelete=1,lastModifiedById=?3,lastUpdatedOn=?4 WHERE approvedRequisitionId =?1 AND companyId =?2 ")
	public void deletedApprovalById(long approvedRequisitionId ,int companyId,Long lastModifiedById,Date lastUpdatedOn);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE ApprovedRequisitionDetails SET approvedStatus=?3,returnedQuantity=?4 WHERE approvedRequisitionId =?1 AND companyId =?2 ")
	public void updateApprovalStatus(long approvedRequisitionId ,int companyId,Short approvalStatus,Double returnedQuantity);

	@Modifying
	@Transactional
	@Query("UPDATE ApprovedRequisitionDetails SET approvedStatus=?5,lastModifiedById=?3,lastUpdatedOn=?4 WHERE approvedRequisitionId =?1 AND companyId =?2 ")
	public void updateApprovalStatus(long approvedRequisitionId ,int companyId,Long lastModifiedById,Date lastUpdatedOn,Short approvalStatus);
	
	@Modifying
	@Transactional
	@Query("UPDATE ApprovedRequisitionDetails SET rejectRemark=?6,approvedStatus=?5,lastModifiedById=?3,lastUpdatedOn=?4 WHERE approvedRequisitionId =?1 AND companyId =?2 ")
	public void updateApprovalStatusAndRemark(long approvedRequisitionId ,int companyId,Long lastModifiedById,Date lastUpdatedOn,Short approvalStatus,String remark);
	
	
	@Modifying
	@Transactional
	@Query("UPDATE ApprovedRequisitionDetails SET approvedStatus=?5,lastModifiedById=?3,lastUpdatedOn=?4,poId=?6 WHERE approvedRequisitionId =?1 AND companyId =?2 ")
	public void updateApprovalStatusAndPO(long approvedRequisitionId ,int companyId,Long lastModifiedById,Date lastUpdatedOn,Short approvalStatus,Long poId);
	
	@Modifying
	@Transactional
	@Query("UPDATE ApprovedRequisitionDetails SET approvedStatus=?5,lastModifiedById=?3,lastUpdatedOn=?4,receivedQuantity=?6 WHERE approvedRequisitionId =?1 AND companyId =?2 ")
	public void updateApprovalStatusAndReceiveQty(long approvedRequisitionId ,int companyId,Long lastModifiedById,Date lastUpdatedOn,Short approvalStatus,Double receivedQuantity);
	
	@Modifying
	@Query("UPDATE ApprovedRequisitionDetails SET approvedStatus=?5,lastModifiedById=?3,lastUpdatedOn=?4,receiverId=?6 WHERE approvedRequisitionId =?1 AND companyId =?2 ")
	public void updateApprovalStatusAndReceiver(long approvedRequisitionId ,int companyId,Long lastModifiedById,Date lastUpdatedOn,Short approvalStatus,Long receiverId);
	
	@Modifying
	@Transactional
	@Query("UPDATE ApprovedRequisitionDetails SET approvedStatus=?5,lastModifiedById=?3,lastUpdatedOn=?4,receivedQuantity=?6,finalRemark=?7 WHERE approvedRequisitionId =?1 AND companyId =?2 ")
	public void updateApprovalStatusAndReceiveQty(long approvedRequisitionId ,int companyId,Long lastModifiedById,Date lastUpdatedOn,Short approvalStatus,Double receivedQuantity,String remark);
	
	@Query("FROM ApprovedRequisitionDetails WHERE subRequisitionId IN(?1) AND approvedStatus=?2 AND companyId =?3 AND markForDelete = 0 ")
	public List<ApprovedRequisitionDetails> getApprovalListByStatus(List<Long> subRequisitionId,short status ,int companyId );
	
	@Query(nativeQuery=true,value="select DISTINCT(A.approvedRequisitionId) from ApprovedRequisitionDetails as A " + 
			"INNER JOIN SubRequisition AS S ON S.subRequisitionId = A.subRequisitionId AND S.markForDelete=0 " + 
			"INNER JOIN Requisition AS R ON R.requisitionId = S.requisitionId AND R.markForDelete =0 " + 
			"WHERE A.approvedStatus IN(?3) AND R.requisitionId=?1 AND A.companyId =?2 AND A.markForDelete = 0 ")
	public List<Object> validateApprovalMarkToComplete(Long requisitionId,int companyId,List<Short> status);
	
//	@Modifying
//	@Query("UPDATE ApprovedRequisitionDetails SET ureaManufacturerId=?2 WHERE approvedRequisitionId =?1 ")
//	public void updateureaManufacturerId(long approvedRequisitionId ,long ureaManufacturerId);

}
