/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.UreaTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface UreaTransferRepository extends JpaRepository<UreaTransfer, Integer> {
	
	@Query("From UreaTransfer UT where UT.ureaRequisitionId = ?1 AND UT.companyId = ?2 AND UT.markForDelete = 0 ")
	public UreaTransfer getUreaTransferByRequisitionId(long ureaRequisitionId, Integer companyId);
	
	@Query("From UreaTransfer UT where UT.ureaTransferId = ?1 AND UT.companyId = ?2 AND UT.markForDelete = 0 ")
	public UreaTransfer getTransferDetailsByUreaTransferId(long ureaRequisitionId, Integer companyId);

	@Modifying
	@Query("UPDATE UreaTransfer UT SET UT.ureaTransferStatusId = ?1 , UT.ureaTransferRemark = ?2 where UT.ureaTransferId = ?3 AND UT.companyId = ?4 ")
	public void completeTransfer(short ureaTransferStatusId, String ureaTransferRemark, long ureaTransferId, Integer companyId);

	@Modifying
	@Query("UPDATE UreaTransfer UT SET UT.ureaTransferStatusId = ?1 where UT.ureaTransferId = ?2 AND UT.companyId = ?3 ")
	public void updateTransferStatus(short ureaTransferStatusId, long ureaTransferId, Integer companyId);
	
}
