package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleStatus;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface VehicleStatusRepository extends JpaRepository<VehicleStatus, Long> {

	
	@Modifying
	@Query("update VehicleStatus VS set VS.vStatus = ?1, VS.lastModifiedBy = ?2, VS.lastupdated =?3 where VS.sid = ?4")
	void updateVehicleStatus(String vStatus, String lastModifiedBy, Date lastupdated, long sid);
	
    List<VehicleStatus> findAll(Sort sort);
    
    VehicleStatus findByVStatus(String vStatus);

    @Override
    void delete(VehicleStatus role);
     
    @Modifying
	@Query("delete VehicleStatus VS where VS.sid = ?1")
	void deleteVehicleStatus(long sid);
    
    long count();

	@Query("FROM VehicleStatus VS where VS.sid = ?1")
	VehicleStatus getVehicleStatusByID(long sid);
	
	@Override
	@Query("FROM VehicleStatus VS where VS.markForDelete = 0")
	List<VehicleStatus> findAll();
	
	@Query("SELECT VS FROM VehicleStatus VS "
			+ " INNER JOIN VehicleStatusPermission VSP ON VSP.sid = VS.sid "
			+ " where VSP.companyId = ?1 AND VSP.markForDelete = 0 AND VS.markForDelete = 0 order by VS.sid asc")
	List<VehicleStatus> findAll(Integer companyId);;
}
