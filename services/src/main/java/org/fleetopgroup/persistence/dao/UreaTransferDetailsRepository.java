/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.UreaTransferDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface UreaTransferDetailsRepository extends JpaRepository<UreaTransferDetails, Integer> {
	
	@Query("From UreaTransferDetails UTD where UTD.ureaTransferId = ?1 AND UTD.companyId = ?2 AND UTD.markForDelete = 0 ")
	public UreaTransferDetails getTransferDetailsByUreaTransferId(long ureaRequisitionId, Integer companyId);

	@Modifying
	@Query("UPDATE UreaTransferDetails UTD SET UTD.markForDelete = 1  where UTD.ureaTransferDetailsId = ?1 AND UTD.companyId = ?2 ")
	public void removeTransfer(long ureaTranferDetailsId, Integer company_id);
	

	@Query("From UreaTransferDetails UTD where UTD.ureaTransferDetailsId = ?1 AND UTD.companyId = ?2 AND UTD.markForDelete = 0 ")
	public UreaTransferDetails getTransferDetailsByUreaTransferDetailsId(long ureaRequisitionId, Integer companyId);

	
}
