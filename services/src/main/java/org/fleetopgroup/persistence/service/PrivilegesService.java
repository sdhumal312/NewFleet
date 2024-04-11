package org.fleetopgroup.persistence.service;

import java.util.HashMap;
import java.util.List;

import org.fleetopgroup.persistence.dao.PrivilegeRepository;
import org.fleetopgroup.persistence.model.Privilege;
import org.fleetopgroup.persistence.serviceImpl.IPrivilegesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrivilegesService implements IPrivilegesService {

	@Autowired	private PrivilegeRepository privilegeRepository;
	
	@Override
	public HashMap<String, Privilege> getPrivilegeHM() throws Exception {
		List<Privilege>   privilegeList		= null;
		HashMap<String, Privilege>	privilegeHM	= null;
		try {
			privilegeList	= privilegeRepository.findAll();
			if(privilegeList != null && privilegeList.size() > 0) {
				privilegeHM	= new HashMap<>();
				for (Privilege i : privilegeList) privilegeHM.put(i.getName(),i);

			}
			return privilegeHM;
		} catch (Exception e) {
			throw e;
		}
	}
}
