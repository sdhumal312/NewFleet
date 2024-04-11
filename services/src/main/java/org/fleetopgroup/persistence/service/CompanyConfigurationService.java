package org.fleetopgroup.persistence.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.constant.PropertyFileConstants;
import org.fleetopgroup.constant.TollApiConfiguration;
import org.fleetopgroup.constant.TripSheetFlavorConstant;
import org.fleetopgroup.persistence.bl.CompanyConfigurationBL;
import org.fleetopgroup.persistence.dao.CompanyConfigurationRepository;
import org.fleetopgroup.persistence.model.CompanyConfiguration;
import org.fleetopgroup.persistence.serviceImpl.ICompanyConfigurationService;
import org.fleetopgroup.web.util.ValueObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.Yaml;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CompanyConfigurationService implements ICompanyConfigurationService {

	@Autowired private ConfiguarationCacheService configurationCache;
	
	CompanyConfigurationBL   companyConfigBL  = new CompanyConfigurationBL();
	
	@Autowired CompanyConfigurationRepository   companyConfigurationRepository;
	@Override
	public HashMap<String, Object> getCompanyConfiguration(Integer companyId, Integer filter) throws Exception{
		String						companyFileName			= null;
		String						defaultFileName			= null;
		HashMap<String, Object>		configuration			= null;
		HashMap<String, Object>		cacheConfiguration		= null;
		Integer                     defaultCompanyId        = 0;
		try {
			cacheConfiguration	= configurationCache.getConfiguration(companyId+"_"+filter) ;
			if(cacheConfiguration != null && !cacheConfiguration.isEmpty()) {
				return cacheConfiguration;
			}
			
			//OLD method from reading
//			companyFileName	= getFullFilePath(companyId, filter);
//			defaultFileName	= getFullDefaultFilePath( filter);
//			configuration	= getConfiguration(companyFileName, defaultFileName);
				
			
			/*	Reading  FROM Database*/
			configuration	= getConfigurationFromDB(defaultCompanyId,companyId,filter);
			
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
	
	public String getFullDefaultFilePath(Integer filter) throws Exception{
		String filepath		= null;
		String fileName		= null;
		try {
			 filepath = getFilePath(filter);
			 fileName = filepath +PropertyFileConstants.DEFAULT+PropertyFileConstants.PROPERTIES_EXTENSION;
			return fileName;
		}catch (Exception e) {
			throw e;
		}finally {
			filepath		= null;	
			fileName		= null;
		}
	}
	
	public String getFilePath(int filter) throws Exception {
		String path = null;
		switch (filter) {

		case PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.DRIVER_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.FUEL_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.FUEL_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.PENALTY_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.PENALTY_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.MASTER_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.MASTER_CONFIGURATION_CONFIG_PATH;
			break;	
		case PropertyFileConstants.CALENDER_BREAK_DOWN_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.CALENDER_BREAK_DOWN_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.VENDOR_TYPE_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.PART_INVENTORY_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.MASTER_PARTS_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.ISSUES_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.TYRE_INVENTORY_CONFIG:
			path 	= PropertyFileConstants.TYRE_INVENTORY_CONFIG_PATH;
			break;
		case PropertyFileConstants.TOLL_API_CONFIG:
			path 	= PropertyFileConstants.TOLL_API_CONFIG_PATH;
			break;
		case PropertyFileConstants.TRIP_COLLECTION_CONFIG:
			path 	= PropertyFileConstants.TRIP_COLLECTION_CONFIG_PATH;
			break;
		case PropertyFileConstants.RENEWAL_REMINDER_CONFIG:
			path 	= PropertyFileConstants.RENEWAL_REMINDER_CONFIG_PATH;
			break;
		case PropertyFileConstants.CASHBOOK_CONFIG:
			path 	= PropertyFileConstants.CASHBOOK_CONFIG_PATH;
			break;	
		case PropertyFileConstants.LHPV_INTEGRATION_CONFIG:
			path 	= PropertyFileConstants.LHPV_INTEGRATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.VEHICLE_GPS_CONFIG:
			path 	= PropertyFileConstants.VEHICLE_GPS_CONFIG_PATH;
			break;
		case PropertyFileConstants.SERVICE_REMINDER_CONFIG:
			path 	= PropertyFileConstants.SERVICE_REMINDER_CONFIG_PATH;
			break;
		case PropertyFileConstants.SMS_CONFIG:
			path 	= PropertyFileConstants.SMS_CONFIG_PATH;
			break;
		case PropertyFileConstants.UPHOLSTERY_CONFIG:
			path 	= PropertyFileConstants.UPHOLSTERY_CONFIG_PATH;
			break;
		case PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.VENDOR_CONFIGURATION_CONFIG_PATH;
			break;	
		case PropertyFileConstants.TRIP_ROUTE_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.TRIP_ROUTE_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.BUS_BOOKING_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.BUS_BOOKING_CONFIGURATION_CONFIG_PATH;
			break;			
		case PropertyFileConstants.MOBILE_APP_VERSION_CHECK_CONFIG:
			path 	= PropertyFileConstants.MOBILE_APP_VERSION_CHECK_CONFIG_PATH;
			break;	
		case PropertyFileConstants.FUEL_PRICE_CONFIG:
			path 	= PropertyFileConstants.FUEL_PRICE_CONFIG_PATH;
			break;	
		case PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.SERVICE_ENTRIES_CONFIGURATION_CONFIG_PATH;
			break;	
		case PropertyFileConstants.USER_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.USER_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.VEHICLE_REPORT_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.VEHICLE_REPORT_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.PICK_DROP_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.PICK_DROP_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.BATTERY_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.BATTERY_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.CLOTH_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.CLOTH_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.UREA_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.UREA_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.SERVICE_PROGRAM_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.SERVICE_PROGRAM_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.PURCHASE_ORDER_CONFIG:
			path 	= PropertyFileConstants.PURCHASE_ORDER_CONFIG_PATH;
			break;	
		case PropertyFileConstants.PROFIT_AND_LOSS_REPORT_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.PROFIT_AND_LOSS_REPORT_CONFIGURATION_CONFIG_PATH;
			break;
		case PropertyFileConstants.FUEL_INVOICE_CONFIG:
			path 	= PropertyFileConstants.FUEL_INVOICE_CONFIG_PATH;
			break;
		case PropertyFileConstants.INSPECTION_CONFIG:
			path 	= PropertyFileConstants.INSPECTION_CONFIG_PATH;
			break;
		case PropertyFileConstants.DSE_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.DSE_CONFIGURATION_CONFIG_PATH;
			break;	
		case PropertyFileConstants.REQUISITION_CONFIG:
			path 	= PropertyFileConstants.REQUISITION_CONFIG_PATH;
			break;	
		case PropertyFileConstants.REPAIR_CONFIG:
			path 	= PropertyFileConstants.REPAIR_CONFIG_PATH;
			break;	
		case PropertyFileConstants.LORRY_HIRE_CONFIG:
			path 	= PropertyFileConstants.LORRY_HIRE_CONFIG_PATH;
			break;
		case PropertyFileConstants.TRIPSHEET_REPORT_CONFIGURATION_CONFIG:
			path 	= PropertyFileConstants.TRIPSHEET_REPORT_CONFIGURATION_CONFIG_PATH;
			break;
		default:
			break;
		}
		return path;
		
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
			throw e;
		}finally {
			yaml		= null;
			data		= null;
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
		}finally {
			
		}
	
	}
	
	@Override
	public boolean getVehicleGroupWisePermission(Integer companyId, Integer filter) throws Exception {
		HashMap<String, Object>	configuration 	= null;
		try {
			configuration	= getCompanyConfiguration(companyId, filter);
			if(configuration != null) {
				return (boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PERMISSION);
			}else {
				return false;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public boolean getVehicleGroupWiseRoutePermission(Integer companyId, Integer filter) throws Exception {
		HashMap<String, Object>	configuration 	= null;
		try {
			configuration	= getCompanyConfiguration(companyId, filter);
			return (boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_ROUTE);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	@Override
	public boolean getVehicleGroupWisePerInRenewal(Integer companyId, Integer filter) throws Exception {
		HashMap<String, Object>	configuration 	= null;
		try {
			configuration	= getCompanyConfiguration(companyId, filter);
			return (boolean) configuration.get(ICompanyConfigurationService.VEHICLE_GROUP_WISE_PER_IN_RENEWAL);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public boolean getCashBookWisePermission(Integer companyId, Integer filter) throws Exception {
		HashMap<String, Object>	configuration 	= null;
		try {
			configuration	= getCompanyConfiguration(companyId, filter);
			return (boolean) configuration.get(ICompanyConfigurationService.CASH_BOOK_WISE_PERMISSION);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public boolean getPartLocationWisePermission(Integer companyId, Integer filter) throws Exception {
		HashMap<String, Object>	configuration 	= null;
		try {
			configuration	= getCompanyConfiguration(companyId, filter);
			return (boolean) configuration.get(ICompanyConfigurationService.PART_LOCATION_WISE_PERMISSION);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public int getTripSheetFlavor(Integer companyId, Integer filter) throws Exception {
		HashMap<String, Object>	configuration 	= null;
		try {
			configuration	= getCompanyConfiguration(companyId, filter);
			return (int) configuration.get(TripSheetFlavorConstant.TRIP_SHEET_FLAVOR);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public int getFastTageExpenseId(Integer companyId, Integer filter) throws Exception {
		HashMap<String, Object>	configuration 	= null;
		try {
			configuration	= getCompanyConfiguration(companyId, filter);
			return (int) configuration.get(TollApiConfiguration.TOLL_EXPENSE_ID);
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public int getCashBookClosedDateView(Integer companyId, Integer filter) throws Exception {
		HashMap<String, Object>	configuration 	= null;
		try {
			configuration	= getCompanyConfiguration(companyId, filter);
			return (int) configuration.get(ICompanyConfigurationService.CASHBOOK_CLOSED_DATE_VIEW);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public int getServiceReminderListByDate(Integer companyId, Integer filter) throws Exception {
		HashMap<String, Object>	configuration 	= null;
		try {
			configuration	= getCompanyConfiguration(companyId, filter);
			return (int) configuration.get(ICompanyConfigurationService.SERVICE_REMINDER_LIST_BY_DATE);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void cacheImporttantConfiguration(Integer companyId) throws Exception {
		try {
			getCompanyConfiguration(companyId, PropertyFileConstants.COMPANY_CONFIGURATION_CONFIG);
			getCompanyConfiguration(companyId, PropertyFileConstants.FUEL_CONFIGURATION_CONFIG);
			getCompanyConfiguration(companyId, PropertyFileConstants.WORK_ORDERS_CONFIGURATION_CONFIG);
			getCompanyConfiguration(companyId, PropertyFileConstants.TRIPSHEET_CONFIGURATION_CONFIG);
			getCompanyConfiguration(companyId, PropertyFileConstants.VEHICLE_CONFIGURATION_CONFIG);
			getCompanyConfiguration(companyId, PropertyFileConstants.VEHICLE_GPS_CONFIG);
			getCompanyConfiguration(companyId, PropertyFileConstants.RENEWAL_REMINDER_CONFIG);
			
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public void refreshAllConfiguration() throws Exception {
		try {
			
			configurationCache.invalidateAll();
			
			
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Override
	public HashMap<String, Object> cacheConfiguration(Integer companyId, Integer filter) throws Exception {
		String						companyFileName			= null;
		String						defaultFileName			= null;
		HashMap<String, Object>		configuration			= null;
		Integer 					defaultCompanyId		= 0;
		try {
//			companyFileName	= getFullFilePath(companyId, filter);
//			defaultFileName	= getFullDefaultFilePath( filter);
//			configuration	= getConfiguration(companyFileName, defaultFileName);
			
			//new
			configuration   = getConfigurationFromDB(defaultCompanyId,companyId,filter);
			
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
		}
	}
	
	
	
	// reading yml starts
		@Override
		public boolean loadConfigFileAndSaveToDB(Integer moduleId)throws Exception {
			
			boolean  flag = false;
			Integer size  =  0;
			System.err.println("inside load file method "+ moduleId);
			String initialPath  = "/home/shivani/IVFleet/New trunk/trunk/services/src/main/resources/";
			
			List<Map<String, Object>> yamlDataList = readAllYamlFilesInFolder(initialPath + PropertyFileConstants.getModulesPathById(moduleId));
			
			if(!yamlDataList.isEmpty() || yamlDataList == null) {
				for (Map<String, Object> dataWithFileName : yamlDataList) {
			            String fileName = (String) dataWithFileName.get("fileName");
			            Map<String, Object> yamlData = (Map<String, Object>) dataWithFileName.get("yamlData");
			            
			            size += (Integer) dataWithFileName.get("size");
			            // Do something with the file name and YAML data
			            System.err.println("\nFile Name: " + fileName);
			            System.err.println("YAML Data: " + yamlData);
			           
		            saveToTAble(fileName, yamlData, moduleId);
		            flag = true;
			     }
			}
			 return flag;
		 }
	
		@SuppressWarnings("unused")
		private void  saveToTAble(String fileName,  Map<String, Object> yamlData, Integer moduleId) {
			List<CompanyConfiguration>   companyConfigList = null;
			CompanyConfiguration		 validate 		   = null;

			Integer companyId = 0;
			Integer properties = 0;
			if (!fileName.equals("default.yml")) {
				if(fileName.split("\\.")[0].matches("\\d+")) {
					companyId = Integer.parseInt(fileName.split("\\.")[0]);
				}
			}
			companyConfigList  = companyConfigBL.prepareCompanyConfiguration(companyId,yamlData,moduleId);
			
			for(CompanyConfiguration  companyConfig : companyConfigList) {
				validate = companyConfigurationRepository.companyConfigurationAlreadyExits(companyConfig.getModuleId(), companyConfig.getCompanyId(), companyConfig.getName());
				if(validate == null) {
					System.err.println("isnide validate -- ");
					companyConfigurationRepository.save(companyConfig);
				}				properties++;
			}
			System.err.println("\nncompanyConfigList "+ companyConfigList);
		}
 	
	   public static List<Map<String, Object>> readAllYamlFilesInFolder(String folderPath) {
	        List<Map<String, Object>> yamlDataList = new ArrayList<>();
	
	        File folder = new File(folderPath);
	        File[] files = folder.listFiles(new FilenameFilter() {
	            @Override
	            public boolean accept(File dir, String name) {
	                return name.toLowerCase().endsWith(".yml");
	            }
	        });
	        if (files != null) {
	            for (File file : files) {
	                try {
	                    Map<String, Object> yamlData = readYamlFile(file);
	                    Map<String, Object> dataWithFileName = new HashMap<>();
	                    dataWithFileName.put("fileName", file.getName());
	                    dataWithFileName.put("yamlData", yamlData);
	                    dataWithFileName.put("size", yamlData.size());
	                    yamlDataList.add(dataWithFileName);
	                } catch (FileNotFoundException e) {
	                    e.printStackTrace();
	                    // Handle the exception as needed
	                }
	            }
	        }
	        return yamlDataList;
	    }

	    @SuppressWarnings("unchecked")
		private static Map<String, Object> readYamlFile(File file) throws FileNotFoundException {
	        Yaml yaml = new Yaml();
	        try (FileInputStream input = new FileInputStream(file)) {
	            return (Map<String, Object>) yaml.load(input);
	        } catch (Exception e) {
	            e.printStackTrace();
	            // Handle the exception as needed
	            return null;
	        }
	    }
	
	//ends
	
	    
	    
	//new companyConfiguration method 
	    @Override
		public HashMap<String, Object> getCompanyConfigurationFromDB(Integer companyId, Integer filter) throws Exception{
			HashMap<String, Object>		configuration			= null;
			HashMap<String, Object>		cacheConfiguration		= null;
			Integer                     defaultCompanyId        = 0;
			try {
				cacheConfiguration	= configurationCache.getConfiguration(companyId+"_"+filter) ;
				if(cacheConfiguration != null && !cacheConfiguration.isEmpty()) {
					return cacheConfiguration;
				}
				
				configuration	= getConfigurationFromDB(defaultCompanyId,companyId,filter);
				
				return configuration;
			} catch (Exception e) {
				System.err.println("exepse  "+e);
				throw e;
			}finally {
				configuration		= null;
				cacheConfiguration	= null;
			}
		}

		private HashMap<String, Object> getConfigurationFromDB(Integer defaultCompanyId, Integer companyId, Integer filter) throws Exception {
			// TODO Auto-generated method stub
			HashMap<String, Object> configuration			= null;
			HashMap<String, Object> defaultConfiguration	= null;
			HashMap<String, Object> companyConfiguration	= null;
			try {
				defaultConfiguration	= loadConfigurationFromDB(defaultCompanyId,filter);
				companyConfiguration	= loadConfigurationFromDB(companyId,filter);
				
				//System.err.println("\ndefaultConfiguration "+ defaultConfiguration);
				//System.err.println("\ncompanyConfiguration "+ companyConfiguration);
				
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

		private HashMap<String, Object> loadConfigurationFromDB(Integer companyId, Integer filter) {
			// TODO Auto-generated method stub
			List<CompanyConfiguration>   companyConfigList   = null;
			HashMap<String, Object>      configuration		 = new HashMap<String, Object>();
			
			companyConfigList = companyConfigurationRepository.getConfigurationByCompanyIdAndModuleId(companyId,filter);
			
			for(CompanyConfiguration  company : companyConfigList) {
				if(company.getValue().equals("true") || company.getValue().equals("false"))
					configuration.put(company.getName(), Boolean.parseBoolean(company.getValue()));
				else if (company.getValue().matches("\\d+")) 
					configuration.put(company.getName(), Integer.parseInt(company.getValue()));
				else if (company.getValue().matches("\\d+(\\.\\d+)?"))
					configuration.put(company.getName(), Double.parseDouble(company.getValue()));
				else 
					configuration.put(company.getName(), company.getValue());
			}
			return configuration;
		}

		
		 @Override
			public List<CompanyConfiguration> getCompanyConfigurationList(Integer companyId, Integer filter) throws Exception{
				Integer                     defaultCompanyId        = 0;
				List<CompanyConfiguration>    FinalConfigList    = null;
				List<CompanyConfiguration>    companyConfigList    = null;
				List<CompanyConfiguration>    defaultConfigList    = null;
				try {
					defaultConfigList   = companyConfigurationRepository.getConfigurationByCompanyIdAndModuleId(defaultCompanyId,filter);
					companyConfigList	= companyConfigurationRepository.getConfigurationByCompanyIdAndModuleId(companyId,filter);
					
					//System.err.println("\ndefaultConfigList -- "+ defaultConfigList);
					//System.err.println("\ncompanyConfigList -- "+ companyConfigList);
					
					FinalConfigList  = new ArrayList<CompanyConfiguration>();
					
					for (CompanyConfiguration defaultConfig : defaultConfigList) {
			            boolean foundInCompanyConfig = false;

			            for (CompanyConfiguration companyConfig : companyConfigList) {
			               
			            	if (defaultConfig.getName().equals(companyConfig.getName())) {
			            		
			            		//System.err.println("companyConfig.getName() -- " + companyConfig.getName());
			            		// System.err.println("value " + companyConfig.getValue());
			                    // Add the companyConfigList object to the final list
			                    FinalConfigList.add(companyConfig);
			                    foundInCompanyConfig = true;
			                    break; // No need to check further for this defaultConfig item
			                }
			            }

			            if (!foundInCompanyConfig) {
			            	System.err.println("inside flag if");
			                // Add the defaultConfigList object to the final list
			                FinalConfigList.add(defaultConfig);
			            }
			        }

					
					//System.err.println("finalList "+ FinalConfigList);
					//System.err.println("FInaal new SIze "+ FinalConfigList.size());
					return FinalConfigList;
					
					
				} catch (Exception e) {
					System.err.println("exepse  "+e);
					throw e;
				}finally {
				}
			}
		
}
