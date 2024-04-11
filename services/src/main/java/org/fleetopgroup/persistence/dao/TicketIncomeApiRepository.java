package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.model.TicketIncomeApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TicketIncomeApiRepository extends JpaRepository<TicketIncomeApi, Long>{
	
	@Query("FROM TicketIncomeApi T WHERE T.ticketIncomeApiId = ?1 AND T.companyId = ?2 AND T.markForDelete = 0")
	public TicketIncomeApi geTticketIncomeApiById(Long ticketIncomeApiId, Integer companyId) throws Exception;
	
	@Query("FROM TicketIncomeApi T WHERE T.tripSheetID = ?1 AND T.markForDelete = 0")
	public java.util.List<TicketIncomeApi> validateTicketIncomeApi(Long tripSheetId) throws Exception;
	
	@Modifying
	@Transactional
	@Query("update TicketIncomeApi set markForDelete = 1  WHERE tripSheetID = ?1 AND companyId = ?2")
	public void deleteTicketIncome(Long tripsheet, Integer companyId);
}