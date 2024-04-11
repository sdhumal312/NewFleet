package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.persistence.model.TripSheetSequenceCounter;
import org.springframework.stereotype.Service;

@Service
public interface ITripsheetSequenceService {
	
	public TripSheetSequenceCounter findNextSequenceNumber(Integer companyId) throws Exception;
	
	public void updateNextSequenceNumber(long nextVal, long sequence_Id) throws Exception;
	

}
