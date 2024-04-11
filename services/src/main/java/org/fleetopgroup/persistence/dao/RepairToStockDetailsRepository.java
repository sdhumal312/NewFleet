package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.RepairToStockDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RepairToStockDetailsRepository  extends JpaRepository<RepairToStockDetails, Long>{

	@Transactional
	@Modifying
	@Query("UPDATE RepairToStockDetails SET repairStockStatusId = ?1 where repairToStockDetailsId =?2 AND companyId = ?3")
	public void updateStatusOfPart(short repairStockStatusInprocess, long long1, int companyId);

	@Transactional
	@Modifying
	@Query("UPDATE RepairToStockDetails SET markForDelete = 1 where repairStockId =?1 AND companyId = ?2")
	public void removeAllStock(long repairStockId, int companyId);
	
	@Modifying
	@Query(" UPDATE RepairToStockDetails SET repairStockStatusId = ?1 where repairToStockDetailsId IN (?2) AND companyId = ?3")
	public void updateStockStatus(short repairStockStatusInprocess, List<Long> builder, int companyId);

	@Transactional
	@Modifying
	@Query("UPDATE RepairToStockDetails SET repairStockStatusId = ?1 where repairStockId =?2 AND companyId = ?3")
	public void updateStatusOfStockToOpen(short repairStockStatusInprocess, long repairStockId, int companyId);

	@Transactional
	@Modifying
	@Query("UPDATE RepairToStockDetails SET transferedInventoryId = ?2 where repairToStockDetailsId =?1 ")
	public void updateTransferdInventoryId(long repairToStockDetailsId, long transferedInventoryId);

	
}
