package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.DriverDocType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DriverDocTypeRepository extends JpaRepository<DriverDocType, Long> {

	@Modifying
	@Query("update DriverDocType DDT set DDT.dri_DocType = ?1, DDT.lastModifiedById = ?2 , DDT.lastModifiedOn = ?3 where DDT.dri_id = ?4 and DDT.company_Id = ?5")
	void updateDriverDocType(String dri_DocType, Long modifiedBy, Timestamp modifiedOn, long dri_id, Integer companyId);
	
    List<DriverDocType> findAll(Sort sort);

     
    @Modifying
	@Query("update DriverDocType DDT set DDT.markForDelete = 1 where DDT.dri_id = ?1 and DDT.company_Id = ?2")
	void deleteDriverDocType(long dri_id, Integer companyId);
    
    @Query("FROM DriverDocType DDT where DDT.dri_DocType = ?1 and DDT.company_Id = ?2 and DDT.markForDelete = 0")
    DriverDocType validateDriDocType(String dri_DocType, Integer company_Id);
    
	@Query("FROM DriverDocType DDT where DDT.dri_id = ?1 and DDT.company_Id = ?2")
	DriverDocType getDriverDocTypeByID(long dri_id, Integer companyid);

    long count();
    
    @Query("FROM DriverDocType DDT where DDT.company_Id = ?1 and DDT.markForDelete = 0")
    List<DriverDocType> findAllByCompanyId(Integer	company_Id);
    
    @Query("FROM DriverDocType DDT where DDT.company_Id = ?1 and DDT.markForDelete = 0 AND DDT.dri_DocType LIKE ('DL%')")
    DriverDocType findByDocType(Integer	company_Id);
}
