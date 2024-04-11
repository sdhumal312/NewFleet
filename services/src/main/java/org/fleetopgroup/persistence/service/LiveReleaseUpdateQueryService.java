package org.fleetopgroup.persistence.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.fleetopgroup.persistence.serviceImpl.ILiveReleaseUpdateQueryService;
import org.springframework.stereotype.Service;

@Service
public class LiveReleaseUpdateQueryService implements ILiveReleaseUpdateQueryService {
	
	@PersistenceContext public 	EntityManager 	entityManager;
	
	@Override
	public void processUpdateLiveQuery() {
		
	}
}
