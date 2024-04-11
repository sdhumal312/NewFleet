package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;

public interface ISummaryService{
	public ValueObject getAllCountDetails(ValueObject valueObject) throws Exception;
	
	public ValueObject getTripsheetCount(ValueObject object)throws Exception;// All Count Tripsheet 1
	
	public ValueObject getFuelCount(ValueObject object)throws Exception;// All Count Fuel 2
	
	public ValueObject getWorkOrderCount(ValueObject object)throws Exception;// All Count WorkOrder 3
	
	public ValueObject getServiceReminderCount(ValueObject object)throws Exception;// All Count ServiceReminder 4
	
	public ValueObject getRRCount(ValueObject object)throws Exception;// All Count RR 5
	
	public ValueObject getIssueCount(ValueObject object)throws Exception;// All Count Issue 6
	
	public ValueObject getServiceEntryCount(ValueObject object)throws Exception;// All Count ServiceEntry 7
	
	public ValueObject getPickAndDropCount(ValueObject valueObject) throws Exception; // All Count PickAndDrop 8
	
	/*********Status Wise Data Count And Table Data Details*************/

	public ValueObject getWorkOrderDataCount(ValueObject valueObject) throws Exception;

	public ValueObject getLocationWiseWorkOrderDataCount(ValueObject object)throws Exception;

	public ValueObject getWorkOrderTableData(ValueObject object)throws Exception;
	
	public ValueObject getIssueDataCount(ValueObject object)throws Exception;
	
	public ValueObject getIssueTableData(ValueObject object)throws Exception;
	
	public ValueObject getTripSheetDataCount(ValueObject valueObject) throws Exception;
	
	public ValueObject getTripSheetTableData(ValueObject valueObject) throws Exception;
	
	public ValueObject getRenewalReminderDataCount(ValueObject valueObject) throws Exception;
	
	public ValueObject getRenewalReminderTableData(ValueObject valueObject) throws Exception;
	
	public ValueObject getServiceReminderDataCount(ValueObject valueObject) throws Exception;
	
	public ValueObject getServiceReminderTableData(ValueObject valueObject) throws Exception;
	
	public ValueObject getFuelDataCount(ValueObject valueObject) throws Exception;
	
	public ValueObject getFuelTableData(ValueObject valueObject) throws Exception;

	public ValueObject getServiceEntryDataCount(ValueObject object)throws Exception;

	public ValueObject getServiceEntryTableData(ValueObject object)throws Exception;
	
	public ValueObject getPickAndDropDataCount(ValueObject valueObject) throws Exception;
	
	public ValueObject getPickAndDropTableData(ValueObject valueObject) throws Exception;

	public ValueObject tyreStockCount(ValueObject object) throws Exception;

	public ValueObject getTyreStockDetails(ValueObject object) throws Exception;

	public ValueObject getAllLocationTyreStockDetailsByStatus(ValueObject valueObject) throws Exception;

	public ValueObject getAssignedTyreAllocation(ValueObject object)throws Exception;

	public ValueObject getMaxTyreRun(ValueObject object)throws Exception;
	public ValueObject getMinTyreRun(ValueObject object)throws Exception;

	public ValueObject getVehicleWithoutTyre(ValueObject object)throws Exception;

	public ValueObject getIssueTyreDetails(ValueObject object)throws Exception;
	
	
	
	
}