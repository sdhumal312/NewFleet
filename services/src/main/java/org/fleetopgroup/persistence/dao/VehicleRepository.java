package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 */
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.model.Vehicle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

	EntityManager em2 = null;

	public default void updateVehicle(Vehicle vehicle) throws Exception {
		Vehicle Vehicle = (Vehicle) em2.find(Vehicle.class, vehicle.getVid());
		Vehicle.setVehicle_registration(vehicle.getVehicle_registration());
	}

	@Query("FROM Vehicle WHERE (vehicle_registration = ?1 OR vehicle_chasis = ?2 OR vehicle_engine = ?3 ) AND markForDelete = 0 ")
	public List<Vehicle> listVehicleVaildateOLD(String vehicle_registration, String vehicle_chasis,
			String vehicle_engine) throws Exception;

	@Query("from Vehicle v where v.vid =?1 AND v.company_Id = ?2 AND v.markForDelete = 0")
	public Vehicle getVehicel(int vid, Integer companyId) throws Exception;

	@Query("From Vehicle v where v.vid = ?1 AND v.company_Id = ?2 AND v.markForDelete = 0 ")
	public Vehicle getVehicel1(Integer vid, Integer companyId) throws Exception;

	@Query("from Vehicle where vehicle_registration =?1 AND markForDelete = 0")
	public Vehicle getVehicelReg(String vehicle_register) throws Exception;

	@Modifying
	@Query("Update  Vehicle  SET lastModifiedById=?2,lastupdated=?3, markForDelete = 1 WHERE vid = ?1 AND company_Id = ?4")
	public void deleteVehicle(Integer vehicle_ID,Long userId,Timestamp toDate , Integer companyId) throws Exception;

	@Modifying
	@Query("update Vehicle set vehicle_photoid =?1 where vid=?2 AND company_Id =?3")
	public void setprofilePhoto(int vehiclephoto_id, Integer vehicle_id, Integer companyId) throws Exception;

	@Modifying
	@Query("update Vehicle  set vehicle_odometer =?2 where vid =?1  AND company_Id = ?3")
	public void updateCurrentOdoMeter(Integer vehicle_id, int oddmeter_id, Integer companyId) throws Exception;

	// Report
	@Query("from Vehicle")
	public List<Vehicle> listVehicleReport(Vehicle vehicle) throws Exception;

	@Query("From Vehicle  where vehicle_registration= ?1 ")
	public List<Vehicle> GETImportVehicel(String vehicle_re) throws Exception;
	
	@Query("select vid From Vehicle  where vehicleTypeId= ?1 AND company_Id= ?2 AND vStatusId NOT IN(?3,?4,?5)  AND markForDelete = 0  ")
	public List<Object[]> getVehicleId(long vehicleType,Integer companyId,short short1,short short2,short short3 ) throws Exception;
	
	@Query("select vid From Vehicle  where vehicleTypeId= ?1 AND company_Id= ?2 AND vStatusId NOT IN(?3,?4,?5) AND branchId = ?6 AND markForDelete = 0  ")
	public List<Object[]> getVehicleId(long vehicleType,Integer companyId,short short1,short short2,short short3,Integer branch ) throws Exception;
	
	@SuppressWarnings("unchecked")
	public default List<Vehicle> listVehiclePatternReport(String vehicle) throws Exception {
		TypedQuery<Vehicle> query = em2.createQuery("from Vehicle as v where '" + vehicle + "'", Vehicle.class);

		return (List<Vehicle>) query.getSingleResult();
	}

	@Query("from Vehicle v where v.vehicle_registration = ?1 ")
	public List<Vehicle> listVehicleImport(String vehicle) throws Exception;

	/*@Query("select count(p) from Vehicle p where p.vehicle_Status = 'Active'")
	public Long countActive() throws Exception;
*/
	/*@Query("select count(p) from Vehicle p Where p.vehicle_Ownership = ?1")
	public Long countOwnership(String Ownership) throws Exception;
*/
	@Query("from Vehicle v where  lower(v.vehicle_registration) Like ('% ?1 %') OR lower(v.vehicle_chasis) Like ('% ?1 %') OR lower(v.vehicle_engine) Like ('% ?1 %') OR lower(v.vehicle_SeatCapacity) Like ('% ?1 %')")
	public List<Vehicle> SearchVehicle(String vehicle) throws Exception;

	@Query("from Vehicle where  lower(vehicle_registration) Like ('% ?1 %')")
	public List<Vehicle> findDropdownList(String vehicle) throws Exception;

	// getVehicle_ID Current odometer values
	@Query("select v.vehicle_Odometer from Vehicle v Where v.vid = ?1 AND v.company_Id = ?2 ")
	public Integer updateCurrentOdoMeterGETVehicleToCurrentOdometer(int vid, Integer companyId) throws Exception;

	/*@Modifying
	@Query("UPDATE from Vehicle AS v SET v.vehicle_Status=?1 Where v.vid = ?2 ")
	public void Update_Vehicle_Status(String vehicleStatus, Integer vehicle_vid);
*/
	@Query("Select V.vid from Vehicle V where V.vStatusId= ?1 and V.company_Id = ?2 and V.markForDelete = 0")
	public Page<Vehicle> getDeployment_Page_Vehicle_Status(short Job_Type,Integer company_Id, Pageable pageable);
	
	@Query("Select v.vid from Vehicle v"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = ?3"
			+ " where v.vStatusId= ?1 and v.company_Id = ?2 and v.markForDelete = 0")
	public Page<Vehicle> getDeployment_Page_Vehicle_Status(short Job_Type,Integer company_Id, long id, Pageable pageable);
	
	
	@Modifying
	@Query("UPDATE from Vehicle AS v SET v.tripSheetID=?1, v.vStatusId = ?4 Where v.vid = ?2 AND v.company_Id = ?3 ")
	public void Update_Vehicle_Status_TripSheetID(Long tripSheetID, Integer vid, Integer companyId, short vStatusId);
	
	@Modifying
	@Query("UPDATE from Vehicle AS v SET v.vStatusId = ?1 Where v.vid = ?2 AND v.company_Id = ?3 ")
	public void update_Vehicle_Status(short status, Integer vid, Integer companyId);

	@Modifying
	@Query("UPDATE from Vehicle AS v SET v.lastOdameterUpdated = ?2 Where v.vid = ?1")
	public void UpdateOdameterUpdateDate(Integer vid, Timestamp date);
	
	@Modifying
	@Query("UPDATE from Vehicle AS v SET v.lastLhpvSyncDateTime = ?2 Where v.vid = ?1")
	public void updateLastLhpvSyncDateTime(Integer vid, Timestamp date);
	
	@Modifying
	@Query("UPDATE Vehicle set vehicle_ExpectedMileage= ?2, vehicle_ExpectedMileage_to= ?3 where vid= ?1 AND company_Id = ?4")
	public void updateVehicleKmplDetails(int vehicleId, Double vehicle_ExpectedMileage, Double vehicle_ExpectedMileage_to, Integer companyId);
	
	@Query("from Vehicle v"
			+ " where v.vehicleGroupId= ?1 and v.company_Id = ?2 and v.markForDelete = 0")
	public List<Vehicle> getVehicleDetailsFromVGroup(long vehicleGroupId,Integer company_Id);
	
	@Query("from Vehicle v"
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = v.vehicleGroupId AND VGP.user_id = ?3"
			+ " where v.vehicleGroupId= ?1 and v.company_Id = ?2 and v.markForDelete = 0")
	public List<Vehicle> getVehicleDetailsFromVGroup(long vehicleGroupId,Integer company_Id, long id);
	
	@Query("select count(V) from Vehicle V where V.company_Id = ?1 AND V.markForDelete = 0 AND V.vStatusId <> 4 ")
	public Long countTotalVehicles(Integer companyId) throws Exception;
	
	@Query("select count(V.vid) from Vehicle V where V.vehicle_ExpectedMileage = 0 AND V.vehicle_ExpectedMileage_to = 0 AND V.company_Id = ?1 AND V.markForDelete = 0 AND V.vStatusId <> 4 ")
	public Long countOfVehiclesWithoutMileage(Integer companyId) throws Exception;

	@Query("SELECT vStatusId, vehicle_registration from Vehicle where vid = ?1")
	public Vehicle getLimitedVehicleDetails(Integer vid) throws Exception;
	
	@Query("SELECT V.vid, V.vehicle_registration, V.company_Id from Vehicle AS V where V.gpsVendorId = ?1  AND V.markForDelete = 0 AND V.vStatusId <> 4")
	public List<Vehicle> getVehicleListByGpsVenordId(Integer vid) throws Exception;
	
	@Query("FROM Vehicle where vehicleTypeId = ?1 AND vehicleModalId = ?2 AND company_Id = ?3 AND markForDelete = 0 AND vStatusId <> 4 ")
	public List<Vehicle> getVehicleByTypeAndModal(Long vehicleType, Long vehicleModelId, Integer companyId)
			throws Exception ;
	
	@Query("FROM Vehicle where vehicleTypeId = ?1 AND vehicleModalId = ?2 AND branchId = ?3 AND company_Id = ?4 AND markForDelete = 0 AND vStatusId <> 4 ")
	public List<Vehicle> getVehicleByTypeModalAndBranch(Long vehicleType, Long vehicleModelId,Integer branchId, Integer companyId)
			throws Exception ;
	
	@Query("select count(V) from Vehicle V where V.company_Id = ?1 AND V.markForDelete = 0 ")
	public Long totalCountVehicles(Integer companyId) throws Exception;

	@Query("FROM Vehicle where vehicleModalId = ?1 AND company_Id = ?2 AND markForDelete = 0 ")
	public List<Vehicle> getvehicleByModelId(long long1, int int1);

	@Query("select count(V) FROM Vehicle WHERE vehicleModalId=?1 AND company_Id = ?2 AND markForDelete = 0 ")
	public Long checkVehicleByVehicleModel(Long vehicleModalId, Integer companyId);

	@Query("FROM Vehicle WHERE gpsVendorId =?1 AND company_Id =?2 AND markForDelete =0 ")
	public List<Vehicle> getVehicleListByGpsVendor(int gpsVendorId,int companyId);
	
	@Query("from Vehicle where REPLACE(vehicle_registration, '-', '') = ?1 AND markForDelete = 0")
	public Vehicle getVehicleByRegNo(String vehicle_register) throws Exception;
	
	
}
