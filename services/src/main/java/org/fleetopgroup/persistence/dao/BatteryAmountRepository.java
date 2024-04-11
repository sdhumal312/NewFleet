package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.BatteryAmount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BatteryAmountRepository extends JpaRepository<BatteryAmount, Long>{

	@Query("FROM BatteryAmount where batteryInvoiceId = ?1 AND markForDelete = 0")
	public List<BatteryAmount> getBatteryAmountDetails (Long id) throws Exception;
	
	@Modifying
	@Query("UPDATE BatteryAmount SET markForDelete = 1 where batteryAmountId = ?1")
	public void deleteBatteryAmount(Long batteryAmountId) throws Exception;
	
	@Query("FROM BatteryAmount WHERE batteryInvoiceId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<BatteryAmount> getBatteryAmountDetailsFromBatteryInvoice (Long batteryInvoiceId, int companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE BatteryAmount SET markForDelete = 1 where batteryInvoiceId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public void deleteBatteryAmountByInvoiceId(Long batteryInvoiceId, int companyId) throws Exception;
	
}
