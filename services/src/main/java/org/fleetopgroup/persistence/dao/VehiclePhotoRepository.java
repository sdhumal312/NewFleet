package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.VehiclePhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehiclePhotoRepository extends JpaRepository<VehiclePhoto, Integer> {
	
	/* Vehicle Photo */
  //	public void addVehiclePhoto(VehiclePhoto vehiclePhoto) throws Exception;

	@Query("from VehiclePhoto where vehid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<VehiclePhoto> listVehiclePhoto(Integer vehiclePhoto, Integer companyId) throws Exception;

	@Modifying
	@Query("from VehiclePhoto where vehid = ?1 and name= ?2 AND markForDelete = 0 ")
	public List<VehiclePhoto> listofSortVehiclePhoto(String vehiclePhoto, String name) throws Exception;

	@Query("from VehiclePhoto where id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public VehiclePhoto getVehiclePhoto(int Photo_id, Integer companyId) throws Exception;

	@Modifying
	@Query("From VehiclePhoto Where id = ?1")
	public VehiclePhoto getDownloadPhoto(int Photo_id) throws Exception;

	@Modifying
	@Query("UPDATE VehiclePhoto SET markForDelete = 1 WHERE id = ?1 AND companyId = ?2")
	public void deleteVehiclePhoto(int Photo_id, Integer companyId) throws Exception;
	
	@Query("SELECT MAX(id) FROM VehiclePhoto")
	public long getVehiclePhotoMaxId() throws Exception;
	
	@Query("FROM VehiclePhoto where id > ?1 AND id <= ?2")
	public List<VehiclePhoto> getVehiclePhotoList(Integer startLimit, Integer endLimit) throws Exception;

	
}
