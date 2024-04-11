package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TransferQuantityDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferQuantityDetailsRepository extends JpaRepository<TransferQuantityDetails, Long> {
	


}
