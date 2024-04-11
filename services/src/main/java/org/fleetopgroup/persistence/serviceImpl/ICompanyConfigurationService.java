package org.fleetopgroup.persistence.serviceImpl;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.model.CompanyConfiguration;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public interface ICompanyConfigurationService {

	public static final String VEHICLE_GROUP_WISE_PERMISSION		= "vehicleGroupWisePermission";
	public static final String CASH_BOOK_WISE_PERMISSION			= "cashBookWisePermission";
	public static final String PART_LOCATION_WISE_PERMISSION		= "partLocationWisePermission";
	
	public static final String CASHBOOK_CLOSED_DATE_VIEW			= "cashBookClosedDateView";
	public static final String SUB_ROUTE_TYPE_NEEDED				= "subRouteTypeNeeded";
	public static final String VEHICLE_GROUP_WISE_ROUTE				= "vehicleGroupWiseRoute";
	public static final String VEHICLE_GROUP_WISE_PER_IN_RENEWAL	= "vehicleGroupWisePerInRenewal";
	public static final String SERVICE_REMINDER_LIST_BY_DATE		= "serviceReminderListByDate";
	public static final String IS_OTP_VALIDATION_AT_LOGIN			= "isOTPValidationAtLogin";
	public static final String SUB_PART_LOCATION_TYPE_NEEDED		= "subPartLocationTypeNeeded";
	
	public HashMap<String, Object> getCompanyConfiguration(Integer companyId, Integer filter) throws Exception;
	
	public boolean	getVehicleGroupWisePermission(Integer companyId, Integer filter) throws Exception;
	
	public boolean	getVehicleGroupWiseRoutePermission(Integer companyId, Integer filter) throws Exception;
	
	public boolean	getVehicleGroupWisePerInRenewal(Integer companyId, Integer filter) throws Exception;
	
	public boolean	getCashBookWisePermission(Integer companyId, Integer filter) throws Exception;
	
	public boolean	getPartLocationWisePermission(Integer companyId, Integer filter) throws Exception;
	
	public int getTripSheetFlavor(Integer companyId, Integer filter) throws Exception;
	
	public int getCashBookClosedDateView(Integer companyId, Integer filter) throws Exception;
	
	public int getServiceReminderListByDate(Integer companyId, Integer filter) throws Exception;
	
	public int getFastTageExpenseId(Integer companyId, Integer filter) throws Exception;
	
	public void cacheImporttantConfiguration(Integer companyId) throws Exception;
	
	public void refreshAllConfiguration() throws Exception;
	
	public HashMap<String, Object> cacheConfiguration(Integer companyId, Integer filter) throws Exception;

	public boolean loadConfigFileAndSaveToDB(Integer moduleId) throws Exception;

	public HashMap<String, Object> getCompanyConfigurationFromDB(Integer companyId, Integer filter) throws Exception;

	public List<CompanyConfiguration> getCompanyConfigurationList(Integer companyId, Integer filter) throws Exception;

	
}
