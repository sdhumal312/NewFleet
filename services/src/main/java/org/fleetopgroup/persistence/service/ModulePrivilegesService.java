package org.fleetopgroup.persistence.service;

import java.util.HashMap;

import org.fleetopgroup.persistence.dao.ModulePrivilegesRepository;
import org.fleetopgroup.persistence.model.ModulePrivileges;
import org.fleetopgroup.persistence.serviceImpl.IModulePrivilegesService;
import org.springframework.beans.factory.annotation.Autowired;

public class ModulePrivilegesService implements IModulePrivilegesService {

	@Autowired ModulePrivilegesRepository		modulePrivilegesRepository;
	
	@Override
	public HashMap<Long, ModulePrivileges> getModulePrivilegesHM() throws Exception {
		try {
			
		} catch (Exception e) {
			throw e;
		}
		return null;
	}
	
}
