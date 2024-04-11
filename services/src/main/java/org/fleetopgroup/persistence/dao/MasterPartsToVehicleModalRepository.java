package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.MasterPartsToVehicleModal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MasterPartsToVehicleModalRepository extends JpaRepository<MasterPartsToVehicleModal, Long>{

	@Modifying
	@Query("UPDATE FROM MasterPartsToVehicleModal set markForDelete = 1 where partId = ?1 ")
	public void deleteByPartId(Long partId) throws Exception;
}
