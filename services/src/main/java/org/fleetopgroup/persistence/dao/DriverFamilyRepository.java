/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.DriverFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface DriverFamilyRepository extends JpaRepository<DriverFamily, Long> {

	// public void Registered_DriverFamily_Details(DriverFamily driverFam);

	@Query("From DriverFamily p  where p.DF_NAME= ?1 AND p.markForDelete = 0")
	public DriverFamily Validate_DriverFamily_Details(String DF_Name);

	@Query("select p From DriverFamily p where p.DRIVERID= ?1 AND p.COMPANY_ID = ?2 AND p.markForDelete = 0")
	public List<DriverFamily> list_Of_DriverID_to_DriverFamily(Integer DriverId, Integer COMPANYId);

	@Query("From DriverFamily p  where p.DF_NAME= ?1 AND p.DRIVERID=?2  AND p.COMPANY_ID = ?3 AND p.markForDelete = 0")
	public List<DriverFamily> Validate_Driver_Family_Member_Name(String string, Integer driverID, Integer companyId);

	/**
	 * @param dFID
	 */
	@Modifying
	@Query("UPDATE DriverFamily p SET p.markForDelete = 1 where p.DFID= ?1 AND p.COMPANY_ID = ?2")
	public void Delete_DriverFamily_Details(Long dFID, Integer companyId);

}
