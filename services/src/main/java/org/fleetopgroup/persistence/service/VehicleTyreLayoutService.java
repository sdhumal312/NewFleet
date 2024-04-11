/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.persistence.dao.VehicleTyreLayoutPositionRepository;
import org.fleetopgroup.persistence.dao.VehicleTyreLayoutRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryTyreDto;
import org.fleetopgroup.persistence.dto.VehicleTyreLayoutDto;
import org.fleetopgroup.persistence.dto.VehicleTyreLayoutPositionDto;
import org.fleetopgroup.persistence.model.VehicleTyreLayout;
import org.fleetopgroup.persistence.model.VehicleTyreLayoutPosition;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreLayoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("VehicleTyreLayoutService")
@Transactional
public class VehicleTyreLayoutService implements IVehicleTyreLayoutService {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private VehicleTyreLayoutRepository vehicleTyreLayoutRepository;

	@Autowired
	private VehicleTyreLayoutPositionRepository vehicleTyreLayoutPositionRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreLayoutService#
	 * registerNewTyreLayout(org.fleetop.persistence.model.VehicleTyreLayout)
	 */
	@Transactional
	public VehicleTyreLayout registerNewTyreLayout(VehicleTyreLayout VehicleTyreLayout) throws Exception {

		return vehicleTyreLayoutRepository.save(VehicleTyreLayout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreLayoutService#
	 * UpdateTyreLayout(org.fleetop.persistence.model.VehicleTyreLayout)
	 */
	@Transactional
	public VehicleTyreLayout UpdateTyreLayout(VehicleTyreLayout VehicleTyreLayout) throws Exception {

		return vehicleTyreLayoutRepository.save(VehicleTyreLayout);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreLayoutService#
	 * Get_Vehicle_Type_Details(java.lang.Integer)
	 */
	@Transactional
	public List<VehicleTyreLayoutDto> Get_Vehicle_Type_Details(Integer Vehicle_id, Integer companyId) throws Exception {
		//return vehicleTyreLayoutRepository.Get_Vehicle_Type_Details(Vehicle_id, companyId);
		
		try {
			TypedQuery<Object[]> query = null;
		
				 query = entityManager.createQuery(
						"SELECT V.L_ID, V.VEHICLE_ID, VH.vehicle_registration, V.AXLE, VS.TYRE_SIZE, V.TYRE_FRONT_PRESSURE, VTS.TYRE_SIZE,"
						+ " V.TYRE_REAR_PRESSURE, U.email, V.CREATED_DATE "
						+ " from VehicleTyreLayout AS V "
						+ " INNER JOIN Vehicle VH ON VH.vid = V.VEHICLE_ID"
						+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = V.TYRE_FRONT_SIZE_ID "
						+ " LEFT JOIN VehicleTyreSize VTS ON VTS.TS_ID = V.TYRE_REAR_SIZE_ID "
						+ " LEFT JOIN User U ON U.id = V.CREATEDBYID"
						+ " WHERE V.VEHICLE_ID ="+Vehicle_id+" AND V.COMPANY_ID = "+companyId+" AND V.markForDelete = 0 ", Object[].class);

				
			List<Object[]> results = query.getResultList();
			
			List<VehicleTyreLayoutDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleTyreLayoutDto>();
				VehicleTyreLayoutDto select = null;
				for (Object[] vehicle : results) {
					select = new VehicleTyreLayoutDto();
					
					select.setL_ID((Long) vehicle[0]);
					select.setVEHICLE_ID((Integer) vehicle[1]);
					select.setVEHICLE_REGISTRATION((String) vehicle[2]);
					select.setAXLE((Integer) vehicle[3]);
					select.setTYRE_FRONT_SIZE((String) vehicle[4]);
					select.setTYRE_FRONT_PRESSURE((String) vehicle[5]);
					select.setTYRE_REAR_SIZE((String) vehicle[6]);
					select.setTYRE_REAR_PRESSURE((String) vehicle[7]);
					select.setCREATEDBY((String) vehicle[8]);
					select.setCREATED_DATE((Date) vehicle[9]);
					Dtos.add(select);
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
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreLayoutService#
	 * registerNewTyreLayoutPosition(org.fleetop.persistence.model.
	 * VehicleTyreLayoutPosition)
	 */
	@Transactional
	public VehicleTyreLayoutPosition registerNewTyreLayoutPosition(VehicleTyreLayoutPosition VehicleTyreLayoutPostion)
			throws Exception {

		return vehicleTyreLayoutPositionRepository.save(VehicleTyreLayoutPostion);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreLayoutService#
	 * Get_Vehicle_TyreLayout_Position_Details(java.lang.Integer)
	 */
	@Transactional
	public List<VehicleTyreLayoutPositionDto> Get_Vehicle_TyreLayout_Position_Details(Integer Vehicle_id, Integer companyId)
			throws Exception {
		//return vehicleTyreLayoutPositionRepository.Get_Vehicle_Type_Details(Vehicle_id, companyId);

		TypedQuery<Object[]> query = null;
	
			 query = entityManager.createQuery(
					"SELECT V.LP_ID, V.VEHICLE_ID, V.AXLE, V.POSITION, VS.TYRE_SIZE, V.TYRE_PRESSURE, V.TYRE_ID, V.TYRE_SERIAL_NO,"
					+ " V.TYRE_ASSIGNED, V.TYRE_SIZE_ID "
					+ " from VehicleTyreLayoutPosition AS V "
					+ " LEFT JOIN VehicleTyreSize VS ON VS.TS_ID = V.TYRE_SIZE_ID "
					+ " WHERE V.VEHICLE_ID ="+Vehicle_id+" AND V.COMPANY_ID = "+companyId+" AND V.markForDelete = 0 ", Object[].class);

			
		List<Object[]> results = query.getResultList();
		
		List<VehicleTyreLayoutPositionDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleTyreLayoutPositionDto>();
			VehicleTyreLayoutPositionDto select = null;
			for (Object[] vehicle : results) {
				select = new VehicleTyreLayoutPositionDto();
				
				select.setLP_ID((Long) vehicle[0]);
				select.setVEHICLE_ID((Integer) vehicle[1]);
				select.setAXLE((Integer) vehicle[2]);
				select.setPOSITION((String) vehicle[3]);
				select.setTYRE_SIZE((String) vehicle[4]);
				select.setTYRE_PRESSURE((String) vehicle[5]);
				select.setTYRE_ID((Long) vehicle[6]);
				select.setTYRE_SERIAL_NO((String) vehicle[7]);
				select.setTYRE_ASSIGNED((boolean) vehicle[8]);
				select.setTYRE_SIZE_ID((Integer) vehicle[9]);
				
				Dtos.add(select);
			}
		}
		return Dtos;
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreLayoutService#
	 * update_Position_Assign_TYRE_To_Vehicle(java.lang.Long, java.lang.String,
	 * boolean, java.lang.Long)
	 */
	@Transactional
	public void update_Position_Assign_TYRE_To_Vehicle(Long TYRE_ID, String TYRE_SERIAL_NO, boolean TYRE_ASSIGNED,
			Long LP_ID,String remark) throws Exception {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		vehicleTyreLayoutPositionRepository.update_Position_Assign_TYRE_To_Vehicle(TYRE_ID, TYRE_SERIAL_NO,
				TYRE_ASSIGNED, LP_ID, userDetails.getCompany_id(),remark);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreLayoutService#
	 * Validate_Vehicle_Tyre_Position_AssignOrNot(java.lang.Integer)
	 */
	@Transactional
	public List<VehicleTyreLayoutPosition> Validate_Vehicle_Tyre_Position_AssignOrNot(Integer Vehicle_id, boolean TYRE_ASSIGNED, Integer companyId) throws Exception {

		return vehicleTyreLayoutPositionRepository.Validate_Vehicle_Tyre_Position_AssignOrNot(Vehicle_id,
				TYRE_ASSIGNED, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreLayoutService#
	 * Delete_VehicleTyreLayoutPosition(java.lang.Integer)
	 */
	@Transactional
	public void Delete_VehicleTyreLayoutPosition(Integer Vehicle_id, Integer companyId) throws Exception {

		vehicleTyreLayoutPositionRepository.Delete_VehicleTyreLayoutPosition(Vehicle_id, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreLayoutService#
	 * Delete_VehicleTyreLayout(java.lang.Integer)
	 */
	@Transactional
	public void Delete_VehicleTyreLayout(Integer Vehicle_id, Integer companyId) throws Exception {

		vehicleTyreLayoutRepository.Delete_VehicleTyreLayout(Vehicle_id, companyId);
	}

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreLayoutService#Get_Position_name_to_GetDetails(java.lang.Integer, java.lang.String)
	 */
	@Transactional
	public VehicleTyreLayoutPosition Get_Position_name_to_GetDetails(Integer Vehicle_id, String position, Integer companyId)
			throws Exception {
		
		return vehicleTyreLayoutPositionRepository.Get_Position_name_to_GetDetails(Vehicle_id, position, companyId);
	}

	

	/* (non-Javadoc)
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreLayoutService#Get_Position_ID_to_GetDetails(java.lang.Long)
	 */
	@Transactional
	public VehicleTyreLayoutPosition Get_Position_ID_to_GetDetails(Long Position_ID, Integer companyId) throws Exception {
		
		return vehicleTyreLayoutPositionRepository.Get_Position_ID_to_GetDetails(Position_ID, companyId);
	}
	
	@Override
	public List<VehicleTyreLayoutPositionDto> getVehicleTyreExpenseDetailsList(Integer vid, Integer companyId)
			throws Exception {
		//return vehicleTyreLayoutPositionRepository.Get_Vehicle_Type_Details(Vehicle_id, companyId);

		TypedQuery<Object[]> query = null;
	
			 query = entityManager.createQuery(
					"SELECT V.LP_ID, V.VEHICLE_ID, V.TYRE_ID, V.TYRE_SERIAL_NO, V.TYRE_ASSIGNED, V.TYRE_SIZE_ID, IT.TYRE_USEAGE, IT.OPEN_ODOMETER,"
					+ " VH.vehicle_Odometer, IT.TYRE_ASSIGN_STATUS_ID,  VCF.costPerKM "
					+ " from VehicleTyreLayoutPosition AS V "
					+ " INNER JOIN Vehicle VH ON VH.vid = V.VEHICLE_ID"
					+ " INNER JOIN InventoryTyre IT ON IT.TYRE_ID = V.TYRE_ID AND V.TYRE_ID IS NOT NULL"
					+ " INNER JOIN VehicleCostFixing VCF ON VCF.tyreSubTypeId = IT.TYRE_MODEL_ID AND VCF.costType = 1 AND VCF.companyId = "+companyId+" "
					+ " WHERE V.VEHICLE_ID ="+vid+" AND V.COMPANY_ID = "+companyId+" AND V.markForDelete = 0 AND V.TYRE_ASSIGNED = 1 AND VCF.markForDelete = 0 ", Object[].class);

			//vehicleTypeId
		List<Object[]> results = query.getResultList();
		
		List<VehicleTyreLayoutPositionDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleTyreLayoutPositionDto>();
			VehicleTyreLayoutPositionDto select = null;
			for (Object[] vehicle : results) {
				select = new VehicleTyreLayoutPositionDto();
				
				select.setLP_ID((Long) vehicle[0]);
				select.setVEHICLE_ID((Integer) vehicle[1]);
				select.setTYRE_ID((Long) vehicle[2]);
				select.setTYRE_SERIAL_NO((String) vehicle[3]);
				select.setTYRE_ASSIGNED((boolean) vehicle[4]);
				select.setTYRE_SIZE_ID((Integer) vehicle[5]);
				select.setTYRE_USEAGE((Integer) vehicle[6]);
				select.setOPEN_ODOMETER((Integer) vehicle[7]);
				select.setVEHICLE_ODOMETER((Integer) vehicle[8]);
				select.setTYRE_ASSIGN_STATUS_ID((short) vehicle[9]);
				select.setCostPerKM((Double) vehicle[10]);
				
				if(select.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE) {
					if(select.getTYRE_USEAGE() != null && select.getVEHICLE_ODOMETER() != null && select.getOPEN_ODOMETER() != null)
						select.setTYRE_USEAGE(select.getTYRE_USEAGE() + (select.getVEHICLE_ODOMETER() - select.getOPEN_ODOMETER()));
				}
				
				
				Dtos.add(select);
			}
		}
		return Dtos;
	
	}
	
	@Override
	public List<VehicleTyreLayoutPositionDto> getGroupTyreExpenseDetailsList(long vehicleGroupId, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> query = null;
	
			 query = entityManager.createQuery(
					"SELECT V.LP_ID, V.VEHICLE_ID, V.TYRE_ID, V.TYRE_SERIAL_NO, V.TYRE_ASSIGNED, V.TYRE_SIZE_ID, IT.TYRE_USEAGE, IT.OPEN_ODOMETER,"
					+ " VH.vehicle_Odometer, IT.TYRE_ASSIGN_STATUS_ID,  VCF.costPerKM "
					+ " from VehicleTyreLayoutPosition AS V "
					+ " INNER JOIN Vehicle VH ON VH.vid = V.VEHICLE_ID "
					+ " INNER JOIN VehicleGroup VG ON VG.gid = VH.vehicleGroupId"
					+ " INNER JOIN InventoryTyre IT ON IT.TYRE_ID = V.TYRE_ID"
					+ " INNER JOIN VehicleCostFixing VCF ON VCF.tyreSubTypeId = IT.TYRE_MODEL_ID AND VCF.costType = 1 AND VCF.companyId = "+companyId+"  "
					+ " WHERE VG.gid ="+vehicleGroupId+" AND V.COMPANY_ID = "+companyId+" AND V.markForDelete = 0 AND TYRE_ASSIGNED = 1 ", Object[].class);

			//vehicleTypeId
		List<Object[]> results = query.getResultList();
		
		List<VehicleTyreLayoutPositionDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleTyreLayoutPositionDto>();
			VehicleTyreLayoutPositionDto select = null;
			for (Object[] vehicle : results) {
				select = new VehicleTyreLayoutPositionDto();
				
				select.setLP_ID((Long) vehicle[0]);
				select.setVEHICLE_ID((Integer) vehicle[1]);
				select.setTYRE_ID((Long) vehicle[2]);
				select.setTYRE_SERIAL_NO((String) vehicle[3]);
				select.setTYRE_ASSIGNED((boolean) vehicle[4]);
				select.setTYRE_SIZE_ID((Integer) vehicle[5]);
				select.setTYRE_USEAGE((Integer) vehicle[6]);
				select.setOPEN_ODOMETER((Integer) vehicle[7]);
				select.setVEHICLE_ODOMETER((Integer) vehicle[8]);
				select.setTYRE_ASSIGN_STATUS_ID((short) vehicle[9]);
				select.setCostPerKM((Double) vehicle[10]);
				
				if(select.getTYRE_ASSIGN_STATUS_ID() == InventoryTyreDto.TYRE_ASSIGN_STATUS_IN_SERVICE) {
					if(select.getTYRE_USEAGE() != null && select.getVEHICLE_ODOMETER() != null && select.getOPEN_ODOMETER() != null)
						select.setTYRE_USEAGE(select.getTYRE_USEAGE() + (select.getVEHICLE_ODOMETER() - select.getOPEN_ODOMETER()));
				}
				
				
				Dtos.add(select);
			}
		}
		return Dtos;
	
	}

}
