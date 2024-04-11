package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.ServeyorDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServeyorDetailsRepository extends JpaRepository<ServeyorDetails, Long>{

	ServeyorDetails findByAccidentId(Long accidentId) throws Exception;
}
