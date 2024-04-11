/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleTyreLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface VehicleTyreLayoutRepository  extends JpaRepository<VehicleTyreLayout, Long>{

    //public VehicleTyreLayout registerNewTyreLayout(VehicleTyreLayout VehicleTyreLayout) throws Exception;
	
	//public VehicleTyreLayout UpdateTyreLayout(VehicleTyreLayout VehicleTyreLayout) throws Exception;

	@Query("From VehicleTyreLayout WHERE VEHICLE_ID =?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public List<VehicleTyreLayout> Get_Vehicle_Type_Details(Integer Vehicle_id, Integer companyId)  throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleTyreLayout SET markForDelete = 1 WHERE VEHICLE_ID =?1 AND COMPANY_ID = ?2 ")
	public void Delete_VehicleTyreLayout(Integer Vehicle_id, Integer companyId) throws Exception;
}
