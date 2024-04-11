package org.fleetopgroup.persistence.report.dao;

import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleCheckingDetailsDto;
import org.fleetopgroup.persistence.model.VehicleCheckingDetails;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleCheckingDetailsDao {

	public List<VehicleCheckingDetailsDto> getCheckingEntryReport(VehicleCheckingDetailsDto checkingDetailsDto , String query) throws Exception;
	
	public Page<VehicleCheckingDetails> getDeployment_Page_Checking(Integer pageNumber, CustomUserDetails userDetails) throws Exception ;
	
	public List<VehicleCheckingDetailsDto> getCheckingEntryList(Integer pageNumber, CustomUserDetails userDetails) throws Exception;
	
	public List<VehicleCheckingDetailsDto> getConductorHistoryReportList(String query, Integer companyId) throws Exception;
}
