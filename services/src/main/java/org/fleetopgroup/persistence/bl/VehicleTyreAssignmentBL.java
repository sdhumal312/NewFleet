package org.fleetopgroup.persistence.bl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.fleetopgroup.constant.IssuesTypeConstant;
import org.fleetopgroup.persistence.model.Issues;
import org.fleetopgroup.persistence.model.IssuesTasks;
import org.fleetopgroup.web.util.ValueObject;

public class VehicleTyreAssignmentBL {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	
	LocalDateTime now = LocalDateTime.now();
	DateTimeFormatter   timeFormatter       = DateTimeFormatter.ofPattern("HH:mm");

	public ValueObject prepareIssue(ValueObject valueObject,HashMap<String, Object> 	configuration) throws Exception {
		try {
			
			valueObject.put("issueType",(short)6);
			valueObject.put("vid",valueObject.getInt("vid"));
			valueObject.put("reportdDate",valueObject.getString("assignDate"));
			valueObject.put("issueStartTime",timeFormatter.format(now));
			valueObject.put("issuesSummary","Spare Tyre Issue "+valueObject.getString("tyreNumber","")+"");
			valueObject.put("issueLabel",(short)1);
			valueObject.put("reportedById",valueObject.getLong("userId"));
			valueObject.put("subscribe",configuration.getOrDefault("issueAsignId",""));
			valueObject.put("partCategory",configuration.getOrDefault("tyreCategoryId",0));
			valueObject.put("description","Tyre Assign From Spare");
			valueObject.put("companyReference","");
			
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public ValueObject prepareDismountIssue(ValueObject valueObject, HashMap<String, Object> configuration) throws Exception {
		try {
			
			valueObject.put("issueType",(short)6);
			valueObject.put("vid",valueObject.getInt("vid"));
			valueObject.put("reportdDate",valueObject.getString("dismountDate"));
			valueObject.put("issueStartTime",timeFormatter.format(now));
			valueObject.put("issuesSummary","Tyre Dismounted");
			valueObject.put("issueLabel",(short)1);
			valueObject.put("reportedById",valueObject.getLong("userId"));
			valueObject.put("subscribe",configuration.getOrDefault("issueAsignId",""));
			valueObject.put("description","Tyre Dismounted");
			valueObject.put("companyReference","");
			
			return valueObject;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public IssuesTasks prepareIssuesTaskDetailsForTyreAssignment(Issues issue, ValueObject valueObject)throws Exception  {
		try {
			java.util.Date 	currentDateUpdate 	= new java.util.Date();
			Timestamp 		toDate 				= new java.sql.Timestamp(currentDateUpdate.getTime());
			IssuesTasks 	issuesTasks 		= new IssuesTasks();
			
			
			issuesTasks.setISSUES_TASK_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_TYRE_ASSIGNMENT);
			issuesTasks.setISSUES_CHANGE_STATUS_ID(IssuesTypeConstant.ISSUES_TASK_STATUS_RESOLVED);
			issuesTasks.setISSUES_REASON("Issue Resolved From Tyre Assignment"); 
			issuesTasks.setISSUES_CREATEBY_ID(valueObject.getLong("userId"));
			issuesTasks.setISSUES_CREATED_DATE(toDate);
			issuesTasks.setISSUES(issue);
			issuesTasks.setCOMPANY_ID(valueObject.getInt("companyId"));
			return issuesTasks;
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}
}
