package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.DriverPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DriverPhotoRepository extends JpaRepository<DriverPhoto, Integer> {

	//public void addDriverPhoto(DriverPhoto diverPhoto);

	//public void updateDriverPhoto(DriverPhoto diverPhoto);

	@Query("From DriverPhoto where driver_id= ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<DriverPhoto> listDriverPhoto(int diverPhoto, Integer companyId);

	@Query("From DriverPhoto where driver_photoid= ?1 AND companyId = ?2 AND markForDelete = 0")
	public DriverPhoto getDriverPhoto(int driver_photoid, Integer companyId);

	@Modifying
	@Query("UPDATE DriverPhoto SET markForDelete = 1 WHERE driver_photoid = ?1 AND companyId = ?2")
	public void deleteDriverPhoto(int driver_Photoid, Integer companyId);
	
	@Query("SELECT MAX(driver_photoid) FROM DriverPhoto")
	public long getDriverPhotoMaxId() throws Exception;
	
	@Query("FROM DriverPhoto where driver_photoid > ?1 AND driver_photoid <= ?2")
	public List<DriverPhoto> getDriverPhotoList(Integer startLimit, Integer endLimit) throws Exception;

}
