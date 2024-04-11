package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.RouteFixedAllowance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteFixedAllowanceRpository extends JpaRepository<RouteFixedAllowance, Long> {

	@Modifying
	@Query("update RouteFixedAllowance set markForDelete = 1, lastUpdatdById =?2, lastUpdatdOn = ?3 where routeFixedAllowanceId = ?1")
	public void removeAllowanceDetails(Long routeFixedAllowanceId, Long updatedBy, Date updatedOn) throws Exception;
	
	@Query("select routeFixedAllowanceId, driJobId from RouteFixedAllowance where driJobId = ?1 and routeId =?2 and markForDelete = 0")
	public List<RouteFixedAllowance>  validateAllowanceForRoute(Integer driJobId, Integer routeId) throws Exception;
	
	@Query("FROM RouteFixedAllowance where routeId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<RouteFixedAllowance> getRouteFixedAllowanceList(Integer routeId, Integer companyId) throws Exception;
}
