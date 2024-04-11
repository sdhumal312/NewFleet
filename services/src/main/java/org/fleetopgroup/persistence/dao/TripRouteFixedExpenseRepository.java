package org.fleetopgroup.persistence.dao;

/**
 * @author fleetop
 *
 *
 *
 */

import org.fleetopgroup.persistence.model.TripRoutefixedExpense;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripRouteFixedExpenseRepository extends JpaRepository<TripRoutefixedExpense, Integer> {

	/*@Query("From TripRoutefixedExpense RT where RT.routeID = ?1")
	public List<TripRoutefixedExpense> listTripRouteFixedExpene(Integer routeID) throws Exception;
*/
	/*@Query("From TripRoutefixedExpense where expenseName = ?1 AND routeID = ?2")
	public List<TripRoutefixedExpense> ValidateTripRouteFixedExpene(String expenseName, Integer routeID)
			throws Exception;*/

	@Query("From TripRoutefixedExpense RT where RT.routefixedID = ?1 AND RT.companyId = ?2 AND RT.markForDelete = 0")
	public TripRoutefixedExpense getTripRoutefixedExpense(Integer RoutefixedID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE TripRoutefixedExpense RT SET RT.markForDelete = 1 where RT.routefixedID = ?1 AND RT.companyId = ?2")
	public void deleteTripRoutefixedExpense(Integer routefixedID, Integer companyId) throws Exception;

	@Query("From TripRoutefixedExpense RT where RT.triproute.routeID = ?1 AND RT.expenseId = ?2 AND RT.companyId = ?3 AND RT.markForDelete = 0")
	public TripRoutefixedExpense getTripRouteFixedExpeneByExpenseId(int int0, int int1, int int2)throws Exception;

	/*@Query("From TripRoutefixedExpense RT where RT.expenseName = ?1 AND RT.routeID = ?2")
	public TripRoutefixedExpense ValidateTripRoute(String expenseName, Integer routeID) throws Exception;*/
}