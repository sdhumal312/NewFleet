package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.CompanyFixedAllowance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CompanyFixedAllowanceRepository extends JpaRepository<CompanyFixedAllowance, Long> {

	public List<CompanyFixedAllowance> findAll();

	@Query("FROM CompanyFixedAllowance DDT where  DDT.COMPANY_ID = ?1 AND DDT.markForDelete=0")
	public List<CompanyFixedAllowance> get_CompanyFixedAllowance_List(Integer company_id);

	
	@Query("FROM CompanyFixedAllowance DDT where DDT.VEHICLEGROUP_ID = ?1 AND DDT.DRIVER_JOBTYPE_ID=?2 AND DDT.markForDelete=0 ")
	public CompanyFixedAllowance validate_CompanyFixedAllowance(Long Group_id, Integer dri_DocType);
	
	
	@Modifying
	@Query("UPDATE CompanyFixedAllowance DDT SET DDT.markForDelete=1 where DDT.COMFIXID = ?1")
	public void delete_CompanyFixedAllowance(Long FixedAllowance_ID) throws Exception;

}
