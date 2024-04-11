package com.fleetop.gpsservice.service;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.fleetop.gpsservice.serviceImpl.ICompanyConfigurationService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class ConfiguarationCacheService {
	

	 private LoadingCache<String, HashMap<String, Object>>   configurationCache;
	 
	 @Autowired @Lazy private ICompanyConfigurationService		companyConfigurationService;

	    public ConfiguarationCacheService() {
	        super();
	        configurationCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, HashMap<String, Object>>() {
	            @Override
	            public HashMap<String, Object> load(final String key) {
	                return new HashMap<>();
	            }
	        });
	    }
	    
	    
//		public void cacheImporttantConfiguration(Integer companyId) throws Exception {
//			try {
//				
//				if(!isCompanyConfigurationExists(companyId+"")) {
//					
//					configurationCache.put(companyId+"_"+PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG, companyConfigurationService.cacheConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG));
//					configurationCache.put(companyId+"_"+PropertyFileConstants.FUEL_CONFIGURATION_CONFIG, companyConfigurationService.cacheConfiguration(companyId, PropertyFileConstants.FUEL_CONFIGURATION_CONFIG));
//					configurationCache.put(companyId+"_"+PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG, companyConfigurationService.cacheConfiguration(companyId, PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG));
//					configurationCache.put(companyId+"_"+PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG, companyConfigurationService.cacheConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG));
//					configurationCache.put(companyId+"_"+PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG, companyConfigurationService.cacheConfiguration(companyId, PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG));
//					configurationCache.put(companyId+"_"+PropertyFileConstants.VEHICLE_GPS_CONFIG, companyConfigurationService.cacheConfiguration(companyId, PropertyFileConstants.VEHICLE_GPS_CONFIG));
//					configurationCache.put(companyId+"_"+PropertyFileConstants.RENEWAL_REMINDER_CONFIG, companyConfigurationService.cacheConfiguration(companyId, PropertyFileConstants.RENEWAL_REMINDER_CONFIG));
//					
//					configurationCache.put(companyId+"", new HashMap<String, Object>());
//					
//				}
//				
//			} catch (Exception e) {
//				throw e;
//			}
//		}
		
		public HashMap<String, Object>  getConfiguration(String key) throws Exception{
			return configurationCache.get(key);
		}
		
		public void invalidateAll() {
			configurationCache.invalidateAll();
		}
		
		public boolean isCompanyConfigurationExists(String key) throws ExecutionException {
			try {
				if(configurationCache.getIfPresent(key) != null) {
					return true;
				}
				return false;
			} catch (Exception e) {
				return false;
			}
		}
		
//		public void refreshConfiguration(Integer companyId) throws Exception{
//			invalidateCacheForCompany(companyId);
//			cacheImporttantConfiguration(companyId);
//		}
		
		public void invalidateCacheForCompany(Integer companyId){
			configurationCache.invalidate(companyId+"");		
		}

}
