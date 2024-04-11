package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.MasterPartsPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MasterPartsPhotoRepository extends JpaRepository<MasterPartsPhoto, Long> {

	// save Master Photo
	//public void addMasterPartsPhoto(MasterPartsPhoto masterPartsPhoto) throws Exception;

	// parts photo to get part_id
	@Query("FROM MasterPartsPhoto WHERE partid= ?1 AND companyId= ?2 AND markForDelete = 0")
	public List<MasterPartsPhoto> listMasterPartsPhoto(Long partid, Integer companyId) throws Exception;

	// Get Part Photo_id in MasterParts
	@Query("FROM MasterPartsPhoto WHERE part_photoid= ?1 AND companyId= ?2 AND markForDelete = 0")
	public MasterPartsPhoto getMasterPartsPhoto(Long sid, Integer companyId) throws Exception;

	// Delete MasterPart Photo
	@Modifying
	@Query("UPDATE MasterPartsPhoto SET markForDelete = 1 WHERE part_photoid= ?1 AND companyId= ?2")
	public void deleteMasterPartsPhoto(Long Part_photoid, Integer companyId) throws Exception;

	@Query("SELECT MAX(part_photoid) FROM MasterPartsPhoto")
	public long getMasterPartsPhotoMaxId() throws Exception;
	
	@Query("FROM MasterPartsPhoto where part_photoid > ?1 AND part_photoid <= ?2")
	public List<MasterPartsPhoto> getMasterPartsPhotoList(Long startLimit, Long endLimit) throws Exception;
}