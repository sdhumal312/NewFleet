package org.fleetopgroup.rest.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.BankMasterBL;
import org.fleetopgroup.persistence.dto.BankAccountDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.BankAccount;
import org.fleetopgroup.persistence.model.BankMaster;
import org.fleetopgroup.persistence.model.CashBookName;
import org.fleetopgroup.persistence.serviceImpl.IBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class BankRestController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired private IBankService		bankService;
	
	BankMasterBL BL = new BankMasterBL();
	
	
	@RequestMapping(value = "/addBank", method = RequestMethod.GET)
	public ModelAndView addCashBookName(@ModelAttribute("command") CashBookName TripExpenseBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			model.put("bankList", bankService.getBankList());
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Bank Controller Service Type Page:", e);

		}
		return new ModelAndView("addBank", model);
	}
	
	/* save the vehicle Status */
	@RequestMapping(value = "/saveBank", method = RequestMethod.POST)
	public ModelAndView saveCashBookName(@ModelAttribute("command") BankMaster bankMasterDto,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			BankMaster	bankMaster	= BL.getBankMasterDto(bankMasterDto);
			List<BankMaster>  bankList	=  bankService.validateBankName(bankMaster.getBankName());
			if(bankList == null || bankList.isEmpty()) {
				bankService.save(bankMaster);
			}else {
				model.put("already", true);
				return new ModelAndView("redirect:/addBank.in", model);
			}
			
		} catch (Exception e) {
			model.put("already", true);
			LOGGER.error("saveBank Service Type Page:", e);
			return new ModelAndView("redirect:/addBank.in", model);
		}
		return new ModelAndView("redirect:/addBank.in", model);
	}

	
	@RequestMapping(value = "/addBankAccount/{pageNumber}", method = RequestMethod.GET)
	public ModelAndView addBankAccount(@PathVariable Integer pageNumber, Model model, final HttpServletRequest request) {
		
		CustomUserDetails userDetails = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Page<BankAccount> page = bankService.getDeployment_Page_BankAccoutn(pageNumber, userDetails.getCompany_id());
			int current = page.getNumber() + 1;
			int begin = Math.max(1, current - 5);
			int end = Math.min(begin + 10, page.getTotalPages());

			model.addAttribute("deploymentLog", page);
			model.addAttribute("beginIndex", begin);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", current);
			model.addAttribute("vehicleTypeCount", page.getTotalElements());
			List<BankAccountDto> pageList = (bankService.getBankAccountList(pageNumber, userDetails.getCompany_id()));

			model.addAttribute("bankAccount", pageList);
			model.addAttribute("bankList", bankService.getBankList());

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("trip addTripRoute Page:", e);
			e.printStackTrace();
		}
	
		return new ModelAndView("addBankAccount");
	}
	
	/* save the vehicle Status */
	@RequestMapping(value = "/saveBankaccount", method = RequestMethod.POST)
	public ModelAndView saveBankaccount(@ModelAttribute("command") BankAccount bankAccount,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		CustomUserDetails	userDetails		= null;
		try {
			userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<BankAccount> 	validate 	= bankService.validateBankAccount(bankAccount.getName(),bankAccount.getBankId() ,userDetails.getCompany_id());
			if (validate != null && !validate.isEmpty()) {

				model.put("already", true);
				return new ModelAndView("redirect:/addBankAccount/1.in", model);
			} else {
				
				bankAccount.setCompanyId(userDetails.getCompany_id());
				bankAccount.setCreatedById(userDetails.getId());
				bankAccount.setLastModifiedById(userDetails.getId());
				bankAccount.setCreatedOn(new Timestamp(System.currentTimeMillis()));
				bankAccount.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
				
				bankService.save(bankAccount);
				
				model.put("saveAccount", true);
			}

		} catch (Exception e) {
			System.err.println("Exception "+e);
			model.put("already", true);
			LOGGER.error("Bank Account Service Type Page:", e);
			return new ModelAndView("redirect:/addBankAccount/1.in", model);
		}
		return new ModelAndView("redirect:/addBankAccount/1.in", model);
	}
	
	@RequestMapping(value = "/deleteBankAccount", method = RequestMethod.GET)
	public ModelAndView deleteBankAccount(@ModelAttribute("command") BankAccount TripExpenseBean,
			final HttpServletRequest request) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			bankService.deleteBankAccount(TripExpenseBean.getBankAccountId());
			
			model.put("delete", true);
		} catch (Exception e) {
			System.err.println("Exception "+e);
			model.put("already", true);
			LOGGER.error("TripExpense Service Type Page:", e);
			return new ModelAndView("redirect:/addBankAccount/1.in", model);
		}
		return new ModelAndView("redirect:/addBankAccount/1.in", model);
	}
	
	@PostMapping(value = "/getBankAccountList")
	public void getBankAccountList( @RequestParam("term") final String term, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(bankService.getBankAccountListForDropDown(term, userDetails.getCompany_id())));
	}

	@GetMapping(value = "/getAllBankNameist")
	public void getAllBankNameist(HttpServletResponse response) throws Exception {
		try {
			List<BankMaster> bankList 			= bankService.getBankList();
			if (bankList == null) 
				bankList = new ArrayList<>();
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(bankList));
		} catch (Exception e) {
			LOGGER.error("e"+e);
		}
	}
	@RequestMapping(value = "/getBankAccountListByBankId", method = RequestMethod.POST)
	public void getBankAccountListByBankId(Map<String, Object> map, @RequestParam("term") final String term,@RequestParam("text") final String bankId,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<BankAccountDto> bankAccountDto = new ArrayList<BankAccountDto>();
		 List<BankAccountDto> list = bankService.getBankAccountListForDropDownByBankId(bankId, userDetails.getCompany_id());
		 if (list != null && !list.isEmpty()) {
			for (BankAccountDto Route : list) {

				BankAccountDto Dto = new BankAccountDto();
				Dto.setBankAccountId(Route.getBankAccountId());
				Dto.setBankId(Route.getBankId());
				Dto.setBankName(Route.getBankName());
				Dto.setName(Route.getName());
				Dto.setAccountNumber(Route.getAccountNumber());
				Dto.setIFSCCode(Route.getIFSCCode());
				Dto.setDescription(Route.getDescription());
				bankAccountDto.add(Dto);
			}
		}
		Gson gson = new Gson();
		response.getWriter().write(gson.toJson(bankAccountDto));
	}
	
}
