package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TallyCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TallyCompanyRepository extends JpaRepository<TallyCompany, Long>{
	
	@Query("FROM TallyCompany where companyName = ?1 AND companyId = ?2 AND markForDelete = 0")
	List<TallyCompany> validateTallyCompanyName(String companyName, Integer companyId) throws Exception;
	
	@Query("FROM TallyCompany where companyId = ?1 AND markForDelete = 0")
	List<TallyCompany> getTallyCompanyListByCompanyId(Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE TallyCompany SET markForDelete = 1 where tallyCompanyId = ?1")
	public void deleteTallyCompanyById(Long id) throws Exception;
	
	@Query("FROM TallyCompany where companyId = ?1 AND tallyCompanyId IN(?2) AND markForDelete = 0")
	List<TallyCompany> getTallyCompanyListByCompanyId(Integer companyId, String ids) throws Exception;
	
	
}