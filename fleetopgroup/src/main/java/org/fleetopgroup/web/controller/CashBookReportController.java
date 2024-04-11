package org.fleetopgroup.web.controller;

import java.sql.Date;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.CashBookStatus;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.CashBookBL;
import org.fleetopgroup.persistence.dto.CashBookDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RenewalReminderDto;
import org.fleetopgroup.persistence.model.CashBookBalance;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CashBookReportController {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ICashBookService CashBookService;
	
	@Autowired
	private IUserProfileService userProfileService;

	@Autowired	
	private ICashBookNameService	cashBookNameService;
	
	@Autowired
	private ICompanyConfigurationService companyConfigurationService;	
	
	CashBookBL CBBL = new CashBookBL();

	DecimalFormat AMOUNTFORMAT = new DecimalFormat("##,##,##0");
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat dateFormatonlyDate = new SimpleDateFormat("dd-MMM-yyyy");
	
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	@RequestMapping("/CBR")
	public ModelAndView CashBookReport(@ModelAttribute("command") RenewalReminderDto renewalReminderDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("CBR", model);
	}


	/*********************************************************/
	/******* SRS TRAVELS CASH BOOK FORMAT *******************/
	/********************************************************/
	
	
	/*SRS Travels Cash Book Date Range Report By Dev Yogi Start*/
	@RequestMapping("/CashBookdateRangeReport")
	public ModelAndView CashBookdateRangeReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("CashBook_DateRange_Report", model);
	}
	/*SRS Travels Cash Book Date Range Report By Dev Yogi End*/

	@RequestMapping(value = "/CashBookdateRangeReport", method = RequestMethod.POST)
	public ModelAndView CashBookdateRangeReport(
			@ModelAttribute("command") @RequestParam("CASH_BOOK_ID") final Integer CASH_BOOK_ID,
			@RequestParam("CASH_DATE") final String CASH_DATE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration	          = null;
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
			model.put("configuration", configuration);
			
			if (CASH_DATE != "" && CASH_BOOK_ID != 0) {
				CashBookName	cashBookName	= 	cashBookNameService.get_CashBookName(CASH_BOOK_ID);

				try {
					// fuel date converted String to date Format
					if (CASH_DATE != null) {
						java.util.Date date = dateFormat.parse(CASH_DATE);
						java.sql.Date cashbookdate = new Date(date.getTime());

						Double OpeningBalanceTotal = 0.0, CloseingBalanceTotal = 0.0;
						int n = 1;
						if (cashBookName.getCASHBOOK_NAME().equalsIgnoreCase("MAIN-CASH-BOOK")) {
							while (n < 3) {
								// this check Before Begin Opening Balances
								Date openingBalanceDate = new Date(date.getTime() - n * 24 * 3600 * 1000);

								CashBookBalance OpeningBalance = CashBookService.Validate_Last_DayCashBook_CloseOrNot(
										openingBalanceDate, CASH_BOOK_ID, CashBookStatus.CASH_BOOK_STATUS_CLOSED, userDetails.getCompany_id());
								if (OpeningBalance != null) {
									// Assign Opening Balance Total
									OpeningBalanceTotal = OpeningBalance.getCASH_ALL_BALANCE();

									String TotalOpenBalance = AMOUNTFORMAT.format(round(OpeningBalanceTotal, 2));
									model.put("OpenBalance", TotalOpenBalance);

									break;
								}

								n++;
							}
						}

						List<CashBookDto> Debit = CashBookService.Report_CashBook_Debit(CASH_BOOK_ID, cashbookdate, userDetails.getCompany_id());

						List<CashBookDto> Credit = CashBookService.Report_CashBook_Credit(CASH_BOOK_ID, cashbookdate, userDetails.getCompany_id());

						model.put("CASHBOOK_NAME", cashBookName.getCASHBOOK_NAME());
						model.put("CASHBOOK_NAME_ID", CASH_BOOK_ID);
						model.put("CASHBOOK_DATE", dateFormatonlyDate.format(date));

						Date yesterday = new Date(date.getTime() - 1 * 24 * 3600 * 1000);
						model.put("YESTERDAY", dateFormat.format(yesterday));
						Date tomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
						model.put("TOMORROW", dateFormat.format(tomorrow));

						model.put("DebitList", CBBL.prepare_CashBookDto_ListDto(Debit));
						model.put("CreditList", CBBL.prepare_CashBookDto_ListDto(Credit));
						
						CashBookBalance CloseBalance = null;
						try {
							CloseBalance = CashBookService.Validate_CashBookBalance_value(cashbookdate,
									CASH_BOOK_ID, userDetails.getCompany_id());
						} catch (Exception e) {
							
							Double debitTotal  = CBBL.getDebitOrCreditTotal(Debit);
							Double creditTotal  = CBBL.getDebitOrCreditTotal(Credit);
							
							Double balanceAmount  = OpeningBalanceTotal + ( creditTotal - debitTotal) ;
							
							model.put("DebitTotal", AMOUNTFORMAT.format(round(debitTotal, 2)));
							model.put("CreditTotal", AMOUNTFORMAT.format(round(creditTotal, 2)));
							model.put("Balance", AMOUNTFORMAT.format(round(balanceAmount, 2)));
							
							ConvertIntegerToWord amount = new ConvertIntegerToWord();
							Double amountInWord = round(balanceAmount, 2);
							int value = Integer.valueOf(amountInWord.intValue());

							String AmountWORLD = amount.convertNumberToWords(value);
							model.put("BalanceWorld", AmountWORLD);
						}
						
						if (CloseBalance != null) {
							if (CloseBalance.getCASH_STATUS_ID() == CashBookStatus.CASH_BOOK_STATUS_CLOSED) {
								if (cashBookName.getCASHBOOK_NAME().equalsIgnoreCase("MAIN-CASH-BOOK")) {
									// Assign Opening Balance Total
									CloseingBalanceTotal = CloseBalance.getCASH_ALL_BALANCE();

									String TotalCloseBalance = AMOUNTFORMAT.format(round(CloseingBalanceTotal, 2));
									model.put("Balance", TotalCloseBalance);
								} else {
									// another Cash Book only day balance
									CloseingBalanceTotal = CloseBalance.getCASH_DAY_BALANCE();

									String TotalCloseBalance = AMOUNTFORMAT.format(round(CloseingBalanceTotal, 2));
									model.put("Balance", TotalCloseBalance);
								}
								model.put("CashBookStatus", CashBookStatus.getCashBookStatusName(CloseBalance.getCASH_STATUS_ID()));

								ConvertIntegerToWord amount = new ConvertIntegerToWord();
								Double AmountWorld = round(CloseingBalanceTotal, 2);
								int value = Integer.valueOf(AmountWorld.intValue());

								String AmountWORLD = amount.convertNumberToWords(value);
								model.put("BalanceWorld", AmountWORLD);
							} else {

								// Assign Opening Balance Total
								CloseingBalanceTotal = OpeningBalanceTotal + CloseBalance.getCASH_DAY_BALANCE();

								String TotalCloseBalance = AMOUNTFORMAT.format(round(CloseingBalanceTotal, 2));
								model.put("Balance", TotalCloseBalance);

								model.put("CashBookStatus", CashBookStatus.getCashBookStatusName(CloseBalance.getCASH_STATUS_ID()));

								ConvertIntegerToWord amount = new ConvertIntegerToWord();
								Double AmountWorld = round(CloseingBalanceTotal, 2);
								int value = Integer.valueOf(AmountWorld.intValue());

								String AmountWORLD = amount.convertNumberToWords(value);
								model.put("BalanceWorld", AmountWORLD);
							}

							String TotalShowDebit = AMOUNTFORMAT.format(round(CloseBalance.getCASH_DEBIT(), 2));
							model.put("DebitTotal", TotalShowDebit);
							Double CASH_CREDIT = OpeningBalanceTotal + CloseBalance.getCASH_CREDIT();
							String TotalShowCredit = AMOUNTFORMAT.format(round(CASH_CREDIT, 2));
							model.put("CreditTotal", TotalShowCredit);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("CashBook_DateRange_Report", model);

	}


	/*SRS Travels Cash Date Range Report  By Dev Yogi Start*/
	@RequestMapping("/CashdateRangeReport")
	public ModelAndView CashdateRangeReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("Cash_DateRange_Report", model);
	}
	
	/*SRS Travels Cash Date Range Report  By Dev Yogi End */
	
	@RequestMapping(value = "/CashdateRangeReport", method = RequestMethod.POST)
	public ModelAndView CashdateRangeReport(
			@ModelAttribute("command") @RequestParam("CASH_BOOK_ID") final Integer CASH_BOOK_ID,
			@RequestParam("CASH_DATE") final String CASH_DATE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			CashBookName	cashBookName	= 	cashBookNameService.get_CashBookName(CASH_BOOK_ID);
			if (CASH_DATE != "" && CASH_BOOK_ID != 0) {

				try {
					// fuel date converted String to date Format
					if (CASH_DATE != null) {
						java.util.Date date = dateFormat.parse(CASH_DATE);
						java.sql.Date cashbookdate = new Date(date.getTime());

						Double OpeningBalanceTotal = 0.0, CloseingBalanceTotal = 0.0;
						int n = 1;
						if (cashBookName.getCASHBOOK_NAME().equalsIgnoreCase("MAIN-CASH-BOOK")) {
							while (n < 3) {
								// this check Before Begin Opening Balances
								Date openingBalanceDate = new Date(date.getTime() - n * 24 * 3600 * 1000);

								CashBookBalance OpeningBalance = CashBookService.Validate_Last_DayCashBook_CloseOrNot(
										openingBalanceDate, CASH_BOOK_ID, CashBookStatus.CASH_BOOK_STATUS_CLOSED, userDetails.getCompany_id());
								if (OpeningBalance != null) {
									// Assign Opening Balance Total
									OpeningBalanceTotal = OpeningBalance.getCASH_ALL_BALANCE();

									String TotalOpenBalance = AMOUNTFORMAT.format(round(OpeningBalanceTotal, 2));
									model.put("OpenBalance", TotalOpenBalance);

									break;
								}

								n++;
							}
						}
						List<CashBookDto> Credit_Debit = CashBookService.Report_CashBook_All(CASH_BOOK_ID, cashbookdate, userDetails.getCompany_id());

						model.put("CASHBOOK_NAME", cashBookName.getCASHBOOK_NAME());
						model.put("CASHBOOK_NAME_ID", CASH_BOOK_ID);
						model.put("CASHBOOK_DATE", dateFormatonlyDate.format(date));

						Date yesterday = new Date(date.getTime() - 1 * 24 * 3600 * 1000);
						model.put("YESTERDAY", dateFormat.format(yesterday));
						Date tomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
						model.put("TOMORROW", dateFormat.format(tomorrow));

						if((Credit_Debit==null)||(Credit_Debit.isEmpty())) 
						{
							model.put("NotFound", true); 							
						}
						
						
						model.put("cashbook", CBBL.prepare_CashBookDto_ListDto(Credit_Debit));
						
						CashBookBalance CloseBalance = null;
						try {
							CloseBalance = CashBookService.Validate_CashBookBalance_value(cashbookdate,
									CASH_BOOK_ID, userDetails.getCompany_id());
						} catch (Exception e) {
							ValueObject	valueObject	=	CBBL.getCreditDebitTotal(Credit_Debit);
							
							Double balanceAmount  = OpeningBalanceTotal + ( valueObject.getDouble("creditTotal") - valueObject.getDouble("debitTotal")) ;
							
							model.put("DebitTotal", AMOUNTFORMAT.format(round(valueObject.getDouble("debitTotal"), 2)));
							model.put("CreditTotal", AMOUNTFORMAT.format(round(valueObject.getDouble("creditTotal"), 2)));
							model.put("Balance", AMOUNTFORMAT.format(round(balanceAmount, 2)));
							
							ConvertIntegerToWord amount = new ConvertIntegerToWord();
							Double amountInWord = round(balanceAmount, 2);
							int value = Integer.valueOf(amountInWord.intValue());

							String AmountWORLD = amount.convertNumberToWords(value);
							model.put("BalanceWorld", AmountWORLD);
						}
						if (CloseBalance != null) {
							if (CloseBalance.getCASH_STATUS_ID() == CashBookStatus.CASH_BOOK_STATUS_CLOSED) {
								if (cashBookName.getCASHBOOK_NAME().equalsIgnoreCase("MAIN-CASH-BOOK")) {
									// Assign Opening Balance Total
									CloseingBalanceTotal = CloseBalance.getCASH_ALL_BALANCE();

									String TotalCloseBalance = AMOUNTFORMAT.format(round(CloseingBalanceTotal, 2));
									model.put("Balance", TotalCloseBalance);
								} else {
									// another Cash Book only day balance
									CloseingBalanceTotal = CloseBalance.getCASH_DAY_BALANCE();

									String TotalCloseBalance = AMOUNTFORMAT.format(round(CloseingBalanceTotal, 2));
									model.put("Balance", TotalCloseBalance);
								}

								model.put("CashBookStatus", CashBookStatus.getCashBookStatusName(CloseBalance.getCASH_STATUS_ID()));

								
							} else {

								// Assign Opening Balance Total
								CloseingBalanceTotal = OpeningBalanceTotal + CloseBalance.getCASH_DAY_BALANCE();

								String TotalCloseBalance = AMOUNTFORMAT.format(round(CloseingBalanceTotal, 2));
								model.put("Balance", TotalCloseBalance);

								model.put("CashBookStatus", CashBookStatus.getCashBookStatusName(CloseBalance.getCASH_STATUS_ID()));

								ConvertIntegerToWord amount = new ConvertIntegerToWord();
								Double AmountWorld = round(CloseingBalanceTotal, 2);
								int value = Integer.valueOf(AmountWorld.intValue());

								String AmountWORLD = amount.convertNumberToWords(value);
								model.put("BalanceWorld", AmountWORLD);
							}

							String TotalShowDebit = AMOUNTFORMAT.format(round(CloseBalance.getCASH_DEBIT(), 2));
							model.put("DebitTotal", TotalShowDebit);

							Double CASH_CREDIT = OpeningBalanceTotal + CloseBalance.getCASH_CREDIT();
							String TotalShowCredit = AMOUNTFORMAT.format(round(CASH_CREDIT, 2));
							model.put("CreditTotal", TotalShowCredit);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("Cash_DateRange_Report", model);

	}

	/*SRS Travels CashBook Payment/Receipt Date Range Report By Dev Yogi Start*/
	@RequestMapping("/CashPaymentdateRangeReport")
	public ModelAndView CashPaymentdateRangeReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("CashPayment_DateRange_Report", model);
	}
	/*SRS Travels CashBook Payment/Receipt Date Range Report By Dev Yogi End*/

	@RequestMapping(value = "/CashPaymentdateRangeReport", method = RequestMethod.POST)
	public ModelAndView CashPaymentdateRangeReport(
			@ModelAttribute("command") @RequestParam("CASH_BOOK_DATE") final String daterange, CashBookDto CashBook,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (daterange != "" && daterange != "") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] Issues_TO_DateRange = null;
				try {

					Issues_TO_DateRange = daterange.split(" to ");

					model.put("CASHBOOK_DATE", Issues_TO_DateRange[0] + " to " + Issues_TO_DateRange[1]);
					
					dateRangeFrom = Issues_TO_DateRange[0];
					dateRangeTo = Issues_TO_DateRange[1];
					
					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(Issues_TO_DateRange[0].trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(Issues_TO_DateRange[1].trim()+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String CashBookNo = "", PaymentTyre = "", PaymentTyreReceipt = "";

					if (CashBook.getCASH_BOOK_ID() != 0) {
						CashBookNo = " AND CB.CASH_BOOK_ID=" + CashBook.getCASH_BOOK_ID() + "";
						CashBookName	cashBookName	= cashBookNameService.get_CashBookName(CashBook.getCASH_BOOK_ID());
						model.put("CASHBOOK_NAME", cashBookName.getCASHBOOK_NAME());

					}

					if (CashBook.getCASH_NATURE_PAYMENT() != "") {
						PaymentTyre = " AND CB.CASH_NATURE_PAYMENT_ID=" + CashBook.getCASH_NATURE_PAYMENT() + "";
					}

					if (CashBook.getCASH_VOUCHER_NO() != "") {
						PaymentTyreReceipt = " AND CB.CASH_NATURE_PAYMENT_ID=" + CashBook.getCASH_VOUCHER_NO() + "";
					}

					String query = "" + CashBookNo + " " + PaymentTyre + " " + PaymentTyreReceipt + " ";

					List<CashBookDto> ReportCash = CashBookService.Report_CashBook_value(dateRangeFrom, dateRangeTo,
							query, userDetails);
					
					
					if((ReportCash == null)||(ReportCash.isEmpty())) 
					{
						model.put("NotFound", true); 
						
					}
					model.put("CashBook", CBBL.prepare_CashBookDto_ListDto(ReportCash));

					Double TotalAmount = CBBL.DebitTotal_CashBookDto(ReportCash);

					String TotalShowDebit = AMOUNTFORMAT.format(round(TotalAmount, 0));
					model.put("TotalAmount", TotalShowDebit);

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("CashPayment_DateRange_Report", model);

	}



	/*********************************************************/
	/******* BOTH CASH BOOK FORMAT *******************/
	/********************************************************/
	
	
	
	
	
	/*CashBook Status Report By Dev Yogi Start */
	@RequestMapping("/CashBookStatusReport")
	public ModelAndView CashBookStatusReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("CashBook_Status_Report", model);
	}	
	/*CashBook Status Report By Dev Yogi  End */

	@RequestMapping(value = "/CashBookStatusReport", method = RequestMethod.POST)
	public ModelAndView CashBookStatusReport(
			@ModelAttribute("command") @RequestParam("CASH_BOOK_DATE") final String daterange, CashBookDto CashBook,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<CashBookDto> cashBookStatusReportList=null; //No Record Found Validation  Logic Y
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (daterange != "" && daterange != "") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] Issues_TO_DateRange = null;
				try {

					Issues_TO_DateRange = daterange.split(" to ");
					model.put("CASHBOOK_DATE", Issues_TO_DateRange[0].trim() + " to " + Issues_TO_DateRange[1].trim());

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(Issues_TO_DateRange[0].trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(Issues_TO_DateRange[1].trim()+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String CashBookNo = "", CASH_STATUS = "";

					if (CashBook.getCASH_BOOK_ID() > 0) {
						CashBookNo = " AND CB.CASH_BOOK_ID=" + CashBook.getCASH_BOOK_ID() + "";

						model.put("CASHBOOK_NAME", cashBookNameService.get_CashBookName(CashBook.getCASH_BOOK_ID()).getCASHBOOK_NAME());

					}

					if (CashBook.getCASH_STATUS_ID() > 0) {
						CASH_STATUS = " AND CB.CASH_APPROVAL_STATUS_ID=" + CashBook.getCASH_STATUS_ID() + "";
					}

					String query = "" + CashBookNo + " " + CASH_STATUS + " ";

					List<CashBookDto> ReportCash = CashBookService.Report_CashBook_value(dateRangeFrom, dateRangeTo,
							query, userDetails);
					
					cashBookStatusReportList= CBBL.prepare_CashBookDto_ListDto(ReportCash);
					//No Record Found Validation  Logic Start Y
					if(cashBookStatusReportList == null || cashBookStatusReportList.isEmpty()) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("CashBook_Status_Report", model);
					}
					//No Record Found Validation  Logic End  Y
					
					
					model.put("CashBook",cashBookStatusReportList );
					//model.put("CashBook", CBBL.prepare_CashBookDto_ListDto(ReportCash)); //Original Code Before No Record Found
 
					Double TotalAmount = CBBL.DebitTotal_CashBookDto(ReportCash);

					String TotalShowDebit = AMOUNTFORMAT.format(round(TotalAmount, 0));
					model.put("TotalAmount", TotalShowDebit);

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("CashBook_Status_Report", model);

	}

	/*CashBook Payment/Receipt Date Range Report By Dev Yogi Starting */
	@RequestMapping("/SRICashPaymentdateRangeReport")
	public ModelAndView SRICashPaymentdateRangeReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("SRICashPayment_DateRange_Report", model);
	}
	/*CashBook Payment/Receipt Date Range Report By Dev Yogi Ending */
	
	@RequestMapping(value = "/SRICashPaymentdateRangeReport", method = RequestMethod.POST)
	public ModelAndView SRICashPaymentdateRangeReport(
			@ModelAttribute("command") @RequestParam("CASH_BOOK_DATE") final String daterange, CashBookDto CashBook,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<CashBookDto> cashBookPayReceiptDateRangeReportList=null; //No Record Found Validation  Logic Y
		
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {

			if (daterange != "" && daterange != "") {

				String dateRangeFrom = "", dateRangeTo = "";

				String[] Issues_TO_DateRange = null;
				try {

					Issues_TO_DateRange = daterange.split(" to ");

					dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(Issues_TO_DateRange[0].trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
					dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(Issues_TO_DateRange[1].trim()+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);


					model.put("CASHBOOK_DATE", dateRangeFrom + " to " + dateRangeTo);

				} catch (Exception e) {
					LOGGER.error("fuelmileage_vid:", e);
				}

				try {
					String CashBookNo = "", PaymentTyre = "", PaymentTyreReceipt = "";

					if (CashBook.getCASH_BOOK_ID() > 0) {
						CashBookNo = " AND CB.CASH_BOOK_ID=" + CashBook.getCASH_BOOK_ID() + "";

						model.put("CASHBOOK_NAME", cashBookNameService.get_CashBookName(CashBook.getCASH_BOOK_ID()).getCASHBOOK_NAME());

					}

					if (CashBook.getCASH_NATURE_PAYMENT() != "") {
						PaymentTyre = " AND CB.CASH_NATURE_PAYMENT_ID=" + CashBook.getCASH_NATURE_PAYMENT() + "";
					}

					if (CashBook.getCASH_VOUCHER_NO() != "") {
						PaymentTyreReceipt = " AND CB.CASH_NATURE_PAYMENT_ID=" + CashBook.getCASH_VOUCHER_NO() + "";
					}

					String query = "" + CashBookNo + " " + PaymentTyre + " " + PaymentTyreReceipt + " ";

					List<CashBookDto> ReportCash = CashBookService.Report_CashBook_value(dateRangeFrom, dateRangeTo,
							query, userDetails);
					
					cashBookPayReceiptDateRangeReportList= CBBL.prepare_CashBookDto_ListDto(ReportCash);
					//No Record Found Validation  Logic Start Y
					if(cashBookPayReceiptDateRangeReportList == null || cashBookPayReceiptDateRangeReportList.isEmpty()) 
					{
						model.put("NotFound", true); 
						return new ModelAndView("SRICashPayment_DateRange_Report", model);
					}
					//No Record Found Validation  Logic End  Y
					
					
					model.put("CashBook",cashBookPayReceiptDateRangeReportList );
					//model.put("CashBook", CBBL.prepare_CashBookDto_ListDto(ReportCash)); //Original Code Before No Record Found

					Double TotalAmount = CBBL.DebitTotal_CashBookDto(ReportCash);

					String TotalShowDebit = AMOUNTFORMAT.format(round(TotalAmount, 0));
					model.put("TotalAmount", TotalShowDebit);

				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("SRICashPayment_DateRange_Report", model);

	}
	
	/* Cash Date Range Report Task by dev yogi  starting */
	@RequestMapping("/SRICashdateRangeReport")
	public ModelAndView SRICashdateRangeReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("SRICash_DateRange_Report", model);
	}
	/* Cash Date Range Report Task by dev yogi ending */
	
	
	
	
	

	@RequestMapping(value = "/SRICashdateRangeReport", method = RequestMethod.POST)
	public ModelAndView SRIDURGAMBACashdateRangeReport(
			@ModelAttribute("command") @RequestParam("CASH_BOOK_ID") final Integer CASH_BOOK_ID,
			@RequestParam("CASH_DATE") final String CASH_DATE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		List<CashBookDto> cashDateRangeReport=null; //No Record Found Validation  Logic Y

		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (CASH_DATE != "" && CASH_BOOK_ID != 0) {

				try {
					// fuel date converted String to date Format
					if (CASH_DATE != null) {
						java.util.Date date = dateFormat.parse(CASH_DATE);
						java.sql.Date cashbookdate = new Date(date.getTime());

						Double OpeningBalanceTotal = 0.0, CloseingBalanceTotal = 0.0;
						int n = 1;
						
						while (n < 3) {
							// this check Before Begin Opening Balances
							Date openingBalanceDate = new Date(date.getTime() - n * 24 * 3600 * 1000);

							CashBookBalance OpeningBalance = CashBookService
									.Validate_Last_DayCashBook_CloseOrNot(openingBalanceDate, CASH_BOOK_ID, CashBookStatus.CASH_BOOK_STATUS_CLOSED, userDetails.getCompany_id() );
							if (OpeningBalance != null) {
								// Assign Opening Balance Total
								OpeningBalanceTotal = OpeningBalance.getCASH_ALL_BALANCE();
								DecimalFormat OpenTyreALL = new DecimalFormat("##,##,##0");
								String TotalOpenBalance = OpenTyreALL.format(round(OpeningBalanceTotal, 2));
								model.put("OpenBalance", TotalOpenBalance);

								// System.out.println(TotalOpenBalance);
								break;
							}

							n++;
						}
						List<CashBookDto> Credit_Debit = CashBookService.Report_CashBook_All(CASH_BOOK_ID, cashbookdate, userDetails.getCompany_id());

						model.put("CASHBOOK_NAME", cashBookNameService.get_CashBookName(CASH_BOOK_ID).getCASHBOOK_NAME());
						
						model.put("CASHBOOK_NAME_ID", CASH_BOOK_ID);
						model.put("CASHBOOK_DATE", dateFormatonlyDate.format(date));

						Date yesterday = new Date(date.getTime() - 1 * 24 * 3600 * 1000);
						model.put("YESTERDAY", dateFormat.format(yesterday));
						Date tomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
						model.put("TOMORROW", dateFormat.format(tomorrow));

						cashDateRangeReport=  CBBL.prepare_CashBookDto_ListDto(Credit_Debit); //No Record Found Validation  Logic
						//No Record Found Validation  Logic Start Y
						if(cashDateRangeReport == null || cashDateRangeReport.isEmpty()) 
						{
							model.put("NotFound", true); 
							return new ModelAndView("SRICash_DateRange_Report", model);
						}
						//No Record Found Validation  Logic End  Y			
						
						
						model.put("cashbook",cashDateRangeReport);
						//model.put("cashbook", CBBL.prepare_CashBookDto_ListDto(Credit_Debit)); //Original Code Before No Record Found

						CashBookBalance CloseBalance = CashBookService.Validate_CashBookBalance_value(cashbookdate,
								CASH_BOOK_ID, userDetails.getCompany_id());
						if (CloseBalance != null) {
							if (CloseBalance.getCASH_STATUS_ID() == CashBookStatus.CASH_BOOK_STATUS_CLOSED) {

								// Assign Opening Balance Total
								CloseingBalanceTotal = CloseBalance.getCASH_ALL_BALANCE();
								DecimalFormat CloseTyreALL = new DecimalFormat("##,##,##0");
								String TotalCloseBalance = CloseTyreALL.format(round(CloseingBalanceTotal, 2));
								model.put("Balance", TotalCloseBalance);

								model.put("CashBookStatus", CashBookStatus.getCashBookStatusName(CloseBalance.getCASH_STATUS_ID()));

								ConvertIntegerToWord amount = new ConvertIntegerToWord();
								Double AmountWorld = round(CloseingBalanceTotal, 2);
								int value = Integer.valueOf(AmountWorld.intValue());

								String AmountWORLD = amount.convertNumberToWords(value);
								model.put("BalanceWorld", AmountWORLD);
							} else {

								// Assign Opening Balance Total
								CloseingBalanceTotal = OpeningBalanceTotal + CloseBalance.getCASH_DAY_BALANCE();
								DecimalFormat CloseTyreALL = new DecimalFormat("##,##,##0");
								String TotalCloseBalance = CloseTyreALL.format(round(CloseingBalanceTotal, 2));
								model.put("Balance", TotalCloseBalance);

								model.put("CashBookStatus", CashBookStatus.getCashBookStatusName(CloseBalance.getCASH_STATUS_ID()));

								ConvertIntegerToWord amount = new ConvertIntegerToWord();
								Double AmountWorld = round(CloseingBalanceTotal, 2);
								int value = Integer.valueOf(AmountWorld.intValue());

								String AmountWORLD = amount.convertNumberToWords(value);
								model.put("BalanceWorld", AmountWORLD);
							}

							DecimalFormat TyreALL = new DecimalFormat("##,##,##0");
							String TotalShowDebit = TyreALL.format(round(CloseBalance.getCASH_DEBIT(), 2));
							model.put("DebitTotal", TotalShowDebit);

							Double CASH_CREDIT = OpeningBalanceTotal + CloseBalance.getCASH_CREDIT();
							String TotalShowCredit = TyreALL.format(round(CASH_CREDIT, 2));
							model.put("CreditTotal", TotalShowCredit);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("SRICash_DateRange_Report", model);

	}


	/*********************************************************/
	/******* SRI DURGAMBA CASH BOOK FORMAT *******************/
	/********************************************************/
	
	
	/* Task for Cash Book Date Range Report By dev yogi */
	@RequestMapping("/SRICashBookdateRangeReport")
	public ModelAndView SRICashBookdateRangeReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		return new ModelAndView("CashBook_DateRange_Report", model);
	}
	/* Task for Cash Book Date Range Report By dev yogi */

	@RequestMapping(value = "/SRICashBookdateRangeReport", method = RequestMethod.POST)
	public ModelAndView SRIDURGAMBACashBookdateRangeReport(
			@ModelAttribute("command") @RequestParam("CASH_BOOK_ID") final Integer CASH_BOOK_ID,
			@RequestParam("CASH_DATE") final String CASH_DATE, final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration	          = null;
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
			model.put("configuration", configuration);
			CashBookName	cashBookName	= 	cashBookNameService.get_CashBookName(CASH_BOOK_ID);
			if (CASH_DATE != "" && CASH_BOOK_ID != 0) {

				try {
					// fuel date converted String to date Format
					if (CASH_DATE != null) {
						java.util.Date date = dateFormat.parse(CASH_DATE);
						java.sql.Date cashbookdate = new Date(date.getTime());

						Double OpeningBalanceTotal = 0.0, CloseingBalanceTotal = 0.0;
						int n = 1;
						
						while (n < 3) {
							// this check Before Begin Opening Balances
							Date openingBalanceDate = new Date(date.getTime() - n * 24 * 3600 * 1000);

							CashBookBalance OpeningBalance = CashBookService
									.Validate_Last_DayCashBook_CloseOrNot(openingBalanceDate, CASH_BOOK_ID, CashBookStatus.CASH_BOOK_STATUS_CLOSED, userDetails.getCompany_id());
							if (OpeningBalance != null) {
								// Assign Opening Balance Total
								OpeningBalanceTotal = OpeningBalance.getCASH_ALL_BALANCE();
								DecimalFormat OpenTyreALL = new DecimalFormat("##,##,##0");
								String TotalOpenBalance = OpenTyreALL.format(round(OpeningBalanceTotal, 2));
								model.put("OpenBalance", TotalOpenBalance);

								break;
							}

							n++;
						}
						/* } */

						List<CashBookDto> Debit = CashBookService.Report_CashBook_Debit(CASH_BOOK_ID, cashbookdate, userDetails.getCompany_id());
						

						List<CashBookDto> Credit = CashBookService.Report_CashBook_Credit(CASH_BOOK_ID, cashbookdate, userDetails.getCompany_id());
						
						if(Debit == null && Credit == null) 
						{
							model.put("NotFound", true); 
							return new ModelAndView("CashBook_DateRange_Report", model);
						}else {
							model.put("Found", true); 
						}
						model.put("CASHBOOK_NAME_ID", CASH_BOOK_ID);
						model.put("CASHBOOK_NAME", cashBookName.getCASHBOOK_NAME());
						model.put("CASHBOOK_DATE", dateFormatonlyDate.format(date));

						Date yesterday = new Date(date.getTime() - 1 * 24 * 3600 * 1000);
						model.put("YESTERDAY", dateFormat.format(yesterday));
						Date tomorrow = new Date(date.getTime() + 1 * 24 * 3600 * 1000);
						model.put("TOMORROW", dateFormat.format(tomorrow));

						model.put("DebitList", CBBL.prepare_CashBookDto_ListDto(Debit));
						model.put("CreditList", CBBL.prepare_CashBookDto_ListDto(Credit));

						
						CashBookBalance CloseBalance = null;
						try {
							CloseBalance = CashBookService.Validate_CashBookBalance_value(cashbookdate,
									CASH_BOOK_ID, userDetails.getCompany_id());
						} catch (Exception e) {
							
							Double debitTotal  = CBBL.getDebitOrCreditTotal(Debit);
							Double creditTotal  = CBBL.getDebitOrCreditTotal(Credit);
							
							Double balanceAmount  = OpeningBalanceTotal + ( creditTotal - debitTotal) ;
							
							model.put("DebitTotal", AMOUNTFORMAT.format(round(debitTotal, 2)));
							model.put("CreditTotal", AMOUNTFORMAT.format(round(creditTotal, 2)));
							model.put("Balance", AMOUNTFORMAT.format(round(balanceAmount, 2)));
							
							ConvertIntegerToWord amount = new ConvertIntegerToWord();
							Double amountInWord = round(balanceAmount, 2);
							int value = Integer.valueOf(amountInWord.intValue());

							String AmountWORLD = amount.convertNumberToWords(value);
							model.put("BalanceWorld", AmountWORLD);
						}
						
						if (CloseBalance != null) {
							if (CloseBalance.getCASH_STATUS_ID() == CashBookStatus.CASH_BOOK_STATUS_CLOSED) {
								// Assign Opening Balance Total
								CloseingBalanceTotal = CloseBalance.getCASH_ALL_BALANCE();
								DecimalFormat CloseTyreALL = new DecimalFormat("##,##,##0");
								String TotalCloseBalance = CloseTyreALL.format(round(CloseingBalanceTotal, 2));
								model.put("Balance", TotalCloseBalance);

								model.put("CashBookStatus", CashBookStatus.getCashBookStatusName(CloseBalance.getCASH_STATUS_ID()));

								ConvertIntegerToWord amount = new ConvertIntegerToWord();
								Double AmountWorld = round(CloseingBalanceTotal, 2);
								int value = Integer.valueOf(AmountWorld.intValue());

								String AmountWORLD = amount.convertNumberToWords(value);
								model.put("BalanceWorld", AmountWORLD);
							} else {

								// Assign Opening Balance Total
								CloseingBalanceTotal = OpeningBalanceTotal + CloseBalance.getCASH_DAY_BALANCE();
								DecimalFormat CloseTyreALL = new DecimalFormat("##,##,##0");
								String TotalCloseBalance = CloseTyreALL.format(round(CloseingBalanceTotal, 2));
								model.put("Balance", TotalCloseBalance);

								model.put("CashBookStatus", CashBookStatus.getCashBookStatusName(CloseBalance.getCASH_STATUS_ID()));

								ConvertIntegerToWord amount = new ConvertIntegerToWord();
								Double AmountWorld = round(CloseingBalanceTotal, 2);
								int value = Integer.valueOf(AmountWorld.intValue());

								String AmountWORLD = amount.convertNumberToWords(value);
								model.put("BalanceWorld", AmountWORLD);
							}

							DecimalFormat TyreALL = new DecimalFormat("##,##,##0");
							String TotalShowDebit = TyreALL.format(round(CloseBalance.getCASH_DEBIT(), 2));
							model.put("DebitTotal", TotalShowDebit);
							Double CASH_CREDIT = OpeningBalanceTotal + CloseBalance.getCASH_CREDIT();
							String TotalShowCredit = TyreALL.format(round(CASH_CREDIT, 2));
							model.put("CreditTotal", TotalShowCredit);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				model.put("company", userProfileService.findUserProfileByUser_email(userDetails.getEmail()));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("CashBook_DateRange_Report", model);

	}
	@RequestMapping("/CashBookReport")
	public ModelAndView CashBookReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails		userDetails		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// show the Vendor List
			model.put("CashBook", CBBL.prepare_CashBookName_List(cashBookNameService.list_CashBookNameByPermission(userDetails)));

		} catch (Exception e) {

		}finally {
			userDetails		= null;
		}
		return new ModelAndView("ReportCashBook", model);
	}

	@RequestMapping(value = "/CashBookReport", method = RequestMethod.POST)
	public ModelAndView CashBookReport(@RequestParam("CASH_BOOK_DATE") final String daterange, CashBookDto CashBook,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (daterange != "" && daterange != "") {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] Issues_TO_DateRange = null;
			try {

				Issues_TO_DateRange = daterange.split(" to ");

				dateRangeFrom = Issues_TO_DateRange[0];
				dateRangeTo = Issues_TO_DateRange[1];

			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {
				String CashBookNo = "", PaymentTyre = "", PaymentTyreReceipt = "";

				if (CashBook.getCASH_BOOK_ID() != 0) {
					CashBookNo = " AND CB.CASH_BOOK_ID=" + CashBook.getCASH_BOOK_ID() + "";
				}

				if (CashBook.getCASH_NATURE_PAYMENT() != "") {
					PaymentTyre = " AND CB.CASH_NATURE_PAYMENT_ID=" + CashBook.getCASH_NATURE_PAYMENT() + "";
				}

				if (CashBook.getCASH_VOUCHER_NO() != "") {
					PaymentTyreReceipt = " AND CB.CASH_NATURE_PAYMENT_ID=" + CashBook.getCASH_VOUCHER_NO() + "";
				}

				String query = "" + CashBookNo + " " + PaymentTyre + " " + PaymentTyreReceipt + " ";
				
				
				
				model.put("CashBook", CBBL.prepare_CashBookDto_ListDto(
						CashBookService.Report_CashBook_value(dateRangeFrom, dateRangeTo, query, userDetails)));


				

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/CashBookReport", model);
		}

		return new ModelAndView("CashBook_Report", model);
	}
	
	@RequestMapping("/SRICashBookReport")
	public ModelAndView SRICashBookReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails		userDetails		= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// show the Vendor List
			model.put("CashBook", CBBL.prepare_CashBookName_List(cashBookNameService.list_CashBookNameByPermission(userDetails)));

		} catch (Exception e) {
			
		}finally {
			userDetails		= null;
		}
		return new ModelAndView("ReportSRICashBook", model);
	}


	@RequestMapping(value = "/SRICashBookReport", method = RequestMethod.POST)
	public ModelAndView SRICashBookReport(@RequestParam("CASH_BOOK_DATE") final String daterange, CashBookDto CashBook,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (daterange != "" && daterange != "") {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] Issues_TO_DateRange = null;
			try {

				Issues_TO_DateRange = daterange.split(" to ");

				dateRangeFrom = Issues_TO_DateRange[0];
				dateRangeTo = Issues_TO_DateRange[1];

			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {
				String CashBookNo = "", PaymentTyre = "", PaymentTyreReceipt = "";

				if (CashBook.getCASH_BOOK_ID() != 0) {
					CashBookNo = " AND CB.CASH_BOOK_ID=" + CashBook.getCASH_BOOK_ID() + "";
				}

				if (CashBook.getCASH_NATURE_PAYMENT_ID() != null && CashBook.getCASH_NATURE_PAYMENT_ID() > 0) {
					PaymentTyre = " AND CB.CASH_NATURE_PAYMENT_ID=" + CashBook.getCASH_NATURE_PAYMENT_ID() + "";
				}

				if (CashBook.getCASH_VOUCHER_NO() != "") {
					PaymentTyreReceipt = " AND CB.CASH_NATURE_PAYMENT_ID=" + CashBook.getCASH_VOUCHER_NO() + "";
				}

				String query = "" + CashBookNo + " " + PaymentTyre + " " + PaymentTyreReceipt + " ";

				model.put("CashBook", CBBL.prepare_CashBookDto_ListDto(
						CashBookService.Report_CashBook_value(dateRangeFrom, dateRangeTo, query, userDetails)));

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/SRICashBookReport", model);
		}

		return new ModelAndView("SRICashBook_Report", model);
	}

	@RequestMapping("/CashBookEntryReport")
	public ModelAndView CashBookEntryReport() {
		Map<String, Object> model = new HashMap<String, Object>();

		CustomUserDetails		userDetails		= null;
		HashMap<String, Object> 	configuration	= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.CASHBOOK_CONFIG);
			// show the Vendor List
			model.put("CashBook", CBBL.prepare_CashBookName_List(cashBookNameService.list_CashBookNameByPermission(userDetails)));
			model.put("configuration", configuration);
		} catch (Exception e) {

		}finally {
			userDetails		= null;
		}
		return new ModelAndView("ReportCashBookEntry", model);
	}

	@RequestMapping(value = "/CashBookEntryReport", method = RequestMethod.POST)
	public ModelAndView CashBookEntryReport(@RequestParam("CASH_BOOK_DATE") final String daterange, CashBookDto CashBook,
			final HttpServletRequest request) {

		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (daterange != "" && daterange != "") {

			String dateRangeFrom = "", dateRangeTo = "";

			String[] Issues_TO_DateRange = null;
			try {

				Issues_TO_DateRange = daterange.split(" to ");

				dateRangeFrom = dateFormatSQL.format(dateFormat.parse(Issues_TO_DateRange[0]));
				dateRangeTo = dateFormatSQL.format(dateFormat.parse(Issues_TO_DateRange[1]));

			} catch (Exception e) {
				LOGGER.error("fuelmileage_vid:", e);
			}

			try {
				String CashBookNo = "", PaymentTyre = "", PaymentTyreReceipt = "";

				if (CashBook.getCASH_BOOK_ID() != 0) {
					CashBookNo = " AND CB.CASH_BOOK_ID=" + CashBook.getCASH_BOOK_ID() + "";
				}

				if (CashBook.getCASH_NATURE_PAYMENT() != "") {
					PaymentTyre = " AND CB.CASH_NATURE_PAYMENT_ID=" + CashBook.getCASH_NATURE_PAYMENT() + "";
				}

				if (CashBook.getCASH_VOUCHER_NO() != "") {
					PaymentTyreReceipt = " AND CB.CASH_NATURE_PAYMENT_ID=" + CashBook.getCASH_VOUCHER_NO() + "";
				}

				String query = "" + CashBookNo + " " + PaymentTyre + " " + PaymentTyreReceipt + " ";
				
				
				
				model.put("CashBook", CBBL.prepare_CashBookDto_ListDto(
						CashBookService.Report_CashBook_value(dateRangeFrom, dateRangeTo, query, userDetails)));


				

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/CashBookReport", model);
		}

		return new ModelAndView("CashBookEntry_Report", model);
	}
}
