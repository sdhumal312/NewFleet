package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.TripSheetHistory;
import org.springframework.stereotype.Service;

@Service
public interface ITripSheetHistoryService {

	public void addTripSheetHistory(TripSheetHistory tripSheetHistory) throws Exception;
}
