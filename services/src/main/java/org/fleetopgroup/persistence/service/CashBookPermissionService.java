package org.fleetopgroup.persistence.service;

import java.util.List;

import org.fleetopgroup.persistence.dao.CashBookPermissionRepository;
import org.fleetopgroup.persistence.model.CashBookPermission;
import org.fleetopgroup.persistence.serviceImpl.ICashBookPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Scope;

@Service
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CashBookPermissionService implements ICashBookPermissionService {

	@Autowired
	private CashBookPermissionRepository		cashBookPermissionRepository;
	
	@Override
	public List<CashBookPermission> getCashBookPermissionList(long userId, Integer companyId) throws Exception {
		
		return cashBookPermissionRepository.getCashBookPermissionList(userId, companyId);
	}

	@Override
	public StringBuffer getCashBookPermission(long userId, Integer companyId) throws Exception {
		List<CashBookPermission>	cashBookPermissionList		= null;
		StringBuffer				cashBookIds					= new StringBuffer();
		try {
			cashBookPermissionList	= getCashBookPermissionList(userId, companyId);
			for (int i = 0; i < cashBookPermissionList.size(); i++) {
				if (i < cashBookPermissionList.size() - 1) {
					cashBookIds = cashBookIds.append(cashBookPermissionList.get(i).getCashBookId() + ",");
				} else {
					cashBookIds = cashBookIds.append(cashBookPermissionList.get(i).getCashBookId());
				}
			} 
			return cashBookIds;
		} catch (Exception e) {
			throw e;
		}finally {
			cashBookPermissionList		= null;
		}
	}

	@Override
	@Transactional
	public void deleteCashBookPermissionByUserId(long userId, Integer companyId) throws Exception {
		
		cashBookPermissionRepository.deleteCashBookPermissionByUserId(userId, companyId);
		
	}
	
	@Override
	@Transactional
	public void registerCashBookPrmissionByUserId(CashBookPermission cashBookPermission) {
			
		cashBookPermissionRepository.save(cashBookPermission);
	}
}
