package org.fleetopgroup.persistence.serviceImpl;

import java.util.List;

import org.fleetopgroup.persistence.model.CashBookPermission;

public interface ICashBookPermissionService {
	
	public List<CashBookPermission> getCashBookPermissionList(long userId, Integer companyId)throws Exception;
	
	public StringBuffer	getCashBookPermission(long userId, Integer companyId) throws Exception;
	
	public void deleteCashBookPermissionByUserId(long userId, Integer companyId) throws Exception;
	
	void registerCashBookPrmissionByUserId(CashBookPermission cashBookPermission);
}
