package org.fleetopgroup.persistence.dao;


import java.util.List;

import org.fleetopgroup.persistence.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BranchRepository extends JpaRepository<Branch, Integer> {
 
	@Query("FROM Branch DDT where DDT.branch_id = ?1 AND DDT.company_id = ?2 AND DDT.markForDelete = 0")
    public Branch getBranchByID(Integer dri_id, Integer companyId);
    
	@Query("FROM Branch DDT where DDT.branch_name = ?1 AND DDT.company_id = ?2 AND DDT.markForDelete = 0")
	public List<Branch> validateBranchName(String Branch_name, Integer companyId);
	
	
	@Query("select count(p.id) from Branch p where p.branch_status = 'ACTIVE' and p.company_id = ?1 AND p.markForDelete = 0")
	public Long countActiveBranch(Integer company_ID) throws Exception;
	
	@Modifying
	@Query("UPDATE Branch b SET b.markForDelete = 1 Where b.branch_id= ?1 AND b.company_id = ?2")
	public void deleteBranch_ID(Integer branch_id, Integer companyId) throws Exception;
	
	
	@Query("FROM Branch DDT where DDT.company_id = ?1 AND DDT.markForDelete = 0")
	public List<Branch> SearchBranchLisrCompanyID(Integer company_ID)throws Exception;
	

	@Query("SELECT count(b.id) FROM Branch b WHERE b.company_id = ?1 AND b.markForDelete = 0")
	public Long countTotalBranch(Integer company_ID)throws Exception;
	
	@Modifying
	@Query("UPDATE Branch b SET b.branch_status = ?3 Where b.branch_id= ?1 AND b.company_id = ?2")
	public void updateBranchStatus(Integer branch_id, Integer companyId, String status) throws Exception;
	
	

}
