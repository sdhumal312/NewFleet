package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.ServiceProgramAsignmentDetailsDto;
import org.fleetopgroup.persistence.model.ServiceProgramAsignmentDetails;

public interface IServiceProgramAsignmentDetailsService {

	List<ServiceProgramAsignmentDetailsDto>  getAsignmentListByServiceProgramId(Long serviceProgramId, Integer companyId) throws Exception;
	
	public void deleteServiceProgramAssignment(Long vehicleTypeId, Long modalId, Long serviceProgramId, Integer companyId) throws Exception;
	
	List<ServiceProgramAsignmentDetails> getServiceProgramListByVehicleTypeAndModal(Long vehicleTypeId, Long vehicleModalId, Integer companyId) throws Exception;

	public void deleteBranchWiseServiceProgramAssignment(Long vehicleTypeId, Long modalId,Integer branchId, Long serviceProgramId, Integer companyId) throws Exception;

	List<ServiceProgramAsignmentDetails> getServiceProgramListByVehicleTypeModalAndBranch(Long vehicleTypeId, Long vehicleModalId, Integer branchId,Integer companyId) throws Exception;
}
