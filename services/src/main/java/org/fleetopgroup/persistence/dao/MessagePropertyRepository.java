package org.fleetopgroup.persistence.dao;


import org.fleetopgroup.persistence.model.MessageProperty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessagePropertyRepository extends JpaRepository<MessageProperty, Long> {
 
	/*@Query("FROM Branch DDT where DDT.branch_id = ?1")
    public Branch getBranchByID(Integer dri_id);
    
	@Query("FROM Branch DDT where DDT.branch_name = ?1")
	public Branch validateBranchName(String Branch_name);
	
	
	@Query("select count(p) from Branch p where p.branch_status = 'ACTIVE'")
	public Long countActiveBranch() throws Exception;
	
	@Modifying
	@Query("DELETE From Branch b Where b.branch_id= ?1 ")
	public void deleteBranch_ID(Integer branch_id) throws Exception;
	
	
	@Query("FROM Branch DDT where DDT.company_id = ?1")
	public List<Branch> SearchBranchLisrCompanyID(Integer company_ID)throws Exception;*/

}
