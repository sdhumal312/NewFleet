package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TripRoutefixedIncome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TripRouteFixedIncomeRepository extends JpaRepository<TripRoutefixedIncome, Integer> {

	

	@Query("From TripRoutefixedIncome RT where RT.routefixedID = ?1 AND RT.companyId= ?2  ")
	public TripRoutefixedIncome getTripRoutefixedIncome(Integer RoutefixedID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE TripRoutefixedIncome RT SET RT.markForDelete=1 where RT.routefixedID = ?1 AND RT.companyId= ?2 ")
	public void deleteTripRoutefixedIncome(Integer routefixedID, Integer companyId) throws Exception;

	
}