package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.BankAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{

	@Query("FROM BankAccount TR  where TR.companyId= ?1 AND TR.markForDelete = 0")
	public Page<BankAccount> getDeployment_Page_BankAccount(Integer company_id, Pageable pageable);

	
	@Query("FROM BankAccount TR  where TR.name= ?1 AND TR.bankId = ?2 AND TR.companyId = ?3 AND TR.markForDelete = 0")
	public List<BankAccount> validateBankAccount(String bankName, Long bankId, Integer companyId);
	
	@Modifying
	@Query("UPDATE BankAccount SET markForDelete = 1 where bankAccountId = ?1")
	public void deleteAccount(Long bankAccountId) throws Exception;

}
