package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 */
import java.util.List;

import javax.persistence.EntityManager;

import org.fleetopgroup.persistence.model.VehicleDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleDocumentRepository extends JpaRepository<VehicleDocument, Integer> {

	EntityManager em2 = null;

	
	//public void addVehicleDocument(VehicleDocument vehicleDocument) throws Exception;
	
	@Modifying
	@Query("from VehicleDocument where vehid =?1 ")
	public void updateVehicleDocument(VehicleDocument vehicleDocument) throws Exception;

	@Modifying
	@Query("from VehicleDocument where vehid =?1 ")
	public List<VehicleDocument> listVehicleDocument(Integer vehicleDocument) throws Exception;

	@Modifying
	@Query("from VehicleDocument where vehid=?1 and name= ?2 AND companyId = ?3 AND markForDelete = 0")
	public List<VehicleDocument> listofSortVehicleDocument(Integer vehicleDocument, String name, Integer companyId) throws Exception;

	@Query("From VehicleDocument Where id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public VehicleDocument getVehicleDocument(int vehicle_id, Integer companyId) throws Exception;

	@Query("From VehicleDocument Where id = ?1 AND companyId = ?2 AND markForDelete = 0")
	public VehicleDocument getDownload(int vehicle_id, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE VehicleDocument vd SET vd.markForDelete = 1 WHERE vd.id = ?1 AND companyId = ?2")
	public void deleteVehicleDocument(int vehicle_id, Integer companyId) throws Exception;
	
	@Query("FROM VehicleDocument where id > ?1 AND id <= ?2")
	public List<VehicleDocument> getVehicleDocumentList(Integer startLimit, Integer endLimit) throws Exception;
	
	@Query("SELECT MAX(id) FROM VehicleDocument")
	public long getVehicleDocumentMaxId() throws Exception;
	
}
