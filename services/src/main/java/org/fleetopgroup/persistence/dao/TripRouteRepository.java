package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.TripRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripRouteRepository extends JpaRepository<TripRoute, Integer> {

	public List<TripRoute> findAll();

	@Query("from TripRoute RT where RT.routeID = ?1 AND RT.companyId = ?2")
	public TripRoute getTripRoute(int RouteID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE TripRoute RT SET RT.markForDelete = 1 where RT.routeID = ?1 AND RT.companyId = ?2")
	public void deleteTripRoute(Integer routeId, Integer companyId) throws Exception;
	
	@Query("FROM TripRoute TR where TR.companyId= ?1 and TR.markForDelete = 0")
	public Page<TripRoute> findAllByCompanyId(Integer companyId, Pageable pageable) throws Exception;
	
	@Query("SELECT TR FROM TripRoute TR "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = TR.vehicleGroupId AND VGP.user_id = ?2"
			+ " where TR.companyId= ?1 and TR.markForDelete = 0")
	public Page<TripRoute> findAllByCompanyId(Integer companyId, long id, Pageable pageable) throws Exception;


	//public List<TripRoute> SearchOnlyRouteNAME(String Search) throws Exception;

	@Query("FROM TripRoute TR where TR.routeName = ?1 AND TR.companyId= ?2 and TR.markForDelete = 0")
	public TripRoute validateTripRoute(String name, Integer companyId) throws Exception ;
	
	@Query("FROM TripRoute TR where TR.companyId= ?1 and TR.markForDelete = 0")
	public List<TripRoute> listTripRoute(Integer companyId) throws Exception ;
	
	@Query("SELECT TR FROM TripRoute TR "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = TR.vehicleGroupId AND VGP.user_id = ?2"
			+ " where TR.companyId= ?1 and TR.markForDelete = 0")
	public List<TripRoute> listTripRoute(Integer companyId, long id) throws Exception ;
	
	@Query("from TripRoute RT where RT.routeName = ?1 AND RT.routeType=?2 and RT.markForDelete = 0")
	public List<TripRoute> Validate_TRIP_ROUTE_NAME(String routeName, Integer routeType);
	
	@Query("SELECT RT.routeApproximateKM from TripRoute RT where RT.routeID = ?1 and RT.markForDelete = 0")
	public Integer getTripRouteApproxKm(Integer routeId);

	@Query("from TripRoute RT where RT.companyId = ?1")
	List<TripRoute> getAllTripRouteByCompanyId(Integer companyId) throws Exception;
}