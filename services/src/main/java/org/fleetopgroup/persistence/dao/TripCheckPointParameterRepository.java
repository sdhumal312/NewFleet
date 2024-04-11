package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TripCheckPointParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TripCheckPointParameterRepository extends JpaRepository<TripCheckPointParameter, Integer> {

	@Query("From TripCheckPointParameter TCPP where TCPP.companyId = ?1 and TCPP.markForDelete = 0 ")
	List<TripCheckPointParameter> findBycompanyId(Integer companyId);

	@Query("From TripCheckPointParameter TCPP where TCPP.tripCheckParameterName =?1 AND TCPP.companyId = ?2 AND TCPP.markForDelete = 0 ")
	public TripCheckPointParameter findByName(String tripCheckParameterName, Integer companyId);

	@Modifying
	@Query("UPDATE TripCheckPointParameter TCPP SET TCPP.markForDelete = 1  where TCPP.tripCheckPointParameterId = ?1")
	public void deleteTripCheckPointParameter(long tripCheckPointParameterId);

	@Query("From TripCheckPointParameter TCPP where lower(TCPP.tripCheckParameterName) like CONCAT('%',?1,'%') AND TCPP.companyId = ?2 AND TCPP.markForDelete = 0 ")
	public List<TripCheckPointParameter> tripCheckPointParameterAutocomplete(String tripCheckParameterName, int companyId);
	

}
