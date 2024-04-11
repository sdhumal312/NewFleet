package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.VehicleInspectionParameterRepository;
import org.fleetopgroup.persistence.model.InspectionParameter;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionParameterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleInspectionParameterService implements IVehicleInspectionParameterService{

	private @Autowired	VehicleInspectionParameterRepository		vehicleInspectionParameterRepository;
	
	@PersistenceContext	public EntityManager entityManager;

	
	@Override
	public List<InspectionParameter> getInspectionParameterList(Integer companyId) throws Exception {
		
		return vehicleInspectionParameterRepository.getInspectionParameterList(companyId);
	}
	
	@Transactional
	@Override
	public InspectionParameter findByParamterName(String parameterName, Integer companyId) throws Exception {

		return vehicleInspectionParameterRepository.findByParamterName(parameterName,companyId);
	}
	
	@Transactional
	@Override
	public InspectionParameter findByParamterId(long parameterId,Integer companyId) throws Exception {

		return vehicleInspectionParameterRepository.findByParamterId(parameterId,companyId);
	}
	
	@Transactional
	@Override
	public InspectionParameter findByInspectionParameterId(long id,Integer companyId) throws Exception {

		return vehicleInspectionParameterRepository.findByParamterId(id,companyId);
	}
	
	@Transactional
	public InspectionParameter registerNewInspectionParameter(InspectionParameter inspectionParameter) throws Exception 
	{
		return vehicleInspectionParameterRepository.save(inspectionParameter);
		
	}
	
	@Transactional
	public void deleteParameter(long photoid, Integer companyId) {

		vehicleInspectionParameterRepository.deleteParameter(photoid,companyId);
	}
	
	@Transactional
	public void updateParameter(Long id, Timestamp lastUpdated, long photoId) {
		vehicleInspectionParameterRepository.updateParameter(id,lastUpdated,photoId);
	}
	
	@Override
	public List<InspectionParameter> getInspectionParameterListForDropDown(String term)
			throws Exception {
		try {

			List<InspectionParameter> Dtos = null;
			TypedQuery<Object[]> queryt = null;
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
				queryt = entityManager.createQuery("SELECT IP.inspectionParameterId, IP.parameterName "
						+ " FROM InspectionParameter  AS IP "
						+ " where  lower(IP.parameterName) Like ('%" + term + "%')  and IP.markForDelete = 0", Object[].class);

			List<Object[]> results = queryt.getResultList();

			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<InspectionParameter>();
				InspectionParameter dropdown = null;
				for (Object[] result : results) {
					dropdown = new InspectionParameter();
					
					dropdown.setInspectionParameterId((Long) result[0]);
					dropdown.setParameterName((String) result[1]);

					Dtos.add(dropdown);
				}
			}
			}
			return Dtos;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional
	public void updateParameterPhotoIdNull(Long id, String name, Timestamp lastUpdated) {
		vehicleInspectionParameterRepository.updateParameterPhotoIdNull(id,name,lastUpdated);
	}
	@Transactional
	public void updateParameterParameterName(Long id, String name, Timestamp lastUpdated,long docId) {
		vehicleInspectionParameterRepository.updateParameterParameterName(id,name,lastUpdated,docId);
	}
	
	@Override
	public Map<Long, InspectionParameter> getInspectionParameterHM(Integer companyId) throws Exception {
		try {
			List<InspectionParameter> list = getInspectionParameterList(companyId);
			Map<Long, InspectionParameter> map = new HashMap<Long,InspectionParameter>();
			for (InspectionParameter i : list) map.put(i.getInspectionParameterId(),i);
			
			return map;
		} catch (Exception e) {
			throw e;
		}
	}
}
