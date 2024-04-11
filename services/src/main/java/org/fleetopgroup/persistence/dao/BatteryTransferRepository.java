package org.fleetopgroup.persistence.dao;

import java.util.Date;

import org.fleetopgroup.persistence.model.BatteryTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BatteryTransferRepository extends JpaRepository<BatteryTransfer, Long> {

	@Query("FROM BatteryTransfer where batteryTransferId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public BatteryTransfer getBatteryTransfer(Long transferId, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE BatteryTransfer SET receiveDate=?1, receiveRemark=?2, lastModifiedById=?3, lastModifiedOn=?4, transferStausId = 2 WHERE batteryTransferId =?5 AND companyId = ?6")
	public void update_BatteryTransfer_ReceivedBy_Details(Date transfer_receiveddate,
			String transfer_receivedReason, Long lastmodifiedby, Date lastupdated_DATE, Long inventory_transfer_id, Integer companyid);


}
