package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.FuelStockDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FuelStockDetailsRepository extends JpaRepository<FuelStockDetails, Long>{

	@Query("FROM FuelStockDetails where petrolPumpId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public FuelStockDetails getFuelStockDetailsByPetrolPumpId(Integer petrolPumpId, Integer companyId) throws Exception;
	
	@Query("SELECT COUNT(*) FROM FuelStockDetails where stockQuantity < ?1 AND companyId = ?2  AND markForDelete = 0 ")
	public long getMinFuelStockCount(Double threashold, Integer companyId) throws Exception;

}
