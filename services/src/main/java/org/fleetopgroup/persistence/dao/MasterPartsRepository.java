package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.MasterParts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MasterPartsRepository extends JpaRepository<MasterParts, Long> {

	
	public List<MasterParts> findAll();
	
	@Query("From MasterParts p  where p.companyId = ?1 AND p.markForDelete = 0")
	public List<MasterParts> findAllPartBYCompanyId(Integer companyId);


	@Query("From MasterParts p  where p.partid= ?1 AND (p.companyId = ?2 OR p.companyId = 0) AND p.markForDelete = 0")
	public MasterParts getMasterParts(Long part_id, Integer companyId) throws Exception;
	
	@Query("From MasterParts p  where p.partid= ?1 AND p.markForDelete = 0")
	public MasterParts getMasterParts(Long part_id) throws Exception;

	@Modifying
	@Query("UPDATE MasterParts T SET T.markForDelete = 1 where T.partid = ?1 AND T.companyId = ?2")
	public void deleteMasterParts(Long part_id, Integer companyId) throws Exception;

	// update Master Part Profile photo_id
	@Modifying
	@Query("UPDATE MasterParts SET part_photoid= ?1 WHERE partid = ?2 ")
	public void setMasterPartProfilePhoto(Long Part_photoid, Long PartId) throws Exception;


	public long count();

	// master part validate
	@Query("FROM MasterParts WHERE partnumber= ?1 AND partname= ?2 AND companyId = ?3 AND isOwnMaterParts = 1 AND markForDelete= 0")
	public List<MasterParts> GetMasterPartValidateForOwn(String  Part_No, String Part_name, Integer companyId) throws Exception;
	
	@Query("FROM MasterParts WHERE partnumber= ?1 AND partname= ?2 AND (companyId = ?3 OR companyId = 0 ) AND isOwnMaterParts = 0 AND markForDelete= 0")
	public List<MasterParts> GetMasterPartValidate(String  Part_No, String Part_name, Integer companyId) throws Exception;
	
	
	@Query("SELECT MP.partid FROM MasterParts MP WHERE ( MP.companyId = ?1 OR MP.companyId = 0 ) AND MP.isOwnMaterParts = 0 AND MP.autoMasterPart = 0 AND MP.markForDelete= 0")
	public Page<MasterParts> listParts(Integer companyId, Pageable pageable) throws Exception;
	
	@Query("SELECT MP.partid FROM MasterParts MP WHERE MP.companyId= ?1 AND MP.isOwnMaterParts = 1 AND MP.autoMasterPart = 0 AND MP.markForDelete= 0")
	public Page<MasterParts> listPartsForOwn(Integer companyId, Pageable pageable) throws Exception;
	
	
	@Query("FROM MasterParts MP WHERE MP.partnumber = ?1 AND MP.makerId= ?2 AND MP.companyId= ?3 AND MP.isOwnMaterParts = 1 AND MP.markForDelete= 0")
	public MasterParts validatePartNumberForOwn(String partnumber, Long makerId, Integer companyId) throws Exception;
	
	@Query("FROM MasterParts MP WHERE MP.partnumber = ?1  AND MP.makerId= ?2 AND ( MP.companyId = ?3 OR MP.companyId = 0 ) AND MP.isOwnMaterParts= 0 AND MP.markForDelete= 0")
	public MasterParts validatePartNumber(String partnumber, Long makerId, Integer companyId) throws Exception;

	@Query("SELECT COUNT(MP) FROM MasterParts MP WHERE MP.companyId= ?1 AND MP.markForDelete= 0")
	public long countByCompanyId(Integer companyId);
}