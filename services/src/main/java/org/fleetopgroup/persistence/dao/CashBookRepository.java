/**
 * 
 */
package org.fleetopgroup.persistence.dao;

import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.model.CashBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author fleetop
 *
 */
public interface CashBookRepository extends JpaRepository<CashBook, Long> {

	
	@Query("From CashBook Where CASHID = ?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public CashBook getCashBookByID(Long CashBookid, Integer companyId);

	@Query("From CashBook Where CASH_VOUCHER_NO = ?1 AND COMPANY_ID = ?2 AND markForDelete = 0")
	public List<CashBook> Validate_CashBook_value(String CASH_VOUCHER_NO, Integer companyId);

	/** This List get CashBook table to get only Debit Report */
	@Query("From CashBook Where CASH_BOOK_NO = ?1 AND CASH_PAYMENT_TYPE ='DEBIT' AND CASH_DATE = ?2 AND CASH_APPROVAL_STATUS='APPROVED' ")
	public List<CashBook> Report_CashBook_Debit(String cashbookNo, Date cashbookdate);

	/** This List get CashBook table to get only Crebit Report */
	@Query("From CashBook Where CASH_BOOK_NO = ?1 AND CASH_PAYMENT_TYPE ='CREDIT' AND CASH_DATE = ?2 AND CASH_APPROVAL_STATUS='APPROVED' ")
	public List<CashBook> Report_CashBook_Credit(String cashbookNo, Date cashbookdate);

	/** This List get CashBook table to get only Debit Report */
	@Query("From CashBook Where CASH_BOOK_NO = ?1 AND CASH_DATE = ?2 AND CASH_APPROVAL_STATUS='APPROVED' ")
	public List<CashBook> Report_CashBook_All(String cashbookNo, Date cashbookdate);

	@Modifying
	@Query("UPDATE CashBook SET markForDelete = 1 Where  CASHID = ?1 AND COMPANY_ID = ?2 ")
	public void Delete_CashBook_ID(Long cashId, Integer companyId);

	@Modifying
	@Query("UPDATE CashBook AS C SET C.CASH_AMOUNT=?1, C.CASH_PAID_BY=?2, C.CASH_NATURE_PAYMENT_ID=?3, C.CASH_REFERENCENO=?4, C.LASTMODIFIEDBY=?5, C.LASTUPDATED_DATE=?6  WHERE C.CASHID = ?7 AND C.COMPANY_ID = ?8")
	public void updateCashBook_Amount_Paid(Double editAmount, String PaidTO, Integer NaturePayment, String ReferenceNo,
			String LASTMODIFIEDBY, Date LASTUPDATED_DATE,
			Long CashBookID, Integer companyId) throws Exception;

	@Modifying
	@Query("UPDATE CashBook AS C SET C.CASH_VOUCHER_NO=?1, C.LASTMODIFIEDBY=?2, C.LASTUPDATED_DATE=?3 WHERE C.CASHID = ?4 AND C.COMPANY_ID = ?5")
	public void updateCashBookVoucherNumber(String cashVoucherNo , String updateByName, Date updateDate, Long cashBookId, Integer companyId) throws Exception;

	@Query("From CashBook Where CASH_VOUCHER_NO = ?1 AND CASH_DATE = ?2 AND CASH_STATUS_ID = ?3 AND COMPANY_ID = ?4 AND CASH_BOOK_ID = ?5 AND markForDelete = 0 ")
	public List<CashBook> getSub_CashBookToVoucherNumber(String VoucherNamer, Date cashDate, short CASH_STATUS, Integer companyId, Integer cashBookId);

	@Modifying
	@Query("UPDATE CashBook AS C SET C.CASH_APPROVAL_STATUS_ID=?1, C.CASH_APPROVALBY=?2, C.CASH_APPROVALDATE=?3, C.CASH_APPROVALCOMMENT=?4 WHERE C.CASHID = ?5 AND COMPANY_ID = ?6")
	public void Update_CashBook_Approval_Entries(short CASH_APPROVAL_STATUS, String cash_APPROVALBY,
			java.util.Date cash_APPROVALDATE, String cash_APPROVALCOMMENT, long cashid, Integer companyId);

	@Modifying
	@Query("UPDATE CashBook AS C SET C.CASH_DOCUMENT_ID=?1, C.CASH_DOCUMENT=?2 WHERE C.CASHID = ?3 AND C.COMPANY_ID = ?4")
	public void Update_CashBook_Document_ID_to_CashBook(Long cashdocid, boolean b, long cashid, Integer companyId);

	@Query("SELECT p.CASHID From CashBook as p where p.CASH_BOOK_ID= ?1 AND p.COMPANY_ID = ?2 AND p.markForDelete = 0")
	public Page<CashBook> getDeployment_Page_CashBook_Book(Integer Job_Type, Integer companyId,Pageable pageable);

	@Modifying
	@Query("UPDATE CashBook AS C SET C.CASH_AMOUNT=?1 WHERE C.CASHID = ?2 AND C.COMPANY_ID = ?3")
	public void Update_Edit_Based_Update_Amount(Double nEWCASH_AMOUNT, Long old_CASHID, Integer companyId);
	
	@Query("From CashBook Where CASH_BOOK_ID= ?1 AND CASH_DATE = ?2 AND CASH_VOUCHER_NO = ?3 AND COMPANY_ID = ?4 AND markForDelete = 0 ")
	public CashBook getCashBookToReopenTripCollection(Integer cashBookId, Date cashDate, String term, Integer companyId) throws Exception;

	@Query("SELECT SUM(B.CASH_AMOUNT) FROM CashBook AS B WHERE B.CASH_BOOK_ID= ?1 AND B.CASH_DATE = ?2 AND B.PAYMENT_TYPE_ID = 1 AND B.markForDelete = 0 ")
	public Double getTotalDebitAmountForDate(Integer cashBookId, Date cashDate) throws Exception;

	@Query("SELECT SUM(B.CASH_AMOUNT) FROM CashBook AS B WHERE B.CASH_BOOK_ID= ?1 AND B.CASH_DATE = ?2 AND B.PAYMENT_TYPE_ID = 2 AND B.markForDelete = 0 ")
	public Double getTotalCreditAmountForDate(Integer cashBookId, Date cashDate) throws Exception;
	
	@Query("From CashBook Where CASH_DATE = ?1 AND CASH_BOOK_ID=?2 AND COMPANY_ID = ?3 AND markForDelete = 0")
	public List<CashBook> checkLastDayCashBookPresentOrNot(Date cashDate,Integer CashBookId, Integer companyId);

}
