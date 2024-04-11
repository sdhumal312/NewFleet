package org.fleetopgroup.persistence.service;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.LoadingSheetToTripSheetRepository;
import org.fleetopgroup.persistence.model.LoadingSheetToTripSheet;
import org.fleetopgroup.persistence.serviceImpl.ILoadingSheetToTripSheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoadingSheetToTripSheetService implements ILoadingSheetToTripSheetService {
	
	@PersistenceContext	EntityManager entityManager;

	@Autowired	LoadingSheetToTripSheetRepository		loadingSheetToTripSheetRepository;
	
	@Override
	public void saveLoadingSheetToTripSheet(LoadingSheetToTripSheet loadingSheetToTripSheet) throws Exception {
		
		loadingSheetToTripSheetRepository.save(loadingSheetToTripSheet);
	}
	
	@Override
	@Transactional
	public void saveAllLoadingSheetToTripSheet(List<LoadingSheetToTripSheet> loadingSheetToTripSheet) throws Exception {
		
		loadingSheetToTripSheetRepository.saveAll(loadingSheetToTripSheet);
	}

	@Override
	public HashMap<String, LoadingSheetToTripSheet> validateLoadingSheetAdded(Long tripSheetId, Integer companyId)
			throws Exception {
		HashMap<String, LoadingSheetToTripSheet>	hashMap = null;
		TypedQuery<Object[]> query = entityManager.createQuery(
				"SELECT T.loadingSheetToTripSheetId, T.dispatchLedgerId, T.wayBillId FROM LoadingSheetToTripSheet T "
				+ " WHERE T.tripSheetId = "+tripSheetId+" AND T.companyId = "+companyId+" AND T.markForDelete = 0", Object[].class);

		List<Object[]> results = query.getResultList();

			if (results != null && !results.isEmpty()) {
				LoadingSheetToTripSheet		loadingSheetToTripSheet	= null;
				hashMap	= new HashMap<>();
				for (Object[] vehicle : results) {
					loadingSheetToTripSheet	= new LoadingSheetToTripSheet();
					
					loadingSheetToTripSheet.setLoadingSheetToTripSheetId((Long) vehicle[0]);
					loadingSheetToTripSheet.setDispatchLedgerId((long) vehicle[1]);
					loadingSheetToTripSheet.setWayBillId((long) vehicle[2]);
					
					hashMap.put(loadingSheetToTripSheet.getDispatchLedgerId()+"_"+loadingSheetToTripSheet.getWayBillId(), loadingSheetToTripSheet);
				}
			}
		
		return hashMap;
		
	}
	
	@Override
	public List<LoadingSheetToTripSheet> getLoadingSheetToTripSheetByDispatchLedgerId(Long dispatchLedgerId, Long tripSheetId)
			throws Exception {
		
		return loadingSheetToTripSheetRepository.getLoadingSheetToTripSheetByDispatchLedgerId(dispatchLedgerId, tripSheetId);
	}
	
	@Override
	public List<LoadingSheetToTripSheet> getLoadingSheetToTripSheetByTripSheetId(Long dispatchLedgerId)
			throws Exception {
		
		return loadingSheetToTripSheetRepository.getLoadingSheetToTripSheetByTripSheetId(dispatchLedgerId);
	}
}
