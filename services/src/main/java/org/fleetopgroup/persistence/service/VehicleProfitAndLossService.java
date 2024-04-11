package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.constant.VehicleExpenseTypeConstant;
import org.fleetopgroup.persistence.bl.DataCreatorForVehicleProfitAndLoss;
import org.fleetopgroup.persistence.bl.FuelEntryTxnCheckerBL;
import org.fleetopgroup.persistence.dao.DateWiseVehicleBalanceRepository;
import org.fleetopgroup.persistence.dao.DateWiseVehicleExpenseRepository;
import org.fleetopgroup.persistence.dao.MonthWiseVehicleBalanceRepository;
import org.fleetopgroup.persistence.dao.MonthWiseVehicleExpenseRepository;
import org.fleetopgroup.persistence.dao.MonthWiseVehicleIncomeRepository;
import org.fleetopgroup.persistence.dao.MothWiseVehicleIncomeBalanceRepository;
import org.fleetopgroup.persistence.dao.VehicleExpenseDetailsRepository;
import org.fleetopgroup.persistence.dao.VehicleIncomeDetailsRepository;
import org.fleetopgroup.persistence.dao.VehicleProfitAndLossIncomeTxnCheckerRepository;
import org.fleetopgroup.persistence.dao.VehicleProfitAndLossTxnCheckerRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DateWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.DriverAttendanceDto;
import org.fleetopgroup.persistence.dto.MonthWiseVehicleExpenseDto;
import org.fleetopgroup.persistence.dto.TripDailyExpenseDto;
import org.fleetopgroup.persistence.dto.TripDailyIncomeDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.dto.TripSheetIncomeDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossDto;
import org.fleetopgroup.persistence.dto.VehicleProfitAndLossReportDto;
import org.fleetopgroup.persistence.model.DateWiseVehicleBalance;
import org.fleetopgroup.persistence.model.DateWiseVehicleExpense;
import org.fleetopgroup.persistence.model.MonthWiseVehicleBalance;
import org.fleetopgroup.persistence.model.MonthWiseVehicleExpense;
import org.fleetopgroup.persistence.model.MonthWiseVehicleIncome;
import org.fleetopgroup.persistence.model.MothWiseVehicleIncomeBalance;
import org.fleetopgroup.persistence.model.TripSheet;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.fleetopgroup.persistence.model.VehicleEmiPaymentDetails;
import org.fleetopgroup.persistence.model.VehicleExpenseDetails;
import org.fleetopgroup.persistence.model.VehicleIncomeDetails;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossIncomeTxnChecker;
import org.fleetopgroup.persistence.model.VehicleProfitAndLossTxnChecker;
import org.fleetopgroup.persistence.report.dao.DateWiseVehicleBalanceDao;
import org.fleetopgroup.persistence.report.dao.DateWiseVehicleExpenseDetailsDao;
import org.fleetopgroup.persistence.report.dao.MonthWiseVehicleBalanceDao;
import org.fleetopgroup.persistence.report.dao.MonthWiseVehicleExpenseDao;
import org.fleetopgroup.persistence.report.dao.MonthWiseVehicleIncomeDao;
import org.fleetopgroup.persistence.report.dao.MothWiseVehicleIncomeBalanceDao;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverAttendanceService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleProfitAndLossService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.antkorwin.xsync.XMutexFactoryImpl;

@Service
public class VehicleProfitAndLossService implements IVehicleProfitAndLossService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@PersistenceContext	EntityManager entityManager;
	
	@Autowired	private	VehicleExpenseDetailsRepository					vehicleExpenseDetailsRepository;
	@Autowired	private DateWiseVehicleExpenseRepository				dateWiseVehicleExpenseRepository;
	@Autowired	private MonthWiseVehicleExpenseRepository				monthWiseVehicleExpenseRepository;
	@Autowired	private DateWiseVehicleBalanceRepository				dateWiseVehicleBalanceRepository;
	@Autowired	private MonthWiseVehicleBalanceRepository				monthWiseVehicleBalanceRepository;
	@Autowired	private DateWiseVehicleBalanceDao						dateWiseVehicleBalanceDao;
	@Autowired	private	MonthWiseVehicleBalanceDao						monthWiseVehicleBalanceDao;
	@Autowired	private DateWiseVehicleExpenseDetailsDao				dateWiseVehicleExpenseDetailsDao;
	@Autowired	private MonthWiseVehicleExpenseDao						monthWiseVehicleExpenseDao;
	@Autowired	private VehicleProfitAndLossTxnCheckerRepository		vehicleProfitAndLossTxnCheckerRepository;
	@Autowired	private VehicleProfitAndLossIncomeTxnCheckerRepository	vehicleProfitAndLossIncomeTxnCheckerRepository;
	@Autowired	private	VehicleIncomeDetailsRepository					vehicleIncomeDetailsRepository;
	@Autowired	private MonthWiseVehicleIncomeRepository				monthWiseVehicleIncomeRepository;
	@Autowired	private MothWiseVehicleIncomeBalanceRepository			mothWiseVehicleIncomeBalanceRepository;
	@Autowired	private MothWiseVehicleIncomeBalanceDao					mothWiseVehicleIncomeBalanceDao;
	@Autowired	private MonthWiseVehicleIncomeDao						monthWiseVehicleIncomeDao;
	@Autowired	private ICompanyConfigurationService					companyConfigurationService;
	@Autowired	private	ITripDailySheetService							tripDailySheetService;
	@Autowired	private	ITripSheetService								tripSheetService;
	@Autowired	private IDriverAttendanceService						driverAttendanceService;
	
	XMutexFactoryImpl<Integer> xMutexFactory = new XMutexFactoryImpl<Integer>();
	
	FuelEntryTxnCheckerBL	txnCheckerBL	= new FuelEntryTxnCheckerBL();		
	
	
	public  void processVehicleExpenseDetails(VehicleProfitAndLossDto	profitAndLossDto) throws Exception {
		VehicleProfitAndLossTxnChecker		profitAndLossTxnChecker	= null;
		try {
			Thread.sleep(2000);
			synchronized (xMutexFactory.getMutex(profitAndLossDto.getVid())) {
				
				profitAndLossTxnChecker	= vehicleProfitAndLossTxnCheckerRepository.getVehicleProfitAndLossTxnChecker(profitAndLossDto.getTxnCheckerId());
				
				if(profitAndLossTxnChecker != null && profitAndLossTxnChecker.isFinallyEntered()) {
					return;
				}
				
				if(profitAndLossTxnChecker != null && !profitAndLossTxnChecker.isVehicleExpenseAdded()) {
					VehicleExpenseDetails	validate		= null;
					if(profitAndLossDto.getType() != VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						validate		= vehicleExpenseDetailsRepository.validateVehicleExpenseDetails(Integer.parseInt(profitAndLossDto.getExpenseId()+""), profitAndLossDto.getCompanyId(), profitAndLossDto.getVid(), profitAndLossDto.getType());
					}
					
					VehicleExpenseDetails	expenseDetails	= txnCheckerBL.createDataForVehicleExpenseDetails(profitAndLossDto);
					if(validate != null) {
						expenseDetails.setVehicleExpenseDetailsId(validate.getVehicleExpenseDetailsId());
					}
					vehicleExpenseDetailsRepository.save(expenseDetails);
					
					if(expenseDetails.getVehicleExpenseDetailsId() != null)
						vehicleProfitAndLossTxnCheckerRepository.updateVehicleExpenseAdded(profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
				}
				
				xMutexFactory.purgeUnreferenced();
			}
				
		} catch (Exception e) {
			logger.error("Exception ",e);
		} finally {
		
		}
		
	}
	public void processVehicleExpenseDetailsForTripSheet(VehicleProfitAndLossDto	profitAndLossDto) throws Exception {
		VehicleProfitAndLossTxnChecker		profitAndLossTxnChecker	= null;
		try {
			
			profitAndLossTxnChecker	= vehicleProfitAndLossTxnCheckerRepository.getVehicleProfitAndLossTxnChecker(profitAndLossDto.getTxnCheckerId());
			
			if(profitAndLossTxnChecker != null && profitAndLossTxnChecker.isFinallyEntered()) {
				return;
			}
			
			if(profitAndLossTxnChecker != null && !profitAndLossTxnChecker.isVehicleExpenseAdded()) {
				VehicleExpenseDetails	validate		=  null;
				if(profitAndLossDto.getType() != VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
					validate		= vehicleExpenseDetailsRepository.validateVehicleExpenseDetails(Integer.parseInt(profitAndLossDto.getTxnTypeId()+""), profitAndLossDto.getCompanyId(), profitAndLossDto.getVid(), profitAndLossDto.getType());
				}
				
				VehicleExpenseDetails	expenseDetails	= txnCheckerBL.createDataForVehicleExpenseDetails(profitAndLossDto);
				if(validate != null) {
					expenseDetails.setVehicleExpenseDetailsId(validate.getVehicleExpenseDetailsId());
				}
				vehicleExpenseDetailsRepository.save(expenseDetails);
				
				if(expenseDetails.getVehicleExpenseDetailsId() != null)
					vehicleProfitAndLossTxnCheckerRepository.updateVehicleExpenseAdded(profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
			}
			
				
				
		} catch (Exception e) {
			logger.error("Exception ",e);
		} finally {
			profitAndLossTxnChecker	= null;
		}
		
	}
	
	public  void processDateWiseVehicleExpense(VehicleProfitAndLossDto		vehicleProfitAndLossDto) throws Exception {

		VehicleProfitAndLossTxnChecker		profitAndLossTxnChecker	= null;
		try {
			Thread.sleep(2000);
			synchronized (xMutexFactory.getMutex(vehicleProfitAndLossDto.getVid())) {
				
				profitAndLossTxnChecker	= vehicleProfitAndLossTxnCheckerRepository.getVehicleProfitAndLossTxnChecker(vehicleProfitAndLossDto.getTxnCheckerId());
				
				if(profitAndLossTxnChecker != null && profitAndLossTxnChecker.isFinallyEntered()) {
					return;
				}
				
				if(profitAndLossTxnChecker != null && !profitAndLossTxnChecker.isDateWiseVehicleExpenseAdded()) {
					DateWiseVehicleExpense	validate		=	null;
					if(vehicleProfitAndLossDto.getType() != VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						validate		= dateWiseVehicleExpenseDetailsDao.validateDateWiseVehicleExpense(vehicleProfitAndLossDto.getType(), vehicleProfitAndLossDto.getCompanyId(), vehicleProfitAndLossDto.getVid(), DateTimeUtility.getDateFromTimeStamp(vehicleProfitAndLossDto.getTransactionDateTime(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
					}else {
						validate		= dateWiseVehicleExpenseDetailsDao.validateDateWiseVehicleExpense(Integer.parseInt(vehicleProfitAndLossDto.getExpenseId()+""), vehicleProfitAndLossDto.getCompanyId(), vehicleProfitAndLossDto.getVid(), DateTimeUtility.getDateFromTimeStamp(vehicleProfitAndLossDto.getTransactionDateTime(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), vehicleProfitAndLossDto.getType());
					}
					DateWiseVehicleExpense	expenseDetails	= txnCheckerBL.createDateWiseVehicleExpense(vehicleProfitAndLossDto, validate);
					
					if(validate != null) {
						expenseDetails.setDateWiseVehicleExpenseId(validate.getDateWiseVehicleExpenseId());
					}
					
					
					dateWiseVehicleExpenseRepository.save(expenseDetails);
					
					if(vehicleProfitAndLossDto.isDateChanged() && !vehicleProfitAndLossDto.getPreviousDate().equals(vehicleProfitAndLossDto.getTransactionDateTime())) {
						DateWiseVehicleExpense	previousDateDetails =	null;
						if(vehicleProfitAndLossDto.getType() != VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
							previousDateDetails		= dateWiseVehicleExpenseDetailsDao.validateDateWiseVehicleExpense(vehicleProfitAndLossDto.getType(), vehicleProfitAndLossDto.getCompanyId(), vehicleProfitAndLossDto.getVid(), DateTimeUtility.getDateFromTimeStamp(vehicleProfitAndLossDto.getPreviousDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
						}else {
							previousDateDetails		= dateWiseVehicleExpenseDetailsDao.validateDateWiseVehicleExpense(Integer.parseInt(vehicleProfitAndLossDto.getExpenseId()+""), vehicleProfitAndLossDto.getCompanyId(), vehicleProfitAndLossDto.getVid(), DateTimeUtility.getDateFromTimeStamp(vehicleProfitAndLossDto.getPreviousDate(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), vehicleProfitAndLossDto.getType());
						}
						
						if(previousDateDetails != null) {
							dateWiseVehicleExpenseDetailsDao.subtractVehicleExpenseFromPreviousDate(previousDateDetails, vehicleProfitAndLossDto.getPreviousAmount());
						}
					}
					if(expenseDetails.getDateWiseVehicleExpenseId() != null)
						vehicleProfitAndLossTxnCheckerRepository.updateDateWiseVehicleExpenseAdded(profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
				}	
			}
					
				
		} catch (Exception e) {
			logger.error("Exception ",e);
		} 
		
	}
	
	public void processMonthWiseVehicleExpense(VehicleProfitAndLossDto		vehicleProfitAndLossDto) {
		Timestamp							previousDateMonthFirstDate	= null;
		VehicleProfitAndLossTxnChecker		profitAndLossTxnChecker		= null;
		try {
			Thread.sleep(2000);
			synchronized (xMutexFactory.getMutex(vehicleProfitAndLossDto.getVid())) {
				Long txnCheckerId = vehicleProfitAndLossDto.getTxnCheckerId();
				//here is the problem when thread is run
				profitAndLossTxnChecker	= mothWiseVehicleIncomeBalanceDao.getVehicleProfitAndLossTxnChecker(txnCheckerId);
				if(profitAndLossTxnChecker != null && profitAndLossTxnChecker.isFinallyEntered()) {
					return;
				}
				if(profitAndLossTxnChecker != null && !profitAndLossTxnChecker.isMonthWiseVehicleExpenseAdded()) {
					if(vehicleProfitAndLossDto.getPreviousDate() != null)
						previousDateMonthFirstDate	= DateTimeUtility.getFirstDayOfMonth(vehicleProfitAndLossDto.getPreviousDate());
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(DateTimeUtility.geTimeStampFromDate(vehicleProfitAndLossDto.getTransactionDateTime()));
					
					MonthWiseVehicleExpense validate		=	null;
					
					if(vehicleProfitAndLossDto.getType() != VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						validate		= monthWiseVehicleExpenseDao.validateMonthWiseVehicleExpense(vehicleProfitAndLossDto.getVid(), DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getFirstDayOfMonth(vehicleProfitAndLossDto.getTransactionDateTime()), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), vehicleProfitAndLossDto.getCompanyId(), vehicleProfitAndLossDto.getType());
					}else {
						validate		= monthWiseVehicleExpenseDao.validateMonthWiseVehicleExpense(vehicleProfitAndLossDto.getVid(), DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getFirstDayOfMonth(vehicleProfitAndLossDto.getTransactionDateTime()), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), vehicleProfitAndLossDto.getCompanyId(), Integer.parseInt( vehicleProfitAndLossDto.getExpenseId()+""), vehicleProfitAndLossDto.getType());
					}
					MonthWiseVehicleExpense	expenseDetails	= txnCheckerBL.createMonthWiseVehicleExpense(vehicleProfitAndLossDto, cal, validate);
					
					if(validate != null) {
						expenseDetails.setMonthWiseVehicleExpenseId(validate.getMonthWiseVehicleExpenseId());
					}
					monthWiseVehicleExpenseRepository.save(expenseDetails);
					
					if(vehicleProfitAndLossDto.isDateChanged() && !previousDateMonthFirstDate.equals(DateTimeUtility.getFirstDayOfMonth(vehicleProfitAndLossDto.getTransactionDateTime()))) {
						MonthWiseVehicleExpense preDetails		= 	null;
						
						if(vehicleProfitAndLossDto.getType() != VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
							preDetails		= monthWiseVehicleExpenseDao.getMonthWiseVehicleExpense(vehicleProfitAndLossDto.getVid(), DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getFirstDayOfMonth(vehicleProfitAndLossDto.getPreviousDate()), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), vehicleProfitAndLossDto.getCompanyId(), vehicleProfitAndLossDto.getType());
						}else {
							preDetails		= monthWiseVehicleExpenseDao.getMonthWiseVehicleExpense(vehicleProfitAndLossDto.getVid(), DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getFirstDayOfMonth(vehicleProfitAndLossDto.getPreviousDate()), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), vehicleProfitAndLossDto.getCompanyId(), Integer.parseInt( vehicleProfitAndLossDto.getExpenseId()+""), vehicleProfitAndLossDto.getType());
						}
						if(preDetails != null)
							monthWiseVehicleExpenseDao.subtractPreviousMonthAmount(preDetails, DateTimeUtility.getFirstDayOfMonth(vehicleProfitAndLossDto.getPreviousDate()), vehicleProfitAndLossDto.getPreviousAmount());
					}
					
					vehicleProfitAndLossTxnCheckerRepository.updateMonthWiseVehicleExpenseAdded(vehicleProfitAndLossDto.getTxnCheckerId());
				}
			}
			
			
		} catch (Exception e) {
			logger.error("Exception ",e);
		} 
		
	}
	
	
	public void procesDateWiseVehicleBalance(VehicleProfitAndLossDto		profitAndLossDto) throws Exception {
		
		VehicleProfitAndLossTxnChecker		profitAndLossTxnChecker	= null;
		try {
			synchronized (xMutexFactory.getMutex(profitAndLossDto.getVid())) {
				profitAndLossTxnChecker	= vehicleProfitAndLossTxnCheckerRepository.getVehicleProfitAndLossTxnChecker(profitAndLossDto.getTxnCheckerId());
				
				if(profitAndLossTxnChecker != null && profitAndLossTxnChecker.isFinallyEntered()) {
					return;
				}
				
				if(profitAndLossTxnChecker != null && !profitAndLossTxnChecker.isDateWiseVehicleBalanceUpdated()) {
					
					DateWiseVehicleBalance	validate		= dateWiseVehicleBalanceDao.validateDateWiseVehicleBalance(profitAndLossDto.getVid(), profitAndLossDto.getCompanyId(), DateTimeUtility.getDateFromTimeStamp(profitAndLossDto.getTransactionDateTime(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
					DateWiseVehicleBalance	lastDayBalance	= dateWiseVehicleBalanceDao.getLastDateVehicleBalance(profitAndLossDto.getVid(), profitAndLossDto.getCompanyId(), DateTimeUtility.getDateFromTimeStamp(profitAndLossDto.getTransactionDateTime(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS));
					
					DateWiseVehicleBalance	expenseDetails	= txnCheckerBL.createDateWiseVehicleBalance(profitAndLossDto, validate, lastDayBalance);
					
					if(validate != null) {
						expenseDetails.setDateWiseVehicleBalanceId(validate.getDateWiseVehicleBalanceId());
					}
					dateWiseVehicleBalanceRepository.save(expenseDetails);
					
					if(profitAndLossDto.getTransactionDateTime().before(DateTimeUtility.geTimeStampFromDate(new Date())) && !profitAndLossDto.isEdit()) {
						ValueObject	object	= new ValueObject();
						object.put("dateWiseVehicleBalance", expenseDetails);
						object.put("amount", profitAndLossDto.getTxnAmount());
						dateWiseVehicleBalanceDao.updateNextDateAllDayBalanceAmount(object);
					}
					if(profitAndLossDto.isEdit()) {
						if(profitAndLossDto.getTransactionDateTime().before(profitAndLossDto.getPreviousDate())) {
							
							dateWiseVehicleBalanceDao.subtractAddAmountForPreviousFuelDate(expenseDetails, profitAndLossDto.getPreviousAmount(), false, profitAndLossDto.getPreviousDate());
							
							dateWiseVehicleBalanceDao.updateAllDayBalanceForBackDateEdit(expenseDetails, profitAndLossDto.getTxnAmount(), profitAndLossDto.getPreviousDate());
							
							ValueObject	object	= new ValueObject();
							object.put("dateWiseVehicleBalance", expenseDetails);
							object.put("previousFuelDate", profitAndLossDto.getPreviousDate());
							object.put("amount", profitAndLossDto.getAmountToBeAdded());
							dateWiseVehicleBalanceDao.updateCurrentNextDateAllDayBalanceAmount(object);
							
							
						}else if(profitAndLossDto.getTransactionDateTime().after(profitAndLossDto.getPreviousDate())) {
							
							dateWiseVehicleBalanceDao.subtractAddAmountForPreviousFuelDate(expenseDetails, profitAndLossDto.getPreviousAmount(), false, profitAndLossDto.getPreviousDate());
							
							dateWiseVehicleBalanceDao.updateAllDayBalanceForNextDateEdit(expenseDetails, profitAndLossDto.getPreviousAmount(), profitAndLossDto.getPreviousDate(), profitAndLossDto.getTransactionDateTime());
						}
					}else {
						ValueObject	object	= new ValueObject();
						object.put("dateWiseVehicleBalance", expenseDetails);
						object.put("amount", profitAndLossDto.getAmountToBeAdded());
						dateWiseVehicleBalanceDao.updateNextDateAllDayBalanceAmount(object);
					}
					
					vehicleProfitAndLossTxnCheckerRepository.updateDateWiseVehicleBalanceUpdated(profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
				}
			}
				
		} catch (Exception e) {
			logger.error("Exception ",e);
		} 
		
	}
	
	
	public void processMonthWiseVehicleBalance(VehicleProfitAndLossDto	profitAndLossDto) throws Exception {

		Timestamp				previousDateMonthFirstDate	= null;
		VehicleProfitAndLossTxnChecker		profitAndLossTxnChecker	= null;
		try {
			synchronized (xMutexFactory.getMutex(profitAndLossDto.getVid())) {
				profitAndLossTxnChecker	= vehicleProfitAndLossTxnCheckerRepository.getVehicleProfitAndLossTxnChecker(profitAndLossDto.getTxnCheckerId());
				
				if(profitAndLossTxnChecker != null && profitAndLossTxnChecker.isFinallyEntered()) {
					return;
				}
				
				if(profitAndLossTxnChecker != null && !profitAndLossTxnChecker.isMonthWiseVehicleBalanceUpdated()) {
					
					
					if(profitAndLossDto.getPreviousDate() != null)
						previousDateMonthFirstDate	= DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getPreviousDate());
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(DateTimeUtility.geTimeStampFromDate(profitAndLossDto.getTransactionDateTime()));
					
					
					MonthWiseVehicleBalance	validate		= monthWiseVehicleBalanceDao.validateMonthWiseVehicleBalance(profitAndLossDto.getVid(), DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getTransactionDateTime()), DateTimeUtility.YYYY_MM_DD_HH_MM_SS) , profitAndLossDto.getCompanyId());
					MonthWiseVehicleBalance	lastDayBalance	= monthWiseVehicleBalanceDao.getLastMonthWiseVehicleBalance(profitAndLossDto.getVid(), DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getTransactionDateTime()), DateTimeUtility.YYYY_MM_DD_HH_MM_SS) , profitAndLossDto.getCompanyId());
					
					
					MonthWiseVehicleBalance	expenseDetails	= txnCheckerBL.createMonthWiseVehicleBalance(profitAndLossDto, validate, lastDayBalance, cal, previousDateMonthFirstDate);
					if(validate != null) {
						expenseDetails.setMonthWiseVehicleBalanceId(validate.getMonthWiseVehicleBalanceId());
					}
					monthWiseVehicleBalanceRepository.save(expenseDetails);
					
					if(DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getTransactionDateTime()).before(DateTimeUtility.getFirstDayOfMonth(DateTimeUtility.geTimeStampFromDate(new Date()))) && !profitAndLossDto.isDateChanged()) {
						ValueObject	object	= new ValueObject();
						object.put("monthWiseVehicleBalance", expenseDetails);
						object.put("amount", profitAndLossDto.getTxnAmount());
						monthWiseVehicleBalanceDao.updateMonthBalance(object);
					}
					
					if(profitAndLossDto.isDateChanged()) {
						if(DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getTransactionDateTime()).before(previousDateMonthFirstDate)) {
							
							monthWiseVehicleBalanceDao.subtractAddAmountForPreviousFuelDate(expenseDetails, profitAndLossDto.getPreviousAmount(), false, previousDateMonthFirstDate);
							
							monthWiseVehicleBalanceDao.updateAllDayBalanceForBackDateEdit(expenseDetails, profitAndLossDto.getTxnAmount(), previousDateMonthFirstDate);
							
							ValueObject	object	= new ValueObject();
							object.put("monthWiseVehicleBalance", expenseDetails);
							object.put("previousFuelDate", previousDateMonthFirstDate);
							object.put("amount", profitAndLossDto.getAmountToBeAdded());
							monthWiseVehicleBalanceDao.updateCurrentNextDateAllDayBalanceAmount(object);
							
							
						}else if(DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getTransactionDateTime()).after(previousDateMonthFirstDate)) {
							
							monthWiseVehicleBalanceDao.subtractAddAmountForPreviousFuelDate(expenseDetails, profitAndLossDto.getPreviousAmount(), false, previousDateMonthFirstDate);
							
							monthWiseVehicleBalanceDao.updateAllDayBalanceForNextDateEdit(expenseDetails, profitAndLossDto.getPreviousAmount(), previousDateMonthFirstDate, DateTimeUtility.getFirstDayOfMonth(profitAndLossDto.getTransactionDateTime()));
						}
					}else {
						ValueObject	object	= new ValueObject();
						object.put("monthWiseVehicleBalance", expenseDetails);
						object.put("amount", profitAndLossDto.getAmountToBeAdded());
						monthWiseVehicleBalanceDao.updateNextDateAllDayBalanceAmount(object);
					}
					vehicleProfitAndLossTxnCheckerRepository.updateMonthWiseVehicleBalanceUpdated(profitAndLossDto.getTxnCheckerId());
				}
			}
		
		} catch (Exception e) {
			logger.error("Exception ",e);
		} 
		
	}


	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public void runThreadForVehicleExpenseDetails(ValueObject valueObject) throws Exception {
		try {			
			
						VehicleProfitAndLossDto		vehicleProfitAndLossDto	=	null;
						
						if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL) {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForFuel(valueObject);
							processVehicleExpenseDetails(vehicleProfitAndLossDto);
						}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForServiceEntries(valueObject);
							processVehicleExpenseDetails(vehicleProfitAndLossDto);
						}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER) {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForWorkOrder(valueObject);
							processVehicleExpenseDetails(vehicleProfitAndLossDto);
						}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForTripSheet(valueObject);
							processVehicleExpenseDetails(vehicleProfitAndLossDto);
						}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY) {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForDriverSalary(valueObject);
							processVehicleExpenseDetails(vehicleProfitAndLossDto);
						}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA) {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForUrea(valueObject);
							processVehicleExpenseDetails(vehicleProfitAndLossDto);
						}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES) {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForDealerServiceEntries(valueObject);
							processVehicleExpenseDetails(vehicleProfitAndLossDto);
						}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI) {
							if(valueObject.get("vehicleEmiDetails") != null) {
								vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForEMIDetails(valueObject);
								processVehicleExpenseDetails(vehicleProfitAndLossDto);
							} else {
								vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForEMIPaymentDetails(valueObject);
								processVehicleExpenseDetails(vehicleProfitAndLossDto);
							}
							
						}
						
		} catch (Exception e) {
			logger.error("Error : ", e);
		}
		
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public void runThreadForDateWiseVehicleExpenseDetails(ValueObject valueObject) throws Exception {
		
				VehicleProfitAndLossDto		vehicleProfitAndLossDto	=	null;
				try {
					if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForFuel(valueObject);
						processDateWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForServiceEntries(valueObject);
						processDateWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForWorkOrder(valueObject);
						processDateWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForTripSheet(valueObject);
						processDateWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForDriverSalary(valueObject);
						processDateWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForUrea(valueObject);
						processDateWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForDealerServiceEntries(valueObject);
						processDateWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI) {
						if(valueObject.get("vehicleEmiDetails") != null) {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForEMIDetails(valueObject);
							processDateWiseVehicleExpense(vehicleProfitAndLossDto);
						} else {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForEMIPaymentDetails(valueObject);
							processDateWiseVehicleExpense(vehicleProfitAndLossDto);
						}
					}
					
				} catch (Exception e) {
					logger.error("Error : ", e);
				}finally {
					vehicleProfitAndLossDto	=	null;
				}
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public void runThreadForMonthWiseVehicleExpenseDetails(ValueObject valueObject) throws Exception {
				VehicleProfitAndLossDto		vehicleProfitAndLossDto	=	null;
				try {
					if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForFuel(valueObject);
						processMonthWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForServiceEntries(valueObject);
						processMonthWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForWorkOrder(valueObject);
						processMonthWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForTripSheet(valueObject);
						processMonthWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForDriverSalary(valueObject);
						processMonthWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForUrea(valueObject);
						processMonthWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DEALER_SERVICE_ENTRIES) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForDealerServiceEntries(valueObject);
						processMonthWiseVehicleExpense(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI) {
						if(valueObject.get("vehicleEmiDetails") != null) {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForEMIDetails(valueObject);
							processMonthWiseVehicleExpense(vehicleProfitAndLossDto);
						} else {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForEMIPaymentDetails(valueObject);
							
							processMonthWiseVehicleExpense(vehicleProfitAndLossDto);
						}
					}
					
				} catch (Exception e) {
					logger.error("Error : ", e);
				}finally {
					vehicleProfitAndLossDto	=	null;
				}
	}
	
	@Override
	public void runThreadForDateWiseVehicleBalanceDetails(ValueObject valueObject) throws Exception {
		
				VehicleProfitAndLossDto		vehicleProfitAndLossDto	=	null;
				try {
					if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForFuel(valueObject);
						procesDateWiseVehicleBalance(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForServiceEntries(valueObject);
						procesDateWiseVehicleBalance(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForWorkOrder(valueObject);
						procesDateWiseVehicleBalance(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForTripSheet(valueObject);
						procesDateWiseVehicleBalance(vehicleProfitAndLossDto);
						dateWiseVehicleBalanceDao.updateDateWiseVehicleBalanceMultiple(valueObject.getString("txnIds"));
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForDriverSalary(valueObject);
						procesDateWiseVehicleBalance(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForUrea(valueObject);
						procesDateWiseVehicleBalance(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI) {
						if(valueObject.get("vehicleEmiDetails") != null) {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForEMIDetails(valueObject);
							procesDateWiseVehicleBalance(vehicleProfitAndLossDto);
						} else {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForEMIPaymentDetails(valueObject);
							procesDateWiseVehicleBalance(vehicleProfitAndLossDto);
						}
					}
					
					
				} catch (Exception e) {
					logger.error("Error : ", e);
				}finally {
					vehicleProfitAndLossDto	=	null;
				}
	}
	
	@Override
	public void runThreadForMonthWiseVehicleBalanceDetails(ValueObject valueObject) throws Exception {
		
				VehicleProfitAndLossDto		vehicleProfitAndLossDto	=	null;
				try {
					if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_FUEL) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForFuel(valueObject);
						processMonthWiseVehicleBalance(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_SERVICE_ENTRIES) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForServiceEntries(valueObject);
						processMonthWiseVehicleBalance(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_WORK_ORDER) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForWorkOrder(valueObject);
						processMonthWiseVehicleBalance(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForTripSheet(valueObject);
						processMonthWiseVehicleBalance(vehicleProfitAndLossDto);
						monthWiseVehicleBalanceDao.updateMonthWiseVehicleBalanceUpdated(valueObject.getString("txnIds"));
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_DRIVER_SALARY) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForDriverSalary(valueObject);
						processMonthWiseVehicleBalance(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_UREA) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForUrea(valueObject);
						processMonthWiseVehicleBalance(vehicleProfitAndLossDto);
					}else if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI) {
						if(valueObject.get("vehicleEmiDetails") != null) {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForEMIDetails(valueObject);
							processMonthWiseVehicleBalance(vehicleProfitAndLossDto);
						} else {
							vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForEMIPaymentDetails(valueObject);
							processMonthWiseVehicleBalance(vehicleProfitAndLossDto);
						}
					}
					
				} catch (Exception e) {
					logger.error("Error : ", e);
				}finally {
					vehicleProfitAndLossDto	=	null;
				}
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public void runThreadForDeleteVehicleExpenseDetails(ValueObject valueObject) throws Exception {
				Thread.sleep(2000);
				try {
					synchronized (xMutexFactory.getMutex(valueObject.getInt("vid",0))) {
						deleteVehicleExpenseDetails(valueObject);
					}
					
				} catch (Exception e) {
					logger.error("Error : ", e);
				}
			
		
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public void runThreadForDeleteDateWiseVehicleExpenseDetails(ValueObject valueObject) throws Exception {
		try {
			
			Thread.sleep(2000);
			synchronized (xMutexFactory.getMutex(valueObject.getInt("vid",0))) {
				dateWiseVehicleExpenseDetailsDao.deleteDateWiseVehicleExpense(valueObject);
			}
			
			
		} catch (Exception e) {
			logger.error("Error : ", e);
		}
	}
	
	
	public void deleteVehicleExpenseDetails(ValueObject		valueObject) throws Exception {
		Integer		vid				= 0;
		try {
			vid			= valueObject.getInt("vid", 0);
			if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) != VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
				vehicleExpenseDetailsRepository.deleteVehicleExpenseDetails(valueObject.getLong("txnTypeId", 0), vid , valueObject.getShort("EXPENSE_TYPE"));
			}else {
				vehicleExpenseDetailsRepository.deleteVehicleExpenseDetails(valueObject.getLong(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, 0), valueObject.getShort("EXPENSE_TYPE"));
			}
			
			
			
		} catch (Exception e) {
			logger.error("Error : ", e);
		}
	}
	
	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED)
	public void runThreadForDeleteMonthWiseVehicleExpenseDetails(ValueObject valueObject) throws Exception {
		Integer		vid						= 0;
		Timestamp	transactionDateTime		= null;
		Double		txnAmount				= 0.0;
		Integer		companyId				= 0;
		Short		txnType					= 0;
		Integer 	expenseId				= 0;
		try {
			
			Thread.sleep(2000);
			
			vid					= valueObject.getInt("vid", 0);
			transactionDateTime	= (Timestamp) valueObject.get("transactionDateTime");
			txnAmount			= valueObject.getDouble("txnAmount", 0);
			companyId			= valueObject.getInt("companyId");
			txnType				= valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE);
			expenseId			= valueObject.getInt("expenseId");
			
			MonthWiseVehicleExpense		monthWiseVehicleExpense	= new MonthWiseVehicleExpense();
			monthWiseVehicleExpense.setCompanyId(companyId);
			monthWiseVehicleExpense.setStartDateOfMonth(DateTimeUtility.getFirstDayOfMonth(transactionDateTime));
			monthWiseVehicleExpense.setVid(vid);
			monthWiseVehicleExpense.setExpenseType(txnType);
			monthWiseVehicleExpense.setExpenseId(expenseId);
			monthWiseVehicleExpense.setExpenseAmount(txnAmount);
			
			synchronized (xMutexFactory.getMutex(valueObject.getInt("vid",0))) {
				if(valueObject.getShort(VehicleExpenseTypeConstant.EXPENSE_TYPE) != VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
					monthWiseVehicleExpenseDao.subtractPreviousMonthAmount(monthWiseVehicleExpense, monthWiseVehicleExpense.getStartDateOfMonth(), txnAmount);
				}else {
					monthWiseVehicleExpenseDao.subtractPreviousMonthAmount(monthWiseVehicleExpense);
				}
			}
		} catch (Exception e) {
			logger.error("Error : ", e);
		}
		
	}
	
	@Override
	public void runThreadForDeleteDateWiseVehicleExpenseBalance(ValueObject valueObject) throws Exception {
		Integer		vid						= 0;
		Timestamp	transactionDateTime		= null;
		Double		txnAmount				= 0.0;
		Integer		companyId				= 0;
		try {
			vid					= valueObject.getInt("vid", 0);
			transactionDateTime	= (Timestamp) valueObject.get("transactionDateTime");
			txnAmount			= valueObject.getDouble("txnAmount", 0);
			companyId			= valueObject.getInt("companyId");
			DateWiseVehicleBalance	dateWiseVehicleBalance = new DateWiseVehicleBalance();
			
			dateWiseVehicleBalance.setCompanyId(companyId);
			dateWiseVehicleBalance.setVid(vid);
			
			synchronized (xMutexFactory.getMutex(valueObject.getInt("vid",0))) {
				
				dateWiseVehicleBalanceDao.subtractAddAmountForPreviousFuelDate(dateWiseVehicleBalance, txnAmount, true, transactionDateTime);
				
				dateWiseVehicleBalanceDao.addAmountToAllDayAmountForNextDays(dateWiseVehicleBalance, txnAmount, transactionDateTime);
			}
			
		} catch (Exception e) {
			logger.error("Error : ", e);
		}
	}
	
	@Override
	public void runThreadForDeleteMonthWiseVehicleExpenseBalance(ValueObject valueObject) throws Exception {
		Integer		vid						= 0;
		Timestamp	transactionDateTime		= null;
		Double		txnAmount				= 0.0;
		Integer		companyId				= 0;
		try {
			vid					= valueObject.getInt("vid", 0);
			transactionDateTime	= (Timestamp) valueObject.get("transactionDateTime");
			txnAmount			= valueObject.getDouble("txnAmount", 0);
			companyId			= valueObject.getInt("companyId");
			
			MonthWiseVehicleBalance	monthWiseVehicleBalance = new MonthWiseVehicleBalance();
			
			monthWiseVehicleBalance.setCompanyId(companyId);
			monthWiseVehicleBalance.setVid(vid);
			monthWiseVehicleBalance.setMonthStartDate(DateTimeUtility.getFirstDayOfMonth(transactionDateTime));
			synchronized (xMutexFactory.getMutex(valueObject.getInt("vid",0))) {
				monthWiseVehicleBalanceDao.subtractAddAmountForPreviousFuelDate(monthWiseVehicleBalance, txnAmount, true, monthWiseVehicleBalance.getMonthStartDate());
				
				ValueObject	object	= new ValueObject();
				object.put("monthWiseVehicleBalance", monthWiseVehicleBalance);
				object.put("amount", txnAmount);
				monthWiseVehicleBalanceDao.addNextDateAllDayBalanceAmount(object);
			}
			
			
		} catch (Exception e) {
			logger.error("Error : ", e);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void runThreadForTripSheetExpenses(ValueObject valueObject) throws Exception {
		
			new Thread() {
				
				public void run() {
					HashMap<Long, VehicleProfitAndLossTxnChecker>		expenseTxnCheckerHashMap		= null;
					HashMap<Long, TripSheetExpenseDto> 					tripSheetExpenseHM				= null;
					CustomUserDetails									userDetails						= null;
					ValueObject											valueInObject					= null;
					TripSheet											tripSheet						= null;
					List<VehicleProfitAndLossTxnChecker>				collection						= null;
					Double												totalTripExpense				= 0.0;
					String												txnIds							= "";
					boolean												isTripSheetClosed				= false;
					HashMap<Long, TripSheetExpense> 					expenseHM						= null;	
				   try {
						
					expenseTxnCheckerHashMap	= (HashMap<Long, VehicleProfitAndLossTxnChecker>) valueObject.get("expenseTxnCheckerHashMap");
					tripSheetExpenseHM			= (HashMap<Long, TripSheetExpenseDto>) valueObject.get("tripSheetExpenseHM");
					userDetails					= (CustomUserDetails) valueObject.get("userDetails");
					tripSheet					= (TripSheet) valueObject.get("tripSheet");
					isTripSheetClosed			= valueObject.getBoolean("isTripSheetClosed", false);
					
					if(isTripSheetClosed)
						expenseHM				= (HashMap<Long, TripSheetExpense>) valueObject.get("tripSheetExpenseHM");
					
					if(expenseTxnCheckerHashMap != null && expenseTxnCheckerHashMap.size() > 0) {
						
						collection	= 	new ArrayList<VehicleProfitAndLossTxnChecker>(expenseTxnCheckerHashMap.values());
						
						for(int i = 0; i< collection.size(); i++) {
							VehicleProfitAndLossTxnChecker	txnChecker	= collection.get(i);
							valueInObject				= new ValueObject();
							valueInObject.put("tripSheet", tripSheet);
							valueInObject.put("userDetails", userDetails);
							valueInObject.put(VehicleProfitAndLossDto.TRIP_EXPENSE_ID, txnChecker.getTripExpenseId());
							valueInObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
							valueInObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
							valueInObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, txnChecker.getVehicleProfitAndLossTxnCheckerId());
							if(!isTripSheetClosed) {
								valueInObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, tripSheetExpenseHM.get(txnChecker.getTripExpenseId()).getExpenseId());
								valueInObject.put("tripSheetAmount", tripSheetExpenseHM.get(txnChecker.getTripExpenseId()).getExpenseAmount());
								
								totalTripExpense	+= tripSheetExpenseHM.get(txnChecker.getTripExpenseId()).getExpenseAmount();
							}else {
		
								valueInObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, expenseHM.get(txnChecker.getTripExpenseId()).getExpenseId());
								valueInObject.put("tripSheetAmount", expenseHM.get(txnChecker.getTripExpenseId()).getExpenseAmount());
								
								totalTripExpense	+= expenseHM.get(txnChecker.getTripExpenseId()).getExpenseAmount();
							
							}
							if(i < collection.size() - 1) {
								txnIds				+= txnChecker.getVehicleProfitAndLossTxnCheckerId()+",";
							}else {
								txnIds				+= txnChecker.getVehicleProfitAndLossTxnCheckerId()+"";
							}
							runThreadForVehicleExpenseDetails(valueInObject);
							runThreadForDateWiseVehicleExpenseDetails(valueInObject);
							runThreadForMonthWiseVehicleExpenseDetails(valueInObject);
							
						}
						ValueObject	object	= new ValueObject();
						object.put("tripSheet", tripSheet);
						object.put("userDetails", userDetails);
						object.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
						object.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
						object.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, valueInObject.getLong(VehicleProfitAndLossDto.TXN_CHECKER_ID));
						object.put("tripSheetAmount", totalTripExpense);
						object.put("txnIds", txnIds);
						
						
					}
					} catch (Exception e) {
						logger.error("Error : ", e);
						}finally {
							expenseTxnCheckerHashMap		= null;
							tripSheetExpenseHM				= null;
							userDetails						= null;
							valueInObject					= null;
							tripSheet						= null;
							collection						= null;
							totalTripExpense				= 0.0;
							txnIds							= "";
							isTripSheetClosed				= false;
							expenseHM						= null;
						}
				}		
			}.start();
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void runThreadForTripSheetIncome(ValueObject valueObject) throws Exception {
		
					HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>	incomeTxnCheckerHashMap			= null;
					HashMap<Long, TripSheetIncomeDto> 					tripSheetIncomeHM				= null;
					CustomUserDetails									userDetails						= null;
					ValueObject											valueInObject					= null;
					TripSheet											tripSheet						= null;
					List<VehicleProfitAndLossIncomeTxnChecker>			collection						= null;
					Double												totalTripIncome					= 0.0;
					String												txnIds							= "";
					boolean												isTripSheetClosed				= false;
					HashMap<Long, TripSheetIncome> 						incomeHM						= null;
					try {
						incomeTxnCheckerHashMap		= (HashMap<Long, VehicleProfitAndLossIncomeTxnChecker>) valueObject.get("incomeTxnCheckerHashMap");
						tripSheetIncomeHM			= (HashMap<Long, TripSheetIncomeDto>) valueObject.get("tripSheetIncomeHM");
						userDetails					= (CustomUserDetails) valueObject.get("userDetails");
						tripSheet					= (TripSheet) valueObject.get("tripSheet");
						isTripSheetClosed			= valueObject.getBoolean("isTripSheetClosed", false);
						if(isTripSheetClosed)
							incomeHM				= (HashMap<Long, TripSheetIncome>) valueObject.get("tripSheetIncomeHM");
						
						if(incomeTxnCheckerHashMap != null && incomeTxnCheckerHashMap.size() > 0) {
							collection	= 	new ArrayList<VehicleProfitAndLossIncomeTxnChecker>(incomeTxnCheckerHashMap.values());
							
							for(int i = 0; i< collection.size(); i++) {
								
								VehicleProfitAndLossIncomeTxnChecker	txnChecker	= collection.get(i);
								valueInObject				= new ValueObject();
								valueInObject.put("tripSheet", tripSheet);
								valueInObject.put("userDetails", userDetails);
								valueInObject.put(VehicleProfitAndLossDto.TRIP_INCOME_ID, txnChecker.getTripIncomeId());
								valueInObject.put(VehicleExpenseTypeConstant.INCOME_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
								valueInObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
								valueInObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, txnChecker.getVehicleProfitAndLossTxnCheckerId());
								if(!isTripSheetClosed) {
									valueInObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, tripSheetIncomeHM.get(txnChecker.getTripIncomeId()).getIncomeId());
									valueInObject.put("tripSheetAmount", tripSheetIncomeHM.get(txnChecker.getTripIncomeId()).getIncomeAmount());
									
									totalTripIncome	+= tripSheetIncomeHM.get(txnChecker.getTripIncomeId()).getIncomeAmount();
								}else {

									valueInObject.put(VehicleProfitAndLossDto.TXN_INCOME_ID, incomeHM.get(txnChecker.getTripIncomeId()).getIncomeId());
									valueInObject.put("tripSheetAmount", incomeHM.get(txnChecker.getTripIncomeId()).getIncomeAmount());
									
									totalTripIncome	+= incomeHM.get(txnChecker.getTripIncomeId()).getIncomeAmount();
								
								}
								
								if(i < collection.size() - 1) {
									txnIds				+= txnChecker.getVehicleProfitAndLossTxnCheckerId()+",";
								}else {
									txnIds				+= txnChecker.getVehicleProfitAndLossTxnCheckerId()+"";
								}
								
								runThreadForVehicleIncomeDetails(valueInObject);
								runThreadForMonthWiseIncomeDetails(valueInObject);
								
							}
							ValueObject	object	= new ValueObject();
							object.put("tripSheet", tripSheet);
							object.put("userDetails", userDetails);
							object.put(VehicleExpenseTypeConstant.INCOME_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
							object.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_INCOME);
							object.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, valueInObject.getLong(VehicleProfitAndLossDto.TXN_CHECKER_ID));
							object.put("tripSheetAmount", totalTripIncome);
							object.put("txnIds", txnIds);
							
							
						}
					}
					 catch (Exception e) {
						 logger.error("Error : ", e);
					}finally {
						incomeTxnCheckerHashMap			= null;
						tripSheetIncomeHM				= null;
						userDetails						= null;
						valueInObject					= null;
						tripSheet						= null;
						collection						= null;
						totalTripIncome					= 0.0;
						txnIds							= "";
						isTripSheetClosed				= false;
						incomeHM						= null;
					}
		/*
		 * } }.start();
		 */
	}

	public void processVehicleIncomeDetails(VehicleProfitAndLossDto	profitAndLossDto) throws Exception {
		VehicleProfitAndLossIncomeTxnChecker		profitAndLossTxnChecker	= null;
		try {
			synchronized (xMutexFactory.getMutex(profitAndLossDto.getVid())) {
				
				profitAndLossTxnChecker	= vehicleProfitAndLossIncomeTxnCheckerRepository.getVehicleProfitAndLossTxnChecker(profitAndLossDto.getTxnCheckerId());
				
				if(profitAndLossTxnChecker != null && profitAndLossTxnChecker.isFinallyEntered()) {
					return;
				}
				
				if(profitAndLossTxnChecker != null && !profitAndLossTxnChecker.isVehicleIncomeAdded()) {
					
					VehicleIncomeDetails	incomeDetails	= txnCheckerBL.createDataForVehicleIncomeDetails(profitAndLossDto);
					
					vehicleIncomeDetailsRepository.save(incomeDetails);
					
					if(incomeDetails.getVehicleIncomeDetailsId() != null)
						vehicleProfitAndLossIncomeTxnCheckerRepository.updateVehicleIncomeAdded(profitAndLossTxnChecker.getVehicleProfitAndLossTxnCheckerId());
				}
			}
			
				
				
		} catch (Exception e) {
			//emailAlertService.sendExceptionEmail(e, getClass().getName());
			logger.error("Exception ",e);
		} finally {
		
		}
		
	}
	
	@Override
	public void runThreadForVehicleIncomeDetails(ValueObject valueObject) throws Exception {
		Thread.sleep(2000);
		new Thread() {
			public void run() {
				VehicleProfitAndLossDto		vehicleProfitAndLossDto	=	null;
				try {
					
					if(valueObject.getShort(VehicleExpenseTypeConstant.INCOME_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForTripSheetIncome(valueObject);
						processVehicleIncomeDetails(vehicleProfitAndLossDto);
					}
					
				} catch (Exception e) {
					
					logger.error("Error : ", e);
				}finally {
					vehicleProfitAndLossDto	= null;
				}
		 }		
		}.start();
	}
	
	@Override
	public void runThreadForMonthWiseIncomeDetails(ValueObject valueObject) throws Exception {
		Thread.sleep(2000);
		new Thread() {
			public void run() {
				VehicleProfitAndLossDto		vehicleProfitAndLossDto	=	null;
				try {
					
					if(valueObject.getShort(VehicleExpenseTypeConstant.INCOME_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForTripSheetIncome(valueObject);
						processMonthWiseVehicleIncome(vehicleProfitAndLossDto);
					}
					
				} catch (Exception e) {
					logger.error("Error : ", e);
				}finally {
					vehicleProfitAndLossDto	=	null;
				}
			 }		
		}.start();	
	}
	
	public void processMonthWiseVehicleIncome(VehicleProfitAndLossDto		vehicleProfitAndLossDto) throws Exception {
		VehicleProfitAndLossIncomeTxnChecker		profitAndLossTxnChecker		= null;
		try {
			synchronized (xMutexFactory.getMutex(vehicleProfitAndLossDto.getVid())) {
				
				profitAndLossTxnChecker	= vehicleProfitAndLossIncomeTxnCheckerRepository.getVehicleProfitAndLossTxnChecker(vehicleProfitAndLossDto.getTxnCheckerId());
				
				if(profitAndLossTxnChecker != null && profitAndLossTxnChecker.isFinallyEntered()) {
					return;
				}
				
				if(profitAndLossTxnChecker != null && !profitAndLossTxnChecker.isMonthWiseVehicleIncomeAdded()) {
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(DateTimeUtility.geTimeStampFromDate(vehicleProfitAndLossDto.getTransactionDateTime()));
					
					MonthWiseVehicleIncome validate		=	null;
					
					if(vehicleProfitAndLossDto.getType() == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						validate		= monthWiseVehicleIncomeDao.validateMonthWiseVehicleIncome(vehicleProfitAndLossDto.getVid(), DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getFirstDayOfMonth(vehicleProfitAndLossDto.getTransactionDateTime()), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), vehicleProfitAndLossDto.getCompanyId(), Integer.parseInt(vehicleProfitAndLossDto.getIncomeId()+""), vehicleProfitAndLossDto.getType());
					}else {
						validate		= monthWiseVehicleIncomeDao.validateMonthWiseVehicleIncome(vehicleProfitAndLossDto.getVid(), DateTimeUtility.getDateFromTimeStamp(DateTimeUtility.getFirstDayOfMonth(vehicleProfitAndLossDto.getTransactionDateTime()), DateTimeUtility.YYYY_MM_DD_HH_MM_SS), vehicleProfitAndLossDto.getCompanyId(), vehicleProfitAndLossDto.getType());
					}
					
					MonthWiseVehicleIncome	incomeDetails	= txnCheckerBL.createMonthWiseVehicleIncome(vehicleProfitAndLossDto, cal, validate);
					if(validate != null) {
						incomeDetails.setMonthWiseVehicleIncomeId(validate.getMonthWiseVehicleIncomeId());
					}
					monthWiseVehicleIncomeRepository.save(incomeDetails);
					
					
					vehicleProfitAndLossIncomeTxnCheckerRepository.updateMonthWiseVehicleIncomeAdded(vehicleProfitAndLossDto.getTxnCheckerId());
				}
			}
			
		} catch (Exception e) {
			//emailAlertService.sendExceptionEmail(e, getClass().getName());
			logger.error("Exception ",e);
		} 
		
	}
	
	@Override
	public void runThreadForMonthWiseIncomeBalance(ValueObject valueObject) throws Exception {
		
				VehicleProfitAndLossDto		vehicleProfitAndLossDto	=	null;
				try {
					
					if(valueObject.getShort(VehicleExpenseTypeConstant.INCOME_TYPE) == VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET) {
						vehicleProfitAndLossDto	= DataCreatorForVehicleProfitAndLoss.prepareVehicleProfitAndLossDtoForTripSheetIncome(valueObject);
						processMonthWiseVehicleIncomeBalance(vehicleProfitAndLossDto);
						
						mothWiseVehicleIncomeBalanceDao.updateMonthWiseVehicleBalanceUpdated(valueObject.getString("txnIds"));
					}
					
				} catch (Exception e) {
					try {
						//emailAlertService.sendExceptionEmail(e, getClass().getName());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					logger.error("Error : ", e);
				}finally {
					vehicleProfitAndLossDto	=	null;
				}

	}
	
	public void processMonthWiseVehicleIncomeBalance(VehicleProfitAndLossDto		vehicleProfitAndLossDto) throws Exception{

		VehicleProfitAndLossIncomeTxnChecker		profitAndLossTxnChecker		= null;
		try {
			
			profitAndLossTxnChecker	= vehicleProfitAndLossIncomeTxnCheckerRepository.getVehicleProfitAndLossTxnChecker(vehicleProfitAndLossDto.getTxnCheckerId());
			
			if(profitAndLossTxnChecker != null && profitAndLossTxnChecker.isFinallyEntered()) {
				return;
			}
			
			if(profitAndLossTxnChecker != null && !profitAndLossTxnChecker.isMonthWiseVehicleBalanceUpdated()) {
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(DateTimeUtility.geTimeStampFromDate(vehicleProfitAndLossDto.getTransactionDateTime()));
				
				//MothWiseVehicleIncomeBalance
				MothWiseVehicleIncomeBalance	currentDetail	=	mothWiseVehicleIncomeBalanceRepository.getMothWiseVehicleIncomeBalance(vehicleProfitAndLossDto.getVid(), DateTimeUtility.getFirstDayOfMonth(vehicleProfitAndLossDto.getTransactionDateTime()));
				MothWiseVehicleIncomeBalance	previousDetails	= 	mothWiseVehicleIncomeBalanceRepository.getLastMonthWiseVehicleBalance(vehicleProfitAndLossDto.getVid(), DateTimeUtility.getFirstDayOfMonth(vehicleProfitAndLossDto.getTransactionDateTime()));
				
				MothWiseVehicleIncomeBalance	incomeBalance	= 	txnCheckerBL.creatMothWiseVehicleIncomeBalance(vehicleProfitAndLossDto,  previousDetails, currentDetail);
				
				if(currentDetail != null) {
					incomeBalance.setMothWiseVehicleIncomeBalanceId(currentDetail.getMothWiseVehicleIncomeBalanceId());
				}
				
				mothWiseVehicleIncomeBalanceRepository.save(incomeBalance);
				
				if(DateTimeUtility.getFirstDayOfMonth(vehicleProfitAndLossDto.getTransactionDateTime()).before(DateTimeUtility.getFirstDayOfMonth(DateTimeUtility.geTimeStampFromDate(new Date())))) {
					
					mothWiseVehicleIncomeBalanceDao.updateMonthBalanceAmount(vehicleProfitAndLossDto);
				}
			}
			
		} catch (Exception e) {
			//emailAlertService.sendExceptionEmail(e, getClass().getName());
			logger.error("Exception ",e);
			throw e;
		}
				
	}
	
	@Override
	@Transactional
	public void runThreadForDeleteIncome(ValueObject valueObject) throws Exception {
		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) valueObject.get("userDetails");
			Long	txnTypeId	= valueObject.getLong("txnTypeId", 0);
			vehicleIncomeDetailsRepository.deleteVehicleIncomeDetails(valueObject.getLong(VehicleProfitAndLossDto.TRIP_INCOME_ID, 0), userDetails.getCompany_id(), valueObject.getInt("vid", 0), txnTypeId);
			
			monthWiseVehicleIncomeDao.deleteMonthWiseVehicleIncome(valueObject.getInt("vid", 0),(Timestamp) valueObject.get("transactionDate"), userDetails.getCompany_id(), valueObject.getInt("incomeId", 0), valueObject.getDouble("incomeAmount",0));
			
			
		} catch (Exception e) {
			logger.error("Exception ",e);
			throw e;
		}
		
	}
	
	@Override
	public List<MonthWiseVehicleExpenseDto> getMonthWiseVehicleExpenseByVid(Integer vid, Timestamp startDateOfMonth,
			Integer companyId)throws Exception {
		List<MonthWiseVehicleExpenseDto>		vehicleExpenseList		= null;
		try {
			
			vehicleExpenseList	= 	monthWiseVehicleExpenseDao.getMonthWiseVehicleExpenseForReport(vid, startDateOfMonth, companyId);
			
			return vehicleExpenseList;
		} catch (Exception e) {
			logger.error("Exception ",e);
			throw e;
		}finally {
			
		}
	}
	
	@Override
	public List<VehicleProfitAndLossReportDto> getMonthIncomeDetails(Integer vid, Timestamp startDateOfMonth,
			Integer companyId) throws Exception {

		return monthWiseVehicleIncomeDao.getMonthIncomeDetailsByVid(vid, startDateOfMonth, companyId);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public void runThreadForDeleteExpenseDetailsOFTripSheet(ValueObject valueObject) throws Exception {
		List<TripDailyExpenseDto>		expenseList			= null;
		List<TripDailyIncomeDto>		incomeList			= null;
		Timestamp						transactionDate		= null;
		CustomUserDetails				userDetails			= null;
		Long							txnTypeId			= (long) 0;
		try {
			expenseList			= (List<TripDailyExpenseDto>) valueObject.get("expenseList");
			incomeList			= (List<TripDailyIncomeDto>) valueObject.get("incomeList");
			transactionDate		= (Timestamp) valueObject.get("transactionDate");
			userDetails			= (CustomUserDetails) valueObject.get("userDetails");
			txnTypeId			= valueObject.getLong("txnTypeId", 0);
		
			if(expenseList != null && !expenseList.isEmpty()) {
				for(TripDailyExpenseDto dailyExpenseDto : expenseList) {
					
					ValueObject		expObject	= new ValueObject();
					expObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET);
					expObject.put("txnAmount", dailyExpenseDto.getExpenseAmount());
					expObject.put("transactionDateTime", transactionDate);
					expObject.put("txnTypeId", txnTypeId);
					expObject.put("expenseId", dailyExpenseDto.getExpenseId());
					expObject.put("vid", valueObject.getInt("vid"));
					expObject.put("companyId", userDetails.getCompany_id());
					
					runThreadForDeleteVehicleExpenseDetails(expObject);
					runThreadForDeleteDateWiseVehicleExpenseDetails(expObject);
					runThreadForDeleteMonthWiseVehicleExpenseDetails(expObject);
					//runThreadForDeleteDateWiseVehicleExpenseBalance(expObject);
					//runThreadForDeleteMonthWiseVehicleExpenseBalance(expObject);
				}
			}
			
			if(incomeList != null && !incomeList.isEmpty()) {
				
				for(TripDailyIncomeDto dailyIncomeDto : incomeList) {
					ValueObject		incomeObject	= new ValueObject();
					
					incomeObject.put("userDetails", userDetails);
					incomeObject.put("vid", valueObject.getInt("vid"));
					incomeObject.put("incomeId", dailyIncomeDto.getIncomeId());
					incomeObject.put("incomeAmount", dailyIncomeDto.getIncomeAmount());
					incomeObject.put("transactionDate", transactionDate);
					incomeObject.put("txnTypeId", txnTypeId);
					
					runThreadForDeleteIncome(incomeObject);
				}
			}
		} catch (Exception e) {
			//emailAlertService.sendExceptionEmail(e, getClass().getName());
			throw e;
		}
	}
	
	@Override
	public List<MonthWiseVehicleExpenseDto> getMonthWiseGroupExpenseByVid(long vehicleGroupId,
			Timestamp startDateOfMonth, Integer companyId) throws Exception {
		List<MonthWiseVehicleExpenseDto>		vehicleExpenseList		= null;
		try {
			
			vehicleExpenseList	= 	monthWiseVehicleExpenseDao.getMonthWiseGroupExpenseForReport(vehicleGroupId, startDateOfMonth, companyId);
			
			return vehicleExpenseList;
		} catch (Exception e) {
			//emailAlertService.sendExceptionEmail(e, getClass().getName());
			logger.error("Exception ",e);
			throw e;
		}finally {
			
		}
	}
	
	@Override
	public List<VehicleProfitAndLossReportDto> getMonthIncomeDetailsByVehicleGroupId(long vehicleGroupId, Timestamp startDateOfMonth, Integer companyId) throws Exception {

		return monthWiseVehicleIncomeDao.getMonthIncomeDetailsByVehicleGroupId(vehicleGroupId, startDateOfMonth, companyId);
	}
	
	@Override
	public List<TripSheetIncomeDto> getVehicleIncomeDetailsOfMonthByIncomeId(TripSheetIncomeDto incomeDto)
			throws Exception {
		List<TripSheetIncomeDto>		incomeList		= null;
		try {
			int flavor	=	companyConfigurationService.getTripSheetFlavor(incomeDto.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if(flavor	== TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				incomeList	=	tripSheetService.getVehicleIncomeDetailsOfMonthByIncomeId(incomeDto);
			}else if(flavor	== TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				
			}else if(flavor	== TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				incomeList	=	tripDailySheetService.getVehicleIncomeDetailsOfMonthByIncomeId(incomeDto);
			}else {
				incomeList = new ArrayList<>();
				List<TripSheetIncomeDto> incomeList1    =   tripSheetService.getVehicleIncomeDetailsOfMonthByIncomeId(incomeDto);
				List<TripSheetIncomeDto> incomeList2	=	tripDailySheetService.getVehicleIncomeDetailsOfMonthByIncomeId(incomeDto);
				incomeList.addAll(incomeList1);
				incomeList.addAll(incomeList2);
			}
			return	incomeList;
		} catch (Exception e) {
			//emailAlertService.sendExceptionEmail(e, getClass().getName());
			throw	e;
		}
	}
	
	@Override
	public List<TripSheetExpenseDto> getVehicleExpenseDetailsOfMonthByExpennseId(TripSheetIncomeDto incomeDto)
			throws Exception {
		List<TripSheetExpenseDto>		expenseList		= null;
		List<TripSheetExpenseDto>		fastTagList		= null;
		try {
			int flavor			 =	companyConfigurationService.getTripSheetFlavor(incomeDto.getCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			int fastTagExpenseId = companyConfigurationService.getFastTageExpenseId(incomeDto.getCompanyId(), PropertyFileConstants.TOLL_API_CONFIG);
			
			if(flavor	== TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_ONE) {
				expenseList	=	tripSheetService.getVehicleExpenseDetailsOfMonthByExpenseId(incomeDto);
			}else if(flavor	== TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_TWO) {
				
			}else if(flavor	== TripSheetFlavorConstant.TRIP_SHEET_FLAVOR_THREE) {
				expenseList	=	tripDailySheetService.getVehicleExpenseDetailsOfMonthByExpenseId(incomeDto);
			}else {
				expenseList	=	tripSheetService.getVehicleExpenseDetailsOfMonthByExpenseId(incomeDto);
			}
			if(fastTagExpenseId == incomeDto.getIncomeId()) {
				fastTagList	=	tripSheetService.getVehicleFastTagDetailsOfMonthByExpenseId(incomeDto);
			}
			
			if(expenseList == null) {
				expenseList = new ArrayList<>();
			}
			
			if(fastTagList != null) {
				expenseList.addAll(fastTagList);
			}
			
			return	expenseList;
		} catch (Exception e) {
			//emailAlertService.sendExceptionEmail(e, getClass().getName());
			throw	e;
		}
	}
	
	@Override
	public List<DriverAttendanceDto> getDriverAttandanceOfMonth(TripSheetIncomeDto incomeDto) throws Exception {
		
		return driverAttendanceService.getDriverAttandanceOfMonth(incomeDto);
	}
	
	@Override
	@Transactional
	public void updateIsFinallyEntered() throws Exception {
		
		try {
			entityManager.createNativeQuery(
					"UPDATE VehicleProfitAndLossTxnChecker SET isFinallyEntered = 1 "
					+ " where isVehicleExpenseAdded = 1 AND isDateWiseVehicleExpenseAdded = 1 AND isMonthWiseVehicleExpenseAdded = 1 AND isDateWiseVehicleBalanceUpdated = 1"
					+ " AND isMonthWiseVehicleBalanceUpdated = 1")
			.executeUpdate();
			} catch (Exception e) {
				//emailAlertService.sendExceptionEmail(e, getClass().getName());
				logger.error("Exception ", e);
		}
	}
	
	@Override
	@Transactional
	public void deleteVehicleExpenseTxnChecker() throws Exception {

		vehicleProfitAndLossTxnCheckerRepository.deleteVehicleExpenseTxnChecker();
	}
	
	@Override
	@Transactional
	public void updateIsFinallyEnteredForIncome() throws Exception {
		
		try {
			entityManager.createNativeQuery(
					"UPDATE VehicleProfitAndLossIncomeTxnChecker SET isFinallyEntered = 1 "
					+ " where isVehicleIncomeAdded = 1 AND isMonthWiseVehicleIncomeAdded = 1 AND isMonthWiseVehicleBalanceUpdated = 1")
			.executeUpdate();
			} catch (Exception e) {
				//emailAlertService.sendExceptionEmail(e, getClass().getName());
				logger.error("Exception ", e);
		}
	}
	
	@Override
	@Transactional
	public void deleteVehicleExpenseIncomeTxnChecker() throws Exception {

		vehicleProfitAndLossIncomeTxnCheckerRepository.deleteVehicleExpenseTxnChecker();
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseVehicleExpenseDtoByVid(Integer vid, String fromDate,
			String toDate, Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.expenseAmount), MVE.expenseType  "
							+ " FROM DateWiseVehicleExpense MVE "
							+ " where MVE.vid = "+vid+" AND MVE.expenseDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.expenseType <> 4 AND MVE.markForDelete = 0 GROUP BY MVE.expenseType", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseType((short) result[1]);
					mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseDriverSalaryByVid(Integer vid, String fromDate, String toDate,
			Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.expenseAmount), MVE.companyId  "
							+ " FROM VehicleExpenseDetails MVE "
							+ " where MVE.vid = "+vid+" AND MVE.expenseDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.expenseType = 9 AND MVE.markForDelete = 0 AND MVE.expenseAmount > 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					if(result[0] != null) {
						mExpenseDto.setExpenseAmount((Double) result[0]);
						mExpenseDto.setExpenseType((short) 9);
						mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
						
						list.add(mExpenseDto);
					}
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<MonthWiseVehicleExpenseDto> getMonthWiseDriverSalaryByVid(Integer vid, String fromDate, String toDate,
			Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.expenseAmount), MVE.companyId  "
							+ " FROM VehicleExpenseDetails MVE "
							+ " where MVE.vid = "+vid+" AND MVE.expenseDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.expenseType = 9 AND MVE.markForDelete = 0 AND MVE.expenseAmount > 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<MonthWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					MonthWiseVehicleExpenseDto	mExpenseDto = new MonthWiseVehicleExpenseDto();
					if(result[0] != null) {
						mExpenseDto.setExpenseAmount((Double) result[0]);
						mExpenseDto.setExpenseType((short) 9);
						mExpenseDto.setExpenseTypeStr(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
						
						list.add(mExpenseDto);
					}
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseDriverSalaryByRouteId(Integer vid, String fromDate, String toDate,
			Integer companyId, Integer routeId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.expenseAmount), MVE.companyId  "
							+ " FROM VehicleExpenseDetails MVE "
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = MVE.txnTypeId AND MVE.expenseType = 4 AND TS.routeID = "+routeId+""
							+ " where MVE.vid = "+vid+" AND MVE.expenseDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.expenseType = 9 AND MVE.markForDelete = 0 AND MVE.expenseAmount > 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					if(result[0] != null) {
						mExpenseDto.setExpenseAmount((Double) result[0]);
						mExpenseDto.setExpenseType((short) 9);
						mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
						
						list.add(mExpenseDto);
					}
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseDriverSalaryByVehicleTypeId(Integer vid, String fromDate,
			String toDate, Integer companyId, Long vehicleTypeId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.expenseAmount), MVE.companyId  "
							+ " FROM VehicleExpenseDetails MVE "
							+ " INNER JOIN Vehicle V ON V.vid = MVE.vid AND V.vehicleTypeId = "+vehicleTypeId+""
							+ " where MVE.vid = "+vid+" AND MVE.expenseDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.expenseType = 9 AND MVE.markForDelete = 0 AND MVE.expenseAmount > 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					if(result[0] != null) {
						mExpenseDto.setExpenseAmount((Double) result[0]);
						mExpenseDto.setExpenseType((short) 9);
						mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
						
						list.add(mExpenseDto);
					}
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseDriverSalaryByVTRouteId(Integer vid, String fromDate,
			String toDate, Integer companyId, Integer routeId, Long vehicleTypeId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.expenseAmount), MVE.companyId  "
							+ " FROM VehicleExpenseDetails MVE "
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = MVE.txnTypeId AND MVE.expenseType = 4 AND TS.routeID = "+routeId+""
							+ " INNER JOIN Vehicle V ON V.vid = MVE.vid AND V.vehicleTypeId = "+vehicleTypeId+""
							+ " where MVE.vid = "+vid+" AND MVE.expenseDate between '"+ fromDate +"' AND '"+toDate+"' AND MVE.companyId = "+companyId+""
									+ " AND MVE.expenseType = 9 AND MVE.markForDelete = 0 AND MVE.expenseAmount > 0", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					if(result[0] != null) {
						mExpenseDto.setExpenseAmount((Double) result[0]);
						mExpenseDto.setExpenseType((short) 9);
						mExpenseDto.setExpenseTypeName(VehicleExpenseTypeConstant.getExpenseTypeName(mExpenseDto.getExpenseType()));
						
						list.add(mExpenseDto);
					}
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseVehicleTripExpenseDtoByVid(Integer vid, String fromDate,
			String toDate) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.expenseAmount), MVE.expenseId, TE.expenseName "
							+ " FROM TripSheetExpense MVE "
							+ " INNER JOIN TripExpense TE ON TE.expenseID = MVE.expenseId AND (MVE.fuel_id is null OR MVE.fuel_id = 0) "
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = MVE.tripsheet.tripSheetID AND TS.markForDelete = 0 "
							+ " where TS.vid = "+vid+" AND TS.closetripDate between '"+ fromDate +"' AND '"+toDate+"' "
									+ " AND MVE.markForDelete = 0 AND MVE.expenseAmount > 0 GROUP BY MVE.expenseId", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseId((Integer) result[1]);
					mExpenseDto.setExpenseTypeName((String) result[2]);
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<MonthWiseVehicleExpenseDto> getMonthWiseVehicleTripExpenseDtoByVid(Integer vid, String fromDate,
			String toDate) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.expenseAmount), MVE.expenseId, TE.expenseName "
							+ " FROM TripSheetExpense MVE "
							+ " INNER JOIN TripExpense TE ON TE.expenseID = MVE.expenseId AND (MVE.fuel_id is null OR MVE.fuel_id = 0) "
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = MVE.tripsheet.tripSheetID AND TS.markForDelete = 0 "
							+ " where TS.vid = "+vid+" AND TS.closetripDate between '"+ fromDate +"' AND '"+toDate+"' "
									+ " AND MVE.markForDelete = 0 AND MVE.expenseAmount > 0 GROUP BY MVE.expenseId", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<MonthWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					MonthWiseVehicleExpenseDto	mExpenseDto = new MonthWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseId((Integer) result[1]);
					mExpenseDto.setExpenseType((short) 4);
					mExpenseDto.setExpenseTypeStr((String) result[2]);
					mExpenseDto.setTripExpenseName((String) result[2]);
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseVehicleTripExpenseDtoByRouteId(Integer vid, String fromDate,
			String toDate, Integer routeId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.expenseAmount), MVE.expenseId, TE.expenseName "
							+ " FROM TripSheetExpense MVE "
							+ " INNER JOIN TripExpense TE ON TE.expenseID = MVE.expenseId AND (MVE.fuel_id is null OR MVE.fuel_id = 0) "
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = MVE.tripsheet.tripSheetID AND TS.routeID = "+routeId+""
							+ " where TS.vid = "+vid+" AND TS.closetripDate between '"+ fromDate +"' AND '"+toDate+"' "
									+ " AND MVE.markForDelete = 0 AND MVE.expenseAmount > 0 GROUP BY MVE.expenseId", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseId((Integer) result[1]);
					mExpenseDto.setExpenseTypeName((String) result[2]);
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseVehicleTripExpenseDtoByVehicleTypeId(Integer vid, String fromDate,
			String toDate, Long vehicleTypeId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.expenseAmount), MVE.expenseId, TE.expenseName "
							+ " FROM TripSheetExpense MVE "
							+ " INNER JOIN TripExpense TE ON TE.expenseID = MVE.expenseId AND (MVE.fuel_id is null OR MVE.fuel_id = 0) "
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = MVE.tripsheet.tripSheetID "
							+ " INNER JOIN Vehicle V ON V.vid = TS.vid AND V.vehicleTypeId = "+vehicleTypeId+""
							+ " where TS.vid = "+vid+" AND TS.closetripDate between '"+ fromDate +"' AND '"+toDate+"' "
									+ " AND MVE.markForDelete = 0 AND MVE.expenseAmount > 0 GROUP BY MVE.expenseId", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseId((Integer) result[1]);
					mExpenseDto.setExpenseTypeName((String) result[2]);
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<DateWiseVehicleExpenseDto> getDateWiseVehicleTripExpenseDtoByVTRouteId(Integer vid, String fromDate,
			String toDate, Long vehicleTypeId, Integer routeId) throws Exception {
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.expenseAmount), MVE.expenseId, TE.expenseName "
							+ " FROM TripSheetExpense MVE "
							+ " INNER JOIN TripExpense TE ON TE.expenseID = MVE.expenseId AND (MVE.fuel_id is null OR MVE.fuel_id = 0) "
							+ " INNER JOIN TripSheet TS ON TS.tripSheetID = MVE.tripsheet.tripSheetID AND TS.routeID = "+routeId+""
							+ " INNER JOIN Vehicle V ON V.vid = TS.vid AND V.vehicleTypeId = "+vehicleTypeId+""
							+ " where TS.vid = "+vid+" AND TS.closetripDate between '"+ fromDate +"' AND '"+toDate+"' "
									+ " AND MVE.markForDelete = 0 AND MVE.expenseAmount > 0 GROUP BY MVE.expenseId", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<DateWiseVehicleExpenseDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					DateWiseVehicleExpenseDto	mExpenseDto = new DateWiseVehicleExpenseDto();
					
					mExpenseDto.setExpenseAmount((Double) result[0]);
					mExpenseDto.setExpenseId((Integer) result[1]);
					mExpenseDto.setExpenseTypeName((String) result[2]);
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<VehicleProfitAndLossReportDto> getIncomeDetailsForDateRange(Integer vid, String fromDate, String toDate,
			Integer companyId) throws Exception {
		
		try {
			TypedQuery<Object[]> queryt = entityManager
					.createQuery("SELECT  SUM(MVE.incomeAmount), MVE.incomeId ,TI.incomeName  "
							+ " FROM TripSheetIncome MVE "
							+ " INNER JOIN TripSheet T ON T.tripSheetID = MVE.tripsheet.tripSheetID "
							+ " INNER JOIN TripIncome TI ON TI.incomeID = MVE.incomeId"
							+ " where T.vid = "+vid+" AND T.closetripDate between '"+ fromDate +"' AND '"+toDate+"' AND T.companyId = "+companyId+""
									+ " AND T.markForDelete = 0 AND MVE.markForDelete = 0 GROUP BY MVE.incomeId", Object[].class);
			List<Object[]>	results = queryt.getResultList();
			
			List<VehicleProfitAndLossReportDto>	list	= null;
			
			if(results != null && !results.isEmpty()) {
				list	=	new ArrayList<>();
				for (Object[] result : results) {
					VehicleProfitAndLossReportDto	mExpenseDto = new VehicleProfitAndLossReportDto();
					
					mExpenseDto.setIncomeAmount((Double) result[0]);
					mExpenseDto.setIncomeId((Integer) result[1]);
					mExpenseDto.setIncomeName((String) result[2]);
					
					list.add(mExpenseDto);
				}
			}
			
			return list;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void runThreadForEMIPaymentDetails(ValueObject valueObject) throws Exception {
		
			/*new Thread() { // this is the problematic thread (see line no 275 )
				
				public void run() {*/
					HashMap<Long, VehicleProfitAndLossTxnChecker>		emiPaymentTxnCheckerHashMap		= null;
					HashMap<Long, VehicleEmiPaymentDetails> 			emiPaymentHM					= null;
					CustomUserDetails									userDetails						= null;
					ValueObject											valueInObject					= null;
					List<VehicleProfitAndLossTxnChecker>				collection						= null;
					boolean												isEMIEdit						= false;
				   try {
					   isEMIEdit   = valueObject.getBoolean("isEMIEdit", false);
					emiPaymentTxnCheckerHashMap	= (HashMap<Long, VehicleProfitAndLossTxnChecker>) valueObject.get("emiPaymentTxnCheckerHashMap");
					userDetails					= (CustomUserDetails) valueObject.get("userDetails");
					emiPaymentHM				= (HashMap<Long, VehicleEmiPaymentDetails>) valueObject.get("emiPaymentHM");
					
					if(emiPaymentTxnCheckerHashMap != null && emiPaymentTxnCheckerHashMap.size() > 0) {
						
						collection	= 	new ArrayList<VehicleProfitAndLossTxnChecker>(emiPaymentTxnCheckerHashMap.values());
						
						for(int i = 0; i< collection.size(); i++) {
							
							VehicleProfitAndLossTxnChecker	txnChecker	= collection.get(i);
							valueInObject				= new ValueObject();
							
							valueInObject.put("userDetails", userDetails);
							valueInObject.put(VehicleExpenseTypeConstant.EXPENSE_TYPE, VehicleExpenseTypeConstant.TRANSACTION_TYPE_VEHICLE_EMI);
							valueInObject.put(VehicleProfitAndLossDto.TRANSACTION_TYPE, VehicleProfitAndLossDto.TRANSACTION_TYPE_EXPENSE);
							valueInObject.put(VehicleProfitAndLossDto.TXN_CHECKER_ID, txnChecker.getVehicleProfitAndLossTxnCheckerId());
							valueInObject.put(VehicleProfitAndLossDto.TXN_EXPENSE_ID, emiPaymentHM.get(txnChecker.getExpenseId()).getVehicleEmiPaymentDetailsId());
							valueInObject.put("vehicleEmiPaymentDetails", emiPaymentHM.get(txnChecker.getExpenseId()));
							valueInObject.put("isEMIEdit", isEMIEdit);
							
							runThreadForVehicleExpenseDetails(valueInObject);
							runThreadForDateWiseVehicleExpenseDetails(valueInObject);
							runThreadForMonthWiseVehicleExpenseDetails(valueInObject);
							//runThreadForDateWiseVehicleBalanceDetails(valueInObject);
							//runThreadForMonthWiseVehicleBalanceDetails(valueInObject);
							
						}
						
					}
					} catch (Exception e) {
							//emailAlertService.sendExceptionEmail(e, getClass().getName());
						logger.error("Error : ", e);
						}finally {
							emiPaymentTxnCheckerHashMap		= null;
							emiPaymentHM					= null;
							userDetails						= null;
							valueInObject					= null;
							collection						= null;
						}
			/*	}		
			}.start();
		*/
	}
	@Override
	public MonthWiseVehicleExpense getMonthWiseVehicleExpenseByExpenseIdAndVid(ValueObject valueObject) throws Exception {
		MonthWiseVehicleExpense	monthWiseVehicleExpense = null;
		try {
			monthWiseVehicleExpense = monthWiseVehicleExpenseRepository.getMonthWiseVehicleExpenseByExpenseIdAndVid(valueObject.getInt("vid"),
					valueObject.getInt("companyId"),valueObject.getInt("vehicleEmiDetailsId"));
			
			return monthWiseVehicleExpense;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	@Override
	public void updateMonthWiseVehicleExpenseAmount(ValueObject valueObject) throws Exception {
		try {
			 monthWiseVehicleExpenseRepository.updateExpenseAmount(valueObject.getInt("vid"),valueObject.getInt("companyId"),valueObject.getInt("vehicleEmiDetailsId"),valueObject.getDouble("expenseAmount"));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Override
	public ValueObject getMonthAndDateWiseVehicleExpenseOfTripExpense(ValueObject valueObject) throws Exception {
		MonthWiseVehicleExpense	monthWiseVehicleExpense = null;
		DateWiseVehicleExpense	dateWiseVehicleExpense 	= null;
		ValueObject				ValueOutObject			= null;
		try {
			ValueOutObject = new ValueObject();
			monthWiseVehicleExpense = monthWiseVehicleExpenseRepository.getMonthWiseVehicleExpenseOfTripExpense(valueObject.getInt("vid"),valueObject.getInt("companyId"),valueObject.getInt("expenseId"),VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET,valueObject.getLong("tripsheetId"));
			dateWiseVehicleExpense = dateWiseVehicleExpenseRepository.getDateWiseVehicleExpenseOfTripExpense(valueObject.getInt("vid"),valueObject.getInt("companyId"),valueObject.getInt("expenseId"),VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET,valueObject.getLong("tripsheetId"));
			ValueOutObject.put("monthWiseVehicleExpense", monthWiseVehicleExpense);
			ValueOutObject.put("dateWiseVehicleExpense", dateWiseVehicleExpense);
			return ValueOutObject;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@Transactional
	@Override
	public void updateExpenseAmountOfTripExpense(ValueObject valueObject) throws Exception {
		try {
			 monthWiseVehicleExpenseRepository.updateExpenseAmountOfTripExpense(valueObject.getInt("vid"),valueObject.getInt("companyId"),valueObject.getInt("expenseId"),VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET,valueObject.getLong("tripsheetId"),valueObject.getDouble("expenseAmount"));
			 dateWiseVehicleExpenseRepository.updateExpenseAmountOfTripExpense(valueObject.getInt("vid"),valueObject.getInt("companyId"),valueObject.getInt("expenseId"),VehicleExpenseTypeConstant.TRANSACTION_TYPE_TRIPSHEET,valueObject.getLong("tripsheetId"),valueObject.getDouble("expenseAmount"));
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	
	
}
