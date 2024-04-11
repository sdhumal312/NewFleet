package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.UreaManufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;

@Repository
public interface UreaManufacturerRepository extends JpaRepository<UreaManufacturer, Long>{

	@Query(" FROM UreaManufacturer WHERE manufacturerName = ?1 AND companyId = ?2 AND markForDelete = 0 ")
	List<UreaManufacturer> validateUreaTypes(String ureaTypeName, Integer companyId) throws Exception;
	
	@Query("FROM UreaManufacturer WHERE companyId = ?1 AND markForDelete = 0")
	List<UreaManufacturer> getUreaTypesListByCompanyId(Integer company_id) throws Exception;
	
	@Modifying
	@Query("UPDATE UreaManufacturer SET markForDelete = 1 where ureaManufacturerId = ?1")
	public void deleteUreaTypeById(Long id) throws Exception;
	
}
