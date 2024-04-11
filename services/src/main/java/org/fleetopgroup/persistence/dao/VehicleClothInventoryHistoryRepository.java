package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;

import org.fleetopgroup.persistence.model.VehicleClothInventoryHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleClothInventoryHistoryRepository extends JpaRepository<VehicleClothInventoryHistory, Long>{
	
	@Query("SELECT BI.vehicleClothInventoryHistoryId From VehicleClothInventoryHistory as BI where BI.companyId = ?1 AND BI.vid = ?2 AND BI.markForDelete = 0")
	public Page<VehicleClothInventoryHistory> getDeployment_Page_ShowClothAssignDetails(Integer companyId, Integer vid, Pageable pageable);
	
	@Query("SELECT BI.vehicleClothInventoryHistoryId From VehicleClothInventoryHistory as BI where BI.companyId = ?1 AND BI.createdOn BETWEEN ?2 AND ?3 AND BI.markForDelete = 0")
	public Page<VehicleClothInventoryHistory> getDeployment_Page_ShowInAssignUpholsteryToVehicleList(Integer companyId,Timestamp startDate, Timestamp endDate, Pageable pageable);

	@Query(" SELECT F.vehicleClothInventoryHistoryId FROM VehicleClothInventoryHistory as F Where F.createdOn between ?1 AND ?2 AND F.companyId =?3 AND F.markForDelete = 0 ORDER BY F.vehicleClothInventoryHistoryId desc ")
	public Page<VehicleClothInventoryHistory> getPageUpholsteryConsumption(Timestamp startDate, Timestamp endDate, int companyId, Pageable pageable);

}
