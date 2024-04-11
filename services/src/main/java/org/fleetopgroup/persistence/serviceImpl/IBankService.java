package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.dto.BankAccountDto;
import org.fleetopgroup.persistence.model.BankAccount;
import org.fleetopgroup.persistence.model.BankMaster;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface IBankService {

	public List<BankMaster>  getBankList()throws Exception;
	
	public void save(BankMaster bankMaster)throws Exception;
	
	public List<BankMaster> validateBankName(String bankName)throws Exception;
	
	public List<BankAccountDto> getBankAccountList(Integer pageNumber, Integer companyId) throws Exception;
	
	public Page<BankAccount> getDeployment_Page_BankAccoutn(Integer pageNumber, Integer companyId);
	
	public List<BankAccount> validateBankAccount(String bankName, Long bankId, Integer companyId)throws Exception;
	
	public void deleteBankAccount(Long id)throws Exception;
	
	public void save(BankAccount bankAccount)throws Exception;
	
	public List<BankAccountDto> getBankAccountListForDropDown(String term, Integer companyId) throws Exception;

	public List<BankAccountDto> getBankAccountListForDropDownByBankId(String bankId, Integer company_id)throws Exception;
}
