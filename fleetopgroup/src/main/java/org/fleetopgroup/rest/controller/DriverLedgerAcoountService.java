package org.fleetopgroup.rest.controller;

import java.util.List;

import org.fleetopgroup.constant.ModuleFilePathConstant;
import org.fleetopgroup.persistence.dto.CustomUserDetails;
import org.fleetopgroup.persistence.dto.DriverLedgerDto;
import org.fleetopgroup.persistence.report.dao.DriverLedgerAccountDao;
import org.fleetopgroup.web.util.DateTimeUtility;
import org.fleetopgroup.web.util.JsonConfigurationUtilityBll;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
@Service
public class DriverLedgerAcoountService {
	
	@Autowired DriverLedgerAccountDao driverLedgerAccountDao;
	

	public ValueObject getDriverLedgerAccountReport(ValueObject valueObject) throws Exception {		
		Integer							driverId							= 0;
		CustomUserDetails				userDetails							= null;
		List<DriverLedgerDto> 	        driverLedeger				        = null;		
		ValueObject						tableConfig							= null;
		String							dateRange							= null;
		String[] 						From_TO_DateRange 					= null;	
		String 							dateRangeFrom 						= null;
		String 							dateRangeTo 						= null;
		
		try {
				userDetails						= (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();			
				driverId						= valueObject.getInt("driverId", 0);
				dateRange						= valueObject.getString("dateRange");
				From_TO_DateRange				= dateRange.split(" to ");
				
				dateRangeFrom = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[0], DateTimeUtility.YYYY_MM_DD_HH_MM_SS);
				dateRangeTo   = DateTimeUtility.getSqlStrDateFromStrDate(From_TO_DateRange[1]+ DateTimeUtility.DAY_END_TIME, DateTimeUtility.YYYY_MM_DD_HH_MM_SS);

			
				StringBuilder driverStr = new StringBuilder(" AND DT.txnDateTime between '" + dateRangeFrom + "' AND '" + dateRangeTo +"' ");
				
				if(driverId > 0)
					driverStr.append(" AND DT.driverId = "+ driverId +" ");
					
					driverLedeger = driverLedgerAccountDao.getDriverLedgerAccountReportList(driverStr.toString(),userDetails.getCompany_id());
					
				
				tableConfig				= new ValueObject();
				tableConfig.put("companyId", userDetails.getCompany_id());
				tableConfig.put(JsonConfigurationUtilityBll.FILE_PATH, ModuleFilePathConstant.DRIVER_LEDGER_ACCOUNT);

				tableConfig				= JsonConfigurationUtilityBll.getConfiguration(tableConfig);
				tableConfig				= JsonConfigurationUtilityBll.getSequencedConfiguration(tableConfig);		
				
				
				
				valueObject.put("tableConfig", tableConfig.getHtData());
				valueObject.put("driverLedeger", driverLedeger);
				
			
			return valueObject;
		} catch (Exception e) {
			throw e;
		}
	}
}
	