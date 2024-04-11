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

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.BranchBL;
import org.fleetopgroup.persistence.dto.BranchDocumentDto;
import org.fleetopgroup.persistence.dto.BranchDto;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.Branch;
import org.fleetopgroup.persistence.model.BranchDocument;
import org.fleetopgroup.persistence.model.BranchHistory;
import org.fleetopgroup.persistence.model.BranchPhoto;
import org.fleetopgroup.persistence.serviceImpl.IBranchHistoryService;
import org.fleetopgroup.persistence.serviceImpl.IBranchService;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

/**
 * @author fleetop
 *
 */
@Controller
public class BranchController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IBranchService branchService;

	@Autowired
	private IBranchHistoryService branchHistoryService;
	BranchBL bbl = new BranchBL();
	
	@Autowired 
	private ICompanyConfigurationService companyConfigurationService;

	/*@Autowired
	private ICacheService cacheService;*/

	ByteImageConvert ByteImageConvert = new ByteImageConvert();

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	@RequestMapping(value = "/searchBranch", method = RequestMethod.POST)
	public ModelAndView searchDriver(@ModelAttribute("command") BranchDto branchDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			model.put("branch",
					branchService.SearchBranchList(branchDto.getBranch_name(), userDetails.getCompany_id()));

		} catch (Exception e) {
			LOGGER.error("Driver Page:", e);
		}
		return new ModelAndView("newBranch", model);
	}

	@RequestMapping(value = "/newBranch", method = RequestMethod.GET)
	public ModelAndView newbranch(@ModelAttribute("command") Branch CompanyDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails currentUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (currentUser != null) {

				model.put("TotalBranch", branchService.countTotalBranch(currentUser.getCompany_id()));
				model.put("ActiveBranch", branchService.countActiveBranch(currentUser.getCompany_id()));
				model.put("branch", branchService.SearchBranchLisrCompanyID(currentUser.getCompany_id()));
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Branch Page:", e);
		}
		return new ModelAndView("newBranch", model);
	}

	@RequestMapping(value = "/addBranch", method = RequestMethod.GET)
	public ModelAndView addBranch(@ModelAttribute("command") Branch CompanyDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration	          = null;
		CustomUserDetails 				userDetails 	          = null;
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			model.put("companyId", userDetails.getCompany_id());
			model.put("configuration", configuration);
		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Branch Page:", e);
		}
		return new ModelAndView("addBranch", model);
	}

	@RequestMapping(value = "/saveBranch", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveBranch(@ModelAttribute("command") BranchDto branchDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Branch GET_branch = bbl.prepareBranchModel(branchDto);

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
            
			GET_branch.setCompany_id(userDetails.getCompany_id());
			GET_branch.setCreatedById(userDetails.getId());
			GET_branch.setLastModifiedById(userDetails.getId());
			List<Branch> validate = branchService.validateBranchName(branchDto.getBranch_name(), branchDto.getCompany_id());
			if (validate.isEmpty() || validate == null   ) {

				branchService.registerNewBranch(GET_branch);
				model.put("saveBranch", true);
				/**
				 * this line is for updating Branches List Cache
				 */
				// cacheService.refreshCacheOfBranch(userDetails.getCompany_id());
			} else {
				model.put("already", true);
				return new ModelAndView("redirect:/newBranch.html", model);
			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("alreadyBranch", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/newBranch.html", model);
		}
		return new ModelAndView("redirect:/newBranch.html", model);
	}

	@RequestMapping(value = "/showBranch", method = RequestMethod.GET)
	public ModelAndView showbranch(@ModelAttribute("command") Branch CompanyDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			model.put("branch", bbl.GetBranchDetailsto(branchService.getBranchByID(CompanyDto.getBranch_id(),userDetails.getCompany_id())));

			model.put("branchDocument", branchService.getBranchDocumentCount(CompanyDto.getBranch_id()));
			model.put("branchPhoto", branchService.getPhotoCount(CompanyDto.getBranch_id()));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Branch Page:", e);
		}
		return new ModelAndView("showBranch", model);
	}

	@RequestMapping(value = "/editBranch", method = RequestMethod.GET)
	public ModelAndView editBranch(@ModelAttribute("command") Branch CompanyDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		HashMap<String, Object> 		configuration	          = null;
		CustomUserDetails 				userDetails 	          = null;
		try {
			userDetails 	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration	= companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			
			model.put("configuration", configuration);
			model.put("branch", bbl.GetBranchDetailsto(branchService.getBranchByID(CompanyDto.getBranch_id(),userDetails.getCompany_id())));

		} catch (NullPointerException e1) {
			e1.printStackTrace();
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Branch Page:", e);
			e.printStackTrace();
		}
		return new ModelAndView("editBranch", model);
	}

	@RequestMapping(value = "/updateBranch", method = RequestMethod.POST)
	public @ResponseBody ModelAndView updateBranch(@ModelAttribute("command") BranchDto branchDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Branch GET_branch = bbl.UpdateprepareBranchModel(branchDto);

			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			GET_branch.setCompany_id(userDetails.getCompany_id());
			GET_branch.setLastModifiedById(userDetails.getId());

			BranchHistory  branchHistory	= bbl.createBranchHistoryDto(branchService.getBranchByID(GET_branch.getBranch_id(),userDetails.getCompany_id()));

			branchService.updateBranch(GET_branch);
			
			branchHistoryService.registerNewBranchHistory(branchHistory);
			
			model.put("saveBranch", true);
			/**
			 * this line is for updating Branches List Cache
			 */
		//	cacheService.refreshCacheOfBranch(userDetails.getCompany_id());

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("alreadyBranch", true);
			LOGGER.error("Company Create Page:", e);
			return new ModelAndView("redirect:/newBranch.html", model);
		}
		return new ModelAndView("redirect:/newBranch.html", model);
	}

	@RequestMapping(value = "/deleteBranch", method = RequestMethod.GET)
	public ModelAndView deleteBranch(@ModelAttribute("command") Branch CompanyDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			BranchHistory  branchHistory	= bbl.createBranchHistoryDto(branchService.getBranchByID(CompanyDto.getBranch_id(),userDetails.getCompany_id()));
			
			branchService.deleteBranch_ID(CompanyDto.getBranch_id(), userDetails.getCompany_id());
			branchHistoryService.registerNewBranchHistory(branchHistory);

			model.put("deletebranch", true);
			/**
			 * this line is for updating Branches List Cache
			 */
			//cacheService.refreshCacheOfBranch(userDetails.getCompany_id());
		} catch (NullPointerException e) {
			LOGGER.error("Branch Page:", e);
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			LOGGER.error("Branch Page:", e);
		}
		return new ModelAndView("redirect:/newBranch.html", model);
	}

	/* Branch Document */
	// this show Branch Document Details and All Information

	/*
	 *******************************************************************************************************************************************************************	*/
	/* Branch Document */
	/*
	 *******************************************************************************************************************************************************************	*/
	@RequestMapping("/ShowbranchDocument")
	public ModelAndView ShowbranchDocument(@ModelAttribute("command") BranchDocument branchDocument,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			model.put("branch", bbl.GetBranchDetailsto(branchService.getBranchByID(branchDocument.getBranch_id(),userDetails.getCompany_id())));

			// List of the branchDocument
			model.put("branchDocument", bbl.prepareListbranchDocument(
					branchService.listBranchDocument(branchDocument.getBranch_id(), userDetails.getCompany_id())));

			model.put("DocumentCount", branchService.getBranchDocumentCount(branchDocument.getBranch_id()));
			model.put("PhotoCount", branchService.getPhotoCount(branchDocument.getBranch_id()));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showDocument", model);
	}

	/* save branch Document */
	@RequestMapping(value = "/uploadbranchDocument")
	public String uploadbranchDocument() {
		return "uploadbranchDocument";
	}

	@RequestMapping(value = "/uploadBranchDocument", method = RequestMethod.POST)
	public @ResponseBody ModelAndView saveBranchDocumentUpload(
			@ModelAttribute("command") BranchDocumentDto branchDocument,
			@RequestParam("input-file-preview") MultipartFile file) {

		Map<String, Object> model = new HashMap<String, Object>();

		org.fleetopgroup.persistence.document.BranchDocument saveDB = new org.fleetopgroup.persistence.document.BranchDocument();
		if (!file.isEmpty()) {

			String[] From_TO_Array = null;
			try {
				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				saveDB.setBranch_id(branchDocument.getBranch_id());
				saveDB.setBranch_documentname(branchDocument.getBranch_documentname());
				saveDB.setCompanyId(userDetails.getCompany_id());

				String From_TO = branchDocument.getBranch_docFrom();
				From_TO_Array = From_TO.split("  to  ");
				try {
					java.util.Date date = dateFormat.parse(From_TO_Array[0]);
					java.sql.Date fromDate = new Date(date.getTime());
					saveDB.setBranch_docFrom(fromDate);
					java.util.Date dateTo = dateFormat.parse(From_TO_Array[1]);
					java.sql.Date toDate = new Date(dateTo.getTime());
					saveDB.setBranch_docTo(toDate);
				} catch (Exception e) {

				}
				byte[] bytes = file.getBytes();
				saveDB.setBranch_filename(file.getOriginalFilename());
				saveDB.setBranch_content(bytes);
				saveDB.setBranch_contentType(file.getContentType());

				java.util.Date currentDate = new java.util.Date();
				Timestamp toDateUpdte = new java.sql.Timestamp(currentDate.getTime());

				saveDB.setBranch_uploaddate(toDateUpdte);

			} catch (IOException e) {

			}

			branchService.saveBranchDocument(saveDB);
			// this message alert of show method
			model.put("addbranchDocument", true);

			return new ModelAndView("redirect:/ShowbranchDocument.in?branch_id=" + branchDocument.getBranch_id() + "",
					model);
		} else {
			// messages
			model.put("emptyDocument", true);
			return new ModelAndView("redirect:/ShowbranchDocument.in?branch_id=" + branchDocument.getBranch_id() + "",
					model);

		}

	}

	/* Should be Download branch Document */
	@RequestMapping("/download/branchDocument/{branch_documentid}")
	public String download(@PathVariable("branch_documentid") Integer branch_documentid, HttpServletResponse response) {
		try {
			if (branch_documentid == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				org.fleetopgroup.persistence.document.BranchDocument file = branchService.getBranchDocuemnt(branch_documentid, userDetails.getCompany_id());
				if (file != null) {
					if (file.getBranch_content() != null) {
						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getBranch_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getBranch_contentType());
						response.getOutputStream().write(file.getBranch_content());
						out.flush();
						out.close();

					}
				} else {
					return "redirect:/NotFound.in";
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (IOException e) {

		}
		return null;
	}

	/* Delete the branch Document */
	@SuppressWarnings("null")
	@RequestMapping("/deleteBranchDocument")
	public ModelAndView deleteBranchDocument(@ModelAttribute("command") BranchDocumentDto branchDocumentDto,
			BindingResult result) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		if (branchDocumentDto != null) {
			// delete method
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			branchService.deleteBranchDocument(branchDocumentDto.getBranch_documentid(), userDetails.getCompany_id());
			// this message alert of show method
			model.put("deletebranchDocument", true);
			return new ModelAndView(
					"redirect:/ShowbranchDocument.in?branch_id=" + branchDocumentDto.getBranch_id() + "", model);
		} else {
			// messages
			model.put("emptyDocument", true);
			return new ModelAndView(
					"redirect:/ShowbranchDocument.in?branch_id=" + branchDocumentDto.getBranch_id() + "", model);
		}
	}

	/* add branch Document */
	/*
	 * @RequestMapping("/addbranchDocument") public ModelAndView
	 * addbranchDocument(@ModelAttribute("command") BranchDocument branchDocument,
	 * BindingResult result) { Map<String, Object> model = new HashMap<String,
	 * Object>();
	 * 
	 * try { model.put("branch",
	 * bbl.GetBranchDetailsto(branchService.getBranchByID(branchDocument.
	 * getBranch_id())));
	 * 
	 * } catch (NullPointerException e) { return new
	 * ModelAndView("redirect:/NotFound.in"); } catch (Exception e) { 
	 * handle exception } return new ModelAndView("addbranchDocument", model); }
	 */

	/*
	 *******************************************************************************************************************************************************************	*/
	/* branch Photo */
	/*
	 *******************************************************************************************************************************************************************	*/
	/* branch Photo */
	@RequestMapping("/ShowBranchPhoto")
	public ModelAndView ShowBranchphoto(@ModelAttribute("command") BranchPhoto branchPhoto, BranchDto branchDto,
			BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			model.put("branch", bbl.GetBranchDetailsto(branchService.getBranchByID(branchPhoto.getBranch_id(),userDetails.getCompany_id())));
			// List of the branchDocument
			model.put("branchPhoto", bbl.prepareListbranchPhoto(
					branchService.listBranchPhoto(branchPhoto.getBranch_id(), userDetails.getCompany_id())));

			model.put("DocumentCount", branchService.getBranchDocumentCount(branchPhoto.getBranch_id()));
			model.put("PhotoCount", branchService.getPhotoCount(branchPhoto.getBranch_id()));

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("showBranchPhoto", model);
	}

	/* save branch Document */
	@RequestMapping(value = "/uploadBranchPhoto", method = RequestMethod.POST)
	public @ResponseBody ModelAndView savebranchPhoto(@ModelAttribute("command") BranchPhoto branchPhoto,
			@RequestParam("file") MultipartFile file) {
		Map<String, Object> model = new HashMap<String, Object>();

		org.fleetopgroup.persistence.document.BranchPhoto saveDB = new org.fleetopgroup.persistence.document.BranchPhoto();
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			if (!file.isEmpty()) {

				saveDB.setBranch_id(branchPhoto.getBranch_id());
				saveDB.setBranch_photoname(branchPhoto.getBranch_photoname());
				saveDB.setCompanyId(userDetails.getCompany_id());
				try {
					byte[] bytes = file.getBytes();
					saveDB.setBranch_filename(file.getOriginalFilename());

					saveDB.setBranch_content(ByteImageConvert.scale(bytes, 600, 600));
					saveDB.setBranch_contentType(file.getContentType());
				} catch (IOException e) {

				}

				java.util.Date currentDate = new java.util.Date();
				Timestamp toDateUpdte = new java.sql.Timestamp(currentDate.getTime());
				saveDB.setBranch_uploaddate(toDateUpdte);

				branchService.addBranchPhoto(saveDB);

				model.put("addbranchPhoto", true);
				return new ModelAndView("redirect:/ShowBranchPhoto.in?branch_id=" + branchPhoto.getBranch_id() + "",
						model);
			} else {
				// messages
				model.put("emptyDocument", true);
				return new ModelAndView("redirect:/ShowBranchPhoto.in?branch_id=" + branchPhoto.getBranch_id() + "",
						model);

			}

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("emptyDocument", true);
			return new ModelAndView("redirect:/ShowBranchPhoto.in?branch_id=" + branchPhoto.getBranch_id() + "", model);

		}
	}

	/* show the image of the branch photo */
	@RequestMapping("/BranchImage/{branch_photoid}")
	public String getImage(@PathVariable("branch_photoid") Integer branch_photoid, HttpServletResponse response) {
		try {
			if (branch_photoid == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext()
						.getAuthentication().getPrincipal();
				org.fleetopgroup.persistence.document.BranchPhoto file = branchService.getBranchPhoto(branch_photoid, userDetails.getCompany_id());

				if (file != null) {
					if (file.getBranch_content() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getBranch_filename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getBranch_contentType());
						response.getOutputStream().write(file.getBranch_content());
						out.flush();
						out.close();

					}
				} else {
					return "redirect:/NotFound.in";
				}
			}
		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (Exception e) {

		}
		return null;
	}

	/* Delete the branch Photo */
	@RequestMapping(value = "deleteBranchPhoto")
	public ModelAndView deletePhoto(@ModelAttribute("command") BranchPhoto branchPhotoDto, BindingResult result) {
		Map<String, Object> model = new HashMap<String, Object>();

		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			// delete method
			branchService.deleteBranchPhoto(branchPhotoDto.getBranch_photoid(), userDetails.getCompany_id());
			// this message alert of show method
			model.put("deletebranchPhoto", true);
			return new ModelAndView("redirect:/ShowBranchPhoto.in?branch_id=" + branchPhotoDto.getBranch_id() + "",
					model);

		} catch (NullPointerException e) {
			return new ModelAndView("redirect:/NotFound.in");
		} catch (Exception e) {
			model.put("danger", true);
			return new ModelAndView("redirect:/ShowBranchPhoto.in?branch_id=" + branchPhotoDto.getBranch_id() + "",
					model);
		}
	}

	/** get UserProfile Drop down List user search */
	@RequestMapping(value = "/getBranchListsearch", method = RequestMethod.POST)
	public void getBranchListsearch(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<Branch> user = new ArrayList<Branch>();
		List<Branch> userName = branchService.Search_Branch_Name_Json(term, userDetails.getCompany_id());
		if (userName != null && !userName.isEmpty()) {
			for (Branch add : userName) {
				Branch wadd = new Branch();

				wadd.setBranch_id(add.getBranch_id());
				wadd.setBranch_name(add.getBranch_name());

				user.add(wadd);
			}
		}
		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(user));
	}
}
