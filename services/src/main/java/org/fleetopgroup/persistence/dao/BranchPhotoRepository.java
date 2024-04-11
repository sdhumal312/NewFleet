package org.fleetopgroup.persistence.dao;


import java.util.List;

import org.fleetopgroup.persistence.model.BranchPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BranchPhotoRepository extends JpaRepository<BranchPhoto, Integer> {
 

	@Query("FROM BranchPhoto DDT where DDT.branch_id = ?1 AND DDT.companyId = ?2 AND DDT.markForDelete = 0")
	public List<BranchPhoto> listBranchPhoto(int diverPhoto, Integer companyId);

	@Query("FROM BranchPhoto DDT where DDT.branch_photoid = ?1 AND DDT.companyId = ?2 AND DDT.markForDelete = 0")
	public BranchPhoto getBranchPhoto(int Branch_photoid, Integer companyId);
	
	@Modifying
	@Query("UPDATE  BranchPhoto b SET b.markForDelete = 1 Where b.branch_photoid= ?1 AND b.companyId = ?2")
	public void deleteBranchPhoto(Integer Branch_Photoid, Integer companyId)  throws Exception;
	
	@Query("select count(p) from BranchPhoto p where p.branch_id = ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public Long getPhotoCount(int Branch_docid, Integer companyId);

	@Query("SELECT MAX(branch_photoid) FROM BranchPhoto")
	public long getBranchPhotoMaxId() throws Exception;
	
	@Query("FROM BranchPhoto where branch_photoid > ?1 AND branch_photoid <= ?2")
	public List<BranchPhoto> getBranchPhotoList(Integer startLimit, Integer endLimit) throws Exception;
}
