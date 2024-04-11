package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner, Integer> {

	@Query("from VehicleOwner where VEHID =?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public List<VehicleOwner> list_Of_Vehicle_ID_VehicleOwner(Integer vehicle_id, Integer companyId) throws Exception;

	@Query("from VehicleOwner where VEHID =?1 and VEH_OWNER_NAME = ?2 AND markForDelete = 0")
	public List<VehicleOwner> listofSortVehiclePurchase(String vehiclePurchase, String name) throws Exception;

	@Query("from VehicleOwner where VEH_OWNER_NAME =?1 and VEH_OWNER_SERIAL = ?2 AND COMPANY_ID = ?3 AND VEHID = ?4 AND markForDelete = 0")
	public List<VehicleOwner> Validate_VehicleOwner_name(String vehicleOwnerName, String vehicleOwnerSerial, Integer companyId, Integer vid)
			throws Exception;

	@Query("from VehicleOwner where VOID =?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public VehicleOwner Get_Vehicle_Owner(int doc_id, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE VehicleOwner SET markForDelete = 1 where VOID= ?1 AND COMPANY_ID = ?2")
	public void Delete_VehicleOwner(int doc_id, Integer companyId) throws Exception;
	
	@Query("from VehicleOwner where VEH_OWNER_NAME =?1 AND COMPANY_ID = ?2 AND VEHID = ?3 AND markForDelete = 0")
	public List<VehicleOwner> Validate_VehicleOwner_name(String vehicleOwnerName, Integer companyId, Integer vid)
			throws Exception;
}
