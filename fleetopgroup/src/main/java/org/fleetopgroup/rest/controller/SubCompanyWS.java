package org.fleetopgroup.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.fleetopgroup.persistence.model.SubCompany;
import org.fleetopgroup.persistence.serviceImpl.ISubCompanyService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

@RestController
public class SubCompanyWS {
	
	@Autowired 	ISubCompanyService   	subCompanyService   ;
	
	@GetMapping(value = "/addSubCompanyDetails")
	public ModelAndView addSubCompanyDetails(final HttpServletRequest request) {
		ModelAndView 	map 	= null;
		try {
			map = new ModelAndView("addSubCompanyDetails");
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value = "/subCompaniesAutoComplete")
	public void getCompanyInformationDetails(Map<String, Object> map, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			List<SubCompany>  	subCompanyList 		= new ArrayList<SubCompany>();
			List<SubCompany>  	subCompanyFinalList = new ArrayList<SubCompany>();
			
			ValueObject 	 	object 				= subCompanyService.getAllSubCompanies();
			subCompanyList							= (List<SubCompany>) object.get("subCompanyList");
			
			if(subCompanyList != null && !subCompanyList.isEmpty()) {	
				for (SubCompany subCompany : subCompanyList) {
					SubCompany 	dto 	= new SubCompany();
					dto.setSubCompanyId(subCompany.getSubCompanyId());
					dto.setSubCompanyName(subCompany.getSubCompanyName());
					subCompanyFinalList.add(dto);
				}
			}

			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(subCompanyFinalList));
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@PostMapping(value="/getAllSubCompanies", produces="application/json")
	public HashMap<Object, Object>  getAllSubCompanies(final HttpServletRequest request) throws Exception {
		try {
			return subCompanyService.getAllSubCompanies().getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	@PostMapping(value="/saveSubCompanyDetails", produces="application/json")
	public HashMap<Object, Object>  saveSubCompanyDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			
			ValueObject valueObject		= new ValueObject(allRequestParams);
			return subCompanyService.saveSubCompany(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@GetMapping(value = "/editSubCompanyDetails")
	public ModelAndView editSubCompanyDetails(@RequestParam("subCompanyId") final Long subCompanyId, final HttpServletRequest request) {
		ModelAndView 	map 	= null;
		try {
			map = new ModelAndView("editSubCompanyDetails");
			map.addObject("subCompanyId",subCompanyId);
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(value="/getSubCompanyById", produces="application/json")
	public HashMap<Object, Object>  getSubCompanyById(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			
			ValueObject	valueObject		= new ValueObject(allRequestParams);
			return subCompanyService.getSubCompanyById(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value="/updateSubCompanyDetails", produces="application/json")
	public HashMap<Object, Object>  updateSubCompanyDetails(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			
			ValueObject valueObject		= new ValueObject(allRequestParams);
			return subCompanyService.updateSubCompany(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	@PostMapping(value="/deleteSubCompanyDetails", produces="application/json")
	public HashMap<Object, Object>  deleteSubCompanyDetails( @RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		try {
			ValueObject	valueObject		= new ValueObject(allRequestParams);
			return subCompanyService.deleteSubCompany(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} 
	}
	
}
