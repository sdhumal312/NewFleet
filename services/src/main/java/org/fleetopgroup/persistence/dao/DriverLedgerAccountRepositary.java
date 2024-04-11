/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.DriverLedger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SANJU
 *
 */
@Repository
public interface DriverLedgerAccountRepositary extends JpaRepository<DriverLedger, Integer> {

}
