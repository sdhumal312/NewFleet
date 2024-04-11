package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VehicleAccidentPersonDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleAccidentPersonDetailsRepository extends JpaRepository<VehicleAccidentPersonDetails, Integer> {

	@Modifying
	@Query("UPDATE VehicleAccidentPersonDetails SET markForDelete = 1  WHERE vehicleAccidentPersonDetailsId =?1 AND companyId =?2 ")
	public void removeAccidentPerson(long vehicleAccidentPersonDetailsId, int companyId);

}
 