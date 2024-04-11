package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.ServiceProgramAsignmentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ServiceProgramAsignmentDetailsRepository extends JpaRepository<ServiceProgramAsignmentDetails, Long> {
	
	@Query("FROM ServiceProgramAsignmentDetails where vehicleTypeId = ?1 AND vehicleModalId = ?2 AND companyId = ?3 AND serviceProgramId = ?4 AND markForDelete = 0")
	public List<ServiceProgramAsignmentDetails>  validateAsignment(Long vehicleType, Long vehicleModal, Integer companyId, Long serviceProgramId) throws Exception;
	
	@Query("FROM ServiceProgramAsignmentDetails where vehicleTypeId = ?1 AND vehicleModalId = ?2 AND companyId = ?3 AND markForDelete = 0")
	public List<ServiceProgramAsignmentDetails> getServiceProgramListByVehicleTypeAndModal(Long vehicleTypeId,
			Long vehicleModalId, Integer companyId) throws Exception;
	
	@Query("FROM ServiceProgramAsignmentDetails where serviceProgramId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<ServiceProgramAsignmentDetails>  validateAsignmentByProgramId(Long serviceProgramId, Integer companyId) throws Exception;
	 
	@Modifying
	@Query("update ServiceProgramAsignmentDetails set markForDelete = 1 where serviceProgramId = ?1 and companyId = ?2")
	public void deleteProgramAsignment(Long serviceProgramId, Integer companyId) throws Exception;
	
	@Modifying
	@Query("update ServiceProgramAsignmentDetails set markForDelete = 1 "
			+ " where vehicleTypeId = ?1 and vehicleModalId= ?2 and serviceProgramId = ?3 and companyId = ?4 ")
	public void deleteServiceProgramAssignment(Long vehicleTypeId, Long modalId, Long serviceProgramId,
			Integer companyId) throws Exception;
	
	@Query("FROM ServiceProgramAsignmentDetails where vehicleTypeId = ?1 AND vehicleModalId = ?2 AND branchId =?3 AND companyId = ?4 AND serviceProgramId = ?5 AND markForDelete = 0")
	public List<ServiceProgramAsignmentDetails>  validateAsignmentByVehicelTypeModalAndBranch(Long vehicleType, Long vehicleModal, Integer branchId, Integer companyId, Long serviceProgramId) throws Exception;
	
	@Modifying
	@Query("update ServiceProgramAsignmentDetails set markForDelete = 1 "
			+ " where vehicleTypeId = ?1 and vehicleModalId= ?2 and branchId =?3 and serviceProgramId = ?4 and companyId = ?5 ")
	public void deleteBranchWiseServiceProgramAssignment(Long vehicleTypeId, Long modalId, Integer branchId , Long serviceProgramId,
			Integer companyId) throws Exception;
	
	@Query("FROM ServiceProgramAsignmentDetails where vehicleTypeId = ?1 AND vehicleModalId = ?2 AND branchId =?3 AND companyId = ?4 AND markForDelete = 0")
	public List<ServiceProgramAsignmentDetails> getServiceProgramListByVehicleTypeModalAndBranch(Long vehicleTypeId,
			Long vehicleModalId,Integer branchId, Integer companyId) throws Exception;
}
