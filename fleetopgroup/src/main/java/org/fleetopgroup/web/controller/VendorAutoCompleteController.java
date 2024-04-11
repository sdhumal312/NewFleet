package org.fleetopgroup.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.Vendor;
import org.fleetopgroup.persistence.serviceImpl.IVendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;

@Controller
public class VendorAutoCompleteController {

	@Autowired
	private IVendorService vendorService;

	
	
	@RequestMapping(value = "/SearchOnlyPetrolPumpVendorName", method = RequestMethod.POST)
	public void getVendorSearchListFuel(Map<String, Object> map, @RequestParam("term") final String term,
			final HttpServletRequest request, HttpServletResponse response) throws Exception {
		CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		List<Vendor> addresses = new ArrayList<Vendor>();
		List<Vendor> vendor = vendorService.SearchOnlyPetrolPumpVendorName(term, userDetails.getCompany_id());
		if (vendor != null && !vendor.isEmpty()) {
			for (Vendor add : vendor) {
				Vendor wadd = new Vendor();
				wadd.setVendorId(add.getVendorId());
				wadd.setVendorName(add.getVendorName());
				wadd.setVendorLocation(add.getVendorLocation());
				wadd.setOwnPetrolPump(add.getOwnPetrolPump());

				addresses.add(wadd);
			}
		}
		Gson gson = new Gson();
		// System.out.println("jsonStudents = " + gson.toJson(addresses));

		response.getWriter().write(gson.toJson(addresses));
	}
}
