package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.DriverLedgerTypeConstant;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.dao.DriverLedgerRepository;
import org.fleetopgroup.persistence.dao.DriverTripSheetBalanceRepository;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverLedgerDto;
import org.fleetopgroup.persistence.dto.TripSheetExpenseDto;
import org.fleetopgroup.persistence.model.DriverLedger;
import org.fleetopgroup.persistence.model.DriverTripSheetBalance;
import org.fleetopgroup.persistence.model.TripSheetAdvance;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.report.dao.DriverLedgerDao;
import org.fleetopgroup.persistence.serviceImpl.IDriverLedgerService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetMobileService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.antkorwin.xsync.XSync;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
@Service
public class DriverLedgerService implements IDriverLedgerService {
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired	private DriverLedgerRepository					driverLedgerRepository;
	@Autowired	private DriverTripSheetBalanceRepository		driverTripSheetBalanceRepository;
	@Autowired	private DriverLedgerDao							driverLedgerDao;
	@Autowired	private XSync<Long> 							xSync;
	@Autowired private  ITripSheetService                       tripSheetService;
	@Autowired private ITripSheetMobileService					tripSheetMobileService;
	
	@Override
	@Transactional
	public ValueObject addDriverLedgerDetails(ValueObject valueInObject) throws Exception {
		try {
			xSync.execute(valueInObject.getLong("driverId",0), () -> {
				DriverLedger				driverLedger				= null;
				DriverTripSheetBalance		driverTripSheetBalance		= null;
				DriverTripSheetBalance		preDriverBalance			= null;
				DriverLedger				preDriverLedger				= null;
				try {
					
					Timestamp txnTime = null;
					
					if(valueInObject.get("txnTime") !=null)
						txnTime = (Timestamp) valueInObject.get("txnTime");
					else
						txnTime = DateTimeUtility.getTimeStamp(DateTimeUtility.getCurrentDateStr(DateTimeUtility.YYYY_MM_DD_HH_MM_SS),
											DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

					
					preDriverBalance	= driverTripSheetBalanceRepository.getDriverTripSheetBalanceByDriverId(valueInObject.getLong("driverId",0), valueInObject.getInt("companyId"));
					preDriverLedger		= driverLedgerRepository.getDriveLedgerByDriverId(valueInObject.getLong("driverId",0), valueInObject.getInt("companyId"), txnTime);
					valueInObject.put("txnTime", txnTime);
					
					if(preDriverBalance != null ) {
						double amount =preDriverBalance.getBalanceAmount();
						if(amount > 0 && valueInObject.getBoolean("tripCreateEntry",false)) {
							transferDriverBalanceToNextTrip(valueInObject,preDriverBalance,preDriverLedger);
							preDriverLedger=(DriverLedger) valueInObject.get("preDriverLedger");
						}
					}
					
					if (valueInObject.getDouble("amount", 0) != 0) {
						driverLedger = DriverBL.getDriverLedgerDto(preDriverLedger, valueInObject);
						driverLedger.setTxnDateTime(txnTime);

						driverTripSheetBalance = DriverBL.getDriverTripSheetBalanceDto(preDriverBalance, valueInObject);

						driverLedgerRepository.save(driverLedger);
						driverTripSheetBalanceRepository.save(driverTripSheetBalance);
						
						new Thread(()->{
							try {
								driverLedgerDao.updateOpeningClosingBalance(valueInObject.getLong("driverId",0),(Timestamp) valueInObject.get("txnTime"),valueInObject.getDouble("amountAdvanceOrExpense",0));
							} catch (Exception e) {
								e.printStackTrace();
							}
						}).start();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			
			return valueInObject;
		} catch (Exception e) {
			throw e;
		}
	}
	
public void transferDriverBalanceToNextTrip(ValueObject valueInObject,DriverTripSheetBalance preDriverBalance,DriverLedger preDriverLedger) {
	try {
			DriverLedger				driverLedger				= null;
				if(preDriverBalance != null ) {
					double amount =preDriverBalance.getBalanceAmount();
					if(valueInObject.getBoolean("tripCreateEntry",false)) {
						ValueObject object = new ValueObject();
						object.put("companyId", valueInObject.getInt("companyId"));
						object.put("userId",  valueInObject.getLong("userId"));
						object.put("driverId", valueInObject.getLong("driverId",0));
						object.put("txnTime", valueInObject.get("txnTime"));
						object.put("amount", amount);
						object.put("transactionId", valueInObject.getLong("transactionId"));
						object.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_ADVANCE);
						object.put("subTransactionId", 0);
						object.put("description", "balance amount is added as driver advance : "+amount+" For TripSheet Number : "+object.getLong("transactionId"));
						object.put("FromBalanceTransfer", true);
						TripSheetAdvance advance = (TripSheetAdvance) valueInObject.get("advance");
						if(advance != null) {
							TripSheetAdvance advance2=new TripSheetAdvance(); 
							advance2.setAdvanceAmount(amount);
							advance2.setAdvanceRefence("");
							advance2.setTripsheet(advance.getTripsheet());
							advance2.setAdvanceRemarks("balance amount is added as driver advance ");
							advance2.setCreated(new Date());
							advance2.setCompanyId(valueInObject.getInt("companyId"));
							advance2.setCreatedById(advance.getCreatedById());
							advance2.setDriverId(valueInObject.getInt("driverId",0));
							advance2.setAdvancePaidbyId(advance.getAdvancePaidbyId());
							advance2.setAdvancePlaceId(advance.getAdvancePlaceId());
							advance2.setPaymentTypeId(advance.getPaymentTypeId());
							advance2.setFromBalance(true);
							advance2.setPaidDate(new Date(((Timestamp)valueInObject.get("txnTime")).getTime()));
							tripSheetService.addTripSheetAdvance(advance2);
						}
						driverLedger = DriverBL.getDriverLedgerDto(preDriverLedger, object);
						driverLedger.setTxnDateTime(new Date());
						driverLedgerRepository.save(driverLedger);
						valueInObject.put("preDriverLedger", driverLedger);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
}	
	@Override
	public ValueObject deleteDataInDriverLedger(ValueObject valueInObject) throws Exception {
		
		try {
			
			xSync.execute(valueInObject.getLong("driverId",0), () -> {
				DriverLedger				driverLedger				= null;
				DriverTripSheetBalance		driverTripSheetBalance		= null;
				DriverTripSheetBalance		preDriverBalance			= null;
				DriverLedger				preDriverLedger				= null;
				try {
					preDriverBalance	= driverTripSheetBalanceRepository.getDriverTripSheetBalanceByDriverId(valueInObject.getLong("driverId",0), valueInObject.getInt("companyId"));
					preDriverLedger		= driverLedgerRepository.getDriveLedgerByDriverId(valueInObject.getLong("driverId",0),
												valueInObject.getLong("transactionId"), valueInObject.getLong("subTransactionId"),
												valueInObject.getShort("txnTypeId"));
					
					driverLedger			= DriverBL.getDriverLedgerDto(preDriverLedger, valueInObject);
					driverTripSheetBalance	= DriverBL.getDriverTripSheetBalanceDto(preDriverBalance, valueInObject);
					
					driverLedgerRepository.save(driverLedger);
					driverTripSheetBalanceRepository.save(driverTripSheetBalance);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			
			
			return valueInObject;
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	public void updateDriverLedgerWhenDriverUpdated(long tripSheetId,long tripNumber,long driverId,long oldDriverId,Date openingDate) {
		
		try {
			CustomUserDetails userDetails = Utility.getObject(null);
			updateDriverLedgerWhenDriverUpdated(tripSheetId,tripNumber, oldDriverId,userDetails.getCompany_id(),userDetails.getId(),true,openingDate);
			updateDriverLedgerWhenDriverUpdated(tripSheetId,tripNumber,driverId,userDetails.getCompany_id(),userDetails.getId(),false,openingDate);
			tripSheetService.updateDriverInTripSheetAdvance((int)driverId, tripSheetId, userDetails.getCompany_id());
			tripSheetService.updateDriverInTripSheetExpense((int)driverId, tripSheetId, userDetails.getCompany_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void updateDriverLedgerWhenDriverUpdated(long tripSheetId,long tripNumber,long driverId,int companyId,long userId,boolean forOldDriver,Date openingDate) throws Exception {
		List<TripSheetAdvance>  advanceList =	tripSheetService.getTripSheetAdvanceList(tripSheetId, companyId);
		double amount = 0;
		ValueObject	driveLedgerObj;
		if(advanceList != null && !advanceList.isEmpty()){
			ValueObject object   = new ValueObject();
			 object.put("companyId",companyId);
			 object.put("tripsheetId",tripSheetId);
			
			for(TripSheetAdvance advance : advanceList) {
				if(advance.getFromBalance() == null || !advance.getFromBalance())
				amount +=advance.getAdvanceAmount();
				else {
					object.put("tripAdvanceId",advance.getTripAdvanceID());
					tripSheetMobileService.removeTripSheetAdvanceDetails(object);
				}
			}
			if(amount > 0) {
				
			driveLedgerObj= new ValueObject();
			driveLedgerObj.put("companyId", companyId);
			driveLedgerObj.put("userId",userId);
			driveLedgerObj.put("driverId", driverId);
			driveLedgerObj.put("amount",amount);
			driveLedgerObj.put("transactionId",tripSheetId);
			driveLedgerObj.put("txnTime",new Timestamp(openingDate.getTime()));
			if (forOldDriver) {
				driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_ADVANCE_REMOVED);
				driveLedgerObj.put("description", "All advance Removed as Driver changed : " + amount+ " , TripSheet Number : " + tripNumber);
			} else {
				driveLedgerObj.put("tripCreateEntry",true);
				driveLedgerObj.put("advance",advanceList.get(0));
				driveLedgerObj.put("description", "All advance added as Driver changed : " + amount+ ", TripSheet Number : " + tripNumber);
				driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_ADVANCE);
			}
			addDriverLedgerDetails(driveLedgerObj);
			tripSheetService.UPDATE_TOTALADVANCE_IN_TRIPSHEET_TRIPSHEETADVANCE_ID(tripSheetId, companyId);
			}
		}
		
		List<TripSheetExpense>  expenseList= tripSheetService.getTripSheetExpenseList(tripSheetId, companyId);
		if(expenseList != null && !expenseList.isEmpty()) {
			amount =0.0;
			for(TripSheetExpense expense : expenseList) {
				amount +=expense.getExpenseAmount();
			}
			driveLedgerObj	= new ValueObject();
			driveLedgerObj.put("companyId",companyId);
			driveLedgerObj.put("userId",userId);
			driveLedgerObj.put("driverId", driverId);
			driveLedgerObj.put("amount",amount);
			driveLedgerObj.put("transactionId",tripSheetId);
			driveLedgerObj.put("txnTime",new Timestamp(openingDate.getTime()));
			if (forOldDriver) {
				driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_EXPENSE_REMOVED);
				driveLedgerObj.put("description", "All advance Removed as Driver changed : " + amount+ " , TripSheet Number : " + tripNumber);
			} else {
				driveLedgerObj.put("description", "All advance added as Driver changed : " + amount+ " , TripSheet Number : " + tripNumber);
				driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_EXPENSE);
			}
			
			addDriverLedgerDetails(driveLedgerObj);
		}
		
	}

	
	@Override
	public void updateDriverLedgerWhenTripDeleted(long tripSheetId ,long driverId,long tripNumber) throws Exception {
		CustomUserDetails userDetails = Utility.getObject(null);
		List<TripSheetAdvance>  advanceList =	tripSheetService.getTripSheetAdvanceList(tripSheetId, userDetails.getCompany_id());
		double amount = 0;
		ValueObject	driveLedgerObj;
		if(advanceList != null && !advanceList.isEmpty()){
			for(TripSheetAdvance advance : advanceList) {
				if(advance.getFromBalance() == null || !advance.getFromBalance())
				amount +=advance.getAdvanceAmount();
			}
			driveLedgerObj= new ValueObject();
			driveLedgerObj.put("companyId", userDetails.getCompany_id());
			driveLedgerObj.put("userId",userDetails.getId());
			driveLedgerObj.put("driverId", driverId);
			driveLedgerObj.put("amount",amount);
			driveLedgerObj.put("transactionId",tripSheetId);
			driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_ADVANCE_REMOVED);
			driveLedgerObj.put("description", "All advance Removed of : "+  amount+" as tripsheet deleted, TripSheet Number : "+tripNumber);
			addDriverLedgerDetails(driveLedgerObj);
			
		}
		
		List<TripSheetExpense>  expenseList= tripSheetService.getTripSheetExpenseList(tripSheetId, userDetails.getCompany_id());
		if(expenseList != null && !expenseList.isEmpty()) {
			amount =0.0;
			for(TripSheetExpense expense : expenseList) {
				amount +=expense.getExpenseAmount();
			}
			driveLedgerObj	= new ValueObject();
			driveLedgerObj.put("companyId", userDetails.getCompany_id());
			driveLedgerObj.put("userId",userDetails.getId());
			driveLedgerObj.put("driverId", driverId);
			driveLedgerObj.put("amount",amount);
			driveLedgerObj.put("transactionId",tripSheetId);
			driveLedgerObj.put("txnType", DriverLedgerTypeConstant.TRIPSHEET_EXPENSE_REMOVED);
			//driveLedgerObj.put("subTransactionId", tripSheetAdvance.getTripAdvanceID());
			driveLedgerObj.put("description", "All Expense Removed of : "+  amount+" as tripsheet deleted, TripSheet Number : "+tripSheetId);
			addDriverLedgerDetails(driveLedgerObj);
			
		}
		
	}

	
	@Override
	public ValueObject getDriverLedgertReportList(ValueObject valueObject) throws Exception  {
		String DATE_RANGE = valueObject.getString("dateRange");
		int driverId   = valueObject.getInt("driverId");
		List<DriverLedgerDto>  driverLedgerlist = null;
		CustomUserDetails userDetails = null;
		try
		{
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(DATE_RANGE != " " && DATE_RANGE != null)
			{
				StringBuilder query = new StringBuilder();
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange =null;
				
				From_TO_DateRange = DATE_RANGE.split(" to ");
				
				try	{
					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1] + DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				if(driverId >0 ){
					query.append(" DL.driverId = "+ driverId + " ") ;
				}
				query.append(" AND DL.txnDateTime BETWEEN '"+  dateRangeFrom + "' AND '"+ dateRangeTo +"' ");
				driverLedgerlist = getDriverLedgerReportList(query, userDetails.getCompany_id());
				
				valueObject.put("DATE_RANGE", DATE_RANGE);
				
				if(driverLedgerlist != null) {
					Collections.sort(driverLedgerlist, Comparator.comparing(DriverLedgerDto::getTxnDateTime).reversed());
				}
				if(driverLedgerlist != null){
					valueObject.put("DriverName", driverLedgerlist.get(0).getDriverName());
					for(int i=0; i<driverLedgerlist.size();i++ ){
						if(i==0)
							valueObject.put("ClosingBal", driverLedgerlist.get(i).getClosingBalance());
						if(i == driverLedgerlist.size()-1) 
							valueObject.put("OpeninngBal", driverLedgerlist.get(i).getOpeningBalance());
					}
				}
				valueObject.put("DriverLedgerList", driverLedgerlist);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return valueObject;
	}
	
	@Override
	@Transactional
	public List<DriverLedgerDto> getDriverLedgerReportList(StringBuilder query, Integer company_id) throws Exception {
		// TODO Auto-generated method stub
		List<DriverLedgerDto> driverLedgerList = null;
		System.err.println("inside service ");
		TypedQuery<Object[]> queryt = null;
		queryt = entityManager.createQuery(
				" SELECT DL.driverLedgerId, DL.driverId, DL.transactionAmount, DL.openingBalance, "
				+ " DL.closingBalance, DL.txnTypeId, DL.txnDateTime, DL.description, DL.transactionAmount, D.driver_firstname, "
				+ " ,D.driver_Lastname, D.driver_fathername, TS.tripSheetID, TS.tripSheetNumber FROM DriverLedger AS DL "
				+ " INNER JOIN Driver D ON  D.driver_id = DL.driverId "
				+ " LEFT JOIN TripSheet TS ON TS.tripSheetID = DL.transactionId "
				+ " WHERE DL.companyId = "+ company_id + " AND " + query + " AND DL.markForDelete = 0 "
				,Object[].class);
		List<Object[]> results = null;
		results = queryt.getResultList();
		
		if(results != null && !results.isEmpty()){
			driverLedgerList = new ArrayList<DriverLedgerDto>();
			DriverLedgerDto  dto ;
			for(Object[] result : results) {
				dto = new DriverLedgerDto();
				dto.setDriverLedgerId((Long) result[0]);
				dto.setDriverId((Long) result[1]);
				dto.setOpeningBalance((Double) result[3]);
				dto.setClosingBalance((Double) result[4]);
				dto.setTxnTypeId((short) result[5]);
				dto.setTxnTypeStr(DriverLedgerTypeConstant.DriverLedgerConstatName((short)result[5]));
				dto.setTxnDateTime((Date) result[6]);
				dto.setTxnDateStr(DateTimeUtility.getStringDateFromDate((Date) result[6],"dd-MM-yyyy"));
				dto.setDescription((String) result[7]);
				if((Double)result[8] > 0)
					dto.setCreditAmount((Double) result[8]);
				if((Double)result[8] < 0)
					dto.setDebitAmount((Double) result[8]);
				dto.setDriverName(result[9] + " "+ result[10] + " "+ result[11]);
				if(result[13]!=null)
					dto.setTransactionNo(result[13]+"");
				else
					dto.setTransactionNo("");
				dto.setTripsheetId((Long) result[12]);
				driverLedgerList.add(dto);
			}
		}
	return driverLedgerList;
   }
}
