package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.PartCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface PartCategoriesRepository  extends JpaRepository<PartCategories, Integer>{
	
	@Modifying
	@Query("update PartCategories T SET T.pcName = ?1, T.pcdescription = ?2 , T.lastModifiedById = ?3, T.lastModifiedOn = ?4, T.incPartIssueCateory = ?7 where T.pcid = ?5 and T.companyId = ?6")
	public void updatePartCategories(String pcName, String pcdescription,long modifiedById, Timestamp modifiedOn, Integer pcid, Integer companyId, boolean incPartIssueCateory) throws Exception;

	@Query("From PartCategories pc  where pc.isOwnPartCategory = 0 and pc.markForDelete = 0")
	public List<PartCategories> findAll();

	@Query("From PartCategories p where p.pcid = ?1 and p.companyId = ?2 and p.isOwnPartCategory = 1 and p.markForDelete = 0")
	public PartCategories getPartCategories(int sid, Integer companyid) throws Exception;
	
	@Query("From PartCategories p where p.pcid = ?1 and p.isOwnPartCategory = 0 and p.markForDelete = 0")
	public PartCategories getPartCategories(int sid) throws Exception;


	@Modifying
	@Query("update PartCategories T set T.markForDelete = 1 where T.pcid = ?1 and T.companyId = ?2")
	public void deletePartCategories(Integer partid, Integer companyId) throws Exception;

	@Query("From PartCategories p  where p.pcName = ?1 and p.companyId = ?2 and p.isOwnPartCategory = 1 and p.markForDelete = 0")
	public List<PartCategories> ValidateCategoriesName(String Categories, Integer companyId) throws Exception;
	
	@Query("From PartCategories p  where p.pcName = ?1 and p.isOwnPartCategory = 0 and p.markForDelete = 0")
	public List<PartCategories> ValidateCategoriesName(String Categories) throws Exception;
	
	
	@Query("From PartCategories p  where p.pcName = ?1 and p.companyId = ?2 AND p.isOwnPartCategory = 1 and p.markForDelete = 0")
	public PartCategories ValidatePCName(String Categories, Integer companyId) throws Exception;
	
	@Query("From PartCategories p  where p.pcName = ?1 AND p.isOwnPartCategory = 0 and p.markForDelete = 0")
	public PartCategories ValidatePCName(String Categories) throws Exception;
	
	@Query("From PartCategories pc  where pc.companyId = ?1 AND pc.isOwnPartCategory = 1 and pc.markForDelete = 0")
	public List<PartCategories> findAllByCompanyId(Integer companyId) throws Exception;
	
	@Query("From PartCategories pc  where pc.companyId = ?1 AND pc.isOwnPartCategory = 1 and pc.markForDelete = 0 AND pc.incPartIssueCateory = 1")
	public List<PartCategories> findCategoryByIncInIssue(Integer companyId) throws Exception;
	
	
}