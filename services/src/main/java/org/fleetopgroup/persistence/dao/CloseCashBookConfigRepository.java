package org.fleetopgroup.persistence.dao;

import java.sql.Date;
import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.CloseCashBookConfiguration;
import org.fleetopgroup.persistence.model.TripSheet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CloseCashBookConfigRepository extends JpaRepository<CloseCashBookConfiguration, Long> {


}
