package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.VehicleGroupRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.VehicleGroup;
import org.fleetopgroup.persistence.model.VehicleType;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class VehicleGroupService implements IVehicleGroupService {
	@PersistenceContext
	EntityManager entityManager;
	@Autowired
	private VehicleGroupRepository vehicleGrouprepository;

	@Autowired
	private ICompanyConfigurationService	companyConfigurationService;

	// API

	@Override
	public VehicleGroup registerNewVehicleGroup(final VehicleGroup accountDto) throws Exception {

		return vehicleGrouprepository.save(accountDto);
	}

	@Override
	public void updateVehicleGroup(String vGroup,  Long updateById, Date updateDate,  long gid, Integer companyId) throws Exception {

		vehicleGrouprepository.updateVehicleGroup(vGroup, updateById, updateDate, gid, companyId);
	}

	@Override
	public List<VehicleGroup> findAll() {

		return vehicleGrouprepository.findAll();
	}

	@Override
	public VehicleGroup getVehicleGroup(String verificationToken) {

		return null;
	}

	@Override
	public void deleteVehicleGroup(long gid, Integer companyId) {

		vehicleGrouprepository.deleteVehicleGroup(gid, companyId);
	}

	@Override
	public VehicleGroup getVehicleGroupByID(long gid) {
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return vehicleGrouprepository.getVehicleGroupByID(gid, userDetails.getCompany_id());
	}

	@Override
	public long count() {

		return vehicleGrouprepository.count();
	}

	@Override
	public VehicleGroup findByVGroup(String vGroup, Integer company_Id) throws Exception {

		return vehicleGrouprepository.findByVGroup(vGroup, company_Id);
	}

	@Override
	public List<VehicleGroup> findAllVehicleGroupByCompanyId(Integer company_Id) throws Exception {
		try {
			
				return vehicleGrouprepository.findAllVehicleGroupByCompanyId(company_Id);
			
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public long findCountOfVehicleGroupByCompanyId(Integer company_Id) throws Exception {
		try {
			
			return vehicleGrouprepository.findCountOfVehicleGroupByCompanyId(company_Id);
		
	} catch (Exception e) {
		throw e;
	}
	}
	
	@Override
	public List<VehicleGroup> getVehiclGroupByPermission(Integer company_Id) throws Exception {
		CustomUserDetails	userDetails			= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			TypedQuery<Object[]> queryt =	null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						" select VG.gid, VG.vGroup, VG.company_Id from VehicleGroup VG"
								+ " where VG.company_Id = "+company_Id+" and VG.markForDelete = 0",
								Object[].class);

			}else {
				queryt = entityManager.createQuery(
						" select VG.gid, VG.vGroup, VG.company_Id from VehicleGroup VG"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid and VGP.user_id = "+userDetails.getId()+""
								+ " where VG.company_Id = "+company_Id+" and VG.markForDelete = 0",
								Object[].class);
			}
			/* this only Select column */
			List<Object[]> results = queryt.getResultList();

			List<VehicleGroup> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleGroup>();
				VehicleGroup list = null;
				for (Object[] result : results) {
					list = new VehicleGroup();
					list.setGid((Long) result[0]);
					list.setvGroup((String) result[1]);
					list.setCompany_Id((Integer) result[2]);
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails			= null;
		}
	}
	
	@Override
	public List<VehicleGroup> getVehiclGroupByPermissionForMobile(Integer company_Id, long userId) throws Exception {
		try {
			TypedQuery<Object[]> queryt =	null;
			if(!companyConfigurationService.getVehicleGroupWisePermission(company_Id, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				queryt = entityManager.createQuery(
						" select VG.gid, VG.vGroup, VG.company_Id from VehicleGroup VG"
								+ " where VG.company_Id = "+company_Id+" and VG.markForDelete = 0",
								Object[].class);

			}else {
				queryt = entityManager.createQuery(
						" select VG.gid, VG.vGroup, VG.company_Id from VehicleGroup VG"
								+ " INNER JOIN VehicleGroupPermision VGP ON VGP.vg_per_id = VG.gid and VGP.user_id = "+userId+""
								+ " where VG.company_Id = "+company_Id+" and VG.markForDelete = 0",
								Object[].class);
			}
			/* this only Select column */
			List<Object[]> results = queryt.getResultList();

			List<VehicleGroup> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<VehicleGroup>();
				VehicleGroup list = null;
				for (Object[] result : results) {
					list = new VehicleGroup();
					list.setGid((Long) result[0]);
					list.setvGroup((String) result[1]);
					list.setCompany_Id((Integer) result[2]);
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
		}
	}
	
	@Override
	public HashMap<Long, VehicleGroup> getVehicleGroupHMByCompanyId(Integer companyId) throws Exception {
		List<VehicleGroup>				vehicleGroupList		=	null;
		HashMap<Long, VehicleGroup>		vehicleGroupHM			= null;
		try {
			vehicleGroupHM	= new HashMap<>();
			vehicleGroupList		=	vehicleGrouprepository.findAllVehicleGroupByCompanyId(companyId);
			
			if(vehicleGroupList != null && !vehicleGroupList.isEmpty()) {
				for (VehicleGroup	vehicleGroup	: vehicleGroupList) {
					vehicleGroupHM.put(vehicleGroup.getGid(), vehicleGroup);
				}
			}
			return vehicleGroupHM;
		} catch (Exception e) {
			throw e;
		}finally {
			vehicleGroupList		=	null;
			vehicleGroupHM			= null;
		}
	}
	
	

	
	
}