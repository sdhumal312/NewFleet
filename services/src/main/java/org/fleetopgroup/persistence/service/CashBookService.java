/**
 * 
 */
package org.fleetopgroup.persistence.service;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.fleetopgroup.constant.CashBookApprovalStatus;
import org.fleetopgroup.constant.CashBookBalanceStatus;
import org.fleetopgroup.constant.CashBookPaymentType;
import org.fleetopgroup.constant.CashBookStatus;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.SequenceCounterConstant;
import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.persistence.bl.CashBookBL;
import org.fleetopgroup.persistence.dao.CashBookBalanceRepository;
import org.fleetopgroup.persistence.dao.CashBookDocumentRepository;
import org.fleetopgroup.persistence.dao.CashBookHistoryRepository;
import org.fleetopgroup.persistence.dao.CashBookNameRepository;
import org.fleetopgroup.persistence.dao.CashBookRepository;
import org.fleetopgroup.persistence.dto.CashBookBalanceDto;
import org.fleetopgroup.persistence.dto.CashBookDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.CashBook;
import org.fleetopgroup.persistence.model.CashBookBalance;
import org.fleetopgroup.persistence.model.CashBookDocument;
import org.fleetopgroup.persistence.model.CashBookHistory;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.model.CashBookSequenceCounter;
import org.fleetopgroup.persistence.model.TripDailyRouteSheet;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookSequenceService;
import org.fleetopgroup.persistence.model.CashBookSequenceCounter;
import org.fleetopgroup.persistence.serviceImpl.ICashBookSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookVoucherSequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ISequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import com.antkorwin.xsync.XMutexFactoryImpl;

/**
 * @author fleetop
 *
 */
@Service("CashBookService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CashBookService implements ICashBookService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	private CashBookRepository CashRepository;

	@Autowired
	private CashBookDocumentRepository CashDocumentRepository;

	@Autowired
	private CashBookBalanceRepository CashBalanceRepository;

	@Autowired
	private CashBookNameRepository cashBookNameRepository;

	@Autowired
	private ICompanyConfigurationService companyConfigurationService;
	
	@Autowired private MongoTemplate	mongoTemplate;
	@Autowired private ISequenceCounterService	sequenceCounterService;
	
	@Autowired private CashBookDocumentRepository	cashBookDocumentRepository;
	
	@Autowired private CashBookHistoryRepository	cashBookHistoryRepository;
	
	@Autowired
	private ICashBookNameService CashBookNameService;
	
	@Autowired private ITripDailySheetService					tripDailySheetService;
	
	@Autowired private ICashBookSequenceService 				cashBookSequenceService;
	@Autowired private ICashBookVoucherSequenceCounterService	bookVoucherSequenceCounterService;
	@Autowired private ITripExpenseService						ITripExpenseService;
	
	private static final int PAGE_SIZE = 10;

	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormat_SQL = new SimpleDateFormat("yyyy-MM-dd");
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
	
	XMutexFactoryImpl<Integer> xMutexFactory = new XMutexFactoryImpl<Integer>();
	
	CashBookBL CBBL = new CashBookBL();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.ICashBookService#registerNewCashBook(
	 * org.fleetop.persistence.model.CashBook)
	 */
	@Transactional
	public CashBook registerNewCashBook(CashBook CashBookDto) throws Exception {

		return CashRepository.save(CashBookDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#updateCashBook(org.
	 * fleetop.persistence.model.CashBook)
	 */
	@Transactional
	public CashBook updateCashBook(CashBook CashBookDto) throws Exception {

		return CashRepository.save(CashBookDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.ICashBookService#getCashBookByID(java
	 * .lang.Integer)
	 */
	@Transactional
	public CashBook getCashBookByID(Long CashBookid) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return CashRepository.getCashBookByID(CashBookid, userDetails.getCompany_id());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * getDeployment_Page_CashBook(java.lang.Integer)
	 */
	@Transactional
	public Page<CashBook> getDeployment_Page_CashBook(Integer SelectBook, Integer pageNumber,
			CustomUserDetails userDetails) {
		@SuppressWarnings("deprecation")
		PageRequest request = new PageRequest(pageNumber - 1, PAGE_SIZE);
		return CashRepository.getDeployment_Page_CashBook_Book(SelectBook, userDetails.getCompany_id(), request);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#list_find_CashBook(
	 * java.lang.Integer)
	 */
	@Transactional
	public List<CashBookDto> list_find_CashBook(Integer SelectBook, Integer pageNumber, CustomUserDetails userDetails) {

		TypedQuery<Object[]> query = entityManager.createQuery(

				"SELECT CB.CASHID, CB.CASH_NUMBER, CB.CASH_BOOK_NO, CB.CASH_BOOK_ID, CB.PAYMENT_TYPE_ID, CB.CASH_DATE, CB.CASH_VOUCHER_NO,"
				+ " CB.CASH_AMOUNT, CB.CASH_NATURE_PAYMENT_ID, TI.incomeName , TE.expenseName, CB.CASH_PAID_RECEIVED, CB.CASH_PAID_BY,"
				+ " CB.CASH_STATUS_ID, CB.CASH_APPROVAL_STATUS_ID, CB.CASH_APPROVALBY, CB.CASH_APPROVALCOMMENT, CB.CASH_APPROVALDATE,"
				+ " CB.CASH_REFERENCENO, CB.CASH_GSTNO, CB.CASH_DOCUMENT, CB.CASH_DOCUMENT_ID, CB.DRIVER_ID, CB.CREATEDBY, CB.LASTMODIFIEDBY,"
				+ " CB.CREATED_DATE, CB.LASTUPDATED_DATE, CB.CASH_NATURE_PAYMENT, CB.INCOME_TYPE"
				+ " FROM CashBook  AS CB "
				+ " LEFT JOIN TripIncome AS TI ON TI.incomeID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_CREDIT+""
				+ " LEFT JOIN TripExpense AS TE ON TE.expenseID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_DEBIT+""
				+ " WHERE CB.CASH_BOOK_ID=" + SelectBook + " AND CB.COMPANY_ID = "+userDetails.getCompany_id()+"  AND CB.markForDelete = 0 ORDER BY CB.CASHID desc",
						Object[].class);
		query.setFirstResult((pageNumber - 1) * PAGE_SIZE);
		query.setMaxResults(PAGE_SIZE);
		List<Object[]>	results = query.getResultList();
		
		List<CashBookDto>	list	= null;
		
		if(results != null && !results.isEmpty()) {
			list	=	new ArrayList<>();
			for (Object[] result : results) {
				CashBookDto	cash = new CashBookDto();
				
				cash.setCASHID((long) result[0]);
				cash.setCASH_NUMBER((long) result[1]);
				cash.setCASH_BOOK_NO((String) result[2]);
				cash.setCASH_BOOK_ID((Integer) result[3]);
				cash.setPAYMENT_TYPE_ID((short) result[4]);
				cash.setCASH_DATE_ON((Date) result[5]);
				cash.setCASH_VOUCHER_NO((String) result[6]);
				cash.setCASH_AMOUNT((Double) result[7]);
				cash.setCASH_NATURE_PAYMENT_ID((Integer) result[8]);
				cash.setIncomeName((String) result[9]);
				cash.setExpenseName((String) result[10]);
				cash.setCASH_PAID_RECEIVED((String) result[11]);
				cash.setCASH_PAID_BY((String) result[12]);
				cash.setCASH_STATUS_ID((short) result[13]);
				cash.setCASH_APPROVAL_STATUS_ID((short) result[14]);
				cash.setCASH_APPROVALBY((String) result[15]);
				cash.setCASH_APPROVALCOMMENT((String) result[16]);
				cash.setCASH_APPROVALDATE_ON((Date) result[17]);
				cash.setCASH_REFERENCENO((String) result[18]);
				cash.setCASH_GSTNO((String) result[19]);
				cash.setCASH_DOCUMENT((boolean) result[20]);
				cash.setCASH_DOCUMENT_ID((Long) result[21]);
				cash.setDRIVER_ID((Integer) result[22]);
				cash.setCREATEDBY((String) result[23]);
				cash.setLASTMODIFIEDBY((String) result[24]);
				cash.setCREATED((Date) result[25]);
				cash.setLASTUPDATED((Date) result[26]);
				cash.setCASH_NATURE_PAYMENT((String) result[27]);
				if(result[28] != null)
					cash.setINCOME_TYPE((String) result[28]);
				
				list.add(cash);
			}
		}
		
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Validate_CashBook_value(java.lang.String)
	 */
	@Transactional
	public List<CashBook> Validate_CashBook_value(String CASH_VOUCHER_NO, Integer companyId) {

		return CashRepository.Validate_CashBook_value(CASH_VOUCHER_NO, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Report_CashBook_value(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Transactional
	public List<CashBookDto> Report_CashBook_value(String dateRangeFrom, String DateRangeTO, String Query,
			CustomUserDetails userDetails) throws Exception {
		TypedQuery<Object[]> query = null;
		
		if (!companyConfigurationService.getCashBookWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			 query = entityManager.createQuery(
					"SELECT CB.CASHID, CB.CASH_NUMBER, CB.CASH_BOOK_NO, CB.CASH_BOOK_ID, CB.PAYMENT_TYPE_ID, CB.CASH_DATE, CB.CASH_VOUCHER_NO,"
					+ " CB.CASH_AMOUNT, CB.CASH_NATURE_PAYMENT_ID, TI.incomeName , TE.expenseName, CB.CASH_PAID_RECEIVED, CB.CASH_PAID_BY,"
					+ " CB.CASH_STATUS_ID, CB.CASH_APPROVAL_STATUS_ID, CB.CASH_APPROVALBY, CB.CASH_APPROVALCOMMENT, CB.CASH_APPROVALDATE,"
					+ " CB.CASH_REFERENCENO, CB.CASH_GSTNO, CB.CASH_DOCUMENT, CB.CASH_DOCUMENT_ID, CB.DRIVER_ID, CB.CREATEDBY, CB.LASTMODIFIEDBY,"
					+ " CB.CREATED_DATE, CB.LASTUPDATED_DATE, CB.CASH_NATURE_PAYMENT "
					+ " FROM CashBook  AS CB "
					+ " LEFT JOIN TripIncome AS TI ON TI.incomeID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_CREDIT+""
					+ " LEFT JOIN TripExpense AS TE ON TE.expenseID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_DEBIT+""
					+ " WHERE (CB.CASH_DATE Between '" + dateRangeFrom + "' AND '"
					+ DateRangeTO + "' " + Query + " ) AND CB.COMPANY_ID = "+userDetails.getCompany_id()+" AND CB.markForDelete = 0",
							Object[].class);
		} else {
			 query = entityManager.createQuery(
					"SELECT CB.CASHID, CB.CASH_NUMBER, CB.CASH_BOOK_NO, CB.CASH_BOOK_ID, CB.PAYMENT_TYPE_ID, CB.CASH_DATE, CB.CASH_VOUCHER_NO,"
					+ " CB.CASH_AMOUNT, CB.CASH_NATURE_PAYMENT_ID, TI.incomeName , TE.expenseName, CB.CASH_PAID_RECEIVED, CB.CASH_PAID_BY,"
					+ " CB.CASH_STATUS_ID, CB.CASH_APPROVAL_STATUS_ID, CB.CASH_APPROVALBY, CB.CASH_APPROVALCOMMENT, CB.CASH_APPROVALDATE,"
					+ " CB.CASH_REFERENCENO, CB.CASH_GSTNO, CB.CASH_DOCUMENT, CB.CASH_DOCUMENT_ID, CB.DRIVER_ID, CB.CREATEDBY, CB.LASTMODIFIEDBY,"
					+ " CB.CREATED_DATE, CB.LASTUPDATED_DATE, CB.CASH_NATURE_PAYMENT "
					+ " FROM CashBook  AS CB "
					+ " INNER JOIN CashBookPermission CBP ON CBP.cashBookId = CB.CASH_BOOK_ID AND CBP.user_Id = "+userDetails.getId()+""
					+ " LEFT JOIN TripIncome AS TI ON TI.incomeID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_CREDIT+""
					+ " LEFT JOIN TripExpense AS TE ON TE.expenseID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_DEBIT+""
					+ " WHERE (CB.CASH_DATE Between '" + dateRangeFrom + "' AND '"
					+ DateRangeTO + "' " + Query + " ) AND CB.COMPANY_ID = "+userDetails.getCompany_id()+" AND CB.markForDelete = 0",
							Object[].class);
		}
		
		List<Object[]>	results = query.getResultList();
		
		List<CashBookDto>	list	= null;
		
		if(results != null && !results.isEmpty()) {
			list	=	new ArrayList<>();
			for (Object[] result : results) {
				CashBookDto	cash = new CashBookDto();
				
				cash.setCASHID((long) result[0]);
				cash.setCASH_NUMBER((long) result[1]);
				cash.setCASH_BOOK_NO((String) result[2]);
				cash.setCASH_BOOK_ID((Integer) result[3]);
				cash.setPAYMENT_TYPE_ID((short) result[4]);
				cash.setCASH_DATE_ON((Date) result[5]);
				cash.setCASH_VOUCHER_NO((String) result[6]);
				cash.setCASH_AMOUNT((Double) result[7]);
				cash.setCASH_NATURE_PAYMENT_ID((Integer) result[8]);
				cash.setIncomeName((String) result[9]);
				cash.setExpenseName((String) result[10]);
				cash.setCASH_PAID_RECEIVED((String) result[11]);
				cash.setCASH_PAID_BY((String) result[12]);
				cash.setCASH_STATUS_ID((short) result[13]);
				cash.setCASH_APPROVAL_STATUS_ID((short) result[14]);
				cash.setCASH_APPROVALBY((String) result[15]);
				cash.setCASH_APPROVALCOMMENT((String) result[16]);
				cash.setCASH_APPROVALDATE_ON((Date) result[17]);
				cash.setCASH_REFERENCENO((String) result[18]);
				cash.setCASH_GSTNO((String) result[19]);
				cash.setCASH_DOCUMENT((boolean) result[20]);
				cash.setCASH_DOCUMENT_ID((Long) result[21]);
				cash.setDRIVER_ID((Integer) result[22]);
				cash.setCREATEDBY((String) result[23]);
				cash.setLASTMODIFIEDBY((String) result[24]);
				cash.setCREATED((Date) result[25]);
				cash.setLASTUPDATED((Date) result[26]);
				cash.setCASH_NATURE_PAYMENT((String) result[27]);
				
				
				list.add(cash);
			}
		}
		
		return list;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Report_CashBook_Debit(java.lang.String, java.util.Date)
	 */
	@Transactional
	public List<CashBookDto> Report_CashBook_Debit(Integer cashbookId, Date cashbookdate, Integer companyId) {

		TypedQuery<Object[]> query = entityManager.createQuery(

				"SELECT CB.CASHID, CB.CASH_NUMBER, CB.CASH_BOOK_NO, CB.CASH_BOOK_ID, CB.PAYMENT_TYPE_ID, CB.CASH_DATE, CB.CASH_VOUCHER_NO,"
				+ " CB.CASH_AMOUNT, CB.CASH_NATURE_PAYMENT_ID, TI.incomeName , TE.expenseName, CB.CASH_PAID_RECEIVED, CB.CASH_PAID_BY,"
				+ " CB.CASH_STATUS_ID, CB.CASH_APPROVAL_STATUS_ID, CB.CASH_APPROVALBY, CB.CASH_APPROVALCOMMENT, CB.CASH_APPROVALDATE,"
				+ " CB.CASH_REFERENCENO, CB.CASH_GSTNO, CB.CASH_DOCUMENT, CB.CASH_DOCUMENT_ID, CB.DRIVER_ID, CB.CREATEDBY, CB.LASTMODIFIEDBY,"
				+ " CB.CREATED_DATE, CB.LASTUPDATED_DATE, CB.CASH_NATURE_PAYMENT "
				+ " FROM CashBook  AS CB "
				+ " LEFT JOIN TripIncome AS TI ON TI.incomeID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_CREDIT+""
				+ " LEFT JOIN TripExpense AS TE ON TE.expenseID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_DEBIT+""
				+ " Where CB.CASH_BOOK_ID = " + cashbookId + " AND CB.PAYMENT_TYPE_ID ="
				+ CashBookPaymentType.PAYMENT_TYPE_DEBIT + " AND CB.CASH_APPROVALDATE LIKE '" + cashbookdate
				+ "%' AND CB.CASH_APPROVAL_STATUS_ID= " + CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED
				+ " AND CB.COMPANY_ID = " + companyId + " AND CB.markForDelete = 0 ",
						Object[].class);
		List<Object[]>	results = query.getResultList();
		
		List<CashBookDto>	list	= null;
		
		if(results != null && !results.isEmpty()) {
			list	=	new ArrayList<>();
			for (Object[] result : results) {
				CashBookDto	cash = new CashBookDto();
				
				cash.setCASHID((long) result[0]);
				cash.setCASH_NUMBER((long) result[1]);
				cash.setCASH_BOOK_NO((String) result[2]);
				cash.setCASH_BOOK_ID((Integer) result[3]);
				cash.setPAYMENT_TYPE_ID((short) result[4]);
				cash.setCASH_DATE_ON((Date) result[5]);
				cash.setCASH_VOUCHER_NO((String) result[6]);
				cash.setCASH_AMOUNT((Double) result[7]);
				cash.setCASH_NATURE_PAYMENT_ID((Integer) result[8]);
				cash.setIncomeName((String) result[9]);
				cash.setExpenseName((String) result[10]);
				cash.setCASH_PAID_RECEIVED((String) result[11]);
				cash.setCASH_PAID_BY((String) result[12]);
				cash.setCASH_STATUS_ID((short) result[13]);
				cash.setCASH_APPROVAL_STATUS_ID((short) result[14]);
				cash.setCASH_APPROVALBY((String) result[15]);
				cash.setCASH_APPROVALCOMMENT((String) result[16]);
				cash.setCASH_APPROVALDATE_ON((Date) result[17]);
				cash.setCASH_REFERENCENO((String) result[18]);
				cash.setCASH_GSTNO((String) result[19]);
				cash.setCASH_DOCUMENT((boolean) result[20]);
				cash.setCASH_DOCUMENT_ID((Long) result[21]);
				cash.setDRIVER_ID((Integer) result[22]);
				cash.setCREATEDBY((String) result[23]);
				cash.setLASTMODIFIEDBY((String) result[24]);
				cash.setCREATED((Date) result[25]);
				cash.setLASTUPDATED((Date) result[26]);
				cash.setCASH_NATURE_PAYMENT((String) result[27]);
				
				
				list.add(cash);
			}
		}
		
		return list;
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Report_CashBook_Crebit(java.lang.String, java.util.Date)
	 */
	@Transactional
	public List<CashBookDto> Report_CashBook_Credit(Integer cashbookId, Date cashbookdate, Integer companyId) {

		TypedQuery<Object[]> query = entityManager.createQuery(

				"SELECT CB.CASHID, CB.CASH_NUMBER, CB.CASH_BOOK_NO, CB.CASH_BOOK_ID, CB.PAYMENT_TYPE_ID, CB.CASH_DATE, CB.CASH_VOUCHER_NO,"
				+ " CB.CASH_AMOUNT, CB.CASH_NATURE_PAYMENT_ID, TI.incomeName , TE.expenseName, CB.CASH_PAID_RECEIVED, CB.CASH_PAID_BY,"
				+ " CB.CASH_STATUS_ID, CB.CASH_APPROVAL_STATUS_ID, CB.CASH_APPROVALBY, CB.CASH_APPROVALCOMMENT, CB.CASH_APPROVALDATE,"
				+ " CB.CASH_REFERENCENO, CB.CASH_GSTNO, CB.CASH_DOCUMENT, CB.CASH_DOCUMENT_ID, CB.DRIVER_ID, CB.CREATEDBY, CB.LASTMODIFIEDBY,"
				+ " CB.CREATED_DATE, CB.LASTUPDATED_DATE, CB.CASH_NATURE_PAYMENT "
				+ " FROM CashBook  AS CB "
				+ " LEFT JOIN TripIncome AS TI ON TI.incomeID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_CREDIT+""
				+ " LEFT JOIN TripExpense AS TE ON TE.expenseID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_DEBIT+""
				+ " Where CB.CASH_BOOK_ID = " + cashbookId + " AND CB.PAYMENT_TYPE_ID ="
				+ CashBookPaymentType.PAYMENT_TYPE_CREDIT + " AND CB.CASH_APPROVALDATE LIKE '" + cashbookdate
				+ "%' AND CB.CASH_APPROVAL_STATUS_ID=" + CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED
				+ " AND CB.COMPANY_ID = " + companyId + " AND CB.markForDelete = 0 ",
						Object[].class);
		List<Object[]>	results = query.getResultList();
		
		List<CashBookDto>	list	= null;
		
		if(results != null && !results.isEmpty()) {
			list	=	new ArrayList<>();
			for (Object[] result : results) {
				CashBookDto	cash = new CashBookDto();
				
				cash.setCASHID((long) result[0]);
				cash.setCASH_NUMBER((long) result[1]);
				cash.setCASH_BOOK_NO((String) result[2]);
				cash.setCASH_BOOK_ID((Integer) result[3]);
				cash.setPAYMENT_TYPE_ID((short) result[4]);
				cash.setCASH_DATE_ON((Date) result[5]);
				cash.setCASH_VOUCHER_NO((String) result[6]);
				cash.setCASH_AMOUNT((Double) result[7]);
				cash.setCASH_NATURE_PAYMENT_ID((Integer) result[8]);
				cash.setIncomeName((String) result[9]);
				cash.setExpenseName((String) result[10]);
				cash.setCASH_PAID_RECEIVED((String) result[11]);
				cash.setCASH_PAID_BY((String) result[12]);
				cash.setCASH_STATUS_ID((short) result[13]);
				cash.setCASH_APPROVAL_STATUS_ID((short) result[14]);
				cash.setCASH_APPROVALBY((String) result[15]);
				cash.setCASH_APPROVALCOMMENT((String) result[16]);
				cash.setCASH_APPROVALDATE_ON((Date) result[17]);
				cash.setCASH_REFERENCENO((String) result[18]);
				cash.setCASH_GSTNO((String) result[19]);
				cash.setCASH_DOCUMENT((boolean) result[20]);
				cash.setCASH_DOCUMENT_ID((Long) result[21]);
				cash.setDRIVER_ID((Integer) result[22]);
				cash.setCREATEDBY((String) result[23]);
				cash.setLASTMODIFIEDBY((String) result[24]);
				cash.setCREATED((Date) result[25]);
				cash.setLASTUPDATED((Date) result[26]);
				cash.setCASH_NATURE_PAYMENT((String) result[27]);
				
				list.add(cash);
			}
		}
		
		return list;
	
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Search_CashBook_value(java.lang.String)
	 */
	@Transactional
	public List<CashBookDto> Search_CashBook_value(String SearchVocher, CustomUserDetails userDetails) throws Exception {
		TypedQuery<Object[]> query = null;
		
		if (!companyConfigurationService.getCashBookWisePermission(userDetails.getCompany_id(),
				PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
			 query = entityManager.createQuery(
					"SELECT CB.CASHID, CB.CASH_NUMBER, CB.CASH_BOOK_NO, CB.CASH_BOOK_ID, CB.PAYMENT_TYPE_ID, CB.CASH_DATE, CB.CASH_VOUCHER_NO,"
					+ " CB.CASH_AMOUNT, CB.CASH_NATURE_PAYMENT_ID, TI.incomeName , TE.expenseName, CB.CASH_PAID_RECEIVED, CB.CASH_PAID_BY,"
					+ " CB.CASH_STATUS_ID, CB.CASH_APPROVAL_STATUS_ID, CB.CASH_APPROVALBY, CB.CASH_APPROVALCOMMENT, CB.CASH_APPROVALDATE,"
					+ " CB.CASH_REFERENCENO, CB.CASH_GSTNO, CB.CASH_DOCUMENT, CB.CASH_DOCUMENT_ID, CB.DRIVER_ID, CB.CREATEDBY, CB.LASTMODIFIEDBY,"
					+ " CB.CREATED_DATE, CB.LASTUPDATED_DATE, CB.CASH_NATURE_PAYMENT "
					+ " FROM CashBook  AS CB "
					+ " LEFT JOIN TripIncome AS TI ON TI.incomeID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_CREDIT+""
					+ " LEFT JOIN TripExpense AS TE ON TE.expenseID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_DEBIT+""
					+ " WHERE ( lower(CB.CASH_NUMBER) Like ('%" + SearchVocher
					+ "%') OR lower(CB.CASH_VOUCHER_NO) Like ('%" + SearchVocher
					+ "%')) AND  CB.COMPANY_ID = "+userDetails.getCompany_id()+" AND CB.markForDelete = 0",
							Object[].class);
		} else {
			 query = entityManager.createQuery(

					"SELECT CB.CASHID, CB.CASH_NUMBER, CB.CASH_BOOK_NO, CB.CASH_BOOK_ID, CB.PAYMENT_TYPE_ID, CB.CASH_DATE, CB.CASH_VOUCHER_NO,"
					+ " CB.CASH_AMOUNT, CB.CASH_NATURE_PAYMENT_ID, TI.incomeName , TE.expenseName, CB.CASH_PAID_RECEIVED, CB.CASH_PAID_BY,"
					+ " CB.CASH_STATUS_ID, CB.CASH_APPROVAL_STATUS_ID, CB.CASH_APPROVALBY, CB.CASH_APPROVALCOMMENT, CB.CASH_APPROVALDATE,"
					+ " CB.CASH_REFERENCENO, CB.CASH_GSTNO, CB.CASH_DOCUMENT, CB.CASH_DOCUMENT_ID, CB.DRIVER_ID, CB.CREATEDBY, CB.LASTMODIFIEDBY,"
					+ " CB.CREATED_DATE, CB.LASTUPDATED_DATE, CB.CASH_NATURE_PAYMENT "
					+ " FROM CashBook  AS CB "
					+ " INNER JOIN CashBookPermission CBP ON CBP.cashBookId = CB.CASH_BOOK_ID AND CBP.user_Id = "+userDetails.getId()+""
					+ " LEFT JOIN TripIncome AS TI ON TI.incomeID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_CREDIT+""
					+ " LEFT JOIN TripExpense AS TE ON TE.expenseID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_DEBIT+""
					+  " WHERE ( lower(CB.CASH_NUMBER) Like ('%" + SearchVocher
					+ "%') OR lower(CB.CASH_VOUCHER_NO) Like ('%" + SearchVocher
					+ "%')) AND  CB.COMPANY_ID = "+userDetails.getCompany_id()+" AND CB.markForDelete = 0",
							Object[].class);
		}
		query.setMaxResults(50);
		List<Object[]>	results = query.getResultList();
		
		List<CashBookDto>	list	= null;
		
		if(results != null && !results.isEmpty()) {
			list	=	new ArrayList<>();
			for (Object[] result : results) {
				CashBookDto	cash = new CashBookDto();
				
				cash.setCASHID((long) result[0]);
				cash.setCASH_NUMBER((long) result[1]);
				cash.setCASH_BOOK_NO((String) result[2]);
				cash.setCASH_BOOK_ID((Integer) result[3]);
				cash.setPAYMENT_TYPE_ID((short) result[4]);
				cash.setCASH_DATE_ON((Date) result[5]);
				cash.setCASH_VOUCHER_NO((String) result[6]);
				cash.setCASH_AMOUNT((Double) result[7]);
				cash.setCASH_NATURE_PAYMENT_ID((Integer) result[8]);
				cash.setIncomeName((String) result[9]);
				cash.setExpenseName((String) result[10]);
				cash.setCASH_PAID_RECEIVED((String) result[11]);
				cash.setCASH_PAID_BY((String) result[12]);
				cash.setCASH_STATUS_ID((short) result[13]);
				cash.setCASH_APPROVAL_STATUS_ID((short) result[14]);
				cash.setCASH_APPROVALBY((String) result[15]);
				cash.setCASH_APPROVALCOMMENT((String) result[16]);
				cash.setCASH_APPROVALDATE_ON((Date) result[17]);
				cash.setCASH_REFERENCENO((String) result[18]);
				cash.setCASH_GSTNO((String) result[19]);
				cash.setCASH_DOCUMENT((boolean) result[20]);
				cash.setCASH_DOCUMENT_ID((Long) result[21]);
				cash.setDRIVER_ID((Integer) result[22]);
				cash.setCREATEDBY((String) result[23]);
				cash.setLASTMODIFIEDBY((String) result[24]);
				cash.setCREATED((Date) result[25]);
				cash.setLASTUPDATED((Date) result[26]);
				cash.setCASH_NATURE_PAYMENT((String) result[27]);
				
				
				list.add(cash);
			}
		}
		
		return list;

	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#Delete_CashBook_ID(
	 * java.lang.Long)
	 */
	@Transactional
	public void Delete_CashBook_ID(Long cashId, Integer companyId) {

		CashRepository.Delete_CashBook_ID(cashId, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.fleetop.persistence.serviceImpl.ICashBookService#Report_CashBook_All(
	 * java.util.Date)
	 */
	@Transactional
	public List<CashBook> Report_CashBook_All(String cashbookNo, Date cashbookdate) {

		// return CashRepository.Report_CashBook_All(cashbookNo, cashbookdate);

		// below Only WHich date Approval Cash Book Details
		TypedQuery<CashBook> query = entityManager.createQuery("From CashBook Where CASH_BOOK_NO = '" + cashbookNo
				+ "' AND CASH_APPROVALDATE LIKE '" + cashbookdate + "%' AND CASH_APPROVAL_STATUS_ID="
				+ CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED + " ", CashBook.class);
		return query.getResultList();
	}

	@Override
	public List<CashBookDto> Report_CashBook_All(Integer cashBookId, Date cashbookdate, Integer companyId) {
		TypedQuery<Object[]> query = entityManager.createQuery(

				"SELECT CB.CASHID, CB.CASH_NUMBER, CB.CASH_BOOK_NO, CB.CASH_BOOK_ID, CB.PAYMENT_TYPE_ID, CB.CASH_DATE, CB.CASH_VOUCHER_NO,"
				+ " CB.CASH_AMOUNT, CB.CASH_NATURE_PAYMENT_ID, TI.incomeName , TE.expenseName, CB.CASH_PAID_RECEIVED, CB.CASH_PAID_BY,"
				+ " CB.CASH_STATUS_ID, CB.CASH_APPROVAL_STATUS_ID, CB.CASH_APPROVALBY, CB.CASH_APPROVALCOMMENT, CB.CASH_APPROVALDATE,"
				+ " CB.CASH_REFERENCENO, CB.CASH_GSTNO, CB.CASH_DOCUMENT, CB.CASH_DOCUMENT_ID, CB.DRIVER_ID, CB.CREATEDBY, CB.LASTMODIFIEDBY,"
				+ " CB.CREATED_DATE, CB.LASTUPDATED_DATE, CB.CASH_NATURE_PAYMENT "
				+ " FROM CashBook  AS CB "
				+ " LEFT JOIN TripIncome AS TI ON TI.incomeID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_CREDIT+""
				+ " LEFT JOIN TripExpense AS TE ON TE.expenseID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_DEBIT+""
				+ " Where CB.CASH_BOOK_ID = '" + cashBookId + "' AND CB.CASH_APPROVALDATE LIKE '" + cashbookdate
				+ "%' AND CB.CASH_APPROVAL_STATUS_ID=" + CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED
				+ " AND CB.COMPANY_ID = " + companyId + " AND CB.markForDelete = 0",
						Object[].class);
		List<Object[]>	results = query.getResultList();
		
		List<CashBookDto>	list	= null;
		
		if(results != null && !results.isEmpty()) {
			list	=	new ArrayList<>();
			for (Object[] result : results) {
				CashBookDto	cash = new CashBookDto();
				
				cash.setCASHID((long) result[0]);
				cash.setCASH_NUMBER((long) result[1]);
				cash.setCASH_BOOK_NO((String) result[2]);
				cash.setCASH_BOOK_ID((Integer) result[3]);
				cash.setPAYMENT_TYPE_ID((short) result[4]);
				cash.setCASH_DATE_ON((Date) result[5]);
				cash.setCASH_VOUCHER_NO((String) result[6]);
				cash.setCASH_AMOUNT((Double) result[7]);
				cash.setCASH_NATURE_PAYMENT_ID((Integer) result[8]);
				cash.setIncomeName((String) result[9]);
				cash.setExpenseName((String) result[10]);
				cash.setCASH_PAID_RECEIVED((String) result[11]);
				cash.setCASH_PAID_BY((String) result[12]);
				cash.setCASH_STATUS_ID((short) result[13]);
				cash.setCASH_APPROVAL_STATUS_ID((short) result[14]);
				cash.setCASH_APPROVALBY((String) result[15]);
				cash.setCASH_APPROVALCOMMENT((String) result[16]);
				cash.setCASH_APPROVALDATE_ON((Date) result[17]);
				cash.setCASH_REFERENCENO((String) result[18]);
				cash.setCASH_GSTNO((String) result[19]);
				cash.setCASH_DOCUMENT((boolean) result[20]);
				cash.setCASH_DOCUMENT_ID((Long) result[21]);
				cash.setDRIVER_ID((Integer) result[22]);
				cash.setCREATEDBY((String) result[23]);
				cash.setLASTMODIFIEDBY((String) result[24]);
				cash.setCREATED((Date) result[25]);
				cash.setLASTUPDATED((Date) result[26]);
				cash.setCASH_NATURE_PAYMENT((String) result[27]);
				
				list.add(cash);
			}
		}
		
		return list;
	
	
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * registerNewCashBookBalance(org.fleetop.persistence.model.CashBookBalance)
	 */
	@Transactional
	public CashBookBalance registerNewCashBookBalance(CashBookBalance CashBookDto) throws Exception {

		try {
			synchronized (xMutexFactory.getMutex(CashBookDto.getCASH_BOOK_ID())) {
				return CashBalanceRepository.save(CashBookDto);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * updateCashBookBalance(org.fleetop.persistence.model.CashBookBalance)
	 */
	@Transactional
	public CashBookBalance updateCashBookBalance(CashBookBalance CashBookDto) throws Exception {
		synchronized (xMutexFactory.getMutex(CashBookDto.getCASH_BOOK_ID())) {
			return CashBalanceRepository.save(CashBookDto);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Validate_CashBookBalance_value(java.util.Date, java.lang.String)
	 */
	@Transactional
	public CashBookBalance Validate_CashBookBalance_value(Date cashDate, String CASH_BOOK_Name) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return CashBalanceRepository.Validate_CashBookBalance_value(cashDate, CASH_BOOK_Name,
				userDetails.getCompany_id());
	}

	@Override
	public CashBookBalance Validate_CashBookBalance_value(Date cashDate, Integer CASH_BOOK_ID, Integer companyId) {
		synchronized (xMutexFactory.getMutex(CASH_BOOK_ID)) {
			return CashBalanceRepository.Validate_CashBookBalance_value(cashDate, CASH_BOOK_ID, companyId);
		}
	}

	@Override
	public List<CashBookBalance> Validate_CashBookBalance_valueByDateAndCashBookID(Date cashDate, Integer CASH_BOOK_ID, Integer companyId) throws Exception {
		synchronized (xMutexFactory.getMutex(CASH_BOOK_ID)) {
			System.err.println("inside validate -- ");
			return CashBalanceRepository.Validate_CashBookBalance_valueByDateAndCashBookID(cashDate, CASH_BOOK_ID, companyId);
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Update_DEBIT_CashBalance_SubtrackBalance_Details(java.lang.Double,
	 * java.lang.Long)
	 */
	@Transactional
	public void Update_DEBIT_CashBalance_SubtrackBalance_Details(short CashBookPayment_ID, String cashDate,
			Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Long CBID,Integer companyId) {
		entityManager.createQuery(
				"UPDATE CashBookBalance AS CB SET CB.CASH_DEBIT = (SELECT COALESCE(ROUND(SUM(C.CASH_AMOUNT),2),0) FROM CashBook AS C WHERE C.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND C.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND C.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND C.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND C.markForDelete=0 AND C.COMPANY_ID = " + companyId + ") "
						+ ", CB.CASH_DAY_BALANCE = (CB.CASH_CREDIT - (SELECT COALESCE(ROUND(SUM(CC.CASH_AMOUNT),2),0) FROM CashBook AS CC WHERE CC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CC.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND CC.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND CC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND CC.markForDelete=0 AND CC.COMPANY_ID = " + companyId
						+ ")) where CB.CBBID=" + CBID + " AND CB.COMPANY_ID = " + companyId + "")
				.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Update_CREDIT_CashBalance_AddBalance_Details(java.lang.Double,
	 * java.lang.Long)
	 */
	@Transactional
	public void Update_CREDIT_CashBalance_AddBalance_Details(short CashBookPayment_ID, String cashDate,
			Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Long CBID, Integer companyId) {
		entityManager.createQuery(
				"UPDATE CashBookBalance AS CB SET CB.CASH_CREDIT = (SELECT COALESCE(ROUND(SUM(C.CASH_AMOUNT),2),0) FROM CashBook C WHERE C.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND C.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND C.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND C.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%' AND C.markForDelete=0 AND C.COMPANY_ID = " + companyId + ") "
						+ ", CB.CASH_DAY_BALANCE = ((SELECT COALESCE(ROUND(SUM(CC.CASH_AMOUNT),2),0) FROM CashBook CC WHERE CC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CC.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND CC.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND CC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%' AND CC.markForDelete=0 AND CC.COMPANY_ID = " + companyId
						+ ") - CB.CASH_DEBIT) where CB.CBBID=" + CBID + " AND CB.COMPANY_ID = "
						+ companyId + "")
				.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Validate_Last_DayCashBook_CloseOrNot(java.util.Date, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public CashBookBalance Validate_Last_DayCashBook_CloseOrNot(Date cashDate, String CASH_BOOK_Name) {

		return CashBalanceRepository.Validate_Last_DayCashBook_CloseOrNot(cashDate, CASH_BOOK_Name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Validate_Last_DayCashBook_CloseOrNot(java.util.Date, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public CashBookBalance Validate_Last_DayCashBook_CloseOrNot(Date cashDate, String CASH_BOOK_Name,
			short CASH_STATUS) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return CashBalanceRepository.Validate_Last_DayCashBook_CloseOrNot(cashDate, CASH_BOOK_Name, CASH_STATUS,
				userDetails.getCompany_id());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * update_CloseDay_Balance(java.lang.Double, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, java.util.Date, long)
	 */
	@Override
	@Transactional
	public void update_CloseDay_Balance(short CASH_STATUS, String CASH_CLOSED_BY,
			String CASH_CLOSED_EMAIL, String CASH_REMARKS, Date CASH_CLOSED_DATE, long CBBID, Double balanceAmount,Integer companyId) {
		CashBalanceRepository.update_CloseDay_Balance(CASH_STATUS, CASH_CLOSED_BY, CASH_CLOSED_EMAIL,
				CASH_REMARKS, CASH_CLOSED_DATE, CBBID, companyId, balanceAmount);
	}

	@Override
	@Transactional
	public void Update_DELETE_DEBIT_CashBalance_AddBalance_Details(short CashBookPayment_ID, Double CASH_DEBIT,
			Date cashDate, Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Integer companyId) {

		entityManager.createNativeQuery(
				"UPDATE CashBookBalance AS CB INNER JOIN CashBookBalance AS CBB ON CBB.CASH_BOOK_ID = CB.CASH_BOOK_ID  "
						+ "  AND CBB.CASH_DATE = (SUBDATE('" + cashDate
						+ "', 1))  AND CBB.markForDelete=0 AND CBB.COMPANY_ID = " + companyId + " "
						+ " SET CB.CASH_DEBIT = (SELECT COALESCE(ROUND(SUM(C.CASH_AMOUNT),2),0) FROM CashBook AS C WHERE C.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND C.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND C.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND C.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND C.markForDelete=0 AND C.COMPANY_ID = " + companyId + ") "
						+ ", CB.CASH_DAY_BALANCE = (CB.CASH_CREDIT - (SELECT COALESCE(ROUND(SUM(CC.CASH_AMOUNT),2),0) FROM CashBook AS CC WHERE CC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CC.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND CC.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND CC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND CC.markForDelete=0 AND CC.COMPANY_ID = " + companyId + ")),"
						+ " CB.CASH_ALL_BALANCE = (CBB.CASH_ALL_BALANCE + (CB.CASH_CREDIT - (SELECT COALESCE(ROUND(SUM(CCC.CASH_AMOUNT),2),0) FROM CashBook AS CCC WHERE CCC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CCC.CASH_BOOK_ID=" + CASH_BOOK_ID + " "
						+ " AND CCC.PAYMENT_TYPE_ID=" + PAYMENT_TYPE_ID + " AND CCC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND CCC.markForDelete=0 AND CCC.COMPANY_ID = " + companyId + ")))  "
						+ " where CB.CASH_DATE='" + cashDate + "' AND CB.CASH_BOOK_ID=" + CASH_BOOK_ID
						+ "  AND CB.COMPANY_ID = " + companyId + "")
				.executeUpdate();

	}

	@Override
	@Transactional
	public void Update_DELETE_CREDIT_CashBalance_SubtrackBalance_Details(short CashBookPayment_ID, Double CASH_CREDIT,
			Date cashDate, Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Integer companyId) {

		entityManager.createNativeQuery(
				"UPDATE CashBookBalance AS CB INNER JOIN CashBookBalance AS CBB ON CBB.CASH_BOOK_ID = CB.CASH_BOOK_ID "
						+ "  AND CBB.CASH_DATE = (SUBDATE('" + cashDate
						+ "', 1))  AND CBB.markForDelete=0 AND CBB.COMPANY_ID = " + companyId + " "
						+ " SET CB.CASH_CREDIT = (SELECT COALESCE(ROUND(SUM(C.CASH_AMOUNT),2),0) FROM CashBook AS C WHERE C.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND C.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND C.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND C.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND C.markForDelete=0 AND C.COMPANY_ID = " + companyId + ") "
						+ ", CB.CASH_DAY_BALANCE = ((SELECT COALESCE(ROUND(SUM(CC.CASH_AMOUNT),2),0) FROM CashBook AS CC WHERE CC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CC.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND CC.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND CC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND CC.markForDelete=0 AND CC.COMPANY_ID = " + companyId + ") - CB.CASH_DEBIT),"
						+ " CB.CASH_ALL_BALANCE = (CBB.CASH_ALL_BALANCE "
						+ " + ((SELECT COALESCE(ROUND(SUM(CCC.CASH_AMOUNT),2),0) FROM CashBook AS CCC WHERE CCC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CCC.CASH_BOOK_ID=" + CASH_BOOK_ID + " "
						+ " AND CCC.PAYMENT_TYPE_ID=" + PAYMENT_TYPE_ID + " AND CCC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND CCC.markForDelete=0 AND CCC.COMPANY_ID = " + companyId + ") - CB.CASH_DEBIT))  "
						+ " where CB.CASH_DATE='" + cashDate + "' AND CB.CASH_BOOK_ID=" + CASH_BOOK_ID
						+ "  AND CB.COMPANY_ID = " + companyId + "")
				.executeUpdate();

	}
	
	@Override
	@Transactional
	public synchronized void updateCashBookBalanceToReopenTripSheet(Double cashCredit, Integer cashBookId, Date cashDate,
			Integer companyId) throws Exception {
		try {
			entityManager.createQuery("UPDATE CashBookBalance  SET CASH_CREDIT = CASH_CREDIT - "
					+ cashCredit + ", CASH_DAY_BALANCE =  CASH_DAY_BALANCE - "
					+ cashCredit + ", " + " CASH_ALL_BALANCE =  CASH_ALL_BALANCE - "
					+ cashCredit + " where CASH_BOOK_ID=" + cashBookId
					+ " AND CASH_DATE = '"+cashDate+"' AND COMPANY_ID = " + companyId + " AND markForDelete = 0")
					.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	@Transactional
	public void Update_EDIT_DEBIT_CashBalance_AddBalance_Details(short CashBookPayment_ID, Date cashDate,
			Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Integer companyId) {

		entityManager.createNativeQuery(
				"UPDATE CashBookBalance AS CB INNER JOIN CashBookBalance AS CBB ON CBB.CASH_BOOK_ID = CB.CASH_BOOK_ID "
						+ "  AND CBB.CASH_DATE = (SUBDATE('" + cashDate
						+ "', 1))  AND CBB.markForDelete=0 AND CBB.COMPANY_ID = " + companyId + " "
						+ " SET CB.CASH_DEBIT = (SELECT COALESCE(ROUND(SUM(C.CASH_AMOUNT),2),0) FROM CashBook AS C WHERE C.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND C.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND C.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND C.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND C.markForDelete=0 AND C.COMPANY_ID = " + companyId + ") "
						+ ", CB.CASH_DAY_BALANCE = (CB.CASH_CREDIT - (SELECT COALESCE(ROUND(SUM(CC.CASH_AMOUNT),2),0) FROM CashBook AS CC WHERE CC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CC.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND CC.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND CC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND CC.markForDelete=0 AND CC.COMPANY_ID = " + companyId + ")),"
						+ "  CB.CASH_ALL_BALANCE = (CBB.CASH_ALL_BALANCE + (CB.CASH_CREDIT - (SELECT COALESCE(ROUND(SUM(CCC.CASH_AMOUNT),2),0) FROM CashBook AS CCC WHERE CCC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CCC.CASH_BOOK_ID=" + CASH_BOOK_ID + " "
						+ " AND CCC.PAYMENT_TYPE_ID=" + PAYMENT_TYPE_ID + " AND CCC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND CCC.markForDelete=0 AND CCC.COMPANY_ID = " + companyId + ")))  "
						+ " where CB.CASH_DATE='" + cashDate + "' AND CB.CASH_BOOK_ID=" + CASH_BOOK_ID
						+ "  AND CB.COMPANY_ID = " + companyId + " AND CB.markForDelete=0 ;")
				.executeUpdate();

	}
	
	@Override
	@Transactional
	public void updateCashBookStatus(Date cashDate, Integer cashBookId, Integer companyId) throws Exception {

		entityManager.createNativeQuery(
				"UPDATE CashBook AS CB SET CB.CASH_STATUS_ID = "+CashBookStatus.CASH_BOOK_STATUS_CLOSED+" "
						+ " where CB.CASH_DATE='" + cashDate + "' AND CB.CASH_BOOK_ID=" + cashBookId
						+ "  AND CB.COMPANY_ID = " + companyId + " AND CB.markForDelete=0 ;")
				.executeUpdate();

	}

	@Override
	@Transactional
	public void Update_EDIT_DEBIT_CashBalance_RemoveBalance_Details(short CashBookPayment_ID, Date cashDate,
			Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Integer companyId) {

		entityManager.createNativeQuery(
				"UPDATE CashBookBalance AS CB INNER JOIN CashBookBalance AS CBB ON CBB.CASH_BOOK_ID = CB.CASH_BOOK_ID  "
						+ "  AND CBB.CASH_DATE = (SUBDATE('" + cashDate
						+ "', 1))  AND CBB.markForDelete=0 AND CBB.COMPANY_ID = " + companyId + " "
						+ " SET CB.CASH_DEBIT = (SELECT COALESCE(ROUND(SUM(C.CASH_AMOUNT),2),0) FROM CashBook AS C WHERE C.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND C.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND C.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND C.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND C.markForDelete=0 AND C.COMPANY_ID = " + companyId + ") "
						+ ", CB.CASH_DAY_BALANCE = (CB.CASH_CREDIT - (SELECT COALESCE(ROUND(SUM(CC.CASH_AMOUNT),2),0) FROM CashBook AS CC WHERE CC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CC.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND CC.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND CC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND CC.markForDelete=0 AND CC.COMPANY_ID = " + companyId + ")),"
						+ " CB.CASH_ALL_BALANCE = (CBB.CASH_ALL_BALANCE + (CB.CASH_CREDIT - (SELECT COALESCE(ROUND(SUM(CCC.CASH_AMOUNT),2),0) FROM CashBook AS CCC WHERE CCC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CCC.CASH_BOOK_ID=" + CASH_BOOK_ID + " "
						+ " AND CCC.PAYMENT_TYPE_ID=" + PAYMENT_TYPE_ID + " AND CCC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND CCC.markForDelete=0 AND CCC.COMPANY_ID = " + companyId + "))) "
						+ " where CB.CASH_DATE='" + cashDate + "' AND CASH_BOOK_ID=" + CASH_BOOK_ID
						+ "  AND CB.COMPANY_ID = " + companyId + "")
				.executeUpdate();

	}

	@Override
	@Transactional
	public void Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(short CashBookPayment_ID, Date cashDate,
			Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Integer companyId) {

		entityManager.createNativeQuery(
				"UPDATE CashBookBalance AS CB INNER JOIN CashBookBalance AS CBB ON CBB.CASH_BOOK_ID = CB.CASH_BOOK_ID "
						+ "  AND CBB.CASH_DATE = (SUBDATE('" + cashDate
						+ "', 1))  AND CBB.markForDelete=0 AND CBB.COMPANY_ID = " + companyId + " "
						+ " SET CB.CASH_CREDIT = (SELECT COALESCE(ROUND(SUM(C.CASH_AMOUNT),2),0) FROM CashBook AS C WHERE C.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND C.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND C.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND C.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND C.markForDelete=0 AND C.COMPANY_ID = " + companyId + ") "
						+ ", CB.CASH_DAY_BALANCE = ((SELECT COALESCE(ROUND(SUM(CC.CASH_AMOUNT),2),0) FROM CashBook AS CC WHERE CC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CC.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND CC.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND CC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND CC.markForDelete=0 AND CC.COMPANY_ID = " + companyId + ") - CB.CASH_DEBIT),"
						+ " CB.CASH_ALL_BALANCE =  (CBB.CASH_ALL_BALANCE + ((SELECT COALESCE(ROUND(SUM(CCC.CASH_AMOUNT),2),0) FROM CashBook AS CCC WHERE CCC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CCC.CASH_BOOK_ID=" + CASH_BOOK_ID + " "
						+ " AND CCC.PAYMENT_TYPE_ID=" + PAYMENT_TYPE_ID + " AND CCC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND CCC.markForDelete=0 AND CCC.COMPANY_ID = " + companyId + ") - CB.CASH_DEBIT))  "
						+ " where CB.CASH_DATE='" + cashDate + "' AND CB.CASH_BOOK_ID=" + CASH_BOOK_ID
						+ " AND CB.COMPANY_ID = " + companyId + "")
				.executeUpdate();

	}

	@Override
	@Transactional
	public void Update_EDIT_CREDIT_CashBalance_AddBalance_Details(short CashBookPayment_ID, Double CASH_CREDIT,
			Date cashDate, Integer CASH_BOOK_ID, short PAYMENT_TYPE_ID, Integer companyId) {

		entityManager.createNativeQuery(
				"UPDATE CashBookBalance AS CB INNER JOIN CashBookBalance AS CBB ON CBB.CASH_BOOK_ID = CB.CASH_BOOK_ID  "
						+ "  AND CBB.CASH_DATE = (SUBDATE('" + cashDate
						+ "', 1))  AND CBB.markForDelete=0 AND CBB.COMPANY_ID = " + companyId + " "
						+ " SET CB.CASH_CREDIT = (SELECT COALESCE(ROUND(SUM(C.CASH_AMOUNT),2),0) FROM CashBook AS C WHERE C.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND C.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND C.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND C.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND C.markForDelete=0 AND C.COMPANY_ID = " + companyId + ") "
						+ ", CB.CASH_DAY_BALANCE = ((SELECT COALESCE(ROUND(SUM(CC.CASH_AMOUNT),2),0) FROM CashBook AS CC WHERE CC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CC.CASH_BOOK_ID=" + CASH_BOOK_ID + " " + " AND CC.PAYMENT_TYPE_ID="
						+ PAYMENT_TYPE_ID + " AND CC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND CC.markForDelete=0 AND CC.COMPANY_ID = " + companyId + ") - CB.CASH_DEBIT),"
						+ " CB.CASH_ALL_BALANCE = (CBB.CASH_ALL_BALANCE + ((SELECT COALESCE(ROUND(SUM(CCC.CASH_AMOUNT),2),0) FROM CashBook AS CCC WHERE CCC.CASH_APPROVAL_STATUS_ID="
						+ CashBookPayment_ID + " AND CCC.CASH_BOOK_ID=" + CASH_BOOK_ID + " "
						+ " AND CCC.PAYMENT_TYPE_ID=" + PAYMENT_TYPE_ID + " AND CCC.CASH_APPROVALDATE LIKE '" + cashDate
						+ "%'  AND CCC.markForDelete=0 AND CCC.COMPANY_ID = " + companyId + ") - CB.CASH_DEBIT)) "
						+ " where CB.CASH_DATE='" + cashDate + "' AND CASH_BOOK_ID=" + CASH_BOOK_ID
						+ "  AND CB.COMPANY_ID = " + companyId + "")
				.executeUpdate();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Validate_Sub_CashBook_Cloesd_or_not(java.lang.String, java.util.Date,
	 * java.lang.String)
	 */
	@Transactional
	public List<CashBookBalance> Validate_Sub_CashBook_Cloesd_or_not(short CASH_BOOK_MAIN_ID, Date cashDate,
			short CASH_STATUS, Integer companyId) {

		return CashBalanceRepository.Validate_Sub_CashBook_Cloesd_or_not(CASH_BOOK_MAIN_ID, cashDate, CASH_STATUS,
				companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * updateCashBook_Amount_Paid(java.lang.Double, java.lang.String,
	 * java.lang.String, java.lang.Long)
	 */
	@Transactional
	public void updateCashBook_Amount_Paid(Double editAmount, String PaidTO, Integer NaturePayment, String ReferenceNo,
			String LASTMODIFIEDBY, Date LASTUPDATED_DATE, Long CashBookID, Integer companyId) throws Exception {

		CashRepository.updateCashBook_Amount_Paid(editAmount, PaidTO, NaturePayment, ReferenceNo, LASTMODIFIEDBY,
				LASTUPDATED_DATE, CashBookID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Update_DEBIT_CashBalance_AfterBalance_Details(java.lang.Double,
	 * java.util.Date, java.lang.String)
	 */
	@Transactional
	public void Update_DEBIT_CashBalance_AfterBalance_Details(Double CASH_DEBIT, Date cashDate, String CASH_BOOK_Name,
			Integer companyId) {

		entityManager.createNativeQuery(
				"UPDATE CashBookBalance AS CB INNER JOIN CashBookBalance AS CBB ON CBB.CASH_BOOK_ID = CB.CASH_BOOK_ID  "
						+ "  AND CBB.CASH_DATE = (SUBDATE('" + cashDate
						+ "', 1))  AND CBB.markForDelete=0 AND CBB.COMPANY_ID = " + companyId + " "
						+ "  SET  CB.CASH_ALL_BALANCE = (CBB.CASH_ALL_BALANCE " + " + CB.CASH_DAY_BALANCE) "
						+ " where CB.CASH_DATE >'" + cashDate + "' AND CB.CASH_BOOK_NAME='" + CASH_BOOK_Name
						+ "' AND CB.CASH_STATUS_ID=" + CashBookStatus.CASH_BOOK_STATUS_CLOSED + "  ")
				.executeUpdate();
	}

	@Override
	@Transactional
	public void Update_DEBIT_CashBalance_AfterBalance_Details(Date cashDate, Integer CASH_BOOK_ID, Integer companyId) {

		try {

			Date currentDate = new Date();

			List<Date> dates = CashBookBL.getDaysBetweenDates(cashDate, currentDate);
			for (int i = 1; i < dates.size(); i++) {
				Date lDate = (Date) dates.get(i);
				String cashDateUpdate = formatter.format(lDate);

				entityManager.createNativeQuery(
						"UPDATE CashBookBalance  AS CB INNER JOIN CashBookBalance AS CBB ON CBB.CASH_BOOK_ID = CB.CASH_BOOK_ID "
								+ "  AND CBB.CASH_DATE = (SUBDATE('" + cashDateUpdate
								+ "', 1))  AND CBB.markForDelete=0 AND CBB.COMPANY_ID = " + companyId + " "
								+ "  SET  CB.CASH_ALL_BALANCE = (CBB.CASH_ALL_BALANCE + CB.CASH_DAY_BALANCE) "
								+ " where CB.CASH_DATE = '" + cashDateUpdate + "' AND CB.CASH_BOOK_ID=" + CASH_BOOK_ID
								+ " AND CB.CASH_STATUS_ID=" + CashBookStatus.CASH_BOOK_STATUS_CLOSED
								+ " AND CB.COMPANY_ID = " + companyId + " AND CB.markForDelete=0")
						.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Update_CREDIT_CashBalance_AfterBalance_Details(java.lang.Double,
	 * java.util.Date, java.lang.String)
	 */
	@Transactional
	public void Update_CREDIT_CashBalance_AfterBalance_Details(Double CASH_CREDIT, Date cashDate, String CASH_BOOK_Name,
			Integer companyId) {

		try {

			Date currentDate = new Date();

			List<Date> dates = CashBookBL.getDaysBetweenDates(cashDate, currentDate);
			for (int i = 0; i < dates.size(); i++) {
				Date lDate = (Date) dates.get(i);
				String cashDateUpdate = formatter.format(lDate);
				entityManager.createNativeQuery(
						"UPDATE CashBookBalance  AS CB INNER JOIN CashBookBalance AS CBB ON CBB.CASH_BOOK_ID = CB.CASH_BOOK_ID "
								+ "  AND CBB.CASH_DATE = (SUBDATE('" + cashDateUpdate
								+ "', 1))  AND CBB.markForDelete=0 AND CBB.COMPANY_ID = " + companyId + " "
								+ "  SET CB.CASH_ALL_BALANCE = (CBB.CASH_ALL_BALANCE + CB.CASH_DAY_BALANCE) "
								+ " where  CB.CASH_DATE = '" + cashDateUpdate + "' AND CB.CASH_BOOK_NAME='"
								+ CASH_BOOK_Name + "' AND CB.CASH_STATUS_ID=" + CashBookStatus.CASH_BOOK_STATUS_CLOSED
								+ " ")
						.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void Update_CREDIT_CashBalance_AfterBalance_Details(Date cashDate, Integer CASH_BOOK_ID, Integer companyId) {

		try {

			Date currentDate = new Date();

			List<Date> dates = CashBookBL.getDaysBetweenDates(cashDate, currentDate);
			for (int i = 1; i < dates.size(); i++) {
				Date lDate = (Date) dates.get(i);
				String cashDateUpdate = formatter.format(lDate);
				entityManager.createNativeQuery(
						"UPDATE CashBookBalance  AS CB INNER JOIN CashBookBalance AS CBB ON CBB.CASH_BOOK_ID = CB.CASH_BOOK_ID "
								+ "  AND CBB.CASH_DATE = (SUBDATE('" + cashDateUpdate
								+ "', 1))  AND CBB.markForDelete=0 AND CBB.COMPANY_ID = " + companyId + " "
								+ "  SET  CB.CASH_ALL_BALANCE = (CBB.CASH_ALL_BALANCE + CB.CASH_DAY_BALANCE)  "
								+ " where  CB.CASH_DATE = '" + cashDateUpdate + "' AND CB.CASH_BOOK_ID=" + CASH_BOOK_ID
								+ " AND CB.CASH_STATUS_ID=" + CashBookStatus.CASH_BOOK_STATUS_CLOSED
								+ " AND CB.COMPANY_ID = " + companyId + " AND CB.markForDelete=0")
						.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * getSub_CashBookToVoucherNumber(java.lang.String, java.util.Date,
	 * java.lang.String)
	 */
	@Transactional
	public CashBook getSub_CashBookToVoucherNumber(String VoucherNamer, Date cashDate, short CASH_STATUS, Integer cashBookId) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<CashBook>   cashbook	= null;
		try {
			cashbook	= CashRepository.getSub_CashBookToVoucherNumber(VoucherNamer, cashDate, CASH_STATUS,
					userDetails.getCompany_id(), cashBookId);
		
			if(cashbook == null || cashbook.isEmpty()) {
				return null;
			}else {
			return 	cashbook.get(0);
			}
		
		} catch (Exception e) {
			throw e;
		}finally {
			cashbook	= null;
		}
		
		/*return CashRepository.getSub_CashBookToVoucherNumber(VoucherNamer, cashDate, CASH_STATUS,
				userDetails.getCompany_id(), cashBookId);*/
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * updateSubCashBook_Amount_EDit(java.lang.Double, java.lang.Long)
	 */
	@Transactional
	public void updateSubCashBook_DEBIT_Amount_EDit(Double editAmount, Long CashBookID) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		entityManager.createQuery("UPDATE CashBook AS C SET C.CASH_AMOUNT = C.CASH_AMOUNT-" + editAmount
				+ " WHERE C.CASHID=" + CashBookID + " AND C.COMPANY_ID = " + userDetails.getCompany_id() + " ")
				.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * updateSubCashBook_CREDIT_Amount_EDit(java.lang.Double, java.lang.Long)
	 */
	@Transactional
	public void updateSubCashBook_CREDIT_Amount_EDit(Double editAmount, Long CashBookID) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		entityManager
				.createQuery("UPDATE CashBook AS C SET C.CASH_AMOUNT = C.CASH_AMOUNT +" + editAmount
						+ " WHERE C.CASHID=" + CashBookID + " AND C.COMPANY_ID = " + userDetails.getCompany_id() + " ")
				.executeUpdate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Validate_Last_DayCashBook_CloseOrNot_list(java.util.Date, java.lang.String)
	 */
	@Transactional
	public List<CashBookBalance> Validate_Last_DayCashBook_CloseOrNot_list(Date cashDate, String CASH_BOOK_Name,
			Integer companyId) {

		return CashBalanceRepository.Validate_Last_DayCashBook_CloseOrNot_list(cashDate, CASH_BOOK_Name, companyId);
	}

	@Override
	@Transactional
	public List<CashBookBalance> Validate_Last_DayCashBook_CloseOrNot_list(Date cashDate, Integer CASH_BOOK_ID,
			Integer companyId) {
		return CashBalanceRepository.Validate_Last_DayCashBook_CloseOrNot_list(cashDate, CASH_BOOK_ID, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Update_CashBook_Approval_Entries(java.lang.String, java.lang.String,
	 * java.util.Date, java.lang.String, long)
	 */
	@Transactional
	public void Update_CashBook_Approval_Entries(short CASH_APPROVAL_STATUS, String cash_APPROVALBY,
			Date cash_APPROVALDATE, String cash_APPROVALCOMMENT, long cashid, Integer companyId) {

		CashRepository.Update_CashBook_Approval_Entries(CASH_APPROVAL_STATUS, cash_APPROVALBY, cash_APPROVALDATE,
				cash_APPROVALCOMMENT, cashid, companyId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * add_CashBook_To_CashBookDocument(org.fleetop.persistence.model.
	 * CashBookDocument)
	 */
	@Transactional
	public CashBookDocument add_CashBook_To_CashBookDocument(CashBookDocument cashDoc) {

		return CashDocumentRepository.save(cashDoc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * get_CASH_BOOK_Document_Details_CASHID(java.lang.Long)
	 */
	@Transactional
	public org.fleetopgroup.persistence.document.CashBookDocument get_CASH_BOOK_Document_Details_CASHID(Long CASHDOCID) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(CASHDOCID).and("COMPANY_ID").is(userDetails.getCompany_id()).and("markForDelete").is(false));
		return mongoTemplate.findOne(query, org.fleetopgroup.persistence.document.CashBookDocument.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Update_CashBook_Document_ID_to_CashBook(java.lang.Long, boolean, long)
	 */
	@Transactional
	public void Update_CashBook_Document_ID_to_CashBook(Long cashdocid, boolean b, long cashid) {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		CashRepository.Update_CashBook_Document_ID_to_CashBook(cashdocid, b, cashid, userDetails.getCompany_id());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Report_CashBook_Month_wise_Expense_Group(java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Transactional
	public List<CashBookDto> Report_CashBook_Month_wise_Expense_Group(Integer cashBoookId, String dateRangeFrom,
			String dateRangeTo, Integer companyId) {

		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.CASH_BOOK_NO, R.CASH_DATE, COALESCE(ROUND(R.CASH_AMOUNT,2),0), R.CASH_NATURE_PAYMENT, R.CASH_VOUCHER_NO, R.PAYMENT_TYPE_ID,"
				+ " TI.incomeName , TE.expenseName, R.CASH_NATURE_PAYMENT_ID, R.CASHID, R.CASH_NUMBER "
				+ " FROM CashBook as R "
				+ " LEFT JOIN TripIncome TI ON TI.incomeID = R.CASH_NATURE_PAYMENT_ID AND R.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_CREDIT+""
				+ " LEFT JOIN TripExpense TE ON TE.expenseID = R.CASH_NATURE_PAYMENT_ID AND R.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_DEBIT+""
						+ " Where R.CASH_BOOK_ID='" + cashBoookId + "' AND R.CASH_DATE BETWEEN '" + dateRangeFrom
						+ "' AND '" + dateRangeTo + "' AND R.COMPANY_ID = " + companyId
						+ " AND R.markForDelete = 0  ORDER BY R.CASH_DATE ASC",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<CashBookDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<CashBookDto>();
			CashBookDto list = null;
			for (Object[] result : results) {
				list = new CashBookDto();

				list.setCASH_BOOK_NO((String) result[0]);
				list.setCASH_DATE_ON((Date) result[1]);
				list.setCASH_AMOUNT((Double) result[2]);
				list.setCASH_NATURE_PAYMENT((String) result[3]);
				list.setCASH_VOUCHER_NO((String) result[4]);
				// list.setCASH_PAYMENT_TYPE(PaymentTypeConstant.getPaymentTypeName((short)
				// result[5]));
				list.setPAYMENT_TYPE_ID((short) result[5]);
				list.setIncomeName((String) result[6]);
				list.setExpenseName((String) result[7]);
				list.setCASH_NATURE_PAYMENT_ID((Integer) result[8]);
				
				if(list.getCASH_NATURE_PAYMENT() == null) {
					if(list.getIncomeName() != null) {
						list.setCASH_NATURE_PAYMENT(list.getIncomeName());
					}
					if(list.getExpenseName() != null) {
						list.setCASH_NATURE_PAYMENT(list.getExpenseName());
					}
					if(list.getIncomeName() == null && list.getExpenseName() == null) {
						list.setCASH_NATURE_PAYMENT("--");
					}
				}
				list.setCASHID((long) result[9]);
				list.setCASH_NUMBER((long) result[10]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Override
	public CashBookName getUserDefaultCashBook(List<CashBookName> cashBookNameList) throws Exception {
		CashBookName bookName = null;
		try {
			for (CashBookName cashBookName : cashBookNameList) {
				if (cashBookName.getCASHBOOK_NAME().matches("MAIN.*CASH.*")) {
					bookName = cashBookName;
					break;
				}
			}
			if (bookName == null) {
				bookName = cashBookNameList.get(0);
			}
			return bookName;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public CashBookBalance Validate_Last_DayCashBook_CloseOrNot(Date cashDate, Integer CASH_BOOK_ID, short Status,
			Integer companyId) throws Exception {

		try {
			
			synchronized (xMutexFactory.getMutex(CASH_BOOK_ID)) {
				return CashBalanceRepository.Validate_Last_DayCashBook_CloseOrNot(cashDate, CASH_BOOK_ID, Status,
						companyId);
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional
	public CashBookBalance checkLastDayEntryCreatedOrNot(Date cashDate, Integer CASH_BOOK_ID, Integer companyId) throws Exception {

		try {
			synchronized (xMutexFactory.getMutex(CASH_BOOK_ID)) {
				return CashBalanceRepository.checkLastDayEntryPresentOrNot(cashDate, CASH_BOOK_ID,companyId);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	@Override
	@Transactional
	public List<CashBook> checkLastDayCashBookCreatedOrNot(Date cashDate, Integer CASH_BOOK_ID, Integer companyId) throws Exception {
		try {
			synchronized (xMutexFactory.getMutex(CASH_BOOK_ID)) {
				String date	 = dateFormat.format(cashDate);
				java.util.Date utildate = dateFormat.parse(date);
				java.sql.Date cashbookDate = new java.sql.Date(utildate.getTime());
				return CashRepository.checkLastDayCashBookPresentOrNot(cashbookDate, CASH_BOOK_ID,companyId);
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public CashBookDto getCashBookByNumber(Long cashBookNumber, CustomUserDetails userDetails) throws Exception {
		javax.persistence.Query query = null;
		try {
			
			if (!companyConfigurationService.getCashBookWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT CB.CASHID, CB.CASH_NUMBER, CB.CASH_BOOK_NO, CB.CASH_BOOK_ID, CB.PAYMENT_TYPE_ID, CB.CASH_DATE, CB.CASH_VOUCHER_NO,"
						+ " CB.CASH_AMOUNT, CB.CASH_NATURE_PAYMENT_ID, TI.incomeName , TE.expenseName, CB.CASH_PAID_RECEIVED, CB.CASH_PAID_BY,"
						+ " CB.CASH_STATUS_ID, CB.CASH_APPROVAL_STATUS_ID, CB.CASH_APPROVALBY, CB.CASH_APPROVALCOMMENT, CB.CASH_APPROVALDATE,"
						+ " CB.CASH_REFERENCENO, CB.CASH_GSTNO, CB.CASH_DOCUMENT, CB.CASH_DOCUMENT_ID, CB.DRIVER_ID, CB.CREATEDBY, CB.LASTMODIFIEDBY,"
						+ " CB.CREATED_DATE, CB.LASTUPDATED_DATE, CB.CASH_NATURE_PAYMENT "
						+ " FROM CashBook  AS CB "
						+ " LEFT JOIN TripIncome AS TI ON TI.incomeID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_CREDIT+""
						+ " LEFT JOIN TripExpense AS TE ON TE.expenseID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_DEBIT+""
						+ " Where CB.CASH_NUMBER = "+cashBookNumber+" AND CB.COMPANY_ID = "+userDetails.getCompany_id()+" AND CB.markForDelete = 0");

			}else {

				query = entityManager.createQuery(
						"SELECT CB.CASHID, CB.CASH_NUMBER, CB.CASH_BOOK_NO, CB.CASH_BOOK_ID, CB.PAYMENT_TYPE_ID, CB.CASH_DATE, CB.CASH_VOUCHER_NO,"
						+ " CB.CASH_AMOUNT, CB.CASH_NATURE_PAYMENT_ID, TI.incomeName , TE.expenseName, CB.CASH_PAID_RECEIVED, CB.CASH_PAID_BY,"
						+ " CB.CASH_STATUS_ID, CB.CASH_APPROVAL_STATUS_ID, CB.CASH_APPROVALBY, CB.CASH_APPROVALCOMMENT, CB.CASH_APPROVALDATE,"
						+ " CB.CASH_REFERENCENO, CB.CASH_GSTNO, CB.CASH_DOCUMENT, CB.CASH_DOCUMENT_ID, CB.DRIVER_ID, CB.CREATEDBY, CB.LASTMODIFIEDBY,"
						+ " CB.CREATED_DATE, CB.LASTUPDATED_DATE, CB.CASH_NATURE_PAYMENT "
						+ " FROM CashBook  AS CB "
						+ " INNER JOIN CashBookPermission CBP ON CBP.cashBookId = CB.CASH_BOOK_ID AND CBP.user_Id = "+userDetails.getId()+""
						+ " LEFT JOIN TripIncome AS TI ON TI.incomeID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_CREDIT+""
						+ " LEFT JOIN TripExpense AS TE ON TE.expenseID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_DEBIT+""
						+ " Where CB.CASH_NUMBER = "+cashBookNumber+" AND CB.COMPANY_ID = "+userDetails.getCompany_id()+" AND CB.markForDelete = 0");

			
			}
			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			CashBookDto cash;
			if (result != null) {

				 cash = new CashBookDto();

				cash.setCASHID((long) result[0]);
				cash.setCASH_NUMBER((long) result[1]);
				cash.setCASH_BOOK_NO((String) result[2]);
				cash.setCASH_BOOK_ID((Integer) result[3]);
				cash.setPAYMENT_TYPE_ID((short) result[4]);
				cash.setCASH_DATE_ON((Date) result[5]);
				cash.setCASH_VOUCHER_NO((String) result[6]);
				cash.setCASH_AMOUNT((Double) result[7]);
				cash.setCASH_NATURE_PAYMENT_ID((Integer) result[8]);
				cash.setIncomeName((String) result[9]);
				cash.setExpenseName((String) result[10]);
				cash.setCASH_PAID_RECEIVED((String) result[11]);
				cash.setCASH_PAID_BY((String) result[12]);
				cash.setCASH_STATUS_ID((short) result[13]);
				cash.setCASH_APPROVAL_STATUS_ID((short) result[14]);
				cash.setCASH_APPROVALBY((String) result[15]);
				cash.setCASH_APPROVALCOMMENT((String) result[16]);
				cash.setCASH_APPROVALDATE_ON((Date) result[17]);
				cash.setCASH_REFERENCENO((String) result[18]);
				cash.setCASH_GSTNO((String) result[19]);
				cash.setCASH_DOCUMENT((boolean) result[20]);
				cash.setCASH_DOCUMENT_ID((Long) result[21]);
				cash.setDRIVER_ID((Integer) result[22]);
				cash.setCREATEDBY((String) result[23]);
				cash.setLASTMODIFIEDBY((String) result[24]);
				cash.setCREATED((Date) result[25]);
				cash.setLASTUPDATED((Date) result[26]);
				cash.setCASH_NATURE_PAYMENT((String) result[27]);
				
				
				return cash;
			

			} else {
				return null;
			}
		

		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public CashBookDto getCashBookIdByNumber(Long cashBookNumber, CustomUserDetails userDetails) throws Exception {
		javax.persistence.Query query = null;
		try {
			
			if (!companyConfigurationService.getCashBookWisePermission(userDetails.getCompany_id(),
					PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG)) {
				query = entityManager.createQuery(
						"SELECT CB.CASHID, CB.CASH_NUMBER "
						+ " FROM CashBook  AS CB "
						+ " Where CB.CASH_NUMBER = "+cashBookNumber+" AND CB.COMPANY_ID = "+userDetails.getCompany_id()+" AND CB.markForDelete = 0");

			}else {

				query = entityManager.createQuery(
						"SELECT CB.CASHID, CB.CASH_NUMBER "
						+ " FROM CashBook  AS CB "
						+ " INNER JOIN CashBookPermission CBP ON CBP.cashBookId = CB.CASH_BOOK_ID AND CBP.user_Id = "+userDetails.getId()+""
						+ " Where CB.CASH_NUMBER = "+cashBookNumber+" AND CB.COMPANY_ID = "+userDetails.getCompany_id()+" AND CB.markForDelete = 0");

			
			}
			
			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			CashBookDto cash;
			if (result != null) {

				 cash = new CashBookDto();

				cash.setCASHID((long) result[0]);
				cash.setCASH_NUMBER((long) result[1]);
				
				
				return cash;
			

			} else {
				return null;
			}
		

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public CashBookName getMainCashBookDetails(String MAINCASHBOOK_NAME, Integer companyId) throws Exception {
		try {

			return cashBookNameRepository.getMainCashBookDetails(MAINCASHBOOK_NAME, companyId);
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.fleetop.persistence.serviceImpl.ICashBookService#
	 * Validate_Last_DayCashBook_CloseOrNot_list(java.util.Date, java.lang.String)
	 */
	@Transactional
	public List<CashBookBalance> Validate_Last_DayCashBook_CloseOrNot_list(Date cashDate, String CASH_BOOK_Name) {

		return CashBalanceRepository.Validate_Last_DayCashBook_CloseOrNot_list(cashDate, CASH_BOOK_Name);
	}

	@Transactional
	public void Update_Edit_Based_Update_Amount(Double nEWCASH_AMOUNT, Long old_CASHID) {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		CashRepository.Update_Edit_Based_Update_Amount(nEWCASH_AMOUNT, old_CASHID, userDetails.getCompany_id());
	}
	
	@Override
	public List<CashBookDto> getPaymentDetailsWithDateRange(String fromDate , String toDate, String payment, Integer CASH_BOOK_ID, CustomUserDetails	userDetails)
			throws Exception {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.CASH_BOOK_NO, R.CASH_DATE, COALESCE(ROUND(R.CASH_AMOUNT,2),0), R.CASH_NATURE_PAYMENT, R.CASH_VOUCHER_NO, R.PAYMENT_TYPE_ID"
				+ "  FROM CashBook as R "
				+ " Where R.CASH_BOOK_ID = "+CASH_BOOK_ID+" AND R.CASH_NATURE_PAYMENT='" + payment + "' AND R.CASH_DATE BETWEEN '" + fromDate
				+ "' AND '" + toDate + "' AND R.COMPANY_ID = " + userDetails.getCompany_id()
				+ " AND R.markForDelete = 0  ORDER BY R.CASH_DATE ASC",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<CashBookDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<CashBookDto>();
			CashBookDto list = null;
			for (Object[] result : results) {
				list = new CashBookDto();

				list.setCASH_BOOK_NO((String) result[0]);
				list.setCASH_DATE_ON((Date) result[1]);
				list.setCASH_AMOUNT((Double) result[2]);
				list.setCASH_NATURE_PAYMENT((String) result[3]);
				list.setCASH_VOUCHER_NO((String) result[4]);
				list.setPAYMENT_TYPE_ID((short) result[5]);

				Dtos.add(list);
			}
		}
		return Dtos;
	}
	
	@Override
	public List<CashBookDto> getPaymentDetailsWithDateRange(String fromDate , String toDate, Integer payment, Integer CASH_BOOK_ID, CustomUserDetails	userDetails) throws Exception {
		TypedQuery<Object[]> queryt = entityManager.createQuery(
				"SELECT R.CASH_BOOK_NO, R.CASH_DATE, COALESCE(ROUND(R.CASH_AMOUNT,2),0), R.CASH_NATURE_PAYMENT, R.CASH_VOUCHER_NO, R.PAYMENT_TYPE_ID,"
				+ " TI.incomeName , TE.expenseName, R.CASH_NATURE_PAYMENT_ID"
				+ "  FROM CashBook as R "
				+ " LEFT JOIN TripIncome AS TI ON TI.incomeID = R.CASH_NATURE_PAYMENT_ID  AND R.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_CREDIT+""
				+ " LEFT JOIN TripExpense AS TE ON TE.expenseID = R.CASH_NATURE_PAYMENT_ID  AND R.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_DEBIT+""
				+ " Where R.CASH_BOOK_ID = "+CASH_BOOK_ID+" AND R.CASH_NATURE_PAYMENT_ID=" + payment + " AND R.CASH_DATE BETWEEN '" + fromDate
				+ "' AND '" + toDate + "' AND R.COMPANY_ID = " + userDetails.getCompany_id()
				+ " AND R.markForDelete = 0  ORDER BY R.CASH_DATE ASC",
				Object[].class);
		List<Object[]> results = queryt.getResultList();

		List<CashBookDto> Dtos = null;
		if (results != null && !results.isEmpty()) {
			Dtos = new ArrayList<CashBookDto>();
			CashBookDto list = null;
			for (Object[] result : results) {
				list = new CashBookDto();

				list.setCASH_BOOK_NO((String) result[0]);
				list.setCASH_DATE_ON((Date) result[1]);
				list.setCASH_AMOUNT((Double) result[2]);
				list.setCASH_NATURE_PAYMENT((String) result[3]);
				list.setCASH_VOUCHER_NO((String) result[4]);
				list.setPAYMENT_TYPE_ID((short) result[5]);
				list.setIncomeName((String) result[6]);
				list.setExpenseName((String) result[7]);
				list.setCASH_NATURE_PAYMENT_ID((Integer) result[8]);
				
				if(list.getCASH_NATURE_PAYMENT() == null) {
					if(list.getIncomeName() != null) {
						list.setCASH_NATURE_PAYMENT(list.getIncomeName());
					}else {
						list.setCASH_NATURE_PAYMENT(list.getExpenseName());
					}
				}

				Dtos.add(list);
			}
		}
		return Dtos;
	}

	@Override
	public void save(org.fleetopgroup.persistence.document.CashBookDocument cashDoc) throws Exception {
		cashDoc.set_id(sequenceCounterService.getNextSequence(SequenceCounterConstant.SEQUENCE_KEY_CASH_BOOK_DOCUMENT));
		mongoTemplate.save(cashDoc);
	}

	@Override
	public void transferCahBookDocument(List<CashBookDocument> list) throws Exception {
		org.fleetopgroup.persistence.document.CashBookDocument			cashBookDocument		= null;
		List<org.fleetopgroup.persistence.document.CashBookDocument> 	cashBookDocumentList	= null;
		try {
			if(list != null && !list.isEmpty()) {
				cashBookDocumentList	= new ArrayList<>();
				for(CashBookDocument	document : list) {
					cashBookDocument	= new org.fleetopgroup.persistence.document.CashBookDocument();
					
					cashBookDocument.set_id(document.getCASHDOCID());
					cashBookDocument.setCASHID(document.getCASHID());
					cashBookDocument.setCASH_CONTENT(document.getCASH_CONTENT());
					cashBookDocument.setCASH_CONTENT_TYPE(document.getCASH_CONTENT_TYPE());
					cashBookDocument.setCASH_FILENAME(document.getCASH_FILENAME());
					cashBookDocument.setCOMPANY_ID(document.getCOMPANY_ID());
					cashBookDocument.setCREATEDBY(document.getCREATEDBY());
					cashBookDocument.setLASTMODIFIEDBY(document.getLASTMODIFIEDBY());
					cashBookDocument.setMarkForDelete(document.isMarkForDelete());
					cashBookDocument.setCREATED_DATE(document.getCREATED_DATE());
					cashBookDocument.setLASTUPDATED_DATE(document.getLASTUPDATED_DATE());
					
					cashBookDocumentList.add(cashBookDocument);
				}
				mongoTemplate.insert(cashBookDocumentList, org.fleetopgroup.persistence.document.CashBookDocument.class);
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e);
		}
	}
	
	@Override
	public long getMaxCashBookId() throws Exception {
		try {
			return cashBookDocumentRepository.getCashBookDocumentMaxId();
		} catch (Exception e) {
			return 0;
		}
		
	}
	
	@Override
	public void runThreadForCashBookHistory(CashBookHistory	cashBookHistory) throws Exception{

		try {
				new Thread() {
					public void run() {
						try {
							cashBookHistoryRepository.save(cashBookHistory);
						} catch (Exception e) {
							LOGGER.error("Error : "+e);
						}
					}
				}.start();
		} catch (Exception e) {
			
		} finally {

		}
	}

	@Override
	public CashBook getCashBookToReopenTripCollection(Integer cashBookId, Date cashDate, String term, Integer companyId) throws Exception {
		try {
			return CashRepository.getCashBookToReopenTripCollection(cashBookId, cashDate, term, companyId);
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional
	public ValueObject updateCashVoucherNoByCashId(ValueObject valueObject) throws Exception {
		
		ValueObject				valOutObject				= null;
		String					voucherNumber				= null;
		CustomUserDetails 		userDetails 				= null;
		List<CashBook>			cashBookList				= null;
		CashBook 				cashBook 					= null; 
		CashBookHistory			cashBookHistory 			= null; 
		Long					cashBookId					= (long)0;
		
		try {
			valOutObject		= new ValueObject();
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			voucherNumber		= valueObject.getString("updatedVoucherNo");
			cashBookId			= valueObject.getLong("cashBookId");

			cashBook 			= getCashBookByID(cashBookId);
			cashBookHistory	 	= CBBL.getCashBookHistoryModel(cashBook);
			
			cashBookList		= CashRepository.Validate_CashBook_value(voucherNumber, userDetails.getCompany_id());
			
			if(cashBookList != null && cashBookList.size() > 0) {
				valOutObject.put("alreadyExist", true);
			} else {
				Timestamp updateDate = new java.sql.Timestamp((new java.util.Date()).getTime());
				/*
				 * Update Voucher no in Cash Book
				 */
				CashRepository.updateCashBookVoucherNumber(voucherNumber, userDetails.getName(), updateDate, cashBookId, userDetails.getCompany_id());
				/*
				 * Insert into Cash Book History
				 */
				runThreadForCashBookHistory(cashBookHistory);
				valOutObject.put("updated", true);
			}
		
			return valOutObject;
		} catch (Exception e) {
			throw e;
		} finally {
			valOutObject				= null;   
			voucherNumber				= null;   
			userDetails 				= null;   
			cashBookList				= null;
			cashBook 					= null;
			cashBookHistory 			= null;
			cashBookId					= (long)0;
		}
	}
	
	@Override
	public CashBookDto getCashBookByID(Long CashBookid, Integer companyId) {
		try {
			javax.persistence.Query query = entityManager.createQuery(
					"SELECT CB.CASHID, CB.CASH_NUMBER, CB.CASH_BOOK_NO, CB.CASH_BOOK_ID, CB.PAYMENT_TYPE_ID, CB.CASH_DATE, CB.CASH_VOUCHER_NO,"
					+ " CB.CASH_AMOUNT, CB.CASH_NATURE_PAYMENT_ID, TI.incomeName , TE.expenseName, CB.CASH_PAID_RECEIVED, CB.CASH_PAID_BY,"
					+ " CB.CASH_STATUS_ID, CB.CASH_APPROVAL_STATUS_ID, CB.CASH_APPROVALBY, CB.CASH_APPROVALCOMMENT, CB.CASH_APPROVALDATE,"
					+ " CB.CASH_REFERENCENO, CB.CASH_GSTNO, CB.CASH_DOCUMENT, CB.CASH_DOCUMENT_ID, CB.DRIVER_ID, CB.CREATEDBY, CB.LASTMODIFIEDBY,"
					+ " CB.CREATED_DATE, CB.LASTUPDATED_DATE, CB.CASH_NATURE_PAYMENT "
					+ " FROM CashBook  AS CB "
					+ " LEFT JOIN TripIncome AS TI ON TI.incomeID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_CREDIT+""
					+ " LEFT JOIN TripExpense AS TE ON TE.expenseID = CB.CASH_NATURE_PAYMENT_ID  AND CB.PAYMENT_TYPE_ID = "+CashBookPaymentType.PAYMENT_TYPE_DEBIT+""
					+ " WHERE CB.CASHID = "+CashBookid+" AND CB.COMPANY_ID = "+companyId+" AND CB.markForDelete = 0");

			Object[] result = null;
			try {
				result = (Object[]) query.getSingleResult();
			} catch (NoResultException nre) {
				// Ignore this because as per your logic this is ok!
			}

			CashBookDto cash;
			if (result != null) {

				 cash = new CashBookDto();

				cash.setCASHID((long) result[0]);
				cash.setCASH_NUMBER((long) result[1]);
				cash.setCASH_BOOK_NO((String) result[2]);
				cash.setCASH_BOOK_ID((Integer) result[3]);
				cash.setPAYMENT_TYPE_ID((short) result[4]);
				cash.setCASH_DATE_ON((Date) result[5]);
				cash.setCASH_VOUCHER_NO((String) result[6]);
				cash.setCASH_AMOUNT((Double) result[7]);
				cash.setCASH_NATURE_PAYMENT_ID((Integer) result[8]);
				cash.setIncomeName((String) result[9]);
				cash.setExpenseName((String) result[10]);
				cash.setCASH_PAID_RECEIVED((String) result[11]);
				cash.setCASH_PAID_BY((String) result[12]);
				cash.setCASH_STATUS_ID((short) result[13]);
				cash.setCASH_APPROVAL_STATUS_ID((short) result[14]);
				cash.setCASH_APPROVALBY((String) result[15]);
				cash.setCASH_APPROVALCOMMENT((String) result[16]);
				cash.setCASH_APPROVALDATE_ON((Date) result[17]);
				cash.setCASH_REFERENCENO((String) result[18]);
				cash.setCASH_GSTNO((String) result[19]);
				cash.setCASH_DOCUMENT((boolean) result[20]);
				cash.setCASH_DOCUMENT_ID((Long) result[21]);
				cash.setDRIVER_ID((Integer) result[22]);
				cash.setCREATEDBY((String) result[23]);
				cash.setLASTMODIFIEDBY((String) result[24]);
				cash.setCREATED((Date) result[25]);
				cash.setLASTUPDATED((Date) result[26]);
				cash.setCASH_NATURE_PAYMENT((String) result[27]);
				
				
				return cash;
			

			} else {
				return null;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	@Transactional
	public void checkCashBookBalanceForMissingEntries(ValueObject valueObject) throws Exception {
		
		List<CashBookDto>		cashBookDtos		= null;
		Timestamp				startDate			= null;
		Timestamp				endDate				= null;
		try {
			
			startDate	= (Timestamp) valueObject.get("startDate");
			endDate	= (Timestamp) valueObject.get("endDate");
			
			cashBookDtos	= getAddedMissingEntriesForADay(startDate, endDate);
			
			if(cashBookDtos != null && !cashBookDtos.isEmpty()) {
				checkAndCorrectDebitAndCreditAmountOnMissingEntries(cashBookDtos);
				checkAndCorrectAllDayBalanceOnMissingEntries(cashBookDtos);
			}
			
			
			//System.err.println("cashBookDtos : "+cashBookDtos);
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	@Transactional
	@Override
	public void checkAndCorrectDebitAndCreditAmountOnMissingEntries(List<CashBookDto>  cashBookList)throws Exception{
		 List<java.util.Date>	dates	= null;
		try {
			if(cashBookList != null && !cashBookList.isEmpty()) {
				for(CashBookDto	bookDto : cashBookList) {
					dates	=	DateTimeUtility.getDaysBetweenDates(bookDto.getCASH_DATE_ON(), new Date());
					if(dates != null && !dates.isEmpty()) {
						for(Date 	cashDate : dates) {
							checkAndCorrectDebitAndCreditAmountOnMissingEntries(cashDate, bookDto.getCASH_BOOK_ID(), bookDto.getCompanyId());
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Transactional
	@Override
	public void checkAndCorrectAllDayBalanceOnMissingEntries(List<CashBookDto>  cashBookList)throws Exception{
		 List<java.util.Date>	dates	= null;
		try {
			if(cashBookList != null && !cashBookList.isEmpty()) {
				for(CashBookDto	bookDto : cashBookList) {
					dates	=	DateTimeUtility.getDaysBetweenDates(bookDto.getCASH_DATE_ON(), new Date());
					if(dates != null && !dates.isEmpty()) {
						for(Date 	cashDate : dates) {
							if(checkAndCorrectAllDayBalanceAmountOnMissingEntries(cashDate, bookDto.getCASH_BOOK_ID(), bookDto.getCompanyId())) {
								break;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	@Transactional
	@Override
	public boolean checkAndCorrectAllDayBalanceAmountOnMissingEntries(Date cashDate, Integer cashBookId, Integer companyId) throws Exception {
		Date					previousDate		= null;
		CashBookBalance			preBookBalance		= null;
		CashBookBalance			currBookBalance		= null;
		boolean					cashBookNotClosed	= false;
		try {
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(cashDate);
			cal.add(Calendar.DATE, -1);
			previousDate = cal.getTime();
			
			preBookBalance	=	Validate_Last_DayCashBook_CloseOrNot(previousDate, cashBookId, CashBookStatus.CASH_BOOK_STATUS_CLOSED, companyId);
			
			if(preBookBalance != null) {
				
				Double previousAllDayBalanceAmt	= preBookBalance.getCASH_ALL_BALANCE();
				currBookBalance	=	Validate_Last_DayCashBook_CloseOrNot(cashDate, cashBookId, CashBookStatus.CASH_BOOK_STATUS_CLOSED, companyId);
				if(currBookBalance != null) {
					Double currAllDayBalanceAmt	= currBookBalance.getCASH_ALL_BALANCE();
					Double currDayBalanceAmt	= currBookBalance.getCASH_DAY_BALANCE();
					Double actualAllDayAmt		= currDayBalanceAmt + previousAllDayBalanceAmt;
					if(actualAllDayAmt.compareTo(currAllDayBalanceAmt) != 0) {
						System.err.println("Wrong All Day Balance Found Correcting it..");
						CashBalanceRepository.upateAllDayBalanceAmount(actualAllDayAmt, cashBookId, cashDate);
					}
					
				}else {
					cashBookNotClosed	= true;
				}
				
			}else {
				cashBookNotClosed	= true;
			}
			
			return cashBookNotClosed;
			
		} catch (Exception e) {
			throw e;
		}finally {
			previousDate		= null;
			preBookBalance		= null;
			currBookBalance		= null;
		}
	}
	
	@Transactional
	@Override
	public void checkAndCorrectDebitAndCreditAmountOnMissingEntries(Date cashDate, Integer cashBookId, Integer companyId) throws Exception {
		Double					debitAmount				= 0.0;
		Double					creditAmount			= 0.0;
		Double					balanceAmount			= 0.0;
		List<CashBookBalance>	balances				= null;
		boolean					bothDebitCReditNUll	= false;
		try {
			
			debitAmount		=	CashRepository.getTotalDebitAmountForDate(cashBookId, cashDate);
			creditAmount	=	CashRepository.getTotalCreditAmountForDate(cashBookId, cashDate);
			
			if(debitAmount == null && creditAmount == null) {
				bothDebitCReditNUll	= true;
			}
			
			if(debitAmount == null) {
				debitAmount = 0.0;
			}
			
			if(creditAmount == null) {
				creditAmount = 0.0;
			}
			
			
			if(!bothDebitCReditNUll) {
					balanceAmount	= 	creditAmount - debitAmount;
					balances		=	Validate_Last_DayCashBook_CloseOrNot_list(cashDate, cashBookId, companyId);
					
					if(balanceAmount.compareTo(balances.get(0).getCASH_DAY_BALANCE())  != 0 || creditAmount.compareTo(balances.get(0).getCASH_CREDIT())  != 0 || debitAmount.compareTo(balances.get(0).getCASH_DEBIT())  != 0) {
						System.err.println(" wrong record found for debit credit balance on date : "+cashDate+" amount correcting it...");
						CashBalanceRepository.upateDebitCreditBalanceAmount(debitAmount, creditAmount, balanceAmount, cashBookId, cashDate);
					}
			}
			
			
			
			
			
		} catch (Exception e) {
			throw e;
		}finally {
			debitAmount		= null;
			creditAmount	= null;
			balanceAmount	= null;
			balances		= null;
		}
	}
	
	@Override
	public List<CashBookDto> getAddedMissingEntriesForADay(Timestamp startDate, Timestamp endDate) throws Exception {
		HashMap<Integer, CashBookDto> 		cashBookDateHM	= null;
		CashBookDto						tempDto				= null;
		try {
			cashBookDateHM	= new HashMap<>(); 
			TypedQuery<Object[]> queryt = entityManager.createQuery(
					"SELECT distinct R.CASH_DATE, R.CASH_BOOK_ID, R.COMPANY_ID "
					+ "  FROM CashBook as R "
					+ " Where R.CREATED_DATE BETWEEN '"+startDate+"' AND '"+endDate+"' AND R.CASH_DATE < '"+startDate+"'"
					+ " AND R.markForDelete = 0  AND R.CASH_NATURE_PAYMENT_ID is not null  ORDER BY R.CASH_DATE ASC",
					Object[].class);
			List<Object[]> results = queryt.getResultList();

			List<CashBookDto> Dtos = null;
			if (results != null && !results.isEmpty()) {
				Dtos = new ArrayList<CashBookDto>();
				CashBookDto list = null;
				for (Object[] result : results) {
					list = new CashBookDto();

					list.setCASH_DATE_ON((Date) result[0]);
					list.setCASH_BOOK_ID((Integer) result[1]);
					list.setCompanyId((Integer) result[2]);
					

					//Dtos.add(list);
					
					tempDto	=	cashBookDateHM.get(list.getCASH_BOOK_ID());
					
					if(tempDto == null) {
						cashBookDateHM.put(list.getCASH_BOOK_ID(), list);
						Dtos.add(list);
					}
					
				}
			}
			return Dtos;
		
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public CashBookBalance getAllTodayCashBookBalance(Date currentDate, Integer cashBookId, Integer CompanyId, short status) throws Exception{
		String date	 = dateFormat.format(currentDate);
		java.util.Date utildate = dateFormat.parse(date);
		java.sql.Date cashDate = new java.sql.Date(utildate.getTime());
		return CashBalanceRepository.getAllTodayCashBookBalance(cashDate,CompanyId,cashBookId,status);
	}
	
	@SuppressWarnings("unused")
	@Override
	@Transactional
	public ValueObject saveCloseCashbookBalance(CashBookBalanceDto CashBook, boolean isfromscheduler) throws Exception{
		ValueObject				valueOutObject				= null;
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails 			userDetails 	= null;
		HashMap<String, Object>     configuration	= null;
		boolean						cashbookStatus	= false;
		try {
			valueOutObject				= new ValueObject();
			
			Date closingDate = null;
			
			configuration	= companyConfigurationService.getCompanyConfiguration(CashBook.getCOMPANY_ID(),PropertyFileConstants.CASHBOOK_CONFIG);
			cashbookStatus	= (boolean) configuration.getOrDefault(CashBookStatus.CASH_BOOK_STATUS, false);
			
			try {
				// fuel date converted String to date Format
				if (CashBook.getCASH_DATE() != null) {
					java.util.Date date = dateFormat.parse(CashBook.getCASH_DATE());
					//java.sql.Date cashDate = (java.sql.Date) new Date(date.getTime());
					java.sql.Date cashDate = new java.sql.Date(date.getTime());
					closingDate = cashDate;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// check closing date not null
			if (closingDate != null) {
				CashBookName	bookName	=	CashBookNameService.get_CashBookName2(CashBook.getCASH_BOOK_ID(),CashBook.getCOMPANY_ID());
				List<TripDailyRouteSheet> validate	=	null;
				
				String linkingNotNeededToGroup = (String)configuration.get("linkingNotNeededToGroup");  
				String[] linkingNotNeededArray	= 	linkingNotNeededToGroup.split(",");
				
				List<Integer> cashBookIdList = new ArrayList<>();
				
				for(int i= 0; i< linkingNotNeededArray.length; i++) {
					cashBookIdList.add(Integer.parseInt(linkingNotNeededArray[i]));
				}
				if(!cashBookIdList.contains(bookName.getNAMEID())) {
					validate	=	tripDailySheetService.validateForDepotTripDayCollectionClosed(closingDate+"", bookName.getVEHICLE_GROUP_ID(), TripDailySheetStatus.TRIP_STATUS_OPEN, CashBook.getCOMPANY_ID());
				}
				
				if(validate == null || validate.isEmpty()) {
					// check current date closed or not
					CashBookBalance validateCloseLast = Validate_Last_DayCashBook_CloseOrNot(closingDate,
							CashBook.getCASH_BOOK_ID(), CashBookStatus.CASH_BOOK_STATUS_CLOSED,
							CashBook.getCOMPANY_ID());
					
					// if true closed current cash message already closed
					if (validateCloseLast != null) {
						model.put("alreadyClosed", true);
						valueOutObject.put("alreadyClosed", true);
						return valueOutObject;
					} 
					else{
						int n = 1;
						Date openingDate = new Date(closingDate.getTime() - n * 24 * 3600 * 1000);
						
							CashBookName cashBookName = getMainCashBookDetails(
									CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME,
									CashBook.getCOMPANY_ID());
							
							
						// not closed cash book check last date book closed are
						// not
						// this check Before Begin Opening Balances
						CashBookBalance validateLastdays = Validate_Last_DayCashBook_CloseOrNot(openingDate,
								CashBook.getCASH_BOOK_ID(), CashBookStatus.CASH_BOOK_STATUS_CLOSED,
								CashBook.getCOMPANY_ID());
						
						if (validateLastdays != null) {
							// last day cash book closed only add Cash book
							// balance
							CashBookBalance CurrentBalance = Validate_Last_DayCashBook_CloseOrNot(
									closingDate, CashBook.getCASH_BOOK_ID(), CashBookStatus.CASH_BOOK_STATUS_OPEN,
									CashBook.getCOMPANY_ID());
							
							if (CurrentBalance != null) {
								// check Cash book name main book are not
								CashBookName cashBookName2 = getMainCashBookDetails(
										CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME,
										CashBook.getCOMPANY_ID());
								
								//CurrentBalance.getCASH_BOOK_ID() == cashBookName.getNAMEID()
								if ( CurrentBalance.getCASH_BOOK_ID().equals(cashBookName2.getNAMEID()) ) {
									// check sub cash book closed or not
									List<CashBookBalance> current = Validate_Sub_CashBook_Cloesd_or_not(
											CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK, closingDate,
											CashBookStatus.CASH_BOOK_STATUS_OPEN, CashBook.getCOMPANY_ID());
									if (current != null && !current.isEmpty()) {
										String subcashbook = "";
										for (CashBookBalance cashBookBalance : current) {
											subcashbook += cashBookBalance.getCASH_BOOK_NAME() + ", ";
										}
										valueOutObject.put("CloseSubCashBook", true);
										valueOutObject.put("SubCashBook", subcashbook);
										return valueOutObject;
									} else {
										// update Balance Main Cash book Name
										/* date */
										java.util.Date currentDateUpdate = new java.util.Date();
										Timestamp CASH_CLOSED_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());
										
										CashBalanceRepository.update_CloseDay_Balance(CashBookStatus.CASH_BOOK_STATUS_CLOSED,
												CashBook.getCASH_CLOSED_BY(), CashBook.getCASH_CLOSED_EMAIL(), CashBook.getCASH_REMARKS(),
												CASH_CLOSED_DATE, CurrentBalance.getCBBID(), CurrentBalance.getCOMPANY_ID(),CurrentBalance.getCASH_DAY_BALANCE() + validateLastdays.getCASH_ALL_BALANCE());
										valueOutObject.put("success", true);
									}
								}
								else {
									/* date */
									java.util.Date currentDateUpdate = new java.util.Date();
									Timestamp CASH_CLOSED_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());
									
									CashBalanceRepository.update_CloseDay_Balance(CashBookStatus.CASH_BOOK_STATUS_CLOSED,
											CashBook.getCASH_CLOSED_BY(), CashBook.getCASH_CLOSED_EMAIL(), CashBook.getCASH_REMARKS(),
											CASH_CLOSED_DATE, CurrentBalance.getCBBID(), CurrentBalance.getCOMPANY_ID(), CurrentBalance.getCASH_DAY_BALANCE() + validateLastdays.getCASH_ALL_BALANCE());
	
									// Add Sub Cash Book Balance Add main Cash
									// Book
									// Amount
									try {
										Double Credit = CurrentBalance.getCASH_CREDIT();
										CashBook cashCredit = CBBL.prepare_SubCashBook_To_MAin_Cashbook_Value(
												CurrentBalance, CashBookPaymentType.PAYMENT_TYPE_CREDIT, round(Credit, 2),
												CashBook.getCASH_CLOSED_BY(), CashBook.getCASH_BOOK_ID(),isfromscheduler);
										
										cashCredit.setCASH_BOOK_ID(cashBookName.getNAMEID());
										
										CashBookSequenceCounter sequenceCounter = cashBookSequenceService
												.findNextSequenceNumber(CashBook.getCOMPANY_ID());
										
										cashCredit.setCASH_NUMBER(sequenceCounter.getNextVal());
										registerNewCashBook(cashCredit);

										SaveToCashBookBalance_Update(cashCredit);
									} catch (Exception e) {
										e.printStackTrace();
									}
									// Add sub cash Book To add main Cash Book
									// Amount
									try {
										Double Debit = CurrentBalance.getCASH_DEBIT();
										CashBook cashDebit = CBBL.prepare_SubCashBook_To_MAin_Cashbook_Value(CurrentBalance,
												CashBookPaymentType.PAYMENT_TYPE_DEBIT, round(Debit, 2),
												CashBook.getCASH_CLOSED_BY(), CashBook.getCASH_BOOK_ID(),isfromscheduler);
										cashDebit.setCASH_BOOK_ID(cashBookName.getNAMEID());
										CashBookSequenceCounter sequenceCounter2 = cashBookSequenceService
												.findNextSequenceNumber(CashBook.getCOMPANY_ID());
										cashDebit.setCASH_NUMBER(sequenceCounter2.getNextVal());
										
										registerNewCashBook(cashDebit);
										
										SaveToCashBookBalance_Update(cashDebit);
									} catch (Exception e) {
										e.printStackTrace();
									}
									valueOutObject.put("successAddMain", true);
								}
								if(cashbookStatus) {
									updateCashBookStatus(closingDate, CashBook.getCASH_BOOK_ID(),CashBook.getCOMPANY_ID());
								}
							}
							else {
								/** This Balance To Create New Date Balance */
								// get the Cash to save CashBalance
								CashBookBalance cashBalance = CBBL.prepare_CLOSED_DAY_ADD_Value(CashBook);
								// save to databases
								cashBalance.setCOMPANY_ID(CashBook.getCOMPANY_ID());
								registerNewCashBookBalance(cashBalance);
							}
						} else {
							valueOutObject.put("CloseLastday", true);
							return valueOutObject;
						}
				}
				}else {
					valueOutObject.put("tripdayNotClosed", true);
					return valueOutObject;
				}

			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return valueOutObject;
		}

		return valueOutObject;
	}
	
	public void SaveToCashBookBalance_Update(CashBook cash) throws Exception {

		CashBookBalance validateBalance = Validate_CashBookBalance_value(cash.getCASH_DATE(),
				cash.getCASH_BOOK_ID(), cash.getCOMPANY_ID());

		try {
			if (validateBalance != null) {
				Long CBBID = validateBalance.getCBBID();
				/**
				 * cash Balance Already Create in DB To Update old Balance to New
				 */
				if (cash.getPAYMENT_TYPE_ID() == CashBookPaymentType.PAYMENT_TYPE_DEBIT) {
					Update_DEBIT_CashBalance_SubtrackBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED,
							dateFormat_SQL.format(cash.getCASH_APPROVALDATE()), cash.getCASH_BOOK_ID(),
							cash.getPAYMENT_TYPE_ID(), CBBID, cash.getCOMPANY_ID());
				} else {
					Update_CREDIT_CashBalance_AddBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED,
							dateFormat_SQL.format(cash.getCASH_APPROVALDATE()), cash.getCASH_BOOK_ID(),
							cash.getPAYMENT_TYPE_ID(), CBBID, cash.getCOMPANY_ID());
				}

			} else {

				/** This Balance To Create New Date Balance */
				// get the Cash to save CashBalance
				CashBookBalance cashBalance = CBBL.prepareCashBookBalance_Value(cash);
				// save to databases
				registerNewCashBookBalance(cashBalance);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	@Transactional
	public ValueObject saveCashBookMissingEntry(CashBookDto CashBook, MultipartFile file, boolean saveDriverBalanceInCashBook) throws Exception {
		
		ValueObject 				object  				= null;
		CustomUserDetails 			userDetails 		    = null;
		CashBookSequenceCounter 	sequenceCounter 		= null;
		List<CashBook> 				validate				= null;
		HashMap<String, Object> 	configuration			= null;
		CashBookBalance 			currentdatevalidate     = null;
		
			try {
				object 			= new ValueObject();
				userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
				
				if(!(boolean) configuration.get("isAutoVoucherNumber") || !CashBook.isVoucherType()) {
					validate = Validate_CashBook_value(CashBook.getCASH_VOUCHER_NO(), userDetails.getCompany_id());
				}
				if (validate != null && !validate.isEmpty()) {
					//return new ModelAndView("redirect:/addCashBookEntry.in?danger=true");
					object.put("danger", true);
					return object;
				} else{
					boolean ClosedValidate = false, ClosedCurent = false, checkCurrentEntry = false; 
					// get the issues Dto to save issues
					CashBook cash = CBBL.prepareCashBook_Value_MissingEntries(CashBook);
					
					sequenceCounter = cashBookSequenceService.findNextSequenceNumber(userDetails.getCompany_id());
					if (sequenceCounter == null) {
						//return new ModelAndView("redirect:/addCashBookEntry.in?sequenceNotFound=true");
						object.put("sequenceNotFound", true);
						return object;
					}
					cash.setCASH_NUMBER(sequenceCounter.getNextVal());
					cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_CLOSED);
					int n = 1;
					
					
					// this check Before Begin Opening Balances
					Date openingDate = new Date(cash.getCASH_DATE().getTime() - n * 24 * 3600 * 1000);
					//java.sql.Date openingDate = (java.sql.Date) new Date(cash.getCASH_DATE().getTime() - n * 24 * 3600 * 1000);
					
					CashBookBalance validateCloseLast = Validate_Last_DayCashBook_CloseOrNot(openingDate,
							cash.getCASH_BOOK_ID(), CashBookStatus.CASH_BOOK_STATUS_CLOSED, userDetails.getCompany_id());
					
					if (validateCloseLast != null) {
						// check yesterday cash book closed enter today entries
						ClosedValidate = true;
					} else {
						List<CashBookBalance> checkLastDayEntry = null;
								checkLastDayEntry = Validate_Last_DayCashBook_CloseOrNot_list(
										openingDate, cash.getCASH_BOOK_ID(), userDetails.getCompany_id());
							
							if(checkLastDayEntry != null && !checkLastDayEntry.isEmpty()) {
								checkCurrentEntry = true;
							}
							
							if(!checkCurrentEntry) {
								// check yesterday cash book not closed enter create close
								// balance entries
								ClosedValidate = true;
								/** This Balance To Create New Date Balance */
								// get the Cash to save CashBalance
								java.sql.Date sqlDate = new java.sql.Date(openingDate.getTime());
								CashBookBalance cashBalance = CBBL.prepare_CLOSED_LAST_Value(sqlDate, cash.getCASH_BOOK_NO());
								// save to databases
								cashBalance.setCOMPANY_ID(userDetails.getCompany_id());
								cashBalance.setCASH_BOOK_ID(cash.getCASH_BOOK_ID());
								registerNewCashBookBalance(cashBalance);
							}else {
								ClosedValidate = true;
							}
						}
					
					if (ClosedValidate) {
						
						if(!saveDriverBalanceInCashBook){
						 currentdatevalidate = Validate_Last_DayCashBook_CloseOrNot(
								cash.getCASH_DATE(), cash.getCASH_BOOK_ID(), CashBookStatus.CASH_BOOK_STATUS_CLOSED,
								userDetails.getCompany_id());
						}
						
						if (currentdatevalidate != null) {
							ClosedCurent = false;
						} else {
							ClosedCurent = true;
						}
						if (ClosedCurent) {
							
							if(saveDriverBalanceInCashBook) {
								List<CashBookBalance> validateBalance = Validate_CashBookBalance_valueByDateAndCashBookID(cash.getCASH_DATE(),
										cash.getCASH_BOOK_ID(), cash.getCOMPANY_ID());
								if (validateBalance == null) {
									/** This Balance To Create New Date Balance */
									// get the Cash to save CashBalance
									CashBookBalance cashBalance = CBBL.prepareCashBookBalance_Value(cash);
									// save to databases
									registerNewCashBookBalance(cashBalance);
								}
							}
							// save to databases
							if((boolean) configuration.get("isAutoVoucherNumber") && CashBook.isVoucherType()) {
								String 		voucherNumber	=	bookVoucherSequenceCounterService.getCashVoucherNumberAndUpdateNext(cash.getCASH_BOOK_ID(), userDetails.getCompany_id());
								cash.setCASH_VOUCHER_NO(voucherNumber);
							}
							
							if(file!=null) {
								if (!file.isEmpty()) {
	
									cash.setCASH_DOCUMENT(true);
								} else {
	
									cash.setCASH_DOCUMENT(false);
								}
							}
							
							registerNewCashBook(cash);

							// show the driver list in all
							object.put("success", true);
							// SaveToCashBookBalance_Update(cash);
							
							switch (cash.getCASH_BOOK_NO()) {
							case "MAIN-CASH-BOOK":

								switch (cash.getPAYMENT_TYPE_ID()) {
								case CashBookPaymentType.PAYMENT_TYPE_DEBIT:
									// Edit Amount Update Values
									Update_EDIT_DEBIT_CashBalance_AddBalance_Details(
											CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, cash.getCASH_DATE(),
											cash.getCASH_BOOK_ID(), cash.getPAYMENT_TYPE_ID(), userDetails.getCompany_id());

									// Update MainCash Book Amount Opening Balance
									// Amount
									Update_DEBIT_CashBalance_AfterBalance_Details(cash.getCASH_DATE(),
											cash.getCASH_BOOK_ID(), userDetails.getCompany_id());

									break;

								case CashBookPaymentType.PAYMENT_TYPE_CREDIT:

									Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(
											CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, cash.getCASH_DATE(),
											cash.getCASH_BOOK_ID(), cash.getPAYMENT_TYPE_ID(), userDetails.getCompany_id());

									// Update MainCash Book Amount Opening Balance
									// Amount
									Update_CREDIT_CashBalance_AfterBalance_Details(cash.getCASH_DATE(),
											cash.getCASH_BOOK_ID(), userDetails.getCompany_id());
									break;
								}
								break;

							default:

								String dateCB = dateFormat.format(cash.getCASH_DATE());
								String cBName = cash.getCASH_BOOK_NO();
								String CVoucher = cBName.substring(0, 3) + "-" + dateCB + "-"
										+ CashBookPaymentType.getPaymentTypeName(cash.getPAYMENT_TYPE_ID()).substring(0, 2)+"-"+cash.getCASH_BOOK_ID();

								// sub Cash Book
								switch (cash.getPAYMENT_TYPE_ID()) {
								case CashBookPaymentType.PAYMENT_TYPE_DEBIT:

									// Edit Amount Update Values 
									//updating debit and cash all and cash day balance
									Update_EDIT_DEBIT_CashBalance_AddBalance_Details(
											CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, cash.getCASH_DATE(),
											cash.getCASH_BOOK_ID(), cash.getPAYMENT_TYPE_ID(), userDetails.getCompany_id());

									Update_Missing_SUBCashBook_Debit_Credit_Balance(CVoucher, cash.getCASH_DATE(),
											cash.getCASH_AMOUNT(), cash.getPAYMENT_TYPE_ID(), cash.getCASH_BOOK_ID());

									break;

								case CashBookPaymentType.PAYMENT_TYPE_CREDIT:

									// Edit Amount Update Values
									Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(
											CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, cash.getCASH_DATE(),
											cash.getCASH_BOOK_ID(), cash.getPAYMENT_TYPE_ID(), userDetails.getCompany_id());

									Update_Missing_SUBCashBook_Debit_Credit_Balance(CVoucher, cash.getCASH_DATE(),
											cash.getCASH_AMOUNT(), cash.getPAYMENT_TYPE_ID(), cash.getCASH_BOOK_ID());

									break;
								}

								break;
							}
							
							if(file!=null) {
							if (!file.isEmpty()) {
								org.fleetopgroup.persistence.document.CashBookDocument cashDoc = new org.fleetopgroup.persistence.document.CashBookDocument();
								cashDoc.setCASHID(cash.getCASHID());
								try {

									byte[] bytes = file.getBytes();
									cashDoc.setCASH_FILENAME(file.getOriginalFilename());
									cashDoc.setCASH_CONTENT(bytes);
									cashDoc.setCASH_CONTENT_TYPE(file.getContentType());

									/** Set Status in Issues */
									cashDoc.setMarkForDelete(false);

									/** Set Created by email Id */
									cashDoc.setCREATEDBY(userDetails.getEmail());
									cashDoc.setLASTMODIFIEDBY(userDetails.getEmail());

									java.util.Date currentDateUpdate = new java.util.Date();
									Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

									/** Set Created Current Date */
									cashDoc.setCREATED_DATE(toDate);
									cashDoc.setLASTUPDATED_DATE(toDate);
									cashDoc.setCOMPANY_ID(userDetails.getCompany_id());
								} catch (IOException e) {
									e.printStackTrace();
								}

								// Note: Save cashDocDocument Details
								//CashBookService.add_CashBook_To_CashBookDocument(cashDoc);
									save(cashDoc);

								// Note: CashBookDocument ID Update CasdBook
								// Table Column
								Update_CashBook_Document_ID_to_CashBook(cashDoc.get_id(), true,
										cash.getCASHID());
							  }
						   }	
						}else {
							//return new ModelAndView("redirect:/addCashBookEntry.in?closedCB=true");
							object.put("closedCB", true);
							return object;
						}
						
						
					}else {
						//return new ModelAndView("redirect:/addCashBookEntry.in?closeLastDayBalance=true");
						object.put("closeLastDayBalance", true);
						return object;
					}
				}
				
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				sequenceCounter = null;
				userDetails = null;
			}
		
		return object;
	}
	
	private void Update_Missing_SUBCashBook_Debit_Credit_Balance(String CVoucher, java.util.Date date,
			Double NEWCASH_AMOUNT, short CASH_PAYMENT_TYPE, Integer Current_CashBook_id) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			CashBookName cashBookName = getMainCashBookDetails(
					CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME,
					userDetails.getCompany_id());

			// check Sub Cash Book Closed or not
			CashBook validateCloseSubCB = getSub_CashBookToVoucherNumber(CVoucher, date,
					CashBookStatus.CASH_BOOK_STATUS_CLOSED, cashBookName.getNAMEID());
			if (validateCloseSubCB != null) {

				
				switch (CASH_PAYMENT_TYPE) {
				case CashBookPaymentType.PAYMENT_TYPE_DEBIT:

					// missing Cash Book Entries update to add Main cashBook
					// Amount
						updateSubCashBook_CREDIT_Amount_EDit(NEWCASH_AMOUNT,
							validateCloseSubCB.getCASHID());

					// Edit Amount Update Values
						Update_EDIT_DEBIT_CashBalance_AddBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date, cashBookName.getNAMEID(),
							CASH_PAYMENT_TYPE, validateCloseSubCB.getCOMPANY_ID());

					// Update MainCash Book Amount Opening Balance Amount
						Update_DEBIT_CashBalance_AfterBalance_Details(date, Current_CashBook_id,
							validateCloseSubCB.getCOMPANY_ID());
					
						Update_DEBIT_CashBalance_AfterBalance_Details(date, cashBookName.getNAMEID(),
							validateCloseSubCB.getCOMPANY_ID());

					break;

				case CashBookPaymentType.PAYMENT_TYPE_CREDIT:

						updateSubCashBook_CREDIT_Amount_EDit(NEWCASH_AMOUNT,
							validateCloseSubCB.getCASHID());

					// Edit Amount Update Values
						Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date, cashBookName.getNAMEID(),
							CASH_PAYMENT_TYPE, validateCloseSubCB.getCOMPANY_ID());

					// Update MainCash Book Amount Opening Balance Amount
						Update_CREDIT_CashBalance_AfterBalance_Details(date, Current_CashBook_id,
							validateCloseSubCB.getCOMPANY_ID());
					
						Update_CREDIT_CashBalance_AfterBalance_Details(date, cashBookName.getNAMEID(),
							validateCloseSubCB.getCOMPANY_ID());

					break;
				}

			} else {
				// Note Not Closed Cash Book in
				switch (CASH_PAYMENT_TYPE) {
				case CashBookPaymentType.PAYMENT_TYPE_DEBIT:

					// Edit Amount Update Values After Add Amount Debit Total Value SUB Cash book
					// Details
					Update_EDIT_DEBIT_CashBalance_AddBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date, Current_CashBook_id,
							CASH_PAYMENT_TYPE, userDetails.getCompany_id());

					// Update Select CashBook Name Amount Closed Balance Amount
					Update_MAINCashBookBALANCE_AfterDebit_Credit_Balance(date, CASH_PAYMENT_TYPE, Current_CashBook_id,
							userDetails.getCompany_id());

					break;

				case CashBookPaymentType.PAYMENT_TYPE_CREDIT:
					// Edit Amount Update Values
					Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date, Current_CashBook_id,
							CASH_PAYMENT_TYPE, userDetails.getCompany_id());

					// Update MainCash Book Amount Opening Balance Amount
					Update_MAINCashBookBALANCE_AfterDebit_Credit_Balance(date, CASH_PAYMENT_TYPE, Current_CashBook_id,
							userDetails.getCompany_id());

					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void Update_MAINCashBookBALANCE_AfterDebit_Credit_Balance(java.util.Date CASH_DATE, short CASH_BOOK_PAYMENT,
			Integer CASH_BOOK_ID, Integer companyId) throws Exception {
		try {
			switch (CASH_BOOK_PAYMENT) {
			case CashBookPaymentType.PAYMENT_TYPE_DEBIT:

					Update_DEBIT_CashBalance_AfterBalance_Details(CASH_DATE, CASH_BOOK_ID, companyId);

				break;

			case CashBookPaymentType.PAYMENT_TYPE_CREDIT:

					Update_CREDIT_CashBalance_AfterBalance_Details(CASH_DATE, CASH_BOOK_ID, companyId);

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
