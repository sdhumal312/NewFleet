package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.ClothTypes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ClothTypesRepository extends JpaRepository<ClothTypes, Long>{
	
	@Query("FROM ClothTypes where companyId = ?1 AND markForDelete = 0")
	List<ClothTypes> getClothTypesListByCompanyId(Integer companyId) throws Exception;
	
	@Query("FROM ClothTypes where clothTypeName = ?1 AND companyId = ?2 AND markForDelete = 0")
	List<ClothTypes> validateClotypes(String clothTypeName, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE ClothTypes SET markForDelete = 1 where clothTypesId = ?1")
	public void deleteClothTypeById(Long id) throws Exception;
	
	@Query("FROM ClothTypes where clothTypesId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public ClothTypes getClothTypes(long clothtypeId, Integer companyId) throws Exception;
}
