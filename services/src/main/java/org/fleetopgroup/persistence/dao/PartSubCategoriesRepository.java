package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.PartSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PartSubCategoriesRepository extends JpaRepository<PartSubCategory, Integer>{
	
	@Query("FROM PartSubCategory where subCategoryName = ?1 AND companyId = ?2 AND markForDelete = 0")
	List<PartSubCategory> validatePartSubCategoriesName(String subCategoryName, Integer companyId) throws Exception;
	
	@Query("FROM PartSubCategory where companyId = ?1 AND markForDelete = 0")
	List<PartSubCategory> getPartSubCategoryListByCompanyId(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE PartSubCategory SET markForDelete = 1 where subCategoryId = ?1")
	public void deleteTallyCompanyById(Integer id) throws Exception;
	
	
	@Query("FROM PartSubCategory where subCategoryId = ?1 and markForDelete = 0")
	public PartSubCategory findPartSubCategoryById(Integer subCategoryId)throws Exception;
	 
	
	
}