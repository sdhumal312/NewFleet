package org.fleetopgroup.persistence.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.LHPVDetailsRepository;
import org.fleetopgroup.persistence.model.LHPVDetails;
import org.fleetopgroup.persistence.serviceImpl.ILHPVDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LHPVDetailsService implements ILHPVDetailsService {
	
	@PersistenceContext	public EntityManager 		entityManager;
	private @Autowired	LHPVDetailsRepository		lHPVDetailsRepository;

	@Override
	public void saveLHPVDetails(LHPVDetails lhpvDetails) throws Exception {
		
		lHPVDetailsRepository.save(lhpvDetails);
	}

	
	@Override
	public void saveLHPVDetailsList(List<LHPVDetails> lhpvDetailsList) throws Exception {

		lHPVDetailsRepository.saveAll(lhpvDetailsList);
	}
	
	@Override
	public List<LHPVDetails> validateLHPVDetails(Long lhpvId) throws Exception {
		
		return lHPVDetailsRepository.validateLHPVDetails(lhpvId);
	}
	
	@Override
	public List<LHPVDetails> getLHPVDetailsDtoList(Integer vid) throws Exception {
		
		return lHPVDetailsRepository.getLHPVDetailsDtoList(vid);
	}
	
	@Override
	public LHPVDetails getLHPVDetailsById(Long lHPVDetailsId) throws Exception {
		try {
			return lHPVDetailsRepository.getLHPVDetailsById(lHPVDetailsId);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateTripSheetCreated(String lhpvIds, Long tripSheetId, Integer companyId) throws Exception {

		
		entityManager.createQuery(
				" UPDATE LHPVDetails AS LH SET LH.isTripSheetCreated = 1, LH.tripSheetId = "+tripSheetId+" where lHPVDetailsId IN ("+lhpvIds+") ")
				.executeUpdate();
		
	}
	
	@Override
	public List<LHPVDetails> getLHPVDetailsList(String lhpvIds, Integer companyId) throws Exception {
		List<LHPVDetails> 		lHPVDetailsList		= null;
		try {
			TypedQuery<LHPVDetails> queryt = null;
			queryt = entityManager.createQuery(
					"SELECT lh  FROM LHPVDetails lh "
							 + " WHERE lh.lHPVDetailsId IN ("+lhpvIds+") AND lh.companyId = "+companyId+""
							+ " and lh.markForDelete = 0 ",
							LHPVDetails.class);
			
			lHPVDetailsList	=	queryt.getResultList();
			return lHPVDetailsList;
			
		} catch (Exception e) {
			throw e;
		}finally {
			lHPVDetailsList		= null;
		}
		
	}
}
