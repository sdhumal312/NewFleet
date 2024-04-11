package org.fleetopgroup.persistence.serviceImpl;

import org.fleetopgroup.web.util.ValueObject;

public interface IUserActivityReportService {
	
	public ValueObject getUSerWiseWOActivityCount(ValueObject object) throws Exception;
	
	public ValueObject getUserWiseServiceEntryCount(ValueObject object) throws Exception;
	
	public ValueObject getUserWiseTripSheetActivityCount(ValueObject object) throws Exception;
	
	public ValueObject getUserWiseFuelEntryActivityCount(ValueObject object) throws Exception;
	
	public ValueObject	getUserWiseRRActivityCount(ValueObject object) throws Exception ;
	
	public ValueObject getUserWiseIssuesCount(ValueObject object) throws Exception;
	
	public ValueObject	getActivityWOData(ValueObject object) throws Exception;
	
	public ValueObject	getActivitySEData(ValueObject object) throws Exception; 
	
	public ValueObject	getActivityTSData(ValueObject object) throws Exception;
	
	public ValueObject	getActivityFEData(ValueObject object) throws Exception;
	
	public ValueObject	getActivityRRData(ValueObject object) throws Exception ;
	
	public ValueObject getActivityIssuesData(ValueObject object) throws Exception;
	
	public ValueObject getUserWiseCount(ValueObject object);
	
	public ValueObject getUserWisePoData(ValueObject object) ;
	
	 public ValueObject getUserWiseDSEData(ValueObject object);
	

}
