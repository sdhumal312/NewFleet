package org.fleetopgroup.persistence.dao.mongo;

import org.fleetopgroup.persistence.document.VehicleAccidentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface VehicleAccidentRepository extends MongoRepository<VehicleAccidentDocument, Long> {

}
