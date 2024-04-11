package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.Companybank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CompanybankRepository extends JpaRepository<Companybank, Integer> {

	List<Companybank> findAll();

	@Query("FROM Companybank DDT where  DDT.company_id = ?1 AND DDT.markForDelete = 0")
	List<Companybank> getCompanybankList(Integer company_id);

	@Query("FROM Companybank DDT where DDT.com_bank_account = ?1 AND DDT.markForDelete = 0")
	Companybank getCompany(String verificationToken);

	@Query("FROM Companybank DDT where DDT.company_id = ?1 AND DDT.markForDelete = 0")
	Companybank getCompanyByID(Integer dri_id);

	@Query("FROM Companybank DDT where DDT.com_bank_account = ?1 AND DDT.markForDelete = 0 AND DDT.company_id = ?2")
	Companybank validateCompanybankAccount(String account_name, Integer companyId);
	
	@Query("FROM Companybank DDT where DDT.com_bank_id = ?1 AND DDT.markForDelete = 0 AND DDT.company_id = ?2")
	Companybank getCompanybankByID(Integer com_bank_id, Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE Companybank DDT SET DDT.markForDelete = 1 where DDT.com_bank_id = ?1 AND DDT.company_id = ?2")
	void deleteCompanybank(Integer com_bank_id, Integer companyId) throws Exception;

}
