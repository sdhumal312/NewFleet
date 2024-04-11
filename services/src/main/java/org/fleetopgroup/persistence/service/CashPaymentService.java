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
import javax.transaction.Transactional;

import org.apache.commons.lang.SerializationUtils;
import org.fleetopgroup.constant.IVPaymentTypeMapper;
import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.dao.CashPaymentRepository;
import org.fleetopgroup.persistence.dao.CashPaymentStatementRepository;
import org.fleetopgroup.persistence.dao.CashPaymentTxnCheckerRepository;
import org.fleetopgroup.persistence.dao.CompanyMapperRepository;
import org.fleetopgroup.persistence.dto.CashPaymentDto;
import org.fleetopgroup.persistence.dto.UserProfileDto;
import org.fleetopgroup.persistence.model.BankPaymetDetailsToIv;
import org.fleetopgroup.persistence.model.CashPayment;
import org.fleetopgroup.persistence.model.CashPaymentStatement;
import org.fleetopgroup.persistence.model.CashPaymentTxnChecker;
import org.fleetopgroup.persistence.model.CompanyMapper;
import org.fleetopgroup.persistence.serviceImpl.ICashPaymentService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.antkorwin.xsync.XSync;
import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

@Service
public class CashPaymentService implements ICashPaymentService {
	@Autowired CashPaymentRepository  cashPaymentRepo;
	@Autowired IUserProfileService    userProfileService;
	@Autowired CashPaymentStatementRepository cashPaymentStatementRepository;
	@Autowired CashPaymentTxnCheckerRepository cashPaymentTxnCheckerRepository;
	@Autowired ICompanyConfigurationService	   companyConfigurationService;
	@Autowired CompanyMapperRepository		   companyMapperRepository;
	@Autowired private XSync<Integer> 			xSync;
	SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtility.DD_MM_YY_HH_MM_SS);
	SimpleDateFormat dateFormat2 = new SimpleDateFormat(DateTimeUtility.DD_MM_YYYY);
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	@Transactional
	public void saveCashPaymentSatement(ValueObject object) throws Exception {
		List<CashPaymentDto>  cashPaymentDtoList = null;
		CashPayment cashPayment = prepareCashPayment(object);
		
		if(object.getBoolean("paidByBranch"))
		{
			cashPayment.setBranchId(object.getInt("paidByBranchId"));
		}
		
		saveCashPayment(cashPayment);
			if(object.getDouble("amount",0.0) > 0) {
				String condition = " AND C.cashPaymentId = "+cashPayment.getCashPaymentId()+" ";
				cashPaymentDtoList = getCashPayment(condition);
				if(cashPaymentDtoList != null && !cashPaymentDtoList.isEmpty()) {
					CashPaymentStatement cashPaymentStatement=getCashStateMentFromCashPayment(cashPaymentDtoList.get(0));
				//cashPaymentStatement.setTransactionDateStr(dateFormat.format(new Date()));
				if (object.getBoolean("fromIncome", false))
					cashPaymentStatement.setIncomeAmount(cashPayment.getAmount());
				else
					cashPaymentStatement.setExpenseAmount(cashPayment.getAmount());
				//cashPaymentStatement.setCreatedBy(object.getLong("userId", 0L));
				if(object.getString("remark") != null)
					cashPaymentStatement.setRemark(object.getString("remark"));
				saveCashPaymentStatement(cashPaymentStatement);
				if(!(boolean) object.getBoolean("stopIvCargoCashBookEntries",false))
					sendCashPaymentStatementToIv(cashPaymentStatement);
			}
		}
	}
	
	@Override
	@Transactional
	public void deleteCashStateMentEntry(long moduleId,short moduleIdentifier,int companyId,long userId,boolean fromIncome){
		
		String condition = " AND C.moduleId = "+moduleId+" AND C.moduleIdentifier ="+moduleIdentifier+" AND C.companyId ="+companyId+"  ";
		List<CashPaymentDto> cashPaymentDtoList = getCashPayment(condition);
		
		if(cashPaymentDtoList != null && !cashPaymentDtoList.isEmpty()) {
		CashPaymentDto cashPayment = cashPaymentDtoList.get(0) ;
		cashPaymentRepo.deleteCashPaymentByPaymentId(cashPayment.getCashPaymentId());
		if(cashPayment.getAmount() > 0) {
		CashPaymentStatement cashStatement = getCashStateMentFromCashPayment(cashPaymentDtoList.get(0));
		if(fromIncome) {
			cashStatement.setExpenseAmount(cashPayment.getAmount());
			cashStatement.setRemark("Expense Entry as Cash transaction deleted ");
		}else {
			cashStatement.setIncomeAmount(cashPayment.getAmount());
			cashStatement.setRemark("Income Entry as Cash transaction deleted ");
		}
		cashStatement.setTransactionDateStr(dateFormat2.format(new Date()));
		//cashStatement.setCreatedOn(new Date());
		//cashStatement.setCreatedBy(userId);
		cashStatement = saveCashPaymentStatement(cashStatement);
		sendCashPaymentStatementToIv(cashStatement);
		}
		}
	}

	@Override
	@Transactional
	public void updateCashPaymentStatement(ValueObject object) throws Exception {
		CashPaymentDto cashPayment = null;
		List<CashPaymentDto> cashPaymentDtoList  = null;
		double oldAmount = 0.0;
		double updatedAmount = object.getDouble("amount", 0.0);
			try {
				
				String condition = " AND C.moduleId = "+object.getLong("moduleId", 0)+" AND C.moduleIdentifier ="+object.getShort("moduleIdentifier", (short) 0)+" AND C.companyId ="+object.getInt("companyId")+"  ";
				cashPaymentDtoList = getCashPayment(condition);
				if (cashPaymentDtoList != null && !cashPaymentDtoList.isEmpty()) {
					cashPayment =cashPaymentDtoList.get(0);
					oldAmount = cashPayment.getAmount();
					if(object.get("paidDate") != null) {
						cashPaymentRepo.updateCashPaymentAmountAndTransactionDate(object.getDouble("amount", 0.0), new Date(),object.getLong("userId", 0L), cashPayment.getCashPaymentId(), object.getInt("companyId"),(Date)object.get("paidDate"));
					}else
						cashPaymentRepo.updateCashPaymentAmount(object.getDouble("amount", 0.0), new Date(),object.getLong("userId", 0L), cashPayment.getCashPaymentId(), object.getInt("companyId"));
					if(object.getBoolean("paidByBranch"))
					{
						cashPaymentRepo.updateCashPaymentBranchId(object.getInt("paidByBranchId"), object.getLong("userId", 0L), cashPayment.getCashPaymentId(), object.getInt("companyId"));
					}
					if(oldAmount != updatedAmount) {
					CashPaymentStatement cashStatement = getCashStateMentFromCashPayment(cashPayment);
					cashStatement.setRemark(" Entry For Amount Updated In : "+cashStatement.getModuleNo());
					if (oldAmount > updatedAmount || oldAmount < updatedAmount) {
						
						if(oldAmount > updatedAmount)
							cashStatement.setIncomeAmount(Utility.round(oldAmount - updatedAmount, 2));
						
						if(oldAmount < updatedAmount) 
							cashStatement.setExpenseAmount(Utility.round(updatedAmount - oldAmount, 2));
						
						cashStatement.setTransactionDateStr(dateFormat2.format(new Date()));
						//cashStatement.setCreatedBy(object.getLong("userId", 0L));
						//cashStatement.setCreatedOn(new Date());
						cashStatement= saveCashPaymentStatement(cashStatement);
						sendCashPaymentStatementToIv(cashStatement);
					}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
	}
	
	public CashPayment prepareCashPayment(ValueObject object) {
		CashPayment cashPayment = new CashPayment();
		cashPayment.setAmount(object.getDouble("amount",0.0));
		cashPayment.setModuleId(object.getLong("moduleId",0));
		cashPayment.setModuleIdentifier(object.getShort("moduleIdentifier", (short)0));
		cashPayment.setCreatedBy(object.getLong("userId", 0L));
		cashPayment.setCreateOn(new Date());
		cashPayment.setCompanyId(object.getInt("companyId",0));
		cashPayment.setModuleNo(object.getLong("moduleNo",0));
		try {
			if(object.get("paidDate") != null)
			cashPayment.setTransactionDate((Date) object.get("paidDate"));
			else
				cashPayment.setTransactionDate(new Date());	
		} catch (Exception e) {
			cashPayment.setTransactionDate(new Date());	
			e.printStackTrace();
		}
		UserProfileDto userProfile= userProfileService.GET_UserProfile_Name_PlaceinBranchname_ByUseremail(object.getLong("userId", 0L));
		if(userProfile.getBranch_id() != null)
			cashPayment.setBranchId(userProfile.getBranch_id());
		
		return cashPayment;
		
	}
	
	public CashPayment saveCashPayment(CashPayment cashPayment) {
		return cashPaymentRepo.save(cashPayment);
	}
	
	public CashPaymentStatement saveCashPaymentStatement(CashPaymentStatement cashPaymentStatement) {
		return cashPaymentStatementRepository.save(cashPaymentStatement);
	}
	
	public void sendCashPaymentStatementToIv(CashPaymentStatement cashPaymentStatement) {
		CashPaymentTxnChecker cashPaymentTxnChecker = new CashPaymentTxnChecker();
		cashPaymentTxnChecker.setCashPaymentStatementId(cashPaymentStatement.getCashPaymentStatementId());
		cashPaymentTxnChecker.setCreatedOn(LocalDateTime.now());
		cashPaymentTxnCheckerRepository.save(cashPaymentTxnChecker);
		sendCashPaymentListToIV(Arrays.asList(cashPaymentStatement));
	}
	

	
	@Override
	public void sendCashPaymentListToIV(List<CashPaymentStatement> cashPaymentStatementList) {
		try {
			if (cashPaymentStatementList == null)
				cashPaymentStatementList = runShedulerForCashMethod();

			cashPaymentDataListSendingToIV(cashPaymentStatementList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void cashPaymentDataListSendingToIV(List<CashPaymentStatement> cashPaymentStatementList) {
		if (cashPaymentStatementList != null) {
			Map<Integer, List<CashPaymentStatement>> cashMap = cashPaymentStatementList.parallelStream()
					.collect(Collectors.groupingBy(CashPaymentStatement::getCompanyId));
			try {
				CompanyMapper companyMapper = null;
				for (Map.Entry<Integer, List<CashPaymentStatement>> details : cashMap.entrySet()) {
					companyMapper = companyMapperRepository.getFleetopCompanyIdByIvGroupId(details.getKey());
					if (companyMapper != null) {
						HashMap<String, Object> configuration = companyConfigurationService.getCompanyConfiguration(
								companyMapper.getFleetCompanyId(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
						Gson gson = new Gson();
						xSync.execute(details.getKey(), () -> {
							String url = (String) configuration.getOrDefault("castStatementIvPath", null);
							HttpResponse<String> response;
							try {
								response = Unirest.post(url).header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
										.body(gson.toJson(cashPaymentStatementList)).asString();
								System.err.println("Response Obj CashStatement " + response.getStatus());
								if (response.getStatus() == 200) {
									JSONObject object = new JSONObject(response.getBody());
									System.err.println("Response Obj CashStatement " + object);
									if (object.get("successList") != null) {
										JSONArray successArray = (JSONArray) object.get("successList");
										if (successArray != null && successArray.length() > 0) {
											List<Long> successList = new ArrayList<>();
											for (int i = 0; i < successArray.length(); i++) {
												if (successArray.get(i) != null)
													successList.add((long) (int) successArray.get(i));
											}
											if(!successList.isEmpty())
											cashPaymentTxnCheckerRepository.updateCashPaymentCheckerList(successList);
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
	
	public List<CashPaymentStatement>  runShedulerForCashMethod() throws Exception {
		try {
			List<CashPaymentStatement>  cashPaymentStatement= null;
			List<Long> cashTxnNonSyncList = cashPaymentTxnCheckerRepository.getNonSyncCashPaymentList(LocalDateTime.now().minusMinutes(20));
		if(cashTxnNonSyncList != null && !cashTxnNonSyncList.isEmpty()) {
			cashTxnNonSyncList=cashTxnNonSyncList.stream().limit(400).collect(Collectors.toList());	
			cashPaymentStatement=	cashPaymentStatementRepository.getCashPaymentList(cashTxnNonSyncList);
		}
		return cashPaymentStatement;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	public CashPaymentStatement getCashStateMentFromCashPayment(CashPaymentDto cashPayment) {
		CashPaymentStatement cashPaymentStatement = new CashPaymentStatement();
		cashPaymentStatement.setModuleId(cashPayment.getModuleId());
		cashPaymentStatement.setModuleIdentifier(cashPayment.getModuleIdentifier());
		//ashPaymentStatement.setTransactionDate();
		cashPaymentStatement.setCompanyId(cashPayment.getIvGroupId());
		if(cashPayment.getIvBranchId() != null)
		cashPaymentStatement.setBranchId((int)(long)cashPayment.getIvBranchId());
		cashPaymentStatement.setModuleNo(ModuleConstant.getNumberPrefixByModuleIdentfier(cashPayment.getModuleIdentifier())+"-"+cashPayment.getModuleNo());
		cashPaymentStatement.setDetails(ModuleConstant.getModuleNameByModuleIdentifier(cashPayment.getModuleIdentifier()));
		if(cashPayment.getTransactionDate() != null)
		cashPaymentStatement.setTransactionDateStr(dateFormat2.format(cashPayment.getTransactionDate()));
		else
			cashPaymentStatement.setTransactionDateStr(dateFormat2.format(new Date()));
		cashPaymentStatement.setPaymentTypeId(IVPaymentTypeMapper.PAYMENT_TYPE_CASH_ID);
		return cashPaymentStatement;
	}
	
	public List<CashPaymentDto> getCashPayment(String condition) {
		List<Object[]> resutlList = null;
		List<CashPaymentDto> cashPaymentDtoList = null;
		try {
			TypedQuery<Object[]> query = entityManager.createQuery(
					"SELECT C.cashPaymentId,C.moduleId,C.moduleNo,C.moduleIdentifier,C.transactionDate,C.amount"
							+ ",C.branchId,C.companyId,BM.ivBranchId,CM.ivGroupId  FROM CashPayment as C"
							+ " LEFT JOIN BranchMapper as BM on BM.fleetBranchId = C.branchId "
							+ " LEFT JOIN CompanyMapper as CM on CM.fleetCompanyId = C.companyId "
							+ " WHERE C.markForDelete=0 " + condition + " ",
					Object[].class);
			resutlList = query.getResultList();

			if (resutlList != null && !resutlList.isEmpty()) {
				cashPaymentDtoList = new ArrayList<>();
				CashPaymentDto cashPaymentDto;
				for (Object[] result : resutlList) {
					cashPaymentDto = new CashPaymentDto();
					cashPaymentDto.setCashPaymentId((Long) result[0]);
					cashPaymentDto.setModuleId((Long) result[1]);
					cashPaymentDto.setModuleNo((Long) result[2]);
					cashPaymentDto.setModuleIdentifier((short) result[3]);
					cashPaymentDto.setTransactionDate((Date) result[4]);
					cashPaymentDto.setAmount((Double) result[5]);
					cashPaymentDto.setBranchId((Integer) result[6]);
					cashPaymentDto.setCompanyId((Integer) result[7]);
					cashPaymentDto.setIvBranchId((Long)result[8]);
					cashPaymentDto.setIvGroupId((Integer) result[9]);
					cashPaymentDtoList.add(cashPaymentDto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cashPaymentDtoList;
	}

	@Override
	public void sendCashlessEntryForOnlinesTransactions(List<BankPaymetDetailsToIv> bankPaymetDetailsToIv) {
		if (bankPaymetDetailsToIv != null && !bankPaymetDetailsToIv.isEmpty()) {
			try {
				List<CashPaymentStatement> cashPaymentStatement = new ArrayList<>();
				CashPaymentStatement cashStatement = null;
				CashPaymentStatement cashStatement2 = null;
				for (BankPaymetDetailsToIv bankPayment : bankPaymetDetailsToIv) {
					cashStatement = new CashPaymentStatement();
					cashStatement.setBankAccount(bankPayment.getBankAccount());
					cashStatement.setBranchId(bankPayment.getBranchId());
					cashStatement.setModuleId(bankPayment.getModuleId());
					cashStatement.setModuleIdentifier(bankPayment.getModuleIdentifier());
					//cashStatement.setTransactionDate(bankPayment.getTransactionDate());
					cashStatement.setCompanyId(bankPayment.getCompanyId());
					cashStatement.setBranchId(bankPayment.getBranchId());
					cashStatement.setModuleNo(bankPayment.getModuleNo());
					cashStatement.setDetails(bankPayment.getDetails());
					cashStatement.setTransactionDateStr(bankPayment.getTransactionDateStr());
					cashStatement.setIncomeAmount(bankPayment.getIncomeAmount());
					cashStatement.setExpenseAmount(bankPayment.getExpenseAmount());
					cashStatement.setPaymentTypeId(bankPayment.getBankPaymentTypeId());
					if(bankPayment.getBankPaymentTypeId() == IVPaymentTypeMapper.PAYMENT_TYPE_CHEQUE_ID)
						cashStatement.setChequeNumber(bankPayment.getTransactionNumber());
						
					cashStatement.setRemark(bankPayment.getRemark());
					cashStatement2 = (CashPaymentStatement) SerializationUtils.clone(cashStatement);
					cashStatement2.setIncomeAmount(bankPayment.getExpenseAmount());
					cashStatement2.setExpenseAmount(bankPayment.getIncomeAmount());
					cashPaymentStatement.add(cashStatement);
					cashPaymentStatement.add(cashStatement2);
				}
				cashPaymentStatement = cashPaymentStatementRepository.saveAll(cashPaymentStatement);

				List<CashPaymentTxnChecker> cashPaymentTxnCheckerList = new ArrayList<>();
				cashPaymentStatement.forEach((e) -> {
					CashPaymentTxnChecker cashPaymentTxnChecker = new CashPaymentTxnChecker();
					cashPaymentTxnChecker.setCashPaymentStatementId(e.getCashPaymentStatementId());
					cashPaymentTxnChecker.setCreatedOn(LocalDateTime.now());
					cashPaymentTxnCheckerList.add(cashPaymentTxnChecker);
				});
				cashPaymentTxnCheckerRepository.saveAll(cashPaymentTxnCheckerList);
				sendCashPaymentListToIV(cashPaymentStatement);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
