package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.Companylogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompanylogoRepository extends JpaRepository<Companylogo, Integer> {

	
	List<Companylogo> findAll();
	
	@Query("FROM Companylogo DDT where DDT.company_id = ?1")
	public Companylogo getCompanyLogo(int logo_id) throws Exception;

	/*@Query("FROM Company DDT where DDT.company_name = ?1")
	Company getCompany(String verificationToken);

	@Query("FROM Company DDT where DDT.company_id = ?1")
	Company getCompanyByID(Integer dri_id);

	@Query("FROM Company DDT where DDT.company_name = ?1")
	Company validateCompanyName(String dri_DocType);*/
	
	@Query("SELECT MAX(log_id) FROM Companylogo")
	public long getCompanylogoMaxId() throws Exception;
	
	@Query("FROM Companylogo where log_id > ?1 AND log_id <= ?2")
	public List<Companylogo> getCompanylogoList(Integer startLimit, Integer endLimit) throws Exception;

}
