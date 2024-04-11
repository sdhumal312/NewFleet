package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleServiceProgram;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleServiceProgramRepository extends JpaRepository<VehicleServiceProgram, Long> {

	@Query("select v.vehicleServiceProgramId FROM VehicleServiceProgram as v Where  v.companyId = ?1 AND v.markForDelete = 0 ORDER BY v.vehicleServiceProgramId DESC ")
	public Page<VehicleServiceProgram> getDeployment_Page_ServiceProgram(Integer companyId, Pageable pageable);
	
	@Modifying
	@Query("UPDATE VehicleServiceProgram SET markForDelete = 1 where vehicleServiceProgramId = ?1")
	public void deleteServiceProgram(Long id) throws Exception;
	
	@Query("FROM VehicleServiceProgram as v Where (v.companyId = ?1 OR v.isVendorProgram = 1) AND v.markForDelete = 0")
	public List<VehicleServiceProgram> getServiceProgramListByCompanyId(Integer companyId);
	
	@Query("FROM VehicleServiceProgram as v Where v.vehicleServiceProgramId = ?1 AND v.markForDelete = 0")
	public VehicleServiceProgram getServiceProgramById(Long vehicleServiceProgramId);

	@Query(nativeQuery = true, value = "SELECT *  From VehicleServiceProgram V where V.programName =?1 AND V.companyId = ?2 AND V.markForDelete = 0 LIMIT 1")
	public VehicleServiceProgram getServiceProgramDetailsByName(String programName, Integer companyId);
	
}
