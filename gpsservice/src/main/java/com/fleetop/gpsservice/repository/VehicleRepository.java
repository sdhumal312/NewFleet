package com.fleetop.gpsservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fleetop.gpsservice.model.Vehicle;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	
	@Query("FROM Vehicle WHERE gpsVendorId =?1 AND company_Id =?2 AND markForDelete =0 ")
	public List<Vehicle> getVehicleListByGpsVendor(int vendorId,int companyId);

}
