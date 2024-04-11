package org.fleetopgroup.persistence.service;

import java.util.List;

import org.fleetopgroup.persistence.dao.TripSheetToLhpvDetailsRepository;
import org.fleetopgroup.persistence.model.TripSheetToLhpvDetails;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetToLhpvDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripSheetToLhpvDetailsService implements ITripSheetToLhpvDetailsService {

	@Autowired	TripSheetToLhpvDetailsRepository			tripSheetToLhpvDetailsRepository;
	@Override
	public void saveTripSheetToLhpvDetails(TripSheetToLhpvDetails tripSheetToLhpvDetails) throws Exception {
			
		tripSheetToLhpvDetailsRepository.save(tripSheetToLhpvDetails);
	}

	@Override
	public void saveTripSheetToLhpvDetailsList(List<TripSheetToLhpvDetails> tripSheetToLhpvDetails) throws Exception {

		tripSheetToLhpvDetailsRepository.saveAll(tripSheetToLhpvDetails);
	}

}
