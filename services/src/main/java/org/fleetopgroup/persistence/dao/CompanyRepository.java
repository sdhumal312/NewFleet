package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Query("FROM Company DDT where DDT.markForDelete = 0")
	List<Company> findAll();

	@Query("FROM Company DDT where DDT.company_name = ?1 AND DDT.markForDelete = 0")
	Company getCompany(String verificationToken);

	@Query("FROM Company DDT where DDT.company_id = ?1 AND DDT.markForDelete = 0")
	Company getCompanyByID(Integer dri_id);

	@Query("FROM Company DDT where DDT.company_name = ?1 AND DDT.markForDelete = 0")
	Company validateCompanyName(String companyName);
	
	@Query("FROM Company DDT where DDT.company_parent_id = ?1 AND DDT.company_status= 'SUBCOMPANY' AND DDT.markForDelete = 0")
	List<Company> getSubCompanyList(Integer dri_id);
    
	@Modifying
	@Query("UPDATE Company DDT SET DDT.markForDelete = 1 where DDT.company_id = ?1")
	void deleteCompanyDetails(Integer company_id) throws Exception;
	
	@Query("FROM Company DDT where DDT.company_id = ?1 and DDT.markForDelete = 0")
	Company getCompanyAdress(Integer company_id);

	@Query("Select C from Company C where C.markForDelete = 0")
	public Page<Company> getDeployment_Find_Page_MasterCompany_Details(Pageable pageable);	

}
