/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.sql.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.TripDailyGroupCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface TripDailyGroupCollectionRepository extends JpaRepository<TripDailyGroupCollection, Long> {

	/*@Query("FROM TripDailyGroupCollection AS T WHERE T.TRIP_OPEN_DATE=?1 AND T.VEHICLE_GROUP_ID=?2 AND T.TRIP_CLOSE_STATUS=?3 ")
	public TripDailyGroupCollection GET_TripDailyGroupCollection_CloseDate(Date fromDate, Long vEHICLEGROUP,
			String string);
*/
	@Query("FROM TripDailyGroupCollection AS T WHERE T.TRIP_OPEN_DATE=?1 AND T.VEHICLE_GROUP_ID=?2 AND T.TRIP_STATUS_ID=?3 AND T.COMPANY_ID = ?4 AND T.markForDelete = 0")
	public TripDailyGroupCollection GET_TripDailyGroupCollection_CloseDate(Date fromDate, long vEHICLEGROUP,
			short string, Integer companyId);
	
	@Query("FROM TripDailyGroupCollection AS T WHERE T.TRIP_OPEN_DATE=?1 AND T.TRIP_STATUS_ID=?2 AND T.COMPANY_ID = ?3 AND T.markForDelete = 0")
	public List<TripDailyGroupCollection> List_TripDailyGroupCollection_Get_all_details(Date fromDate,
			short TRIP_CLOSE_STATUS, Integer companyId);

	/**
	 * @param fromDate
	 * @param tRIP_CLOSE_STATUS
	 * @return
	 */
	@Query("FROM TripDailyGroupCollection AS T WHERE T.TRIP_OPEN_DATE=?1 AND T.TRIP_STATUS_ID=?2  AND T.COMPANY_ID = ?3 AND T.markForDelete = 0")
	public List<TripDailyGroupCollection> List_TripDailyGroupCollection_Get_all_details_Date(java.util.Date fromDate,
			short tRIP_CLOSE_STATUS, Integer companyId);

}
