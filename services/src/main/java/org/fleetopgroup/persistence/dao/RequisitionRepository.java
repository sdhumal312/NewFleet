package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.Requisition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RequisitionRepository extends JpaRepository<Requisition, Long> {
	
	@Modifying
	@Query("UPDATE Requisition SET requisitionStatusId =?2 ,lastModifiedById=?3,lastUpdatedOn=?4 Where requisitionId =?1 AND companyId =?5")
	public void updateRequisitionStatus(Long requisitionId,Short requisitionStatus,Long updatedBy,Date date,int companyId);
	
	@Modifying
	@Query("UPDATE Requisition SET markForDelete = 1,lastModifiedById=?2,lastUpdatedOn=?3 Where requisitionId =?1 AND companyId =?4")
	public void deleteRequisitionById(Long requisitionId,Long updatedBy,Date date,int companyId);
	
	@Query(" FROM Requisition Where requisitionId =?1 AND companyId =?2 AND markForDelete =0")
	public Requisition getRequisitionbyId(Long requisitionId,int companyId);
	
	@Query(" FROM Requisition Where requisitionNum =?1 AND companyId =?2 AND markForDelete =0")
	public Requisition getRequisitionbyNumber(Long requisitionNum,int companyId);
	
	@Query("SELECT R.requisitionId FROM Requisition AS R WHERE R.companyId =?1 AND R.markForDelete = 0")
	public Page<Requisition> getDeployablePage(int companyId,Pageable pageable);
	
	@Query("SELECT R.requisitionId FROM Requisition AS R "
			+ "Inner join SubRequisition as S on S.requisitionId = R.requisitionId "
			+ "Inner join ApprovedRequisitionDetails as A on A.subRequisitionId = S.subRequisitionId "
			+ "WHERE A.approvedStatus =?1 AND A.assignedTo=?2 AND A.companyId =?3 AND R.markForDelete=0 AND A.markForDelete = 0")
	public Page<Requisition> getDeployableTransferPage(short approvalStatus,long userId,int companyId,Pageable pageable);
	
	@Query("SELECT R.requisitionId FROM Requisition AS R "
			+ "Inner join SubRequisition as S on S.requisitionId = R.requisitionId "
			+ "Inner join ApprovedRequisitionDetails as A on A.subRequisitionId = S.subRequisitionId "
			+ "WHERE A.approvedStatus IN(?1) AND A.receiverId=?2 AND A.companyId =?3 AND R.markForDelete=0 AND A.markForDelete = 0")
	public Page<Requisition> getDeployableReceivePage(List<Short> approvalStatusList,long userId,int companyId,Pageable pageable);
	
	@Query("SELECT R.requisitionId FROM Requisition AS R WHERE R.companyId =?1 AND R.requisitionStatusId IN(?2) AND R.markForDelete = 0")
	public Page<Requisition> getDeployablePageAsPerStatus(int companyId,List<Short> statusList,Pageable pageable);
	
	@Query("SELECT R.requisitionId FROM Requisition AS R WHERE R.companyId =?1 AND R.requisitionStatusId=?2 AND R.assignTo=?3 AND R.markForDelete = 0")
	public Page<Requisition> getDeployablePageAsPerStatusAndUser(int companyId,short status,Long userId,Pageable pageable);
}
