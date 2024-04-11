package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.BankMaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BankMasterRepository extends JpaRepository<BankMaster, Long>{

	@Override
	@Query("FROM BankMaster VS where VS.markForDelete = 0 order by VS.bankName ASC")
	List<BankMaster> findAll();
	
	@Query("FROM BankMaster VS where VS.markForDelete = 0 AND VS.bankName = ?1 order by VS.bankName ASC")
	public List<BankMaster> validateBankName(String bankName) throws Exception;
}
