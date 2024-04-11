package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.PartLocationPermission;
import org.fleetopgroup.persistence.model.PartLocations;

public interface IPartLocationPermissionService {
	
	public List<PartLocationPermission> getPartLocationPermissionList(long userId, Integer companyId)throws Exception;
	
	public StringBuffer	getPartLocationPermission(long userId, Integer companyId) throws Exception;
	
	public void deletePartLocationPermissionByUserId(long userId, Integer companyId) throws Exception;
	
	void registerPartLocationPermissionByUserId(PartLocationPermission partLocationPermission);

	public List<PartLocations> getPartLocationPermissionIdWithName(long id, Integer company_id)  throws Exception ;
	
	public List<PartLocations> getPartLocationForPermissionCheck(long id, Integer company_id) throws Exception ;
}
