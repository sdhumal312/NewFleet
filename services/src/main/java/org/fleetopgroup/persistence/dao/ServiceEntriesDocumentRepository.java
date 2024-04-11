package org.fleetopgroup.persistence.dao;

import java.util.List;

/**
 * @author fleetop
 *
 *
 *
 */

import org.fleetopgroup.persistence.model.ServiceEntriesDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ServiceEntriesDocumentRepository extends JpaRepository<ServiceEntriesDocument, Long> {

	// upload Document save
	// public void uploadServiceEntriesDocument(ServiceEntriesDocument ServiceEntriesdocument) throws Exception;

	// get Document
	@Query("From ServiceEntriesDocument where service_documentid = ?1 AND companyId = ?2 AND markForDelete = 0")
	public ServiceEntriesDocument getServiceEntriesDocument(Long ServiceEntries_id, Integer companyId) throws Exception;

	// upload to Old Document Update
	// public void updateOldServiceEntriesDocument(ServiceEntriesDocument  ServiceEntriesDocument) throws Exception;

	@Query(nativeQuery = true, value = "SELECT * FROM ServiceEntriesDocument where service_documentid > ?1 AND service_documentid <= ?2")
	public List<ServiceEntriesDocument> getServiceEntriesDocumentList(long startLimit, long endLimit) throws Exception;
	
	@Query("SELECT COUNT(SD) From ServiceEntriesDocument SD")
	public long getServiceEntriesDocumentCount() throws Exception;

}
