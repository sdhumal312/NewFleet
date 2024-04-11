package org.fleetopgroup.persistence.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.WorkOrdersType;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.InventoryDto;
import org.fleetopgroup.persistence.dto.InventoryLocationDto;
import org.fleetopgroup.persistence.dto.RequisitionDto;
import org.fleetopgroup.persistence.dto.WorkOrdersDto;
import org.fleetopgroup.persistence.dto.WorkOrdersTasksToPartsDto;
import org.fleetopgroup.persistence.report.dao.IPartReportDao;
import org.fleetopgroup.persistence.serviceImpl.IInventoryService;
import org.fleetopgroup.persistence.serviceImpl.IPartReportService;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionService;
import org.fleetopgroup.persistence.serviceImpl.IUserProfileService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PartReportService implements IPartReportService {
	
	@Autowired 	IPartReportDao		partReportDao;
	@Autowired	IUserProfileService	userProfileService;
	@Autowired
	private 	IInventoryService 	InventoryService;
	@Autowired  IRequisitionService RequisitionService;
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Override
	public ValueObject getPartWiseConsumtionReport(ValueObject valueObject ) throws Exception {
		CustomUserDetails						userDetails					= null;
		String 									dataArrayObj 				= null;
		String									dateRange					= null;
		String[] 								From_TO_DateRange 			= null;
		String 									dateRangeFrom 				= null;
		String 									dateRangeTo 				= null;
		String 									query						= null;
		short									serviceTypeId				= 0;
		Integer									vid							= 0;
		List<WorkOrdersTasksToPartsDto>			tasksToPartsDtos			= null;
		List<WorkOrdersTasksToPartsDto>			seTasksToPartsDtos			= null;
		ValueObject								tableConfig					= null;
		
		try {
			dataArrayObj			= valueObject.getString("partId");
			
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRange				= valueObject.getString("dateRange");
			serviceTypeId			= valueObject.getShort("serviceTypeId", (short) 0);
			vid						= valueObject.getInt("vid", 0);
			
			if(dateRange != null) {
				
				From_TO_DateRange = dateRange.trim().split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				
				if(dataArrayObj != "") {
					query 			=" WTP.partid IN ("+dataArrayObj+") AND WO.completed_date between '"+dateRangeFrom+"' AND '"+dateRangeTo+"' ";
				}else {
					query 			=" WO.completed_date between '"+dateRangeFrom+"' AND '"+dateRangeTo+"' ";
				}
				if(vid != null && vid > 0) {
					query	+= " AND V.vid = "+vid+" ";
				}
				tableConfig			= new ValueObject();
				
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.PART_WISE_CONSUMPTION_TABLE_DATA_FILE_PATH);
				
				tableConfig			= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig			= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				
				if(serviceTypeId < 0) {
					tasksToPartsDtos	=	partReportDao.getWorkOrderPartConsumptionList(query, userDetails.getCompany_id());
					seTasksToPartsDtos	=	partReportDao.getServiceEntiresPartConsumptionList(query, userDetails.getCompany_id());
					if((seTasksToPartsDtos != null && !seTasksToPartsDtos.isEmpty()) && (tasksToPartsDtos != null && !tasksToPartsDtos.isEmpty())) {
						
						tasksToPartsDtos.addAll(seTasksToPartsDtos);
					}else if(tasksToPartsDtos == null || tasksToPartsDtos.isEmpty()) {
						tasksToPartsDtos = seTasksToPartsDtos;
					}
				}
				
				else if(serviceTypeId == 1) {
					tasksToPartsDtos	=	partReportDao.getWorkOrderPartConsumptionList(query, userDetails.getCompany_id());
				}
				
				else if(serviceTypeId == 2) {
					tasksToPartsDtos	=	partReportDao.getServiceEntiresPartConsumptionList(query, userDetails.getCompany_id());
				}
				
					
			}
			
			valueObject.put("tasksToPartsDtos", tasksToPartsDtos);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			tasksToPartsDtos	= null;
			seTasksToPartsDtos	= null;
			userDetails			= null;
			dateRange			= null;
		}
	}
	
	@Override
	public ValueObject getPartPurchaseInvoiceReport(ValueObject valueObject) throws Exception {
		String							dateRange				= null;
		int								vendorId				= 0;
		int								partId					= 0;
		CustomUserDetails				userDetails				= null;
		List<InventoryDto> 				Inven					= null;
		try {
			HashMap<Long, List<InventoryDto>>  map = new HashMap<Long, List<InventoryDto>>();
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRange		= valueObject.getString("dateRange");
			vendorId		= valueObject.getInt("vendorId", 0);
			partId			= valueObject.getInt("partId", 0);
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				
				String Vendor_Name = "", Part_name = "", Date = "";
				
				if(vendorId > 0 )
				{
					Vendor_Name = " AND I.vendor_id = "+ vendorId +" ";
				}
				
				if(partId != 0)
				{
					Part_name = " AND I.partid = "+ partId +" ";
					
				}
				
				Date =  " AND I.invoice_date between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;
				String query = " " + Vendor_Name + " " + Part_name + " " + Date + " ";
				
				//Below Commented Code is of SlickGrid. 
				/*tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.PART_PURCHASE_INVOICE_TABLE_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);*/
				
				Inven = InventoryService.Part_Purchase_Invoice_Report(query, userDetails.getCompany_id());
				
				
				List<InventoryDto> partDataList	= null;
				
				if(Inven != null && !Inven.isEmpty()) {
					
					InventoryDto tempDto = null;
					for (InventoryDto inventoryDto : Inven) {
						
						tempDto = inventoryDto;
						tempDto.setPartname(inventoryDto.getPartnumber()+"_"+inventoryDto.getPartname());
						
						partDataList = map.get(inventoryDto.getPartInvoiceId());
						if(partDataList == null || partDataList.isEmpty()) {
							partDataList = new ArrayList<InventoryDto>();
						}
						
						partDataList.add(tempDto);
						map.put(inventoryDto.getPartInvoiceId(), partDataList);
						
					}
				}

			}
			valueObject.put("PartPurchaseInvoiceReport", Inven);
			valueObject.put("partDetailsMap", map);
			if(vendorId > 0 ) {
				if(Inven != null)
				valueObject.put("VendorName", Inven.get(0).getVendor_name());
			} else {
			valueObject.put("VendorName", "All");
			}
			if(partId != 0  ) {
				if(Inven != null)
				valueObject.put("PartName", Inven.get(0).getPartnumber()+"-"+Inven.get(0).getPartname());
			} else {
				valueObject.put("PartName", "All");
			}
			
			/*valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",
					userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));*/
			
			return valueObject;
		} catch (Exception e) {
			LOGGER.error("Exception", e);
			throw e;
		}finally {
			Inven				= null;
			userDetails			= null;
			dateRange			= null;
		}
	}
	
	//Technician Wise Part Report Start
	@Override
	public ValueObject getTechnicianWisePartReport(ValueObject valueObject) throws Exception {
		String						dateRange				= null;		
		CustomUserDetails			userDetails				= null;
		List<WorkOrdersDto>			technician				= null;		
		ValueObject					tableConfig			    = null;
		int 						technicianId            = 0 ;
		int							partId				    = 0 ;		
		short 						workordercomplete 		= WorkOrdersType.WORKORDERS_STATUS_COMPLETE;		 
		try {
			userDetails		= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			dateRange		= valueObject.getString("dateRange");
			technicianId	= valueObject.getInt("technicianId", 0);
			partId			= valueObject.getInt("partId", 0);
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;			

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

				String Technician_Name = "", Part_name = "" , Date = "", WorkorderComplete = "";				
				
				if(technicianId > 0 )
				{
					Technician_Name = " AND WOLAB.laberid = "+ technicianId +" ";
				}
				
				if(partId != 0)
				{
					Part_name = " AND WOPA.partid= "+ partId +" ";				
				}
				
				Date			   =  " AND W.completed_date between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' " ;					
				WorkorderComplete  =  " AND W.workorders_statusId= "+workordercomplete+" ";						
				String query       = " " + Technician_Name + " " + Part_name+ " " + Date + " "+ WorkorderComplete +" ";
				
				tableConfig		= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.TECHNICIAN_WISE_PART_TABLE_DATA_FILE_PATH);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);				
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);				
				technician	    = partReportDao.getTechnicianWisePartReportList(query, userDetails.getCompany_id());
				
			}
			
			valueObject.put("technician", technician);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",
			userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			technician	= null;			
			userDetails			= null;
			dateRange			= null;
		}
	}
	//Technician Wise Part Report Stop
	@Override
	public ValueObject getPartStockReport(ValueObject valueObject ) throws Exception {
		CustomUserDetails						userDetails					= null;
		String 									partID						= null;
		long									partLocation				= 0;	
		String 									query						= null;
		List<InventoryLocationDto> 				Inven						= null;
		ValueObject								tableConfig					= null;
		
		try {

			partID					= valueObject.getString("partId","");

			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			partLocation			= valueObject.getLong("Partwarehouselocation",0);

			if(!partID.isEmpty() && partLocation > 0) {
				query 				= " R.location_quantity > 0.0 AND R.partid IN (" + partID + ") AND R.locationId = " + partLocation + " ";
			}else if( partLocation <= 0 && !partID.isEmpty()) {
				query 				 = " R.location_quantity > 0.0 AND R.partid IN (" + partID + ") "; 
			}else if(partID.isEmpty() && partLocation > 0) {
				query				= " R.location_quantity > 0.0 AND R.locationId = " + partLocation + " ";
			}else {
				query 				 = " R.location_quantity > 0.0 ";  
			}
			
			Inven = InventoryService.Report_list_Of_Location_Inventory(query, userDetails.getCompany_id());
			tableConfig			= new ValueObject();

			tableConfig.put("companyId", userDetails.getCompany_id());
			tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.PART_STOCK_TABLE_DATA_FILE_PATH);

			tableConfig			= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
			tableConfig			= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);


			/*Double Total 	= InventoryService.Report_list_Of_Location_Inventory_Total_Inventory_Amount(Inventoryquery, userDetails.getCompany_id());
		TotalValue 		= AMOUNTFORMAT.format(round(Total, 0));*/

			valueObject.put("Inven", Inven);
			valueObject.put("tableConfig", tableConfig.getHtData());
			valueObject.put("company",userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));


			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			userDetails			= null;
		}
	}
	
	@Override
	public ValueObject getPartRequisitionStatusWiseReport(ValueObject valueObject ) throws Exception {
		CustomUserDetails						userDetails					= null;
		int  									userId						= 0;
		int 									locationId					= 0;
		int 									statusId					= 0;
		int 									reqTypeId 					= 0;
		String									dateRange				= null;
		List<RequisitionDto> 				    requisition					= null;
		ValueObject								tableConfig					= null;

		try {
			HashMap<Long, List<RequisitionDto>>  map = new HashMap<Long, List<RequisitionDto>>();
			
			userDetails				= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			userId					= valueObject.getInt("createdByUser",0);
			locationId				= valueObject.getInt("Partwarehouselocation2",0);
			statusId 				= valueObject.getInt("status",0);
			reqTypeId			    = valueObject.getInt("reqType",0);
			dateRange				= valueObject.getString("PartInventryRange");
			
			if(dateRange != null) {
				String dateRangeFrom = "", dateRangeTo = "";
				String[] From_TO_DateRange = null;

				From_TO_DateRange = dateRange.split(" to ");

				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				
				StringBuilder	query	= new StringBuilder();
				query.append("And  R.creationOn between ' "+ dateRangeFrom + "' AND '" + dateRangeTo +" '" );
				
				if(userId > 0)
					query.append(" AND u.id = "+ userId +" ");
				if(statusId > 0)
					query.append(" AND R.requisitionStatusId = " + statusId +" ");
				if(locationId > 0 )
					query.append(" AND R.location = "+  locationId +" ");
				if(reqTypeId > 0)
					query.append(" AND SR.requisitionTypeId = "+ reqTypeId +" ");
				
				requisition = RequisitionService.getPartRequisitionStatusWiseReport(query.toString(), userDetails.getCompany_id());
			
				if(requisition != null  && !requisition.isEmpty())
				{
					requisition.forEach(e->e.setRequisitionNumStr("<a style=\"color:blue\" href=\"showRequisition?requisitionId="+e.getRequisitionId()+"\"' target='_blank'>R-"+e.getRequisitionNum()+"</a>"));
				
					tableConfig			= new ValueObject();
					tableConfig.put("companyId", userDetails.getCompany_id()+"");
					tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.PART_REQUISITION_STATUS_TABLE_DATA_FILE_PATH);
					tableConfig			= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
					tableConfig			= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
					
					valueObject.put("requisition", requisition);
					valueObject.put("tableConfig", tableConfig.getHtData());
					valueObject.put("company",userProfileService.findUserProfileByUser_email_Company_ESI_PF_DIABLE(userDetails.getId()));
				}
			}
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			tableConfig			= null;
			userDetails			= null;
		}
	
	}
}
