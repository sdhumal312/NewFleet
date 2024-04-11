package org.fleetopgroup.persistence.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.VehicleDto;
import org.fleetopgroup.persistence.serviceImpl.IServiceEntriesService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.persistence.serviceImpl.IWareHouseLocationService;
import org.fleetopgroup.persistence.serviceImpl.IWorkOrdersService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class WareHouseLocationService implements IWareHouseLocationService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired	IUserProfileService					userProfileService;
	@Autowired	IServiceEntriesService				ServiceEntriesService;
	@Autowired	IWorkOrdersService					WorkOrdersService;
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy"); 
	SimpleDateFormat dateFormatSQL = new SimpleDateFormat("yyyy-MM-dd"); 
	
	@Override
	public ValueObject getWareHouseLocationWiseCostReport(ValueObject valueObject) throws Exception {
		String							dateRange				= null;		
		CustomUserDetails				userDetails				= null;
		List<VehicleDto>				workOrdersList			= null;		
		List<VehicleDto>				workOrdersDetailsList	= null;		
		HashMap<Integer, VehicleDto> 	finalMapOfVehicle 		= null;
		ValueObject						tableConfig			    = null;
		VehicleDto 						vehicleDto 				= null;
		VehicleDto 						tempDate 				= null;
		int 							locationId           	= 0 ;
		List<VehicleDto>				vehicleWiseList			= null;	
		
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			locationId		= valueObject.getInt("locationId", 0);
			dateRange		= valueObject.getString("dateRange");
			
			if(dateRange != null) {
				
				tableConfig		= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.WAREHOUSE_LOCATION_WISE_COST_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);				
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);				
				
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");
				dateRangeFrom = dateFormatSQL.format(dateFormat.parse(From_TO_DateRange[0])) ;
				dateRangeTo = dateFormatSQL.format(dateFormat.parse(From_TO_DateRange[1]))+" "+DateTimeUtility.DAY_END_TIME;			
				
				String locationName = "", WorkOrderDate = "";				
				
				if(locationId > 0 )
				{
					locationName = " AND PL.partlocation_id = "+ locationId +" ";
				}
				
				WorkOrderDate   	   =  " AND WO.start_date between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;					
				String WorkOrderQuery  =  " " + locationName + "  " + WorkOrderDate + "  ";
				
				workOrdersList = WorkOrdersService.getVehicleWiseWorkOrders(WorkOrderQuery, userDetails.getCompany_id());
				
				workOrdersDetailsList	= new ArrayList<VehicleDto>();
				if(workOrdersList != null)
				workOrdersDetailsList.addAll(workOrdersList);
				valueObject.put("WorkOrdersList", workOrdersDetailsList);
				
				finalMapOfVehicle	= new HashMap<Integer, VehicleDto>();
				if(workOrdersList != null) {
					for(int i = 0; i < workOrdersList.size(); i++) {
						
						if(finalMapOfVehicle.containsKey(workOrdersList.get(i).getVid())) {
							vehicleDto			= finalMapOfVehicle.get(workOrdersList.get(i).getVid());
							
							tempDate = new VehicleDto();
							tempDate.setVid(workOrdersList.get(i).getVid());
							tempDate.setVehicle_registration(workOrdersList.get(i).getVehicle_registration());
							tempDate.setPartlocation_name(workOrdersList.get(i).getPartlocation_name());
							tempDate.setVehicleWorkOrdersCount(vehicleDto.getVehicleWorkOrdersCount() + 1);
							tempDate.setTotalworkorder_cost(vehicleDto.getTotalworkorder_cost() + workOrdersList.get(i).getTotalworkorder_cost());
							
						} else {
							tempDate = new VehicleDto();
							tempDate.setVid(workOrdersList.get(i).getVid());
							tempDate.setVehicle_registration(workOrdersList.get(i).getVehicle_registration());
							tempDate.setPartlocation_name(workOrdersList.get(i).getPartlocation_name());
							tempDate.setVehicleWorkOrdersCount(1);
							tempDate.setTotalworkorder_cost(workOrdersList.get(i).getTotalworkorder_cost());
						}
						finalMapOfVehicle.put(workOrdersList.get(i).getVid(), tempDate);
					}
				}
				
				if(finalMapOfVehicle != null && !finalMapOfVehicle.isEmpty()) {
					vehicleWiseList	= new ArrayList<VehicleDto>();
					vehicleWiseList.addAll(finalMapOfVehicle.values());
					
					valueObject.put("VehicleWiseList", vehicleWiseList);
					valueObject.put("tableConfig", tableConfig.getHtData());
					valueObject.put("company", userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
				}
				
				
			}
			
			return valueObject;
			
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			throw e;
		}finally {
			tableConfig			= null;
			userDetails			= null;
			dateRange			= null;
		}
	}
	
	
}