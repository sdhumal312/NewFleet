package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VehicleIncomeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleIncomeDetailsRepository extends JpaRepository<VehicleIncomeDetails, Long>{

	@Query("FROM VehicleIncomeDetails where incomeId = ?1 AND companyId = ?2 AND vid = ?3 AND markForDelete = 0")
	public VehicleIncomeDetails validateVehicleIncomeDetails(Integer incomeId, Integer companyId, Integer vid) throws Exception;
	
	@Modifying
	@Query("UPDATE VehicleIncomeDetails SET markForDelete = 1 where tripincomeId = ?1 AND companyId = ?2 AND vid = ?3 AND txnTypeId = ?4")
	public void deleteVehicleIncomeDetails(Long incomeId, Integer companyId, Integer vid, Long txnTypeId) throws Exception;
	
}
