/**
 * 
 */
package org.fleetopgroup.persistence.bl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.fleetopgroup.constant.CashBookApprovalStatus;
import org.fleetopgroup.constant.CashBookBalanceStatus;
import org.fleetopgroup.constant.CashBookPaymentType;
import org.fleetopgroup.constant.CashBookStatus;
import org.fleetopgroup.persistence.dto.CashBookBalanceDto;
import org.fleetopgroup.persistence.dto.CashBookDto;
import org.fleetopgroup.persistence.dto.CashBookNameDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.CashBook;
import org.fleetopgroup.persistence.model.CashBookBalance;
import org.fleetopgroup.persistence.model.CashBookHistory;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.model.CashBookNameHistory;
import org.fleetopgroup.persistence.model.TripDailyGroupCollection;
import org.fleetopgroup.persistence.serviceImpl.ICashBookSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.web.util.ConvertIntegerToWord;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author fleetop
 *
 */
public class CashBookBL {

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatonlyDate = new SimpleDateFormat("dd-MMM-yyyy");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	public CashBookBL() {
		super();
	}

	public static List<java.util.Date> getDaysBetweenDates(java.util.Date startDate, java.util.Date endDate) {
		ArrayList<java.util.Date> dates = new ArrayList<java.util.Date>();
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(startDate);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(endDate);

		while (cal1.before(cal2) || cal1.equals(cal2)) {
			dates.add(cal1.getTime());
			cal1.add(Calendar.DATE, 1);
		}
		return dates;
	}

	public CashBook prepareCashBook_Value(CashBookDto cBookDto) {

		CashBook cash = new CashBook();
		cash.setCASHID(cBookDto.getCASHID());
		cash.setCASH_NUMBER(cBookDto.getCASH_NUMBER());
		cash.setCASH_BOOK_NO(cBookDto.getCASH_BOOK_NO());
		cash.setCASH_BOOK_ID(cBookDto.getCASH_BOOK_ID());
		cash.setCASH_VOUCHER_NO(cBookDto.getCASH_VOUCHER_NO());
		cash.setPAYMENT_TYPE_ID(cBookDto.getPAYMENT_TYPE_ID());
		// cash.setCASH_PAYMENT_TYPE(CashBookPaymentType.getPaymentTypeName(cBookDto.getPAYMENT_TYPE_ID()));
		try {
			// fuel date converted String to date Format
			if (cBookDto.getCASH_DATE() != null) {
				java.util.Date date = dateFormat.parse(cBookDto.getCASH_DATE());
				java.sql.Date cashDate = new Date(date.getTime());
				cash.setCASH_DATE(cashDate);
			}
			if (cBookDto.getCASH_APPROVALDATE() != null) {
				java.util.Date dateA = dateFormat.parse(cBookDto.getCASH_APPROVALDATE());
				java.sql.Date cashDateA = new Date(dateA.getTime());
				cash.setCASH_APPROVALDATE(cashDateA);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cBookDto.getCASH_AMOUNT() != null) {
			cash.setCASH_AMOUNT(cBookDto.getCASH_AMOUNT());
		} else {
			cash.setCASH_AMOUNT(0.0);
		}
		//cash.setCASH_NATURE_PAYMENT(cBookDto.getCASH_NATURE_PAYMENT());
		cash.setCASH_NATURE_PAYMENT_ID(cBookDto.getCASH_NATURE_PAYMENT_ID());
		cash.setCASH_PAID_RECEIVED(cBookDto.getCASH_PAID_RECEIVED());
		cash.setCASH_PAID_BY(cBookDto.getCASH_PAID_BY());

		cash.setCASH_REFERENCENO(cBookDto.getCASH_REFERENCENO());
		cash.setCASH_GSTNO(cBookDto.getCASH_GSTNO());

		cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_OPEN);
		// cash.setCASH_STATUS(CashBookStatus.CASH_BOOK_STATUS_OPEN_NAME);
		cash.setCASH_APPROVAL_STATUS_ID(CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_NOT_APPROVED);
		// cash.setCASH_APPROVAL_STATUS(CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_NOT_APPROVED_NAME);

		cash.setCASH_DOCUMENT_ID((long) 0);

		/** Who Login User Email ID */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		cash.setCREATEDBY(userDetails.getEmail());
		cash.setLASTMODIFIEDBY(userDetails.getEmail());
		cash.setCOMPANY_ID(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		cash.setCREATED_DATE(toDate);
		cash.setLASTUPDATED_DATE(toDate);

		cash.setMarkForDelete(false);
		cash.setDRIVER_ID(cBookDto.getDRIVER_ID());

		return cash;
	}

	public CashBook prepareCashBook_Value_MissingEntries(CashBookDto cBookDto) {

		CashBook cash = new CashBook();
		cash.setCASHID(cBookDto.getCASHID());
		cash.setCASH_BOOK_ID(cBookDto.getCASH_BOOK_ID());
		cash.setCASH_BOOK_NO(cBookDto.getCASH_BOOK_NO());
		cash.setCASH_VOUCHER_NO(cBookDto.getCASH_VOUCHER_NO());
		cash.setPAYMENT_TYPE_ID(cBookDto.getPAYMENT_TYPE_ID());
		// cash.setCASH_PAYMENT_TYPE(CashBookPaymentType.getPaymentTypeName(cBookDto.getPAYMENT_TYPE_ID()));
		try {
			// fuel date converted String to date Format
			if (cBookDto.getCASH_DATE() != null) {
				java.util.Date date = dateFormat.parse(cBookDto.getCASH_DATE());
				java.sql.Date cashDate = new Date(date.getTime());
				cash.setCASH_DATE(cashDate);

				// Why Approval Entries missing data Showing Details
				cash.setCASH_APPROVALDATE(cashDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cBookDto.getCASH_AMOUNT() != null) {
			cash.setCASH_AMOUNT(cBookDto.getCASH_AMOUNT());
		} else {
			cash.setCASH_AMOUNT(0.0);
		}
		cash.setCASH_NATURE_PAYMENT_ID(cBookDto.getCASH_NATURE_PAYMENT_ID());
		cash.setCASH_PAID_RECEIVED(cBookDto.getCASH_PAID_RECEIVED());
		cash.setCASH_PAID_BY(cBookDto.getCASH_PAID_BY());

		cash.setCASH_REFERENCENO(cBookDto.getCASH_REFERENCENO());

		cash.setCASH_GSTNO(cBookDto.getCASH_GSTNO());

		// cash.setCASH_STATUS(CashBookStatus.CASH_BOOK_STATUS_OPEN_NAME);
		// cash.setCASH_APPROVAL_STATUS(CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED_NAME);

		cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_OPEN);
		cash.setCASH_APPROVAL_STATUS_ID(CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED);

		cash.setCASH_DOCUMENT_ID((long) 0);

		cash.setDRIVER_ID(cBookDto.getDRIVER_ID());

		/** Who Login User Email ID */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		cash.setCASH_APPROVALBY(userDetails.getEmail());
		cash.setCREATEDBY(userDetails.getEmail());
		cash.setLASTMODIFIEDBY(userDetails.getEmail());
		cash.setCOMPANY_ID(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		cash.setCASH_APPROVALCOMMENT("Enter on Missing Entrie " + toDate + "");

		cash.setCREATED_DATE(toDate);
		cash.setLASTUPDATED_DATE(toDate);
		cash.setDRIVER_ID(cBookDto.getDRIVER_ID());
		cash.setCASH_NATURE_PAYMENT(cBookDto.getCASH_NATURE_PAYMENT());
		cash.setINCOME_TYPE(cBookDto.getINCOME_TYPE());
		cash.setMarkForDelete(false);

		return cash;
	}
	
	
	public CashBook prepareCashBook_Value_LastDayEntries(CashBookDto cBookDto) {

		CashBook cash = new CashBook();
		cash.setCASHID(cBookDto.getCASHID());
		cash.setCASH_BOOK_ID(cBookDto.getCASH_BOOK_ID());
		cash.setCASH_BOOK_NO(cBookDto.getCASH_BOOK_NO());
		cash.setCASH_VOUCHER_NO(cBookDto.getCASH_VOUCHER_NO());
		cash.setPAYMENT_TYPE_ID(cBookDto.getPAYMENT_TYPE_ID());
		// cash.setCASH_PAYMENT_TYPE(CashBookPaymentType.getPaymentTypeName(cBookDto.getPAYMENT_TYPE_ID()));
		try {
			// fuel date converted String to date Format
			if (cBookDto.getCASH_DATE() != null) {
				java.util.Date date = dateFormat.parse(cBookDto.getCASH_DATE());
				java.sql.Date cashDate = new Date(date.getTime());
				cash.setCASH_DATE(cashDate);

				// Why Approval Entries missing data Showing Details
				cash.setCASH_APPROVALDATE(cashDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (cBookDto.getCASH_AMOUNT() != null) {
			cash.setCASH_AMOUNT(cBookDto.getCASH_AMOUNT());
		} else {
			cash.setCASH_AMOUNT(0.0);
		}
		cash.setCASH_NATURE_PAYMENT_ID(cBookDto.getCASH_NATURE_PAYMENT_ID());
		cash.setCASH_PAID_RECEIVED(cBookDto.getCASH_PAID_RECEIVED());
		cash.setCASH_PAID_BY(cBookDto.getCASH_PAID_BY());

		cash.setCASH_REFERENCENO(cBookDto.getCASH_REFERENCENO());

		cash.setCASH_GSTNO(cBookDto.getCASH_GSTNO());

		// cash.setCASH_STATUS(CashBookStatus.CASH_BOOK_STATUS_OPEN_NAME);
		// cash.setCASH_APPROVAL_STATUS(CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED_NAME);

		cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_OPEN);
		cash.setCASH_APPROVAL_STATUS_ID(CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED);

		cash.setCASH_DOCUMENT_ID((long) 0);

		cash.setDRIVER_ID(cBookDto.getDRIVER_ID());

		/** Who Login User Email ID */
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		cash.setCASH_APPROVALBY(userDetails.getEmail());
		cash.setCREATEDBY(userDetails.getEmail());
		cash.setLASTMODIFIEDBY(userDetails.getEmail());
		cash.setCOMPANY_ID(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		cash.setCASH_APPROVALCOMMENT("Enter on Last Day Entrie " + toDate + "");

		cash.setCREATED_DATE(toDate);
		cash.setLASTUPDATED_DATE(toDate);
		cash.setDRIVER_ID(cBookDto.getDRIVER_ID());

		cash.setMarkForDelete(false);

		return cash;
	}
	
	

	public CashBookDto Edit_CashBook_Value(CashBook cBookDto) {

		CashBookDto cash = new CashBookDto();

		cash.setCASHID(cBookDto.getCASHID());
		cash.setCASH_NUMBER(cBookDto.getCASH_NUMBER());
		cash.setCASH_BOOK_NO(cBookDto.getCASH_BOOK_NO());
		cash.setCASH_BOOK_ID(cBookDto.getCASH_BOOK_ID());
		cash.setCASH_VOUCHER_NO(cBookDto.getCASH_VOUCHER_NO());
		cash.setPAYMENT_TYPE_ID(cBookDto.getPAYMENT_TYPE_ID());
		cash.setCASH_PAYMENT_TYPE(CashBookPaymentType.getPaymentTypeName(cBookDto.getPAYMENT_TYPE_ID()));
		if (cBookDto.getCASH_DATE() != null) {
			cash.setCASH_DATE(dateFormat.format(cBookDto.getCASH_DATE()));
		}

		cash.setCASH_AMOUNT(cBookDto.getCASH_AMOUNT());
		cash.setCASH_AMT_STR(String.format("%.0f", cBookDto.getCASH_AMOUNT()));
		cash.setCASH_NATURE_PAYMENT(cBookDto.getCASH_NATURE_PAYMENT());
		cash.setCASH_PAID_RECEIVED(cBookDto.getCASH_PAID_RECEIVED());
		cash.setCASH_PAID_BY(cBookDto.getCASH_PAID_BY());

		cash.setCASH_REFERENCENO(cBookDto.getCASH_REFERENCENO());

		cash.setCASH_GSTNO(cBookDto.getCASH_GSTNO());

		cash.setCASH_DOCUMENT(cBookDto.isCASH_DOCUMENT());
		cash.setCASH_DOCUMENT_ID(cBookDto.getCASH_DOCUMENT_ID());

		/*
		 * cash.setCREATEDBY(cBookDto.getCREATEDBY());
		 * 
		 * if (cBookDto.getCREATED_DATE() != null) {
		 * cash.setCREATED_DATE(dateFormatonlyDate.format(cBookDto. getCREATED_DATE()));
		 * }
		 */

		return cash;
	}
	
	public CashBookDto Edit_CashBook_Value(CashBookDto cBookDto) {

		CashBookDto cash = new CashBookDto();

		cash.setCASHID(cBookDto.getCASHID());
		cash.setCASH_NUMBER(cBookDto.getCASH_NUMBER());
		cash.setCASH_BOOK_NO(cBookDto.getCASH_BOOK_NO());
		cash.setCASH_BOOK_ID(cBookDto.getCASH_BOOK_ID());
		cash.setCASH_VOUCHER_NO(cBookDto.getCASH_VOUCHER_NO());
		cash.setPAYMENT_TYPE_ID(cBookDto.getPAYMENT_TYPE_ID());
		cash.setCASH_PAYMENT_TYPE(CashBookPaymentType.getPaymentTypeName(cBookDto.getPAYMENT_TYPE_ID()));
		if (cBookDto.getCASH_DATE_ON() != null) {
			cash.setCASH_DATE(dateFormat.format(cBookDto.getCASH_DATE_ON()));
		}

		cash.setCASH_AMOUNT(cBookDto.getCASH_AMOUNT());
		cash.setCASH_AMT_STR(String.format("%.0f", cBookDto.getCASH_AMOUNT()));
		cash.setCASH_NATURE_PAYMENT_ID(cBookDto.getCASH_NATURE_PAYMENT_ID());
		cash.setCASH_PAID_RECEIVED(cBookDto.getCASH_PAID_RECEIVED());
		cash.setCASH_PAID_BY(cBookDto.getCASH_PAID_BY());
		cash.setCASH_STATUS_ID(cBookDto.getCASH_STATUS_ID());

		cash.setCASH_REFERENCENO(cBookDto.getCASH_REFERENCENO());

		cash.setCASH_GSTNO(cBookDto.getCASH_GSTNO());

		cash.setCASH_DOCUMENT(cBookDto.isCASH_DOCUMENT());
		cash.setCASH_DOCUMENT_ID(cBookDto.getCASH_DOCUMENT_ID());
		
		if(cBookDto.getCASH_NATURE_PAYMENT() == null) {
			if(cBookDto.getIncomeName() != null) {
				cash.setCASH_NATURE_PAYMENT(cBookDto.getIncomeName());
			}else if(cBookDto.getExpenseName() != null) {
				cash.setCASH_NATURE_PAYMENT(cBookDto.getExpenseName());
			}else {
				cash.setCASH_NATURE_PAYMENT("--");
			}
		}else {
			cash.setCASH_NATURE_PAYMENT(cBookDto.getCASH_NATURE_PAYMENT());
		}

		return cash;
	}

	public CashBookDto Show_CashBook_Value(CashBookDto cBookDto) {

		CashBookDto cash = new CashBookDto();

		cash.setCASHID(cBookDto.getCASHID());
		cash.setCASH_NUMBER(cBookDto.getCASH_NUMBER());
		cash.setCASH_BOOK_NO(cBookDto.getCASH_BOOK_NO());
		cash.setCASH_BOOK_ID(cBookDto.getCASH_BOOK_ID());
		cash.setCASH_VOUCHER_NO(cBookDto.getCASH_VOUCHER_NO());
		if (cBookDto.getCASH_DATE_ON() != null) {
			cash.setCASH_DATE(dateFormatonlyDate.format(cBookDto.getCASH_DATE_ON()));
		}
		cash.setCASH_AMOUNT(cBookDto.getCASH_AMOUNT());
		cash.setCASH_AMT_STR(String.format("%.0f", cBookDto.getCASH_AMOUNT()));
		cash.setCASH_PAID_RECEIVED(cBookDto.getCASH_PAID_RECEIVED());
		cash.setCASH_PAID_BY(cBookDto.getCASH_PAID_BY());

		cash.setCASH_REFERENCENO(cBookDto.getCASH_REFERENCENO());
		cash.setCASH_GSTNO(cBookDto.getCASH_GSTNO());

		cash.setCASH_DOCUMENT(cBookDto.isCASH_DOCUMENT());

		cash.setCASH_DOCUMENT_ID(cBookDto.getCASH_DOCUMENT_ID());
		cash.setCASH_STATUS_ID(cBookDto.getCASH_STATUS_ID());

		cash.setCASH_APPROVALBY(cBookDto.getCASH_APPROVALBY());
		cash.setCASH_APPROVALCOMMENT(cBookDto.getCASH_APPROVALCOMMENT());
		if (cBookDto.getCASH_APPROVALDATE_ON() != null) {
			cash.setCASH_APPROVALDATE(CreatedDateTime.format(cBookDto.getCASH_APPROVALDATE_ON()));
		}

		ConvertIntegerToWord amount = new ConvertIntegerToWord();
		Double AmountWorld = cBookDto.getCASH_AMOUNT();
		int value = Integer.valueOf(AmountWorld.intValue());

		String AmountWORLD = amount.convertNumberToWords(value);
		cash.setCASH_AMOUNT_WORLD(AmountWORLD);
		// System.out.println(AmountWORLD);

		/** Set Created by email Id */
		cash.setCREATEDBY(cBookDto.getCREATEDBY());
		cash.setLASTMODIFIEDBY(cBookDto.getLASTMODIFIEDBY());
		/** Set Created Current Date */
		if (cBookDto.getCREATED() != null) {
			/** Set Created Current Date */
			cash.setCREATED_DATE(CreatedDateTime.format(cBookDto.getCREATED()));
		}
		if (cBookDto.getLASTUPDATED() != null) {
			cash.setLASTUPDATED_DATE(CreatedDateTime.format(cBookDto.getLASTUPDATED()));
		}
		cash.setPAYMENT_TYPE_ID(cBookDto.getPAYMENT_TYPE_ID());
		cash.setCASH_PAYMENT_TYPE(CashBookPaymentType.getPaymentTypeName(cBookDto.getPAYMENT_TYPE_ID()));
		cash.setCASH_APPROVAL_STATUS_ID(cBookDto.getCASH_APPROVAL_STATUS_ID());
		cash.setCASH_APPROVAL_STATUS(CashBookApprovalStatus.getCashBookApprovalStatusName(cBookDto.getCASH_APPROVAL_STATUS_ID()));
		
		if(cBookDto.getCASH_NATURE_PAYMENT() == null) {
			if(cBookDto.getIncomeName() != null) {
				cash.setCASH_NATURE_PAYMENT(cBookDto.getIncomeName());
			}else if(cBookDto.getExpenseName() != null) {
				cash.setCASH_NATURE_PAYMENT(cBookDto.getExpenseName());
			}else {
				cash.setCASH_NATURE_PAYMENT("--");
			}
		}else {
			cash.setCASH_NATURE_PAYMENT(cBookDto.getCASH_NATURE_PAYMENT());
		}
		
		
		cash.setCASH_NATURE_PAYMENT_ID(cBookDto.getCASH_NATURE_PAYMENT_ID());
		return cash;
	}
	
	public CashBookDto Show_CashBook_Value(CashBook cBookDto) {

		CashBookDto cash = new CashBookDto();

		cash.setCASHID(cBookDto.getCASHID());
		cash.setCASH_NUMBER(cBookDto.getCASH_NUMBER());
		cash.setCASH_BOOK_NO(cBookDto.getCASH_BOOK_NO());
		cash.setCASH_BOOK_ID(cBookDto.getCASH_BOOK_ID());
		cash.setCASH_VOUCHER_NO(cBookDto.getCASH_VOUCHER_NO());
		if (cBookDto.getCASH_DATE() != null) {
			cash.setCASH_DATE(dateFormatonlyDate.format(cBookDto.getCASH_DATE()));
		}
		cash.setCASH_AMOUNT(cBookDto.getCASH_AMOUNT());
		cash.setCASH_AMT_STR(String.format("%.0f", cBookDto.getCASH_AMOUNT()));
		cash.setCASH_NATURE_PAYMENT(cBookDto.getCASH_NATURE_PAYMENT());
		cash.setCASH_PAID_RECEIVED(cBookDto.getCASH_PAID_RECEIVED());
		cash.setCASH_PAID_BY(cBookDto.getCASH_PAID_BY());

		cash.setCASH_REFERENCENO(cBookDto.getCASH_REFERENCENO());
		cash.setCASH_GSTNO(cBookDto.getCASH_GSTNO());

		cash.setCASH_DOCUMENT(cBookDto.isCASH_DOCUMENT());

		cash.setCASH_DOCUMENT_ID(cBookDto.getCASH_DOCUMENT_ID());

		cash.setCASH_APPROVALBY(cBookDto.getCASH_APPROVALBY());
		cash.setCASH_APPROVALCOMMENT(cBookDto.getCASH_APPROVALCOMMENT());
		if (cBookDto.getCASH_APPROVALDATE() != null) {
			cash.setCASH_APPROVALDATE(CreatedDateTime.format(cBookDto.getCASH_APPROVALDATE()));
		}

		ConvertIntegerToWord amount = new ConvertIntegerToWord();
		Double AmountWorld = cBookDto.getCASH_AMOUNT();
		int value = Integer.valueOf(AmountWorld.intValue());

		String AmountWORLD = amount.convertNumberToWords(value);
		cash.setCASH_AMOUNT_WORLD(AmountWORLD);
		// System.out.println(AmountWORLD);

		/** Set Created by email Id */
		cash.setCREATEDBY(cBookDto.getCREATEDBY());
		cash.setLASTMODIFIEDBY(cBookDto.getLASTMODIFIEDBY());
		/** Set Created Current Date */
		if (cBookDto.getCREATED_DATE() != null) {
			/** Set Created Current Date */
			cash.setCREATED_DATE(CreatedDateTime.format(cBookDto.getCREATED_DATE()));
		}
		if (cBookDto.getLASTUPDATED_DATE() != null) {
			cash.setLASTUPDATED_DATE(CreatedDateTime.format(cBookDto.getLASTUPDATED_DATE()));
		}
		cash.setPAYMENT_TYPE_ID(cBookDto.getPAYMENT_TYPE_ID());
		cash.setCASH_PAYMENT_TYPE(CashBookPaymentType.getPaymentTypeName(cBookDto.getPAYMENT_TYPE_ID()));
		cash.setCASH_APPROVAL_STATUS_ID(cBookDto.getCASH_APPROVAL_STATUS_ID());
		cash.setCASH_APPROVAL_STATUS(
				CashBookApprovalStatus.getCashBookApprovalStatusName(cBookDto.getCASH_APPROVAL_STATUS_ID()));
		return cash;
	}

	public List<CashBookDto> prepare_CashBookDto_ListofDto(List<CashBook> cBook) {
		List<CashBookDto> Dtos = null;
		if (cBook != null && !cBook.isEmpty()) {
			Dtos = new ArrayList<CashBookDto>();
			CashBookDto cash = null;
			for (CashBook cBookDto : cBook) {
				cash = new CashBookDto();

				cash.setCASHID(cBookDto.getCASHID());
				cash.setCASH_NUMBER(cBookDto.getCASH_NUMBER());
				cash.setCASH_BOOK_NO(cBookDto.getCASH_BOOK_NO());
				cash.setCASH_VOUCHER_NO(cBookDto.getCASH_VOUCHER_NO());
				cash.setPAYMENT_TYPE_ID(cBookDto.getPAYMENT_TYPE_ID());
				cash.setCASH_PAYMENT_TYPE(CashBookPaymentType.getPaymentTypeName(cBookDto.getPAYMENT_TYPE_ID()));
				if (cBookDto.getCASH_DATE() != null) {
					cash.setCASH_DATE(dateFormatonlyDate.format(cBookDto.getCASH_DATE()));
				}

				cash.setCASH_AMOUNT(cBookDto.getCASH_AMOUNT());
				cash.setCASH_AMT_STR(String.format("%.0f", cBookDto.getCASH_AMOUNT()));
				cash.setCASH_NATURE_PAYMENT(cBookDto.getCASH_NATURE_PAYMENT());
				cash.setCASH_PAID_RECEIVED(cBookDto.getCASH_PAID_RECEIVED());
				cash.setCASH_PAID_BY(cBookDto.getCASH_PAID_BY());
				cash.setCASH_REFERENCENO(cBookDto.getCASH_REFERENCENO());
				cash.setCASH_GSTNO(cBookDto.getCASH_GSTNO());
				cash.setCASH_STATUS_ID(cBookDto.getCASH_STATUS_ID());
				cash.setCASH_STATUS(CashBookStatus.getCashBookStatusName(cBookDto.getCASH_STATUS_ID()));
				cash.setCASH_APPROVAL_STATUS_ID(cBookDto.getCASH_APPROVAL_STATUS_ID());
				cash.setCASH_APPROVAL_STATUS(
						CashBookApprovalStatus.getCashBookApprovalStatusName(cBookDto.getCASH_APPROVAL_STATUS_ID()));

				cash.setCASH_DOCUMENT(cBookDto.isCASH_DOCUMENT());

				cash.setCASH_DOCUMENT_ID(cBookDto.getCASH_DOCUMENT_ID());
				Dtos.add(cash);
			}

		}
		return Dtos;
	}
	
	public ValueObject getCreditDebitTotal(List<CashBookDto> cBook) throws Exception {
		
		ValueObject	valueObject = new ValueObject();
		Double	creditTotal = 0.0;
		Double	debitTotal = 0.0;
		for (CashBookDto cBookDto : cBook) {
			if(cBookDto.getPAYMENT_TYPE_ID() == CashBookPaymentType.PAYMENT_TYPE_DEBIT) {
				debitTotal += cBookDto.getCASH_AMOUNT();
			}else {
				creditTotal += cBookDto.getCASH_AMOUNT();
			}
		}
		valueObject.put("creditTotal", creditTotal);
		valueObject.put("debitTotal", debitTotal);
		
		return valueObject;
	}
	
	public Double getDebitOrCreditTotal(List<CashBookDto> cBook) throws Exception {
		
		Double	debitTotal = 0.0;
		for (CashBookDto cBookDto : cBook) {
			debitTotal += cBookDto.getCASH_AMOUNT();
		}
		return debitTotal;
	}
	
	public List<CashBookDto> prepare_CashBookDto_ListDto(List<CashBookDto> cBook) {
		List<CashBookDto> Dtos = null;
		if (cBook != null && !cBook.isEmpty()) {
			Dtos = new ArrayList<CashBookDto>();
			CashBookDto cash = null;
			for (CashBookDto cBookDto : cBook) {
				cash = new CashBookDto();

				cash.setCASHID(cBookDto.getCASHID());
				cash.setCASH_NUMBER(cBookDto.getCASH_NUMBER());
				cash.setCASH_BOOK_NO(cBookDto.getCASH_BOOK_NO());
				cash.setCASH_VOUCHER_NO(cBookDto.getCASH_VOUCHER_NO());
				cash.setPAYMENT_TYPE_ID(cBookDto.getPAYMENT_TYPE_ID());
				cash.setCASH_PAYMENT_TYPE(CashBookPaymentType.getPaymentTypeName(cBookDto.getPAYMENT_TYPE_ID()));
				if (cBookDto.getCASH_DATE_ON() != null) {
					cash.setCASH_DATE(dateFormatonlyDate.format(cBookDto.getCASH_DATE_ON()));
				}

				cash.setCASH_AMOUNT(cBookDto.getCASH_AMOUNT());
				cash.setCASH_AMT_STR(String.format("%.0f", cBookDto.getCASH_AMOUNT()));
				cash.setCASH_NATURE_PAYMENT_ID(cBookDto.getCASH_NATURE_PAYMENT_ID());
				cash.setCASH_PAID_RECEIVED(cBookDto.getCASH_PAID_RECEIVED());
				cash.setCASH_PAID_BY(cBookDto.getCASH_PAID_BY());
				cash.setCASH_REFERENCENO(cBookDto.getCASH_REFERENCENO());
				cash.setCASH_GSTNO(cBookDto.getCASH_GSTNO());
				cash.setCASH_STATUS_ID(cBookDto.getCASH_STATUS_ID());
				cash.setCASH_STATUS(CashBookStatus.getCashBookStatusName(cBookDto.getCASH_STATUS_ID()));
				cash.setCASH_APPROVAL_STATUS_ID(cBookDto.getCASH_APPROVAL_STATUS_ID());
				cash.setCASH_APPROVAL_STATUS(
						CashBookApprovalStatus.getCashBookApprovalStatusName(cBookDto.getCASH_APPROVAL_STATUS_ID()));

				cash.setCASH_DOCUMENT(cBookDto.isCASH_DOCUMENT());

				cash.setCASH_DOCUMENT_ID(cBookDto.getCASH_DOCUMENT_ID());
				
				if(cBookDto.getCASH_NATURE_PAYMENT() == null) {
					if(cBookDto.getIncomeName() != null) {
						cash.setCASH_NATURE_PAYMENT(cBookDto.getIncomeName());
					}else if(cBookDto.getExpenseName() != null) {
						cash.setCASH_NATURE_PAYMENT(cBookDto.getExpenseName());
					}else {
						cash.setCASH_NATURE_PAYMENT("--");
					}
				}else {
					cash.setCASH_NATURE_PAYMENT(cBookDto.getCASH_NATURE_PAYMENT());
				}
				cash.setINCOME_TYPE(cBookDto.getINCOME_TYPE());
				Dtos.add(cash);
			}

		}
		return Dtos;
	}

	public List<CashBookDto> prepare_CashBookDto_List(List<CashBookDto> cBook) {
		List<CashBookDto> Dtos = null;
		if (cBook != null && !cBook.isEmpty()) {
			Dtos = new ArrayList<CashBookDto>();
			CashBookDto cash = null;
			for (CashBookDto cBookDto : cBook) {
				cash = new CashBookDto();

				cash.setCASHID(cBookDto.getCASHID());
				cash.setCASH_NUMBER(cBookDto.getCASH_NUMBER());
				cash.setCASH_BOOK_NO(cBookDto.getCASH_BOOK_NO());
				cash.setCASH_VOUCHER_NO(cBookDto.getCASH_VOUCHER_NO());
				cash.setPAYMENT_TYPE_ID(cBookDto.getPAYMENT_TYPE_ID());
				cash.setCASH_PAYMENT_TYPE(CashBookPaymentType.getPaymentTypeName(cBookDto.getPAYMENT_TYPE_ID()));
				if (cBookDto.getCASH_DATE_ON() != null) {
					cash.setCASH_DATE(dateFormatonlyDate.format(cBookDto.getCASH_DATE_ON()));
				}

				cash.setCASH_AMOUNT(cBookDto.getCASH_AMOUNT());
				cash.setCASH_AMT_STR(String.format("%.0f", cBookDto.getCASH_AMOUNT()));
				cash.setCASH_NATURE_PAYMENT(cBookDto.getCASH_NATURE_PAYMENT());
				cash.setCASH_PAID_RECEIVED(cBookDto.getCASH_PAID_RECEIVED());
				cash.setCASH_PAID_BY(cBookDto.getCASH_PAID_BY());
				cash.setCASH_REFERENCENO(cBookDto.getCASH_REFERENCENO());
				cash.setCASH_GSTNO(cBookDto.getCASH_GSTNO());
				cash.setCASH_STATUS_ID(cBookDto.getCASH_STATUS_ID());
				cash.setCASH_STATUS(CashBookStatus.getCashBookStatusName(cBookDto.getCASH_STATUS_ID()));
				cash.setCASH_APPROVAL_STATUS_ID(cBookDto.getCASH_APPROVAL_STATUS_ID());
				cash.setCASH_APPROVAL_STATUS(
						CashBookApprovalStatus.getCashBookApprovalStatusName(cBookDto.getCASH_APPROVAL_STATUS_ID()));

				cash.setCASH_DOCUMENT(cBookDto.isCASH_DOCUMENT());

				cash.setCASH_DOCUMENT_ID(cBookDto.getCASH_DOCUMENT_ID());
				Dtos.add(cash);
			}

		}
		return Dtos;
	}

	
	
	public HashMap<String, Object> getCashPaymentNatureWiseHashMap(List<CashBookDto> cBook){
		
		LinkedHashMap<String, CashBookDto> 	paymentNatureWiseMap  = null;
		CashBookDto								cash			 	  = null;
		HashMap<String, CashBookDto> 			singleMap			  = null;
		HashMap<String, CashBookDto> 			multipleMap			  = null;
		LinkedHashMap<String, CashBookDto>		linkedHashMap			= null;
		HashMap<String, Object>					map						= null;
		
		try {
			paymentNatureWiseMap	= new LinkedHashMap<>();
			singleMap				= new HashMap<>();
			multipleMap				= new HashMap<>();
			linkedHashMap			= new LinkedHashMap<>();
			map						= new HashMap<>();
			
			for(CashBookDto cBookDto : cBook) {
				
				cash	= paymentNatureWiseMap.get(cBookDto.getCASH_NATURE_PAYMENT()+"_"+cBookDto.getPAYMENT_TYPE_ID());
				if(cash == null) {
					cash = new CashBookDto();
					
					cash.setCASHID(cBookDto.getCASHID());
					cash.setCASH_NUMBER(cBookDto.getCASH_NUMBER());
					cash.setCASH_BOOK_NO(cBookDto.getCASH_BOOK_NO());
					cash.setCASH_VOUCHER_NO(cBookDto.getCASH_VOUCHER_NO());
					cash.setPAYMENT_TYPE_ID(cBookDto.getPAYMENT_TYPE_ID());
					cash.setCASH_PAYMENT_TYPE(CashBookPaymentType.getPaymentTypeName(cBookDto.getPAYMENT_TYPE_ID()));
					if (cBookDto.getCASH_DATE_ON() != null) {
						cash.setCASH_DATE(dateFormatonlyDate.format(cBookDto.getCASH_DATE_ON()));
					}
					cash.setCASH_DATE_ON(cBookDto.getCASH_DATE_ON());
					cash.setCASH_AMOUNT(cBookDto.getCASH_AMOUNT());
					cash.setCASH_AMT_STR(String.format("%.0f", cBookDto.getCASH_AMOUNT()));
					cash.setCASH_NATURE_PAYMENT(cBookDto.getCASH_NATURE_PAYMENT());
					cash.setCASH_NATURE_PAYMENT_ID(cBookDto.getCASH_NATURE_PAYMENT_ID());
					cash.setCASH_PAID_RECEIVED(cBookDto.getCASH_PAID_RECEIVED());
					cash.setCASH_PAID_BY(cBookDto.getCASH_PAID_BY());
					cash.setCASH_REFERENCENO(cBookDto.getCASH_REFERENCENO());
					cash.setCASH_GSTNO(cBookDto.getCASH_GSTNO());
					cash.setCASH_STATUS_ID(cBookDto.getCASH_STATUS_ID());
					cash.setCASH_STATUS(CashBookStatus.getCashBookStatusName(cBookDto.getCASH_STATUS_ID()));
					cash.setCASH_APPROVAL_STATUS_ID(cBookDto.getCASH_APPROVAL_STATUS_ID());
					cash.setCASH_APPROVAL_STATUS(
							CashBookApprovalStatus.getCashBookApprovalStatusName(cBookDto.getCASH_APPROVAL_STATUS_ID()));

					cash.setCASH_DOCUMENT(cBookDto.isCASH_DOCUMENT());

					cash.setCASH_DOCUMENT_ID(cBookDto.getCASH_DOCUMENT_ID());
					singleMap.put(cBookDto.getCASH_NATURE_PAYMENT()+"_"+cBookDto.getPAYMENT_TYPE_ID(), cash);
				}else {
					cash.setCASH_AMOUNT(cash.getCASH_AMOUNT() + cBookDto.getCASH_AMOUNT());	
					cash.setCASH_AMT_STR(String.format("%.0f", cash.getCASH_AMOUNT()));
					cash.setMultiplePayment(true);
					singleMap.remove(cBookDto.getCASH_NATURE_PAYMENT()+"_"+cBookDto.getPAYMENT_TYPE_ID());
					multipleMap.put(cBookDto.getCASH_NATURE_PAYMENT()+"_"+cBookDto.getPAYMENT_TYPE_ID(), cash);
				}
				paymentNatureWiseMap.put(cBookDto.getCASH_NATURE_PAYMENT()+"_"+cBookDto.getPAYMENT_TYPE_ID(), cash);
				linkedHashMap.put(cBookDto.getCASH_NATURE_PAYMENT(), cash);
			}
			paymentNatureWiseMap.clear();
			paymentNatureWiseMap.putAll(getSortedValue(singleMap));
			paymentNatureWiseMap.putAll(multipleMap);
			
			map.put("cashBookMap", paymentNatureWiseMap);
			map.put("paymentTypeWise", linkedHashMap);
			
		} catch (Exception e) {
			System.err.println("Exception "+e);
		}
		return map;
	}
	
	public LinkedHashMap<String, CashBookDto>  getSortedValue(HashMap<String, CashBookDto> 	singleMap){
		List<CashBookDto>  cashBookDtoList	= new ArrayList<>(singleMap.values());
		LinkedHashMap<String, CashBookDto>	hashMap = new LinkedHashMap<>();
		
			Collections.sort(cashBookDtoList, new Comparator<CashBookDto>() {
			  public int compare(CashBookDto o1, CashBookDto o2) {
			      if (o1.getCASH_DATE_ON() == null || o2.getCASH_DATE_ON() == null)
			        return 0;
			      return o1.getCASH_DATE_ON().compareTo(o2.getCASH_DATE_ON());
			  }
			});
		 for(CashBookDto bookDto : cashBookDtoList) {
			 hashMap.put(bookDto.getCASH_NATURE_PAYMENT()+"_"+bookDto.getPAYMENT_TYPE_ID(), bookDto);
		 }
		return hashMap;
	}
	
	public Double DebitTotal_CashBookDto(List<CashBookDto> Debit) {
		Double DebitTotal = 0.0;
		if (Debit != null && !Debit.isEmpty()) {
			for (CashBookDto cBookDto : Debit) {
				// counting Total debit Amount
				DebitTotal += cBookDto.getCASH_AMOUNT();
			}
		}

		return DebitTotal;
	}

	public Double CreditTotal_CashBookDto(List<CashBook> Credit) {
		Double CreditTotal = 0.0;

		if (Credit != null && !Credit.isEmpty()) {
			for (CashBook cCreditDto : Credit) {
				// counting Total debit Amount
				CreditTotal += cCreditDto.getCASH_AMOUNT();
			}
		}
		return CreditTotal;
	}

	public Double Amount_DebitTotal_CashBookDto(List<CashBook> Debit) {
		Double DebitTotal = 0.0;
		if (Debit != null && !Debit.isEmpty()) {
			for (CashBook cBookDto : Debit) {
				if (cBookDto.getPAYMENT_TYPE_ID() == CashBookPaymentType.PAYMENT_TYPE_DEBIT) {
					// counting Total debit Amount
					DebitTotal += cBookDto.getCASH_AMOUNT();
				}
			}
		}
		return DebitTotal;
	}

	public Double Amount_CreditTotal_CashBookDto(List<CashBook> Credit) {
		Double CreditTotal = 0.0;
		if (Credit != null && !Credit.isEmpty()) {
			for (CashBook cCreditDto : Credit) {
				if (cCreditDto.getPAYMENT_TYPE_ID() == CashBookPaymentType.PAYMENT_TYPE_CREDIT) {
					// counting Total debit Amount
					CreditTotal += cCreditDto.getCASH_AMOUNT();
				}
			}
		}

		return CreditTotal;
	}

	/** CashBook Name Details * */

	public List<CashBookName> prepare_CashBookName_List(List<CashBookName> cBook) {
		List<CashBookName> Dtos = null;
		if (cBook != null && !cBook.isEmpty()) {
			Dtos = new ArrayList<CashBookName>();
			CashBookName cash = null;
			for (CashBookName cBookDto : cBook) {
				cash = new CashBookName();

				cash.setNAMEID(cBookDto.getNAMEID());
				cash.setCASHBOOK_NAME(cBookDto.getCASHBOOK_NAME());
				cash.setCASHBOOK_REMARKS(cBookDto.getCASHBOOK_REMARKS());

				Dtos.add(cash);
			}

		}
		return Dtos;
	}

	public List<CashBookNameDto> getCashBookName_List(List<CashBookNameDto> cBook) {
		List<CashBookNameDto> Dtos = null;
		if (cBook != null && !cBook.isEmpty()) {
			Dtos = new ArrayList<CashBookNameDto>();
			CashBookNameDto cash = null;
			for (CashBookNameDto cBookDto : cBook) {
				cash = new CashBookNameDto();

				cash.setNAMEID(cBookDto.getNAMEID());
				cash.setCASHBOOK_NAME(cBookDto.getCASHBOOK_NAME());
				cash.setCASHBOOK_REMARKS(cBookDto.getCASHBOOK_REMARKS());
				cash.setCASHBOOK_CODE(cBookDto.getCASHBOOK_CODE());
				cash.setVEHICLE_GROUP_ID(cBookDto.getVEHICLE_GROUP_ID());
				cash.setVEHICLE_GROUP(cBookDto.getVEHICLE_GROUP());

				Dtos.add(cash);
			}

		}
		return Dtos;
	}

	public List<CashBookName> prepare_CashBookName(String cBookCASHBOOK_NAME, Integer CASH_BOOK_ID) {
		List<CashBookName> Dtos = null;
		if (cBookCASHBOOK_NAME != null) {
			Dtos = new ArrayList<CashBookName>();
			CashBookName cash = null;
			cash = new CashBookName();

			cash.setNAMEID(CASH_BOOK_ID);
			cash.setCASHBOOK_NAME(cBookCASHBOOK_NAME);
			Dtos.add(cash);
		}
		return Dtos;
	}

	public CashBookName prepare_CashBookName(CashBookName cBookDto) {
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		CashBookName cash = new CashBookName();

		cash.setNAMEID(cBookDto.getNAMEID());
		cash.setCASHBOOK_NAME(cBookDto.getCASHBOOK_NAME());
		cash.setCASHBOOK_CODE(cBookDto.getCASHBOOK_CODE());
		cash.setCASHBOOK_REMARKS(cBookDto.getCASHBOOK_REMARKS());
		cash.setVEHICLE_GROUP_ID(cBookDto.getVEHICLE_GROUP_ID());
		cash.setCREATED(toDate);

		return cash;
	}

	public CashBookName prepare_CashBookNameForUpdate(CashBookName cBookDto) {
		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		CashBookName cash = new CashBookName();

		cash.setNAMEID(cBookDto.getNAMEID());
		cash.setCASHBOOK_NAME(cBookDto.getCASHBOOK_NAME());
		cash.setCASHBOOK_REMARKS(cBookDto.getCASHBOOK_REMARKS());
		cash.setVEHICLE_GROUP_ID(cBookDto.getVEHICLE_GROUP_ID());
		cash.setLASTMODIFIEDON(toDate);
		cash.setCASHBOOK_CODE(cBookDto.getCASHBOOK_CODE());

		return cash;
	}

	public CashBookBalance prepareCashBookBalance_Value(CashBook cBookDto) {

		CashBookBalance cash = new CashBookBalance();

		if (cBookDto.getCASH_BOOK_NO()
				.equalsIgnoreCase(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME)) {

			cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN);
		} else {
			cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK);
		}
		cash.setCASH_BOOK_NAME(cBookDto.getCASH_BOOK_NO());
		cash.setCASH_BOOK_ID(cBookDto.getCASH_BOOK_ID());
		cash.setCASH_DATE(cBookDto.getCASH_DATE());

		if (cBookDto.getPAYMENT_TYPE_ID() == CashBookPaymentType.PAYMENT_TYPE_DEBIT) {

			cash.setCASH_DEBIT(cBookDto.getCASH_AMOUNT());
			cash.setCASH_DAY_BALANCE(-cBookDto.getCASH_AMOUNT());
			cash.setCASH_ALL_BALANCE(0.0);
			cash.setCASH_CREDIT(0.0);
		} else {
			cash.setCASH_DEBIT(0.0);
			cash.setCASH_DAY_BALANCE(cBookDto.getCASH_AMOUNT());
			cash.setCASH_ALL_BALANCE(0.0);
			cash.setCASH_CREDIT(cBookDto.getCASH_AMOUNT());
		}
		cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_OPEN);
		// cash.setCASH_STATUS(CashBookStatus.CASH_BOOK_STATUS_OPEN_NAME);
		cash.setCOMPANY_ID(cBookDto.getCOMPANY_ID());

		return cash;
	}

	public CashBookBalance prepareCashBookBalance_Value_cashBookApproval(CashBook cBookDto) {

		CashBookBalance cash = new CashBookBalance();

		if (cBookDto.getCASH_BOOK_NO()
				.equalsIgnoreCase(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME)) {

			cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN);
		} else {
			cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK);
		}

		cash.setCASH_BOOK_NAME(cBookDto.getCASH_BOOK_NO());
		cash.setCASH_BOOK_ID(cBookDto.getCASH_BOOK_ID());
		cash.setCASH_DATE(cBookDto.getCASH_APPROVALDATE());
		cash.setCOMPANY_ID(cBookDto.getCOMPANY_ID());

		if (cBookDto.getPAYMENT_TYPE_ID() == CashBookPaymentType.PAYMENT_TYPE_DEBIT) {

			cash.setCASH_DEBIT(cBookDto.getCASH_AMOUNT());
			cash.setCASH_DAY_BALANCE(-cBookDto.getCASH_AMOUNT());
			cash.setCASH_ALL_BALANCE(0.0);
			cash.setCASH_CREDIT(0.0);
		} else {
			cash.setCASH_DEBIT(0.0);
			cash.setCASH_DAY_BALANCE(cBookDto.getCASH_AMOUNT());
			cash.setCASH_ALL_BALANCE(0.0);
			cash.setCASH_CREDIT(cBookDto.getCASH_AMOUNT());
		}
		cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_OPEN);
		// cash.setCASH_STATUS(CashBookStatus.CASH_BOOK_STATUS_OPEN_NAME);

		return cash;
	}

	public CashBookBalance prepare_CLOSED_LAST_Value(Date openingDate, String CASH_BOOK_NAME) {

		CashBookBalance cash = new CashBookBalance();

		if (CASH_BOOK_NAME.equalsIgnoreCase(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME)) {

			cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN);
		} else {
			cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK);
		}
		cash.setCASH_BOOK_NAME(CASH_BOOK_NAME);
		try {
			// fuel date converted String to date Format
			if (openingDate != null) {
				cash.setCASH_DATE(openingDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		cash.setCASH_DEBIT(0.0);
		cash.setCASH_DAY_BALANCE(0.0);
		cash.setCASH_ALL_BALANCE(0.0);
		cash.setCASH_CREDIT(0.0);
		// cash.setCASH_STATUS(CashBookStatus.CASH_BOOK_STATUS_OPEN_NAME);
		cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_OPEN);

		return cash;
	}

	public CashBookBalance prepare_CLOSED_DAY_ADD_Value(CashBookBalanceDto cBookDto) {

		CashBookBalance cash = new CashBookBalance();

		if (cBookDto.getCASH_BOOK_NAME()
				.equalsIgnoreCase(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME)) {

			cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN);
		} else {
			cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK);
		}

		cash.setCASH_BOOK_NAME(cBookDto.getCASH_BOOK_NAME());
		cash.setCASH_BOOK_ID(cBookDto.getCASH_BOOK_ID());
		try {
			// fuel date converted String to date Format
			if (cBookDto.getCASH_DATE() != null) {
				java.util.Date date = dateFormat.parse(cBookDto.getCASH_DATE());
				java.sql.Date cashDate = new Date(date.getTime());
				cash.setCASH_DATE(cashDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		cash.setCASH_DEBIT(0.0);
		cash.setCASH_DAY_BALANCE(0.0);
		cash.setCASH_ALL_BALANCE(0.0);
		cash.setCASH_CREDIT(0.0);
		cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_OPEN);
		// cash.setCASH_STATUS(CashBookStatus.CASH_BOOK_STATUS_OPEN_NAME);

		return cash;
	}

	public CashBook prepare_SubCashBook_To_MAin_Cashbook_Value(CashBookBalance cBookDto, short CASH_PAYMENT_TYPE,
			Double Amount, String CloseBy, Integer cashBookId, boolean isfromscheduler) {

		CashBook cash = new CashBook();

		cash.setCASH_BOOK_NO(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME);

		cash.setPAYMENT_TYPE_ID(CASH_PAYMENT_TYPE);
		String CREATEDBY = null;
		// cash.setCASH_PAYMENT_TYPE(CashBookPaymentType.getPaymentTypeName(CASH_PAYMENT_TYPE));
		try {
			// fuel date converted String to date Format
			if (cBookDto.getCASH_DATE() != null) {
				cash.setCASH_DATE(cBookDto.getCASH_DATE());
				String dateCB = dateFormat.format(cBookDto.getCASH_DATE());
				String cBName = cBookDto.getCASH_BOOK_NAME();
				String CVoucher = cBName.substring(0, 3) + "-" + dateCB + "-"
						+ CashBookPaymentType.getPaymentTypeName(CASH_PAYMENT_TYPE).substring(0, 2)+"-"+cashBookId;
				cash.setCASH_VOUCHER_NO(CVoucher);

				// Approval Date
				cash.setCASH_APPROVALDATE(cBookDto.getCASH_DATE());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cash.setCASH_AMOUNT(Amount);
		cash.setCASH_NATURE_PAYMENT(cBookDto.getCASH_BOOK_NAME() + " To Main Cash Book");
		cash.setCASH_PAID_RECEIVED(CloseBy);
		cash.setCASH_PAID_BY(CloseBy);
		cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_CLOSED);
		// cash.setCASH_STATUS(CashBookStatus.CASH_BOOK_STATUS_CLOSED_NAME);
		// Approval Date APPROVED IN SHOW ENTRES
		cash.setCASH_APPROVAL_STATUS_ID(CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED);
		// cash.setCASH_APPROVAL_STATUS(CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED_NAME);
		cash.setCASH_APPROVALBY(CloseBy);
		cash.setCASH_APPROVALCOMMENT("Sub Cash Book Closed");

		/** Who Closed the cash Book */
		if(isfromscheduler) {
			CREATEDBY  = "ADMIN";
		}
		else {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			CREATEDBY = auth.getName();
		}
		cash.setCREATEDBY(CREATEDBY);
		cash.setLASTMODIFIEDBY(CREATEDBY);
		cash.setCOMPANY_ID(cBookDto.getCOMPANY_ID());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());

		cash.setCREATED_DATE(toDate);
		cash.setLASTUPDATED_DATE(toDate);
		cash.setMarkForDelete(false);

		return cash;
	}

	/*******************************************************************/
	/*********** SRI DURGAMBA CASHBOOK LOGIC **************************/
	/*****************************************************************/
	/**
	 * @param totalGroupSheet
	 * @return this cash book Save value of Trip Daily Group Collection Total
	 */
	public CashBook prepareCashBook_Value_TripDailyGroupCollection_TotalAmount(TripDailyGroupCollection totalGroupSheet,
			String VEHICLEGROUP) {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();

		CashBook cash = new CashBook();

		// Vehicle Group name is Cash Book Name
		cash.setCASH_BOOK_NO(VEHICLEGROUP);
		// HERE all was Payment Type is Credit only
		cash.setPAYMENT_TYPE_ID(CashBookPaymentType.PAYMENT_TYPE_CREDIT);
		// cash.setCASH_PAYMENT_TYPE(CashBookPaymentType.PAYMENT_TYPE_CREDIT_NAME);
		try {
			// fuel date converted String to date Format
			if (totalGroupSheet.getTRIP_OPEN_DATE() != null) {
				cash.setCASH_DATE(totalGroupSheet.getTRIP_OPEN_DATE());
				cash.setCASH_APPROVALDATE(totalGroupSheet.getTRIP_OPEN_DATE());

				String dateCB = dateFormat.format(totalGroupSheet.getTRIP_OPEN_DATE());
				String cBName = VEHICLEGROUP;
				String CVoucher = cBName.substring(0, 3) + "-" + dateCB + "-" + totalGroupSheet.getTRIPGROUPID()
						+ "-CR";
				cash.setCASH_VOUCHER_NO(CVoucher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cash.setCASH_AMOUNT(totalGroupSheet.getTOTAL_BALANCE());
		cash.setCASH_NATURE_PAYMENT("Trip closed to " + VEHICLEGROUP + " Cash Book");
		cash.setCASH_PAID_RECEIVED(userDetails.getEmail());
		cash.setCASH_PAID_BY(userDetails.getEmail());
		cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_CLOSED);
		// cash.setCASH_STATUS(CashBookStatus.CASH_BOOK_STATUS_CLOSED_NAME);
		// Approval Date APPROVED IN SHOW ENTRES
		cash.setCASH_APPROVAL_STATUS_ID(CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED);
		// cash.setCASH_APPROVAL_STATUS(CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED_NAME);
		cash.setCASH_APPROVALBY(userDetails.getEmail());
		cash.setCASH_APPROVALCOMMENT("This Trip Collection Balance Amount" + totalGroupSheet.getTRIP_CLOSE_REMARKS());

		/** Who Closed the cash Book */
		cash.setCREATEDBY(userDetails.getEmail());
		cash.setLASTMODIFIEDBY(userDetails.getEmail());
		cash.setCOMPANY_ID(userDetails.getCompany_id());

		java.util.Date currentDateUpdate = new java.util.Date();
		Timestamp toDate = new java.sql.Timestamp(currentDateUpdate.getTime());
		// Approval Date

		cash.setCREATED_DATE(toDate);
		cash.setLASTUPDATED_DATE(toDate);
		cash.setMarkForDelete(false);

		return cash;
	}

	public CashBookHistory	getCashBookHistoryModel(CashBook	cBookDto)throws Exception {
		CashBookHistory	cashBookHistory	= null;
		try {
			if(cBookDto != null) {
				cashBookHistory	= new CashBookHistory();
				cashBookHistory.setCASHID(cBookDto.getCASHID());
				cashBookHistory.setCASH_NUMBER(cBookDto.getCASH_NUMBER());
				cashBookHistory.setCASH_BOOK_NO(cBookDto.getCASH_BOOK_NO());
				cashBookHistory.setCASH_BOOK_ID(cBookDto.getCASH_BOOK_ID());
				cashBookHistory.setCASH_VOUCHER_NO(cBookDto.getCASH_VOUCHER_NO());
				cashBookHistory.setPAYMENT_TYPE_ID(cBookDto.getPAYMENT_TYPE_ID());
				cashBookHistory.setCASH_DATE(cBookDto.getCASH_DATE());
				cashBookHistory.setCASH_APPROVALDATE(cBookDto.getCASH_APPROVALDATE());
				cashBookHistory.setCASH_APPROVALBY(cBookDto.getCASH_APPROVALBY());
				cashBookHistory.setCASH_APPROVALCOMMENT(cBookDto.getCASH_APPROVALCOMMENT());
				
				cashBookHistory.setCASH_AMOUNT(cBookDto.getCASH_AMOUNT());
				cashBookHistory.setCASH_NATURE_PAYMENT(cBookDto.getCASH_NATURE_PAYMENT());
				cashBookHistory.setCASH_NATURE_PAYMENT_ID(cBookDto.getCASH_NATURE_PAYMENT_ID());
				cashBookHistory.setCASH_PAID_RECEIVED(cBookDto.getCASH_PAID_RECEIVED());
				cashBookHistory.setCASH_PAID_BY(cBookDto.getCASH_PAID_BY());

				cashBookHistory.setCASH_REFERENCENO(cBookDto.getCASH_REFERENCENO());
				cashBookHistory.setCASH_GSTNO(cBookDto.getCASH_GSTNO());

				cashBookHistory.setCASH_STATUS_ID(cBookDto.getCASH_STATUS_ID());
				cashBookHistory.setCASH_APPROVAL_STATUS_ID(cBookDto.getCASH_APPROVAL_STATUS_ID());

				cashBookHistory.setCASH_DOCUMENT_ID(cBookDto.getCASH_DOCUMENT_ID());
				cashBookHistory.setCASH_DOCUMENT(cBookDto.isCASH_DOCUMENT());
				cashBookHistory.setCREATEDBY(cBookDto.getCREATEDBY());
				cashBookHistory.setLASTMODIFIEDBY(cBookDto.getLASTMODIFIEDBY());
				cashBookHistory.setCOMPANY_ID(cBookDto.getCOMPANY_ID());
				cashBookHistory.setCREATED_DATE(cBookDto.getCREATED_DATE());
				cashBookHistory.setLASTUPDATED_DATE(cBookDto.getLASTUPDATED_DATE());

				cashBookHistory.setMarkForDelete(false);
				cashBookHistory.setDRIVER_ID(cBookDto.getDRIVER_ID());
			}
			return cashBookHistory;
		} catch (Exception e) {
			throw e;
		}
	}

	public CashBookNameHistory prepareHistoryModel(CashBookName cashBookName) {
		CashBookNameHistory		cashBookNameHistory		= new CashBookNameHistory();
		
		cashBookNameHistory.setCASHBOOK_NAME(cashBookName.getCASHBOOK_NAME());
		cashBookNameHistory.setCASHBOOK_REMARKS(cashBookName.getCASHBOOK_REMARKS());
		cashBookNameHistory.setCOMPANY_ID(cashBookName.getCOMPANY_ID());
		cashBookNameHistory.setLASTMODIFIEDBYID(cashBookName.getLASTMODIFIEDBYID());
		cashBookNameHistory.setLASTMODIFIEDON(cashBookName.getLASTMODIFIEDON());
		cashBookNameHistory.setMarkForDelete(cashBookName.isMarkForDelete());
		cashBookNameHistory.setNAMEID(cashBookName.getNAMEID());
		cashBookNameHistory.setVEHICLE_GROUP_ID(cashBookName.getVEHICLE_GROUP_ID());
		cashBookNameHistory.setCASHBOOK_CODE(cashBookName.getCASHBOOK_CODE());

		return cashBookNameHistory;
	}
	
	public CashBookBalanceDto prepare_CashBookBalanceDto(CashBookBalance cashbook) {
		CashBookBalanceDto cash = new CashBookBalanceDto();

		cash.setCBBID(cashbook.getCBBID());
		if (cashbook.getCASH_BOOK_NAME()
				.equalsIgnoreCase(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME)) {

			cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN);
		} else {
			cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK);
		}
		cash.setCASH_BOOK_NAME(cashbook.getCASH_BOOK_NAME());
		cash.setCASH_BOOK_ID(cashbook.getCASH_BOOK_ID());
		try {
			// fuel date converted String to date Format
			if (cashbook.getCASH_DATE() != null) {
				String date = dateFormat.format(cashbook.getCASH_DATE());
				cash.setCASH_DATE(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cash.setCASH_DEBIT(cashbook.getCASH_DEBIT());
		cash.setCASH_CREDIT(cashbook.getCASH_CREDIT());
		cash.setCASH_DAY_BALANCE(cashbook.getCASH_DAY_BALANCE());
		cash.setCASH_ALL_BALANCE(cashbook.getCASH_ALL_BALANCE());
		cash.setCASH_STATUS_ID(cashbook.getCASH_STATUS_ID());
		cash.setCOMPANY_ID(cashbook.getCOMPANY_ID());
		return cash;
	}
	
	public CashBook  prepareCashBookDetails(java.util.Date cashdate,Integer cashbookId, String cashBookName, String user,
			String userEmail, Integer companyId) {
		
		CashBook  cash = new CashBook();
		
		try {
	
			cash.setCASH_NUMBER((long)0);
			cash.setCASH_BOOK_ID(cashbookId);
			cash.setCASH_BOOK_NO(cashBookName);
			
			if(cashdate!=null) {
				String date	 = dateFormat.format(cashdate);
				java.util.Date date2 = dateFormat.parse(date);
				java.sql.Date cashDate = new Date(date2.getTime());
				cash.setCASH_DATE(cashDate);
				cash.setCASH_APPROVALDATE(cashDate);
				
				cash.setCREATED_DATE(cashDate);
				cash.setLASTUPDATED_DATE(cashDate);
				
			}
			String dateCB = dateFormat.format(cashdate);
			String cBName = cashBookName;
			String CVoucher = cBName.substring(0, 3) + "-" + dateCB + "-"
					+ CashBookPaymentType.getPaymentTypeName((short)1).substring(0, 2)+"-"+cashbookId;
			cash.setCASH_VOUCHER_NO(CVoucher);

			
			cash.setPAYMENT_TYPE_ID((short) 1);
			cash.setCASH_AMOUNT(0.0);
			cash.setCASH_APPROVALBY(user);
			cash.setCASH_PAID_BY(user);
			cash.setCASH_PAID_RECEIVED(user);
			
			cash.setCASH_APPROVALCOMMENT("Approved");
			cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_CLOSED);
			cash.setCASH_APPROVAL_STATUS_ID(CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED);
			
			
			cash.setCREATEDBY(userEmail);
			cash.setLASTMODIFIEDBY(userEmail);
			cash.setCOMPANY_ID(companyId);
			cash.setMarkForDelete(false);
			
		}catch(Exception e) {
			
		}
		return cash;
	}
	
	public CashBookBalance prepareCashBookBalanceDetails(CashBookBalanceDto cashbook) {

		CashBookBalance cash = new CashBookBalance();

		try {
			
			if (cashbook.getCASH_BOOK_NAME()
					.equalsIgnoreCase(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME)) {
	
				cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN);
			} else {
				cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK);
			}
			cash.setCASH_BOOK_NAME(cashbook.getCASH_BOOK_NAME());
			cash.setCASH_BOOK_ID(cashbook.getCASH_BOOK_ID());
			//cash.setCASH_DATE();
			
			if(cashbook.getCASH_DATE()!=null) {
				java.util.Date date = dateFormat.parse(cashbook.getCASH_DATE());
				java.sql.Date cashDate = new Date(date.getTime());
				//Date newDate = new Date(cashDate.getTime() - 1 * 24 * 3600 * 1000);
				Date newDate = new Date(cashDate.getTime());
				cash.setCASH_DATE(newDate);
				cash.setCASH_CLOSED_DATE(newDate);
			}
	
			cash.setCASH_DEBIT(0.0);
			cash.setCASH_DAY_BALANCE(0.0);
			cash.setCASH_ALL_BALANCE(0.0);
			cash.setCASH_CREDIT(0.0);
			cash.setCASH_CLOSED_BY(cashbook.getCASH_CLOSED_BY());
			cash.setCASH_CLOSED_EMAIL(cashbook.getCASH_CLOSED_EMAIL());
			cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_OPEN);
			cash.setCOMPANY_ID(cashbook.getCOMPANY_ID());
			cash.setMarkForDelete(false);
		}
		catch(Exception e) {
			
		}
		return cash;
	}

	public CashBookBalanceDto prepareTodayCashBookBalanceDto(java.util.Date currentDate, Integer cashbookId, String cashBookNme,
			Integer companyId) {
		// TODO Auto-generated method stub
		
		CashBookBalanceDto cash = new CashBookBalanceDto();
		if (cashBookNme.equalsIgnoreCase(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME)) {

			cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN);
		} else {
			cash.setCASH_BOOK_MAIN_ID(CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK);
		}
		cash.setCASH_BOOK_NAME(cashBookNme);
		cash.setCASH_BOOK_ID(cashbookId);
		try {
			// fuel date converted String to date Format
			if (currentDate != null) {
				String date = dateFormat.format(currentDate);
				cash.setCASH_DATE(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		cash.setCASH_DEBIT(0.0);
		cash.setCASH_CREDIT(0.0);
		cash.setCASH_DAY_BALANCE(0.0);
		cash.setCASH_ALL_BALANCE(0.0);
		cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_OPEN);
		cash.setCOMPANY_ID(companyId);
		return cash;
		
	}
	
	public CashBookDto PrepareCashBook(Double amount, String cashdate,Integer driverId, Integer companyId) {
		CashBookDto cash = new CashBookDto();
		try {
			if(amount>=0) {
				cash.setPAYMENT_TYPE_ID(CashBookPaymentType.PAYMENT_TYPE_CREDIT);
				cash.setCASH_AMOUNT(amount);
			}else {
				cash.setPAYMENT_TYPE_ID(CashBookPaymentType.PAYMENT_TYPE_DEBIT);
				cash.setCASH_AMOUNT(-amount);
			}
			cash.setCASH_DATE(cashdate);
			cash.setCompanyId(companyId);
			cash.setCASH_APPROVAL_STATUS_ID(CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED);
			cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_OPEN);
			cash.setCASH_APPROVALDATE(cashdate);
			cash.setLASTUPDATED_DATE(cashdate);
			cash.setCASH_DOCUMENT(false);
			cash.setCASH_PAID_RECEIVED(null);
			cash.setDRIVER_ID(driverId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cash;
	}
}
