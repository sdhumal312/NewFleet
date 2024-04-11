/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.sql.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.TripDailyAllGroupDay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TripDailyAllGroupDayCollectionRepository extends JpaRepository<TripDailyAllGroupDay, Long> {

	@Query("FROM TripDailyAllGroupDay As a WHERE a.TRIP_OPEN_DATE=?1 AND a.TRIP_STATUS_ID=?2 AND a.COMPANY_ID = ?3 AND markForDelete = 0")
	public List<TripDailyAllGroupDay> Validate_All_GroupDepartment_CloseOrNot_list(java.util.Date trip_OPEN_DATE,
			short Status, Integer companyId);

	@Query("FROM TripDailyAllGroupDay as v  Where v.TRIP_STATUS_ID=?1 AND v.COMPANY_ID = ?2 AND markForDelete = 0 ORDER BY v.TRIPALLGROUPID desc ")
	public Page<TripDailyAllGroupDay> getDeployment_Page_TripDailyAllGroupDay(short status, Integer companyId, Pageable pageable);

	@Query("FROM TripDailyAllGroupDay As a WHERE a.TRIP_OPEN_DATE=?1 AND a.COMPANY_ID = ?2 AND a.markForDelete = 0")
	public TripDailyAllGroupDay GET_DETAILS_TO_TRIPSHEET_ALLGROUPDAY_COLLECTION_DETILS(Date fromDate, Integer companyId);

	/**
	 * @param rfidrcg
	 * @param rfidcard
	 * @param rfidusage
	 * @param booking
	 * @param total_DIESELEXPENSE
	 * @param advance
	 * @param total_WT
	 * @param collection_BALANCE
	 * @param expense_DAY
	 * @param total_BALANCE
	 * @param trip_REMARKS
	 * @param lastmodifiedby
	 * @param date
	 * @param tripallgroupid
	 */
	@Modifying
	@Query("UPDATE TripDailyAllGroupDay AS T SET T.RFIDRCG=?1, T.RFIDCARD=?2, T.RFIDUSAGE=?3, T.BOOKING=?4, T.TOTAL_DIESELEXPENSE=?5, T.ADVANCE=?6, T.TOTAL_WT=?7, T.COLLECTION_BALANCE=?8, "
			+ " T.EXPENSE_DAY=?9, T.TOTAL_BALANCE=?10, T.TRIP_REMARKS=?11, T.TRIP_STATUS_ID = 2,  T.LASTMODIFIED_BY_ID=?12, T.LASTUPDATED=?13  WHERE T.TRIPALLGROUPID=?14  AND T.COMPANY_ID = ?15")
	public void Update_AllGroup_Day_Collection_Closed_Details(Double rfidrcg, Double rfidcard, Double rfidusage,
			Double booking, Double total_DIESELEXPENSE, Integer advance, String total_WT, Double collection_BALANCE,
			Double expense_DAY, Double total_BALANCE, String trip_REMARKS, Long lastmodifiedby, java.util.Date date,
			Long tripallgroupid, Integer companyId);

	@Query("FROM TripDailyAllGroupDay As a WHERE a.TRIPALLGROUPID=?1 AND a.COMPANY_ID = ?2 AND a.markForDelete = 0")
	public TripDailyAllGroupDay GET_DETAILS_TO_TRIPSHEET_ALLGROUPDAY_ID(Long tRIPALLGROUPID, Integer companyId);

}
