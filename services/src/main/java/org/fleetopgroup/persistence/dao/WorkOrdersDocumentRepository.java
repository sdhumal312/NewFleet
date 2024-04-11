package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */

import java.util.List;
import org.fleetopgroup.persistence.model.WorkOrdersDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface WorkOrdersDocumentRepository  extends JpaRepository<WorkOrdersDocument, Long>{

	// upload Document save
	//public void uploadWorkOrdersDocument(WorkOrdersDocument WorkOrdersdocument) throws Exception;

	// get Document
	@Query("from WorkOrdersDocument where workorder_documentid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public WorkOrdersDocument getWorkOrdersDocument(Long WorkOrders_id, Integer companyId) throws Exception;

	@Query("SELECT MAX(workorder_documentid) FROM WorkOrdersDocument")
	public long getWorkOrderDocumentMaxId() throws Exception;
	
	@Query("FROM WorkOrdersDocument where workorder_documentid > ?1 AND workorder_documentid <= ?2")
	public List<WorkOrdersDocument> getWorkOrderDocumentList(Long startLimit, Long endLimit) throws Exception;
}
