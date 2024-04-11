package org.fleetopgroup.web.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.constant.CashBookApprovalStatus;
import org.fleetopgroup.constant.CashBookBalanceStatus;
import org.fleetopgroup.constant.CashBookPaymentType;
import org.fleetopgroup.constant.CashBookStatus;
import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TripDailySheetStatus;
import org.fleetopgroup.persistence.bl.CashBookBL;
import org.fleetopgroup.persistence.bl.DriverBL;
import org.fleetopgroup.persistence.dto.CashBookBalanceDto;
import org.fleetopgroup.persistence.dto.CashBookDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.CashBook;
import org.fleetopgroup.persistence.model.CashBookBalance;
import org.fleetopgroup.persistence.model.CashBookHistory;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.model.CashBookSequenceCounter;
import org.fleetopgroup.persistence.model.DriverSalaryAdvance;
import org.fleetopgroup.persistence.model.DriverSalaryAdvanceSequenceCounter;
import org.fleetopgroup.persistence.model.TripDailyRouteSheet;
import org.fleetopgroup.persistence.serviceImpl.ICashBookNameService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookSequenceService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookService;
import org.fleetopgroup.persistence.serviceImpl.ICashBookVoucherSequenceCounterService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryAdvanceSequenceService;
import org.fleetopgroup.persistence.serviceImpl.IDriverSalaryAdvanceService;
import org.fleetopgroup.persistence.serviceImpl.ITripDailySheetService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class CashBookEntryController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private ICashBookService CashBookService;
	CashBookBL CBBL = new CashBookBL();

	@Autowired
	private ICashBookNameService CashBookNameService;

	@Autowired
	private ICashBookSequenceService cashBookSequenceService;
	
	@Autowired
	private ICompanyConfigurationService companyConfigurationService;
	
	@Autowired
	private IDriverSalaryAdvanceSequenceService driverSalaryAdvanceSequenceService;


	@Autowired private IDriverSalaryAdvanceService				driverSalaryAdvanceService;
	@Autowired private ITripDailySheetService					tripDailySheetService;
	@Autowired private IDriverSalaryAdvanceSequenceService		sequenceService;
	@Autowired private ICashBookVoucherSequenceCounterService	bookVoucherSequenceCounterService;
	
	
	CashBookBL NameBL = new CashBookBL();
	DriverBL DBL = new DriverBL();

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
	
	@RequestMapping(value = "/CashBookEntry/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView CashBook(@PathVariable Integer pageNumber) throws Exception {
		CustomUserDetails userDetails = null;
		List<CashBookName> cashBookPermissionList = null;
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>   configuration		= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);	
			cashBookPermissionList = CashBookNameService.list_CashBookNameByPermission(userDetails);
			if (cashBookPermissionList == null || cashBookPermissionList.size() <= 0) {
				model.put("cashBookPermissionNotFound", true);
				return new ModelAndView("CashBookNewEntry", model);
			} else {

				CashBookName cashBookName = CashBookService.getUserDefaultCashBook(cashBookPermissionList);
				Page<CashBook> page = CashBookService.getDeployment_Page_CashBook(cashBookName.getNAMEID(), pageNumber,
						userDetails);
				int current = page.getNumber() + 1;
				int begin = Math.max(1, current - 5);
				int end = Math.min(begin + 10, page.getTotalPages());

				model.put("deploymentLog", page);
				model.put("beginIndex", begin);
				model.put("endIndex", end);
				model.put("currentIndex", current);

				model.put("CashBookCount", page.getTotalElements());

				List<CashBookDto> pageList = (CashBookService.list_find_CashBook(cashBookName.getNAMEID(), pageNumber,
						userDetails));

				model.put("CashBook", CBBL.prepare_CashBookDto_ListDto(pageList));

				model.put("CashBookName", NameBL.prepare_CashBookName_List(cashBookPermissionList));

				model.put("SelectCashBook", cashBookName.getCASHBOOK_NAME());
				model.put("cashBookPermissionNotFound", false);
				model.put("Configuration", configuration);
			}

		} catch (NullPointerException e) {
			e.printStackTrace();
			return new ModelAndView("NotFound");
		} catch (Exception e) {
			LOGGER.error("cash Book Page:", e);
			e.printStackTrace();
		} finally {
			userDetails = null;
			cashBookPermissionList = null;
		}
		return new ModelAndView("CashBookNewEntry", model);
	}
	
	@RequestMapping(value = "/addCashBookEntry", method = RequestMethod.GET)
	public ModelAndView addCashBook(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
			model.put("CashBook",
					CBBL.prepare_CashBookName_List(CashBookNameService.list_CashBookNameByPermission(userDetails)));
			model.put("configuration", configuration);
			model.put("PaidBy", userDetails.getFirstName());
			model.put("serverDate",DateTimeUtility.getDateBeforeNoOfDays(0));
			
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			model.put("accessToken", uniqueID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("CashBookEntryAdd", model);
	}
	
	@RequestMapping(value = "/saveCashBook", method = RequestMethod.POST)
	public ModelAndView WithApprovalCashBook(@RequestParam("CASH_BOOK_NO") final String CASH_BOOK_NO,
			@ModelAttribute("command") CashBookDto CashBook, @RequestParam("input-file-preview") MultipartFile file,
			HttpServletRequest req, RedirectAttributes redir) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Vendor Type List
		CustomUserDetails userDetails = null;
		CashBookSequenceCounter sequenceCounter = null;
		HashMap<String, Object>   configuration		= null;
		List<CashBook> validate =	null;
		try {
			// Validate Voucher number this there or not
			// Already Exits return main add CashBook page
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
			
			if(!(boolean) configuration.get("isAutoVoucherNumber") || !CashBook.isVoucherType()) {
				validate = CashBookService.Validate_CashBook_value(CashBook.getCASH_VOUCHER_NO(), userDetails.getCompany_id());
			}
			
			if (validate != null && !validate.isEmpty()) {
				String AlreadyEx = "";
				for (CashBook exist : validate) {
					AlreadyEx += exist.getCASH_VOUCHER_NO() + "-" + exist.getCASHID() + " ";
				}
				redir.addFlashAttribute("Already", AlreadyEx);
				return new ModelAndView("redirect:/addCashBookEntry.in?alreadyExist=true");
			} else {
				boolean ClosedValidate = false, ClosedCurent = false;
				try {
					
					// get the issues Dto to save issues
					CashBook cash = CBBL.prepareCashBook_Value(CashBook);
					
					sequenceCounter = cashBookSequenceService.findNextSequenceNumber(userDetails.getCompany_id());
					if (sequenceCounter == null) {
						return new ModelAndView("redirect:/addCashBookEntry.in?sequenceNotFound=true");
					}
					cash.setCASH_NUMBER(sequenceCounter.getNextVal());
					int n = 1;
					// this check Before Begin Opening Balances
					Date openingDate = new Date(cash.getCASH_DATE().getTime() - n * 24 * 3600 * 1000);
					List<CashBookBalance> validateCloseLast = CashBookService.Validate_Last_DayCashBook_CloseOrNot_list(
							openingDate, cash.getCASH_BOOK_ID(), userDetails.getCompany_id());
					if (validateCloseLast != null && !validateCloseLast.isEmpty()) {
						ClosedValidate = true;
					} else {
						ClosedValidate = true;
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
								// save to databases
								if((boolean) configuration.get("isAutoVoucherNumber") && CashBook.isVoucherType()) {
									String 		voucherNumber	=	bookVoucherSequenceCounterService.getCashVoucherNumberAndUpdateNext(cash.getCASH_BOOK_ID(), userDetails.getCompany_id());
									cash.setCASH_VOUCHER_NO(voucherNumber);
								}
								
								if (!file.isEmpty()) {

									cash.setCASH_DOCUMENT(true);
								} else {

									cash.setCASH_DOCUMENT(false);
								}
								
								CashBookService.registerNewCashBook(cash);

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
									//CashBookService.add_CashBook_To_CashBookDocument(cashDoc);
									CashBookService.save(cashDoc);

									// Note: CashBookDocument ID Update CasdBook
									// Table Column
									CashBookService.Update_CashBook_Document_ID_to_CashBook(cashDoc.get_id(),
											true, cash.getCASHID());
								}
								
								if (cash.getDRIVER_ID() != null && !cash.getDRIVER_ID().equals(0)
										&& CashBook.getCASH_NATURE_PAYMENT().equalsIgnoreCase("SALARY ADVANCE")) {

									try {

										DriverSalaryAdvance Advance = DBL.prepare_DriverSalary_Advance_CASHBOOK(cash);

										if (Advance.getADVANCE_AMOUNT() != null && Advance.getADVANCE_AMOUNT() != 0.0) {
											DriverSalaryAdvanceSequenceCounter	advanceCounter	= sequenceService.findNextSequenceNumber(Advance.getCOMPANY_ID());
											Advance.setDSANUMBER(advanceCounter.getNextVal());
											driverSalaryAdvanceService.register_New_DriverSalaryAdvance(Advance);

										} else {
											model.put("advance", true);
										}

									} catch (Exception e) {
										System.err.println("Exception  : "+e);
										model.put("alreadyDriver", true);
									}

								}
								// show the driver list in all
								model.put("success", true);

								return new ModelAndView("redirect:/showCashBookEntry.in?Id=" + cash.getCASHID() + "",
										model);
							} else {

								return new ModelAndView("redirect:/addCashBookEntry.in?closedCB=true");
							}
						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/addCashBookEntry.in?danger=true");
						}
					} else {

						return new ModelAndView("redirect:/addCashBookEntry.in?closeLastDayBalance=true");
					}
				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/addCashBookEntry.in?danger=true");
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/addCashBookEntry.in?danger=true");
		} finally {
			userDetails = null;
			sequenceCounter = null;
		}

	}
	
	@RequestMapping(value = "/showCashBookEntry", method = RequestMethod.GET)
	public ModelAndView showCashBookEntry(@RequestParam("Id") final Long CashId, final HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Vendor Type List
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (CashId != null) {
				HashMap<String, Object>  configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
				CashBookDto cashbook = CashBookService.getCashBookByID(CashId, userDetails.getCompany_id());
				
				model.put("CashBook", CBBL.Show_CashBook_Value(cashbook));
				model.put("configuration", configuration);

			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("cash Book Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("showCashBookEntry", model);
	}
	
	
	@RequestMapping(value = "/approveCashBook", method = RequestMethod.POST)
	public ModelAndView approvalCashBook(@RequestParam("CASHID") final Long CASHID,
			@RequestParam("CASH_BOOK_ID") final Integer CASH_BOOK_ID, @ModelAttribute("command") CashBookDto CashBook,
			HttpServletRequest req, RedirectAttributes redir) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Vendor Type List
		try {

			CashBook cbBook = CashBookService.getCashBookByID(CASHID);
			if (cbBook == null) {
				return new ModelAndView("redirect:/addCashBookEntry.in?alreadyExist=true");
			} else {
				boolean ClosedCurent = false;
				try {

					// get the issues Dto to save issues
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
							String success = " " + cbBook.getCASH_NUMBER();
							redir.addFlashAttribute("successID", success);
							// show the driver list in all
							model.put("success", true);
							
							SaveToCashBookBalance_Update_APPROVAL(cbBook);
							
						} else {

							return new ModelAndView("redirect:/addCashBookEntry.in?closedCB=true");
						}
					} catch (Exception e) {
						e.printStackTrace();
						return new ModelAndView("redirect:/addCashBookEntry.in?danger=true");
					}

				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/addCashBookEntry.in?danger=true");
				}

			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/addCashBookEntry.in?danger=true");
		}
		String CSA = CashBook.getCASH_BOOK_NO().replace(" ", "_");
		model.put("CBN", CSA);
		model.put("CASH_BOOK_ID", CASH_BOOK_ID);
		return new ModelAndView("redirect:/addMoreCashBookEntry.in", model);
	}

	@RequestMapping(value = "/addMoreCashBookEntry", method = RequestMethod.GET)
	public ModelAndView addMoreCashBook(@RequestParam("CBN") final String CashBookName,
			@RequestParam("CASH_BOOK_ID") Integer CASH_BOOK_ID, final HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Vendor Type List
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (CashBookName != null) {
				HashMap<String, Object>  configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
				String CSA = CashBookName.replace("_", " ");
				// model.put("success", true);
				model.put("CashBook", CBBL.prepare_CashBookName(CSA, CASH_BOOK_ID));

				model.put("PaidBy", userDetails.getFirstName());
				model.put("configuration", configuration);
				model.put("serverDate",DateTimeUtility.getDateBeforeNoOfDays(0));
				
				String uniqueID = UUID.randomUUID().toString();
				request.getSession().setAttribute(uniqueID, uniqueID);
				model.put("accessToken", uniqueID);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("Add_CashBookEntry", model);
	}
	
	@RequestMapping(value = "/CashBookEntry/{SelectBook}/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView CashBook(@PathVariable("SelectBook") Integer SelectBook,
			@PathVariable("pageNumber") Integer pageNumber) throws Exception {
		CustomUserDetails customUserDetails = null;
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object>   configuration		= null;
		try {
			customUserDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(customUserDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
			
			bookVoucherSequenceCounterService.getCashVoucherNumber(SelectBook, customUserDetails.getCompany_id());
			
			Page<CashBook> page = CashBookService.getDeployment_Page_CashBook(SelectBook, pageNumber,
					customUserDetails);
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.put("deploymentLog", page);
			model.put("beginIndex", begin);
			model.put("endIndex", end);
			model.put("currentIndex", current);

			
			model.put("CashBookCount", page.getTotalElements());

			List<CashBookDto> pageList = (CashBookService.list_find_CashBook(SelectBook, pageNumber, customUserDetails));

			model.put("CashBook", CBBL.prepare_CashBookDto_ListDto(pageList));

			model.put("CashBookName", NameBL
					.prepare_CashBookName_List(CashBookNameService.list_CashBookNameByPermission(customUserDetails)));

			model.put("SelectCashBook", SelectBook);

			CashBookName CBNAM = CashBookNameService.get_CashBookName(SelectBook);
			model.put("SelectCashBookName", CBNAM.getCASHBOOK_NAME());
			model.put("Configuration", configuration);
		} catch (NullPointerException e) {
			e.printStackTrace();
			return new ModelAndView("NotFound", model);
		} catch (Exception e) {
			LOGGER.error("cash Book Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("cashbookEntry_StatusBook", model);
	}
	
	@RequestMapping(value = "/editCashBookEntry", method = RequestMethod.GET)
	public ModelAndView editCashBook(@RequestParam("Id") final Long CashId, final HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Vendor Type List
		try {
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (CashId != null) {
				
				CashBookDto editCashBook = CashBookService.getCashBookByID(CashId, userDetails.getCompany_id());
				
				model.put("CashBook", CBBL.Edit_CashBook_Value(editCashBook));
				HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
				
				model.put("configuration", configuration);
				
			} else {
				return new ModelAndView("redirect:/CashBookEntry/1.in?danger=true");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/CashBookEntry/1.in?errorCashbook=true");
		}
		return new ModelAndView("edit_CashBookEntry", model);
	}
	
	@RequestMapping(value = "/updateCashBookEntry", method = RequestMethod.POST)
	public ModelAndView updateCashBookEntry(@RequestParam("CASHID") Long CASHID,
			@ModelAttribute("command") CashBookDto CashBook, BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		boolean		allowEditAfterClose		= false;
		try {
			
			if (CASHID != null) {
				CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				HashMap<String, Object>  configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
				CashBook oldCashBook = CashBookService.getCashBookByID(CASHID);
				
				allowEditAfterClose	= (boolean) configuration.get("AllowEditAfterClose");
				
				CashBookBalance	cashBookBalance	= CashBookService.Validate_CashBookBalance_value(oldCashBook.getCASH_DATE(), oldCashBook.getCASH_BOOK_ID(), userDetails.getCompany_id());
				if(cashBookBalance == null || (cashBookBalance.getCASH_STATUS_ID() != CashBookStatus.CASH_BOOK_STATUS_CLOSED) || allowEditAfterClose) {
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
					model.put("success", true);
				}else {
					return new ModelAndView("redirect:/CashBookEntry/1.in?alreadyClosed=true");
				}
				
				

			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/addCashBookEntry.in?danger=true");
		}
		return new ModelAndView("redirect:/showCashBookEntry?Id=" + CASHID + "", model);
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
	
	@RequestMapping(value = "/deleteCashBookEntry", method = RequestMethod.GET)
	public ModelAndView deleteCashBookEntry(@RequestParam("Id") final Long CashId, final HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
					return new ModelAndView("redirect:/CashBookEntry/1.in", model);
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
				return new ModelAndView("redirect:/CashBookEntry/1.in?danger=true");
			}
			// show the driver list in all
			model.put("deleteCashbook", true);
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/CashBookEntry/1.in?errorCashbook=true");
		}
		return new ModelAndView("redirect:/CashBookEntry/1.in", model);
	}
	
	@RequestMapping(value = "/addOldCashBookEntry", method = RequestMethod.GET)
	public ModelAndView addOldCashBookEntry(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			HashMap<String, Object>  configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
			model.put("configuration", configuration);
			model.put("CashBook",
					CBBL.prepare_CashBookName_List(CashBookNameService.list_CashBookNameByPermission(userDetails)));
			model.put("oldCashbookBackDateStart",DateTimeUtility.getDateBeforeNoOfDays((int)configuration.get("oldCashbookBackDateStart")));
			model.put("oldCashbookBackDateEnd",DateTimeUtility.getDateBeforeNoOfDays((int)configuration.get("oldCashbookBackDateEnd")));
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			model.put("accessToken", uniqueID);
			model.put("PaidBy", userDetails.getFirstName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("OldCashBookEntryAdd", model);
	}
	
	@RequestMapping(value = "/SCashBookEntry", method = RequestMethod.POST)
	public ModelAndView SCashBookEntry(@RequestParam("CASH_BOOK_NO") final String CASH_BOOK_NO,
			@ModelAttribute("command") CashBookDto CashBook, @RequestParam("input-file-preview") MultipartFile file,
			HttpServletRequest req, RedirectAttributes redir) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		CashBookSequenceCounter sequenceCounter = null;
		DriverSalaryAdvanceSequenceCounter SalaryAdvanceSequenceCounter = null;
		HashMap<String, Object> 		   configuration				= null;
		List<CashBook> validate =	null;
		try {
			// Validate Voucher number this there or not
			// Already Exits return main add CashBook page
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
			
			if(!(boolean) configuration.get("isAutoVoucherNumber") || !CashBook.isVoucherType()) {
				validate = CashBookService.Validate_CashBook_value(CashBook.getCASH_VOUCHER_NO(), userDetails.getCompany_id());
			}
			
			if (validate != null && !validate.isEmpty()) {
				String AlreadyEx = "";
				for (CashBook exist : validate) {

					AlreadyEx += exist.getCASH_VOUCHER_NO() + "<a href=\"showCashBookEntry.in?Id=" + exist.getCASHID()
							+ "\" target=\"_blank\">  C-" + exist.getCASH_NUMBER()
							+ "  <i class=\"fa fa-external-link\"></i></a> ,";
				}
				redir.addFlashAttribute("Already", AlreadyEx);
				return new ModelAndView("redirect:/addOldCashBookEntry.in?alreadyExist=true");
			} else {
				boolean ClosedValidate = false, ClosedCurent = false;
				try {
					// get the issues Dto to save issues
					CashBook cash = CBBL.prepareCashBook_Value_LastDayEntries(CashBook);
					
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
									return new ModelAndView("redirect:/addCashBookEntry.in?sequenceNotFound=true");
								}
								cash.setCASH_NUMBER(sequenceCounter.getNextVal());
								
								if((boolean) configuration.get("isAutoVoucherNumber") && CashBook.isVoucherType()) {
									String 		voucherNumber	=	bookVoucherSequenceCounterService.getCashVoucherNumberAndUpdateNext(cash.getCASH_BOOK_ID(), userDetails.getCompany_id());
									cash.setCASH_VOUCHER_NO(voucherNumber);
								}
								
								// save to databases
								CashBookService.registerNewCashBook(cash);
								// cashBookSequenceService.updateNextSequenceCounter(sequenceCounter.getNextVal()
								// + 1, sequenceCounter.getSequence_Id());
								String success = " " + cash.getCASH_NUMBER();
								redir.addFlashAttribute("successID", success);
								// show the driver list in all
								if (cash.getDRIVER_ID() != null && !cash.getDRIVER_ID().equals(0)
										&& CashBook.getCASH_NATURE_PAYMENT().equalsIgnoreCase("SALARY ADVANCE")) {

									try {

										DriverSalaryAdvance Advance = DBL.prepare_DriverSalary_Advance_CASHBOOK(cash);
										SalaryAdvanceSequenceCounter = driverSalaryAdvanceSequenceService
												.findNextSequenceNumber(Advance.getCOMPANY_ID());
										if (SalaryAdvanceSequenceCounter == null) {
											model.put("sequenceNotFound", true);
										} else {
											Advance.setDSANUMBER(sequenceCounter.getNextVal());

											if (Advance.getADVANCE_AMOUNT() != 0.0
													&& Advance.getADVANCE_AMOUNT() != null) {
												DriverSalaryAdvanceSequenceCounter	advanceCounter	= sequenceService.findNextSequenceNumber(Advance.getCOMPANY_ID());
												Advance.setDSANUMBER(advanceCounter.getNextVal());
												driverSalaryAdvanceService.register_New_DriverSalaryAdvance(Advance);

											} else {
												model.put("advance", true);
											}
										}

									} catch (Exception e) {
										model.put("alreadyDriver", true);
									}

								}
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

								return new ModelAndView("redirect:/addOldCashBookEntry.in?closedCB=true");
							}
						} catch (Exception e) {
							e.printStackTrace();
							return new ModelAndView("redirect:/addOldCashBookEntry.in?danger=true");
						}
					} else {

						return new ModelAndView("redirect:/addOldCashBookEntry.in?closeLastDayBalance=true");
					}
				} catch (Exception e) {
					e.printStackTrace();
					return new ModelAndView("redirect:/addOldCashBookEntry.in?danger=true");
				}

			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/addOldCashBookEntry.in?danger=true");
		} finally {
			userDetails = null;
			sequenceCounter = null;
		}
		String CSA = CASH_BOOK_NO.replace(" ", "_");
		model.put("CBN", CSA);
		model.put("CASH_BOOK_ID", CashBook.getCASH_BOOK_ID());
		return new ModelAndView("redirect:/addOldCashBookEntry.in", model);
	}

	public void SaveToCashBookBalance_Update(CashBook cash) throws Exception {

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
	
	@RequestMapping(value = "/addMoreOldCashBookEntry", method = RequestMethod.GET)
	public ModelAndView addMoreOldCashBookEntry(@RequestParam("CBN") final String CashBookName,
			@RequestParam("CASH_BOOK_ID") final Integer CASH_BOOK_ID, final HttpServletRequest request,
			HttpServletResponse response) throws Exception {
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
				HashMap<String, Object>  configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
				model.put("configuration", configuration);
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return new ModelAndView("OldCashBookEntryAdd", model);
	}
	
	@RequestMapping(value = "/missingAddCashBookEntry")
	public ModelAndView missingAddCashBook(HttpServletRequest request) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("CashBook",
					CBBL.prepare_CashBookName_List(CashBookNameService.list_CashBookNameByPermission(userDetails)));
			model.put("PaidBy", userDetails.getFirstName());
			HashMap<String, Object>  configuration		= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.CASHBOOK_CONFIG);
			model.put("configuration", configuration);
			model.put("missingCashbookBackDateStart",DateTimeUtility.getDateBeforeNoOfDays((int)configuration.get("missingCashbookBackDateStart")));
			model.put("missingCashbookBackDateEnd",DateTimeUtility.getDateBeforeNoOfDays((int)configuration.get("missingCashbookBackDateEnd")));
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			model.put("accessToken", uniqueID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("missingAddCashBookEntry", model);
	}
	
	@RequestMapping(value = "/saveMissingCashBookEntry", method = RequestMethod.POST)
	public ModelAndView saveMissingCashBookEntry(@ModelAttribute("command") CashBookDto CashBook,
			@RequestParam("input-file-preview") MultipartFile file, HttpServletRequest req, RedirectAttributes redir) throws Exception {
		
		Map<String, Object> model = new HashMap<String, Object>();
		// show the Vendor Type List
		ValueObject 					object		  		= null;
		
		try {
			object = CashBookService.saveCashBookMissingEntry(CashBook, file, false);
			if(object != null) {
				if(object.getBoolean("danger")) {
					model.put("danger", true);
					return new ModelAndView("redirect:/addCashBookEntry.in?danger=true");
				}
				else if(object.getBoolean("sequenceNotFound")) {
					model.put("sequenceNotFound", true);
					return new ModelAndView("redirect:/addCashBookEntry.in?sequenceNotFound=true");
				}
				else if(object.getBoolean("closedCB")) {
					model.put("closedCB", true);
					return new ModelAndView("redirect:/addCashBookEntry.in?closedCB=true");
				}
				else if(object.getBoolean("success")) {
					model.put("success", true);
				}
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/addCashBookEntry.in?danger=true");
		} finally {
			
		}
		return new ModelAndView("redirect:/missingAddCashBookEntry.in", model);
	}

	
	@RequestMapping(value = "/closeCashBookEntry")
	public ModelAndView closeCashBookEntry(HttpServletRequest request) throws Exception {
		Map<String, Object> 		model 			= new HashMap<String, Object>();
		CustomUserDetails 			userDetails 	= null;
		HashMap<String, Object> 	configuration	= null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration 	 	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(),PropertyFileConstants.CASHBOOK_CONFIG);
			model.put("CashBook", CBBL.prepare_CashBookName_List(CashBookNameService.list_CashBookNameByPermission(userDetails)));

			model.put("PaidBy", userDetails.getFirstName());
			String uniqueID = UUID.randomUUID().toString();
			request.getSession().setAttribute(uniqueID, uniqueID);
			model.put("accessToken", uniqueID);
			model.put("closeBeforeDate",DateTimeUtility.getDateBeforeNoOfDays((int)configuration.get("closeDatePayment")));
			model.put("serverDate",DateTimeUtility.getDateBeforeNoOfDays(0));
			model.put("configuration", configuration);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("Close_CashBookEntry", model);
	}
	
	@RequestMapping(value = "/saveCloseCashBookEntry", method = RequestMethod.POST)
	public ModelAndView saveCloseCashBookEntry(@ModelAttribute("command") CashBookBalanceDto CashBook,
			HttpServletRequest req, RedirectAttributes redir, BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails 			userDetails 	= null;
		HashMap<String, Object>     configuration	= null;
		boolean						cashbookStatus	= false;
		ValueObject  object = null;
		
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			object	= new ValueObject();
			CashBook.setCOMPANY_ID(userDetails.getCompany_id());
			CashBook.setCASH_CLOSED_EMAIL(userDetails.getEmail());
			object = CashBookService.saveCloseCashbookBalance(CashBook,false);

			
			if(object.getBoolean("alreadyClosed")){
				model.put("alreadyClosed", true);
				return new ModelAndView("redirect:/closeCashBookEntry.in", model);
			}
			else if(object.getBoolean("CloseSubCashBook")) {
				redir.addFlashAttribute("SubCashBook", object.get("SubCashBook"));
				model.put("CloseSubCashBook", true);
				return new ModelAndView("redirect:/closeCashBookEntry.in", model);
			}
			else if(object.getBoolean("CloseLastday")) {
				model.put("CloseLastday", true);
				return new ModelAndView("redirect:/closeCashBookEntry.in", model);
				
			}else if(object.getBoolean("tripdayNotClosed")) {
				return new ModelAndView("redirect:/closeCashBookEntry.in?tripDayNotClosed=true");
			}
			else if(object.getBoolean("successAddMain")) {
				model.put("successAddMain", true);
			}
			else if(object.getBoolean("success")) {
				model.put("success", true);
			}
		}catch (Exception e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/closeCashBookEntry.in?danger=true");
		}
		return new ModelAndView("redirect:/closeCashBookEntry.in", model);
	}

	@RequestMapping(value = "/searchCashBookEntryShow", method = RequestMethod.POST)
	public ModelAndView searchCashBookEntryShow(@RequestParam("Search") final Long CashId, final HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (CashId != null) {
				CashBookDto cashbook = CashBookService.getCashBookIdByNumber(CashId, userDetails);
				
				if(cashbook != null) {
					return new ModelAndView("redirect:/showCashBookEntry?Id=" + cashbook.getCASHID() + "", model);
				}else {
					model.put("NotFound", true);
					return new ModelAndView("redirect:/CashBookEntry/1.in", model);
				}
			}
		} catch (NullPointerException e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/CashBookEntry/1.in", model);
		} catch (Exception e) {
			model.put("NotFound", true);
			return new ModelAndView("redirect:/CashBookEntry/1.in", model);
		} finally {
			userDetails = null;
		}
		return new ModelAndView("Show_CashBook", model);
	}
	
	@RequestMapping(value = "/searchCashBookEntry", method = RequestMethod.POST)
	public ModelAndView searchCashBookEntry(@RequestParam("Search") final String CashBookName,
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

		return new ModelAndView("CashBookEntry_Report", model);
	}
	
	private void SaveToCashBookBalance_Update_APPROVAL(CashBook cash) throws Exception {

		CashBookBalance validateBalance = CashBookService.Validate_CashBookBalance_value(cash.getCASH_APPROVALDATE(),
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
							cash.getPAYMENT_TYPE_ID(), CBBID, cash.getCOMPANY_ID());
				} else {

					CashBookService.Update_CREDIT_CashBalance_AddBalance_Details(
							CashBookApprovalStatus.CASH_BOOK_APPROVAL_STATUS_APPROVED,
							dateFormat_SQL.format(cash.getCASH_APPROVALDATE()), cash.getCASH_BOOK_ID(),
							cash.getPAYMENT_TYPE_ID(), CBBID, cash.getCOMPANY_ID());
				}

			} else {

				/** This Balance To Create New Date Balance */
				// get the Cash to save CashBalance
				CashBookBalance cashBalance = CBBL.prepareCashBookBalance_Value_cashBookApproval(cash);
				// save to databases
				CashBookService.registerNewCashBookBalance(cashBalance);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/getCashBookVoucherNumber", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getCashBookVoucherNumber(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject 		object 					= null;
		CustomUserDetails	userDetails				= null;
		try {
			object 				= new ValueObject(allRequestParams);
			
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			object.put("voucherNumber", bookVoucherSequenceCounterService.getCashVoucherNumber(object.getInt("cashBookId"), userDetails.getCompany_id()));
			
			return object.getHtData();
		} catch (Exception e) {
			throw e;
		} 
	}

}
