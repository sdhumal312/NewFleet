package org.fleetopgroup.persistence.dao;


import org.fleetopgroup.persistence.model.TripGroupCollection;
import org.springframework.data.jpa.repository.JpaRepository;



public interface TripGroupCollectionRepository extends JpaRepository<TripGroupCollection, Long> {

	
	/*@Query("FROM TripGroupCollection AS T WHERE T.TRIP_OPEN_DATE = ?1 AND T.VEHICLE_GROUP=?2")
	public List<TripGroupCollection> Validate_TripGroupCollection(java.util.Date trip_OPEN_DATE, String vehicle_GROUP)
			throws Exception;*/
}
