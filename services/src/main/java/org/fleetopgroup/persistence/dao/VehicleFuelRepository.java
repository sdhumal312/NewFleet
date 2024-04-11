package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleFuel;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleFuelRepository extends JpaRepository<VehicleFuel, Long> {

	@Modifying
	@Query("update VehicleFuel VF set VF.vFuel = ?1 where VF.fid = ?2")
	void updateVehicleFuel(String vFuel, long fid);
	
    List<VehicleFuel> findAll(Sort sort);

    @Override
    void delete(VehicleFuel role);
     
    @Modifying
	@Query("UPDATE VehicleFuel VF SET VF.markForDelete = 1 where VF.fid = ?1")
	void deleteVehicleFuel(long fid);
    
    VehicleFuel findByVFuel(String vFuel);
    
    long count();

	@Query("FROM VehicleFuel VF where VF.fid = ?1 AND VF.markForDelete = 0")
	VehicleFuel getVehicleFuelByID(long fid);
	
	@Override
	@Query("FROM VehicleFuel VF where VF.markForDelete = 0")
	public List<VehicleFuel> findAll();
	
	
	@Query("SELECT VF FROM VehicleFuel VF "
			+ " INNER JOIN VehicleFuelPermission VFP ON VFP.fid = VF.fid"
			+ " where VFP.companyId = ?1 AND VFP.markForDelete = 0 AND VF.markForDelete = 0")
	public List<VehicleFuel> findAllByPermission(Integer companyId);
}
