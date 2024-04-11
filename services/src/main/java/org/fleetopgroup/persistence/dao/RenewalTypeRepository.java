package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.List;

import org.fleetopgroup.persistence.model.RenewalType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RenewalTypeRepository extends JpaRepository<RenewalType, Integer> {

	@Modifying
	@Query("update RenewalType RT set RT.renewal_Type = ?1, RT.lastModifiedById = ?2, RT.lastModifiedOn = ?3 where RT.renewal_id = ?4 AND RT.companyId IN (?5, 0)")
	void updateRenewalType(String renewal_Type, Long modifiedBy, Timestamp modifiedOn, Integer renewal_id, Integer companyId);
	
    List<RenewalType> findAll(Sort sort);

    @Override
    void delete(RenewalType role);
    
    
     
    @Modifying
	@Query("update RenewalType RT set RT.markForDelete = 1 where RT.renewal_id = ?1 AND RT.companyId IN (?2, 0)")
	void deleteRenewalType(Integer renewal_id, Integer companyid);
    
    long count();
    
    @Query("From RenewalType RT where RT.renewal_Type = ?1 and RT.companyId IN (?2, 0) and RT.markForDelete = 0")
    List<RenewalType> getRenewalType(String renewal_Type, Integer companyId);

	@Query("From RenewalType RT where RT.renewal_id = ?1  and RT.companyId IN (?2, 0)")
	RenewalType getRenewalTypeByID(Integer renewal_id, Integer companyId);

	@Query("From RenewalType RT where RT.companyId IN (?1, 0) and RT.markForDelete = 0 Order By RT.renewal_Type ASC")
	List<RenewalType> findAll_VehicleMandatory_Renewal_Type(Integer companyId);
	
	@Query("From RenewalType RT where RT.companyId IN (?1, 0) and RT.markForDelete = 0")
	List<RenewalType> findAllByCompanyId(Integer companyId);
	
	@Query("From RenewalType RT where RT.companyId =?1 and RT.markForDelete = 0")
	List<RenewalType> findrenewalTypeByCompany(Integer companyId);
}
