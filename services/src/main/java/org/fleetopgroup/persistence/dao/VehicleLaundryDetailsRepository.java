package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleLaundryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleLaundryDetailsRepository extends JpaRepository<VehicleLaundryDetails, Long>{

	@Query("FROM VehicleLaundryDetails where laundryInvoiceId = ?1 and markForDelete = 0")
	List<VehicleLaundryDetails>  getVehicleLaundryDetailsById(Long invoiceId) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleLaundryDetails SET markForDelete = 1 where vehicleLaundryDetailsId = ?1")
	public void removeVehicleLaundry(Long vehicleLaundryDetailsId) throws Exception;
}
