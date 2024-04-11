package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {

	@Modifying
	@Query("update VehicleType VT set VT.vtype = ?1, VT.lastModifiedBy = ?2, VT.lastupdated =?3, VT.maxAllowedOdometer = ?6, VT.serviceProgramId = ?7 where VT.tid = ?4 AND (VT.company_Id = ?5 OR VT.company_Id = 0)")
	void updateVehicleType(String vType, String updateBy, Date updateDate, long tid, Integer companyId, Integer maxAllowedOdometer, Long serviceProgramId);

	@Modifying
	@Query("update VehicleType VT set VT.vtype = ?1, VT.lastModifiedBy = ?2, VT.lastupdated =?3, VT.maxAllowedOdometer = ?6, VT.superProgramId = ?7 where VT.tid = ?4 AND (VT.company_Id = ?5 OR VT.company_Id = 0)")
	void updateVehicleTypeST(String vType, String updateBy, Date updateDate, long tid, Integer companyId, Integer maxAllowedOdometer, Long serviceProgramId);

	
	@Query("FROM VehicleType VT Where VT.vtype = ?1 and (VT.company_Id = ?2 OR VT.company_Id = 0) and VT.markForDelete = 0")
	VehicleType findByVtype(String vtype, Integer company_Id);

	@Override
	void delete(VehicleType role);

	@Modifying
	@Query("update VehicleType VT set VT.markForDelete = 1 where VT.tid = ?1 and VT.company_Id = ?2")
	void deleteVehicleType(long tid, Integer companyId);

	long count();

	@Query("FROM VehicleType VT where VT.tid = ?1 and (VT.company_Id = ?2 OR VT.company_Id = 0) AND VT.markForDelete = 0")
	VehicleType getVehicleTypeByID(long tid, Integer companyId);
	
	@Query("FROM VehicleType VT where (VT.company_Id = ?1 OR VT.company_Id = 0) and VT.markForDelete = 0")
	List<VehicleType> findAllVehileTypeByCompanyId(Integer company_Id);
	
	@Query("FROM VehicleType VT where VT.company_Id = ?1 and VT.markForDelete = 0")
	List<VehicleType> findAllVehileTypeByOnlyCompanyId(Integer company_Id);
	
	@Query("Select count(VT) FROM VehicleType VT where (VT.company_Id = ?1 OR VT.company_Id = 0) and VT.markForDelete = 0")
	long vehicleTypeCountForCompany(Integer company_Id);

	@Query("FROM VehicleType TR  where (TR.company_Id= ?1 OR TR.company_Id = 0) and TR.markForDelete = 0")
	Page<VehicleType> getDeployment_Page_VehileType(Integer company_id, Pageable pageable) throws Exception;
}
