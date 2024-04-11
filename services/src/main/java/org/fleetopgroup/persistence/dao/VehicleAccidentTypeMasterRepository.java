package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleAccidentTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleAccidentTypeMasterRepository extends JpaRepository<VehicleAccidentTypeMaster, Integer> {

	@Query("FROM VehicleAccidentTypeMaster WHERE companyId =?1 AND markForDelete =0 ")
	public List<VehicleAccidentTypeMaster> getAllVehicleAccidentTypeMaster(int companyId);

	@Query("FROM VehicleAccidentTypeMaster WHERE vehicleAccidentTypeMasterId = ?1 AND companyId =?2 AND markForDelete =0 ")
	public VehicleAccidentTypeMaster getVehicleAccidentTypeMasterById(long vehicleAccidentTypeId, int companyId);

	@Query("FROM VehicleAccidentTypeMaster WHERE vehicleAccidentTypeMasterName = ?1 AND companyId =?2 AND markForDelete =0 ")
	public VehicleAccidentTypeMaster findByName(String vehicleAccidentTypeMasterName, Integer companyId);

	@Modifying
	@Query("UPDATE VehicleAccidentTypeMaster SET vehicleAccidentTypeMasterName = ?1, description = ?2, lastUpdatedBy =?3, lastUpdatedOn =?4  WHERE vehicleAccidentTypeMasterId =?5 ")
	public void updateVehicleAccidentTypeMaster(String vehicleAccidentTypeName, String description, long userId, Date lastUpdatedDate, long vehicleAccidentTypeId);

	@Modifying
	@Query("UPDATE VehicleAccidentTypeMaster SET markForDelete = 1  WHERE vehicleAccidentTypeMasterId =?1 AND companyId =?2 ")
	public void deleteVehicleAccidentTypeMaster(long vehicleAccidentTypeId, int companyId);


}
