package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.IVFleetExecutiveMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IVFleetExecutiveMappingRepository extends JpaRepository<IVFleetExecutiveMapping, Long> {

	@Query("FROM IVFleetExecutiveMapping where  ivExecutiveId = ?1 AND markForDelete = 0")
	public IVFleetExecutiveMapping findByIVExecutiveId(Long groupId) throws  Exception;

}
