package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.VehicleGPSCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface VehicleGPSCredentialRepository extends JpaRepository<VehicleGPSCredential, Long> {

	@Query("From VehicleGPSCredential where companyId = ?1 AND markForDelete = 0")
	public List<VehicleGPSCredential> getVehicleGPSCredentialListByCompanyId(Integer	companyId) throws Exception;
	
	@Query("From VehicleGPSCredential where userName = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<VehicleGPSCredential> validateVehicleGPSCredential(String userName, Integer companyId) throws Exception;
	
	@Query("From VehicleGPSCredential where vehicleGPSCredentialId = ?1 AND markForDelete = 0")
	public VehicleGPSCredential getVehicleGPSCredentialById(Long vehicleGPSCredentialId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleGPSCredential v SET v.userName = ?1 , v.password = ?2, v.description = ?3 , v.lastModifiedById =?4, lastModifiedOn =?5  where v.vehicleGPSCredentialId =?6")
	public void updateVehicleGPSCredential(String userName, String password, String description, Long updatedBy, Date updatedOn, Long id)throws Exception;
	
	
	@Modifying
	@Transactional
	@Query("UPDATE VehicleGPSCredential v SET v.markForDelete = 1 where v.vehicleGPSCredentialId =?1")
	public void deleteVehicleGPSCredential(Long id)throws Exception;
	
}
