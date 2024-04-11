/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.DriverBasicDetailsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface DriverBasicDetailsTypeRepository extends JpaRepository<DriverBasicDetailsType, Integer> {

	@Query("From DriverBasicDetailsType DBDT where DBDT.companyId = ?1 and DBDT.markForDelete = 0 ")
	List<DriverBasicDetailsType> findBycompanyId(Integer companyId);

	@Query("From DriverBasicDetailsType DBDT where lower(DBDT.driverBasicDetailsTypeName) = ?1 AND DBDT.companyId = ?2 AND DBDT.markForDelete = 0 ")
	public DriverBasicDetailsType findByDetailTypeName(String detailType, Integer companyId);
	
	@Query("From DriverBasicDetailsType DBDT where DBDT.driverBasicDetailsTypeId = ?1 AND DBDT.companyId = ?2 AND DBDT.markForDelete = 0 ")
	public DriverBasicDetailsType getDriverBasicDetailsTypeById(Long driverBasicDetailsTypeId , Integer companyId);
	
	@Modifying
	@Query("UPDATE DriverBasicDetailsType DBDT SET DBDT.markForDelete = 1  where DBDT.driverBasicDetailsTypeId = ?1 AND DBDT.companyId = ?2 ")
	public void deleteDriverBasicDetailsType(Long driverBasicDetailsTypeId,  Integer company_id);
}
