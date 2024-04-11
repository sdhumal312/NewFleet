package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.constant.InventoryStockTypeConstant;
import org.fleetopgroup.persistence.bl.RefreshmentEntriesBL;
import org.fleetopgroup.persistence.dao.DayWiseInventoryStockRepository;
import org.fleetopgroup.persistence.dto.DayWiseInventoryStockDto;
import org.fleetopgroup.persistence.model.DayWiseInventoryStock;
import org.fleetopgroup.persistence.report.dao.DayWiseInventoryStockServiceDao;
import org.fleetopgroup.persistence.serviceImpl.IDayWiseInventoryStockService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DayWiseInventoryStockService implements IDayWiseInventoryStockService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	private DayWiseInventoryStockServiceDao		dayWiseInventoryStockServiceDao;
	@Autowired	private DayWiseInventoryStockRepository		dayWiseInventoryStockRepository;

	SimpleDateFormat	format		= new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD);
	SimpleDateFormat	dateFormat	= new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
	
	RefreshmentEntriesBL	entiresBL	= new RefreshmentEntriesBL();
	
	
	@Override
	@Transactional
	public void processLocationWiseInventoryStockDetails(ValueObject valueObject) throws Exception {
		DayWiseInventoryStock				dayWiseInventoryStock				= null;
		try {
			
			if(valueObject.getShort("transactionType", (short) 0) != InventoryStockTypeConstant.STOCK_TYPE_DELETED) {
				dayWiseInventoryStock	=	entiresBL.getDayWiseInventoryStockDTOForInsert(valueObject);
				
				DayWiseInventoryStock	validate	=	dayWiseInventoryStockRepository.getDayWiseInventoryStockDetails(valueObject.getLong("transactionId"), valueObject.getShort("transactionType", (short) 0));
				if(validate != null) {
					dayWiseInventoryStock.setDayWiseInventoryStockId(validate.getDayWiseInventoryStockId());
				}
				dayWiseInventoryStockRepository.save(dayWiseInventoryStock);
				
			}else {
				dayWiseInventoryStockRepository.deleteDayWiseInventoryStock(valueObject.getLong("transactionId"), valueObject.getShort("primaryType", (short) 0));
			}
			
			procressOpeningClosingLocationWiseInventoryStock(valueObject);
		} catch (Exception e) {
			LOGGER.error("Exeception ", e);
			System.err.println("inside exception "+e);
			throw e;
		}finally {
			dayWiseInventoryStock				= null;
		}
		
	}
	
	@Override
	@Transactional
	public void procressOpeningClosingLocationWiseInventoryStock(ValueObject valueObject) throws Exception {
		
		Date					transactionDate			= null;
		DayWiseInventoryStock	preDetails				= null;
		DayWiseInventoryStock	preClosingBalance		= null;
		Double					quantity				= 0.0;
		try {
			
			transactionDate	= (Date) valueObject.get("transactionDate");
			quantity	= valueObject.getDouble("quantity", 0);
					
			preDetails			= dayWiseInventoryStockServiceDao.getOpeningClosingQuantityForDate(InventoryStockTypeConstant.STOCK_TYPE_CLOSING, format.format(transactionDate), valueObject.getInt("locationId", 0), valueObject.getLong("partId", 0));
			preClosingBalance	= dayWiseInventoryStockServiceDao.getOpeningClosingQuantityForPreviousDate(InventoryStockTypeConstant.STOCK_TYPE_CLOSING, format.format(transactionDate), valueObject.getInt("locationId", 0), valueObject.getLong("partId", 0));
			
			if(preClosingBalance != null && preDetails == null) {
				quantity = quantity + preClosingBalance.getAddedQuantity();
			}
			
			if(preDetails == null) {
				preDetails = entiresBL.getDayWiseInventoryStockDTO(valueObject);
				preDetails.setAddedQuantity(quantity);
				dayWiseInventoryStockRepository.save(preDetails);
			
			}else {
				
				quantity	= quantity + preDetails.getAddedQuantity();
				
				dayWiseInventoryStockServiceDao.updateClosingQuantity(quantity, format.format(transactionDate), valueObject.getInt("locationId", 0), valueObject.getLong("partId", 0));
			}
			dayWiseInventoryStockServiceDao.updateClosingQuantityForNextDates(valueObject.getDouble("quantity", 0), format.format(transactionDate), valueObject.getInt("locationId", 0), valueObject.getLong("partId", 0));
			
			
			if(valueObject.getBoolean("isDateChanged", false)) {
				Date previousDate = DateTimeUtility.substractDayFromDate((Date) valueObject.get("previousDate"), 1);
				dayWiseInventoryStockServiceDao.updateClosingQuantityForNextDates(-valueObject.getDouble("previousQuantity", 0), format.format(previousDate),  valueObject.getInt("locationId", 0), valueObject.getLong("partId", 0));
			}
			
			
		} catch (Exception e) {
			LOGGER.error("Exeception ", e);
		}
		 
	}
	
	@Override
	public List<DayWiseInventoryStockDto> getDayWiseInventoryStockList(String query, Integer companyId)
			throws Exception {
		
		return dayWiseInventoryStockServiceDao.getDayWiseInventoryStockList(query, companyId);
	}

}
