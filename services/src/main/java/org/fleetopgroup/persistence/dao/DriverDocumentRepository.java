package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.DriverDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DriverDocumentRepository extends JpaRepository<DriverDocument, Integer> {

	//public void saveDriverDocument(DriverDocument driverDocument);

	//public void updateDriverDocument(DriverDocument driverDocument);

	@Query("From DriverDocument where driver_id= ?1 AND markForDelete = 0")
	public List<DriverDocument> listDriverDocument(int diverreminder);

	@Query("From DriverDocument where driver_documentid= ?1 AND companyId = ?2 AND markForDelete = 0")
	public DriverDocument getDriverDocuemnt(int driver_docid, Integer companyId);

	@Modifying
	@Query("UPDATE DriverDocument SET markForDelete = 1 WHERE driver_documentid = ?1 AND companyId = ?2")
	public void deleteDriverDocument(int driver_documentid, Integer companyId);
	
	@Modifying
	@Query("select count(*) from DriverDocument where driver_id= ?1 and markForDelete = 0")
	public Integer getDocumentCount(int driver_remid);
	
	@Query("SELECT COUNT(SD) From DriverDocument SD")
	public long getDriverDocumentCount() throws Exception;
	
	@Query("FROM DriverDocument where driver_documentid > ?1 AND driver_documentid <= ?2")
	public List<DriverDocument> getDriverDocumentList(Integer startLimit, Integer endLimit) throws Exception;
	
	@Query("SELECT MAX(driver_documentid) FROM DriverDocument")
	public long getMaxId() throws Exception;
	
}
