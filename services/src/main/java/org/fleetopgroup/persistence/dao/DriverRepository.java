package org.fleetopgroup.persistence.dao;

import java.sql.Timestamp;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;



public interface DriverRepository  extends JpaRepository<Driver, Integer> {
	
	@Query("From Driver p  where p.driver_dlnumber= ?1  AND p.companyId = ?3 AND p.markForDelete = 0 OR p.driver_empnumber=?2 AND p.companyId = ?3 AND p.markForDelete = 0")
	public List<Driver> ValidateDriver(String driver_DLnumber, String driver_Empnumber, Integer companyId);
	
	@Query("From Driver p  where p.driver_empnumber=?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public List<Driver> ValidateEmpDriver(String driver_Empnumber, Integer companyId);
	
	
	@Query("From Driver p  where p.companyId = ?2 AND p.markForDelete = 0 OR p.driver_empnumber=?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public List<Driver> ValidateDriver(String driver_Empnumber, Integer companyId);


	public List<Driver> findAll();

	@Query("SELECT p.driver_id From Driver as p where p.driJobId= ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public Page<Driver> getDeployment_Page_Driver_JobType(Integer Job_Type, Integer companyId, Pageable pageable);

	@Query("SELECT p.driver_id From Driver as p "
			+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = p.vehicleGroupId AND VGP.user_id = ?3"
			+ " where p.driJobId= ?1 AND p.companyId = ?2 AND p.markForDelete = 0")
	public Page<Driver> getDeployment_Page_Driver_JobType(Integer Job_Type, Integer companyId, long id, Pageable pageable);
	
	@Query("From Driver p INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = p.vehicleGroupId and VGP.user_id = ?3  where p.driver_id= ?1  and p.companyId = ?2 and p.markForDelete = 0")
	public Driver getDriver(int driver_id, Integer companyId, Long userId);
	
	@Query("From Driver p where p.driver_id= ?1  and p.companyId = ?2 and p.markForDelete = 0")
	public Driver getDriver(int driver_id, Integer companyId);

	@Query("From Driver p  where p.driver_empnumber= ?1")
	public Driver getDriverReg(String driver_register);

	
	@Modifying
	@Query("UPDATE  Driver T SET T.lastModifiedById=?1,T.lastupdated=?2,  T.markForDelete = 1 where T.driver_id = ?3 and T.companyId = ?4")
	public void deleteDriver(Long userId,Timestamp toDate ,Integer driver_id, Integer companyId);

	@Modifying
	@Query("update Driver set driver_photoid= ?1 where driver_id= ?2 ")
	public void setprofilePhoto(int Driverphoto_id, int driver_id);
	
	@Query("From Driver where driver_empnumber= ?1 ")
	public List<Driver> GetDriverEmpNumberToName(String empnumber) throws Exception;

	@Query("From Driver where driver_empnumber= ?1 ")
	public List<Driver> listDriverJOB_LC();

	@Query("select count(*) from Driver")
	public Long countTotalDriver() throws Exception;

	@Query("select count(*) from Driver Where driverStatusId=1 ")
	public Long countActiveDriver() throws Exception;
	
	@Modifying
	@Query("update Driver set driverStatusId= ?1 where driver_id= ?2 ")
	public void Update_Driver_Status(short DriveRStatus_ID, int tripFristDriverID);

	@Modifying
	@Query("update Driver set driverStatusId= ?1, tripSheetID=?2  where driver_id= ?3 AND companyId = ?4")
	public void Update_Driver_Status_TripSheetID(short DriveRStatus_ID, Long tripSheetID, int tripFristDriverID, Integer companyId);
	
	@Query("From Driver where driver_mobnumber =?1 and companyId = ?2 and markForDelete = 0")
	public Driver getDriverFromMobileNumber(String driver_mobnumber, Integer companyId);
	
	@Query("From Driver where driver_id =?1 and markForDelete = 0")
	public Driver getLimitedDriverDetails(Integer driverId) throws Exception;
	
	@Query("From Driver D where D.companyId = ?1 AND D.driverStatusId = ?2 AND D.markForDelete = 0")
	public List<Driver> getActiveDriverList(Integer companyId, short driverStatusId);
	
	@Query("SELECT p.driver_id FROM Driver  as p  where p.driver_dlnumber= ?1 AND p.markForDelete = 0")
	public Integer getDriverByDriverLicense(String driver_DLnumber);
	
	
	
}
