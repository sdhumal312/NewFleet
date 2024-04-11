package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.fleetopgroup.persistence.dao.VehicleGroupPermissionRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleGroupPermissionDto;
import org.fleetopgroup.persistence.model.VehicleGroupPermision;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class VehicleGroupPermissionService implements IVehicleGroupPermissionService {

	@Autowired
	private VehicleGroupPermissionRepository vehicleGroupPermissionRepository;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public String getVehicleGroupPrmissionOfUser() {
		String vehicleGroupId = "";
		List<VehicleGroupPermissionDto> vehicleGroupPermissionList = null;
		CustomUserDetails currentUser = null;
		try {
			currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicleGroupPermissionList = (List<VehicleGroupPermissionDto>) currentUser.getVehicleGroupPermission();
			if (vehicleGroupPermissionList == null || vehicleGroupPermissionList.size() <= 0) {
				vehicleGroupId = "0";
				return vehicleGroupId;
			}
			for (int i = 0; i < vehicleGroupPermissionList.size(); i++) {
				if (i < vehicleGroupPermissionList.size() - 1) {
					vehicleGroupId += vehicleGroupPermissionList.get(i).getVehicleGroupId() + ",";
				} else {
					vehicleGroupId += vehicleGroupPermissionList.get(i).getVehicleGroupId();

				}
			}
			return vehicleGroupId;
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<VehicleGroupPermissionDto> getVehicleGroupPrmissionByUserId(Long id, Integer companyId)
			throws Exception {
		try {
			Query query = entityManager.createQuery(
					"SELECT VG.gid, VG.vGroup from VehicleGroupPermision VGP inner join VehicleGroup VG on VGP.vg_per_id = VG.gid where VGP.user_id =  :id AND VGP.companyId =:comId");
			query.setParameter("id", id);
			query.setParameter("comId", companyId);
			List<Object[]> results = null;
			results = query.getResultList();

			List<VehicleGroupPermissionDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleGroupPermissionDto>();
				VehicleGroupPermissionDto list = null;
				for (Object[] result : results) {
					list = new VehicleGroupPermissionDto();
					list.setUserId(id);
					list.setVehicleGroupId((long) result[0]);
					list.setvGroupName((String) result[1]);
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public void registerVehicleGroupPrmissionByUserId(VehicleGroupPermision vehicleGroup) {

		try {
			vehicleGroupPermissionRepository.save(vehicleGroup);
		} catch (Exception e) {

		}
	}

	@Override
	@Transactional
	public void deleteVehicleGroupPrmissionByUserId(Long user_id, Integer companyId) {

		try {
			vehicleGroupPermissionRepository.deleteVehicleGroupPrmissionByUserId(user_id, companyId);
		} catch (Exception e) {

		}
	}
}
