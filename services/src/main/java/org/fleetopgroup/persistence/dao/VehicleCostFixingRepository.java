package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VehicleCostFixing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleCostFixingRepository extends JpaRepository<VehicleCostFixing, Long>{

	@Query("FROM VehicleCostFixing where companyId = ?1 AND tyreSubTypeId = ?2 AND markForDelete = 0")
	public VehicleCostFixing getVehicleCostFixingByTyreSubTypeId(Integer companyId, Integer tyreSubTypeId) throws Exception;
}
