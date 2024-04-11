package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.PurchaseOrderToBattery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PurchaseOrderToBatteryRepository extends JpaRepository<PurchaseOrderToBattery, Long>{

	@Query("from PurchaseOrderToBattery where purchaseOrderId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<PurchaseOrderToBattery> getPurchaseOrdersTasksIDToBatteryList(long PO_ID, Integer companyId);
	
	@Modifying
	@Query("UPDATE PurchaseOrderToBattery SET markForDelete = 1 WHERE purchaseOrderId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public void deletePurchaseOrderToBattery(Long purchaseOrderId, Integer companyId)throws Exception;
}
