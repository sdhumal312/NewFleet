package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.TripSheetToLhpvDetails;

public interface ITripSheetToLhpvDetailsService {

	public void saveTripSheetToLhpvDetails(TripSheetToLhpvDetails	tripSheetToLhpvDetails) throws Exception;
	
	public void saveTripSheetToLhpvDetailsList(List<TripSheetToLhpvDetails>	tripSheetToLhpvDetails) throws Exception;
}
