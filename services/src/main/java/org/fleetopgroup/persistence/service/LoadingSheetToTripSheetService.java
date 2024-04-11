package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.LoadingSheetToTripSheetRepository;
import org.fleetopgroup.persistence.dto.LoadingSheetToTripSheetDto;
import org.fleetopgroup.persistence.model.LoadingSheetToTripSheet;
import org.fleetopgroup.persistence.serviceImpl.ILoadingSheetToTripSheetService;
import org.fleetopgroup.web.util.DateTimeUtility;
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
	
	@SuppressWarnings("null")
	public List<LoadingSheetToTripSheetDto>  getLoadingSheetTotripSheetDetailsByTripSheetId(Integer companyId,Long tripSheetId) throws Exception{

		List<LoadingSheetToTripSheetDto> loadingSheetList = null;
		
		TypedQuery<Object[]> query = entityManager.createQuery("SELECT LS.lsNumber, LS.lsSourceBranch, LS.lsDestinationBranch, "
				+ " T.incomeAmount, T.incomeRefence, LS.tripDateTime FROM LoadingSheetToTripSheet LS "
				+ " INNER JOIN TripSheetIncome T ON T.dispatchLedgerId = LS.dispatchLedgerId "
				+ " WHERE LS.tripSheetId = "+ tripSheetId + " AND LS.companyId= "+ companyId , Object[].class);
			List<Object[]> results = null;
			try {
				results = query.getResultList();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}
			LoadingSheetToTripSheetDto		loadingSheet	= null;
			ArrayList<String> al = new ArrayList<>();
			loadingSheetList = new ArrayList<LoadingSheetToTripSheetDto>();
			if(results != null && !results.isEmpty()) {
				for(Object[] result : results) {
					loadingSheet	= new LoadingSheetToTripSheetDto();
					loadingSheet.setLsNumber((String) result[0]);
					loadingSheet.setLsSourceBranch((String) result[1]);
					loadingSheet.setLsDestinationBranch((String) result[2]);
					loadingSheet.setTripDateTime((Timestamp) result[5]);
					loadingSheet.setTripIncome((Double) result[3]);
					if(!al.contains(loadingSheet.getLsNumber())) {
						loadingSheetList.add(loadingSheet);
					}
					al.add(loadingSheet.getLsNumber());
				}
			}
		
		return loadingSheetList;
	}
}
