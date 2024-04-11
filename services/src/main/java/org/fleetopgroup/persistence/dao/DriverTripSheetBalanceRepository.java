package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.DriverTripSheetBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DriverTripSheetBalanceRepository extends JpaRepository<DriverTripSheetBalance, Long> {

	@Query("FROM DriverTripSheetBalance where driverId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public DriverTripSheetBalance getDriverTripSheetBalanceByDriverId(long driverId, Integer companyId) throws Exception;

}
