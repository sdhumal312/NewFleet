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

import org.fleetopgroup.persistence.dao.VehicleTyreSizeRepository;
import org.fleetopgroup.persistence.model.VehicleTyreSize;
import org.fleetopgroup.persistence.serviceImpl.IVehicleTyreSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fleetop
 *
 */
@Service("VehicleTyreSizeService")
@Transactional
public class VehicleTyreSizeService implements IVehicleTyreSizeService {

	@Autowired
	private VehicleTyreSizeRepository vehicleTyreSizeRepository;

	@PersistenceContext
	EntityManager entityManager;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreSize#
	 * registerNewVehicleType(org.fleetop.persistence.model.VehicleTyreSize)
	 */
	@Transactional
	public VehicleTyreSize registerNewVehicleType(VehicleTyreSize accountDto) throws Exception {

		return vehicleTyreSizeRepository.save(accountDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IVehicleTyreSize#updateVehicleType(
	 * java.lang.String, java.lang.String, java.lang.String, java.util.Date,
	 * java.lang.Integer)
	 */
	@Transactional
	public void update_VehicleTyre_size(String tyre_size, String tyre_size_descrition,  Long updateById, Date updateDate, Integer tid, Integer companyId) throws Exception {

		vehicleTyreSizeRepository.update_VehicleTyre_size(tyre_size, tyre_size_descrition, updateById, updateDate, tid, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreSize#findAll()
	 */
	@Transactional
	public List<VehicleTyreSize> findAll() {

		return vehicleTyreSizeRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IVehicleTyreSize#findByVtype(java.
	 * lang.String)
	 */
	@Transactional
	@Override
	public VehicleTyreSize findByTYRE_SIZE(String tyre_size, Integer companyId) throws Exception {

		return vehicleTyreSizeRepository.findByTYRE_SIZE(tyre_size, companyId);
	}
	
	@Override
	public VehicleTyreSize findByTYRE_SIZE(String tyre_size) throws Exception {
	
		return vehicleTyreSizeRepository.findByTYRE_SIZE(tyre_size);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IVehicleTyreSize#deleteVehicleType(
	 * java.lang.Integer)
	 */
	@Transactional
	public void delete_Vehicle_TyreSize(Integer tid, Integer companyId) {

		vehicleTyreSizeRepository.delete_Vehicle_TyreSize(tid, companyId);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.IVehicleTyreSize#getVehicleTypeByID(
	 * java.lang.Integer)
	 */
	@Transactional
	public VehicleTyreSize get_Vehicle_Tyre_ID(Integer TS_id, Integer companyid) {

		return vehicleTyreSizeRepository.get_Vehicle_Tyre_ID(TS_id, companyid);
	}

	@Override
	@Transactional
	public VehicleTyreSize get_Vehicle_Tyre_ID(Integer TS_id) {
		
		return vehicleTyreSizeRepository.get_Vehicle_Tyre_ID(TS_id);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreSize#count()
	 */
	@Transactional
	public long count() {

		return vehicleTyreSizeRepository.count();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.IVehicleTyreSizeService#
	 * Search_VehicleTyreSizeType_select(java.lang.String)
	 */
	@Transactional
	@Override
	public List<VehicleTyreSize> Search_VehicleTyreSizeType_select(String search, Integer companyId) throws Exception {
		List<VehicleTyreSize> Dtos = null;
		if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> query = entityManager
				.createQuery("SELECT c.TS_ID, c.TYRE_SIZE from VehicleTyreSize AS c where lower(c.TYRE_SIZE) Like ('%"
						+ search + "%') and c.companyId = "+companyId+" and c.markForDelete = 0 AND c.ownTyreSize = 1", Object[].class);

		List<Object[]> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleTyreSize>();

			VehicleTyreSize Tyresize = null;
			for (Object[] result : results) {
				Tyresize = new VehicleTyreSize();
				Tyresize.setTS_ID((Integer) result[0]);
				Tyresize.setTYRE_SIZE((String) result[1]);

				Dtos.add(Tyresize);
			}
		}
		}
		return Dtos;

	}
	
	@Override
	public List<VehicleTyreSize> Search_VehicleTyreSizeType_select(String search) throws Exception {
		List<VehicleTyreSize> Dtos = null;
		if(search != null && !search.trim().equalsIgnoreCase("") && search.indexOf('\'') != 0 ) {
		TypedQuery<Object[]> query = entityManager
				.createQuery("SELECT c.TS_ID, c.TYRE_SIZE from VehicleTyreSize AS c where lower(c.TYRE_SIZE) Like ('%"
						+ search + "%') and c.markForDelete = 0 AND c.ownTyreSize = 0", Object[].class);

		List<Object[]> results = query.getResultList();

		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<VehicleTyreSize>();

			VehicleTyreSize Tyresize = null;
			for (Object[] result : results) {
				Tyresize = new VehicleTyreSize();
				Tyresize.setTS_ID((Integer) result[0]);
				Tyresize.setTYRE_SIZE((String) result[1]);

				Dtos.add(Tyresize);
			}
		}
		}
		return Dtos;

	}

	@Override
	public List<VehicleTyreSize> findAllByCompanyId(Integer companyId) throws Exception {
		try {
			return vehicleTyreSizeRepository.findAllByCompanyId(companyId);
		} catch (Exception e) {
			throw e;
		}
	}

}
