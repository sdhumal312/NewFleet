/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.UreaRequisition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface UreaRequisitionRepository extends JpaRepository<UreaRequisition, Integer> {
	
	@Modifying
	@Query("UPDATE UreaRequisition UR SET UR.ureaRequisitionStatusId = ?1  where UR.ureaRequisitionId = ?2 AND UR.companyId = ?3 ")
	void updateRequisitionStatus(short ureaRequisitionStatusId, Long ureaRequisitionId, Integer companyId);
	
	@Query("From UreaRequisition AS UR where UR.ureaRequisitionId = ?1 AND  UR.companyId = ?2 AND UR.markForDelete = 0")
	UreaRequisition getByUreaRequisitionId(Long ureaRequisitionId, Integer companyId);
	
	@Query("SELECT UR.ureaRequisitionId From UreaRequisition AS UR where UR.companyId = ?1 AND  UR.ureaRequisitionStatusId = ?2 AND UR.markForDelete = 0")
	Page<UreaRequisition> getDeployment_Page_UR(Integer companyId,short status, Pageable pageable);

	@Query("SELECT UR.ureaRequisitionId From UreaRequisition AS UR where UR.companyId = ?1 AND  UR.ureaRequisitionStatusId = ?2 AND UR.ureaRequisitionSenderId = ?3 	AND UR.markForDelete = 0")
	Page<UreaRequisition> getDeployment_Page_Your_Send_UR(Integer companyId, short status,long userId, Pageable pageable);
	
	@Query("SELECT UR.ureaRequisitionId From UreaRequisition AS UR where UR.companyId = ?1 AND  UR.ureaRequisitionStatusId = ?2 AND UR.ureaRequisitionReceiverId = ?3 AND UR.markForDelete = 0")
	Page<UreaRequisition> getDeployment_Page_Your_Received_UR(Integer companyId, short status, long userId,Pageable pageable);

	@Modifying
	@Query("UPDATE UreaRequisition UR SET UR.markForDelete = 1  where UR.ureaRequisitionId = ?1 AND UR.companyId = ?2 ")
	void deleteUreaRequisition(long ureaRequisitionId, Integer companyId);

	@Modifying
	@Query("UPDATE UreaRequisition UR SET UR.ureaRequisitionStatusId = ?1 , UR.ureaRequisitionRemark = ?2 where UR.ureaRequisitionId = ?3 AND UR.companyId = ?4 ")
	void updateRequisitionStatusAndRemark(short short1, String remark, long ureaRequisitionId, Integer companyId);
	
	@Modifying
	@Query("UPDATE UreaRequisition UR SET UR.ureaRequisitionStatusId = ?1 , UR.ureaRequisitionRemark = ?2, UR.ureaReceivedQuantity = ?3 where UR.ureaRequisitionId = ?4 AND UR.companyId = ?5 ")
	void updateReceivedUreaQuantityInUreaRequisition(short ureaRequisitionStatusId, String remark, double ureaReceivedQuantity,  long ureaRequisitionId, Integer companyId);
	
	@Modifying
	@Query("UPDATE UreaRequisition UR SET UR.ureaRequisitionStatusId = ?1 , UR.ureaRequisitionRemark = ?2, UR.ureaRejectedQuantity = ?3 where UR.ureaRequisitionId = ?4 AND UR.companyId = ?5 ")
	void updateRejectUreaQuantityInUreaRequisition(short ureaRequisitionStatusId, String remark, double ureaRejectedQuantity,  long ureaRequisitionId, Integer companyId);
}
