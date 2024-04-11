/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.TripSheetOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TripSheetOptionsRepository extends JpaRepository<TripSheetOptions, Integer> {


	@Query("From TripSheetOptions TO where TO.companyId = ?1 and TO.markForDelete = 0")
	List<TripSheetOptions> findAllByCompanyId(Integer companyId);

	
	
	/*@Query("From tripsheetoptions TO where TO.markForDelete = 0")
	List<TripSheet> findAll();*/
	
	@Query("FROM TripSheetOptions TO Where TO.tripsheetextraname = ?1 and TO.companyId = ?2 and TO.markForDelete = 0")
	public TripSheetOptions findBytripsheetextraname(String tripsheetextraname, Integer companyId);


	@Query("FROM TripSheetOptions TO Where TO.tripsheetoptionsId = ?1 and TO.companyId = ?2 and TO.markForDelete = 0")
	public TripSheetOptions get_TripSheet_Options_Id(Long tripsheetoptionsId, Integer companyId);


	@Modifying
	@Query("update TripSheetOptions TO set TO.tripsheetextraname = ?1, TO.tripsheetextradescription= ?2,  TO.lastModifiedById =?3,  TO.lastupdated = ?4 where TO.tripsheetoptionsId = ?5 AND TO.companyId = ?6")
	public void update_TripSheet_Options_Name(String tripsheetextraname, String tripsheetextradescription,
			Long lastModifiedById, Date lastupdated, Long tripsheetoptionsId, Integer companyId);


	@Modifying
	@Query("update TripSheetOptions TO SET TO.markForDelete = 1 where TO.tripsheetoptionsId = ?1 and TO.companyId = ?2")
	public void delete_Vehicle_TyreSize(Long tripsheetoptionsId, Integer companyId);


	@Query("From TripSheetOptions TO where TO.companyId = ?1 and TO.markForDelete = 0")
	public List<TripSheetOptions> listTripExtraOptions(Integer companyId);
	


}