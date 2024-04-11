package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.VehicleAgentConstant;
import org.fleetopgroup.persistence.bl.VehicleAgentTxnCheckerBL;
import org.fleetopgroup.persistence.dao.VehicleAgentIncomeExpenseDetailsRepository;
import org.fleetopgroup.persistence.dao.VehicleAgentTxnCheckerRepository;
import org.fleetopgroup.persistence.dto.VehicleAgentIncomeExpenseDetailsDto;
import org.fleetopgroup.persistence.model.VehicleAgentIncomeExpenseDetails;
import org.fleetopgroup.persistence.model.VehicleAgentPaymentDetails;
import org.fleetopgroup.persistence.model.VehicleAgentTxnChecker;
import org.fleetopgroup.persistence.report.dao.VehicleAgentIncomeExpenseDetailsDao;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentIncomeExpenseDetailsService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleAgentPaymentDetailsService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VehicleAgentIncomeExpenseDetailsService implements IVehicleAgentIncomeExpenseDetailsService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext
	public EntityManager entityManager;

	@Autowired private VehicleAgentTxnCheckerRepository				vehicleAgentTxnCheckerRepository;
	@Autowired private VehicleAgentIncomeExpenseDetailsRepository	vehicleAgentIncomeExpenseDetailsRepository;
	@Autowired private VehicleAgentIncomeExpenseDetailsDao			vehicleAgentIncomeExpenseDetailsDao;
	@Autowired private IVehicleAgentPaymentDetailsService			vehicleAgentPaymentDetailsService;
	
	VehicleAgentTxnCheckerBL	agentTxnCheckerBL			= new VehicleAgentTxnCheckerBL();
	
	SimpleDateFormat	format		= new SimpleDateFormat(DateTimeUtility.YYYY_MM_DD);
	SimpleDateFormat	dateFormat	= new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
	
	
	@Override
	public void startThreadForVehicleAgentIncomeExpense(ValueObject valueObject) throws Exception {
		try {
			new Thread() {
				public void run() {  
					try {
						processVehicleAgentIncomeExpense(valueObject);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Override
	@Transactional
	public void processVehicleAgentIncomeExpense(ValueObject valueObject) throws Exception {
		VehicleAgentTxnChecker				vehicleAgentTxnChecker				= null;
		VehicleAgentIncomeExpenseDetails	vehicleAgentIncomeExpenseDetails	= null;
		try {
			vehicleAgentTxnChecker	= vehicleAgentTxnCheckerRepository.getVehicleAgentTxnChecker(valueObject.getLong(VehicleAgentConstant.TRANSACTION_CHECKER_ID));
			if(vehicleAgentTxnChecker != null && !vehicleAgentTxnChecker.isIncomeExpenseAdded()) {
				
				vehicleAgentIncomeExpenseDetails	=	agentTxnCheckerBL.getVehicleAgentIncomeExpenseDetailsDTO(valueObject);
				
					VehicleAgentIncomeExpenseDetails	validate	=	vehicleAgentIncomeExpenseDetailsRepository
					.getVehicleAgentIncomeExpenseDetails(vehicleAgentIncomeExpenseDetails.getTransactionId(), vehicleAgentIncomeExpenseDetails.getTxnTypeId(), vehicleAgentIncomeExpenseDetails.getIdentifier());
					
					if(validate != null) {
						vehicleAgentIncomeExpenseDetails.setAgentIncomeExpenseDetailsId(validate.getAgentIncomeExpenseDetailsId());
					}
				vehicleAgentIncomeExpenseDetailsRepository.save(vehicleAgentIncomeExpenseDetails);
			}
			valueObject.put("vehicleAgentIncomeExpenseDetails", vehicleAgentIncomeExpenseDetails);
			
			vehicleAgentTxnCheckerRepository.updateVehicleAgentTxnChecker(valueObject.getLong(VehicleAgentConstant.TRANSACTION_CHECKER_ID));
			
			insertOpeningClosingBalance(valueObject);
		} catch (Exception e) {
			LOGGER.error("Exeception ", e);
			System.err.println("inside exception "+e);
			throw e;
		}finally {
			vehicleAgentTxnChecker				= null;
			vehicleAgentIncomeExpenseDetails	= null;
		}
		
	}
	@Transactional
	public synchronized void insertOpeningClosingBalance(ValueObject	valueObject)throws Exception{
		Date	transactionDate	= null;
		VehicleAgentIncomeExpenseDetails	preDetails				= null;
		VehicleAgentIncomeExpenseDetails	preClosingBalance		= null;
		Double		creditAmount	= 0.0;
		try {
			
			transactionDate	= (Date) valueObject.get(VehicleAgentConstant.TRANSACTION_DATE);
			creditAmount	= valueObject.getDouble(VehicleAgentConstant.CLOSING_AMOUNT, 0);
					
			preDetails			= getOpeningClosingBalanceForDate(VehicleAgentConstant.TXN_TYPE_CLOSING_ENTRY, format.format(transactionDate), valueObject.getInt(VehicleAgentConstant.VEHICLE_ID, 0));
			preClosingBalance	= getOpeningClosingBalanceForPreviousDate(VehicleAgentConstant.TXN_TYPE_CLOSING_ENTRY, format.format(transactionDate), valueObject.getInt(VehicleAgentConstant.VEHICLE_ID, 0));
			
			if(preClosingBalance != null && preDetails == null) {
				creditAmount = creditAmount + preClosingBalance.getCreditAmount();
			}
			
			if(preDetails == null) {
				preDetails = agentTxnCheckerBL.getClosingBalanceDTO(valueObject);
				preDetails.setCreditAmount(creditAmount);
				vehicleAgentIncomeExpenseDetailsRepository.save(preDetails);
			
			}else {
				creditAmount	= creditAmount + preDetails.getCreditAmount();
				vehicleAgentIncomeExpenseDetailsDao.updateClosingBalanceAmount(creditAmount, format.format(transactionDate), valueObject.getInt(VehicleAgentConstant.VEHICLE_ID, 0));
			}
			vehicleAgentIncomeExpenseDetailsDao.updateClosingBalanceAmountForNextDates(valueObject.getDouble(VehicleAgentConstant.CLOSING_AMOUNT, 0), format.format(transactionDate), valueObject.getInt(VehicleAgentConstant.VEHICLE_ID, 0));
			
			if(valueObject.getBoolean("isDateChanged", false)) {
				Date previousDate = DateTimeUtility.substractDayFromDate((Date) valueObject.get("previousDate"), 1);
				vehicleAgentIncomeExpenseDetailsDao.updateClosingBalanceAmountForNextDates(-valueObject.getDouble("previousAmount", 0), format.format(previousDate), valueObject.getInt(VehicleAgentConstant.VEHICLE_ID, 0));
			}
			
		} catch (Exception e) {
			LOGGER.error("Exeception ", e);
		}
	} 
	
	@Transactional
	public VehicleAgentIncomeExpenseDetails getOpeningClosingBalanceForDate(short txnTypeId, String txnDate, Integer vid)throws Exception{
		try {
			TypedQuery<VehicleAgentIncomeExpenseDetails> query = entityManager.createQuery(
					"SELECT VAE "
					+ " from VehicleAgentIncomeExpenseDetails VAE "
					+ " WHERE VAE.vid ="+vid+" AND VAE.txnTypeId = "+txnTypeId+" AND VAE.transactionDateTime = '"+txnDate+"' AND VAE.markForDelete = 0", VehicleAgentIncomeExpenseDetails.class);

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
	
	@Transactional
	public VehicleAgentIncomeExpenseDetails getOpeningClosingBalanceForPreviousDate(short txnTypeId, String txnDate, Integer vid)throws Exception{
		try {
			TypedQuery<VehicleAgentIncomeExpenseDetails> query = entityManager.createQuery(
					"SELECT VAE "
					+ " from VehicleAgentIncomeExpenseDetails VAE "
					+ " WHERE VAE.vid ="+vid+" AND VAE.txnTypeId = "+txnTypeId+" AND VAE.transactionDateTime < '"+txnDate+"' AND VAE.markForDelete = 0 order by VAE.transactionDateTime DESC ", VehicleAgentIncomeExpenseDetails.class);

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
	
	@Transactional
	public void updateClosingBalanceAmount(Double amount , String txnDate, Integer vid) throws Exception{
		  entityManager.createQuery(
			        "UPDATE VehicleAgentIncomeExpenseDetails SET creditAmount = "+amount+" " 
			        +" where transactionDateTime = '" + txnDate + "' AND vid=" + vid +" AND txnTypeId = "+VehicleAgentConstant.TXN_TYPE_CLOSING_ENTRY+" AND markForDelete=0")
			        .executeUpdate();
	}
	
	@Transactional
	public void updateClosingBalanceAmountForNextDates(Double amount , String txnDate, Integer vid) throws Exception{
		entityManager.createQuery(
			        "UPDATE VehicleAgentIncomeExpenseDetails SET creditAmount = creditAmount + "+amount+" " 
			        +" where transactionDateTime > '" + txnDate + "' AND vid=" + vid +" AND txnTypeId = "+VehicleAgentConstant.TXN_TYPE_CLOSING_ENTRY+" AND markForDelete = 0")
			        .executeUpdate();
	}
	
	
	@Override
	@Transactional
	public void deleteVehicleAgentIncomeExpense(ValueObject valueObject) throws Exception {
		short txnTypeId		 = 0;
		short identifier 	 = 0;
		Date transactionDate = null;
		try {
			
			txnTypeId		= valueObject.getShort(VehicleAgentConstant.TRANSACTION_TYPE);
			identifier		= valueObject.getShort(VehicleAgentConstant.IDENTIFIER);
			transactionDate	= DateTimeUtility.substractDayFromDate((Date) valueObject.get(VehicleAgentConstant.TRANSACTION_DATE), 1);
			VehicleAgentIncomeExpenseDetails	validate =	vehicleAgentIncomeExpenseDetailsRepository.
					getVehicleAgentIncomeExpenseDetails(valueObject.getLong(VehicleAgentConstant.TRANSACTION_ID), txnTypeId, identifier);
			
			if(validate != null) {
				vehicleAgentIncomeExpenseDetailsRepository.deleteIncomeExpenseDetails
				(valueObject.getLong(VehicleAgentConstant.TRANSACTION_ID), txnTypeId, identifier);
				
				updateClosingBalanceAmountForNextDates(valueObject.getDouble(VehicleAgentConstant.CLOSING_AMOUNT, 0), format.format(transactionDate), valueObject.getInt(VehicleAgentConstant.VEHICLE_ID, 0));
			}
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void processVehicleAgentIncomeExpense(HashMap<Long, ValueObject> ownerShipObjectHM) throws Exception {
		try {
			new Thread() {
				public void run() {  
					try {
						for (Long key : ownerShipObjectHM.keySet()) {
							processVehicleAgentIncomeExpense(ownerShipObjectHM.get(key));
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.start();
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public ValueObject getVehicleAgentPaymentDetails(ValueObject valueObject) throws Exception {
		List<VehicleAgentIncomeExpenseDetailsDto> 	paymentList					= null;
		ValueObject									dateRangeObj				= null;
		String										fromDate					= null;
		String										toDate						= null;
		Double										openingBalance				= 0.0;
		Date										openingDate					= null;
		Double										closingBalance				= 0.0;
		VehicleAgentPaymentDetails					vehicleAgentPaymentDetails	= null;
		try {
				dateRangeObj	= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
				fromDate		= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
				toDate			= dateRangeObj.getString(DateTimeUtility.TO_DATE);
				paymentList		= vehicleAgentIncomeExpenseDetailsDao.getVehicleAgentPendingPayment(valueObject.getInt("vid",0), fromDate, toDate);
				paymentList		= getFinalListForUI(paymentList);
				openingDate		= DateTimeUtility.substractDayFromDate(DateTimeUtility.getDateFromString(fromDate, DateTimeUtility.YYYY_MM_DD), 1);
				openingBalance	= vehicleAgentIncomeExpenseDetailsDao.getVehicleAgentOpeningClosingBalanceByDate(valueObject.getInt("vid",0), format.format(openingDate));
				closingBalance	= vehicleAgentIncomeExpenseDetailsDao.getVehicleAgentOpeningClosingBalanceByDate(valueObject.getInt("vid",0), toDate);
				
				vehicleAgentPaymentDetails	= vehicleAgentPaymentDetailsService.validatePaymentAfterDate(toDate, valueObject.getInt("vid",0));
				
				if(vehicleAgentPaymentDetails == null) {
					valueObject.put("isPaymentDone", false);
				}else {
					valueObject.put("isPaymentDone", true);
				}
				valueObject.put("openingBalance", openingBalance);
				valueObject.put("closingBalance", closingBalance);
				valueObject.put("paymentList", paymentList);valueObject.put("paymentList", paymentList);
				valueObject.put("fromDate", fromDate);
				valueObject.put("toDate", toDate);
				
				return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			paymentList					= null;
			dateRangeObj				= null;
			fromDate					= null;
			toDate						= null;
			openingBalance				= 0.0;
			openingDate					= null;
			closingBalance				= 0.0;
			vehicleAgentPaymentDetails	= null;
		}
	}	
	
	private List<VehicleAgentIncomeExpenseDetailsDto> getFinalListForUI(List<VehicleAgentIncomeExpenseDetailsDto> paymentList)throws Exception{
		List<VehicleAgentIncomeExpenseDetailsDto> 	finalList			= null;
		VehicleAgentIncomeExpenseDetailsDto			expenseDetailsDto	= null;
		try {
				if(paymentList != null && paymentList.size() > 0) {
					finalList	= new ArrayList<>();
					for (VehicleAgentIncomeExpenseDetailsDto detailsDto : paymentList) {
						expenseDetailsDto	= detailsDto;
						
						expenseDetailsDto.setTxnTypeName(VehicleAgentConstant.getTxnTypeName(expenseDetailsDto.getTxnTypeId()));
						expenseDetailsDto.setIdentifierName(VehicleAgentConstant.getTxnIdentifierName(expenseDetailsDto.getIdentifier()));
						expenseDetailsDto.setTransactionDateStr(dateFormat.format(expenseDetailsDto.getTransactionDateTime()));
						
						finalList.add(expenseDetailsDto);
					}
				}
			return finalList;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Double getVehicleAgentClosingBalanceByDate(Integer vid, String txnDate) throws Exception {
		try {
			return vehicleAgentIncomeExpenseDetailsDao.getVehicleAgentOpeningClosingBalanceByDate(vid, txnDate);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void updatePaymentDone(Integer vid, String date) throws Exception {
		vehicleAgentIncomeExpenseDetailsDao.updatePaymentDone(vid, date);
	}
}
