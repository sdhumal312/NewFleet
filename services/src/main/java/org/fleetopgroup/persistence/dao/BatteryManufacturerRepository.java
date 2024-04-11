package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.BatteryManufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BatteryManufacturerRepository  extends JpaRepository<BatteryManufacturer, Long>{

	@Modifying
	/*@Query("update batteryManufacturer bmf set bmf.markForDelete = 1 where bmf.batteryManufactureId = ?1 and bmf.company_Id = ?2")
	void deletebatteryManufacturer(long batteryManufactureId, Integer companyId);*/
	
	@Query("from BatteryManufacturer v where v.markForDelete = 0")
	List<BatteryManufacturer> findAll();
	
	@Query("from BatteryManufacturer v where v.manufacturerName = ?1 AND v.markForDelete = 0")
	public List<BatteryManufacturer> validateBatteryManufacturer(String name) throws Exception ;
	
	
	@Query("FROM BatteryManufacturer v where v.batteryManufacturerId = ?1 And v.markForDelete = 0")
	BatteryManufacturer getbatteryManufacturerByID(Long batteryManufacturerId);
	
	
	@Query("Select count(v.batteryManufacturerId) FROM BatteryManufacturer v where  v.markForDelete = 0")
	Long countBatteryManufacturerByCompanyId();
	
	
}