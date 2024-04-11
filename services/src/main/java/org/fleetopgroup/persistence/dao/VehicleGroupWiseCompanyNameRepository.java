package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VehicleGroupWiseCompanyName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface VehicleGroupWiseCompanyNameRepository extends JpaRepository<VehicleGroupWiseCompanyName, Long>{

	@Query("FROM VehicleGroupWiseCompanyName where vehicleGroupId = ?1 and markForDelete = 0")
	public VehicleGroupWiseCompanyName getGroupWiseCompanyName(Long groupId) throws Exception;
}
