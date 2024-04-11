package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleInspectionSheetDto;
import org.fleetopgroup.persistence.dto.VehicleTypeAssignmebtDetailsDto;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTypeAssignmentDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("VehicleTypeAssignmentDetailsService")
@Transactional
public class VehicleTypeAssignmentDetailsService implements  IVehicleTypeAssignmentDetailsService {

	@PersistenceContext
	EntityManager entityManager;

	SimpleDateFormat 		dateFormat2 			= new SimpleDateFormat("dd-MM-yyyy");


	@Override
	public VehicleTypeAssignmebtDetailsDto  getvehicleTypeAssignmebtDetailsByTypeId(Long vehicleTypeId) throws Exception {

		CustomUserDetails		userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {


			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT VA.vehicleTypeAssignmentDetailsId ,VA.assignById,VA.assignedOn,U.firstName,U.lastName,VA.inspectionSheetId "
							+ " FROM VehicleTypeAssignmentDetails AS VA "
							+ " INNER JOIN User U ON U.id = VA.assignById "
							+ " where VA.vehicleTypeId = "+vehicleTypeId+" AND VA.companyId = "+userDetails.getCompany_id()+" AND VA.markForDelete = 0 "
							, Object[].class);
			query.setFirstResult(0);
			query.setMaxResults(1); 		

			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
			}
				VehicleTypeAssignmebtDetailsDto select = null;
			if(result!= null) {
				select= new VehicleTypeAssignmebtDetailsDto();
				select.setVehicleTypeAssignmentDetailsId((Long) result[0]);
				select.setAssignById((Long) result[1]);
				if(result[2] != null)
					select.setAssignedOnStr(dateFormat2.format(result[2]));
				select.setAssignByStr(result[3]+" "+result[4]);
				select.setInspectionSheetId((Long) result[5]);
			}
			return select;

		} catch (Exception e) {
			throw e;
		}

	}
	@Override
	public long checkIfSheetAssignedToVehicleType(String vehiceType,Integer companyId) throws Exception{
		javax.persistence.Query query = null;		 
		query =  entityManager.createQuery("SELECT COUNT(*) FROM VehicleTypeAssignmentDetails WHERE vehicleTypeId IN ("+vehiceType+") AND companyId="+companyId+" AND markForDelete = 0");
		Long count = (long) 0;
		try {

			if((Long) query.getSingleResult() != null) {
				count = (Long) query.getSingleResult();
			}

		} catch (NoResultException nre) {
		}
		return count;
	}
	
	public List<VehicleInspectionSheetDto> getvehicleTypeAssignmebtDetailsBySheetId(Long inspectionSheetId) throws Exception {

		CustomUserDetails		userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT VA.assignedOn,U.firstName,U.lastName,B.branch_id,B.branch_name,VT.tid ,VT.vtype "
							+ " FROM VehicleTypeAssignmentDetails AS VA "
							+ " INNER JOIN User U ON U.id = VA.assignById "
							+ " INNER JOIN VehicleType VT ON VT.tid = VA.vehicleTypeId AND VT.markForDelete = 0 "
							+ " LEFT JOIN Branch B ON B.branch_id = VA.branchId "
							+ " where VA.inspectionSheetId = "+inspectionSheetId+" AND VA.companyId = "+userDetails.getCompany_id()+" AND VA.markForDelete = 0 "
							, Object[].class);
			List <Object[]> results = null;
			results = query.getResultList();
			
			List<VehicleInspectionSheetDto> dtosList = new ArrayList<>(); 
			if(results != null && !results.isEmpty()) {
				VehicleInspectionSheetDto select = null;
				for(Object[] result : results) {
						select= new VehicleInspectionSheetDto();
						if(result[0] != null)
						select.setAssignOnStr(dateFormat2.format(result[0]));
						select.setAssignByName(result[1]+" "+result[2]);
						select.setBranchId((Integer) result[3]);
						if(result[4] != null) {
							select.setBranchName((String) result[4]);
						}else {
							select.setBranchName("-");
						}
						select.setVehicleTypeId((Long) result[5]);
						select.setVehicleType((String) result[6]);
						dtosList.add(select);
				}
				
			}
			
			return dtosList;

		} catch (Exception e) {
			throw e;
		}

	}
	

}
