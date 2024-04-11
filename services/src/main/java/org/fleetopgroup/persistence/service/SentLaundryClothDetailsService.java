package org.fleetopgroup.persistence.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dto.SentLaundryClothDetailsDto;
import org.fleetopgroup.persistence.serviceImpl.ISentLaundryClothDetailsService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SentLaundryClothDetailsService implements	ISentLaundryClothDetailsService{

	@PersistenceContext EntityManager entityManager;
	DecimalFormat toFixedTwo = new DecimalFormat("#.##");
	
	@Override
	public List<SentLaundryClothDetailsDto> getSentLaundryClothDetailsDto(Long invoiceId, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> 					typedQuery 				= null;
		List<Object[]> 							resultList 				= null; 
		List<SentLaundryClothDetailsDto> 		clothInvoiceList 		= null;
		SentLaundryClothDetailsDto 				clothInvoiceDto			= null;
		double									total					= 0.0;
		double									reaminingQty			= 0.0;
		
		try {

			typedQuery = entityManager.createQuery("SELECT CD.laundryClothDetailsId, CD.clothTypesId, CD.laundryInvoiceId, CD.quantity, CD.receivedQuantity, "
					+ " CD.losedQuantity, CD.damagedQuantity, CD.clothEachCost, CD.clothDiscount, CD.clothGst, CD.clothTotal, CT.clothTypeName"
					+ " FROM SentLaundryClothDetails AS CD"
					+ " INNER JOIN ClothTypes CT ON CT.clothTypesId = CD.clothTypesId"
					+ " WHERE CD.laundryInvoiceId = "+invoiceId+" AND CD.companyId ="+companyId+" AND CD.markForDelete = 0 ORDER BY CD.laundryClothDetailsId DESC", Object[].class);


			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				clothInvoiceList = new ArrayList<>();

				for (Object[] result : resultList) {
					clothInvoiceDto = new SentLaundryClothDetailsDto();
					
					clothInvoiceDto.setLaundryClothDetailsId((Long) result[0]);
					clothInvoiceDto.setClothTypesId((Long) result[1]);
					clothInvoiceDto.setLaundryInvoiceId((Long) result[2]);
					if(result[3] != null)
					clothInvoiceDto.setQuantity(Double.parseDouble(toFixedTwo.format(result[3])));
					if(result[4] != null)
					clothInvoiceDto.setReceivedQuantity(Double.parseDouble(toFixedTwo.format(result[4])));
					if(result[5] != null)
					clothInvoiceDto.setLosedQuantity(Double.parseDouble(toFixedTwo.format(result[5])));
					if(result[6] != null)
					clothInvoiceDto.setDamagedQuantity(Double.parseDouble(toFixedTwo.format(result[6])));
					if(result[7] != null)
					clothInvoiceDto.setClothEachCost(Double.parseDouble(toFixedTwo.format(result[7])));
					if(result[8] != null)
					clothInvoiceDto.setClothDiscount(Double.parseDouble(toFixedTwo.format(result[8])));
					if(result[9] != null)
					clothInvoiceDto.setClothGst(Double.parseDouble(toFixedTwo.format(result[9])));
					if(result[10] != null)
					clothInvoiceDto.setClothTotal(Double.parseDouble(toFixedTwo.format(result[10])));
					clothInvoiceDto.setClothTypeName((String) result[11]);
					
					total = clothInvoiceDto.getReceivedQuantity() + clothInvoiceDto.getDamagedQuantity() + clothInvoiceDto.getLosedQuantity();
					reaminingQty = clothInvoiceDto.getQuantity() - total;
					clothInvoiceDto.setRemainingQuantity(Double.parseDouble(toFixedTwo.format(reaminingQty)));
					
					clothInvoiceList.add(clothInvoiceDto);
				}
			}

			return clothInvoiceList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 				= null;
			resultList 				= null;
			clothInvoiceList 		= null;
			clothInvoiceDto			= null;
		}
	}

	@Override
	@Transactional
	public void updateSentLaundryDetailsToReceive(ValueObject valueObject) throws Exception {
		double                              receiveQuantity     = 0;
		Integer								InvoiceId			= 0;
		
		receiveQuantity   	= valueObject.getDouble("receiveQuantity");
		InvoiceId		   	= valueObject.getInt("laundryClothDetailsId");
		
		entityManager.createQuery(
				" UPDATE SentLaundryClothDetails AS VC SET VC.receivedQuantity = VC.receivedQuantity + "+receiveQuantity+" "
						+ " WHERE VC.laundryClothDetailsId = "+InvoiceId+" "
						+ "  AND VC.markForDelete = 0 ")
				.executeUpdate();
		
	}
	
	@Override
	@Transactional
	public void updateSentLaundryDetailsToDamage(ValueObject valueObject) throws Exception {
		double                              damageQuantity     	= 0;
		Integer								clothDetailsId		= 0;
		
		damageQuantity   	= valueObject.getDouble("damageQuantity");
		clothDetailsId		= valueObject.getInt("damagelaundryClothDetailsId");
		
		entityManager.createQuery(
				" UPDATE SentLaundryClothDetails AS VC SET VC.damagedQuantity = VC.damagedQuantity + "+damageQuantity+" "
						+ " WHERE VC.laundryClothDetailsId = "+clothDetailsId+" "
						+ "  AND VC.markForDelete = 0 ")
		.executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateSentLaundryDetailsToLost(ValueObject valueObject) throws Exception {
		double                              lostQuantity     	= 0;
		Integer								clothDetailsId		= 0;
		
		lostQuantity   		= valueObject.getDouble("lostQuantity");
		clothDetailsId		= valueObject.getInt("lostlaundryClothDetailsId");
		
		entityManager.createQuery(
				" UPDATE SentLaundryClothDetails AS VC SET VC.losedQuantity = VC.losedQuantity + "+lostQuantity+" "
						+ " WHERE VC.laundryClothDetailsId = "+clothDetailsId+" "
						+ "  AND VC.markForDelete = 0 ")
		.executeUpdate();
	}
	
	@Override
	public SentLaundryClothDetailsDto getDamageWashingQtyDetails(Long clothDetailsId, Integer companyId) throws Exception {
		double									total					= 0.0;
		double									reaminingQty			= 0.0;
		try {
			Query query = entityManager.createQuery("SELECT CD.laundryClothDetailsId, CD.clothTypesId, CD.laundryInvoiceId, CD.quantity, CD.receivedQuantity, "
					+ " CD.losedQuantity, CD.damagedQuantity, CD.clothEachCost, CD.clothDiscount, CD.clothGst, CD.clothTotal, CT.clothTypeName"
					+ " FROM SentLaundryClothDetails AS CD"
					+ " INNER JOIN ClothTypes CT ON CT.clothTypesId = CD.clothTypesId"
					+ " WHERE CD.laundryClothDetailsId = "+clothDetailsId+" AND CD.companyId ="+companyId+" AND CD.markForDelete = 0 ");
			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
				
			} catch (NoResultException nre) {
				
			}
			
			SentLaundryClothDetailsDto clothInvoiceDto;
			if (result != null) {
				clothInvoiceDto = new SentLaundryClothDetailsDto();
				
				clothInvoiceDto.setLaundryClothDetailsId((Long) result[0]);
				clothInvoiceDto.setClothTypesId((Long) result[1]);
				clothInvoiceDto.setLaundryInvoiceId((Long) result[2]);
				clothInvoiceDto.setQuantity(Double.parseDouble(toFixedTwo.format(result[3])));
				clothInvoiceDto.setReceivedQuantity(Double.parseDouble(toFixedTwo.format( result[4])));
				clothInvoiceDto.setLosedQuantity(Double.parseDouble(toFixedTwo.format( result[5])));
				clothInvoiceDto.setDamagedQuantity(Double.parseDouble(toFixedTwo.format( result[6])));
				clothInvoiceDto.setClothEachCost((Double) result[7]);
				clothInvoiceDto.setClothDiscount((Double) result[8]);
				clothInvoiceDto.setClothGst((Double) result[9]);
				clothInvoiceDto.setClothTotal((Double) result[10]);
				clothInvoiceDto.setClothTypeName((String) result[11]);
				
				total = clothInvoiceDto.getReceivedQuantity() + clothInvoiceDto.getDamagedQuantity() + clothInvoiceDto.getLosedQuantity();
				reaminingQty = clothInvoiceDto.getQuantity() - total;
				clothInvoiceDto.setRemainingQuantity(reaminingQty);
				
			} else {
				return null;
			}
	
			return clothInvoiceDto;	

		} catch (Exception e) {
			throw e;
	  }
   }
}
