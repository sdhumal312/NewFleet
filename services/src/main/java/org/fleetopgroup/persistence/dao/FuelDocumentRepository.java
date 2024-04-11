package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.FuelDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FuelDocumentRepository extends JpaRepository<FuelDocument, Long> {

	@Query("FROM FuelDocument WHERE fueldocid=?1 AND companyId = ?2 AND markForDelete = 0")
	public FuelDocument get_Fuel_Document_Details(Long fuel_id, Integer companyId);

	@Query("SELECT MAX(fueldocid) FROM FuelDocument")
	public long getFuelDocumentMaxId() throws Exception;
	
	@Query("FROM FuelDocument where fueldocid > ?1 AND fueldocid <= ?2")
	public List<FuelDocument> getFuelDocumentList(Integer startLimit, Integer endLimit) throws Exception;
}
