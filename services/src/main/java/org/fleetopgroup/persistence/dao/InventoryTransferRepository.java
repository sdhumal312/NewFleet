package org.fleetopgroup.persistence.dao;

import java.util.Date;

import org.fleetopgroup.persistence.model.InventoryTransfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InventoryTransferRepository extends JpaRepository<InventoryTransfer, Long> {

	// save InventoryTransfer All Details
	// public void add_InventoryTransfer(InventoryTransfer inventoryTransfer)
	// throws Exception;

	// get InventoryAll ID To InventoryTransfer Part Location Quantiy
	// @Query("")
	// public List<InventoryTransfer>
	// Get_InventoryAll_id_To_InventoryTransfer(Long inventory_all_id) throws
	// Exception;

	@Query("FROM InventoryTransfer WHERE inventory_transfer_id =?1 AND companyId = ?2 AND markForDelete = 0")
	public InventoryTransfer Find_InventoryTranfer_Details_ID(Long inventory_transfer_id, Integer companyId);

	@Modifying
	@Query("UPDATE InventoryTransfer SET transfer_receiveddate=?1, transfer_receivedReason=?2, LASTMODIFIEDBY=?3, LASTUPDATED_DATE=?4, TRANSFER_STATUS_ID=?5 WHERE inventory_transfer_id =?6 AND companyId = ?7")
	public void Update_InventoryTransfer_ReceivedBy_Details(Date transfer_receiveddate, String transfer_receivedReason,
			String lastmodifiedby, Date lastupdated_DATE, short TRANSFER_STATUS_ID, Long inventory_transfer_id, Integer companyId);

	/**
	 * @param userName
	 * @param transferStatus
	 * @param pageable
	 * @return
	 */
	@Query("FROM InventoryTransfer as P WHERE P.transfer_by_ID =?1 AND companyId = ?2 AND markForDelete = 0")
	public Page<InventoryTransfer> getDeployment_InventoryAll_id_To_InventoryTransfer_transfer_byEmail_Status(
			Long transfer_by_ID, Integer companyId, Pageable pageable);

	/**
	 * @param userName
	 * @param pageable
	 * @return
	 */
	@Query("FROM InventoryTransfer as P WHERE P.transfer_receivedby_ID =?1 AND companyId = ?2 AND markForDelete = 0")
	public Page<InventoryTransfer> getDeployment_InventoryAll_id_To_InventoryTransfer_transfer_receivedbyEmail(
			Long transfer_receivedby_ID, Integer companyId, Pageable pageable);

	/**
	 * @param inventory_transfer_id
	 */
	@Modifying
	@Query("UPDATE InventoryTransfer SET markForDelete = 1 WHERE inventory_transfer_id =?1 AND companyId = ?2")
	public void Delete_InventoryTransfer_History_Delete_By_ID(Long inventory_transfer_id, Integer companyId);
	
	@Modifying
	@Query("UPDATE InventoryTransfer SET TRANSFER_STATUS_ID =?3, rejectRemark =?4, LASTUPDATED_DATE =?5 WHERE inventory_transfer_id =?1 AND companyId = ?2")
	public void Reject_InventoryTransfer_History_Delete_By_ID(Long inventory_transfer_id, Integer companyId, short transferStatusID, String rejectRemark, Date lastupdated_DATE);

}