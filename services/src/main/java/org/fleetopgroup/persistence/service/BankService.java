package org.fleetopgroup.persistence.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.ModuleConstant;
import org.fleetopgroup.persistence.dao.BankAccountRepository;
import org.fleetopgroup.persistence.dao.BankMasterRepository;
import org.fleetopgroup.persistence.dao.TripSheetAdvanceRepository;
import org.fleetopgroup.persistence.dto.BankAccountDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.TripSheetDto;
import org.fleetopgroup.persistence.model.BankAccount;
import org.fleetopgroup.persistence.model.BankMaster;
import org.fleetopgroup.persistence.model.TripSheetAdvance;
import org.fleetopgroup.persistence.serviceImpl.IBankPaymentService;
import org.fleetopgroup.persistence.serviceImpl.IBankService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BankService implements IBankService{

	private static final int PAGE_SIZE = 25;
	
	@Autowired private BankMasterRepository		bankMasterRepository;
	@Autowired private BankAccountRepository	bankAccountRepository;
	@Autowired private TripSheetAdvanceRepository	advanceRepository;
	@Autowired private ITripSheetService			tripSheetService;
	@Autowired private IBankPaymentService			bankPaymentService;
	
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<BankMaster> getBankList() throws Exception {
		
		return bankMasterRepository.findAll();
	}
	
	@Override
	public void save(BankMaster bankMaster) throws Exception {
			
		bankMasterRepository.save(bankMaster);
	}
	
	@Override
	public List<BankMaster> validateBankName(String bankName) throws Exception {
		return bankMasterRepository.validateBankName(bankName);
	}
	
	@Override
	public List<BankAccountDto> getBankAccountList(Integer pageNumber, Integer companyId) throws Exception {
		try {

			TypedQuery<Object[]> query = entityManager
					.createQuery(" SELECT BA.bankAccountId, BA.bankId, BM.bankName, BA.name, BA.accountNumber, BA.IFSCCode, BA.MICRCode,"
							+ " BA.contactNumber, BA.description "
							+ " FROM BankAccount AS BA "
							+ " INNER JOIN BankMaster BM ON BM.bankId = BA.bankId"
							+ " WHERE BA.companyId = "+companyId+" AND BA.markForDelete = 0", Object[].class);
			
			query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
			query.setMaxResults(PAGE_SIZE);
			List<Object[]> results = query.getResultList();
			List<BankAccountDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<BankAccountDto>();
				BankAccountDto bankAccountDto = null;
				for (Object[] result : results) {
					bankAccountDto = new BankAccountDto();

					bankAccountDto.setBankAccountId((Long) result[0]);
					bankAccountDto.setBankId((Long) result[1]);
					bankAccountDto.setBankName((String) result[2]);
					bankAccountDto.setName((String) result[3]);
					bankAccountDto.setAccountNumber((String) result[4]);
					bankAccountDto.setIFSCCode((String) result[5]);
					bankAccountDto.setMICRCode((String) result[6]);
					bankAccountDto.setContactNumber((String) result[7]);
					bankAccountDto.setDescription((String) result[8]);


					Dtos.add(bankAccountDto);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public Page<BankAccount> getDeployment_Page_BankAccoutn(Integer pageNumber, Integer companyId) {

		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE, Sort.Direction.DESC, "bankAccountId");
		return bankAccountRepository.getDeployment_Page_BankAccount(companyId,  request);
	}
	
	@Override
	public List<BankAccount> validateBankAccount(String bankName, Long bankId, Integer companyId) throws Exception {
		return bankAccountRepository.validateBankAccount(bankName, bankId, companyId);
	}
	
	@Override
	public void save(BankAccount bankAccount) throws Exception {
		bankAccountRepository.save(bankAccount);
	}
	
	@Override
	@Transactional
	public void deleteBankAccount(Long id) throws Exception {
		bankAccountRepository.deleteAccount(id);
	}
	
	@Override
	public List<BankAccountDto> getBankAccountListForDropDown(String term, Integer companyId) throws Exception {
		List<BankAccountDto> dtos= new ArrayList<>();
		try {
			if(term != null && !term.trim().equalsIgnoreCase("") && term.indexOf('\'') != 0 ) {
			TypedQuery<Object[]> query = entityManager
					.createQuery(" SELECT BA.bankAccountId, BA.bankId, BM.bankName, BA.name, BA.accountNumber, BA.IFSCCode, BA.MICRCode,"
							+ " BA.contactNumber, BA.description "
							+ " FROM BankAccount AS BA "
							+ " INNER JOIN BankMaster BM ON BM.bankId = BA.bankId"
							+ " WHERE (lower(BA.name) Like ('%" + term + "%') OR lower(BA.accountNumber) Like ('%" + term + "%') OR lower(BA.IFSCCode) Like ('%" + term + "%')) AND  BA.companyId = "+companyId+" AND BA.markForDelete = 0", Object[].class);
			
			List<Object[]> results = query.getResultList();
			
			if (results != null && !results.isEmpty()) {
				
				BankAccountDto bankAccountDto = null;
				for (Object[] result : results) {
					bankAccountDto = new BankAccountDto();

					bankAccountDto.setBankAccountId((Long) result[0]);
					bankAccountDto.setBankId((Long) result[1]);
					bankAccountDto.setBankName((String) result[2]);
					bankAccountDto.setName((String) result[3]);
					bankAccountDto.setAccountNumber((String) result[4]);
					bankAccountDto.setIFSCCode((String) result[5]);
					bankAccountDto.setMICRCode((String) result[6]);
					bankAccountDto.setContactNumber((String) result[7]);
					bankAccountDto.setDescription((String) result[8]);
					dtos.add(bankAccountDto);
				}
			}
			}
			return dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public List<BankAccountDto> getBankAccountListForDropDownByBankId(String bankId, Integer companyId) throws Exception {
		try {

			TypedQuery<Object[]> query = entityManager
					.createQuery(" SELECT BA.bankAccountId, BA.bankId, BM.bankName, BA.name, BA.accountNumber, BA.IFSCCode, BA.MICRCode,"
							+ " BA.contactNumber, BA.description "
							+ " FROM BankAccount AS BA "
							+ " INNER JOIN BankMaster BM ON BM.bankId = BA.bankId"
							+ " WHERE BA.bankId ="+bankId+" AND  BA.companyId = "+companyId+" AND BA.markForDelete = 0", Object[].class);
			
			List<Object[]> results = query.getResultList();
			List<BankAccountDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<BankAccountDto>();
				BankAccountDto bankAccountDto = null;
				for (Object[] result : results) {
					bankAccountDto = new BankAccountDto();
					bankAccountDto.setBankAccountId((Long) result[0]);
					bankAccountDto.setBankId((Long) result[1]);
					bankAccountDto.setBankName((String) result[2]);
					bankAccountDto.setName((String) result[3]);
					bankAccountDto.setAccountNumber((String) result[4]);
					bankAccountDto.setIFSCCode((String) result[5]);
					bankAccountDto.setMICRCode((String) result[6]);
					bankAccountDto.setContactNumber((String) result[7]);
					bankAccountDto.setDescription((String) result[8]);
					Dtos.add(bankAccountDto);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void insertMissingBankPayment(ValueObject valueObject) throws Exception {
		List<TripSheetAdvance>  missingAdvance		= null;
		Long lowerLimit								= 0L;
		Long upperLimit								= 0L;
		ValueObject bankPaymentValueObject 			= null;
		TripSheetDto			tripSheet				= null;
		try {
			lowerLimit		= 61329L;
			upperLimit		= 61359L;
			
			System.err.println("lowerLimit : "+61329);
			System.err.println("upperLimit : "+61359);
			
			missingAdvance	= advanceRepository.findAllTripSheetAdvanceByIds(lowerLimit, upperLimit);
			
			System.err.println("missingAdvance : "+missingAdvance.size());
			
			if(missingAdvance != null && !missingAdvance.isEmpty()) {
				for (TripSheetAdvance tripSheetAdvance : missingAdvance) {
					tripSheet		= tripSheetService.getTripSheetDetails(tripSheetAdvance.getTripsheet().getTripSheetID(), new CustomUserDetails(tripSheetAdvance.getCompanyId(), tripSheetAdvance.getAdvancePaidbyId()));
					
					bankPaymentValueObject = new ValueObject();
					
					bankPaymentValueObject.put("transactionNumber", "000");
					bankPaymentValueObject.put("payerName", "");
					bankPaymentValueObject.put("upiId", "");
					bankPaymentValueObject.put("mobileNumber", "0000000000");
					bankPaymentValueObject.put("partyAccountNumber", "00");
					bankPaymentValueObject.put("transactionDate", DateTimeUtility.getDateFromTimeStamp(new Timestamp(tripSheetAdvance.getCreated().getTime())));
					bankPaymentValueObject.put("bankAccountId", 103);
					bankPaymentValueObject.put("partyBankId", 0);
					bankPaymentValueObject.put("chequeTransactionDate", DateTimeUtility.getDateFromTimeStamp(new Timestamp(tripSheetAdvance.getCreated().getTime())));
					bankPaymentValueObject.put("allowBankPaymentDetails", true);
					
					bankPaymentValueObject.put("bankPaymentTypeId",tripSheetAdvance.getPaymentTypeId());
					bankPaymentValueObject.put("userId", tripSheetAdvance.getAdvancePaidbyId());
					bankPaymentValueObject.put("companyId", tripSheetAdvance.getCompanyId());
					bankPaymentValueObject.put("moduleId", tripSheetAdvance.getTripsheet().getTripSheetID());
					bankPaymentValueObject.put("moduleNo", tripSheet.getTripSheetNumber());
					bankPaymentValueObject.put("moduleIdentifier", ModuleConstant.TRIP_ADVANCE);
					bankPaymentValueObject.put("amount", tripSheetAdvance.getAdvanceAmount());
					bankPaymentValueObject.put("remark", "Driver Name "+tripSheet.getTripFristDriverName()+", Vehicle Number : "+tripSheet.getVehicle_registration()+" ");
					
					bankPaymentService.addBankPaymentDetailsFromValueObject(bankPaymentValueObject);
				}
			}
			
		} catch (Exception e) {
			throw e;
		}
		
	}
}
