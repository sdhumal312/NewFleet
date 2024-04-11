package org.fleetopgroup.persistence.bl;

import java.util.ArrayList;
import java.util.List;

import org.fleetopgroup.constant.PriviledgeType;
import org.fleetopgroup.persistence.model.CompanyWisePrivileges;
import org.fleetopgroup.web.util.Utility;
import org.fleetopgroup.web.util.ValueObject;

public class CompanyWisePrivilegesBL {

	
	public List<CompanyWisePrivileges>  getCompanyModulePrivilegesDto(ValueObject	valueObject) throws Exception {
		CompanyWisePrivileges		modulePrivileges		= null;
		List<CompanyWisePrivileges> list					= null;
		try {
			String	privilegesIds	= Utility.removeLastComma(valueObject.getString("privilegesIds")) ;
			if(privilegesIds != null) {
				
				list	= new ArrayList<>();
				
				String[] privilegesArr	= privilegesIds.split(",");
				
				for(int i = 0; i< privilegesArr.length; i++) {
					modulePrivileges	= new CompanyWisePrivileges();	
					
					modulePrivileges.setPriviledgeId(Long.parseLong(privilegesArr[i]+""));
					modulePrivileges.setPriviledgeType(PriviledgeType.PRIVILEDGE_TYPE_MODULE);
					modulePrivileges.setMainPrivilegeId((long) 0);
					modulePrivileges.setCompanyId(valueObject.getInt("companyId"));
					
					list.add(modulePrivileges);
				}
			}
			
			
			
			
			return list;
		} catch (Exception e) {
			throw e;
		}finally {
			modulePrivileges		= null;
		}
	}
}
