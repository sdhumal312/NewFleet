package org.fleetopgroup.persistence.service;

import java.util.List;

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.constant.RequisitionConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.RequisitionDto;
import org.fleetopgroup.persistence.serviceImpl.IRequisitionService;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class RequisitionReportService {
	
	@Autowired IRequisitionService requisitionService;
	
	
	public ValueObject getRequisitionReportDetails(ValueObject object) throws Exception {

		CustomUserDetails userDetails = null;
		ValueObject tableConfig = null;
		try {
			userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			short statusId = object.getShort("reportLabel",(short) 0);
			
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append(" R.requireOn between '"+object.getString("fromDate","")+DateTimeUtility.DAY_START_TIME+"' and '"+object.getString("toDate","")+DateTimeUtility.DAY_END_TIME+"' AND ");
			
			if(statusId > 0) {
				String statusIds="";
				if(statusId == RequisitionConstant.REQUISITION_CREATED) 
					statusIds =RequisitionConstant.REQUISITION_CREATED+","+RequisitionConstant.REQUISITION_SEND;
				else if (statusId == RequisitionConstant.REQUISITION_APPROVED)			
					statusIds =RequisitionConstant.REQUISITION_APPROVED+"";
				else
					statusIds =RequisitionConstant.REQUISITION_COMPLETE+","+RequisitionConstant.REQUISITION_MARKED_COMPLETE;
				
				queryBuilder.append(" R.requisitionStatusId IN ( "+statusIds+") AND ");
			} 
			List<RequisitionDto> requisitionList = requisitionService.getRequisitionById(object, queryBuilder.toString(), userDetails.getCompany_id());
			
			if(requisitionList != null  && !requisitionList.isEmpty())
				requisitionList.forEach(e->e.setRequisitionNumStr("<a style=\"color:blue\" href=\"showRequisition?requisitionId="+e.getRequisitionId()+"\">R-"+e.getRequisitionNum()+"</a>"));
				
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.REQUISITION_REPORT);
				tableConfig		= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig		= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);
				object.put("tableConfig", tableConfig.getHtData());
				object.put("list", requisitionList);
			return object;
		} catch (Exception e) {
			throw e;
		}
	}
}
