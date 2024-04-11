package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.FuelDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartDocumentRepository extends JpaRepository<FuelDocument, Long> {

	
}
