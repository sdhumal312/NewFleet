package org.fleetopgroup.persistence.dao;


/**
 * @author fleetop
 *
 *
 *
 */

import org.fleetopgroup.persistence.model.RouteWiseTripSheetWeight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;



public interface TripSheetRouteWiseWeightRepository  extends JpaRepository<RouteWiseTripSheetWeight, Long>{

	@Modifying
	@Query("UPDATE RouteWiseTripSheetWeight SET markForDelete = 1 WHERE routeWiseTripSheetWeightId = ?1 AND companyId = ?2")
	public void deleteTripSheetRouteWiseWeight(Long routewiseWeightId, Integer companyId) throws Exception;
	
}
