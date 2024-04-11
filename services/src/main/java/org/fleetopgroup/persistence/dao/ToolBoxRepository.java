package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.ToolBox;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ToolBoxRepository extends JpaRepository<ToolBox, Long> 
{
	@Query("FROM ToolBox where toolBoxName = ?1 AND companyId = ?2 AND markForDelete = 0")
	public ToolBox validateToolBoxName(String toolBoxName, Integer companyId) throws Exception;
	
	@Query("FROM ToolBox where companyId = ?1 AND markForDelete = 0")
	List<ToolBox> getToolBoxListByCompanyId(Integer companyId) throws Exception;
	
	@Query("FROM ToolBox where toolBoxId = ?1 AND companyId = ?2 AND markForDelete = 0")
	ToolBox findByToolBoxId(Long toolBoxId , Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE ToolBox  SET toolBoxName =?2, description =?3 where toolBoxId = ?1 AND companyId = ?4 AND markForDelete = 0 ")
	public void updateToolBoxByExpenseId(Long TyreExpense_ID, String TyreExpense_Name, String description, Integer company_id);
	
	@Modifying
	@Query("UPDATE ToolBox SET markForDelete = 1 where toolBoxId = ?1")
	public void deleteToolBoxById(Long id) throws Exception;
	
}
