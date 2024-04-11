
package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.StatusChangeRemark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusChangeRemarkRepository extends JpaRepository<StatusChangeRemark, Long> {

//	@Query(nativeQuery = true, value = "SELECT * FROM VehicleStatusRemark where vid = ?1 AND companyId = ?2  AND markForDelete = 0 ORDER BY vehicleStatusRemarkId DESC LIMIT 1")
//	VehicleStatusRemark getLatestVehicleStatusRemark(int vid, int companyId);
	
}
