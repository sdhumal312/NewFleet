/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.CashBookBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface CashBookBalanceRepository extends JpaRepository<CashBookBalance, Long> {

	@Query("FROM CashBookBalance AS B WHERE B.CASH_DATE= ?1 AND B.CASH_BOOK_NAME = ?2 AND B.COMPANY_ID = ?3 AND B.markForDelete = 0")
	public CashBookBalance Validate_CashBookBalance_value(Date cashDate, String CASH_BOOK_Name, Integer companyId);

	@Query("FROM CashBookBalance AS B WHERE B.CASH_DATE= ?1 AND B.CASH_BOOK_ID = ?2 AND B.COMPANY_ID = ?3 AND B.markForDelete = 0")
	public CashBookBalance Validate_CashBookBalance_value(Date cashDate, Integer CASH_BOOK_ID, Integer companyId);

	
	@Query("FROM CashBookBalance AS B WHERE B.CASH_DATE= ?1 AND B.CASH_BOOK_NAME = ?2 ")
	public CashBookBalance Validate_Last_DayCashBook_CloseOrNot(Date cashDate, String CASH_BOOK_Name);

	@Query("FROM CashBookBalance AS B WHERE B.CASH_DATE= ?1 AND B.CASH_BOOK_NAME = ?2 AND B.COMPANY_ID = ?3 AND B.markForDelete = 0")
	public List<CashBookBalance> Validate_Last_DayCashBook_CloseOrNot_list(Date cashDate, String CASH_BOOK_Name, Integer companyId);

	@Query("FROM CashBookBalance AS B WHERE B.CASH_DATE= ?1 AND B.CASH_BOOK_NAME = ?2 AND B.CASH_STATUS_ID =?3 AND B.COMPANY_ID = ?4 AND B.markForDelete = 0")
	public CashBookBalance Validate_Last_DayCashBook_CloseOrNot(Date cashDate, String CASH_BOOK_Name, short CASH_STATUS, Integer companyId);

	@Modifying
	@Query("UPDATE FROM CashBookBalance AS B SET  B.CASH_STATUS_ID=?1, B.CASH_CLOSED_BY=?2, B.CASH_CLOSED_EMAIL =?3, B.CASH_REMARKS=?4, B.CASH_CLOSED_DATE=?5, B.CASH_ALL_BALANCE = ?8 WHERE B.CBBID= ?6 AND B.COMPANY_ID = ?7")
	public void update_CloseDay_Balance(short CASH_STATUS, String CASH_CLOSED_BY,
			String CASH_CLOSED_EMAIL, String CASH_REMARKS, Date CASH_CLOSED_DATE, long CBBID, Integer companyId, Double balanceAmount);

	@Query("FROM CashBookBalance AS B WHERE B.CASH_BOOK_MAIN_ID = ?1 AND B.CASH_DATE= ?2 AND B.CASH_STATUS_ID =?3 AND B.COMPANY_ID = ?4 AND B.markForDelete = 0")
	public List<CashBookBalance> Validate_Sub_CashBook_Cloesd_or_not(short CASH_BOOK_MAIN_ID, Date cashDate,
			short CASH_STATUS, Integer companyId);
	
	@Query("FROM CashBookBalance AS B WHERE B.CASH_DATE= ?1 AND B.CASH_BOOK_ID = ?2 AND B.CASH_STATUS_ID =?3 AND B.COMPANY_ID = ?4 AND B.markForDelete = 0")
	public CashBookBalance Validate_Last_DayCashBook_CloseOrNot(Date cashDate, Integer CASH_BOOK_ID, short Status, Integer companyId) throws Exception;

	@Query("FROM CashBookBalance AS B WHERE B.CASH_DATE= ?1 AND B.CASH_BOOK_ID = ?2 AND B.COMPANY_ID = ?3 AND B.markForDelete = 0")
	public List<CashBookBalance> Validate_Last_DayCashBook_CloseOrNot_list(Date cashDate, Integer CASH_BOOK_ID, Integer companyId);
	
	
	@Query("FROM CashBookBalance AS B WHERE B.CASH_DATE= ?1 AND B.CASH_BOOK_NAME = ?2 AND B.markForDelete = 0 ")
	public List<CashBookBalance> Validate_Last_DayCashBook_CloseOrNot_list(Date cashDate, String CASH_BOOK_Name);
	
	
	@Modifying
	@Query("UPDATE FROM CashBookBalance AS B SET  B.CASH_DEBIT=?1, B.CASH_CREDIT=?2, B.CASH_DAY_BALANCE =?3 WHERE B.CASH_BOOK_ID =?4 AND CASH_DATE =?5 ")
	public void upateDebitCreditBalanceAmount(Double CASH_DEBIT, Double CASH_CREDIT, Double CASH_DAY_BALANCE, Integer CASH_BOOK_ID, Date CASH_DATE);

	@Modifying
	@Query("UPDATE FROM CashBookBalance AS B SET  B.CASH_ALL_BALANCE=?1 WHERE B.CASH_BOOK_ID =?2 AND CASH_DATE =?3 ")
	public void upateAllDayBalanceAmount(Double CASH_DEBIT, Integer CASH_BOOK_ID, Date CASH_DATE);

	@Query("From CashBookBalance AS CB WHERE CB.CASH_DATE=?1 AND CB.COMPANY_ID=?2 AND CB.CASH_BOOK_ID =?3 AND CB.CASH_STATUS_ID=?4 AND CB.markForDelete = 0")
	public CashBookBalance getAllTodayCashBookBalance(Date currentDate,Integer companyId, Integer cashBookId, short status);

	@Query("FROM CashBookBalance AS B WHERE B.CASH_DATE= ?1 AND B.CASH_BOOK_ID = ?2 AND B.COMPANY_ID = ?3 AND B.markForDelete = 0")
	public List<CashBookBalance> Validate_CashBookBalance_valueByDateAndCashBookID(Date cashDate, Integer CASH_BOOK_ID, Integer companyId);

	@Query("FROM CashBookBalance AS B WHERE B.CASH_DATE= ?1 AND B.CASH_BOOK_ID = ?2 AND B.COMPANY_ID = ?3 AND B.markForDelete = 0")
	public CashBookBalance checkLastDayEntryPresentOrNot(Date cashDate, Integer CASH_BOOK_ID, Integer companyId) throws Exception;
}
