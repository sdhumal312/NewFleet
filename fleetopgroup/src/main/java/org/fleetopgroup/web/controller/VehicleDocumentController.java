package org.fleetopgroup.web.controller;
/**
 * @author fleetop
 *
 *
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.fleetopgroup.persistence.bl.RenewalReminderBL;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleDocTypeBL;
import org.fleetopgroup.persistence.bl.VehicleDocumentBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDocumentDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.VehicleDocType;
import org.fleetopgroup.persistence.model.VehicleDocument;
import org.fleetopgroup.persistence.serviceImpl.IRenewalReminderService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocTypeService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleDocumentService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
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

@Controller
public class VehicleDocumentController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IVehicleDocTypeService vehicleDocTypeService;
	@Autowired
	private IVehicleService vehicleService;
	
	@Autowired private IVehicleDocumentService	vehicleDocumentService;
	
	@Autowired private MongoOperations	mongoOperations;
	@Autowired
	private IRenewalReminderService RenewalReminderService;

	VehicleBL VBL = new VehicleBL();
	VehicleDocumentBL VDOCBL = new VehicleDocumentBL();
	VehicleDocTypeBL V_Doc_TYBL = new VehicleDocTypeBL();
	RenewalReminderBL RRBL = new RenewalReminderBL();
	
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");


	/* Driver Reminder */
	@RequestMapping("/ShowVehicleDocument")
	public ModelAndView ShowVehicleDocument(@ModelAttribute("command") VehicleDocument vehicleDocument,
			BindingResult result) {
		Map<String, Object> model 			= new HashMap<String, Object>();
		VehicleDto				vehicle		  	= null;
		CustomUserDetails	userDetails		= null;
		try {
			java.util.Date currentDate = new java.util.Date();
			DateFormat ft = new SimpleDateFormat("YYYY-MM-dd");
			/** Show Only Select Column Value in Vehicle Tables **/
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicle	= vehicleService.Get_Vehicle_Header_Details(vehicleDocument.getVehid());
			if(vehicle != null) {
				model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));
				// vehicle Document Master Type
				model.put("vehicledoctype", V_Doc_TYBL.prepareListofDto(vehicleDocTypeService.findAllVehicleDocTypeByCompanyId(userDetails.getCompany_id())));
				/* list of driver reminder */
				
				model.put("renewal", RRBL.Only_Show_ListofRenewalDto(RenewalReminderService.listVehicleRenewalReminder(
						vehicleDocument.getVehid(), ft.format(currentDate)+DateTimeUtility.DAY_END_TIME, userDetails.getCompany_id())));
				model.put("vehicledocumentList", getVehicleDocumentList(vehicleDocumentService.listVehicleDocument(vehicleDocument.getVehid(), userDetails.getCompany_id())));
				Object[] count = vehicleService
						.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(vehicleDocument.getVehid());
				model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(vehicleDocument.getVehid(), userDetails.getCompany_id()));
				model.put("photo_Count", (Long) count[0]);
				model.put("purchase_Count", (Long) count[1]);
				model.put("reminder_Count", (Long) count[2]);
				model.put("fuelEntrie_Count", (Long) count[3]);
				model.put("serviceEntrie_Count", (Long) count[4]);
				model.put("serviceReminder_Count", (Long) count[5]);
				model.put("tripsheet_Count", (Long) count[6]);
				model.put("workOrder_Count", (Long) count[7]);
				model.put("issues_Count", (Long) count[8]);
				model.put("odometerhis_Count", (Long) count[9]);
				model.put("comment_Count", (Long) count[10]);
				model.put("breakDownCount", ((Long) count[4]+(Long) count[8]));
			}

		} catch (Exception e) {
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		return new ModelAndView("Show_Vehicle_Document", model);
	}

	
	public List<VehicleDocumentDto> getVehicleDocumentList(List<org.fleetopgroup.persistence.document.VehicleDocument> list){
		CustomUserDetails	userDetails		= null;
		try {
			/** Show Only Select Column Value in Vehicle Tables **/
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			List<VehicleDocumentDto> Dtos = null;
			if(list != null) {
				VehicleDocType	docType	= null;
				Dtos = new ArrayList<>();
				for(org.fleetopgroup.persistence.document.VehicleDocument document : list) {
					VehicleDocumentDto Documentto = null;
					Documentto = new VehicleDocumentDto();
					Documentto.setId(document.getId());
					if(document.getDocTypeId() != null)
						docType	= vehicleDocTypeService.getVehicleDocTypeByID(document.getDocTypeId(), userDetails.getCompany_id());
					
					if(docType != null)
						Documentto.setName(vehicleDocTypeService.getVehicleDocTypeByID(document.getDocTypeId(), userDetails.getCompany_id()).getvDocType());
					if(document.getCreated() != null)
						Documentto.setUploaddate(CreatedDateTime.format(document.getCreated()));
					Documentto.setFilename(document.getFilename());
					Documentto.setVehid(document.getVehid());

					Dtos.add(Documentto);
				}
			}
			return Dtos;
		} catch (Exception e) {
			throw e;
		}finally {
			userDetails = null;
		}
	}
	// sort the Document Name
	@RequestMapping("/sortVehicleDocument")
	public ModelAndView sortVehicleDocument(@ModelAttribute("command") VehicleDocument VehicleDocumentBean,
			BindingResult result) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// First get the driver_remid all information
			VehicleDocumentDto vehicleDocument = VDOCBL
					.GetVehicleDocumentPhoto(vehicleService.getVehicleDocument(VehicleDocumentBean.getId(), userDetails.getCompany_id()));
			model.put("vehicle", VBL.prepare_Vehicle_Header_Show(
					vehicleService.Get_Vehicle_Header_Details(vehicleDocument.getVehid())));
			// vehicle Document Master Type
			model.put("vehicledoctype", V_Doc_TYBL.prepareListofDto(vehicleDocTypeService.findAllVehicleDocTypeByCompanyId(userDetails.getCompany_id())));
			/* list of driver reminder */
			model.put("vehicledocumentList",
					vehicleService.listofSortVehicleDocument(vehicleDocument.getVehid(), vehicleDocument.getDocTypeId(), userDetails.getCompany_id()));
		} catch (Exception e) {
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		return new ModelAndView("Show_Vehicle_Document", model);
	}

	// save vehicle Document image and value

	@RequestMapping(value = "/saveVehicleDocument")
	public String singleUpload() {
		return "saveVehicleDocument";
	}

	@RequestMapping(value = "/saveVehicleDocument", method = RequestMethod.POST)
	public @ResponseBody ModelAndView singleSave(@RequestParam("fileUpload") MultipartFile file,
			org.fleetopgroup.persistence.document.VehicleDocument photo) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			if (!file.isEmpty()) {
				try {
					CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
					byte[] bytes = file.getBytes();
					photo.setFilename(file.getOriginalFilename());
					photo.setContent(bytes);
					photo.setContentType(file.getContentType());

					photo.setCompanyId(userDetails.getCompany_id());
					//String timestamp = new SimpleDateFormat("dd-MM-YYYY HH:mm:ss").format(new Date());
					//photo.setUploaddate(timestamp);
					photo.setCreated(new Date());
					photo.setCreatedById(userDetails.getId());
					photo.setId(2973);

					//vehicleService.addVehicleDocument(photo);
					if(photo.getId() == null) {
						vehicleDocumentService.saveVehicleDoc(photo);
					}else {
						mongoOperations.save(photo);
					}
				} catch (Exception e) {
					e.printStackTrace();
					// messages
					model.put("emptyDocument", true);
					LOGGER.error("Add Vehicle Status Page:", e);
					return new ModelAndView("redirect:/ShowVehicleDocument.in?vehid=" + photo.getVehid() + "", model);
				}
			} else {
				// messages
				model.put("emptyDocument", true);
				return new ModelAndView("redirect:/ShowVehicleDocument.in?vehid=" + photo.getVehid() + "", model);

			}
			// messages
			model.put("saveVehicleDocument", true);
		} catch (Exception e) {
			e.printStackTrace();
			// messages
			model.put("alreadyVehicleDocument", true);
			LOGGER.error("Add Vehicle Status Page:", e);
			return new ModelAndView("redirect:/ShowVehicleDocument.in?vehid=" + photo.getVehid() + "", model);

		}
		return new ModelAndView("redirect:/ShowVehicleDocument.in?vehid=" + photo.getVehid() + "", model);
	}

	// Image Document the Document id
	@RequestMapping("/downloaddocument/{documentId}")
	public String download(@PathVariable("documentId") Integer documentId, HttpServletResponse response) {

		try {
			if (documentId == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				// Lookup file by fileId in database.
				org.fleetopgroup.persistence.document.VehicleDocument file = vehicleDocumentService.getVehicleDocument(documentId);
				// Check if file is actually retrieved from database.
				if (file != null) {
					if (file.getContent() != null) {

						response.setHeader("Content-Disposition", "inline;filename=\"" + file.getFilename() + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(file.getContentType());
						response.getOutputStream().write(file.getContent());
						out.flush();
						out.close();

					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Vehicle Status Page:", e);
		}
		return null;
	}

	// edit Vehicle Document and revise the Image
	@RequestMapping(value = "/editVehicleDocument", method = RequestMethod.GET)
	public ModelAndView editVehicle(@ModelAttribute("command") VehicleDocument VehicleDocumentBean,
			HttpServletResponse response) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			VehicleDocumentDto vehi = prepareVehicleDocumentBean(vehicleDocumentService.getVehicleDocument(VehicleDocumentBean.getId(), userDetails.getCompany_id()));
			model.put("vehicledocument", vehi);

			model.put("vehicle",
					VBL.prepare_Vehicle_Header_Show(vehicleService.Get_Vehicle_Header_Details(vehi.getVehid())));

		} catch (Exception e1) {
			LOGGER.error("Add Vehicle Status Page:", e1);
		}
		return new ModelAndView("editVehicleDocument", model);

	}
	
	public VehicleDocumentDto prepareVehicleDocumentBean(org.fleetopgroup.persistence.document.VehicleDocument photo) {
		VehicleDocumentDto bean = new VehicleDocumentDto();
		CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		bean.setId(photo.getId());
		bean.setVehid(photo.getVehid());
		bean.setName(vehicleDocTypeService.getVehicleDocTypeByID(photo.getDocTypeId(), userDetails.getCompany_id()).getvDocType());
		if(bean.getCreated() != null)
			bean.setUploaddate(CreatedDateTime.format(photo.getCreated()));
		bean.setDocTypeId(photo.getDocTypeId());
		
		return bean;
	}

	/* Delete the Driver Document */
	@RequestMapping("/deleteVehicleDocument")
	public ModelAndView deleteVehicleDocument(@ModelAttribute("command") VehicleDocument VehicleDocumentBean,
			HttpServletResponse response) {

		Map<String, Object> model = new HashMap<String, Object>();
		try {
			CustomUserDetails	userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			// delete method
			vehicleDocumentService.deleteVehicleDocument(VehicleDocumentBean.getId(), userDetails.getCompany_id());
			// this message alert of show method
			model.put("deleteVehicleDocument", true);
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Vehicle Status Page:", e);
			return new ModelAndView("redirect:/ShowVehicleDocument.in?vehid=" + VehicleDocumentBean.getVehid() + "",
					model);
		}
		return new ModelAndView("redirect:/ShowVehicleDocument.in?vehid=" + VehicleDocumentBean.getVehid() + "", model);
	}
	
	@RequestMapping("/ViewVehicleDocument")
	public ModelAndView ViewVehicleDocument(@ModelAttribute("command") VehicleDocument vehicleDocument,	final HttpServletRequest request) {
		Map<String, Object> 	model 			= new HashMap<String, Object>();
		VehicleDto				vehicle		  	= null;
		CustomUserDetails		userDetails		= null;
		try {
			
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			vehicle	= vehicleService.Get_Vehicle_Header_Details(vehicleDocument.getVehid());
			
			if(vehicle != null) {
				model.put("vehicle", VBL.prepare_Vehicle_Header_Show(vehicle));
				model.put("vehicledoctype", V_Doc_TYBL.prepareListofDto(vehicleDocTypeService.findAllVehicleDocTypeByCompanyId(userDetails.getCompany_id())));
				
				/*model.put("renewal", RRBL.Only_Show_ListofRenewalDto(RenewalReminderService.listVehicleRenewalReminder(
						vehicleDocument.getVehid(), ft.format(currentDate)+DateTimeUtility.DAY_END_TIME, userDetails.getCompany_id())));*/
				
				model.put("vehicledocumentList", getVehicleDocumentList(vehicleDocumentService.listVehicleDocument(vehicleDocument.getVehid(), userDetails.getCompany_id())));
				
				model.put("document_Count", vehicleDocumentService.getVehicleDocumentCount(vehicleDocument.getVehid(), userDetails.getCompany_id()));
				
				Object[] count = vehicleService.countTotalVehicle_Doc_Pho_Pur_RR_FE_SE_SR_TE_WOR_ISSU_ODOH_COM(vehicleDocument.getVehid());
				model.put("photo_Count", (Long) count[0]);
				model.put("purchase_Count", (Long) count[1]);
				model.put("reminder_Count", (Long) count[2]);
				model.put("fuelEntrie_Count", (Long) count[3]);
				model.put("serviceEntrie_Count", (Long) count[4]);
				model.put("serviceReminder_Count", (Long) count[5]);
				model.put("tripsheet_Count", (Long) count[6]);
				model.put("workOrder_Count", (Long) count[7]);
				model.put("issues_Count", (Long) count[8]);
				model.put("odometerhis_Count", (Long) count[9]);
				model.put("comment_Count", (Long) count[10]);
				model.put("breakDownCount", ((Long) count[4]+(Long) count[8]));
				model.put("dseCount", (Long) count[12]);
				
				String uniqueID = UUID.randomUUID().toString();
				request.getSession().setAttribute(uniqueID, uniqueID);
				model.put("accessToken", uniqueID);
			}

		} catch (Exception e) {
			LOGGER.error("ViewVehicleDocument:", e);
		}
		return new ModelAndView("ViewVehicleDocument", model);
	}
	
	@RequestMapping(value = "/downloadfuelentrydocument", method = RequestMethod.GET)
	public String downloadFuelEntry(HttpServletResponse response) {

		CustomUserDetails 			userDetails 					= null;
		HSSFWorkbook 				hssfWorkbook					= null;
		HSSFSheet 					hssfSheet						= null;
		HSSFRow 					hssfRow							= null; 

		try {
			userDetails 		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();   
			
			hssfWorkbook 		= new HSSFWorkbook();
			hssfSheet 			= hssfWorkbook.createSheet();
			hssfRow 			= hssfSheet.createRow((short) 0);
			

				DataFormat fmt = hssfWorkbook.createDataFormat();
				CellStyle textStyle = hssfWorkbook.createCellStyle();
				textStyle.setDataFormat(fmt.getFormat("@"));
				hssfSheet.setDefaultColumnStyle(0, textStyle);
				hssfSheet.setDefaultColumnStyle(1, textStyle);

				hssfRow.createCell(0).setCellValue("Vehicle No.");
				hssfRow.createCell(1).setCellValue("Fuel Date");
				hssfRow.createCell(2).setCellValue("Odometer");
				hssfRow.createCell(3).setCellValue("Usage KM");
				hssfRow.createCell(4).setCellValue("Diesel Quantity");
				hssfRow.createCell(5).setCellValue("Price");
				hssfRow.createCell(6).setCellValue("Full/Partial");
				hssfRow.createCell(7).setCellValue("Vendor Name");
			
			FileOutputStream fileOut = new FileOutputStream(new File("Fuel_Entry.xls"));
			hssfWorkbook.write(fileOut);
			fileOut.close();

			//Code to download
			File fileToDownload = new File("Fuel_Entry.xls");
			InputStream in = new FileInputStream(fileToDownload);

			// Gets MIME type of the file
			String mimeType = new MimetypesFileTypeMap().getContentType("Fuel_Entry.xls");

			if (mimeType == null) {
				// Set to binary type if MIME mapping not found
				mimeType = "application/octet-stream";
			}
			// Modifies response
			response.setContentType(mimeType);
			response.setContentLength((int) fileToDownload.length());

			// Forces download
			String headerKey = "Content-Disposition";
			String headerValue = String.format("attachment; filename=\"%s\"", fileToDownload.getName());
			response.setHeader(headerKey, headerValue);

			// obtains response's output stream
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[4096];
			int bytesRead = -1;

			while ((bytesRead = in.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}
			
			in.close();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("Add Fuel Status Page:", e);
		}
		return null;
	}

}