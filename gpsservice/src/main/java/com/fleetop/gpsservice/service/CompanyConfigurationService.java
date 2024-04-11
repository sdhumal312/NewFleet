package com.fleetop.gpsservice.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import com.fleetop.gpsservice.constant.PropertyFileConstants;
import com.fleetop.gpsservice.serviceImpl.ICompanyConfigurationService;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompanyConfigurationService implements ICompanyConfigurationService {
	
	@Autowired ConfiguarationCacheService configurationCache; 
	
	@Override
	public Map<String, Object> getCompanyConfiguration(Integer companyId, Integer filter) throws Exception{
		String						companyFileName			= null;
		String						defaultFileName			= null;
		HashMap<String, Object>		configuration			= null;
		HashMap<String, Object>		cacheConfiguration		= null;
		try {
			cacheConfiguration	= configurationCache.getConfiguration(companyId+"_"+filter) ;
			if(cacheConfiguration != null && !cacheConfiguration.isEmpty()) {
				return cacheConfiguration;
			}
			companyFileName	= getFullFilePath(companyId, filter);
			defaultFileName	= getFullDefaultFilePath( filter);
			configuration	= getConfiguration(companyFileName, defaultFileName);
			
			return configuration;
		} catch (FileNotFoundException e) {
			System.err.println("exepse  "+e);
			throw e;
		}catch (IOException e) {
			System.err.println("exepse  "+e);
			throw e;
		}catch (Exception e) {
			System.err.println("exepse  "+e);
			throw e;
		}finally {
			companyFileName		= null;
			defaultFileName		= null;
			configuration		= null;
			cacheConfiguration	= null;
		}
	}
	
	public String getFullFilePath(Integer companyId, Integer filter) throws Exception{
		String filepath		= null;
		String fileName		= null;
		try {
			 filepath = getFilePath(filter);
			 fileName = filepath +companyId+PropertyFileConstants.PROPERTIES_EXTENSION;
			return fileName;
		}catch (Exception e) {
			throw e;
		}finally {
			filepath		= null;	
			fileName		= null;
		}
	}
	
	public HashMap<String, Object> getConfiguration(String companyfileName, String defaultFileName) throws Exception{
		HashMap<String, Object> configuration			= null;
		HashMap<String, Object> defaultConfiguration	= null;
		HashMap<String, Object> companyConfiguration	= null;
		try {
			defaultConfiguration	= loadConfiguration(defaultFileName);
			companyConfiguration	= loadConfiguration(companyfileName);
			if(companyConfiguration != null) { 
			 configuration	= getFinalConfiguration(companyConfiguration, defaultConfiguration);
			}else {
				return defaultConfiguration;
			}
			return  configuration;
		} catch (Exception e) {
			throw e;
		}finally {
			defaultConfiguration	= null;
			companyConfiguration	= null;
			configuration			= null;
		}
	}
	
	public HashMap<String, Object> getFinalConfiguration(HashMap<String, Object> companyConfiguration, HashMap<String, Object> defaultConfiguration) throws Exception{
		try {
			HashMap<String, Object> configuration	= new HashMap<>();
			defaultConfiguration.forEach((key, value) -> {
				  if(companyConfiguration.get(key) == null) {
				    	configuration.put(key, value);
				  }else {
					  configuration.put(key, companyConfiguration.get(key));
				  }
			});
			return  configuration;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> loadConfiguration(String filename) throws Exception{
		Yaml					yaml		= null;
		HashMap<String, Object> data		= null;
		try {
				yaml	= new Yaml();
				  InputStream in = this.getClass().getClassLoader().getResourceAsStream(filename);
				   if(in != null) {
					 data = (HashMap<String, Object>) yaml.load(in);
				   }
				   
			return  data;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			yaml		= null;
			data		= null;
		}
	}
	
	public String getFullDefaultFilePath(Integer filter) throws Exception{
		String filepath		= null;
		String fileName		= null;
		try {
			 filepath = getFilePath(filter);
			 fileName = filepath +PropertyFileConstants.DEFAULT+PropertyFileConstants.PROPERTIES_EXTENSION;
			return fileName;
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			filepath		= null;	
			fileName		= null;
		}
	}
	
	public String getFilePath(int filter) throws Exception {
		String path = null;
		switch (filter) {

		case PropertyFileConstants.VEHICLE_GPS_CONFIG:
			path 	= PropertyFileConstants.VEHICLE_GPS_CONFIG_PATH;
			break;
		default:
			break;
		}
		return path;
		
	}

}
