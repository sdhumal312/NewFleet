package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.VehiclePurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehiclePurchaseRepository extends JpaRepository<VehiclePurchase, Integer> {
	
	/* Vehicle Purchase */
	//public void addVehiclePurchase(VehiclePurchase vehiclePurchase) throws Exception;

	@Modifying
	@Query("from VehiclePurchase where vehid =?1 and name = ?2 ")
	public void updateVehiclePurchase(VehiclePurchase vehiclePhoto) throws Exception;

	@Query("from VehiclePurchase where vehid =?1 AND markForDelete = 0")
	public List<VehiclePurchase> listVehiclePurchase(Integer vehiclePurchase) throws Exception;
	
	@Modifying
	@Query("from VehiclePurchase where vehid =?1 and name = ?2 AND markForDelete = 0")
	public List<VehiclePurchase> listofSortVehiclePurchase(String vehiclePurchase, String name) throws Exception;

	@Modifying
	@Query("from VehiclePurchase where id =?1 AND companyId = ?2 AND markForDelete = 0")
	public VehiclePurchase getVehiclePurchase(int doc_id, Integer companyId) throws Exception;
	
	@Query("from VehiclePurchase where id =?1 AND companyId = ?2")
	public VehiclePurchase getDownloadPurchase(int doc_id, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE VehiclePurchase SET markForDelete = 1 where id= ?1 AND companyId = ?2")
	public void deleteVehiclePurchase(int doc_id, Integer companyId) throws Exception;
}
