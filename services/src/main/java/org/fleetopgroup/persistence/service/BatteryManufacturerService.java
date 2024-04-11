package org.fleetopgroup.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.BatteryManufacturerRepository;
import org.fleetopgroup.persistence.model.BatteryManufacturer;
import org.fleetopgroup.persistence.serviceImpl.IBatteryManufacturerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatteryManufacturerService implements IBatteryManufacturerService {
	
	@Autowired private BatteryManufacturerRepository	batteryManufacturerRepository;
	
	@PersistenceContext
	EntityManager entityManager;

	
	@Override
	public List<BatteryManufacturer> findAll() throws Exception {
		
		return batteryManufacturerRepository.findAll();
	}
	
	@Override
	public BatteryManufacturer getBatteryManufactureByID(Long batteryManufacturerId) {

		return batteryManufacturerRepository.getbatteryManufacturerByID(batteryManufacturerId);
	}
	
	
	@Override
	public long countBatteryManufactureByCompanyId(Integer company_Id) throws Exception {
		try {
			return batteryManufacturerRepository.countBatteryManufacturerByCompanyId();
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<BatteryManufacturer> validateBatteryManufacturer(String name) throws Exception {

		return batteryManufacturerRepository.validateBatteryManufacturer(name);
	}
	
	@Override
	public void save(BatteryManufacturer batteryManufacturer) throws Exception {

		batteryManufacturerRepository.save(batteryManufacturer);
	}
	
	@Override
	public List<BatteryManufacturer> getBatteryManufacturerList(String name) throws Exception {
		try {
			if(name != null && !name.trim().equalsIgnoreCase("") && name.indexOf('\'') != 0 ) {
			TypedQuery<BatteryManufacturer> queryt = entityManager
					.createQuery("select BM from BatteryManufacturer AS BM where lower(BM.manufacturerName) Like ('%"+ name + "%')"
					+ " AND  BM.markForDelete = 0 ", BatteryManufacturer.class);
			return queryt.getResultList();
			}else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
