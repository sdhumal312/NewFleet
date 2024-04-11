package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleGroupRepository extends JpaRepository<VehicleGroup, Long> {

	@Modifying
	@Query("update VehicleGroup VG set VG.vGroup = ?1, VG.lastModifiedById = ?2, VG.lastupdated =?3 where VG.gid = ?4 AND VG.company_Id = ?5")
	void updateVehicleGroup(String vGroup,  Long updateById, Date updateDate,  long gid, Integer companyId);

	@Query("From VehicleGroup VG  WHERE VG.vGroup = ?1 and VG.company_Id = ?2 and VG.markForDelete = 0")
	VehicleGroup findByVGroup(String vGroup, Integer company_Id);

	@Override
	void delete(VehicleGroup role);

	@Modifying
	@Query("UPDATE VehicleGroup VG set VG.markForDelete = 1 where VG.gid = ?1 and VG.company_Id = ?2")
	void deleteVehicleGroup(long gid, Integer companyId);

	@Query("From VehicleGroup VG  WHERE VG.gid = ?1 AND VG.company_Id = ?2 AND VG.markForDelete = 0")
	VehicleGroup getVehicleGroupByID(long gid, Integer companyId);

	long count();
	
	@Query("From VehicleGroup VG  WHERE VG.company_Id = ?1 and VG.markForDelete = 0")
	List<VehicleGroup> findAllVehicleGroupByCompanyId(Integer company_Id);
	
	@Query("Select count(VG.gid) From VehicleGroup VG  WHERE VG.company_Id = ?1 and VG.markForDelete = 0")
	long findCountOfVehicleGroupByCompanyId(Integer company_Id);

	/*@Query("From vehicle_group_permission VG  WHERE VG.user_id = ?1")
	public List<VehicleGroup> findVehicleGroupPermissionByUserId(Long userId) throws Exception;*/
}
