package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.CommentType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentTypeRepository extends JpaRepository<CommentType, Integer>{
	
	@Query("FROM CommentType where commentTypeName = ?1 AND companyId = ?2 AND markForDelete = 0")
	List<CommentType> validateCommentTypeName(String commentTypeName, Integer companyId) throws Exception;
	
	@Query("FROM CommentType where commentTypeId = ?1 AND markForDelete = 0")
	public CommentType findCommentTypeById(Long commentTypeId)throws Exception;
	
	@Modifying
	@Query("UPDATE CommentType SET markForDelete = 1 where commentTypeId = ?1")
	public void deleteTallyCompanyById(Integer id) throws Exception;
	
	
	@Query("FROM CommentType where commentTypeId = ?1 and markForDelete = 0")
	public CommentType findPartSubCategoryById(Integer commentTypeId)throws Exception;
	 
	@Query("SELECT ct.commentTypeId From CommentType as ct where ct.companyId = ?1 AND ct.markForDelete = 0")
	public Page<CommentType> getDeployment_Page_CommentType(Integer companyId, Pageable pageable);
	
	@Query("FROM CommentType where companyId = ?1 AND markForDelete = 0")
	public List<CommentType> getCommentTypeList(Integer companyId)throws Exception;
}