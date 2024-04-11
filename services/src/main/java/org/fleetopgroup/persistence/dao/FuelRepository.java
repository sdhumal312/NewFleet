package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.Fuel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FuelRepository extends JpaRepository<Fuel, Long> {
	
	@Query("From Fuel p  where p.fuel_id= ?1")
	public Fuel getFuelList(Integer fuelId);

	public List<Fuel> findAll();

	@Query("FROM Fuel where created= ?1 ORDER BY fuel_id desc")
	public List<Fuel> listUPYesterdayFuel(Date yesterday) throws Exception;

	@Query("From Fuel p  where p.fuel_id= ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public Fuel getFuel(Long fuelId, Integer companyId);

	@Modifying
	@Query("UPDATE Fuel SET lastupdated=?2,lastModifiedById=?3,markForDelete = 1 WHERE fuel_id = ?1 AND companyId = ?4")
	public void deleteFuel(Long fuel,Timestamp toDate, long userId, Integer companyId);

	@Query("from Fuel where vid= ?1 and fuel_tank= ?2 AND markForDelete = 0 ")
	public List<Fuel> listOfGetvehicelpartialFuel(Integer vid, Integer fuelTank);


	@Query("select count(*) from Fuel ")
	public Long countFuel() throws Exception;

	@Query("select count(*) from Fuel where companyId = ?1 AND markForDelete = 0")
	public Long countFuelById(Integer companyId) throws Exception;

	
	@Query("select count(*) from Fuel where created= ?1 AND companyId = ?2 AND markForDelete = 0 ")
	public Long countFuelTodayEntries(Date todaydate, Integer companyId) throws Exception;

	@Query("from Fuel where vid = ?1 and fuel_meter < ?2 and fuel_date < ?3 ORDER BY  fuel_meter, fuel_date desc ")
	public List<Fuel> findMissingOddmeterFirstDESC_Vaule_Liter_Amount_DELETE(Integer vid, Integer fuelOddMeter, Date fuelDate);

	// missing oddmeter value
	/**
	 * the value Fuel calculation using Odometer Desc value in search v_id,
	 * current_odometer & fuel_date also
	 */
	@Query("from Fuel where vid =?1 and fuel_meter <= ?2 and fuel_date <= ?3  ORDER BY  fuel_meter desc ")
	public List<Fuel> findMissingOddmeterFirstDESC_Vaule_Liter_Amount(Integer fuel_vehicleid, Integer fuel_Odd_Meter,
			Date fuel_date);

	@Query("from Fuel where vid = ?1 and fuel_meter >= ?2 and fuel_date >= ?3 ORDER BY  fuel_meter asc ")
	public List<Fuel> findMissingOddmeterSecondASC_Vaule_Liter_Amount(Integer fuel_vehicleid, Integer fuel_Odd_Meter,
			Date fuel_date);

	@Query("from Fuel where vid =?1 and fuel_meter > ?2 and fuel_date > ?3 ORDER BY  fuel_meter, fuel_date asc ")
	public List<Fuel> findMissingOddmeterSecondASC_Vaule_Liter_Amount_DELETE(Integer fuel_vehicleid,
			Integer fuel_Odd_Meter, Date fuel_date);

	@Modifying
	@Query("update Fuel set fuel_meter_old= ?1 , fuel_usage= ?2, fuel_kml= ?4, fuel_cost= ?5 where fuel_id= ?3 ")
	public void update_Missing_OddMeter_KM_cost(Integer OldFullTank_OpringKM, Integer OldFullTank_OpringKM_Usage,
			Long FuellFullTank_ID, Double km, Double cost);

	@Modifying
	@Query("update Fuel set fuel_meter_old= ?1, fuel_usage= ?2 where fuel_id= ?3 AND companyId = ?4")
	public void update_Missing_OddMeter_Usage_Partial(Integer OldFullTank_OpringKM, Integer OldFullTank_OpringKM_Usage,
			Long FuellFullTank_ID, Integer companyId);

	@Modifying
	@Query("update Fuel set fuel_kml= ?2, fuel_cost= ?3 where fuel_id= ?1 AND companyId = ?4")
	public void update_Missing_FuelNextFull_KM_cost(Long FuellFullTank_ID, Double km, Double cost, Integer companyId);

	@Query("select sum(fuel_amount) from Fuel where  ( vendor_id= ?1 and paymentTypeId= 2 and fuel_vendor_paymodeId = 2 OR vendor_id= ?1 "
			+ " and paymentTypeId= 2 and fuel_vendor_paymodeId = 3 ) AND companyId = ?2 AND markForDelete = 0")
	public List<Double> listOFCountTotal_Cost_NotPaid(Integer vendorId, Integer companyId) throws Exception;


	@Query("from Fuel where  vendor_name= ?1 and fuel_payment= ?2 ")
	public List<Fuel> listFuel_vendor_History(String vendor_name, String fuel_payment) throws Exception;

	/* Update the partialFuel */
	@Modifying
	@Query("update Fuel f set f.fuel_tank= ?2 where f.vid= ?1 AND f.companyId = ?2")
	public void updatepartialFuel(Integer fuel_vehicleid, Integer fuel_tank, Integer companyId);

	@Query("from Fuel where  fuel_reference= ?1 ")
	public List<Fuel> listFuelValidate(String refernceNumber);

	@Query("from Fuel where fuel_vendor_approvalID= ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<Fuel> getVendorApproval_IN_FuelTable_List(Long VendorApproval_Id, Integer companyId) throws Exception;

	@Query("From Fuel where fuel_TripsheetID= ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<Fuel> Get_TripSheet_IN_FuelTable_List(Long Tripsheet_Id, Integer companyId) throws Exception;

	// Fuel validate of the APPROVAL Status Entries to enter the values
	@Query("from Fuel where  (fuel_id= ?1 AND  fuel_vendor_paymodeId=3 AND fuel_vendor_approvalID != NULL "
			+ " OR  fuel_id= ?1 AND  fuel_vendor_paymodeId=1 AND fuel_vendor_approvalID != NULL) AND markForDelete = 0 AND companyId = ?2 ")
	public List<Fuel> getFuelValidate_APPROVAL_Status(Long Fuel_ID, Integer companyId);


	@Query("from Fuel as F where F.vendor_id= ?1 AND F.companyId = ?2 AND markForDelete = 0")
	public List<Fuel> Vendor_delete_ValidateIn_Fuel(Integer vendor_Id, Integer companyId) throws Exception;

	/**
	 * @param vid
	 * @param pageable
	 * @return
	 */
	@Query("SELECT F.fuel_id From Fuel as F where F.vid=?1 AND F.companyId = ?2 AND F.markForDelete = 0 ORDER BY F.fuel_date DESC , F.fuel_meter DESC ")
	public Page<Fuel> findByVid(Integer vid, Integer companyId, Pageable pageable);


	@Query(" SELECT v.fuel_id FROM Fuel as v  "
			+ " INNER JOIN Vehicle VH ON VH.vid = v.vid"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VH.vehicleGroupId AND VGP.user_id = ?2"
			+ " Where v.companyId =?1 AND v.markForDelete = 0 ORDER BY v.fuel_id desc ")
	public Page<Fuel> getDeployment_Page_Fuel(Integer companyId, long id, Pageable pageable);

	@Query(" SELECT v.fuel_id FROM Fuel as v  "
			+ " Where v.companyId =?1 AND v.markForDelete = 0 ORDER BY v.fuel_id desc ")
	public Page<Fuel> getDeployment_Page_Fuel(Integer companyId, Pageable pageable);

	
	@Modifying
	@Query("Update Fuel set fuel_document_id= ?1, fuel_document= ?2 where fuel_id= ?3 AND companyId = ?4")
	public void Update_FuelDocument_ID_to_Fuel(Long fueldocid, boolean b, Long fuel_id, Integer companyId);
	
	@Query("SELECT v.fuel_id FROM Fuel as v  Where v.vendor_id=?1 AND markForDelete = 0 ORDER BY v.fuel_id desc ")
	public Page<Fuel> getDeployment_Page_FuelVendor_Page(Integer VendorId, Pageable pageable);

	@Query("From Fuel F "
			+ " INNER JOIN Vehicle V ON V.vid = F.vid "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = ?2"
			+ " where F.fuel_Number = ?1 AND F.companyId = ?3 AND F.markForDelete = 0")
	public Fuel getFuelByNumber(Long fuel_id, long id, Integer companyId) throws Exception;
	
	@Query("From Fuel F  where F.fuel_Number = ?1 AND F.companyId = ?2 AND F.markForDelete = 0")
	public Fuel getFuelByNumber(Long fuel_id,Integer companyId) throws Exception;
	
	@Query("select COUNT(DISTINCT F.vid) from Fuel F "
			+ " INNER JOIN Vehicle V ON V.vid = F.vid "
			+ " WHERE F.companyId = ?1 AND F.markForDelete = 0 AND V.vStatusId <> 4 ")
	public Long fuelEntriesCreatedOnDistinctVehicles(Integer companyId) throws Exception;
	
	@Query("select DISTINCT F.vid from Fuel F "
			+ " INNER JOIN Vehicle V ON V.vid = F.vid "
			+ " WHERE F.companyId = ?1 AND F.markForDelete = 0 AND V.vStatusId <> 4 ")
	public List<Fuel> listFuelEmtriesCreatedOnVehicles(Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE Fuel F SET markForDelete = 1  where F.fuel_TripsheetID = ?1")
	public void deleteFuelByTripSheetId(Long fuel_id) throws Exception;
	
	@Query("from Fuel where fuel_id = ?1")
	public Fuel getCurrentFuelDetails(Long fuelId) throws Exception;
	
	@Query(nativeQuery = true, value = "select * from Fuel where vid = ?1 and fuelDateTime < ?2 AND markForDelete = 0 order by fuelDateTime desc limit 1")
	public Fuel getPreviousFuelDetails(Integer vid , Date date)throws Exception;
	

	@Modifying
	@Query("UPDATE Fuel F SET gpsUsageKM = ?2  where F.fuel_id = ?1")
	public void updateGPSUsageKM(Long fuelId, Double gpsUsageKM) throws Exception;

	@Query(nativeQuery = true, value = "select * From Fuel F  where F.fuelInvoiceId = ?1 AND F.companyId = ?2 AND F.markForDelete = 0 limit 1")
	public Fuel getfueldetailsbyfuelInvoiceId(Long fuelInvoiceId, Integer companyId);

	@Query(nativeQuery = true, value = "select * From Fuel F  where F.fuel_id <> ?1 AND F.fuelInvoiceId = ?2 AND F.companyId = ?3 AND F.markForDelete = 0 limit 1")
	public Fuel getfueldetailsbyfuelInvoiceIdAndFuelId(Long fuelId, Long fuelInvoiceId, Integer companyId);
	
	@Query(" SELECT F.fuel_id FROM Fuel as F  "
			+ " Where F.fuel_date between ?1 AND ?2 AND F.companyId =?3 AND F.markForDelete = 0 ORDER BY F.fuel_id desc ")
	public Page<Fuel> getPageFuelConsumptionByTransactionDate(Timestamp startDate, Timestamp endDate, int companyId, Pageable pageable);
	
	@Query(" SELECT F.fuel_id FROM Fuel as F  "
			+ " Where F.created between ?1 AND ?2 AND F.companyId =?3 AND F.markForDelete = 0 ORDER BY F.fuel_id desc ")
	public Page<Fuel> getPageFuelConsumptionByCreatedDate(Timestamp startDate, Timestamp endDate, int companyId, Pageable pageable);
	
	@Query("select sum(F.fuel_liters) from Fuel as F where  F.fuel_TripsheetID = ?1 and F.companyId = ?2 and F.markForDelete = 0 ")
	public Long getNewLogicForTripsheeet(Long tripSheetID, Integer companyId) throws Exception;

	@Query("select F.fuel_price from Fuel as F where fuel_TripsheetID= ?1 AND companyId = ?2 AND markForDelete = 0")
	public double getFuelCostPerLiter(Long Tripsheet_Id, Integer companyId) throws Exception;
	
	@Query("SELECT F.fuel_Number FROM Fuel AS F WHERE F.companyId = ?1 AND F.fuel_id = ?2 AND F.markForDelete = 0")
	public Long getFuel_Number(Integer companyId, Long fuel_id ) throws Exception;
	
	@Query("select sum(F.fuel_amount) from Fuel as F where F.fuel_TripsheetID= ?1 AND F.companyId = ?2 AND F.markForDelete = 0")
	public Double getTotalFuelAmount(Long Tripsheet_Id, Integer companyId) throws Exception;
	
	@Query("from Fuel as F where F.vid=?1 AND  F.companyId = ?2 AND F.markForDelete = 0")
	public List<Fuel> getFuelByVId(Integer vid,  Integer companyId) throws Exception;
	
}
