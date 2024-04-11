/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.PurchasePartForVehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface PurchasePartForVehicleRepository extends JpaRepository<PurchasePartForVehicle, Integer> {

	@Modifying
	@Query("UPDATE PurchasePartForVehicle P SET P.markForDelete = 1  where P.purchasePartForVehicleId = ?1 AND P.companyId = ?2 ")
	void deletePurchasePartForVehicle(long purchasePartForVehicleId, Integer company_id);

	@Modifying
	@Query("UPDATE PurchasePartForVehicle P SET P.markForDelete = 1  where P.purchaseorderToPartId = ?1 AND P.purchaseOrderId = ?2 AND P.companyId = ?3 ")
	void deletePurchasePartForVehicle(Long purchaseorderToPartId, Long purchaseorder_id, Integer company_id);


}
