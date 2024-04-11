package org.fleetopgroup.persistence.serviceImpl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.fleetopgroup.persistence.dto.CashBookBalanceDto;
import org.fleetopgroup.persistence.dto.CashBookDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.CashBook;
import org.fleetopgroup.persistence.model.CashBookBalance;
import org.fleetopgroup.persistence.model.CashBookDocument;
import org.fleetopgroup.persistence.model.CashBookHistory;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ICashBookService {

	public CashBook registerNewCashBook(CashBook CashBookDto) throws Exception;

	public CashBook updateCashBook(CashBook CashBookDto) throws Exception;

	public void updateCashBook_Amount_Paid(Double editAmount, String PaidTO, Integer NaturePayment, String ReferenceNo,
			String LASTMODIFIEDBY, Date LASTUPDATED_DATE,
			Long CashBookID, Integer companyId)
			throws Exception;

	public CashBook getCashBookByID(Long CashBookid);
	
	public CashBookDto getCashBookByNumber(Long cashBookNumber, CustomUserDetails	userDetails) throws Exception;

	/** This Page get CashBook table to get pagination values */
	public Page<CashBook> getDeployment_Page_CashBook(Integer SelectBook, Integer pageNumber, CustomUserDetails	userDetails);

	/** This List get CashBook table to get pagination last 10 entries values */
	public List<CashBookDto> list_find_CashBook(Integer SelectBook, Integer pageNumber, CustomUserDetails	userDetails);

	/** This List get CashBook table to get voucher Validate */
	public List<CashBook> Validate_CashBook_value(String CASH_VOUCHER_NO, Integer companyId);

	/** This List get CashBook table to get voucher Validate */
	public List<CashBookDto> Report_CashBook_value(String dateRangeFrom, String DateRangeTO, String Query, CustomUserDetails userDetails) throws Exception;

	/** This List get CashBook table to get voucher Validate */
	public List<CashBookDto> Search_CashBook_value(String SearchVocher, CustomUserDetails userDetails) throws Exception;

	/** This List get CashBook table to get only Debit Report */
	public List<CashBookDto> Report_CashBook_Debit(Integer cashbookId, Date cashbookdate, Integer companyId) throws Exception;

	/** This List get CashBook table to get only Crebit Report */
	public List<CashBookDto> Report_CashBook_Credit(Integer cashbookId, Date cashbookdate, Integer companyId) throws Exception;

	/** This get CashBook table to get Long Id To delete */
	public void Delete_CashBook_ID(Long cashId, Integer companyId);

	/** This List get CashBook table to get only Debit Report */
	public List<CashBook> Report_CashBook_All(String cashbookNo, Date cashbookdate);
	
	public List<CashBookDto> Report_CashBook_All(Integer cashBookId, Date cashbookdate, Integer companyId);

	/** Cash Book Balances */

	/** This List CashBookBalance table to save */
	public CashBookBalance registerNewCashBookBalance(CashBookBalance CashBookDto) throws Exception;

	/** This List CashBookBalance table to Update */
	public CashBookBalance updateCashBookBalance(CashBookBalance CashBookDto) throws Exception;

	/**
	 * This List get CashBookBalance table to get voucher Validate Date,
	 * Cashbook Name
	 */
	public CashBookBalance Validate_CashBookBalance_value(Date cashDate, String CASH_BOOK_Name);
	
	public CashBookBalance Validate_CashBookBalance_value(Date cashDate, Integer CASH_BOOK_ID, Integer companyId) throws Exception;

	/** This get CashBook Balance To Update Debit Values */
	public void Update_DEBIT_CashBalance_SubtrackBalance_Details(short CashBookAPPROVAL_ID, String cashDate, Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Long CBID,Integer companyId);

	/** This get CashBook Balance To Update CREDIT Values */
	public void Update_CREDIT_CashBalance_AddBalance_Details(short CashBookAPPROVAL_ID, String cashDate, Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Long CBID,Integer companyId);

	/**
	 * This Validate Date, CashBook , Status is close or not Check
	 */
	public CashBookBalance Validate_Last_DayCashBook_CloseOrNot(Date cashDate, String CASH_BOOK_Name);

	/**
	 * This Validate Date, CashBook , Status is close or not Check
	 */
	public List<CashBookBalance> Validate_Last_DayCashBook_CloseOrNot_list(Date cashDate, String CASH_BOOK_Name, Integer companyId);

	/**
	 * This Validate Date, CashBook , Status is close or not Check
	 */
	public List<CashBookBalance> Validate_Last_DayCashBook_CloseOrNot_list(Date cashDate, Integer CASH_BOOK_ID, Integer companyId);

	/**
	 * This Validate Date, CashBook , Status is close or not Check
	 */
	public CashBookBalance Validate_Last_DayCashBook_CloseOrNot(Date cashDate, String CASH_BOOK_Name, short Status_ID);

	/**
	 * This Validate Date, CashBook , Status is close or not Check
	 */
	public void update_CloseDay_Balance(short CASH_STATUS, String CASH_CLOSED_BY,
			String CASH_CLOSED_EMAIL, String CASH_REMARKS, Date CASH_CLOSED_DATE, long CBBID, Double balanceAmount,Integer companyId);

	/** This get CashBook Balance To Update Debit Values */
//	public void Update_DELETE_DEBIT_CashBalance_AddBalance_Details(Double CASH_DEBIT, Date cashDate,
//			String CASH_BOOK_Name);
	/** This get CashBook Balance To Update Debit Values */
	public void Update_DELETE_DEBIT_CashBalance_AddBalance_Details(short CashBookPayment_ID, Double CASH_DEBIT, Date cashDate,
			Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Integer companyId);

	/** This get CashBook Balance To Update CREDIT Values */
//	public void Update_DELETE_CREDIT_CashBalance_SubtrackBalance_Details(Double CASH_CREDIT, Date cashDate,
//			String CASH_BOOK_Name);
	
	/** This get CashBook Balance To Update CREDIT Values */
	public void Update_DELETE_CREDIT_CashBalance_SubtrackBalance_Details(short CashBookPayment_ID, Double CASH_CREDIT, Date cashDate,
			Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Integer companyId);

	
	public void updateCashBookBalanceToReopenTripSheet(Double cashCredit, Integer cashBookId, Date cashDate, Integer companyId) throws Exception;

	/** This get CashBook Balance To Update Debit Values */
//	public void Update_EDIT_DEBIT_CashBalance_AddBalance_Details(Double CASH_DEBIT, Date cashDate,
//			String CASH_BOOK_Name);
	
	/** This get CashBook Balance To Update Debit Values */
	public void Update_EDIT_DEBIT_CashBalance_AddBalance_Details(short CashBookPayment_ID,  Date cashDate,
			Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Integer companyId);
	
	public void updateCashBookStatus(Date cashDate, Integer cashBookId, Integer companyId) throws Exception;

	/** This get CashBook Balance To Update Debit Values */
	public void Update_EDIT_DEBIT_CashBalance_RemoveBalance_Details(short CashBookPayment_ID, Date cashDate,
			Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Integer companyId);

	/** This get CashBook Balance To Update CREDIT Values */
//	public void Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(Double CASH_CREDIT, Date cashDate,
//			String CASH_BOOK_Name);
	
	/** This get CashBook Balance To Update CREDIT Values */
	public void Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(short CashBookPayment_ID,  Date cashDate,
			Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Integer companyId);

	/** This get CashBook Balance To Update CREDIT Values */
	public void Update_EDIT_CREDIT_CashBalance_AddBalance_Details(short CashBookPayment_ID, Double CASH_CREDIT, Date cashDate,
			Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Integer companyId);

	/** This get Update_DEBIT_CashBalance_AfterBalance_Details Values */
	//public void Update_DEBIT_CashBalance_AfterBalance_Details(Double CASH_DEBIT, Date cashDate, String CASH_BOOK_Name);
	
	/** This get Update_DEBIT_CashBalance_AfterBalance_Details Values */
	public void Update_DEBIT_CashBalance_AfterBalance_Details(Date cashDate, Integer CASH_BOOK_ID, Integer companyId);


	/** This get Update_CREDIT_CashBalance_AfterBalance_Details Values */
	public void Update_CREDIT_CashBalance_AfterBalance_Details(Double CASH_CREDIT, Date cashDate,
			String CASH_BOOK_Name, Integer companyId);
	
	/** This get Update_CREDIT_CashBalance_AfterBalance_Details Values */
	public void Update_CREDIT_CashBalance_AfterBalance_Details(Date cashDate,
			Integer CASH_BOOK_ID, Integer companyId);

	/** This List get CashBookBalance closed or not */
	public List<CashBookBalance> Validate_Sub_CashBook_Cloesd_or_not(short CASH_BOOK_MAIN_ID, Date cashDate,
			short CASH_STATUS, Integer companyId);

	/** This Search get Voucher Number */
	public CashBook getSub_CashBookToVoucherNumber(String VoucherNamer, Date cashDate, short CASH_STATUS, Integer cashBookId);

	/** This Search Subdate _DEBIT_ Amount in date */
	public void updateSubCashBook_DEBIT_Amount_EDit(Double editAmount, Long CashBookID) throws Exception;

	/** This Search Subdate _CREDIT_ Amount in date */
	public void updateSubCashBook_CREDIT_Amount_EDit(Double editAmount, Long CashBookID) throws Exception;

	/**
	 * @param cashid
	 * @param cash_APPROVALBY
	 * @param cash_APPROVALDATE
	 * @param cash_APPROVALCOMMENT
	 */
	public void Update_CashBook_Approval_Entries(short CASH_APPROVAL_STATUS, String cash_APPROVALBY,
			java.util.Date cash_APPROVALDATE, String cash_APPROVALCOMMENT, long cashid, Integer companyId);

	/**
	 * @param cashDoc
	 */
	public CashBookDocument add_CashBook_To_CashBookDocument(CashBookDocument cashDoc);

	/**
	 * @param cASH_id
	 * @return
	 */
	public org.fleetopgroup.persistence.document.CashBookDocument get_CASH_BOOK_Document_Details_CASHID(Long CASHDOCID);

	/**
	 * @param cashdocid
	 * @param b
	 * @param cashid
	 */
	public void Update_CashBook_Document_ID_to_CashBook(Long cashdocid, boolean b, long cashid);

	/**
	 * @param vEHICLEGROUP
	 * @param dateRangeFrom
	 * @param dateRangeTo
	 * @return
	 */
	public List<CashBookDto> Report_CashBook_Month_wise_Expense_Group(Integer cashBoookId, String dateRangeFrom,
			String dateRangeTo, Integer companyId);
	
	public CashBookName getUserDefaultCashBook(List<CashBookName> cashBookNameList)throws Exception;
	
	public CashBookBalance Validate_Last_DayCashBook_CloseOrNot(Date cashDate, Integer	CASH_BOOK_ID, short Status, Integer companyId) throws Exception;
	
	public CashBookName		getMainCashBookDetails(String MAINCASHBOOK_NAME, Integer companyId) throws Exception;
	
	
	/**
	 * This Validate Date, CashBook , Status is close or not Check
	 */
	public List<CashBookBalance> Validate_Last_DayCashBook_CloseOrNot_list(Date cashDate, String CASH_BOOK_Name);

	public void Update_Edit_Based_Update_Amount(Double nEWCASH_AMOUNT, Long old_CASHID);

	public List<CashBookDto> getPaymentDetailsWithDateRange(String fromDate , String toDate, String payment, Integer VEHICLEGROUPID ,CustomUserDetails	userDetails) throws Exception;
	
	public List<CashBookDto> getPaymentDetailsWithDateRange(String fromDate , String toDate, Integer paymentId, Integer VEHICLEGROUPID ,CustomUserDetails	userDetails) throws Exception;

	public void save(org.fleetopgroup.persistence.document.CashBookDocument cashDoc) throws Exception;

	public void transferCahBookDocument(List<CashBookDocument> list) throws Exception;
	
	public long getMaxCashBookId() throws Exception;
	
	public void runThreadForCashBookHistory(CashBookHistory	cashBookHistory) throws Exception ;
	
	public CashBook getCashBookToReopenTripCollection(Integer cashBookId, Date cashDate, String term, Integer companyId)throws Exception;

	/*
	 * Method For Cash Book Voucher No By Cash Id
	 */
	public ValueObject updateCashVoucherNoByCashId(ValueObject valueObject) throws Exception;
	
	public CashBookDto getCashBookByID(Long CashBookid, Integer companyId) ;

	public void checkCashBookBalanceForMissingEntries(ValueObject	valueObject) throws Exception;
	
	public List<CashBookDto>  getAddedMissingEntriesForADay(Timestamp	startDate, Timestamp endDate) throws Exception;
	
	public void checkAndCorrectDebitAndCreditAmountOnMissingEntries(List<CashBookDto>  cashBookList)throws Exception;
	
	public void checkAndCorrectDebitAndCreditAmountOnMissingEntries(Date cashDate, Integer cashBookId, Integer companyId) throws Exception;
	
	public void checkAndCorrectAllDayBalanceOnMissingEntries(List<CashBookDto>  cashBookList)throws Exception;
	
	public boolean checkAndCorrectAllDayBalanceAmountOnMissingEntries(Date cashDate, Integer cashBookId, Integer companyId) throws Exception;
	
	public CashBookDto getCashBookIdByNumber(Long cashBookNumber, CustomUserDetails	userDetails) throws Exception;

	public CashBookBalance getAllTodayCashBookBalance(Date currentDate,Integer cashBookId, Integer CompanyId, short status) throws Exception;

	public ValueObject saveCloseCashbookBalance(CashBookBalanceDto CashBook, boolean fromsScheduler) throws Exception;

	public CashBookBalance checkLastDayEntryCreatedOrNot(Date cashDate, Integer CASH_BOOK_ID, Integer companyId)
			throws Exception;

	public List<CashBook> checkLastDayCashBookCreatedOrNot(Date cashDate, Integer CASH_BOOK_ID, Integer companyId)
			throws Exception;

	public ValueObject saveCashBookMissingEntry(CashBookDto CashBook, MultipartFile file, boolean saveDriverBalanceInCashBook)
			throws Exception;

	List<CashBookBalance> Validate_CashBookBalance_valueByDateAndCashBookID(Date cashDate, Integer CASH_BOOK_ID,
			Integer companyId) throws Exception;
}
