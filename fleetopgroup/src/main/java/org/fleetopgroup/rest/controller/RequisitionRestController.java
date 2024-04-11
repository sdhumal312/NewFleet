package org.fleetopgroup.rest.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.persistence.bl.PartLocationsBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RequisitionDto;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.persistence.serviceImpl.IPartLocationPermissionService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionApprovalService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionReceivelService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class RequisitionRestController {
	
	@Autowired  ICompanyConfigurationService 		companyConfigurationService;
	PartLocationsBL plbl = new PartLocationsBL();
	
	@Autowired IPartLocationPermissionService partLocationPermissionService;
	
	@Autowired IRequisitionService requisitionService;
	
	@Autowired IRequisitionApprovalService requisitionApprovalService;
	
	@Autowired IRequisitionReceivelService requisitionReceivelService;
	

	

	@GetMapping(value ="/createRequisition")
	public ModelAndView createRequisition() throws Exception {
		ModelAndView 				map 			= null;
		CustomUserDetails			userDetails		= null;
		HashMap<String, Object> 	configuration 	= null;
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			configuration =  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.REQUISITION_CONFIG);
			map 		= new ModelAndView("createRequisition");
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("userId", userDetails.getId());
			map.addObject("configuration", configuration);
			map.addObject("partLocationPermission", plbl.prepareListofPartLocation(partLocationPermissionService
					.getPartLocationPermissionIdWithName(userDetails.getId(), userDetails.getCompany_id())));
			
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/saveRequisition" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> saveRequisition(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		ValueObject valueOutObject = null;
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			valueOutObject = requisitionService.prepareRequisitionToSave(valueObject);
			return valueOutObject.getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@GetMapping(value ="/showRequisition")
	public ModelAndView showRequisition(@RequestParam("requisitionId")final Long requisitionId) {
		ModelAndView map =  null;
		CustomUserDetails userDetails = null;
		HashMap<String, Object> configuration = null;
		try {
			map = new ModelAndView("showRequisition");
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority> permission = userDetails.getGrantedAuthoritiesList();
			configuration =  companyConfigurationService.getCompanyConfiguration(userDetails.getCompany_id(), PropertyFileConstants.REQUISITION_CONFIG);
			boolean allowToEdit = false; 
			String queryStr=" R.requisitionId ="+requisitionId+" AND ";
			ValueObject valueObject= new ValueObject();
			
			
			List<RequisitionDto> requisitionDetailsList =requisitionService.getRequisitionById(valueObject,queryStr, userDetails.getCompany_id());
			
			if(requisitionDetailsList != null && !requisitionDetailsList.isEmpty()) {
				map.addObject("requisitionDetails",requisitionDetailsList.get(0));
				
				if(requisitionDetailsList.get(0).getCreatedById() == userDetails.getId())
					allowToEdit = true;
			}
			
			map.addObject("allPermission", permission.contains(new SimpleGrantedAuthority("ALL_REQUISITION_PERMISSIONS")));
			map.addObject("markAsComplete", permission.contains(new SimpleGrantedAuthority("REQUISITION_MARK_AS_COMPLETE")));
			map.addObject("allowToEdit",allowToEdit);
			map.addObject("requisitionId",requisitionId);
			map.addObject("companyId",userDetails.getCompany_id());
			map.addObject("configuration",configuration);
			map.addObject("userId",userDetails.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	@PostMapping(path="/getSubRequisitionById" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> getSubRequisitionById(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionService.getSubRequisitionDetails(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/getSubRequisitionBySubRequisitionId" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> getSubRequisitionByBySubRequisitionId(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionService.getSubRequisitionBySubRequisitionId(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@PostMapping(path="/updateSubRequisition" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> updateSubRequisition(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionService.updateSubRequisitionById(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/rejectSubRequisition" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> rejectSubRequisition(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionService.rejectSubRequisitionById(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/getTransactionStock" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> getTransactionStock(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionService.getStockWiseBranchList(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/getLocationWiseTransactionStock" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> getLocationWiseTransactionStock(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionService.getLocationWiseTransactionStock(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/approveSubRequisition" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> approveSubRequisition(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionApprovalService.approveSubRequisition(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/addToapproveRequisition" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> addToapproveRequisition(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionApprovalService.addToApproveRequisition(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/getApprovalDetails" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> getApprovalDetails(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionApprovalService.getApprovalDetailsById(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/processTransfer" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> processTransfer(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionApprovalService.getApprovalDetailsById(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/deleteApprovalById" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> deleteApprovalById(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionApprovalService.deletedRequisitionApprovalById(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/rejectApprovalById" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> rejectApprovalById(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionApprovalService.rejectApproval(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/changeRequisitionStatus" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> changeRequisitionStatus(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionService.finalApproval(valueObject).getHtData() ;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/saveTransferQuantity" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> saveTransferQuantity(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionApprovalService.saveTransferQuantity(valueObject).getHtData() ;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@GetMapping(value = "/requisition")
	public ModelAndView requisitionPage(final HttpServletRequest request) throws Exception {
		ModelAndView 				map 			= null;
		CustomUserDetails			userDetails		= null;
		boolean allowDelete = false;
		
		
		try {
			userDetails	= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Collection<GrantedAuthority> permission =userDetails.getGrantedAuthoritiesList();
			if(permission.contains(new SimpleGrantedAuthority("DELETE_REQUISITION")) || permission.contains(new SimpleGrantedAuthority("ALL_REQUISITION_PERMISSIONS"))){
				allowDelete= true;
			}
			map 	= new ModelAndView("requisition");
			map.addObject("companyId", userDetails.getCompany_id());
			map.addObject("allowDelete", allowDelete);
			map.addObject("userId", userDetails.getId());
			return map;
		}  catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/getRequisitionList" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> getRequisitionList(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionService.getRequisitionList(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/getStockWiseBranchList" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> getStockWiseBranchList(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionService.getStockWiseBranchList(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@PostMapping(path="/receiveTransfer" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> receiveTransfer(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionReceivelService.receiveTransferByApprovalId(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/rejectReceive" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> rejectReceiveTransfer(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionReceivelService.rejectReceivalApprove(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@PostMapping(path="/createPOFromRequisition" ,produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<Object, Object> createPOFromRequisition(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
		try {
			ValueObject valueObject = new ValueObject(allReqParam);
			return requisitionReceivelService.createPOFromRequisition(valueObject).getHtData();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
		@PostMapping(path="/deleteRequisitionById" ,produces = MediaType.APPLICATION_JSON_VALUE)
		public HashMap<Object, Object> deleteRequisitionById(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
			try {
				ValueObject valueObject = new ValueObject(allReqParam);
				return requisitionService.deleteRequisitionById(valueObject).getHtData();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		
		@PostMapping(path="/markRequisitionAsComplete" ,produces = MediaType.APPLICATION_JSON_VALUE)
		public HashMap<Object, Object> markRequisitionAsComplete(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
			try {
				ValueObject valueObject = new ValueObject(allReqParam);
				return requisitionReceivelService.markRequisitionAsComplete(valueObject).getHtData();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@PostMapping(path="/receiveAllPartApprovals" ,produces = MediaType.APPLICATION_JSON_VALUE)
		public HashMap<Object, Object> receiveAllPartApprovals(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
			try {
				return requisitionReceivelService.receiveAllPartApprovals(new ValueObject(allReqParam)).getHtData();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		@PostMapping(path="/searchRequisitionByNumber" ,produces = MediaType.APPLICATION_JSON_VALUE)
		public HashMap<Object, Object> searchRequisitionByNumber(@RequestParam HashMap<Object, Object> allReqParam) throws Exception{
			try {
				ValueObject valueObject = new ValueObject(allReqParam);
				return requisitionService.searchRequisitionByNumber(valueObject).getHtData();
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}

		@GetMapping(value ="/showPartRequisitionPrint")
		public ModelAndView showPartRequisitionPrint(@RequestParam("requisitionId")final Long requisitionId) throws Exception {
			try {
				return new ModelAndView("showPartRequisitionPrint",requisitionService.getPartRequsitionPrintData(requisitionId));
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}


}
