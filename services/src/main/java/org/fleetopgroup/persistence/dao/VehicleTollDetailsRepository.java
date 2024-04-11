package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleTollDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface VehicleTollDetailsRepository extends JpaRepository<VehicleTollDetails, Long> {

	@Query("from VehicleTollDetails v where v.companyId =?1 AND v.markForDelete = 0")
	public List<VehicleTollDetails>  getVehicleTollDetails(Integer companyId)throws Exception;
	
	@Query("from VehicleTollDetails v where v.companyId =?1 AND v.customerId = ?2 AND v.markForDelete = 0")
	public List<VehicleTollDetails>  validateVehicleTollDetails(Integer companyId, String customerId)throws Exception;
	
	@Query("from VehicleTollDetails v where v.vehicleTollDetailsId =?1 AND v.markForDelete = 0")
	public VehicleTollDetails	getVehicleTollDetails(Long vehicleTollDetailsId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleTollDetails v SET v.customerId = ?1 , v.walletId = ?2, v.description = ?3 , v.lastUpdatedById =?4, lastUpdatedOn =?5  where v.vehicleTollDetailsId =?6")
	public void updateVehicleTollDetails(String customerId,String walletId, String description, Long updatedBy, Date updatedOn, Long id)throws Exception;
	
	@Query("from VehicleTollDetails v "
			+ " INNER JOIN VehicleExtraDetails VE ON VE.vehicleTollDetailsId = v.vehicleTollDetailsId"
			+ " where VE.vid = ?1 AND v.markForDelete = 0")
	public VehicleTollDetails	getVehicleTollDetailsByVid(Integer vid) throws Exception;
	
	@Modifying
	@Query("update VehicleTollDetails set markForDelete = 1 where vehicleTollDetailsId = ?1")
	public void deleteVehicleToll(Long id)throws Exception;
	
}
