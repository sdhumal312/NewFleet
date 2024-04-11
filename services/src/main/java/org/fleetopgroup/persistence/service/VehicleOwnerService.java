package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehicleOwnerRepository;
import org.fleetopgroup.persistence.dto.VehicleOwnerDto;
import org.fleetopgroup.persistence.model.VehicleOwner;
import org.fleetopgroup.persistence.serviceImpl.IVehicleOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehicleOwnerService implements IVehicleOwnerService {
	@Autowired
	private VehicleOwnerRepository vehicleOwnerrepository;

	@PersistenceContext
	public EntityManager entityManager;
	
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleOwnerService#
	 * Register_New_VehicleOwner(org.fleetop.persistence.model.VehicleOwner)
	 */
	@Transactional
	public VehicleOwner Register_New_VehicleOwner(VehicleOwner accountDto) throws Exception {

		return vehicleOwnerrepository.save(accountDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleOwnerService#
	 * listVehiclePurchase(java.lang.Integer)
	 */
	@Transactional
	public List<VehicleOwnerDto> list_Of_Vehicle_ID_VehicleOwner(Integer vehicle_id, Integer companyId) throws Exception {
		//CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//return vehicleOwnerrepository.list_Of_Vehicle_ID_VehicleOwner(vehicle_id, userDetails.getCompany_id());
		
		TypedQuery<Object[]> queryt = null;
		
			queryt = entityManager.createQuery(
					" SELECT R.VOID, R.VEHID, R.VEH_OWNER_SERIAL, R.VEH_OWNER_NAME, R.VEH_OWNER_PANNO, R.VEH_OWNER_PHONE,"
					+ " R.VEH_OWNER_AADHARNO, R.VEH_OWNER_ADDRESS, R.VEH_OWNER_CITY, R.VEH_OWNER_STATE, R.VEH_OWNER_COUNTRY,"
					+ " R.VEH_OWNER_PINCODE, R.VEH_DRIVER_NAME, R.VEH_DRIVER_PHONE, U.email, U2.email, R.CREATED_DATE,"
					+ " R.LASTUPDATED_DATE "
					+ " FROM VehicleOwner AS R "
					+ " LEFT JOIN User U ON U.id = R.CREATEDBYID "
					+ " LEFT JOIN User U2 ON U2.id = R.LASTMODIFIEDBYID "
					+ " WHERE R.VEHID = "+vehicle_id+" AND R.COMPANY_ID = "+companyId+" AND R.markForDelete = 0",Object[].class);
		
		List<Object[]> results = queryt.getResultList();

		List<VehicleOwnerDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleOwnerDto>();
			VehicleOwnerDto NewOwner = null;
			for (Object[] result : results) {
				NewOwner = new VehicleOwnerDto();

				NewOwner.setVOID((Integer) result[0]);
				NewOwner.setVEHID((Integer) result[1]);
				NewOwner.setVEH_OWNER_SERIAL((String) result[2]);
				NewOwner.setVEH_OWNER_NAME((String) result[3]);
				NewOwner.setVEH_OWNER_PANNO((String) result[4]);
				NewOwner.setVEH_OWNER_PHONE((String) result[5]);
				NewOwner.setVEH_OWNER_AADHARNO((String) result[6]);
				NewOwner.setVEH_OWNER_ADDRESS((String) result[7]);
				NewOwner.setVEH_OWNER_CITY((String) result[8]);
				NewOwner.setVEH_OWNER_STATE((String) result[9]);
				NewOwner.setVEH_OWNER_COUNTRY((String) result[10]);
				NewOwner.setVEH_OWNER_PINCODE((String) result[11]);
				NewOwner.setVEH_DRIVER_NAME((String) result[12]);
				NewOwner.setVEH_DRIVER_PHONE((String) result[13]);

				NewOwner.setCREATEDBY((String) result[14]);
				NewOwner.setLASTMODIFIEDBY((String) result[15]);

				if (result[16] != null) {
					/** Set Created Current Date */
					NewOwner.setCREATED_DATE(CreatedDateTime.format(result[16]));
				}
				if (result[17] != null) {
					NewOwner.setLASTUPDATED_DATE(CreatedDateTime.format(result[17]));
				}

				Dtos.add(NewOwner);
			}
		}
		return Dtos;
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleOwnerService#
	 * Get_VehiclePurchase(int)
	 */
	@Transactional
	public VehicleOwner Get_Vehicle_Owner(int doc_id, Integer companyId) throws Exception {
		
		return vehicleOwnerrepository.Get_Vehicle_Owner(doc_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleOwnerService#
	 * Delete_VehicleOwner(int)
	 */
	@Transactional
	public void Delete_VehicleOwner(int doc_id, Integer companyId) throws Exception {

		vehicleOwnerrepository.Delete_VehicleOwner(doc_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleOwnerService#
	 * Validate_VehicleOwner_name(java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<VehicleOwner> Validate_VehicleOwner_name(String vehicleOwnerName, String vehicleOwnerSerial, Integer companyId, Integer vid)
			throws Exception {

		return vehicleOwnerrepository.Validate_VehicleOwner_name(vehicleOwnerName, vehicleOwnerSerial, companyId, vid);
	}
	
	@Transactional
	public List<VehicleOwner> Validate_VehicleOwner_name(String vehicleOwnerName, Integer companyId, Integer vid)
			throws Exception {

		return vehicleOwnerrepository.Validate_VehicleOwner_name(vehicleOwnerName, companyId, vid);
	}

}