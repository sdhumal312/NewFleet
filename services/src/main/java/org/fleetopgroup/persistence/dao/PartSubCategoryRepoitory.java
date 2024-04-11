package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.PartSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PartSubCategoryRepoitory extends JpaRepository<PartSubCategory, Integer>{

	@Query("FROM PartSubCategory where markForDelete = 0")
	List<PartSubCategory>  findAll();
	
	@Query("FROM PartSubCategory where companyId = ?1 AND markForDelete = 0")
	List<PartSubCategory>  findAllByCompanyId(Integer companyId);
}
