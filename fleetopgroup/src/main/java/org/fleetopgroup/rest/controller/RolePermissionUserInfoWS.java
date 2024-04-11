package org.fleetopgroup.rest.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.TypedQuery;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.UserProfile;
import org.fleetopgroup.persistence.serviceImpl.IPartCategoriesService;
import org.fleetopgroup.persistence.serviceImpl.IRoleService;
import org.fleetopgroup.persistence.serviceImpl.ITripCollectionService;
import org.fleetopgroup.persistence.serviceImpl.ITripSheetService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/RolePermissionUserInfoWS")
public class RolePermissionUserInfoWS {
	@Autowired private IRoleService roleService;
	@Autowired ITripCollectionService   iTripCollectionService;
	@Autowired ITripSheetService   		iTripSheetService;
	@Autowired IPartCategoriesService   PartCategoriesService;

	@RequestMapping(value = "/getRolePermissionUserInfo", method = RequestMethod.POST, produces="application/json")
	public HashMap<Object, Object>  getRolePermissionUserInfo(@RequestParam HashMap<Object, Object> allRequestParams) throws Exception {
		ValueObject			valueOutObject 			= null;
		ValueObject 		object 					= null;
		
		try {
			CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			
			object 				= new ValueObject(allRequestParams);
			valueOutObject		= roleService.getRolePermissionUserInfoList(object,userDetails.getCompany_id());
			
			return valueOutObject.getHtData();
		} catch (Exception e) {
			throw e;
		} finally {
			valueOutObject 	= null;
		}
	}
	
	
}