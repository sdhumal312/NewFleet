package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.TallyCompanyPermission;

public interface ITallyCompanyPermissionService {
	
	public StringBuffer getTallyCompanyPermission(long userId, Integer companyId) throws Exception;
	
	public List<TallyCompanyPermission>  getTallyCompanyPermissionList(long userId, Integer companyId) throws Exception;
}
