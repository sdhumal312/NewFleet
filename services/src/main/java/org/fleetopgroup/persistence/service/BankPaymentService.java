package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.security.auth.login.Configuration;
import javax.transaction.Transactional;

import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PaymentTypeConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.BankPaymentBL;
import org.fleetopgroup.persistence.dao.BankPaymentHistoryRepository;
import org.fleetopgroup.persistence.dao.BankPaymentRepository;
import org.fleetopgroup.persistence.dao.BankPaymentTxnCheckerRepository;
import org.fleetopgroup.persistence.dao.BankPaymetDetailsToIvRepository;
import org.fleetopgroup.persistence.dao.CashPaymentStatementRepository;
import org.fleetopgroup.persistence.dao.CompanyMapperRepository;
import org.fleetopgroup.persistence.dto.BankPaymentDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.BankPayment;
import org.fleetopgroup.persistence.model.BankPaymentTxnChecker;
import org.fleetopgroup.persistence.model.BankPaymetDetailsToIv;
import org.fleetopgroup.persistence.model.CompanyMapper;
import org.fleetopgroup.persistence.model.TripSheetAdvance;
import org.fleetopgroup.persistence.model.TripSheetExpense;
import org.fleetopgroup.persistence.model.TripSheetIncome;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IBankService;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.antkorwin.xsync.XSync;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@Service
public class BankPaymentService implements IBankPaymentService{

	@PersistenceContext EntityManager entityManager;
	@Autowired BankPaymentRepository bankPaymentRepository;
	@Autowired BankPaymentBL bankPaymentBL;
	@Autowired BankPaymentHistoryRepository bankPaymentHistoryRepository;
	@Autowired BankPaymetDetailsToIvRepository bankPaymetDetailsToIvRepository;
	@Autowired BankPaymentTxnCheckerRepository bankPaymentTxnCheckerRepository;
	@Autowired IUserProfileService userProfileService;
	@Autowired ICompanyConfigurationService companyConfigurationService;
	@Autowired ICashPaymentService	cashPaymentService;
	@Autowired CashPaymentStatementRepository	cashPaymentStatementRepo;
	@Autowired CompanyMapperRepository companyMapperRepository;
	@Autowired private XSync<Integer> 			xSync;
	@Autowired private ITripSheetService		tripSheetService;
	@Autowired private IBankService				bankService;
	@Autowired private 	IBankPaymentService		bankPaymentService;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
	


	@Transactional
	public BankPayment save(BankPayment bankPayment) {
		return bankPaymentRepository.save(bankPayment);
	}

	@Transactional
	public void saveBankPayment(BankPayment bankPayment) {
		bankPaymentRepository.save(bankPayment);
	}
	
	@Override
	@Transactional
	public void addBankPaymentDetailsFromValueObject(ValueObject object) {
		try {
			if(object.getLong("bankAccountId", 0) > 0) {
			BankPayment bankPayment = bankPaymentBL.prepareBankPaymentFromValueObject(object);
		
				UserProfileDto userProfile = userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(object.getLong("userId", 0L));
				if (userProfile.getBranch_id() != null)
					bankPayment.setBranchId(userProfile.getBranch_id());
			
			bankPayment = save(bankPayment);
			if(bankPayment.getAmount() > 0) {
			String query = " AND B.bankPaymentId =" + bankPayment.getBankPaymentId() + " ";
			List<BankPaymentDto> bankPaymentDtoList = getBankPaymentDetails(query);
			boolean fromIncome= object.getBoolean("fromIncome");
			bankPaymentDtoList.forEach(e -> {
				if(fromIncome)
					e.setIncomeAmount(e.getAmount());
				else
					e.setExpenseAmount(e.getAmount());
			});
			if(!(boolean) object.getBoolean("stopIvCargoCashBookEntries",false))
				saveBankStateMentToIv(bankPaymentDtoList);
			
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void addBankPaymentDetailsAndSendToIv(BankPaymentDto bankPaymentDto,ValueObject object) throws Exception {
		try {
			short currentPaymentTypeId= object.getShort("currentPaymentTypeId", (short)0);
			bankPaymentDto.setBankPaymentTypeId(currentPaymentTypeId);
			bankPaymentDto.setCreatedBy(object.getLong("userId", 0L));
			bankPaymentDto.setCompanyId(object.getInt("companyId"));
			bankPaymentDto.setModuleId(object.getLong("moduleId",0));
			bankPaymentDto.setModuleIdentifier(object.getShort("moduleIdentifier", (short)0));
			bankPaymentDto.setAmount(object.getDouble("amount",0.0));
			bankPaymentDto.setModuleNo(object.getLong("moduleNo",0));
			UserProfileDto userProfile= userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(object.getLong("userId", 0L));
			if(userProfile.getBranch_id() != null)
				bankPaymentDto.setBranchId(userProfile.getBranch_id());
			BankPayment bankPayment=addBankPaymentDetails(bankPaymentDto);
			if(bankPaymentDto.getAmount() > 0) {
			String query = " AND B.bankPaymentId =" + bankPayment.getBankPaymentId()+ " ";
			List<BankPaymentDto> bankPaymentDtoList=getBankPaymentDetails(query);
			bankPaymentDtoList.forEach(e->{
				e.setExpenseAmount(e.getAmount());	
			});
			saveBankStateMentToIv(bankPaymentDtoList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void updatePaymentDetailsFromValuObject(ValueObject object) throws Exception {
		CustomUserDetails 			userDetails			= null;
		HashMap<String, Object> 	configuration		= null;
		
		userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		configuration 	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
		
		if((boolean)configuration.get("fuleEntryPaymentOptions"))
		{
			
			if(object.getLong("oldpaidByBranchId") <=0 && object.getLong("paidByBranchId")>0 && object.getShort("currentPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
				cashPaymentService.saveCashPaymentSatement(object);
			}
			else if(object.getLong("oldpaidByBranchId")>0 && object.getLong("paidByBranchId")<=0 && object.getShort("currentPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
				cashPaymentService.deleteCashStateMentEntry(object.getLong("moduleId",0), object.getShort("moduleIdentifier", (short)0), object.getInt("companyId"), object.getLong("userId", 0L),false);
			}
			else if(object.getLong("oldpaidByBranchId") == object.getLong("paidByBranchId") && object.getShort("currentPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
				cashPaymentService.updateCashPaymentStatement(object);
			}
			else if (object.getShort("oldPaymentTypeId", (short) 0) > 0 && object.getShort("oldPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CASH && object.getShort("currentPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
				cashPaymentService.updateCashPaymentStatement(object);
			}
			else if( (object.getShort("oldPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT) && object.getShort("currentPaymentTypeId", (short)0) == PaymentTypeConstant.PAYMENT_TYPE_CASH){
				cashPaymentService.saveCashPaymentSatement(object);
			}
			else if(object.getShort("oldPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CASH && object.getShort("currentPaymentTypeId", (short)0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT ) {
				cashPaymentService.deleteCashStateMentEntry(object.getLong("moduleId",0), object.getShort("moduleIdentifier", (short)0), object.getInt("companyId"), object.getLong("userId", 0L),false);
			}
			else if(object.getShort("oldPaymentTypeId", (short) 0) > 0 && object.getShort("oldPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CASH && (object.getShort("currentPaymentTypeId", (short) 0) != PaymentTypeConstant.PAYMENT_TYPE_CASH && object.getShort("currentPaymentTypeId", (short) 0) != PaymentTypeConstant.PAYMENT_TYPE_CREDIT))	
			{
				cashPaymentService.deleteCashStateMentEntry(object.getLong("moduleId",0), object.getShort("moduleIdentifier", (short)0), object.getInt("companyId"), object.getLong("userId", 0L),false);
				bankPaymentService.addBankPaymentDetailsFromValueObject(object);
			}
			else if(object.getShort("oldPaymentTypeId", (short) 0) > 0 && object.getShort("oldPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && (object.getShort("currentPaymentTypeId", (short) 0) != PaymentTypeConstant.PAYMENT_TYPE_CASH && object.getShort("currentPaymentTypeId", (short) 0) != PaymentTypeConstant.PAYMENT_TYPE_CREDIT))
			{
				bankPaymentService.addBankPaymentDetailsFromValueObject(object);
			}
			else if( (object.getShort("oldPaymentTypeId", (short) 0) > 0 && object.getShort("oldPaymentTypeId", (short) 0) != PaymentTypeConstant.PAYMENT_TYPE_CASH && object.getShort("oldPaymentTypeId", (short) 0) > 0 && object.getShort("oldPaymentTypeId", (short) 0) != PaymentTypeConstant.PAYMENT_TYPE_CREDIT) &&  (object.getShort("currentPaymentTypeId", (short) 0) != PaymentTypeConstant.PAYMENT_TYPE_CASH && object.getShort("currentPaymentTypeId", (short) 0) != PaymentTypeConstant.PAYMENT_TYPE_CREDIT))
			{
				BankPaymentDto bankPaymentDto = bankPaymentBL.prepareBankPaymentDtoFromValueObject(object);
				deleteBankPaymentDetails(bankPaymentDto.getModuleId(), bankPaymentDto.getModuleIdentifier(),object.getInt("companyId"), object.getLong("userId"), false);
				bankPaymentService.addBankPaymentDetailsFromValueObject(object);
			}
			else if( (object.getShort("oldPaymentTypeId", (short) 0) != PaymentTypeConstant.PAYMENT_TYPE_CASH || object.getShort("oldPaymentTypeId", (short) 0) != PaymentTypeConstant.PAYMENT_TYPE_CREDIT )  &&  object.getShort("currentPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CASH)
			{
				BankPaymentDto bankPaymentDto = bankPaymentBL.prepareBankPaymentDtoFromValueObject(object);
				deleteBankPaymentDetails(bankPaymentDto.getModuleId(), bankPaymentDto.getModuleIdentifier(),object.getInt("companyId"), object.getLong("userId"), false);
				cashPaymentService.saveCashPaymentSatement(object);
			}
			else if( (object.getShort("oldPaymentTypeId", (short) 0) != PaymentTypeConstant.PAYMENT_TYPE_CASH || object.getShort("oldPaymentTypeId", (short) 0) != PaymentTypeConstant.PAYMENT_TYPE_CREDIT )  &&  object.getShort("currentPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT)
			{
				BankPaymentDto bankPaymentDto = bankPaymentBL.prepareBankPaymentDtoFromValueObject(object);
				deleteBankPaymentDetails(bankPaymentDto.getModuleId(), bankPaymentDto.getModuleIdentifier(),object.getInt("companyId"), object.getLong("userId"), false);
			}
		}
		else
		{
			if (object.getShort("oldPaymentTypeId", (short) 0) > 0 && object.getShort("oldPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CASH && object.getShort("currentPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
				cashPaymentService.updateCashPaymentStatement(object);
				
			}else if(object.getShort("oldPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && object.getShort("currentPaymentTypeId", (short)0) == PaymentTypeConstant.PAYMENT_TYPE_CASH){
				cashPaymentService.saveCashPaymentSatement(object);
				
			}else if(object.getShort("oldPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CASH && object.getShort("currentPaymentTypeId", (short)0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT ) {
				cashPaymentService.deleteCashStateMentEntry(object.getLong("moduleId",0), object.getShort("moduleIdentifier", (short)0), object.getInt("companyId"), object.getLong("userId", 0L),false);
	
			}else if(object.getShort("oldPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && object.getShort("currentPaymentTypeId", (short)0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT ) {
				//do nothing
			} else {
				BankPaymentDto bankPaymentDto = bankPaymentBL.prepareBankPaymentDtoFromValueObject(object);
				updateBankPaymentDetails(object, bankPaymentDto);
			}
		}
		
		
		
	}
	
	
	@Override
	public void updatePaymentDetails(ValueObject object, BankPaymentDto bankPaymentDto) throws Exception {
		if (object.getShort("oldPaymentTypeId", (short) 0)  > 0 && object.getShort("oldPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CASH && object.getShort("currentPaymentTypeId", (short)0) == PaymentTypeConstant.PAYMENT_TYPE_CASH ) {
			cashPaymentService.updateCashPaymentStatement(object);
		}else if(object.getShort("oldPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && object.getShort("currentPaymentTypeId", (short)0) == PaymentTypeConstant.PAYMENT_TYPE_CASH){
			cashPaymentService.saveCashPaymentSatement(object);
		}else if(object.getShort("oldPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CASH && object.getShort("currentPaymentTypeId", (short)0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT ) {
			cashPaymentService.deleteCashStateMentEntry(object.getLong("moduleId",0), object.getShort("moduleIdentifier", (short)0), object.getInt("companyId"), object.getLong("userId", 0L),false);	
		}else if(object.getShort("oldPaymentTypeId", (short) 0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT && object.getShort("currentPaymentTypeId", (short)0) == PaymentTypeConstant.PAYMENT_TYPE_CREDIT ) {
			//do nothing
		} else {
			bankPaymentDto.setBankPaymentTypeId(object.getShort("currentPaymentTypeId", (short)0));
			bankPaymentDto.setLastUpdatedBy(object.getLong("userId", 0L));
			bankPaymentDto.setCompanyId(object.getInt("companyId"));
			bankPaymentDto.setModuleId(object.getLong("moduleId",0));
			bankPaymentDto.setModuleNo(object.getLong("moduleNo",0));
			bankPaymentDto.setModuleIdentifier(object.getShort("moduleIdentifier", (short)0));
			bankPaymentDto.setAmount(object.getDouble("amount",0.0));
//			if(bankPaymentDto.getChequeTransactionDateStr() != null && !bankPaymentDto.getChequeTransactionDateStr().trim().equals(""))
//			bankPaymentDto.setChequeTransactionDate(DateTimeUtility.getDateFromString(bankPaymentDto.getChequeTransactionDateStr(),DateTimeUtility.DD_MM_YYYY));
			updateBankPaymentDetails(object, bankPaymentDto);
		}

	}

	@Override
	public BankPayment addBankPaymentDetails(BankPaymentDto bankPaymentDto) {
		BankPayment bankPayment = null;
		try {
			bankPayment = bankPaymentBL.prepareBankPayment(bankPaymentDto);
			bankPayment = save(bankPayment);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bankPayment;
	}

	@Transactional
	public void updateBankPaymentDetails(ValueObject object, BankPaymentDto updatedBankPaymentDto) throws Exception {
		short oldPaymentTypeId = object.getShort("oldPaymentTypeId", (short) 0);
		List<Short> paymentTypeIdList = PaymentTypeConstant.getPaymentTypeIdList();
		long userId =object.getLong("userId",0);
		BankPaymentDto currentBankPaymentDto = null;
		List<BankPaymentDto> ivList = null;
		try {
			String query = " AND B.moduleIdentifier =" + updatedBankPaymentDto.getModuleIdentifier() + " AND B.moduleId = "+ updatedBankPaymentDto.getModuleId() + " AND B.companyId = " + updatedBankPaymentDto.getCompanyId() + " ";
			List<BankPaymentDto> currentBankPaymentDtoList = getBankPaymentDetails(query);
			if (currentBankPaymentDtoList != null && !currentBankPaymentDtoList.isEmpty()) {
				currentBankPaymentDto = currentBankPaymentDtoList.get(0);
				updatedBankPaymentDto.setBranchId(currentBankPaymentDto.getBranchId());
				if (paymentTypeIdList.contains(updatedBankPaymentDto.getBankPaymentTypeId())) {
					if (updatedBankPaymentDto.getBankPaymentTypeId() == oldPaymentTypeId) {
						currentBankPaymentDto.setLastUpdatedBy(userId);
						ivList =sendListToIvIfNewAndOldPaymentTypeMatches(currentBankPaymentDto, updatedBankPaymentDto);
					} else {
						updatedBankPaymentDto.setLastUpdatedBy(userId);
						ivList =sendListToIvIfPaymentTypeChanged(currentBankPaymentDto, updatedBankPaymentDto);
					}
					bankPaymentHistoryRepository.save(bankPaymentBL.prepareBankPaymentHistory(currentBankPaymentDto));
				}else if(paymentTypeIdList.contains(oldPaymentTypeId)) {
					
					if(updatedBankPaymentDto.getBankPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)
						removeBankPaymentDetailsIfTransactionChangedToCash(object, updatedBankPaymentDto);
					else
						deleteBankPaymentDetailsIfTransactionDeleted(updatedBankPaymentDto.getModuleId(), updatedBankPaymentDto.getModuleIdentifier(), object.getInt("companyId"),userId,false);
					// ValueObject object, BankPaymentDto updatedBankPaymentDto
				}
			} else if((oldPaymentTypeId == PaymentTypeConstant.PAYMENT_TYPE_CREDIT || oldPaymentTypeId == PaymentTypeConstant.PAYMENT_TYPE_CASH) && paymentTypeIdList.contains(updatedBankPaymentDto.getBankPaymentTypeId())) {
				UserProfileDto userProfile= userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(object.getLong("userId", 0L));
				if(userProfile.getBranch_id() != null)
					updatedBankPaymentDto.setBranchId(userProfile.getBranch_id());
				ivList =sendListToIvIfPayemetTypeChangedToNonCash(updatedBankPaymentDto, userId);
				if(oldPaymentTypeId == PaymentTypeConstant.PAYMENT_TYPE_CASH) {
					cashPaymentService.deleteCashStateMentEntry(updatedBankPaymentDto.getModuleId(), updatedBankPaymentDto.getModuleIdentifier(), updatedBankPaymentDto.getCompanyId(), userId,false);
				}
			}
			if(ivList!=null && !ivList.isEmpty()) {
				saveBankStateMentToIv(ivList);
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@Transactional
	public List<BankPaymentDto> sendListToIvIfNewAndOldPaymentTypeMatches(BankPaymentDto currentBankPaymentDto,BankPaymentDto bankPaymentDto ) {
		BankPayment bankPayment;
		List<BankPaymentDto> ivList=null;
		try {
			bankPayment = bankPaymentBL.prepareBankPayment(currentBankPaymentDto);
			double currentAmount = currentBankPaymentDto.getAmount();
			double updatedAmount = bankPaymentDto.getAmount();
			bankPayment.setAmount(updatedAmount);
			bankPayment.setLastUpdatedOn(new Date());
			currentBankPaymentDto.setRemark(" Amount Updated in "+currentBankPaymentDto.getModuleNo());	
			if (currentAmount == updatedAmount) {
				// do nothing
			} else if (currentAmount > updatedAmount) {
				currentBankPaymentDto.setIncomeAmount(currentAmount - updatedAmount);
				if(currentAmount > 0) {
				currentBankPaymentDto.setTransactionDate(new Date());
				currentBankPaymentDto.setTransactionDateStr(dateFormat.format(new Date()));
				}
				ivList = Arrays.asList(currentBankPaymentDto);
				saveBankPayment(bankPayment);
			} else {
				currentBankPaymentDto.setExpenseAmount(updatedAmount - currentAmount);
				if(currentAmount > 0) {
				currentBankPaymentDto.setTransactionDate(new Date());
				currentBankPaymentDto.setTransactionDateStr(dateFormat.format(new Date()));
				}
				ivList = Arrays.asList(currentBankPaymentDto);
				saveBankPayment(bankPayment);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ivList; 
	}
	@Transactional
	public List<BankPaymentDto> sendListToIvIfPaymentTypeChanged(BankPaymentDto currentBankPaymentDto,BankPaymentDto updatedBankPaymentDto ) {
		BankPayment bankPayment;
		List<BankPaymentDto> ivList=null;
		try {
			Date date = new Date();
			bankPayment = bankPaymentBL.prepareBankPayment(updatedBankPaymentDto);
			bankPayment.setBankPaymentId(currentBankPaymentDto.getBankPaymentId());
			bankPayment.setCreateOn(currentBankPaymentDto.getCreateOn());
			bankPayment.setCreatedBy(currentBankPaymentDto.getCreatedBy());
			bankPayment.setLastUpdatedOn(date);
			currentBankPaymentDto.setIncomeAmount(currentBankPaymentDto.getAmount());
			currentBankPaymentDto.setRemark(" Income entry for Payment Typed changed : ");
			currentBankPaymentDto.setTransactionDate(date);
			currentBankPaymentDto.setTransactionDateStr(dateFormat.format(date));
			double updatedAmpunt =updatedBankPaymentDto.getAmount();
			bankPayment=save(bankPayment);
			String query = " AND B.bankPaymentId =" + bankPayment.getBankPaymentId()+ " ";
			List<BankPaymentDto> bankPaymentDtoList=getBankPaymentDetails(query);
			updatedBankPaymentDto = bankPaymentDtoList.get(0);
			updatedBankPaymentDto.setExpenseAmount(updatedAmpunt);
			ivList = Arrays.asList(currentBankPaymentDto,updatedBankPaymentDto);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return ivList; 
	}
	
	@Transactional
	public List<BankPaymentDto> sendListToIvIfPayemetTypeChangedToNonCash(BankPaymentDto bankPaymentDto,long userId ) {
		BankPayment bankPayment;
		List<BankPaymentDto> ivList=null;
		try {
			bankPayment = bankPaymentBL.prepareBankPayment(bankPaymentDto);
			bankPayment.setCreatedBy(userId);
			bankPayment = save(bankPayment);
			String query = " AND B.bankPaymentId ="+bankPayment.getBankPaymentId();
			List<BankPaymentDto> currentBankPaymentDtoList=getBankPaymentDetails(query);
			if(currentBankPaymentDtoList != null && !currentBankPaymentDtoList.isEmpty()) {
				BankPaymentDto currentBankPaymentDto = currentBankPaymentDtoList.get(0);
				currentBankPaymentDto.setExpenseAmount(currentBankPaymentDto.getAmount());
				ivList = Arrays.asList(currentBankPaymentDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ivList; 
	}
	
	@Transactional
	public void saveBankStateMentToIv(List<BankPaymentDto> bankPaymentDtoList) throws Exception{
		try {
			List<BankPaymetDetailsToIv> bankPaymetDetailsToIvList = bankPaymetDetailsToIvRepository.saveAll(bankPaymentBL.prepareBankPaymetDetailsToIvList(bankPaymentDtoList)) ;
			new Thread(()->cashPaymentService.sendCashlessEntryForOnlinesTransactions(bankPaymetDetailsToIvList)).start();;
			List<BankPaymentTxnChecker> paymentChecker= new ArrayList<>();
			bankPaymetDetailsToIvList.forEach((e)->{
				BankPaymentTxnChecker bankPaymentTxnChecker = new BankPaymentTxnChecker();
				bankPaymentTxnChecker.setBankPaymetDetailsToIvId(e.getBankPaymetDetailsToIvId());
				bankPaymentTxnChecker.setCreatedOn(LocalDateTime.now());
				paymentChecker.add(bankPaymentTxnChecker);
			});
			bankPaymentTxnCheckerRepository.saveAll(paymentChecker);
			sendBankStateMentToIv(bankPaymetDetailsToIvList);
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public void sendBankStateMentToIv(List<BankPaymetDetailsToIv> ivList) {

		try {
				if (ivList == null)
					ivList = runShedulerMethodForBankStatement();
				bankStateMentDataSendingToIv(ivList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void bankStateMentDataSendingToIv(List<BankPaymetDetailsToIv> ivList) {
		if (ivList != null && !ivList.isEmpty()) {
			Map<Integer, List<BankPaymetDetailsToIv>> bankMap = ivList.parallelStream()
					.collect(Collectors.groupingBy(BankPaymetDetailsToIv::getCompanyId));
			try {
				for (Map.Entry<Integer, List<BankPaymetDetailsToIv>> details : bankMap.entrySet()) {
					CompanyMapper companyMapper = companyMapperRepository
							.findByAccountGroupId(details.getKey());
					if (companyMapper != null) {
						xSync.execute(details.getKey(), () -> {
							HashMap<String, Object> configuration;
							try {
								configuration = companyConfigurationService.getCompanyConfiguration(
										companyMapper.getFleetCompanyId(),
										PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
								String url = (String) configuration.getOrDefault("bankPaymentStatementIvPath", null);
								Gson gson = new Gson();
								HttpResponse<String> response;
								response = Unirest.post(url).header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
										.body(gson.toJson(ivList)).asString();

								if (response.getStatus() == 200) {
									JSONObject object = new JSONObject(response.getBody());
									System.err.println("Response Obj BankStatement " + object);
									if (object.get("successList") != null) {
										JSONArray successArray = (JSONArray) object.get("successList");
										if (successArray != null && successArray.length() > 0) {
											List<Long> successList = new ArrayList<>();
											for (int i = 0; i < successArray.length(); i++) {
												if (successArray.get(i) != null)
													successList.add((long) (int) successArray.get(i));
											}
											if (!successList.isEmpty())
												bankPaymentTxnCheckerRepository
														.updateBankPaymentTxnCheckerList(successList);
										}
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
						Thread.sleep(700);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<BankPaymetDetailsToIv>  runShedulerMethodForBankStatement() throws Exception {
		try {
			List<BankPaymetDetailsToIv> bankPaymetDetailsToIvList=null;
			List<Long> bankTransactionList = bankPaymentTxnCheckerRepository.getNonSyncTxnList(LocalDateTime.now().minusMinutes(5));
		if(bankTransactionList != null && !bankTransactionList.isEmpty()) {
			bankTransactionList=bankTransactionList.stream().limit(300).collect(Collectors.toList());	
			bankPaymetDetailsToIvList = bankPaymetDetailsToIvRepository.getBankPaymentDetailsList(bankTransactionList);
		}
		return bankPaymetDetailsToIvList;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public BankPaymentDto getBankPaymentDetailsDto(Long moduleId,short moduleIdentifier) {
		CustomUserDetails userDetails = Utility.getObject(null);
		BankPaymentDto bankPaymentDto = new BankPaymentDto() ;
		String query = " AND B.moduleId = "+moduleId+" AND B.moduleIdentifier="+moduleIdentifier+" AND B.companyId ="+userDetails.getCompany_id()+" ";
		List<BankPaymentDto> bankPaymentList =getBankPaymentDetails(query);
		if(!bankPaymentList.isEmpty())
			bankPaymentDto = bankPaymentList.get(0);
		return bankPaymentDto;
	} 

	public List<BankPaymentDto> getBankPaymentDetails(String condition) {

		List<BankPaymentDto> bankPaymentDtoList = new ArrayList<>();
		try {

			TypedQuery<Object[]> query = entityManager
					.createQuery(" SELECT B.bankPaymentId,B.moduleId,B.moduleIdentifier,B.paymentTypeId"
							+ " ,B.branchId,B.companyId,B.transactionNumber,B.transactionDate,B.bankAccountId,B.upiId"
							+ " ,B.createOn,B.createdBy,B.lastUpdatedOn,B.lastUpdatedBy,B.amount"
							+ " ,B.mobileNumber,B.payerName,B.chequeGivenBy,BA.accountNumber,B.partyBankId"
							+ " ,B.branchId,B.partyAccountNumber,B.moduleNo,BMM.ivBranchId,CM.ivGroupId,BM.bankName,B.chequeTransactionDate "
							+ " FROM BankPayment as B"
							+ " INNER JOIN BankAccount as BA on BA.bankAccountId = B.bankAccountId "
							+ " LEFT JOIN BankMaster as BM on BM.bankId = B.partyBankId "
							+ " LEFT JOIN BranchMapper as BMM on BMM.fleetBranchId = B.branchId "
							+ " LEFT JOIN CompanyMapper as CM on CM.fleetCompanyId = B.companyId "
							+ " WHERE B.markForDelete=0 " + condition + " ", Object[].class);
			List<Object[]> resutlList = query.getResultList();
			if (resutlList != null && !resutlList.isEmpty()) {
				BankPaymentDto dto = null;
				for (Object[] result : resutlList) {
					dto = new BankPaymentDto();
					dto.setBankPaymentId((Long) result[0]);
					dto.setModuleId((Long) result[1]);
					dto.setModuleIdentifier((short) result[2]);
					dto.setBankPaymentTypeId((short) result[3]);
					dto.setBranchId( (Integer) result[4]);
					dto.setCompanyId((Integer) result[5]);
					dto.setTransactionNumber((String) result[6]);
					dto.setTransactionDate((Date) result[7]);
					if(dto.getTransactionDate() != null)
						dto.setTransactionDateStr(dateFormat.format(dto.getTransactionDate()));
					dto.setBankAccountId((Long) result[8]);
					dto.setUpiId((String) result[9]);
					dto.setCreateOn((Date) result[10]);
					dto.setCreatedBy((Long) result[11]);
					dto.setLastUpdatedOn((Date) result[12]);
					dto.setLastUpdatedBy((Long) result[13]);
					dto.setAmount((Double) result[14]);
					dto.setMobileNumber((String) result[15]);
					dto.setPayerName((String) result[16]);
					dto.setChequeGivenBy((String) result[17]);
					dto.setBankAccountNumber((String) result[18]);
					dto.setPartyBankId((Long) result[19]);
					dto.setBranchId((Integer) result[20]);
					dto.setPartyAccountNumber((String) result[21]);
					dto.setModuleNo((Long) result[22]);
					if(result[23] != null)
					dto.setIvBranchId((Long)result[23]);
					dto.setIvGroupId((Integer) result[24]);
					dto.setModuleNoStr(ModuleConstant.getNumberPrefixByModuleIdentfier(dto.getModuleIdentifier())+"-"+dto.getModuleNo());
					dto.setDetails(ModuleConstant.getModuleNameByModuleIdentifier(dto.getModuleIdentifier()));
					dto.setBankAccount((String) result[25]);
					
					if(result[26] != null)
					dto.setChequeTransactionDate((Date) result[26]);
					
					if(dto.getChequeTransactionDate() != null)
					dto.setChequeTransactionDateStr(dateFormat.format(dto.getChequeTransactionDate()));
					
					bankPaymentDtoList.add(dto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bankPaymentDtoList;
	}

	@Transactional
	@Override
	public void deleteBankPaymentDetailsIfTransactionDeleted(long transactionId, short moduleIdentifier, int companyId,
			long userId,boolean fromIncome) {
		try {
			HashMap<String, Object> companyConfiguration;
			companyConfiguration = companyConfigurationService.getCompanyConfiguration(companyId,PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if ((boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails", false)) {
				deleteBankPaymentDetails(transactionId, moduleIdentifier, companyId, userId,fromIncome);
				cashPaymentService.deleteCashStateMentEntry(transactionId, moduleIdentifier, companyId, userId,fromIncome);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void deleteBankPaymentDetails(long transactionId, short moduleIdentifier, int companyId,
			long userId,boolean fromIncome) throws Exception {
	
			String query = " AND B.moduleIdentifier =" + moduleIdentifier + " AND B.moduleId = " + transactionId+ " AND B.companyId = " + companyId + " ";
			List<BankPaymentDto> currentBankPaymentDtoList = getBankPaymentDetails(query);
			BankPaymentDto bankPaymentDto;
			if (currentBankPaymentDtoList != null && !currentBankPaymentDtoList.isEmpty()) {
				bankPaymentDto = currentBankPaymentDtoList.get(0);
				bankPaymentRepository.deleteBankPayment(bankPaymentDto.getBankPaymentId(), companyId, userId,new Date());
				if (bankPaymentDto.getAmount() > 0) {
					if(fromIncome) {
					bankPaymentDto.setExpenseAmount(bankPaymentDto.getAmount());
					bankPaymentDto.setRemark("Expense Entry as CashLess transaction deleted :");	
					}else{
					bankPaymentDto.setIncomeAmount(bankPaymentDto.getAmount());
					bankPaymentDto.setRemark("Income Entry as CashLess transaction deleted :");
					}						
					Date date = new Date();
					bankPaymentDto.setTransactionDateStr(dateFormat.format(date));
					bankPaymentDto.setTransactionDate(date);
					saveBankStateMentToIv(Arrays.asList(bankPaymentDto));
				}
			}
		}
	
	
	@Transactional
	public void removeBankPaymentDetailsIfTransactionChangedToCash(ValueObject object,
			BankPaymentDto updatedBankPaymentDto) {
		try {
			deleteBankPaymentDetails(updatedBankPaymentDto.getModuleId(), updatedBankPaymentDto.getModuleIdentifier() , object.getInt("companyId"), object.getLong("userId",0),object.getBoolean("fromIncome", false));
			cashPaymentService.saveCashPaymentSatement(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void deleteAllStatmentDetailsFromTrip(long tripSheetId,int companyId,long userId) {
		
		try {
			HashMap<String, Object> companyConfiguration;
			companyConfiguration = companyConfigurationService.getCompanyConfiguration(companyId,PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			if ((boolean) companyConfiguration.getOrDefault("allowBankPaymentDetails", false)) {
				 List<TripSheetExpense> tripExpenseList	= tripSheetService.getTripSheetExpenseList(tripSheetId, companyId);
				 List<TripSheetAdvance> tripAdvanceList	= tripSheetService.getTripSheetAdvanceList(tripSheetId, companyId);
				 List<TripSheetIncome>	tripIncomeList	= tripSheetService.findAllTripSheetIncomeList(tripSheetId, companyId);

				 if(tripExpenseList != null && !tripExpenseList.isEmpty())
				 for(TripSheetExpense tripExpense:tripExpenseList) 
					 if(tripExpense.getPaymentTypeId()  != null)
					 if(tripExpense.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH){
						 //	do nothing // cashPaymentService.deleteCashStateMentEntry(tripExpense.getTripExpenseID(), ModuleConstant.TRIP_EXPENSE, companyId, userId,false);
					 }
					 else
						 deleteBankPaymentDetails(tripExpense.getTripExpenseID(),  ModuleConstant.TRIP_EXPENSE, companyId, userId,false);
				 
				 if(tripAdvanceList!= null && !tripAdvanceList.isEmpty())
				 for(TripSheetAdvance tripSheetAdvance:tripAdvanceList)
					 if((tripSheetAdvance.getFromBalance() == null || !tripSheetAdvance.getFromBalance())&& tripSheetAdvance.getPaymentTypeId() != null)
					 if(tripSheetAdvance.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)
						 cashPaymentService.deleteCashStateMentEntry(tripSheetAdvance.getTripAdvanceID(), ModuleConstant.TRIP_ADVANCE, companyId, userId,false);
					 else
						 deleteBankPaymentDetails(tripSheetAdvance.getTripAdvanceID(),  ModuleConstant.TRIP_ADVANCE, companyId, userId,false); 
				 
				 
				 if(tripIncomeList!= null && !tripIncomeList.isEmpty())
				 for(TripSheetIncome tripSheetIncome:tripIncomeList) 
					 if(tripSheetIncome.getPaymentTypeId() != null)
					 if(tripSheetIncome.getPaymentTypeId() == PaymentTypeConstant.PAYMENT_TYPE_CASH)
						 cashPaymentService.deleteCashStateMentEntry(tripSheetIncome.getTripincomeID(), ModuleConstant.TRIP_INCOME, companyId, userId,true);
					 else
						 deleteBankPaymentDetails(tripSheetIncome.getTripincomeID(),  ModuleConstant.TRIP_INCOME, companyId, userId,true); 
				 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void delete_Bank_PaymentDetails(Long fuel_id, short fuelEntry, Integer company_id, long id)
			throws Exception {
		// TODO Auto-generated method stub
		deleteBankPaymentDetails(fuel_id,fuelEntry,company_id,id,false);
	}
	
	
	
}
