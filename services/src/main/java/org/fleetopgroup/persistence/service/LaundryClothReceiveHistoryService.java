package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dao.LaundryClothReceiveHistoryRepository;
import org.fleetopgroup.persistence.dto.LaundryClothReceiveHistoryDto;
import org.fleetopgroup.persistence.model.LaundryClothReceiveHistory;
import org.fleetopgroup.persistence.serviceImpl.ILaundryClothReceiveHistoryService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LaundryClothReceiveHistoryService implements ILaundryClothReceiveHistoryService {
	
	@PersistenceContext EntityManager entityManager;

	@Autowired	private LaundryClothReceiveHistoryRepository		laundryClothReceiveHistoryRepository;
	
	@Override
	public void saveLaundryClothReceiveHistory(LaundryClothReceiveHistory clothReceiveHistory) throws Exception {
		
		laundryClothReceiveHistoryRepository.save(clothReceiveHistory);
	}
	
	@Override
	public List<LaundryClothReceiveHistoryDto> getReceivedClothDetailsList(Long laundryInvoiceId, Long laundryClothDetailsId) throws Exception {

		TypedQuery<Object[]> 							typedQuery 				= null;
		List<Object[]> 									resultList 				= null; 
		List<LaundryClothReceiveHistoryDto> 			receiveHistoryList 		= null;
		LaundryClothReceiveHistoryDto 					receiveHistory			= null;

		try {

			typedQuery = entityManager.createQuery("SELECT LH.clothReceiveHistoryId, LH.laundryInvoiceId, LH.laundryClothDetailsId, LH.receiveQuantity, LH.description, "
					+ " LH.receiveById, LH.receiveDate, U.firstName, CT.clothTypeName  "
					+ " FROM LaundryClothReceiveHistory AS LH "
					+ " INNER JOIN User U ON U.id = LH.receiveById"
					+ " INNER JOIN SentLaundryClothDetails SC ON SC.laundryClothDetailsId = LH.laundryClothDetailsId"
					+ " INNER JOIN ClothTypes CT ON CT.clothTypesId = LH.clothTypesId"
					+ " WHERE LH.laundryInvoiceId ="+laundryInvoiceId+" AND LH.laundryClothDetailsId = "+laundryClothDetailsId+" AND LH.markForDelete = 0 ORDER BY LH.laundryInvoiceId DESC", Object[].class);

			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				receiveHistoryList = new ArrayList<>();

				for (Object[] result : resultList) {
					receiveHistory = new LaundryClothReceiveHistoryDto();
					
					receiveHistory.setClothReceiveHistoryId((Long) result[0]);
					receiveHistory.setLaundryInvoiceId((Long) result[1]);
					receiveHistory.setLaundryClothDetailsId((Long) result[2]);
					receiveHistory.setReceiveQuantity((Double) result[3]);
					receiveHistory.setDescription((String) result[4]);
					receiveHistory.setReceiveById((Long) result[5]);
					receiveHistory.setReceiveDate((Timestamp) result[6]);
					receiveHistory.setReceivedBy((String) result[7]);
					receiveHistory.setClothTypesName((String) result[8]);
					
					if(receiveHistory.getReceiveDate() != null) {
						receiveHistory.setReceiveDateStr(DateTimeUtility.getDateFromTimeStamp(receiveHistory.getReceiveDate(), DateTimeUtility.DD_MM_YYYY));
					}
					
					receiveHistoryList.add(receiveHistory);
				}
			}

			return receiveHistoryList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			receiveHistoryList 		= null;
			receiveHistory			= null;
		}
	}
}
