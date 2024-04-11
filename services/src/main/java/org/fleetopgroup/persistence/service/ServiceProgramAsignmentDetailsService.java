package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.ServiceProgramAsignmentDetailsRepository;
import org.fleetopgroup.persistence.dto.ServiceProgramAsignmentDetailsDto;
import org.fleetopgroup.persistence.model.ServiceProgramAsignmentDetails;
import org.fleetopgroup.persistence.serviceImpl.IServiceProgramAsignmentDetailsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServiceProgramAsignmentDetailsService implements IServiceProgramAsignmentDetailsService {
	@PersistenceContext EntityManager entityManager;
	
	@Autowired private ServiceProgramAsignmentDetailsRepository		asignmentDetailsRepository;
	
	@Override
	public List<ServiceProgramAsignmentDetailsDto> getAsignmentListByServiceProgramId(Long serviceProgramId,
			Integer companyId) throws Exception {
		try {

			TypedQuery<Object[]> query = null;
			query = entityManager.createQuery(
					"SELECT SE.programAsignmentId, SE.vehicleTypeId, SE.vehicleModalId, SE.vid,"
							+ " SE.createdOn, U.firstName, U.lastName, vt.vtype, VM.vehicleModelName,"
							+ " V.vehicle_registration, SE.branchId , B.branch_name"
							+ " FROM ServiceProgramAsignmentDetails  AS SE "
							+ " INNER JOIN User U ON U.id = SE.createdById"
							+ " LEFT JOIN VehicleType vt on vt.tid = SE.vehicleTypeId"
							+ " LEFT JOIN VehicleModel VM ON VM.vehicleModelId = SE.vehicleModalId"
							+ " LEFT JOIN Vehicle V ON V.vid = SE.vid AND V.company_Id = "+companyId+""
							+ " LEFT JOIN Branch B ON B.branch_id = SE.branchId "
							+ " WHERE SE.serviceProgramId = "+serviceProgramId+" AND SE.companyId = "+companyId+" "
							+ " AND SE.markForDelete = 0 ",
					Object[].class);
			
			List<Object[]> results = query.getResultList();

			List<ServiceProgramAsignmentDetailsDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<ServiceProgramAsignmentDetailsDto>();
				ServiceProgramAsignmentDetailsDto list = null;
				for (Object[] result : results) {
					list = new ServiceProgramAsignmentDetailsDto();

					list.setServiceProgramId(serviceProgramId);
					list.setProgramAsignmentId((Long) result[0]);
					list.setVehicleTypeId((Long) result[1]);
					list.setVehicleModalId((Long) result[2]);
					list.setVid((Integer) result[3]);
					list.setCreatedOnStr(DateTimeUtility.getStringDateFromDate((Date) result[4], DateTimeUtility.DD_MM_YY_HH_MM_SS));
					list.setCreatedBy((String) result[5]+" "+(String) result[6]);
					list.setVehicleType((String) result[7]);
					list.setVehicleModal((String) result[8]);
					list.setVehicleNumber((String) result[9]);
					if(result[10] != null) {
						list.setBranchId((Integer) result[10]);
						list.setBranchName((String) result[11]);	
					}

					Dtos.add(list);
				}
			}
			return Dtos;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<ServiceProgramAsignmentDetails> getServiceProgramListByVehicleTypeAndModal(Long vehicleTypeId,
			Long vehicleModalId, Integer companyId) throws Exception {
		return asignmentDetailsRepository.getServiceProgramListByVehicleTypeAndModal(vehicleTypeId, vehicleModalId, companyId);
	}
	
	@Override
	@Transactional
	public void deleteServiceProgramAssignment(Long vehicleTypeId, Long modalId, Long serviceProgramId,
			Integer companyId) throws Exception {
		
		asignmentDetailsRepository.deleteServiceProgramAssignment(vehicleTypeId, modalId, 
									serviceProgramId, companyId);
	}
	
	@Override
	@Transactional
	public void deleteBranchWiseServiceProgramAssignment(Long vehicleTypeId, Long modalId, Integer branchId,  Long serviceProgramId,
			Integer companyId) throws Exception {
		
		asignmentDetailsRepository.deleteBranchWiseServiceProgramAssignment(vehicleTypeId, modalId, branchId,
									serviceProgramId, companyId);
	}
	
	@Override
	public List<ServiceProgramAsignmentDetails> getServiceProgramListByVehicleTypeModalAndBranch(Long vehicleTypeId,
			Long vehicleModalId,Integer branchId, Integer companyId) throws Exception {
		return asignmentDetailsRepository.getServiceProgramListByVehicleTypeModalAndBranch(vehicleTypeId, vehicleModalId,branchId, companyId);
	}
}
