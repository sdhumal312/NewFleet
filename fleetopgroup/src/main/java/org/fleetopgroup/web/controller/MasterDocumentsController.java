package org.fleetopgroup.web.controller;
/**
 * @author fleetop
 *
 */

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.fleetopgroup.constant.MasterDocumentsConstant;
import org.fleetopgroup.persistence.document.MasterDocuments;
import org.fleetopgroup.persistence.model.DriverDocumentHistory;
import org.fleetopgroup.persistence.serviceImpl.IMasterDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MasterDocumentsController {
	
	@Autowired IMasterDocumentService  masterDocumentService;
	
	@RequestMapping(value = "/uploadMasterDocuments", method = RequestMethod.POST)
	public @ResponseBody ModelAndView uploadMasterDocuments(final @RequestParam("documentTypeId") Short documentTypeId,
			DriverDocumentHistory dochistory, @RequestParam("input-file-preview") MultipartFile file) {

		Map<String, Object> 		model 					= new HashMap<String, Object>();
		MasterDocuments				masterDocuments			= null;
		MasterDocuments				masterDocumentsFromDB	= null;
		byte[] 						bytes 					= null; 
		if (!file.isEmpty()) {
			try {
				masterDocuments		= new MasterDocuments();
				bytes 				= file.getBytes();
				
				masterDocuments.setName(file.getOriginalFilename());
				masterDocuments.setContent(bytes);
				masterDocuments.setContentType(file.getContentType());
			} catch (IOException e) {
				e.printStackTrace();
			}
			masterDocuments.setDocumentTypeId(documentTypeId);
			
			masterDocumentsFromDB	= masterDocumentService.getMasterDocuemntByDocumentTypeId(documentTypeId);
			
			if(masterDocumentsFromDB == null) {
				masterDocumentService.saveMasterDocuments(masterDocuments);
			} else {
				masterDocuments.set_id(masterDocumentsFromDB.get_id());
				masterDocumentService.saveOrUpdateMasterDocuments(masterDocuments);
			}
			
			model.put("masterDocumentList", MasterDocumentsConstant.getMaterDocumentList());
			return new ModelAndView("vehicle", model);
		} else {
			// messages
			model.put("masterDocumentList", MasterDocumentsConstant.getMaterDocumentList());
			model.put("emptyDocument", true);
			return new ModelAndView("vehicle", model);

		}

	}
}