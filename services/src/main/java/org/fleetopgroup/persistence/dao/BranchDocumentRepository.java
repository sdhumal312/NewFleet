package org.fleetopgroup.persistence.dao;


import java.util.List;

import org.fleetopgroup.persistence.model.BranchDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BranchDocumentRepository extends JpaRepository<BranchDocument, Integer> {
 
	@Query("FROM BranchDocument DDT where DDT.branch_id = ?1 AND DDT.companyId = ?2 AND DDT.markForDelete = 0")
	public List<BranchDocument> listBranchDocument(int Branch_id, Integer companyId);

	@Query("FROM BranchDocument DDT where DDT.branch_documentid = ?1 AND DDT.markForDelete = 0 AND DDT.companyId = ?2")
	public BranchDocument getBranchDocuemnt(int Branch_docid, Integer companyId);
	
	@Query("select count(p) from BranchDocument p where p.branch_id = ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public Long getBranchDocumentCount(int Branch_docid, Integer companyId);
	
	@Modifying
	@Query("UPDATE BranchDocument b SET b.markForDelete = 1 Where b.branch_documentid= ?1 AND b.companyId = ?2")
	public void deleteBranchDocumentBranch_ID(Integer Branch_documentid, Integer companyId) throws Exception;
	
	@Query("SELECT MAX(branch_documentid) FROM BranchDocument")
	public long getBranchDocumentMaxId() throws Exception;
	
	@Query("FROM BranchDocument where branch_documentid > ?1 AND branch_documentid <= ?2")
	public List<BranchDocument> getBranchDocumentList(Integer startLimit, Integer endLimit) throws Exception;
}
