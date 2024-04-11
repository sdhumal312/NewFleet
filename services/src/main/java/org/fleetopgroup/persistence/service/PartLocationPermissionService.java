package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PartLocationsType;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.PartLocationPermissionRepository;
import org.fleetopgroup.persistence.model.PartLocationPermission;
import org.fleetopgroup.persistence.model.PartLocations;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PartLocationPermissionService implements IPartLocationPermissionService {

	@Autowired
	private PartLocationPermissionRepository partLocationPermissionRepository;

	@Autowired
	private ICompanyConfigurationService companyConfigurationService;

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<PartLocationPermission> getPartLocationPermissionList(long userId, Integer companyId) throws Exception {

		return partLocationPermissionRepository.getPartLocationPermissionList(userId, companyId);
	}

	@Override
	public StringBuffer getPartLocationPermission(long userId, Integer companyId) throws Exception {
		List<PartLocationPermission> PartLocationPermissionList = null;
		StringBuffer PartLocationIds = new StringBuffer();
		try {
			PartLocationPermissionList = getPartLocationPermissionList(userId, companyId);
			for (int i = 0; i < PartLocationPermissionList.size(); i++) {
				if (i < PartLocationPermissionList.size() - 1) {
					PartLocationIds = PartLocationIds
							.append(PartLocationPermissionList.get(i).getPartLocationId() + ",");
				} else {
					PartLocationIds = PartLocationIds.append(PartLocationPermissionList.get(i).getPartLocationId());
				}
			}
			return PartLocationIds;
		} catch (Exception e) {
			throw e;
		} finally {
			PartLocationPermissionList = null;
		}
	}

	@Override
	@Transactional
	public void deletePartLocationPermissionByUserId(long userId, Integer companyId) throws Exception {

		partLocationPermissionRepository.deletePartLocationPermissionByUserId(userId, companyId);

	}

	@Override
	@Transactional
	public void registerPartLocationPermissionByUserId(PartLocationPermission partLocationPermission) {

		partLocationPermissionRepository.save(partLocationPermission);
	}

	@Override
	@Transactional
	public List<PartLocations> getPartLocationPermissionIdWithName(long id, Integer company_id) throws Exception  {
		TypedQuery<Object[]> query = null;
		HashMap<String, Object> configuration = null;

		try {
			/* this only Select column */
			configuration = companyConfigurationService.getCompanyConfiguration(company_id,
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if (!(boolean) configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
				query = entityManager
						.createQuery("SELECT VG.partlocation_id, VG.partlocation_name from  PartLocations VG "
								+ " WHERE  VG.companyId =:comId AND VG.markForDelete = 0 AND VG.partLocationType ="+PartLocationsType.PART_LOCATION_TYPE_MAIN_LOCATION+" AND VG.isAutoCreated = 0", Object[].class);
			} else {

				query = entityManager
						.createQuery("SELECT VG.partlocation_id, VG.partlocation_name from PartLocationPermission PLP "
								+ " INNER JOIN PartLocations VG on PLP.partLocationId = VG.partlocation_id AND PLP.user_Id =:id"
								+ " WHERE PLP.companyId =:comId AND VG.markForDelete = 0 AND VG.partLocationType ="+PartLocationsType.PART_LOCATION_TYPE_MAIN_LOCATION+" AND VG.isAutoCreated = 0", Object[].class);
				query.setParameter("id", id);
			}
			query.setParameter("comId", company_id);
			List<Object[]> results = null;
			results = query.getResultList();

			List<PartLocations> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<PartLocations>();
				PartLocations list = null;
				for (Object[] result : results) {
					list = new PartLocations();
					list.setPartlocation_id((Integer) result[0]);
					list.setPartlocation_name((String) result[1]);
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			query			= null;
		}
	}
	
	@Override
	public List<PartLocations> getPartLocationForPermissionCheck(long id, Integer company_id) throws Exception {
		TypedQuery<Object[]> query = null;
		HashMap<String, Object> configuration = null;

		try {
			/* this only Select column */
			configuration = companyConfigurationService.getCompanyConfiguration(company_id,
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if (!(boolean) configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION)) {
				query = entityManager
						.createQuery("SELECT VG.partlocation_id, VG.partlocation_name from  PartLocations VG "
								+ " WHERE  VG.companyId =:comId AND VG.partLocationType ="+PartLocationsType.PART_LOCATION_TYPE_MAIN_LOCATION+" AND VG.markForDelete = 0 ", Object[].class);
			} else {

				query = entityManager
						.createQuery("SELECT VG.partlocation_id, VG.partlocation_name from PartLocationPermission PLP "
								+ " INNER JOIN PartLocations VG on PLP.partLocationId = VG.partlocation_id AND PLP.user_Id =:id OR  VG.isAutoCreated = 1"
								+ " WHERE PLP.companyId =:comId AND VG.partLocationType ="+PartLocationsType.PART_LOCATION_TYPE_MAIN_LOCATION+" AND VG.markForDelete = 0 ", Object[].class);
				query.setParameter("id", id);
			}
			query.setParameter("comId", company_id);
			List<Object[]> results = null;
			results = query.getResultList();

			List<PartLocations> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<PartLocations>();
				PartLocations list = null;
				for (Object[] result : results) {
					list = new PartLocations();
					list.setPartlocation_id((Integer) result[0]);
					list.setPartlocation_name((String) result[1]);
					Dtos.add(list);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			query			= null;
		}
	}
}
