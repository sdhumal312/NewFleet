package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.InspectionParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InspectionParameterRepository extends JpaRepository<InspectionParameter, Long> {
	
	@Query("FROM InspectionParameter as I INNER JOIN VehicleInspectionSheetToParameter as VI ON VI.inspectionParameterId = I.inspectionParameterId "
			+ " WHERE VI.inspectionSheetToParameterId = ?1 AND I.companyId = ?2 AND I.markForDelete = 0 ")
	public InspectionParameter getParameterById (Long inspectionParameterId,Integer companyId);

}
