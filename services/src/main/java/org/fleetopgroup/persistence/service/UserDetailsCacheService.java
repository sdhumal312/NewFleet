package org.fleetopgroup.persistence.service;

import java.util.concurrent.TimeUnit;

import org.fleetopgroup.persistence.serviceImpl.IUserDetailsCacheService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class UserDetailsCacheService implements IUserDetailsCacheService {

	
	//cache based on username and OPT MAX 8 
	 private static final Integer EXPIRE_MINS = 60;
	 private LoadingCache<Long, String> userCache;
	 
	 public UserDetailsCacheService(){
		 super();
		 userCache = CacheBuilder.newBuilder().
	     expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES).build(new CacheLoader<Long, String>() {
	      public String load(Long key) {
	             return "";
	       }
	   });
	 }
	
	@Override
	public void setOTPUserDetails(String email, String password, String companyCode, Long id) throws Exception {
		userCache.put(id, email+"_"+password+"_"+companyCode);
		
	}
	
	@Override
	public void clearUserDetails(Long key) {
		userCache.invalidate(key);
	}
	
	@Override
	public ValueObject getUserDetailsByKey(Long key) throws Exception{
		ValueObject		valueObject   = null;
		try {
			valueObject	= new ValueObject();
			String details = userCache.get(key);
			if(details != null) {
				valueObject.put("username", details.split("_")[0]);
				valueObject.put("password", details.split("_")[1]);
				valueObject.put("companyCode", details.split("_")[2]);
			}
			return valueObject;
		} catch (Exception e) {
			throw e;
		}finally {
			valueObject		= null;
		}
	}
}
