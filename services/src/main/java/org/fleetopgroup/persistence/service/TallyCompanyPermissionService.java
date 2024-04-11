package org.fleetopgroup.persistence.service;

import java.util.List;

import org.fleetopgroup.persistence.dao.TallyCompanyPermissionRepository;
import org.fleetopgroup.persistence.model.TallyCompanyPermission;
import org.fleetopgroup.persistence.serviceImpl.ITallyCompanyPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TallyCompanyPermissionService implements ITallyCompanyPermissionService {
	
	@Autowired TallyCompanyPermissionRepository		tallyCompanyPermissionRepository;
	
	@Override
	public StringBuffer getTallyCompanyPermission(long userId, Integer companyId) throws Exception {
		 List<TallyCompanyPermission>		companyPermissions		= null;
		 StringBuffer 						companyIds				= new StringBuffer();
		try {
			companyPermissions	= getTallyCompanyPermissionList(userId, companyId);
			if(companyPermissions != null && !companyPermissions.isEmpty()) {
				for (int i = 0; i < companyPermissions.size(); i++) {
					if (i < companyPermissions.size() - 1) {
						companyIds = companyIds.append(companyPermissions.get(i).getVg_per_id() + ",");
					} else {
						companyIds = companyIds.append(companyPermissions.get(i).getVg_per_id());
					}
				} 
			}
			return companyIds;
			
		} catch (Exception e) {
			throw e;
		}finally {
			companyPermissions		= null;
			companyIds				= null;
		}
	}

	@Override
	public List<TallyCompanyPermission> getTallyCompanyPermissionList(long userId, Integer companyId) throws Exception {
		
		return tallyCompanyPermissionRepository.getTallyCompanyPermissionList(userId, companyId);
	}
}
