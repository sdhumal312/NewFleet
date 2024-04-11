package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.BatteryHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BatteryHistoryRepository extends JpaRepository<BatteryHistory, Long>{
	
	
	@Query(" SELECT BH.batteryHistoryId FROM BatteryHistory as BH  Where BH.batteryAsignDate between ?1 AND ?2 AND BH.companyId =?3 AND BH.markForDelete = 0 ORDER BY BH.batteryHistoryId desc ")
	public Page<BatteryHistory> getPageBatteryConsumptionByTransactionDate(Timestamp startDate, Timestamp endDate, int companyId, Pageable pageable);

	@Query(" SELECT BH.batteryHistoryId FROM BatteryHistory as BH  Where BH.createdOn between ?1 AND ?2 AND BH.companyId =?3 AND BH.markForDelete = 0 ORDER BY BH.batteryHistoryId desc ")
	public Page<BatteryHistory> getPageBatteryConsumptionByCreatedDate(Timestamp startDate, Timestamp endDate, int companyId, Pageable pageable);

}
