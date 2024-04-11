package org.fleetopgroup.web.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.bl.VehicleInspectionBL;
import org.fleetopgroup.persistence.document.InspectionParameterDocument;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InspectionParameterDocumentDto;
import org.fleetopgroup.persistence.dto.InspectionParameterDto;
import org.fleetopgroup.persistence.model.InspectionParameter;
import org.fleetopgroup.persistence.model.VehicleInspectionSheetToParameter;
import org.fleetopgroup.persistence.serviceImpl.IInspectionParameterDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionParameterService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionSheetToParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

@Controller
public class VehicleInspectionParameterController {
	
	@Autowired 
	MongoOperations				mongoOperations;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	private @Autowired	IVehicleInspectionParameterService				vehicleInspectionParameterService;
	private @Autowired	IInspectionParameterDocumentService				inspectionParameterDocumentService;
	//private @Autowired	IVehicleInspectionSheetService					vehicleInspectionSheetService;
	private @Autowired	IVehicleInspectionSheetToParameterService		vehicleInspectionSheetToParameterService;
	
	
	VehicleInspectionBL vehicleInspectionBL = new VehicleInspectionBL();
	
	@RequestMapping(value = "/getInspectionParameterList", method = RequestMethod.GET)
	public void getInspectionParameterList(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		List<InspectionParameter> parameterList =	new ArrayList<>();
		CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<InspectionParameter> inspectionParameterList = vehicleInspectionParameterService.getInspectionParameterList(userDetails.getCompany_id());

		if(inspectionParameterList != null && !inspectionParameterList.isEmpty()) {
			for (InspectionParameter parameter : inspectionParameterList) {

				InspectionParameter bean = new InspectionParameter();
				bean.setInspectionParameterId(parameter.getInspectionParameterId());
				bean.setParameterName(parameter.getParameterName());

				parameterList.add(bean);
			}
		}

		Gson gson = new Gson();

		response.getWriter().write(gson.toJson(parameterList));
	}
	
	
	@RequestMapping(value = "/addInspectionParameter", method = RequestMethod.GET)
	public ModelAndView addInspectionParameter(final InspectionParameterDto inspectionParameterDto, final HttpServletRequest request) {
		Map<String, Object> 					model 									= new HashMap<String, Object>();
		List<InspectionParameter>				inspectionParam							= null;
		CustomUserDetails						userDetails 							= null;
		ArrayList<InspectionParameterDocument>	arraylist								= null;
		try {
			userDetails			= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			inspectionParam 	= vehicleInspectionParameterService.getInspectionParameterList(userDetails.getCompany_id());
			arraylist 			= new ArrayList<InspectionParameterDocument>();
				
			for (InspectionParameter Rolelist : inspectionParam) {
				if(Rolelist.getParameterPhotoId() != null) {
				arraylist.add(inspectionParameterDocumentService.getInspectionParameterPhoto(Rolelist.getParameterPhotoId(),userDetails.getCompany_id()));
				}
			}
			model.put("InspectionParameter", inspectionParam);
			model.put("ParamterPhoto",vehicleInspectionBL.prepareListMasterParameterPhoto(arraylist));
			model.put("CompanyId", userDetails.getCompany_id());
		} 
		catch (Exception e) {
			LOGGER.error("add tripsheet", e);
		}
		
		return new ModelAndView("addInspectionParameter", model);

      }
	
	
	@RequestMapping(value = "/saveInspectionParameter", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView saveInspectionParameter(@ModelAttribute("command") InspectionParameterDocument inspectParamDoc,
		@RequestParam("parameterPhoto") MultipartFile file, final InspectionParameterDto inspectionParameterDto, final HttpServletRequest request) throws Exception {

		Map<String, Object>			model 					= new HashMap<String, Object>();
		InspectionParameter      	inspectionParameter 	= null;
		CustomUserDetails			userDetails				=	null;
		
		
		final InspectionParameter parameter =  vehicleInspectionBL.prepareModel(inspectionParameterDto);
		userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		inspectionParameter = vehicleInspectionParameterService.findByParamterName(parameter.getParameterName().trim(), userDetails.getCompany_id());
		
		if (inspectionParameter == null) {
			
			parameter.setCreatedById(userDetails.getId());
			parameter.setLastModifiedById(userDetails.getId());
			parameter.setCompanyId(userDetails.getCompany_id());
			
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					
					inspectParamDoc.setFilename(file.getOriginalFilename());
					inspectParamDoc.setContent(bytes);
					inspectParamDoc.setContentType(file.getContentType());
					inspectParamDoc.setCreatedById(userDetails.getId());
					inspectParamDoc.setCompanyId(userDetails.getCompany_id());
					inspectParamDoc.setCreated(new Date());
				} catch (IOException e) {

				}
				inspectionParameterDocumentService.saveInspectionParameterDocument(inspectParamDoc);

			}
			
			if(inspectParamDoc.getId() != null) {
			parameter.setParameterPhotoId((long)inspectParamDoc.getId());
			}
			
			//save all the data in vehicleInspectionParameter table
			vehicleInspectionParameterService.registerNewInspectionParameter(parameter);
			model.put("saveInspectionParameter", true);
			
		} else {
			model.put("alreadyInspectionParameter", true);
			return new ModelAndView("redirect:/addInspectionParameter.html", model);
		}
		
		return new ModelAndView("redirect:/addInspectionParameter.html", model);
		
		
	}	
		
	/* show the image of the InspectionParameter photo */
	@RequestMapping("/getParameterImage/{ParamterPhoto_id}")
	public String getImage(@PathVariable("ParamterPhoto_id") Long ParamterPhoto_id, HttpServletResponse response)
			throws Exception {
		try {
			CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if (ParamterPhoto_id == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				InspectionParameterDocument file = inspectionParameterDocumentService.getInspectionParameterPhoto(ParamterPhoto_id,userDetails.getCompany_id());
				if (file != null) {
					if (file.getContent() != null) {

						response.setHeader("Content-Disposition",
								"inline;filename=\"" + file.getFilename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getContentType());
						response.getOutputStream().write(file.getContent());
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
		
	
	// edit Parameter Document and revise the Image
		@RequestMapping(value = "/editParameterDocument", method = RequestMethod.GET)
		public ModelAndView editVehicle(@ModelAttribute("command") InspectionParameter ParameterDocumentBean,
				HttpServletResponse response) {
			InspectionParameter		inspectionParam		= null;
			Map<String, Object> model = new HashMap<String, Object>();
			try {
				CustomUserDetails	userDetails	=	(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				if(ParameterDocumentBean.getParameterPhotoId() != null) {
				InspectionParameterDocumentDto para = prepareParameterDocumentBean(inspectionParameterDocumentService.getInspectionParameterPhoto(ParameterDocumentBean.getParameterPhotoId(),userDetails.getCompany_id()));
				model.put("ParameterDocument", para);
				}

				inspectionParam = vehicleInspectionParameterService.findByParamterId(ParameterDocumentBean.getInspectionParameterId(), userDetails.getCompany_id());
				model.put("InspectionParameter", inspectionParam);

			} catch (Exception e1) {
				LOGGER.error("Add Vehicle Status Page:", e1);
			}
			return new ModelAndView("editInspectionParameter", model);

		}
		
		public InspectionParameterDocumentDto prepareParameterDocumentBean(InspectionParameterDocument photo) {
			InspectionParameterDocumentDto bean = new InspectionParameterDocumentDto();
			
			bean.setId(photo.getId());
			bean.setFilename(photo.getFilename());
			bean.setCreated(photo.getCreated());
			
			return bean;
		}
		
		
		/* add Driver Document revise */
		@RequestMapping(value = "/reviseParameterDocument", method = RequestMethod.POST)
		public @ResponseBody ModelAndView reviseParameterDocument(@ModelAttribute("command") InspectionParameterDto ParameterDocumentBean, @RequestParam("file") MultipartFile file) throws Exception {
			InspectionParameterDocument 				inspectParamDoc				= null;
			InspectionParameter 						inspeParamName				= null;
			CustomUserDetails							userDetails 				= null;
			InspectionParameter							parameter					= null;
			Timestamp 									toDate 						= null;
			Map<String, Object> 						model 						= new HashMap<String, Object>();
			long 										docId						= 0;
			
			try {
				userDetails 				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				toDate 						= new Timestamp(new Date().getTime());
				
				parameter					= vehicleInspectionParameterService.findByParamterId(ParameterDocumentBean.getInspectionParameterId(),userDetails.getCompany_id());

				if(parameter.getParameterName().trim().equalsIgnoreCase(ParameterDocumentBean.getParameterName().trim())) {
					//checking file is not empty
					if( !file.isEmpty()) {
						inspectParamDoc	 = new InspectionParameterDocument();
						try {
							byte[] bytes = file.getBytes();
							
							inspectParamDoc.setFilename(file.getOriginalFilename());
							inspectParamDoc.setContent(bytes);
							inspectParamDoc.setContentType(file.getContentType());
							inspectParamDoc.setCompanyId(userDetails.getCompany_id());
							inspectParamDoc.setCreatedById(userDetails.getId());
							inspectParamDoc.setCreated(new Date());
						} catch (IOException e) {
							LOGGER.error("Err"+e);
						}
						docId = inspectionParameterDocumentService.saveInspectionParameterDocument(inspectParamDoc);//save File
						
						vehicleInspectionParameterService.updateParameter(ParameterDocumentBean.getInspectionParameterId() ,toDate,docId);//update parameter Table With PhotoId
						return new ModelAndView("redirect:/addInspectionParameter.html", model);
					}else {
						model.put("alreadyInspectionParameter", true);
						return new ModelAndView("redirect:/addInspectionParameter.html", model);
					}
				} else {	// inputparameter and parameter name in table is diff
					inspeParamName = vehicleInspectionParameterService.findByParamterName(ParameterDocumentBean.getParameterName(),userDetails.getCompany_id());
					
					if(inspeParamName == null) {
						if( !file.isEmpty()) {
							inspectParamDoc  = new InspectionParameterDocument();
							try {
								byte[] bytes = file.getBytes();
								
								inspectParamDoc.setFilename(file.getOriginalFilename());
								inspectParamDoc.setContent(bytes);
								inspectParamDoc.setContentType(file.getContentType());
								inspectParamDoc.setCompanyId(userDetails.getCompany_id());
								inspectParamDoc.setCreatedById(userDetails.getId());
								inspectParamDoc.setCreated(new Date());
							} catch (IOException e) {
								LOGGER.error("Err"+e);
							}
							
							docId = inspectionParameterDocumentService.saveInspectionParameterDocument(inspectParamDoc);//save File
							
							vehicleInspectionParameterService.updateParameterParameterName(ParameterDocumentBean.getInspectionParameterId() ,ParameterDocumentBean.getParameterName(),toDate,docId);//update parameter Table With PhotoId
							
							model.put("updateDriverDocument", true);
							return new ModelAndView("redirect:/addInspectionParameter.html", model);
							
						}else {
							vehicleInspectionParameterService.updateParameterPhotoIdNull(ParameterDocumentBean.getInspectionParameterId() ,ParameterDocumentBean.getParameterName(),toDate);//update parameter Table Without PhotoId
							return new ModelAndView("redirect:/addInspectionParameter.html", model);
							
						}
					}else {
						model.put("alreadyInspectionParameter", true);
						return new ModelAndView("redirect:/addInspectionParameter.html", model);
					}
				}
			}catch(Exception e) {
				LOGGER.error("Error"+e);
			}
			return null;
		}
		
		
		/* Delete the Parameter Document */
		@RequestMapping("/deleteParameterDocument")
		public ModelAndView deleteParameterDocument(@ModelAttribute("command") InspectionParameterDto parameterDto,BindingResult result,RedirectAttributes redir) throws Exception {
			
			Map<String, Object> 						model 						= new HashMap<String, Object>();
			List<VehicleInspectionSheetToParameter>		sheetToParameterDtoList		= null;
			CustomUserDetails							userDetails 				= null;
			try {
				userDetails 				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				sheetToParameterDtoList		=	vehicleInspectionSheetToParameterService.getVehicleInspectionSheetsToParameterListByParameterId(parameterDto.getInspectionParameterId(), userDetails.getCompany_id());
				
				if (sheetToParameterDtoList.isEmpty()) {
					// delete method
					if(parameterDto.getParameterPhotoId() != null) {
						long id = parameterDto.getParameterPhotoId();
						int id1 = (int)id;
						inspectionParameterDocumentService.deleteParameterDocument(id1);
					}
					vehicleInspectionParameterService.deleteParameter(parameterDto.getInspectionParameterId(),userDetails.getCompany_id());
					// this message alert of show method
					model.put("deleteParameterDocument", true);
					return new ModelAndView(
							"redirect:/addInspectionParameter.in?id=" + parameterDto.getInspectionParameterId() + "", model);
				} else {
					String Link ="";
					String TIDMandatory ="";
					
					for (VehicleInspectionSheetToParameter sheet :sheetToParameterDtoList) {
					Link += " <a href=\"EditInspectionSheet.in?ISID_id=" + sheet.getInspectionSheetId()
					+ "\" target=\"_blank\">-" + sheet.getInspectionSheetId()
					+ "  <i class=\"fa fa-external-link\"></i></a> ";
					
					}
					
					TIDMandatory += " <span> This Parameter  "
							+ " Used In VehicleInspectionSheet " + " Please Remove First " + Link + "" + " </span> ,";
					
					redir.addFlashAttribute("VMandatory", TIDMandatory);
					// messages
					model.put("emptyDocument", true);
					return new ModelAndView(
							"redirect:addInspectionParameter.in?id=" + parameterDto.getInspectionParameterId() + "", model);
				}
			}catch(Exception e) {
				LOGGER.error("Err"+e);
				return new ModelAndView("redirect:/addInspectionParameter.html", model);
			}
		}
		
		/* Should be Download Driver Document */
		@RequestMapping("/download/parameterDocument/{parameterPhotoId}")
		public String download(@PathVariable("parameterPhotoId") Integer parameterPhotoId, HttpServletResponse response) throws Exception {
			CustomUserDetails userDetails 				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			InspectionParameterDocument file = inspectionParameterDocumentService.getInspectionParameterPhoto(parameterPhotoId, userDetails.getCompany_id());
			try {
				if (parameterPhotoId == null) {
					response.setContentType("Empty Image");
					//response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
				} else {
					if (file != null) {
						if (file.getContent() != null) {

							response.setHeader("Content-Disposition",
									"inline;filename=\"" + file.getFilename() + "\"");
							OutputStream out = response.getOutputStream();
							response.setContentType(file.getContentType());
							response.getOutputStream().write(file.getContent());
							out.flush();
							out.close();

						}
					}
				}

			} catch (NullPointerException e) {
				return "redirect:/NotFound.in";
			} catch (IOException e) {

			}
			return null;
		}
	
}
