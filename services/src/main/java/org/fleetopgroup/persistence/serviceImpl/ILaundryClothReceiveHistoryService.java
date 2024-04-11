package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.LaundryClothReceiveHistoryDto;
import org.fleetopgroup.persistence.model.LaundryClothReceiveHistory;

public interface ILaundryClothReceiveHistoryService {

	public void saveLaundryClothReceiveHistory(LaundryClothReceiveHistory	clothReceiveHistory) throws Exception;
	
	List<LaundryClothReceiveHistoryDto>  getReceivedClothDetailsList(Long laundryInvoiceId, Long laundryClothDetailsId) throws Exception;
}
