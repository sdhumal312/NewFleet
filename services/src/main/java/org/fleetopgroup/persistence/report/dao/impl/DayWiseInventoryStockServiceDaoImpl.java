package org.fleetopgroup.persistence.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.InventoryStockTypeConstant;
import org.fleetopgroup.persistence.dto.DayWiseInventoryStockDto;
import org.fleetopgroup.persistence.model.DayWiseInventoryStock;
import org.fleetopgroup.persistence.report.dao.DayWiseInventoryStockServiceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class DayWiseInventoryStockServiceDaoImpl implements DayWiseInventoryStockServiceDao {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext
	public EntityManager entityManager;

	
	@Override
	public DayWiseInventoryStock getOpeningClosingQuantityForPreviousDate(short txnTypeId, String txnDate,
			Integer locationId, Long partId) throws Exception {
		try {
			TypedQuery<DayWiseInventoryStock> query = entityManager.createQuery(
					"SELECT VAE "
					+ " from DayWiseInventoryStock VAE "
					+ " WHERE VAE.locationId ="+locationId+" and VAE.partId = "+partId+" AND VAE.transactionType = "+txnTypeId+" "
							+ " AND VAE.transactionDate < '"+txnDate+"' AND VAE.markForDelete = 0 order by VAE.transactionDate DESC ", DayWiseInventoryStock.class);

			query.setMaxResults(1);
			try {
				return query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
			
		} catch (Exception e) {
			LOGGER.error("Exeception ", e);
			throw e;
		}
	}

	@Override
	public DayWiseInventoryStock getOpeningClosingQuantityForDate(short txnTypeId, String txnDate,
			Integer locationId, Long partId) throws Exception {
		try {
			TypedQuery<DayWiseInventoryStock> query = entityManager.createQuery(
					"SELECT VAE "
					+ " from DayWiseInventoryStock VAE "
					+ " WHERE VAE.locationId ="+locationId+" AND VAE.partId = "+partId+" AND VAE.transactionType = "+txnTypeId+" AND VAE.transactionDate = '"+txnDate+"' AND VAE.markForDelete = 0", DayWiseInventoryStock.class);

			query.setMaxResults(1);
			try {
				return query.getSingleResult();
			} catch (Exception e) {
				return null;
			}
			
		} catch (Exception e) {
			LOGGER.error("Exeception ", e);
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updateClosingQuantity(Double quantity, String txnDate, Integer locationId, Long partId)
			throws Exception {
		  entityManager.createQuery(
			        "UPDATE DayWiseInventoryStock SET addedQuantity = "+quantity+" " 
			        +" where transactionDate = '" + txnDate + "' AND locationId=" + locationId +" AND partId = "+partId+" AND transactionType = "+InventoryStockTypeConstant.STOCK_TYPE_CLOSING+" AND markForDelete=0")
			        .executeUpdate();
	}
	
	@Override
	@Transactional
	public void updateClosingQuantityForNextDates(Double quantity, String txnDate, Integer locationId, Long partId)
			throws Exception {
		entityManager.createQuery(
		        "UPDATE DayWiseInventoryStock SET addedQuantity = addedQuantity + "+quantity+" " 
		        +" where transactionDate > '" + txnDate + "' AND locationId=" + locationId +" AND partId = "+partId+" AND transactionType = "+InventoryStockTypeConstant.STOCK_TYPE_CLOSING+" AND markForDelete = 0")
		        .executeUpdate();
}
	
	@Override
	public List<DayWiseInventoryStockDto> getDayWiseInventoryStockList(String query, Integer companyId)
			throws Exception {

		TypedQuery<Object[]> 						typedQuery 				= null;
		List<Object[]> 								resultList 				= null; 
		List<DayWiseInventoryStockDto> 				inventoryList 			= null;
		DayWiseInventoryStockDto 					dayWiseInventoryStockDto			= null;
		try {
				SimpleDateFormat	format = new SimpleDateFormat("dd-MM-yyyy");
				typedQuery = entityManager.createQuery(" SELECT DS.dayWiseInventoryStockId, DS.partId, DS.locationId, DS.addedQuantity,"
						+ " DS.usedQuantity, DS.transactionType, DS.transactionId, DS.transactionDate, MP.partname, PL.partlocation_name  "
						+ " FROM DayWiseInventoryStock DS "
						+ " INNER JOIN MasterParts MP ON MP.partid = DS.partId"
						+ " INNER JOIN PartLocations PL ON PL.partlocation_id = DS.locationId"
						+ " where DS.companyId = "+companyId+" "+query+" AND DS.transactionType = "+InventoryStockTypeConstant.STOCK_TYPE_CLOSING+" "
						+ " AND DS.markForDelete = 0 ORDER BY DS.transactionDate DESC", Object[].class);

				typedQuery.setMaxResults(1);
			resultList = typedQuery.getResultList();

			if (resultList != null && !resultList.isEmpty()) {
				inventoryList = new ArrayList<>();
				
				for (Object[] result : resultList) {
					dayWiseInventoryStockDto = new DayWiseInventoryStockDto();
					
					dayWiseInventoryStockDto.setDayWiseInventoryStockId((Long) result[0]);
					dayWiseInventoryStockDto.setPartId((Long) result[1]);
					dayWiseInventoryStockDto.setLocationId((Integer) result[2]);
					dayWiseInventoryStockDto.setAddedQuantity((Double) result[3]);
					dayWiseInventoryStockDto.setUsedQuantity((Double) result[4]);
					dayWiseInventoryStockDto.setTransactionType((short) result[5]);
					dayWiseInventoryStockDto.setTransactionId((Long) result[6]);
					dayWiseInventoryStockDto.setTransactionDate((Date) result[7]);
					dayWiseInventoryStockDto.setPartName((String) result[8]);
					dayWiseInventoryStockDto.setLocationName((String) result[9]);
					
					dayWiseInventoryStockDto.setTransactionDateStr(format.format(dayWiseInventoryStockDto.getTransactionDate()));
					
					inventoryList.add(dayWiseInventoryStockDto);
					
				}
			}
			
			return inventoryList;
		} catch (Exception e) {
			throw e;
		} finally {
			typedQuery 							= null;
			resultList 							= null;
			inventoryList 						= null;
			dayWiseInventoryStockDto			= null;
		}
	}

}
