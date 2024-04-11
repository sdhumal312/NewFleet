package org.fleetopgroup.rest.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.serviceImpl.IDocumentService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DocumentRestController {
	
	@Autowired	IDocumentService					documentService;

	@RequestMapping("/downloadDoc/{type}/{documentId}")
	public String downloadDocument(@PathVariable("documentId") Long documentId, @PathVariable("type") short type, 
			HttpServletResponse response) throws Exception {
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ValueObject	valueObject =  documentService.getDocumentByDocumentId(documentId, userDetails.getCompany_id(), type);
		try {
			if (documentId == null) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
			} else {
				if (valueObject.get("file") != null) {
					if(valueObject.get("content") != null) {
						response.setHeader("Content-Disposition",
								"inline;filename=\"" + valueObject.getString("fileName") + "\"");
						OutputStream out = response.getOutputStream();
						response.setContentType(valueObject.getString("contentType"));
						response.getOutputStream().write((byte[])valueObject.get("content"));
						out.flush();
						out.close();

					}
				}
			}

		} catch (NullPointerException e) {
			return "redirect:/NotFound.in";
		} catch (IOException e) {
			throw e;
		}
		return null;
	}
}
