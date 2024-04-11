package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.RepairFromAssetPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RepairFromAssetPartRepository  extends JpaRepository<RepairFromAssetPart, Long>{

	@Modifying
	@Query("UPDATE RepairFromAssetPart SET markForDelete = 1 WHERE repairFromAssetPartId =?1")
	public void removeAsset(long repairFromAssetPartId);

	@Query("FROM RepairFromAssetPart WHERE repairToStockDetailsId =?1 AND markForDelete = 0 ")
	public List<RepairFromAssetPart> getAssetByStockToDetailsId(long repairToStockDetailsId);

	
	
}
