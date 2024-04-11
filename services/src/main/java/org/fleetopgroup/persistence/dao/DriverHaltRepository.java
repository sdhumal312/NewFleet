package org.fleetopgroup.persistence.dao;

import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.DriverHalt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DriverHaltRepository extends JpaRepository<DriverHalt, Long> {

	@Query("From DriverHalt p  where p.DRIVERID=?1 AND p.HALT_DATE_FROM= ?2 AND p.markForDelete = 0")
	public DriverHalt Validate_DriverHalt(int driver_id, Date driver_Date);

	@Query("From DriverHalt p  where p.DRIVERID= ?1 AND p.markForDelete = 0")
	public List<DriverHalt> list_DriverHalt(int driver_id);

	@Query("From DriverHalt p  where p.DRIVERID= ?1 AND p.markForDelete = 0")
	public List<DriverHalt> list_Date_DriverHalt(int driver_id, String from, String To);

	@Query("From DriverHalt p  where p.TRIPSHEETID=?1 AND p.COMPANY_ID = ?2 AND p.markForDelete = 0")
	public List<DriverHalt> Find_list_TripSheet_DriverHalt(Long TripSheetID, Integer companyId);

	@Query("From DriverHalt p  where p.DHID= ?1 AND p.markForDelete = 0")
	public DriverHalt get_DriverHalt(Long driver_id);

	@Modifying
	@Query("UPDATE DriverHalt T SET T.markForDelete = 1 where T.DHID = ?1 AND T.COMPANY_ID = ?2")
	public void delete_DriverHalt(Long driver_id, Integer companyId);

	@Modifying
	@Query("UPDATE DriverHalt SET VID=?1, REFERENCE_NO=?2, HALT_AMOUNT=?3, HALT_REASON=?4, LASTUPDATED=?5, LASTUPDATED_BY_ID=?6 where DHID=?7 AND COMPANY_ID =?8 ")
	public void update_DriverHalt(Integer vid, String reference_NO, Double halt_AMOUNT, String halt_REASON,
			Date lastupdated, Long lastupdated_BY_ID, Long dhid, Integer company_ID);

	@Modifying
	@Query("UPDATE DriverHalt T SET T.markForDelete = 1 where T.TRIPSHEETID = ?1")
	public void deleteDriverHaltByTripSheetId(Long driver_id);

}
