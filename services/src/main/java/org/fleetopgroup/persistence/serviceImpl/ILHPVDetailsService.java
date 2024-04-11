package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.LHPVDetails;
import org.springframework.stereotype.Service;

@Service
public interface ILHPVDetailsService {

	public void saveLHPVDetails(LHPVDetails	lhpvDetails) throws Exception;
	
	public void saveLHPVDetailsList(List<LHPVDetails>	lhpvDetailsList) throws Exception;
	
	public List<LHPVDetails> 	validateLHPVDetails(Long	lhpvId) throws Exception;
	
	public List<LHPVDetails>  getLHPVDetailsDtoList(Integer vid) throws Exception;
	
	public LHPVDetails	getLHPVDetailsById(Long lHPVDetailsId) throws Exception;
	
	public void updateTripSheetCreated(String lhpvIds, Long tripSheetId, Integer companyId) throws Exception;
	
	public List<LHPVDetails>	getLHPVDetailsList(String lhpvIds, Integer companyId) throws Exception;
}
