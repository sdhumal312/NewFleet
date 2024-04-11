package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.LoadTypeMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LoadTypesRepository  extends JpaRepository<LoadTypeMaster,Long>{
	
	@Query("FROM LoadTypeMaster where loadTypeName = ?1 AND companyId = ?2 AND markForDelete = 0")
	List<LoadTypeMaster> validateLoadTypes(String loadTypeName, Integer companyId) throws Exception;
	
	@Query("FROM LoadTypeMaster where companyId = ?1 AND markForDelete = 0")
	List<LoadTypeMaster> getLoadTypesListByCompanyId(Integer company_id) throws Exception;
	
	@Modifying
	@Query("UPDATE LoadTypeMaster SET markForDelete = 1 where loadTypesId = ?1")
	public void deleteLoadTypeById(Long id) throws Exception;	

}


