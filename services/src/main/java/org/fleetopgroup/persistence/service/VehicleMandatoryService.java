/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.VehicleMandatoryRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleMandatoryDto;
import org.fleetopgroup.persistence.model.VehicleMandatory;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleMandatoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service
@Transactional
public class VehicleMandatoryService implements IVehicleMandatoryService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private VehicleMandatoryRepository vehMandatoryrepository;
	
	@Autowired
	private ICompanyConfigurationService	companyConfigurationService;


	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleMandatoryService#
	 * register_New_VehicleMandatory(org.fleetop.persistence.model.
	 * VehicleMandatory)
	 */
	@Transactional
	public VehicleMandatory register_New_VehicleMandatory(VehicleMandatory mandatory) throws Exception {

		return vehMandatoryrepository.save(mandatory);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleMandatoryService#
	 * List_Vehicle_Mandatory_Details_GET_VEHICLEID(java.lang.Integer)
	 */
	@Transactional
	public List<VehicleMandatoryDto> List_Vehicle_Mandatory_Details_GET_VEHICLEID(Integer vehicleID, Integer companyId) {

		//return vehMandatoryrepository.List_Vehicle_Mandatory_Details_GET_VEHICLEID(vehicleID, companyId);
		
		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT RR.VMID, RR.VEHICLE_ID, V.vehicle_registration, RST.renewal_SubType, RR.MANDATORY_RENEWAL_SUBID"
						+ " FROM VehicleMandatory AS RR"
						+ " INNER JOIN Vehicle AS V ON V.vid = RR.VEHICLE_ID "
						+ " INNER JOIN RenewalSubType AS RST ON RST.renewal_Subid = RR.MANDATORY_RENEWAL_SUBID  WHERE RR.VEHICLE_ID ="+vehicleID+" AND RR.COMPANY_ID = "+companyId+" AND RR.markForDelete = 0 ", Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<VehicleMandatoryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleMandatoryDto>();
			VehicleMandatoryDto renewal = null;
			for (Object[] result : results) {
				renewal = new VehicleMandatoryDto();

				renewal.setVMID((Long) result[0]);
				renewal.setVEHICLE_ID((Integer) result[1]);
				renewal.setVEHICLE_REGISTRATION((String) result[2]);
				renewal.setMANDATORY_RENEWAL_SUB_NAME((String) result[3]);
				renewal.setMANDATORY_RENEWAL_SUBID((Integer) result[4]);
				Dtos.add(renewal);
			}
		}
		return Dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleMandatoryService#
	 * Delete_VehicleMandatory_Old_Vehicle_ID(java.lang.Integer)
	 */
	@Transactional
	public void Delete_VehicleMandatory_Old_Vehicle_ID(Integer vEHICLE_VID, Integer companyId) {

		vehMandatoryrepository.Delete_VehicleMandatory_Old_Vehicle_ID(vEHICLE_VID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleMandatoryService#
	 * List_Vehicle_Mandatory_Details_GET_VEHICLEID_NOT_EXISTS(java.lang.
	 * Integer, java.sql.Date)
	 */
	@Transactional
	public List<VehicleMandatoryDto> List_Vehicle_Mandatory_Details_GET_VEHICLEID_NOT_EXISTS(Integer vid,
			Date tripfromDate, Date triptoDate, Integer companyId) {

		TypedQuery<Object[]> queryt = entityManager
				.createQuery("SELECT RR.VMID, RR.VEHICLE_ID, V.vehicle_registration, RST.renewal_SubType"
						+ " FROM VehicleMandatory AS RR"
						+ " INNER JOIN Vehicle AS V ON V.vid = RR.VEHICLE_ID "
						+ " INNER JOIN RenewalSubType AS RST ON RST.renewal_Subid = RR.MANDATORY_RENEWAL_SUBID  WHERE RR.VEHICLE_ID ="+vid+" AND NOT EXISTS (FROM RenewalReminder  WHERE vid="+vid+"  AND  renewal_Subid = RR.MANDATORY_RENEWAL_SUBID  AND '"
						+ tripfromDate
						+ "' between renewal_from AND renewal_to  OR vid="+vid+"  AND renewal_Subid = RR.MANDATORY_RENEWAL_SUBID  AND '"
						+ triptoDate + "' between renewal_from AND renewal_to AND markForDelete = 0  AND companyId = "+companyId+") AND RR.COMPANY_ID = "+companyId+" AND RR.markForDelete = 0 ", Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<VehicleMandatoryDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleMandatoryDto>();
			VehicleMandatoryDto renewal = null;
			for (Object[] result : results) {
				renewal = new VehicleMandatoryDto();

				renewal.setVMID((Long) result[0]);
				renewal.setVEHICLE_ID((Integer) result[1]);
				renewal.setVEHICLE_REGISTRATION((String) result[2]);
				renewal.setMANDATORY_RENEWAL_SUB_NAME((String) result[3]);

				Dtos.add(renewal);
			}
		}
		return Dtos;
	}
	
	@Transactional
	public List<VehicleMandatoryDto> List_Vehicle_Mandatory_Details_GET_RENEWALREMINDERVALUES(
			String tripfromDate, String triptoDate) throws Exception {
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt =	null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager
						.createQuery("SELECT RR.VMID, RR.VEHICLE_ID, V.vehicle_registration, RST.renewal_SubType"
								+ " FROM VehicleMandatory AS RR "
								+ " INNER JOIN Vehicle V ON V.vid = RR.VEHICLE_ID "
								+ " INNER JOIN RenewalSubType AS RST ON RST.renewal_Subid = RR.MANDATORY_RENEWAL_SUBID "
								+ " WHERE NOT EXISTS ( FROM RenewalReminder R WHERE ( R.vid= RR.VEHICLE_ID  AND  R.renewal_Subid = RR.MANDATORY_RENEWAL_SUBID  AND '"
								+ tripfromDate
								+ "' between R.renewal_from AND R.renewal_to  OR R.vid= RR.VEHICLE_ID  AND R.renewal_Subid = RR.MANDATORY_RENEWAL_SUBID  AND '"
								+ triptoDate + "' between R.renewal_from AND R.renewal_to ) AND R.companyId = "+userDetails.getCompany_id()+" AND R.markForDelete = 0)"
										+ " AND RR.COMPANY_ID = "+userDetails.getCompany_id()+" AND RR.markForDelete = 0 ", Object[].class);
			}else {
				
				queryt = entityManager
						.createQuery("SELECT RR.VMID, RR.VEHICLE_ID, V.vehicle_registration, RST.renewal_SubType"
								+ " FROM VehicleMandatory AS RR "
								+ " INNER JOIN Vehicle V ON V.vid = RR.VEHICLE_ID "
								+ " INNER JOIN RenewalSubType AS RST ON RST.renewal_Subid = RR.MANDATORY_RENEWAL_SUBID  "
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+""
								+ " WHERE NOT EXISTS ( FROM RenewalReminder R WHERE ( R.vid= RR.VEHICLE_ID  AND  R.renewal_Subid = RR.MANDATORY_RENEWAL_SUBID  AND '"
								+ tripfromDate
								+ "' between R.renewal_from AND R.renewal_to  OR R.vid= RR.VEHICLE_ID  AND R.renewal_Subid = RR.MANDATORY_RENEWAL_SUBID  AND '"
								+ triptoDate + "' between R.renewal_from AND R.renewal_to ) AND R.companyId = "+userDetails.getCompany_id()+" AND R.markForDelete = 0)"
										+ " AND RR.COMPANY_ID = "+userDetails.getCompany_id()+" AND RR.markForDelete = 0 ", Object[].class);
			}
			List<Object[]> results = queryt.getResultList();

			List<VehicleMandatoryDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleMandatoryDto>();
				VehicleMandatoryDto renewal = null;
				for (Object[] result : results) {
					renewal = new VehicleMandatoryDto();

					renewal.setVMID((Long) result[0]);
					renewal.setVEHICLE_ID((Integer) result[1]);
					renewal.setVEHICLE_REGISTRATION((String) result[2]);
					renewal.setMANDATORY_RENEWAL_SUB_NAME((String) result[3]);

					Dtos.add(renewal);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleMandatoryService#
	 * Count_Vehicle_Mandatory_Details_GET_VEHICLEID_NOT_EXISTS_Count(java.sql.
	 * Date)
	 */
	@Transactional
	public Long Count_Vehicle_Mandatory_Details_GET_VEHICLEID_NOT_EXISTS_Count(String tripfromDate) throws Exception {
		CustomUserDetails		userDetails				= null;
		try {
			// Note IS Count WorkOrders Details
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Query query =	null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery("SELECT count(*)"
						+ " FROM VehicleMandatory AS RR"
						+ " INNER JOIN Vehicle V ON V.vid = RR.VEHICLE_ID "
						+ " WHERE NOT EXISTS (FROM RenewalReminder R "
						+ " WHERE R.vid= RR.VEHICLE_ID  AND  R.renewal_Subid = RR.MANDATORY_RENEWAL_SUBID  AND '"
						+ tripfromDate
						+ "' between R.renewal_from AND R.renewal_to  OR R.vid= RR.VEHICLE_ID  AND R.renewal_Subid = RR.MANDATORY_RENEWAL_SUBID  AND '"
						+ tripfromDate + "' between R.renewal_from AND R.renewal_to AND R.companyId = "+userDetails.getCompany_id()+" and R.markForDelete = 0"
								+ "  AND RR.COMPANY_ID = "+userDetails.getCompany_id()+") AND RR.markForDelete = 0 AND RR.COMPANY_ID = "+userDetails.getCompany_id()+" ");

			}else {
				
				query = entityManager.createQuery("SELECT count(*)"
						+ " FROM VehicleMandatory AS RR"
						+ " INNER JOIN Vehicle V ON V.vid = RR.VEHICLE_ID "
						+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = V.vehicleGroupId AND VGP.user_id = "+userDetails.getId()+""
						+ " WHERE NOT EXISTS ( FROM RenewalReminder R "
						+ " WHERE R.vid= RR.VEHICLE_ID  AND  R.renewal_Subid = RR.MANDATORY_RENEWAL_SUBID  AND '"
						+ tripfromDate
						+ "' between R.renewal_from AND R.renewal_to  OR R.vid= RR.VEHICLE_ID  AND R.renewal_Subid = RR.MANDATORY_RENEWAL_SUBID  AND '"
						+ tripfromDate + "' between R.renewal_from AND R.renewal_to AND R.companyId = "+userDetails.getCompany_id()+" and R.markForDelete = 0 ) AND RR.COMPANY_ID = "+userDetails.getCompany_id()+" AND RR.markForDelete = 0");
			}

			return (Long) (query.getSingleResult());
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails				= null;
		}
	}

}
