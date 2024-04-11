package org.fleetopgroup.persistence.serviceImpl;

import java.util.HashMap;

import org.fleetopgroup.persistence.model.Privilege;

public interface IPrivilegesService {

	HashMap<String, Privilege>  getPrivilegeHM() throws Exception;
}
