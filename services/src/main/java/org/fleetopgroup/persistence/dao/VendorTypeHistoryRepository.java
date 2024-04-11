package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.VendorTypeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorTypeHistoryRepository extends JpaRepository<VendorTypeHistory, Long> {

}
