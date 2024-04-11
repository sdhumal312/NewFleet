package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.BatteryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BatteryTypeRepository extends JpaRepository<BatteryType, Long> {

	@Query("FROM BatteryType AS BT where BT.markForDelete = 0")
	List<BatteryType> findAll();
	
	@Query("FROM BatteryType AS BT where BT.batteryType = ?1 AND BT.batteryManufacturerId = ?2 AND BT.partNumber = ?3 AND BT.warrantyPeriod = ?4 AND BT.markForDelete = 0")
	List<BatteryType> validateType(String type, Long manufactureId, String partNumber, Integer warranty);
	
	@Query("FROM BatteryType v where v.batteryTypeId = ?1 And v.markForDelete = 0")
	BatteryType getbatteryTypeByID(Long batteryTypeId);


	@Query("from BatteryType v where v.batteryType = ?1 AND v.markForDelete = 0")
	public List<BatteryType> validateBatteryType(String batteryType) throws Exception ;
}
