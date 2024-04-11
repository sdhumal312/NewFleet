package org.fleetopgroup.persistence.serviceImpl;

import java.util.HashMap;

import org.fleetopgroup.persistence.model.ModulePrivileges;

public interface IModulePrivilegesService {

	HashMap<Long, ModulePrivileges>  getModulePrivilegesHM() throws Exception;
}
