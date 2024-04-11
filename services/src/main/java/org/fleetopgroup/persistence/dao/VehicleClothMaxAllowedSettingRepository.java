package org.fleetopgroup.persistence.dao;


import org.fleetopgroup.persistence.model.VehicleClothMaxAllowedSetting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleClothMaxAllowedSettingRepository extends JpaRepository<VehicleClothMaxAllowedSetting, Long>{
	
	@Query("FROM VehicleClothMaxAllowedSetting where clothTypesId = ?1 AND vid = ?2 AND markForDelete = 0 ")
	public VehicleClothMaxAllowedSetting validateVehicleClothMaxAllowed(Long clothTypeId, Integer vehicleId) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleClothMaxAllowedSetting SET markForDelete = 1 where maxAllowedSettingId = ?1")
	public void deleteVehicleClothMaxAllowedById(Long maxAllowedSettingId) throws Exception;
	
	@Query("SELECT BI.maxAllowedSettingId From VehicleClothMaxAllowedSetting as BI where BI.companyId = ?1 AND BI.markForDelete = 0")
	public Page<VehicleClothMaxAllowedSetting> getDeployment_Page_VehicleClothMaxAllowedSetting(Integer companyId, Pageable pageable);
	
}