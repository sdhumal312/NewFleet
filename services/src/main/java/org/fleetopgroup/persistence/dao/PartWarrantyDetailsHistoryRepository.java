package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.PartWarrantyDetailsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartWarrantyDetailsHistoryRepository	extends JpaRepository<PartWarrantyDetailsHistory, Long> {

}
