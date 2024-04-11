package org.fleetopgroup.persistence.serviceImpl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDailyInspectionDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.model.VehicleDailyInspection;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.security.core.GrantedAuthority;

public interface IVehicleDailyInspectionService {
	
	public void saveVehicleDailyInspection(VehicleDailyInspection assignment) throws Exception;
	
	public List<VehicleDailyInspectionDto> getTodaysVehicleInspectionListNew(CustomUserDetails userDetail,Collection<GrantedAuthority>	permission,HashMap<String, Object> 	configuration) throws Exception;
	
	public List<VehicleDailyInspectionDto> getTodaysInspectedListNew(CustomUserDetails userDetail,Collection<GrantedAuthority>	permission,HashMap<String, Object> 	configuration) throws Exception;
	
	public List<VehicleDailyInspectionDto> getTodaysInspectionPendingListNew(CustomUserDetails userDetail,Collection<GrantedAuthority>	permission,HashMap<String, Object> 	configuration) throws Exception;
	
	public List<VehicleDailyInspectionDto> getTodaysSavedInspectedListNew(CustomUserDetails userDetail,Collection<GrantedAuthority>	permission,HashMap<String, Object> 	configuration) throws Exception;
	
	public ValueObject getMissedInspectionListByVehicle(ValueObject valueObject) throws Exception;
	
	public List<VehicleDailyInspectionDto> getMissedInspectionList(int vid, String inspectionDate, Integer companyId) throws Exception;
	
	public ValueObject runDailyInspection(ValueObject valueObject) throws Exception;
	
	public void getDailyInspectionList() throws Exception;
	
	public List<VehicleDto>  getMissedInspectedVehicleList(CustomUserDetails userDetails, Collection<GrantedAuthority> permission,
			HashMap<String, Object> configuration) throws Exception;
	
	public ValueObject  getMissedInspectedDatesByVId(ValueObject	valueObject) throws Exception;
	
	public ValueObject  skipInspectionSheet(ValueObject	valueObject) throws Exception;
	
	public List<VehicleDailyInspectionDto> getTodaysInspectionSkipedList(CustomUserDetails userDetail,Collection<GrantedAuthority>	permission,HashMap<String, Object> 	configuration) throws Exception ;
	
	public void saveAllVehicleDailyInspection(List<VehicleDailyInspection> assignment) throws Exception;
}