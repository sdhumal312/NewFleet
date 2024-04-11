package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.RepairFromAssetPart;
import org.fleetopgroup.persistence.model.RepairToAssetPart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RepairToAssetPartRepository extends JpaRepository<RepairToAssetPart, Long> {

	@Modifying
	@Query("UPDATE RepairToAssetPart SET markForDelete = 1 WHERE repairToAssetPartId =?1")
	public void removeAdditionalAsset(long repairFromAssetPartId);
	
	@Query("FROM RepairToAssetPart WHERE repairStockToPartDetailsId =?1 ")
	public List<RepairToAssetPart> getAssetByAdditionalPartId(long additionalPartId);


}
