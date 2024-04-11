package org.fleetopgroup.persistence.dao;

import javax.transaction.Transactional;

import org.fleetopgroup.persistence.model.VehicleTypeToServiceProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleTypeToServiceProgramRepository extends JpaRepository<VehicleTypeToServiceProgram, Long> {

	@Query("FROM VehicleTypeToServiceProgram where companyId = ?1 AND vehicleTypeId = ?2 AND markForDelete = 0")
	public VehicleTypeToServiceProgram getVehicleTypeToServiceProgramByCompanyId(Integer companyId, Long tid) throws Exception;

	@Modifying
	@Transactional
	@Query("update VehicleTypeToServiceProgram set markForDelete = 1 where vehicleTypeToServiceProgramId = ?1")
	public void deleteVehicleTypeToServiceProgram(Long id) throws Exception;
	
	
	@Modifying
	@Transactional
	@Query("update VehicleTypeToServiceProgram set markForDelete = 1 where vehicleTypeId = ?1 AND companyId = ?2")
	public void deleteVehicleTypeToServiceByVT(Long id, Integer companyId) throws Exception;
}
