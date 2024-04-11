package org.fleetopgroup.persistence.dao;

import java.util.List;

import org.fleetopgroup.persistence.model.TollExpensesDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TollExpensesDetailsRepository extends JpaRepository<TollExpensesDetails, Long>{

	@Query("FROM TollExpensesDetails where transactionId = ?1 AND companyId = ?2 AND markForDelete = 0")
	public List<TollExpensesDetails> validateTollExpensesDetails(String transactionId, Integer companyId) throws Exception ;
	
	@Query("SELECT SUM(transactionAmount) from TollExpensesDetails where tripSheetId = ?1 AND markForDelete = 0 ")
	public Double getTripSheetTollAmount(Long tripSheetId) throws Exception ;
}
