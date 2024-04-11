package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.Companydirector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CompanydirectorRepository extends JpaRepository<Companydirector, Integer> {

	List<Companydirector> findAll();

	@Query("FROM Companydirector D where D.company_id = ?1 AND D.markForDelete = 0")
	List<Companydirector> getCompanydirectorList(Integer company_id);

	@Query("FROM Companydirector DDT where DDT.com_directors_name = ?1 AND DDT.markForDelete = 0")
	Companydirector getCompany(String verificationToken);

	@Query("FROM Companydirector DDT where DDT.company_id = ?1  AND DDT.markForDelete = 0")
	Companydirector getCompanyByID(Integer dri_id);

	@Query("FROM Companydirector DDT where DDT.com_directors_name = ?1 and DDT.company_id =?2 AND DDT.markForDelete = 0")
	Companydirector validateCompanydirectorName(String dri_DocType,Integer company_Id);
	
	@Query("FROM Companydirector DDT where DDT.com_directors_id = ?1 AND DDT.markForDelete = 0 AND DDT.company_id = ?2")
	Companydirector getCompanydirectorByID(Integer com_directors_id, Integer companyId);
	
	@Modifying
	@Query("UPDATE Companydirector DDT SET DDT.markForDelete = 1 where DDT.com_directors_id = ?1 AND DDT.company_id = ?2")
	void deleteCompanydirector(Integer com_directors_id, Integer companyId) throws Exception;

}
