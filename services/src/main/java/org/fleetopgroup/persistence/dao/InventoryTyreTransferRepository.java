package org.fleetopgroup.persistence.dao;

import java.util.Date;

import org.fleetopgroup.persistence.model.InventoryTyreTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InventoryTyreTransferRepository extends JpaRepository<InventoryTyreTransfer, Long> {

	@Query("FROM InventoryTyreTransfer WHERE ITTID =?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public InventoryTyreTransfer Find_InventoryTyreTransfer_Details_ID(Long inventory_transfer_id, Integer companyId);

	@Modifying
	@Query("UPDATE InventoryTyreTransfer SET TRANSFER_RECEIVEDDATE=?1, TRANSFER_RECEIVEDREASON=?2, LASTMODIFIEDBYID=?3, LASTUPDATED_DATE=?4, TRANSFER_STATUS_ID = 2 WHERE ITTID =?5 AND COMPANY_ID = ?6")
	public void Update_InventoryTyreTransfer_ReceivedBy_Details(Date transfer_receiveddate,
			String transfer_receivedReason, Long lastmodifiedby, Date lastupdated_DATE, Long inventory_transfer_id, Integer companyid);


}