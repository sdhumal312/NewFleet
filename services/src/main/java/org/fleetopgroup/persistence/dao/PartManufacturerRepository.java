package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.PartManufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PartManufacturerRepository   extends JpaRepository<PartManufacturer, Integer> {
	
	@Modifying
	@Query("update PartManufacturer T SET T.pmName = ?1, T.pmdescription = ?2 , T.lastModifiedById = ?3, T.lastModifiedOn = ?4 where T.pmid = ?5 AND T.companyId = ?6")
	public void updatePartManufacturer(String pmName, String pmdescription,Long modifiedById, Timestamp modifiedOn,  Integer pmid, Integer companyId) throws Exception;

	public List<PartManufacturer> findAll();

	@Query("From PartManufacturer p where p.pmid = ?1 and p.companyId = ?2 AND p.isOwnPartManufacturer = 1 AND p.markForDelete = 0")
	public PartManufacturer getPartManufacturer(int sid, Integer companyId) throws Exception;
	
	@Query("From PartManufacturer p where p.pmid = ?1 and p.isOwnPartManufacturer = 0 AND p.markForDelete = 0")
	public PartManufacturer getPartManufacturer(int sid) throws Exception;


	@Modifying
	@Query("UPDATE PartManufacturer T SET T.markForDelete = 1 where T.pmid = ?1 and T.companyId = ?2")
	public void deletePartManufacturer(Integer status, Integer companyId) throws Exception;

	@Query("From PartManufacturer p where p.pmName = ?1 and p.companyId = ?2 and p.isOwnPartManufacturer = 1 and p.markForDelete = 0")
	public List<PartManufacturer> ValidatePartManufacturerName(String Manufacturer , Integer companyId) throws Exception;
	
	@Query("From PartManufacturer p where p.pmName = ?1 and p.isOwnPartManufacturer = 0 and p.markForDelete = 0")
	public List<PartManufacturer> ValidatePartManufacturerName(String Manufacturer ) throws Exception;


	@Query("From PartManufacturer p where p.pmName = ?1 and p.companyId = ?2 and p.isOwnPartManufacturer = 1 and p.markForDelete = 0")
	public PartManufacturer ValidateManufacturerName(String Manufacturer , Integer companyId) throws Exception;
	
	@Query("From PartManufacturer p where p.pmName = ?1 and p.isOwnPartManufacturer = 0 and p.markForDelete = 0")
	public PartManufacturer ValidateManufacturerName(String Manufacturer) throws Exception;
	
	
	@Query("From PartManufacturer p where p.companyId = ?1 AND p.isOwnPartManufacturer = 1 and p.markForDelete = 0")
	public List<PartManufacturer> listPartManufacturerByCompanyId(Integer companyId) throws Exception;
	
	@Query("From PartManufacturer p where  p.isOwnPartManufacturer = 0 and p.markForDelete = 0")
	public List<PartManufacturer> listPartManufacturerByCompanyId() throws Exception;
}