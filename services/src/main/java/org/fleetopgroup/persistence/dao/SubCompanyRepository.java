/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.SubCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface SubCompanyRepository extends JpaRepository<SubCompany, Long> {

	@Query("From SubCompany SC where SC.companyId = ?1 AND SC.markForDelete = 0 ")
	public List<SubCompany> findBycompanyId(Integer companyId);

	@Query("From SubCompany SC where SC.subCompanyName = ?1 AND SC.companyId = ?2 AND SC.markForDelete = 0 ")
	public SubCompany findBySubcompanyName(String subCompanyName, Integer companyId);
	
	@Query("From SubCompany SC where SC.subCompanyId = ?1 AND SC.companyId = ?2 AND SC.markForDelete = 0 ")
	public SubCompany findBySubcompanyId(Long subCompanyId, Integer companyId);

	@Modifying
	@Query("UPDATE SubCompany SC SET SC.markForDelete = 1  where SC.subCompanyId = ?1 AND SC.companyId = ?2 ")
	public void deleteSubCompany(Long subcompanyId,  Integer companyId);
	
	@Query("From SubCompany SC where SC.subCompanyId = ?1 AND SC.companyId = ?2 AND SC.markForDelete = 0 ")
	public SubCompany findSubCompanyBySubCompanyId(Long subCompanyId , Integer companyId);
	
	
}
