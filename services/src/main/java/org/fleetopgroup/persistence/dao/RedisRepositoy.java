package org.fleetopgroup.persistence.dao;

import org.fleetopgroup.persistence.dto.CustomUserDetails;

public interface RedisRepositoy {

	public void saveUser(CustomUserDetails customUserDetails) throws Exception;
}
