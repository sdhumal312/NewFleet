package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.DriverDocumentHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DriverDocumentHistoryRepository extends JpaRepository<DriverDocumentHistory, Integer> {

	@Query("From DriverDocumentHistory where driver_id= ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<DriverDocumentHistory> listDriverDocHis(int diverDocHis_id, Integer companyId);

	@Query("From DriverDocumentHistory where driver_docHisid= ?1 AND companyId = ?2 AND markForDelete = 0")
	public DriverDocumentHistory getDriverDocHis(int diverDocHis_id, Integer companyId);

	@Modifying
	@Query("UPDATE DriverDocumentHistory SET markForDelete = 1 WHERE driver_doHisid = ?1 AND companyId = ?2")
	public void deleteDriverDocHis(int driver_doHistid, Integer companyId);

}
