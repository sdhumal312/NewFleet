package org.fleetopgroup.persistence.service;

import java.util.List;
import java.util.function.Predicate;

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.persistence.bl.VehicleBL;
import org.fleetopgroup.persistence.bl.VehicleInspectionBL;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InspectionCompletionToParameterDto;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.serviceImpl.IInspectionCompletionToParameterService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleGroupService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionParameterService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleInspectionReportService;
import org.fleetopgroup.persistence.serviceImpl.IVehicleService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class VehicleInspectionReportService implements IVehicleInspectionReportService {
	
	private @Autowired	IInspectionCompletionToParameterService		inspectionCompletionToParameterService;
	private @Autowired	IVehicleService								vehicleService;
	private @Autowired	IVehicleGroupService						vehicleGroupService;
	private @Autowired	IVehicleInspectionParameterService			vehicleInspectionParameterService;
	
	VehicleBL VBL = new VehicleBL();

	VehicleInspectionBL	inspectionBL	= new VehicleInspectionBL();
	
	@Override
	public ValueObject getVehicleWiseInspctionReport(ValueObject valueObject) throws Exception {
		List<InspectionCompletionToParameterDto>		parameterList					= null;
		String											fromDate						= null;
		String											toDate							= null;
		ValueObject										dateRangeObj					= null;
		String											query							= "";
		CustomUserDetails								userDetails						= null;
		try {
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRangeObj			= DateTimeUtility.getStartAndEndDateStr(valueObject.getString("dateRange"));
			fromDate				= dateRangeObj.getString(DateTimeUtility.FROM_DATE);
			toDate					= dateRangeObj.getString(DateTimeUtility.TO_DATE);
			short testResult		= valueObject.getShort("testResult", (short) 0);
			fromDate = DateTimeUtility.getSqlStrDateFromStrDate(fromDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
			toDate   = DateTimeUtility.getSqlStrDateFromStrDate(toDate.trim(), DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			
			if(valueObject.getLong("inspectedBy", 0) > 0) {
				query += " AND ICP.inspectedById = "+valueObject.getLong("inspectedBy", 0)+"";
			}
			if(valueObject.getInt("frequency", 0) > 0) {
				query += " AND VP.frequency = "+valueObject.getInt("frequency", 0)+"";
			}
			
			if(testResult > 0 && testResult < 3) {
				query += " AND ICP.isInspectionSuccess = "+testResult+"";
			}
			if(testResult > 0 && testResult == 3) {
				query += " AND ICP.isInspectionSuccess IN (1,2)";
			}
			if(testResult > 0 && testResult == 4) {
				query += " AND ICP.isInspectionSuccess = 0 ";
			}
			
			InspectionCompletionToParameterDto	parameterDto	= new InspectionCompletionToParameterDto();
			
			parameterDto.setVid(valueObject.getInt("vid", 0));
			parameterDto.setFromDate(fromDate);
			parameterDto.setToDate(toDate);
			
			parameterList	=	inspectionCompletionToParameterService.getVehicleInspectionReportDetails(parameterDto, query, vehicleInspectionParameterService.getInspectionParameterHM(userDetails.getCompany_id()));
			
			if(valueObject.getLong("inspectionParameter", 0) > 0) {
				Predicate<InspectionCompletionToParameterDto> personPredicate = p-> p.getInspectionParameterId() != valueObject.getLong("inspectionParameter", 0);
				parameterList.removeIf(personPredicate);
			}
			
			VehicleDto vehicle = VBL.prepare_Vehicle_Header_Fuel_Entries_Show(vehicleService.Get_Vehicle_Header_Fuel_Entries_Details(valueObject.getInt("vid", 0), userDetails.getCompany_id()));
			
			valueObject.put("parameterList", parameterList);
			valueObject.put("vehicle", vehicle);
			valueObject.put("details", inspectionBL.getVehicleInspectionReportDetails(parameterList, valueObject));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			fromDate						= null;
			toDate							= null;
			dateRangeObj					= null;
			parameterList					= null;
		}
	}
	
	@Override
	public ValueObject getGroupWiseInspctionReport(ValueObject valueObject) throws Exception {
		List<InspectionCompletionToParameterDto>		parameterList					= null;
		String											fromDate						= null;
		String											query							= "";
		ValueObject					tableConfig				= null;
		try {
			fromDate				= valueObject.getString("dateRange");
			
			CustomUserDetails	userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			short testResult		= valueObject.getShort("testResult", (short) 0);
			
			if(valueObject.getLong("inspectedBy", 0) > 0) {
				query += " AND ICP.inspectedById = "+valueObject.getLong("inspectedBy", 0)+"";
			}
			if(valueObject.getInt("frequency", 0) > 0) {
				query += " AND VP.frequency = "+valueObject.getInt("frequency", 0)+"";
			}
			
			if(testResult > 0 && testResult < 3) {
				query += " AND ICP.isInspectionSuccess = "+testResult+"";
			}
			if(testResult > 0 && testResult == 3) {
				query += " AND ICP.isInspectionSuccess IN (1,2)";
			}
			if(testResult > 0 && testResult == 4) {
				query += " AND ICP.isInspectionSuccess = 0 ";
			}
			
			InspectionCompletionToParameterDto	parameterDto	= new InspectionCompletionToParameterDto();
			
			tableConfig				= new ValueObject();
			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.GROUP_WISE_INSPECTION_REPORT_DATA_FILE_PATH);

			tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
			
			parameterDto.setVid(valueObject.getInt("vid", 0));
			parameterDto.setFromDate(DateTimeUtility.getStartOfDayTimeStamp(fromDate)+"");
			parameterDto.setToDate(DateTimeUtility.getEndOfDayTimeStamp(fromDate)+"");
			parameterDto.setVehicleGroupId(valueObject.getInt("vehicleGroupId", 0));
			
			parameterList	=	inspectionCompletionToParameterService.getGroupInspectionReportDetails(parameterDto, query, vehicleInspectionParameterService.getInspectionParameterHM(userDetails.getCompany_id()));
			if(valueObject.getLong("inspectionParameter", 0) > 0) {
				Predicate<InspectionCompletionToParameterDto> personPredicate = p-> p.getInspectionParameterId() != valueObject.getLong("inspectionParameter", 0);
				parameterList.removeIf(personPredicate);
			}
			valueObject.put("parameterList", parameterList);
			valueObject.put("vehicleGroup", vehicleGroupService.getVehicleGroupByID(valueObject.getInt("vehicleGroupId", 0)));
			valueObject.put("details", inspectionBL.getVehicleInspectionReportDetails(parameterList, valueObject));
			valueObject.put("tableConfig", tableConfig.getHtData());
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			fromDate						= null;
			parameterList					= null;
		}
	}
}
