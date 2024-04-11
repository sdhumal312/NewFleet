package org.fleetopgroup.persistence.dao;

import java.util.Date;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.List;

import org.fleetopgroup.persistence.model.CashBookName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CashBookNameRepository extends JpaRepository<CashBookName, Integer> {

	@Modifying
	@Query("Update FROM CashBookName SET CASHBOOK_NAME = ?1, CASHBOOK_REMARKS = ?2, LASTMODIFIEDBY = ?3, LASTMODIFIEDON = ?4, CASHBOOK_CODE = ?7 where NAMEID = ?5 AND COMPANY_ID = ?6")
	public void updateTripExpense(String cashName, String expenseRemarks, String lastModifiedBy, Date lastModifiedOn, Integer incomeID, Integer companyId, String cashBookCode) throws Exception;

	public List<CashBookName> findAll();

	@Query("From CashBookName p where p.NAMEID = ?1 AND p.COMPANY_ID = ?2")
	public CashBookName get_CashBookName(int ExpenseID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE CashBookName T SET T.markForDelete = 1 where T.NAMEID = ?1 AND T.COMPANY_ID = ?2")
	public void delete_CashBookName(Integer status, Integer companyId) throws Exception;

	@Query("From CashBookName p where (p.CASHBOOK_NAME = ?1 OR  lower(p.CASHBOOK_NAME) like CONCAT('',?2,'%') OR p.VEHICLE_GROUP_ID = ?4) and p.COMPANY_ID = ?3 and p.markForDelete = 0")
	public CashBookName Validate_CashBookName(String cashNAME, String THREEWORKD, Integer companyId, long vehicleGroupId) throws Exception;
	
	@Query("From CashBookName p where p.COMPANY_ID = ?1 and p.markForDelete = 0")
	public List<CashBookName> list_CashBookName(Integer companyId) throws Exception ;
	
	@Query("From CashBookName p "
			+ " INNER JOIN CashBookPermission CBP ON CBP.cashBookId = p.NAMEID AND user_Id = ?1"
			+ " where p.COMPANY_ID = ?2 and p.markForDelete = 0")
	public List<CashBookName> list_CashBookNameByPermission(long userId, Integer companyId) throws Exception;
	
	@Query("From CashBookName p where p.CASHBOOK_NAME =?1 and p.COMPANY_ID = ?2 and p.markForDelete = 0")
	public CashBookName getMainCashBookDetails(String MAINCASHBOOK_NAME, Integer companyId) throws Exception;
	
	@Query("From CashBookName p where p.CASHBOOK_NAME = ?1 and p.COMPANY_ID = ?2 and p.markForDelete = 0")
	public CashBookName getCashBookByName(String cashNAME, Integer companyId) throws Exception;
	
	@Query("From CashBookName p where p.VEHICLE_GROUP_ID = ?1 and p.COMPANY_ID = ?2 and p.markForDelete = 0")
	public CashBookName getCashBookByVehicleGroupId(long vehicleGroupID, Integer companyId) throws Exception;
	
}
