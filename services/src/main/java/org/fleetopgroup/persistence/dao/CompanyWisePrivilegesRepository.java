package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.CompanyWisePrivileges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CompanyWisePrivilegesRepository extends JpaRepository<CompanyWisePrivileges, Long>{

	@Query("FROM CompanyWisePrivileges where companyId = ?1 AND priviledgeType = 1 AND markForDelete = 0")
	public List<CompanyWisePrivileges>   getCompanyWiseModulePrivilegesList(Integer	companyId) throws Exception;
	
	@Query("FROM CompanyWisePrivileges where companyId = ?1 AND priviledgeType = 2 AND markForDelete = 0")
	public List<CompanyWisePrivileges>   getCompanyWisePrivilegesList(Integer	companyId) throws Exception;
	
	@Modifying
	@Query("DELETE FROM CompanyWisePrivileges where companyId = ?1 AND priviledgeType = 1 AND markForDelete = 0")
	public void   deleteModulePrivileges(Integer	companyId) throws Exception;
	
	@Modifying
	@Query("DELETE FROM CompanyWisePrivileges where companyId = ?1 AND priviledgeType = 2 AND markForDelete = 0")
	public void   deleteFeildPrivileges(Integer	companyId) throws Exception;
	
	
	@Query("SELECT priviledgeId FROM CompanyWisePrivileges where companyId = ?1 AND priviledgeType = 2 AND markForDelete = 0")
	public List<Long>   getCompanyWisePrivilegesListIds(Integer	companyId) throws Exception;
	
	
}
