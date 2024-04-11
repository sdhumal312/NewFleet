package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.ToolBoxDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ToolBoxDetailsRepository extends JpaRepository<ToolBoxDetails, Long> 
{
	@Query("FROM ToolBoxDetails where ToolBoxDetailsId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public ToolBoxDetails getToolBoxDetailsByToolBoxDetailsId(Long ToolBoxDetailsId, Integer companyId) throws Exception;
	
	@Query("FROM ToolBoxDetails where toolBoxId = ?1 AND companyId = ?2 AND markForDelete = 0")
	ToolBoxDetails getToolBoxDetailsByToolBoxId(Long toolBoxId , Integer companyId) throws Exception;
	
	@Query("FROM ToolBoxDetails where vid = ?1 AND companyId = ?2 AND markForDelete = 0")
	ToolBoxDetails getToolBoxDetailsByVid(Integer vid , Integer companyId) throws Exception;
	
	@Modifying
	@Query("UPDATE ToolBoxDetails  SET toolBoxId =?1, quantity =?2, description =?3 where ToolBoxDetailsId = ?4 AND vid = ?5 AND companyId = ?6 AND markForDelete = 0 ")
	public void updateToolBoxDetailsByToolBoxDetailsId(Long toolBoxId, Double quantity, String description,Long ToolBoxDetailsId, Integer vid ,Integer company_id);
	
	@Modifying
	@Query("UPDATE ToolBoxDetails SET markForDelete = 1 where ToolBoxDetailsId = ?1")
	public void deleteToolBoxDetailsByToolBoxDetailsId(Long ToolBoxDetailsId) throws Exception;

	@Query("FROM ToolBoxDetails where companyId = ?2 AND markForDelete = 0")
	public List<ToolBoxDetails> getAllToolBoxDetailsList(Integer company_id);
	
}
