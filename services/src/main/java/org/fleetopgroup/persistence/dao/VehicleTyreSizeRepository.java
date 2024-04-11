/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleTyreSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface VehicleTyreSizeRepository extends JpaRepository<VehicleTyreSize, Integer> {

	@Modifying
	@Query("update VehicleTyreSize VT set VT.TYRE_SIZE = ?1, VT.TYRE_SIZE_DESCRITION= ?2,  VT.LASTMODIFIEDBYID =?3,  VT.LASTMODIFIED_DATE = ?4 where VT.TS_ID = ?5 AND VT.companyId = ?6")
	public void update_VehicleTyre_size(String tyre_size, String tyre_size_descrition,  Long updateById, Date updateDate, Integer tid, Integer companyid);

	@Query("FROM VehicleTyreSize VT Where VT.TYRE_SIZE = ?1 and VT.companyId = ?2 and VT.markForDelete = 0 AND VT.ownTyreSize = 1")
	public VehicleTyreSize findByTYRE_SIZE(String tyre_size, Integer companyId);
	
	@Query("FROM VehicleTyreSize VT Where VT.TYRE_SIZE = ?1 and VT.markForDelete = 0 AND VT.ownTyreSize = 0")
	public VehicleTyreSize findByTYRE_SIZE(String tyre_size);


	@Modifying
	@Query("update VehicleTyreSize VT SET VT.markForDelete = 1 where VT.TS_ID = ?1 and VT.companyId = ?2")
	public void delete_Vehicle_TyreSize(Integer tid, Integer companyId);

	long count();

	@Query("From VehicleTyreSize VT where VT.TS_ID = ?1 and VT.companyId = ?2 AND VT.markForDelete = 0 AND VT.ownTyreSize = 1")
	public VehicleTyreSize get_Vehicle_Tyre_ID(Integer TS_id, Integer companyid);
	
	@Query("From VehicleTyreSize VT where VT.TS_ID = ?1 AND VT.markForDelete = 0 AND VT.ownTyreSize = 0")
	public VehicleTyreSize get_Vehicle_Tyre_ID(Integer TS_id);
	
	
	@Query("From VehicleTyreSize VT where VT.companyId = ?1 and VT.markForDelete = 0 AND VT.ownTyreSize = 1")
	List<VehicleTyreSize> findAllByCompanyId(Integer companyId);
	
	@Query("From VehicleTyreSize VT where VT.markForDelete = 0 AND VT.ownTyreSize = 0")
	List<VehicleTyreSize> findAll();
}
