
package org.fleetopgroup.persistence.bl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
/**
 * @author fleetop
 *
 *
 *
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.fleetopgroup.persistence.model.CompanyConfiguration;
import org.fleetopgroup.web.util.ValueObject;


public class CompanyConfigurationBL {
	public CompanyConfigurationBL() {

	}

	public List<CompanyConfiguration> prepareCompanyConfiguration(Integer companyId, Map<String, Object> yamlData,
			Integer moduleId) {
		List<CompanyConfiguration> companyConfigList   = null;
		CompanyConfiguration       companyConfig       = null;
		
		companyConfigList  = new ArrayList<CompanyConfiguration>();
		for (Map.Entry<String, Object> entry : yamlData.entrySet()) {
		    companyConfig       = new CompanyConfiguration();
		
            String key = entry.getKey();
            Object value = entry.getValue();
            
            System.out.println("Key: " + key + ", Value: " + value);
            
            companyConfig.setModuleId(moduleId);
            companyConfig.setCompanyId(companyId);
            companyConfig.setName(key);  
            companyConfig.setValue(String.valueOf(value));
            companyConfig.setDescription(key);
            
            java.util.Date currentDateUpdate = new java.util.Date();
    		Timestamp addedON = new java.sql.Timestamp(currentDateUpdate.getTime());
    		
    		System.err.println("addedON "+ addedON);
            companyConfig.setAddedOn(addedON);
            
            System.err.println("\nn companyConfig-- "+ companyConfig );
            
            companyConfigList.add(companyConfig);
        }
		
		return companyConfigList;
	}
	
	public CompanyConfiguration prepareCompanyConfigObj(ValueObject object) {
		CompanyConfiguration  company = new CompanyConfiguration();
		try {
			company.setModuleId(object.getInt("moduleId"));
			company.setName(object.getString("propertyName"));
			company.setDescription(object.getString("propertyName"));
			company.setValue(object.getString("propertyValue"));
			company.setCompanyId(object.getInt("companyId"));
			
			java.util.Date currentDateUpdate = new java.util.Date();
    		Timestamp addedON = new java.sql.Timestamp(currentDateUpdate.getTime());
    		company.setAddedOn(addedON);
            company.setMarkForDelete(false);
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return company;
		
	}
	
	
}
	