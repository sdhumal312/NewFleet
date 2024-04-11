package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.LHPVDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LHPVDetailsRepository extends JpaRepository<LHPVDetails, Long>{

	@Query("FROM LHPVDetails where lhpvId = ?1")
	public List<LHPVDetails> validateLHPVDetails(Long lhpvId) throws Exception;
	
	@Query(value = "SELECT * FROM LHPVDetails where vid = ?1 AND markForDelete = 0 AND isTripSheetCreated = 0 ORDER BY lhpvDateTimeStamp DESC LIMIT 15", nativeQuery = true)
	public List<LHPVDetails> getLHPVDetailsDtoList(Integer vid) throws Exception;
	
	@Query("FROM LHPVDetails where lHPVDetailsId = ?1")
	public LHPVDetails getLHPVDetailsById(Long lHPVDetailsId) throws Exception;
	
	@Modifying
	@Query("UPDATE LHPVDetails SET isTripSheetCreated = 1 where lHPVDetailsId = ?1")
	public void updateTripSheetCreated(Long lHPVDetailsId) throws Exception;
	
	@Query(nativeQuery = true, value = "SELECT * FROM LHPVDetails l where l.lHPVDetailsId IN (?1) AND l.companyId = ?2 AND l.markForDelete = 0")
	public List<LHPVDetails> getLHPVDetailsList(String lhpvIds, Integer companyId) throws Exception;
	
}
