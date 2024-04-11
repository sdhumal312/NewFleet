package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleBodyMaker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleBodyMakerRepository extends JpaRepository<VehicleBodyMaker, Long> {
	
	@Query("FROM VehicleBodyMaker WHERE companyId =?1 AND markForDelete=0 ")
	public List<VehicleBodyMaker> getVehicleBodyMakerByCompanyId(int companyId);

	
	@Query("FROM VehicleBodyMaker WHERE vehicleBodyMakerName=?1 AND companyId =?2 AND markForDelete=0")
	public VehicleBodyMaker validateVehicleBodyMaker(String vehicleBodyMakerName,int companyId);
	
	@Query("FROM VehicleBodyMaker WHERE vehicleBodyMakerId=?1 AND companyId =?2 AND markForDelete=0")
	public VehicleBodyMaker getVehicleBodyMakerById(Integer vehicleBodyMakerId,int companyId);
	
	
	@Modifying
	@Query("UPDATE VehicleBodyMaker SET markForDelete =1 WHERE vehicleBodyMakerId =?1 AND companyId =?2")
	public Integer deleteVehicleBodyMakerById(int vehicleBodyMakerId,int companyId);
	
	
	@Modifying
	@Query("UPDATE VehicleBodyMaker SET vehicleBodyMakerName=?1,lastModifiedById=?2,lastModifiedOn=?3 Where vehicleBodyMakerId=?4  ")
	public Integer updateVehicleBodyMaker(String vehicleBodyMakerName,Long lastModifiedById,Date lastModifiedOn,Integer vehicleBodyMakerId); 
}
