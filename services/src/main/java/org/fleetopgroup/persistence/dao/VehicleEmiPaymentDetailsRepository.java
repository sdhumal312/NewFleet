package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleEmiPaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleEmiPaymentDetailsRepository extends JpaRepository<VehicleEmiPaymentDetails, Long>{
	
	@Modifying
	@Query("UPDATE VehicleEmiPaymentDetails SET markForDelete = 1 where vehicleEmiDetailsId = ?1")
	public void deleteVehicleEmiPaymentDetailsById(Long vehicleEmiDetailsId) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleEmiPaymentDetails SET markForDelete = 1 where vehicleEmiDetailsId = ?1 AND paymentStatusId =?2 ")
	public void deleteNonPaidVehicleEmiPaymentDetails(Long vehicleEmiDetailsId, short unPaidStatus) throws Exception;
	
	@Query("select COUNT(vehicleEmiDetailsId) from VehicleEmiPaymentDetails where vehicleEmiDetailsId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public long getVehicleEmiPaymentCount(Long vehicleEmiDetailsId, int companyId) throws Exception;
	
	@Query("select SUM(emiPaidAmount) from VehicleEmiPaymentDetails where vehicleEmiDetailsId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public double getVehicleEmiPaymentSum(Long vehicleEmiDetailsId, int companyId) throws Exception;
	
	@Query("FROM VehicleEmiPaymentDetails where vehicleEmiDetailsId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<VehicleEmiPaymentDetails> getVehicleEmiPaymentByEMIDetailsId(Long vehicleEmiDetailsId, int companyId) throws Exception;
	
}