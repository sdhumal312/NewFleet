package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.VehicleExpenseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleExpenseDetailsRepository extends JpaRepository<VehicleExpenseDetails, Long>{

	@Query("FROM VehicleExpenseDetails where expenseId = ?1 AND companyId = ?2 AND vid = ?3 AND expenseType = ?4 AND markForDelete = 0")
	public VehicleExpenseDetails validateVehicleExpenseDetails(Integer expenseId, Integer companyId, Integer vid, short type) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleExpenseDetails SET markForDelete = 1 where txnTypeId = ?1 AND expenseType = ?3 AND vid = ?2")
	public void  deleteVehicleExpenseDetails(Long fuelId, Integer vid, short expenseType) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleExpenseDetails SET markForDelete = 1 where tripExpenseId = ?1 and expenseType = ?2")
	public void  deleteVehicleExpenseDetails(Long expenseId, short expenseType) throws Exception;
	
	
	@Query("FROM VehicleExpenseDetails where txnTypeId = ?1 AND companyId = ?2 AND vid = ?3 AND expenseType = ?4 AND markForDelete = 0")
	public VehicleExpenseDetails validateVehicleExpenseDetails(Long expenseId, Integer companyId, Integer vid, short type) throws Exception;
	
	
	@Query("FROM VehicleExpenseDetails where txnTypeId = ?1 AND companyId = ?2 AND vid = ?3 AND markForDelete = 0")
	public  List<VehicleExpenseDetails> validateVehicleExpenseDetailsList(Long expenseId, Integer companyId, Integer vid) throws Exception;
	
}
