/**
 * 
 */
package org.fleetopgroup.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.CashBookApprovalStatus;
import org.fleetopgroup.constant.CashBookBalanceStatus;
import org.fleetopgroup.constant.CashBookPaymentType;
import org.fleetopgroup.constant.CashBookStatus;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.CashBookBL;
import org.fleetopgroup.persistence.dto.CashBookBalanceDto;
import org.fleetopgroup.persistence.dto.CashBookDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.CashBook;
import org.fleetopgroup.persistence.model.CashBookBalance;
import org.fleetopgroup.persistence.model.CashBookHistory;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.model.CashBookSequenceCounter;
import org.fleetopgroup.persistence.model.TripExpense;
import org.fleetopgroup.persistence.model.TripIncome;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.persistence.serviceImpl.ITripExpenseService;
import org.fleetopgroup.persistence.serviceImpl.ITripIncomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;

import com.google.gson.Gson;

/**
 * @author fleetop
 *
 */
@Controller
public class CashBookController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ICashBookService CashBookService;
	CashBookBL CBBL = new CashBookBL();

	@Autowired
	private ITripExpenseService TripExpenseService;

	@Autowired
	private ITripIncomeService TripIncomeService;

	@Autowired
	private ICashBookNameService CashBookNameService;

	@Autowired
	private ICashBookSequenceService cashBookSequenceService;
	
	@Autowired
	private ICompanyConfigurationService companyConfigurationService;

	CashBookBL NameBL = new CashBookBL();

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

	// This CashBook Search Controller is Search in cashBook Table
	// Base on CashBook_Id and Voucher No To show the Details
	@RequestMapping(value = "/searchCashBook", method = RequestMethod.POST)
	public ModelAndView searchCashBook(@RequestParam("Search") final String CashBookName,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		if (CashBookName != null) {
			try {

				model.put("CashBook", CBBL.prepare_CashBookDto_ListDto(
						CashBookService.Search_CashBook_value(CashBookName, customUserDetails)));
			} catch (NullPointerException e) {
				return new ModelAndView("redirect:/NotFound.in");
			}
		} else {
			model.put("emptyNotRange", true);
			return new ModelAndView("redirect:/CashBookReport", model);
		}

		return new ModelAndView("CashBook_Report", model);
	}

	// This CashBook Search Controller is Search in cashBook Table
	// Base on CashBook_Id and Voucher No To show the Details
	@RequestMapping(value = "/searchCashBookShow", method = RequestMethod.POST)
	public ModelAndView searchCashBook(@RequestParam("Search") final Long CashId, final HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (CashId != null) {
				CashBookDto cashbook = CashBookService.getCashBookByNumber(CashId, userDetails);

				model.put("CashBook", CBBL.Show_CashBook_Value(cashbook));
			}
		} catch (NullPointerException e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/CashBook/1.in", model);
		} catch (Exception e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/CashBook/1.in", model);
		} finally {
			userDetails = null;
		}
		return new ModelAndView("Show_CashBook", model);
	}

	/* show main page of Issues */
	@RequestMapping(value = "/CashBook/{pageNumber}", method = RequestMethod.GET)
	public String CashBook(@PathVariable Integer pageNumber, Model model) throws Exception {
		CustomUserDetails userDetails = null;
		List<CashBookName> cashBookPermissionList = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			cashBookPermissionList = CashBookNameService.list_CashBookNameByPermission(userDetails);
			if (cashBookPermissionList == null || cashBookPermissionList.size() <= 0) {
				model.addAttribute("cashBookPermissionNotFound", true);
				return "cashbook";
			} else {

				CashBookName cashBookName = CashBookService.getUserDefaultCashBook(cashBookPermissionList);
				Page<CashBook> page = CashBookService.getDeployment_Page_CashBook(cashBookName.getNAMEID(), pageNumber,
						userDetails);
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				model.addAttribute("deploymentLog", page);
				model.addAttribute("beginIndex", begin);
				model.addAttribute("endIndex", end);
				model.addAttribute("currentIndex", current);

				model.addAttribute("CashBookCount", page.getTotalElements());

				List<CashBookDto> pageList = (CashBookService.list_find_CashBook(cashBookName.getNAMEID(), pageNumber,
						userDetails));

				model.addAttribute("CashBook", CBBL.prepare_CashBookDto_ListDto(pageList));

				model.addAttribute("CashBookName", NameBL.prepare_CashBookName_List(cashBookPermissionList));

				model.addAttribute("SelectCashBook", cashBookName.getCASHBOOK_NAME());
				model.addAttribute("cashBookPermissionNotFound", false);
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("cash Book Page:", e);
			e.printStackTrace();
		} finally {
			userDetails = null;
			cashBookPermissionList = null;
		}
		return "cashbook";
	}

	/* show main page of Issues */
	@RequestMapping(value = "/CashBook/{SelectBook}/{pageNumber}", method = RequestMethod.GET)
	public String CashBook(@PathVariable("SelectBook") Integer SelectBook,
			@PathVariable("pageNumber") Integer pageNumber, Model model) throws Exception {
		CustomUserDetails customUserDetails = null;
		try {
			customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			Page<CashBook> page = CashBookService.getDeployment_Page_CashBook(SelectBook, pageNumber,
					customUserDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);

			LOGGER.error("cash Book Page:", page.getTotalElements());
			
			model.addAttribute("CashBookCount", page.getTotalElements());

			List<CashBookDto> pageList = (CashBookService.list_find_CashBook(SelectBook, pageNumber, customUserDetails));

			model.addAttribute("CashBook", CBBL.prepare_CashBookDto_ListDto(pageList));

			model.addAttribute("CashBookName", NameBL
					.prepare_CashBookName_List(CashBookNameService.list_CashBookNameByPermission(customUserDetails)));

			model.addAttribute("SelectCashBook", SelectBook);

			CashBookName CBNAM = CashBookNameService.get_CashBookName(SelectBook);
			model.addAttribute("SelectCashBookName", CBNAM.getCASHBOOK_NAME());

		} catch (NullPointerException e) {
			e.printStackTrace();
			return "redirect:/NotFound.in";
		} catch (Exception e) {
			LOGGER.error("cash Book Page:", e);
			e.printStackTrace();
		}
		return "cashbook_StatusBook";
	}

	// This Cash Book Add in Total Cash book Entries to the Using NotApproval
	// Status in
	// Once Who Approval Olny Sent the Value IN Database
	@RequestMapping(value = "/addCashBook", method = RequestMethod.GET)
	public ModelAndView addCashBook() {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("CashBook",
					CBBL.prepare_CashBookName_List(CashBookNameService.list_CashBookNameByPermission(userDetails)));

			model.put("PaidBy", userDetails.getFirstName());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Add_CashBook", model);
	}

	// This Cash Book To WithOut Approval mean Who Create Today Entries in
	// addCashBook Controller to this controller
	// This to With out NotApproval Status Details
	@RequestMapping(value = "/WithApprovalCashBook", method = RequestMethod.POST)
	public ModelAndView WithApprovalCashBook(@RequestParam("CASH_BOOK_NO") final String CASH_BOOK_NO,
			@ModelAttribute("command") CashBookDto CashBook, @RequestParam("input-file-preview") MultipartFile file,
			HttpServletRequest req, RedirectAttributes redir) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		CashBookSequenceCounter sequenceCounter = null;
		try {
			// Validate Voucher number this there or not
			// Already Exits return main add CashBook page
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<CashBook> validate = CashBookService.Validate_CashBook_value(CashBook.getCASH_VOUCHER_NO(),
					userDetails.getCompany_id());
			if (validate != null && !validate.isEmpty()) {
				String AlreadyEx = "";
				for (CashBook exist : validate) {
					AlreadyEx += exist.getCASH_VOUCHER_NO() + "-" + exist.getCASHID() + " ";
				}
				redir.addFlashAttribute("Already", AlreadyEx);
				return new ModelAndView("redirect:/addCashBook.in?alreadyExist=true");
			} else {
				boolean ClosedValidate = false, ClosedCurent = false;
				try {
					// get the issues Dto to save issues
					CashBook cash = CBBL.prepareCashBook_Value(CashBook);
					
					int n = 1;
					// this check Before Begin Opening Balances
					Date openingDate = new Date(cash.getCASH_DATE().getTime() - n * 24 * 3600 * 1000);
					List<CashBookBalance> validateCloseLast = CashBookService.Validate_Last_DayCashBook_CloseOrNot_list(
							openingDate, cash.getCASH_BOOK_ID(), cash.getCOMPANY_ID());
					if (validateCloseLast != null && !validateCloseLast.isEmpty()) {
						ClosedValidate = true;

					} else {
						ClosedValidate = true;
						/** This Balance To Create New Date Balance */
						// get the Cash to save CashBalance
						CashBookBalance cashBalance = CBBL.prepare_CLOSED_LAST_Value(openingDate,
								cash.getCASH_BOOK_NO());
						cashBalance.setCOMPANY_ID(userDetails.getCompany_id());
						cashBalance.setCASH_BOOK_ID(cash.getCASH_BOOK_ID());
						// save to databases
						CashBookService.registerNewCashBookBalance(cashBalance);
					}
					// Checking last day entries close or not
					if (ClosedValidate) {
						try {
							CashBookBalance currentdatevalidate = CashBookService.Validate_Last_DayCashBook_CloseOrNot(
									cash.getCASH_DATE(), cash.getCASH_BOOK_ID(), CashBookStatus.CASH_BOOK_STATUS_CLOSED,
									userDetails.getCompany_id());
							if (currentdatevalidate != null) {
								ClosedCurent = false;
							} else {
								ClosedCurent = true;
							}
							if (ClosedCurent) {

								if (!file.isEmpty()) {

									cash.setCASH_DOCUMENT(true);
								} else {

									cash.setCASH_DOCUMENT(false);
								}
								sequenceCounter = cashBookSequenceService.findNextSequenceNumber(userDetails.getCompany_id());
								if (sequenceCounter == null) {
									return new ModelAndView("redirect:/addCashBook.in?sequenceNotFound=true");
								}
								cash.setCASH_NUMBER(sequenceCounter.getNextVal());
								// save to databases
								CashBookService.registerNewCashBook(cash);
								// cashBookSequenceService.updateNextSequenceCounter(sequenceCounter.getNextVal()
								// + 1, sequenceCounter.getSequence_Id());
								String success = " " + cash.getCASH_NUMBER();
								redir.addFlashAttribute("successID", success);

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
									
									CashBookService.save(cashDoc);

									// Note: CashBookDocument ID Update CasdBook
									// Table Column
									
									CashBookService.Update_CashBook_Document_ID_to_CashBook(cashDoc.get_id(),
											true, cash.getCASHID());
								}
								// show the driver list in all
								model.put("success", true);

								return new ModelAndView("redirect:/showCashBook.in?Id=" + cash.getCASHID() + "", model);
							} else {

								return new ModelAndView("redirect:/addCashBook.in?closedCB=true");
							}
						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/addCashBook.in?danger=true");
						}
					} else {

						return new ModelAndView("redirect:/addCashBook.in?closeLastDayBalance=true");
					}
				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/addCashBook.in?danger=true");
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/addCashBook.in?danger=true");
		}

	}

	// This Cash Book NotApproval to To Change Approval Status mean Who Create
	// Today Entries in addCashBook Controller
	// this save WithApprovalCashBook to change approvalCashBook Controller
	// This Change Approval Status also Add CashBookBalance Table to Details
	@RequestMapping(value = "/approvalCashBook", method = RequestMethod.POST)
	public ModelAndView approvalCashBook(@RequestParam("CASHID") final Long CASHID,
			@RequestParam("CASH_BOOK_ID") final Integer CASH_BOOK_ID, @ModelAttribute("command") CashBookDto CashBook,
			HttpServletRequest req, RedirectAttributes redir) {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Vendor Type List
		try {

			CashBook cbBook = CashBookService.getCashBookByID(CASHID);
			if (cbBook == null) {
				return new ModelAndView("redirect:/addCashBook.in?alreadyExist=true");
			} else {
				boolean ClosedCurent = false;
				try {

					// get the issues Dto to save issues
					/** Who Login User Email ID */
					CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
							.getAuthentication().getPrincipal();

					java.util.Date currentDateUpdate = new java.util.Date();
					Timestamp cASH_APPROVALDATE = new java.sql.Timestamp(currentDateUpdate.getTime());

					try {
						// get Current date For to change dateFormar for
						String APdate = dateFormat.format(cASH_APPROVALDATE);
						// fuel date converted String to date Format
						if (APdate != null) {
							java.util.Date date = dateFormat.parse(APdate);
							java.sql.Date cashDate = new Date(date.getTime());
							cbBook.setCASH_APPROVALDATE(cashDate);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					try {
						CashBookBalance currentdatevalidate = CashBookService.Validate_Last_DayCashBook_CloseOrNot(
								cbBook.getCASH_APPROVALDATE(), cbBook.getCASH_BOOK_ID(),
								CashBookStatus.CASH_BOOK_STATUS_CLOSED, userDetails.getCompany_id());
						if (currentdatevalidate != null) {
							ClosedCurent = false;
						} else {
							ClosedCurent = true;
						}
						if (ClosedCurent) {

							CashBookService.Update_CashBook_Approval_Entries(
									CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, userDetails.getEmail(),
									cASH_APPROVALDATE, CashBook.getCASH_APPROVALCOMMENT(), cbBook.getCASHID(),
									userDetails.getCompany_id());

							// Approval Date To Update Balance validate in
							CashBookBalance validateBalance = CashBookService.Validate_CashBookBalance_value(
									cbBook.getCASH_APPROVALDATE(), cbBook.getCASH_BOOK_ID(),
									userDetails.getCompany_id());

							try {
								if (validateBalance != null) {
									Long CBBID = validateBalance.getCBBID();
									/**
									 * cash Balance Already Create in DB To Update old Balance to New
									 */
									if (cbBook.getPAYMENT_TYPE_ID() == CashBookPaymentType.PAYMENT_TYPE_DEBIT) {

										CashBookService.Update_DEBIT_CashBalance_SubtrackBalance_Details(
												CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED,
												dateFormat_SQL.format(cbBook.getCASH_APPROVALDATE()),
												cbBook.getCASH_BOOK_ID(), cbBook.getPAYMENT_TYPE_ID(), CBBID,userDetails.getCompany_id());
									} else if (cbBook.getPAYMENT_TYPE_ID() == CashBookPaymentType.PAYMENT_TYPE_CREDIT) {

										CashBookService.Update_CREDIT_CashBalance_AddBalance_Details(
												CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED,
												dateFormat_SQL.format(cbBook.getCASH_APPROVALDATE()),
												cbBook.getCASH_BOOK_ID(), cbBook.getPAYMENT_TYPE_ID(), CBBID,userDetails.getCompany_id());
									}

								} else {

									/**
									 * This Balance To Create New Date Balance
									 */
									// get the Cash to save CashBalance
									CashBookBalance cashBalance = CBBL
											.prepareCashBookBalance_Value_cashBookApproval(cbBook);
									// save to databases
									CashBookService.registerNewCashBookBalance(cashBalance);

								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							String success = " " + cbBook.getCASH_NUMBER();
							redir.addFlashAttribute("successID", success);
							// show the driver list in all
							model.put("success", true);

						} else {

							return new ModelAndView("redirect:/addCashBook.in?closedCB=true");
						}
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("redirect:/addCashBook.in?danger=true");
					}

				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/addCashBook.in?danger=true");
				}

			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/addCashBook.in?danger=true");
		}
		String CSA = CashBook.getCASH_BOOK_NO().replace(" ", "_");
		model.put("CBN", CSA);
		model.put("CASH_BOOK_ID", CASH_BOOK_ID);
		return new ModelAndView("redirect:/addMoreCashBook.in", model);
	}

	// This Add More cash book entries is add more CashBook Details in More time
	// no need to select again again
	// in cash book name that will show cash book name
	// Only Today CashBook Entries More
	@RequestMapping(value = "/addMoreCashBook", method = RequestMethod.GET)
	public ModelAndView addMoreCashBook(@RequestParam("CBN") final String CashBookName,
			@RequestParam("CASH_BOOK_ID") Integer CASH_BOOK_ID, final HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Vendor Type List
		try {

			if (CashBookName != null) {
				String CSA = CashBookName.replace("_", " ");
				// model.put("success", true);
				model.put("CashBook", CBBL.prepare_CashBookName(CSA, CASH_BOOK_ID));

				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();

				model.put("PaidBy", userDetails.getFirstName());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Add_CashBook", model);
	}

	// This Old Cash Book page add in Who missing
	// the Cash book Entries in last day that will add to direct to
	// CashBookBalance Table
	// No Approval this Already Approval Status Only to save SCashBook
	// Controller
	@RequestMapping(value = "/addOldCashBook", method = RequestMethod.GET)
	public ModelAndView addOldCashBook() {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("CashBook",
					CBBL.prepare_CashBookName_List(CashBookNameService.list_CashBookNameByPermission(userDetails)));

			model.put("PaidBy", userDetails.getFirstName());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Add_OldCashBook", model);
	}

	// This CashBook Save addOldCashBook Controller to Save CashbookBalance
	// This Last day CashBook Entries this Status Already Approval Direct To
	// CashbookBalance
	// This Need More Entries SCashBook Controller to MoreOldCashBook Controller
	@RequestMapping(value = "/SCashBook", method = RequestMethod.POST)
	public ModelAndView sCashBook(@RequestParam("CASH_BOOK_NO") final String CASH_BOOK_NO,
			@ModelAttribute("command") CashBookDto CashBook, @RequestParam("input-file-preview") MultipartFile file,
			HttpServletRequest req, RedirectAttributes redir) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		CashBookSequenceCounter sequenceCounter = null;
		try {
			// Validate Voucher number this there or not
			// Already Exits return main add CashBook page
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<CashBook> validate = CashBookService.Validate_CashBook_value(CashBook.getCASH_VOUCHER_NO(),
					userDetails.getCompany_id());
			if (validate != null && !validate.isEmpty()) {
				String AlreadyEx = "";
				for (CashBook exist : validate) {

					AlreadyEx += exist.getCASH_VOUCHER_NO() + "<a href=\"showCashBook.in?Id=" + exist.getCASHID()
							+ "\" target=\"_blank\">  C-" + exist.getCASH_NUMBER()
							+ "  <i class=\"fa fa-external-link\"></i></a> ,";
				}
				redir.addFlashAttribute("Already", AlreadyEx);
				return new ModelAndView("redirect:/addOldCashBook.in?alreadyExist=true");
			} else {
				boolean ClosedValidate = false, ClosedCurent = false;
				try {
					// get the issues Dto to save issues
					CashBook cash = CBBL.prepareCashBook_Value_MissingEntries(CashBook);
					
					int n = 1;
					// this check Before Begin Opening Balances
					Date openingDate = new Date(cash.getCASH_DATE().getTime() - n * 24 * 3600 * 1000);
					List<CashBookBalance> validateCloseLast = CashBookService.Validate_Last_DayCashBook_CloseOrNot_list(
							openingDate, cash.getCASH_BOOK_ID(), userDetails.getCompany_id());
					if (validateCloseLast != null && !validateCloseLast.isEmpty()) {
						for (CashBookBalance CloseLast : validateCloseLast) {

							if (CloseLast.getCASH_STATUS_ID() == CashBookStatus.CASH_BOOK_STATUS_CLOSED) {
								// check yesterday cash book closed enter today
								// entries
								ClosedValidate = true;
							} else {
								// this else condition only temper
								ClosedValidate = true;
							}
						}
					} else {

						// check yesterday cash book not closed enter create
						// close
						// balance entries
						ClosedValidate = true;
						/** This Balance To Create New Date Balance */
						// get the Cash to save CashBalance
						CashBookBalance cashBalance = CBBL.prepare_CLOSED_LAST_Value(openingDate,
								cash.getCASH_BOOK_NO());
						cashBalance.setCOMPANY_ID(userDetails.getCompany_id());
						cashBalance.setCASH_BOOK_ID(cash.getCASH_BOOK_ID());
						// save to databases
						CashBookService.registerNewCashBookBalance(cashBalance);
					}
					// Checking last day entries close or not
					if (ClosedValidate) {

						try {
							CashBookBalance currentdatevalidate = CashBookService.Validate_Last_DayCashBook_CloseOrNot(
									cash.getCASH_DATE(), cash.getCASH_BOOK_ID(), CashBookStatus.CASH_BOOK_STATUS_CLOSED,
									userDetails.getCompany_id());
							if (currentdatevalidate != null) {
								ClosedCurent = false;
							} else {
								ClosedCurent = true;
							}
							if (ClosedCurent) {

								if (!file.isEmpty()) {

									cash.setCASH_DOCUMENT(true);
								} else {

									cash.setCASH_DOCUMENT(false);
								}
								sequenceCounter = cashBookSequenceService.findNextSequenceNumber(userDetails.getCompany_id());
								if (sequenceCounter == null) {
									return new ModelAndView("redirect:/addCashBook.in?sequenceNotFound=true");
								}
								cash.setCASH_NUMBER(sequenceCounter.getNextVal());
								// save to databases
								CashBookService.registerNewCashBook(cash);
								// cashBookSequenceService.updateNextSequenceCounter(sequenceCounter.getNextVal()
								// + 1, sequenceCounter.getSequence_Id());
								String success = " " + cash.getCASH_NUMBER();
								redir.addFlashAttribute("successID", success);
								// show the driver list in all
								model.put("success", true);

								SaveToCashBookBalance_Update(cash);

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
									CashBookService.save(cashDoc);

									// Note: CashBookDocument ID Update CasdBook
									// Table Column
									CashBookService.Update_CashBook_Document_ID_to_CashBook(cashDoc.get_id(),
											true, cash.getCASHID());
								}

							} else {

								return new ModelAndView("redirect:/addOldCashBook.in?closedCB=true");
							}
						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/addOldCashBook.in?danger=true");
						}
					} else {

						return new ModelAndView("redirect:/addOldCashBook.in?closeLastDayBalance=true");
					}
				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/addOldCashBook.in?danger=true");
				}

			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/addOldCashBook.in?danger=true");
		} finally {
			userDetails = null;
			sequenceCounter = null;
		}
		String CSA = CASH_BOOK_NO.replace(" ", "_");
		model.put("CBN", CSA);
		model.put("CASH_BOOK_ID", CashBook.getCASH_BOOK_ID());
		return new ModelAndView("redirect:/addMoreOldCashBook.in", model);
	}

	// This Add More cash book entries is add more CashBook Details in More time
	// no need to select again again
	// in cash book name that will show cash book name
	// Only Old CashBook Entries More SCashBook Controller to MoreOldCashBook
	// Controller
	@RequestMapping(value = "/addMoreOldCashBook", method = RequestMethod.GET)
	public ModelAndView addMoreOldCashBook(@RequestParam("CBN") final String CashBookName,
			@RequestParam("CASH_BOOK_ID") final Integer CASH_BOOK_ID, final HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Vendor Type List
		try {

			if (CashBookName != null) {
				String CSA = CashBookName.replace("_", " ");
				// model.put("success", true);
				model.put("CashBook", CBBL.prepare_CashBookName(CSA, CASH_BOOK_ID));

				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();

				model.put("PaidBy", userDetails.getFirstName());
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Add_OldCashBook", model);
	}

	// This Missing CashBook Entries is Once CashBook Closed After one entries
	// missing
	// we have to insert the entries in CashBookBalance
	// This Entries are Already Approved CashBook Entries By mistake Missing
	// this missingAddCashBook Controller to saveMissingCashBook Controller sent
	// Return
	@RequestMapping(value = "/missingAddCashBook")
	public ModelAndView missingAddCashBook() {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("CashBook",
					CBBL.prepare_CashBookName_List(CashBookNameService.list_CashBookNameByPermission(userDetails)));

			model.put("PaidBy", userDetails.getFirstName());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("missingAdd_CashBook", model);
	}

	// This Missing CashBook Entries is Once CashBook Closed After one entries
	// missing
	// we have to insert the entries in CashBookBalance
	// This Entries are Already Approved CashBook Entries
	// saveMissingCashBook Controller to save
	@RequestMapping(value = "/saveMissingCashBook", method = RequestMethod.POST)
	public ModelAndView saveMissingCashBook(@ModelAttribute("command") CashBookDto CashBook,
			@RequestParam("input-file-preview") MultipartFile file, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Vendor Type List
		CustomUserDetails userDetails = null;
		CashBookSequenceCounter sequenceCounter = null;
		try {
			// Validate Voucher number this there or not
			// Already Exits return main add CashBook page
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<CashBook> validate = CashBookService.Validate_CashBook_value(CashBook.getCASH_VOUCHER_NO(),
					userDetails.getCompany_id());
			if (validate != null && !validate.isEmpty()) {

				return new ModelAndView("redirect:/addCashBook.in?danger=true");
			} else {
				boolean ClosedValidate = false, ClosedCurent = false;
				// get the issues Dto to save issues
				CashBook cash = CBBL.prepareCashBook_Value_MissingEntries(CashBook);
				sequenceCounter = cashBookSequenceService.findNextSequenceNumber(userDetails.getCompany_id());
				if (sequenceCounter == null) {
					return new ModelAndView("redirect:/addCashBook.in?sequenceNotFound=true");
				}
				cash.setCASH_NUMBER(sequenceCounter.getNextVal());
				cash.setCASH_STATUS_ID(CashBookStatus.CASH_BOOK_STATUS_CLOSED);
				int n = 1;
				// this check Before Begin Opening Balances
				Date openingDate = new Date(cash.getCASH_DATE().getTime() - n * 24 * 3600 * 1000);
				CashBookBalance validateCloseLast = CashBookService.Validate_Last_DayCashBook_CloseOrNot(openingDate,
						cash.getCASH_BOOK_ID(), CashBookStatus.CASH_BOOK_STATUS_CLOSED, userDetails.getCompany_id());
				if (validateCloseLast != null) {
					// check yesterday cash book closed enter today entries
					ClosedValidate = true;
				} else {

					// check yesterday cash book not closed enter create close
					// balance entries
					ClosedValidate = true;
					/** This Balance To Create New Date Balance */
					// get the Cash to save CashBalance
					CashBookBalance cashBalance = CBBL.prepare_CLOSED_LAST_Value(openingDate, cash.getCASH_BOOK_NO());
					// save to databases
					cashBalance.setCOMPANY_ID(userDetails.getCompany_id());
					cashBalance.setCASH_BOOK_ID(cash.getCASH_BOOK_ID());
					CashBookService.registerNewCashBookBalance(cashBalance);
				}
				// Checking last day entries close or not
				if (ClosedValidate) {
					CashBookBalance currentdatevalidate = CashBookService.Validate_Last_DayCashBook_CloseOrNot(
							cash.getCASH_DATE(), cash.getCASH_BOOK_ID(), CashBookStatus.CASH_BOOK_STATUS_CLOSED,
							userDetails.getCompany_id());
					if (currentdatevalidate != null) {
						ClosedCurent = true;
					} else {
						ClosedCurent = false;
					}
					if (ClosedCurent) {

						if (!file.isEmpty()) {

							cash.setCASH_DOCUMENT(true);
						} else {

							cash.setCASH_DOCUMENT(false);
						}
						// save to databases
						CashBookService.registerNewCashBook(cash);
						// cashBookSequenceService.updateNextSequenceCounter(sequenceCounter.getNextVal()
						// + 1, sequenceCounter.getSequence_Id());
						// show the driver list in all
						model.put("success", true);
						// SaveToCashBookBalance_Update(cash);
						switch (cash.getCASH_BOOK_NO()) {
						case CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME:
							/**
							 * cash Balance Already Create in DB To Update old Balance to New
							 */
							switch (cash.getPAYMENT_TYPE_ID()) {
							case CashBookPaymentType.PAYMENT_TYPE_DEBIT:
								// Edit Amount Update Values
								CashBookService.Update_EDIT_DEBIT_CashBalance_AddBalance_Details(
										CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, cash.getCASH_DATE(),
										cash.getCASH_BOOK_ID(), cash.getPAYMENT_TYPE_ID(), userDetails.getCompany_id());

								// Update MainCash Book Amount Opening Balance
								// Amount
								CashBookService.Update_DEBIT_CashBalance_AfterBalance_Details(cash.getCASH_DATE(),
										cash.getCASH_BOOK_ID(), userDetails.getCompany_id());

								break;

							case CashBookPaymentType.PAYMENT_TYPE_CREDIT:

								CashBookService.Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(
										CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, cash.getCASH_DATE(),
										cash.getCASH_BOOK_ID(), cash.getPAYMENT_TYPE_ID(), userDetails.getCompany_id());

								// Update MainCash Book Amount Opening Balance
								// Amount
								CashBookService.Update_CREDIT_CashBalance_AfterBalance_Details(cash.getCASH_DATE(),
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
								CashBookService.Update_EDIT_DEBIT_CashBalance_AddBalance_Details(
										CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, cash.getCASH_DATE(),
										cash.getCASH_BOOK_ID(), cash.getPAYMENT_TYPE_ID(), userDetails.getCompany_id());

								Update_Missing_SUBCashBook_Debit_Credit_Balance(CVoucher, cash.getCASH_DATE(),
										cash.getCASH_AMOUNT(), cash.getPAYMENT_TYPE_ID(), cash.getCASH_BOOK_ID());

								break;

							case CashBookPaymentType.PAYMENT_TYPE_CREDIT:

								// Edit Amount Update Values
								CashBookService.Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(
										CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, cash.getCASH_DATE(),
										cash.getCASH_BOOK_ID(), cash.getPAYMENT_TYPE_ID(), userDetails.getCompany_id());

								Update_Missing_SUBCashBook_Debit_Credit_Balance(CVoucher, cash.getCASH_DATE(),
										cash.getCASH_AMOUNT(), cash.getPAYMENT_TYPE_ID(), cash.getCASH_BOOK_ID());

								break;
							}

							break;
						}

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
							CashBookService.save(cashDoc);
							// Note: CashBookDocument ID Update CasdBook
							// Table Column
							CashBookService.Update_CashBook_Document_ID_to_CashBook(cashDoc.get_id(), true,
									cash.getCASHID());
						}

					} else {
						return new ModelAndView("redirect:/addCashBook.in?closedCB=true");
					}
				} else {
					return new ModelAndView("redirect:/addCashBook.in?closeLastDayBalance=true");
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/addCashBook.in?danger=true");
		}
		return new ModelAndView("redirect:/missingAddCashBook.in", model);
	}

	// This CloseCashBook Controller is Show the page CashBook to Close the page
	// this closeCashBook Controller To Close the cash Book name and Date
	// Search the saveCloseCashBook Controller
	@RequestMapping(value = "/closeCashBook")
	public ModelAndView closeCashBook() {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("CashBook",
					CBBL.prepare_CashBookName_List(CashBookNameService.list_CashBookNameByPermission(userDetails)));

			model.put("PaidBy", userDetails.getFirstName());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("Close_CashBook", model);
	}

	// This saveCloseCashBook Controller is Close the cash Book name and Date
	// Validate the CashBook The Details to the Already Close Are Not Check
	// After Only Close the CashBook Name .
	// if CashBook is Sub Cash Book the Close date to transfer to MAIN_CASH_BOOK
	// the Credit and Debit Amount\
	// Only Credit and Debit transfer Completed
	// Sub Cash Book Close to CashBooKBalance
	@RequestMapping(value = "/saveCloseCashBook", method = RequestMethod.POST)
	public ModelAndView saveCloseCashBook(@ModelAttribute("command") CashBookBalanceDto CashBook,
			HttpServletRequest req, RedirectAttributes redir, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		HashMap<String, Object>     configuration	= null;
		boolean	cashbookStatus	= false;
		
		try {
			Date closingDate = null;
			
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.CASHBOOK_CONFIG);
			cashbookStatus	= (boolean) configuration.getOrDefault(CashBookStatus.CASH_BOOK_STATUS, false);
			
			try {
				// fuel date converted String to date Format
				if (CashBook.getCASH_DATE() != null) {
					java.util.Date date = dateFormat.parse(CashBook.getCASH_DATE());
					java.sql.Date cashDate = new Date(date.getTime());
					closingDate = cashDate;
					
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			// check closing date not null
			if (closingDate != null) {

				// check current date closed or not
				CashBookBalance validateCloseLast = CashBookService.Validate_Last_DayCashBook_CloseOrNot(closingDate,
						CashBook.getCASH_BOOK_ID(), CashBookStatus.CASH_BOOK_STATUS_CLOSED,
						userDetails.getCompany_id());
				// if true closed current cash message already closed
				if (validateCloseLast != null) {
					model.put("alreadyClosed", true);
					return new ModelAndView("redirect:/closeCashBook.in", model);
				} else {
					// not closed cash book check last date book closed are
					// not
					int n = 1;
					// this check Before Begin Opening Balances
					Date openingDate = new Date(closingDate.getTime() - n * 24 * 3600 * 1000);
					CashBookBalance validateLastdays = CashBookService.Validate_Last_DayCashBook_CloseOrNot(openingDate,
							CashBook.getCASH_BOOK_ID(), CashBookStatus.CASH_BOOK_STATUS_CLOSED,
							userDetails.getCompany_id());

					if (validateLastdays != null) {
						// last day cash book closed only add Cash book
						// balance
						CashBookBalance CurrentBalance = CashBookService.Validate_Last_DayCashBook_CloseOrNot(
								closingDate, CashBook.getCASH_BOOK_ID(), CashBookStatus.CASH_BOOK_STATUS_OPEN,
								userDetails.getCompany_id());
						
						if (CurrentBalance != null) {
							
							CashBookName cashBookName = CashBookService.getMainCashBookDetails(
									CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME,
									userDetails.getCompany_id());
							// check Cash book name main book are not
							if (CurrentBalance.getCASH_BOOK_ID() == cashBookName.getNAMEID()) {
								// check sub cash book closed or not
								List<CashBookBalance> current = CashBookService.Validate_Sub_CashBook_Cloesd_or_not(
										CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK, closingDate,
										CashBookStatus.CASH_BOOK_STATUS_OPEN, userDetails.getCompany_id());
								if (current != null && !current.isEmpty()) {
									String subcashbook = "";
									for (CashBookBalance cashBookBalance : current) {
										subcashbook += cashBookBalance.getCASH_BOOK_NAME() + ", ";
									}
									redir.addFlashAttribute("SubCashBook", subcashbook);
									model.put("CloseSubCashBook", true);
									return new ModelAndView("redirect:/closeCashBook.in", model);
								} else {
									// update Balance Main Cash book Name

									String CASH_CLOSED_EMAIL = userDetails.getEmail();
									/* date */
									java.util.Date currentDateUpdate = new java.util.Date();
									Timestamp CASH_CLOSED_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());
									CashBookService.update_CloseDay_Balance(CashBookStatus.CASH_BOOK_STATUS_CLOSED,
											CashBook.getCASH_CLOSED_BY(), CASH_CLOSED_EMAIL, CashBook.getCASH_REMARKS(),
											CASH_CLOSED_DATE, CurrentBalance.getCBBID(), CurrentBalance.getCASH_DAY_BALANCE() + validateLastdays.getCASH_ALL_BALANCE(),userDetails.getCompany_id());
									model.put("success", true);
								}
							} else {

								String CASH_CLOSED_EMAIL = userDetails.getEmail();
								/* date */
								java.util.Date currentDateUpdate = new java.util.Date();
								Timestamp CASH_CLOSED_DATE = new java.sql.Timestamp(currentDateUpdate.getTime());
								CashBookService.update_CloseDay_Balance(CashBookStatus.CASH_BOOK_STATUS_CLOSED,
										CashBook.getCASH_CLOSED_BY(), CASH_CLOSED_EMAIL, CashBook.getCASH_REMARKS(),
										CASH_CLOSED_DATE, CurrentBalance.getCBBID(), CurrentBalance.getCASH_DAY_BALANCE() + validateLastdays.getCASH_ALL_BALANCE(),userDetails.getCompany_id());

								// Add Sub Cash Book Balance Add main Cash
								// Book
								// Amount

								try {
									Double Credit = CurrentBalance.getCASH_CREDIT();

									CashBook cashCredit = CBBL.prepare_SubCashBook_To_MAin_Cashbook_Value(
											CurrentBalance, CashBookPaymentType.PAYMENT_TYPE_CREDIT, round(Credit, 2),
											CashBook.getCASH_CLOSED_BY(), CashBook.getCASH_BOOK_ID(),false);
									
									cashCredit.setCASH_BOOK_ID(cashBookName.getNAMEID());
									
									CashBookSequenceCounter sequenceCounter = cashBookSequenceService
											.findNextSequenceNumber(userDetails.getCompany_id());
									cashCredit.setCASH_NUMBER(sequenceCounter.getNextVal());
									CashBookService.registerNewCashBook(cashCredit);

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
											CashBook.getCASH_CLOSED_BY(), CashBook.getCASH_BOOK_ID(),false);
									
									cashDebit.setCASH_BOOK_ID(cashBookName.getNAMEID());
									
									CashBookSequenceCounter sequenceCounter2 = cashBookSequenceService
											.findNextSequenceNumber(userDetails.getCompany_id());
									cashDebit.setCASH_NUMBER(sequenceCounter2.getNextVal());
									
									CashBookService.registerNewCashBook(cashDebit);

									SaveToCashBookBalance_Update(cashDebit);

								} catch (Exception e) {
									e.printStackTrace();
								}

								model.put("successAddMain", true);
							}
							if(cashbookStatus) {
								CashBookService.updateCashBookStatus(closingDate, CashBook.getCASH_BOOK_ID(), userDetails.getCompany_id());
							} 
							
						} else {
							/** This Balance To Create New Date Balance */
							// get the Cash to save CashBalance
							CashBookBalance cashBalance = CBBL.prepare_CLOSED_DAY_ADD_Value(CashBook);
							// save to databases
							cashBalance.setCOMPANY_ID(userDetails.getCompany_id());
							CashBookService.registerNewCashBookBalance(cashBalance);

						}
					} else {
						model.put("CloseLastday", true);
						return new ModelAndView("redirect:/closeCashBook.in", model);
					}
				}

			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/closeCashBook.in?danger=true");
		}
		return new ModelAndView("redirect:/closeCashBook.in", model);
	}

	// This Controller is Show the Cash Book Id Details
	// Search On Cash Book Id to Show the details
	@RequestMapping(value = "/showCashBook", method = RequestMethod.GET)
	public ModelAndView showCashBook(@RequestParam("Id") final Long CashId, final HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Vendor Type List
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (CashId != null) {
				CashBookDto cashbook = CashBookService.getCashBookByID(CashId, userDetails.getCompany_id());

				model.put("CashBook", CBBL.Show_CashBook_Value(cashbook));

			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("cash Book Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("Show_CashBook", model);
	}

	// This Controller is edit the CashBook Details into
	// CashBook Id the Get the Detail show the Edit page
	@RequestMapping(value = "/editCashBook", method = RequestMethod.GET)
	public ModelAndView editCashBook(@RequestParam("Id") final Long CashId, final HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Vendor Type List
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (CashId != null) {

				CashBookDto editCashBook = CashBookService.getCashBookByID(CashId, userDetails.getCompany_id());

				/*
				 * CashBookBalance validateCloseLast =
				 * CashBookService.Validate_Last_DayCashBook_CloseOrNot(
				 * editCashBook.getCASH_DATE(), editCashBook.getCASH_BOOK_NO(), "CLOSED");
				 */
				/*
				 * if (validateCloseLast != null) { model.put("alreadyClosed", true); return new
				 * ModelAndView("redirect:/CashBook/1.in", model); } else {
				 */
				model.put("CashBook", CBBL.Edit_CashBook_Value(editCashBook));
				/* } */
			} else {
				return new ModelAndView("redirect:/CashBook/1.in?danger=true");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/CashBook/1.in?errorCashbook=true");
		}
		return new ModelAndView("edit_CashBook", model);
	}

	// This Controller is Update To Base On editCashBook Controller to
	// updateCashBook Entries
	// This Once Edit to Update To CashBookBalance Table also Change the Credit
	// and Debit Details Also
	// Once Update Change the cash Book Balance Also
	@RequestMapping(value = "/updateCashBook", method = RequestMethod.POST)
	public ModelAndView updateCashBook(@RequestParam("CASHID") Long CASHID,
			@ModelAttribute("command") CashBookDto CashBook, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Vendor Type List
		try {

			if (CASHID != null) {
				CashBook oldCashBook = CashBookService.getCashBookByID(CASHID);
				
				CashBookHistory	cashBookHistory = CBBL.getCashBookHistoryModel(oldCashBook);
				
				// get the issues Dto to save issues
				CashBook cash = CBBL.prepareCashBook_Value(CashBook);

				try {
					if (oldCashBook.getCASH_APPROVALDATE() != null) {
						// get Current date For to change dateFormar for
						String APdate = dateFormat.format(oldCashBook.getCASH_APPROVALDATE());
						// fuel date converted String to date Format
						if (APdate != null) {
							java.util.Date date = dateFormat.parse(APdate);
							java.sql.Date cashDate = new Date(date.getTime());
							cash.setCASH_APPROVALDATE(cashDate);
						}
					}
					cash.setCASH_DOCUMENT(oldCashBook.isCASH_DOCUMENT());
					cash.setCASH_DOCUMENT_ID(oldCashBook.getCASH_DOCUMENT_ID());
				} catch (Exception e) {
					e.printStackTrace();
				}

				// Approval true Only Edit
				CashBookService.updateCashBook_Amount_Paid(cash.getCASH_AMOUNT(), cash.getCASH_PAID_BY(),
						cash.getCASH_NATURE_PAYMENT_ID(), cash.getCASH_REFERENCENO(), cash.getLASTMODIFIEDBY(),
						cash.getLASTUPDATED_DATE(), CASHID, oldCashBook.getCOMPANY_ID());
				
				CashBookService.runThreadForCashBookHistory(cashBookHistory);

				if (oldCashBook
						.getCASH_APPROVAL_STATUS_ID() == CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED) {

					switch (oldCashBook.getCASH_BOOK_NO()) {

					case CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME:

						switch (cash.getPAYMENT_TYPE_ID()) {
						case CashBookPaymentType.PAYMENT_TYPE_DEBIT:

							// Edit Amount MAIN-CASH-BOOK Total Update DEBIT
							CashBookService.Update_EDIT_DEBIT_CashBalance_AddBalance_Details(
									CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED,
									cash.getCASH_APPROVALDATE(), cash.getCASH_BOOK_ID(), cash.getPAYMENT_TYPE_ID(),
									oldCashBook.getCOMPANY_ID());

							// Update MainCash Book Amount Opening Balance
							// Amount
							Update_MAINCashBookBALANCE_AfterDebit_Credit_Balance(cash.getCASH_APPROVALDATE(),
									cash.getPAYMENT_TYPE_ID(), oldCashBook.getCASH_BOOK_ID(),
									oldCashBook.getCOMPANY_ID());

							break;

						case CashBookPaymentType.PAYMENT_TYPE_CREDIT:

							// Update MainCash Book Amount Opening Balance Amount
							CashBookService.Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(
									CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED,
									cash.getCASH_APPROVALDATE(), cash.getCASH_BOOK_ID(), cash.getPAYMENT_TYPE_ID(),
									oldCashBook.getCOMPANY_ID());

							// Update MainCash Book Amount Opening Balance
							// Amount
							Update_MAINCashBookBALANCE_AfterDebit_Credit_Balance(cash.getCASH_APPROVALDATE(),
									cash.getPAYMENT_TYPE_ID(), oldCashBook.getCASH_BOOK_ID(),
									oldCashBook.getCOMPANY_ID());

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

							Update_SUBCashBook_Debit_Credit_Balance(CVoucher, cash.getCASH_APPROVALDATE(),
									cash.getCASH_AMOUNT(), oldCashBook.getCASH_AMOUNT(), cash.getPAYMENT_TYPE_ID(),
									oldCashBook.getCASH_BOOK_ID(), oldCashBook.getCASHID());

							break;

						case CashBookPaymentType.PAYMENT_TYPE_CREDIT:

							Update_SUBCashBook_Debit_Credit_Balance(CVoucher, cash.getCASH_APPROVALDATE(),
									cash.getCASH_AMOUNT(), oldCashBook.getCASH_AMOUNT(), cash.getPAYMENT_TYPE_ID(),
									oldCashBook.getCASH_BOOK_ID(), oldCashBook.getCASHID());

							break;
						}

						break;
					}
				}
				// show the driver list in all
				model.put("success", true);

			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/addCashBook.in?danger=true");
		}
		return new ModelAndView("redirect:/showCashBook?Id=" + CASHID + "", model);
	}

	// This Controller is Delete the CashBook entries Once Any Error in data
	// Details the details Once Details the Details to the CashBook Entries
	// This Update CashBookBalance to Subtract the Amount to
	// which entries delete to The details
	@RequestMapping(value = "/deleteCashBook", method = RequestMethod.GET)
	public ModelAndView deleteCashBook(@RequestParam("Id") final Long CashId, final HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (CashId != null) {
				CashBook editCashBook = CashBookService.getCashBookByID(CashId);
				CashBookBalance validateCloseLast = CashBookService.Validate_Last_DayCashBook_CloseOrNot(
						editCashBook.getCASH_DATE(), editCashBook.getCASH_BOOK_ID(),
						CashBookStatus.CASH_BOOK_STATUS_CLOSED, userDetails.getCompany_id());
				if (validateCloseLast != null) {
					model.put("alreadyClosed", true);
					return new ModelAndView("redirect:/CashBook/1.in", model);
				} else {

					try {
						// get Current date For to change dateFormar for
						if (editCashBook.getCASH_APPROVALDATE() != null) {
						String APdate = dateFormat.format(editCashBook.getCASH_APPROVALDATE());
						// fuel date converted String to date Format
							java.util.Date date = dateFormat.parse(APdate);
							java.sql.Date cashDate = new Date(date.getTime());
							editCashBook.setCASH_APPROVALDATE(cashDate);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					CashBookService.Delete_CashBook_ID(CashId, userDetails.getCompany_id());

					if (editCashBook
							.getCASH_APPROVAL_STATUS_ID() == (CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED)) {
						/**
						 * cash Balance Already Create in DB To Update old Balance to New
						 */
						if (editCashBook.getPAYMENT_TYPE_ID() == CashBookPaymentType.PAYMENT_TYPE_DEBIT) {

							CashBookService.Update_DELETE_DEBIT_CashBalance_AddBalance_Details(
									CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED,
									editCashBook.getCASH_AMOUNT(), editCashBook.getCASH_APPROVALDATE(),
									editCashBook.getCASH_BOOK_ID(), editCashBook.getPAYMENT_TYPE_ID(),
									userDetails.getCompany_id());
						} else {

							CashBookService.Update_DELETE_CREDIT_CashBalance_SubtrackBalance_Details(
									CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED,
									editCashBook.getCASH_AMOUNT(), editCashBook.getCASH_APPROVALDATE(),
									editCashBook.getCASH_BOOK_ID(), editCashBook.getPAYMENT_TYPE_ID(),
									userDetails.getCompany_id());
						}
					} // Approval true Only Deleted
					
					model.put("success", true);
				}
			} else {
				return new ModelAndView("redirect:/CashBook/1.in?danger=true");
			}
			// show the driver list in all
			model.put("deleteCashbook", true);
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/CashBook/1.in?errorCashbook=true");
		}
		return new ModelAndView("redirect:/CashBook/1.in", model);
	}

	/* Should be Download Driver Reminder document */
	@RequestMapping("/download/CashDocument/{CASHDOCID}")
	public String CashDocument(@PathVariable("CASHDOCID") Long CASH_id, HttpServletResponse response) {

		try {
			if (CASH_id == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				
				org.fleetopgroup.persistence.document.CashBookDocument file = CashBookService.get_CASH_BOOK_Document_Details_CASHID(CASH_id);

				if (file != null) {
					if (file.getCASH_CONTENT() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getCASH_FILENAME() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getCASH_CONTENT_TYPE());
						response.getOutputStream().write(file.getCASH_CONTENT());
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {

		}
		return null;
	}

	/** get Expense Drop down List Trip Sheet */
	@RequestMapping(value = "/getCashExpense", method = RequestMethod.POST)
	public void getCashExpense(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<TripExpense> TripExpenseList = new ArrayList<TripExpense>();
		List<TripExpense> TripList = TripExpenseService.SearchTripExpense_DropDown(term, userDetails.getCompany_id());
		if (TripList != null && !TripList.isEmpty()) {
			for (TripExpense Expense : TripList) {
				TripExpense bean = new TripExpense();
				bean.setExpenseID(Expense.getExpenseID());
				bean.setExpenseName(Expense.getExpenseName());

				TripExpenseList.add(bean);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(TripExpenseList));
	}

	/** get Expense Drop down List Trip Sheet */
	@RequestMapping(value = "/getCashIncome", method = RequestMethod.POST)
	public void getCashIncome(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {

		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<TripIncome> addresses = new ArrayList<TripIncome>();
		List<TripIncome> income = TripIncomeService.SearchTripIncome_DropDown(term, userDetails.getCompany_id());
		if (income != null && !income.isEmpty()) {
			for (TripIncome add : income) {
				TripIncome wadd = new TripIncome();

				wadd.setIncomeID(add.getIncomeID());
				wadd.setIncomeName(add.getIncomeName());
				// System.out.println(add.getVid());
				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));
		response.getWriter().write(gson.toJson(addresses));
	}

	// update balance update cash book to value
	private void SaveToCashBookBalance_Update(CashBook cash) throws Exception {

		CashBookBalance validateBalance = CashBookService.Validate_CashBookBalance_value(cash.getCASH_DATE(),
				cash.getCASH_BOOK_ID(), cash.getCOMPANY_ID());

		try {
			if (validateBalance != null) {
				Long CBBID = validateBalance.getCBBID();
				/**
				 * cash Balance Already Create in DB To Update old Balance to New
				 */
				if (cash.getPAYMENT_TYPE_ID() == CashBookPaymentType.PAYMENT_TYPE_DEBIT) {

					CashBookService.Update_DEBIT_CashBalance_SubtrackBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED,
							dateFormat_SQL.format(cash.getCASH_APPROVALDATE()), cash.getCASH_BOOK_ID(),
							cash.getPAYMENT_TYPE_ID(), CBBID,cash.getCOMPANY_ID());
				} else {

					CashBookService.Update_CREDIT_CashBalance_AddBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED,
							dateFormat_SQL.format(cash.getCASH_APPROVALDATE()), cash.getCASH_BOOK_ID(),
							cash.getPAYMENT_TYPE_ID(), CBBID,cash.getCOMPANY_ID());
				}

			} else {

				/** This Balance To Create New Date Balance */
				// get the Cash to save CashBalance
				CashBookBalance cashBalance = CBBL.prepareCashBookBalance_Value(cash);
				// save to databases
				CashBookService.registerNewCashBookBalance(cashBalance);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void Update_SUBCashBook_Debit_Credit_Balance(String CVoucher, java.util.Date date, Double NEWCASH_AMOUNT,
			Double OLDCASH_AMOUNT, short CASH_PAYMENT_TYPE, Integer CASH_BOOK_ID, Long Old_CASHID) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// Edit Cash Book Balance Update
			CashBookService.Update_Edit_Based_Update_Amount(NEWCASH_AMOUNT, Old_CASHID);
			
			CashBookName cashBookName = CashBookService.getMainCashBookDetails(
					CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME,
					userDetails.getCompany_id());
			// check Sub Cash Book Closed or not
			CashBook validateCloseSubCB = CashBookService.getSub_CashBookToVoucherNumber(CVoucher, date,
					CashBookStatus.CASH_BOOK_STATUS_CLOSED, cashBookName.getNAMEID());

			// Closed
			if (validateCloseSubCB != null) {

				if (NEWCASH_AMOUNT < OLDCASH_AMOUNT) {
					// CREDIT Remove Amount Balance CashBook Closed Main Voucher Number Based
					Double CashbookAmount = OLDCASH_AMOUNT - NEWCASH_AMOUNT;
					CashBookService.updateSubCashBook_DEBIT_Amount_EDit(CashbookAmount, validateCloseSubCB.getCASHID());

				} else if (NEWCASH_AMOUNT > OLDCASH_AMOUNT) {
					// CREDIT Add Balance CashBook CashBook Closed Main Voucher Number Based
					Double CBAmountGrathan = NEWCASH_AMOUNT - OLDCASH_AMOUNT;
					CashBookService.updateSubCashBook_CREDIT_Amount_EDit(CBAmountGrathan,
							validateCloseSubCB.getCASHID());
				}

				
				switch (CASH_PAYMENT_TYPE) {
				case CashBookPaymentType.PAYMENT_TYPE_DEBIT:

					// Edit Amount Update Values After Add Amount Debit Total Value SUB Cash book
					// Details
					CashBookService.Update_EDIT_DEBIT_CashBalance_AddBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date, CASH_BOOK_ID,
							CASH_PAYMENT_TYPE, userDetails.getCompany_id());

					// Edit Amount MAIN-CASH-BOOK Total Update DEBIT
					CashBookService.Update_EDIT_DEBIT_CashBalance_AddBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date,
							validateCloseSubCB.getCASH_BOOK_ID(), CASH_PAYMENT_TYPE, userDetails.getCompany_id());

					// Update MainCash Book Amount Opening Balance Amount
					Update_MAINCashBookBALANCE_AfterDebit_Credit_Balance(date, CASH_PAYMENT_TYPE,
							cashBookName.getNAMEID(), userDetails.getCompany_id());

					break;

				case CashBookPaymentType.PAYMENT_TYPE_CREDIT:

					// Edit Amount Update Values
					CashBookService.Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date, CASH_BOOK_ID,
							CASH_PAYMENT_TYPE, userDetails.getCompany_id());

					// Update MainCash Book Amount Opening Balance Amount
					CashBookService.Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date,
							validateCloseSubCB.getCASH_BOOK_ID(), CASH_PAYMENT_TYPE, userDetails.getCompany_id());

					// Update MainCash Book Amount Opening Balance Amount
					Update_MAINCashBookBALANCE_AfterDebit_Credit_Balance(date, CASH_PAYMENT_TYPE,
							cashBookName.getNAMEID(), userDetails.getCompany_id());

					break;
				}

			} else {
				// Note Not Closed Cash Book in
				switch (CASH_PAYMENT_TYPE) {
				case CashBookPaymentType.PAYMENT_TYPE_DEBIT:

					// Edit Amount Update Values After Add Amount Debit Total Value SUB Cash book
					// Details
					CashBookService.Update_EDIT_DEBIT_CashBalance_AddBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date, CASH_BOOK_ID,
							CASH_PAYMENT_TYPE, userDetails.getCompany_id());

					break;

				case CashBookPaymentType.PAYMENT_TYPE_CREDIT:
					// Edit Amount Update Values
					CashBookService.Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date, CASH_BOOK_ID,
							CASH_PAYMENT_TYPE, userDetails.getCompany_id());

					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void Update_Missing_SUBCashBook_Debit_Credit_Balance(String CVoucher, java.util.Date date,
			Double NEWCASH_AMOUNT, short CASH_PAYMENT_TYPE, Integer Current_CashBook_id) throws Exception {
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			CashBookName cashBookName = CashBookService.getMainCashBookDetails(
					CashBookBalanceStatus.CASHBOOK_BALANCE_STATUS_MAIN_CASH_BOOK_NAME,
					userDetails.getCompany_id());
			// check Sub Cash Book Closed or not
			CashBook validateCloseSubCB = CashBookService.getSub_CashBookToVoucherNumber(CVoucher, date,
					CashBookStatus.CASH_BOOK_STATUS_CLOSED, cashBookName.getNAMEID());
			if (validateCloseSubCB != null) {
				
				switch (CASH_PAYMENT_TYPE) {
				case CashBookPaymentType.PAYMENT_TYPE_DEBIT:

					// missing Cash Book Entries update to add Main cashBook
					// Amount
					CashBookService.updateSubCashBook_CREDIT_Amount_EDit(NEWCASH_AMOUNT,
							validateCloseSubCB.getCASHID());

					// Edit Amount Update Values
					CashBookService.Update_EDIT_DEBIT_CashBalance_AddBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date, cashBookName.getNAMEID(),
							CASH_PAYMENT_TYPE, validateCloseSubCB.getCOMPANY_ID());

					// Update MainCash Book Amount Opening Balance Amount
					CashBookService.Update_DEBIT_CashBalance_AfterBalance_Details(date, Current_CashBook_id,
							validateCloseSubCB.getCOMPANY_ID());
					
					CashBookService.Update_DEBIT_CashBalance_AfterBalance_Details(date, cashBookName.getNAMEID(),
							validateCloseSubCB.getCOMPANY_ID());

					break;

				case CashBookPaymentType.PAYMENT_TYPE_CREDIT:

					CashBookService.updateSubCashBook_CREDIT_Amount_EDit(NEWCASH_AMOUNT,
							validateCloseSubCB.getCASHID());

					// Edit Amount Update Values
					CashBookService.Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date, cashBookName.getNAMEID(),
							CASH_PAYMENT_TYPE, validateCloseSubCB.getCOMPANY_ID());

					// Update MainCash Book Amount Opening Balance Amount
					CashBookService.Update_CREDIT_CashBalance_AfterBalance_Details(date, Current_CashBook_id,
							validateCloseSubCB.getCOMPANY_ID());
					
					CashBookService.Update_CREDIT_CashBalance_AfterBalance_Details(date, cashBookName.getNAMEID(),
							validateCloseSubCB.getCOMPANY_ID());


					break;
				}

			} else {
				
				switch (CASH_PAYMENT_TYPE) {
				case CashBookPaymentType.PAYMENT_TYPE_DEBIT:

				
					CashBookService.Update_EDIT_DEBIT_CashBalance_AddBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date, Current_CashBook_id,
							CASH_PAYMENT_TYPE, userDetails.getCompany_id());
					
					CashBookService.Update_DEBIT_CashBalance_AfterBalance_Details(date, Current_CashBook_id,
							userDetails.getCompany_id());

					break;

				case CashBookPaymentType.PAYMENT_TYPE_CREDIT:
					// Edit Amount Update Values
					
					CashBookService.Update_EDIT_CREDIT_CashBalance_SubtrackBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED, date, Current_CashBook_id,
							CASH_PAYMENT_TYPE, userDetails.getCompany_id());
					
					// Update MainCash Book Amount Opening Balance Amount
					CashBookService.Update_CREDIT_CashBalance_AfterBalance_Details(date, Current_CashBook_id,
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

				CashBookService.Update_DEBIT_CashBalance_AfterBalance_Details(CASH_DATE, CASH_BOOK_ID, companyId);

				break;

			case CashBookPaymentType.PAYMENT_TYPE_CREDIT:

				CashBookService.Update_CREDIT_CashBalance_AfterBalance_Details(CASH_DATE, CASH_BOOK_ID, companyId);

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
}
