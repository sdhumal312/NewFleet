/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TripRouteExpenseRange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TripRouteExpenseRangeRepository extends JpaRepository<TripRouteExpenseRange, Integer> {
	
	@Query("SELECT TRER.tripRouteExpenseRangeId From TripRouteExpenseRange as TRER where TRER.companyId = ?1 AND TRER.markForDelete = 0")
	public Page<TripRouteExpenseRange> getDeployment_Page_TripRouteExpenseRange(Integer companyId, Pageable pageable);

	@Query("From TripRouteExpenseRange TRER where TRER.routeId = ?1 and TRER.markForDelete = 0 ")
	List<TripRouteExpenseRange> findByRouteId(int routeId);
	
	@Query("From TripRouteExpenseRange TRER where TRER.routeId = ?1 AND TRER.expenseId = ?2 AND TRER.companyId = ?3 AND TRER.markForDelete = 0 ")
	public TripRouteExpenseRange getExpenseRangeByRouteIdAndExpenseId(int routeId , int expenseId, Integer companyId);

	@Modifying
	@Query("UPDATE TripRouteExpenseRange TRER SET TRER.expenseMaxLimt =?1 where TRER.routeId = ?2 AND TRER.expenseId = ?3 AND TRER.companyId = ?4 AND TRER.markForDelete = 0 ")
	public void updateTripRouteExpenseRange(Double expenseMaxLimt, int routeId, int expenseId, Integer company_id);
	
	@Modifying
	@Query("UPDATE TripRouteExpenseRange TRER SET TRER.markForDelete = 1  where TRER.tripRouteExpenseRangeId = ?1 AND TRER.companyId = ?2 ")
	public void deleteTripRouteExpenseRange(Long tripRouteExpenseRangeId,  Integer company_id);

}
