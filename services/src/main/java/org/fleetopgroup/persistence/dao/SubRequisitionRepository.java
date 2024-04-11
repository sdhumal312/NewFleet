package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.SubRequisition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubRequisitionRepository extends JpaRepository<SubRequisition, Long> {
	
	@Query("FROM SubRequisition WHERE subRequisitionId =?1 AND companyId=?2 AND markForDelete=0 ")
	public SubRequisition getSubrequisitionById(long subRequisitionId, int companyId);
	
	@Query("FROM SubRequisition WHERE requisitionId =?1 AND companyId=?3 AND subRequisitionStatusId =?2 AND markForDelete=0 ")
	public List<SubRequisition> getSubRequisitionListByStatus(long requisitionId,short status, int companyId);
	
	@Query("SELECT subRequisitionId FROM SubRequisition WHERE requisitionId =?1 AND requisitionTypeId=?2 AND subRequisitionStatusId=?3 AND companyId=?4 AND markForDelete=0 ")
	public List<Long> getSubRequisitionIdsByStatus(long requisitionId,short requisitionTypeId ,short statusId, int companyId);
	
	@Modifying
	@Query("UPDATE SubRequisition SET subRequisitionStatusId =?2 , lastModifiedById =?3, lastUpdatedOn =?4 ,remark =?6 WHERE subRequisitionId =?1 AND companyId =?5")
	public void updateSubRequisitionStatus(long subRequisitionId,short subRequisitionStatus, long lastModifiedById,Date lastUpdatedOn, int companyId,String remark);

	@Modifying
	@Query("UPDATE SubRequisition SET subRequisitionStatusId =?2 , lastModifiedById =?3, lastUpdatedOn =?4 WHERE subRequisitionId =?1 AND companyId =?5")
	public void updateSubRequisitionStatus(long subRequisitionId,short subRequisitionStatus, long lastModifiedById,Date lastUpdatedOn, int companyId);
	
	@Modifying
	@Query("UPDATE SubRequisition SET subRequisitionStatusId =?2 , lastModifiedById =?3, lastUpdatedOn =?4, approvalQuantity =?5 ,remark =?6  WHERE subRequisitionId =?1 ")
	public void updateSubRequisitionStatusAndQty(long subRequisitionId,short subRequisitionStatus, long lastModifiedById,Date lastUpdatedOn,Double quantity,String remark);
	
	@Modifying
	@Query("UPDATE SubRequisition SET  lastModifiedById =?2, lastUpdatedOn =?3, approvalQuantity =?4  WHERE subRequisitionId =?1 ")
	public void updateSubRequisitionQty(long subRequisitionId, long lastModifiedById,Date lastUpdatedOn,Double quantity);

}
