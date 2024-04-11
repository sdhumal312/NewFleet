package org.fleetopgroup.rest.controller;

import java.util.HashMap;

import org.fleetopgroup.persistence.dto.BranchDto;
import org.fleetopgroup.persistence.serviceImpl.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartPrintController {

	
@Autowired IBranchService branchService;
	
	@RequestMapping(value = "/saveBranchFromWS", method = RequestMethod.POST)
	public HashMap<Object,Object> saveBranchFromWS(@RequestBody BranchDto branchDto) {
		System.out.println(branchDto);
		return branchService.saveBranch(branchDto).getHtData() ;
	}
	
	@RequestMapping(value = "/updateBranchFromWS", method = RequestMethod.POST)
	public HashMap<Object,Object> updateBranchFromWS(@RequestBody BranchDto branchDto) throws Exception {
		System.out.println(branchService);
		return branchService.updateBranch(branchDto).getHtData() ;
	}

}


