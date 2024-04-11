package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.model.BankMaster;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
public class BankMasterBL {

	public BankMasterBL() {
	}

	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	SimpleDateFormat CreatedDateTime = new SimpleDateFormat("EEE, d MMM yyyy hh:mm aaa ");

	public BankMaster getBankMasterDto(BankMaster bankMaster)throws Exception {
		CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		bankMaster.setCreatedById(userDetails.getId());
		bankMaster.setLastModifiedById(userDetails.getId());
		bankMaster.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		bankMaster.setLastModifiedOn(new Timestamp(System.currentTimeMillis()));
		return bankMaster;
	}
}
