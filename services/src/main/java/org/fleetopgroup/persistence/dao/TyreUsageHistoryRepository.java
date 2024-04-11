/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TyreUsageHistory;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author fleetop
 *
 */
public interface TyreUsageHistoryRepository  extends JpaRepository<TyreUsageHistory, Long>{
	
}
