package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TransferRequisitionDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TransferRequisitionRepositiory extends JpaRepository<TransferRequisitionDetails, Long> {

	@Query("FROM TransferRequisitionDetails WHERE approvedRequisitionId =?1 AND companyId =?2 AND markForDelete = 0 AND transferStatusId NOT IN(3,4,8)") //excluded rejected and received
	public List<TransferRequisitionDetails> gettransferListByApprovalId(Long approvalId,int companyId);
	
	@Modifying
	@Transactional
	@Query("UPDATE TransferRequisitionDetails SET transferStatusId = ?2 WHERE transferRequisitionId IN(?1) AND companyId =?3 AND markForDelete = 0 ") 
	public void updateTransferListByApprovalIds(List<Long> transferRequisitionIds,short transferStatusId,int companyId);
	
	@Modifying
	@Transactional
	@Query("UPDATE TransferRequisitionDetails SET transferStatusId = ?2 WHERE transferRequisitionId =?1 AND companyId =?3 AND markForDelete = 0 ") 
	public void updateTransferListByApprovalId(Long transferRequisitionId,short transferStatusId,int companyId);
	
	
	@Modifying
	@Query("UPDATE TransferRequisitionDetails SET transferStatusId = ?1,createdInventoryId=?2  WHERE transferRequisitionId =?3  AND markForDelete = 0 ") 
	public void updateTransferStatusAndNewInven(short transferStatusId,Long createdInventoryId,long transferRequisitionId);
	
	
	@Modifying
	@Query("UPDATE TransferRequisitionDetails SET transferStatusId = ?1,receivedQuantity=?3  WHERE transferRequisitionId =?2  AND markForDelete = 0 ") 
	public void updateTransferStatusQunatityAndNewInven(short transferStatusId,long transferRequisitionId,double receivedQuantity);
	
	
}
