package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.MasterPartsToVehicleMaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MasterPartsToVehicleMakerReepository extends JpaRepository<MasterPartsToVehicleMaker, Long>{

	@Modifying
	@Query("UPDATE FROM MasterPartsToVehicleMaker set markForDelete = 1 where partId = ?1 ")
	public void deleteByPartId(Long partId) throws Exception;
}
