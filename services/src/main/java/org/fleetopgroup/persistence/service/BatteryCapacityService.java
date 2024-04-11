package org.fleetopgroup.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.BatteryCapacityRepository;
import org.fleetopgroup.persistence.model.BatteryCapacity;
import org.fleetopgroup.persistence.serviceImpl.IBatteryCapacityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatteryCapacityService implements IBatteryCapacityService {

	@Autowired	BatteryCapacityRepository		batteryCapacityRepository;
	@PersistenceContext	EntityManager entityManager;

	
	@Override
	public List<BatteryCapacity> getBatteryCapacityList() throws Exception {
		
		return batteryCapacityRepository.findAll();
	}

	@Override
	public void save(BatteryCapacity batteryCapacity) throws Exception {
		batteryCapacityRepository.save(batteryCapacity);
	}
	
	@Override
	public List<BatteryCapacity> validateBatteryCapacity(String capacity) throws Exception {
		
		return batteryCapacityRepository.validateBatteryCapacity(capacity);
	}
	
	@Override
	public List<BatteryCapacity> searchBatteryCapacity(String term) throws Exception {
		try {
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			TypedQuery<BatteryCapacity> queryt = entityManager.createQuery(
					"SELECT BC FROM BatteryCapacity  AS BC "
					+ " where lower(BC.batteryCapacity) Like ('%"
							+ term + "%')  AND BC.markForDelete = 0 ",
							BatteryCapacity.class);
			return queryt.getResultList();
			}else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}
}
